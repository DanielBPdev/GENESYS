package com.asopagos.usuarios.ejb;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;


public class EmailService {

    private final ILogger logger = LogManager.getLogger(EmailService.class);

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String fromEmail;

    /**
     * Constructor para inicializar el servicio de correo con las configuraciones.
     * @param host Host SMTP 
     * @param port Puerto SMTP 
     * @param username Usuario SMTP.
     * @param password Contraseña SMTP.
     * @param fromEmail Correo remitente
     */
    public EmailService(String host, int port, String username, String password, String fromEmail) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.fromEmail = fromEmail;
    }

    /**
     * Envía un correo electrónico de restablecimiento de contraseña.
     *
     * @param toEmail El correo electrónico del destinatario.
     * @param resetLink La URL completa para restablecer la contraseña.
     * @param expirationMinutes El tiempo de expiración del enlace en minutos.
     * @throws Exception Si ocurre un error al enviar el correo.
     */
    public void sendPasswordResetEmail(String toEmail, String resetLink, int expirationMinutes) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Usar STARTTLS para seguridad
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.ssl.trust", host); // Importante para TLS/STARTTLS si el certificado no es completamente reconocido

        // Obtener la sesión de correo con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session); 
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Restablecimiento de Contraseña");
            String emailBody = "Alguien ha solicitado cambiar las credenciales de tu cuenta. "
                             + "Si has sido tú, haz clic en el enlace siguiente para restablecer contraseña:\n\n"
                             + resetLink + "\n\n"
                             + "Este enlace expirará en " + expirationMinutes + " minutos.\n\n"
                             + "Si no quieres reiniciar tus credenciales, simplemente ignora este mensaje y no se realizará ningún cambio.";

            message.setText(emailBody);

            Transport.send(message);
            logger.info("Correo de restablecimiento de contraseña enviado exitosamente a: " + toEmail);
        } catch (Exception e) {
            logger.error("Error al enviar el correo de restablecimiento de contraseña a " + toEmail + ": " + e.getMessage());
            throw e; 
        }
    }

    /**
     * Envía un correo electrónico con una nueva contraseña generada.
     *
     * @param toEmail El correo electrónico del destinatario.
     * @param username El nombre de usuario (email o login) del destinatario.
     * @param newPassword La nueva contraseña generada.
     * @throws Exception Si ocurre un error al enviar el correo.
     */
    public void sendNewPasswordEmail(String toEmail, String username, String newPassword) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailService.this.username, EmailService.this.password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Nueva Contraseña de Acceso");
            String emailBody = "Alguien ha solicitado cambiar las credenciales de tu cuenta. "
                                 + "Si has sido tú, a continuación se muestran tus nuevas credenciales:\n\n"
                                 + "Usuario: " + username + "\n"
                                 + "Contraseña Nueva: " + newPassword + "\n\n"
                                 + "Por seguridad te recomendamos cambiar esta contraseña por una personal en tu primer inicio de sesión.\n\n"
                                 + "Si no solicitaste este cambio, por favor, contactar con el administrador del sistema.";

            message.setText(emailBody);

            Transport.send(message);
            logger.info("Correo con nueva contraseña enviado exitosamente a: " + toEmail);
        } catch (Exception e) {
            logger.error("Error al enviar el correo con la nueva contraseña a " + toEmail + ": " + e.getMessage());
            throw e;
        }
    }
}