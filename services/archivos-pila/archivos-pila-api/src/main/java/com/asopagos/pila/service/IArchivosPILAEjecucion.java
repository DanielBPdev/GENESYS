package com.asopagos.pila.service;

import java.util.List;
import java.util.concurrent.Future;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.pila.dto.DatosProcesoFtpDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * Servicio de ejecución de procesos PILA
 * Este servicio se encarga de la ejecución asincrona de tareas de procesamiento de archivos
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 *
 */
public interface IArchivosPILAEjecucion {

    /**
     * Método para realizar la ejecución de las validaciones de OI en un listado de índices de planilla
     * 
     * @param indices
     *        Listado de índices de planilla OI a validar
     * @param usuario
     *        Nombre del usuario que inicia el proceso
     * @param idProcesoAgrupador
     * 		  ID del proceso que solicita la validación
     * @return Future<List<RespuestaServicioDTO>>
     *         Respuesta asincrona con el listado de las respuestas del servicio, uno por cada DTO de entrada
     */
    public void validarArchivosOIAsincrono(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador);

    /**
     * Método para realizar la ejecución de las validaciones de OI en un listado de índices de planilla de manera sincrona
     * 
     * @param indices
     *        Listado de índices de planilla OI a validar
     * @param usuario
     *        Nombre del usuario que inicia el proceso
     * @param idProcesoAgrupador
     * 		  ID del proceso que solicita la validación
     * @return List<RespuestaServicioDTO>
     *         Respuesta asincrona con el listado de las respuestas del servicio, uno por cada DTO de entrada
     */
    public void validarArchivosOISincrono(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador);

    /**
     * Método para realizar la ejecución de las validaciones de OI en un listado de índices de planilla OF
     * 
     * @param indices
     *        Listado de índices de planilla OF a validar
     * @param usuario
     *        Nombre del usuario que inicia el proceso
     * @return Future<List<RespuestaServicioDTO>>
     *         Respuesta asincrona con el listado de las respuestas del servicio, uno por cada DTO de entrada
     */
    public Future<List<RespuestaServicioDTO>> validarArchivosOF(List<IndicePlanillaOF> indices, String usuario);

    /**
     * Método para realizar la ejecución de las validaciones de OI en un listado de índices de planilla OF
     * de manera sincrona
     * 
     * @param indices
     *        Listado de índices de planilla OF a validar
     * @param usuario
     *        Nombre del usuario que inicia el proceso
     * @return List<RespuestaServicioDTO>
     *         Respuesta asincrona con el listado de las respuestas del servicio, uno por cada DTO de entrada
     */
    public List<RespuestaServicioDTO> validarArchivosOFSincrono(List<IndicePlanillaOF> indices, String usuario);

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
    public Long instanciarProceso(String usuarioProceso, TipoProcesoPilaEnum tipoProceso);

    /**
     * Método para finalizar el proceso
     * 
     * @param idProceso
     *        ID del proceso que se va a finalizar
     * @param estadoProceso
     *        Estado en el que se finaliza el proceso
     */
    public void finalizarProceso(Long idProceso, EstadoProcesoValidacionEnum estadoProceso);

    /**
     * Función para llevar a cabo la carga de un archivo en el índice de planillas
     * 
     * @param archivoPila
     *        DTO con la información del archivo que será cargado en el índice
     * @param user
     *        Usuario de carga
     * @param tipoCarga
     *        Tipo de carga que se está realizando
     * @param validarBloque0
     *        Indicador para determinar sí se debe ejecutar el B0 de validación luego de la carga del archivo
     * @return RespuestaServicioDTO
     *         DTO de respuesta con el resultado de la operación
     */
    public RespuestaServicioDTO cargarArchivo(ArchivoPilaDTO archivoPila, String user, TipoCargaArchivoEnum tipoCarga,
            Boolean validarBloque0);

    /**
     * Método para hacer consulta de los índices de planilla de OI para ser verificados
     * 
     * @return List<IndicePlanilla>
     *         Listado de los indices consultados
     */
    @Deprecated
    public List<IndicePlanilla> consultarIndicesParaProcesarOI();

    /**
     * Método para hacer consulta de los índices de planilla de OF que serán verificados como
     * parte de un proceso automático general programado
     * 
     * @return List<IndicePlanillaOF>
     *         Listado de los indices OF consultados
     */
    public List<IndicePlanillaOF> consultarIndicesParaProcesarOF();

    /**
     * Método asíncrono para la descarga y carga automática de archivos PILA desde FTP
     * 
     * @param idOperadorInformacion
     *        ID del operador de información seleccionado. Un valor nulo indica que el proceso se realiza para todos los OI
     * @param usuario
     *        Usuario que inicia el proceso
     * @param tipoCarga
     *        Tipo de carga con la cual serán marcados los archivos en el índice de planillas
     */
    public void descargarYCargarArchivosAsincrono(Long idOperadorInformacion, String usuario, TipoCargaArchivoEnum tipoCarga);

