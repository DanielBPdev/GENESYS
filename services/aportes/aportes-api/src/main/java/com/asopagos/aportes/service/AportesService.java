package com.asopagos.aportes.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;


import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.ConsultaAporteRelacionadoDTO;
import com.asopagos.aportes.dto.ConsultaMovimientoIngresosDTO;
import com.asopagos.aportes.dto.ConsultaPlanillaResultDTO;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.ConsultarCotizanteDTO;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import com.asopagos.aportes.dto.CorreccionVistasDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.aportes.dto.DatosConsultaSolicitudesAporDevCorDTO;
import com.asopagos.aportes.dto.DatosPersistenciaAportesDTO;
import com.asopagos.aportes.dto.DetalleCorreccionAportanteVista360DTO;
import com.asopagos.aportes.dto.DetalleCorreccionCotizanteNuevoVista360DTO;
import com.asopagos.aportes.dto.DetalleCorreccionCotizanteVista360DTO;
import com.asopagos.aportes.dto.DetalleDevolucionCotizanteDTO;
import com.asopagos.aportes.dto.DetalleDevolucionVista360DTO;
import com.asopagos.aportes.dto.DetalleRegistroDTO;
import com.asopagos.aportes.dto.DevolucionVistasDTO;
import com.asopagos.aportes.dto.EstadoServiciosIndPenDTO;
import com.asopagos.aportes.dto.GeneracionArchivoCierreDTO;
import com.asopagos.aportes.dto.HistoricoNovedadesDTO;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import com.asopagos.aportes.dto.JuegoAporteMovimientoDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDetalladoDTO;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import com.asopagos.aportes.dto.ResultadoConsultaNovedadesExistentesDTO;
import com.asopagos.aportes.dto.ResultadoModificarTasaInteresDTO;
import com.asopagos.aportes.dto.ResultadoRecaudoCotizanteDTO;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.aportes.dto.SolicitudAporteHistoricoDTO;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.dto.AportanteDiaVencimientoDTO;
import com.asopagos.dto.HistoricoAportes360DTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.aportes.AportePilaDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.HistoricoDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.aportes.ResultadoArchivoAporteDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.CorreccionModeloDTO;
import com.asopagos.dto.modelo.DevolucionAporteDetalleModeloDTO;
import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.InformacionFaltanteAportanteModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.TasasInteresMoraModeloDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;

import com.asopagos.rest.security.dto.UserDTO;

