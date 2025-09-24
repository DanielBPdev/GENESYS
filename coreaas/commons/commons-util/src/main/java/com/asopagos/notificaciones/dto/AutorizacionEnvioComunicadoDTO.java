package com.asopagos.notificaciones.dto;

import java.io.Serializable;

/**
 * Permite administrar los datos de autorizacion de correo a fin
 * de conocer si en su momento debe ser o no enviada la notificación
 * al servidor SMTP.
 * @author mamonroy
 *
 */
public class AutorizacionEnvioComunicadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Indica el correo electronico del destinatario
     */
    private String destinatario;
    
    /**
     * Indica si la persona destinatario autoriza el envio de correos.
     */
    private Boolean autorizaEnvio;
    
    /**
     * Empleador implacado en el proceso del comunicado (Para el caso de Cartera)
     */
    private Long idEmpleador;
    
    /**
     * Persona implicada en el proceso del comunicado (Para el caso de Cartera)
     */
    private Long idPersona;
    
    public AutorizacionEnvioComunicadoDTO() {
    }
    
    public AutorizacionEnvioComunicadoDTO(String destinatario, Boolean autorizaEnvio) {
        super();
        this.destinatario = destinatario;
        this.autorizaEnvio = autorizaEnvio;
    }

    /**
     * @return the destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario the destinatario to set
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * @return the autorizaEnvio
     */
    public Boolean getAutorizaEnvio() {
        return autorizaEnvio;
    }

    /**
     * @param autorizaEnvio the autorizaEnvio to set
     */
    public void setAutorizaEnvio(Boolean autorizaEnvio) {
        this.autorizaEnvio = autorizaEnvio;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }
}
