package com.asopagos.solicitud.composite.factory;

import com.asopagos.cartera.clients.ActualizarEstadoSolicitudGestionCobroElectronico;
import com.asopagos.cartera.clients.ConsultarSolicitudGestionCobroElectronico;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudGestionCobroElectronicoModeloDTO;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum;
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

public class SolicitudGestionCobroElectronico extends Solicitud {
    
    private ILogger logger = LogManager.getLogger(SolicitudGestionCobroElectronico.class);
    
    /** (non-Javadoc)
     * @see com.asopagos.solicitud.composite.factory.Solicitud#actualizarEstadoSolicitud(com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO)
     */
    @Override
    public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
        ConsultarSolicitudGestionCobroElectronico consultarSolicitudGestionCobroElectronico = new ConsultarSolicitudGestionCobroElectronico(inDTO.getNumeroRadicado());
        consultarSolicitudGestionCobroElectronico.execute();
        SolicitudGestionCobroElectronicoModeloDTO solicitudGestionCobroElectronicoModeloDTO = consultarSolicitudGestionCobroElectronico.getResult();

        if (solicitudGestionCobroElectronicoModeloDTO == null) {
            logger.error("El valor del número de radicado de la solicitud de gestion Fiscalizacion no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }

        EstadoSolicitudGestionCobroEnum estadoSolicitud = EstadoSolicitudGestionCobroEnum.valueOf(inDTO.getEstado());
        ActualizarEstadoSolicitudGestionCobroElectronico actualizarEstadoSolicitud = new ActualizarEstadoSolicitudGestionCobroElectronico(
                inDTO.getNumeroRadicado(), estadoSolicitud);
        actualizarEstadoSolicitud.execute();

        actualizarEstadoSolicitud = new ActualizarEstadoSolicitudGestionCobroElectronico(inDTO.getNumeroRadicado(),
                EstadoSolicitudGestionCobroEnum.CERRADA);
        actualizarEstadoSolicitud.execute();

        AbortarProceso abortarProceso = new AbortarProceso(solicitudGestionCobroElectronicoModeloDTO.getTipoTransaccion().getProceso(),
                inDTO.getIdInstanciaProceso());
        abortarProceso.execute();
    }


}
