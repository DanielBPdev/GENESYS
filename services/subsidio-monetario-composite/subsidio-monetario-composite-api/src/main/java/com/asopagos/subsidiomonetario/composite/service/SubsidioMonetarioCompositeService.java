package com.asopagos.subsidiomonetario.composite.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.asopagos.dto.subsidiomonetario.liquidacion.CargueArchivoBloqueoCMDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoValidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.TrazabilidadSubsidioEspecificoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoValidacionArchivoBloqueoCMDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * a la definicion de servicios de composicion
 * <b>Módulo:</b> Asopagos - 311 - 438<br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co> Roy López Cardona</a>
 */

@Path("subsidioMonetarioComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SubsidioMonetarioCompositeService {

    /**
     * Método que se encarga de cambiar el estado de la liquidación, modificando su estado a "Aprobada primer nivel" y crear la respectiva
     * tarea en la bandeja del supervisor
     * @author rlopez
     * @param numeroSolicitud
     *        número que identifica el proceso de liquidación
     * @param usernameSupervisor
     *        nombre de usuario del supervisor al que se asigna la tarea
     * @param userDTO
     */
    @POST
    @Path("/aprobarLiquidacionMasivaPrimerNivelComposite/{numeroSolicitud}/{idTarea}")
    public void aprobarLiquidacionMasivaPrimerNivelComposite(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, @NotNull @QueryParam("usernameSupervisor") String usernameSupervisor,
            @Context UserDTO userDTO, @Context HttpHeaders headers);

    /**
     * Método que se encarga de cambiar el estado de la liquidación, modificando su estado a "Aprobada segundo nivel", cambia el Estado de
     * Derecho a "Derecho Asignado" e inicia el proceso de dispersión a medios de pago
     * @author rlopez
     * @param numeroSolicitud
     *        número que identifica el proceso de liquidación
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información relacionada en la aprobación
     * @param userDTO
     */
    @POST
    @Path("/aprobarLiquidacionMasivaSegundoNivelComposite/{numeroSolicitud}/{idTarea}")
    public Map<String, String> aprobarLiquidacionMasivaSegundoNivelComposite(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO,
            @Context UserDTO userDTO, @Context HttpHeaders headers);

    /**
     * Método que se encarga de registrar el rechazo de un proceso de liquidación masiva, anexando la información relacionada
     * @author rlopez
     * @param numeroSolicitud
     *        número que identifica el proceso de liquidación
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información relacionada en el rechazo
     * @param userDTO
     */
    @POST
    @Path("/rechazarLiquidacionMasivaPrimerNivelComposite/{numeroSolicitud}/{idTarea}")
    public void rechazarLiquidacionMasivaPrimerNivelComposite(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO,
            @Context UserDTO userDTO, @Context HttpHeaders headers);

    /**
     * Método que se encarga de registar el rechazo de un proceso de liquidación masiva, anexando la información relacionada
     * @author rlopez
     * @param numeroSolicitud
     *        número que identifica el proceso de liquidación
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la infromación relacionada en el rechazo
     * @param userDTO
     */
    @POST
    @Path("/rechazarLiquidacionMasivaSegundoNivelComposite/{numeroSolicitud}/{idTarea}")
    public Map<String,String> rechazarLiquidacionMasivaSegundoNivelComposite(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO,
            @Context UserDTO userDTO, @Context HttpHeaders headers);
    
    /**
     * Método que se encarga de registar el rechazo de un proceso de liquidación masiva asíncrono, anexando la información relacionada
     * @author rlopez
     * @param numeroSolicitud
     *        número que identifica el proceso de liquidación
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la infromación relacionada en el rechazo
     * @param userDTO
     */
    @POST
    @Path("/rechazarLiquidacionMasivaSegundoNivelCompositeAsyn/{numeroSolicitud}/{idTarea}")
    public void rechazarLiquidacionMasivaSegundoNivelCompositeAsyn(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO,
            @Context UserDTO userDTO, @Context HttpHeaders headers);

    /**
     * Metodo que guarda una liquidacion masiva para su posterior ejecución
     * @param liquidacion
     *        Datos del modelo de liquidacion
     * @param periodo
     *        Periodo en el que se realiza la liquidacion
     * @param userDTO
     *        Usuario que realiza el proceso
     * @return Boolean flag de condicion exitosa
     */
    @POST
    @Path("guardarLiquidacionMasiva")
    public RespuestaGenericaDTO guardarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion,
            @QueryParam("periodo") Long periodo, @Context UserDTO userDTO);

    /**
     * Metodo que guarda una liquidacion masiva para su ejecución inmediata
     * @param liquidacion
     *        Datos del modelo de liquidacion
     * @param periodo
     *        Periodo en el que se realiza la liquidacion
     * @param userDTO
     *        Usuario que realiza el proceso
     * @return Boolean flag de condicion exitosa
     */
    @POST
    @Path("ejecutarLiquidacionMasiva")
    public Boolean ejecutarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, @QueryParam("periodo") Long periodo,
            @Context UserDTO userDTO);
    
    /**
     * Método que se encarga de finalizar una tarea dado su identificador y el numero de solicitud para una liquidación cancelada
     * @param numeroSolicitud
     *        valor del número de solicitud
     * @param idTarea
     *        identificador de la tarea
     * @param userDTO
     *        DTO con la información del usuario
     */
    @POST
    @Path("/terminarTarea/liquidacionCancelada/{numeroSolicitud}/{idTarea}")
    public Map<String,String> terminarTareaLiquidacionCancelada(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, @Context UserDTO userDTO);
    
    /**
     * Método que se encarga de finalizar una tarea dado su identificador y el numero de solicitud para una liquidación dispersada
     * @param numeroSolicitud
     *        valor del número de solicitud
     * @param idTarea
     *        identificador de la tarea
     * @param userDTO
     *        DTO con la información del usuario
     */
    @POST
    @Path("/terminarTarea/liquidacionDispersada/{numeroSolicitud}/{idTarea}")
    public void terminarTareaLiquidacionDispersada(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, @Context UserDTO userDTO);
    

    /**
     * Cancela una solicitud de liquidacion en proceso asi como correspondiente proceso en BPM
     * @param params
     */
    @POST
    @Path("cancelarLiquidacionMasivaComposite")
    public Map<String,String> cancelarLiquidacionMasivaComposite(IniciarSolicitudLiquidacionMasivaDTO params, @Context UserDTO userDTO);
    
    /**
     * Método encargado de generar el archivo de liquidación dado el número de
     * radicación asociado a la solicitud
     * 
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return identificador del archivo de liquidación en el ECM
     * @author rlopez
     */
    @GET
    @Path("/generarArchivoLiquidacion/{archivoLiquidacionSubsidio}/{numeroRadicacion}")
    public void generarArchivoLiquidacion(
        @PathParam("archivoLiquidacionSubsidio") Long archivoLiquidacionSubsidio,
        @PathParam("numeroRadicacion") String numeroRadicacion
    );

    @GET
    @Path("/generarRegistroArchivoLiquidacion/{numeroRadicacion}")
    public Long generarRegistroArchivoLiquidacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Método encargado de generar el archivo de personas sin derecho asociadas
     * a una liquidación
     * 
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return identificador del archivo de liquidación en el ECM
     * @author rlopez
     */
    @GET
    @Path("/generarArchivoPersonasSinDerecho/{numeroRadicacion}")
    public String generarArchivoPersonasSinDerecho(@PathParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Método encargado de consultar la trazabilidad de una liquidación de subsidio específica
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return información de trazabilidad
     * @author rlopez
     */
    @GET
    @Path("/consultarTrazabilidadSubsidioEspecifico/{numeroRadicacion}")
    public List<TrazabilidadSubsidioEspecificoDTO> consultarTrazabilidadSubsidioEspecifico(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Metodo que permite ejecutar una liquidacion especifica
     * @param liquidacionEspecifica
     * @return
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarLiquidacionEspecificaComposite")
    public RespuestaGenericaDTO ejecutarLiquidacionEspecificaComposite(LiquidacionEspecificaDTO liquidacionEspecifica, @Context UserDTO userDTO);
    
    
    /**
     * Metodo que gestiona y ejecuta liquidaciones en paralelo
     * @param 
     * @return
     * @author dsuesca
     */
    @POST
    @Path("/gestionarColaEjecucionLiquidacion")
    public List<String> gestionarColaEjecucionLiquidacion();
    

    /**
     * Método que permite confirmar un beneficiario dentro de un proceso de liquidación por fallecimiento
     * 
     * @param condicionBeneficiarioAfiliado
     *        identificador de la condición del beneficiario
     * @param numeroRadicacion
     *        valor del número de radicación
     * 
     * @return DTO con la información actualizada de la liquidación
     * @author rlopez
     */
    @PUT
    @Path("/liquidacionFallecimiento/resultados/confirmarBeneficiarioAfiliado/{numeroRadicacion}")
    public ResultadoLiquidacionFallecimientoDTO confirmarBeneficiarioAfiliadoLiquidacionFallecimiento(
            @QueryParam("identificadorCondicion") Long condicionBeneficiarioAfiliado,
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Metodo que permite ejecutar una liquidacion especifica por fallecimiento de trabajador
     * @param liquidacionEspecifica
     *      DTO con la parametrización
     * @return
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarLiquidacionEspecificaFallecimiento")
    public RespuestaGenericaDTO ejecutarLiquidacionEspecificaFallecimiento(LiquidacionEspecificaDTO liquidacionEspecifica, @Context UserDTO userDTO);

    /**
     * Método que permite escarlar una solicitud de liquidación de fallecimiento a un supervisor de subsidio
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idTarea
     *        identificador de la tarea
     * @param consideracionAportes
     *        Indicador de consideración de aportes por parte de la caja
     * @param tipoDesembolso
     *        Modo en el que se realiza el desembolso de subsidio
     * @param userDTO
     * @author rlopez
     */
    @POST
    @Path("/liquidacionFallecimiento/escalar/{numeroRadicacion}/{idTarea}")
    public void escalarLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("idTarea") String idTarea, @QueryParam("consideracionAportes") Boolean consideracionAportes,
            @QueryParam("tipoDesembolso") ModoDesembolsoEnum tipoDesembolso,
            @QueryParam("estadoAporte") EstadoAporteSubsidioEnum estadoAporte, @QueryParam("usernameSupervisor") String usernameSupervisor,
            @Context UserDTO userDTO);

    /**
     * Método que permite rechazar una liquidación por fallecimiento, especificación la razón y comentarios asociados
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param idTarea
     *        identificador de la tarea
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información de rechazo
     * @param userDTO
     *        usuario del contexto
     * @author rlopez
     */
    @POST
    @Path("/liquidacionFallecimiento/rechazar/{numeroRadicacion}/{idTarea}")
    public Map<String, String> rechazarLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("idTarea") String idTarea, AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO,
            @Context UserDTO userDTO);

    /**
     * Método que se encarga de realizar las validaciones tras la acción de finalización sobre una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param idTarea
     *        identificador de la tarea
     * @param consideracionAportes
     *        Indicador de consideración de aportes por parte de la caja
     * @param tipoDesembolso
     *        Modo en el que se realiza el desembolso de subsiido
     * @param estadoAporte
     *        Estado del aporte del afiliado
     * @param userDTO
     * @author rlopez
     */
    @POST
    @Path("/liquidacionFallecimiento/finalizar/{numeroRadicacion}/{idTarea}")
    public ResultadoLiquidacionFallecimientoDTO finalizarLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("idTarea") String idTarea, @QueryParam("consideracionAportes") Boolean consideracionAportes,
            @QueryParam("tipoDesembolso") ModoDesembolsoEnum tipoDesembolso,
            @QueryParam("estadoAporte") EstadoAporteSubsidioEnum estadoAporte, @Context UserDTO userDTO);

    /**
     * Método que permite cerrar el proceso de liquidación de fallecimiento porque no se encuentran beneficiarios con derecho
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idTarea
     *        Identificador de la tarea
     * @param userDTO
     * @author rlopez
     */
    @POST
    @Path("/liquidacionFallecimiento/cerrar/{numeroRadicacion/{idTarea}")
    public Map<String,String> cerrarLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("idTarea") String idTarea,
            @Context UserDTO userDTO);
    
    /**
     * Método que permite aprobar la liquidación de fallecimiento por parte de supervisor
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param idTarea
     *        Identificador de la tarea
     * @param consideracionAportes
     *        Variable para saber si considera o no un aporte
     * @param tipoDesembolso
     *        Modo en el que se realiza el desembolso de subsidio
     * @param userDTO
     * @author rlopez
     */
    @POST
    @Path("/liquidacionFallecimiento/aprobar/supervisor/{numeroRadicacion}/{idTarea}")
    public void aprobarLiquidacionFallecimientoSupervisor(@PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("idTarea") String idTarea, @QueryParam("consideracionAportes") Boolean consideracionAportes,
            @QueryParam("tipoDesembolso") ModoDesembolsoEnum tipoDesembolso,
            @Context UserDTO userDTO);
    
    /**
     * Método encargado de consultar la trazabilidad de una liquidación de subsidio específica por fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return información de trazabilidad
     * @author rlopez
     */
    @GET
    @Path("/consultarTrazabilidadSubsidioEspecificoFallecimiento/{numeroRadicacion}")
    public List<TrazabilidadSubsidioEspecificoDTO> consultarTrazabilidadSubsidioEspecificoFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Método que permite consultar los resultados de validación para el trabajador seleccionado
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param condicionPersona
     *        Valor del identificador de condición persona
     * @return DTO con la información de las validaciones
     * @author rlopez
     */
    @GET
    @Path("/consultar/validacionesTrabajador/gestionFallecimiento/{numeroRadicacion}/{condicionPersona}")
    public List<ResultadoValidacionFallecimientoDTO> consultarValidacionesTrabajadorGestionFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("condicionPersona") Long condicionPersona);

    /**
     * Método que permite consultar los resultados de validación para el beneficiario seleccionado
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param condicionPersona
     *        Identificador de condición persona
     * @return DTO con la información de las validaciones
     * @author rlopez
     */
    @GET
    @Path("/consultar/validacionesBeneficiario/gestionFallecimiento/{numeroRadicacion}/{condicionPersona}")
    public List<ResultadoValidacionFallecimientoDTO> consultarValidacionesBeneficiarioGestionFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("condicionPersona") Long condicionPersona);
    
    /**
     * Método que se encarga de almacenar los datos necesarios a partir de la gestión realizada sobre un trabajador
     * @param validacion
     *        Conjunto de validación gestionada
     * @param idCondicionPersona
     *        Identificador de la condición de la persona
     * @author rlopez
     */
    @POST
    @Path("/guardar/gestionTrabajador/liquidacionFallecimiento/{numeroRadicacion}")
    public void guardarGestionTrabajadorLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("validacion") ConjuntoValidacionSubsidioEnum validacion, @QueryParam("idCondicionPersona") Long idCondicionPersona);

    /**
     * Método que se encarga de almacenar los datos necesarios a partir de la gestión realizada sobre un beneficiario
     * @param validacion
     *        Conjunto de validación gestionada
     * @param idCondicionPersona
     *        Identificador de la condición de la persona
     * @author rlopez
     */
    @POST
    @Path("/guardar/gestionBeneficiario/liquidacionFallecimiento/{numeroRadicacion}")
    public void guardarGestionBeneficiarioLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("validacion") ConjuntoValidacionSubsidioEnum validacion, @QueryParam("idCondicionPersona") Long idCondicionPersona);

    /**
     * Método que se encarga de ejecutar el proceso de liquidación de fallecimiento tras la gestión del trabajador
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param idCondicionPersona
     *        Identificador de condición persona
     * @author rlopez
     */
    @POST
    @Path("/ejecutar/accionesGestion/liquidacionFallecimiento/{numeroRadicacion}")
    public void ejecutarAccionesGestionLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("idCondicionPersona") Long idCondicionPersona);

    /**
     * Método que retorna el archivo de liquidacion por empleador
     *  
     * @param numeroRadicacion
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    @GET
    @Path("/generarArchivoLiquidacionEmpleador/{numeroRadicacion}")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	public Response generarArchivoLiquidacionEmpleador(@PathParam("numeroRadicacion") String numeroRadicacion,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);
    
    /**
     * Método encargado de generar el archivo de personas sin derecho asociadas
     * a una liquidación
     * 
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return identificador del archivo de liquidación en el ECM
     * @author rlopez
     */
    @GET
	@Path("/generarArchivoPersonasSinDerechoEmpleador/{numeroRadicacion}")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
	public Response generarArchivoPersonasSinDerechoEmpleador(@PathParam("numeroRadicacion") String numeroRadicacion,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);
    
    /**
     * <b>Descripción:</b> Método que se encarga de finalizar una tarea dado su identificador y el numero de solicitud para una liquidación
     * de fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author mosorio
     * 
     * @param numeroSolicitud
     *        valor del número de solicitud
     * @param idTarea
     *        identificador de la tarea
     * @param userDTO
     *        DTO con la información del usuario
     */
    @POST
    @Path("/terminarTarea/liquidacionFallecimiento/{numeroRadicacion}/{idTarea}")
    public void terminarTareaLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("idTarea") String idTarea, @Context UserDTO userDTO);

    /**
     * Metodo para inicializar la pantalla de solicitud de liquidacion de subsidio
     * @return DTO con informacion acerca del proceso actual
     * @author rarboleda
     */
    @GET
    @Path("inicializarPantallaSolicitudLiquidacionComposite")
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionComposite(@Context UserDTO userDTO);
    
    /**
     * Metodo para inicializar la pantalla de solicitud de liquidacion de subsidio para ejecucion de liquidacion masiva
     * @param userDTO
     * @return DTO con informacion acerca de la ultima liquidación masiva en proceso
     */
    /*@GET
    @Path("inicializarPantallaSolicitudLiquidacionCerradaComposite")
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerradaComposite(@Context UserDTO userDTO);*/
    
    /**
     * Método que se encarga de finalizar y cerrar las liquidaciones por fallecimiento cuando son los casos 1 y 3
     * enviando los comunicados respectivos.
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param idTarea
     *        identificador de la tarea
     * @param userDTO
     * @author mosorio
     */
    @POST
    @Path("/finalizar/liquidacionFallecimiento/casos1y3/{numeroRadicacion}/{idTarea}")
    public Map<String,String> finalizarCancelarLiquidacionFallecimientoCasos1y3(@PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("idTarea") String idTarea, @QueryParam("consideracionAportes") Boolean consideracionAportes,
            @QueryParam("tipoDesembolso") ModoDesembolsoEnum tipoDesembolso,
            @QueryParam("estadoAporte") EstadoAporteSubsidioEnum estadoAporte,
            @Context UserDTO userDTO);
    
    
    /**
     * Método que permite rechazar una liquidación por fallecimiento, especificación la razón y comentarios asociados
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param idTarea
     *        identificador de la tarea
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información de rechazo
     * @param userDTO
     *        usuario del contexto
     * @author rlopez
     */
    @POST
    @Path("/validarArchivoBloqueoCM")
    public ResultadoValidacionArchivoBloqueoCMDTO validarArchivoBloqueoCM(CargueArchivoBloqueoCMDTO cargue, @Context UserDTO userDTO);

    /**
     * Realiza la revisión de pagos pendientes de respuesta por anibol, si ya existe una respuesta y el pago fue exitoso se registra, sino se guarda como error
     */
    @POST
    @Path("/dispersarPagos")
    public void dispersarPagos();
    
    /**
     * 
     * @param numeroRadicado
     * @return
     */
    @POST
    @Path("/verificarPersonasSinCondicionesAprobarResultadoComposite")
    public Boolean verificarPersonasSinCondicionesAprobarResultadoComposite(@QueryParam("numeroRadicado") String numeroRadicado);

    @POST
    @Path("/terminarTarea/liquidacionDispersadaAnibol/{numeroSolicitud}/{idTarea}")
    public void terminarLiquidacionDispersadaAnibol(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, @Context UserDTO userDTO);
        
    @GET
    @Path("/generarArchivosSalidaEntidadDescuentoCompositeExp/{numeroRadicacion}")
    public void generarArchivosSalidaEntidadDescuentoCompositeExp(@PathParam("numeroRadicacion") String numeroRadicacion);
}
