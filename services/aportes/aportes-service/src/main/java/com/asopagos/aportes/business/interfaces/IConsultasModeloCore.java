package com.asopagos.aportes.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import javax.ejb.Local;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.asopagos.aportes.dto.ConsultaAporteRelacionadoDTO;
import com.asopagos.aportes.dto.ConsultaMovimientoIngresosDTO;
import com.asopagos.aportes.dto.ConsultaPlanillaResultDTO;
import com.asopagos.aportes.dto.CorreccionVistasDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosAfiliadoServiciosDTO;
import com.asopagos.aportes.dto.DatosAportanteDTO;
import com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO;
import com.asopagos.aportes.dto.DatosConsultaSubsidioPagadoDTO;
import com.asopagos.aportes.dto.DatosCotizanteIntegracionDTO;
import com.asopagos.aportes.dto.DetalleCorreccionAportanteVista360DTO;
import com.asopagos.aportes.dto.DetalleCorreccionCotizanteNuevoVista360DTO;
import com.asopagos.aportes.dto.DetalleCorreccionCotizanteVista360DTO;
import com.asopagos.aportes.dto.DetalleDevolucionCotizanteDTO;
import com.asopagos.aportes.dto.DetalleDevolucionVista360DTO;
import com.asopagos.aportes.dto.DevolucionVistasDTO;
import com.asopagos.aportes.dto.HistoricoNovedadesDTO;
import com.asopagos.aportes.dto.JuegoAporteMovimientoDTO;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDetalladoDTO;
import com.asopagos.aportes.dto.RecaudoCotizanteDTO;
import com.asopagos.aportes.dto.ResultadoDetalleRegistroDTO;
import com.asopagos.aportes.dto.ResultadoModificarTasaInteresDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.dto.AportanteDiaVencimientoDTO;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.dto.EmpresaDTO;
import com.asopagos.dto.HistoricoAportes360DTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.dto.modelo.TasasInteresMoraModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.Correccion;
import com.asopagos.entidades.ccf.aportes.DevolucionAporte;
import com.asopagos.entidades.ccf.aportes.DevolucionAporteDetalle;
import com.asopagos.entidades.ccf.aportes.InformacionFaltanteAportante;
import com.asopagos.entidades.ccf.aportes.MovimientoAporte;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.aportes.SolicitudAporte;
import com.asopagos.entidades.ccf.aportes.SolicitudCorreccionAporte;
import com.asopagos.entidades.ccf.aportes.SolicitudDevolucionAporte;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;

import javax.ws.rs.core.UriInfo;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Local
public interface IConsultasModeloCore {

	/**
	 * 
	 * @param solicitudAporteDTO
	 * @return
	 */
	public void crearSolicitudAporte(SolicitudAporte solicitudAporte);

	/**
	 * 
	 * @param idSolicitudGlobal
	 * @param estado
	 */
	public void actualizarSolicitud(SolicitudAporte solicitudAporte);

	/**
	 * 
	 * @param idSolicitud
	 * @return
	 */
	public SolicitudAporte consultarSolicitudAporte(Long idSolicitud);

	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public Long consultarOperadorInformacionCodigo(String codigo);

	/**
	 * 
	 * @param aporteGeneral
	 * @return
	 */
	public void crearAporteGeneral(AporteGeneral aporteGeneral);

