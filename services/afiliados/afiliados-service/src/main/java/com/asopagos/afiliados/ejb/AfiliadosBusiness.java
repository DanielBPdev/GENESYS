package com.asopagos.afiliados.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;
import com.asopagos.afiliados.constants.ConstantesProcesoSitiosPago;
import com.asopagos.afiliados.constants.MensajesSalidaEnum;
import com.asopagos.afiliados.constants.NamedQueriesConstants;
import com.asopagos.afiliados.constants.QueryConstants;
import com.asopagos.afiliados.dto.ActivacionAfiliadoDTO;
import com.asopagos.afiliados.dto.AfiliadoIndependienteVista360DTO;
import com.asopagos.afiliados.dto.AfiliadoPensionadoVista360DTO;
import com.asopagos.afiliados.dto.CriteriosConsultaModeloInfraestructuraDTO;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.afiliados.dto.HistoricoAfiBeneficiario360DTO;
import com.asopagos.afiliados.dto.InfoAfiliadoRespectoEmpleadorDTO;
import com.asopagos.afiliados.dto.InfoEstadoAfiliadoDTO;
import com.asopagos.afiliados.dto.InfoRelacionLaboral360DTO;
import com.asopagos.afiliados.dto.InfoUltimoAporteDTO;
import com.asopagos.afiliados.dto.InfoVacionesYSuspensionAfiliadoDTO;
import com.asopagos.afiliados.dto.PresentacionResultadoModeloInfraestructuraDTO;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.afiliados.dto.SolicitudGlobalPersonaDTO;
import com.asopagos.afiliados.service.AfiliadosService;
import com.asopagos.cache.CacheManager;
import com.asopagos.clienteanibol.clients.ConsultarTarjetaActiva;
import com.asopagos.clienteanibol.dto.TarjetaDTO;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.constants.QueriesConstants;
import com.asopagos.dto.AfiliacionJefeHogarDTO;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AfiliadoSolicitudDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.BeneficiarioHijoPadreDTO;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.dto.CategoriaDTO;
import com.asopagos.dto.CertificadoEscolarBeneficiarioDTO;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.GrupoFamiliarDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.PersonaEmpresaDTO;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import com.asopagos.dto.InformacionPersonaDTO;
import com.asopagos.dto.InformacionPersonaEmpleadorDTO;
import com.asopagos.dto.InformacionPersonaCompletaDTO;
import com.asopagos.dto.InformacionBeneficiarioDTO;
import com.asopagos.dto.InformacionBeneficiarioAfiliadoDTO;
import com.asopagos.dto.InformacionBeneficiarioCompletaDTO;
import com.asopagos.dto.ConsultaBeneficiarioRequestDTO;
import com.asopagos.dto.TrabajadorEmpleadorDTO;
import com.asopagos.dto.RespuestaPaginacionDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.ExpulsionSubsanadaModeloDTO;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.dto.modelo.InfraestructuraModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.NovedadDetalleModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SitioPagoModeloDTO;
import com.asopagos.dto.modelo.TipoInfraestructuraModeloDTO;
import com.asopagos.dto.modelo.TipoTenenciaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.core.Infraestructura;
import com.asopagos.entidades.ccf.core.SitioPago;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.TipoInfraestructura;
import com.asopagos.entidades.ccf.core.TipoTenencia;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.AdminSubsidioGrupo;
import com.asopagos.entidades.ccf.personas.AdministradorSubsidio;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.BeneficiarioDetalle;
import com.asopagos.entidades.ccf.personas.Categoria;
import com.asopagos.entidades.ccf.personas.CertificadoEscolarBeneficiario;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.ExpulsionSubsanada;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.entidades.ccf.personas.MedioCheque;
import com.asopagos.entidades.ccf.personas.MedioConsignacion;
import com.asopagos.entidades.ccf.personas.MedioDePago;
import com.asopagos.entidades.ccf.personas.MedioEfectivo;
import com.asopagos.entidades.ccf.personas.MedioPagoPersona;
import com.asopagos.entidades.ccf.personas.MedioTarjeta;
import com.asopagos.entidades.ccf.personas.MedioTransferencia;
import com.asopagos.entidades.ccf.personas.NovedadDetalle;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralValidacionEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.EstadoTarjetaEnum;
import com.asopagos.enumeraciones.core.MedidaCapacidadInfraestructuraEnum;
import com.asopagos.enumeraciones.core.MedioComunicacionEntidadPagadoraEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rutine.afiliadosrutines.activarafiliado.ActivarAfiliadoRutine;
import com.asopagos.rutine.afiliadosrutines.actualizarbeneficiario.ActualizarBeneficiarioRutine;
import com.asopagos.rutine.afiliadosrutines.actualizarbeneficiariosimple.ActualizarBeneficiarioSimpleRutine;
import com.asopagos.rutine.afiliadosrutines.actualizardatosafiliado.ActualizarDatosAfiliadoRutine;
import com.asopagos.rutine.afiliadosrutines.actualizargrupofamiliarpersona.ActualizarGrupoFamiliarPersonaRutine;
import com.asopagos.rutine.afiliadosrutines.actualizarmediodepagogrupofamiliar.ActualizarMedioDePagoGrupoFamiliarRutine;
import com.asopagos.rutine.afiliadosrutines.actualizarnovedadpila.ActualizarNovedadPilaRutine;
import com.asopagos.rutine.afiliadosrutines.actualizarrolafiliado.ActualizarRolAfiliadoRutine;
import com.asopagos.rutine.afiliadosrutines.calcularcategoriasafiliado.CalcularCategoriasAfiliadoRutine;
import com.asopagos.rutine.afiliadosrutines.consultardatosafiliado.ConsultarDatosAfiliadoRutine;
import com.asopagos.rutine.afiliadosrutines.deshacergestioncerotrabajadores.DeshacerGestionCeroTrabajadoresRutine;
import com.asopagos.rutine.afiliadosrutines.inactivarbeneficiariorolafiliado.InactivarBeneficiarioRolAfiliadoRutine;
import com.asopagos.util.BigDecimalUtils;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import com.asopagos.util.Interpolator;
import com.asopagos.util.TarjetaUtils;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.ParameterMode;
import com.asopagos.entidades.ccf.personas.PuebloIndigena;
import com.asopagos.entidades.ccf.personas.Resguardo;
import com.asopagos.enumeraciones.personas.SolicitudTarjetaEnum;
import com.asopagos.dto.RolafiliadoNovedadAutomaticaDTO;
import java.time.LocalDate;
import com.asopagos.constants.ParametrosGapConstants;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response; 
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import com.asopagos.entidades.ccf.core.AuditoriaIntegracionServicios;
import com.asopagos.util.AuditoriaIntegracionInterceptor;
import javax.persistence.PersistenceContext;
import com.asopagos.rest.security.dto.UserDTO;
import java.util.Iterator;
import com.asopagos.pagination.PaginationQueryParamsEnum;

/**
 * <b>Descripcion:</b> EJB que implementa los metodos de negocio relacionados
 * con los afiliados a la caja de compensacion<b>Historia de Usuario:</b>
 * Transversal
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */

@Stateless
public class AfiliadosBusiness implements AfiliadosService {

	/**
	 * Referencia a la unidad de persistencia del servicio
	 */
	@PersistenceContext(unitName = "afiliados_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(AfiliadosBusiness.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#buscarAfiliados(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.Long,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaDTO> buscarAfiliados(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			String primerNombre, String primerApellido, Long fechaNacimiento, TipoAfiliadoEnum tipoAfiliado) {
		try {
			logger.debug("Inicia buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum)");
			// 1) Se buscan coincidencias por tipo y numero documento
			logger.info("Numero de identificacion:"+numeroIdentificacion+"Tipo Identificacion"+tipoIdentificacion);
			buscarAfiliadoPorTipoNumeroDocumento(tipoIdentificacion, numeroIdentificacion);
			// 2) Se buscan coincidencias por numero documento
			if (numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
				List<Afiliado> afiliados = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_NUMERO_DOCUMENTO, Afiliado.class)
						.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
				if (afiliados != null && !afiliados.isEmpty()) {
					logger.debug(
							"Finaliza buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum)");
					return convertirDatosAPersonaDTO(afiliados);
				}
			}
			// 3) Se buscan coincidencias primer nombre, primer apellido y fecha
			// de
			// nacimiento ( si esta diligenciado )
			if (fechaNacimiento != null && primerNombre != null && !primerNombre.isEmpty() && primerApellido != null
					&& !primerApellido.isEmpty()) {
				Date fecNac = CalendarUtils.truncarHora(new Date(fechaNacimiento));
				List<Afiliado> afiliados = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_DATOS_FECHA, Afiliado.class)
						.setParameter("primerNombre", primerNombre).setParameter("primerApellido", primerApellido)
						.setParameter("fechaNacimiento", fecNac).getResultList();
				if (afiliados != null && !afiliados.isEmpty()) {
					logger.debug(
							"Finaliza buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum)");
					return convertirDatosAPersonaDTO(afiliados);
				}
			}
			logger.info("Primer Nombre:"+primerNombre+"PrimerApellido:"+primerApellido);
			if (primerNombre != null && !primerNombre.isEmpty() && primerApellido != null
					&& !primerApellido.isEmpty()) {
				List<Afiliado> afiliados = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_DATOS, Afiliado.class)
						.setParameter("primerNombre", primerNombre).setParameter("primerApellido", primerApellido)
						.getResultList();
				if (afiliados != null && !afiliados.isEmpty()) {
					logger.debug(
							"Finaliza buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum)");
					return convertirDatosAPersonaDTO(afiliados);
				}
			}
			logger.debug(
					"Finaliza buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum): No se encontro el recurso solicitado");
			return null;
		} catch (NoResultException nure) {
			logger.debug(
					"Finaliza buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum): No se encontraron datos para el recurso solicitado");
			return null;
		} catch (Exception e) {
			logger.error("No es posible realizar la busqueda de Afiliados", e);
			logger.debug("Finaliza buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

    private List<PersonaDTO> buscarAfiliadoPorTipoNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        if (tipoIdentificacion != null && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
        	List<Afiliado> afiliados = entityManager
        			.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_TIPO_NUMERO_DOCUMENTO, Afiliado.class)
        			.setParameter("tipoIdentificacion", tipoIdentificacion)
        			.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
        	if (afiliados != null && !afiliados.isEmpty()) {
        		logger.debug(
        				"Finaliza buscarAfiliados(TipoIdentificacionEnum, String, String, Date, TipoAfiliadoEnum)");
        		return convertirDatosAPersonaDTO(afiliados);
        	}
        }
        return Collections.emptyList();
    }

	/**
	 * Mtodo encargado de convertir los datos del afiliado a PersonaDTO
	 * 
	 * @param afiliados
	 * @return Listado de Personas DTO
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<PersonaDTO> convertirDatosAPersonaDTO(List<Afiliado> afiliados) {
		List<PersonaDTO> personasDTO = new ArrayList<>();
		Long idPersona = null;
		List<ConsultarEstadoDTO> listConsulta = new ArrayList<ConsultarEstadoDTO>();
		for (Afiliado afiliado : afiliados) {
			idPersona = afiliado.getPersona().getIdPersona();
			PersonaDetalle personaDetalle = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_ID_PERSONA, PersonaDetalle.class)
					.setParameter("idPersona", idPersona).getSingleResult();
			PersonaDTO persona = PersonaDTO.obtenerPersonaDTODeAfiliado(afiliado, personaDetalle);
			personasDTO.add(persona);

			ConsultarEstadoDTO consulaEstado = new ConsultarEstadoDTO();
			consulaEstado.setEntityManager(entityManager);
			consulaEstado.setNumeroIdentificacion(afiliado.getPersona().getNumeroIdentificacion());
			consulaEstado.setTipoIdentificacion(afiliado.getPersona().getTipoIdentificacion());
			consulaEstado.setTipoPersona(ConstantesComunes.PERSONAS);
			listConsulta.add(consulaEstado);
		}

		List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsulta);
		for (PersonaDTO afiliado : personasDTO) {
			for (EstadoDTO consultarEstadoDTO : estados) {
				if (consultarEstadoDTO.getNumeroIdentificacion().equals(afiliado.getNumeroIdentificacion())
						&& consultarEstadoDTO.getTipoIdentificacion().equals(afiliado.getTipoIdentificacion())) {
					afiliado.setEstadoAfiliadoCaja(consultarEstadoDTO.getEstado());
				}
			}
		}
		return personasDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#crearAfiliado(com.asopagos.dto.AfiliadoInDTO)
	 */
	@Override
	public AfiliadoInDTO crearAfiliado(AfiliadoInDTO inDTO) {
		try {
			logger.info("Inicia crearAfiliado(AfiliadoInDTO)");
			Afiliado afiliado = null;
			Persona persona = null;
			PersonaDetalle personaDetalle = null;
                        Date fechaAfiliacion = new Date();
			if (inDTO.getIdAfiliado() != null) {
				afiliado = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_ID_AFILIADO, Afiliado.class)
						.setParameter("idAfiliado", inDTO.getIdAfiliado()).getSingleResult();
				persona = afiliado.getPersona();
				personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_ID_PERSONA, PersonaDetalle.class)
						.setParameter("idPersona", persona.getIdPersona()).getSingleResult();
			}

			if (persona != null && inDTO.getPersona().getTipoIdentificacion() != null
					&& inDTO.getPersona().getNumeroIdentificacion() != null
					&& (!persona.getTipoIdentificacion().equals(inDTO.getPersona().getTipoIdentificacion()) || !persona
							.getNumeroIdentificacion().equals(inDTO.getPersona().getNumeroIdentificacion()))) {
				logger.debug("Finaliza crearAfiliado(AfiliadoInDTO)");
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
			}

			if (inDTO.getPersona().getTipoIdentificacion() != null
					&& inDTO.getPersona().getNumeroIdentificacion() != null) {
				if (afiliado == null) {
					try {
						afiliado = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_TIPO_NUMERO_DOCUMENTO,
										Afiliado.class)
								.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
								.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
								.getSingleResult();
						if (afiliado != null) {
							persona = afiliado.getPersona();
						}
					} catch (NoResultException e) {
						afiliado = null;
					}
				}
				if (persona == null) {
					try {
						persona = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
										Persona.class)
								.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
								.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
								.getSingleResult();
					} catch (NoResultException e) {
						persona = null;
					}

				}
				if (personaDetalle == null) {
					try {
						personaDetalle = entityManager
								.createNamedQuery(
										NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
										PersonaDetalle.class)
								.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
								.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
								.getSingleResult();
					} catch (NoResultException e) {
						personaDetalle = null;
					}

				}
			}

			if (afiliado == null) {
				afiliado = new Afiliado();
				if (persona == null) {
					persona = crearPersona(inDTO);
					if (personaDetalle == null) {
						// Persona person = entityManager
						// .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
						// Persona.class)
						// .setParameter("tipoIdentificacion",
						// inDTO.getPersona().getTipoIdentificacion())
						// .setParameter("numeroIdentificacion",
						// inDTO.getPersona().getNumeroIdentificacion()).getSingleResult();
						inDTO.getPersona().setIdPersona(persona.getIdPersona());
						personaDetalle = crearPersonaDetalle(inDTO);
					}
				} else {
					if (persona.getIdPersona() != null) {
						inDTO.getPersona().setIdPersona(persona.getIdPersona());
					}
				}
				if (personaDetalle == null) {
					personaDetalle = crearPersonaDetalle(inDTO);
				}
				else if (personaDetalle != null && inDTO.getPersona().getFechaNacimiento() != null && personaDetalle.getFechaNacimiento() == null) {
					inDTO.getPersona().setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
					personaDetalle = crearPersonaDetalle(inDTO);
				}
				afiliado.setPersona(persona);
				entityManager.persist(afiliado);
			}
			boolean creaRolAfiliado = true;
			if (inDTO.getTipoAfiliado() != TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE) {
				List<RolAfiliado> roles = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID_TIPO, RolAfiliado.class)
						.setParameter("idAfiliado", afiliado.getIdAfiliado())
						.setParameter("tipoAfiliado", inDTO.getTipoAfiliado()).getResultList();
				creaRolAfiliado = (roles == null || roles.isEmpty());
				if (!creaRolAfiliado) {
					RolAfiliado rolAfiliado = roles.get(0);
					inDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
				}
			} else {
				List<RolAfiliado> roles = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID_TIPO, RolAfiliado.class)
						.setParameter("idAfiliado", afiliado.getIdAfiliado())
						.setParameter("tipoAfiliado", inDTO.getTipoAfiliado()).getResultList();
				for (RolAfiliado rolAfiliado : roles) {
					if (rolAfiliado.getEmpleador().getIdEmpleador().equals(inDTO.getIdEmpleador())) {
						inDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
						creaRolAfiliado = false;
					}
				}
			}

			if (creaRolAfiliado) {
				RolAfiliado rolAfiliado = new RolAfiliado();
				rolAfiliado.setAfiliado(afiliado);
				rolAfiliado.setTipoAfiliado(inDTO.getTipoAfiliado());
                //rolAfiliado.setFechaAfiliacion(fechaAfiliacion);
				// rolAfiliado.setEstadoAfiliado(EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION);
				rolAfiliado.setValorSalarioMesadaIngresos(inDTO.getValorSalarioMesada());
				if (inDTO.getSucursalEmpleadorId() != null
						&& inDTO.getSucursalEmpleadorId() > NumerosEnterosConstants.CERO) {
					SucursalEmpresa sucursal = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSAL_ID, SucursalEmpresa.class)
							.setParameter("idSucursalEmpresa", inDTO.getSucursalEmpleadorId()).getSingleResult();
					rolAfiliado.setSucursalEmpleador(sucursal);
				}
				if (inDTO.getFechaInicioContrato() != null) {
					rolAfiliado.setFechaIngreso(inDTO.getFechaInicioContrato());
				}

				if (inDTO.getClaseTrabajador() != null) {
					rolAfiliado.setClaseTrabajador(inDTO.getClaseTrabajador());
				}

				if (inDTO.getIdPagadorPension() != null) {
					AFP pagador = new AFP();
					pagador.setIdAFP(inDTO.getIdPagadorPension());
					rolAfiliado.setPagadorPension(pagador);
				}
				Empleador empleador = buscarEmpleadorId(inDTO.getIdEmpleador());
				if (empleador != null) {
					rolAfiliado.setEmpleador(empleador);
				}
				entityManager.persist(rolAfiliado);
				inDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
				if (persona.getUbicacionPrincipal() != null) {
					if (persona.getUbicacionPrincipal().getEmail() != null) {
						inDTO.setNombreUsuarioCaja(persona.getUbicacionPrincipal().getEmail());
					}
				}
			}
			logger.debug("Finaliza crearAfiliado(AfiliadoInDTO)");
			inDTO.setIdAfiliado(afiliado.getIdAfiliado());
			return inDTO;
		} catch (NoResultException nre) {
			logger.debug("Finaliza crearAfiliado(AfiliadoInDTO): No hay recursos suficientes para crear un afiliado");
			return null;
		} catch (Exception e) {
			logger.error("No es posible realizar la creacion del afiliado", e);
			logger.debug("Finaliza crearAfiliado(AfiliadoInDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * Metodo encargado de buscar el empleador por el Id
	 * 
	 *            id del empleador a buscar
	 * @return retorna el empleador en caso de ser encontrador
	 */
	private Empleador buscarEmpleadorId(Long idEmpleador) {
		try {
			Empleador empleador = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_ID, Empleador.class)
					.setParameter("idEmpleador", idEmpleador).getSingleResult();
			// Verificacion que se encuentre el empleador
			return empleador;
		} catch (NoResultException nre) {
			logger.debug("Finaliza buscarEmpleadorId(Long idEmpleador): No se encuentra el recurso solicitado");
			return null;
		} catch (Exception e) {
			logger.error("No es posible buscar el empleador", e);
			logger.debug("Finaliza buscarEmpleadorId(Long idEmpleador)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * Mtodo encargado de crear una Persona
	 *	
	 * @return persona creada
	 */
	private Persona crearPersona(AfiliadoInDTO inDTO) {
		Persona persona = new Persona();
		if (inDTO.getPersona().getTipoIdentificacion() != null) {
			persona.setTipoIdentificacion(inDTO.getPersona().getTipoIdentificacion());
		}
		if (inDTO.getPersona().getNumeroIdentificacion() != null
				&& !inDTO.getPersona().getNumeroIdentificacion().isEmpty()) {
			persona.setNumeroIdentificacion(inDTO.getPersona().getNumeroIdentificacion());
		}
		if (inDTO.getPersona().getPrimerNombre() != null && !inDTO.getPersona().getPrimerNombre().isEmpty()) {
			persona.setPrimerNombre(inDTO.getPersona().getPrimerNombre());
		}
		if (inDTO.getPersona().getSegundoNombre() != null && !inDTO.getPersona().getSegundoNombre().isEmpty()) {
			persona.setSegundoNombre(inDTO.getPersona().getSegundoNombre());
		}
		if (inDTO.getPersona().getPrimerApellido() != null && !inDTO.getPersona().getPrimerApellido().isEmpty()) {
			persona.setPrimerApellido(inDTO.getPersona().getPrimerApellido());
		}
		if (inDTO.getPersona().getSegundoApellido() != null && !inDTO.getPersona().getSegundoApellido().isEmpty()) {
			persona.setSegundoApellido(inDTO.getPersona().getSegundoApellido());
		}
		if (inDTO.getPersona().getRazonSocial() != null && !inDTO.getPersona().getRazonSocial().isEmpty()) {
			persona.setRazonSocial(inDTO.getPersona().getRazonSocial());
		}
		if (inDTO.getPersona().getUbicacionDTO() != null) {
			// Se crea la ubicacin de la persona
			Ubicacion ubicacion = new Ubicacion();
			ubicacion.setDireccionFisica(inDTO.getPersona().getUbicacionDTO().getDireccion());
			ubicacion.setDescripcionIndicacion(inDTO.getPersona().getUbicacionDTO().getDescripcionIndicacion());
			ubicacion.setAutorizacionEnvioEmail(inDTO.getPersona().getUbicacionDTO().getAutorizacionEnvioEmail());
			ubicacion.setCodigoPostal(inDTO.getPersona().getUbicacionDTO().getCodigoPostal());
			ubicacion.setEmail(inDTO.getPersona().getUbicacionDTO().getCorreoElectronico());
			ubicacion.setTelefonoFijo(inDTO.getPersona().getUbicacionDTO().getTelefonoFijo());
			ubicacion.setTelefonoCelular(inDTO.getPersona().getUbicacionDTO().getTelefonoCelular());

			// Si viene de Afiliacion Web, que no sea trabajador
			if (!inDTO.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
					&& inDTO.getCanalRecepcion().equals(CanalRecepcionEnum.WEB)
					&& inDTO.getPersona().getUbicacionDTO().getCorreoElectronico() != null) {
				ubicacion.setAutorizacionEnvioEmail(Boolean.TRUE);
			}

			if (inDTO.getPersona().getUbicacionDTO().getIdMunicipio() != null) {
				Municipio municipio = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_MUNICIPIO_ID, Municipio.class)
						.setParameter("idMunicipio", inDTO.getPersona().getUbicacionDTO().getIdMunicipio())
						.getSingleResult();
				ubicacion.setMunicipio(municipio);
			}
			entityManager.persist(ubicacion);

			// Se configurar la ubicacin en la persona a crear
			persona.setUbicacionPrincipal(ubicacion);
		}
		entityManager.persist(persona);
		return persona;
	}

	/**
	 * Mtodo encargado de crear una Persona Detalle
	 *	 
	 *            afiliado in dto
	 * @return personaDetalle creada
	 */
	private PersonaDetalle crearPersonaDetalle(AfiliadoInDTO inDTO) {
		PersonaDetalle personaDetalle = new PersonaDetalle();
		if (inDTO.getPersona().getIdPersonaDetalle() != null) {
			personaDetalle.setIdPersonaDetalle(inDTO.getPersona().getIdPersonaDetalle());
		}
		if (inDTO.getPersona().getIdPersona() != null) {
			personaDetalle.setIdPersona(inDTO.getPersona().getIdPersona());
		}
		if (inDTO.getPersona().getFechaNacimiento() != null) {
			personaDetalle.setFechaNacimiento(new Date(inDTO.getPersona().getFechaNacimiento()));
		}
		if (inDTO.getPersona().getFechaExpedicionDocumento() != null) {
			personaDetalle.setFechaExpedicionDocumento(inDTO.getPersona().getFechaExpedicionDocumento());
		}
		if (inDTO.getPersona().getGenero() != null) {
			personaDetalle.setGenero(inDTO.getPersona().getGenero());
		}
		if (inDTO.getPersona().getIdOcupacionProfesion() != null) {
			personaDetalle.setIdOcupacionProfesion(inDTO.getPersona().getIdOcupacionProfesion());
		}
		if (inDTO.getPersona().getNivelEducativo() != null) {
			personaDetalle.setNivelEducativo(inDTO.getPersona().getNivelEducativo());
		}
		if (inDTO.getPersona().getCabezaHogar() != null) {
			personaDetalle.setCabezaHogar(inDTO.getPersona().getCabezaHogar());
		}
		if (inDTO.getPersona().getHabitaCasaPropia() != null) {
			personaDetalle.setHabitaCasaPropia(inDTO.getPersona().getHabitaCasaPropia());
		}
		if (inDTO.getPersona().getAutorizaUsoDatosPersonales() != null) {
			personaDetalle.setAutorizaUsoDatosPersonales(inDTO.getPersona().getAutorizaUsoDatosPersonales());
		}
		if (inDTO.getPersona().getResideSectorRural() != null) {
			personaDetalle.setResideSectorRural(inDTO.getPersona().getResideSectorRural());
		}
		if (inDTO.getPersona().getEstadoCivil() != null) {
			personaDetalle.setEstadoCivil(inDTO.getPersona().getEstadoCivil());
		}
		if (inDTO.getPersona().getFallecido() != null) {
			personaDetalle.setFallecido(inDTO.getPersona().getFallecido());
		}
		if (inDTO.getPersona().getGradoAcademico() != null) {
			personaDetalle.setGradoAcademico(inDTO.getPersona().getGradoAcademico());
		}
		if(inDTO.getPersona().getOrientacionSexual() != null){
			personaDetalle.setOrientacionSexual(inDTO.getPersona().getOrientacionSexual());
        }
        if(inDTO.getPersona().getFactorVulnerabilidad()!= null){
			personaDetalle.setFactorVulnerabilidad(inDTO.getPersona().getFactorVulnerabilidad());
        }
        if(inDTO.getPersona().getPertenenciaEtnica()!= null){
			personaDetalle.setPertenenciaEtnica(inDTO.getPersona().getPertenenciaEtnica());
        }
        if(inDTO.getPersona().getIdPaisResidencia()!= null){
			personaDetalle.setIdPaisResidencia(inDTO.getPersona().getIdPaisResidencia());
        }
		if(personaDetalle.getIdPersonaDetalle() != null){
			logger.info("personaDetalle.getIdPersonaDetalle");
			entityManager.merge(personaDetalle);
		}
		else{
			logger.info("personaDetalle.getIdPersonaDetalle");
			entityManager.persist(personaDetalle);
		}

		return personaDetalle;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#actualizarAfiliado(java.lang.Long,
	 *      com.asopagos.dto.AfiliadoInDTO)
	 */
	@Override
	public void actualizarAfiliado(Long idAfiliado, AfiliadoInDTO inDTO) {
		try {
			logger.debug("Inicia actualizarAfiliado(Long, AfiliadoInDTO)");
			Afiliado afiliado = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_ID_AFILIADO, Afiliado.class)
					.setParameter("idAfiliado", idAfiliado).getSingleResult();

			if (inDTO.getPersona() != null) {
				PersonaDetalle personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_ID_PERSONA, PersonaDetalle.class)
						.setParameter("idPersona", afiliado.getPersona().getIdPersona()).getSingleResult();
				if (inDTO.getPersona().getTipoIdentificacion() != null) {
					afiliado.getPersona().setTipoIdentificacion(inDTO.getPersona().getTipoIdentificacion());
				}
				if (inDTO.getPersona().getNumeroIdentificacion() != null
						&& !inDTO.getPersona().getNumeroIdentificacion().isEmpty()) {
					afiliado.getPersona().setNumeroIdentificacion(inDTO.getPersona().getNumeroIdentificacion());
				}
				if (inDTO.getPersona().getPrimerNombre() != null && !inDTO.getPersona().getPrimerNombre().isEmpty()) {
					afiliado.getPersona().setPrimerNombre(inDTO.getPersona().getPrimerNombre());
				}
				if (inDTO.getPersona().getSegundoNombre() != null && !inDTO.getPersona().getSegundoNombre().isEmpty()) {
					afiliado.getPersona().setSegundoNombre(inDTO.getPersona().getSegundoNombre());
				}
				if (inDTO.getPersona().getPrimerApellido() != null
						&& !inDTO.getPersona().getPrimerApellido().isEmpty()) {
					afiliado.getPersona().setPrimerApellido(inDTO.getPersona().getPrimerApellido());
				}
				if (inDTO.getPersona().getSegundoApellido() != null
						&& !inDTO.getPersona().getSegundoApellido().isEmpty()) {
					afiliado.getPersona().setSegundoApellido(inDTO.getPersona().getSegundoApellido());
				}
				if (inDTO.getPersona().getFechaNacimiento() != null) {
					personaDetalle.setFechaNacimiento(new Date(inDTO.getPersona().getFechaNacimiento()));
				}
				entityManager.merge(afiliado.getPersona());
			}

			RolAfiliado rolAfiliado = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID, RolAfiliado.class)
					.setParameter("idRolAfiliado", inDTO.getIdRolAfiliado()).getSingleResult();

			if (rolAfiliado != null) {
				if (!BigDecimalUtils.isNullOrEmpty(inDTO.getValorSalarioMesada())) {
					rolAfiliado.setValorSalarioMesadaIngresos(inDTO.getValorSalarioMesada());
				}
				if (inDTO.getFechaAfiliacion() != null) {
					rolAfiliado.setFechaAfiliacion(inDTO.getFechaAfiliacion());
				}
				entityManager.merge(rolAfiliado);
			}
			logger.debug("Finaliza actualizarAfiliado(Long, AfiliadoInDTO)");
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza actualizarAfiliado(Long, AfiliadoInDTO):No se pudo actualizar el afiliado, recursos no encontrados");
		} catch (Exception e) {
			logger.error("No es posible realizar la actualizacion del afiliado", e);
			logger.debug("Finaliza actualizarAfiliado(Long, AfiliadoInDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarTrabajadoresEmpleador(java.lang.Long,
     *      com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TrabajadorEmpleadorDTO> consultarTrabajadoresEmpleador(Long idEmpleador, EstadoAfiliadoEnum estado, Long idEmpresa) {
        logger.debug(Interpolator.interpolate("Inicia consultarTrabajadoresEmpleador({0}, {1}, {2})", idEmpleador, estado, idEmpresa));
        if (idEmpresa == null && idEmpleador == null) {
            return Collections.emptyList();
        }
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPRESA_EMPLEADOR_ESTADO,
                        TrabajadorEmpleadorDTO.class)
                .setParameter("idEmpleador", idEmpleador).setParameter("idEmpresa", idEmpresa).setParameter("estado", estado)
                .getResultList();
    }


	@Override
	public List<TrabajadorEmpleadorDTO> consultarTrabajadoresEmpleador2(Long idEmpleador, Long idEmpresa, UriInfo uri, HttpServletResponse response){
				Integer offset = Integer.parseInt(uri.getQueryParameters().get(PaginationQueryParamsEnum.OFFSET.getValor()).get(0));
		Integer limit = Integer.parseInt(uri.getQueryParameters().get(PaginationQueryParamsEnum.LIMIT.getValor()).get(0));
		String orderBy = uri.getQueryParameters().get(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor()) != null ? uri.getQueryParameters().get(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor()).get(0) : "";
		logger.debug(Interpolator.interpolate("Inicia consultarTrabajadoresEmpleador2({0}, {1})", idEmpleador, idEmpresa));
		if (idEmpleador == null) {
			return Collections.emptyList();
		}
		String nombreComercial = "";
		List<TrabajadorEmpleadorDTO> trabajadoresAfiliadosDTO = new ArrayList<TrabajadorEmpleadorDTO>(); 
		try {
			StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_TRABAJADORES_EMPRESA_BY_EMPLEADOR);
			query.setParameter("idEmpleador", idEmpleador);
			query.setParameter("offset", offset);
            query.setParameter("limit", limit);
			query.setParameter("orderBy", orderBy);
			query.execute();
			trabajadoresAfiliadosDTO =  query.getResultList();
		} catch (Exception e) {
			logger.info(" :: Hubo un error en el SP: " + e);
		} 
		if (trabajadoresAfiliadosDTO != null && trabajadoresAfiliadosDTO.size() > 0) {
			nombreComercial = trabajadoresAfiliadosDTO.get(0).getNombreSucursalEmpleador();
			idEmpresa = trabajadoresAfiliadosDTO.get(0).getIdEmpresa();
			response.addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(), trabajadoresAfiliadosDTO.get(0).getTotalRegistros());
		}
		

	/* 	List<TrabajadorEmpleadorDTO> trabajadoresAportesfiliados = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_APORTES_NO_AFILIADOS,
						TrabajadorEmpleadorDTO.class)
				.setParameter("idEmpleador", idEmpleador)
				.getResultList();*/

		// List<TrabajadorEmpleadorDTO> trabajadoresAportesfiliados = new ArrayList<TrabajadorEmpleadorDTO>();
		// try {
		// StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_APORTES_NO_AFILIADOS);
		// query.setParameter("idEmpleador", idEmpleador);
		// query.execute();
		// trabajadoresAportesfiliados =  query.getResultList();
		// Long trabajadoresAfiliadosSP = Calendar.getInstance().getTimeInMillis();
		// logger.info("trabajadoresAfiliadosSP"+ (trabajadoresAfiliadosSP - tiempoInicial));
		// } catch (Exception e) {
		// 	logger.info(" :: Hubo un error en el SP: " + e);
		// } 	

		// Se ajusta los datos traidos por query
		// fechaIngreso, fechaRetiro, idEmpresa, nombreComercial
		// for (int i = 0; i < trabajadoresAfiliadosDTO.size(); i++) {
		// 	trabajadoresAfiliadosDTO.get(i).setNombreSucursalEmpleador(nombreComercial);
		// 	trabajadoresAfiliadosDTO.get(i).setFechaIngreso(null);
		// 	trabajadoresAfiliadosDTO.get(i).setFechaRetiro(null);
		// 	trabajadoresAfiliadosDTO.get(i).setIdEmpresa(idEmpresa);
		// }
		// trabajadoresAfiliadosDTO.addAll(trabajadoresAportesfiliados);
		return trabajadoresAfiliadosDTO;
	}

	@Override
	public Integer consultarTrabajadoresEmpleador2Conteo(Long idEmpleador, Long idEmpresa){
		logger.debug(Interpolator.interpolate("Inicia consultarTrabajadoresEmpleador2({0}, {1})", idEmpleador, idEmpresa));
		if (idEmpleador == null) {
			return 0;
		}

		Integer trabajadoresAfiliados = 0;
		try {
			trabajadoresAfiliados = (Integer) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_AFILIADOS_CONTEO_ACTIVO)
					.setParameter("idEmpleador", idEmpleador)
					.getSingleResult();
		} catch (Exception e) {
			logger.error("Error al consultar los trabajadores afiliados", e);
		}
		return trabajadoresAfiliados;
	}

    /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#guardarDatosIdentificacionYUbicacion(com.asopagos.dto.IdentificacionUbicacionPersonaDTO)
	 */
	@Override
	public void guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO inDTO) {
		try {
			logger.debug("Inicia guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO)");
			boolean update = true;
			Persona persona = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION, Persona.class)
					.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
					.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
					.getSingleResult();
			PersonaDetalle personaDetalle = null;
			try {
				personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
								PersonaDetalle.class)
						.setParameter("tipoIdentificacion", persona.getTipoIdentificacion())
						.setParameter("numeroIdentificacion", persona.getNumeroIdentificacion()).getSingleResult();
			} catch (NoResultException nre) {
				personaDetalle = null;
			}
			if (inDTO.getAutorizacionUsoDatosPersonales() != null) {
				personaDetalle.setAutorizaUsoDatosPersonales(inDTO.getAutorizacionUsoDatosPersonales());
				inDTO.getPersona().setAutorizaUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
			}
			if (inDTO.getResideSectorRural() != null) {
				personaDetalle.setResideSectorRural(inDTO.getResideSectorRural());
				inDTO.getPersona().setResideSectorRural(inDTO.getResideSectorRural());
			}

			List<Object> respuesta = crearActualizarPersona(inDTO.getPersona(), persona, personaDetalle);
			persona = (Persona) respuesta.get(0);
			personaDetalle = (PersonaDetalle) respuesta.get(1);
			// persona = crearActualizarPersona(inDTO.getPersona(), persona,
			// personaDetalle);

			if (inDTO.getMedioDePago() != null) {
				MedioDePagoModeloDTO medioDePagoModeloDTO = inDTO.getMedioDePago();
				PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
				personaModeloDTO.convertToDTO(persona, personaDetalle);
				/* Se asocia la persona al Medio de Pago. */
				medioDePagoModeloDTO.setPersona(personaModeloDTO);
				/* Se actualiza el medio de pago asociado. */
				this.actualizarMedioDePagoPersona(medioDePagoModeloDTO);
			}

			Ubicacion ubicacion = persona.getUbicacionPrincipal();
			if (ubicacion == null) {
				ubicacion = new Ubicacion();
				update = false;
			}

			if (inDTO.getUbicacion() != null) {
				if (inDTO.getUbicacion().getIdMunicipio() != null) {
					Municipio municipio = new Municipio();
					municipio.setIdMunicipio(inDTO.getUbicacion().getIdMunicipio());
					ubicacion.setMunicipio(municipio);
				}
				if (inDTO.getUbicacion().getDireccion() != null && !inDTO.getUbicacion().getDireccion().isEmpty()) {
					ubicacion.setDireccionFisica(inDTO.getUbicacion().getDireccion());
				}
				if (inDTO.getUbicacion().getDescripcionIndicacion() != null
						&& !inDTO.getUbicacion().getDescripcionIndicacion().isEmpty()) {
					ubicacion.setDescripcionIndicacion(inDTO.getUbicacion().getDescripcionIndicacion());
				}
				if (inDTO.getUbicacion().getCodigoPostal() != null
						&& !inDTO.getUbicacion().getCodigoPostal().isEmpty()) {
					ubicacion.setCodigoPostal(inDTO.getUbicacion().getCodigoPostal());
				}
				if (inDTO.getUbicacion().getTelefonoCelular() != null
						&& !inDTO.getUbicacion().getTelefonoCelular().isEmpty()) {
					ubicacion.setTelefonoCelular(inDTO.getUbicacion().getTelefonoCelular());
				}
				if (inDTO.getUbicacion().getIndicativoTelefonoFijo() != null
						&& !inDTO.getUbicacion().getIndicativoTelefonoFijo().isEmpty()) {
					ubicacion.setIndicativoTelFijo(inDTO.getUbicacion().getIndicativoTelefonoFijo());
				}
				if (inDTO.getUbicacion().getTelefonoFijo() != null
						&& !inDTO.getUbicacion().getTelefonoFijo().isEmpty()) {
					ubicacion.setTelefonoFijo(inDTO.getUbicacion().getTelefonoFijo());
				}
				if (inDTO.getUbicacion().getCorreoElectronico() != null
						&& !inDTO.getUbicacion().getCorreoElectronico().isEmpty()) {
					ubicacion.setEmail(inDTO.getUbicacion().getCorreoElectronico());
				}
				if (inDTO.getPersona().getAutorizacionEnvioEmail() != null) {
					ubicacion.setAutorizacionEnvioEmail(inDTO.getPersona().getAutorizacionEnvioEmail());
				}
			}
			if (validarUbicacion(ubicacion)){
			    persona.setUbicacionPrincipal(ubicacion);
			    if (update) {
	                if(persona.getUbicacionPrincipal() != null) {
	                    logger.info("UBI: Inicia guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO) persona.getUbicacionPrincipal() " + persona.getUbicacionPrincipal().getIdUbicacion());
	                }
	                entityManager.merge(persona.getUbicacionPrincipal());
	            } else {
	                entityManager.persist(persona.getUbicacionPrincipal());
	            }    
			}

			if (inDTO.getIdRolAfiliado() != null) {
				RolAfiliado rolAfiliado = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID, RolAfiliado.class)
						.setParameter("idRolAfiliado", inDTO.getIdRolAfiliado()).getSingleResult();

				if (rolAfiliado != null) {
					if (inDTO.getValorMesadaSalarioIngresos() != null) {
						rolAfiliado.setValorSalarioMesadaIngresos(inDTO.getValorMesadaSalarioIngresos());
					}
					if (inDTO.getIdPagadorPension() != null) {
						AFP pagadorPension = new AFP();
						pagadorPension.setIdAFP(inDTO.getIdPagadorPension());
						rolAfiliado.setPagadorPension(pagadorPension);
					}
					if (inDTO.getIdEntidadPagadora() != null) {
						EntidadPagadora entidadPagadora = new EntidadPagadora();
						entidadPagadora.setIdEntidadPagadora(inDTO.getIdEntidadPagadora());
						rolAfiliado.setPagadorAportes(entidadPagadora);
					}
					if (inDTO.getIdentificadorAnteEntidadPagadora() != null
							&& !inDTO.getIdentificadorAnteEntidadPagadora().isEmpty()) {
						rolAfiliado.setIdentificadorAnteEntidadPagadora(inDTO.getIdentificadorAnteEntidadPagadora());
					}
					if (inDTO.getClaseIndependiente() != null) {
						rolAfiliado.setClaseIndependiente(inDTO.getClaseIndependiente());
					}
					if (inDTO.getPorcentajeAportes() != null) {
						rolAfiliado.setPorcentajePagoAportes(inDTO.getPorcentajeAportes());
					}
					if (inDTO.getOportunidadPago() != null) {
						rolAfiliado.setOportunidadPago(inDTO.getOportunidadPago());
					}
					if(inDTO.getMunicipioDesempenioLabores() != null){
						rolAfiliado.setIdMunicipioDesempenioLabores(inDTO.getMunicipioDesempenioLabores());
					}
					RolAfiliado managedRolAfiliado = entityManager.merge(rolAfiliado);

					if (managedRolAfiliado.getTipoAfiliado().equals(TipoAfiliadoEnum.PENSIONADO)
							|| managedRolAfiliado.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)) {
						// Se realiza el reconocimiento de aportes "Retroactivo
						// Automtico" cuando se activa la persona independiente
						// o pensionada -> HU-262
						ejecutarRetroactivoAutomaticoPersona(persona.getIdPersona(),
								managedRolAfiliado.getTipoAfiliado());
					}
				}
			}

			logger.debug("Finaliza guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO)");
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO):No se pudo guardar, son insuficientes los recursos");
		} catch (Exception e) {
			logger.error("No es posible guardar los datos de identificacion y ubicacion", e);
			logger.debug("Finaliza guardarDatosIdentificacionYUbicacion(IdentificacionUbicacionPersonaDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
     * @param ubicacion
     * @return
     */
    private boolean validarUbicacion(Ubicacion ubicacion) {
        return !(ubicacion.getDireccionFisica() == null && ubicacion.getCodigoPostal() == null && ubicacion.getTelefonoFijo() == null
                && ubicacion.getIndicativoTelFijo() == null && ubicacion.getTelefonoCelular() == null && ubicacion.getEmail() == null
                && ubicacion.getAutorizacionEnvioEmail() == null && ubicacion.getMunicipio() == null
                && ubicacion.getDescripcionIndicacion() == null);
    }

    /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#guardarInformacionLaboral(com.asopagos.dto.InformacionLaboralTrabajadorDTO)
	 */
	@Override
	public void guardarInformacionLaboral(InformacionLaboralTrabajadorDTO inDTO) {
		try {
			logger.debug("Inicia guardarInformacionLaboral(InformacionLaboralTrabajadorDTO)");
			boolean update = false;
			List<RolAfiliado> rolesAfiliado = null;
			SucursalEmpresa sucursalEmpleador = null;
			RolAfiliado rolAfiliado = null;
			if (inDTO.getIdRolAfiliado() != null) {
				rolAfiliado = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID, RolAfiliado.class)
						.setParameter("idRolAfiliado", inDTO.getIdRolAfiliado()).getSingleResult();
				if (inDTO.getIdSucursalEmpleador() != null) {
					sucursalEmpleador = new SucursalEmpresa();
					sucursalEmpleador.setIdSucursalEmpresa(inDTO.getIdSucursalEmpleador());
				}
			}
			if (rolAfiliado == null) {
				if (inDTO.getIdSucursalEmpleador() != null) {
					rolesAfiliado = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_SUCURSAL, RolAfiliado.class)
							.setParameter("idEmpleador", inDTO.getIdEmpleador())
							.setParameter("idSucursalEmpleador", inDTO.getIdSucursalEmpleador())
							.setParameter("tipoIdentificacion", inDTO.getTipoIdentificacion())
							.setParameter("numeroIdentificacion", inDTO.getNumeroIdentificacion()).getResultList();
					sucursalEmpleador = new SucursalEmpresa();
					sucursalEmpleador.setIdSucursalEmpresa(inDTO.getIdSucursalEmpleador());
				} else {
					rolesAfiliado = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO, RolAfiliado.class)
							.setParameter("idEmpleador", inDTO.getIdEmpleador())
							.setParameter("tipoIdentificacion", inDTO.getTipoIdentificacion())
							.setParameter("numeroIdentificacion", inDTO.getNumeroIdentificacion()).getResultList();
				}
			}
			List<Afiliado> afiliados = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_TIPO_NUMERO_DOCUMENTO, Afiliado.class)
					.setParameter("tipoIdentificacion", inDTO.getTipoIdentificacion())
					.setParameter("numeroIdentificacion", inDTO.getNumeroIdentificacion()).getResultList();

			Afiliado afiliado = afiliados.iterator().next();
			Empleador empleador = null;

			if (inDTO.getIdEmpleador() != null) {
				empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_ID)
						.setParameter("idEmpleador", inDTO.getIdEmpleador()).getSingleResult();
			}

			if (rolAfiliado == null && (rolesAfiliado == null || rolesAfiliado.isEmpty())) {
				rolAfiliado = new RolAfiliado();
			} else {
				if (rolAfiliado == null) {
					rolAfiliado = rolesAfiliado.iterator().next();
				}
				update = true;
			}

			rolAfiliado.setSucursalEmpleador(sucursalEmpleador);
			rolAfiliado.setEmpleador(empleador);
			rolAfiliado.setAfiliado(afiliado);

			if (inDTO.getTipoAfiliado() != null) {
				rolAfiliado.setTipoAfiliado(inDTO.getTipoAfiliado());
			}
			if (inDTO.getEstadoAfiliado() != null) {

				// Se hace la trasformacion del estado del afiliado antes de
				// guardar ya que solo se permiten los valore ACTIVO e INACTIVO
				if (EstadoAfiliadoEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES.equals(inDTO.getEstadoAfiliado())) {
					rolAfiliado.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
					rolAfiliado.setCanalReingreso(null);
					rolAfiliado.setReferenciaAporteReingreso(null);
					rolAfiliado.setReferenciaSolicitudReingreso(null);
				} else if (EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION.equals(inDTO.getEstadoAfiliado())
						|| EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES
								.equals(inDTO.getEstadoAfiliado())) {
					rolAfiliado.setEstadoAfiliado(null);
				} else {
					rolAfiliado.setEstadoAfiliado(inDTO.getEstadoAfiliado());
				}
			}
			if (inDTO.getClaseTrabajador() != null) {
				rolAfiliado.setClaseTrabajador(inDTO.getClaseTrabajador());
			}
			if (inDTO.getFechaInicioContrato() != null) {
				rolAfiliado.setFechaIngreso(inDTO.getFechaInicioContrato());
			}
			if (inDTO.getFechaFinContrato() != null) {
				rolAfiliado.setFechaFinContrato(inDTO.getFechaFinContrato());
			}
			if (inDTO.getTipoSalario() != null) {
				rolAfiliado.setTipoSalario(inDTO.getTipoSalario());
			}
			if (inDTO.getValorSalario() != null) {
				rolAfiliado.setValorSalarioMesadaIngresos(inDTO.getValorSalario());
			}
			if (inDTO.getHorasLaboradasMes() != null) {
				rolAfiliado.setHorasLaboradasMes(inDTO.getHorasLaboradasMes());
			}
			if (inDTO.getCargo() != null && !inDTO.getCargo().isEmpty()) {
				rolAfiliado.setCargo(inDTO.getCargo());
			}
			if (inDTO.getTipoContrato() != null) {
				rolAfiliado.setTipoContrato(inDTO.getTipoContrato());
			}
			rolAfiliado.setIdMunicipioDesempenioLabores(inDTO.getMunicipioDesempenioLabores());
			rolAfiliado.setFechaInicioCondicionVet(inDTO.getFechaInicioCondicionVet());
			rolAfiliado.setFechaFinCondicionVet(inDTO.getFechaFinCondicionVet());
			if (update) {
				entityManager.merge(rolAfiliado);
			} else {
				entityManager.persist(rolAfiliado);
			}

			logger.debug("Finaliza guardarInformacionLaboral(InformacionLaboralTrabajadorDTO)");
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza guardarInformacionLaboral(InformacionLaboralTrabajadorDTO): No se pudo guardar la informacion laboral, no se encuentran los recursos necesarios");
		} catch (Exception e) {
			logger.error("No es posible guardar la informacion laboral", e);
			logger.debug("Finaliza guardarInformacionLaboral(InformacionLaboralTrabajadorDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarTarjetasAfiliado(java.lang.Long,
	 *      com.asopagos.enumeraciones.core.EstadoTarjetaEnum)
	 */
	@Override
	public List<String> consultarTarjetasAfiliado(Long idAfiliado, EstadoTarjetaEnum estado) {
		try {
			logger.debug("Inicia consultarTarjetasAfiliado(Long,EstadoTarjetaEnum)");
			List<String> tarjetas = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_TARJETAS_AFILIADO, String.class)
					.setParameter("idAfiliado", idAfiliado).setParameter("estado", estado).getResultList();

			logger.debug("Fin consultarTarjetasAfiliado(Long,EstadoTarjetaEnum)");
			return tarjetas;
		} catch (NoResultException nre) {
			logger.debug(
					"Fin consultarTarjetasAfiliado(Long,EstadoTarjetaEnum): No se encontraron los recursos solicitados");
			return null;
		} catch (Exception e) {
			logger.error("No es posible consultar las tarjetas del afiliado", e);
			logger.debug("Fin consultarTarjetasAfiliado(Long,EstadoTarjetaEnum)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarGrupoFamiliar(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoFamiliarDTO> consultarGrupoFamiliar(Long idAfiliado) {
		try {
			logger.debug("Inicia consultarGrupoFamiliar(Long)");
			List<GrupoFamiliar> gruposFamiliares = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR_AFILIADO, GrupoFamiliar.class)
					.setParameter("idAfiliado", idAfiliado).getResultList();

			List<GrupoFamiliarDTO> gruposFamiliaresDTO = new ArrayList<>();
			for (GrupoFamiliar grupoFamiliar : gruposFamiliares) {
				Persona persona = grupoFamiliar.getAfiliado().getPersona();
				GrupoFamiliarDTO grupoFamiliarDTO = new GrupoFamiliarDTO();
				UbicacionDTO ubicacionDTO = UbicacionDTO.obtenerUbicacionDTO(grupoFamiliar.getUbicacion());

				/*
				 * Consulta el Administrador del Subsidio asociado al Grupo
				 * Familiar.
				 */
				List<Object[]> datosAdmonSubsidio = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_ADMONSUBSIDIO_ASOCIADO_A_GRUPOFAMILIAR)
						.setParameter("idGrupoFamiliar", grupoFamiliar.getIdGrupoFamiliar())
						.setParameter("medioActivo", Boolean.TRUE).getResultList();
				if (datosAdmonSubsidio != null && !datosAdmonSubsidio.isEmpty()) {
					Object[] datoAdmonSubsidio = datosAdmonSubsidio.get(0);
					/*
					 * Se asignan los datos de la persona Administrador del
					 * Subsidio.
					 */
					Persona admonSubsidio = (Persona) datoAdmonSubsidio[0];
					AdminSubsidioGrupo adminSubsidioGrupo = (AdminSubsidioGrupo) datoAdmonSubsidio[1];
					MedioDePago medioDePago = (MedioDePago) datoAdmonSubsidio[2];

					// Obtener PersonaDetalle del Administrador de Subsidio
					PersonaDetalle personaDetalleAdmonSubsidio = null;
					try {
						personaDetalleAdmonSubsidio = entityManager
								.createNamedQuery(
										NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
										PersonaDetalle.class)
								.setParameter("tipoIdentificacion", admonSubsidio.getTipoIdentificacion())
								.setParameter("numeroIdentificacion", admonSubsidio.getNumeroIdentificacion())
								.getSingleResult();
					} catch (NoResultException nre) {
						personaDetalleAdmonSubsidio = null;
					}

					DatosBasicosIdentificacionDTO administradorSubsidio = new DatosBasicosIdentificacionDTO();
					PersonaDTO personaDTO = PersonaDTO.convertPersonaToDTO(admonSubsidio, personaDetalleAdmonSubsidio);
					administradorSubsidio.setPersona(personaDTO);
					grupoFamiliarDTO.setAdministradorSubsidio(administradorSubsidio);
					grupoFamiliarDTO
							.setIdRelacionAdministradorSubsidio(adminSubsidioGrupo.getIdRelacionGrupoFamiliar());
					grupoFamiliarDTO
							.setAfiliadoAdministradorSubsidio(adminSubsidioGrupo.getAfiliadoEsAdministradorSubsidio());

					MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
					medioDePagoModeloDTO.convertToDTO(medioDePago);

					/* Se asocia el Medio de Pago al Grupo Familiar. */
					grupoFamiliarDTO.setMedioDePagoModeloDTO(medioDePagoModeloDTO);
                                        }

				PersonaDetalle personaDetalle = null;
				try {
					personaDetalle = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
									PersonaDetalle.class)
							.setParameter("tipoIdentificacion", persona.getTipoIdentificacion())
							.setParameter("numeroIdentificacion", persona.getNumeroIdentificacion()).getSingleResult();
				} catch (NoResultException nre) {
					personaDetalle = null;
				}
				if (personaDetalle != null) {
					grupoFamiliarDTO.setAutorizacionUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
				}
				grupoFamiliarDTO.setUbicacion(ubicacionDTO);
				grupoFamiliarDTO.setIdGrupoFamiliar(grupoFamiliar.getIdGrupoFamiliar());
				grupoFamiliarDTO.setNumero(grupoFamiliar.getNumero());
				grupoFamiliarDTO.setInembargable(grupoFamiliar.getInembargable());
				gruposFamiliaresDTO.add(grupoFamiliarDTO);
			}
			logger.debug("Finaliza consultarGrupoFamiliar(Long)");
			return gruposFamiliaresDTO;
		} catch (NoResultException nre) {
			logger.debug("Finaliza consultarGrupoFamiliar(Long): No se encontraron los recursos solicitados");
			return null;
		} catch (Exception e) {
			logger.error("No es posible consultar el grupo familiar", e);
			logger.debug("Finaliza consultarGrupoFamiliar(Long)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#crearGrupoFamiliar(java.lang.Long,
	 *      com.asopagos.dto.GrupoFamiliarDTO)
	 */
	@Override
	public GrupoFamiliarDTO crearGrupoFamiliar(Long idAfiliado, GrupoFamiliarDTO grupoFamiliarDTO) {
		try {
			logger.debug("Inicia crearGrupoFamiliar(Long,GrupoFamiliarDTO)");

			GrupoFamiliar grupoFamiliar = new GrupoFamiliar();
			if (grupoFamiliarDTO.getUbicacion() != null) {

				Ubicacion ubicacion = new Ubicacion();

				if (grupoFamiliarDTO.getUbicacion().getIdMunicipio() != null) {
					Municipio municipio = entityManager.find(Municipio.class,
							grupoFamiliarDTO.getUbicacion().getIdMunicipio());
					ubicacion.setMunicipio(municipio);
				}

				if (grupoFamiliarDTO.getUbicacion().getAutorizacionEnvioEmail() != null) {
					ubicacion.setAutorizacionEnvioEmail(grupoFamiliarDTO.getUbicacion().getAutorizacionEnvioEmail());
				}
				if (grupoFamiliarDTO.getUbicacion().getCodigoPostal() != null
						&& !grupoFamiliarDTO.getUbicacion().getCodigoPostal().isEmpty()) {
					ubicacion.setCodigoPostal(grupoFamiliarDTO.getUbicacion().getCodigoPostal());
				}
				if (grupoFamiliarDTO.getUbicacion().getDireccion() != null
						&& !grupoFamiliarDTO.getUbicacion().getDireccion().isEmpty()) {
					ubicacion.setDireccionFisica(grupoFamiliarDTO.getUbicacion().getDireccion());
				}
				if (grupoFamiliarDTO.getUbicacion().getDescripcionIndicacion() != null
						&& !grupoFamiliarDTO.getUbicacion().getDescripcionIndicacion().isEmpty()) {
					ubicacion.setDescripcionIndicacion(grupoFamiliarDTO.getUbicacion().getDescripcionIndicacion());
				}
				if (grupoFamiliarDTO.getUbicacion().getCorreoElectronico() != null
						&& !grupoFamiliarDTO.getUbicacion().getCorreoElectronico().isEmpty()) {
					ubicacion.setEmail(grupoFamiliarDTO.getUbicacion().getCorreoElectronico());
				}
				if (grupoFamiliarDTO.getUbicacion().getIndicativoTelefonoFijo() != null
						&& !grupoFamiliarDTO.getUbicacion().getIndicativoTelefonoFijo().isEmpty()) {
					ubicacion.setIndicativoTelFijo(grupoFamiliarDTO.getUbicacion().getIndicativoTelefonoFijo());
				}
				if (grupoFamiliarDTO.getUbicacion().getTelefonoCelular() != null
						&& !grupoFamiliarDTO.getUbicacion().getTelefonoCelular().isEmpty()) {
					ubicacion.setTelefonoCelular(grupoFamiliarDTO.getUbicacion().getTelefonoCelular());
				}
				if (grupoFamiliarDTO.getUbicacion().getTelefonoFijo() != null
						&& !grupoFamiliarDTO.getUbicacion().getTelefonoFijo().isEmpty()) {
					ubicacion.setTelefonoFijo(grupoFamiliarDTO.getUbicacion().getTelefonoFijo());
				}
				if (grupoFamiliarDTO.getUbicacion().getSectorUbicacion() != null) {
                    ubicacion.setSectorUbicacion(grupoFamiliarDTO.getUbicacion().getSectorUbicacion());
                }
				grupoFamiliar.setUbicacion(ubicacion);
				entityManager.persist(grupoFamiliar.getUbicacion());
			}

			Afiliado afiliado = new Afiliado();
			afiliado.setIdAfiliado(idAfiliado);

			grupoFamiliar.setAfiliado(afiliado);

			if (grupoFamiliarDTO.getObservaciones() != null && !grupoFamiliarDTO.getObservaciones().isEmpty()) {
				grupoFamiliar.setObservaciones(grupoFamiliarDTO.getObservaciones());
			}

			if (grupoFamiliarDTO.getInembargable() != null) {
				grupoFamiliar.setInembargable(grupoFamiliarDTO.getInembargable());
			}
			Integer numero;
			try {
				numero = entityManager
						.createNamedQuery(NamedQueriesConstants.RETORNAR_NUMERO_CONSECUTIVO_GRUPO_FAMILIAR,
								Integer.class)
						.setParameter("idAfiliado", idAfiliado).getSingleResult();
				if (numero != null) {
					grupoFamiliar.setNumero(numero.byteValue());
				} else {
					grupoFamiliar.setNumero(new Byte("1"));
				}
			} catch (NoResultException nre) {
				grupoFamiliar.setNumero(new Byte("1"));
			}

			entityManager.persist(grupoFamiliar);
			logger.info("id grupo familiar: " + grupoFamiliar.getIdGrupoFamiliar());

			grupoFamiliarDTO.setIdGrupoFamiliar(grupoFamiliar.getIdGrupoFamiliar());
			grupoFamiliarDTO.setNumero(grupoFamiliar.getNumero());

			/*
			 * Se crea el medio de Pago asociado al Grupo Familiar y
			 * Administrador del subsidio
			 */
			if (grupoFamiliarDTO.getMedioDePagoModeloDTO() != null
					&& grupoFamiliarDTO.getMedioDePagoModeloDTO().getTipoMedioDePago() != null) {
				MedioDePagoModeloDTO medioDePagoModeloDTO = grupoFamiliarDTO.getMedioDePagoModeloDTO();
				medioDePagoModeloDTO.setIdGrupoFamiliar(grupoFamiliar.getIdGrupoFamiliar());
				if (grupoFamiliarDTO.getAdministradorSubsidio() != null
						&& grupoFamiliarDTO.getAdministradorSubsidio().getPersona() != null
						&& grupoFamiliarDTO.getAdministradorSubsidio().getPersona().getNumeroIdentificacion() != null
						&& grupoFamiliarDTO.getAdministradorSubsidio().getPersona().getTipoIdentificacion() != null) {
					PersonaDTO personaDTO = grupoFamiliarDTO.getAdministradorSubsidio().getPersona();
					PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
					personaModeloDTO.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
					personaModeloDTO.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
					personaModeloDTO.setPrimerNombre(personaDTO.getPrimerNombre());
					personaModeloDTO.setSegundoNombre(personaDTO.getSegundoNombre());
					personaModeloDTO.setPrimerApellido(personaDTO.getPrimerApellido());
					personaModeloDTO.setSegundoApellido(personaDTO.getSegundoApellido());
					personaModeloDTO.setFechaNacimiento(personaDTO.getFechaNacimiento());
					medioDePagoModeloDTO.setAdmonSubsidio(personaModeloDTO);
					medioDePagoModeloDTO
							.setAfiliadoEsAdministradorSubsidio(grupoFamiliarDTO.getAfiliadoAdministradorSubsidio());
				}
				if (grupoFamiliarDTO.getIdRelacionAdministradorSubsidio() != null) {
					medioDePagoModeloDTO
							.setIdRelacionGrupoFamiliar(grupoFamiliarDTO.getIdRelacionAdministradorSubsidio());
				}
				this.actualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO);
			}

			logger.debug("Finaliza crearGrupoFamiliar(Long,GrupoFamiliarDTO)");
			return grupoFamiliarDTO;
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza crearGrupoFamiliar(Long,GrupoFamiliarDTO):No se pudo crear el grupo familiar, recursos no encontrados");
			return null;
		} catch (Exception e) {
			logger.error("No es posible crear el grupo familiar", e);
			logger.debug("Finaliza crearGrupoFamiliar(Long,GrupoFamiliarDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#actualizarGrupoFamiliar(java.lang.Long,
	 *      com.asopagos.dto.GrupoFamiliarDTO)
	 */
	@Override
	public void actualizarGrupoFamiliar(Long idAfiliado, GrupoFamiliarDTO grupoFamiliarDTO) {
		logger.debug("Inicia actualizarGrupoFamiliar(Long,GrupoFamiliarDTO)");
		GrupoFamiliar grupoFamiliar = null;
		// Afiliado afiliado =
		// entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_ID_AFILIADO,
		// Afiliado.class)
		// .setParameter("idAfiliado", idAfiliado).getSingleResult();

		if (grupoFamiliarDTO.getIdGrupoFamiliar() != null) {
			grupoFamiliar = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR_ID, GrupoFamiliar.class)
					.setParameter("idGrupoFamiliar", grupoFamiliarDTO.getIdGrupoFamiliar()).getSingleResult();
		} else {
			List<GrupoFamiliar> grupos = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR_AFILIADO_NUMERO, GrupoFamiliar.class)
					.setParameter("idAfiliado", idAfiliado).setParameter("numero", grupoFamiliarDTO.getNumero())
					.getResultList();
			if (grupos != null && !grupos.isEmpty()) {
				grupoFamiliar = grupos.iterator().next();
			}
		}

		Ubicacion ubicacion = grupoFamiliar.getUbicacion();
		if (grupoFamiliarDTO.getUbicacion() != null) {
			boolean actualizaUbicacion = true;
			if (ubicacion == null) {
				ubicacion = new Ubicacion();
				actualizaUbicacion = false;
			}
			if (grupoFamiliarDTO.getUbicacion().getIdMunicipio() != null) {
				Municipio municipio = new Municipio();
				municipio.setIdMunicipio(grupoFamiliarDTO.getUbicacion().getIdMunicipio());
				ubicacion.setMunicipio(municipio);
			} else {

				ubicacion.setMunicipio(null);

			}

			ubicacion.setDireccionFisica(grupoFamiliarDTO.getUbicacion().getDireccion());
			ubicacion.setDescripcionIndicacion(grupoFamiliarDTO.getUbicacion().getDescripcionIndicacion());

			ubicacion.setCodigoPostal(grupoFamiliarDTO.getUbicacion().getCodigoPostal());

			ubicacion.setTelefonoFijo(grupoFamiliarDTO.getUbicacion().getTelefonoFijo());

			if (grupoFamiliarDTO.getUbicacion().getIndicativoTelefonoFijo() != null
					&& !grupoFamiliarDTO.getUbicacion().getIndicativoTelefonoFijo().isEmpty()) {
				ubicacion.setIndicativoTelFijo(grupoFamiliarDTO.getUbicacion().getIndicativoTelefonoFijo());
			}
			if (grupoFamiliarDTO.getUbicacion().getTelefonoCelular() != null
					&& !grupoFamiliarDTO.getUbicacion().getTelefonoCelular().isEmpty()) {
				ubicacion.setTelefonoCelular(grupoFamiliarDTO.getUbicacion().getTelefonoCelular());
			}
			if (grupoFamiliarDTO.getUbicacion().getCorreoElectronico() != null
					&& !grupoFamiliarDTO.getUbicacion().getCorreoElectronico().isEmpty()) {
				ubicacion.setEmail(grupoFamiliarDTO.getUbicacion().getCorreoElectronico());
			}

			if (grupoFamiliarDTO.getUbicacion().getAutorizacionEnvioEmail() != null) {
				ubicacion.setAutorizacionEnvioEmail(grupoFamiliarDTO.getUbicacion().getAutorizacionEnvioEmail());
			}
			if (grupoFamiliarDTO.getUbicacion().getSectorUbicacion() != null) {
                ubicacion.setSectorUbicacion(grupoFamiliarDTO.getUbicacion().getSectorUbicacion());
            }
			grupoFamiliar.setUbicacion(ubicacion);
			if (actualizaUbicacion) {
				entityManager.merge(grupoFamiliar.getUbicacion());
			} else {
				entityManager.persist(grupoFamiliar.getUbicacion());
			}
		}

		/*
		 * Se actualiza el medio de Pago asociado al Grupo Familiar y
		 * Administrador del subsidio
		 */
		if (grupoFamiliarDTO.getMedioDePagoModeloDTO() != null
				&& grupoFamiliarDTO.getMedioDePagoModeloDTO().getTipoMedioDePago() != null) {
			MedioDePagoModeloDTO medioDePagoModeloDTO = grupoFamiliarDTO.getMedioDePagoModeloDTO();
			medioDePagoModeloDTO.setIdGrupoFamiliar(grupoFamiliar.getIdGrupoFamiliar());
			if (grupoFamiliarDTO.getAdministradorSubsidio() != null
					&& grupoFamiliarDTO.getAdministradorSubsidio().getPersona() != null
					&& grupoFamiliarDTO.getAdministradorSubsidio().getPersona().getNumeroIdentificacion() != null
					&& grupoFamiliarDTO.getAdministradorSubsidio().getPersona().getTipoIdentificacion() != null) {
				PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
				PersonaDTO personaDTO = grupoFamiliarDTO.getAdministradorSubsidio().getPersona();
				personaModeloDTO.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
				personaModeloDTO.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
				personaModeloDTO.setPrimerNombre(personaDTO.getPrimerNombre());
				personaModeloDTO.setSegundoNombre(personaDTO.getSegundoNombre());
				personaModeloDTO.setPrimerApellido(personaDTO.getPrimerApellido());
				personaModeloDTO.setSegundoApellido(personaDTO.getSegundoApellido());
				personaModeloDTO.setFechaNacimiento(personaDTO.getFechaNacimiento());
				medioDePagoModeloDTO.setAdmonSubsidio(personaModeloDTO);
				medioDePagoModeloDTO
						.setAfiliadoEsAdministradorSubsidio(grupoFamiliarDTO.getAfiliadoAdministradorSubsidio());
			}
			if (grupoFamiliarDTO.getIdRelacionAdministradorSubsidio() != null) {
				medioDePagoModeloDTO.setIdRelacionGrupoFamiliar(grupoFamiliarDTO.getIdRelacionAdministradorSubsidio());
			}
			this.actualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO);
		}

		if (grupoFamiliarDTO.getObservaciones() != null && !grupoFamiliarDTO.getObservaciones().isEmpty()) {
			grupoFamiliar.setObservaciones(grupoFamiliarDTO.getObservaciones());
		}
		if (grupoFamiliarDTO.getInembargable() != null) {
			grupoFamiliar.setInembargable(grupoFamiliarDTO.getInembargable());
		}

		entityManager.merge(grupoFamiliar);
	}

	/**
	 * Metodo encargado de actualizar o crear persona
	 * 
	 *            Persona DTO	 
	 *            Persona a actualizar
	 * @return Persona creada o actualizada
	 */
	private List<Object> crearActualizarPersona(PersonaDTO personaDTO, Persona pers, PersonaDetalle persDetalle) {
		Persona persona = pers;
		PersonaDetalle personaDetalle = persDetalle;
		if (persona == null) {
			persona = new Persona();
		}
		if (personaDetalle == null) {
			personaDetalle = new PersonaDetalle();
		}

		try {
			if (persona.getTipoIdentificacion() != null && persona.getNumeroIdentificacion() != null) {
				Persona personaBD = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
								Persona.class)
						.setParameter("tipoIdentificacion", persona.getTipoIdentificacion())
						.setParameter("numeroIdentificacion", persona.getNumeroIdentificacion()).getSingleResult();

				persona = personaDTO.obtenerDatosPersona(personaBD);
			} else {
				persona = personaDTO.obtenerDatosPersona(persona);
			}
		} catch (NoResultException e) {
			persona = personaDTO.obtenerDatosPersona(persona);
		}
		try {
			if (persona.getIdPersona() != null) {
				PersonaDetalle personaDetalleBD = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE_ID_PERSONA,
								PersonaDetalle.class)
						.setParameter("idPersona", persona.getIdPersona()).getSingleResult();

				personaDetalle = personaDTO.obtenerDatosPersonaDetalle(personaDetalleBD);
			} else {
				personaDetalle = personaDTO.obtenerDatosPersonaDetalle(personaDetalle);
			}
		} catch (NoResultException e) {
			personaDetalle = personaDTO.obtenerDatosPersonaDetalle(personaDetalle);
		}

		if (pers != null && pers.getIdPersona() != null) {
			entityManager.merge(persona);
			if (persDetalle != null && persDetalle.getIdPersonaDetalle() != null) {
				entityManager.merge(personaDetalle);
			} else {
				personaDetalle.setIdPersona(persona.getIdPersona());
				entityManager.persist(personaDetalle);
			}
		} else {
			entityManager.persist(persona);
			if (persDetalle != null && persDetalle.getIdPersonaDetalle() != null) {
				entityManager.merge(personaDetalle);
			} else {
				personaDetalle.setIdPersona(persona.getIdPersona());
				entityManager.persist(personaDetalle);
			}
		}
		List<Object> respuesta = new ArrayList<Object>();
		respuesta.add(persona);
		respuesta.add(personaDetalle);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#eliminarGrupoFamiliar(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public void eliminarGrupoFamiliar(Long idAfiliado, Long idGrupoFamiliar) {
		try {
			logger.debug("Inicia eliminarGrupoFamiliar(Long,Long)");
			GrupoFamiliar grupoFamiliar = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR, GrupoFamiliar.class)
					.setParameter("idAfiliado", idAfiliado).setParameter("idGrupoFamiliar", idGrupoFamiliar)
					.getSingleResult();

			if (grupoFamiliar.getUbicacion() != null) {
				entityManager.remove(grupoFamiliar.getUbicacion());
			}

			entityManager.remove(grupoFamiliar);
			logger.debug("Finaliza eliminarGrupoFamiliar(Long,Long)");
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza consultarTarjetasAfiliado(Long,EstadoTarjetaEnum): No existe el grupo familiar a eliminar");
		} catch (Exception e) {
			logger.error("No es posible eliminar el grupo familiar", e);
			logger.debug("Finaliza consultarTarjetasAfiliado(Long,EstadoTarjetaEnum)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarBeneficiario(java.lang.Long,
	 *      com.asopagos.dto.BeneficiarioDTO)
	 */
	@Override
	public Long registrarBeneficiario(Long idAfiliado, BeneficiarioDTO beneficiarioDTO) {
		try {
			logger.debug("Inicia registrarBeneficiario(Long, BeneficiarioDTO)");
			// si es true se asigna la fecha de afiliacin al empleador
			boolean auxFechaAfiliacion = false;
			boolean ingresoPersona = false;
			boolean ingresoBeneficiario = false;
			boolean ingresoInvaliz = false;
			boolean ingresoDetalle = false;
			boolean ingresoBenDetalle = false;
			Beneficiario beneficiario = null;
			Persona persona = null;
			PersonaDetalle personaDetalle = null;
			CondicionInvalidez condiInvalidez = null;
			BeneficiarioDetalle benDetalle = null;
			CertificadoEscolarBeneficiario certificadoEscolarBeneficiario = new CertificadoEscolarBeneficiario();

			// Se asume que si el beneficiario existe en el servicio se envia el
			// identificador
			// if (beneficiarioDTO.getIdBeneficiario() != null) {
			// }
			try {
				beneficiario = entityManager
						.createNamedQuery(
								NamedQueriesConstants.BUSCAR_BENEFICIARIO_NUMERO_TIPO_IDENTIFICACION_ID_AFILIADO,
								Beneficiario.class)
						.setParameter("numeroIdentificacion", beneficiarioDTO.getPersona().getNumeroIdentificacion())
						.setParameter("tipoIdentificacion", beneficiarioDTO.getPersona().getTipoIdentificacion())
						.setParameter("idAfiliado", idAfiliado).getSingleResult();
				persona = beneficiario.getPersona();

			} catch (NoResultException e) {
				beneficiario = new Beneficiario();
				auxFechaAfiliacion = true;
				ingresoBeneficiario = true;
				if (beneficiarioDTO.getIdRolAfiliado() != null) {
					beneficiario.setIdRolAfiliado(beneficiarioDTO.getIdRolAfiliado());
				}
			}
			try {
				condiInvalidez = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_CONDICION_INVALIDEZ_BENEFICIARIO,
								CondicionInvalidez.class)
						.setParameter("tipoIdentificacion", beneficiarioDTO.getPersona().getTipoIdentificacion())
						.setParameter("numeroIdentificacion", beneficiarioDTO.getPersona().getNumeroIdentificacion())
						.getSingleResult();
			} catch (NoResultException e) {
				condiInvalidez = new CondicionInvalidez();
				ingresoInvaliz = true;
			}

			try {
				persona = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
								Persona.class)
						.setParameter("tipoIdentificacion", beneficiarioDTO.getPersona().getTipoIdentificacion())
						.setParameter("numeroIdentificacion", beneficiarioDTO.getPersona().getNumeroIdentificacion())
						.getSingleResult();
			} catch (NoResultException nre) {
				persona = new Persona();
				ingresoPersona = true;
			}

			try {
				personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
								PersonaDetalle.class)
						.setParameter("tipoIdentificacion", persona.getTipoIdentificacion())
						.setParameter("numeroIdentificacion", persona.getNumeroIdentificacion()).getSingleResult();

				benDetalle = consultarDatosBeneficiarioDetalle(personaDetalle.getIdPersonaDetalle());
				if (benDetalle == null) {
					benDetalle = new BeneficiarioDetalle();
					ingresoBenDetalle = true;
				}
			} catch (NoResultException nre) {
				benDetalle = new BeneficiarioDetalle();
				ingresoBenDetalle = true;
				personaDetalle = new PersonaDetalle();
				ingresoDetalle = true;
			}
			if (beneficiarioDTO.getPersona().getTipoIdentificacion() != null) {
				persona.setTipoIdentificacion(beneficiarioDTO.getPersona().getTipoIdentificacion());
			}
			if (beneficiarioDTO.getPersona().getNumeroIdentificacion() != null
					&& !beneficiarioDTO.getPersona().getNumeroIdentificacion().isEmpty()) {
				persona.setNumeroIdentificacion(beneficiarioDTO.getPersona().getNumeroIdentificacion());
			}
			if (beneficiarioDTO.getPersona().getPrimerNombre() != null
					&& !beneficiarioDTO.getPersona().getPrimerNombre().isEmpty()) {
				persona.setPrimerNombre(beneficiarioDTO.getPersona().getPrimerNombre());
			}
			if (beneficiarioDTO.getPersona().getSegundoNombre() != null
					&& !beneficiarioDTO.getPersona().getSegundoNombre().isEmpty()) {
				persona.setSegundoNombre(beneficiarioDTO.getPersona().getSegundoNombre());
			}
			if (beneficiarioDTO.getPersona().getPrimerApellido() != null
					&& !beneficiarioDTO.getPersona().getPrimerApellido().isEmpty()) {
				persona.setPrimerApellido(beneficiarioDTO.getPersona().getPrimerApellido());
			}
			if (beneficiarioDTO.getPersona().getSegundoApellido() != null
					&& !beneficiarioDTO.getPersona().getSegundoApellido().isEmpty()) {
				persona.setSegundoApellido(beneficiarioDTO.getPersona().getSegundoApellido());
			}
			if (beneficiarioDTO.getPersona().getFechaNacimiento() != null) {
				personaDetalle.setFechaNacimiento(new Date(beneficiarioDTO.getPersona().getFechaNacimiento()));
			}
			if (beneficiarioDTO.getPersona().getGradoAcademico() != null) {
				personaDetalle.setGradoAcademico(beneficiarioDTO.getPersona().getGradoAcademico());
			}
			if (beneficiarioDTO.getEstudianteTrabajoDesarrolloHumano() != null) {
				personaDetalle
						.setEstudianteTrabajoDesarrolloHumano(beneficiarioDTO.getEstudianteTrabajoDesarrolloHumano());
			}
			if (beneficiarioDTO.getPersona().getFechaExpedicionDocumento() != null) {
				personaDetalle
						.setFechaExpedicionDocumento(beneficiarioDTO.getPersona().getFechaExpedicionDocumento());
			}

			if (ingresoPersona) {
				entityManager.persist(persona);
				personaDetalle.setIdPersona(persona.getIdPersona());
				entityManager.persist(personaDetalle);
			} else {
				entityManager.merge(persona);
				if (ingresoDetalle) {
					personaDetalle.setIdPersona(persona.getIdPersona());
					entityManager.persist(personaDetalle);
				} else {
					entityManager.merge(personaDetalle);
				}
			}

			if (idAfiliado != null && beneficiarioDTO.getIdGrupoFamiliar() != null) {
				List<GrupoFamiliar> grupoFamiliar = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR, GrupoFamiliar.class)
						.setParameter("idAfiliado", idAfiliado)
						.setParameter("idGrupoFamiliar", beneficiarioDTO.getIdGrupoFamiliar()).getResultList();

				if (grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
					beneficiario.setGrupoFamiliar(grupoFamiliar.iterator().next());
				}
			}

			beneficiario.setPersona(persona);
			Afiliado afiliado = new Afiliado();
			afiliado.setIdAfiliado(idAfiliado);
			beneficiario.setAfiliado(afiliado);

			if (beneficiarioDTO.getInvalidez() != null) {
				condiInvalidez.setCondicionInvalidez(beneficiarioDTO.getInvalidez());
			}
			if (beneficiarioDTO.getFechaReporteInvalidez() != null) {
				condiInvalidez.setFechaReporteInvalidez(beneficiarioDTO.getFechaReporteInvalidez());
			}
			if (beneficiarioDTO.getComentariosInvalidez() != null
					&& !beneficiarioDTO.getComentariosInvalidez().isEmpty()) {
				condiInvalidez.setComentarioInvalidez(beneficiarioDTO.getComentariosInvalidez());
			}

			if (ingresoInvaliz) {
				if (condiInvalidez.getCondicionInvalidez() != null) {
					condiInvalidez.setIdPersona(persona.getIdPersona());
					entityManager.persist(condiInvalidez);
				}
			} else {
				entityManager.merge(condiInvalidez);
			}

			if (beneficiarioDTO.getCertificadoEscolaridad() != null) {
				benDetalle.setCertificadoEscolaridad(beneficiarioDTO.getCertificadoEscolaridad());
			}
			if (beneficiarioDTO.getFechaVencimientoCertificadoEscolar() != null) {
			    certificadoEscolarBeneficiario
						.setFechaVencimientoCertificadoEscolar(beneficiarioDTO.getFechaVencimientoCertificadoEscolar());
			}
			if (beneficiarioDTO.getFechaRecepcionCertificadoEscolar() != null) {
			    certificadoEscolarBeneficiario.setFechaRecepcionCertificadoEscolar(beneficiarioDTO.getFechaRecepcionCertificadoEscolar());
			}
			if (beneficiarioDTO.getLabora() != null) {
				benDetalle.setLabora(beneficiarioDTO.getLabora());
			}
			if (beneficiarioDTO.getSalarioMensualBeneficiario() != null) {
				benDetalle.setSalarioMensualBeneficiario(beneficiarioDTO.getSalarioMensualBeneficiario());
			}

			if (ingresoBenDetalle) {
				benDetalle.setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
				entityManager.persist(benDetalle);
				certificadoEscolarBeneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
			} else {
				entityManager.merge(benDetalle);
				certificadoEscolarBeneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
			}
			crearActualizarCertificadoEscolar(certificadoEscolarBeneficiario, persona.getIdPersona(), Boolean.FALSE);

			if (beneficiarioDTO.getResultadoValidacion() != null) {
				beneficiario.setEstadoBeneficiarioAfiliado(
						beneficiarioDTO.getResultadoValidacion().equals(ResultadoGeneralValidacionEnum.NO_AFILIABLE)
								? EstadoAfiliadoEnum.INACTIVO : EstadoAfiliadoEnum.ACTIVO);
			}
			if (beneficiarioDTO.getEstadoBeneficiarioAfiliado() != null) {
				beneficiario.setEstadoBeneficiarioAfiliado(beneficiarioDTO.getEstadoBeneficiarioAfiliado());
			}
			if (beneficiarioDTO.getEstudianteTrabajoDesarrolloHumano() != null) {
				beneficiario
						.setEstudianteTrabajoDesarrolloHumano(beneficiarioDTO.getEstudianteTrabajoDesarrolloHumano());
			}
			if (beneficiarioDTO.getFechaAfiliacion() != null) {
				beneficiario.setFechaAfiliacion(beneficiarioDTO.getFechaAfiliacion());
			}
			if (beneficiarioDTO.getTipoBeneficiario() != null) {
				beneficiario.setTipoBeneficiario(beneficiarioDTO.getTipoBeneficiario());
			}
			if (auxFechaAfiliacion && EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
				beneficiario.setFechaAfiliacion(new Date());
			}

			if (ingresoBeneficiario) {
				beneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
				entityManager.persist(beneficiario);
			} else {
				if (beneficiario.getIdBeneficiarioDetalle() == null) {
					beneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
				}
				entityManager.merge(beneficiario);
			}

			List<ItemChequeoDTO> listaChequeo = beneficiarioDTO.getListaChequeo();

			if (listaChequeo != null && !listaChequeo.isEmpty()) {
				for (ItemChequeoDTO itemChequeoDTO : listaChequeo) {
					ItemChequeo itemChequeo = new ItemChequeo();
					if (itemChequeoDTO.getIdRequisito() != null) {
						Requisito requisito = new Requisito();
						requisito.setIdRequisito(itemChequeoDTO.getIdRequisito());
						itemChequeo.setRequisito(requisito);
					}
					if (itemChequeoDTO.getIdSolicitudGlobal() != null) {
						Solicitud solicitud = new Solicitud();
						solicitud.setIdSolicitud(itemChequeoDTO.getIdSolicitudGlobal());
						itemChequeo.setSolicitudGlobal(solicitud);
					}
					else if(beneficiarioDTO.getIdSolicitud() != null) {
						Solicitud solicitud = new Solicitud();
						solicitud.setIdSolicitud(beneficiarioDTO.getIdSolicitud());
						itemChequeo.setSolicitudGlobal(solicitud);
					}
					itemChequeo.setPersona(persona);
					if (itemChequeoDTO.getComentarios() != null && !itemChequeoDTO.getComentarios().isEmpty()) {
						itemChequeo.setComentarios(itemChequeoDTO.getComentarios());
					}
					if (itemChequeoDTO.getComentariosBack() != null && !itemChequeoDTO.getComentariosBack().isEmpty()) {
						itemChequeo.setComentariosBack(itemChequeoDTO.getComentariosBack());
					}
					if (itemChequeoDTO.getCumpleRequisito() != null) {
						itemChequeo.setCumpleRequisito(itemChequeoDTO.getCumpleRequisito());
					}
					if (itemChequeoDTO.getEstadoRequisito() != null) {
						itemChequeo.setEstadoRequisito(itemChequeoDTO.getEstadoRequisito());
					}
					if (itemChequeoDTO.getFormatoEntregaDocumento() != null) {
						itemChequeo.setFormatoEntregaDocumento(itemChequeoDTO.getFormatoEntregaDocumento());
					}
					if (itemChequeoDTO.getIdentificadorDocumento() != null
							&& !itemChequeoDTO.getIdentificadorDocumento().isEmpty()) {
						itemChequeo.setIdentificadorDocumento(itemChequeoDTO.getIdentificadorDocumento());
					}
					if (itemChequeoDTO.getVersionDocumento() != null) {
						itemChequeo.setVersionDocumento(itemChequeoDTO.getVersionDocumento());
					}
					entityManager.persist(itemChequeo);
				}
			}
			logger.debug("Finaliza registrarBeneficiario(Long, BeneficiarioDTO)");
			return beneficiario.getIdBeneficiario();
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza registrarBeneficiario(Long, BeneficiarioDTO):No se pudo registrar beneficiario, recursos insuficientes");
			return null;
		} catch (Exception e) {
			logger.error("No es posible registrar el grupo familiar", e);
			logger.debug("Finaliza registrarBeneficiario(Long, BeneficiarioDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarBeneficiario(java.lang.Long,
	 *      com.asopagos.dto.BeneficiarioDTO)
	 */
	@Override
	public List<Long> registrarBeneficiarios(Long idAfiliado, List<BeneficiarioDTO> beneficiariosDTO) {
		List<Long> idsBeneficiarios = new ArrayList<Long>();
		if (!beneficiariosDTO.isEmpty()) {
			for (BeneficiarioDTO bene : beneficiariosDTO) {
				Long idBeneficiario = registrarBeneficiario(idAfiliado, bene);
				idsBeneficiarios.add(idBeneficiario);
			}
		}
		return idsBeneficiarios;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarInformacionBeneficiarioConyugue(java.lang.Long,
	 *      com.asopagos.dto.IdentificacionUbicacionPersonaDTO)
	 */
	@Override
	public Long registrarInformacionBeneficiarioConyugue(Long idAfiliado, IdentificacionUbicacionPersonaDTO inDTO) {
		try {
			logger.debug("Inicia registrarInformacionBeneficiarioConyugue(Long, IdentificacionUbicacionPersonaDTO)");
			// si es true se asigna la fecha de afiliacin al empleador
			boolean auxFechaAfiliacion = false;
			boolean ingresoPersona = false;
			boolean ingresoBeneficiario = false;
			boolean ingresoBenDetalle = false;
			Beneficiario beneficiario = null;
			Persona persona = null;
			PersonaDetalle personaDetalle = null;
			BeneficiarioDetalle benDetalle = null;
			boolean existeDiferenteAfiliado = false;
			// Se asume que si el beneficiario existe en el servicio se envia el
			// identificador
			if (inDTO.getIdBeneficiario() != null) {
				beneficiario = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ID_BENEFICIARIO, Beneficiario.class)
						.setParameter("idBeneficiario", inDTO.getIdBeneficiario()).getSingleResult();
				persona = beneficiario.getPersona();
			}
			else {
				List<Beneficiario> listBeneficiario = consultarBeneficiarioAsociadoAfiliadoTipoBeneficiario(idAfiliado, inDTO.getPersona().getTipoIdentificacion(),
					inDTO.getPersona().getNumeroIdentificacion(), ClasificacionEnum.CONYUGE);
				if(listBeneficiario != null && ! listBeneficiario.isEmpty()) {
					beneficiario = listBeneficiario.iterator().next();
				}
			}
			if(beneficiario != null){
				if(Long.toString(beneficiario.getAfiliado().getIdAfiliado()).equals(Long.toString(idAfiliado))) {
					existeDiferenteAfiliado = true;
				}else{
					existeDiferenteAfiliado = false;
				}
			}

			try {
				persona = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
								Persona.class)
						.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
						.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
						.getSingleResult();
			} catch (NoResultException nre) {
				persona = null;
				ingresoPersona = true;
			}

			try {
				personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
								PersonaDetalle.class)
						.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
						.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
						.getSingleResult();

				benDetalle = consultarDatosBeneficiarioDetalle(personaDetalle.getIdPersonaDetalle());
				if (benDetalle == null) {
					ingresoBenDetalle = true;
					benDetalle = new BeneficiarioDetalle();
				}
			} catch (NoResultException nre) {
				personaDetalle = null;
				ingresoBenDetalle = true;
				benDetalle = new BeneficiarioDetalle();
			}
			/*CCREPNORMATIVOS*/
//			if (personaDetalle != null) {
//				personaDetalle.setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
//			}
//			if (inDTO.getPersona() != null) {
//				inDTO.getPersona().setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
//			}

			List<Object> respuesta = crearActualizarPersona(inDTO.getPersona(), persona, personaDetalle);
			persona = (Persona) respuesta.get(0);
			personaDetalle = (PersonaDetalle) respuesta.get(1);
			// persona = crearActualizarPersona(inDTO.getPersona(), persona,
			// personaDetalle);

			if (inDTO.getUbicacion() != null) {
				Ubicacion ubicacion = null;
				boolean ingresoUbicacion = false;
				if (!ingresoPersona) {
					ubicacion = persona.getUbicacionPrincipal();
				}
				if (ubicacion == null) {
					ingresoUbicacion = true;
					ubicacion = new Ubicacion();
				}

				if (inDTO.getUbicacion().getIdMunicipio() != null) {
					Municipio municipio = new Municipio();
					municipio.setIdMunicipio(inDTO.getUbicacion().getIdMunicipio());
					ubicacion.setMunicipio(municipio);
				} else {
					ubicacion.setMunicipio(null);
				}

				ubicacion.setCodigoPostal(inDTO.getUbicacion().getCodigoPostal());

				ubicacion.setDireccionFisica(inDTO.getUbicacion().getDireccion());
				ubicacion.setDescripcionIndicacion(inDTO.getUbicacion().getDescripcionIndicacion());

				ubicacion.setEmail(inDTO.getUbicacion().getCorreoElectronico());

				if (inDTO.getUbicacion().getIndicativoTelefonoFijo() != null
						&& !inDTO.getUbicacion().getIndicativoTelefonoFijo().isEmpty()) {
					ubicacion.setIndicativoTelFijo(inDTO.getUbicacion().getIndicativoTelefonoFijo());
				}
				if (inDTO.getUbicacion().getTelefonoCelular() != null
						&& !inDTO.getUbicacion().getTelefonoCelular().isEmpty()) {
					ubicacion.setTelefonoCelular(inDTO.getUbicacion().getTelefonoCelular());
				}
				if (inDTO.getUbicacion().getTelefonoFijo() != null
						&& !inDTO.getUbicacion().getTelefonoFijo().isEmpty()) {
					ubicacion.setTelefonoFijo(inDTO.getUbicacion().getTelefonoFijo());
				}

				persona.setUbicacionPrincipal(ubicacion);

				if (ingresoUbicacion) {
					entityManager.persist(persona.getUbicacionPrincipal());
				} else {
					entityManager.merge(persona.getUbicacionPrincipal());
				}

			}

			if (beneficiario == null || !existeDiferenteAfiliado) {
				ingresoBeneficiario = true;
				auxFechaAfiliacion = true;
				beneficiario = new Beneficiario();
				if (inDTO.getIdRolAfiliado() != null) {
					beneficiario.setIdRolAfiliado(inDTO.getIdRolAfiliado());
				}
			}
			Afiliado afiliadoPrincipal = null;
			PersonaDetalle personaDetallePrincipal = null;
			if (idAfiliado != null) {
				try {
					afiliadoPrincipal = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_ID_AFILIADO, Afiliado.class)
							.setParameter("idAfiliado", idAfiliado).getSingleResult();
					if (afiliadoPrincipal != null) {
						try {
							personaDetallePrincipal = entityManager
									.createNamedQuery(
											NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
											PersonaDetalle.class)
									.setParameter("tipoIdentificacion",
											afiliadoPrincipal.getPersona().getTipoIdentificacion())
									.setParameter("numeroIdentificacion",
											afiliadoPrincipal.getPersona().getNumeroIdentificacion())
									.getSingleResult();
							if (personaDetallePrincipal != null) {
							    /*CCREPNORMATIVOS*/
//								personaDetallePrincipal.setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
								entityManager.merge(personaDetallePrincipal);
							}
						} catch (NoResultException nre) {
							personaDetallePrincipal = null;
						}
					}
				} catch (NoResultException e) {
					afiliadoPrincipal = null;
				}

			}

			if (idAfiliado != null && inDTO.getIdGrupoFamiliar() != null) {
				List<GrupoFamiliar> gruposFamiliar = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR, GrupoFamiliar.class)
						.setParameter("idAfiliado", idAfiliado)
						.setParameter("idGrupoFamiliar", inDTO.getIdGrupoFamiliar()).getResultList();
				if (gruposFamiliar != null && !gruposFamiliar.isEmpty()) {
					beneficiario.setGrupoFamiliar(gruposFamiliar.iterator().next());
				}
			}

			Afiliado afiliado = new Afiliado();
			afiliado.setIdAfiliado(idAfiliado);
			beneficiario.setAfiliado(afiliado);
			beneficiario.setPersona(persona);

			if (inDTO.getOmitirValidaciones()!=null){
			    beneficiario.setOmitirValidaciones(inDTO.getOmitirValidaciones());
			}
			
			if (inDTO.getResultadoValidacion() != null
					&& inDTO.getResultadoValidacion().equals(ResultadoGeneralValidacionEnum.NO_AFILIABLE)) {
				beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
			} else {
				if (inDTO.getPersona().getEstadoAfiliadoRol() != null) {
					beneficiario.setEstadoBeneficiarioAfiliado(inDTO.getPersona().getEstadoAfiliadoRol());
				} else {
					beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.ACTIVO);
				}
			}
			
			if (inDTO.getFechaAfiliacion() != null && EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
			    beneficiario.setFechaAfiliacion(new Date());
            }
			
			beneficiario.setTipoBeneficiario(ClasificacionEnum.CONYUGE);
			if (auxFechaAfiliacion && EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
				beneficiario.setFechaAfiliacion(new Date());
			}
			if (inDTO.getLabora() != null) {
				benDetalle.setLabora(inDTO.getLabora());
				// beneficiario.setLabora(inDTO.getLabora());
			}
			if (inDTO.getSalarioMensualBeneficiario() != null) {
				benDetalle.setSalarioMensualBeneficiario(inDTO.getSalarioMensualBeneficiario());
				// beneficiario.setSalarioMensualBeneficiario(inDTO.getSalarioMensualBeneficiario());
			}

			if (ingresoBenDetalle) {
				benDetalle.setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
				entityManager.persist(benDetalle);
			} else {
				entityManager.merge(benDetalle);
			}
			AfiliadoModeloDTO afiDto = consultarAfiliadoPorId(idAfiliado);
			boolean crearModificar = true;
			if (ingresoBeneficiario || !existeDiferenteAfiliado) {
				beneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
				entityManager.persist(beneficiario);
				logger.info("Entre-1-registrarInformacionBeneficiarioConyugue: " + crearModificar);
			} else {
				crearModificar = false;
				if (beneficiario.getIdBeneficiarioDetalle() == null) {
					beneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
				}
				if (EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
                    beneficiario.setFechaRetiro(null);
                    beneficiario.setMotivoDesafiliacion(null);
                }
				entityManager.merge(beneficiario);
				logger.info("Entre-2-registrarInformacionBeneficiarioConyugue: " + crearModificar);
			}
			if (EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
				CategoriaDTO categoria = new CategoriaDTO();
				categoria.setIdAfiliado(idAfiliado);
				categoria.setTipoIdentificacion(afiDto.getTipoIdentificacion());
				categoria.setNumeroIdentificacion(afiDto.getNumeroIdentificacion());
				categoria.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NUEVA_AFILIACION);
				calcularCategoriasAfiliado(categoria);
			}

			if (inDTO != null && inDTO.getListaChequeo() != null && !inDTO.getListaChequeo().isEmpty()) {
				//Ajuste glpi 32784
				for (ItemChequeoDTO listaChequeo : inDTO.getListaChequeo()) {
					if (listaChequeo != null && listaChequeo.getCumpleRequisito() != null
					&& listaChequeo.getCumpleRequisito().equals(Boolean.TRUE)
					&& listaChequeo.getIdentificadorDocumento() != null) {
						listaChequeo.setCumpleRequisitoBack(Boolean.TRUE);
					}
					
				}
			}

			crearItemsChequeo(inDTO.getListaChequeo(), persona, crearModificar);

			logger.debug("Finaliza registrarInformacionBeneficiarioConyugue(Long, IdentificacionUbicacionPersonaDTO)");
			return beneficiario.getIdBeneficiario();
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza registrarInformacionBeneficiarioConyugue(Long, IdentificacionUbicacionPersonaDTO):Recursos insuficientes para realizar el servicio");
			return null;
		} catch (Exception e) {
			logger.error("No es posible registrar la informacion del beneficiario conyugue", e);
			logger.debug("Finaliza registrarInformacionBeneficiarioConyugue(Long, IdentificacionUbicacionPersonaDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	private void crearItemsChequeo(List<ItemChequeoDTO> listaChequeo, Persona persona, boolean crear) {
		logger.debug(Interpolator.interpolate("Inicia crearItemsChequeo(List<ItemChequeoDTO>, {0}, {1})", persona.getIdPersona(), crear));
		if (listaChequeo != null && !listaChequeo.isEmpty()) {
            Long idSolicitud = obtenerIdSolicitud(listaChequeo);
            logger.debug(Interpolator.interpolate("crearItemsChequeo({0}) - idSolicitud: {1}", persona.getIdPersona(), idSolicitud));
            // Lista de item asociados a la persona en la solicitud
            List<ItemChequeo> listItemRegistrados = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_ITEM_CHEQUEO_POR_ID_SOLICITUD_ID_PERSONA, ItemChequeo.class)
                    .setParameter("idSolicitud", idSolicitud).setParameter("idPersona", persona.getIdPersona()).getResultList();
            // Se actualiza el item chequeo existe para el mismo requisito
			for (ItemChequeoDTO itemChequeo : listaChequeo) {
                Boolean actualizar = false;
                ItemChequeo item = null;
                if (!listItemRegistrados.isEmpty()) {
                    item = obtenerItemRequisitoExistente(listItemRegistrados, itemChequeo);
                }
                if (item == null) {
                    item = new ItemChequeo();
                }else {
                    actualizar = true;
                }
				if (itemChequeo.getIdSolicitudGlobal() != null) {
					Solicitud solicitud = new Solicitud();
					solicitud.setIdSolicitud(itemChequeo.getIdSolicitudGlobal());
					item.setSolicitudGlobal(solicitud);
				}
				if (itemChequeo.getIdRequisito() != null) {
					Requisito requisito = new Requisito();
					requisito.setIdRequisito(itemChequeo.getIdRequisito());
					item.setRequisito(requisito);
				}
				if (itemChequeo.getIdentificadorDocumento() != null
						&& !itemChequeo.getIdentificadorDocumento().isEmpty()) {
					item.setIdentificadorDocumento(itemChequeo.getIdentificadorDocumento());
				}
				if (itemChequeo.getVersionDocumento() != null) {
					item.setVersionDocumento(itemChequeo.getVersionDocumento());
				}
				if (itemChequeo.getFechaRecepcionDocumentos() != null) {
                    item.setFechaRecepcionDocumentos(new Date(itemChequeo.getFechaRecepcionDocumentos()));
                }
				if (itemChequeo.getEstadoRequisito() != null) {
					item.setEstadoRequisito(itemChequeo.getEstadoRequisito());
				}
				if (itemChequeo.getCumpleRequisito() != null) {
					item.setCumpleRequisito(itemChequeo.getCumpleRequisito());
				}
				if (itemChequeo.getFormatoEntregaDocumento() != null) {
					item.setFormatoEntregaDocumento(itemChequeo.getFormatoEntregaDocumento());
				}
				if (itemChequeo.getComentarios() != null && !itemChequeo.getComentarios().isEmpty()) {
					item.setComentarios(itemChequeo.getComentarios());
				}
				if (itemChequeo.getCumpleRequisitoBack() != null) {
					item.setCumpleRequisitoBack(itemChequeo.getCumpleRequisitoBack());
				}
				if (itemChequeo.getComentariosBack() != null && !itemChequeo.getComentariosBack().isEmpty()) {
					item.setComentariosBack(itemChequeo.getComentariosBack());
				}
				if (persona != null) {
					item.setPersona(persona);
				}
				/* Si crear es equivalente a true se procede a persistir */
				logger.debug(Interpolator.interpolate("crearItemsChequeo({0}) - requisito: {1}, actualiza: {2}", persona.getIdPersona(), item.getRequisito().getIdRequisito(), actualizar));
				if (crear && !actualizar) {
					entityManager.persist(item);
				} else {
					entityManager.merge(item);
				}
			}
		}
		logger.debug(Interpolator.interpolate("Finaliza crearItemsChequeo(List<ItemChequeoDTO>, {0}, {1})", persona.getIdPersona(), crear));
	}

    /**
     * Obtiene el numero de solicitud global que agrega la lista de chequeo
     * @param listaChequeo
     *        Lista de chequeo enviada desde pantalla
     * @return Identificador de la solicitud o <code>null</code> en caso de no encontrar el numero
     */
    private Long obtenerIdSolicitud(List<ItemChequeoDTO> listaChequeo) {
        Long idSolicitud = null;
        for (ItemChequeoDTO itemChequeoDTO : listaChequeo) {
            if (itemChequeoDTO.getIdSolicitudGlobal() != null) {
                idSolicitud = itemChequeoDTO.getIdSolicitudGlobal();
                break;
            }
        }
        return idSolicitud;
    }

    /**
     * Obtiene el item de chequeo registrado al mismo requisito
     * @param listItemRegistrados
     *        Lista de item de chequeo registrados
     * @param itemChequeo
     *        Item de chequeo a registra o actualizar
     * @return Item de chequeo a actualizar o null si no se encuentra relacin con el requisito
     */
    private ItemChequeo obtenerItemRequisitoExistente(List<ItemChequeo> listItemRegistrados, ItemChequeoDTO itemChequeo) {
        for (ItemChequeo itemChequeoRegistrado : listItemRegistrados) {
            if (itemChequeoRegistrado.getRequisito().getIdRequisito().equals(itemChequeo.getIdRequisito())) {
                return itemChequeoRegistrado;
            }
        }
        return null;
    }

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarInformacionBeneficiarioHijoPadre(java.lang.Long,
	 *      com.asopagos.dto.BeneficiarioHijoPadreDTO)
	 */
	@Override
	public Long registrarInformacionBeneficiarioHijoPadre(Long idAfiliado, BeneficiarioHijoPadreDTO inDTO) {
		try {
			logger.info("Inicia registrarInformacionBeneficiarioHijoPadre(Long, BeneficiarioHijoPadreDTO) con Datos: ");
			logger.info("idAfiliado = "+idAfiliado);
	        logger.info("inDTO = " + inDTO.toString());
			// si es true se asigna la fecha de afiliacin al empleador
			boolean auxFechaAfiliacion = false;
			boolean ingresoBeneficiario = false;
			boolean ingresoBenDetalle = false;
			Beneficiario beneficiario = null;
			Persona persona = null;
			CondicionInvalidez condicionInvalidez = null;
			BeneficiarioDetalle benDetalle = null;
			CertificadoEscolarBeneficiario certificadoEscolarBeneficiario = new CertificadoEscolarBeneficiario();
			boolean crearCondicion = false;
			boolean existeDiferenteAfiliado = false;
			// Se asume que si el beneficiario existe en el servicio se envia el
			// identificador
			if (inDTO.getIdBeneficiario() != null) {
				beneficiario = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ID_BENEFICIARIO, Beneficiario.class)
						.setParameter("idBeneficiario", inDTO.getIdBeneficiario()).getSingleResult();
				if (beneficiario == null) {
					logger.info(
							"Finaliza registrarBeneficiario(Long, BeneficiarioDTO):El beneficiario no se encuentra registrado");
					return null;
				}
				persona = beneficiario.getPersona();
			}
			else {
				List<Beneficiario> listBeneficiario = consultarBeneficiarioAsociadoAfiliadoTipoBeneficiario(idAfiliado, inDTO.getPersona().getTipoIdentificacion(),
					inDTO.getPersona().getNumeroIdentificacion(), inDTO.getTipoBeneficiario());
				if(listBeneficiario != null && ! listBeneficiario.isEmpty()) {
					beneficiario = listBeneficiario.iterator().next();
				}
			}

			if(beneficiario != null){
				if(Long.toString(beneficiario.getAfiliado().getIdAfiliado()).equals(Long.toString(idAfiliado))) {
					existeDiferenteAfiliado = true;
				}else{
					existeDiferenteAfiliado = false;
				}
			}
			try {
				persona = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
								Persona.class)
						.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
						.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
						.getSingleResult();
			} catch (NoResultException nre) {
				persona = null;
			}
			PersonaDetalle personaDetalle;
			try {
				personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
								PersonaDetalle.class)
						.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
						.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
						.getSingleResult();

				benDetalle = consultarDatosBeneficiarioDetalle(personaDetalle.getIdPersonaDetalle());
				if (benDetalle == null) {
					benDetalle = new BeneficiarioDetalle();
					ingresoBenDetalle = true;
				}

			} catch (NoResultException nre) {
				benDetalle = new BeneficiarioDetalle();
				ingresoBenDetalle = true;
				personaDetalle = null;
			}

			try {
				condicionInvalidez = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_CONDICION_INVALIDEZ_BENEFICIARIO,
								CondicionInvalidez.class)
						.setParameter("tipoIdentificacion", inDTO.getPersona().getTipoIdentificacion())
						.setParameter("numeroIdentificacion", inDTO.getPersona().getNumeroIdentificacion())
						.getSingleResult();
			} catch (NoResultException e) {
				condicionInvalidez = new CondicionInvalidez();
				crearCondicion = true;
			}

			if (inDTO.getIdGradoAcademico() != null && inDTO.getIdGradoAcademico() > NumerosEnterosConstants.CERO) {
				inDTO.getPersona().setGradoAcademico(inDTO.getIdGradoAcademico());
			}

			// Establecer condicion de estudiante desarrollo humano
			if (personaDetalle != null) {
				personaDetalle.setEstudianteTrabajoDesarrolloHumano(inDTO.getEstudianteTrabajoDesarrolloHumano());
			}

			List<Object> respuesta = crearActualizarPersona(inDTO.getPersona(), persona, personaDetalle);
			persona = (Persona) respuesta.get(0);
			personaDetalle = (PersonaDetalle) respuesta.get(1);
			// persona = crearActualizarPersona(inDTO.getPersona(), persona,
			// personaDetalle);
			if (beneficiario == null || !existeDiferenteAfiliado) {
				ingresoBeneficiario = true;
				auxFechaAfiliacion = true;
				beneficiario = new Beneficiario();
				if (inDTO.getIdRolAfiliado() != null) {
					beneficiario.setIdRolAfiliado(inDTO.getIdRolAfiliado());
				}
			}

			if (idAfiliado != null && inDTO.getIdGrupoFamiliar() != null) {
				List<GrupoFamiliar> grupoFamiliar = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR, GrupoFamiliar.class)
						.setParameter("idAfiliado", idAfiliado)
						.setParameter("idGrupoFamiliar", inDTO.getIdGrupoFamiliar()).getResultList();
				if (grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
					beneficiario.setGrupoFamiliar(grupoFamiliar.iterator().next());
				}
			}

			Afiliado afiliado = new Afiliado();
			afiliado.setIdAfiliado(idAfiliado);
			beneficiario.setAfiliado(afiliado);
			beneficiario.setPersona(persona);

			if (inDTO.getOmitirValidaciones()!=null){
                beneficiario.setOmitirValidaciones(inDTO.getOmitirValidaciones());
            }
			
			if (inDTO.getResultadoValidacion() != null
					&& inDTO.getResultadoValidacion().equals(ResultadoGeneralValidacionEnum.NO_AFILIABLE)) {
				beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
			} else {
				if (inDTO.getPersona().getEstadoAfiliadoRol() != null) {
					beneficiario.setEstadoBeneficiarioAfiliado(inDTO.getPersona().getEstadoAfiliadoRol());
				} else {
					beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.ACTIVO);
				}
			}

			if (inDTO.getCertificadoEscolaridad() != null) {
				benDetalle.setCertificadoEscolaridad(inDTO.getCertificadoEscolaridad());
				// beneficiario.setCertificadoEscolaridad(inDTO.getCertificadoEscolaridad());
			}
			if (inDTO.getFechaVencimientoCertificadoEscolar() != null) {
			    certificadoEscolarBeneficiario.setFechaVencimientoCertificadoEscolar(inDTO.getFechaVencimientoCertificadoEscolar());
				// beneficiario.setFechaVencimientoCertificadoEscolar(inDTO.getFechaVencimientoCertificadoEscolar());
			}
			if (inDTO.getFechaRecepcionCertificadoEscolar() != null) {
			    certificadoEscolarBeneficiario.setFechaRecepcionCertificadoEscolar(inDTO.getFechaRecepcionCertificadoEscolar());
				// beneficiario.setFechaRecepcionCertificadoEscolar(inDTO.getFechaRecepcionCertificadoEscolar());
			}
			if (inDTO.getEstudianteTrabajoDesarrolloHumano() != null) {
				beneficiario.setEstudianteTrabajoDesarrolloHumano(inDTO.getEstudianteTrabajoDesarrolloHumano());
			}
			if (inDTO.getTipoBeneficiario() != null) {
				beneficiario.setTipoBeneficiario(inDTO.getTipoBeneficiario());
			}
			if (inDTO.getInvalidez() != null) {
				condicionInvalidez.setCondicionInvalidez(inDTO.getInvalidez());
			}
			if (inDTO.getFechaReporteInvalidez() != null) {
				condicionInvalidez.setFechaReporteInvalidez(inDTO.getFechaReporteInvalidez());
			}
			if (inDTO.getFechaInicioInvalidez() != null) {
                condicionInvalidez.setFechaInicioInvalidez(inDTO.getFechaInicioInvalidez());
            }
			if (inDTO.getConyugeCuidador() != null) {
                condicionInvalidez.setConyugeCuidador(inDTO.getConyugeCuidador());
            }
            if (inDTO.getFechaInicioConyugeCuidador() != null) {
                condicionInvalidez.setFechaInicioConyugeCuidador(inDTO.getFechaInicioConyugeCuidador());
            }
            if (inDTO.getFechaFinConyugeCuidador() != null) {
                condicionInvalidez.setFechaFinConyugeCuidador(inDTO.getFechaFinConyugeCuidador());
            }
            if (inDTO.getIdConyugeCuidador() != null) {
                condicionInvalidez.setIdConyugeCuidador(inDTO.getIdConyugeCuidador());
            }
			if (inDTO.getComentariosInvalidez() != null && !inDTO.getComentariosInvalidez().isEmpty()) {
				condicionInvalidez.setComentarioInvalidez(inDTO.getComentariosInvalidez());
			}
			if (inDTO.getFechaAfiliacion() != null && EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
                //beneficiario.setFechaAfiliacion(inDTO.getFechaAfiliacion());
			    beneficiario.setFechaAfiliacion(new Date());
            }
			
			if (crearCondicion) {
				
				condicionInvalidez.setIdPersona(persona.getIdPersona());
				entityManager.persist(condicionInvalidez);
			} else {
				entityManager.merge(condicionInvalidez);
			}

			if (ingresoBenDetalle) {
				benDetalle.setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
				entityManager.persist(benDetalle);
				certificadoEscolarBeneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
			} else {
				entityManager.merge(benDetalle);
				certificadoEscolarBeneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
			}
			crearActualizarCertificadoEscolar(certificadoEscolarBeneficiario, persona.getIdPersona(), Boolean.TRUE);

			if (inDTO.getIdGradoAcademico() != null && inDTO.getIdGradoAcademico() > NumerosEnterosConstants.CERO) {
				beneficiario.setGradoAcademico(inDTO.getIdGradoAcademico());
			}
			if (inDTO.getIdGradoAcademico() != null && inDTO.getIdGradoAcademico() > NumerosEnterosConstants.CERO) {
				beneficiario.setGradoAcademico(inDTO.getIdGradoAcademico());
			}

			if (auxFechaAfiliacion && EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
				beneficiario.setFechaAfiliacion(new Date());
			}

			boolean crear = true;
			AfiliadoModeloDTO afiDto = consultarAfiliadoPorId(idAfiliado);
			if (ingresoBeneficiario || !existeDiferenteAfiliado) {
				logger.info("persist beneficiario");
				beneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
				entityManager.persist(beneficiario);
			} else {
				crear = false;
				if (beneficiario.getIdBeneficiarioDetalle() == null || benDetalle.getIdBeneficiarioDetalle() != null) {
					beneficiario.setIdBeneficiarioDetalle(benDetalle.getIdBeneficiarioDetalle());
				}
				if (EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
				    beneficiario.setFechaRetiro(null);
				    beneficiario.setMotivoDesafiliacion(null);
                }
				entityManager.merge(beneficiario);
			}
			//comentado por nuevo calculo en los triggers de beneficiario
			/*if (EstadoAfiliadoEnum.ACTIVO.equals(beneficiario.getEstadoBeneficiarioAfiliado())) {
				CategoriaDTO categoria = new CategoriaDTO();
				categoria.setIdAfiliado(idAfiliado);
				categoria.setTipoIdentificacion(afiDto.getTipoIdentificacion());
				categoria.setNumeroIdentificacion(afiDto.getNumeroIdentificacion());
				categoria.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NUEVA_AFILIACION);
				logger.info("**__**iniciocalcularCategoriasAfiliado en afiliados");
				calcularCategoriasAfiliado(categoria);
			}*/
			
			if(beneficiario != null){
				logger.info("el beneficiario registrado tiene el id: "+beneficiario.getIdBeneficiario()+" y el detalle del beneficiario tiene id: "+beneficiario.getIdBeneficiarioDetalle());
			}else{
				logger.info("no hay beneficiario ni detalle");
			}
			  
			if(persona != null){
				logger.info("la persona asociada al beneficiario tiene el id: "+persona.getIdPersona());
			}else{
				logger.info("no hay persona asociada al beneficario");
			}

			if (inDTO != null && inDTO.getListaChequeo() != null && !inDTO.getListaChequeo().isEmpty()) {
				//Ajuste glpi 32784
				for (ItemChequeoDTO listaChequeo : inDTO.getListaChequeo()) {
					if (listaChequeo != null && listaChequeo.getCumpleRequisito() != null
					&& listaChequeo.getCumpleRequisito().equals(Boolean.TRUE)
					&& listaChequeo.getIdentificadorDocumento() != null) {
						listaChequeo.setCumpleRequisitoBack(Boolean.TRUE);
					}
					
				}
			}
			
			crearItemsChequeo(inDTO.getListaChequeo(), persona, crear);

			logger.info("Finaliza registrarInformacionBeneficiarioHijoPadre(Long, BeneficiarioHijoPadreDTO)");
			return beneficiario.getIdBeneficiario();
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza registrarInformacionBeneficiarioHijoPadre(Long, BeneficiarioHijoPadreDTO): No se pudo registrar el beneficiario, recursos insuficientes");
			return null;
		} catch (Exception e) {
			logger.error("No es posible registrar la informacion del beneficiario hijo o padre", e);
			logger.debug("Finaliza registrarInformacionBeneficiarioHijoPadre(Long, BeneficiarioHijoPadreDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiariosSolicitud(java.lang.Long, java.lang.Boolean, java.lang.Long)
	 */
	@Override
	public List<BeneficiarioDTO> consultarBeneficiariosSolicitud(Long idAfiliado,Boolean sinDesafiliacion, Long idSolicitud) {
	    logger.debug("Se ejecuta el mtodo consultarBeneficiariosSolicitud(Long, Boolean, Long)");
	    return consultarListaBeneficiarios(idAfiliado, sinDesafiliacion, idSolicitud, false);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarios(java.lang.Long)
	 */
	@Override
	public List<BeneficiarioDTO> consultarBeneficiarios(Long idAfiliado,Boolean sinDesafiliacion) {
	    logger.debug("Se ejecuta el mtodo consultarBeneficiarios(Long, Boolean)");
	    return consultarListaBeneficiarios(idAfiliado, sinDesafiliacion, null, false);
	}
	@Override
	public List<BeneficiarioDTO> consultarBeneficiariosMismaFecha(Long idAfiliado,Boolean sinDesafiliacion) {
	    logger.debug("Se ejecuta el mtodo consultarBeneficiarios(Long, Boolean)");
	    return consultarListaBeneficiarios(idAfiliado, sinDesafiliacion, null, true);
	}

    private List<BeneficiarioDTO> consultarListaBeneficiarios(Long idAfiliado, Boolean sinDesafiliacion, Long idSolicitud, Boolean fechaHoy) { 
	    logger.debug("Inicia consultarBeneficiarios(Long)");
		List<Beneficiario> beneficiarios = null;
		logger.info("idAfiliadoconsultaRF"+idAfiliado);
		//Sin desafiliacion= consulta todos los beneficiarios que no han tenido motivo de desafiliacion
		if(sinDesafiliacion!= null && sinDesafiliacion){
			beneficiarios = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_AFILIADO_SINDESAFILIACION, Beneficiario.class)
					.setParameter("idAfiliado", idAfiliado)
					.getResultList();
		}else{
			
			beneficiarios =entityManager
			.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_AFILIADO, Beneficiario.class)
			.setParameter("idAfiliado", idAfiliado).getResultList();
		}
		
		

		if (beneficiarios == null || beneficiarios.isEmpty()) {
			logger.debug("Finaliza consultarBeneficiarios(Long): El beneficiario no se encuentra registrado");
			return null;
		}

		try {
			if (fechaHoy == Boolean.TRUE) {
				Date hoy = new Date();
				Iterator<Beneficiario> iter = beneficiarios.iterator();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				while (iter.hasNext()) {
					Beneficiario beneficiario = iter.next();
					Date fechaAfi = beneficiario.getFechaAfiliacion();
					
					if (!sdf.format(fechaAfi).equals(sdf.format(hoy))) {
						iter.remove();
					}
				}
			}
			List<BeneficiarioDTO> beneficiarioDTOs = new ArrayList<>();
			PersonaDetalle personaDetalle = null;
			CondicionInvalidez condicionInvalidez = null;
			BeneficiarioDetalle benDetalle = null;
			CertificadoEscolarBeneficiario certificado = null;
			for (Beneficiario beneficiario : beneficiarios) {
				try {
					personaDetalle = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
									PersonaDetalle.class)
							.setParameter("tipoIdentificacion", beneficiario.getPersona().getTipoIdentificacion())
							.setParameter("numeroIdentificacion", beneficiario.getPersona().getNumeroIdentificacion())
							.getSingleResult();

					benDetalle = consultarDatosBeneficiarioDetalle(personaDetalle.getIdPersonaDetalle());
					// Consultar informacion certificado escolar vigente
					certificado = consultarCertificadoEscolarVigentePorIdPersona(beneficiario.getPersona().getIdPersona());
				} catch (NoResultException nre) {
					personaDetalle = null;
				}

				try {
					condicionInvalidez = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_CONDICION_INVALIDEZ_BENEFICIARIO,
									CondicionInvalidez.class)
							.setParameter("tipoIdentificacion", beneficiario.getPersona().getTipoIdentificacion())
							.setParameter("numeroIdentificacion", beneficiario.getPersona().getNumeroIdentificacion())
							.getSingleResult();
				} catch (NoResultException e) {
					condicionInvalidez = null;
				}

				BeneficiarioDTO beneficiarioDTO = BeneficiarioDTO.convertBeneficiarioToDTO(beneficiario, personaDetalle,
						condicionInvalidez, benDetalle);
                if (certificado != null) {
                    beneficiarioDTO.setFechaRecepcionCertificadoEscolar(certificado.getFechaRecepcionCertificadoEscolar() != null
                            ? certificado.getFechaRecepcionCertificadoEscolar() : null);
                    beneficiarioDTO.setFechaVencimientoCertificadoEscolar(certificado.getFechaVencimientoCertificadoEscolar() != null
                            ? certificado.getFechaVencimientoCertificadoEscolar() : null);
                }
                Integer idPersona = Integer.parseInt(String.valueOf(beneficiario.getPersona().getIdPersona()));
				// Consulta la lista de chequeo asociada a la solicitud de afiliacin
				List<ItemChequeo> itemsChequeo = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ITEM_CHEQUEO, ItemChequeo.class)
					.setParameter("idAfiliado", idAfiliado != null ? Integer.parseInt(idAfiliado.toString()) : null) 
					.setParameter("idSolicitud", idSolicitud != null ? Integer.parseInt(idAfiliado.toString()) : null)
					.setParameter("idPersona",  idPersona != null ? idPersona : null) 
					.getResultList();

				if (itemsChequeo != null && !itemsChequeo.isEmpty()) {
					List<ItemChequeoDTO> itemChequeoAfiliacionDTOs = new ArrayList<>();
					for (ItemChequeo itemChequeo : itemsChequeo) {
						ItemChequeoDTO itemChequeoDTO = ItemChequeoDTO.convertItemChequeoToDTO(itemChequeo);
						itemChequeoAfiliacionDTOs.add(itemChequeoDTO);
					}
					beneficiarioDTO.setListaChequeo(itemChequeoAfiliacionDTOs);
				}
				// Consulta la lista de chequeo asociada a la solicitud de novedad de activacin
				else {
					itemsChequeo = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ITEM_CHEQUEO_BENEFICIARIO_NOVEDAD,
									ItemChequeo.class)
							.setParameter("idAfiliado", idAfiliado)
							.setParameter("idSolicitud", idSolicitud)
							.setParameter("idPersona", beneficiario.getPersona().getIdPersona()).getResultList();

					if (itemsChequeo != null && !itemsChequeo.isEmpty()) {
						List<ItemChequeoDTO> itemChequeoAfiliacionDTOs = new ArrayList<>();
						for (ItemChequeo itemChequeo : itemsChequeo) {
							ItemChequeoDTO itemChequeoDTO = ItemChequeoDTO.convertItemChequeoToDTO(itemChequeo);
							itemChequeoDTO.setIdentificadorDocumentoPrevio(itemChequeoDTO.getIdentificadorDocumento());
							itemChequeoAfiliacionDTOs.add(itemChequeoDTO);
						}
						beneficiarioDTO.setListaChequeo(itemChequeoAfiliacionDTOs);
					}

				}
				beneficiarioDTOs.add(beneficiarioDTO);
			}
			logger.debug("Finaliza consultarBeneficiarios(Long)");
			return beneficiarioDTOs;
		} catch (Exception e) {
			logger.error("No es posible consultar los beneficiarios", e);
			logger.debug("Finaliza consultarBeneficiarios(Long)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#asociarBeneficiarioAGrupoFamiliar(java.lang.Long,
	 *      java.lang.Long, com.asopagos.dto.DatosBasicosIdentificacionDTO)
	 */
	@Override
	public void asociarBeneficiarioAGrupoFamiliar(Long idAfiliado, Long idGrupoFamiliar,
			DatosBasicosIdentificacionDTO inDTO) {
		try {
			logger.info("Inicia asociarBeneficiarioAGrupoFamiliar(Long,Long,DatosBasicosIdentificacionDTO)");

			List<Beneficiario> beneficiarios = consultarBeneficiarioAsociadoAfiliado(idAfiliado, inDTO.getPersona().getTipoIdentificacion(),
					inDTO.getPersona().getNumeroIdentificacion());

			Beneficiario beneficiario = beneficiarios.iterator().next();

			List<GrupoFamiliar> grupoFamiliares = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR, GrupoFamiliar.class)
					.setParameter("idAfiliado", idAfiliado).setParameter("idGrupoFamiliar", idGrupoFamiliar)
					.getResultList();

			GrupoFamiliar grupoFamiliar = grupoFamiliares.iterator().next();

			beneficiario.setGrupoFamiliar(grupoFamiliar);

			entityManager.merge(beneficiario);

			logger.debug("Finaliza asociarBeneficiarioAGrupoFamiliar(Long,Long,DatosBasicosIdentificacionDTO)");
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza asociarBeneficiarioAGrupoFamiliar(Long,Long,DatosBasicosIdentificacionDTO):No se pudo asociar el beneficiario, recursos insuficientes");
		} catch (Exception e) {
			logger.error("No es posible asociar el beneficiario al grupo familiar", e);
			logger.debug("Finaliza asociarBeneficiarioAGrupoFamiliar(Long,Long,DatosBasicosIdentificacionDTO)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#eliminarBeneficiario(java.lang.Long,
	 *      com.asopagos.dto.DatosBasicosIdentificacionDTO)
	 */
	@Override
	public void eliminarBeneficiario(Long idAfiliado, DatosBasicosIdentificacionDTO inDTO) {
		try {
			List<Beneficiario> beneficiarios = consultarBeneficiarioAsociadoAfiliado(idAfiliado, inDTO.getPersona().getTipoIdentificacion(),
					inDTO.getPersona().getNumeroIdentificacion());

			Beneficiario beneficiario = beneficiarios.iterator().next();
			entityManager.remove(beneficiario);
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza actualizarEstadoAfiliado(Long,EstadoAfiliadoEnum):No pudo ser encontrado el beneficiario");
		} catch (Exception e) {
			logger.error("No es posible actualizar el estado del afiliado", e);
			logger.debug("Finaliza actualizarEstadoAfiliado(Long,EstadoAfiliadoEnum)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

    /**
     * Consulta la informacin de beneficiario asociado por afiliado, tipo y numero documento
     * @param idAfiliado
     *        Identificador afiliado
     * @param tipoIdentificacion
     *        Tipo identificacin beneficiario
     * @param numeroIdentificacion
     *        Numero identificacin beneficiario
     * @return Lista de beneficiarios
     */
    private List<Beneficiario> consultarBeneficiarioAsociadoAfiliado(Long idAfiliado, TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        return entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_AFILIADO_TIPO_NUMERO_DOCUMENTO, Beneficiario.class)
                .setParameter("idAfiliado", idAfiliado).setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
    }

    /**
     * Consulta la informacin de beneficiario asociado por afiliado, tipo y numero documento y tipo de beneficiario
     * @param idAfiliado
     *        Identificador afiliado
     * @param tipoIdentificacion
     *        Tipo identificacin beneficiario
     * @param numeroIdentificacion
     *        Numero identificacin beneficiario
     * @param tipoBeneficiario
     *        Tipo de beneficiario
     * @return Lista de beneficiarios
     */
    private List<Beneficiario> consultarBeneficiarioAsociadoAfiliadoTipoBeneficiario(Long idAfiliado,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, ClasificacionEnum tipoBeneficiario) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_AFILIADO_TIPO_NUMERO_DOCUMENTO_CLASIFICACION,
                        Beneficiario.class)
                .setParameter("idAfiliado", idAfiliado).setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoBeneficiario", tipoBeneficiario)
                .getResultList();
    }

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public ConsultarAfiliadoOutDTO consultarAfiliado(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Boolean ubicacionesIdentificacion) {
		try {

			if (ubicacionesIdentificacion == null) {
				ubicacionesIdentificacion = false;
			}
			logger.debug("Inicia consultarAfiliado(TipoIdentificacionEnum, String)");

			List<RolAfiliado> rolesAfiliado = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_TIPO_NUMUMERO_DOCUMENTO,
							RolAfiliado.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

			ConsultarAfiliadoOutDTO consultarAfiliadoOutDTO = new ConsultarAfiliadoOutDTO();
			consultarAfiliadoOutDTO.setIdentificacionUbicacionPersona(new IdentificacionUbicacionPersonaDTO());
			Afiliado afiliado = null;
			PersonaDetalle personaDetalle = null;

			try {
				personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
								PersonaDetalle.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();
			} catch (NoResultException nre) {
				personaDetalle = null;
			}
			MedioDePagoModeloDTO medioDePagoDTO = new MedioDePagoModeloDTO();
			try {
				MedioDePago medioDePago = (MedioDePago) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_PERSONA_ESTADO)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("medioActivo", Boolean.TRUE).getSingleResult();
				medioDePagoDTO.convertToDTO(medioDePago);
			} catch (NoResultException e) {
				medioDePagoDTO = null;
			}

			if (rolesAfiliado != null && !rolesAfiliado.isEmpty()) {
				consultarAfiliadoOutDTO.setInformacionLaboralTrabajador(new ArrayList<>());

				if (ubicacionesIdentificacion) {
					consultarAfiliadoOutDTO.setLstIdentificacionUbicacionPersona(new ArrayList<>());
				} else {
					consultarAfiliadoOutDTO.setIdentificacionUbicacionPersona(new IdentificacionUbicacionPersonaDTO());
				}
				List<ConsultarEstadoDTO> listConsul = new ArrayList<ConsultarEstadoDTO>();
				for (RolAfiliado rolAfiliado : rolesAfiliado) {
					IdentificacionUbicacionPersonaDTO idenDto = new IdentificacionUbicacionPersonaDTO();
					idenDto.setClaseIndependiente(rolAfiliado.getClaseIndependiente());
					idenDto.setPorcentajeAportes(rolAfiliado.getPorcentajePagoAportes());
					idenDto.setValorMesadaSalarioIngresos(rolAfiliado.getValorSalarioMesadaIngresos());
					idenDto.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
					idenDto.setMedioDePago(medioDePagoDTO);
					idenDto.setMunicipioDesempenioLabores(rolAfiliado.getIdMunicipioDesempenioLabores());					
					if (rolAfiliado.getPagadorPension() != null) {
						idenDto.setIdPagadorPension(rolAfiliado.getPagadorPension().getIdAFP());
					}

					if (rolAfiliado.getPagadorAportes() != null) {
						idenDto.setIdEntidadPagadora(rolAfiliado.getPagadorAportes().getIdEntidadPagadora());
					}

					if (rolAfiliado.getOportunidadPago() != null) {
						idenDto.setOportunidadPago(rolAfiliado.getOportunidadPago());
					}

					idenDto.setIdentificadorAnteEntidadPagadora(rolAfiliado.getIdentificadorAnteEntidadPagadora());

					idenDto.setPersona(
							PersonaDTO.convertPersonaToDTO(rolAfiliado.getAfiliado().getPersona(), personaDetalle));
					if (rolAfiliado.getAfiliado().getPersona().getUbicacionPrincipal() != null) {

						if (rolAfiliado.getAfiliado().getPersona().getUbicacionPrincipal()
								.getAutorizacionEnvioEmail() != null) {
							idenDto.getPersona().setAutorizacionEnvioEmail(rolAfiliado.getAfiliado().getPersona()
									.getUbicacionPrincipal().getAutorizacionEnvioEmail());
						}
					}
					if (ubicacionesIdentificacion) {
						consultarAfiliadoOutDTO.getLstIdentificacionUbicacionPersona().add(idenDto);
					} else {
						consultarAfiliadoOutDTO.setIdentificacionUbicacionPersona(idenDto);
					}

					afiliado = rolAfiliado.getAfiliado();

					InformacionLaboralTrabajadorDTO laboralTrabajadorDTO = new InformacionLaboralTrabajadorDTO();
					if (rolAfiliado.getSucursalEmpleador() != null) {
						laboralTrabajadorDTO
								.setIdSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa());
					}
					laboralTrabajadorDTO.setClaseTrabajador(rolAfiliado.getClaseTrabajador());
					laboralTrabajadorDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
					laboralTrabajadorDTO.setFechaInicioContrato(rolAfiliado.getFechaIngreso());
					laboralTrabajadorDTO.setFechaFinContrato(rolAfiliado.getFechaFinContrato());
					laboralTrabajadorDTO.setTipoSalario(rolAfiliado.getTipoSalario());
					laboralTrabajadorDTO.setValorSalario(rolAfiliado.getValorSalarioMesadaIngresos());
					laboralTrabajadorDTO.setHorasLaboradasMes(rolAfiliado.getHorasLaboradasMes());
					laboralTrabajadorDTO.setCargo(rolAfiliado.getCargo());
					laboralTrabajadorDTO.setTipoContrato(rolAfiliado.getTipoContrato());
					laboralTrabajadorDTO.setTipoAfiliado(rolAfiliado.getTipoAfiliado());
					laboralTrabajadorDTO
							.setTipoIdentificacion(rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion());
					laboralTrabajadorDTO
							.setNumeroIdentificacion(rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
					laboralTrabajadorDTO.setMotivoDesafiliacion(rolAfiliado.getMotivoDesafiliacion());
					laboralTrabajadorDTO.setEstadoAfiliado(rolAfiliado.getEstadoAfiliado());
					

					// se consulta estado por cada rol
					ConsultarEstadoDTO consulEstado = new ConsultarEstadoDTO();
					consulEstado.setEntityManager(entityManager);
					consulEstado
							.setNumeroIdentificacion(rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
					consulEstado.setTipoIdentificacion(rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion());
					consulEstado.setTipoPersona(ConstantesComunes.PERSONAS);
					if (rolAfiliado.getEmpleador() != null) {
						laboralTrabajadorDTO.setIdEmpleador(rolAfiliado.getEmpleador().getIdEmpleador());
						consulEstado.setIdEmpleador(rolAfiliado.getEmpleador().getIdEmpleador());
					}
					consulEstado.setTipoRol(rolAfiliado.getTipoAfiliado().toString());
					listConsul.add(consulEstado);
					consultarAfiliadoOutDTO.getInformacionLaboralTrabajador().add(laboralTrabajadorDTO);

				}

//				List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listConsul);
//				for (InformacionLaboralTrabajadorDTO infoLaboral : consultarAfiliadoOutDTO
//						.getInformacionLaboralTrabajador()) {
//					for (EstadoDTO estadoDTO : listEstados) {
//						if (estadoDTO.getTipoIdentificacion().equals(infoLaboral.getTipoIdentificacion())
//								&& estadoDTO.getNumeroIdentificacion().equals(infoLaboral.getNumeroIdentificacion())) {
//							infoLaboral.setEstadoAfiliado(estadoDTO.getEstado());
//						}
//					}
//				}
			}

			if (afiliado == null) {
				List<Afiliado> afiliados = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_TIPO_NUMERO_DOCUMENTO, Afiliado.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
				if (afiliados == null || afiliados.isEmpty()) {
					logger.debug(
							"Finaliza consultarAfiliado(TipoIdentificacionEnum, String):No se encontro el afiliado");
					return null;
				}
				afiliado = afiliados.iterator().next();
			}

			PersonaDTO personaDTO = PersonaDTO.obtenerPersonaDTODeAfiliado(afiliado, personaDetalle);

			/* Se asignan los datos del medio de Pago de la persona */
			personaDTO.setMedioDePagoPersona(medioDePagoDTO);
			consultarAfiliadoOutDTO.getIdentificacionUbicacionPersona().setMedioDePago(medioDePagoDTO);
			if (ubicacionesIdentificacion) {

				if (afiliado.getPersona().getUbicacionPrincipal() != null) {
					UbicacionDTO ubicacionDTO = UbicacionDTO
							.obtenerUbicacionDTO(afiliado.getPersona().getUbicacionPrincipal());
					consultarAfiliadoOutDTO.getLstIdentificacionUbicacionPersona().get(0).setUbicacion(ubicacionDTO);
				}
				consultarAfiliadoOutDTO.getLstIdentificacionUbicacionPersona().get(0)
						.setAutorizacionUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());

				consultarAfiliadoOutDTO.getLstIdentificacionUbicacionPersona().get(0)
						.setResideSectorRural(personaDetalle.getResideSectorRural());

				consultarAfiliadoOutDTO.getLstIdentificacionUbicacionPersona().get(0).getPersona()
						.setIdAfiliado(afiliado.getIdAfiliado());

			} else {
				consultarAfiliadoOutDTO.getIdentificacionUbicacionPersona().setPersona(personaDTO);
				if (afiliado.getPersona().getUbicacionPrincipal() != null) {
					UbicacionDTO ubicacionDTO = UbicacionDTO
							.obtenerUbicacionDTO(afiliado.getPersona().getUbicacionPrincipal());
					consultarAfiliadoOutDTO.getIdentificacionUbicacionPersona().setUbicacion(ubicacionDTO);
				}
				consultarAfiliadoOutDTO.getIdentificacionUbicacionPersona()
						.setAutorizacionUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
				consultarAfiliadoOutDTO.getIdentificacionUbicacionPersona()
						.setResideSectorRural(personaDetalle.getResideSectorRural());
				consultarAfiliadoOutDTO.getIdentificacionUbicacionPersona().getPersona()
						.setIdAfiliado(afiliado.getIdAfiliado());
			}

			return consultarAfiliadoOutDTO;

		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza consultarAfiliado(TipoIdentificacionEnum, String):No se pudo encontrar el recurso solicitado");
			return null;
		} catch (Exception e) {
			logger.error("No es posible consultar el afiliado", e);
			logger.debug("Finaliza consultarAfiliado(TipoIdentificacionEnum, String)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 *      actualizarEstadoBeneficiario(Long, EstadoAfiliadoEnum)
	 */
	@Override
	public void actualizarEstadoBeneficiario(Long idBeneficiario, EstadoAfiliadoEnum estado,
			MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
		logger.debug("Inicia actualizarEstadoBeneficiario(Long,EstadoAfiliadoEnum)");
		try {
			Beneficiario beneficiario = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ID_BENEFICIARIO, Beneficiario.class)
					.setParameter("idBeneficiario", idBeneficiario).getSingleResult();
			beneficiario.setEstadoBeneficiarioAfiliado(estado);
			if (motivoDesafiliacion != null) {
				beneficiario.setMotivoDesafiliacion(motivoDesafiliacion);
				if(EstadoAfiliadoEnum.INACTIVO.equals(estado)){
				    beneficiario.setFechaRetiro(new Date());
				}
			}
			logger.debug("Validacin fecha de retiro de beneficiario: "+beneficiario.getFechaRetiro());
			beneficiario = entityManager.merge(beneficiario);
			logger.info("ncespedes DesafiliarBeneficiario:  "+beneficiario.toString());
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza actualizarEstadoBeneficiario(Long,EstadoAfiliadoEnum):No se pudo encontrar el recurso solicitado");
		} catch (IllegalArgumentException e) {
			logger.error("No es posible actualizar el estado del beneficiario", e);
			logger.debug("Finaliza actualizarEstadoBeneficiario(Long,EstadoAfiliadoEnum)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * actualizarEstadoRolAfiliado(java.lang.Long,
	 * com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum)
	 */
	@Override
	public void actualizarEstadoRolAfiliado(Long idRolAfiliado, EstadoAfiliadoEnum estado) {
		logger.debug("Inicia actualizarEstadoRolAfiliado(Long,EstadoAfiliadoEnum)");
		/* Se valida si los datos llegan null */
		try {
			RolAfiliado rolAfiliado = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID, RolAfiliado.class)
					.setParameter("idRolAfiliado", idRolAfiliado).getSingleResult();
			if (rolAfiliado != null) {
				/*
				 * Si el estado es activo borrar marcas de fecha de retiro y
				 * motivo de desafiliacion si las hay, ya que afectan el calculo
				 * del estado
				 */
				if (estado.equals(EstadoAfiliadoEnum.ACTIVO)) {
					rolAfiliado.setFechaRetiro(null);
					rolAfiliado.setMotivoDesafiliacion(null);
					inactivarBeneficiarioRolAfiliado(idRolAfiliado);
					
					//Si previamente el empleador estuvo en bandeja de 0 trabajadores y se gestin MANTENER AFILICIN
		            //se deshace dicha gestin para que no se inactive posteiormente de manera automtica
					if (rolAfiliado.getEmpleador() != null) {
					    deshacerGestionCeroTrabajadores(rolAfiliado.getEmpleador().getIdEmpleador());
                    }
				}
				rolAfiliado.setEstadoAfiliado(estado);
				entityManager.merge(rolAfiliado);
			}
			logger.debug("Finaliza actualizarEstadoRolAfiliado(Long,EstadoAfiliadoEnum)");
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza actualizarEstadoRolAfiliado(Long,EstadoAfiliadoEnum):No se pudo encontrar el recurso solicitado");
		} catch (IllegalArgumentException e) {
			logger.error("No es posible actualizar el estado del rol afiliado", e);
			logger.debug("Finaliza actualizarEstadoBeneficiario(Long,EstadoAfiliadoEnum)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

    /**
     * Inactiva los registros de beneficiario de la persona que se va a Activar como afiliado principal
     * @param idRolAfiliado
     *        Identificador de rol afiliado a Activar
     */
    private void inactivarBeneficiarioRolAfiliado(Long idRolAfiliado) {
        /*List<Beneficiario> afiliadoComoBeneficiario = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_COMO_BENEFICIARIO, Beneficiario.class)
                .setParameter("idRolAfiliado", idRolAfiliado).getResultList();

        if (afiliadoComoBeneficiario != null && !afiliadoComoBeneficiario.isEmpty()) {
            for (Beneficiario beneficiario : afiliadoComoBeneficiario) {
                if (!ClasificacionEnum.CONYUGE.equals(beneficiario.getTipoBeneficiario())) {
                    beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
                    beneficiario.setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
                }
            }
        }
        */
        
        InactivarBeneficiarioRolAfiliadoRutine i = new InactivarBeneficiarioRolAfiliadoRutine();
        i.inactivarBeneficiarioRolAfiliado(idRolAfiliado, entityManager);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * actualizarFechaAfiliacionRolAfiliado(java.lang.Long, java.util.Date)
	 */
	@Override
	public void actualizarFechaAfiliacionRolAfiliado(Long idRolAfiliado, Long fecha) {
		logger.debug("Inicia actualizarFechaAfiliacionRolAfiliado(Long,Date)");
		try {
			RolAfiliado rolAfiliado = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID, RolAfiliado.class)
					.setParameter("idRolAfiliado", idRolAfiliado).getSingleResult();
			Date fAfiliacion = new Date(fecha);
			rolAfiliado.setFechaAfiliacion(fAfiliacion);
			entityManager.merge(rolAfiliado);
			logger.debug("Finaliza actualizarFechaAfiliacionRolAfiliado(Long,Date)");
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza actualizarFechaAfiliacionRolAfiliado(Long,Date):No se pudo encontrar el recurso solicitado");
		} catch (IllegalArgumentException e) {
			logger.error("No es posible actualizar el estado del rol afiliado", e);
			logger.debug("Finaliza actualizarFechaAfiliacionRolAfiliado(Long,Date)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override
	public EstadoAfiliadoEnum consultarEstadoRolAfiliado(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado, Long idEmpleador) {
		try {
			List<RolAfiliado> rolAfilado = null;
			if (idEmpleador != null) {
				rolAfilado = entityManager
						.createNamedQuery(
								NamedQueriesConstants.BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO_EMPLEADOR,
								RolAfiliado.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("tipoAfiliado", tipoAfiliado).setParameter("idEmpleador", idEmpleador)
						.getResultList();
			} else {
				rolAfilado = entityManager
						.createNamedQuery(
								NamedQueriesConstants.BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO,
								RolAfiliado.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("tipoAfiliado", tipoAfiliado).getResultList();
			}

			if (!rolAfilado.isEmpty() && rolAfilado != null) {
				
				RolAfiliado rolAfiliado = rolAfilado.get(0);
				
				List<EstadoDTO> estados = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_AFILIACION_AFILIADO)
						.setParameter("tipoId", rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion().name())
						.setParameter("numeroId", rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion())
						.setParameter("tipoAfiliado", tipoAfiliado.name())
						.setParameter("idEmpleador", idEmpleador)
						.getResultList();
				
				
				
//				ConsultarEstadoDTO consulEstado = new ConsultarEstadoDTO();
//				consulEstado.setEntityManager(entityManager);
//				consulEstado.setNumeroIdentificacion(rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
//				consulEstado.setTipoIdentificacion(rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion());
//				if (idEmpleador != null) {
//					consulEstado.setIdEmpleador(idEmpleador);
//				} else {
//					consulEstado.setTipoPersona(ConstantesComunes.PERSONAS);
//					consulEstado.setTipoRol(tipoAfiliado.toString());
//				}
//				List<ConsultarEstadoDTO> listaConsulta = new ArrayList<ConsultarEstadoDTO>();
//				listaConsulta.add(consulEstado);
//				List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listaConsulta);
				if (!estados.isEmpty() && estados != null) {
					return estados.get(0).getEstado();
				} else {
					if (rolAfiliado.getEstadoAfiliado() != null) {
						return rolAfiliado.getEstadoAfiliado();
					} else {
						return EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION;
					}
				}
			} else {
				return null;
			}

		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza consultarEstadoRolAfiliado(TipoIdentificacionEnum,String,TipoAfiliadoEnum):No se pudo encontrar el recurso solicitado");
			return null;
		} catch (IllegalArgumentException e) {
			logger.error("No es posible consultar el estado del Rol Afiliado", e);
			logger.debug("Finaliza consultarEstadoRolAfiliado(TipoIdentificacionEnum,String,TipoAfiliadoEnum)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarTrabajadoresEmpresaSucursal(java.lang.Long, java.util.List)
	 */
	@Override
	public List<TrabajadorEmpleadorDTO> consultarTrabajadoresEmpresaSucursal(Long idEmpleadorOrigen,
			Long idEmpleadorDestino, Long fechaFinLabores, List<Long> idSucursales) {
		logger.debug(
				"Inicio de mtodo consultarTrabajadoresEmpresaSucursal(Long idEmpleador, List<Long> idSucursales)");
		try {
			List<RolAfiliado> rolAfiliados = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_ID_EMPLEADOR_ID_SUCURSALES)
					.setParameter("idEmpleador", idEmpleadorOrigen).setParameter("idSucursales", idSucursales)
					.setParameter("estadoEmpleador", EstadoEmpleadorEnum.ACTIVO)
					.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
					.setParameter("fechaFinLabores", new Date(fechaFinLabores))
					.setParameter("idEmpleadorDestino", idEmpleadorDestino).getResultList();

			List<TrabajadorEmpleadorDTO> trabajadores = new ArrayList<TrabajadorEmpleadorDTO>();
			for (RolAfiliado rolAfiliado : rolAfiliados) {
				TrabajadorEmpleadorDTO trabajador = new TrabajadorEmpleadorDTO();
				trabajador.setTipoIdentificacion(rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion());
				trabajador.setNumeroIdentificacion(rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
				trabajador.setPrimerNombre(rolAfiliado.getAfiliado().getPersona().getPrimerNombre());
				trabajador.setSegundoNombre(rolAfiliado.getAfiliado().getPersona().getSegundoNombre());
				trabajador.setPrimerApellido(rolAfiliado.getAfiliado().getPersona().getPrimerApellido());
				trabajador.setSegundoApellido(rolAfiliado.getAfiliado().getPersona().getSegundoApellido());
				trabajador.setNombreSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getNombre());
				trabajador.setIdSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa());
				trabajador.setCodigoSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getCodigo());
				trabajador.setIdPersona(rolAfiliado.getAfiliado().getPersona().getIdPersona());

				trabajadores.add(trabajador);
			}
			logger.debug(
					"Fin de mtodo consultarTrabajadoresEmpresaSucursal(Long idEmpleador, List<Long> idSucursales)");
			return trabajadores;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#calcularCategoriasAfiliado(com.asopagos.dto.CategoriaDTO)
	 */
	@Override
	public Map<TipoAfiliadoEnum, CategoriaPersonaEnum> calcularCategoriasAfiliado(CategoriaDTO categoriaDTO) {
	    CalcularCategoriasAfiliadoRutine c = new CalcularCategoriasAfiliadoRutine();
			logger.info("**__**AFILIADOS BUSINNESS CalcularCategoriasAfiliadoRutine");
	    return c.calcularCategoriasAfiliado(categoriaDTO, entityManager);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarCategoriasAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CategoriaDTO> consultarCategoriasAfiliado(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado) {

		logger.debug("Inicia consultarCategoriasAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)");
		List<Categoria> categoriasAfiliados = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_CATEGORIA_AFILIADO_PERSONA_TIPO_IDENTIFICACION_NUMERO,
						Categoria.class)
				.setParameter("tipoIdentificacion", tipoIdentificacion)
				.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		List<CategoriaDTO> categoriasDTO = new ArrayList<CategoriaDTO>();
		if (categoriasAfiliados != null && !categoriasAfiliados.isEmpty()) {
			for (Categoria categoriaIter : categoriasAfiliados) {
				if (tipoAfiliado != null) {
					if (tipoAfiliado.equals(categoriaIter.getTipoAfiliado())) {
						if (categoriaIter.getAfiliadoPrincipal() == true) {
							CategoriaDTO categoria = CategoriaDTO.convertCategoriaToDTO(categoriaIter);
							categoriasDTO.add(categoria);
						}
					}
				} else {
					// Guarda sin especificar el parametro de tipo Afiliado
					if (categoriaIter.getAfiliadoPrincipal() == true) {
						CategoriaDTO categoria = CategoriaDTO.convertCategoriaToDTO(categoriaIter);
						categoriasDTO.add(categoria);
					}
				}
			}
			logger.debug("Finaliza consultarCategoriasAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)");
			return categoriasDTO;
		} else {
			logger.debug(
					"Finaliza consultarCategoriasAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum):No se encontraron categoras para el afiliado");
			return null;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarCategoriasBeneficiario(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String,
	 *      com.asopagos.enumeraciones.personas.TipoAfiliadoEnum,
	 *      java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CategoriaDTO> consultarCategoriasBeneficiario(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado, Long idAfiliado) {
		logger.debug("Inicia consultarCategoriasBeneficiario(TipoIdentificacionEnum, String, TipoAfiliadoEnum, Long)");

		List<Categoria> categoriasBeneficiario;

		categoriasBeneficiario = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_CATEGORIA_AFILIADO_PERSONA_TIPO_IDENTIFICACION_NUMERO,
						Categoria.class)
				.setParameter("tipoIdentificacion", tipoIdentificacion)
				.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		List<CategoriaDTO> categoriasDTO = new ArrayList<CategoriaDTO>();
		if (categoriasBeneficiario != null) {
			for (Categoria categoriaIter : categoriasBeneficiario) {

				// Se verifica si se debe filtar por id de afiliado
				if (idAfiliado != null && tipoAfiliado != null) {
					if (idAfiliado.equals(categoriaIter.getIdAfiliado())
							&& tipoAfiliado.equals(categoriaIter.getTipoAfiliado())) {
						if (categoriaIter.getAfiliadoPrincipal() == false) {
							CategoriaDTO categoria = CategoriaDTO.convertCategoriaToDTO(categoriaIter);
							categoriasDTO.add(categoria);
							continue; // como ya lo asign debe continuar con el
										// siguente registro
						}
					} else {
						continue; // como no cumple con ambos filtros debe
									// continuar con el siguiente registro
					}
				}

				// Se verifica si se debe filtar por id de afiliado
				if (idAfiliado != null) {
					if (idAfiliado.equals(categoriaIter.getIdAfiliado())) {
						if (categoriaIter.getAfiliadoPrincipal() == false) {
							CategoriaDTO categoria = CategoriaDTO.convertCategoriaToDTO(categoriaIter);
							categoriasDTO.add(categoria);
							continue; // como ya lo asin debe continuar con el
										// siguente registro
						}
					} else {
						continue; // como no cumple con el filtro debe continuar
									// con el siguiente registro
					}
				}
			}
		}

		// si no existen registros que cumplan con los filtros se lanza la
		// excepcion de recurso no encontrado
		if (categoriasDTO.isEmpty()) {
			logger.debug(
					"Finaliza consultarCategoriasBeneficiario(TipoIdentificacionEnum, String, TipoAfiliadoEnum, Long)");
			return null;
		}

		logger.debug(
				"Finaliza consultarCategoriasBeneficiario(TipoIdentificacionEnum, String, TipoAfiliadoEnum, Long)");
		return categoriasDTO;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#consultarRolesAfiliado(
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
	 */
	@Override
	public List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado) {
		logger.debug("Inicio de mtodo consultarEmpleadoresPersona(Long idAfiliado, TipoAfiliadoEnum tipoAfiliado)");
		List<RolAfiliado> roles = null;
		if (tipoAfiliado != null) {
			roles = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO, RolAfiliado.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoAfiliado", tipoAfiliado).getResultList();
		} else {
			roles = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_CATEGORIA_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO, RolAfiliado.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion.name())
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
		}
		List<ConsultarEstadoDTO> listConsultEstado = new ArrayList<ConsultarEstadoDTO>();
        List<RolAfiliadoEmpleadorDTO> rolesDTO = new ArrayList<>();
        if (!roles.isEmpty()) {
            for (RolAfiliado rolAfiliado : roles) {
                PersonaDetalle personaDetalle = consultarPersonaDetalle(rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion(),
                        rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());

                RolAfiliadoModeloDTO rolAfiliadoDTO = new RolAfiliadoModeloDTO();
                rolAfiliadoDTO.convertToDTO(rolAfiliado, personaDetalle);
                EmpleadorModeloDTO empleadorDTO = null;
                if (rolAfiliado.getEmpleador() != null) {
                    empleadorDTO = new EmpleadorModeloDTO();
                    empleadorDTO.convertToDTO(rolAfiliado.getEmpleador());
                }

                RolAfiliadoEmpleadorDTO rolAfiliadoEmpleadorDTO = new RolAfiliadoEmpleadorDTO();
                rolAfiliadoEmpleadorDTO.setEmpleador(empleadorDTO);
                rolAfiliadoEmpleadorDTO.setRolAfiliado(rolAfiliadoDTO);

                ConsultarEstadoDTO consulEstado = new ConsultarEstadoDTO();
                consulEstado.setNumeroIdentificacion(rolAfiliadoEmpleadorDTO.getRolAfiliado().getAfiliado().getNumeroIdentificacion());
                consulEstado.setTipoIdentificacion(rolAfiliadoEmpleadorDTO.getRolAfiliado().getAfiliado().getTipoIdentificacion());
                consulEstado.setEntityManager(entityManager);
                consulEstado.setTipoPersona(ConstantesComunes.PERSONAS);
                if (rolAfiliado.getEmpleador() != null) {
                    consulEstado.setIdEmpleador(rolAfiliado.getEmpleador().getIdEmpleador());
                }
                listConsultEstado.add(consulEstado);
                rolesDTO.add(rolAfiliadoEmpleadorDTO);
            }

            // Se evita la consulta de estado pues la consulta principal sin tipo de afiliado trae ese dato
            if (tipoAfiliado == null) {
                return rolesDTO;
            }
            List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsultEstado);
            for (RolAfiliadoEmpleadorDTO rolesEstado : rolesDTO) {
                for (EstadoDTO estadoDTO : estados) {
                    if (rolesEstado.getRolAfiliado().getAfiliado().getNumeroIdentificacion().equals(estadoDTO.getNumeroIdentificacion())
                            && rolesEstado.getRolAfiliado().getAfiliado().getTipoIdentificacion()
                                    .equals(estadoDTO.getTipoIdentificacion())) {
                        rolesEstado.getRolAfiliado().setEstadoAfiliado(estadoDTO.getEstado());
                    }
                }
            }
        }
		logger.debug("Fin de mtodo consultarEmpleadoresPersona(Long idAfiliado, TipoAfiliadoEnum tipoAfiliado)");
		return rolesDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarRolesEmpleadorAfiliado(java.lang.Long, java.util.List)
	 */
	@Override
	public List<RolAfiliadoModeloDTO> consultarRolesEmpleadorAfiliado(Long idEmpleador, List<Long> idsPersona) {
		logger.debug("Inicio de mtodo consultarRolesEmpleadorAfiliado(Long idEmpleador, List<Long> idsPersona)");
		List<RolAfiliado> roles = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_IDS_PERSONA_EMPLEADOR)
				.setParameter("idEmpleador", idEmpleador).setParameter("idsPersona", idsPersona).getResultList();

		List<RolAfiliadoModeloDTO> rolesDTO = new ArrayList<>();
		for (RolAfiliado rolAfiliado : roles) {
			PersonaDetalle personaDetalle = consultarPersonaDetalle(
					rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion(),
					rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
			RolAfiliadoModeloDTO rolAfiliadoDTO = new RolAfiliadoModeloDTO();
			rolAfiliadoDTO.convertToDTO(rolAfiliado, personaDetalle);
			rolesDTO.add(rolAfiliadoDTO);
		}
		logger.debug("Fin de mtodo consultarRolesEmpleadorAfiliado(Long idEmpleador, List<Long> idsPersona)");
		return rolesDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarClasificacionesAfiliado(com.asopagos.enumeraciones.personas.
	 * TipoIdentificacionEnum, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ClasificacionEnum> consultarClasificacionesAfiliado(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(
				"Inicio de mtodo consultarClasificacionesPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		List<Object[]> resultados = entityManager
				.createNamedQuery(
						NamedQueriesConstants.BUSCAR_CLASIFICACIONES_TIPO_IDENTIFICACION_NUMERO_IDENTIFICACION)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
		List<ClasificacionEnum> clasificaciones = new ArrayList<>();
		for (Object[] objeto : resultados) {
			if (objeto[1] != null) {
				ClasificacionEnum clasificacion = ClasificacionEnum.valueOf(objeto[1].toString().trim());
				clasificaciones.add(clasificacion);
			}
		}
		logger.debug(
				"Fin de mtodo consultarClasificacionesPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		return clasificaciones;

	}
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response consultarClasificacionesAfiliadoFovis(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion,HttpServletRequest requestContext,UserDTO userDTO) {
		logger.debug("Inicio de mtodo consultarClasificacionesPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		String firmaServicio = "FovisComposite.obtenerInfoFovis(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
		List<ClasificacionEnum> clasificaciones  =null;
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
		try{
		List<Object[]> resultados = entityManager
				.createNamedQuery(
						NamedQueriesConstants.BUSCAR_CLASIFICACIONES_TIPO_IDENTIFICACION_NUMERO_IDENTIFICACION)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
	    clasificaciones = new ArrayList<>();
		for (Object[] objeto : resultados) {
			if (objeto[1] != null) {
				ClasificacionEnum clasificacion = ClasificacionEnum.valueOf(objeto[1].toString().trim());
				clasificaciones.add(clasificacion);
			}
		}
		logger.debug("Fin de mtodo consultarClasificacionesPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		} catch (Exception e) {
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,null,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,clasificaciones,entityManager,auditoriaIntegracionServicios);
	}


	/**
	 * Servicio encargado de actualizar una lista de roles afiliado.
	 * 
	 * @param rolesDTO
	 *            roles afiliado a actualizar.
	 */
	public void actualizarRolesAfiliado(List<RolAfiliadoModeloDTO> rolesDTO) {
		logger.debug("Inicio de mtodo actualizarRolesAfiliado(List<RolAfiliadoModeloDTO> rolesDTO)");
		for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : rolesDTO) {
			RolAfiliado rolAfiliado = rolAfiliadoModeloDTO.convertToEntity();
			entityManager.merge(rolAfiliado);
		}
		logger.debug("Fin de mtodo actualizarRolesAfiliado(List<RolAfiliadoModeloDTO> rolesDTO)");
	}

	/**
	 * Servicio que consulta todos los roles afiliados de un empleador.
	 * 
	 * @param idEmpleador
	 *            id del empleador a buscar los afiliados.
	 * @param estadoAfiliado
	 *            estado de los afiliados.
	 */
	public List<RolAfiliadoModeloDTO> consultarRolesAfiliadosEmpleador(Long idEmpleador,
			EstadoAfiliadoEnum estadoAfiliado) {
		logger.info(
				"Inicio de mtodo consultarRolesAfiliadosEmpleador(Long idEmpleador, EstadoAfiliadoEnum estadoAfiliado) idEmpleador: "+idEmpleador+" estadoAfiliado: "+estadoAfiliado);
		List<RolAfiliado> roles;
		if (estadoAfiliado == null) {
			roles = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_EMPLEADOR)
					.setParameter("idEmpleador", idEmpleador).getResultList();
		} else {
			roles = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_EMPLEADOR_ESTADO)
					.setParameter("idEmpleador", idEmpleador).setParameter("estadoAfiliado", estadoAfiliado)
					.getResultList();
		}
		List<RolAfiliadoModeloDTO> rolesDTO = new ArrayList<>();
                int i = 0;
                logger.info("**--consultarRolesAfiliadosEmpleador NUM-size-" + roles.size());
		for (RolAfiliado rolAfiliado : roles) {
			PersonaDetalle personaDetalle = consultarPersonaDetalle(
					rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion(),
					rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
			RolAfiliadoModeloDTO rolAfiliadoDTO = new RolAfiliadoModeloDTO();
			rolAfiliadoDTO.convertToDTO(rolAfiliado, personaDetalle);
			rolesDTO.add(rolAfiliadoDTO);
                        i++;
		}
                logger.info("**--consultarRolesAfiliadosEmpleador NUM-i-" + i);
		logger.debug(
				"Fin de mtodo consultarRolesAfiliadosEmpleador(Long idEmpleador, EstadoAfiliadoEnum estadoAfiliado)");
		return rolesDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#actualizarBeneficiario(
	 * com.asopagos.dto.modelo.BeneficiarioModeloDTO)
	 */
	@Override
	public Long actualizarBeneficiario(BeneficiarioModeloDTO beneficiarioModeloDTO) {
		ActualizarBeneficiarioRutine a =  new ActualizarBeneficiarioRutine();
		return a.actualizarBeneficiario(beneficiarioModeloDTO, entityManager);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * actualizarGrupoFamiliarPersona(com.asopagos.dto.modelo.
	 * GrupoFamiliarModeloDTO)
	 */
	@Override
	public Long actualizarGrupoFamiliarPersona(GrupoFamiliarModeloDTO grupoFamiliarModeloDTO) {
	    ActualizarGrupoFamiliarPersonaRutine a = new ActualizarGrupoFamiliarPersonaRutine();
	    return a.actualizarGrupoFamiliarPersona(grupoFamiliarModeloDTO, entityManager);

	}

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#actualizarRolAfiliado(com.asopagos.dto.modelo.RolAfiliadoModeloDTO)
     */
    @Override
    public void actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {  	
        
        ActualizarRolAfiliadoRutine a = new ActualizarRolAfiliadoRutine();
        a.actualizarRolAfiliado(rolAfiliadoModeloDTO, entityManager);
    }

	/**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#actualizarRolAfiliado(com.asopagos.dto.modelo.RolAfiliadoModeloDTO)
     */
    @Override
    public Long crearRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {  	
		logger.info("Inicia metodo crearRolAfiliado(RolAfiliadoModeloDTO)");
        ActualizarRolAfiliadoRutine a = new ActualizarRolAfiliadoRutine();
        return a.crearRolAfiliado(rolAfiliadoModeloDTO, entityManager);
		
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiario(
	 * java.lang.Long)
	 */
	@Override
	public BeneficiarioModeloDTO consultarBeneficiario(Long idBeneficiario) {
		logger.debug("Inicia operacin consultarBeneficiario(Long)");
		BeneficiarioModeloDTO beneficiarioDTO = new BeneficiarioModeloDTO();
		try {
			Beneficiario beneficiario = (Beneficiario) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_POR_ID)
					.setParameter("idBeneficiario", idBeneficiario).getSingleResult();
			/* Convierte a BeneficiarioDTO. */
			if (beneficiario.getIdBeneficiario() != null) {
				PersonaDetalle personaDetalle = this
						.consultarDatosPersonaDetalle(beneficiario.getPersona().getIdPersona());
				CondicionInvalidez conInvalidez = this
						.consultarDatosCondicion(beneficiario.getPersona().getIdPersona());
				BeneficiarioDetalle beneDetalle = this
						.consultarDatosBeneficiarioDetalle(personaDetalle.getIdPersonaDetalle());
                // Consultar informacion certificado escolar vigente
                CertificadoEscolarBeneficiario certificado = consultarCertificadoEscolarVigentePorIdPersona(beneficiario.getPersona().getIdPersona());
				beneficiarioDTO.convertToDTO(beneficiario, personaDetalle, conInvalidez, beneDetalle, certificado);
			}
		} catch (NoResultException e) {
			logger.debug("Finaliza consultarObjetoBeneficiario(Long): No existe el Beneficiario");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarBeneficiario(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operacin consultarBeneficiario(Long)");
		return beneficiarioDTO;
	}

	/**
	 * Consulta los datos de PersonaDetalle
	 * 
	 * @param idPersona
	 *            identificacin Persona Asociada
	 * @return PersonaDetalle
	 */
	private PersonaDetalle consultarDatosPersonaDetalle(Long idPersona) {
		logger.debug("Inicia operacin consultarDatosPersonaDetalle(Long)");
		PersonaDetalle personaDetalle = new PersonaDetalle();
		try {
			/* Consulta el detalle Persona. */
			personaDetalle = (PersonaDetalle) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE_ID_PERSONA)
					.setParameter("idPersona", idPersona).getSingleResult();
		} catch (NoResultException noResult) {
			logger.debug("Finaliza consultarDatosPersonaDetalle(Long): No existe Persona Detalle");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarDatosPersonaDetalle(Long))", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operacin consultarDatosPersonaDetalle(Long)");
		return personaDetalle;
	}

	/**
	 * Consulta los datos de PersonaDetalle
	 * 
	 * @param idPersona
	 *            identificacin Persona Asociada
	 * @return PersonaDetalle
	 */
	private BeneficiarioDetalle consultarDatosBeneficiarioDetalle(Long idPersonaDetalle) {
		logger.debug("Inicia operacin consultarDatosPersonaDetalle(Long)");
		BeneficiarioDetalle beneficiarioDetalle = new BeneficiarioDetalle();
		try {
			/* Consulta el detalle Persona. */
			beneficiarioDetalle = (BeneficiarioDetalle) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_DETALLE_ID_PERSONADETALLE)
					.setParameter("idPersonaDetalle", idPersonaDetalle).getSingleResult();
		} catch (NoResultException noResult) {
			logger.debug(
					"Finaliza consultarDatosBeneficiarioDetalle(Long idPersonaDetalle): No existe BeneficiarioDetalle Detalle");
			return null;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarDatosPersonaDetalle(Long))", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operacin consultarDatosPersonaDetalle(Long)");
		return beneficiarioDetalle;
	}

	/**
	 * Consulta los datos de PersonaDetalle
	 * 
	 * @param idPersona
	 *            identificacin Persona Asociada
	 * @return PersonaDetalle
	 */
	private CondicionInvalidez consultarDatosCondicion(Long idPersona) {
		logger.debug("Inicia operacin consultarDatosCondicion(Long)");
		CondicionInvalidez condiInvalidez = new CondicionInvalidez();
		try {
			/* Consulta el detalle Persona. */

			condiInvalidez = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_CONDICION_INVALIDEZ_BENEFICIARIO_POR_ID,
							CondicionInvalidez.class)
					.setParameter("idPersona", idPersona).getSingleResult();

		} catch (NoResultException noResult) {
			logger.debug("Finaliza consultarDatosCondicion(Long): No existe Persona Detalle");
			return null;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarDatosCondicion(Long))", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operacin consultarDatosPersonaDetalle(Long)");
		return condiInvalidez;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarDatosGrupoFamiliar(java.lang.Long)
	 */
	@Override
	public GrupoFamiliarModeloDTO consultarDatosGrupoFamiliar(Long idGrupoFamiliar) {
		logger.debug("Inicia operacin consultarGrupoFamiliar(Long)");
		GrupoFamiliarModeloDTO grupoFamiliarDTO = new GrupoFamiliarModeloDTO();
		try {
			GrupoFamiliar grupoFamiliar = (GrupoFamiliar) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPOFAMILIAR_POR_ID)
					.setParameter("idGrupoFamiliar", idGrupoFamiliar).getSingleResult();
			grupoFamiliarDTO.convertToDTO(grupoFamiliar);
		} catch (NoResultException noResult) {
			logger.debug("Finaliza consultarGrupoFamiliar(Long): No existe el Grupo Familiar");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarGrupoFamiliar(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operacin consultarGrupoFamiliar(Long)");
		return grupoFamiliarDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#consultarRolAfiliado(java
	 * .lang.Long)
	 */
	@Override
	public RolAfiliadoModeloDTO consultarRolAfiliado(Long idRolAfiliado) {
		logger.debug("Inicia operacin consultarRolAfiliado(Long)");
		try {
			RolAfiliado rolAfiliado = (RolAfiliado) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ROLAFILIADO_POR_ID)
					.setParameter("idRolAfiliado", idRolAfiliado).getSingleResult();
			PersonaDetalle personaDetalle = consultarPersonaDetalle(
					rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion(),
					rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
			RolAfiliadoModeloDTO rolAfiliadoDTO = new RolAfiliadoModeloDTO();
			rolAfiliadoDTO.convertToDTO(rolAfiliado, personaDetalle);
			logger.debug("Finaliza operacin consultarRolAfiliado(Long)");
			return rolAfiliadoDTO;

		} catch (NoResultException noResult) {
			logger.debug("Finaliza consultarRolAfiliado(Long): No existe el Rol Afiliado");
			return null;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarRolAfiliado(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarVencimientoIncapacidades()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> consultarVencimientoIncapacidades() {
		logger.debug("Inicia operacin consultarVencimientoIncapacidades()");
		try {
			List<Long> personasVencimiento = new ArrayList<>();
			/* Se obtiene la fecha actual del sistema. */
			Calendar fechaActualSistema = Calendar.getInstance();
			/* Se asocian los tipos de Novedad Incapacidad. */
			List<String> tiposNovedad = new ArrayList<>();
			tiposNovedad.add(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL.name());
			List<BigInteger> idPersonasVencimiento = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_VENCIMIENTO_INCAPACIDADES)
					.setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name())
					.setParameter("tiposNovedad", tiposNovedad).setParameter("vigente", NumerosEnterosConstants.UNO)
					.setParameter("fechaActual", fechaActualSistema.getTime()).getResultList();
			if (idPersonasVencimiento != null && !idPersonasVencimiento.isEmpty()) {
				for (BigInteger idPersonaVencimiento : idPersonasVencimiento) {
					personasVencimiento.add(idPersonaVencimiento.longValue());
				}
			}

			logger.debug("Finaliza operacin consultarVencimientoIncapacidades()");
			return personasVencimiento;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarVencimientoIncapacidades()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * inactivarVencimientoIncapacidades(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void inactivarVencimientoIncapacidades(List<Long> idPersonasInactivar) {
		logger.debug("Inicia operacin inactivarCertificadoEscolaridad(List<Long>)");
		try {
			List<Object[]> novedadesIncapacidadInactivar = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_INACTIVAR)
					.setParameter("idPersonas", idPersonasInactivar).getResultList();
			if (novedadesIncapacidadInactivar != null && !novedadesIncapacidadInactivar.isEmpty()) {

				/*
				 * Se inactiva en batch por Vencimiento de Incapacidades.
				 * Configuracin actual: hibernate.jdbc.batch_size value = 500
				 */
				for (Object[] novedadIncapacidad : novedadesIncapacidadInactivar) {
					BigInteger idNovedadDetalle = (BigInteger) novedadIncapacidad[NumerosEnterosConstants.CERO];
					BigInteger idSolicitudNovedad = (BigInteger) novedadIncapacidad[NumerosEnterosConstants.UNO];
					Date fechaInicio = (Date) novedadIncapacidad[NumerosEnterosConstants.DOS];
					Date fechaFin = (Date) novedadIncapacidad[NumerosEnterosConstants.TRES];
					NovedadDetalle novedadDetalle = new NovedadDetalle();
					novedadDetalle.setIdNovedadDetalle(idNovedadDetalle.longValue());
					novedadDetalle.setIdSolicitudNovedad(idSolicitudNovedad.longValue());
					novedadDetalle.setFechaInicio(fechaInicio);
					novedadDetalle.setFechaFin(fechaFin);
					novedadDetalle.setVigente(Boolean.FALSE);
					entityManager.merge(novedadDetalle);
				}
			}
			logger.debug("Finaliza operacin inactivarCertificadoEscolaridad(List<Long>)");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo inactivarCertificadoEscolaridad(List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#actualizarNovedadPila(com
	 * .asopagos.dto.modelo.NovedadPilaModeloDTO)
	 */
	public void actualizarNovedadPila(NovedadDetalleModeloDTO novedadPilaModeloDTO) {
		logger.debug("Inicia operacin actualizarNovedadPila(NovedadPilaModeloDTO)");
		/*try {			
			NovedadDetalle novedadPila = novedadPilaModeloDTO.convertToEntity();
			if (novedadPila.getIdNovedadDetalle() != null) {
				entityManager.merge(novedadPila);
			} else {
				entityManager.persist(novedadPila);
			}
			logger.debug("Finaliza operacin actualizarNovedadPila(NovedadPilaModeloDTO)");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo actualizarNovedadPila(NovedadPilaModeloDTO)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		*/
		
		  ActualizarNovedadPilaRutine a = new ActualizarNovedadPilaRutine();
		  a.actualizarNovedadPila(novedadPilaModeloDTO, entityManager); 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#consultarDatosAfiliado(
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@Override
	public AfiliadoModeloDTO consultarDatosAfiliado(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
	    ConsultarDatosAfiliadoRutine c = new ConsultarDatosAfiliadoRutine();
	    return c.consultarDatosAfiliado(tipoIdentificacion, numeroIdentificacion, entityManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#actualizarDatosAfiliado(
	 * com.asopagos.dto.modelo.AfiliadoModeloDTO)
	 */
	@Override
	public void actualizarDatosAfiliado(AfiliadoModeloDTO afiliadoModeloDTO) {
		/*logger.debug("Inicia operacin actualizarDatosAfiliado(AfiliadoModeloDTO)");
		try {
			// Se convierte a la entidad para persistir 
			Afiliado afiliado = afiliadoModeloDTO.convertToEntity();
			if (afiliado.getIdAfiliado() != null) {
				entityManager.merge(afiliado);
			} else {
				entityManager.persist(afiliado);
			}
			Persona persona = afiliadoModeloDTO.convertToPersonaEntity();
			if (persona.getIdPersona() != null) {
				entityManager.merge(persona);
			} else {
				entityManager.persist(persona);
			}
			PersonaDetalle personaDetalle = afiliadoModeloDTO.convertToPersonaDetalleEntity();
			if (personaDetalle.getIdPersonaDetalle() != null) {
				entityManager.merge(personaDetalle);
			} else {
				entityManager.persist(personaDetalle);
			}
			logger.debug("Finaliza operacin actualizarDatosAfiliado(AfiliadoModeloDTO)");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo actualizarDatosAfiliado(AfiliadoModeloDTO)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		*/
		System.out.println("GLPI-45051->actualizarDatosAfiliado->a.actualizarDatosAfiliado");
	    ActualizarDatosAfiliadoRutine a= new ActualizarDatosAfiliadoRutine();
	    a.actualizarDatosAfiliado(afiliadoModeloDTO, entityManager);
	    
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * validarEmpleadorCeroTrabajadores(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean validarEmpleadorCeroTrabajadores(Long idEmpleador) {
		logger.debug("Inicio de mtodo consultarAfiliadosRestantes(Long idEmpleador,List<Long> idRolesAfiliado)");
		try {
			List<RolAfiliado> roles = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_EMPLEADOR_ESTADO)
					.setParameter("idEmpleador", idEmpleador).setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
					.getResultList();

			logger.debug("Fin de mtodo consultarAfiliadosRestantes(Long idEmpleador,List<Long> idRolesAfiliado)");
			if (roles.isEmpty()) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;

		} catch (Exception e) {
			logger.error(
					"Ocurri un error inesperado en el mtodo consultarAfiliadosRestantes(Long idEmpleador,List<Long> idRolesAfiliado)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * validarDesactivarAfiliadoCaja(java.lang.Long)
	 */
	@Override
	public Boolean validarDesactivarAfiliadoCaja(Long idAfiliado) {
		logger.debug("Inicio de mtodo validarDesactivarAfiliadoCaja(Long idAfiliado)");
		try {
			Integer rolesActivos = (Integer) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTEN_ROLES_ACTIVOS)
					.setParameter("idAfiliado", idAfiliado)
					.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name()).getSingleResult();

			logger.debug("Fin de mtodo validarDesactivarAfiliadoCaja(Long idAfiliado)");
			/*
			 * Si el afiliado tiene roles con estado diferente a INACTIVO,
			 * retorna false
			 */
			if (rolesActivos > 0) {
				return Boolean.FALSE;
			} else {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo validarDesactivarAfiliadoCaja(Long idAfiliado)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarTrabajadoresSucursal(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<TrabajadorEmpleadorDTO> consultarTrabajadoresSucursal(Long idEmpleador, Long idSucursal) {
		logger.debug("Fin de mtodo consultarTrabajadoresEmpresaSucursal(Long idEmpleador, Long idSucursal)");
		try {
			List<RolAfiliado> rolAfiliados = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_ID_EMPLEADOR_ID_SUCURSAL)
					.setParameter("idEmpleador", idEmpleador)
					.setParameter("estadoEmpleador", EstadoEmpleadorEnum.ACTIVO).setParameter("idSucursal", idSucursal)
					.getResultList();

			List<TrabajadorEmpleadorDTO> trabajadores = new ArrayList<TrabajadorEmpleadorDTO>();
			for (RolAfiliado rolAfiliado : rolAfiliados) {
				TrabajadorEmpleadorDTO trabajador = new TrabajadorEmpleadorDTO();
				trabajador.setTipoIdentificacion(rolAfiliado.getAfiliado().getPersona().getTipoIdentificacion());
				trabajador.setNumeroIdentificacion(rolAfiliado.getAfiliado().getPersona().getNumeroIdentificacion());
				trabajador.setPrimerNombre(rolAfiliado.getAfiliado().getPersona().getPrimerNombre());
				trabajador.setSegundoNombre(rolAfiliado.getAfiliado().getPersona().getSegundoNombre());
				trabajador.setPrimerApellido(rolAfiliado.getAfiliado().getPersona().getPrimerApellido());
				trabajador.setSegundoApellido(rolAfiliado.getAfiliado().getPersona().getSegundoApellido());
				trabajador.setNombreSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getNombre());
				trabajador.setIdSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa());
				trabajador.setCodigoSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getCodigo());
				trabajador.setIdPersona(rolAfiliado.getAfiliado().getPersona().getIdPersona());

				trabajadores.add(trabajador);
			}
			logger.debug(
					"Fin de mtodo consultarTrabajadoresEmpresaSucursal(Long idEmpleador, List<Long> idSucursales)");
			return trabajadores;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * Mtodo encargado de consultar el detalle de una persona
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return PersonaDetalle
	 */
	private PersonaDetalle consultarPersonaDetalle(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Inicia consultarPersonaDetalle(TipoIdentificacionEnum, String)");
		PersonaDetalle personaDetalle = null;
		try {
			personaDetalle = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
							PersonaDetalle.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();
		} catch (NoResultException noResult) {
			logger.debug("Finaliza consultarPersonaDetalle(TipoIdentificacionEnum, String): No existe el Afiliado");
		} catch (Exception e) {
			logger.error(
					"Ocurri un error inesperado en el mtodo consultarPersonaDetalle(TipoIdentificacionEnum, String)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operacin consultarPersonaDetalle(TipoIdentificacionEnum, String)");
		return personaDetalle;
	}

	/**
	 * Actualiza el Medio de Pago asociado a la Persona
	 * 
	 * @param medioDePagoModeloDTO
	 */
	@SuppressWarnings("unchecked")
public void actualizarMedioDePagoPersona(MedioDePagoModeloDTO medioDePagoModeloDTO) {
		System.out.println("**__** actualizarMedioDePagoPersona medioDePagoModeloDTO "+medioDePagoModeloDTO.toString());
		logger.debug("Inicia actualizarMedioDePagoPersona(MedioDePagoModeloDTO)");
		try {
			// Se verifica si el medio de pago es tarjeta para consultarla en anibol
			if (TipoMedioDePagoEnum.TARJETA.equals(medioDePagoModeloDTO.getTipoMedioDePago())) {
				System.out.println("**__** actualizarMedioDePagoPersona entra al if.tarjeta ");
				MedioDePagoModeloDTO medioPagoTarjeta = consultarTarjetaPersona(medioDePagoModeloDTO.getPersona().getTipoIdentificacion(),
						medioDePagoModeloDTO.getPersona().getNumeroIdentificacion());
				if (medioPagoTarjeta != null && medioPagoTarjeta.getNumeroTarjeta() != null) {
					System.out.println("**__** actualizarMedioDePagoPersona entra al if.tarjeta!=null ");
					medioDePagoModeloDTO.setNumeroTarjeta(medioPagoTarjeta.getNumeroTarjeta());
				}
			} 
			/* Identifica los medios de pago asociados a la persona */
			Map<TipoMedioDePagoEnum, MedioDePago> mediosPagoAsociados = new HashMap<>();
			/* Identifica los medios de pago */
			Map<TipoMedioDePagoEnum, MedioPagoPersona> mediosPagoPersona = new HashMap<>();

			/* Se consulta o crea la persona asociada al medio de pago. */
			Long idPersonaMedioPago = this.actualizarPersona(medioDePagoModeloDTO.getPersona());
			System.out.println("**__**actualizarMedioDePagoPersona idPersonaMedioPago "+idPersonaMedioPago);
			if (idPersonaMedioPago != null) {
				/*
				 * Se consulta si existen Medios de Pago asociados a la persona.
				 */
				List<Object[]> medioDePagoPersonaList = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_PERSONA)
						.setParameter("idPersona", idPersonaMedioPago).getResultList();
				System.out.println("**__**actualizarMedioDePagoPersona medioDePagoPersonaList "+ medioDePagoPersonaList);
				System.out.println("---");
				medioDePagoPersonaList.forEach(System.out::println);
				if (medioDePagoPersonaList != null && !medioDePagoPersonaList.isEmpty()) {
					for (Object[] mediosPago : medioDePagoPersonaList) {
						MedioDePago medioDePago = (MedioDePago) mediosPago[0];
						System.out.println("**__**actualizarMedioDePagoPersona medioDePago "+medioDePago.getIdMedioPago() + " "+medioDePago.getTipoMediopago());
						MedioPagoPersona medioPagoPersona = (MedioPagoPersona) mediosPago[1];
						System.out.println("**__**actualizarMedioDePagoPersona medioPagoPersona "+medioPagoPersona.getIdPersona()+ " "+ medioPagoPersona);
						mediosPagoAsociados.put(medioDePago.getTipoMediopago(), medioDePago);
						mediosPagoPersona.put(medioDePago.getTipoMediopago(), medioPagoPersona);
					}
				}
			}
			Boolean crearMedioPago = Boolean.TRUE;
			/* Si tiene Medios de Pago asociados */
			if (!mediosPagoAsociados.isEmpty()) {
				System.out.println("**__**actualizarMedioDePagoPersona if tiene mediosPagoAsociados "+mediosPagoAsociados);
				/*
				 * Se verifica si existe el tipo de Medio de pago a actualizar
				 */
				if (mediosPagoAsociados.containsKey(medioDePagoModeloDTO.getTipoMedioDePago())) {
					System.out.println("**__**actualizarMedioDePagoPersona mediosPagoAsociados.containsKey(medioDePagoModeloDTO.getTipoMedioDePago()) ");
					System.out.println("medioDePagoModeloDTO.getTipoMedioDePago() "+medioDePagoModeloDTO.getTipoMedioDePago());
					MedioDePago medioDePago = mediosPagoAsociados.get(medioDePagoModeloDTO.getTipoMedioDePago());
					/* Actualiza el Medio de Pago */
					/*
					medioDePago.setTipoMediopago(medioDePagoModeloDTO.getTipoMedioDePago());
					medioDePago = entityManager.merge(medioDePago);
					medioDePagoModeloDTO.convertToMedioDePagoEntity(medioDePago);
					 */
					entityManager.createNamedQuery(NamedQueriesConstants.AFILIADOS_ACTUALIZAR_TIPOPAGO_MEDIOPAGO)
						.setParameter("tipoDePago", medioDePagoModeloDTO.getTipoMedioDePago().name())
						.setParameter("idMedioDePago", medioDePago.getIdMedioPago())
						.executeUpdate();
					/* Se actualiza a activo el medio de Pago persona */
					MedioPagoPersona medioPagoPersona = mediosPagoPersona
							.get(medioDePagoModeloDTO.getTipoMedioDePago());
					medioPagoPersona.setMppTarjetaMultiservicio(medioDePagoModeloDTO.getTarjetaMultiservicio());		
					medioPagoPersona.setMedioActivo(Boolean.TRUE);
					logger.info("Antes del mergue medioPagoPersona: " + medioPagoPersona.getIdMedioPagoPersona());
					medioPagoPersona = entityManager.merge(medioPagoPersona);
					mediosPagoPersona.remove(medioDePagoModeloDTO.getTipoMedioDePago());
					crearMedioPago = Boolean.FALSE;
				}
			}
			/* Si se debe crear un nuevo Medio de Pago */
			if (crearMedioPago) {
				/* Si no tiene medio de Pago asociado se crea. */
				MedioDePago medioDePago = medioDePagoModeloDTO.convertToMedioDePagoEntity(null);
				medioDePago.setTipoMediopago(medioDePagoModeloDTO.getTipoMedioDePago());
				entityManager.persist(medioDePago);
				/* Se asocia el medio de pago a la persona */
				MedioPagoPersona medioPagoPersona = new MedioPagoPersona();
				medioPagoPersona.setIdPersona(idPersonaMedioPago);
				medioPagoPersona.setIdMedioDePago(medioDePago.getIdMedioPago());
				medioPagoPersona.setMedioActivo(Boolean.TRUE);
				medioPagoPersona.setMppTarjetaMultiservicio(medioDePagoModeloDTO.getTarjetaMultiservicio());
				entityManager.persist(medioPagoPersona);
			}
			
			/* Si tiene otros medios de Pago asociados se inactivan */
			if (!mediosPagoPersona.isEmpty()) {
				Collection<MedioPagoPersona> mediosInactivar = mediosPagoPersona.values();
				for (MedioPagoPersona medioPagoPersona : mediosInactivar) {
					logger.info("Dato id medio de pago persona a desactviar: " + medioPagoPersona.getIdMedioPagoPersona());
					medioPagoPersona.setMedioActivo(Boolean.FALSE);
					medioPagoPersona = entityManager.merge(medioPagoPersona);
					System.out.println("**__**actualizarMedioDePagoPersona !mediosPagoPersona.isEmpty() "+medioPagoPersona.getIdPersona());
				}
			}
			// Verificar si se ajusta el medio de pago de grupos familiares asociados a la persona
			procesarActualizacionMedioPagoPersonaEnGrupo(consultarGruposFamiliaresAsociadosPersona(idPersonaMedioPago),
					medioDePagoModeloDTO);
			logger.debug("Finaliza actualizarMedioDePagoPersona(MedioDePagoModeloDTO)");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo actualizarMedioDePagoPersona(MedioDePagoModeloDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * Procesa la creación de un nuevo medio de pago asociado a la persona
     * @param medioDePagoModeloDTO
     *        Id de la persona
     * @param idPersonaMedioPago
     *        Informacin de medio de pago a registrar
	*/
	private Long crearMedioDePago(MedioDePagoModeloDTO medioDePagoModeloDTO, Long idPersonaMedioPago) {
		logger.info("Inicia crearMedioDePago(MedioDePagoModeloDTO medioDePagoModeloDTO, Long idPersonaMedioPago)");
		MedioDePago medioDePagoNuevo = medioDePagoModeloDTO.convertToMedioDePagoEntity(null);
		medioDePagoNuevo.setTipoMediopago(medioDePagoModeloDTO.getTipoMedioDePago());
		entityManager.persist(medioDePagoNuevo);
		logger.info("**dmorales** - Crea el nuevo medio de pago id: " + medioDePagoNuevo.getIdMedioPago());
		
		/* Se asocia el medio de pago a la persona */
		MedioPagoPersona medioPagoPersonaNuevo = new MedioPagoPersona();
		medioPagoPersonaNuevo.setIdPersona(idPersonaMedioPago);
		medioPagoPersonaNuevo.setIdMedioDePago(medioDePagoNuevo.getIdMedioPago());
		medioPagoPersonaNuevo.setMedioActivo(Boolean.TRUE);
		entityManager.persist(medioPagoPersonaNuevo);
		logger.info("Crea el nuevo medioPagoPersona id: " + medioPagoPersonaNuevo.getIdMedioPagoPersona());
		logger.info("Finaliza crearMedioDePago(MedioDePagoModeloDTO medioDePagoModeloDTO, Long idPersonaMedioPago)");
		return medioDePagoNuevo.getIdMedioPago();
	}

    /**
     * Procesa la actualizacin de los medios de pago de grupo cuando el afiliado principal es el mismo administrador de subsidio
     * @param listGrupoFamiliar
     *        Lista de grupo familiar
     * @param medioDePagoModeloDTO
     *        Informacin de medio de pago a registrar
     */
    private void procesarActualizacionMedioPagoPersonaEnGrupo(List<Long> listGrupoFamiliar, MedioDePagoModeloDTO medioDePagoModeloDTO){
		logger.info("**__**procesarActualizacionMedioPagoPersonaEnGrupo listGrupoFamiliar "+listGrupoFamiliar);
		try {
			if (listGrupoFamiliar == null || listGrupoFamiliar.isEmpty()) {
				return;
			}
			for (Long idGrupo : listGrupoFamiliar) {
				medioDePagoModeloDTO.setIdGrupoFamiliar(idGrupo);
				medioDePagoModeloDTO.setAdmonSubsidio(medioDePagoModeloDTO.getPersona());
				medioDePagoModeloDTO.setAfiliadoEsAdministradorSubsidio(true);
				actualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO);
			}
		} catch (Exception e) {
			logger.error("Error inesperado en procesarActualizacionMedioPagoPersonaEnGrupo", e);
			return;
		}
    }

	/**
	 * Actualiza el Medio de Pago asociado al Grupo Familiar
	 * 
	 * @param medioDePagoModeloDTO
	 */
	@SuppressWarnings("unchecked")
	public void actualizarMedioDePagoGrupoFamiliar(MedioDePagoModeloDTO medioDePagoModeloDTO) {
		logger.warn("**__**actualizarMedioDePagoGrupoFamiliar  medioDePagoModeloDTO " +medioDePagoModeloDTO.toString());
		ActualizarMedioDePagoGrupoFamiliarRutine a = new ActualizarMedioDePagoGrupoFamiliarRutine();
		a.actualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO, entityManager);
	    
	}
	
	

	/**
	 * Actualiza el titular de la cuenta asociado al medio de pago
	 * Transferencia.
	 * 
	 * @param personaModeloDTO
	 * @return id del Titular de la cuenta.
	 */
	private Long actualizarPersona(PersonaModeloDTO personaModeloDTO) {
		Persona persona = null;
		Long idPersona = null;
		System.out.println("**__**actualizarPersona personaModeloDTO.identification "+personaModeloDTO.getNumeroIdentificacion());
		try {
			/* Se consulta la Persona Administrador Subsidio */
			persona = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION, Persona.class)
					.setParameter("numeroIdentificacion", personaModeloDTO.getNumeroIdentificacion())
					.setParameter("tipoIdentificacion", personaModeloDTO.getTipoIdentificacion()).getSingleResult();
			idPersona = persona.getIdPersona();
			System.out.println("**__**actualizarPersona persona "+persona+" getNumeroIdentificacion "+persona.getNumeroIdentificacion());
		} catch (NoResultException e) {
			logger.error("error consultando Persona Administrador Subsidio: "+e);
			persona = null;
		}
		/* Si la persona titular de la cuenta no existe se crea. */
		if (persona == null) {
			Persona personaTitular = personaModeloDTO.convertToPersonaEntity();
			entityManager.persist(personaTitular);
			PersonaDetalle personaDetalle = personaModeloDTO.convertToPersonaDetalleEntity();
			personaDetalle.setIdPersona(personaTitular.getIdPersona());
			entityManager.persist(personaDetalle);
			idPersona = personaTitular.getIdPersona();
			System.out.println("**__**actualizarPersona idPersona "+idPersona);
		}
		return idPersona;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#consultarMedioDePago(java
	 * .lang.Long)
	 */
	public MedioDePagoModeloDTO consultarMedioDePago(Long idGrupoFamiliar, TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoMedioDePagoEnum tipoMedioDePagoEnum) {
		logger.debug("Inicia consultarMedioDePago(Long, TipoIdentificacionEnum, String, TipoMedioDePagoEnum)");
		MedioDePagoModeloDTO medioDePagoReturn = new MedioDePagoModeloDTO();
		MedioDePago medioDePago = null;
		try {
			if (idGrupoFamiliar != null) {
				/*
				 * Se consulta por tipo de Medio de pago que tenga asociado el
				 * Grupo Familiar.
				 */
				if (tipoMedioDePagoEnum != null) {
					medioDePago = (MedioDePago) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_GRUPOFAMILIAR_TIPO)
							.setParameter("idGrupoFamiliar", idGrupoFamiliar)
							.setParameter("tipoMedioPago", tipoMedioDePagoEnum).getSingleResult();
				} else {
					/*
					 * Se consulta el Medio de Pago activo que tenga asociado el
					 * grupo Familiar.
					 */
					medioDePago = (MedioDePago) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_ACTIVO_GRUPOFAMILIAR)
							.setParameter("idGrupoFamiliar", idGrupoFamiliar).setParameter("medioActivo", Boolean.TRUE)
							.getSingleResult();
				}

				medioDePagoReturn.convertToDTO(medioDePago);
			} else if (tipoIdentificacion != null && numeroIdentificacion != null) {
				if (tipoMedioDePagoEnum != null) {
					medioDePago = (MedioDePago) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_PERSONA_TIPO)
							.setParameter("tipoIdentificacion", tipoIdentificacion)
							.setParameter("numeroIdentificiacion", numeroIdentificacion)
							.setParameter("tipoMedioPago", tipoMedioDePagoEnum).getSingleResult();
				} else {
					/*
					 * Se consulta el Medio de Pago activo que tenga asociado la
					 * persona.
					 */
					medioDePago = (MedioDePago) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_PERSONA_ESTADO)
							.setParameter("tipoIdentificacion", tipoIdentificacion)
							.setParameter("numeroIdentificacion", numeroIdentificacion)
							.setParameter("medioActivo", Boolean.TRUE).getSingleResult();
				}
				 MedioPagoPersona medioPagoPersona = (MedioPagoPersona) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIODEPAGO_PERSONA_ESTADO_2)
							.setParameter("tipoIdentificacion", tipoIdentificacion)
							.setParameter("numeroIdentificacion", numeroIdentificacion)
							.setParameter("medioActivo", Boolean.TRUE).getSingleResult();
				

				medioDePagoReturn.convertToDTO(medioDePago);
				medioDePagoReturn.setTarjetaMultiservicio(medioPagoPersona.getMppTarjetaMultiservicio() != null ? medioPagoPersona.getMppTarjetaMultiservicio() : Boolean.FALSE);
			}
		} catch (Exception e) {
			medioDePagoReturn = new MedioDePagoModeloDTO();
			medioDePago = null;
		}
		logger.debug("Finaliza consultarMedioDePago(Long, TipoIdentificacionEnum, String, TipoMedioDePagoEnum)");
		return medioDePagoReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * verificarExisteAfiliadoAsociado(com.asopagos.dto.modelo.PersonaModeloDTO)
	 */
	@Override
	public RolAfiliado verificarExisteAfiliadoAsociado(PersonaEmpresaDTO personaEmpresaDTO) {
		logger.debug("Inicia verificarExisteAfiliadoAsociado(personaEmpresaDTO)");

		RolAfiliadoModeloDTO rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO();

		try {
			RolAfiliado rolAfiliado = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_POR_EMPLEADOR_Y_PERSONA,
							RolAfiliado.class)
					.setParameter("tipoIdentificacion", personaEmpresaDTO.getPersona().getTipoIdentificacion())
					.setParameter("numeroIdentificacion", personaEmpresaDTO.getPersona().getNumeroIdentificacion())
					.setParameter("tipoIdentificacionEmpleador",
							personaEmpresaDTO.getEmpleador().getTipoIdentificacion())
					.setParameter("numeroIdentificacionEmpleador",
							personaEmpresaDTO.getEmpleador().getNumeroIdentificacion())
					.getSingleResult();

			logger.debug("Finaliza verificarExisteAfiliadoAsociado(PersonaModeloDTO)");
			return rolAfiliado;

			// List<RolAfiliado> rolesAfiliadoPersona =(List<RolAfiliado>)
			// entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_POR_PERSONA)
			// .setParameter("tipoIdentificacion",
			// personaEmpresaDTO.getPersona().getTipoIdentificacion())
			// .setParameter("numeroIdentificacion",
			// personaEmpresaDTO.getPersona().getNumeroIdentificacion())
			// .getResultList();
			//
			//
			// List<RolAfiliado> rolesAfiliadoEmpleador =(List<RolAfiliado>)
			// entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_POR_EMPLEADOR)
			// .setParameter("tipoIdentificacionEmpleador",
			// personaEmpresaDTO.getEmpleador().getTipoIdentificacion())
			// .setParameter("numeroIdentificacionEmpleador",
			// personaEmpresaDTO.getEmpleador().getNumeroIdentificacion())
			// .getResultList();
			//
			//
			// if(rolesAfiliadoPersona != null &&
			// !rolesAfiliadoPersona.isEmpty() &&
			// rolesAfiliadoEmpleador != null &&
			// !rolesAfiliadoEmpleador.isEmpty()){
			//
			// for (int i = 0; i < rolesAfiliadoEmpleador.size(); i++) {
			// for (int j = 0; j < rolesAfiliadoPersona.size(); j++) {
			//
			// if(rolesAfiliadoEmpleador.get(i).equals(rolesAfiliadoPersona.get(j))){
			// logger.debug("Finaliza
			// verificarExisteAfiliadoAsociado(PersonaModeloDTO)");
			// return rolesAfiliadoEmpleador.get(i);
			// }
			// }
			// }
			// }
			//
			//
			// logger.debug("Finaliza
			// verificarExisteAfiliadoAsociado(PersonaModeloDTO): No existe el
			// Afiliado");
			// return null;

		} catch (NoResultException nre) {
			logger.debug("Finaliza verificarExisteAfiliadoAsociado(PersonaModeloDTO): No existe el Afiliado");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarBeneficariosMayorEdadConTI()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosMayorEdadConTI() {
		logger.info("Inicia operacin consultarBeneficariosMayorEdadConTI()");
		try {
			Calendar fechaActualSistema = Calendar.getInstance();

			List<Long> personaBeneficiario = new ArrayList<>();
			/* Se asocian las calsificaciones de tipo beneficiario HIJO */
			List<ClasificacionEnum> listaClasificacion = new ArrayList<>();
			listaClasificacion.add(ClasificacionEnum.HIJO_BIOLOGICO);
			listaClasificacion.add(ClasificacionEnum.HIJO_ADOPTIVO);
			listaClasificacion.add(ClasificacionEnum.HIJASTRO);
			listaClasificacion.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
			listaClasificacion.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);

			List<Beneficiario> listaBeneficiarios = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_MAYOR_EDAD_CON_TI)
					.setParameter("listaClasificacion", listaClasificacion)
					.setParameter("tipoIdentificacion", TipoIdentificacionEnum.TARJETA_IDENTIDAD)
					.setParameter("invalidez", Boolean.TRUE).setParameter("fechaActual", fechaActualSistema)
					.setParameter("edad", NumerosEnterosConstants.DIECIOCHO)
					.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
					.setParameter("estadoBeneficiario", EstadoAfiliadoEnum.ACTIVO).getResultList();

			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiariosDTO = new ArrayList<>();
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				for (Beneficiario beneficiario : listaBeneficiarios) {
					BeneficiarioNovedadAutomaticaDTO beneficiarioDTO = new BeneficiarioNovedadAutomaticaDTO();
					beneficiarioDTO.setIdBeneficiario(beneficiario.getIdBeneficiario());
					beneficiarioDTO.setIdPersonaAfiliado(beneficiario.getAfiliado().getPersona().getIdPersona());
					System.out.println("Beneficiario : " + beneficiario.getIdBeneficiario() + " Persona (afiliado) : " + beneficiario.getAfiliado().getPersona().getIdPersona());

					listaBeneficiariosDTO.add(beneficiarioDTO);
				}
			}
			logger.debug("Finaliza operacin consultarBeneficariosMayorEdadConTI()");
			return listaBeneficiariosDTO;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarBeneficariosMayorEdadConTI()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * retirarBeneficiarioMayorEdadConTI(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void retirarBeneficiarioMayorEdadConTI(List<Long> idsBeneficiario) {
		logger.info("Inicia operacin retirarBeneficiarioMayorEdadConTI(List<Long>)");
		try {
			int registrosPag = 1000;
			int contRegistros = 0;
			int cantBeneficiarios  = idsBeneficiario.size();
			
			ArrayList<Long> paginaIdBen = new ArrayList<>();
			
			do{
				for (int i = 0; i < registrosPag && contRegistros<cantBeneficiarios; i++) {
				    System.out.println("Contador " + contRegistros);
					paginaIdBen.add(idsBeneficiario.get(contRegistros));
					System.out.println("Beneficiario " + idsBeneficiario.get(contRegistros));
					contRegistros++;
				}
				
				List<Beneficiario> beneficiariosRetirar = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_RETIRO_POR_ID)
						.setParameter("listaIdBeneficiario", paginaIdBen).getResultList();

				if (beneficiariosRetirar != null && !beneficiariosRetirar.isEmpty()) {
					retirarBeneficiarioCambioCategoria(beneficiariosRetirar,MotivoDesafiliacionBeneficiarioEnum.BENEFICIARIO_SIN_ACTUALIZAR_TIPO_DOCUMENTO);
				/*Comentado para contemplar categorias ya que se trata de un retiro el metodo siguiente es dinamico usado tambien por retiro BeneficariosXEdad
					for (Beneficiario beneficiario : beneficiariosRetirar) {
					    System.out.println("2 if beneficiario " + beneficiario.getIdBeneficiario());
						entityManager.merge(beneficiario);
						beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
						beneficiario.setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum.BENEFICIARIO_SIN_ACTUALIZAR_TIPO_DOCUMENTO);
						beneficiario.setFechaRetiro(new Date());

					}*/
				}
				paginaIdBen.clear();
			}while(contRegistros<cantBeneficiarios);
		
			logger.debug("Finaliza operacin retirarBeneficiarioMayorEdadConTI(List<Long>)");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo retirarBeneficiarioMayorEdadConTI(List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarBeneficariosXEdad()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosXEdad() {
		logger.info("afiliadosInicia operacin consultarBeneficariosXEdad()");
		try {
			Calendar fechaActualSistema = Calendar.getInstance();
			String edadParametro = (String) CacheManager
					.getParametro(ParametrosSistemaConstants.EDAD_RETIRO_BENEFICIARIO);
					
			List<String> listaClasificacion = new ArrayList<>();
			listaClasificacion.add(ClasificacionEnum.HIJO_BIOLOGICO.name());
			listaClasificacion.add(ClasificacionEnum.HIJO_ADOPTIVO.name());
			listaClasificacion.add(ClasificacionEnum.HIJASTRO.name());
			listaClasificacion.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES.name());
			listaClasificacion.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA.name());

			List<Object[]> listaBeneficiarios  = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_POR_EDAD)
					.setParameter("listaClasificacion", listaClasificacion)
					//.setParameter("invalidez", Boolean.TRUE)
					//.setParameter("fechaActual", fechaActualSistema)
					.setParameter("edad", new Integer(edadParametro))
					.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name())
					.setParameter("estadoBeneficiario", EstadoAfiliadoEnum.ACTIVO.name()).getResultList();
	logger.info("CONSULTAR_BENEFICIARIOS_POR_EDAD cantidad: "+listaBeneficiarios.size()); 
			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiariosDTO = new ArrayList<>();
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				for (Object[] beneficiario : listaBeneficiarios) {
					BeneficiarioNovedadAutomaticaDTO beneficiarioDTO = new BeneficiarioNovedadAutomaticaDTO();
					beneficiarioDTO.setIdBeneficiario(beneficiario[0] != null ? Long.valueOf(beneficiario[0].toString()) : null);
					beneficiarioDTO.setIdPersonaAfiliado(beneficiario[1] != null ? Long.valueOf(beneficiario[1].toString()) : null);
					 listaBeneficiariosDTO.add(beneficiarioDTO);
				}
				logger.info("afiliadosfinalizanamedquery no vacio");
			}else{
				logger.info("afiliadosfinalizanamedquery  vacio");
				listaBeneficiariosDTO=null;
			}

			logger.info("}Finaliza operacin consultarBeneficariosXEdad()");
			return listaBeneficiariosDTO;

		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarBeneficariosXEdad()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#retirarBeneficiarioXEdad(
	 * java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override

	public void retirarBeneficiarioXEdad(List<Long> idsBeneficiario) {
		logger.debug("Inicia operacin retirarBeneficiarioXEdad(List<Long>)");
			logger.info("**__**afiliadosInicia operacin retirarBeneficiarioXEdad(List<Long>)");
		try {
			int registrosPag = 1000;
			int contRegistros = 0;
			int cantBeneficiarios  = idsBeneficiario.size();
			ArrayList<Long> paginaIdBen = new ArrayList<>();
			do{
				for (int i = 0; i < registrosPag && contRegistros < cantBeneficiarios; i++) {
					paginaIdBen.add(idsBeneficiario.get(contRegistros));
					contRegistros++;
				}
				List<Beneficiario> beneficiariosRetirar = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_RETIRO_POR_ID)
				.setParameter("listaIdBeneficiario", paginaIdBen).getResultList();

				if (beneficiariosRetirar != null && !beneficiariosRetirar.isEmpty()) {
					retirarBeneficiarioCambioCategoria(beneficiariosRetirar,MotivoDesafiliacionBeneficiarioEnum.BENEFICIARIO_CUMPLIO_EDAD_X);
				}
				paginaIdBen.clear();
				    System.out.println("retirarBeneficiarioXEdad Contador " + contRegistros);
			}while(contRegistros < cantBeneficiarios);
			logger.info("Finaliza operacin retirarBeneficiarioXEdad(List<Long>)");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo retirarBeneficiarioXEdad(List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
public void retirarBeneficiarioCambioCategoria(List<Beneficiario> beneficiariosRetirar,MotivoDesafiliacionBeneficiarioEnum motivoRetiro) {

				if (beneficiariosRetirar != null && !beneficiariosRetirar.isEmpty()) {
					for (Beneficiario beneficiario : beneficiariosRetirar) {
						entityManager.merge(beneficiario);
						beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
						beneficiario.setMotivoDesafiliacion(motivoRetiro);
						beneficiario.setFechaRetiro(new Date());
						//categoria sin categoriaentityManager.persist(categorias); 
						//nuevo si se inactiva en novedad queda sin categoria
						if(beneficiario.getIdBeneficiario() != null){
						//CategoriaDTO categoria=null;
						List<Object[]> lstObject = null;
						try{
						 lstObject = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_POR_ID_BENEFICIARIO_INACTIVOS)
						.setParameter("idBeneficiario", beneficiario.getIdBeneficiario()).getResultList();
						}catch (NoResultException e) {
							lstObject = null;
				
						}
							if (lstObject != null && !lstObject.isEmpty() ) {
								for (Object[] object : lstObject) {
							CategoriaDTO categoria = new CategoriaDTO();
								categoria.setTipoAfiliado( object[1] != null ? TipoAfiliadoEnum.valueOf(object[1].toString()) : null);
								categoria.setTipoBeneficiario(object[3] != null ? TipoBeneficiarioEnum.valueOf(object[3].toString()) : null);
								categoria.setClasificacionAfiliado(object[4] != null ? ClasificacionEnum.valueOf(object[4].toString()) : null);
								categoria.setTotalIngresoMesada(object[5] != null ? (BigDecimal)object[5] : null);
								categoria.setIdAfiliado(object[9] != null ? Long.parseLong(object[9].toString()) : null);
								categoria.setIdBeneficiario( beneficiario.getIdBeneficiario());
								categoria.setAfiliadoPrincipal(false);
								categoria.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.RETIRO);
								categoria.setFechaCambioCategoria(Calendar.getInstance().getTime());
								categoria.setCategoriaPersona(CategoriaPersonaEnum.valueOf("SIN_CATEGORIA"));
								categoria.setTarifUVTPersona(CategoriaPersonaEnum.valueOf("SIN_TARIFA"));
								Categoria cat = CategoriaDTO.convertCategoriaDTO(categoria);
                                entityManager.persist(cat);
								}
							}else{
							logger.info("**__** EL beneficiario no tiene registro en la tabla categorias retirarBeneficiarioCambioCategoria: "+beneficiario.getIdBeneficiario());
						Object[] ObjectAfiliado = null;
							    try{
									Categoria categoriasCambiarExiste = new Categoria();
								ObjectAfiliado = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO)
								.setParameter("idBeneficiario", beneficiario.getIdBeneficiario()).getSingleResult();
								categoriasCambiarExiste.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.RETIRO);
								categoriasCambiarExiste.setFechaCambioCategoria(Calendar.getInstance().getTime());
								categoriasCambiarExiste.setCategoriaPersona(CategoriaPersonaEnum.valueOf("SIN_CATEGORIA"));
								categoriasCambiarExiste.setTarifUVTPersona(CategoriaPersonaEnum.valueOf("SIN_TARIFA"));
								categoriasCambiarExiste.setTotalIngresoMesada(BigDecimal.valueOf(Double.valueOf(ObjectAfiliado[2].toString())));
								categoriasCambiarExiste.setAfiliadoPrincipal(false);
								categoriasCambiarExiste.setIdBeneficiario(beneficiario.getIdBeneficiario());
								categoriasCambiarExiste.setTipoAfiliado(TipoAfiliadoEnum.valueOf(ObjectAfiliado[0].toString()));
								categoriasCambiarExiste.setClasificacionAfiliado(ClasificacionEnum.valueOf(ObjectAfiliado[3].toString()));
										//Afiliado afiliado = new Afiliado();
										//afiliado.setIdAfiliado(ObjectAfiliado[1]);
										logger.info("**__** BENEDFICIARIO ID :"+beneficiario.getIdBeneficiario());
								categoriasCambiarExiste.setIdAfiliado(Long.valueOf(ObjectAfiliado[1].toString()));
								entityManager.persist(categoriasCambiarExiste); 
								}catch (NoResultException eb) {
									logger.info("**__** CONSULTA VACIA CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO");
								}
							}
						}
					 
					}
					
				}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarBeneficariosXEdadCambioCategoria()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosXEdadCambioCategoria() {
		logger.info(">>Inicia operacin consultarBeneficariosXEdadCambioCategoria()");
		try {
			Calendar fechaActualSistema = Calendar.getInstance();
			String edadParametro = (String) CacheManager
					.getParametro(ParametrosSistemaConstants.EDAD_CAMBIO_CATEGORIA_BENEFICIARIO);
			List<String> listaClasificacion = new ArrayList<>();
			listaClasificacion.add(ClasificacionEnum.HIJO_BIOLOGICO.name());
			listaClasificacion.add(ClasificacionEnum.HIJO_ADOPTIVO.name());
			listaClasificacion.add(ClasificacionEnum.HIJASTRO.name());
			listaClasificacion.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES.name());
			listaClasificacion.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA.name());

			List<Object[]> listaBeneficiarios = (List<Object[]>)entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_CAMBIO_CATEGORIA_POR_EDAD)
					.setParameter("listaClasificacion", listaClasificacion)
				 	//.setParameter("fechaActual", fechaActualSistema)
					.setParameter("edad", Integer.valueOf(edadParametro))
					.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name())
					.setParameter("estadoBeneficiario", EstadoAfiliadoEnum.ACTIVO.name())
					.getResultList();

			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiariosDTO = new ArrayList<>();
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				for (Object[] beneficiario : listaBeneficiarios) {					
                                    BeneficiarioNovedadAutomaticaDTO beneficiarioDTO = new BeneficiarioNovedadAutomaticaDTO();
                                    beneficiarioDTO.setIdBeneficiario(beneficiario[0] != null ? Long.valueOf(beneficiario[0].toString()) : null);
                                    beneficiarioDTO.setIdPersonaAfiliado(beneficiario[1] != null ? Long.valueOf(beneficiario[1].toString()) : null);
                                    listaBeneficiariosDTO.add(beneficiarioDTO);
				}
			}else{
logger.info("snull listaBeneficiarios");
			}
			
			logger.info("-----Finaliza operacin consultarBeneficariosXEdadCambioCategoria()");
			return listaBeneficiariosDTO;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarBeneficariosXEdadCambioCategoria()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
	/**
     * Cambia la categpria de los beneficiarios hijos que lleguen por parametro
     * 
     *  @param idsBeneficiariosHijos
     *         Identificadores de beneficiarios (Hijos)
     */
	@Override
	public void cambiarCategoriaBeneficiarioHijosCertificadoEscolar(List<Long> datosHijos) {

		for (Long idBen : datosHijos) {
			Categoria categoria = new Categoria();
			Object[] datosCategoria;
			try{ 
				datosCategoria =(Object[]) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_BENEFICIARIO_SI_EXISTE_CATEGORIA)
					.setParameter("idBeneficiario", idBen)
					.getSingleResult(); 
			}

			catch (NoResultException e) {
				logger.info("**__** CONSULYTA VACIA CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO");
				datosCategoria = null;
				
			}
				if (datosCategoria != null) {
				categoria.setTipoAfiliado( datosCategoria[1] != null ? TipoAfiliadoEnum.valueOf(datosCategoria[1].toString()) : null);
				categoria.setTipoBeneficiario(datosCategoria[3] != null ? TipoBeneficiarioEnum.valueOf(datosCategoria[3].toString()) : null);
				categoria.setClasificacionAfiliado(datosCategoria[4] != null ? ClasificacionEnum.valueOf(datosCategoria[4].toString()) : null);
				categoria.setCategoriaPersona(CategoriaPersonaEnum.C);
				categoria.setTotalIngresoMesada(datosCategoria[5] != null ? (BigDecimal)datosCategoria[5] : null);
				categoria.setIdAfiliado(datosCategoria[9] != null ? Long.parseLong(datosCategoria[9].toString()) : null);
				categoria.setIdBeneficiario(idBen);
				categoria.setAfiliadoPrincipal(false);
				categoria.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA);
				categoria.setFechaCambioCategoria(Calendar.getInstance().getTime());

				entityManager.persist(categoria); 

				}else{
					logger.info("**__** EL beneficiario no tiene registro en la tabla categorias "+idBen);
				Object[] ObjectAfiliado = null;
						try{
							Categoria categoriasCambiarExiste = new Categoria();
						ObjectAfiliado = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO)
						.setParameter("idBeneficiario", idBen).getSingleResult();
						categoriasCambiarExiste.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA);
						categoriasCambiarExiste.setFechaCambioCategoria(Calendar.getInstance().getTime());
						categoriasCambiarExiste.setCategoriaPersona(CategoriaPersonaEnum.C);
						categoriasCambiarExiste.setTotalIngresoMesada(BigDecimal.valueOf(Double.valueOf(ObjectAfiliado[2].toString())));
						categoriasCambiarExiste.setAfiliadoPrincipal(false);
						categoriasCambiarExiste.setIdBeneficiario(idBen);
						categoriasCambiarExiste.setTipoAfiliado(TipoAfiliadoEnum.valueOf(ObjectAfiliado[0].toString()));
						categoriasCambiarExiste.setClasificacionAfiliado(ClasificacionEnum.valueOf(ObjectAfiliado[3].toString()));
								//Afiliado afiliado = new Afiliado();
								//afiliado.setIdAfiliado(ObjectAfiliado[1]);
								logger.info("**__** BENEDFICIARIO ID :"+idBen);
						categoriasCambiarExiste.setIdAfiliado(Long.valueOf(ObjectAfiliado[1].toString()));
						entityManager.persist(categoriasCambiarExiste); 
						}catch (NoResultException eb) {
							logger.info("**__** CONSULTA VACIA CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO");
						}
					}
			}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarBeneficariosCertificadoEscolarCambioCategoria()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosCertificadoEscolarCambioCategoria(String sinCertificado) {
		logger.info(">>Inicia operacin consultarBeneficariosCertificadoEscolarCambioCategoria()");
		try {
			String edadParametro = (String) CacheManager
					.getParametro(ParametrosSistemaConstants.EDAD_CAMBIO_CATEGORIA_BENEFICIARIO_HIJOS_CERTIFICADO_ESCOLAR);

			List<String> edades = Arrays.asList(edadParametro.split(","));
			List<String> listaClasificacion = new ArrayList<>();
			listaClasificacion.add(ClasificacionEnum.HIJO_BIOLOGICO.name());
			listaClasificacion.add(ClasificacionEnum.HIJO_ADOPTIVO.name());
			listaClasificacion.add(ClasificacionEnum.HIJASTRO.name());
			listaClasificacion.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES.name());
			listaClasificacion.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA.name());


			List<Object[]> listaBeneficiarios = (List<Object[]>)entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_CAMBIO_CATEGORIA_POR_CERTIFICADO_ESCOLAR)
					.setParameter("listaClasificacion", listaClasificacion)
					.setParameter("edadMin", Integer.valueOf(edades.get(0)))
					.setParameter("edadMax", Integer.valueOf(edades.get(1)))
					.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name())
					.setParameter("estadoBeneficiario", EstadoAfiliadoEnum.ACTIVO.name())
					.setParameter("sinCertificado", sinCertificado.equals("sinCertificado")? 1 : 0)
					.setMaxResults(3000).getResultList();
		
			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiariosDTO = new ArrayList<>();
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				for (Object[] beneficiario : listaBeneficiarios) {					
                                    BeneficiarioNovedadAutomaticaDTO beneficiarioDTO = new BeneficiarioNovedadAutomaticaDTO();
                                    beneficiarioDTO.setIdBeneficiario(beneficiario[0] != null ? Long.valueOf(beneficiario[0].toString()) : null);
                                    beneficiarioDTO.setIdPersonaAfiliado(beneficiario[1] != null ? Long.valueOf(beneficiario[1].toString()) : null);
                                    listaBeneficiariosDTO.add(beneficiarioDTO);
				}
			}else{
logger.info("snull listaBeneficiarios");
			}
			
			logger.info("-----Finaliza operacin consultarBeneficariosCertificadoEscolarCambioCategoria()");
			return listaBeneficiariosDTO;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarBeneficariosCertificadoEscolarCambioCategoria()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

		/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarBeneficariosPadresXEdadCambioCategoria()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficiarioNovedadAutomaticaDTO> consultarBeneficariosPadresXEdadCambioCategoria() {
		logger.info(">>Inicia operacin consultarBeneficariosPadresXEdadCambioCategoria()");
		try {
			String edadParametro = (String) CacheManager
					.getParametro(ParametrosSistemaConstants.EDAD_CAMBIO_CATEGORIA_BENEFICIARIO_PADRE);

			List<String> listaClasificacion = new ArrayList<>();
			listaClasificacion.add(ClasificacionEnum.PADRE.name());
			listaClasificacion.add(ClasificacionEnum.MADRE.name());

			List<Object[]> listaBeneficiarios = (List<Object[]>)entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_CAMBIO_CATEGORIA_PADRES_MENORES_X_EDAD)
					.setParameter("listaClasificacion", listaClasificacion)
					.setParameter("edad", edadParametro)
					.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name())
					.setParameter("estadoBeneficiario", EstadoAfiliadoEnum.ACTIVO.name())
					.setMaxResults(2000).getResultList();

			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiariosDTO = new ArrayList<>();
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				for (Object[] beneficiario : listaBeneficiarios) {					
                                    BeneficiarioNovedadAutomaticaDTO beneficiarioDTO = new BeneficiarioNovedadAutomaticaDTO();
                                    beneficiarioDTO.setIdBeneficiario(beneficiario[0] != null ? Long.valueOf(beneficiario[0].toString()) : null);
                                    beneficiarioDTO.setIdPersonaAfiliado(beneficiario[1] != null ? Long.valueOf(beneficiario[1].toString()) : null);
                                    listaBeneficiariosDTO.add(beneficiarioDTO);
				}
			}else{
logger.info("snull listaBeneficiarios");
			}
			
			logger.info("-----Finaliza operacin consultarBeneficariosPadresXEdadCambioCategoria()");
			return listaBeneficiariosDTO;
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo consultarBeneficariosPadresXEdadCambioCategoria()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	public void cambiarMarcaEmpresaTrasladadaCCF(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.info("2G Entra metodo de cambio");
		logger.info("getNumeroIdAfiliado"+datosEmpleador.getIdEmpleador());
		// logger.info("getNumeroIdAfiliado"+datosEmpleador.getNumeroIdAfiliado());

		logger.info("Inicia operación cambiarMarcaEmpresaTrasladadaCCF");
			try {

				if (datosEmpleador.getTrasladoCajasCompensacion() != null) {
					if (datosEmpleador.getTrasladoCajasCompensacion() != null) {
						Boolean marcaBD = (Boolean)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MARCA_TRASLADO_EMPRESAS_CCF)
						.setParameter("idEmpleador", datosEmpleador.getIdEmpleador())
						// .setParameter("tipoIdentificacion", datosEmpleador.getNumeroIdAfiliado())
						.getSingleResult();
						
						logger.info("Marca"+ marcaBD);
						
						if (marcaBD == null || !marcaBD.equals(datosEmpleador.getTrasladoCajasCompensacion())) {
							entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_MARCA_TRASLADO_EMPRESAS_CCF)
						.setParameter("marca", datosEmpleador.getTrasladoCajasCompensacion())
						.setParameter("idEmpleador", datosEmpleador.getIdEmpleador())
						// .setParameter("tipoIdentificacion", datosEmpleador.getNumeroIdAfiliado())
						.executeUpdate();
						}
					}
					
				} 
			} catch (Exception e) {
				logger.error("Ocurrió un error inesperado en actualizarBeneficioEmpleador(BeneficioEmpleador)", e);
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
			logger.debug("Finaliza operación actualizarBeneficioEmpleador(BeneficioEmpleador)");

		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * cambiarCategoriaBeneficiarioXEdad(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cambiarCategoriaBeneficiarioXEdad(List<Long> idsBeneficiario) {
		logger.info("**__**afiliadosInicia operacin cambiarCategoriaBeneficiarioXEdad(List<Long>)");
		List<Long> idsBeneficiarioList1=null;
		List<Long> idsBeneficiarioList2=null;
		List<Long> idsBeneficiarioList3=null;
		List<Long> idsBeneficiarioList4=null;
		List<Long> idsBeneficiarioList5=null;
		List<Long> idsBeneficiarioListaconsultar=null;
    if(idsBeneficiario.size()>=2100){
				int division=idsBeneficiario.size()/5;
				//idsBeneficiarioparte1=idsBeneficiario.subList(0, idsBeneficiario.size()/2);
				idsBeneficiarioList1=idsBeneficiario.subList(0, division);
				idsBeneficiarioList2=idsBeneficiario.subList(division, (division * 2));
				idsBeneficiarioList3=idsBeneficiario.subList((division * 2), (division * 3));
				idsBeneficiarioList4=idsBeneficiario.subList((division * 3), (division * 4));
				idsBeneficiarioList5=idsBeneficiario.subList((division * 4),idsBeneficiario.size());
				
			}
			int ciclos=6;
		try {
			
			//	if(idsBeneficiario.size() < 2100){
			//		ciclos=2;
			//	}
			//	for(int i = 1; i < ciclos; i++){
			//		if(i==1){
			//			if(idsBeneficiario.size()>=2100){
			//			idsBeneficiarioListaconsultar=null;
			//			idsBeneficiarioListaconsultar=idsBeneficiarioList1;
			//			}else{
			//			idsBeneficiarioListaconsultar=idsBeneficiario;
			//			}
			//		}else if(i==2){
			//			idsBeneficiarioListaconsultar=null;
			//			idsBeneficiarioListaconsultar=idsBeneficiarioList2;
			//		}else if(i==3){
			//			idsBeneficiarioListaconsultar=null;
			//			idsBeneficiarioListaconsultar=idsBeneficiarioList3;
			//		}else if(i==4){
			//			idsBeneficiarioListaconsultar=null;
			//			idsBeneficiarioListaconsultar=idsBeneficiarioList4;
			//		}else if(i==5){
			//			idsBeneficiarioListaconsultar=null;
			//			idsBeneficiarioListaconsultar=idsBeneficiarioList5;
			//		}
				//	if(idsBeneficiarioListaconsultar!=null){
					String categoriaParametro = (String) CacheManager
							.getParametro(ParametrosSistemaConstants.CATEGORIA_CAMBIO_BENEFICIARIO);
		//	for(Long idb : idsBeneficiarioListaconsultar){
					for(Long idb : idsBeneficiario){
						Categoria categoriasCambiarExiste = new Categoria();
						Object[] lstCategoriasCambiar = null;
						try{ 
						lstCategoriasCambiar =(Object[]) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_BENEFICIARIO_SI_EXISTE_CATEGORIA)
							.setParameter("idBeneficiario", idb).getSingleResult();
								if (lstCategoriasCambiar != null ) {
							categoriasCambiarExiste.setIdCategoria(Long.valueOf(lstCategoriasCambiar[0].toString()));
							categoriasCambiarExiste.setTipoAfiliado( lstCategoriasCambiar[1] != null ? TipoAfiliadoEnum.valueOf(lstCategoriasCambiar[1].toString()) : null);
							categoriasCambiarExiste.setTipoBeneficiario(lstCategoriasCambiar[3] != null ? TipoBeneficiarioEnum.valueOf(lstCategoriasCambiar[3].toString()) : null);
							categoriasCambiarExiste.setClasificacionAfiliado(lstCategoriasCambiar[4] != null ? ClasificacionEnum.valueOf(lstCategoriasCambiar[4].toString()) : null);
							categoriasCambiarExiste.setTotalIngresoMesada(lstCategoriasCambiar[5] != null ? (BigDecimal)lstCategoriasCambiar[5] : null);
							categoriasCambiarExiste.setIdAfiliado(lstCategoriasCambiar[9] != null ? Long.parseLong(lstCategoriasCambiar[9].toString()) : null);
							categoriasCambiarExiste.setIdBeneficiario(idb);
							categoriasCambiarExiste.setAfiliadoPrincipal(false);
							categoriasCambiarExiste.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NOVEDAD_ACTUALIZACION_POR_X_EDAD_BENEFICIARIO);
							categoriasCambiarExiste.setFechaCambioCategoria(Calendar.getInstance().getTime());
							categoriasCambiarExiste.setCategoriaPersona(CategoriaPersonaEnum.valueOf(categoriaParametro));
							entityManager.merge(categoriasCambiarExiste); 
								}
						}catch (NoResultException nre) {
						logger.info("**__** EL beneficiario no tiene registro en la tabla categorias se creara uno idbeneficiario: "+idb);
						Object[] ObjectAfiliado = null;
							try{
							ObjectAfiliado = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO)
							.setParameter("idBeneficiario", idb).getSingleResult();
							categoriasCambiarExiste.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NOVEDAD_ACTUALIZACION_POR_X_EDAD_BENEFICIARIO);
							categoriasCambiarExiste.setFechaCambioCategoria(Calendar.getInstance().getTime());
							categoriasCambiarExiste.setCategoriaPersona(CategoriaPersonaEnum.valueOf(categoriaParametro));
							categoriasCambiarExiste.setTotalIngresoMesada(BigDecimal.valueOf(Double.valueOf(ObjectAfiliado[2].toString())));
							categoriasCambiarExiste.setAfiliadoPrincipal(false);
							categoriasCambiarExiste.setIdBeneficiario(idb);
							categoriasCambiarExiste.setTipoAfiliado(TipoAfiliadoEnum.valueOf(ObjectAfiliado[0].toString()));
							categoriasCambiarExiste.setClasificacionAfiliado(ClasificacionEnum.valueOf(ObjectAfiliado[3].toString()));
									//Afiliado afiliado = new Afiliado();
									//afiliado.setIdAfiliado(ObjectAfiliado[1]);
									logger.info("**__** BENEDFICIARIO ID :"+idb);
							categoriasCambiarExiste.setIdAfiliado(Long.valueOf(ObjectAfiliado[1].toString()));
							entityManager.persist(categoriasCambiarExiste); 
							}catch (NoResultException e) {
								logger.info("**__** CONSULYTA VACIA CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO");
							}
						}catch(Exception e){
							logger.info("**__** Error Interno error:"+e);
						}
						//como estaba
					}
				//} //como estaba  ..se remplazo desde el if
					//if(idsBeneficiarioListaconsultar!=null){
					//	String categoriaParametro = (String) CacheManager
					//			.getParametro(ParametrosSistemaConstants.CATEGORIA_CAMBIO_BENEFICIARIO);
					//	List<Categoria> categoriasCambiar = entityManager
					//			.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_POR_ID_BENEFICIARIO)
					//			.setParameter("listaIdBeneficiario", idsBeneficiarioListaconsultar).getResultList();
					//
					//	if (categoriasCambiar != null && !categoriasCambiar.isEmpty()) {
					//		for (Categoria categorias : categoriasCambiar) {
					//			
					//			entityManager.merge(categorias); 
					//			categorias.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NOVEDAD_ACTUALIZACION_POR_X_EDAD_BENEFICIARIO);
					//			categorias.setFechaCambioCategoria(Calendar.getInstance().getTime());
					//			categorias.setCategoriaPersona(CategoriaPersonaEnum.valueOf(categoriaParametro));
					//		}
					//	}
					//	logger.info("Finaliza operacin cambiarCategoriaBeneficiarioXEdad(List<Long>)");
					//}
					
					logger.info("Finaliza operacin cambiarCategoriaBeneficiarioXEdad(List<Long>) ciclos");
			//}
		logger.info("Finaliza operacin cambiarCategoriaBeneficiarioXEdad(List<Long>) en su totalidad");
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en el mtodo cambiarCategoriaBeneficiarioXEdad(List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
	/**
	 * Servicio que consulta todos los roles afiliados de de una lista de
	 * empleadores.
	 * 
	 * @param idEmpleadores
	 *            id de empleadores a buscar los afiliados.
	 * @param estadoAfiliado
	 *            estado de los afiliados.
	 */
	@SuppressWarnings("unchecked")
	public List<RolAfiliadoModeloDTO> consultarRolesAfiliadosEmpleadorMasivo(List<Long> idEmpleadores,
			EstadoAfiliadoEnum estadoAfiliado) {
		logger.debug("Inicio de mtodo consultarRolesAfiliadosEmpleadorMasivo(List<Long>, EstadoAfiliadoEnum)");
		List<RolAfiliadoModeloDTO> rolesDTO = new ArrayList<>();
		try {
			List<RolAfiliado> roles = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROLAFILIADO_EMPLEADORES_MASIVO)
					.setParameter("idEmpleadores", idEmpleadores).setParameter("estadoAfiliado", estadoAfiliado)
					.getResultList();

			for (RolAfiliado rolAfiliado : roles) {
				RolAfiliadoModeloDTO rolAfiliadoDTO = new RolAfiliadoModeloDTO();
				rolAfiliadoDTO.convertToDTO(rolAfiliado, null);
				rolesDTO.add(rolAfiliadoDTO);
			}
		} catch (Exception e) {
			logger.error(
					"Ocurri un error inesperado en el mtodo consultarRolesAfiliadosEmpleadorMasivo(List<Long>, EstadoAfiliadoEnum)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Fin de mtodo consultarRolesAfiliadosEmpleadorMasivo(List<Long>, EstadoAfiliadoEnum)");
		return rolesDTO;
	}

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarPersonasMoraAportes()
     */
    @SuppressWarnings("unchecked")

    public List<PersonaRetiroNovedadAutomaticaDTO> consultarPersonasMoraAportes() {
        logger.debug("Inicia consultarPersonasMoraAportes()");
        List<PersonaRetiroNovedadAutomaticaDTO> personasRetiroNovedadAutomatica = new ArrayList<>();
        // Se consultan los pensionados en mora
        PersonaRetiroNovedadAutomaticaDTO personasPensionado = new PersonaRetiroNovedadAutomaticaDTO();
        List<Object[]> listRolesPensionados = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONAS_MORA_APORTES)
                .setParameter("tipoSolicitante", TipoSolicitanteMovimientoAporteEnum.PENSIONADO.name())
                .setParameter("tipoAfiliado", TipoAfiliadoEnum.PENSIONADO.name()).getResultList();
        if (listRolesPensionados != null && !listRolesPensionados.isEmpty()) {
            RolAfiliadoModeloDTO rolAfiliadoModeloDTO;
            List<RolAfiliadoModeloDTO> listRoles = new ArrayList<>();
            List<Long> idPersonas = new ArrayList<>();
            for (Object[] entidad : listRolesPensionados) {
                rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO((RolAfiliado) entidad[0], (Afiliado) entidad[1]);
                listRoles.add(rolAfiliadoModeloDTO);
                idPersonas.add(rolAfiliadoModeloDTO.getAfiliado().getIdPersona());
            }
            personasPensionado.setListRolAfiliado(listRoles);
            personasPensionado.setIdPersonaAfiliado(idPersonas);
            personasPensionado.setTipoAfiliadoEnum(TipoAfiliadoEnum.PENSIONADO);
            personasRetiroNovedadAutomatica.add(personasPensionado);
        }
        // Se consultan los independientes en mora
        PersonaRetiroNovedadAutomaticaDTO personasIndependiente = new PersonaRetiroNovedadAutomaticaDTO();
        List<Object[]> listRolesIndependiente = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONAS_MORA_APORTES)
                .setParameter("tipoSolicitante", TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.name())
                .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name()).getResultList();
        if (listRolesIndependiente != null && !listRolesIndependiente.isEmpty()) {
            RolAfiliadoModeloDTO rolAfiliadoModeloDTO;
            List<RolAfiliadoModeloDTO> listRoles = new ArrayList<>();
            List<Long> idPersonas = new ArrayList<>();
            for (Object[] entidad : listRolesIndependiente) {
                rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO((RolAfiliado) entidad[0], (Afiliado) entidad[1]);
                listRoles.add(rolAfiliadoModeloDTO);
                idPersonas.add(rolAfiliadoModeloDTO.getAfiliado().getIdPersona());
            }
            personasIndependiente.setListRolAfiliado(listRoles);
            personasIndependiente.setIdPersonaAfiliado(idPersonas);
            personasIndependiente.setTipoAfiliadoEnum(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
            personasRetiroNovedadAutomatica.add(personasIndependiente);
        }
        logger.debug("Finaliza consultarPersonasMoraAportes()");
        return personasRetiroNovedadAutomatica;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#inactivarAfiliadosMasivo(java.util.List)
     */
    @Override
    public void inactivarAfiliadosMasivo(List<RolAfiliadoModeloDTO> rolesAfiliado) {
        logger.debug("Inicia inactivarAfiliadosMasivo(List<RolAfiliadoModeloDTO>)");
        for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : rolesAfiliado) {
            // Se consulta el rolAfiliado para actualizarlo
            RolAfiliado rolAfiliado = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_ID, RolAfiliado.class)
                    .setParameter("idRolAfiliado", rolAfiliadoModeloDTO.getIdRolAfiliado()).getSingleResult();
            // Entidad manejada
            rolAfiliado.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
            rolAfiliado.setMotivoDesafiliacion(rolAfiliadoModeloDTO.getMotivoDesafiliacion());
            if (rolAfiliadoModeloDTO.getFechaRetiro() != null) {
                rolAfiliado.setFechaRetiro(new Date(rolAfiliadoModeloDTO.getFechaRetiro()));
            }
            rolAfiliado.setCanalReingreso(null);
            rolAfiliado.setReferenciaAporteReingreso(null);
            rolAfiliado.setReferenciaSolicitudReingreso(null);
        }
        logger.debug("Finaliza inactivarAfiliadosMasivo(List<RolAfiliadoModeloDTO>)");
    }

	@Override
	public void desasociarBeneficiario(Long idAfiliado, Long idBeneficiario) {
		logger.debug("Inicia operacin desasociarBeneficiario(Long idAfiliado, Long idBeneficiarios)");
		if (idAfiliado != null && idBeneficiario != null) {
			try {
				Beneficiario beneficiario = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ID_BENEFICIARIO_ID_AFILIADO,
								Beneficiario.class)
						.setParameter("idBeneficiario", idBeneficiario).setParameter("idAfiliado", idAfiliado)
						.getSingleResult();
				//entityManager.remove(beneficiario);
			} catch (NoResultException e) {
				logger.debug(
						"Finaliza desasociarBeneficiario(Long idAfiliado, Long idBeneficiarios):recursos no encontrados");
				throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
			} catch (Exception e) {
				logger.debug(
						"Finaliza desasociarBeneficiario(Long idAfiliado, Long idBeneficiarios):error Tcnico inesperado");
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
		} else {
			logger.debug("Finaliza desasociarBeneficiario(Long idAfiliado, Long idBeneficiarios): Faltan parametros.");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	@Override
	public void actualizarExpulsionSubsanada(ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO) {
		logger.debug("Inicia operacin actualizarExpulsionSubsanada(ExpulsionSubsanadaModeloDTO)");
		try {
			/* Se convierte a la entidad para persistir */
			ExpulsionSubsanada expulsion = expulsionSubsanadaModeloDTO.convertToEntity();
			if (expulsionSubsanadaModeloDTO.getIdExpulsionSubsanada() != null) {
				entityManager.merge(expulsion);
			} else {
				entityManager.persist(expulsion);
			}
			logger.debug("Finaliza operacin actualizarExpulsionSubsanada(ExpulsionSubsanadaModeloDTO)");
		} catch (Exception e) {
			logger.error(
					"Ocurri un error inesperado en el mtodo actualizarExpulsionSubsanada(ExpulsionSubsanadaModeloDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#activarAfiliado(com.
	 * asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String,
	 * com.asopagos.enumeraciones.personas.TipoAfiliadoEnum,
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@Override
	public void activarAfiliado(ActivacionAfiliadoDTO datosActivacion) {
	    ActivarAfiliadoRutine a = new ActivarAfiliadoRutine();
	    a.activarAfiliado(datosActivacion, entityManager);
	    
	}

    /**
     * Deshace la gestin (Si aplica) de la bandeja de 0 trabajadores del empleador
     * @param rolAfiliado
     */
    private void deshacerGestionCeroTrabajadores(Long idEmpleador) {
       /* logger.debug("Inicia deshacerGestionCeroTrabajadores (");
        if (idEmpleador != null) {
            Empleador emp = entityManager.find(Empleador.class, idEmpleador);
            if (emp.getFechaGestionDesafiliacion() != null) {
                emp.setFechaGestionDesafiliacion(null);
            }
        }        
        */
        
        DeshacerGestionCeroTrabajadoresRutine d = new DeshacerGestionCeroTrabajadoresRutine();
        d.deshacerGestionCeroTrabajadores(idEmpleador, entityManager);
    }

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarBeneficiarioTipoNroIdentificacion(com.asopagos.enumeraciones.
	 * personas. TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	public List<BeneficiarioModeloDTO> consultarBeneficiarioTipoNroIdentificacion(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		try {
			List<Beneficiario> listBeneficiarios = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_TIPO_NRO_DOCUMENTO, Beneficiario.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

			List<BeneficiarioModeloDTO> beneficiarios = new ArrayList<>();
			Long idPersona = null;
			for (Beneficiario beneficiario : listBeneficiarios) {
				idPersona = beneficiario.getPersona().getIdPersona();
				PersonaDetalle personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_ID_PERSONA, PersonaDetalle.class)
						.setParameter("idPersona", idPersona).getSingleResult();
				CondicionInvalidez condiInvalidez = consultarDatosCondicion(idPersona);
				BeneficiarioDetalle beneDetalle = consultarDatosBeneficiarioDetalle(
						personaDetalle.getIdPersonaDetalle());
				BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
				// Consultar informacion certificado escolar vigente
				CertificadoEscolarBeneficiario certificado = consultarCertificadoEscolarVigentePorIdPersona(idPersona);
				beneficiarioModeloDTO.convertToDTO(beneficiario, personaDetalle, condiInvalidez, beneDetalle, certificado);
				beneficiarios.add(beneficiarioModeloDTO);
			}
			return beneficiarios;
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiario(com
	 * .asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Long, java.util.List,
	 * com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficiarioModeloDTO> consultarBeneficiarioByTipo(UriInfo uriInfo, HttpServletResponse response, TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, Long fechaFinCertificado, List<ClasificacionEnum> clasificacion,
			EstadoAfiliadoEnum estadoRespectoAfiliado, Boolean tipoHijo, String textoFiltro) {
		try {
			List<BeneficiarioModeloDTO> listRespuesta = new ArrayList<>();
			// Se verifica si se consultan los beneficiarios tipo hijo,se
			// adicionan estas clasificaciones en caso que no se haya enviado
			// filtro de clasificacion
			if (tipoHijo != null && tipoHijo && (clasificacion == null || clasificacion.isEmpty())) {
				clasificacion = Arrays.asList(ClasificacionEnum.HIJO_BIOLOGICO, ClasificacionEnum.HIJO_ADOPTIVO,
						ClasificacionEnum.HIJASTRO, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES,
						ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
			}
			// Se consultan los beneficiarios que cumplan con los criterios
			List<Object[]> listBeneficiarios = crearQueryPersonaBeneficiario(uriInfo, response, tipoIdentificacion,
					numeroIdentificacion, primerNombre, segundoNombre, primerApellido, segundoApellido,
					fechaFinCertificado, clasificacion, estadoRespectoAfiliado, textoFiltro).getResultList();
			if (listBeneficiarios != null && !listBeneficiarios.isEmpty()) {
				for (Object[] object : listBeneficiarios) {
				    Beneficiario beneficiario = (Beneficiario) object[0];
				    PersonaDetalle personaDetalle = (PersonaDetalle) object[1];
				    BeneficiarioDetalle beneDetalle = (BeneficiarioDetalle) object[2];
				    CondicionInvalidez condiInvalidez = (CondicionInvalidez) object[3];
				    CertificadoEscolarBeneficiario certificado = (CertificadoEscolarBeneficiario) object[4];
					BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
					beneficiarioModeloDTO.convertToDTO(beneficiario, personaDetalle, condiInvalidez, beneDetalle, certificado);
					listRespuesta.add(beneficiarioModeloDTO);
				}
			}
			return listRespuesta;
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Crea el query de consulta a partir de los datos enviados por pantalla
	 * 
	 * @param tipoIdentificacion
	 *            Tipo idnetifiacion
	 * @param numeroIdentificacion
	 *            Numero identificacion
	 * @param primerNombre
	 *            Primer nombre persona
	 * @param segundoNombre
	 *            Segundo nombre persona
	 * @param primerApellido
	 *            Primer apellido persona
	 * @param segundoApellido
	 *            segundo apellido persona
	 * @param fechaFinCertificado
	 *            Fecha fin certificado escolar
	 * @param clasificacion
	 *            Lista clasificacion
	 * @param estadoRespectoAfiliado
	 *            Estado respecto al afiliado
	 * @return Query con parametros listo para la ejecucion
	 */
	private Query crearQueryPersonaBeneficiario(UriInfo uriInfo, HttpServletResponse response, TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, Long fechaFinCertificado, List<ClasificacionEnum> clasificacion,
			EstadoAfiliadoEnum estadoRespectoAfiliado, String textoFiltro) {

		// Se crea la consulta apartir de los filtros enviados
		StringBuilder query = new StringBuilder();
		query.append(QueryConstants.SQL_QUERY_PERSONA_BENEFICIARIO);
		StringBuilder queryCondition = new StringBuilder();
		if (tipoIdentificacion != null) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_TIPO_IDENTIFICACION);
		}
		if (numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_NRO_IDENTIFICACION);
		}
		if (primerNombre != null && !primerNombre.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_PRIMER_NOMBRE);
		}
		if (segundoNombre != null && !segundoNombre.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_SEGUNDO_NOMBRE);
		}
		if (primerApellido != null && !primerApellido.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_PRIMER_APELLIDO);
		}
		if (segundoApellido != null && !segundoApellido.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_SEGUNDO_APELLIDO);
		}
		if (clasificacion != null && !clasificacion.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_TIPO_BENEFICIARIO);
		}
		if (estadoRespectoAfiliado != null) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_CONDICION_ESTADO_RESP_AFILIADO);
		}
		if (textoFiltro != null) {
		    verifyAddWhereOrAnd(queryCondition);
		    queryCondition.append(QueryConstants.SQL_CONDICION_TEXTO_TABLA);
		    textoFiltro = "%" + textoFiltro + "%";
		}
		query.append(queryCondition.toString());
		
		Map<String, String> hints = new HashMap<>();
		hints.put("tipoIdentificacion", "info.perTipoIdentificacion");
		hints.put("numeroIdentificacion", "info.perNumeroIdentificacion");
		hints.put("primerNombre", "info.perPrimerNombre");
		hints.put("tipoBeneficiario", "info.benTipoBeneficiario");
		hints.put("estadoBeneficiarioAfiliado", "info.benEstadoBeneficiarioAfiliado");
		hints.put("fechaVencimientoCertificadoEscolar", "info.cebFechaVencimiento");
		
		QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
		queryBuilder.setHints(hints);
		verifyAddParam(queryBuilder, QueriesConstants.TIPO_IDENTIFICACION, tipoIdentificacion);
		verifyAddParam(queryBuilder, QueriesConstants.NRO_IDENTIFICACION, numeroIdentificacion);
		verifyAddParam(queryBuilder, QueriesConstants.PRIMER_NOMBRE, primerNombre);
		verifyAddParam(queryBuilder, QueriesConstants.PRIMER_APELLIDO, primerApellido);
		verifyAddParam(queryBuilder, QueriesConstants.SEGUNDO_NOMBRE, segundoNombre);
		verifyAddParam(queryBuilder, QueriesConstants.SEGUNDO_APELLIDO, segundoApellido);
		verifyAddParam(queryBuilder, QueriesConstants.LISTA_CLASIFICACION, convertListEnumToString(clasificacion));
		verifyAddParam(queryBuilder, QueriesConstants.ESTADO_BENEFICIARIO, estadoRespectoAfiliado);
		verifyAddParam(queryBuilder, QueriesConstants.FILTRO_TEXTO, textoFiltro);
		Query querys = queryBuilder.createNativeQuery(query.toString(), "consultar.beneficiario.by.tipo");
		return querys;
	}

	/**
	 * Verifica si hay condicion para agregar AND y en caso contrario agregar
	 * WHERE
	 * 
	 * @param queryCondition
	 *            Condicion de consulta
	 */
	private void verifyAddWhereOrAnd(StringBuilder queryCondition) {
		if (queryCondition.length() > 0) {
			queryCondition.append(QueriesConstants.AND_CLAUSE);
		} else {
			queryCondition.append(QueriesConstants.WHERE_CLAUSE);
		}
	}

	/**
	 * Verifica si el parametro se debe agregar y lo agrega al query
	 * 
	 * @param query
	 *            Consulta construida
	 * @param campo
	 *            Campo parametro del query
	 * @param valorCampo
	 *            Valor del campo parametro del query
	 */
	private void verifyAddParam(QueryBuilder query, String campo, Object valorCampo) {
		if (valorCampo != null) {
			if (valorCampo instanceof String && ((String) valorCampo).isEmpty()) {
				return;
			}
			if (valorCampo instanceof List<?> && ((List<?>) valorCampo).isEmpty()) {
				return;
			}
			if (valorCampo instanceof Enum) {
				query.addParam(campo, ((Enum<?>) valorCampo).name());
				return;
			}
			query.addParam(campo, valorCampo);
		}
	}

    /**
     * Convierte una lista de enumerados a una lista estring
     * @param listEnum
     *        Lista de enumerados
     * @return Lista de valores string de los enumerados
     */
    private List<String> convertListEnumToString(List<?> listEnum) {
        if (listEnum == null || listEnum.isEmpty()) {
            return null;
        }
        List<String> listResult = new ArrayList<>();
        for (Object value : listEnum) {
            if (value instanceof Enum) {
                listResult.add(((Enum<?>) value).name());
            }
        }
        return listResult;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.afiliados.service.AfiliadosService#consultarAfiliadoPorId(
	 * java.lang.Long)
	 */
	@Override
	public AfiliadoModeloDTO consultarAfiliadoPorId(Long idAfiliado) {
		try {
			Afiliado afiliado = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_ID_AFILIADO, Afiliado.class)
					.setParameter("idAfiliado", idAfiliado).getSingleResult();
			AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
			afiliadoModeloDTO.convertToDTO(afiliado, null);
			return afiliadoModeloDTO;
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarTipoInfraestructura(com.asopagos.dto.modelo.TipoInfraestructuraModeloDTO)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO registrarTipoInfraestructura(
			TipoInfraestructuraModeloDTO tipoInfraestructura) {
		String firmaMetodo = "AfiliadosBusiness.registrarTipoInfraestructura(TipoInfraestructuraModeloDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();

		try {
			// se intenta la creacin del tipo de infraestructura con base en la
			// informacin recibida
			TipoInfraestructura entityTipoInfraestructura = tipoInfraestructura.convertToEntity();

			entityManager.persist(entityTipoInfraestructura);

			// se aade mensaje de registro exitoso
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.REGISTRO_EXITOSO.getReadableMessage(
					ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA, entityTipoInfraestructura.getCodigo()));

		} catch (EntityExistsException e) {
			// no se crea el nuevo tipo, ya existe
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_REGISTRO_EXISTENTE
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la persistencia, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_REGISTRO_GENERAL
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarTiposInfraestructura()
	 */
	@Override
	public List<TipoInfraestructuraModeloDTO> consultarTiposInfraestructura(Boolean estado) {
		String firmaMetodo = "AfiliadosBusiness.consultarTiposInfraestructura(Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<TipoInfraestructuraModeloDTO> respuesta = consultarTiposInfraestructuraDTO(null, null, estado);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargagado de consultar tipos de infraestructuras, retornando
	 * listado de sus DTO. Puede recibir como parmetro un identificador interno
	 * de registro o un cdigo SSF
	 * 
	 * @param idInterno
	 *            ID de registro de infraestructura (parmetro nulleable)
	 * @param idSSF
	 *            Cdigo de tipo de infraestructura asignado por la SSF
	 *            (parmetro nulleable)
	 * @param buscarActivos
	 *            Indica que se buscan registros activos o inactivos nicamente
	 *            o de todos los estados cuando se enva nulo)
	 * @return <b>List<TipoInfraestructuraModeloDTO></b> Listado de resultados
	 *         obtenido (DTOs)
	 */
	private List<TipoInfraestructuraModeloDTO> consultarTiposInfraestructuraDTO(Long idInterno, String idSSF,
			Boolean buscarActivos) {
		String firmaMetodo = "AfiliadosBusiness.consultarTiposInfraestructuraDTO(Long, String, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<TipoInfraestructuraModeloDTO> respuesta = new ArrayList<>();

		List<TipoInfraestructura> resultadoEntity = consultarTiposInfraestructura(idInterno, idSSF, buscarActivos);

		if (resultadoEntity != null) {
			for (TipoInfraestructura tipoInfraestructura : resultadoEntity) {
				TipoInfraestructuraModeloDTO dto = new TipoInfraestructuraModeloDTO();
				dto.convertToDTO(tipoInfraestructura);

				respuesta.add(dto);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargagado de consultar tipos de infraestructuras. Puede recibir
	 * como parmetro un identificador interno de registro o un cdigo SSF
	 * 
	 * @param idInterno
	 *            ID de registro de infraestructura (parmetro nulleable)
	 * @param idSSF
	 *            Cdigo de tipo de infraestructura asignado por la SSF
	 *            (parmetro nulleable)
	 * @param buscarActivos
	 *            Indica que se buscan registros activos o inactivos nicamente
	 *            o de todos los estados cuando se enva nulo)
	 * @return <b>List<TipoInfraestructura></b> Listado de resultados obtenido
	 *         (Entities)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<TipoInfraestructura> consultarTiposInfraestructura(Long idInterno, String idSSF,
			Boolean buscarActivos) {
		String firmaMetodo = "AfiliadosBusiness.consultarTiposInfraestructura(Long, String, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<TipoInfraestructura> respuesta = new ArrayList<>();

		try {
			respuesta = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_INFRAESTRUCTURA)
					.setParameter(ConstantesProcesoSitiosPago.ID_INTERNO, idInterno)
					.setParameter(ConstantesProcesoSitiosPago.CODIGO_INF_SSF, idSSF)
					.setParameter(ConstantesProcesoSitiosPago.ESTADO, buscarActivos).getResultList();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retorna listado vaco
			logger.debug(firmaMetodo + MensajesSalidaEnum.CONSULTA_SIN_RESULTADOS
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la consulta, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarTipoInfraestructuraPorID(java.lang.Long)
	 */
	@Override
	public TipoInfraestructuraModeloDTO consultarTipoInfraestructuraPorID(Long idTipoInfraestructura) {
		String firmaMetodo = "AfiliadosBusiness.consultarTipoInfraestructuraPorID(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		TipoInfraestructuraModeloDTO respuesta = null;

		// se hace la consulta en la BD
		List<TipoInfraestructuraModeloDTO> resultadoConsulta = consultarTiposInfraestructuraDTO(idTipoInfraestructura,
				null, true);

		// se comprueba s se ha recibido un resultado
		if (!resultadoConsulta.isEmpty()) {
			respuesta = resultadoConsulta.get(0);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarTipoInfraestructuraPorIdSSF(java.lang.String)
	 */
	@Override
	public TipoInfraestructuraModeloDTO consultarTipoInfraestructuraPorIdSSF(String idSsfTipoInfraestructura) {
		String firmaMetodo = "AfiliadosBusiness.consultarTipoInfraestructuraPorIdSSF(String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		TipoInfraestructuraModeloDTO respuesta = null;

		// se hace la consulta en la BD
		List<TipoInfraestructuraModeloDTO> resultadoConsulta = consultarTiposInfraestructuraDTO(null,
				idSsfTipoInfraestructura, true);

		// se comprueba s se ha recibido ms de un resultado
		if (resultadoConsulta.size() == 1) {
			respuesta = resultadoConsulta.get(0);
		} else if (!resultadoConsulta.isEmpty()) {
			// en el caso de que se presente ms de un resultado para el mismo
			// ID, se lanza excepcin
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA_RESPUESTA_MULTIPLE
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA);

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#inactivarActivarTipoInfraestructura(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO inactivarActivarTipoInfraestructura(Long idTipoInfraestructura,
			Boolean activar) {
		String firmaMetodo = "AfiliadosBusiness.inactivarActivarTipoInfraestructura(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();

		List<TipoInfraestructura> tiposInfraestructura = consultarTiposInfraestructura(idTipoInfraestructura, null,
				null);
		TipoInfraestructura tipoParaActualizar = null;

		if (!tiposInfraestructura.isEmpty()) {
			tipoParaActualizar = tiposInfraestructura.get(0);
		}

		try {
			tipoParaActualizar = entityManager.merge(tipoParaActualizar);
			tipoParaActualizar.setActivo(activar);

			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ACTUALIZACION_EXITOSA
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la actualizacin
			String mensaje = MensajesSalidaEnum.ERROR_ACTUALIZACION_COMPLETO
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_ACTUALIZACION_SIMPLE
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_INFRAESTRUCTURA));
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarInfraestructura(com.asopagos.dto.modelo.InfraestructuraModeloDTO)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO registrarInfraestructura(InfraestructuraModeloDTO infraestructura) {
		String firmaMetodo = "AfiliadosBusiness.registrarInfraestructura(InfraestructuraModeloDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();

		try {
			// se consulta el cdigo de la CCF
			String codigoCCF = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);

			// se consulta el tipo de la infraestructura para obtener su cdigo
			// SSF
			TipoInfraestructuraModeloDTO tipoInfraestructura = consultarTipoInfraestructuraPorID(
					infraestructura.getTipoInfraestructura());

			// se valida la medida de capacidad del tipo, para constatar que se
			// tenga una capacidad esperada en el caso de requerirla
			if (!MedidaCapacidadInfraestructuraEnum.NO_APLICA.equals(tipoInfraestructura.getMedidaCapacidad())
					&& (infraestructura.getCapacidadEstimada() == null
							|| infraestructura.getCapacidadEstimada().compareTo(BigDecimal.ZERO) == 0)) {
				respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_REGISTRO_GENERAL.getReadableMessage(
						ConstantesProcesoSitiosPago.INFRAESTRUCTURA,
						ConstantesProcesoSitiosPago.CAPACIDAD_ESTIMADA_INFRAESTRUCTURA));
			} else {
				// se consulta el consecutivo de infraestructura, se toma el
				// mayor consecutivo registrado agregando 1
				Short consecutivoNumerico = consultarSiguienteConsecutivoInfraestructura();

				String consecutivo = formatoConsecutivoCodigoInfraestructura(consecutivoNumerico);

				// se solicita la creacin del cdigo de la infraestructura
				infraestructura.construirCodigosInfraestructura(codigoCCF, tipoInfraestructura.getCodigo(),
						consecutivo);

				// se aade el consecutivo numrico al DTO
				infraestructura.setConsecutivoInfraestructura(consecutivoNumerico);

				// se intenta la creacin del tipo de infraestructura con base
				// en la informacin recibida
				Infraestructura entityInfraestructura = infraestructura.convertToEntity();

				entityManager.persist(entityInfraestructura);

				// se aade mensaje de registro exitoso
				respuesta.setMensajeRespuesta(MensajesSalidaEnum.REGISTRO_EXITOSO.getReadableMessage(
						ConstantesProcesoSitiosPago.INFRAESTRUCTURA, entityInfraestructura.getCodigoParaCCF()));
			}
		} catch (EntityExistsException e) {
			// no se crea el nuevo tipo, ya existe
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_REGISTRO_EXISTENTE
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la persistencia, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_REGISTRO_GENERAL
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargado de la consulta del siguiente valor de consecutivo para
	 * la infraestructura
	 * 
	 * @return <b>Short</b> Valor consecutivo para asignar al cdigo de la
	 *         infraestructura
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private Short consultarSiguienteConsecutivoInfraestructura() {
		String firmaMetodo = "AfiliadosBusiness.consultarSiguienteConsecutivoInfraestructura()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Short respuesta = null;

		try {
			Integer respuestaInt = (Integer) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SIGUIENTE_CONSECUTIVO).getSingleResult();

			respuesta = respuestaInt.shortValue();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retorna 1 (sera la primera
			// infraestructura)
			respuesta = 1;
		} catch (Exception e) {
			// se presenta un error inesperado durante la consulta, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo que toma el valor nmerico del consecutivo de infraestructura y lo
	 * convierte a su forma textual para el cdigo ("NNN")
	 * 
	 * @param consecutivoNumerico
	 *            Valor nmerico del consecutivo de infraestructura
	 * @return <b>String</b> Valor en texto del consecutivo de infraestructura
	 */
	private String formatoConsecutivoCodigoInfraestructura(Short consecutivoNumerico) {
		String firmaMetodo = "AfiliadosBusiness.formatoConsecutivoCodigoInfraestructura(Short)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		StringBuilder respuesta = new StringBuilder();

		if (consecutivoNumerico < 10) {
			respuesta.append("00");
		} else if (consecutivoNumerico < 100) {
			respuesta.append("0");
		}

		respuesta.append(consecutivoNumerico);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta.toString();
	}

	/**
	 * Mtodo encargagado de consultar infraestructuras, retornando listado de
	 * sus DTO. Puede recibir como parmetro un identificador interno de
	 * registro
	 * 
	 * @param criterios
	 *            DTO empleado para el paso de los criterios de bsqueda
	 * @return <b>List<InfraestructuraModeloDTO></b> Listado de resultados
	 *         obtenido (DTOs)
	 */
	private List<InfraestructuraModeloDTO> consultarInfraestructuraDTO(
			CriteriosConsultaModeloInfraestructuraDTO criterios) {
		String firmaMetodo = "AfiliadosBusiness.consultarTiposInfraestructuraDTO(CriteriosConsultaModeloInfraestructuraDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<InfraestructuraModeloDTO> respuesta = new ArrayList<>();

		List<Infraestructura> resultadoEntity = consultarInfraestructura(criterios);

		if (resultadoEntity != null) {
			for (Infraestructura infraestructura : resultadoEntity) {
				InfraestructuraModeloDTO dto = new InfraestructuraModeloDTO();
				dto.convertToDTO(infraestructura);

				respuesta.add(dto);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargagado de consultar infraestructuras. Puede recibir como
	 * parmetro un identificador interno de registro
	 * 
	 * @param criterios
	 *            DTO de infraestructura empleado para el paso de los criterios
	 *            de bsqueda
	 * @return <b>List<Infraestructura></b> Listado de resultados obtenido
	 *         (Entities)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<Infraestructura> consultarInfraestructura(CriteriosConsultaModeloInfraestructuraDTO criterios) {
		String firmaMetodo = "AfiliadosBusiness.consultarInfraestructura(CriteriosConsultaModeloInfraestructuraDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<Infraestructura> respuesta = new ArrayList<>();

		String nombre = criterios.getNombreInfraestructura();
		if (nombre != null) {
			nombre = "%" + nombre + "%";
		}

		String zona = criterios.getZona();
		if (zona != null) {
			zona = "%" + zona + "%";
		}

		try {
			respuesta = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFRAESTRUCTURA)
					.setParameter(ConstantesProcesoSitiosPago.ID_INF, criterios.getIdInfraestructura())
					.setParameter(ConstantesProcesoSitiosPago.NOMBRE_INF, nombre)
					.setParameter(ConstantesProcesoSitiosPago.TIPO_INF, criterios.getTipoInfraestructura())
					.setParameter(ConstantesProcesoSitiosPago.ZONA_INF, zona)
					.setParameter(ConstantesProcesoSitiosPago.MUNICIPIO_INF, criterios.getMunicipioInfraestructura())
					.setParameter(ConstantesProcesoSitiosPago.TENENCIA_INF, criterios.getTenencia())
					.setParameter(ConstantesProcesoSitiosPago.ESTADO, criterios.getEstadoActivo()).getResultList();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retorna listado vaco
			logger.debug(firmaMetodo + MensajesSalidaEnum.CONSULTA_SIN_RESULTADOS
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la consulta, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarInfraestructuras()
	 */
	@Override
	public List<PresentacionResultadoModeloInfraestructuraDTO> consultarInfraestructuras(
			CriteriosConsultaModeloInfraestructuraDTO criterios, UriInfo uri, HttpServletResponse response) {
		String firmaMetodo = "AfiliadosBusiness.consultarInfraestructuras(CriteriosConsultaModeloInfraestructuraDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		// s no se reciben criterios, se inicializa un DTO en blanco para
		// evitar punteros nulos
		CriteriosConsultaModeloInfraestructuraDTO temporal = criterios;
		if (temporal == null) {
			temporal = new CriteriosConsultaModeloInfraestructuraDTO();
		}

		List<PresentacionResultadoModeloInfraestructuraDTO> infraestructura = consultarInfraestructuraPantallaDTO(temporal,
				uri, response);
		
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return infraestructura;
	}
	/**
	 * Mtodo encargado de realizar la consulta completa de infraestructuras
	 * para su presentacin en pantalla
	 * 
	 * @param criterios
	 *            DTO de infraestructura empleado para el paso de los criterios
	 *            de bsqueda
	 * @param uri
	 *            Informacin de la peticin
	 * @param response
	 *            Respuesta HTML
	 * @return <b>List<PresentacionResultadoModeloInfraestructuraDTO></b>
	 *         Listado de resultados en forma de DTO para presentacin en
	 *         pantalla
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<PresentacionResultadoModeloInfraestructuraDTO> consultarInfraestructuraPantallaDTO(
			CriteriosConsultaModeloInfraestructuraDTO criterios, UriInfo uri, HttpServletResponse response) {
		String firmaMetodo = "AfiliadosBusiness.consultarInfraestructuraPantallaDTO(CriteriosConsultaModeloInfraestructuraDTO, UriInfo, "
				+ "HttpServletResponse)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<PresentacionResultadoModeloInfraestructuraDTO> respuesta = null;

		String nombre = criterios.getNombreInfraestructura();
		if (nombre != null) {
			nombre = "%" + nombre + "%";
		}

		String zona = criterios.getZona();
		if (zona != null) {
			zona = "%" + zona + "%";
		}

		String codigo = criterios.getCodigoInfraestructura();
		if (codigo != null) {
			codigo = "%" + codigo + "%";
		}

		try {
			QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);

			queryBuilder.addParam(ConstantesProcesoSitiosPago.ID_INF, criterios.getIdInfraestructura());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.CODIGO_INF_SSF, codigo);
			queryBuilder.addParam(ConstantesProcesoSitiosPago.ESTADO, criterios.getEstadoActivo());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.NOMBRE_INF, nombre);
			queryBuilder.addParam(ConstantesProcesoSitiosPago.TIPO_INF, criterios.getTipoInfraestructura());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.TENENCIA_INF, criterios.getTenencia());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.DEPARTAMENTO_INF,
					criterios.getDepartamentoInfraestructura());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.MUNICIPIO_INF, criterios.getMunicipioInfraestructura());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.ZONA_INF, zona);
			queryBuilder.addParam(ConstantesProcesoSitiosPago.AREA_GEOGRAFICA, criterios.getAreaGeografica());

			queryBuilder.addOrderByDefaultParam("-" + ConstantesProcesoSitiosPago.ID_INF);

			Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_INFRAESTRUCTURA_PANTALLA, null);

			respuesta = (List<PresentacionResultadoModeloInfraestructuraDTO>) query.getResultList();

			
		} catch (NoResultException e) {
			// no se encuentran resultados, se retorna listado vaco
			logger.debug(firmaMetodo + MensajesSalidaEnum.CONSULTA_SIN_RESULTADOS
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la consulta, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}
	
	/**
	 * Consulta que el idInfraestructura tenga un sitio pago y valida si es un sitio pago principal
	 * @param idInfraestructura
	 * @return a
	 */
	@SuppressWarnings("unchecked")
    public Boolean[] consultarSitioPagoPrincipal (Long idInfraestructura) {
	    
	    Boolean[] respuesta = new Boolean[2];
	    //Existen sitios pago para la infraestructura
	    respuesta[0] = Boolean.FALSE;
	    //Tiene un sitio pago principal
        respuesta[1] = Boolean.FALSE;
	    
	    try {
	        
            List<Object[]> result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTENCIA_SITIO_PAGO)
                    .setParameter("idInfraestructura", idInfraestructura)
                    .getResultList();
            
            if (result != null && !result.isEmpty()) {
                respuesta[0] = Boolean.TRUE;
                for (Object[] registro : result) {
                    if (registro[1] == Boolean.TRUE) {
                        respuesta[1] = Boolean.TRUE;
                        break;
                    }
                }
            }
	        
	    } catch (NoResultException nre) {
	        respuesta[0] = Boolean.FALSE;
	        respuesta[1] = Boolean.FALSE;
	        return respuesta;
	    }
	    
//	    QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
//	    queryBuilder.addParam(ConstantesProcesoSitiosPago.ID_INF, idInfraestructura);
//	    queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_EXISTENCIA_SITIO_PAGO, null);

	    return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarInfraestructuraPorID(java.lang.Long)
	 */
	@Override
	public InfraestructuraModeloDTO consultarInfraestructuraPorID(Long idInfraestructura) {
		String firmaMetodo = "AfiliadosBusiness.consultarInfraestructuraPorID(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		InfraestructuraModeloDTO respuesta = null;
		CriteriosConsultaModeloInfraestructuraDTO criterios = new CriteriosConsultaModeloInfraestructuraDTO();
		criterios.setIdInfraestructura(idInfraestructura);
		criterios.setEstadoActivo(true);

		// se hace la consulta en la BD
		List<InfraestructuraModeloDTO> resultadoConsulta = consultarInfraestructuraDTO(criterios);

		// se comprueba s se ha recibido un resultado
		if (!resultadoConsulta.isEmpty()) {
			respuesta = resultadoConsulta.get(0);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#actualizarInfraestructura(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO actualizarInfraestructura(InfraestructuraModeloDTO datosActualizados) {
		String firmaMetodo = "AfiliadosBusiness.inactivarActivarInfraestructura(InfraestructuraModeloDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();
		CriteriosConsultaModeloInfraestructuraDTO criterios = new CriteriosConsultaModeloInfraestructuraDTO();
		criterios.setIdInfraestructura(datosActualizados.getId());
		criterios.setEstadoActivo(null);

		// s bien se recibe un DTO de infraestructura, se le consulta para
		// confirmar su existencia y controlar los campos que cambian
		List<Infraestructura> infraestructuras = consultarInfraestructura(criterios);
		Infraestructura infraestructuraParaActualizar = null;

		if (!infraestructuras.isEmpty()) {
			infraestructuraParaActualizar = infraestructuras.get(0);
		}

		try {
			infraestructuraParaActualizar = entityManager.merge(infraestructuraParaActualizar);
			infraestructuraParaActualizar.setNombre(datosActualizados.getNombre());
			infraestructuraParaActualizar.setTipoTenencia(datosActualizados.getTipoTenencia());
			infraestructuraParaActualizar.setMunicipio(datosActualizados.getMunicipio());
			infraestructuraParaActualizar.setZona(datosActualizados.getZona());
			infraestructuraParaActualizar.setDireccion(datosActualizados.getDireccion());
			infraestructuraParaActualizar.setLatitud(datosActualizados.getLatitud());
			infraestructuraParaActualizar.setLongitud(datosActualizados.getLongitud());
			infraestructuraParaActualizar.setAreaGeografica(datosActualizados.getAreaGeografica());
			infraestructuraParaActualizar.setCapacidadEstimada(datosActualizados.getCapacidadEstimada());
			infraestructuraParaActualizar.setActivo(datosActualizados.getActivo());

			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ACTUALIZACION_EXITOSA
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la actualizacin
			String mensaje = MensajesSalidaEnum.ERROR_ACTUALIZACION_COMPLETO
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_ACTUALIZACION_SIMPLE
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA));
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarSitioPago(com.asopagos.dto.modelo.SitioPagoModeloDTO)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO registrarSitioPago(SitioPagoModeloDTO sitioPago) {
		String firmaMetodo = "AfiliadosBusiness.registrarSitioPago(SitioPagoModeloDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();
		
		List<SitioPago> listSitiosPago = new ArrayList<>();
		CriteriosConsultaModeloInfraestructuraDTO criterios = new CriteriosConsultaModeloInfraestructuraDTO();
		
		criterios.setIdInfraestructura(sitioPago.getInfraestructura());
		
		try {
		    
		    if (sitioPago.getPrincipal() == Boolean.TRUE) {		        
		        listSitiosPago = consultarSitioPago(criterios);         
		        for (SitioPago sitio : listSitiosPago) {       
		            if (sitio.getPrincipal() == Boolean.TRUE) {
		                sitio.setPrincipal(Boolean.FALSE);
		                entityManager.merge(sitio);
		            }
		        }
            }
		    
			// se intenta la creacin del tipo de infraestructura con base en la
			// informacin recibida
			SitioPago entitySitioPago = sitioPago.convertToEntity();

			// se calcula el cdigo del sitio de pago con base en el anterior
			entitySitioPago.setCodigo(calcularSiguienteCodigo());

			entityManager.persist(entitySitioPago);

			// se aade mensaje de registro exitoso
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.REGISTRO_EXITOSO
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO, entitySitioPago.getCodigo()));

		} catch (EntityExistsException e) {
			// no se crea el nuevo tipo, ya existe
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_REGISTRO_EXISTENTE
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO));
		} catch (Exception e) {
			// se presenta un error inesperado durante la persistencia, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_REGISTRO_GENERAL
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargado de la obtencin del codigo que ha de ser asignado a un
	 * nuevo sitio de pago
	 * 
	 * @return <b>String</b> Cdigo calculado para el nuevo sitio de pago
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private String calcularSiguienteCodigo() {
		String firmaMetodo = "AfiliadosBusiness.calcularSiguienteCodigo()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		String respuesta = null;

		try {
			respuesta = (String) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SIGUIENTE_CONSECUTIVO_SITIO_PAGO)
					.getSingleResult();

			// se agregan los ceros restantes en caso de ser necesario
			if (respuesta.length() == 1) {
				respuesta = "00" + respuesta;
			} else if (respuesta.length() == 2) {
				respuesta = "0" + respuesta;
			}
		} catch (NoResultException e) {
			// no se tienen datos, se retorna 001 al ser la primera insercin
			respuesta = "001";
		} catch (Exception e) {
			// se presenta un error inesperado durante la persistencia, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_REGISTRO_GENERAL
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}
	
	/**
	 * Mtodo encargagado de consultar sitios de pago, retornando listado de sus
	 * DTO. Puede recibir como parmetro un identificador interno de registro
	 * 
	 * @param criterios
	 *            Listado de criterios de bsqueda, basados en la estructura del
	 *            entity de sitios de pago
	 * @return <b>List<SitioPagoModeloDTO></b> Listado de resultados obtenido
	 *         (DTOs)
	 */
	private List<SitioPagoModeloDTO> consultarSitioPagoDTO(CriteriosConsultaModeloInfraestructuraDTO criterios) {
		String firmaMetodo = "AfiliadosBusiness.consultarSitioPagoDTO(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<SitioPagoModeloDTO> respuesta = new ArrayList<>();

		CriteriosConsultaModeloInfraestructuraDTO temporal = criterios;
		if (temporal == null) {
			temporal = new CriteriosConsultaModeloInfraestructuraDTO();
		}

		List<SitioPago> resultadoEntity = consultarSitioPago(temporal);

		if (resultadoEntity != null) {
			for (SitioPago infraestructura : resultadoEntity) {
				SitioPagoModeloDTO dto = new SitioPagoModeloDTO();
				dto.convertToDTO(infraestructura);

				respuesta.add(dto);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargagado de consultar sitios de pago. Puede recibir como
	 * parmetro un identificador interno de registro
	 * 
	 * @param criterios
	 *            Listado de criterios de bsqueda, basados en la estructura del
	 *            entity de sitios de pago
	 * @return <b>List<SitioPago></b> Listado de resultados obtenido (Entities)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<SitioPago> consultarSitioPago(CriteriosConsultaModeloInfraestructuraDTO criterios) {
		String firmaMetodo = "AfiliadosBusiness.consultarSitioPago(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<SitioPago> respuesta = new ArrayList<>();

		String codigo = criterios.getCodigoSitioPago();
		if (codigo != null) {
			codigo = "%" + codigo + "%";
		}

		String nombre = criterios.getNombreSitioPago();
		if (nombre != null) {
			nombre = "%" + nombre + "%";
		}

		try {
			respuesta = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SITIO_PAGO)
					.setParameter(ConstantesProcesoSitiosPago.ID_SITIO, criterios.getIdSitioPago())
					.setParameter(ConstantesProcesoSitiosPago.ID_INF, criterios.getIdInfraestructura())
					.setParameter(ConstantesProcesoSitiosPago.CODIGO_SITIO, codigo)
					.setParameter(ConstantesProcesoSitiosPago.NOMBRE_SITIO, nombre)
					.setParameter(ConstantesProcesoSitiosPago.ESTADO, criterios.getEstadoActivo()).getResultList();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retorna listado vaco
			logger.debug(firmaMetodo + MensajesSalidaEnum.CONSULTA_SIN_RESULTADOS
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO));
		} catch (Exception e) {
			// se presenta un error inesperado durante la consulta, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarSitiosPago()
	 */
	@Override
	public List<PresentacionResultadoModeloInfraestructuraDTO> consultarSitiosPago(
			CriteriosConsultaModeloInfraestructuraDTO criterios, UriInfo uri, HttpServletResponse response) {
		String firmaMetodo = "AfiliadosBusiness.consultarSitiosPago(PresentacionResultadoModeloInfraestructuraDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		// se incializa un DTO de criterios en caso de que no se envie ninguno
		// (consulta general de infraestructuras)
		CriteriosConsultaModeloInfraestructuraDTO criterioConsulta = criterios;
		if (criterioConsulta == null) {
			criterioConsulta = new CriteriosConsultaModeloInfraestructuraDTO();
		}

		List<PresentacionResultadoModeloInfraestructuraDTO> respuesta = consultarSitioPagoPantalla(criterioConsulta,
				uri, response);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargado de realizar la consulta completa de sitios de pago para
	 * su presentacin en pantalla
	 * 
	 * @param criterios
	 *            DTO de sitio de pago empleado para el paso de los criterios de
	 *            bsqueda
	 * @param uri
	 *            Informacin de la peticin
	 * @param response
	 *            Respuesta HTML
	 * @return <b>List<PresentacionResultadoModeloInfraestructuraDTO></b>
	 *         Listado de resultados en forma de DTO para presentacin en
	 *         pantalla
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<PresentacionResultadoModeloInfraestructuraDTO> consultarSitioPagoPantalla(
			CriteriosConsultaModeloInfraestructuraDTO criterios, UriInfo uri, HttpServletResponse response) {
		String firmaMetodo = "AfiliadosBusiness.consultarSitioPagoPantalla(CriteriosConsultaModeloInfraestructuraDTO, UriInfo, HttpServletResponse)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<PresentacionResultadoModeloInfraestructuraDTO> respuesta = null;

		String nombre = criterios.getNombreSitioPago();
		if (nombre != null) {
			nombre = "%" + nombre + "%";
		}

		String codigo = criterios.getCodigoSitioPago();
		if (codigo != null) {
			codigo = "%" + codigo + "%";
		}

		try {
			QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
			queryBuilder.addParam(ConstantesProcesoSitiosPago.ID_SITIO, criterios.getIdSitioPago());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.ID_INF, criterios.getIdInfraestructura());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.CODIGO_SITIO, codigo);
			queryBuilder.addParam(ConstantesProcesoSitiosPago.NOMBRE_SITIO, nombre);
			queryBuilder.addParam(ConstantesProcesoSitiosPago.ESTADO, criterios.getEstadoActivo());
			queryBuilder.addParam(ConstantesProcesoSitiosPago.SITIO_PAGO_PRINCIPAL, criterios.getSitioPagoPrincipal());
			queryBuilder.addOrderByDefaultParam("-" + ConstantesProcesoSitiosPago.ID_SITIO);
			Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_SITIO_PAGO_PANTALLA, null);
			respuesta = (List<PresentacionResultadoModeloInfraestructuraDTO>) query.getResultList();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retorna listado vaco
			logger.debug(firmaMetodo + MensajesSalidaEnum.CONSULTA_SIN_RESULTADOS
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA));
                        return null;
		} catch (Exception e) {
			// se presenta un error inesperado durante la consulta, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesProcesoSitiosPago.INFRAESTRUCTURA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarSitioPagoPorID(java.lang.Long)
	 */
	@Override
	public SitioPagoModeloDTO consultarSitioPagoPorID(Long idSitioPago) {
		String firmaMetodo = "AfiliadosBusiness.consultarSitioPagoPorID(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		SitioPagoModeloDTO respuesta = null;
		CriteriosConsultaModeloInfraestructuraDTO criterios = new CriteriosConsultaModeloInfraestructuraDTO();
		criterios.setIdSitioPago(idSitioPago);
		criterios.setEstadoActivo(true);

		// se hace la consulta en la BD
		List<SitioPagoModeloDTO> resultadoConsulta = consultarSitioPagoDTO(criterios);

		// se comprueba s se ha recibido un resultado
		if (!resultadoConsulta.isEmpty()) {
			respuesta = resultadoConsulta.get(0);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarSitioPagoPredeterminado(java.lang.Long)
	 */
	@Override
	public Long consultarIdSitioPagoPredeterminado() {
		String firmaMetodo = "AfiliadosBusiness.consultarIdSitioPagoPredeterminado()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Long respuesta = null;
		Object resIni = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_SITIO_PAGO_PREDETERMINADO).getSingleResult();
		respuesta = Long.parseLong(resIni.toString());
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#actualizarSitioPago(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO actualizarSitioPago(SitioPagoModeloDTO datosActualizados) {
		String firmaMetodo = "AfiliadosBusiness.inactivarActivarSitioPago(SitioPagoModeloDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();
		CriteriosConsultaModeloInfraestructuraDTO criterios = new CriteriosConsultaModeloInfraestructuraDTO();
		criterios.setIdSitioPago(datosActualizados.getId());
		criterios.setEstadoActivo(null);

		// s bien se recibe un DTO de sitio de pago, se le consulta para
		// confirmar su existencia y controlar los campos que cambian
		List<SitioPago> sitiosPago = consultarSitioPago(criterios);
		SitioPago sitioPagoParaActualizar = null;

		if (!sitiosPago.isEmpty()) {
			sitioPagoParaActualizar = sitiosPago.get(0);
			
			//Se revisan los otros sitios pagos relacionados a la infraestructura y
			//se asigna valor falso a sus sitios pago principales
	        if (datosActualizados.getPrincipal() == Boolean.TRUE) {
	            CriteriosConsultaModeloInfraestructuraDTO criteriosInfraestructura = new CriteriosConsultaModeloInfraestructuraDTO();
	            criteriosInfraestructura.setIdInfraestructura(datosActualizados.getInfraestructura());
	            List<SitioPago> sitiosPagoInfraestructura = consultarSitioPago(criteriosInfraestructura);
	            
	            for (SitioPago sitio : sitiosPagoInfraestructura) {
	                if (sitio.getPrincipal() == Boolean.TRUE) {
	                    sitio.setPrincipal(Boolean.FALSE);
	                    entityManager.merge(sitio);
	                }
	            }
	        }
	        
		}

		try {
			sitioPagoParaActualizar = entityManager.merge(sitioPagoParaActualizar);
			sitioPagoParaActualizar.setNombre(datosActualizados.getNombre());
			sitioPagoParaActualizar.setInfraestructura(datosActualizados.getInfraestructura());
			sitioPagoParaActualizar.setActivo(datosActualizados.getActivo());
			sitioPagoParaActualizar.setPrincipal(datosActualizados.getPrincipal());

			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ACTUALIZACION_EXITOSA
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO));
		} catch (Exception e) {
			// se presenta un error inesperado durante la actualizacin
			String mensaje = MensajesSalidaEnum.ERROR_ACTUALIZACION_COMPLETO
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_ACTUALIZACION_SIMPLE
					.getReadableMessage(ConstantesProcesoSitiosPago.SITIO_PAGO));
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#registrarTipoTenencia(com.asopagos.dto.modelo.TipoTenenciaModeloDTO)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO registrarTipoTenencia(TipoTenenciaModeloDTO tipoTenencia) {
		String firmaMetodo = "AfiliadosBusiness.registrarSitioPago(SitioPagoModeloDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();

		try {
			// se intenta la creacin del tipo de infraestructura con base en la
			// informacin recibida
			TipoTenencia entityTipoTenencia = tipoTenencia.convertToEntity();

			entityManager.persist(entityTipoTenencia);

			// se aade mensaje de registro exitoso
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.REGISTRO_EXITOSO.getReadableMessage(
					ConstantesProcesoSitiosPago.TIPO_TENENCIA, entityTipoTenencia.getCodigo().toString()));

		} catch (EntityExistsException e) {
			// no se crea el nuevo tipo, ya existe
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_REGISTRO_EXISTENTE
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_TENENCIA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la persistencia, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_REGISTRO_GENERAL
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_TENENCIA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargagado de consultar Tipos de Tenencia, retornando listado de
	 * sus DTO. Puede recibir como parmetro un identificador interno de
	 * registro
	 * 
	 * @param idInterno
	 *            ID de registro de tipo de tenencia (parmetro nulleable)
	 * @param buscarActivos
	 *            Indica que se buscan registros activos o inactivos nicamente
	 *            o de todos los estados cuando se enva nulo)
	 * @return <b>List<TipoTenenciaModeloDTO></b> Listado de resultados obtenido
	 *         (DTOs)
	 */
	private List<TipoTenenciaModeloDTO> consultarTipoTenenciaDTO(Long idInterno, Boolean buscarActivos) {
		String firmaMetodo = "AfiliadosBusiness.consultarTipoTenenciaDTO(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<TipoTenenciaModeloDTO> respuesta = new ArrayList<>();

		List<TipoTenencia> resultadoEntity = consultarTipoTenencia(idInterno, buscarActivos);

		if (resultadoEntity != null) {
			for (TipoTenencia infraestructura : resultadoEntity) {
				TipoTenenciaModeloDTO dto = new TipoTenenciaModeloDTO();
				dto.convertToDTO(infraestructura);

				respuesta.add(dto);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Mtodo encargagado de consultar Tipos de Tenencia. Puede recibir como
	 * parmetro un identificador interno de registro
	 * 
	 * @param idInterno
	 *            ID de registro de tipo de tenencia (parmetro nulleable)
	 * @param buscarActivos
	 *            Indica que se buscan registros activos o inactivos nicamente
	 *            o de todos los estados cuando se enva nulo)
	 * @return <b>List<TipoTenencia></b> Listado de resultados obtenido
	 *         (Entities)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<TipoTenencia> consultarTipoTenencia(Long idInterno, Boolean buscarActivos) {
		String firmaMetodo = "AfiliadosBusiness.consultarTipoTenencia(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<TipoTenencia> respuesta = new ArrayList<>();

		try {
			respuesta = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_TENENCIA)
					.setParameter(ConstantesProcesoSitiosPago.ID_INTERNO, idInterno)
					.setParameter(ConstantesProcesoSitiosPago.ESTADO, buscarActivos).getResultList();
		} catch (NoResultException e) {
			// no se encuentran resultados, se retorna listado vaco
			logger.debug(firmaMetodo + MensajesSalidaEnum.CONSULTA_SIN_RESULTADOS
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_TENENCIA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la consulta, se lanza
			// excepcin tcnica
			String mensaje = " - " + MensajesSalidaEnum.ERROR_CONSULTA
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_TENENCIA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			throw new TechnicalException(mensaje);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarTiposTenencia()
	 */
	@Override
	public List<TipoTenenciaModeloDTO> consultarTiposTenencia(Boolean estado) {
		String firmaMetodo = "AfiliadosBusiness.consultarTiposTenencia(Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<TipoTenenciaModeloDTO> respuesta = consultarTipoTenenciaDTO(null, estado);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarTipoTenenciaPorID(java.lang.Long)
	 */
	@Override
	public TipoTenenciaModeloDTO consultarTipoTenenciaPorID(Long idTipoTenencia) {
		String firmaMetodo = "AfiliadosBusiness.consultarTipoTenenciaPorID(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		TipoTenenciaModeloDTO respuesta = null;

		// se hace la consulta en la BD
		List<TipoTenenciaModeloDTO> resultadoConsulta = consultarTipoTenenciaDTO(idTipoTenencia, true);

		// se comprueba s se ha recibido un resultado
		if (!resultadoConsulta.isEmpty()) {
			respuesta = resultadoConsulta.get(0);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#inactivarActivarTipoTenencia(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@Override
	public RespuestaServicioInfraestructuraDTO inactivarActivarTipoTenencia(Long idTipoTenencia, Boolean activar) {
		String firmaMetodo = "AfiliadosBusiness.inactivarActivarTipoTenencia(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		RespuestaServicioInfraestructuraDTO respuesta = new RespuestaServicioInfraestructuraDTO();

		List<TipoTenencia> tiposTenencia = consultarTipoTenencia(idTipoTenencia, null);
		TipoTenencia tipoTenenciaParaActualizar = null;

		if (!tiposTenencia.isEmpty()) {
			tipoTenenciaParaActualizar = tiposTenencia.get(0);
		}

		try {
			tipoTenenciaParaActualizar = entityManager.merge(tipoTenenciaParaActualizar);
			tipoTenenciaParaActualizar.setActivo(activar);

			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ACTUALIZACION_EXITOSA
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_TENENCIA));
		} catch (Exception e) {
			// se presenta un error inesperado durante la actualizacin
			String mensaje = MensajesSalidaEnum.ERROR_ACTUALIZACION_COMPLETO
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_TENENCIA, e.getMessage());

			logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
			respuesta.setMensajeRespuesta(MensajesSalidaEnum.ERROR_ACTUALIZACION_SIMPLE
					.getReadableMessage(ConstantesProcesoSitiosPago.TIPO_TENENCIA));
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * desafiliarAfiliacionesEmpleador(com.asopagos.enumeraciones.afiliaciones.
	 * MotivoDesafiliacionBeneficiarioEnum, java.util.List)
	 */
	@Override
	public List<BeneficiarioDTO> desafiliarAfiliacionesEmpleador(
			MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion, List<RolAfiliadoModeloDTO> roles) {
		logger.debug(
				"Inicia servicio desafiliarAfiliacionesEmpleador(MotivoDesafiliacionBeneficiarioEnum, List<RolAfiliadoModeloDTO>)");
		try {
			List<BeneficiarioDTO> listaBeneficiarios = new ArrayList<>();
			for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : roles) {
				if (rolAfiliadoModeloDTO.getAfiliado() != null) {
					listaBeneficiarios.addAll(desafiliarBeneficiario(rolAfiliadoModeloDTO.getAfiliado().getIdAfiliado(),
							rolAfiliadoModeloDTO.getIdRolAfiliado(), motivoDesafiliacion, rolAfiliadoModeloDTO.getFechaRetiro()));
				}
			}
			logger.debug(
					"Finaliza servicio desafiliarAfiliacionesEmpleador(MotivoDesafiliacionBeneficiarioEnum, List<RolAfiliadoModeloDTO>)");
			return listaBeneficiarios;
		} catch (Exception e) {
			logger.debug(
					"Finaliza servicio con error desafiliarAfiliacionesEmpleador(MotivoDesafiliacionBeneficiarioEnum, List<RolAfiliadoModeloDTO>)");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * desafiliarBeneficiarioAfiliado(java.lang.Long, java.lang.Long,
	 * java.lang.Long, com.asopagos.enumeraciones.afiliaciones.
	 * MotivoDesafiliacionBeneficiarioEnum)
	 */
	@Override
	public List<BeneficiarioDTO> desafiliarBeneficiarioAfiliado(Long idAfiliado, Long idRolAfiliado,
			MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion, Long fechaRetiroAfiliado) {
		logger.debug("Inicia servicio desafiliarBeneficiarioAfiliado(InactivacionBeneficiarioDTO");
		try {
			return desafiliarBeneficiario(idAfiliado, idRolAfiliado, motivoDesafiliacion, fechaRetiroAfiliado);
		} catch (Exception e) {
			logger.error("Finaliza error desafiliarBeneficiarioAfiliado(Long,Long,MotivoDesafiliacionBeneficiarioEnum");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Mtodo encargado de realizar la desafiliacin de un beneficiario
	 *	
	 *            id del afiliado principal	 
	 *            id del rol Afiliado al que pertenece
	 *            motivo de desafiliacin
	 */
	private List<BeneficiarioDTO> desafiliarBeneficiario(Long idAfiliado, Long idRolAfiliado,
			MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion, Long fechaRetiroAfiliado) {
		TypedQuery<Beneficiario> query = null;
		List<Beneficiario> beneficiarios = null;
		List<BeneficiarioDTO> listBeneficiario = new ArrayList<>();
		logger.info("**__**desafiliarBeneficiario ingresos  ahora named query BUSCAR_BENEFICIARIOS_AFILIADO_ACTIVO");
		query = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_AFILIADO_ACTIVO, Beneficiario.class)
				.setParameter("idAfiliado", idAfiliado).setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
				.setParameter("totalAfiliacionActiva", 1L);
		beneficiarios = query.getResultList();
		if (idRolAfiliado != null && beneficiarios != null && beneficiarios.isEmpty()) {
			query = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_POR_AFILIACIONES_AFILIADO,
							Beneficiario.class)
					.setParameter("idAfiliado", idAfiliado).setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
					.setParameter("idRolAfiliado", idRolAfiliado).setParameter("totalAfiliacionActiva", 1L);
			beneficiarios = query.getResultList();
		}
		if (beneficiarios != null && !beneficiarios.isEmpty()) {
			for (Beneficiario beneficiario : beneficiarios) {
				beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
				beneficiario.setMotivoDesafiliacion(motivoDesafiliacion);
                beneficiario.setFechaRetiro(fechaRetiroAfiliado != null ? new Date(fechaRetiroAfiliado) : new Date());
				beneficiario = entityManager.merge(beneficiario);

				// Se agrega a la lista de beneficiarios modificados
				listBeneficiario.add(BeneficiarioDTO.convertBeneficiarioToDTO(beneficiario, null, null, null));
			}
		}
		return listBeneficiario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * desafiliarBeneficiarioEmpleador(java.lang.Long,
	 * com.asopagos.enumeraciones.afiliaciones.
	 * MotivoDesafiliacionBeneficiarioEnum)
	 */
	@Override
	public void desafiliarBeneficiarioEmpleador(Long idEmpleador,
			MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
		logger.debug("Inicia servicio desafiliarBeneficiarioEmpleador(InactivacionBeneficiarioDTO");
		try {
			List<RolAfiliado> trabajadoresEmpleador = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_TRABAJADORES_EMPLEADOR)
					.setParameter("idEmpleador", idEmpleador).getResultList();
			for (RolAfiliado rolAfiliado : trabajadoresEmpleador) {
                desafiliarBeneficiario(rolAfiliado.getAfiliado().getIdAfiliado(), null, motivoDesafiliacion,
                        rolAfiliado.getFechaRetiro() != null ? rolAfiliado.getFechaRetiro().getTime() : null);
			}
			logger.debug("Finaliza servicio desafiliarBeneficiarioEmpleador(InactivacionBeneficiarioDTO");
		} catch (Exception e) {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#
	 * consultarValorIngresoMensual(com.asopagos.enumeraciones.personas.
	 * TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	public BigDecimal consultarValorIngresoMensual(TipoIdentificacionEnum tipoIdentificacionJefe,
			String numeroIdentificacionJefe, TipoIdentificacionEnum tipoIdentificacionIntegrante,
			String numeroIdentificacionIntegrante) {
		try {
			logger.debug("Inicia servicio consultarValorIngresosMensuales");
			BigDecimal valorIngresoMensual = null;
			List<PersonaDTO> listaPersonaDTO = new ArrayList<>();
			if (tipoIdentificacionIntegrante != null && numeroIdentificacionIntegrante != null
					&& !numeroIdentificacionIntegrante.isEmpty()) {
				listaPersonaDTO = buscarAfiliados(tipoIdentificacionIntegrante, numeroIdentificacionIntegrante, null,
						null, null, null);
			} else {
				listaPersonaDTO = buscarAfiliados(tipoIdentificacionJefe, numeroIdentificacionJefe, null, null, null,
						null);
			}
			if (listaPersonaDTO != null && !listaPersonaDTO.isEmpty()) {
				PersonaDTO personaDTO = listaPersonaDTO.get(0);
				Object valor = entityManager
						.createNamedQuery(NamedQueriesConstants.FOVIS_CONSULTAR_INGRESOMENSUAL_AFILIADO)
						.setParameter("tipoIdentificacion", personaDTO.getTipoIdentificacion())
						.setParameter("numeroIdentificacion", personaDTO.getNumeroIdentificacion())
						.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
						.getSingleResult();
				valorIngresoMensual = valor != null ? new BigDecimal(valor.toString()) : null;
			}

			if (valorIngresoMensual == null && tipoIdentificacionIntegrante != null
					&& numeroIdentificacionIntegrante != null && !numeroIdentificacionIntegrante.isEmpty()) {
				try {
					Object valor = entityManager
							.createNamedQuery(
									NamedQueriesConstants.FOVIS_CONSULTAR_INGRESO_BENEFICIARIO_ASOCIADO_AFILIADO)
							.setParameter("tipoIdentificacionAfiliado", tipoIdentificacionJefe)
							.setParameter("numeroIdentificacionAfiliado", numeroIdentificacionJefe)
							.setParameter("tipoIdentificacionBen", tipoIdentificacionIntegrante)
							.setParameter("numeroIdentificacionBen", numeroIdentificacionIntegrante)
							.setParameter("estadoBeneficiarioAfiliado", EstadoAfiliadoEnum.ACTIVO).getSingleResult();
					valorIngresoMensual = valor != null ? new BigDecimal(valor.toString()) : null;
				} catch (NoResultException e) {
					valorIngresoMensual = null;
				}
				if (valorIngresoMensual == null) {
					try {
						/*
						 * Se consulta los ingresos mensuales del Integrante de
						 * Hogar
						 */
						Object valor = entityManager
								.createNamedQuery(NamedQueriesConstants.FOVIS_CONSULTAR_INGRESOMENSUAL_INTEGRANTE)
								.setParameter("tipoIdentificacion", tipoIdentificacionIntegrante)
								.setParameter("numeroIdentificacion", numeroIdentificacionIntegrante).getSingleResult();
						valorIngresoMensual = valor != null ? new BigDecimal(valor.toString()) : null;
					} catch (NoResultException e) {
						valorIngresoMensual = null;	
					}
				}
				
				if (valorIngresoMensual == null) {
					try {
						/*
						 * Se consulta los ingresos mensuales del beneficiario
						 */
						Object valor = entityManager
								.createNamedQuery(NamedQueriesConstants.FOVIS_CONSULTAR_INGRESOMENSUAL_BENEFICIARIO)
								.setParameter("tipoIdentificacion", tipoIdentificacionIntegrante)
								.setParameter("numeroIdentificacion", numeroIdentificacionIntegrante)
								.setParameter("estado", EstadoAfiliadoEnum.ACTIVO).getSingleResult();
						valorIngresoMensual = valor != null ? new BigDecimal(valor.toString()) : null;
					} catch (NoResultException e) {
						valorIngresoMensual = null;	
					}
				}
			}
			logger.debug("Finaliza servicio consultarValorIngresosMensuales");
			return valorIngresoMensual;
		} catch (Exception e) {
			logger.info("Ocurri un error en el servicio consultarValorIngresosMensuales: "+e.getMessage());
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#buscarTipoAfiliacionPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String,
	 *      com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
	 */
	@Override
	public PersonaDTO buscarTipoAfiliacionPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		logger.debug(
				"Inicia servicio buscarTipoPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
		Empleador empleador = null;
		RolAfiliado roaAfiliado = null;
		TipoAfiliadoEnum tipoAfiliado = null;
		if (tipoSolicitante.equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR)) {
			try {
				empleador = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION,
								Empleador.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();
			} catch (NoResultException ex) {
				empleador = null;
			}
		} else {
			try {
				if (tipoSolicitante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)) {
					tipoAfiliado = TipoAfiliadoEnum.PENSIONADO;
				}
				if (tipoSolicitante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)) {
					tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE;
				}
				roaAfiliado = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIACIONES_POR_PERSONA, RolAfiliado.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("tipoAfiliado", tipoAfiliado).getSingleResult();
			} catch (NoResultException ex) {
				roaAfiliado = null;
			}
		}
		return consultarTipoAfiliacion(empleador, roaAfiliado);
	}

	/**
	 * Mtodo encargado de consultar la persona que tipo afiliacion tiene
	 * 
	 * @param tipoAfiliacion
	 * @param empleador
	 * @param roaAfiliado
	 * @param persona
	 * @param personaDTO
	 * @return retorna la personaDTO
	 */
	private PersonaDTO consultarTipoAfiliacion(Empleador empleador, RolAfiliado roaAfiliado) {
		try {
			Persona persona = null;
			PersonaDTO personaDTO = null;
			TipoSolicitanteMovimientoAporteEnum tipoAfiliacion = null;
			if (empleador != null) {
				Empresa empresa = empleador.getEmpresa();
				if (empresa != null) {
					persona = empresa.getPersona();
				}
				tipoAfiliacion = TipoSolicitanteMovimientoAporteEnum.EMPLEADOR;
			} else {
				if (roaAfiliado != null) {
					Afiliado afiliado = roaAfiliado.getAfiliado();
					persona = afiliado.getPersona();
					if (roaAfiliado.getTipoAfiliado().equals(TipoAfiliadoEnum.PENSIONADO)) {
						tipoAfiliacion = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
					}
					if (roaAfiliado.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)) {
						tipoAfiliacion = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;
					}
				}
			}
			if (persona != null) {
				personaDTO = new PersonaDTO(persona);
				personaDTO.setTipoSolicitante(tipoAfiliacion);
			}
			logger.debug(
					"Finaliza servicio buscarTipoPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
			return personaDTO;
		} catch (Exception e) {
			logger.error(
					"Ocurri un error inesperado en el servicio buscarTipoPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * Mtodo que realiza la ejecucin del retroactivo automtico para una
	 * persona independiente o pensionada
	 * 
	 * @param idEmpleador
	 *            Identificador del empleador
	 */
	private void ejecutarRetroactivoAutomaticoPersona(Long idPersona, TipoAfiliadoEnum tipoAfiliado) {
		logger.debug("Inicia mtodo ejecutarRetroactivoAutomaticoPersona");
		TipoSolicitanteMovimientoAporteEnum tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;

		if (tipoAfiliado.equals(TipoAfiliadoEnum.PENSIONADO)) {
			tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
		}

		// Consulta y actualiza "AporteGeneral" de la persona, a la fecha actual
		List<Long> listaIdsAporteGeneral = new ArrayList<Long>();
		List<AporteGeneralModeloDTO> listaAporteGeneralDTO = consultarAporteGeneralPersona(idPersona, tipoSolicitante,
				EstadoAporteEnum.VIGENTE, EstadoRegistroAporteEnum.RELACIONADO);
		Object consultaEstadoParametro = CacheManager.getParametroGap(ParametrosGapConstants.APORTES_FUTURO);
		for (AporteGeneralModeloDTO aporteGeneralDTO : listaAporteGeneralDTO) {
			listaIdsAporteGeneral.add(aporteGeneralDTO.getId());
			aporteGeneralDTO.setFechaReconocimiento(new Date().getTime());
			LocalDate periodoFecha = LocalDate.parse(aporteGeneralDTO.getPeriodoAporte()+ "-01");
        	LocalDate periodoActual = LocalDate.now().withDayOfMonth(1);
			periodoActual = periodoActual.minusMonths(1L);
			LocalDate primerDiaMesActual = LocalDate.now().withDayOfMonth(1);

			//Ajuste pruebas Mauricio GLPI 59039
			if(!aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") || (aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") && periodoActual.compareTo(periodoFecha) >= 0)
            || (aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") && primerDiaMesActual.compareTo(periodoFecha) >= 0 && consultaEstadoParametro.toString().equals("ACTIVO"))){  
				logger.info("Entro a REGISTRADO 1"); 
				aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.REGISTRADO);
            }else{
				logger.info("Entro a RELACIONADO 1");          
                aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.RELACIONADO);
			}
			aporteGeneralDTO
					.setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
			crearActualizarAporteGeneral(aporteGeneralDTO);
			if (aporteGeneralDTO != null) {
				// Consulta y actualiza "AporteDetallado" de la persona, a la fecha
				// actual
				List<Long> listaIdAporteGeneral= new ArrayList();
				listaIdAporteGeneral.add(aporteGeneralDTO.getId());
				List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = consultarAporteDetalladoPorIdsGeneralPersona(
						listaIdAporteGeneral, idPersona, tipoAfiliado, EstadoAporteEnum.VIGENTE,
						EstadoRegistroAporteEnum.RELACIONADO);

				for (AporteDetalladoModeloDTO aporteDetalladoDTO : listaAporteDetalladoDTO) {
					//Ajuste pruebas Mauricio GLPI 59039
					periodoActual = periodoActual.minusMonths(1L);
					if(!aporteDetalladoDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") || (aporteDetalladoDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") && periodoActual.compareTo(periodoFecha) >= 0)
            		|| (aporteDetalladoDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO") && primerDiaMesActual.compareTo(periodoFecha) >= 0 && consultaEstadoParametro.toString().equals("ACTIVO"))){            
						logger.info("Entro a REGISTRADO 2");
						aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.REGISTRADO);
					}else{
						logger.info("Entro a RELACIONADO 2");
						aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.RELACIONADO);
					}
						aporteDetalladoDTO.setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
						aporteDetalladoDTO.setFechaMovimiento(new Date().getTime());
						crearActualizarAporteDetallado(aporteDetalladoDTO);
					}
			}
		}

		// if (!listaIdsAporteGeneral.isEmpty()) {
		// 	// Consulta y actualiza "AporteDetallado" de la persona, a la fecha
		// 	// actual
		// 	List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = consultarAporteDetalladoPorIdsGeneralPersona(
		// 			listaIdsAporteGeneral, idPersona, tipoAfiliado, EstadoAporteEnum.VIGENTE,
		// 			EstadoRegistroAporteEnum.RELACIONADO);

		// 	for (AporteDetalladoModeloDTO aporteDetalladoDTO : listaAporteDetalladoDTO) {
		// 		//Ajuste pruebas Mauricio GLPI 59039
		// 	LocalDate periodoFecha = LocalDate.parse(aporteGeneralDTO.getPeriodoAporte());
        // 	LocalDate periodoActual = LocalDate.now().withDayOfMonth(1);
		// 	periodoActual = periodoActual.minusMonths(1L);
		// 	if(aporteDetalladoDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO")){            
        //         aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.RELACIONADO);
        //     }else{
		// 		aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.REGISTRADO);
		// 	}
        //         aporteDetalladoDTO.setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
        //         aporteDetalladoDTO.setFechaMovimiento(new Date().getTime());
		// 		crearActualizarAporteDetallado(aporteDetalladoDTO);
		// 	}
		// }

		logger.debug("Finaliza mtodo ejecutarRetroactivoAutomaticoPersona");
	}

	/**
	 * Mtodo que consulta los aportes generales relacionados a una persona
	 * independiente o pensionada
	 * 
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoSolicitante
	 *            Tipo de solicitante
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param estadoRegistroAporte
	 *            Estado del registro
	 * @return Lista de aporte generales asociados a la persona
	 */
	private List<AporteGeneralModeloDTO> consultarAporteGeneralPersona(Long idPersona,
			TipoSolicitanteMovimientoAporteEnum tipoSolicitante, EstadoAporteEnum estadoAporte,
			EstadoRegistroAporteEnum estadoRegistroAporte) {
		logger.debug("Inicia mtodo consultarAporteGeneralPersona");
		List<AporteGeneralModeloDTO> listaAporteGeneralDTO = new ArrayList<AporteGeneralModeloDTO>();
		List<AporteGeneral> listaAporteGeneral = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_GENERAL_PERSONA, AporteGeneral.class)
				.setParameter("idPersona", idPersona).setParameter("tipoSolicitante", tipoSolicitante)
				.setParameter("estadoRegistroAporte", estadoRegistroAporte).setParameter("estadoAporte", estadoAporte)
				.getResultList();

		for (AporteGeneral aporteGeneral : listaAporteGeneral) {
			AporteGeneralModeloDTO aporteGeneralDTO = new AporteGeneralModeloDTO();
			aporteGeneralDTO.convertToDTO(aporteGeneral);
			listaAporteGeneralDTO.add(aporteGeneralDTO);
		}

		logger.debug("Inicia mtodo consultarAporteGeneralPersona");
		return listaAporteGeneralDTO;
	}

	/**
	 * Mtodo que consulta un conjunto de aportes detallados, de acuerdo a una
	 * lista de ids de <code>AporteGeneral</code>, pasada como parmetro
	 * 
	 * @param listaIdsAporteGeneral
	 *            Lista de ids de <code>AporteGeneral</code>
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoAfiliado
	 *            Tipo de afiliado
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param estadoRegistroAporte
	 *            Estado del registro del aporte
	 * @return La lista de aportes detallados
	 */
	private List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneralPersona(
			List<Long> listaIdsAporteGeneral, Long idPersona, TipoAfiliadoEnum tipoAfiliado,
			EstadoAporteEnum estadoAporte, EstadoRegistroAporteEnum estadoRegistroAporte) {
		logger.debug("Inicia mtodo consultarAporteDetalladoPorIdsGeneralPersona");
		List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = new ArrayList<AporteDetalladoModeloDTO>();
		List<AporteDetallado> listaAporteGeneral = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_APORTE_DETALLADO_IDS_GENERAL_PERSONA,
						AporteDetallado.class)
				.setParameter("idPersona", idPersona).setParameter("tipoAfiliado", tipoAfiliado)
				.setParameter("estadoRegistroAporte", estadoRegistroAporte).setParameter("estadoAporte", estadoAporte)
				.setParameter("listaIdsAporteGeneral", listaIdsAporteGeneral).getResultList();

		for (AporteDetallado aporteDetallado : listaAporteGeneral) {
			AporteDetalladoModeloDTO aporteDetalladoDTO = new AporteDetalladoModeloDTO();
			aporteDetalladoDTO.convertToDTO(aporteDetallado);
			listaAporteDetalladoDTO.add(aporteDetalladoDTO);
		}

		logger.debug("Inicia mtodo consultarAporteDetalladoPorIdsGeneralPersona");
		return listaAporteDetalladoDTO;
	}

	/**
	 * Mtodo que crea o actualiza un registro en la tabla
	 * <code>AporteGeneral</code>
	 * 
	 * @param aporteGeneralDTO
	 *            Informacin del registro a actualizar
	 */
	private void crearActualizarAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
		logger.debug("Inicia mtodo crearActualizarAporteGeneral");
		AporteGeneral aporteGeneral = aporteGeneralDTO.convertToEntity();
		entityManager.merge(aporteGeneral);
		logger.debug("Inicia mtodo crearActualizarAporteGeneral");
	}

	/**
	 * Mtodo de creacin o actualizacin de un registro en
	 * <code>AporteDetallado</code>
	 * 
	 * @param aporteDetalladoDTO
	 *            Datos del registro a modificar
	 */
	private void crearActualizarAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoDTO) {
		logger.debug("Inicia mtodo crearActualizarDevolucionAporte");
		AporteDetallado aporteDetallado = aporteDetalladoDTO.convertToEntity();
		entityManager.merge(aporteDetallado);
		logger.debug("Inicia mtodo crearActualizarDevolucionAporte");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarAfiliadoPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<AfiliadoModeloDTO> consultarAfiliadoPrincipal(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido) {

		String firmaServicio = "AfiliadosBusiness.consultarAfiliadoPrincipal(TipoIdentificacionEnum,String,String,String,String,String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<Afiliado> listaPersonas = null;

		final String PORCENTAJE = "%";

		String parametroPrimerNombre = null;
		String parametroSegundoNombre = null;
		String parametroPrimerApellido = null;
		String parametroSegundoApellido = null;

		if (primerNombre != null) {
			parametroPrimerNombre = PORCENTAJE + primerNombre + PORCENTAJE;
		}
		if (segundoNombre != null) {
			parametroSegundoNombre = PORCENTAJE + segundoNombre + PORCENTAJE;
		}
		if (primerApellido != null) {
			parametroPrimerApellido = PORCENTAJE + primerApellido + PORCENTAJE;
		}
		if (segundoApellido != null) {
			parametroSegundoApellido = PORCENTAJE + segundoApellido + PORCENTAJE;
		}

		try {
			listaPersonas = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_POR_FILTROS, Afiliado.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("primerNombre", parametroPrimerNombre)
					.setParameter("segundoNombre", parametroSegundoNombre)
					.setParameter("primerApellido", parametroPrimerApellido)
					.setParameter("segundoApellido", parametroSegundoApellido).getResultList();
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en " + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		List<AfiliadoModeloDTO> listaAfiliados = new ArrayList<>();

		for (int i = 0; i < listaPersonas.size(); i++) {
			PersonaDTO personaDTO = new PersonaDTO(listaPersonas.get(i).getPersona());
			AfiliadoModeloDTO personaModeloDTO = new AfiliadoModeloDTO();
			personaModeloDTO.setIdAfiliado(listaPersonas.get(i).getIdAfiliado());
			personaModeloDTO.convertFromPersonaDTO(personaDTO);

			listaAfiliados.add(personaModeloDTO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return listaAfiliados.isEmpty() ? null : listaAfiliados;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiarioPrincipal(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficiarioModeloDTO> consultarBeneficiarioPrincipal(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido) {

		String firmaServicio = "AfiliadosBusiness.consultarBeneficiarioPrincipal(TipoIdentificacionEnum,String,String,String,String,String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<BeneficiarioModeloDTO> listaBeneficiarios = null;

		List<Object[]> lstBeneficiarios = null;

		final String PORCENTAJE = "%";

		String parametroPrimerNombre = null;
		String parametroSegundoNombre = null;
		String parametroPrimerApellido = null;
		String parametroSegundoApellido = null;

		if (primerNombre != null) {
			parametroPrimerNombre = PORCENTAJE + primerNombre + PORCENTAJE;
		}
		if (segundoNombre != null) {
			parametroSegundoNombre = PORCENTAJE + segundoNombre + PORCENTAJE;
		}
		if (primerApellido != null) {
			parametroPrimerApellido = PORCENTAJE + primerApellido + PORCENTAJE;
		}
		if (segundoApellido != null) {
			parametroSegundoApellido = PORCENTAJE + segundoApellido + PORCENTAJE;
		}

		try {
			lstBeneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_POR_FILTROS)
					.setParameter("tipoIdentificacion",
							tipoIdentificacion != null ? tipoIdentificacion.toString() : null)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("primerNombre", parametroPrimerNombre)
					.setParameter("segundoNombre", parametroSegundoNombre)
					.setParameter("primerApellido", parametroPrimerApellido)
					.setParameter("segundoApellido", parametroSegundoApellido).getResultList();
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en " + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		if (lstBeneficiarios.isEmpty()) {

			logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
			return listaBeneficiarios;
		}

		listaBeneficiarios = new ArrayList<>();

		for (int i = 0; i < lstBeneficiarios.size(); i++) {

			Persona persona = new Persona();
			persona.setIdPersona(Long.valueOf((lstBeneficiarios.get(i)[0]).toString()));
			persona.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(lstBeneficiarios.get(i)[1].toString()));
			persona.setNumeroIdentificacion(lstBeneficiarios.get(i)[2].toString());
			if (lstBeneficiarios.get(i)[3] != null)
				persona.setPrimerNombre(lstBeneficiarios.get(i)[3].toString());
			if (lstBeneficiarios.get(i)[4] != null)
				persona.setSegundoNombre(lstBeneficiarios.get(i)[4].toString());
			if (lstBeneficiarios.get(i)[5] != null)
				persona.setPrimerApellido(lstBeneficiarios.get(i)[5].toString());
			if (lstBeneficiarios.get(i)[6] != null)
				persona.setSegundoApellido(lstBeneficiarios.get(i)[6].toString());

			Beneficiario ben = new Beneficiario();
			ben.setIdBeneficiario(Long.valueOf(lstBeneficiarios.get(i)[7].toString()));
			ben.setPersona(persona);
			BeneficiarioDetalle benDet = new BeneficiarioDetalle();
			benDet.setIdBeneficiarioDetalle(Long.valueOf(lstBeneficiarios.get(i)[8].toString()));
			BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO(ben, benDet);
			listaBeneficiarios.add(beneficiarioModeloDTO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return listaBeneficiarios.isEmpty() ? null : listaBeneficiarios;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiariosPorGrupoFamiliar(java.lang.Long)
	 */
	@Override
	public List<BeneficiarioModeloDTO> consultarBeneficiariosPorGrupoFamiliar(Long idGrupoFamiliar) {
		String firmaServicio = "AfiliadosBusiness.consultarBeneficiariosPorGrupoFamiliar(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		List<BeneficiarioModeloDTO> lstBeneficiarioModeloDTO = null;
		BeneficiarioModeloDTO beneficiarioModeloDTO = null;
		List<Object[]> lstBeneficiariosPorGrupoFamiliar = null;
		try {
			lstBeneficiariosPorGrupoFamiliar = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_POR_GRUPO_FAMILIAR)
					.setParameter("idGrupoFamiliar", idGrupoFamiliar).getResultList();
		} catch (Exception e) {
			logger.error("Ocurri un error inesperado en " + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		if (lstBeneficiariosPorGrupoFamiliar != null && lstBeneficiariosPorGrupoFamiliar.isEmpty()) {
			logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
			return lstBeneficiarioModeloDTO;
		}

		lstBeneficiarioModeloDTO = new ArrayList<>();

		for (Object[] beneficiario : lstBeneficiariosPorGrupoFamiliar) {
			beneficiarioModeloDTO = new BeneficiarioModeloDTO();
			beneficiarioModeloDTO.setIdPersona(Long.valueOf((beneficiario[0]).toString()));
			beneficiarioModeloDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(beneficiario[1].toString()));
			beneficiarioModeloDTO.setNumeroIdentificacion(beneficiario[2].toString());
			if (beneficiario[3] != null)
				beneficiarioModeloDTO.setPrimerNombre(beneficiario[3].toString());
			if (beneficiario[4] != null)
				beneficiarioModeloDTO.setSegundoNombre(beneficiario[4].toString());
			if (beneficiario[5] != null)
				beneficiarioModeloDTO.setPrimerApellido(beneficiario[5].toString());
			if (beneficiario[6] != null)
				beneficiarioModeloDTO.setSegundoApellido(beneficiario[6].toString());
			if (beneficiario[7] != null)
				beneficiarioModeloDTO
						.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.valueOf(beneficiario[7].toString()));
			lstBeneficiarioModeloDTO.add(beneficiarioModeloDTO);
		}
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return lstBeneficiarioModeloDTO.isEmpty() ? null : lstBeneficiarioModeloDTO;
	}

	@Override
	public RolAfiliadoModeloDTO consultarRolAfiliadoEspecifico(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado, Long idEmpleador) {
		try {
			RolAfiliadoModeloDTO rolAfiliadoDTO = null;
			if (idEmpleador != null) {
				rolAfiliadoDTO = (RolAfiliadoModeloDTO) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_ROLAFILIADO_POR_FILTROS_CON_EMPLEADOR)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("idEmpleador", idEmpleador).setParameter("tipoAfiliado", tipoAfiliado)
						.getSingleResult();

			} else {
				rolAfiliadoDTO = (RolAfiliadoModeloDTO) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_ROLAFILIADO_POR_FILTROS_SIN_EMPLEADOR)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("tipoAfiliado", tipoAfiliado).getSingleResult();
			}
			return rolAfiliadoDTO;

		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliados.service.AfiliadosService#consultarSolicitudGlobalPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public SolicitudGlobalPersonaDTO consultarSolicitudGlobalPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		String firmaServicio = "consultarSolicitudGlobalPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);";
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

		SolicitudGlobalPersonaDTO solicitudPersona = null;
		try {
			solicitudPersona = (SolicitudGlobalPersonaDTO) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_PERSONA)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("estadoSolicitud", EstadoSolicitudAfiliacionPersonaEnum.RADICADA).getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return solicitudPersona;
	}


    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#obtenerInfoDetalladaAfiliadoComoPensionado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public AfiliadoPensionadoVista360DTO obtenerInfoDetalladaAfiliadoComoPensionado(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        String firmaServicio = "obtenerInfoDetalladaAfiliadoComoPensionado(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        try {
            // Se consulta el estado de la persona como independiente
            List<ConsultarEstadoDTO> listConsulta = new ArrayList<ConsultarEstadoDTO>();
            ConsultarEstadoDTO consulaEstado = new ConsultarEstadoDTO();
            consulaEstado.setEntityManager(entityManager);
            consulaEstado.setNumeroIdentificacion(numeroIdentificacion);
            consulaEstado.setTipoIdentificacion(tipoIdentificacion);
            consulaEstado.setTipoPersona(ConstantesComunes.TIPO_ROL);
            consulaEstado.setTipoRol(TipoAfiliadoEnum.PENSIONADO.name());
            listConsulta.add(consulaEstado);
            List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsulta);
			logger.info("estados " +estados.size());
            EstadoAfiliadoEnum estado = null;
            if (estados != null && !estados.isEmpty()) {
                estado = estados.get(0).getEstado();
            }

			logger.info("estado " + estado);
            
            Object[] objeto = (Object[])entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_DETALLADA_AFILIADO_PENSIONADO)
                    .setParameter("tipoAfiliado", TipoAfiliadoEnum.PENSIONADO.name())
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
            
            AfiliadoPensionadoVista360DTO afiliado = new AfiliadoPensionadoVista360DTO();
            
            afiliado.setEstadoPensionado(estado);
            afiliado.setUltimaFechaIngreso(objeto[1] != null ? objeto[1].toString() : null);
            // logger.info("FECHA AFILIADOS"+ objeto[1].toString());
			afiliado.setCanal(objeto[2] != null ? CanalRecepcionEnum.valueOf(objeto[2].toString()) : null);
            afiliado.setFechaRetiro(objeto[3] != null ? objeto[3].toString() : null);
            afiliado.setEstadoServicios(objeto[4] != null ? Boolean.valueOf(objeto[4].toString()) : null);
            afiliado.setUltimoAporteRecibido(objeto[5] != null ? objeto[5].toString() : null);
            afiliado.setFechaUltimoAporteRecibido(objeto[6] != null ? objeto[6].toString() : null);
            afiliado.setPeriodoPagado(objeto[7] != null ? objeto[7].toString() : null);
            afiliado.setPagadorPensiones(objeto[8] != null ? objeto[8].toString() : null);
            afiliado.setEstadoConEntidadPagadora(objeto[9] != null ? EstadoActivoInactivoEnum.valueOf(objeto[9].toString()) : null);
            afiliado.setValorMesada(objeto[10] != null ? BigDecimal.valueOf(Double.parseDouble(objeto[10].toString())) : null);
            afiliado.setIdAnteEntidadPagadora(objeto[11] != null ? objeto[11].toString() : null);
            afiliado.setEntidadPagadoraAportes(objeto[12] != null ? objeto[12].toString() : null);
            afiliado.setClasePensionado(objeto[13] != null ? ClasificacionEnum.valueOf(objeto[13].toString()) : null);
            afiliado.setNumeroRadicado(objeto[14] != null ? objeto[14].toString() : null);
            afiliado.setIdInstanciaProceso(objeto[15] != null ? objeto[15].toString() : null);
            afiliado.setIdSolicitud(objeto[16] != null ? objeto[16].toString() : null);
            afiliado.setMotivoDesafiliacion(objeto[17] != null ? MotivoDesafiliacionAfiliadoEnum.valueOf(objeto[17].toString()) : null);
            afiliado.setFechaRecepcionDocumento(objeto[18] != null ? objeto[18].toString() : null);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return afiliado;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarCategoriaConyugeAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public List<CategoriaDTO> consultarCategoriaConyugeAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaServicio = "consultarCategoriaConyugeAfiliado(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        try {
            List<Categoria> categorias = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_CONYUGE_AFILIADO)
            .setParameter("tipoIdentificacion", tipoIdentificacion)
            .setParameter("numeroIdentificacion", numeroIdentificacion)
            .getResultList();
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return null;
            //TODO HACER LA LOGICA DE MAPEAR LA INFO AL DTO Y RETORNAR EL ARREGLO 
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarTiposAfiliacionAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public List<TipoAfiliadoEnum> consultarTiposAfiliacionAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaServicio = "consultarTiposAfiliacionAfiliado(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<TipoAfiliadoEnum> tiposAfiliacion = new ArrayList<>();
        try {
                tiposAfiliacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPOS_AFILIACION_AFILIADO)
                        .setParameter("tipoIdentificacion", tipoIdentificacion)
                        .setParameter("numeroIdentificacion", numeroIdentificacion)
                        .getResultList();
                
                if( !tiposAfiliacion.contains(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)){
                    List<String> relacionAporte = (List<String>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RELACION_COMO_DEPENDIENTE_POR_APORTES)
                            .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                            .setParameter("numeroIdentificacion", numeroIdentificacion)
                            .setParameter("tipoCotizante", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.getName())
                            .getResultList(); 

                    if(relacionAporte != null && !relacionAporte.isEmpty() && relacionAporte.get(0) != null){
                        tiposAfiliacion.add(TipoAfiliadoEnum.valueOf(relacionAporte.get(0)));
                    }
                } 
                if( !tiposAfiliacion.contains(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)){
                    List<String> relacionAporte = (List<String>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RELACION_COMO_DEPENDIENTE_POR_APORTES)
                            .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                            .setParameter("numeroIdentificacion", numeroIdentificacion)
                            .setParameter("tipoCotizante", TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.getName())
                            .getResultList(); 

                    if(relacionAporte != null && !relacionAporte.isEmpty() && relacionAporte.get(0) != null){
                        tiposAfiliacion.add(TipoAfiliadoEnum.valueOf(relacionAporte.get(0)));
                    }
                } 
                if( !tiposAfiliacion.contains(TipoAfiliadoEnum.PENSIONADO)){
                    List<String> relacionAporte = (List<String>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RELACION_COMO_DEPENDIENTE_POR_APORTES)
                            .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                            .setParameter("numeroIdentificacion", numeroIdentificacion)
                            .setParameter("tipoCotizante", TipoAfiliadoEnum.PENSIONADO.getName())
                            .getResultList(); 

                    if(relacionAporte != null && !relacionAporte.isEmpty() && relacionAporte.get(0) != null){
                        tiposAfiliacion.add(TipoAfiliadoEnum.valueOf(relacionAporte.get(0)));
                    }
                }
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                return tiposAfiliacion;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#obtenerInfoDetalladaAfiliadoComoIndependiente(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public AfiliadoIndependienteVista360DTO obtenerInfoDetalladaAfiliadoComoIndependiente(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        String firmaServicio = "obtenerInfoDetalladaAfiliadoComoIndependiente(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        try {
            // Se consulta el estado de la persona como independiente
            List<ConsultarEstadoDTO> listConsulta = new ArrayList<ConsultarEstadoDTO>();
            ConsultarEstadoDTO consulaEstado = new ConsultarEstadoDTO();
            consulaEstado.setEntityManager(entityManager);
            consulaEstado.setNumeroIdentificacion(numeroIdentificacion);
            consulaEstado.setTipoIdentificacion(tipoIdentificacion);
            consulaEstado.setTipoPersona(ConstantesComunes.TIPO_ROL);
            consulaEstado.setTipoRol(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name());
            listConsulta.add(consulaEstado);
            List<EstadoDTO> estados = EstadosUtils.consultarEstadoCaja(listConsulta);
            EstadoAfiliadoEnum estado = null;
            if (estados != null && !estados.isEmpty()) {
                estado = estados.get(0).getEstado();
            }
            
            Object[] objeto = (Object[])entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_DETALLADA_AFILIADO_INDEPENDIENTE)
                    .setParameter("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name())
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
            
            AfiliadoIndependienteVista360DTO afiliado = new AfiliadoIndependienteVista360DTO ();
            
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            
            afiliado.setEstadoIndependiente(estado);
            afiliado.setUltimaFechaIngreso(objeto[1] != null ? objeto[1].toString() : null);
            afiliado.setCanal(objeto[2] != null ? CanalRecepcionEnum.valueOf(objeto[2].toString()) : null);
            afiliado.setFechaRetiro(objeto[3] != null ? simpleDateFormat.format(simpleDateFormat.parse(objeto[3].toString())) : null);
            afiliado.setEstadoServicios(objeto[4] != null ? Boolean.valueOf(objeto[4].toString()) : null);
            afiliado.setUltimoAporteRecibido(objeto[5] != null ? objeto[5].toString() : null);
            afiliado.setValorUltimoSalarioRecibido(objeto[6] != null ? BigDecimal.valueOf(Double.valueOf(objeto[6].toString())) : null);
            afiliado.setFechaUltimoAporteRecibido(objeto[7] != null ? objeto[7].toString() : null);
            afiliado.setPeriodoPagado(objeto[8] != null ? objeto[8].toString() : null);
            afiliado.setClaseIndependiente(objeto[9] != null ? ClaseIndependienteEnum.valueOf(objeto[9].toString()) : null);
            afiliado.setPorcentajePagoAportes(objeto[10] != null ? objeto[10].toString() : null);
            afiliado.setIngresosMensuales(objeto[11] != null ? BigDecimal.valueOf(Double.valueOf(objeto[11].toString())) : null);
            afiliado.setNumeroRadicado(objeto[12] != null ? objeto[12].toString() : null);
            afiliado.setIdInstanciaProceso(objeto[13] != null ? objeto[13].toString() : null);
            afiliado.setIdSolicitud(objeto[14] != null ? objeto[14].toString() : null);
            afiliado.setMotivoDesafiliacion(objeto[15] != null ? MotivoDesafiliacionAfiliadoEnum.valueOf(objeto[15].toString()) : null);
            afiliado.setFechaRecepcionDocumento(objeto[16] != null ? objeto[16].toString() : null);
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return afiliado;
        } catch (NoResultException nre) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, nre);
            return null;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#obtenerInfoAfiliadoRespectoEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public InfoAfiliadoRespectoEmpleadorDTO obtenerInfoAfiliadoRespectoEmpleador(TipoIdentificacionEnum tipoIdAfiliado,
			String numeroIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
		String firmaServicio = "obtenerInfoAfiliadoRespectoEmpleador(TipoIdentificacionEnum, String, TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		try {
			InfoAfiliadoRespectoEmpleadorDTO info = new InfoAfiliadoRespectoEmpleadorDTO();

			List<InfoEstadoAfiliadoDTO> infoEstadoAfiliado = (List<InfoEstadoAfiliadoDTO>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_ESTADO_AFILIADO)
					.setParameter("tipoIdAfiliado", tipoIdAfiliado.name()).setParameter("numeroIdAfiliado", numeroIdAfiliado)
					.setParameter("tipoIdEmpleador", tipoIdEmpleador.name())
					.setParameter("numeroIdEmpleador", numeroIdEmpleador).getResultList();

			List<String> estadoActualAfiliado = (List<String>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFI_ACTUAL_AFILIADO_RESP_EMPLEADOR)
					.setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
					.setParameter("numeroIdAfiliado", numeroIdAfiliado)
					.setParameter("tipoIdEmpleador", tipoIdEmpleador.name())
					.setParameter("numeroIdEmpleador", numeroIdEmpleador).getResultList();

			EstadoAfiliadoEnum estado = null;

			if (estadoActualAfiliado != null && !estadoActualAfiliado.isEmpty()
					&& estadoActualAfiliado.get(0) != null) {
				estado = EstadoAfiliadoEnum.valueOf(estadoActualAfiliado.get(0));
			}
			if (infoEstadoAfiliado != null && !infoEstadoAfiliado.isEmpty()) {
				info.setInfoEstadoAfiliado(infoEstadoAfiliado.get(0));

				if (info.getInfoEstadoAfiliado().getIdRolAfiliado() != null) {

					List<String> nombreSucursal = (List<String>) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_SUCURSAL)
							.setParameter("idRolAfiliado", info.getInfoEstadoAfiliado().getIdRolAfiliado())
							.getResultList();

					if (nombreSucursal != null && !nombreSucursal.isEmpty()) {
						info.getInfoEstadoAfiliado().setSucursalAsociada(nombreSucursal.get(0));
					}
				}

				// if(estado!= null){
				// info.getInfoEstadoAfiliado().setEstadoTrabRespectoEmpl(estado);
				// }
				if (info.getInfoEstadoAfiliado().getIdEmpresa() != null
						&& info.getInfoEstadoAfiliado().getIdPersona() != null) {
					List<InfoUltimoAporteDTO> infoUltimoAporte = (List<InfoUltimoAporteDTO>) entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_ULTIMO_APORTE)
							.setParameter("idEmpresa", info.getInfoEstadoAfiliado().getIdEmpresa())
							.setParameter("idPersona", info.getInfoEstadoAfiliado().getIdPersona()).getResultList();

					if (infoUltimoAporte != null && !infoUltimoAporte.isEmpty()) {
						info.setInfoUltimoAporte(infoUltimoAporte.get(0));

					}
				}
			}

			if (info.getInfoUltimoAporte() == null) {
				List<InfoUltimoAporteDTO> infoUltimoAporte = (List<InfoUltimoAporteDTO>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_ULTIMO_APORTE_TIPO_NUMERO_ID)
						.setParameter("tipoIdPersona", tipoIdAfiliado).setParameter("numeroIdPersona", numeroIdAfiliado)
						.setParameter("tipoIdEmpresa", tipoIdEmpleador)
						.setParameter("numeroIdEmpresa", numeroIdEmpleador).getResultList();

				if (infoUltimoAporte != null && !infoUltimoAporte.isEmpty()) {
					info.setInfoUltimoAporte(infoUltimoAporte.get(0));

				}
			}

			if (estado != null) {
				if (info.getInfoEstadoAfiliado() != null) {
					info.getInfoEstadoAfiliado().setEstadoTrabRespectoEmpl(estado);
				} else {
					InfoEstadoAfiliadoDTO infoPersonaSinRolAfiliado = new InfoEstadoAfiliadoDTO();
					infoPersonaSinRolAfiliado.setEstadoTrabRespectoEmpl(estado);
					info.setInfoEstadoAfiliado(infoPersonaSinRolAfiliado);
				}

			}

			if (info.getInfoEstadoAfiliado() != null && info.getInfoEstadoAfiliado().getIdRolAfiliado() != null) {
				List<Object[]> objects = (List<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.INFO_VACACIONES_Y_SUSPENSION)
						.setParameter("idRolAfiliado", info.getInfoEstadoAfiliado().getIdRolAfiliado()).getResultList();

				if (objects != null && !objects.isEmpty()) {
					InfoVacionesYSuspensionAfiliadoDTO infoVacacionesYSuspension = new InfoVacionesYSuspensionAfiliadoDTO();
					infoVacacionesYSuspension.setVacaciones(false);
					infoVacacionesYSuspension.setSuspensionTempTrabajo(false);
					for (Object[] object : objects) {
						if (TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL.name()
								.equals(object[0].toString())
								|| TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB.name()
										.equals(object[0].toString())) {
							infoVacacionesYSuspension.setVacaciones(true);
							Date fechaInicioVacaciones = CalendarUtils.darFormatoYYYYMMDDGuionDate(object[1].toString());
							infoVacacionesYSuspension.setFechaInicioVacaciones(CalendarUtils.truncarHora(fechaInicioVacaciones));
							Date fechaFinVacaciones = CalendarUtils.darFormatoYYYYMMDDGuionDate(object[2].toString());
							infoVacacionesYSuspension.setFechaFinVacaciones(CalendarUtils.truncarHoraMaxima(fechaFinVacaciones));
						} else if (TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB
								.name().equals(object[0].toString())
								|| TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL
										.name().equals(object[0].toString())) {
							infoVacacionesYSuspension.setSuspensionTempTrabajo(true);
							Date fechaInicioSuspencion = CalendarUtils.darFormatoYYYYMMDDGuionDate(object[1].toString());
							infoVacacionesYSuspension.setFechaInicioSuspencion(CalendarUtils.truncarHora(fechaInicioSuspencion));
							Date fechaFinSuspencion = CalendarUtils.darFormatoYYYYMMDDGuionDate(object[2].toString());
							infoVacacionesYSuspension.setFechaFinSuspencion(CalendarUtils.truncarHoraMaxima(fechaFinSuspencion));
						}
					}
					info.setInfoVacacionesYSuspension(infoVacacionesYSuspension);

				}
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
			return info;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#obtenerEmpleadoresRelacionadosAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
     */
    @Override
    public List<EmpleadorRelacionadoAfiliadoDTO> obtenerEmpleadoresRelacionadosAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
            String numeroIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, String razonSocial) {
        String firmaServicio = "**__**obtenerEmpleadorRelacionadoAfiliado(TipoIdentificacionEnum, String, TipoIdentificacionEnum, String, String)";
        logger.info(firmaServicio);
        try {
            List<EmpleadorRelacionadoAfiliadoDTO> empleadores = new ArrayList<>();
            if(tipoIdAfiliado != null && numeroIdAfiliado != null && 
                    tipoIdEmpleador == null && numeroIdEmpleador == null && 
                    razonSocial == null){
                
                empleadores = (List<EmpleadorRelacionadoAfiliadoDTO>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_EMPLEADORES_RELACIONADOS_AFILIADO_SIN_INFO_EMPLEADOR)
                        .setParameter("tipoIdAfiliado", tipoIdAfiliado)
                        .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                        .getResultList();

					for (EmpleadorRelacionadoAfiliadoDTO emppleadorTemp : empleadores) {
					List<String> estadoActualEmpleador = (List<String>)entityManager.createNativeQuery("SELECT est.empEstadoEmpleador FROM Persona per	INNER JOIN Empresa emp ON emp.empPersona = per.perId	INNER JOIN Empleador empl ON emp.empId = empl.empEmpresa LEFT JOIN VW_EstadoAfiliacionEmpleadorCaja est ON est.perId = per.perId	WHERE empl.empId= :numeroIdEmpleador ")
					.setParameter("numeroIdEmpleador", emppleadorTemp.getIdEmpleador())
					.getResultList();
						
						if(estadoActualEmpleador!= null){
							emppleadorTemp.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoActualEmpleador.get(0)));
						}
					}
                List<Object[]> empleadorPorAportes = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_APORTES_SIN_INFO_EMPLEADOR)
                        .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                        .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                        .getResultList();
                
                
                if(empleadorPorAportes != null && !empleadorPorAportes.isEmpty()){
                    for (Object[] emplApo : empleadorPorAportes) {
                        EmpleadorRelacionadoAfiliadoDTO empleador = new EmpleadorRelacionadoAfiliadoDTO();
                        
                        empleador.setTipoIdentificacion(emplApo[0] != null ? TipoIdentificacionEnum.valueOf(emplApo[0].toString()) : null);
                        empleador.setNumeroIdentificacion(emplApo[1] != null ? emplApo[1].toString() : null);
                        empleador.setRazonSocial(emplApo[2] != null ? emplApo[2].toString() : null);
                        empleador.setEstadoEmpleador(emplApo[3] != null ? EstadoEmpleadorEnum.valueOf(emplApo[3].toString()) : null);
      logger.info("**AILIADOS 1"+empleador.getEstadoEmpleador());
                        empleador.setEstadoAfiliado(EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                        empleador.setIdEmpleador(emplApo[4] != null ? Long.valueOf(emplApo[4].toString()) : null);
						//if(empleador.getEstadoEmpleador() == EstadoEmpleadorEnum.INACTIVO ){
						//	 BigInteger idCategoriaAfiliado;
                        //    try{
                        //        System.out.println("**__** afiliados buscar estado persona : ");  
                        //        idCategoriaAfiliado = (BigInteger)entityManager.createNativeQuery("SELECT top 1 c.ctaId FROM CategoriaAfiliado c inner join Afiliado  a on a.afiId=c.ctaAfiliado inner join RolAfiliado r on r.roaAfiliado=a.afiId inner join SolicitudAfiliacionPersona s on s.sapRolAfiliado=r.roaId inner join Solicitud so on so.solId=s.sapSolicitudGlobal where a.afiId= :idAfiliado and  ctaFechaCambioCategoria >= so.solFechaCreacion AND s.sapEstadoSolicitud != 'CERRADO'")
                        //            .setParameter("idAfiliado", categoria.getIdAfiliado())
                        //            .getSingleResult();
                        //    } catch (NoResultException e) {
                        //        idCategoriaAfiliado = null;
                        //    }
						//}
                        empleador.setIdEmpresa(emplApo[5] != null ? Long.valueOf(emplApo[5].toString()) : null);
                        empleador.setIdAfiliado(null);
                        empleador.setIdRolAfiliado(null);
                        
                        empleadores.add(empleador);
                    }
                }
                
                if(empleadores!= null){
                    for (EmpleadorRelacionadoAfiliadoDTO empl : empleadores) {
                        List<String> estadoActualAfiliado = (List<String>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFI_ACTUAL_AFILIADO_RESP_EMPLEADOR)
                                .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                                .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                                .setParameter("tipoIdEmpleador", empl.getTipoIdentificacion().name())
                                .setParameter("numeroIdEmpleador", empl.getNumeroIdentificacion())
                                .getResultList();
                        
                        if(estadoActualAfiliado!= null && !estadoActualAfiliado.isEmpty()){
                            EstadoAfiliadoEnum estado = empl.getEstadoAfiliado();
                            empl.setEstadoAfiliado(estadoActualAfiliado.get(0) != null ? EstadoAfiliadoEnum.valueOf(estadoActualAfiliado.get(0)) : estado);
                        }
                    }
                }
                
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                return empleadores;
            }
            if(tipoIdEmpleador != null && numeroIdEmpleador != null)
            {
                empleadores = (List<EmpleadorRelacionadoAfiliadoDTO>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_EMPLEADORES_RELACIONADOS_AFILIADO_TIPO_NUM_ID)
                        .setParameter("tipoIdEmpleador", tipoIdEmpleador)
                        .setParameter("numeroIdEmpleador", numeroIdEmpleador)
                        .setParameter("tipoIdAfiliado", tipoIdAfiliado)
                        .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                        .getResultList();
            }
            else if(razonSocial != null)
            {
                empleadores = (List<EmpleadorRelacionadoAfiliadoDTO>)entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_EMPLEADORES_RELACIONADOS_AFILIADO_RAZON_SOCIAL)
                        .setParameter("razonSocial", "%"+razonSocial.toUpperCase()+"%")
                        .setParameter("tipoIdAfiliado", tipoIdAfiliado)
                        .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                        .getResultList();
            }
            
            if(empleadores!=null && empleadores.isEmpty()){
                
                if(tipoIdEmpleador != null && numeroIdEmpleador != null){
                    List<Object[]> empleadorPorAportes = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_APORTES_CON_TIPO_Y_NUMERO_ID)
                            .setParameter("tipoIdEmpleador", tipoIdEmpleador.name())
                            .setParameter("numeroIdEmpleador", numeroIdEmpleador)
                            .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                            .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                            .getResultList();
                    
                    if(empleadorPorAportes != null && !empleadorPorAportes.isEmpty()){
                        EmpleadorRelacionadoAfiliadoDTO empleador = new EmpleadorRelacionadoAfiliadoDTO();
                        
                        empleador.setTipoIdentificacion(empleadorPorAportes.get(0)[0] != null ? TipoIdentificacionEnum.valueOf(empleadorPorAportes.get(0)[0].toString()) : null);
                        empleador.setNumeroIdentificacion(empleadorPorAportes.get(0)[1] != null ? empleadorPorAportes.get(0)[1].toString() : null);
                        empleador.setRazonSocial(empleadorPorAportes.get(0)[2] != null ? empleadorPorAportes.get(0)[2].toString() : null);
                        empleador.setEstadoEmpleador(empleadorPorAportes.get(0)[3] != null ? EstadoEmpleadorEnum.valueOf(empleadorPorAportes.get(0)[3].toString()) : null);
                              logger.info("**AILIADOS 2"+empleador.getEstadoEmpleador());
						empleador.setEstadoAfiliado(null);
                        empleador.setIdEmpleador(empleadorPorAportes.get(0)[4] != null ? Long.valueOf(empleadorPorAportes.get(0)[4].toString()) : null);
                        empleador.setIdAfiliado(null);
                        empleador.setIdRolAfiliado(null);
                        
                        empleadores.add(empleador);
                        
                        
                    }
                }
                else if(razonSocial != null){
                    List<Object[]> empleadorPorAportes = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_APORTES_CON_RAZON_SOCIAL)
                            .setParameter("razonSocial", "%"+razonSocial.toUpperCase()+"%")
                            .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                            .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                            .getResultList();
                    
                    if(empleadorPorAportes != null && !empleadorPorAportes.isEmpty()){
                        EmpleadorRelacionadoAfiliadoDTO empleador = new EmpleadorRelacionadoAfiliadoDTO();
                        
                        empleador.setTipoIdentificacion(empleadorPorAportes.get(0)[0] != null ? TipoIdentificacionEnum.valueOf(empleadorPorAportes.get(0)[0].toString()) : null);
                        empleador.setNumeroIdentificacion(empleadorPorAportes.get(0)[1] != null ? empleadorPorAportes.get(0)[1].toString() : null);
                        empleador.setRazonSocial(empleadorPorAportes.get(0)[2] != null ? empleadorPorAportes.get(0)[2].toString() : null);
                        empleador.setEstadoEmpleador(empleadorPorAportes.get(0)[3] != null ? EstadoEmpleadorEnum.valueOf(empleadorPorAportes.get(0)[3].toString()) : null);
                         logger.info("**AILIADOS 3"+empleador.getEstadoEmpleador());
						empleador.setEstadoAfiliado(null);
                        empleador.setIdEmpleador(empleadorPorAportes.get(0)[4] != null ? Long.valueOf(empleadorPorAportes.get(0)[5].toString()) : null);
                        empleador.setIdAfiliado(null);
                        empleador.setIdRolAfiliado(null);
                        
                        empleadores.add(empleador);
                    }
                }
                
            }
            if(empleadores!= null){
                for (EmpleadorRelacionadoAfiliadoDTO empl : empleadores) {
                    List<String> estadoActualAfiliado = (List<String>)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFI_ACTUAL_AFILIADO_RESP_EMPLEADOR)
                            .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                            .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                            .setParameter("tipoIdEmpleador", empl.getTipoIdentificacion().name())
                            .setParameter("numeroIdEmpleador", empl.getNumeroIdentificacion())
                            .getResultList();
                    
                    if(estadoActualAfiliado!= null && !estadoActualAfiliado.isEmpty()){
                        EstadoAfiliadoEnum estado = empl.getEstadoAfiliado();
                        empl.setEstadoAfiliado(estadoActualAfiliado.get(0) != null ? EstadoAfiliadoEnum.valueOf(estadoActualAfiliado.get(0)) : estado);
                    }
                }
            }
            
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return empleadores;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

//    /* (non-Javadoc)
//     * @see com.asopagos.afiliados.service.AfiliadosService#obtenerCategoriasPropiasAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
//     */
//    @Override
//    public CategoriasComoAfiliadoPrincipal360DTO obtenerCategoriasPropiasAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
//            String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado) {
//        String firmaServicio = "obtenerCategoriasPropiasAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
//        CategoriasComoAfiliadoPrincipal360DTO categoriasAfiliadoPrincipal = new CategoriasComoAfiliadoPrincipal360DTO();
//        try {
//            List<Categoria360DTO> historicoCategorias = new ArrayList<>();
//            
//            List<Categoria> categorias = (List<Categoria>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_CATEGORIA_ACTUAL_AFI_PRINCIPAL)
//                   .setParameter("tipoIdAfiliado", tipoIdAfiliado)
//                   .setParameter("numeroIdAfiliado", numeroIdAfiliado)
//                   .getResultList();
//            
//            if(categorias != null && !categorias.isEmpty()){
//                categoriasAfiliadoPrincipal.setCategoriaMarcada(false);
//                categorias.forEach( (categoria) ->{
//                    if(!categoriasAfiliadoPrincipal.getCategoriaMarcada() && categoria.getTipoAfiliado()!= null && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(categoria.getTipoAfiliado())){
//                        categoriasAfiliadoPrincipal.setCategoriaMarcada(true);
//                        categoriasAfiliadoPrincipal.setCategoriaDependiente(categoria.getCategoriaPersona());
//                    }
//                    if(!categoriasAfiliadoPrincipal.getCategoriaMarcada() && categoria.getTipoAfiliado()!= null && TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(categoria.getTipoAfiliado())){
//                        categoriasAfiliadoPrincipal.setCategoriaMarcada(true);
//                        categoriasAfiliadoPrincipal.setCategoriaIndependiente(categoria.getCategoriaPersona());
//                    }
//                    if(!categoriasAfiliadoPrincipal.getCategoriaMarcada() && categoria.getTipoAfiliado()!= null && TipoAfiliadoEnum.PENSIONADO.equals(categoria.getTipoAfiliado())){
//                        categoriasAfiliadoPrincipal.setCategoriaMarcada(true);
//                        categoriasAfiliadoPrincipal.setCategoriaPensionado(categoria.getCategoriaPersona());
//                    }
//                    
//                    Categoria360DTO cat = new Categoria360DTO();
//                    cat.setCategoriaDependiente(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(categoria.getTipoAfiliado()) ? categoria.getCategoriaPersona() : null);
//                    cat.setCategoriaIndependiente(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(categoria.getTipoAfiliado()) ? categoria.getCategoriaPersona() : null);
//                    cat.setCategoriaPensionado(TipoAfiliadoEnum.PENSIONADO.equals(categoria.getTipoAfiliado()) ? categoria.getCategoriaPersona() : null);
//                    cat.setFechaCambioCategoria(categoria.getFechaCambioCategoria() != null ? categoria.getFechaCambioCategoria() : null);
//                    cat.setMotivoCambioCategoria(categoria.getMotivoCambioCategoria() != null ? categoria.getMotivoCambioCategoria() : null);
//                    
//                    historicoCategorias.add(cat);
//                });
//                
//                categoriasAfiliadoPrincipal.setCategoriaMarcada(null);
//                categoriasAfiliadoPrincipal.setCategorias(historicoCategorias);
//            }
//            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
//            
//            return categoriasAfiliadoPrincipal;
//        } catch (Exception e) {
//            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
//            return categoriasAfiliadoPrincipal;
//        }
//    }
    
//    /* (non-Javadoc)
//     * @see com.asopagos.afiliados.service.AfiliadosService#obtenerCategoriasConyugeAfiliado(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
//     */
//    @Override
//    public CategoriasComoAfiliadoPrincipal360DTO obtenerCategoriasConyugeAfiliado(TipoIdentificacionEnum tipoIdAfiliado,
//            String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado) {
//        String firmaServicio = "obtenerCategoriasConyugeAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum)";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
//        CategoriasComoAfiliadoPrincipal360DTO categoriasAfiliadoPrincipal = new CategoriasComoAfiliadoPrincipal360DTO();
//        try {
//            
//            List<Categoria360DTO> historicoCategorias = new ArrayList<>();
//            
//            
//            List<Categoria> categorias = (List<Categoria>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_CONYUGE_AFILIADO)
//                    .setParameter("tipoIdentificacion", tipoIdAfiliado)
//                    .setParameter("numeroIdentificacion", numeroIdAfiliado)
//                    .getResultList();
//            
//            if(categorias != null && !categorias.isEmpty()){
//                categoriasAfiliadoPrincipal.setCategoriaMarcada(false);
//                categorias.forEach( (categoria) ->{
//                    if(!categoriasAfiliadoPrincipal.getCategoriaMarcada() && categoria.getTipoAfiliado()!= null && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(categoria.getTipoAfiliado())){
//                        categoriasAfiliadoPrincipal.setCategoriaMarcada(true);
//                        categoriasAfiliadoPrincipal.setCategoriaDependiente(categoria.getCategoriaPersona());
//                    }
//                    if(!categoriasAfiliadoPrincipal.getCategoriaMarcada() && categoria.getTipoAfiliado()!= null && TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(categoria.getTipoAfiliado())){
//                        categoriasAfiliadoPrincipal.setCategoriaMarcada(true);
//                        categoriasAfiliadoPrincipal.setCategoriaIndependiente(categoria.getCategoriaPersona());
//                    }
//                    if(!categoriasAfiliadoPrincipal.getCategoriaMarcada() && categoria.getTipoAfiliado()!= null && TipoAfiliadoEnum.PENSIONADO.equals(categoria.getTipoAfiliado())){
//                        categoriasAfiliadoPrincipal.setCategoriaMarcada(true);
//                        categoriasAfiliadoPrincipal.setCategoriaPensionado(categoria.getCategoriaPersona());
//                    }
//                    
//                    Categoria360DTO cat = new Categoria360DTO();
//                    cat.setCategoriaDependiente(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(categoria.getTipoAfiliado()) ? categoria.getCategoriaPersona() : null);
//                    cat.setCategoriaIndependiente(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(categoria.getTipoAfiliado()) ? categoria.getCategoriaPersona() : null);
//                    cat.setCategoriaPensionado(TipoAfiliadoEnum.PENSIONADO.equals(categoria.getTipoAfiliado()) ? categoria.getCategoriaPersona() : null);
//                    cat.setFechaCambioCategoria(categoria.getFechaCambioCategoria() != null ? categoria.getFechaCambioCategoria() : null);
//                    cat.setMotivoCambioCategoria(categoria.getMotivoCambioCategoria() != null ? categoria.getMotivoCambioCategoria() : null);
//                    
//                    historicoCategorias.add(cat);
//                });
//                
//                categoriasAfiliadoPrincipal.setCategoriaMarcada(null);
//                categoriasAfiliadoPrincipal.setCategorias(historicoCategorias);
//            }
//            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
//            
//            return categoriasAfiliadoPrincipal;
//        } catch (Exception e) {
//            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
//            return categoriasAfiliadoPrincipal;
//        }
//    }


//    @Override
//    public CategoriasComoBeneficiario360DTO obtenerCategoriasHeredadas(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
//            Boolean isAfiliadoPrincipal) {
//        String firmaServicio = "obtenerCategoriasHeredadas(TipoIdentificacionEnum, String, Boolean)";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
//        CategoriasComoBeneficiario360DTO categoriasComoBeneficiario = new CategoriasComoBeneficiario360DTO();
//        try {
//
//            List<CategoriaHeredada360DTO> historicoCategorias = new ArrayList<>();
//            
//            
//            List<Object[]> categorias = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CATEGORIA_HEREDADA_AFI_PPAL)
//                    .setParameter("tipoIdentificacion", tipoIdAfiliado.name())
//                    .setParameter("numeroIdentificacion", numeroIdAfiliado)
//                    .getResultList();
//            
//            if(categorias != null && !categorias.isEmpty()){
//                for (Object[] objeto : categorias) {
//                    
//                    categoriasComoBeneficiario.setCategoriaMarcada(false);
//                    if(!categoriasComoBeneficiario.getCategoriaMarcada() && objeto[10] != null){
//                        TipoAfiliadoEnum tipoAfiliado = TipoAfiliadoEnum.valueOf(objeto[10].toString());
//                        if(objeto[11] != null && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)){
//                            categoriasComoBeneficiario.setCategoriaMarcada(true);
//                            categoriasComoBeneficiario.setCategoriaDependiente(objeto[11] != null ? CategoriaPersonaEnum.valueOf(objeto[11].toString()) : null);
//                        }
//                        if(objeto[11] != null && TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliado)){
//                            categoriasComoBeneficiario.setCategoriaMarcada(true);
//                            categoriasComoBeneficiario.setCategoriaIndependiente(objeto[11] != null ? CategoriaPersonaEnum.valueOf(objeto[11].toString()) : null);
//                        }
//                        if(objeto[11] != null && TipoAfiliadoEnum.PENSIONADO.equals(tipoAfiliado)){
//                            categoriasComoBeneficiario.setCategoriaMarcada(true);
//                            categoriasComoBeneficiario.setCategoriaPensionado(objeto[11] != null ? CategoriaPersonaEnum.valueOf(objeto[11].toString()) : null);
//                        }
//                        
//                        categoriasComoBeneficiario.setTipoIdentificacion(objeto[1] != null ? TipoIdentificacionEnum.valueOf(objeto[1].toString()) : null);
//                        categoriasComoBeneficiario.setNumeroIdentificacion(objeto[2] != null ? objeto[2].toString() : null);
//                        categoriasComoBeneficiario.setEstadoActualAfiliado(objeto[3] != null ? EstadoAfiliadoEnum.valueOf(objeto[3].toString()) : null);
//                        String nombres = new String(objeto[4] != null ? objeto[4].toString() : "");
//                        nombres = nombres + (objeto[5] != null ? " "+objeto[5].toString() : "")+(objeto[6] != null ? " "+objeto[6].toString() : "")+(objeto[7] != null ? " "+objeto[7].toString() : "");
//                        categoriasComoBeneficiario.setNombres(nombres);
//                        categoriasComoBeneficiario.setParentezco(objeto[8] != null ? TipoBeneficiarioEnum.valueOf(ClasificacionEnum.valueOf(objeto[8].toString()).getSujetoTramite().getName()) : null);
//                        categoriasComoBeneficiario.setClasificacion(objeto[9] != null ? ClasificacionEnum.valueOf(objeto[8].toString()) : null);
//                        
//                    }
//
//                    CategoriaHeredada360DTO cat = new CategoriaHeredada360DTO();
//                    cat.setCategoriaDependiente(categoriasComoBeneficiario.getCategoriaDependiente()!= null ? (objeto[11] != null ? CategoriaPersonaEnum.valueOf(objeto[11].toString()) : null): null);
//                    cat.setCategoriaIndependiente(categoriasComoBeneficiario.getCategoriaIndependiente()!= null ? (objeto[11] != null ? CategoriaPersonaEnum.valueOf(objeto[11].toString()) : null): null);
//                    cat.setCategoriaPensionado(categoriasComoBeneficiario.getCategoriaPensionado()!= null ? (objeto[11] != null ? CategoriaPersonaEnum.valueOf(objeto[11].toString()) : null): null);
//                    cat.setFechaCambioCategoria(objeto[12] != null ? objeto[12].toString() : null);
//                    cat.setMotivoCambioCategoria(objeto[13] != null ? MotivoCambioCategoriaEnum.valueOf(objeto[13].toString()) : null);
//                    
//                    historicoCategorias.add(cat);
//                }
//
//                categoriasComoBeneficiario.setCategoriaMarcada(null);
//                categoriasComoBeneficiario.setCategorias(historicoCategorias);
//            }
//            
//            
//            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
//            return categoriasComoBeneficiario;
//        } catch (Exception e) {
//            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
//            return categoriasComoBeneficiario;
//        }
//    }

    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarInformacionRelacionLaboral(java.lang.Long)
     */
    @Override
    public InfoRelacionLaboral360DTO consultarInformacionRelacionLaboral(Long idRolAfiliado) {
        String firmaServicio = "consultarInformacionRelacionLaboral(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        InfoRelacionLaboral360DTO info = new InfoRelacionLaboral360DTO();
        try {
            if(idRolAfiliado != null){
                info = (InfoRelacionLaboral360DTO)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_RELACION_LABORAL)
                        .setParameter("idRolAfiliado", idRolAfiliado)
                        .getSingleResult();
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return info;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            return info;
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarHistoricoAfiliadoComoBeneficiario(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public List<HistoricoAfiBeneficiario360DTO> consultarHistoricoAfiliadoComoBeneficiario(TipoIdentificacionEnum tipoIdAfiliado,
            String numeroIdAfiliado) {
        String firmaServicio = "consultarHistoricoAfiliadoComoBeneficiario(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<HistoricoAfiBeneficiario360DTO> historicoAfiBeneficiario = new ArrayList<>();
        try {
            historicoAfiBeneficiario = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFILIADO_COMO_BENEFICIARIO)
                    .setParameter("tipoIdAfiliado", tipoIdAfiliado.name())
                    .setParameter("numeroIdAfiliado", numeroIdAfiliado)
                    .getResultList();
            
            for (HistoricoAfiBeneficiario360DTO historico : historicoAfiBeneficiario) {
                historico.setParentezcoAfiPrincipal(TipoBeneficiarioEnum.valueOf(historico.getClasificacion().getSujetoTramite().getName()));
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return historicoAfiBeneficiario;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            return historicoAfiBeneficiario;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarEstadoAfiliacionRespectoCCF(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long)
     */
    @Override
    public ConsultaEstadoAfiliacionDTO consultarEstadoAfiliacionRespectoCCF(TipoIdentificacionEnum tipoIdPersona, String numIdPersona,
            Long idPersona) {
        String firmaServicio = "AfiliadosBusiness.consultarEstadoAfiliacionRespectoCCF(TipoIdentificacionEnum, String, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        ConsultaEstadoAfiliacionDTO result;
        
        // se debe contar con el ID de la persona o el tipo y numero de identificacin de la persona
        if((tipoIdPersona == null || numIdPersona == null) && idPersona == null){
            String mensaje = " :: No se cuenta con los parmetros bsicos para la consulta";
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + mensaje);
            throw new TechnicalException(
                    MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + mensaje);
        }
        
        try{
            Object[] resultQuery = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_RESPECTO_CCF)
                    .setParameter("idPersona", idPersona).setParameter("tipoIdPersona", tipoIdPersona != null ? tipoIdPersona.name() : null)
                    .setParameter("numIdPersona", numIdPersona).getSingleResult();

            result = new ConsultaEstadoAfiliacionDTO();
            result.setEstadoAfiliacion(EstadoAfiliadoEnum.valueOf((String) resultQuery[0]));
            result.setFechaRetiro(resultQuery[1] != null ? ((Date) resultQuery[1]).getTime() : null);
            result.setIdPersona(resultQuery[2] != null ? ((BigInteger) resultQuery[2]).longValue() : null);
            result.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) resultQuery[3]));
            result.setNumeroIdentificacion((String) resultQuery[4]);
        } catch (NoResultException e) {
            result = new ConsultaEstadoAfiliacionDTO();
            result.setIdPersona(idPersona);
            result.setTipoIdentificacion(tipoIdPersona);
            result.setNumeroIdentificacion(numIdPersona);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    @Override
    public ConsultaEstadoAfiliacionDTO consultarEstadoAfiliacionRespectoTipoAfiliacion(TipoIdentificacionEnum tipoIdPersona,
            String numIdPersona, Long idPersona, TipoIdentificacionEnum tipoIdEmpleador, String numIdEmpleador, Long idPerEmpleador,
            TipoAfiliadoEnum tipoAfiliado) {
        String firmaServicio = "AfiliadosBusiness.consultarEstadoAfiliacionRespectoTipoAfiliacion(TipoIdentificacionEnum, String, Long, "
                + "TipoIdentificacionEnum, String, Long, TipoAfiliadoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConsultaEstadoAfiliacionDTO result = null;
        
        // se debe contar con el ID de la persona o el tipo y numero de identificacin de la persona
        if(((tipoIdPersona == null || numIdPersona == null) && idPersona == null)
                && (tipoIdEmpleador == null || numIdEmpleador == null) && idPerEmpleador == null){
            String mensaje = " :: No se cuenta con los parmetros bsicos para la consulta";
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + mensaje);
            throw new TechnicalException(
                    MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + mensaje);
        }
        
        try{
            Query query = null;
            
            if (tipoAfiliado != null) {
                switch (tipoAfiliado) {
                    case PENSIONADO:
                        query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_PENSIONADO)
                                .setParameter("idPersona", idPersona)
                                .setParameter("tipoIdPersona", tipoIdPersona != null ? tipoIdPersona.name() : null)
                                .setParameter("numIdPersona", numIdPersona);
                        break;
                    case TRABAJADOR_DEPENDIENTE:
                        query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_DEPENDIENTE)
                                .setParameter("idPersona", idPersona)
                                .setParameter("tipoIdPersona", tipoIdPersona != null ? tipoIdPersona.name() : null)
                                .setParameter("numIdPersona", numIdPersona).setParameter("idPerEmpleador", idPerEmpleador)
                                .setParameter("tipoIdEmpleador", tipoIdEmpleador != null ? tipoIdEmpleador.name() : null)
                                .setParameter("numIdEmpleador", numIdEmpleador);
                        break;
                    case TRABAJADOR_INDEPENDIENTE:
                        query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_INDEPENDIENTE)
                                .setParameter("idPersona", idPersona)
                                .setParameter("tipoIdPersona", tipoIdPersona != null ? tipoIdPersona.name() : null)
                                .setParameter("numIdPersona", numIdPersona);
                        break;
                    default:
                        break;
                }
            }
            else {
                query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_RESPECTO_CCF)
                        .setParameter("idPersona", idPersona)
                        .setParameter("tipoIdPersona", tipoIdPersona != null ? tipoIdPersona.name() : null)
                        .setParameter("numIdPersona", numIdPersona);
            }
            
            Object[] resultQuery = (Object[]) query.getSingleResult();

            result = new ConsultaEstadoAfiliacionDTO();
            result.setEstadoAfiliacion(EstadoAfiliadoEnum.valueOf((String) resultQuery[0]));
            result.setFechaRetiro(resultQuery[1] != null ? ((Date) resultQuery[1]).getTime() : null);
            result.setIdPersona(resultQuery[2] != null ? ((BigInteger) resultQuery[2]).longValue() : null);
            result.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) resultQuery[3]));
            result.setNumeroIdentificacion((String) resultQuery[4]);
        } catch (NoResultException e) {
            result = new ConsultaEstadoAfiliacionDTO();
            result.setIdPersona(idPersona);
            result.setTipoIdentificacion(tipoIdPersona);
            result.setNumeroIdentificacion(numIdPersona);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#actualizarBeneficiarioSimple(com.asopagos.dto.BeneficiarioDTO)
     */
    @Override
    public void actualizarBeneficiarioSimple(BeneficiarioDTO beneficiario) {
        ActualizarBeneficiarioSimpleRutine a = new ActualizarBeneficiarioSimpleRutine();
        a.actualizarBeneficiarioSimple(beneficiario, entityManager);
    }
 
    /**
     * Crea los certificados escolares
     * @param certificadoEscolarBeneficiario
     *        Informacin certificado escolar
     */
    private void crearActualizarCertificadoEscolar(CertificadoEscolarBeneficiario certificadoEscolarBeneficiario, Long idPersona, Boolean esRadicacion) {
        CertificadoEscolarBeneficiario certificado = consultarCertificadoEscolarVigentePorIdPersona(idPersona);
        // Si es radicacion se toma el certificado previamente registrado y se actualiza con la informacion enviada
        if (esRadicacion && certificado != null) {
            certificadoEscolarBeneficiario.setIdCertificadoEscolarBeneficiario(certificado.getIdCertificadoEscolarBeneficiario());
        }
        
        if ((certificadoEscolarBeneficiario.getFechaVencimientoCertificadoEscolar() != null
                || certificadoEscolarBeneficiario.getFechaRecepcionCertificadoEscolar() != null)
                && certificadoEscolarBeneficiario.getIdBeneficiarioDetalle() != null) {
            if (certificadoEscolarBeneficiario.getFechaCreacionCertificadoEscolar() == null) {
                certificadoEscolarBeneficiario.setFechaCreacionCertificadoEscolar(new Date());
            }
            entityManager.merge(certificadoEscolarBeneficiario);
        }
    }

    /**
     * Consultar el ultimo certificado registrado asociado al beneficiario por el identificador de la persona beneficiario
     * @param idPersona
     *        Identificador persona beneficiario
     * @return Informacin ultimo certificado registrado o <code>null</code> en caso de no encontrar certificado
     */
    private CertificadoEscolarBeneficiario consultarCertificadoEscolarVigentePorIdPersona(Long idPersona) {
        List<CertificadoEscolarBeneficiario> listCertificados = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CERTIFICADO_ESCOLAR_POR_ID_PERSONA,
                        CertificadoEscolarBeneficiario.class)
                .setParameter("idPersona", idPersona).getResultList();
        if (listCertificados != null && !listCertificados.isEmpty()) {
            return listCertificados.get(0);
        }
        return null;
    }

    @Override
    public void crearActualizarRolSustitucionPatronal(List<RolAfiliadoModeloDTO> listRolDTO) {
        String firmaServicio = "AfiliadosBusiness.crearActualizarRolSustitucionPatronal(List<RolAfiliadoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        for (RolAfiliadoModeloDTO rol : listRolDTO) {
            // Se crea el nuevo RolAfiliado
            crearRolAfiliadoSustitucionPatronal(rol);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Registra la informacin del rol afiliado con los mismos datos del existe pero con la nueva empresa
     * Registra la informacin del rol afiliado con los mismos datos del existe pero con la nueva empresa
     * @param rolAfiliadoModeloDTO
     * @param rolAfiliadoModeloDTO
     *        Informacin actual rolAfiliado
     *        Informacin actual rolAfiliado
     * @return Rol Afiliado creado
     * @return Rol Afiliado creado
     */
	private RolAfiliado crearRolAfiliadoSustitucionPatronal(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        RolAfiliado rolAfiliadoOriginal = rolAfiliadoModeloDTO.convertToEntity();
        RolAfiliado rolAfiliado =  new RolAfiliado();
        rolAfiliado.setAfiliado(rolAfiliadoOriginal.getAfiliado());
        rolAfiliado.setCanalReingreso(rolAfiliadoOriginal.getCanalReingreso());
        rolAfiliado.setCargo(rolAfiliadoOriginal.getCargo());
        rolAfiliado.setClaseTrabajador(rolAfiliadoOriginal.getClaseTrabajador());
        rolAfiliado.setEmpleador(rolAfiliadoOriginal.getEmpleador());
        rolAfiliado.setEstadoAfiliado(rolAfiliadoOriginal.getEstadoAfiliado());
        rolAfiliado.setFechaAfiliacion(rolAfiliadoOriginal.getFechaAfiliacion());
        //GLPI 67296 CC sustitucion patronal, se debe independizar la fecha de fechaAfiliacion y la fecha de afiliacion
        rolAfiliado.setFechaIngreso(rolAfiliadoOriginal.getFechaIngreso());
		//rolAfiliado.setFechaIngreso(new Date());
        rolAfiliado.setFechaFinContrato(rolAfiliadoOriginal.getFechaFinContrato());
        rolAfiliado.setSucursalEmpleador(rolAfiliadoOriginal.getSucursalEmpleador());
        rolAfiliado.setSustitucionPatronal(rolAfiliadoOriginal.getSustitucionPatronal());
        rolAfiliado.setTipoAfiliado(rolAfiliadoOriginal.getTipoAfiliado());
        rolAfiliado.setTipoContrato(rolAfiliadoOriginal.getTipoContrato());
        rolAfiliado.setTipoSalario(rolAfiliadoOriginal.getTipoSalario());
        rolAfiliado.setValorSalarioMesadaIngresos(rolAfiliadoOriginal.getValorSalarioMesadaIngresos());
                rolAfiliado.setFechaFinCondicionVet(rolAfiliadoOriginal.getFechaFinCondicionVet());
                rolAfiliado.setFechaInicioCondicionVet(rolAfiliadoOriginal.getFechaInicioCondicionVet());
        entityManager.persist(rolAfiliado);
        return rolAfiliado;
    }
    

    /**
     * Consulta la informacin de los grupos familiares asociados a la persona afiliado principal
     * y en los cuales el afiliado es el administrador de subsidio obteniendo el medio de pago asociado
     * @param idPersona
     *        Identificador de persona
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<Long> consultarGruposFamiliaresAsociadosPersona(Long idPersona) {
		List<Long> idsGrupos = new ArrayList<>();
		try {
			idsGrupos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPOS_FAM_CON_ADMIN_SUBS_IGUAL_DEPEND)
					.setParameter("medioActivo", Boolean.TRUE).setParameter("mismoAfiliado", Boolean.TRUE).setParameter("idPersona", idPersona)
					.getResultList();
			logger.info("**__**consultarGruposFamiliaresAsociadosPersona idsGrupos "+idsGrupos);
		} catch (Exception e) {
			logger.error("Error inesperado en consultarGruposFamiliaresAsociadosPersona", e);
			return null;
		}
		return idsGrupos;
    }

    /**
     * Consulta contra anibol si la persona enviada como parametro tiene relacionada una tarjeta ACTIVA
     * @param tipoIdentificacion
     *        Tipo identificacin persona
     * @param numeroIdentificacion
     *        Numero identificacin persona
     * @return Informacin de la tarjeta en anibol, <code>null</code> en caso de excepcin
     */
    private MedioDePagoModeloDTO consultarTarjetaPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            String tipoIdentificacionAnibol = 
                    com.asopagos.clienteanibol.enums.TipoIdentificacionEnum.valueOf(tipoIdentificacion.name()).getTipoIdentificacion();
            String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
            TarjetaDTO tarjetaDTO = null;
            if(conexionAnibol.equals("TRUE")){
            ConsultarTarjetaActiva consultarTarjetaActiva = new ConsultarTarjetaActiva(numeroIdentificacion, tipoIdentificacionAnibol);
            consultarTarjetaActiva.execute();
            tarjetaDTO = consultarTarjetaActiva.getResult();
            }
            MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
            if(tarjetaDTO != null && tarjetaDTO.getNumeroTarjeta() != null){
                medioDePagoModeloDTO.setNumeroTarjeta(tarjetaDTO.getNumeroTarjeta());
                medioDePagoModeloDTO.setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum.ACTIVA);
            }
            return medioDePagoModeloDTO;
        } catch (Exception e) {
            logger.error("Error inesperado en consultarTarjetaPersona", e);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarTarjetaVista360(TipoIdentificacionEnum, String)
     */
    @Override
    public MedioDePagoModeloDTO consultarTarjetaVista360(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        // Se consulta la informacin de la tarjeta
        MedioDePagoModeloDTO medioPagoTarjeta = consultarTarjetaPersona(tipoIdentificacion, numeroIdentificacion);
        if (medioPagoTarjeta != null && medioPagoTarjeta.getNumeroTarjeta() != null) {
            // Se realiza ajuste al numero de la tarjeta
            medioPagoTarjeta.setNumeroTarjeta(TarjetaUtils.truncarNumeroTarjeta(medioPagoTarjeta.getNumeroTarjeta()));
        }
        return medioPagoTarjeta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarBeneficiariosAfiliacion(Long, Long)
     */
    @Override
    public List<BeneficiarioModeloDTO> consultarBeneficiariosAfiliacion(Long idAfiliado, Long idRolAfiliado) {
		  System.out.println("**__**consultarBeneficiariosAfiliacion idAfiliado: "+idAfiliado +" idRolAfiliado: "+idRolAfiliado );
        TypedQuery<Beneficiario> query = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_AFILIADO_ACTIVO, Beneficiario.class)
                .setParameter("idAfiliado", idAfiliado).setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
                .setParameter("totalAfiliacionActiva", 1L);
        List<Beneficiario> beneficiarios = query.getResultList();
		
        // if (idRolAfiliado != null && beneficiarios != null && beneficiarios.isEmpty()) {
        //     query = entityManager
        //             .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_POR_AFILIACIONES_AFILIADO,
        //                     Beneficiario.class)
        //             .setParameter("idAfiliado", idAfiliado).setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
        //             .setParameter("idRolAfiliado", idRolAfiliado).setParameter("totalAfiliacionActiva", 1L);
        //     beneficiarios = query.getResultList();
        // }
        List<BeneficiarioModeloDTO> listBeneficiario = new ArrayList<>();
        if (beneficiarios != null && !beneficiarios.isEmpty()) {
            for (Beneficiario beneficiario : beneficiarios) {
                listBeneficiario.add( new BeneficiarioModeloDTO(beneficiario, null));
            }
        }
		  System.out.println("**__**FinconsultarBeneficiariosAfiliacion cantidad:" +listBeneficiario.size());
        return listBeneficiario;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliados.service.AfiliadosService#consultarAfiliacionJefeHogar(TipoIdentificacionEnum, String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AfiliacionJefeHogarDTO consultarAfiliacionJefeHogar(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicio consultarAfiliacionJefeHogar("+tipoIdentificacion+","+numeroIdentificacion+")");
        List<AfiliacionJefeHogarDTO> listaAfiliaciones = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIACION_JEFE_HOGAR, AfiliacionJefeHogarDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name()).setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();
        AfiliacionJefeHogarDTO afiliacionJefe;
        if (!listaAfiliaciones.isEmpty()) {
            afiliacionJefe = listaAfiliaciones.get(0);
        } else {
            afiliacionJefe = new AfiliacionJefeHogarDTO();
        }
        return afiliacionJefe;
    }

    @Override
    public List<AfiliadoSolicitudDTO> consultarAfiliadosUnGrupoFamiliar() {
        List<Object[]> afiliadosObj = new ArrayList<>();
        List<AfiliadoSolicitudDTO> afiliados = new ArrayList<>();
        
        afiliadosObj = (List<Object[]>)entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_UN_GRUPO_FAMILIAR)
                .getResultList();
        
        for (Object[] afiliadoObj : afiliadosObj) {
            afiliados.add(new AfiliadoSolicitudDTO(Long.valueOf(afiliadoObj[0].toString()), Long.valueOf(afiliadoObj[1].toString()), Long.valueOf(afiliadoObj[2].toString()), afiliadoObj[3].toString(),TipoIdentificacionEnum.valueOf(afiliadoObj[4].toString())));
        }
        
        return afiliados;
    }
    
    @Override
    public List<Long> registrarPersonasBeneficiariosAbreviada(Long idAfiliado, List<BeneficiarioDTO> beneficiariosDTO) {
        logger.debug("Inicia registrarPersonasBeneficiariosAbreviada(Long, List<BeneficiarioDTO>)");
        List<Long> idsBeneficiarios = new ArrayList<Long>();
        if (!beneficiariosDTO.isEmpty()) {
            for (BeneficiarioDTO bene : beneficiariosDTO) {
                Long idBeneficiario = registrarDatosPersonaBeneficiarioAfiAbreviada(idAfiliado, bene);
                idsBeneficiarios.add(idBeneficiario);
            }
        }
        logger.debug("Finaliza registrarPersonasBeneficiariosAbreviada(Long, List<BeneficiarioDTO>)");
        return idsBeneficiarios;
    }
    
    @Override
    public Long registrarDatosPersonaBeneficiarioAfiAbreviada(Long idAfiliado, BeneficiarioDTO beneficiarioDTO) {
        
        try {
            logger.debug("Inicia registrarDatosPersonaBeneficiarioAfiAbreviada(Long, BeneficiarioDTO)");
            boolean ingresoPersona = false;
            boolean ingresoInvaliz = false;
            boolean ingresoDetalle = false;
            Persona persona = null;
            PersonaDetalle personaDetalle = null;
            CondicionInvalidez condiInvalidez = null;

            try {
                condiInvalidez = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_CONDICION_INVALIDEZ_BENEFICIARIO,
                                CondicionInvalidez.class)
                        .setParameter("tipoIdentificacion", beneficiarioDTO.getPersona().getTipoIdentificacion())
                        .setParameter("numeroIdentificacion", beneficiarioDTO.getPersona().getNumeroIdentificacion())
                        .getSingleResult();
            } catch (NoResultException e) {
                condiInvalidez = new CondicionInvalidez();
                ingresoInvaliz = true;
            }

            try {
                persona = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION,
                                Persona.class)
                        .setParameter("tipoIdentificacion", beneficiarioDTO.getPersona().getTipoIdentificacion())
                        .setParameter("numeroIdentificacion", beneficiarioDTO.getPersona().getNumeroIdentificacion())
                        .getSingleResult();
            } catch (NoResultException nre) {
                persona = new Persona();
                ingresoPersona = true;
            }

            try {
                personaDetalle = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
                                PersonaDetalle.class)
                        .setParameter("tipoIdentificacion", persona.getTipoIdentificacion())
                        .setParameter("numeroIdentificacion", persona.getNumeroIdentificacion()).getSingleResult();

            } catch (NoResultException nre) {
                personaDetalle = new PersonaDetalle();
                ingresoDetalle = true;
            }
            if (beneficiarioDTO.getPersona().getTipoIdentificacion() != null) {
                persona.setTipoIdentificacion(beneficiarioDTO.getPersona().getTipoIdentificacion());
            }
            if (beneficiarioDTO.getPersona().getNumeroIdentificacion() != null
                    && !beneficiarioDTO.getPersona().getNumeroIdentificacion().isEmpty()) {
                persona.setNumeroIdentificacion(beneficiarioDTO.getPersona().getNumeroIdentificacion());
            }
            if (beneficiarioDTO.getPersona().getPrimerNombre() != null
                    && !beneficiarioDTO.getPersona().getPrimerNombre().isEmpty()) {
                persona.setPrimerNombre(beneficiarioDTO.getPersona().getPrimerNombre());
            }
            if (beneficiarioDTO.getPersona().getSegundoNombre() != null
                    && !beneficiarioDTO.getPersona().getSegundoNombre().isEmpty()) {
                persona.setSegundoNombre(beneficiarioDTO.getPersona().getSegundoNombre());
            }
            if (beneficiarioDTO.getPersona().getPrimerApellido() != null
                    && !beneficiarioDTO.getPersona().getPrimerApellido().isEmpty()) {
                persona.setPrimerApellido(beneficiarioDTO.getPersona().getPrimerApellido());
            }
            if (beneficiarioDTO.getPersona().getSegundoApellido() != null
                    && !beneficiarioDTO.getPersona().getSegundoApellido().isEmpty()) {
                persona.setSegundoApellido(beneficiarioDTO.getPersona().getSegundoApellido());
            }
            if (beneficiarioDTO.getPersona().getFechaNacimiento() != null) {
                personaDetalle.setFechaNacimiento(new Date(beneficiarioDTO.getPersona().getFechaNacimiento()));
            }
            if (beneficiarioDTO.getPersona().getGradoAcademico() != null) {
                personaDetalle.setGradoAcademico(beneficiarioDTO.getPersona().getGradoAcademico());
            }
            if (beneficiarioDTO.getEstudianteTrabajoDesarrolloHumano() != null) {
                personaDetalle
                        .setEstudianteTrabajoDesarrolloHumano(beneficiarioDTO.getEstudianteTrabajoDesarrolloHumano());
            }

            if (ingresoPersona) {
                entityManager.persist(persona);
                personaDetalle.setIdPersona(persona.getIdPersona());
                entityManager.persist(personaDetalle);
            } else {
                entityManager.merge(persona);
                if (ingresoDetalle) {
                    personaDetalle.setIdPersona(persona.getIdPersona());
                    entityManager.persist(personaDetalle);
                } else {
                    entityManager.merge(personaDetalle);
                }
            }

            Afiliado afiliado = new Afiliado();
            afiliado.setIdAfiliado(idAfiliado);

            if (beneficiarioDTO.getInvalidez() != null) {
                condiInvalidez.setCondicionInvalidez(beneficiarioDTO.getInvalidez());
            }
            if (beneficiarioDTO.getFechaReporteInvalidez() != null) {
                condiInvalidez.setFechaReporteInvalidez(beneficiarioDTO.getFechaReporteInvalidez());
            }
            if (beneficiarioDTO.getComentariosInvalidez() != null
                    && !beneficiarioDTO.getComentariosInvalidez().isEmpty()) {
                condiInvalidez.setComentarioInvalidez(beneficiarioDTO.getComentariosInvalidez());
            }

            if (ingresoInvaliz) {
                if (condiInvalidez.getCondicionInvalidez() != null) {
                    condiInvalidez.setIdPersona(persona.getIdPersona());
                    entityManager.persist(condiInvalidez);
                }
            } else {
                entityManager.merge(condiInvalidez);
            }

            List<ItemChequeoDTO> listaChequeo = beneficiarioDTO.getListaChequeo();

            if (listaChequeo != null && !listaChequeo.isEmpty()) {
                for (ItemChequeoDTO itemChequeoDTO : listaChequeo) {
                    ItemChequeo itemChequeo = new ItemChequeo();
                    if (itemChequeoDTO.getIdRequisito() != null) {
                        Requisito requisito = new Requisito();
                        requisito.setIdRequisito(itemChequeoDTO.getIdRequisito());
                        itemChequeo.setRequisito(requisito);
                    }
                    if (itemChequeoDTO.getIdSolicitudGlobal() != null) {
                        Solicitud solicitud = new Solicitud();
                        solicitud.setIdSolicitud(itemChequeoDTO.getIdSolicitudGlobal());
                        itemChequeo.setSolicitudGlobal(solicitud);
                    }
                    else if(beneficiarioDTO.getIdSolicitud() != null) {
                        Solicitud solicitud = new Solicitud();
                        solicitud.setIdSolicitud(beneficiarioDTO.getIdSolicitud());
                        itemChequeo.setSolicitudGlobal(solicitud);
                    }
                    itemChequeo.setPersona(persona);
                    if (itemChequeoDTO.getComentarios() != null && !itemChequeoDTO.getComentarios().isEmpty()) {
                        itemChequeo.setComentarios(itemChequeoDTO.getComentarios());
                    }
                    if (itemChequeoDTO.getComentariosBack() != null && !itemChequeoDTO.getComentariosBack().isEmpty()) {
                        itemChequeo.setComentariosBack(itemChequeoDTO.getComentariosBack());
                    }
                    if (itemChequeoDTO.getCumpleRequisito() != null) {
                        itemChequeo.setCumpleRequisito(itemChequeoDTO.getCumpleRequisito());
                    }
                    if (itemChequeoDTO.getEstadoRequisito() != null) {
                        itemChequeo.setEstadoRequisito(itemChequeoDTO.getEstadoRequisito());
                    }
                    if (itemChequeoDTO.getFormatoEntregaDocumento() != null) {
                        itemChequeo.setFormatoEntregaDocumento(itemChequeoDTO.getFormatoEntregaDocumento());
                    }
                    if (itemChequeoDTO.getIdentificadorDocumento() != null
                            && !itemChequeoDTO.getIdentificadorDocumento().isEmpty()) {
                        itemChequeo.setIdentificadorDocumento(itemChequeoDTO.getIdentificadorDocumento());
                    }
                    if (itemChequeoDTO.getVersionDocumento() != null) {
                        itemChequeo.setVersionDocumento(itemChequeoDTO.getVersionDocumento());
                    }
                    entityManager.persist(itemChequeo);
                }
            }
            logger.debug("Finaliza registrarDatosPersonaBeneficiarioAfiAbreviada(Long, BeneficiarioDTO)");
            return null;
        } catch (NoResultException nre) {
            logger.debug(
                    "Finaliza registrarDatosPersonaBeneficiarioAfiAbreviada(Long, BeneficiarioDTO):No se pudo registrar beneficiario, recursos insuficientes");
            return null;
        } catch (Exception e) {
            logger.debug("Finaliza registrarDatosPersonaBeneficiarioAfiAbreviada(Long, BeneficiarioDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    
    }
    /**
     * Consultar el ultimo certificado registrado asociado al beneficiario por el identificador de la persona beneficiario
     * @param idPersona
     *        Identificador persona beneficiario
     * @return Informacin ultimo certificado registrado o <code>null</code> en caso de no encontrar certificado
     */
    @Override
    public CertificadoEscolarBeneficiarioDTO consultarCertificadoEscolarVigentePersona(Long idPersona) {
        CertificadoEscolarBeneficiarioDTO certificadoEscolarBeneficiarioDTO = null;
        
        List<CertificadoEscolarBeneficiario> listCertificados = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CERTIFICADO_ESCOLAR_POR_ID_PERSONA,
                        CertificadoEscolarBeneficiario.class)
                .setParameter("idPersona", idPersona).getResultList();
        if (listCertificados != null && !listCertificados.isEmpty()) {
            certificadoEscolarBeneficiarioDTO = CertificadoEscolarBeneficiarioDTO.convertToDTO(listCertificados.get(0));
        }
        return certificadoEscolarBeneficiarioDTO;
    }

    /**
     * Consultar si el beneficiario tiene una condicin de invalidez vigente por el identificador de la persona beneficiario
     * @author Francisco Alejandro Hoyos Rojas
     * @param idPersona Identificador persona beneficiario
     * @return Informacin de la ltima condicin de invalidez registrada o <code>null</code> en caso de no encontrar ninguna condicin de invalidez
     */
	@Override
	public CondicionInvalidezModeloDTO consultarCondicionInvalidezVigentePersona(Long idPersona) {
		try {
            logger.debug("Inicia consultarCondicionInvalidezVigentePersona(Long idPersona)");
            CondicionInvalidezModeloDTO condicionInvalidezModeloDTO = null;
            CondicionInvalidez condicionInvalidez = consultarDatosCondicion(idPersona);
            if(condicionInvalidez!=null) {
            	condicionInvalidezModeloDTO = new CondicionInvalidezModeloDTO();
            	condicionInvalidezModeloDTO.convertToDTO(condicionInvalidez);
            }
            return condicionInvalidezModeloDTO;
		}catch (Exception e) {
            logger.debug("Finaliza consultarCondicionInvalidezVigentePersona(Long idPersona)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    
	}
	@Override
    public List<CondicionInvalidez> consultarCondicionInvalidezConyugeCuidador(TipoIdentificacionEnum tipoIdentificacionConyuge, String numeroIdentificacionConyuge) {
        logger.debug("Inicia consultarCondicionInvalidezVigentePersona(Long idPersona)");
        List<CondicionInvalidez> condicionInvalidez = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICION_INVALIDEZ_ID_PERSONA_CONYUGE,
                        CondicionInvalidez.class)
                .setParameter("tipoIdentificacionConyuge", tipoIdentificacionConyuge)
                .setParameter("numeroIdentificacionConyuge", numeroIdentificacionConyuge).getResultList();
        return condicionInvalidez;

    }
	
	@Override
	public Boolean beneficiarioActivoAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado,
	        TipoIdentificacionEnum tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario) {
	    logger.debug("Inicia beneficiarioActivoAfiliado(TipoIdentificacionEnum, String, TipoIdentificacionEnum, String)");
	    
	    try {
            List<Beneficiario> beneficiario = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_ACTIVO_AFILIADO)
                    .setParameter("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado)
                    .setParameter("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
                    .setParameter("tipoIdentificacionBeneficiario", tipoIdentificacionBeneficiario)
                    .setParameter("numeroIdentificacionBeneficiario", numeroIdentificacionBeneficiario)
                    .setParameter("estado", EstadoAfiliadoEnum.ACTIVO)
                    .getResultList(); 
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Finaliza beneficiarioActivoAfiliado(TipoIdentificacionEnum, String, TipoIdentificacionEnum, String");
            return Boolean.FALSE;
        }
	    
	    logger.debug("Finaliza beneficiarioActivoAfiliado(TipoIdentificacionEnum, String, TipoIdentificacionEnum, String");
	    return Boolean.TRUE;
	    
	}
	

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadoAfiliadoEnum consultarEstadoRolAfiliadoConEmpleador(ActivacionAfiliadoDTO datosAfiliado) {
		logger.debug(
				"Inicia operacin consultarEstadoRolAfiliadoConEmpleador()");
		RolAfiliado rolAfiliado;
		
		try {
			if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(datosAfiliado.getTipoAfiliado())) {
				rolAfiliado = (RolAfiliado) entityManager
						.createNamedQuery(
								NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_DE_ID_DEL_AFI_Y_EMPLEDOR)
						.setParameter("tipoIdAfiliado", datosAfiliado.getTipoIdAfiliado())
						.setParameter("numeroIdAfiliado", datosAfiliado.getNumeroIdAfiliado())
						.setParameter("tipoIdEmpleador", datosAfiliado.getTipoIdAportante())
						.setParameter("numeroIdEmpleador", datosAfiliado.getNumeroIdAportante()).getSingleResult();
			} else {
				rolAfiliado = (RolAfiliado) entityManager
						.createNamedQuery(
								NamedQueriesConstants.BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO)
						.setParameter("tipoIdentificacion", datosAfiliado.getTipoIdAfiliado())
						.setParameter("numeroIdentificacion", datosAfiliado.getNumeroIdAfiliado())
						.setParameter("tipoAfiliado", datosAfiliado.getTipoAfiliado()).getSingleResult();
			}
			
			if(rolAfiliado != null) {
				return rolAfiliado.getEstadoAfiliado();
			}else {
				return null;
			}
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error(
					"Finaliza operacin consultarEstadoRolAfiliadoConEmpleador()",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}
	@Override
	public Long consultarRolAfiliadoEmpresa(TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado,
		TipoIdentificacionEnum tipoIdentificacionEmpresa,String numeroIdentificacionEmpresa){
			logger.info(tipoIdentificacionAfiliado+" "+numeroIdentificacionAfiliado+" "+tipoIdentificacionEmpresa+" "+numeroIdentificacionEmpresa);
			try {
				BigInteger  rolAfiliado = (BigInteger) entityManager
							.createNamedQuery(
									NamedQueriesConstants.CONSULTAR_ROLAFILIADO_POR_EMPRESA)
							.setParameter("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado.name())
							.setParameter("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
							.setParameter("tipoIdentificacionEmpresa", tipoIdentificacionEmpresa.name())
							.setParameter("numeroIdentificacionEmpresa", numeroIdentificacionEmpresa)
							.getSingleResult();
				return rolAfiliado.longValue();
				
			} catch (NoResultException e) {
				return null;
			}
		
	}
	public List<String> consultarListaResguardo(){
		try {
			List<String> resguardos = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_RESGUARDO, String.class).getResultList();
			return resguardos;
			
		} catch (NoResultException e) {
			return null;
		}
	}
       
        /**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.afiliados.service.AfiliadosService#ActualizarBeneficiariomasivamente(java.lang.Long,
	 *     java.lang.Long)
	 */
	@Override
	public void actualizarBeneficiariomasivamente(Long idGrupoFamiliar, Long idAfiliado) {
		try {
			logger.info("Inicia ActualizarBeneficiariomasivamente(idGrupoFamiliar)" + idGrupoFamiliar);
			logger.info("Inicia ActualizarBeneficiariomasivamente(idAfiliado)" + idAfiliado);
			Beneficiario Beneficiario = entityManager
					.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_AFILIADO_MASIVAMENTE, Beneficiario.class)
					.setParameter("idGrupoFamiliar", idGrupoFamiliar)
					.setParameter("idAfiliado", idAfiliado)
					.getSingleResult();

		}
		catch (Exception e) {
			logger.error("No es posible realizar la actualizacion del beneficiario", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
        
        /**
	 * (non-Javadoc)
	 * @see com.asopagos.afiliados.service.AfiliadosService#actualizarBeneficiarioMasivo(com.asopagos.dto.BeneficiarioDTO)
	 */
	@Override
	public void actualizarBeneficiarioMasivo(BeneficiarioDTO beneficiario) {
		ActualizarBeneficiarioSimpleRutine a = new ActualizarBeneficiarioSimpleRutine();
		a.actualizarBeneficiarioMasivo(beneficiario, entityManager);
	}


	public List<String> consultarListaPuebloIndigena(){
		try {
			List<String> puebloIndigenas = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTADO_PUEBLO_INDIGENA,String.class).getResultList();
			return puebloIndigenas;
			
		} catch (NoResultException e) {
			return null;
		}
	}

		
	@Override
	public List<RolafiliadoNovedadAutomaticaDTO> consultarExVeteranos(){
			try{
					List<RolafiliadoNovedadAutomaticaDTO> roles = (List<RolafiliadoNovedadAutomaticaDTO>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_AFLIADO_VETERANIA_FINALIZADA).getResultList();
					return roles;
			}catch(NoResultException e){
					return null;
			}catch(Exception e){
					logger.info("Finaliza consultarExVeterano",e);
					throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
	}
	@Override
	public void actualizarAfiliadosExVeteranos(List<Long> rolesA){
			try{
					List<RolAfiliadoModeloDTO> roles = new ArrayList<>();
					for(Long rolA : rolesA){
							RolAfiliadoModeloDTO rol = new RolAfiliadoModeloDTO();
							rol.setIdRolAfiliado(rolA);
							rol.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
							roles.add(rol);
					}
					actualizarRolesAfiliado(roles);
			}catch(Exception e){
					logger.info("Hubo un fallo al consultar los afiliados ex veteranos");
					logger.info("Finaliza ActualizarAfiliadosExVeteranos",e);
					throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
	}

	
	@Override 
	public int calcularEdad(Long edad){
		Date fecha = new Date(edad);
		return CalendarUtils.calcularEdadAnios(fecha);
	}

	@Override
	public Map<String,String> consultarArchivoAfiliacion(Long idSolicitud) {

		Map<String, String> res = new HashMap<>();
		res.put("formulario", "");
		List<Object> formulario = entityManager.createNamedQuery(
			NamedQueriesConstants.CONSULTA_FORMULARIO_AFILIACION
		)
		.setParameter("idSolicitud", idSolicitud)
		.getResultList();

		if (formulario != null && !formulario.isEmpty()){
			res.put("formulario", formulario.get(0).toString());
		}
		return res;
	}
	
	/**
	 * Nuevo WEB service
	 */
	@Override
	public InformacionPersonaCompletaDTO ObtenerInfoPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.info("Inicia servicio obtenerInfoPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");

		InformacionPersonaCompletaDTO resultado = new InformacionPersonaCompletaDTO();
		List<InformacionPersonaEmpleadorDTO> listaEmpleadoresDTO = new ArrayList<>();
		List<InformacionPersonaDTO> personaInfo = new ArrayList<>();
		InformacionPersonaEmpleadorDTO empleadorDTO = null;

		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

		try {
			StoredProcedureQuery query = entityManager
				.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_INFO_PERSONA);
			query.setParameter("tipoIdentificacion", tipoIdentificacion.name());
			query.setParameter("numeroIdentificacion", numeroIdentificacion);
			query.execute();

			List<Object[]> personas =  query.getResultList();
			if (personas.isEmpty()) {
				return null;
			}

			for(Object[] objeto: personas){
				InformacionPersonaDTO info = new InformacionPersonaDTO();
				logger.info(objeto[0].toString());
				info.setTipoAfiliacion(objeto[0] != null ? objeto[0].toString() : "");
				info.setClaseIndependiente(objeto[1] != null ? ClaseIndependienteEnum.valueOf(objeto[1].toString()) : null);
				info.setClaseTrabajador(objeto[2] != null ? ClaseTrabajadorEnum.valueOf(objeto[2].toString()) : null);
				info.setAfiliadoPrincipal(objeto[3] != null ? objeto[3].toString() : "");
				info.setTipoIdentificacion(objeto[4] != null ? TipoIdentificacionEnum.valueOf(objeto[4].toString()) : null);
				info.setNumeroIdentificacion(objeto[5] != null ? objeto[5].toString() : "");
				info.setNombreCompleto(objeto[6] != null ? objeto[6].toString() : "");
				if (objeto[7] != null) {
					Date fechaNacimiento = (Date) objeto[7];
					info.setFechaNacimiento(sdf.format(fechaNacimiento)); 
				}else{
					info.setFechaNacimiento("");
				}
				info.setEdad(objeto[8] != null ? Integer.valueOf(objeto[8].toString()) : null);
				if (objeto[9] != null) {
					Date fechaFallecimiento = (Date) objeto[9];
					info.setFechaFallecimiento(sdf.format(fechaFallecimiento));
				}else{
					info.setFechaFallecimiento("");
				}
				info.setEstadoCivil(objeto[10] != null ? EstadoCivilEnum.valueOf(objeto[10].toString()) : null);
				info.setGenero(objeto[11] != null ? GeneroEnum.valueOf(objeto[11].toString()) : null);
				info.setCantidadHijos(objeto[12] != null ? Integer.valueOf(objeto[12].toString()) : null);
				info.setCondicionInvalidez(objeto[13] != null ? objeto[13].toString() : "");
				info.setDireccionRecidencia(objeto[14] != null ? objeto[14].toString() : "");
				info.setHabitaCasaPropia(objeto[15] != null ? objeto[15].toString() : "");
				info.setCodigoMunicipio(objeto[16] != null ? Long.valueOf(objeto[16].toString()) : null);
				info.setNombreMunicipio(objeto[17] != null ? objeto[17].toString() : "");
				info.setCodigoDepartamento(objeto[18] != null ? Long.valueOf(objeto[18].toString()) : null);
				info.setNombreDepartamento(objeto[19] != null ? objeto[19].toString() : "");
				info.setCodigoPostal(objeto[20] != null ? objeto[20].toString() : "");
				info.setIndicativoYTelFijo(objeto[21] != null ? objeto[21].toString() : "");
				info.setCelular(objeto[22] != null ? objeto[22].toString() : "");
				info.setCorreoElectronico(objeto[23] != null ? objeto[23].toString() : "");
				info.setAutorizacionEnvioEmail(objeto[24] != null ? objeto[24].toString() : "");
				info.setAutorizaUsoDatosPersonales(objeto[25] != null ? objeto[25].toString() : "");
				info.setCodigoCCF(objeto[26] != null ? objeto[26].toString() : "");
				info.setCategoria(objeto[27] != null ? CategoriaPersonaEnum.valueOf(objeto[27].toString()) : null);
				info.setClasificacion(objeto[28] != null ? ClasificacionEnum.valueOf(objeto[28].toString()) : null);
				info.setEstadoAfiliado(objeto[29] != null ? EstadoAfiliadoEnum.valueOf(objeto[29].toString()) : null);
				if (objeto[30] != null) {
					Date fechaAfiliacionCCF = (Date) objeto[30];
					info.setFechaAfiliacionCCF(sdf.format(fechaAfiliacionCCF));
				}else{
					info.setFechaAfiliacionCCF("");
				}
				if (objeto[31] != null) {
					Date ultimoPagoCuotaMonetaria = (Date) objeto[31];
					info.setUltimoPagoCuotaMonetaria(sdf.format(ultimoPagoCuotaMonetaria));
				}else{
					info.setUltimoPagoCuotaMonetaria("");
				}
				info.setSaldoDescuentoPorCuotaMonetaria(objeto[32] != null ? new BigDecimal(objeto[32].toString()) : null);
				info.setCodigoEntidadDescuento(objeto[33] != null ? objeto[33].toString() : "");
				info.setNombreEntidadDescuento(objeto[34] != null ? objeto[34].toString() : "");
				if (objeto[35] != null) {
					Date fechaExpedicionDocumento = (Date) objeto[35];
					info.setFechaExpedicionDocumento(sdf.format(fechaExpedicionDocumento));
				}else{
					info.setFechaExpedicionDocumento("");
				}
				info.setNivelEducativo(objeto[36] != null ? NivelEducativoEnum.valueOf(objeto[36].toString()) : null);
				info.setGradoAcademico(objeto[37] != null ? objeto[37].toString() : "");
				info.setTipoIdentificacionEmpleador(objeto[38] != null ? TipoIdentificacionEnum.valueOf(objeto[38].toString()) : null);
				info.setNumeroIdentificacionEmpleador(objeto[39] != null ? objeto[39].toString() : "");
				info.setDigitoVerificacion(objeto[40] != null ? Short.valueOf(objeto[40].toString()) : null);
				info.setNombreEmpleador(objeto[41] != null ? objeto[41].toString() : "");
				info.setSucursalEmpleador(objeto[42] != null ? objeto[42].toString() : "");
				info.setNombreSucursalEmpleador(objeto[43] != null ? objeto[43].toString() : "");
				if (objeto[44] != null) {
					Date fechaIngresoEmpresa = (Date) objeto[44];
					info.setFechaIngresoEmpresa(sdf.format(fechaIngresoEmpresa));
				}else{
					info.setFechaIngresoEmpresa("");
				}
				info.setSalario(objeto[45] != null ? new BigDecimal(objeto[45].toString()) : null);
				info.setPorcentajeAporte(objeto[46] != null ? new BigDecimal(objeto[46].toString()) : null);
				info.setCargo(objeto[47] != null ? objeto[47].toString() : "");
				if (objeto[48] != null) {
					Date fechaRetiro = (Date) objeto[48];
					info.setFechaRetiro(sdf.format(fechaRetiro));
				}else{
					info.setFechaRetiro("");
				}
				info.setMotivoDesafiliacion(objeto[49] != null ? objeto[49].toString() : "");
				info.setUltimoPeriodoPagoAportes(objeto[50] != null ? objeto[50].toString() : "");
				info.setNitEmpresaAnterior(objeto[51] != null ? objeto[51].toString() : "");
				info.setNombreEmpresaAnterior(objeto[52] != null ? objeto[52].toString() : "");
				if (objeto[53] != null) {
					Date setFechaIngresoEmpresaAnterior = (Date) objeto[53];
					info.setFechaIngresoEmpresaAnterior(sdf.format(setFechaIngresoEmpresaAnterior)); 
				}else{
					info.setFechaIngresoEmpresaAnterior("");
				}if (objeto[54] != null) {
					Date setFechaRetiroEmpresaAnterior = (Date) objeto[54];
					info.setFechaRetiroEmpresaAnterior(sdf.format(setFechaRetiroEmpresaAnterior));
				}else{
					info.setFechaRetiroEmpresaAnterior("");
				}
				personaInfo.add(info);
			}
			// Siempre es la misma persona, pero el empleador puede ser diferente
			for (InformacionPersonaDTO persona: personaInfo){
				empleadorDTO = new InformacionPersonaEmpleadorDTO();
				empleadorDTO.setTipoIdentificacionEmpleador(persona.getTipoIdentificacionEmpleador());
				empleadorDTO.setNumeroIdentificacionEmpleador(persona.getNumeroIdentificacionEmpleador());
				empleadorDTO.setDigitoVerificacion(persona.getDigitoVerificacion());
				empleadorDTO.setNombreEmpleador(persona.getNombreEmpleador());
				empleadorDTO.setSucursalEmpleador(persona.getSucursalEmpleador());
				empleadorDTO.setNombreSucursalEmpleador(persona.getNombreSucursalEmpleador());
				empleadorDTO.setFechaIngresoEmpresa(persona.getFechaIngresoEmpresa());
				empleadorDTO.setSalario(persona.getSalario());
				empleadorDTO.setPorcentajeAporte(persona.getPorcentajeAporte());
				empleadorDTO.setCargo(persona.getCargo());
				empleadorDTO.setFechaRetiro(persona.getFechaRetiro());
				empleadorDTO.setMotivoDesafiliacion(persona.getMotivoDesafiliacion());
				empleadorDTO.setUltimoPeriodoPagoAportes(persona.getUltimoPeriodoPagoAportes());
				listaEmpleadoresDTO.add(empleadorDTO);
				persona.setTipoIdentificacionEmpleador(null);
				persona.setNumeroIdentificacionEmpleador(null);
				persona.setDigitoVerificacion(null);
				persona.setNombreEmpleador(null);
				persona.setSucursalEmpleador(null);
				persona.setNombreSucursalEmpleador(null);
				persona.setFechaIngresoEmpresa(null);
				persona.setSalario(null);
				persona.setPorcentajeAporte(null);
				persona.setCargo(null);
				persona.setFechaRetiro(null);
				persona.setMotivoDesafiliacion(null);
				persona.setUltimoPeriodoPagoAportes(null);
			}

			resultado.setInformacionPersona(personaInfo.get(0));
			resultado.setInformacionEmpleadores(listaEmpleadoresDTO);

			}catch(NoResultException e){
					return null;
			}catch(Exception e){
					logger.info("Finaliza ObtenerInfoPersona",e);
					throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			}
		return resultado;
	}
		
	/**
	 * Nuevo WEB service
	 */
	@Override
	public InformacionBeneficiarioCompletaDTO obtenerInfoBeneficiario(ConsultaBeneficiarioRequestDTO request) {	
    logger.debug("Inicia servicio obtenerInfoBeneficiario(...)");

    List<InformacionBeneficiarioDTO> beneficiarioInfo = new ArrayList<>();
    InformacionBeneficiarioCompletaDTO resultado = new InformacionBeneficiarioCompletaDTO();

    final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

    try {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_INFO_BENEFICIARIO);
        query.setParameter("tipoIdentificacion", request.getTipoIdentificacion().name());
        query.setParameter("numeroIdentificacion", request.getNumeroIdentificacionBeneficiario() != null ? request.getNumeroIdentificacionBeneficiario() : "");
        query.setParameter("numeroIdentificacionAfiliado", request.getNumeroIdentificacionAfiliado() != null ? request.getNumeroIdentificacionAfiliado() : "");
        query.execute();

        List<Object[]> beneficiarios = query.getResultList();
        if (beneficiarios.isEmpty()) {
            return null;
        }

        for (Object[] objeto : beneficiarios) {
            InformacionBeneficiarioDTO info = new InformacionBeneficiarioDTO();

            info.setTipoBeneficiario(objeto[0] != null ? objeto[0].toString() : "");
            info.setTipoIdentifiacion(objeto[1] != null ? TipoIdentificacionEnum.valueOf(objeto[1].toString()) : null);
            info.setNumeroIdentifacion(objeto[2] != null ? objeto[2].toString() : "");
            info.setPrimerNombre(objeto[3] != null ? objeto[3].toString() : "");
            info.setSegundoNombre(objeto[4] != null ? objeto[4].toString() : "");
            info.setPrimerApellido(objeto[5] != null ? objeto[5].toString() : "");
            info.setSegundoApellido(objeto[6] != null ? objeto[6].toString() : "");
            info.setNombreCompleto(objeto[7] != null ? objeto[7].toString() : "");
            info.setFechaNacimiento(objeto[8] != null ? sdf.format((Date) objeto[8]) : "");
            info.setEdad(objeto[9] != null ? Integer.valueOf(objeto[9].toString()) : null);
            info.setFechaFallecimiento(objeto[10] != null ? sdf.format((Date) objeto[10]) : "");
            info.setEstadoCivil(objeto[11] != null ? EstadoCivilEnum.valueOf(objeto[11].toString()) : null);
            info.setGenero(objeto[12] != null ? GeneroEnum.valueOf(objeto[12].toString()) : null);
            info.setDireccionRecidencia(objeto[13] != null ? objeto[13].toString() : "");
            info.setCodigoMunicipio(objeto[14] != null ? Long.valueOf(objeto[14].toString()) : null);
            info.setNombreMunicipio(objeto[15] != null ? objeto[15].toString() : "");
            info.setCodigoDepartamento(objeto[16] != null ? Long.valueOf(objeto[16].toString()) : null);
            info.setNombreDepartamento(objeto[17] != null ? objeto[17].toString() : "");
            info.setCodigoPostal(objeto[18] != null ? objeto[18].toString() : "");
            info.setIndicativoYTelFijo(objeto[19] != null ? objeto[19].toString() : "");
            info.setCelular(objeto[20] != null ? objeto[20].toString() : "");
            info.setCorreoElectronico(objeto[21] != null ? objeto[21].toString() : "");
            info.setEstadoAfiliado(objeto[22] != null ? EstadoAfiliadoEnum.valueOf(objeto[22].toString()) : null);
            info.setFechaRetiro(objeto[23] != null ? sdf.format((Date) objeto[23]) : "");
            info.setMotivoDesafiliacion(objeto[24] != null ? objeto[24].toString() : "");
            info.setUltimpoPagoCuotaMonetaria(objeto[25] != null ? objeto[25].toString() : "");
            info.setCondicionInvalidez(objeto[26] != null ? objeto[26].toString() : "");
            info.setEstudianteTrabajoDesarrolloHumano(objeto[27] != null ? objeto[27].toString() : "");
            info.setDebePresentarEscolaridad(objeto[28] != null ? objeto[28].toString() : "");
            info.setFechaFinVigencia(objeto[29] != null ? sdf.format((Date) objeto[29]) : "");
            info.setEstadoCertificadoEscolaridad(objeto[30] != null ? objeto[30].toString() : "");
            info.setGradoEscolar(objeto[31] != null ? objeto[31].toString() : "");
            info.setFechaRecepcion(objeto[32] != null ? sdf.format((Date) objeto[32]) : "");

            info.setTipoIdentificacionAfiliado(objeto[33] != null ? TipoIdentificacionEnum.valueOf(objeto[33].toString()) : null);
            info.setNumeroIdentificacionAfiliado(objeto[34] != null ? objeto[34].toString() : "");
            info.setPrimerNombreAfiliado(objeto[35] != null ? objeto[35].toString() : "");
            info.setSegundoNombreAfiliado(objeto[36] != null ? objeto[36].toString() : "");
            info.setPrimerApellidoAfiliado(objeto[37] != null ? objeto[37].toString() : "");
            info.setSegundoApellidoAfiliado(objeto[38] != null ? objeto[38].toString() : "");
            info.setCategoria(objeto[39] != null ? CategoriaPersonaEnum.valueOf(objeto[39].toString()) : CategoriaPersonaEnum.SIN_CATEGORIA);

            beneficiarioInfo.add(info);
        }

        boolean esConsultaPorAfiliado = request.getNumeroIdentificacionAfiliado() != null && !request.getNumeroIdentificacionAfiliado().isEmpty()
                && (request.getNumeroIdentificacionBeneficiario() == null || request.getNumeroIdentificacionBeneficiario().isEmpty());

        if (esConsultaPorAfiliado) {
            resultado.setInformacionBeneficiario(beneficiarioInfo);

            InformacionBeneficiarioDTO primero = beneficiarioInfo.get(0);
            InformacionBeneficiarioAfiliadoDTO afiliadoDTO = new InformacionBeneficiarioAfiliadoDTO();
            afiliadoDTO.setTipoIdentificacionAfiliado(primero.getTipoIdentificacionAfiliado());
            afiliadoDTO.setNumeroIdentificacionAfiliado(primero.getNumeroIdentificacionAfiliado());
            afiliadoDTO.setPrimerNombreAfiliado(primero.getPrimerNombreAfiliado());
            afiliadoDTO.setSegundoNombreAfiliado(primero.getSegundoNombreAfiliado());
            afiliadoDTO.setPrimerApellidoAfiliado(primero.getPrimerApellidoAfiliado());
            afiliadoDTO.setSegundoApellidoAfiliado(primero.getSegundoApellidoAfiliado());
            afiliadoDTO.setCategoria(primero.getCategoria());

            resultado.setInformacionBeneficiarioAfiliado(Collections.singletonList(afiliadoDTO));

        } else {
            InformacionBeneficiarioDTO beneficiario = beneficiarioInfo.get(0);
            resultado.setInformacionBeneficiario(Collections.singletonList(beneficiario));

            Map<String, InformacionBeneficiarioAfiliadoDTO> afiliadosUnicos = new LinkedHashMap<>();
            for (InformacionBeneficiarioDTO b : beneficiarioInfo) {
                String clave = (b.getTipoIdentificacionAfiliado() != null ? b.getTipoIdentificacionAfiliado().name() : "") +
                        "_" + (b.getNumeroIdentificacionAfiliado() != null ? b.getNumeroIdentificacionAfiliado() : "");

                if (!clave.trim().equals("_") && !afiliadosUnicos.containsKey(clave)) {
                    InformacionBeneficiarioAfiliadoDTO afiliadoDTO = new InformacionBeneficiarioAfiliadoDTO();
                    afiliadoDTO.setTipoIdentificacionAfiliado(b.getTipoIdentificacionAfiliado());
                    afiliadoDTO.setNumeroIdentificacionAfiliado(b.getNumeroIdentificacionAfiliado());
                    afiliadoDTO.setPrimerNombreAfiliado(b.getPrimerNombreAfiliado());
                    afiliadoDTO.setSegundoNombreAfiliado(b.getSegundoNombreAfiliado());
                    afiliadoDTO.setPrimerApellidoAfiliado(b.getPrimerApellidoAfiliado());
                    afiliadoDTO.setSegundoApellidoAfiliado(b.getSegundoApellidoAfiliado());
                    afiliadoDTO.setCategoria(b.getCategoria() != null ? b.getCategoria() : CategoriaPersonaEnum.SIN_CATEGORIA);
                    afiliadosUnicos.put(clave, afiliadoDTO);
                }
            }
            resultado.setInformacionBeneficiarioAfiliado(new ArrayList<>(afiliadosUnicos.values()));
        }

        for (InformacionBeneficiarioDTO b : beneficiarioInfo) {
            b.setTipoIdentificacionAfiliado(null);
            b.setNumeroIdentificacionAfiliado(null);
            b.setPrimerNombreAfiliado(null);
            b.setSegundoNombreAfiliado(null);
            b.setPrimerApellidoAfiliado(null);
            b.setSegundoApellidoAfiliado(null);
            b.setCategoria(null);
        }

    } catch (NoResultException e) {
        return null;
    } catch (Exception e) {
        logger.info("Finaliza obtenerInfoBeneficiario con error", e);
        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
    }

    return resultado;
	}
// =============== MASIVO TRANSFERENCIA

	@Override
	public List<Long> consultarIdsGruposFamiliaresCargueMasivoTransferencia(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion){

		logger.info("inicia public List<Long> consultarIdsGruposFamiliaresCargueMasivoTransferencia(TipoIdentificacionEnum "+tipoIdentificacion+",String"+ numeroIdentificacion+")");
		try{
			return (List<Long>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_IDS_CARGUE_MASIVO_TRANSFERENCIA)
					.setParameter("tipoIdentificacion",tipoIdentificacion.name())
					.setParameter("numeroIdentificacion",numeroIdentificacion)
					.getResultList();
		}catch(Exception e){
			logger.error("no se han encontrado grupos familiares con los parametros.");
			logger.warn("tipo identificacion afiliado: "+tipoIdentificacion);
			logger.warn("numero identificacion afiliado: "+numeroIdentificacion);
			e.printStackTrace();
			return new ArrayList<Long>();
		}
	}

	@Override
	public void correccionCategoriaAfiliadoBeneficiario(){

		logger.info("inicia public void correccionCategoria()");
		try{
			StoredProcedureQuery query = entityManager
				.createNamedStoredProcedureQuery(NamedQueriesConstants.CORRECCION_CATEGORIA_AFILIADO_BENEFICIARIO);
			query.execute();
		}catch(Exception e){
			logger.error("no se han encontrado grupos familiares con los parametros.");
			e.printStackTrace();
		}
	}

	@Override
	public List<BeneficiarioModeloDTO> consultarBeneficiariosPorIds(List<Long> idsBeneficiarios) {
		logger.debug("Inicia operacion consultarBeneficiariosPorIds(List<Long>)");
		List<BeneficiarioModeloDTO> beneficiariosDTO = new ArrayList<>();
		if (idsBeneficiarios == null || idsBeneficiarios.isEmpty()) {
			return beneficiariosDTO;
		}
		try {
			List<Beneficiario> beneficiarios = (List<Beneficiario>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_POR_IDS)
					.setParameter("idsBeneficiarios", idsBeneficiarios)
					.getResultList();

			for (Beneficiario beneficiario : beneficiarios) {
				if (beneficiario.getIdBeneficiario() != null) {
					PersonaDetalle personaDetalle = this.consultarDatosPersonaDetalle(beneficiario.getPersona().getIdPersona());
					CondicionInvalidez conInvalidez = this.consultarDatosCondicion(beneficiario.getPersona().getIdPersona());
					BeneficiarioDetalle beneDetalle = this.consultarDatosBeneficiarioDetalle(personaDetalle.getIdPersonaDetalle());
					CertificadoEscolarBeneficiario certificado = consultarCertificadoEscolarVigentePorIdPersona(beneficiario.getPersona().getIdPersona());
					BeneficiarioModeloDTO beneficiarioDTO = new BeneficiarioModeloDTO();
					beneficiarioDTO.convertToDTO(beneficiario, personaDetalle, conInvalidez, beneDetalle, certificado);
					beneficiariosDTO.add(beneficiarioDTO);
				}
			}
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarBeneficiarios(List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operacion consultarBeneficiarios(List<Long>)");
		return beneficiariosDTO;
	}
}
