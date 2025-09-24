package com.asopagos.solicitud.composite.factory;

import com.asopagos.aportes.clients.ActualizarEstadoSolicitud;
import com.asopagos.aportes.clients.ConsultarSolicitudAporte;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudAporte extends Solicitud {
    
    private ILogger logger = LogManager.getLogger(SolicitudAporte.class);
    
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
       
        ConsultarSolicitudAporte consultarSolicitudAporte = new ConsultarSolicitudAporte(inDTO.getIdSolicitudGlobal());
        consultarSolicitudAporte.execute();
        SolicitudAporteModeloDTO solicitudAporteModeloDTO = consultarSolicitudAporte.getResult();
        
        if(solicitudAporteModeloDTO == null){
            logger.error("El valor del número de radicado de la solicitud de aporte no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoSolicitudAporteEnum estadoSolicitud = EstadoSolicitudAporteEnum
                .valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitud actualizarEstadoSolicitud = new ActualizarEstadoSolicitud(inDTO.getIdSolicitudGlobal(), estadoSolicitud);
        actualizarEstadoSolicitud.execute();
        
        actualizarEstadoSolicitud = new ActualizarEstadoSolicitud(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAporteEnum.CERRADA);
        actualizarEstadoSolicitud.execute();
        
        AbortarProceso abortarProceso = new AbortarProceso(solicitudAporteModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
    }

}
