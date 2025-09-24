package com.asopagos.pila.business.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import com.asopagos.dto.AportePeriodoCertificadoDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoNovedadModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.pila.DetalleTablaAportanteDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.pila.dto.ConsultaNovedadesPorRegistroDTO;
import com.asopagos.pila.dto.DetalleAporteVista360DTO;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de Staging <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface IConsultasModeloStaging {
    /**
     * Metodo encargado de retornar los bloques de validacion por los cuales paso un archivo
     * 
     * @param idPlanilla
     * @return
     */
    public Long obtenerIdRegistroGeneral(Long idPlanilla);

    /**
     * Metodo encargado de retornar los bloques de validacion por los cuales paso un archivo
     * 
     * @param idPlanilla
     * @return
     */
    public List<RegistroDetallado> obtenerRegistroDetallado(Long idRegistroGeneral);

    /**
     * 
     * @return
     */
    public String[] obtenerParametrosIndependientes();
    
    /**
     * Método encargado de la consulta del estado general de validación de una planilla y sus registros de acuerdo
     * a PILA mundo 2
     * 
     * @param idIndicePlanilla
     *        Número de planilla a consultar
     * @return <b>RespuestaConsultaEmpleadorDTO</b>
     *         DTO con el resultado de la consulta
     */
    public RespuestaConsultaEmpleadorDTO consultarEstadoGeneralPlanilla(Long idIndicePlanilla);

    /**
     * Método encargado de la consulta del estado detallado de validación de una planilla y sus registros de acuerdo
     * a PILA mundo 2
     * 
     * @param idIndicePlanilla
     *        identificador de la planilla a consultar
     * @return <b>List<DetalleTablaAportanteDTO></b>
     *         Listado de DTOs con el resultado de la consulta
     */
    public List<DetalleTablaAportanteDTO> consultarEstadoDetalladoPlanilla(Long idIndicePlanilla);

    /**
     * Método encargado de la consulta completa de los registros detallados de un número de planilla específico
     * @param idPlanilla
     *        ID de la planilla consultada
     * @param ignorarA
     *        Indica que en el resultado se ignoren los registros con corrección A
     * @param usarInicial
     *        Indica que se desea obtener el valor original del regisro antes de devoluciones y correcciones
     * @param discriminar
     *        Indica que se discriminan los resultados por evaluación como aporte anulado (<code>TRUE</code>), con evaluación diferente a
     *        aporte anulado (<code>FALSE</code>) o no se discrimina (<code>NULL</code>)
     * @return <b>List<RegistroDetalladoModeloDTO></b>
     *         Listado de los registros detallados del la planilla
     */
    public List<RegistroDetalladoModeloDTO> consultarRegistrosDetalladosPorIdPlanilla(Long idPlanilla, Boolean ignorarA,
            Boolean usarInicial, Boolean discriminar);

    /**
     * Método para la actualización de un registro detallado
     * @param registroDetallado
     *        Registro detallado a actualizar
     */
    public void actualizarRegistroDetallado(RegistroDetallado registroDetallado);

    /**
     * Metodo que permite la consulta del registro general de procesamiento de la planilla por idRegistroGeneral
     * 
     * @param idRegistroGeneral
     *        <code>RegistroGeneral</code>
     *        El identificador del registro general.
     * 
     * @return <code>RegistroGeneral</code>
     *         El registro general consultado
     */
    public RegistroGeneral consultarRegistroGeneral(Long idRegistroGeneral);

    /**
     * Metodo que permite la actualizacion del registro general de procesamiento de la planilla
     * 
     * @param registroGeneral
     *        <code>RegistroGeneral</code>
     *        La entidad que representa el registro general de procesamiento de la planilla
     * 
     * @return <code>RegistroGeneral</code>
     *         La entidad que representa del registro general de procesamiento de la planilla modificada
     */
    public RegistroGeneral actualizaRegistroGeneral(RegistroGeneral registroGeneral);

    /**
     * Método que consulta una entrada específica de registro detallado con base en su ID
     * @param idRegistroDetallado
     *        ID del registro detallado a consultar
     * @return <b>RegistroDetalladoModeloDTO</b>
     *         DTO con el contenido del registro detallado consultado
     */
    public RegistroDetalladoModeloDTO obtenerRegistroDetalladoEspecifico(Long idRegistroDetallado);

    /**
     * Método que consulta una entrada específica de registro detallado con base en su ID
     * @param idRegistroDetallado
     *        ID del registro detallado a consultar
     * @return <b>RegistroDetalladoModeloDTO</b>
     *         DTO con el contenido del registro detallado consultado
     */
    public RegistroDetallado obtenerEntidadRegistroDetalladoEspecifico(Long idRegistroDetallado);
    
    /**
     * Consulta un registro detallado segun su identificador
     * @param idRegistroDetallado
     *        identificador del registro detallado
     * @return registro detallado
     */
    public RegistroDetallado consultarRegistroDetallado(Long idRegistroDetallado);

    /**
     * Actualiza el estado del registro detallado indicado
     * @param registroDetallado
     *        a modificar
     */
    public void actualizaRegistroDetallado(RegistroDetallado registroDetallado);

    /**
     * Consulta el registro general por el número de planilla indicado
     * @param numeroPlanilla
     * @return
     */
    public RegistroGeneral consultarRegistroGeneralPorNumeroPlanilla(String numeroPlanilla);

    /**
     * Método que consulta un listado de registros detallados con base en un ID de registro general
     * @param idRegistroGeneral
     *        ID del registro general para la consulta
     * @return <b>List<RegistroDetalladoModeloDTO></b>
     *         Listado de registros detallados correspondientes al ID de registro general
     */
    public List<RegistroDetalladoModeloDTO> consultarRegistrosDetalladosPorRegistroGeneral(Long idRegistroGeneral);

    /**
     * Método que consulta la info básica de los registros detallados asociados a un id de registro general
     * @param idRegistroGeneral
     *        ID del registro general para la consulta
     * @return <b>List<RegistroDetalladoModeloDTO></b>
     *         Listado de registros detallados correspondientes al ID de registro general
     */
    public List<RegistroDetallado> consultarInfoBasicaRegistrosDetalladosPorRegistroGeneral(Long idRegistroGeneral);
    
    
    /**
     * Método empleado para la consulta de las novedades encontradas en un registro detallado
     * @param idRegistroDetallado
     *        ID del registro detallado para el cual se hace la consulta
     * @return <b>List<RegistroDetalladoNovedadModeloDTO></b>
     *         Listado de los DTO con la información consultada
     */
    public List<RegistroDetalladoNovedadModeloDTO> consultarNovedadesRegistroDetallado(Long idRegistroDetallado);

    /**
     * Método encargado de la actualización de un listado de novedades detalladas
     * @param novedadesDetalladas
     *        Listado de novedades detalladas para actualizar
     */
    @Deprecated
    public void actualizarNovedadesDetalladas(List<RegistroDetalladoNovedadModeloDTO> novedadesDetalladas);

    /**
     * Método encargado de la consulta de los IDs de registros detallado de un registro general junto a la cantidad de novedades que tiene
     * asociadas
     * @param idRegistroGeneral
     *        ID de Registro General a consultar
     * @return <b>List<ConsultaNovedadesPorRegistroDTO></b>
     *         Listado con los DTO del resultado obtenido
     */
    public List<ConsultaNovedadesPorRegistroDTO> consultarCantidadNovedadesPorRegistroDetallado(Long idRegistroGeneral);

    /**
     * Método encargado de marcar como registrados para novedades a los cotizantes que no presentan novedades
     * @param registrosSinNovedad
     *        Listado de identificadores de registro detallado para marcar como registrado para novedades
     */
    public void marcarRegistrosSinNovedades(List<Long> registrosSinNovedad);

    /**
     * Método para la consulta de un registro general a manera de DTO
     * @param idRegistroGeneral
     *        ID de Registro General a consultar
     * @return <b>RegistroGeneralModeloDTO</b>
     *         DTO del registro general consultado
     */
    public RegistroGeneralModeloDTO consultarRegistroGeneralDTO(Long idRegistroGeneral);

    /**
     * Método que consulta los datos de las novedades de reintegro aplicadas de un determinado registro detallado.
     * @param idsRegDet
     *        Listado de identificadores de registro detallado.
     * @return Mapa con las listas con los datos de la novedades encontradas.
     */
    public Map<Long, List<DetallePestanaNovedadesDTO>> consultarNovedadReintegroAplicadaRegistroDetallado(List<Long> idsRegDet);
    
    
    /**-----Inicio métodos para vistas 360---------------------*/
    /**
     * Método encargado de consultar la información detallada de un aporte para las vistas 360
     * 
     * @param idRegistroDetallado
     *          identificador del registro detallado en bdat.
     * 
     * @return DetalleAporteVista360DTO con el detalle del aporte solicitado.
     * 
     * @author squintero
     */
    public DetalleAporteVista360DTO consultarDetalleAporte(Long idRegistroDetallado);
    
    /**
     * Método encargado de consultar los aportes por anio, resolviendo el tipo de afiliacion 
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param anio
     * @return
     */
    public List<AportePeriodoCertificadoDTO> consultarAportePeriodo(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Short anio);
    /**-----Fin métodos para vistas 360------------------------*/
    
    /**
     * Método que consulta la información de los aportes que contiene una planilla
	 * @param idIndicePlanilla
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public List<RegistroDetalladoModeloDTO> consultarRegistrosPlanillasParaAgrupar(Long idIndicePlanilla,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);
	
    /**
     * Método que consulta la información de los aportes que contiene una planilla
	 * @param idIndicePlanilla
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public byte[] consultarAgrupaciones(Long idIndicePlanilla);
	
	/**
	 * Métod que actualza el valor del grupo de los registros agrupados (Planilla N)
	 * @param idsRegistrosDetallados
	 * @param agrupar
	 * @param idIndicePlanilla
	 */
	public void gestionarRegistrosPlanillasParaAgrupar(List<Long> idsRegistrosDetallados, Boolean agrupar, Long idIndicePlanilla);
	
	/**
	 * Método que agrupa los registros de una planilla N sin original
	 * @param idIndicePlanilla
	 */
	public void agruparAutomaticamentePlanillaN(Long idIndicePlanilla);

    /**
     * Método para consultar un registro general por ID de planilla PILA
     * @param idIndicePlanilla
     * @return
     */
    public RegistroGeneralModeloDTO consultarRegistroGeneralPorIdPlanilla(Long idIndicePlanilla);

    /**
     * Método encargado de la consulta del listado de IDs de registro detallado asociados a una planilla PILA
     * @param idsPlanilla
     *        Listado de IDs de índice de planilla consultados
     * @param ignorarA
     *        Indicador para ignorar registros de corrección tipo A
     * @return <b>Map<Long, List<Long>></b> Mapa que contiene los datos por planilla con sus de IDs de
     *         registro detallado asociados
     */
    public Map<Long, List<Long>> consultarIdsRegistrosDetalladosPorIdPlanilla(List<Long> idsPlanilla, Boolean ignorarA);

    /**
     * Método encargado de marcar un grupo de registros detallados como aprobados para validaciones de Bloque 7
     * @param idsRegistrosPorAprobar
     *        Listado de los IDs de registro detallado a aprobar
     */
    public void marcarRegistrosDetalladosAprobados(List<Long> idsRegistrosPorAprobar);

	/**
	 * Método encargado de limpiar las agrupaciones realizadas en una planilla de corrección
	 * @param idPlanilla
	 * 		  ID de la planilla a limpiar
	 */
	public void limpiarAgrupacionesPlanillaCorreccion(Long idPlanilla);
	
	/**
     * Método encargado de consultar el id del registro general, asociado a una planilla en staging
     * 
     * @param numeroPlanilla
     * 			numero de la planilla a consultar.
     * 
     * @return Long con el id en tabla del registro general asociado a la planilla. Null en caso de no encontrar coincidencias.
     */
    public Long consultarIdRegistroGeneralPorPlanilla(String numeroPlanilla);

	public boolean verificarExistenciaAportesPendientes(Long idRegGeneralAdicionCorreccion);

	public boolean verificarExistenciaNovedadesPendientes(Long idRegGeneralAdicionCorreccion);

	public void registrarPlanillaCandidataReproceso(Long idRegistroGeneral, String motivoBloqueo);
}