    /**
     * Método que se encarga de realizar en forma conjunta el proceso de descarga, carga y validación de planillas
     * 
     * @param idOperadorInformacion
     *        Código interno del Operador de Información
     * @param usuario
     *        Usuario que realiza la carga
     */
    public void descargarCargarYValidarArchivos(Long idOperadorInformacion, String usuario);

    /**
     * Metodo que permite el reprocesamiento de una planilla en una fase del proceso de PILA 2
     * 
     * @param idIndicePlanilla
     *        <code>Long</code>
     *        Identificador de índice de planilla a reprocesar
     * 
     * @param faseProceso
     *        <code>FasePila2Enum</code>
     *        Representa la fase de procesamiento de PILA 2
     * 
     * @param esSimulado
     *        <code>Boolean</code>
     *        Marca para saber que la ejecución es una simulación de proceso asistido
     * 
     * @param usuarioProceso
     *        <code>String</code>
     *        El usuario que realiza la ejecución de un proceso asistido
     */
    public void reprocesarPlanilla(Long idIndicePlanilla, FasePila2Enum faseProceso, Boolean esSimulado, String usuarioProceso);

    /**
     * Metodo que permite el reprocesamiento de una planilla en una fase del proceso de PILA 2 (Síncrono)
     * 
     * @param idIndicePlanilla
     *        <code>Long</code>
     *        Identificador de índice de planilla a reprocesar
     * 
     * @param faseProceso
     *        <code>FasePila2Enum</code>
     *        Representa la fase de procesamiento de PILA 2
     * 
     * @param esSimulado
     *        <code>Boolean</code>
     *        Marca para saber que la ejecución es una simulación de proceso asistido
     * 
     * @param usuarioProceso
     *        <code>String</code>
     *        El usuario que realiza la ejecución de un proceso asistido
     */
    public void reprocesarPlanillaSinc(Long idIndicePlanilla, FasePila2Enum faseProceso, Boolean esSimulado, String usuarioProceso);

    /**
     * Método que consulta sí el archivo validado es un aporte propio, para determinar si se debe buscar al aportante
     * como una persona o como una empresa
     * @param id
     *        ID del índice de planilla
     * @return <Boolean>
     *         Indicador para saber sí se trata de una aporte propio
     */
    public Boolean esAportePropio(Long id);

    /**
     * Método encargado de actualizar el estado de un archivo OI en un bloque específico
     * @param indicePlanilla
     *        Instancia del índice de planilla de Operador de Información
     * @param estado
     *        Estado del archivo a asignar
     * @param result
     *        DTO con el resultado de la validación
     * @param bloque
     *        Número del bloque que se está actualizando
     * @param accionPredeterminada
     *        Acción definida de manera predeterminada para el bloque
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación actualizado en caso de presentarse errores
     */
    public RespuestaValidacionDTO actualizarIndiceYEstadoBloque(IndicePlanilla indicePlanilla, EstadoProcesoArchivoEnum estado,
            RespuestaValidacionDTO result, Integer bloque, AccionProcesoArchivoEnum accionPredeterminada);

    /**
     * Método para realizar la ejecución de las validaciones de OI en un listado de índices de planilla de proceso por selección
     * 
     * @param indices
     *        Listado de índices de planilla OI a validar
     * @param usuario
     *        Nombre del usuario que inicia el proceso
     * @param idProcesoAgrupador
     *        ID del proceso que solicita la validación
     * @return Future<List<RespuestaServicioDTO>>
     *         Respuesta asincrona con el listado de las respuestas del servicio, uno por cada DTO de entrada
     */
    public void validarArchivosOISeleccion(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador);

    /**
     * Método para el proceso de PILA M1 para las planillas que pueden ser reanudadas
     * @param usuarioCarga
     */
    @Deprecated
    public void ejecutarPila1SinCarga(String usuarioCarga);
    
    /**
     * Método llamado desde una tarea programada para lanzar los archivos que se detuvieron en la conciliación
     * 
     * @param usuario
     */
    public void conciliarArchivosOIyOF(String usuario,List<Long> indicesPlanilla);
    
    /**
     * Método para iniciar variables llamado desde aportes composite
     */
    public void iniciarVariablesGenerales();

    /**
     * Método que ejecuta de forma síncrona el servicio de conciliarArchivosOIyOF(String usuario) 
     * 
     * @param usuario
     */
	public void conciliarArchivosOIyOFSincrono(String usuario,List<Long> indicesPlanilla);
	
	/**
	 * se encarga de iniciar el procesamiento de las planillas
	 * @param archivosDescargados
	 * @param datosControl
	 * @param ejecutarB0
	 * @param usuario
	 */
	public void cargarArchivosParalelo(List<ArchivoPilaDTO> archivosDescargados, DatosProcesoFtpDTO datosControl,
            Boolean ejecutarB0, String usuario);

        public void validarArchivosOISinValidacionAsincrono(List<IndicePlanilla> indices, String usuario, Long idProcesoAgrupador);
}
