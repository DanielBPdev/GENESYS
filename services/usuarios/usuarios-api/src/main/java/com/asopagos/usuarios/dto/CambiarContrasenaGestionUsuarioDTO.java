package com.asopagos.usuarios.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que transporta los de ingreso de un Usuario
 * 
 * 
 */
@XmlRootElement
public class CambiarContrasenaGestionUsuarioDTO implements Serializable{
    
    private String nombreUsuario;
    
    private String email;
    
    private String passNuevo;
    
    private String passConfirmacion;

    private String tokenRestablecimiento;

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
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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

    /**
     * @return the tokenRestablecimiento
     */
    public String getTokenRestablecimiento() {
        return this.tokenRestablecimiento;
    }

    /**
     * @param tokenRestablecimiento the tokenRestablecimiento to set
     */
    public void setTokenRestablecimiento(String tokenRestablecimiento) {
        this.tokenRestablecimiento = tokenRestablecimiento;
    }
}
