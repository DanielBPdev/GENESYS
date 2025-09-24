package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada para el mismo emleador.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaGrupoFamiliar extends ValidadorAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorPersonaGrupoFamiliar.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
                String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                String tipoIdA = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
                TipoIdentificacionEnum tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoIdA);
                String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
                Long idBeneficiario=null;
                Long idGrupoFamiliar=null;
                if(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO) != null){
                    idBeneficiario =new Long(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO));
                }
                if (datosValidacion.get(ConstantesValidaciones.ID_GRUPOFAMILIAR) != null) {
                    idGrupoFamiliar =new Long(datosValidacion.get(ConstantesValidaciones.ID_GRUPOFAMILIAR));
                }

                if (tipoIdentificacionAfiliado != null && numeroIdentificacionAfiliado != null && !numeroIdentificacionAfiliado.equals("")
                        && tipoIdentificacion != null && numeroIdentificacion != null && !numeroIdentificacion.equals("")) {
                    boolean existe = false;
                    List<Beneficiario> personasConTipoYNumero = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_ESTADO)
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                            .setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacionAfiliado)
                            .setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionAfiliado)
                            .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerListaEstadoActivo())
                            .getResultList();
                    if (personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) {
                        List<Beneficiario> personasConNumero = entityManager
                                .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_ESTADO)
                                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                                .setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacionAfiliado)
                                .setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionAfiliado)
                                .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerListaEstadoActivo())
                                .getResultList();
                        if (personasConNumero != null && !personasConNumero.isEmpty()) {
                            if (idGrupoFamiliar != null && idGrupoFamiliar
                                    .equals(personasConNumero.iterator().next().getGrupoFamiliar().getIdGrupoFamiliar())) {
                                existe = false;
                            }
                            else {
                                existe = true;
                            }
                        }
                        else {
                            existe = false;
                        }
                    }
                    else {
                        if (idGrupoFamiliar != null && idGrupoFamiliar
                                .equals(personasConTipoYNumero.iterator().next().getGrupoFamiliar().getIdGrupoFamiliar())) {
                            existe = false;
                        }
                        else {
                            existe = true;
                        }
                    }
                    if (existe) {
                        logger.debug("No aprobada- La persona ya existe en grupo familiar");
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_CON_GRUPO_FAMILIAR),
                                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_GRUPO_FAMILIAR,
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
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_GRUPO_FAMILIAR);
        } catch (Exception e) {
            logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razon no se puede evaluar.
     * @return validacion afiliaacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_CON_GRUPO_FAMILIAR),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_GRUPO_FAMILIAR,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

}
