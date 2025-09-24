package com.asopagos.solicitud.composite.factory;

import com.asopagos.cartera.clients.ActualizarEstadoSolicitudGestionCobroManual;
import com.asopagos.cartera.clients.ConsultarSolicitudGestionCobroManual;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudGestionCobroManualModeloDTO;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class SolicitudGestionCobroManual extends Solicitud{
    
    private ILogger logger = LogManager.getLogger(SolicitudGestionCobroManual.class);
    
    /** (non-Javadoc)
     * @see com.asopagos.solicitud.composite.factory.Solicitud#actualizarEstadoSolicitud(com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO)
     */
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudGestionCobroManual consultarSolicitudGestionCobroManual = new ConsultarSolicitudGestionCobroManual(null,inDTO.getNumeroRadicado());
        consultarSolicitudGestionCobroManual.execute();
        SolicitudGestionCobroManualModeloDTO solicitudGestionCobroManualModeloDTO = consultarSolicitudGestionCobroManual.getResult();

        if (solicitudGestionCobroManualModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de gestion Fiscalizacion no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        EstadoFiscalizacionEnum estadoSolicitud = EstadoFiscalizacionEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudGestionCobroManual actualizarEstadoSolicitud = new ActualizarEstadoSolicitudGestionCobroManual(
                inDTO.getNumeroRadicado(),estadoSolicitud);
        actualizarEstadoSolicitud.execute();

        actualizarEstadoSolicitud = new ActualizarEstadoSolicitudGestionCobroManual(inDTO.getNumeroRadicado(),
                EstadoFiscalizacionEnum.CERRADA);
        actualizarEstadoSolicitud.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudGestionCobroManualModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute(); 
    }


}
