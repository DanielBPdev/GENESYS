package com.asopagos.solicitud.composite.factory;

import com.asopagos.cartera.clients.ActualizarEstadoSolicitudFiscalizacion;
import com.asopagos.cartera.clients.ConsultarSolicitudFiscalizacion;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudFiscalizacionModeloDTO;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudFiscalizacion extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudFiscalizacion.class);
    
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        
        ConsultarSolicitudFiscalizacion consultarSolicitudFiscalizacion = new ConsultarSolicitudFiscalizacion(inDTO.getNumeroRadicado());
        consultarSolicitudFiscalizacion.execute();
        SolicitudFiscalizacionModeloDTO solicitudFiscalizacionModeloDTO = consultarSolicitudFiscalizacion.getResult();
        
        if(solicitudFiscalizacionModeloDTO == null){
            logger.error("El valor del número de radicado de la solicitud de gestion Fiscalizacion no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoFiscalizacionEnum estadoSolicitud = EstadoFiscalizacionEnum
                .valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudFiscalizacion actualizarEstadoSolicitud = new ActualizarEstadoSolicitudFiscalizacion(inDTO.getNumeroRadicado(), estadoSolicitud);
        actualizarEstadoSolicitud.execute();
        
        actualizarEstadoSolicitud = new ActualizarEstadoSolicitudFiscalizacion(inDTO.getNumeroRadicado(), EstadoFiscalizacionEnum.CERRADA);
        actualizarEstadoSolicitud.execute();
        
        AbortarProceso abortarProceso = new AbortarProceso(solicitudFiscalizacionModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
    }

}
