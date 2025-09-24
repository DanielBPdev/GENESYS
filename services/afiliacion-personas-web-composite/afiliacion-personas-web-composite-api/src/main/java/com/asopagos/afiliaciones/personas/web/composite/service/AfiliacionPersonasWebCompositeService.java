package com.asopagos.afiliaciones.personas.web.composite.service;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.CancelacionSolicitudPersonasDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.ConsultaConceptoEscalamientoDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.IntentoAfiliacionesComunicadoDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.ReintegraAfiliadoDTO;
import com.asopagos.afiliaciones.personas.web.composite.dto.VerificarResultadoSolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.AfiliacionPersonaWebMasivaDTO;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AnalizarSolicitudAfiliacionDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.AfiliacionArchivoPlanoDTO;

/**
 * <b>Descripción:</b> Interfaz que define los servicios de composición del
 * proceso de afiliación de personas WEB
 *
 * @author Julian Andres Sanchez
 *         <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez@heinsohn.com.co
 *         </a>
 */
@Path("afiliacionesPersonasWeb")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionPersonasWebCompositeService {

	/**
	 * Servicio encargado de finalizar la afiliacion del trabajador candidato
	 * 
	 * @param idEmpleador,
	 *            empleador al cual pertenece el trabajador
	 * @param afiliarTrabajadorCandidatoDTO,
	 *            trabajador candidato a realizar la finalizacion de la
	 *            afiliacion
	 */
	@POST
	@Path("finalizarAfiliacionTrabajadorCandidato/{idEmpleador}")
	public void finalizarAfiliacionTrabajadorCandidato(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO, @Context UserDTO userDTO);

	/**
	 * Servicio encargo de validar la estructura del cargue multiple
	 * 
	 * @param idEmpleador,
	 *            id del empleador
	 * @param cargue,
	 *            cargue multiple a realizar la validacion
	 * @param userDTO
	 * @return ResultadoValidacionArchivoDTO que contiene la información valida
	 */
	@POST
	@Path("validarEstructuraContenidoArchivoMultiple/{idEmpleador}")
	public ResultadoValidacionArchivoDTO validarEstructuraContenidoArchivoMultiple(
			@NotNull @PathParam("idEmpleador") Long idEmpleador, @NotNull CargueMultipleDTO cargue,
			@Context UserDTO userDTO);

	

    /**
     * Servicio encargado de afiliar un trabajador dependiente Proceso: 122
     * HU-361
     * @param idEmpleador,
     *          empleador al cual pertenece el trabajador
     * @param asignarBack,
     *          bandera que indica si se debe asignar al back 
     * @param afiliarTrabajadorCandidatoDTO
     *          dto que contendra toda la informacion del afiliado a registrar
     * @param userDTO
     * @return
     */
	@POST
	@Path("afiliarTrabajador/{idEmpleador}")
	public AfiliadoInDTO afiliarTrabajador(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO, @Context UserDTO userDTO);

	@POST
	@Path("afiliarTrabajadorCandidato/{idEmpleador}")
	public AfiliadoInDTO afiliarTrabajadorCandidato(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO, @Context UserDTO userDTO);

	/**
	 * Servicio encargado de iniciar la verificacion de la solicitud por medio
	 * del dto
	 * 
	 * @param idEmpleador,
	 *            id del empleador a verificar
	 * @param afiliarTrabajadorCandidatoDTO
	 *            afiliado al cual se le realizara la verificacion de la
	 *            solicitud
	 */
	@POST
	@Path("iniciarVerificarInformacionSolicitud/{idEmpleador}")
	public Long iniciarVerificarInformacionSolicitud(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO,@QueryParam("idInstanciaProceso") Long idInstanciaProceso, @Context UserDTO userDTO);

	/**
	 * Servicio encargado de digitar la informacion contacto para el proceso
	 * 1.2.3. Afiliacion independiente web
	 * 
	 * @param inDTO,
	 *            Informacion de contacto a registrar
	 * @param UserDTO,
	 *            Usuario del contexto
	 * @return Mapa con los resultados de las validaciones de la HU-123-374 y
	 *         resultado de la solicitud
	 * 
	 * @author Luis Arturo Zarate Ayala <lzarate@heisohn.com.co>
	 */
	@POST
	@Path("digitarInformacionContacto")
	public Map<String, Object> digitarInformacionContacto(AfiliadoInDTO inDTO, @Context UserDTO UserDTO);

	/**
	 * Servicio encargado de verificar el resultado del producto no conforme,
	 * referente a la HU-122-373
	 * 
	 * @param idEmpleador,
	 *            identificador del empledor
	 * @param inDTO,
	 *            datos de entrada del servicio
	 * @author Luis Arturo Zarate Ayala <lzarate@heisohn.com.co>
	 */
	@POST
	@Path("/verificarResultadoProductoNoConforme/{idEmpleador}")
	public void verificarResultadoProductoNoConforme(@PathParam("idEmpleador") Long idEmpleador,
			VerificarSolicitudAfiliacionPersonaDTO inDTO);

	/**
	 * Servicio encargado de gestionar el resultado del producto no conforme,
	 * referente a la HU-122-370
	 * 
	 * @param idEmpleador,
	 *            identificador del empledor
	 * @author Luis Arturo Zarate Ayala <lzarate@heisohn.com.co>
	 */
	@POST
	@Path("/gestionarResultadoProductoNoConforme/{idEmpleador}")
	public void gestionarResultadoProductoNoConforme(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			GestionarProductoNoConformeSubsanableDTO inDTO, @Context UserDTO userDTO);

	/**
	 * Servicio encargado de verificar la informacion de la solicitud,
	 * HU-122-369
	 * 
	 * @param idEmpleador,
	 *            identificador del empleador
	 * @param inDTO,
	 *            verificacion solitud afiliacion dependiente
	 */
	@POST
	@Path("/verificarInformacionSolicutud/{idEmpleador}")
	public void verificarInformacionSolicitud(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			VerificarSolicitudAfiliacionPersonaDTO inDTO, @Context UserDTO userDTO);

	/**
	 * Método encargado de radocar la solicitud de afiliacion web, HU-123-376
	 * 
	 * @param idSolicitud,
	 *            identificador de la solicitud
	 * @param idTarea,
	 *            identificador de la tarea
	 */
	@POST
	@Path("/radicarSolicitud/{idSolicitud}")
	public Map<String, Object> radicarSolicitudAfiliacionWeb(@NotNull @PathParam("idSolicitud") Long idSolicitud,
			@NotNull @QueryParam("resultadoFormulario") Integer resultadoFormulario, @Context UserDTO userDTO);

	/**
	 * Metodo encargado de realizar la verificacion de la solicitud de
	 * afilicacion, HU-123-380
	 */
	@POST
	@Path("/verficarSolicitudAfiliacionIndependienteWeb")
	public void verificarSolicitudAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO,
			@Context UserDTO userDTO);

	/**
	 * Metodo encargado de analizar la solicitud de afiliacion independiente web
	 * HU-123-381
	 * 
	 * @param inDTO,
	 *            entrada de datos
	 * @param userDTO,
	 *            usuario del sistema
	 */
	@POST
	@Path("/analizarSolicitudAfiliacionIndependienteWeb")
	public void analizarSolicitudAfiliacionIndependienteWeb(AnalizarSolicitudAfiliacionDTO inDTO,
			@Context UserDTO userDTO);

	/**
	 * Metodo encargado de verificar los resultados de afialicion independiente
	 * web
	 * 
	 * HU-384 Verificar los resultados del producto no conforme
	 * 
	 * @param inDTO,
	 *            entrada de datos
	 * @param userDTO,
	 *            usuario del sistema
	 */
	@POST
	@Path("/verificarResultadosAfiliacionIndependienteWeb")
	public void verificarResultadosAfiliacionIndependienteWeb(VerificarResultadoSolicitudAfiliacionPersonaDTO inDTO,
			@Context UserDTO userDTO);

	/**
	 * Servicio encargado de crear registros temporales para una solicitud de
	 * trabajar candidato
	 * 
	 * @param afiliarTrabajadorCandidatoDTO,
	 *            DTO a realizar la creacion
	 * @return retorna el id de la solicitud global
	 */
	@POST
	@Path("/crearRegistroTemporalSolicitudTrabajadorCandidato")
	public Long crearRegistroTemporalSolicitudTrabajadorCandidato(
			AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO);

	/**
	 * Metodo encargado de reintegrar un independiente o pensionado por
	 * solicitud web, HU-123-379
	 */
	@POST
	@Path("/reintegrarIndependienteWeb")
	public Map<String, Object> reintegrarIndependienteWeb(ReintegraAfiliadoDTO reintegraAfiliadoDTO,
			@Context UserDTO userDTO);

	/**
	 * Servicio encargado de guardar los datos de afiliacion de candidatos que
	 * llegan por medio del cargue multiple
	 * 
	 * @param idEmpleador,
	 *            idEmpleador
	 * @param lstTrabajadorCandidatoDTO,
	 *            lista de candidatos a realizar la afiliacion
	 * @param numeroDiaTemporizador,
	 *            numero de dias en los que inicia el temporizador
	 * @param userDTO
	 */
	@POST
	@Path("/guardarDatosAfiliacionTrabajadorCandidato/{idEmpleador}")
	public void guardarDatosAfiliacionTrabajadorCandidato(@PathParam("idEmpleador") Long idEmpleador,
			@QueryParam("numeroDiaTemporizador") Long numeroDiaTemporizador,
			List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO,
			@QueryParam("nombreArchivo") String nombreArchivo, @Context UserDTO userDTO);

	/**
	 * Servicio encargado de validar productos no conforme del empleador
	 * 
	 * @param idEmpleador,empleador
	 *            al cual se le realizara la validacion
	 * @param idIstanciaProceso,
	 *            identificador de la instancia de proceso
	 */
	@POST
	@Path("/validarProduectoNoConformeEmpleador/{idEmpleador}")
	public void validarProductoNoConformeEmpleador(@PathParam("idEmpleador") Long idEmpleador, Long idIstanciaProceso);

	/**
	 * Servicio encargado de notificar que el futuro afiliado independiente o
	 * pensionado realizó las correciones a las solicitud HU-362 seguimiento a
	 * solicitudes de afiliación (Corregir información)
	 */
	@POST
	@Path("/seguimientoCorregirInformacion")
	public void corregirInformacionAfiliacion(@QueryParam("idInstanciaProceso") Long idInstanciaProceso,
			@QueryParam("resultadoGestion") String resultadoGestion);

	/**
	 * Servicio encargado de gestionar el resultado del producto no conforme,
	 * referente a la HU-123-383
	 * 
	 * @author Luis Arturo Zarate Ayala <lzarate@heisohn.com.co>
	 */
	@POST
	@Path("/gestionarResultadoProductoNoConformeIndepndiente")
	public void gestionarResultadoProductoNoConformeIndependiente(GestionarProductoNoConformeSubsanableDTO inDTO,
			@QueryParam("resultadoGestion") Integer resultadoGestion, @Context UserDTO userDTO);

	/**
	 * Metodo encargado de ejecutar la consulta del resultado del analisis
	 * especializado HU-382 Consultar concepto escalamiento - af indep
	 * pensionado web
	 * 
	 * @param resultadoAnalisis,
	 *            resultado Analisis especializado
	 * @param userDTO,
	 *            usuario del sistema
	 */
	@POST
	@Path("/consultaConceptoEscalamiento")
	public void consultaConceptoEscalamiento(ConsultaConceptoEscalamientoDTO inDTO, @Context UserDTO userDTO);

	/**
	 * Metodo que crea el registro de intento de afiliación y envía el
	 * comunicado para cada uno de los intentos recibidos
	 *
	 * @param intentoAfiliacionInDTO
	 * @param userDTO,
	 *            usuario autenticado
	 * @return El id del registro de intento de afiliación
	 */
	@POST
	@Path("intentosAfiliaciones")
	public List<IntentoAfiliacionInDTO> registrarIntentosAfliliaciones(
			@NotNull IntentoAfiliacionesComunicadoDTO intentoAfiliacionesInDTO, @Context UserDTO userDTO);
	
	
	/**
	 * Metodo que crea el registro de intento de afiliación y envía el
	 * comunicado para cada uno de los intentos recibidos
	 *
	 * @param intentoAfiliacionInDTO
	 * @param userDTO,
	 *            usuario autenticado
	 * @return El id del registro de intento de afiliación
	 */
	@POST
	@Path("reenviarCorreoEnrolamiento")
	public void reenviarCorreoEnrolamiento(
			AfiliadoInDTO inDTO, @Context UserDTO userDTO);
	
	/**
	 * Metodo que cancela y cierra la solicitud cuando el link enviado
	 * pierde su vigencia
	 *
	 * @param cancelacion
	 * @return N/A
	 */
	@POST
	@Path("cancelarSolicitudTimeout")
    public void cancelarSolicitudPersonasWebTimeout(CancelacionSolicitudPersonasDTO cancelacion);
	
	/**
     * Metodo encargado de registrar beneficiarios a un afiliado
     * 
     * @param idAfiliado,
     *        identificador del afiliado
     * @param beneficiarioDTO,
     *        beneficiario a agregar
     * @return identificador del beneficiario
     */
    @POST
    @Path("/{idAfiliado}/beneficiariosEnvioComunicado")
    public Long registrarBeneficiarioEnvio(@NotNull @PathParam("idAfiliado") Long idAfiliado, BeneficiarioDTO beneficiarioDTO);
    
    /**
     * Servicio temporal para la actualizacion de JSON asociados a las afiliaciones
     */
    @POST
    @Path("actualizarDatos")
    public String actualizarDatos(); 

	/**
     *
     * @param idEmpleador
     * @param afiliarTrabajadorCandidatoDTO
     * @param userDTO
     * @return
     */
    @POST
    @Path("afiliarTrabajadorCandidatoCopyMasivo")
    public AfiliadoInDTO afiliarTrabajadorCandidatoCopyMasivo(@NotNull @QueryParam("idEmpleador") Long idEmpleador,AfiliacionPersonaWebMasivaDTO afiliarTrabajadorCandidatoDTO);


	@POST 
	@Path("completarAfiliacionCargueMultiple")
	public AfiliarTrabajadorCandidatoDTO completarAfiliacionCargueMultiple(AfiliarTrabajadorCandidatoDTO afiliado); 

	
	@POST 
	@Path("completarAfiliacionCargueMultipleMasivo")
	public List<AfiliarTrabajadorCandidatoDTO> completarAfiliacionCargueMultipleMasivo(List<AfiliarTrabajadorCandidatoDTO> afiliados);
	
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
	@Path("digitarInformacionContactoWS")
	public Map<String, Object> digitarInformacionContactoWS(AfiliadoInDTO inDTO, @Context UserDTO UserDTO);
     
}
