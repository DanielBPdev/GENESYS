package com.asopagos.aportes.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.RecaudoCotizanteDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.pila.staging.Transaccion;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de Staging<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Local
public interface IConsultasModeloStaging {

    /**
     * 
     * @param registroGeneral
     */
    public void crearRegistroGeneral(RegistroGeneral registroGeneral);

    /**
     * 
     * @param registroGeneral
     */
    public void actualizarRegistroGeneral(RegistroGeneral registroGeneral);

    /**
     * 
     * @param registroGeneral
     */
    public void crearRegistroDetallado(RegistroDetallado registroDetallado);

    /**
     * 
     * @param registroGeneral
     */
    public void actualizarRegistroDetallado(RegistroDetallado registroDetallado);

    /**
     * 
     * @param transaccion
     */
    public void crearTransaccion(Transaccion transaccion);

    /**
     * 
     * @param idSolicitud
     * @param limitarFinalizados
     * @return
     */
    public RegistroGeneral consultarRegistroGeneral(Long idSolicitud, Boolean limitarFinalizados);

    /**
     * 
     * @param idSolicitud
     * @param limitarFinalizados
     * @return
     */
    public RegistroGeneral consultarRegistroGeneralIdRegGen(Long idSolicitud, Boolean limitarFinalizados, Long idRegistroGeneral);
    
    /**
     * 
     * @param idRegistroGeneral
     * @return
     */
    public List<RegistroDetallado> consultarRegistroDetallado(Long idRegistroGeneral);

    

    /**
     * 
     * @param idSolicitud
     * @return
     */
    public RegistroGeneral consultarRegistroGeneralId(Long idRegistroGeneral);

    /**
     * Método que consulta la información de un aporte detallado en el staging de PILA, por identificador
     * @param idRegistroDetallado
     *        Identificador del registro
     * @return Objeto <code>RegistroDetalladoModeloDTO</code> con la información del registro detallado
     */
    RegistroDetalladoModeloDTO consultarRegistroDetalladoPorId(Long idRegistroDetallado);

    /**
     * Método que consulta la lista de novedades en estado RECHAZADA para un cotizante
     * @param idRegistroDetallado
     *        Identificador del registro detallado
     * @param tiposTransaccionNovedadesRechazadas
     *        Lista de tipos de transacción de novedades rechazadas
     * @return Lista de novedades que cumplen con los parámetros de consulta
     */
    public List<NovedadCotizanteDTO> consultarNovedadesRechazadasCotizanteAporte(Long idRegistroDetallado,
            List<String> tiposTransaccionNovedadesRechazadas);

    /**
     * Método que actualiza el estado del aporte en la entidad de RegistroDetallado
     * @param idCotizantes
     */
    public void actualizarEstadoAporteRegistroDetallado(List<Long> idCotizantes);

    /**
     * Método que consulta las novedades rechazadas
     * @param fechaInicio
     *        Fecha de inicio para las novedades rechazadas
     * @param fechaFin
     *        Fecha fin para las novedades rechazadas
     * @param tipoIdentificacion
     *        Tipo de identificacion de la persona
     * @param numeroIdentificacion
     *        Número de identificación de la persona
     * @return Lista del objeto consultado
     */
    public List<Object[]> consultarNovedadesRechazadas(Date fechaInicio, Date fechaFin, TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion);

    /**
     * Método que actualiza el estado del archivo en la entidad de RegistroGeneral
     * @param idRegistroGeneral
     *        Identificador de la entidad RegistroGeneral
     */
    public void actualizarEstadoAporteRegistroGeneral(List<Long> idRegistroGeneral);

