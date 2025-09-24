package com.asopagos.afiliaciones.ejb;

import static com.asopagos.util.Interpolator.interpolate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.afiliaciones.clients.PersistirFechaCreacionCertificado;
import com.asopagos.afiliaciones.constants.NamedQueriesConstants;
import com.asopagos.afiliaciones.constants.ValidacionEtiquetaConstans;
import com.asopagos.afiliaciones.dto.ActualizacionEstadoListaEspecialDTO;
import com.asopagos.afiliaciones.dto.CertificadoEscolaridadOutDTO;
import com.asopagos.afiliaciones.dto.ConsultarInformacionCompletaAfiliadoDTO;
import com.asopagos.afiliaciones.dto.ConsultarInformacionCompletaBeneficiarioDTO;
import com.asopagos.afiliaciones.dto.HistoricoAfiliacion360DTO;
import com.asopagos.afiliaciones.dto.InfoHijoBiologicoOutDTO;
import com.asopagos.afiliaciones.dto.InfoPadresBiologicosOutDTO;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionEmpleador360DTO;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.dto.ListaEspecialRevisionDTO;
import com.asopagos.afiliaciones.dto.RelacionTrabajadorEmpresaDTO;
import com.asopagos.afiliaciones.service.AfiliacionesService;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.database.NumeroRadicadoUtil;
import com.asopagos.dto.AfiliadoNovedadRetiroNoAplicadaDTO;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.dto.ProductoNoConformeDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.modelo.IntentoAfiliacionDTO;
import com.asopagos.dto.modelo.SolicitudAfiliacionPersonaModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.IntentoAfiliacion;
import com.asopagos.entidades.ccf.afiliaciones.IntentoAfiliacionRequisito;
import com.asopagos.entidades.ccf.afiliaciones.ProductoNoConforme;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.entidades.ccf.aportes.SolicitudAporte;
import com.asopagos.entidades.ccf.aportes.SolicitudCierreRecaudo;
import com.asopagos.entidades.ccf.aportes.SolicitudCorreccionAporte;
import com.asopagos.entidades.ccf.aportes.SolicitudDevolucionAporte;
import com.asopagos.entidades.ccf.cartera.SolicitudDesafiliacion;
import com.asopagos.entidades.ccf.cartera.SolicitudFiscalizacion;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroElectronico;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroFisico;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroManual;
import com.asopagos.entidades.ccf.cartera.SolicitudPreventiva;
import com.asopagos.entidades.ccf.core.EtiquetaCorrespondenciaRadicado;
import com.asopagos.entidades.ccf.fovis.SolicitudAnalisisNovedadFovis;
import com.asopagos.entidades.ccf.fovis.SolicitudAsignacion;
import com.asopagos.entidades.ccf.fovis.SolicitudGestionCruce;
import com.asopagos.entidades.ccf.fovis.SolicitudLegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.SolicitudNovedadFovis;
import com.asopagos.entidades.ccf.fovis.SolicitudPostulacion;
import com.asopagos.entidades.ccf.fovis.SolicitudVerificacionFovis;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.CertificadoEscolarBeneficiario;
import com.asopagos.entidades.pila.soporte.LogErrorPilaM1;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoProductoNoConformeEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.core.ProcedenciaEtiquetaEnum;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoListaEspecialRevisionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliacionesrutines.radicarlistasolicitudes.RadicarListaSolicitudesRutine;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.ObtenerTipoSolicitud;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.entidades.ccf.novedades.CargueMultipleSupervivencia;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.afiliaciones.dto.InfoPersonasEnRelacionHijosOutDTO;
import com.asopagos.entidades.ccf.afiliaciones.ArchivosTrasladosEmpresasCCF;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.dto.ResultadoValidacionArchivoTrasladoDTO;




/**
 * <b>Descripcion:</b> Clase que <br/>
 * implemanta los servicios relacionados con la gestion de afiliaciones
 * <b>Módulo:</b> Asopagos - HU <br/>
 * m 66-92-93-70
 *
 * @author Josué Nicolás Pinzón Villamil
 *         <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon@heinsohn.com.co
 *         </a>
 */
