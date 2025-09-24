package com.asopagos.afiliaciones.personas.web.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.servlet.http.HttpServletResponse;
import com.asopagos.dto.FiltroConsultaSolicitudesEnProcesoDTO;


/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para afiliación de
 * personas <b>Historia de Usuario:</b> HU-TRA-104
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("afiliacionesPersonasWeb")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionPersonasWebService {

	@POST
	@Path("/buscarSolicitudesAfiliacionPersonaPorEmpleador")
	public List<SolicitudAfiliacionPersonaDTO> buscarSolicitudesAfiliacionPersonaPorEmpleador(FiltroConsultaSolicitudesEnProcesoDTO filtro);
	/**
	 * Servicio encargado de consultar la solicitudes de afiliacion persona por
	 * empleador
	 * 
	 * @param idEmpleador,
	 *            id del empleador a buscar
	 * @param canalRecepcionEnum,
	 *            canal de recepcion a consultar
	 * @param estadoSolicitud,
	 *            estado de la solicitud a consultar
	 * @param tipoIdentificacion,
	 *          tip de documento de trabajador
	 * @param numeroIdentificacion,
	 *         numero de docuemento de trabajador 
	 * @param numeroRadicado,
	 * 			numero de radicado de trabajador 
	 * 		
	 * @return retorna la lista de dto solicitudes afiliacion persona dto
	 */		

	
	@GET
	@Path("/buscarSolicitudesAfiliacionPersonaPorEmpleadorConsultar/{idEmpleador}")
	public List<SolicitudAfiliacionPersonaDTO> buscarSolicitudesAfiliacionPersonaPorEmpleadorConsultar(
			@NotNull @PathParam("idEmpleador") Long idEmpleador,
			@QueryParam("canalRecepcionEnum") CanalRecepcionEnum canalRecepcionEnum,
			@QueryParam("estadoSolicitud") EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("numeroRadicado") String numeroRadicado);	

	/**
	 * Servicio encargado de consultar los resultados de validacion para
	 * trabajadores dependientes por empleador y estado
	 * 
	 * @param idEmpleador,
	 *            id del empleador a consultar los trabajadores
	 * @param estado,
	 *            estado a consultar las solicitudes
	 * @return retorna un ResultadoValidacionArchivoDTO que contiene la lista de
	 *         candidatos en formato JSON
	 */
	@GET
	@Path("/consultarResultadoValidacion/{idEmpleador}")
	public ResultadoValidacionArchivoDTO consultarResultadoValidacionTrabajadoresDependientes(
			@PathParam("idEmpleador") Long idEmpleador, @QueryParam("estado") EstadoCargaMultipleEnum estado);
}