	/**
	 * 
	 * @param fechaInicio
	 * @return
	 */
	public List<HistoricoNovedadesDTO> consultarHistoricoNovedades(Date fechaInicio, Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	/**
	 * 
	 * @param fileLoadedId
	 * @return
	 */
	public List<DefinicionCamposCargaDTO> buscarCamposArchivo(Long fileLoadedId);

	/**
	 * 
	 * @param idPlanilla
	 * @return
	 */
	public ConsultaPlanillaResultDTO consultarPlanilla(Long idPlanilla);

	/**
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public List<SolicitudAporte> consultarSolicitudAporte(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * 
	 * @param c
	 * @return
	 */
	public List<AporteGeneral> obtenerListaAportes(CriteriaQuery<AporteGeneral> c);

	/**
	 * Método que consulta la información de un aporte detallado, por
	 * idnetificador (número de operación de segundo nivel)
	 * 
	 * @param idAporteDetallado
	 *            Número de operación de segundo nivel
	 * @return Objeto <code>AporteDetallado</code> con la información del aporte
	 */
	AporteDetallado consultarAporteDetallado(Long idAporteDetallado);

	/**
	 * Método que consulta la información de una solicitud de aportes, por
	 * identificador del aporte general asociado
	 * 
	 * @param idAporteGeneral
	 *            Identificador del aporte general
	 * @return Un objeto <code>SolicitudAporte</code> con la información de la
	 *         solicitud
	 */
	SolicitudAporte consultarSolicitudAportePorIdAporte(Long idAporteGeneral);

	/**
	 * Método que consulta las novedades de un cotizante asociadas a un aporte
	 * detallado
	 * 
	 * @param idAporteDetallado
	 *            Número de operación de segundo nivel
	 * @return Lista de <code>NovedadCotizanteDTO</code> con las novedades
	 *         encontradas
	 */
	List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(Long idAporteDetallado);

	/**
	 * 
	 * @param aporteDetallado
	 */
	public void actualizarAporteDetallado(AporteDetallado aporteDetallado);

	/**
	 * 
	 * @param aporteDetallado
	 */
	public void crearAporteDetallado(AporteDetallado aporteDetallado);

	/**
	 * 
	 * @param infoFaltante
	 */
	public void crearInfoFaltante(InformacionFaltanteAportante infoFaltante);

	/**
	 * 
	 * @param infoFaltante
	 */
	public void actualizarInfoFaltante(InformacionFaltanteAportante infoFaltante);

	/**
	 * 
	 * @param idSolicitud
	 * @return
	 */
	public List<InformacionFaltanteAportante> consultarInformacionFaltanteId(Long idSolicitud);

	/**
	 * 
	 * @return
	 */
	public CriteriaBuilder obtenerCriteriaBuilder();

	/**
	 * Método que consulta el numero de aporte detallado anterior
	 * @param idAporteDetallado
	 * @return
	 */
	public Long consultarRegistroDetalladoAnterior(Long idAporteDetallado);

	/**
	 * Metodo que consulta el aporte obligatorio y los intereses de un aporte por 
	 * su aporte detallado
	 * 
	 * @param idAporteDetallado
	 * @return
	 */
	public List<BigDecimal> consultarAporteObligatorioInteres(Long idAporteDetallado);

	/**
	 * 
	 * @param c
	 * @return
	 */
	public List<SolicitudAporte> obtenerListaSolicitudes(CriteriaQuery<SolicitudAporte> c);

	/**
	 * 
	 * @return
	 */
	public List<Departamento> consultarDepartamentos();

	/**
	 * 
	 * @return
	 */
	public List<Municipio> consultarMunicipios();

	/**
	 * 
	 * @param tipoSolicitante
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public SolicitanteDTO consultarSolicitanteIndependientePensionado(
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * 
	 * @param tipoSolicitante
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public SolicitanteDTO consultarSolicitanteEmpleador(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	/**
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public List<SolicitanteDTO> consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public List<SolicitanteDTO> consultarPersonaEmpresaSolicitanteAporteGeneral(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	/**
	 * Método que consulta una solicitud de devolución de aportes por
	 * identificador de la solicitud global
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global
	 * @return Objeto <code>SolicitudDevolucionAporte</code> con la información
	 *         de la solicitud de devolución
	 */
	SolicitudDevolucionAporte consultarSolicitudDevolucionAporte(Long idSolicitudGlobal);

	/**
	 * Método que consulta una solicitud global por identificador
	 * 
	 * @param idSolicitud
	 *            Identificador de la solicitud global
	 * @return Objeto <code>Solicitud</code> con la información de la solicitud
	 *         hallada
	 */
	Solicitud consultarSolicitudGlobal(Long idSolicitud);

	/**
	 * Método que almacena o actuliza una solicitud de devolución de aportes
	 * 
	 * @param solicitudDevolucionAporte
	 *            La solicitud a almacenar/actualizar
	 * @return El identificador de la solicitud global creada/actualizada
	 */
	Long crearActualizarSolicitudDevolucionAporte(SolicitudDevolucionAporte solicitudDevolucionAporte);

	/**
	 * Método que consulta el movimiento de aporte por su identificador
	 * 
	 * @param idMovimientoAporte
	 * @return
	 */
	public MovimientoAporte consultarMovimientoAporte(Long idMovimientoAporte);

	/**
	 * Método que actualiza el movimiento aporte
	 * 
	 * @param movimientoAporte
	 */
	public void actualizarMovimientoAporte(MovimientoAporte movimientoAporte);

	/**
	 * Método que crea un movimiento aporte
	 * 
	 * @param movimientoAporte
	 */
	public void crearMovimientoAporte(MovimientoAporte movimientoAporte);

	/**
	 * Consulta las cuentas de aportes relacionadas a un id de aporte general
	 * 
	 * @param idsAporteGeneral
	 * @return
	 */
	public List<CuentaAporteDTO> consultarCuentasAporte(List<Long> idsAporteGeneral,TipoMovimientoRecaudoAporteEnum tipoRecaudo);

	/**
	 * Método que crea o actualiza una solicitud en la tabla
	 * <code>Solicitud</code>
	 * 
	 * @param solicitud
	 *            La solicictud a actualizar
	 * @return El identificador de la solicitud creada/actualizada
	 */
	Long crearActualizarSolicitudGlobal(Solicitud solicitud);

	/**
	 * Método que actualiza un conjunto de registros en la tabla
	 * <code>AporteDetallado</code>
	 * 
	 * @param listaAporteDetallado
	 *            Lista de registros a actualizar
	 */
	void actualizarAporteDetallado(List<AporteDetallado> listaAporteDetallado);

	/**
	 * Consulta para obtener personas.
	 * 
	 * @param campos
	 *            listado de los campos por los cuales se va a realizar la
	 *            consulta
	 * @param valores
	 *            listado de los valores con que se va a realizar la consulta
	 */
	public List<Persona> consultarPersonas(Map<String, String> campos, Map<String, Object> valores);

	/**
	 * Consulta para obtener <i><b>un cotizante</i></b> cuando se busca por
	 * idPersona, <i><b>con al menos un parámetro de búsqueda</b></i> durante un
	 * proceso de devolución de aportes.
	 * 
	 * @param consultarCotizante
	 * @param idAporte
	 * @param idPersonas
	 * @return List<CotizanteDTO>
	 */
	public List<CotizanteDTO> consultarCotizantesPorIdAporte(Long idAporte, List<Long> idPersonas);

	/**
	 * Consulta para obtener <i><b>los cotizantes</i></b> cuando <i><b>no se
	 * ingresan parámetros de búsqueda</b></i> durante un proceso de devolución
	 * de aportes.
	 * 
	 * @param idAporte
	 * @return List<CotizanteDTO>
	 */
	public List<CotizanteDTO> consultarCotizantesSinParametros(Long idAporte);

	/**
	 * Método que crea o actualiza una lista de registros en la tabla
	 * <code>MovimientoAporte</code>
	 * 
	 * @param listaMovimientoAporte
	 *            Lista de registros a crear/actualizar
	 */
	void actualizarMovimientoAporte(List<MovimientoAporte> listaMovimientoAporte);

	/**
	 * Método que almacena o actualiza una devolución de aportes
	 * 
	 * @param devolucionAporte
	 *            Los datos de la devolución a almacenar/actualizar
	 * @return El identificador del registro insertado/actualizado
	 */
	Long crearActualizarDevolucionAporte(DevolucionAporte devolucionAporte);

	/**
	 * Método encargado de consultar el empleador por tipo y número de
	 * identificación
	 * 
	 * @param tipoIdentificacion,
	 *            tipo de identificación
	 * @param numeroIdentificacion,
	 *            número de identificacion
	 * @return retorna el empleador encontrado
	 */
	public Empleador consultarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	/**
	 * Método encargado de consultar los aportes realizados por el empleador
	 * 
	 * @param estadoAfiliado,
	 *            estado de la afiliacion del empleador
	 * @param idEmpleador,
	 *            empleador a consultar los aportes
	 * @return retorna la lista de aportes realizados por el empleador
	 */
	public List<AporteDetallado> consultarAportesPorEmpleador(EstadoAfiliadoEnum estadoAfiliado, Long idEmpleador);

	/**
	 * Método encargado de consultar los aportes detallados por persona
	 * 
	 * @param tipoIdentificacion,
	 *            tipo de identificacion de la persona
	 * @param numeroIdentificacion,
	 *            numero de identificacion de la persona
	 * @return retorna la lista de aportes de la persona
	 */
	public List<AporteDetallado> consultarAportesDetalladosPorPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * Método encargado de consultar los aportes realizados al aportante por
	 * parte del empleador
	 * 
	 * @param tipoIdentificacion,
	 *            Tipo de identificación del aportante
	 * @param numeroIdentificacion,
	 *            número de identificación del aportante
	 * @return retorna la lista de aportes detallados
	 */
	public List<AporteDetallado> consultarAportesPorAportanteEmpleador(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * Método que representa la consulta para obtener el o los movimientos
	 * históricos de ingresos.
	 * 
	 * @param consultaMovimientosIngresos,
	 *            DTO con los datos de consulta.
	 * @return MovimientoIngresosDTO, DTO con los datos consultados.
	 */
	public List<MovimientoIngresosDTO> consultarMovimientoHistoricos(
			ConsultaMovimientoIngresosDTO consultaMovimientosIngresos);

	/**
	 * Método que se encarga de consultar un aporte general por id de registro.
	 * 
	 * @param idRegistroGeneral
	 *            id del registro general.
	 * @return aporte general consultado.
	 */
	@Deprecated
	public AporteGeneral consultarAporteGeneralPorRegistro(Long idRegistroGeneral);

	/**
	 * Método que consulta un registro en la tabla <code>AporteGeneral</code>,
	 * por id
	 * 
	 * @param idAporteGeneral
	 *            id del registro
	 * @return El registro consultado
	 */
	AporteGeneral consultarAporteGeneral(Long idAporteGeneral);

	/**
	 * Método que consulta los movimientos de aportes detallados
	 * 
	 * @param idsAporteGeneral
	 * @return
	 */
	public List<MovimientoIngresosDetalladoDTO> consultarMovimientoDetallado(Long idAporteGeneral);

	/**
	 * Método que crea o actualiza una solicitud de corrección de aportes
	 * 
	 * @param solicitudCorreccionAporte
	 *            Información de la solicitud a ser creada/actualizada
	 * @return El identificador de la solicitud global creada/actualizada
	 */
	Long crearActualizarSolicitudCorreccionAporte(SolicitudCorreccionAporte solicitudCorreccionAporte);

	/**
	 * Método que consulta un registro en la tabla
	 * <code>SolicitudCorreccionAporte</code>, por identificador de la solicitud
	 * global asociada
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global
	 * @return El registro consultado
	 */
	SolicitudCorreccionAporte consultarSolicitudCorreccionAporte(Long idSolicitudGlobal);

	/**
	 * Método que crea o actualiza un registro en la tabla
	 * <code>Correccion</code>
	 * 
	 * @param correccion
	 *            El registro a crear o actualizar
	 * @return El identificador del registro modificado
	 */
	Long crearActualizarCorreccion(Correccion correccion);

	/**
	 * Método encargado de consultar cotizantes por id de personas
	 * 
	 * @param idPersonas,
	 *            listado id de personas a consultar
	 * @return retorna la lista de contizantes encontrados
	 */
	public List<CotizanteDTO> consultarCotizantesPorPametro(List<Long> idPersonas);

	/**
	 * Método que crea o actualiza un movimiento de aportes
	 * 
	 * @param movimientoAporte
	 *            El registro a modificar
	 * @return El identificador del registro modificado
	 */
	Long crearActualizarMovimientoAporte(MovimientoAporte movimientoAporte);

	/**
	 * Método que crea o actualiza un registro en la tabla
	 * <code>AporteDetallado</code>
	 * 
	 * @param aporteDetallado
	 *            El registro a actualizar
	 * @return El identificador del registro modificado
	 */
	Long crearActualizarAporteDetallado(AporteDetallado aporteDetallado);

	/**
	 * Método que crea o actualiza un registro en la tabla
	 * <code>AporteGeneral</code>
	 * 
	 * @param aporteGeneral
	 *            El registro a actualizar
	 * @return El identificador del registro modificado
	 */
	Long crearActualizarAporteGeneral(AporteGeneral aporteGeneral);

	/**
	 * Método que crea o actualiza un registro en la tabla
	 * <code>DevolucionAporteDetalle</code>
	 * 
	 * @param devolucionAporteDetalle
	 *            El registro a actualizar
	 * @return El identificador del registro modificado
	 */
	Long crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalle devolucionAporteDetalle);

	/**
	 * Método que se encarga de consultar un aporte general por el id de Persona
	 * relacionado en un aporte detallado
	 * 
	 * @param idPersona
	 * @return
	 */
	public List<AporteGeneralModeloDTO> consultarAporteGeneralIdPersonaPeriodo(Long idPersona, String periodoAporte);

	/**
	 * Método que consulta el aportante relacionado a un aporte general
	 * 
	 * @param idPersona
	 * @param idEmpresa
	 * @return
	 */
	public Persona consultarAportante(Long idPersona);

	/**
	 * Método que consulta el aportante relacionado a un aporte general por
	 * medio de la empresa
	 * 
	 * @param idPersona
	 * @return
	 */
	public Persona consultarAportanteEmpresa(Long idEmpresa);

	/**
	 * Método que consulta la cantidad de movimientos relacionados a un aporte
	 * donde si tipo de ajuste sea DEVOLUCION o CORECCION_A_LA_BAJA
	 * 
	 * @param idsAporte
	 * @return
	 */
	public List<AporteGeneralModeloDTO> consultarAporteYMovimiento(List<Long> idsAporte);

	/**
	 * Método encargado de consultar si existe el aporte general y detallado
	 * 
	 * @param idAporteGeneral
	 *            es el identificador del aporte general
	 * @param idAporteDetallado
	 *            es el identificador del aporte detallado
	 * 
	 * @return Map<String,Boolean> mapa con dos boolean que indican si el aporte
	 *         general y el aporte detallado ya han sido procesados.
	 */
	public Map<String, Boolean> consultarEstadoAporte(Long idAporteGeneral, Long idAporteDetallado);

	/**
	 * Método que consulta la existencia de registros de solicitud corrección de
	 * aportes
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return List<SolicitudAporte>
	 */
	public List<SolicitudCorreccionAporte> consultarSolicitudCorrecionAporte(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * Método que consulta la existencia de registros de solicitud devolución de
	 * aportes
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return List<SolicitudDevolucionAporte>
	 */
	public List<SolicitudDevolucionAporte> consultarSolicitudDevolucionAporte(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * Método que consulta el id de un municipio por su codigo
	 * 
	 * @param codigoMunicipio
	 *            Codigo que representa el municipio
	 * @return idMunicipio Identificador de la entidad Municipio
	 */
	public Short buscarMunicipio(String codigoMunicipio);

	/**
	 * Método que consulta el cotizante
	 * 
	 * @param idPersona
	 *            Id de la Persona cotizante
	 * @return Persona retorna el cotizante
	 */
	public Persona consultarPersonaDetallado(Long idPersona);

	/**
	 * Método que consulta histórico de las novedades de retiro.
	 * 
	 * @param fechaInicio
	 *            del histórico.
	 * @param fechaFin
	 *            del histórico.
	 * @param tipoIdentificacion
	 *            tipo de identificación del trabajador
	 * @param numeroIdentificacion
	 *            número de identificación.
	 * @return listado del historico de novedades de retiro.
	 */
	public List<HistoricoNovedadesDTO> consultarHistoricoNovedadesRetiro(Date fechaInicio, Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	/**
	 * Método que consulta la lista de tipos de transacción de novedades
	 * rechazadas para un cotizante
	 * 
	 * @param idAporteDetallado
	 *            Identificador del aporte detallado, asociado al cotizante
	 * @return La lista de tipos de transacción en estado rechazado
	 */
	public List<String> consultarTipoTransaccionNovedadRechazadaCotizante(Long idAporteDetallado);

	/**
	 * Método que consulta los cotizantes por id de rol afiliado
	 * 
	 * @param idRoles
	 *            id de los roles afiliados.
	 * @return cotizantes consultados
	 */
	public List<CotizanteDTO> consultarCotizantesPorRol(List<Long> idRoles);

	/**
	 * Método que consulta las novedades de retiro.
	 * 
	 * @param idAporteDetallado
	 *            id del aporte detallado
	 * @param idPersona
	 *            id de la persona.
	 * @return lista de las novedades de retiro.
	 */
	public List<NovedadCotizanteDTO> consultarNovedadesRetiro(Long idAporteDetallado, Long idPersona);

	/**
	 * Método que consulta los aportantes que no cuentan con el dato del día
	 * hábil de vencimiento de aportes
	 * 
	 * @return <b>List<ListadoAportantesDiaVencimientoDTO></b> Listado de los
	 *         aportantes que cumplen con el criterio indicado
	 */
	public List<AportanteDiaVencimientoDTO> consultarAportantesSinDiaVencimiento();

	/**
	 * Método encargado de la actualización del día hábil de vencimiento de
	 * aportes para aportantes empleadores
	 * 
	 * @param diaVencimiento
	 *            Día de vencimiento para el aportante
	 * @param idsEmpleadores
	 *            Listado de empleadores a actualizar
	 */
	public void actualizarDiaVencimientoAportesEmpleadores(Short diaVencimiento, List<Long> idsEmpleadores);

	/**
	 * Método encargado de la actualización del día hábil de vencimiento de
	 * aportes para aportantes independientes y pensionados
	 * 
	 * @param diaVencimiento
	 *            Día de vencimiento para el aportante
	 * @param idsIdePen
	 *            Listado de independientes y pensionados a actualizar
	 */
	public void actualizarDiaVencimientoAportesIndPen(Short diaVencimiento, List<Long> idsIdePen);

	/**
	 * Método que consulta la lista de aportes generales por empleador
	 * 
	 * @param idEmpleador
	 *            Identificador del empleador
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @param estadoAporteAportante
	 *            Estado del aporte
	 * @return La lista de <code>AporteGeneral</code>
	 */
	public List<AporteGeneral> consultarAporteGeneralEmpleador(Long idEmpleador,
			EstadoRegistroAporteEnum estadoRegistroAporte, EstadoAporteEnum estadoAporteAportante);

	/**
	 * Método que consulta registros en <code>AporteDetallado</code>,
	 * relacionados a los ids de <code>AporteGeneral</code> pasados como
	 * parámetro
	 * 
	 * @param listaIdAporteGeneral
	 *            Lista de ids de <code>AporteGeneral</code>
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @param estadoAporteAportante
	 *            Estado del aporte
	 * @return La lista de registros de <code>AporteDetallado</code>
	 */
	public List<AporteDetallado> consultarAporteDetalladoPorIdsGeneral(List<Long> listaIdAporteGeneral,
			EstadoRegistroAporteEnum estadoRegistroAporte, EstadoAporteEnum estadoAporteAportante);

	/**
	 * Método que consulta los aportes generales de una persona independiente o
	 * pensionada
	 * 
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoSolicitante
	 *            Tipo de solicitante
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @return La lista de aportes generales asociados a la persona
	 */
	public List<AporteGeneral> consultarAporteGeneralPersona(Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, EstadoAporteEnum estadoAporte,
			EstadoRegistroAporteEnum estadoRegistroAporte);

	/**
	 * Método que consulta los aportes detallados de un independiente o
	 * pensionado, asociados a una lista de aportes generales
	 * 
	 * @param listaIdAporteGeneral
	 *            La lista de ids de <code>AporteGeneral</code>
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoAfiliado
	 *            Tipo de afiliado/cotizante
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @param estadoAporteAportante
	 *            Estado del aporte
	 * @return La lista de registro de <code>AporteDetallado</code>
	 */
	public List<AporteDetallado> consultarAporteDetalladoPorIdsGeneralPersona(List<Long> listaIdAporteGeneral,
			Long idPersona, TipoAfiliadoEnum tipoAfiliado, EstadoRegistroAporteEnum estadoRegistroAporte,
			EstadoAporteEnum estadoAporteAportante);

	/**
	 * Método que consulta una lista de aportes detallados relacionados a un
	 * aporte general
	 * 
	 * @param idAporteGeneral
	 *            Identificador del aporte general
	 * @return La lista de aportes detallados asociados
	 */
	public List<AporteDetallado> consultarAporteDetalladoPorIdGeneral(Long idAporteGeneral);

	/**
	 * Consulta las cuentas de aportes relacionadas a una lista de ids de aporte
	 * general
	 * 
	 * @param idsAporteGeneral
	 * @return Retorna una lista de cuenta de aportes
	 */
	public List<CuentaAporteDTO> consultarCuentasAporteSinDetalle(List<Long> idsAporteGeneral,UriInfo uri, HttpServletResponse response);

	/**
	 * Consulta la lista de aportes en estado RELACIONADO, sobre la tabla
	 * <code>AporteGeneral</code>
	 * 
	 * @param consultaAportesRelacionados
	 *            Filtros de consulta
	 * @return La lista de aportes obtenida
	 */
	public List<MovimientoIngresosDTO> consultarAportesRelacionados(
			ConsultaAporteRelacionadoDTO consultaAportesRelacionados);

	/**
	 * Consulta la cuenta de aportes relacionados a un cotizante
	 * 
	 * @param idsAporteGeneral
	 * @param idPersonaCotizante
	 * @return
	 */
	public List<CuentaAporteDTO> consultarCuentasAporteCotizante(List<Long> idsAporteGeneral, Long idPersonaCotizante,UriInfo uri, HttpServletResponse response, TipoMovimientoRecaudoAporteEnum tipo);

	/**
	 * Consulta los solicitantes relacionados a una lista de personas
	 * 
	 * @param idsPersona
	 *            Identificadores de las personas
	 * @return Lista de solicitantes
	 */
	public List<SolicitanteDTO> consultarSolicitanteAporteGeneral(List<Long> idsPersona);

	/**
	 * Consulta el dto de persona por tipo y número de identificación
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación relacionado a una persona
	 * @param numeroIdentificacion
	 *            Número de identificación relacionado a una persona
	 * @return DTO de Persona
	 */
	public PersonaDTO consultarPersonaTipoNumeroIdentificacion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * Consulta la empresa por el identificador de la persona
	 * 
	 * @param idPersona
	 *            Identificador de la persona relacionado a la empresa
	 * @return El DTO de Empresa
	 */
	public EmpresaDTO consultarEmpresa(Long idPersona);

	/**
	 * Consulta la solicitud de corrección de aporte por el identificador del
	 * aporte general
	 * 
	 * @param idAporteGeneral,
	 *            Identificador del aporte general relacionado a la solicitud de
	 *            correccion de aporte
	 * @return La entidad de solicitud de corrección de aporte
	 */
	public SolicitudCorreccionAporte consultarSolicitudCorreccionAporteAporteGeneral(Long idAporteGeneral);

	/**
	 * Consulta del historico de cierre o pre cierre, por fechas o número de
	 * radicación.
	 * 
	 * @param fechaInicio
	 *            fecha de inicio.
	 * @param fechaFin
	 *            fecha de fin.
	 * @param numeroRadicacion
	 *            número de radicación.
	 * @return lista de los cierre.
	 */
	public List<SolicitudCierreRecaudoModeloDTO> consultarHistoricoCierre(Long fechaInicio, Long fechaFin,
			String numeroRadicacion, TipoCierreEnum tipoCierre);

	/**
	 * Consulta que valida si ya hay un cierre aprobado o en proceso para la
	 * misma fecha.
	 * 
	 * @param fechaInicio
	 *            fecha de inicio.
	 * @param fechaFin
	 *            fecha fin.
	 * @return true si la validacion es superada, false si la validación no es
	 *         superada.
	 */
	public Boolean validarGeneracionCierre(Long fechaInicio, Long fechaFin);

	/**
	 * Crea o actualiza la solicitud del cierre del recaudo.
	 * 
	 * @param solicitudCierreDTO
	 *            solicitud de cierre del recaudo.
	 * @return id de la solicitud creada.
	 */
	public Long guardarSolicitudCierreRecaudo(SolicitudCierreRecaudoModeloDTO solicitudCierreDTO);

	/**
	 * Consulta la solicitud de cierre de recaudo por número de radicación.
	 * 
	 * @param numeroRadicacion
	 *            número de radicación.
	 */
	public SolicitudCierreRecaudoModeloDTO consultarSolicitudCierreRecaudo(String numeroRadicacion);

	/**
	 * Actualización del estado de la solicitud de cierre.
	 * 
	 * @param estado
	 *            estado de la solicitud a modificar.
	 * @param numeroRadicacion
	 *            número de radicación de la solicitud a modificar.
	 */
	public void actualizarEstadoSolicitudCierre(EstadoSolicitudCierreRecaudoEnum estado, String numeroRadicacion);

	/**
	 * Consultar los registros de cierre recaudo que se clasifican como aportes
	 * ya sean manuales o por pila
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return Lista de registro de aportes para armar el resumen del cierre de
	 *         recaudo
	 */
	public List<Object[]> consultarRegistrosAportes(Long fechaInicio, Long fechaFin);

	/**
	 * Consultar los registros de cierre recaudo que se clasifican como aportes
	 * ya sean manuales o por pila
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return Lista de registro de aportes para armar el resumen del cierre de
	 *         recaudo
	 */
	public List<Object[]> consultarRegistrosAportesRelacionados(Long fechaInicio, Long fechaFin);

	/**
	 * Consultar los registros de cierre recaudo que se clasifican como
	 * devoluciones
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return Lista de registro de aportes para armar el resumen del cierre de
	 *         recaudo
	 */
	public List<Object[]> consultarRegistrosDevoluciones(Long fechaInicio, Long fechaFin);

	/**
	 * Consultar los registros de cierre recaudo que se clasifican como
	 * devoluciones
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return Lista de registro de aportes para armar el resumen del cierre de
	 *         recaudo
	 */
	public List<Object[]> consultarRegistrosDevolucionesRelacionados(Long fechaInicio, Long fechaFin);

	/**
	 * Consultar los registros de cierre recaudo que se clasifican como
	 * correcciones
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return Lista de registro de aportes para armar el resumen del cierre de
	 *         recaudo
	 */
	public List<Object[]> consultarRegistrosCorrecciones(Long fechaInicio, Long fechaFin);

	/**
	 * Consultar los registros de cierre recaudo que se clasifican como
	 * correcciones
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return Lista de registro de aportes para armar el resumen del cierre de
	 *         recaudo
	 */
	public List<Object[]> consultarRegistrosCorreccionesRelacionados(Long fechaInicio, Long fechaFin);

	/**
	 * Consultar los registros de cierre recaudo que se clasifican como
	 * Registros legalizados
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return Lista de registro de aportes para armar el resumen del cierre de
	 *         recaudo
	 */
	public List<Object[]> consultarRegistrosLegalizados(Long fechaInicio, Long fechaFin);

	/**
	 * Metodo que sirve para consultar la solicitud de devolucion
	 * 
	 * @param idsAporteGeneral
	 *            id aporte general por medio de este se consulta el solicitud
	 *            de devolucion
	 * @return un DTO SolicitudDevolucionAporteModeloDTO
	 */
	public SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionVista360(Long idsAporteGeneral,
			Long numeroOperacion);

	/**
	 * Metodo que sirve para consultar la solicitud de Correcion
	 * 
	 * @param idsAporteGeneral
	 *            id aporte general por medio de este se consulta el solicitud
	 *            de Correcion
	 * @return Mapa de lista de DTOs de SolicitudDevolucionAporteModeloDTO
	 *         asociados al aporte que les corresponde
	 */
	public Map<Long, List<SolicitudCorreccionAporteModeloDTO>> consultarSolicitudCorrecionVista360(
			List<Long> idsAporteGeneral);

	/**
	 * Metodo que sirve para consultar el detalle de la devolucion de la vista
	 * 360 de aportes
	 * 
	 * @param idSolicitudDevolucion
	 *            por medio de este se consulta el detalle de la devolución
	 * @return un DTO DetalleDevolucionVista360DTO
	 */
	public List<DetalleDevolucionVista360DTO> consultarDetalleDevolucionVista360(Long idSolicitudDevolucion);

	/**
	 * Metodo que sirve para consultar el detalle de la correccion del cotizante
	 * de la vista 360 de aportes
	 * 
	 * @param idAporteDetallado
	 *            identificador de aporte detallado
	 * @return un dto DetalleCorreccionCotizanteVista360DTO
	 */
	public List<DetalleCorreccionCotizanteVista360DTO> consultarDetalleCorrecionCotizanteVista360(
			Long idSolicitudCorreccion);

	/**
	 * Metodo que sirve para consultar el detalle de la correccion del aportante
	 * de la vista 360 de aportes
	 * 
	 * @param idAporteGeneral
	 *            identificador de aporte general
	 * @return un dto DetalleCorreccionAportanteVista360DTO
	 */
	public DetalleCorreccionAportanteVista360DTO consultarDetalleCorrecionAportanteVista360(Long idSolicitudCorreccion);

	/**
	 * Metodo que sirve para consultar el detalle de la correccion del aportante
	 * de la vista 360 de aportes
	 * 
	 * @param idAporteDetallado
	 * @param idAporteGeneral
	 *            identificador de aporte general
	 * @param tipoIdentificacion
	 *            tipo identificacion del aportante
	 * @param numeroIdentificacion
	 *            numero identificacion del aportante
	 * @return un dto DetalleCorreccionCotizanteNuevoVista360DTO
	 */
	public List<DetalleCorreccionCotizanteNuevoVista360DTO> consultarDetalleCorrecionCotizanteNuevoVista360(
			Long idSolicitudCorreccion, Long idAporteDetallado);

	/**
	 * Metodo que sirve para consultar el detalle de la devolucion de la vista
	 * 360 de aportes
	 * 
	 * @param idsAporteGeneral
	 *            por medio de este se consulta el detalle de la devolucion
	 * @return un DTO DetalleDevolucionVista360DTO
	 */
	public List<Long> consultarAporteGeneralPorCotizante360Persona(Long idPersona);

	/**
	 * Metodo que sirve para consultar la solicitud de aporte
	 * 
	 * @param idAporteGeneral
	 *            id aporte general por medio de este se consulta el solicitud
	 *            de Aporte
	 * @return un DTO SolicitudAporteModeloDTO
	 */
	public SolicitudAporteModeloDTO consultarSolicitudAporteVista360Empleador(Long idAporteGeneral);

	/**
	 * Méwtodo que consulta el preiodo de pago para un afiliado que sea
	 * independiente o pensionado
	 * 
	 * @param idPersona
	 * @param tipoAfiliado
	 * @return
	 */
	public PeriodoPagoPlanillaEnum consultarPeriodoPagoAfiliacion(Long idPersona, TipoAfiliadoEnum tipoAfiliado);

	/**
	 * Se consulta la persona relacionada en aporte detallado como cotizante
	 * 
	 * @param idsAporteDetallado
	 *            Listado de identificadores de aporte detallado
	 * @return Datos de detalle de devolución
	 */
	public List<DetalleDevolucionCotizanteDTO> consultarCotizanteAporteDetallado(List<Long> idsAporteDetallado);

	/**
	 * Consulta los registros que quedaron con personas pendientes por afiliar
	 * 
	 * @param idAporteGeneral
	 * @return Lista de recaudo de cotizantes
	 */
	public List<RecaudoCotizanteDTO> consultarRegistrosPendientesAfiliar(Long idAporteGeneral);

	/**
	 * Método encargado de determinar sí un cotizante a causado subsidio
	 * monetario
	 * 
	 * @param tipoIdentificacionCotizante
	 *            Tipo de identificación del cotizante a consultar
	 * @param numeroIdentificacionCotizante
	 *            Número de identificación del cotizante a consultar
	 * @param periodoAporte
	 *            Período de aporte para el cual se hace la consulta
	 * @return <b>Boolean</b> Indica sí el cotizante causó o no subsidio para el
	 *         período
	 */
	public Boolean cotizanteConSubsidioPeriodo(TipoIdentificacionEnum tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String periodoAporte);

	/**
	 * Consulta el detalle de registros de los aportes o legalizaciones y sus cotizantes
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @param legalizacion
	 * @return
	 */
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroAportesLegalizacion(Long fechaInicio,
			Long fechaFin, Boolean legalizacion, Boolean otrosIngresos);

	/**
	 * consulta el radicado de cierre de un registro general
	 * @param idRegistroGeneral
	 */
	public String consultarRadicadoCierre(String idAporteGeneral);

	/**
	 * Consulta el detalle de registros de las devoluciones y sus cotizantes
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroDevoluciones(Long fechaInicio, Long fechaFin);

	/**
	 * Consulta el detalle de registros de las devoluciones y sus cotizantes
	 * 
	 * @param idAporteGeneral
	 * @return
	 */
	public Long consultarAporteGeneralCorregido(Long idAporteGeneral);


	/**
	 * Consulta el detalle de registros de las correcciones y sus cotizantes
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @param original
	 * @return
	 */
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroCorrecciones(Long fechaInicio, Long fechaFin,
			Boolean original);

	/**
	 * Consulta el detalle de registros de las correcciones y sus cotizantes
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @param original
	 * @return
	 */
	public List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroCorreccionesALaAlta(Long fechaInicio, Long fechaFin,
			Boolean original);

	/**
	 * Actualiza las marcas de aportes conciliados por los identificadores de
	 * los aportes generales
	 * 
	 * @param idsAporteGeneral
	 */
	public void actualizarConciliadoAporteGeneral(String idsAporteGeneral);

	/**
	 * Consulta el historico de las novedades mas recientes
	 * 
	 * @param fechaFin
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public List<HistoricoNovedadesDTO> consultarHistoricoNovedadesRecientes(Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	/**
	 * Consulta las novedades que no se encuentren parametrizadas en rol
	 * afiliado
	 * 
	 * @param time
	 * @param fechaFin
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public List<HistoricoNovedadesDTO> consultarNovedadesSinRolAfiliado(Date fechaInicio, Date fechaFin,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	/**
	 * Consulta que obtiene los identificadores de los cotizantes nuevos de las
	 * correcciones
	 * 
	 * @param idAporteGeneral
	 *            Lista de identificadores de aporte general
	 * @return Mapa con los identificadores de cotizantes
	 */
	public Map<Long, String> consultarCotizantesCorrecciones(List<Long> idsAporteGeneral);

	/**
	 * Consulta los datos del aportante
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param numeroIdentificacionCotizante
	 * @return
	 */
	public DatosAportanteDTO consultarDatosAportante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionCotizante);

	/**
	 * Consulta el historico de los aportes mostrando los tres ultimos registros
	 * para un aportante especifico dentro de unos periodos
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param numeroIdentificacionCotizante
	 * @param periodoInicio
	 * @param periodoFin
	 * @return
	 */
	public DatosAportanteDTO consultarHistoricoDatosAportante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionCotizante, Long periodoInicio, Long periodoFin);

	/**
	 * Consulta los datos del cotizante
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param numeroIdentificacionAportante
	 * @return
	 */
	public DatosCotizanteIntegracionDTO consultarDatosCotizante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionAportante);

	/**
	 * Consulta el historico de los cotizantes mostrando los tres ultimos
	 * registros para un cotizante especifico dentro de unos periodos
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param numeroIdentificacionAportante
	 * @param periodoInicio
	 * @param periodoFin
	 * @return
	 */
	public DatosCotizanteIntegracionDTO consultarHistoricoDatosCotizante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroIdentificacionAportante, Long periodoInicio, Long periodoFin);