    /**
     * Metodo que consulta la informacion general del aportante en vista 360 empleador
     * @param idAporteGeneral
     *        identificador de aporte general
     * @param tipoIdentificacion
     *        tipo identificacion del aportante
     * @param numeroIdentificacion
     *        numero identificacion del aportante
     * @return un dto RegistroGeneralModeloDTO
     */
    public RegistroGeneralModeloDTO consultarRegistroGeneralPorDatosAportante(Long idRegistroGeneral,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

    /**
     * Consulta los registros que quedaron con las novedades procesadas y aplicadas
     * @param idRegistroGeneral
     * @return Lista de recaudo de cotizantes
     */
    public List<RecaudoCotizanteDTO> consultarRegistrosNovedadesAplicadas(Long idRegistroGeneral);

    /**
     * Consulta los registros que quedaron con las novedades no procesadas y guardadas
     * @param idRegistroGeneral
     * @return Lista de recaudo de cotizantes
     */
    public List<RecaudoCotizanteDTO> consultarRegistrosNovedadesGuardadas(Long idRegistroGeneral);

    /**
     * @param fechaFin
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    public List<Object[]> consultarNovedadesRechazadasRecientes(Date fechaFin, TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion);

    /**
     * Método para consultar el tipo de cotizante como trabajador independiente, más reciente, encontrado en un aporte
     * para una persona específica
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param periodoCalculo
     * @return <b>TipoCotizanteEnum</b>
     *         Tipo contizante del último aporte como independiente
     */
    public TipoCotizanteEnum consultarTipoCotizanteMasReciente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String periodoCalculo);

    /**
     * Método que consulta las novedades de un cotizante asociadas a un aporte
     * detallado
     * 
     * @param idAporteDetallado
     *        Número de operación de segundo nivel
     * @return Lista de <code>NovedadCotizanteDTO</code> con las novedades
     *         encontradas
     */
    public List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(Long idRegistroDetallado);

    /**
     * Método encargado de eliminar los registros detallados asociados a un registro general
     * (por motivo de simulación de aporte manual)
     * @param idRegistroGeneral
     *        ID del registro general
     */
    public void eliminarRegistrosDetalladosPorRG(Long idRegistroGeneral);

    /**
     * Método para determinar que la planilla de un registro general es una planilla manual o no
     * @param idRegistroGeneral
     * @return
     */
    public Boolean consultarEsPlanillaManual(Long idRegistroGeneral);

    /**
     * Método para la consulta de la presencia de novedades por listado de IDs de registro general
     * @param ids
     * @return
     */
    public Map<Long, ConsultaPresenciaNovedadesDTO> consultarPresenciaNovedadesPorRegistroGeneral(List<Long> ids);

    /**
     * Método encargado de completar la información de una aporte original para el caso de PILA
     * @param aporteOriginal
     * @return
     */
    public CuentaAporteDTO completarDatosAporteOriginal(CuentaAporteDTO aporteOriginal);
    
    /**
     * Método encargado de completar la información de una aporte original para el caso de PILA
     * @param registroGeneral 
     * @param aporteOrigi
     * @return a
     */
    public CuentaAporteDTO consultarDatosPlanillaAporte(Long registroGeneral);

    /**
     * Método encargado de eliminar un registro general por ID
     * @param idRegistroGeneral
     */
    public void eliminarRegistroGeneralId(Long idRegistroGeneral);

    /**
     * Método encargado de la consulta de registros generales por listado de IDs
     * @param idsRegistrosGeneral
     * @return
     */
    public Map<Long, RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorListaId(List<Long> idsRegistrosGeneral);

    /**
     * @param idRegistroGeneral
     * @param tipoAfiliado
     * @param tipoId
     * @param numId
     * @return
     */
    public List<RegistroDetalladoModeloDTO> consultarRegistroDetalladoPorTipoAfiliado(Long idRegistroGeneral,
            TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoId, String numId);

    /**
     * @param numeroPlanilla
     * @return
     */
    public List<RegistroGeneralModeloDTO> consultarRegistroGeneralPorPlanilla(String numeroPlanilla);

	/**
	 * @param idsDetalle
	 */
	public void eliminarControlPlanilla(List<Long> idsDetalle);
}
