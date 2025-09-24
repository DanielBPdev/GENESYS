package com.asopagos.novedades.composite.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.aportes.NovedadAportesDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.composite.dto.AnalizarSolicitudNovedadDTO;
import com.asopagos.novedades.composite.dto.AsignarSolicitudNovedadDTO;
import com.asopagos.novedades.composite.dto.ConsultarAnalisisNovedadDTO;
import com.asopagos.novedades.composite.dto.CorregirInformacionNovedadDTO;
import com.asopagos.novedades.composite.dto.GestionarPNCNovedadDTO;
import com.asopagos.novedades.composite.dto.ProcesarNovedadCargueArchivoDTO;
import com.asopagos.novedades.composite.dto.RegistrarRespuestaConfirmacionDTO;
import com.asopagos.novedades.composite.dto.VerificarGestionPNCNovedadDTO;
import com.asopagos.novedades.composite.dto.VerificarSolicitudNovedadDTO;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorDTO;
import com.asopagos.novedades.dto.DatosNovedadVista360DTO;
import com.asopagos.novedades.dto.IntentoNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.novedades.composite.dto.ResolverNovedadDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de novedades de una persoona o empleador <b>Historia de
 * Usuario:</b> Proceso 1.3
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@Path("novedadesComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesCompositeService {

    /**
     * Método encargado de radicar una solicitud de novedad, y enviarla al back
     * o cerrarla de acuerdo al punto de resolución de la novedad.
     * 
     * @param solNovedadDTO
     *        datos de la solicitud de novedad.
     * @param userDTO
     *        datos del usuario que radica la solicitud.
     * @return
     */
    @POST
    @Path("/radicarSolicitudNovedad")
    public SolicitudNovedadDTO radicarSolicitudNovedad(SolicitudNovedadDTO solNovedadDTO, @Context UserDTO userDTO);

    /**
     * Método encargado de registrar un intento de novedad.
     * 
     * @param intentoNovedadDTO
     *        intento de novedad.
     * @return id de la solicitud de novedad.
     */
    @POST
    @Path("/registrarIntentoNovedad")
    public Long registrarIntentoFallido(IntentoNovedadDTO intentoNovedadDTO, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la asignación de solicitudes de novedad
     * 
     * @param entrada
     *        Es el payload de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/asignarSolicitudNovedad")
    public void asignarSolicitudNovedad(AsignarSolicitudNovedadDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización de la verificación de la
     * solicitud de novedad por parte del back
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/verificarSolicitudNovedad")
    public void verificarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización de la gestión del producto
     * no conforme subsanable
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/gestionarPNCNovedad")
    public void gestionarPNCNovedad(GestionarPNCNovedadDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización de la verificación de la
     * gestión del producto no conforme subsanable
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/verificarGestionPNCNovedad")
    public void verificarGestionPNCNovedad(VerificarGestionPNCNovedadDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización del análisis de la solicitud
     * de novedad
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/analizarSolicitudNovedad")
    public void analizarSolicitudNovedad(AnalizarSolicitudNovedadDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización de la consulta del análisis
     * de la solicitud de novedad
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/consultarAnalisisNovedad")
    public void consultarAnalisisNovedad(ConsultarAnalisisNovedadDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización del registro de respuesta de
     * la confirmación de la novedad de desafiliación
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/registrarRespuestaConfirmacion")
    public void registrarRespuestaConfirmacion(RegistrarRespuestaConfirmacionDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización de la corrección de la
     * información
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/corregirInformacionNovedad")
    public void corregirInformacionNovedad(CorregirInformacionNovedadDTO entrada, @Context UserDTO userDTO);

    /**
     * Método encargado de consultar los datos temporales de una solicitud con
     * punto de resolución en el back.
     * 
     * @param idsolicitud
     *        id de la solicitud global de la novedad.
     * @return solicitudNovedadDTO dto con los datos de la solicitud de novedad.
     */
    @GET
    @Path("/consultarSolicitudNovedad")
    public SolicitudNovedadDTO consultarSolicitudNovedadTemporal(@QueryParam("idSolicitud") Long idsolicitud);

    /**
     * Método encargado de radicar una solicitud de novedad masiva.
     * 
     * @param solNovedadDTO
     *        datos de la solicitud de novedad.
     * @param userDTO
     *        datos del usuario que radica la solicitud.
     * @return
     */
    @POST
    @Path("/radicarSolicitudNovedadMasiva")
    public SolicitudNovedadDTO radicarSolicitudNovedadMasiva(SolicitudNovedadDTO solNovedadDTO, @Context UserDTO userDTO);

    /**
     * Servicio encargo de validar la estructura del cargue multiple
     * 
     * @param idEmpleador,
     *        id del empleador
     * @param cargue,
     *        cargue multiple a realizar la validacion
     * @param userDTO
     * @return ResultadoValidacionArchivoDTO que contiene la información valida
     */
    @POST
    @Path("/verificarEstructuraArchivoMultiple/{idEmpleador}")
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoMultiple(@NotNull @PathParam("idEmpleador") Long idEmpleador,
            @NotNull CargueMultipleDTO cargue, @Context UserDTO userDTO);

    /**
     * Método encargado de guardar la informacion
     * 
     * @param idEmpleador
     * @param numeroDiaTemporizador
     * @param nombreArchivo
     * @param codigoCargue
     * @param lstSolicitudNovedadDTO
     * @param userDTO
     * @return
     */
    @POST
    @Path("/guardarDatosNovedadArchivoMultiple/{idEmpleador}")
    public List<TrabajadorCandidatoNovedadDTO> guardarDatosNovedadArchivoMultiple(@PathParam("idEmpleador") Long idEmpleador,
            @QueryParam("numeroDiaTemporizador") Long numeroDiaTemporizador, @QueryParam("nombreArchivo") String nombreArchivo,
            @QueryParam("codigoCargue") Long codigoCargue, List<TrabajadorCandidatoNovedadDTO> lstSolicitudNovedadDTO,
            @Context UserDTO userDTO);

    /**
     * Realiza la verificación de estructura y el procesamiento de los archivos de carga multiple de supervivencia
     * @param archivoSuperVivenciaDTO
     *        Información de archivos agregados en pantalla
     * @param userDTO
     *        Usuario que ejecuta el cargue
     */
    @POST
    @Path("/verificarEstructuraArchivoSupervivencia")
    public void verificarEstructuraArchivoSupervivencia(@NotNull ArchivoSupervivenciaDTO archivoSuperVivenciaDTO,
            @Context UserDTO userDTO);

    /**
     * Servicio encargado de radicar una solicitud de novedad para los aportes (PILA o manuales)
     * @param novedadAportesDTO
     *        datos de la novedad.
     * @param userDTO
     *        usuario que radica.
     */
    @POST
    @Path("/radicarSolicitudNovedadAportes")
    public void radicarSolicitudNovedadAportes(NovedadAportesDTO novedadAportesDTO, @Context UserDTO userDTO);

    /**
     * Valida la estructura y contenido del archivo de respuesta enviado
     * @param tipoArchivo
     *        Indica el tipo de archivo de respuesta
     * @param cargue
     *        Contiene la informacion del cargue realizado
     * @param userDTO
     *        Usuario que realiza el cargue y el procesamiento del archivo
     * @return Resultado de validacion del archivo enviado
     */
    @POST
    @Path("/validarArchivoRespuesta")
    public ResultadoValidacionArchivoDTO validarArchivoRespuesta(@NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
            @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);

    /**
     * Realiza el registro de solicitudes para la genstion de novedad por cada diferencia encontrada
     * @param codigoCargue
     *        Identificador del cargue
     * @param tipoArchivo
     *        Tipo archivo enviado
     * @param usuarioDestino
     *        Usuario a quien se asignan las tareas generadas
     * @param listActualizacionInfoNovedad
     *        Lista de informacion a actualizar
     * @param userDTO
     *        Usuario que realiza el proceso
     */
    @POST
    @Path("/remitirNovedades")
    public void remitirNovedades(@QueryParam("codigoCargue") Long codigoCargue,
            @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo, @QueryParam("usuarioDestino") String usuarioDestino,
            List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad, @Context UserDTO userDTO);

    /**
     * Valida la estructrua y ejecuta la actualizacion de informacion de acuerdo a la informacion que contiene el archivo de certificados
     * escolares de beneficiarios
     * @param cargue
     *        Informacion del cargue
     * @param userDTO
     *        Usuario que realiza el procedimiento
     */
    @POST
    @Path("/validarArchivoCertificadoEscolar")
    public void validarArchivoCertificadoEscolar(@NotNull CargueArchivoActualizacionDTO cargue,
            @Context UserDTO userDTO);

    /**
     * Valida la estructura y ejecuta las actualizacion necesarias
     * @param cargue
     *        Informacion del cargue
     * @param userDTO
     *        Usuario que realiza el procedimiento
     */
    @POST
    @Path("/validarArchivoPensionado")
    public void validarArchivoPensionado(@NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);

    /**
     * Realiza el registro del resultado de aceptacion del archivo actualizacion
     * @param codigoCargue
     *        Identificador del cargue
     */
    @POST
    @Path("/aceptarResultadoArchivoActualizacion")
    public void aceptarResultadoArchivoActualizacion(@QueryParam("codigoCargue") Long codigoCargue);

    /**
     * Realiza la finalizacion de la solicitud novedad de acuerdo a la diferencia y al solicitud trata por el especialista
     * @param idSolicitud
     *        identificador de solicitud
     * @param idTarea
     *        Identificador tarea relacionada
     * @param observaciones
     *        Observaciones de finalizacion de tarea
     */
    @POST
    @Path("/finalizarNovedadArchivoActualizacion")
    public void finalizarNovedadArchivoActualizacion(@QueryParam("idSolicitud") Long idSolicitud, @QueryParam("idTarea") Long idTarea,
            String observaciones);

    /**
     * Consulta la informacion de una solicitud de novedead originada por una diferencia en cargue de actualizacion
     * @param idsolicitud
     *        Identificador solicitud
     * @return Informacion solicitud
     */
    @GET
    @Path("/consultarSolicitudNovedadDiferencia")
    public SolicitudNovedadDTO consultarSolicitudDiferencia(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio para el registro de las novedades futuras por medio de tarea programada
     * @param userDTO
     *        Usuario que radica
     */
    @POST
    @Path("/radicarSolicitudNovedadFutura")
    public void radicarSolicitudNovedadFutura(@Context UserDTO userDTO);

    /**
     * Verifica las personas asociadas a la novedad y registra si es necesario el analisis de la novedad por parte de fovis
     * @param listPersonasVerificar
     *        Lista de personas asociadas a la novedad
     * @param userDTO
     *        Usuario logueado en el sistema
     */
    @POST
    @Path("/verificarPersonaNovedadRegistrarAnalisisFovis")
    public void verificarPersonaNovedadRegistrarAnalisisFovis(@QueryParam("idSolicitudNovedad") Long idSolicitudNovedad,
            List<PersonaDTO> listPersonasVerificar, @Context UserDTO userDTO);

    /**
     * Servicio encargado de la consulta de las novedades futuras que se deben registrar por el cumplimiento de la novedad automatica
     * 
     * @return Lista de novedades futuras a registrar
     */
    @GET
    @Path("/consultarNovedadesEmpleador360")
    public List<DatosNovedadEmpleadorDTO> consultarNovedadesEmpleador360(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion, @Context UriInfo uri,
            @Context HttpServletResponse response);

    /**
     * Servicio para el registro de la solicitud de novedad de forma automatica interna sin la ejecucion de validaciones
     * @param solNovedadDTO
     *        Información de la solicitud novedad
     * @param userDTO
     *        Información usuario del contexto
     */
    @POST
    @Path("/radicarSolicitudNovedadAutomaticaSinValidaciones")
    public void radicarSolicitudNovedadAutomaticaSinValidaciones(SolicitudNovedadDTO solNovedadDTO, @Context UserDTO userDTO);

    /**
     * Servicio para el registro de la novedad de retiro cuando se registra el fallecimiento de la persona
     * @param solicitudNovedad
     *        Información solicitud novedad
     * @param userDTO
     *        Información usuario contexto
     */
    @POST
    @Path("/registrarRetiroAutomaticoPorFallecimiento")
    public void registrarRetiroAutomaticoPorFallecimiento(SolicitudNovedadDTO solicitudNovedad, @Context UserDTO userDTO);

    /**
     * Servicio para la consulta de los datos de la pestaña Novedades en la Vista 360 de personas
     * @param tipoIdentificacion Tipo de Identificación de la persona
     * @param numeroIdentificacion Número de Identificación de la persona
     * @param esBeneficiario Indica si la persona es beneficiario
     * @return Datos asociados a las Novedades de la Persona
     */
    @GET
    @Path("/obtenerNovedadesPersonaVista360")
	DatosNovedadVista360DTO obtenerNovedadesPersonaVista360(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("esBeneficiario") Boolean esBeneficiario);

    /**
     * Servicio para el registro de novedades en cascada basadas en el registro de información previo
     * @param datosNovedadConsecutiva
     *        Datos de novedad consecutiva
     * @param userDTO
     *        Usuario de contexto
     */
    @POST
    @Path("/radicarSolicitudNovedadCascada")
    public void radicarSolicitudNovedadCascada(DatosNovedadCascadaDTO datosNovedadConsecutiva, @Context UserDTO userDTO);
    
    /**
     * Valida que la tarea de verificación no haya sido ejecutada previamente desde
     * la pantalla
     * 
     * @param entrada
     *        Son los datos de entrada
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/validarSolicitudNovedad")
    public Map<String, Object> validarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, @Context UserDTO userDTO);
    
    /**
     * Inactiva los beneficiarios asociados a afiliados principales
     * inactivos (Producto de cascadas incompletas de retiros PILA)
     * 
     * @param userDTO
     *        es el usuario del contexto de seguridad
     */
    @POST
    @Path("/inactivarBeneficiariosAfiiadoInactivo")
    public String inactivarBeneficiariosAfiiadoInactivo(@Context UserDTO usuario);
    
    
    /**
     * Activa el beneficiario asociado a un afiliado objeto de un ingreso
     * por el canal PILA
     * @param novedadAportesDTO
     * @param userDTO
     */
    @POST
    @Path("/procesarActivacionBeneficiarioPILA")
    public void procesarActivacionBeneficiarioPILA(NovedadAportesDTO novedadAportesDTO,@Context UserDTO userDTO);

    /**
     * Radica la novedad de solicitud de archivo de actualizacion y cambia de estado la solicitud padre
     * 
     * @param novedadAportesDTO
     * @param userDTO
     */
    @POST
    @Path("/radicarSolicitudNovedadArchivoActualizacion")
	public SolicitudNovedadDTO radicarSolicitudNovedadArchivoActualizacion(SolicitudNovedadDTO solNovedadDTO, @Context UserDTO userDTO,
			@QueryParam("idSolicitud") Long idSolicitud);
    
    @POST
    @Path("/radicarSolicitudNovedadRetiroTrabajadorDependienteSinValidaciones")
    public Integer radicarSolicitudNovedadRetiroTrabajadorDependienteSinValidacionesUtil(@Context UserDTO userDTO);

    @POST
    @Path("/radicarNovedadRetiroPilaUtil")
	public void radicarNovedadRetiroPilaUtil(@Context UserDTO userDTO);

    /**
     * Método encargado de obtener las validaciones de una solicitud de novedad
     *
     * @param solicitudNovedadDTO
     *        datos de la solicitud de novedad.
     * @return
     */
    @POST
    @Path("/obtenerValidacionesSolicitudNovedad")
    public SolicitudNovedadDTO obtenerValidacionesSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO);

    /**
     * Permite la invocacion del metodo NovedadesCompositeBusiness.procesarNovedadCertificadoBeneficiario (Date,InformacionActualizacionNovedadDTO ,String, UserDTO)
       por medio de una peticion REST a traves del Cliente generado
     * @param procesarNovedadDTO
     * @param userDTO
     * @return
     */
    @POST
    @Path("/registrarNovedadCertificadoBeneficiario")
    public SolicitudNovedadDTO registrarNovedadCertificadoBeneficiario(ProcesarNovedadCargueArchivoDTO procesarNovedadDTO, @Context UserDTO userDTO);
    
    /**
     * Permite la invocacion del metodo NovedadesCompositeBusiness.verificarArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO)
       por medio de una peticion REST a traves del Cliente generado
     * @param archivoSuperVivenciaDTO
     * @param userDTO
     * @return
     * @throws Exception
     */
    @POST
    @Path("/validarArchivoSupervivencia")
    public ResultadoValidacionArchivoDTO validarArchivoSupervivencia(ArchivoSupervivenciaDTO archivoSuperVivenciaDTO, @Context UserDTO userDTO) throws Exception;
    
    /**
     * Permite la invocacion del metodo NovedadesCompositeBusiness.procesarSupervivenciaPersona(ResultadoSupervivenciaDTO, UserDTO, String)
       por medio de una peticion REST a traves del Cliente generado
     * @param resulDTO
     * @param userDTO
     * @param numeroRadicado
     * @throws Exception
     */
    @POST
    @Path("/registrarSupervivenciaPersona")
    public void registrarSupervivenciaPersona(ResultadoSupervivenciaDTO resulDTO, @Context UserDTO userDTO,@QueryParam("numeroRadicado") String numeroRadicado) throws Exception;
    
    /**
     * Permite la invocacion del metodo NovedadesCompositeBusiness.procesarNovedadInactivarBeneficiario(InformacionActualizacionNovedadDTO, UserDTO, String)
       por medio de una peticion REST a traves del Cliente generado
     * @param procesarNovedadDTO
     * @param userDTO
     * @return
     */
    @POST
    @Path("/registrarNovedadInactivarBeneficiario")
    public SolicitudNovedadDTO registrarNovedadInactivarBeneficiario(ProcesarNovedadCargueArchivoDTO procesarNovedadDTO,
            @Context UserDTO userDTO);

          /**
     * Permite la invocacion del metodo NovedadesCompositeBusiness.procesarNovedadInactivarBeneficiario(InformacionActualizacionNovedadDTO, UserDTO, String)
       por medio de una peticion REST a traves del Cliente generado
     * @param procesarNovedadDTO
     * @param userDTO
     * @return
     */
    @POST
    @Path("/gestionarNovedad")
    public String gestionarNovedad(Map<String,Object> datos,@Context UserDTO userDTO);

    @POST
    @Path("/insercionMonitoreoLogs")
    public void insercionMonitoreoLogs(@QueryParam("puntoEjecucion") String puntoEjecucion, @QueryParam("ubicacion")String ubicacion);

    /**
     * GLPI 82800 Gestion Crear Usuario Empleador Masivo
     *
     */
    @POST
    @Path("/validarArchivoEmpleador")
    public void validarArchivoEmpleador(@NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);

    /**
     * GLPI 82800 Gestion Crear Usuario Persona Masivo
     *
     */
    @POST
    @Path("/validarArchivoPersona")
    public void validarArchivoPersona(@NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);

    /**
     * GLPI 82800 Gestion Crear Usuario CCF Masivo
     *
     */
    @POST
    @Path("/validarArchivoCcf")
    public void validarArchivoCcf(@NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);

    @POST
    @Path("/resolverNovedades")
    public void resolverNovedades(ResolverNovedadDTO resolverNovedad, @Context UserDTO userDTO);

    /**
     * GLPI 96686 Novedad masiva cambio de tipo y número de identificación
     *
     */
    @POST
    @Path("/validarArchivoAfiliado")
    public void validarArchivoAfiliado(@NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);

    @POST
    @Path("/registrarNovedadCambioTipoNumeroDocumentoAfiliado")
    public SolicitudNovedadDTO registrarNovedadCambioTipoNumeroDocumentoAfiliado(ProcesarNovedadCargueArchivoDTO procesarNovedadDTO,
            @Context UserDTO userDTO);

    @POST
    @Path("/validarArchivoBeneficiario")
    public void validarArchivoBeneficiario(@NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);

    @POST
    @Path("/registrarNovedadCambioTipoNumeroDocumentoBeneficiario")
    public SolicitudNovedadDTO registrarNovedadCambioTipoNumeroDocumentoBeneficiario(ProcesarNovedadCargueArchivoDTO procesarNovedadDTO,
            @Context UserDTO userDTO);

    }
