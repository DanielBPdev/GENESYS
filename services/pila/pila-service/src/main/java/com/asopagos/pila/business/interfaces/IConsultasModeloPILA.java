package com.asopagos.pila.business.interfaces;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.dto.modelo.TemAporteModeloDTO;
import com.asopagos.dto.modelo.TemNovedadModeloDTO;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.temporal.TemAporte;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.pila.dto.ArchivosProcesadosFinalizadosOFDTO;
import com.asopagos.pila.dto.BloquesValidacionArchivoDTO;
import com.asopagos.pila.dto.CabeceraDetalleArchivoDTO;
import com.asopagos.pila.dto.CriterioConsultaDTO;
import com.asopagos.pila.dto.CriteriosBusquedaArchivosProcesados;
import com.asopagos.pila.dto.DatosAportanteTemporalDTO;
import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe Buitrago </a>
 */
@Local
public interface IConsultasModeloPILA {

    /**
     * Cantidad total de archivos que fueron cargados en el sistema (por medio
     * de la ejecución de la “HU-211-387” o de la “HU-211-386”) y que aún no han
     * sido procesados (por medio de la ejecución de la “HU-211-391”), es decir
     * los archivos con “Estado” igual a “Cargado”.
     * 
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosPendientesPorProcesarInformacion();

    /**
     * Cantidad total de archivos cuyo procesamiento finalizó en las horas
     * transcurridas del día actual y las 48 horas de los 2 días calendario
     * anteriores, es decir los archivos con “Estado” igual a “Recaudo
     * notificado”
     * 
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosProcesoFinalizado();

    /**
     * Cantidad total de archivos que después de ser procesados presentaron
     * inconsistencias (hasta el “bloque 6 Conciliación O.I. vs O.F.” de las
     * validaciones del recaudo PILA)
     * 
     * @return Integer con la cantidad de archivos en gestion
     */
    public Long archivosInconsistentesOI();

    /**
     * Cantidad total de archivos que después de ser procesados presentaron
     * inconsistencias (después del “bloque 6 Conciliación O.I. vs O.F.” de las
     * validaciones del recaudo PILA)
     * 
     * @return Integer con la cantidad de archivos en gestion
     */
    public Long archivosBandejaGestionOI();

    /**
     * Cantidad total de archivos que fueron cargados en el sistema (por medio
     * de la ejecución de la “HU-211-387” o de la “HU-211-386”) y que aún no han
     * sido procesados
     * 
     * @return Integer con la cantidad de archivos en gestion
     */
    public Long archivosPendientesPorProcesarInformacionManualOI();

    /**
     * Cantidad total de archivos que fueron procesados manualmente en las horas
     * transcurridas del día actual y las 48 horas de los 2 días calendario
     * anteriores (por medio de la ejecución de la “HU-211-400 Notificar
     * resultados de recaudo PILA”), es decir los archivos con “Estado” igual a
     * “Recaudo notificado - manual”.
     * 
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosProcesoFinalizadoManual();

    /**
     * Cantidad total de archivos que fueron descargados en las horas transcurridas del día actual y las 24 horas del día hábil anterior,
     * con “Estado” igual a “Cargado” (por medio de la ejecución de la “HU-211-387” o de la “HU-211-386”).
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosCargados();

    /**
     * Cantidad total de archivos que fueron cargados en el sistema en las horas transcurridas del día actual y las 24 horas del día
     * calendario anterior (ejecutando la “HU-211-387” o la “HU-211-386”) y que aún no han sido procesados
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosEnProcesoControl();

    /**
     * Cantidad total de archivos cuyo procesamiento finalizó en las horas transcurridas del día actual y las 24 horas del día hábil
     * anterior, es decir los archivos con “Estado” igual a “Recaudo notificado” (“HU-211-400 Notificar resultados de recaudo PILA”)
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosProcesoFinalizadoControl();

    /**
     * Cantidad total de archivos que fueron procesados en las horas transcurridas del día actual y las 24 horas del día hábil anterior y
     * que presentaron inconsistencias (hasta el “bloque 6 Conciliación O.I. vs O.F.” de las validaciones del recaudo PILA – “HU-211-392”)
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosInconsistentesControl();

    /**
     * Cantidad total de archivos que fueron procesados en las horas transcurridas del día actual y las 24 horas del día hábil anterior y
     * que presentaron inconsistencias (después del “bloque 6 Conciliación O.I. vs O.F.” de las validaciones del recaudo PILA –
     * “HU-211-399”)
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosEnGestionControl();

    /**
     * Cantidad total de archivos que fueron cargados en el sistema (por medio de la ejecución de la “HU-211-387” o de la “HU-211-386”) y
     * que aún no han sido procesados (por medio de la ejecución de la “HU-211-410 Procesar manualmente planillas PILA”)
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosEnProcesoManualControl();

    /**
     * Cantidad total de archivos que fueron procesados manualmente en las horas transcurridas del día actual y las 48 horas de los 2 días
     * calendario anteriores (por medio de la ejecución de la “HU-211-400 Notificar resultados de recaudo PILA”)
     * @return Integer con la cantidad total de archivos
     */
    public Long archivosProcesoFinalizadoManualControl();

