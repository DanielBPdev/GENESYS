package co.gov.sed.sace.util;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.sat.business.ejb.EjecutarProcedimientoRecoleccion;
import com.asopagos.util.DesEncrypter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Sergio Reyes
 */
public class CorreoUtil {
    
    private static final ILogger logger = LogManager.getLogger(CorreoUtil.class);
    
    private final static String EMAIL_REGEX = "(^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$)|^$";
    private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private String smtpSendPartial;
    private String smtpAuth;
    private String smtpSsl;
    private String smtpHost;
    private String smtpPort;
    private String smtpUser;
    private String smtpFrom;
    private String smtpPasswordEncypted;
    private String smtpFromName;
    
    

    public CorreoUtil(String smtpSendPartial,String smtpAuth,String smtpSsl,String smtpHost,String smtpPort,String smtpUser,String smtpFrom,String smtpPasswordEncypted, String smtpFromName) {
        this.smtpSendPartial = smtpSendPartial;
        this.smtpAuth = smtpAuth;
        this.smtpSsl = smtpSsl;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.smtpUser = smtpUser;
        this.smtpFrom = smtpFrom;
        this.smtpPasswordEncypted = smtpPasswordEncypted;
        this.smtpFromName = smtpFromName;
    }
    
    
    
    private Properties initProperties() {
        logger.debug("Inicia iniProperties()");

        Properties props = new Properties();
        // Se setean las propiedades del servidor
        // leer desde cache constantes
        props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_SENDPARTIAL, this.smtpSendPartial);
        props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_AUTH, this.smtpAuth);
        props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_SSL, this.smtpSsl);
        props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_HOST, this.smtpHost);
        props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_PORT, this.smtpPort);
        props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_USER, this.smtpUser);
        props.setProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM, this.smtpFrom);

        logger.debug("Finaliza iniProperties()");
        return props;
    }
    
    private MimeMessage inicializarDestinatarios(MimeMessage message, Map<String,List<String>> notificacion)
			throws AddressException, MessagingException {
        logger.debug("Inicia inicializarDestinatarios(MimeMessage, NotificacionDTO)");
		boolean hayDestinatario = false;

        if (notificacion.get("TO") != null) {
                for (String email : notificacion.get("TO")) {
                        if (email != null && !email.isEmpty()) {
            if (!validarEmail(email)) {
                continue;
            }
                                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                                hayDestinatario = true;
                        }
                }
        }
        if (notificacion.get("CC") != null) {
            for (String email : notificacion.get("CC")) {
                if (email != null && !email.isEmpty()) {
                    if (!validarEmail(email)) {
                        continue;
                    }                    
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
                    hayDestinatario = true;
                }
            }
        }
        if (notificacion.get("BCC") != null) {
            for (String email : notificacion.get("BCC")) {
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
        return message;
    }
    
    private boolean validarEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email.toLowerCase());
        return matcher.matches();
    }
    
    public void enviarCorreoUtil(Map<String,Object> notificacion) {
        logger.debug("Inicia enviarCorreoUtil(NotificacionDTO)");
        if (notificacion == null) {
                logger.debug("Finaliza enviarCorreoUtil(NotificacionDTO): No parámetros no válidos");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }
        try {
            String smtpPassword = DesEncrypter.getInstance().decrypt(this.smtpPasswordEncypted);

            Properties props = initProperties();
            Session session = initSession(props, smtpPassword);

            MimeMessage message;

            try {
                Transport transport = session.getTransport("smtp");
                transport.connect(session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_USER), smtpPassword);

                // se resuelven los parametros que pueda tener la notificación
                // en el cuerpo
                // y en el asunto en caso de tener parametros no propios de la
                // plantilla
                message = new MimeMessage(session);
                String smtpFromName = this.smtpFromName;
                if (smtpFromName != null) {
                        message.setFrom(new InternetAddress(session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM),
                                        smtpFromName));
                } else {
                        message.setFrom(
                                        new InternetAddress(session.getProperty(ParametrosSistemaConstants.MAIL_SMTP_FROM)));
                }
                message.setSubject(notificacion.get("asunto").toString());
                message.setDescription(notificacion.get("mensaje").toString());
                Map<String,List<String>> destintararios = (Map<String,List<String>>)notificacion.get("destinatarios");
                message = inicializarDestinatarios(message, destintararios);

                MimeMultipart mimeMP = new MimeMultipart();
                MimeBodyPart mimeAttachmentPart = new MimeBodyPart();
                mimeAttachmentPart.setContent(notificacion.get("mensaje").toString(), "text/html");
                mimeMP.addBodyPart(mimeAttachmentPart);
                message.setContent(mimeMP);
                transport.sendMessage(message, message.getAllRecipients());

                transport.close();
            } catch (SendFailedException e) {
                    e.printStackTrace();
            }
        } catch (Exception e) {
                logger.error(
                            "Finaliza enviarCorreoUtil: Ha ocurrido un error construyendo el mensaje a notificar",
                            e);
                logger.debug(
                            "Finaliza enviarCorreoUtil: Ha ocurrido un error construyendo el mensaje a notificar");
                if (e instanceof ParametroInvalidoExcepcion) {
                        throw (ParametroInvalidoExcepcion) e;
                }
                throw new TechnicalException("Ha ocurrido un error construyendo el mensaje a notificar: ", e);
        }
        logger.debug("Finaliza enviarCorreoUtil()");
    }
    
    private Session initSession(Properties props, String smtpPassword) {
        Session session = null;
        try {
            String smtpAuth = props.getProperty(ParametrosSistemaConstants.MAIL_SMTP_AUTH);
            if (smtpAuth != null && Boolean.TRUE.toString().equals(smtpAuth.toLowerCase())) {
                session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty(ParametrosSistemaConstants.MAIL_SMTP_USER), smtpPassword);
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

}