package com.asopagos.validaciones.validadores.novedades.personas;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Evalua las condiciones para la habilitación de las novedades que dependen del estado 
 * general del afiliado con respecto a la CCF
 * @author mamonroy
 *
 */
public class ValidacionEstadoGeneralPersonaCCF extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {

        try {
            logger.debug("Inicio de método ValidacionEstadoGeneralPersonaCCF.execute");

            EstadoAfiliadoEnum estadoAfiliado = null;
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            BigInteger idResult = null;
            String estadoAfiliadoObj = null;
            String estado = null;

            estadoAfiliadoObj = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIADO_CCF)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
            if (estadoAfiliadoObj == null) {
                logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                        ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }

            estadoAfiliado = EstadoAfiliadoEnum.valueOf(estadoAfiliadoObj);
            switch (estadoAfiliado) {
                case ACTIVO:
                    try {
                        idResult = (BigInteger) entityManager
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_AFILIACION_DEPENDIENTE_ACTIVA)
                                .setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name())
                                .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO.name())
                                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
                        logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO);
                    } catch (Exception e) {
                        logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute", e);
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MSG_AFILIADO_ESTADO_INVALIDO_CCF),
                                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
                case INACTIVO:
                    estado = (String) entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_AFILIACION_DEPENDIENTE_INACTIVA)
                            .setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name())
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
                    if (EstadoAfiliadoEnum.INACTIVO.name().equals(estado)) {
                        logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO);
                    }
                    logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MSG_AFILIADO_ESTADO_INVALIDO_CCF),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                case NO_FORMALIZADO_RETIRADO_CON_APORTES:
                case NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES:
                    estado = (String) entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_AFILIACION_DEPENDIENTE_INACTIVA)
                            .setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name())
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
                    logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");

                    if (estado != null) {
                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO);
                    }
                    List<BigInteger> aportes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTES_AFILIADO)
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
                    //Se evalua si ha realizado aportes
                    if (!aportes.isEmpty()) {
                        logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO);
                    }
                    logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MSG_AFILIADO_ESTADO_INVALIDO_CCF),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                default:
                    logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MSG_AFILIADO_ESTADO_INVALIDO_CCF),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("Fin de método ValidacionEstadoGeneralPersonaCCF.execute", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_ESTADO_CCF_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }

    }

}
