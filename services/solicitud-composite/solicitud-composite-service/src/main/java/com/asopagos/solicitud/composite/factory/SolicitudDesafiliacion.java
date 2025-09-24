package com.asopagos.solicitud.composite.factory;

import com.asopagos.cartera.clients.ActualizarEstadoSolicitudDesafiliacion;
import com.asopagos.cartera.clients.ConsultarSolicitudDesafiliacion;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudDesafiliacionModeloDTO;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudDesafiliacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class SolicitudDesafiliacion extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudDesafiliacion.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.solicitud.composite.factory.Solicitud#actualizarEstadoSolicitud(com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO)
     */
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {

        ConsultarSolicitudDesafiliacion consultarSolicitudDesafiliacion = new ConsultarSolicitudDesafiliacion(inDTO.getNumeroRadicado());
        consultarSolicitudDesafiliacion.execute();
        SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO = consultarSolicitudDesafiliacion.getResult();

        if (solicitudDesafiliacionModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de gestion Fiscalizacion no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        EstadoSolicitudDesafiliacionEnum estadoSolicitud = EstadoSolicitudDesafiliacionEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudDesafiliacion actualizarEstadoSolicitud = new ActualizarEstadoSolicitudDesafiliacion(
                inDTO.getNumeroRadicado(), estadoSolicitud);
        actualizarEstadoSolicitud.execute();

        actualizarEstadoSolicitud = new ActualizarEstadoSolicitudDesafiliacion(inDTO.getNumeroRadicado(),
                EstadoSolicitudDesafiliacionEnum.CERRADA);
        actualizarEstadoSolicitud.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudDesafiliacionModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
    }

}