	/**
	 * Método encargado de la consulta de aportes detallados a partir de su ID
	 * de registro detallado asociado
	 * 
	 * @param idRegistroDetallado
	 *            ID de RegistroDetallado consultado
	 * @return <b>List<AporteDetalladoModeloDTO></b> Listado de aportes
	 *         detallados asociados al registro detallado consultado
	 */
	public List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorRegistroDetallado(Long idRegistroDetallado);

	/**
	 * Método encargado de la actualización de un listado de Aportes detallados
	 * 
	 * @param aportes
	 *            Listado de aportes
	 */
	public void actualizarAportesReprocesados(List<AporteDetalladoModeloDTO> aportes);

	/**
	 * @param idEmpresa
	 * @param idPersona
	 * @param tipo
	 * @param periodoRecuado
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public List<Long> consultarAportesConCorrecciones(Long idEmpresa, Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodoRecuado, Long fechaInicio, Long fechaFin,
			TipoMovimientoRecaudoAporteEnum tipoMovimiento);

	public List<Long> consultarAportesConCorreccionesEmpleador(Long idEmpresa, Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Long periodoRecuado, Long fechaInicio, Long fechaFin,
			TipoMovimientoRecaudoAporteEnum tipoMovimiento);

	/**
	 * Método para la consulta de los datos de afiliación de un aportante para
	 * cálculo de servicios
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param tipoAfiliacion
	 * @param periodoEnFecha
	 * @return <b>DatosAfiliadoServiciosDTO</b> Datos del afiliado
	 */
	public DatosAfiliadoServiciosDTO consultarDatosAfiliadoServicios(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliacion, Date periodoEnFecha);

