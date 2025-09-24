package com.asopagos.aportes.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.TemAporteActualizadoModeloDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de
 * información en el modelo de datos de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Local
public interface IConsultasModeloPILA {

	/**
	 * 
	 * @param idTransaccion
	 * @return
	 */
	public void ejecutarBloqueStaging(Long idTransaccion);

	/**
	 * 
	 * @param idTransaccion
	 * @return
	 */
	public void simularFaseUnoPilaDos(Long idTransaccion);

	/**
	 * 
	 * @param idTransaccion
	 * @return
	 */
	public void ejecutarDeleteBloqueStaging(Long idTransaccion);

	/**
	 * 
	 * @param idRegistroGeneral
	 * @return
	 */
	public Object[] obtenerNotificacionRegistro(Long idRegistroGeneral);

	/**
	 * 
	 * @param idRegistroGeneral
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public Object[] obtenerNotificacionRegistroEspecial(Long idRegistroGeneral, String tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * 
	 * @param idTransaccion
	 * @param esProcesoManual
	 */
	public void simularFaseDosPilaDos(Long idTransaccion, Boolean esProcesoManual, Boolean esSimulado);

	/**
	 * 
	 * @param idTransaccion
	 * @param esProcesoManual
	 * @param esSimulado
	 */
	public void simularFaseTresPilaDos(Long idTransaccion, Boolean esProcesoManual, Boolean esSimulado);

	/**
	 * @param numPlanilla
	 * @return
	 */
	public IndicePlanilla consultarIndicePlanilla(String numPlanilla);

	public IndicePlanilla consultarIndicePlanillaNumeroAportante(String numPlanilla,Long registroDetallado);

	/**
	 * 
	 * @param idTransaccion
	 */
	public void organizarNovedadesSucursal(Long idTransaccion);

	/**
	 * consulta el estado del procesamiento de la planilla 
	 * @param idRegistroGeneral
	 * @return
	 */
	public Long buscarNotificacionPlanillasNCore(Long idRegistroGeneral);

	/**
	 * Método encargado de consultar un aporte temporal por el id de la
	 * transacción
	 * 
	 * @param idRegistroGeneral
	 *            id del registro general.
	 * @return lista de los aportes para ser relacionados o registrados.
	 */
	public List<AporteDTO> consultarAporteTemporal(Long idRegistroGeneral);

	/**
	 * Método encargado de consultar una novedad temporal por el id de la
	 * transacción
	 * 
	 * @param idRegistroGeneral
	 *            id del registro general.
	 * @return lista de las novedades para ser relacionados o registrados.
	 */
	public List<TemNovedad> consultarNovedad(Long idRegistroGeneral);

	/**
	 * Método que borra los datos de las tablas temporales
	 * <code>TemAporte</code>, <code>TemNovedad</code>,
	 * <code>TemCotizante</code> y <code>TemAportante</code>
	 * 
	 * @param idRegistroGeneral
	 *            El identificador del registro general asociado
	 */
	public void ejecutarDeleteTemporalesPILA(Long idRegistroGeneral);

	/**
	 * Método encargado de validar sí las novedades futuras son aplicables para
	 * una fecha dada
	 * 
	 * @param fechaValidacion
	 */
	public void ejecutarVerificacionNovedadesFuturas(Date fechaValidacion);

	/**
	 * Ejecuta el SP para validar la sucursal del aportante
	 * 
	 * @param idRegistroGeneral
	 *            Identificador del registro general
	 * @param sucursalPILA
	 *            Codigo de la sucursal de PILA ingresada desde el proceso de
	 *            correcciones
	 * @param sucursalPrincipal
	 *            Código de la sucursal principal que se encuentra registrada en
	 *            core
	 * @return StoredProcedureQuery
	 */
	public Boolean validarSucursal(Long idRegistroGeneral, String sucursalPILA, String sucursalPrincipal);

