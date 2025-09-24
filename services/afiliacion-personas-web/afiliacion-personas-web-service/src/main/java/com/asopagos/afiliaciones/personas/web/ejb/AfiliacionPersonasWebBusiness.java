package com.asopagos.afiliaciones.personas.web.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Date;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.afiliacion.personas.web.constants.NamedQueriesConstants;
import com.asopagos.afiliaciones.personas.web.service.AfiliacionPersonasWebService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.entidades.ccf.afiliaciones.CargueMultiple;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.dto.FiltroConsultaSolicitudesEnProcesoDTO;
import java.util.stream.Collectors;
import com.asopagos.validaciones.clients.VerificarSolicitudesEnCurso;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;


/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la afiliación de personas <b>Historia de Usuario:</b> HU-121-104
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@Stateless
public class AfiliacionPersonasWebBusiness implements AfiliacionPersonasWebService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "afiliacionpersonasweb_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private ILogger logger = LogManager.getLogger(AfiliacionPersonasWebBusiness.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.service.
	 * AfiliacionPersonasWebService#
	 * buscarSolicitudesAfiliacionPersonaPorEmpleador(java.lang.Long,
	 * com.asopagos.enumeraciones.core.CanalRecepcionEnum,
	 * com.asopagos.enumeraciones.afiliaciones.
	 * EstadoSolicitudAfiliacionPersonaEnum)
	 */
	@Override
	public List<SolicitudAfiliacionPersonaDTO> buscarSolicitudesAfiliacionPersonaPorEmpleadorConsultar(Long idEmpleador,
	CanalRecepcionEnum canalRecepcionEnum, EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud, TipoIdentificacionEnum tipoIdentificacion,
	String numeroIdentificacion, String numeroRadicado) {

		List<SolicitudAfiliacionPersonaDTO> listaSolicitudes = new ArrayList<SolicitudAfiliacionPersonaDTO>();
		Query qSolicitud = null;
		qSolicitud = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_AFILIACION_PERSONA_POR_EMPLEADOR_CONSULTAR);
		qSolicitud.setParameter("canalRecepcion", canalRecepcionEnum);
		qSolicitud.setParameter("idEmpleador", idEmpleador);
		qSolicitud.setParameter("tipoIdentificacion", tipoIdentificacion);
		qSolicitud.setParameter("numeroIdentificacion", numeroIdentificacion);
		qSolicitud.setParameter("numeroRadicacion", numeroRadicado);

		List<Object[]>res = qSolicitud.getResultList();
		for (Object[] item: res) {
			listaSolicitudes.add(
				new SolicitudAfiliacionPersonaDTO(
					(SolicitudAfiliacionPersona) item[0],
					(PersonaDetalle) item[1],
					(RolAfiliado) item[2],
					(Solicitud) item[3],
					(Afiliado) item[4],
					(Empleador) item[5],
					(Persona) item[6],
					(Ubicacion) item[7],
					(Municipio) item[8]
				)
			);
		}
		return listaSolicitudes;	
		
	}
	
	@Override
	public List<SolicitudAfiliacionPersonaDTO> buscarSolicitudesAfiliacionPersonaPorEmpleador(FiltroConsultaSolicitudesEnProcesoDTO filtro) {
		logger.debug(
				"Inicia buscarSolicitudesAfiliacionPersonaPorEmpleador(FiltroConsultaSolicitudesEnProcesoDTO)");
		List<SolicitudAfiliacionPersonaDTO> solicitudesAfiliacionPersonaDTO = new ArrayList<SolicitudAfiliacionPersonaDTO>();
		/* Validacion que los campos no sean vacios */
		List<SolicitudAfiliacionPersonaDTO> listaSolicitudes = new ArrayList<SolicitudAfiliacionPersonaDTO>();
		logger.info("Filtro recibido: " + filtro.toString());
			// Se crea la consulta para buscar las solicitudes de afiliacion persona por empleador
		Query qSolicitud = null;

			qSolicitud = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_AFILIACION_PERSONA_POR_EMPLEADOR_WEB);
				qSolicitud.setParameter("tipoIdentificacionEmpleador",
					filtro.getTipoIdentificacionEmpleador() != null ? filtro.getTipoIdentificacionEmpleador() : null);

				qSolicitud.setParameter("numeroIdentificacionEmpleador",
					filtro.getNumeroIdentificacionEmpleador() != null ? filtro.getNumeroIdentificacionEmpleador() : null);

				qSolicitud.setParameter("tipoIdentificacionSolicitante",
					filtro.getTipoIdentificacionSolicitante() != null ? filtro.getTipoIdentificacionSolicitante() : null);

				qSolicitud.setParameter("numeroIdentificacionSolicitante",
					filtro.getNumeroIdentificacionSolicitante() != null ? filtro.getNumeroIdentificacionSolicitante() : null);

				qSolicitud.setParameter("numeroSolicitud",
					filtro.getNumeroSolicitud() != null ? filtro.getNumeroSolicitud() : null);

				qSolicitud.setParameter("fechaInicioDate",
					filtro.getFechaInicio() != null ? new Date(filtro.getFechaInicio()) : null);

				qSolicitud.setParameter("fechaFinDate",
					filtro.getFechaFin() != null ? limpiarHora(new Date(filtro.getFechaFin())) : null);

				List<Object[]>res = qSolicitud.getResultList();
				for (Object[] item: res) {
					listaSolicitudes.add(
						new SolicitudAfiliacionPersonaDTO(
							(SolicitudAfiliacionPersona) item[0],
							(PersonaDetalle) item[1],
							(RolAfiliado) item[2],
							(Solicitud) item[3],
							(Afiliado) item[4],
							(Empleador) item[5],
							(Persona) item[6]
						)
					);
				}
			

			/* Se verifica si se encuentran solicitudes */
			// if (!listaSolicitudes.isEmpty()) {
			// 	for (SolicitudAfiliacionPersona solicitudAfiliacionPersona : listaSolicitudes) {
			// 		SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = new SolicitudAfiliacionPersonaDTO();
			// 		solicitudAfiliacionPersonaDTO
			// 				.setIdSolicitudGlobal(solicitudAfiliacionPersona.getSolicitudGlobal().getIdSolicitud());
			// 		solicitudAfiliacionPersonaDTO.setEstadoSolicitud(solicitudAfiliacionPersona.getEstadoSolicitud());
			// 		solicitudAfiliacionPersonaDTO.setAnulada(solicitudAfiliacionPersona.getSolicitudGlobal().getAnulada());
			// 		AfiliadoInDTO afiliadoInDTO = new AfiliadoInDTO();
			// 		PersonaDetalle personaDetalle=null;
			// 		// try{
			// 		// 	personaDetalle = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION, PersonaDetalle.class)
			// 		// 			.setParameter("tipoIdentificacion", solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getPersona().getTipoIdentificacion())
			// 		// 			.setParameter("numeroIdentificacion", solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getPersona().getNumeroIdentificacion()).getSingleResult();
			// 		// }catch(NoResultException nre){		
			// 			// personaDetalle=null;
			// 		// }
			// 		// Se asigna la persona dto
			// 		PersonaDTO personaDTO = new PersonaDTO().convertPersonaToDTO(
			// 				solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getPersona(), personaDetalle);
			// 		afiliadoInDTO.setPersona(personaDTO);
			// 		afiliadoInDTO
			// 				.setCanalRecepcion(solicitudAfiliacionPersona.getSolicitudGlobal().getCanalRecepcion());
			// 		afiliadoInDTO
			// 				.setIdAfiliado(solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getIdAfiliado());
			// 		afiliadoInDTO.setIdEmpleador(
			// 				solicitudAfiliacionPersona.getRolAfiliado().getEmpleador().getIdEmpleador());
			// 		afiliadoInDTO.setIdRolAfiliado(solicitudAfiliacionPersona.getRolAfiliado().getIdRolAfiliado());
			// 		afiliadoInDTO.setTipoAfiliado(solicitudAfiliacionPersona.getRolAfiliado().getTipoAfiliado());
			// 		afiliadoInDTO.setValorSalarioMesada(
			// 				solicitudAfiliacionPersona.getRolAfiliado().getValorSalarioMesadaIngresos());
			// 		if (solicitudAfiliacionPersona.getRolAfiliado().getSucursalEmpleador() != null) {
			// 			afiliadoInDTO.setSucursalEmpleadorId(solicitudAfiliacionPersona.getRolAfiliado()
			// 					.getSucursalEmpleador().getIdSucursalEmpresa());
			// 		}
			// 		solicitudAfiliacionPersonaDTO.setAfiliadoInDTO(afiliadoInDTO);
			// 		solicitudAfiliacionPersonaDTO
			// 				.setTipoTransaccion(solicitudAfiliacionPersona.getSolicitudGlobal().getTipoTransaccion());
			// 		solicitudAfiliacionPersonaDTO
			// 				.setIdTipoSolicitante(solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona());
			// 		solicitudAfiliacionPersonaDTO
			// 				.setNumeroRadicacion(solicitudAfiliacionPersona.getSolicitudGlobal().getNumeroRadicacion());
			// 		solicitudAfiliacionPersonaDTO
			// 				.setClasificacion(solicitudAfiliacionPersona.getSolicitudGlobal().getClasificacion());
			// 		solicitudAfiliacionPersonaDTO.setIdInstanciaProceso(
			// 				solicitudAfiliacionPersona.getSolicitudGlobal().getIdInstanciaProceso());
			// 		solicitudAfiliacionPersonaDTO
			// 				.setMetodoEnvio(solicitudAfiliacionPersona.getSolicitudGlobal().getMetodoEnvio());
			// 		solicitudAfiliacionPersonaDTO.setUsuarioRadicacion(
			// 				solicitudAfiliacionPersona.getSolicitudGlobal().getUsuarioRadicacion());
			// 		solicitudAfiliacionPersonaDTO
			// 				.setFechaRadicacion(solicitudAfiliacionPersona.getSolicitudGlobal().getFechaRadicacion());
			// 		solicitudAfiliacionPersonaDTO
			// 				.setTipoRadicacion(solicitudAfiliacionPersona.getSolicitudGlobal().getTipoRadicacion());
			// 		solicitudAfiliacionPersonaDTO.setIdSolicitudAfiliacionPersona(
			// 				solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona());
			// 		solicitudesAfiliacionPersonaDTO.add(solicitudAfiliacionPersonaDTO);
			// 	}
			// } else {
			// 	return null;
			// }
		
		logger.debug(
				"Finaliza buscarSolicitudesAfiliacionPersonaPorEmpleador(Long,CanalRecepcionEnum,EstadoSolicitudAfiliacionPersonaEnum)");
		return listaSolicitudes;
	}

	private Date limpiarHora(Date date) {
    if (date == null) return null;
    Calendar cal = java.util.Calendar.getInstance();
    cal.setTime(date);
    cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
    cal.set(java.util.Calendar.MINUTE, 59);
    cal.set(java.util.Calendar.SECOND, 59);
    cal.set(java.util.Calendar.MILLISECOND, 999);
    return cal.getTime();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ResultadoValidacionArchivoDTO consultarResultadoValidacionTrabajadoresDependientes(Long idEmpleador,
			EstadoCargaMultipleEnum estado) {
		logger.debug(
				"Finaliza consultarResultadoValidacionTrabajadoresDependientes(Long idEmpleador,EstadoCargaMultiplePersonaEnum estado)");
		Map<Long, String> lstCandidatos = new HashMap<>();
		ResultadoValidacionArchivoDTO resultadoValidacionDTO = new ResultadoValidacionArchivoDTO();
		Query qCargaMultiple = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_CARGA_MULTIPLE_POR_EMPLEADOR_ESTADO);
		qCargaMultiple.setParameter("idEmpleador", idEmpleador);
		if (estado != null) {
			qCargaMultiple.setParameter("estado", estado);
		} else {
			estado = EstadoCargaMultipleEnum.EVALUADO;
			qCargaMultiple.setParameter("estado", estado);
		}
		List<CargueMultiple> lstCargueMultiple = qCargaMultiple.getResultList();
		/* Se verifica si se encuentra el cargue multiple */
		if (!lstCargueMultiple.isEmpty()) {
			CargueMultiple cargueMultiple = lstCargueMultiple.get(0);
			Query qSolicitud = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_CARGA_MULTIPLE_POR_ID_CARGA);
			qSolicitud.setParameter("idCargaMultiple", cargueMultiple.getIdSolicitudAfiliacionMultiple());
			List<Solicitud> lstSolicitudCargue = qSolicitud.getResultList();
			List<Long> lstIdSolicitudes = new ArrayList<Long>();
			// Se recorren las solicitudes y se extraen los id de las
			// solicitudes encontradas
			for (Solicitud solicitud : lstSolicitudCargue) {
				lstIdSolicitudes.add(solicitud.getIdSolicitud());
			}
			logger.info(lstIdSolicitudes.get(0));
			ConsultarDatosTemporales consultarDatosTemporales = new ConsultarDatosTemporales(lstIdSolicitudes.get(0));
			consultarDatosTemporales.execute();
			String jsonPayload = consultarDatosTemporales.getResult();
			logger.info(jsonPayload);
			// Query qDatoTemporal = entityManager
			// 		.createNamedQuery(NamedQueriesConstants.BUSCAR_DATO_TEMPORAL_POR_ID_SOLICITUD);
			// qDatoTemporal.setParameter("idSolicitud", lstIdSolicitudes);
			
			//List<DatoTemporalSolicitud> lstDatoTemporal = qDatoTemporal.getResultList();
			
			if (jsonPayload != null) {
				Gson gson = new GsonBuilder().create();
                TypeToken<Object[]> listType = new TypeToken<Object[]>() {};
                Object[] candidatos = gson.fromJson(jsonPayload, listType.getType());

				VerificarSolicitudesEnCurso servicio;
				ValidacionDTO resultadoValidacion;

				for (int i = 0; i < candidatos.length; i++) {
					logger.info(gson.toJson(candidatos[i]));

					Type type = new TypeToken<Map<String, Object>>() {}.getType();

        			Map<String, Object> map = gson.fromJson(gson.toJson(candidatos[i]), type);

					Map<String, Object> afiliadoInDTO = (Map<String, Object>) map.get("afiliadoInDTO");
					Map<String, Object> persona = (Map<String, Object>) afiliadoInDTO.get("persona");

					String tipoIdentificacion = persona.get("tipoIdentificacion").toString();
					String nuemroIdentificacion = persona.get("numeroIdentificacion").toString();
					String idSolicitudStr = afiliadoInDTO.get("idSolicitudGlobal").toString();
					idSolicitudStr = idSolicitudStr.split("\\.")[0]; // elimina cualquier parte decimal
					Long idSolicitud = Long.valueOf(idSolicitudStr);


					servicio = new VerificarSolicitudesEnCurso(
						null,
						nuemroIdentificacion,
						Long.valueOf(idSolicitud),
						TipoIdentificacionEnum.valueOf(tipoIdentificacion),
						null, null, null
					);
					servicio.execute();
					resultadoValidacion = servicio.getResult();

					if (ResultadoValidacionEnum.NO_APROBADA.equals(resultadoValidacion.getResultado())) {
						map.put("afiliable", false);
					}
					
				
					lstCandidatos.put((long) i,gson.toJson(map));
				}
				
			} else {
				return null;
			}
		} else {
			return null;
		}
		resultadoValidacionDTO.setLstCandidatos(lstCandidatos);
		logger.debug(
				"Finaliza consultarResultadoValidacionTrabajadoresDependientes(Long idEmpleador,EstadoCargaMultiplePersonaEnum estado)");
		return resultadoValidacionDTO;
	}


}