package com.asopagos.afiliaciones.personas.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.afiliacion.personas.constants.NamedQueriesConstants;
import com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.ListadoSolicitudesAfiliacionDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.exception.ValidacionFallidaException;
import com.asopagos.rutine.afiliacionpersonasrutines.actualizarsolicitudafiliacionpersona.ActualizarSolicitudAfiliacionPersonaRutine;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.entidades.transversal.core.ConsolaEstadoCargueMasivo;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.solicitudes.clients.CalcularTiempoDesistirSolicitud;


/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la afiliación de personas <b>Historia de Usuario:</b> HU-121-104
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@Stateless
public class AfiliacionPersonasBusiness implements AfiliacionPersonasService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "afiliacionpersonas_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private ILogger logger = LogManager.getLogger(AfiliacionPersonasBusiness.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#crearSolicitudAfiliacionPersona(com.asopagos.dto.DatosBasicosIdentificacionDTO)
	 */
	public Long crearSolicitudAfiliacionPersona(AfiliadoInDTO inAfiliadoDTO) {
		// Busqueda de la persona el numero de identificación y el tipo de
		// identificación
		 logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP getNumeroIdentificacion:"+inAfiliadoDTO.getPersona().getNumeroIdentificacion());
		  logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP getTipoIdentificacion:"+inAfiliadoDTO.getPersona().getTipoIdentificacion());
		Query qPersona = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_IDENTIFICACION);
		qPersona.setParameter("numIdentificacion", inAfiliadoDTO.getPersona().getNumeroIdentificacion());
		qPersona.setParameter("tipoIdentificacion", inAfiliadoDTO.getPersona().getTipoIdentificacion());
		List<Persona> listaPersona = qPersona.getResultList();
		// Verificacion de que exista la persona
		if (listaPersona.size() > 0) {
			Solicitud solicitud = new Solicitud();
			// Por especificacion se setea el campo estadoDocumento en null
			solicitud.setEstadoDocumentacion(null);
			solicitud.setTipoTransaccion(inAfiliadoDTO.getPersona().getTipoTransaccion());
			solicitud.setCanalRecepcion(inAfiliadoDTO.getCanalRecepcion());
			solicitud.setFechaCreacion(new Date());
			solicitud.setClasificacion(inAfiliadoDTO.getPersona().getClasificacion());
			/* Se agrega validacion para el proceso 122 CargueMultiple */
			if (inAfiliadoDTO.getCodigoCargueMultiple() != null) {
				solicitud.setCargaAfiliacionMultiple(inAfiliadoDTO.getCodigoCargueMultiple());
			}
			entityManager.persist(solicitud);
			// Consultar rol Afiliado por Id
			Query qRolAfiliacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_ID);
			qRolAfiliacion.setParameter("idRolAfiliado", inAfiliadoDTO.getIdRolAfiliado());
			RolAfiliado rolAfiliadoResult = (RolAfiliado) qRolAfiliacion.getSingleResult();

			// Creacion de la solicitudAfiliacionPersona
			SolicitudAfiliacionPersona solAfiLliacion = new SolicitudAfiliacionPersona();
			solAfiLliacion.setSolicitudGlobal(solicitud);
			solAfiLliacion.setRolAfiliado(rolAfiliadoResult);
			/*
			 * Por especificacion se setea el estado de la solicitud en
			 * PRE_RADICADA
			 */
			solAfiLliacion.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA);
			entityManager.persist(solAfiLliacion);
			logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP FIN solicitud.getIdSolicitud(): "+solicitud.getIdSolicitud());
			logger.info(solicitud.getIdInstanciaProceso());
			String idTarea = solicitud.getIdInstanciaProceso() == null ? "null" : solicitud.getIdInstanciaProceso()+"";
			logger.info("**__**Si entro a consumir el SP "+ solicitud.getIdSolicitud());
			CalcularTiempoDesistirSolicitud calcularTiempoDesistirSolicitud = new CalcularTiempoDesistirSolicitud(
			solicitud.getIdSolicitud(), idTarea,
			solAfiLliacion.getEstadoSolicitud().toString(),solicitud.getTipoTransaccion().toString());
			calcularTiempoDesistirSolicitud.execute();	
			
			return solicitud.getIdSolicitud();
		} else {
			// Persona no encontrada
			return null;
		}
	}

	@Override
	public void crearSolicitudAfiliacionPersonaSolo(Long rolAfiliado, Long solicitudGlobal){
		// Creacion de la solicitudAfiliacionPersona
		Query qRolAfiliacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_ID);
		qRolAfiliacion.setParameter("idRolAfiliado", rolAfiliado);
		RolAfiliado rolAfiliadoResult = (RolAfiliado) qRolAfiliacion.getSingleResult();

		Solicitud solicitud = entityManager.find(Solicitud.class, solicitudGlobal);

		SolicitudAfiliacionPersona solAfiLliacion = new SolicitudAfiliacionPersona();
		solAfiLliacion.setSolicitudGlobal(solicitud);
		solAfiLliacion.setRolAfiliado(rolAfiliadoResult);
		/*
			* Por especificacion se setea el estado de la solicitud en
			* PRE_RADICADA
			*/
		solAfiLliacion.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA);
		entityManager.persist(solAfiLliacion);
	}
    

	@Override
	public Long crearSolicitudCargueMultiple(Long idCargue){

		Solicitud solicitudCargueMultiple = new Solicitud();
		solicitudCargueMultiple.setFechaRadicacion(new Date());
		solicitudCargueMultiple.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
		solicitudCargueMultiple.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
		solicitudCargueMultiple.setFechaCreacion(new Date());
		solicitudCargueMultiple.setCargaAfiliacionMultiple(idCargue);
		entityManager.persist(solicitudCargueMultiple);
		return solicitudCargueMultiple.getIdSolicitud();
	}
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#consultarSolicitudAfiliacionPersona(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SolicitudAfiliacionPersonaDTO consultarSolicitudAfiliacionPersona(Long idSolicitudGlobal) {
		logger.info("Inicia consultarSolicitudAfiliacionPersona(Long)");
		Query qSolicitudAfiliacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_SOLICITUD_GLOBAL);
		qSolicitudAfiliacion.setParameter("idSolicitudGlobal", idSolicitudGlobal);
		try {
			SolicitudAfiliacionPersona solicitudAfiResult = (SolicitudAfiliacionPersona) qSolicitudAfiliacion
					.getSingleResult();
			SolicitudAfiliacionPersonaDTO solAfiPersonaDTO = new SolicitudAfiliacionPersonaDTO();
			logger.info("Inicia named query BUSCAR_ID_SOLICITUD"+idSolicitudGlobal);
			Query qSolicitud = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_SOLICITUD);
			qSolicitud.setParameter("idSolicitud", idSolicitudGlobal);
			Solicitud solicitudResult = (Solicitud) qSolicitud.getSingleResult();
			// Asignacion datos SolicitudAfiliacionPersonaDTO

			solAfiPersonaDTO.setIdSolicitudGlobal(solicitudAfiResult.getSolicitudGlobal().getIdSolicitud());
			solAfiPersonaDTO.setComentarioSolicitud(solicitudAfiResult.getSolicitudGlobal().getObservacion());
			solAfiPersonaDTO.setIdSolicitudAfiliacionPersona(solicitudAfiResult.getIdSolicitudAfiliacionPersona());
			EstadoSolicitudAfiliacionPersonaEnum estadoSolicitudEnum = solicitudAfiResult.getEstadoSolicitud();
			solAfiPersonaDTO.setEstadoSolicitud(estadoSolicitudEnum);
			// Consultar rol Afiliado por Id
			logger.info("Inicia named query BUSCAR_ROL_AFILIADO_POR_ID"+solicitudAfiResult.getRolAfiliado().getIdRolAfiliado());
			Query qRolAfiliacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_ID);
			qRolAfiliacion.setParameter("idRolAfiliado", solicitudAfiResult.getRolAfiliado().getIdRolAfiliado());
			RolAfiliado rolAfiliadoResult = (RolAfiliado) qRolAfiliacion.getSingleResult();
			AfiliadoInDTO afiDTO = new AfiliadoInDTO();
			if (rolAfiliadoResult != null) {
				// Asignacion datos afiliacionDTO
				// Consultar Afiliado
					logger.info("Inicia named query BUSCAR_ID_AFILIADO");
				Query qAfiliado = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_AFILIADO);
				qAfiliado.setParameter("idAfiliado", rolAfiliadoResult.getAfiliado().getIdAfiliado());
				Afiliado afiliadoResult = (Afiliado) qAfiliado.getSingleResult();
				PersonaDTO personaDTO = null;
				if (afiliadoResult != null) {
					// Consultar la persona
					logger.info("Inicia named query BUSCAR_ID_PERSONA"+afiliadoResult.getPersona().getIdPersona());
					Query qPersona = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_PERSONA);
					qPersona.setParameter("idPersona", afiliadoResult.getPersona().getIdPersona());
					Persona personaResult = (Persona) qPersona.getSingleResult();

					// Consultar la persona detalle
               logger.info("Inicia named query BUSCAR_ID_PERSONA_DETALLE"+afiliadoResult.getPersona().getIdPersona());
					PersonaDetalle personaDetalleResult = null;
					Query qPersonaDetalle = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_PERSONA_DETALLE);
					qPersonaDetalle.setParameter("idPersona", personaResult.getIdPersona());
					try {
						personaDetalleResult = (PersonaDetalle) qPersonaDetalle.getSingleResult();

					} catch (NoResultException nre) {
						personaDetalleResult = null;
					}

					personaDTO = PersonaDTO.convertPersonaToDTO(personaResult, personaDetalleResult);
					if (afiliadoResult.getIdAfiliado() != null) {
						personaDTO.setIdAfiliado(afiliadoResult.getIdAfiliado());
						afiDTO.setIdAfiliado(afiliadoResult.getIdAfiliado());
					}
					afiDTO.setPersona(personaDTO);
				}
				afiDTO.setTipoAfiliado(rolAfiliadoResult.getTipoAfiliado());
				afiDTO.setIdRolAfiliado(rolAfiliadoResult.getIdRolAfiliado());
				afiDTO.setCanalRecepcion(solicitudResult.getCanalRecepcion());
				/* Se valida si el empleador existe o no */
				if (rolAfiliadoResult.getEmpleador() != null) {
					afiDTO.setIdEmpleador(rolAfiliadoResult.getEmpleador().getIdEmpleador());
				}
				afiDTO.setValorSalarioMesada(rolAfiliadoResult.getValorSalarioMesadaIngresos());
			}
			solAfiPersonaDTO.setAfiliadoInDTO(afiDTO);
			solAfiPersonaDTO.setIdTipoSolicitante(solicitudAfiResult.getIdSolicitudAfiliacionPersona());
			solAfiPersonaDTO.setTipoTransaccion(solicitudResult.getTipoTransaccion());
			if (solicitudResult.getNumeroRadicacion() != null && !solicitudResult.getNumeroRadicacion().equals("")) {
				solAfiPersonaDTO.setNumeroRadicacion(solicitudResult.getNumeroRadicacion());
			}
			if (solicitudResult.getIdInstanciaProceso() != null
					&& !solicitudResult.getIdInstanciaProceso().equals("")) {
				solAfiPersonaDTO.setIdInstanciaProceso(solicitudResult.getIdInstanciaProceso());
			}
			logger.info(" edwin entra aca *******************" + solicitudResult.getClasificacion());
			solAfiPersonaDTO.setClasificacion(solicitudResult.getClasificacion());
			solAfiPersonaDTO.setMetodoEnvio(solicitudResult.getMetodoEnvio());
			solAfiPersonaDTO.setUsuarioRadicacion(solicitudResult.getUsuarioRadicacion());
			solAfiPersonaDTO.setFechaRadicacion(solicitudResult.getFechaRadicacion());
			solAfiPersonaDTO.setTipoRadicacion(solicitudResult.getTipoRadicacion());
			logger.info("Finaliza consultarSolicitudAfiliacionPersona(Long)" );

			return solAfiPersonaDTO;
		} catch (NoResultException e) {
			logger.debug("Finaliza consultarSolicitudAfiliacionPersona");
			return null;
		} catch (Exception e) {
				logger.info("ERROR : "+e );
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#actualizarEstadoSolicitudAfiliacionPersona()
	 */
	@Override
	public void actualizarEstadoSolicitudAfiliacionPersona(Long idSolicitudGlobal,
			EstadoSolicitudAfiliacionPersonaEnum estado) {
		logger.debug("Inicia actualizarEstadoSolicitudAfiliacionPersona(Long)");
		try {
			if (idSolicitudGlobal == null || estado == null) {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
			} else {
				Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_SOLICITUD_GLOBAL);
				q.setParameter("idSolicitudGlobal", idSolicitudGlobal);
				SolicitudAfiliacionPersona solicitudResult = (SolicitudAfiliacionPersona) q.getSingleResult();
				/*
				* se verifica si el nuevo estado es CERRADA para actualizar el
				* resultado del proceso Mantis 0216650
				*/
				if (EstadoSolicitudAfiliacionPersonaEnum.CERRADA.equals(estado)
				&& (EstadoSolicitudAfiliacionPersonaEnum.APROBADA.equals(solicitudResult.getEstadoSolicitud())
				|| EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA
				.equals(solicitudResult.getEstadoSolicitud())
				|| EstadoSolicitudAfiliacionPersonaEnum.CANCELADA
				.equals(solicitudResult.getEstadoSolicitud())
				|| EstadoSolicitudAfiliacionPersonaEnum.DESISTIDA
				.equals(solicitudResult.getEstadoSolicitud()))) {
					solicitudResult.getSolicitudGlobal().setResultadoProceso(
						ResultadoProcesoEnum.valueOf(solicitudResult.getEstadoSolicitud().name()));
						
						logger.info("Si consume el SP 1 ");
						String idTarea = solicitudResult.getSolicitudGlobal().getIdInstanciaProceso() == null 
						? "null" 
						: String.valueOf(solicitudResult.getSolicitudGlobal().getIdInstanciaProceso());	
						CalcularTiempoDesistirSolicitud calcularTiempoDesistirSolicitud = new CalcularTiempoDesistirSolicitud(
						solicitudResult.getSolicitudGlobal().getIdSolicitud(),idTarea,
						estado.toString(), solicitudResult.getSolicitudGlobal().getTipoTransaccion().toString());
					calcularTiempoDesistirSolicitud.execute();	
				}
				// Si el estado es DESISTIDA o REGISTRO_INTENTO_AFILIACION poner resultado del proceso en RECHAZADA
				if (EstadoSolicitudAfiliacionPersonaEnum.DESISTIDA.equals(estado)
						|| EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION.equals(estado)) {
					solicitudResult.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
				}

				// Si se registra un intento de afiliacion persona, inactivar el rolAfiliado
				if(EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION.equals(estado)){
					RolAfiliado rolAfiliado = entityManager.find(RolAfiliado.class, solicitudResult.getRolAfiliado().getIdRolAfiliado());
					entityManager.merge(rolAfiliado);
					rolAfiliado.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
				}
				solicitudResult.setEstadoSolicitud(estado);
				logger.info("Si consume el SP 2");
				String idTarea = solicitudResult.getSolicitudGlobal().getIdInstanciaProceso() == null 
						? "null" 
						: String.valueOf(solicitudResult.getSolicitudGlobal().getIdInstanciaProceso());	
				CalcularTiempoDesistirSolicitud calcularTiempoDesistirSolicitud = new CalcularTiempoDesistirSolicitud(
					solicitudResult.getSolicitudGlobal().getIdSolicitud(),idTarea,
					solicitudResult.getEstadoSolicitud().toString(), solicitudResult.getSolicitudGlobal().getTipoTransaccion().toString());
				calcularTiempoDesistirSolicitud.execute();	
				entityManager.merge(solicitudResult);
				logger.debug(
						"Finaliza actualizarEstadoSolicitudAfiliacionPersona(Long,EstadoSolicitudAfiliacionPersonaEnum)");
			}
		} catch (Exception e) {
			logger.error("No es posible actualizar el estado de la solicitud safiliacion persona", e);
			logger.debug("Finaliza actualizarEstadoSolicitudAfiliacionPersona(Long)");
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#crearSolicitudAsociacionPersonaEntidadPagadora(com.asopagos.dto.DatosBasicosIdentificacionDTO)
	 */
	@Override
	public Long crearSolicitudAsociacionPersonaEntidadPagadora(AfiliadoInDTO inAfiliadoDTO) {
		logger.debug("Inicia crearSolicitudAsociacionPersonaEntidadPagadora(AfiliadoInDTO)");
		// Busqueda de la persona el numero de identificación y el tipo de
		// identificación
		Query qPersona = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_IDENTIFICACION);
		qPersona.setParameter("numIdentificacion", inAfiliadoDTO.getPersona().getNumeroIdentificacion());
		qPersona.setParameter("tipoIdentificacion", inAfiliadoDTO.getPersona().getTipoIdentificacion());
		List<Persona> listaPersona = qPersona.getResultList();
		// Verificacion de que exista la persona
		if (listaPersona.size() > 0) {
			Solicitud solicitud = new Solicitud();
			// Por especificacion se setea el campo estadoDocumento en
			// null
			solicitud.setEstadoDocumentacion(null);
			solicitud.setTipoTransaccion(inAfiliadoDTO.getPersona().getTipoTransaccion());
			solicitud.setCanalRecepcion(inAfiliadoDTO.getCanalRecepcion());
			solicitud.setFechaCreacion(new Date());
			entityManager.persist(solicitud);
			// Consultar rol Afiliado por Id
			Query qRolAfiliacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_ID);
			qRolAfiliacion.setParameter("idRolAfiliado", inAfiliadoDTO.getIdRolAfiliado());
			RolAfiliado rolAfiliadoResult = (RolAfiliado) qRolAfiliacion.getSingleResult();
			// Creacion de la SolicitudAsociacionPersonaEntidadPagadora
			SolicitudAsociacionPersonaEntidadPagadora solAfiPerEntidadPagadora = new SolicitudAsociacionPersonaEntidadPagadora();
			solAfiPerEntidadPagadora.setSolicitudGlobal(solicitud);
			solAfiPerEntidadPagadora.setRolAfiliado(rolAfiliadoResult);
			solAfiPerEntidadPagadora.setEstado(EstadoSolicitudPersonaEntidadPagadoraEnum.APROBADA);
			entityManager.persist(solAfiPerEntidadPagadora);
			logger.debug("Finaliza crearSolicitudAsociacionPersonaEntidadPagadora(AfiliadoInDTO)");
			return solicitud.getIdSolicitud();
		} else {
			// Persona no encontrada
			return null;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#
	 *      actualizarSolicitudAfiliacionPersona(java.lang.Long,
	 *      com.asopagos.dto.SolicitudDTO)
	 */
	@Override
	public void actualizarSolicitudAfiliacionPersona(Long idSolicitudGlobal, SolicitudDTO solicitudDTO) {
	    /*
		try {
			logger.debug("Inicia actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
			Solicitud solicitud = entityManager.find(Solicitud.class, idSolicitudGlobal);
			if (solicitud != null) {
				if (solicitudDTO.getCanalRecepcion() != null && !solicitudDTO.getCanalRecepcion().equals("")) {
					solicitud.setCanalRecepcion(solicitudDTO.getCanalRecepcion());
				}
				if (solicitudDTO.getIdInstanciaProceso() != null && !solicitudDTO.getIdInstanciaProceso().isEmpty()) {
					solicitud.setIdInstanciaProceso(solicitudDTO.getIdInstanciaProceso());
				}
				if (solicitudDTO.getEstadoDocumentacion() != null) {
					solicitud.setEstadoDocumentacion(solicitudDTO.getEstadoDocumentacion());
				}
				if (solicitudDTO.getMetodoEnvio() != null) {
					solicitud.setMetodoEnvio(solicitudDTO.getMetodoEnvio());
				}
				if (solicitudDTO.getIdCajaCorrespondencia() != null) {
					solicitud.setIdCajaCorrespondencia(solicitudDTO.getIdCajaCorrespondencia());
				}
				if (solicitudDTO.getTipoTransaccion() != null) {
					solicitud.setTipoTransaccion(solicitudDTO.getTipoTransaccion());
				}
				if (solicitudDTO.getClasificacion() != null) {
					solicitud.setClasificacion(solicitudDTO.getClasificacion());
				}
				if (solicitudDTO.getTipoRadicacion() != null) {
					solicitud.setTipoRadicacion(solicitudDTO.getTipoRadicacion());
				}
				if (solicitudDTO.getNumeroRadicacion() != null && !solicitudDTO.getNumeroRadicacion().isEmpty()) {
					solicitud.setNumeroRadicacion(solicitudDTO.getNumeroRadicacion());
				}
				if (solicitudDTO.getUsuarioRadicacion() != null && !solicitudDTO.getUsuarioRadicacion().isEmpty()) {
					solicitud.setUsuarioRadicacion(solicitudDTO.getUsuarioRadicacion());
				}
				if (solicitudDTO.getFechaRadicacion() != null) {
					solicitud.setFechaRadicacion(solicitudDTO.getFechaRadicacion());
				}
				if (solicitudDTO.getCiudadSedeRadicacion() != null
						&& !solicitudDTO.getCiudadSedeRadicacion().isEmpty()) {
					solicitud.setCiudadSedeRadicacion(solicitudDTO.getCiudadSedeRadicacion());
				}
				if (solicitudDTO.getDestinatario() != null && !solicitudDTO.getDestinatario().isEmpty()) {
					solicitud.setDestinatario(solicitudDTO.getDestinatario());
				}
				if (solicitudDTO.getSedeDestinatario() != null && !solicitudDTO.getSedeDestinatario().isEmpty()) {
					solicitud.setSedeDestinatario(solicitudDTO.getSedeDestinatario());
				}
				if (solicitudDTO.getFechaCreacion() != null && !solicitudDTO.getFechaCreacion().equals("")) {
					solicitud.setFechaCreacion(solicitudDTO.getFechaCreacion());
				}
				if (solicitudDTO.getObservacion() != null && !solicitudDTO.getObservacion().isEmpty()) {
					if (solicitud.getObservacion() != null && solicitud.getObservacion().isEmpty()) {
						solicitud.setObservacion(solicitud.getObservacion().concat(solicitudDTO.getObservacion()));
					} else {
						solicitud.setObservacion(solicitudDTO.getObservacion());
					}

				}
				if (solicitudDTO.getCargaAfiliacionMultiple() != null
						&& !solicitudDTO.getCargaAfiliacionMultiple().equals("")) {
					solicitud.setCargaAfiliacionMultiple(solicitudDTO.getCargaAfiliacionMultiple());
				}

				if (solicitudDTO.getAnulada() != null) {
					solicitud.setAnulada(solicitudDTO.getAnulada());
				}
				entityManager.merge(solicitud);
			}
			logger.debug("Finaliza actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
		} catch (Exception e) {
			logger.error("No es posible actualizar la solicitud de afiliacion persona", e);
			logger.debug("Finaliza actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
		}
		*/
	    ActualizarSolicitudAfiliacionPersonaRutine a = new ActualizarSolicitudAfiliacionPersonaRutine();
	    a.actualizarSolicitudAfiliacionPersona(idSolicitudGlobal, solicitudDTO, entityManager);
	    
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#
	 *      actualizarSolicitudAfiliacionPersona(java.lang.Long,
	 *      com.asopagos.dto.SolicitudDTO)
	 */
	@Override
	public void actualizarSolicitudAfiliacionPersonaMasivas(Long idSolicitudGlobal, SolicitudDTO solicitudDTO) {
		ActualizarSolicitudAfiliacionPersonaRutine a = new ActualizarSolicitudAfiliacionPersonaRutine();
		a.actualizarSolicitudAfiliacionPersonaMasivas(idSolicitudGlobal, solicitudDTO, entityManager);

	}


	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#consultarSolicitudAfiliacionPersona(java.lang.String,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum)
	 */
	@Override
	public ListadoSolicitudesAfiliacionDTO consultarSolicitudAfiliacionPersonaAfiliada(String numeroIdentificacion,
			TipoIdentificacionEnum tipoIdentificacion, TipoAfiliadoEnum tipoAfiliado,
			EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud, CanalRecepcionEnum canalRecepcion,
			String numeroRadicacion) {
		ListadoSolicitudesAfiliacionDTO listadoSolicitudesDTO = new ListadoSolicitudesAfiliacionDTO();
		logger.debug(
				"consultarSolicitudAfiliacionPersonaAfiliada(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion, TipoAfiliadoEnum tipoAfiliadoEnum,EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud, CanalRecepcionEnum canalRecepcion)");
		List<CanalRecepcionEnum> canales = new ArrayList<>();
		List<TipoAfiliadoEnum> tipoAfi = new ArrayList<>();
		List<SolicitudAfiliacionPersonaDTO> lstSolicitudesAfiliacionPersona = new ArrayList<SolicitudAfiliacionPersonaDTO>();
		if (numeroIdentificacion != null && !numeroIdentificacion.isEmpty() && tipoIdentificacion != null) {
			if (estadoSolicitud != null || canalRecepcion != null || tipoAfiliado != null) {

				if (canalRecepcion != null) {
					canales.add(canalRecepcion);
				} else {
					canales.add(CanalRecepcionEnum.PRESENCIAL);
					canales.add(CanalRecepcionEnum.WEB);
				}
				if (tipoAfiliado != null) {
					tipoAfi.add(tipoAfiliado);
				} else {
					tipoAfi.add(TipoAfiliadoEnum.PENSIONADO);
					tipoAfi.add(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
					tipoAfi.add(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
				}

				List<Afiliado> afiliados = entityManager
						.createNamedQuery(
								NamedQueriesConstants.BUSCAR_PERSONA_NUMERO_IDENTIFICACION_TIPO_IDENTIFICACION,
								Afiliado.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
				if (!afiliados.isEmpty()) {
					Afiliado afiliado = afiliados.get(0);
					Query qRolAfiliacion = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_ID_AFILIADO);
					qRolAfiliacion.setParameter("idAfiliado", afiliado.getIdAfiliado());
					List<RolAfiliado> rolAfiliadoResult = qRolAfiliacion.getResultList();
					for (RolAfiliado rolAfiliado : rolAfiliadoResult) {

						List<SolicitudAfiliacionPersona> solAfiliacionPersona = new ArrayList<>();
						if (estadoSolicitud != null) {
							if (numeroRadicacion != null && !numeroRadicacion.isEmpty()) {
								solAfiliacionPersona = entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_ESTADO_SOLICITUD_CANAL_NUMERORADICACION)
										.setParameter("idRolAfiliado", rolAfiliado.getIdRolAfiliado())
										.setParameter("canalRecepcion", canales)
										.setParameter("estadoSolicitud", estadoSolicitud)
										.setParameter("tipoAfiliadoEnum", tipoAfi)
										.setParameter("numeroRadicacion", numeroRadicacion).getResultList();
							} else {
								solAfiliacionPersona = entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_ESTADO_SOLICITUD_CANAL)
										.setParameter("idRolAfiliado", rolAfiliado.getIdRolAfiliado())
										.setParameter("canalRecepcion", canales)
										.setParameter("estadoSolicitud", estadoSolicitud)
										.setParameter("tipoAfiliadoEnum", tipoAfi).getResultList();
							}
						} else if (numeroRadicacion != null && !numeroRadicacion.isEmpty()) {
							solAfiliacionPersona = entityManager
									.createNamedQuery(
											NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_SOLICITUD_CANAL_NUMERORADICACION)
									.setParameter("idRolAfiliado", rolAfiliado.getIdRolAfiliado())
									.setParameter("canalRecepcion", canales).setParameter("tipoAfiliadoEnum", tipoAfi)
									.setParameter("numeroRadicacion", numeroRadicacion).getResultList();
						} else {
							solAfiliacionPersona = entityManager
									.createNamedQuery(
											NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_ID_ROL_AFILIADO_TIPO_SOLICITUD_CANAL)
									.setParameter("idRolAfiliado", rolAfiliado.getIdRolAfiliado())
									.setParameter("canalRecepcion", canales).setParameter("tipoAfiliadoEnum", tipoAfi)
									.getResultList();
						}
						Long idPersona = null;
						for (SolicitudAfiliacionPersona solicitudAfiliacionPersona : solAfiliacionPersona) {
							idPersona = solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getPersona()
									.getIdPersona();
							PersonaDetalle personaDetalle = null;
							try {
								personaDetalle = entityManager
										.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_PERSONA_DETALLE,
												PersonaDetalle.class)
										.setParameter("idPersona", idPersona).getSingleResult();

							} catch (NoResultException nre) {
								personaDetalle = null;
							}

							lstSolicitudesAfiliacionPersona
									.add(SolicitudAfiliacionPersonaDTO.convertSolicitudAfiliacionPersonaToDTO(
											solicitudAfiliacionPersona, personaDetalle));
						}
					}
				} else {
					listadoSolicitudesDTO.setLstSolicitudes(lstSolicitudesAfiliacionPersona);
					return listadoSolicitudesDTO;
				}
			} else {
				List<SolicitudAfiliacionPersona> solAfiliacionPersona = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_TIPO_NUM_IDENTIFICACION)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

				if (solAfiliacionPersona != null && !solAfiliacionPersona.isEmpty()) {
					Long idPersona = null;
					for (SolicitudAfiliacionPersona solicitudAfiliacionPersona : solAfiliacionPersona) {
						idPersona = solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getPersona()
								.getIdPersona();

						PersonaDetalle personaDetalle = null;
						try {
							personaDetalle = entityManager
									.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_PERSONA_DETALLE,
											PersonaDetalle.class)
									.setParameter("idPersona", idPersona).getSingleResult();

						} catch (NoResultException nre) {
							personaDetalle = null;
						}

						lstSolicitudesAfiliacionPersona.add(SolicitudAfiliacionPersonaDTO
								.convertSolicitudAfiliacionPersonaToDTO(solicitudAfiliacionPersona, personaDetalle));
					}
				}
			}
		}	
		listadoSolicitudesDTO.setLstSolicitudes(lstSolicitudesAfiliacionPersona);
		return listadoSolicitudesDTO;

	}

	@Override
	public ListadoSolicitudesAfiliacionDTO consultarSolicitudAfiliacionPersonaPorRadicacion(CanalRecepcionEnum canalRecepcion, String numeroRadicacion){
		logger.info("Inicia consultarSolicitudAfiliacionPersonaPorRadicacion(canalesRecepcion,String)");
		List<SolicitudAfiliacionPersona> solAfiliacionPersona = new ArrayList<SolicitudAfiliacionPersona>();
		List<SolicitudAfiliacionPersonaDTO> lstSolicitudesAfiliacionPersona = new ArrayList<SolicitudAfiliacionPersonaDTO>();
		ListadoSolicitudesAfiliacionDTO listadoSolicitudesDTO = new ListadoSolicitudesAfiliacionDTO();


		if(numeroRadicacion != null && !numeroRadicacion.isEmpty()){
				solAfiliacionPersona = entityManager
									.createNamedQuery(
											NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_NUMERORADICACION_CANAL)
									.setParameter("canalRecepcion", canalRecepcion)
									.setParameter("numeroRadicacion", numeroRadicacion)
									.getResultList();
						
				Long idPersona = null;
				for (SolicitudAfiliacionPersona solicitudAfiliacionPersona : solAfiliacionPersona) {
						idPersona = solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getPersona()
						.getIdPersona();
						PersonaDetalle personaDetalle = null;
						try {
							personaDetalle = entityManager
									.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_PERSONA_DETALLE,
											PersonaDetalle.class)
									.setParameter("idPersona", idPersona).getSingleResult();

						} catch (NoResultException nre) {
							personaDetalle = null;
						}
						lstSolicitudesAfiliacionPersona
								.add(SolicitudAfiliacionPersonaDTO.convertSolicitudAfiliacionPersonaToDTO(
										solicitudAfiliacionPersona, personaDetalle));
					}
				}
					listadoSolicitudesDTO.setLstSolicitudes(lstSolicitudesAfiliacionPersona);
					return listadoSolicitudesDTO;
				}

	@Override
	public void actualizarSolicitudAsociacionPersonaEntidadPagadora(
			SolicitudAsociacionPersonaEntidadPagadoraDTO inDTO) {
		if (inDTO != null) {
			SolicitudAsociacionPersonaEntidadPagadora solicitudAsociacion = entityManager.find(
					SolicitudAsociacionPersonaEntidadPagadora.class,
					inDTO.getIdSolicitudAsociacionPersonaEntidadPagadora());
			if (solicitudAsociacion != null) {
				Solicitud solicitudGlobal = solicitudAsociacion.getSolicitudGlobal();
				if (inDTO.getSolicitudDTO().getIdInstanciaProceso() != null
						&& !inDTO.getSolicitudDTO().getIdInstanciaProceso().isEmpty()) {
					solicitudGlobal.setIdInstanciaProceso(inDTO.getSolicitudDTO().getIdInstanciaProceso());
				}
				if (inDTO.getSolicitudDTO().getCanalRecepcion() != null) {
					solicitudGlobal.setCanalRecepcion(inDTO.getSolicitudDTO().getCanalRecepcion());
				}
				if (inDTO.getSolicitudDTO().getEstadoDocumentacion() != null) {
					solicitudGlobal.setEstadoDocumentacion(inDTO.getSolicitudDTO().getEstadoDocumentacion());
				}
				if (inDTO.getSolicitudDTO().getMetodoEnvio() != null) {
					solicitudGlobal.setMetodoEnvio(inDTO.getSolicitudDTO().getMetodoEnvio());
				}
				if (inDTO.getSolicitudDTO().getIdCajaCorrespondencia() != null) {
					solicitudGlobal.setIdCajaCorrespondencia(inDTO.getSolicitudDTO().getIdCajaCorrespondencia());
				}
				if (inDTO.getSolicitudDTO().getClasificacion() != null) {
					solicitudGlobal.setClasificacion(inDTO.getSolicitudDTO().getClasificacion());
				}
				if (inDTO.getSolicitudDTO().getTipoRadicacion() != null) {
					solicitudGlobal.setTipoRadicacion(inDTO.getSolicitudDTO().getTipoRadicacion());
				}
				if (inDTO.getSolicitudDTO().getNumeroRadicacion() != null
						&& !inDTO.getSolicitudDTO().getNumeroRadicacion().isEmpty()) {
					solicitudGlobal.setNumeroRadicacion(inDTO.getSolicitudDTO().getNumeroRadicacion());
				}
				if (inDTO.getSolicitudDTO().getUsuarioRadicacion() != null
						&& !inDTO.getSolicitudDTO().getUsuarioRadicacion().isEmpty()) {
					solicitudGlobal.setUsuarioRadicacion(inDTO.getSolicitudDTO().getUsuarioRadicacion());
				}
				if (inDTO.getSolicitudDTO().getFechaRadicacion() != null) {
					solicitudGlobal.setFechaRadicacion(inDTO.getSolicitudDTO().getFechaRadicacion());
				}
				if (inDTO.getSolicitudDTO().getCiudadSedeRadicacion() != null
						&& !inDTO.getSolicitudDTO().getCiudadSedeRadicacion().isEmpty()) {
					solicitudGlobal.setCiudadSedeRadicacion(inDTO.getSolicitudDTO().getCiudadSedeRadicacion());
				}
				if (inDTO.getSolicitudDTO().getDestinatario() != null
						&& !inDTO.getSolicitudDTO().getDestinatario().isEmpty()) {
					solicitudGlobal.setDestinatario(inDTO.getSolicitudDTO().getDestinatario());
				}
				if (inDTO.getSolicitudDTO().getSedeDestinatario() != null
						&& !inDTO.getSolicitudDTO().getSedeDestinatario().isEmpty()) {
					solicitudGlobal.setSedeDestinatario(inDTO.getSolicitudDTO().getSedeDestinatario());
				}
				if (inDTO.getSolicitudDTO().getFechaCreacion() != null) {
					solicitudGlobal.setFechaCreacion(inDTO.getSolicitudDTO().getFechaCreacion());
				}
				if (inDTO.getSolicitudDTO().getObservacion() != null
						&& !inDTO.getSolicitudDTO().getObservacion().isEmpty()) {
					solicitudGlobal.setObservacion(inDTO.getSolicitudDTO().getObservacion());
				}

				if (inDTO.getSolicitudDTO().getAnulada()) {
					solicitudGlobal.setAnulada(inDTO.getSolicitudDTO().getAnulada());
				}

				entityManager.merge(solicitudGlobal);
				solicitudAsociacion.setSolicitudGlobal(solicitudGlobal);

				if (inDTO.getConsecutivo() != null && !inDTO.getConsecutivo().isEmpty()) {
					solicitudAsociacion.setConsecutivo(inDTO.getConsecutivo());
				}
				if (inDTO.getEstado() != null) {
					solicitudAsociacion.setEstado(inDTO.getEstado());
				}
				if (inDTO.getFechaGestion() != null) {
					solicitudAsociacion.setFechaGestion(inDTO.getFechaGestion());
				}
				if (inDTO.getIdentificadorArchivoCarta() != null && !inDTO.getIdentificadorArchivoCarta().isEmpty()) {
					solicitudAsociacion.setIdentificadorArchivoCarta(inDTO.getIdentificadorArchivoCarta());
				}
				if (inDTO.getIdentificadorArchivoPlano() != null && !inDTO.getIdentificadorArchivoPlano().isEmpty()) {
					solicitudAsociacion.setIdentificadorArchivoPlano(inDTO.getIdentificadorArchivoPlano());
				}
				if (inDTO.getIdentificadorCartaResultadoGestion() != null
						&& !inDTO.getIdentificadorCartaResultadoGestion().isEmpty()) {
					solicitudAsociacion
							.setIdentificadorCartaResultadoGestion(inDTO.getIdentificadorCartaResultadoGestion());
				}
				if (inDTO.getUsuarioGestion() != null && !inDTO.getUsuarioGestion().isEmpty()) {
					solicitudAsociacion.setUsuarioGestion(inDTO.getUsuarioGestion());
				}
				if (inDTO.getTipoGestion() != null) {
					solicitudAsociacion.setTipoGestion(inDTO.getTipoGestion());
				}
				entityManager.merge(solicitudAsociacion);
			}
		} else {
			throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
		}
	}
	
	@Override
    public List<SolicitudAfiliacionPersonaDTO> obtenerAfiliacionesSinInstanciaProceso() {
        logger.debug("Inicia obtenerAfiliacionesSinInstanciaProceso()");
        
        List<String> estados = new ArrayList<String>();
        List<String> canales = new ArrayList<String>();
        LocalDate fecha =  LocalDate.of(2020, 1, 1);
        List<SolicitudAfiliacionPersonaDTO> listaSolicitudes = new ArrayList<SolicitudAfiliacionPersonaDTO>();
        SolicitudAfiliacionPersonaDTO solicitud =null;
        
        estados.add(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA.name());
        canales.add(CanalRecepcionEnum.PRESENCIAL.name());
        
        
        List<Object[]> solicitudes = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_SIN_INSTANCIA_PROCESO)
                .setParameter("estados", estados)
                .setParameter("canales", canales)
                .setParameter("fechaInicial", fecha)
                .getResultList();
        
        for (Object[] object : solicitudes) {
            solicitud = new SolicitudAfiliacionPersonaDTO();
            solicitud.setIdSolicitudGlobal(Long.valueOf(object[0].toString()));
            solicitud.setNumeroRadicacion(object[1].toString());
            solicitud.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.valueOf(object[2].toString()));
            
            listaSolicitudes.add(solicitud);
            
        }
        
        logger.debug("Finaliza obtenerAfiliacionesSinInstanciaProceso()");
        return listaSolicitudes;
    }

	
	@Override
	public Object[] ConsultarClasificacionPensionado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.info("Inicia tipoIdentificacion() "+tipoIdentificacion.name());
		logger.info("Inicia numeroIdentificacion() "+numeroIdentificacion);
		
		
		Object[] result = (Object[]) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CLASIFICACION_PENSIONADO)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion)
				.getSingleResult();

				// List<Object[]> clasificacionFinal = null;
				// for (ClasificacionEnum clasificacion : ClasificacionEnum.values()) {
				// 	if (clasificacion.name().equals(clasificacionResult) ) {
				// 		clasificacionFinal = clasificacion;
				// 		break;
				// 	}
				// }
				return result;

		
	}

	@Override
    public Afiliado25AniosDTO ConsultarDatosExistenteNoPensionado(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        logger.info("Inicia tipoIdentificacion() "+tipoIdentificacion.name());
        logger.info("Inicia numeroIdentificacion() "+numeroIdentificacion);
        
        
        Object[] result = (Object[]) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EXISTENTE_NO_PENSIONADO)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .getSingleResult();

				
				Afiliado25AniosDTO afiliado = new  Afiliado25AniosDTO();
				TipoIdentificacionEnum resultTipoIdentificacionEnum = null;
				logger.info("Inicia result[0]() "+result[0]);

				if(result[0] != null){
				logger.info("Inicia result[0]() entra"+result[0]);

					String tipodocumento = result[0].toString();
					for (TipoIdentificacionEnum tipoIdentificacionEnum : TipoIdentificacionEnum.values()) {
						if (tipoIdentificacionEnum.name().equals(tipodocumento) ) {
							resultTipoIdentificacionEnum = tipoIdentificacionEnum;
							afiliado.setTipoIdentificacion(resultTipoIdentificacionEnum);

							break;
						}
					}
				}
                    
                       
                    afiliado.setNumeroIdentificacion (result[1] == null ? null: result[1].toString());
                    afiliado.setPrimerNombre(result[2] == null ? null: result[2].toString());
                    afiliado.setSegundoNombre(result[3] == null ? null: result[3].toString());
                    afiliado.setPrimerApellido(result[4] == null ? null: result[4].toString());
                    afiliado.setSegundoApellido(result[5] == null ? null: result[5].toString());
					if(result[6] != null){
						SimpleDateFormat s2 = new SimpleDateFormat("yyyyMMdd");
						Date date = null;
						
						try {
							date = s2.parse(result[6].toString());
						} catch (ParseException e) {
							e.printStackTrace(); 
						}
						
						String fecha = s2.format(date);

						afiliado.setFechaNacimiento(fecha);

					}
                    afiliado.setDepartamento(result[7] == null ? null: result[7].toString());
                    afiliado.setMunicipio(result[8] == null ? null: result[8].toString());
                    afiliado.setDireccion(result[9] == null ? null: result[9].toString()); 
                    afiliado.setGenero(result[10] == null ? null: result[10].toString()); 
                    afiliado.setEstadoCivil(result[11] == null ? null: result[11].toString());
                    afiliado.setTelefono(result[12] == null ? null: Long.parseLong(result[12].toString()));
                    afiliado.setCelular(result[13] == null ? null: Long.parseLong(result[13].toString()));
                    afiliado.setCorreoElectronico(result[14] == null ? null: result[14].toString());
                    
                

				// List<Object[]> clasificacionFinal = null;
				// for (ClasificacionEnum clasificacion : ClasificacionEnum.values()) {
				// 	if (clasificacion.name().equals(clasificacionResult) ) {
				// 		clasificacionFinal = clasificacion;
				// 		break;
				// 	}
				// }
				return afiliado;

        
    }

		public ConsolaEstadoCargueProcesoDTO obtenerCarguePensionados25Anios(Long idCargue){
				ConsolaEstadoCargueMasivo result = (ConsolaEstadoCargueMasivo) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARGUE_PENSIONADOS)
				.setParameter("idCargue", idCargue)
				.getSingleResult();
				ConsolaEstadoCargueProcesoDTO f = new ConsolaEstadoCargueProcesoDTO();
			    f.convertToDTO(result);

				return f;
        
		}

		public ConsolaEstadoCargueProcesoDTO obtenerCargueTrasladosMasivos(Long idCargue){
				ConsolaEstadoCargueMasivo result = (ConsolaEstadoCargueMasivo) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARGUE_TRANSLADOS)
				.setParameter("idCargue", idCargue)
				.getSingleResult();
				ConsolaEstadoCargueProcesoDTO f = new ConsolaEstadoCargueProcesoDTO();
			    f.convertToDTO(result);

				return f;
        
		}



}
