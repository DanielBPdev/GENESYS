package com.asopagos.solicitud.composite.factory;

import com.asopagos.cartera.clients.ActualizarEstadoSolicitudPreventiva;
import com.asopagos.cartera.clients.ConsultarSolicitudPreventiva;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudPreventivaModeloDTO;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudGestionPreventiva extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudGestionPreventiva.class);
    
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        
        ConsultarSolicitudPreventiva consultarSolicitudPreventiva = new ConsultarSolicitudPreventiva(inDTO.getNumeroRadicado());
        consultarSolicitudPreventiva.execute();
        SolicitudPreventivaModeloDTO solicitudPreventivaModeloDTO = consultarSolicitudPreventiva.getResult();
        
        if(solicitudPreventivaModeloDTO == null){
            logger.error("El valor del número de radicado de la solicitud de gestion preventiva no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoSolicitudPreventivaEnum estadoSolicitud = EstadoSolicitudPreventivaEnum
                .valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudPreventiva actualizarEstadoSolicitud = new ActualizarEstadoSolicitudPreventiva(inDTO.getNumeroRadicado(), estadoSolicitud);
        actualizarEstadoSolicitud.execute();
        
        actualizarEstadoSolicitud = new ActualizarEstadoSolicitudPreventiva(inDTO.getNumeroRadicado(), EstadoSolicitudPreventivaEnum.CERRADA);
        actualizarEstadoSolicitud.execute();
        
        AbortarProceso abortarProceso = new AbortarProceso(solicitudPreventivaModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();

    }

}
