package com.asopagos.pila.business.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.dto.DiasFestivosModeloDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoPlanillaDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.pila.dto.CriterioConsultaDTO;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;
import com.asopagos.pila.dto.ResumenNovedadVigenteDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface IConsultasModeloCore {
    /**
     * 
     * @param idPlanilla
     * @return BigDecimal
     */
    public BigDecimal totalAportesRelacionados(Long idRegistroGeneral);

    /**
     * 
     * @param idPlanilla
     * @return BigDecimal
     */
    public BigDecimal totalAportesRegistrados(Long idRegistroGeneral);

    /**
     * @param idRegistroGeneral
     * @return AporteDetalladoModeloDTO
     */
    public List<AporteDetalladoPlanillaDTO> detalleAportesPlanilla(Long idRegistroGeneral);

    /**
     * @param idRegistroGeneral
     * @param uri
     * @param response
     * @return AporteDetalladoModeloDTO
     */
    public List<AporteDetalladoPlanillaDTO> detalleAportesPlanillaPaginada(Long idRegistroGeneral, UriInfo uri, HttpServletResponse response);

    /**
     * @param idsRegDet
     * @return
     */
    public Map<Long, List<DetallePestanaNovedadesDTO>> obtenerNovedades(List<Long> idsRegDet);

    /**
     * capacidad para consultar los bancos (operadores financieros para pila),
     * parametrizados en el sistema
     * 
     * @return <code>List<Banco></code>
     *         La lista de los bancos paametrizada en el sistema.
     */
    public List<Banco> consultarBancos();

    /**
     * Método que realiza la consulta de los días festivos parametrizados en el sistema
     * @return <b>List<DiasFestivosModeloDTO></b>
     *         Listado de DTOs de los días festivos parametrizados
     */
    public List<DiasFestivosModeloDTO> consultarFestivos();

    /**
     * Método que consulta sí se encuentra un registro de RolAfiliado con fecha de ingreso o retiro mayos a la
     * indicada en la fecha de referencia respectiva
     * @param criteriosConsulta
     *        DTO con los criterios de búsqueda de la consulta
     * @return <b>Long</b>
     *         Cuenta de los registros que coinciden con los criterios establecidos
     */
    public Long consultarNovedadesIngRetCotizante(CriterioConsultaDTO criteriosConsulta);

    /**
     * Método para la consulta de un aporte detallado con base en el registro detallado que lo causó
     * @param idRegistroDetallado
     *        ID de registro detallado que causó el aporte
     * @return <b>AporteDetalladoModeloDTO</b>
     *         DTO que representa la entrada del aporte detallado encontrado, null en caso contrario
     */
    public AporteDetalladoModeloDTO consultarAporteDetalladoPorTransaccion(Long idRegistroDetallado);

    /**
     * Método encargado de actualizar un Aporte Detallado
     * @param aporteDetallado
     *        Entrada de AporteDetallado a actualizar
     */
    public void actualizarAporteDetallado(AporteDetallado aporteDetallado);

    /**
     * Método encargado de actualizar un Aporte General
     * @param aporteGeneral
     *        Entrada de AporteGeneral a actualizar
     */
    public void actualizarAporteGeneral(AporteGeneral aporteGeneral);

    /**
     * Método para la consulta de las novedades vigentes para un cotizante específico
     * @param tipoIdentificacionCotizante
     *        Tipo de identificación del cotizante consultado
     * @param numeroIdentificacionCotizante
     *        Número de identificación del cotizante consultado
     * @return <b>List<ResumenNovedadVigenteDTO></b>
     *         Listado de tipos de novedad y sus fechas
     */
    public List<ResumenNovedadVigenteDTO> consultarNovedadesVigentesCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante,
            String numeroIdentificacionCotizante);

    /**
     * Método encargado de determinar sí un cotizante a causado subsidio monetario
     * @param tipoIdentificacionCotizante
     *        Tipo de identificación del cotizante a consultar
     * @param numeroIdentificacionCotizante
     *        Número de identificación del cotizante a consultar
     * @param periodoAporte
     *        Período de aporte para el cual se hace la consulta
     * @return <b>Boolean</b>
     *         Indica sí el cotuzante causó o no subsidio para el período
     */
    public Boolean cotizanteConSubsidioPeriodo(TipoIdentificacionEnum tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
            String periodoAporte);

    /**
     * Método encargado de la consulta de un aporte general
     * @param idAporteGeneral
     *        Id del aporte general consultado
     * @return <b>AporteGeneralModeloDTO</b>
     *         DTO con la información del aporte general consultado
     */
    public AporteGeneralModeloDTO consultarAporteGeneralPorId(Long idAporteGeneral);

    /**
     * Método encargado de la identificación de los aportes orginales anulados por correcciones
     * @param idsRegistroDetallado
     *        Listado de IDs de Registro detallado para la búsqueda de aportes
     * @return <b>Map<Long, Boolean></b>
     *         Mapa que contiene el indicador de anulado para los registros detallados
     */
    public Map<Long, Boolean> consultarAnulacionAportesOriginales(List<Long> idsRegistroDetallado);
    
    /**
     * Método encargado de consultar un listado de personas
     */
	public List<PersonaModeloDTO> consultarPersonas(List<PersonaModeloDTO> personasABuscar);
	
	/**
	 * Método que retorna el contenidode la entidad de listas blancas
	 * @param numeroIds lista de números de identificación a consultar
	 * @return
	 */
	public List<ListasBlancasAportantes> consultarListasBlancasAportantes(List<String> numeroIds);

        public List<ListasBlancasAportantes> consultarlistasBlancas();

        public Boolean agregarEditarlistasBlancas(ListasBlancasAportantes listaBlancaAportante);

        public void editarEstadolistasBlancas(ListasBlancasAportantes listaBlancaAportante);
        }
