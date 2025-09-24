package com.asopagos.solicitud.composite.factory;

import com.asopagos.aportes.clients.ActualizarEstadoSolicitud;
import com.asopagos.aportes.clients.ActualizarEstadoSolicitudCierre;
import com.asopagos.aportes.clients.ConsultarSolicitudAporte;
import com.asopagos.aportes.clients.ConsultarSolicitudCierreRecaudo;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudCierreRecaudo extends Solicitud {
    
    private ILogger logger = LogManager.getLogger(SolicitudCierreRecaudo.class);
    
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
       
        ConsultarSolicitudCierreRecaudo consultarSolicitudCierre= new ConsultarSolicitudCierreRecaudo(inDTO.getNumeroRadicado());
        consultarSolicitudCierre.execute();
        SolicitudCierreRecaudoModeloDTO solicitudCierreModeloDTO = consultarSolicitudCierre.getResult();
        
        if(solicitudCierreModeloDTO == null){
            logger.error("El valor del número de radicado de la solicitud de cierre de recaudo de aportes no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoSolicitudCierreRecaudoEnum estadoSolicitud = EstadoSolicitudCierreRecaudoEnum
                .valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudCierre actualizarEstadoSolicitud = new ActualizarEstadoSolicitudCierre(estadoSolicitud, inDTO.getNumeroRadicado());
        actualizarEstadoSolicitud.execute();
        
        actualizarEstadoSolicitud = new ActualizarEstadoSolicitudCierre(EstadoSolicitudCierreRecaudoEnum.CERRADA, inDTO.getNumeroRadicado());
        actualizarEstadoSolicitud.execute();
        
        AbortarProceso abortarProceso = new AbortarProceso(solicitudCierreModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
    }

}
