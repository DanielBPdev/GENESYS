package com.asopagos.usuarios.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ClientErrorException;
import javax.persistence.NoResultException;
import java.time.Instant;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rest.security.util.PreguntasSeguridadUtil;
import com.asopagos.tareashumanas.clients.ObtenerTareasAsignadasUsuario;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.KeyCloakRestClient;
import com.asopagos.usuarios.clients.KeyCloakRestClientFactory;
import com.asopagos.usuarios.constants.GruposUsuariosEnum;
import com.asopagos.usuarios.constants.UserAttributesEnum;
import com.asopagos.usuarios.dto.CambiarContrasenaDTO;
import com.asopagos.usuarios.dto.GrupoDTO;
import com.asopagos.usuarios.dto.InformacionReenvioDTO;
import com.asopagos.usuarios.dto.PreguntaUsuarioDTO;
import com.asopagos.usuarios.dto.ResultadoDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.usuarios.dto.UsuarioGestionDTO; //Nuevo importDTO
import com.asopagos.usuarios.dto.UsuarioEmpleadorDTO;
import com.asopagos.usuarios.dto.UsuarioTemporalDTO;
import com.asopagos.usuarios.mapper.KeyCloakMapper;
import com.asopagos.usuarios.mapper.KeycloakAdapter;
import com.asopagos.usuarios.mapper.KeycloakClientFactory;
import com.asopagos.usuarios.service.IPreguntasPersistenceServices;
import com.asopagos.usuarios.service.UsuariosService;
import com.asopagos.usuarios.utils.SecurePasswordGenerator;
import com.asopagos.usuarios.constants.NamedQueriesConstants;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import com.asopagos.util.CalendarUtils;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.BadRequestException;
import com.asopagos.usuarios.dto.CambiarContrasenaGestionUsuarioDTO;
import com.asopagos.usuarios.dto.DatosConsultaGestionUsuariosDTO;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.asopagos.util.DesEncrypter;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import java.util.Optional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import org.keycloak.admin.client.resource.UserResource;
import java.net.URI;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
/**
 *
 */
@Stateless
public class UsuariosBusiness implements UsuariosService {

	private static final String PREFIJO_EMPLEADOR = "emp_";

	private static final String USUARIO_REGISTRADO_MENSAJE = "El usuario y/o email ya está registrado";

	private static final String USUARIO_TEMPORAL_REGISTRADO_MENSAJE = "El usuario temporal y/o email ya está registrado y estan dentro del rango de acceso permitido";

	private static final String USUARIO_NO_REGISTRADO_MENSAJE = "El usuario no está registrado";

	private static final String NO_INHABILITAR_USUARIO_REGISTRADO_MENSAJE = "El usuario no se puede inhabilitar ya que tiene tareas pendientes por realizar";

	private static final String UPDATE_PASSWORD = "Update Password";

	private static final String PREFIJO_PREG = "PREG_";

	private static final Long DIAS_TO_MILIS = 86400000L;

	/**
	 * Referencia a la unidad de persistencia del servicio
	 */
	@PersistenceContext(unitName = "usuarios_PU")
	private EntityManager entityManager;

	@Inject
	private IPreguntasPersistenceServices preguntasService;

	@Inject
    private HttpServletRequest request;

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(UsuariosBusiness.class);

	//Variables Para GLPI 95241
	// Clave Secreta JWT
    private String JWT_SECRET; 
    // Tiempo de expiración del token
    private long JWT_EXPIRATION_MS;
	// Es la URL base del frontend donde el usuario va a cambiar la contraseña
    private String FRONTEND_RESET_PASSWORD_URL;
	// Instancias de los servicios
    private PasswordResetTokenService passwordResetTokenService;
    private EmailService emailService;

