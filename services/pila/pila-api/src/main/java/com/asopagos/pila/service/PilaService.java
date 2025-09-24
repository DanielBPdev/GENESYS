package com.asopagos.pila.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import com.asopagos.dto.AportePeriodoCertificadoDTO;
import com.asopagos.dto.modelo.AporteDetalladoPlanillaDTO;
import com.asopagos.dto.modelo.BancoModeloDTO;
import com.asopagos.dto.modelo.EstadoArchivoPorBloqueModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.pila.DetalleTablaAportanteDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.pila.dto.ArchivosProcesadosFinalizadosOFDTO;
import com.asopagos.pila.dto.BloquesValidacionArchivoDTO;
import com.asopagos.pila.dto.CabeceraDetalleArchivoDTO;
import com.asopagos.pila.dto.ConsultasArchivosOperadorFinancieroDTO;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;
import com.asopagos.pila.dto.InformacionAporteAdicionDTO;
import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import com.asopagos.pila.dto.ResultadoAprobacionCorreccionAporteDTO;
import com.asopagos.pila.dto.ResultadoFinalizacionPlanillaAsistidaDTO;
import com.asopagos.pila.dto.ResultadoSimulacionAporteDetalladoDTO;
import com.asopagos.pila.dto.ResultadoSimulacionNovedadDTO;
import com.asopagos.pila.dto.ResultadoValidacionRegistrosAdicionCorrecionDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.pila.dto.DetalleAporteVista360DTO;
import com.asopagos.pila.dto.DatosArchvioReporteDTO;
import java.io.IOException;

/**
 * Este servicio sera el encargado de controlar el microservicio de PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-401, HU-211-410 <br/>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez Cediel </a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E. </a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe Buitrago </a>
 */

