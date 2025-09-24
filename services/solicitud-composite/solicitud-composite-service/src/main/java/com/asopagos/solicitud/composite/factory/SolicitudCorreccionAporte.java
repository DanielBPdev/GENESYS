package com.asopagos.solicitud.composite.factory;

import com.asopagos.aportes.clients.ActualizarEstadoSolicitudCorreccion;
import com.asopagos.aportes.clients.ConsultarSolicitudCorreccionAporte;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudCorreccionAporte extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudCorreccionAporte.class);
            
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        
        ConsultarSolicitudCorreccionAporte consultarSolicitudCorreccionAporte = new ConsultarSolicitudCorreccionAporte(inDTO.getIdSolicitudGlobal());
        
        consultarSolicitudCorreccionAporte.execute();
        SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteModeloDTO = consultarSolicitudCorreccionAporte.getResult();
        
        if(solicitudCorreccionAporteModeloDTO == null){
            logger.error("El valor del número de radicado de la solicitud de correction de aporte no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoSolicitudAporteEnum estadoSolicitud = EstadoSolicitudAporteEnum
                .valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudCorreccion actualizarCorreccion = new ActualizarEstadoSolicitudCorreccion(inDTO.getIdSolicitudGlobal(), estadoSolicitud);
        actualizarCorreccion.execute();
        
        actualizarCorreccion = new ActualizarEstadoSolicitudCorreccion(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAporteEnum.CERRADA);
        actualizarCorreccion.execute();

        logger.info("Proceso:" + solicitudCorreccionAporteModeloDTO.getTipoTransaccion().getProceso());
        AbortarProceso abortarProceso = new AbortarProceso(solicitudCorreccionAporteModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
        
    }

}
