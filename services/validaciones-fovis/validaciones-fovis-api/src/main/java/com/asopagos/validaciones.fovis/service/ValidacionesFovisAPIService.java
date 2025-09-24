package com.asopagos.validaciones.fovis.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.ListaDatoValidacionDTO;
import com.asopagos.dto.ParametrizacionNovedadDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoNovedadEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.validaciones.fovis.dto.ConsultaRegistroCivilDTO;
import com.asopagos.validaciones.fovis.dto.ParametrosRegistroCivilDTO;

/**
 * <b>Descripción:</b> Clase que <br/>
 * define los servicios relacionados con la gestión de validaciones de negocio
 * <b>Módulo:</b> Asopagos - HU-TRA<br/>
 * 
 *
 * @author Jorge Leonardo Camargo Cuervo
 *         <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 *         </a>
 */
@Path("validacionesFovisAPI")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ValidacionesFovisAPIService {
	/**
	 * Método que ejecuta validaciones para el proceso de Afiliación de empresas
	 * WEB
	 * 
	 * @param proceso
	 *            Es el proceso
	 * @param idValidacion
	 *            Es el identificador de la validación
	 * @param datosValidacion
	 *            Es el mapa con los datos necesarios para la validación
	 * @return Listado de resultados de las validaciones ejecutadas
	 */
	@POST
	@Path("empleadores/validar")
	public List<ValidacionDTO> validarEmpleadores(@QueryParam("proceso") ProcesoEnum proceso,
			@QueryParam("bloque") String bloque, Map<String, String> datosValidacion);

	/**
	 * Método que ejecuta validaciones de negocio para el proceso de afiliación
	 * de personas
	 * 
	 * @param proceso
	 *            Es el proceso
	 * @param tipoAfiliado
	 *            Es el tipo de afiliado
	 * @param tipoBeneficiario
	 *            Es el tipo de benficiario
	 * @param idValidacion
	 *            Es el identificador del bloque de validación
	 * @param datosValidacion
	 *            Es el mapa con los datos necesarios para la validación
	 * @return Listado de resultados de las validaciones ejecutadas
	 */
	@POST
	@Path("personas/validar")
	public List<ValidacionDTO> validarPersonasFovis(@QueryParam("proceso") ProcesoEnum proceso,
													@QueryParam("objetoValidacion") String objetoValidacion, @QueryParam("bloque") String bloque,
													Map<String, String> datosValidacion) throws ExecutionException, ClassNotFoundException, InterruptedException, InstantiationException, IllegalAccessException;

	/**
	 * Método que ejecuta validaciones de negocio para los proceso de carga múltiple.
	 * 
	 * @param proceso
	 *            Es el proceso
	 * @param objetoValidacion
	 *            Es el objeto a validar
	 * @param bloque
	 *            Es el bloque
	 * @return Listado de resultados de las validaciones ejecutadas
	 */
//	@POST
//	@Path("/validarCargaMultipleAfiliaciones")
//	public ListaDatoValidacionDTO validarCargaMultipleAfiliaciones(@QueryParam("proceso")ProcesoEnum proceso, @QueryParam("objetoValidacion")String objetoValidacion,
//			@QueryParam("bloque")String bloque,List<AfiliarTrabajadorCandidatoDTO> candidatosDTO);
	
		/**
	 * Método que ejecuta validaciones de negocio para los proceso de carga múltiple.
	 * 
	 * @param proceso
	 *            Es el proceso
	 * @param objetoValidacion
	 *            Es el objeto a validar
	 * @param bloque
	 *            Es el bloque
	 * @return Listado de resultados de las validaciones ejecutadas
	 */
	@POST
	@Path("/validarCargaMultipleAfiliacionesStoredProcedure")
	public ListaDatoValidacionDTO validarCargaMultipleAfiliacionesStoredProcedure(@QueryParam("proceso")ProcesoEnum proceso, @QueryParam("objetoValidacion")String objetoValidacion,
			@QueryParam("bloque")String bloque,List<AfiliarTrabajadorCandidatoDTO> candidatosDTO);
	/**
	 * Método que ejecuta validaciones de negocio para los proceso de carga múltiple.
	 * 
	 * @param proceso
	 *            Es el proceso
	 * @param objetoValidacion
	 *            Es el objeto a validar
	 * @param bloque
	 *            Es el bloque
	 * @return Listado de resultados de las validaciones ejecutadas
	 */
	@POST
	@Path("/validarCargaMultipleNovedades")
	public ListaDatoValidacionDTO validarCargaMultipleNovedades(@QueryParam("proceso")ProcesoEnum proceso, @QueryParam("objetoValidacion")String objetoValidacion,
			@QueryParam("bloque")String bloque,List<TrabajadorCandidatoNovedadDTO> candidatosDTO);
	
	/**
	 * Método encargado de validar cuales son las novedades que se pueden habilitar.
	 * @param proceso proceso que se esta ejecutando
	 * @param datosValidacion valores necesarios para realizar las validaciones.
	 * @return lista de las novedades a habilitar.
	 */
	@POST
	@Path("novedades/habilitar")
	public List<ParametrizacionNovedadDTO> ValidadorNovedadesHabilitadasFovis(@QueryParam("proceso")ProcesoEnum proceso, @NotNull @QueryParam("objetoValidacion") List<String> objetoValidacion, @QueryParam("tipoNovedad")TipoNovedadEnum tipoNovedad, Map<String, String> datosValidacion);
	
	
	/**
	 * Método encargado de realizar validaciones a las reglas de negocio de un proceso en particular
	 * @param proceso proceso sobre el que se esta realizando las validaciones.
	 * @param objetoValidacion se refiere a la clasificación del empleador o persona.
	 * @param bloque agrupacion de  todas las validaciones que se ejecutaran.
	 * @param datosValidacion datos necesarios para validar.
	 * @return lista de las validaciones ejecutadas.
	 */
	@POST
	@Path("validarReglasNegocio")	
	public List<ValidacionDTO> validarReglasNegocio(@QueryParam("proceso") ProcesoEnum proceso,@QueryParam("objetoValidacion") String objetoValidacion, @QueryParam("bloque") String bloque,
			Map<String, String> datosValidacion) throws ExecutionException, ClassNotFoundException, InterruptedException, InstantiationException, IllegalAccessException;
	
	/**
	 * Método encargado de consultar las novedades de aportes por el tipo de afiliado
	 * @param tipoCotizante
	 * @return
	 */
    @GET
    @Path("/{tipoCotizante}/habilitarNovedadesAportes")
    public List<TipoTransaccionEnum> habilitarNovedadesAportes(
            @PathParam("tipoCotizante") TipoAfiliadoEnum tipoCotizante);
    

    /**
     * Verifica si el afiliado tiene asociadas solicitudes de afiliacion o novedad (sin terminar[CERRADA-RECHAZADA])
     * en el momento de la consulta.
     * 
     * @param inDTO
     */
//    @GET
//    @Path("/verificarSolicitudesEnCurso")
//    public ValidacionDTO verificarSolicitudesEnCurso(@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
//            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("idSolicitud") Long idSolicitud,
//            @QueryParam("esNovedad") Boolean esNovedad,@Context UserDTO userDTO, @QueryParam("tipoNovedad") TipoTransaccionEnum tipoNovedad);

	@POST
	@Path("/consultarDatosRegistraduriaNacional")
	public ConsultaRegistroCivilDTO consultarDatosRegistraduriaNacional(ParametrosRegistroCivilDTO parametro, @Context UserDTO userDTO) throws Exception;

	@GET
    @Path("/existeRegistraduriaNacional")
    public Boolean existeRegistraduriaNacional(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	@GET
	@Path("/validarConyugeCuidador")
	public Map<String,String> validarConyugeCuidador(@QueryParam("idAfiliado") Long idAfiliado, @QueryParam("tipoIdentificacionBen") TipoIdentificacionEnum tipoIdentificacion,@QueryParam("numeroIdentificacionBen") String numeroIdentificacion);
	
}