@Stateless
public class AfiliacionesBusiness implements AfiliacionesService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "afiliaciones_PU")
	private EntityManager entityManager;

	@PersistenceContext(unitName = "AfiliacionesAud_PU")
	private EntityManager entityManagerAud;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(AfiliacionesBusiness.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#asignarSolicitudAlBack()
	 */
	@Override
	public void asignarSolicitudAlBack() {
		// TODO Auto-generated method stub

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#registrarIntentoAfliliacion(com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO)
	 */
	@Override
	public Long registrarIntentoAfliliacion(IntentoAfiliacionInDTO intentoAfiliacionInDTO, UserDTO userDTO) {

		IntentoAfiliacion intentoAfiliacion = new IntentoAfiliacion();
		intentoAfiliacion.setCausaIntentoFallido(intentoAfiliacionInDTO.getCausaIntentoFallido());
		intentoAfiliacion.setFechaCreacion(new Date());
		intentoAfiliacion.setFechaInicioProceso(intentoAfiliacionInDTO.getFechaInicioProceso());
		if (intentoAfiliacionInDTO.getIdSolicitud() != null) {
			intentoAfiliacion.setIdSolicitud(intentoAfiliacionInDTO.getIdSolicitud());
		} else if (intentoAfiliacionInDTO.getSolicitud() != null) {
			SolicitudDTO soliDTO = intentoAfiliacionInDTO.getSolicitud();
			Solicitud solicitud = new Solicitud();
			if (soliDTO.getCanalRecepcion() != null) {
				solicitud.setCanalRecepcion(soliDTO.getCanalRecepcion());
			}
			if (soliDTO.getClasificacion() != null) {
				solicitud.setClasificacion(soliDTO.getClasificacion());
			}
			if (soliDTO.getTipoTransaccion() != null) {
				solicitud.setTipoTransaccion(soliDTO.getTipoTransaccion());
			}
			solicitud.setFechaCreacion(new Date());
			solicitud.setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
			entityManager.persist(solicitud);
			intentoAfiliacion.setIdSolicitud(solicitud.getIdSolicitud());
		}
		intentoAfiliacion.setTipoTransaccion(intentoAfiliacionInDTO.getTipoTransaccion());
		intentoAfiliacion.setUsuarioCreacion(userDTO.getNombreUsuario());
		intentoAfiliacion.setTipoIdentificacion(
				intentoAfiliacionInDTO.getTipoIdentificacion() != null ? intentoAfiliacionInDTO.getTipoIdentificacion()
						: null);
		intentoAfiliacion.setNumeroIdentificacion(intentoAfiliacionInDTO.getNumeroIdentificacion() != null
				? intentoAfiliacionInDTO.getNumeroIdentificacion()
				: null);

		entityManager.persist(intentoAfiliacion);
		if (intentoAfiliacionInDTO.getCausaIntentoFallido()
				.equals(CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_REQUISITOS_DOCUMENTALES)) {

			if (intentoAfiliacionInDTO.getIdsRequsitos() == null
					|| intentoAfiliacionInDTO.getIdsRequsitos().isEmpty()) {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
			}

			for (Long idRequisito : intentoAfiliacionInDTO.getIdsRequsitos()) {
				IntentoAfiliacionRequisito intentoAfiliacionRequisito = new IntentoAfiliacionRequisito();
				intentoAfiliacionRequisito.setIntentoAfiliacion(intentoAfiliacion);
				intentoAfiliacionRequisito.setIdRequisito(idRequisito);
				entityManager.persist(intentoAfiliacionRequisito);
			}
		}
		return intentoAfiliacion.getIdIntentoAfiliacion();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#actualizarSolicitud(java.lang.Long,
	 *      com.asopagos.entidades.ccf.general.Solicitud)
	 */
	@Override
	public void actualizarSolicitud(Long idSolicitud, Solicitud solicitud) {
		solicitud.setIdSolicitud(idSolicitud);
		entityManager.merge(solicitud);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#buscarSolicitudPorId(java.lang.Long)
	 */
	@Override
	public Solicitud buscarSolicitudPorId(Long idSolicitud) {
		return entityManager.find(Solicitud.class, idSolicitud);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#validarEtiqueta(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String validarEtiqueta(String codigoEtiqueta) {

		Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ETIQUETA);
		q.setParameter("codigoEtiqueta", codigoEtiqueta);
		List<EtiquetaCorrespondenciaRadicado> listaResult = q.getResultList();

		if (listaResult.size() == 1) {
			if (listaResult.get(0).getAsignada()) {
				return ValidacionEtiquetaConstans.ESTA_EN_USO;
			} else {
				return ValidacionEtiquetaConstans.EXITO;
			}
		}
		return ValidacionEtiquetaConstans.NO_EXISTE_CODIGO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#radicarSolicitud(
	 *      java.lang.Long, java.lang.String)
	 */
	@Override
	public String radicarSolicitud(Long idSolicitud, String sccID, UserDTO userDTO) {
		logger.debug("Inicia radicarSolicitud(String, Long)");
		String numeroRadicado = obtenerNumeroRadicado(idSolicitud, userDTO);
		logger.debug("Finalizar radicarSolicitud(String, Long)");
		return numeroRadicado;
	}

	// metodos para la generación o asignación de número de radicados de la
	// solicitud o de número de stikers para correspondencia física

	/**
	 * Método que se encarga de retornar un número de radicado determinando si
	 * se auto genera o se trata de uno preimpreso
	 * 
	 * <ul>
	 * <li>número de radicado CCAAAA###### donde:
	 * <ul>
	 * <li>CC: código de la caja de compensación</li>
	 * <li>AAAA: año</li>
	 * <li>######: número de consecutivo (se reinicia cada año)</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param idSolicitud
	 * @param sccID
	 * @return
	 */
	private String obtenerNumeroRadicado(Long idSolicitud, UserDTO userDTO) {
		logger.debug("Inicia obtenerNumeroRadicado(Long, String)");

		if (idSolicitud == null) {
			logger.debug("Finaliza obtenerNumeroRadicado(Long, String): Parametros inválidos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
		}

		// se ingresa la fecha del sistema para la fecha de la
		// radicación
		Solicitud solicitud = entityManager.find(Solicitud.class, idSolicitud);
		logger.info("solicitud afiliaciones" + solicitud);
		if (solicitud == null) {
			logger.debug("Finaliza obtenerNumeroRadicado(Long, String): No existe la solicitud");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
		}

		// TODO: validar si se debe eliminar esta verificación
		// Se verifica que la solicitud no se haya radicado previamente
		if (solicitud.getNumeroRadicacion() != null) {
			logger.debug("Finaliza obtenerNumeroRadicado(Long, String): La solicitud ya fué radicada previamente");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
		}

		// genera el numero de radicado
		NumeroRadicadoCorrespondenciaDTO dto = obtenerNumeroRadicadoCorrespondencia(TipoEtiquetaEnum.NUMERO_RADICADO, 1,
				userDTO);
		Calendar fecha = Calendar.getInstance();
		String numeroRadicado = dto.nextValue();

		// actualiza la solicitud
		Solicitud solicitudUpdate = entityManager.getReference(Solicitud.class, idSolicitud);
		solicitudUpdate.setFechaRadicacion(fecha.getTime());
		solicitudUpdate.setNumeroRadicacion(numeroRadicado);
		entityManager.merge(solicitudUpdate);

		// entityManager.createNamedQuery(NamedQueriesConstants.RADICAR_SOLICITUD_GLOBAL)
		// .setParameter("fechaRadicacion", dto.getFecha())
		// .setParameter("numeroRadicacion",
		// dto.getNumeroRadicado()).setParameter("idSolicitud", idSolicitud)
		// .executeUpdate();

		// marca el codigo como asignado
		// asignarCodigoPreImpreso(dto.getNumeroRadicado());
		logger.debug("Finaliza obtenerNumeroRadicado(Long, String)");
		return numeroRadicado;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#buscarSolicitud(
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitudDTO> buscarSolicitud(String numeroRadicado) {
		logger.debug("Inicia buscarSolicitud(String)");
		if (numeroRadicado == null) {
			logger.debug("Finaliza buscarSolicitud(String): Parámetros no válidos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
		}

		try {

			SolicitudAfiliacionPersona soliAfiliacionPersona;
			SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador;
			SolicitudNovedad solicitudNovedad;
			SolicitudAporte solicitudAporte;
			SolicitudCorreccionAporte solicitudCorreccionAporte;
			SolicitudDevolucionAporte solicitudDevolucionAporte;
			SolicitudAsociacionPersonaEntidadPagadora solicitudPagadora;
			SolicitudPreventiva solicitudPreventiva;
			SolicitudPostulacion solicitudPostulacion;
			SolicitudNovedadFovis solicitudNovedadFovis;
			SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis;
			SolicitudDesafiliacion solicitudDesafiliacion;
			SolicitudFiscalizacion solicitudFiscalizacion;
			SolicitudGestionCobroElectronico solicitudGestionCobroElectronico;
			SolicitudGestionCobroFisico solicitudGestionCobroFisico;
			SolicitudGestionCobroManual solicitudGestionCobroManual;
			SolicitudAsignacion solicitudAsignacion;
			SolicitudLegalizacionDesembolso solicitudLegalizacionDesembolso;
			SolicitudVerificacionFovis solicitudVerificacionFovis;
			SolicitudGestionCruce solicitudGestionCruce;
			SolicitudCierreRecaudo solicitudCierre;

			List<SolicitudDTO> solicitudes = (ArrayList<SolicitudDTO>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICTUD_POR_NUMERO_RADICADO)
					.setParameter("numeroRadicacion", numeroRadicado.concat("%")).getResultList();

			if (!solicitudes.isEmpty()) {
				logger.debug("Finaliza buscarSolicitud(String)");
				try {
					for (SolicitudDTO solicitudDTO : solicitudes) {
						switch (ObtenerTipoSolicitud
								.obtenerTipoSolicitudPorTipoTransaccion(solicitudDTO.getTipoTransaccion())) {
							case NOVEDAD_EMPLEADOR:
								solicitudNovedad = (SolicitudNovedad) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_NOVEDAD_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudNovedad.getEstadoSolicitud().toString());

							case NOVEDAD_PERSONA:
								solicitudNovedad = (SolicitudNovedad) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_NOVEDAD_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudNovedad.getEstadoSolicitud().toString());
								break;
							case EMPLEADOR:
								solicitudAfiliacionEmpleador = (SolicitudAfiliacionEmpleador) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_EMPLEADOR_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(
												solicitudAfiliacionEmpleador.getEstadoSolicitud().toString());
								break;
							case PERSONA:
								soliAfiliacionPersona = (SolicitudAfiliacionPersona) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_PERSONA_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(soliAfiliacionPersona.getEstadoSolicitud().toString());
								break;

							case APORTES:
								solicitudAporte = (SolicitudAporte) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_APORTES_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudAporte.getEstadoSolicitud().toString());
								break;
							case APORTES_CORRECCION:
								solicitudCorreccionAporte = (SolicitudCorreccionAporte) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_CORRECION_APORTE_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(solicitudCorreccionAporte.getEstadoSolicitud().toString());
								break;
							case APORTES_DEVOLUCION:
								solicitudDevolucionAporte = (SolicitudDevolucionAporte) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_DEVOLUCION_APORTE_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(solicitudDevolucionAporte.getEstadoSolicitud().toString());
								break;

							case ENTIDAD_PAGADORA:
								solicitudPagadora = (SolicitudAsociacionPersonaEntidadPagadora) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_ENTIDAD_PAGADORA_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudPagadora.getEstado().toString());
								break;

							case GESTION_PREVENTIVA:
								solicitudPreventiva = (SolicitudPreventiva) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_PREVENTIVA_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(
												solicitudPreventiva.getEstadoSolicitudPreventiva().toString());
								break;
							case POSTULACION_FOVIS:
								solicitudPostulacion = (SolicitudPostulacion) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_POSTULACION_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudPostulacion.getEstadoSolicitud().toString());
								break;
							case POSTULACION_FOVIS_VERIFICACION:
								solicitudVerificacionFovis = (SolicitudVerificacionFovis) entityManager
										.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_VERIFICACION_FOVIS)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(solicitudVerificacionFovis.getEstadoSolicitud().toString());
								break;
							case POSTULACION_FOVIS_GESTION_CRUCE:
								solicitudGestionCruce = (SolicitudGestionCruce) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICITUD_GESTION_CRUCE_FOVIS_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(
										solicitudGestionCruce.getEstadoSolicitudGestionCruce().toString());
								break;
							case NOVEDAD_FOVIS:
								solicitudNovedadFovis = (SolicitudNovedadFovis) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_NOVEDAD_FOVIS_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudNovedadFovis.getEstadoSolicitud().toString());
								break;
							case NOVEDAD_FOVIS_ANALISIS:
								solicitudAnalisisNovedadFovis = (SolicitudAnalisisNovedadFovis) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICITUD_NOVEDAD_FOVIS_ANALISIS_NOVEDAD_PERSONA)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(
												solicitudAnalisisNovedadFovis.getEstadoSolicitud().toString());
								break;
							case LEGALIZACION_FOVIS:
								solicitudLegalizacionDesembolso = (SolicitudLegalizacionDesembolso) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_LEGALIZACION_DESEMBOLSO_FOVIS_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(
										solicitudLegalizacionDesembolso.getEstadoSolicitud().toString());
								break;
							case ASIGNACION_FOVIS:
								solicitudAsignacion = (SolicitudAsignacion) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_ASIGNACION_FOVIS_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(
												solicitudAsignacion.getEstadoSolicitudAsignacion().toString());
								break;
							case DESAFILIACION_APORTANTES:
								solicitudDesafiliacion = (SolicitudDesafiliacion) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_DESAFILIACION_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudDesafiliacion.getEstadoSolicitud().toString());
								break;

							case GESTION_COBRO_ELECTRONICO:
								solicitudGestionCobroElectronico = (SolicitudGestionCobroElectronico) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_GESTION_COBRO_ELECTRONICO_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(solicitudGestionCobroElectronico.getEstado().toString());
								break;

							case GESTION_COBRO_FISICO:
								solicitudGestionCobroFisico = (SolicitudGestionCobroFisico) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_GESTION_COBRO_FISICO_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudGestionCobroFisico.getEstado().toString());
								break;

							case GESTION_COBRO_MANUAL:
								solicitudGestionCobroManual = (SolicitudGestionCobroManual) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICTUD_GESTION_COBRO_MANUAL_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(
												solicitudGestionCobroManual.getEstadoSolicitud().toString());
								break;
							case FISCALIZACION:
								solicitudFiscalizacion = (SolicitudFiscalizacion) entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_SOLICITUD_FISCALIZACION_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO
										.setEstadoSolicitud(solicitudFiscalizacion.getEstadoFiscalizacion().toString());
								break;

							case CIERRE_RECAUDO:
								solicitudCierre = (SolicitudCierreRecaudo) entityManager
										.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICTUD_CIERRE_POR_ID_SOLICITUD)
										.setParameter("idSolicitudGlobal", solicitudDTO.getIdSolicitud())
										.getSingleResult();
								solicitudDTO.setEstadoSolicitud(solicitudCierre.getEstadoSolicitud().toString());
								break;

							default: // entidad pagadora
								logger.error(
										"Finaliza getInstance(TipoTransaccionEnum): no se encuentra una implementación para la fabrica solicitada");
								throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
						}
					}
				} catch (NoResultException e) {
					logger.error("Finaliza buscarSolicitud(String): Error inesperado", e);
					logger.debug(
							"Finaliza buscarSolicitud(String): Error inesperado no se encontro solicitud de afiliación asociada");
					throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO, e);
				}

				return solicitudes;
			} else {
				logger.debug(
						"Finaliza buscarSolicitud(String): No se encuentra información para los filtros de búsqueda");
				return null;
			}
		} catch (Exception e) {
			logger.error("Finaliza buscarSolicitud(String): Error inesperado", e);
			logger.debug("Finaliza buscarSolicitud(String): Error inesperado");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#actualizarEstadoDocumentacionAfiliacion(java.lang.Long,
	 *      com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum)
	 */
	@Override
	public void actualizarEstadoDocumentacionAfiliacion(Long idSolicitudGlobal, EstadoDocumentacionEnum estado) {
		Solicitud solicitudUpdate = entityManager.getReference(Solicitud.class, idSolicitudGlobal);
		solicitudUpdate.setEstadoDocumentacion(estado);
		entityManager.merge(solicitudUpdate);
		// entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_DOCUMENTACION_AFILIACION_EMPLEADOR)
		// .setParameter("idSolicitud",
		// idSolicitudGlobal).setParameter("nuevoEstado",
		// estado).executeUpdate();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#crearProductoNoConforme()
	 */
	@Override
	public List<Long> crearProductosNoConforme(Long idSolicitudAfiliacion,
			List<ProductoNoConformeDTO> productosNoConformeDTO) {

		List<Long> idsProductosNoConforme = new ArrayList<>();
		Solicitud solicitud = consultarSolicitud(idSolicitudAfiliacion);
		if (solicitud != null) {
			short consecutivo = 1;
			for (ProductoNoConformeDTO productoNoConformeDTO : productosNoConformeDTO) {
				productoNoConformeDTO.setIdSolicitud(solicitud.getIdSolicitud());
				productoNoConformeDTO.setNumeroConsecutivo(consecutivo);
				ProductoNoConforme productoNoConforme = productoNoConformeDTO.obtenerProductoNoConforme();
				entityManager.persist(productoNoConforme);
				idsProductosNoConforme.add(productoNoConforme.getIdProductoNoConformeNoResuelto());
				consecutivo++;
			}
		} else {
			String errorMsg = interpolate("No es posible localizar la solicitud con id {0}", idSolicitudAfiliacion);
			logger.error(errorMsg);
			throw new TechnicalException(errorMsg);
		}
		return idsProductosNoConforme;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#consultarSolicitudAfiliacionEmpleador(java.lang.Long)
	 */
	private Solicitud consultarSolicitud(Long idSolicitud) {
		// Verifica que no exista una solicitud de escalamiento ya asociada.
		Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICTUD);
		q.setParameter("idSolicitud", idSolicitud);
		List<Solicitud> list = q.getResultList();
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#actualizarProductoNoConforme()
	 */
	@Override
	public void actualizarProductoNoConforme(Long idSolicitudAfiliacion, Long idProductoNoConforme,
			ProductoNoConformeDTO productoNoConformeDTO) {
		Solicitud solicitud = consultarSolicitud(idSolicitudAfiliacion);
		if (solicitud != null) {
			productoNoConformeDTO.setIdSolicitud(solicitud.getIdSolicitud());
			productoNoConformeDTO.setIdProductoNoConformeNoResuelto(idProductoNoConforme);
			entityManager.merge(productoNoConformeDTO.obtenerProductoNoConforme());
		} else {
			// en caso se no estar en el sistema se alerta con un mensaje
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#actualizarProductoNoConforme()
	 */
	@Override
	public void actualizarProductoNoConforme(Long idSolicitudAfiliacion,
			List<ProductoNoConformeDTO> listProductoNoConformeDTO) {
		Solicitud solicitud = consultarSolicitud(idSolicitudAfiliacion);
		if (solicitud != null) {
			for (ProductoNoConformeDTO productoNoConformeDTO : listProductoNoConformeDTO) {
				try {
					productoNoConformeDTO.setIdSolicitud(solicitud.getIdSolicitud());
					entityManager.merge(productoNoConformeDTO.obtenerProductoNoConforme());
				} catch (Exception e) {
					throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
				}
			}
		} else {
			// en caso se no estar en el sistema se alerta con un mensaje
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#consultarProductosNoConforme(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@Override
	public List<ProductoNoConformeDTO> consultarProductosNoConforme(Long idSolicitudAfiliacion, Boolean resuelto) {

		List<ProductoNoConforme> listaResult;
		if (resuelto != null) {

			Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_NO_CONFORME_ESTADO);
			q.setParameter("idSolictud", idSolicitudAfiliacion);
			if (resuelto == false) {
				q.setParameter("resuelto", TipoProductoNoConformeEnum.NO_RESUELTO);
			} else {
				q.setParameter("resuelto", TipoProductoNoConformeEnum.RESUELTO);
			}

			listaResult = q.getResultList();
		} else {
			Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_NO_CONFORME);
			q.setParameter("idSolictud", idSolicitudAfiliacion);
			listaResult = q.getResultList();
		}

		if (listaResult.isEmpty()) {
			// en caso se no estar en el sistema se alerta con un mensaje
			return Collections.emptyList();
		}

		List<ProductoNoConformeDTO> resultado = new ArrayList<>();

		for (ProductoNoConforme productoNoConforme : listaResult) {
			ProductoNoConformeDTO productoNoConformeDTO = new ProductoNoConformeDTO(productoNoConforme);
			if (productoNoConforme.getIdBeneficiario() != null) {
				Beneficiario beneficiario = entityManager.find(Beneficiario.class,
						productoNoConforme.getIdBeneficiario());
				if (beneficiario != null) {
					productoNoConformeDTO.setTipoBeneficiario(beneficiario.getTipoBeneficiario());
					productoNoConformeDTO.setIdGrupoFamiliar(beneficiario.getGrupoFamiliar().getIdGrupoFamiliar());
					productoNoConformeDTO.setNumeroGrupoFamiliar(beneficiario.getGrupoFamiliar().getNumero());
				}
			}
			resultado.add(productoNoConformeDTO);
		}
		return resultado;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.service.AfiliacionEmpleadoresService#eliminarProductoNoConforme(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public void eliminarProductoNoConforme(Long idSolicitudAfiliacion, Long idProductoNoConforme) {
		logger.debug("Inicia eliminarProductoNoConforme(Long,Long) con id de PNC : " + idProductoNoConforme);
		try {
			ProductoNoConforme producto = entityManager.getReference(ProductoNoConforme.class, idProductoNoConforme);
			entityManager.remove(producto);
		} catch (EntityNotFoundException e) {
			logger.warn("eliminarProductoNoConforme(Long,Long): Está intentando eliminar un PNC que no existe", e);
		}
		logger.debug("Finaliza eliminarProductoNoConforme(Long,Long)");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#guardarInstanciaProceso(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public void guardarInstanciaProceso(Long idSolicitudGlobal, String idInstanciaProceso) {
		logger.debug("Inicia guardarInstanciaProceso(Long,Long)");
		Solicitud solicitud = entityManager.find(Solicitud.class, idSolicitudGlobal);
		if (solicitud == null) {
			logger.error("Finaliza guardarInstanciaProceso(Long,Long)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
		solicitud.setIdInstanciaProceso(idInstanciaProceso);
		entityManager.merge(solicitud);
		logger.debug("Finaliza guardarInstanciaProceso(Long,Long)");
	}

	@Override
	public String generarIdentificadorCargaMultiple(UserDTO userDTO) {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * registrarPersonaEnListaEspecialRevision(com.asopagos.afiliaciones.dto.
	 * ListaEspecialRevisionDTO)
	 */
	@Override
	public ListaEspecialRevision registrarPersonaEnListaEspecialRevision(
			ListaEspecialRevisionDTO listaEspecialRevisionDTO,
			@Context UserDTO userDTO) {
		logger.debug("Inicia registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO)");
		if (listaEspecialRevisionDTO != null) {
			try {
				ListaEspecialRevision listaEspecialRevision = new ListaEspecialRevision();
				listaEspecialRevision.setTipoIdentificacion(listaEspecialRevisionDTO.getTipoIdentificacion());
				listaEspecialRevision.setNumeroIdentificacion(listaEspecialRevisionDTO.getNumeroIdentificacion());
				listaEspecialRevision.setNombreEmpleador(listaEspecialRevisionDTO.getNombreEmpleador());
				listaEspecialRevision.setRazonInclusion(listaEspecialRevisionDTO.getRazonInclusion());
				listaEspecialRevision.setComentario(listaEspecialRevisionDTO.getComentario());
				listaEspecialRevision.setCajaCompensacion(listaEspecialRevisionDTO.getCajaCompensacion());
				listaEspecialRevision.setCajaCompensacionCodigo(listaEspecialRevisionDTO.getCajaCompensacionCodigo());
				if (listaEspecialRevisionDTO.getDigitoVerificacion() != null) {
					listaEspecialRevision.setDigitoVerificacion(listaEspecialRevisionDTO.getDigitoVerificacion());
				}
				listaEspecialRevision.setEstado(listaEspecialRevisionDTO.getEstado());
				listaEspecialRevision.setFechaInicioInclusion(new Date());

				entityManager.persist(listaEspecialRevision);

				logger.debug("Finaliza registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO)");
				return listaEspecialRevision;
			} catch (NoResultException e) {
				logger.debug("Finaliza registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO)");
				throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
			} catch (Exception e) {
				logger.debug("Finaliza registrarPersonaEnListaEspecialRevision(ListaEspecialRevisionDTO)");
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
		} else {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * consultarListaEspecialRevision(com.asopagos.enumeraciones.personas.
	 * TipoIdentificacionEnum, java.lang.Long, java.lang.Byte, java.lang.Long,
	 * java.lang.Long, java.lang.String)
	 */
	@Override
	public List<ListaEspecialRevisionDTO> consultarListaEspecialRevision(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Byte digitoVerificacion, Long fechaInicio, Long fechaFin,
			String nombreEmpleador) {
		logger.debug("Inicia consultarListaEspecialRevision(TipoIdentificacionEnum, Long, Long, Long, Long,String)");
		List<ListaEspecialRevision> lstEspecialRevision = consultarListaRevision(tipoIdentificacion,
				numeroIdentificacion, digitoVerificacion, fechaInicio, fechaFin, nombreEmpleador);
		List<ListaEspecialRevisionDTO> lstEspecialRevisionDTO = new ArrayList<>();
		if (lstEspecialRevision != null && !lstEspecialRevision.isEmpty()) {
			for (ListaEspecialRevision listaEspecialRevision : lstEspecialRevision) {
				ListaEspecialRevisionDTO listaEspecialDTO = ListaEspecialRevisionDTO
						.convertListaEspecialRevisionDTO(listaEspecialRevision);
				lstEspecialRevisionDTO.add(listaEspecialDTO);
			}
			logger.debug(
					"Finaliza consultarListaEspecialRevision(TipoIdentificacionEnum, Long, Long, Long, Long,String)");
			return lstEspecialRevisionDTO;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * cambiarEstadoRegistroLista(com.asopagos.enumeraciones.personas.
	 * TipoIdentificacionEnum, java.lang.Long, java.lang.Byte,
	 * com.asopagos.afiliaciones.dto.ActualizacionEstadoListaEspecialDTO,
	 * com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void cambiarEstadoRegistroLista(ListaEspecialRevision lista,
			UserDTO userDTO) {
		logger.debug("Inicia cambiarEstadoRegistroLista(ActualizacionEstadoListaEspecialDTO)");
		if (lista.getIdListaEspecialRevision() == null) {

			List<ListaEspecialRevision> listadoEspecialRevision = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_REGISTRO_LISTA_ESPECIAL_REVISION,
							ListaEspecialRevision.class)
					.setParameter("tipoIdentificacion", lista.getTipoIdentificacion())
					.setParameter("numeroIdentificacion", lista.getNumeroIdentificacion())
					.getResultList();
			if (listadoEspecialRevision != null && !listadoEspecialRevision.isEmpty()) {

				ListaEspecialRevision listaEspecialRevision = listadoEspecialRevision.get(0);

				listaEspecialRevision.setEstado(lista.getEstado());
				listaEspecialRevision.setComentario(lista.getComentario());
				listaEspecialRevision.setCajaCompensacionCodigo(lista.getCajaCompensacionCodigo());
				if (lista.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO)) {
					listaEspecialRevision.setFechaInicioInclusion(new Date());
					listaEspecialRevision.setFechaFinInclusion(null);
				} else {
					listaEspecialRevision.setFechaFinInclusion(new Date());
				}
				if (userDTO.getSedeCajaCompensacion() != null) {
					listaEspecialRevision.setCajaCompensacion(Integer.valueOf(
							CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString()));
				}
				entityManager.merge(listaEspecialRevision);
				logger.debug("Finaliza cambiarEstadoRegistroLista(ActualizacionEstadoListaEspecialDTO)");
			} else {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
			}
		} else {
			try {
				entityManager.merge(lista);
			} catch (Exception e) {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}

		}
	}

	/**
	 * Metodo encargado de consultar de manera dinamica la ListaEspecialRevision
	 * 
	 * @param tipoIdentificacion,
	 *                              tipo de identificacion
	 * @param numeroIdentificacion,
	 *                              numero de identificacion
	 * @param digitoVerificacion,
	 *                              digito verificacion
	 * @param fechaInicio,
	 *                              fecha inicio
	 * @param fechaFin,
	 *                              fecha fin
	 * @param nombreEmpleador,
	 *                              nombre del empleador
	 * @return retorna la lista de ListaEspecialRevision
	 */
	private List<ListaEspecialRevision> consultarListaRevision(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Byte digitoVerificacion, Long fechaInicio, Long fechaFin,
			String nombreEmpleador) {
		Map<String, String> fields = new HashMap<>();
		Map<String, Object> values = new HashMap<>();
		if (tipoIdentificacion != null && !tipoIdentificacion.equals("")) {
			fields.put("tipoIdentificacion", ConsultasDinamicasConstants.IGUAL);
			values.put("tipoIdentificacion", tipoIdentificacion);
		}
		if (numeroIdentificacion != null && !numeroIdentificacion.equals("")) {
			fields.put("numeroIdentificacion", ConsultasDinamicasConstants.IGUAL);
			values.put("numeroIdentificacion", numeroIdentificacion);
		}
		if (digitoVerificacion != null && !digitoVerificacion.equals("")) {
			fields.put("digitoVerificacion", ConsultasDinamicasConstants.IGUAL);
			values.put("digitoVerificacion", digitoVerificacion);
		}
		if (fechaInicio != null && !fechaInicio.equals("")) {
			fields.put("fechaInicioInclusion", ConsultasDinamicasConstants.MAYOR_IGUAL);
			values.put("fechaInicioInclusion", new Date(fechaInicio));
		}
		if (fechaFin != null && !fechaFin.equals("")) {
			fields.put("fechaFinInclusion", ConsultasDinamicasConstants.MENOR_IGUAL);
			values.put("fechaFinInclusion", new Date(fechaFin));
		}
		if (nombreEmpleador != null && !nombreEmpleador.equals("")) {
			fields.put("nombreEmpleador", ConsultasDinamicasConstants.LIKE);
			values.put("nombreEmpleador", nombreEmpleador);
		}
		List<ListaEspecialRevision> lstEspecialRevision = JPAUtils.consultaEntidad(entityManager,
				ListaEspecialRevision.class, fields, values);
		return lstEspecialRevision;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#consultarUltimaAfiliacionPersona(java.lang.Long,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	public SolicitudAfiliacionPersonaDTO consultarUltimaAfiliacionPersona(
			@QueryParam("idRolAfiliado") Long idRolAfiliado, @Context UserDTO userDTO) {
		SolicitudAfiliacionPersonaDTO solDTO = new SolicitudAfiliacionPersonaDTO();
		logger.debug("Inicia consultarUltimaAfiliacionPersona(Long)");
		List<ResultadoProcesoEnum> resultadoProceso = new ArrayList<ResultadoProcesoEnum>();
		resultadoProceso.add(ResultadoProcesoEnum.APROBADA);
		Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ULTIMA_SOLICITUD_AFILIACION_PERSONA);
		q.setParameter("idRolAfiliado", idRolAfiliado).setParameter("resultadoProceso", resultadoProceso);
		q.setMaxResults(0);
		List<SolicitudAfiliacionPersona> listaResult = q.getResultList();
		if (listaResult.isEmpty()) {
			logger.debug(
					"Finaliza consultarUltimaAfiliacionPersona(Long) : No se encontró una solicitud para el rolAfiliado");
			return null;
		}
		// se asume que llega un unico dato, dado que se restringe la consulta
		// para que solo retorne un unico registro.
		SolicitudAfiliacionPersona sol = listaResult.get(0);
		solDTO.setClasificacion(sol.getSolicitudGlobal().getClasificacion());
		solDTO.setIdSolicitudGlobal(sol.getSolicitudGlobal().getIdSolicitud());
		solDTO.setIdSolicitudAfiliacionPersona(sol.getIdSolicitudAfiliacionPersona());
		solDTO.setMetodoEnvio(sol.getSolicitudGlobal().getMetodoEnvio());
		solDTO.setEstadoSolicitud(sol.getEstadoSolicitud());
		solDTO.setFechaRadicacion(sol.getSolicitudGlobal().getFechaRadicacion());
		solDTO.setIdInstanciaProceso(sol.getSolicitudGlobal().getIdInstanciaProceso());
		solDTO.setNumeroRadicacion(sol.getSolicitudGlobal().getNumeroRadicacion());
		solDTO.setTipoTransaccion(sol.getSolicitudGlobal().getTipoTransaccion());
		logger.debug("Finaliza consultarUltimaAfiliacionPersona(Long)");
		return solDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * consultarUltimaSolicitudAfiliacion(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SolicitudAfiliacionPersonaModeloDTO consultarUltimaSolicitudAfiliacion(Long idRolAfiliado) {
		logger.debug("Inicia operación consultarUltimaSolicitudAfiliacion(Long)");
		SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacionPersonaModeloDTO = new SolicitudAfiliacionPersonaModeloDTO();
		try {
			List<SolicitudAfiliacionPersona> solicitudesAfiliacion = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_AFILIACION_PERSONA)
					.setParameter("idRolAfiliado", idRolAfiliado).setMaxResults(0).getResultList();
			if (solicitudesAfiliacion == null || solicitudesAfiliacion.isEmpty()) {
				logger.debug(
						"Finaliza consultarUltimaSolicitudAfiliacion(Long) : No se encontró una solicitud para el rolAfiliado");
				return solicitudAfiliacionPersonaModeloDTO;
			}
			solicitudAfiliacionPersonaModeloDTO.convertToDTO(solicitudesAfiliacion.get(0));
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarUltimaSolicitudAfiliacion(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación consultarUltimaSolicitudAfiliacion(Long)");
		return solicitudAfiliacionPersonaModeloDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * actualizarSolicitudAfiliacionPersona(com.asopagos.dto.modelo.
	 * SolicitudAfiliacionPersonaModeloDTO)
	 */
	public void actualizarSolicitudAfiPersona(SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacionDTO) {
		logger.debug("Inicia operación actualizarSolicitudAfiliacionPersona(SolicitudAfiliacionPersonaModeloDTO)");
		try {
			/* Se actualiza la solicitud de Afiliación como llega. */
			SolicitudAfiliacionPersona solicitudAfiliacionPersona = solicitudAfiliacionDTO.convertToEntity();
			solicitudAfiliacionPersona = entityManager.merge(solicitudAfiliacionPersona);

			/* Se actualiza la solicitud global asociada */
			Solicitud solicitud = solicitudAfiliacionDTO.convertToSolicitudEntity();
			entityManager.merge(solicitud);

		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el método actualizarSolicitudAfiliacionPersona(SolicitudAfiliacionPersonaModeloDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación actualizarSolicitudAfiliacionPersona(SolicitudAfiliacionPersonaModeloDTO)");
	}

	@Override
	public IntentoAfiliacionDTO consultarIntentoAfiliacion(Long idSolicitud) {
		logger.debug("Inicia operación consultarIntentoAfiliacion(Long)");
		IntentoAfiliacionDTO intentoAfiliacionDTO = new IntentoAfiliacionDTO();
		IntentoAfiliacion intentoAfiliacion = new IntentoAfiliacion();

		try {
			intentoAfiliacion = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_INTENTO_AFILIACION_POR_ID_SOLICITUD,
							IntentoAfiliacion.class)
					.setParameter("idSolicitudGlobal", idSolicitud).getSingleResult();
			intentoAfiliacion.setRequsitos(new ArrayList<>());
		} catch (NoResultException e) {
			logger.error("No se encontró el intento de afiliacion", e);
			return null;
		}

		try {
			intentoAfiliacionDTO = intentoAfiliacionDTO.convertToDTO(intentoAfiliacion);
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarIntentoAfiliacion(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación consultarIntentoAfiliacion(Long)");
		return intentoAfiliacionDTO;
	}

	@Override
	public Short buscarMunicipio(String codigoMunicipio) {
		logger.info("Inicia operación buscarMunicipio(String)");
		try {
			Short idMunicipio = (Short) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_MUNICIPIO_UBICACION_PERSONA)
					.setParameter("codigoMunicipio", codigoMunicipio).getSingleResult();
			logger.info("Finaliza operación buscarMunicipio(String)");
			return idMunicipio;

		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método buscarMunicipio(String)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * consultarIdSolicitudGlobal(java.lang.Long)
	 */
	@Override
	public Long consultarIdSolicitudGlobal(Long idRolAfiliado) {
		logger.debug("Inicia operación consultarIdSolicitudGlobal(Long)");
		try {
			logger.debug("Finaliza operación consultarIdSolicitudGlobal(Long)");
			return (Long) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ID_SOLICITUD_GLOBAL)
					.setParameter("idRolAfiliado", idRolAfiliado).getSingleResult();

		} catch (Exception e) {
			logger.debug("Finaliza operación consultarIdSolicitudGlobal(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.solicitudes.service.SolicitudesService#consultarUltimaClasificacionPersona(com.asopagos.enumeraciones.personas.
	 *      TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	public ClasificacionEnum consultarUltimaClasificacionPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(
				"Inicio consultarClasificacionAportante(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)");
		try {
			Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CLASIFICACION_AFILIACION_PERSONA);
			q.setParameter("tipoIdentificacion", tipoIdentificacion);
			q.setParameter("numeroIdentificacion", numeroIdentificacion);
			q.setMaxResults(0);
			List<ClasificacionEnum> clasificaciones = q.getResultList();
			logger.debug(
					"Finaliza consultarClasificacionAportante(TipoIdentificacionEnum, String,TipoSolicitanteMovimientoAporteEnum)");
			return !clasificaciones.isEmpty() ? clasificaciones.get(0) : null;
		} catch (NoResultException ex) {
			logger.debug(
					"Finaliza consultarClasificacionAportante(TipoIdentificacionEnum, String,TipoSolicitanteMovimientoAporteEnum): sin resultados");
			return null;
		} catch (Exception e) {
			logger.debug(
					"Finaliza consultarClasificacionAportante(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum) error",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliaciones.service.AfiliacionesService#radicarSolicitudes(
	 * java.util.List, java.lang.String, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public Map<String, String> radicarListaSolicitudes(String sccID, List<Long> listSolicitudes, UserDTO userDTO) {

		logger.info("**__**inicia radicarListaSolicitudes en afiliaciones sccID: " + sccID);
		/*
		 * String firma =
		 * "radicarListaSolicitudes(List<Long>, String, UserDTO):Map<Long, String>";
		 * 
		 * try {
		 * logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		 * Map<String, String> mapResult = new HashMap<>();
		 * // Se consulta la informacion de las solicitudes enviadas
		 * List<Solicitud> listaSolicitud = entityManager
		 * .createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_IDS,
		 * Solicitud.class)
		 * .setParameter("idsSolicitud", listSolicitudes).getResultList();
		 * if (listaSolicitud == null || listaSolicitud.isEmpty()) {
		 * logger.debug(ConstantesComunes.FIN_LOGGER + firma + "- No existe solicitud");
		 * throw new
		 * ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO)
		 * ;
		 * }
		 * for (Solicitud solicitud : listaSolicitud) {
		 * // Se verifica que la solicitud no se haya radicado previamente
		 * if (solicitud.getNumeroRadicacion() != null) {
		 * logger.debug(ConstantesComunes.FIN_LOGGER + firma +
		 * "- La solicitud ya fué radicada previamente");
		 * throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.
		 * ERROR_RECURSO_YA_ESTA_REGISTRADO);
		 * }
		 * // Se calcula el numero de radicado
		 * NumeroRadicadoCorrespondenciaDTO numeroRadicadoDTO =
		 * obtenerNumeroRadicadoCorrespondencia(
		 * TipoEtiquetaEnum.NUMERO_RADICADO, 1, userDTO);
		 * Calendar fecha = Calendar.getInstance();
		 * String numeroRadicado = numeroRadicadoDTO.nextValue();
		 * 
		 * // Se actualiza la informacion de la solicitud
		 * entityManager.merge(solicitud);
		 * solicitud.setNumeroRadicacion(numeroRadicado);
		 * solicitud.setFechaRadicacion(fecha.getTime());
		 * // Se marca el codigo como asignado
		 * //asignarCodigoPreImpreso(numeroRadicado);
		 * // Se agrega a la respuesta del servicio
		 * mapResult.put(solicitud.getIdSolicitud().toString(),
		 * solicitud.getNumeroRadicacion());
		 * }
		 * return mapResult;
		 * } catch (Exception e) {
		 * logger.error(ConstantesComunes.FIN_LOGGER + firma, e);
		 * throw new
		 * TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		 * }
		 */
		RadicarListaSolicitudesRutine r = new RadicarListaSolicitudesRutine();
		logger.info("**__**Fin1 radicarListaSolicitudes en afiliaciones sccID: " + sccID);
		return r.radicarListaSolicitudes(listSolicitudes, sccID, userDTO, entityManager);
		// logger.info("**__**Fin2 radicarListaSolicitudes en afiliaciones sccID:
		// "+sccID );

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * consultarIntentosAfiliacionEmpleador(java.lang.Long)
	 */
	@Override
	public List<IntentoAfiliacionEmpleador360DTO> consultarIntentosAfiliacionEmpleador(String numeroIdentificacion,
			TipoIdentificacionEnum tipoIdentificacion) {
		String firma = "consultarIntentosAfiliacionEmpleador(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		List<IntentoAfiliacionEmpleador360DTO> intentosAfiliacion = new ArrayList<>();
		List<String> tipoTransaccion = new ArrayList<>();
		tipoTransaccion.add(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION.name());
		tipoTransaccion.add(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION.name());

		try {
			List<Object[]> objects = (List<Object[]>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INTENTOS_AFILIACION_EMPLEADOR)
					.setParameter("tipoIdentificacion", tipoIdentificacion.name())
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoTransaccion", tipoTransaccion)
					.getResultList();

			if (objects != null && !objects.isEmpty()) {
				for (Object[] obj : objects) {
					IntentoAfiliacionEmpleador360DTO intento = new IntentoAfiliacionEmpleador360DTO();

					intento.setFechaCreacion(obj[0] != null ? obj[0].toString() : null);
					intento.setNumeroRadicacion(obj[1] != null ? obj[1].toString() : null);
					intento.setCanalRecepcion(obj[2] != null ? obj[2].toString() : null);
					intento.setCausaIntentoFallido(obj[3] != null ? obj[3].toString() : null);
					intento.setIdentificaArchivoComunicado(obj[4] != null ? obj[4].toString() : null);
					intento.setIdSolicitud(obj[5] != null ? Long.valueOf(obj[5].toString()) : null);
					intento.setIdDatoTemporal(obj[6] != null ? Long.valueOf(obj[6].toString()) : null);
					intento.setEstadoSolcitud(obj[7] != null ? obj[7].toString() : null);

					intentosAfiliacion.add(intento);
				}
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firma);
			return intentosAfiliacion;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	@Override
	public List<SolicitudDTO> buscarSolicitudRecepcionDocumento(String numeroRadicado) {
		try {
			logger.info("Inicia buscarSolicitudRecepcionDocumento(String)");
			List<SolicitudDTO> solicitudes = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_POR_NRO_RADICADO_ESTADO_DOCUMENTO,
							SolicitudDTO.class)
					.setParameter("estadoDocumento", EstadoDocumentacionEnum.ENVIADA_AL_BACK)
					.setParameter("metodoEnvio", FormatoEntregaDocumentoEnum.FISICO)
					.setParameter("numeroRadicacion", numeroRadicado.concat("%")).getResultList();
			logger.info("Finaliza buscarSolicitudRecepcionDocumento(String)");
			return solicitudes;
		} catch (Exception e) {
			logger.debug("Finaliza buscarSolicitudRecepcionDocumento(String): Error inesperado");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	@Override
	public List<HistoricoAfiliacion360DTO> consultarHistoricoAfiliacionDependiente(
			TipoIdentificacionEnum tipoIdAfiliado,
			String numeroIdAfiliado, Long idEmpleador) {

		return consultarHistoricoAfiliacion(tipoIdAfiliado, numeroIdAfiliado, idEmpleador,
				TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
	}

	@Override
	public List<HistoricoAfiliacion360DTO> consultarHistoricoAfiliacionIndependiente(
			TipoIdentificacionEnum tipoIdAfiliado,
			String numeroIdAfiliado) {

		return consultarHistoricoAfiliacion(tipoIdAfiliado, numeroIdAfiliado, null,
				TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
	}

	@Override
	public List<HistoricoAfiliacion360DTO> consultarHistoricoAfiliacionPensionado(TipoIdentificacionEnum tipoIdAfiliado,
			String numeroIdAfiliado) {

		return consultarHistoricoAfiliacion(tipoIdAfiliado, numeroIdAfiliado, null, TipoAfiliadoEnum.PENSIONADO);
	}

	private List<HistoricoAfiliacion360DTO> consultarHistoricoAfiliacion(TipoIdentificacionEnum tipoIdAfiliado,
			String numeroIdAfiliado, Long idEmpleador, TipoAfiliadoEnum tipoAfiliado) {
		List<HistoricoAfiliacion360DTO> salida = new ArrayList<>();

		try {
			List<Object[]> histoAfi = new ArrayList<>();
			if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
				histoAfi = (List<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFI_DEP)
						.setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
						.setParameter("numeroIdAfiliado", numeroIdAfiliado)
						.setParameter("idEmpleador", idEmpleador)
						.getResultList();

			} else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliado)) {
				histoAfi = (List<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFI_INDEP)
						.setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
						.setParameter("numeroIdAfiliado", numeroIdAfiliado)
						.getResultList();

			} else if (TipoAfiliadoEnum.PENSIONADO.equals(tipoAfiliado)) {
				histoAfi = (List<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFI_PEN)
						.setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
						.setParameter("numeroIdAfiliado", numeroIdAfiliado)
						.getResultList();

			}

			if (histoAfi != null && !histoAfi.isEmpty()) {
				Boolean addRegistry = Boolean.TRUE;
				for (int i = 0; i < histoAfi.size(); i++) {
					HistoricoAfiliacion360DTO hafi = new HistoricoAfiliacion360DTO();
					hafi.setIdInstanciaProceso(histoAfi.get(i)[0] != null ? histoAfi.get(i)[0].toString() : null);
					hafi.setNumeroRadicado(histoAfi.get(i)[1] != null ? histoAfi.get(i)[1].toString() : null);
					hafi.setIdSolicitud(
							histoAfi.get(i)[2] != null ? Long.valueOf(histoAfi.get(i)[2].toString()) : null);
					if (histoAfi.get(i)[4] != null && histoAfi.get(i)[3] == null && i >= 1) {
						hafi.setFechaIngreso(histoAfi.get(i - 1)[3] != null ? histoAfi.get(i - 1)[3].toString() : null);
					} else {
						hafi.setFechaIngreso(histoAfi.get(i)[3] != null ? histoAfi.get(i)[3].toString() : null);
					}
					hafi.setFechaRetiro(histoAfi.get(i)[4] != null ? histoAfi.get(i)[4].toString() : null);
					hafi.setCanalAfiliacion(
							histoAfi.get(i)[5] != null ? CanalRecepcionEnum.valueOf(histoAfi.get(i)[5].toString())
									: null);

					if (histoAfi.get(i)[4] != null && histoAfi.get(i)[6] == null
							&& CanalRecepcionEnum.PILA.equals(hafi.getCanalAfiliacion())) {
						hafi.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.RETIRO_VOLUNTARIO);
					} else {
						hafi.setMotivoDesafiliacion(histoAfi.get(i)[6] != null ? (histoAfi.get(i)[4] != null
								? MotivoDesafiliacionAfiliadoEnum.valueOf(histoAfi.get(i)[6].toString())
								: null) : null);
					}
					// if (hafi.getFechaRetiro() != null && CanalRecepcionEnum.PILA.equals(hafi.getCanalAfiliacion())
					// 		&& i >= 1 && histoAfi.get(i - 1)[4] != null) {
					// 	HistoricoAfiliacion360DTO hafi2 = new HistoricoAfiliacion360DTO();
					// 	hafi2.setFechaIngreso(hafi.getFechaIngreso());
					// 	hafi2.setCanalAfiliacion(hafi.getCanalAfiliacion());
					// 	salida.add(hafi2);
					// 	addRegistry = Boolean.FALSE;
					// }
					/// GLPI 58755 correcccion
					if (!CanalRecepcionEnum.NOVEDAD_SUS_PATR.equals(hafi.getCanalAfiliacion())
						&& addRegistry) {
						salida.add(hafi);
					}
					addRegistry = Boolean.TRUE;
				}
			}

			List<HistoricoAfiliacion360DTO> newList = salida.stream()
					.distinct()
					.collect(Collectors.toList());

			return newList;
		} catch (Exception e) {
			logger.debug("Finaliza buscarSolicitudRecepcionDocumento(String): Error inesperado");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	@Override
	public ListaEspecialRevision consultarRegistroListaEspecialRevision(
			ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO,
			UserDTO userDTO) {
		String firmaServicio = "consultarRegistroListaEspecialRevision(ActualizacionEstadoListaEspecialDTO, UserDTO)";
		try {
			logger.debug("Inicia " + firmaServicio);
			logger.info("ListaEspecialRevision 1");
			ListaEspecialRevision listaEspecialRevision = entityManager.find(ListaEspecialRevision.class,
					actualizacionEstadoListaEspecialDTO.getIdListaEspecialRevision());
			logger.info("ListaEspecialRevision 2");
			logger.debug("Finaliza " + firmaServicio);
			return listaEspecialRevision;
		} catch (Exception e) {
			logger.debug("Finaliza " + firmaServicio + ": Error inesperado");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * obtenerNumeroRadicadoCorrespondencia(com.asopagos.enumeraciones.core.
	 * TipoEtiquetaEnum, java.lang.Integer, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicadoCorrespondencia(TipoEtiquetaEnum tipoEtiqueta,
			Integer cantidad, UserDTO userDTO) {
		String firmaServicio = "consultarRegistroListaEspecialRevision(TipoEtiquetaEnum, Integer, UserDTO)";
		logger.debug("Inicia " + firmaServicio);

		NumeroRadicadoUtil util = new NumeroRadicadoUtil();
		NumeroRadicadoCorrespondenciaDTO num = util.obtenerNumeroRadicadoCorrespondencia(entityManager, tipoEtiqueta,
				cantidad, userDTO);
		logger.debug("Finaliza " + firmaServicio);
		return num;
	}

	/**
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#asociarComunicadoIntentoAfiliacion(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public void asociarComunicadoIntentoAfiliacion(Long idIntentoAfiliacion, Long idComunicado) {
		String firmaServicio = "AfiliacionesService.asociarComunicadoIntentoAfiliacion(Long, Long)";
		logger.debug("Inicia " + firmaServicio);

		IntentoAfiliacion intentoAfiliacion = new IntentoAfiliacion();
		try {
			intentoAfiliacion = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INTENTO_AFILIACION_BY_ID, IntentoAfiliacion.class)
					.setParameter("idIntentoAfiliacion", idIntentoAfiliacion).getSingleResult();
			intentoAfiliacion.setIdComunicado(idComunicado);
		} catch (NoResultException nre) {
			logger.warn("Finaliza " + firmaServicio + ": No se encontró el intento de afiliación con id "
					+ idIntentoAfiliacion);
		} catch (NonUniqueResultException nure) {
			logger.warn("Finaliza " + firmaServicio + ": Se encontró más de un intento de afiliación con id "
					+ idIntentoAfiliacion);
		}
	}

	@Override
	public Integer utilitarioDatosLiquidacion0620() {
		// TODO Auto-generated method stub
		List<Long> idsBeneficiarios = new ArrayList<Long>();
		int registrosProcesados = 0;

		idsBeneficiarios = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_SIN_FECHA_AFILIACION, Long.class)
				.getResultList();

		for (Long id : idsBeneficiarios) {

			Beneficiario beneficiario = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO, Beneficiario.class)
					.setParameter("idBeneficiario", id)
					.getSingleResult();

			Date fechaAfiliacion = ultimaFechaActivacion(id);
			beneficiario.setFechaAfiliacion(fechaAfiliacion);
			System.out.println(
					"BenId : " + beneficiario.getIdBeneficiario() + " = " + id + " Fecha : " + fechaAfiliacion);
			registrosProcesados++;
		}

		return registrosProcesados;
	}

	private Date ultimaFechaActivacion(Long idBeneficiario) {

		String estadoAnterior = "INACTIVO";
		String estadoActual = "";
		String fechaS = "";
		Date fechaAfiliacion = new Date();

		List<Object[]> reporteEstados = entityManagerAud
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ESTADOS_BENEFICIARIO)
				.setParameter("idBeneficiario", idBeneficiario)
				.getResultList();

		for (Object[] objects : reporteEstados) {

			estadoActual = objects[1].toString();
			if (estadoActual.equals("ACTIVO") && estadoAnterior.equals("INACTIVO")) {
				fechaS = objects[0].toString();
			}
			estadoAnterior = estadoActual;
		}
		try {
			fechaAfiliacion = new SimpleDateFormat("yyyy-MM-dd").parse(fechaS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fechaAfiliacion;
	}

		@Override
	public List<InfoPersonasEnRelacionHijosOutDTO> obtenerPersonasEnRelacionHijos(TipoIdentificacionEnum tipoIdentificacionAfiliado,
		String numeroIdentificacionAfiliado, Boolean tipo) {
		String firmaServicio = "obtenerPersonasEnRelacionHijos(TipoIdentificacionEnum, String)";
		List<Object[]> datosPersonasEnRelacionHijos = null;
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		try {
			List<InfoPersonasEnRelacionHijosOutDTO> infoPersonasEnRelacionHijosList = new ArrayList<>();
			if(tipo == null){
				tipo = false;
			}

			if(!tipo){
				logger.info("Tipo de consulta: " + tipo);
				datosPersonasEnRelacionHijos = (List<Object[]>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_PERSONAS_EN_RELACION_HIJOS)
					.setParameter("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
					.setParameter("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado.name())
					.getResultList();
			}else{
				logger.info("Tipo de consulta2: " + tipo);
				datosPersonasEnRelacionHijos = (List<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_PERSONAS_EN_RELACION_HIJOS2)
						.setParameter("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
						.setParameter("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado.name())
						.getResultList();
			}

			if (datosPersonasEnRelacionHijos != null && !datosPersonasEnRelacionHijos.isEmpty()) {
				for (Object[] datosPersona : datosPersonasEnRelacionHijos) {
					InfoPersonasEnRelacionHijosOutDTO infoPersonasEnRelacionHijos = new InfoPersonasEnRelacionHijosOutDTO();
					infoPersonasEnRelacionHijos.setIdPersona(
						datosPersona[0] != null ? Long.parseLong(datosPersona[0].toString()): null);
					infoPersonasEnRelacionHijos.setIdBeneficiario(
						datosPersona[1] != null ? Long.parseLong(datosPersona[1].toString()): null);
					infoPersonasEnRelacionHijos.setIdentificacionPersonaEnRelacionHijos(
						datosPersona[2] != null ? datosPersona[2].toString() : null);
					infoPersonasEnRelacionHijos.setNumeroIdentificacionPersonaEnRelacionHijos(
						datosPersona[3] != null ? datosPersona[3].toString() : null);
					infoPersonasEnRelacionHijos.setPrimerNombrePersonaEnRelacionHijos(
						datosPersona[4] != null ? datosPersona[4].toString() : null);
					infoPersonasEnRelacionHijos.setSegundoNombrePersonaEnRelacionHijos(
						datosPersona[5] != null ? datosPersona[5].toString() : null);
					infoPersonasEnRelacionHijos.setPrimerApellidoPersonaEnRelacionHijos(
						datosPersona[6] != null ? datosPersona[6].toString() : null);
					infoPersonasEnRelacionHijos.setSegundoApellidoPersonaEnRelacionHijos(
						datosPersona[7] != null ? datosPersona[7].toString() : null);
					infoPersonasEnRelacionHijos.setEstadoRerspectoAfiliadoPersonaEnRelacionHijos(
						datosPersona[8] != null ? datosPersona[8].toString() : null);
					infoPersonasEnRelacionHijosList.add(infoPersonasEnRelacionHijos);
					logger.info("Persona en relacion hijos: " + infoPersonasEnRelacionHijos.toString());
				}
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
			return infoPersonasEnRelacionHijosList;
		} catch (Exception e) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
		}
	}
	
	@Override
	public InfoPadresBiologicosOutDTO obtenerPadresBiologicosPersona(TipoIdentificacionEnum tipoID,
			String identificacion) {
		String firmaServicio = "obtenerPadresBiologicosBeneficiario(TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		try {
			InfoPadresBiologicosOutDTO infoPadresBiologicos = new InfoPadresBiologicosOutDTO();

			List<Object[]> datosPadre = (List<Object[]>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_PADRE_BIOLOGICO)
					.setParameter("numeroId", identificacion)
					.setParameter("tipoId", tipoID.name())
					.getResultList();

			List<Object[]> datosMadre = (List<Object[]>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_MADRE_BIOLOGICA)
					.setParameter("numeroId", identificacion)
					.setParameter("tipoId", tipoID.name())
					.getResultList();

			if (datosPadre != null && !datosPadre.isEmpty()) {
				infoPadresBiologicos
						.setTipoIdPadreBiologico(datosPadre.get(0)[0] != null ? datosPadre.get(0)[0].toString() : null);
				infoPadresBiologicos.setIdentificacionPadreBiologico(
						datosPadre.get(0)[1] != null ? datosPadre.get(0)[1].toString() : null);
				infoPadresBiologicos
						.setNombrePadreBiologico(datosPadre.get(0)[2] != null ? datosPadre.get(0)[2].toString() : null);
				infoPadresBiologicos
						.setFechaInicioExclusionSumatoriaPadre(datosPadre.get(0)[3] != null ? datosPadre.get(0)[3].toString() : null);
				infoPadresBiologicos
						.setFechaFinExclusionSumatoriaPadre(datosPadre.get(0)[4] != null ? datosPadre.get(0)[4].toString() : null);
			}
			if (datosMadre != null && !datosMadre.isEmpty()) {
				infoPadresBiologicos
						.setTipoIdMadreBiologica(datosMadre.get(0)[0] != null ? datosMadre.get(0)[0].toString() : null);
				infoPadresBiologicos.setIdentificacionMadreBiologica(
						datosMadre.get(0)[1] != null ? datosMadre.get(0)[1].toString() : null);
				infoPadresBiologicos
						.setNombreMadreBiologica(datosMadre.get(0)[2] != null ? datosMadre.get(0)[2].toString() : null);
				infoPadresBiologicos
						.setFechaInicioExclusionSumatoriaMadre(datosMadre.get(0)[3] != null ? datosMadre.get(0)[3].toString() : null);
				infoPadresBiologicos
						.setFechaFinExclusionSumatoriaMadre(datosMadre.get(0)[4] != null ? datosMadre.get(0)[4].toString() : null);
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
			return infoPadresBiologicos;
		} catch (Exception e) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public List<InfoHijoBiologicoOutDTO> obtenerHijosBiologicosPersona(TipoIdentificacionEnum tipoID,
			String identificacion) {
		String firmaServicio = "obtenerPadresBiologicosBeneficiario(TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		try {
			List<InfoHijoBiologicoOutDTO> infoHijosBiologicos = new ArrayList<>();

			List<Object[]> datosHijos = (List<Object[]>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_DATOS_HIJO_BIOLOGICO_PERSONA)
					.setParameter("numeroId", identificacion)
					.setParameter("tipoId", tipoID.name())
					.getResultList();

			if (datosHijos != null && !datosHijos.isEmpty()) {
				for (Object[] datosHijoBiologico : datosHijos) {
					InfoHijoBiologicoOutDTO hijoBiologico = new InfoHijoBiologicoOutDTO();
					hijoBiologico.setTipoIdHijoBiologico(
							datosHijoBiologico[0] != null ? datosHijoBiologico[0].toString() : null);
					hijoBiologico.setIdentificacionHijoBiologico(
							datosHijoBiologico[1] != null ? datosHijoBiologico[1].toString() : null);
					hijoBiologico.setNombreHijoBiologico(
							datosHijoBiologico[2] != null ? datosHijoBiologico[2].toString() : null);
					infoHijosBiologicos.add(hijoBiologico);
				}
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
			return infoHijosBiologicos;
		} catch (Exception e) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public List<CertificadoEscolaridadOutDTO> obtenerCertificadosEscolaridad(TipoIdentificacionEnum tipoID,
			String identificacion) {
		String firmaServicio = "obtenerCertificadosEscolaridad(TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		try {
			List<CertificadoEscolaridadOutDTO> certificadosEscolares = new ArrayList<>();

			List<Object[]> resultadoConsulta = (List<Object[]>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_CERTIFICADOS_ESCOLARES)
					.setParameter("numeroId", identificacion)
					.setParameter("tipoId", tipoID.name())
					.getResultList();

			if (resultadoConsulta != null && !resultadoConsulta.isEmpty()) {
				for (Object[] registroCertificado : resultadoConsulta) {

					CertificadoEscolaridadOutDTO certificado = new CertificadoEscolaridadOutDTO();
					certificado.setFechaRecepcion(
							registroCertificado[0] != null ? registroCertificado[0].toString() : null);
					certificado.setFechaVencimientoCertificadoEscolar(
							registroCertificado[1] != null ? registroCertificado[1].toString() : null);

					if (certificado.getFechaVencimientoCertificadoEscolar() != null) {
						Date fechaVencimiento = CalendarUtils
								.darFormatoYYYYMMDDGuionDate(certificado.getFechaVencimientoCertificadoEscolar());
						Date ahora = new Date();

						Boolean actualMenorVencimiento = CalendarUtils.esFechaMenorIgual(ahora, fechaVencimiento);

						if (actualMenorVencimiento) {
							certificado.setCertificadoEscolaridad(Boolean.TRUE);
						} else {
							certificado.setCertificadoEscolaridad(Boolean.FALSE);
						}
					}

					certificadosEscolares.add(certificado);
				}
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
			return certificadosEscolares;
		} catch (Exception e) {
			logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public Integer actualizarFechaCreacionCertificadoEscolar() {
		String firmaServicio = "AfiliacionesService.actualizarFechaCreacionCerfiticadoEscolar()";
		logger.debug("Inicia " + firmaServicio);
		List<Long> idsCertificados = new ArrayList<Long>();
		int registrosProcesados = 0;

		idsCertificados = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_CERTIFICADOS_FECHA_CREACION_NULL, Long.class)
				.getResultList();

		int totalregistros = idsCertificados.size();

		for (Long idCertificado : idsCertificados) {

			try {
				PersistirFechaCreacionCertificado persistSrv = new PersistirFechaCreacionCertificado(idCertificado);
				persistSrv.execute();

				logger.warn("Registro " + registrosProcesados + " de " + totalregistros);
				registrosProcesados++;
			} catch (Exception e) {
				logger.error("Error procesando " + idCertificado, e);
			}

		}
		logger.debug("Finaliza " + firmaServicio);
		return registrosProcesados;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistirFechaCreacionCertificado(Long idCertificado) {
		CertificadoEscolarBeneficiario certificadoEscolarBeneficiario = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CERTIFICADO_ESCOLAR,
						CertificadoEscolarBeneficiario.class)
				.setParameter("idCertificado", idCertificado)
				.getSingleResult();
		certificadoEscolarBeneficiario
				.setFechaCreacionCertificadoEscolar(this.fechaCreacionCertificadoEscolar(idCertificado));
	}

	private Date fechaCreacionCertificadoEscolar(Long idCertificado) {

		Date fechaCreacion = null;

		Object fechaCreacionObj = (Object) entityManagerAud
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_CREACION_CERFICADO_AUD)
				.setParameter("idCertificado", idCertificado)
				.getSingleResult();

		try {
			fechaCreacion = new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionObj.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fechaCreacion;
	}

	/**
	 * Consulta los trabajadores activos respecto a empleadores inactivos
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<RelacionTrabajadorEmpresaDTO> consultarTrabajadoresActivosConEmpleadorInactivo() {

		List<RelacionTrabajadorEmpresaDTO> personasAInactivar = new ArrayList<>();
		// Se consultan las personas que están activas respecto a un empleador inactivo
		List<Object[]> result = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_ACTIVOS_EMPLEADORES_INACTIVOS)
				.getResultList();

		if (result.size() > 0) {
			Date fechaRetiroAfiliado = null;
			for (Object[] objects : result) {
				RelacionTrabajadorEmpresaDTO persona = new RelacionTrabajadorEmpresaDTO();
				persona.setTipoIdentificacionEmpleador(TipoIdentificacionEnum.valueOf(objects[0].toString()));
				persona.setNumeroIdentificacionEmpleador(objects[1].toString());
				persona.setTipoIdentificacionAfiliado(TipoIdentificacionEnum.valueOf(objects[2].toString()));
				persona.setNumeroIdentificacionAfiliado(objects[3].toString());
				String rolAfiTemp = objects[4].toString();
				persona.setIdRolAfiliado(Long.parseLong(rolAfiTemp));
				fechaRetiroAfiliado = calcularFechaRetiroAfiliadoDesdeAuditoria(Long.parseLong(rolAfiTemp));
				if (fechaRetiroAfiliado != null) {
					persona.setFechaRetiro(fechaRetiroAfiliado);
				} else if (objects[5] != null) {
					String fecha = objects[5].toString();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
					Date fechaFormateada = null;
					try {
						fechaFormateada = (Date) formatter.parse(fecha);
					} catch (ParseException e) {
						persona.setFechaRetiro(null);
						e.printStackTrace();
					}
					persona.setFechaRetiro(fechaFormateada);
				} // si no tiene fecha de retiro no se agrega
				if (persona.getFechaRetiro() != null) {
					personasAInactivar.add(persona);
				}

			}
		}

		return personasAInactivar;
	}

	/**
	 * Calcula la fecha más reciente de inactivación según los registros de
	 * auditoria
	 * 
	 * @param rolAfiliado
	 * @return
	 */
	public Date calcularFechaRetiroAfiliadoDesdeAuditoria(Long rolAfiliado) {
		List<Object[]> transaccionesRolAfiliado = entityManagerAud
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_ROLES_AFILIADO_AUDITORIA)
				.setParameter("rolAfiliadoId", rolAfiliado)
				.getResultList();

		Date fechaRetiroRecienteAfiliado = null;
		String estadoActual = "";
		String estadoAnterior = "";
		Date fechaFormateada = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS XXX", Locale.US);

		if (transaccionesRolAfiliado.size() > 0) {
			if (transaccionesRolAfiliado.size() > 1) {
				for (int i = 0; i < transaccionesRolAfiliado.size() - 1; i++) {
					estadoAnterior = transaccionesRolAfiliado.get(i)[1] != null
							? transaccionesRolAfiliado.get(i)[1].toString()
							: "";
					estadoActual = transaccionesRolAfiliado.get(i + 1)[1] != null
							? transaccionesRolAfiliado.get(i + 1)[1].toString()
							: "";
					// Compara el estado actual con el enterior, si es INACTIVO se asigna la
					// fechaRetiro
					if (!estadoAnterior.equals(estadoActual) && estadoActual.equals("INACTIVO")) {
						String fecha = transaccionesRolAfiliado.get(i + 1)[0].toString();
						try {
							fechaFormateada = (Date) formatter.parse(fecha);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						fechaRetiroRecienteAfiliado = fechaFormateada;
						// Si el primer registro es INACTIVO se asigna la fecha de auditoria
					} else if (i == 0 && estadoAnterior.equals("INACTIVO")) {
						String fecha = transaccionesRolAfiliado.get(i)[0].toString();
						try {
							fechaFormateada = (Date) formatter.parse(fecha);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						fechaRetiroRecienteAfiliado = fechaFormateada;
					}
				}
			} // Si solo tiene un registro se verifica si es de inactivacion
			else {
				estadoActual = transaccionesRolAfiliado.get(0)[1] != null
						? transaccionesRolAfiliado.get(0)[1].toString()
						: "";
				if (transaccionesRolAfiliado.size() == 1 && estadoActual.equals("INACTIVO")) {
					String fecha = transaccionesRolAfiliado.get(0)[0].toString();
					try {
						fechaFormateada = (Date) formatter.parse(fecha);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					fechaRetiroRecienteAfiliado = fechaFormateada;
					;
				}
			}
		}

		return fechaRetiroRecienteAfiliado;

	}

	@Override
	public List<AfiliadoNovedadRetiroNoAplicadaDTO> obtenerNovedadesRetiroNoProcesadasPila() {
		try {
			List<Object[]> resultQuery = (List<Object[]>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_NO_RETIRADOS_PILA)
					.getResultList();

			return resultQuery.stream().map(
					obj -> new AfiliadoNovedadRetiroNoAplicadaDTO.AfiliadoNovedadRetiroNoAplicadaDTOBuilder()
							.setId(Long.parseLong(obj[0].toString()))
							.setFechaInicioNovedad(obj[1].toString())
							.setNumeroIdentificacionAfiliado(obj[2].toString())
							.setTipoIdentificacionAfiliado(TipoIdentificacionEnum.valueOf(obj[3].toString()))
							.setNumeroIdentificacionEmpleador(obj[4].toString())
							.setTipoIdentificacionEmpleador(TipoIdentificacionEnum.valueOf(obj[5].toString()))
							.setIdRolAfiliado(Long.parseLong(obj[6].toString()))
							.build())
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void marcarNovedadesRetiroNoProcesadasPila(Long id) {
		entityManager.createNamedQuery(NamedQueriesConstants.MARCAR_PROCESO_REGISTRO_AFILIADO_NO_RETIRADOS_PILA)
				.setParameter("id", id).executeUpdate();
	}

	@Override
	public List<ConsultarInformacionCompletaAfiliadoDTO> consultarInformacionCompletaAfiliadoConfa(
			TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado) {

		String firmaMetodo = "consultarInformacionCompletaAfiliadoConfa(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String Periodo)";
		List<Object[]> reultStoreProcedureAf = null;
		logger.info("consultarInformacionCompletaAfiliadoConfa tipoIdentificacionAfiliado "
				+ tipoIdentificacionAfiliado.name());
		logger.info("consultarInformacionCompletaAfiliadoConfa numeroIdentificacionAfiliado "
				+ numeroIdentificacionAfiliado);
		try {
			StoredProcedureQuery query = entityManager
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.STORED_PROCEDURE_WEB_CONSULTAR_INFORMACION_COMPLETA_AFILIADO_CONFA);

			query.setParameter("tipoDocumento", tipoIdentificacionAfiliado.name());
			query.setParameter("numeroDocumento", numeroIdentificacionAfiliado);
			// query.execute();
			// result = (Date) query.getOutputParameterValue("dFechaHabil");
			reultStoreProcedureAf = (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.info(" :: Hubo un error en el SP Afiliaciones consultarInformacionCompletaAfiliadoConfa: " + e);
		}
		List<ConsultarInformacionCompletaAfiliadoDTO> resulDTO = new ArrayList<>();
		if (reultStoreProcedureAf != null) {
			for (Object[] reultObj : reultStoreProcedureAf) {
				ConsultarInformacionCompletaAfiliadoDTO datosafi = new ConsultarInformacionCompletaAfiliadoDTO();
				datosafi.setResultado_consulta(reultObj[0] != null ? reultObj[0].toString() : null);
				datosafi.setPri_nom_afiliado(reultObj[1] != null ? reultObj[1].toString() : null);
				datosafi.setSeg_nom_afiliado(reultObj[2] != null ? reultObj[2].toString() : null);
				datosafi.setPri_ape_afiliado(reultObj[3] != null ? reultObj[3].toString() : null);
				datosafi.setSeg_ape_afiliado(reultObj[4] != null ? reultObj[4].toString() : null);
				datosafi.setTipo_doc_afiliado(reultObj[5] != null ? reultObj[5].toString() : null);
				datosafi.setNum_doc_afiliado(reultObj[6] != null ? reultObj[6].toString() : null);
				datosafi.setEstado_afiliado(reultObj[7] != null ? reultObj[7].toString() : null);
				datosafi.setCategoria_afiliado(reultObj[8] != null ? reultObj[8].toString() : null);
				datosafi.setClasificacion(reultObj[9] != null ? reultObj[9].toString() : null);
				datosafi.setClase_trabajador(reultObj[10] != null ? reultObj[10].toString() : null);
				datosafi.setTipo_afiliacion(reultObj[11] != null ? reultObj[11].toString() : null);
				datosafi.setCod_depto_ubicacion(reultObj[12] != null ? reultObj[12].toString() : null);
				datosafi.setCod_mun_ubicacion(reultObj[13] != null ? reultObj[13].toString() : null);
				datosafi.setCorreo_afiliado(reultObj[14] != null ? reultObj[14].toString() : null);
				datosafi.setFecha_nacimiento(reultObj[15] != null ? reultObj[15].toString() : null);
				datosafi.setTipo_doc_emp(reultObj[16] != null ? reultObj[16].toString() : null);
				datosafi.setNum_doc_emp(reultObj[17] != null ? reultObj[17].toString() : null);
				datosafi.setEstado_emp(reultObj[18] != null ? reultObj[18].toString() : null);
				datosafi.setNombre_comercial(reultObj[19] != null ? reultObj[19].toString() : null);
				resulDTO.add(datosafi);
			}
		}
		logger.info(ConstantesComunes.FIN_LOGGER);
		return resulDTO;
	}

	@Override
	public List<ConsultarInformacionCompletaBeneficiarioDTO> consultarInformacionCompletaBeneficiarioConfa(
			TipoIdentificacionEnum tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario) {
		String firmaMetodo = "consultarInformacionCompletaBeneficiarioConfa(TipoIdentificacionEnum tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario, String Periodo)";
		List<Object[]> reultStoreProcedureBen = null;
		logger.info("consultarInformacionCompletaBeneficiarioConfa tipoIdentificacionBeneficiario "
				+ tipoIdentificacionBeneficiario.name());
		logger.info("consultarInformacionCompletaBeneficiarioConfa numeroIdentificacionAfiliado "
				+ numeroIdentificacionBeneficiario);
		try {
			StoredProcedureQuery query = entityManager
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.STORED_PROCEDURE_WEB_CONSULTAR_INFORMACION_COMPLETA_BENEFICIARIO_CONFA);
			query.setParameter("tipoDocumento", tipoIdentificacionBeneficiario.name());
			query.setParameter("numeroDocumento", numeroIdentificacionBeneficiario);
			// query.execute();
			// result = (Date) query.getOutputParameterValue("dFechaHabil");
			reultStoreProcedureBen = (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.info(" :: Hubo un error en el SP Afiliaciones consultarInformacionCompletaBeneficiarioConfa: " + e);
		}
		List<ConsultarInformacionCompletaBeneficiarioDTO> resulDTOBen = new ArrayList<>();
		if (reultStoreProcedureBen != null) {
			for (Object[] reultObj : reultStoreProcedureBen) {
				ConsultarInformacionCompletaBeneficiarioDTO datosb = new ConsultarInformacionCompletaBeneficiarioDTO();
				datosb.setResultado_consulta(reultObj[0] != null ? reultObj[0].toString() : null);
				datosb.setTipo_doc_ben(reultObj[1] != null ? reultObj[1].toString() : null);
				datosb.setNum_doc_ben(reultObj[2] != null ? reultObj[2].toString() : null);
				datosb.setPri_nom_ben(reultObj[3] != null ? reultObj[3].toString() : null);
				datosb.setSeg_nom_ben(reultObj[4] != null ? reultObj[4].toString() : null);
				datosb.setPri_ape_ben(reultObj[5] != null ? reultObj[5].toString() : null);
				datosb.setSeg_ape_ben(reultObj[6] != null ? reultObj[6].toString() : null);
				datosb.setEstado_beneficiario(reultObj[7] != null ? reultObj[7].toString() : null);
				datosb.setCondicion_inv(reultObj[8] != null ? reultObj[8].toString() : null);
				datosb.setEdad(reultObj[9] != null ? reultObj[9].toString() : null);
				datosb.setFecha_nacimiento(reultObj[10] != null ? reultObj[10].toString() : null);
				datosb.setNum_grupo_fam(reultObj[11] != null ? reultObj[11].toString() : null);
				datosb.setGrado(reultObj[12] != null ? reultObj[12].toString() : null);
				datosb.setNivel_educativo(reultObj[13] != null ? reultObj[13].toString() : null);
				datosb.setSitio_pago(reultObj[14] != null ? reultObj[14].toString() : null);
				datosb.setEstado_grupo_fam(reultObj[15] != null ? reultObj[15].toString() : null);
				datosb.setParentesco(reultObj[16] != null ? reultObj[16].toString() : null);
				datosb.setTipo_doc_otro_padre(reultObj[17] != null ? reultObj[17].toString() : null);
				datosb.setNum_doc_otro_padre(reultObj[18] != null ? reultObj[18].toString() : null);
				datosb.setCertificado_vigente(reultObj[19] != null ? reultObj[19].toString() : null);
				datosb.setFecha_ini_cert(reultObj[20] != null ? reultObj[20].toString() : null);
				datosb.setFecha_fin_cert(reultObj[21] != null ? reultObj[21].toString() : null);
				datosb.setFecha_liquidacion(reultObj[22] != null ? reultObj[22].toString() : null);
				datosb.setTipo_doc_afi(reultObj[23] != null ? reultObj[23].toString() : null);
				datosb.setNum_doc_afi(reultObj[24] != null ? reultObj[24].toString() : null);
				resulDTOBen.add(datosb);
			}
		}
		return resulDTOBen;
	}

	@Override
	public Long actualizarCargueSupervivencia(ArchivoSupervivenciaDTO archivoSupervivenciaDTO) {
		logger.debug("Inicia modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
		try {
			boolean crear = false;
			CargueMultipleSupervivencia cargueSupervivencia = null;
			if (archivoSupervivenciaDTO.getIdentificadorCargue() != null) {
				try {
					cargueSupervivencia = (CargueMultipleSupervivencia) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_CARGUE_MULTIPLE_SUPERVIVENCIA_POR_ID)
							.setParameter("idCargueSupervivencia", archivoSupervivenciaDTO.getIdentificadorCargue())
							.getSingleResult();
					if (cargueSupervivencia != null) {
						if (archivoSupervivenciaDTO.getIdentificadorECMRegistro() != null) {
							cargueSupervivencia
									.setIdentificacionECM(archivoSupervivenciaDTO.getIdentificadorECMRegistro());
						}
						if (archivoSupervivenciaDTO.getFechaIngreso() != null) {
							cargueSupervivencia.setFechaIngreso(new Date(archivoSupervivenciaDTO.getFechaIngreso()));
						}
						if (archivoSupervivenciaDTO.getUsuario() != null) {
							cargueSupervivencia.setUsuario(archivoSupervivenciaDTO.getUsuario());
						}
						if (archivoSupervivenciaDTO.getEstadoCargue() != null) {
							cargueSupervivencia.setEstadoCargueSupervivencia(archivoSupervivenciaDTO.getEstadoCargue());
						}
						if (archivoSupervivenciaDTO.getPeriodo() != null) {
							cargueSupervivencia.setPeriodo(new Date(archivoSupervivenciaDTO.getPeriodo()));
						}
						if (archivoSupervivenciaDTO.getNombreArhivo() != null) {
							cargueSupervivencia.setNombreArchivo(archivoSupervivenciaDTO.getNombreArhivo());
						}
						entityManager.merge(cargueSupervivencia);
					} else {
						crear = true;
					}
				} catch (NoResultException e) {
					crear = true;
				}
			} else {
				crear = true;
			}
			if (crear) {
				if (archivoSupervivenciaDTO.getIdentificadorECMRegistro() != null
						&& archivoSupervivenciaDTO.getFechaIngreso() != null
						&& archivoSupervivenciaDTO.getUsuario() != null
						&& archivoSupervivenciaDTO.getEstadoCargue() != null
						&& archivoSupervivenciaDTO.getNombreArhivo() != null) {
					cargueSupervivencia = new CargueMultipleSupervivencia();
					cargueSupervivencia.setFechaIngreso(new Date(archivoSupervivenciaDTO.getFechaIngreso()));
					cargueSupervivencia.setIdentificacionECM(archivoSupervivenciaDTO.getIdentificadorECMRegistro());
					cargueSupervivencia.setEstadoCargueSupervivencia(archivoSupervivenciaDTO.getEstadoCargue());
					cargueSupervivencia.setUsuario(archivoSupervivenciaDTO.getUsuario());
					cargueSupervivencia.setNombreArchivo(archivoSupervivenciaDTO.getNombreArhivo());
					if (archivoSupervivenciaDTO.getPeriodo() != null) {
						cargueSupervivencia.setPeriodo(new Date(archivoSupervivenciaDTO.getPeriodo()));
					}
					entityManager.persist(cargueSupervivencia);
				} else {
					logger.debug("Finaliza modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
					throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
				}
			}
			logger.debug("Finaliza modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
			return cargueSupervivencia.getIdCargueMultipleSupervivencia();
		} catch (Exception e) {
			logger.debug("Finaliza modificarCargueSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
					"Error al calcular el identificador de la carga múltiple");
		}
	}
	@Override
    public String consultarUltimaClasificacionCategoria( String numeroDocumento, TipoIdentificacionEnum tipoDocumento){
		logger.info(numeroDocumento);
		logger.info(tipoDocumento);

		try {
			return (String) entityManager.createNamedQuery(NamedQueriesConstants.CLASIFICACION_CATEGORIA_AFILIADO)
			.setParameter("tipoDocumento", tipoDocumento.name())
			.setParameter("numeroDocumento", numeroDocumento)
			.getSingleResult();
		} catch (NoResultException e) {
			logger.error("No se encontraron resultados", e);
			return null;
		}
		
	}

	@Override
	public Departamento consultarDepartamentoPorIdMunicipio(Short codigoMunicipio) {
		logger.debug("Inicia operación consultarDepartamentoPorIdMunicipio(Short)");
		try {
			Departamento departamento = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_DEPARTAMENTO_ID_MUNICIPIO,
							Departamento.class)
					.setParameter("codigoMunicipio", codigoMunicipio).getSingleResult();
			logger.debug("Finaliza operación consultarDepartamentoPorIdMunicipio(Short)");
			return departamento;

		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarDepartamentoPorIdMunicipio(Short)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override 
	 	public void desistirSolicitudAfiliacion(){
			try{
				StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SCHEDULE_DESISTIR_SOLICITUD_AFILIACION);
				query.execute();
			}catch(Exception e){
				logger.info("Fallo el servicio de desistir solicitud "+ e);
			}
		}

	@Override
	public ArchivosTrasladosEmpresasCCF crearArchivoTrasladosCFF(Long IdCargueC, Long idEmpleadorE, String datosTemporales ){
		ArchivosTrasladosEmpresasCCF ArchivoTrasladosCFF = new  ArchivosTrasladosEmpresasCCF();
		ArchivoTrasladosCFF.setIdCargueC(IdCargueC);
		ArchivoTrasladosCFF.setIdEmpleadorE(idEmpleadorE);
		ArchivoTrasladosCFF.setDatosTemporales(datosTemporales);
		entityManager.persist(ArchivoTrasladosCFF);
		return  ArchivoTrasladosCFF;
	}

	@Override
	public List<ArchivosTrasladosEmpresasCCF> consultarArchivosTrasladosCCF(String numeroDocumentoEmpleador, TipoIdentificacionEnum tipoDocumentoEmpleador) {
		Long idArchivoTraslados = null;

		List<Object> idArchivos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_TRASLADOS_CCF)
				.setParameter("numeroIdentificacion", numeroDocumentoEmpleador)
				.setParameter("tipoIdentificacion", tipoDocumentoEmpleador.name())
				.getResultList();
		
		if (idArchivos != null && !idArchivos.isEmpty()) {
			List<Long> idArchivosLong = new ArrayList<>();
			for (Object idArchivo : idArchivos) {
				idArchivosLong.add(Long.parseLong(idArchivo.toString()));
			}
			List<ArchivosTrasladosEmpresasCCF> res = new ArrayList<>();
			for (Long idArchivo: idArchivosLong) {
				res.add(entityManager.find(ArchivosTrasladosEmpresasCCF.class, idArchivo));
			}
			return res;
		}
		// Si no entra al if entonces no hay data
		return null;
			

	}
	
	@Override
	public void cancelarCarguesEmpleador(String numeroDocumentoEmpleador, TipoIdentificacionEnum tipoDocumentoEmpleador) {
		Long idArchivoTraslados = null;

		
		entityManager.createNamedQuery(NamedQueriesConstants.CANCELAR_ARCHIVOS_TRASLADOS_CCF_A_CARGO)
		.setParameter("numeroIdentificacion", numeroDocumentoEmpleador)
		.setParameter("tipoIdentificacion", tipoDocumentoEmpleador.name())
		.executeUpdate();
		
		List<ArchivosTrasladosEmpresasCCF> archivosTraslados = consultarArchivosTrasladosCCF(numeroDocumentoEmpleador, tipoDocumentoEmpleador);
		
		if (archivosTraslados != null && !archivosTraslados.isEmpty()) {
			List<Long> idsCargueEmpleador = new ArrayList<>();
			for (ArchivosTrasladosEmpresasCCF archivo : archivosTraslados) {
				String objetoTemporal = archivo.getDatosTemporales();
				try {
					ObjectMapper mapper = new ObjectMapper();
					ResultadoValidacionArchivoTrasladoDTO datosEmp = mapper.readValue(objetoTemporal, ResultadoValidacionArchivoTrasladoDTO.class);
					idsCargueEmpleador.add(datosEmp.getIdCargue());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			entityManager.createNamedQuery(NamedQueriesConstants.CANCELAR_ARCHIVOS_TRASLADOS_CCF)
			.setParameter("idCargues", idsCargueEmpleador)
			.executeUpdate();

		}			
	}

	@Override
	public Boolean validarNumeroIdentificacionExistente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		Long count = (Long) entityManager
			.createNamedQuery(NamedQueriesConstants.OBTENER_NUMEROS_DOCUMENTOS_EXISTENTES)
			.setParameter("tipoIdentificacion", tipoIdentificacion)
			.setParameter("numeroIdentificacion", numeroIdentificacion)
			.getSingleResult();

		return count != null ? count > 0 : false;
	}
}