    @PostConstruct
    public void init() {
        logger.info("Inicializando UsuariosBusiness EJB y cargando parámetros de correo.");

        try {
            String smtpHost = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_HOST);
            String smtpPortStr = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_PORT);
            String smtpUser = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_USER);
            String smtpPasswordEncypted = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_PASSWORD);
            String smtpFrom = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_FROM);

            String smtpPassword = DesEncrypter.getInstance().decrypt(smtpPasswordEncypted);

            int smtpPort = Integer.parseInt(smtpPortStr);

            this.emailService = new EmailService(smtpHost, smtpPort, smtpUser, smtpPassword, smtpFrom);

			this.JWT_SECRET = (String) CacheManager.getParametro(ParametrosSistemaConstants.JWT_SECRET);
            String jwtExpirationValue = (String) CacheManager.getParametro(ParametrosSistemaConstants.JWT_EXPIRATION_MS);
            this.JWT_EXPIRATION_MS = parseExpirationTimeToMillis(jwtExpirationValue);
            this.FRONTEND_RESET_PASSWORD_URL = (String) CacheManager.getParametro(ParametrosSistemaConstants.FRONTEND_RESET_PASSWORD_URL);

            this.passwordResetTokenService = new PasswordResetTokenService(JWT_SECRET, JWT_EXPIRATION_MS);

            logger.info("Servicios de correo y token inicializados correctamente.");

        } catch (Exception e) {
            logger.error("Error durante la inicialización de EmailService o PasswordResetTokenService: " + e.getMessage(), e);
            throw new RuntimeException("Fallo en la inicialización de servicios de correo/token.", e);
        }
    }

    // Nuevo método para parsear el tiempo de expiración
    private long parseExpirationTimeToMillis(String timeValue) {
        if (timeValue == null || timeValue.isEmpty()) {
            logger.warn("JWT_EXPIRATION_MS no encontrado o vacío. Usando 15 minutos (900000ms) por defecto.");
            return 900000L;
        }

        timeValue = timeValue.trim().toLowerCase();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)([smhd]?)");
        java.util.regex.Matcher matcher = pattern.matcher(timeValue);

        if (matcher.matches()) {
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "s": return value * 1000L; 
                case "m": return value * 60 * 1000L; 
                case "h": return value * 60 * 60 * 1000L; 
                case "d": return value * 24 * 60 * 60 * 1000L; 
                case "":  return value;
                default:
                    logger.warn("Unidad de tiempo desconocida para JWT_EXPIRATION_MS: " + unit + ". Usando 15 minutos (900000ms) por defecto.");
                    return 900000L;
            }
        } else {
            try {
                return Long.parseLong(timeValue);
            } catch (NumberFormatException e) {
                logger.error("Formato inválido para JWT_EXPIRATION_MS: " + timeValue + ". Usando 15 minutos (900000ms) por defecto.");
                return 900000L;
            }
        }
    }

	/**
	 * <b>Descripción</b>Método que se encarga de obtener usuarios de una caja
	 * de compensación familiar
	 *
	 * * @return se retorna la lista con los usuarios obtenidos
	 *
	 */
	public UsuarioCCF obtenerDatosUsuarioCajaCompensacion(String nombreUsuario, Boolean roles, String primerNombre,
														  String primerApellido) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation user = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (user != null) {
			if (primerNombre != null && !primerNombre.isEmpty()
					&& user.getFirstName() != null && !user.getFirstName().trim().toLowerCase().contains(primerNombre.trim().toLowerCase())) {
				return null;
			}
			if (primerApellido != null && !primerApellido.isEmpty()
					&& user.getLastName() != null && !user.getLastName().trim().toLowerCase().contains(primerApellido.trim().toLowerCase())) {
				return null;
			}
			UsuarioCCF usuario = KeyCloakMapper.toUsuarioCCF(user);
			if (roles) {
				usuario.setGrupos(obtenerGruposUsuario(adapter, user.getId()));
			}
			return usuario;
		}
		return null;
	}

	private List<String> obtenerGruposUsuario(KeycloakAdapter adapter, String id) {
		List<String> nombreGrupos = new ArrayList<>();
		List<GroupRepresentation> grupos = adapter.getKc().realm(adapter.getRealm()).users().get(id).groups();
		for (GroupRepresentation groupRepresentation : grupos) {
			nombreGrupos.add(groupRepresentation.getId());
		}
		return nombreGrupos;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void crearUsuarioAdminEmpleador(UsuarioEmpleadorDTO user) {
		logger.info("entra metodo crear usuarioAdmin" + user);
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		logger.info("entra metodo crear usuarioAdmin2^^^" + adapter );
		final String prefijoEmpleador = PREFIJO_EMPLEADOR;
		logger.info("entra metodo crear usuarioAdmin3^^^" + prefijoEmpleador );
		String username = prefijoEmpleador + user.getTipoIdentificacion() + "_" + user.getNumIdentificacion();
		logger.info("entra metodo crear usuarioAdmin3^^^" + username);
		UserRepresentation admon = KeyCloakMapper.toUserRepresentation(user);
		logger.info("entra metodo crear usuarioAdmin4^^^" + admon);
		if (!validarExistenciaUsuario(username, adapter, kc)) {
			logger.info("entra if5^^^" + username );
			logger.info("entra if6^^^" + adapter);
			logger.info("entra if54446^^^" + kc);
			admon.setUsername(prefijoEmpleador + user.getTipoIdentificacion() + "_" + user.getNumIdentificacion());
			admon.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
			HashMap<String, Object> attrs = new HashMap<>();
			attrs.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(user.getEmail()));
			attrs.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(), Arrays.asList(String.valueOf(Boolean.TRUE)));
			attrs.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
					Arrays.asList(String.valueOf(Boolean.TRUE)));
			logger.info("entra i787887876^^^" + admon);
			if (user.getTipoIdentificacion() != null && !user.getTipoIdentificacion().equals("")) {
				attrs.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
						Arrays.asList(user.getTipoIdentificacion()));
			}
			if (user.getNumIdentificacion() != null && !user.getNumIdentificacion().equals("")) {
				attrs.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
						Arrays.asList(user.getNumIdentificacion()));
			}
			if (user.getSegundoNombre() != null && !user.getSegundoNombre().isEmpty()) {
				attrs.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(user.getSegundoNombre()));
			}
			if (user.getSegundoApellido() != null && !user.getSegundoApellido().isEmpty()) {
				attrs.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(user.getSegundoApellido()));
			}
			admon.setAttributes(attrs);
			Response response = kc.realm(adapter.getRealm()).users().create(admon);
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
				logger.error(response.getStatusInfo());
				throw new TechnicalException(response.getEntity() == null ? null : response.getEntity().toString());
			}
			logger.info("entra if7^^^");
			// se adiciona por defecto el grupo administrador
			agregarAGrupo(GruposUsuariosEnum.ADMINISTRADOR_EMPLEADOR.getNombre(), admon.getUsername(), adapter, kc);

			// se adiciona por defecto al grupo funcionario empleador
			logger.info("entra if888^^^");
			agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_EMPLEADOR.getNombre(), admon.getUsername(), adapter, kc);
			logger.info("entra if9999^^^");
			String tempPass = asignarPasswordTemporal(admon.getUsername(), adapter, kc);
			// se envia correo notificando la creación del usuario
			logger.info("entra if955555^^^" + tempPass);
			Map<String, String> params = new HashMap<>();
			params.put("nombreUsuario", obtenerNombreUsuario(user.getPrimerNombre(), user.getSegundoNombre(),
					user.getPrimerApellido(), user.getSegundoApellido()));
			params.put("usuario", admon.getUsername());
			params.put("Password", tempPass);
			params.put("idSolicitud", user.getIdSolicitudGlobal().toString());
			logger.info("entra if80000^^^" + params);
			enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_EXT, user.getEmail());
			logger.info("entra if8111^^^");
		} else {
			logger.info("entra else91111^^^");
			if (!estaUsuarioActivo(username)) {
				desbloquearCuentaUsuario(username, null);
			} else {
				logger.info(USUARIO_REGISTRADO_MENSAJE);
			}
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String crearUsuarioAdminEmpleadorMasivo(UsuarioEmpleadorDTO user) {
		String respuesta ="";
		logger.debug("MASIVO entra metodo crear crearUsuarioAdminEmpleadorMasivo");
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		final String prefijoEmpleador = PREFIJO_EMPLEADOR;
		String username = prefijoEmpleador + user.getTipoIdentificacion() + "_" + user.getNumIdentificacion();
		if(user.getTipoIdentificacion() != "NIT"){
			if( user.getTipoIdentificacion().equals("CEDULA_CIUDADANIA")){
			username="emp_cc_"+ user.getNumIdentificacion();
			}else if(user.getTipoIdentificacion().equals("CEDULA_EXTRANJERIA")){
				username="emp_ce_"+ user.getNumIdentificacion();
			}else if(user.getTipoIdentificacion().equals("PASAPORTE")){
				username="emp_pa_"+ user.getNumIdentificacion();
			}else if(user.getTipoIdentificacion().equals("PERM_PROT_TEMPORAL")){
				username="emp_pt_"+ user.getNumIdentificacion();
			}
		}
		
		UserRepresentation admon = KeyCloakMapper.toUserRepresentation(user);
		if(user.getTipoIdentificacion() == "NIT"){
			admon.setUsername(prefijoEmpleador + user.getTipoIdentificacion() + "_" + user.getNumIdentificacion());
		}else{
			admon.setUsername(username);
		}
		String tempPass = "c6X0duiJ*";
		if (!validarExistenciaUsuario(username, adapter, kc)) {
			admon.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
			HashMap<String, Object> attrs = new HashMap<>();
			if(user.getEmail() !=null){
				attrs.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(user.getEmail()));
			}
			attrs.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(), Arrays.asList(String.valueOf(Boolean.TRUE)));
			attrs.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
					Arrays.asList(String.valueOf(Boolean.TRUE)));
			if (user.getTipoIdentificacion() != null && !user.getTipoIdentificacion().equals("")) {
				attrs.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
						Arrays.asList(user.getTipoIdentificacion()));
			}
			if (user.getNumIdentificacion() != null && !user.getNumIdentificacion().equals("")) {
				attrs.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
						Arrays.asList(user.getNumIdentificacion()));
			}
			if (user.getSegundoNombre() != null && !user.getSegundoNombre().isEmpty()) {
				attrs.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(user.getSegundoNombre()));
			}
			if (user.getSegundoApellido() != null && !user.getSegundoApellido().isEmpty()) {
				attrs.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(user.getSegundoApellido()));
			}
			admon.setAttributes(attrs);
			Response response = kc.realm(adapter.getRealm()).users().create(admon);
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
				logger.error(response.getStatusInfo());
				throw new TechnicalException(response.getEntity() == null ? null : response.getEntity().toString());
			}
			// se adiciona por defecto el grupo administrador
			agregarAGrupo(GruposUsuariosEnum.ADMINISTRADOR_EMPLEADOR.getNombre(), admon.getUsername(), adapter, kc);
			// se adiciona por defecto al grupo funcionario empleador
			agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_EMPLEADOR.getNombre(), admon.getUsername(), adapter, kc);
			 //asignarPasswordTemporal(admon.getUsername(), adapter, kc);
			// se envia correo notificando la creación del usuario
			tempPass = asignarPasswordTemporalMasivo(admon.getUsername(),tempPass, adapter, kc);
			Map<String, String> params = new HashMap<>();
			params.put("nombreUsuario", obtenerNombreUsuario(user.getPrimerNombre(), user.getSegundoNombre(),
					user.getPrimerApellido(), user.getSegundoApellido()));
			params.put("usuario", admon.getUsername());
			params.put("Password", tempPass);
			params.put("idSolicitud", user.getIdSolicitudGlobal().toString());
			
			//enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_EXT, user.getEmail());
			respuesta=String.valueOf("CREADO"+"|"+user.getEmail() == null ? "SIN_CORREO"  :user.getEmail()+"|"+user.getPrimerApellido()+"|"+user.getPrimerNombre()+"|"+user.getTipoIdentificacion()
			+"|"+user.getNumIdentificacion()+"|"+user.getIdSolicitudGlobal()+"|"+admon.getUsername()+"|"+tempPass);
		} else {
			String desbloqueo="";
			if (!estaUsuarioActivo(username)) {
				desbloquearCuentaUsuario(username, null);
				// se envia correo notificando la creación del usuario
				tempPass = asignarPasswordTemporalMasivo(admon.getUsername(),tempPass, adapter, kc);
				desbloqueo="NO_CREADO_USUARIO_EXISTENTE_DESBLOQUEADO|CLAVE_RESTABLECIDA:"+tempPass;
			} else {
				tempPass = asignarPasswordTemporalMasivo(admon.getUsername(),tempPass, adapter, kc);
				desbloqueo="NO_CREADO_USUARIO_EXISTENTE|CLAVE_RESTABLECIDA:"+tempPass;
				logger.info(USUARIO_REGISTRADO_MENSAJE);
			}
			respuesta=String.valueOf(desbloqueo+"|"+user.getEmail()+"|"+user.getPrimerApellido()+"|"+user.getPrimerNombre()+"|"+user.getTipoIdentificacion()
			+"|"+user.getNumIdentificacion()+"|"+user.getIdSolicitudGlobal()+"|"+admon.getUsername()+"|");
		}
		return respuesta;
	}
	private String asignarPasswordTemporalMasivo(String userName, String tempPass ,KeycloakAdapter adapter, Keycloak kc) {
		logger.info("Busqueda de usuario por userName");
		UserRepresentation userRep = buscarUsuarioPorUsername(userName, adapter, kc);
		logger.info("Busqueda de usuario por userName222" + userRep);
		if (userRep != null) {
			logger.info("Busqueda de usuario por userName333 " );
			/*Integer numCharacters = Integer.valueOf(
					(String) CacheManager.getConstante(ConstantesSistemaConstants.SEC_INITIAL_CHARACTERS_PASSWORD));
			logger.info("Busqueda de usuario por userName555" + numCharacters );*/
			CredentialRepresentation credenciales = new CredentialRepresentation();
			credenciales.setType(CredentialRepresentation.PASSWORD);
			//String tempPass = SecurePasswordGenerator.generatePassword(numCharacters);
			credenciales.setValue(tempPass);
			credenciales.setTemporary(true);
			logger.info("Realm : " + adapter.getRealm() + " User : " + userRep.getId() + " Key : " + tempPass);
			try{
				logger.info("**::__** ingreso a actualizar contraseña en Keycloak");
				kc.realm(adapter.getRealm()).users().get(userRep.getId()).resetPassword(credenciales);
				logger.info("**::__** finaliza  actualizar contraseña en Keycloak");
			}catch(Exception e ){
				logger.info("**::__** Error kc.realm(adapter.getRealm()).users().get(userRep.getId())"+
				".resetPassword(credenciales) no asigno contraseña temporal" + tempPass +" Error: "+e);
			}
			
			return tempPass;
		}
		return null;
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void reasignarContrasena(UsuarioEmpleadorDTO user) {
		logger.info("Se obtiene adaptador de keycloak");
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		logger.info("Validando existencia de usuario");
		UserRepresentation admon = KeyCloakMapper.toUserRepresentation(user);
		if (validarExistenciaUsuario(user.getNombreUsuario(), adapter, kc)) {
			logger.info("Asingnando Password Temporal");
			String tempPass = asignarPasswordTemporal(user.getNombreUsuario(), adapter, kc);
			if(tempPass != null){
				Map<String, String> params = new HashMap<>();
				params.put("nombreUsuario", admon.getUsername());
				params.put("contrasenia", tempPass);
				logger.info("Enviando notificación a: " + user.getEmail());
				enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_RES_CTRS, user.getEmail());
				logger.info("Notificación enviada");
			}else{
				logger.info("No se pudo asignar un password temporal");
			}
		}else{
			logger.info("Usuario no encontrado: " + admon.getUsername());
		}
	}


	@Override
	public boolean estaUsuarioActivo(String nombreUsuario) {
		if (!nombreUsuario.equals("")) {
			final int maxLimit = 100;
			final int minLimit = 0;
			KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
					.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
			Keycloak kc = adapter.getKc();
			List<UserRepresentation> users = kc.realm(adapter.getRealm()).users().search(nombreUsuario, minLimit,
					maxLimit);
			for (UserRepresentation userRepresentation : users) {
				/* Se verifica si el usuario esta activo */
				if ((userRepresentation.getUsername().equalsIgnoreCase(nombreUsuario))
						&& userRepresentation.isEnabled()) {
					return true;
				}
			}
			return false;
		} else {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}
	}

	@Override
	public void borrarUsuarioAdminEmpleador(String nombreUsuario) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation user = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (user != null) {
			Response response = kc.realm(adapter.getRealm()).users().delete(user.getId());
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
				logger.error(response.getStatusInfo());
				throw new TechnicalException(response.getEntity() == null ? null : response.getEntity().toString());
			}
		}
	}

	@Override
	public List<PreguntaUsuarioDTO> consultarPreguntasUsuario(String nombreUsuario, boolean respuestas, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRep != null) {
			Map<String, Object> attrs = userRep.getAttributes();
			List<PreguntaUsuarioDTO> preguntas = new ArrayList<>();
			for (Map.Entry<String, Object> attr : attrs.entrySet()) {
				if (attr.getKey().startsWith(PREFIJO_PREG)) {
					PreguntaUsuarioDTO pregunta = new PreguntaUsuarioDTO();
					String idPreg = attr.getKey().split("_")[1];
					pregunta.setIdPregunta(Long.parseLong(idPreg));
					String preg = preguntasService.buscarPregunta(Long.parseLong(idPreg));
					pregunta.setPregunta(preg);
					if (respuestas) {
						List<String> resp = (List<String>) attr.getValue();
						pregunta.setRespuesta(
								PreguntasSeguridadUtil.obtenerPregunta(userRep.getUsername(), resp.get(0)));
					}
					preguntas.add(pregunta);
				}
			}
			return preguntas;
		}
		return null;
	}

	@Override
	public Boolean recuperarContrasena(String nombreUsuario, Map<String, String> respuestas, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		int respuestasCorrectas = 0;
		UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRep != null) {
			Map<String, Object> attrs = userRep.getAttributes();
			for (Map.Entry<String, String> respuesta : respuestas.entrySet()) {
				if (attrs.containsKey(PREFIJO_PREG + respuesta.getKey())) {
					Object resp = attrs.get(PREFIJO_PREG + respuesta.getKey());
					List<String> respuestaRegistrada = (List<String>) resp;
					String respDesenc = PreguntasSeguridadUtil.obtenerPregunta(userRep.getUsername(),
							respuestaRegistrada.get(0));
					if (respDesenc.equals(respuesta.getValue())) {
						respuestasCorrectas++;
					}
				}
			}
			if (respuestasCorrectas >= 2) {
				String tempPass = asignarPasswordTemporal(userRep.getUsername(), adapter, kc);
				Map<String, String> params = new HashMap<>();
				String password = tempPass;
				params.put("nombreUsuario",
						obtenerNombreUsuario(userRep.getFirstName(), "", userRep.getLastName(), ""));
				params.put("usuario", nombreUsuario);
				params.put("password", password);
				String email = userRep.getEmail();
				if (userRep.getAttributes() != null
						&& userRep.getAttributes().containsKey(UserAttributesEnum.EMAIL.getNombre())) {
					List<String> attr = (List<String>) userRep.getAttributes()
							.get(UserAttributesEnum.EMAIL.getNombre());
					email = attr.get(0);
				}
				enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_RECU_CNTRS_EXT, email);
			}
		}

		return respuestasCorrectas >= 2;
	}

	private void enviarNotificacion(Map<String, String> variables, EtiquetaPlantillaComunicadoEnum notificacion,
									String destinatario) {
		try {
			NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
			notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(notificacion);
			notificacionParametrizadaDTO.setParams(variables);
			notificacionParametrizadaDTO.setDestinatarioTO(new ArrayList<String>());
			notificacionParametrizadaDTO.getDestinatarioTO().add(destinatario);
			notificacionParametrizadaDTO.setReplantearDestinatarioTO(true);
			notificacionParametrizadaDTO.setNoEnviarAdjunto(true);

			if (variables.containsKey("idSolicitud")) {
				notificacionParametrizadaDTO.setIdSolicitud(Long.valueOf(variables.get("idSolicitud")));
			}

			EnviarNotificacionComunicado enviarNotificacionComunicado = new EnviarNotificacionComunicado(
					notificacionParametrizadaDTO);
			enviarNotificacionComunicado.execute();
		} catch (Exception e) {
			// este es el caso en que el envío del correo del comunicado no debe
			// abortar el proceso de afiliación
			logger.warn("No fue posible enviar el correo con el comunicado, el  proceso continuará normalmente");
		}
	}

	private String asignarPasswordTemporal(String userName, KeycloakAdapter adapter, Keycloak kc) {
		logger.info("Busqueda de usuario por userName");
		UserRepresentation userRep = buscarUsuarioPorUsername(userName, adapter, kc);
		logger.info("Busqueda de usuario por userName222" + userRep);
		if (userRep != null) {
			logger.info("Busqueda de usuario por userName333 " );
			Integer numCharacters = Integer.valueOf(
					(String) CacheManager.getConstante(ConstantesSistemaConstants.SEC_INITIAL_CHARACTERS_PASSWORD));
			logger.info("Busqueda de usuario por userName555" + numCharacters );
			CredentialRepresentation credenciales = new CredentialRepresentation();
			credenciales.setType(CredentialRepresentation.PASSWORD);
			String tempPass = SecurePasswordGenerator.generatePassword(numCharacters);
			credenciales.setValue(tempPass);
			credenciales.setTemporary(true);
			logger.info("Realm : " + adapter.getRealm() + " User : " + userRep.getId() + " Key : " + tempPass);
			try{
				logger.info("**::__** ingreso a actualizar contraseña en Keycloak");
				kc.realm(adapter.getRealm()).users().get(userRep.getId()).resetPassword(credenciales);
				logger.info("**::__** finaliza  actualizar contraseña en Keycloak");
			}catch(Exception e ){
				logger.info("**::__** Error kc.realm(adapter.getRealm()).users().get(userRep.getId())"+
				".resetPassword(credenciales) no asigno contraseña temporal" + tempPass +" Error: "+e);
			}
			
			return tempPass;
		}
		return null;
	}

    private String asignarPasswordTemporalGestionUsuarios(String userName, KeycloakAdapter adapter, Keycloak kc) {
        logger.info("Busqueda de usuario por userName GestionUsuarios");
        UserRepresentation userRep = buscarUsuarioPorUsername(userName, adapter, kc);
        logger.info("Busqueda de usuario por userName222 GestionUsuarios: " + userRep);
        if (userRep != null) {
            logger.info("Busqueda de usuario por userName333 GestionUsuarios");

            String tempPass = generarClaveGenerica();
            logger.info("Password generado: " + tempPass);
            CredentialRepresentation credenciales = new CredentialRepresentation();
            credenciales.setType(CredentialRepresentation.PASSWORD);
            credenciales.setValue(tempPass);
            credenciales.setTemporary(true);
            logger.info("Realm: " + adapter.getRealm() + " User: " + userRep.getId() + " Key: " + tempPass);
            try {
                logger.info("**::__** ingreso a actualizar contraseña en Keycloak GestionUsuarios");
                kc.realm(adapter.getRealm()).users().get(userRep.getId()).resetPassword(credenciales);
                logger.info("**::__** finaliza actualizar contraseña en Keycloak GestionUsuarios");
            } catch (Exception e) {
                logger.error("**::__** Error al asignar contraseña temporal GestionUsuarios " + tempPass + " Error: " + e.getMessage(), e);
            }

            return tempPass;
        }
        return null;
    }

	@Override
	public void actualizarPreguntasUsuario(String nombreUsuario, List<PreguntaUsuarioDTO> preguntas, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRep != null) {
			Map<String, Object> attrs = borrarPreguntasAntiguas(userRep.getAttributes());
			if (attrs == null) {
				attrs = new HashMap<>();
			}
			for (PreguntaUsuarioDTO pregunta : preguntas) {
				String encResp = PreguntasSeguridadUtil.aplicarHashPregunta(userRep.getUsername(),
						pregunta.getRespuesta());
				attrs.put(PREFIJO_PREG + pregunta.getIdPregunta(), Arrays.asList(encResp));
			}
			if (attrs.containsKey(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre())) {
				List<String> attr = (List<String>) attrs.get(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre());
				boolean debeCrearPreguntas = Boolean.parseBoolean(attr.get(0));
				if (debeCrearPreguntas) {
					attrs.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
							Arrays.asList(String.valueOf(Boolean.FALSE)));
				}
			}
			userRep.setAttributes(attrs);
			kc.realm(adapter.getRealm()).users().get(userRep.getId()).update(userRep);
		}
	}

	private Map<String, Object> borrarPreguntasAntiguas(Map<String, Object> atributos) {
		if (atributos != null) {
			Map<String, Object> attrs = new HashMap<>();
			attrs.putAll(atributos);
			for (Map.Entry<String, Object> entry : atributos.entrySet()) {
				if (entry.getKey().startsWith(PREFIJO_PREG)) {
					attrs.remove(entry.getKey());
				}
			}
			return attrs;
		}
		return null;
	}

	@Override
	public void crearUsuario(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		if (!validarExistenciaUsuario(usuario.getEmail(), adapter, kc)) {
			UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
			usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));

			usuarioCCF.setRealmRoles(usuario.getRoles());
			HashMap<String, Object> atributos = new HashMap<>();
			atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
					Arrays.asList(String.valueOf(Boolean.TRUE)));
			atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
					Arrays.asList(String.valueOf(Boolean.TRUE)));
			atributos.put(UserAttributesEnum.DEPENDENCIA.getNombre(), Arrays.asList(usuario.getDependencia()));
			atributos.put(UserAttributesEnum.TELEFONO.getNombre(), Arrays.asList(usuario.getTelefono()));
			atributos.put(UserAttributesEnum.SEDE.getNombre(), Arrays.asList(usuario.getCodigoSede()));
			atributos.put(UserAttributesEnum.CIUDAD_SEDE.getNombre(), Arrays.asList(usuario.getCiudadSede()));
			atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));
			atributos.put(UserAttributesEnum.FECHA_FIN_CONTRATO.getNombre(),
					Arrays.asList(usuario.getFechaFinContrato().toString()));
			if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().equals("")) {
				atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
						Arrays.asList(usuario.getTipoIdentificacion()));
			}
			if (usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().equals("")) {
				atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
						Arrays.asList(usuario.getNumIdentificacion()));
			}
			if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
				atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
			}
			if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
				atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(),
						Arrays.asList(usuario.getSegundoApellido()));
			}
			String creadoPor = user.getNombreUsuario();  
			if (user.getNombreUsuario() != null && !user.getNombreUsuario().isEmpty()) {
				atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));
			}
			usuarioCCF.setAttributes(atributos);

			Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					&& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
				logger.error(response.getStatusInfo());
				throw new TechnicalException(Objects.toString(response.getEntity()));
			}

			// se asigna por defecto al grupo de funcionarios CCF
			agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_CCF.getNombre(), usuarioCCF.getUsername(), adapter, kc);

			for (String grupo : usuario.getGrupos()) {
				agregarAGrupoId(grupo, usuarioCCF.getUsername(), adapter, kc);
			}
			if (usuario.getRoles() != null) {
				for (String rol : usuario.getRoles()) {
					agregarUsuarioARol(usuarioCCF.getUsername(), rol, adapter, kc);
				}
			}
			String tempPass = asignarPasswordTemporal(usuarioCCF.getUsername(), adapter, kc);
			// se envia correo notificando la creación del usuario
			Map<String, String> params = new HashMap<>();
			params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
					usuario.getPrimerApellido(), usuario.getSegundoApellido()));
			params.put("usuario", usuario.getEmail());
			params.put("Password", tempPass);
			enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());
		} else {
			throw new FunctionalConstraintException(USUARIO_REGISTRADO_MENSAJE);
		}
	}

	private void agregarUsuarioARol(String nombreUsuario, String rol, KeycloakAdapter adapter, Keycloak kc) {
		RoleRepresentation rolRep = kc.realm(adapter.getRealm()).roles().get(rol).toRepresentation();
		UserRepresentation user = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (user != null) {
			kc.realm(adapter.getRealm()).users().get(user.getId()).roles().realmLevel().add(Arrays.asList(rolRep));
		}
	}

	private void quitarUsuarioARol(String nombreUsuario, String rol, KeycloakAdapter adapter, Keycloak kc) {
		RoleRepresentation rolRep = kc.realm(adapter.getRealm()).roles().get(rol).toRepresentation();
		UserRepresentation user = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (user != null) {
			kc.realm(adapter.getRealm()).users().get(user.getId()).roles().realmLevel().remove(Arrays.asList(rolRep));
		}
	}

	private void agregarAGrupo(String grupo, String nombreUsuario, KeycloakAdapter adapter, Keycloak kc) {
		UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRep != null) {
			GroupRepresentation grp = kc.realm(adapter.getRealm()).getGroupByPath("/" + grupo);
			if (grp != null) {
				kc.realm(adapter.getRealm()).users().get(userRep.getId()).joinGroup(grp.getId());
			} else {
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.GRUPO_NO_EXISTE);
			}
		}
	}

	private void agregarAGrupoId(String grupoId, String nombreUsuario, KeycloakAdapter adapter, Keycloak kc) {
		UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRep != null) {
			kc.realm(adapter.getRealm()).users().get(userRep.getId()).joinGroup(grupoId);
		} else {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_USUARIO_NO_EXISTE, nombreUsuario);
		}
	}

	private void quitarAGrupoId(String grupoId, String nombreUsuario, KeycloakAdapter adapter, Keycloak kc) {
		UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRep != null) {
			kc.realm(adapter.getRealm()).users().get(userRep.getId()).leaveGroup(grupoId);
		} else {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_USUARIO_NO_EXISTE, nombreUsuario);
		}
	}

	private UserRepresentation buscarUsuarioPorUsername(String nombreUsuario, KeycloakAdapter adapter, Keycloak kc) {

		List<UserRepresentation> users = kc.realm(adapter.getRealm()).users().search(nombreUsuario, null, null, null, 0,
				null);
		if (users != null && !users.isEmpty()) {
			for (UserRepresentation userRepresentation : users) {
				if (userRepresentation.getUsername().equalsIgnoreCase(nombreUsuario)) {
					return userRepresentation;
				}
			}
		}

		return null;
	}

	private boolean validarExistenciaUsuario(String nombreUsuario, KeycloakAdapter adapter, Keycloak kc) {
		UserRepresentation usNombreUsuario = null;
		if (nombreUsuario != null) {
			usNombreUsuario = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		}
		return (usNombreUsuario != null);
	}

	@Override
	public void reenviarCorreoEnrolamiento(@NotNull @Valid InformacionReenvioDTO datosReenvio,
										   @Context UserDTO userDTO) {

		NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
		notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(datosReenvio.getNotificacion());
		notificacionParametrizadaDTO.setParams(datosReenvio.getParametrosNotificacion());
		notificacionParametrizadaDTO.setDestinatarioTO(new ArrayList<String>());
		notificacionParametrizadaDTO.getDestinatarioTO().add(datosReenvio.getCorreoDestinatario());
		notificacionParametrizadaDTO.setReplantearDestinatarioTO(true);
		notificacionParametrizadaDTO.setNoEnviarAdjunto(true);
		if (datosReenvio.getIdSolicitud() != null) {
			notificacionParametrizadaDTO.setIdSolicitud(datosReenvio.getIdSolicitud());
		}
		EnviarNotificacionComunicado enviarCorreoParametrizado = new EnviarNotificacionComunicado(
				notificacionParametrizadaDTO);
		enviarCorreoParametrizado.execute();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#consultarUsuarioSede(java.lang.Long,
	 *      com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum)
	 */
	@Override
	public List<UsuarioDTO> consultarUsuarioSede(Long idSede, EstadoActivoInactivoEnum estado, boolean pertenecenCaJa) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		List<UserRepresentation> users;
		if(pertenecenCaJa) {
			String idGrupo;
			try{
				idGrupo = (String) CacheManager.getParametro(ConstantesSistemaConstants.ID_GRUPO_CAJA_COMPENSACION);
				if(idGrupo==null || idGrupo.isEmpty())
					idGrupo = "ecfaf657-8ecd-499c-947e-87c04b7ce508";
			} catch (Exception ex){
				logger.error(ex.getMessage());
				idGrupo = "ecfaf657-8ecd-499c-947e-87c04b7ce508";
			}
			users = kc.realm(adapter.getRealm()).groups().group(idGrupo).members(0, 1000);
		} else {
			users =  kc.realm(adapter.getRealm()).users().search(null, null, null);
		}
		List<UsuarioDTO> result = new ArrayList<>();
		HashSet<UserRepresentation> usersSet = new HashSet<>();
		if (users != null && !users.isEmpty()) {
			for (UserRepresentation userRepresentation : users) {
				if ((EstadoActivoInactivoEnum.ACTIVO == estado && userRepresentation.isEnabled())
						|| (EstadoActivoInactivoEnum.INACTIVO == estado && !userRepresentation.isEnabled())) {
					usersSet.add(userRepresentation); // Agregar usuarios al HashSet
				}
			}
		}
		// if (users != null && !users.isEmpty()) {
			for (UserRepresentation userRepresentation : usersSet) { // Se recorre el HashSet
				// if ((EstadoActivoInactivoEnum.ACTIVO == estado && userRepresentation.isEnabled())
				// 		|| (EstadoActivoInactivoEnum.INACTIVO == estado && !userRepresentation.isEnabled())) {
					Map<String, Object> attributes = userRepresentation.getAttributes();
					if (attributes != null && !attributes.isEmpty()) {
						List<String> sedes = (List<String>) attributes.get(UserAttributesEnum.SEDE.getNombre());

						if (sedes != null && !sedes.isEmpty()) {
							for (String codigoSede : sedes) {
								if (codigoSede != null && codigoSede.equals(idSede.toString())) {
									result.add(KeyCloakMapper.toUserDTO(userRepresentation));
									break;
							
								}
							}
						}
					}
				// }
			}
		// }
		return result;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#actualizarUsuarioCCF(
	 *      com.asopagos.usuarios.dto.UsuarioCCF,
	 *      com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public ResultadoDTO actualizarUsuarioCCF(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		ResultadoDTO dto = new ResultadoDTO();
		dto.setError(false);
		if (validarExistenciaUsuario(usuario.getNombreUsuario(), adapter, kc)) {
			ObtenerTareasAsignadasUsuario obtenerTareas = new ObtenerTareasAsignadasUsuario(usuario.getNombreUsuario());
			obtenerTareas.execute();
			List<TareaDTO> tareas = obtenerTareas.getResult();
			UserRepresentation usuarioCCF = kc.realm(adapter.getRealm()).users().get(usuario.getIdUsuario()).toRepresentation();
			Map<String, Object> atributos = usuarioCCF.getAttributes();

			if (usuario.getDependencia() != null && !usuario.getDependencia().equals("")) {
				atributos.put(UserAttributesEnum.DEPENDENCIA.getNombre(), Arrays.asList(usuario.getDependencia()));
			}
			if (usuario.getTelefono() != null && !usuario.getTelefono().equals("")) {
				atributos.put(UserAttributesEnum.TELEFONO.getNombre(), Arrays.asList(usuario.getTelefono()));
			}
			if (usuario.getCodigoSede() != null && !usuario.getCodigoSede().equals("")) {
				atributos.put(UserAttributesEnum.SEDE.getNombre(), Arrays.asList(usuario.getCodigoSede()));
			}
			if (usuario.getCiudadSede() != null && !usuario.getCiudadSede().equals("")) {
				atributos.put(UserAttributesEnum.CIUDAD_SEDE.getNombre(), Arrays.asList(usuario.getCiudadSede()));
			}
			if (usuario.getEmail() != null && !usuario.getEmail().equals("")) {
				atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));
			}
			if (usuario.getFechaFinContrato() != null) {
				atributos.put(UserAttributesEnum.FECHA_FIN_CONTRATO.getNombre(),
						Arrays.asList(usuario.getFechaFinContrato().toString()));
			}
			if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().equals("")) {
				atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
						Arrays.asList(usuario.getTipoIdentificacion()));
			}
			if (usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().equals("")) {
				atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
						Arrays.asList(usuario.getNumIdentificacion()));
			}
			if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
				atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
			}
			if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
				atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(),
						Arrays.asList(usuario.getSegundoApellido()));
			}
			if (tareas != null && !tareas.isEmpty() && !usuario.isUsuarioActivo()) {
				dto.setError(true);
				dto.setMensaje(NO_INHABILITAR_USUARIO_REGISTRADO_MENSAJE);
				return dto;
			}
			usuarioCCF.setEnabled(usuario.isUsuarioActivo());
			usuarioCCF.setEmailVerified(usuario.isEmailVerified());
			usuarioCCF.setAttributes(atributos);

			if (usuario.getReintegro() != null && usuario.getReintegro().booleanValue()) {
				String tempPass = asignarPasswordTemporal(usuarioCCF.getUsername(), adapter, kc);
				// se envia correo notificando la creación del usuario
				Map<String, String> params = new HashMap<>();
				params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
						usuario.getPrimerApellido(), usuario.getSegundoApellido()));
				params.put("usuario", usuario.getNombreUsuario());
				params.put("Password", tempPass);
				enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_EXT, usuario.getEmail());
			}

			kc.realm(adapter.getRealm()).users().get(usuario.getIdUsuario()).update(usuarioCCF);

			// Se borran todos los grupos asociados al usuario
			for (GroupRepresentation grupoConsultado : kc.realm(adapter.getRealm()).users().get(usuario.getIdUsuario())
					.groups()) {
				quitarAGrupoId(grupoConsultado.getId(), usuario.getNombreUsuario(), adapter, kc);
			}
			// se asigna por defecto al grupo de funcionarios CCF
			agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_CCF.getNombre(), usuarioCCF.getUsername(), adapter, kc);

			// Se agregan los grupos ingresados por pantalla
			if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
				for (String grupo : usuario.getGrupos()) {
					agregarAGrupoId(grupo, usuario.getNombreUsuario(), adapter, kc);
				}
			}

		} else {
			dto.setError(true);
			dto.setMensaje(USUARIO_NO_REGISTRADO_MENSAJE);
		}
		return dto;
	}

	@Override
	public ResultadoDTO cambiarContrasena(CambiarContrasenaDTO dto) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		    Keycloak kc = adapter.getKc();
		KeyCloakRestClient client = KeyCloakRestClientFactory.getKeyCloakRestClient(
				(String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME));

		ResultadoDTO resultado = client.cambiarContrasenia(dto);
		if(!resultado.getError()){
			UserRepresentation userRep = buscarUsuarioPorUsername(dto.getNombreUsuario(), adapter, kc);
		logger.info("Busqueda de usuario por userName222" + userRep);
			if (userRep != null) {
				logger.info("Busqueda de usuario por userName333 " );
				CredentialRepresentation credenciales = new CredentialRepresentation();
				credenciales.setType(CredentialRepresentation.PASSWORD);
				String nuevaContraseña = dto.getPassNuevo();
				credenciales.setValue(nuevaContraseña);
				credenciales.setTemporary(false);
				try{
					logger.info("**::__** ingreso a actualizar contraseña en Keycloak");
					kc.realm(adapter.getRealm()).users().get(userRep.getId()).resetPassword(credenciales);
				}catch(Exception e ){
					logger.info("**::__** Error kc.realm(adapter.getRealm()).users().get(userRep.getId())"+
					".resetPassword(credenciales) no asigno contraseña temporal" + nuevaContraseña +" Error: "+e);
				}
			}
		}

		return resultado;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#consultarUsuarios()
	 */
	@Override
	public List<UsuarioDTO> consultarUsuarios() {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		List<UserRepresentation> users = kc.realm(adapter.getRealm()).users().search(null, null, null);
		List<UsuarioDTO> result = new ArrayList<>();

		if (users != null && !users.isEmpty()) {
			for (UserRepresentation userRepresentation : users) {
				result.add(KeyCloakMapper.toUserDTO(userRepresentation));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#
	 * actualizarEstadoActivacionUsuario(java.lang.String, boolean)
	 */
	@Override
	public void actualizarEstadoActivacionUsuario(String nombreUsuario, boolean estado) {
		actualizarEstadoUsuario(nombreUsuario, null, estado, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#
	 * actualizarEstadoUsuarioMasivo(boolean, java.util.List)
	 */
	@Override
	public void actualizarEstadoUsuarioMasivo(boolean estado, List<String> lstNombreUsuario) {
		actualizarEstadoUsuario(null, lstNombreUsuario, estado, true);
	}

	/**
	 * Metodo encargado de actualizar el estado del usuario
	 *
	 * @param nombreUsuario,
	 *            nombre del usuario del cual se realizara la actualizacion
	 * @param estado,
	 *            estado a realizar la actualizacion
	 * @param actualizarMasivo,booleano
	 *            que se encarga de verificar si se realiza una actualizacion
	 *            masiva o individual
	 */
	private void actualizarEstadoUsuario(String userName, List<String> lstNombreUsuarios, boolean estado,
										 boolean actualizarMasivo) {
		final int maxLimit = 100;
		final int minLimit = 0;
		String nombreUsuario = userName.toLowerCase();
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		List<UserRepresentation> users = null;
		// Se verifica si es actualizacion indivual de usuario
		if (!actualizarMasivo) {
			if (nombreUsuario != null) {
				users = kc.realm(adapter.getRealm()).users().search(nombreUsuario, minLimit, maxLimit);
				for (UserRepresentation userRepresentation : users) {
					if (userRepresentation.getUsername().equals(nombreUsuario)) {
						userRepresentation.setEnabled(estado);
						kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
					}
				}
			} else {
				throw new TechnicalException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
			}
		} else {
			if (!lstNombreUsuarios.isEmpty()) {
				// Se consultan todos los usuarios asociados a Integración
				users = kc.realm(adapter.getRealm()).users().search(nombreUsuario, minLimit, null);
				for (UserRepresentation userRepresentation : users) {
					for (String usuarioNombre : lstNombreUsuarios) {
						if (userRepresentation.getUsername().equalsIgnoreCase(usuarioNombre)) {
							userRepresentation.setEnabled(estado);
							kc.realm(adapter.getRealm()).users().get(userRepresentation.getId())
									.update(userRepresentation);
						}
					}
				}
			} else {
				throw new TechnicalException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
			}
		}
	}

	@Override
	public void crearActualizarUsuarioTemporal(UsuarioTemporalDTO usuario) {

		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation temporal = buscarUsuarioPorUsername(usuario.getEmail(), adapter, kc);
		if (temporal == null) {
			// si no existe el usuario temporal
			temporal = KeyCloakMapper.toUserRepresentation(usuario);
			temporal.setUsername(temporal.getUsername());
			temporal.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
			HashMap<String, Object> attrs = new HashMap<>();
			attrs.put(UserAttributesEnum.TELEFONO.getNombre(), Arrays.asList(usuario.getTelefono()));
			attrs.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));
			attrs.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(), Arrays.asList(String.valueOf(Boolean.TRUE)));
			attrs.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
					Arrays.asList(String.valueOf(Boolean.TRUE)));
			attrs.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
					Arrays.asList(usuario.getTipoIdentificacion()));
			attrs.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(), Arrays.asList(usuario.getNumIdentificacion()));
			attrs.put(UserAttributesEnum.TEMPORAL.getNombre(), Arrays.asList(String.valueOf(Boolean.TRUE)));
			attrs.put(UserAttributesEnum.FECHA_INICIO_TEMPORAL.getNombre(),
					Arrays.asList(String.valueOf(usuario.getFechaInicio())));
			attrs.put(UserAttributesEnum.FECHA_FIN_TEMPORAL.getNombre(),
					Arrays.asList(String.valueOf(usuario.getFechaFin())));
			if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
				attrs.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
			}
			if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
				attrs.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(usuario.getSegundoApellido()));
			}
			temporal.setAttributes(attrs);
			temporal.setEnabled(habilitarTemporal(usuario.getFechaInicio(), usuario.getFechaFin()));

			Response response = kc.realm(adapter.getRealm()).users().create(temporal);
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
				logger.error(response.getStatusInfo());
				throw new TechnicalException(response.getEntity() == null ? null : response.getEntity().toString());
			}

			agregarAGrupo(GruposUsuariosEnum.TEMPORAL.getNombre(), temporal.getUsername(), adapter, kc);

			if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
				for (String grupo : usuario.getGrupos()) {
					agregarAGrupoId(grupo, temporal.getUsername(), adapter, kc);
				}
			}
			if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
				for (String rol : usuario.getRoles()) {
					agregarUsuarioARol(temporal.getUsername(), rol, adapter, kc);
				}
			}

			String tempPass = asignarPasswordTemporal(temporal.getUsername(), adapter, kc);
			Map<String, String> params = new HashMap<>();
			params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
					usuario.getPrimerApellido(), usuario.getSegundoApellido()));
			params.put("usuario", temporal.getUsername());
			params.put("Password", tempPass);
			enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_EXT, usuario.getEmail());
		} else {
			List<?> atributos = (List<?>) temporal.getAttributes().get(UserAttributesEnum.TEMPORAL.getNombre());
			if (atributos != null && !atributos.isEmpty() && new Boolean((String) atributos.get(0))) {
				temporal.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
				HashMap<String, Object> attrs = new HashMap<>();
				attrs.put(UserAttributesEnum.TELEFONO.getNombre(), Arrays.asList(usuario.getTelefono()));
				attrs.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));
				attrs.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
						Arrays.asList(String.valueOf(Boolean.TRUE)));
				attrs.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
						Arrays.asList(String.valueOf(Boolean.TRUE)));
				attrs.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
						Arrays.asList(usuario.getTipoIdentificacion()));
				attrs.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
						Arrays.asList(usuario.getNumIdentificacion()));
				attrs.put(UserAttributesEnum.TEMPORAL.getNombre(), Arrays.asList(String.valueOf(Boolean.TRUE)));
				attrs.put(UserAttributesEnum.FECHA_INICIO_TEMPORAL.getNombre(),
						Arrays.asList(String.valueOf(usuario.getFechaInicio())));
				attrs.put(UserAttributesEnum.FECHA_FIN_TEMPORAL.getNombre(),
						Arrays.asList(String.valueOf(usuario.getFechaFin())));
				if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
					attrs.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
				}
				if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
					attrs.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(),
							Arrays.asList(usuario.getSegundoApellido()));
				}
				temporal.setEnabled(habilitarTemporal(usuario.getFechaInicio(), usuario.getFechaFin()));
				temporal.setAttributes(attrs);
				temporal.setCreatedTimestamp(Calendar.getInstance().getTimeInMillis());
				temporal.setFirstName(usuario.getPrimerNombre());
				temporal.setLastName(usuario.getPrimerApellido());
				temporal.setUsername(usuario.getEmail());
				temporal.setEmail(usuario.getEmail());
				kc.realm(adapter.getRealm()).users().get(temporal.getId()).update(temporal);

				if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
					List<String> gruposActuales = temporal.getGroups();
					if (gruposActuales != null && !gruposActuales.isEmpty()) {
						for (String grupoId : gruposActuales) {
							quitarAGrupoId(grupoId, temporal.getUsername(), adapter, kc);
						}
					}
					for (String grupo : usuario.getGrupos()) {
						agregarAGrupoId(grupo, temporal.getUsername(), adapter, kc);
					}
				}

				agregarAGrupo(GruposUsuariosEnum.TEMPORAL.getNombre(), temporal.getUsername(), adapter, kc);

				if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
					List<String> rolesActuales = temporal.getRealmRoles();
					for (String rol : rolesActuales) {
						quitarUsuarioARol(temporal.getUsername(), rol, adapter, kc);
					}
					for (String rol : usuario.getRoles()) {
						agregarUsuarioARol(temporal.getUsername(), rol, adapter, kc);
					}
				}
				String tempPass = asignarPasswordTemporal(temporal.getUsername(), adapter, kc);
				Map<String, String> params = new HashMap<>();
				params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
						usuario.getPrimerApellido(), usuario.getSegundoApellido()));
				params.put("usuario", temporal.getUsername());
				params.put("Password", tempPass);
				enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_EXT, usuario.getEmail());

			} else {
				throw new FunctionalConstraintException(USUARIO_TEMPORAL_REGISTRADO_MENSAJE);
			}
		}
	}

	/**
	 * Metodo encargado de determinar si un usuario se activa dependiendo de la
	 * fecha de inicio y fecha fin
	 *
	 * @param fechaInicio
	 * @param fechaFin
	 * @return true en caso de activarse, false caso contrario
	 */
	private Boolean habilitarTemporal(Long fechaInicio, Long fechaFin) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(fechaInicio);
		Date fecIni = CalendarUtils.truncarHora(cal.getTime());

		cal = Calendar.getInstance();
		cal.setTimeInMillis(fechaFin);
		Date fecFin = CalendarUtils.truncarHoraMaxima(cal.getTime());

		cal = Calendar.getInstance();
		Date fecAct = CalendarUtils.truncarHoraMaxima(cal.getTime());

		return CalendarUtils.esFechaMayorIgual(fecAct, fecIni) && CalendarUtils.esFechaMenorIgual(fecAct, fecFin);
	}

	@Override
	public UsuarioTemporalDTO consultarUsuarioTemporal(TipoIdentificacionEnum tipoIden, String valorNI) {
		final int maxLimit = 1000;
		final int minLimit = 0;
		UsuarioTemporalDTO usuarioTemporal = null;

		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		GroupRepresentation rep = kc.realm(adapter.getRealm())
				.getGroupByPath("/" + GruposUsuariosEnum.TEMPORAL.getNombre());

		List<UserRepresentation> users = kc.realm(adapter.getRealm()).groups().group(rep.getId()).members(minLimit,
				maxLimit);

		if (users != null && !users.isEmpty()) {
			for (UserRepresentation userRepresentation : users) {
				List<?> numIdent = (List<?>) userRepresentation.getAttributes()
						.get(UserAttributesEnum.NUM_IDENTIFICACION.getNombre());
				List<?> tipIdent = (List<?>) userRepresentation.getAttributes()
						.get(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre());

				if (numIdent != null && tipIdent != null
						&& valorNI.equals(numIdent.get(0)) && tipoIden.toString().equals(tipIdent.get(0))) {
					// Obtener los Grupos
					List<GroupRepresentation> membership = kc.realm(adapter.getRealm()).users()
							.get(userRepresentation.getId()).groups();
					List<String> groups = new ArrayList<>();
					for (GroupRepresentation repGroup : membership) {
						groups.add(repGroup.getId());
					}

					List<?> fechInicio = (List<?>) userRepresentation.getAttributes()
							.get(UserAttributesEnum.FECHA_INICIO_TEMPORAL.getNombre());
					List<?> fechFin = (List<?>) userRepresentation.getAttributes()
							.get(UserAttributesEnum.FECHA_FIN_TEMPORAL.getNombre());

					usuarioTemporal = KeyCloakMapper.toUsuarioTemporalDTO(userRepresentation,
							fechInicio != null ? new Long(fechInicio.get(0).toString()) : null,
							fechFin != null ? new Long(fechFin.get(0).toString()) : null);

					usuarioTemporal.setGrupos(groups);
					return usuarioTemporal;
				}
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#consultarUsuariosAuditoria()
	 */
	@Override
	public List<UsuarioDTO> consultarUsuariosAuditoria(String nombreUsuario, UriInfo uri,
													   HttpServletResponse response) {
		KeyCloakRestClient client = KeyCloakRestClientFactory.getKeyCloakRestClient(
				(String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME));
		return client.consultarUsuariosAuditoriaPaginado(null,0,20);
	}

	@Override
	public void inactivarUsuario(String nombreUsuario, Boolean isInmediate) {

		if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
			KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
					.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
			Keycloak kc = adapter.getKc();
			UserRepresentation userRepresentation = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
			if (userRepresentation == null) {
				/* Si no existe el usuario termina el proceso. */
				return;
			}
			if (isInmediate) {
				userRepresentation.setEnabled(false);
				kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
			} else {
				agregarAGrupo(GruposUsuariosEnum.USUARIOS_RETIRADOS.getNombre(), userRepresentation.getUsername(),
						adapter, kc);
				Map<String, Object> atributos = userRepresentation.getAttributes();
				Calendar cal = Calendar.getInstance();
				Date fecAct = CalendarUtils.truncarHora(cal.getTime());
				Long timeParam = CalendarUtils.toMilis((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_INACTIVAR_CUENTA));
				Integer tiempoInactivar = (int) (timeParam / (3600 * 24 * 1000));
				Date fechaInactivacion = CalendarUtils
						.truncarHoraMaxima(CalendarUtils.sumarDias(fecAct, tiempoInactivar));
				atributos.put(UserAttributesEnum.FECHA_INACTIVACION.getNombre(),
						Arrays.asList(String.valueOf(fechaInactivacion.getTime())));
				userRepresentation.setAttributes(atributos);
				kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);

			}
		} else {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
	}

	@Override
	public void inactivarActivarUsuariosTemporales() {
		final int maxLimit = 1000;
		final int minLimit = 0;
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();

		// se consulta que exista el grupo
		GroupRepresentation rep = kc.realm(adapter.getRealm())
				.getGroupByPath("/" + GruposUsuariosEnum.TEMPORAL.getNombre());
		if (rep != null) {
			List<UserRepresentation> users = kc.realm(adapter.getRealm()).groups().group(rep.getId()).members(minLimit,
					maxLimit);
			for (UserRepresentation userRepresentation : users) {
				Long fechInicio = new Long(((List<?>) userRepresentation.getAttributes()
						.get(UserAttributesEnum.FECHA_INICIO_TEMPORAL.getNombre())).get(0).toString());
				Long fechFin = new Long(((List<?>) userRepresentation.getAttributes()
						.get(UserAttributesEnum.FECHA_FIN_TEMPORAL.getNombre())).get(0).toString());
				userRepresentation.setEnabled(habilitarTemporal(fechInicio, fechFin));
				kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);

			}
		}
	}

	/**
	 * Metodo encargado de obtener el nombre de un usuario
	 *
	 * @param primerNombre
	 * @param segundoNombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @return nombre completo [nombres] [apellidos]
	 */
	private String obtenerNombreUsuario(String primerNombre, String segundoNombre, String primerApellido,
										String segundoApellido) {
		return formatearValorNombre(primerNombre) + formatearValorNombre(segundoNombre)
				+ formatearValorNombre(primerApellido) + formatearValorNombre(segundoApellido);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.asopagos.usuarios.service.UsuariosService#bloquearCuentasUsuarioCCF()
	 */
	@Override
	public void bloquearCuentasUsuarioCCF() {
		final int maxLimit = 10000;
		final int minLimit = 0;
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		List<GroupRepresentation> grupos = kc.realm(adapter.getRealm()).groups().groups();
		if (grupos != null && !grupos.isEmpty()) {
			for (GroupRepresentation groupRepresentation : grupos) {
				if (groupRepresentation.getName().equals(GruposUsuariosEnum.FUNCIONARIO_CCF.getNombre())) {
					List<UserRepresentation> users = kc.realm(adapter.getRealm()).groups()
							.group(groupRepresentation.getId()).members(minLimit, maxLimit);
					if (users != null && !users.isEmpty()) {
						for (UserRepresentation userRepresentation : users) {
							logger.debug("Revisando usuario: " + userRepresentation.getUsername());
							if (userRepresentation.isEnabled()
									&& userRepresentation.getAttributes() != null
									&& userRepresentation.getAttributes()
									.get(UserAttributesEnum.FECHA_FIN_CONTRATO.getNombre()) != null) {
								logger.debug("Usuario Habilitado: " + userRepresentation.getUsername());
								List<?> listDate = (List<?>) userRepresentation.getAttributes()
										.get(UserAttributesEnum.FECHA_FIN_CONTRATO.getNombre());
								if (listDate != null && !listDate.isEmpty()) {
									Calendar fechaFinContrato = Calendar.getInstance();
									fechaFinContrato.setTimeInMillis(new Long(listDate.get(0).toString()));
									logger.debug("Fecha contrato " + fechaFinContrato);
									if (CalendarUtils.esFechaMayorIgual(
											CalendarUtils.truncarHora(Calendar.getInstance().getTime()),
											CalendarUtils.truncarHora(fechaFinContrato.getTime()))) {
										userRepresentation.setEnabled(false);
										kc.realm(adapter.getRealm()).users().get(userRepresentation.getId())
												.update(userRepresentation);
										logger.debug("Usuario deshabilitado: " + userRepresentation.getUsername());

									}
								}
							}
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * Metodo encargado de retornar vacio cuando el valor esta nulo, o retornar
	 * el valor cuando no es ni nulo ni vacio
	 *
	 * @param value,
	 *            valor a validar
	 * @return nuevo valor
	 */
	private String formatearValorNombre(String value) {
		return (value != null && !value.isEmpty()) ? value + " " : "";
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#aceptarDerechosAcceso(java.lang.String,
	 *      boolean)
	 */
	@Override
	public void aceptarDerechosAcceso(String nombreUsuario, Boolean debeAceptarTerminos) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation usuario = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (usuario != null) {
			Map<String, Object> atributos = usuario.getAttributes();
			atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
					Arrays.asList(String.valueOf(debeAceptarTerminos)));
			usuario.setAttributes(atributos);
			kc.realm(adapter.getRealm()).users().get(usuario.getId()).update(usuario);
		} else {
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.asopagos.usuarios.service.UsuariosService#
	 * generarAvisoVencimientoClaveUsuario()
	 */
	@Override
	public void generarAvisoVencimientoClaveUsuario() {
		KeyCloakRestClient client = KeyCloakRestClientFactory.getKeyCloakRestClient(
				(String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME));
		List<UsuarioDTO> usuariosActivos = client.consultarUsuariosActivos();
		if (usuariosActivos != null && !usuariosActivos.isEmpty()) {
			Long dias = CalendarUtils.toMilis((String) CacheManager.getParametro(ParametrosSistemaConstants.DIAS_NOTIFICACION_EXPIRACION_CONTRASENA));
			for (UsuarioDTO usuarioDTO : usuariosActivos) {
				if (usuarioDTO.getDiasRestantesExpiracionPassword() != null
						&& (usuarioDTO.getDiasRestantesExpiracionPassword()*DIAS_TO_MILIS) <= dias
						&& usuarioDTO.getEmail() != null && !usuarioDTO.getEmail().isEmpty()
						&& usuarioDTO.getDiasRestantesExpiracionPassword() >= 0) {
					Map<String, String> params = new HashMap<>();
					params.put("nombresYApellidosDelAfiliadoPrincipal",
							obtenerNombreUsuario(usuarioDTO.getPrimerNombre(), usuarioDTO.getSegundoNombre(),
									usuarioDTO.getPrimerApellido(), usuarioDTO.getSegundoApellido()));
					params.put("diasVencimiento", usuarioDTO.getDiasRestantesExpiracionPassword().toString());
					enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_VEN_CON_USR,
							usuarioDTO.getEmail());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.asopagos.usuarios.service.UsuariosService#obtenerGruposUsuario(java.
	 * lang.String, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public List<GrupoDTO> obtenerGruposUsuario(String nombreUsuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRep != null) {
			List<GroupRepresentation> grupos = adapter.getKc().realm(adapter.getRealm()).users().get(userRep.getId())
					.groups();
			List<GrupoDTO> lis = new ArrayList<>();
			for (GroupRepresentation groupRepresentation : grupos) {
				lis.add(toGrupoDTO(groupRepresentation));
			}
			return lis;
		}
		return Collections.emptyList();
	}

	/**
	 * Metodo encargado de convertir una representacion de grupo en KeyCloak a
	 * un DTO
	 *
	 * @param gr,
	 *            representacion grupo
	 * @return dto con la informacion del grupo
	 */
	private GrupoDTO toGrupoDTO(GroupRepresentation gr) {
		GrupoDTO grupo = new GrupoDTO();
		grupo.setAttributes(gr.getAttributes());
		grupo.setClientRoles(gr.getClientRoles());
		grupo.setId(gr.getId());
		grupo.setName(gr.getName());
		grupo.setPath(gr.getPath());
		grupo.setRealmRoles(gr.getRealmRoles());
		return grupo;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.asopagos.usuarios.service.UsuariosService#desbloquearCuentaUsuario(
	 * java.lang.String, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void desbloquearCuentaUsuario(String nombreUsuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		UserRepresentation userRepresentation = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
		if (userRepresentation != null) {
			List<GroupRepresentation> grupos = adapter.getKc().realm(adapter.getRealm()).users()
					.get(userRepresentation.getId()).groups();
			for (GroupRepresentation grupoUsuario : grupos) {
				if (grupoUsuario.getName().equals(GruposUsuariosEnum.USUARIOS_RETIRADOS.getNombre())) {
					quitarAGrupoId(grupoUsuario.getId(), nombreUsuario, adapter, kc);
					break;
				}
			}
			userRepresentation.setEnabled(true);
			kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
			String tempPass = asignarPasswordTemporal(nombreUsuario, adapter, kc);
			// se envia correo notificando la creación del usuario
			Map<String, String> params = new HashMap<>();

			params.put("nombreUsuario", obtenerNombreUsuario(userRepresentation.getFirstName(), null,
					userRepresentation.getLastName(), null));
			params.put("usuario", userRepresentation.getUsername());
			params.put("password", tempPass);

			String email = userRepresentation.getEmail();

			if (userRepresentation.getAttributes() != null
					&& userRepresentation.getAttributes().containsKey(UserAttributesEnum.EMAIL.getNombre())) {
				List<String> attr = (List<String>) userRepresentation.getAttributes()
						.get(UserAttributesEnum.EMAIL.getNombre());
				email = attr.get(0);
			}

			enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_BLQ_CTA_USR, email);
		}
	}

	private void consultarBloquearUsuarios(Boolean consulta, UserRepresentation userRepresentation,
										   KeycloakAdapter adapter, Keycloak kc, List<UsuarioDTO> usuarios) {
		if (!consulta) {
			userRepresentation.setEnabled(false);
			List<GroupRepresentation> grupos = adapter.getKc().realm(adapter.getRealm()).users()
					.get(userRepresentation.getId()).groups();
			for (GroupRepresentation grupoUsuario : grupos) {
				if (grupoUsuario.getName().equals(GruposUsuariosEnum.USUARIOS_RETIRADOS.getNombre())) {
					quitarAGrupoId(grupoUsuario.getId(), userRepresentation.getUsername(), adapter, kc);
					userRepresentation.setEnabled(false);
					kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
				}
			}
		} else {
			usuarios.add(KeyCloakMapper.toUserDTO(userRepresentation));
		}
	}

	@Override
	public List<UsuarioDTO> bloquearUsuariosPendienteInactivacion(Boolean consulta) {
		final int maxLimit = 1000;
		final int minLimit = 0;
		List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		GroupRepresentation rep = kc.realm(adapter.getRealm())
				.getGroupByPath("/" + GruposUsuariosEnum.USUARIOS_RETIRADOS.getNombre());
		if (rep != null) {
			List<UserRepresentation> users = kc.realm(adapter.getRealm()).groups().group(rep.getId()).members(minLimit,
					maxLimit);
			for (UserRepresentation userRepresentation : users) {

				List<?> listDate = (List<?>) userRepresentation.getAttributes()
						.get(UserAttributesEnum.FECHA_INACTIVACION.getNombre());

				if (listDate != null && !listDate.isEmpty()) {
					Calendar fechaBloqueo = Calendar.getInstance();

					fechaBloqueo.setTimeInMillis(new Long(listDate.get(0).toString()));

					if (CalendarUtils.esFechaMenorIgual(CalendarUtils.truncarHora(fechaBloqueo.getTime()),
							CalendarUtils.truncarHora(Calendar.getInstance().getTime()))) {
						consultarBloquearUsuarios(consulta, userRepresentation, adapter, kc, usuarios);
					}
				}
			}
		}
		return usuarios;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.asopagos.usuarios.service.UsuariosService#inactivarUsuariosMasivo(
	 * java.util.List, java.lang.Boolean)
	 */
	@Override
	public void inactivarUsuariosMasivo(List<String> usuarios, Boolean inmediate) {
		for (String usuario : usuarios) {
			this.inactivarUsuario(usuario, inmediate);
		}
	}

	@Override
	public List<UsuarioDTO> usuariosEnSesion() {
		List<UsuarioDTO> nombresUsuarios = new ArrayList<>();
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		List<Map<String, String>> clients = kc.realm(adapter.getRealm()).getClientSessionStats();
		String id = null;
		for (Map<String, String> map : clients) {
			if ("frontend".equals(map.get("clientId"))) {
				id = map.get("id");
			}
		}

		if (!StringUtils.isBlank(id)) {
			List<UserSessionRepresentation> sessions = kc.realm(adapter.getRealm()).clients().get(id).getUserSessions(0,
					100);

			for (UserSessionRepresentation userSessionRepresentation : sessions) {
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setNombreUsuario(userSessionRepresentation.getUsername());
				nombresUsuarios.add(usuario);
			}
		}
		return nombresUsuarios;
	}

	@Override
	public void activarUsuario(String nombreUsuario, Boolean isInmediate) {

		if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
			KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
					.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
			Keycloak kc = adapter.getKc();
			UserRepresentation userRepresentation = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);
			if (userRepresentation == null) {
				/* Si no existe el usuario termina el proceso. */
				return;
			}
			if (isInmediate) {
				userRepresentation.setEnabled(true);
				kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
			}
		} else {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
	}

	@Override
	public Long validarDiasVencimientoClaveUsuario(UsuarioDTO usuarioIn) {
		logger.info("Inicia el servicio validarDiasVencimientoClaveUsuario(String)");
		//Se contruyen las propiedades del keyCloak
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		KeyCloakRestClient client = KeyCloakRestClientFactory.getKeyCloakRestClient(
				(String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME));
		List<UsuarioDTO> usuarios = null;

		if(usuarioIn != null && usuarioIn.getNombreUsuario() != null){
			//Se obtienen las propiedades del usuario
			usuarios = client.consultarUsuariosAuditoriaPaginado(usuarioIn.getNombreUsuario(), null, null);
		}

		if (usuarios != null && !usuarios.isEmpty()) {
			UsuarioDTO usuario = usuarios.get(0);
			UserRepresentation userRepresentation = buscarUsuarioPorUsername(usuarioIn.getNombreUsuario(), adapter, kc); 

            if (userRepresentation != null) {
                usuarioIn.setIdUsuario(userRepresentation.getId());
                logger.info("Este es el ID del usuario que inició sesión: " + usuarioIn.getIdUsuario());
				usuariosEnSesionParaInactivar(usuarioIn.getIdUsuario());
				usuariosEnSesionParaCerrarSesion(usuarioIn.getIdUsuario());
            } else {
                logger.error("No se pudo encontrar el ID del usuario para: " + usuario.getNombreUsuario());
                return null; 
            }
			Long dias = CalendarUtils.toMilis((String) CacheManager.getParametro(ParametrosSistemaConstants.DIAS_NOTIFICACION_EXPIRACION_CONTRASENA));
			if (usuario.getDiasRestantesExpiracionPassword() != null
					&& (usuario.getDiasRestantesExpiracionPassword()*DIAS_TO_MILIS) <= dias) {
				Long response = Long.valueOf(usuario.getDiasRestantesExpiracionPassword());
				logger.info("Finaliza el servicio validarDiasVencimientoClaveUsuario(String)");
				return response;
			}
			else
				logger.info("Finaliza el servicio validarDiasVencimientoClaveUsuario(String)");
			return null;

		} else {
			logger.error("El usuario no existe");
			return null;
		}
	}
	//Inicio Metodos 82800
    /**
     * GLPI 82800 Gestion Consultar Usuario Empleador
     * 
     * <b>Descripción</b>Método que se encarga de obtener usuarios empleador
	 *
	 * * @return se retorna la lista con los usuarios obtenidos
	 *
     */
    @Override
    public List<UsuarioGestionDTO> gestionConsultarEmpleador(
            String nombreUsuario, Boolean roles, String tipoIdentificacion, String numIdentificacion, String razonSocial,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            Boolean usuarioActivo, String fechaCreacion, String creadoPor,
            String fechaModificacion, String modificadoPor) {

        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        // Buscar usuarios en Keycloak
        List<UserRepresentation> users = kc.realm(adapter.getRealm()).users()
                .search(null, null, null, null, 0, Integer.MAX_VALUE);

        // Filtrar usuarios según los parámetros proporcionados
        List<UserRepresentation> usuariosFiltrados = users.stream()
                .filter(user -> filtrarUsuarioEmpleador(user, nombreUsuario, tipoIdentificacion, numIdentificacion, razonSocial,
                        primerNombre, primerApellido, segundoNombre, segundoApellido,
                        usuarioActivo, fechaCreacion, creadoPor, fechaModificacion, modificadoPor))
                .collect(Collectors.toList());

        // Convertir los usuarios filtrados a DTOs y consultar base de datos si aplica
        List<UsuarioGestionDTO> resultado = usuariosFiltrados.stream()
                .map(user -> {
                    UsuarioGestionDTO dto = KeyCloakMapper.toUsuarioGestion(user);

                    boolean datosIncompletos = dto.getRazonSocial() == null ||
                            dto.getEmail() == null;

                    // Precarga de datos desde la base de datos si hay tipo/número o razón social
                    if (datosIncompletos && (tipoIdentificacion != null && numIdentificacion != null)) {
                        try {
                            Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION);
                            query.setParameter("numIdentificacion", numIdentificacion);
                            query.setParameter("tipoIdentificacion", tipoIdentificacion);

                            List<Object[]> resultados = query.getResultList();
                            if (!resultados.isEmpty()) {
                                Object[] datos = resultados.get(0);
                                if (dto.getRazonSocial() == null) dto.setRazonSocial((String) datos[0]);
                                if (dto.getPrimerNombre() == null) dto.setPrimerNombre((String) datos[1]);
                                if (dto.getSegundoNombre() == null) dto.setSegundoNombre((String) datos[2]);
                                if (dto.getPrimerApellido() == null) dto.setPrimerApellido((String) datos[3]);
                                if (dto.getSegundoApellido() == null) dto.setSegundoApellido((String) datos[4]);
                                if (dto.getEmail() == null) dto.setEmail((String) datos[5]);
                                if (dto.getNumIdentificacion() == null) dto.setNumIdentificacion((String) datos[6]);
                                if (dto.getTipoIdentificacion() == null) dto.setTipoIdentificacion((String) datos[7]);
                            }

                        } catch (Exception e) {
                            throw new TechnicalException("Error al consultar usuario", e);
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // Si no hay resultados desde Keycloak, intentar buscar solo en la base de datos
        if (resultado.isEmpty() && ((tipoIdentificacion != null && numIdentificacion != null) || razonSocial != null)) {
            try {
                Query query;
                if (tipoIdentificacion != null && numIdentificacion != null) {
                    query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION);
                    query.setParameter("tipoIdentificacion", tipoIdentificacion);
                    query.setParameter("numIdentificacion", numIdentificacion);
                } else {
                    query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RAZON_SOCIAL_EMPLEADOR_POR_NUMERO_IDENTIFICACION);
                    query.setParameter("razonSocial", razonSocial);
                }

                List<Object[]> resultados = query.getResultList();
                if (!resultados.isEmpty()) {
                    Object[] datos = resultados.get(0);
                    UsuarioGestionDTO dto = new UsuarioGestionDTO();
                    dto.setRazonSocial((String) datos[0]);
                    dto.setPrimerNombre((String) datos[1]);
                    dto.setSegundoNombre((String) datos[2]);
                    dto.setPrimerApellido((String) datos[3]);
                    dto.setSegundoApellido((String) datos[4]);
                    dto.setEmail((String) datos[5]);
                    dto.setNumIdentificacion((String) datos[6]);
                    dto.setTipoIdentificacion((String) datos[7]);
                    

                    if (tipoIdentificacion != null && numIdentificacion != null) {
                        dto.setNombreUsuario("emp_" + tipoIdentificacion.toLowerCase() + "_" + numIdentificacion);
                    }

                    resultado.add(dto);
                }

            } catch (Exception e) {
                throw new TechnicalException("Error al consultar usuario solo en BD", e);
            }
        }

        return resultado;
    }

    private boolean filtrarUsuarioEmpleador(UserRepresentation user,
            String nombreUsuario, String tipoIdentificacion, String numIdentificacion, String razonSocial,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            Boolean usuarioActivo, String fechaCreacion, String creadoPor,
            String fechaModificacion, String modificadoPor) {

        Map<String, List<String>> attributes = convertirAtributos(user.getAttributes());
        Boolean estadoUsuario = user.isEnabled();
		Long fechaCreacionEpoch = user.getCreatedTimestamp();
		Long fechaCreacionKeycloak = fechaCreacionEpoch / 1000;

        // Verificar si la búsqueda incluye alguno de los atributos específicos
        boolean filtroEspecialActivo = razonSocial !=null || tipoIdentificacion != null || numIdentificacion != null || 
									   fechaCreacion != null || creadoPor != null || 
                                       fechaModificacion != null || modificadoPor != null || 
                                       usuarioActivo != null;

        // Filtrar usuarios según criterios
        return (!filtroEspecialActivo || (user.getUsername() != null && user.getUsername().toLowerCase().startsWith("emp_"))) &&
               (nombreUsuario == null || user.getUsername().equalsIgnoreCase(nombreUsuario)) &&
               (primerNombre == null || 
                    (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(primerNombre.toLowerCase()))) &&
               (primerApellido == null || 
                    (user.getLastName() != null && user.getLastName().toLowerCase().contains(primerApellido.toLowerCase()))) &&
               (segundoNombre == null || 
                    (attributes != null && attributes.getOrDefault("segundoNombre", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(segundoNombre))) &&
               (segundoApellido == null || 
                    (attributes != null && attributes.getOrDefault("segundoApellido", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(segundoApellido))) &&
               (tipoIdentificacion == null || 
                    (attributes != null && attributes.getOrDefault("tipoIdentificacion", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(tipoIdentificacion))) &&
               (numIdentificacion == null || 
                    (attributes != null && attributes.getOrDefault("numIdentificacion", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(numIdentificacion))) &&
               (razonSocial == null || 
                    (attributes != null && attributes.getOrDefault("razonSocial", Arrays.asList("")).get(0)
                            .toLowerCase().contains(razonSocial.toLowerCase()))) &&
               (usuarioActivo == null || estadoUsuario.equals(usuarioActivo)) &&
               (creadoPor == null || 
                    (attributes != null && attributes.getOrDefault("creadoPor", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(creadoPor))) &&
               (fechaCreacion == null ||  fechaCreacionKeycloak.equals(fechaCreacion)) &&
               (modificadoPor == null || 
                    (attributes != null && attributes.getOrDefault("modificadoPor", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(modificadoPor))) &&
               (fechaModificacion == null || 
                    (attributes != null && attributes.getOrDefault("fechaModificacion", Arrays.asList("")).get(0)
                            .equals(fechaModificacion)));
    }

	/**
     * GLPI 82800 Gestion Crear Usuario Empleador
     * 
	 *
	 *
     */
	@Override
	public void gestionCrearEmpleador(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		logger.info("Usuario que esta creando los usuarios de manera individual: " + user.getNombreUsuario());
		// Validar existencia de usuario por email
		if (!validarExistenciaUsuario(usuario.getEmail(), adapter, kc)) {
			try {
				// Consultar datos del empleador por número de identificación
				Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION);
				query.setParameter("numIdentificacion", usuario.getNumIdentificacion());
				query.setParameter("tipoIdentificacion", usuario.getTipoIdentificacion());
				List<Object[]> resultados = query.getResultList(); 

                // Verificar si se encontraron resultados
                if (resultados.isEmpty()) {
					throw new FunctionalConstraintException("El usuario con tipo identificación " + usuario.getTipoIdentificacion() 
                                + " y número " + usuario.getNumIdentificacion() + " no existe en la base de datos.");
				}

				Object[] resultado = resultados.get(0);
				String razonSocial = (String) resultado[0];
				String primerNombreConsulta = (String) resultado[1];
				String primerApellidoConsulta = (String) resultado[2];
				String segundoNombreConsulta = (String) resultado[3];
				String segundoApellidoConsulta = (String) resultado[4];
				String emailConsulta = (String) resultado[5];

				// Asignar nombres y apellidos desde la consulta si no están en los datos de entrada
				if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().isEmpty()) {
					usuario.setPrimerNombre(primerNombreConsulta);
				}
				if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().isEmpty()) {
					usuario.setPrimerApellido(primerApellidoConsulta);
				}
				if (usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isEmpty()) {
					usuario.setSegundoNombre(segundoNombreConsulta);
				}
				if (usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isEmpty()) {
					usuario.setSegundoApellido(segundoApellidoConsulta);
				}

				logger.info("Razón social en los datos de entrada: " + usuario.getRazonSocial());
				if (usuario.getRazonSocial() == null || usuario.getRazonSocial().isEmpty()) {
					usuario.setRazonSocial(razonSocial);
				}

				// Manejo del email
				if (emailConsulta != null && !emailConsulta.isEmpty()) {
					// Si ya tiene email en la consulta, setearlo en Keycloak
					// usuario.setEmail(emailConsulta);
				} else if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
					// Si no tiene email en la consulta y hay en los datos de entrada, actualizar en la BD y Keycloak
					Query updateQuery = entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_EMPLEADOR_POR_NUMERO_IDENTIFICACION);
					updateQuery.setParameter("email", usuario.getEmail());
					updateQuery.setParameter("numIdentificacion", usuario.getNumIdentificacion());
					updateQuery.executeUpdate();
				}

				// Crear representación de usuario en Keycloak
				UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
				// usuarioCCF.setEmail(usuario.getEmail());
				usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
				usuarioCCF.setRealmRoles(usuario.getRoles());

				// Mapeo de atributos personalizados
				HashMap<String, Object> atributos = new HashMap<>();
				atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
						Arrays.asList(String.valueOf(Boolean.FALSE)));
				atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
						Arrays.asList(String.valueOf(Boolean.FALSE)));
				atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));

				// Validar y asignar tipoIdentificacion y numIdentificacion
				if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() 
					&& usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {
					atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
							Arrays.asList(usuario.getTipoIdentificacion()));
					atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
							Arrays.asList(usuario.getNumIdentificacion()));
				} else {
					throw new FunctionalConstraintException("Tipo de identificación y número de identificación son obligatorios.");
				}

				// Agregar segundo nombre y segundo apellido si existen
				if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
					atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
				}
				if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
					atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(usuario.getSegundoApellido()));
				}

				if (usuario.getRazonSocial() != null && !usuario.getRazonSocial().isEmpty()) {
					atributos.put(UserAttributesEnum.RAZON_SOCIAL.getNombre(), Arrays.asList(usuario.getRazonSocial()));
				}

				atributos.put("usuarioActivo", Arrays.asList("Activo"));

				// Agregar fechaCreacion en formato epoch
				Long fechaCreacionEpoch = Instant.now().getEpochSecond();
				atributos.put("fechaCreacion", Arrays.asList(String.valueOf(fechaCreacionEpoch)));

				// Agregar usuarioCreacion (por ejemplo, del objeto `user`)
				String creadoPor = user.getNombreUsuario();  
				atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));

				usuarioCCF.setAttributes(atributos);

				// Generar el username con el prefijo, tipoIdentificacion, y numIdentificacion
				String username = PREFIJO_EMPLEADOR 
						+ usuario.getTipoIdentificacion() 
						+ "_" 
						+ usuario.getNumIdentificacion();
				usuarioCCF.setUsername(username);

				// Crear el usuario en Keycloak
				Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
				if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					&& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
					logger.error("Error al crear el usuario en Keycloak Debido a que ya existe un usario con esa informacion: " + response.getStatusInfo());
					throw new TechnicalException("No se pudo crear el usuario. Código de error: " + response.getStatus());
				}

				// Asignar al grupo por defecto
				agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_EMPLEADOR.getNombre(), usuarioCCF.getUsername(), adapter, kc);
				agregarAGrupo(GruposUsuariosEnum.ADMINISTRADOR_EMPLEADOR.getNombre(), usuarioCCF.getUsername(), adapter, kc);

				// Asignar contraseña temporal y enviar notificación
				String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);
				Map<String, String> params = new HashMap<>();
				params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
						usuario.getPrimerApellido(), usuario.getSegundoApellido()));
				params.put("usuario", usuario.getEmail());
				params.put("Password", tempPass);
				enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());
			
			} catch (FunctionalConstraintException e) {
                logger.warn("Excepción de restricción funcional: " + e.getMessage());
                throw e; // Lanza la excepción si deseas que la propague
			} catch (Exception e) {
				logger.error("Excepción al crear el usuario en Keycloak", e);
				throw new TechnicalException("Error al crear el usuario en Keycloak Debido a que ya existe un usario con esa informacion", e);
			}
		} else {
			throw new FunctionalConstraintException(USUARIO_REGISTRADO_MENSAJE);
		}
	}

	/**
     * GLPI 82800 Gestion Actualizar Usuario Empleador
     * 
	 *
	 *
     */
    @Override
    public ResultadoDTO gestionActualizarEmpleador(UsuarioCCF usuario, UserDTO user) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        ResultadoDTO dto = new ResultadoDTO();
        dto.setError(false);

        if (kc == null) {
            dto.setError(true);
            dto.setMensaje("Error: Keycloak no se ha inicializado correctamente.");
            return dto;
        }

        String realm = adapter.getRealm();
        if (realm == null) {
            dto.setError(true);
            dto.setMensaje("Error: El realm es nulo.");
            return dto;
        }

        // Obtener representación del usuario usando idUsuario
        UserResource userResource = kc.realm(realm).users().get(usuario.getIdUsuario());
        UserRepresentation usuarioCCF = userResource.toRepresentation();
        if (usuarioCCF == null) {
            dto.setError(true);
            dto.setMensaje("Error: Usuario no encontrado en Keycloak.");
            return dto;
        }

        Map<String, Object> atributos = usuarioCCF.getAttributes();
        if (atributos == null) {
            atributos = new HashMap<>();
        }

        EntityManager em = entityManager;  // Obtener EntityManager

        // Variables para registrar auditoría
        String modificadoPor = user.getNombreUsuario();  
        long fechaModificacionEpoch = Instant.now().getEpochSecond();
        String fechaModificacion = String.valueOf(fechaModificacionEpoch);

        // Actualizar datos básicos
        usuarioCCF.setEmail(usuario.getEmail());
        // usuarioCCF.setEnabled(usuario.isUsuarioActivo());
        // usuarioCCF.setEmailVerified(usuario.isEmailVerified());

        // Actualizar atributos
        actualizarYCargarDatos(usuarioCCF, em, atributos, 
            "email", usuario.getEmail(), modificadoPor, fechaModificacion);

        actualizarYCargarDatos(usuarioCCF, em, atributos, 
            "tipoIdentificacion", usuario.getTipoIdentificacion(), modificadoPor, fechaModificacion);

        actualizarYCargarDatos(usuarioCCF, em, atributos, 
            "numIdentificacion", usuario.getNumIdentificacion(), modificadoPor, fechaModificacion);

        actualizarYCargarDatos(usuarioCCF, em, atributos, 
            "segundoNombre", usuario.getSegundoNombre(), modificadoPor, fechaModificacion);

        actualizarYCargarDatos(usuarioCCF, em, atributos, 
            "segundoApellido", usuario.getSegundoApellido(), modificadoPor, fechaModificacion);

        actualizarYCargarDatos(usuarioCCF, em, atributos, 
            "razonSocial", usuario.getRazonSocial(), modificadoPor, fechaModificacion);

		atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
        atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));
            
        // Calcular nuevo nombre de usuario
        String nuevoNombreUsuario = "emp_" + usuario.getTipoIdentificacion() + "_" + usuario.getNumIdentificacion();
        boolean requiereCambioNombreUsuario = !usuarioCCF.getUsername().equals(nuevoNombreUsuario);

        if (requiereCambioNombreUsuario) {
            List<UserRepresentation> usuariosConNuevoNombre = kc.realm(realm).users().search(nuevoNombreUsuario, null, null);
            boolean esMismoUsuario = usuariosConNuevoNombre.stream()
                    .anyMatch(u -> u.getId().equals(usuario.getIdUsuario()));

            if (usuariosConNuevoNombre.isEmpty() || esMismoUsuario) {
                usuarioCCF.setUsername(nuevoNombreUsuario);
            } else {
                dto.setError(true);
                dto.setMensaje("Error: El nuevo nombre de usuario ya está en uso.");
                return dto;
            }
        }

        // Actualizar atributos relacionados con la identificación
        atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(), Arrays.asList(usuario.getTipoIdentificacion()));
        atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(), Arrays.asList(usuario.getNumIdentificacion()));

        // Actualizar en Keycloak
        usuarioCCF.setAttributes(atributos);
        userResource.update(usuarioCCF);

        // Actualizar grupos
        for (GroupRepresentation grupoConsultado : userResource.groups()) {
            quitarAGrupoId(grupoConsultado.getId(), nuevoNombreUsuario, adapter, kc);
        }

        agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_EMPLEADOR.getNombre(), nuevoNombreUsuario, adapter, kc);
        agregarAGrupo(GruposUsuariosEnum.ADMINISTRADOR_EMPLEADOR.getNombre(), nuevoNombreUsuario, adapter, kc);

        if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
            for (String grupo : usuario.getGrupos()) {
                agregarAGrupoId(grupo, nuevoNombreUsuario, adapter, kc);
            }
        }

        dto.setMensaje("Usuario actualizado exitosamente.");
        return dto;
    }

	private void actualizarYCargarDatos(UserRepresentation usuario, EntityManager em, 
        Map<String, Object> atributos, String campo, String nuevoValor, 
        String modificadoPor, String fechaModificacion) {

		String valorAnterior = ((List<String>) atributos.getOrDefault(campo, Arrays.asList(""))).get(0);

		if (nuevoValor != null && !nuevoValor.equals(valorAnterior)) {
			// Actualizar en Keycloak
			atributos.put(campo, Arrays.asList(nuevoValor));

			// Registrar en la base de datos
			em.createNamedQuery(NamedQueriesConstants.INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_EMPLEADOR)
				.setParameter("usuarioEditado", usuario.getUsername())
				.setParameter("campoModificado", campo)
				.setParameter("valorAnterior", valorAnterior)
				.setParameter("nuevoValor", nuevoValor)
				.setParameter("fechaModificacion", new java.sql.Timestamp(System.currentTimeMillis()))
				.setParameter("modificadoPor", modificadoPor)
				.executeUpdate();
		}
	}

	/**
     * GLPI 82800 Gestion Restablecer Contraseña Usuario Empleador
     * 
	 *
	 *
     */
	// @Override
	// @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	// public void gestionRestablecerContrasenaEmpleador(CambiarContrasenaGestionUsuarioDTO dto) {
    //     logger.info("Iniciando proceso de restablecimiento de contraseña para el usuario: " + dto.getNombreUsuario());

    //     // Validar que el email no esté vacío
    //     if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
    //         logger.warn("Email vacío para el usuario: " + dto.getNombreUsuario() + " Por favor, ingrese su email.");
	// 		return;
    //     }

    //     // Validar formato de email
    //     if (!isValidEmail(dto.getEmail())) {
    //         logger.warn("Formato de email inválido para el usuario: " + dto.getNombreUsuario() + " Por favor, ingrese un email válido.");
	// 		return;
    //     }

    //     // Obtener el adaptador de Keycloak
    //     KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
    //             .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
    //     Keycloak kc = adapter.getKc();

    //     // Validar si el email está asociado a un usuario en Keycloak
    //     logger.info("Validando existencia de usuario en Keycloak");
    //     UserRepresentation userRepresentation = validarExistenciaUsuarioPorEmail(dto.getEmail(), adapter, kc);
    //     if (userRepresentation != null) {
    //         // Enviar correo para restablecer la contraseña
    //         enviarCorreoRestablecerContrasena(dto.getEmail(), dto.getNombreUsuario());
    //     } else {
    //         logger.info("El email ingresado no está registrado: " + dto.getEmail());
    //     }
	// }

	private boolean cambiarPasswordUsuario(String userName, String nuevaContrasena, KeycloakAdapter adapter, Keycloak kc) {
		logger.info("Búsqueda de usuario por userName");
		UserRepresentation userRep = buscarUsuarioPorUsername(userName, adapter, kc);
		
		if (userRep != null) {
			logger.info("Usuario encontrado: " + userRep.getUsername());

			CredentialRepresentation credenciales = new CredentialRepresentation();
			credenciales.setType(CredentialRepresentation.PASSWORD);
			credenciales.setValue(nuevaContrasena);  // Usar la nueva contraseña elegida por el usuario
			credenciales.setTemporary(false);  // Definir que no es una contraseña temporal
			
			logger.info("Actualizando contraseña en el Realm: " + adapter.getRealm() + " para el usuario: " + userRep.getUsername());

			try {
				// Actualizar la contraseña del usuario en Keycloak
				kc.realm(adapter.getRealm()).users().get(userRep.getId()).resetPassword(credenciales);
				logger.info("Contraseña actualizada exitosamente en Keycloak para el usuario: " + userRep.getUsername());
				return true;
			} catch (BadRequestException e) {
				logger.error("La contraseña que has elegido ya ha sido utilizada anteriormente. Por favor, selecciona una nueva contraseña.");
			} catch (Exception e) {
				logger.error("Error al cambiar la contraseña en Keycloak para el usuario: " + userRep.getUsername(), e);
			}
		} else {
			logger.warn("Usuario no encontrado: " + userName);
		}

		return false;  // Retornar false si no se pudo cambiar la contraseña
	}


	/**
     * GLPI 82800 Gestion Activar/Inactivar Usuario Empleador
     * 
	 *
	 *
     */
	@Override
	public ResultadoDTO gestionActivarInactivarEmpleador(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		ResultadoDTO dto = new ResultadoDTO();
		dto.setError(false);

		if (kc == null) {
			dto.setError(true);
			dto.setMensaje("Error: Keycloak no se ha inicializado correctamente.");
			return dto;
		}

		String realm = adapter.getRealm();
		if (realm == null) {
			dto.setError(true);
			dto.setMensaje("Error: El realm es nulo.");
			return dto;
		}

		String nombreUsuarioOriginal = usuario.getNombreUsuario();

		// Validar si el usuario existe en Keycloak
		if (validarExistenciaUsuario(nombreUsuarioOriginal, adapter, kc)) {
			ObtenerTareasAsignadasUsuario obtenerTareas = new ObtenerTareasAsignadasUsuario(usuario.getNombreUsuario());
			obtenerTareas.execute();
			List<TareaDTO> tareas = obtenerTareas.getResult();

			if (!usuario.isUsuarioActivo() && tareas != null && !tareas.isEmpty()) {
				dto.setError(true);
				dto.setMensaje(NO_INHABILITAR_USUARIO_REGISTRADO_MENSAJE);
				return dto;
			}

			UserRepresentation usuarioCCF = kc.realm(realm).users().get(usuario.getIdUsuario()).toRepresentation();
			if (usuarioCCF == null) {
				dto.setError(true);
				dto.setMensaje("Error: No se pudo obtener la representación del usuario.");
				return dto;
			}

			Map<String, Object> atributos = usuarioCCF.getAttributes();
			if (atributos == null) {
				atributos = new HashMap<>();
			}

			EntityManager em = entityManager; // Obtener EntityManager

			// Variables para auditoría
			String modificadoPor = user.getNombreUsuario();
			long fechaModificacionEpoch = Instant.now().getEpochSecond();
			String fechaModificacion = String.valueOf(fechaModificacionEpoch);

			// Actualizar y cargar el estado "usuarioActivo"
			String estado = usuario.isUsuarioActivo() ? "Activo" : "Inactivo";
			actualizarYCargarDatos(
				usuarioCCF, 
				em, 
				atributos, 
				"usuarioActivo", 
				estado, 
				modificadoPor, 
				fechaModificacion
			);

			atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
			atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));

			// Guardar los cambios en Keycloak
			if (usuarioCCF.isEnabled() != usuario.isUsuarioActivo()) {
				usuarioCCF.setEnabled(usuario.isUsuarioActivo());
				usuarioCCF.setAttributes(atributos);
				kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);
			}

		} else {
			dto.setError(true);
			dto.setMensaje("Error: El usuario no está registrado en el sistema.");
		}

		return dto;
	}

	/**
     * GLPI 82800 Gestion Crear Usuarios Masivos Empleador
     * 
	 *
	 *
     */
    @Override
    public void gestionMasivosEmpleador(List<UsuarioCCF> usuarios, UserDTO userDTO) {
		logger.info("UserDTO: " + userDTO.toString());
		logger.info("Nombre UsuarioDTO: " + userDTO.getNombreUsuario());
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        String creadoPor = userDTO != null && userDTO.getNombreUsuario() != null ? userDTO.getNombreUsuario() : "Masivamente";
        for (UsuarioCCF usuario : usuarios) {
            try {
                // Convertir el tipoIdentificacion usando el método auxiliar
                String tipoIdentificacionConvertido = convertirTipoIdentificacionEmpleador(usuario.getTipoIdentificacion());
                usuario.setTipoIdentificacion(tipoIdentificacionConvertido);

                // Consultar datos del empleador por número de identificación
                Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
                query.setParameter("numIdentificacion", usuario.getNumIdentificacion());
                query.setParameter("tipoIdentificacion", usuario.getTipoIdentificacion());
                List<Object[]> resultados = query.getResultList();

                if (resultados.isEmpty()) {
                    logger.warn("El empleador con numero de identificacion " + usuario.getNumIdentificacion() 
                        + " y tipo de documento " + usuario.getTipoIdentificacion() + " no existe en la BD.");
                    continue; // Saltar a la siguiente iteración para el próximo usuario
                }

                // Obtener datos del resultado de la consulta
                Object[] resultado = resultados.get(0);
                String razonSocial = (String) resultado[0];
                String primerNombreConsulta = (String) resultado[1];
                String primerApellidoConsulta = (String) resultado[2];
                String segundoNombreConsulta = (String) resultado[3];
                String segundoApellidoConsulta = (String) resultado[4];
                String emailConsulta = (String) resultado[5];

                // Asignar nombres y apellidos desde la consulta si no están en los datos de entrada
                if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().isEmpty()) {
                    usuario.setPrimerNombre(primerNombreConsulta);
                }
                if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().isEmpty()) {
                    usuario.setPrimerApellido(primerApellidoConsulta);
                }
                if (usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isEmpty()) {
                    usuario.setSegundoNombre(segundoNombreConsulta);
                }
                if (usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isEmpty()) {
                    usuario.setSegundoApellido(segundoApellidoConsulta);
                }
                HashMap<String, Object> atributos = new HashMap<>();
                // Manejo del email
                if (emailConsulta != null && !emailConsulta.isEmpty()) {
                    // Si ya tiene email en la consulta, setearlo en Keycloak
                    atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(emailConsulta));
                } else if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
                    // Si no tiene email en la consulta y hay en los datos de entrada, actualizar en la BD y Keycloak
                    Query updateQuery = entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_EMPLEADOR_POR_NUMERO_IDENTIFICACION);
                    updateQuery.setParameter("email", usuario.getEmail());
                    updateQuery.setParameter("numIdentificacion", usuario.getNumIdentificacion());
                    updateQuery.executeUpdate();
                }

                // Crear representación de usuario en Keycloak
                UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
                // usuarioCCF.setEmail(usuario.getEmail());
                usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
                usuarioCCF.setRealmRoles(usuario.getRoles());

                // Mapeo de atributos personalizados
                
                atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
                        Arrays.asList(String.valueOf(Boolean.FALSE)));
                atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
                        Arrays.asList(String.valueOf(Boolean.FALSE)));


                // Validar y asignar tipoIdentificacion y numIdentificacion
                if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() 
                    && usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {
                    atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
                            Arrays.asList(usuario.getTipoIdentificacion()));
                    atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
                            Arrays.asList(usuario.getNumIdentificacion()));
                } else {
                    throw new FunctionalConstraintException("Tipo de identificación y número de identificación son obligatorios.");
                }

                // Agregar segundo nombre y segundo apellido si existen
                if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
                    atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
                }
                if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
                    atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(usuario.getSegundoApellido()));
                }

                atributos.put(UserAttributesEnum.RAZON_SOCIAL.getNombre(), Arrays.asList(razonSocial));

                atributos.put("usuarioActivo", Arrays.asList("Activo"));

                // Agregar fechaCreacion en formato epoch
                Long fechaCreacionEpoch = Instant.now().getEpochSecond();
                atributos.put("fechaCreacion", Arrays.asList(String.valueOf(fechaCreacionEpoch)));

                // Agregar usuarioCreacion (por ejemplo, del objeto `user`)
                // String creadoPor = userDTO.getNombreUsuario();  
                atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));

                // Configuración del usuario en Keycloak
                usuarioCCF.setAttributes(atributos);
                String username = PREFIJO_EMPLEADOR + usuario.getTipoIdentificacion() + "_" + usuario.getNumIdentificacion();
                usuarioCCF.setUsername(username);

                // Crear el usuario en Keycloak
                Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
                if (response.getStatus() != Response.Status.CREATED.getStatusCode()
                        && response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                    logger.error("Error al crear el usuario en Keycloak para " + username + ": " + response.getStatusInfo());
                    continue; // Saltar al siguiente usuario en caso de error
                }

                // Asignar grupos y contraseña temporal
                agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_EMPLEADOR.getNombre(), usuarioCCF.getUsername(), adapter, kc);
                agregarAGrupo(GruposUsuariosEnum.ADMINISTRADOR_EMPLEADOR.getNombre(), usuarioCCF.getUsername(), adapter, kc);
                String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);

                // Enviar notificación al usuario creado
                Map<String, String> params = new HashMap<>();
                params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
                        usuario.getPrimerApellido(), usuario.getSegundoApellido()));
                params.put("usuario", usuario.getEmail());
                params.put("Password", tempPass);
                enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());

            } catch (Exception e) {
                logger.error("Error al crear el usuario con numero identificacion " + usuario.getNumIdentificacion(), e);
                // Continuar con el siguiente usuario en caso de error
            }
        }
    }

    private String convertirTipoIdentificacionEmpleador(String tipoIdentificacion) {
        Map<String, String> tipoIdentificacionMap = new HashMap<>();
        tipoIdentificacionMap.put("NI", "NIT");
        tipoIdentificacionMap.put("CC", "CEDULA_CIUDADANIA");
        tipoIdentificacionMap.put("CE", "CEDULA_EXTRANJERIA");
        tipoIdentificacionMap.put("TI", "TARJETA_IDENTIDAD");
        tipoIdentificacionMap.put("RC", "REGISTRO_CIVIL");
        tipoIdentificacionMap.put("PA", "PASAPORTE");
        tipoIdentificacionMap.put("CD", "CARNE_DIPLOMATICO");
        tipoIdentificacionMap.put("SC", "SALVOCONDUCTO");
        tipoIdentificacionMap.put("PE", "PERM_ESP_PERMANENCIA");
        tipoIdentificacionMap.put("PT", "PERM_PROT_TEMPORAL");

        return tipoIdentificacionMap.getOrDefault(tipoIdentificacion, tipoIdentificacion);
    }

	private String limpiarCampo(String valor) {
		if (valor == null || valor.trim().isEmpty() || "null".equalsIgnoreCase(valor.trim())) {
			return null;
		}
		return valor.trim();
	}

	/**
     * GLPI 82800 Gestion Consultar Usuario Persona
     * 
     * <b>Descripción</b>Método que se encarga de obtener usuarios persona
     *
     * * @return se retorna la lista con los usuarios obtenidos
     *
     */
    @Override
    public List<UsuarioGestionDTO> gestionConsultarPersona(
            String nombreUsuario, Boolean roles, String tipoIdentificacion, String numIdentificacion,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            Boolean usuarioActivo, String fechaCreacion, String creadoPor,
            String fechaModificacion, String modificadoPor) {


        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();		

        // Limpiar campos
        final String primerNombreFiltrado = limpiarCampo(primerNombre);
        final String segundoNombreFiltrado = limpiarCampo(segundoNombre);
        final String primerApellidoFiltrado = limpiarCampo(primerApellido);
        final String segundoApellidoFiltrado = limpiarCampo(segundoApellido);

        // Buscar todos los usuarios en Keycloak
        List<UserRepresentation> users = kc.realm(adapter.getRealm()).users()
                .search(null, null, null, null, 0, Integer.MAX_VALUE);

        // Filtrar usuarios según los criterios
        List<UserRepresentation> usuariosFiltrados = users.stream()
                .filter(user -> filtrarUsuario(user, nombreUsuario, tipoIdentificacion, numIdentificacion,
                        primerNombreFiltrado, primerApellidoFiltrado, segundoNombreFiltrado, segundoApellidoFiltrado,
                        usuarioActivo, fechaCreacion, creadoPor, fechaModificacion, modificadoPor))
                .collect(Collectors.toList());

        // Convertir a DTOs y completar datos si faltan
        List<UsuarioGestionDTO> resultado = usuariosFiltrados.stream()
                .map(user -> {
                    UsuarioGestionDTO dto = KeyCloakMapper.toUsuarioGestion(user);

                    // Verificar si faltan datos
                    boolean datosIncompletos = dto.getPrimerNombre() == null ||
                            dto.getPrimerApellido() == null ||
                            dto.getEmail() == null;

                    if (datosIncompletos && (tipoIdentificacion != null && numIdentificacion != null)) {
                        try {
                            Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION);
                            query.setParameter("numIdentificacion", numIdentificacion);
                            query.setParameter("tipoIdentificacion", tipoIdentificacion);

                            List<Object[]> resultados = query.getResultList();
                            if (!resultados.isEmpty()) {
                                Object[] datos = resultados.get(0);
                                if (dto.getPrimerNombre() == null) dto.setPrimerNombre((String) datos[0]);
                                if (dto.getPrimerApellido() == null) dto.setPrimerApellido((String) datos[1]);
                                if (dto.getSegundoNombre() == null) dto.setSegundoNombre((String) datos[2]);
                                if (dto.getSegundoApellido() == null) dto.setSegundoApellido((String) datos[3]);
                                if (dto.getEmail() == null) dto.setEmail((String) datos[4]);
                                if (dto.getNumIdentificacion() == null) dto.setNumIdentificacion((String) datos[7]);
                                if (dto.getTipoIdentificacion() == null) dto.setTipoIdentificacion((String) datos[8]);
                            }
                        } catch (Exception e) {
                            throw new TechnicalException("Error al consultar usuario", e);
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // Si no hubo usuarios en Keycloak, intentar consultar solo la BD
        if (resultado.isEmpty() &&
                ((tipoIdentificacion != null && numIdentificacion != null) ||
                        (primerNombreFiltrado != null || segundoNombreFiltrado != null || primerApellidoFiltrado != null || segundoApellidoFiltrado != null))) {
            try {
                Query query;
                if (tipoIdentificacion != null && numIdentificacion != null) {
                    query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION);
                    query.setParameter("tipoIdentificacion", tipoIdentificacion);
                    query.setParameter("numIdentificacion", numIdentificacion);
                } else {
                    query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NOMBRES_APELLIDOS);
                    query.setParameter("primerNombre", primerNombreFiltrado);
                    query.setParameter("segundoNombre", segundoNombreFiltrado);
                    query.setParameter("primerApellido", primerApellidoFiltrado);
                    query.setParameter("segundoApellido", segundoApellidoFiltrado);
                }

                List<Object[]> resultados = query.getResultList();
                if (!resultados.isEmpty()) {
                    Object[] datos = resultados.get(0);
                    UsuarioGestionDTO dto = new UsuarioGestionDTO();
                    dto.setPrimerNombre((String) datos[0]);
                    dto.setPrimerApellido((String) datos[1]);
                    dto.setSegundoNombre((String) datos[2]);
                    dto.setSegundoApellido((String) datos[3]);
                    dto.setEmail((String) datos[4]);
                    dto.setNumIdentificacion((String) datos[7]);
                    dto.setTipoIdentificacion((String) datos[8]);

                    // Si tenemos tipo y número, generamos nombre de usuario
                    if (tipoIdentificacion != null && numIdentificacion != null) {
                        dto.setNombreUsuario(tipoIdentificacion.toLowerCase() + "_" + numIdentificacion);
                    }

                    resultado.add(dto);
                }

            } catch (Exception e) {
                throw new TechnicalException("Error al consultar usuario solo en BD", e);
            }
        }

        return resultado;
    }

    // Método de filtrado de usuarios persona
    private boolean filtrarUsuario(UserRepresentation user,
            String nombreUsuario, String tipoIdentificacion, String numIdentificacion,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            Boolean usuarioActivo, String fechaCreacion, String creadoPor,
            String fechaModificacion, String modificadoPor) {

        Map<String, List<String>> attributes = convertirAtributos(user.getAttributes());
        Boolean estadoUsuario = user.isEnabled();
		Long fechaCreacionEpoch = user.getCreatedTimestamp();
		Long fechaCreacionKeycloak = fechaCreacionEpoch / 1000;

        // Verificar si la búsqueda incluye alguno de los atributos específicos
        boolean filtroEspecialActivo = fechaCreacion != null || creadoPor != null ||  
                                        fechaModificacion != null || modificadoPor != null ||  
                                        usuarioActivo != null ||  
                                        primerNombre != null || primerApellido != null ||  
                                        segundoNombre != null || segundoApellido != null ||  
                                        tipoIdentificacion != null || numIdentificacion != null;

        // Filtrar usuarios según criterios
        return (!filtroEspecialActivo || 
                (user.getUsername() != null && !user.getUsername().toLowerCase().startsWith("emp_") &&
                 (attributes == null || 
                  attributes.get("tipoUsuario") == null || 
                  "TRABAJADOR".equalsIgnoreCase(attributes.getOrDefault("tipoUsuario", Arrays.asList("")).get(0))))) &&
               (nombreUsuario == null || user.getUsername().equalsIgnoreCase(nombreUsuario)) &&
               (primerNombre == null || 
                    (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(primerNombre.toLowerCase()))) &&
               (primerApellido == null || 
                    (user.getLastName() != null && user.getLastName().toLowerCase().contains(primerApellido.toLowerCase()))) &&
               (segundoNombre == null || 
                    (attributes != null && attributes.getOrDefault("segundoNombre", Arrays.asList("")).get(0)
                            .toLowerCase().contains(segundoNombre.toLowerCase()))) &&
               (segundoApellido == null || 
                    (attributes != null && attributes.getOrDefault("segundoApellido", Arrays.asList("")).get(0)
                            .toLowerCase().contains(segundoApellido.toLowerCase()))) &&
               (tipoIdentificacion == null || 
                    (attributes != null && attributes.getOrDefault("tipoIdentificacion", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(tipoIdentificacion))) &&
               (numIdentificacion == null || 
                    (attributes != null && attributes.getOrDefault("numIdentificacion", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(numIdentificacion))) &&
               (usuarioActivo == null || estadoUsuario.equals(usuarioActivo)) &&
               (creadoPor == null || 
                    (attributes != null && attributes.getOrDefault("creadoPor", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(creadoPor))) &&
               (fechaCreacion == null ||  fechaCreacionKeycloak.equals(fechaCreacion)) &&
               (modificadoPor == null || 
                    (attributes != null && attributes.getOrDefault("modificadoPor", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(modificadoPor))) &&
               (fechaModificacion == null || 
                    (attributes != null && attributes.getOrDefault("fechaModificacion", Arrays.asList("")).get(0)
                            .equals(fechaModificacion)));
    }

	@SuppressWarnings("unchecked")
	private Map<String, List<String>> convertirAtributos(Map<String, Object> rawAttributes) {
		if (rawAttributes == null) return Collections.emptyMap();

		return rawAttributes.entrySet().stream()
				.filter(entry -> entry.getValue() instanceof List) // Filtrar solo listas
				.collect(Collectors.toMap(
						entry -> entry.getKey(), // Uso de lambda
						entry -> (List<String>) entry.getValue() // Convertir al tipo esperado
				));
	}

    /**
     * GLPI 82800 Gestion Crear Usuario Persona
     * 
     *
     *
     */
	@Override
	public void gestionCrearPersona(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		
		// Validar existencia de usuario por email
		if (!validarExistenciaUsuario(usuario.getEmail(), adapter, kc)) {
			try {
				// Consultar datos de la persona por número de identificación
				Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION);
				query.setParameter("numIdentificacion", usuario.getNumIdentificacion());
				query.setParameter("tipoIdentificacion", usuario.getTipoIdentificacion());

				// Intentar obtener el resultado
				List<Object[]> resultados = query.getResultList(); // Cambiar a getResultList()

				// Verificar si se encontraron resultados
				if (resultados.isEmpty()) {
					throw new FunctionalConstraintException("El usuario con tipo identificación " + usuario.getTipoIdentificacion() 
                                + " y número " + usuario.getNumIdentificacion() + " no existe en la base de datos.");
				}

				// Si hay resultados, proceder a procesar la información
				Object[] resultado = resultados.get(0);
				String primerNombreConsulta = (String) resultado[0];
				String primerApellidoConsulta = (String) resultado[1];
				String segundoNombreConsulta = (String) resultado[2];
				String segundoApellidoConsulta = (String) resultado[3];
				String emailConsulta = (String) resultado[4];
				String tipoTrabajadorConsultar = (String) resultado[5];
				String idEmpleadorConsulta = resultado[6] != null ? resultado[6].toString() : null;

				// Asignar nombres y apellidos desde la consulta si no están en los datos de entrada
				if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().isEmpty()) {
					usuario.setPrimerNombre(primerNombreConsulta);
				}
				if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().isEmpty()) {
					usuario.setPrimerApellido(primerApellidoConsulta);
				}
				if (usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isEmpty()) {
					usuario.setSegundoNombre(segundoNombreConsulta);
				}
				if (usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isEmpty()) {
					usuario.setSegundoApellido(segundoApellidoConsulta);
				}

				// Manejo del email
				if (emailConsulta != null && !emailConsulta.isEmpty()) {
					// Si ya tiene email en la consulta, setearlo en Keycloak
					// usuario.setEmail(emailConsulta);
				} else if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
					// Si no tiene email en la consulta y hay en los datos de entrada, actualizar en la BD y Keycloak
					Query updateQuery = entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_PERSONA_POR_NUMERO_IDENTIFICACION);
					updateQuery.setParameter("email", usuario.getEmail());
					updateQuery.setParameter("numIdentificacion", usuario.getNumIdentificacion());
					updateQuery.executeUpdate();
				}

				// Crear representación de usuario en Keycloak
				UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
				// usuarioCCF.setEmail(usuario.getEmail());
				usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
				usuarioCCF.setRealmRoles(usuario.getRoles());

				// Mapeo de atributos personalizados
				HashMap<String, Object> atributos = new HashMap<>();
				atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
						Arrays.asList(String.valueOf(Boolean.FALSE)));
				atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
						Arrays.asList(String.valueOf(Boolean.FALSE)));
				atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));

				// Agregar primerNombre y primerApellido a los atributos personalizados
				if (usuario.getPrimerNombre() != null) {
					atributos.put("primerNombre", Arrays.asList(usuario.getPrimerNombre()));
				}
				if (usuario.getPrimerApellido() != null) {
					atributos.put("primerApellido", Arrays.asList(usuario.getPrimerApellido()));
				}

				if (idEmpleadorConsulta != null && !idEmpleadorConsulta.isEmpty() && "TRABAJADOR_DEPENDIENTE".equals(tipoTrabajadorConsultar)) {
					atributos.put("idEmpleador", Arrays.asList(idEmpleadorConsulta));
				}

				// Agregar estado activo
				atributos.put("usuarioActivo", Arrays.asList("Activo"));

				atributos.put(UserAttributesEnum.TIPO_USUARIO.getNombre(), Arrays.asList("TRABAJADOR"));

				// Validar y asignar tipoIdentificacion y numIdentificacion
				if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() 
					&& usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {
					atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
							Arrays.asList(usuario.getTipoIdentificacion()));
					atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
							Arrays.asList(usuario.getNumIdentificacion()));
				} else {
					throw new FunctionalConstraintException("Tipo de identificación y número de identificación son obligatorios.");
				}

				// Agregar segundo nombre y segundo apellido si existen
				if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
					atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
				}
				if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
					atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(usuario.getSegundoApellido()));
				}

				// Agregar fechaCreacion en formato epoch
				Long fechaCreacionEpoch = Instant.now().getEpochSecond();
				atributos.put("fechaCreacion", Arrays.asList(String.valueOf(fechaCreacionEpoch)));

				// Agregar usuarioCreacion (por ejemplo, del objeto `user`)
				String creadoPor = user.getNombreUsuario();  
				atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));

				usuarioCCF.setAttributes(atributos);

				// Generar el username con el prefijo correcto basado en el tipoIdentificacion
				String prefix;
				switch (usuario.getTipoIdentificacion()) {
					case "REGISTRO_CIVIL":
						prefix = "REGISTRO_CIVIL_";
						break;
					case "TARJETA_IDENTIDAD":
						prefix = "TARJETA_IDENTIDAD_";
						break;
					case "CEDULA_CIUDADANIA":
						prefix = "CEDULA_CIUDADANIA_";
						break;
					case "CEDULA_EXTRANJERIA":
						prefix = "CEDULA_EXTRANJERIA_";
						break;
					case "PASAPORTE":
						prefix = "PASAPORTE_";
						break;
					case "CARNE_DIPLOMATICO":
						prefix = "CARNE_DIPLOMATICO_";
						break;
					case "SALVOCONDUCTO":
						prefix = "SALVOCONDUCTO_";
						break;
					case "PERM_ESP_PERMANENCIA":
						prefix = "PERM_ESP_PERMANENCIA_";
						break;
					case "PERM_PROT_TEMPORAL":
						prefix = "PERM_PROT_TEMPORAL_";
						break;
					default:
						throw new FunctionalConstraintException("Tipo de identificación no válido.");
				}

				// Crear el username usando el prefijo y numIdentificacion
				String username = prefix + usuario.getNumIdentificacion();
				usuarioCCF.setUsername(username);

				// Crear el usuario en Keycloak
				Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
				if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					&& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
					logger.error("Error al crear el usuario en Keycloak Debido a que ya existe un usario con esa informacion: " + response.getStatusInfo());
					throw new TechnicalException("No se pudo crear el usuario. Código de error: " + response.getStatus());
				}

				// Asignar al grupo por defecto
				agregarAGrupo(GruposUsuariosEnum.PERSONA_AFILIADA.getNombre(), usuarioCCF.getUsername(), adapter, kc);

				// Asignar contraseña temporal y enviar notificación
				String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);
				Map<String, String> params = new HashMap<>();
				params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
						usuario.getPrimerApellido(), usuario.getSegundoApellido()));
				params.put("usuario", usuario.getEmail());
				params.put("Password", tempPass);
				enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());

			} catch (FunctionalConstraintException e) {
				logger.warn("Excepción de restricción funcional: " + e.getMessage());
				throw e; // Lanza la excepción si deseas que la propague
			} catch (Exception e) {
				logger.error("Excepción al crear el usuario en Keycloak", e);
				throw new TechnicalException("Error al crear el usuario en Keycloak Debido a que ya existe un usario con esa informacion ", e);
			}
		} else {
			throw new FunctionalConstraintException(USUARIO_REGISTRADO_MENSAJE);
		}
	}

    /**
     * GLPI 82800 Gestion Actualizar Usuario Persona
     * 
     *
     *
     */
    @Override
    public ResultadoDTO gestionActualizarPersona(UsuarioCCF usuario, UserDTO user) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        ResultadoDTO dto = new ResultadoDTO();
        dto.setError(false);

        if (kc == null) {
            dto.setError(true);
            dto.setMensaje("Error: Keycloak no se ha inicializado correctamente.");
            return dto;
        }

        String realm = adapter.getRealm();
        if (realm == null) {
            dto.setError(true);
            dto.setMensaje("Error: El realm es nulo.");
            return dto;
        }

        // Buscar al usuario por ID (en lugar de por nombre de usuario)
        UserRepresentation usuarioCCF = kc.realm(realm).users().get(usuario.getIdUsuario()).toRepresentation();
        if (usuarioCCF == null) {
            dto.setError(true);
            dto.setMensaje("Error: No se pudo obtener la representación del usuario.");
            return dto;
        }

        Map<String, Object> atributos = usuarioCCF.getAttributes();
        if (atributos == null) {
            atributos = new HashMap<>();
        }

		EntityManager em = entityManager;

        // Variables para registrar auditoría
        String modificadoPor = user.getNombreUsuario();  
        long fechaModificacionEpoch = Instant.now().getEpochSecond();
        String fechaModificacion = String.valueOf(fechaModificacionEpoch);

        // Actualización de los datos básicos del usuario
        usuarioCCF.setFirstName(usuario.getPrimerNombre());
        usuarioCCF.setLastName(usuario.getPrimerApellido());
        usuarioCCF.setEmail(usuario.getEmail()); 

        // Actualizar y auditar los datos en Keycloak
            actualizarYCargarDatosPersona(usuarioCCF, em, atributos, 
                "email", usuario.getEmail(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosPersona(usuarioCCF, em, atributos, 
                "tipoIdentificacion", usuario.getTipoIdentificacion(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosPersona(usuarioCCF, em, atributos, 
                "numIdentificacion", usuario.getNumIdentificacion(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosPersona(usuarioCCF, em, atributos, 
                "primerNombre", usuario.getPrimerNombre(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosPersona(usuarioCCF, em, atributos, 
                "segundoNombre", usuario.getSegundoNombre(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosPersona(usuarioCCF, em, atributos, 
                "primerApellido", usuario.getPrimerApellido(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosPersona(usuarioCCF, em, atributos, 
                "segundoApellido", usuario.getSegundoApellido(), modificadoPor, fechaModificacion);

        atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
        atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));
        atributos.put(UserAttributesEnum.TIPO_USUARIO.getNombre(), Arrays.asList("TRABAJADOR"));

        // Guardar cambios en Keycloak
        // usuarioCCF.setEnabled(usuario.isUsuarioActivo());
        // usuarioCCF.setEmailVerified(usuario.isEmailVerified());
        usuarioCCF.setAttributes(atributos);
        kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);

        // Gestionar cambio de nombre de usuario si el tipo y número de identificación han cambiado
        String nuevoNombreUsuario = null;
        boolean requiereCambioNombreUsuario = false;

        if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() &&
            usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {

            String prefijo;
            switch (usuario.getTipoIdentificacion()) {
                case "REGISTRO_CIVIL":
                    prefijo = "REGISTRO_CIVIL_";
                    break;
                case "TARJETA_IDENTIDAD":
                    prefijo = "TARJETA_IDENTIDAD_";
                    break;
                case "CEDULA_CIUDADANIA":
                    prefijo = "CEDULA_CIUDADANIA_";
                    break;
                case "CEDULA_EXTRANJERIA":
                    prefijo = "CEDULA_EXTRANJERIA_";
                    break;
                case "PASAPORTE":
                    prefijo = "PASAPORTE_";
                    break;
                case "CARNE_DIPLOMATICO":
                    prefijo = "CARNE_DIPLOMATICO_";
                    break;
                case "SALVOCONDUCTO":
                    prefijo = "SALVOCONDUCTO_";
                    break;
                case "PERM_ESP_PERMANENCIA":
                    prefijo = "PERM_ESP_PERMANENCIA_";
                    break;
                case "PERM_PROT_TEMPORAL":
                    prefijo = "PERM_PROT_TEMPORAL_";
                    break;
                default:
                    dto.setError(true);
                    dto.setMensaje("Error: Tipo de identificación no permitido.");
                    return dto;
            }
            nuevoNombreUsuario = prefijo + usuario.getNumIdentificacion();
        }

        // Si el nombre de usuario ha cambiado, actualizarlo
        if (!usuarioCCF.getUsername().equals(nuevoNombreUsuario)) {
            try {
                List<UserRepresentation> usuariosConNuevoNombre = kc.realm(realm).users().search(nuevoNombreUsuario, null, null);

                // Verificar si el usuario con el nuevo nombre es el mismo usuario
                boolean esMismoUsuario = usuariosConNuevoNombre.stream()
                    .anyMatch(u -> u.getId().equals(usuario.getIdUsuario()));

                if (usuariosConNuevoNombre.isEmpty() || esMismoUsuario) {
                    usuarioCCF.setUsername(nuevoNombreUsuario);
                    kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);
                } else {
                    dto.setError(true);
                    dto.setMensaje("Error: El nuevo nombre de usuario ya está en uso.");
                    return dto;
                }
            } catch (Exception e) {
                dto.setError(true);
                dto.setMensaje("Error al actualizar el nombre de usuario: " + e.getMessage());
                return dto;
            }

            // Actualizar los atributos relacionados con la identificación
            atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(), Arrays.asList(usuario.getTipoIdentificacion()));
            atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(), Arrays.asList(usuario.getNumIdentificacion()));
        }

        // Gestión de grupos
        for (GroupRepresentation grupoConsultado : kc.realm(realm).users().get(usuario.getIdUsuario()).groups()) {
            quitarAGrupoId(grupoConsultado.getId(), nuevoNombreUsuario, adapter, kc);
        }

        agregarAGrupo(GruposUsuariosEnum.PERSONA_AFILIADA.getNombre(), nuevoNombreUsuario, adapter, kc);

        if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
            for (String grupo : usuario.getGrupos()) {
                agregarAGrupoId(grupo, nuevoNombreUsuario, adapter, kc);
            }
        }
        dto.setMensaje("Usuario actualizado exitosamente.");
        return dto;
    }

	private void actualizarYCargarDatosPersona(UserRepresentation usuario, EntityManager em, 
        Map<String, Object> atributos, String campo, String nuevoValor, 
        String modificadoPor, String fechaModificacion) {

		String valorAnterior = ((List<String>) atributos.getOrDefault(campo, Arrays.asList(""))).get(0);

		if (nuevoValor != null && !nuevoValor.equals(valorAnterior)) {
			// Actualizar en Keycloak
			atributos.put(campo, Arrays.asList(nuevoValor));

			// Registrar en la base de datos
			em.createNamedQuery(NamedQueriesConstants.INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_PERSONA)
				.setParameter("usuarioEditado", usuario.getUsername())
				.setParameter("campoModificado", campo)
				.setParameter("valorAnterior", valorAnterior)
				.setParameter("nuevoValor", nuevoValor)
				.setParameter("fechaModificacion", new java.sql.Timestamp(System.currentTimeMillis()))
				.setParameter("modificadoPor", modificadoPor)
				.executeUpdate();
		}
	}

    /**
     * GLPI 82800 Gestion Restablecer Contraseña Usuario Persona
     * 
     *
     *
     */
    // @Override
    // @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    // public void gestionRestablecerContrasenaPersona(CambiarContrasenaGestionUsuarioDTO dto) {
    //     logger.info("Iniciando proceso de restablecimiento de contraseña para el usuario: " + dto.getNombreUsuario());

    //     // Validar que el email no esté vacío
    //     if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
    //         logger.warn("Email vacío para el usuario: " + dto.getNombreUsuario() + " Por favor, ingrese su email.");
	// 		return;
    //     }

    //     // Validar formato de email
    //     if (!isValidEmail(dto.getEmail())) {
    //         logger.warn("Formato de email inválido para el usuario: " + dto.getNombreUsuario() + " Por favor, ingrese un email válido.");
	// 		return;
    //     }

    //     // Obtener el adaptador de Keycloak
    //     KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
    //             .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
    //     Keycloak kc = adapter.getKc();

    //     // Validar si el email está asociado a un usuario en Keycloak
    //     logger.info("Validando existencia de usuario en Keycloak");
    //     UserRepresentation userRepresentation = validarExistenciaUsuarioPorEmail(dto.getEmail(), adapter, kc);
    //     if (userRepresentation != null) {
    //         // Enviar correo para restablecer la contraseña
    //         enviarCorreoRestablecerContrasena(dto.getEmail(), dto.getNombreUsuario());
    //     } else {
    //         logger.info("El email ingresado no está registrado: " + dto.getEmail());
    //     }
    // }

    // Método para validar el formato de email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    // Método para validar existencia de usuario por email en Keycloak
	private UserRepresentation validarExistenciaUsuarioPorEmail(String email, KeycloakAdapter adapter, Keycloak kc) {
		try {
			// Primero, buscar por el email en el campo específico
			List<UserRepresentation> usersByEmail = kc.realm(adapter.getRealm()).users().search(null, null, null, email, null, null);
			if (!usersByEmail.isEmpty()) {
				return usersByEmail.get(0);
			}

			// Si no se encuentra, buscar por el atributo personalizado de email
			List<UserRepresentation> allUsers = kc.realm(adapter.getRealm()).users().search(null, null, null);
			for (UserRepresentation user : allUsers) {
				Map<String, Object> attributes = user.getAttributes();
				if (attributes != null && attributes.containsKey("email")) {
					Object emailAttribute = attributes.get("email");
					if(emailAttribute instanceof List){
						List<?> customEmails = (List<?>) emailAttribute;
						if (customEmails != null && customEmails.contains(email)) {
							return user; 
						}
					}
				}
			}

			logger.info("Usuario no encontrado por email: " + email);
			throw new FunctionalConstraintException("Usuario no encontrado por email");
		} catch (Exception e) {
			logger.error("Error al buscar el usuario por email: " + email, e);
			throw new FunctionalConstraintException("Error al buscar el usuario por email");
		}
	}

	// Método para que envia el enlace para cambiar la contraseña del usuario
    @Override
    public void restablecerContrasena(String email) throws Exception {
        logger.info("**__** Se inicia el método usuarioBusiness.restablecerContrasena");

        // Validar que el correo no sea nulo o vacío
        if (email == null || email.isEmpty()) {
            logger.warn("El correo electrónico no puede estar vacío.");
        }

		if (!isValidEmail(email)) {
            logger.warn("Formato de email inválido. Por favor, ingrese un email válido.");
            return;
        }

        // Obtener el adaptador y la instancia de Keycloak
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        try {
            enviarContrasenaCambioPersonalizado(email, adapter, kc);
        } catch (Exception e) {
            logger.error("Error al intentar restablecer la contraseña para el correo: " + email, e);
            throw e;
        }
    }

	// Inicio metodos adicionales  GLPI 95241
    private void enviarContrasenaCambioPersonalizado(String email, KeycloakAdapter adapter, Keycloak kc) throws Exception {
        try {
            UserRepresentation userRepresentation = validarExistenciaUsuarioPorEmail(email, adapter, kc);

            if (userRepresentation == null) {
                logger.warn("Usuario no encontrado con el correo: " + email);
                throw new FunctionalConstraintException("Usuario no encontrado con el correo.");
            }

            // 1. Generar la nueva contraseña
            String nuevaContrasena = generarClaveGenerica();
            logger.info("Nueva contraseña generada para el usuario: " + email);

            // 2. Actualizar la contraseña del usuario en Keycloak
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(true);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(nuevaContrasena);

            UserResource userResource = kc.realm(adapter.getRealm()).users().get(userRepresentation.getId());
            userResource.resetPassword(passwordCred);
            logger.info("Contraseña de usuario actualizada en Keycloak para: " + email);

            // 3. Obtener el nombre de usuario
            String usernameToDisplay = userRepresentation.getUsername();

            // 4. Enviar el correo usando el nuevo método en EmailService
            emailService.sendNewPasswordEmail(email, usernameToDisplay, nuevaContrasena);
            logger.info("Correo con nueva contraseña enviado a: " + email);

        } catch (NotFoundException e) {
            logger.error("No se encontró el usuario con el email: " + email, e);
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("Error con el correo proporcionado: " + email, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error al enviar el correo de restablecimiento de contraseña para el usuario: " + email, e);
            throw e;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED) // Se mantuvo REQUIRED porque actualiza Keycloak
    public void confirmarCambioContrasena(CambiarContrasenaGestionUsuarioDTO dto) {
        logger.info("Iniciando procesamiento de cambio de contraseña para usuario.");

        // --- 1. Validar Token de Restablecimiento ---
        if (dto.getTokenRestablecimiento() == null || dto.getTokenRestablecimiento().isEmpty()) {
            logger.warn("Token de restablecimiento no proporcionado.");
            throw new IllegalArgumentException("Token de restablecimiento es requerido.");
        }

        String userIdentifierFromToken = null;
        try {
            // Asegúrate de que el método en PasswordResetTokenService se llama 'validatePasswordResetToken'
            // y acepta String token, String email
            userIdentifierFromToken = passwordResetTokenService.validatePasswordResetToken(dto.getTokenRestablecimiento(), dto.getEmail());
        } catch (Exception e) {
            logger.error("Error al validar token de restablecimiento: " + e.getMessage(), e);
            throw new SecurityException("Token de restablecimiento inválido o expirado. Por favor, solicite uno nuevo.");
        }

        if (userIdentifierFromToken == null) {
            logger.warn("Token de restablecimiento inválido o expirado, o email no coincide.");
            throw new SecurityException("Token de restablecimiento inválido o expirado. Por favor, solicite uno nuevo.");
        }

        // --- 2. Validar Nueva Contraseña y Confirmación ---
        if (dto.getPassNuevo() == null || dto.getPassNuevo().isEmpty() ||
            dto.getPassConfirmacion() == null || dto.getPassConfirmacion().isEmpty()) {
            logger.warn("Nueva contraseña o confirmación vacía para el usuario: " + userIdentifierFromToken);
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no pueden estar vacías.");
        }

        if (!dto.getPassNuevo().equals(dto.getPassConfirmacion())) {
            logger.warn("La nueva contraseña y la confirmación no coinciden para el usuario: " + userIdentifierFromToken);
            throw new IllegalArgumentException("La nueva contraseña y la confirmación no coinciden.");
        }

        // --- 3. Aplicar Políticas de Contraseña (REGEX) ---
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";

        if (!dto.getPassNuevo().matches(passwordRegex)) {
            logger.warn("La nueva contraseña no cumple con las políticas de seguridad para el usuario: " + userIdentifierFromToken);
            throw new IllegalArgumentException("La contraseña debe tener al menos 12 caracteres, incluyendo una mayúscula, una minúscula, un número y un carácter especial (ej. @, #, $).");
        }

        // --- 4. Obtener Adaptador de Keycloak ---
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                    .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        // --- 5. Encontrar el Usuario en Keycloak (usando el ID obtenido del token) ---
        // userIdentifierFromToken es el ID de Keycloak, así que lo obtenemos directamente.
        UserRepresentation userToUpdate;
        try {
            userToUpdate = kc.realm(adapter.getRealm()).users().get(userIdentifierFromToken).toRepresentation();
            if (userToUpdate == null) {
                // Aunque get().toRepresentation() suele lanzar NotFoundException, es bueno verificar si devuelve null
                logger.error("Usuario no encontrado en Keycloak para el ID: " + userIdentifierFromToken);
                throw new IllegalArgumentException("No se pudo encontrar el usuario asociado al token de restablecimiento.");
            }
        } catch (javax.ws.rs.NotFoundException e) { // Captura la excepción específica de JAX-RS para no encontrado
            logger.error("Usuario no encontrado en Keycloak para el ID: " + userIdentifierFromToken + ". Error: " + e.getMessage());
            throw new IllegalArgumentException("No se pudo encontrar el usuario asociado al token de restablecimiento.");
        } catch (Exception e) {
            logger.error("Error inesperado al buscar usuario en Keycloak por ID: " + userIdentifierFromToken + ". Error: " + e.getMessage(), e);
            throw new RuntimeException("Fallo al buscar el usuario.", e);
        }
        logger.info("Usuario encontrado en Keycloak: " + userToUpdate.getUsername() + " (" + userToUpdate.getEmail() + ")");

        // --- 6. Actualizar la Contraseña en Keycloak ---
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(dto.getPassNuevo());
        passwordCred.setTemporary(false); // No es una contraseña temporal, es permanente

        try {
            kc.realm(adapter.getRealm()).users().get(userToUpdate.getId()).resetPassword(passwordCred);
            logger.info("Contraseña actualizada exitosamente en Keycloak para el usuario: " + userToUpdate.getUsername());

            // --- 7. Invalidar el Token de Restablecimiento ---
            // ESTA LÍNEA SE REMUEVE (O COMENTA) POR TU SOLICITUD TEMPORAL
            // passwordResetTokenService.invalidateToken(dto.getTokenRestablecimiento());
            // logger.info("Token de restablecimiento invalidado para el usuario: " + userToUpdate.getUsername());

        } catch (javax.ws.rs.BadRequestException e) {
            String errorMessage = e.getResponse().readEntity(String.class);
            logger.error("Error de Keycloak al actualizar contraseña para " + userToUpdate.getUsername() + ": " + errorMessage, e);
            if (errorMessage != null && errorMessage.contains("Password history policy failed")) {
                throw new IllegalArgumentException("La nueva contraseña no puede ser una contraseña usada anteriormente. Por favor, elija una diferente.");
            }
            throw new IllegalArgumentException("No se pudo actualizar la contraseña en Keycloak. Verifique las políticas de contraseña: " + errorMessage);
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar contraseña en Keycloak para " + userToUpdate.getUsername() + ": " + e.getMessage(), e);
            throw new RuntimeException("Fallo al actualizar la contraseña.", e);
        }
    }
	// Terminan metodos adicionales  GLPI 95241

    private void enviarContrasenaCambio(String email, KeycloakAdapter adapter, Keycloak kc) throws Exception{
        try {
            // Buscar el usuario por su email
            UserRepresentation userRepresentation = validarExistenciaUsuarioPorEmail(email, adapter, kc);

            if (userRepresentation == null) {
                logger.warn("Usuario no encontrado con el correo: " + email);
                throw new FunctionalConstraintException("Usuario no encontrado con el correo");
            }

			// Obtener el email desde los atributos personalizados
			Map<String, Object> atributosPersonalizados = userRepresentation.getAttributes();
			String emailPersonalizado = null;

			if (atributosPersonalizados != null && atributosPersonalizados.containsKey(UserAttributesEnum.EMAIL.getNombre())) {
				List<String> emails = (List<String>) atributosPersonalizados.get(UserAttributesEnum.EMAIL.getNombre());
				if (emails != null && !emails.isEmpty()) {
					emailPersonalizado = emails.get(0); 
					logger.info("Correo Atributo Personalizado: " + emailPersonalizado);
				}
			}

			// Verificar que el email proporcionado coincida con el registrado en Keycloak (campo predeterminado o atributo personalizado)
			if (!email.equals(userRepresentation.getEmail()) && !email.equals(emailPersonalizado)) {
				logger.warn("El correo proporcionado no coincide con el registrado en Keycloak.");
				throw new FunctionalConstraintException("El correo proporcionado no coincide con el registrado en Keycloak");
			}

            // Obtener el recurso del usuario
            UserResource userResource = kc.realm(adapter.getRealm()).users().get(userRepresentation.getId());

			// Si el correo predeterminado está vacío, asignar el correo del atributo personalizado para el envio del correo
			if (userRepresentation.getEmail() == null || userRepresentation.getEmail().isEmpty()) {
				userRepresentation.setEmail(emailPersonalizado); 
				userResource.update(userRepresentation);
			}

            // Establecer las acciones necesarias
            List<String> requiredActions = Arrays.asList("UPDATE_PASSWORD");
            userRepresentation.setRequiredActions(requiredActions);
            userResource.update(userRepresentation);

            // Enviar el correo de restablecimiento de contraseña con redirección
            userResource.executeActionsEmail(requiredActions);

            logger.info("Correo de restablecimiento de contraseña enviado a: " + email);

			// Restaurar el estado original del usuario (opcional)
			// if (userRepresentation.getEmail() == null || userRepresentation.getEmail().isEmpty()) {
			//     userRepresentation.setEmail(null); // Restaurar el campo predeterminado a null
			//     userResource.update(userRepresentation); // Actualizar el usuario en Keycloak
			// }
        } catch (NotFoundException e) {
            logger.error("No se encontró el usuario con el email: " + email, e);
			throw e; 
        } catch (IllegalArgumentException e) {
            logger.error("Error con el correo proporcionado: " + email, e);
			throw e; 
        } catch (Exception e) {
            logger.error("Error al enviar el correo de restablecimiento de contraseña para el usuario: " + email, e);
			throw e; 
        }
    }
	// Fin Método para que envia el enlace para cambiar la contraseña del usuario

    /**
     * GLPI 82800 Gestion Activar/Inactivar Usuario Persona
     * 
     *
     *
     */
    @Override
    public ResultadoDTO gestionActivarInactivarPersona(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		ResultadoDTO dto = new ResultadoDTO();
		dto.setError(false);

		if (kc == null) {
			dto.setError(true);
			dto.setMensaje("Error: Keycloak no se ha inicializado correctamente.");
			return dto;
		}

		String realm = adapter.getRealm();
		if (realm == null) {
			dto.setError(true);
			dto.setMensaje("Error: El realm es nulo.");
			return dto;
		}

		String nombreUsuarioOriginal = usuario.getNombreUsuario();

		// Validar si el usuario existe en Keycloak
		if (validarExistenciaUsuario(nombreUsuarioOriginal, adapter, kc)) {
            ObtenerTareasAsignadasUsuario obtenerTareas = new ObtenerTareasAsignadasUsuario(usuario.getNombreUsuario());
            obtenerTareas.execute();
            List<TareaDTO> tareas = obtenerTareas.getResult();
			UserRepresentation usuarioCCF = kc.realm(realm).users().get(usuario.getIdUsuario()).toRepresentation();
			if (usuarioCCF == null) {
				dto.setError(true);
				dto.setMensaje("Error: No se pudo obtener la representación del usuario.");
				return dto;
			}

			Map<String, Object> atributos = usuarioCCF.getAttributes();
			if (atributos == null) {
				atributos = new HashMap<>();
			}

			EntityManager em = entityManager; // Obtener EntityManager

			// Variables para auditoría
			String modificadoPor = user.getNombreUsuario();
			long fechaModificacionEpoch = Instant.now().getEpochSecond();
			String fechaModificacion = String.valueOf(fechaModificacionEpoch);

			// Actualizar y cargar el estado "usuarioActivo"
			String estado = usuario.isUsuarioActivo() ? "Activo" : "Inactivo";
			actualizarYCargarDatosPersona(
				usuarioCCF, 
				em, 
				atributos, 
				"usuarioActivo", 
				estado, 
				modificadoPor, 
				fechaModificacion
			);

            if (tareas != null && !tareas.isEmpty() && !usuario.isUsuarioActivo()) {
                dto.setError(true);
                dto.setMensaje(NO_INHABILITAR_USUARIO_REGISTRADO_MENSAJE);
                return dto;
            }

			atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
			atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));

			// Guardar los cambios en Keycloak
			usuarioCCF.setEnabled(usuario.isUsuarioActivo());
			usuarioCCF.setAttributes(atributos);
			kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);

		} else {
			dto.setError(true);
			dto.setMensaje("Error: El usuario no está registrado en el sistema.");
		}

		return dto;
    }

    /**
     * GLPI 82800 Gestion Crear Usuarios Masivos Persona
     * 
     *
     *
     */
    public void gestionMasivosPersona(List<UsuarioCCF> usuarios, UserDTO user) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
		String creadoPor = user != null && user.getNombreUsuario() != null ? user.getNombreUsuario() : "Masivamente";
        for (UsuarioCCF usuario : usuarios) {
            try {
                // Convertir el tipoIdentificacion usando el método auxiliar
                String tipoIdentificacionConvertido = convertirTipoIdentificacion(usuario.getTipoIdentificacion());
                usuario.setTipoIdentificacion(tipoIdentificacionConvertido);

                // Consultar datos del empleador por número de identificación
                Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
                query.setParameter("numIdentificacion", usuario.getNumIdentificacion());
                query.setParameter("tipoIdentificacion", usuario.getTipoIdentificacion());
                List<Object[]> resultados = query.getResultList();

                if (resultados.isEmpty()) {
                    logger.warn("El trabajador con numero de identificacion " + usuario.getNumIdentificacion() 
                        + " y tipo de documento " + usuario.getTipoIdentificacion() + " no existe en la BD.");
                    continue; // Saltar a la siguiente iteración para el próximo usuario
                }

                // Si hay resultados, proceder a procesar la información
                Object[] resultado = resultados.get(0);
                String primerNombreConsulta = (String) resultado[0];
                String primerApellidoConsulta = (String) resultado[1];
                String segundoNombreConsulta = (String) resultado[2];
                String segundoApellidoConsulta = (String) resultado[3];
                String emailConsulta = (String) resultado[4];
                String tipoTrabajadorConsultar = (String) resultado[5];
                String idEmpleadorConsulta = resultado[6] != null ? resultado[6].toString() : null;

                // Asignar nombres y apellidos desde la consulta si no están en los datos de entrada
                if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().isEmpty()) {
                    usuario.setPrimerNombre(primerNombreConsulta);
                }
                if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().isEmpty()) {
                    usuario.setPrimerApellido(primerApellidoConsulta);
                }
                if (usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isEmpty()) {
                    usuario.setSegundoNombre(segundoNombreConsulta);
                }
                if (usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isEmpty()) {
                    usuario.setSegundoApellido(segundoApellidoConsulta);
                }
                HashMap<String, Object> atributos = new HashMap<>();
                // Manejo del email
                if (emailConsulta != null && !emailConsulta.isEmpty()) {
                    // Si ya tiene email en la consulta, setearlo en Keycloak
                    atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(emailConsulta));
                } else if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
                    // Si no tiene email en la consulta y hay en los datos de entrada, actualizar en la BD y Keycloak
                    Query updateQuery = entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_PERSONA_POR_NUMERO_IDENTIFICACION);
                    updateQuery.setParameter("email", usuario.getEmail());
                    updateQuery.setParameter("numIdentificacion", usuario.getNumIdentificacion());
                    updateQuery.executeUpdate();
                }

                // Crear representación de usuario en Keycloak
                UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
                // usuarioCCF.setEmail(usuario.getEmail());
                usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
                usuarioCCF.setRealmRoles(usuario.getRoles());

                // Mapeo de atributos personalizados
                
                atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
                        Arrays.asList(String.valueOf(Boolean.FALSE)));
                atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
                        Arrays.asList(String.valueOf(Boolean.FALSE)));
                

                // Agregar primerNombre y primerApellido a los atributos personalizados
                if (usuario.getPrimerNombre() != null) {
                    atributos.put("primerNombre", Arrays.asList(usuario.getPrimerNombre()));
                }
                if (usuario.getPrimerApellido() != null) {
                    atributos.put("primerApellido", Arrays.asList(usuario.getPrimerApellido()));
                }

                if (idEmpleadorConsulta != null && !idEmpleadorConsulta.isEmpty() && "TRABAJADOR_DEPENDIENTE".equals(tipoTrabajadorConsultar)) {
                    atributos.put("idEmpleador", Arrays.asList(idEmpleadorConsulta));
                }

                // Agregar estado activo
                atributos.put("usuarioActivo", Arrays.asList("Activo"));

                atributos.put(UserAttributesEnum.TIPO_USUARIO.getNombre(), Arrays.asList("TRABAJADOR"));

                // Validar y asignar tipoIdentificacion y numIdentificacion
                if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() 
                    && usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {
                    atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
                            Arrays.asList(usuario.getTipoIdentificacion()));
                    atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
                            Arrays.asList(usuario.getNumIdentificacion()));
                } else {
                    throw new FunctionalConstraintException("Tipo de identificación y número de identificación son obligatorios.");
                }

                // Agregar segundo nombre y segundo apellido si existen
                if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
                    atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
                }
                if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
                    atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(usuario.getSegundoApellido()));
                }

                // Agregar fechaCreacion en formato epoch
                Long fechaCreacionEpoch = Instant.now().getEpochSecond();
                atributos.put("fechaCreacion", Arrays.asList(String.valueOf(fechaCreacionEpoch)));

				// Agregar usuarioCreacion (por ejemplo, del objeto `user`)
				//String creadoPor = user.getNombreUsuario();  
				atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));

                // Configuración del usuario en Keycloak
                usuarioCCF.setAttributes(atributos);
                String username = usuario.getTipoIdentificacion() + "_" + usuario.getNumIdentificacion();
                usuarioCCF.setUsername(username);

                // Crear el usuario en Keycloak
                Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
                if (response.getStatus() != Response.Status.CREATED.getStatusCode()
                        && response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                    logger.error("Error al crear el usuario en Keycloak para " + username + ": " + response.getStatusInfo());
                    continue; // Saltar al siguiente usuario en caso de error
                }

                // Asignar grupos y contraseña temporal
                agregarAGrupo(GruposUsuariosEnum.PERSONA_AFILIADA.getNombre(), usuarioCCF.getUsername(), adapter, kc);
                String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);

                // Enviar notificación al usuario creado
                Map<String, String> params = new HashMap<>();
                params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
                        usuario.getPrimerApellido(), usuario.getSegundoApellido()));
                params.put("usuario", usuario.getEmail());
                params.put("Password", tempPass);
                enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());

            } catch (Exception e) {
                logger.error("Error al crear el usuario con numero identificacion " + usuario.getNumIdentificacion(), e);
                // Continuar con el siguiente usuario en caso de error
            }
        }
    }

    private String convertirTipoIdentificacion(String tipoIdentificacion) {
        Map<String, String> tipoIdentificacionMap = new HashMap<>();
        tipoIdentificacionMap.put("CC", "CEDULA_CIUDADANIA");
        tipoIdentificacionMap.put("CE", "CEDULA_EXTRANJERIA");
        tipoIdentificacionMap.put("TI", "TARJETA_IDENTIDAD");
        tipoIdentificacionMap.put("RC", "REGISTRO_CIVIL");
        tipoIdentificacionMap.put("PA", "PASAPORTE");
        tipoIdentificacionMap.put("CD", "CARNE_DIPLOMATICO");
        tipoIdentificacionMap.put("SC", "SALVOCONDUCTO");
        tipoIdentificacionMap.put("PE", "PERM_ESP_PERMANENCIA");
        tipoIdentificacionMap.put("PT", "PERM_PROT_TEMPORAL");

        return tipoIdentificacionMap.getOrDefault(tipoIdentificacion, tipoIdentificacion);
    }

	/**
     * GLPI 82800 Gestion Consultar Usuario Ccf
     * 
     * <b>Descripción</b>Método que se encarga de obtener usuarios ccf
     *
     * * @return se retorna la lista con los usuarios obtenidos
     *
     */
    @Override
    public List<UsuarioGestionDTO> gestionConsultarCcf(
            String nombreUsuario, Boolean roles, String tipoIdentificacion, String numIdentificacion,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            Boolean usuarioActivo, String fechaCreacion, String creadoPor,
            String fechaModificacion, String modificadoPor) {

        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        // Limpiar campos
        final String primerNombreFiltrado = limpiarCampo(primerNombre);
        final String segundoNombreFiltrado = limpiarCampo(segundoNombre);
        final String primerApellidoFiltrado = limpiarCampo(primerApellido);
        final String segundoApellidoFiltrado = limpiarCampo(segundoApellido);

        // Buscar todos los usuarios en Keycloak
        List<UserRepresentation> users = kc.realm(adapter.getRealm()).users()
                .search(null, null, null, null, 0, Integer.MAX_VALUE);

        // Filtrar usuarios según los criterios
        List<UserRepresentation> usuariosFiltrados = users.stream()
                .filter(user -> filtrarUsuarioCcf(user, nombreUsuario, tipoIdentificacion, numIdentificacion,
                        primerNombreFiltrado, primerApellidoFiltrado, segundoNombreFiltrado, segundoApellidoFiltrado,
                        usuarioActivo, fechaCreacion, creadoPor, fechaModificacion, modificadoPor))
                .collect(Collectors.toList());

        // Convertir a DTOs y completar datos si faltan
        List<UsuarioGestionDTO> resultado = usuariosFiltrados.stream()
                .map(user -> {
                    UsuarioGestionDTO dto = KeyCloakMapper.toUsuarioGestion(user);

                    // Verificar si faltan datos
                    boolean datosIncompletos = dto.getPrimerNombre() == null ||
                            dto.getPrimerApellido() == null ||
                            dto.getEmail() == null;

                    if (datosIncompletos && (tipoIdentificacion != null && numIdentificacion != null)) {
                        try {
                            Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION);
                            query.setParameter("numIdentificacion", numIdentificacion);
                            query.setParameter("tipoIdentificacion", tipoIdentificacion);

                            List<Object[]> resultados = query.getResultList();
                            if (!resultados.isEmpty()) {
                                Object[] datos = resultados.get(0);
                                if (dto.getPrimerNombre() == null) dto.setPrimerNombre((String) datos[0]);
                                if (dto.getPrimerApellido() == null) dto.setPrimerApellido((String) datos[1]);
                                if (dto.getSegundoNombre() == null) dto.setSegundoNombre((String) datos[2]);
                                if (dto.getSegundoApellido() == null) dto.setSegundoApellido((String) datos[3]);
                                if (dto.getEmail() == null) dto.setEmail((String) datos[4]);
                                if (dto.getNumIdentificacion() == null) dto.setNumIdentificacion((String) datos[7]);
                                if (dto.getTipoIdentificacion() == null) dto.setTipoIdentificacion((String) datos[8]);
                            }
                        } catch (Exception e) {
                            throw new TechnicalException("Error al consultar usuario", e);
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // Si no hubo usuarios en Keycloak, intentar consultar solo la BD
        if (resultado.isEmpty() &&
                ((tipoIdentificacion != null && numIdentificacion != null) ||
                        (primerNombreFiltrado != null || segundoNombreFiltrado != null || primerApellidoFiltrado != null || segundoApellidoFiltrado != null))) {
            try {
                Query query;
                if (tipoIdentificacion != null && numIdentificacion != null) {
                    query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION);
                    query.setParameter("tipoIdentificacion", tipoIdentificacion);
                    query.setParameter("numIdentificacion", numIdentificacion);
                } else {
                    query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NOMBRES_APELLIDOS);
                    query.setParameter("primerNombre", primerNombreFiltrado);
                    query.setParameter("segundoNombre", segundoNombreFiltrado);
                    query.setParameter("primerApellido", primerApellidoFiltrado);
                    query.setParameter("segundoApellido", segundoApellidoFiltrado);
                }

                List<Object[]> resultados = query.getResultList();
                if (!resultados.isEmpty()) {
                    Object[] datos = resultados.get(0);
                    UsuarioGestionDTO dto = new UsuarioGestionDTO();
                    dto.setPrimerNombre((String) datos[0]);
                    dto.setPrimerApellido((String) datos[1]);
                    dto.setSegundoNombre((String) datos[2]);
                    dto.setSegundoApellido((String) datos[3]);
                    dto.setEmail((String) datos[4]);
                    dto.setNumIdentificacion((String) datos[7]);
                    dto.setTipoIdentificacion((String) datos[8]);

                    // Si tenemos tipo y número, generamos nombre de usuario
                    if (tipoIdentificacion != null && numIdentificacion != null) {
                        dto.setNombreUsuario(tipoIdentificacion.toLowerCase() + "_" + numIdentificacion);
                    }

                    resultado.add(dto);
                }

            } catch (Exception e) {
                throw new TechnicalException("Error al consultar usuario solo en BD", e);
            }
        }

        return resultado;
    }

    // Método de filtrado de usuarios ccf
    private boolean filtrarUsuarioCcf(UserRepresentation user,
            String nombreUsuario, String tipoIdentificacion, String numIdentificacion,
            String primerNombre, String primerApellido, String segundoNombre, String segundoApellido,
            Boolean usuarioActivo, String fechaCreacion, String creadoPor,
            String fechaModificacion, String modificadoPor) {

        Map<String, List<String>> attributes = convertirAtributos(user.getAttributes());
        Boolean estadoUsuario = user.isEnabled();
		Long fechaCreacionEpoch = user.getCreatedTimestamp();
		Long fechaCreacionKeycloak = fechaCreacionEpoch / 1000;

        // Verificar si la búsqueda incluye alguno de los atributos específicos
        boolean filtroEspecialActivo = fechaCreacion != null || creadoPor != null ||  
                                        fechaModificacion != null || modificadoPor != null ||  
                                        usuarioActivo != null ||  
                                        primerNombre != null || primerApellido != null ||  
                                        segundoNombre != null || segundoApellido != null ||  
                                        tipoIdentificacion != null || numIdentificacion != null;

        // Filtrar usuarios según criterios
        return (!filtroEspecialActivo || 
                (user.getUsername() != null && !user.getUsername().toLowerCase().startsWith("emp_") &&
                 (attributes == null || 
                  attributes.get("tipoUsuario") == null || 
                  "CCF".equalsIgnoreCase(attributes.getOrDefault("tipoUsuario", Arrays.asList("")).get(0))))) &&
               (nombreUsuario == null || user.getUsername().equalsIgnoreCase(nombreUsuario)) &&
               (primerNombre == null || 
                    (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(primerNombre.toLowerCase()))) &&
               (primerApellido == null || 
                    (user.getLastName() != null && user.getLastName().toLowerCase().contains(primerApellido.toLowerCase()))) &&
               (segundoNombre == null || 
                    (attributes != null && attributes.getOrDefault("segundoNombre", Arrays.asList("")).get(0)
                            .toLowerCase().contains(segundoNombre.toLowerCase()))) &&
               (segundoApellido == null || 
                    (attributes != null && attributes.getOrDefault("segundoApellido", Arrays.asList("")).get(0)
                            .toLowerCase().contains(segundoApellido.toLowerCase()))) &&
               (tipoIdentificacion == null || 
                    (attributes != null && attributes.getOrDefault("tipoIdentificacion", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(tipoIdentificacion))) &&
               (numIdentificacion == null || 
                    (attributes != null && attributes.getOrDefault("numIdentificacion", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(numIdentificacion))) &&
               (usuarioActivo == null || estadoUsuario.equals(usuarioActivo)) &&
               (creadoPor == null || 
                    (attributes != null && attributes.getOrDefault("creadoPor", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(creadoPor))) &&
               (fechaCreacion == null ||  fechaCreacionKeycloak.equals(fechaCreacion)) &&
               (modificadoPor == null || 
                    (attributes != null && attributes.getOrDefault("modificadoPor", Arrays.asList("")).get(0)
                            .equalsIgnoreCase(modificadoPor))) &&
               (fechaModificacion == null || 
                    (attributes != null && attributes.getOrDefault("fechaModificacion", Arrays.asList("")).get(0)
                            .equals(fechaModificacion)));
    }

    /**
     * GLPI 82800 Gestion Crear Usuario Ccf
     * 
     *
     *
     */
	@Override
	public void gestionCrearCcf(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
						.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		
		// Validar existencia de usuario por email
		if (!validarExistenciaUsuario(usuario.getEmail(), adapter, kc)) {
				try {
						// Consultar datos de la persona por número de identificación
						Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION);
						query.setParameter("numIdentificacion", usuario.getNumIdentificacion());
						query.setParameter("tipoIdentificacion", usuario.getTipoIdentificacion());

						// Intentar obtener el resultado
						List<Object[]> resultados = query.getResultList(); // Cambiar a getResultList()

						// Verificar si se encontraron resultados
						if (resultados.isEmpty()) {
							throw new FunctionalConstraintException("El usuario con tipo identificación " + usuario.getTipoIdentificacion() 
                                + " y número " + usuario.getNumIdentificacion() + " no existe en la base de datos.");
						}

						// Si hay resultados, proceder a procesar la información
						Object[] resultado = resultados.get(0);
						String primerNombreConsulta = (String) resultado[0];
						String primerApellidoConsulta = (String) resultado[1];
						String segundoNombreConsulta = (String) resultado[2];
						String segundoApellidoConsulta = (String) resultado[3];
						String emailConsulta = (String) resultado[4];
						String tipoTrabajadorConsultar = (String) resultado[5];
						String idEmpleadorConsulta = resultado[6] != null ? resultado[6].toString() : null;

						// Asignar nombres y apellidos desde la consulta si no están en los datos de entrada
						if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().isEmpty()) {
								usuario.setPrimerNombre(primerNombreConsulta);
						}
						if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().isEmpty()) {
								usuario.setPrimerApellido(primerApellidoConsulta);
						}
						if (usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isEmpty()) {
								usuario.setSegundoNombre(segundoNombreConsulta);
						}
						if (usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isEmpty()) {
								usuario.setSegundoApellido(segundoApellidoConsulta);
						}

						// Manejo del email
						if (emailConsulta != null && !emailConsulta.isEmpty()) {
								// Si ya tiene email en la consulta, setearlo en Keycloak
								// usuario.setEmail(emailConsulta);
						} else if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
								// Si no tiene email en la consulta y hay en los datos de entrada, actualizar en la BD y Keycloak
								Query updateQuery = entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_PERSONA_POR_NUMERO_IDENTIFICACION);
								updateQuery.setParameter("email", usuario.getEmail());
								updateQuery.setParameter("numIdentificacion", usuario.getNumIdentificacion());
								updateQuery.executeUpdate();
						}

						// Crear representación de usuario en Keycloak
						UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
						// usuarioCCF.setEmail(usuario.getEmail());
						usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
						usuarioCCF.setRealmRoles(usuario.getRoles());

						// Mapeo de atributos personalizados
						HashMap<String, Object> atributos = new HashMap<>();
						atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
										Arrays.asList(String.valueOf(Boolean.FALSE)));
						atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
										Arrays.asList(String.valueOf(Boolean.FALSE)));
						atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));

						// Agregar primerNombre y primerApellido a los atributos personalizados
						if (usuario.getPrimerNombre() != null) {
								atributos.put("primerNombre", Arrays.asList(usuario.getPrimerNombre()));
						}
						if (usuario.getPrimerApellido() != null) {
								atributos.put("primerApellido", Arrays.asList(usuario.getPrimerApellido()));
						}

						if (idEmpleadorConsulta != null && !idEmpleadorConsulta.isEmpty() && "TRABAJADOR_DEPENDIENTE".equals(tipoTrabajadorConsultar)) {
							atributos.put("idEmpleador", Arrays.asList(idEmpleadorConsulta));
						}

						// Agregar estado activo
						atributos.put("usuarioActivo", Arrays.asList("Activo"));

						atributos.put(UserAttributesEnum.TIPO_USUARIO.getNombre(), Arrays.asList("CCF"));

						// Validar y asignar tipoIdentificacion y numIdentificacion
						if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() 
								&& usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {
								atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
												Arrays.asList(usuario.getTipoIdentificacion()));
								atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
												Arrays.asList(usuario.getNumIdentificacion()));
						} else {
								throw new FunctionalConstraintException("Tipo de identificación y número de identificación son obligatorios.");
						}

						// Agregar segundo nombre y segundo apellido si existen
						if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
								atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
						}
						if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
								atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(usuario.getSegundoApellido()));
						}

						// Agregar fechaCreacion en formato epoch
						Long fechaCreacionEpoch = Instant.now().getEpochSecond();
						atributos.put("fechaCreacion", Arrays.asList(String.valueOf(fechaCreacionEpoch)));

						// Agregar usuarioCreacion (por ejemplo, del objeto `user`)
						String creadoPor = user.getNombreUsuario();  
						atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));

						usuarioCCF.setAttributes(atributos);

						// Generar el username con el prefijo correcto basado en el tipoIdentificacion
						String prefix;
						switch (usuario.getTipoIdentificacion()) {
								case "REGISTRO_CIVIL":
										prefix = "REGISTRO_CIVIL_";
										break;
								case "TARJETA_IDENTIDAD":
										prefix = "TARJETA_IDENTIDAD_";
										break;
								case "CEDULA_CIUDADANIA":
										prefix = "CEDULA_CIUDADANIA_";
										break;
								case "CEDULA_EXTRANJERIA":
										prefix = "CEDULA_EXTRANJERIA_";
										break;
								case "PASAPORTE":
										prefix = "PASAPORTE_";
										break;
								case "CARNE_DIPLOMATICO":
										prefix = "CARNE_DIPLOMATICO_";
										break;
								case "SALVOCONDUCTO":
										prefix = "SALVOCONDUCTO_";
										break;
								case "PERM_ESP_PERMANENCIA":
										prefix = "PERM_ESP_PERMANENCIA_";
										break;
								case "PERM_PROT_TEMPORAL":
										prefix = "PERM_PROT_TEMPORAL_";
										break;
								default:
										throw new FunctionalConstraintException("Tipo de identificación no válido.");
						}

						// Crear el username usando el prefijo y numIdentificacion
						String username = prefix + usuario.getNumIdentificacion();
						usuarioCCF.setUsername(username);

						// Crear el usuario en Keycloak
						Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
						if (response.getStatus() != Response.Status.CREATED.getStatusCode()
								&& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
								logger.error("Error al crear el usuario en Keycloak Debido a que ya existe un usario con esa informacion: " + response.getStatusInfo());
								throw new TechnicalException("No se pudo crear el usuario. Código de error: " + response.getStatus());
						}

						// Asignar al grupo por defecto
						agregarAGrupo(GruposUsuariosEnum.PERSONA_AFILIADA.getNombre(), usuarioCCF.getUsername(), adapter, kc);

						// Asignar contraseña temporal y enviar notificación
						String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);
						Map<String, String> params = new HashMap<>();
						params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
										usuario.getPrimerApellido(), usuario.getSegundoApellido()));
						params.put("usuario", usuario.getEmail());
						params.put("Password", tempPass);
						enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());

				} catch (FunctionalConstraintException e) {
						logger.warn("Excepción de restricción funcional: " + e.getMessage());
						throw e; // Lanza la excepción si deseas que la propague
				} catch (Exception e) {
						logger.error("Excepción al crear el usuario en Keycloak", e);
						throw new TechnicalException("Error al crear el usuario en Keycloak Debido a que ya existe un usario con esa informacion", e);
				}
		} else {
				throw new FunctionalConstraintException(USUARIO_REGISTRADO_MENSAJE);
		}
	}

    /**
     * GLPI 82800 Gestion Actualizar Usuario Ccf
     * 
     *
     *
     */
    @Override
    public ResultadoDTO gestionActualizarCcf(UsuarioCCF usuario, UserDTO user) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        ResultadoDTO dto = new ResultadoDTO();
        dto.setError(false);

        if (kc == null) {
            dto.setError(true);
            dto.setMensaje("Error: Keycloak no se ha inicializado correctamente.");
            return dto;
        }

        String realm = adapter.getRealm();
        if (realm == null) {
            dto.setError(true);
            dto.setMensaje("Error: El realm es nulo.");
            return dto;
        }

        // Buscar al usuario por ID (en lugar de por nombre de usuario)
        UserRepresentation usuarioCCF = kc.realm(realm).users().get(usuario.getIdUsuario()).toRepresentation();
        if (usuarioCCF == null) {
            dto.setError(true);
            dto.setMensaje("Error: No se pudo obtener la representación del usuario.");
            return dto;
        }

        Map<String, Object> atributos = usuarioCCF.getAttributes();
        if (atributos == null) {
            atributos = new HashMap<>();
        }

		EntityManager em = entityManager;

        // Variables para registrar auditoría
        String modificadoPor = user.getNombreUsuario();  
        long fechaModificacionEpoch = Instant.now().getEpochSecond();
        String fechaModificacion = String.valueOf(fechaModificacionEpoch);

        // Actualización de los datos básicos del usuario
        usuarioCCF.setFirstName(usuario.getPrimerNombre());
        usuarioCCF.setLastName(usuario.getPrimerApellido());
        usuarioCCF.setEmail(usuario.getEmail()); 

        // Actualizar y auditar los datos en Keycloak
            actualizarYCargarDatosCcf(usuarioCCF, em, atributos, 
                "email", usuario.getEmail(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosCcf(usuarioCCF, em, atributos, 
                "tipoIdentificacion", usuario.getTipoIdentificacion(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosCcf(usuarioCCF, em, atributos, 
                "numIdentificacion", usuario.getNumIdentificacion(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosCcf(usuarioCCF, em, atributos, 
                "primerNombre", usuario.getPrimerNombre(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosCcf(usuarioCCF, em, atributos, 
                "segundoNombre", usuario.getSegundoNombre(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosCcf(usuarioCCF, em, atributos, 
                "primerApellido", usuario.getPrimerApellido(), modificadoPor, fechaModificacion);

            actualizarYCargarDatosCcf(usuarioCCF, em, atributos, 
                "segundoApellido", usuario.getSegundoApellido(), modificadoPor, fechaModificacion);

        atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
        atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));
        atributos.put(UserAttributesEnum.TIPO_USUARIO.getNombre(), Arrays.asList("CCF"));

        // Guardar cambios en Keycloak
        // usuarioCCF.setEnabled(usuario.isUsuarioActivo());
        // usuarioCCF.setEmailVerified(usuario.isEmailVerified());
        usuarioCCF.setAttributes(atributos);
        kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);

        // Gestionar cambio de nombre de usuario si el tipo y número de identificación han cambiado
        String nuevoNombreUsuario = null;
        boolean requiereCambioNombreUsuario = false;

        if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() &&
            usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {

            String prefijo;
            switch (usuario.getTipoIdentificacion()) {
                case "REGISTRO_CIVIL":
                    prefijo = "REGISTRO_CIVIL_";
                    break;
                case "TARJETA_IDENTIDAD":
                    prefijo = "TARJETA_IDENTIDAD_";
                    break;
                case "CEDULA_CIUDADANIA":
                    prefijo = "CEDULA_CIUDADANIA_";
                    break;
                case "CEDULA_EXTRANJERIA":
                    prefijo = "CEDULA_EXTRANJERIA_";
                    break;
                case "PASAPORTE":
                    prefijo = "PASAPORTE_";
                    break;
                case "CARNE_DIPLOMATICO":
                    prefijo = "CARNE_DIPLOMATICO_";
                    break;
                case "SALVOCONDUCTO":
                    prefijo = "SALVOCONDUCTO_";
                    break;
                case "PERM_ESP_PERMANENCIA":
                    prefijo = "PERM_ESP_PERMANENCIA_";
                    break;
                case "PERM_PROT_TEMPORAL":
                    prefijo = "PERM_PROT_TEMPORAL_";
                    break;
                default:
                    dto.setError(true);
                    dto.setMensaje("Error: Tipo de identificación no permitido.");
                    return dto;
            }
            nuevoNombreUsuario = prefijo + usuario.getNumIdentificacion();
        }

        // Si el nombre de usuario ha cambiado, actualizarlo
        if (!usuarioCCF.getUsername().equals(nuevoNombreUsuario)) {
            try {
                List<UserRepresentation> usuariosConNuevoNombre = kc.realm(realm).users().search(nuevoNombreUsuario, null, null);

                // Verificar si el usuario con el nuevo nombre es el mismo usuario
                boolean esMismoUsuario = usuariosConNuevoNombre.stream()
                    .anyMatch(u -> u.getId().equals(usuario.getIdUsuario()));

                if (usuariosConNuevoNombre.isEmpty() || esMismoUsuario) {
                    usuarioCCF.setUsername(nuevoNombreUsuario);
                    kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);
                } else {
                    dto.setError(true);
                    dto.setMensaje("Error: El nuevo nombre de usuario ya está en uso.");
                    return dto;
                }
            } catch (Exception e) {
                dto.setError(true);
                dto.setMensaje("Error al actualizar el nombre de usuario: " + e.getMessage());
                return dto;
            }

            // Actualizar los atributos relacionados con la identificación
            atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(), Arrays.asList(usuario.getTipoIdentificacion()));
            atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(), Arrays.asList(usuario.getNumIdentificacion()));
        }

        // Gestión de grupos
        for (GroupRepresentation grupoConsultado : kc.realm(realm).users().get(usuario.getIdUsuario()).groups()) {
            quitarAGrupoId(grupoConsultado.getId(), nuevoNombreUsuario, adapter, kc);
        }

        agregarAGrupo(GruposUsuariosEnum.PERSONA_AFILIADA.getNombre(), nuevoNombreUsuario, adapter, kc);

        if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
            for (String grupo : usuario.getGrupos()) {
                agregarAGrupoId(grupo, nuevoNombreUsuario, adapter, kc);
            }
        }
        dto.setMensaje("Usuario actualizado exitosamente.");
        return dto;
    }

	private void actualizarYCargarDatosCcf(UserRepresentation usuario, EntityManager em, 
        Map<String, Object> atributos, String campo, String nuevoValor, 
        String modificadoPor, String fechaModificacion) {

		String valorAnterior = ((List<String>) atributos.getOrDefault(campo, Arrays.asList(""))).get(0);

		if (nuevoValor != null && !nuevoValor.equals(valorAnterior)) {
			// Actualizar en Keycloak
			atributos.put(campo, Arrays.asList(nuevoValor));

			// Registrar en la base de datos
			em.createNamedQuery(NamedQueriesConstants.INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_CCF)
				.setParameter("usuarioEditado", usuario.getUsername())
				.setParameter("campoModificado", campo)
				.setParameter("valorAnterior", valorAnterior)
				.setParameter("nuevoValor", nuevoValor)
				.setParameter("fechaModificacion", new java.sql.Timestamp(System.currentTimeMillis()))
				.setParameter("modificadoPor", modificadoPor)
				.executeUpdate();
		}
	}

    /**
     * GLPI 82800 Gestion Restablecer Contraseña Usuario Ccf
     * 
     *
     *
     */
	// @Override
	// @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	// public void gestionRestablecerContrasenaCcf(CambiarContrasenaGestionUsuarioDTO dto) {
    //     logger.info("Iniciando proceso de restablecimiento de contraseña para el usuario: " + dto.getNombreUsuario());

    //     // Validar que el email no esté vacío
    //     if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
    //         logger.warn("Email vacío para el usuario: " + dto.getNombreUsuario() + " Por favor, ingrese su email.");
	// 		return;
    //     }

    //     // Validar formato de email
    //     if (!isValidEmail(dto.getEmail())) {
    //         logger.warn("Formato de email inválido para el usuario: " + dto.getNombreUsuario() + " Por favor, ingrese un email válido.");
	// 		return;
    //     }

    //     // Obtener el adaptador de Keycloak
    //     KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
    //             .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
    //     Keycloak kc = adapter.getKc();

    //     // Validar si el email está asociado a un usuario en Keycloak
    //     logger.info("Validando existencia de usuario en Keycloak");
    //     UserRepresentation userRepresentation = validarExistenciaUsuarioPorEmail(dto.getEmail(), adapter, kc);
    //     if (userRepresentation != null) {
    //         // Enviar correo para restablecer la contraseña
    //         enviarCorreoRestablecerContrasena(dto.getEmail(), dto.getNombreUsuario());
    //     } else {
    //         logger.info("El email ingresado no está registrado: " + dto.getEmail());
    //     }
	// }

    /**
     * GLPI 82800 Gestion Activar/Inactivar Usuario Ccf
     * 
     *
     *
     */
    @Override
    public ResultadoDTO gestionActivarInactivarCcf(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		ResultadoDTO dto = new ResultadoDTO();
		dto.setError(false);

		if (kc == null) {
			dto.setError(true);
			dto.setMensaje("Error: Keycloak no se ha inicializado correctamente.");
			return dto;
		}

		String realm = adapter.getRealm();
		if (realm == null) {
			dto.setError(true);
			dto.setMensaje("Error: El realm es nulo.");
			return dto;
		}

		String nombreUsuarioOriginal = usuario.getNombreUsuario();

		// Validar si el usuario existe en Keycloak
		if (validarExistenciaUsuario(nombreUsuarioOriginal, adapter, kc)) {
            ObtenerTareasAsignadasUsuario obtenerTareas = new ObtenerTareasAsignadasUsuario(usuario.getNombreUsuario());
            obtenerTareas.execute();
            List<TareaDTO> tareas = obtenerTareas.getResult();
			UserRepresentation usuarioCCF = kc.realm(realm).users().get(usuario.getIdUsuario()).toRepresentation();
			if (usuarioCCF == null) {
				dto.setError(true);
				dto.setMensaje("Error: No se pudo obtener la representación del usuario.");
				return dto;
			}

			Map<String, Object> atributos = usuarioCCF.getAttributes();
			if (atributos == null) {
				atributos = new HashMap<>();
			}

			EntityManager em = entityManager; // Obtener EntityManager

			// Variables para auditoría
			String modificadoPor = user.getNombreUsuario();
			long fechaModificacionEpoch = Instant.now().getEpochSecond();
			String fechaModificacion = String.valueOf(fechaModificacionEpoch);

			// Actualizar y cargar el estado "usuarioActivo"
			String estado = usuario.isUsuarioActivo() ? "Activo" : "Inactivo";
			actualizarYCargarDatosCcf(
				usuarioCCF, 
				em, 
				atributos, 
				"usuarioActivo", 
				estado, 
				modificadoPor, 
				fechaModificacion
			);

            if (tareas != null && !tareas.isEmpty() && !usuario.isUsuarioActivo()) {
                dto.setError(true);
                dto.setMensaje(NO_INHABILITAR_USUARIO_REGISTRADO_MENSAJE);
                return dto;
            }

			atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
			atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));

			// Guardar los cambios en Keycloak
			usuarioCCF.setEnabled(usuario.isUsuarioActivo());
			usuarioCCF.setAttributes(atributos);
			kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);

		} else {
			dto.setError(true);
			dto.setMensaje("Error: El usuario no está registrado en el sistema.");
		}

		return dto;
    }

    /**
     * GLPI 82800 Gestion Crear Usuarios Masivos Ccf
     * 
     *
     *
     */
    public void gestionMasivosCcf(List<UsuarioCCF> usuarios, UserDTO user) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
		String creadoPor = user != null && user.getNombreUsuario() != null ? user.getNombreUsuario() : "Masivamente";
        for (UsuarioCCF usuario : usuarios) {
            try {
                // Convertir el tipoIdentificacion usando el método auxiliar
                String tipoIdentificacionConvertido = convertirTipoIdentificacion(usuario.getTipoIdentificacion());
                usuario.setTipoIdentificacion(tipoIdentificacionConvertido);

                // Consultar datos del empleador por número de identificación
                Query query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
                query.setParameter("numIdentificacion", usuario.getNumIdentificacion());
                query.setParameter("tipoIdentificacion", usuario.getTipoIdentificacion());
                List<Object[]> resultados = query.getResultList();

                if (resultados.isEmpty()) {
                    logger.warn("La persona con numero de identificacion " + usuario.getNumIdentificacion() 
                        + " y tipo de documento " + usuario.getTipoIdentificacion() + " no existe en la BD.");
                    continue; // Saltar a la siguiente iteración para el próximo usuario
                }

                // Si hay resultados, proceder a procesar la información
                Object[] resultado = resultados.get(0);
                String primerNombreConsulta = (String) resultado[0];
                String primerApellidoConsulta = (String) resultado[1];
                String segundoNombreConsulta = (String) resultado[2];
                String segundoApellidoConsulta = (String) resultado[3];
                String emailConsulta = (String) resultado[4];
                String tipoTrabajadorConsultar = (String) resultado[5];
                String idEmpleadorConsulta = resultado[6] != null ? resultado[6].toString() : null;

                // Asignar nombres y apellidos desde la consulta si no están en los datos de entrada
                if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().isEmpty()) {
                    usuario.setPrimerNombre(primerNombreConsulta);
                }
                if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().isEmpty()) {
                    usuario.setPrimerApellido(primerApellidoConsulta);
                }
                if (usuario.getSegundoNombre() == null || usuario.getSegundoNombre().isEmpty()) {
                    usuario.setSegundoNombre(segundoNombreConsulta);
                }
                if (usuario.getSegundoApellido() == null || usuario.getSegundoApellido().isEmpty()) {
                    usuario.setSegundoApellido(segundoApellidoConsulta);
                }
                HashMap<String, Object> atributos = new HashMap<>();
                // Manejo del email
                if (emailConsulta != null && !emailConsulta.isEmpty()) {
                    // Si ya tiene email en la consulta, setearlo en Keycloak
                    atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(emailConsulta));
                } else if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
                    // Si no tiene email en la consulta y hay en los datos de entrada, actualizar en la BD y Keycloak
                    Query updateQuery = entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_PERSONA_POR_NUMERO_IDENTIFICACION);
                    updateQuery.setParameter("email", usuario.getEmail());
                    updateQuery.setParameter("numIdentificacion", usuario.getNumIdentificacion());
                    updateQuery.executeUpdate();
                }

                // Crear representación de usuario en Keycloak
                UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
                // usuarioCCF.setEmail(usuario.getEmail());
                usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
                usuarioCCF.setRealmRoles(usuario.getRoles());

                // Mapeo de atributos personalizados
                
                atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
                        Arrays.asList(String.valueOf(Boolean.FALSE)));
                atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
                        Arrays.asList(String.valueOf(Boolean.FALSE)));

                // Agregar primerNombre y primerApellido a los atributos personalizados
                if (usuario.getPrimerNombre() != null) {
                    atributos.put("primerNombre", Arrays.asList(usuario.getPrimerNombre()));
                }
                if (usuario.getPrimerApellido() != null) {
                    atributos.put("primerApellido", Arrays.asList(usuario.getPrimerApellido()));
                }

                if (idEmpleadorConsulta != null && !idEmpleadorConsulta.isEmpty() && "TRABAJADOR_DEPENDIENTE".equals(tipoTrabajadorConsultar)) {
                    atributos.put("idEmpleador", Arrays.asList(idEmpleadorConsulta));
                }

                // Agregar estado activo
                atributos.put("usuarioActivo", Arrays.asList("Activo"));

                atributos.put(UserAttributesEnum.TIPO_USUARIO.getNombre(), Arrays.asList("CCF"));

                // Validar y asignar tipoIdentificacion y numIdentificacion
                if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().isEmpty() 
                    && usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().isEmpty()) {
                    atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
                            Arrays.asList(usuario.getTipoIdentificacion()));
                    atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
                            Arrays.asList(usuario.getNumIdentificacion()));
                } else {
                    throw new FunctionalConstraintException("Tipo de identificación y número de identificación son obligatorios.");
                }

                // Agregar segundo nombre y segundo apellido si existen
                if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
                    atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(), Arrays.asList(usuario.getSegundoNombre()));
                }
                if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
                    atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(), Arrays.asList(usuario.getSegundoApellido()));
                }

                // Agregar fechaCreacion en formato epoch
                Long fechaCreacionEpoch = Instant.now().getEpochSecond();
                atributos.put("fechaCreacion", Arrays.asList(String.valueOf(fechaCreacionEpoch)));

				// Agregar usuarioCreacion (por ejemplo, del objeto `user`)
				//String creadoPor = user.getNombreUsuario();  
				atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));

                // Configuración del usuario en Keycloak
                usuarioCCF.setAttributes(atributos);
                String username = usuario.getTipoIdentificacion() + "_" + usuario.getNumIdentificacion();
                usuarioCCF.setUsername(username);

                // Crear el usuario en Keycloak
                Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
                if (response.getStatus() != Response.Status.CREATED.getStatusCode()
                        && response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                    logger.error("Error al crear el usuario en Keycloak para " + username + ": " + response.getStatusInfo());
                    continue; // Saltar al siguiente usuario en caso de error
                }

                // Asignar grupos y contraseña temporal
                agregarAGrupo(GruposUsuariosEnum.PERSONA_AFILIADA.getNombre(), usuarioCCF.getUsername(), adapter, kc);
                String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);

                // Enviar notificación al usuario creado
                Map<String, String> params = new HashMap<>();
                params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
                        usuario.getPrimerApellido(), usuario.getSegundoApellido()));
                params.put("usuario", usuario.getEmail());
                params.put("Password", tempPass);
                enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());

            } catch (Exception e) {
                logger.error("Error al crear el usuario con numero identificacion " + usuario.getNumIdentificacion(), e);
                // Continuar con el siguiente usuario en caso de error
            }
        }
    }

	/**
     * GLPI 82800 Gestion Consultar Usuario Tercero
     * 
     * <b>Descripción</b>Método que se encarga de obtener usuarios terceros
     *
     * * @return se retorna la lista con los usuarios obtenidos
     *
     */
    @Override
    public List<UsuarioGestionDTO> gestionConsultarTerceros(
        String nombreUsuario, Boolean roles, 
        String primerNombre, String primerApellido,
        String estadoConvenio, String nombreConvenio) {

        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        // Buscar usuarios en Keycloak
        List<UserRepresentation> users = kc.realm(adapter.getRealm()).users()
                .search(null, null, null, null, 0, Integer.MAX_VALUE);

        // Filtrar usuarios según los parámetros proporcionados
        List<UserRepresentation> usuariosFiltrados = users.stream()
                .filter(user -> filtrarUsuarioTercero(user, nombreUsuario,
                        primerNombre, primerApellido, estadoConvenio, nombreConvenio))
                .collect(Collectors.toList());

        // Convertir los usuarios filtrados a UsuarioGestionDTO y devolver la lista
        return usuariosFiltrados.stream()
                .map(KeyCloakMapper::toUsuarioGestion)
                .collect(Collectors.toList());
    }

    // Método de filtrado de usuarios
    private boolean filtrarUsuarioTercero(UserRepresentation user,
            String nombreUsuario,String primerNombre, String primerApellido, 
            String estadoConvenio, String nombreConvenio) {

        Map<String, List<String>> attributes = convertirAtributos(user.getAttributes());
        Boolean estadoUsuario = user.isEnabled();

        // Verificar si la búsqueda incluye alguno de los atributos específicos
        boolean filtroEspecialActivo = estadoConvenio != null || nombreConvenio != null;

        // Si no se han proporcionado filtros, solo devolver los usuarios que tengan atributos válidos
        if (nombreUsuario == null && primerNombre == null && primerApellido == null && !filtroEspecialActivo) {
            return (attributes != null && 
                    (attributes.get("estadoConvenio") != null || attributes.get("nombreConvenio") != null));
        }

        // Filtrar usuarios según criterios
        return (!filtroEspecialActivo || (user.getUsername() != null && !user.getUsername().toLowerCase().startsWith("emp_"))) &&
               (nombreUsuario == null || user.getUsername().equalsIgnoreCase(nombreUsuario)) &&
               (primerNombre == null || 
                    (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(primerNombre.toLowerCase()))) &&
               (primerApellido == null || 
                    (user.getLastName() != null && user.getLastName().toLowerCase().contains(primerApellido.toLowerCase()))) &&
               (estadoConvenio == null || 
                    (attributes != null && attributes.getOrDefault("estadoConvenio", Arrays.asList("")).get(0)
                            .toLowerCase().contains(estadoConvenio.toLowerCase()))) &&
               (nombreConvenio == null || 
                    (attributes != null && attributes.getOrDefault("nombreConvenio", Arrays.asList("")).get(0)
                            .toLowerCase().contains(nombreConvenio.toLowerCase())));
    }


    /**
     * GLPI 82800 Gestion Crear Usuario Tercero
     * 
     *
     *
     */
	@Override
	public void gestionCrearTerceros(UsuarioCCF usuario, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();

		// Consulta para verificar existencia del email
		String emailEncontrado = null;
		String estadoConvenio = null;
		String nombreConvenioBD = null;
		String idConvenidoBD = null;
		String medioPagoBD = null;
		try {
			// Realiza la consulta a la base de datos
			Object[] result = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_TERCERO_POR_NOMBRE_EMAIL)
					.setParameter("email", usuario.getEmail()) 
					.getSingleResult();

			// Si la consulta devuelve resultados, obtiene el email
			idConvenidoBD = String.valueOf(result[0]);
			emailEncontrado = (String) result[4]; 
			nombreConvenioBD = (String) result[1];
			medioPagoBD = (String) result [2];
			estadoConvenio = (String) result[3]; 
		} catch (NoResultException e) {
			// Manejo de cuando no se encuentra un resultado
			logger.info("No se encontraron datos para el usuario: " + usuario.getEmail());
		}

		if (emailEncontrado == null || nombreConvenioBD == null || estadoConvenio == null) {
			logger.error("No se puede crear el usuario Tercero, ya que no existe en base de datos");
			throw new FunctionalConstraintException("No se puede crear el usuario Tercero, ya que no existe la información completa en la base de datos.");
		}

		// Si se encontró un email, usarlo en Keycloak
		if (emailEncontrado != null) {
			usuario.setEmail(emailEncontrado); // Setear email encontrado en el objeto usuario
		} else if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
			// Si no se encontró email, pero el objeto usuario tiene un email, se debe actualizar en la BD
			entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_TERCERO_POR_NOMBRE_EMAIL)
					.setParameter("email", usuario.getEmail())
					.setParameter("nombreConvenio", usuario.getNombreConvenio())
					.executeUpdate();
		}

		if (!validarExistenciaUsuario(usuario.getEmail(), adapter, kc)) {
			UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
			// usuarioCCF.setEmail(usuario.getEmail());
			usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));

			usuarioCCF.setRealmRoles(usuario.getRoles());
			HashMap<String, Object> atributos = new HashMap<>();
			atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
					Arrays.asList(String.valueOf(Boolean.TRUE)));
			atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
					Arrays.asList(String.valueOf(Boolean.TRUE)));
			atributos.put(UserAttributesEnum.DEPENDENCIA.getNombre(), Arrays.asList(usuario.getDependencia()));
			atributos.put(UserAttributesEnum.TELEFONO.getNombre(), Arrays.asList(usuario.getTelefono()));
			atributos.put(UserAttributesEnum.SEDE.getNombre(), Arrays.asList(usuario.getCodigoSede()));
			atributos.put(UserAttributesEnum.CIUDAD_SEDE.getNombre(), Arrays.asList(usuario.getCiudadSede()));
			atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail())); // Asegúrate de usar el email correcto
			atributos.put(UserAttributesEnum.FECHA_FIN_CONTRATO.getNombre(),
					Arrays.asList(usuario.getFechaFinContrato().toString()));


			// Agregar fechaCreacion en formato epoch
			Long fechaCreacionEpoch = Instant.now().getEpochSecond();
			atributos.put("fechaCreacion", Arrays.asList(String.valueOf(fechaCreacionEpoch)));

			atributos.put("usuarioActivo", Arrays.asList("Activo"));
			atributos.put(UserAttributesEnum.TIPO_USUARIO.getNombre(), Arrays.asList("TERCERO"));

			atributos.put("nombreConvenio", Arrays.asList(nombreConvenioBD));
            atributos.put("estadoConvenio", Arrays.asList(estadoConvenio));
			atributos.put("idConvenio", Arrays.asList(idConvenidoBD));
			atributos.put("medioPago", Arrays.asList(medioPagoBD));
			atributos.put("usuarioLogin", Arrays.asList(usuario.getEmail()));

			// Agregar usuarioCreacion (por ejemplo, del objeto `user`)
			String creadoPor = user.getNombreUsuario();  
			atributos.put(UserAttributesEnum.USUARIO_CREADO_POR.getNombre(), Arrays.asList(creadoPor));

			usuarioCCF.setAttributes(atributos);

			// Crea el usuario en Keycloak
			Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
			if (response.getStatus() != Response.Status.CREATED.getStatusCode()
					&& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
				logger.error(response.getStatusInfo());
				throw new TechnicalException(Objects.toString(response.getEntity()));
			}

			// Se asigna por defecto al grupo de funcionarios CCF
			agregarAGrupo(GruposUsuariosEnum.TERCERO_PAGADOR.getNombre(), usuarioCCF.getUsername(), adapter, kc);

			// Verificación de grupos
			if (usuario.getGrupos() != null) {
				for (String grupo : usuario.getGrupos()) {
					agregarAGrupoId(grupo, usuarioCCF.getUsername(), adapter, kc);
				}
			} else {
				logger.info("No hay grupos asignados al usuario.");
			}

			// Verificación de roles
			if (usuario.getRoles() != null) {
				for (String rol : usuario.getRoles()) {
					agregarUsuarioARol(usuarioCCF.getUsername(), rol, adapter, kc);
				}
			} else {
				logger.info("No hay roles asignados al usuario.");
			}

			String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);
			// Enviar correo notificando la creación del usuario
			Map<String, String> params = new HashMap<>();
			params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
					usuario.getPrimerApellido(), usuario.getSegundoApellido()));
			params.put("usuario", usuario.getEmail());
			params.put("Password", tempPass);
			enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());
		} else {
			throw new FunctionalConstraintException(USUARIO_REGISTRADO_MENSAJE);
		}
	}


    /**
     * GLPI 82800 Gestion Actualizar Usuario Tercero
     * 
     *
     *
     */
    @Override
    public ResultadoDTO gestionActualizarTerceros(UsuarioCCF usuario, UserDTO user) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        ResultadoDTO dto = new ResultadoDTO();
        dto.setError(false);

        // Buscar usuario actual en Keycloak usando el ID
        UserRepresentation usuarioCCF = kc.realm(adapter.getRealm()).users().get(usuario.getIdUsuario()).toRepresentation();
        String emailActual = usuarioCCF.getEmail();  // Obtener el email actual
        String nombreUsuarioActual = usuarioCCF.getUsername(); // Obtener el nombre de usuario actual
        String nuevoNombreUsuario = usuario.getNombreUsuario();  // El nuevo nombre de usuario
        String nuevoEmail = usuario.getEmail();  // El nuevo email

        // Verificar si el nombre de usuario ha cambiado
        boolean nombreUsuarioCambio = nuevoNombreUsuario != null && !nuevoNombreUsuario.equals(nombreUsuarioActual);

        if (validarExistenciaUsuario(nombreUsuarioActual, adapter, kc)) {
            // Obtener las tareas asignadas al usuario
            ObtenerTareasAsignadasUsuario obtenerTareas = new ObtenerTareasAsignadasUsuario(nombreUsuarioActual);
            obtenerTareas.execute();
            List<TareaDTO> tareas = obtenerTareas.getResult();

            // Actualizar los atributos del usuario
            Map<String, Object> atributos = usuarioCCF.getAttributes();

            // Actualizar el nombre de usuario y el email si el nombre de usuario ha cambiado
            if (nombreUsuarioCambio) {
                usuarioCCF.setUsername(nuevoNombreUsuario);  // Actualizar nombre de usuario
				String nuevoEmailCambio = nuevoNombreUsuario;
				usuarioCCF.setEmail(nuevoEmailCambio);
				atributos.put("email", Arrays.asList(nuevoEmailCambio));

                if (!nombreUsuarioActual.equals(nuevoNombreUsuario)) {
                    try {
                        kc.realm(adapter.getRealm()).users().get(usuario.getIdUsuario()).update(usuarioCCF);
                        nombreUsuarioActual = nuevoNombreUsuario;
                    } catch (Exception e) {
                        dto.setError(true);
                        dto.setMensaje("Error al actualizar el usuario en Keycloak: " + e.getMessage());
                        return dto;
                    }
                }
            }



            EntityManager em = entityManager;  // Obtener EntityManager

            // Variables para registrar auditoría
            String modificadoPor = user.getNombreUsuario();  
            long fechaModificacionEpoch = Instant.now().getEpochSecond();
            String fechaModificacion = String.valueOf(fechaModificacionEpoch);

            actualizarYCargarDatosTerceros(usuarioCCF, em, atributos, 
                "usuarioLogin", usuario.getNombreUsuario(), modificadoPor, fechaModificacion);

            // Verificar si el usuario tiene tareas asignadas y si está inhabilitado
            if (tareas != null && !tareas.isEmpty() && !usuario.isUsuarioActivo()) {
                dto.setError(true);
                dto.setMensaje(NO_INHABILITAR_USUARIO_REGISTRADO_MENSAJE);
                return dto;
            }

            // Establecer la fecha de modificación y el usuario que realizó la modificación
            atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
            atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));

            // Actualizar el estado del usuario
            //usuarioCCF.setEnabled(usuario.isUsuarioActivo());
            //usuarioCCF.setEmailVerified(usuario.isEmailVerified());
            usuarioCCF.setAttributes(atributos);

            // Verificar si es un usuario de reintegro y asignar password temporal
            if (usuario.getReintegro() != null && usuario.getReintegro().booleanValue()) {
                String tempPass = asignarPasswordTemporal(usuarioCCF.getUsername(), adapter, kc);
                // Enviar correo notificando la creación del usuario
                Map<String, String> params = new HashMap<>();
                params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(), usuario.getPrimerApellido(), usuario.getSegundoApellido()));
                params.put("usuario", usuario.getNombreUsuario());
                params.put("Password", tempPass);
                enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_EXT, usuario.getEmail());
            }

            // Actualizar el usuario en Keycloak
            kc.realm(adapter.getRealm()).users().get(usuario.getIdUsuario()).update(usuarioCCF);

            // Eliminar todos los grupos asociados al usuario
            for (GroupRepresentation grupoConsultado : kc.realm(adapter.getRealm()).users().get(usuario.getIdUsuario()).groups()) {
                quitarAGrupoId(grupoConsultado.getId(), nombreUsuarioActual, adapter, kc);
            }

            // Asignar al grupo por defecto de funcionarios CCF
            agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_CCF.getNombre(), usuarioCCF.getUsername(), adapter, kc);

            // Asignar los nuevos grupos ingresados por pantalla
            if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
                for (String grupo : usuario.getGrupos()) {
                    agregarAGrupoId(grupo, nombreUsuarioActual, adapter, kc);
                }
            }
        } else {
            dto.setError(true);
            dto.setMensaje(USUARIO_NO_REGISTRADO_MENSAJE);
        }
        return dto;
    }

	private void actualizarYCargarDatosTerceros(UserRepresentation usuario, EntityManager em, 
        Map<String, Object> atributos, String campo, String nuevoValor, 
        String modificadoPor, String fechaModificacion) {

        String valorAnterior = ((List<String>) atributos.getOrDefault(campo, Arrays.asList(""))).get(0);

        if (nuevoValor != null && !nuevoValor.equals(valorAnterior)) {
            // Actualizar en Keycloak
            atributos.put(campo, Arrays.asList(nuevoValor));

            // Registrar en la base de datos
            em.createNamedQuery(NamedQueriesConstants.INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_TERCERO)
                .setParameter("usuarioEditado", usuario.getUsername())
                .setParameter("campoModificado", campo)
                .setParameter("valorAnterior", valorAnterior)
                .setParameter("nuevoValor", nuevoValor)
                .setParameter("fechaModificacion", new java.sql.Timestamp(System.currentTimeMillis()))
                .setParameter("modificadoPor", modificadoPor)
                .executeUpdate();
        }
    }

	/**
     * GLPI 82800 Gestion Activar/Inactivar Usuario Tercero
     * 
     *
     *
     */
    @Override
    public ResultadoDTO gestionActivarInactivarTerceros(UsuarioCCF usuario, UserDTO user) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        ResultadoDTO dto = new ResultadoDTO();
        dto.setError(false);

        if (kc == null) {
            dto.setError(true);
            dto.setMensaje("Error: Keycloak no se ha inicializado correctamente.");
            return dto;
        }

        String realm = adapter.getRealm();
        if (realm == null) {
            dto.setError(true);
            dto.setMensaje("Error: El realm es nulo.");
            return dto;
        }

        String nombreUsuarioOriginal = usuario.getNombreUsuario();

        // Validar si el usuario existe en Keycloak
        if (validarExistenciaUsuario(nombreUsuarioOriginal, adapter, kc)) {
            ObtenerTareasAsignadasUsuario obtenerTareas = new ObtenerTareasAsignadasUsuario(usuario.getNombreUsuario());
            obtenerTareas.execute();
            List<TareaDTO> tareas = obtenerTareas.getResult();
            UserRepresentation usuarioCCF = kc.realm(realm).users().get(usuario.getIdUsuario()).toRepresentation();
            if (usuarioCCF == null) {
                dto.setError(true);
                dto.setMensaje("Error: No se pudo obtener la representación del usuario.");
                return dto;
            }

            Map<String, Object> atributos = usuarioCCF.getAttributes();
            if (atributos == null) {
                atributos = new HashMap<>();
            }

            EntityManager em = entityManager; // Obtener EntityManager

            // Variables para auditoría
            String modificadoPor = user.getNombreUsuario();
            long fechaModificacionEpoch = Instant.now().getEpochSecond();
            String fechaModificacion = String.valueOf(fechaModificacionEpoch);

            // Actualizar y cargar el estado "usuarioActivo"
            String estado = usuario.isUsuarioActivo() ? "Activo" : "Inactivo";
            actualizarYCargarDatosTerceros(
                usuarioCCF, 
                em, 
                atributos, 
                "usuarioActivo", 
                estado, 
                modificadoPor, 
                fechaModificacion
            );

            if (tareas != null && !tareas.isEmpty() && !usuario.isUsuarioActivo()) {
                dto.setError(true);
                dto.setMensaje(NO_INHABILITAR_USUARIO_REGISTRADO_MENSAJE);
                return dto;
            }

            atributos.put(UserAttributesEnum.FECHA_MODIFICACION.getNombre(), Arrays.asList(String.valueOf(fechaModificacionEpoch)));
            atributos.put(UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre(), Arrays.asList(modificadoPor));

            // Guardar los cambios en Keycloak
            usuarioCCF.setEnabled(usuario.isUsuarioActivo());
            usuarioCCF.setAttributes(atributos);
            kc.realm(realm).users().get(usuario.getIdUsuario()).update(usuarioCCF);

        } else {
            dto.setError(true);
            dto.setMensaje("Error: El usuario no está registrado en el sistema.");
        }

        return dto;
    }

    /**
     * GLPI 82800 Gestion Restablecer Contraseña Usuario Tercero
     * 
     *
     *
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void gestionRestablecerContrasenaTerceros(CambiarContrasenaDTO dto) {
		logger.info("Iniciando proceso de reasignación de contraseña para el usuario: " + dto.getNombreUsuario());

		// Validar que la nueva contraseña y la confirmación coinciden
		if (!dto.getPassNuevo().equals(dto.getPassConfirmacion())) {
			logger.warn("La nueva contraseña y la confirmación no coinciden para el usuario: " + dto.getNombreUsuario());
			throw new IllegalArgumentException("La nueva contraseña y la confirmación no coinciden.");
		}

		// Obtener el adaptador de Keycloak
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();

		// Validar si el usuario existe en Keycloak
		logger.info("Validando existencia de usuario en Keycloak");
		if (validarExistenciaUsuario(dto.getNombreUsuario(), adapter, kc)) {

			// Asignar la nueva contraseña proporcionada por el usuario
			logger.info("Asignando nueva contraseña proporcionada por el usuario");
			boolean passwordCambiada = cambiarPasswordUsuario(dto.getNombreUsuario(), dto.getPassNuevo(), adapter, kc);

			if (passwordCambiada) {
				logger.info("Contraseña cambiada exitosamente para el usuario: " + dto.getNombreUsuario());

				// Opcional: Enviar notificación al usuario sobre el cambio de contraseña
				Map<String, String> params = new HashMap<>();
				params.put("nombreUsuario", dto.getNombreUsuario());
				// Por seguridad, puedes omitir el envío de la contraseña en la notificación
				params.put("contrasenia", dto.getPassNuevo());  // Contraseña que el usuario escogió

				// String emailUsuario = obtenerCorreoUsuario(dto.getNombreUsuario());
				// logger.info("Enviando notificación a: " + emailUsuario);
				// enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_RES_CTRS, emailUsuario);
			} else {
				logger.info("No se pudo cambiar la contraseña para el usuario: " + dto.getNombreUsuario());
			}
		} else {
			logger.info("Usuario no encontrado: " + dto.getNombreUsuario());
		}
	}

	/**
     * GLPI 82800 Gestion Crear Usuarios Masivos Tercero
     * 
     *
     *
     */
	@Override
	public String gestionMasivosTerceros(List<UsuarioCCF> usuarios, UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();

		StringBuilder resultado = new StringBuilder();
		
		for (UsuarioCCF usuario : usuarios) {
			try {
				if (!validarExistenciaUsuario(usuario.getEmail(), adapter, kc)) {
					UserRepresentation usuarioCCF = KeyCloakMapper.toUserRepresentation(usuario);
					usuarioCCF.setRequiredActions(Arrays.asList(UPDATE_PASSWORD));
					usuarioCCF.setRealmRoles(usuario.getRoles());

					// Configuración de atributos
					HashMap<String, Object> atributos = new HashMap<>();
					atributos.put(UserAttributesEnum.DEBE_CREAR_PREGUNTAS.getNombre(),
							Arrays.asList(String.valueOf(Boolean.TRUE)));
					atributos.put(UserAttributesEnum.DEBE_ACEPTAR_TERMINOS.getNombre(),
							Arrays.asList(String.valueOf(Boolean.TRUE)));
					atributos.put(UserAttributesEnum.DEPENDENCIA.getNombre(), Arrays.asList(usuario.getDependencia()));
					atributos.put(UserAttributesEnum.TELEFONO.getNombre(), Arrays.asList(usuario.getTelefono()));
					atributos.put(UserAttributesEnum.SEDE.getNombre(), Arrays.asList(usuario.getCodigoSede()));
					atributos.put(UserAttributesEnum.CIUDAD_SEDE.getNombre(), Arrays.asList(usuario.getCiudadSede()));
					atributos.put(UserAttributesEnum.EMAIL.getNombre(), Arrays.asList(usuario.getEmail()));
					atributos.put(UserAttributesEnum.FECHA_FIN_CONTRATO.getNombre(),
							Arrays.asList(usuario.getFechaFinContrato().toString()));
					
					if (usuario.getTipoIdentificacion() != null && !usuario.getTipoIdentificacion().equals("")) {
						atributos.put(UserAttributesEnum.TIPO_IDENTIFICACION.getNombre(),
								Arrays.asList(usuario.getTipoIdentificacion()));
					}
					if (usuario.getNumIdentificacion() != null && !usuario.getNumIdentificacion().equals("")) {
						atributos.put(UserAttributesEnum.NUM_IDENTIFICACION.getNombre(),
								Arrays.asList(usuario.getNumIdentificacion()));
					}
					if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isEmpty()) {
						atributos.put(UserAttributesEnum.SEGUNDO_NOMBRE.getNombre(),
								Arrays.asList(usuario.getSegundoNombre()));
					}
					if (usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().isEmpty()) {
						atributos.put(UserAttributesEnum.SEGUNDO_APELLIDO.getNombre(),
								Arrays.asList(usuario.getSegundoApellido()));
					}

					usuarioCCF.setAttributes(atributos);

					// Creación del usuario en Keycloak
					Response response = kc.realm(adapter.getRealm()).users().create(usuarioCCF);
					if (response.getStatus() != Response.Status.CREATED.getStatusCode()
							&& response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
						logger.error("Error al crear el usuario: " + response.getStatusInfo());
						throw new TechnicalException(Objects.toString(response.getEntity()));
					}

					// Asignar grupo y roles
					agregarAGrupo(GruposUsuariosEnum.FUNCIONARIO_CCF.getNombre(), usuarioCCF.getUsername(), adapter, kc);
					// for (String grupo : usuario.getGrupos()) {
					// 	agregarAGrupoId(grupo, usuarioCCF.getUsername(), adapter, kc);
					// }
					// if (usuario.getRoles() != null) {
					// 	for (String rol : usuario.getRoles()) {
					// 		agregarUsuarioARol(usuarioCCF.getUsername(), rol, adapter, kc);
					// 	}
					// }

					// Asignar contraseña temporal
					String tempPass = asignarPasswordTemporalGestionUsuarios(usuarioCCF.getUsername(), adapter, kc);

					// Enviar notificación
					Map<String, String> params = new HashMap<>();
					params.put("nombreUsuario", obtenerNombreUsuario(usuario.getPrimerNombre(), usuario.getSegundoNombre(),
							usuario.getPrimerApellido(), usuario.getSegundoApellido()));
					params.put("usuario", usuario.getEmail());
					params.put("Password", tempPass);
					enviarNotificacion(params, EtiquetaPlantillaComunicadoEnum.NTF_CRCN_USR_CCF_EXT, usuario.getEmail());

					resultado.append("Usuario " + usuario.getEmail() + " creado exitosamente.\n");
				} else {
					resultado.append("El usuario con email " + usuario.getEmail() + " ya está registrado.\n");
				}
			} catch (FunctionalConstraintException e) {
				logger.error("El usuario con email " + usuario.getEmail() + " ya está registrado: " + e.getMessage());
			} catch (TechnicalException e) {
				logger.error("Error técnico al crear el usuario con email " + usuario.getEmail() + ": " + e.getMessage());
			} catch (Exception e) {
				logger.error("Error inesperado al procesar el usuario con email " + usuario.getEmail() + ": " + e.getMessage());
			}
		}

		return resultado.toString();
	}

    /**
     * GLPI 82800 Metodos para la inactivacion de usuario/cierre de sesion 
     * 
     *
     *
     */
    @Override
    public void inactivarUsuarioKeycloak(String idUsuario) {
		logger.info("**__** Se inicia el método usuarioBusiness.inactivarUsuarioKeycloak");
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        // Obtener el valor del parámetro de inactividad desde el sistema
        String tiempoInactivarStr = (String) CacheManager.getParametro(
                ParametrosSistemaConstants.TIEMPO_INACTIVAR_USUARIO_KEYCLOAK);
        int diasInactivar = Integer.parseInt(tiempoInactivarStr.replace("d", ""));
        logger.info("Días para inactivación: " + diasInactivar);

        // Verificar si se debe inactivar un usuario específico o todos los usuarios inactivos
        if (idUsuario != null && !idUsuario.isEmpty()) {
            // Inactivar un usuario específico
            inactivarUsuarioEspecifico(idUsuario, adapter, kc, diasInactivar);
        } else {
            // Inactivar todos los usuarios inactivos
            inactivarUsuariosInactivos(adapter, kc, diasInactivar);
        }
    }

    // Método para inactivar un usuario específico
    private void inactivarUsuarioEspecifico(String idUsuario, KeycloakAdapter adapter, Keycloak kc, int diasInactivar) {
        UserRepresentation userRepresentation = buscarUsuarioPorIdUsuario(idUsuario, adapter, kc);

        if (userRepresentation == null) {
            logger.info("El usuario no existe.");
            return;
        }

        Date fechaUltimaActividad = verificarTiempoInactividad(idUsuario, adapter);
        if (fechaUltimaActividad == null) {
            logger.info("No se encontró una fecha de última actividad para el usuario: " + idUsuario);
            return;
        }

        long diasInactividad = calcularDiasEntreFechas(fechaUltimaActividad, new Date());
        logger.info("Días de inactividad para el usuario " + idUsuario + ": " + diasInactividad);

        if (diasInactividad >= diasInactivar) {
            Map<String, Object> atributos = userRepresentation.getAttributes();
            if (atributos == null) {
                atributos = new HashMap<>();
            }

			atributos.put("usuarioActivo", Arrays.asList("Inactivo"));

            userRepresentation.setEnabled(false);
			userRepresentation.setAttributes(atributos);
            kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
            logger.info("Usuario inactivado: " + idUsuario);
        } else {
            logger.info("El usuario " + idUsuario + " aún no cumple los días de inactividad para ser inactivado.");
        }
    }

	// Método auxiliar para calcular la diferencia en días entre dos fechas.
    private long calcularDiasEntreFechas(Date fechaInicio, Date fechaFin) {
        long diferenciaMillis = fechaFin.getTime() - fechaInicio.getTime();
        return diferenciaMillis / (24 * 60 * 60 * 1000); 
    }

    // Método para inactivar usuarios automáticamente
    private void inactivarUsuariosInactivos(KeycloakAdapter adapter, Keycloak kc, int diasInactivar) {
        EntityManager em = entityManager;
        Query query = em.createNamedQuery(NamedQueriesConstants.CONTAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK);
		Query consultaFestivos = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_DIAS_FESTIVOS);

        // Realizar la consulta para obtener todos los usuarios con su última actividad
        List<Object[]> resultados = query.getResultList();

		// Obtener la lista de días festivos
        List<Date> diasFestivos = consultaFestivos.getResultList();

        for (Object[] resultado : resultados) {
            String userId = (String) resultado[0];
            Date fechaUltimaActividad = (Date) resultado[1];

            // Calcular los días de inactividad
            long diasInactividad = calcularDiasHabiles(fechaUltimaActividad, new Date(), diasFestivos);
            logger.info("Usuario: " + userId + " - Días de inactividad: " + diasInactividad);

            // Buscar el usuario en Keycloak y verificar si debe inactivarse
            if (diasInactividad >= diasInactivar) {
                UserRepresentation userRepresentation = buscarUsuarioPorIdUsuario(userId, adapter, kc);
                if (userRepresentation != null && userRepresentation.isEnabled()) {
                    Map<String, Object> atributos = userRepresentation.getAttributes();
                    if (atributos == null) {
                        atributos = new HashMap<>();
                    }
					atributos.put("usuarioActivo", Arrays.asList("Inactivo"));
                    userRepresentation.setEnabled(false);
					userRepresentation.setAttributes(atributos);
                    kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
                    logger.info("Usuario inactivado: " + userId);
                }
            } else {
                logger.info("Usuario: " + userId + " aún no cumple los días de inactividad para ser inactivado.");
            }
        }
    }

    // Método auxiliar para calcular la diferencia en días habiles y festivos
    private long calcularDiasHabiles(Date fechaInicio, Date fechaFin, List<Date> diasFestivos) {
        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(fechaInicio);
        Calendar calFin = Calendar.getInstance();
        calFin.setTime(fechaFin);
        long diasHabiles = 0;
        // Avanzar al día siguiente de fechaInicio para contar desde el día posterior a la última actividad
        calInicio.add(Calendar.DAY_OF_MONTH, 1);
        // Iterar entre las fechas hasta incluir fechaFin
        while (calInicio.before(calFin) || calInicio.equals(calFin)) {
            int diaSemana = calInicio.get(Calendar.DAY_OF_WEEK);
            // Verificar que no sea fin de semana (sábado o domingo) ni un día festivo
            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY) {
                boolean esFestivo = false;
                // Verificar si el día actual es un festivo
                for (Date festivo : diasFestivos) {
                    Calendar calFestivo = Calendar.getInstance();
                    calFestivo.setTime(festivo);

                    if (calInicio.get(Calendar.YEAR) == calFestivo.get(Calendar.YEAR) &&
                        calInicio.get(Calendar.MONTH) == calFestivo.get(Calendar.MONTH) &&
                        calInicio.get(Calendar.DAY_OF_MONTH) == calFestivo.get(Calendar.DAY_OF_MONTH)) {
                        esFestivo = true;
                        break;
                    }
                }
                // Si no es festivo, contar como día hábil
                if (!esFestivo) {
                    diasHabiles++;
                }
            }
            // Avanzar al siguiente día
            calInicio.add(Calendar.DAY_OF_MONTH, 1);
        }
        return diasHabiles;
    }

	private UserRepresentation buscarUsuarioPorIdUsuario(String idUsuario, KeycloakAdapter adapter, Keycloak kc) {
		try {
			return kc.realm(adapter.getRealm()).users().get(idUsuario).toRepresentation();
		} catch (NotFoundException e) {
			logger.info("Usuario no encontrado por ID: " + idUsuario);
			return null;
		} catch (Exception e) {
			logger.error("Error al buscar el usuario por ID: " + idUsuario, e);
			return null;
		}
	}

	public void registrarActividadUsuario(String userId, String direccionIp) { 
		EntityManager em = entityManager; // Obtener el EntityManager
		try {
			Timestamp ultimaFechaSesion = obtenerUltimaFechaSesionDesdeKeycloak(userId);
			// Intentar actualizar el registro de actividad para el usuario
			Query updateQuery = em.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK);
			updateQuery.setParameter("fechaHora", ultimaFechaSesion);
			updateQuery.setParameter("direccionIp", direccionIp);
			updateQuery.setParameter("idUsuario", userId);
			
			// Obtener el número de filas actualizadas
			int rowsUpdated = updateQuery.executeUpdate();

			// Si no se actualizó ninguna fila, significa que no había registro, así que insertamos uno nuevo
			if (rowsUpdated == 0) {
				Query insertQuery = em.createNamedQuery(NamedQueriesConstants.INSERTAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK);
				insertQuery.setParameter("idUsuario", userId);
				insertQuery.setParameter("fechaHora", ultimaFechaSesion);
				insertQuery.setParameter("direccionIp", direccionIp);
				insertQuery.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("Error al registrar actividad del usuario: " + userId, e);
		}
	}

	private Date verificarTiempoInactividad(String userId, KeycloakAdapter adapter) {
		// Utiliza el EntityManager para realizar la consulta
		EntityManager em = entityManager;

		// Log para verificar el usuario
		logger.info("Consultando actividad de sesión para el usuario: " + userId);

		// Obtener el tiempo de inactividad
		Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK);
		query.setParameter("idUsuario", userId.trim());  // Eliminar espacios en blanco
		
		try {
			Object result = query.getSingleResult();
			return result != null ? (Date) result : null;
		} catch (NoResultException e) {
			logger.warn("No se encontró actividad para el usuario: " + userId);
			return null;
		} catch (Exception e) {
			logger.error("Error al consultar la actividad de sesión: " + e.getMessage(), e);
			return null;
		}
	}

	private Timestamp obtenerUltimaFechaSesionDesdeKeycloak(String userId) {
		try {
			// 1. Obtener la instancia de Keycloak utilizando tu KeycloakAdapter
			KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
					.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
			Keycloak keycloak = adapter.getKc();

			// 2. Consultar las sesiones activas del usuario en el realm configurado
			List<UserSessionRepresentation> sesiones = keycloak.realm(adapter.getRealm())
					.users().get(userId).getUserSessions();

			// 3. Obtener la última sesión si existe
			if (!sesiones.isEmpty()) {
				UserSessionRepresentation sesion = sesiones.get(0); // Primera sesión encontrada
				long timestampInicioSesion = sesion.getLastAccess();
				return new Timestamp(timestampInicioSesion);
			}
		} catch (Exception e) {
			logger.error("Error al obtener la fecha de sesión de Keycloak para el usuario: " + userId, e);
		}
		// En caso de error, devolver la fecha del sistema como fallback
		return new Timestamp(System.currentTimeMillis());
	}

    public List<UsuarioDTO> usuariosEnSesionParaInactivar(String clientId) {
        List<UsuarioDTO> nombresUsuarios = new ArrayList<>();
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        List<Map<String, String>> clients = kc.realm(adapter.getRealm()).getClientSessionStats();
        String frontendClientId = null;

        // Obtenemos el ID del cliente "frontend" como fallback si no se pasa clientId.
        for (Map<String, String> map : clients) {
            if ("frontend".equals(map.get("clientId"))) {
                frontendClientId = map.get("id");
                break;
            }
        }

        if (frontendClientId != null) {
            // Obtenemos las sesiones de usuario para el cliente "frontend".
            List<UserSessionRepresentation> sessions = kc.realm(adapter.getRealm())
                    .clients()
                    .get(frontendClientId)
                    .getUserSessions(0, 100);

            for (UserSessionRepresentation session : sessions) {
                // Filtrar por clientId (userId) si se proporciona.
                if (clientId == null || clientId.equals(session.getUserId())) {
                    UsuarioDTO usuario = new UsuarioDTO();
                    usuario.setNombreUsuario(session.getUsername());
                    usuario.setIdUsuario(session.getUserId());
                    nombresUsuarios.add(usuario);

                    // Registrar actividad del usuario.
                    //String direccionIp = request.getRemoteAddr();
					String direccionIp = obtenerDireccionIpServidor();
                    registrarActividadUsuario(session.getUserId(), direccionIp);
                }
            }
        }

        return nombresUsuarios;
    }
	
	// Método para obtener la IP del servidor
	private String obtenerDireccionIpServidor() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			return ip.getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("No se pudo obtener la dirección IP del servidor", e);
			return "IP desconocida";
		}
	}
    public List<UsuarioDTO> usuariosEnSesionParaCerrarSesion(String clientId) {
        List<UsuarioDTO> nombresUsuarios = new ArrayList<>();
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
                .getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        List<Map<String, String>> clients = kc.realm(adapter.getRealm()).getClientSessionStats();
        MonitoreoSesionUsuario monitoreo = new MonitoreoSesionUsuario(kc);
        String frontendClientId = null;

        // Obtenemos el ID del cliente "frontend" como fallback si no se pasa clientId.
        for (Map<String, String> map : clients) {
            if ("frontend".equals(map.get("clientId"))) {
                frontendClientId = map.get("id");
                break;
            }
        }

        if (frontendClientId != null) {
            // Obtenemos las sesiones de usuario para el cliente "frontend".
            List<UserSessionRepresentation> sessions = kc.realm(adapter.getRealm())
                    .clients()
                    .get(frontendClientId)
                    .getUserSessions(0, 100);

            for (UserSessionRepresentation session : sessions) {
                // Filtrar por clientId (userId) si se proporciona.
                if (clientId == null || clientId.equals(session.getUserId())) {
                    UsuarioDTO usuario = new UsuarioDTO();
                    usuario.setNombreUsuario(session.getUsername());
                    usuario.setIdUsuario(session.getUserId());
                    nombresUsuarios.add(usuario);

                    // Registrar actividad del usuario.
                    monitoreo.iniciarMonitoreoSesion(session.getUserId(), adapter.getRealm());
                }
            }
        }

        return nombresUsuarios;
    }

	// Método para exportar a excel la consulta de usuarios empleador
    @Override
    public Response exportarInformacionConsultaGestionUsuariosEmpleador(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO,
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException {

        // Lógica para obtener los datos de la consulta realizada
        List<UsuarioGestionDTO> usuarios = datosConsultaGestionUsuariosDTO.getUsuarios();

        // Verificar si la lista de usuarios no está vacía
        if (usuarios == null || usuarios.isEmpty()) {
            // Si no hay usuarios, devolver un error o una respuesta vacía
            return Response.status(Response.Status.NO_CONTENT)
                           .entity("No hay usuarios para exportar.")
                           .build();
        }

        // Generar el archivo Excel
        Workbook workbook = new XSSFWorkbook(); // Para Excel 2007 o superior
        Sheet sheet = workbook.createSheet("Usuarios");

        // Crear encabezados según los datos de la consulta
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Tipo Identificación", "Número Identificación", "Usuario Log In", "Razón Social", "Fecha Creación", 
                             "Usuario Creación", "Fecha Modificación", "Usuario Modificación", "Estado Usuario" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Crear un formateador para las fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Llenar los datos en el archivo Excel
        int rowNum = 1;
        for (UsuarioGestionDTO usuario : usuarios) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(usuario.getTipoIdentificacion() != null ? usuario.getTipoIdentificacion() : "");
            row.createCell(1).setCellValue(usuario.getNumIdentificacion() != null ? usuario.getNumIdentificacion() : "");
            row.createCell(2).setCellValue(usuario.getNombreUsuario() != null ? usuario.getNombreUsuario() : "");
            row.createCell(3).setCellValue(usuario.getRazonSocial() != null ? usuario.getRazonSocial() : "");
			if (usuario.getFechaCreacion() != null) {
				long fechaCreacionEpoch = usuario.getFechaCreacion() * 1000;
				row.createCell(4).setCellValue(dateFormat.format(new Date(fechaCreacionEpoch)));
			} else {
				row.createCell(4).setCellValue("");
			}
            row.createCell(5).setCellValue(usuario.getCreadoPor() != null ? usuario.getCreadoPor() : "");
			if (usuario.getFechaModificacion() != null) {
				long fechaModificacionEpoch = usuario.getFechaModificacion() * 1000; 
				row.createCell(6).setCellValue(dateFormat.format(new Date(fechaModificacionEpoch)));
			} else {
				row.createCell(6).setCellValue("");
			}
            row.createCell(7).setCellValue(usuario.getModificadoPor() != null ? usuario.getModificadoPor() : "");
            row.createCell(8).setCellValue(usuario.isUsuarioActivo() ? "Activo" : "Inactivo");
        }

        // Configurar la respuesta para devolver el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios_exportados.xlsx");

        // Escribir el archivo al response OutputStream
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al generar el archivo Excel").build();
        }

        return Response.ok().build();
    }

    // Método para exportar a excel la consulta de usuarios persona
    @Override
    public Response exportarInformacionConsultaGestionUsuariosPersona(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO,
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException {

        // Lógica para obtener los datos de la consulta realizada
        List<UsuarioGestionDTO> usuarios = datosConsultaGestionUsuariosDTO.getUsuarios();

        // Verificar si la lista de usuarios no está vacía
        if (usuarios == null || usuarios.isEmpty()) {
            // Si no hay usuarios, devolver un error o una respuesta vacía
            return Response.status(Response.Status.NO_CONTENT)
                           .entity("No hay usuarios para exportar.")
                           .build();
        }

        // Generar el archivo Excel
        Workbook workbook = new XSSFWorkbook(); // Para Excel 2007 o superior
        Sheet sheet = workbook.createSheet("Usuarios");

        // Crear encabezados según los datos de la consulta
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Tipo Identificación", "Número Identificación", "Usuario Log In", "Primer Nombre", "Segundo Nombre", 
            "Segundo Nombre", "Segundo Apellido", "Fecha Creación", "Usuario Creación", "Fecha Modificación", "Usuario Modificación", "Estado Usuario" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Crear un formateador para las fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Llenar los datos en el archivo Excel
        int rowNum = 1;
        for (UsuarioGestionDTO usuario : usuarios) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(usuario.getTipoIdentificacion() != null ? usuario.getTipoIdentificacion() : "");
            row.createCell(1).setCellValue(usuario.getNumIdentificacion() != null ? usuario.getNumIdentificacion() : "");
            row.createCell(2).setCellValue(usuario.getNombreUsuario() != null ? usuario.getNombreUsuario() : "");
            row.createCell(3).setCellValue(usuario.getPrimerNombre() != null ? usuario.getPrimerNombre() : "");
            row.createCell(4).setCellValue(usuario.getPrimerApellido() != null ? usuario.getPrimerApellido() : "");
            row.createCell(5).setCellValue(usuario.getSegundoNombre() != null ? usuario.getSegundoNombre() : "");
            row.createCell(6).setCellValue(usuario.getSegundoApellido() != null ? usuario.getSegundoApellido() : "");
            if (usuario.getFechaCreacion() != null) {
                long fechaCreacionEpoch = usuario.getFechaCreacion() * 1000;
                row.createCell(7).setCellValue(dateFormat.format(new Date(fechaCreacionEpoch)));
            } else {
                row.createCell(7).setCellValue("");
            }
            row.createCell(8).setCellValue(usuario.getCreadoPor() != null ? usuario.getCreadoPor() : "");
            if (usuario.getFechaModificacion() != null) {
                long fechaModificacionEpoch = usuario.getFechaModificacion() * 1000; 
                row.createCell(9).setCellValue(dateFormat.format(new Date(fechaModificacionEpoch)));
            } else {
                row.createCell(9).setCellValue("");
            }
            row.createCell(10).setCellValue(usuario.getModificadoPor() != null ? usuario.getModificadoPor() : "");
            row.createCell(11).setCellValue(usuario.isUsuarioActivo() ? "Activo" : "Inactivo");
        }

        // Configurar la respuesta para devolver el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios_exportados.xlsx");

        // Escribir el archivo al response OutputStream
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al generar el archivo Excel").build();
        }

        return Response.ok().build();
    }

    // Método para exportar a excel la consulta de usuarios ccf
    @Override
    public Response exportarInformacionConsultaGestionUsuariosCcf(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO,
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException {

        // Lógica para obtener los datos de la consulta realizada
        List<UsuarioGestionDTO> usuarios = datosConsultaGestionUsuariosDTO.getUsuarios();

        // Verificar si la lista de usuarios no está vacía
        if (usuarios == null || usuarios.isEmpty()) {
            // Si no hay usuarios, devolver un error o una respuesta vacía
            return Response.status(Response.Status.NO_CONTENT)
                           .entity("No hay usuarios para exportar.")
                           .build();
        }

        // Generar el archivo Excel
        Workbook workbook = new XSSFWorkbook(); // Para Excel 2007 o superior
        Sheet sheet = workbook.createSheet("Usuarios");

        // Crear encabezados según los datos de la consulta
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Tipo Identificación", "Número Identificación", "Usuario Log In", "Primer Nombre", "Segundo Nombre", 
            "Segundo Nombre", "Segundo Apellido", "Fecha Creación", "Usuario Creación", "Fecha Modificación", "Usuario Modificación", "Estado Usuario" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Crear un formateador para las fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Llenar los datos en el archivo Excel
        int rowNum = 1;
        for (UsuarioGestionDTO usuario : usuarios) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(usuario.getTipoIdentificacion() != null ? usuario.getTipoIdentificacion() : "");
            row.createCell(1).setCellValue(usuario.getNumIdentificacion() != null ? usuario.getNumIdentificacion() : "");
            row.createCell(2).setCellValue(usuario.getNombreUsuario() != null ? usuario.getNombreUsuario() : "");
            row.createCell(3).setCellValue(usuario.getPrimerNombre() != null ? usuario.getPrimerNombre() : "");
            row.createCell(4).setCellValue(usuario.getPrimerApellido() != null ? usuario.getPrimerApellido() : "");
            row.createCell(5).setCellValue(usuario.getSegundoNombre() != null ? usuario.getSegundoNombre() : "");
            row.createCell(6).setCellValue(usuario.getSegundoApellido() != null ? usuario.getSegundoApellido() : "");
            if (usuario.getFechaCreacion() != null) {
                long fechaCreacionEpoch = usuario.getFechaCreacion() * 1000;
                row.createCell(7).setCellValue(dateFormat.format(new Date(fechaCreacionEpoch)));
            } else {
                row.createCell(7).setCellValue("");
            }
            row.createCell(8).setCellValue(usuario.getCreadoPor() != null ? usuario.getCreadoPor() : "");
            if (usuario.getFechaModificacion() != null) {
                long fechaModificacionEpoch = usuario.getFechaModificacion() * 1000; 
                row.createCell(9).setCellValue(dateFormat.format(new Date(fechaModificacionEpoch)));
            } else {
                row.createCell(9).setCellValue("");
            }
            row.createCell(10).setCellValue(usuario.getModificadoPor() != null ? usuario.getModificadoPor() : "");
            row.createCell(11).setCellValue(usuario.isUsuarioActivo() ? "Activo" : "Inactivo");
        }

        // Configurar la respuesta para devolver el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios_exportados.xlsx");

        // Escribir el archivo al response OutputStream
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al generar el archivo Excel").build();
        }

        return Response.ok().build();
    }

    // Método para exportar a excel la consulta de usuarios terceros
    @Override
    public Response exportarInformacionConsultaGestionUsuariosTerceros(
        DatosConsultaGestionUsuariosDTO datosConsultaGestionUsuariosDTO,
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException {

        // Lógica para obtener los datos de la consulta realizada
        List<UsuarioGestionDTO> usuarios = datosConsultaGestionUsuariosDTO.getUsuarios();

        // Verificar si la lista de usuarios no está vacía
        if (usuarios == null || usuarios.isEmpty()) {
            // Si no hay usuarios, devolver un error o una respuesta vacía
            return Response.status(Response.Status.NO_CONTENT)
                           .entity("No hay usuarios para exportar.")
                           .build();
        }

        // Generar el archivo Excel
        Workbook workbook = new XSSFWorkbook(); // Para Excel 2007 o superior
        Sheet sheet = workbook.createSheet("Usuarios");

        // Crear encabezados según los datos de la consulta
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Código Convenio", "Nombre Convenio", "Medio de Pago", "Fecha Creación", "Usuario Creación", 
            "Fecha Modificación", "Usuario Modificación", "Usuario Log In", "Estado Convenio" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Crear un formateador para las fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Llenar los datos en el archivo Excel
        int rowNum = 1;
        for (UsuarioGestionDTO usuario : usuarios) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(usuario.getIdConvenio() != null ? usuario.getIdConvenio().toString() : "");
            row.createCell(1).setCellValue(usuario.getNombreConvenio() != null ? usuario.getNombreConvenio() : "");
            row.createCell(2).setCellValue(usuario.getMedioPago() != null ? usuario.getMedioPago() : "");
            if (usuario.getFechaCreacion() != null) {
                long fechaCreacionEpoch = usuario.getFechaCreacion() * 1000;
                row.createCell(3).setCellValue(dateFormat.format(new Date(fechaCreacionEpoch)));
            } else {
                row.createCell(3).setCellValue("");
            }
            row.createCell(4).setCellValue(usuario.getCreadoPor() != null ? usuario.getCreadoPor() : "");
            if (usuario.getFechaModificacion() != null) {
                long fechaModificacionEpoch = usuario.getFechaModificacion() * 1000; 
                row.createCell(5).setCellValue(dateFormat.format(new Date(fechaModificacionEpoch)));
            } else {
                row.createCell(5).setCellValue("");
            }
            row.createCell(6).setCellValue(usuario.getModificadoPor() != null ? usuario.getModificadoPor() : "");
            row.createCell(7).setCellValue(usuario.getNombreUsuario() != null ? usuario.getNombreUsuario() : "");
            row.createCell(8).setCellValue(usuario.getEstadoConvenio() != null ? usuario.getEstadoConvenio() : "");
        }

        // Configurar la respuesta para devolver el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios_exportados.xlsx");

        // Escribir el archivo al response OutputStream
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al generar el archivo Excel").build();
        }

        return Response.ok().build();
    }
	//Fin metodos 82800

	//Reset Contraseñas usuarios masivos
	@Override
	public Map<String, String>  actualizarContrasenasUsuariosMasivos(String usuarios,  UserDTO user) {
		KeycloakAdapter adapter = KeycloakClientFactory.getInstance()
				.getKeycloakClient(KeycloakClientFactory.INTEGRACION);
		Keycloak kc = adapter.getKc();
		Map<String, String> respuestas = new HashMap<>();
		String[] arregloMensaje;
		String nombreUsuario = "";
		
		String smtpPassword = (String) CacheManager
            .getParametro(ParametrosSistemaConstants.PASSWORD_ACTUALIZAR_USUARIOS_MASIVOS);

		/*
		String smtpPasswordEncrypted = (String) CacheManager
            .getParametro(ParametrosSistemaConstants.PASSWORD_ACTUALIZAR_USUARIOS_MASIVOS);
        String smtpPassword = DesEncrypter.getInstance().decrypt(smtpPasswordEncrypted);
		*/
			
		logger.info("SMTP Password desencriptada: " + smtpPassword);

		if (usuarios.contains(";")) {
			arregloMensaje = usuarios.split(";");
		}else{
			arregloMensaje = new String[]{usuarios};
		}

		for (String usuario : arregloMensaje) {
			nombreUsuario = usuario.trim();
			UserRepresentation userRep = buscarUsuarioPorUsername(nombreUsuario, adapter, kc);

			if (userRep != null) {
				boolean actualizado = false;
				int intentos = 0;

				try {
					// Intentar con la clave original primero
					cambiarContrasenaKeycloak(kc, adapter, userRep, smtpPassword);
					actualizado = true;
					respuestas.put(nombreUsuario, "Clave asignada correctamente");
					logger.info("Clave definitiva asignada exitosamente para usuario: " + nombreUsuario);
				} catch (Exception e) {
					logger.error("Error al intentar asignar clave definitiva en el primer intento para usuario: " + nombreUsuario);

					// Intentar con claves genéricas
					while (!actualizado && intentos < 3) {
						try {
							String claveGenerica = generarClaveGenerica();
							cambiarContrasenaKeycloak(kc, adapter, userRep, claveGenerica);
							logger.info("Clave genérica asignada exitosamente en intento " + (intentos + 1) + " para usuario: " + nombreUsuario);
							intentos++;
						} catch (Exception e1) {
							logger.error("Error al intentar asignar clave genérica en intento " + (intentos + 1) + " para usuario: " + nombreUsuario);
							intentos++;
						}
					}

					// Si la clave genérica fue exitosa, se intenta nuevamente con la clave original
					if (intentos <= 3) {
						try {
							cambiarContrasenaKeycloak(kc, adapter, userRep, smtpPassword);
							actualizado = true;
							respuestas.put(nombreUsuario, "Clave asignada correctamente");
							logger.info("Clave definitiva asignada exitosamente tras usar clave genérica para usuario: " + nombreUsuario);
						} catch (Exception e2) {
							logger.error("Error al intentar asignar clave definitiva después de usar clave genérica para usuario: " + nombreUsuario);
							respuestas.put(nombreUsuario, "Error al asignar clave");
						}
					}
				}

				if (!actualizado) {
					respuestas.put(nombreUsuario, "No se pudo actualizar la contraseña.");
					logger.error("No se pudo actualizar la contraseña del usuario tras varios intentos: " + nombreUsuario);
				}
			} else {
				respuestas.put(nombreUsuario, "Usuario no existe");
				logger.error("El usuario no existe en el sistema: " + nombreUsuario);
			}
		}
		return respuestas;
	}

	private void cambiarContrasenaKeycloak(Keycloak kc, KeycloakAdapter adapter, UserRepresentation userRep, String contrasena) throws Exception {
		CredentialRepresentation credenciales = new CredentialRepresentation();
		credenciales.setType(CredentialRepresentation.PASSWORD);
		credenciales.setValue(contrasena);
		credenciales.setTemporary(true);
		kc.realm(adapter.getRealm()).users().get(userRep.getId()).resetPassword(credenciales);
	}

	private String generarClaveGenerica() {
		int longitudMinima = 12;
		String caracteresEspeciales = "!@#$%^&*";
		String numeros = "0123456789";
		String letrasMayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letrasMinusculas = "abcdefghijklmnopqrstuvwxyz";
		SecureRandom random = new SecureRandom();
		StringBuilder clave = new StringBuilder(longitudMinima);

		clave.append(caracteresEspeciales.charAt(random.nextInt(caracteresEspeciales.length())));
		clave.append(numeros.charAt(random.nextInt(numeros.length())));
		clave.append(letrasMayusculas.charAt(random.nextInt(letrasMayusculas.length())));
		clave.append(letrasMinusculas.charAt(random.nextInt(letrasMinusculas.length())));

		String todosLosCaracteres = caracteresEspeciales + numeros + letrasMayusculas + letrasMinusculas;
		for (int i = 4; i < longitudMinima; i++) {
			clave.append(todosLosCaracteres.charAt(random.nextInt(todosLosCaracteres.length())));
		}

		List<Character> claveMezclada = clave.chars()
				.mapToObj(c -> (char) c)
				.collect(Collectors.toList());
		Collections.shuffle(claveMezclada, random);

		return claveMezclada.stream()
				.map(String::valueOf)
				.collect(Collectors.joining());
	}
}
