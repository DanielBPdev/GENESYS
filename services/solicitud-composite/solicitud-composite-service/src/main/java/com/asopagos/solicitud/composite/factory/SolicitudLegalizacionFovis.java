package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.legalizacionfovis.clients.ActualizarEstadoSolicitudLegalizacionDesembolso;
import com.asopagos.legalizacionfovis.clients.ConsultarSolicitudLegalizacionDesembolso;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudLegalizacionFovis extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudLegalizacionFovis.class);

    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudLegalizacionDesembolso consultarSolicitudLegalizacionDesembolso = new ConsultarSolicitudLegalizacionDesembolso(
                inDTO.getIdSolicitudGlobal());
        consultarSolicitudLegalizacionDesembolso.execute();
        SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoModeloDTO = consultarSolicitudLegalizacionDesembolso
                .getResult();

        if (solicitudLegalizacionDesembolsoModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de postulación FOVIS no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        // Se actualiza el estado a CANCELADA o DESISTIDA
        EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud = EstadoSolicitudLegalizacionDesembolsoEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudLegalizacionDesembolso actualizarEstado = new ActualizarEstadoSolicitudLegalizacionDesembolso(
                inDTO.getIdSolicitudGlobal(), estadoSolicitud);
        actualizarEstado.execute();
        // Se actualiza el estado a CERRADA
        actualizarEstado = new ActualizarEstadoSolicitudLegalizacionDesembolso(inDTO.getIdSolicitudGlobal(),
                EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_CERRADO);
        actualizarEstado.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudLegalizacionDesembolsoModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();

    }

}
