package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica si una solicitud de postulacion se encuentra en estado POSTULACION RECHAZADA
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPostulacionRechazada extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            if (!postulacionRechazada(getIdPostulacion(datosValidacion))) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_SOLICITUD_POSTULACION_NO_RECHAZADA),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_POSTULACION_RECHAZADA_51_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_POSTULACION_RECHAZADA_51_FOVIS);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_POSTULACION_RECHAZADA_51_FOVIS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si la solicitud de postulacion se encuentra en estado POSTULACION RECHAZADA
     * @param idPostulacion
     * @return
     */
    private boolean postulacionRechazada(Long idPostulacion) {
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_SOLICITUD_POSTULACION)
                    .setParameter(ConstantesValidaciones.ESTADO_SOLICITUD_POSTULACION,
                            EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA.name())
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
