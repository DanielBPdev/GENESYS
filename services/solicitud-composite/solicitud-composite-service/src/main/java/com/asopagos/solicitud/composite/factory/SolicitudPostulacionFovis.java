package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudPostulacion;
import com.asopagos.fovis.clients.ConsultarSolicitudPostulacion;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

public class SolicitudPostulacionFovis extends Solicitud {

    private ILogger logger = LogManager.getLogger(SolicitudPostulacionFovis.class);
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudPostulacion consultarSolicitudPostulacion = new ConsultarSolicitudPostulacion(inDTO.getIdSolicitudGlobal());
        consultarSolicitudPostulacion.execute();
        SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO = consultarSolicitudPostulacion.getResult();
        
        if(solicitudPostulacionModeloDTO == null){
            logger.error("El valor del número de radicado de la solicitud de postulación FOVIS no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        
        EstadoSolicitudPostulacionEnum estadoSolicitud = EstadoSolicitudPostulacionEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudPostulacion actualizarEstado = new ActualizarEstadoSolicitudPostulacion(inDTO.getIdSolicitudGlobal(), estadoSolicitud);
        actualizarEstado.execute();
        
        actualizarEstado = new ActualizarEstadoSolicitudPostulacion(inDTO.getIdSolicitudGlobal(), EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
        actualizarEstado.execute();
        
        AbortarProceso abortarProceso = new AbortarProceso(solicitudPostulacionModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
        
    }

}
