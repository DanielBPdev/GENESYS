package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudAsociacionPersonaEntidadPagadoraModeloDTO;
import com.asopagos.entidades.pagadoras.clients.ActualizarEstadoSolicitudEntidadPagadora;
import com.asopagos.entidades.pagadoras.clients.ConsultarSolicitudEntidadPagadora;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudEntidadPagadora extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudAporte.class);
    
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        
        ConsultarSolicitudEntidadPagadora consultarSolicitudEntidadPagadora = new ConsultarSolicitudEntidadPagadora(inDTO.getIdSolicitudGlobal());
        consultarSolicitudEntidadPagadora.execute();
        SolicitudAsociacionPersonaEntidadPagadoraModeloDTO solicitudEntidadPagadora = consultarSolicitudEntidadPagadora.getResult();
        
        if(solicitudEntidadPagadora == null){
            logger.error("El valor del número de radicado de la solicitud de aporte no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoSolicitudPersonaEntidadPagadoraEnum estadoSolicitudEntidadPagadora = 
                EstadoSolicitudPersonaEntidadPagadoraEnum.valueOf(inDTO.getEstado());
        
        ActualizarEstadoSolicitudEntidadPagadora actualizarEstadoSolicitud = new ActualizarEstadoSolicitudEntidadPagadora(inDTO.getIdSolicitudGlobal(), estadoSolicitudEntidadPagadora);
        actualizarEstadoSolicitud.execute();
        
        actualizarEstadoSolicitud = new ActualizarEstadoSolicitudEntidadPagadora(inDTO.getIdSolicitudGlobal(), EstadoSolicitudPersonaEntidadPagadoraEnum.CERRADA);
        actualizarEstadoSolicitud.execute();
        
        AbortarProceso abortarProceso = new AbortarProceso(solicitudEntidadPagadora.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
        
    }

}