	/**
	 * Método que realiza el llamado al Sp ASP_ValidarProcesadoNovedades
	 * @param registroGeneral
	 * @return <b>Boolean</b> Validación novedades antes de notificar
	 */
	public Object validarProcesadoNovedades(Long registroGeneral);


	/**
	 * Método encargado de la consulta de las entradas existentes de la tabla
	 * TemAporteActualizado
	 * @param idsRegistrosOrigen 
	 * @param procesoManual 
	 * 
	 * @return <b>List<TemAporteActualizadoModeloDTO></b>
	 */
    public List<TemAporteActualizadoModeloDTO> consultarActualizacionesAporte(Boolean procesoManual, List<Long> idsRegistrosOrigen);

	/**
	 * Método encargadod e la eliminación por selección de entradas de la tabla
	 * TemAporteActualizado
	 * 
	 * @param listaIdPeticiones
	 *            Listado de IDs a eliminar
	 */
	public void eliminarActualizacionesAporte(Set<Long> listaIdPeticiones);

	/**
	 * Método que realiza el llamado al Sp USP_GET_RevalidarPila2Fase1
	 * 
	 * @param idRegistroGeneral
	 */
	public void ejecutarRevalidarPila2Fase1(Long idRegistroGeneral);

	/**
	 * Método para la consulta de los IDs de TemAporteProcesado existentes
	 * asociados a un listado de IDs de registro general
	 * 
	 * @param ids
	 * @return
	 */
	public List<Long> consultarIdsTemAporteProcesado(List<Long> ids);

	/**
	 * Método para crear registros de TemAporteProcesado para registros
	 * generales de aportes
	 * 
	 * @param mapaNovedades
	 */
	public void crearTemAporteProcesado(List<ConsultaPresenciaNovedadesDTO> mapaNovedades);

	/**
	 * Método encargado de la consulta de índices de planilla PILA a partir de
	 * un listado de IDs
	 * 
	 * @param idsPlanilla
	 * @return
	 */
	public List<IndicePlanillaModeloDTO> consultarIndicesPlanillaPorIds(List<Long> idsPlanilla);

	/**
	 * Método que consulta los aportes que se encuentran en la información
	 * temporal de pila sin registrar o relacionar
	 * 
	 * @return
	 */
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionAportesProcesar();

	/**
	 * Método que consulta las novedades que se encuentran en la información
	 * temporal de pila sin registrar o relacionar
	 * 
	 * @return
	 */
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesar();

	/**
	 * Método que consulta los aportes de las planillas que se encuentran en la
	 * información temporal de pila sin registrar o relacionar
	 * 
	 * @param planillaAProcesar
	 * @param pagina
	 * @return
	 */
	public List<AporteDTO> consultarAportesPlanillasRegistrarProcesar(Long planillaAProcesar, Integer pagina, Integer tamanoPaginador);


	/**
	 * Método que consulta las novedades de las planillas que se encuentran en la
	 * información temporal de pila sin registrar o relacionar
	 * 
	 * @param planillaAProcesar
	 * @return
	 */
	public Object construirNovedadesPlanillasRegistrarProcesar(Long planillaAProcesar);

	/**
	 * Método que consulta las novedades de las planillas que se encuentran en la
	 * información temporal de pila sin registrar o relacionar
	 * 
	 * @param planillaAProcesar
	 * @param pagina
	 * @return
	 */
	public List<NovedadesProcesoAportesDTO> consultarNovedadesPlanillasRegistrarProcesar(Long planillaAProcesar, Integer pagina);

	/**
	 * Método para eliminar contenido de tablas temporales por listado de IDs de
	 * registro detallado
	 * 
	 * @param sonAportes
	 *            Indica que se quiere eliminar temporales relacionadas con
	 *            aportes, caso contrario, elimina temporales relacionadas con
	 *            novedades
	 * @param idsDetalle
	 *            Listado de IDs de registro detallado a eliminar
	 */
	public void eliminarTemporales(Boolean sonAportes, List<Long> idsDetalle);

