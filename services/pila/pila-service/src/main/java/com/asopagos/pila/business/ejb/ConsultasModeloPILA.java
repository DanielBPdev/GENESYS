package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.TemAporteModeloDTO;
import com.asopagos.dto.modelo.TemNovedadModeloDTO;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.soporte.ProcesoPila;
import com.asopagos.entidades.pila.temporal.TemAportante;
import com.asopagos.entidades.pila.temporal.TemAporte;
import com.asopagos.entidades.pila.temporal.TemCotizante;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.pila.business.interfaces.IConsultasModeloPILA;
import com.asopagos.pila.constants.ConstantesComunesPila;
import com.asopagos.pila.constants.ConstantesParaMensajes;
import com.asopagos.pila.constants.MensajesErrorPersistenciaEnum;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.constants.NamedStoredProcedureConstants;
import com.asopagos.pila.dto.ArchivosProcesadosFinalizadosOFDTO;
import com.asopagos.pila.dto.BloquesValidacionArchivoDTO;
import com.asopagos.pila.dto.CabeceraDetalleArchivoDTO;
import com.asopagos.pila.dto.CriterioConsultaDTO;
import com.asopagos.pila.dto.CriteriosBusquedaArchivosProcesados;
import com.asopagos.pila.dto.DatosAportanteTemporalDTO;
import com.asopagos.pila.dto.DetalleBloqueValidacionArchivoDTO;
import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import com.asopagos.pila.service.PilaService;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.asopagos.pila.dto.DetallePestanaNovedadesDTO;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe
 *         Buitrago </a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson A. Arboleda </a>
 */