import javax.ws.rs.core.UriInfo;
import javax.servlet.http.HttpServletResponse;
/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con las acciones de registro o relación de aportes
 * 
 * <b>Módulo:</b> Asopagos - HU-211-397 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Path("aportes")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AportesService {

	/**
	 * Servicio que se encarga de guardar una solicitud de aporte y una
	 * solicitud global
	 * 
	 * @param solicitudAporteDTO
	 *            solicitud a guardar.
	 */
	@Path("/crearSolicitudAporte")
	@POST
	public SolicitudAporteModeloDTO crearSolicitudAporte(@NotNull SolicitudAporteModeloDTO solicitudAporteDTO);

	/**
	 * Servicio que se encarga de actualizar el estado de una solicitud de
	 * aportes.
	 * 
	 * @param idSolicitudGlobal
	 *            id de la solicitud global.
	 * @param estado
	 *            de la solicitud de aporte.
	 */
	@POST
	@Path("/{idSolicitudGlobal}/estadoSolicitud")
	public void actualizarEstadoSolicitud(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal,
			@QueryParam("estadoSolicitud") @NotNull EstadoSolicitudAporteEnum estado);

	/**
	 * Método encargado de consultar una solicitud de aporte por el id.
	 * 
	 * @param idSolicitudNovedad
	 *            id de la solicitud de noevedad.
	 * @return solicitud de la novedad encontrada.
	 */
	@GET
	@Path("/consultarSolicitudAporte")
	public SolicitudAporteModeloDTO consultarSolicitudAporte(@QueryParam("idSolicitud") @NotNull Long idSolicitud);

	/**
	 * Servicio que se encarga de actualizar una solicitud de aportes.
	 * 
	 * @param solicitudAporteDTO
	 *            solicitud de aportes a actualizar.
	 */
	@POST
	@Path("/actualizarSolicitudAporte")
	public void actualizarSolicitudAporte(@NotNull SolicitudAporteModeloDTO solicitudAporteDTO);

	/**
	 * Servicio que se encarga de crear un aporte general.
	 * 
	 * @param aporteGeneralDTO
	 *            aporte a crear.
	 * @return aporte guardado.
	 */
	@POST
	@Path("/crearAporteGeneral")
	@Deprecated
	public AporteGeneralModeloDTO crearAporteGeneral(@NotNull AporteGeneralModeloDTO aporteGeneralDTO);

	/**
	 * Servicio encargado de crear un aporte detallado
	 * 
	 * @param aporteDetalladoModeloDTO,
	 *            dto que contiene la información de un aporte detallado
	 * @return retorna el id del aporte detalle creado o actualizado
	 */
	@POST
	@Path("/crearAporteDetallado")
	public Long crearAporteDetallado(@Valid @NotNull AporteDetalladoModeloDTO aporteDetalladoModeloDTO);

	/**
	 * Servicio encargado de crear la información faltante de los resgistros de
	 * aportes.
	 * 
	 * @param infoFaltanteDTO:
	 *            dto con la información de la información faltante del
	 *            aportante.
	 */
	@POST
	@Path("/crearInfoFaltante")
	public void crearInfoFaltante(@NotNull List<InformacionFaltanteAportanteModeloDTO> infoFaltanteDTO);

	/**
	 * Servicio encargado de consultar la informacion faltante por id de
	 * solicitud.
	 * 
	 * @param idSolicitud
	 *            id de la solicitud a consultar su información faltante.
	 */
	@GET
	@Path("/{idSolicitud}/consultarInfoFaltante")
	public List<InformacionFaltanteAportanteModeloDTO> consultarInfoFaltante(
			@PathParam("idSolicitud") Long idSolicitud);

	/**
	 * Servicio que se encarga de ejecutar el armado staging.
	 * 
	 * @param idTransaccion
	 *            id de la transacción.
	 */
	@POST
	@Path("/ejecutarArmadoStaging")
	public void ejecutarArmadoStaging(@NotNull @QueryParam("idTransaccion") Long idTransaccion);

	/**
	 * Servicio que se encarga de consultar el historico de solicitudes dado
	 * unos filtros.
	 * 
	 * @param solicitudAporteDTO
	 *            filtros.
	 * @return lista de los registros encontrados.
	 */
	@POST
	@Path("/consultarHistoricoSolicitudes")
	public List<SolicitudAporteHistoricoDTO> consultarHistoricoSolicitudes(
			@NotNull SolicitudAporteHistoricoDTO solicitudAporteDTO);

	/**
	 * Servicio que se encarga de guardar o actualizar registros en Registro
	 * general.
	 * 
	 * @param registroGeneralDTO
	 *            dto a guardar o modificar.
	 * @return id del registro general creado.
	 */
	@POST
	@Path("/crearRegistroGeneral")
	public Long crearRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO);

	/**
	 * Servicio que se encarga de consultar un registro general por el id de la
	 * solicitud.
	 * 
	 * @return RegistroGeneralModeloDTO registro encontrado.
	 */
	@GET
	@Path("/{idSolicitud}/consultarRegistroGeneral")
	public RegistroGeneralModeloDTO consultarRegistroGeneral(@PathParam("idSolicitud") Long idSolicitud);

	/**
	 * Servicio que se encarga de consultar un registro general por el id de la
	 * solicitud. (Límita a solicitudes no finalizadas)
	 * 
	 * @return RegistroGeneralModeloDTO registro encontrado.
	 */
	@GET
	@Path("/{idSolicitud}/consultarRegistroGeneralLimitado")
	public RegistroGeneralModeloDTO consultarRegistroGeneralLimitado(@PathParam("idSolicitud") Long idSolicitud);

	/**
	 * Servicio que se encarga de consultar un registro general por el id de la
	 * solicitud. (Límita a solicitudes no finalizadas)
	 * 
	 * @return RegistroGeneralModeloDTO registro encontrado.
	 */
	@GET
	@Path("/{idSolicitud}/consultarRegistroGeneralLimitadoIdRegGen")
	public RegistroGeneralModeloDTO consultarRegistroGeneralLimitadoIdRegGen(@PathParam("idSolicitud") Long idSolicitud, @QueryParam("idRegistroGeneral") Long idRegistroGeneral);
	
	/**
	 * Servicio que se encarga de consultar un registro general por el id de la
	 * solicitud. (Límita a solicitudes no finalizadas)
	 * 
	 * @return RegistroGeneralModeloDTO registro encontrado.
	 */
	@GET
	@Path("/consultarPlanillaNNotificar/")
	public Boolean consultarPlanillaNNotificar(@NotNull @QueryParam("idPlanilla") Long idPlanilla);
	
	/**
	 * Servicio que se encarga de guardar o actualizar registros en Registro
	 * detallado.
	 * 
	 * @param registroDetalladoDTO
	 *            dto a guardar o modificar.
	 */
	@POST
	@Path("/crearRegistroDetallado")
	public Long crearRegistroDetallado(RegistroDetalladoModeloDTO registroDetalladoDTO);

	/**
	 * Servicio que se encarga de guardar una transacción.
	 * 
	 * @return id de la transacción.
	 */
	@POST
	@Path("/crearTransaccion")
	public Long crearTransaccion();

	/**
	 * Servicio que se encarga de invocar el procedimiento almacenado de
	 * simulación de fase PILA 2.
	 * 
	 * @param idTransaccion
	 *            id de la transacción.
	 */
	@POST
	@Path("/simularFasePila2")
	public void simularFasePila2(@QueryParam("idTransaccion") Long idTransaccion);

	/**
	 * Servicio encargado de validar la estructura de un archivo que contienen
	 * registros de trabajadores de pago manual de aportes
	 * 
	 * @param archivoDTO,
	 *            archivo a validar
	 * @return listado de cotizantes a devolver
	 */
	@POST
	@Path("validarArchivoPagoManualAportes")
	public ResultadoArchivoAporteDTO validarArchivoPagoManualAportes(InformacionArchivoDTO archivoDTO);

	/**
	 * Servicio encargado de validar la estructura de un archivo que contienen
	 * registros de trabajadores de pago manual de aportes de pensionados
	 * 
	 * @param archivoDTO,
	 *            archivo a validar
	 * @return listado de cotizantes a devolver
	 */
	@POST
	@Path("validarArchivoPagoManualAportesPensionado")
	public ResultadoArchivoAporteDTO validarArchivoPagoManualAportesPensionados(InformacionArchivoDTO archivoDTO);

	/**
	 * Servicio que se encarga de consultar los cotizantes que estuvieron
	 * activos en el periodo cotizado.
	 * 
	 * @param tipoIdentificacion
	 *            tipo de identificacion del empleador.
	 * @param numeroIdentificacion
	 *            número de identificacion del empleador.
	 * @param periodoAporte
	 *            perido del aporte de pago.
	 * @return lista de los cotizantes.
	 */
	@POST
	@Path("/consultarCotizantesPorRol")
	public List<CotizanteDTO> consultarCotizantesPorRol(List<Long> idRoles);

	/**
	 * Servicio encargado de consultar el solicitante.
	 * 
	 * @param tipoSolicitante
	 *            tipo solicitante (Empleador, independiente, pensionado)
	 * @param tipoIdentificacion
	 *            tipo de identificacion del solicitante.
	 * @param numeroIdentificacion
	 *            número de identificación del solicitante.
	 * @return persona modelo DTO.
	 */
	@GET
	@Path("/consultarSolicitante")
	public SolicitanteDTO consultarSolicitante(
			@NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Se consulta la lista de solicitantes relacionadas a un numero de
	 * identificación y tipo de identificación
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	@GET
	@Path("obtenerSolicitantes")
	public List<SolicitanteDTO> consultarSolicitanteCorreccion(
			@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio encargado de ejecutar el borrado staging.
	 * 
	 * @param idTransaccion
	 *            id de la transaccion
	 */
	@POST
	@Path("/ejecutarBorradoStaging")
	public void ejecutarBorradoStaging(@NotNull @QueryParam("idTransaccion") Long idTransaccion);

	/**
	 * Servicio que se encarga de consultar unregistro detallado por el id del
	 * registro genera.
	 * 
	 * @param idRegistroGeneral
	 *            id del registro general.
	 * @return listado de los registros detallados.
	 */
	@GET
	@Path("/{idRegistroGeneral}/consultarRegistroDetallado")
	public List<RegistroDetalladoModeloDTO> consultarRegistroDetallado(
			@PathParam("idRegistroGeneral") Long idRegistroGeneral);

	/**
	 * Servicio que se encarga de consultar las novedades de un cotizante.
	 * 
	 * @param tipoIdentificacion
	 *            tipo de identificación del cotizante.
	 * @param numeroIdentificacion
	 *            número de identificación del cotizante.
	 * @return listado de las novedades.
	 */
	@GET
	@Path("/{tipoIdentificacion}/{numeroIdentificacion}/consultarNovedades")
	public List<HistoricoNovedadesDTO> consultarNovedades(
			@PathParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@PathParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("periodopago") Long periodopago);

	/**
	 * Servicio encargado de consultar la planilla
	 * 
	 * @param idPlanilla
	 *            el identificador de la planilla a buscar
	 * @return List<AportePilaDTO> con la información de la planilla
	 */
	@GET
	@Path("/consultarPlanilla/{idPlanilla}")
	public ConsultaPlanillaResultDTO consultarPlanilla(@NotNull @PathParam("idPlanilla") Long idPlanilla);

	/**
	 * Servicio encargado de crear un arreglo con las notificaciones a enviar
	 * dada una planilla
	 * 
	 * @param planilla
	 *            es la información de los aportes sobre los cuales se desea
	 *            enviar la información
	 * @return List<NotificacionParametrizadaDTO> son las notificaciones que se
	 *         desea enviar.
	 */
	@POST
	@Path("/crearNotificacionesParametrizadas")
	public List<NotificacionParametrizadaDTO> crearNotificacionesParametrizadas(List<AportePilaDTO> planilla);

	/**
	 * Servicio encargado de consultar una solicitud de correccion por su
	 * identificador
	 * 
	 * @param idSolicitudGlobal,
	 *            id de la global solicitud de correccion a consultar
	 * @return retorna la solicitud de correccion dto
	 */
	@GET
	@Path("/consultarSolicitudCorreccion/{idSolicitudGlobal}")
	public SolicitudCorreccionAporteModeloDTO consultarSolicitudCorreccionAporte(
			@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

	/**
	 * Servicio encargado de consultar una solicitud de recaudo por su
	 * identificador
	 * 
	 * @param idSolicitudGlobal,
	 *            id de la global solicitud de correccion a consultar
	 * @return retorna la solicitud de correccion dto
	 */
	@POST
	@Path("/consultarRecaudo")
	public List<AnalisisDevolucionDTO> consultarRecaudo(ConsultarRecaudoDTO consultaRecaudo,
			@QueryParam("tipoMovimientoRecaudoAporte") TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,
			@QueryParam("hayParametros") Boolean hayParametros, @QueryParam("vista360") Boolean vista360);

	/**
	 * Servicio encargado de consultar una solicitud de recaudo por su
	 * identificador
	 * 
	 * @param idSolicitudGlobal,
	 *            id de la global solicitud de correccion a consultar
	 * @return retorna la solicitud de correccion dto
	 */
	@POST
	@Path("/consultarRecaudoCotizante")
	public List<AnalisisDevolucionDTO> consultarRecaudoCotizante(ConsultarRecaudoDTO consultaRecaudo);

	/**
	 * Servicio para validar si existe una solicitud de aporte para un aportante
	 * que aún no esté cerrada.
	 * 
	 * @param
	 * @return boolean
	 */
	@GET
	@Path("/validarExistenciaSolicitud")
	public Boolean validarExistenciaSolicitud(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio que se encarga de invocar los procedimientos almacenados que
	 * registra y relaciona aportes y novedades
	 * 
	 * @param idTransaccion
	 *            id de la transacción.
	 * @param esProcesoManual
	 *            Indica si es procesamiento de aportes y novedades manuales o
	 *            automatica
	 * @param esSimulado
	 *            Indica si el proceso es simulado o no
	 */
	@POST
	@Path("/{idTransaccion}/registrar")
	public void registrarRelacionarAportesNovedades(@PathParam("idTransaccion") Long idTransaccion,
			@QueryParam("esProcesoManual") Boolean esProcesoManual, @QueryParam("esSimulado") Boolean esSimulado);

	/**
	 * Servicio que se encarga de invocar los procedimientos almacenados que
	 * registra y relaciona aportes y novedades
	 * 
	 * @param idTransaccion
	 *            id de la transacción.
	 * @param esProcesoManual
	 *            Indica si es procesamiento de aportes y novedades manuales o
	 *            automatica
	 * @param esSimulado
	 *            Indica si el proceso es simulado o no
	 */
	@POST
	@Path("/{idTransaccion}/registrarRelacionarAportes")
	public void registrarRelacionarAportes(@PathParam("idTransaccion") Long idTransaccion,
			@QueryParam("esProcesoManual") Boolean esProcesoManual, @QueryParam("esSimulado") Boolean esSimulado);

	/**
	 * Servicio que se encarga de invocar los procedimientos almacenados que
	 * registra y relaciona novedades
	 * 
	 * @param idTransaccion
	 *            id de la transacción.
	 * @param esProcesoManual
	 *            Indica si es procesamiento de aportes y novedades manuales o
	 *            automatica
	 * @param esSimulado
	 *            Indica si el proceso es simulado o no
	 */
	@POST
	@Path("/{idTransaccion}/registrarRelacionarNovedades")
	public void registrarRelacionarNovedades(@PathParam("idTransaccion") Long idTransaccion,
			@QueryParam("esProcesoManual") Boolean esProcesoManual, @QueryParam("esSimulado") Boolean esSimulado);

	/**
	 * Servicio que se encarga de invocar los procedimientos almacenados que
	 * registra y relaciona novedades
	 * 
	 * @param idTransaccion
	 *            id de la transacción.
	 */
	@POST
	@Path("/{idTransaccion}/organizarNovedadesSucursal")
	public void organizarNovedadesSucursal(@PathParam("idTransaccion") Long idTransaccion);
	
	/**
	 * Servicio que se encarga de consultar los aportes en la temporal de pila.
	 * 
	 * @param idTransaccion
	 *            id de la transaccion.
	 * @return listado de los aportes.
	 */
	@GET
	@Path("{idRegistroGeneral}/consultarAporteTemporal")
	public List<AporteDTO> consultarAporteTemporal(@PathParam("idRegistroGeneral") Long idRegistroGeneral);

	/**
	 * Servicio que se encarga de consultar la novedad relacionada a un aporte
	 * temporal
	 * 
	 * @param idRegistroGeneral
	 * @return
	 */
	@GET
	@Path("{idRegistroGeneral}/consultarTemporalNovedad")
	public List<TemNovedad> consultarNovedad(@PathParam("idRegistroGeneral") Long idRegistroGeneral);

	/**
	 * Servicio que consulta la información de un aporte detallado, por número
	 * de operación de segundo nivel
	 * 
	 * @param idAporteDetallado
	 *            Número de operación de segundo nivel
	 * @return Un objeto <code>AporteDetalladoModelotDTO</code> con la
	 *         información del aporte
	 */
	@GET
	@Path("/consultarAporteDetallado/{idAporteDetallado}")
	public AporteDetalladoModeloDTO consultarAporteDetallado(@PathParam("idAporteDetallado") Long idAporteDetallado);

	/**
	 * Servicio que consulta la información de un índice de planilla, por número
	 * de operación de primer nivel
	 * 
	 * @param idPlanilla
	 *            Número de operación de primer nivel
	 * @return Un objeto <code>IndicePlanillaModeloDTO</code> con la información
	 *         del archivo
	 */
	@GET
	@Path("/consultarIndicePlanilla/{idPlanilla}")
	public IndicePlanillaModeloDTO consultarIndicePlanilla(@PathParam("idPlanilla") Long idPlanilla);

	@GET
	@Path("/consultarIndicePlanillaNumeroAportante/{idPlanilla}")
	public IndicePlanillaModeloDTO consultarIndicePlanillaNumeroAportante(@PathParam("idPlanilla") Long idPlanilla,@QueryParam("registroDetallado") Long registroDetallado);

	/**
	 * Servicio que consulta la información de una solicitud de aportes, por
	 * identificador del aporte general asociado
	 * 
	 * @param idAporteGeneral
	 *            Número de operación de primer nivel en la solcitud
	 * @return Un objeto <code>SolicitudAporteModeloDTO</code> con la
	 *         información de la solicitud
	 */
	@GET
	@Path("/consultarSolicitudAportePorIdAporte/{idAporteGeneral}")
	public SolicitudAporteModeloDTO consultarSolicitudAportePorIdAporte(
			@PathParam("idAporteGeneral") Long idAporteGeneral);

	/**
	 * Servicio que consulta las novedades de un cotizante asociadas a un aporte
	 * detallado
	 * 
	 * @param idAporteDetallado
	 *            Número de operación de segundo nivel
	 * @return La lista de novedades, aplicadas y no aplicadas, para el
	 *         cotizante
	 */
	@GET
	@Path("/consultarNovedadesCotizanteAporte/{idRegistroDetallado}")
	public List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(
			@PathParam("idRegistroDetallado") Long idRegistroDetallado);

	/**
	 * Servicio que se encarga de consultar una solicitud de devolución de
	 * aportes, por identificador de la solicitud global asociada
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global
	 * @return La solicitud de devolución consultada
	 */
	@GET
	@Path("/consultarSolicitudDevolucionAporte/{idSolicitudGlobal}")
	public SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionAporte(
			@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

	/**
	 * Servicio que almacena o actualiza una solicitud de devolución de aportes
	 * 
	 * @param solicitudDevolucionAporteDTO
	 *            La solicitud a ser almacenada/actualizada
	 * @return El identificador de la solicitud global creada/actualizada
	 */
	@POST
	@Path("/crearActualizarSolicitudDevolucionAporte")
	public Long crearActualizarSolicitudDevolucionAporte(
			@NotNull SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO);

	/**
	 * Servicio que crea/actualiza una solicitud global
	 * 
	 * @param solicitudDTO
	 *            Los datos de la solicitud
	 * @return El identificador de la solicitud creada/actualizada
	 */
	@POST
	@Path("/crearActualizarSolicitudGlobal")
	public Long crearActualizarSolicitudGlobal(@NotNull SolicitudModeloDTO solicitudDTO);

	/**
	 * Servicio que actualiza o crea el movimiento del aporte
	 * 
	 * @param movimientoAporteModeloDTO
	 */
	@PUT
	@Path("/actualizarMovimientoAporte")
	public Long actualizarMovimientoAporte(MovimientoAporteModeloDTO movimientoAporteModeloDTO);

	/**
	 * Servicio que consulta las cuentas de un aporte por el id del aporte
	 * general mostrando los recaudos y ajustes ya sea de corrección o
	 * devolución
	 * 
	 * @param idAporteGeneral
	 * @return
	 */
	@POST
	@Path("/consultarCuentaAportes")
	public List<CuentaAporteDTO> consultarCuentaAporte(@QueryParam("idPersonaCotizante") Long idPersonaCotizante,
			List<AnalisisDevolucionDTO> analisisDevolucionDTO);

	/**
	 * Servicio que consulta las cuentas de un aporte por el id del aporte
	 * general mostrando los recaudos y ajustes ya sea de corrección o
	 * devolución
	 * 
	 * @param idAporteGeneral
	 * @return
	 */
	@POST
	@Path("/consultarCuentaAportesConTipoRecaudo")
	public List<CuentaAporteDTO> consultarCuentaAportesConTipoRecaudo(@QueryParam("idPersonaCotizante") Long idPersonaCotizante,
			List<AnalisisDevolucionDTO> analisisDevolucionDTO,@QueryParam("tipoRecaudo") TipoMovimientoRecaudoAporteEnum tipoRecaudo);

	/**
	 * Servicio que consulta la información de un aporte detallado en el staging
	 * de PILA, por identificador
	 * 
	 * @param idRegistroDetallado
	 *            Número de operación de segundo nivel
	 * @return Un objeto <code>RegistroDetalladoModeloDTO</code> con la
	 *         información del registro
	 */
	@GET
	@Path("/consultarRegistroDetalladoPorId/{idRegistroDetallado}")
	public RegistroDetalladoModeloDTO consultarRegistroDetalladoPorId(
			@PathParam("idRegistroDetallado") Long idRegistroDetallado);

	/**
	 * Servicio que actualiza una lista de registros en la tabla
	 * <code>AporteDetallado</code>
	 * 
	 * @param listaAporteDetalladoDTO
	 *            Lista de registros a actualizar
	 */
	@PUT
	@Path("/actualizarAporteDetallado")
	public void actualizarAporteDetallado(List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO);

	/**
	 * Servicio para consultar un cotizante.
	 * 
	 * @param datosCotizante
	 *            como parámetros de consulta del cotizante.
	 * @return Lista de cotizanteDTO como resultado de los datos consultados.
	 */
	@POST
	@Path("/consultarCotizante")
	public List<CotizanteDTO> consultarCotizante(ConsultarCotizanteDTO consultarCotizante);

	/**
	 * Servicio que crea o actualiza una lista de registros en la tabla
	 * <code>MovimientoAporte</code>
	 * 
	 * @param listaMovimientoAporteDTO
	 *            Lista de registros a crear/actualizar
	 */
	@POST
	@Path("/actualizarListaMovimientoAporte")
	public void actualizarListaMovimientoAporte(List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO);

	/**
	 * Servicio para consultar los movimientos históricos de ingresos.
	 * 
	 * @param consultaMovimientosIngresos
	 * @return MovimientoIngresosDTO
	 */
	@POST
	@Path("/consultarMovimientoHistoricos")
	public List<MovimientoIngresosDTO> consultarMovimientoHistoricos(
			@NotNull ConsultaMovimientoIngresosDTO consultaMovimientosIngresos);

	/**
	 * Método que crea/actualiza una devolución de aporte
	 * 
	 * @param devolucionAporteDTO
	 *            Los datos de la devolución
	 * @return El identificador del registro insertado/actualizado
	 */
	@POST
	@Path("/crearActualizarDevolucionAporte")
	public Long crearActualizarDevolucionAporte(DevolucionAporteModeloDTO devolucionAporteDTO);

	/**
	 * Servicio encargado de reconocer los ingresos obtenidos para un empleador,
	 * afiliado dependiente, independiente
	 * 
	 * @param tipoIdentificacion,
	 *            Tipo de identificación a consultar
	 * @param numeroIdentificacion,
	 *            número de identificación
	 * @param tipoAfiliado,
	 *            tipo de afiliado al cual se le reconoceran los ingresos
	 */
	@POST
	@Path("/reconocerIngresos")
	public void reconocerIngresos(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@NotNull @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado);

	/**
	 * Método que consulta un registro en la tabla <code>RegistroGeneral</code>,
	 * por identificador
	 * 
	 * @param idRegistroGeneral
	 *            Identificador del registro
	 * @return Un objeto <code>RegistroGeneralModeloDTO</code> con la
	 *         información del registro
	 */
	@GET
	@Path("/consultarRegistroGeneralId/{idRegistroGeneral}")
	RegistroGeneralModeloDTO consultarRegistroGeneralId(@PathParam("idRegistroGeneral") Long idRegistroGeneral);

	/**
	 * Método encargado de consultar un aporte general por id de registro
	 * general.
	 * 
	 * @param idRegistroGeneral
	 *            id del registro general.
	 * @return aporte general consultado.
	 */
	@GET
	@Path("/{idRegistroGeneral}/consultarAporteGeneralPorRegistro")
	@Deprecated
	public AporteGeneralModeloDTO consultarAporteGeneralPorRegistro(
			@PathParam("idRegistroGeneral") Long idRegistroGeneral);

	/**
	 * Servicio que consulta un registro en <code>AporteGeneral</code>, por
	 * identificador
	 * 
	 * @param idAporteGeneral
	 *            Identificador único del registro
	 * @return Objeto <code>AporteGeneralModeloDTO</code> con la información del
	 *         registro
	 */
	@GET
	@Path("/consultarAportegeneral/{idAporteGeneral}")
	AporteGeneralModeloDTO consultarAporteGeneral(@PathParam("idAporteGeneral") Long idAporteGeneral);

	/**
	 * Método que permite ver los movimientos de aportes detallados de un
	 * movimiento de aporte general
	 * 
	 * @param movimientoIngresosDTO
	 * @return
	 */
	@POST
	@Path("/obtenerMovimientoHistoricosDetallado")
	public List<MovimientoIngresosDetalladoDTO> consultarMovimientoHistoricosDetallado(
			MovimientoIngresosDTO movimientoIngresosDTO);

	/**
	 * Servicio encargado de registrar los movimientos de ingreso realizados
	 * 
	 * @param idsAportes,
	 *            ids de aportes detallados a registrar los movimientos
	 * @param estadoRegistro,
	 *            estado del registro de aporte
	 */
	@POST
	@Path("/registrarMovimientoIngresos")
	public void registrarMovimientoIngresos(@NotNull List<Long> idsAportes,
			@NotNull @QueryParam("estadoRegistro") EstadoRegistroAporteEnum estadoRegistro);

	/**
	 * Servicio encargado de actualizar el estado de una solicitud de corrección
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global
	 * @param estado
	 *            Estado de la solicitud
	 */
	@POST
	@Path("/actualizarEstadoSolicitudCorreccion/{idSolicitudGlobal}")
	void actualizarEstadoSolicitudCorreccion(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal,
			@QueryParam("estadoSolicitud") @NotNull EstadoSolicitudAporteEnum estado);

	/**
	 * Servicio encargado de actualizar el estado de una solicitud de devolución
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global
	 * @param estado
	 *            Estado de la solicitud
	 */
	@POST
	@Path("/actualizarEstadoSolicitudDevolucion/{idSolicitudGlobal}")
	void actualizarEstadoSolicitudDevolucion(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal,
			@QueryParam("estadoSolicitud") @NotNull EstadoSolicitudAporteEnum estado);

	/**
	 * Servicio que crea o actualiza una solicitud de corrección de aportes
	 * 
	 * @param solicitudCorreccionAporteDTO
	 *            Información de la solicitud a almacenar
	 * @return El identificador de la solicitud global creada/actualizada
	 */
	@POST
	@Path("/crearActualizarSolicitudCorreccionAporte")
	Long crearActualizarSolicitudCorreccionAporte(
			@NotNull SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO);

	/**
	 * Método que crea o actualiza una corrección de aportes
	 * 
	 * @param correccionDTO
	 *            Los datos de la corrección
	 * @return El identificador de la corrección creada/actualizada
	 */
	@POST
	@Path("/crearActualizarCorreccion")
	Long crearActualizarCorreccion(@NotNull CorreccionModeloDTO correccionDTO);

	/**
	 * Servicio que crea o actualiza un movimiento de aportes
	 * 
	 * @param movimientoAporteModeloDTO
	 *            Información del movimiento
	 * @return El identificador del registro modificado
	 */
	@POST
	@Path("/crearActualizarMovimientoAporte")
	Long crearActualizarMovimientoAporte(@NotNull MovimientoAporteModeloDTO movimientoAporteModeloDTO);

	/**
	 * Servicio que crea o actualiza un registro en <code>AporteDetallado</code>
	 * 
	 * @param aporteDetalladoDTO
	 *            La información del registro a crear/actualizar
	 * @return El identificador del registro modificado
	 */
	@POST
	@Path("/crearActualizarAporteDetallado")
	Long crearActualizarAporteDetallado(@NotNull AporteDetalladoModeloDTO aporteDetalladoDTO);

	/**
	 * Servicio que crea o actualiza un registro en <code>AporteGeneral</code>
	 * 
	 * @param aporteGeneralDTO
	 *            La información del registro a crear/actualizar
	 * @return El identificador del registro modificado
	 */
	@POST
	@Path("/crearActualizarAporteGeneral")
	Long crearActualizarAporteGeneral(@NotNull AporteGeneralModeloDTO aporteGeneralDTO);

	/**
	 * Servicio que crea o actualiza un registro en
	 * <code>DevolucionAporteDetalle</code>
	 * 
	 * @param aporteGeneralDTO
	 *            La información del registro a crear/actualizar
	 * @return El identificador del registro modificado
	 */
	@POST
	@Path("/crearActualizarDevolucionAporteDetalle")
	Long crearActualizarDevolucionAporteDetalle(@NotNull DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO);

	/**
	 * Método encargado de consultar los cotizantes en la cuenta de aporte
	 * 
	 * @param consultarCotizante
	 * @return lista de los cotizantes para la cuenta de aportes.
	 */
	@POST
	@Path("/consultarCotizanteCuentaAporte")
	List<CotizanteDTO> consultarCotizanteCuentaAporte(ConsultarCotizanteDTO consultarCotizante);

	/**
	 * Servicio que borra los datos de las tablas temporales
	 * <code>TemAporte</code>, <code>TemNovedad</code>,
	 * <code>TemCotizante</code> y <code>TemAportante</code>
	 * 
	 * @param idRegistroGeneral
	 *            Identificador del registro general del aporte asociado
	 */
	@DELETE
	@Path("/borrarTemporalesPILA/{idRegistroGeneral}")
	void borrarTemporalesPILA(@PathParam("idRegistroGeneral") Long idRegistroGeneral);

	/**
	 * Servicio encargado de verificar si existe el aporte general y detallado
	 * 
	 * @param idAporteGeneral
	 *            es el identificador del aporte general
	 * @param idAporteDetallado
	 *            es el identificador del aporte detallado
	 * 
	 * @return Map<String,Boolean> mapa con dos boolean que indican si el aporte
	 *         general y el aporte detallado ya han sido procesados.
	 */
	@GET
	@Path("/validarAporteProcesado/{idAporteGeneral}/{idAporteDetallado}")
	public Map<String, Boolean> validarAporteProcesado(@PathParam("idAporteGeneral") Long idAporteGeneral,
			@PathParam("idAporteDetallado") Long idAporteDetallado);

	/**
	 * Servicio para validar si existe una solicitud de corrección de aporte
	 * para un aportante que aún no esté cerrada.
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return boolean
	 */
	@GET
	@Path("/validarExistenciaSolicitudCorreccion")
	public Boolean validarExistenciaSolicitudCorreccion(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio para validar si existe una solicitud de devolución de aporte
	 * para un aportante que aún no esté cerrada.
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return boolean
	 */
	@GET
	@Path("/validarExistenciaSolicitudDevolucion")
	public Boolean validarExistenciaSolicitudDevolucion(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio que consulta el identificador de Municipio por medio de su
	 * codigo
	 * 
	 * @param codigoMunicipio
	 *            Codigo que representa el municipio ingresado por pantalla
	 * @return idMunicipio Identificador unico de un Municipio
	 */
	@GET
	@Path("/buscarMunicipio/{codigoMunicipio}")
	public Short buscarMunicipio(@PathParam("codigoMunicipio") String codigoMunicipio);

	/**
	 * Servicio que consulta la evaluación de condiciones por las cuales un
	 * aporte puede ser tenido o no en cuenta en una solicitud de devolución o
	 * corrección, de acuerdo a su registro histórico
	 * 
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param modalidadRecaudo
	 *            Modalidad de recaudo (PILA manual, PILA automático o Aporte
	 *            Manual)
	 * @param estadoProcesoArchivo
	 *            Estado de la planilla con la que se realizó el aporte, si éste
	 *            se hizo por PILA
	 * @param tieneModificaciones
	 *            Indica si el archivo ha sido modificado. Aplica sólo para PILA
	 * @param estadoSolicitudAporte
	 *            Estado de la solicitud de aportes
	 * @param tipoIdentificacionCotizante
	 *            Tipo de identificación del cotizante
	 * @param numeroIdentificacionCotizante
	 *            Número de identificación del cotizante
	 * @param periodoAporte
	 *            Periodo del aporte a validar
	 * @return Información de la evaluación de condiciones de acuerdo al
	 *         registro histórico del aporte
	 */
	@GET
	@Path("/consultarHistoricoEvaluacionAporte")
	HistoricoDTO consultarHistoricoEvaluacionAporte(@QueryParam("estadoAporte") EstadoAporteEnum estadoAporte,
			@QueryParam("modalidadRecaudo") ModalidadRecaudoAporteEnum modalidadRecaudo,
			@QueryParam("estadoProcesoArchivo") EstadoProcesoArchivoEnum estadoProcesoArchivo,
			@QueryParam("tieneModificaciones") Boolean tieneModificaciones,
			@QueryParam("estadoSolicitudAporte") EstadoSolicitudAporteEnum estadoSolicitudAporte,
			@QueryParam("tipoIdentificacionCotizante") TipoIdentificacionEnum tipoIdentificacionCotizante,
			@QueryParam("numeroIdentificacionCotizante") String numeroIdentificacionCotizante,
			@QueryParam("periodoAporte") String periodoAporte);

	/**
	 * Servicio que consulta la lista de transacciones de solicitudes de novedad
	 * en estado "RECHAZADA", para un cotizante
	 * 
	 * @param idAporteDetallado
	 *            Identificador único del aporte detallado, relacionado al
	 *            cotizante
	 * @return La lista de transacciones
	 */
	@GET
	@Path("/consultarTipoTransaccionNovedadRechazadaCotizante")
	List<String> consultarTipoTransaccionNovedadRechazadaCotizante(
			@QueryParam("idAporteDetallado") Long idAporteDetallado);

	/**
	 * Servicio que consulta la lista de novedades rechazadas para un cotizante
	 * 
	 * @param idRegistroDetallado
	 *            Identificador del registro detallado
	 * @param tiposTransaccionNovedadesRechazadas
	 *            Lista de tipos de transacción, como filtro de las novedades a
	 *            consultar
	 * @return La lista de novedades
	 */
	@POST
	@Path("/consultarNovedadesRechazadasCotizanteAporte/{idRegistroDetallado}")
	List<NovedadCotizanteDTO> consultarNovedadesRechazadasCotizanteAporte(
			@PathParam("idRegistroDetallado") Long idRegistroDetallado,
			@NotNull List<String> tiposTransaccionNovedadesRechazadas);

	/**
	 * Servicio actualiza el estado de los aportes en registro detallado si
	 * estos estan con un estado NO_OK o en estado NO_VALIDADO_BD
	 * 
	 * @param idCotizantes
	 *            Ids de los registros detallados que se obtienen de la lista de
	 *            cotizantes
	 */
	@POST
	@Path("/cambiarEstadoRegistroDetallado")
	public void cambiarEstadoRegistroDetallado(List<Long> idCotizantes);

	/**
	 * Servicio que consulta las novedades de retiro
	 * 
	 * @param idRegistroDetallado
	 *            del registro detallado asociado.
	 * @param idPersona
	 *            id de la persona.
	 * @return lista de las novedades de retiro.
	 */
	@GET
	@Path("{idRegistroDetallado}/{idPersona}/consultarNovedadRetiro")
	public List<NovedadCotizanteDTO> consultarNovedadRetiro(@PathParam("idRegistroDetallado") Long idRegistroDetallado,
			@PathParam("idPersona") Long idPersona);

	/**
	 * Servicio que consulta los aportantes (empleadores o afiliados
	 * independientes y pensionados) que no presentan el dato de día hábil de
	 * vencimiento de aportes
	 * 
	 * @author abaquero
	 * @return <b>List<ListadoAportantesDiaVencimientoDTO></b> Listado de
	 *         aportantes que no presentan día de vencimiento
	 */
	@GET
	@Path("consultarAportantesSinVencimiento")
	public List<AportanteDiaVencimientoDTO> consultarAportantesSinVencimiento();

	/**
	 * Servicio que actualiza el día hábil de vencimiento de aportes
	 * 
	 * @param aportantesPorActualizar
	 *            Listado de los aportantes a los cuales se les actualizará su
	 *            día hábil de vencimiento de aportes
	 */
	@POST
	@Path("actualizarDiaHabilVencimientoAporte")
	public void actualizarDiaHabilVencimientoAporte(@NotNull List<AportanteDiaVencimientoDTO> aportantesPorActualizar);

	/**
	 * Servicio que realiza la consulta de aportes generales por empleador
	 * 
	 * @param idEmpleador
	 *            Identificador del empleador
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @param estadoAporteAportante
	 *            Estado del aporte
	 * @return La lista de aportes generales
	 */
	@GET
	@Path("consultarAporteGeneralEmpleador")
	List<AporteGeneralModeloDTO> consultarAporteGeneralEmpleador(@QueryParam("idEmpleador") Long idEmpleador,
			@QueryParam("estadoRegistroAporte") EstadoRegistroAporteEnum estadoRegistroAporte,
			@QueryParam("estadoAporteAportante") EstadoAporteEnum estadoAporteAportante);

	/**
	 * Servicio que consulta registros en <code>AporteDetallado</code>,
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
	@POST
	@Path("consultarAporteDetalladoPorIdsGeneral")
	List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneral(@NotNull List<Long> listaIdAporteGeneral,
			@QueryParam("estadoRegistroAporte") EstadoRegistroAporteEnum estadoRegistroAporte,
			@QueryParam("estadoAporteAportante") EstadoAporteEnum estadoAporteAportante);

	/**
	 * Servicio que consulta los aportes generales asociados a un pensionado o
	 * independiente
	 * 
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoSolicitante
	 *            Tipo de solicitante
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @return La lista de aportes generales
	 */
	@GET
	@Path("consultarAporteGeneralPersona")
	List<AporteGeneralModeloDTO> consultarAporteGeneralPersona(@QueryParam("idPersona") Long idPersona,
			@QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
			@QueryParam("estadoAporte") EstadoAporteEnum estadoAporte,
			@QueryParam("estadoRegistroAporte") EstadoRegistroAporteEnum estadoRegistroAporte);

	/**
	 * Servicio que consulta los aportes detallados para un independiente o
	 * pensionado, con base en una lista de aportes generales
	 * 
	 * @param listaIdAporteGeneral
	 *            La lista de aportes generales
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoAfiliado
	 *            Tipo de afiliado/cotizante
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @param estadoAporteAportante
	 *            Estado del aporte
	 * @return La lista de registros de <code>AporteDetallado</code> que cumplen
	 *         con los criterios de búsqueda
	 */
	@POST
	@Path("consultarAporteDetalladoPorIdsGeneralPersona")
	List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneralPersona(
			@NotNull List<Long> listaIdAporteGeneral, @QueryParam("idPersona") Long idPersona,
			@QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado,
			@QueryParam("estadoRegistroAporte") EstadoRegistroAporteEnum estadoRegistroAporte,
			@QueryParam("estadoAporteAportante") EstadoAporteEnum estadoAporteAportante);

	/**
	 * Servicio que consulta la lista de aportes detallados relacionados a un
	 * aporte general
	 * 
	 * @param idAporteGeneral
	 *            Identificador del aporte general
	 * @return La lista de aportes detallados (por cotizante)
	 */
	@GET
	@Path("consultarAporteDetalladoPorIdGeneral")
	List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdGeneral(
			@QueryParam("idAporteGeneral") Long idAporteGeneral);

	/**
	 * Servicio que obtiene la lista de aportes en estado <i>RELACIONADO</i>,
	 * utilizado en la HU-261, previo a realizar un movimiento de ingresos
	 * manual
	 * 
	 * @param consultaAportesRelacionados
	 *            Filtros de consulta de los aportes
	 * @return La lista de aportes relacionados
	 */
	@POST
	@Path("/consultarAportesRelacionados")
	List<MovimientoIngresosDTO> consultarAportesRelacionados(
			@NotNull ConsultaAporteRelacionadoDTO consultaAportesRelacionados);

	/**
	 * Servicio actualiza el estado de los aportes en registro general si estos
	 * estan con un estado PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD
	 * 
	 * @param idRegistroGeneral
	 *            Id del registro general que se obtienen del aporte temporal
	 */
	@POST
	@Path("/cambiarEstadoRegistroGeneral")
	public void cambiarEstadoRegistroGeneral(List<Long> idRegistroGeneral);

	/**
	 * Se consulta la lista de solicitantes relacionadas a una lista de personas
	 * 
	 * @param personas
	 *            Lista de personas consultadas de acuerdo a los parametros de
	 *            búsqueda ingresados
	 * @return Lista de solicitantes que tengan aportes
	 * @deprecated
	 */
	@POST
	@Path("obtenerSolicitantesCuentaAportes")
	@Deprecated
	public List<SolicitanteDTO> consultarSolicitanteCorreccionCuentaAportes(List<PersonaDTO> personas);

	/**
	 * Se consulta la lista de solicitantes relacionadas a una lista de personas
	 * 
	 * @param idsPersonas
	 *            Lista de personas consultadas de acuerdo a los parametros de
	 *            búsqueda ingresados
	 * @return Lista de solicitantes que tengan aportes
	 */
	@POST
	@Path("obtenerSolicitantesCuentaAportesIds")
	public List<SolicitanteDTO> consultarSolicitanteCorreccionCuentaAportesIds(List<Long> idsPersonas);

	/**
	 * Servicio encargado de consultar una solicitud de correccion por el
	 * identificador del Aporte general
	 * 
	 * @param idAporteGeneral,
	 *            identificador del aporte general relacionado a la solicitud de
	 *            correccion de aportes
	 * @return retorna la solicitud de correccion de aportes dto
	 */
	@GET
	@Path("/consultarSolicitudCorreccionAporteGeneral/{idAporteGeneral}")
	public SolicitudCorreccionAporteModeloDTO consultarSolicitudCorreccionAporteAporteGeneral(
			@PathParam("idAporteGeneral") Long idAporteGeneral);

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
	@GET
	@Path("/consultarHistoricoCierre")
	public List<SolicitudCierreRecaudoModeloDTO> consultarHistoricoCierre(@QueryParam("fechaInicio") Long fechaInicio,
			@QueryParam("fechaFin") Long fechaFin, @QueryParam("numeroRadicacion") String numeroRadicacion,
			@QueryParam("tipoCierre") TipoCierreEnum tipoCierre);

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
	@GET
	@Path("/validarGeneracionCierre")
	public Boolean validarGeneracionCierre(
		    @NotNull @QueryParam("fechaInicio") Long fechaInicio,
			@NotNull @QueryParam("fechaFin") Long fechaFin);

	/**
	 * Crea o actualiza la solicitud del cierre del recaudo.
	 * 
	 * @param solicitudCierreDTO
	 *            solicitud de cierre del recaudo.
	 * @return id de la solicitud creada.
	 */
	@POST
	@Path("/guardarSolicitudCierreRecaudo")
	public Long guardarSolicitudCierreRecaudo(SolicitudCierreRecaudoModeloDTO solicitudCierreDTO);

	/**
	 * Consulta la solicitud de cierre de recaudo por número de radicación.
	 * 
	 * @param numeroRadicacion
	 *            número de radicación.
	 * @return solicitud consultada.
	 */
	@GET
	@Path("/{numeroRadicacion}/consultarSolicitudCierreRecaudo")
	public SolicitudCierreRecaudoModeloDTO consultarSolicitudCierreRecaudo(
			@PathParam("numeroRadicacion") String numeroRadicacion);

	/**
	 * Actualización del estado de la solicitud de cierre.
	 * 
	 * @param estado
	 *            estado de la solicitud a modificar.
	 * @param numeroRadicacion
	 *            número de radicación de la solicitud a modificar.
	 */
	@POST
	@Path("/{estado}/{numeroRadicacion}/consultarSolicitudCierreRecaudo")
	public void actualizarEstadoSolicitudCierre(
		    @PathParam("estado") EstadoSolicitudCierreRecaudoEnum estado,
			@PathParam("numeroRadicacion") String numeroRadicacion);

	/**
	 * Servicio encargado de consultar el recaudo devolucion y correcion de la
	 * vista 360 persona
	 * 
	 * @param ConsultarRecaudoDTO,
	 *            payload con la informacion a buscar
	 * 
	 * @param tipoMovimientoRecaudoAporte
	 *            tipo por el cual se filtra si es un recaudo devolucion o
	 *            correcion
	 * @param idAporteGeneral
	 *            cuando se accede directamente como persona se consultaran por
	 *            sus aportes generales segun el tipo de movimiento que le
	 *            llegue al servicio
	 * @return retorna CuentaAporteDTO
	 */
	@POST
	@Path("/consultarRecaudoDevolucionCorreccionVista360Persona")
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360Persona(
			ConsultarRecaudoDTO consultaRecaudo,
			@QueryParam("tipoMovimientoRecaudoAporte") TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,
			@QueryParam("idAporteGeneral") List<Long> idAporteGeneral);

	/**
	 * Servicio encargado de consultar el recaudo devolucion y correcion de la
	 * vista 360 persona
	 * 
	 * @param ConsultarRecaudoDTO,
	 *            payload con la informacion a buscar
	 * 
	 * @param tipoMovimientoRecaudoAporte
	 *            tipo por el cual se filtra si es un recaudo devolucion o
	 *            correcion
	 * @return retorna CuentaAporteDTO
	 */
	@POST
	@Path("/consultarRecaudoDevolucionCorreccionVista360PersonaPrincipal")
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360PersonaPrincipal(
			ConsultarRecaudoDTO consultaRecaudo,
			@QueryParam("tipoMovimientoRecaudoAporte") TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte);

	@POST
	@Path("/consultarRecaudoDevolucionCorreccionVista360PersonaPrincipalVistaBoton")
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360PersonaPrincipalVistaBoton(
			ConsultarRecaudoDTO consultaRecaudo,
			@QueryParam("tipoMovimientoRecaudoAporte") TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,
			@Context UriInfo uri, @Context HttpServletResponse response);

	/**
	 * Servicio encargado de consultar las devoluciones del aportante de la
	 * vista 360
	 * 
	 * @param ConsultarRecaudoDTO,
	 *            payload con la informacion a buscar
	 * @return retorna DevolucionVista360DTO
	 */
	@GET
	@Path("/consultarDetalleDevolucionesVista360")
	public DetalleDevolucionVista360DTO consultarDetalleDevolucionesVista360(
			@QueryParam("idSolicitudDevolucion") Long idSolicitudDevolucion);

	/**
	 * Servicio encargado de consultar los cotizantos de la vista 360 persona
	 * 
	 * @param ConsultarRecaudoDTO,
	 *            payload con la informacion a buscar
	 * @return retorna lista de id del aporte general
	 */
	@GET
	@Path("/consultarCotizantesVista360Persona")
	public List<Long> consultarCotizantesVista360Persona(@QueryParam("idPersona") Long idPersona);

	/**
	 * Servicio encargado de consultar las devoluciones del aportante de la
	 * vista 360
	 * 
	 * @param idAporteDetallado
	 *            identificador de aporte detallado
	 * @param tipoIdentificacion
	 *            tipo identificacion del aportante
	 * @param numeroIdentificacion
	 *            numero identificacion del aportante
	 * @return retorna DetalleCorreccionCotizanteVista360DTO
	 */
	@GET
	@Path("/consultarDetalleCorrecionCotizanteVista360/{idSolicitudCorreccion}")
	public List<DetalleCorreccionCotizanteVista360DTO> consultarDetalleCorrecionCotizanteVista360(
			@PathParam("idSolicitudCorreccion") Long idSolicitudCorreccion);

	/**
	 * Metodo que sirve para consultar el detalle de la correccion del aportante
	 * de la vista 360 de aportes
	 * 
	 * @param idAporteGeneral
	 *            identificador de aporte general
	 * @param tipoIdentificacion
	 *            tipo identificacion del aportante
	 * @param numeroIdentificacion
	 *            numero identificacion del aportante
	 * @return un dto DetalleCorreccionAportanteVista360DTO
	 */
	@GET
	@Path("/consultarDetalleCorrecionAportanteVista360/{idSolicitudCorreccion}")
	public DetalleCorreccionAportanteVista360DTO consultarDetalleCorrecionAportanteVista360(
			@PathParam("idSolicitudCorreccion") Long idSolicitudCorreccion);

	/**
	 * Metodo que sirve para consultar el detalle de la correccion del aportante
	 * de la vista 360 de aportes
	 * 
	 * @param idAporteGeneral
	 *            identificador de aporte general
	 * @param tipoIdentificacion
	 *            tipo identificacion del aportante
	 * @param numeroIdentificacion
	 *            numero identificacion del aportante
	 * @return un dto DetalleCorreccionCotizanteNuevoVista360DTO
	 */
	@GET
	@Path("/consultarDetalleCorrecionCotizanteNuevoVista360/{idSolicitudCorreccion}")
	public List<DetalleCorreccionCotizanteNuevoVista360DTO> consultarDetalleCorrecionCotizanteNuevoVista360(
			@PathParam("idSolicitudCorreccion") Long idSolicitudCorreccion,
			@QueryParam("idAporteDetallado") Long idAporteDetallado);

	/**
	 * Servicio que consulta la informacion general del aportante en vista 360
	 * empleador
	 * 
	 * @param idAporteGeneral
	 *            identificador de aporte general
	 * @param tipoIdentificacion
	 *            tipo identificacion del aportante
	 * @param numeroIdentificacion
	 *            numero identificacion del aportante
	 * @return un dto InformacionGeneralRecaudoEmpleadorVista360DTO
	 */
	@GET
	@Path("/consultarInformacionGeneralRecaudoEmpleadorVista360")
	public RegistroGeneralModeloDTO consultarInformacionGeneralRecaudoEmpleadorVista360(
			@QueryParam("idAporteGeneral") Long idAporteGeneral,
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion);

	/**
	 * Servicio que consulta la informacion general del aportante en vista 360
	 * empleador
	 * 
	 * @param idAporteGeneral
	 *            identificador de aporte general
	 * @param tipoIdentificacion
	 *            tipo identificacion del aportante
	 * @param numeroIdentificacion
	 *            numero identificacion del aportante
	 * @return un dto InformacionGeneralRecaudoEmpleadorVista360DTO
	 */
	@GET
	@Path("/consultarResultadoRecaudo")
	public List<ResultadoRecaudoCotizanteDTO> consultarResultadoRecaudo(
			@QueryParam("idAporteGeneral") Long idAporteGeneral);

	/**
	 * Servicio encargado de establecer sí es momento de procesar novedades
	 * futuras
	 * 
	 * @param fechaValidacion
	 *            Fecha para la cual se evalúa sí la novedad es procedente (en
	 *            milisegundos)
	 * @author abaquero
	 */
	@GET
	@Path("/validarProcesamientoNovedadFutura")
	public void validarProcesamientoNovedadFutura(@QueryParam("fechaValidacion") @NotNull Long fechaValidacion);

	/**
	 * Servicio que se encarga de consultar todos los registros de aporte que se
	 * les puede aplicar un cierre de recaudo de aportes
	 * 
	 * @param fechaInicio
	 *            fecha inicio para consultar los aportes
	 * @param fechaFin
	 *            fecha fin para consultar los aportes
	 * @return List<ResumenCierreRecaudoDTO> Lista con todos los registros de
	 *         aportes
	 */
	@POST
	@Path("/consultarRegistrosCierreRecaudo")
	public List<ResumenCierreRecaudoDTO> consultarRegistrosCierreRecaudo(
			@QueryParam("fechaInicio") @NotNull Long fechaInicio, @QueryParam("fechaFin") @NotNull Long fechaFin);

	/**
	 * Servicio que consulta datos adicionales del cotizante relacionado a una
	 * devolución de aportes
	 * 
	 * @param detalles
	 *            Lista con datos de la devolución por cotizante
	 * @return Lista de DetalleDevolucionCotizanteDTO
	 */
	@POST
	@Path("/consultarDetalleDevolucionCotizantes")
	public List<DetalleDevolucionCotizanteDTO> consultarDetalleDevolucionCotizantes(
			List<DetalleDevolucionCotizanteDTO> datosCotizante);

	/**
	 * Servicio que se encarga de consultar los resultados de registros de
	 * aportes, devoluciones, correcciones
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@POST
	@Path("/consultarResultadoRegistroRecaudoAportes")
	public List<DetalleRegistroDTO> consultarResultadoRegistroRecaudoAportes(
			@QueryParam("fechaInicio") @NotNull Long fechaInicio, @QueryParam("fechaFin") @NotNull Long fechaFin);

	/**
	 * Agrega la marca de conciliado alos aportes generales ingresados por
	 * parametros
	 * 
	 * @param idsAPorteGeneral
	 */
	@POST
	@Path("/conciliarAporteGeneral")
	public void conciliarAporteGeneral(String idsAporteGeneral);

	/**
	 * Servicio que valida el codigo y nombre de la sucursal registrados en el
	 * aporte manual desde el modulo de correcciones sean validos para el
	 * empleador
	 * 
	 * @param idRegistroGeneral
	 *            Identificador del registro general
	 * @param codigoSucursalPila
	 *            Codigo de la sucursal de PILA ingresada desde el proceso de
	 *            correcciones
	 * @param codigoSucursalPrincipal
	 *            Código de la sucursal principal que se encuentra registrada en
	 *            core
	 * @return Cumple o no cumple la validación del codigo y nombre de la
	 *         sucursal
	 */
	@POST
	@Path("/validarCodigoNombreSucursal")
	public Boolean validarCodigoNombreSucursal(@NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral,
			@QueryParam("codigoSucursalPila") String codigoSucursalPila,
			@QueryParam("codigoSucursalPrincipal") String codigoSucursalPrincipal);

	/**
	 * Servicio encargado de la actualización de aportes ya registrados y
	 * vueltos a calcular, con base en los datos registrados en la tabla
	 * temporal TemAporteActualizado
	 */
	@POST
	@Path("/actualizacionAportesRecalculados")
    public Boolean actualizacionAportesRecalculados(@QueryParam("procesoManual") Boolean procesoManual,
            List<Long> idsRegistrosOrigen);

	/**
	 * Servicio encargado de lanzar la consulta de estado de servicios para un
	 * independiente o pensionado
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación del aportante
	 * @param numeroIdentificacion
	 *            Número de identificación del aportante
	 * @param tipoAfiliacion
	 *            Tipo de afiliación del aportante (TRABAJADOR_INDEPENDIENTE o
	 *            PENSIONADO)
	 * @param fechaReferencia
	 *            Fecha en milisegundos para el cálculo de los servicios (fecha
	 *            actual en caso de ser nulo)
	 * @return <b>EstadoServiciosIndPenDTO</b> DTO que contiene la información
	 *         del resultado del cálculo
	 */
	@POST
	@Path("invocarCalculoEstadoServiciosIndPen")
	public EstadoServiciosIndPenDTO invocarCalculoEstadoServiciosIndPen(
			@QueryParam("tipoIdentificacion") @NotNull TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") @NotNull String numeroIdentificacion,
			@QueryParam("tipoAfiliacion") @NotNull TipoAfiliadoEnum tipoAfiliacion,
			@QueryParam("fechaReferencia") Long fechaReferencia);

	/**
	 * Servicio que se encarga de consultar una solicitud de devolución de
	 * aportes, por identificador de la solicitud global asociada
	 * 
	 * @param idSolicitudGlobal
	 *            Identificador de la solicitud global
	 * @return La solicitud de devolución consultada
	 */
	@GET
	@Path("/consultarSolicitudDevolucionAporteIdGeneral")
	public SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionAporteIdGeneral(
			@QueryParam("idAporteGeneral") Long idAporteGeneral,
			@QueryParam("idMovimientoAporte") Long numeroOperacion);

	/**
	 * Servicio encargado de consultar el recaudo devolucion y correcion de la
	 * vista 360 empleador
	 * 
	 * @param ConsultarRecaudoDTO,
	 *            payload con la informacion a buscar
	 * 
	 * @param tipoMovimientoRecaudoAporte
	 *            tipo por el cual se filtra si es un recaudo devolucion o
	 *            correcion
	 * @param vistaEmpleador
	 *            filtro cuando se accede directamente como empleador presencial
	 *            o web
	 * @return retorna CuentaAporteDTO
	 */
	@POST
	@Path("/consultarRecaudoDevolucionCorreccionVista360Empleador")
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360Empleador(
			ConsultarRecaudoDTO consultaRecaudo,
			@QueryParam("tipoMovimientoRecaudoAporte") TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,
			@QueryParam("idPersonaCotizante") Long idPersonaCotizante,
			@QueryParam("vistaEmpleador") String vistaEmpleador, @QueryParam("hayParametros") Boolean hayParametros);

	/**
	 * Servicio encargado de consultar las solicitudes de devoluciones de
	 * aportes asociadas a los identificadores de aportes generales
	 * 
	 * @param idsAporteGeneral
	 *            Identificadores de aportes generales
	 * @return Lista de solicitudes de devoluocines de aportes
	 */
	@POST
	@Path("/consultarSolicitudDevolucion")
	public List<DevolucionVistasDTO> consultarSolicitudDevolucion(List<Long> idsAporteGeneral);

	/**
	 * Servicio encargado de consultar las solicitudes de devoluciones de
	 * aportes asociadas a los identificadores de aportes generales
	 * 
	 * @param idsAporteGeneral
	 *            Identificadores de aportes generales
	 * @return Lista de solicitudes de devoluocines de aportes
	 */
	@POST
	@Path("/consultarRecaudoDevolucion")
	public List<AnalisisDevolucionDTO> consultarRecaudoDevolucion(
			@QueryParam("idSolicitudDevolucion") Long idSolicitudDevolucion, List<AnalisisDevolucionDTO> analisis);

	/**
	 * Servicio encargado de consultar los datos de las solicitudes de
	 * correcciones de aportes asociadas a los identificadores de aportes
	 * generales
	 * 
	 * @param idsAporteGeneral
	 *            Identificadores de aportes generales
	 */
	@POST
	@Path("/consultarDatosSolicitudCorreccion")
	public List<CorreccionVistasDTO> consultarDatosSolicitudCorreccion(List<Long> idsAporteGeneral);

	/**
	 * Servicio encargado de lqa eliminación de registros detallados asociados a
	 * un registro general por simulación de aportes
	 * 
	 * @param idRegistroGeneral
	 *            ID del registro general
	 */
	@POST
	@Path("/eliminarRegistrosDetalladosPorRegistroGeneral")
	public void eliminarRegistrosDetalladosPorRegistroGeneral(
			@QueryParam("idRegistroGeneral") @NotNull Long idRegistroGeneral);

	/**
	 * Servicio encargado de lqa eliminación de un registro general por
	 * simulación de aportes
	 * 
	 * @param idRegistroGeneral
	 *            ID del registro general
	 */
	@POST
	@Path("/eliminarRegistroGeneralPorId")
	public void eliminarRegistroGeneralPorId(Long idRegistroGeneral);

	/**
	 * Servicio encargado de la preparaciónde los datos para el proceso de
	 * persistencia de aportes
	 * 
	 * @param aportes
	 *            Listado de los DTO con la información procesada en BD de PILA
	 * @returm <b></b> DTO que contiene toda la información requerida para
	 *         procesar los aportes en Core
	 */
	@POST
	@Path("/prepararProcesoRegistroAportes")
	@Deprecated
	public DatosPersistenciaAportesDTO prepararProcesoRegistroAportes(@NotNull @Size(min = 1) List<AporteDTO> aportes);

	/**
	 * Servicio encargado de la consulta de los tipos de novedad que se
	 * encuentran registradas en Core con base a un id de registro detallado de
	 * PILA
	 * 
	 * @param registrosDetallados
	 *            Listado de los ID de registro detallado para la consulta
	 * @return </b>ResultadoConsultaNovedadesExistentesDTO</b> DTO que contiene
	 *         el mapa con los listados de tipos de novedad respecto al id de
	 *         registro detallado
	 */
	@POST
	@Path("/consultarNovedadesPorRegistroDetallado")
	public ResultadoConsultaNovedadesExistentesDTO consultarNovedadesPorRegistroDetallado(
			@NotNull List<Long> registrosDetallados);

	/**
	 * Servicio encargado de la creación de los registros de TemAporteProcesado
	 * para los registros generales de aportes que no se han creado aún
	 * 
	 * @param idsPorCrear
	 *            Mapa de los IDs de registro general para los cuales crear los
	 *            TemAporteProcesado
	 * @param listaPresneciaNovedades
	 *            Lista que contiene la relación de presencia de novedades por
	 *            registro general
	 */
	@POST
	@Path("/crearTemAporteProcesadosNuevos")
	public void crearTemAporteProcesadosNuevos(List<ConsultaPresenciaNovedadesDTO> listaPresneciaNovedades);

	/**
	 * Servicio encargado de la consulta de los datos del aporte original de una
	 * corrección o devolución
	 * 
	 * @param idAporteDetallado
	 *            ID del aporte detallado corregido o devuelto
	 * @return <b>CuentaAporteDTO</b> DTO con la información a presentar
	 */
	@GET
	@Path("/consultarRecaudoOriginalDevolucionCorreccionV360Persona")
	public CuentaAporteDTO consultarRecaudoOriginalDevolucionCorreccionV360Persona(
			@QueryParam("idAporteDetallado") @NotNull Long idAporteDetallado);

	/**
	 * Servicio para la consulta de solicitudes de corrección de aportes por
	 * listado de IDs de aporte general
	 * 
	 * @param idsAporteGeneral
	 * @return <b>DatosConsultaSolicitudesCorreccionDTO</b> DTO con los
	 *         resultados de la consulta
	 */
	@POST
	@Path("/consultarSolicitudesCorreccion")
	public DatosConsultaSolicitudesAporDevCorDTO consultarSolicitudesCorreccion(@NotNull List<Long> idsAporteGeneral);

	/**
	 * Servicio encargado de consultar el Histórico de aportes por tipo de
	 * afiliación
	 * 
	 * @param tipoIdAfiliado
	 * @param numeroIdAfiliado
	 * @param tipoAfiliado
	 * @param idEmpresa
	 * @return
	 */
	@GET
	@Path("/consultarHistoricoAportesAfiliado")
	public List<HistoricoAportes360DTO> consultarHistoricoAportesAfiliado(
			@QueryParam("tipoIdAfiliado") @NotNull TipoIdentificacionEnum tipoIdAfiliado,
			@QueryParam("numeroIdAfiliado") @NotNull String numeroIdAfiliado,
			@QueryParam("tipoAfiliado") @NotNull TipoAfiliadoEnum tipoAfiliado,
			@QueryParam("idEmpresa") Long idEmpresa);

	/**
	 * Servicio encargado de llevar a cabo las operaciones de reconocimiento de
	 * aportes
	 * 
	 * @param idsAportes
	 * @param estadoRegistro
	 * @param formaReconocimiento
	 */
	@POST
	@Path("/actualizarReconocimientoAportes")
	public void actualizarReconocimientoAportes(@NotNull List<Long> idsAportes,
			@QueryParam("estadoRegistro") EstadoRegistroAporteEnum estadoRegistro,
			@QueryParam("formaReconocimiento") FormaReconocimientoAporteEnum formaReconocimiento);

	/**
	 * Servicio encargado de la consulta de aportes generales por ID de Registro
	 * General
	 * 
	 * @param idRegistroGeneral
	 * @return <b>List<AporteGeneralModeloDTO></b> Listado de los aportes
	 *         generales que corresponden con el id de Registro General
	 */
	@GET
	@Path("/consultarAportesGeneralesPorIdRegGeneral")
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorIdRegGeneral(
			@QueryParam("idRegistroGeneral") @NotNull Long idRegistroGeneral);

	/**
	 * Método que consulta los aportes que pueden ser procesados y el número de
	 * detalles de aportes
	 * 
	 * @return
	 */
	@GET
	@Path("/consultarInformacionPlanillasRegistrarProcesar")
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionPlanillasRegistrarProcesar();

	/**
	 * Método que consulta las novedades que puede ser procesadas y el número de
	 * novedades
	 * 
	 * @return
	 */
	@GET
	@Path("/consultarInformacionNovedadesRegistrarProcesar")
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesRegistrarProcesar();

	/**
	 * Método que consulta los aportes de las planillas que se encuentran en la
	 * información temporal de pila sin registrar o relacionar
	 * 
	 * @param idRegistroGeneral
	 *            planilla aprocesar
	 * @param pagina
	 * @return
	 */
	@GET
	@Path("/consultarAportesPlanillasRegistrarProcesar")
	public List<AporteDTO> consultarAportesPlanillasRegistrarProcesar(
			@QueryParam("idRegistroGeneral") @NotNull Long idRegistroGeneral,
			@QueryParam("pagina") @NotNull Integer pagina);

	/**
	 * Método que consulta las novedades de las planillas que se encuentran en
	 * la información temporal de pila sin registrar o relacionar
	 * 
	 * @param planillaAProcesar
	 *            planilla aprocesar
	 * @param pagina
	 * @return
	 */
	@GET
	@Path("/consultarNovedadesPlanillasRegistrarProcesar")
	public List<NovedadesProcesoAportesDTO> consultarNovedadesPlanillasRegistrarProcesar(
			@QueryParam("planillaAProcesar") @NotNull Long planillaAProcesar,
			@QueryParam("pagina") @NotNull Integer pagina);

	/**
	 * Método para la eliminación de temporales de aporte por listado de IDs de
	 * registro detallado
	 * 
	 * @param idsDetalle
	 *            Listado de IDs de Registro Detallado
	 */
	@POST
	@Path("/eliminarTemporalesAporte")
	public void eliminarTemporalesAporte(@NotNull List<Long> idsDetalle);

	/**
	 * Método para la eliminación de temporales de novedad por listado de IDs de
	 * registro detallado
	 * 
	 * @param idsDetalle
	 *            Listado de IDs de Registro Detallado
	 */
	@POST
	@Path("/eliminarTemporalesNovedad")
	public void eliminarTemporalesNovedad(@NotNull List<Long> idsDetalle);

	/**
	 * Método para la persistencia de aportes generales
	 * 
	 * @param aportesGenerales
	 *            Mapa de DTO con los datos a persistir
	 * @return <b>Map<String, Long></b Listado del resultado del proceso
	 */
	@POST
	@Path("/procesarPaqueteAportesGenerales")
	public Map<String, Long> procesarPaqueteAportesGenerales(
			@NotNull Map<String, AporteGeneralModeloDTO> aportesGenerales);

	/**
	 * Método para la persistencia de aportes detallados
	 * 
	 * @param aportesDetallados
	 *            DTO con los datos a persistir
	 * @return <b>List<Long></b Listado de los registros detallados de los
	 *         aportes registrados
	 */
	@POST
	@Path("/procesarPaqueteAportesDetallados")
	public List<Long> procesarPaqueteAportesDetallados(@NotNull List<JuegoAporteMovimientoDTO> aportesDetallados);

	/**
	 * Servicio encargado de la actualización de los registros de TemAporte
	 * Procesado
	 */
	@POST
	@Path("/actualizarTemAporteProcesado")
	public void actualizarTemAporteProcesado();

        /**
	 * Servicio encargado de la actualización de los registros de TemAporte
	 * Procesado
	 */
	@POST
	@Path("/actualizarTemAporteProcesadoByIdPlanilla")
	public void actualizarTemAporteProcesadoByIdPlanilla(@QueryParam("idPlanilla") @NotNull Long idPlanilla);
        
	/**
	 * Servicio para la consulta de datos de planillas listas para notificar
	 * 
	 * @return <b>List<DatosComunicadoPlanillaDTO></b> Listado de DTOs con los
	 *         datos para la solicitud de notificación de aportes PILA
	 */
	@GET
	@Path("/consultarDatosComunicado")
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicado();
        
        /**
	 * Servicio para la consulta de datos de planillas listas para notificar
	 * @param idPlanilla  Identificador de {{PilaIndicePlanilla}}
	 * @return <b>List<DatosComunicadoPlanillaDTO></b> Listado de DTOs con los
	 *         datos para la solicitud de notificación de aportes PILA
	 */
	@GET
	@Path("/consultarDatosComunicadoByIdPlanilla")
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicadoByIdPlanilla(@QueryParam("idPlanilla") @NotNull Long idPlanilla);

	/**
	 * Método para la eliminación de temporales de novedad por listado de IDs de
	 * registro detallado
	 * 
	 * @param idsDetalle
	 *            Listado de IDs de Registro Detallado
	 */
	@POST
	@Path("/eliminarTemAporteProcesado")
	public void eliminarTemAporteProcesado(@NotNull List<Long> idsDetalle);

	/**
	 * Servicio para la actualización de marcas de temporal en proceso para PILA
	 * @param infoPlanillas
	 * 		  Listado de DTO con los datos de las planillas a marcar 
	 * @param esAporte
	 * 	      Indica que se están marcando temporales de aportes o de novedades
	 * @param enProceso
	 *        Indica que el temporal está en proceso o no
	 */
	@POST
	@Path("/actualizarMarcaProcesoTemporales")
	public void actualizarMarcaProcesoTemporales(@NotNull List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas,
			@QueryParam("esAporte") @NotNull Boolean esAporte, @QueryParam("enProceso") @NotNull Boolean enProceso);
	
	
	@GET
	@Path("/actualizarCategoriaAfiliadosAporteFuturo")
	public void actualizarCategoriaAfiliadosAporteFuturo(@QueryParam("fecha") String fecha);
	
	@GET
	@Path("/ejecutarCalculoCategoriasMasiva")
	public void ejecutarCalculoCategoriasMasiva();
		
    /**
	 * Método que consulta las novedades que puede ser procesadas y el número de
	 * novedades
	 * 
	 * @return
	 */
	@GET
	@Path("/consultarInformacionNovedadesRegistrarProcesarByIdPlanilla")
	public List<InformacionPlanillasRegistrarProcesarDTO> 
            consultarInformacionNovedadesRegistrarProcesarByIdPlanilla(
                @QueryParam("idPlanilla") Long idPlanilla,
                @QueryParam("omitirMarca") Boolean omitirMarca);
	
	/**
	 * Servicio encargado de la preparaciónde los datos para el proceso de
	 * persistencia de aportes
	 * 
	 * @param idRegistroGeneral
	 *            identificador del registro general del cual se quiere consultar información
	 * @returm <b></b> DTO que contiene toda la información requerida para
	 *         procesar los aportes en Core
	 */
	@POST
	@Path("/prepararDatosRegistroAportes")
	public DatosPersistenciaAportesDTO prepararDatosRegistroAportes(
			@QueryParam("idRegistroGeneral") @NotNull Long idRegistroGeneral, 
			@QueryParam("tamanoPaginador") @NotNull Integer tamanoPaginador);
	
	
	/**
     * Servicio que modifica la parametrización de las tasas de interes de mora de aportes
     */
    @POST
    @Path("/modificarTasaInteresMoraAportes")
    public ResultadoModificarTasaInteresDTO modificarTasaInteresMoraAportes(ModificarTasaInteresMoraDTO tasaModificada);
    
    /**
     * 
     * @return
     */
    @GET
    @Path("/consultarTasasInteresMoraAportes")
    public List<TasasInteresMoraModeloDTO> consultarTasasInteresMoraAportes();
    
    /**
     * Servicio que crea una nueva parametrización de mora aportes para un periodo
     * @param nuevaTasa
     * @return
     */
    @POST
    @Path("/crearTasaInteresInteresMora")
    public Boolean crearTasaInteresInteresMora(ModificarTasaInteresMoraDTO nuevaTasa);
    
    /**
     * Genera y almacena el archivo correspondiente al cirre - precierre de recaudo
     * @param generacionArchivo
     * @return
     */
    @POST
    @Path("/generarArchivoCierreRecaudo")
    public SolicitudCierreRecaudoModeloDTO generarArchivoCierreRecaudo(GeneracionArchivoCierreDTO generacionArchivo);

    /**
	 * Método que consulta las novedades que puede ser procesadas y el número de
	 * novedades
	 * 
	 * @return
	 */
	@GET
	@Path("/consultarInformacionNovedadesRegistrarProcesarFuturas")
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesRegistrarProcesarFuturas();

	/**
	 * Método que consulta las novedades que puede ser procesadas y el número de
	 * novedades
	 * 
	 * @return
	 */
	@GET
	@Path("/buscarNotificacionPlanillasN/{idRegistroGeneral}")
	public Long buscarNotificacionPlanillasN(@NotNull @PathParam("idRegistroGeneral")  Long idRegistroGeneral);

	@POST
	@Path("/consultarNovedadesYaProcesadasCORE")
	public List<Object[]>  consultarNovedadesYaProcesadasCORE(
			@NotNull List<Long> registrosDetallados);

	/**
	 * Servicio que consulta las cuentas de un aporte por el id del aporte
	 * general mostrando los recaudos y ajustes ya sea de corrección o
	 * devolución
	 * 
	 * @param idAporteGeneral
	 * @return
	 */
	@POST
	@Path("/consultarCuentaAporteVistaBoton")
	public List<CuentaAporteDTO> consultarCuentaAporteVistaBoton(@QueryParam("idPersonaCotizante") Long idPersonaCotizante,
			List<AnalisisDevolucionDTO> analisisDevolucionDTO,@Context UriInfo uri, @Context HttpServletResponse response, TipoMovimientoRecaudoAporteEnum tipo);


	@GET
	@Path("/cargarAutomaticamenteArchivosCrucesAportesAutomatico")
	public void cargarAutomaticamenteArchivosCrucesAportesAutomatico();

}
