package com.asopagos.afiliaciones.personas.web.service;

import com.asopagos.dto.AfiliacionArchivoPlanoDTO;
import com.asopagos.dto.AfiliacionPersonaWebMasivaDTO;
import com.asopagos.dto.AfiliadoInDTO;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.asopagos.rest.security.dto.UserDTO;
import java.util.List;
import java.util.Map;
import com.asopagos.entidades.ccf.afiliaciones.CargueMultiple;


/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para afiliación de
 * personas <b>Historia de Usuario:</b> HU-TRA-104
 * 
 * @author Juan Diego Ocampo Q <jocampo@heinsohn.com.co>
 */
@Path("afiliacionesPersonasWebMultiple")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionPersonasWebMultipleService {

	/**
	 * Método que recive el archivo cargado y verifica si cumple con la
	 * restricciones y validaciones descritas en la HU-364
	 * 
	 * @param idEmpleador
	 * @param archivoMultiple
	 * @param userDTO
	 * @return
	 */
	@POST
	@Path("validarEstructuraContenidoArchivo/{idEmpleador}/{idCargueMultiple}")
	public ResultadoValidacionArchivoDTO validarEstructuraContenidoArchivo(
			@NotNull @PathParam("idEmpleador") Long idEmpleador,
			@NotNull @PathParam("idCargueMultiple") Long idCargueMultiple,
			@NotNull InformacionArchivoDTO archivoMultiple, @Context UserDTO userDTO);

	/**
	 * Servicio encargado buscar de cargue del identificador multiple
	 * 
	 * @param idEmpleador,
	 *            id del empleador
	 * @return retorna el EstadoCargaMultiplePersonaEnum
	 */
	@GET
	@Path("/estadoIdentificadorCargueMultiple/{idEmpleador}")
	public EstadoCargaMultipleEnum estadoIdentificadorCargueMultiple(
			@NotNull @PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Servicio encargado de actualizar el estado de cargue multiple
	 * 
	 * @param idCargue,
	 *            id de carga a realizar la actualizacion
	 * @param empleadorCargue
	 *            boolean que se encarga de identificar si es un empleador con
	 *            true
	 * @param estadoCargueMultiple,
	 *            estado a realizar la actualizacion
	 */
	@PUT
	@Path("/actualizarEstadoCargueMultiple/{identificador}")
	public Long actualizarEstadoCargueMultiple(@NotNull @PathParam("identificador") Long identificador,
			@QueryParam("empleadorCargue") Boolean empleadorCargue,
			EstadoCargaMultipleEnum estadoCargueMultiple);


	@GET
	@Path("/consultarSucursalECM/{idEmpleador}")
	public CargueMultipleDTO consultarSucursalECM(
			@NotNull @PathParam("idEmpleador") Long idEmpleador);


	/**
	 * Servicio para registrar un cargue múltiple
	 * 
	 * @param idEmpleador
	 * @param cargueMultipleDTO
	 */
	@POST
	@Path("/registrarCargueMultiple/{idEmpleador}")
	public Long registrarCargueMultiple(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			CargueMultipleDTO cargueMultipleDTO, @Context UserDTO userDTO);

        
  
    /**
     *
     * @param idValidacion
     * @param valorCampo
     * @return
     */
    @GET
	@Path("/validacionesArchivoAfiliaciones")
	public RespuestaValidacionArchivoDTO validacionesArchivoAfiliaciones(@NotNull @QueryParam("idValidacion") String idValidacion, @NotNull @QueryParam("valorCampo") String valorCampo);
        
    /**
     *
     * @param nombrePais
     * @return
     */
    @GET
	@Path("/buscarCodigoPais")
	public RespuestaValidacionArchivoDTO buscarCodigoPais(@NotNull @QueryParam("nombrePais") String nombrePais);
       
    /**
     *
     * @param idEmpleador
     * @param candidatosAfiliacion
     * @param userDTO
     */
    @POST
    @Path("/solicitarAfiliacionMasiva")
    public void solicitarAfiliacionMasiva(@NotNull @QueryParam("idEmpleador") Long idEmpleador,AfiliacionArchivoPlanoDTO candidatosAfiliacion,@Context UserDTO userDTO);
      
    @POST
    @Path("/cancelarSolicitudes")
    public void cancelarSolicitudes(List<Long> solicitudesCandidatosACancelar);

    /**
     *
     * @param idEmpleador
     * @param afiliacionPersonaWeb
     * @return
     */
    @POST
	@Path("/procesarAfiliacionPersonasWebMasiva")
	public AfiliadoInDTO procesarAfiliacionPersonasWebMasiva(@NotNull @QueryParam("idEmpleador") Long idEmpleador,AfiliacionPersonaWebMasivaDTO afiliacionPersonaWeb);
    
    
    /**
     *
     * @param nombreOcupacion
     * @return
     */
    @GET
	@Path("/buscarIdOcupacion")
	public RespuestaValidacionArchivoDTO buscarIdOcupacion(@NotNull @QueryParam("nombreOcupacion") String nombreOcupacion);
      
}