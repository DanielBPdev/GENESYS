package com.asopagos.notificaciones.ejb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.EntityManager;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.notificaciones.EstadoEnvioNotificacionEnum;
import com.asopagos.enumeraciones.notificaciones.RolContactoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.ConstansNotification;
import com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion;
import com.asopagos.notificaciones.dto.ArchivoAdjuntoDTO;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.DesEncrypter;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.notificaciones.client.NiyarakyClient;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import com.google.gson.Gson;
import java.util.Collections;

/**
 * Utilitario para la construcción y envió de notificaciones
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 */
public class NotificacionesUtil {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(NotificacionesUtil.class);

	/** tipos de datos soportados como imagenes embebidas */
	private final static String JPG = "image/jpeg";
	private final static String GIF = "image/gif";
	private final static String PNG = "image/png";
	private final static String ZIP = "application/zip";
    private final static String EMAIL_REGEX = "(^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$)|^$";
    private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
	private final Gson gson = new Gson();
	/**
	 * Método que se encarga de generar y enviar una notificación (email)
	 * 
	 * @param notificacion
	 */
	public void enviarCorreoUtil(NotificacionDTO notificacion) {
		logger.debug("Inicia enviarCorreoUtil(NotificacionDTO)");
		if (notificacion == null) {
			logger.debug("Finaliza enviarCorreoUtil(NotificacionDTO): No parámetros no válidos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
		}
		try {
			String smtpPasswordEncypted = (String) CacheManager
					.getParametro(ParametrosSistemaConstants.MAIL_SMTP_PASSWORD);
			String smtpPassword = DesEncrypter.getInstance().decrypt(smtpPasswordEncypted);

			Properties props = initProperties();
			Session session = initSession(props, smtpPassword);

			MimeMessage message;

			try {
				Transport transport = session.getTransport("smtp");
				transport.connect(session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_USER), smtpPassword);
				resolverParametros(notificacion);

				// se resuelven los parametros que pueda tener la notificación
				// en el cuerpo
				// y en el asunto en caso de tener parametros no propios de la
				// plantilla
				resolverParametros(notificacion); message = new MimeMessage(session);
				message = new MimeMessage(session);
				String smtpFromName = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_FROM_NAME);
				if (smtpFromName != null) { message.setFrom(new InternetAddress(
					session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM), smtpFromName));
				} else { message.setFrom(new InternetAddress(
					session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM)));
				}

				message = inicializarDestinatarios(message, notificacion);
				message.setSubject(notificacion.getAsunto());
				MimeMultipart mimeMP = new MimeMultipart();

				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(notificacion.getMensaje(), "text/html; charset=utf-8"); mimeMP.addBodyPart(textPart);

				mimeMP = inicializarArchivosAdjuntos(mimeMP, notificacion);
				adjuntarArchivos(mimeMP, notificacion);

