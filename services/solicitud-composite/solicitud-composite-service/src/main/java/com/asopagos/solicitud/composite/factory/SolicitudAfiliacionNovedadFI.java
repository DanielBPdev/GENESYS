package com.asopagos.solicitud.composite.factory;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.clients.ActualizarEstadoSolicitudNovedad;
import com.asopagos.novedades.clients.ConsultarSolicitudNovedad;
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

public class SolicitudAfiliacionNovedadFI extends Solicitud {

	/** Referencia al logger */
	private ILogger logger = LogManager.getLogger(SolicitudCompositeBusiness.class);

	public void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO) {
		logger.debug("Inicia actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO)");
		try {
			ConsultarSolicitudNovedad consultar = new ConsultarSolicitudNovedad(inDTO.getIdSolicitudGlobal());
			consultar.execute();
			SolicitudNovedadModeloDTO sol = consultar.getResult();
			if (sol != null) {

				logger.debug("Inicia se actuliza el estado de la solicitud");
				EstadoSolicitudNovedadEnum estadoSol = EstadoSolicitudNovedadEnum.valueOf(inDTO.getEstado());
				ActualizarEstadoSolicitudNovedad actualizar = new ActualizarEstadoSolicitudNovedad(sol.getIdSolicitud(),
						estadoSol);
				actualizar.execute();

				// Se actuliza el estado de la solicitud a cerrada

				actualizar = new ActualizarEstadoSolicitudNovedad(sol.getIdSolicitud(),
						EstadoSolicitudNovedadEnum.CERRADA);
				actualizar.execute();

				logger.debug("Inicia se actuliza las observaciones de la solicitud");
				SolicitudNovedadModeloDTO solicitud = new SolicitudNovedadModeloDTO();
				solicitud.setObservacion("Administracion de solicitud: " + inDTO.getObservacion());
				
//				ActualizarSolicitudNovedad actualizarSolicitud = new ActualizarSolicitudNovedad(
//						inDTO.getIdSolicitudGlobal(), solicitud);
//				actualizarSolicitud.execute();

				ProcesoEnum proceso = sol.getTipoTransaccion().getProceso();
				if (CanalRecepcionEnum.ARCHIVO_ACTUALIZACION.equals(sol.getCanalRecepcion())) {
					proceso = ProcesoEnum.NOVEDADES_ARCHIVOS_ACTUALIZACION;
				}
				AbortarProceso abortarProceso = new AbortarProceso(proceso, new Long(sol.getIdInstanciaProceso()));
				abortarProceso.execute();
			} else {
				logger.error("El valor del id de la solicitud no corresponde a una solicitud");
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
			}
		} catch (IllegalArgumentException iae) {
			logger.error("El valor del estado no es valido para la solicitud de afiliación de Novedades");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST, iae);
		}
		logger.debug("Finaliza actualizarEstadoSolicitud(Long, String)");
	}
}