	/**
	 * Método para la actualización de los registros de temAporteProcesado luego del 
	 * proceso de novedades
	 */
	public void actualizarTemAporteProcesado();
        
        /**
	 * Método para la actualización de los registros de temAporteProcesado luego del 
	 * proceso de novedades por planilla
         * @param idPlanilla identificador de pilaindice planilla
	 */
	public void actualizarTemAporteProcesadoByIdPlanilla(Long idPlanilla);

	/**
	 * Método para la consulta de TemAporteProcesado válidos para datos de comunicado
	 * @return
	 */
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicado();
        
        /**
	 * Método para la consulta de TemAporteProcesado válidos para datos de comunicado
	 * @return
	 */
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicadoByIdPlanilla(Long idPlanilla);

	/**
	 * Método para eliminar temAporteProcesado
	 * @param idsDetalle
	 */
	public void eliminarTemAporteProcesado(List<Long> idsDetalle);

	/**
	 * Método para marcar registros de TemAporte o TemNovedad como en proceso
	 * @param infoPlanillas
	 * @param esAporte
	 * @param enProceso
	 */
	public void marcarTemporalEnProceso(List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas, Boolean esAporte, Boolean enProceso);
        
        /**
	 * Método que consulta los aportes que se encuentran en la información
	 * temporal de pila sin registrar o relacionar
	 * 
	 * @return
	 */
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionAportesProcesarByIdPlanilla(Long idPlanilla);
        
        /**
	 * Método que consulta las novedades que se encuentran en la información
	 * temporal de pila sin registrar o relacionar
	 * 
	 * @return
	 */
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesarByIdPlanilla(Long idPlanilla);

	/**
	 * Método que consulta que consulta las planillas de correccion con respecto 
	 * las originales
	 *  
	 * @return
	 */
	public List<CuentaAporteDTO> consultarPlanillasCorreccionN(List<String> listaPlanillaConCorrecion);

	/**
	 * Método que consulta las novedades que se encuentran en la información
	 * temporal de pila sin registrar o relacionar q son novedades futuras a realizar
	 * 
	 * @return
	 */
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesarFuturas();
	
	/**
	 * Método que se encarga de consultar la información necesarioa para procesar un aportante
	 * 
	 * @param ididRegistroGeneral identificador del registro general de la planilla
	 */
    public List<AporteDTO> consultarDatosAportantesTemporales(Long idRegistroGeneral);
    
    /**
     * Método que consulta la lista de cotizantes por crear  
     * 
     * @param idRegistroGeneral
     * @return
     */
    public List<AporteDTO> consultarDatosCotizantesTemporalesPorCrear(Long idRegistroGeneral);
    
    /**
     * Retorna el número de aportes temporales existentes para una planilla
     * @param idPlanilla
     * @return
     */
    public Long contarTemAportesPendientes(Long idPlanilla);

	public  List<Long>  PilaIndicePlanilla();
	//

	public List<MovimientoIngresosDTO> consultarMovimientoHistoricosPila(List<MovimientoIngresosDTO> movimientos);

	public String consultarValorTotalAporteObligatorio(String idPlanilla);

	public String consultarValorMora(String idPlanilla);

	public String consultarValorTotalReacudo(String idPlanilla);

	public String consultarValorTarifaAportante(String idRegistroGeneral, String numeroIdentificacionAportante, String tipoIdentificacionAportante);

	public String consultarValorTarifaCotizante(String idPlanilla, String idRegistro);

	public String consultarValorTarifaAportanteCotizante(String idPlanilla, String numeroIdentificacion);

	public String consultarValorTarifaAportanteCotizanteTotal(String idPlanilla, String numeroIdentificacion);

	public String consultarValorTarifaAportanteCotizanteMora(String idPlanilla, String numeroIdentificacion);
}
