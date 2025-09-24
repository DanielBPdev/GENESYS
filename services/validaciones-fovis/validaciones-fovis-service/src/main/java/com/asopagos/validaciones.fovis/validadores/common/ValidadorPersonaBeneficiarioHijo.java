package com.asopagos.validaciones.fovis.validadores.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
import com.asopagos.validaciones.fovis.util.ValidacionFovisPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona existe como beneficiario tipo hijo.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaBeneficiarioHijo extends ValidadorFovisAbstract {

    /**
     * Metodo que se encarga de realizar la validacion Persona Beneficiario Hijo
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            logger.debug("Inicio ValidadorPersonaBeneficiarioHijo");
            boolean existe = false;
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
                String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                String identificadorGrupoFamiliar = datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR);
                Long idGrupoFamiliar = 0L;
                if (identificadorGrupoFamiliar!=null){
                    idGrupoFamiliar = Long.parseLong(identificadorGrupoFamiliar);    
                }
                
                TipoAfiliadoEnum tipoAfiliado = null;
                TipoBeneficiarioEnum tipoBeneficiario = null;
                List<Long> idBeneficiarios = null;
                try {
                    tipoBeneficiario = TipoBeneficiarioEnum.valueOf(datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM));
                } catch (Exception e) {
                    tipoBeneficiario = null;
                    try {
                        tipoAfiliado = TipoAfiliadoEnum.valueOf(datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM));
                    } catch (Exception e2) {
                        tipoAfiliado = null;
                    }
                }

                if (tipoIdentificacion != null) {
                    idBeneficiarios = validarBeneficiario(tipoIdentificacion, numeroIdentificacion, idGrupoFamiliar);
                    if(idBeneficiarios != null && !idBeneficiarios.isEmpty()){
                        existe=true;
                    }
                }
                else {
                    /* mensaje no evaluado porque no hay parametros */
                    return crearMensajeNoEvaluado();
                }
                if (existe) {
                    ValidacionDTO validacionClasificacion = validarClasificacion(tipoAfiliado,tipoBeneficiario,idBeneficiarios);
                    if (validacionClasificacion != null) {
                        return validacionClasificacion;
                    }
                    /*
                     * No aprobada- Existe Persona registrada como beneficiario
                     * tipo hijo y estado afiliado activo
                     */
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_HIJO),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
                }
            }
            else {
                /* mensaje no evaluado porque no llegaron datos */
                return crearMensajeNoEvaluado();
            }
            /* exitoso */
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO);
        } catch (Exception e) {
            logger.error("Ocurrió un error en ValidadorPersonaBeneficiarioHijo.execute", e);
            /* No evaluado ocurrió alguna excepción */
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * @param existe
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private List<Long> validarBeneficiario(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long idGrupoFamiliar) {

        List<Long> idBeneficiarios = new ArrayList<Long>();
        List<Beneficiario> beneficiarioTipoNumeroActivo = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO_GRUP_FAM)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionFovisPersonaUtils.obtenerClasificacionHijo())
                .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
                .setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar).getResultList();
        if (beneficiarioTipoNumeroActivo != null && !beneficiarioTipoNumeroActivo.isEmpty()) {
            for (Beneficiario beneficiario : beneficiarioTipoNumeroActivo) {
                idBeneficiarios.add(beneficiario.getIdBeneficiario());
            }
        }
        else {
            List<Beneficiario> beneficiarioNumeroActivo = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO_GRUP_FAM)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionFovisPersonaUtils.obtenerClasificacionHijo())
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
                    .setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar).getResultList();
            if (beneficiarioNumeroActivo != null && !beneficiarioNumeroActivo.isEmpty()) {
                for (Beneficiario beneficiario : beneficiarioNumeroActivo) {
                    idBeneficiarios.add(beneficiario.getIdBeneficiario());
                }
            }
        }
        return idBeneficiarios;
    }

    /**
     * Mensaje utilizado cuando por alguna razon no se puede evaluar.
     * 
     * @return validacion afiliaacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_HIJO),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Método encargado de validar la clasificacion
     * @param clasificacionEnum,
     *        clasificacion a validar
     * @return retorna la validacion dto
     */
    private ValidacionDTO validarClasificacion(TipoAfiliadoEnum tipoAfiliado, TipoBeneficiarioEnum tipoBeneficiario, List<Long> idBeneficiarios) {

        // Caso 1
        if (tipoAfiliado != null) {
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_HIJO),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1,idBeneficiarios);
        }
        else if (tipoBeneficiario != null) {
            if (tipoBeneficiario.equals(TipoBeneficiarioEnum.CONYUGE)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_HIJO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1,idBeneficiarios);
            }
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_HIJO),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
        }
        return null;
    }
}
