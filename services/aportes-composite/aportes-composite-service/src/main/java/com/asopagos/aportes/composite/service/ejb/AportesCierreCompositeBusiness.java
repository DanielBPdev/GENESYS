package com.asopagos.aportes.composite.service.ejb;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.Asynchronous;
import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;


import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.aportes.clients.ConciliarAporteGeneral;
import com.asopagos.aportes.clients.ConsultarRegistrosCierreRecaudo;
import com.asopagos.aportes.clients.ConsultarResultadoRegistroRecaudoAportes;
import com.asopagos.aportes.clients.ConsultarSolicitudCierreRecaudo;
import com.asopagos.aportes.clients.GenerarArchivoCierreRecaudo;
import com.asopagos.aportes.clients.GuardarSolicitudCierreRecaudo;
import com.asopagos.aportes.composite.dto.ResultadoCierreDTO;
import com.asopagos.aportes.composite.service.AportesCierreCompositeService;
import com.asopagos.aportes.dto.DetalleRegistroDTO;
import com.asopagos.aportes.dto.GeneracionArchivoCierreDTO;
import com.asopagos.aportes.dto.RegistroAporteDTO;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.enumeraciones.aportes.MarcaPeriodoEnum;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import com.asopagos.enumeraciones.aportes.TipoRegistroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.RetomarTarea;
import com.asopagos.tareashumanas.clients.SuspenderTarea;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.TokenDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.asopagos.util.CalendarUtils;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.annotation.Resource;

/**
 * <b>Descripción: EJB que contiene la lógica de negocio para el proceso de
 * cierre de recaudo 2.1.5</b>
 * 
 * @author Angélica Toro Murillo<atoro@heinsohn.com.co>
 */
@Stateless
public class AportesCierreCompositeBusiness implements AportesCierreCompositeService {

	/**
	 * Instancia del gestor de registro de eventos.
	 */
	private static final ILogger logger = LogManager.getLogger(AportesCierreCompositeBusiness.class);


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.composite.service.AportesCierreCompositeService#
	 * generarCierre(com.asopagos.enumeraciones.aportes.TipoCierreEnum,
	 * java.lang.Long, java.lang.Long,
	 * com.asopagos.aportes.composite.service.UsertDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generarCierre(TipoCierreEnum tipoCierre, Long fechaInicio,
			Long fechaFinSinHora, UserDTO userDTO) {

		String firmaServicio = "AportesCierreCompositeBusiness.generarCierreasync(TipoCierreEnum, Long, Long, UserDTO)";

		logger.info("INICIA --> " + firmaServicio);

		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		logger.debug("Proceso de generarCierre - SolicitudCierreRecaudoModeloDTO ");
		
		;

		// se asegura que la fecha fin esté ubicada al final del día
		LocalDate fin = Instant.ofEpochMilli(fechaFinSinHora).atZone(ZoneId.systemDefault()).toLocalDate();
		fin = fin.plusDays(1);
		Long fechaFin = fin.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;
		
		if (fechaFinSinHora != null) {
			logger.debug(fechaFinSinHora);
			Date d = new Date(fechaFinSinHora);
			d = CalendarUtils.truncarHora(new Date(fechaFinSinHora)); 
			fechaFinSinHora = d.getTime();
			logger.debug(fechaFinSinHora);
		}

		/* se arma la solicitud y se almacena. */
		SolicitudCierreRecaudoModeloDTO solicitudCierreDTO = new SolicitudCierreRecaudoModeloDTO();

		solicitudCierreDTO.setFechaFin(fechaFinSinHora);
		solicitudCierreDTO.setFechaInicio(fechaInicio);
		solicitudCierreDTO.setTipoCierre(tipoCierre);
		solicitudCierreDTO.setDestinatario(userDTO.getNombreUsuario());
		solicitudCierreDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
		solicitudCierreDTO.setTipoTransaccion(TipoTransaccionEnum.CIERRE_RECAUDO);
		solicitudCierreDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
		solicitudCierreDTO.setFechaCreacion(new Date().getTime());
		solicitudCierreDTO.setEstadoSolicitud(EstadoSolicitudCierreRecaudoEnum.GENERADA);


		//guardarSolicitudCierreRecaudo
			Long idSolicitud = guardarSolicitudCierreRecaudo(solicitudCierreDTO);


        
		/* Solicitud cierre de recaudo modelo DTO  */
		logger.debug("Fecha fin sin hora " + fechaFinSinHora+
		"Fecha inicio " + fechaInicio+
		"tipo cierre  " + tipoCierre+
		"userDTO" + userDTO.getNombreUsuario()+
		"CanalRecepcionEnum " + CanalRecepcionEnum.PRESENCIAL+
		"TipoTransaccionEnum " + TipoTransaccionEnum.CIERRE_RECAUDO+
		"userDTO - radicacion  " + userDTO.getNombreUsuario()+
		"EstadoSolicitudCierreRecaudoEnum " + EstadoSolicitudCierreRecaudoEnum.GENERADA
		);

		/* Se genera el número de radicación para enviar al bpm */
		String numeroRadicacion = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());
		logger.debug(" Numero de radicacion "+numeroRadicacion);
		/* se inicia el proceso BPM para el cierre o precierre */
		Map<String, Object> params = new HashMap<>();

		String tiempoProcesoSolicitud = (String) CacheManager
				.getParametro(ParametrosSistemaConstants.BPM_CRAYCO_TIEMPO_PROCESO_SOLICITUD);
		String tiempoPendienteInformacion = (String) CacheManager
			.getParametro(ParametrosSistemaConstants.BPM_AM_TIEMPO_PENDIENTE_INFORMACION);


		params.put("numeroRadicado", numeroRadicacion);
		params.put("idSolicitud", idSolicitud);
		params.put("analistaAportes", userDTO.getNombreUsuario());
		params.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);


