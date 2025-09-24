package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudVerificacionFovis;
import com.asopagos.fovis.clients.ConsultarSolicitudVerificacionFovis;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudVerificacionPostulacionFovis extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudVerificacionPostulacionFovis.class);

    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudVerificacionFovis consultarSolicitudVerificacionFovis = new ConsultarSolicitudVerificacionFovis(
                inDTO.getIdSolicitudGlobal());
        consultarSolicitudVerificacionFovis.execute();
        SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisModeloDTO = consultarSolicitudVerificacionFovis.getResult();

        if (solicitudVerificacionFovisModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de postulación FOVIS no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        // Se actualiza el estado a CANCELADA o DESISTIDA
        EstadoSolicitudVerificacionFovisEnum estadoSolicitud = EstadoSolicitudVerificacionFovisEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudVerificacionFovis actualizarEstado = new ActualizarEstadoSolicitudVerificacionFovis(
                inDTO.getIdSolicitudGlobal(), estadoSolicitud);
        actualizarEstado.execute();
        // Se actualiza el estado a CERRADA
        actualizarEstado = new ActualizarEstadoSolicitudVerificacionFovis(inDTO.getIdSolicitudGlobal(),
                EstadoSolicitudVerificacionFovisEnum.CERRADA);
        actualizarEstado.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudVerificacionFovisModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();

    }

}
