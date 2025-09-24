package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudGestionCruce;
import com.asopagos.fovis.clients.ConsultarSolicitudGestionCrucePorSolicitudGlobal;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudGestionCruceFovis extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudGestionCruceFovis.class);

    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudGestionCrucePorSolicitudGlobal consultarSolicitudGestionCruce = new ConsultarSolicitudGestionCrucePorSolicitudGlobal(
                inDTO.getIdSolicitudGlobal());
        consultarSolicitudGestionCruce.execute();
        SolicitudGestionCruceDTO solicitudGestionCruceDTO = consultarSolicitudGestionCruce.getResult();

        if (solicitudGestionCruceDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de postulación FOVIS no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        // Se actualiza el estado a CANCELADA o DESISTIDA
        EstadoSolicitudGestionCruceEnum estadoSolicitud = EstadoSolicitudGestionCruceEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudGestionCruce actualizarEstado = new ActualizarEstadoSolicitudGestionCruce(
                inDTO.getIdSolicitudGlobal(), estadoSolicitud);
        actualizarEstado.execute();
        // Se actualiza el estado a CERRADA
        actualizarEstado = new ActualizarEstadoSolicitudGestionCruce(inDTO.getIdSolicitudGlobal(),
                EstadoSolicitudGestionCruceEnum.CERRADA);
        actualizarEstado.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudGestionCruceDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();

    }

}