	/**
	 * Método que realiza el llamado al Sp USP_calculoFechaDiaHabil
	 * 
	 * @param iMes
	 * @param iAnio
	 * @param iDiaHabil
	 * @return <b>Date</b> Fecha calendario de correspondiente al año, mes y día
	 *         habil suministrados
	 */
	public Date ejecutarCalculoFechaHabil(Integer iMes, Integer iAnio, Short iDiaHabil);

	/**
	 * Método para la consulta del aporte más reciente respecto a un período
	 * dado de un afiliado
	 * 
	 * @param datosAfiliado
	 * @param periodoCalculo
	 * @param fechaCalendarioVencimiento
	 * @return <b>DatosAfiliadoServiciosDTO</b> DTO con los datos del afiliado,
	 *         actualizado
	 */
	public DatosAfiliadoServiciosDTO consultarEstadoAporteMasRecientePeriodo(DatosAfiliadoServiciosDTO datosAfiliado,
			String periodoCalculo, Date fechaCalendarioVencimiento);

	/**
	 * Método que consulta el estado de afiliación de una persona por tipo de
	 * afiliación
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param tipoAfiliado
	 * @return <b>EstadoAfiliadoEnum</b> Estado de afiliación respecto al tipo
	 *         de afiliación
	 */
	public EstadoAfiliadoEnum consultarEstadoAfiliacionPorTipoAfiliacion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado);

	/**
	 * Consulta los registros que quedaron con los aportes en estado OK o NO OK
	 * 
	 * @param idAporteGeneral
	 * @return Lista de recaudo de cotizantes
	 */
	public List<RecaudoCotizanteDTO> consultarEstadoRegistros(Long idAporteGeneral,
			List<EstadoRegistroAportesArchivoEnum> estadoRegistro);

	/**
	 * Consulta las solicitudes de devoluciones de aportes
	 * 
	 * @param idsAporteGeneral
	 *            Identificadores de aportes
	 * @return
	 */
	public List<DevolucionVistasDTO> consultarSolicitudDevolucion(List<Long> idsAporteGeneral);

	/**
	 * @param idAporte
	 * @param idSolicitudDevolucion
	 * @return
	 */
	public AnalisisDevolucionDTO consultarDatosDevolucion(Long idAporte, Long idSolicitudDevolucion);

	/**
	 * @param idsAporteGeneral
	 * @return
	 */
	public List<CorreccionVistasDTO> consultarDatosSolicitudCorreccion(List<Long> idsAporteGeneral);

	/**
	 * @param ids
	 * @return
	 */
	public List<AporteGeneralModeloDTO> consultarAportesGenerales(List<Long> ids, List<Long> idsPersona);

	/**
	 * @param ids
	 * @return
	 */
	public List<AporteDetalladoModeloDTO> consultarAportesDetallados(List<Long> ids);

	/**
	 * @param codigos
	 * @return
	 */
	public List<Municipio> consultarMunicipiosPorCodigos(List<String> codigos);

	/**
	 * @param nums
	 * @return
	 */
	public List<PersonaModeloDTO> consultarPersonasPorListado(List<String> nums);

	/**
	 * @param ids
	 * @return
	 */
	public List<EmpresaModeloDTO> consultarEmpresasPorIdsPersonas(List<Long> ids);

	/**
	 * @param ids
	 * @return
	 */
	public List<EmpleadorModeloDTO> consultarEmpleadoresPorIdsEmpresas(List<Long> ids);

	/**
	 * @param ids
	 * @return
	 */
	public List<AfiliadoModeloDTO> consultarAfiliadosPorPersona(List<Long> ids);

	/**
	 * @param ids
	 * @return
	 */
	public List<RolAfiliadoModeloDTO> consultarRolesAfiliadosPorPersona(List<Long> ids);

	/**
	 * @param codigos
	 * @return
	 */
	public List<OperadorInformacion> consultarOperadoresInformacionPorCodigos(List<Integer> codigos);

	/**
	 * @param sucursales
	 * @return
	 */
	public Map<Long, List<SucursalEmpresaModeloDTO>> consultarSucursalesPorLlaves(List<String> sucursales);

	/**
	 * @param ids
	 * @return
	 */
	public List<Long> consultarPersonasInconsistentes(List<Long> ids);
	
	/**
	 * @param registrosDetallados
	 * @return
	 */
	public List<Object[]> consultarNovedadesPorListaIdRegDet(List<Long> ids);

	/**
	 * @param idAporteDetallado
	 * @return
	 */
	public CuentaAporteDTO consultarAporteDetalladoOriginal(Long idAporteDetallado);

	/**
	 * @param criterios
	 * @return
	 */
	public List<Object[]> consultarRegistrosResumenCierre(DatosConsultaAportesCierreDTO criterios);

	/**
	 * @param criterios
	 * @return
	 */
	public List<Object[]> consultarRegistrosResumenCierreN(DatosConsultaAportesCierreDTO criterios);

	public List<Object[]> consultarRegistrosResumenCierreAportes(DatosConsultaAportesCierreDTO criterios);

	public List<Object[]> consultarRegistrosResumenCierreRegistrados(DatosConsultaAportesCierreDTO criterios);



	/**
	 * @param idsRegistroGeneral
	 * @return
	 */
	public Map<Long, SolicitudAporteModeloDTO> consultarSolicitudesDevolucionListaIds(List<Long> idsRegistroGeneral);

	/**
	 * @param datosCotizantes
	 * @return
	 */
	public List<DatosConsultaSubsidioPagadoDTO> consultarPagoSubsidioCotizantes(
			List<DatosConsultaSubsidioPagadoDTO> datosCotizantes);

	/**
	 * @param idsPersona
	 * @param desdeEmpresa
	 * @return
	 */
	public Map<Long, PersonaModeloDTO> consultarPersonasPorListadoIds(List<Long> idsPersona, Boolean desdeEmpresa);

	/**
	 * @param idAporteGeneral
	 * @return
	 */
	public Map<Long, SolicitudAporteModeloDTO> consultarSolicitudAportePorListaIdAporte(List<Long> idAporteGeneral);

	/**
	 * @param idsAporteGeneral
	 * @return
	 */
	public Map<Long, Map<Long, SolicitudDevolucionAporteModeloDTO>> consultarSolicitudDevolucionVista360ListaIds(
			List<Long> idsAporteGeneral);

	/**
	 * @param tipoIdAfiliado
	 * @param numeroIdAfiliado
	 * @param tipoAfiliado
	 * @param idEmpresa
	 * @return
	 */
	public List<HistoricoAportes360DTO> consultarHistoricoAportesPorTipoAfiliacion(
			TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado,
			Long idEmpresa);

	/**
	 * @param ids
	 * @return
	 */
	public List<EmpresaModeloDTO> consultarEmpresasPorIds(List<Long> ids);

	/**
	 * @param idsAportes
	 * @return
	 */
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorIds(List<Long> idsAportes);

	/**
	 * @param idsAportes
	 * @return
	 */
	public List<AporteDetalladoModeloDTO> consultarAportesDetalladosPorIdsGeneral(List<Long> idsAportes);

	/**
	 * @param aportesGenerales
	 * @param aportesDetallados
	 */
	public void actualizarPaqueteAportes(List<AporteGeneralModeloDTO> aportesGenerales,
			List<AporteDetalladoModeloDTO> aportesDetallados);

	/**
	 * @param metodoRecaudo
	 * @return
	 */
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorMetodo(ModalidadRecaudoAporteEnum metodoRecaudo,
			List<Long> idsPersonas);

	/**
	 * @param idRegistroGeneral
	 * @return
	 */
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorIdRegGen(Long idRegistroGeneral);

    /**
     * Método para la persistencia de un paquete de aportes generales
     * @param aportesGenerales
     * @return
     */
    public Map<String, Long> registrarAportesGenerales(Map<String, AporteGeneralModeloDTO> aportesGenerales);

	/**
     * Método para la persistencia de un paquete de aportes detallados
	 * @param aportesDetallados
	 * @return
	 */
	public List<Long> registrarAportesDetallados(List<JuegoAporteMovimientoDTO> aportesDetallados);
	
	public void consultarPersonasAfiliadasConAporteFuturo(String fecha, String periodo);
	
	public void ejecutarCalculoCategoriasMasiva();
	
	public List<Object> ejecutarCalculoCategoriaAportesFuturos();

    /**
     * Consultar aportante ingresado en el proceso de corrección 
     * 
     * @param idAporteGeneralCorregido Identificador del aporte general que se corrigieron sus valores
     * @return Persona aportante ingresada en la corección
     */
    public PersonaModeloDTO consultarAportanteCorreccion(Long idAporteGeneralCorregido, Long idAporteDetalladoCorregido);
    
    /**
	 * Servicio encargado lanzar el copiado de los aportes temporales a core
	 * 
	 * @param idRegistroGeneral
	 *            identificador del registro general del cual se quiere consultar información
	 */
    public void copiarDatosAportes(Long idRegistroGeneral);

	/**
	 * Servicio encargado de verificar Novedades
	 * 
	 * @param idRegistroGeneral
	 *            identificador del registro general del cual se quiere consultar información
	 */
	public Boolean validarNovedadesPila(Long idRegistroGeneral);
	
	/**
     * Consulta todas las tasas de interes de mora de aportes
     * @return TasasInteresMoraModeloDTO
     */
    public List<TasasInteresMoraModeloDTO> consultarTasasInteresMora();
    
    /**
     * Método que actualiza una tasa de interes mora para un periodo especifico
     * @param fechaInicioTasa
     * @param porcentajeNuevo
     * @param normativaNueva
     * @return
     */
    public ResultadoModificarTasaInteresDTO actualizarTasaInteresMora(ModificarTasaInteresMoraDTO tasaModificada);
    
    /**
     * Método que persiste una nueva parametrización de una tasa de interes mora para un periodo
     * @param nuevaTasa
     * @return
     */
    public Boolean crearTasaInteresMora(ModificarTasaInteresMoraDTO nuevaTasa);

    /**
     * Método que consulta los nuevos numeros de operacion para las correcciones
     * @param listaIdApDetallados
     * @return 
     */
	public Map<Long, String> consultarNuevosNumerosCorreccion(List<Long> listaIdApDetallados);
	
	/**
	 * consulta el estado del procesamiento de la planilla 
	 * @param idAporteGeneral
	 * @return
	 */
	public List<PilaEstadoTransitorio> consultarEstadoProcesamientoPlanilla(Long idAporteGeneral);
	

	/**
	 * consulta el estado del procesamiento de la planilla 
	 * @param idSolicitud
	 * @return
	 */
	public String consultarClasificacionIndependiente(Long idSolicitud);
	

	/**
	 * consulta el estado del procesamiento de la planilla 
	 * @param idSolicitud
	 * @return
	 */
	public Double consultarPorcentajeIndependiente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

	public String consultarTarifa(String registroGeneral);

	public String consultarTarifaAportante(String registroGeneral, String numeroIdentificacion, String tipoIdentificacion);

	/**
     * 
     * @param idRegistroGeneral
     * @param numeroPlanilla
     * @return
     */
    public String consultarNumeroDePlanillaN(Long aporteDetallado);

	/**
     * 
     * @param idRegistroGeneral
     * @param numeroPlanilla
     * @return
     */
    public String consultarNumeroDePlanillaCorregida(Long aporteDetallado);

	public Map<Long, Boolean> consultaModificaciones(List<CuentaAporteDTO> cuentaAportes);

	public List<AporteGeneralModeloDTO> consultarCuentaBancariaRecaudo(List<AporteGeneralModeloDTO> aportesGenerales);
	
		/**
	 * Consulta las cuentas de aportes relacionadas a una lista de ids de aporte
	 * general
	 * 
	 * @param idsAporteGeneral
	 * @return Retorna una lista de cuenta de aportes
	 */
	public List<CuentaAporteDTO> consultarCuentasAporteLimit(List<Long> idsAporteGeneral,UriInfo uri, HttpServletResponse response);

	public Object[] consultarCuentaBancariaAporteGeneral(Long idAporteGeneral);

	public String consutlarNumeroOperacionCorreccion(Long idAporteDetallado);

	public Object[] consultarCuentaBancariaAportePlanillaN(Long numPlanillaN);

	public void ejecutarCalculoCategoriaAportesFuturosSP(BigInteger registroDetallado);

	public List<Object[]> consultarRegistrosResumenCierreAportesExtemporaneos(DatosConsultaAportesCierreDTO criterios);

	List<ResultadoDetalleRegistroDTO> consultarDetalleRegistroAportesExtemporaneos(Long fechaInicio,
																				   Long fechaFin, Boolean legalizacion, Boolean otrosIngresos);
}
