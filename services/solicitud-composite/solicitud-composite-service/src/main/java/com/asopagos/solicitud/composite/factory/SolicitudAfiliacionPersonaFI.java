package com.asopagos.solicitud.composite.factory;

import com.asopagos.afiliaciones.personas.clients.ActualizarEstadoSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersona;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.solicitud.composite.ejb.SolicitudCompositeBusiness;
import com.asopagos.tareashumanas.clients.AbortarProceso;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class SolicitudAfiliacionPersonaFI extends Solicitud {

	/** Referencia al logger */
	private ILogger logger = LogManager.getLogger(SolicitudCompositeBusiness.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.solicitud.composite.factory.Solicitud#actualizarEstadoSolicitud(java.lang.Long,
	 *      java.lang.String)
	 */
	public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
		logger.debug("Inicia actualizarEstadoSolicitud(Long, String)");
		try {
			EstadoSolicitudAfiliacionPersonaEnum estadoSol = EstadoSolicitudAfiliacionPersonaEnum
					.valueOf(inDTO.getEstado());
			ActualizarEstadoSolicitudAfiliacionPersona actualizar = new ActualizarEstadoSolicitudAfiliacionPersona(
					inDTO.getIdSolicitudGlobal(), estadoSol);
			actualizar.execute();

			// Se actuliza el estado de la solicitud a cerrada
			actualizar = new ActualizarEstadoSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal(),
					EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
			actualizar.execute();

			// se actuliza la solicitud con las nuevas observaciones
			SolicitudDTO solicitud = new SolicitudDTO();
			solicitud.setObservacion("Administracion de solicitud: " + inDTO.getObservacion());
			ActualizarSolicitudAfiliacionPersona actulizarSolicitud = new ActualizarSolicitudAfiliacionPersona(
					inDTO.getIdSolicitudGlobal(), solicitud);
			actulizarSolicitud.execute();

			AbortarProceso abortarProceso = new AbortarProceso(inDTO.getTipoTx().getProceso(),
					new Long(inDTO.getIdInstanciaProceso()));
			abortarProceso.execute();
		} catch (IllegalArgumentException iae) {
			logger.error("El valor del estado no es valido para la solicitud de afiliación de personas");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST, iae);
		}
		logger.debug("Finaliza actualizarEstadoSolicitud(Long, String)");
	}
}
