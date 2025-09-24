package com.asopagos.legalizacionfovis.composite.service;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.asopagos.dto.fovis.RegistroExistenciaHabitabilidadDTO;
import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.fovis.VisitaDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.ProveedorModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.legalizacionfovis.composite.dto.AnalisisSolicitudLegalizacionDesembolsoDTO;
import com.asopagos.legalizacionfovis.composite.dto.AsignarSolicitudLegalizacionDTO;
import com.asopagos.legalizacionfovis.composite.dto.ResultadoAnalisisLegalizacionDesembolsoDTO;
import com.asopagos.legalizacionfovis.composite.dto.VerificacionGestionPNCLegalizacionDesembolsoDTO;
import com.asopagos.legalizacionfovis.dto.SolicitudPostulacionLegalizacionDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de Proceso de Legalizacion FOVIS <b>Historia de Usuario:</b>
 * Proceso 3.2.4
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Path("legalizacionFovisComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface LegalizacionFovisCompositeService {

	/**
	 * 
	 * Servicio que registra o edita un oferente
	 * <code>LegalizacionFovisComposite</code>
	 * 
	 * @param OferenteModeloDTO
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/registrarEditarOferente")
	OferenteModeloDTO registrarEditarOferente(@NotNull OferenteModeloDTO oferenteModeloDTO);
        
        
        /**
	 * 
	 * Servicio que registra o edita un proveedor
	 * <code>LegalizacionFovisComposite</code>
	 * 
	 * @param proveedorModeloDTO
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/registrarEditarProveedor")
	ProveedorModeloDTO registrarEditarProveedor(@NotNull ProveedorModeloDTO proveedorModeloDTO);

        

	/**
	 * Servicio que registra o edita un proyecto solucion de vivienda
	 * <code>ProyectoSolucionVivienda</code>
	 * 
	 * @param proyectoDTO
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/registrarEditarProyectoSolucionVivienda")
	ProyectoSolucionViviendaModeloDTO registrarEditarProyectoSolucionVivienda(
			@NotNull ProyectoSolucionViviendaModeloDTO proyectoDTO);

	/**
	 * Servicio que se encarga de realizar la autorización del desembolso
	 * <code>LegalizacionFovisComposite</code>
	 * 
	 * @param proyectoDTO
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/autorizarDesembolso")
	SolicitudLegalizacionDesembolsoDTO autorizarDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, @Context UserDTO userDTO);

	/**
	 * Servicio que se encarga de reintentar la transaccion desembolso
	 * <code>LegalizacionFovisComposite</code>
	 * 
	 * @param SolicitudLegalizacionDesembolsoDTO
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/reintentarTransaccionDesembolso")
	SolicitudLegalizacionDesembolsoModeloDTO reintentarTransaccionDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, @Context UserDTO userDTO);

	/**
	 * Servicio que consulta las solicitudes de postulación asociadas al jefe
	 * del hogar, por en número de radicado de la solicitud y/ o los datos del
	 * jefe del hogar, las cuales son posibles candidatas a iniciar un proceso
	 * de legalización y desembolso.
	 * 
	 * @param numeroRadicadoSolicitud
	 *            Número de radicado de la solicitud.
	 * @param tipoIdentificacion
	 *            Tipo de identificación de la persona.
	 * @param numeroIdentificacion
	 *            Número de identificación de la persona.
	 * @return Lista de solicitudes de postulación encontradas.
	 */
	@GET
	@Path("/consultarPostulacionesParaLegalizacionDesembolso")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SolicitudPostulacionLegalizacionDTO> consultarPostulacionesParaLegalizacionDesembolso(
			@QueryParam("numeroRadicadoSolicitud") String numeroRadicadoSolicitud,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Método que se encarga de guardar los datos de la solicitud de
	 * legalización y desembolso temporalmente.
	 * 
	 * @param numeroIdentificacion
	 *            Número de identificación de la persona
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación de la persona
	 */
	@POST
	@Path("/guardarLegalizacionDesembolsoTemporal")
	public SolicitudLegalizacionDesembolsoDTO guardarLegalizacionDesembolsoTemporal(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, @Context UserDTO userDTO);

	/**
	 * Servicio que consulta los datos temporales de una postulación FOVIS
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global
	 * @return Objeto <code>SolicitudLegalizacionDesembolsoFOVISDTO</code> con
	 *         la información de la solicitud
	 */
	@GET
	@Path("/consultarLegalizacionDesembolsoTemporal/{idSolicitudGlobal}")
	public SolicitudLegalizacionDesembolsoDTO consultarLegalizacionDesembolsoTemporal(
			@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

	/**
	 * <b>Descripción:</b> Método encargado de registrar un intento de
	 * legalizacuión y desembolso.
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 *            Datos de la solicitud para registrar en el intento.
	 * @param userDTO
	 *            Usuario del contexto de seguridad.
	 * @return El identificador del intento de legalización y desembolso.
	 */
	@POST
	@Path("/registrarIntentoLegalizacionDesembolso")
	public Long registrarIntentoLegalizacionDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, @Context UserDTO userDTO);

	/**
	 * Servicio que radica una postulación de subsidio FOVIS
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 *            Datos actuales de la solicitud de postulación
	 * @param terminarTarea
	 *            Indica si el servicio debe finalizar la tarea del BPM.
	 *            <code>null</code> o <code>true</code> tienen el mismo efecto,
	 *            e implica que la tarea será terminada.
	 * @param userDTO
	 *            Usuario tomado del contexto
	 * @return Lo solicitud de postulación actualizada
	 */
	@POST
	@Path("/radicarLegalizacionDesembolso")
	public SolicitudLegalizacionDesembolsoDTO radicarLegalizacionDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO,
			@QueryParam("terminarTarea") Boolean terminarTarea, @Context UserDTO userDTO);

	/**
	 * Servicio que registra el escalamiento de la solicitud de legalización y
	 * desembolso a los distintos analistas según aplique.
	 * 
	 * @param solicitudLegalizacionDesembolsoDTO
	 *            datos de la solicitud de legalización
	 * @param userDTO
	 *            usuario del contexto de seguridad.
	 * @return Solicitud legalizacion
	 */
	@POST
	@Path("/escalarSolicitudLegalizacionYDesembolso")
	public SolicitudLegalizacionDesembolsoDTO escalarSolicitudLegalizacionYDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO, @Context UserDTO userDTO);

	/**
	 * Método encargado de registrar un análisis a una solicitud de legalización
	 * y desembolso
	 * 
	 * @param analisisSolicitud
	 *            Son los datos del análisis de la solicitud
	 * @param userDTO
	 *            Es el usuario del contexto de seguridad
	 */
	@POST
	@Path("/analizarSolicitudLegalizacionDesembolso")
	public void analizarSolicitudLegalizacionDesembolso(
			@NotNull AnalisisSolicitudLegalizacionDesembolsoDTO analisisSolicitud, @Context UserDTO userDTO);

	/**
	 * Método encargado de registrar el resultado definitivo del análisis de la
	 * solicitud de legalizacion y desembolso.
	 * 
	 * @param resultadoAnalisis
	 *            Son los datos del resultado del análisis de la legalizacion y
	 *            desembolso.
	 * @param userDTO
	 *            Es el usuario del contexto de seguridad.
	 */
	@POST
	@Path("/finalizarAnalisisLegalizacionDesembolso")
	public void finalizarAnalisisLegalizacionDesembolso(
			@NotNull ResultadoAnalisisLegalizacionDesembolsoDTO resultadoAnalisis, @Context UserDTO userDTO);

	/**
	 * Servicio que realiza las verificaciones de la solicitud de legalización y
	 * desembolso.
	 * 
	 * @param datosVerificacion
	 *            Información para registrar.
	 * @param userDTO
	 *            Es el usuario del contexto de seguridad.
	 */
	@POST
	@Path("/verificarSolicitudLegalizacionDesembolso")
	public void verificarSolicitudLegalizacionDesembolso(
			@NotNull VerificacionGestionPNCLegalizacionDesembolsoDTO datosVerificacion, @Context UserDTO userDTO);

	/**
	 * Servicio registra la gestión de Producto No Conforme para la solicitud de
	 * Legalización y desembolso.
	 * 
	 * @param idSolicitud
	 *            Identificador global de la solicitud.
	 * @param userDTO
	 *            Es el usuario del contexto de seguridad.
	 */
	@POST
	@Path("/gestionarPNCLegalizacionDesembolso/{idSolicitudGlobal}")
	public void gestionarPNCLegalizacionDesembolso(@NotNull @PathParam("idSolicitudGlobal") Long idSolicitud,
			@Context UserDTO userDTO);

	/**
	 * Método encargado de realizar la finalización de la verificación de la
	 * gestión del producto no conforme subsanable para la solicitud de
	 * Legalización y desembolso.
	 * 
	 * @param datosVerificacion
	 *            Son los datos de entrada.
	 * @param userDTO
	 *            Es el usuario del contexto de seguridad.
	 */
	@POST
	@Path("/verificarGestionPNCLegalizacionDesembolso")
	public void verificarGestionPNCLegalizacionDesembolso(
			@NotNull VerificacionGestionPNCLegalizacionDesembolsoDTO datosVerificacion, @Context UserDTO userDTO);

	/**
	 * Servicio que consulta la informacion de una visita y las condiciones
	 * asociadas a la misma
	 * 
	 * @param idVisita
	 * @return
	 */
	@GET
	@Path("/consultarInformacionVisita")
	public VisitaDTO consultarInformacionVisita(@QueryParam("idVisita") @NotNull Long idVisita);

	/**
	 * Servicio que Asigna los diferentes usuarios FOVIS.
	 * 
	 * @param entrada
	 * @param userDTO
	 */
	@POST
	@Path("/asignarSolicitudLegalizacion")
	public void asignarSolicitudLegalizacion(@NotNull AsignarSolicitudLegalizacionDTO entrada,
			@Context UserDTO userDTO);

	/**
	 * Servicio que registra el concepto de existencia y habitabilidad HU050-324
	 * 
	 * @param entrada
	 * @param userDTO
	 */
	@POST
	@Path("/registrarConceptoExistenciaHabitabilidad")
	public RegistroExistenciaHabitabilidadDTO registrarConceptoExistenciaHabitabilidad(
			@NotNull RegistroExistenciaHabitabilidadDTO existenciaHabitabilidadDTO, @Context UserDTO userDTO);

	/**
	 * Servicio que se encarga de asignar una tarea a la bandeja de trabajo del
	 * usuario con rol Pagador FOVIS, mediante la HU-TRA-140.
	 * <code>LegalizacionFovisComposite</code>
	 * 
	 * @param solicitudLegalizacionDesembolso
	 *            Información del registro a procesar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/procesarTransaccionDesembolso")
	SolicitudLegalizacionDesembolsoDTO procesarTransaccionDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, @Context UserDTO userDTO);

	/**
	 * Servicio que se encarga de realizar las verificaciones finales de la
	 * solicitud <code>LegalizacionFovis</code>
	 * 
	 * @param proyectoDTO
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/noAutorizarDesembolso")
	SolicitudLegalizacionDesembolsoModeloDTO noAutorizarDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolso);

	/**
	 * Servicio que consulta consulta la informacion del oferente por tipo y
	 * numero de indenfiticacion o razon social
	 * 
	 * @param numeroRadicado
	 * @return OferenteModeloDTO DTO con la informacion del oferente
	 */
	@GET
	@Path("/consultarOferentePorTipoNumeroIdORazonSocial")
	public List<OferenteModeloDTO> consultarOferentePorTipoNumeroIdORazonSocial(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("razonSocialNombre") String razonSocialNombre);
        
        
        /**
	 * Servicio que consulta consulta la informacion del proveedor por tipo y
	 * numero de indenfiticacion o razon social
	 * 
	 * @param numeroRadicado
	 * @return ProveedorModeloDTO DTO con la informacion del proveedor
	 */
	@GET
	@Path("/consultarProveedorPorTipoNumeroIdORazonSocial")
	public List<ProveedorModeloDTO> consultarProveedorPorTipoNumeroIdORazonSocial(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("razonSocialNombre") String razonSocialNombre);

	/**
	 * Servicio que se encarga de registrar el resultado del desembolso FOVIS
	 * <code>LegalizacionFovisComposite</code>
	 * 
	 * @param solicitudLegalizacionDesembolso
	 *            Información del registro a procesar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/registrarResultadoEjecucionDesembolso")
	SolicitudLegalizacionDesembolsoDTO registrarResultadoEjecucionDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, @Context UserDTO userDTO);

	/**
	 * Servicio que se encarga de finalizar el tramite del desembolso FOVIS
	 * <code>LegalizacionFovisComposite</code>
	 * 
	 * @param solicitudLegalizacionDesembolso
	 *            Información del registro a procesar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/finalizarTramiteDesembolso")
	SolicitudLegalizacionDesembolsoDTO finalizarTramiteDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso, @Context UserDTO userDTO);

	/**
	 * Servicio que se encarga de rechazar la solicitud de legalizacion y
	 * desembolo si el desembolso no fue exitoso en el décimo intento
	 * <code>LegalizacionFovis</code>
	 * 
	 * @param solicitudLegalizacionDesembolso
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/rechazarSolicitudLegalizacionDesembolso")
	public void rechazarSolicitudLegalizacionDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso);
	
	/**
	 * Servicio que se encarga de cerrar la solicitud de legalizacion y
	 * desembolo si el desembolso supera el numero maximo de reintentos intento
	 * <code>LegalizacionFovis</code>
	 * 
	 * @param solicitudLegalizacionDesembolso
	 *            Información del registro a crear/actualizar
	 * @return El registro actualizado
	 */
	@POST
	@Path("/cerrarSolicitudLegalizacionDesembolso")
	public void cerrarSolicitudLegalizacionDesembolso(
			@NotNull SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso);

}