    /**
     * Permite realizar las consultas asociadas al operador de informacion en la HU-401
     * 
     * @param namedQuery
     *        Consulta
     * @param fechaDesde
     *        Fecha de inicio de la consulta
     * @return
     */
    public Long consultarArchivosOperadorFinanciero(String namedQuery, Date fechaDesde);

    /**
     * Método encargado de la consulta de los archivos procesados finalizados. HU-401
     * @param criterios
     *        DTO con los parámetros de consulta
     * @param response
     * @param uri
     * @return <b>List<ArchivosProcesadosFinalizadosOFDTO></b>
     *         Listado de los resultados que cumplen los criterios específicados, retorna lista vacía en caso de no
     *         presentar coincidencias
     */
    public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizados(CriteriosBusquedaArchivosProcesados criterios,
            UriInfo uri, HttpServletResponse response);

    /**
     * Método encargado de la consulta de los archivos procesados finalizados. HU-401
     * @param criterios
     *        DTO con los parámetros de consulta
     * @param response
     * @param uri
     * @return <b>List<ArchivosProcesadosFinalizadosOFDTO></b>
     *         Listado de los resultados que cumplen los criterios específicados, retorna lista vacía en caso de no
     *         presentar coincidencias
     */
    public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizadosManual(
            CriteriosBusquedaArchivosProcesados criterios, UriInfo uri, HttpServletResponse response);

    /**
     * Método encargado de la consulta de los archivos procesados finalizados, no aplica criterios de busqueda.
     * @param uri
     * @param response
     * @return
     */
    public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizados();

    /**
     * @param idsRegDet
     * @return
     */
    public List<DetallePestanaNovedadesDTO> obtenerTodasNovedades(Long idRegGen);
    
    /**
     * Metodo encargado de retornar los bloques de validacion por los cuales paso un archivo
     * 
     * @param idPlanilla
     * @return
     */
    public List<BloquesValidacionArchivoDTO> verDetalleBloquesValidacionArchivo(Long idPlanilla, TipoOperadorEnum tipoOperador);

    /**
     * Metodo encargado de retornar los bloques de validacion por los cuales paso un archivo
     * 
     * @param idPlanilla
     * @return
     */
    public CabeceraDetalleArchivoDTO verCabeceraDetalleBloquesValidacionArchivo(Long idPlanilla);

    /**
     * Metodo encargado de retornar los bloques de validacion por los cuales paso un archivo OF
     * 
     * @param idPlanilla
     * @return
     */
    public CabeceraDetalleArchivoDTO verCabeceraDetalleBloquesValidacionArchivoOF(Long idPlanilla);

