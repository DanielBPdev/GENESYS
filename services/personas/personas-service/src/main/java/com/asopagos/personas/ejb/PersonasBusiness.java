package com.asopagos.personas.ejb;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.text.SimpleDateFormat;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.constants.QueriesConstants;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.dto.ComparacionFechasDefuncionYAporteDTO;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.Vista360PersonaAfiliadoPrincipalDTO;
import com.asopagos.dto.Vista360PersonaCabeceraDTO;
import com.asopagos.dto.modelo.AdministradorSubsidioModeloDTO;
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import com.asopagos.dto.modelo.HistoricoActivacionAccesoModeloDTO;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaDetalleModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.fovis.IntegranteHogar;
import com.asopagos.entidades.ccf.fovis.JefeHogar;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.BeneficiarioDetalle;
import com.asopagos.entidades.ccf.personas.CondicionEspecialPersona;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.entidades.ccf.personas.MedioEfectivo;
import com.asopagos.entidades.ccf.personas.MedioPagoPersona;
import com.asopagos.entidades.ccf.personas.MedioTarjeta;
import com.asopagos.entidades.ccf.personas.AdminSubsidioGrupo;
import com.asopagos.entidades.ccf.personas.MedioDePago;
import com.asopagos.entidades.ccf.personas.MedioTransferencia;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.SolicitudTarjetaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.personas.constants.NamedQueriesConstants;
import com.asopagos.personas.constants.QueryConstants;
import com.asopagos.personas.dto.ConsultaEstadoCajaPersonaDTO;
import com.asopagos.personas.dto.DetalleBeneficiarioGrupoFamiliarDTO;
import com.asopagos.personas.dto.Ubicacion360DTO;
import com.asopagos.personas.service.PersonasService;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.exception.ValidacionFallidaException;
import com.asopagos.rutine.personasrutines.actualizardatospersonarutine.ActualizarDatosPersonaRutine;
import com.asopagos.rutine.personasrutines.consultardatospersonarutine.CrearPersonaRutine;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import com.asopagos.util.Interpolator;
import com.asopagos.dto.ActualizarExclusionSumatoriaSalarioDTO;
import com.asopagos.entidades.ccf.personas.PersonaExclusionSumatoriaSalario;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.personas.dto.PaisDTO;
import com.asopagos.personas.dto.DetalleBeneficiarioDTO;
import com.asopagos.personas.dto.ConsultarPersonaProcesoOffcoreDTO;
import java.util.ResourceBundle;
import com.asopagos.dto.modelo.RegistroDespliegueAmbienteDTO;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import java.util.concurrent.CompletableFuture;
import javax.ejb.Asynchronous;
import com.asopagos.personas.clients.EjecutarSPCoreSubsidio;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de personas<br/>
 * <b>Módulo:</b> Asopagos - HU 104
 *
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
@Stateless
@SuppressWarnings({ "deprecation", "unchecked" })
public class PersonasBusiness implements PersonasService {

	@PersistenceContext(unitName = "personas_PU")
	private EntityManager entityManager;
	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(PersonasBusiness.class);


