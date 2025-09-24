package com.asopagos.validaciones.fovis.validadores.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
import com.asopagos.validaciones.fovis.util.ValidacionFovisPersonaUtils;

/**
 * Clase que contiene la lógica para validar si una persona tiene afiliado un beneficiario tipo padre activo o inactivo.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorAfiliadoPrincipalBeneficiarioPadre extends ValidadorFovisAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorAfiliadoPrincipalBeneficiarioPadre.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoIdA = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
                TipoIdentificacionEnum tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoIdA);
                String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
                Long idBeneficiario = null;
                ClasificacionEnum clasificacionEnum = ClasificacionEnum
                        .valueOf(datosValidacion.get(ConstantesValidaciones.CLASIFICACION_PARAM));
                if (datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO) != null) {
                    idBeneficiario = new Long(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO));
                }
                if (tipoIdentificacionAfiliado != null && numeroIdentificacionAfiliado != null
                        && !numeroIdentificacionAfiliado.equals("")) {
                    List<ClasificacionEnum> tipoBeneficiarioList = new ArrayList<>();
                    tipoBeneficiarioList.add(ClasificacionEnum.PADRE);
                    boolean existe = validarBeneficiario(tipoIdentificacionAfiliado, numeroIdentificacionAfiliado, idBeneficiario,
                            tipoBeneficiarioList);
                    if (existe) {
                        ValidacionDTO validacionClasificacion = validarClasificacion(clasificacionEnum);
                        if (validacionClasificacion != null) {
                            return validacionClasificacion;
                        }
                        
                        logger.debug("No aprobada- Existe persona beneficiario tipo padre activo o inactivo");
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_PADRE_REGISTRADO),
                                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_PADRE,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
                }
                else {
                    logger.debug("NO EVALUADO - No hay parametros");
                    return crearMensajeNoEvaluado();
                }
            }
            else {
                logger.debug("NO EVALUADO- no hay valores en el map");
                return crearMensajeNoEvaluado();
            }
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_PADRE);
        } catch (Exception e) {
            logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Método encargado de validar el beneficiario
     * @param tipoIdentificacionAfiliado
     * @param numeroIdentificacionAfiliado
     * @param idBeneficiario
     * @param tipoBeneficiarioList
     * @return retorna el resultado de la validacion
     */
    private boolean validarBeneficiario(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado,
            Long idBeneficiario, List<ClasificacionEnum> tipoBeneficiarioList) {
        boolean existe = false;
        List<Beneficiario> personasConTipoYNumero = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADOS)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionAfiliado)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
                .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
                .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionFovisPersonaUtils.obtenerEstadosAfiliado()).getResultList();
        if (personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) {
            List<Beneficiario> personasConNumero = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADOS)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionFovisPersonaUtils.obtenerEstadosAfiliado())
                    .getResultList();
            if (personasConNumero != null && !personasConNumero.isEmpty()) {
                existe = true;
                if (personasConNumero.iterator().next().getIdBeneficiario().equals(idBeneficiario)) {
                    existe = false;
                }
            }
        }
        else {
            existe = true;
            if (personasConTipoYNumero.iterator().next().getIdBeneficiario().equals(idBeneficiario)) {
                existe = false;
            }
        }
        return existe;
    }

    /**
     * Mensaje utilizado cuando por alguna razon no se puede evaluar.
     * @return validacion afiliaacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_PADRE_REGISTRADO),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_PADRE,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Método encargado de validar la clasificacion
     * @param clasificacionEnum,
     *        clasificacion a validar
     * @return retorna la validacion dto
     */
    private ValidacionDTO validarClasificacion(ClasificacionEnum clasificacionEnum) {
        if (clasificacionEnum != null) {
            //Caso 1
            if (clasificacionEnum.getSujetoTramite().equals(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
                    || clasificacionEnum.getSujetoTramite().equals(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)
                    || clasificacionEnum.getSujetoTramite().equals(TipoAfiliadoEnum.PENSIONADO)
                    || clasificacionEnum.getSujetoTramite().equals(TipoBeneficiarioEnum.CONYUGE)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_HIJO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
            }
            //Caso 2
            if (clasificacionEnum.getSujetoTramite().equals(TipoBeneficiarioEnum.HIJO)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_HIJO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_HIJO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
            }
        }
        return null;
    }
}