    /**
     * Método encargado de retornar la cuenta de las planillas que se encuentran pendientes para la gestión manual del aporte
     * 
     * @return <b>Integer</b>
     *         Cantidad de planillas pendientes de gestión manual
     */
    public Integer consultarCantidadPlanillasPendientesgestionManual();

    /**
     * Método encargado de la consulta de las planillas que presentan un estado con acción para gestión asistida
     * @param criterios
     *        DTO con los parámetros de consulta
     * @param response
     * @param uri
     * @return <b>List<PlanillaGestionManualDTO></b>
     *         Listado de los resultados que cumplen los criterios específicados, retorna lista vacía en caso de no
     *         presentar coincidencias
     */
    public List<PlanillaGestionManualDTO> consultarPlanillasParaGestionManual(CriterioConsultaDTO criterios, UriInfo uri,
            HttpServletResponse response);

    /**
     * Método encargado de la consulta del número de planilla original de una planilla de corrección
     * @param numeroPlanilla
     *        Número de planilla de corrección consultado
     * @return <b>String</b>
     *         Número de la planilla original de una corrección
     */
    @Deprecated
    public String consultarNumeroPlanillaOriginal(String numeroPlanilla);

    /**
     * Método encargado de la actualización de índice de planilla y estado por bloque de PILA
     * 
     * @param idIndicePlanilla
     *        Identificador del indice de planilla a modificar
     * @param bloque
     *        Bloque de validación para actualizar
     * @param estado
     *        Estado de archivo a asignar
     * @param accion
     *        Acción para el bloque a actualizar
     */
    public void actualizarEstado(Long idIndicePlanilla, BloqueValidacionEnum bloque, EstadoProcesoArchivoEnum estado,
            AccionProcesoArchivoEnum accion);

    /**
     * Método encargado de consultar la fecha de proceso de una planilla PILA
     * 
     * @param idIndicePlanilla
     *        ID de índice de planilla a consultar
     * @return <b>Date</b>
     *         Fecha de procesamiento de la planilla consultada
     */
    public Date consultarFechaProcesoIndicePlanilla(Long idIndicePlanilla);

    /**
     * Método encargado de ejecutar el procedimiento almacenado que da inicio al procesamiento
     * de la fase 1 de PILA 2 para un índice de planilla determinado
     * 
     * @param idIndicePlanilla
     *        ID de índice de planilla a validar
     * @param usuario
     *        Usuario que invoca la ejecución
     * @param fase
     *        Fase desde la cual se inicia la ejecución de PILA 2
     */
    public void ejecutarUSPporFaseSimulada(Long idIndicePlanilla, String usuario, FasePila2Enum fase);

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
    public IndicePlanilla consultarIndicePlanilla(Long idIndicePlanilla);

    /**
     * Metodo que permite la consulta del registro del estado por bloque de planilla PILA por idIndicePlanilla
     * 
     * @param idIndicePlanilla
     *        <code>Long</code>
     *        El identificador de la planilla PILA.
     * 
     * @return <code>EstadoArchivoPorBloque</code>
     *         El registro del estado por bloque de planilla PILA
     */
    public EstadoArchivoPorBloque consultarEstadoArchivoPorBloque(Long idIndicePlanilla);

    /**
     * Metodo que permite la actualizacion del registro del archivo de planilla PILA
     * 
     * @param indicePlanilla
     *        <code>IndicePlanilla</code>
     *        La entidad que representa el archivo de la planilla PILA.
     * 
     * @return <code>IndicePlanilla</code>
     *         La entidad que representa el archivo de la planilla PILA modificada
     */
    public IndicePlanilla actualizarIndicePlanilla(IndicePlanilla indicePlanilla);