@Path("pila")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface PilaService {

    /**
     * Cantidad total de archivos que fueron cargados en el sistema (por medio
     * de la ejecución de la “HU-211-387” o de la “HU-211-386”) y que aún no han
     * sido procesados (por medio de la ejecución de la “HU-211-391”), es decir
     * los archivos con “Estado” igual a “Cargado”.
     * 
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosPendientesPorProcesarOI")
    public Long archivosPendientesPorProcesarInformacion();

    /**
     * Cantidad total de archivos cuyo procesamiento finalizó en las horas
     * transcurridas del día actual y las 48 horas de los 2 días calendario
     * anteriores, es decir los archivos con “Estado” igual a “Recaudo
     * notificado”
     * 
     * @return Long con la cantidad total de archivos
     */
    @GET
    @Path("archivosProcesoFinalizado")
    public Long archivosProcesoFinalizado();

    /**
     * Cantidad total de archivos que después de ser procesados presentaron
     * inconsistencias (hasta el “bloque 6 Conciliación O.I. vs O.F.” de las
     * validaciones del recaudo PILA)
     * 
     * @return Long con la cantidad de archivos en gestion
     */
    @GET
    @Path("archivosInconsistentesOI")
    public Long archivosInconsistentesOI();

    /**
     * Cantidad total de archivos que después de ser procesados presentaron
     * inconsistencias (después del “bloque 6 Conciliación O.I. vs O.F.” de las
     * validaciones del recaudo PILA)
     * 
     * @return Long con la cantidad de archivos en gestion
     */
    @GET
    @Path("archivosBandejaGestionOI")
    public Long archivosBandejaGestionOI();

    /**
     * Cantidad total de archivos que fueron cargados en el sistema (por medio
     * de la ejecución de la “HU-211-387” o de la “HU-211-386”) y que aún no han
     * sido procesados
     * 
     * @return Long con la cantidad de archivos en gestion
     */
    @GET
    @Path("archivosPendientesPorProcesarInformacionManualOI")
    public Long archivosPendientesPorProcesarInformacionManualOI();

    /**
     * Cantidad total de archivos que fueron procesados manualmente en las horas
     * transcurridas del día actual y las 48 horas de los 2 días calendario
     * anteriores (por medio de la ejecución de la “HU-211-400 Notificar
     * resultados de recaudo PILA”), es decir los archivos con “Estado” igual a
     * “Recaudo notificado - manual”.
     * 
     * @return Long con la cantidad total de archivos
     */
    @GET
    @Path("archivosProcesoFinalizadoManual")
    public Long archivosProcesoFinalizadoManual();

    /**
     * Cantidad total de archivos que fueron descargados en las horas transcurridas del día actual y las 24 horas del día hábil anterior,
     * con “Estado” igual a “Cargado” (por medio de la ejecución de la “HU-211-387” o de la “HU-211-386”).
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosCargados")
    public Long archivosCargados();

    /**
     * Cantidad total de archivos que fueron cargados en el sistema en las horas transcurridas del día actual y las 24 horas del día
     * calendario anterior (ejecutando la “HU-211-387” o la “HU-211-386”) y que aún no han sido procesados
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosEnProcesoControl")
    public Long archivosEnProcesoControl();

    /**
     * Cantidad total de archivos cuyo procesamiento finalizó en las horas transcurridas del día actual y las 24 horas del día hábil
     * anterior, es decir los archivos con “Estado” igual a “Recaudo notificado” (“HU-211-400 Notificar resultados de recaudo PILA”)
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosProcesoFinalizadoControl")
    public Long archivosProcesoFinalizadoControl();

    /**
     * Cantidad total de archivos que fueron procesados en las horas transcurridas del día actual y las 24 horas del día hábil anterior y
     * que presentaron inconsistencias (hasta el “bloque 6 Conciliación O.I. vs O.F.” de las validaciones del recaudo PILA – “HU-211-392”)
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosInconsistentesControl")
    public Long archivosInconsistentesControl();

    /**
     * Cantidad total de archivos que fueron procesados en las horas transcurridas del día actual y las 24 horas del día hábil anterior y
     * que presentaron inconsistencias (después del “bloque 6 Conciliación O.I. vs O.F.” de las validaciones del recaudo PILA –
     * “HU-211-399”)
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosEnGestionControl")
    public Long archivosEnGestionControl();

    /**
     * Cantidad total de archivos que fueron cargados en el sistema (por medio de la ejecución de la “HU-211-387” o de la “HU-211-386”) y
     * que aún no han sido procesados (por medio de la ejecución de la “HU-211-410 Procesar manualmente planillas PILA”)
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosEnProcesoManualControl")
    public Long archivosEnProcesoManualControl();

    /**
     * Cantidad total de archivos que fueron procesados manualmente en las horas transcurridas del día actual y las 48 horas de los 2 días
     * calendario anteriores (por medio de la ejecución de la “HU-211-400 Notificar resultados de recaudo PILA”)
     * @return Integer con la cantidad total de archivos
     */
    @GET
    @Path("archivosProcesoFinalizadoManualControl")
    public Long archivosProcesoFinalizadoManualControl();

    /**
     * Suma total de los aportes con estado Relacionado
     * @param idPlanilla
     * @return Long
     */
    @GET
    @Path("totalAportesRelacionados")
    public BigDecimal totalAportesRelacionados(@QueryParam("idRegistroGeneral") Long idRegistroGeneral);

    /**
     * Suma total de los aportes con estado Registrado
     * @param idPlanilla
     * @return Long
     */
    @GET
    @Path("totalAportesRegistrados")
    public BigDecimal totalAportesRegistrados(@QueryParam("idRegistroGeneral") Long idRegistroGeneral);

    /**
     * Lista que contiene la lista de aportantes filtrados por su numero de registro general
     * @param idPlanilla
     * @return AporteDetalladoModeloDTO
     */
    @GET
    @Path("consultarDetalleAportesPorPlanilla")
    public List<AporteDetalladoPlanillaDTO> consultarDetalleAportesPorPlanilla(@QueryParam("idRegistroGeneral") Long idRegistroGeneral,
            @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Lista que contiene la lista de aportantes filtrados por su numero de registro general
     * @param idPlanilla
     * @return AporteDetalladoModeloDTO
     */
    @GET
    @Path("consultarDetalleNovedadesPorPlanilla")
    public List<DetallePestanaNovedadesDTO> consultarDetalleNovedadesPorPlanilla(@QueryParam("idRegistroGeneral") Long idRegistroGeneral);

    /**
     * Metodo que se encarga de realizar todas las consultas para operador financiero
     * definidas en la HU-401
     * 
     * @return ConsultasArchivosOperadorFinancieroDTO
     *         Con cada una de las consultas asociadasa operador financiero
     */
    @GET
    @Path("consultasArchivosOperadorFinanciero")
    public ConsultasArchivosOperadorFinancieroDTO consultasArchivosOperadorFinanciero();

    /**
     * Metodo que se encarga de realizar todas las consultas para operador financiero
     * definidas en la HU-401
     * 
     * @return <b>List<ConsultasArchivosOperadorFinancieroDTO></b>
     *         Con cada una de las consultas asociadasa operador financiero
     */
    @GET
    @Path("buscarArchivosOIProcesadosFinalizados")
    public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizados(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("digitoVerificacion") Short digitoVerificacion,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("tipoOperador") TipoOperadorEnum tipoOperador, @QueryParam("numeroPlanilla") Long numeroPlanilla,
            @QueryParam("idBanco") String idBanco, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Metodo que se encarga de realizar todas las consultas para operador financiero
     * definidas en la HU-401
     * 
     * @return <b>List<ConsultasArchivosOperadorFinancieroDTO></b>
     *         Con cada una de las consultas asociadasa operador financiero
     */
    @GET
    @Path("buscarArchivosOIProcesadosFinalizadosManual")
    public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizadosManual(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("digitoVerificacion") Short digitoVerificacion,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("tipoOperador") TipoOperadorEnum tipoOperador, @QueryParam("numeroPlanilla") Long numeroPlanilla,
            @QueryParam("bancoId") String idBanco, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Metodo que retorna la lista de los archivos procesados finalizados
     * 
     * @return <b>List<ConsultasArchivosOperadorFinancieroDTO></b>
     *         Con cada una de las consultas asociadasa operador financiero
     */
    @GET
    @Path("verArchivosProcesadosFinalizados")
    public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizados();

    /**
     * Metodo encargado de retornar los bloques de validacion por los cuales paso un archivo
     * 
     * @param idPlanilla
     * @param tipoOperador
     * @return
     */
    @GET
    @Path("verDetalleBloquesValidacionArchivo")
    public List<BloquesValidacionArchivoDTO> verDetalleBloquesValidacionArchivo(@QueryParam("idPlanilla") Long idPlanilla,
            @QueryParam("tipoOperador") TipoOperadorEnum tipoOperador);

    /**
     * Metodo encargado de retornar los bloques de validacion por los cuales paso un archivo
     * 
     * @param idPlanilla
     * @param tipoOperador
     * @return
     */
    @GET
    @Path("verCabeceraDetalleBloquesValidacionArchivo")
    public CabeceraDetalleArchivoDTO verCabeceraDetalleBloquesValidacionArchivo(@QueryParam("idPlanilla") Long idPlanilla,
            @QueryParam("tipoOperador") TipoOperadorEnum tipoOperador);

    /**
     * Servicio de consulta de los bancos (operadores financieros para pila),
     * parametrizados en el sistema
     * 
     * @return <code>List<BancoModeloDTO></code>
     *         La lista de los bancos paametrizada en el sistema.
     */
    @GET
    @Path("consultarBancosParametrizados")
    public List<BancoModeloDTO> consultarBancosParametrizados();

    /**
     * Capacidad que devuele el conteo actual de las planillas pendientes por gestión manual
     * 
     * @return <b>Integer</b>
     *         Cantidad de planillas con gestión manual pendiente
     */
    @GET
    @Path("consultarCantidadPlanillasPendientesGestionManual")
    public Integer consultarCantidadPlanillasPendientesGestionManual();

    /**
     * Capacidad que consulta, con base en criterios recibidos, un listado de planillas marcadas para procesamiento
     * asistido de PILA mundo 2
     * 
     * @param numeroPlanilla
     *        Criterio de consulta por número de planilla
     * @param fechaIngreso
     *        Criterio de consulta por fecha de carga de la planilla
     * @param uri
     *        Información de la petición
     * @param response
     *        Respuesta HTML
     * @return <b>List<PlanillaGestionManualDTO></b>
     *         Listado de los resultados obtenidos
     */
    @GET
    @Path("consultarPlanillasManualPendientes")
    public List<PlanillaGestionManualDTO> consultaPlanillasGestionManual(@QueryParam("numeroPlanilla") Long numeroPlanilla,
            @QueryParam("fechaIngreso") Long fechaIngreso, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Capacidad que actualiza el estado de un índice de planilla para archivos de preproceso en gestión asistida
     * 
     * @param planillaDTO
     *        DTO con la información del archivo a actualizar
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     */
    @POST
    @Path("marcarNoProcesamientoArchivoR")
    public void marcarNoProcesamientoArchivoR(PlanillaGestionManualDTO planillaDTO, @Context UserDTO userDTO);

    /**
     * Capacidad encargada de presentar el contenido de una planilla de corrección, indicando es estado de vigencia del mismo
     * 
     * @param idPlanillaOriginal
     *        ID de la planilla original asociada a la planilla consultada
     * @param idPlanillaCorrecion
     *        ID de la planilla consultada
     * @return <b>RespuestaConsultaEmpleadorDTO</b>
     *         DTO con el contenido a presentar en pantalla
     */
    @GET
    @Path("consultarContenidoPlanillaOriginal")
    public RespuestaConsultaEmpleadorDTO consultarContenidoPlanillaOriginal(
    		@QueryParam("idPlanillaOriginal") Long idPlanillaOriginal,
            @QueryParam("idPlanillaCorrecion") @NotNull Long idPlanillaCorrecion,
    		@Context UserDTO userDTO
    		);

    /**
     * TODO pendiente agregar en XLS
     * Capacidad que permite consultar los datos de una planilla de corrección ejecutada en fase 1
     * 
     * @param idPlanillaOriginal
     *        ID de la planilla original asociada a la planilla consultada
     * @param idPlanillaCorrecion
     *        ID de la planilla consultada
     * @return <b>ResultadoValidacionRegistrosCorrecionDTO</b>
     *         DTO con el contenido del archivo de corrección, incluyendo la evaluación de los registros
     */
    @GET
    @Path("consultarResultadoFase1SimuladaCorrecion")
    public ResultadoValidacionRegistrosAdicionCorrecionDTO consultarResultadoFase1SimuladaCorrecion(
            @QueryParam("idPlanillaOriginal") Long idPlanillaOriginal,
            @QueryParam("idPlanillaCorrecion") @NotNull Long idPlanillaCorrecion);

    /**
     * Capacidad para dar inicio a la ejecución de los USP encargados de la fase 1 de pila 2 en modo simulado. Esta capacidad
     * retorna el DTO con la información que se presenta para evaluar la simulación de los registros corregidos
     * 
     * @param idPlanillaOriginal
     *        ID de la planilla original asociada a la planilla consultada
     * @param idPlanillaCorrecion
     *        ID de la planilla consultada
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>ResultadoValidacionRegistrosCorrecionDTO</b>
     *         DTO con el contenido del archivo de corrección, incluyendo la evaluación de los registros
     */
    @GET
    @Path("solicitarEjecucionFase1SimuladaCorreccion")
    public ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarEjecucionFase1SimuladaCorreccion(
            @QueryParam("idPlanillaOriginal") Long idPlanillaOriginal,
            @QueryParam("idPlanillaCorrecion") @NotNull Long idPlanillaCorrecion, @Context UserDTO userDTO);

    /**
     * Capacidad para dar inicio a la ejecución de los USP encargados de la fase 1 de pila 2 en modo simulado. Esta capacidad
     * retorna el DTO con la información que se presenta para evaluar la simulación de los registros de adición
     * 
     * @param idIndicePlanilla
     *        ID de la entrada de índice de planilla para la cual se ejecuta la simulación
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>ResultadoValidacionRegistrosAdicionCorrecionDTO</b>
     *         DTO con el listado del resultado de procesamiento de planilla de adición para Fase 1
     */
    @GET
    @Path("solicitarEjecucionFase1SimuladaAdicion")
    public ResultadoValidacionRegistrosAdicionCorrecionDTO solicitarEjecucionFase1SimuladaAdicion(
            @NotNull @QueryParam("idIndicePlanilla") Long idIndicePlanilla, @Context UserDTO userDTO);

    /**
     * Capacidad que define el registro o el rechazo de una entrada de corrección en un archivo de correción
     * 
     * @param registrarCorreccionDTO
     *        DTO con los parámetros para la operación
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>Map<String, String></b>
     *         Respuesta que indica sí es posible continuar a la siguiente etapa
     */
    @POST
    @Path("registrarNoRegistrarCorreccion")
    public Map<String, String> registrarNoRegistrarCorreccion(RegistrarCorreccionAdicionDTO registrarCorreccionDTO,
            @Context UserDTO userDTO);

    /**
     * Capacidad que define el registro o el rechazo de una entrada de adición
     * 
     * @param registrarAdicionDTO
     *        DTO con los parámetros para la operación
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>Map<String, String></b>
     *         Respuesta que indica sí es posible continuar a la siguiente etapa
     */
    @POST
    @Path("registrarNoRegistrarAdicion")
    public Map<String, String> registrarNoRegistrarAdicion(RegistrarCorreccionAdicionDTO registrarAdicionDTO, @Context UserDTO userDTO);

    /**
     * Capacidad empleada para determinar sí los registros detallados asociados a un registro general
     * son válidos para continuar a la Fase 2 (registro o relación de aportes)
     * @param idPlanillaCorreccion
     *        ID de planilla de corrección
     * @return <b>Map<String, String></b>
     *         Mensaje que indica sí se puede o no continuar a la siguiente fase
     */
    @GET
    @Deprecated
    @Path("validoParaContinuarAFase2Correccion")
    public Map<String, String> validoParaContinuarAFase2Correccion(@QueryParam("idPlanillaCorreccion") Long idPlanillaCorreccion);

    /**
     * Capacidad que permite hacer el cambio de la marca de habilitación para proceso manual de una planilla de corrección
     * 
     * @param idIndicePlanilla
     *        ID del índice de planilla a actualizar
     * @param nuevaMarca
     *        Nuevo valor para la marca de habilitación
     */
    @GET
    @Path("cambiarMarcaHabilitacionPila2Manual")
    public void cambiarMarcaHabilitacionPila2Manual(@QueryParam("idIndicePlanilla") Long idIndicePlanilla,
            @QueryParam("nuevaMarca") Boolean nuevaMarca);

    /**
     * Capacidad que obtiene la información relevante de registros generales y detallados para pantalla de Aportes
     * @param criteriosSimulacion
     *        DTO que incluye los parámetros de la consulta
     * @return <b>ResultadoSimulacionAporteDetalladoDTO</b>
     *         DTO con la información a presentar en pantalla
     */
    @POST
    @Path("consultarDatosFase2SimuladaCorreccion")
    public ResultadoSimulacionAporteDetalladoDTO consultarDatosFase2SimuladaCorreccion(RegistrarCorreccionAdicionDTO criteriosSimulacion);

    /**
     * Capacidad que aplica los cambios aceptados para aportes en una planilla de corrección
     * @param criteriosSimulacion
     *        DTO que incluye los parámetros de la solicitud
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>ResultadoAprobacionCorreccionAporteDTO</b>
     *         DTO que incluye el mensaje que indica los estados que fueron actualizados para el archivo, null en caso de fallo
     */
    @POST
    @Path("aprobarCambioAportesCorrecciones")
    public ResultadoAprobacionCorreccionAporteDTO aprobarCambioAportesCorrecciones(RegistrarCorreccionAdicionDTO criteriosSimulacion,
            @Context UserDTO userDTO);

    /**
     * Capacidad que obtiene la información requerida para la presentación de la pestaña de novedades
     * @param criteriosSimulacion
     *        DTO que incluye los parámetros de la solicitud
     * @return <b>ResultadoAprobacionCorreccionAporteDTO</b>
     *         DTO que incluye el mensaje que indica los estados que fueron actualizados para el archivo, null en caso de fallo
     */
    @POST
    @Path("consultarDatosFase3SimuladaAdicionCorreccion")
    public ResultadoSimulacionNovedadDTO consultarDatosFase3SimuladaAdicionCorreccion(RegistrarCorreccionAdicionDTO criteriosSimulacion);

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
    @Path("aprobarProcesoNovedadesAdicionCorreccion")
    public ResultadoAprobacionCorreccionAporteDTO aprobarProcesoNovedadesAdicionCorreccion(
            RegistrarCorreccionAdicionDTO criteriosSimulacion, @Context UserDTO userDTO);

    /**
     * Capacidad que actualiza el estado del índice de planilla para marcar su finalización
     * @param idRegistroGeneral
     *        ID del registro general de la operación para verificar la validez del proceso
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @return <b>ResultadoFinalizacionPlanillaAsistidaDTO</b>
     *         DTO con la cantidad de los aportes causados o corregidos y los indicadores que permiten establecer que no quedan tareas
     *         pendientes para el ESB
     */

    @GET
    @Path("finalizarPlanillaAsistida")
    public ResultadoFinalizacionPlanillaAsistidaDTO finalizarPlanillaAsistida(
            @NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral, @Context UserDTO userDTO);

    /**
     * Servicio que actualiza los estados del registro de procesamiento de una planilla PILA
     * 
     * @param actualizacionEstadosPlanillaDTO
     *        <code>ActualizacionEstadosPlanillaDTO</code>
     *        Representa los metadatos para actualizar los estados de archivos y registros de procesamiento de planilla PILA
     * 
     * @return <code>Boolean</code>
     *         resultado de actualizacion de los estados de archivos y registros de procesamiento de planilla PILA
     */
    @POST
    @Path("actualizarEstadosRegistroPlanilla")
    public Boolean actualizarEstadosRegistroPlanilla(ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO);

    
    @GET
    @Path("consultarDatosEmpleadorByRegistroDetallado")
    public Object[] consultarDatosEmpleadorByRegistroDetallado(@NotNull @QueryParam("idRegDetallado") Long idRegDetallado);

    @GET
    @Path("consultarDatosAfiliacionByRegistroDetallado")
    public Object[] consultarDatosAfiliacionByRegistroDetallado(@NotNull @QueryParam("idRegDetallado") Long idRegDetallado);
    /**
     * Método que se encarga de consultar el resultado de la ejecución simulada de la carga manual de un archivo de adición. HU-211-410
     * Procesar manualmente planillas PILA
     * @param idIndicePlanilla
     *        ID de la planilla consultada
     * @param userDTO
     *        Usuario del contexto que realiza la petición
     * @return <b>ResultadoValidacionRegistrosAdicionCorrecionDTO</b>
     *         Listado de aportes del aportante
     * @autor jocampo
     */

    @GET
    @Path("consultarResultadoProcesamientoPlanillaAdicion")
    public ResultadoValidacionRegistrosAdicionCorrecionDTO consultarResultadoProcesamientoPlanillaAdicion(
            @NotNull @QueryParam("idIndicePlanilla") Long idIndicePlanilla, @Context UserDTO userDTO);

    /**
     * Método que se encarga de registar el resultado de la simulación de adición para un resgistro dado. HU-211-410 Procesar manualmente
     * planillas PILA
     * @param registroPlanillaAdicion
     *        registro simulado que se desea registrar (aplicar)
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @autor jocampo
     */
    @POST
    @Path("aplicarRegistroPlanillasAdicion")
    public void aplicarRegistroPlanillasAdicion(@NotNull DetalleTablaAportanteDTO registroPlanillaAdicion, @Context UserDTO userDTO);

    /**
     * Método que se encarga de descartar el resultado de la simulación de adición para un resgistro dado. HU-211-410 Procesar manualmente
     * planillas PILA
     * @param registroPlanillaAdicion
     *        registro simulado que se desea descartar (no aplicar)
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     * @autor jocampo
     */
    @POST
    @Path("noAplicarRegistroPlanillasAdicion")
    public void noAplicarRegistroPlanillasAdicion(@NotNull DetalleTablaAportanteDTO registroPlanillaAdicion, @Context UserDTO userDTO);

    /**
     * Método encargado de consultar un registro de aporte detalle de adición y el resultado de simulación
     * @param idRegistroDetallado
     * @param userDTO
     * @return
     */
    @GET
    @Path("consultarComparativoAporte")
    public InformacionAporteAdicionDTO consultarComparativoAporte(@NotNull @QueryParam("idRegistroDetallado") Long idRegistroDetallado,
            @Context UserDTO userDTO);

    /**
     * Método encargado de consultar un registro de aporte detalle de adición y el resultado de simulación
     * 
     * @param idRegistroGeneral
     * @param fase
     * @param actualizarEstado
     * @param userDTO
     * @return
     */
    @GET
    @Path("verificarEstadoRegistrosAdicionCorreccion")
    public Map<String, String> verificarEstadoRegistrosAdicionCorreccion(@NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral,
            @NotNull @QueryParam("FasePila2Enum") FasePila2Enum fase, @NotNull @QueryParam("actualizarEstado") Boolean actualizarEstado,
            @Context UserDTO userDTO);

    /**
     * Método encargado de actualizar el estado de la planilla y de su bloque
     * @param numeroPlanilla
     */
    @POST
    @Path("actualizarEstadosRegistroPlanillaEstadoBloque")
    public void actualizarEstadosRegistroPlanillaEstadoBloque(@NotNull @QueryParam("numeroPlanilla") String numeroPlanilla);

    /**
     * Capacidad que permite la consulta de un registro general a partir de su ID
     * @param idRegistroGeneral
     *        ID del registro general a consultar
     * @return <b>RegistroGeneralModeloDTO</b>
     *         DTO con los datos del registro general consultado
     */
    @GET
    @Path("obtenerRegistroGeneral")
    public RegistroGeneralModeloDTO obtenerRegistroGeneral(@NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral);

    /**
     * Capacidad que obtiene la información relevante de registros generales y detallados para pantalla de Aportes de adición
     * @param criteriosSimulacion
     *        DTO que incluye los parámetros de la consulta
     * @return <b>ResultadoSimulacionAporteDetalladoDTO</b>
     *         DTO con la información a presentar en pantalla
     */
    @POST
    @Path("consultarDatosFase2SimuladaAdicion")
    public ResultadoSimulacionAporteDetalladoDTO consultarDatosFase2SimuladaAdicion(RegistrarCorreccionAdicionDTO criteriosSimulacion);

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
    @Path("aprobarAportesAdicion")
    public ResultadoAprobacionCorreccionAporteDTO aprobarAportesAdicion(RegistrarCorreccionAdicionDTO criteriosSimulacion,
            @Context UserDTO userDTO);

    /**
     * Metodo que permite la consulta del registro del archivo de planilla PILA por idIndicePlanilla
     * 
     * @param idIndicePlanilla
     *        <code>Long</code>
     *        El identificador de la planilla PILA.
     * 
     * @return <code>IndicePlanilla</code>
     *         El registro del archivo de planilla PILA
     */
    @GET
    @Path("consultarIndicePlanillaEntidad")
    public IndicePlanilla consultarIndicePlanillaEntidad(@QueryParam("idIndicePlanilla") Long idIndicePlanilla);

    /**
     * Capacidad que se encarga de revisar sí se han generado novedades para un registro general, sí no se incluyeron novedades en la
     * planilla, se marcan los registros detallados asociados como "registrados" para evitar que el proceso se quede bloqueado
     * @param idRegistroGeneral
     *        ID del registro general de la operación para verificar la validez del proceso
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     */
    @GET
    @Path("comprobarExistenciaNovedades")
    public void comprobarExistenciaNovedades(@QueryParam("idRegistroGeneral") @NotNull Long idRegistroGeneral, @Context UserDTO userDTO);

    /**
     * Capacidad para marcar una planilla como notificada
     * @param idRegistroGeneral
     *        ID del registro general de la operación para verificar la validez del proceso
     * @param userDTO
     *        DTO con la información del usuario que realiza la operación
     */
    @GET
    @Path("marcarPlanillaAsistidaNotificada")
    public void marcarPlanillaAsistidaNotificada(@QueryParam("idRegistroGeneral") @NotNull Long idRegistroGeneral,
            @Context UserDTO userDTO);

    /**
     * Metodo que permite ver los archivos procesados finalizados desde hace 7 dias
     * @return
     */
    @GET
    @Path("/verArchivosProcesadosFinalizadosOI")
    public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizadosOI(@Context UriInfo uri,
            @Context HttpServletResponse response);

    /**
     * Capacidad que permite la consulta de los estados por bloque de un índice de planilla a partir de su ID
     * 
     * @param idPlanilla
     *        ID del índice de planilla consultado
     * @return <b>EstadoArchivoPorBloqueModeloDTO</b>
     *         DTO que representa los estados por bloque del índice de planilla requerido
     */
    @GET
    @Path("consultarEstadosIndicePlanillaPorId")
    public EstadoArchivoPorBloqueModeloDTO consultarEstadosIndicePlanillaPorId(@QueryParam("idPlanilla") @NotNull Long idPlanilla);

    /**
     * Método que comprueba la existencia de procesos PILA sin finalizar
     * 
     * @return <b>Boolean</b>
     *         Indicador de existencia de procesos activos PILA
     * @author abaquero
     */
    @GET
    @Path("consultarProcesoPilaActivo")
    public Boolean consultarProcesoPilaActivo();

    /** -----Inicio métodos para vistas 360--------------------- */
    /**
     * Servicio encargado de consultar la información detallada de un aporte para las vistas 360
     * 
     * @param idRegistroDetallado
     *        identificador del registro detallado en bdat.
     * 
     * @return DetalleAporteVista360DTO con el detalle del aporte solicitado.
     * 
     * @author squintero
     */
    @GET
    @Path("/consultarDetalleAporte")
    public DetalleAporteVista360DTO consultarDetalleAporte(@QueryParam("idRegistroDetallado") Long idRegistroDetallado);

    /**
     * Servicio ENcargado De Consultar Los Aportes de un empleador o persona por anio
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param anio
     * @return
     */
    @GET
    @Path("/consultarAportePeriodo")
    public List<AportePeriodoCertificadoDTO> consultarAportePeriodo(@QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("anio") Short anio);
    /** -----Fin métodos para vistas 360------------------------ */
    
    /**
     * Capacidad que permite la consulta de los registros de aporte de las
     * planillas N para ser agrupadas
     * 
     * @param idIndicePlanilla
     *        <code>Long</code> código identificador de la planilla
     *        de la planilla
     * 
     * @return List<code>InconsistenciaRegistroAporteDTO</code> La lista de los
     *         registros con inconsistencias de archivos de planilla
     */
    @GET
    @Path("consultarRegistrosPlanillasParaAgrupar")
    public List<RegistroDetalladoModeloDTO> consultarRegistrosPlanillasParaAgrupar(
    		@NotNull @QueryParam("idIndicePlanilla") Long idIndicePlanilla,
    		@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
    		@QueryParam("numeroIdentificacion") String numeroIdentificacion
    );
    
    /**
     * Capacidad que permite agrupar o desagrupar los registros de aporte de las
     * planillas N para ser agrupadas
     * 
     * @param agrupar indica si se debe agrupar o eliminar el grupo
     * @param idsRegistrosDetallados registros que conforman el grupo
     */
    @POST
    @Path("gestionarRegistrosPlanillasParaAgrupar/{agrupar}/{idIndicePlanilla}")
	public void gestionarRegistrosPlanillasParaAgrupar(
			@PathParam("agrupar") Boolean agrupar,
			@PathParam("idIndicePlanilla") Long idIndicePlanilla,
			List<Long> idsRegistrosDetallados);

    /**
     * Método para consultar un registro general por ID de planilla PILA
     * @param idIndicePlanilla
     * @return
     */
    @GET
    @Path("consultarRegistroGeneralPorIdPlanilla")
    public RegistroGeneralModeloDTO consultarRegistroGeneralPorIdPlanilla(@QueryParam("idIndicePlanilla") @NotNull Long idIndicePlanilla);

    /**
     * Capacidad que lleva a cabo una rectificación de la asignación de valores de corrección para las
     * planillas N sin original
     * 
     * @param idPlanillaCorrecion
     *        ID de índice de planilla de corrección a verificar
     */
    @POST
    @Path("comprobarAsignacionValoresCorreccion")
    public void comprobarAsignacionValoresCorreccion(@QueryParam("idPlanillaCorrecion") @NotNull Long idPlanillaCorrecion);

    /**
     * Capacidad que lleva a cabo la aprobación de registros detallados evaluados como NO_OK o NO_VALIDADO_BD
     * en una planilla de corrección para que no impidan la creación de temporales de aportes y/o novedades
     * 
     * @param idPlanillaCorrecion
     *        ID de índice de planilla de corrección a verificar
     */
    @POST
    @Path("aprobarDetallesNoRegistrados")
    public void aprobarDetallesNoRegistrados(@QueryParam("idPlanillaCorrecion") @NotNull Long idPlanillaCorrecion);
    
    /**
	 * Servicio para la eliminación de las marcas de agrupación de registros de una planilla de corrección
	 * @param idPlanilla
     *        ID de índice de planilla de corrección a verificar
	 */
    @POST
    @Path("consultarAgrupaciones")
	public Response consultarAgrupaciones(@QueryParam("idPlanilla") @NotNull Long idPlanilla);

	/**
	 * Servicio para la eliminación de las marcas de agrupación de registros de una planilla de corrección
	 * @param idPlanilla
     *        ID de índice de planilla de corrección a verificar
	 */
    @POST
    @Path("limpiarAgrupacionesPlanillaCorreccion")
	public void limpiarAgrupacionesPlanillaCorreccion(@QueryParam("idPlanilla") @NotNull Long idPlanilla);
    
    /**
	 * Servicio para la actualización de Ejecucion GestionIncositencias
	 * @param Proceso
	 * @param Activo
	 * @param Estado
	 */
    @POST
    @Path("actualizarEjecucionGestion")
	public void actualizarEjecucionGestion(@QueryParam("proceso") @NotNull String proceso, @QueryParam("activo") @NotNull Boolean activo, @QueryParam("estado") @NotNull String estado);
    
    /**
	 * Servicio para la consultar el estado de Ejecucion GestionIncositencias
	 * @param Proceso
     * @return 
	 */
    @GET
    @Path("consultarEstadoEjecucionGestion")
	public Boolean consultarEstadoEjecucionGestion(@QueryParam("proceso") @NotNull String proceso);
    
    /**
     * Servicio que retorna la lista de indices planilla a partir de un listado de registros generales
     * @param regGenerales List<Long> regGenerales
     * @return List<Long> indice planilla PilaIndicePlanilla pipId
     */
    @POST
    @Path("indicesPlanillaPorRegistroGeneral")
	public List<Long> indicesPlanillaPorRegistroGeneral(List<Long> regGenerales);
	
	@POST
    @Path("/consultarAportanteReprocesoMundoUno")
	public List <PersonaModeloDTO> consultarAportanteReprocesoMundoUno(List<PlanillaGestionManualDTO> indicesPlanilla);
	
	@GET
	@Path("/definirBloqueAProcesarPilaMundoDos")
	public FasePila2Enum definirBloqueAProcesarPilaMundoDos(@QueryParam("idRegGeneralAdicionCorreccion") Long idRegGeneralAdicionCorreccion, @Context UserDTO userDTO);

	/**
     * Método encargado de consultar el id del registro general, asociado a una planilla en staging
     * 
     * @param numeroPlanilla
     * 			numero de la planilla a consultar.
     * 
     * @return Long con el id en tabla del registro general asociado a la planilla. Null en caso de no encontrar coincidencias.
     */
	@GET
	@Path("/consultarIdRegistroGeneralPorNumeroPlanilla")
	public Long consultarIdRegistroGeneralPorNumeroPlanilla(@QueryParam("numeroPlanilla") String numeroPlanilla);

	/**
	 * Verifica el estado actual de ejecución del proceso automático de planillas manuales (HU 410).  
	 * 
	 * @return boolean true si hay una ejecución activa del proceso, false en caso contrario.
	 */
	@GET
	@Path("/validarEstadoEjecucion410Automatico")
	public boolean validarEstadoEjecucion410Automatico();

	@POST
	@Path("/aprobarBloqueAportesAdicion")
	public void aprobarBloqueAportesAdicion(List<RegistrarCorreccionAdicionDTO> criteriosSimulacion, @Context UserDTO userDTO);

	@POST
	@Path("/aprobarBloqueNovedadesAdicionCorreccionCompuesto")
	public void aprobarBloqueNovedadesAdicionCorreccionCompuesto(List<Long> idsRegDetCorA,
			@QueryParam("idRegistroGeneralAdicionCorreccion") Long idRegistroGeneralAdicionCorreccion, @Context UserDTO userDTO);

	@POST
	@Path("/registrarNoRegistrarAdicionEnBloque")
	public void registrarNoRegistrarAdicionEnBloque(List<RegistrarCorreccionAdicionDTO> registrosAdicion, @Context UserDTO userDTO);

	@POST
	@Path("/registrarNoRegistrarCorreccionesEnBloque")
	public void registrarNoRegistrarCorreccionesEnBloque(List<RegistrarCorreccionAdicionDTO> registrarCorreccionDTO,
			@Context UserDTO userDTO);

	@GET
	@Path("/copiarAgruparPlanilla")
	public void copiarAgruparPlanilla(@QueryParam("idPlanillaOriginal") Long idPlanillaOriginal, @QueryParam("idPlanillaCorrecion") @NotNull Long idPlanillaCorrecion,
			@Context UserDTO userDTO);

	@POST
	@Path("/aprobarCambioAportesCorreccionesEnBloque")
	public List<ResultadoAprobacionCorreccionAporteDTO> aprobarCambioAportesCorreccionesEnBloque(List<RegistrarCorreccionAdicionDTO> listadoCriteriosSimulacion, @Context UserDTO userDTO);

	@POST
	@Path("/registrarPlanillaCandidataReproceso")
	public void registrarPlanillaCandidataReproceso(@QueryParam("idRegistroGeneral") Long idRegistroGeneral, @QueryParam("motivoBloqueo") String motivoBloqueo);
	
    @GET
    @Path("consultarEstadoRegistrosAdicionCorreccion")
    public Map<String, String> consultarEstadoRegistrosAdicionCorreccion(@NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral,
            @NotNull @QueryParam("FasePila2Enum") FasePila2Enum fase, @Context UserDTO userDTO);
    
    /**
     * Método que retorna cuales personas que se envía por parametro se encuentran en lista blanca de aportantes
     * @param aportantes
     * @param userDTO
     * @return
     */
    @POST
	@Path("/consultarListaBlancaAportantes")
    public List<ListasBlancasAportantes> consultarListaBlancaAportantes(List <PersonaModeloDTO> aportantes, @Context UserDTO userDTO);

    @GET
    @Path("ejecutarUSPporFasePila")
	public Long ejecutarUSPporFasePila(@QueryParam("idIndicePlanilla") Long idIndicePlanilla, @QueryParam("usuario") String usuario, @QueryParam("idTransaccion") Long idTransaccion, @QueryParam("reanudarTransaccion") boolean reanudarTransaccion, @QueryParam("fase") FasePila2Enum fase);
    
    @POST
    @Path("/exportarArchivosPila")
    public Response exportarArchivosPila(DatosArchvioReporteDTO datosReporte) throws IOException ;

    @POST
    @Path("reprocesarPlanillasM1")
    public List<Long> reprocesarPlanillasM1(List<Long> idPlanilla);

    @POST
    @Path("reprocesarPlanillasB3")
    public List<Long> reprocesarPlanillasB3();

    @GET
    @Path("/consultarlistasBlancas")
    public List<ListasBlancasAportantes> consultarlistasBlancas();    
    @POST
    @Path("/agregarEditarlistasBlancas")
    public Boolean agregarEditarlistasBlancas(ListasBlancasAportantes listaBlancaAportante);    
    @POST
    @Path("/editarEstadolistasBlancas")
    public void editarEstadolistasBlancas(ListasBlancasAportantes listaBlancaAportante);
}
