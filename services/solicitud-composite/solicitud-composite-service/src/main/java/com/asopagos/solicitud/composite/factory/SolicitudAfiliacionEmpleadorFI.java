package com.asopagos.solicitud.composite.factory;

import com.asopagos.afiliaciones.empleadores.clients.ActualizarEstadoSolicitudAfiliacion;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitudAfiliacionEmpleadorPorRadicado;
import com.asopagos.afiliaciones.empleadores.dto.SolicitudAfiliacionEmpleadorDTO;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;
import com.asopagos.solicitud.composite.ejb.SolicitudCompositeBusiness;
import com.asopagos.tareashumanas.clients.AbortarProceso;

/**
 * <b>Descripcion:</b> Clase que contiene la implementación de la fabrica para
 * las solicitudes de afiliación de empleadores<br/>
 * <b>Módulo:</b> Asopagos - HU - TRA<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class SolicitudAfiliacionEmpleadorFI extends Solicitud {

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
			ConsultarSolicitudAfiliacionEmpleadorPorRadicado consultar = new ConsultarSolicitudAfiliacionEmpleadorPorRadicado(
					inDTO.getNumeroRadicado());
			consultar.execute();
			SolicitudAfiliacionEmpleadorDTO sol = consultar.getResult();
			if (sol != null) {
				EstadoSolicitudAfiliacionEmpleadorEnum estadoSol = EstadoSolicitudAfiliacionEmpleadorEnum
						.valueOf(inDTO.getEstado());
				ActualizarEstadoSolicitudAfiliacion actualizar = new ActualizarEstadoSolicitudAfiliacion(
						sol.getIdSolicitudAfiliacionEmpleador(), estadoSol);
				actualizar.execute();
				// Se actuliza el estado de la solicitud a cerrada
				actualizar = new ActualizarEstadoSolicitudAfiliacion(sol.getIdSolicitudAfiliacionEmpleador(),
						EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
				actualizar.execute();

				AbortarProceso abortarProceso = new AbortarProceso(sol.getTipoTransaccion().getProceso(),
						new Long(sol.getIdInstanciaProceso()));
				abortarProceso.execute();
			} else {
				logger.error("El valor del número de radicado de la solicitud de afiliación de empleadores no existe");
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
			}
		} catch (IllegalArgumentException iae) {
			logger.error("El valor del estado no es valido para la solicitud de afiliación de empleadores");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST, iae);
		}
		logger.debug("Finaliza actualizarEstadoSolicitud(Long, String)");
	}
}