    /**
     * Metodo que permite la actualizacion del registro del estado por bloque de planilla PILA
     * 
     * @param estadoArchivoPorBloque
     *        <code>EstadoArchivoPorBloque</code>
     *        la entidad que representa El registro del estado por bloque de planilla PILA
     * @param historialEstado
     *        <code>Entrada de historial de estados</code>
     * @return <code>EstadoArchivoPorBloque</code>
     *         la entidad que representa El registro del estado por bloque de planilla PILA modificada
     */
    public EstadoArchivoPorBloque actualizarEstadoArchivoPorBloque(EstadoArchivoPorBloque estadoArchivoPorBloque,
            HistorialEstadoBloque historialEstado);

    /**
     * Método que actualiza el estado de habilitación de un archivo para ejecución Manual en PILA 2
     * 
     * @param idIndicePlanilla
     *        ID del índice de planilla a actualizar
     * @param nuevaMarca
     *        Nuevo valor para la marca de habilitación
     */
    public void actualizarHabilitacionPila2Manual(Long idIndicePlanilla, Boolean nuevaMarca);

    /**
     * Método encargado de consultar el subtipo de cotizante de in registro en tabla de persistencia
     * @param idRegistro2Pila
     *        ID del registro 2 a consultar
     * @return <b>Short</b>
     *         Código del subtipo de cotizante
     */
    public Short consultarSubtipoCotizante(Long idRegistro2Pila);

    /**
     * Método para la consulta de un registro temporal de aporte simulado a partir del ID del registro detallado
     * 
     * @param idRegistroDetallado
     *        ID del registro detallado asociado a la simulación de aporte
     * @return <b>TemAporteModeloDTO</b>
     *         DTO con la información del aporte temporal
     */
    public TemAporteModeloDTO contultarAporteTemporal(Long idRegistroDetallado);

    /**
     * Método encargado de eliminar una entrada de TemAporte
     * @param idRegistroDetallado
     *        ID de registro detallado asaociado a la entrada del TemAporte a eliminar
     */
    public void eliminarAporteTemporal(Long idRegistroDetallado);

    /**
     * Método para consultar las novedades temporales asociadas a un registro general
     * @param idRegistroDetallado
     *        ID de registro detallado para la consulta
     * @return <b>List<TemNovedadModeloDTO></b>
     */
    public List<TemNovedadModeloDTO> consultarNovedadesTemporalesPorRegistroDetallado(Long idRegistroDetallado);

    /**
     * Método encardado de cambiar el estado de simulación de un listado de novedades temporales para procesamiento por ESB
     * @param listaNovedadesTemporales
     *        Listado de novedades temporales por habilitar
     */
    public void actualizarNovedadesTemporales(List<TemNovedadModeloDTO> listaNovedadesTemporales);

    /**
     * Método encardado de eliminar un listado de novedades temporales
     * @param listaNovedadesTemporales
     *        Listado de noevdades temporales por eliminar
     */
    @Deprecated
    public void eliminarNovedadesTemporalesEspecificas(List<TemNovedad> listaNovedadesTemporales);

    /**
     * Método encargado de persistir la actualización de un aporte temporal
     * @param aporteTemporal
     *        Aporte temporal a actualizar
     */
    public void actualizarAporteTemporal(TemAporte aporteTemporal);

    /**
     * Método encargado de la consulta de los aportes temporales asociados a un registro general
     * @param idRegistroGeneral
     *        ID del registro general consultado
     * @return <b>List<TemAporteModeloDTO></b>
     *         Listado de los aportes temporales consultados
     */
    public List<TemAporteModeloDTO> consultarAportesTemporalesPorRegistroGeneral(Long idRegistroGeneral);

    /**
     * Método encargado de la consulta de las novedades temporales asociadas a un registro general
     * @param idRegistroGeneral
     *        ID del registro general consultado
     * @return <b>List<TemNovedadModeloDTO></b>
     *         Listado de las novedades temporales consultadas
     */
    public List<TemNovedadModeloDTO> consultarNovedadesTemporalesPorRegistroGeneral(Long idRegistroGeneral);