		//fase 1
		Long idInstancia = iniciarProceso(ProcesoEnum.CIERRE_RECAUDO, params);
	

		
		try {
			generarCierreAsinc(idInstancia, fechaInicio, fechaFin, numeroRadicacion, fechaFinSinHora, userDTO);

		} catch (Exception e) {

			logger.debug(" Exception  -   GuardarSolicitudCierreRecaudo ");
            e.printStackTrace();
        }

		logger.info("FINALIZA --> " + firmaServicio);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return numeroRadicacion;
		//return solicitudCierreDTO;

	}

    @Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void generarCierreAsinc(Long idInstancia, Long fechaInicio, Long fechaFin,
			String numeroRadicacion, Long fechaFinSinHora, UserDTO userDTO) {
		String firmaServicio = "AportesCierreCompositeBusiness.generarCierreAsync(TipoCierreEnum, Long, Long, UserDTO)";
		logger.info("INICIA --> " + firmaServicio);
		Long idTarea = null;
		SolicitudCierreRecaudoModeloDTO solicitudCierreDTO = new SolicitudCierreRecaudoModeloDTO();
				/* se consulta de nuevo la solicitud por si ha sufrido cambios */
		solicitudCierreDTO = consultarSolicitudCierreRecaudo(numeroRadicacion);
		try {
			solicitudCierreDTO.setIdInstanciaProceso(idInstancia.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		// Se consulta el resumen del recaudo y se guarda por el
		List<ResumenCierreRecaudoDTO> resumenRecaudo = consultarResumenCierreRecaudo(fechaInicio, fechaFin);

		try {
			for (ResumenCierreRecaudoDTO resumenCierreRecaudoDTO : resumenRecaudo) {
				if ( MarcaPeriodoEnum.RESUMEN.equals( resumenCierreRecaudoDTO.getPeriodo() ) ) {

					ObjectMapper mapper = new ObjectMapper();
					String jsonPayload;
					jsonPayload = mapper.writeValueAsString(resumenCierreRecaudoDTO);

					if (jsonPayload != null && !jsonPayload.isEmpty() ) { solicitudCierreDTO.setResumen(jsonPayload); }
				}
			}
			guardarSolicitudCierreRecaudo(solicitudCierreDTO);
		} catch (JsonProcessingException e) {

			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			logger.debug("JsonProcessingException "+e);

			throw new TechnicalException(e);
		}
		
		try {
            //Una vez iniciada la tarea, se suspende la tarea para que se retome en badeja
            //Solo hasta que termine el proceso
            //idTarea = suspenderTarea(idInstancia);
            
            GeneracionArchivoCierreDTO generacionArchivo = new GeneracionArchivoCierreDTO(fechaInicio, fechaFin, resumenRecaudo);
            GenerarArchivoCierreRecaudo archivoSrv = new GenerarArchivoCierreRecaudo(generacionArchivo);
            archivoSrv.execute();
            SolicitudCierreRecaudoModeloDTO solicitudCierreDetalleDTO = consultarSolicitudCierreRecaudo(numeroRadicacion);
            SolicitudCierreRecaudoModeloDTO datosArchivo = archivoSrv.getResult();

			logger.info("archivoSrv.getResult() " +archivoSrv.getResult());

			logger.info("datosArchivo.getIdEcm() " +datosArchivo.getIdEcm());

            solicitudCierreDetalleDTO.setIdEcm(datosArchivo.getIdEcm());
            solicitudCierreDetalleDTO.setIdsAportesGenerales(datosArchivo.getIdsAportesGenerales());

			logger.info("solicitudCierreDetalleDTO  --> " + solicitudCierreDetalleDTO);

            /*
             * se almacena la actualización de la solicitud con el id de la
             * instancia del proceso.
             */
			logger.debug(" proceso -> list -> guardarSolicitudCierreRecaudo ");
            guardarSolicitudCierreRecaudo(solicitudCierreDetalleDTO);

        } catch (Exception e) {

			logger.debug(" Exception  -   GuardarSolicitudCierreRecaudo ");

            //retomarTarea(idTarea);
            e.printStackTrace();
        }
		logger.debug(" proceso -> retomar tarea ");
		//retomarTarea(idTarea);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		logger.info("FINALIZA --> " + firmaServicio);
		}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.composite.service.AportesCierreCompositeService#
	 * emitirResultadoAnalista(com.asopagos.aportes.composite.dto.
	 * ResultadoCierreDTO, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void emitirResultadoAnalista(ResultadoCierreDTO resultadoCierre, UserDTO userDTO) {
		try {
			logger.debug("Inicio de método emitirResultadoAnalista");

			SolicitudCierreRecaudoModeloDTO solicitudCierreDTO = consultarSolicitudCierreRecaudo(resultadoCierre.getNumeroRadicacion());

			solicitudCierreDTO.setEstadoSolicitud(resultadoCierre.getEstado());
			solicitudCierreDTO.setUsuarioSupervisor(resultadoCierre.getUsuario());
			guardarSolicitudCierreRecaudo(solicitudCierreDTO);

			Map<String, Object> params = new HashMap<>();
			params.put("aprobadoAnalista",  EstadoSolicitudCierreRecaudoEnum.PENDIENTE_APROBACION_SUPERVISOR.equals(resultadoCierre.getEstado()) ? Boolean.TRUE : Boolean.FALSE);
			params.put("supervisorAportes", resultadoCierre.getUsuario());
			terminarTarea(resultadoCierre.getIdTarea(), params);

			logger.debug("Fin de método emitirResultadoAnalista");
			logger.debug("Fin de método emitirResultadoAnalista");

		} catch (AsopagosException ae) {
			logger.debug("AsopagosException "+ae);
			throw ae;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en emitirResultadoAnalista", e);
			logger.debug("Ocurrió un error inesperado en emitirResultadoAnalista", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.composite.service.AportesCierreCompositeService#
	 * emitirResultadoSupervisor(com.asopagos.aportes.composite.dto.
	 * ResultadoCierreDTO, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void emitirResultadoSupervisor(ResultadoCierreDTO resultadoCierre, UserDTO userDTO) {
		try {
			logger.debug("Inicio de método emitirResultadoSupervisor");
			SolicitudCierreRecaudoModeloDTO solicitudCierreDTO = consultarSolicitudCierreRecaudo(
					resultadoCierre.getNumeroRadicacion());
			solicitudCierreDTO.setEstadoSolicitud(resultadoCierre.getEstado());
			solicitudCierreDTO.setObservacionesSupervisor(resultadoCierre.getObservaciones());
			solicitudCierreDTO.setUsuarioAnalistaContable(resultadoCierre.getUsuario());
			guardarSolicitudCierreRecaudo(solicitudCierreDTO);
			if (EstadoSolicitudCierreRecaudoEnum.RECHAZADA_SUPERVISOR.equals(resultadoCierre.getEstado())) {
				Map<String, String> paramsComunicado = new HashMap<>();
				paramsComunicado.put("cargoUsuario", "Analista de Aportes");
				paramsComunicado.put("cargoUsuarioRemitente", "Supervisor de Aportes");

				NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
				notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.RCHZ_SUP_APO);
				notificacion.setTipoTx(TipoTransaccionEnum.CIERRE_RECAUDO);
				notificacion.setReplantearDestinatarioTO(true);
				notificacion.setProcesoEvento(ProcesoEnum.CIERRE_RECAUDO.name());
				notificacion.setParams(paramsComunicado);
				notificacion.setIdSolicitud(solicitudCierreDTO.getIdSolicitud());
				UsuarioCCF usuarioAnalistaAporte = obtenerDatoUsuario(solicitudCierreDTO.getUsuarioRadicacion());
				List<String> destinatarios = new ArrayList<>();
				destinatarios.add(usuarioAnalistaAporte != null ? usuarioAnalistaAporte.getEmail() : null);
				notificacion.setDestinatarioTO(destinatarios);
				enviarNotificacionComunicado(notificacion);
			}

			Map<String, Object> params = new HashMap<>();
			params.put("aprobadoSupervisor",
					EstadoSolicitudCierreRecaudoEnum.PENDIENTE_APROBACION_CONTABLE.equals(resultadoCierre.getEstado())
							? Boolean.TRUE : Boolean.FALSE);
			params.put("analistaContable", resultadoCierre.getUsuario());
			terminarTarea(resultadoCierre.getIdTarea(), params);
			logger.debug("Fin de método emitirResultadoSupervisor");
		} catch (AsopagosException ae) {
			throw ae;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en emitirResultadoSupervisor", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.aportes.composite.service.AportesCierreCompositeService#
	 * emitirResultadoAnalistaContable(com.asopagos.aportes.composite.
	 * dto.ResultadoCierreDTO, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void emitirResultadoAnalistaContable(ResultadoCierreDTO resultadoCierre, UserDTO userDTO) {
		try {
			logger.debug("Inicio de método emitirResultadoAnalistaContable");
			SolicitudCierreRecaudoModeloDTO solicitudCierreDTO = consultarSolicitudCierreRecaudo(
					resultadoCierre.getNumeroRadicacion());
			solicitudCierreDTO.setEstadoSolicitud(resultadoCierre.getEstado());
			solicitudCierreDTO.setObservacionesContabilidad(resultadoCierre.getObservaciones());
			guardarSolicitudCierreRecaudo(solicitudCierreDTO);
			NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
			notificacion.setTipoTx(TipoTransaccionEnum.CIERRE_RECAUDO);
			notificacion.setReplantearDestinatarioTO(true);
			notificacion.setProcesoEvento(ProcesoEnum.CIERRE_RECAUDO.name());
			notificacion.setIdSolicitud(solicitudCierreDTO.getIdSolicitud());
			List<String> destinatarios = new ArrayList<>();

			if (EstadoSolicitudCierreRecaudoEnum.CERRADA.equals(resultadoCierre.getEstado())) {
				notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.APR_ANL_CON);

				for (int i = 0; i < 2; i++) {
					if (i == 0) {
						Map<String, String> paramsComunicado = new HashMap<>();
						paramsComunicado.put("cargoUsuarioRemitente", "Analista Contable");
						paramsComunicado.put("cargoUsuario", "Analista de Aportes");
						paramsComunicado.put("nombreUsuarioNotificacion", solicitudCierreDTO.getUsuarioRadicacion());
						notificacion.setParams(paramsComunicado);
						UsuarioCCF usuarioAnalistaAporte = obtenerDatoUsuario(
								solicitudCierreDTO.getUsuarioRadicacion());
						destinatarios.add(usuarioAnalistaAporte != null ? usuarioAnalistaAporte.getEmail() : null);
						notificacion.setDestinatarioTO(destinatarios);
						enviarNotificacionComunicado(notificacion);
					} else {
						Map<String, String> paramsComunicado = new HashMap<>();
						paramsComunicado.put("cargoUsuarioRemitente", "Analista Contable");
						paramsComunicado.put("cargoUsuario", "Supervisor de Aportes");
						paramsComunicado.put("nombreUsuarioNotificacion", solicitudCierreDTO.getUsuarioSupervisor());
						notificacion.setParams(paramsComunicado);
						UsuarioCCF usuarioSupervisorAporte = obtenerDatoUsuario(
								solicitudCierreDTO.getUsuarioSupervisor());
						destinatarios.add(usuarioSupervisorAporte != null ? usuarioSupervisorAporte.getEmail() : null);
						notificacion.setDestinatarioTO(destinatarios);
						enviarNotificacionComunicado(notificacion);
					}
				}
				if (TipoCierreEnum.CIERRE.equals(solicitudCierreDTO.getTipoCierre())) {
					conciliarAporteGeneral(solicitudCierreDTO.getIdsAportesGenerales());
				}
			} else if (EstadoSolicitudCierreRecaudoEnum.RECHAZADA_CONTABILIDAD.equals(resultadoCierre.getEstado())) {
				notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.RCHZ_ANL_CON);
				Map<String, String> paramsComunicado = new HashMap<>();
				paramsComunicado.put("cargoUsuario", "Analista de aportes");
				paramsComunicado.put("cargoUsuarioRemitente", "Analista Contable");
				notificacion.setParams(paramsComunicado);
				UsuarioCCF usuarioAnalistaAporte = obtenerDatoUsuario(solicitudCierreDTO.getUsuarioRadicacion());
				destinatarios.add(usuarioAnalistaAporte != null ? usuarioAnalistaAporte.getEmail() : null);
				notificacion.setDestinatarioTO(destinatarios);
				enviarNotificacionComunicado(notificacion);
			}
			terminarTarea(resultadoCierre.getIdTarea(), null);
			logger.debug("Fin de método emitirResultadoAnalistaContable");
		} catch (AsopagosException ae) {
			throw ae;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en emitirResultadoAnalistaContable", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.composite.service.AportesCierreCompositeService#consultarResumenCierreRecaudo(java.util.List)
	 */
	@Override
	public List<ResumenCierreRecaudoDTO> consultarResumenCierreRecaudo(Long fechaInicio, Long fechaFin) {
		try {
			logger.debug("Inicio de método consultarResumenCierreRecaudo(Long fechaInicio, Long fechaFin)");
			logger.info("Inicio de método consultarResumenCierreRecaudo(Long fechaInicio, Long fechaFin)");

			List<ResumenCierreRecaudoDTO> registrosCierre = consultarRegistrosCierreRecaudo(fechaInicio, fechaFin);
			logger.info("registrosCierre " +registrosCierre.size());
			logger.info("fechaInicio " +fechaInicio);
			logger.info("fechaFin " +fechaFin);
			ResumenCierreRecaudoDTO resumen = new ResumenCierreRecaudoDTO();
			List<RegistroAporteDTO> registros = new ArrayList<>();
			RegistroAporteDTO aporte = new RegistroAporteDTO();
			RegistroAporteDTO planillasN = new RegistroAporteDTO();
			RegistroAporteDTO devolucion = new RegistroAporteDTO();
			RegistroAporteDTO correcciones = new RegistroAporteDTO();
			RegistroAporteDTO legalizados = new RegistroAporteDTO();
			RegistroAporteDTO otrosIngresos = new RegistroAporteDTO();
			RegistroAporteDTO extemporaneos = new RegistroAporteDTO();

			resumen.setPeriodo(MarcaPeriodoEnum.RESUMEN);
			for (ResumenCierreRecaudoDTO resumenCierreRecaudoDTO : registrosCierre) {
				logger.info("resumenCierreRecaudoDTO  "+resumenCierreRecaudoDTO);
				for (RegistroAporteDTO registroAporteDTO : resumenCierreRecaudoDTO.getRegistros()) {
					logger.info("registroAporteDTO  "+registroAporteDTO);
					logger.info("registroAporteDTO.getTipoRegistro()  "+registroAporteDTO.getTipoRegistro());
					if(registroAporteDTO.getTipoRegistro() != null) {
						switch (registroAporteDTO.getTipoRegistro()) {
							case APORTES:
								logger.debug("Construir Resumen APORTES - Proceso");
								aporte = construirResumen(registroAporteDTO, aporte);
								break;
							case PLANILLAS_N:
								logger.debug("Construir Resumen  PLANILLAS_N - Proceso");
								planillasN = construirResumen(registroAporteDTO, planillasN);
								break;
							case DEVOLUCIONES:
								logger.debug("Construir Resumen DEVOLUCIONES- Proceso");
								devolucion = construirResumen(registroAporteDTO, devolucion);
								break;
							case CORRECCIONES:
								logger.debug("Construir Resumen  CORRECCIONES - Proceso");
								correcciones = construirResumen(registroAporteDTO, correcciones);
								break;
							case REGISTRADOS:
								logger.debug("Construir Resumen REGISTRADOS - Proceso");
								legalizados = construirResumen(registroAporteDTO, legalizados);
								break;
							case OTROS_INGRESOS:
								logger.debug("Construir Resumen  OTROS_INGRESOS - Proceso ");
								otrosIngresos = construirResumen(registroAporteDTO, otrosIngresos);
								break;
							case APORTES_EXTEMPORANEOS:
								logger.debug("Construir Resumen  APORTES_EXTEMPORANEOS - Proceso ");
								extemporaneos = construirResumen(registroAporteDTO, extemporaneos);
								break;
							default:
								break;
						}
					}
				}
			}

			
			aporte.setTipoRegistro(TipoRegistroEnum.APORTES);
			registros.add(aporte);
			logger.debug("setTipoRegistro  CORRECCIONES - Proceso ");
			planillasN.setTipoRegistro(TipoRegistroEnum.PLANILLAS_N);
			registros.add(planillasN);
			extemporaneos.setTipoRegistro(TipoRegistroEnum.APORTES_EXTEMPORANEOS);
			registros.add(extemporaneos);
			logger.debug("setTipoRegistro  DEVOLUCIONES - Proceso ");
			devolucion.setTipoRegistro(TipoRegistroEnum.DEVOLUCIONES);
			registros.add(devolucion);
			logger.debug("setTipoRegistro  CORRECCIONES - Proceso ");
			correcciones.setTipoRegistro(TipoRegistroEnum.CORRECCIONES);
			registros.add(correcciones);
			logger.debug("setTipoRegistro  REGISTRADOS - Proceso ");
			legalizados.setTipoRegistro(TipoRegistroEnum.REGISTRADOS);
			registros.add(legalizados);
			logger.debug("setTipoRegistro  OTROS_INGRESOS - Proceso ");
			otrosIngresos.setTipoRegistro(TipoRegistroEnum.OTROS_INGRESOS);
            registros.add(otrosIngresos);
			logger.debug("setTipoRegistro  OTROS_INGRESOS - Proceso ");


			logger.debug("resumen.setRegistros ");
			resumen.setRegistros(registros);


			logger.debug("Finaliza método consultarResumenCierreRecaudo(Long fechaInicio, Long fechaFin)");
			logger.debug("Finaliza método consultarResumenCierreRecaudo(Long fechaInicio, Long fechaFin)");

			registrosCierre.add(resumen);


			logger.debug("registrosCierre.add");
			logger.info("FINALIZO de método consultarResumenCierreRecaudo(Long fechaInicio, Long fechaFin)");
			return registrosCierre;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en consultarResumenCierreRecaudo", e);
			logger.debug("Ocurrió un error inesperado en consultarResumenCierreRecaudo", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Método encargado de invocar el servicio Iniciar proceso.
	 * 
	 * @param procesoEnum
	 *            proceso que se desea iniciar.
	 * @param parametrosProceso
	 *            parametros del proceso.
	 * @return id de la instancia del proceso.
	 */
	private Long iniciarProceso(ProcesoEnum procesoEnum, Map<String, Object> parametrosProceso) {
		logger.debug("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
		logger.debug("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
		IniciarProceso iniciarProcesoNovedadService = new IniciarProceso(procesoEnum, parametrosProceso);
		iniciarProcesoNovedadService.execute();
		//Long idInstanciaProcesoNovedad = ;
		logger.debug("fin de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
		logger.debug("fin de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
		return iniciarProcesoNovedadService.getResult();

	}


	/**
	 * Método que termina una tarea del BPM
	 * 
	 * @param idTarea,
	 *            es el identificador de la tarea
	 * @param params,
	 *            son los parámetros de la tarea
	 */
	private void terminarTarea(Long idTarea, Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<>();
		}
		TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
		terminarTarea.execute();
	}
	
	/**
     * Suspende una tarea del BPM
     * 
     * @param idTarea,
     *            es el identificador de la tarea
     * @param params,
     *            son los parámetros de la tarea
     */
    private Long suspenderTarea(Long idInstanciaProceso) {
        logger.debug("Inicio de método suspenderTarea(Long)");
        
        Long idTarea = null; 
        try {
            ObtenerTareaActiva tareaActivaSrv = new ObtenerTareaActiva(idInstanciaProceso);
            tareaActivaSrv.execute();
			GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
			accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            Map<String, Object> respuesta =  tareaActivaSrv.getResult();
            
            if (respuesta.containsKey("idTarea") && respuesta.get("idTarea") != null) {
                idTarea = Long.valueOf(respuesta.get("idTarea").toString());
                SuspenderTarea suspenderTarea = new SuspenderTarea(idTarea, new HashMap<String, Object>());
				suspenderTarea.setToken(token.getToken());
                suspenderTarea.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Fin de método suspenderTarea(Long)");
            return null;
        }
        
        logger.debug("Fin de método suspenderTarea(Long)");
        return idTarea;
    }
    
    /**
     * Retoma una tarea en BPM previamente suspendida
     * @param idTarea
     */
    private void retomarTarea(Long idTarea) {
        logger.debug("Inicio de retomarTarea(Long)");
        if (idTarea != null) {

			logger.debug("if retomarTare ");
            RetomarTarea retomarTarea = new RetomarTarea(idTarea, new HashMap<>());
            retomarTarea.execute();
        }
        logger.debug("Fin de retomarTarea(Long)");
    }

	/**
	 * Consulta la solicitud de cierre de recaudo por número de radicación.
	 * 
	 * @param numeroRadicacion
	 *            número de radicación.
	 * @return solicitud consultada.
	 */

	 //Fase 3
	private SolicitudCierreRecaudoModeloDTO consultarSolicitudCierreRecaudo(String numeroRadicacion) {
		logger.info("Inicio de método consultarSolicitudCierreRecaudo");
		logger.debug("Inicio de método consultarSolicitudCierreRecaudo");
		ConsultarSolicitudCierreRecaudo consultarSolicitudCierreService = new ConsultarSolicitudCierreRecaudo(
				numeroRadicacion);
		logger.info("Fin de método consultarSolicitudCierreRecaudo");
		logger.debug("Fin de método consultarSolicitudCierreRecaudo");
		consultarSolicitudCierreService.execute();
		return consultarSolicitudCierreService.getResult();
	}

	/**
	 * Método que hace la peticion REST al servicio de generar nuemro de
	 * radicado
	 * 
	 * @param idSolicitud
	 *            <code>Long</code> El identificador de la solicitud
	 * @param sedeCajaCompensacion
	 *            <code>String</code> El usuario del sistema
	 */
	private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
		logger.debug("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
		RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
		radicarSolicitudService.execute();
		logger.debug("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
		return radicarSolicitudService.getResult();
	}

	/**
	 * Crea o actualiza la solicitud del cierre del recaudo.
	 * 
	 * @param solicitudCierreDTO
	 *            solicitud de cierre del recaudo.
	 * @return id de la solicitud creada.
	 */
	private Long guardarSolicitudCierreRecaudo(SolicitudCierreRecaudoModeloDTO solicitudCierreDTO) {
		logger.debug("Inicio de método guardarSolicitudCierreRecaudo");
		logger.debug("Inicio de método guardarSolicitudCierreRecaudo");
		GuardarSolicitudCierreRecaudo guardarSolicitudCierreService = new GuardarSolicitudCierreRecaudo(
				solicitudCierreDTO);
		guardarSolicitudCierreService.execute();
		logger.debug("Inicio de método guardarSolicitudCierreRecaudo");
		logger.debug("Inicio de método guardarSolicitudCierreRecaudo");
		return guardarSolicitudCierreService.getResult();
	}

	/**
	 * Método para enviar una notificación mediante un comunicado.
	 * 
	 * @param notificacion
	 */
	private void enviarNotificacionComunicado(NotificacionParametrizadaDTO notificacion) {
		EnviarNotificacionComunicado enviarNotificacion = new EnviarNotificacionComunicado(notificacion);
		enviarNotificacion.execute();
	}

	/**
	 * <b>Descripción</b>Método encargado de recuperar los datos del usuario
	 * <br/>
	 * 
	 * @param nombreUsuario,
	 *            nombre del usuario al que se le recuperarán datos
	 * @return String, dato del usuario a retornar
	 */
	private UsuarioCCF obtenerDatoUsuario(String nombreUsuario) {
		logger.debug("Inicia obtenerDatoUsuario(String, String)");
		try {
			UsuarioCCF usuarioDTO = new UsuarioCCF();
			ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacion = new ObtenerDatosUsuarioCajaCompensacion(
					nombreUsuario, null, null, false);
			obtenerDatosUsuariosCajaCompensacion.execute();
			usuarioDTO = (UsuarioCCF) obtenerDatosUsuariosCajaCompensacion.getResult();
			logger.debug("Finaliza obtenerDatoUsuario(String, String)");
			return usuarioDTO;
		} catch (TechnicalException te) {
			logger.debug("Finaliza obtenerDatoUsuario(String, String): error inesperado");
			return null;
		}
	}

	/**
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	private List<ResumenCierreRecaudoDTO> consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin) {


		logger.debug("Inicia consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin)");
		logger.info("Inicia consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin)");

		
		try {
			ConsultarRegistrosCierreRecaudo cierreRecaudoService = new ConsultarRegistrosCierreRecaudo(fechaFin,
					fechaInicio);
			cierreRecaudoService.execute();
			logger.info("Finaliza consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin)" + cierreRecaudoService.getResult().size());
			return cierreRecaudoService.getResult();
		} catch (TechnicalException te) {
			logger.debug("Finaliza consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin): error inesperado");
			logger.error("Finaliza consultarRegistrosCierreRecaudo(Long fechaInicio, Long fechaFin): error inesperado");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, te);
		}
	}

	/**
	 * Metodo que forma los registros de aporte para construir el Resumen de
	 * aporte
	 * 
	 * @param registroAporteDTO
	 */
	private RegistroAporteDTO construirResumen(RegistroAporteDTO registroAporteConsulado,
			RegistroAporteDTO aporteResumen) {
		logger.debug("Inicia construirResumen(RegistroAporteDTO registroAporteDTO) ");
		logger.debug("Inicia construirResumen(RegistroAporteDTO registroAporteDTO) ");
		try {
			BigDecimal montoRegistradoDependiente = aporteResumen.getMontoRegistradoDependiente();
			BigDecimal interesRegistradoDependiente = aporteResumen.getInteresRegistradoDependiente();
			BigDecimal totalRegistradoDependiente = aporteResumen.getTotalRegistradoDependiente();
			BigDecimal montoRelacionadoDependiente = aporteResumen.getMontoRelacionadoDependiente();
			BigDecimal interesRelacionadoDependiente = aporteResumen.getInteresRelacionadoDependiente();
			BigDecimal totalRelacionadoDependiente = aporteResumen.getTotalRelacionadoDependiente();
			BigDecimal montoRegistradoIndependiente = aporteResumen.getMontoRegistradoIndependiente();
			BigDecimal interesRegistradoIndependiente = aporteResumen.getInteresRegistradoIndependiente();
			BigDecimal totalRegistradoIndependiente = aporteResumen.getTotalRegistradoIndependiente();
			BigDecimal montoRelacionadoIndependiente = aporteResumen.getMontoRelacionadoIndependiente();
			BigDecimal interesRelacionadoIndependiente = aporteResumen.getInteresRelacionadoIndependiente();
			BigDecimal totalRelacionadoIndependiente = aporteResumen.getTotalRelacionadoIndependiente();
			BigDecimal montoRegistradoPensionado = aporteResumen.getMontoRegistradoPensionado();
			BigDecimal interesRegistradoPensionado = aporteResumen.getInteresRegistradoPensionado();
			BigDecimal totalRegistradoPensionado = aporteResumen.getTotalRegistradoPensionado();
			BigDecimal montoRelacionadoPensionado = aporteResumen.getMontoRelacionadoPensionado();
			BigDecimal interesRelacionadoPensionado = aporteResumen.getInteresRelacionadoPensionado();
			BigDecimal totalRelacionadoPensionado = aporteResumen.getTotalRelacionadoPensionado();
			BigDecimal montoRegistradoIndependiente_02 = aporteResumen.getMontoRegistradoIndependiente_02();
			BigDecimal interesRegistradoIndependiente_02 = aporteResumen.getInteresRegistradoIndependiente_02();
			///////////////////////////////////////////////////////////////////////////////////
			BigDecimal montoRegistradoPensionado_02 = aporteResumen.getMontoRegistradoPensionado_02();
			BigDecimal interesRegistradoPensionado_02 = aporteResumen.getInteresRegistradoPensionado_02();
			BigDecimal totalRegistradoPensionado_02 = aporteResumen.getTotalRegistradoPensionado_02();
			BigDecimal montoRelacionadoPensionado_02 = aporteResumen.getMontoRelacionadoPensionado_02();
			BigDecimal interesRelacionadoPensionado_02 = aporteResumen.getInteresRelacionadoPensionado_02();
			BigDecimal totalRelacionadoPensionado_02 = aporteResumen.getTotalRelacionadoPensionado_02();
			BigDecimal montoRegistradoPensionado_06 = aporteResumen.getMontoRegistradoPensionado_06();
			BigDecimal interesRegistradoPensionado_06 = aporteResumen.getInteresRegistradoPensionado_06();
			BigDecimal interesRelacionadoPensionado_06 = aporteResumen.getInteresRelacionadoPensionado_06();
			BigDecimal totalRelacionadoPensionado_06 = aporteResumen.getTotalRelacionadoPensionado_06();
			BigDecimal totalRegistradoPensionado_06 = aporteResumen.getTotalRegistradoPensionado_06();
			BigDecimal montoRelacionadoPensionado_06 = aporteResumen.getMontoRelacionadoPensionado_06();

			

			BigDecimal totalRegistradoIndependiente_02 = aporteResumen.getTotalRegistradoIndependiente_02();
			BigDecimal montoRelacionadoIndependiente_02 = aporteResumen.getMontoRelacionadoIndependiente_02();
			BigDecimal interesRelacionadoIndependiente_02 = aporteResumen.getInteresRelacionadoIndependiente_02();
			BigDecimal totalRelacionadoIndependiente_02 = aporteResumen.getTotalRelacionadoIndependiente_02();
			BigDecimal montoRegistradoIndependiente_06 = aporteResumen.getMontoRegistradoIndependiente_06();
			BigDecimal interesRegistradoIndependiente_06 = aporteResumen.getInteresRegistradoIndependiente_06();
			BigDecimal totalRegistradoIndependiente_06 = aporteResumen.getTotalRegistradoIndependiente_06();
			BigDecimal montoRelacionadoIndependiente_06 = aporteResumen.getMontoRelacionadoIndependiente_06();
			BigDecimal interesRelacionadoIndependiente_06 = aporteResumen.getInteresRelacionadoIndependiente_06();
			BigDecimal totalRelacionadoIndependiente_06 = aporteResumen.getTotalRelacionadoIndependiente_06();

			aporteResumen.setMontoRegistradoDependiente(
					montoRegistradoDependiente.add(registroAporteConsulado.getMontoRegistradoDependiente()));
			aporteResumen.setInteresRegistradoDependiente(
					interesRegistradoDependiente.add(registroAporteConsulado.getInteresRegistradoDependiente()));
			aporteResumen.setTotalRegistradoDependiente(
					totalRegistradoDependiente.add(registroAporteConsulado.getTotalRegistradoDependiente()));
			aporteResumen.setMontoRelacionadoDependiente(
					montoRelacionadoDependiente.add(registroAporteConsulado.getMontoRelacionadoDependiente()));
			aporteResumen.setInteresRelacionadoDependiente(
					interesRelacionadoDependiente.add(registroAporteConsulado.getInteresRelacionadoDependiente()));
			aporteResumen.setTotalRelacionadoDependiente(
					totalRelacionadoDependiente.add(registroAporteConsulado.getTotalRelacionadoDependiente()));
			aporteResumen.setMontoRegistradoIndependiente(
					montoRegistradoIndependiente.add(registroAporteConsulado.getMontoRegistradoIndependiente()));
			aporteResumen.setInteresRegistradoIndependiente(
					interesRegistradoIndependiente.add(registroAporteConsulado.getInteresRegistradoIndependiente()));
			aporteResumen.setTotalRegistradoIndependiente(
					totalRegistradoIndependiente.add(registroAporteConsulado.getTotalRegistradoIndependiente()));
			aporteResumen.setMontoRelacionadoIndependiente(
					montoRelacionadoIndependiente.add(registroAporteConsulado.getMontoRelacionadoIndependiente()));
			aporteResumen.setInteresRelacionadoIndependiente(
					interesRelacionadoIndependiente.add(registroAporteConsulado.getInteresRelacionadoIndependiente()));
			aporteResumen.setTotalRelacionadoIndependiente(
					totalRelacionadoIndependiente.add(registroAporteConsulado.getTotalRelacionadoIndependiente()));
			aporteResumen.setMontoRegistradoPensionado(
					montoRegistradoPensionado.add(registroAporteConsulado.getMontoRegistradoPensionado()));
			aporteResumen.setInteresRegistradoPensionado(
					interesRegistradoPensionado.add(registroAporteConsulado.getInteresRegistradoPensionado()));
			aporteResumen.setTotalRegistradoPensionado(
					totalRegistradoPensionado.add(registroAporteConsulado.getTotalRegistradoPensionado()));
			aporteResumen.setMontoRelacionadoPensionado(
					montoRelacionadoPensionado.add(registroAporteConsulado.getMontoRelacionadoPensionado()));
			aporteResumen.setInteresRelacionadoPensionado(
					interesRelacionadoPensionado.add(registroAporteConsulado.getInteresRelacionadoPensionado()));
			aporteResumen.setTotalRelacionadoPensionado(
					totalRelacionadoPensionado.add(registroAporteConsulado.getTotalRelacionadoPensionado()));
			aporteResumen.setMontoRegistradoIndependiente_02(
					montoRegistradoIndependiente_02.add(registroAporteConsulado.getMontoRegistradoIndependiente_02()));
			aporteResumen.setInteresRegistradoIndependiente_02(
					interesRegistradoIndependiente_02.add(registroAporteConsulado.getInteresRegistradoIndependiente_02()));
			aporteResumen.setTotalRegistradoIndependiente_02(
					totalRegistradoIndependiente_02.add(registroAporteConsulado.getTotalRegistradoIndependiente_02()));
			aporteResumen.setMontoRelacionadoIndependiente_02(
					montoRelacionadoIndependiente_02.add(registroAporteConsulado.getMontoRelacionadoIndependiente_02()));
			aporteResumen.setInteresRelacionadoIndependiente_02(
					interesRelacionadoIndependiente_02.add(registroAporteConsulado.getInteresRelacionadoIndependiente_02()));
			aporteResumen.setTotalRelacionadoIndependiente_02(
					totalRelacionadoIndependiente_02.add(registroAporteConsulado.getTotalRelacionadoIndependiente_02()));
			aporteResumen.setMontoRegistradoIndependiente_06(
					montoRegistradoIndependiente_06.add(registroAporteConsulado.getMontoRegistradoIndependiente_06()));
			aporteResumen.setInteresRegistradoIndependiente_06(
					interesRegistradoIndependiente_06.add(registroAporteConsulado.getInteresRegistradoIndependiente_06()));
			aporteResumen.setTotalRegistradoIndependiente_06(
					totalRegistradoIndependiente_06.add(registroAporteConsulado.getTotalRegistradoIndependiente_06()));
			aporteResumen.setMontoRelacionadoIndependiente_06(
					montoRelacionadoIndependiente_06.add(registroAporteConsulado.getMontoRelacionadoIndependiente_06()));
			aporteResumen.setInteresRelacionadoIndependiente_06(
					interesRelacionadoIndependiente_06.add(registroAporteConsulado.getInteresRelacionadoIndependiente_06()));
			aporteResumen.setTotalRelacionadoIndependiente_06(
					totalRelacionadoIndependiente_06.add(registroAporteConsulado.getTotalRelacionadoIndependiente_06()));
			///////////////////////////////////////////////////////////////////////////////////////////////////////////

				aporteResumen.setMontoRegistradoPensionado_02(
					montoRegistradoPensionado_02.add(registroAporteConsulado.getMontoRegistradoPensionado_02()));
			aporteResumen.setInteresRegistradoPensionado_02(
					interesRegistradoPensionado_02.add(registroAporteConsulado.getInteresRegistradoPensionado_02()));
			aporteResumen.setTotalRegistradoPensionado_02(
					totalRegistradoPensionado_02.add(registroAporteConsulado.getTotalRegistradoPensionado_02()));
			aporteResumen.setMontoRelacionadoPensionado_02(
					montoRelacionadoPensionado_02.add(registroAporteConsulado.getMontoRelacionadoPensionado_02()));
			aporteResumen.setInteresRelacionadoPensionado_02(
					interesRelacionadoPensionado_02.add(registroAporteConsulado.getInteresRelacionadoPensionado_02()));
			aporteResumen.setTotalRelacionadoPensionado_02(
					totalRelacionadoPensionado_02.add(registroAporteConsulado.getTotalRelacionadoPensionado_02()));
			aporteResumen.setMontoRegistradoPensionado_06(
					montoRegistradoPensionado_06.add(registroAporteConsulado.getMontoRegistradoPensionado_06()));
			aporteResumen.setInteresRegistradoPensionado_06(
					interesRegistradoPensionado_06.add(registroAporteConsulado.getInteresRegistradoPensionado_06()));
			aporteResumen.setTotalRegistradoPensionado_06(
					totalRegistradoPensionado_06.add(registroAporteConsulado.getTotalRegistradoPensionado_06()));
			aporteResumen.setMontoRelacionadoPensionado_06(
					montoRelacionadoPensionado_06.add(registroAporteConsulado.getMontoRelacionadoPensionado_06()));
			aporteResumen.setInteresRelacionadoPensionado_06(
					interesRelacionadoPensionado_06.add(registroAporteConsulado.getInteresRelacionadoPensionado_06()));
			aporteResumen.setTotalRelacionadoPensionado_06(
					totalRelacionadoPensionado_06.add(registroAporteConsulado.getTotalRelacionadoPensionado_06()));

			logger.debug("Finaliza construirResumen(RegistroAporteDTO registroAporteDTO) ");
			logger.debug("Finaliza construirResumen(RegistroAporteDTO registroAporteDTO) ");
			return aporteResumen;
		} catch (TechnicalException te) {
			logger.debug("Finaliza construirResumen(RegistroAporteDTO registroAporteDTO): error inesperado");
			logger.debug("Finaliza construirResumen(RegistroAporteDTO registroAporteDTO): error inesperado");
			return null;
		}
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
		return idECM.toString();
	}

	// /**
	// * Almacena en el ecm excel que se genera en el cierre de recaudo
	// * @param infoFile
	// * @return String con el identificador para el ecm
	// */
	// private ArchivoCierreDTO
	// generarReporteCierreRecaudo(RegistrosArchivoAporteDTO registros) {
	// logger.debug("Inicia
	// generarReporteCierreRecaudo(RegistrosArchivoAporteDTO registros)");
	// GenerarReporteCierreRecaudo reporte = new
	// GenerarReporteCierreRecaudo(registros);
	// reporte.execute();
	// logger.debug("Inicia
	// generarReporteCierreRecaudo(RegistrosArchivoAporteDTO registros)");
	// return reporte.getResult();
	// }

	/**
	 * Consulta los detalles por registro para el cierre de recaudo de aportes
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	private List<DetalleRegistroDTO> consultarDetallePorRegistro(Long fechaInicio, Long fechaFin) {
		logger.debug("Inicia generarReporteCierreRecaudo(RegistrosArchivoAporteDTO registros)");
		ConsultarResultadoRegistroRecaudoAportes resultado = new ConsultarResultadoRegistroRecaudoAportes(fechaFin,
				fechaInicio);
		resultado.execute();
		logger.debug("Inicia generarReporteCierreRecaudo(RegistrosArchivoAporteDTO registros)");
		return resultado.getResult();
	}

	/**
	 * Actualiza los aportes generales con la marca de conciliado
	 * 
	 * @param idsAportesGenerales
	 */
	private void conciliarAporteGeneral(String idsAportesGenerales) {
		logger.debug("Inicia conciliarAporteGeneral(String idsAportesGenerales)");
		ConciliarAporteGeneral conciliacion = new ConciliarAporteGeneral(idsAportesGenerales);
		conciliacion.execute();
		logger.debug("Finaliza conciliarAporteGeneral(String idsAportesGenerales)");
	}

}
