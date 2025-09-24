package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.validaciones.constants.NamedQueriesConstants;

/**
 * Validador que verifica si el hogar tiene estado:
 * Postulado
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorEstadoHogarPostuladoCierreFinanciero extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            // Lista con los estados del hogar definidos para la validación
            List<String> estadosHogar = new ArrayList<>();

            String idPostulacion = datosValidacion.get(ConstantesValidaciones.ID_POSTULACION);
            Boolean suspencionCambioAnio = false;
            Object[] postulacionAEvaluar = null;
            List<Object[]> postulaciones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_HOGAR_CIERRE_FINANCIERO)
                    .setParameter(ConstantesValidaciones.NUMERO_IDENTIFICACION_AFILIADO, datosValidacion.get(ConstantesValidaciones.NUMERO_IDENTIFICACION_AFILIADO))
                    .setParameter(ConstantesValidaciones.TIPO_IDENTIFICACION_AFILIADO, datosValidacion.get(ConstantesValidaciones.TIPO_IDENTIFICACION_AFILIADO)).getResultList();

                    
            logger.info("tamaño postulaciones "+postulaciones.size());
            if(!postulaciones.isEmpty()){
                if(postulaciones.size() == 1){
                    logger.info("EstadoHogarEnum "+EstadoHogarEnum.valueOf(postulaciones.get(0)[1].toString()));
                    logger.info("ResultadoAsignacionEnum "+postulaciones.get(0)[2]);
                    logger.info("condicion 1 "+EstadoHogarEnum.valueOf(postulaciones.get(0)[1].toString()).equals(EstadoHogarEnum.POSTULADO));
                    logger.info("condicion 2 "+postulaciones.get(0)[2] == null);
                    if(EstadoHogarEnum.valueOf(postulaciones.get(0)[1].toString()).equals(EstadoHogarEnum.POSTULADO) && postulaciones.get(0)[2] == null){
                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO);
                    }else if((EstadoHogarEnum.valueOf(postulaciones.get(0)[1].toString()).equals(EstadoHogarEnum.HABIL_SEGUNDO_ANIO) || EstadoHogarEnum.valueOf(postulaciones.get(0)[1].toString()).equals(EstadoHogarEnum.HABIL))
                              && ResultadoAsignacionEnum.valueOf(postulaciones.get(0)[2].toString()).equals(ResultadoAsignacionEnum.ESTADO_CALIFICADO_NO_ASIGNADO) 
                              && (EstadoSolicitudPostulacionEnum.valueOf(postulaciones.get(0)[0].toString()).equals(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA)||EstadoSolicitudPostulacionEnum.valueOf(postulaciones.get(0)[0].toString()).equals(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO))){

                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO);
                    }else{
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HOGAR_NO_POSTULADO),
                                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO,
                                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
                }else{
                    for(Object[] postulacion : postulaciones){
                        if(EstadoHogarEnum.valueOf(postulacion[1].toString()).equals(EstadoHogarEnum.SUSPENDIDO_POR_CAMBIO_DE_ANIO)){
                            suspencionCambioAnio = true;
                        }
                        logger.info("postulacion[3] "+postulacion[3]);
                        logger.info("ID_POSTULACION "+datosValidacion.get(ConstantesValidaciones.ID_POSTULACION));
                        if(postulacion[3].toString().equals(datosValidacion.get(ConstantesValidaciones.ID_POSTULACION))){
                            postulacionAEvaluar = postulacion;
                        }
                    }
                    logger.info("suspencionCambioAnio "+suspencionCambioAnio);
                    logger.info("postulacionAEvaluar "+postulacionAEvaluar);
                    if(suspencionCambioAnio && EstadoHogarEnum.valueOf(postulacionAEvaluar[1].toString()).equals(EstadoHogarEnum.POSTULADO)){
                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO);

                    }else if((EstadoHogarEnum.valueOf(postulacionAEvaluar[1].toString()).equals(EstadoHogarEnum.HABIL_SEGUNDO_ANIO) || EstadoHogarEnum.valueOf(postulacionAEvaluar[1].toString()).equals(EstadoHogarEnum.HABIL))
                              && ResultadoAsignacionEnum.valueOf(postulacionAEvaluar[2].toString()).equals(ResultadoAsignacionEnum.ESTADO_CALIFICADO_NO_ASIGNADO) 
                              && (EstadoSolicitudPostulacionEnum.valueOf(postulacionAEvaluar[0].toString()).equals(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA)
                                    || EstadoSolicitudPostulacionEnum.valueOf(postulacionAEvaluar[0].toString()).equals(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO))){

                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO);
                    }else{
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HOGAR_NO_POSTULADO),
                                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO,
                                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
                }
            }
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HOGAR_NO_POSTULADO),
                                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO,
                                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_CIERRE_FINANCIERO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