    /**
     * @param uri
     * @param response
     * @return
     */
    public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizadosOI(UriInfo uri, HttpServletResponse response);

    /**
     * Método encargado de la consulta de procesos de PILA por tipo (listado) y estado
     * @param tiposProceso
     *        Listado de los tipos de proceso consultados
     * @param estadoProceso
     *        Estado del proceso a buscar
     * @return <b>Boolean</b>
     *         Indicador de la precencia de procesos PILA por los criterios indicados
     * @author abaquero
     */
    public Boolean consultarExistenciaProcesosPilaPorEstadoYTipo(List<TipoProcesoPilaEnum> tiposProceso,
            EstadoProcesoValidacionEnum estadoProceso);

    /**
     * Método para la consulta de estados por bloque de una planilla por número y tipos de archivos solicitados
     * @param idPlanilla
     *        Número de la planilla consultada
     * @param listaTipos
     *        Listado de los tipos de archivos requeridos
     * @param estados
     *        Listado de los estados de archivos requeridos
     * @return <b>List<EstadoArchivoPorBloque></b>
     *         Listado de las entradas estado por bloque encontradas
     */
    public List<EstadoArchivoPorBloque> consultarEstadoArchivoPorBloquePorNumeroYTipo(Long idPlanilla, List<TipoArchivoPilaEnum> listaTipos,
            List<EstadoProcesoArchivoEnum> estados);

    /**
     * Método para la consulta del índice de planilla original de una corrección
     * @param idOriginal
     *        ID de índice de planilla de corrección
     * @return <b>Long</b>
     *         ID de índice de planilla original (sí existe y sí es único resultado)
     */
    public Long consultarIdIndicePlanillaOriginal(Long idOriginal);


    /**
     * Método encargado de ejecutar el procedimiento almacenado que copia los datos de la planilla 
     * de la fase 1 de PILA 2 para un índice de planilla determinado
     * 
     * @param idIndicePlanilla
     *        ID de índice de planilla a validar
     * @param usuario
     *        Usuario que invoca la ejecución
     */
    public void ejecutarUSPCopiarPlanillaporFaseSimulada(Long idIndicePlanilla, String usuario);

    
    /**
     * Método encargado de actulizar el estado de la ejecucion de gestion de inconsistencia
     */
	public void actualizarEjecucionGestion(String proceso, Boolean activo, String estado);

	/**
     * Método encargado de consultar el estado de la ejecucion de gestion de inconsistencia
     */
	public Boolean consultarEjecucionGestion(String proceso);
	
    /**
     * Método que retorna una lista de los pipId de PilaIndicePlanilla a partir de un grupo de registros generales
     * @param regGenerales List<Long> regGenerales  registros generales a consultar
     * @return List<Long> listado de los pipId relacionados a los registros generales
     */
    public List<Long> consultarIndicePlanillaRegistroGeneral(List<Long> regGenerales);


    /**
     * Verifica el estado actual de ejecución del proceso automático de planillas manuales (HU 410).  
     * 
     * @return boolean true si hay una ejecución activa del proceso, false en caso contrario.
     */
    public boolean validarEstadoEjecucion410();

    
    public Long ejecutarUSPporFaseSimulada410(Long idIndicePlanilla, String usuario, Long idTransaccion, boolean reanudarTransaccion, FasePila2Enum fase);
    
    public Long ejecutarUSPporFaseSimulada410Adicion(Long idIndicePlanilla, String usuario, Long idTransaccion, boolean reanudarTransaccion, FasePila2Enum fase);
    
    public Object[] consultarDatosEmpleadorByRegistroDetallado(Long idIndicePlanilla);
    public Object[] consultarDatosAfiliacionByRegistroDetallado(Long idIndicePlanilla);
    public List<Long> reprocesarPlanillasM1(List<Long> idIndicePlanilla);
    public List<Long> reprocesarPlanillasB3();


}
