package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudNovedadFovisModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.ActualizarEstadoSolicitudNovedadFovis;
import com.asopagos.novedades.fovis.clients.ConsultarSolicitudNovedadFovis;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudNovedadFovis extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudNovedadFovis.class);

    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudNovedadFovis consultarSolicitudNovedadFovis = new ConsultarSolicitudNovedadFovis(inDTO.getIdSolicitudGlobal());
        consultarSolicitudNovedadFovis.execute();
        SolicitudNovedadFovisModeloDTO solicitudNovedadFovisModeloDTO = consultarSolicitudNovedadFovis.getResult();

        if (solicitudNovedadFovisModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de postulación FOVIS no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        // Se actualiza el estado a CANCELADA o DESISTIDA
        EstadoSolicitudNovedadFovisEnum estadoSolicitud = EstadoSolicitudNovedadFovisEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudNovedadFovis actualizarEstado = new ActualizarEstadoSolicitudNovedadFovis(inDTO.getIdSolicitudGlobal(),
                estadoSolicitud);
        actualizarEstado.execute();
        // Se actualiza el estado a CERRADA
        actualizarEstado = new ActualizarEstadoSolicitudNovedadFovis(inDTO.getIdSolicitudGlobal(),
                EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_CERRADA);
        actualizarEstado.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudNovedadFovisModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();

    }

}
