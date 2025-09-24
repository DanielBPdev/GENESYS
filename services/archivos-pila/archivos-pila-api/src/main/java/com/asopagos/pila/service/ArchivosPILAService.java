package com.asopagos.pila.service;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.pila.dto.OperadorInformacionDTO;
import com.asopagos.pila.dto.ProcesoPilaDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;

/**
 * Servicio de Archivos PILA <br>
 * Este servicio sera la fachada del orquestador de carga de los diferentes
 * archivos del proceso PILA
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 *
 */
@Path("archivosPILA")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ArchivosPILAService {

    /**
     * Método para la carga de archivos (Bloque Cero de validación y registro en
     * índice de planillas)
     * 
     * @param listaArchivoPila
     *        Listado de DTO con los archivos a cargar
     * @param userDTO
     *        Información del usuario
     * @return List<RespuestaServicioDTO> Listado de las respuestas del
     *         servicio, uno por cada DTO de entrada
     */
    @POST()
    @Path("cargarArchivosPilaManual")
    public List<RespuestaServicioDTO> cargarArchivoPilaManual(List<ArchivoPilaDTO> listaArchivoPila, @Context UserDTO userDTO);
    
    @POST()
    @Path("reprocesarB0")
    public void reprocesarB0(List<Long> listaArchivoPila, @Context UserDTO userDTO);

    @POST()
    @Path("reprocesarB0F")
    public void reprocesarB0F(List<Long> listaArchivoPila, @Context UserDTO userDTO);
    /**
     * Método para inactivar archivos del índice de planillas
     * 
     * @param listaArchivoPila
     *        Listado de DTO con los archivos a inactivar
     * @param userDTO
     *        Información del usuario
     * @return List<RespuestaServicioDTO> Listado de las respuestas del
     *         servicio, uno por cada DTO de entrada
     */
    @POST()
    @Path("eliminarArchivosPila")
    public List<RespuestaServicioDTO> inactivarArchivoPila(List<ArchivoPilaDTO> listaArchivoPila, @Context UserDTO userDTO);

    /***
     * Método para consultar los índices de planilla
     * 
     * @param userDTO
     *        Información del usuario
     * @return List<ArchivoPilaDTO> Listado de planillas consultadas
     */
    @GET()
    @Path("consultarPlanillas")
    public List<ArchivoPilaDTO> consultarPlanillas(@Context UserDTO userDTO);

    /**
     * Método para iniciar el procesamiento por selección para Operador de
     * Información
     * 
     * @param listaArchivoPila
     *        Listado de DTO con los archivos OI a procesar
     * @param userDTO
     *        Información del usuario
     * @return List<RespuestaServicioDTO> Listado de las respuestas del
     *         servicio, uno por cada DTO de entrada
     */
    @POST()
    @Path("procesarSeleccionOI")
    public void procesarSeleccionOI(List<ArchivoPilaDTO> listaArchivoPila, @Context UserDTO userDTO);

    /**
     * Método para consultar los operadores de información
     * 
     * @param userDTO
     *        Información del usuario
     * @return List<OperadorInformacion> Listado de los Operadores de
     *         Información encontrados en la BD
     */
    @GET
    @Path("consultarOperadoresInformacion")
    public List<OperadorInformacionDTO> consultarOperadoresInformacion(@Context UserDTO userDTO);

    /**
     * Método para iniciar la ejecución del proceso de descarga y procesamiento
     * automático de archivos de Operador de Información
     * 
     * @param operadorInformacionDTO
     *        DTO que indice el Operador de Información que será procesado
     * @param userDTO
     *        Información del usuario
     * @return RespuestaServicioDTO Respuesta general del servicio
     */
    @POST
    @Path("iniciarDescargaAutomatica")
    public RespuestaServicioDTO iniciarDescargaAutomatica(OperadorInformacionDTO operadorInformacionDTO, @Context UserDTO userDTO);

    /**
     * Método para consultar los procesos de validación activos
     * 
     * @param userDTO
     *        Información del usuario
     * @return List<ProcesoPilaDTO> Listado de los procesos de tipo validación
     *         en estado activo
     */
    @GET
    @Path("consultarProcesoValidacionActivo")
    public List<ProcesoPilaDTO> consultarProcesosValidacionActivos(@Context UserDTO userDTO);

    /**
     * Método para consultar los operadores de información relacionados a la CCF
     * 
     * @param codigoCcf
     *        Código de la Superintendencia del Subsidio Familiar para la
     *        CCF
     * @param userDTO
     *        Información del usuario
     * @return List<OperadorInformacion> Listado de los Operadores de
     *         Información encontrados en la BD relacionados a la CCF
     */
    @GET
    @Path("consultarOperadoresInformacionCcf")
    public List<OperadorInformacionDTO> consultarOperadoresInformacionCcf(String codigoCcf, @Context UserDTO userDTO);

    /**
     * Método que lleva a cabo la ejecución de proceso programado
     * 
     * @param operadorInformacionDTO
     *        DTO que indice el Operador de Información que será procesado
     * @param userDTO
     *        Información del usuario
     */
    @POST
    @Path("ejecutarProcesoDescargaCargaValidacionAutomatica")
    public void ejecutarProcesoAutomatico(OperadorInformacionDTO operadorInformacionDTO, @Context UserDTO userDTO);

    @POST
    @Path("validarArchivosOI")
    public void validarArchivosOI(@NotNull List<IndicePlanilla> indices, @Context UserDTO usuario);

    @POST
    @Path("validarArchivosOF")
    public void validarArchivosOF(@NotNull List<IndicePlanillaOF> indices, @Context UserDTO usuario);

    /**
     * Capacidad que permite el reprocesamiento de una planilla en una fase del proceso de PILA 2
     * 
     * @param archivoPilaDTO
     *        <code>ArchivoPilaDTO</code>
     *        Estructura de tranferencia de datos para el consumo de los servicios de PILA
     */
    @POST
    @Path("reprocesarPlanillaOI")
    public void reprocesarPlanilla(@NotNull ArchivoPilaDTO archivoPilaDTO, @Context UserDTO usuario);

    /**
     * Capacidad que permite el reprocesamiento de una planilla en una fase del proceso de PILA 2 (Síncrono)
     * 
     * @param archivoPilaDTO
     *        <code>ArchivoPilaDTO</code>
     *        Estructura de tranferencia de datos para el consumo de los servicios de PILA
     */
    @POST
    @Path("reprocesarPlanillaOISincrono")
    public void reprocesarPlanillaSincrono(@NotNull ArchivoPilaDTO archivoPilaDTO, @Context UserDTO usuario);

    /**
     * Servicio que calcula la fecha de vencimiento de un aportante para un período determinado
     * @param periodo
     *        Período para el cual se consulta la fecha de vencimiento
     * @param oportunidad
     *        Indicador de oportunidad en para el pago (Mes vencido o mes actual)
     * @param numeroDocumentoAportante
     *        Número de identificación del aportante
     * @param tipoArchivo
     *        Tipo de archivo evaluado (I para dependientes independientes - IP parpensionados)
     * @param cantidadPersonas
     *        Cantidad de trabajadores / independientes / pensionados relacionados
     * @param claseAportante
     *        Clase de aportante de acuerdo a PILA para el aportante
     * @param naturalezaJuridica
     *        Naturaleza jurídica del aportante
     * @return <b>Long</b>
     *         Tiempo en milisegundos correspondientes a la fecha de vencimiento del aporte
     */
    @GET
    @Path("calcularFechaVencimiento")
    public Long calcularFechaVencimiento(@QueryParam("periodo") @NotNull String periodo,
            @QueryParam("oportunidad") @NotNull PeriodoPagoPlanillaEnum oportunidad,
            @QueryParam("numeroDocumentoAportante") @NotNull String numeroDocumentoAportante,
            @QueryParam("tipoArchivo") @NotNull TipoArchivoPilaEnum tipoArchivo,
            @QueryParam("cantidadPersonas") @NotNull Integer cantidadPersonas,
            @QueryParam("claseAportante") @NotNull ClaseAportanteEnum claseAportante,
            @QueryParam("naturalezaJuridica") @NotNull NaturalezaJuridicaEnum naturalezaJuridica);

    /**
     * Servicio encargado de determinar el caso de normatividad para fecha de vencimiento de aporte
     * @param periodo
     *        Período para el cual se consulta la fecha de vencimiento
     * @param oportunidad
     *        Indicador de oportunidad en para el pago (Mes vencido o mes actual)
     * @param numeroDocumentoAportante
     *        Número de identificación del aportante
     * @param tipoArchivo
     *        Tipo de archivo evaluado (I para dependientes independientes - IP parpensionados)
     * @param cantidadPersonas
     *        Cantidad de trabajadores / independientes / pensionados relacionados
     * @param claseAportante
     *        Clase de aportante de acuerdo a PILA para el aportante
     * @param naturalezaJuridica
     *        Naturaleza jurídica del aportante
     * @return <b>Short</b>
     *         Día habil de vencimiento de aportes para el número de identificación consultado
     **/
    @GET
    @Path("calcularDiaHabilVencimientoAporte")
    public Short calcularDiaHabilVencimientoAporte(@QueryParam("numeroDocumentoAportante") @NotNull String numeroDocumentoAportante,
            @QueryParam("periodo") @NotNull String periodo, @QueryParam("tipoArchivo") @NotNull TipoArchivoPilaEnum tipoArchivo,
            @QueryParam("cantidadPersonas") @NotNull Integer cantidadPersonas,
            @QueryParam("claseAportante") @NotNull ClaseAportanteEnum claseAportante,
            @QueryParam("naturalezaJuridica") @NotNull NaturalezaJuridicaEnum naturalezaJuridica);

    /**
     * Servicio encargado de realizar el cálculo de valor de mora a fecha actual para un aporte
     * vencido de acuerdo al período del aportes y su fecha de vencimiento
     * @param periodo
     *        Período para el cual se consulta el valor de interés de mora
     * @param fechaVencimiento
     *        Tiempo en milisegundos correspondientes a la fecha de vencimiento del aporte
     * @param valorAporte
     *        Valor de la deuda presunta del aporte
     * @return <b>BigDecimal</b>
     *         Valor del interés de mora para el aporte consultado
     */
    @GET
    @Path("calcularInteresesDeMora")
    public BigDecimal calcularInteresesDeMora(@QueryParam("periodo") @NotNull String periodo,
            @QueryParam("fechaVencimiento") @NotNull Long fechaVencimiento, @QueryParam("valorAporte") @NotNull BigDecimal valorAporte);
    
    /**
     * Método que consulta sí el archivo validado es un aporte propio, para determinar si se debe buscar al aportante
     * como una persona o como una empresa
     * @param id
     *        ID del índice de planilla
     * @return <Boolean>
     *         Indicador para saber sí se trata de una aporte propio
     */
    @GET
    @Path("esAportePropio")
    public Boolean esAportePropio(@QueryParam("id") @NotNull Long id);

    /**
     * Servicio que indica sí se encuentra algún proceso de descarga de archivos automática web activo
     * @return <Boolean>
     *         Indicador para saber sí existe algún proceso de descarga automático web activo
     */
    @GET
    @Path("procesoDescargaActivo")
    public Boolean procesoDescargaActivo();
    
    /**
     * Servicio para la ejecución de PILA M1 para los archivos que cumplan los requisitos para ello
     */
    @POST
    @Path("/ejecutarPila1SinCarga")
    public void ejecutarPila1SinCarga(@QueryParam("usuario") String usuario);
    
    /**
     * Método llamado desde una tarea programada para lanzar los archivos que se detuvieron en la conciliación
     * 
     * @param userDTO
     */
    @POST
    @Path("/conciliarArchivosOIyOF")
    public void conciliarArchivosOIyOF(@Context UserDTO userDTO);
    
    /**
     * No usar este metodo creado solo para caso de emergencia
     * @param indicesPlanilla
     * @return 
     */
    @POST
    @Path("reprocesarByids")
    public void reprocesarMundo1(List<Long> indicesPlanilla);
    
    @POST
    @Path("/iniciarVariablesGenerales")
    public void iniciarVariablesGenerales();
    
    /**
     * Método que ejecuta de forma síncrona el servicio de conciliarArchivosOIyOF(String usuario) 
     * 
     * @param UserDTO
     */
    @POST
    @Path("/conciliarArchivosOIyOFSincrono")
    public void conciliarArchivosOIyOFSincrono(@Context UserDTO userDTO);

    /**
     * Método que ejecuta de forma síncrona el servicio de ReprocesarPlanillasPendientesConciliacionSincrono(String usuario) 
     * 
     * @param UserDTO
     */
    @POST
    @Path("/reprocesarPlanillasPendientesConciliacionSincrono")
    public void reprocesarPlanillasPendientesConciliacionSincrono(@Context UserDTO userDTO);
    
    // mejoras carga ftp
    
    /**
     * Método para instanciar el proceso PILA
     * 
     * @param usuarioProceso
     *        Usuario que ejecuta el proceso
     * @param tipoProceso
     *        Tipo de proceso que se está iniciando
     * @return Long
     *         ID del proceso registrado
     */
    @POST
    @Path("/instanciarProceso")
    public Long instanciarProceso(
    		@QueryParam("usuarioProceso") String usuarioProceso, 
    		@QueryParam("tipoProceso") TipoProcesoPilaEnum tipoProceso, 
    		@Context UserDTO userDTO);
    
    /**
     * Método para finalizar el proceso
     * 
     * @param idProceso
     *        ID del proceso que se va a finalizar
     * @param estadoProceso
     *        Estado en el que se finaliza el proceso
     */
    @POST
    @Path("/finalizarProceso")
    public void finalizarProceso(
    		@QueryParam("idProceso") Long idProceso, 
    		@QueryParam("estadoProceso") EstadoProcesoValidacionEnum estadoProceso,
    		@Context UserDTO userDTO);
    
    /**
     * Este metodo se encarga de realizar la consulta de operador de informacion de acuerdo al objeto
     * @param idOperadorInformacion
     *        ID del operador de información a consultar
     * @return List<ConexionOperadorInformacion>
     *         Listado de DTOs que contienen la información de conexión por cada operador de información que cumple los criteris de búsqueda
     * @throws ErrorFuncionalValidacionException
     */
    @GET
    @Path("/consultarDatosConexionOperadorInformacion")
    public List<ConexionOperadorInformacion> consultarDatosConexionOperadorInformacion(
    		@QueryParam("idProceso") Long idOperadorInformacion,
    		@Context UserDTO userDTO);
    
    /**
     * Método encargado de determinar los archivos en FTP que no han sido cargados previamente en el índice de planillas
     * @param archivosFTP
     *        Listado de archivos a descargar del FTP
     * @param tipoArchivos
     *        Tipo de archivos esperado
     *        0 -> Operador Información
     *        1 -> Operador Financiero
     *        2 -> Ambos
     * @return
     */
    @POST
    @Path("/filtrarExistentes")
    public List<ArchivoPilaDTO> filtrarExistentes(
    		List<ArchivoPilaDTO> archivosFTP, 
    		@QueryParam("tipoArchivos") Integer tipoArchivos,
    		@Context UserDTO userDTO);

     /**
     * Método encargado de determinar los archivos en FTP que no han sido cargados previamente en el índice de planillas
     * @param archivosFTP
     *        Listado de archivos a descargar del FTP
     * @param tipoArchivos
     *        Tipo de archivos esperado
     *        0 -> Operador Información
     *        1 -> Operador Financiero
     *        2 -> Ambos
     * @return
     */
    @POST
    @Path("/filtrarExistentesPlanillas")
    public List<ArchivoPilaDTO> filtrarExistentesPlanillas(
    		List<ArchivoPilaDTO> archivosFTP, 
    		@QueryParam("tipoArchivos") Integer tipoArchivos,
    		@Context UserDTO userDTO);
    
    /**
     * Método que inicia el procesamiento de las planillas desde el bloque indicado 
     * @param archivosDescargados
     * @param tipoCarga
     * @param ejecutarB0
     * @param idProceso
     * @param userDTO
     */
    @POST
    @Path("/filtrarExistentes")
    public void cargarArchivosParalelo(
    		List<ArchivoPilaDTO> archivosDescargados, 
    		@QueryParam("tipoCarga") TipoCargaArchivoEnum tipoCarga,
    		@QueryParam("ejecutarB0") Boolean ejecutarB0, 
			@QueryParam("idProceso") Long idProceso, 
			@Context UserDTO userDTO);


}
