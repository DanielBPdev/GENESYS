package com.asopagos.pila.composite.service;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import com.asopagos.pila.dto.ResultadoAprobacionCorreccionAporteDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de empleadores <b>Módulo:</b> Asopagos - HU-211-395,
 * HU-211-396, HU-211-480, HU-211-397, HU-211-398, HU-211-410, HU-211-389,
 * HU-211-400 <br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co> Andres Felipe
 *         Buitrago</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Path("PilaComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface PilaCompositeService {

    /**
     * 411 Metodo de respuesta para la solicitud de cambio de identificacion
     * <code>SolicitudCambioNumIdentAportante</code>
     */
    @POST
    @Path("aprobarSolicitudCambioIdentificacion")
    public void aprobarSolicitudCambioIdentificacion(@NotNull List<SolicitudCambioNumIdentAportante> solicitudes, @Context UserDTO userDTO);
    
    /**
     * 392 Servicio encargado de solicitar la anulación de un registro de operador financier que falla en su conciliación
     * con el operador de información y luego lanza nuevamente el procesamiento de PILA para la planilla OI
     * */
    @POST
    @Path("anularRegistroFinanciero")
    public void anularRegistroFinanciero (@NotNull InconsistenciaDTO inconsistencia, @Context UserDTO user);

    /**
     * Capacidad que permite reprocesar los registros de aporte de las planillas
     * con alguna inconsistencia por aprobar en la bandeja de aportes
     * 
     * @param List<code>InconsistenciaRegistroAporteDTO</code>
     *        La lista de los registros con inconsistencias de archivos de
     *        planilla seleccionados a reprocesar
     * @param <code>fase</code>
     *        Indica sí el reproceso se está realizando para la fase 1 o la fase 2 de PILA M2
     */
    @POST
    @Path("reprocesarRegistrosAporteConInconsistencias")
    public void reprocesarRegistrosAporteConInconsistencias(
            @QueryParam("fase") @NotNull String fase, 
            @NotNull List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO, @Context UserDTO user);

    /**
     * Capacidad que permite desafiliar a empleadores con cero trabajadores desde la pantalla de la HU-404
     * 
     * @param List<code>long</code>
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     */
    @POST
    @Path("desafiliarEmpleadoresCeroTrabajadores")
    public void desafiliarEmpleadoresCeroTrabajadores(List<Long> idEmpleadores, @Context UserDTO user);

    /* ------------------------------------------ INICIO SERVICIOS HU-211-410 --------------------------------------------- */
    //    /**
    //     * Método que se encarga de ejecutar las validaciones cotejando la información consignada en la base de datos
    //     * 
    //     * @param planillaDTO
    //     *        DTO con la información del archivo a validar
    //     * @param userDTO
    //     *        DTO con la información del usuario que realiza la operación
    //     * @return <b>List<DetalleTablaAportanteDTO></b>
    //     *         Listado de aportes del aportante
    //     * @autor jocampo
    //     */
    //    @POST
    //    @Path("validarEnBDPlanillasAdicion")
    //    public List<DetalleTablaAportanteDTO> validarEnBDPlanillasAdicion(@NotNull PlanillaGestionManualDTO planillaDTO,
    //            @Context UserDTO userDTO);

    /**
     * Capacidad que solicita la ejecución simulada de la fase 2 de PILA 2 para archivo de corrección/adición
     * @param idIndicePlanilla
     *        ID del índice de planilla a ejecutar
     * @param idPlanillaOriginal
     *        ID de la planilla original asociada a la planilla consultada
     * @param idRegistroGeneral
     *        ID del registro general de la operación para verificar la validez del proceso
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     */
    @GET
    @Path("registrarRelacionarAportesPlanilla")
    public void registrarRelacionarAportesPlanilla(@NotNull @QueryParam("idIndicePlanilla") Long idIndicePlanilla,
            @QueryParam("idPlanillaOriginal") Long idPlanillaOriginal, @NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral, Long idTransaccion,
            @Context UserDTO userDTO);

    /**
     * Capacidad compuesta que actualiza un aporte original con los datos de una corrección y su respectivo
     * moviemiento de cuenta de aportes
     * @param criteriosSimulacion
     *        DTO que incluye los parámetros de la solicitud
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>ResultadoAprobacionCorreccionAporteDTO</b>
     *         Mensaje que indica los estados que fueron actualizados para el archivo, null en caso de fallo
     */
    @POST
    @Path("registrarAporteSimuladoCorreccion")
    public ResultadoAprobacionCorreccionAporteDTO registrarAporteSimuladoCorreccion(RegistrarCorreccionAdicionDTO criteriosSimulacion,
            @Context UserDTO userDTO);

    /**
     * Capacidad que solicita la ejecución simulada de la fase 3 de PILA 2 para archivo de corrección/adición
     * @param idIndicePlanilla
     *        ID del índice de planilla a ejecutar
     * @param idRegistroGeneral
     *        ID del registro general de la operación para verificar la validez del proceso
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     */
    @GET
    @Path("registrarRelacionarNovedadesPlanilla")
    public void registrarRelacionarNovedadesPlanilla(@NotNull @QueryParam("idIndicePlanilla") Long idIndicePlanilla,
            @NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral, @Context UserDTO userDTO);

    /**
     * Capacidad que da por finalizada el procesamiento manual de planillas de corrección o adición solicitando su
     * respectiva notificación
     * @param idRegistroGeneral
     *        ID del registro general del archivo a finalizar
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>Map<String, String></b>
     *         Mensaje con el resultado de la operación de finalización
     */
    @GET
    @Path("finalizarPlanillaAdicionCorreccion")
    public Map<String, String> finalizarPlanillaAdicionCorreccion(@NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral,
            @Context UserDTO userDTO);

    /**
     * Método que se encarga de verificar si todos los registos de aportes de una planilla tienen un estado aceptable para su registro o
     * relación
     * 
     * @param idIndicePlanilla
     * @param idRegistroGeneral
     * @param userDTO
     * @return
     */
    @POST
    @Path("verificarEstadoRegistrosAdicionRegistrarRelacionarAporte")
    public Map<String, String> verificarEstadoRegistrosAdicionRegistrarRelacionarAporte(
            @NotNull @QueryParam("idIndicePlanilla") Long idIndicePlanilla,
            @NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral, @Context UserDTO userDTO);
    /* -------------------------------------------- FIN SERVICIOS HU-211-410 ---------------------------------------------- */

    /**
     * Capacidad que permite la aprobacion de manera manualde los registros de
     * aporte de las planillas con alguna inconsistencia seleccionados en la
     * bandeja de aportes
     * 
     * @param List<code>InconsistenciaRegistroAporteDTO</code>
     *        La lista de los registros con inconsistencias de archivos de
     *        planilla seleccionados a aprobar
     * 
     * @return List<code>InconsistenciaRegistroAporteDTO</code> La lista de los
     *         registros con inconsistencias de archivos de planilla
     */
    @POST
    @Path("aprobarRegistrosAporteConInconsistencias")
    public List<InconsistenciaRegistroAporteDTO> aprobarRegistrosAporteConInconsistencias(
            List<InconsistenciaRegistroAporteDTO> lstInconsistenciaRegistroAporteDTO, @Context UserDTO user);

    /**
     * Metodo que envia una solicitud de cambio de identificacion del aportante
     * a la bandeja del supervisor
     * 
     * @param solicitudCambio
     *        contiene los campos requeridos para enviar a la bandeja
     * @param user
     *        contiene los datos del usuario que esta realizando la
     *        solicitud
     */
    @POST
    @Path("enviarSolicitudCambioIden")
    public InconsistenciaDTO enviarSolicitudCambioIden(InconsistenciaDTO inconsistencias,
            @QueryParam(value = "numeroIdentificacion") Long numeroIdentificacion,
            @QueryParam(value = "tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @Context UserDTO user);

    /**
     * Servicio encargado del proceso de la creación de un aportante PILA desde bandeja 392
     * @param idPlanilla
     * @param inconsistencia
     * @return
     */
    @POST
    @Path("crearAportantePila")
    public InconsistenciaDTO crearAportantePila(@QueryParam("idPlanilla") Long idPlanilla, InconsistenciaDTO inconsistencia);

    /**
     * Servicio encargado de la aprobación de un archivo en bandeja 392 para continuar su proceso
     * @param inconsistencia
     */
    @POST
    @Path("continuarProcesoBandeja392")
    public void continuarProcesoBandeja392(InconsistenciaDTO inconsistencia);

    /**
     * Servicio para la consulta de la cuenta aportes de la planilla original de una corrección PILA
     * @param idPlanillaOriginal
     * @return
     */
    @GET
    @Path("consultarCuentaAportePlanillaOriginal")
    public List<CuentaAporteDTO> consultarCuentaAportePlanillaOriginal(@QueryParam("idPlanillaOriginal") @NotNull Long idPlanillaOriginal);

    /**
     * Capacidad que aplica los aportes en una planilla de adición
     * @param criteriosSimulacion
     *        DTO que incluye los parámetros de la solicitud
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>ResultadoAprobacionCorreccionAporteDTO</b>
     *         DTO que incluye el mensaje que indica los estados que fueron actualizados para el archivo, null en caso de fallo
     */
    @POST
    @Path("aprobarAportesAdicionCompuesto")
    public ResultadoAprobacionCorreccionAporteDTO aprobarAportesAdicionCompuesto(@NotNull RegistrarCorreccionAdicionDTO criteriosSimulacion,
            @Context UserDTO userDTO);

    /**
     * Capacidad que aplica los cambios aceptados para novedades en una planilla de corrección o adición
     * @param criteriosSimulacion
     *        DTO que incluye los parámetros de la solicitud
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>ResultadoAprobacionCorreccionAporteDTO</b>
     *         DTO que incluye el mensaje que indica los estados que fueron actualizados para el archivo, null en caso de fallo
     */
    @POST
    @Path("aprobarProcesoNovedadesAdicionCorreccionCompuesto")
    public ResultadoAprobacionCorreccionAporteDTO aprobarProcesoNovedadesAdicionCorreccionCompuesto(
            RegistrarCorreccionAdicionDTO criteriosSimulacion, @Context UserDTO userDTO);
    
    /**
     * Capacidad que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser aprobadas en la bandeja de
     * aportes y aprobar estos registros
     * 
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code> El tipo de identificacion
     *        del aportante a consultar
     * @param numeroIdentificacionAportante
     *        <code>String</code> El numero de identificacion del aportante
     *        a consultar
     * @param digitoVerificacionAportante
     *        <code>Short</code> El digito de verificacion del aportante
     * @param fechaInicio
     *        <code>Long</code> la fecha de inicio del procesamiento del
     *        aporte de la planilla
     * @param fechaFin
     *        <code>Long</code> la fecha de fin del procesamiento del aporte
     *        de la planilla
     * 
     * @return
     */
    @GET
    @Path("aprobarTodasConInconsistenciasValidacion")
    public void aprobarTodasConInconsistenciasValidacion(
            @QueryParam("tipoIdentificacionAportante") TipoIdentificacionEnum tipoIdentificacionAportante,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
            @QueryParam("digitoVerificacionAportante") Short digitoVerificacionAportante, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin, @Context UserDTO user);
    
    /**
    * Capacidad que permite la consulta de los registros de aporte de las
    * planillas con alguna inconsistencia para ser reprocesadas en la bandeja de
    * aportes y reprocesar estos registros
    * 
    * @param tipoIdentificacion
    *        <code>TipoIdentificacionEnum</code> El tipo de identificacion
    *        del aportante a consultar
    * @param numeroIdentificacionAportante
    *        <code>String</code> El numero de identificacion del aportante
    *        a consultar
    * @param digitoVerificacionAportante
    *        <code>Short</code> El digito de verificacion del aportante
    * @param fechaInicio
    *        <code>Long</code> la fecha de inicio del procesamiento del
    *        aporte de la planilla
    * @param fechaFin
    *        <code>Long</code> la fecha de fin del procesamiento del aporte
    *        de la planilla
    * 
    * @return
    */
   @GET
   @Path("reprocesarTodasConInconsistenciasValidacion")
   public void reprocesarTodasConInconsistenciasValidacion(
           @QueryParam("tipoIdentificacionAportante") TipoIdentificacionEnum tipoIdentificacionAportante,
           @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
           @QueryParam("digitoVerificacionAportante") Short digitoVerificacionAportante, @QueryParam("fechaInicio") Long fechaInicio,
           @QueryParam("fechaFin") Long fechaFin, @Context UserDTO user);

   /**
    * Capacidad que permite reprocesar las planillas de manera automatica 
    * guardando el estado del procesamiento
    *  
    * @param indicesPlanillas
    * @return
    */
   @POST
   @Path("reprocesarPlanillasMundoUno")
	public void reprocesarPlanillasMundoUno(List<PlanillaGestionManualDTO> indicesPlanilla, UserDTO user);

   /**
    * Capacidad que permite crear los aportantes de las planillas de manera masiva
    *  
    * @param indicesPlanillas
    * @return
    */
   @POST
   @Path("crearAportantesPlanillasMundoUno")
   public void crearAportantesPlanillasMundoUno(List<PlanillaGestionManualDTO> indicesPlanilla, UserDTO user);

   /**
    * Capacidad que permite reprocesar todos los registros en blq 5 q cumplan con el criterio de busqueda
    *  @param tipoIdentificacion
    *        El tipo de identificacion para la busqueda
    * @param numeroPlanilla
    *        Numero de planilla asociado a la busqueda
    * @param fechaInicio
    *        Fecha inicial de busqueda
    * @param fechaFin
    *        Fecha final de busqueda
    * @param numeroIdentificacion
    *        Numero de identificacion de busqueda
    * @param operador
    *        Tipo de operador de la busqueda
    * @param userDTO
    *        Datos del usuario
    */
   @GET
   @Path("reprocesarTodasPlanillasMundoUno")
   public void reprocesarTodasPlanillasMundoUno(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroPlanilla") String numeroPlanilla,
           @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
           @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("operador") TipoOperadorEnum operador,
           @QueryParam("digitoVerificacion") Short digitoVerificacion, @QueryParam("bloqueValidacion") String bloqueValidacion, @QueryParam("ocultarBlq5") Boolean ocultarBlq5,  @Context UserDTO user);

   /**
    * Capacidad que permite crear todos los aportantes de los registros en blq 5 q cumplan con el criterio de busqueda
    *  @param tipoIdentificacion
    *        El tipo de identificacion para la busqueda
    * @param numeroPlanilla
    *        Numero de planilla asociado a la busqueda
    * @param fechaInicio
    *        Fecha inicial de busqueda
    * @param fechaFin
    *        Fecha final de busqueda
    * @param numeroIdentificacion
    *        Numero de identificacion de busqueda
    * @param operador
    *        Tipo de operador de la busqueda
    * @param userDTO
    *        Datos del usuario
    */
   @GET
   @Path("crearAportantesTodosMundoUno")
   public void crearAportantesTodosMundoUno(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroPlanilla") String numeroPlanilla,
           @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
           @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("operador") TipoOperadorEnum operador,
           @QueryParam("digitoVerificacion") Short digitoVerificacion, @QueryParam("bloqueValidacion") String bloqueValidacion, @QueryParam("ocultarBlq5") Boolean ocultarBlq5,  @Context UserDTO user);
    
    /**
     * Capacidad que aplica para procesar planillas manualmente de manera automatica 410
     * @param PlanillaGestionManualDTO
     *        DTO que incluye la planilla a procesar
     *        DTO con la información del usuario que realiza la operación
     * @return null
     */
    @POST
    @Path("procesarAutomaticoPlanillaManual")
    public void procesarAutomaticoPlanillaManual(@QueryParam("numeroPlanilla") Long numeroPlanilla, @Context UserDTO user);

    
    /**
     * Servicio encargado de ejecutar los procesos de conciliación de archivos OI y OF y el procesamiento automático 
     * de planillas manuales (HU 410)

     * @param UserDTO se recibe por contexto con la información del usuario que realiza la acción (cuando lo ejecuta el schedule ConciliarArchivosOIyOF el usuario es sistema). 
     */
    @POST
    @Path("conciliarOIOFyProcesarPilaManual")
	public void conciliarOIOFyProcesarPilaManual(@Context UserDTO userDTO);

     /**
     * Servicio que reprocesa las planillas en B3 por inconsistencia
     * @param UserDTO se recibe por contexto con la información del usuario que realiza la acción (cuando lo ejecuta el schedule ConciliarArchivosOIyOF el usuario es sistema). 
     */
    @POST
    @Path("reprocesarB3Aut")
	public void reprocesarB3Aut(@Context UserDTO userDTO);


    /**
     * Servicio encargado de ejecutar los procesos de reproceso sobre planillas F que quedaron en estado conciliado y su I en espera de conciliacion

     * @param UserDTO se recibe por contexto con la información del usuario que realiza la acción (cuando lo ejecuta el schedule ConciliarArchivosOIyOF el usuario es sistema). 
     */
    @POST
    @Path("reprocesarPlanillasPendientesConciliacion")
    public void reprocesarPlanillasPendientesConciliacion(@Context UserDTO userDTO);

    @POST
    @Path("cargarArchivosPilaFtp")
    public void cargarArchivosPilaFtp(@QueryParam("idOperadorInformacion") Long idOperadorInformacion, @Context UserDTO userDTO) throws Exception;
}
