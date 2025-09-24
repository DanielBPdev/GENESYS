package com.asopagos.aportes.ejb;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.asopagos.aportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.aportes.business.interfaces.IConsultasModeloPILA;
import com.asopagos.aportes.business.interfaces.IConsultasModeloStaging;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.ArchivoCierreDTO;
import com.asopagos.aportes.dto.ConsultaAporteRelacionadoDTO;
import com.asopagos.aportes.dto.ConsultaMovimientoIngresosDTO;
import com.asopagos.aportes.dto.ConsultaPlanillaResultDTO;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.ConsultarCotizanteDTO;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import com.asopagos.aportes.dto.CorreccionVistasDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosAfiliadoServiciosDTO;
import com.asopagos.aportes.dto.DatosAnalisisDevolucionDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.aportes.dto.DatosConsultaCuentaAporteDTO;
import com.asopagos.aportes.dto.DatosConsultaSolicitudesAporDevCorDTO;
import com.asopagos.aportes.dto.DatosConsultaSubsidioPagadoDTO;
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
import com.asopagos.aportes.dto.RecaudoCotizanteDTO;
import com.asopagos.aportes.dto.RegistroAporteDTO;
import com.asopagos.aportes.dto.RegistrosArchivoAporteDTO;
import com.asopagos.aportes.dto.ResultadoConsultaNovedadesExistentesDTO;
import com.asopagos.aportes.dto.ResultadoDetalleRegistroDTO;
import com.asopagos.aportes.dto.ResultadoModificarTasaInteresDTO;
import com.asopagos.aportes.dto.ResultadoRecaudoCotizanteDTO;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.aportes.dto.SolicitudAporteHistoricoDTO;
import com.asopagos.aportes.service.AportesService;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import com.asopagos.dto.*;
import com.asopagos.dto.modelo.TasasInteresMoraModeloDTO;
import com.asopagos.aportes.util.ArchivoAportesUtil;
import com.asopagos.aportes.util.FuncionesUtilitarias;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.afiliados.dto.AfiliadoPensionadoVista360DTO;
import com.asopagos.afiliados.dto.AfiliadoIndependienteVista360DTO;
import com.asopagos.afiliados.clients.ObtenerInfoDetalladaAfiliadoComoPensionado;
import com.asopagos.afiliados.clients.ObtenerInfoDetalladaAfiliadoComoIndependiente;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesDatosAportesPila;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.aportes.AportePilaDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.HistoricoDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.aportes.ResultadoArchivoAporteDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.CorreccionModeloDTO;
import com.asopagos.dto.modelo.DevolucionAporteDetalleModeloDTO;
import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.InformacionFaltanteAportanteModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.dto.modelo.TemAporteActualizadoModeloDTO;
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
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.pila.staging.Transaccion;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoGestionAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.MarcaPeriodoEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.PilaAccionTransitorioEnum;
import com.asopagos.enumeraciones.aportes.PilaEstadoTransitorioEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoRegistroEnum;
import com.asopagos.enumeraciones.aportes.TipoRegistroRecaudoEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.fovis.PlazoVencimientoEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoServiciosEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.clients.ObtenerCorreosPrioridad;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.personas.clients.BuscarPersonas;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;

import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;

import javax.ws.rs.core.UriInfo;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import com.asopagos.aportes.masivos.clients.CargarAutomaticamenteArchivosCrucesAportes;
import java.math.BigInteger;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con el registro o relación de aportes
 * 
 * <b>Módulo:</b> Asopagos - HU-211-397 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class AportesBusiness implements AportesService {

	/**
	 * Inject del EJB para consultas en modelo PILA entityManagerPila
	 */
	@Inject
	private IConsultasModeloPILA consultasPila;

	/**
	 * Inject del EJB para consultas en modelo Staging entityManagerStPila
	 */
	@Inject
	private IConsultasModeloStaging consultasStaging;

	/**
	 * Inject del EJB para consultas en modelo Core entityManager
	 */
	@Inject
	private IConsultasModeloCore consultasCore;

	/**
	 * Se inyecta la clase FileLoaderInterface del componente de LION
	 */
	@Inject
	private FileLoaderInterface fileLoader;
	
	@Resource
	private ManagedExecutorService mes;

	/**
	 * Instancia del gestor de registro de eventos.
	 */
	private static final ILogger logger = LogManager.getLogger(AportesBusiness.class);
	
	/**
	 * Numero de consultas a ejecutar por hilo
	 */
	private final Integer CONSULTAS_POR_HILO = 100;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearSolicitudAporte(com.
	 *      asopagos.dto.modelo.SolicitudAporteModeloDTO)
	 */
	@Override
	public SolicitudAporteModeloDTO crearSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteDTO) {
		logger.debug("Inicio de método guardarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteDTO)");
		try {
			SolicitudAporte solicitudAporte = solicitudAporteDTO.convertToEntity();
			/* se guarda la solicitud del aporte */
			consultasCore.crearSolicitudAporte(solicitudAporte);
			solicitudAporteDTO.convertToDTO(solicitudAporte);
			logger.debug("Fin de método guardarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteDTO)");
			return solicitudAporteDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en guardarSolicitudAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizarEstadoSolicitud(
	 *      java.lang.Long,
	 *      com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum)
	 */
	@Override
	public void actualizarEstadoSolicitud(Long idSolicitudGlobal, EstadoSolicitudAporteEnum estado) {
		logger.debug("Inicio de método actualizarEstadoSolicitud(Long idSolicitud, EstadoSolicitudAporteEnum estado)");
		try {
			SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAporte(idSolicitudGlobal);
			SolicitudAporte solicitudAporte = solicitudAporteDTO.convertToEntity();

			/*
			 * se verifica si el nuevo estado es CERRADA para actualizar el
			 * resultado del proceso
			 */
			if (EstadoSolicitudAporteEnum.CERRADA.equals(estado)) {
				if (EstadoSolicitudAporteEnum.NOTIFICADA.equals(solicitudAporte.getEstadoSolicitud())) {
					solicitudAporte.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.APROBADA);
				} else if (EstadoSolicitudAporteEnum.DESISTIDA.equals(solicitudAporte.getEstadoSolicitud())
						|| EstadoSolicitudAporteEnum.CANCELADA.equals(solicitudAporte.getEstadoSolicitud())) {
					solicitudAporte.getSolicitudGlobal().setResultadoProceso(
							ResultadoProcesoEnum.valueOf(solicitudAporte.getEstadoSolicitud().name()));
				}
			}

			solicitudAporte.setEstadoSolicitud(estado);
			consultasCore.actualizarSolicitud(solicitudAporte);
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en actualizarEstadoSolicitud: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		logger.debug("Fin de método actualizarEstadoSolicitud(Long idSolicitud, EstadoSolicitudAporteEnum estado)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * actualizarEstadoSolicitudCorreccion(java.lang.Long,
	 * com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum)
	 */
	@Override
	public void actualizarEstadoSolicitudCorreccion(Long idSolicitudGlobal, EstadoSolicitudAporteEnum estado) {
		logger.debug("Inicio de método actualizarEstadoSolicitudCorreccion");
		try {
			SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO = consultarSolicitudCorreccionAporte(
					idSolicitudGlobal);
			SolicitudCorreccionAporte solicitudCorreccionAporte = solicitudCorreccionAporteDTO.convertToEntity();

			// Verifica si el nuevo estado es CERRADA, para actualizar el
			// resultado del proceso
			if (EstadoSolicitudAporteEnum.CERRADA.equals(estado)) {
				if (EstadoSolicitudAporteEnum.NOTIFICADA.equals(solicitudCorreccionAporte.getEstadoSolicitud())
						|| EstadoSolicitudAporteEnum.APROBADA.equals(solicitudCorreccionAporte.getEstadoSolicitud())) {
					solicitudCorreccionAporte.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.APROBADA);
				} else if (EstadoSolicitudAporteEnum.RECHAZADA.equals(solicitudCorreccionAporte.getEstadoSolicitud())) {
					solicitudCorreccionAporte.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
				} else if (EstadoSolicitudAporteEnum.DESISTIDA.equals(solicitudCorreccionAporte.getEstadoSolicitud())
						|| EstadoSolicitudAporteEnum.CANCELADA.equals(solicitudCorreccionAporte.getEstadoSolicitud())) {
					solicitudCorreccionAporte.getSolicitudGlobal().setResultadoProceso(
							ResultadoProcesoEnum.valueOf(solicitudCorreccionAporte.getEstadoSolicitud().name()));
				}
			}

			solicitudCorreccionAporte.setEstadoSolicitud(estado);
			consultasCore.crearActualizarSolicitudCorreccionAporte(solicitudCorreccionAporte);
			logger.debug("Fin de método actualizarEstadoSolicitudCorreccion");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en actualizarEstadoSolicitudCorreccion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * actualizarEstadoSolicitudDevolucion(java.lang.Long,
	 * com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum)
	 */
	@Override
	public void actualizarEstadoSolicitudDevolucion(Long idSolicitudGlobal, EstadoSolicitudAporteEnum estado) {
		logger.debug("Inicio de método actualizarEstadoSolicitudDevolucion");
		try {
			SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = consultarSolicitudDevolucionAporte(
					idSolicitudGlobal);
			SolicitudDevolucionAporte solicitudDevolucionAporte = solicitudDevolucionAporteDTO.convertToEntity();

			// Verifica si el nuevo estado es CERRADA, para actualizar el
			// resultado del proceso
			if (EstadoSolicitudAporteEnum.CERRADA.equals(estado)) {
				if (EstadoSolicitudAporteEnum.NOTIFICADA.equals(solicitudDevolucionAporte.getEstadoSolicitud())
						|| EstadoSolicitudAporteEnum.PAGO_PROCESADO
								.equals(solicitudDevolucionAporte.getEstadoSolicitud())) {
					solicitudDevolucionAporte.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.APROBADA);
				} else if (EstadoSolicitudAporteEnum.RECHAZADA.equals(solicitudDevolucionAporte.getEstadoSolicitud())) {
					solicitudDevolucionAporte.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
				} else if (EstadoSolicitudAporteEnum.DESISTIDA.equals(solicitudDevolucionAporte.getEstadoSolicitud())
						|| EstadoSolicitudAporteEnum.CANCELADA.equals(solicitudDevolucionAporte.getEstadoSolicitud())) {
					solicitudDevolucionAporte.getSolicitudGlobal().setResultadoProceso(
							ResultadoProcesoEnum.valueOf(solicitudDevolucionAporte.getEstadoSolicitud().name()));
				}
			}
			solicitudDevolucionAporte.setEstadoSolicitud(estado);
			consultasCore.crearActualizarSolicitudDevolucionAporte(solicitudDevolucionAporte); 
			logger.debug("Fin de método actualizarEstadoSolicitudDevolucion");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en actualizarEstadoSolicitudDevolucion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitudAporte(java
	 *      .lang.Long)
	 */
	@Override
	public SolicitudAporteModeloDTO consultarSolicitudAporte(Long idSolicitud) {
		logger.debug("Inicio de método consultarSolicitudAporte(Long idSolicitud)");
		try {
			SolicitudAporte solicitudAporte = consultasCore.consultarSolicitudAporte(idSolicitud);
			SolicitudAporteModeloDTO solicitudAporteModeloDTO = new SolicitudAporteModeloDTO();
			solicitudAporteModeloDTO.convertToDTO(solicitudAporte);
			logger.debug("Fin de método consultarSolicitudAporte(Long idSolicitud)");
			return solicitudAporteModeloDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarSolicitudAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizarSolicitudAporte(com
	 *      .asopagos.dto.modelo.SolicitudAporteModeloDTO)
	 */
	@Override
	public void actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteDTO) {
		logger.debug("Inicio de método actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteDTO)");
		try {
			SolicitudAporte solicitudAporte = solicitudAporteDTO.convertToEntity();
			/* se guarda la solicitud del aporte */
			consultasCore.actualizarSolicitud(solicitudAporte);
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en actualizarSolicitudAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		logger.debug("Fin de método actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteDTO)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearAporteGeneral(com.
	 *      asopagos.dto.modelo.AporteGeneralModeloDTO)
	 */
	@Override
	@Deprecated
	public AporteGeneralModeloDTO crearAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
		logger.info("Inicio de método crearAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO)");
		if (aporteGeneralDTO.getMarcaPeriodo() == null) {
			aporteGeneralDTO.setMarcaPeriodo(consultarPeriodoAporte(aporteGeneralDTO.getPeriodoAporte(),
					aporteGeneralDTO.getTipoSolicitante(), aporteGeneralDTO.getIdPersona()));
		}
		AporteGeneral aporteGeneral = aporteGeneralDTO.convertToEntity();

		if (aporteGeneral.getIdOperadorInformacion() != null) {
			Long idOperadorInformacion = consultasCore
					.consultarOperadorInformacionCodigo(aporteGeneral.getIdOperadorInformacion().toString());
			aporteGeneral.setIdOperadorInformacion(idOperadorInformacion);
		}
		/* se guarda aporte general */
		consultasCore.crearAporteGeneral(aporteGeneral);
		logger.info("Fin de método crearAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO)");
		aporteGeneralDTO.convertToDTO(aporteGeneral);
		return aporteGeneralDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearAporteDetallado(com.
	 *      asopagos.dto.modelo.AporteDetalladoModeloDTO)
	 */
	@Override
	public Long crearAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoModeloDTO) {
		Long idAporteDetallado = null;
		logger.debug("Inicio método crearAporteDetallado(AporteDetalladoModeloDTO)");
		try {
			AporteDetallado aporteDetallado = null;
			try {
				if (aporteDetalladoModeloDTO.getId() != null) {
					aporteDetallado = consultasCore.consultarAporteDetallado(aporteDetalladoModeloDTO.getId());
					AporteGeneral aporteGeneral = consultasCore
							.consultarAporteGeneral(aporteDetallado.getIdAporteGeneral());
					aporteDetalladoModeloDTO.setMarcaPeriodo(aporteGeneral.getMarcaPeriodo());
					logger.info("Datos del aporte detalladoDTO: " + aporteDetalladoModeloDTO.toString());
					aporteDetallado = aporteDetalladoModeloDTO.copyDTOToEntiy(aporteDetallado);
					logger.info("Datos del aporte detallado: " + aporteDetallado.toString());
				}
			} catch (NoResultException e) {
				aporteDetallado = null;
			}
			if (aporteDetallado != null) {
				consultasCore.actualizarAporteDetallado(aporteDetallado);
			} else {
				aporteDetalladoModeloDTO.setFechaCreacion(new Date());
				AporteGeneral aporteGeneral = consultasCore
						.consultarAporteGeneral(aporteDetalladoModeloDTO.getIdAporteGeneral());
				aporteDetalladoModeloDTO.setMarcaPeriodo(aporteGeneral.getMarcaPeriodo());
				aporteDetallado = aporteDetalladoModeloDTO.convertToEntity();
				consultasCore.crearAporteDetallado(aporteDetallado);
			}
			idAporteDetallado = aporteDetallado.getId();
			logger.debug("Fin método crearAporteDetallado(AporteDetalladoModeloDTO)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en actualizarSolicitudAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return idAporteDetallado;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearInfoFaltante(java.util.
	 *      List)
	 */
	@Override
	public void crearInfoFaltante(List<InformacionFaltanteAportanteModeloDTO> infoFaltanteDTO) {
		logger.debug("Inicio de método crearInfoFaltante(InformacionFaltanteAportanteModeloDTO infoFaltanteDTO)");
		try {
			for (InformacionFaltanteAportanteModeloDTO inforFaltante : infoFaltanteDTO) {
				InformacionFaltanteAportante infoFaltanteAportante = inforFaltante.convertToEntity();
				infoFaltanteAportante.setFechaRegistro(new Date());
				// Si el idInformacionFaltante de infoFaltanteDTO no es null se
				// actualiza la entidad, que ya existe.
				if (inforFaltante.getIdInformacionFaltante() != null) {
					consultasCore.actualizarInfoFaltante(infoFaltanteAportante);
					logger.debug(
							"Fin de método crearInfoFaltante(InformacionFaltanteAportanteModeloDTO infoFaltanteDTO)");
				}
				// Si no, es porque el idInformacionFaltante de infoFaltanteDTO
				// es null y se persiste entonces la entidad,
				// pues no existe todavía.
				else {
					consultasCore.crearInfoFaltante(infoFaltanteAportante);
					logger.debug(
							"Fin de método crearInfoFaltante(InformacionFaltanteAportanteModeloDTO infoFaltanteDTO)");
				}
			}
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en crearInfoFaltante: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarInfoFaltante(java.
	 *      lang.Long)
	 */
	@Override
	public List<InformacionFaltanteAportanteModeloDTO> consultarInfoFaltante(Long idSolicitud) {
		logger.debug("Inicio de método consultarInfoFaltante(Long idSolicitud)");
		try {
			List<InformacionFaltanteAportante> infosFaltantesAportante = consultasCore
					.consultarInformacionFaltanteId(idSolicitud);
			List<InformacionFaltanteAportanteModeloDTO> infosFaltantesAportanteDTO = new ArrayList<>();
			for (InformacionFaltanteAportante infoFaltanteAportante : infosFaltantesAportante) {
				InformacionFaltanteAportanteModeloDTO infoFaltanteAportanteDTO = new InformacionFaltanteAportanteModeloDTO();
				infoFaltanteAportanteDTO.convertToDTO(infoFaltanteAportante);
				infosFaltantesAportanteDTO.add(infoFaltanteAportanteDTO);
			}
			logger.debug("Fin de método consultarInfoFaltante(Long idSolicitud)");
			return infosFaltantesAportanteDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarInfoFaltante: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#ejecutarArmadoStaging(java.
	 *      lang.Long)
	 */
	@Override
	public void ejecutarArmadoStaging(Long idTransaccion) {
		logger.debug("Inicio de método ejecutarArmadoStaging(Long idTransaccion)");
		consultasPila.ejecutarBloqueStaging(idTransaccion);
		logger.debug("Fin de método ejecutarArmadoStaging(Long idTransaccion)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarHistoricoSolicitudes
	 *      (com.asopagos.aportes.dto.SolicitudAporteHistoricoDTO)
	 */
	@Override
	public List<SolicitudAporteHistoricoDTO> consultarHistoricoSolicitudes(
			SolicitudAporteHistoricoDTO solicitudAporteDTO) {
		try {
			logger.debug("Fin de método consultarHistoricoSolicitudes(SolicitudAporteHistoricoDTO solicitudAporteDTO)");
			CriteriaBuilder cb = consultasCore.obtenerCriteriaBuilder();
			CriteriaQuery<SolicitudAporte> c = cb.createQuery(SolicitudAporte.class);
			Root<SolicitudAporte> solicitudAporte = c.from(SolicitudAporte.class);
			c.select(solicitudAporte);
			List<SolicitudAporte> solicitudes = null;

			List<Predicate> predicates = new ArrayList<Predicate>();
			if (solicitudAporteDTO.getTipoIdentificacion() != null) {
				predicates.add(cb.equal(solicitudAporte.get("tipoIdentificacion"),
						solicitudAporteDTO.getTipoIdentificacion()));
			}
			if (solicitudAporteDTO.getNumeroIdentificacion() != null) {
				predicates.add(cb.equal(solicitudAporte.get("numeroIdentificacion"),
						solicitudAporteDTO.getNumeroIdentificacion()));
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			if (solicitudAporteDTO.getPeriodoConsulta() != null) {
				String fechaPagoString = null;
				fechaPagoString = dateFormat.format(new Date(solicitudAporteDTO.getPeriodoConsulta()));
				predicates.add(cb.equal(solicitudAporte.get("periodoAporte"), fechaPagoString));
			}
			if (solicitudAporteDTO.getNumeroSolicitud() != null) {
				predicates.add(cb.equal(solicitudAporte.get("solicitudGlobal").get("numeroRadicacion"),
						solicitudAporteDTO.getNumeroSolicitud()));
			}
			if (solicitudAporteDTO.getFechaInicioConsulta() != null) {
				predicates.add(cb.greaterThanOrEqualTo(solicitudAporte.get("solicitudGlobal").get("fechaRadicacion"),
						new Date(solicitudAporteDTO.getFechaInicioConsulta())));
			}
			if (solicitudAporteDTO.getFechaFinConsulta() != null) {
				Date fechaFinConsulta = Date.from(Instant.ofEpochMilli(solicitudAporteDTO.getFechaFinConsulta()).atZone(ZoneId.systemDefault())
	                    .toLocalDate().atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant()); 

				predicates.add(cb.lessThanOrEqualTo(solicitudAporte.get("solicitudGlobal").get("fechaRadicacion"),
						fechaFinConsulta));
			}
			c.select(solicitudAporte).where(predicates.toArray(new Predicate[] {}))
					.orderBy(cb.desc(solicitudAporte.get("solicitudGlobal").get("fechaRadicacion")));
			solicitudes = consultasCore.obtenerListaSolicitudes(c);
			List<SolicitudAporteHistoricoDTO> solicitudesHistorico = new ArrayList<>();
			for (SolicitudAporte solicitud : solicitudes) {
				SolicitudAporteHistoricoDTO solicitudHistoricoDTO = new SolicitudAporteHistoricoDTO();
				solicitudHistoricoDTO.setNumeroIdentificacion(solicitud.getNumeroIdentificacion());
				solicitudHistoricoDTO.setNumeroSolicitud(solicitud.getSolicitudGlobal().getNumeroRadicacion());
				if (solicitud.getPeriodoAporte() != null) {
					solicitudHistoricoDTO.setPeriodoConsulta(dateFormat.parse(solicitud.getPeriodoAporte()).getTime());
				}
				solicitudHistoricoDTO.setTipoIdentificacion(solicitud.getTipoIdentificacion());
				solicitudHistoricoDTO.setIdSolicitud(solicitud.getSolicitudGlobal().getIdSolicitud());
				solicitudHistoricoDTO.setEstadoSolicitud(solicitud.getEstadoSolicitud());
				solicitudHistoricoDTO.setFechaRadicacion(solicitud.getSolicitudGlobal().getFechaCreacion().getTime());
				solicitudHistoricoDTO.setNombreAportante(solicitud.getNombreAportante());
				solicitudesHistorico.add(solicitudHistoricoDTO);
			}

			logger.debug("Fin de método consultarHistoricoSolicitudes(SolicitudAporteHistoricoDTO solicitudAporteDTO)");
			return solicitudesHistorico;
		} catch (Exception e) {
			logger.error("Ocurrio un error en consultarHistoricoSolicitudes", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearRegistroGeneral(com.
	 *      asopagos.dto.modelo.RegistroGeneralModeloDTO)
	 */
	@Override
	public Long crearRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO) {
		try {
			logger.debug("Fin de método crearRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO)");
			RegistroGeneral registroGeneral = registroGeneralDTO.convertToEntity();
			if (registroGeneral.getId() != null) {
				consultasStaging.actualizarRegistroGeneral(registroGeneral);
			} else {
				consultasStaging.crearRegistroGeneral(registroGeneral);
			}
			logger.debug("Fin de método crearRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO)");
			return registroGeneral.getId();
		} catch (Exception e) {
			logger.error("Ocurrio un errror creando un registro general", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRegistroGeneral(java
	 *      .lang.Long)
	 */
	@Override
	public RegistroGeneralModeloDTO consultarRegistroGeneral(Long idSolicitud) {
		RegistroGeneralModeloDTO result = consultarRegGen(idSolicitud, Boolean.FALSE);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRegistroGeneralLimitado(java.lang.Long)
	 */
	@Override
	public RegistroGeneralModeloDTO consultarRegistroGeneralLimitado(Long idSolicitud) {
		RegistroGeneralModeloDTO result = consultarRegGen(idSolicitud, Boolean.TRUE);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRegistroGeneralLimitado(java.lang.Long)
	 */
	@Override
	public RegistroGeneralModeloDTO consultarRegistroGeneralLimitadoIdRegGen(Long idSolicitud, Long idRegistroGeneral) {
		RegistroGeneralModeloDTO result = consultarRegGenId(idSolicitud, Boolean.TRUE, idRegistroGeneral);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRegistroGeneralLimitado(java.lang.Long)
	 */
	@Override
	public Boolean consultarPlanillaNNotificar(Long idPlanilla) {
		Boolean notificar = false;
		notificar  = consultasCore.validarNovedadesPila(idPlanilla);
		return notificar;
	}
	
	/**
	 * @param idSolicitud
	 * @param limitar
	 * @return
	 */
	private RegistroGeneralModeloDTO consultarRegGen(Long idSolicitud, Boolean limitar) {

		try {
			logger.info("*__**Inicio de método consultarRegGen(Long idSolicitud, Boolean)");
			RegistroGeneral registroGeneral = consultasStaging.consultarRegistroGeneral(idSolicitud, limitar);

			if (registroGeneral != null) {
				RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();
				registroGeneralDTO.convertToDTO(registroGeneral);
				logger.debug("Fin de método consultarRegistroGeneral(Long idSolicitud)");
				return registroGeneralDTO;
			} else {
				logger.debug("Fin de método consultarRegistroGeneral(Long idSolicitud)");
				return null;
			}
		} catch (NoResultException nre) {
			return null;
		} catch (NonUniqueResultException nue) {
			logger.error("Existe mas de un registro general para una solicitud de aporte", nue);
			throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);

		} catch (Exception e) {
			logger.error("Ocurrio un error en consultarRegistroGeneral", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}
	
	/**
	 * @param idSolicitud
	 * @param limitar
	 * @return
	 */
	private RegistroGeneralModeloDTO consultarRegGenId(Long idSolicitud, Boolean limitar, Long idRegistroGeneral) {

		try {
			logger.debug("Inicio de método consultarRegGen(Long idSolicitud, Boolean)");
			RegistroGeneral registroGeneral = consultasStaging.consultarRegistroGeneralIdRegGen(idSolicitud, limitar, idRegistroGeneral);

			if (registroGeneral != null) {
				RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();
				registroGeneralDTO.convertToDTO(registroGeneral);
				logger.debug("Fin de método consultarRegistroGeneral(Long idSolicitud)");
				return registroGeneralDTO;
			} else {
				logger.debug("Fin de método consultarRegistroGeneral(Long idSolicitud)");
				return null;
			}
		} catch (NoResultException nre) {
			return null;
		} catch (NonUniqueResultException nue) {
			logger.error("Existe mas de un registro general para una solicitud de aporte", nue);
			throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);

		} catch (Exception e) {
			logger.error("Ocurrio un error en consultarRegistroGeneral", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearRegistroDetallado(com.
	 *      asopagos.dto.modelo.RegistroDetalladoModeloDTO)
	 */
	@Override
	public Long crearRegistroDetallado(RegistroDetalladoModeloDTO registroDetalladoDTO) {
		try {
			logger.debug("Inicio de método crearRegistroDetallado(RegistroDetalladoModeloDTO registroDetalladoDTO)");

			RegistroDetallado registroDetallado = registroDetalladoDTO.convertToEntity();
			if (registroDetallado.getId() != null) {
				consultasStaging.actualizarRegistroDetallado(registroDetallado);
			} else {
				consultasStaging.crearRegistroDetallado(registroDetallado);
			}
			logger.debug("Fin de método crearRegistroDetallado(RegistroDetalladoModeloDTO registroDetalladoDTO)");
			return registroDetallado.getId();
		} catch (Exception e) {
			logger.error("Ocurrio un errror creando un registro detallado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearTransaccion()
	 */
	@Override
	public Long crearTransaccion() {
		try {
			logger.debug("Inicio de método crearTransaccion()");
			Transaccion transaccion = new Transaccion();
			transaccion.setFechaTransaccion(new Date());
			consultasStaging.crearTransaccion(transaccion);
			logger.debug("Fin de método crearTransaccion()");
			return transaccion.getId();
		} catch (Exception e) {
			logger.error("Ocurrio un errror creando un registro detallado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#simularFasePila2(java.lang.
	 *      Long)
	 */
	@Override
	public void simularFasePila2(Long idTransaccion) {
		logger.debug("Inicio de método simularFasePila2(Long idTransaccion)");
		consultasPila.simularFaseUnoPilaDos(idTransaccion);
		logger.debug("Fin de método simularFasePila2(Long idTransaccion)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 *      validarArchivoPagoManualAportes(com.asopagos.dto.InformacionArchivoDTO)
	 */
	@Override
	public ResultadoArchivoAporteDTO validarArchivoPagoManualAportes(InformacionArchivoDTO archivoDTO) {
		String firmaServicio = "AportesBusiness.validarEstructuraArchivoPagoManualAportes(InformacionArchivoDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		ResultadoArchivoAporteDTO resultadoArchivoAporteDTO = null;
		// se toma el Id de lectura de independientes y dependientes de las
		// constantes
		Long idLectura = null;
		try {
			idLectura = new Long(CacheManager
					.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_PAGO_MANUAL_APORTES).toString());
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
		}

		resultadoArchivoAporteDTO = procesarArchivoPagoManual(archivoDTO, idLectura);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return resultadoArchivoAporteDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#validarArchivoPagoManualAportesPensionados(com.asopagos.dto.InformacionArchivoDTO)
	 */
	@Override
	public ResultadoArchivoAporteDTO validarArchivoPagoManualAportesPensionados(InformacionArchivoDTO archivoDTO) {
		String firmaServicio = "AportesBusiness.validarEstructuraArchivoPagoManualAportes(InformacionArchivoDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		ResultadoArchivoAporteDTO resultadoArchivoAporteDTO = null;
		// se toma el Id de lectura de independientes y dependientes de las
		// constantes
		Long idLectura = null;
		try {
			idLectura = new Long(CacheManager
					.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_PAGO_MANUAL_APORTES_PEN).toString());
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
		}

		resultadoArchivoAporteDTO = procesarArchivoPagoManual(archivoDTO, idLectura);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return resultadoArchivoAporteDTO;
	}

	/**
	 * Método encargado del procesamiento de una rchivo de aportes manuales con
	 * base en si ID de lectura
	 * 
	 * @param archivoDTO
	 * @param idLectura
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ResultadoArchivoAporteDTO procesarArchivoPagoManual(InformacionArchivoDTO archivoDTO, Long idLectura) {
		String firmaMetodo = "AportesBusiness.procesarArchivoPagoManual(InformacionArchivoDTO, Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		ResultadoArchivoAporteDTO resultadoArchivoAporteDTO = null;

		if (!archivoDTO.getFileType().equals(ArchivoMultipleCampoConstants.TEXT_PLAIN)) {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
		}

		// Ajuste novedad IRL campo activado GLPI 44927
		if (new Long(CacheManager
		.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_PAGO_MANUAL_APORTES).toString())
		.compareTo(new Long(idLectura)) == 0) {
			// Si novedad IRL == 00 entonces se convierten los dos 0 a espacios
			
			byte[] fileTxt = archivoDTO.getDataFile();

			logger.info("Ajuste novedades: " + archivoDTO);
			for (int i = 0; i < (int) ((fileTxt.length + (2* i) )/ 336.0) ; i++) {
				logger.info(144 + (2* i) + (336 * i) + ":" + fileTxt[144 + (2* i) + (336 * i)]);
				logger.info(143 + (2* i) + (336 * i) + ":" + fileTxt[143 + (2* i) + (336 * i)]);
				if (fileTxt[144 + (2* i) + (336 * i)] == 48 && fileTxt[143 + (2* i) + (336 * i)] == 48) {
					fileTxt[144 + (2* i) + (336 * i)] = 32;
					fileTxt[143 + (2* i) + (336 * i)] = 32;
				}
			}
			archivoDTO.setDataFile(fileTxt);
		}
		FileLoaderOutDTO fileOutDTO = null;
		Map<String, Object> context = new HashMap<>();
		List<CotizanteDTO> lstCotizantes = new ArrayList<>();
		context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, lstCotizantes);
		List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
		context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
		List<Departamento> listaDepartamentos = consultarDepartamentos();
		context.put(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO, listaDepartamentos);
		List<Municipio> listaMunicipios = consultarMunicipios();
		context.put(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO, listaMunicipios);
		try {
			resultadoArchivoAporteDTO = new ResultadoArchivoAporteDTO();
			fileOutDTO = fileLoader.validateAndLoad(context, FileFormat.FIXED_TEXT_PLAIN,
					FuncionesUtilitarias.limpiarLineasVacias(archivoDTO.getDataFile()), idLectura);
			// se actualiza el contexto
			context = fileOutDTO.getContext();
			listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) context
					.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

			if (FileLoadedState.SUCCESFUL.equals(fileOutDTO.getState())) {
				lstCotizantes = (List<CotizanteDTO>) context.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
				resultadoArchivoAporteDTO.setLstCotizantes(lstCotizantes);
			} else {
				for (DetailedErrorDTO error : fileOutDTO.getDetailedErrors()) {
					listaHallazgos.add(FuncionesUtilitarias.crearHallazgo(error.getLineNumber(), null, null));
				}
			}
			listaHallazgos.addAll(consultarListaHallazgos(idLectura, fileOutDTO));
			resultadoArchivoAporteDTO.setLstHallazgos(listaHallazgos);
		} catch (FileProcessingException e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return resultadoArchivoAporteDTO;
	}

	/**
	 * Método que consulta todos los departamentos
	 * 
	 * @return retorna la lista de departamantos consultados
	 */
	private List<Departamento> consultarDepartamentos() {
		return consultasCore.consultarDepartamentos();
	}

	/**
	 * Método que consulta todos los municipios
	 * 
	 * @return lista de todos los municipios
	 */
	private List<Municipio> consultarMunicipios() {
		return consultasCore.consultarMunicipios();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarCotizantes(com.
	 *      asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.Long)
	 */
	@Override
	public List<CotizanteDTO> consultarCotizantesPorRol(List<Long> idRoles) {
		try {
			logger.debug(
					"consultarCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,Long periodoAporte)");
			List<CotizanteDTO> cotizantes = consultasCore.consultarCotizantesPorRol(idRoles);
			logger.debug(
					"Inicio de método consultarCotizantes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,Long periodoAporte)");
			return cotizantes;
		} catch (Exception e) {
			logger.error("Ocurrio un errror radicando una solicitud", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitante(com.asopagos.enumeraciones.personas.TipoAfiliadoEnum,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public SolicitanteDTO consultarSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		try {
			logger.debug(
					"Inicio de método consultarSolicitante(TipoAfiliadoEnum tipoSolicitante, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			if (TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)
					|| TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)) {
				SolicitanteDTO solicitanteDTO = consultasCore.consultarSolicitanteIndependientePensionado(
						tipoSolicitante, tipoIdentificacion, numeroIdentificacion);
				return solicitanteDTO;
			} else {
				SolicitanteDTO solicitanteDTO = consultasCore.consultarSolicitanteEmpleador(tipoSolicitante,
						tipoIdentificacion, numeroIdentificacion);
				return solicitanteDTO;
			}
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrio un errror radicando una solicitud", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarListaSolicitantes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<SolicitanteDTO> consultarSolicitanteCorreccion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicio de método consultarSolicitanteCorreccion(TipoIdentificacionEnum, String)");
		try {
			// validacion nombre
			List<SolicitanteDTO> solicitantes = consultasCore
					.consultarPersonaSolicitanteAporteGeneral(tipoIdentificacion, numeroIdentificacion);
			List<SolicitanteDTO> solicitantesEmpresa = consultasCore
					.consultarPersonaEmpresaSolicitanteAporteGeneral(tipoIdentificacion, numeroIdentificacion);
			solicitantes.addAll(solicitantesEmpresa);
			return solicitantes;
		} catch (NoResultException e) {
			logger.debug(
					"Finaliza método consultarSolicitanteCorreccion(TipoIdentificacionEnum, String):No se encuentran registros con los parametros ingresados");
			return null;
		} catch (Exception e) {
			logger.debug(
					"Finaliza método consultarSolicitanteCorreccion(TipoIdentificacionEnum, String):Ocurrio un error técnico inesperado");
			logger.error(
					"Finaliza método consultarSolicitanteCorreccion(TipoIdentificacionEnum, String):Ocurrio un error técnico inesperado",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#ejecutarBorradoStaging(java.lang.Long)
	 */
	@Override
	public void ejecutarBorradoStaging(Long idTransaccion) {
		logger.debug("Inicio de método ejecutarArmadoStaging(Long idTransaccion)");
		consultasPila.ejecutarDeleteBloqueStaging(idTransaccion);
		logger.debug("Fin de método ejecutarArmadoStaging(Long idTransaccion)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRegistroDetallado(java.lang.Long)
	 */
	@Override
	public List<RegistroDetalladoModeloDTO> consultarRegistroDetallado(Long idRegistroGeneral) {
		try {
			logger.debug("Fin de método consultarRegistroDetallado(Long idRegistroGeneral)");
			List<RegistroDetallado> registroGeneral = consultasStaging.consultarRegistroDetallado(idRegistroGeneral);
			List<RegistroDetalladoModeloDTO> registroDetalladoList = new ArrayList<>();
			for (RegistroDetallado registroDetallado : registroGeneral) {
				RegistroDetalladoModeloDTO registroDetalladoDTO = new RegistroDetalladoModeloDTO();
				registroDetalladoDTO.convertToDTO(registroDetallado);
				registroDetalladoList.add(registroDetalladoDTO);
			}

			logger.debug("Fin de método consultarRegistroGeneral(Long idSolicitud)");
			return registroDetalladoList;
		} catch (Exception e) {
			logger.error("Ocurrio un error en consultarRegistroDetallado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Método para la consulta de registros detallados por ID de registro
	 * general y tipo de afiliado
	 */
	private List<RegistroDetalladoModeloDTO> consultarRegistroDetalladoPorTipo(Long idRegistroGeneral,
			TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoId, String numId) {
		String firmaMetodo = "AportesBusiness.consultarRegistroDetalladoPorTipo(Long, TipoAfiliadoEnum, TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<RegistroDetalladoModeloDTO> result = consultasStaging
				.consultarRegistroDetalladoPorTipoAfiliado(idRegistroGeneral, tipoAfiliado, tipoId, numId);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}


	/**
	 * Método encargado de consultar la lista hallazgos de un archivo
	 * 
	 * @param fileDefinitionId
	 * @param outDTO
	 * @return retorna la lista de hallazgos
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> consultarListaHallazgos(Long fileDefinitionId,
			FileLoaderOutDTO outDTO) {
		List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
		List<DefinicionCamposCargaDTO> campos = consultarCamposDelArchivo(fileDefinitionId);
		String mensaje = "";
		boolean encontreCampo = false;
		if (outDTO.getDetailedErrors() != null) {
			for (DetailedErrorDTO detalleError : outDTO.getDetailedErrors()) {
				mensaje = detalleError.getMessage();
				for (DefinicionCamposCargaDTO campo : campos) {
					encontreCampo = construirHallazgoDTO(listaHallazgos, mensaje, encontreCampo, detalleError, campo);
				}
			}
		}
		return listaHallazgos;
	}

	/**
	 * Método encargadod de construir un hallazgo dto
	 * 
	 * @param listaHallazgos,
	 *            lista de hallazgos
	 * @param mensaje,
	 *            mensaje a mostrar
	 * @param encontreCampo,
	 *            boolean que idenfica si se encontro el campo
	 * @param detalleError,
	 *            detalle del error
	 * @param campo,
	 *            campo a asignar el valor
	 * @return retorna la lista de hallazgos construidos
	 */
	private boolean construirHallazgoDTO(List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos, String mensaje,
			boolean encontreCampo, DetailedErrorDTO detalleError, DefinicionCamposCargaDTO campo) {
		String[] arregloMensaje;
		String mensajeHallazgo = "";
		boolean construirHallazgo = false;
		if (mensaje.contains(":")) {
			arregloMensaje = mensaje.split(":");
			for (int i = 0; i < arregloMensaje.length; i++) {
				if (arregloMensaje[i].trim().equals(campo.getName().trim())) {
					mensajeHallazgo = mensaje.replace(campo.getName(), campo.getLabel());
					construirHallazgo = true;
				}
			}
		} else {
			arregloMensaje = mensaje.split(" ");
			for (int i = 0; i < arregloMensaje.length; i++) {
				if (arregloMensaje[i].contains(campo.getName())) {
					mensajeHallazgo = mensaje.replace(campo.getName(), campo.getLabel());
					construirHallazgo = true;
				}
			}
		}
		if (construirHallazgo) {
			ResultadoHallazgosValidacionArchivoDTO hallazgo = crearHallazgo(detalleError.getLineNumber(),
					campo.getLabel(), mensajeHallazgo);
			listaHallazgos.add(hallazgo);
		}
		return encontreCampo;
	}

	/**
	 * Metodo encargado de consultar los campos del archivo
	 * 
	 * @param fileLoadedId
	 *            identificador del fileLoadedId
	 * @return lista de definiciones de campos
	 */
	private List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
		try {
			return consultasCore.buscarCamposArchivo(fileLoadedId);
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * Método encargado de crear un hallazgo dto
	 * 
	 * @param lineNumber,
	 *            número de linea
	 * @param campo,
	 *            campo al que pertenece el hallazgos
	 * @param errorMessage,
	 *            mensaje de error
	 * @return retorna el dto ResultadoHallazgosValidacionArchivoDTO
	 */
	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(errorMessage);
		return hallazgo;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarPlanilla(java.lang.Long)
	 */
	@Override
	public ConsultaPlanillaResultDTO consultarPlanilla(Long idPlanilla) {
		String firmaServicio = "AportesBusiness.consultarPlanilla(Long)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		try {
		ConsultaPlanillaResultDTO result = consultasCore.consultarPlanilla(idPlanilla);

		if (result != null) {
			result.setEsPilaManual(consultasStaging.consultarEsPlanillaManual(idPlanilla));
		}

		
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("errorPila " + e.getMessage());
			throw e;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearNotificacionesParametrizadas(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NotificacionParametrizadaDTO> crearNotificacionesParametrizadas(List<AportePilaDTO> planilla) {
		List<NotificacionParametrizadaDTO> listaNotificaciones = new ArrayList<>();
		try {
			logger.debug("Inicia método crearNotificacionesParametrizadas(List<AportePilaDTO> planilla)");

			// se procesa cadas aporte verificando el tipo de cotizante. dado
			// esto, se identifica que tipo de comunicado se debe enviar
			for (AportePilaDTO aportePilaDTO : planilla) {
				if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE
						.equals(aportePilaDTO.getAporteDetallado().getTipoCotizante())) {
					Object[] arreglo = consultasPila
							.obtenerNotificacionRegistro(aportePilaDTO.getAporteGeneral().getIdRegistroGeneral());

					Map<String, String> comunicado = crearMapaInfoComunicado(arreglo);

					NotificacionParametrizadaDTO notificacion = obtenerNotificacionDEP(aportePilaDTO, comunicado,
							TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,
							aportePilaDTO.getAporteGeneral().getIdRegistroGeneral());

					listaNotificaciones.add(notificacion);
				} else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
						.equals(aportePilaDTO.getAporteDetallado().getTipoCotizante())) {
					Object[] arreglo = consultasPila.obtenerNotificacionRegistroEspecial(
							aportePilaDTO.getAporteGeneral().getIdRegistroGeneral(),
							aportePilaDTO.getTipoIdCotizante().toString(), aportePilaDTO.getNumeroIdCotizante());
					Map<String, String> comunicado = crearMapaInfoComunicado(arreglo);

					NotificacionParametrizadaDTO notificacion = obtenerNotificacionINoPE(aportePilaDTO, comunicado,
							TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, false);
					listaNotificaciones.add(notificacion);
				} else if (TipoAfiliadoEnum.PENSIONADO.equals(aportePilaDTO.getAporteDetallado().getTipoCotizante())) {
					Object[] arreglo = consultasPila.obtenerNotificacionRegistroEspecial(
							aportePilaDTO.getAporteGeneral().getIdRegistroGeneral(),
							aportePilaDTO.getTipoIdCotizante().toString(), aportePilaDTO.getNumeroIdCotizante());
					Map<String, String> comunicado = crearMapaInfoComunicado(arreglo);

					NotificacionParametrizadaDTO notificacion = obtenerNotificacionINoPE(aportePilaDTO, comunicado,
							TipoAfiliadoEnum.PENSIONADO, true);
					listaNotificaciones.add(notificacion);
				}
			}
			logger.debug("Finaliza crearNotificacionesParametrizadas(List<AportePilaDTO> planilla)");

		} catch (Exception e) {

			logger.error("Ocurrio un error creando las notificaciones parametrizadas", e);
			logger.debug("Finaliza con errores crearNotificacionesParametrizadas(List<AportePilaDTO> planilla)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);

		}
		return listaNotificaciones;
	}

	/**
	 * Método encargado de crear el mapa con la información de la planilla para
	 * el envío del comunicado
	 * 
	 * @param arregloInformacion
	 *            contiene los datos obtenidos por el SP
	 * 
	 * @return Map<String, String> con la información para el comunicado.
	 */
	private Map<String, String> crearMapaInfoComunicado(Object[] arregloInformacion) {
		Map<String, String> comunicado = new HashMap<String, String>();
		if (arregloInformacion != null && arregloInformacion.length == 21) {
		    DecimalFormat format = new DecimalFormat("###.##");
			comunicado.put("regTipoNotificacion", arregloInformacion[0].toString());
			comunicado.put("TipoIdentificacionAportante", arregloInformacion[1].toString());
			comunicado.put("NumeroIdentificacionAportante", arregloInformacion[2].toString());
			comunicado.put("NombreAportante", arregloInformacion[3].toString());
			comunicado.put("Municipio", arregloInformacion[4].toString());
			comunicado.put("Departamento", arregloInformacion[5].toString());
			comunicado.put("NumPlanilla", arregloInformacion[6].toString());
			comunicado.put("PeriodoAporte", arregloInformacion[7].toString());
			if (arregloInformacion[8] != null) {
				comunicado.put("FechaPago", arregloInformacion[8].toString());
			} else {
				comunicado.put("FechaPago", "");
			}
			if (arregloInformacion[9] != null) {
				comunicado.put("EstadoAportante", arregloInformacion[9].toString());
			} else {
				comunicado.put("EstadoAportante", EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES.name());
			}
			comunicado.put("NumeroTrabajadores", arregloInformacion[10].toString());
			comunicado.put("NumeroTrabajadoresActivos", arregloInformacion[11].toString());
			comunicado.put("NumeroTrabajadoresDiferenteActivos", arregloInformacion[12].toString());
			comunicado.put("ExisteRegistroConAporteMayorACero", arregloInformacion[13].toString());
			comunicado.put("MontoTotalAportesRecibidos", format.format(arregloInformacion[14]));
			comunicado.put("CantidadAportesRecibidos", arregloInformacion[15].toString());
			comunicado.put("CantidadAportesMayorACero", arregloInformacion[16].toString());
			comunicado.put("InteresesMoraRecibidos", format.format(arregloInformacion[17]));
			comunicado.put("TodosLosRegistrosMarcadosOK", arregloInformacion[18].toString());
			comunicado.put("CantidadCotizantesConNovedades", arregloInformacion[19].toString());
			comunicado.put("CantidadNovedadesPlanilla", arregloInformacion[20].toString());

		}
		return comunicado;
	}

	/**
	 * Método que permite obtener la NotificacionParametrizadaDTO para el caso
	 * de un cotizante DEPENDIENTE
	 * 
	 * @param aportePilaDTO
	 *            es el DTO con la información del aporte
	 * @param comunicado
	 *            es la información que se obtuvo mediante la ejecución del
	 *            proceso almacenado
	 * @return NotificacionParametrizadaDTO para el aporte/planilla Actual.
	 */
	private NotificacionParametrizadaDTO obtenerNotificacionDEP(AportePilaDTO aportePilaDTO,
			Map<String, String> comunicado, TipoAfiliadoEnum tipoAfiliado, Long idRegistroGeneral) {
		NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
		// es la etiqueta a armar para identificar la planilla
		String EtiquetaPlantilla = "";

		if (comunicado.get("EstadoAportante") != null && comunicado.get("EstadoAportante").equals("ACTIVO")) {
			EtiquetaPlantilla = EtiquetaPlantilla + "1";
		} else {
			EtiquetaPlantilla = EtiquetaPlantilla + "0";
		}

		if (comunicado.get("NumeroTrabajadoresDiferenteActivos") != null
				&& Integer.parseInt(comunicado.get("NumeroTrabajadoresDiferenteActivos").toString()) > 0) {
			listadoCotizante(idRegistroGeneral, comunicado, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, null, null);
			EtiquetaPlantilla = EtiquetaPlantilla + "1";
		} else {
			EtiquetaPlantilla = EtiquetaPlantilla + "0";
		}

		if (comunicado.get("CantidadAportesMayorACero") != null
				&& Integer.parseInt(comunicado.get("CantidadAportesMayorACero").toString()) > 0) {
			EtiquetaPlantilla = EtiquetaPlantilla + "1";
		} else {
			EtiquetaPlantilla = EtiquetaPlantilla + "0";
		}

		if (comunicado.get("TodosLosRegistrosMarcadosOK") != null
				&& "true".equals(comunicado.get("TodosLosRegistrosMarcadosOK").toString())) {
			EtiquetaPlantilla = EtiquetaPlantilla + "1";
		} else {
			EtiquetaPlantilla = EtiquetaPlantilla + "0";
		}

		if (comunicado.get("CantidadNovedadesPlanilla") != null
				&& Integer.parseInt(comunicado.get("CantidadNovedadesPlanilla").toString()) > 0) {
			EtiquetaPlantilla = EtiquetaPlantilla + "1";
		} else {
			EtiquetaPlantilla = EtiquetaPlantilla + "0";
		}

		listadoCotizanteNoOk(idRegistroGeneral, comunicado, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, null, null);

		// se obtiene, en base a la etiqueta armada en los pasos anteriores, el
		// valor correspondiente en EtiquetaPlanillaComunicadoDTO
		notificacion.setEtiquetaPlantillaComunicado(
				EtiquetaPlantillaComunicadoEnum.getEtiquetaPlantillaPila(EtiquetaPlantilla));

		List<String> destinatarioTO = new ArrayList<>();
		Map<String, String> parametrosPlanilla = new HashMap<String, String>();

		if (comunicado.get("TipoIdentificacionAportante") != null
				&& comunicado.get("NumeroIdentificacionAportante") != null) {
			StringBuilder idPersona = new StringBuilder();
			destinatarioTO = obtenerdestinatario(
					TipoIdentificacionEnum.valueOf(comunicado.get("TipoIdentificacionAportante").toString()),
					comunicado.get("NumeroIdentificacionAportante").toString(), "RECAUDO_APORTES_PILA",
					EtiquetaPlantillaComunicadoEnum.NTF_REC_APT_PLA_DEP1, idPersona, TipoTipoSolicitanteEnum.EMPLEADOR);
			if (!idPersona.toString().isEmpty()) {
				notificacion.setIdPersona(Long.valueOf(idPersona.toString()));
			} else {
				notificacion.setIdPersona(getIdPersona(
						TipoIdentificacionEnum.valueOf(comunicado.get("TipoIdentificacionAportante").toString()),
						comunicado.get("NumeroIdentificacionAportante").toString()));
			}
			if (comunicado.containsKey("tipoSolicitante")) {
				parametrosPlanilla.put("tipoSolicitante", TipoTipoSolicitanteEnum.EMPLEADOR.getDescripcion());
			} else {
				parametrosPlanilla.put("tipoSolicitante", "");
			}
		}

		notificacion.setDestinatarioTO(destinatarioTO);

		notificacion.setReplantearDestinatarioTO(true);

		notificacion.setProcesoEvento("PILA");

		notificacion.setIdEmpresa(aportePilaDTO.getAporteGeneral().getIdEmpresa() != null
				? aportePilaDTO.getAporteGeneral().getIdEmpresa() : null);

		parametrosPlanilla.put("tipoAfiliado", tipoAfiliado.getName());
		parametrosPlanilla.put("idEmpresa", aportePilaDTO.getAporteGeneral().getIdEmpresa().toString());

		// se calcula la fecha del sistema
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate ahora = LocalDate.now();
		parametrosPlanilla.put("fechaDelSistema", timeFormat.format(ahora));

		// se terminan de adicionar los demás valores
		if (comunicado.get("NombreAportante") != null) {
			parametrosPlanilla.put("razonSocial/Nombre", comunicado.get("NombreAportante").toString());
		} else {
			parametrosPlanilla.put("razonSocial/Nombre", "");
		}

		if (comunicado.get("TipoIdentificacionAportante") != null) {
			logger.info("comunicado.get(\"TipoIdentificacionAportante\").toString() " + comunicado.get("TipoIdentificacionAportante").toString());
			parametrosPlanilla.put("tipoIdentificacion", comunicado.get("TipoIdentificacionAportante").toString());
		} else {
			parametrosPlanilla.put("tipoIdentificacion", "");
			logger.info("comunicado.get(\"TipoIdentificacionAportante\").toString() null");
		}

		if (comunicado.get("NumeroIdentificacionAportante") != null) {
			parametrosPlanilla.put("numeroIdentificacion", comunicado.get("NumeroIdentificacionAportante").toString());
		} else {
			parametrosPlanilla.put("numeroIdentificacion", "");
		}

		if (comunicado.get("NumPlanilla") != null) {
			parametrosPlanilla.put("numeroPlanilla", comunicado.get("NumPlanilla").toString());
		} else {
			parametrosPlanilla.put("numeroPlanilla", "");
		}

		if (comunicado.get("PeriodoAporte") != null) {
			parametrosPlanilla.put("periodoAporte", comunicado.get("PeriodoAporte").toString());
		} else {
			parametrosPlanilla.put("periodoAporte", "");
		}

		if (comunicado.get("FechaPago") != null) {
			parametrosPlanilla.put("fechaPagoAporte", comunicado.get("FechaPago").toString());
		} else {
			parametrosPlanilla.put("fechaPagoAporte", "");
		}

		if (comunicado.get("MontoTotalAportesRecibidos") != null) {
			parametrosPlanilla.put("montoAporteRecibido", comunicado.get("MontoTotalAportesRecibidos").toString());
		} else {
			parametrosPlanilla.put("montoAporteRecibido", "");
		}

		if (comunicado.get("CantidadAportesRecibidos") != null) {
			parametrosPlanilla.put("cantidadDeAportes", comunicado.get("CantidadAportesRecibidos").toString());
		} else {
			parametrosPlanilla.put("cantidadDeAportes", "");
		}

		if (comunicado.get("InteresesMoraRecibidos") != null) {
			parametrosPlanilla.put("interesMora", comunicado.get("InteresesMoraRecibidos").toString());
		} else {
			parametrosPlanilla.put("interesMora", "");
		}

		if (comunicado.get("CantidadAportesMayorACero") != null) {
			parametrosPlanilla.put("regitrosRecibidos", comunicado.get("CantidadAportesMayorACero").toString());
		} else {
			parametrosPlanilla.put("regitrosRecibidos", "");
		}

		if (comunicado.get("CantidadNovedadesPlanilla") != null) {
			parametrosPlanilla.put("cantidadNovedadesReportadas",
					comunicado.get("CantidadNovedadesPlanilla").toString());
		} else {
			parametrosPlanilla.put("cantidadNovedadesReportadas", "");
		}

		if (comunicado.get("CantidadCotizantesConNovedades") != null) {
			parametrosPlanilla.put("cantidadCotizantesAfectados",
					comunicado.get("CantidadCotizantesConNovedades").toString());
		} else {
			parametrosPlanilla.put("cantidadCotizantesAfectados", "");
		}

		if (comunicado.get("NumeroTrabajadores") != null) {
			parametrosPlanilla.put("numeroTrabajadores", comunicado.get("NumeroTrabajadores").toString());
		} else {
			parametrosPlanilla.put("numeroTrabajadores", "");
		}

		if (comunicado.get("NumeroTrabajadoresActivos") != null) {
			parametrosPlanilla.put("numeroTrabajadoresActivo", comunicado.get("NumeroTrabajadoresActivos").toString());
		} else {
			parametrosPlanilla.put("numeroTrabajadoresActivo", "");
		}

		if (comunicado.get("NumeroTrabajadoresDiferenteActivos") != null) {
			parametrosPlanilla.put("numeroTrabajadoresNoActivo",
					comunicado.get("NumeroTrabajadoresDiferenteActivos").toString());
		} else {
			parametrosPlanilla.put("numeroTrabajadoresNoActivo", "");
		}
		if (comunicado.containsKey("Cotizantes")) {
			parametrosPlanilla.put("cotizantes", comunicado.get("Cotizantes").toString());
		} else {
			parametrosPlanilla.put("cotizantes", "");
		}
        if (comunicado.containsKey("CotizantesNoOk")) {
            parametrosPlanilla.put("cotizantesNoOK", comunicado.get("CotizantesNoOk").toString());
        } else {
            parametrosPlanilla.put("cotizantesNoOK", "");
        }
		if (comunicado.containsKey("Municipio")) {
			parametrosPlanilla.put("municipio", comunicado.get("Municipio").toString());
		} else {
			parametrosPlanilla.put("municipio", "");
		}
		if (comunicado.containsKey("Municipio")) {
			parametrosPlanilla.put("ciudad", comunicado.get("Municipio").toString());
		} else {
			parametrosPlanilla.put("ciudad", "");
		}
		parametrosPlanilla.put("obtenerConsulta", Boolean.TRUE.toString());

		ParametrosComunicadoDTO parametros = new ParametrosComunicadoDTO();
		Map<String, Object> parametrosMapa = new HashMap<String, Object>();
		parametrosMapa.put("obtenerConsulta", Boolean.TRUE.toString());
		parametros.setParams(parametrosMapa);
		notificacion.setParams(parametrosPlanilla);
		notificacion.setParametros(parametros);

		return notificacion;
	}

	/**
	 * Método que permite obtener la NotificacionParametrizadaDTO para el caso
	 * de un cotizante INDEPENDIENTE o PENSIONADO
	 * 
	 * @param aportePilaDTO
	 *            es el DTO con la información del aporte
	 * @param comunicado
	 *            es la información que se obtuvo mediante la ejecución del
	 *            proceso almacenado
	 * @return NotificacionParametrizadaDTO para el aporte/planilla Actual.
	 */
	private NotificacionParametrizadaDTO obtenerNotificacionINoPE(AportePilaDTO aportePilaDTO,
			Map<String, String> comunicado, TipoAfiliadoEnum tipoAfiliado, boolean isPensionado) {
		NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
		// es la etiqueta a armar para identificar la planilla
		String EtiquetaPlantilla = "";

		// se realizan las validaciones determinadas para el comunicado de
		// TRABAJADOR_INDEPENDIENTE
		if (comunicado.get("EstadoAportante") != null && comunicado.get("EstadoAportante").equals("ACTIVO")) {
			EtiquetaPlantilla = EtiquetaPlantilla + "0";

			if (comunicado.get("CantidadAportesMayorACero") != null
					&& Integer.parseInt(comunicado.get("CantidadAportesMayorACero")) > 0) {
				EtiquetaPlantilla = EtiquetaPlantilla + "1";
			} else {
				EtiquetaPlantilla = EtiquetaPlantilla + "0";
			}

			if (comunicado.get("TodosLosRegistrosMarcadosOK") != null
					&& "true".equals(comunicado.get("TodosLosRegistrosMarcadosOK"))) {
				EtiquetaPlantilla = EtiquetaPlantilla + "1";
			} else {
				EtiquetaPlantilla = EtiquetaPlantilla + "0";
			}

			if (comunicado.get("CantidadNovedadesPlanilla") != null
					&& Integer.parseInt(comunicado.get("CantidadNovedadesPlanilla")) > 0) {
				EtiquetaPlantilla = EtiquetaPlantilla + "1";
			} else {
				EtiquetaPlantilla = EtiquetaPlantilla + "0";
			}
		} else {
			EtiquetaPlantilla = EtiquetaPlantilla + "1000";
			listadoCotizante(aportePilaDTO.getAporteGeneral().getIdRegistroGeneral(), comunicado,
					aportePilaDTO.getAporteDetallado().getTipoCotizante(), aportePilaDTO.getTipoIdCotizante(),
					aportePilaDTO.getNumeroIdCotizante());
		}

		// se obtiene, en base a la etiqueta armada en los pasos anteriores, el
		// valor correspondiente en EtiquetaPlanillaComunicadoDTO
		notificacion.setEtiquetaPlantillaComunicado(
				EtiquetaPlantillaComunicadoEnum.getEtiquetaPlantillaPila(EtiquetaPlantilla, isPensionado));

		List<String> destinatarioTO = new ArrayList<>();
		Map<String, String> parametrosPlanilla = new HashMap<>();
		StringBuilder idPersona = new StringBuilder();
		if (aportePilaDTO.getNumeroIdCotizante() != null && aportePilaDTO.getTipoIdCotizante() != null) {
			if (isPensionado)
				destinatarioTO = obtenerdestinatario(aportePilaDTO.getTipoIdCotizante(),
						aportePilaDTO.getNumeroIdCotizante(), "RECAUDO_APORTES_PILA",
						EtiquetaPlantillaComunicadoEnum.NTF_REC_APT_PLA_PNS1, idPersona, TipoTipoSolicitanteEnum.PERSONA);
			else
				destinatarioTO = obtenerdestinatario(aportePilaDTO.getTipoIdCotizante(),
						aportePilaDTO.getNumeroIdCotizante(), "RECAUDO_APORTES_PILA",
						EtiquetaPlantillaComunicadoEnum.NTF_REC_APT_PLA_IDPE1, idPersona, TipoTipoSolicitanteEnum.PERSONA);

			if (!idPersona.toString().isEmpty()) {
				notificacion.setIdPersona(Long.valueOf(idPersona.toString()));
			} else {
				notificacion.setIdPersona(
						getIdPersona(aportePilaDTO.getTipoIdCotizante(), aportePilaDTO.getNumeroIdCotizante()));
			}
			if (comunicado.containsKey("tipoSolicitante")) {
				parametrosPlanilla.put("tipoSolicitante", TipoTipoSolicitanteEnum.PERSONA.getDescripcion());
			} else {
				parametrosPlanilla.put("tipoSolicitante", "");
			}
		}

		notificacion.setDestinatarioTO(destinatarioTO);

		notificacion.setReplantearDestinatarioTO(true);

		notificacion.setProcesoEvento("PILA");

		parametrosPlanilla.put("tipoAfiliado", tipoAfiliado.getName());

		// se calcula la fecha del sistema
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate ahora = LocalDate.now();

		// se terminan de adicionar los demás valores
		parametrosPlanilla.put("fechaDelSistema", timeFormat.format(ahora));

		if (comunicado.get("NombreAportante") != null) {
			parametrosPlanilla.put("razonSocial/Nombre", comunicado.get("NombreAportante"));
		} else {
			parametrosPlanilla.put("razonSocial/Nombre", "");
		}

		if (comunicado.get("TipoIdentificacionAportante") != null) {
			parametrosPlanilla.put("tipoIdentificacion", comunicado.get("TipoIdentificacionAportante"));
		} else {
			parametrosPlanilla.put("tipoIdentificacion", "");
		}

		if (comunicado.get("NumeroIdentificacionAportante") != null) {
			parametrosPlanilla.put("numeroIdentificacion", comunicado.get("NumeroIdentificacionAportante"));
		} else {
			parametrosPlanilla.put("numeroIdentificacion", "");
		}

		if (comunicado.get("NumPlanilla") != null) {
			parametrosPlanilla.put("numeroPlanilla", comunicado.get("NumPlanilla"));
		} else {
			parametrosPlanilla.put("numeroPlanilla", "");
		}

		if (comunicado.get("PeriodoAporte") != null) {
			parametrosPlanilla.put("periodoAporte", comunicado.get("PeriodoAporte"));
		} else {
			parametrosPlanilla.put("periodoAporte", "");
		}

		if (comunicado.get("FechaPago") != null) {
			parametrosPlanilla.put("fechaPagoAporte", comunicado.get("FechaPago"));
		} else {
			parametrosPlanilla.put("fechaPagoAporte", "");
		}

		if (comunicado.get("MontoTotalAportesRecibidos") != null) {
			parametrosPlanilla.put("montoAporteRecibido", comunicado.get("MontoTotalAportesRecibidos"));
		} else {
			parametrosPlanilla.put("montoAporteRecibido", "");
		}

		if (comunicado.get("CantidadAportesMayorACero") != null) {
			parametrosPlanilla.put("regitrosRecibidos", comunicado.get("CantidadAportesMayorACero"));
		} else {
			parametrosPlanilla.put("regitrosRecibidos", "");
		}

		if (comunicado.get("CantidadAportesRecibidos") != null) {
			parametrosPlanilla.put("cantidadDeAportes", comunicado.get("CantidadAportesRecibidos"));
		} else {
			parametrosPlanilla.put("cantidadDeAportes", "");
		}
		if (comunicado.get("InteresesMoraRecibidos") != null) {
			parametrosPlanilla.put("interesMora", comunicado.get("InteresesMoraRecibidos"));
		} else {
			parametrosPlanilla.put("interesMora", "");
		}
		if (comunicado.get("CantidadNovedadesPlanilla") != null) {
			parametrosPlanilla.put("cantidadNovedadesReportadas", comunicado.get("CantidadNovedadesPlanilla"));
		} else {
			parametrosPlanilla.put("cantidadNovedadesReportadas", "");
		}
		if (comunicado.containsKey("Cotizantes")) {
			parametrosPlanilla.put("cotizantes", comunicado.get("Cotizantes"));
		} else {
			parametrosPlanilla.put("cotizantes", "");
		}
		if (comunicado.containsKey("Municipio")) {
			parametrosPlanilla.put("municipio", comunicado.get("Municipio").toString());
		} else {
			parametrosPlanilla.put("municipio", "");
		}
		if (comunicado.containsKey("Municipio")) {
			parametrosPlanilla.put("ciudad", comunicado.get("Municipio").toString());
		} else {
			parametrosPlanilla.put("ciudad", "");
		}
		parametrosPlanilla.put("cantidadCotizantesAfectados", "1");

		parametrosPlanilla.put("obtenerConsulta", Boolean.TRUE.toString());

		ParametrosComunicadoDTO parametros = new ParametrosComunicadoDTO();
		Map<String, Object> parametrosMapa = new HashMap<>();
		parametrosMapa.put("obtenerConsulta", Boolean.TRUE.toString());
		parametros.setParams(parametrosMapa);
		notificacion.setParams(parametrosPlanilla);
		notificacion.setParametros(parametros);

		return notificacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * crearActualizarSolicitudCorreccionAporte(com.asopagos.dto.modelo.
	 * SolicitudCorreccionAporteModeloDTO)
	 */
	@Override
	public Long crearActualizarSolicitudCorreccionAporte(
			SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO) {
		try {
			logger.debug("Inicia servicio crearActualizarSolicitudCorreccionAporte");
			SolicitudCorreccionAporte solicitudCorreccionAporte = solicitudCorreccionAporteDTO.convertToEntity();
			Long id = consultasCore.crearActualizarSolicitudCorreccionAporte(solicitudCorreccionAporte);
			logger.debug("Finaliza servicio crearActualizarSolicitudCorreccionAporte");
			return id;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio crearActualizarSolicitudCorreccionAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarSolicitudCorreccionAporte(java.lang.Long)
	 */
	@Override
	public SolicitudCorreccionAporteModeloDTO consultarSolicitudCorreccionAporte(Long idSolicitudGlobal) {
		try {
			logger.debug("Inicio de método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)");
			SolicitudCorreccionAporte solicitudCorreccionAporte = consultasCore
					.consultarSolicitudCorreccionAporte(idSolicitudGlobal);
			SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO = new SolicitudCorreccionAporteModeloDTO();
			solicitudCorreccionAporteDTO.convertToDTO(solicitudCorreccionAporte);
			logger.debug("Finaliza método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)");
			return solicitudCorreccionAporteDTO;
		} catch (NoResultException e) {
			logger.error("Ocurrió un error en el método consultarSolicitudCorreccionAporte(Long idSolicitudGlobal)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitudCorreccionAporteAporteGeneral(java.lang.Long)
	 */
	@Override
	public SolicitudCorreccionAporteModeloDTO consultarSolicitudCorreccionAporteAporteGeneral(Long idAporteGeneral) {
		try {
			logger.debug("Inicio de método consultarSolicitudCorreccionAporteAporteGeneral(Long idAporteGeneral)");
			SolicitudCorreccionAporte solicitudCorreccionAporte = consultasCore
					.consultarSolicitudCorreccionAporteAporteGeneral(idAporteGeneral);
			SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO = new SolicitudCorreccionAporteModeloDTO();
			solicitudCorreccionAporteDTO.convertToDTO(solicitudCorreccionAporte);
			logger.debug("Finaliza método consultarSolicitudCorreccionAporteAporteGeneral(Long idAporteGeneral)");
			return solicitudCorreccionAporteDTO;
		} catch (NoResultException e) {
			logger.error(
					"Ocurrió un error en el método consultarSolicitudCorreccionAporteAporteGeneral(Long idAporteGeneral)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 *  
	 * @see com.asopagos.aportes.service.AportesService#consultarRecaudo(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AnalisisDevolucionDTO> consultarRecaudo(ConsultarRecaudoDTO consultaRecaudo,
			TipoMovimientoRecaudoAporteEnum tipo, Boolean hayParametros, Boolean vista360) {
		logger.debug("Inicia método consultarRecaudo(Long, ConsultarRecaudoDTO)");
		if (consultaRecaudo.getNumeroIdentificacion() == null && consultaRecaudo.getTipoIdentificacion() == null
				&& consultaRecaudo.getTipoSolicitante() == null) {
			logger.debug("Finaliza método consultarRecaudo(Long, ConsultarRecaudoDTO):Parámetros Incompletos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		} else {
			PersonaDTO persona = consultasCore.consultarPersonaTipoNumeroIdentificacion(
					consultaRecaudo.getTipoIdentificacion(), consultaRecaudo.getNumeroIdentificacion());
					logger.info("getNumeroIdentificacion"+persona.getNumeroIdentificacion());
			if (persona != null) {
				if (persona.getRazonSocial() == null || persona.getRazonSocial().isEmpty()) {
					StringBuilder nombreAportante = new StringBuilder();
					nombreAportante.append(persona.getPrimerNombre() + " ");
					nombreAportante.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre() + " " : "");
					nombreAportante.append(persona.getPrimerApellido() + " ");
					nombreAportante.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
					consultaRecaudo.setNombreCompleto(nombreAportante.toString());

				} else {
					consultaRecaudo.setNombreCompleto(persona.getRazonSocial());
				}
			}

			CriteriaBuilder cb = consultasCore.obtenerCriteriaBuilder();
			CriteriaQuery<AporteGeneral> c = cb.createQuery(AporteGeneral.class);
			Root<AporteGeneral> aporteGeneral = c.from(AporteGeneral.class);
			c.select(aporteGeneral);
			List<AporteGeneral> aportesGenerales = null;
			EmpresaDTO empresa = null;
			List<Predicate> predicates = new ArrayList<Predicate>();
			List<Long> idsAportesCorDev = new ArrayList<>();
			List<AnalisisDevolucionDTO> analisisDevolucion = new ArrayList<>();

			
			if (persona.getIdPersona() != null) {
				empresa = consultasCore.consultarEmpresa(persona.getIdPersona());

			}


			if (tipo != null && (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(tipo)
					|| TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(tipo))) {
						logger.info("Es devolucion");
				Long idEmpresa = 0L;
				if (empresa != null) {
					idEmpresa = empresa.getIdEmpresa();
				}

				// se unifica el listado de modalidades de recaudo
				if (consultaRecaudo.getListMetodoRecaudo() == null && consultaRecaudo.getMetodoRecaudo() != null) {
					consultaRecaudo.setListMetodoRecaudo(new ArrayList<>());
				}
				if (consultaRecaudo.getMetodoRecaudo() != null
						&& !consultaRecaudo.getListMetodoRecaudo().contains(consultaRecaudo.getMetodoRecaudo())) {
					consultaRecaudo.getListMetodoRecaudo().add(consultaRecaudo.getMetodoRecaudo());
				}
				if(consultaRecaudo.getTipoSolicitante().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR)){
					idsAportesCorDev = consultasCore.consultarAportesConCorreccionesEmpleador(idEmpresa, persona.getIdPersona(),
						consultaRecaudo.getTipoSolicitante(), consultaRecaudo.getPeriodoRecaudo(),
						consultaRecaudo.getFechaInicio(), consultaRecaudo.getFechaFin(), tipo);
				}else{
				idsAportesCorDev = consultasCore.consultarAportesConCorrecciones(idEmpresa, persona.getIdPersona(),
						consultaRecaudo.getTipoSolicitante(), consultaRecaudo.getPeriodoRecaudo(),
						consultaRecaudo.getFechaInicio(), consultaRecaudo.getFechaFin(), tipo);
				}
				
				

				if (hayParametros && consultaRecaudo.getListMetodoRecaudo() != null
						&& !consultaRecaudo.getListMetodoRecaudo().isEmpty()) {
					predicates.add(
							aporteGeneral.get("modalidadRecaudoAporte").in(consultaRecaudo.getListMetodoRecaudo()));
				}

				if (idsAportesCorDev != null && !idsAportesCorDev.isEmpty()) {
					logger.info("No esta Nulo");
					predicates.add(aporteGeneral.get("id").in(idsAportesCorDev));
				} else {
					logger.info("Devuelve Nulo");
					return analisisDevolucion;
				}
			} else {
				if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(consultaRecaudo.getTipoSolicitante())
						&& empresa != null) {
					predicates.add(cb.or(cb.equal(aporteGeneral.get("idEmpresa"), empresa.getIdEmpresa())));
				} else if (persona.getIdPersona() != null && empresa != null
						&& (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE
								.equals(consultaRecaudo.getTipoSolicitante())
								|| TipoSolicitanteMovimientoAporteEnum.PENSIONADO
										.equals(consultaRecaudo.getTipoSolicitante()))) {
					predicates.add(cb.or(cb.or(cb.equal(aporteGeneral.get("idPersona"), persona.getIdPersona())),
							cb.equal(aporteGeneral.get("empresaTramitadoraAporte"), empresa.getIdEmpresa())));
				} else if (persona.getIdPersona() != null) {
					predicates.add(cb.equal(aporteGeneral.get("idPersona"), persona.getIdPersona()));
				}

				if (consultaRecaudo.getTipoSolicitante() != null && !TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
						.equals(consultaRecaudo.getTipoSolicitante())) {;
					predicates
							.add(cb.equal(aporteGeneral.get("tipoSolicitante"), consultaRecaudo.getTipoSolicitante()));
				}
				if (consultaRecaudo.getIdRegistroGeneral() != null) {
					predicates.add(
							cb.equal(aporteGeneral.get("idRegistroGeneral"), consultaRecaudo.getIdRegistroGeneral()));
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
				if (vista360 == null || (vista360 != null && !vista360)) {
					if (consultaRecaudo.getMetodoRecaudo() != null && consultaRecaudo.getPeriodoRecaudo() != null) {
						String fechaPagoString = null;
						fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
						predicates.add(cb.equal(aporteGeneral.get("periodoAporte"), fechaPagoString));
						predicates.add(cb.equal(aporteGeneral.get("modalidadRecaudoAporte"),
								consultaRecaudo.getMetodoRecaudo()));
					} else {
						if (consultaRecaudo.getPeriodoRecaudo() != null) {
							String fechaPagoString = null;
							fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
							predicates.add(cb.equal(aporteGeneral.get("periodoAporte"), fechaPagoString));
						}
					}
				}

				if (hayParametros != null && hayParametros) {
					if (consultaRecaudo.getListMetodoRecaudo() != null
							&& !consultaRecaudo.getListMetodoRecaudo().isEmpty()) {
						predicates.add(
								aporteGeneral.get("modalidadRecaudoAporte").in(consultaRecaudo.getListMetodoRecaudo()));
					}

					/* validacion cuando es empleador presencial */
					if (TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(tipo)
							&& consultaRecaudo.getPeriodoRecaudo() != null && consultaRecaudo.getPeriodoFin() != null) {
						String periodoInicialFormat = null;
						String periodoFinalFormat = null;
						periodoInicialFormat = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
						periodoFinalFormat = dateFormat.format(new Date(consultaRecaudo.getPeriodoFin()));
						predicates.add(cb.between(aporteGeneral.get("periodoAporte"), periodoInicialFormat,
								periodoFinalFormat));
					} else if (consultaRecaudo.getPeriodoRecaudo() != null) {
						String fechaPagoString = null;
						fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
						predicates.add(cb.greaterThanOrEqualTo(aporteGeneral.get("periodoAporte"), fechaPagoString));
					} else if (consultaRecaudo.getPeriodoFin() != null) {
						String fechaPagoString = null;
						fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoFin()));
						predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("periodoAporte"), fechaPagoString));
					}

					if (consultaRecaudo.getFechaInicio() != null && consultaRecaudo.getFechaFin() != null) {
						Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
						Date fechaFinDate = new Date(consultaRecaudo.getFechaFin());
						predicates.add(
								cb.between(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate, fechaFinDate));
					} else if (consultaRecaudo.getFechaInicio() != null) {
						Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
						predicates
								.add(cb.greaterThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate));
					} else if (consultaRecaudo.getFechaFin() != null) {
						Date fechaFinDate = new Date(consultaRecaudo.getFechaFin());
						predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaFinDate));
					}
				}

				if (consultaRecaudo.getIdAporteGeneral() != null) {
					predicates.add(cb.equal(aporteGeneral.get("id"), consultaRecaudo.getIdAporteGeneral()));
				}

			}


			for (Predicate i :predicates)
			{
	//			logger.info("Predicates datos... " + i.toString());
			};

			c.select(aporteGeneral).where(predicates.toArray(new Predicate[] {}));
			logger.info("2g consulta aportes "+c);
			aportesGenerales = consultasCore.obtenerListaAportes(c);

			//logger.info("aportesGenerales_1... " + aportesGenerales.toString());

			// se aplica el criterio de número de planilla (sí aplica)
			aportesGenerales = aplicarCriterioNumeroPlanilla(aportesGenerales, consultaRecaudo.getNumeroPlanilla());

			if (aportesGenerales != null && !aportesGenerales.isEmpty() && hayParametros != null && !hayParametros
					&& vista360 != null && vista360) {
				aportesGenerales.sort(Comparator.comparing(AporteGeneral::getFechaProcesamiento)
						.thenComparing(AporteGeneral::getPeriodoAporte).reversed());
				if (aportesGenerales.size() > 24) {
					aportesGenerales = aportesGenerales.subList(0, 24);
				}
			}

			if (aportesGenerales.size() > 0) {

			List<Long> idsAporte = new ArrayList<>();
			for (AporteGeneral aporte : aportesGenerales) {
				idsAporte.add(aporte.getId());
			}
			List<AporteGeneralModeloDTO> aportesGeneralesDTO = new ArrayList<>();
			if (!idsAporte.isEmpty()) {
				aportesGeneralesDTO = consultasCore.consultarAporteYMovimiento(idsAporte);
				
				// luego de obtener los aportes, buscar las personas
				// tramitadoras
				agregarTramitadores(consultaRecaudo, aportesGeneralesDTO);
			}
			analisisDevolucion = obtenerAnalisisDevolucionDTO(aportesGeneralesDTO, consultaRecaudo);
			
			
			//logger.info("NumAportes: " + aportesGenerales.size());
			//logger.info("getIdRegistroGeneral: " + aportesGenerales.get(0).getIdRegistroGeneral());
			
			
			CuentaAporteDTO infoAporte = consultasStaging.consultarDatosPlanillaAporte(aportesGenerales.get(0).getIdRegistroGeneral());
			
			//logger.info("getRegistroControl: " + infoAporte.getRegistroControl());
            //logger.info("EstadoProcesoArchivoEnum: " + infoAporte.getEstadoArchivo());
			
			if(aportesGeneralesDTO == null) {
			    //logger.info("caso 1: ");
				analisisDevolucion.get(0).setEnProcesamiento(true);
				List<PilaEstadoTransitorio> pet = consultasCore.consultarEstadoProcesamientoPlanilla(infoAporte.getRegistroControl());
				if (pet.isEmpty()) {
					analisisDevolucion.get(0).setEnBandejaTransitoria(false);
				} else {
					analisisDevolucion.get(0).setEnBandejaTransitoria(true);
				}
			} else {
			    //logger.info("caso2: ");
				List<PilaEstadoTransitorio> pet = consultasCore.consultarEstadoProcesamientoPlanilla(infoAporte.getRegistroControl());
				// si está vacío puede ser por:
				// caso 1: planillas cargadas antes del ajuste de pila fase 3
				// caso 2: aún no se copia el aportes en AporteGeneral, como tal en la vista 360 no lo mostraría (no se tiene en cuenta) 
				if (pet.isEmpty()) {
				    //logger.info("caso3: ");
					analisisDevolucion.get(0).setEnBandejaTransitoria(false);
					if (analisisDevolucion.get(0) != null && 
							(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO.equals(infoAporte.getEstadoArchivo()))){
						analisisDevolucion.get(0).setEnProcesamiento(false);
					} else {
						analisisDevolucion.get(0).setEnProcesamiento(true);
					}
				} else {
				    //logger.info("caso4: ");
					analisisDevolucion.get(0).setEnProcesamiento(true);
					analisisDevolucion.get(0).setEnBandejaTransitoria(true);
					
					for (PilaEstadoTransitorio p : pet) {
						if (PilaEstadoTransitorioEnum.EXITOSO.equals(p.getEstado())
								&& PilaAccionTransitorioEnum.NOTIFICAR_PLANILLA.equals(p.getAccion())) {
							analisisDevolucion.get(0).setEnProcesamiento(false);
							analisisDevolucion.get(0).setEnBandejaTransitoria(false);
						}
					}
				}
			}
		}

			logger.debug("Finaliza método consultarRecaudo(Long, ConsultarRecaudoDTO)");
			return analisisDevolucion;
		}

	}

	/**
	 * @param aportesGenerales
	 * @param numeroPlanilla
	 * @return
	 */
	private List<AporteGeneral> aplicarCriterioNumeroPlanilla(List<AporteGeneral> aportesGenerales,
			String numeroPlanilla) {
		String firmaMetodo = "AportesBusiness.aplicarCriterioNumeroPlanilla(List<AporteGeneral>, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<AporteGeneral> result = new ArrayList<>();

		if (numeroPlanilla != null && !numeroPlanilla.isEmpty()) {
			Map<Long, AporteGeneral> mapaAportes = new HashMap<>();
			List<Long> idsRegistroGeneral = new ArrayList<>();

			// en primer lugar, los aportes manuales se descartan del resultado
			// y se listan los ID de registro general en los aportes
			for (AporteGeneral aporteGeneral : aportesGenerales) {
				if (!ModalidadRecaudoAporteEnum.MANUAL.equals(aporteGeneral.getModalidadRecaudoAporte())) {
					mapaAportes.put(aporteGeneral.getIdRegistroGeneral(), aporteGeneral);
					idsRegistroGeneral.add(aporteGeneral.getIdRegistroGeneral());
				}
			}

			if (!idsRegistroGeneral.isEmpty()) {
				// se consultan los registros generales
				List<RegistroGeneralModeloDTO> registrosGenerales = new ArrayList<>(
						consultasStaging.consultarRegistrosGeneralesPorListaId(idsRegistroGeneral).values());

				for (RegistroGeneralModeloDTO registrogeneral : registrosGenerales) {
					// se compara el # de planilla para agregar al resultado
					if (numeroPlanilla.equals(registrogeneral.getNumPlanilla())) {
						result.add(mapaAportes.get(registrogeneral.getId()));
					}
				}
			} else {
				result = aportesGenerales;
			}
		} else {
			result = aportesGenerales;
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRecaudoDevolucion(java.lang.Long,
	 *      java.util.List)
	 */
	public List<AnalisisDevolucionDTO> consultarRecaudoDevolucion(Long idSolicitudDevolucion,
			List<AnalisisDevolucionDTO> analisis) {
		try {
			for (AnalisisDevolucionDTO analisisDevolucionDTO : analisis) {
				AnalisisDevolucionDTO datosDevolucion = consultasCore
						.consultarDatosDevolucion(analisisDevolucionDTO.getIdAporte(), idSolicitudDevolucion);
				analisisDevolucionDTO.setEstadoAporte(EstadoAporteEnum.CORREGIDO);
				analisisDevolucionDTO
						.setMonto(datosDevolucion.getMonto() != null ? datosDevolucion.getMonto() : BigDecimal.ZERO);
				analisisDevolucionDTO.setInteres(
						datosDevolucion.getInteres() != null ? datosDevolucion.getInteres() : BigDecimal.ZERO);
				BigDecimal monto = analisisDevolucionDTO.getMonto();
				BigDecimal interes = analisisDevolucionDTO.getInteres();
				analisisDevolucionDTO.setTotal(monto.add(interes));
			}
			return analisis;
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia de la solicitud", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRecaudoCotizante(com.asopagos.aportes.dto.ConsultarRecaudoDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AnalisisDevolucionDTO> consultarRecaudoCotizante(ConsultarRecaudoDTO consultaRecaudo) {
		logger.info("2g prueba texto cuenta consultarRecaudoCotizante");

		String firmaServicio = "AportesBusiness.consultarRecaudoCotizante(ConsultarRecaudoDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AnalisisDevolucionDTO> analisisDevolucion = null;

		if (consultaRecaudo.getPeriodoRecaudo() == null && consultaRecaudo.getNumeroPlanilla() == null
				&& consultaRecaudo.getMetodoRecaudo() == null) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + " :: "
					+ MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		} else {
			List<AporteGeneralModeloDTO> aportesGenerales = new ArrayList<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String fechaPagoString = null;

			if (consultaRecaudo.getPeriodoRecaudo() != null) {
				fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
				aportesGenerales.addAll(consultasCore
						.consultarAporteGeneralIdPersonaPeriodo(consultaRecaudo.getIdPersona(), fechaPagoString));
			}

			if (consultaRecaudo.getNumeroPlanilla() != null) {
				// se consultan los registros generales que correspondan al # de
				// planilla
				List<RegistroGeneralModeloDTO> registrosGenerales = consultasStaging
						.consultarRegistroGeneralPorPlanilla(consultaRecaudo.getNumeroPlanilla());
				List<Long> idsRegistroGeneral = new ArrayList<>();

				for (RegistroGeneralModeloDTO registroGeneral : registrosGenerales) {
					idsRegistroGeneral.add(registroGeneral.getId());
				}

				if (!idsRegistroGeneral.isEmpty()) {
					List<Long> idsPersona = new ArrayList<>();
					idsPersona.add(consultaRecaudo.getIdPersona());
					aportesGenerales.addAll(consultasCore.consultarAportesGenerales(idsRegistroGeneral, idsPersona));
				}
			}

			if (consultaRecaudo.getMetodoRecaudo() != null) {
				List<Long> idsPersona = new ArrayList<>();
				idsPersona.add(consultaRecaudo.getIdPersona());
				aportesGenerales.addAll(consultasCore
						.consultarAportesGeneralesPorMetodo(consultaRecaudo.getMetodoRecaudo(), idsPersona));
			}

			// se limpia el listado para no presentar movimientos repetidos
			Set<Long> idsAportes = new HashSet<>();
			for (int i = 0; i < aportesGenerales.size(); i++) {
				AporteGeneralModeloDTO aporteGeneral = aportesGenerales.get(i);
				if ((idsAportes.contains(aporteGeneral.getId()))
						|| (consultaRecaudo.getMetodoRecaudo() != null && !aporteGeneral.getModalidadRecaudoAporte()
								.equals(consultaRecaudo.getMetodoRecaudo()))
						|| (fechaPagoString != null && !aporteGeneral.getPeriodoAporte().equals(fechaPagoString))
						|| (consultaRecaudo.getTipoSolicitante() != null
								&& !consultaRecaudo.getTipoSolicitante().equals(aporteGeneral.getTipoSolicitante()))) {
					aportesGenerales.remove(i);
					i--;
				} else {
					idsAportes.add(aporteGeneral.getId());
				}
			}

			// luego de obtener los aportes, buscar las personas tramitadoras
			agregarTramitadores(consultaRecaudo, aportesGenerales);

			aportesGenerales = consultasCore.consultarCuentaBancariaRecaudo(aportesGenerales);

			analisisDevolucion = obtenerAnalisisDevolucionDTO(aportesGenerales, consultaRecaudo);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

		
		return analisisDevolucion;
	}

	/**
	 * Método privado que retorna una lista de AnalisisDevolucionDTO
	 * 
	 * @param aportesGenerales
	 * @param consultaRecaudo
	 * @return
	 */
	private List<AnalisisDevolucionDTO> obtenerAnalisisDevolucionDTO(List<AporteGeneralModeloDTO> aportesGenerales,
			ConsultarRecaudoDTO consultaRecaudo) {

		String firmaMetodo = "AportesBusiness.obtenerAnalisisDevolucionDTO(List<AporteGeneralModeloDTO>, ConsultarRecaudoDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + "2g");


		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		List<AnalisisDevolucionDTO> analisisDevolucion = new ArrayList<>();

		DatosAnalisisDevolucionDTO datosBD = new DatosAnalisisDevolucionDTO();

		// listado de IDs de registros generales, aportes y DTOs para la
		// consulta de subsidio
		List<Long> idsRegistrosGeneral = new ArrayList<>();
		List<Long> idsAporteDevolucion = new ArrayList<>();
		List<Long> idsEmpresas = new ArrayList<>();
		List<Long> idsPersonas = new ArrayList<>();
		List<DatosConsultaSubsidioPagadoDTO> datosConsultaSubsidios = new ArrayList<>();

		// se recopilan los ID de registros generales y de aportes
		for (AporteGeneralModeloDTO aporte : aportesGenerales) {
			if (!idsRegistrosGeneral.contains(aporte.getIdRegistroGeneral())) {
				idsRegistrosGeneral.add(aporte.getIdRegistroGeneral());
			}

			if (!idsAporteDevolucion.contains(aporte.getId())) {
				idsAporteDevolucion.add(aporte.getId());
			}

			if (aporte.getIdEmpresa() != null && !idsEmpresas.contains(aporte.getIdEmpresa())) {
				idsEmpresas.add(aporte.getIdEmpresa());
			}

			if (aporte.getIdPersona() != null && !idsPersonas.contains(aporte.getIdPersona())) {
				idsPersonas.add(aporte.getIdPersona());
			}

			datosConsultaSubsidios.add(new DatosConsultaSubsidioPagadoDTO(consultaRecaudo.getTipoIdentificacion(),
					consultaRecaudo.getNumeroIdentificacion(), aporte.getPeriodoAporte()));
		}
		datosBD.setIdsRegistrosGeneral(idsRegistrosGeneral);
		datosBD.setIdsAporteDevolucion(idsAporteDevolucion);
		datosBD.setIdsEmpresas(idsEmpresas);
		datosBD.setIdsPersonas(idsPersonas);
		datosBD.setDatosCotizantes(datosConsultaSubsidios);

		datosBD = consultaPreviaAnalisisDevolucion(datosBD);

		Long periodo = null;
		String nombreCompletoAportante = null;
		TipoIdentificacionEnum tipoIdentificacionAportante = null;
		String numeroIdentificacionAportante = null;
		TipoSolicitanteMovimientoAporteEnum tipoSolicitante = null;

		for (AporteGeneralModeloDTO aportes : aportesGenerales) {
			nombreCompletoAportante = consultaRecaudo.getNombreCompleto();
			tipoIdentificacionAportante = consultaRecaudo.getTipoIdentificacion();
			numeroIdentificacionAportante = consultaRecaudo.getNumeroIdentificacion();
			tipoSolicitante = consultaRecaudo.getTipoSolicitante();

			try {
				periodo = dateFormat.parse(aportes.getPeriodoAporte()).getTime();
			} catch (ParseException e) {
				logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			}

			AnalisisDevolucionDTO analisisDevolucionDTO = new AnalisisDevolucionDTO();
			analisisDevolucionDTO.setEstadoAporte(aportes.getEstadoAporteAportante());
			analisisDevolucionDTO.setResultado(EstadoGestionAporteEnum.PENDIENTE);
			RegistroGeneralModeloDTO registroGeneral = datosBD.getRegistrosGenerales()
					.get(aportes.getIdRegistroGeneral());

			DatosConsultaSubsidioPagadoDTO datoCotizante = ubicarPagoSubsidioCotizante(
					consultaRecaudo.getTipoIdentificacion(), consultaRecaudo.getNumeroIdentificacion(),
					aportes.getPeriodoAporte(), datosBD.getDatosCotizantes());

			if (consultaRecaudo.getNumeroPlanilla() != null && !consultaRecaudo.getNumeroPlanilla().isEmpty()
					&& registroGeneral != null) {
						logger.info("2g prueba texto cuenta +aporte.getNumeroPlanilla() entra");

				if (consultaRecaudo.getNumeroPlanilla().equals(registroGeneral.getNumPlanilla())) {
					if (aportes.getFechaProcesamiento() != null) {
						analisisDevolucionDTO.setFecha(aportes.getFechaProcesamiento());
					}
					if (aportes.getFechaRecaudo() != null) {
                        analisisDevolucionDTO.setFechaPago(aportes.getFechaRecaudo());
                    }
					if (aportes.getCuentaBancariaRecaudoTexto() != null) {
						logger.info("2g prueba texto cuenta +aporte.getCuentaBancariaRecaudoTexto() entra");

						analisisDevolucionDTO.setCuentaBancariaRecaudo(aportes.getCuentaBancariaRecaudo());
						analisisDevolucionDTO.setCuentaBancariaRecaudoTexto(aportes.getCuentaBancariaRecaudoTexto());
					}
					analisisDevolucionDTO.setIdAporte(aportes.getId());
					analisisDevolucionDTO.setMetodo(aportes.getModalidadRecaudoAporte());
					analisisDevolucionDTO.setConDetalle(aportes.getAporteConDetalle());
					analisisDevolucionDTO.setPeriodo(periodo);
					analisisDevolucionDTO.setMonto(aportes.getValorTotalAporteObligatorio());
					analisisDevolucionDTO.setIdRegistroGeneral(registroGeneral.getId());
					analisisDevolucionDTO.setInteres(aportes.getValorInteresesMora());
					analisisDevolucionDTO.setNumPlanilla(registroGeneral.getNumPlanilla());
					analisisDevolucionDTO.setEstadoArchivo(registroGeneral.getOutEstadoArchivo());
					if (registroGeneral.getTipoPlanilla() != null) {
						analisisDevolucionDTO.setTipoPlanilla(
								TipoPlanillaEnum.obtenerTipoPlanilla(registroGeneral.getTipoPlanilla()));
					}
					if (aportes.getId() != null) {
						analisisDevolucionDTO.setNumOperacion(aportes.getId().toString());
					}

					if (registroGeneral.getRegistroControl() != null
							&& datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl()) != null) {
						analisisDevolucionDTO.setIdEcmArchivo(
								datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[0]);
						analisisDevolucionDTO.setTipoArchivo(TipoArchivoPilaEnum.valueOf(
								datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[1]));
					}
					BigDecimal monto = (aportes.getValorTotalAporteObligatorio() != null
							? aportes.getValorTotalAporteObligatorio() : BigDecimal.ZERO);
					BigDecimal interes = (aportes.getValorInteresesMora() != null ? aportes.getValorInteresesMora()
							: BigDecimal.ZERO);
					analisisDevolucionDTO.setTotal(monto.add(interes));
					analisisDevolucionDTO.setIdPersona(aportes.getIdPersona());
					analisisDevolucionDTO.setIdEmpresa(aportes.getIdEmpresa());
					analisisDevolucionDTO.setTieneModificaciones(aportes.getTieneModificaciones());

					// Histórico evaluación de aportes
					SolicitudAporteModeloDTO solicitudAporteDTO = datosBD.getSolicitudesDevolucion()
							.get(registroGeneral.getOutRegInicial() != null ? registroGeneral.getOutRegInicial()
									: registroGeneral.getId());

					HistoricoDTO historicoDTO = consultarHistoricoEvaluacionAporteDetalle(
							aportes.getEstadoAporteAportante(), analisisDevolucionDTO.getMetodo(),
							analisisDevolucionDTO.getEstadoArchivo(), analisisDevolucionDTO.getTieneModificaciones(),
							solicitudAporteDTO != null ? solicitudAporteDTO.getEstadoSolicitud() : null,
							datoCotizante.getTipoIdentificacion(), datoCotizante.getNumeroIdentificacion(),
							datoCotizante.getPeriodo(), datoCotizante.getTieneSubsidio());

					analisisDevolucionDTO.setHistorico(historicoDTO);
					/* Se agrega pagos de terceros para vista 360 */
					if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportes.getTipoSolicitante())) {
						analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

						EmpresaModeloDTO empresa = datosBD.getEmpresasAportantes().get(aportes.getIdEmpresa());
						if (empresa != null) {
							analisisDevolucionDTO.setTipoIdentificacionTramitador(empresa.getTipoIdentificacion());
							analisisDevolucionDTO.setNumeroIdentificacionTramitador(empresa.getNumeroIdentificacion());

							if (empresa.getRazonSocial() == null || empresa.getRazonSocial().isEmpty()) {
								StringBuilder nombreAportante = new StringBuilder();
								nombreAportante.append(empresa.getPrimerNombre() + " ");
								nombreAportante.append(
										empresa.getSegundoNombre() != null ? empresa.getSegundoNombre() + " " : "");
								nombreAportante.append(empresa.getPrimerApellido() + " ");
								nombreAportante.append(
										empresa.getSegundoApellido() != null ? empresa.getSegundoApellido() : "");
								analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

							} else {
								analisisDevolucionDTO.setNombreCompletoTramitador(empresa.getRazonSocial());
							}
						}
					} else if (aportes.getEmpresaTramitadoraAporte() != null) {
						analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

						EmpresaModeloDTO tramitador = consultaRecaudo.getEmpresasTramitadoras().get(aportes.getId());

						if (tramitador != null) {
							analisisDevolucionDTO.setTipoIdentificacionTramitador(tramitador.getTipoIdentificacion());
							analisisDevolucionDTO
									.setNumeroIdentificacionTramitador(tramitador.getNumeroIdentificacion());

							if (tramitador.getRazonSocial() == null || tramitador.getRazonSocial().isEmpty()) {
								StringBuilder nombreAportante = new StringBuilder();
								nombreAportante.append(tramitador.getPrimerNombre() + " ");
								nombreAportante.append(tramitador.getSegundoNombre() != null
										? tramitador.getSegundoNombre() + " " : "");
								nombreAportante.append(tramitador.getPrimerApellido() + " ");
								nombreAportante.append(
										tramitador.getSegundoApellido() != null ? tramitador.getSegundoApellido() : "");
								analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

							} else {
								analisisDevolucionDTO.setNombreCompletoTramitador(tramitador.getRazonSocial());
							}
						}

						PersonaModeloDTO personaAportante = datosBD.getPersonasAportantes().get(aportes.getIdPersona());
						if (personaAportante != null) {
							tipoIdentificacionAportante = personaAportante.getTipoIdentificacion();
							numeroIdentificacionAportante = personaAportante.getNumeroIdentificacion();
							nombreCompletoAportante = personaAportante.getRazonSocial();
						}

						tipoSolicitante = aportes.getTipoSolicitante();
					} else {
						analisisDevolucionDTO.setPagadorPorTerceros(Boolean.FALSE);
					}

					analisisDevolucionDTO.setNombreCompleto(nombreCompletoAportante);
					analisisDevolucionDTO.setTipoIdentificacion(tipoIdentificacionAportante);
					analisisDevolucionDTO.setNumeroIdentificacion(numeroIdentificacionAportante);

					analisisDevolucionDTO.setTipoSolicitante(tipoSolicitante);
					analisisDevolucionDTO.setCodigoEntidadFinanciera(
							aportes.getCodigoEntidadFinanciera() != null ? aportes.getCodigoEntidadFinanciera() : null);
					analisisDevolucion.add(analisisDevolucionDTO);
				}
			} else if (registroGeneral != null) {
				if (aportes.getFechaProcesamiento() != null) {
					analisisDevolucionDTO.setIdAporte(aportes.getId());
					analisisDevolucionDTO.setFecha(aportes.getFechaProcesamiento());
				}
				if (aportes.getFechaRecaudo() != null) {
                    analisisDevolucionDTO.setFechaPago(aportes.getFechaRecaudo());
                }
				analisisDevolucionDTO.setIdAporte(aportes.getId());
				analisisDevolucionDTO.setMetodo(aportes.getModalidadRecaudoAporte());
				analisisDevolucionDTO.setConDetalle(aportes.getAporteConDetalle());
				analisisDevolucionDTO.setPeriodo(periodo);
				analisisDevolucionDTO.setMonto(aportes.getValorTotalAporteObligatorio());
				analisisDevolucionDTO.setInteres(aportes.getValorInteresesMora());
				if (aportes.getCuentaBancariaRecaudoTexto() != null) {
                analisisDevolucionDTO.setCuentaBancariaRecaudo(aportes.getCuentaBancariaRecaudo());
                analisisDevolucionDTO.setCuentaBancariaRecaudoTexto(aportes.getCuentaBancariaRecaudoTexto());																				 
				}

				analisisDevolucionDTO.setIdRegistroGeneral(registroGeneral.getId());
				analisisDevolucionDTO.setNumPlanilla(registroGeneral.getNumPlanilla());
				analisisDevolucionDTO.setEstadoArchivo(registroGeneral.getOutEstadoArchivo());
				if (registroGeneral.getTipoPlanilla() != null) {
					analisisDevolucionDTO
							.setTipoPlanilla(TipoPlanillaEnum.obtenerTipoPlanilla(registroGeneral.getTipoPlanilla()));
				}

				if (aportes.getId() != null) {
					analisisDevolucionDTO.setNumOperacion(aportes.getId().toString());
				}

				if (registroGeneral.getRegistroControl() != null
						&& datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl()) != null) {
					analisisDevolucionDTO.setIdEcmArchivo(
							datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[0]);
					analisisDevolucionDTO.setTipoArchivo(TipoArchivoPilaEnum
							.valueOf(datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[1]));
				}

				BigDecimal monto = (aportes.getValorTotalAporteObligatorio() != null
						? aportes.getValorTotalAporteObligatorio() : BigDecimal.ZERO);
				BigDecimal interes = (aportes.getValorInteresesMora() != null ? aportes.getValorInteresesMora()
						: BigDecimal.ZERO);
				analisisDevolucionDTO.setTotal(monto.add(interes));
				analisisDevolucionDTO.setIdPersona(aportes.getIdPersona());
				analisisDevolucionDTO.setIdEmpresa(aportes.getIdEmpresa());
				analisisDevolucionDTO.setTieneModificaciones(aportes.getTieneModificaciones());

				// Histórico evaluación de aportes
				SolicitudAporteModeloDTO solicitudAporteDTO = datosBD.getSolicitudesDevolucion()
						.get(registroGeneral.getOutRegInicial() != null ? registroGeneral.getOutRegInicial()
								: registroGeneral.getId());

				HistoricoDTO historicoDTO = consultarHistoricoEvaluacionAporteDetalle(
						aportes.getEstadoAporteAportante(), analisisDevolucionDTO.getMetodo(),
						analisisDevolucionDTO.getEstadoArchivo(), analisisDevolucionDTO.getTieneModificaciones(),
						solicitudAporteDTO != null ? solicitudAporteDTO.getEstadoSolicitud() : null,
						datoCotizante.getTipoIdentificacion(), datoCotizante.getNumeroIdentificacion(),
						datoCotizante.getPeriodo(), datoCotizante.getTieneSubsidio());
				analisisDevolucionDTO.setHistorico(historicoDTO);

				/* Se agrega pagos de terceros para vista 360 */
				if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportes.getTipoSolicitante())) {
					analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

					EmpresaModeloDTO empresa = datosBD.getEmpresasAportantes().get(aportes.getIdEmpresa());
					if (empresa != null) {
						analisisDevolucionDTO.setTipoIdentificacionTramitador(empresa.getTipoIdentificacion());
						analisisDevolucionDTO.setNumeroIdentificacionTramitador(empresa.getNumeroIdentificacion());

						if (empresa.getRazonSocial() == null || empresa.getRazonSocial().isEmpty()) {
							StringBuilder nombreAportante = new StringBuilder();
							nombreAportante.append(empresa.getPrimerNombre() + " ");
							nombreAportante
									.append(empresa.getSegundoNombre() != null ? empresa.getSegundoNombre() + " " : "");
							nombreAportante.append(empresa.getPrimerApellido() + " ");
							nombreAportante
									.append(empresa.getSegundoApellido() != null ? empresa.getSegundoApellido() : "");
							analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

						} else {
							analisisDevolucionDTO.setNombreCompletoTramitador(empresa.getRazonSocial());
						}
					}
				} else if (aportes.getEmpresaTramitadoraAporte() != null) {
					analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

					EmpresaModeloDTO tramitador = consultaRecaudo.getEmpresasTramitadoras().get(aportes.getId());

					if (tramitador != null) {
						analisisDevolucionDTO.setTipoIdentificacionTramitador(tramitador.getTipoIdentificacion());
						analisisDevolucionDTO.setNumeroIdentificacionTramitador(tramitador.getNumeroIdentificacion());

						if (tramitador.getRazonSocial() == null || tramitador.getRazonSocial().isEmpty()) {
							StringBuilder nombreAportante = new StringBuilder();
							nombreAportante.append(tramitador.getPrimerNombre() + " ");
							nombreAportante.append(
									tramitador.getSegundoNombre() != null ? tramitador.getSegundoNombre() + " " : "");
							nombreAportante.append(tramitador.getPrimerApellido() + " ");
							nombreAportante.append(
									tramitador.getSegundoApellido() != null ? tramitador.getSegundoApellido() : "");
							analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

						} else {
							analisisDevolucionDTO.setNombreCompletoTramitador(tramitador.getRazonSocial());
						}
					}

					PersonaModeloDTO personaAportante = datosBD.getPersonasAportantes().get(aportes.getIdPersona());
					if (personaAportante != null) {
						tipoIdentificacionAportante = personaAportante.getTipoIdentificacion();
						numeroIdentificacionAportante = personaAportante.getNumeroIdentificacion();
						nombreCompletoAportante = personaAportante.getRazonSocial();
					}

					tipoSolicitante = aportes.getTipoSolicitante();
				} else {
					analisisDevolucionDTO.setPagadorPorTerceros(Boolean.FALSE);
				}

				analisisDevolucionDTO.setNombreCompleto(nombreCompletoAportante);
				analisisDevolucionDTO.setTipoIdentificacion(tipoIdentificacionAportante);
				analisisDevolucionDTO.setNumeroIdentificacion(numeroIdentificacionAportante);

				analisisDevolucionDTO.setTipoSolicitante(tipoSolicitante);
				analisisDevolucionDTO.setCodigoEntidadFinanciera(
						aportes.getCodigoEntidadFinanciera() != null ? aportes.getCodigoEntidadFinanciera() : null);
				analisisDevolucion.add(analisisDevolucionDTO);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return analisisDevolucion;

	}

	/**
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param periodoAporte
	 * @param datosCotizante
	 * @return
	 */
	private DatosConsultaSubsidioPagadoDTO ubicarPagoSubsidioCotizante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String periodoAporte, List<DatosConsultaSubsidioPagadoDTO> datosCotizante) {
		String firmaMetodo = "AportesBusiness.consultaPreviaAnalisisDevolucion(TipoIdentificacionEnum, String, String, "
				+ "List<DatosConsultaSubsidioPagadoDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DatosConsultaSubsidioPagadoDTO result = null;

		for (DatosConsultaSubsidioPagadoDTO cotizante : datosCotizante) {
			if (cotizante.getTipoIdentificacion().equals(tipoIdentificacion)
					&& cotizante.getNumeroIdentificacion().equalsIgnoreCase(numeroIdentificacion)
					&& cotizante.getPeriodo().equals(periodoAporte)) {
				result = cotizante;
				break;
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * @param idsRegistrosGeneral
	 * @return
	 */
	private DatosAnalisisDevolucionDTO consultaPreviaAnalisisDevolucion(DatosAnalisisDevolucionDTO datosBase) {
		String firmaMetodo = "AportesBusiness.consultaPreviaAnalisisDevolucion()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DatosAnalisisDevolucionDTO result = datosBase;

		// se consultan los registros generales y las solicitudes de devolución
		// asociadas
		if (datosBase.getIdsRegistrosGeneral() != null && !datosBase.getIdsRegistrosGeneral().isEmpty()) {
			result.setRegistrosGenerales(
					consultasStaging.consultarRegistrosGeneralesPorListaId(datosBase.getIdsRegistrosGeneral()));
			/*
			 * en el caso de que los aportes presenten registros generales
			 * modificados por motivo de movimiento en aporte sin detalle, los
			 * ids se agregan al listado de registros para la consulta de
			 * solicitudes
			 */
			for (RegistroGeneralModeloDTO registro : result.getRegistrosGenerales().values()) {
				if (registro.getOutRegInicial() != null
						&& !datosBase.getIdsRegistrosGeneral().contains(registro.getOutRegInicial())) {
					datosBase.getIdsRegistrosGeneral().add(registro.getOutRegInicial());
				}
			}

			result.setSolicitudesDevolucion(
					consultasCore.consultarSolicitudesDevolucionListaIds(datosBase.getIdsRegistrosGeneral()));
		}

		/*
		 * con los registros generales, se consultan los Índices de planilla que
		 * aplique para obtener los identificadores de documento de los archivos
		 * de PILA
		 */
		if (!result.getIdsRegistrosGeneral().isEmpty()) {
			List<Long> idsPlanilla = new ArrayList<>();
			for (RegistroGeneralModeloDTO registroGeneral : result.getRegistrosGenerales().values()) {
				if (registroGeneral.getRegistroControl() != null
						&& !idsPlanilla.contains(registroGeneral.getRegistroControl())) {
					idsPlanilla.add(registroGeneral.getRegistroControl());
				}
			}

			if (idsPlanilla != null && !idsPlanilla.isEmpty()) {
				List<IndicePlanillaModeloDTO> indices = consultasPila.consultarIndicesPlanillaPorIds(idsPlanilla);
				for (IndicePlanillaModeloDTO indice : indices) {
					String[] documento = { indice.getIdDocumento(), indice.getTipoArchivo().name() };
					result.getIdsDocumentosPlanilla().put(indice.getId(), documento);
				}
			}
		}

		if (datosBase.getDatosCotizantes() != null && !datosBase.getDatosCotizantes().isEmpty()) {
			result.setDatosCotizantes(consultasCore.consultarPagoSubsidioCotizantes(datosBase.getDatosCotizantes()));
		}

		Map<Long, EmpresaModeloDTO> mapaEmpresas = new HashMap<>();
		if (datosBase.getIdsEmpresas() != null && !datosBase.getIdsEmpresas().isEmpty()) {
			List<EmpresaModeloDTO> empresas = consultasCore.consultarEmpresasPorIds(datosBase.getIdsEmpresas());
			for (EmpresaModeloDTO empresa : empresas) {
				if (!mapaEmpresas.containsKey(empresa.getIdEmpresa())) {
					mapaEmpresas.put(empresa.getIdEmpresa(), empresa);
				}
			}
		}
		result.setEmpresasAportantes(mapaEmpresas);

		if (datosBase.getIdsPersonas() != null && !datosBase.getIdsPersonas().isEmpty()) {
			datosBase.setPersonasAportantes(
					consultasCore.consultarPersonasPorListadoIds(datosBase.getIdsPersonas(), Boolean.FALSE));
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#validarExistenciaSolicitud(java.lang.Long)
	 */
	public Boolean validarExistenciaSolicitud(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug(
				"Inicio de método validarExistenciaSolicitud(Long idSolicitudGlobal, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		try {
			List<SolicitudAporte> solicitudesAportes = consultasCore.consultarSolicitudAporte(tipoIdentificacion,
					numeroIdentificacion);
			logger.debug(
					"Finaliza método validarExistenciaSolicitud(Long idSolicitudGlobal, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			if (solicitudesAportes.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia de la solicitud", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#validarExistenciaSolicitud(java.lang.Long)
	 */
	public Boolean validarExistenciaSolicitudCorreccion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(
				"Inicio de método validarExistenciaSolicitudCorreccion(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		try {
			List<SolicitudCorreccionAporte> solicitudesCorreccionAportes = consultasCore
					.consultarSolicitudCorrecionAporte(tipoIdentificacion, numeroIdentificacion);
			logger.debug(
					"Finaliza método validarExistenciaSolicitudCorreccion(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			if (solicitudesCorreccionAportes.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia de la solicitud", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#validarExistenciaSolicitud(java.lang.Long)
	 */
	public Boolean validarExistenciaSolicitudDevolucion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(
				"Inicio de método validarExistenciaSolicitudDevolucion(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		try {
			List<SolicitudDevolucionAporte> solicitudesDevolucionAportes = consultasCore
					.consultarSolicitudDevolucionAporte(tipoIdentificacion, numeroIdentificacion);
			logger.debug(
					"Finaliza método validarExistenciaSolicitudDevolucion(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			if (solicitudesDevolucionAportes.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia de la solicitud", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#registrarRelacionarAportesNovedades(java.lang.Long,
	 *      java.Lang.Boolean, java.Lang.Boolean)
	 */
	@Override
	public void registrarRelacionarAportesNovedades(Long idTransaccion, Boolean esProcesoManual, Boolean esSimulado) {
		logger.debug("Inicio de método registrarRelacionarAportesNovedades(Long idTransaccion)");
		consultasPila.simularFaseDosPilaDos(idTransaccion, esProcesoManual, esSimulado);
		consultasPila.simularFaseTresPilaDos(idTransaccion, esProcesoManual, esSimulado);
		logger.debug("Inicio de método registrarRelacionarAportesNovedades(Long idTransaccion)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#registrarRelacionarAportes(java.lang.Long,
	 *      java.Lang.Boolean, java.Lang.Boolean)
	 */
	@Override
	public void registrarRelacionarAportes(Long idTransaccion, Boolean esProcesoManual, Boolean esSimulado) {
		logger.debug("Inicio de método registrarRelacionarAportes(Long idTransaccion)");
		consultasPila.simularFaseDosPilaDos(idTransaccion, esProcesoManual, esSimulado);
		logger.debug("Inicio de método registrarRelacionarAportes(Long idTransaccion)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#registrarRelacionarNovedades(java.lang.Long,
	 *      java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	public void registrarRelacionarNovedades(Long idTransaccion, Boolean esProcesoManual, Boolean esSimulado) {
		logger.debug("Inicio de método registrarRelacionarNovedades(Long idTransaccion)");
		consultasPila.simularFaseTresPilaDos(idTransaccion, esProcesoManual, esSimulado);
		logger.debug("Inicio de método registrarRelacionarNovedades(Long idTransaccion)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#organizarNovedadesSucursal(java.lang.Long,
	 *      java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	public void organizarNovedadesSucursal(Long idTransaccion) {
		logger.info("Inicio de método organizarNovedadesSucursal(Long idTransaccion)" + idTransaccion);
		consultasPila.organizarNovedadesSucursal(idTransaccion);
		logger.info("Inicio de método organizarNovedadesSucursal(Long idTransaccion)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarCotizante(com.
	 *      asopagos.dto.aportes.CotizanteDTO)
	 */
	@Override
	public List<CotizanteDTO> consultarCotizante(ConsultarCotizanteDTO consultarCotizante) {
		String firmaServicio = "AportesBusiness.consultarCotizante(ConsultarCotizanteDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		if (consultarCotizante.getIdAporte() == null) {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
		Map<String, String> campos = new HashMap<>();
		Map<String, Object> valores = new HashMap<>();
		List<CotizanteDTO> cotizantesDTO = null;
		List<Long> idPersonas = new ArrayList<>();
		boolean tieneParametro = validarDatosConsultaPersona(consultarCotizante, campos, valores);
		// Se valida si se cuenta con parametros a consultar
		if (tieneParametro) {
			List<Persona> personas = consultasCore.consultarPersonas(campos, valores);
			if (personas.isEmpty()) {
				return cotizantesDTO;
			}
			for (Persona persona : personas) {
				idPersonas.add(persona.getIdPersona());
			}
		}
		if (!idPersonas.isEmpty()) {
			cotizantesDTO = consultasCore.consultarCotizantesPorIdAporte(consultarCotizante.getIdAporte(), idPersonas);
		} else {
			cotizantesDTO = consultasCore.consultarCotizantesSinParametros(consultarCotizante.getIdAporte());
		}
		for (CotizanteDTO cotizante : cotizantesDTO) {
			cotizante.setResultado(EstadoGestionAporteEnum.PENDIENTE);
			cotizante.setGestionado(Boolean.FALSE);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return cotizantesDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarCotizanteCuentaAporte(com.asopagos.aportes.dto.
	 * ConsultarCotizanteDTO)
	 */
	@Override
	public List<CotizanteDTO> consultarCotizanteCuentaAporte(ConsultarCotizanteDTO consultarCotizante) {
		try {
			logger.debug("Inicio del método consultarCotizanteCuentaAporte(ConsultarCotizanteDTO consultarCotizante)");
			Map<String, String> campos = new HashMap<>();
			Map<String, Object> valores = new HashMap<>();
			List<Long> idPersonas = new ArrayList<>();
			List<CotizanteDTO> cotizantesDTO = null;
			boolean tieneParametro = validarDatosConsultaPersona(consultarCotizante, campos, valores);

			if(consultarCotizante.getNumeroOperacionCotizante()!=null){
			    AporteDetalladoModeloDTO aporteDetallado = consultarAporteDetallado(consultarCotizante.getNumeroOperacionCotizante());
			    if(aporteDetallado!=null){
			        idPersonas.add(aporteDetallado.getIdPersona());
			        cotizantesDTO = consultasCore.consultarCotizantesPorPametro(idPersonas);
			    }
			}
			
			// Se valida si se cuenta con parametros a consultar
			if (idPersonas.isEmpty() && tieneParametro) {
				List<Persona> personas = consultasCore.consultarPersonas(campos, valores);
				if (personas.isEmpty()) {
					return cotizantesDTO;
				}
				for (Persona persona : personas) {
					idPersonas.add(persona.getIdPersona());
					cotizantesDTO = consultasCore.consultarCotizantesPorPametro(idPersonas);
				}
			}
			logger.debug("Fin del método consultarCotizanteCuentaAporte(ConsultarCotizanteDTO consultarCotizante)");
			return cotizantesDTO;
		} catch (Exception e) {
			logger.error("Ocurrio un error consultando al cotizante en consultarCotizante.", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Método encargado de verificar los datos a consultar de la persona
	 * 
	 * @param consultarCotizante,
	 *            DTO que contiene la informacion para construir la consulta
	 * @param campos,
	 *            lista de campos a validar los datos
	 * @param valores,
	 *            listado de valores que se tienen a consultar
	 * @return retorna true si se cuenta con al menos un parametro
	 */
	private boolean validarDatosConsultaPersona(ConsultarCotizanteDTO consultarCotizante, Map<String, String> campos,
			Map<String, Object> valores) {
		boolean tieneParametro = false;
		if (consultarCotizante.getTipoIdentificacion() != null) {
			campos.put("tipoIdentificacion", ConsultasDinamicasConstants.IGUAL);
			valores.put("tipoIdentificacion", consultarCotizante.getTipoIdentificacion());
			tieneParametro = true;
		}
		if (consultarCotizante.getNumeroIdentificacion() != null
				&& !consultarCotizante.getNumeroIdentificacion().equals("")) {
			campos.put("numeroIdentificacion", ConsultasDinamicasConstants.IGUAL);
			valores.put("numeroIdentificacion", consultarCotizante.getNumeroIdentificacion());
			tieneParametro = true;
		}
		if (consultarCotizante.getPrimerNombre() != null && !consultarCotizante.getPrimerNombre().equals("")) {
			campos.put("primerNombre", ConsultasDinamicasConstants.IGUAL);
			valores.put("primerNombre", consultarCotizante.getPrimerNombre());
			tieneParametro = true;
		}
		if (consultarCotizante.getSegundoNombre() != null && !consultarCotizante.getSegundoNombre().equals("")) {
			campos.put("segundoNombre", ConsultasDinamicasConstants.IGUAL);
			valores.put("segundoNombre", consultarCotizante.getSegundoNombre());
			tieneParametro = true;
		}
		if (consultarCotizante.getPrimerApellido() != null && !consultarCotizante.getPrimerApellido().equals("")) {
			campos.put("primerApellido", ConsultasDinamicasConstants.IGUAL);
			valores.put("primerApellido", consultarCotizante.getPrimerApellido());
			tieneParametro = true;
		}
		if (consultarCotizante.getSegundoApellido() != null && !consultarCotizante.getSegundoApellido().equals("")) {
			campos.put("segundoApellido", ConsultasDinamicasConstants.IGUAL);
			valores.put("segundoApellido", consultarCotizante.getSegundoApellido());
			tieneParametro = true;
		}
		return tieneParametro;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarAporteTemporal(java.
	 * lang.Long)
	 */
	@Override
	public List<AporteDTO> consultarAporteTemporal(Long idRegistroGeneral) {
		logger.info("Inicio de método consultarAporteTemporal(Long idTransaccion)");
		List<AporteDTO> aportes = consultasPila.consultarAporteTemporal(idRegistroGeneral);
		logger.info("Fin de método consultarAporteTemporal(Long idTransaccion)");
		return aportes;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarAporteTemporalNovedad(java.lang.Long)
	 */
	@Override
	public List<TemNovedad> consultarNovedad(Long idRegistroGeneral) {
		try {
			logger.info("Inicio de método consultarNovedad(Long)");
			List<TemNovedad> novedad = consultasPila.consultarNovedad(idRegistroGeneral);
			logger.info("Fin de método consultarNovedad(Long)");
			for (TemNovedad temNovedad : novedad) {
             logger.info("**__**¨777777))))). : getAccionNovedad: "+ temNovedad.getAccionNovedad());
			logger.info("**__**¨ 777777))))) : getRegistroDetallado"+ temNovedad.getRegistroDetallado());
            logger.info("**__**¨ 777777))))) getTipoTransaccion: "+ temNovedad.getTipoTransaccion());
			logger.info("**__**¨ 777777))))) getFechaInicioNovedad: "+ temNovedad.getFechaInicioNovedad());
			  }
			return novedad;
		} catch (Exception e) {
			logger.error("Ocurrio un error consultando los registros temporales.", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarRegistroDetalladoPorId(java.lang.Long)
	 */
	@Override
	public RegistroDetalladoModeloDTO consultarRegistroDetalladoPorId(Long idRegistroDetallado) {
		try {
			logger.debug("Inicia servicio consultarRegistroDetalladoPorId");
			RegistroDetalladoModeloDTO registroDetalladoDTO = consultasStaging
					.consultarRegistroDetalladoPorId(idRegistroDetallado);
			logger.debug("Finaliza servicio consultarRegistroDetalladoPorId");
			return registroDetalladoDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarRegistroDetalladoPorId", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarAporteDetallado(java.lang.Long)
	 */
	@Override
	public AporteDetalladoModeloDTO consultarAporteDetallado(Long idAporteDetallado) {
		try {
			logger.debug("Inicia servicio consultarAporteDetallado");
			AporteDetallado aporteDetallado = consultasCore.consultarAporteDetallado(idAporteDetallado);
			AporteDetalladoModeloDTO aporteDetalladoDTO = new AporteDetalladoModeloDTO();
			aporteDetalladoDTO.convertToDTO(aporteDetallado);
			logger.debug("Finaliza servicio consultarAporteDetallado");
			return aporteDetalladoDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarAporteDetallado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarIndicePlanilla(java.lang.Long)
	 */
	@Override
	public IndicePlanillaModeloDTO consultarIndicePlanilla(Long idPlanilla) {
		try {
			logger.debug("Inicia servicio consultarIndicePlanilla");
			IndicePlanillaModeloDTO indicePlanillaDTO = null;

			if (idPlanilla != null) {
				IndicePlanilla indicePlanilla = consultasPila.consultarIndicePlanilla(idPlanilla.toString());
				if (indicePlanilla != null) {
					indicePlanillaDTO = new IndicePlanillaModeloDTO();
					indicePlanillaDTO.convertToDTO(indicePlanilla);
				}
			}

			logger.debug("Finaliza servicio consultarIndicePlanilla");
			return indicePlanillaDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarIndicePlanilla", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	@Override
	public IndicePlanillaModeloDTO consultarIndicePlanillaNumeroAportante(Long idPlanilla, Long registroDetallado) {
		try {
			logger.debug("Inicia servicio consultarIndicePlanilla");
			IndicePlanillaModeloDTO indicePlanillaDTO = null;

			if (idPlanilla != null) {
				IndicePlanilla indicePlanilla = consultasPila.consultarIndicePlanillaNumeroAportante(idPlanilla.toString(),registroDetallado);
				if (indicePlanilla != null) {
					indicePlanillaDTO = new IndicePlanillaModeloDTO();
					indicePlanillaDTO.convertToDTO(indicePlanilla);
				}
			}

			logger.debug("Finaliza servicio consultarIndicePlanilla");
			return indicePlanillaDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarIndicePlanilla", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitudAportePorIdAporte(java.lang.Long)
	 */
	@Override
	public SolicitudAporteModeloDTO consultarSolicitudAportePorIdAporte(Long idAporteGeneral) {
		try {
			logger.debug("Inicia servicio consultarSolicitudAportePorIdAporte");
			SolicitudAporte solicitudAporte = consultasCore.consultarSolicitudAportePorIdAporte(idAporteGeneral);
			if (solicitudAporte != null) {
				SolicitudAporteModeloDTO solicitudAporteDTO = new SolicitudAporteModeloDTO();
				solicitudAporteDTO.convertToDTO(solicitudAporte);
				logger.debug("Finaliza servicio consultarSolicitudAportePorIdAporte");
				return solicitudAporteDTO;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarSolicitudAportePorIdAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarNovedadesCotizanteAporte(java.lang.Long)
	 */
	@Override
	public List<NovedadCotizanteDTO> consultarNovedadesCotizanteAporte(Long idRegistroDetallado) {
		try {
			logger.debug("Inicia servicio consultarNovedadesCotizanteAporte");
			List<NovedadCotizanteDTO> listaNovedadesDTO = consultasStaging
					.consultarNovedadesCotizanteAporte(idRegistroDetallado);
			logger.debug("Finaliza servicio consultarNovedadesCotizanteAporte");
			return listaNovedadesDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarNovedadesCotizanteAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitudDevolucionAporte(java.lang.Long)
	 */
	@Override
	public SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionAporte(Long idSolicitudGlobal) {
		try {
			logger.debug("Inicia servicio consultarSolicitudDevolucionAporte");
			SolicitudDevolucionAporte solicitudDevolucionAporte = consultasCore
					.consultarSolicitudDevolucionAporte(idSolicitudGlobal);
			SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = new SolicitudDevolucionAporteModeloDTO();
			solicitudDevolucionAporteDTO.convertToDTO(solicitudDevolucionAporte);
			logger.debug("Finaliza servicio consultarSolicitudDevolucionAporte");
			return solicitudDevolucionAporteDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarSolicitudDevolucionAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearActualizarSolicitudDevolucionAporte(com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO)
	 */
	@Override
	public Long crearActualizarSolicitudDevolucionAporte(
			SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO) {
		try {
			logger.debug("Inicia servicio crearActualizarSolicitudDevolucionAporte");
			SolicitudDevolucionAporte solicitudDevolucionAporte = solicitudDevolucionAporteDTO.convertToEntity();
			Long id = consultasCore.crearActualizarSolicitudDevolucionAporte(solicitudDevolucionAporte);
			logger.debug("Finaliza servicio crearActualizarSolicitudDevolucionAporte");
			return id;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio crearActualizarSolicitudDevolucionAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearActualizarSolicitudGlobal(com.asopagos.dto.modelo.SolicitudModeloDTO)
	 */
	@Override
	public Long crearActualizarSolicitudGlobal(SolicitudModeloDTO solicitudDTO) {
		try {
			logger.debug("Inicia servicio actualizarSolicitudGlobal");
			Solicitud solicitud = solicitudDTO.convertToSolicitudEntity();
			Long id = consultasCore.crearActualizarSolicitudGlobal(solicitud);
			logger.debug("Finaliza servicio actualizarSolicitudGlobal");
			return id;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio actualizarSolicitudGlobal", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizarMovimientoAporte(com.asopagos.dto.modelo.MovimientoAporteModeloDTO)
	 */
	public Long actualizarMovimientoAporte(MovimientoAporteModeloDTO movimientoAporteModeloDTO) {
		logger.debug("Inicio de método actualizarMovimientoAporte(MovimientoAporteModeloDTO)");
		try {
			MovimientoAporte movimientoAporte = null;
			try {
				if (movimientoAporteModeloDTO.getIdMovimientoAporte() != null) {
					// logger.info("***___*** si entra al primer if ");
					movimientoAporte = consultasCore
							.consultarMovimientoAporte(movimientoAporteModeloDTO.getIdMovimientoAporte());
				}
			} catch (NoResultException e) {
				movimientoAporte = null;
			}
			if (movimientoAporte != null) {
				// logger.info("***___*** si entra al segundo if ");
				movimientoAporte = movimientoAporteModeloDTO.convertToEntity();
				consultasCore.actualizarMovimientoAporte(movimientoAporte);
			} else {
				// logger.info("***___*** si entra al else ");
				movimientoAporte = movimientoAporteModeloDTO.convertToEntity();
				consultasCore.crearMovimientoAporte(movimientoAporte);
			}
			// logger.info("Fin método actualizarMovimientoAporte(MovimientoAporteModeloDTO)");
			return movimientoAporte.getIdMovimientoAporte();
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en actualizarMovimientoAporte: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * crearActualizarMovimientoAporte(com.asopagos.dto.modelo.
	 * MovimientoAporteModeloDTO)
	 */
	@Override
	public Long crearActualizarMovimientoAporte(MovimientoAporteModeloDTO movimientoAporteModeloDTO) {
		try {
			// logger.info("Inicio de método crearActualizarMovimientoAporte :: MovimientoAporteDTO = " + (movimientoAporteModeloDTO != null ? movimientoAporteModeloDTO.toString() : "null"));
			MovimientoAporte movimientoAporte = movimientoAporteModeloDTO.convertToEntity();
			Long id = consultasCore.crearActualizarMovimientoAporte(movimientoAporte);
			logger.debug("Fin método crearActualizarMovimientoAporte");
			return id;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en crearActualizarMovimientoAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * crearActualizarAporteDetallado(com.asopagos.dto.modelo.
	 * AporteDetalladoModeloDTO)
	 */
	@Override
	public Long crearActualizarAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoDTO) {
		String firmaServicio = "AportesBusiness.crearActualizarAporteDetallado(AporteDetalladoModeloDTO) :: "
				+ " Aporte Detallado ID " + aporteDetalladoDTO.getId();
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		AporteDetallado aporteDetallado = aporteDetalladoDTO.convertToEntity();
		Long id = consultasCore.crearActualizarAporteDetallado(aporteDetallado);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#crearActualizarAporteGeneral(
	 * com.asopagos.dto.modelo.AporteGeneralModeloDTO)
	 */
	@Override
	public Long crearActualizarAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
		String firmaServicio = "AportesBusiness.crearActualizarAporteGeneral(AporteGeneralModeloDTO) :: "
				+ " Aporte ID " + aporteGeneralDTO.getId();
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		if (aporteGeneralDTO.getMarcaPeriodo() == null) {
			aporteGeneralDTO.setMarcaPeriodo(consultarPeriodoAporte(aporteGeneralDTO.getPeriodoAporte(),
					aporteGeneralDTO.getTipoSolicitante(), aporteGeneralDTO.getIdPersona()));
		}

		AporteGeneral aporteGeneral = aporteGeneralDTO.convertToEntity();
		if (aporteGeneral.getIdOperadorInformacion() != null) {
			logger.info("AporteGeneral.getIdOperadorInformacion() != null");
			Long idOperadorInformacion = consultasCore
					.consultarOperadorInformacionCodigo(aporteGeneral.getIdOperadorInformacion().toString());

			aporteGeneral.setIdOperadorInformacion(idOperadorInformacion);
		}
		Long id = consultasCore.crearActualizarAporteGeneral(aporteGeneral);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * crearActualizarDevolucionAporteDetalle(com.asopagos.dto.modelo.
	 * DevolucionAporteDetalleModeloDTO)
	 */
	@Override
	public Long crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO) {
		try {
			logger.debug("Inicio de método crearActualizarDevolucionAporteDetalle");
			DevolucionAporteDetalle devolucionAporteDetalle = devolucionAporteDetalleDTO.convertToEntity();
			Long id = consultasCore.crearActualizarDevolucionAporteDetalle(devolucionAporteDetalle);
			logger.debug("Fin método crearActualizarDevolucionAporteDetalle");
			return id;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en crearActualizarDevolucionAporteDetalle", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	
	// inicia ajuste
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarCuentaAporte(java.lang.Long)
	 */
	public List<CuentaAporteDTO> consultarCuentaAporte(Long idPersonaCotizante, List<AnalisisDevolucionDTO> analisisDevolucionDTO) {
		String firmaServicio = "AportesBusiness.consultarCuentaAporte(Long, List<AnalisisDevolucionDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);	
	
		Set<Long> idsAporteGeneral = new HashSet<>();
		Set<Long> idsRegistroGeneral = new HashSet<>();
		Set<Long> idsPersona = new HashSet<>();
		Set<Long> idsEmpresa = new HashSet<>();
	
		for (AnalisisDevolucionDTO analisisDevolucion : analisisDevolucionDTO) {
			idsAporteGeneral.add(analisisDevolucion.getIdAporte());
			idsRegistroGeneral.add(analisisDevolucion.getIdRegistroGeneral());
			if (analisisDevolucion.getIdPersona() != null) {
				idsPersona.add(analisisDevolucion.getIdPersona());
			}
			if (analisisDevolucion.getIdEmpresa() != null) {
				idsEmpresa.add(analisisDevolucion.getIdEmpresa());
			}
		}
		List<CuentaAporteDTO> cuentasAporte = new ArrayList<>();
		if (idPersonaCotizante != null) {
			cuentasAporte.addAll(consultasCore.consultarCuentasAporteCotizante(new ArrayList<>(idsAporteGeneral), idPersonaCotizante, null, null, null));
		} else {
			cuentasAporte.addAll(consultasCore.consultarCuentasAporte(new ArrayList<>(idsAporteGeneral),null));
			cuentasAporte.addAll(consultasCore.consultarCuentasAporteSinDetalle(new ArrayList<>(idsAporteGeneral), null, null));
		}
	
		DatosConsultaCuentaAporteDTO datosBd = new DatosConsultaCuentaAporteDTO();
		datosBd.setIdsAporteGeneral(new ArrayList<>(idsAporteGeneral));
		datosBd.setIdsRegistroGeneral(new ArrayList<>(idsRegistroGeneral));
		datosBd.setIdsPersona(new ArrayList<>(idsPersona));
		datosBd.setIdsEmpresa(new ArrayList<>(idsEmpresa));
	
		datosBd = consultarDatosParaCuentaAportes(datosBd, analisisDevolucionDTO);
		Map<Long, BigDecimal[]> controlSaldoAporte = new HashMap<>();
		List<Long> numOpNuevosCorreccion = new ArrayList<>();
	
		long timeStartAnalisisDevolucionDos = System.currentTimeMillis();
		for (CuentaAporteDTO cuentaAporteDTO : cuentasAporte) {
			for (AnalisisDevolucionDTO analisisDevolucion : analisisDevolucionDTO) {
				if (analisisDevolucion.getIdAporte().equals(cuentaAporteDTO.getIdAporteGeneral())) {
					actualizarCuentaAporteConAnalisisDevolucion(cuentaAporteDTO, analisisDevolucion);
				}
			}
		}
		for (int i = 0; i < cuentasAporte.size(); i++) {
			CuentaAporteDTO cuenta = cuentasAporte.get(i);
			actualizarCuentaAporte(cuenta, cuentasAporte, analisisDevolucionDTO, i, controlSaldoAporte, datosBd, numOpNuevosCorreccion);
		}
	
		if (numOpNuevosCorreccion != null && !numOpNuevosCorreccion.isEmpty()) {
			logger.warn("numOpNuevosCorreccion");
			Map<Long, String> nuevoNumerosCorreccionMapa = consultasCore.consultarNuevosNumerosCorreccion(numOpNuevosCorreccion);
			logger.warn("previo ingrsar metodo correccion");
			actualizarNumerosCorreccion(cuentasAporte, nuevoNumerosCorreccionMapa);
		}
	
		List<String> listaPlanillaConCorrecion = cuentasAporte.stream()
			.filter(cuentaAporte -> TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(cuentaAporte.getTipoMovimientoRecaudo()) &&
					TipoPlanillaEnum.CORRECIONES.equals(cuentaAporte.getTipoPlanilla()))
			.map(CuentaAporteDTO::getNumeroPlanilla)
			.collect(Collectors.toList());
	
		if (!listaPlanillaConCorrecion.isEmpty()) {
			List<CuentaAporteDTO> listaPlanillaConCorreccion = consultasPila.consultarPlanillasCorreccionN(listaPlanillaConCorrecion);
			actualizarPlanillasCorreccion(cuentasAporte, listaPlanillaConCorreccion);
		}
	
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return cuentasAporte;
	}

	public List<CuentaAporteDTO> consultarCuentaAportesConTipoRecaudo(Long idPersonaCotizante, List<AnalisisDevolucionDTO> analisisDevolucionDTO,
	TipoMovimientoRecaudoAporteEnum tipoRecaudo) {
			String firmaServicio = "AportesBusiness.consultarCuentaAportesConTipoRecaudo(Long, List<AnalisisDevolucionDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		logger.info("Inicia consultar Cuenta Aporte tipoRecaudo: "+tipoRecaudo);
	
		long timeStartconsultarCuentaAporte = System.currentTimeMillis();
	
		Set<Long> idsAporteGeneral = new HashSet<>();
		Set<Long> idsRegistroGeneral = new HashSet<>();
		Set<Long> idsPersona = new HashSet<>();
		Set<Long> idsEmpresa = new HashSet<>();
	
		long timeStartAnalisisDevolucion = System.currentTimeMillis();
		for (AnalisisDevolucionDTO analisisDevolucion : analisisDevolucionDTO) {
			idsAporteGeneral.add(analisisDevolucion.getIdAporte());
			idsRegistroGeneral.add(analisisDevolucion.getIdRegistroGeneral());
			if (analisisDevolucion.getIdPersona() != null) {
				idsPersona.add(analisisDevolucion.getIdPersona());
			}
			if (analisisDevolucion.getIdEmpresa() != null) {
				idsEmpresa.add(analisisDevolucion.getIdEmpresa());
			}
		}
		long timeEndAnalisisDevolucion = System.currentTimeMillis();
		logger.info("Finaliza consultarCuentaAportesConTipoRecaudo aporte ANALISIS DEVOLUCIÓN FOR: " + (timeEndAnalisisDevolucion - timeStartAnalisisDevolucion) + " ms");
	
		List<CuentaAporteDTO> cuentasAporte = new ArrayList<>();
		if (idPersonaCotizante != null) {
			cuentasAporte.addAll(consultasCore.consultarCuentasAporteCotizante(new ArrayList<>(idsAporteGeneral), idPersonaCotizante, null, null, null));
		} else {
			cuentasAporte.addAll(consultasCore.consultarCuentasAporte(new ArrayList<>(idsAporteGeneral),tipoRecaudo));
			cuentasAporte.addAll(consultasCore.consultarCuentasAporteSinDetalle(new ArrayList<>(idsAporteGeneral), null, null));
		}
	
		DatosConsultaCuentaAporteDTO datosBd = new DatosConsultaCuentaAporteDTO();
		datosBd.setIdsAporteGeneral(new ArrayList<>(idsAporteGeneral));
		datosBd.setIdsRegistroGeneral(new ArrayList<>(idsRegistroGeneral));
		datosBd.setIdsPersona(new ArrayList<>(idsPersona));
		datosBd.setIdsEmpresa(new ArrayList<>(idsEmpresa));
	
		datosBd = consultarDatosParaCuentaAportes(datosBd, analisisDevolucionDTO);
		long timeConsultarDatosParaCuentaAportes = System.currentTimeMillis();
		logger.info("timeConsultarDatosParaCuentaAportes " + (timeConsultarDatosParaCuentaAportes - timeStartconsultarCuentaAporte));
	
		Map<Long, BigDecimal[]> controlSaldoAporte = new HashMap<>();
		List<Long> numOpNuevosCorreccion = new ArrayList<>();
	
		long timeStartAnalisisDevolucionDos = System.currentTimeMillis();
		for (CuentaAporteDTO cuentaAporteDTO : cuentasAporte) {
			logger.warn("prueba antes de devolucion Con tipo recudo cuentasAporte");
			//logger.warn(cuentasAporte.toString());
			for (AnalisisDevolucionDTO analisisDevolucion : analisisDevolucionDTO) {
				logger.warn("prueba antes de ssdevolucion AnalisisDevolucion");
				//logger.warn(analisisDevolucion.toString());
				if (analisisDevolucion.getIdAporte().equals(cuentaAporteDTO.getIdAporteGeneral())) {
					actualizarCuentaAporteConAnalisisDevolucion(cuentaAporteDTO, analisisDevolucion);
					break;
				}
			}
		}
		long timeEndAnalisisDevolucionDos = System.currentTimeMillis();
		logger.info("Finaliza Cuenta con tipo recaudo aporte: " + (timeEndAnalisisDevolucionDos - timeStartAnalisisDevolucionDos) + " ms");
	
		long timeStartconsultarCuentaAporteTamano = System.currentTimeMillis();
		for (int i = 0; i < cuentasAporte.size(); i++) {
			CuentaAporteDTO cuenta = cuentasAporte.get(i);
			actualizarCuentaAporte(cuenta, cuentasAporte, analisisDevolucionDTO, i, controlSaldoAporte, datosBd, numOpNuevosCorreccion);
		}
		long timeEndCuentaAporteTamano = System.currentTimeMillis();
		logger.info("Finaliza Cuenta aporte: " + (timeEndCuentaAporteTamano - timeStartconsultarCuentaAporteTamano) + " ms");
	
		if (numOpNuevosCorreccion != null && !numOpNuevosCorreccion.isEmpty()) {
			logger.warn("numOpNuevosCorreccion");
			Map<Long, String> nuevoNumerosCorreccionMapa = consultasCore.consultarNuevosNumerosCorreccion(numOpNuevosCorreccion);
			logger.warn("previo ingrsar metodo correccion");
			actualizarNumerosCorreccion(cuentasAporte, nuevoNumerosCorreccionMapa);
		}
	
		List<String> listaPlanillaConCorrecion = cuentasAporte.stream()
			.filter(cuentaAporte -> TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(cuentaAporte.getTipoMovimientoRecaudo()) &&
					TipoPlanillaEnum.CORRECIONES.equals(cuentaAporte.getTipoPlanilla()))
			.map(CuentaAporteDTO::getNumeroPlanilla)
			.collect(Collectors.toList());
	
		if (!listaPlanillaConCorrecion.isEmpty()) {
			List<CuentaAporteDTO> listaPlanillaConCorreccion = consultasPila.consultarPlanillasCorreccionN(listaPlanillaConCorrecion);
			actualizarPlanillasCorreccion(cuentasAporte, listaPlanillaConCorreccion);
		}
	
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		long timeEndCuentaAporte = System.currentTimeMillis();
		logger.info("Finaliza Cuenta aporte: " + (timeEndCuentaAporte - timeStartconsultarCuentaAporte) + " ms");
		return cuentasAporte;
	}
	
	private void actualizarCuentaAporte(CuentaAporteDTO cuenta, List<CuentaAporteDTO> cuentasAporte, List<AnalisisDevolucionDTO> analisisDevolucionDTO, int i,
                                    Map<Long, BigDecimal[]> controlSaldoAporte, DatosConsultaCuentaAporteDTO datosBd,
                                    List<Long> numOpNuevosCorreccion) {
		AnalisisDevolucionDTO analisis = findAnalisisForCuenta(cuenta, analisisDevolucionDTO);
		if (analisis == null) {
			return;
		}

		BigDecimal totalAjuste;
		BigDecimal[] saldos;

		if (isEstadoVigente(cuenta)) {
			totalAjuste = calculateTotalAjuste(cuenta);
			saldos = updateSaldos(controlSaldoAporte, cuenta, totalAjuste);
			resetCuentaAporte(cuenta, totalAjuste, saldos);
		} else if (isEstadoCorregido(cuenta)) {
			totalAjuste = calculateTotalAjuste(cuenta);
			saldos = updateSaldos(controlSaldoAporte, cuenta, totalAjuste);
			resetCuentaAporte(cuenta, totalAjuste, saldos);
		} else if (i != 0) {
			CuentaAporteDTO cuentaAnterior = cuentasAporte.get(i - 1);
			if (cuenta.getIdAporteDetallado() != null && isSameAporte(cuenta, cuentaAnterior)) {
				saldos = updateSaldoAnterior(controlSaldoAporte, cuenta);
				totalAjuste = calculateTotalAjuste(cuenta);
				updateCuentaAporte(cuenta, saldos, totalAjuste, numOpNuevosCorreccion, datosBd);
			} else {
				saldos = updateSaldoAnterior(controlSaldoAporte, cuenta);
				totalAjuste = calculateTotalAjuste(cuenta);
				updateCuentaAporte(cuenta, saldos, totalAjuste, numOpNuevosCorreccion, datosBd);
			}
		}
	}
	
	private AnalisisDevolucionDTO findAnalisisForCuenta(CuentaAporteDTO cuenta, List<AnalisisDevolucionDTO> analisisDevolucionDTO) {
		for (AnalisisDevolucionDTO analisis : analisisDevolucionDTO) {
			if (cuenta.getIdAporteGeneral().equals(analisis.getIdAporte())) {
				return analisis;
			}
		}
		return null;
	}
	
	private boolean isEstadoVigente(CuentaAporteDTO cuenta) {
		return EstadoAporteEnum.VIGENTE.equals(cuenta.getEstadoAporte()) &&
			   !TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(cuenta.getTipoMovimientoRecaudo()) &&
			   !TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(cuenta.getTipoMovimientoRecaudo());
	}
	
	private boolean isEstadoCorregido(CuentaAporteDTO cuenta) {
		return EstadoAporteEnum.CORREGIDO.equals(cuenta.getEstadoAporte()) &&
			   !TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(cuenta.getTipoMovimientoRecaudo()) &&
			   !TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(cuenta.getTipoMovimientoRecaudo());
	}
	
	private BigDecimal calculateTotalAjuste(CuentaAporteDTO cuenta) {
		return (cuenta.getAjuste() != null ? cuenta.getAjuste() : BigDecimal.ZERO)
			   .add(cuenta.getInteresesAjuste() != null ? cuenta.getInteresesAjuste() : BigDecimal.ZERO);
	}
	
	
	private BigDecimal[] updateSaldos(Map<Long, BigDecimal[]> controlSaldoAporte, CuentaAporteDTO cuenta, BigDecimal totalAjuste) {
		BigDecimal[] saldos = {cuenta.getAjuste() != null ? cuenta.getAjuste() : BigDecimal.ZERO,
							cuenta.getInteresesAjuste() != null ? cuenta.getInteresesAjuste() : BigDecimal.ZERO,
							totalAjuste};

		Long key = cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L;
		if (!controlSaldoAporte.containsKey(key)) {
			controlSaldoAporte.put(key, saldos);
		} else {
			BigDecimal[] saldoActual = controlSaldoAporte.get(key);
			saldoActual[0] = saldoActual[0].add(saldos[0]);
			saldoActual[1] = saldoActual[1].add(saldos[1]);
			saldoActual[2] = saldoActual[2].add(saldos[2]);
		}
		return saldos;
	}
	
	private void resetCuentaAporte(CuentaAporteDTO cuenta, BigDecimal totalAjuste, BigDecimal[] saldos) {
		cuenta.setAporteDeRegistro(cuenta.getAjuste());
		cuenta.setInteresesAporte(cuenta.getInteresesAjuste());
		cuenta.setAporteFinalRegistro(cuenta.getAjuste());
		cuenta.setInteresesFinalAjuste(cuenta.getInteresesAjuste());
		cuenta.setTotalAporteFinal(totalAjuste);
		cuenta.setTotalAporte(totalAjuste);
		cuenta.setAjuste(BigDecimal.ZERO);
		cuenta.setInteresesAjuste(BigDecimal.ZERO);
		cuenta.setTotalAjuste(BigDecimal.ZERO);
		cuenta.setNumeroOperacion(cuenta.getIdMovimientoAporte().toString());
	}
	
	private boolean isSameAporte(CuentaAporteDTO cuenta, CuentaAporteDTO cuentaAnterior) {
		return cuenta.getIdAporteGeneral().equals(cuentaAnterior.getIdAporteGeneral()) &&
			   cuenta.getIdAporteDetallado().equals(cuentaAnterior.getIdAporteDetallado());
	}
	
	private BigDecimal[] updateSaldoAnterior(Map<Long, BigDecimal[]> controlSaldoAporte, CuentaAporteDTO cuenta) {
		Long key = cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L;
		BigDecimal[] saldoActual = controlSaldoAporte.get(key);
		if (saldoActual == null) {
			saldoActual = new BigDecimal[3];
			saldoActual[0] = BigDecimal.ZERO;
			saldoActual[1] = BigDecimal.ZERO;
			saldoActual[2] = BigDecimal.ZERO;
			controlSaldoAporte.put(key, saldoActual);
		}
		cuenta.setAporteDeRegistro(saldoActual[0]);
		cuenta.setInteresesAporte(saldoActual[1]);
		cuenta.setTotalAporte(saldoActual[2]);
		return saldoActual;
	}
	
	private void updateCuentaAporte(CuentaAporteDTO cuenta, BigDecimal[] saldoActual, BigDecimal totalAjuste,
									List<Long> numOpNuevosCorreccion, DatosConsultaCuentaAporteDTO datosBd) {
		BigDecimal montoAjuste = (cuenta.getAjuste() != null ? cuenta.getAjuste() : BigDecimal.ZERO);
		BigDecimal interesAjuste = (cuenta.getInteresesAjuste() != null ? cuenta.getInteresesAjuste() : BigDecimal.ZERO);
		BigDecimal totalAporteAjuste = montoAjuste.add(interesAjuste);
	
		cuenta.setAjuste(montoAjuste);
		cuenta.setInteresesAjuste(interesAjuste);
	
		if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_ALTA.equals(cuenta.getTipoAjusteMonetario())) {
			handleCorreccionAlAlza(cuenta, saldoActual, montoAjuste, interesAjuste, totalAporteAjuste);
		} else if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA.equals(cuenta.getTipoAjusteMonetario())) {
			handleCorreccionALaBaja(cuenta, saldoActual, montoAjuste, interesAjuste, totalAporteAjuste, numOpNuevosCorreccion, datosBd);
		} else if (TipoAjusteMovimientoAporteEnum.DEVOLUCION.equals(cuenta.getTipoAjusteMonetario())) {
			handleDevolucion(cuenta, saldoActual, montoAjuste, interesAjuste, totalAporteAjuste);
		}
	
		saldoActual[0] = cuenta.getAporteFinalRegistro();
		saldoActual[1] = cuenta.getInteresesFinalAjuste();
		saldoActual[2] = cuenta.getTotalAporteFinal();
	}
	
	private void handleCorreccionAlAlza(CuentaAporteDTO cuenta, BigDecimal[] saldoActual, BigDecimal montoAjuste, BigDecimal interesAjuste, BigDecimal totalAporteAjuste) {
		if (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(cuenta.getTipoMovimientoRecaudo())) {
			String numeroPlanillaN = consultasCore.consultarNumeroDePlanillaN(cuenta.getIdMovimientoAporte());
			cuenta.setNumeroPlanilla(numeroPlanillaN);
			cuenta.setAjuste(montoAjuste.subtract(saldoActual[0]));
			cuenta.setNumeroPlanillaCorregida(consultasCore.consultarNumeroDePlanillaCorregida(cuenta.getIdMovimientoAporte()));
			cuenta.setInteresesAjuste(interesAjuste.subtract(saldoActual[1]));
			cuenta.setAporteDeRegistro(BigDecimal.ZERO);
			cuenta.setInteresesAporte(BigDecimal.ZERO);
			cuenta.setTotalAporte(BigDecimal.ZERO);
			cuenta.setAporteFinalRegistro(montoAjuste);
			cuenta.setInteresesFinalAjuste(interesAjuste);
			cuenta.setTotalAporteFinal(montoAjuste.add(interesAjuste));
			cuenta.setNumeroOperacion(cuenta.getIdMovimientoAporte().toString());
			cuenta.setTotalAjuste(interesAjuste.subtract(saldoActual[1]).add(montoAjuste.subtract(saldoActual[0])));
			cuenta.setTipoPlanilla(TipoPlanillaEnum.CORRECIONES);
		} else {
			cuenta.setAporteFinalRegistro(saldoActual[0].add(montoAjuste));
			cuenta.setInteresesFinalAjuste(saldoActual[1].add(interesAjuste));
			cuenta.setTotalAporteFinal(saldoActual[2].add(totalAporteAjuste));
			cuenta.setNumeroOperacion(cuenta.getIdMovimientoAporte().toString());
		}
	}
	
	private void handleCorreccionALaBaja(CuentaAporteDTO cuenta, BigDecimal[] saldoActual, BigDecimal montoAjuste, BigDecimal interesAjuste,
										 BigDecimal totalAporteAjuste, List<Long> numOpNuevosCorreccion, DatosConsultaCuentaAporteDTO datosBd) {
		numOpNuevosCorreccion.add(cuenta.getIdAporteDetallado());
		cuenta.setAporteFinalRegistro(saldoActual[0].subtract(montoAjuste));
		cuenta.setInteresesFinalAjuste(saldoActual[1].subtract(interesAjuste));
		cuenta.setTotalAporteFinal(saldoActual[2].subtract(totalAporteAjuste));
		cuenta.setNumeroOperacion(cuenta.getIdMovimientoAporte().toString());
		cuenta.setEstadoAporte(EstadoAporteEnum.ANULADO);
		cuenta.setNuevoNumeroOperacion(datosBd.getNumerosOperacion().get(cuenta.getIdAporteGeneral()));
		PersonaModeloDTO persona = consultarAportanteNuevoCorreccion(cuenta.getIdAporteGeneral(), cuenta.getIdAporteDetallado());
		cuenta.setTipoIdentificacionAportanteCorreccion(persona.getTipoIdentificacion());
		cuenta.setNumeroIdentificacionAportanteCorreccion(persona.getNumeroIdentificacion());
	}
	
	private void handleDevolucion(CuentaAporteDTO cuenta, BigDecimal[] saldoActual, BigDecimal montoAjuste, BigDecimal interesAjuste, BigDecimal totalAporteAjuste) {
		cuenta.setAporteFinalRegistro(saldoActual[0].subtract(montoAjuste));
		cuenta.setInteresesFinalAjuste(saldoActual[1].subtract(interesAjuste));
		cuenta.setTotalAporteFinal(saldoActual[2].subtract(totalAporteAjuste));
		cuenta.setNumeroOperacion(cuenta.getIdMovimientoAporte().toString());
	}
	
	private void actualizarCuentaAporteConAnalisisDevolucion(CuentaAporteDTO cuentaAporteDTO, AnalisisDevolucionDTO analisisDevolucion) {
		cuentaAporteDTO.setNumeroOperacion(analisisDevolucion.getNumOperacion());
		cuentaAporteDTO.setNumeroPlanilla(analisisDevolucion.getNumPlanilla());
		cuentaAporteDTO.setTipoArhivo(analisisDevolucion.getTipoArchivo());
		cuentaAporteDTO.setTipoPlanilla(analisisDevolucion.getTipoPlanilla());
		cuentaAporteDTO.setEstadoArchivo(analisisDevolucion.getEstadoArchivo());
	
		if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(analisisDevolucion.getTipoSolicitante()) ||
			(cuentaAporteDTO.getTipoCotizante() != null && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(cuentaAporteDTO.getTipoCotizante()))) {
			cuentaAporteDTO.setTipoIdentificacionAportante(analisisDevolucion.getTipoIdentificacionTramitador());
			cuentaAporteDTO.setNumeroIdentificacionAportante(analisisDevolucion.getNumeroIdentificacionTramitador());
			cuentaAporteDTO.setNombreCompletoAportante(analisisDevolucion.getNombreCompletoTramitador());
		} else {
			cuentaAporteDTO.setTipoIdentificacionAportante(analisisDevolucion.getTipoIdentificacion());
			cuentaAporteDTO.setNumeroIdentificacionAportante(analisisDevolucion.getNumeroIdentificacion());
			cuentaAporteDTO.setNombreCompletoAportante(analisisDevolucion.getNombreCompleto());
		}
	
		cuentaAporteDTO.setTipoIdentificacionTramitador(analisisDevolucion.getTipoIdentificacionTramitador());
		cuentaAporteDTO.setNumeroIdentificacionTramitador(analisisDevolucion.getNumeroIdentificacionTramitador());
		cuentaAporteDTO.setNombreCompletoTramitador(analisisDevolucion.getNombreCompletoTramitador());
		cuentaAporteDTO.setPeriodoPago(analisisDevolucion.getPeriodo());
		cuentaAporteDTO.setPagadorPorTerceros(analisisDevolucion.getPagadorPorTerceros());
		cuentaAporteDTO.setNombreCompleto(analisisDevolucion.getNombreCompleto());
		cuentaAporteDTO.setConDetalle(analisisDevolucion.getConDetalle());
		cuentaAporteDTO.setCodigoEntidadFinanciera(analisisDevolucion.getCodigoEntidadFinanciera());
		cuentaAporteDTO.setTieneModificaciones(analisisDevolucion.getTieneModificaciones());
		cuentaAporteDTO.setIdentificadorDocumento(analisisDevolucion.getIdEcmArchivo());
	}
	
	private void actualizarNumerosCorreccion(List<CuentaAporteDTO> cuentasAporte, Map<Long, String> nuevoNumerosCorreccionMapa) {
		int i = 0;
		for (CuentaAporteDTO cuenta : cuentasAporte) {
			i++;
			logger.warn("correccion en actualizarNumerosCorreccion : " +i);
			// aca consultamos :D.
			String numeroOPCorreccion = consultasCore.consutlarNumeroOperacionCorreccion(cuenta.getIdAporteDetallado());
			logger.warn(cuenta.toString());
			cuenta.setNumeroOperacion(numeroOPCorreccion);
			if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA.equals(cuenta.getTipoAjusteMonetario()) &&
				nuevoNumerosCorreccionMapa.containsKey(cuenta.getIdAporteDetallado())) {
				cuenta.setNuevoNumeroOperacion(nuevoNumerosCorreccionMapa.get(cuenta.getIdAporteDetallado()));
			}
		}
	}
	
	private void actualizarPlanillasCorreccion(List<CuentaAporteDTO> cuentasAporte, List<CuentaAporteDTO> listaPlanillaConCorreccion) {
		for (CuentaAporteDTO cuentaAporte : cuentasAporte) {
			logger.warn("cuenta aporte actualizarPlanillasCorreccion");
			logger.warn(cuentaAporte.toString());
			for (CuentaAporteDTO cuentaConCorreccion : listaPlanillaConCorreccion) {
				logger.warn("cuenta correccion actualizarPlanillasCorreccion");
				logger.warn(cuentaConCorreccion);
				if (TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(cuentaAporte.getTipoMovimientoRecaudo()) &&
					TipoPlanillaEnum.CORRECIONES.equals(cuentaAporte.getTipoPlanilla()) &&
					cuentaAporte.getNumeroPlanilla().equals(cuentaConCorreccion.getNumeroPlanilla())) {
					cuentaAporte.setNumeroPlanillaCorregida(cuentaConCorreccion.getNumeroPlanillaCorregida());
				}
			}
		}
	}

	// finaliza ajuste

	/**
     * Método que obtiene la información del aportante ingresado en la corrección como nuevo aporte
     * 
     * @param idAporteGeneralCorregido
     *        Identificador del aporte general al cual se le corrigieron sus valores de aportes
     * @return Persona aportante
     */
	private PersonaModeloDTO consultarAportanteNuevoCorreccion(Long idAporteGeneralCorregido, Long idAporteDetalladoCorregido){
	    String firmaServicio = "AportesBusiness.consultarAportanteNuevoCorreccion(Long idAporteGeneralCorregido)";
	    logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
	    return consultasCore.consultarAportanteCorreccion(idAporteGeneralCorregido, idAporteDetalladoCorregido);
	}
	
	/**
	 * Método para la consulta de datos previos para la consulta de cuenta
	 * aportes
	 * 
	 * @param datosConsulta
	 * @param datosAnalisis
	 * @return
	 */
	private DatosConsultaCuentaAporteDTO consultarDatosParaCuentaAportes(DatosConsultaCuentaAporteDTO datosConsulta,
			List<AnalisisDevolucionDTO> datosAnalisis) {
		String firmaServicio = "AportesBusiness.consultarDatosParaCuentaAportes(DatosConsultaCuentaAporteDTO)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		DatosConsultaCuentaAporteDTO result = datosConsulta;

		if (datosConsulta.getIdsAporteGeneral() != null && !datosConsulta.getIdsAporteGeneral().isEmpty()) {
			List<AporteGeneralModeloDTO> aportes = consultasCore
					.consultarAportesGenerales(datosConsulta.getIdsAporteGeneral(), null);
			for (AporteGeneralModeloDTO aporte : aportes) {
				AnalisisDevolucionDTO analisis = ubicarAnalisisDTOPorIdAPorte(datosAnalisis, aporte.getId());

				if (analisis != null && aporte.getIdEmpresa() != null
						&& !datosConsulta.getIdsEmpresa().contains(aporte.getIdEmpresa())) {
					datosConsulta.getIdsEmpresa().add(aporte.getIdEmpresa());
					analisis.setIdEmpresa(aporte.getIdEmpresa());
				}
				if (analisis != null && aporte.getIdPersona() != null
						&& !datosConsulta.getIdsPersona().contains(aporte.getIdPersona())) {
					datosConsulta.getIdsPersona().add(aporte.getIdPersona());
					analisis.setIdPersona(aporte.getIdPersona());
				}
			}
		}

		if (datosConsulta.getIdsAporteGeneral() != null && !datosConsulta.getIdsAporteGeneral().isEmpty()) {
			result.setNumerosOperacion(
					consultasCore.consultarCotizantesCorrecciones(datosConsulta.getIdsAporteGeneral()));
		}

		if (datosConsulta.getIdsPersona() != null && !datosConsulta.getIdsPersona().isEmpty()) {
			result.setPersonas(
					consultasCore.consultarPersonasPorListadoIds(datosConsulta.getIdsPersona(), Boolean.FALSE));

			for (PersonaModeloDTO persona : result.getPersonas().values()) {
				List<AnalisisDevolucionDTO> analisisPersona = ubicarAnalisisDTOPorIdPersona(datosAnalisis,
						persona.getIdPersona(), Boolean.FALSE);

				for (AnalisisDevolucionDTO analisis : analisisPersona) {
					analisis.setTipoIdentificacion(persona.getTipoIdentificacion());
					analisis.setNumeroIdentificacion(persona.getNumeroIdentificacion());
				}
			}
		}
		//logger.info("consultarDatosParaCuentaAportes Log_8");

		if (datosConsulta.getIdsEmpresa() != null && !datosConsulta.getIdsEmpresa().isEmpty()) {
			result.setPersonasPorEmpresa(
					consultasCore.consultarPersonasPorListadoIds(datosConsulta.getIdsEmpresa(), Boolean.TRUE));

			for (Long idEmpresa : datosConsulta.getIdsEmpresa()) {
				List<AnalisisDevolucionDTO> analisisEmpresa = ubicarAnalisisDTOPorIdPersona(datosAnalisis, idEmpresa,
						Boolean.TRUE);
				PersonaModeloDTO personaEmpresa = result.getPersonasPorEmpresa().get(idEmpresa);

				for (AnalisisDevolucionDTO analisis : analisisEmpresa) {
					analisis.setTipoIdentificacion(personaEmpresa.getTipoIdentificacion());
					analisis.setNumeroIdentificacion(personaEmpresa.getNumeroIdentificacion());
				}
			}
		}
		//logger.info("consultarDatosParaCuentaAportes Log_11");

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * @param datosAnalisis
	 * @param id
	 * @return
	 */
	private AnalisisDevolucionDTO ubicarAnalisisDTOPorIdAPorte(List<AnalisisDevolucionDTO> datosAnalisis, Long id) {
		String firmaServicio = "AportesBusiness.ubicarAnalisisDTOPorIdAPorte(List<AnalisisDevolucionDTO>, Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		for (AnalisisDevolucionDTO analisis : datosAnalisis) {
			if (id.equals(analisis.getIdAporte())) {
				return analisis;
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return null;
	}

	/**
	 * @param datosAnalisis
	 * @param idPersona
	 * @param esEmpresa
	 * @return
	 */
	private List<AnalisisDevolucionDTO> ubicarAnalisisDTOPorIdPersona(List<AnalisisDevolucionDTO> datosAnalisis,
			Long idPersona, Boolean esEmpresa) {
		String firmaServicio = "AportesBusiness.ubicarAnalisisDTOPorIdPersona(List<AnalisisDevolucionDTO>, Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<AnalisisDevolucionDTO> result = new ArrayList<>();

		for (AnalisisDevolucionDTO analisis : datosAnalisis) {
			Boolean agregar = Boolean.FALSE;
			if (!esEmpresa && idPersona.equals(analisis.getIdPersona()) && analisis.getTipoIdentificacion() == null) {
				agregar = Boolean.TRUE;
			}

			if (esEmpresa && idPersona.equals(analisis.getIdEmpresa()) && analisis.getTipoIdentificacion() == null) {
				agregar = Boolean.TRUE;
			}

			if (agregar && !result.contains(analisis)) {
				result.add(analisis);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#actualizarAporteDetallado(
	 * java.util.List)
	 */
	@Override
	public void actualizarAporteDetallado(List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO) {
		try {
			logger.debug(
					"Inicia servicio actualizarAporteDetallado(List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO)");
			List<AporteDetallado> listaAporteDetallado = new ArrayList<>();

			for (AporteDetalladoModeloDTO aporteDetalladoDTO : listaAporteDetalladoDTO) {
				listaAporteDetallado.add(aporteDetalladoDTO.convertToEntity());
			}

			consultasCore.actualizarAporteDetallado(listaAporteDetallado);
			logger.debug(
					"Finaliza servicio actualizarAporteDetallado(List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO)");
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el servicio actualizarAporteDetallado(List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * actualizarListaMovimientoAporte(java.util.List)
	 */
	@Override
	public void actualizarListaMovimientoAporte(List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO) {
		try {
			logger.debug(
					"Inicio de método actualizarMovimientoAporte(List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO)");
			List<MovimientoAporte> listaMovimientoAporte = new ArrayList<>();

			for (MovimientoAporteModeloDTO movimientoAporteDTO : listaMovimientoAporteDTO) {
				MovimientoAporte movimientoAporte = movimientoAporteDTO.convertToEntity();
				listaMovimientoAporte.add(movimientoAporte);
			}

			consultasCore.actualizarMovimientoAporte(listaMovimientoAporte);
			logger.debug(
					"Fin método actualizarMovimientoAporte(List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO)");
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método actualizarMovimientoAporte(List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * crearActualizarDevolucionAporte(com.asopagos.dto.modelo.
	 * DevolucionAporteModeloDTO)
	 */
	@Override
	public Long crearActualizarDevolucionAporte(DevolucionAporteModeloDTO devolucionAporteDTO) {
		try {
			logger.debug("Inicia servicio crearActualizarDevolucionAporte");
			DevolucionAporte devolucionAporte = devolucionAporteDTO.convertToEntity();
			Long id = consultasCore.crearActualizarDevolucionAporte(devolucionAporte);
			logger.debug("Finaliza servicio crearActualizarDevolucionAporte");
			return id;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio crearActualizarDevolucionAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarRegistroGeneralId(
	 * java.lang.Long)
	 */
	@Override
	public RegistroGeneralModeloDTO consultarRegistroGeneralId(Long idRegistroGeneral) {
		try {
			logger.debug("Inicio de método consultarRegistroGeneralId(Long idRegistroGeneral)");
			RegistroGeneral registroGeneral = consultasStaging.consultarRegistroGeneralId(idRegistroGeneral);
			RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();
			registroGeneralDTO.convertToDTO(registroGeneral);
			logger.debug("Fin de método consultarRegistroGeneralId(Long idRegistroGeneral) ");
			return registroGeneralDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (NonUniqueResultException nue) {
			logger.error("Existe más de un registro general para una solicitud de aporte", nue);
			throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);
		} catch (Exception e) {
			logger.error("Ocurrio un error en el método consultarRegistroGeneralId(Long idRegistroGeneral)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#reconocerIngresos(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
	 */
	@Override
	@Asynchronous
	public void reconocerIngresos(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			TipoAfiliadoEnum tipoAfiliado) {
		// Se consulta el empleador por tipo y número de identificación
		Empleador empleador = consultasCore.consultarEmpleador(tipoIdentificacion, numeroIdentificacion);
		List<AporteDetallado> aportes = new ArrayList<>();
		if (empleador != null) {
			// Se consultan los aportes realizados por el empleador
			aportes = consultasCore.consultarAportesPorEmpleador(EstadoAfiliadoEnum.ACTIVO, empleador.getIdEmpleador());
		} else {
			/*
			 * PERSONA - DEPENDIENTE = AportesDetallados -> AporteGeneral y miro
			 * el empleador si EXISTE para poderlo pasar a registrado el aporte
			 */
			if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
				consultasCore.consultarAportesPorAportanteEmpleador(tipoIdentificacion, numeroIdentificacion);
			} else {
				if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliado)
						|| TipoAfiliadoEnum.PENSIONADO.equals(tipoAfiliado)) {
					/*
					 * Se consultan todos los aportes detallados por persona
					 * independiente
					 */
					aportes = consultasCore.consultarAportesDetalladosPorPersona(tipoIdentificacion,
							numeroIdentificacion);
				}
			}
		}
		List<Long> idsAportes = new ArrayList<>();
		for (AporteDetallado aporteDetallado : aportes) {
			idsAportes.add(aporteDetallado.getId());
		}

		actualizarReconocimientoAportes(idsAportes, EstadoRegistroAporteEnum.REGISTRADO,
				FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarAporteGeneral(java.
	 * lang.Long)
	 */
	@Override
	public AporteGeneralModeloDTO consultarAporteGeneral(Long idAporteGeneral) {
		try {
			logger.debug("Inicio de método consultarAporteGeneral(Long idAporteGeneral)");
			AporteGeneral aporteGeneral = consultasCore.consultarAporteGeneral(idAporteGeneral);
            if (aporteGeneral != null) {
                AporteGeneralModeloDTO aporteGeneralDTO = new AporteGeneralModeloDTO();
                aporteGeneralDTO.convertToDTO(aporteGeneral);
                logger.debug("Fin de método consultarAporteGeneral(Long idAporteGeneral)");
                return aporteGeneralDTO;
            }
            else {
                return null;
			}
		} catch (NonUniqueResultException nue) {
			logger.error("Existe más de un aporte general", nue);
			throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, nue);
		} catch (Exception e) {
			logger.error("Ocurrio un error en el método consultarAporteGeneral(Long idAporteGeneral)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarMovimientoHistoricos
	 * (com.asopagos.aportes.dto.ConsultaMovimientoIngresosDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MovimientoIngresosDTO> consultarMovimientoHistoricos(
			ConsultaMovimientoIngresosDTO consultaMovimientosIngresos) {
		try {
			logger.debug(
					"Inicio de consultarMovimientoHistoricos(ConsultaMovimientoIngresosDTO consultaMovimientosIngresos)");
			List<MovimientoIngresosDTO> movimientosIngresos = consultasCore
					.consultarMovimientoHistoricos(consultaMovimientosIngresos);
			movimientosIngresos = consultasPila
					.consultarMovimientoHistoricosPila(movimientosIngresos);

			if (movimientosIngresos.size() > 1) {
				Collections.sort(movimientosIngresos,
						(o1, o2) -> o2.getFechaReconocimiento().compareTo(o1.getFechaReconocimiento()));
			}
			logger.debug(
					"Fin de método consultarMovimientoHistoricos(ConsultaMovimientoIngresosDTO consultaMovimientosIngresos)");
			return movimientosIngresos;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el método consultarMovimientoHistoricos(ConsultaMovimientoIngresosDTO consulta)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarMovimientoHistoricosDetallado(com.asopagos.aportes.dto.
	 * MovimientoIngresosDTO)
	 */
	@Override
	public List<MovimientoIngresosDetalladoDTO> consultarMovimientoHistoricosDetallado(
			MovimientoIngresosDTO movimientoIngresosDTO) {
		try {
			logger.debug("Inicia servicio consultarMovimientoHistoricosDetallado(MovimientoIngresosDTO)");
			List<MovimientoIngresosDetalladoDTO> movimientosDetallados = consultasCore
					.consultarMovimientoDetallado(movimientoIngresosDTO.getIdAporte());
			logger.debug("Finaliza servicio consultarMovimientoHistoricosDetallado(MovimientoIngresosDTO)");
			return movimientosDetallados;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error en el servicio consultarMovimientoHistoricosDetallado(MovimientoIngresosDTO)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarMovimientoHistoricos
	 * (com.asopagos.aportes.dto.ConsultaMovimientoIngresosDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MovimientoIngresosDTO> consultarAportesRelacionados(
			ConsultaAporteRelacionadoDTO consultaAportesRelacionados) {
		logger.debug("Inicio de consultarAportesRelacionados");
		List<MovimientoIngresosDTO> movimientosIngresos = consultasCore
				.consultarAportesRelacionados(consultaAportesRelacionados);
		movimientosIngresos = consultasPila
					.consultarMovimientoHistoricosPila(movimientosIngresos);
		logger.debug("Fin de método consultarAportesRelacionados");
		return movimientosIngresos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarAporteGeneralPorRegistro(java.lang.Long)
	 */
	@Override
	@Deprecated
	public AporteGeneralModeloDTO consultarAporteGeneralPorRegistro(Long idRegistroGeneral) {
		try {
			logger.debug("Inicio de método consultarAporteGeneralPorRegistro(Long idRegistroGeneral)");
			AporteGeneral aporteGeneral = consultasCore.consultarAporteGeneralPorRegistro(idRegistroGeneral);
			AporteGeneralModeloDTO aporteGeneralDTO = new AporteGeneralModeloDTO();
			aporteGeneralDTO.convertToDTO(aporteGeneral);
			logger.debug("Fin de método consultarAporteGeneralPorRegistro(Long idRegistroGeneral)");
			return aporteGeneralDTO;
		} catch (Exception e) {
			logger.error(
					"Ocurrio un error en el método Inicio de método consultarAporteGeneralPorRegistro(Long idRegistroGeneral)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#registrarMovimientoIngresos(java.util.List)
	 */
	@Override
	@Asynchronous
	public void registrarMovimientoIngresos(List<Long> idsAportes, EstadoRegistroAporteEnum estadoRegistro) {
		String firmaServicio = "AportesBusiness.registrarMovimientoIngresos(List<Long>, EstadoRegistroAporteEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		actualizarReconocimientoAportes(idsAportes, estadoRegistro,
				FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * Método encargado de la actualización de aportes por reconocimiento
	 * 
	 * @param idsAportes
	 * @param estadoRegistro
	 * @param formaReconocimiento
	 */
	@Override
	@Asynchronous
	public void actualizarReconocimientoAportes(List<Long> idsAportes, EstadoRegistroAporteEnum estadoRegistro,
			FormaReconocimientoAporteEnum formaReconocimiento) {
		String firmaServicio = "AportesBusiness.actualizarReconocimientoAportes(List<Long>, EstadoRegistroAporteEnum, "
				+ "FormaReconocimientoAporteEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		// se consultan los aportes generales y detallados a modificar
		List<AporteGeneralModeloDTO> aportesGenerales = consultasCore.consultarAportesGeneralesPorIds(idsAportes);
		List<AporteDetalladoModeloDTO> aportesDetallados = consultasCore
				.consultarAportesDetalladosPorIdsGeneral(idsAportes);

		// primero se actualizan los aportes generales para marcar el inicio del
		// proceso
		for (AporteGeneralModeloDTO aporteGeneral : aportesGenerales) {
			aporteGeneral.setEnProcesoReconocimiento(Boolean.TRUE);
		}
		consultasCore.actualizarPaqueteAportes(aportesGenerales, null);

		// luego se actualizan los aortes generales y detallados con sus nuevos
		// valores de reconocimiento
		for (AporteDetalladoModeloDTO aporteDetallado : aportesDetallados) {
			aporteDetallado.setEstadoRegistroAporteCotizante(estadoRegistro);
			aporteDetallado.setFormaReconocimientoAporte(formaReconocimiento);
			aporteDetallado.setFechaMovimiento(new Date().getTime());
		}

		for (AporteGeneralModeloDTO aporteGeneral : aportesGenerales) {
			aporteGeneral.setFechaReconocimiento(new Date().getTime());
			aporteGeneral.setEstadoRegistroAporteAportante(estadoRegistro);
			aporteGeneral.setFormaReconocimientoAporte(formaReconocimiento);
		}
		consultasCore.actualizarPaqueteAportes(aportesGenerales, aportesDetallados);

		// finalmente se marcan de nuevo los aportes generales para dejar
		// constancia que terminaron su proceso
		for (AporteGeneralModeloDTO aporteGeneral : aportesGenerales) {
			aporteGeneral.setEnProcesoReconocimiento(Boolean.FALSE);
		}
		consultasCore.actualizarPaqueteAportes(aportesGenerales, null);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#crearActualizarCorreccion(com
	 * .asopagos.dto.modelo.CorreccionModeloDTO)
	 */
	@Override
	public Long crearActualizarCorreccion(CorreccionModeloDTO correccionDTO) {
		try {
			logger.debug("Inicia servicio crearActualizarCorreccion");
			Correccion correccion = correccionDTO.convertToEntity();
			Long id = consultasCore.crearActualizarCorreccion(correccion);
			logger.debug("Finaliza servicio crearActualizarCorreccion");
			return id;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio crearActualizarCorreccion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#borrarTemporalesPILA(java.
	 * lang.Long)
	 */
	@Override
	public void borrarTemporalesPILA(Long idRegistroGeneral) {
		try {
			logger.debug("Inicia servicio borrarTemporalesPILA(Long idRegistroGeneral)");
			consultasPila.ejecutarDeleteTemporalesPILA(idRegistroGeneral);
			logger.debug("Finaliza servicio borrarTemporalesPILA(Long idRegistroGeneral)");
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio borrarTemporalesPILA(Long idRegistroGeneral)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#validarAporteProcesado(java.
	 * lang.Long, java.lang.Long)
	 */
	@Override
	public Map<String, Boolean> validarAporteProcesado(Long idAporteGeneral, Long idAporteDetallado) {
		try {
			logger.debug("Inicia el servicio validarAporteProcesado(Long idAporteGeneral, Long idAporteDetallado)");
			Map<String, Boolean> validacion = consultasCore.consultarEstadoAporte(idAporteGeneral, idAporteDetallado);
			logger.debug("Finaliza el servicio validarAporteProcesado(Long idAporteGeneral, Long idAporteDetallado)");
			return validacion;

		} catch (Exception e) {
			logger.debug("Finaliza el servicio validarAporteProcesado(Long idAporteGeneral, Long idAporteDetallado)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#buscarMunicipio(java.lang.Long)
	 */
	public Short buscarMunicipio(String codigoMunicipio) {
		logger.debug("Inicia el servicio buscarMunicipio(Long codigoMunicipio)");
		try {
			logger.debug("Finaliza el servicio buscarMunicipio(Long codigoMunicipio)");
			return consultasCore.buscarMunicipio(codigoMunicipio);
		} catch (Exception e) {
			logger.debug("Finaliza el servicio buscarMunicipio(Long codigoMunicipio):Error técnico inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarHistoricoEvaluacionAporte(com.asopagos.enumeraciones.aportes.EstadoAporteEnum,
	 *      com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum,
	 *      com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum,
	 *      java.lang.Boolean,
	 *      com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public HistoricoDTO consultarHistoricoEvaluacionAporte(EstadoAporteEnum estadoAporte,
			ModalidadRecaudoAporteEnum modalidadRecaudo, EstadoProcesoArchivoEnum estadoProcesoArchivo,
			Boolean tieneModificaciones, EstadoSolicitudAporteEnum estadoSolicitudAporte,
			TipoIdentificacionEnum tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
			String periodoAporte) {

		String firmaMetodo = "AportesBusiness.consultarHistoricoEvaluacionAporte(EstadoAporteEnum, ModalidadRecaudoAporteEnum, "
				+ "EstadoProcesoArchivoEnum, Boolean, EstadoSolicitudAporteEnum, TipoIdentificacionEnum, String, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		HistoricoDTO historico = consultarHistoricoEvaluacionAporteDetalle(estadoAporte, modalidadRecaudo,
				estadoProcesoArchivo, tieneModificaciones, estadoSolicitudAporte, tipoIdentificacionCotizante,
				numeroIdentificacionCotizante, periodoAporte, null);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return historico;
	}

	/**
	 * Método que consulta la evaluación de condiciones por las cuales un aporte
	 * puede ser tenido o no en cuenta en una solicitud de devolución o
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
	 * @param subsidioPagado
	 *            Indicador de pago de subsidio para el cotizante
	 * @return Información de la evaluación de condiciones de acuerdo al
	 *         registro histórico del aporte
	 */
	private HistoricoDTO consultarHistoricoEvaluacionAporteDetalle(EstadoAporteEnum estadoAporte,
			ModalidadRecaudoAporteEnum modalidadRecaudo, EstadoProcesoArchivoEnum estadoProcesoArchivo,
			Boolean tieneModificaciones, EstadoSolicitudAporteEnum estadoSolicitudAporte,
			TipoIdentificacionEnum tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
			String periodoAporte, Boolean subsidioPagado) {

		String firmaMetodo = "AportesBusiness.consultarHistoricoEvaluacionAporteDetalle(EstadoAporteEnum, ModalidadRecaudoAporteEnum, "
				+ "EstadoProcesoArchivoEnum, Boolean, EstadoSolicitudAporteEnum, TipoIdentificacionEnum, String, String, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		HistoricoDTO historicoDTO = new HistoricoDTO();

		try {

			// ¿Archivo original ha sido reemplazado?
			historicoDTO.setArchivoRemplazado(Boolean.FALSE);

			if (modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA_MANUAL)
					|| modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA)) {
				historicoDTO.setArchivoRemplazado(tieneModificaciones);
			}

			// ¿Archivo/Recaudo manual ha finalizado?
			if (modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA_MANUAL)
					|| modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA)) {
				historicoDTO.setArchivoFinalizado(Boolean.FALSE);
				if (estadoProcesoArchivo != null) {
					if (estadoProcesoArchivo.equals(EstadoProcesoArchivoEnum.PROCESADO_NOVEDADES)
							|| estadoProcesoArchivo.equals(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO)) {
						historicoDTO.setArchivoFinalizado(Boolean.TRUE);
					}
				}
			} else {
				historicoDTO.setArchivoFinalizado(Boolean.FALSE);

				if (estadoSolicitudAporte != null && estadoSolicitudAporte.equals(EstadoSolicitudAporteEnum.CERRADA)) {
					historicoDTO.setArchivoFinalizado(Boolean.TRUE);
				}
			}

			// ¿Aporte está vigente?
			historicoDTO.setAporteVigente(EstadoAporteEnum.VIGENTE.equals(estadoAporte));

			// ¿Se ha pagado subsidio monetario para el periodo?
			historicoDTO.setSeHaPagadoEnPeriodo(subsidioPagado != null ? subsidioPagado
					: consultasCore.cotizanteConSubsidioPeriodo(tipoIdentificacionCotizante,
							numeroIdentificacionCotizante, periodoAporte));
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return historicoDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarTipoTransaccionNovedadRechazadaCotizante(java.lang.Long)
	 */
	@Override
	public List<String> consultarTipoTransaccionNovedadRechazadaCotizante(Long idAporteDetallado) {
		try {
			logger.debug("Inicio de servicio consultarTipoTransaccionNovedadRechazadaCotizante");
			List<String> lista = consultasCore.consultarTipoTransaccionNovedadRechazadaCotizante(idAporteDetallado);
			logger.debug("Fin de servicio consultarTipoTransaccionNovedadRechazadaCotizante");
			return lista;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarTipoTransaccionNovedadRechazadaCotizante", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarNovedadesRechazadasCotizanteAporte(java.lang.Long,
	 * java.util.List)
	 */
	@Override
	public List<NovedadCotizanteDTO> consultarNovedadesRechazadasCotizanteAporte(Long idRegistroDetallado,
			List<String> tiposTransaccionNovedadesRechazadas) {
		try {
			logger.debug("Inicia servicio consultarNovedadesRechazadasCotizanteAporte");
			List<NovedadCotizanteDTO> listaNovedadesDTO = consultasStaging.consultarNovedadesRechazadasCotizanteAporte(
					idRegistroDetallado, tiposTransaccionNovedadesRechazadas);
			logger.debug("Finaliza servicio consultarNovedadesRechazadasCotizanteAporte");
			return listaNovedadesDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarNovedadesRechazadasCotizanteAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#cambiarEstadoRegistroDetallado(java.util.List)
	 */
	@Override
	public void cambiarEstadoRegistroDetallado(List<Long> idCotizantes) {
		try {
			logger.debug("Inicia servicio cambiarEstadoRegistroDetallado(List<Long> idCotizantes");
			consultasStaging.actualizarEstadoAporteRegistroDetallado(idCotizantes);
			logger.debug("Finaliza servicio cambiarEstadoRegistroDetallado(List<Long> idCotizantes)");
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio cambiarEstadoRegistroDetallado: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarNovedadRetiro(java.
	 * lang.Long, java.lang.Long)
	 */
	@Override
	public List<NovedadCotizanteDTO> consultarNovedadRetiro(Long idRegistroDetallado, Long idPersona) {
		try {
			logger.debug("Inicia servicio consultarNovedadRetiro(Long idRegistroDetallado)");
			return consultasCore.consultarNovedadesRetiro(idRegistroDetallado, idPersona);
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarNovedadRetiro: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarAportantesSinVencimiento()
	 */
	@Override
	public List<AportanteDiaVencimientoDTO> consultarAportantesSinVencimiento() {
		String firmaServicio = "AportesBusiness.consultarAportantesSinVencimiento()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AportanteDiaVencimientoDTO> result = consultasCore.consultarAportantesSinDiaVencimiento();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizarDiaHabilVencimientoAporte(java.util.List)
	 */
	@Override
	public void actualizarDiaHabilVencimientoAporte(List<AportanteDiaVencimientoDTO> aportantesPorActualizar) {
		String firmaServicio = "AportesBusiness.actualizarDiaHabilVencimientoAporte(List<ListadoAportantesDiaVencimientoDTO>)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		Map<Short, List<Long>> empleadores = new HashMap<>();
		Map<Short, List<Long>> indPen = new HashMap<>();

		// se separan los casos que se van a actualizar en Empleador de los que
		// se van a actualizar en RolAfiliado
		for (AportanteDiaVencimientoDTO aportante : aportantesPorActualizar) {
			if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportante.getTipoSolicitanteAporte())
					&& !empleadores.containsKey(aportante.getDiaHabilVencimiento())) {
				List<Long> idsEmpleadores = new ArrayList<>();
				idsEmpleadores.add(aportante.getIdRegistro());
				empleadores.put(aportante.getDiaHabilVencimiento(), idsEmpleadores);
			} else if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportante.getTipoSolicitanteAporte())
					&& empleadores.containsKey(aportante.getDiaHabilVencimiento())) {

				empleadores.get(aportante.getDiaHabilVencimiento()).add(aportante.getIdRegistro());
			} else if (!indPen.containsKey(aportante.getDiaHabilVencimiento())) {
				List<Long> idsIndPen = new ArrayList<>();
				idsIndPen.add(aportante.getIdRegistro());
				indPen.put(aportante.getDiaHabilVencimiento(), idsIndPen);
			} else {
				indPen.get(aportante.getDiaHabilVencimiento()).add(aportante.getIdRegistro());
			}
		}

		// se actualizan los registros agrupados por su día hábil de vencimiento
		empleadores.forEach((k, v) -> consultasCore.actualizarDiaVencimientoAportesEmpleadores(k, v));
		indPen.forEach((k, v) -> consultasCore.actualizarDiaVencimientoAportesIndPen(k, v));

		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarAporteGeneralEmpleador(java.lang.Long,
	 * com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoAporteEnum)
	 */
	@Override
	public List<AporteGeneralModeloDTO> consultarAporteGeneralEmpleador(Long idEmpleador,
			EstadoRegistroAporteEnum estadoRegistroAporte, EstadoAporteEnum estadoAporteAportante) {
		try {
			logger.debug("Inicia método consultarAporteGeneralEmpleador");
			List<AporteGeneral> listaAporteGeneral = consultasCore.consultarAporteGeneralEmpleador(idEmpleador,
					estadoRegistroAporte, estadoAporteAportante);
			List<AporteGeneralModeloDTO> listaAporteGeneralDTO = new ArrayList<AporteGeneralModeloDTO>();

			for (AporteGeneral aporteGeneral : listaAporteGeneral) {
				AporteGeneralModeloDTO aporteGeneralDTO = new AporteGeneralModeloDTO();
				aporteGeneralDTO.convertToDTO(aporteGeneral);
				listaAporteGeneralDTO.add(aporteGeneralDTO);
			}

			logger.debug("Finaliza método consultarAporteGeneralEmpleador");
			return listaAporteGeneralDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteGeneralEmpleador", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarAporteDetalladoPorIdsGeneral(java.util.List,
	 * com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoAporteEnum)
	 */
	@Override
	public List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneral(List<Long> listaIdAporteGeneral,
			EstadoRegistroAporteEnum estadoRegistroAporte, EstadoAporteEnum estadoAporteAportante) {
		try {
			logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneral");
			List<AporteDetallado> listaAporteDetallado = consultasCore.consultarAporteDetalladoPorIdsGeneral(
					listaIdAporteGeneral, estadoRegistroAporte, estadoAporteAportante);
			List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = new ArrayList<AporteDetalladoModeloDTO>();

			for (AporteDetallado aporteDetallado : listaAporteDetallado) {
				AporteDetalladoModeloDTO aporteDetalladoDTO = new AporteDetalladoModeloDTO();
				aporteDetalladoDTO.convertToDTO(aporteDetallado);
				listaAporteDetalladoDTO.add(aporteDetalladoDTO);
			}

			logger.debug("Finaliza método consultarAporteDetalladoPorIdsGeneral");
			return listaAporteDetalladoDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteDetalladoPorIdsGeneral", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarAporteGeneralPersona
	 * (java.lang.Long,
	 * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoAporteEnum,
	 * com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum)
	 */
	@Override
	public List<AporteGeneralModeloDTO> consultarAporteGeneralPersona(Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, EstadoAporteEnum estadoAporte,
			EstadoRegistroAporteEnum estadoRegistroAporte) {
		try {
			logger.debug("Inicia método consultarAporteGeneralPersona");
			List<AporteGeneral> listaAporteGeneral = consultasCore.consultarAporteGeneralPersona(idPersona,
					tipoSolicitante, estadoAporte, estadoRegistroAporte);
			List<AporteGeneralModeloDTO> listaAporteGeneralDTO = new ArrayList<AporteGeneralModeloDTO>();

			for (AporteGeneral aporteGeneral : listaAporteGeneral) {
				AporteGeneralModeloDTO aporteGeneralDTO = new AporteGeneralModeloDTO();
				aporteGeneralDTO.convertToDTO(aporteGeneral);
				listaAporteGeneralDTO.add(aporteGeneralDTO);
			}

			logger.debug("Finaliza método consultarAporteGeneralPersona");
			return listaAporteGeneralDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteGeneralPersona", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarAporteDetalladoPorIdsGeneralPersona(java.util.List,
	 *      java.lang.Long,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum,
	 *      com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum,
	 *      com.asopagos.enumeraciones.aportes.EstadoAporteEnum)
	 */
	@Override
	public List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneralPersona(List<Long> listaIdAporteGeneral,
			Long idPersona, TipoAfiliadoEnum tipoAfiliado, EstadoRegistroAporteEnum estadoRegistroAporte,
			EstadoAporteEnum estadoAporteAportante) {
		try {
			logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneralPersona");
			List<AporteDetallado> listaAporteDetallado = consultasCore.consultarAporteDetalladoPorIdsGeneralPersona(
					listaIdAporteGeneral, idPersona, tipoAfiliado, estadoRegistroAporte, estadoAporteAportante);
			List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = new ArrayList<AporteDetalladoModeloDTO>();

			for (AporteDetallado aporteDetallado : listaAporteDetallado) {
				AporteDetalladoModeloDTO aporteDetalladoDTO = new AporteDetalladoModeloDTO();
				aporteDetalladoDTO.convertToDTO(aporteDetallado);
				listaAporteDetalladoDTO.add(aporteDetalladoDTO);
			}

			logger.debug("Finaliza método consultarAporteDetalladoPorIdsGeneralPersona");
			return listaAporteDetalladoDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteDetalladoPorIdsGeneralPersona", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	@Override
	public List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdGeneral(Long idAporteGeneral) {
		try {
			logger.debug("Inicia método consultarAporteDetalladoPorIdGeneral");
			List<AporteDetallado> listaAporteDetallado = consultasCore
					.consultarAporteDetalladoPorIdGeneral(idAporteGeneral);
			List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = new ArrayList<AporteDetalladoModeloDTO>();

			for (AporteDetallado aporteDetallado : listaAporteDetallado) {
				AporteDetalladoModeloDTO aporteDetalladoDTO = new AporteDetalladoModeloDTO();
				aporteDetalladoDTO.convertToDTO(aporteDetallado);
				listaAporteDetalladoDTO.add(aporteDetalladoDTO);
			}

			logger.debug("Finaliza método consultarAporteDetalladoPorIdGeneral");
			return listaAporteDetalladoDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el método consultarAporteDetalladoPorIdGeneral", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#cambiarEstadoRegistroGeneral(java.lang.Long)
	 */
	@Override
	public void cambiarEstadoRegistroGeneral(List<Long> idRegistroGeneral) {
		try {
			logger.debug("Inicia servicio cambiarEstadoRegistroGeneral(Long idRegistroGeneral)");
			consultasStaging.actualizarEstadoAporteRegistroGeneral(idRegistroGeneral);
			logger.debug("Finaliza servicio cambiarEstadoRegistroGeneral(Long idRegistroGeneral)");
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio cambiarEstadoRegistroGeneral: ", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitanteCorreccionCuentaAportes(java.util.List)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public List<SolicitanteDTO> consultarSolicitanteCorreccionCuentaAportes(List<PersonaDTO> personas) {
		logger.debug("Inicio del método consultarSolicitanteCorreccionCuentaAportes(List<PersonaDTO> personas");
		try {
			List<Long> idsPersona = new ArrayList<>();
			for (PersonaDTO personaDTO : personas) {
				idsPersona.add(personaDTO.getIdPersona());
			}

			List<SolicitanteDTO> solicitantes = consultasCore.consultarSolicitanteAporteGeneral(idsPersona);
			return solicitantes;
		} catch (NoResultException e) {
			logger.debug(
					"Finaliza método consultarSolicitanteCorreccionCuentaAportes(List<PersonaDTO> personas):No se encuentran registros con los parametros ingresados");
			return null;
		} catch (Exception e) {
			logger.debug(
					"Finaliza método consultarSolicitanteCorreccionCuentaAportes(List<PersonaDTO> personas):Ocurrio un error técnico inesperado");
			logger.error(
					"Finaliza método consultarSolicitanteCorreccionCuentaAportes(List<PersonaDTO> personas):Ocurrio un error técnico inesperado",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitanteCorreccionCuentaAportesIds(java.util.List)
	 */
	@Override
	public List<SolicitanteDTO> consultarSolicitanteCorreccionCuentaAportesIds(List<Long> idsPersona) {
		String firmaServicio = "AportesBusiness.consultarSolicitanteCorreccionCuentaAportesIds(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<SolicitanteDTO> solicitantes = null;

		try {
			solicitantes = consultasCore.consultarSolicitanteAporteGeneral(idsPersona);
		} catch (NoResultException e) {
			solicitantes = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return solicitantes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#consultarHistoricoCierre(java
	 * .lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public List<SolicitudCierreRecaudoModeloDTO> consultarHistoricoCierre(Long fechaInicio, Long fechaFin,
			String numeroRadicacion, TipoCierreEnum tipoCierre) {
		try {
			logger.debug("Inicio de método consultarHistoricoCierre");
			return consultasCore.consultarHistoricoCierre(fechaInicio, fechaFin, numeroRadicacion, tipoCierre);
		} catch (Exception e) {
			logger.error("Ocurrio un error técnico inesperado en consultarHistoricoCierre", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#validarGeneracionCierre(java.
	 * lang.Long, java.lang.Long)
	 */
	@Override
	public Boolean validarGeneracionCierre(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio de método validarGeneracionCierre");
			logger.info("Inicio de método validarGeneracionCierre - AportesBusiness ");

			return consultasCore.validarGeneracionCierre(fechaInicio, fechaFin);
		} catch (Exception e) {
			logger.error("Ocurrio un error técnico inesperado en validarGeneracionCierre", e);
			logger.info("Ocurrio un error técnico inesperado en validarGeneracionCierre", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.service.AportesService#guardarSolicitudCierreRecaudo
	 * (com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO)
	 */
	@Override
	public Long guardarSolicitudCierreRecaudo(SolicitudCierreRecaudoModeloDTO solicitudCierreDTO) {
		try {
			logger.debug("Inicio de método guardarSolicitudCierreRecaudo");
			return consultasCore.guardarSolicitudCierreRecaudo(solicitudCierreDTO);
		} catch (Exception e) {
			logger.error("Ocurrio un error técnico inesperado en guardarSolicitudCierreRecaudo", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * consultarSolicitudCierreRecaudo(java.lang.String)
	 */
	@Override
	public SolicitudCierreRecaudoModeloDTO consultarSolicitudCierreRecaudo(String numeroRadicacion) {
		try {
			logger.debug("Inicio de método consultarSolicitudCierreRecaudo");
			return consultasCore.consultarSolicitudCierreRecaudo(numeroRadicacion);
		} catch (Exception e) {
			logger.error(
					"Ocurrio un error técnico inesperado en consultarHistoconsultarSolicitudCierreRecaudoricoCierre",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#
	 * actualizarEstadoSolicitudCierre(com.asopagos.enumeraciones.aportes.
	 * EstadoSolicitudCierreRecaudoEnum, java.lang.String)
	 */
	@Override
	public void actualizarEstadoSolicitudCierre(EstadoSolicitudCierreRecaudoEnum estado, String numeroRadicacion) {
		try {
			logger.debug("Inicio de método actualizarEstadoSolicitudCierre");
			consultasCore.actualizarEstadoSolicitudCierre(estado, numeroRadicacion);
			logger.debug("Fin de método actualizarEstadoSolicitudCierre");
		} catch (Exception e) {
			logger.error("Ocurrio un error técnico inesperado en actualizarEstadoSolicitudCierre", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * 
	 * @param periodo
	 * @param idPersona
	 *            Identificador de la persona
	 * @return
	 */
	private MarcaPeriodoEnum consultarPeriodoAporte(String periodo, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
			Long idPersona) {
		try {
			logger.debug("Inicio de método consultarPeriodoAporte(String periodo)");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String periodoActual = dateFormat.format(new Date());

			if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)
					|| TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, -1);
				periodoActual = dateFormat.format(calendar.getTime());
			} else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)) {

				PeriodoPagoPlanillaEnum periodoPagoAfiliacion = consultasCore.consultarPeriodoPagoAfiliacion(idPersona,
						TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);

				if (periodoPagoAfiliacion != null
						&& periodoPagoAfiliacion.equals(PeriodoPagoPlanillaEnum.MES_VENCIDO)) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.MONTH, -1);
					periodoActual = dateFormat.format(calendar.getTime());
				} else if (periodoPagoAfiliacion != null
						&& periodoPagoAfiliacion.equals(PeriodoPagoPlanillaEnum.ANTICIPADO)) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.MONTH, 1);
					periodoActual = dateFormat.format(calendar.getTime());
				}
			}

			int comparacion = periodoActual.compareTo(periodo);
			logger.debug("Fin de método consultarPeriodoAporte(String periodo)");
			if (comparacion > 0) {
				return MarcaPeriodoEnum.PERIODO_RETROACTIVO;
			} else if (comparacion == 0) {
				return MarcaPeriodoEnum.PERIODO_REGULAR;
			} else {
				return MarcaPeriodoEnum.PERIODO_FUTURO;
			}

		} catch (Exception e) {
			logger.error("Ocurrio un error técnico inesperado en consultarPeriodoAporte", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#validarProcesamientoNovedadFutura(java.lang.Long)
	 */
	@Override
	public void validarProcesamientoNovedadFutura(Long fechaValidacion) {
		String firmaServicio = "AportesBusiness.validarProcesamientoNovedadFutura(Long)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.ejecutarVerificacionNovedadesFuturas(new Date(fechaValidacion));

		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRegistrosCierreRecaudo(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public List<ResumenCierreRecaudoDTO> consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin) {


		String firmaServicio = "APORTES -> AportesBusiness.consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		logger.info("inicio proceso de consultarRegistrosCierreRecaudo");
		logger.info("firmaServicio --> " +firmaServicio);

		List<ResumenCierreRecaudoDTO> cierreRecaudosPeriodos = new ArrayList<>();

		try {

			List<Object[]> aportesRegistrados = consultasCore.consultarRegistrosResumenCierreAportes(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(1, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> correccionesALaAltaRegistrados = consultasCore.consultarRegistrosResumenCierreN(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(11, new Date(fechaInicio), new Date(fechaFin)));
				

			List<Object[]> devolucionesRegistrados = consultasCore.consultarRegistrosResumenCierre(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(2, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> registradosLegalizadosReg = consultasCore
					.consultarRegistrosResumenCierreRegistrados(FuncionesUtilitarias.construirCriteriosConsultaCierre(3,
							new Date(fechaInicio), new Date(fechaFin)));

			logger.debug(" otrosIngresosRegistrados = consultasCore.consultarRegistrosResumenCierre Proceso ");				
            List<Object[]> otrosIngresosRegistrados = consultasCore.consultarRegistrosResumenCierre(
                    FuncionesUtilitarias.construirCriteriosConsultaCierre(4, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> correccionesRegistrados = consultasCore.consultarRegistrosResumenCierre(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(5, new Date(fechaInicio), new Date(fechaFin)));


			List<Object[]> aportesRelacionados = consultasCore.consultarRegistrosResumenCierreAportes(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(6, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> correccionesALaAltaRelacionados = consultasCore.consultarRegistrosResumenCierreN(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(12, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> devolucionesRelacionados = consultasCore.consultarRegistrosResumenCierre(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(7, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> registradosLegalizadosRel = consultasCore
					.consultarRegistrosResumenCierreRegistrados(FuncionesUtilitarias.construirCriteriosConsultaCierre(8,
							new Date(fechaInicio), new Date(fechaFin)));

			logger.debug(" otrosIngresosRelacionados = consultasCore.consultarRegistrosResumenCierre Proceso ");			
			List<Object[]> otrosIngresosRelacionados = consultasCore.consultarRegistrosResumenCierre(
                    FuncionesUtilitarias.construirCriteriosConsultaCierre(9, new Date(fechaInicio), new Date(fechaFin)));

					
			List<Object[]> correccionesRelacionados = consultasCore.consultarRegistrosResumenCierre(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(10, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> aportesExtemporaneosRegistrados = consultasCore.consultarRegistrosResumenCierreAportesExtemporaneos(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(1, new Date(fechaInicio), new Date(fechaFin)));

			List<Object[]> aportesExtemporaneosRelacionados = consultasCore.consultarRegistrosResumenCierreAportesExtemporaneos(FuncionesUtilitarias
					.construirCriteriosConsultaCierre(6, new Date(fechaInicio), new Date(fechaFin)));



			List<MarcaPeriodoEnum> marcas = new ArrayList<>();
			marcas.add(MarcaPeriodoEnum.PERIODO_REGULAR);
			marcas.add(MarcaPeriodoEnum.PERIODO_RETROACTIVO);
			marcas.add(MarcaPeriodoEnum.PERIODO_FUTURO);

			int point = 0; 
			for (MarcaPeriodoEnum marca : marcas) {
				RegistroAporteDTO aporte = new RegistroAporteDTO();
				RegistroAporteDTO planillasN = new RegistroAporteDTO();
				RegistroAporteDTO devolucion = new RegistroAporteDTO();
				RegistroAporteDTO correccion = new RegistroAporteDTO();
				RegistroAporteDTO legalizado = new RegistroAporteDTO();
				RegistroAporteDTO otrosIngresos = new RegistroAporteDTO();
				RegistroAporteDTO extemporaneo = new RegistroAporteDTO();
				ResumenCierreRecaudoDTO registroCierre = new ResumenCierreRecaudoDTO();
				List<RegistroAporteDTO> registros = new ArrayList<>();

				logger.info(" armarRegistroAporte - aportesRegistrados  ");
				aporte = armarRegistroAporte(aportesRegistrados, aporte, Boolean.TRUE, marca);
				aporte = armarRegistroAporte(aportesRelacionados, aporte, Boolean.FALSE, marca);
				aporte.setTipoRegistro(TipoRegistroEnum.APORTES);

				logger.info(" armarRegistroAporte - correccionALaAltaRegistrados  ");
				planillasN = armarRegistroAporte(correccionesALaAltaRegistrados, planillasN, Boolean.TRUE, marca);
				planillasN = armarRegistroAporte(correccionesALaAltaRelacionados, planillasN, Boolean.FALSE, marca);
				planillasN.setTipoRegistro(TipoRegistroEnum.PLANILLAS_N);

				logger.info(" armarRegistroAporte - devolucionesRegistrados  ");
				devolucion = armarRegistroAporte(devolucionesRegistrados, devolucion, Boolean.TRUE, marca);
				devolucion = armarRegistroAporte(devolucionesRelacionados, devolucion, Boolean.FALSE, marca);
				devolucion.setTipoRegistro(TipoRegistroEnum.DEVOLUCIONES);

				logger.info(" armarRegistroAporte - correccionesRegistrados  ");
				correccion = armarRegistroAporte(correccionesRegistrados, correccion, Boolean.TRUE, marca);
				correccion = armarRegistroAporte(correccionesRelacionados, correccion, Boolean.FALSE, marca);
				correccion.setTipoRegistro(TipoRegistroEnum.CORRECCIONES);

				logger.info(" armarRegistroAporte - registradosLegalizadosReg  ");
				legalizado = armarRegistroAporte(registradosLegalizadosReg, legalizado, Boolean.TRUE, marca);
				legalizado = armarRegistroAporte(registradosLegalizadosRel, legalizado, Boolean.FALSE, marca);
				legalizado.setTipoRegistro(TipoRegistroEnum.REGISTRADOS);

				logger.info(" armarRegistroAporte - otrosIngresos  ");

				logger.info(" armarRegistroAporte - otrosIngresosRegistrados  ");
				otrosIngresos = armarRegistroAporte(otrosIngresosRegistrados, otrosIngresos, Boolean.TRUE, marca);
				logger.info(" armarRegistroAporte - otrosIngresosRelacionados  ");
				otrosIngresos = armarRegistroAporte(otrosIngresosRelacionados, otrosIngresos, Boolean.FALSE, marca);
				otrosIngresos.setTipoRegistro(TipoRegistroEnum.OTROS_INGRESOS);

				logger.info(" armarRegistroAporte - registradosLegalizadosReg  ");
				extemporaneo = armarRegistroAporte(aportesExtemporaneosRegistrados, extemporaneo, Boolean.TRUE, marca);
				extemporaneo = armarRegistroAporte(aportesExtemporaneosRelacionados, extemporaneo, Boolean.FALSE, marca);
				extemporaneo.setTipoRegistro(TipoRegistroEnum.APORTES_EXTEMPORANEOS);

				registros.add(aporte);
				logger.info("aporte " +aporte);
				registros.add(planillasN);
				logger.info("planillasN " +planillasN);
				registros.add(devolucion);
				logger.info("devolucion " +devolucion);
				registros.add(correccion);
				logger.info("correccion " +correccion);
				registros.add(legalizado);
				logger.info("legalizado " +legalizado);
				registros.add(otrosIngresos);
				logger.info("otrosIngresos " +otrosIngresos);
				registros.add(extemporaneo);
				logger.info("extemporaneo " +extemporaneo);


				registroCierre.setRegistros(registros);
				logger.info("registros " +registros.size());
				registroCierre.setPeriodo(marca);
				logger.info("marca " +marca);

				cierreRecaudosPeriodos.add(registroCierre);
 
				logger.info(" ciclo contador MarcaPeriodoEnum  "+point);
				point++;
			}
		} catch (Exception e) {
			logger.info("Caught IOException: -> " + e.getMessage());
			logger.error("Ocurrio un error técnico inesperado en " + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
        logger.info("APORTES -> fin proceso de consultarRegistrosCierreRecaudo");
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return cierreRecaudosPeriodos;
	}

	private RegistroAporteDTO armarRegistroAporte(List<Object[]> aportes, RegistroAporteDTO registroAporte,
			Boolean registrado, MarcaPeriodoEnum marca) {

		String firmaServicio = "AportesBusiness.armarRegistroAporte(List<Object[]> aportes, RegistroAporteDTO registroAporte)";
		
		logger.debug("SERVICIO -> "+ConstantesComunes.INICIO_LOGGER + firmaServicio);
		logger.info("Inicio proceso ->  armarRegistroAporte");
		try {
			// Monto [0], Interes[1], TipoAfiliado[2], MarcaPeriodo[3]
			BigDecimal monto = BigDecimal.ZERO;
			BigDecimal interes = BigDecimal.ZERO;

			int count = 1;
			int count_if = 1;
			for (Object[] aporte : aportes) {

				logger.debug(" Object ->  "+aporte.toString()  );

			
				logger.info(" ** marca.equals -> - "+aporte[3].toString() );

				/*if( Objects.nonNull( aporte[2].toString() )  ) {  System.out.println(" Aporte Flag 3 ");   }
				if( Objects.nonNull( aporte[1].toString() )  ) {  System.out.println(" Aporte Flag 3 ");   }
				if( Objects.nonNull( aporte[0].toString() )  ) {  System.out.println(" Aporte Flag 3 ");   }*/
				// logger.debug(" aporte[2] ->"+aporte[2].toString() );
				// logger.debug(" aporte[1] ->"+aporte[1].toString() );
				// logger.debug(" aporte[0] ->"+aporte[0].toString() );


				if (
					aporte[3] != null && 
					marca.equals(MarcaPeriodoEnum.valueOf(aporte[3].toString())) ) {

						

					monto = aporte[0] != null ? new BigDecimal(aporte[0].toString()) : BigDecimal.ZERO;
					interes = aporte[1] != null ? new BigDecimal(aporte[1].toString()) : BigDecimal.ZERO;

                    logger.info(count_if+"Monto e Interes"+monto+" "+interes+ " "+ aporte[3].toString());
					logger.info(aporte[2]);

					if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name().equals(aporte[2].toString())) {

                        logger.info(count_if+" - "+TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name()+ "TRABAJADOR_DEPENDIENTE " );

						if (registrado) {
							registroAporte.setMontoRegistradoDependiente(monto);
							registroAporte.setInteresRegistradoDependiente(interes);
							registroAporte.setTotalRegistradoDependiente(monto.add(interes));
						} else {
							registroAporte.setMontoRelacionadoDependiente(monto);
							registroAporte.setInteresRelacionadoDependiente(interes);
							registroAporte.setTotalRelacionadoDependiente(monto.add(interes));
						}
					} else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name().equals(aporte[2].toString()) ) {

						 logger.info(count_if+" - "+TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name()+ "TRABAJADOR_INDEPENDIENTE ");
						if (registrado) {
							registroAporte.setMontoRegistradoIndependiente(registroAporte.getMontoRegistradoIndependiente().add(monto));
							registroAporte.setInteresRegistradoIndependiente(registroAporte.getInteresRegistradoIndependiente().add(interes));
							registroAporte.setTotalRegistradoIndependiente(registroAporte.getTotalRegistradoIndependiente().add(monto.add(interes)));
							if(aporte[4] != null && aporte[4].toString().equals("0.02000")){
								registroAporte.setMontoRegistradoIndependiente_02(registroAporte.getMontoRegistradoIndependiente_02().add(monto));
								registroAporte.setInteresRegistradoIndependiente_02(registroAporte.getInteresRegistradoIndependiente_02().add(interes));
								registroAporte.setTotalRegistradoIndependiente_02(registroAporte.getTotalRegistradoIndependiente_02().add(monto.add(interes)));
							}
							else if (aporte[4] != null && aporte[4].toString().equals("0.00600")){
								registroAporte.setMontoRegistradoIndependiente_06(registroAporte.getMontoRegistradoIndependiente_06().add(monto));
								registroAporte.setInteresRegistradoIndependiente_06(registroAporte.getInteresRegistradoIndependiente_06().add(interes));
								registroAporte.setTotalRegistradoIndependiente_06(registroAporte.getTotalRegistradoIndependiente_06().add(monto.add(interes)));
							}
						} else {
							registroAporte.setMontoRelacionadoIndependiente(registroAporte.getMontoRelacionadoIndependiente().add(monto));
							registroAporte.setInteresRelacionadoIndependiente(registroAporte.getInteresRelacionadoIndependiente().add(interes));
							registroAporte.setTotalRelacionadoIndependiente(registroAporte.getTotalRelacionadoIndependiente().add(monto.add(interes)));
							if(aporte[4] != null && aporte[4].toString().equals("0.02000")){
								logger.info(" seteo de 0.02000 relacionado");
								registroAporte.setMontoRelacionadoIndependiente_02(registroAporte.getMontoRelacionadoIndependiente_02().add(monto));
								registroAporte.setInteresRelacionadoIndependiente_02(registroAporte.getInteresRelacionadoIndependiente_02().add(interes));
								registroAporte.setTotalRelacionadoIndependiente_02(registroAporte.getTotalRelacionadoIndependiente_02().add(monto.add(interes)));
							}
							else if(aporte[4] != null && aporte[4].toString().equals("0.00600")){
								logger.info(" seteo de 0.00600 relacionado");
								registroAporte.setMontoRelacionadoIndependiente_06(registroAporte.getMontoRelacionadoIndependiente_06().add(monto));
								registroAporte.setInteresRelacionadoIndependiente_06(registroAporte.getInteresRelacionadoIndependiente_06().add(interes));
								registroAporte.setTotalRelacionadoIndependiente_06(registroAporte.getTotalRelacionadoIndependiente_06().add(monto.add(interes)));
							}
						}
					} else if (TipoAfiliadoEnum.PENSIONADO.name().equals(aporte[2].toString())) {
						if (registrado) {
							registroAporte.setMontoRegistradoPensionado(registroAporte.getMontoRegistradoPensionado().add(monto));
							registroAporte.setInteresRegistradoPensionado(registroAporte.getInteresRegistradoPensionado().add(interes));
							registroAporte.setTotalRegistradoPensionado(registroAporte.getTotalRegistradoPensionado().add(monto.add(interes)));
							if(aporte[4] != null && aporte[4].toString().equals("0.02000")){
								registroAporte.setMontoRegistradoPensionado_02(registroAporte.getMontoRegistradoPensionado_02().add(monto));
								registroAporte.setInteresRegistradoPensionado_02(registroAporte.getInteresRegistradoPensionado_02().add(interes));
								registroAporte.setTotalRegistradoPensionado_02(registroAporte.getTotalRegistradoPensionado_02().add(monto.add(interes)));
							}
							else if (aporte[4] != null && aporte[4].toString().equals("0.00600")){
								registroAporte.setMontoRegistradoPensionado_06(registroAporte.getMontoRegistradoPensionado_06().add(monto));
								registroAporte.setInteresRegistradoPensionado_06(registroAporte.getInteresRegistradoPensionado_06().add(interes));
								registroAporte.setTotalRegistradoPensionado_06(registroAporte.getTotalRegistradoPensionado_06().add(monto.add(interes)));
							}
						} else {
							registroAporte.setMontoRelacionadoPensionado(monto);
							registroAporte.setInteresRelacionadoPensionado(interes);
							registroAporte.setTotalRelacionadoPensionado(monto.add(interes));
							if(aporte[4] != null && aporte[4].toString().equals("0.02000")){
								logger.info(" seteo de 0.02000 relacionado");
								registroAporte.setMontoRelacionadoPensionado_02(registroAporte.getMontoRelacionadoPensionado_02().add(monto));
								registroAporte.setInteresRelacionadoPensionado_02(registroAporte.getInteresRelacionadoPensionado_02().add(interes));
								registroAporte.setTotalRelacionadoPensionado_02(registroAporte.getTotalRelacionadoPensionado_02().add(monto.add(interes)));
							}
							else if(aporte[4] != null && aporte[4].toString().equals("0.00600")){
								logger.info(" seteo de 0.00600 relacionado");
								registroAporte.setMontoRelacionadoPensionado_06(registroAporte.getMontoRelacionadoPensionado_06().add(monto));
								registroAporte.setInteresRelacionadoPensionado_06(registroAporte.getInteresRelacionadoPensionado_06().add(interes));
								registroAporte.setTotalRelacionadoPensionado_06(registroAporte.getTotalRelacionadoPensionado_06().add(monto.add(interes)));
							}
						}
					}
					count_if++;
				}
				count++;
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
			logger.info("fin proceso  armarRegistroAporte");
			return registroAporte;
		} catch (Exception e) {
			logger.error("Ocurrio un error técnico inesperado en " + firmaServicio, e);
			logger.info("Ocurrio un error técnico inesperado en " + e.getMessage());
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRecaudoDevolucionCorreccionVista360PersonaPrincipal(com.asopagos.aportes.dto.ConsultarRecaudoDTO,
	 *      com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360PersonaPrincipal(
			ConsultarRecaudoDTO consultaRecaudo, TipoMovimientoRecaudoAporteEnum tipo) {
		List<CuentaAporteDTO> resultadoAportes = consultarRecaudoDevolucionCorreccionVista360Persona(consultaRecaudo, tipo,
				consultaRecaudo.getListaIdsAporteGeneral());
		List<CuentaAporteDTO> resTotal = new ArrayList<>();
		int fin = 0;
		for (int inicio = 0; inicio < resultadoAportes.size(); inicio+= 1000) {
			fin = inicio + 1000;
			if (fin > resultadoAportes.size()) {
				fin = resultadoAportes.size();
			}
			resTotal.addAll(ajusteTieneModificaciones(resultadoAportes.subList(inicio,fin)));
		}
		return resTotal;
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360PersonaPrincipalVistaBoton(
			ConsultarRecaudoDTO consultaRecaudo, TipoMovimientoRecaudoAporteEnum tipo, UriInfo uri, HttpServletResponse response) {
		List<CuentaAporteDTO> resultadoAportes = consultarRecaudoDevolucionCorreccionVista360PersonaVistaBoton(consultaRecaudo, tipo,
				consultaRecaudo.getListaIdsAporteGeneral(), uri, response);
		List<CuentaAporteDTO> resTotal = new ArrayList<>();
		int fin = 0;
		for (int inicio = 0; inicio < resultadoAportes.size(); inicio+= 1000) {
			fin = inicio + 1000;
			if (fin > resultadoAportes.size()) {
				fin = resultadoAportes.size();
			}
			resTotal.addAll(ajusteTieneModificaciones(resultadoAportes.subList(inicio,fin)));
		}
		return resTotal;
	}
	private List<CuentaAporteDTO> ajusteTieneModificaciones(List<CuentaAporteDTO> cuentaAportes) {
		//Mapa con los ajustes de los detalles y si tiene modificaciones
		Map<Long, Boolean> tieneModificaciones = new HashMap<Long, Boolean>();

		tieneModificaciones = consultasCore.consultaModificaciones(cuentaAportes);
		for (CuentaAporteDTO cuenta: cuentaAportes) {
			cuenta.setTieneModificaciones(tieneModificaciones.get(cuenta.getIdAporteDetallado()));
		}
		return cuentaAportes;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaAporteDTO consultarRecaudoOriginalDevolucionCorreccionV360Persona(Long idAporteDetallado) {
		String firmaServicio = "consultarRecaudoOriginalCorreccionV360Persona(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		CuentaAporteDTO aporteOriginal = consultasCore.consultarAporteDetalladoOriginal(idAporteDetallado);

		if (!TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(aporteOriginal.getTipoMovimientoRecaudo())) {
			aporteOriginal = consultasStaging.completarDatosAporteOriginal(aporteOriginal);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return aporteOriginal;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRecaudoVista360(com.asopagos.aportes.dto.ConsultarRecaudoDTO)
	 */
	@SuppressWarnings("static-access")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360Persona(
			ConsultarRecaudoDTO consultaRecaudo, TipoMovimientoRecaudoAporteEnum tipo, List<Long> idAporteGeneral) {
		String firmaServicio = "AportesBusiness.consultarRecaudoDevolucionCorreccionVista360Persona(ConsultarRecaudoDTO,TipoMovimientoRecaudoAporteEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);	

		List<CuentaAporteDTO> cuentaAporteGeneralDTOs = new ArrayList<>();
		if (consultaRecaudo.getNumeroIdentificacion() == null && consultaRecaudo.getTipoIdentificacion() == null
				&& consultaRecaudo.getTipoSolicitante() == null) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + " : Parámetros Incompletos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	

		List<CuentaAporteDTO> cuentaAporteRecaudoDTOs = new ArrayList<>();
		List<CuentaAporteDTO> cuentaAporteDevolucionDTOs = new ArrayList<>();
		List<CuentaAporteDTO> cuentaAporteCorrecionDTOs = new ArrayList<>();

	

		/* Se consulta la persona */
		PersonaDTO persona = consultasCore.consultarPersonaTipoNumeroIdentificacion(
				consultaRecaudo.getTipoIdentificacion(), consultaRecaudo.getNumeroIdentificacion());

		CriteriaBuilder cb = consultasCore.obtenerCriteriaBuilder();
		CriteriaQuery<AporteGeneral> c = cb.createQuery(AporteGeneral.class);
	

		Root<AporteGeneral> aporteGeneral = c.from(AporteGeneral.class);
		c.select(aporteGeneral);

	

		List<AporteGeneral> aportesGenerales = null;
		List<Predicate> predicates = new ArrayList<>();



		if (consultaRecaudo.getTipoSolicitante() != null) {
			predicates.add(cb.equal(aporteGeneral.get("tipoSolicitante"), consultaRecaudo.getTipoSolicitante()));
		}
	

		if (consultaRecaudo.getMetodoRecaudo() != null) {
			predicates.add(cb.equal(aporteGeneral.get("modalidadRecaudoAporte"), consultaRecaudo.getMetodoRecaudo()));
		}
		

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		if (consultaRecaudo.getPeriodoRecaudo() != null) {
			String fechaPagoString = null;
			fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
			predicates.add(cb.equal(aporteGeneral.get("periodoAporte"), fechaPagoString));
		}


		Long millisDia = (long) ((24 * 3600 * 1000) - 1); // para la
															// fecha fin

		if (consultaRecaudo.getFechaInicio() != null && consultaRecaudo.getFechaFin() != null) {
			Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
			Date fechaFinDate = new Date(consultaRecaudo.getFechaFin() + millisDia);
			predicates.add(cb.between(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate, fechaFinDate));
		} else if (consultaRecaudo.getFechaInicio() != null) {
			Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
			predicates.add(cb.greaterThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate));
		} else if (consultaRecaudo.getFechaFin() != null) {
			Date fechaFinDate = new Date(consultaRecaudo.getFechaFin() + millisDia);
			predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaFinDate));
		}

		if (!idAporteGeneral.isEmpty()) {
			predicates.add(aporteGeneral.get("id").in(idAporteGeneral));
		}


		c.select(aporteGeneral).where(predicates.toArray(new Predicate[] {}));
		aportesGenerales = consultasCore.obtenerListaAportes(c);


		List<Long> idsAporte = new ArrayList<>();
		for (AporteGeneral aporte : aportesGenerales) {
			idsAporte.add(aporte.getId());
		}
	

		if (persona != null) {
			if (persona.getRazonSocial() == null || persona.getRazonSocial().isEmpty()) {
				StringBuilder nombreAportante = new StringBuilder();
				nombreAportante.append(persona.getPrimerNombre() + " ");
				nombreAportante.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre() + " " : "");
				nombreAportante.append(persona.getPrimerApellido() + " ");
				nombreAportante.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
				consultaRecaudo.setNombreCompleto(nombreAportante.toString());

			} else {
				consultaRecaudo.setNombreCompleto(persona.getRazonSocial());
			}
		}


		List<AporteGeneralModeloDTO> aportesGeneralesDTO = new ArrayList<>();
		if (!idsAporte.isEmpty()) {
			aportesGeneralesDTO = consultasCore.consultarAporteYMovimiento(idsAporte);

			// luego de obtener los aportes, buscar las personas tramitadoras
			agregarTramitadores(consultaRecaudo, aportesGeneralesDTO);

		}
	

		List<AnalisisDevolucionDTO> analisisDevolucion = obtenerAnalisisDevolucionDTO(aportesGeneralesDTO,
				consultaRecaudo);


		if (analisisDevolucion != null && !analisisDevolucion.isEmpty()) {
		

			List<CuentaAporteDTO> cuentaRecuadoDevolucionCorrecion = consultarCuentaAporte(
					persona != null ? persona.getIdPersona() : null, analisisDevolucion);
		

			if (cuentaRecuadoDevolucionCorrecion != null && !cuentaRecuadoDevolucionCorrecion.isEmpty()) {
		

				for (AnalisisDevolucionDTO analisis : analisisDevolucion) {
					for (CuentaAporteDTO cuentaAporteDTO : cuentaRecuadoDevolucionCorrecion) {
						if (analisis.getIdAporte().equals(cuentaAporteDTO.getIdAporteGeneral())) {
				
							cuentaAporteDTO.setMontoAporteActual(analisis.getMonto());
							cuentaAporteDTO.setInteresAporteActual(analisis.getInteres());
							cuentaAporteDTO.setTotalAporteActual(analisis.getTotal());
						}
					}
				}

				for (CuentaAporteDTO cuentaAporteDTO : cuentaRecuadoDevolucionCorrecion) {

					if (tipo.RECAUDO_MANUAL.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
							|| TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES
									.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
							|| TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO
									.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
		

						// Se arma la lista de cuenta de aportes para solo
						// recuados
						cuentaAporteRecaudoDTOs.add(cuentaAporteDTO);

					} else if (tipo.DEVOLUCION_APORTES.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
			
						/* Se consulta la solicitud de devolucion */
						logger.info ("#getIdMovimientoAporte Vista Devolucion" + cuentaAporteDTO.getIdMovimientoAporte());
						SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO = consultasCore
								.consultarSolicitudDevolucionVista360(cuentaAporteDTO.getIdAporteGeneral(),
										cuentaAporteDTO.getIdMovimientoAporte());
						/*logger.info("IdSolicitudDevolucion " + solicitudDevolucionAporteModeloDTO.getIdSolicitudDevolucionAporte+
						" <-> idDevolucionAporte "+ solicitudDevolucionAporteModeloDTO.getIdDevolucionAporte);*/

						cuentaAporteDTO.setSolicitudDevolucionAporteModeloDTO(solicitudDevolucionAporteModeloDTO);
						cuentaAporteDevolucionDTOs.add(cuentaAporteDTO);
					} else if (tipo.CORRECCION_APORTES.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
				
						/* Se consulta la solicitud de correcion */
						List<Long> idsAportes = new ArrayList<>();
						idsAportes.add(cuentaAporteDTO.getIdAporteGeneral());
						Map<Long, List<SolicitudCorreccionAporteModeloDTO>> solicitudesCorreccionAporte = consultasCore
								.consultarSolicitudCorrecionVista360(idsAportes);

						cuentaAporteDTO.setSolicitudCorreccionAporteModeloDTO(
								solicitudesCorreccionAporte.get(cuentaAporteDTO.getIdAporteGeneral()));
						cuentaAporteCorrecionDTOs.add(cuentaAporteDTO);
					}
				}
			}
		}


		if (TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.equals(tipo)
				|| TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(tipo)
				|| TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(tipo)) {

			actualizarValoresAporteBase(cuentaAporteRecaudoDTOs, cuentaAporteDevolucionDTOs, cuentaAporteCorrecionDTOs);
			cuentaAporteGeneralDTOs.addAll(cuentaAporteRecaudoDTOs);
		} else if (TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(tipo)) {
	
			cuentaAporteGeneralDTOs.addAll(cuentaAporteDevolucionDTOs);
		} else if (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(tipo)) {
		
			cuentaAporteGeneralDTOs.addAll(cuentaAporteCorrecionDTOs);
		}
		for(CuentaAporteDTO variable: cuentaAporteGeneralDTOs ){
		if(EstadoAporteEnum.CORREGIDO.equals(variable.getEstadoAporte())
		&& !TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES
				.equals(variable.getTipoMovimientoRecaudo())
		&& !TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES
				.equals(variable.getTipoMovimientoRecaudo())){
		List<BigDecimal> resultado = consultasCore.consultarAporteObligatorioInteres(variable.getIdAporteDetallado());
		variable.setAporteDeRegistro(resultado.get(1));
		variable.setInteresesAporte(resultado.get(0));
		variable.setTotalAporte(resultado.get(1).add(resultado.get(0)));
		}		
	}
	

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return cuentaAporteGeneralDTOs;
	}
	@SuppressWarnings("static-access")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360PersonaVistaBoton(
			ConsultarRecaudoDTO consultaRecaudo, TipoMovimientoRecaudoAporteEnum tipo, List<Long> idAporteGeneral, UriInfo uri, HttpServletResponse response) {
		String firmaServicio = "AportesBusiness.consultarRecaudoDevolucionCorreccionVista360Persona(ConsultarRecaudoDTO,TipoMovimientoRecaudoAporteEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
	

		List<CuentaAporteDTO> cuentaAporteGeneralDTOs = new ArrayList<>();
		if (consultaRecaudo.getNumeroIdentificacion() == null && consultaRecaudo.getTipoIdentificacion() == null
				&& consultaRecaudo.getTipoSolicitante() == null) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + " : Parámetros Incompletos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	

		List<CuentaAporteDTO> cuentaAporteRecaudoDTOs = new ArrayList<>();
		List<CuentaAporteDTO> cuentaAporteDevolucionDTOs = new ArrayList<>();
		List<CuentaAporteDTO> cuentaAporteCorrecionDTOs = new ArrayList<>();

	

		/* Se consulta la persona */
		PersonaDTO persona = consultasCore.consultarPersonaTipoNumeroIdentificacion(
				consultaRecaudo.getTipoIdentificacion(), consultaRecaudo.getNumeroIdentificacion());

		CriteriaBuilder cb = consultasCore.obtenerCriteriaBuilder();
		CriteriaQuery<AporteGeneral> c = cb.createQuery(AporteGeneral.class);
	

		Root<AporteGeneral> aporteGeneral = c.from(AporteGeneral.class);
		c.select(aporteGeneral);

	

		List<AporteGeneral> aportesGenerales = null;
		List<Predicate> predicates = new ArrayList<>();



		if (consultaRecaudo.getTipoSolicitante() != null) {
			predicates.add(cb.equal(aporteGeneral.get("tipoSolicitante"), consultaRecaudo.getTipoSolicitante()));
		}
	

		if (consultaRecaudo.getMetodoRecaudo() != null) {
			predicates.add(cb.equal(aporteGeneral.get("modalidadRecaudoAporte"), consultaRecaudo.getMetodoRecaudo()));
		}
		

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		if (consultaRecaudo.getPeriodoRecaudo() != null) {
			String fechaPagoString = null;
			fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
			predicates.add(cb.equal(aporteGeneral.get("periodoAporte"), fechaPagoString));
		}


		Long millisDia = (long) ((24 * 3600 * 1000) - 1); // para la
															// fecha fin

		if (consultaRecaudo.getFechaInicio() != null && consultaRecaudo.getFechaFin() != null) {
			Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
			Date fechaFinDate = new Date(consultaRecaudo.getFechaFin() + millisDia);
			predicates.add(cb.between(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate, fechaFinDate));
		} else if (consultaRecaudo.getFechaInicio() != null) {
			Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
			predicates.add(cb.greaterThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate));
		} else if (consultaRecaudo.getFechaFin() != null) {
			Date fechaFinDate = new Date(consultaRecaudo.getFechaFin() + millisDia);
			predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaFinDate));
		}

		if (!idAporteGeneral.isEmpty()) {
			predicates.add(aporteGeneral.get("id").in(idAporteGeneral));
		}


		c.select(aporteGeneral).where(predicates.toArray(new Predicate[] {}));
		aportesGenerales = consultasCore.obtenerListaAportes(c);


		List<Long> idsAporte = new ArrayList<>();
		for (AporteGeneral aporte : aportesGenerales) {
			idsAporte.add(aporte.getId());
		}
	

		if (persona != null) {
			if (persona.getRazonSocial() == null || persona.getRazonSocial().isEmpty()) {
				StringBuilder nombreAportante = new StringBuilder();
				nombreAportante.append(persona.getPrimerNombre() + " ");
				nombreAportante.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre() + " " : "");
				nombreAportante.append(persona.getPrimerApellido() + " ");
				nombreAportante.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
				consultaRecaudo.setNombreCompleto(nombreAportante.toString());

			} else {
				consultaRecaudo.setNombreCompleto(persona.getRazonSocial());
			}
		}


		List<AporteGeneralModeloDTO> aportesGeneralesDTO = new ArrayList<>();
		if (!idsAporte.isEmpty()) {
			aportesGeneralesDTO = consultasCore.consultarAporteYMovimiento(idsAporte);

			// luego de obtener los aportes, buscar las personas tramitadoras
			agregarTramitadores(consultaRecaudo, aportesGeneralesDTO);
		}
	

		List<AnalisisDevolucionDTO> analisisDevolucion = obtenerAnalisisDevolucionDTO(aportesGeneralesDTO,
				consultaRecaudo);


		if (analisisDevolucion != null && !analisisDevolucion.isEmpty()) {
		

			List<CuentaAporteDTO> cuentaRecuadoDevolucionCorrecion = consultarCuentaAporteVistaBoton(
					persona != null ? persona.getIdPersona() : null, analisisDevolucion, uri, response,tipo);
		
			logger.info("cuentaRecuadoDevolucionCorrecion.size() "+cuentaRecuadoDevolucionCorrecion.size());
			if (cuentaRecuadoDevolucionCorrecion != null && !cuentaRecuadoDevolucionCorrecion.isEmpty()) {
		

				for (AnalisisDevolucionDTO analisis : analisisDevolucion) {
					for (CuentaAporteDTO cuentaAporteDTO : cuentaRecuadoDevolucionCorrecion) {
						if (analisis.getIdAporte().equals(cuentaAporteDTO.getIdAporteGeneral())) {
				
							cuentaAporteDTO.setMontoAporteActual(analisis.getMonto());
							cuentaAporteDTO.setInteresAporteActual(analisis.getInteres());
							cuentaAporteDTO.setTotalAporteActual(analisis.getTotal());
						}
					}
				}

				for (CuentaAporteDTO cuentaAporteDTO : cuentaRecuadoDevolucionCorrecion) {

					if (tipo.RECAUDO_MANUAL.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
							|| TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES
									.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
							|| TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO
									.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
		

						// Se arma la lista de cuenta de aportes para solo
						// recuados
						cuentaAporteRecaudoDTOs.add(cuentaAporteDTO);

					} else if (tipo.DEVOLUCION_APORTES.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
			
						/* Se consulta la solicitud de devolucion */
						logger.info ("#getIdMovimientoAporte Vista Devolucion" + cuentaAporteDTO.getIdMovimientoAporte());
						SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO = consultasCore
								.consultarSolicitudDevolucionVista360(cuentaAporteDTO.getIdAporteGeneral(),
										cuentaAporteDTO.getIdMovimientoAporte());
						/*logger.info("IdSolicitudDevolucion " + solicitudDevolucionAporteModeloDTO.getIdSolicitudDevolucionAporte+
						" <-> idDevolucionAporte "+ solicitudDevolucionAporteModeloDTO.getIdDevolucionAporte);*/

						cuentaAporteDTO.setSolicitudDevolucionAporteModeloDTO(solicitudDevolucionAporteModeloDTO);
						cuentaAporteDevolucionDTOs.add(cuentaAporteDTO);
					} else if (tipo.CORRECCION_APORTES.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
				
						/* Se consulta la solicitud de correcion */
						List<Long> idsAportes = new ArrayList<>();
						idsAportes.add(cuentaAporteDTO.getIdAporteGeneral());
						Map<Long, List<SolicitudCorreccionAporteModeloDTO>> solicitudesCorreccionAporte = consultasCore
								.consultarSolicitudCorrecionVista360(idsAportes);

						cuentaAporteDTO.setSolicitudCorreccionAporteModeloDTO(
								solicitudesCorreccionAporte.get(cuentaAporteDTO.getIdAporteGeneral()));
						cuentaAporteCorrecionDTOs.add(cuentaAporteDTO);
					}
				}
			}
		}


		if (TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.equals(tipo)
				|| TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(tipo)
				|| TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(tipo)) {

			actualizarValoresAporteBase(cuentaAporteRecaudoDTOs, cuentaAporteDevolucionDTOs, cuentaAporteCorrecionDTOs);
			cuentaAporteGeneralDTOs.addAll(cuentaAporteRecaudoDTOs);
		} else if (TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(tipo)) {
	
			cuentaAporteGeneralDTOs.addAll(cuentaAporteDevolucionDTOs);
		} else if (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(tipo)) {
		
			cuentaAporteGeneralDTOs.addAll(cuentaAporteCorrecionDTOs);
		}
		for(CuentaAporteDTO variable: cuentaAporteGeneralDTOs ){
		if(EstadoAporteEnum.CORREGIDO.equals(variable.getEstadoAporte())
		&& !TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES
				.equals(variable.getTipoMovimientoRecaudo())
		&& !TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES
				.equals(variable.getTipoMovimientoRecaudo())){
		List<BigDecimal> resultado = consultasCore.consultarAporteObligatorioInteres(variable.getIdAporteDetallado());
		variable.setAporteDeRegistro(resultado.get(1));
		variable.setInteresesAporte(resultado.get(0));
		variable.setTotalAporte(resultado.get(1).add(resultado.get(0)));
		}		
	}
	

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return cuentaAporteGeneralDTOs;
	}

	/**
	 * Método encargado de llevar los valores actuales del aporte al registro de
	 * recaudo
	 * 
	 * @param recaudos
	 * @param devoluciones
	 * @param correcciones
	 */
	private void actualizarValoresAporteBase(List<CuentaAporteDTO> recaudos, List<CuentaAporteDTO> devoluciones,
			List<CuentaAporteDTO> correcciones) {
		String firmaMetodo = "AportesBusiness.actualizarValoresAporteBase(List<CuentaAporteDTO>, List<CuentaAporteDTO>, List<CuentaAporteDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		// se mapean los listados por tipo de movimiento de acuerdo a ID de
		// aporte general y detallado (sí aplica)
		Map<String, List<CuentaAporteDTO>> mapaDevoluciones = new HashMap<>();
		Map<String, List<CuentaAporteDTO>> mapaCorrecciones = new HashMap<>();

		String llave = null;
		List<CuentaAporteDTO> valores = null;

		for (CuentaAporteDTO devolucion : devoluciones) {
			llave = devolucion.getIdAporteDetallado() != null
					? devolucion.getIdAporteGeneral().toString() + devolucion.getIdAporteDetallado().toString()
					: devolucion.getIdAporteGeneral().toString();

			valores = mapaDevoluciones.get(llave);
			if (valores == null) {
				valores = new ArrayList<>();
				mapaDevoluciones.put(llave, valores);
			}

			valores.add(devolucion);
		}

		for (CuentaAporteDTO correccion : correcciones) {
			llave = correccion.getIdAporteDetallado() != null
					? correccion.getIdAporteGeneral().toString() + correccion.getIdAporteDetallado().toString()
					: correccion.getIdAporteGeneral().toString();

			valores = mapaCorrecciones.get(llave);
			if (valores == null) {
				valores = new ArrayList<>();
				mapaCorrecciones.put(llave, valores);
			}

			valores.add(correccion);
		}

		for (CuentaAporteDTO recaudo : recaudos) {
			llave = recaudo.getIdAporteDetallado() != null
					? recaudo.getIdAporteGeneral().toString() + recaudo.getIdAporteDetallado().toString()
					: recaudo.getIdAporteGeneral().toString();

			// cuando el recaudo tiene corrección, los valores se ponen en cero
			if (mapaCorrecciones.containsKey(llave)) {
				recaudo.setAporteDeRegistro(BigDecimal.ZERO);
				recaudo.setInteresesAporte(BigDecimal.ZERO);
				recaudo.setTotalAporte(BigDecimal.ZERO);
			}
			// cuando el recaudo tiene devoluciones, se le resta el valor de las
			// mismas al valor del aporte
			else if (mapaDevoluciones.containsKey(llave)) {
				valores = mapaDevoluciones.get(llave);
				for (CuentaAporteDTO devolucion : valores) {
					recaudo.setAporteDeRegistro(recaudo.getAporteDeRegistro().subtract(devolucion.getAjuste()));
					recaudo.setInteresesAporte(recaudo.getInteresesAporte().subtract(devolucion.getInteresesAjuste()));
					recaudo.setTotalAporte(recaudo.getTotalAporte().subtract(devolucion.getAjuste()));
				}
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

		/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarNovedades(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<HistoricoNovedadesDTO> consultarNovedades(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Long periodopago) {
		try {
			logger.debug(
					"Inicio de método consultarNovedades(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			/*
			 * se consultan las novedades aplicadas y no aplicadas 13 periodos
			 * antes del periodo de pago
			 */
			Calendar fechaInicio = Calendar.getInstance();
			fechaInicio.setTime(new Date(periodopago));
			fechaInicio.set(Calendar.DAY_OF_MONTH, fechaInicio.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date fechaFin = fechaInicio.getTime();
			fechaInicio.add(Calendar.MONTH, -13);

			List<HistoricoNovedadesDTO> novedades = consultasCore.consultarHistoricoNovedades(fechaInicio.getTime(),
					fechaFin, tipoIdentificacion, numeroIdentificacion);

			List<HistoricoNovedadesDTO> novedadesSinEmpleador = consultasCore.consultarNovedadesSinRolAfiliado(
					fechaInicio.getTime(), fechaFin, tipoIdentificacion, numeroIdentificacion);

			// List<HistoricoNovedadesDTO> novedadesRecientes =
			// consultasCore.consultarHistoricoNovedadesRecientes(fechaFin,
			// tipoIdentificacion, numeroIdentificacion);

			List<Object[]> novedadesRechazadas = consultasStaging.consultarNovedadesRechazadas(fechaInicio.getTime(),
					fechaFin, tipoIdentificacion, numeroIdentificacion);

			// List<Object[]> novedadesRechazadasRecientes =
			// consultasStaging.consultarNovedadesRechazadasRecientes(fechaFin,
			// tipoIdentificacion, numeroIdentificacion);

			// novedades.addAll(novedadesRecientes);
			// novedadesRechazadas.addAll(novedadesRechazadasRecientes);
			novedades.addAll(novedadesSinEmpleador);

			for (Object[] novedadRechazada : novedadesRechazadas) {
				HistoricoNovedadesDTO historicoNovedad = new HistoricoNovedadesDTO();
				Date fechaRegistro = (Date) novedadRechazada[0];
				historicoNovedad.setFechaRegistro(fechaRegistro != null ? fechaRegistro.getTime() : null);
				historicoNovedad.setTipoNovedad(
						novedadRechazada[1] != null ? TipoTransaccionEnum.valueOf(novedadRechazada[1].toString())
								: TipoNovedadPilaEnum.NOVEDAD_ING
										.equals(TipoNovedadPilaEnum.valueOf(novedadRechazada[6].toString()))
												? TipoTransaccionEnum.NOVEDAD_ING : null);
				historicoNovedad.setCanal(
						novedadRechazada[2] != null ? CanalRecepcionEnum.APORTE_MANUAL : CanalRecepcionEnum.PILA);
				historicoNovedad.setEmpleador(novedadRechazada[3] != null ? novedadRechazada[3].toString() : null);
				if (novedadRechazada[4] != null) {
					Date fechaInicioBD = (Date) novedadRechazada[4];
					historicoNovedad.setFechaInicio(fechaInicioBD != null ? fechaInicioBD.getTime() : null);
				}
				if (novedadRechazada[5] != null) {
					Date fechaFinBD = (Date) novedadRechazada[5];
					historicoNovedad.setFechaFin(fechaFinBD != null ? fechaFinBD.getTime() : null);
				}
				historicoNovedad.setEstado(Boolean.FALSE);
				novedades.add(historicoNovedad);
			}
			logger.debug(
					"Fin de método consultarNovedades(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			return novedades;
		} catch (Exception e) {
			logger.error("Ocurrio un error en consultarNovedades", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}


	/**
	 * Método encargado de la consulta de datos de tramitadores de aportes
	 * 
	 * @param consultaRecaudo
	 * @param aportesGenerales
	 */
	private void agregarTramitadores(ConsultarRecaudoDTO consultaRecaudo,
			List<AporteGeneralModeloDTO> aportesGenerales) {
		String firmaMetodo = "agregarTramitadores(ConsultarRecaudoDTO, List<AporteGeneral>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		ConsultarRecaudoDTO consultaRecaudoTemp = consultaRecaudo;

		// se listan los ID de empresa tramitadora de los aportes
		List<Long> idsEmpresaTramitadora = new ArrayList<>();
		for (AporteGeneralModeloDTO aporte : aportesGenerales) {
			if (aporte.getEmpresaTramitadoraAporte() != null
					&& !idsEmpresaTramitadora.contains(aporte.getEmpresaTramitadoraAporte())) {
				idsEmpresaTramitadora.add(aporte.getEmpresaTramitadoraAporte());
			}
		}

		// se consultan las empresas
		Map<Long, EmpresaModeloDTO> mapaEmpresasTramitadoras = new HashMap<>();
		if (idsEmpresaTramitadora != null && !idsEmpresaTramitadora.isEmpty()) {
			List<EmpresaModeloDTO> empresasTramitadoras = consultasCore.consultarEmpresasPorIds(idsEmpresaTramitadora);

			// se organiza el mapa de empresas por aporte
			for (AporteGeneralModeloDTO aporte : aportesGenerales) {
				if (aporte.getEmpresaTramitadoraAporte() != null) {
					for (EmpresaModeloDTO tramitador : empresasTramitadoras) {
						if (tramitador.getIdEmpresa().equals(aporte.getEmpresaTramitadoraAporte())) {
							mapaEmpresasTramitadoras.put(aporte.getId(), tramitador);
							break;
						}
					}
				}
			}
		}

		consultaRecaudoTemp.setEmpresasTramitadoras(mapaEmpresasTramitadoras);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDetalleDevolucionesVista360(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DetalleDevolucionVista360DTO consultarDetalleDevolucionesVista360(Long idSolicitudDevolucion) {
		String firmaMetodo = "consultarDetalleDevolucionesVista360(Long idSolicitudDevolucion)";
		logger.debug("Inicia " + firmaMetodo);
		/*
		 * Se consultan las devoluciones por medio de los id de aporte general y
		 * detallado
		 */
		List<DetalleDevolucionVista360DTO> devolucionVista360DTO = consultasCore
				.consultarDetalleDevolucionVista360(idSolicitudDevolucion);
		logger.debug("Finaliza " + firmaMetodo);
		return !devolucionVista360DTO.isEmpty() ? devolucionVista360DTO.get(0) : new DetalleDevolucionVista360DTO();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarCotizantesVista360Persona(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> consultarCotizantesVista360Persona(Long idPersona) {
		String firmaMetodo = "AportesBusiness.consultarCotizantesVista360Persona(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		/*
		 * Se consultan los id de aporte general para que esta lista sea
		 * utilizada en consultarRecaudoDevolucionCorreccionVista360
		 */
		List<Long> idAporteGeneral = consultasCore.consultarAporteGeneralPorCotizante360Persona(idPersona);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return idAporteGeneral;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDetalleCorrecionCotizanteVista360(java.lang.Long)
	 */
	@Override
	public List<DetalleCorreccionCotizanteVista360DTO> consultarDetalleCorrecionCotizanteVista360(
			Long idSolicitudCorreccion) {
		String firmaMetodo = "consultarDetalleCorrecionCotizanteVista360(Long idSolicitudCorreccion)";
		try {
			logger.debug("Inicia " + firmaMetodo);
			List<DetalleCorreccionCotizanteVista360DTO> detalleCorreccionCotizanteVista360DTO = consultasCore
					.consultarDetalleCorrecionCotizanteVista360(idSolicitudCorreccion);
			logger.debug("Finaliza " + firmaMetodo);
			return detalleCorreccionCotizanteVista360DTO;
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia en " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDetalleCorrecionAportanteVista360(java.lang.Long,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DetalleCorreccionAportanteVista360DTO consultarDetalleCorrecionAportanteVista360(
			Long idSolicitudCorreccion) {
		String firmaMetodo = "AportesBusiness.consultarDetalleCorrecionAportanteVista360(Long, TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DetalleCorreccionAportanteVista360DTO detalleCorreccionAportanteVista360DTO = null;

		try {
			/*
			 * Se consultan las devoluciones por medio de los id de aporte
			 * general y detallado
			 */
			detalleCorreccionAportanteVista360DTO = consultasCore
					.consultarDetalleCorrecionAportanteVista360(idSolicitudCorreccion);
			if (detalleCorreccionAportanteVista360DTO != null) {
				/*
				 * Se consulta en registro general para obtener el tipo del
				 * beneficio
				 */
				RegistroGeneral registroGeneral = consultasStaging
						.consultarRegistroGeneralId(detalleCorreccionAportanteVista360DTO.getIdRegistroGeneral());
				if (registroGeneral != null) {
					detalleCorreccionAportanteVista360DTO.setTipoPersona(
							registroGeneral.getTipoPersona() != null ? registroGeneral.getTipoPersona() : null);
					detalleCorreccionAportanteVista360DTO.setClaseAportante(
							registroGeneral.getClaseAportante() != null ? registroGeneral.getClaseAportante() : null);
					if (registroGeneral.getOutTipoBeneficio() != null
							&& TipoBeneficioEnum.LEY_1429.equals(registroGeneral.getOutTipoBeneficio())) {
						detalleCorreccionAportanteVista360DTO
								.setOutBeneficioActivo(registroGeneral.getOutBeneficioActivo() != null
										? registroGeneral.getOutBeneficioActivo() : null);
						if (registroGeneral.getTipoPersona() != null && !registroGeneral.getTipoPersona().isEmpty()) {
							detalleCorreccionAportanteVista360DTO.setTipoPersona(TipoPersonaEnum
									.obtenerTipoPersona(registroGeneral.getTipoPersona()).getDescripcion());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return detalleCorreccionAportanteVista360DTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDetalleCorrecionCotizanteNuevoVista360(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DetalleCorreccionCotizanteNuevoVista360DTO> consultarDetalleCorrecionCotizanteNuevoVista360(
			Long idSolicitudCorreccion, Long idAporteDetallado) {
		logger.info("----------------------------------------- Inicia consultar detalle Corrección Cotización -------------------------------------------------");
		long timeStartconsultarCorreccionCotizacion = System.currentTimeMillis();
		String firmaMetodo = "consultarDetalleCorrecionCotizanteNuevoVista360(Long idAporteGeneral,TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)";
		try {
			logger.debug("Inicia " + firmaMetodo);
			/*
			 * Se consultan las devoluciones por medio de los id de aporte
			 * general y detallado
			 */
			List<DetalleCorreccionCotizanteNuevoVista360DTO> listCotizanteNuevoVista360DTOs = consultasCore
					.consultarDetalleCorrecionCotizanteNuevoVista360(idSolicitudCorreccion, idAporteDetallado);
			logger.debug("Finaliza " + firmaMetodo);
			return listCotizanteNuevoVista360DTOs;
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia en " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		// long timeEndconsultarCorreccionCotizacion = System.currentTimeMillis();
		// logger.info("Finaliza Cuenta aporte ANALISIS DEVOLUCIÓN FOR: " + (timeEndconsultarCorreccionCotizacion - timeStartconsultarCorreccionCotizacion) + " ms");
		// logger.info("-----------------------------------------FIN consular correccion cotización vista 360 -------------------------------------------------");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarResultadoRecaudo(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ResultadoRecaudoCotizanteDTO> consultarResultadoRecaudo(Long idAporteGeneral) {
		String firmaMetodo = "consultarResultadoRecaudo(Long idAporteGeneral)";
		try {
			logger.debug("Inicia " + firmaMetodo);
			List<ResultadoRecaudoCotizanteDTO> resultadosRecaudos = new ArrayList<>();

			AporteGeneral aporteGeneral = consultasCore.consultarAporteGeneral(idAporteGeneral);

			List<EstadoRegistroAportesArchivoEnum> estadoRegistroOk = new ArrayList<>();
			estadoRegistroOk.add(EstadoRegistroAportesArchivoEnum.OK);
			List<RecaudoCotizanteDTO> recaudoAporteOK = consultasCore.consultarEstadoRegistros(idAporteGeneral,
					estadoRegistroOk);
			ResultadoRecaudoCotizanteDTO resultadoAporteOK = new ResultadoRecaudoCotizanteDTO();
			resultadoAporteOK.setRecaudosCotizante(recaudoAporteOK);
			resultadoAporteOK.setTipoRegistro(TipoRegistroRecaudoEnum.APORTES_OK);
			resultadosRecaudos.add(resultadoAporteOK);

			List<EstadoRegistroAportesArchivoEnum> estadoRegistroNoOk = new ArrayList<>();
			estadoRegistroNoOk.add(EstadoRegistroAportesArchivoEnum.NO_OK_APROBADO);
			estadoRegistroNoOk.add(EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD_APROBADO);
			List<RecaudoCotizanteDTO> recaudoAporteNOOK = consultasCore.consultarEstadoRegistros(idAporteGeneral,
					estadoRegistroNoOk);
			ResultadoRecaudoCotizanteDTO resultadoAporteNOOK = new ResultadoRecaudoCotizanteDTO();
			resultadoAporteNOOK.setRecaudosCotizante(recaudoAporteNOOK);
			resultadoAporteNOOK.setTipoRegistro(TipoRegistroRecaudoEnum.APORTES_NO_OK);
			resultadosRecaudos.add(resultadoAporteNOOK);

			List<RecaudoCotizanteDTO> novedadesAplicadas = consultasStaging
					.consultarRegistrosNovedadesAplicadas(aporteGeneral.getIdRegistroGeneral());
			ResultadoRecaudoCotizanteDTO resultadoNovedadesAplicadas = new ResultadoRecaudoCotizanteDTO();
			resultadoNovedadesAplicadas.setRecaudosCotizante(novedadesAplicadas);
			resultadoNovedadesAplicadas.setTipoRegistro(TipoRegistroRecaudoEnum.NOVEDADES_PROCESADAS_APLICADAS);
			resultadosRecaudos.add(resultadoNovedadesAplicadas);

			List<RecaudoCotizanteDTO> novedadesNoProcesadas = consultasStaging
					.consultarRegistrosNovedadesGuardadas(aporteGeneral.getIdRegistroGeneral());
			ResultadoRecaudoCotizanteDTO resultadoNovedadesNoProcesadas = new ResultadoRecaudoCotizanteDTO();
			resultadoNovedadesNoProcesadas.setRecaudosCotizante(novedadesNoProcesadas);
			resultadoNovedadesNoProcesadas.setTipoRegistro(TipoRegistroRecaudoEnum.NOVEDADES_GUARDADAS_NO_PROCESADAS);
			resultadosRecaudos.add(resultadoNovedadesNoProcesadas);

			List<RecaudoCotizanteDTO> pendientesAfiliar = consultasCore
					.consultarRegistrosPendientesAfiliar(idAporteGeneral);
			ResultadoRecaudoCotizanteDTO resultadoPendientesAfiliar = new ResultadoRecaudoCotizanteDTO();
			resultadoPendientesAfiliar.setRecaudosCotizante(pendientesAfiliar);
			resultadoPendientesAfiliar.setTipoRegistro(TipoRegistroRecaudoEnum.PENDIENTES_AFILIAR);
			resultadosRecaudos.add(resultadoPendientesAfiliar);

			logger.debug("Finaliza " + firmaMetodo);
			return resultadosRecaudos;
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia en " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarInformacionGeneralRecaudoEmpleadorVista360(java.lang.Long,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public RegistroGeneralModeloDTO consultarInformacionGeneralRecaudoEmpleadorVista360(Long idAporteGeneral,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		String firmaServicio = "consultarInformacionGeneralRecaudoEmpleadorVista360(Long idAporteGeneral, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		RegistroGeneralModeloDTO registroGeneralModeloDTO = new RegistroGeneralModeloDTO();

		/* Se el aporte general */
		AporteGeneralModeloDTO aporteGeneralModeloDTO = consultarAporteGeneral(idAporteGeneral);
		if (aporteGeneralModeloDTO != null) {
			registroGeneralModeloDTO = consultasStaging.consultarRegistroGeneralPorDatosAportante(
					aporteGeneralModeloDTO.getIdRegistroGeneral(), tipoIdentificacion, numeroIdentificacion);
			
			List<PilaEstadoTransitorio> pet = consultasCore.consultarEstadoProcesamientoPlanilla(registroGeneralModeloDTO.getRegistroControl());
			// si está vacío puede ser por:
			// caso 1: planillas cargadas antes del ajuste de pila fase 3
			// caso 2: aún no se copia el aportes en AporteGeneral, como tal en la vista 360 no lo mostraría (no se tiene en cuenta) 
			if (pet.isEmpty()) {
				registroGeneralModeloDTO.setEnBandejaTransitoria(false);
				if (registroGeneralModeloDTO != null && 
						EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO.equals(registroGeneralModeloDTO.getOutEstadoArchivo())) {
					registroGeneralModeloDTO.setEnProcesamiento(false);
				} else {
					registroGeneralModeloDTO.setEnProcesamiento(true);
				}
			} else {
				registroGeneralModeloDTO.setEnProcesamiento(true);
				registroGeneralModeloDTO.setEnBandejaTransitoria(true);
				
				for (PilaEstadoTransitorio p : pet) {
					if (PilaEstadoTransitorioEnum.EXITOSO.equals(p.getEstado())
							&& PilaAccionTransitorioEnum.NOTIFICAR_PLANILLA.equals(p.getAccion())) {
						registroGeneralModeloDTO.setEnProcesamiento(false);
						registroGeneralModeloDTO.setEnBandejaTransitoria(false);
					}
				}
			}
		} else {
			registroGeneralModeloDTO.setEnProcesamiento(true);
			List<PilaEstadoTransitorio> pet = consultasCore.consultarEstadoProcesamientoPlanilla(idAporteGeneral);
			if (pet.isEmpty()) {
				registroGeneralModeloDTO.setEnBandejaTransitoria(false);
			} else {
				registroGeneralModeloDTO.setEnBandejaTransitoria(true);
			}
			
		}

		if (registroGeneralModeloDTO != null && registroGeneralModeloDTO.getRegistroControlManual() != null) {
			registroGeneralModeloDTO.setOutEstadoArchivo(null);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return registroGeneralModeloDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDetalleDevolucionCotizantes(java.util.List)
	 */
	@Override
	public List<DetalleDevolucionCotizanteDTO> consultarDetalleDevolucionCotizantes(
			List<DetalleDevolucionCotizanteDTO> datosCotizante) {
		String firmaServicio = "AportesBusiness.consultarDetalleDevolucionCotizantes(List<DetalleDevolucionCotizanteDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<DetalleDevolucionCotizanteDTO> detallesDevoluciones = null;

		// se mapean los ids de aportes detallados y de movimiento de aportes
		Map<Long, Long> mapaIdsAporMov = new HashMap<>();
		for (DetalleDevolucionCotizanteDTO cotizante : datosCotizante) {
			if (!mapaIdsAporMov.containsKey(cotizante.getIdAporteDetallado())) {
				mapaIdsAporMov.put(cotizante.getIdAporteDetallado(), cotizante.getIdMovimientoAporte());
			}
		}

		detallesDevoluciones = consultasCore
				.consultarCotizanteAporteDetallado(new ArrayList<Long>(mapaIdsAporMov.keySet()));

		// se completan los datos faltantes
		for (DetalleDevolucionCotizanteDTO detalleCotizante : detallesDevoluciones) {
			detalleCotizante.setIdMovimientoAporte(mapaIdsAporMov.get(detalleCotizante.getIdAporteDetallado()));
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return detallesDevoluciones;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarResultadoRegistroRecaudoAportes(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public List<DetalleRegistroDTO> consultarResultadoRegistroRecaudoAportes(Long fechaInicio, Long fechaFin) {
		String firmaMetodo = "consultarResultadoRegistroRecaudoAportes(Long fechaInicio, Long fechaFin) ";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<DetalleRegistroDTO> detallesPorRegistro = new ArrayList<>();

		List<ResultadoDetalleRegistroDTO> aportes = obtenerListadadoDetalle(TipoRegistroEnum.APORTES, fechaInicio,
				fechaFin);
		List<ResultadoDetalleRegistroDTO> devoluciones = obtenerListadadoDetalle(TipoRegistroEnum.DEVOLUCIONES,
				fechaInicio, fechaFin);
		List<ResultadoDetalleRegistroDTO> registrados = obtenerListadadoDetalle(TipoRegistroEnum.REGISTRADOS,
				fechaInicio, fechaFin);
		List<ResultadoDetalleRegistroDTO> otrosIngresos = obtenerListadadoDetalle(TipoRegistroEnum.OTROS_INGRESOS,
                fechaInicio, fechaFin);
		List<ResultadoDetalleRegistroDTO> correccionesOrigen = obtenerListadadoDetalle(
				TipoRegistroEnum.CORRECCIONES_ORIGEN, fechaInicio, fechaFin);
		List<ResultadoDetalleRegistroDTO> correcciones = obtenerListadadoDetalle(TipoRegistroEnum.CORRECCIONES,
				fechaInicio, fechaFin);

		List<ResultadoDetalleRegistroDTO> planillasN = obtenerListadadoDetalle(TipoRegistroEnum.PLANILLAS_N,
				fechaInicio, fechaFin);
		List<ResultadoDetalleRegistroDTO> extemporaneos = obtenerListadadoDetalle(TipoRegistroEnum.APORTES_EXTEMPORANEOS,
				fechaInicio, fechaFin);

		for(ResultadoDetalleRegistroDTO resultadoDetalleRegistroDTO :extemporaneos){
			logger.info("resultadoDetalleRegistroDTO " + resultadoDetalleRegistroDTO);
		}

		DetalleRegistroDTO detalleAportes = new DetalleRegistroDTO();
		detalleAportes.setResultadoporRegistros(aportes);
		detalleAportes.setTipoRegistro(TipoRegistroEnum.APORTES);
		DetalleRegistroDTO detalleDevoluciones = new DetalleRegistroDTO();
		detalleDevoluciones.setResultadoporRegistros(devoluciones);
		detalleDevoluciones.setTipoRegistro(TipoRegistroEnum.DEVOLUCIONES);
		DetalleRegistroDTO detalleRegitrados = new DetalleRegistroDTO();
		detalleRegitrados.setResultadoporRegistros(registrados);
		detalleRegitrados.setTipoRegistro(TipoRegistroEnum.REGISTRADOS);
		DetalleRegistroDTO detalleOtrosIngresos = new DetalleRegistroDTO();
        detalleOtrosIngresos.setResultadoporRegistros(otrosIngresos);
        detalleOtrosIngresos.setTipoRegistro(TipoRegistroEnum.OTROS_INGRESOS);
		DetalleRegistroDTO detalleCorreccionesOrigen = new DetalleRegistroDTO();
		detalleCorreccionesOrigen.setResultadoporRegistros(correccionesOrigen);
		detalleCorreccionesOrigen.setTipoRegistro(TipoRegistroEnum.CORRECCIONES_ORIGEN);
		DetalleRegistroDTO detalleCorreccionesAnulados = new DetalleRegistroDTO();
		detalleCorreccionesAnulados.setResultadoporRegistros(correcciones);
		detalleCorreccionesAnulados.setTipoRegistro(TipoRegistroEnum.CORRECCIONES);
		DetalleRegistroDTO detallePlanillasN = new DetalleRegistroDTO();
		detallePlanillasN.setResultadoporRegistros(planillasN);
		detallePlanillasN.setTipoRegistro(TipoRegistroEnum.PLANILLAS_N);
		DetalleRegistroDTO detalleExtemporaneo = new DetalleRegistroDTO();
		detalleExtemporaneo.setResultadoporRegistros(extemporaneos);
		detalleExtemporaneo.setTipoRegistro(TipoRegistroEnum.APORTES_EXTEMPORANEOS);

		detallesPorRegistro.add(detalleAportes);
		detallesPorRegistro.add(detalleDevoluciones);
		detallesPorRegistro.add(detalleRegitrados);
		detallesPorRegistro.add(detalleOtrosIngresos);
		detallesPorRegistro.add(detalleCorreccionesOrigen);
		detallesPorRegistro.add(detalleCorreccionesAnulados);
		detallesPorRegistro.add(detallePlanillasN);
		detallesPorRegistro.add(detalleExtemporaneo);

		logger.info("detallesPorRegistro.size()" + detallesPorRegistro.size());

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return detallesPorRegistro;
	}

	public boolean esPrimerDiaDelMes(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		logger.info("fecha primero dia ?? " +(calendar.get(Calendar.DAY_OF_MONTH) == 1));
		return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}

	/**
	 * Método encargado de la solicitud de datos detallado en el cierre de
	 * aportes
	 */
	private List<ResultadoDetalleRegistroDTO> obtenerListadadoDetalle(TipoRegistroEnum tipo, Long fechaInicio,
			Long fechaFin) {
		String firmaMetodo = "AportesBusiness.obtenerListadadoDetalle (TipoRegistroEnum, Long, Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<ResultadoDetalleRegistroDTO> result = null;

		switch (tipo) {
		case APORTES:
			result = consultasCore.consultarDetalleRegistroAportesLegalizacion(fechaInicio, fechaFin, Boolean.FALSE, Boolean.FALSE);
			break;
		case CORRECCIONES:
			result = consultasCore.consultarDetalleRegistroCorrecciones(fechaInicio, fechaFin, Boolean.TRUE);
			break;
		case CORRECCIONES_ORIGEN:
			result = consultasCore.consultarDetalleRegistroCorrecciones(fechaInicio, fechaFin, Boolean.FALSE);
			break;
		case PLANILLAS_N:
			result = consultasCore.consultarDetalleRegistroCorreccionesALaAlta(fechaInicio, fechaFin, Boolean.TRUE);
			break;
		case DEVOLUCIONES:
			result = consultasCore.consultarDetalleRegistroDevoluciones(fechaInicio, fechaFin);
			break;
		case REGISTRADOS:
			result = consultasCore.consultarDetalleRegistroAportesLegalizacion(fechaInicio, fechaFin, Boolean.TRUE, Boolean.FALSE);
			break;
		case OTROS_INGRESOS:
            result = consultasCore.consultarDetalleRegistroAportesLegalizacion(fechaInicio, fechaFin, Boolean.TRUE, Boolean.TRUE);
            break;
		case APORTES_EXTEMPORANEOS:
			Date fechaInicioHabil = new Date(fechaInicio);
			if(esPrimerDiaDelMes(new Date(fechaInicio))) {
				ConsultarListaValores consultarListafestivos = new ConsultarListaValores(239, null, null);
				consultarListafestivos.execute();
				List<ElementoListaDTO> festivos = consultarListafestivos.getResult();
				fechaInicioHabil = CalendarUtils.calcularFecha(
						new Date(fechaInicio), 2, CalendarUtils.TipoDia.HABIL, festivos);
			}
			result = consultasCore.consultarDetalleRegistroAportesExtemporaneos(fechaInicioHabil.getTime(), fechaFin, Boolean.FALSE, Boolean.FALSE); // ya esta
			logger.info("result APORTES_EXTEMPORANEOS----> " + result);
			break;
		default:
			result = Collections.emptyList();
			break;
		}

		// se completa la información de PILA faltante para los casos que aplican
		List<Long> idsRegistroGeneral = new ArrayList<>();
		for (ResultadoDetalleRegistroDTO aporte : result) {
			if (!idsRegistroGeneral.contains(aporte.getAportante().getIdRegistroGeneral())) {
				idsRegistroGeneral.add(aporte.getAportante().getIdRegistroGeneral());
			}
		}

		if (!idsRegistroGeneral.isEmpty()) {
		    
		    int count = 0;
		    List<Callable<Map<Long, RegistroGeneralModeloDTO>>> paralelas = new LinkedList<>();
		    List<Long> ids = new ArrayList<Long>();
		    
		    for (Long id : idsRegistroGeneral) {
		        count++;
		        ids.add(id);
		        if (count == CONSULTAS_POR_HILO) {
                    final List<Long> idsFinal = ids;
                    Callable<Map<Long, RegistroGeneralModeloDTO>> parallelTask = () ->{
                        return consultasStaging.consultarRegistrosGeneralesPorListaId(idsFinal);
                    };
                    paralelas.add(parallelTask);
                    count = 0;
                    ids = new ArrayList<Long>();
                }
            }
		    
		    if (count > 0) {
		        final List<Long> idsFinal = ids;
                Callable<Map<Long, RegistroGeneralModeloDTO>> parallelTask = () ->{
                    return consultasStaging.consultarRegistrosGeneralesPorListaId(idsFinal);
                };
                paralelas.add(parallelTask);
            }
		    
		    List<Future<Map<Long, RegistroGeneralModeloDTO>>> resultadosFuturos = new ArrayList<>();
		    
		    try {
                resultadosFuturos = mes.invokeAll(paralelas);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		    
		    Map<Long, RegistroGeneralModeloDTO> registros = new HashMap<Long, RegistroGeneralModeloDTO>();
		    
            for (Future<Map<Long, RegistroGeneralModeloDTO>> future : resultadosFuturos) {
                try {
                    registros.putAll(future.get());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
			//Map<Long, RegistroGeneralModeloDTO> registros = consultasStaging.consultarRegistrosGeneralesPorListaId(idsRegistroGeneral);
	        
			for (ResultadoDetalleRegistroDTO aporte : result) {
				RegistroGeneralModeloDTO regGen = registros.get(aporte.getAportante().getIdRegistroGeneral());
				aporte.getAportante().setTipoPlanilla(regGen.getTipoPlanilla());

				//TODO: agregar valores de registro general
				BigDecimal tarifa = null;
				//logger.info(regGen.toString());
				//logger.info(aporte.toString());
				if (regGen != null && aporte.getAportante() != null) {
					
					aporte.getAportante().setNumeroPlanilla(regGen.getNumPlanilla()!=null?regGen.getNumPlanilla().toString():null);
										
					if(!ModalidadRecaudoAporteEnum.MANUAL.equals(aporte.getAportante().getModalidadRecaudo())){
						// aporte.getAportante().setCuentaBancariaRecaudo("");
						if(tipo.equals(TipoRegistroEnum.PLANILLAS_N)){
							Object[] cuentaBancariaPlanillaN = consultasCore.consultarCuentaBancariaAportePlanillaN(Long.parseLong(aporte.getAportante().getRecaudoOriginal()));
							if(cuentaBancariaPlanillaN !=null){
								aporte.getAportante().setCuentaBancariaRecaudo(cuentaBancariaPlanillaN[0] != null?cuentaBancariaPlanillaN[0].toString():null);
								aporte.getAportante().setCodigoBancoRecaudador(cuentaBancariaPlanillaN[1] != null?cuentaBancariaPlanillaN[1].toString():null);
							}
							

						}else{
							if (regGen.getCuentaBancariaRecaudo() != null) {
								aporte.getAportante().setCuentaBancariaRecaudo(regGen.getCuentaBancariaRecaudo().toString());

							}
							if (regGen.getNumeroCuenta() != null) {
								aporte.getAportante().setCuentaBancariaRecaudo(regGen.getNumeroCuenta());
							}
							// aporte.getAportante().setCodigoBancoRecaudador("");
							if (regGen.getCodigoEntidadFinanciera() != null) {
								aporte.getAportante().setCodigoBancoRecaudador(String.valueOf(regGen.getCodigoEntidadFinanciera()));
							}
						}
						
					}else{
						Object[] numeroYCodigo = consultasCore.consultarCuentaBancariaAporteGeneral(aporte.getAportante().getIdRegistroGeneral());
						String codigoCuenta, numeroCuenta;

						// Verificación y asignación para codigoCuenta
						if (numeroYCodigo[1] == null || numeroYCodigo[1].toString().isEmpty()) {
							codigoCuenta = String.valueOf(regGen.getCodigoEntidadFinanciera());
						} else {
							codigoCuenta = numeroYCodigo[1].toString();
						}

						// Verificación y asignación para numeroCuenta
						if (numeroYCodigo[0] == null || numeroYCodigo[0].toString().isEmpty()) {
							numeroCuenta = String.valueOf(regGen.getCuentaBancariaRecaudo());
						} else {
							numeroCuenta = numeroYCodigo[0].toString();
						}

						aporte.getAportante().setCodigoBancoRecaudador(codigoCuenta);
						aporte.getAportante().setCuentaBancariaRecaudo(numeroCuenta);
					}
					if(aporte.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())){
						aporte.getAportante().setTarifaAportante(regGen.getOutTarifaEmpleador());
						if (regGen.getOutTarifaEmpleador() == null) {
							aporte.getAportante().setTarifaAportante(new BigDecimal("0.04000"));
						}
					}else{
						try {
							 
							tarifa = new BigDecimal(consultasCore.consultarTarifaAportante(
									Long.toString(aporte.getAportante().getIdRegistroGeneral()),
									aporte.getAportante().getNumeroIdentificacionAportante(),
									aporte.getAportante().getTipoIdentificacionAportante().name()
								));
							/*
							tarifa = new BigDecimal(consultasPila.consultarValorTarifaAportante(
									Long.toString(aporte.getAportante().getIdRegistroGeneral()),
									aporte.getAportante().getNumeroIdentificacionAportante(),
									aporte.getAportante().getTipoIdentificacionAportante().name()
								));
							*/
							
						} catch (Exception e) {
							logger.info("No se encontro tarifa para el registro general: " + aporte.getAportante().getIdRegistroGeneral());
						}
						if(tarifa != null){
							aporte.getAportante().setTarifaAportante(tarifa);
						}else{
							aporte.getAportante().setTarifaAportante(new BigDecimal("0"));

						}
					}

					if (aporte.getCotizantes() != null && !aporte.getCotizantes().isEmpty()) {
						aporte.getAportante().setUsuario(aporte.getCotizantes().get(0).getUsuario());
					}
					if (regGen.getFechaRecaudo() != null) {
						aporte.getAportante().setFechaPago(new Date(regGen.getFechaRecaudo()));
					}
					Long numeroOperacionCorrecion = consultasCore.consultarAporteGeneralCorregido(aporte.getAportante().getNumeroOperacion());
					if(numeroOperacionCorrecion != null){
						aporte.getAportante().setNumeroOperacionCorreccion(numeroOperacionCorrecion);
					}
				}else if (aporte.getAportante() != null){
					if(!aporte.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())){
						try {
							
							tarifa = new BigDecimal(consultasCore.consultarTarifaAportante(
									Long.toString(aporte.getAportante().getIdRegistroGeneral()),
									aporte.getAportante().getNumeroIdentificacionAportante(),
									aporte.getAportante().getTipoIdentificacionAportante().name()
								));
							
							/*tarifa = new BigDecimal(consultasPila.consultarValorTarifaAportante(
									Long.toString(aporte.getAportante().getIdRegistroGeneral()),
									aporte.getAportante().getNumeroIdentificacionAportante(),
									aporte.getAportante().getTipoIdentificacionAportante().name()
								));*/
							
						} catch (Exception e) {
						}
						if(tarifa != null){
							aporte.getAportante().setTarifaAportante(tarifa);
						}else{
							aporte.getAportante().setTarifaAportante(new BigDecimal("0"));

						}
					}
				}

				if(aporte.getAportante().getNumeroPlanilla()==null || aporte.getAportante().getNumeroPlanilla().equals("")){
					aporte.getAportante().setNumeroPlanilla(consultasCore.consultarRadicadoCierre(aporte.getAportante().getNumeroOperacion().toString()));
				}

			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}
	

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#conciliarAporteGeneral(java.lang.String)
	 */
	@Override
	public void conciliarAporteGeneral(String idsAporteGeneral) {
		String firmaMetodo = "conciliarAporteGeneral(String idsAporteGeneral)";
		try {
			logger.debug("Inicia " + firmaMetodo);
			consultasCore.actualizarConciliadoAporteGeneral(idsAporteGeneral);
			logger.debug("Finaliza " + firmaMetodo);
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia en " + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#validarCodigoNombreSucursal(java.lang.Long,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean validarCodigoNombreSucursal(Long idRegistroGeneral, String codigoSucursalPila,
			String codigoSucursalPrincipal) {
		String firmaMetodo = "validarCodigoNombreSucursal(Long, String, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Boolean result = false;
		try {
			result = consultasPila.validarSucursal(idRegistroGeneral, codigoSucursalPila, codigoSucursalPrincipal);
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizacionAportesRecalculados()
	 */
	@Override
	public Boolean actualizacionAportesRecalculados(Boolean procesoManual, List<Long> idsRegistrosOrigen) {
		String firmaServicio = "AportesBusiness.actualizacionAportesRecalculados(Boolean, List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		// se consultan las entradas de TemAporteActualizado
        List<TemAporteActualizadoModeloDTO> listaPeticiones = consultasPila.consultarActualizacionesAporte(procesoManual,
                idsRegistrosOrigen);
		Set<Long> listaIdPeticiones = new HashSet<>();

		// se recorren las solicitudes para realizar el ajuste
		for (TemAporteActualizadoModeloDTO peticion : listaPeticiones) {
			// se consulta el/los aporte/s relacionado/s con el registro
			// detallado de la petición
			List<AporteDetalladoModeloDTO> aportes = consultasCore
					.consultarAporteDetalladoPorRegistroDetallado(peticion.getIdRegistroDetallado());

			// se actualizan los aportes consultados
			for (AporteDetalladoModeloDTO aporte : aportes) {
				aporte.setEstadoRegistroAporteArchivo(peticion.getEstadoRegistroAporte());
			}
			consultasCore.actualizarAportesReprocesados(aportes);

			// se agrega el ID de la petición procesada a la lista
			listaIdPeticiones.add(peticion.getId());
		}

		// se eliminan las peticiones procesadas de la BD
		if (!listaIdPeticiones.isEmpty()) {
			consultasPila.eliminarActualizacionesAporte(listaIdPeticiones);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return Boolean.TRUE;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#invocarCalculoEstadoServiciosIndPen(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum,
	 *      java.lang.Long)
	 */
	@Override
	public EstadoServiciosIndPenDTO invocarCalculoEstadoServiciosIndPen(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliacion, Long fechaReferencia) {
		String firmaServicio = "AportesBusiness.invocarCalculoEstadoServiciosIndPen(TipoIdentificacionEnum, "
				+ "String, TipoAfiliadoEnum, Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		EstadoServiciosIndPenDTO result = null;

		// se consultan los datos del afiliado
		Date periodoEnFecha = null;
		Long fechaCalculo;
		if (fechaReferencia != null) {
			periodoEnFecha = new Date(fechaReferencia);
		} else {
			periodoEnFecha = new Date();
		}
		fechaCalculo = CalendarUtils.truncarHora(periodoEnFecha).getTime();
		periodoEnFecha = CalendarUtils.obtenerPrimerDiaMesTruncarHora(periodoEnFecha);

		DatosAfiliadoServiciosDTO datosAfiliado = consultasCore.consultarDatosAfiliadoServicios(tipoIdentificacion,
				numeroIdentificacion, tipoAfiliacion, periodoEnFecha);


		if (datosAfiliado != null) {
			Date fechaCalendarioVencimiento = datosAfiliado.getFechaVencimiento();
			String periodoCalculo = CalendarUtils.darFormatoYYYYMM(datosAfiliado.getFechaVencimiento());

			String mensaje = null;
			/*
			 * sí no se cuenta con fecha de vencimiento de aporte, se fija en
			 * una fecha que siempre de como resultado un aporte vencido, con la
			 * nota de que no se cuenta con el día hábil de vencimiento de
			 * aporte
			 */
			if (fechaCalendarioVencimiento == null) {
				LocalDate fechaVencida = LocalDate.of(1900, 1, 1);
				fechaCalendarioVencimiento = Date.from(fechaVencida.atStartOfDay(ZoneId.systemDefault()).toInstant());
				mensaje = "No se cuenta con el día hábil de vencimiento de aporte para el tipo de afiliación especificado";
			}
			
//			if (PeriodoPagoPlanillaEnum.MES_VENCIDO.equals(datosAfiliado.getOportunidadPago())) {
//				LocalDate localDateBase = Instant.ofEpochMilli(periodoEnFecha.getTime())
//						.atZone(ZoneId.systemDefault()).toLocalDate();
//				
//				localDateBase = localDateBase.minusMonths(1L);
//				Date fechaMenos = Date.from(localDateBase.atStartOfDay(ZoneId.systemDefault()).toInstant());
//				periodoCalculo = CalendarUtils.darFormatoYYYYMM(fechaMenos);
//			}

			datosAfiliado.setTipoAfiliado(tipoAfiliacion);

			// se consultan el aporte correspondiente al período de cálculo
			datosAfiliado = consultasCore.consultarEstadoAporteMasRecientePeriodo(datosAfiliado, periodoCalculo,
					fechaCalendarioVencimiento);

			// se valida la re-evaluación del aporte de acuerdo a las
			// validaciones de la fase 1 de PILA 2 (HU-211-395 / 396 / 480)
			if (datosAfiliado.getEstadoAporte() != null
					&& !EstadoRegistroAportesArchivoEnum.OK.equals(datosAfiliado.getEstadoAporte())) {
				consultasPila.ejecutarRevalidarPila2Fase1(datosAfiliado.getIdRegistroGeneral());
			}

			// se actualizan los aportes que se hayan podido afectar con la
			// reevaluación
			// se soluciona glpi 53806 se comenta la linea siguiente del metodo actualizacionAportesRecalculados(null, null)
		//	actualizacionAportesRecalculados(null, null);

			// se confirma el aporte correspondiente al período de cálculo (en
			// caso de haber recalculado)
			datosAfiliado = consultasCore.consultarEstadoAporteMasRecientePeriodo(datosAfiliado, periodoCalculo,
					fechaCalendarioVencimiento);

			// se prepara la respuesta
			result = definirEstadoServiciosYPrepararSalida(datosAfiliado, tipoIdentificacion, numeroIdentificacion,
					mensaje, new Date(fechaCalculo));
		} else {
			result = new EstadoServiciosIndPenDTO();
			result.setNombreAportante("Persona no identificada");
			result.setMensajeRespuesta("Aportante no encontrado por este tipo de afiliación");
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * Método para determinar la fecha calendario de vencimiento del aporte con
	 * el cual se calculará el estado de los servicios
	 * 
	 * @param fechaReferencia
	 * @return <b>Date</b> Fecha calendario de vencimiento de aportes
	 */
	@Deprecated
	public Date calcularFechaCalendarioVencimientoAporte(Long fechaReferencia, Short diaVencimiento) {
		String firmaServicio = "AportesBusiness.calcularFechaCalendarioVencimientoAporte(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		Date result = null;
		LocalDate periodoEnFecha = null;

		if (fechaReferencia != null) {
			periodoEnFecha = Instant.ofEpochMilli(fechaReferencia).atZone(ZoneId.systemDefault()).toLocalDate();
		} else {
			periodoEnFecha = LocalDate.now();
		}

		if (diaVencimiento != null) {
			result = consultasCore.ejecutarCalculoFechaHabil(periodoEnFecha.getMonthValue(), periodoEnFecha.getYear(),
					diaVencimiento);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * Método que define el estado de los servicios de acuerdo a los estados de
	 * aporte y afiliación
	 * 
	 * @param datosAfiliado
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param mensaje
	 * @param fechaReferencia 
	 * @return <b>EstadoServiciosIndPenDTO</b> DTO que contiene la información
	 *         para la salida
	 */
	private EstadoServiciosIndPenDTO definirEstadoServiciosYPrepararSalida(DatosAfiliadoServiciosDTO datosAfiliado,
																		   TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String mensaje, Date fechaReferencia) {
		String firmaServicio = "AportesBusiness.definirEstadoServiciosYPrepararSalida(DatosAfiliadoServiciosDTO, TipoIdentificacionEnum, "
				+ "String, String, Date)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		EstadoServiciosIndPenDTO result = new EstadoServiciosIndPenDTO();
		String mensajeTemp = mensaje;

		datosAfiliado.setEstadoAfiliacion(consultasCore.consultarEstadoAfiliacionPorTipoAfiliacion(tipoIdentificacion,
				numeroIdentificacion, datosAfiliado.getTipoAfiliado()));

		Date fechaAporteVencimiento = new Date();
		String clasificacionInd = null;
		Double porcentajeUltAporte = 0.0;
		ObtenerInfoDetalladaAfiliadoComoPensionado infoPensionado = new ObtenerInfoDetalladaAfiliadoComoPensionado(numeroIdentificacion, tipoIdentificacion);
		infoPensionado.execute();
		AfiliadoPensionadoVista360DTO datosAportesPen = infoPensionado.getResult();
		try {
			Calendar calen = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaAporte;
			if (datosAfiliado.getTipoAfiliado().equals(TipoAfiliadoEnum.PENSIONADO)) {
				fechaAporte = df.parse(datosAportesPen.getFechaUltimoAporteRecibido());
			} else {
				ObtenerInfoDetalladaAfiliadoComoIndependiente infoIndependiente = new ObtenerInfoDetalladaAfiliadoComoIndependiente(numeroIdentificacion, tipoIdentificacion);
				infoIndependiente.execute();
				AfiliadoIndependienteVista360DTO datosAportesIn = infoIndependiente.getResult();
				fechaAporte = df.parse(datosAportesIn.getFechaUltimoAporteRecibido());

				clasificacionInd = consultasCore.consultarClasificacionIndependiente(Long.parseLong(datosAportesIn.getIdSolicitud()));
				porcentajeUltAporte = consultasCore.consultarPorcentajeIndependiente(tipoIdentificacion, numeroIdentificacion);
			}

			calen.setTime(fechaAporte);
			calen.add(Calendar.MONTH, 1);
			fechaAporteVencimiento = calen.getTime();

		} catch (Exception e) {
		}

		ClasificacionEnum clasificacionPen = datosAportesPen.getClasePensionado();
		result.setEstadoServicios(EstadoServiciosEnum.INACTIVOS);

		if (EstadoAfiliadoEnum.ACTIVO.equals(datosAfiliado.getEstadoAfiliacion())) {
			Integer dia = calcularDiaVencimiento();
			if (clasificacionPen != null) {
				if (clasificacionPen.equals(ClasificacionEnum.FIDELIDAD_25_ANIOS) || clasificacionPen.equals(ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO)) {
					result.setEstadoServicios(EstadoServiciosEnum.ACTIVOS);
				}
			}
			if (datosAfiliado.getDiaVencimiento() > dia) {
				if (clasificacionInd != null && !clasificacionInd.isEmpty()) {
					if (((clasificacionInd.equals(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO.name())) && (porcentajeUltAporte >= 0.02))
							|| (clasificacionInd.equals(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO.name()) && (porcentajeUltAporte >= 0.006))) {
						result.setEstadoServicios(EstadoServiciosEnum.ACTIVOS);
					}
				} else {
					result.setEstadoServicios(EstadoServiciosEnum.ACTIVOS);
				}

			} else {
				if (datosAfiliado.getFechaAfiliacion() != null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datosAfiliado.getFechaAfiliacion());
					cal.add(Calendar.MONTH, 1);
					Date fechaAfiliacionVencimiento = cal.getTime();
					if (
							(datosAfiliado.getFechaAfiliacion().equals(fechaReferencia))
									|| (CalendarUtils.esFechaMayor(fechaAfiliacionVencimiento, fechaReferencia))

					) {
						result.setEstadoServicios(EstadoServiciosEnum.ACTIVOS);
					}

				}
			}
		}

		/*
		 * el aportante no se encuentra afiliado
		 */
		if (mensajeTemp == null && !EstadoAfiliadoEnum.ACTIVO.equals(datosAfiliado.getEstadoAfiliacion())) {
			mensajeTemp = "El aportante no cuenta con una afiliación activa de este tipo";
		}
		/*
		 * no se cuenta con un aporte vigente
		 */
		else if (mensajeTemp == null && !EstadoRegistroAportesArchivoEnum.OK.equals(datosAfiliado.getEstadoAporte())) {
			mensajeTemp = "El aportante no cuenta con un aporte válido para el período";
		}

		// se completa la salida
		result.setTipoIdentificacion(tipoIdentificacion);
		result.setNumeroIdentificacion(numeroIdentificacion);
		result.setNombreAportante(datosAfiliado.getNombreAportante());
		result.setTipoAfiliadoConsulta(datosAfiliado.getTipoAfiliado());
		result.setEstadoAfiliacion(datosAfiliado.getEstadoAfiliacion());
		result.setMensajeRespuesta(mensajeTemp);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	private Integer calcularDiaVencimiento() {
		LocalDate fechaActual = LocalDate.now();

		DayOfWeek diaSemana = fechaActual.getDayOfWeek();

		LocalDate fechaInicioMes = fechaActual.withDayOfMonth(1);

		ConsultarListaValores consultarFestivos = new ConsultarListaValores(239, null, null);
		consultarFestivos.execute();
		List<ElementoListaDTO> festivosDTO = consultarFestivos.getResult();

		Set<LocalDate> diasFestivos = obtenerDiasFestivos(festivosDTO);

		int diaHabil = 0;
		for (LocalDate fecha = fechaInicioMes; !fecha.isAfter(fechaActual); fecha = fecha.plusDays(1)) {
			DayOfWeek dow = fecha.getDayOfWeek();
			boolean esFinDeSemana = dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
			boolean esFestivo = diasFestivos.contains(fecha);

			if (!esFinDeSemana && !esFestivo) {
				diaHabil++;
			}
		}

		return diaHabil - 1;
	}

	private Set<LocalDate> obtenerDiasFestivos(List<ElementoListaDTO> festivos) {
		Set<LocalDate> diasFestivos = new HashSet<>();
		for (ElementoListaDTO dto : festivos) {
			Object fechaAttr = dto.getAtributos().get("fecha");
			if (fechaAttr instanceof Long) {
				Instant instant = Instant.ofEpochMilli((Long) fechaAttr);
				LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();
				diasFestivos.add(fecha);
			}
		}
		return diasFestivos;
	}
	private List<String> obtenerdestinatario(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			String procesoEvento, EtiquetaPlantillaComunicadoEnum etiqueta, StringBuilder idPersona, TipoTipoSolicitanteEnum tipoSolicitante) {
		List<String> destinatarioTO = new ArrayList<>();
		String idPer = "";
		if (!numeroIdentificacion.isEmpty()) {
			NotificacionParametrizadaDTO notificaciontmp = new NotificacionParametrizadaDTO();
			List<CorreoPrioridadPersonaDTO> correoPrioridadPersonaDTOs;
			notificaciontmp.setEtiquetaPlantillaComunicado(etiqueta);
			notificaciontmp.setProcesoEvento(procesoEvento);
			ParametrosComunicadoDTO parametros = new ParametrosComunicadoDTO();
			parametros.setNumeroIdentificacion(numeroIdentificacion);
			parametros.setTipoIdentificacion(tipoIdentificacion);
			parametros.setTipoSolicitante(tipoSolicitante);
			notificaciontmp.setParametros(parametros);
			ObtenerCorreosPrioridad notificaciones = new ObtenerCorreosPrioridad(notificaciontmp);
			notificaciones.execute();
			correoPrioridadPersonaDTOs = notificaciones.getResult();
			for (CorreoPrioridadPersonaDTO correoPrioridadPersonaDTO : correoPrioridadPersonaDTOs) {
				idPer = String.valueOf(correoPrioridadPersonaDTO.getIdPersona());
				destinatarioTO.add(correoPrioridadPersonaDTO.getEmail());
			}
			idPersona.append(idPer);
		}
		return destinatarioTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitudDevolucionAporteIdGeneral(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionAporteIdGeneral(Long idAporteGeneral,
			Long numeroOperacion) {
		try {
			logger.debug("Inicia servicio consultarSolicitudDevolucionAporteIdGeneral");
			SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO = consultasCore
					.consultarSolicitudDevolucionVista360(idAporteGeneral, numeroOperacion);
			logger.debug("Finaliza servicio consultarSolicitudDevolucionAporteIdGeneral");
			return solicitudDevolucionAporteModeloDTO;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarSolicitudDevolucionAporteIdGeneral", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarRecaudoDevolucionCorreccionVista360Empleador(com.asopagos.aportes.dto.ConsultarRecaudoDTO,
	 *      com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum,
	 *      java.lang.String)
	 */
	@SuppressWarnings("static-access")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaAporteDTO> consultarRecaudoDevolucionCorreccionVista360Empleador(
			ConsultarRecaudoDTO consultaRecaudo, TipoMovimientoRecaudoAporteEnum tipo, Long idPersonaCotizante,
			String vistaEmpleador, Boolean hayParametros) {
		try {
			logger.debug(
					"Inicia método consultarRecaudoDevolucionCorreccionVista360Persona(ConsultarRecaudoDTO consultaRecaudo,TipoMovimientoRecaudoAporteEnum tipo)");
			if (consultaRecaudo.getNumeroIdentificacion() == null && consultaRecaudo.getTipoIdentificacion() == null
					&& consultaRecaudo.getTipoSolicitante() == null) {
				logger.debug(
						"Finaliza método consultarRecaudoVista360(ConsultarRecaudoDTO consultaRecaudo):Parámetros Incompletos");
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
			} else {
				List<CuentaAporteDTO> cuentaAporteRecuadoDTOs = new ArrayList<>();
				List<CuentaAporteDTO> cuentaAporteDevolucionDTOs = new ArrayList<>();
				List<CuentaAporteDTO> cuentaAporteCorrecionDTOs = new ArrayList<>();
				List<CuentaAporteDTO> cuentaAporteGeneralDTOs = new ArrayList<>();

				/* Se consulta la persona */
				PersonaDTO persona = consultasCore.consultarPersonaTipoNumeroIdentificacion(
						consultaRecaudo.getTipoIdentificacion(), consultaRecaudo.getNumeroIdentificacion());

				CriteriaBuilder cb = consultasCore.obtenerCriteriaBuilder();
				CriteriaQuery<AporteGeneral> c = cb.createQuery(AporteGeneral.class);
				Root<AporteGeneral> aporteGeneral = c.from(AporteGeneral.class);
				c.select(aporteGeneral);
				List<AporteGeneral> aportesGenerales = null;
				EmpresaDTO empresa = null;
				List<Predicate> predicates = new ArrayList<Predicate>();
				List<Long> idsAportesCorDev = new ArrayList<>();

				if (persona != null) {
					empresa = consultasCore.consultarEmpresa(persona.getIdPersona());
				}
				if (tipo != null && (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(tipo)
						|| TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(tipo))) {
					Long idEmpresa = 0L;
					if (empresa != null) {
						idEmpresa = empresa.getIdEmpresa();
					}
					idsAportesCorDev = consultasCore.consultarAportesConCorrecciones(idEmpresa, persona.getIdPersona(),
							consultaRecaudo.getTipoSolicitante(), consultaRecaudo.getPeriodoRecaudo(),
							consultaRecaudo.getFechaInicio(), consultaRecaudo.getFechaFin(), tipo);
					if (hayParametros && consultaRecaudo.getListMetodoRecaudo() != null
							&& !consultaRecaudo.getListMetodoRecaudo().isEmpty()) {
						predicates.add(
								aporteGeneral.get("modalidadRecaudoAporte").in(consultaRecaudo.getListMetodoRecaudo()));
					}
					if (idsAportesCorDev != null && !idsAportesCorDev.isEmpty()) {
						predicates.add(aporteGeneral.get("id").in(idsAportesCorDev));
					} else {
						return cuentaAporteGeneralDTOs;
					}
				} else {
					if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(consultaRecaudo.getTipoSolicitante())
							&& persona != null && persona.getIdPersona() != null && empresa != null) {
						predicates.add(cb.equal(aporteGeneral.get("idEmpresa"), empresa.getIdEmpresa()));
					} else if (persona != null && persona.getIdPersona() != null) {
						predicates.add(cb.equal(aporteGeneral.get("idPersona"), persona.getIdPersona()));
					}
					if (consultaRecaudo.getTipoSolicitante() != null) {
						predicates.add(
								cb.equal(aporteGeneral.get("tipoSolicitante"), consultaRecaudo.getTipoSolicitante()));
					}
					if (hayParametros) {
						if (consultaRecaudo.getListMetodoRecaudo() != null
								&& !consultaRecaudo.getListMetodoRecaudo().isEmpty()) {
							predicates.add(aporteGeneral.get("modalidadRecaudoAporte")
									.in(consultaRecaudo.getListMetodoRecaudo()));
						}

						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
						/* validacion cuando es empleador presencial */
						if (TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(tipo)
								&& consultaRecaudo.getPeriodoRecaudo() != null
								&& consultaRecaudo.getPeriodoFin() != null) {
							String periodoInicialFormat = null;
							String periodoFinalFormat = null;
							periodoInicialFormat = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
							periodoFinalFormat = dateFormat.format(new Date(consultaRecaudo.getPeriodoFin()));
							predicates.add(cb.between(aporteGeneral.get("periodoAporte"), periodoInicialFormat,
									periodoFinalFormat));
						} else if (consultaRecaudo.getPeriodoRecaudo() != null) {
							String fechaPagoString = null;
							fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
							predicates
									.add(cb.greaterThanOrEqualTo(aporteGeneral.get("periodoAporte"), fechaPagoString));
						} else if (consultaRecaudo.getPeriodoFin() != null) {
							String fechaPagoString = null;
							fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoFin()));
							predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("periodoAporte"), fechaPagoString));
						}

						if (consultaRecaudo.getFechaInicio() != null && consultaRecaudo.getFechaFin() != null) {
							Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
							Date fechaFinDate = new Date(consultaRecaudo.getFechaFin());
							predicates.add(
									cb.between(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate, fechaFinDate));
						} else if (consultaRecaudo.getFechaInicio() != null) {
							Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
							predicates.add(
									cb.greaterThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate));
						} else if (consultaRecaudo.getFechaFin() != null) {
							Date fechaFinDate = new Date(consultaRecaudo.getFechaFin());
							predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaFinDate));
						}
					}

					if (consultaRecaudo.getIdAporteGeneral() != null) {
						predicates.add(cb.equal(aporteGeneral.get("id"), consultaRecaudo.getIdAporteGeneral()));
					}
				}

				c.select(aporteGeneral).where(predicates.toArray(new Predicate[] {}));
				aportesGenerales = consultasCore.obtenerListaAportes(c);
				if (aportesGenerales != null && !aportesGenerales.isEmpty()) {
					aportesGenerales.sort(Comparator.comparing(AporteGeneral::getFechaProcesamiento)
							.thenComparing(AporteGeneral::getPeriodoAporte).reversed());
					if (!hayParametros && aportesGenerales.size() > 24) {
						aportesGenerales = aportesGenerales.subList(0, 24);
					}
				}
				List<Long> idsAporte = new ArrayList<>();
				for (AporteGeneral aporte : aportesGenerales) {
					idsAporte.add(aporte.getId());
				}
				List<AporteGeneralModeloDTO> aportesGeneralesDTO = new ArrayList<>();
				if (!idsAporte.isEmpty()) {
					aportesGeneralesDTO = consultasCore.consultarAporteYMovimiento(idsAporte);
				}

				if (persona != null) {
					if (persona.getRazonSocial() == null || persona.getRazonSocial().isEmpty()) {
						StringBuilder nombreAportante = new StringBuilder();
						nombreAportante.append(persona.getPrimerNombre() + " ");
						nombreAportante
								.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre() + " " : "");
						nombreAportante.append(persona.getPrimerApellido() + " ");
						nombreAportante
								.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
						consultaRecaudo.setNombreCompleto(nombreAportante.toString());

					} else {
						consultaRecaudo.setNombreCompleto(persona.getRazonSocial());
					}
				}

				List<AnalisisDevolucionDTO> analisisDevolucion = obtenerAnalisisDevolucionDTO(aportesGeneralesDTO,
						consultaRecaudo);

				if (analisisDevolucion != null && !analisisDevolucion.isEmpty()) {

					List<CuentaAporteDTO> cuentaRecuadoDevolucionCorrecion = consultarCuentaAporte(idPersonaCotizante,
							analisisDevolucion);
					if (tipo != null) {
						if (cuentaRecuadoDevolucionCorrecion != null && !cuentaRecuadoDevolucionCorrecion.isEmpty()) {

							for (CuentaAporteDTO cuentaAporteDTO : cuentaRecuadoDevolucionCorrecion) {
								if (tipo.RECAUDO_MANUAL.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
										|| TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES
												.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())
										|| TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO
												.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {

									/*
									 * Se arma la lista de cuenta de aportes
									 * para solo recuados
									 */
									/* Se consulta la solicitud de aporte */
									SolicitudAporteModeloDTO solicitudAporteModeloDTO = consultasCore
											.consultarSolicitudAporteVista360Empleador(
													cuentaAporteDTO.getIdAporteGeneral());
									cuentaAporteDTO.setSolicitudAporteModeloDTO(solicitudAporteModeloDTO);
									cuentaAporteRecuadoDTOs.add(cuentaAporteDTO);
								} else if (tipo.DEVOLUCION_APORTES.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
									/* Se consulta la solicitud de devolucion */
									SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO = consultasCore
											.consultarSolicitudDevolucionVista360(cuentaAporteDTO.getIdAporteGeneral(),
													cuentaAporteDTO.getIdMovimientoAporte());
									cuentaAporteDTO
											.setSolicitudDevolucionAporteModeloDTO(solicitudDevolucionAporteModeloDTO);
									cuentaAporteDevolucionDTOs.add(cuentaAporteDTO);
								} else if (tipo.CORRECCION_APORTES.equals(cuentaAporteDTO.getTipoMovimientoRecaudo())) {
									List<Long> idsAportes = new ArrayList<>();
									idsAportes.add(cuentaAporteDTO.getIdAporteGeneral());
									/* Se consulta la solicitud de correcion */
									Map<Long, List<SolicitudCorreccionAporteModeloDTO>> solicitudesCorreccionAporte = consultasCore
											.consultarSolicitudCorrecionVista360(idsAportes);

									cuentaAporteDTO.setSolicitudCorreccionAporteModeloDTO(
											solicitudesCorreccionAporte.get(cuentaAporteDTO.getIdAporteGeneral()));
									cuentaAporteCorrecionDTOs.add(cuentaAporteDTO);
								}
							}
						}
					} else {
						cuentaAporteGeneralDTOs.addAll(cuentaRecuadoDevolucionCorrecion);
					}

				}
				logger.debug(
						"Finaliza método consultarRecaudoDevolucionCorreccionVista360Persona(ConsultarRecaudoDTO consultaRecaudo,TipoMovimientoRecaudoAporteEnum tipo)");
				if (TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(tipo)) {
					cuentaAporteGeneralDTOs.addAll(cuentaAporteRecuadoDTOs);
				} else if (TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.equals(tipo)) {
					cuentaAporteGeneralDTOs.addAll(cuentaAporteDevolucionDTOs);
				} else if (TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(tipo)) {
					cuentaAporteGeneralDTOs.addAll(cuentaAporteCorrecionDTOs);
				}
				if (hayParametros != null && !hayParametros && cuentaAporteGeneralDTOs.size() > 24) {
					cuentaAporteGeneralDTOs = cuentaAporteGeneralDTOs.subList(0, 24);
				}

				return cuentaAporteGeneralDTOs;
			}
		} catch (Exception e) {
			logger.error("Ocurrio un error validando la existencia de la solicitud", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitudDevolucion(java.util.List)
	 */
	@Override
	public List<DevolucionVistasDTO> consultarSolicitudDevolucion(List<Long> idsAporteGeneral) {
		try {
			logger.debug("Inicia servicio consultarSolicitudDevolucionAporteIdGeneral");
			List<DevolucionVistasDTO> devoluciones = consultasCore.consultarSolicitudDevolucion(idsAporteGeneral);
			logger.debug("Finaliza servicio consultarSolicitudDevolucionAporteIdGeneral");
			return devoluciones;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarSolicitudDevolucionAporteIdGeneral", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	private void listadoCotizante(Long idRegistroGeneral, Map<String, String> comunicado, TipoAfiliadoEnum tipoAfiliado,
			TipoIdentificacionEnum tipoId, String numId) {
		String firmaMetodo = "AportesBusiness.listadoCotizante(Long, Map<String, String>, TipoAfiliadoEnum, TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		StringBuilder cotizante = new StringBuilder();
		cotizante.append(
				"<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>Tipo Identificación</th><th>Número Identificación</th><th>Nombre</th></tr>");
		List<RegistroDetalladoModeloDTO> registroDetallado = consultarRegistroDetalladoPorTipo(idRegistroGeneral,
				tipoAfiliado, tipoId, numId);
		for (RegistroDetalladoModeloDTO registroDetalladoModeloDTO : registroDetallado) {
			cotizante.append("<tr>");
			cotizante.append("<td>" + registroDetalladoModeloDTO.getTipoIdentificacionCotizante() + "</td>");
			cotizante.append("<td>" + registroDetalladoModeloDTO.getNumeroIdentificacionCotizante() + "</td>");
			cotizante.append("<td>" + registroDetalladoModeloDTO.getPrimerApellido() + " "
					+ registroDetalladoModeloDTO.getSegundoApellido() + " "
					+ registroDetalladoModeloDTO.getPrimerNombre() + " " + registroDetalladoModeloDTO.getSegundoNombre()
					+ "</td>");

			cotizante.append("</tr>");
		}
		cotizante.append("</table>");
		comunicado.put("Cotizantes", cotizante.toString());

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

    private void listadoCotizanteNoOk(Long idRegistroGeneral, Map<String, String> comunicado, TipoAfiliadoEnum tipoAfiliado,
                                       TipoIdentificacionEnum tipoId, String numId) {
        String firmaMetodo = "AportesBusiness.listadoCotizanteNoOk(Long, Map<String, String>, TipoAfiliadoEnum, TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StringBuilder cotizante = new StringBuilder();
        cotizante.append(
                "<style>table{border-collapse:collapse;width:100%;font-size: 10px;}th,td{border:1px solid black;padding: 4px;}</style><table><tr><th>Tipo Identificación</th><th>Número Identificación</th><th>Nombre</th><th>Tipo de Inconsistencia</th></tr>");

        List<RegistroDetalladoModeloDTO> registroDetallado = consultarRegistroDetalladoPorTipo(idRegistroGeneral,
                tipoAfiliado, tipoId, numId);

        List<Map.Entry<RegistroDetalladoModeloDTO, String>> cotizantesConInconsistencia = new ArrayList<>();

        for (RegistroDetalladoModeloDTO registroDetalladoModeloDTO : registroDetallado) {
            String tipoInconsistencia = "Sin Inconsistencia";

            if (registroDetalladoModeloDTO.getNovIngreso() != null) {
                tipoInconsistencia = "Tiene una Novedad de Ingreso";
            } else if (registroDetalladoModeloDTO.getNovRetiro() != null) {
                tipoInconsistencia = "Tiene una Novedad de Retiro";
            } else if (registroDetalladoModeloDTO.getNovVSP() != null) {
                tipoInconsistencia = "Tiene una Novedad de Variación permanente de salario.";
            } else if (registroDetalladoModeloDTO.getNovVST() != null) {
                tipoInconsistencia = "Tiene una Novedad de Variación transitoria del salario";
            } else if (registroDetalladoModeloDTO.getNovSLN() != null) {
                tipoInconsistencia = "Tiene una Novedad de Suspensión temporal del contrato de trabajo, licencia no remunerada o comisión de servicios.";
            } else if (registroDetalladoModeloDTO.getNovIGE() != null) {
                tipoInconsistencia = "Tiene una Novedad de Incapacidad Temporal por Enfermedad General.";
            } else if (registroDetalladoModeloDTO.getNovLMA() != null) {
                tipoInconsistencia = "Tiene una Novedad de Licencia de Maternidad o paternidad.";
            } else if (registroDetalladoModeloDTO.getNovVACLR() != null) {
                tipoInconsistencia = "Tiene una Novedad de Vacaciones, Licencia remunerada";
            } else if (registroDetalladoModeloDTO.getNovSUS() != null) {
                tipoInconsistencia = "Tiene una Novedad de Suspensión";
            }

            if (!"Sin Inconsistencia".equals(tipoInconsistencia)) {
                cotizantesConInconsistencia.add(new AbstractMap.SimpleEntry<>(registroDetalladoModeloDTO, tipoInconsistencia));
            }
        }

        Collections.sort(cotizantesConInconsistencia, new Comparator<Map.Entry<RegistroDetalladoModeloDTO, String>>() {
            @Override
            public int compare(Map.Entry<RegistroDetalladoModeloDTO, String> entry1, Map.Entry<RegistroDetalladoModeloDTO, String> entry2) {
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });

        for (Map.Entry<RegistroDetalladoModeloDTO, String> entry : cotizantesConInconsistencia) {
            RegistroDetalladoModeloDTO registroDetalladoModeloDTO = entry.getKey();
            String tipoInconsistencia = entry.getValue();

            cotizante.append("<tr>");

            cotizante.append("<td>" + registroDetalladoModeloDTO.getTipoIdentificacionCotizante().toString().toUpperCase() + "</td>");
            
            cotizante.append("<td>" + registroDetalladoModeloDTO.getNumeroIdentificacionCotizante() + "</td>");

            StringBuilder nombreCompletoBuilder = new StringBuilder();
            
            if (registroDetalladoModeloDTO.getPrimerApellido() != null && !registroDetalladoModeloDTO.getPrimerApellido().trim().isEmpty()) {
                nombreCompletoBuilder.append(capitalizeFirstLetter(registroDetalladoModeloDTO.getPrimerApellido()));
            }
            if (registroDetalladoModeloDTO.getSegundoApellido() != null && !registroDetalladoModeloDTO.getSegundoApellido().trim().isEmpty()) {
                if (nombreCompletoBuilder.length() > 0) nombreCompletoBuilder.append(" ");
                nombreCompletoBuilder.append(capitalizeFirstLetter(registroDetalladoModeloDTO.getSegundoApellido()));
            }
            if (registroDetalladoModeloDTO.getPrimerNombre() != null && !registroDetalladoModeloDTO.getPrimerNombre().trim().isEmpty()) {
                if (nombreCompletoBuilder.length() > 0) nombreCompletoBuilder.append(" ");
                nombreCompletoBuilder.append(capitalizeFirstLetter(registroDetalladoModeloDTO.getPrimerNombre()));
            }
            if (registroDetalladoModeloDTO.getSegundoNombre() != null && !registroDetalladoModeloDTO.getSegundoNombre().trim().isEmpty()) {
                if (nombreCompletoBuilder.length() > 0) nombreCompletoBuilder.append(" ");
                nombreCompletoBuilder.append(capitalizeFirstLetter(registroDetalladoModeloDTO.getSegundoNombre()));
            }
            cotizante.append("<td>" + nombreCompletoBuilder.toString() + "</td>");

            cotizante.append("<td>" + tipoInconsistencia + "</td>");

            cotizante.append("</tr>");
        }

        cotizante.append("</table>");
        comunicado.put("CotizantesNoOk", cotizante.toString());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método auxiliar para capitalizar
     */
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        // Capitaliza la primera letra y convierte el resto a minúsculas
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDatosSolicitudCorreccion(java.util.List)
	 */
	@Override
	public List<CorreccionVistasDTO> consultarDatosSolicitudCorreccion(List<Long> idsAporteGeneral) {
		try {
			logger.debug("Inicia servicio consultarDatosSolicitudCorreccion");
			List<CorreccionVistasDTO> correcciones = consultasCore.consultarDatosSolicitudCorreccion(idsAporteGeneral);
			logger.debug("Finaliza servicio consultarDatosSolicitudCorreccion");
			return correcciones;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarDatosSolicitudCorreccion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	private Long getIdPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null, null, null, null, numeroIdentificacion,
				tipoIdentificacion, null);
		buscarPersonas.execute();
		if (buscarPersonas.getResult() == null) {
			return 0L;
		}
		return buscarPersonas.getResult().get(0).getIdPersona();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#eliminarRegistrosDetalladosPorRegistroGeneral(java.lang.Long)
	 */
	@Override
	public void eliminarRegistrosDetalladosPorRegistroGeneral(Long idRegistroGeneral) {
		String firmaServicio = "AportesBusiness.elminarRegistrosDetalladosPorRegistroGeneral(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasStaging.eliminarRegistrosDetalladosPorRG(idRegistroGeneral);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#eliminarRegistroGeneralPorId(java.lang.Long)
	 */
	@Override
	public void eliminarRegistroGeneralPorId(Long idRegistroGeneral) {
		String firmaServicio = "AportesBusiness.eliminarRegistroGeneralPorId(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasStaging.eliminarRegistroGeneralId(idRegistroGeneral);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#prepararProcesoRegistroAportes(java.util.List)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Deprecated
	public DatosPersistenciaAportesDTO prepararProcesoRegistroAportes(List<AporteDTO> aportes) {
		return prepararDatosProcesoRegistroAportes(aportes);
	}
	
	private DatosPersistenciaAportesDTO prepararDatosProcesoRegistroAportes(List<AporteDTO> aportes) {
		String firmaServicio = "AportesBusiness.prepararDatosProcesoRegistroAportes(List<AporteDTO>)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " :: Inicia preparación para " + aportes.size()
				+ " aportes");

		DatosPersistenciaAportesDTO resultDatosPersistenciaAportesDTO = new DatosPersistenciaAportesDTO();
		Map<String, List> listasIds = FuncionesUtilitarias.obtenerListadosRegistros(aportes);
		List<String> personasAportantes = FuncionesUtilitarias.construirListadoIdsPersonas(aportes, 1);
		List<String> personasCotizantes = FuncionesUtilitarias.construirListadoIdsPersonas(aportes, 2);
		List<String> personasTramitadoras = FuncionesUtilitarias.construirListadoIdsPersonas(aportes, 3);
		List<String> sucursales = FuncionesUtilitarias.construirListadoLlavesSucursales(aportes);

		/*
		 * se consultan todos los aportes generales asociados al registro
		 * general y los ID de los registros de TemAporteProcesado y el mapa de
		 * la presencia de novedades por registro general
		 */
		if (!listasIds.get(ConstantesDatosAportesPila.LISTA_ID_REGGEN_INPUT).isEmpty()) {
			resultDatosPersistenciaAportesDTO.setAportesGenerales(consultasCore
					.consultarAportesGenerales(listasIds.get(ConstantesDatosAportesPila.LISTA_ID_REGGEN_INPUT), null));

			resultDatosPersistenciaAportesDTO.setIdsTemAporteProcesado(consultasPila
					.consultarIdsTemAporteProcesado(listasIds.get(ConstantesDatosAportesPila.LISTA_ID_REGGEN_INPUT)));

			// TODO: validar necesidad de este dato
			resultDatosPersistenciaAportesDTO.setMapaPresenciaNovedades(consultasStaging.consultarPresenciaNovedadesPorRegistroGeneral(
					listasIds.get(ConstantesDatosAportesPila.LISTA_ID_REGGEN_INPUT)));
		}

		// se consultan todos los aportes detallados asociados a los registros
		// detallados
		if (!listasIds.get(ConstantesDatosAportesPila.LISTA_ID_REGDET_INPUT).isEmpty()) {
			resultDatosPersistenciaAportesDTO.setAportesDetallados(consultasCore
					.consultarAportesDetallados(listasIds.get(ConstantesDatosAportesPila.LISTA_ID_REGDET_INPUT)));
		}

		// se consultan todos los códigos de municipios
		if (!listasIds.get(ConstantesDatosAportesPila.LISTA_COD_MUNICIPIOS_INPUT).isEmpty()) {
			resultDatosPersistenciaAportesDTO.setMunicipios(consultarMunicipiosPorCodigos(
					listasIds.get(ConstantesDatosAportesPila.LISTA_COD_MUNICIPIOS_INPUT)));
		}

		List<PersonaModeloDTO> personas = new ArrayList<PersonaModeloDTO>();
		
		// se consultan todas las personas aportantes
		if (!personasAportantes.isEmpty()) {
			personas = consultasCore.consultarPersonasPorListado(personasAportantes);
		}
		resultDatosPersistenciaAportesDTO.setPersonasAportantes(personas);

		// se consultan todas las personas cotizantes
		if (!personasCotizantes.isEmpty()) {
			personas = consultasCore.consultarPersonasPorListado(personasCotizantes);
		}
		resultDatosPersistenciaAportesDTO.setPersonasCotizantes(personas);

		// se consultan todas las personas tramitadoras
		if (!personasTramitadoras.isEmpty()) {
			personas = consultasCore.consultarPersonasPorListado(personasTramitadoras);
		}
		resultDatosPersistenciaAportesDTO.setPersonasTramitadoras(personas);

		List<Long> idsPersonasAportantes = new ArrayList<>();
		for (PersonaModeloDTO persona : resultDatosPersistenciaAportesDTO.getPersonasAportantes()) {
			idsPersonasAportantes.add(persona.getIdPersona());
		}

		List<Long> idsPersonasCotizantes = new ArrayList<>();
		for (PersonaModeloDTO persona : resultDatosPersistenciaAportesDTO.getPersonasCotizantes()) {
			idsPersonasCotizantes.add(persona.getIdPersona());
		}

		List<Long> idsPersonasTramitadoras = new ArrayList<>();
		for (PersonaModeloDTO persona : resultDatosPersistenciaAportesDTO.getPersonasTramitadoras()) {
			idsPersonasTramitadoras.add(persona.getIdPersona());
		}

		List<Long> idsAfiliadosCotizantes = new ArrayList<>();
		List<Long> idsEmpresasAportantes = new ArrayList<>();

		// se consultan las empresas asociadas a las personas aportantes
		if (!idsPersonasAportantes.isEmpty()) {
			resultDatosPersistenciaAportesDTO.setEmpresasAportantes(consultasCore.consultarEmpresasPorIdsPersonas(idsPersonasAportantes));

			for (EmpresaModeloDTO empresa : resultDatosPersistenciaAportesDTO.getEmpresasAportantes()) {
				idsEmpresasAportantes.add(empresa.getIdEmpresa());
			}
		}

		// se consultan los empleadores de las empresas aportantes
		if (!idsEmpresasAportantes.isEmpty()) {
			resultDatosPersistenciaAportesDTO.setEmpleadoresAportantes(consultasCore.consultarEmpleadoresPorIdsEmpresas(idsEmpresasAportantes));
		}

		// se consultan las sucursales asociadas a los aportes
		if (!sucursales.isEmpty()) {
			resultDatosPersistenciaAportesDTO.setSucursales(consultasCore.consultarSucursalesPorLlaves(sucursales));
		}

		// se consultan las empresas asociadas a las personas tramitadoras
		if (!idsPersonasTramitadoras.isEmpty()) {
			List<EmpresaModeloDTO> empresasTramitadoras = consultasCore
					.consultarEmpresasPorIdsPersonas(idsPersonasTramitadoras);
			resultDatosPersistenciaAportesDTO.getEmpresasAportantes().addAll(empresasTramitadoras);
		}

		// se consultan los afiliados asociados a las personas cotizantes
		if (!idsPersonasCotizantes.isEmpty()) {
			resultDatosPersistenciaAportesDTO.setAfiliadosCotizantes(consultasCore.consultarAfiliadosPorPersona(idsPersonasCotizantes));

			for (AfiliadoModeloDTO afiliado : resultDatosPersistenciaAportesDTO.getAfiliadosCotizantes()) {
				idsAfiliadosCotizantes.add(afiliado.getIdAfiliado());
			}

			// se consultan las personas en listados que presenten registro de
			// inconsistencia
			resultDatosPersistenciaAportesDTO.setIdsPersonasConInconsistencia(
					consultasCore.consultarPersonasInconsistentes(idsPersonasCotizantes));
		}

		// se consultan los roles afiliado de los afiliados cotizantes
		if (!idsAfiliadosCotizantes.isEmpty()) {
			resultDatosPersistenciaAportesDTO.setRolesAfiliadosCotizantes(consultasCore.consultarRolesAfiliadosPorPersona(idsAfiliadosCotizantes));
		}

		// se consulta el código del operador de información del aporte
		if (!listasIds.get(ConstantesDatosAportesPila.LISTA_COD_OI_INPUT).isEmpty()) {
			resultDatosPersistenciaAportesDTO.setOperadoresInformacion(consultasCore.consultarOperadoresInformacionPorCodigos(
					listasIds.get(ConstantesDatosAportesPila.LISTA_COD_OI_INPUT)));
		}

		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return resultDatosPersistenciaAportesDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#procesarPaqueteAportesGenerales(java.util.Map)
	 */
	@Override
	@Deprecated
	public Map<String, Long> procesarPaqueteAportesGenerales(Map<String, AporteGeneralModeloDTO> aportesGenerales) {
		String firmaServicio = "AportesBusiness.procesarPaqueteAportesGenerales(Map<String, AporteGeneralModeloDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		// se solicita la creación de los aportes generales
		Map<String, Long> llavesPersistidas = consultasCore.registrarAportesGenerales(aportesGenerales);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return llavesPersistidas;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#procesarPaqueteAportesDetallados(com.asopagos.aportes.dto.PaqueteProcesoAportesDTO)
	 */
	@Override
	public List<Long> procesarPaqueteAportesDetallados(List<JuegoAporteMovimientoDTO> aportesDetallados) {
		String firmaServicio = "AportesBusiness.procesarPaqueteAportesDetallados(List<AporteDetalladoModeloDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<Long> listaRegistrosProcesados = consultasCore.registrarAportesDetallados(aportesDetallados);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return listaRegistrosProcesados;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarNovedadesPorRegistroDetallado(java.util.List)
	 */
	@Override
	public ResultadoConsultaNovedadesExistentesDTO consultarNovedadesPorRegistroDetallado(
			List<Long> registrosDetallados) {
		String firmaServicio = "AportesBusiness.consultarNovedadesPorRegistroDetallado(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		ResultadoConsultaNovedadesExistentesDTO result = new ResultadoConsultaNovedadesExistentesDTO();

		Map<Long, Set<TipoTransaccionEnum>> mapaResultado = new HashMap<>();

		List<Object[]> resultQuery = consultasCore.consultarNovedadesPorListaIdRegDet(registrosDetallados);

		// se recorren los resultados de la consulta para hacer el mapa de
		// salida
		if (resultQuery != null && !resultQuery.isEmpty()) {
			for (Object[] linea : resultQuery) {
				Long idRegistroDetallado = Long.valueOf(linea[0].toString());
				String tipoTransaccion = (String) linea[1];
				TipoTransaccionEnum tipoTransaccionEnum = TipoTransaccionEnum.valueOf(tipoTransaccion);

				Set<TipoTransaccionEnum> setTransacciones = mapaResultado.get(idRegistroDetallado);

				if (setTransacciones == null) {
					setTransacciones = new HashSet<>();
					mapaResultado.put(idRegistroDetallado, setTransacciones);
				}

				setTransacciones.add(tipoTransaccionEnum);
			}
		}

		result.setNovedadesRegistro(mapaResultado);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#crearTemAporteProcesadosNuevos(java.util.List)
	 */
	@Override
	public void crearTemAporteProcesadosNuevos(List<ConsultaPresenciaNovedadesDTO> listaPresneciaNovedades) {
		String firmaServicio = "AportesBusiness.crearTemAporteProcesadosNuevos(List<ConsultaPresenciaNovedadesDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.crearTemAporteProcesado(listaPresneciaNovedades);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarSolicitudesCorreccion(java.util.List)
	 */
	@Override
	public DatosConsultaSolicitudesAporDevCorDTO consultarSolicitudesCorreccion(List<Long> idsAporteGeneral) {
		String firmaServicio = "AportesBusiness.consultarSolicitudesCorreccion(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		DatosConsultaSolicitudesAporDevCorDTO result = new DatosConsultaSolicitudesAporDevCorDTO();

		result.setSolicitudesAporte(consultasCore.consultarSolicitudAportePorListaIdAporte(idsAporteGeneral));
		result.setSolicitudesDevolucion(consultasCore.consultarSolicitudDevolucionVista360ListaIds(idsAporteGeneral));
		result.setSolicitudesCorreccion(consultasCore.consultarSolicitudCorrecionVista360(idsAporteGeneral));

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	@Override
	public List<HistoricoAportes360DTO> consultarHistoricoAportesAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
			String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado, Long idEmpresa) {
		String firmaServicio = "AportesBusiness.consultarHistoricoAportesAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum, Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<HistoricoAportes360DTO> result = consultasCore.consultarHistoricoAportesPorTipoAfiliacion(tipoIdAfiliado,
				numeroIdAfiliado, tipoAfiliado, idEmpresa);

		if (result != null && !result.isEmpty()) {
			List<Long> idsRegGen = new ArrayList<>();
			for (HistoricoAportes360DTO resgistro : result) {
				if (!ModalidadRecaudoAporteEnum.MANUAL.equals(resgistro.getMetodoRecaudo())) {
					idsRegGen.add(resgistro.getIdRegistroGeneral());
				}
			}

			if (!idsRegGen.isEmpty()) {
				Map<Long, RegistroGeneralModeloDTO> regGens = consultasStaging
						.consultarRegistrosGeneralesPorListaId(idsRegGen);

				for (HistoricoAportes360DTO registro : result) {
					RegistroGeneralModeloDTO regGen = regGens.get(registro.getIdRegistroGeneral());

					if (regGen != null) {
						registro.setNumeroPlanilla(regGen.getNumPlanilla());
						registro.setTipoPlanilla(TipoPlanillaEnum.obtenerTipoPlanilla(regGen.getTipoPlanilla()));
						//CC vistas 360
//						if (regGen.getFechaRecaudo() != null) {
//						    registro.setFechaPago(new Date(regGen.getFechaRecaudo()));
//						}
					}
				}
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarAportesGeneralesPorIdRegGeneral(java.lang.Long)
	 */
	@Override
	public List<AporteGeneralModeloDTO> consultarAportesGeneralesPorIdRegGeneral(Long idRegistroGeneral) {
		String firmaServicio = "AportesBusiness.consultarAportesGeneralesPorIdRegGeneral(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AporteGeneralModeloDTO> result = consultasCore.consultarAportesGeneralesPorIdRegGen(idRegistroGeneral);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarInformacionPlanillasRegistrarProcesar()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionPlanillasRegistrarProcesar() {
		String firmaServicio = "AportesBusiness.consultarInformacionPlanillasRegistrarProcesar()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas = consultasPila
				.consultarInformacionAportesProcesar();
		
		// las planillas seleccionadas se marcan como en uso
		consultasPila.marcarTemporalEnProceso(infoPlanillas, Boolean.TRUE, Boolean.TRUE);
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoPlanillas;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarInformacionNovedadesRegistrarProcesar()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesRegistrarProcesar() {
		String firmaServicio = "AportesBusiness.consultarInformacionNovedadesRegistrarProcesar()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades = consultasPila
				.consultarInformacionNovedadesProcesar();
		
		// las planillas seleccionadas se marcan como en uso
		consultasPila.marcarTemporalEnProceso(infoNovedades, Boolean.FALSE, Boolean.TRUE);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoNovedades;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarAportesPlanillasRegistrarProcesar(java.lang.Long,
	 *      java.lang.Integer)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AporteDTO> consultarAportesPlanillasRegistrarProcesar(Long idRegistroGeneral, Integer pagina) {
		String firmaServicio = "AportesBusiness.consultarInformacionPlanillasRegistrarProcesar()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<AporteDTO> aportesDTO = consultasPila.consultarAportesPlanillasRegistrarProcesar(idRegistroGeneral,
				pagina, 1000);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return aportesDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarAportesPlanillasRegistrarProcesar(java.lang.Long,
	 *      java.lang.Integer)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<NovedadesProcesoAportesDTO> consultarNovedadesPlanillasRegistrarProcesar(Long planillaAProcesar,
			Integer pagina) {
		String firmaServicio = "AportesBusiness.consultarNovedadesPlanillasRegistrarProcesar()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Boolean resultado =false;
		Object respuesta = consultasPila.construirNovedadesPlanillasRegistrarProcesar(planillaAProcesar);
		logger.info("respuesta   "+ respuesta);
		if(respuesta.toString().equals("true") || respuesta.toString().equals("1")){
			resultado = true;
		}
		logger.info("planillaAProcesar " + planillaAProcesar);
		List<NovedadesProcesoAportesDTO> result = consultasPila
				.consultarNovedadesPlanillasRegistrarProcesar(planillaAProcesar, pagina);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#eliminarTemporalesAporte(java.util.List)
	 */
	@Override
	public void eliminarTemporalesAporte(List<Long> idsDetalle) {
		String firmaServicio = "AportesBusiness.eliminarTemporalesAporte(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.eliminarTemporales(Boolean.TRUE, idsDetalle);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#eliminarTemporalesNovedad(java.util.List)
	 */
	@Override
	public void eliminarTemporalesNovedad(List<Long> idsDetalle) {
		String firmaServicio = "AportesBusiness.eliminarTemporalesNovedad(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.eliminarTemporales(Boolean.FALSE, idsDetalle);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizarTemAporteProcesado()
	 */
	@Override
	public void actualizarTemAporteProcesado() {
		String firmaServicio = "AportesBusiness.actualizarTemAporteProcesado()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.actualizarTemAporteProcesado();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

	}

        /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizarTemAporteProcesado()
	 */
	@Override
	public void actualizarTemAporteProcesadoByIdPlanilla(Long idPlanilla) {
		String firmaServicio = "AportesBusiness.actualizarTemAporteProcesadoByIdPlanilla(idPlanilla)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.actualizarTemAporteProcesado();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

	}
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDatosComunicado()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicado() {
		String firmaServicio = "AportesBusiness.consultarDatosComunicado()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<DatosComunicadoPlanillaDTO> result = consultasPila.consultarDatosComunicado();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}
        
        /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarDatosComunicadoByIdPlanilla()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DatosComunicadoPlanillaDTO> consultarDatosComunicadoByIdPlanilla(Long idPlanilla) {
		String firmaServicio = "AportesBusiness.consultarDatosComunicadoByIdPlanilla()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<DatosComunicadoPlanillaDTO> result = consultasPila.consultarDatosComunicadoByIdPlanilla(idPlanilla);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#eliminarTemAporteProcesado(java.util.List)
	 */
	@Override
	public void eliminarTemAporteProcesado(List<Long> idsDetalle) {
		String firmaServicio = "AportesBusiness.eliminarTemAporteProcesado(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.eliminarTemAporteProcesado(idsDetalle);
		
		consultasStaging.eliminarControlPlanilla(idsDetalle);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#actualizarMarcaProcesoTemporales(java.util.List,
	 *      java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	public void actualizarMarcaProcesoTemporales(List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas,
			Boolean esAporte, Boolean enProceso) {
		String firmaServicio = "AportesBusiness.actualizarMarcaProcesoTemporales(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		consultasPila.marcarTemporalEnProceso(infoPlanillas, esAporte, enProceso);
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

	@Override
	public void actualizarCategoriaAfiliadosAporteFuturo(String fecha) {
		String firmaServicio = "AportesBusiness.actualizarCategoriaAfiliadosAporteFuturo(fecha)";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<Callable<Void>> tareasParalelas = new LinkedList<>();
		/*
		StringBuilder sb = new StringBuilder();
		if(fecha != null && !fecha.equals("") && (fecha.length() == 7 || fecha.length() == 10)){
			sb.append(fecha.length() == 7 ? fecha+"-01" : fecha);
			
			String periodo = fecha.length() == 7 ? fecha : fecha.substring(0, 7);
			String fechaActual = sb.toString();
			
			//if(LocalDate.now().isEqual(LocalDate.parse(fechaActual)) || LocalDate.now().isBefore(LocalDate.parse(fechaActual))){
				consultasCore.consultarPersonasAfiliadasConAporteFuturo(fechaActual, periodo);
			//}
			 
		}
		*/
		List<Object> idsRegistroDetallado = consultasCore.ejecutarCalculoCategoriaAportesFuturos();
		for(Object id : idsRegistroDetallado){
			Callable<Void> parallelTask = () -> {
                consultasCore.ejecutarCalculoCategoriaAportesFuturosSP(BigInteger.valueOf(Long.parseLong(id.toString())));
                return null;
            };
			tareasParalelas.add(parallelTask); 
		}
		try {
            mes.invokeAll(tareasParalelas);
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

	logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);

	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#ejecutarArmadoStaging(java.
	 *      lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarCalculoCategoriasMasiva() { 
		logger.debug("Inicio de método ejecutarArmadoStaging(Long idTransaccion)");
		consultasCore.ejecutarCalculoCategoriasMasiva(); 
		logger.debug("Fin de método ejecutarArmadoStaging(Long idTransaccion)");
	}
        
            
    /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarInformacionNovedadesRegistrarProcesarByIdPlanilla()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesRegistrarProcesarByIdPlanilla(Long idPlanilla, Boolean omitirMarca) {
		String firmaServicio = "AportesBusiness.consultarInformacionNovedadesRegistrarProcesarByIdPlanilla()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades = consultasPila
				.consultarInformacionNovedadesProcesarByIdPlanilla(idPlanilla);
		
		if(Boolean.FALSE.equals(omitirMarca) || omitirMarca == null) {
			// las planillas seleccionadas se marcan como en uso
			consultasPila.marcarTemporalEnProceso(infoNovedades, Boolean.FALSE, Boolean.TRUE);
		}
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoNovedades;
	}
	
	/**
	 * Método que consulta los municipios asociados a los cotizantes y aportantes de la planilla
	 * 
	 * @param codigosMunicipio
	 * @return
	 */
	private List<Municipio> consultarMunicipiosPorCodigos(List<String> codigosMunicipio) {
		// TODO: validar existenia para no consultar si ya se tienen para la siguente iteración
		return consultasCore.consultarMunicipiosPorCodigos(codigosMunicipio);
	}

	// AJUSTE REFACTOR PILA MAYO
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#prepararDatosRegistroAportes(java.lang.Long, java.lang.Integer)
	 */
	@Override
	//se usa?
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DatosPersistenciaAportesDTO prepararDatosRegistroAportes(Long idRegistroGeneral, Integer tamanoPaginador) {
		String firmaServicio = "AportesBusiness.prepararDatosRegistroAportes()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		
		List<AporteDTO> aportesDTO = new ArrayList<>();
		List<AporteDTO> aportesDTOtmp;
		int pagina = 0;
		do {
			aportesDTOtmp = consultasPila.consultarAportesPlanillasRegistrarProcesar(idRegistroGeneral, pagina, tamanoPaginador);
			aportesDTO.addAll(aportesDTOtmp);
			pagina++;
		} while (!aportesDTOtmp.isEmpty());

		// TODO : paginar las consultas internas
		DatosPersistenciaAportesDTO result = prepararDatosProcesoRegistroAportes(aportesDTO);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result; 
	}
	

	/**
     * Clase que modifica la interes de mora de aporte
     */
    @Override
    public ResultadoModificarTasaInteresDTO modificarTasaInteresMoraAportes(ModificarTasaInteresMoraDTO tasaModificada) {
        String firmaServicio = "modificarTasaInteresMoraAportes(ModificarTasaInteresMoraDTO tasaModificada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        ResultadoModificarTasaInteresDTO resultadoExitoso;
        
        resultadoExitoso = consultasCore.actualizarTasaInteresMora(tasaModificada);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoExitoso;
    }
    
    @Override
    public List<TasasInteresMoraModeloDTO> consultarTasasInteresMoraAportes() {
        String firmaServicio = "consultarTasasInteresMoraAportes()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<TasasInteresMoraModeloDTO> tasas = new ArrayList<>();
        
        tasas = consultasCore.consultarTasasInteresMora();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return tasas;
    }
    
    @Override
    public Boolean crearTasaInteresInteresMora(ModificarTasaInteresMoraDTO nuevaTasa) {
        String firmaServicio = "consultarTasasInteresMoraAportes()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        Boolean result = consultasCore.crearTasaInteresMora(nuevaTasa);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    @Override
    public SolicitudCierreRecaudoModeloDTO generarArchivoCierreRecaudo(GeneracionArchivoCierreDTO generacionArchivo) {

		logger.info( "[ Cierre de Recaudo  ] - Proceso de generacion del archivo concilacion ");
        String firmaServicio = "generarArchivoCierreRecaudo(GeneracionArchivoCierreDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        SolicitudCierreRecaudoModeloDTO solicitudCierreDTO = new SolicitudCierreRecaudoModeloDTO();
        RegistrosArchivoAporteDTO registro = new RegistrosArchivoAporteDTO();
        List<DetalleRegistroDTO> detallePorRegistro = consultarResultadoRegistroRecaudoAportes(generacionArchivo.getFechaInicio(), generacionArchivo.getFechaFin());
        
        registro.setResumenCierreRecaudo(generacionArchivo.getResumenRecaudo());
        registro.setDetallePorRegistro(detallePorRegistro);
        
		logger.info( "[ Cierre de Recaudo  ] -  Proceso de generacion de .zip");
        ArchivoCierreDTO archivoExcel = ArchivoAportesUtil.generarReporteCierreRecaudo(registro);
		logger.info("archivoExcel " +archivoExcel);
        byte[] archivo = archivoExcel.getArchivo();
        solicitudCierreDTO.setIdsAportesGenerales(archivoExcel.getIdsAporteGeneral());
        InformacionArchivoDTO info = new InformacionArchivoDTO();
        info.setDataFile(archivo);
		logger.info("archivo " +archivo);
        info.setFileType("application/zip");
        info.setProcessName(ProcesoEnum.CIERRE_RECAUDO.name());
        info.setDescription("CierreRecaudoAportes");
        info.setDocName("ConciliacionAportes.zip");
        info.setFileName("ConciliacionAportes.zip");
        solicitudCierreDTO.setIdEcm(almacenarArchivo(info));

		logger.info("solicitudCierreDTO setIdEcm " +solicitudCierreDTO.getIdEcm());

        return solicitudCierreDTO;
    }
    
    /**
     * Almacena en el ecm excel que se genera en el cierre de recaudo
     * 
     * @param infoFile
     * @return String con el identificador para el ecm
     */
    private String almacenarArchivo(InformacionArchivoDTO infoFile) {
        logger.debug("Inicia almacenarArchivo(InformacionArchivoDTO infoFile)");
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
        almacenarArchivo.execute();

        InformacionArchivoDTO archivo = almacenarArchivo.getResult();
        StringBuilder idECM = new StringBuilder();
        idECM.append(archivo.getIdentificadorDocumento());
        idECM.append("_");
        idECM.append(archivo.getVersionDocumento());
        logger.debug("Finaliza almacenarArchivo(InformacionArchivoDTO infoFile)");
		logger.info("idECM.toString() " +idECM.toString());
        return idECM.toString();
    }
	@Override
	public void cargarAutomaticamenteArchivosCrucesAportesAutomatico() {
        logger.info("Inicia almacenarArchivo(InformacionArchivoDTO infoFile)");
        CargarAutomaticamenteArchivosCrucesAportes cargarAutomaticamenteArchivosCrucesAportes = new CargarAutomaticamenteArchivosCrucesAportes();
        cargarAutomaticamenteArchivosCrucesAportes.execute();

    }
    
    /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#consultarInformacionNovedadesRegistrarProcesarFuturas()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesRegistrarProcesarFuturas() {
		String firmaServicio = "AportesBusiness.consultarInformacionNovedadesRegistrarProcesarFuturas()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades = consultasPila
				.consultarInformacionNovedadesProcesarFuturas();
		
		// las planillas seleccionadas se marcan como en uso
		consultasPila.marcarTemporalEnProceso(infoNovedades, Boolean.FALSE, Boolean.TRUE);
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoNovedades;
	}

		/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.service.AportesService#procesarPaqueteAportesDetallados(com.asopagos.aportes.dto.PaqueteProcesoAportesDTO)
	 */
	@Override
	public Long buscarNotificacionPlanillasN(Long idRegistroGeneral) {
		String firmaServicio = "AportesBusiness.buscarNotificacionPlanillasN(List<AporteDetalladoModeloDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		Long cantidad = consultasPila.buscarNotificacionPlanillasNCore(idRegistroGeneral);
		logger.info("**__**Cantidad buscarNotificacionPlanillasN cantidad: "+cantidad +" idRegistroGeneral: "+idRegistroGeneral);
		logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return cantidad;
	}


	@Override
	public List<Object[]>  consultarNovedadesYaProcesadasCORE(List<Long> registrosDetallados) {
		String firmaServicio = "AportesBusiness.consultarNovedadesPorRegistroDetallado(List<Long>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		Map<Long, Set<TipoTransaccionEnum>> mapaResultado = new HashMap<>();

		List<Object[]> resultQuery = consultasCore.consultarNovedadesPorListaIdRegDet(registrosDetallados);

		// se recorren los resultados de la consulta para hacer el mapa de
		// salida
		/*if (resultQuery != null && !resultQuery.isEmpty()) {
			for (Object[] linea : resultQuery) {
				Long idRegistroDetallado = Long.valueOf(linea[0].toString());
				String tipoTransaccion = (String) linea[1];
				TipoTransaccionEnum tipoTransaccionEnum = TipoTransaccionEnum.valueOf(tipoTransaccion);

				Set<TipoTransaccionEnum> setTransacciones = mapaResultado.get(idRegistroDetallado);

				if (setTransacciones == null) {
					setTransacciones = new HashSet<>();
					mapaResultado.put(idRegistroDetallado, setTransacciones);
				}

				setTransacciones.add(tipoTransaccionEnum);
			}
		}

		result.setNovedadesRegistro(mapaResultado);
*/
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return resultQuery;

	}


	//Rendimiento Consulta Cuenta Aporte para limitra de a 10, insumo para la vista, se duplico
	@Override
	public List<CuentaAporteDTO> consultarCuentaAporteVistaBoton(Long idPersonaCotizante,List<AnalisisDevolucionDTO> analisisDevolucionDTO, UriInfo uri, HttpServletResponse response, TipoMovimientoRecaudoAporteEnum tipo) {
		String firmaServicio = "AportesBusiness.consultarCuentaAporte(Long, List<AnalisisDevolucionDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		//logger.info("consultarCuentaAporte_1");
		logger.info("-----------------------------------------Inicia consultar Cuenta Aporte-------------------------------------------------");
		long timeStartconsultarCuentaAporte = System.currentTimeMillis();
		List<Long> idsAporteGeneral = new ArrayList<>();
		List<Long> idsRegistroGeneral = new ArrayList<>();
		List<Long> idsPersona = new ArrayList<>();
		List<Long> idsEmpresa = new ArrayList<>();
		//	logger.info("consultarCuentaAporte_2");
		logger.info("-----------------------------------------Inicia consultar Cuenta Aporte FOR ANALISIS DEVOLUCION-------------------------------------------------");
		long timeStartAnalisisDevolucion = System.currentTimeMillis();
		for (AnalisisDevolucionDTO analisisDevolucion : analisisDevolucionDTO) {
		//	logger.info("consultarCuentaAporte_3");
			if (!idsAporteGeneral.contains(analisisDevolucion.getIdAporte())) {
				//logger.info("consultarCuentaAporte_4");
				idsAporteGeneral.add(analisisDevolucion.getIdAporte());
			}
			if (!idsRegistroGeneral.contains(analisisDevolucion.getIdRegistroGeneral())) {
				//logger.info("consultarCuentaAporte_5");
				idsRegistroGeneral.add(analisisDevolucion.getIdRegistroGeneral());
			}
			if (analisisDevolucion.getIdPersona() != null && !idsPersona.contains(analisisDevolucion.getIdPersona())) {
				//logger.info("consultarCuentaAporte_6");
				idsPersona.add(analisisDevolucion.getIdPersona());
			}
			if (analisisDevolucion.getIdEmpresa() != null && !idsPersona.contains(analisisDevolucion.getIdEmpresa())) {
				//logger.info("consultarCuentaAporte_7");
				idsEmpresa.add(analisisDevolucion.getIdEmpresa());
			}
		}
		long timeEndAnalisisDevolucion = System.currentTimeMillis();
		logger.info("Finaliza Cuenta aporte ANALISIS DEVOLUCIÓN FOR: " + (timeEndAnalisisDevolucion - timeStartAnalisisDevolucion) + " ms");
		logger.info("-----------------------------------------ANALISIS DEVOLUCIÓN FOR FIN -------------------------------------------------");
		//logger.info("consultarCuentaAporte_8");
		List<CuentaAporteDTO> cuentasAporte = new ArrayList<>();
		if (idPersonaCotizante != null) {
			//logger.info("consultarCuentaAporte_9");
			cuentasAporte.addAll(consultasCore.consultarCuentasAporteCotizante(idsAporteGeneral, idPersonaCotizante, uri, response,tipo));
		} else {
			//logger.info("consultarCuentaAporte_10");
			cuentasAporte.addAll(consultasCore.consultarCuentasAporteLimit(idsAporteGeneral, uri, response));
			cuentasAporte.addAll(consultasCore.consultarCuentasAporteSinDetalle(idsAporteGeneral, uri, response));
		}
		//logger.info("consultarCuentaAporte_11");

		DatosConsultaCuentaAporteDTO datosBd = new DatosConsultaCuentaAporteDTO();
		datosBd.setIdsAporteGeneral(idsAporteGeneral);
		datosBd.setIdsRegistroGeneral(idsRegistroGeneral);
		datosBd.setIdsPersona(idsPersona);
		datosBd.setIdsEmpresa(idsEmpresa);
		//logger.info("consultarCuentaAporte_12");

		//logger.info("consultarCuentaAporte_12 *** Inicio datosBd: "+ datosBd.toString() + analisisDevolucionDTO.toString());

		datosBd = consultarDatosParaCuentaAportes(datosBd, analisisDevolucionDTO);

		//logger.info("consultarCuentaAporte_12 *** Fin datosBd: "+ datosBd.toString() + "... analisisDevolucionDTO..." + analisisDevolucionDTO.toString());

		Map<Long, BigDecimal[]> controlSaldoAporte = new HashMap<>();
		//logger.info("consultarCuentaAporte_13");
		logger.info("-----------------------------------------Inicia analisis for devolucion 2-------------------------------------------------");
		long timeStartAnalisisDevolucionDos = System.currentTimeMillis();
		for (CuentaAporteDTO cuentaAporteDTO : cuentasAporte) {
			//logger.info("consultarCuentaAporte_14");
			for (AnalisisDevolucionDTO analisisDevolucion : analisisDevolucionDTO) {
			//	logger.info("consultarCuentaAporte_15");

				if (analisisDevolucion.getIdAporte().equals(cuentaAporteDTO.getIdAporteGeneral())) {
					//logger.info("consultarCuentaAporte_16");
					cuentaAporteDTO.setNumeroOperacion(analisisDevolucion.getNumOperacion());
					cuentaAporteDTO.setNumeroPlanilla(analisisDevolucion.getNumPlanilla());
					cuentaAporteDTO.setTipoArhivo(analisisDevolucion.getTipoArchivo());
					cuentaAporteDTO.setTipoPlanilla(analisisDevolucion.getTipoPlanilla());
					cuentaAporteDTO.setEstadoArchivo(analisisDevolucion.getEstadoArchivo());
					//logger.info("consultarCuentaAporte_17");
					
					if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(analisisDevolucion.getTipoSolicitante())
							|| (cuentaAporteDTO.getTipoCotizante() != null && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE
									.equals(cuentaAporteDTO.getTipoCotizante()))) {
						//logger.info("consultarCuentaAporte_18");
						// Se agrega validacion del tipo de cotizante para cuando se consulta la cuenta de aportes
						// por un cotizante por que en ese punto no se tiene el tipo de solicitante
						cuentaAporteDTO
								.setTipoIdentificacionAportante(analisisDevolucion.getTipoIdentificacionTramitador());
						cuentaAporteDTO.setNumeroIdentificacionAportante(
								analisisDevolucion.getNumeroIdentificacionTramitador());
						cuentaAporteDTO.setNombreCompletoAportante(analisisDevolucion.getNombreCompletoTramitador());
					} else {
						//logger.info("consultarCuentaAporte_19");
						cuentaAporteDTO.setTipoIdentificacionAportante(analisisDevolucion.getTipoIdentificacion());
						cuentaAporteDTO.setNumeroIdentificacionAportante(analisisDevolucion.getNumeroIdentificacion());
						cuentaAporteDTO.setNombreCompletoAportante(analisisDevolucion.getNombreCompleto());
						//logger.info("consultarCuentaAporte_20");
					}
					//logger.info("consultarCuentaAporte_21");

					cuentaAporteDTO
							.setTipoIdentificacionTramitador(analisisDevolucion.getTipoIdentificacionTramitador());
					cuentaAporteDTO
							.setNumeroIdentificacionTramitador(analisisDevolucion.getNumeroIdentificacionTramitador());
					cuentaAporteDTO.setNombreCompletoTramitador(analisisDevolucion.getNombreCompletoTramitador());

					cuentaAporteDTO.setPeriodoPago(analisisDevolucion.getPeriodo());
					//logger.info("consultarCuentaAporte_22");
					/*
					* Se agrega pagos de terceros y nombre completo para vista
					* 360
					*/
					cuentaAporteDTO.setPagadorPorTerceros(analisisDevolucion.getPagadorPorTerceros() != null
							? analisisDevolucion.getPagadorPorTerceros() : null);
					cuentaAporteDTO.setNombreCompleto(analisisDevolucion.getNombreCompleto() != null
							? analisisDevolucion.getNombreCompleto() : null);
					cuentaAporteDTO.setConDetalle(analisisDevolucion.getConDetalle());
					cuentaAporteDTO.setCodigoEntidadFinanciera(analisisDevolucion.getCodigoEntidadFinanciera() != null
							? analisisDevolucion.getCodigoEntidadFinanciera() : null);
					cuentaAporteDTO.setTieneModificaciones(analisisDevolucion.getTieneModificaciones() != null
							? analisisDevolucion.getTieneModificaciones() : null);
					cuentaAporteDTO.setIdentificadorDocumento(analisisDevolucion.getIdEcmArchivo());
					//logger.info("consultarCuentaAporte_23");
				}
				//logger.info("consultarCuentaAporte_24");
			}
			//logger.info("consultarCuentaAporte_25");
		}
		long timeEndAnalisisDevolucionDos = System.currentTimeMillis();
		logger.info("Finaliza Cuenta aporte: " + (timeEndAnalisisDevolucionDos - timeStartAnalisisDevolucionDos) + " ms");
		logger.info("-----------------------------------------for analisis devolcuión 2 FIN -------------------------------------------------");
		//logger.info("consultarCuentaAporte_26");

		int temporalIndiceAnalisisDevolucion = 0;

		List<Long> numOpNuevosCorreccion = new ArrayList<Long>();

		Long temporalMovimientoAporteManual = 0L;
		//logger.info("consultarCuentaAporte_27");
		logger.info("----------------------------------------- incia for cuenta de aporte tamaño -------------------------------------------------");
		long timeStartconsultarCuentaAporteTamano = System.currentTimeMillis();
		for (int i = 0; i < cuentasAporte.size(); i++) {
			CuentaAporteDTO cuenta = cuentasAporte.get(i);
			for (int j = 0; j < analisisDevolucionDTO.size(); j++) {
				AnalisisDevolucionDTO analisis = analisisDevolucionDTO.get(j);
				if (cuenta.getIdAporteGeneral().equals(analisis.getIdAporte())) {
					if (EstadoAporteEnum.VIGENTE.equals(cuenta.getEstadoAporte())
							&& !TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES
									.equals(cuenta.getTipoMovimientoRecaudo())
							&& !TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES
									.equals(cuenta.getTipoMovimientoRecaudo())) {
						// El tipo de ajuste monetario para el primer registro
						// relacionado a cada aporte general
						// debe estar con un valor no aplica asignado en
						// pantallas visualmente
						cuenta.setAporteDeRegistro(cuenta.getAjuste());
						cuenta.setInteresesAporte(cuenta.getInteresesAjuste());
						BigDecimal totalAjuste = (cuenta.getAjuste() != null ? cuenta.getAjuste() : BigDecimal.ZERO)
								.add(cuenta.getInteresesAjuste() != null ? cuenta.getInteresesAjuste()
										: BigDecimal.ZERO);

						// en el aporte base, se presenta el valor inicial del
						// aporte
						cuenta.setAporteFinalRegistro(cuenta.getAjuste());
						cuenta.setInteresesFinalAjuste(cuenta.getInteresesAjuste());
						cuenta.setTotalAporteFinal(totalAjuste);

						// se inicializa o actualiza el saldo para el aporte
						BigDecimal[] saldos = { cuenta.getAjuste() != null ? cuenta.getAjuste() : BigDecimal.ZERO,
								cuenta.getInteresesAjuste() != null ? cuenta.getInteresesAjuste() : BigDecimal.ZERO,
								totalAjuste };

						if (!controlSaldoAporte
								.containsKey(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L)) {
							controlSaldoAporte.put(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L, saldos);
						} else {
							BigDecimal[] saldoActual = controlSaldoAporte
									.get(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L);
							saldoActual[0] = saldoActual[0].add(saldos[0]);
							saldoActual[1] = saldoActual[1].add(saldos[1]);
							saldoActual[2] = saldoActual[2].add(saldos[2]);
						}

						cuenta.setTotalAporte(totalAjuste);
						cuenta.setAjuste(BigDecimal.ZERO);
						cuenta.setInteresesAjuste(BigDecimal.ZERO);
						cuenta.setTotalAjuste(BigDecimal.ZERO);
						cuenta.setNumeroOperacion(cuenta.getIdMovimientoAporte().toString());

						temporalMovimientoAporteManual = cuenta.getIdMovimientoAporte();
						temporalIndiceAnalisisDevolucion = i;
						break;
					}else if (EstadoAporteEnum.CORREGIDO.equals(cuenta.getEstadoAporte())
					&& !TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES
							.equals(cuenta.getTipoMovimientoRecaudo())
					&& !TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES
							.equals(cuenta.getTipoMovimientoRecaudo())) {
				// El tipo de ajuste monetario para el primer registro
				// relacionado a cada aporte general
				// debe estar con un valor no aplica asignado en
				// pantallas visualmente
				
				cuenta.setAporteDeRegistro(cuenta.getAjuste());
				cuenta.setInteresesAporte(cuenta.getInteresesAjuste());
				BigDecimal totalAjuste = (cuenta.getAjuste() != null ? cuenta.getAjuste() : BigDecimal.ZERO)
						.add(cuenta.getInteresesAjuste() != null ? cuenta.getInteresesAjuste()
								: BigDecimal.ZERO);

				// en el aporte base, se presenta el valor inicial del
				// aporte
				cuenta.setAporteFinalRegistro(cuenta.getAjuste());
				cuenta.setInteresesFinalAjuste(cuenta.getInteresesAjuste());
				cuenta.setTotalAporteFinal(totalAjuste);

				// se inicializa o actualiza el saldo para el aporte
				BigDecimal[] saldos = { cuenta.getAjuste() != null ? cuenta.getAjuste() : BigDecimal.ZERO,
						cuenta.getInteresesAjuste() != null ? cuenta.getInteresesAjuste() : BigDecimal.ZERO,
						totalAjuste };

				if (!controlSaldoAporte
						.containsKey(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L)) {
					controlSaldoAporte.put(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L, saldos);
				} else {
					BigDecimal[] saldoActual = controlSaldoAporte
							.get(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L);
					saldoActual[0] = saldoActual[0].add(saldos[0]);
					saldoActual[1] = saldoActual[1].add(saldos[1]);
					saldoActual[2] = saldoActual[2].add(saldos[2]);
				}

				cuenta.setTotalAporte(totalAjuste);
				cuenta.setAjuste(BigDecimal.ZERO);
				cuenta.setInteresesAjuste(BigDecimal.ZERO);
				cuenta.setTotalAjuste(BigDecimal.ZERO);
				cuenta.setNumeroOperacion(cuenta.getIdMovimientoAporte().toString());

				temporalMovimientoAporteManual = cuenta.getIdMovimientoAporte();
				temporalIndiceAnalisisDevolucion = i;
				break;
				} else if (i != 0) {
						CuentaAporteDTO cuentaAnterior = cuentasAporte.get(i - 1);
						if (cuenta.getIdAporteDetallado() != null) {
							if ((cuenta.getIdAporteGeneral().equals(cuentaAnterior.getIdAporteGeneral()))
									&& (cuenta.getIdAporteDetallado().equals(cuentaAnterior.getIdAporteDetallado()))) {

								// se toma el saldo para el aporte general
								BigDecimal[] saldoActual = controlSaldoAporte
										.get(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L);
								if (saldoActual == null) {
									saldoActual = new BigDecimal[3];
									saldoActual[0] = BigDecimal.ZERO;
									saldoActual[1] = BigDecimal.ZERO;
									saldoActual[2] = BigDecimal.ZERO;
									controlSaldoAporte.put(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L,
											saldoActual);
								}

								cuenta.setAporteDeRegistro(saldoActual[0]);
								cuenta.setInteresesAporte(saldoActual[1]);
								cuenta.setTotalAporte(saldoActual[2]);

								BigDecimal montoAjuste = (cuenta.getAjuste() != null ? cuenta.getAjuste()
										: BigDecimal.ZERO);
								BigDecimal interesAjuste = (cuenta.getInteresesAjuste() != null
										? cuenta.getInteresesAjuste() : BigDecimal.ZERO);

								BigDecimal totalAporteAjuste = montoAjuste.add(interesAjuste);

								cuenta.setAjuste(montoAjuste);
								cuenta.setInteresesAjuste(interesAjuste);

								if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_ALTA
										.equals(cuenta.getTipoAjusteMonetario())) {
									
									if(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(cuenta.getTipoMovimientoRecaudo())){
									logger.info("cuenta.getIdAporteDetallado()"+ cuenta.getIdAporteDetallado());
									logger.info("idMovimientoAporte"+cuenta.getIdMovimientoAporte());
									// GLPI 71896
									//Long Registro = consultasCore.consultarRegistroDetalladoAnterior(cuenta.getIdAporteDetallado()); 
									String Planilla = cuenta.getNumeroPlanilla();  
									//logger.info("Numero Planillla"+ Planilla);
									//logger.info("Registro" + Registro);
									String NumeroPlanillaN= consultasCore.consultarNumeroDePlanillaN(cuenta.getIdMovimientoAporte());								
									cuenta.setNumeroPlanilla(NumeroPlanillaN);                       
									cuenta.setAjuste(montoAjuste.subtract(saldoActual[0]));
									cuenta.setNumeroPlanillaCorregida(consultasCore.consultarNumeroDePlanillaCorregida(cuenta.getIdMovimientoAporte()));
									cuenta.setInteresesAjuste(interesAjuste.subtract(saldoActual[1]));
									cuenta.setAporteDeRegistro(BigDecimal.ZERO);
									cuenta.setInteresesAporte(BigDecimal.ZERO);
									cuenta.setTotalAporte(BigDecimal.ZERO);
									cuenta.setAporteFinalRegistro(montoAjuste);
									cuenta.setInteresesFinalAjuste(interesAjuste);
									cuenta.setTotalAporteFinal(montoAjuste.add(interesAjuste));
									cuenta.setNumeroOperacion(temporalMovimientoAporteManual.toString());
									cuenta.setTotalAjuste(interesAjuste.subtract(saldoActual[1]).add(montoAjuste.subtract(saldoActual[0])));
									
									cuenta.setTipoPlanilla(TipoPlanillaEnum.CORRECIONES);
									cuenta.setIdAporteDetallado(cuenta.getIdAporteDetallado());
									
									}
									else{
									cuenta.setAporteFinalRegistro(saldoActual[0].add(montoAjuste));
									cuenta.setInteresesFinalAjuste(saldoActual[1].add(interesAjuste));
									cuenta.setTotalAporteFinal(saldoActual[2].add(totalAporteAjuste));
									cuenta.setNumeroOperacion(temporalMovimientoAporteManual.toString());
									
									}
									if(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(cuenta.getTipoMovimientoRecaudo())) {
										cuenta.setTipoPlanilla(TipoPlanillaEnum.CORRECIONES);
									}
									
								} else if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA
										.equals(cuenta.getTipoAjusteMonetario())) {
									
									numOpNuevosCorreccion.add(cuenta.getIdAporteDetallado());
									
									cuenta.setAporteFinalRegistro(saldoActual[0].subtract(montoAjuste));
									cuenta.setInteresesFinalAjuste(saldoActual[1].subtract(interesAjuste));
									cuenta.setTotalAporteFinal(saldoActual[2].subtract(totalAporteAjuste));
									cuenta.setNumeroOperacion(temporalMovimientoAporteManual.toString());
									cuentasAporte.get(temporalIndiceAnalisisDevolucion)
											.setEstadoAporte(EstadoAporteEnum.ANULADO);
									cuenta.setNuevoNumeroOperacion(
											datosBd.getNumerosOperacion().get(cuenta.getIdAporteGeneral()));
									PersonaModeloDTO persona = consultarAportanteNuevoCorreccion(cuenta.getIdAporteGeneral(),
											cuenta.getIdAporteDetallado());
									cuenta.setTipoIdentificacionAportanteCorreccion(persona.getTipoIdentificacion());
									cuenta.setNumeroIdentificacionAportanteCorreccion(persona.getNumeroIdentificacion());
									
									if(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(cuenta.getTipoMovimientoRecaudo())) {
										cuenta.setTipoPlanilla(TipoPlanillaEnum.CORRECIONES);
									}
									
								} else if (TipoAjusteMovimientoAporteEnum.DEVOLUCION
										.equals(cuenta.getTipoAjusteMonetario())) {
									cuenta.setAporteFinalRegistro(saldoActual[0].subtract(montoAjuste));
									cuenta.setInteresesFinalAjuste(saldoActual[1].subtract(interesAjuste));
									cuenta.setTotalAporteFinal(saldoActual[2].subtract(totalAporteAjuste));
									cuenta.setNumeroOperacion(temporalMovimientoAporteManual.toString());
								}

								// se actualiza el saldo
								saldoActual[0] = cuenta.getAporteFinalRegistro();
								saldoActual[1] = cuenta.getInteresesFinalAjuste();
								saldoActual[2] = cuenta.getTotalAporteFinal();

								break;
							}
						} else {
							// se toma el saldo para el aporte general
							BigDecimal[] saldoActual = controlSaldoAporte
									.get(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L);
							if (saldoActual == null) {
								saldoActual = new BigDecimal[3];
								saldoActual[0] = BigDecimal.ZERO;
								saldoActual[1] = BigDecimal.ZERO;
								saldoActual[2] = BigDecimal.ZERO;
								controlSaldoAporte.put(cuenta.getConDetalle() ? cuenta.getIdAporteDetallado() : 0L,
										saldoActual);
							}

							cuenta.setAporteDeRegistro(saldoActual[0]);
							cuenta.setInteresesAporte(saldoActual[1]);
							cuenta.setTotalAporte(saldoActual[2]);

							BigDecimal montoAjuste = (cuenta.getAjuste() != null ? cuenta.getAjuste()
									: BigDecimal.ZERO);
							BigDecimal interesAjuste = (cuenta.getInteresesAjuste() != null
									? cuenta.getInteresesAjuste() : BigDecimal.ZERO);

							BigDecimal totalAporteAjuste = montoAjuste.add(interesAjuste);

							cuenta.setAjuste(montoAjuste);
							cuenta.setInteresesAjuste(interesAjuste);

							if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_ALTA
									.equals(cuenta.getTipoAjusteMonetario())) {
								cuenta.setAporteFinalRegistro(saldoActual[0].add(montoAjuste));
								cuenta.setInteresesFinalAjuste(saldoActual[1].add(interesAjuste));
								cuenta.setTotalAporteFinal(saldoActual[2].add(totalAporteAjuste));
								cuenta.setNumeroOperacion(temporalMovimientoAporteManual.toString());
								if(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.equals(cuenta.getTipoMovimientoRecaudo())){
									cuenta.setTipoPlanilla(TipoPlanillaEnum.CORRECIONES);
									}
							} else if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA
									.equals(cuenta.getTipoAjusteMonetario())) {

								numOpNuevosCorreccion.add(cuenta.getIdAporteDetallado());
								logger.info("Lista completa de idAporteDetallado agregados: " + numOpNuevosCorreccion);

								cuenta.setAporteFinalRegistro(saldoActual[0].subtract(montoAjuste));
								cuenta.setInteresesFinalAjuste(saldoActual[1].subtract(interesAjuste));
								cuenta.setTotalAporteFinal(saldoActual[2].subtract(totalAporteAjuste));
								cuenta.setNumeroOperacion(temporalMovimientoAporteManual.toString());
								cuentasAporte.get(temporalIndiceAnalisisDevolucion)
										.setEstadoAporte(EstadoAporteEnum.ANULADO);
								cuenta.setNuevoNumeroOperacion(
										datosBd.getNumerosOperacion().get(cuenta.getIdAporteGeneral()));
								PersonaModeloDTO persona = consultarAportanteNuevoCorreccion(cuenta.getIdAporteGeneral(),
										null);
								cuenta.setTipoIdentificacionAportanteCorreccion(persona.getTipoIdentificacion());
								cuenta.setNumeroIdentificacionAportanteCorreccion(persona.getNumeroIdentificacion());
							} else if (TipoAjusteMovimientoAporteEnum.DEVOLUCION
									.equals(cuenta.getTipoAjusteMonetario())) {
								cuenta.setAporteFinalRegistro(saldoActual[0].subtract(montoAjuste));
								cuenta.setInteresesFinalAjuste(saldoActual[1].subtract(interesAjuste));
								cuenta.setTotalAporteFinal(saldoActual[2].subtract(totalAporteAjuste));
								cuenta.setNumeroOperacion(temporalMovimientoAporteManual.toString());
							}

							// se actualiza el saldo
							saldoActual[0] = cuenta.getAporteFinalRegistro();
							saldoActual[1] = cuenta.getInteresesFinalAjuste();
							saldoActual[2] = cuenta.getTotalAporteFinal();

							break;
						}
					}
				}
			}
		}
		long timeEndCuentaAporteTamano = System.currentTimeMillis();
		logger.info("Finaliza Cuenta aporte: " + (timeEndCuentaAporteTamano - timeStartconsultarCuentaAporteTamano) + " ms");
		logger.info("-----------------------------------------CUENTA DE APORTER TAMAÑO FIN -------------------------------------------------");
		//logger.info("consultarCuentaAporte_28");
				
		//Se debe consultar los nuevos numeros de operacion para las correcciones a la baja
		if(numOpNuevosCorreccion.size()>0) {
			Map<Long, String> nuevoNumerosCorreccionMapa = new HashMap<Long, String>();
			nuevoNumerosCorreccionMapa = consultasCore.consultarNuevosNumerosCorreccion(numOpNuevosCorreccion);
			
			if(nuevoNumerosCorreccionMapa.size()>0) {
				for (CuentaAporteDTO cuenta : cuentasAporte) {
					
					if (TipoAjusteMovimientoAporteEnum.CORRECCION_A_LA_BAJA
							.equals(cuenta.getTipoAjusteMonetario()) 
							&& nuevoNumerosCorreccionMapa.containsKey(cuenta.getIdAporteDetallado())) {
						cuenta.setNuevoNumeroOperacion(nuevoNumerosCorreccionMapa.get(cuenta.getIdAporteDetallado()));
					}
				}
			}
		}
		//logger.info("consultarCuentaAporte_29");


		List<String> listaPlanillaConCorrecion = new ArrayList<String>();

		for (CuentaAporteDTO cuentaAporte : cuentasAporte) {

			if (!TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO
					.equals(cuentaAporte.getTipoMovimientoRecaudo()) && !TipoPlanillaEnum.CORRECIONES.equals(cuentaAporte.getTipoPlanilla()) ) {
				cuentaAporte.setEstadoArchivo(null);
			}
			
			
			//Se agregan en una lista las planillas que tienen correccion y son manuales
			if(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(cuentaAporte.getTipoMovimientoRecaudo())
					&& TipoPlanillaEnum.CORRECIONES.equals(cuentaAporte.getTipoPlanilla())) {
				listaPlanillaConCorrecion.add(cuentaAporte.getNumeroPlanilla());
			}
		}
		//logger.info("consultarCuentaAporte_30");

		//Se busca la planilla de correccion de la original
		if(listaPlanillaConCorrecion.size()>0) {
			List<CuentaAporteDTO> listaPlanillaConCorreccion =  consultasPila.consultarPlanillasCorreccionN(listaPlanillaConCorrecion);
			
			if(listaPlanillaConCorreccion.size()>0) {
				for (CuentaAporteDTO cuentaAporte : cuentasAporte) {
					for (CuentaAporteDTO cuentaConCorreccion : listaPlanillaConCorreccion) {
						if(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.equals(cuentaAporte.getTipoMovimientoRecaudo())
								&& TipoPlanillaEnum.CORRECIONES.equals(cuentaAporte.getTipoPlanilla())
								&& cuentaAporte.getNumeroPlanilla().equals(cuentaConCorreccion.getNumeroPlanilla()) ) {
							cuentaAporte.setNumeroPlanillaCorregida(cuentaConCorreccion.getNumeroPlanillaCorregida());
						}

					}

				}
			}
		}
		//logger.info("consultarCuentaAporte_31");

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		long timeEndCuentaAporte = System.currentTimeMillis();
		logger.info("Finaliza Cuenta aporte: " + (timeEndCuentaAporte - timeStartconsultarCuentaAporte) + " ms");
		logger.info("-----------------------------------------CUENTA APORTE FIN -------------------------------------------------");
		return cuentasAporte;
		}
}