	static {
        String propiedades;
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
            propiedades = resourceBundle.getString("fecha.compilacion");
			System.out.println("Prueba propiedades exito: " + propiedades);

        } catch (MissingResourceException mre) {
            System.out.println("Prueba propiedades falló");
        } 
    }

	/**
	 * <b>Descripción</b>Metodo que se encarga validar el digito de verificación
	 * <code>idEmpleador, con este id se consultara el Empleador, listadoSucursales
	     * son las sucursales que se asignarán al empleador</code><br/>
	 *
	 * @param persona
	 *                Es la persona a la cual se le aplica la validación
	 * @return true si la validación es exitosa
	 *
	 */
	public Boolean validarDigitoVerificacion(Persona persona) {
		try {
			final int CERO = 0;

			if (persona.getNumeroIdentificacion() != null) {
				if (persona.getNumeroIdentificacion().equals(CERO)) {
					throw new ValidacionFallidaException("El Número Documento debe ser mayor que 0.");
				}
			} else {
				throw new ValidacionFallidaException("No se ingresó el número de documento");
			}

			if (persona.getDigitoVerificacion() == null) {
				throw new ValidacionFallidaException("No se ingresó el dígito de verificación");
			}

			if (persona.getTipoIdentificacion().equals(TipoIdentificacionEnum.NIT)) {
				String NIT;
				if (persona.getNumeroIdentificacion().length() == 7) {
					NIT = "00" + persona.getNumeroIdentificacion();
				} else {
					if (persona.getNumeroIdentificacion().length() == 8) {
						NIT = "0" + persona.getNumeroIdentificacion();
					} else {
						if (persona.getNumeroIdentificacion().length() == 9) {
							NIT = persona.getNumeroIdentificacion();
						} else {
							throw new ValidacionFallidaException("El NIT ingresado es incorrecto.");
						}
					}
				}
				return validarNit(NIT, persona.getDigitoVerificacion());
			} else {
				throw new ValidacionFallidaException("El tipo de documento ingresado es incorrecto.");
			}
		} catch (Exception ex) {
			if (ex instanceof ValidacionFallidaException) {
				throw ex;
			} else {
				logger.error("Finaliza validarDigitoVerificacion, Error no esperado", ex);
				throw new TechnicalException("Error no esperado", ex);
			}
		}
	}

	private Boolean validarNit(String nit, Short digito) {
		String ceros = "000000";
		int[] liPeso = new int[15];
		liPeso[0] = 71;
		liPeso[1] = 67;
		liPeso[2] = 59;
		liPeso[3] = 53;
		liPeso[4] = 47;
		liPeso[5] = 43;
		liPeso[6] = 41;
		liPeso[7] = 37;
		liPeso[8] = 29;
		liPeso[9] = 23;
		liPeso[10] = 19;
		liPeso[11] = 17;
		liPeso[12] = 13;
		liPeso[13] = 7;
		liPeso[14] = 3;

		String lsStrNit = ceros + nit;
		int liSuma = 0;
		int ls = 0;
		for (int i = 0; i < 15; i++) {
			ls = Integer.parseInt(lsStrNit.charAt(i) + "");
			liSuma += ls * liPeso[i];
		}
		int digitoChequeo = liSuma % 11;
		if (digitoChequeo >= 2) {
			digitoChequeo = 11 - digitoChequeo;
		}
		if (digito != digitoChequeo) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * @Override
	 * public List<PersonaDTO> buscarPersonas(TipoIdentificacionEnum valorTI, String
	 * valorNI, String primerNombre,
	 * String primerApellido, Long fechaNacimiento, Long idEmpleador, String
	 * segundoNombre,
	 * String segundoApellido, Boolean esVista360Web) {
	 * try {
	 * logger.debug("Inicia buscarPersonas(valorTI = " + valorTI +
	 * ", valorNI = "+valorNI+", primerNombre ="+primerNombre+" ,"
	 * + "primerApellido = "+primerApellido+" , fechaNacimiento = "
	 * +fechaNacimiento+", idEmpleador ="+idEmpleador+""
	 * + ", segundoNombre = "+segundoNombre+", segundoApellido="+segundoApellido+
	 * ",esVista360Web="+esVista360Web+")");
	 * 
	 * //se agrega validación (esVista360Web) para resolución del mantis 245754
	 * if (esVista360Web != null && esVista360Web && valorTI != null && valorNI !=
	 * null && !valorNI.isEmpty() && idEmpleador != null) {
	 * // 1) Se buscan coincidencias por tipo y numero documento
	 * logger.info("entro");
	 * List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
	 * List<PersonaDTO> personaDetalle = new ArrayList<PersonaDTO>();
	 * 
	 * personas = entityManager
	 * .createNamedQuery(NamedQueriesConstants.
	 * CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_VISTA_360_EMPLEADOR,
	 * PersonaDTO.class)
	 * .setParameter("tipoIdentificacion",
	 * valorTI).setParameter("numeroIdentificacion", valorNI)
	 * .setParameter("idEmpleador", idEmpleador)
	 * .getResultList();
	 * 
	 * 
	 * if(personas!=null && !personas.isEmpty()&&(personas.size()<2)){
	 * personas.get(0).setPrecargar(Boolean.TRUE);
	 * }
	 * try {
	 * personaDetalle = entityManager
	 * .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE,
	 * PersonaDTO.class)
	 * .setParameter("tipoIdentificacion",
	 * valorTI).setParameter("numeroIdentificacion", valorNI)
	 * .getResultList();
	 * if (personas != null && !personas.isEmpty()) {
	 * if (personaDetalle != null && !personaDetalle.isEmpty()) {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * if(personaDetalle!=null &&
	 * !personaDetalle.isEmpty()&&(personaDetalle.size()<2)){
	 * personaDetalle.get(0).setPrecargar(Boolean.TRUE);
	 * }
	 * return convertirADTO(personaDetalle, idEmpleador);
	 * } else {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personas, idEmpleador);
	 * }
	 * }
	 * else{
	 * //si no encontró ninguna relación entre el afiliado y el empleador no retorna
	 * nada.
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return null;
	 * }
	 * } catch (NoResultException e) {
	 * if (personas != null && !personas.isEmpty()) {
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personas, idEmpleador);
	 * }
	 * }
	 * }
	 * 
	 * if (valorTI != null && valorNI != null && !valorNI.isEmpty()) {
	 * // 1) Se buscan coincidencias por tipo y numero documento
	 * 
	 * List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
	 * List<PersonaDTO> personaDetalle = new ArrayList<PersonaDTO>();
	 * 
	 * personas = entityManager
	 * .createNamedQuery(NamedQueriesConstants.
	 * CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION,
	 * PersonaDTO.class)
	 * .setParameter("tipoIdentificacion",
	 * valorTI).setParameter("numeroIdentificacion", valorNI)
	 * .getResultList();
	 * 
	 * if(personas!=null && !personas.isEmpty()&&(personas.size()<2)){
	 * personas.get(0).setPrecargar(Boolean.TRUE);
	 * }
	 * try {
	 * personaDetalle = entityManager
	 * .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE,
	 * PersonaDTO.class)
	 * .setParameter("tipoIdentificacion",
	 * valorTI).setParameter("numeroIdentificacion", valorNI)
	 * .getResultList();
	 * if (personas != null && !personas.isEmpty()) {
	 * if (personaDetalle != null && !personaDetalle.isEmpty()) {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * if(personaDetalle!=null &&
	 * !personaDetalle.isEmpty()&&(personaDetalle.size()<2)){
	 * personaDetalle.get(0).setPrecargar(Boolean.TRUE);
	 * }
	 * return convertirADTO(personaDetalle, idEmpleador);
	 * } else {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personas, idEmpleador);
	 * }
	 * }
	 * } catch (NoResultException e) {
	 * if (personas != null && !personas.isEmpty()) {
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personas, idEmpleador);
	 * }
	 * }
	 * }
	 * 
	 * if (valorNI != null && !valorNI.isEmpty()) {
	 * // 2) Se buscan coincidencias por numero documento
	 * 
	 * List<PersonaDTO> personas = entityManager
	 * .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUM_IDENTIFICACION)
	 * .setParameter("numeroIdentificacion", valorNI)
	 * .setParameter("tipoIdNIT", TipoIdentificacionEnum.NIT).getResultList();
	 * 
	 * List<PersonaDTO> personaDetalle = new ArrayList<PersonaDTO>();
	 * try {
	 * List<Object[]> lista = entityManager
	 * .createNamedQuery(NamedQueriesConstants.
	 * CONSULTAR_PERSONA_DETALLE_NUM_IDENTIFICACION)
	 * .setParameter("numeroIdentificacion", valorNI)
	 * .setParameter("tipoIdNIT",
	 * TipoIdentificacionEnum.NIT.name()).getResultList();
	 * if (lista != null && !lista.isEmpty()) {
	 * for (Object[] obj : lista) {
	 * PersonaDTO perso = new PersonaDTO((Persona)obj[0], (PersonaDetalle)obj[1]);
	 * personaDetalle.add(perso);
	 * }
	 * }
	 * 
	 * if (personas != null && !personas.isEmpty()) {
	 * if (personaDetalle != null && !personaDetalle.isEmpty()) {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personaDetalle, idEmpleador);
	 * } else {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personas, idEmpleador);
	 * }
	 * }
	 * } catch (NoResultException nre) {
	 * if (personas != null && !personas.isEmpty()) {
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personas, idEmpleador);
	 * }
	 * }
	 * }
	 * 
	 * if ((primerNombre != null && !primerNombre.isEmpty())
	 * || (primerApellido != null && !primerApellido.isEmpty()) || fechaNacimiento
	 * != null
	 * || segundoNombre != null && !segundoNombre.isEmpty()
	 * || segundoApellido != null && !segundoApellido.isEmpty()) {
	 * // 3) Se buscan coincidencias primer nombre, primer apellido y
	 * // fecha
	 * // de
	 * // nacimiento ( si esta diligenciado )
	 * Map<String, String> fields = new HashMap<>();
	 * Map<String, Object> values = new HashMap<>();
	 * long cant = 0;
	 * if (primerNombre != null) {
	 * fields.put("primerNombre", ConsultasDinamicasConstants.IGUAL);
	 * values.put("primerNombre", primerNombre);
	 * cant++;
	 * }
	 * 
	 * if (primerApellido != null) {
	 * fields.put("primerApellido", ConsultasDinamicasConstants.IGUAL);
	 * values.put("primerApellido", primerApellido);
	 * cant++;
	 * }
	 * 
	 * if (segundoNombre != null) {
	 * fields.put("segundoNombre", ConsultasDinamicasConstants.IGUAL);
	 * values.put("segundoNombre", segundoNombre);
	 * cant++;
	 * }
	 * 
	 * if (segundoApellido != null) {
	 * fields.put("segundoApellido", ConsultasDinamicasConstants.IGUAL);
	 * values.put("segundoApellido", segundoApellido);
	 * cant++;
	 * }
	 * List<Persona> personasConsulta = new ArrayList<Persona>();
	 * if (cant > 0) {
	 * fields.put("tipoIdentificacion", ConsultasDinamicasConstants.DIFERENTE);
	 * values.put("tipoIdentificacion", TipoIdentificacionEnum.NIT);
	 * personasConsulta = (List<Persona>) JPAUtils.consultaEntidad(entityManager,
	 * Persona.class, fields,
	 * values);
	 * }
	 * 
	 * List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
	 * List<PersonaDTO> personaDetalle = new ArrayList<PersonaDTO>();
	 * 
	 * ArrayList<Long> listaIdsPersonas = new ArrayList<Long>();
	 * for (Persona persona : personasConsulta) {
	 * listaIdsPersonas.add(persona.getIdPersona());
	 * personas.add(new PersonaDTO(persona));
	 * }
	 * 
	 * try {
	 * if (!personas.isEmpty() && personas.size() > 0) {
	 * if (fechaNacimiento != null) {
	 * Date fecNac = CalendarUtils.truncarHora(new Date(fechaNacimiento));
	 * personaDetalle = entityManager
	 * .createNamedQuery(NamedQueriesConstants.
	 * CONSULTAR_PERSONA_POR_ID_FECHANACIMIENTO)
	 * .setParameter("idPersonas", listaIdsPersonas)
	 * .setParameter("fechaNacimiento", fecNac, TemporalType.DATE).getResultList();
	 * if (personaDetalle != null && !personaDetalle.isEmpty()) {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personaDetalle, idEmpleador);
	 * }
	 * } else {
	 * personaDetalle = entityManager
	 * .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_ID)
	 * .setParameter("idPersonas", listaIdsPersonas).getResultList();
	 * List<PersonaDTO> listaPersonas = new ArrayList<PersonaDTO>();
	 * for (PersonaDTO perDTO : personas) {
	 * boolean noExite = true;
	 * for (PersonaDTO perDtoDetalle : personaDetalle) {
	 * if (perDTO.getIdPersona().equals(perDtoDetalle.getIdPersona())) {
	 * noExite = false;
	 * listaPersonas.add(perDtoDetalle);
	 * break;
	 * }
	 * }
	 * if (noExite) {
	 * listaPersonas.add(perDTO);
	 * }
	 * }
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(listaPersonas, idEmpleador);
	 * }
	 * } else if (fechaNacimiento != null && cant == 0) {
	 * Date fecNac = CalendarUtils.truncarHora(new Date(fechaNacimiento));
	 * personaDetalle = entityManager
	 * .createNamedQuery(NamedQueriesConstants.
	 * CONSULTAR_PERSONA_POR_FECHANACIMIENTO)
	 * .setParameter("fechaNacimiento", fecNac, TemporalType.DATE).getResultList();
	 * if (personaDetalle != null && !personaDetalle.isEmpty()) {
	 * logger.debug(
	 * "Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personaDetalle, idEmpleador);
	 * }
	 * }
	 * } catch (NoResultException e) {
	 * if (personas != null && !personas.isEmpty()) {
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return convertirADTO(personas, idEmpleador);
	 * }
	 * }
	 * }
	 * 
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return null;
	 * } catch (NoResultException nre) {
	 * logger.error("No existen afiliados", nre);
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return null;
	 * } catch (NonUniqueResultException nure) {
	 * logger.error("Existe más de una definicion de Afiliados", nure);
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return null;
	 * } catch (Exception e) {
	 * logger.error("No es posible realizar la busqueda de Personas", e);
	 * logger.
	 * debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)"
	 * );
	 * return null;
	 * }
	 * }
	 * 
	 */

	public List<PersonaDTO> buscarPersonas(TipoIdentificacionEnum valorTI, String valorNI, String primerNombre,
			String primerApellido, Long fechaNacimiento, Long idEmpleador, String segundoNombre,
			String segundoApellido, Boolean esVista360Web) {

		logger.debug("Inicia buscarPersonasNew(valorTI = " + valorTI + ", valorNI = " + valorNI + ", primerNombre ="
				+ primerNombre + " ,"
				+ "primerApellido = " + primerApellido + " , fechaNacimiento = " + fechaNacimiento + ", idEmpleador ="
				+ idEmpleador + ""
				+ ", segundoNombre = " + segundoNombre + ", segundoApellido=" + segundoApellido + ",esVista360Web="
				+ esVista360Web + ")");

		// se agrega validación (esVista360Web) para resolución del mantis 245754
		if (esVista360Web != null && esVista360Web && valorTI != null && valorNI != null && !valorNI.isEmpty()
				&& idEmpleador != null) {
			// 1) Se buscan coincidencias por tipo y numero documento

			ArrayList<Object[]> personasObj = (ArrayList<Object[]>) entityManager
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_VISTA_360_EMPLEADOR_NATIVA)
					.setParameter("tipoIdentificacion", valorTI.name())
					.setParameter("numeroIdentificacion", valorNI)
					.setParameter("idEmpleador", idEmpleador)
					.getResultList();

			List<PersonaDTO> listaPersonas = new ArrayList<>();
			for (Object[] infoInh : personasObj) {
				PersonaDTO p = new PersonaDTO((Persona) infoInh[0], (PersonaDetalle) infoInh[1],
						(RolAfiliado) infoInh[2]);
				p.setEstadoAfiliadoCaja(
						infoInh[4] == null ? null : (EstadoAfiliadoEnum.valueOf(infoInh[4].toString())));
				p.setEstadoAfiliadoRol(
						p.getEstadoAfiliadoRol() == null ? p.getEstadoAfiliadoCaja() : p.getEstadoAfiliadoRol());
				p.setEstadoAfiliadoRol(
						p.getEstadoAfiliadoRol() == null ? EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION
								: p.getEstadoAfiliadoRol());
				p.setAfiliable(EstadoAfiliadoEnum.ACTIVO.equals(p.getEstadoAfiliadoRol()) ? false : true);
				listaPersonas.add(p);
			}

			if (listaPersonas != null && !listaPersonas.isEmpty() && (listaPersonas.size() < 2)) {
				listaPersonas.get(0).setPrecargar(Boolean.TRUE);
			}
			logger.debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)");
			return listaPersonas;
		}

		if (valorTI != null && valorNI != null && !valorNI.isEmpty()) {
			// 1) Se buscan coincidencias por tipo y numero documento
			List<PersonaDTO> listaPersonas = new ArrayList<>();
			if (idEmpleador == null) {
				logger.info("idEmpleador tipoYdoc");
				ArrayList<Object[]> personasObj = (ArrayList<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_NATIVA)
						.setParameter("tipoIdentificacion", valorTI.name())
						.setParameter("numeroIdentificacion", valorNI)
						.getResultList();

				for (Object[] infoInh : personasObj) {
					PersonaDTO p = new PersonaDTO((Persona) infoInh[0], (PersonaDetalle) infoInh[1]);
					p.setEstadoAfiliadoCaja((EstadoAfiliadoEnum.valueOf(infoInh[3].toString())));
					p.setAfiliable(true);
					listaPersonas.add(p);
				}
			} else {
				ArrayList<Object[]> personasObj = (ArrayList<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_EMP_NATIVA)
						.setParameter("tipoIdentificacion", valorTI.name())
						.setParameter("numeroIdentificacion", valorNI)
						.setParameter("idEmpleador", idEmpleador)
						.getResultList();

				for (Object[] infoInh : personasObj) {
					PersonaDTO p = new PersonaDTO((Persona) infoInh[0], (PersonaDetalle) infoInh[1],
							(RolAfiliado) infoInh[2]);
					p.setEstadoAfiliadoCaja(
							infoInh[3] == null ? null : (EstadoAfiliadoEnum.valueOf(infoInh[3].toString())));
					p.setEstadoAfiliadoRol(
							p.getEstadoAfiliadoRol() == null ? p.getEstadoAfiliadoCaja() : p.getEstadoAfiliadoRol());
					p.setEstadoAfiliadoRol(
							p.getEstadoAfiliadoRol() == null ? EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION
									: p.getEstadoAfiliadoRol());
					p.setAfiliable(!EstadoAfiliadoEnum.ACTIVO.equals(p.getEstadoAfiliadoRol()));
					listaPersonas.add(p);
					logger.debug("getEstadoAfiliadoRol()" + p.getEstadoAfiliadoRol());
				}
			}

			if (listaPersonas != null && !listaPersonas.isEmpty() && (listaPersonas.size() < 2)) {
				listaPersonas.get(0).setPrecargar(Boolean.TRUE);
			}
			logger.debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)");
			return listaPersonas;

		}

		if (valorNI != null && !valorNI.isEmpty()) {
			// 2) Se buscan coincidencias por numero documento
			List<PersonaDTO> listaPersonas = new ArrayList<>();
			if (idEmpleador == null) {
				logger.info("idEmpleador doc-----");
				ArrayList<Object[]> personasObj = (ArrayList<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUM_IDENTIFICACION_NATIVA)
						.setParameter("numeroIdentificacion", valorNI)
						.setParameter("tipoIdNIT", TipoIdentificacionEnum.NIT.name()).getResultList();

				for (Object[] infoInh : personasObj) {
					PersonaDTO p = new PersonaDTO((Persona) infoInh[0], (PersonaDetalle) infoInh[1]);
					p.setEstadoAfiliadoCaja((EstadoAfiliadoEnum.valueOf(infoInh[2].toString())));
					p.setAfiliable(true);
					listaPersonas.add(p);
				}
			} else {
				ArrayList<Object[]> personasObj = (ArrayList<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUM_IDENTIFICACION_EMP_NATIVA)
						.setParameter("numeroIdentificacion", valorNI)
						.setParameter("tipoIdNIT", TipoIdentificacionEnum.NIT.name())
						.setParameter("idEmpleador", idEmpleador)
						.getResultList();

				for (Object[] infoInh : personasObj) {
					PersonaDTO p = new PersonaDTO((Persona) infoInh[0], (PersonaDetalle) infoInh[1],
							(RolAfiliado) infoInh[2]);
					p.setEstadoAfiliadoCaja(
							infoInh[3] == null ? null : (EstadoAfiliadoEnum.valueOf(infoInh[3].toString())));
					p.setEstadoAfiliadoRol(
							p.getEstadoAfiliadoRol() == null ? p.getEstadoAfiliadoCaja() : p.getEstadoAfiliadoRol());
					p.setEstadoAfiliadoRol(
							p.getEstadoAfiliadoRol() == null ? EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION
									: p.getEstadoAfiliadoRol());
					p.setAfiliable(!EstadoAfiliadoEnum.ACTIVO.equals(p.getEstadoAfiliadoRol()));
					listaPersonas.add(p);
				}
			}
			logger.debug(
					"Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)");
			return listaPersonas;

		}

		if ((primerNombre != null && !primerNombre.isEmpty())
				|| (primerApellido != null && !primerApellido.isEmpty()) || fechaNacimiento != null
				|| segundoNombre != null && !segundoNombre.isEmpty()
				|| segundoApellido != null && !segundoApellido.isEmpty()) {

			if (idEmpleador == null) {
				logger.debug("parametros persona");

				Date fechaNa = (fechaNacimiento == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate("1800-01-01")
						: new Date(fechaNacimiento);

				ArrayList<Object[]> personasObj = (ArrayList<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_PARAMETROS)
						.setParameter("primerNombre", primerNombre)
						.setParameter("primerApellido", primerApellido)
						.setParameter("segundoNombre", segundoNombre)
						.setParameter("segundoApellido", segundoApellido)
						.setParameter("tipoIdentificacion", TipoIdentificacionEnum.NIT.name())
						.setParameter("fechaNacimiento", fechaNa)
						.getResultList();

				List<PersonaDTO> listaPersonas = new ArrayList<>();
				for (Object[] infoInh : personasObj) {
					PersonaDTO p = new PersonaDTO((Persona) infoInh[0], (PersonaDetalle) infoInh[1]);
					p.setEstadoAfiliadoCaja((EstadoAfiliadoEnum.valueOf(infoInh[2].toString())));
					p.setAfiliable(true);
					listaPersonas.add(p);
				}

				logger.debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)");
				return listaPersonas;
			} else {
				logger.debug("parametros persona");

				Date fechaNa = (fechaNacimiento == null) ? CalendarUtils.darFormatoYYYYMMDDGuionDate("1800-01-01")
						: new Date(fechaNacimiento);

				ArrayList<Object[]> personasObj = (ArrayList<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_ROLAFILIADO_PARAMETROS)
						.setParameter("primerNombre", primerNombre)
						.setParameter("primerApellido", primerApellido)
						.setParameter("segundoNombre", segundoNombre)
						.setParameter("segundoApellido", segundoApellido)
						.setParameter("tipoIdentificacion", TipoIdentificacionEnum.NIT.name())
						.setParameter("fechaNacimiento", fechaNa)
						.setParameter("idEmpleador", idEmpleador)
						.getResultList();

				List<PersonaDTO> listaPersonas = new ArrayList<>();
				for (Object[] infoInh : personasObj) {
					PersonaDTO p = new PersonaDTO((Persona) infoInh[0], (PersonaDetalle) infoInh[1],
							(RolAfiliado) infoInh[2]);
					p.setEstadoAfiliadoCaja(
							infoInh[3] == null ? null : (EstadoAfiliadoEnum.valueOf(infoInh[3].toString())));
					p.setEstadoAfiliadoRol(
							p.getEstadoAfiliadoRol() == null ? p.getEstadoAfiliadoCaja() : p.getEstadoAfiliadoRol());
					p.setEstadoAfiliadoRol(
							p.getEstadoAfiliadoRol() == null ? EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION
									: p.getEstadoAfiliadoRol());
					p.setAfiliable(!EstadoAfiliadoEnum.ACTIVO.equals(p.getEstadoAfiliadoRol()));
					listaPersonas.add(p);
				}

				logger.debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)");
				return listaPersonas;
			}
		}

		logger.debug("Finaliza buscarPersonas(TipoIdentificacionEnum, String, String, String, Date)");
		return null;
	}

	/**
	 * @param personas
	 * @param idEmpleador
	 * @return a
	 */
	public List<PersonaDTO> convertirADTO(List<PersonaDTO> personas, Long idEmpleador) {
		List<PersonaDTO> personasDTO = new ArrayList<>();
		List<ConsultarEstadoDTO> listConsulEstado = new ArrayList<>();
		for (PersonaDTO personaDTO : personas) {
			ConsultarEstadoDTO consulEsta = new ConsultarEstadoDTO();
			List<RolAfiliado> roles = null;
			consulEsta.setEntityManager(entityManager);
			consulEsta.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
			consulEsta.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
			// se consultan los roles que tiene asociado el afiliado a una
			// empresa especifico
			// para determinar si es afiliable
			if (idEmpleador != null && !idEmpleador.equals("")) {
				consulEsta.setIdEmpleador(idEmpleador);
				roles = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_EMPLEADOR_ESTADO, RolAfiliado.class)
						.setParameter("tipoIdentificacion", personaDTO.getTipoIdentificacion())
						.setParameter("numeroIdentificacion", personaDTO.getNumeroIdentificacion())
						.setParameter("idEmpleador", idEmpleador).getResultList();
				personaDTO.setEstadoAfiliadoRol(null);
				if (!roles.isEmpty()) {
					for (RolAfiliado rolAfiliado : roles) {
						if (personaDTO.getEstadoAfiliadoRol() != null) {
							if (rolAfiliado.getEstadoAfiliado().getCodigo() < personaDTO.getEstadoAfiliadoRol()
									.getCodigo()) {
								personaDTO.setEstadoAfiliadoRol(rolAfiliado.getEstadoAfiliado());
								personaDTO.setFechaIngreso(rolAfiliado.getFechaIngreso());
								personaDTO.setFechaRetiro(rolAfiliado.getFechaRetiro());
							}
						} else {
							personaDTO.setEstadoAfiliadoRol(rolAfiliado.getEstadoAfiliado());
							personaDTO.setFechaIngreso(rolAfiliado.getFechaIngreso());
							personaDTO.setFechaRetiro(rolAfiliado.getFechaRetiro());
						}
					}
				}
				personaDTO.setAfiliable(
						EstadoAfiliadoEnum.ACTIVO.equals(personaDTO.getEstadoAfiliadoRol()) ? false : true);
				if (personaDTO.getEstadoAfiliadoRol() == null) {
					personaDTO.setEstadoAfiliadoRol(EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION);
				}
			} else {
				consulEsta.setTipoPersona(ConstantesComunes.PERSONAS);
			}

			listConsulEstado.add(consulEsta);
			personasDTO.add(personaDTO);
		}

		List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listConsulEstado);
		for (EstadoDTO estadoDTO : listEstados) {
			for (PersonaDTO persona : personasDTO) {
				if (estadoDTO.getNumeroIdentificacion().equals(persona.getNumeroIdentificacion())
						&& estadoDTO.getTipoIdentificacion().equals(persona.getTipoIdentificacion())) {
					persona.setEstadoAfiliadoCaja(estadoDTO.getEstado());
					persona.setEstadoAfiliadoRol(persona.getEstadoAfiliadoRol() != null ? estadoDTO.getEstado() : null);
				}
			}
		}
		return personasDTO;
	}

	@Override
	public PersonaModeloDTO consultarPersona(Long idPersona) {
		logger.debug("Inicio de método consultarPersona(Long idPersona)");
		PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
		try {
			personaModeloDTO = (PersonaModeloDTO) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_ID, PersonaModeloDTO.class)
					.setParameter("idPersona", idPersona).getSingleResult();

			logger.debug("Fin de método consultarPersona(Long idPersona)");
			return personaModeloDTO;

		} catch (NoResultException nre) {
			try {
				Persona persona = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_SOLO_ID, Persona.class)
						.setParameter("idPersona", idPersona).getSingleResult();
				personaModeloDTO.convertToDTO(persona, null);

				return personaModeloDTO;
			} catch (NoResultException nore) {
				logger.error("No se encontró la persona con ese id", nre);
			}
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		return null;
	}

	@Override
	public void actualizarPersona(Persona persona) {
		try {
			logger.debug("Inicio de método actualizarPersona(Persona persona)");
			entityManager.merge(persona);
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarUbicacion(java.lang.Long)
	 */
	@Override
	public UbicacionDTO consultarUbicacion(Long idUbicacion) {
		logger.debug("Inicia operación consultarUbicacion(Long)");
		UbicacionDTO ubicacionDTO = new UbicacionDTO();
		try {
			Ubicacion ubicacion = this.consultarObjetoUbicacion(idUbicacion);
			ubicacionDTO = UbicacionDTO.obtenerUbicacionDTO(ubicacion);
		} catch (NoResultException noResult) {
			logger.debug("Finaliza consultarUbicacion(Long): No existe Ubicacion");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarUbicacion(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación consultarUbicacion(Long)");
		return ubicacionDTO;
	}

	/**
	 * Consulta una ubicacion por Identificacion
	 * 
	 * @param idUbicacion
	 * @return Ubicacion
	 */
	private Ubicacion consultarObjetoUbicacion(Long idUbicacion) {
		logger.debug("Inicia operación consultarObjetoUbicacion(Long)");
		Ubicacion ubicacion = new Ubicacion();
		try {
			ubicacion = (Ubicacion) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_POR_ID)
					.setParameter("idUbicacion", idUbicacion).getSingleResult();
		} catch (NoResultException e) {
			logger.debug("Finaliza consultarObjetoUbicacion(Long): No existe el Beneficiario");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarObjetoUbicacion(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación consultarObjetoUbicacion(Long)");
		return ubicacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#actualizarDatosPersona(com.
	 * asopagos.dto.modelo.PersonaModeloDTO)
	 */
	@Override
	public void actualizarDatosPersona(PersonaModeloDTO personaDTO) {
		/*
		 * logger.debug("Inicia operación actualizarPersona(PersonaDTO)");
		 * try {
		 * Persona personaActual = personaDTO.convertToPersonaEntity();
		 * //Se actualiza la persona como llega en el DTO.
		 * personaActual = entityManager.merge(personaActual);
		 * //Se actualiza PersonaDetalle
		 * PersonaDetalle personaDetalle = personaDTO.convertToPersonaDetalleEntity();
		 * if (personaDetalle.getIdPersonaDetalle() != null) {
		 * entityManager.merge(personaDetalle);
		 * }
		 * // Se asigna la Ubicación asociada a la Persona
		 * UbicacionModeloDTO ubicacionModeloDTO = personaDTO.getUbicacionModeloDTO();
		 * if (ubicacionModeloDTO != null) {
		 * // Se actualiza la Ubicación como llega al DTO.
		 * Ubicacion ubicacion = ubicacionModeloDTO.convertToEntity();
		 * if (ubicacion.getIdUbicacion() != null) {
		 * entityManager.merge(ubicacion);
		 * } else {
		 * entityManager.persist(ubicacion);
		 * // Se asocia la nueva Ubicación a la persona.
		 * personaActual.setUbicacionPrincipal(ubicacion);
		 * }
		 * }
		 * } catch (Exception e) {
		 * logger.
		 * error("Ocurrió un error inesperado en el método actualizarPersona(PersonaDTO)"
		 * , e);
		 * throw new
		 * TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		 * }
		 * logger.debug("Finaliza operación actualizarPersona(PersonaDTO)");
		 */
		ActualizarDatosPersonaRutine a = new ActualizarDatosPersonaRutine();
		a.actualizarDatosPersona(personaDTO, entityManager);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#consultarPersonaDTO(com.
	 * asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarDatosPersona(com.
	 * asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	public PersonaModeloDTO consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.info("**__**Inicia operación consultarPersonaDTO(TipoIdentificacionEnum, String) numeroIdentificacion: "
				+ numeroIdentificacion + " tipoIdentificacion:" + tipoIdentificacion);
		PersonaModeloDTO personaModeloDTO = null;
		PersonaModeloDTO personaDetalleModeloDTO = null;
		try {
			/** inicio modificado 26/05/2022 para usar WITH(NOLOCK) */
			/*
			 * Object[] personaObject =
			 * (Object[])entityManager.createNamedQuery(NamedQueriesConstants.
			 * CONSULTAR_PERSONA_TIPO_NUM_IDENTIFICACION)
			 * .setParameter("numeroIdentificacion", numeroIdentificacion)
			 * .setParameter("tipoIdentificacion",
			 * tipoIdentificacion.name()).getSingleResult();
			 * Persona persona = new Persona();
			 * if(personaObject != null){
			 * Ubicacion ubi = new Ubicacion();
			 * ubi.setIdUbicacion(personaObject[5] != null ?
			 * Long.valueOf(personaObject[5].toString()) : null);
			 * persona.setIdPersona(Long.parseLong(personaObject[0].toString()));
			 * persona.setDigitoVerificacion(personaObject[1] != null ?
			 * Short.parseShort(personaObject[1].toString()) : null);
			 * persona.setNumeroIdentificacion(personaObject[2] != null ?
			 * personaObject[2].toString() : null);
			 * persona.setRazonSocial(personaObject[3] != null ? personaObject[3].toString()
			 * : null);
			 * persona.setTipoIdentificacion(personaObject[4] != null ?
			 * TipoIdentificacionEnum.valueOf(personaObject[4].toString()) : null);
			 * persona.setUbicacionPrincipal(ubi != null ? ubi : null);
			 * persona.setPrimerNombre(personaObject[6] != null ?
			 * personaObject[6].toString() : null);
			 * persona.setSegundoNombre(personaObject[7] != null ?
			 * personaObject[7].toString() : null);
			 * persona.setPrimerApellido(personaObject[8] != null ?
			 * personaObject[8].toString() : null);
			 * persona.setSegundoApellido(personaObject[9] != null ?
			 * personaObject[9].toString() : null);
			 * persona.setCreadoPorPila(personaObject[10] != null ?
			 * (Boolean)personaObject[10] : null);
			 * }
			 */
			/**
			 * FIN modificado 26/05/2022 para usar WITH(NOLOCK) nota: si se requiere
			 * dejar a una version como estaba eliminar desde este comentario hacia atras al
			 * inicio modificado y
			 * descometariar persona=entity tener en cuenta namedquery jpa
			 */
			Persona persona = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_NUM_IDENTIFICACION, Persona.class)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();
			personaModeloDTO = new PersonaModeloDTO();
			personaModeloDTO.convertToDTO(persona, null);
			logger.info("**_**consultarPersonaDTO getIdPersona(): " + personaModeloDTO.getIdPersona());
			personaDetalleModeloDTO = (PersonaModeloDTO) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE_TIPO_NUM_IDENTIFICACION)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();
		} catch (NoResultException noResult) {
			logger.info("No se encontro informacion con los datos suministrados " + personaModeloDTO);
			return personaModeloDTO;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el método consultarPersonaDTO(TipoIdentificacionEnum, String))", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.info("**_**Finaliza operación consultarPersonaDTO(TipoIdentificacionEnum, String)" + personaDetalleModeloDTO.toString());
		return personaDetalleModeloDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * consultarVencimientoCertificadoEscolaridad()
	 */
	@Override
	public List<BeneficiarioNovedadAutomaticaDTO> consultarVencimientoCertificadoEscolaridad() {
		logger.debug("Inicia operación consultarVencimientoCertificadoEscolaridad()");
		try {
			/* Se obtiene la fecha actual del sistema. */
			Calendar fechaActualSistema = Calendar.getInstance();

			List<Beneficiario> listaBeneficiarios = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_VENCIMIENTO_CERTIFICADO_ESCOLARIDAD)
					.setParameter("fechaActualSistema", fechaActualSistema.getTime())
					.setParameter("certificadoActivo", NumerosEnterosConstants.UNO).getResultList();

			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiariosDTO = new ArrayList<>();
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				for (Beneficiario beneficiario : listaBeneficiarios) {
					BeneficiarioNovedadAutomaticaDTO beneficiarioDTO = new BeneficiarioNovedadAutomaticaDTO();
					beneficiarioDTO.setIdBeneficiario(beneficiario.getIdBeneficiario());
					beneficiarioDTO.setIdPersonaAfiliado(beneficiario.getAfiliado().getPersona().getIdPersona());

					listaBeneficiariosDTO.add(beneficiarioDTO);
				}
			}
			logger.debug("Finaliza operación consultarVencimientoCertificadoEscolaridad()");
			return listaBeneficiariosDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarVencimientoCertificadoEscolaridad()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * inactivarCertificadoEscolaridad(java.util.List)
	 */
	@Override
	public void inactivarCertificadoEscolaridad(List<Long> idBeneficiariosInactivar) {
		logger.debug("Inicia operación inactivarCertificadoEscolaridad(List<Long>)");
		try {
			Calendar fechaActualSistema = Calendar.getInstance();
			int registrosPag = 1000;
			int contRegistros = 0;
			int cantBeneficiarios = idBeneficiariosInactivar.size();

			ArrayList<Long> paginaIdBen = new ArrayList<>();

			do {
				for (int i = 0; i < registrosPag && contRegistros < cantBeneficiarios; i++) {
					System.out.println("Contador " + contRegistros);
					paginaIdBen.add(idBeneficiariosInactivar.get(contRegistros));
					System.out.println("Beneficiario " + idBeneficiariosInactivar.get(contRegistros));
					contRegistros++;
				}

				List<BeneficiarioDetalle> benDetalleInactivar = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_INACTIVAR_CERTIFICADO)
						.setParameter("idBeneficiarios", paginaIdBen)
						.setParameter("fechaActualSistema", fechaActualSistema.getTime())
						.setParameter("certificadoActivo", NumerosEnterosConstants.UNO).getResultList();

				if (benDetalleInactivar != null && !benDetalleInactivar.isEmpty()) {
					/*
					 * Se inactiva en batch los Certificado Escolaridad del
					 * Beneficiario Configuración actual: hibernate.jdbc.batch_size
					 * value = 500
					 */
					for (BeneficiarioDetalle beneficiarioDetalle : benDetalleInactivar) {
						beneficiarioDetalle.setCertificadoEscolaridad(Boolean.FALSE);
						entityManager.merge(beneficiarioDetalle);
						logger.info("BED Procesado : " + beneficiarioDetalle.getIdBeneficiarioDetalle());

					}
				}
				paginaIdBen.clear();
			} while (contRegistros < cantBeneficiarios);

			logger.debug("Finaliza operación inactivarCertificadoEscolaridad(List<Long>)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método inactivarCertificadoEscolaridad(List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * consultarPersonasInactivarCtaWeb(com.asopagos.enumeraciones.core.
	 * ClasificacionEnum)
	 */
	@Override
	public List<Long> consultarPersonasInactivarCtaWeb() {
		Set<Long> personasInactivarHash = new HashSet<>();
		List<Long> idPersonasInactivar = new ArrayList<>();
		/* Consulta las personas que se deben inactivar por Fallecimiento */
		List<String> tipoAfiliados = new ArrayList<>();
		tipoAfiliados.add(TipoAfiliadoEnum.PENSIONADO.name());
		tipoAfiliados.add(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name());
		tipoAfiliados.add(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name());

		List<BigInteger> idPersonasFallecidas = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_INACTIVAR_FALLECIMIENTO)
				.setParameter("tiposAfiliado", tipoAfiliados).setParameter("fallecido", NumerosEnterosConstants.UNO)
				.getResultList();
		/* Se agregan las personas Fallecidas. */
		if (idPersonasFallecidas != null && !idPersonasFallecidas.isEmpty()) {
			for (BigInteger idPersonaFallecida : idPersonasFallecidas) {
				personasInactivarHash.add(idPersonaFallecida.longValue());
			}
		}
		// CC 220114
		List<EstadoAfiliadoEnum> estadosAfiliado = new ArrayList<EstadoAfiliadoEnum>();
		estadosAfiliado.add(EstadoAfiliadoEnum.INACTIVO);
		// estadosAfiliado.add(EstadoAfiliadoEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
		/*
		 * Consulta las personas a inactivar Cta Web por Retiro o inactivas con
		 * servicios sin afiliacion inactivos.
		 */
		List<Object[]> personasRetiradas = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_RETIRO)
				.setParameter("estadosAfiliadoCaja", estadosAfiliado)
				.setParameter("servicioSinAfiliacion", NumerosEnterosConstants.CERO).getResultList();
		/* Se agregan las personas retiradas. */
		if (personasRetiradas != null && !personasRetiradas.isEmpty()) {
			Date fechaActual = new Date();
			/*
			 * Se obtiene el Tiempo de Reintegro asignado como parámetro del
			 * sistema.
			 */
			Long diasReintegro = CalendarUtils
					.toMilis((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_REINTEGRO_AFILIADO));
			for (Object[] personaRetirada : personasRetiradas) {
				Date fechaRetiro = (Date) personaRetirada[NumerosEnterosConstants.CERO];
				/* Se calculan los días transcurridos hasta la fecha actual */
				Long diasTranscurridos = (fechaActual.getTime() - fechaRetiro.getTime()) / (3600 * 24 * 1000);
				/*
				 * Si ya cumple con el plazo de reintegro se debe inactivar la
				 * cuenta web
				 */
				if (diasTranscurridos > diasReintegro) {
					personasInactivarHash.add(((BigInteger) personaRetirada[NumerosEnterosConstants.UNO]).longValue());
				}
			}
		}
		idPersonasInactivar.addAll(personasInactivarHash);
		return idPersonasInactivar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * consultarNombreUsuarioPersonas(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> consultarNombreUsuarioPersonas(List<Long> idPersonas) {
		logger.debug("Inicia operación consultarNombreUsuarioPersonas(List<Long>)");
		try {
			/*
			 * Consulta los datos de tipo y número de Identificación asociados a
			 * los Empleadores.
			 */
			List<Object[]> personasInactivar = entityManager
					.createNamedQuery(NamedQueriesConstants.NOVEDADES_PERSONA_CONSULTAR_NOMBRE_USUARIO)
					.setParameter("idsPersona", idPersonas).getResultList();
			List<String> userNames = new ArrayList<>();
			if (personasInactivar != null && !personasInactivar.isEmpty()) {
				for (Object[] datosPersona : personasInactivar) {
					String tipoIdentificacion = (String) datosPersona[0];
					String numeroIdentificacion = (String) datosPersona[1];
					/* Se arma el nombre de usuario de cada Persona. */
					String userName = tipoIdentificacion + "_" + numeroIdentificacion;
					/*
					 * Se agrega a la lista de Nombres de Usuario cada
					 * Empleador.
					 */
					userNames.add(userName);
				}
			}
			logger.debug("Finaliza operación consultarNombreUsuarioPersonas(List<Long>)");
			return userNames;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarNombreUsuarioPersonas(List<Long>)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * actualizarCondicionInvalidez(com.asopagos.dto.modelo.
	 * CondicionInvalidezModeloDTO)
	 */
	@Override
	public void actualizarCondicionInvalidez(CondicionInvalidezModeloDTO condicionInvalidezModeloDTO) {
		logger.debug("Inicia operación actualizarCondicionInvalidez(CondicionInvalidezModeloDTO)");
		try {
			CondicionInvalidez condicionInvalidez = condicionInvalidezModeloDTO.convertToEntity();
			if (condicionInvalidez.getIdCondicionInvalidez() != null) {
				entityManager.merge(condicionInvalidez);
			} else {
				entityManager.persist(condicionInvalidez);
			}
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el método actualizarCondicionInvalidez(CondicionInvalidezModeloDTO)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación actualizarCondicionInvalidez(CondicionInvalidezModeloDTO)");
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CondicionInvalidezModeloDTO consultarCondicionInvalidez(String numeroIdentificacion,
			TipoIdentificacionEnum tipoIdentificacion) {

		logger.debug("Inicia operación consultarCondicionInvalidez(String, TipoIdentificacionEnum)");
		CondicionInvalidezModeloDTO condicionInvalidezModeloDTO = new CondicionInvalidezModeloDTO();
		try {
			CondicionInvalidez condicionInvalidez = (CondicionInvalidez) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICION_INVALIDEZ_POR_PERSONA)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();

			if (condicionInvalidez != null) {
				condicionInvalidezModeloDTO.convertToDTO(condicionInvalidez);
			}

		} catch (NoResultException e) {
			logger.debug(
					"Finaliza consultarCondicionInvalidez(String, TipoIdentificacionEnum): No existe la condición de invalidez para la persona.");
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el consultarCondicionInvalidez(String, TipoIdentificacionEnum)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación consultarCondicionInvalidez(String, TipoIdentificacionEnum)");
		return condicionInvalidezModeloDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#CrearPersona(com.asopagos.
	 * dto.modelo.PersonaModeloDTO)
	 */
	@Override
	public Long CrearPersona(PersonaModeloDTO personaModeloDTO) {
		CrearPersonaRutine cp = new CrearPersonaRutine();
		return cp.crearPersona(personaModeloDTO, entityManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * ConsultarAportePosteriorFechaFallecimiento(com.asopagos.dto.modelo.
	 * PersonaModeloDTO, java.lang.Long)
	 */
	@Override
	public Boolean ConsultarAportePosteriorFechaFallecimiento(ComparacionFechasDefuncionYAporteDTO comparacion) {
		logger.debug("ConsultarAportePosteriorFechaFallecimiento(PersonaModeloDTO, Long)");

		Boolean aprobado = true;
		try {
			Persona personaModelo = comparacion.getPersona().convertToPersonaEntity();

			PersonaModeloDTO personaDetalle = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_DETALLE_TIPO_NUM_IDENTIFICACION,
							PersonaModeloDTO.class)
					.setParameter("numeroIdentificacion", personaModelo.getNumeroIdentificacion())
					.setParameter("tipoIdentificacion", personaModelo.getTipoIdentificacion()).getSingleResult();

			// No se utiliza. se comenta
			// int edadEnAnios = NumerosEnterosConstants.UNO_NEGATIVO;

			// se realiza el calculo de la edad en años de la persona
			if (personaDetalle.getFechaFallecido() != null) {
				Date fechaAporte = new Date(comparacion.getFechaRegistroAporte());
				Date fechaFallecido = new Date(personaDetalle.getFechaFallecido());

				logger.debug("Finaliza ConsultarAportePosteriorFechaFallecimiento(PersonaModeloDTO, Long)");
				return CalendarUtils.esFechaMayor(fechaAporte, fechaFallecido);
			}

			logger.debug(
					"Finaliza ConsultarAportePosteriorFechaFallecimiento(PersonaModeloDTO, Long): la persona ya está registrada pero no tiene fecha de fallecimiento");
			return aprobado;

		} catch (NonUniqueResultException nue) {
			logger.error(
					"Finaliza ConsultarAportePosteriorFechaFallecimiento(PersonaModeloDTO, Long): hay más de una persona registrada con el tipo y numero de identificación suministrados",
					nue);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza ConsultarAportePosteriorFechaFallecimiento(PersonaModeloDTO, Long): la persona no se encuentra registrada en el sistema",
					nre);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * consultarFechaNacimientoPersona(java.lang.String,
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum)
	 */
	@Override
	public Long consultarFechaNacimientoPersona(String numeroIdentificacion,
			TipoIdentificacionEnum tipoIdentificacion) {
		logger.debug("inicia consultarFechaNacimientoPersona(String, TipoIdentificacionEnum)");

		try {
			Date fecha = (Date) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_NACIMIENTO_PERSONA)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();

			logger.debug("finaliza consultarFechaNacimientoPersona(String, TipoIdentificacionEnum)");
			return fecha.getTime();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			logger.debug("finaliza consultarFechaNacimientoPersona(String, TipoIdentificacionEnum)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarPersonas(com.
	 * asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<PersonaDTO> consultarPersonasEstadoCaja(UriInfo uriInfo, HttpServletResponse response,
			TipoIdentificacionEnum valorTI, String valorNI, String primerNombre, String primerApellido,
			EstadoAfiliadoEnum estadoCaja,
			String textoFiltro) {
		logger.debug("Inicia consultarPersonasEstadoCaja(TipoIdentificacionEnum, String, String, String, String)");
		try {
			// Lista con los datos de retorno
			List<PersonaDTO> retorno = new ArrayList<>();

			// Se consultan los beneficiarios que cumplan con los criterios
			List<Object[]> listPersonas = crearQueryPersonaEstadoCaja(uriInfo, response, valorTI, valorNI, primerNombre,
					null,
					primerApellido, null, estadoCaja, textoFiltro).getResultList();
			for (Object[] objects : listPersonas) {
				PersonaDTO personaDTO = new PersonaDTO();
				BigInteger idPersona = (BigInteger) objects[0];
				personaDTO.setIdPersona(idPersona.longValue());
				personaDTO.setDigitoVerificacion((Short) objects[1]);
				personaDTO.setNumeroIdentificacion((String) objects[2]);
				personaDTO.setRazonSocial((String) objects[3]);
				String tipoIdentificacion = (String) objects[4];
				personaDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(tipoIdentificacion));
				personaDTO.setPrimerNombre((String) objects[5]);
				personaDTO.setSegundoNombre((String) objects[6]);
				personaDTO.setPrimerApellido((String) objects[7]);
				personaDTO.setSegundoApellido((String) objects[8]);
				String estadoAfiliado = (String) objects[9];
				personaDTO.setEstadoAfiliadoCaja(EstadoAfiliadoEnum.valueOf(estadoAfiliado));
				retorno.add(personaDTO);
			}
			logger.debug("Finaliza consultarPersonasEstadoCaja(TipoIdentificacionEnum, String, String, String, Date)");
			return retorno;
		} catch (Exception e) {
			logger.debug("Finaliza consultarPersonasEstadoCaja(TipoIdentificacionEnum, String, String, String, Date)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO, e);
		}
	}

	/**
	 * Crea el query de consulta a partir de los datos enviados por pantalla
	 * 
	 * @param tipoIdentificacion
	 *                             Tipo idnetifiacion
	 * @param numeroIdentificacion
	 *                             Numero identificacion
	 * @param primerNombre
	 *                             Primer nombre persona
	 * @param segundoNombre
	 *                             Segundo nombre persona
	 * @param primerApellido
	 *                             Primer apellido persona
	 * @param segundoApellido
	 *                             segundo apellido persona
	 * @param estadoRespectoCaja
	 *                             Estado respecto a la caja
	 * @return Query con paremtros listo para la ejecucion
	 */
	private Query crearQueryPersonaEstadoCaja(@Context UriInfo uriInfo, @Context HttpServletResponse response,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre,
			String segundoNombre,
			String primerApellido, String segundoApellido, EstadoAfiliadoEnum estadoRespectoCaja, String textoFiltro) {

		// Se crea la consulta apartir de los filtros enviados
		StringBuilder query = new StringBuilder();
		query.append(QueryConstants.SQL_QUERY_PERSONA_ESTADO);
		StringBuilder queryCondition = new StringBuilder();
		if (tipoIdentificacion != null) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_ESTADO_CONDICION_TIPO_IDENTIFICACION);
		}
		if (numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_ESTADO_CONDICION_NRO_IDENTIFICACION);
		}
		if (primerNombre != null && !primerNombre.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_ESTADO_CONDICION_PRIMER_NOMBRE);
		}
		if (segundoNombre != null && !segundoNombre.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_ESTADO_CONDICION_SEGUNDO_NOMBRE);
		}
		if (primerApellido != null && !primerApellido.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_ESTADO_CONDICION_PRIMER_APELLIDO);
		}
		if (segundoApellido != null && !segundoApellido.isEmpty()) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_ESTADO_CONDICION_SEGUNDO_APELLIDO);
		}
		if (estadoRespectoCaja != null) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_ESTADO_CONDICION_ESTADO_CAJA);
		}
		if (textoFiltro != null) {
			verifyAddWhereOrAnd(queryCondition);
			queryCondition.append(QueryConstants.SQL_PERSONA_FILTRO_TEXTO);
			textoFiltro = "%".concat(textoFiltro).concat("%");
		}
		query.append(queryCondition.toString());

		Map<String, String> hints = new HashMap<>();
		hints.put("tipoIdentificacion", "per.perTipoIdentificacion");
		hints.put("numeroIdentificacion", "per.perNumeroIdentificacion");
		hints.put("razonSocial", "per.perRazonSocial");
		hints.put("estado", "per.estado");

		QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
		queryBuilder.setHints(hints);

		verifyAddParameter(queryBuilder, QueriesConstants.TIPO_IDENTIFICACION, tipoIdentificacion);
		verifyAddParameter(queryBuilder, QueriesConstants.NRO_IDENTIFICACION, numeroIdentificacion);
		verifyAddParameter(queryBuilder, QueriesConstants.PRIMER_NOMBRE, primerNombre);
		verifyAddParameter(queryBuilder, QueriesConstants.PRIMER_APELLIDO, primerApellido);
		verifyAddParameter(queryBuilder, QueriesConstants.SEGUNDO_NOMBRE, segundoNombre);
		verifyAddParameter(queryBuilder, QueriesConstants.SEGUNDO_APELLIDO, segundoApellido);
		verifyAddParameter(queryBuilder, QueriesConstants.ESTADO_CAJA, estadoRespectoCaja);
		verifyAddParameter(queryBuilder, QueriesConstants.FILTRO_TEXTO, textoFiltro);
		queryBuilder.addOrderByDefaultParam("-tipoIdentificacion");

		return queryBuilder.createNativeQuery(query.toString());
	}

	/**
	 * Verifica si hay condicion para agregar AND y en caso contrario agregar
	 * WHERE
	 * 
	 * @param queryCondition
	 *                       Condicion de consulta
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
	 *                   Consulta construida
	 * @param campo
	 *                   Campo parametro del query
	 * @param valorCampo
	 *                   Valor del campo parametro del query
	 */
	private void verifyAddParameter(QueryBuilder query, String campo, Object valorCampo) {
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
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#crearActualizarIntegranteHogar(com.asopagos.dto.modelo.IntegranteHogarModeloDTO)
	 */
	@Override
	public IntegranteHogarModeloDTO crearActualizarIntegranteHogar(IntegranteHogarModeloDTO integranteHogarModeloDTO) {
		logger.info("Inicia crearActualizarIntegranteHogar(IntegranteHogarModeloDTO)"
				+ integranteHogarModeloDTO.getIdPersona());
		PersonaDetalle personaDetalle = new PersonaDetalle();
		Long idPersona = null;
		if (integranteHogarModeloDTO.getIdPersona() != null) {
			PersonaModeloDTO personaDTO = consultarPersona(integranteHogarModeloDTO.getIdPersona());
			idPersona = personaDTO.getIdPersona();
			integranteHogarModeloDTO.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
			integranteHogarModeloDTO.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
		} else {
			// Validar si existe la persona
			Object idPersonaObject = null;
			try {
				idPersonaObject = (Object) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_NUM_IDENTIFICACION_NATIVA)
						.setParameter("numeroIdentificacion", integranteHogarModeloDTO.getNumeroIdentificacion())
						.setParameter("tipoIdentificacion", integranteHogarModeloDTO.getTipoIdentificacion().name())
						.getSingleResult();
			} catch (NoResultException nre) {
				idPersona = null;
			}
			if (idPersonaObject != null) {
				idPersona = Long.valueOf(idPersonaObject.toString());
			}
		}
		// Se debe crear la persona
		if (idPersona == null) {
			idPersona = CrearPersona(integranteHogarModeloDTO);
			// Se crea la persona Detalle asociada al integrante de Hogar.
			personaDetalle = integranteHogarModeloDTO.convertToPersonaDetalleEntity();
			personaDetalle.setIdPersona(idPersona);
			personaDetalle.setBeneficiarioSubsidio(integranteHogarModeloDTO.getBeneficiarioSubsidio());
			if (idPersona != null) {
				entityManager.persist(personaDetalle);
			}
			// debera hacer merge?
		} else {
			/*
			 * Se actualiza el valor de beneficiarioSubsidio en
			 * PersonaDetalle asociado al integrante de Hogar.
			 */
			try {
				personaDetalle = entityManager
						.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
								PersonaDetalle.class)
						.setParameter("tipoIdentificacion", integranteHogarModeloDTO.getTipoIdentificacion())
						.setParameter("numeroIdentificacion", integranteHogarModeloDTO.getNumeroIdentificacion())
						.getSingleResult();

				if (integranteHogarModeloDTO.getBeneficiarioSubsidio() != null) {
					personaDetalle.setBeneficiarioSubsidio(integranteHogarModeloDTO.getBeneficiarioSubsidio());
				}
				// Se actualizan los demás datos de la persona Detalle
				if (integranteHogarModeloDTO.getEstadoCivil() != null) {
					personaDetalle.setEstadoCivil(integranteHogarModeloDTO.getEstadoCivil());
				}
				if (integranteHogarModeloDTO.getGenero() != null) {
					personaDetalle.setGenero(integranteHogarModeloDTO.getGenero());
				}
			} catch (NoResultException e) {
				logger.debug("No existe persona detalle asociada al Integrante de Hogar");
			}
			if (personaDetalle == null || personaDetalle.getIdPersonaDetalle() == null) {
				personaDetalle = integranteHogarModeloDTO.convertToPersonaDetalleEntity();
				personaDetalle.setIdPersona(idPersona);
				personaDetalle.setBeneficiarioSubsidio(integranteHogarModeloDTO.getBeneficiarioSubsidio());
				entityManager.persist(personaDetalle);

			} else {
				entityManager.merge(personaDetalle);
			}
		}

		IntegranteHogar integranteHogar = integranteHogarModeloDTO.convertToEntity();
		if (integranteHogarModeloDTO.getIdPostulacion() != null) {
			// Consulta si el integrante existe en la postulación asociada, en dado caso lo
			// actualiza.
			List<BigInteger> idsIntegranteHogar = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_INTEGRANTE_POSTULACION)
					.setParameter("idPersona", idPersona)
					.setParameter("idPostulacion", integranteHogarModeloDTO.getIdPostulacion())
					.getResultList();
			if (idsIntegranteHogar != null && !idsIntegranteHogar.isEmpty()) {
				integranteHogar.setIdIntegranteHogar(idsIntegranteHogar.get(0).longValue());
			}
		}
		integranteHogar.setIdPersona(idPersona);
		if (integranteHogar.getIdIntegranteHogar() != null) {
			entityManager.merge(integranteHogar);
		} else {
			entityManager.persist(integranteHogar);
		}

		integranteHogarModeloDTO.setIdIntegranteHogar(integranteHogar.getIdIntegranteHogar());
		integranteHogarModeloDTO.setIdPersona(idPersona);
		logger.debug("Finaliza crearActualizarIntegranteHogar(IntegranteHogarModeloDTO)");
		return integranteHogarModeloDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#
	 * registrarCondicionesEspecialesFOVIS(java.util.List, java.lang.Long)
	 */
	@Override
	public void registrarCondicionesEspecialesFOVIS(List<NombreCondicionEspecialEnum> condicionesEspeciales,
			Long idPersona) {
		logger.debug(
				"Se inicia el servicio de registrarCondicionesEspecialesFOVIS(List<CondicionEspecialPersonaModeloDTO>)");
		try {

			// TODO revisar quitar de la lista
			for (NombreCondicionEspecialEnum nombreCondicionEspecial : condicionesEspeciales) {
				CondicionEspecialPersona condicionEspecialPersona = new CondicionEspecialPersona();
				condicionEspecialPersona.setNombreCondicion(nombreCondicionEspecial);
				condicionEspecialPersona.setIdPersona(idPersona);
				entityManager.persist(condicionEspecialPersona);
			}
			logger.debug(
					"Finaliza el servicio de registrarCondicionesEspecialesFOVIS(List<CondicionEspecialPersonaModeloDTO>)");
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio de registrarCondicionesEspecialesFOVIS(List<CondicionEspecialPersonaModeloDTO>)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#crearActualizarJefeHogar(
	 * com.asopagos.dto.modelo.JefeHogarModeloDTO)
	 */
	@Override
	public JefeHogarModeloDTO crearActualizarJefeHogar(JefeHogarModeloDTO jefeHogarDTO) {
		try {
			logger.debug("Inicia el servicio crearActualizarJefeHogar");
			JefeHogar jefeHogar = jefeHogarDTO.convertToEntity();

			PersonaModeloDTO persona = consultarDatosPersona(jefeHogarDTO.getTipoIdentificacion(),
					jefeHogarDTO.getNumeroIdentificacion());
			PersonaDetalle personaDetalle = persona.convertToPersonaDetalleEntity();
			boolean actualizar = false;
			if (jefeHogarDTO.getBeneficiarioSubsidio() != null) {
				personaDetalle.setBeneficiarioSubsidio(jefeHogarDTO.getBeneficiarioSubsidio());
				actualizar = true;
			}
			if (jefeHogarDTO.getResideSectorRural() != null
					&& !jefeHogarDTO.getResideSectorRural().equals(personaDetalle.getResideSectorRural())) {
				personaDetalle.setResideSectorRural(jefeHogarDTO.getResideSectorRural());
				actualizar = true;
			}
			if (jefeHogarDTO.getHabitaCasaPropia() != null
					&& !jefeHogarDTO.getHabitaCasaPropia().equals(personaDetalle.getHabitaCasaPropia())) {
				personaDetalle.setHabitaCasaPropia(jefeHogarDTO.getHabitaCasaPropia());
				actualizar = true;
			}
			if (jefeHogarDTO.getAutorizaUsoDatosPersonales() != null && !jefeHogarDTO.getAutorizaUsoDatosPersonales()
					.equals(personaDetalle.getAutorizaUsoDatosPersonales())) {
				personaDetalle.setAutorizaUsoDatosPersonales(jefeHogarDTO.getAutorizaUsoDatosPersonales());
				actualizar = true;
			}
			if (jefeHogarDTO.getGenero() != null && !jefeHogarDTO.getGenero().equals(personaDetalle.getGenero())) {
				personaDetalle.setGenero(jefeHogarDTO.getGenero());
				actualizar = true;
			}
			if (actualizar) {
				entityManager.merge(personaDetalle);
			}

			Persona personaActual = persona.convertToPersonaEntity();
			/* Se asigna la Ubicación asociada a la Persona */
			UbicacionModeloDTO ubicacionModeloDTO = jefeHogarDTO.getUbicacionModeloDTO();
			if (personaActual.getUbicacionPrincipal() != null) {
				ubicacionModeloDTO.setIdUbicacion(personaActual.getUbicacionPrincipal().getIdUbicacion());
			}
			if (ubicacionModeloDTO != null) {
				/* Se actualiza la Ubicación como llega al DTO. */
				Ubicacion ubicacion = ubicacionModeloDTO.convertToEntity();
				if (ubicacion.getIdUbicacion() != null) {
					entityManager.merge(ubicacion);
				} else {
					entityManager.persist(ubicacion);
					/* Se asocia la nueva Ubicación a la persona. */
					personaActual.setUbicacionPrincipal(ubicacion);
					entityManager.merge(personaActual);
				}
				ubicacionModeloDTO.setIdUbicacion(ubicacion.getIdUbicacion());
			}

			JefeHogar managed = entityManager.merge(jefeHogar);
			jefeHogarDTO.setIdJefeHogar(managed.getIdJefeHogar());
			jefeHogarDTO.setUbicacionModeloDTO(ubicacionModeloDTO);
			logger.debug("Finaliza el servicio crearActualizarJefeHogar");
			return jefeHogarDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio crearActualizarJefeHogar", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarListaIntegranteHogar(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<IntegranteHogarModeloDTO> consultarListaIntegranteHogar(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Long idPostulacion) {
		logger.debug(Interpolator.interpolate(
				"Inicia consultarListaIntegranteHogar(TipoIdentificacionEnum, String, Long) {0}, {1}, {2}",
				tipoIdentificacion, numeroIdentificacion, idPostulacion));
		List<Object[]> listIntegrantes = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_INTEGRANTE_HOGAR_SALARIO_POR_JEFE_POSTULACION)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion)
				.setParameter("estadoHogar", EstadoFOVISHogarEnum.ACTIVO.name())
				.setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name())
				.setParameter("estadoBeneficiarioAfiliado", EstadoAfiliadoEnum.ACTIVO.name())
				.setParameter("idPostulacion", idPostulacion).getResultList();

		List<IntegranteHogarModeloDTO> listaIntegranteHogarDTO = new ArrayList<>();
		IntegranteHogarModeloDTO integranteHogarModeloDTO;
		for (Object[] infoInh : listIntegrantes) {
			integranteHogarModeloDTO = new IntegranteHogarModeloDTO((Persona) infoInh[1], (PersonaDetalle) infoInh[2],
					(IntegranteHogar) infoInh[0]);
			listaIntegranteHogarDTO.add(integranteHogarModeloDTO);
		}
		return listaIntegranteHogarDTO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarJefeHogar(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public JefeHogarModeloDTO consultarJefeHogar(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug(Interpolator.interpolate("Inicia consultarJefeHogar(TipoIdentificacionEnum, String): {0}, {1}",
				tipoIdentificacion,
				numeroIdentificacion));
		JefeHogarModeloDTO jefeHogarDTO = null;

		List<Object[]> datosJefeHogar = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_JEFEHOGAR_POR_IDENTIFICACION)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion)
				.getResultList();
		if (datosJefeHogar != null && !datosJefeHogar.isEmpty()) {
			Object[] infoJefeHogar = datosJefeHogar.get(0);
			jefeHogarDTO = new JefeHogarModeloDTO();
			jefeHogarDTO.convertToJefeHogarDTO((JefeHogar) infoJefeHogar[0], (Persona) infoJefeHogar[1],
					(PersonaDetalle) infoJefeHogar[2]);
		}
		logger.debug("Finaliza el servicio consultarJefeHogar");
		return jefeHogarDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#crearActualizarUbicacion(
	 * com.asopagos.dto.modelo.UbicacionModeloDTO)
	 */
	@Override
	public UbicacionModeloDTO crearActualizarUbicacion(UbicacionModeloDTO ubicacionDTO) {
		try {
			logger.debug("Inicia el servicio crearActualizarUbicacion");
			Ubicacion ubicacion = ubicacionDTO.convertToEntity();
			Ubicacion managed = entityManager.merge(ubicacion);
			ubicacionDTO.setIdUbicacion(managed.getIdUbicacion());
			logger.debug("Finaliza el servicio crearActualizarUbicacion");
			return ubicacionDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio crearActualizarUbicacion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#registrarPersonasDetalle(java.util.List)
	 */
	public void registrarPersonasDetalle(List<PersonaDetalleModeloDTO> personasDetalle) {
		logger.debug("Se inicia el servicio de registrarPersonasDetalle(List<PersonaDetalleModeloDTO>)");
		try {
			for (PersonaDetalleModeloDTO personaDetalleModeloDTO : personasDetalle) {
				entityManager.merge(personaDetalleModeloDTO.convertToEntity());
			}
			logger.debug("Finaliza el servicio de registrarPersonasDetalle(List<PersonaDetalleModeloDTO>)");
			return;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio registrarPersonasDetalle(List<PersonaDetalleModeloDTO>)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#consultarPersonaDetalle(com
	 * .asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@Override
	public PersonaDetalleModeloDTO consultarPersonaDetalle(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		logger.debug("Se inicia el servicio consultarPersonaDetalle");
		PersonaDetalleModeloDTO personaDetalleDTO = new PersonaDetalleModeloDTO();
		try {
			PersonaDetalle personaDetalle = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONADETALLE, PersonaDetalle.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();

			personaDetalleDTO.convertToDTO(personaDetalle);

			if(personaDetalleDTO.getFechaNacimiento() != null){
				// Calcular la edad en anios, meses y dias CC Vistas 360
			Date fechaNacimiento = new Date(personaDetalleDTO.getFechaNacimiento());
			String edadAniosMesesDias;

			edadAniosMesesDias = CalendarUtils.calcularEdadAniosMesesDias(fechaNacimiento);

			personaDetalleDTO.setEdad(edadAniosMesesDias);
		}

			try {
				// Consultar pais residencia
				Object paisResidencia = (Object) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_PAIS_RESIDENCIA)
						.setParameter("idPaisResidencia", personaDetalleDTO.getIdPaisResidencia()).getSingleResult();

				if (paisResidencia != null) {
					personaDetalleDTO.setNombrePaisResidencia(paisResidencia.toString());
				}
			} catch (NoResultException nre) {
				personaDetalleDTO.setNombrePaisResidencia("No registra país de residencia");
			}
			try {
                Object resguardo = (Object) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_RESGUARDO)
                        .setParameter("idResguardo", personaDetalleDTO.getIdResguardo()).getSingleResult();

                if (resguardo != null) {
                    personaDetalleDTO.setNombreResguardo(resguardo.toString());
                }
            } catch (NoResultException nre) {
            }
            try {
                Object puebloIndigena = (Object) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_PUEBLO_INDIGENA)
                        .setParameter("idPuebloIndigena", personaDetalleDTO.getIdPuebloIndigena()).getSingleResult();

                if (puebloIndigena != null) {
                    personaDetalleDTO.setNombrePuebloIndigena(puebloIndigena.toString());
                }
            } catch (NoResultException nre) {
            }

			logger.debug("Finaliza el servicio consultarPersonaDetalle");
			return personaDetalleDTO;
		} catch (NoResultException nre) {
			logger.debug(
					"Finaliza el servicio consultarPersonaDetalle, no se encontró un registro asociado a los datos ingresados");
			return personaDetalleDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio consultarPersonaDetalle", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#actualizarPersonaDetalle(
	 * java.util.List)
	 */
	@Override
	public void actualizarPersonaDetalle(List<PersonaDetalleModeloDTO> listaPersonaDetalleDTO) {
		try {
			logger.debug("Se inicia el servicio actualizarPersonaDetalle");
			List<PersonaDetalle> listaPersonaDetalle = new ArrayList<PersonaDetalle>();

			for (PersonaDetalleModeloDTO personaDetalleDTO : listaPersonaDetalleDTO) {
				PersonaDetalle personaDetalle = personaDetalleDTO.convertToEntity();
				listaPersonaDetalle.add(personaDetalle);
			}

			entityManager.merge(listaPersonaDetalle);
			logger.debug("Finaliza el servicio actualizarPersonaDetalle");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio actualizarPersonaDetalle", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#consultarEstadoCajaPersona(
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public EstadoAfiliadoEnum consultarEstadoCajaPersona(TipoIdentificacionEnum valorTI, String valorNI,
			Long idEmpleador) {
		logger.debug("Inicio servicio consultarEstadoCajaPersona(TipoIdentificacionEnum, String, Long)");
		ConsultarEstadoDTO paramEstados = new ConsultarEstadoDTO();
		List<ConsultarEstadoDTO> requestListaEstados = new ArrayList<>();
		List<EstadoDTO> responseListEstadoEmpleador = null;
		EstadoAfiliadoEnum estadoPersona = null;
		String estadoAfiliadoCajaResult = null;
		try {
			if (valorTI != null && valorNI != null && !valorNI.isEmpty()) {
				paramEstados.setEntityManager(entityManager);
				paramEstados.setNumeroIdentificacion(valorNI);
				paramEstados.setTipoIdentificacion(valorTI);
				paramEstados.setTipoPersona(ConstantesComunes.PERSONAS);
				if (idEmpleador != null) {
					paramEstados.setTipoPersona(ConstantesComunes.EMPLEADORES_ID);
					paramEstados.setIdEmpleador(idEmpleador);
				}
				requestListaEstados.add(paramEstados);
				responseListEstadoEmpleador = EstadosUtils.consultarEstadoCaja(requestListaEstados);
				if (responseListEstadoEmpleador != null && !responseListEstadoEmpleador.isEmpty()) {
					estadoAfiliadoCajaResult = responseListEstadoEmpleador.get(0).getEstado().name();
				}
			}

			// Se verifica que se encontro un estado
			if (estadoAfiliadoCajaResult != null && !estadoAfiliadoCajaResult.isEmpty()) {
				estadoPersona = EstadoAfiliadoEnum.valueOf(estadoAfiliadoCajaResult);
			}
			return estadoPersona;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio consultarEstadoCajaPersona(TipoIdentificacionEnum, String, Long)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#buscarEstadoCajaPersonasMasivo(java.util.List)
	 */
	@Override
	public List<ConsultaEstadoCajaPersonaDTO> buscarEstadoCajaPersonasMasivo(
			List<ConsultaEstadoCajaPersonaDTO> lstPersonas) {
		try {
			logger.debug(
					"Inicio servicio buscarEstadoCajaPersonasMasivo(List<ConsultaEstadoCajaPersonaDTO> lstPersonas)");
			for (ConsultaEstadoCajaPersonaDTO consultaEstadoCajaPersonaDTO : lstPersonas) {
				consultaEstadoCajaPersonaDTO.setEstadoAfiliadoEnum(
						consultarEstadoCajaPersona(consultaEstadoCajaPersonaDTO.getTipoIdentificacion(),
								consultaEstadoCajaPersonaDTO.getNumeroIdentificacion(),
								consultaEstadoCajaPersonaDTO.getIdEmpleador()));
			}
			logger.debug(
					"Finaliza servicio buscarEstadoCajaPersonasMasivo(List<ConsultaEstadoCajaPersonaDTO> lstPersonas)");
			return lstPersonas;
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio buscarEstadoCajaPersonasMasivo(List<ConsultaEstadoCajaPersonaDTO> lstPersonas)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#consultarPersonaJefeHogar(
	 * java.lang.Long)
	 */
	@Override
	public PersonaModeloDTO consultarPersonaJefeHogar(Long idJefeHogar) {
		try {
			logger.debug("Inicio servicio consultarPersonaJefeHogar");
			Persona personaJefeHogar = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_JEFEHOGAR, Persona.class)
					.setParameter("idJefeHogar", idJefeHogar).getSingleResult();
			PersonaModeloDTO personaJefeHogarDTO = new PersonaModeloDTO();
			personaJefeHogarDTO.convertToDTO(personaJefeHogar, null);
			logger.debug("Finaliza servicio consultarPersonaJefeHogar");
			return personaJefeHogarDTO;
		} catch (NoResultException e) {
			logger.error("No se encuentra el jefe de hogar con id=" + idJefeHogar, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio consultarPersonaJefeHogar", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#buscarPersonasSinDetalle(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<PersonaDTO> buscarPersonasSinDetalle(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String primerApellido, String segundoNombre,
			String segundoApellido, String razonSocial) {
		try {
			logger.debug(
					"Inicio servicio buscarPersonasSinDetalle(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,String primerNombre, String primerApellido, String segundoNombre, String segundoApellido, String razonSocial)");
			Map<String, String> fields = new HashMap<>();
			Map<String, Object> values = new HashMap<>();

			if (tipoIdentificacion != null) {
				fields.put("tipoIdentificacion", ConsultasDinamicasConstants.IGUAL);
				values.put("tipoIdentificacion", tipoIdentificacion);
			}
			if (numeroIdentificacion != null) {
				fields.put("numeroIdentificacion", ConsultasDinamicasConstants.IGUAL);
				values.put("numeroIdentificacion", numeroIdentificacion);
			}
			if (razonSocial != null) {
				fields.put("razonSocial", ConsultasDinamicasConstants.LIKE);
				values.put("razonSocial", razonSocial);
			}
			if (primerNombre != null) {
				fields.put("primerNombre", ConsultasDinamicasConstants.IGUAL);
				values.put("primerNombre", primerNombre);
			}
			if (primerApellido != null) {
				fields.put("primerApellido", ConsultasDinamicasConstants.IGUAL);
				values.put("primerApellido", primerApellido);
			}
			if (segundoNombre != null) {
				fields.put("segundoNombre", ConsultasDinamicasConstants.IGUAL);
				values.put("segundoNombre", segundoNombre);
			}
			if (segundoApellido != null) {
				fields.put("segundoApellido", ConsultasDinamicasConstants.IGUAL);
				values.put("segundoApellido", segundoApellido);
			}
			List<PersonaDTO> personas = new ArrayList<>();
			List<Persona> personasConsulta = JPAUtils.consultaEntidad(entityManager, Persona.class, fields, values);
			for (Persona persona : personasConsulta) {
				personas.add(new PersonaDTO(persona));
			}
			logger.debug(
					"Finaliza servicio buscarPersonasSinDetalle(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,String primerNombre, String primerApellido, String segundoNombre, String segundoApellido, String razonSocial)");
			return personas.isEmpty() ? null : personas;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio buscarPersonasSinDetalle", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarPersonaRazonSocial(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaDTO> consultarPersonaRazonSocial(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String razonSocial) {
		try {
			logger.debug("Se inicia el servicio consultarPersonaRazonSocial");
			List<PersonaDTO> lstPersonas = new ArrayList<>();
			if (tipoIdentificacion != null && numeroIdentificacion != null) {
				lstPersonas = entityManager
						.createNamedQuery(
								NamedQueriesConstants.CONSULTAR_PERSONA_POR_TIPO_NUMERO_IDENTIFICACION,
								PersonaDTO.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
			} else if (razonSocial != null) {
				lstPersonas = entityManager
						.createNamedQuery(
								NamedQueriesConstants.CONSULTAR_PERSONA_POR_RAZONSOCIAL,
								PersonaDTO.class)
						.setParameter("razonSocial", "%" + razonSocial + "%").getResultList();
			}
			logger.debug("Finaliza el servicio consultarPersonaRazonSocial");
			return lstPersonas.isEmpty() ? null : lstPersonas;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio consultarPersonaRazonSocial", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * Consulta los datos de BeneficiarioDetalle
	 * 
	 * @param idPersona
	 *                  identificación Persona Asociada
	 * @return PersonaDetalle
	 */
	private BeneficiarioDetalle consultarDatosBeneficiarioDetalle(Long idPersonaDetalle) {
		logger.debug("Inicia operación consultarDatosPersonaDetalle(Long)");
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
			logger.error("Ocurrió un error inesperado en el método consultarDatosPersonaDetalle(Long))", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación consultarDatosPersonaDetalle(Long)");
		return beneficiarioDetalle;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarAdministradorSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<AdministradorSubsidioModeloDTO> consultarAdministradorSubsidio(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido) {

		logger.info("Inicia el servicio consultarAdministradorSubsidio");
		logger.info("tipoIdentificacion: " + tipoIdentificacion);
		logger.info("numeroIdentificacion: " + numeroIdentificacion);
		String firmaServicio = "PersonasBusiness.consultarAdministradorSubsidio(TipoIdentificacionEnum,String,String,String,String,String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AdministradorSubsidioModeloDTO> listaAdministradores = null;

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
			listaAdministradores = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ADMINISTRADORES_SUBSIDIO,
							AdministradorSubsidioModeloDTO.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("primerNombre", parametroPrimerNombre)
					.setParameter("segundoNombre", parametroSegundoNombre)
					.setParameter("primerApellido", parametroPrimerApellido)
					.setParameter("segundoApellido", parametroSegundoApellido).getResultList();

		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en " + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.info(listaAdministradores.isEmpty());

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return listaAdministradores.isEmpty() ? null : listaAdministradores;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#guardarMedioDePago(com.
	 * asopagos.dto.modelo.MedioDePagoModeloDTO)
	 */
	@Override
	public MedioDePagoModeloDTO guardarMedioDePago(MedioDePagoModeloDTO medioDePagoModeloDTO) {
		try {
			logger.debug("Se inicia el servicio guardarMedioDePago");
			Long idMedioDePago = null;
			Long idMedioPagoPersona = null;

			if (TipoMedioDePagoEnum.EFECTIVO.equals(medioDePagoModeloDTO.getTipoMedioDePago())) {
				MedioEfectivo medioEfectivo = new MedioEfectivo();
				medioEfectivo.setIdMedioPago(null);
				medioEfectivo.setEfectivo(medioDePagoModeloDTO.getEfectivo());
				medioEfectivo.setSitioPago(medioDePagoModeloDTO.getSitioPago());
				medioEfectivo.setSedeCaja(medioDePagoModeloDTO.getSede());
				medioEfectivo.setTipoMediopago(medioDePagoModeloDTO.getTipoMedioDePago());
				MedioEfectivo managed = entityManager.merge(medioEfectivo);
				idMedioDePago = managed.getIdMedioPago();
			} else if (TipoMedioDePagoEnum.TRANSFERENCIA.equals(medioDePagoModeloDTO.getTipoMedioDePago())) {
				MedioTransferencia medioTransferencia = new MedioTransferencia();
				medioTransferencia.setIdMedioPago(null);
				medioTransferencia.setDigitoVerificacionTitular(medioDePagoModeloDTO.getDigitoVerificacionTitular());
				medioTransferencia.setIdBanco(medioDePagoModeloDTO.getIdBanco());
				medioTransferencia.setNombreTitularCuenta(medioDePagoModeloDTO.getNombreTitularCuenta());
				medioTransferencia.setNumeroCuenta(medioDePagoModeloDTO.getNumeroCuenta());
				medioTransferencia
						.setNumeroIdentificacionTitular(medioDePagoModeloDTO.getNumeroIdentificacionTitular());
				medioTransferencia.setTipoCuenta(medioDePagoModeloDTO.getTipoCuenta());
				medioTransferencia.setTipoIdentificacionTitular(medioDePagoModeloDTO.getTipoIdentificacionTitular());
				medioTransferencia.setTipoMediopago(medioDePagoModeloDTO.getTipoMedioDePago());
				MedioTransferencia managed = entityManager.merge(medioTransferencia);
				idMedioDePago = managed.getIdMedioPago();
			}else if (TipoMedioDePagoEnum.TARJETA.equals(medioDePagoModeloDTO.getTipoMedioDePago())) {
				MedioTarjeta medioTarjeta = new MedioTarjeta();
				medioTarjeta.setIdMedioPago(null);
				medioTarjeta.setNumeroTarjeta(medioDePagoModeloDTO.getNumeroTarjeta());
				medioTarjeta.setDisponeTarjeta(medioDePagoModeloDTO.getDisponeTarjeta());
				medioTarjeta.setEstadoTarjetaMultiservicios(medioDePagoModeloDTO.getEstadoTarjetaMultiservicios());
				//medioTarjeta.setSolicitudTarjeta(medioDePagoModeloDTO.getSolicitudTarjeta());
				String solicitudTarjeta = String.valueOf(medioDePagoModeloDTO.getSolicitudTarjeta());
				if (solicitudTarjeta.isEmpty()) {
					medioTarjeta.setSolicitudTarjeta(SolicitudTarjetaEnum.EXPEDICION);
				} else {
					medioTarjeta.setSolicitudTarjeta(medioDePagoModeloDTO.getSolicitudTarjeta());
				}
				MedioTarjeta managed = entityManager.merge(medioTarjeta);
				idMedioDePago = managed.getIdMedioPago();
				logger.info("Ingreso a realizar persistencia en MedioPagoPersona con los valores de:  " + medioDePagoModeloDTO.getTarjetaMultiservicio() + "  Y el valor de  "+ medioDePagoModeloDTO.getNumeroIdentificacionTitular() + "y el valors de la solicitud es:  " + medioDePagoModeloDTO.getSolicitudTarjeta());
					if (medioDePagoModeloDTO.getTarjetaMultiservicio() && !medioDePagoModeloDTO.getNumeroIdentificacionTitular().isEmpty()){
						MedioPagoPersona medioPagoPersona = new MedioPagoPersona();
						medioPagoPersona.setIdMedioPagoPersona(null);
						medioPagoPersona.setIdPersona(Long.valueOf(medioDePagoModeloDTO.getNumeroIdentificacionTitular()));
						medioPagoPersona.setIdMedioDePago(idMedioDePago);
						medioPagoPersona.setMedioActivo(Boolean.TRUE);
						medioPagoPersona.setMppTarjetaMultiservicio(Boolean.FALSE);
						String srt = String.valueOf(medioPagoPersona);
						logger.info("Este es el objeto creado para MedioDePagoPersona:   "+ srt);
						MedioPagoPersona managed2 = entityManager.merge(medioPagoPersona);
						idMedioPagoPersona = managed2.getIdMedioPagoPersona();
					}
			}
			// TODO Incluir los demás medios de pago

			medioDePagoModeloDTO.setIdMedioDePago(idMedioDePago);
			logger.debug("Finaliza el servicio guardarMedioDePago");
			return medioDePagoModeloDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio guardarMedioDePago", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarUbicacionPersona(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UbicacionDTO consultarUbicacionPersona(Long idPersona) {
		try {
			logger.debug("Inicia el servicio consultarUbicacionPersona(Long)");
			Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_POR_ID_PERSONA);
			q.setParameter("idPersona", idPersona);
			Ubicacion ubicacion = (Ubicacion) q.getSingleResult();
			UbicacionDTO ubicacionDTO = UbicacionDTO.obtenerUbicacionDTO(ubicacion);
			logger.debug("Finaliza el servicio consultarUbicacionPersona(Long)");
			return ubicacionDTO;
		} catch (NoResultException ex) {
			logger.debug("Finaliza consultarUbicacionPersona(Long): sin resultado");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio consultarUbicacionPersona(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override
	@Asynchronous
	public void ejecutarSPCoreSubsidio() {
		StoredProcedureQuery procedimiento = entityManager
					.createNamedStoredProcedureQuery(NamedQueriesConstants.MOVER_DATA_PERSONA_EXCLUSION_SUMATORIA_SALARIO);
		procedimiento.executeUpdate();
	}


	@Override
	public ActualizarExclusionSumatoriaSalarioDTO actualizarExclusionSumatoriaSalario(ActualizarExclusionSumatoriaSalarioDTO actualizarExclusionSumatoriaSalarioDTO) {
		logger.info("Inicia el servicio actualizarExclusionSumatoriaSalario");
		logger.info(actualizarExclusionSumatoriaSalarioDTO.getNovedad());
		TipoTransaccionEnum novedad = actualizarExclusionSumatoriaSalarioDTO.getNovedad();

		Boolean continuarProceso = false;
		TipoIdentificacionEnum tipoIdentificacion;
		String numeroIdentificacion;
		try {
			// Obtener ID de la persona de CORE
			if (novedad == TipoTransaccionEnum.EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL || 
				novedad == TipoTransaccionEnum.INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL) {				
				tipoIdentificacion = actualizarExclusionSumatoriaSalarioDTO.getDatosPersonaNovedadDTO().getTipoIdentificacion();
				numeroIdentificacion = actualizarExclusionSumatoriaSalarioDTO.getDatosPersonaNovedadDTO().getNumeroIdentificacion();
			}else{
				tipoIdentificacion = actualizarExclusionSumatoriaSalarioDTO.getDatosPersonaNovedadDTO().getTipoIdentificacionBeneficiario();
				numeroIdentificacion = actualizarExclusionSumatoriaSalarioDTO.getDatosPersonaNovedadDTO().getNumeroIdentificacionBeneficiario();
			}

			Long idPersona = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_PERSONA_TIPO_Y_NUM_IDENTIFICACION)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.getSingleResult();

			logger.info("ID de persona obtenido: " + idPersona);

			// Buscar o crear PersonaExclusionSumatoriaSalario
			PersonaExclusionSumatoriaSalario datosPersonaExclusionSumatoria = null;
			boolean esNuevoDato = false;
			
			try {
				// Consulta desde la tabla de PersonaExclusionSumatoriaSalario la persona
				datosPersonaExclusionSumatoria = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_SUMATORIA_SALARIOS_DETALLE, PersonaExclusionSumatoriaSalario.class)
						.setParameter("idPersona", idPersona)
						.getSingleResult();
				logger.info("Registro existente encontrado: " + datosPersonaExclusionSumatoria);
			} catch (NoResultException e) {
				logger.info("No se encontró registro existente, creando uno nuevo");
				datosPersonaExclusionSumatoria = new PersonaExclusionSumatoriaSalario();
				datosPersonaExclusionSumatoria.setIdPersona(idPersona);
				esNuevoDato = true;
			}

			// Actualizar datos según el tipo de novedad
			switch (novedad) {
				case EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL:
					datosPersonaExclusionSumatoria.setEstadoExclusion(true);
					datosPersonaExclusionSumatoria.setFechaInicioExclusion(new Date());
					datosPersonaExclusionSumatoria.setFechaFinExclusion(null);
					break;
				case EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL:
					datosPersonaExclusionSumatoria.setEstadoExclusion(true);
					datosPersonaExclusionSumatoria.setFechaInicioExclusion(new Date());
					datosPersonaExclusionSumatoria.setFechaFinExclusion(null);
					break;
				case INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL:
					datosPersonaExclusionSumatoria.setEstadoExclusion(false);
					datosPersonaExclusionSumatoria.setFechaFinExclusion(new Date());
					break;
				case INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL:
					datosPersonaExclusionSumatoria.setEstadoExclusion(false);
					datosPersonaExclusionSumatoria.setFechaFinExclusion(new Date());
					break;
				default:
					logger.warn("Tipo de novedad no reconocido: " + novedad);
			}

			// Persistir o actualizar la entidad
			if (esNuevoDato) {
				Long id = null;
				try{
					entityManager.persist(datosPersonaExclusionSumatoria);
					logger.info("Nueva entidad persistida");
				}catch(Exception e){
					logger.error("Error al persistir nueva entidad", e);
				}finally{
					EjecutarSPCoreSubsidio ejecutarSPCoreSubsidio = new EjecutarSPCoreSubsidio();
					ejecutarSPCoreSubsidio.execute();
				}	
			} else {
				entityManager.merge(datosPersonaExclusionSumatoria);
				logger.info("Entidad existente actualizada");
				EjecutarSPCoreSubsidio ejecutarSPCoreSubsidio = new EjecutarSPCoreSubsidio();
				ejecutarSPCoreSubsidio.execute();
			}
			
			logger.info("Finaliza el servicio actualizarExclusionSumatoriaSalario");
		} catch (NoResultException e) {
			logger.info("No se encontró la persona con la identificación proporcionada", e);
		} catch (Exception e) {
			logger.info("Error en el servicio actualizarExclusionSumatoriaSalario", e);
		}

		return actualizarExclusionSumatoriaSalarioDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long obtenerTrabajadoreActivosEmpleador(Long idEmpleador) {
		String firmaMetodo = "EmpleadoresBusiness.obtenerBeneficioEmpleador(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_TRABAJADORES_EMPLEADOR, Long.class)
				.setParameter("idEmpleador", idEmpleador).setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO)
				.getSingleResult();
	}

	@Override
	public List<PersonaModeloDTO> consultarDatosPersonaListNumeroIdentificacion(
			TipoIdentificacionEnum tipoIdentificacion, List<String> numeroIdentificacion) {
		logger.debug(
				"Inicia operación consultarDatosPersonaListNumeroIdentificacion(TipoIdentificacionEnum, List<String>)");
		try {
			logger.debug("Finaliza operación consultarPersonaDTO(TipoIdentificacionEnum, String)");
			return entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_TIPO_LISTA_NRO_IDENTIFICA,
							PersonaModeloDTO.class)
					.setParameter("listNumeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoIdentificacion", tipoIdentificacion).getResultList();
		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el método consultarDatosPersonaListNumeroIdentificacion(TipoIdentificacionEnum, List<String>))",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#cabeceraVista360Persona(com
	 * .asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Vista360PersonaCabeceraDTO cabeceraVista360Persona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {
		String firmaMetodo = "EmpleadoresBusiness.cabeceraVista360Persona(TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Vista360PersonaCabeceraDTO cabecera = new Vista360PersonaCabeceraDTO();

		List<Object[]> listaObject = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_CABECERA_PERSONA_VISTA360)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		for (Object[] cab : listaObject) {
			cabecera.setNumeroIdentificacion(cab[0] != null ? cab[0].toString() : null);
			cabecera.setTipoIdentificacion(cab[1] != null ? TipoIdentificacionEnum.valueOf(cab[1].toString()) : null);
			cabecera.setPrimerNombre(cab[2] != null ? cab[2].toString() : null);
			cabecera.setSegundoNombre(cab[3] != null ? cab[3].toString() : null);
			cabecera.setPrimerApellido(cab[4] != null ? cab[4].toString() : null);
			cabecera.setSegundoApellido(cab[5] != null ? cab[5].toString() : null);
			cabecera.setEstadoConRespectoCaja(
					consultarEstadoCajaPersona(tipoIdentificacion, numeroIdentificacion, null));
			cabecera.setIsIndependiente((Integer) cab[6]);
			cabecera.setIsPensionado((Integer) cab[7]);
			cabecera.setIsDependiente((Integer) cab[8]);
			cabecera.setIsEmpleador((Integer) cab[9]);
			cabecera.setIsAdministradorSubsidio((Integer) cab[10]);
			cabecera.setIsBeneficiario((Integer) cab[11]);

			// Si la persona es empleador obtener el idEmpleador
			if (cabecera.getIsEmpleador().equals(1)) {
				cabecera.setIdEmpleador(consultarIdEmpleadorPersona(tipoIdentificacion, numeroIdentificacion));
			}

			// Si la persona es beneficiario buscar los afiliados para los
			// cuales está activo
			if (cabecera.getIsBeneficiario().equals(1)) {
				cabecera.setAfiliadosPrincipalesPersona(
						consultarAfiliadosPrincipalesPersona(tipoIdentificacion, numeroIdentificacion));
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return cabecera;
	}

	/**
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private Long consultarIdEmpleadorPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		String firmaMetodo = "EmpleadoresBusiness.consultarIdEmpleadrPersona(TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		BigInteger idEmpleador = null;

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		try {
			idEmpleador = (BigInteger) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_EMPLEADOR_PERSONA)
					.setParameter("tipoIdentificacion", tipoIdentificacion.name())
					.setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();
		} catch (NoResultException e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return null;
		} catch (NonUniqueResultException e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
		} catch (Exception e) {
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		return idEmpleador.longValue();

	}

	/**
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<Vista360PersonaAfiliadoPrincipalDTO> consultarAfiliadosPrincipalesPersona(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		String firmaMetodo = "EmpleadoresBusiness.consultarIdEmpleadrPersona(TipoIdentificacionEnum, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<Vista360PersonaAfiliadoPrincipalDTO> afiliados = new ArrayList<>();

		List<Object[]> listaObject = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_PERSONA_BENEFICIARIO)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		for (Object[] afi : listaObject) {
			Vista360PersonaAfiliadoPrincipalDTO afiliado = new Vista360PersonaAfiliadoPrincipalDTO();
			afiliado.setNombreCompletoAfiliado(afi[0] != null ? afi[0].toString() : null);
			afiliado.setEstadoAfiliadoBeneficiario(
					afi[1] != null ? EstadoAfiliadoEnum.valueOf(afi[1].toString()) : null);
			afiliado.setParentezco(afi[2] != null ? ClasificacionEnum.valueOf(afi[2].toString()) : null);
			afiliado.setNumeroIdentificacionAfiliado(afi[3] != null ? afi[3].toString() : null);
			afiliado.setTipoIdentificacionAfiliado(
					afi[4] != null ? TipoIdentificacionEnum.valueOf(afi[4].toString()) : null);

			afiliados.add(afiliado);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return afiliados;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#consultarAdministradorSubsidioGeneral(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<AdministradorSubsidioModeloDTO> consultarAdministradorSubsidioGeneral(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido) {
		String firmaServicio = "PersonasBusiness.consultarAdministradorSubsidioGeneral(TipoIdentificacionEnum,String,String,String,String,String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		List<AdministradorSubsidioModeloDTO> listaAdministradores = null;

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
			listaAdministradores = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ADMINISTRADORES_SUBSIDIO_GENERAL,
							AdministradorSubsidioModeloDTO.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("primerNombre", parametroPrimerNombre)
					.setParameter("segundoNombre", parametroSegundoNombre)
					.setParameter("primerApellido", parametroPrimerApellido)
					.setParameter("segundoApellido", parametroSegundoApellido).getResultList();
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en " + firmaServicio, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return listaAdministradores.isEmpty() ? null : listaAdministradores;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.personas.service.PersonasService#obtenerInfoDetalladaBeneficiarioGrupo(java.lang.Long,
	 *      com.asopagos.enumeraciones.core.ClasificacionEnum,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public DetalleBeneficiarioGrupoFamiliarDTO obtenerInfoDetalladaBeneficiarioGrupo(Long idGrupo,
			ClasificacionEnum parentezco,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		String firmaMetodo = "PersonasBusiness.obtenerInfoDetalladaBeneficiarioGrupo(" + idGrupo + ", " + parentezco
				+ ", "
				+ tipoIdentificacion + ", " + numeroIdentificacion + ")";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		return entityManager
				.createNamedQuery(NamedQueriesConstants.OBTENER_INFO_DETALLADA_BENEFICIARIO_GRUPO,
						DetalleBeneficiarioGrupoFamiliarDTO.class)
				.setParameter("tipoIdentificacion", tipoIdentificacion.name())
				.setParameter("numeroIdentificacion", numeroIdentificacion)
				.setParameter("idGrupo", idGrupo).setParameter("tipoBeneficiario", parentezco.name()).getSingleResult();
	}

	@Override
	public Ubicacion360DTO consultarUbicacionPersona360(Long idPersona) {
		try {
			logger.debug("Inicia el servicio consultarUbicacionPersona(Long)");

			Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_POR_ID_PERSONA);
			q.setParameter("idPersona", idPersona);
			Ubicacion ubicacion = (Ubicacion) q.getSingleResult();
			Ubicacion360DTO ubicacionDTO = Ubicacion360DTO.obtenerUbicacionDTO(ubicacion);

			logger.debug("Finaliza el servicio consultarUbicacionPersona(Long)");
			return ubicacionDTO;
		} catch (NoResultException ex) {
			logger.debug("Finaliza consultarUbicacionPersona(Long): sin resultado");
			return null;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio consultarUbicacionPersona(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.personas.service.PersonasService#consultarAportantesCaja(com
	 * .asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum,
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaDTO> consultarAportantesCaja(TipoSolicitanteMovimientoAporteEnum tipoAportante,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial,
			String primerNombre, String segundoNombre, String primerApellido, String segundoApellido) {
		try {
			logger.debug("Inicia el servicio consultarAportantesCaja");
			List<PersonaDTO> lista = new ArrayList<PersonaDTO>();

			razonSocial = razonSocial != null ? '%' + razonSocial + '%' : razonSocial;
			primerNombre = primerNombre != null ? '%' + primerNombre + '%' : primerNombre;
			segundoNombre = segundoNombre != null ? '%' + segundoNombre + '%' : segundoNombre;
			primerApellido = primerApellido != null ? '%' + primerApellido + '%' : primerApellido;
			segundoApellido = segundoApellido != null ? '%' + segundoApellido + '%' : segundoApellido;

			if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoAportante)) {
				lista = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_APORTANTE_EMPLEADOR_CAJA,
								PersonaDTO.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("razonSocial", razonSocial).getResultList();
			} else {
				lista = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_APORTANTE_PERSONA_CAJA,
								PersonaDTO.class)
						.setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("primerNombre", primerNombre).setParameter("segundoNombre", segundoNombre)
						.setParameter("primerApellido", primerApellido).setParameter("segundoApellido", segundoApellido)
						.setParameter("tipoAfiliado",
								TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoAportante)
										? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
										: TipoAfiliadoEnum.PENSIONADO)
						.getResultList();
			}

			logger.debug("Finaliza el servicio consultarAportantesCaja");
			return lista;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarAportantesCaja");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override
	public void registrarHistoricoActivacionAcceso(HistoricoActivacionAccesoModeloDTO activacionAcceso) {
		logger.debug("Se inicia el servicio de registrarPersonasDetalle(activacionAcceso)");
		try {
			List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
			Persona nueva = activacionAcceso.getPersonaCreada();
			if (nueva.getIdPersona() != null) {

				entityManager.persist(activacionAcceso.convertToEntity());
			} else {
				personas = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION,
								PersonaDTO.class)
						.setParameter("tipoIdentificacion", nueva.getTipoIdentificacion())
						.setParameter("numeroIdentificacion", nueva.getNumeroIdentificacion()).getResultList();
				if (personas.isEmpty()) {
					PersonaModeloDTO personaInsertar = new PersonaModeloDTO(nueva);
					CrearPersona(personaInsertar);
					personas = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION,
									PersonaDTO.class)
							.setParameter("tipoIdentificacion", personaInsertar.getTipoIdentificacion())
							.setParameter("numeroIdentificacion", personaInsertar.getNumeroIdentificacion())
							.getResultList();
					if (!personas.isEmpty()) {
						activacionAcceso.getPersonaCreada().setIdPersona(personas.get(0).getIdPersona());
						entityManager.persist(activacionAcceso.convertToEntity());
					} else {
						logger.debug(
								"Finaliza el servicio de registrarPersonasDetalle(List<PersonaDetalleModeloDTO>) No se pudo persistir la persona");

					}
				} else {
					entityManager.persist(activacionAcceso.convertToEntity());
				}
			}
			logger.debug("Finaliza el servicio de registrarPersonasDetalle(List<PersonaDetalleModeloDTO>)");

		} catch (Exception e) {
			logger.error(
					"Ocurrió un error inesperado en el servicio registrarHistoricoActivacionAcceso(activacionAcceso)",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override
	public List<HistoricoActivacionAccesoModeloDTO> consultarHistoricoActivacion(Long id) {

		try {
			logger.debug("Inicia el servicio consultarAportantesCaja");
			List<HistoricoActivacionAccesoModeloDTO> lista = new ArrayList<HistoricoActivacionAccesoModeloDTO>();
			List<HistoricoActivacionAccesoModeloDTO> listaFinal = new ArrayList<HistoricoActivacionAccesoModeloDTO>();
			PersonaModeloDTO persona = consultarPersona(id);
			Persona consulta = persona.convertToPersonaEntity();

			lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ACTIVACION,
					HistoricoActivacionAccesoModeloDTO.class).setParameter("id", consulta).getResultList();

			logger.debug("Finaliza el servicio consultarAportantesCaja");
			return lista;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarAportantesCaja");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	@Override
	public PaisDTO obtenerNombrePais(Long idPais) {

		PaisDTO pais = new PaisDTO();
		try {
			String nombrePais = "";
			logger.debug("Inicia el servicio obtenerNombrePais");

			nombrePais = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_PAIS)
					.setParameter("idPais", idPais).getSingleResult();

			logger.debug("Finaliza el servicio obtenerNombrePais");
			pais.setNombre(nombrePais);
			return pais;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio obtenerNombrePais");
			pais.setNombre("");
			return pais;
		}

	}

	@Override
	public DetalleBeneficiarioDTO consultarBeneficiario(String tipoIdentificacion, String numeroIdentificacion) {
		logger.debug("Inicia el servicio obtenerNombrePais");

		DetalleBeneficiarioDTO ben = new DetalleBeneficiarioDTO();

		try {
			Object[] datosBeneficiario = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_BENEFICIARIO)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.getSingleResult();
			ben.setIdBeneficiario(datosBeneficiario[0] != null ? Long.valueOf(datosBeneficiario[0].toString()) : null);
			ben.setIdGrupoFamiliar(datosBeneficiario[1] != null ? Long.valueOf(datosBeneficiario[1].toString()): null);
		} catch (Exception e) {
			logger.info("Dato de beneficiario no encontrado" + e);
			ben.setIdBeneficiario(null);
		}

		return ben;

	}

	@Override
	public ConsultarPersonaProcesoOffcoreDTO consultarPersonaProcesoOffcore(String tipoIdentificacion, String numeroIdentificacion) {
		logger.info("Inicia el servicio consultarPersonaProcesoOffcore");

		ConsultarPersonaProcesoOffcoreDTO per = new ConsultarPersonaProcesoOffcoreDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String ciudadCcf = (String) CacheManager.getParametro(ParametrosSistemaConstants.CIUDAD_CCF);
        String departamentoCcf = (String) CacheManager.getParametro(ParametrosSistemaConstants.DEPARTAMENTO_CCF);
        String direccionCcf = (String) CacheManager.getParametro(ParametrosSistemaConstants.DIRECCION_CCF);
        String telefonoCcf = (String) CacheManager.getParametro(ParametrosSistemaConstants.TELEFONO_CCF);
		String correoCcf = (String) CacheManager.getParametro(ParametrosSistemaConstants.CORREO_CCF);

		try {
			Object[] datosPersona = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_PROCESO_OFFCORE)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.getSingleResult();
					
			//String tipoIdentificacionAnibol = com.asopagos.anibol.enums.TipoIdentificacionEnum.valueOf(tipoIdentificacion).getTipoIdentificacion();
			per.setIdTipoDcto(datosPersona[0] != null ? datosPersona[0].toString() : null);
			per.setNumIdentificacion(datosPersona[1] != null ? datosPersona[1].toString() : null);
			per.setPrimerApellido(datosPersona[2] != null ? datosPersona[2].toString() : null);
			per.setSegundoApellido(datosPersona[3] != null ? datosPersona[3].toString() : null);
			per.setPrimerNombre(datosPersona[4] != null ? datosPersona[4].toString() : null);
			per.setSegundoNombre(datosPersona[5] != null ? datosPersona[5].toString() : null);
			if (datosPersona[6] != null) {
				try {
					Date fechaNacimiento = (Date) datosPersona[6];
					per.setFechaNacimiento(sdf.format(fechaNacimiento));
				} catch (Exception e) {
					logger.info("Error al formatear la fecha de nacimiento: " + e);
					per.setFechaNacimiento("19000101");
				}
			} else {
				per.setFechaNacimiento("19000101");
			}
			per.setIdSexo(datosPersona[7] != null ? datosPersona[7].toString() : "I");
			per.setIdEstadoCivil(datosPersona[8] != null ? datosPersona[8].toString() : "1");
			per.setDireccion(datosPersona[9] != null ? datosPersona[9].toString() : direccionCcf);
			per.setCodDepartamentoResidencia(datosPersona[10] != null ? datosPersona[10].toString() : departamentoCcf);
			per.setCodCiudadResidencia(datosPersona[11] != null ? datosPersona[11].toString() : ciudadCcf);
			per.setTelefono(datosPersona[12] != null ? datosPersona[12].toString() : telefonoCcf);
			per.setCorreo(datosPersona[13] != null ? datosPersona[13].toString() : correoCcf);
			per.setCelular(datosPersona[14] != null ? datosPersona[14].toString() : per.getTelefono());
	
		} catch (Exception e) {
			logger.info("Dato de la persona no encontrados" + e);
		}
		return per;
	}

	@Override
	public PaisDTO consultarPaisPorCodigo(Long codigoPais) {
		logger.debug("Inicia operación consultarPaisPorCodigo(Long)");
		PaisDTO paisDTO = new PaisDTO();
		try {
			paisDTO = (PaisDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PAIS_POR_CODIGO)
					.setParameter("codigoPais", codigoPais).getSingleResult();
			if (paisDTO.getId() == null) {
				paisDTO = null;
			}
		} catch (NoResultException e) {
			logger.debug("Finaliza consultarPaisPorCodigo(Long): No existe el Pais");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el método consultarPaisPorCodigo(Long)", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		logger.debug("Finaliza operación consultarPaisPorCodigo(Long)");
		return paisDTO;
	}

	@Override
	public RegistroDespliegueAmbienteDTO consultarVersionamiento(){
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
            String nuevoDespliegue = resourceBundle.getString("fecha.compilacion");
			if(nuevoDespliegue != null || !nuevoDespliegue.isEmpty()){
				SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
				Date fechaCompilacion = formateador.parse(nuevoDespliegue);
				String rama = resourceBundle.getString("deployd.branch");
				entityManager.createNamedQuery(NamedQueriesConstants.PERSISTIR_DESPLIEGUE_COMPLETO)
    				.setParameter("fechaDespliegue", fechaCompilacion)
    				.setParameter("ramaDespliegue", rama)
    				.executeUpdate();
				RegistroDespliegueAmbienteDTO registro = new RegistroDespliegueAmbienteDTO(fechaCompilacion,resourceBundle.getString("deployd.branch"));
				return registro;
			}
        } catch (MissingResourceException mre) {
            System.out.println("Prueba propiedades falló");
        }catch (Exception e){
			System.out.println("Prueba propiedades falló");
		}
		
		RegistroDespliegueAmbienteDTO registro = (RegistroDespliegueAmbienteDTO) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESPLIEGUE_COMPLETO).getSingleResult();
		return registro;
	}

	@Override
	public MedioDePagoModeloDTO registrarTarjetaExpedicion(MedioDePagoModeloDTO medioDePagoModeloDTO) {
		try {
			logger.debug("Se inicia el servicio registrarTarjetaExpedicion" + medioDePagoModeloDTO.toString());
			Long idMedioDePago = null;

			// Usa MedioTarjeta en lugar de MedioDePago ya que MedioDePago es abstracto
			MedioTarjeta medioTarjeta = new MedioTarjeta();
			medioTarjeta.setTipoMediopago(medioDePagoModeloDTO.getTipoMedioDePago());
			medioTarjeta.setIdMedioPago(null);
			medioTarjeta.setNumeroTarjeta(medioDePagoModeloDTO.getNumeroTarjeta());
			medioTarjeta.setDisponeTarjeta(medioDePagoModeloDTO.getDisponeTarjeta());
			medioTarjeta.setEstadoTarjetaMultiservicios(medioDePagoModeloDTO.getEstadoTarjetaMultiservicios());

			String solicitudTarjeta = String.valueOf(medioDePagoModeloDTO.getSolicitudTarjeta());
			if (solicitudTarjeta.isEmpty()) {
				medioTarjeta.setSolicitudTarjeta(SolicitudTarjetaEnum.EXPEDICION);
			} else {
				medioTarjeta.setSolicitudTarjeta(medioDePagoModeloDTO.getSolicitudTarjeta());
			}

			logger.warn("**--previo merge tarjeta");
			logger.warn(medioTarjeta.toString());

			MedioTarjeta managedMedioTarjeta = entityManager.merge(medioTarjeta);
			idMedioDePago = managedMedioTarjeta.getIdMedioPago();
			medioDePagoModeloDTO.setIdMedioDePago(idMedioDePago);

			return medioDePagoModeloDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio registrarTarjetaExpedicion", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}


	@Override
	public void reemplazarMedioDePagoGrupoFamiliar(List<Long> idGruposFamiliares,Long idMedioPagoTarjeta){
		if(!idGruposFamiliares.isEmpty() && idGruposFamiliares.size() > 0){
			for(Long idGrupo : idGruposFamiliares){
				// primero consultamos el admin SubsidioGrupo de cada grupo familiar
				List<AdminSubsidioGrupo> listaAdminSubsidio = (List<AdminSubsidioGrupo>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_ADMIN_SUBSIDIO_DE_GRUPO_CON_MARCA)
					.setParameter("idGrupo",idGrupo).getResultList();
				for(AdminSubsidioGrupo admin : listaAdminSubsidio){
					admin.setIdMedioDePago(idMedioPagoTarjeta);
					entityManager.merge(admin);
				}
			}
		}

	}

	@Override
	public void registrarActualizacionTarjetaGrupoFamiliar(Long idGrupo, MedioDePagoModeloDTO medioDePagoModeloDTO){
		if(medioDePagoModeloDTO.getIdMEdioDePagoActualizar() != null){
			List<AdminSubsidioGrupo> listaAdminSubsidio = (List<AdminSubsidioGrupo>) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_ADMIN_SUBSIDIO_DE_GRUPO_CON_MARCA)
					.setParameter("idGrupo",idGrupo).getResultList();
			for(AdminSubsidioGrupo admin : listaAdminSubsidio){
				admin.setIdMedioDePago(medioDePagoModeloDTO.getIdMEdioDePagoActualizar());
				entityManager.merge(admin);
			}
		}
	}

	public void ejecutarSp(){
		StoredProcedureQuery procedimiento = entityManager
				.createNamedStoredProcedureQuery(NamedQueriesConstants.MOVER_DATA_PERSONA_EXCLUSION_SUMATORIA_SALARIO);
		procedimiento.executeUpdate();
		procedimiento.executeUpdate();
		logger.info("Ejecutado");

	}

}