@Stateless
public class ConsultasModeloPILA implements IConsultasModeloPILA, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(PilaService.class);

	/** Entity Manager */
	@PersistenceContext(unitName = "pila_PU")
	private EntityManager entityManager;

	/** Variable de mensaje de error */
	private String mensaje;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosPendientesPorProcesarInformacion()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosPendientesPorProcesarInformacion() {
		logger.debug("Inicia archivosPendientesPorProcesarInformacion");

		try {
			Long result = entityManager.createNamedQuery(NamedQueriesConstants.PENDIENTES_POR_PROCESAR_OI, Long.class)
					.getSingleResult(); 
			logger.debug("Finaliza archivosPendientesPorProcesarInformacion");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosPendientesPorProcesarInformacion");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosProcesoFinalizado()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosProcesoFinalizado() {
		logger.debug("Inicia archivosProcesoFinalizado");

		try {
			Long result = entityManager.createNamedQuery(NamedQueriesConstants.PROCESO_FINALIZADO, Long.class)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosProcesoFinalizado");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosProcesoFinalizado");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosInconsistentesOI()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosInconsistentesOI() {

	//String firmaServicio = "ConsultasModeloPila.archivosInconsistentesOI()";
	//logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
	//Long result = null;

	//try {
	//	List<String> estadosOI = obtenerListadoEstadosInconsistentes(1);

	//	Object resultQuery = entityManager.createNamedQuery(NamedQueriesConstants.REGISTROS_BANDEJA_INCONSISTENTES)
	//			.setParameter("estados", estadosOI).getSingleResult();

	//	result = ((Integer) resultQuery).longValue();
	//} catch (NoResultException e) {
	//	result = 0L;
	//} catch (Exception e) {
	//	logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
	//	throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
	//}

	//logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	//return result;
		 	    Long result = 0L;

        try {
            List<String> estadosOI = obtenerListadoEstadosInconsistentes(1);
            List<String> estadosOF = obtenerListadoEstadosInconsistentes(2);
            List<String> estadosF6 = obtenerListadoEstadosInconsistentes(3);
            
            Date epochAsopagos = new GregorianCalendar(1900, 1, 1).getTime();
            
            Object resultQuery = (Object) entityManager.createNamedQuery(NamedQueriesConstants.CONTEO_INCONSISTENCIAS_ERROR_TOTAL_PILA)
                    .setParameter("estados", estadosOI).setParameter("estadosOF", estadosOF).setParameter("estadosF6", estadosF6)
                    .getSingleResult();
            
            result = ((Integer) resultQuery).longValue(); 
            
        } catch (NoResultException nre) {
            logger.debug(ConstantesComunes.FIN_LOGGER );
            return 0L;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER );
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        logger.info("finalizo ");
        return result;
	}

	/**
	 * @return
	 */
	private List<EstadoProcesoArchivoEnum> obtenerEstadosProcesoArchivo() {
		String firmaMetodo = "ConsultasModeloPILA.obtenerEstadosProcesoArchivo()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<EstadoProcesoArchivoEnum> listaEstados = new ArrayList<>();

		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO_GRUPO);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_NO_VALIDA);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_DOBLE);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_SIN_FECHA_MODIFICACION);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO_ANTERIOR);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_GRUPO_NO_VALIDO);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_REPROCESO_PREVIO);
		listaEstados.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_ANULACION_FALLIDA);
		listaEstados.add(EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO);
		listaEstados.add(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA);
		listaEstados.add(EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_EN_ESPERA);
		listaEstados.add(EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_INCONSISTENTES);
		listaEstados.add(EstadoProcesoArchivoEnum.APORTANTE_NO_CREADO_EN_BD);
		listaEstados.add(EstadoProcesoArchivoEnum.GESTIONAR_DIFERENCIA_EN_CONCILIACION);
		listaEstados.add(EstadoProcesoArchivoEnum.RECAUDO_VALOR_CERO_CONCILIADO);
		listaEstados.add(EstadoProcesoArchivoEnum.PENDIENTE_CONCILIACION);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return listaEstados;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosBandejaGestionOI()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosBandejaGestionOI() {
		logger.debug("Inicia archivosBandejaGestionOI");
                Integer numPlanillas = null;
		try {
			numPlanillas = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.REGISTROS_BANDEJA_GESTION)
					.getSingleResult();
			logger.debug("Finaliza archivosBandejaGestionOI");

			return new Long(numPlanillas);
		} catch (Exception e) {
			logger.debug("Finaliza archivosBandejaGestionOI");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosPendientesPorProcesarInformacionManualOI()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosPendientesPorProcesarInformacionManualOI() {
		logger.debug("Inicia archivosPendientesPorProcesarInformacionManualOI");

		try {
                        //Se ajusta consulta por mantis 258353 diferencia de conteo entre bandeja de estados y gestion
                        Long result = (Long) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_PLANILLAS_PENDIENTES_GESTION_MANUAL)
					.setMaxResults(1).getSingleResult();
                        
                        
			logger.debug("Finaliza archivosPendientesPorProcesarInformacionManualOI");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosPendientesPorProcesarInformacionManualOI");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosProcesoFinalizadoManual()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosProcesoFinalizadoManual() {
		logger.debug("Inicia archivosProcesoFinalizadoManual");

		try {
			Long result = entityManager.createNamedQuery(NamedQueriesConstants.PROCESO_FINALIZADO_MANUAL_TOTAL, Long.class)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosProcesoFinalizadoManual");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosProcesoFinalizadoManual");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosCargados()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosCargados() {
		logger.debug("Inicia archivosCargados");

		try {
			Long result = entityManager.createNamedQuery(NamedQueriesConstants.ARCHIVOS_CARGADOS_CONTROL, Long.class)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosCargados");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosCargados");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosEnProcesoControl()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosEnProcesoControl() {
		logger.debug("Inicia archivosEnProcesoControl");

		try {
			List<EstadoProcesoArchivoEnum> estadosExclusion = obtenerEstadosProcesoArchivo();
			estadosExclusion.add(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO);
			estadosExclusion.add(EstadoProcesoArchivoEnum.PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD);
			estadosExclusion.add(EstadoProcesoArchivoEnum.PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES);

			Long result = entityManager.createNamedQuery(NamedQueriesConstants.ARCHIVOS_EN_PROCESO_CONTROL, Long.class)
					.setParameter("estados", estadosExclusion)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosEnProcesoControl");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosEnProcesoControl");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosProcesoFinalizadoControl()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosProcesoFinalizadoControl() {
		logger.debug("Inicia archivosProcesoFinalizadoControl");

		try {
			Long result = entityManager.createNamedQuery(NamedQueriesConstants.PROCESO_FINALIZADO, Long.class)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosProcesoFinalizadoControl");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosProcesoFinalizadoControl");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosInconsistentesControl()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosInconsistentesControl() {
		String firmaMetodo = "ConsultasModeloPILA.archivosInconsistentesControl()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		try {
			Long result = entityManager
					.createNamedQuery(NamedQueriesConstants.REGISTROS_BANDEJA_INCONSISTENTES_CONTROL, Long.class)
					.setParameter("estados", obtenerEstadosProcesoArchivo())
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

			return result;
		} catch (Exception e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosEnGestionControl()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosEnGestionControl() {
		logger.debug("Inicia archivosEnGestionControl");

		try {
			Long result = entityManager
					.createNamedQuery(NamedQueriesConstants.REGISTROS_BANDEJA_GESTION_CONTROL, Long.class)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosEnGestionControl");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosEnGestionControl");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosEnProcesoManualControl()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosEnProcesoManualControl() {
		logger.debug("Inicia archivosProcesoFinalizadoManualControl");

		try {
			Long result = entityManager
					.createNamedQuery(NamedQueriesConstants.PENDIENTES_POR_PROCESAR_MANUAL_CONTROL, Long.class)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosProcesoFinalizadoManualControl");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosProcesoFinalizadoManualControl");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosProcesoFinalizadoManualControl()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long archivosProcesoFinalizadoManualControl() {
		logger.debug("Inicia archivosProcesoFinalizadoManualControl");

		try {
			Long result = entityManager.createNamedQuery(NamedQueriesConstants.PROCESO_FINALIZADO_MANUAL, Long.class)
					.setParameter("fechaInicio", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)))
					.getSingleResult();
			logger.debug("Finaliza archivosProcesoFinalizadoManualControl");

			return result;
		} catch (Exception e) {
			logger.debug("Finaliza archivosProcesoFinalizadoManualControl");
			logger.error("Hubo un error en la consulta");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#archivosOFProcesadosFinalizados(java.lang.Integer)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long consultarArchivosOperadorFinanciero(String namedQuery, Date fechaDesde) {
		logger.debug("Inicia consultarArchivosOperadorFinanciero");

		Long result = 0L;
		try {
			// Si llegan los buscadores de registros enviar un parametro
			// adicional con los estados de archivo
			if (namedQuery.equals(NamedQueriesConstants.CONSULTAR_REGISTROS_F_EN_PROCESO)
					|| namedQuery.equals(NamedQueriesConstants.CONSULTAR_REGISTROS_F_PROCESADOS)
					|| namedQuery.equals(NamedQueriesConstants.CONSULTAR_REGISTROS_F_INCONSISTENCIAS)
					|| namedQuery.equals(NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_CON_INCONSISTENCIAS)) {

				List<EstadoProcesoArchivoEnum> estadosArchivo = new ArrayList<>();
				Boolean usarEstados = Boolean.TRUE;
				Boolean usarBase = Boolean.TRUE;

				switch (namedQuery) {
				case NamedQueriesConstants.CONSULTAR_REGISTROS_F_EN_PROCESO:
					estadosArchivo.add(EstadoProcesoArchivoEnum.CARGADO_EXITOSAMENTE);
					usarEstados = Boolean.FALSE;
					break;
				case NamedQueriesConstants.CONSULTAR_REGISTROS_F_PROCESADOS:
					estadosArchivo.add(EstadoProcesoArchivoEnum.ARCHIVO_FINANCIERO_CONCILIADO);
					break;
				case NamedQueriesConstants.CONSULTAR_REGISTROS_F_INCONSISTENCIAS:
				case NamedQueriesConstants.CONSULTAR_ARCHIVOS_OF_CON_INCONSISTENCIAS:
					usarBase = Boolean.FALSE;
					List<String> estadosOF = obtenerListadoEstadosInconsistentes(2);
					List<String> estadosF6 = obtenerListadoEstadosInconsistentes(3);

					Object resultQuery = (Object) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_F_INCONSISTENCIAS)
							.setParameter("estadosOF", estadosOF).setParameter("estadosF6", estadosF6)
							.setParameter("fechaDesde", CalendarUtils.truncarHora(fechaDesde)).getSingleResult();

					result = ((Integer) resultQuery).longValue();
					break;
				default:
					break;
				}

				if (usarBase) {
					result = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_F_EN_ESTADO_ARCHIVO, Long.class)
							.setParameter("fechaDesde", CalendarUtils.truncarHora(fechaDesde))
							.setParameter("estadosArchivo", estadosArchivo)
							.setParameter("usarEstados", usarEstados ? 1 : 0).getSingleResult();
				}
				logger.debug("Finaliza archivosOFProcesadosFinalizados");
			} else {
				result = entityManager.createNamedQuery(namedQuery, Long.class)
						.setParameter("fechaDesde", CalendarUtils.truncarHora(fechaDesde)).getSingleResult();
				logger.debug("Finaliza archivosOFProcesadosFinalizados");
			}

			return result;
		} catch (NoResultException nre) {
			return 0L;
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.ARCHIVOS_OPERADOR_FINANCIERO, e.getMessage());
			logger.error("Finaliza consultarArchivosOperadorFinanciero :: " + mensaje);
			throw new TechnicalException(mensaje);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#buscarArchivosOIProcesadosFinalizados(com.asopagos.pila.dto.CriteriosBusquedaArchivosProcesados,
	 *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizados(
			CriteriosBusquedaArchivosProcesados criterios, UriInfo uri, HttpServletResponse response) {
		String firmaMetodo = "ConsultasModeloPILA.buscarArchivosOIProcesadosFinalizados(CriteriosBusquedaArchivosProcesados, UriInfo, "
				+ "HttpServletResponse)";

		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		logger.info(criterios.getTipoOperador());
		logger.info(criterios.getFechaInicio());
		logger.info(criterios.getFechaFin());
		String valorParamIdentificacion = null;

		List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();

		try {

			// queryBuilder.addOrderByDefaultParam("-fechaRecibo");

			result = new ArrayList<>();

			if (criterios.getTipoOperador() == null || TipoOperadorEnum.OPERADOR_INFORMACION.equals(criterios.getTipoOperador())) {
				if (criterios.getTipoIdentificacion() != null && criterios.getNumeroIdentificacion() != null) {
					valorParamIdentificacion = "%_" + criterios.getTipoIdentificacion() + "_"
							+ criterios.getNumeroIdentificacion() + "_%";
				}
				Query query = entityManager.createNamedQuery(
										NamedQueriesConstants.CONSULTAR_ARCHIVOS_PROCESADOS_FINALIZADOS_CRITERIOS, 
										null);

				query.setParameter("tipoNumDocumento", valorParamIdentificacion);
				query.setParameter("fechaInicio", (criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio())
									: CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
				query.setParameter("fechaFin", (criterios.getFechaFin() != null) ? new Date(criterios.getFechaFin()) : null);
				
				query.setParameter("numeroPlanilla", (criterios.getNumeroPlanilla() != null) ? criterios.getNumeroPlanilla() : null);
				query.setParameter("fechaProceso", null); 

				if (criterios.getIdBanco() != null) {
					query.setParameter("idBanco", criterios.getIdBanco().substring(1));
				} else {
					query.setParameter("idBanco", null);
				}


				if (criterios.getTipoIdentificacion() != null || criterios.getNumeroIdentificacion() != null
						|| criterios.getDigitoVerificacion() != null || criterios.getFechaInicio() != null
						|| criterios.getFechaFin() != null || criterios.getNumeroPlanilla() != null
						|| criterios.getIdBanco() != null) {
						query.setParameter("fechaInicio", (criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio()) : null);
						query.setParameter("fechaProceso", null);
				} else {
					query.setParameter("fechaInicio",
							(criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio())
									: CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
					query.setParameter("fechaProceso",
							CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
				}

				result.addAll((List<ArchivosProcesadosFinalizadosOFDTO>) query.getResultList());
			}

			if (criterios.getTipoOperador() == null || TipoOperadorEnum.OPERADOR_FINANCIERO.equals(criterios.getTipoOperador())) {
				if (criterios.getTipoIdentificacion() != null && criterios.getNumeroIdentificacion() != null) {
					valorParamIdentificacion = criterios.getNumeroIdentificacion();
				}

				

				Query query = entityManager.createNamedQuery(
										NamedQueriesConstants.CONSULTAR_ARCHIVOS_PROCESADOS_FINALIZADOS_CRITERIOS_OF, 
										null);

				query.setParameter("tipoNumDocumento", valorParamIdentificacion);
				query.setParameter("fechaInicio", (criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio())
									: CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
				query.setParameter("fechaFin", (criterios.getFechaFin() != null) ? new Date(criterios.getFechaFin()) : null);
				
				query.setParameter("numeroPlanilla", (criterios.getNumeroPlanilla() != null) ? criterios.getNumeroPlanilla() : null);
				query.setParameter("fechaProceso", null); 

				if (criterios.getIdBanco() != null) {
					query.setParameter("idBanco", criterios.getIdBanco().substring(1));
				} else {
					query.setParameter("idBanco", null);
				}


				if (criterios.getTipoIdentificacion() != null || criterios.getNumeroIdentificacion() != null
						|| criterios.getDigitoVerificacion() != null || criterios.getFechaInicio() != null
						|| criterios.getFechaFin() != null || criterios.getNumeroPlanilla() != null
						|| criterios.getIdBanco() != null) {
						query.setParameter("fechaInicio", (criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio()) : null);
						query.setParameter("fechaProceso", null);
				} else {
					query.setParameter("fechaInicio",
							(criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio())
									: CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
					query.setParameter("fechaProceso",
							CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
				}


				result.addAll((List<ArchivosProcesadosFinalizadosOFDTO>) query.getResultList());
			}

		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un listado vacío
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.ARCHIVOS_OPERADOR_FINANCIERO, e.getMessage());
			e.printStackTrace();
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#buscarArchivosOIProcesadosFinalizadosManual(com.asopagos.pila.dto.CriteriosBusquedaArchivosProcesados,
	 *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosProcesadosFinalizadosOFDTO> buscarArchivosOIProcesadosFinalizadosManual(
			CriteriosBusquedaArchivosProcesados criterios, UriInfo uri, HttpServletResponse response) {
		String firmaMetodo = "ConsultasModeloPILA.buscarArchivosOIProcesadosFinalizados(CriteriosBusquedaArchivosProcesados, UriInfo, "
				+ "HttpServletResponse)";

		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();

		try {
			QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
			if (criterios.getTipoIdentificacion() != null || criterios.getNumeroIdentificacion() != null
					|| criterios.getDigitoVerificacion() != null || criterios.getFechaInicio() != null
					|| criterios.getFechaFin() != null || criterios.getNumeroPlanilla() != null
					|| criterios.getIdBanco() != null) {

				queryBuilder.addParam("tipoIdentificacion", criterios.getTipoIdentificacion());
				queryBuilder.addParam("numeroIdentificacion", criterios.getNumeroIdentificacion());
				queryBuilder.addParam("digitoVerificacion", criterios.getDigitoVerificacion());
				queryBuilder.addParam("fechaInicio",
						(criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio()) : new Date(1L));
				queryBuilder.addParam("fechaFin",
						(criterios.getFechaFin() != null) ? new Date(criterios.getFechaFin()) : new Date());
				queryBuilder.addParam("numeroPlanilla", criterios.getNumeroPlanilla());
				queryBuilder.addParam("idBanco", criterios.getIdBanco());
				queryBuilder.addParam("fechaProceso", null);
			} else {
				queryBuilder.addParam("tipoIdentificacion", criterios.getTipoIdentificacion());
				queryBuilder.addParam("numeroIdentificacion", criterios.getNumeroIdentificacion());
				queryBuilder.addParam("digitoVerificacion", criterios.getDigitoVerificacion());
				queryBuilder.addParam("fechaInicio",
						(criterios.getFechaInicio() != null) ? new Date(criterios.getFechaInicio())
								: CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
				queryBuilder.addParam("fechaFin",
						(criterios.getFechaFin() != null) ? new Date(criterios.getFechaFin()) : null);
				queryBuilder.addParam("numeroPlanilla", criterios.getNumeroPlanilla());
				queryBuilder.addParam("idBanco", criterios.getIdBanco());
				queryBuilder.addParam("fechaProceso",
						CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
			}

			queryBuilder.addOrderByDefaultParam("-fechaRecibo");

			Query query = queryBuilder.createQuery(
					NamedQueriesConstants.CONSULTAR_ARCHIVOS_PROCESADOS_FINALIZADOS_MANUAL_CRITERIOS, null);

			if (query != null) {
				result = (List<ArchivosProcesadosFinalizadosOFDTO>) query.getResultList();
			} else {
				// no se pudo crear el query, se retorna lista vacía
				result = new ArrayList<>();
			}

		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un listado vacío
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.ARCHIVOS_OPERADOR_FINANCIERO, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultasModeloPila#obtenerTodasNovedades(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DetallePestanaNovedadesDTO> obtenerTodasNovedades(Long idRegGen) {
        String firmaMetodo = "ConsultasModeloCore.obtenerTodasNovedades(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DetallePestanaNovedadesDTO> result = new ArrayList<>();

        // se consultan los registros
		List<Object[]> resultQuery = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_TODAS_NOVEDADES)
				.setParameter("idRegistroGeneral", idRegGen).getResultList();
		for (Object[] detalle : resultQuery) {
			DetallePestanaNovedadesDTO nuevoDetalle = new DetallePestanaNovedadesDTO();
			nuevoDetalle.setNumIdentificacionCotizante(detalle[0].toString());
			if (detalle[1].toString().equals("PENSIONADO")) { 
				nuevoDetalle.setTipoCotizante(TipoAfiliadoEnum.PENSIONADO);
			}
			else {
				if (detalle[1].toString().equals("TRABAJADOR_DEPENDIENTE")) {
					nuevoDetalle.setTipoCotizante(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
				}
				else {
					nuevoDetalle.setTipoCotizante(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
				}
			}
			try {
				nuevoDetalle.setFechaRadicacion(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(detalle[2].toString()));
				nuevoDetalle.setResultado(TipoTransaccionEnum.valueOf(detalle[3].toString()));
			} catch (Exception e) {
			}
			nuevoDetalle.setUsuario("");
			if (detalle[4] != null) {
				nuevoDetalle.setUsuario(detalle[4].toString());
			}

			result.add(nuevoDetalle);

		}
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#verArchivosProcesadosFinalizados(javax.ws.rs.core.UriInfo,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizados() {
		String firmaServicio = "PilaBusiness.verArchivosProcesadosFinalizados()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		// Calcular la fecha 7 dias antes
		Calendar antier = Calendar.getInstance();
		antier.add(Calendar.DATE, -7);

		List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();

		try {
			Query query = null;
			//queryBuilder.addOrderByDefaultParam("-fechaRecibo");
			query = entityManager.createNamedQuery(NamedQueriesConstants.VER_ARCHIVOS_PROCESADOS_FINALIZADOS);
			query.setParameter("fechaDesde", antier.getTime());

			if (query != null) {
				result = (List<ArchivosProcesadosFinalizadosOFDTO>) query.getResultList();
			} else {
				// no se pudo crear el query, se retorna lista vacía
				result = new ArrayList<>();
			}

		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un listado vacío
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.ARCHIVOS_OPERADOR_FINANCIERO, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#verDetalleBloquesValidacionArchivo(java.lang.Long,
	 *      com.asopagos.enumeraciones.aportes.TipoOperadorEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BloquesValidacionArchivoDTO> verDetalleBloquesValidacionArchivo(Long idPlanilla,
			TipoOperadorEnum tipoOperador) {
		String firmaServicio = "PilaBusiness.verDetalleBloquesValidacionArchivo(Long, TipoOperadorEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<BloquesValidacionArchivoDTO> result = new ArrayList<>();
		DetalleBloqueValidacionArchivoDTO detalle = null;
		List<HistorialEstadoBloque> historialEstados = null;
		BloqueValidacionEnum bloque = null;

		try {
			Query query = null;

			if (TipoOperadorEnum.OPERADOR_INFORMACION.equals(tipoOperador)) {
				query = entityManager.createNamedQuery(NamedQueriesConstants.VER_DETALLE_BLOQUE_ARCHIVO);
			} else {
				query = entityManager.createNamedQuery(NamedQueriesConstants.VER_DETALLE_BLOQUE_ARCHIVO_OF);
			}
			detalle = (DetalleBloqueValidacionArchivoDTO) query.setParameter("idPlanilla", idPlanilla).setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un listado vacío
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.ARCHIVOS_OPERADOR_FINANCIERO, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		// Recibir el detalle y crear el DTO de respuesta por bloque.
		List<String> usuarios = null;

		if (detalle != null) {
			if (detalle.getEstadoB0() != null) {
				if (TipoOperadorEnum.OPERADOR_INFORMACION.equals(tipoOperador)) {
					bloque = BloqueValidacionEnum.BLOQUE_0_OI;
				} else {
					bloque = BloqueValidacionEnum.BLOQUE_0_OF;
				}
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
				usuarios.add(detalle.getUsuario() != null ? detalle.getUsuario() : ConstantesComunesPila.USUARIO_SISTEMA);
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B0, detalle.getEstadoB0(),
						detalle.getFechaB0(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB1() != null) {
				if (TipoOperadorEnum.OPERADOR_INFORMACION.equals(tipoOperador)) {
					bloque = BloqueValidacionEnum.BLOQUE_1_OI;
				} else {
					bloque = BloqueValidacionEnum.BLOQUE_1_OF;
				}
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
				for (HistorialEstadoBloque historial : historialEstados) {
						if (historial.getUsuarioEspecifico() != null) {
							usuarios.add(historial.getUsuarioEspecifico());
						}else{
							usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);

						}
				}
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B1, detalle.getEstadoB1(),
						detalle.getFechaB1(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB2() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_2_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
				for (HistorialEstadoBloque historial : historialEstados) {
						if (historial.getUsuarioEspecifico() != null) {
							usuarios.add(historial.getUsuarioEspecifico());
							}else{
							usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);

						}
				}
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B2, detalle.getEstadoB2(),
						detalle.getFechaB2(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB3() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_3_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
					for (HistorialEstadoBloque historial : historialEstados) {
						if (historial.getUsuarioEspecifico() != null) {
							usuarios.add(historial.getUsuarioEspecifico());
							}else{
							usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);

						}
					}
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B3, detalle.getEstadoB3(),
						detalle.getFechaB3(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB4() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_4_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
				for (HistorialEstadoBloque historial : historialEstados) {
						if (historial.getUsuarioEspecifico() != null) {
							usuarios.add(historial.getUsuarioEspecifico());
						}else{
							usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);

						}
				}
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B4, detalle.getEstadoB4(),
						detalle.getFechaB4(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB5() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_5_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
	
				if (historialEstados.isEmpty()) {
					usuarios.add(detalle.getUsuario() != null ? detalle.getUsuario() : ConstantesComunesPila.USUARIO_SISTEMA);
				} else {
					for (HistorialEstadoBloque historial : historialEstados) {
						if (historial.getUsuarioEspecifico() != null) {
							Short claseUsuario = historial.getClaseUsuario() != null ? historial.getClaseUsuario() : 0;
							switch (claseUsuario) {
							case 1:
								usuarios.add(historial.getUsuarioEspecifico() + " (Usuario Solicitante)");
								break;
							case 2:
								usuarios.add(historial.getUsuarioEspecifico() + " (Usuario Solicitante Creación)");
								break;
							case 3:
								usuarios.add(historial.getUsuarioEspecifico() + " (Usuario Aprobador)");
								break;
							case 4:
								usuarios.add(historial.getUsuarioEspecifico() + " (Usuario Rechazo)");
								break;
							default:
								usuarios.add(historial.getUsuarioEspecifico());
								break;
							}
						} else {
							usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);
						}
					}
				}
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B5, detalle.getEstadoB5(),
						detalle.getFechaB5(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB6() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_6_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
				usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B6, detalle.getEstadoB6(),
						detalle.getFechaB6(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB7() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_7_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
	
				if (historialEstados.isEmpty()) {
					usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);
				} else {
					for (HistorialEstadoBloque historial : historialEstados) {
						if (historial.getUsuarioEspecifico() != null) {
							Short claseUsuario = historial.getClaseUsuario() != null ? historial.getClaseUsuario() : 0;
							switch (claseUsuario) {
							case 3:
								usuarios.add(historial.getUsuarioEspecifico() + " (Usuario Aprobador)");
								break;
							case 5:
								usuarios.add(historial.getUsuarioEspecifico() + " (Usuario Solicitante Reproceso)");
								break;
							default:
								usuarios.add(historial.getUsuarioEspecifico());
								break;
							}
						} else {
							usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);
						}
					}
				}
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B7, detalle.getEstadoB7(),
						detalle.getFechaB7(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB8() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_8_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
	
				if (historialEstados.isEmpty()) {
					usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);
				} else {
					for (HistorialEstadoBloque historial : historialEstados) {
						if (historial.getUsuarioEspecifico() != null) {
							Short claseUsuario = historial.getClaseUsuario() != null ? historial.getClaseUsuario() : 0;
							switch (claseUsuario) {
							case 5:
								usuarios.add(historial.getUsuarioEspecifico() + " (Usuario Solicitante Reproceso)");
								break;
							default:
								usuarios.add(historial.getUsuarioEspecifico());
								break;
							}
						} else {
							usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);
						}
					}
				}
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B8, detalle.getEstadoB8(),
						detalle.getFechaB8(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB9() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_9_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
				
				usuarios.add(ConstantesComunesPila.USUARIO_SISTEMA);
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B9, detalle.getEstadoB9(),
						detalle.getFechaB9(), usuarios, bloque, historialEstados));
			}
			if (detalle.getEstadoB10() != null) {
				bloque = BloqueValidacionEnum.BLOQUE_10_OI;
				historialEstados = consultarHistorialPorEstado(idPlanilla, bloque, tipoOperador);
				usuarios = new ArrayList<>();
				usuarios.add(detalle.getUsuario() != null ? detalle.getUsuario() : ConstantesComunesPila.USUARIO_SISTEMA);
	
				result.add(new BloquesValidacionArchivoDTO(ConstantesParaMensajes.DESC_VALIDACION_B10,
						detalle.getEstadoB10(), detalle.getFechaB10(), usuarios, bloque, historialEstados));
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * Método encargado de la consulta del histórico de estados por bloque de
	 * validación
	 * 
	 * @param idPlanilla
	 *            ID del índice de planilla
	 * @param bloque
	 *            Bloque de validación consultado
	 * @return <b>List<HistorialEstadoBloque></b> Listado con las entradas de
	 *         historial de estados
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<HistorialEstadoBloque> consultarHistorialPorEstado(Long idPlanilla, BloqueValidacionEnum bloque,
			TipoOperadorEnum tipoOperador) {
		String firmaMetodo = "ConsultasModeloPILA.consultarHistorialPorEstado(Long, BloqueValidacionEnum, TipoOperadorEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<HistorialEstadoBloque> result = null;

		List<Object> resultQuery = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORIAL_ESTADOS_POR_BLOQUE)
				.setParameter("idPlanilla", idPlanilla).setParameter("bloque", bloque.name())
				.setParameter("tipoOperador", tipoOperador.name()).getResultList();

		result = new ArrayList<>();
		for (Object object : resultQuery) {
			HistorialEstadoBloque historico = (HistorialEstadoBloque) object;

			result.add(historico);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#verCabeceraDetalleBloquesValidacionArchivo(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CabeceraDetalleArchivoDTO verCabeceraDetalleBloquesValidacionArchivo(Long idPlanilla) {
		String firmaServicio = "PilaBusiness.verCabeceraDetalleBloquesValidacionArchivo(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		CabeceraDetalleArchivoDTO result = null;
		String[] itemsNombreArchivo = null;

		if (idPlanilla != null) {
			IndicePlanilla indicePlanilla = entityManager.find(IndicePlanilla.class, idPlanilla);
			if (indicePlanilla.getNombreArchivo() != null) {
				itemsNombreArchivo = indicePlanilla.getNombreArchivo().split("_");
			}

			result = new CabeceraDetalleArchivoDTO(itemsNombreArchivo != null ? itemsNombreArchivo[3] : null,
					itemsNombreArchivo != null ? itemsNombreArchivo[4] : null, null, indicePlanilla.getIdPlanilla(),
					indicePlanilla.getEstadoArchivo(), indicePlanilla.getNombreArchivo(),
					indicePlanilla.getTipoArchivo(), indicePlanilla.getIdDocumento(),
					indicePlanilla.getVersionDocumento());

			// Consultar los archivos relacionados
			if (indicePlanilla.getIdPlanilla() != null) {
				result.setArchivosRelacionados(this.obtenerArchivosRelacionados(indicePlanilla.getIdPlanilla()));
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#verCabeceraDetalleBloquesValidacionArchivo(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CabeceraDetalleArchivoDTO verCabeceraDetalleBloquesValidacionArchivoOF(Long idPlanilla) {
		String firmaServicio = "PilaBusiness.verCabeceraDetalleBloquesValidacionArchivoOF(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		CabeceraDetalleArchivoDTO result = null;

		if (idPlanilla != null) {
			IndicePlanillaOF indicePlanilla = entityManager.find(IndicePlanillaOF.class, idPlanilla);

			result = new CabeceraDetalleArchivoDTO(null, null, null, null, indicePlanilla.getEstado(),
					indicePlanilla.getNombreArchivo(), indicePlanilla.getTipoArchivo(), indicePlanilla.getIdDocumento(),
					indicePlanilla.getVersionDocumento());
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * Metodo que permite consultar la lista de archivos relacionados a partir
	 * de su número de planilla
	 * 
	 * @param numeroPlanilla
	 *            Numero de la planilla
	 * @return List<String> Con los nombres de los archivos relacionados en
	 *         indice planilla
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<String> obtenerArchivosRelacionados(Long numeroPlanilla) {
		String firmaServicio = "PilaBusiness.obtenerArchivosRelacionados(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<String> result = new ArrayList<>();

		try {
			result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_ARCHIVOS_RELACIONADOS)
					.setParameter("numeroPlanilla", numeroPlanilla).getResultList();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un listado vacío
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.ARCHIVOS_OPERADOR_FINANCIERO, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarCantidadPlanillasPendientesgestionManual()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Integer consultarCantidadPlanillasPendientesgestionManual() {
		String firmaMetodo = "ConsultasModeloPILA.consultarPlanillasParaGestionManual(CriterioConsultaPlanillaManualDTO, UriInfo, "
				+ "HttpServletResponse)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Long result = null;

		try {
			result = (Long) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_PLANILLAS_PENDIENTES_GESTION_MANUAL)
					.setMaxResults(1).getSingleResult();

		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un listado vacío
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.INDICE_PLANILLA, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result.intValue();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarPlanillasParaGestionManual(com.asopagos.pila.dto.CriterioConsultaDTO,
	 *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<PlanillaGestionManualDTO> consultarPlanillasParaGestionManual(CriterioConsultaDTO criterios,
			UriInfo uri, HttpServletResponse response) {
		String firmaMetodo = "ConsultasModeloPILA.consultarPlanillasParaGestionManual(CriterioConsultaPlanillaManualDTO, UriInfo, "
				+ "HttpServletResponse)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Integer cantidadPlanillas = null;
		try {
			cantidadPlanillas = Integer.parseInt(CacheManager.getConstante(ConstantesSistemaConstants.CANTIDAD_PLANILLAS_MANUALES_POR_CICLO).toString());
		} catch (NumberFormatException e) {
			logger.error("error consultando la constante CANTIDAD_PLANILLAS_MANUALES_POR_CICLO : ", e);
			e.printStackTrace();
		}
		
		List<PlanillaGestionManualDTO> result = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLAS_PENDIENTES_GESTION_MANUAL,
						PlanillaGestionManualDTO.class)
				.setParameter("fechaIngreso", criterios.getFechaIngreso())
				.setParameter("numeroPlanilla", criterios.getNumeroPlanilla())
				.setParameter("cantidadPlanillas", cantidadPlanillas != null ? cantidadPlanillas.longValue() : 20).getResultList();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarNumeroPlanillaOriginal(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String consultarNumeroPlanillaOriginal(String numeroPlanilla) {
		String firmaMetodo = "ConsultasModeloPILA.consultarNumeroPlanillaOriginal(String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		String result = null;

		try {
			result = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_PLANILLA_ORIGINAL)
					.setParameter("numeroPlanilla", numeroPlanilla).setMaxResults(1).getSingleResult();

		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un String vacío
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.REGISTRO_1_I_IR_PILA, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#actualizarEstado(java.lang.Long,
	 *      com.asopagos.enumeraciones.pila.BloqueValidacionEnum,
	 *      com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum,
	 *      com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarEstado(Long idIndicePlanilla, BloqueValidacionEnum bloque, EstadoProcesoArchivoEnum estado,
			AccionProcesoArchivoEnum accion) {
		String firmaMetodo = "ConsultasModeloPILA.actualizarEstado(Long, BloqueValidacionEnum, EstadoProcesoArchivoEnum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		EstadoArchivoPorBloque estadoBloque = null;

		try {
			// en primer lugar se consultan los estados por bloque de la
			// planilla
			estadoBloque = (EstadoArchivoPorBloque) entityManager
					.createNamedQuery(NamedQueriesConstants.ACTUALIZACION_ESTADO_PLANILLA)
					.setParameter("idIndicePlanilla", idIndicePlanilla).getSingleResult();

		} catch (Exception e) {
			// se presentan errores en la sentencia, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.INDICE_PLANILLA, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		// sí se cuenta con el estado por bloque, se le sicroniza con el
		// contexto de persistencia para su actualización
		if (estadoBloque != null) {
			estadoBloque = entityManager.merge(estadoBloque);

			HistorialEstadoBloque historialEstado = null;
			EstadoProcesoArchivoEnum estadoPrevio = null;
			AccionProcesoArchivoEnum accionPrevia = null;
			Date fechaPrevia = null;

			switch (bloque) {
			case BLOQUE_7_OI:
				// se actualiza el estado en el índice de la planilla
				estadoBloque.getIndicePlanilla().setEstadoArchivo(estado);

				estadoPrevio = estadoBloque.getEstadoBloque7();
				accionPrevia = estadoBloque.getAccionBloque7();
				fechaPrevia = estadoBloque.getFechaBloque7();

				// se actualiza el estado y acción del bloque respectivo
				estadoBloque.setEstadoBloque7(estado);
				estadoBloque.setAccionBloque7(accion);
				estadoBloque.setFechaBloque7(new Date());
				break;
			case BLOQUE_8_OI:
				// se actualiza el estado en el índice de la planilla
				estadoBloque.getIndicePlanilla().setEstadoArchivo(estado);

				estadoPrevio = estadoBloque.getEstadoBloque8();
				accionPrevia = estadoBloque.getAccionBloque8();
				fechaPrevia = estadoBloque.getFechaBloque8();

				// se actualiza el estado y acción del bloque respectivo
				estadoBloque.setEstadoBloque8(estado);
				estadoBloque.setAccionBloque8(accion);
				estadoBloque.setFechaBloque8(new Date());
				break;
			case BLOQUE_9_OI:
				// se actualiza el estado en el índice de la planilla
				estadoBloque.getIndicePlanilla().setEstadoArchivo(estado);

				estadoPrevio = estadoBloque.getEstadoBloque9();
				accionPrevia = estadoBloque.getAccionBloque9();
				fechaPrevia = estadoBloque.getFechaBloque9();

				// se actualiza el estado y acción del bloque respectivo
				estadoBloque.setEstadoBloque9(estado);
				estadoBloque.setAccionBloque9(accion);
				estadoBloque.setFechaBloque9(new Date());
				break;
			case BLOQUE_10_OI:
				// se actualiza el estado en el índice de la planilla
				estadoBloque.getIndicePlanilla().setEstadoArchivo(estado);

				estadoPrevio = estadoBloque.getEstadoBloque10();
				accionPrevia = estadoBloque.getAccionBloque10();
				fechaPrevia = estadoBloque.getFechaBloque10();

				// se actualiza el estado y acción del bloque respectivo
				estadoBloque.setEstadoBloque10(estado);
				estadoBloque.setAccionBloque10(accion);
				estadoBloque.setFechaBloque10(new Date());
				break;
			default:
				/*
				 * En este servicio, sólo se trabaja con los bloques de
				 * validación correspondientes a las validaciones del Mundo 2 de
				 * PILA para planillas I-IR IP-IPR
				 */
				break;
			}

			if (estadoPrevio != null) {
				historialEstado = new HistorialEstadoBloque();
				historialEstado.setIdIndicePlanilla(idIndicePlanilla);
				historialEstado.setEstado(estadoPrevio);
				historialEstado.setAccion(accionPrevia);
				historialEstado.setFechaEstado(fechaPrevia);
				historialEstado.setBloque(bloque);

				entityManager.persist(historialEstado);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarFechaProcesoIndicePlanilla(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Date consultarFechaProcesoIndicePlanilla(Long idIndicePlanilla) {
		String firmaMetodo = "ConsultasModeloPILA.consultarFechaProcesoIndicePlanilla(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Date result = null;

		try {
			result = (Date) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_PROCESO_PLANILLA)
					.setParameter("idIndicePlanilla", idIndicePlanilla).setMaxResults(1).getSingleResult();

		} catch (NoResultException e) {
			// no se encuentran resultados, se retona un Date nulo
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.INDICE_PLANILLA, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#ejecutarUSPporFaseSimulada(java.lang.Long,
	 *      java.lang.String, com.asopagos.enumeraciones.pila.FasePila2Enum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarUSPporFaseSimulada(Long idIndicePlanilla, String usuario, FasePila2Enum fase) {
		String firmaMetodo = "ConsultasModeloPILA.ejecutarUSPFase1Simulada(Long, String, FasePila2Enum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		//logger.info("los parámetros que llegaron al servicio son: ");
		//logger.info("idIndicePLanilla: " + idIndicePlanilla.toString());
		//logger.info("usuario: "  + usuario);
		//logger.info("fase: " + fase.name());
		
		StoredProcedureQuery sQuery = entityManager
				.createStoredProcedureQuery(NamedStoredProcedureConstants.USP_EXECUTEPILA2);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class,
				ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.FASE_INICIO, String.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.class, ParameterMode.IN);
		sQuery.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
		sQuery.setParameter(ConstantesParametrosSp.FASE_INICIO, fase.toString());
		sQuery.setParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.valueOf(true));
		if (usuario != null) {
			sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.USUARIO_PROCESO, String.class,
					ParameterMode.IN);
			sQuery.setParameter(ConstantesParametrosSp.USUARIO_PROCESO, usuario);
		}
		sQuery.execute();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

	}
	
	
	/** (non-Javadoc)
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#ejecutarUSPporFaseSimulada410(java.lang.Long, java.lang.String, java.lang.Long, boolean, com.asopagos.enumeraciones.pila.FasePila2Enum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long ejecutarUSPporFaseSimulada410(Long idIndicePlanilla, String usuario, Long idTransaccion, boolean reanudarTransaccion, FasePila2Enum fase) {
		String firmaMetodo = "ConsultasModeloPILA.ejecutarUSPFase1Simulada410(Long, String, FasePila2Enum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		logger.info("los parámetros que llegaron al servicio son: ");
		logger.info("idIndicePLanilla: " + idIndicePlanilla.toString());
		logger.info("usuario: "  + usuario);
		logger.info("fase: " + fase.name());
		logger.info("idTransaccion: " + idTransaccion);
		logger.info("reanudarTransaccion: " + reanudarTransaccion);

		StoredProcedureQuery sQuery = entityManager.createStoredProcedureQuery(NamedStoredProcedureConstants.USP_EXECUTEPILA2);
		
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.FASE_INICIO, String.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.USUARIO_PROCESO, String.class,ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.REANUDAR_TRANSACCION_410, Boolean.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_TRANSACCION_410, Long.class, ParameterMode.OUT);
		
		sQuery.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
		sQuery.setParameter(ConstantesParametrosSp.FASE_INICIO, fase.toString());
		sQuery.setParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.valueOf(true));
		sQuery.setParameter(ConstantesParametrosSp.USUARIO_PROCESO, usuario != null ? usuario : "SISTEMA");
		sQuery.setParameter(ConstantesParametrosSp.REANUDAR_TRANSACCION_410, Boolean.valueOf(true));
		//sQuery.setParameter(ConstantesParametrosSp.ID_TRANSACCION_410, idTransaccion);
		
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_PAQUETE, Long.class, ParameterMode.IN);
		sQuery.setParameter(ConstantesParametrosSp.ID_PAQUETE, 0L);

		sQuery.execute();
				
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		
		return (Long)sQuery.getOutputParameterValue(ConstantesParametrosSp.ID_TRANSACCION_410);
	}

	/** (non-Javadoc)
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#ejecutarUSPporFaseSimulada410(java.lang.Long, java.lang.String, java.lang.Long, boolean, com.asopagos.enumeraciones.pila.FasePila2Enum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long ejecutarUSPporFaseSimulada410Adicion(Long idIndicePlanilla, String usuario, Long idTransaccion, boolean reanudarTransaccion, FasePila2Enum fase) {
		String firmaMetodo = "ConsultasModeloPILA.ejecutarUSPFase1Simulada410(Long, String, FasePila2Enum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		logger.info("los parámetros que llegaron al servicio son: ");
		logger.info("idIndicePLanilla: " + idIndicePlanilla.toString());
		logger.info("usuario: "  + usuario);
		logger.info("fase: " + fase.name());
		logger.info("idTransaccion: " + idTransaccion);
		logger.info("reanudarTransaccion: " + reanudarTransaccion);

		StoredProcedureQuery sQuery = entityManager.createStoredProcedureQuery(NamedStoredProcedureConstants.USP_EXECUTEPILA2);
		
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.FASE_INICIO, String.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.USUARIO_PROCESO, String.class,ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.REANUDAR_TRANSACCION_410, Boolean.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_TRANSACCION_410, Long.class, ParameterMode.OUT);
		
		sQuery.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
		sQuery.setParameter(ConstantesParametrosSp.FASE_INICIO, fase.toString());
		sQuery.setParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.valueOf(false));
		sQuery.setParameter(ConstantesParametrosSp.USUARIO_PROCESO, usuario != null ? usuario : "SISTEMA");
		sQuery.setParameter(ConstantesParametrosSp.REANUDAR_TRANSACCION_410, Boolean.valueOf(false));
		//sQuery.setParameter(ConstantesParametrosSp.ID_TRANSACCION_410, idTransaccion);
		
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_PAQUETE, Long.class, ParameterMode.IN);
		sQuery.setParameter(ConstantesParametrosSp.ID_PAQUETE, 0L);

		sQuery.execute();
				
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		
		return (Long)sQuery.getOutputParameterValue(ConstantesParametrosSp.ID_TRANSACCION_410);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarIndicePlanilla(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public IndicePlanilla consultarIndicePlanilla(Long idIndicePlanilla) {
		logger.info("Inicia ConsultasModeloPILA.consultarIndicePlanilla(Long)");
		IndicePlanilla indicePlanilla = null;
		try {
			indicePlanilla = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_PLANILLA_POR_ID, IndicePlanilla.class)
					.setParameter("idIndicePlanilla", idIndicePlanilla).getSingleResult();
		} catch (NoResultException nre) {
			logger.error(
					"ConsultasModeloPILA.consultarIndicePlanilla(Long) :: No se pudo obtener el registro del archiv de planilla PILA :: "
							+ nre.getMessage());
		} catch (NonUniqueResultException e) {
			logger.error(
					"ConsultasModeloPILA.consultarIndicePlanilla(Long) :: Se obtuvo mas de un resultado, se esperaba solo un registro :: "
							+ e.getMessage());
		}
		logger.info("Inicia ConsultasModeloPILA.consultarIndicePlanilla(Long) indicePlanilla: "+indicePlanilla);
		return indicePlanilla;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarEstadoArchivoPorBloque(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadoArchivoPorBloque consultarEstadoArchivoPorBloque(Long idIndicePlanilla) {
		logger.info("Inicia ConsultasModeloPILA.consultarEstadoArchivoPorBloque(Long: "+idIndicePlanilla+")");
		EstadoArchivoPorBloque estadoArchivoPorBloque = null;
		try {
			estadoArchivoPorBloque = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_BLOQUE_PLANILLA_POR_ID,
							EstadoArchivoPorBloque.class)
					.setParameter("idIndicePlanilla", idIndicePlanilla).getSingleResult();
		} catch (NoResultException nre) {
			logger.error(
					"ConsultasModeloStaging.consultarEstadoArchivoPorBloque(Long) :: No se pudo obtener el registro de estados de archivo por bloque de planilla :: "
							+ nre.getMessage());
		} catch (NonUniqueResultException e) {
			logger.error(
					"ConsultasModeloPILA.consultarEstadoArchivoPorBloque(Long) :: Se obtuvo mas de un resultado, se esperaba solo un registro :: "
							+ e.getMessage());
		}
		logger.info("Finaliza ConsultasModeloPILA.consultarEstadoArchivoPorBloque(Long: "+idIndicePlanilla+")");
		return estadoArchivoPorBloque;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#actualizarIndicePlanilla(com.asopagos.entidades.pila.procesamiento.IndicePlanilla)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public IndicePlanilla actualizarIndicePlanilla(IndicePlanilla indicePlanilla) {
		logger.info("**__**Inicia ConsultasModeloPILA.actualizarIndicePlanilla( IndicePlanilla: "+indicePlanilla+" )");
		indicePlanilla = entityManager.merge(indicePlanilla);
		logger.info("**__**NOTIFICACION DE PLANILLA EXITOSO CON MERGE INDICE PLANILLAFinaliza ConsultasModeloPILA.actualizarIndicePlanilla( IndicePlanilla: "+indicePlanilla+" )");
		return indicePlanilla;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#actualizarEstadoArchivoPorBloque(com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque,
	 *      com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public EstadoArchivoPorBloque actualizarEstadoArchivoPorBloque(EstadoArchivoPorBloque estadoArchivoPorBloque,
			HistorialEstadoBloque historialEstado) {
		logger.debug("Inicia ConsultasModeloPILA.actualizarEstadoArchivoPorBloque( EstadoArchivoPorBloque | "+
				" IndicePlanilla:"+estadoArchivoPorBloque.getIndicePlanilla()+" )");
		entityManager.merge(estadoArchivoPorBloque);
		if (historialEstado != null && historialEstado.getEstado() != null) {
			entityManager.persist(historialEstado);
			//logger.debug("Inicia ConsultasModeloPILA.actualizarEstadoArchivoPorBloque( historialEstado: id: "+historialEstado.getId()+"  )");
		}
		logger.debug("Finaliza ConsultasModeloPILA.actualizarEstadoArchivoPorBloque( EstadoArchivoPorBloque )");
		return estadoArchivoPorBloque;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#actualizarHabilitacionPila2Manual(java.lang.Long,
	 *      java.lang.Boolean)
	 */

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarHabilitacionPila2Manual(Long idIndicePlanilla, Boolean nuevaMarca) {
		String firmaMetodo = "ConsultasModeloPILA.actualizarHabilitacionPila2Manual(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		logger.info("actualizarHabilitacionPila2Manual idIndicePlanilla: "+idIndicePlanilla);
		IndicePlanilla indice = consultarIndicePlanilla(idIndicePlanilla);

		entityManager.merge(indice);

		indice.setHabilitadoProcesoManual(nuevaMarca);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloCore#consultarSubtipoCotizante(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Short consultarSubtipoCotizante(Long idRegistro2Pila) {
		String firmaMetodo = "ConsultasModeloPILA.consultarSubtipoCotizante(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Short result = 0;

		try {
			result = (Short) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUBTIPO_COTIZANTE)
					.setParameter("idRegistro2", idRegistro2Pila).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retona 0
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.REGISTRO_2_I_IR_PILA, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#contultarAporteTemporal(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TemAporteModeloDTO contultarAporteTemporal(Long idRegistroDetallado) {
		String firmaMetodo = "ConsultasModeloPILA.contultarAporteTemporal(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		TemAporteModeloDTO result = null;

		try {
			TemAporte resultEntity = (TemAporte) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_SIMULADO_POR_REGISTRO_DETALLADO)
					.setParameter("idRegistroDetallado", idRegistroDetallado).setMaxResults(1).getSingleResult();

			result = new TemAporteModeloDTO();
			result.convertToDTO(resultEntity);
		} catch (NoResultException e) {
			// no se encuentran resultados, se retona null
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.APORTE_TEMPORAL, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#eliminarAporteTemporal(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminarAporteTemporal(Long idRegistroDetallado) {
		String firmaMetodo = "ConsultasModeloPILA.eliminarAporteTemporal(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		entityManager.createNamedQuery(NamedQueriesConstants.BORRAR_APORTE_TEMPORAL)
				.setParameter("idRegistroDetallado", idRegistroDetallado).executeUpdate();

		entityManager.createNamedQuery(NamedQueriesConstants.BORRAR_APORTANTE_TEMPORAL)
				.setParameter("idRegistroDetallado", idRegistroDetallado).executeUpdate();

		entityManager.createNamedQuery(NamedQueriesConstants.BORRAR_COTIZANTE_TEMPORAL)
				.setParameter("idRegistroDetallado", idRegistroDetallado).executeUpdate();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloStaging#consultarNovedadesTemporalesPorRegistroDetallado(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TemNovedadModeloDTO> consultarNovedadesTemporalesPorRegistroDetallado(Long idRegistroDetallado) {
		String firmaMetodo = "ConsultasModeloStaging.consultarNovedadesTemporalesPorRegistroDetallado(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<TemNovedadModeloDTO> result = new ArrayList<>();

		try {
			List<TemNovedad> resultEntity = (List<TemNovedad>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_TEMPORALES_REGISTRO_DETALLADO)
					.setParameter("idRegistroDetallado", idRegistroDetallado).getResultList();

			if (resultEntity != null) {
				for (TemNovedad temNovedad : resultEntity) {
					TemNovedadModeloDTO dto = new TemNovedadModeloDTO();
					dto.convertToDTO(temNovedad);
					result.add(dto);
				}
			}
		} catch (NoResultException e) {
			// se retorna el listado nulo
			mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS
					.getReadableMessage(ConstantesParaMensajes.RESULTADO_NOVEDAD_TEMPORAL);
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			return null;
		} catch (Exception e) {
			// se lanza la excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.RESULTADO_NOVEDAD_TEMPORAL, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#actualizarNovedadesTemporales(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarNovedadesTemporales(List<TemNovedadModeloDTO> listaNovedadesTemporales) {
		//String firmaMetodo = "ConsultasModeloStaging.habilitarNovedadesTemporalesEspecificas(List<TemNovedad>)";
		//logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		if (listaNovedadesTemporales != null && !listaNovedadesTemporales.isEmpty()) {
			for (TemNovedadModeloDTO temNovedadDTO : listaNovedadesTemporales) {
				TemNovedad temNovedad = entityManager.merge(temNovedadDTO.convetToEntity());
				temNovedad.setMarcaNovedadSimulado(false);
			}
		}
		//logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#eliminarNovedadesTemporalesEspecificas(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Deprecated
	public void eliminarNovedadesTemporalesEspecificas(List<TemNovedad> listaNovedadesTemporales) {
		String firmaMetodo = "ConsultasModeloStaging.eliminarNovedadesTemporalesEspecificas(List<TemNovedad>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		if (listaNovedadesTemporales != null && !listaNovedadesTemporales.isEmpty()) {
			entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_NOVEDADES_TEMPORALES_ESPECIFICAS)
					.setParameter("listadoNovedades", listaNovedadesTemporales).executeUpdate();
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#actualizarAporteTemporal(com.asopagos.entidades.pila.temporal.TemAporte)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarAporteTemporal(TemAporte aporteTemporal) {
		String firmaMetodo = "ConsultasModeloStaging.actualizarAporteTemporal(TemAporte)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		entityManager.merge(aporteTemporal);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarAportesTemporalesPorRegistroGeneral(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TemAporteModeloDTO> consultarAportesTemporalesPorRegistroGeneral(Long idRegistroGeneral) {
		String firmaMetodo = "ConsultasModeloStaging.consultarAportesTemporalesPorRegistroGeneral(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<TemAporteModeloDTO> respuesta = null;

		try {
			List<TemAporte> respuestaEntity = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_TEMPORAL_REGISTRO_GENERAL)
					.setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

			if (respuestaEntity != null) {
				respuesta = new ArrayList<>();
				for (TemAporte temAporte : respuestaEntity) {
					TemAporteModeloDTO dto = new TemAporteModeloDTO();
					dto.convertToDTO(temAporte);
					respuesta.add(dto);
				}
			}
		} catch (NoResultException e) {
			// se retorna el listado vacío
			mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS
					.getReadableMessage(ConstantesParaMensajes.APORTE_TEMPORAL);
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			return Collections.emptyList();
		} catch (Exception e) {
			// se lanza la excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.APORTE_TEMPORAL, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarNovedadesTemporalesPorRegistroGeneral(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TemNovedadModeloDTO> consultarNovedadesTemporalesPorRegistroGeneral(Long idRegistroGeneral) {
		String firmaMetodo = "ConsultasModeloStaging.consultarNovedadesTemporalesPorRegistroGeneral(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<TemNovedadModeloDTO> respuesta = null;

		try {
			List<TemNovedad> respuestaEntity = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_TEMPORALES_REGISTRO_GENERAL)
					.setParameter("idRegistroGeneral", idRegistroGeneral).getResultList();

			if (respuestaEntity != null) {
				respuesta = new ArrayList<>();
				for (TemNovedad temAporte : respuestaEntity) {
					TemNovedadModeloDTO dto = new TemNovedadModeloDTO();
					dto.convertToDTO(temAporte);
					respuesta.add(dto);
				}
			}
		} catch (NoResultException e) {
			// se retorna el listado vacío
			mensaje = MensajesErrorPersistenciaEnum.SIN_RESULTADOS
					.getReadableMessage(ConstantesParaMensajes.RESULTADO_NOVEDAD_TEMPORAL);
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			return Collections.emptyList();
		} catch (Exception e) {
			// se lanza la excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.RESULTADO_NOVEDAD_TEMPORAL, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#verArchivosProcesadosFinalizadosOI(javax.ws.rs.core.UriInfo,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivosProcesadosFinalizadosOFDTO> verArchivosProcesadosFinalizadosOI(UriInfo uri,
			HttpServletResponse response) {
		String firmaMetodo = "ConsultasModeloStaging.verArchivosProcesadosFinalizadosOI(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ArchivosProcesadosFinalizadosOFDTO> result = new ArrayList<>();

		try {
			QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
			queryBuilder.addParam("fechaProceso", CalendarUtils.truncarHora(CalendarUtils.restarDias(new Date(), 7)));
			queryBuilder.addOrderByDefaultParam("-fechaRecibo");

			Query query = queryBuilder.createQuery(NamedQueriesConstants.VER_ARCHIVOS_PROCESADOS_FINALIZADOS_OI, null);

			if (query != null) {
				result = (List<ArchivosProcesadosFinalizadosOFDTO>) query.getResultList();
			} else {
				// no se pudo crear el query, se retorna lista vacía

				logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
				return Collections.emptyList();
			}

		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.ARCHIVOS_OPERADOR_FINANCIERO, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarExistenciaProcesosPilaPorEstadoYTipo(java.util.List,
	 *      com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean consultarExistenciaProcesosPilaPorEstadoYTipo(List<TipoProcesoPilaEnum> tiposProceso,
			EstadoProcesoValidacionEnum estadoProceso) {
		String firmaMetodo = "ConsultasModeloStaging.verArchivosProcesadosFinalizadosOI(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Boolean result = null;

		try {
			List<ProcesoPila> procesos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_PROCESOS_PILA)
					.setParameter("estadoProceso", estadoProceso).setParameter("listaTipos", tiposProceso)
					.getResultList();

			if (procesos != null && !procesos.isEmpty()) {
				result = Boolean.TRUE;
			} else {
				result = Boolean.FALSE;
			}
		} catch (NoResultException e) {
			result = Boolean.FALSE;
		} catch (Exception e) {
			// se presentan errores en la consulta, se lanza excepción técnica
			mensaje = MensajesErrorPersistenciaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesParaMensajes.PROCESOS_PILA, e.getMessage());
			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: " + mensaje, e);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarEstadoArchivoPorBloquePorNumeroYTipo(java.lang.Long,
	 *      java.util.List, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstadoArchivoPorBloque> consultarEstadoArchivoPorBloquePorNumeroYTipo(Long idPlanilla,
			List<TipoArchivoPilaEnum> listaTipos, List<EstadoProcesoArchivoEnum> estados) {
		String firmaMetodo = "ConsultasModeloStaging.consultarEstadoArchivoPorBloquePorNumeroYTipo(Long, List<TipoArchivoPilaEnum>, "
				+ "List<EstadoProcesoArchivoEnum>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<EstadoArchivoPorBloque> result = null;

		try {
			result = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_BLOQUE_PLANILLA_POR_NUMERO_TIPO,
							EstadoArchivoPorBloque.class)
					.setParameter("idPlanilla", idPlanilla).setParameter("tiposArchivo", listaTipos)
					.setParameter("estados", estados).getResultList();
		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * Método para obtener listados de estados con inconsistencias para consulta
	 */
	private List<String> obtenerListadoEstadosInconsistentes(Integer tipo) {
		List<String> result = new ArrayList<>();

		switch (tipo) {
		case 1:
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO_GRUPO.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_NO_VALIDA.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_DOBLE.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_SIN_FECHA_MODIFICACION.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO_ANTERIOR.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_GRUPO_NO_VALIDO.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_REPROCESO_PREVIO.name());
			result.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_ANULACION_FALLIDA.name());
			result.add(EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO.name());
			result.add(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA.name());
			result.add(EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_INCONSISTENTES.name());
			result.add(EstadoProcesoArchivoEnum.APORTANTE_NO_CREADO_EN_BD.name());
			result.add(EstadoProcesoArchivoEnum.GESTIONAR_DIFERENCIA_EN_CONCILIACION.name());
			result.add(EstadoProcesoArchivoEnum.RECAUDO_VALOR_CERO_CONCILIADO.name());
			result.add(EstadoProcesoArchivoEnum.PENDIENTE_CONCILIACION.name());
			break;
		case 2:
			result.add(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA.name());
			result.add(EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO.name());
			break;
		case 3:
			result.add(EstadoConciliacionArchivoFEnum.REGISTRO_6_GESTIONAR_DIFERENCIAS.name());
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#consultarIdIndicePlanillaOriginal(java.lang.Long)
	 */
	@Override
	public Long consultarIdIndicePlanillaOriginal(Long idOriginal) {
		String firmaMetodo = "ConsultasModeloStaging.consultarIdIndicePlanillaOriginal(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Object resultQuery = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_ORIGINAL)
				.setParameter("idOriginal", idOriginal).getSingleResult();
		Long result = resultQuery != null ? ((BigInteger) resultQuery).longValue() : null;

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.pila.business.interfaces.IConsultasModeloPILA#ejecutarUSPCopiarPlanillaporFaseSimulada(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ejecutarUSPCopiarPlanillaporFaseSimulada(Long idIndicePlanilla, String usuario) {
		String firmaMetodo = "ConsultasModeloPILA.ejecutarUSPFase1Simulada(Long, String, FasePila2Enum)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		StoredProcedureQuery sQuery = entityManager
				.createStoredProcedureQuery(NamedStoredProcedureConstants.USP_EXECUTE_PILA2_COPIAR_PLANILLA);

		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class,
				ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.FASE_INICIO, String.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.USUARIO_PROCESO, String.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_PAQUETE, Long.class, ParameterMode.IN);
		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.BORRAR_BLOQUE_STAGING, Boolean.class,
				ParameterMode.IN);

		sQuery.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
		sQuery.setParameter(ConstantesParametrosSp.FASE_INICIO, FasePila2Enum.SIN_PARAMETRO.toString());
		sQuery.setParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.valueOf(true));
		sQuery.setParameter(ConstantesParametrosSp.USUARIO_PROCESO, usuario != null ? usuario : "SISTEMA");
		sQuery.setParameter(ConstantesParametrosSp.ID_PAQUETE, 0L);
		sQuery.setParameter(ConstantesParametrosSp.BORRAR_BLOQUE_STAGING, Boolean.valueOf(true));

		sQuery.registerStoredProcedureParameter(ConstantesParametrosSp.ID_TRANSACCION, Long.class, ParameterMode.OUT);

		sQuery.execute();
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

	}

	@Override
	public void actualizarEjecucionGestion(String proceso, Boolean activo, String estado) {
		String firmaMetodo = "ConsultasModeloPila.actualizarEjecucionGestion(String proceso, Boolean activo, String estado)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		
		Date fechaActual =  new Date();
		
		entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_EJECUCION_GESTION_INCONSISTENCIAS)
			.setParameter("proceso", proceso)
			.setParameter("activo", activo)
			.setParameter("fecha", fechaActual)
			.setParameter("estado", estado).executeUpdate();
					
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}

	@Override
	public Boolean consultarEjecucionGestion(String proceso) {
		String firmaMetodo = "ConsultasModeloPila.consultarEjecucionGestion(String proceso)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);	  
		  
		Boolean result = false;

		try {
			Object resultQuery = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_EJECUCION_GESTION_INCONSISTENCIAS)
					.setParameter("proceso", proceso).getSingleResult();

			result = ((Boolean) resultQuery).booleanValue();
		} catch (NoResultException e) {
			result = false;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		
		return result;
		
	}
    
    public List<Long> consultarIndicePlanillaRegistroGeneral(List<Long> regGenerales){
        String firmaMetodo = "ConsultasModeloStaging.consultarIndicePlanillaRegistroGeneral(List<Long> regGenerales)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<Long> result = null;
        
		List<BigInteger> resultQuery = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_REGENERAL)
				.setParameter("regGenerales", regGenerales).getResultList();
        
		result = new ArrayList<>();
        for (BigInteger registro : resultQuery) {
            result.add(registro.longValue());
        }

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
		return result;
    }
	
	@Override
	public boolean validarEstadoEjecucion410() {
		String firma = "ConsultasModeloPILA.validarEstadoEjecucion410()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		
		//por defecto se devuelve true indicando que tiene una ejecución activa
		boolean isActivo = true;
		Object resultQuery = new Object();
		
		try {
			resultQuery = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_EJECUCION_GESTION_INCONSISTENCIAS)
					.setParameter("proceso", "410").getSingleResult();

		} catch (NoResultException nre) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firma + " :: No se encontró registro para el proceso " + nre);
			throw new TechnicalException(nre);
		}
		catch (NonUniqueResultException nure) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firma + " :: Se encontró más de un registro para el proceso " + nure);
			throw new TechnicalException(nure);
		}
		
		isActivo = ((Boolean) resultQuery).booleanValue();
		
		if(!isActivo){
			actualizarEjecucionGestion("410", true, "EN_PROCESO");
		}else{
			try {
				Object validacionBloqueo = entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_BLOQUEO_PROCESO_410_AUTOMATICO)
						.setParameter("proceso", "410").getSingleResult();
				
				if(validacionBloqueo != null){
					isActivo = false;
					actualizarEjecucionGestion("410", true, "EN_PROCESO");
				}
			} catch (NoResultException nre) {
				return isActivo;
			} 
			catch (NonUniqueResultException nure) {
				logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firma + " :: Se encontró más de un registro para el proceso " + nure);
				throw new TechnicalException(nure);
			}
		}
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firma);
		return isActivo;
	}


	@Override
	public Object[] consultarDatosEmpleadorByRegistroDetallado(Long idRegDetallado) {
		String firma = "ConsultasModeloPILA.consultarDatosEmpleadoByRegistroDetallado()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

		try {
			return (Object[])entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_BY_REGISTRO_DETALLADO)
				.setParameter("idRegDetallado", idRegDetallado).getResultList().get(0);

		} catch (NoResultException nre) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firma + " :: No se encontró registro para el proceso " + nre);
			throw new TechnicalException(nre);
		}
		
	}

	@Override
	public Object[] consultarDatosAfiliacionByRegistroDetallado(Long idRegDetallado) {
		String firma = "ConsultasModeloPILA.consultarDatosAfiliacionByRegistroDetallado()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

		try {
			return (Object[])entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_AFILIACION_BY_REGISTRO_DETALLADO)
				.setParameter("idRegDetallado", idRegDetallado).getResultList().get(0);

		} catch (NoResultException nre) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firma + " :: No se encontró registro para el proceso " + nre);
			throw new TechnicalException(nre);
		}
		
	}

	@Override
	public List<Long> reprocesarPlanillasM1(List<Long> idIndicePlanilla) {
		String firma = "ConsultasModeloPILA.reprocesarPlanillasM1()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

		String strIds = "";
		for (Long ids : idIndicePlanilla) {
			strIds += ids.toString() + ",";
		}

		List<Object> planillas = entityManager.createStoredProcedureQuery(NamedStoredProcedureConstants.USP_ReprocesarPlanillaM1)
                .registerStoredProcedureParameter("pipId", String.class, ParameterMode.IN)
                .setParameter("pipId",strIds)
				.getResultList();

		List<Long> res = new ArrayList<>();

		if (planillas != null) {
			for (Object o : planillas) {
				res.add(Long.valueOf(o.toString()));
			}
		}
		return res;
	}

	@Override
	public List<Long> reprocesarPlanillasB3(){
	String firma = "ConsultasModeloPILA.reprocesarPlanillasB3()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		try{

		List<Object> planillas = entityManager.createStoredProcedureQuery(NamedStoredProcedureConstants.USP_ReprocesarB3)
				.getResultList();

		List<Long> res = new ArrayList<>();

		if (planillas != null) {
			for (Object o : planillas) {
				res.add(Long.valueOf(o.toString()));
			}
		}
		return res;
		}catch(Exception e){
			logger.error("No hay planillas por reprocesar B3");
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

}




