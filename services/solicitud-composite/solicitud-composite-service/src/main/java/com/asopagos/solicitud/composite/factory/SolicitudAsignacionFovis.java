package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudAsignacionFOVISModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAsignacionEnum;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudAsignacion;
import com.asopagos.fovis.clients.ConsultarSolicitudAsignacionPorSolicitudGlobal;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudAsignacionFovis extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudAsignacionFovis.class);

    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudAsignacionPorSolicitudGlobal consultarSolicitudAsignacion = new ConsultarSolicitudAsignacionPorSolicitudGlobal(
                inDTO.getIdSolicitudGlobal());
        consultarSolicitudAsignacion.execute();
        SolicitudAsignacionFOVISModeloDTO solicitudAsignacionFOVISModeloDTO = consultarSolicitudAsignacion.getResult();

        if (solicitudAsignacionFOVISModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de postulación FOVIS no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        // Se actualiza el estado a CANCELADA o DESISTIDA
        EstadoSolicitudAsignacionEnum estadoSolicitud = EstadoSolicitudAsignacionEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudAsignacion actualizarEstado = new ActualizarEstadoSolicitudAsignacion(inDTO.getIdSolicitudGlobal(),
                estadoSolicitud);
        actualizarEstado.execute();
        // Se actualiza el estado a CERRADA
        actualizarEstado = new ActualizarEstadoSolicitudAsignacion(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAsignacionEnum.CERRADA);
        actualizarEstado.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudAsignacionFOVISModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();

    }

}
