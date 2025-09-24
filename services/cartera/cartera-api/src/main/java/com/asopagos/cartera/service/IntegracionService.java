package com.asopagos.cartera.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asopagos.cartera.dto.IntegracionCarteraDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response; 
import javax.ws.rs.core.Context;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción: </b> Interface que define los métodos de negocio relacionados
 * a los servicios de integración de cartera <br/>
 * <b>Historia de Usuario: </b> Portafolio de servicios - Integración cartera
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@Path("externalAPI/cartera")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface IntegracionService {

	/**
	 * Servicio que obtiene el estado en cartera de un aportante
	 * 
	 * @param tipoAportante
	 *            Tipo de aportante
	 * @param tipoIdentificacion
	 *            Tipo de identificación del aportante
	 * @param numeroIdentificacion
	 *            Número de identificación del aportante
	 * @return El registro con la información del estado de cartera del
	 *         aportante
	 */
	@GET
	@Path("/obtenerEstadoCarteraOld")
	IntegracionCarteraDTO obtenerEstadoCarteraOld(@QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante, @QueryParam("tipoID") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("identificacion") String numeroIdentificacion);

	/**
	 * Servicio que obtiene el detalle del estado en cartera de un aportante
	 * 
	 * @param tipoAportante
	 *            Tipo de aportante
	 * @param tipoIdentificacion
	 *            Tipo de identificación del aportante
	 * @param numeroIdentificacion
	 *            Número de identificación del aportante
	 * @return La lista de registros con la información del detalle del estado
	 *         de cartera del aportante
	 */
	@GET
	@Path("/obtenerEstadoCarteraDetalleOld")
	Response obtenerEstadoCarteraDetalleOld(@QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante, @QueryParam("tipoID") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("identificacion") String numeroIdentificacion, @Context HttpServletRequest requestContext,
	@Context UserDTO userDTO);

	/**
	 * Servicio que consulta el estado de cartera de un aportante y su convenio
	 * asociado
	 * 
	 * @param tipoAportante
	 *            Tipo de aportante
	 * @param tipoIdentificacion
	 *            Tipo de identificación del aportante
	 * @param numeroIdentificacion
	 *            Número de identificación del aportante
	 * @return El estado en cartera del aportante y el convenio asociado
	 */
	@GET
	@Path("/obtenerEstadoCarteraConvenioPagoOld")
	Response obtenerEstadoCarteraConvenioPagoOld(@QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante,
    @QueryParam("tipoID") TipoIdentificacionEnum tipoIdentificacion, 
	@QueryParam("identificacion") String numeroIdentificacion, 
	@Context HttpServletRequest requestContext,
	@Context UserDTO userDTO);

	/**
	 * Servicio que obtiene la información de un convenio de pago por número
	 * 
	 * @param numeroConvenio
	 *            Número del convenio de pago
	 * @return La información del convenio
	 */
	@GET
	@Path("/obtenerConvenioPagoOld")
	Response obtenerConvenioPagoOld(@QueryParam("numeroConvenioPago") Long numeroConvenio,
	@Context HttpServletRequest requestContext,
	@Context UserDTO userDTO
	);

	/**
	 * Servicio que obtiene el estado de cartera de un aportante o cotizante
	 * @param tipoIdentificacion Tipo de identificación (aplica para aportante o cotizante)
	 * @param numeroIdentificacionAportante Número de identificación del aportante
	 * @param numeroIdentificacionDependiente Número de identificación del cotizante
	 * @param periodoConsulta Periodo de consulta, formato YYYY-MM
	 * @return La lista de registros (por aportante) con cartera vigente
	 */
	@GET
	@Path("/obtenerEstadoCartera")
	List<IntegracionCarteraDTO> obtenerEstadoCartera(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante, @QueryParam("numeroIdentificacionDependiente") String numeroIdentificacionDependiente, @QueryParam("periodoConsulta") String periodoConsulta);

	/**
	 * Servicio que obtiene el estado de cartera de un aportante, por convenio de pago o identificación
	 * @param numeroConvenioPago Número del convenio de pago
	 * @param tipoIdentificacion Tipo de identificación del aportante
	 * @param numeroIdentificacion Número de identificación del aportante
	 * @return La lista de registros en cartera vigente relacionados al aportante
	 */
	@GET
	@Path("/obtenerEstadoCarteraConvenioPago")
	Response obtenerEstadoCarteraConvenioPago(@QueryParam("numeroConvenioPago") Long numeroConvenioPago,
    @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
    @QueryParam("numeroIdentificacion") String numeroIdentificacion,
	@Context HttpServletRequest requestContext,
	@Context UserDTO userDTO);
}
