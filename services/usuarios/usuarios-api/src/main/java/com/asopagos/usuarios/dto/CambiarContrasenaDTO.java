package com.asopagos.usuarios.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que transporta los de ingreso de un Afiliado
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class CambiarContrasenaDTO implements Serializable{
    
    private String nombreUsuario;
    
    private String passActual;
    
    private String passNuevo;
    
    private String passConfirmacion;

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the passActual
     */
    public String getPassActual() {
        return passActual;
    }

    /**
     * @param passActual the passActual to set
     */
    public void setPassActual(String passActual) {
        this.passActual = passActual;
    }

    /**
     * @return the passNuevo
     */
    public String getPassNuevo() {
        return passNuevo;
    }

    /**
     * @param passNuevo the passNuevo to set
     */
    public void setPassNuevo(String passNuevo) {
        this.passNuevo = passNuevo;
    }

    /**
     * @return the passConfirmacion
     */
    public String getPassConfirmacion() {
        return passConfirmacion;
    }

    /**
     * @param passConfirmacion the passConfirmacion to set
     */
    public void setPassConfirmacion(String passConfirmacion) {
        this.passConfirmacion = passConfirmacion;
    }

}