				message.setContent(mimeMP);
				transport.sendMessage(message, message.getAllRecipients());
			} catch (SendFailedException e) {
				e.printStackTrace();
				verificarCausaError(e);
			}
		} catch (Exception e) {
			logger.error(
					"Finaliza enviarCorreoUtil(NotificacionDTO): Ha ocurrido un error construyendo el mensaje a notificar",
					e);
			logger.debug(
					"Finaliza enviarCorreoUtil(NotificacionDTO): Ha ocurrido un error construyendo el mensaje a notificar");
			if (e instanceof ParametroInvalidoExcepcion) {
				throw (ParametroInvalidoExcepcion) e;
			}
			throw new TechnicalException("Ha ocurrido un error construyendo el mensaje a notificar: ", e);
		}
		logger.debug("Finaliza enviarCorreoUtil(NotificacionDTO)");
	}

	/**
	 * Envia el correo usando el proveedor Niyaraky.
	 */
	public String enviarCorreoNiyarakyUtil(NotificacionDTO notificacion) {
		logger.info("Inicia enviarCorreoNiyarakyUtil()");

		// Resuelve los parámetros dinámicos de la notificación
		resolverParametros(notificacion);

		try{
			NiyarakyClient client = new NiyarakyClient();
			
			// Declaración de variables de entrada

			String asunto = (notificacion.getNumeroIdentificacionEmpleador() != null && !notificacion.getNumeroIdentificacionEmpleador().isEmpty()) 
				? notificacion.getAsunto() + " - " + notificacion.getNumeroIdentificacionEmpleador()
				: notificacion.getAsunto();
			String texto = notificacion.getMensaje();
			String nombreDestinatario = prepararNombreDestinatario(notificacion.getDestinatarioTO());
			String correoDestinatario = prepararCorreoDestinatario(notificacion);
			List<Map<String, String>> adjuntos = prepararAdjuntos(notificacion);
			String adjuntosJson = adjuntos != null ? gson.toJson(adjuntos) : "[]";
			String respuesta = client.enviarMensaje(asunto, texto, nombreDestinatario, correoDestinatario, adjuntosJson);

            if (respuesta == null || respuesta.isEmpty()) {
                throw new TechnicalException("No se recibió una respuesta válida del servicio Niyaraky.");
            }

			logger.info("Finaliza enviarCorreoNirayakyUtil()");

			return respuesta;
		}catch(Exception e){
			logger.error("Error al enviar el correo a través del servicio Niyaraky", e);
			throw new TechnicalException("Error al enviar el correo", e);
		}
	}

    private String prepararNombreDestinatario(List<String> nombresDestinatarios) {
        if (nombresDestinatarios == null || nombresDestinatarios.isEmpty()) {
            throw new TechnicalException("Debe especificar al menos un destinatario.");
        }

        return nombresDestinatarios.get(0);
    }

    private String prepararCorreoDestinatario(NotificacionDTO notificacion) {
        logger.debug("Inicia prepararCorreoDestinatario(NotificacionDTO)");
        List<String> destinatarios = new ArrayList<>();
        boolean hayDestinatario = false;

        if (notificacion.getDestinatarioTO() != null) {
            for (String email : notificacion.getDestinatarioTO()) {
                if (email != null && !email.isEmpty() && validarEmail(email)) {
                    destinatarios.add(email);
                    hayDestinatario = true;
                }
            }
        }

        if (notificacion.getDestinatarioCC() != null) {
            for (String email : notificacion.getDestinatarioCC()) {
                if (email != null && !email.isEmpty() && validarEmail(email)) {
                    destinatarios.add(email);
                    hayDestinatario = true;
                }
            }
        }

        if (notificacion.getDestinatarioBCC() != null) {
            for (String email : notificacion.getDestinatarioBCC()) {
                if (email != null && !email.isEmpty() && validarEmail(email)) {
                    destinatarios.add(email);
                    hayDestinatario = true;
                }
            }
        }

        if (!hayDestinatario) {
            logger.debug("Finaliza prepararCorreoDestinatario(NotificacionDTO): No se encontraron destinatarios válidos");
            throw new TechnicalException("Debe especificar al menos un destinatario válido.");
        }

		if(destinatarios.size() > 1){
			/*
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8))) {

				for (String email : destinatarios) {
					writer.println(email + ";" + email);
				}

				writer.flush();
				return baos.toString(StandardCharsets.UTF_8.name());

			} catch (Exception e) {
				throw new TechnicalException("Error generando el archivo CSV de destinatarios en base64", e);
			}*/
			return destinatarios.get(0);
		}else{
			return destinatarios.get(0);
		}
    }

	///==========================================================================
	/**
	 * Prepara los adjuntos en formato JSON para el servicio Niyaraky.
	 * 
	 * @param notificacion DTO con la información de la notificación
	 * @return List<Map<String, String>> Lista de adjuntos con nombre y contenido
	 */
	private List<Map<String, String>> prepararAdjuntos(NotificacionDTO notificacion) {
		logger.debug("Inicia prepararAdjuntos(NotificacionDTO)");
		
		if (notificacion == null || notificacion.getArchivosAdjuntos() == null) {
			return null;
		}

		try {
			// Procesar imágenes integradas en el mensaje HTML
			String mensaje = procesarImagenesIntegradas(notificacion);
			notificacion.setMensaje(mensaje);

			List<Map<String, String>> adjuntos = new ArrayList<>();
			Integer attachedFileMaxSize = 1; // Megas
			String rutaAdjuntosFallidos = null;
			
			List<ArchivoAdjuntoDTO> archivos = notificacion.getArchivosAdjuntos();
			
			if (archivos != null && !archivos.isEmpty()) {
				// Verificar si los archivos deben comprimirse
				if (Boolean.TRUE.equals(notificacion.getArchivosAdjuntosZip())) {
					ArchivoAdjuntoDTO fileTemp = getZip("notificacion", archivos);
					double tamArchivo = obtenerTamanoAdjuntos(Collections.singletonList(fileTemp));
					
					if (tamArchivo <= attachedFileMaxSize) {
						Map<String, String> adjunto = new HashMap<>();
						adjunto.put("NombreAdjunto", "notificacion.zip");
						adjunto.put("Adjunto", Base64.getEncoder().encodeToString(fileTemp.getContent()));
						adjuntos.add(adjunto);
					} else {
						guardarArchivos(Collections.singletonList(fileTemp), rutaAdjuntosFallidos);
						notificacion.setMensaje(agregarMensajeAdicional(notificacion.getMensaje(), 
							attachedFileMaxSize, tamArchivo, rutaAdjuntosFallidos, fileTemp.getFileName()));
					}
				} else {
					double tamArchivos = obtenerTamanoAdjuntos(archivos);
					if (tamArchivos <= attachedFileMaxSize) {
						// Procesar cada archivo individual
						for (ArchivoAdjuntoDTO archivo : archivos) {
							Map<String, String> adjunto = new HashMap<>();
							adjunto.put("NombreAdjunto", archivo.getFileName());
							adjunto.put("Adjunto", Base64.getEncoder().encodeToString(archivo.getContent()));
							adjuntos.add(adjunto);
						}
					} else {
						guardarArchivos(archivos, rutaAdjuntosFallidos);
						String nombresArchivos = obtenerNombresArchivos(archivos);
						notificacion.setMensaje(agregarMensajeAdicional(notificacion.getMensaje(), 
							attachedFileMaxSize, tamArchivos, rutaAdjuntosFallidos, nombresArchivos));
					}
				}
			}
			
			return adjuntos.isEmpty() ? null : adjuntos;
			
		} catch (Exception e) {
			logger.error("Error preparando los archivos adjuntos", e);
			throw new TechnicalException("Error preparando los archivos adjuntos", e);
		}
	}

	/**
	 * Procesa las imágenes integradas en el mensaje HTML
	 */
	private String procesarImagenesIntegradas(NotificacionDTO notificacion) {
		String mensaje = notificacion.getMensaje();
		if (notificacion.getArchivosAdjuntos() != null) {
			int cont = 0;
			StringBuilder fileName = new StringBuilder();
			for (ArchivoAdjuntoDTO attachFile : notificacion.getArchivosAdjuntos()) {
				if (attachFile.getContent() != null
						&& (attachFile.getFileType().equals(JPG) || attachFile.getFileType().equals(PNG)
								|| attachFile.getFileType().equals(GIF))
						&& attachFile.getIntegratedImage() != null && attachFile.getIntegratedImage()) {
					fileName.setLength(0);
					fileName.append(attachFile.getFileName());
					if (mensaje.contains(fileName)) {
						String htmlText = ConstansNotification.ETIQUETA_IMG + cont + ConstansNotification.ETIQUETA_TITLE
								+ attachFile.getFileName() + ConstansNotification.ETIQUETA_CLOSE;
						mensaje = mensaje.replace(fileName, htmlText);
						cont++;
					}
				}
			}
		}
		return mensaje;
	}

	/**
	 * Obtiene los nombres de los archivos concatenados
	 */
	private String obtenerNombresArchivos(List<ArchivoAdjuntoDTO> archivos) {
		StringBuilder nombres = new StringBuilder(": ");
		for (ArchivoAdjuntoDTO attachFile : archivos) {
			nombres.append(attachFile.getFileName()).append(", ");
		}
		return nombres.toString();
	}

	/**
	 * Agrega el mensaje adicional cuando los archivos exceden el tamaño
	 */
	private String agregarMensajeAdicional(String mensaje, double maxSize, double tamReal, 
		String rutaFallidos, String nombresArchivos) {
		Map<String, String> params = new HashMap<>();
		params.put("0", String.valueOf(maxSize));
		params.put("1", String.valueOf(tamReal));
		params.put("2", rutaFallidos);
		params.put("3", nombresArchivos);
		return mensaje + ".\n\n " + resolverMsjAdicional(ConstansNotification.MESSAGE_ADITIONAL, params);
	}

	///==========================================================================

	/**
	 * Método que se encarga de generar y enviar una notificación (email)
	 * 
	 * @param notificacion
	 */
	public List<NotificacionParametrizadaDTO> enviarListaCorreoUtil(List<NotificacionParametrizadaDTO> notificaciones) {
		logger.debug("Inicia enviarCorreoUtil(List<NotificacionDTO>)");
		try {
			if (notificaciones == null && notificaciones.isEmpty()) {
				logger.debug("Finaliza enviarCorreoUtil(NotificacionDTO): Parámetros no válidos");
				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
			}
			
			String smtpPasswordEncypted = (String) CacheManager
					.getParametro(ParametrosSistemaConstants.MAIL_SMTP_PASSWORD);
			String smtpPassword = DesEncrypter.getInstance().decrypt(smtpPasswordEncypted);

			Properties props = initProperties();
			Session session = initSession(props, smtpPassword);

			MimeMessage message;
			Transport transport = session.getTransport("smtp");
			transport.connect(session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_USER), smtpPassword);
			for(NotificacionDTO notificacion : notificaciones) {
				try {
					// se resuelven los parametros que pueda tener la notificación en el cuerpo
					// y en el asunto en caso de tener parametros no propios de la plantilla
					resolverParametros(notificacion);
					message = new MimeMessage(session);
					String smtpFromName = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_FROM_NAME);
					if(smtpFromName != null) {
					    message.setFrom(new InternetAddress(session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM), smtpFromName));
					} else {
					    message.setFrom(new InternetAddress(session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM)));
					}
					message.setSubject(notificacion.getAsunto());
					message.setDescription(notificacion.getMensaje());
					message = inicializarDestinatarios(message, notificacion);
					
					MimeMultipart mimeMP = new MimeMultipart();
					mimeMP = inicializarArchivosAdjuntos(mimeMP, notificacion);
					message.setContent(mimeMP);
					adjuntarArchivos(mimeMP, notificacion);
					transport.sendMessage(message, message.getAllRecipients());
				} catch (SendFailedException e) {
					try {
						verificarCausaError(e);
					} catch (Exception eVCE) {
						logger.error(
								"Finaliza enviarCorreoUtil(List<NotificacionDTO>): Ha ocurrido un error construyendo el mensaje a notificar",
								eVCE);
						logger.debug(
								"Finaliza enviarCorreoUtil(List<NotificacionDTO>): Ha ocurrido un error construyendo el mensaje a notificar");
						if (eVCE instanceof ParametroInvalidoExcepcion) {
							notificacion.setCausaErrorEnvio(e.getMessage());
							
						} else {
							notificacion.setCausaErrorEnvio("Ha ocurrido un error construyendo el mensaje a notificar: " +e.getMessage());
						}
						notificacion.setEstadoEnvioNotificacion(EstadoEnvioNotificacionEnum.FALLIDA);
					}
				}
			}
			transport.close();
		}catch (Exception e) {
			for(NotificacionDTO notificacion : notificaciones) {
				notificacion.setCausaErrorEnvio("Ha ocurrido un error construyendo el mensaje a notificar: " +e.getMessage());
				notificacion.setEstadoEnvioNotificacion(EstadoEnvioNotificacionEnum.FALLIDA);
			}
			logger.error(
					"Finaliza enviarCorreoUtil(List<NotificacionDTO>): Ha ocurrido un error al conectar con el smtp",
					e);
			logger.debug(
					"Finaliza enviarCorreoUtil(List<NotificacionDTO>): Ha ocurrido un error al conectar con el smtp");
		}
		logger.debug("Finaliza enviarCorreoUtil(List<NotificacionDTO>)");
		return notificaciones;
	}
	
	/**
	 * Método que inicializa ls propiedades de la conexión smtp
	 * @return properties
	 */
	private Properties initProperties() {
		logger.debug("Inicia iniProperties()");
		// se obtinen parametros de la base de datos por medio de la cache
		String smtpSendPartial = (String) CacheManager
		        .getParametro(ParametrosSistemaConstants.MAIL_SMTP_SENDPARTIAL);
		String smtpAuth = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_AUTH);
		String smtpSsl = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_SSL);
		String smtpHost = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_HOST);
		String smtpPort = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_PORT);
		String smtpUser = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_USER);
		String smtpFrom = (String) CacheManager.getParametro(ParametrosSistemaConstants.MAIL_SMTP_FROM);

		Properties props = new Properties();
		// Se setean las propiedades del servidor
		// leer desde cache constantes
		props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_SENDPARTIAL, smtpSendPartial);
		props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_AUTH, smtpAuth);
		props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_SSL, smtpSsl);
		props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_HOST, smtpHost);
		props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_PORT, smtpPort);
		props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_USER, smtpUser);
		props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM, smtpFrom);
		
		logger.debug("Finaliza iniProperties()");
		return props;
	}
	
	/**
	 * Método que inicializa la session SMTP
	 * 
	 * @param props
	 * @param smtpPassword
	 * @return session
	 */
	private Session initSession(Properties props, String smtpPassword) {
		Session session = null;
		try {
			String smtpAuth = props.getProperty(ParametrosSistemaConstants.MAIL_SMTP_AUTH);
			if (smtpAuth != null && Boolean.TRUE.toString().equals(smtpAuth.toLowerCase())) {
				session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								props.getProperty(ParametrosSistemaConstants.MAIL_SMTP_USER), smtpPassword);
					}
				});
			} else {
				session = Session.getInstance(props);
			}
		} catch (Exception e) {
			logger.error("Se ha presentado un error iniciando el servidor de correo parametrizado", e);
			throw new TechnicalException(
					"Se ha presentado un error iniciando el servidor de correo parametrizado.");
		}
		return session;
	}

	/**
	 * Método que se encarga de construir los destinatarios del mensaje
	 * 
	 * @param message
	 * @param notificacion
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private MimeMessage inicializarDestinatarios(MimeMessage message, NotificacionDTO notificacion)
			throws AddressException, MessagingException {

		logger.debug("Inicia inicializarDestinatarios(MimeMessage, NotificacionDTO)");
		boolean hayDestinatario = false;
	logger.info("**__** inicializarDestinatarios"+message);
		if (notificacion.getDestinatarioTO() != null) {
			for (String email : notificacion.getDestinatarioTO()) {
					logger.info("**__** inicializarDestinatarios email:"+email);
				if (email != null && !email.isEmpty()) {
                    if (!validarEmail(email)) {
                        continue;
                    }
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
					hayDestinatario = true;
				}
			}
		}
		if (notificacion.getDestinatarioCC() != null) {
			for (String email : notificacion.getDestinatarioCC()) {
				if (email != null && !email.isEmpty()) {
                    if (!validarEmail(email)) {
                        continue;
                    }                    
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
					hayDestinatario = true;
				}
			}
		}
		if (notificacion.getDestinatarioBCC() != null) {
			for (String email : notificacion.getDestinatarioBCC()) {
				if (email != null && !email.isEmpty()) {
                    if (!validarEmail(email)) {
                        continue;
                    }                    
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(email));
					hayDestinatario = true;
				}
			}
		}

		if (hayDestinatario == false) {
			logger.debug("Finaliza inicializarDestinatarios(MimeMessage, NotificacionDTO): Error no hay destinatarios");
			throw new ParametroInvalidoExcepcion(
					"Ha ocurrido un error construyendo el mensaje a notificar: No se indicaron destinatarios");
		}

		logger.debug("Finaliza inicializarDestinatarios(MimeMessage, NotificacionDTO)");
			logger.info("**__** inicializarDestinatarios messagefin :"+message);
		return message;
	}

	/**
	 * Método que se encarga de parsear links para los nombres de los archivos
	 * dentro del mensaje
	 * 
	 * @param mimeMP
	 * @param notificacion
	 * @return
	 * @throws MessagingException
	 */
	private MimeMultipart inicializarArchivosAdjuntos(MimeMultipart mimeMP, NotificacionDTO notificacion)
			throws MessagingException {
		logger.debug("Inicia inicializarArchivosAdjuntos(MimeMultipart, NotificacionDTO)");
		String mensaje = notificacion.getMensaje();
		if (notificacion.getArchivosAdjuntos() != null) {
			int cont = 0;
			StringBuilder fileName = new StringBuilder();
			for (ArchivoAdjuntoDTO attachFile : notificacion.getArchivosAdjuntos()) {
				if (attachFile.getContent() != null
						&& (attachFile.getFileType().equals(JPG) || attachFile.getFileType().equals(PNG)
								|| attachFile.getFileType().equals(GIF))
						&& attachFile.getIntegratedImage() != null && attachFile.getIntegratedImage()) {
					fileName.setLength(0);
					fileName.append(attachFile.getFileName());
					if (mensaje.contains(fileName)) {
						String htmlText = ConstansNotification.ETIQUETA_IMG + cont + ConstansNotification.ETIQUETA_TITLE
								+ attachFile.getFileName() + ConstansNotification.ETIQUETA_CLOSE;
						mensaje = mensaje.replace(fileName, htmlText);
						cont++;
					}
				}
			}
		}
		MimeBodyPart mimeAttachmentPart = new MimeBodyPart();

		mimeAttachmentPart.setContent(mensaje, "text/html");
		mimeMP.addBodyPart(mimeAttachmentPart);
		logger.debug("Finalizar inicializarArchivosAdjuntos(MimeMultipart, NotificacionDTO)");
		return mimeMP;
	}

	/**
	 * Método que se encarga de adjuntar los binarios de los archivos
	 * 
	 * @param mimeMP
	 * @param notificacion
	 */
	private void adjuntarArchivos(MimeMultipart mimeMP, NotificacionDTO notificacion) {

		logger.debug("Inicia adjuntarArchivos(MimeMultipart, NotificacionDTO)");

		String rutaAdjuntosFallidos = null;
		Integer attachedFileMaxSize = 1; // Megas
		String msjAdicional = ConstansNotification.MESSAGE_ADITIONAL;
		String cuerpoMensaje = notificacion.getMensaje();

		// si viene archivos deben adjuntarse
		if (notificacion.getArchivosAdjuntos() != null && notificacion.getArchivosAdjuntos().size() > 0) {

			// se obtienen los filesAdjuntos
			List<ArchivoAdjuntoDTO> archivos = notificacion.getArchivosAdjuntos();

			if (archivos != null && archivos.size() > 0) {

				// se hace la verificacion si los archivos deben comprimirse

				if (notificacion.getArchivosAdjuntosZip() != null && notificacion.getArchivosAdjuntosZip()) {
					// se comprimen los archivos y se adjunta
					ArchivoAdjuntoDTO fileTemp = getZip("notificacion", archivos);
					List<ArchivoAdjuntoDTO> archivosTemp = new ArrayList<ArchivoAdjuntoDTO>();
					archivosTemp.add(fileTemp);

					/*
					 * se verfica antes de adjuntar el tamaño de los archivos
					 */
					double tamArchivos = obtenerTamanoAdjuntos(archivosTemp);
					if (tamArchivos <= attachedFileMaxSize) {
						adjuntarArchivos(archivosTemp, mimeMP);
					} else {
						guardarArchivos(archivosTemp, rutaAdjuntosFallidos);
						Map<String, String> params = new HashMap<String, String>();
						params.put("0", "" + attachedFileMaxSize);
						params.put("1", "" + tamArchivos);
						params.put("2", rutaAdjuntosFallidos);
						params.put("3", fileTemp.getFileName());
						cuerpoMensaje += ".\n\n " + resolverMsjAdicional(msjAdicional, params);
					}
				} else {
					/*
					 * si no deben ir comprimidos los archivos, se adjuntan tal
					 * como vienen, pero antes se hace la verficacion del tamaño
					 */
					double tamArchivos = obtenerTamanoAdjuntos(archivos);
					if (tamArchivos <= attachedFileMaxSize) {
						adjuntarArchivos(archivos, mimeMP);
					} else {
						guardarArchivos(archivos, rutaAdjuntosFallidos);
						/*
						 * se obtienen todos los nombres de los archivos para
						 * agregarlos al cuerpo del mensaje
						 */
						String nombresArchivos = ": ";
						for (ArchivoAdjuntoDTO attachFile : archivos) {
							nombresArchivos += attachFile.getFileName() + ", ";
						}

						Map<String, String> params = new HashMap<String, String>();
						params.put("0", "" + attachedFileMaxSize);
						params.put("1", "" + tamArchivos);
						params.put("2", rutaAdjuntosFallidos);
						params.put("3", nombresArchivos);
						cuerpoMensaje += ".\n\n " + resolverMsjAdicional(msjAdicional, params);
					}
				}
			}
		}
		logger.debug("Finalizar adjuntarArchivos(MimeMultipart, NotificacionDTO)");
	}

	/**
	 * Método que se encarga de validar el fallo del envío para determinar si es
	 * por error de destinatarios
	 * 
	 * @param sendFailedException
	 */
	private void verificarCausaError(SendFailedException sendFailedException) {
		StringBuilder error = new StringBuilder();
		if (sendFailedException.getValidUnsentAddresses() != null
				&& sendFailedException.getValidUnsentAddresses().length > 0) {
			error.append("Direcciones válidas a las que no se envió el correo: ");
			Address[] b = sendFailedException.getValidUnsentAddresses();
			for (int i = 0; i < b.length; i++) {
				error.append(b[i]);
				if (i < (b.length - 1)) {
					error.append(", ");
				}
			}
		}

		if (sendFailedException.getInvalidAddresses() != null && sendFailedException.getInvalidAddresses().length > 0) {
			error.append(". \n  Direcciones destino inválidas: ");
			Address[] a = sendFailedException.getInvalidAddresses();

			for (int i = 0; i < a.length; i++) {
				error.append(a[i]);

				if (i < (a.length - 1)) {
					error.append(", ");
				}
			}
			error.append(". ");
		}

		if (error.length() == 0) {
			error.append(" Error inesperado ");
			error.append(sendFailedException.getMessage());
		}

		// el correo tiene direcciones validas a las que no se pudo
		// enviar o tiene todas sus direcciones invalidas
		if (!(sendFailedException.getValidSentAddresses() != null
				&& sendFailedException.getValidSentAddresses().length > 0
				&& (sendFailedException.getValidUnsentAddresses() == null
						|| sendFailedException.getValidUnsentAddresses().length == 0))) {
			logger.error("Ha ocurrido un error enviando el mensaje a notificar, " + error.toString(),
					sendFailedException);
			throw new ParametroInvalidoExcepcion(
					"Ha ocurrido un error enviando el mensaje a notificar, " + error.toString());
		}
	}

	/**
	 * Este metodo se encarga de comprimir los archivos del mensaje si asi esta
	 * parametrizado para la configuracion de la notificacion.
	 * 
	 * @param nombreZip
	 *            , nombre del zip a generar.
	 * @param archivos
	 *            , lista de archivos para comprimir.
	 * @return un AttachFile que con el contenido zip de los archivos adjuntos.
	 */
	private ArchivoAdjuntoDTO getZip(String nombreZip, List<ArchivoAdjuntoDTO> archivos) {

		logger.debug("Inicializa getZip(String, List<ArchivoAdjuntoDTO>)");

		// TODO: extraer utilitario de zip

		ArchivoAdjuntoDTO fileTemp = new ArchivoAdjuntoDTO();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(baos);
		zip.setLevel(Deflater.BEST_COMPRESSION);

		try {

			// validación para verificar si es un solo archivo y ya no esta comprimido.
			if (archivos.size() == 1) {
				ArchivoAdjuntoDTO comprimido = archivos.iterator().next();
				String nombreArchivo = comprimido.getFileName().toLowerCase();
				String[] extensionesArchivo = { "zip", "rar", "tar", "gzip", "gz" };

				for (int i = 0; i < extensionesArchivo.length; i++) {
					if (nombreArchivo.endsWith(extensionesArchivo[i])) {
						return comprimido;
					}
				}
			}

			boolean zipFormatoFecha = false;
			String zipFormato = null;
			for (ArchivoAdjuntoDTO file : archivos) {

				// por si hay que concatenar la fecha antes de comprimir
				if (file.getNameDate() != null && file.getNameDate()) {
					obtenerNombreFileConFecha(file);
				}

				zip.putNextEntry(new ZipEntry(file.getFileName()));
				zip.write(file.getContent());

				if (!zipFormatoFecha && file.getNameDate() != null && file.getNameDate()) {
					zipFormatoFecha = true;
					zipFormato = file.getFormatDate();
				}

			}
			zip.flush();
			zip.finish();
			zip.close();
			baos.close();

			// se establece el contenido el zip dentro de un attachfile
			fileTemp.setContent(baos.toByteArray());
			fileTemp.setFileType(ZIP);
			fileTemp.setFileName(nombreZip);
			fileTemp.setNameDate(zipFormatoFecha);
			if (fileTemp.getNameDate() != null && fileTemp.getNameDate()) {
				fileTemp.setFormatDate(zipFormato);
			}

		} catch (Exception e) {
			logger.error("Finaliza getZip(String, List<ArchivoAdjuntoDTO>)", e);
			logger.debug("Finaliza getZip(String, List<ArchivoAdjuntoDTO>): Error comprimiendo adjuntos");
			throw new ParametroInvalidoExcepcion("Error intentando comprimir archivos que no se adjuntarón");
		}

		logger.debug("Finaliza getZip(String, List<ArchivoAdjuntoDTO>)");
		return fileTemp;
	}

	/**
	 * Este metodo se encarga de concatenar al nombre del archivo la fecha con
	 * el formato establecido para el file.
	 * 
	 * @param file
	 *            , file al cual se le va a cambiar el nombre del archivo.
	 */
	private void obtenerNombreFileConFecha(ArchivoAdjuntoDTO file) {

		logger.debug("Inicia obtenerNombreFileConFecha(ArchivoAdjuntoDTO");

		// se establece un formtao por defecto por si no viene
		SimpleDateFormat formato = (new SimpleDateFormat("dd-MM-yyy"));
		if (file.getFormatDate() != null) {
			formato = new SimpleDateFormat(file.getFormatDate());
		}
		Calendar fecha = Calendar.getInstance();
		Date date = fecha.getTime();
		String fechaForm = formato.format(date);
		file.setFileName(file.getFileName() + " -" + fechaForm);

		logger.debug("Finaliza obtenerNombreFileConFecha(ArchivoAdjuntoDTO");
	}

	/**
	 * Este método se encarga de guardar los archivos adjuntos en caso de que el
	 * peso de los archivo supere el limite configurado para el smtp.
	 * 
	 * @param archivos
	 *            , archivos que se van a guardar en la ruta del disco duro
	 *            especificada por la parametrizacion del smtp en la escritura
	 *            de los archivos adjuntos sobre el disco duro.
	 */
	private void guardarArchivos(List<ArchivoAdjuntoDTO> archivos, String rutaAdjuntosFallidos) {

		logger.debug("Inicia guardarArchivos(List<ArchivoAdjuntoDTO>)");

		if (rutaAdjuntosFallidos != null) {
			for (ArchivoAdjuntoDTO attachFile : archivos) {
				try {

					if (attachFile.getNameDate() != null && attachFile.getNameDate()) {
						obtenerNombreFileConFecha(attachFile);
					}

					File file = new File(rutaAdjuntosFallidos + "//" + attachFile.getFileName());
					FileOutputStream out = new FileOutputStream(file);
					out.write(attachFile.getContent());
					out.flush();
					out.close();

				} catch (Exception e) {
					logger.error("Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>)");
					logger.debug("Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>)");
					throw new ParametroInvalidoExcepcion("Error escribiendo archivos adjuntos, no se encontro la ruta");
				}
			}
		}
		logger.debug("Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>)");
	}

	/**
	 * Este metodo se encarga de adjuntar los archivos al message.
	 * 
	 * @param archivos
	 *            , archivoos que se van a adjuntar.
	 * @param mimeMP
	 *            , parte del message que contiene los archivos.
	 * @throws NotificationException
	 *             , si ocurre algun problema cuando se adjuntan los archivos
	 */
	private void adjuntarArchivos(List<ArchivoAdjuntoDTO> archivos, MimeMultipart mimeMP) {

		logger.debug("Inicia guardarArchivos(List<ArchivoAdjuntoDTO>, MimeMultipart)");

		try {
			int cont = 0;
			for (ArchivoAdjuntoDTO file : archivos) {
				MimeBodyPart mimeattachmentPart2 = new MimeBodyPart();

				// en caso que deba concatenarse al nombre del file, la fecha
				if (file.getNameDate() != null && file.getNameDate()) {
					obtenerNombreFileConFecha(file);
				}

				if (file.getContent() != null) {
					if ((file.getFileType().equals(JPG) || file.getFileType().equals(PNG)
							|| file.getFileType().equals(GIF)) && file.getIntegratedImage() != null
							&& file.getIntegratedImage().equals(Boolean.TRUE)) {
						File file2 = new File(file.getFileName());
						OutputStream output = new FileOutputStream(file2);
						output.write(file.getContent());
						output.flush();
						output.close();

						mimeattachmentPart2.attachFile(file2);
						mimeattachmentPart2.setHeader(ConstansNotification.CONTENT_ID,
								ConstansNotification.CONTENT_OPEN + cont + ConstansNotification.MAYOR_QUE);

						cont++;
					} else {
						ByteArrayDataSource dataSource = new ByteArrayDataSource((byte[]) file.getContent(),
								file.getFileType());
						mimeattachmentPart2.setDataHandler(new DataHandler(dataSource));
						mimeattachmentPart2.setFileName(file.getFileName());
						mimeattachmentPart2.setDisposition(Part.ATTACHMENT);
					}

					mimeMP.addBodyPart(mimeattachmentPart2);
				}
			}
		} catch (Exception e) {
			logger.error(
					"Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>, MimeMultipart): Error adjutando bytes de adjuntos",
					e);
			logger.debug(
					"Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>, MimeMultipart): Error adjutando bytes de adjuntos");
			throw new ParametroInvalidoExcepcion("Error adjutando bytes de adjuntos");
		}

		logger.debug("Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>, MimeMultipart)");
	}

	/**
	 * Este metodo se encarga de obtener el tamano de los archivos adjuntos.
	 * 
	 * @param archivos
	 *            , llista de archivos a adjuntar.
	 * @return un double con el tamano de los archivos a adjuntar durante el
	 *         calculo del tamano de los archivos adjuntos.
	 * @throws NotificationException
	 *             , en caso de ocurrir algun problema durante el calculo del
	 *             tamano de los archivos adjuntos.
	 */
	private double obtenerTamanoAdjuntos(List<ArchivoAdjuntoDTO> archivos) {
		logger.debug("Inicia guardarArchivos(List<ArchivoAdjuntoDTO>)");

		final float CONV_MB = 1048576;
		double tamTotal = 0;
		try {
			for (ArchivoAdjuntoDTO file : archivos) {
				if (file.getContent() != null) {
					tamTotal += file.getContent().length;
				}
			}
		} catch (Exception e) {
			logger.error(
					"Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>): Error determinando el tamano de los archivos adjuntos",
					e);
			logger.debug(
					"Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>): Error determinando el tamano de los archivos adjuntos");
			throw new ParametroInvalidoExcepcion("Error determinando el tamano de los archivos adjuntos");
		}
		tamTotal /= CONV_MB;

		logger.debug("Finaliza guardarArchivos(List<ArchivoAdjuntoDTO>)");
		return tamTotal;
	}

	/**
	 * Este metodo se encarga de resolver los parametros para el asunto y cuerpo
	 * del mensaje en caso de que vengan.
	 * 
	 * @param notificacion
	 *            , datos de la notificacion a enviar.
	 * @param params
	 *            , mapa con los parametros a reemplazar.
	 */
	private void resolverParametros(NotificacionDTO notificacion) {
		if(notificacion.getParams() != null) {

			if (notificacion.getAsunto() != null) {
				notificacion.setAsunto(resolverMsjAdicional(
						notificacion.getAsunto(), notificacion.getParams()));
			}
	
			if (notificacion.getMensaje() != null) {
				notificacion.setMensaje(resolverMsjAdicional(
						notificacion.getMensaje(), notificacion.getParams()));
			}
		}
	}
	
	/**
	 * Este método se encarga de reemplazar en una cadena los parametros que le
	 * llegan como parámetro.
	 * 
	 * @param message
	 *            mensaje a resolver.
	 * @param params
	 *            , parametros a reemplazar.
	 * @return String con el mensaje resuelto.
	 */
	private String resolverMsjAdicional(String message, Map<String, String> params) {
		logger.debug("Inicia resolverMsjAdicional(String, Map<String, String>)");

		List<Pattern> paramPatterns = new ArrayList<Pattern>();
		List<String> paramValues = new ArrayList<String>();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue() != null) {
				paramPatterns.add(Pattern.compile("\\[" + entry.getKey() + "\\]"));
				paramValues.add(entry.getValue());
			}
		}

		message = replaceParameters(message, paramPatterns, paramValues);

		logger.debug("Finaliza resolverMsjAdicional(String, Map<String, String>)");
		return message;
	}

	/**
	 * Realiza el reemplazo de los parametros por sus valores
	 * 
	 * @param txt
	 *            , texto.
	 * @param paramPatterns
	 *            , parametros.
	 * @param paramValues
	 *            , valores de parametros.
	 * @return string con los nuevos valores.
	 */
	private String replaceParameters(String txt, List<Pattern> paramPatterns, List<String> paramValues) {
		logger.debug("Inicia replaceParameters(String, List<Pattern>, List<String>)");
		for (int i = 0; i < paramPatterns.size(); i++) {
			txt = paramPatterns.get(i).matcher(txt).replaceAll(paramValues.get(i));
		}
		logger.debug("Finaliza replaceParameters(String, List<Pattern>, List<String>)");
		return txt;
	}
	
	/**
	 * Obtiene la lista de correos según el rol del destinatario
	 * 
	 * @param destinatario Rol de contancto para el destinatario
	 * @param tipoTx Tipo de transacción para el proceso 
	 * @param parametros Mapa con los datos necesarios para realizar la consulta
	 * @param em EntityManager
	 * @return Retorna la lista de correos
	 */
	public List<CorreoPrioridadPersonaDTO> obtenerDestinatario(RolContactoEnum destinatario, TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em)  {
	    try {
	        logger.debug("Inicia obtenerDestinatario(RolContactoEnum destinatario, TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em)");
            Class<?> clazz = Class.forName(destinatario.getNombreQuery());
            DestinatarioNotificacion d =  (DestinatarioNotificacion) clazz.newInstance();
            logger.debug("Finaliza obtenerDestinatario(RolContactoEnum destinatario, TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em)");
        	return d.obtenerCorreoDestinatario(tipoTx, parametros, em);
        } catch (ClassNotFoundException e) {
            logger.debug("Finaliza obtenerDestinatario: No se encuentra la clase parametrizada");
            logger.error("Finaliza obtenerDestinatario: No se encuentra la clase parametrizada",e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.debug("Finaliza obtenerDestinatario: No se puede instanciar la clase");
            logger.error("Finaliza obtenerDestinatario: No se puede instanciar la clase",e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.debug("Finaliza obtenerDestinatario:Error en la consulta");
            logger.error("Finaliza obtenerDestinatario:Error en la consulta",e);
            e.printStackTrace();
        } 
	    return null;
	}

    /**
     * Valida una dirección de correo
     * @param La dirección de correo a validar
     * @return true si la dirección de correo es válida
     */
    private boolean validarEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email.toLowerCase());
        return matcher.matches();
    }
    
}
