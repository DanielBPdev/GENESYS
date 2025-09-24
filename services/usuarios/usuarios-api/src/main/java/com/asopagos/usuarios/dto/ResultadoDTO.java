package com.asopagos.usuarios.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que transporta los resultados de cambio de contraseña
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class ResultadoDTO implements Serializable{

    /**
     * Mensaje de respuesta
     */
    private String mensaje;
    
    /**
     * Tipo de respuesta
     */
    private Boolean error;

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
     * @return the error
     */
    public Boolean getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(Boolean error) {
        this.error = error;
    }
    
}
