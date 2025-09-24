package com.asopagos.dto;

import java.io.Serializable;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO para wrapper de mensajes de respuesta de tipo error
 * <b>Historia de Usuario:</b> Transversal
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class RespuestaErrorDTO implements Serializable {
    
    
    private Status status;
    
    private String mensaje;
    
    /**
     * Reoresenta mensaje de excepción para debug
     */
    private String stackTrace;
    
    /**
     * Indica si la excepción ocurrió al consumir el API del BPM
     */
    private Boolean errorBPM;

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

	/**
	 * @return
	 */
	public String getStackTrace() {
		return stackTrace;
	}

	/**
	 * @param stackTrace
	 */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

    /**
     * @return the errorBPM
     */
    public Boolean getErrorBPM() {
        return errorBPM;
    }

    /**
     * @param errorBPM the errorBPM to set
     */
    public void setErrorBPM(Boolean errorBPM) {
        this.errorBPM = errorBPM;
    }
}
