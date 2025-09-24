package com.asopagos.fovis.composite.service;

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

import com.asopagos.dto.CargueArchivoCruceFovisDTO;
import com.asopagos.dto.CruceDetalleDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import com.asopagos.dto.fovis.OferenteDTO;
import com.asopagos.dto.fovis.ProveedorDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.TipoPostulacionFOVISEnum;
import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.CondicionHogarEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.composite.dto.AnalisisSolicitudPostulacionDTO;
import com.asopagos.fovis.composite.dto.AsignaResultadoCruceDTO;
import com.asopagos.fovis.composite.dto.AsignarSolicitudPostulacionDTO;
import com.asopagos.fovis.composite.dto.CancelacionSolicitudPostulacionDTO;
import com.asopagos.fovis.composite.dto.GestionPNCPostulacionDTO;
import com.asopagos.fovis.composite.dto.RegistrarVerificacionControlInternoDTO;
import com.asopagos.fovis.composite.dto.ResultadoAnalisisPostulacionDTO;
import com.asopagos.fovis.composite.dto.ValidacionOferenteDTO;
import com.asopagos.fovis.composite.dto.VariablesGestionFOVISDTO;
import com.asopagos.fovis.composite.dto.VerificacionCorreccionHallazgos;
import com.asopagos.fovis.composite.dto.VerificacionGestionControlInterno;
import com.asopagos.fovis.composite.dto.VerificacionGestionPNCPostulacionDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de del Proceso FOVIS <b>Historia de Usuario:</b> Proceso 3.2
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Path("fovisComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface FovisCompositeService {

    /**
     * Servicio que consulta todas las variables de la parametrización de FOVIS.
     * @return el objeto VariablesGestionFOVISDTO con la parametrización
     */
    @GET
    @Path("/consultarVariablesGestionFOVIS")
    @Produces(MediaType.APPLICATION_JSON)
    public VariablesGestionFOVISDTO consultarVariablesGestionFOVIS();

    /**
     * Servicio que registra las variables generales de la parametrización de FOVIS
     * @param variablesGestionFOVISDTO
     */
    @POST
    @Path("/registrarVariablesGestionFOVIS")
    public void registrarVariablesGestionFOVIS(@NotNull VariablesGestionFOVISDTO variablesGestionFOVISDTO);

    /**
     * Servicio que realiza la validación de una persona (natural o empleador),
     * para continuar con el registro como oferente -> 3.1.2.2.1.3
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación
     * @param numeroIdentificacion
     *        Número de identificación
     * @return Objeto <code>ValidacionOferenteDTO</code> con el resultado de la
     *         validación
     */
    @GET
    @Path("/validarOferente")
    public ValidacionOferenteDTO validarOferente(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que permite realizar la consulta de oferentes, de acuerdo a los
     * criterios de búsqueda seleccionados por el usuario
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación
     * @param numeroIdentificacion
     *        Número de identificación
     * @param digitoVerificacion
     *        Dígito de vertificación, caso
     *        <code>tipoIdentificación=NIT</code>
     * @param razonSocial
     *        Razón social, caso <code>tipoIdentificación=NIT</code>
     * @param primerNombre
     *        Primer nombre, caso <code>tipoIdentificación!=NIT</code>
     * @param segundoNombre
     *        Segundo nombre, caso <code>tipoIdentificación!=NIT</code>
     * @param primerApellido
     *        Primer apellido, caso <code>tipoIdentificación!=NIT</code>
     * @param segundoApellido
     *        Segundo apellido, caso <code>tipoIdentificación!=NIT</code>
     * @return La lista de oferentes encontrados
     */
    @GET
    @Path("/consultarListaOferentes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OferenteDTO> consultarListaOferentes(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("digitoIdentificacion") Short digitoVerificacion,
            @QueryParam("razonSocial") String razonSocial, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("segundoNombre") String segundoNombre, @QueryParam("primerApellido") String primerApellido,
            @QueryParam("segundoApellido") String segundoApellido);

     /**
     * Servicio que permite realizar la consulta de proveedores, de acuerdo a los
     * criterios de búsqueda seleccionados por el usuario
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación
     * @param numeroIdentificacion
     *        Número de identificación
     * @param digitoVerificacion
     *        Dígito de vertificación, caso
     *        <code>tipoIdentificación=NIT</code>
     * @param razonSocial
     *        Razón social, caso <code>tipoIdentificación=NIT</code>
     * @param primerNombre
     *        Primer nombre, caso <code>tipoIdentificación!=NIT</code>
     * @param segundoNombre
     *        Segundo nombre, caso <code>tipoIdentificación!=NIT</code>
     * @param primerApellido
     *        Primer apellido, caso <code>tipoIdentificación!=NIT</code>
     * @param segundoApellido
     *        Segundo apellido, caso <code>tipoIdentificación!=NIT</code>
     * @return La lista de proveedores encontrados
     */
    @GET
    @Path("/consultarListaProveedores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProveedorDTO> consultarListaProveedores(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("digitoIdentificacion") Short digitoVerificacion,
            @QueryParam("razonSocial") String razonSocial, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("segundoNombre") String segundoNombre, @QueryParam("primerApellido") String primerApellido,
            @QueryParam("segundoApellido") String segundoApellido);

    
    /**
     * HU-020
     * <b>Descripción:</b> Método que se encarga de manejar la información de la solicitud de postulación inicial
     * @param solicitudPostulacion
     *        Datos de la solicitud de postulación
     * @param tipoPostulacion
     *        Tipo de Postulación a realizar
     */
    @POST
    @Path("/radicarPostulacionInicial")
    public SolicitudPostulacionFOVISDTO radicarPostulacionInicial(@NotNull SolicitudPostulacionFOVISDTO solicitudPostulacion,
            @QueryParam("tipoPostulacion") TipoPostulacionFOVISEnum tipoPostulacion, @Context UserDTO userDTO);

    /**
     * Servicio que cancela una solicitud de postulación
     * @param idSolicitudGlobal
     *        Identificador de la solicitud global
     */
    @GET
    @Path("/cancelarPostulacion/{idSolicitudGlobal}")
    void cancelarPostulacion(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que cambia el estado de un oferente
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del oferente
     * @param numeroIdentificacion
     *        Número de identificación del oferente
     * @param nuevoEstado
     *        Nuevo estado del oferente
     * @return El nuevo estado del oferente, para validación
     */
    @GET
    @Path("/cambiarEstadoOferente")
    public EstadoOferenteEnum cambiarEstadoOferente(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("nuevoEstado") EstadoOferenteEnum nuevoEstado);

     /**
     * Servicio que cambia el estado de un proveedor
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del proveedor
     * @param numeroIdentificacion
     *        Número de identificación del proveedor
     * @param nuevoEstado
     *        Nuevo estado del proveedor
     * @return El nuevo estado del proveedor, para validación
     */
    @GET
    @Path("/cambiarEstadoProveedor")
    public EstadoOferenteEnum cambiarEstadoProveedor(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("nuevoEstado") EstadoOferenteEnum nuevoEstado);

    
    /**
     * Servicio que radica una postulación de subsidio FOVIS
     * 
     * @param solicitudPostulacionDTO
     *        Datos actuales de la solicitud de postulación
     * @param idSolicitudGlobal
     *        Identificador de la solicitud global, utilizado cuando no se tenga el "solicitudPostulacionDTO"
     * @param terminarTarea
     *        Indica si el servicio debe finalizar la tarea del BPM. <code>null</code> o <code>true</code> tienen el mismo efecto, e implica
     *        que la tarea será terminada.
     * @param userDTO
     *        Usuario tomado del contexto
     * @return Lo solicitud de postulación actualizada
     */
    @POST
    @Path("/radicarPostulacion")
    SolicitudPostulacionFOVISDTO radicarPostulacion(@NotNull SolicitudPostulacionFOVISDTO solicitudPostulacionDTO,
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal, @QueryParam("terminarTarea") Boolean terminarTarea,
            @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b> Método que se encarga de guardar la solicitud temporalmente.
     * @param numeroIdentificacion
     *        Número de identificación de la persona
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona
     */
    @POST
    @Path("/guardarPostulacionTemporal")
    public SolicitudPostulacionFOVISDTO guardarPostulacionTemporal(@NotNull SolicitudPostulacionFOVISDTO solicitudPostulacion,
            @Context UserDTO userDTO);

    /**
     * Servicio que consulta los datos temporales de una postulación FOVIS
     * 
     * @param idSolicitudGlobal
     *        Identificador de la solicitud global
     * @return Objeto <code>SolicitudPostulacionFOVISDTO</code> con la
     *         información de la solicitud
     */
    @GET
    @Path("/consultarPostulacionTemporal/{idSolicitudGlobal}")
    SolicitudPostulacionFOVISDTO consultarPostulacionTemporal(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Método encargado de registrar un análisis a una solicitud de postulación
     * 
     * @param analisisSolicitud
     *        Son los datos del análisis de la solicitud
     * @param userDTO
     *        Es el usuario del contexto de seguridad
     */
    @POST
    @Path("/analizarSolicitudPostulacion")
    public void analizarSolicitudPostulacion(@NotNull AnalisisSolicitudPostulacionDTO analisisSolicitud, @Context UserDTO userDTO);

    /**
     * Método encargado de registrar el resultado definitivo del análisis de la solicitud de postulación.
     * 
     * @param resultadoAnalisis
     *        Son los datos del resultado del análisis de la postulacion.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/finalizarAnalisisPostulacion")
    public void finalizarAnalisisPostulacion(@NotNull ResultadoAnalisisPostulacionDTO resultadoAnalisis, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b> Método encargado de registrar un intento de postulacion.
     * @param IntentoPostulacionDTO
     *        intento postulacion
     * @param userDTO
     * @return usuario del proceso
     */
    @POST
    @Path("/registrarIntentoPostulacion")
    public Long registrarIntentoPostulacion(@NotNull SolicitudPostulacionFOVISDTO solicitudPostulacionDTO, @Context UserDTO userDTO);

    /**
     * Método encargado de realizar la finalización de la verificación de la
     * gestión del producto no conforme subsanable para la solicitud de postulación.
     * 
     * @param datosVerificacion
     *        Son los datos de entrada.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/verificarGestionPNCPostulacion")
    public void verificarGestionPNCPostulacion(@NotNull VerificacionGestionPNCPostulacionDTO datosVerificacion, @Context UserDTO userDTO);

    /**
     * Método encargado de registrar en envío de las postulaciones para verificación a control interno.
     * 
     * @param solicitudesPostulacion
     *        Son los datos a registrar para control interno.
     * @param usuariosControlInterno
     *        Lista de usuarios de control interno para programarles la verificacion.
     */
    @POST
    @Path("/registrarVerificacionControlInterno")
    public void registrarVerificacionAControlInterno(@NotNull RegistrarVerificacionControlInternoDTO registroVerificacionDTO,
            @Context UserDTO userDTO);

    /**
     * Método encargado de registrar los resultados de la verificación por parte de control interno.
     * 
     * @param gestionControlInterno
     *        Son los datos a registrar de control interno.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/registrarResultadoControlInterno")
    public void registrarResultadoControlInterno(@NotNull VerificacionGestionControlInterno gestionControlInterno,
            @Context UserDTO userDTO);

    /**
     * Método encargado de guardar los resultados de la verificación del Back y enviarlos a control interno.
     * 
     * @param correccionHallazgos
     *        Son los datos a registrar.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/enviarCorreccionesAControlInterno")
    public void enviarCorreccionesAControlInterno(@NotNull VerificacionCorreccionHallazgos correccionHallazgos, @Context UserDTO userDTO);

    /**
     * Método encargado de verificar y guardar los resultados de corrección de hallazgos en la postulación.
     * 
     * @param correccionHallazgos
     *        Son los datos a registrar.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/verificarCorreccionesControlInterno")
    public void verificarCorreccionesControlInterno(@NotNull VerificacionGestionControlInterno correccionHallazgos,
            @Context UserDTO userDTO);

    /**
     * Servicio para registrar los datos del ciclo de asignación, incluidas las modalidades habilitadas para el ciclo.
     * @param cicloAsignacionModelDTO
     *        Ciclo de asignación con los datos a registrar.
     */
    @POST
    @Path("/registrarDatosCicloAsignacion")
    public void registrarDatosCicloAsignacion(@NotNull CicloAsignacionModeloDTO cicloAsignacionModelDTO);

    /**
     * Servicio para soportar HU-TRA-140 Asignar solicitud presencial
     * @param entrada
     * @param userDTO
     */
    @POST
    @Path("/asignarSolicitudPostulacion")
    public void asignarSolicitudPostulacion(@NotNull AsignarSolicitudPostulacionDTO entrada, @Context UserDTO userDTO);

    /**
     * 3.2.1-HU020
     * Servicio que consulta y valida las personas encontradas
     * para verificar si pueden postularse, retorna cada persona con el resultado de la validación.
     * @param personaDTO
     * @param userDTO
     * 
     * @return List<PersonaModeloDTO>
     */
    @POST
    @Path("/validarAfiliadosPostulacion")
    public List<PersonaModeloDTO> validarAfiliadosPostulacion(@NotNull PersonaModeloDTO personaDTO, @Context UserDTO userDTO);

    /**
     * Servicio que calcula el valor del Subsidio Familiar de Vivienda
     * 
     * @param modalidad
     *        Modalidad de adquisición de vivienda
     * @param tipoIdentificacionJefeHogar
     *        Tipo de identificación del jefe de hogar
     * @param numeroIdentificacionJefeHogar
     *        Número de identificación del jefe del hogar
     * @param beneficiarioViviendaMejoramientoSaludable
     *        Indica si el jefe del hogar o alguno de los integrantes ha
     *        sido beneficiario de vivienda de mejoramiento saludable
     * @param departamentoSolucionVivienda
     *        Nombre del departamento de ubicación de la solución de
     *        vivienda
     * @param valorSolucionVivienda
     *        Valor de la solución de vivienda
     * @param ingresosHogar
     *        Total de ingresos del hogar. Si este valor llega <code>null</code>, se calcula dentro del servicio
     *@param condicionHogar
     *        Condicion de hogar
     *@param condicionEspecial
     *        Condicion especial del hogar      
     * @return Valor del SFV calculado
     */
    @GET
    @Path("/calcularValorSFV")
    Double calcularValorSFV(@QueryParam("modalidad") ModalidadEnum modalidad,
            @QueryParam("tipoIdentificacionJefeHogar") TipoIdentificacionEnum tipoIdentificacionJefeHogar,            
            @QueryParam("numeroIdentificacionJefeHogar") String numeroIdentificacionJefeHogar,
            @QueryParam("beneficiarioViviendaMejoramientoSaludable") Boolean beneficiarioViviendaMejoramientoSaludable,
            @QueryParam("departamentoSolucionVivienda") String departamentoSolucionVivienda,
            @QueryParam("valorSolucionVivienda") Double valorSolucionVivienda,
            @QueryParam("ingresosHogar") Double ingresosHogar,            
            @QueryParam("idPostulacion") Long idPostulacion,
            @QueryParam("condicionHogar") CondicionHogarEnum condicionHogar,
            @QueryParam("condicionEspecial") NombreCondicionEspecialEnum condicionEspecial);
    		

    /**
     * Registra los datos de la programacion y parametrización de las novedades para FOVIS.
     * @param variables
     *        Contiene los datos de la programación y parametrizacion FOVIS a registrar.
     */
    @POST
    @Path("/registrarParametrizacionNovedades")
    public void registrarParametrizacionNovedades(@NotNull VariablesGestionFOVISDTO variables);

    /**
     * Servicio que realiza las verificaciones de la solicitud de postulación ya sea web, prescencial completa o abreviada
     * @param datosVerificacion
     *        Información para registrar.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/verificarSolicitudPostulacion")
    public void verificarSolicitudPostulacion(@NotNull VerificacionGestionPNCPostulacionDTO datosVerificacion, @Context UserDTO userDTO);

    /**
     * Servicio registra la gestión de Producto No Conforme para la solicitud de postulación.
     * @param datosVerificacion
     *        Información necesaria para registrar.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/gestionarPNCPostulacion")
    public void gestionarPNCPostulacion(@NotNull GestionPNCPostulacionDTO entrada, @Context UserDTO userDTO);

    /**
     * Servicio que realiza el escalamiento múltiple de una solicitud de postulación
     * @param solicitudPostulacionFOVISDTO
     *        Datos de la postulación
     * @param userDTO
     *        Información del usuario, tomada del contexto
     * @return Objeto <code>SolicitudPostulacionFOVISDTO</code> con la información actualizada de la solicitud de postulación
     */
    @POST
    @Path("/registrarEscalamientoMultiple")
    SolicitudPostulacionFOVISDTO registrarEscalamientoMultipleFront(@NotNull SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO,
            @Context UserDTO userDTO);

    /**
     * Servicio registra la gestión de Producto No Conforme para la solicitud de postulación Web.
     * @param datosVerificacion
     *        Información necesaria para registrar.
     * @param userDTO
     *        Es el usuario del contexto de seguridad.
     */
    @POST
    @Path("/gestionarPNCPostulacionWeb")
    public void gestionarPNCPostulacionWeb(@NotNull GestionPNCPostulacionDTO entrada, @Context UserDTO userDTO);
	
    /**
     * Valida la estructrua de un archivo cruce
     * @param cargue
     *        Informacion del archivo
     * @param userDTO
     *        Usuario que realiza el procedimiento
     * @return Resultado validacion
     */
    @POST
    @Path("/validarArchivoCruce")
    public ResultadoValidacionArchivoDTO validarArchivoCruce(@NotNull CargueArchivoCruceFovisDTO cargue,
            @Context UserDTO userDTO);

    /**
     * Consolida la informacion del archivo de cruce, verificando e estado de cruce y si es necesario revisarlo
     * @param idCargue
     *        Identificador cargue cruce
     * @return Lista de cruces realizados
     */
    @POST
    @Path("/consolidarCruce")
    public List<CruceDetalleDTO> consolidarCruce(@NotNull @QueryParam("idCargue") Long idCargue);

    /**
     * Realiza el registro de la informacion del cruce y asigna las tareas a los usuario seleccionados
     * @param asignarResultadoCruce
     *        Informacion cruces hechos y usuarios a asignar
     */
    @POST
    @Path("/aceptarResultadoCruce")
    public void aceptarResultadoCruce(@NotNull AsignaResultadoCruceDTO asignarResultadoCruce, @Context UserDTO userDTO);

    /**
     * Realiza el guardado parcial de la informacion del cruce con las observaciones y el archivo asociado
     * @param listCruces
     *        Lista con la informacion de los cruces
     */
    @POST
    @Path("/guardarParcialCruces")
    public void guardarParcialCruces(@NotNull List<CruceDetalleDTO> listCruces);

    /**
     * Resgitra el resultado de la verificacion de los cruces
     * @param listCruces
     *        Lista de cruces validados
     */
    @POST
    @Path("/registrarResultadoCruces")
    public void registrarResultadoCruces(@NotNull AsignaResultadoCruceDTO asignaResultadoCruce);
    
    /**
     * Cancela la solicitu de postulación Web por expiración de tiempo de corrección 
     * de datos por parte del Usuario Web.
     * 
     * @param CancelacionSolicitudPostulacionPostulacionDTO 
     *        Datos de la solicitud a rechazar.
     */
    @POST
    @Path("/cancelarSolicitudPostulacionTimeout")
    public void cancelarSolicitudPostulacionTimeout(@NotNull CancelacionSolicitudPostulacionDTO cancelacion);

    /**
     * Servicio para soportar HU-TRA-140 Asignar solicitud presencial
     * @param entrada
     * @param userDTO
     */
    @POST
    @Path("/asignarSolicitudVerificacionFovis")
    public void asignarSolicitudVerificacionFovis(@NotNull AsignarSolicitudPostulacionDTO entrada, @Context UserDTO userDTO);

    /**
     * Servicio que consulta las fechas de vigencia de la asignacion teniendo en cuenta la parametrizacion
     * @param fechaPublicacion
     *        Fecha en la que se publicara la asignacion
     * @return Informacion de las fehcas de vigencia
     */
    @GET
    @Path("/consultarFechaVigenciaAsignacion")
    public InformacionDocumentoActaAsignacionDTO consultarFechaVigenciaAsignacion(@QueryParam("fechaPublicacion") Long fechaPublicacion);

    /**
     * Se procesa las postulaciones que hicieron parte de la asignación para que se guarde el estado actual de cada postulación
     * @param listaPostulaciones
     *        Lista de postulaciones asignadas
     */
    @POST
    @Path("/procesarEstadoActualPostulaciones")
    public void procesarEstadoActualPostulaciones(List<PostulacionFOVISModeloDTO> listaPostulaciones);

    /**
     * Método genérico para la terminación de tareas
     *
     * @param idTarea
     * @param tipoTransaccionEnum
     * @param instanciaProceso
     * @param userDTO
     */
    @POST
    @Path("{idTarea}/{tipoTransaccionEnum}/{instanciaProceso}/terminarTareaPadre")
    public void terminarTareaPadre(@PathParam("idTarea") Long idTarea, @PathParam("tipoTransaccionEnum")  String tipoTransaccionEnum,@PathParam("instanciaProceso") Long instanciaProceso,@Context UserDTO userDTO);}
