package com.asopagos.solicitud.composite.factory;

import com.asopagos.aportes.clients.ActualizarEstadoSolicitudDevolucion;
import com.asopagos.aportes.clients.ConsultarSolicitudDevolucionAporte;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudDevolucionAporte extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudDevolucionAporte.class);
    
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        
        ConsultarSolicitudDevolucionAporte consultarSolicitudDevolucionAporte = new ConsultarSolicitudDevolucionAporte(inDTO.getIdSolicitudGlobal());
        
        consultarSolicitudDevolucionAporte.execute();
        SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO = consultarSolicitudDevolucionAporte.getResult();
        
        if(solicitudDevolucionAporteModeloDTO == null){
            logger.error("El valor del número de radicado de la solicitud de devolución de aporte no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoSolicitudAporteEnum estadoSolicitud = EstadoSolicitudAporteEnum
                .valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudDevolucion actualizarDevoloucion = new ActualizarEstadoSolicitudDevolucion(inDTO.getIdSolicitudGlobal(), estadoSolicitud);
        actualizarDevoloucion.execute();
        
        actualizarDevoloucion = new ActualizarEstadoSolicitudDevolucion(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAporteEnum.CERRADA);
        actualizarDevoloucion.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudDevolucionAporteModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
    }

}
