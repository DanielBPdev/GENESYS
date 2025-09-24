package com.asopagos.dto.afiliaciones;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/*
 * <b>Descripcion:</b> Clase DTO que representa la información básica del empleador al registrar un intento de afiliación
 *
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 */
public class EmpleadorIntentoAfiliacionDTO implements Serializable{
    
    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = 5652928105393569005L;

    /**
     * Atributo que representa la razón social del empleador
     */
    private String razonSocialEmp;
    
    /**
     * Atributo que representa la identificación del empleador
     */
    private TipoIdentificacionEnum tipoIdentificacionEmp;
    
    /**
     * Atributo que representa el número de identificación del empleador
     */
    private String numeroIdentificacionEmp;
    
    /**
     * Atributo que representa la dirección del empleador
     */
    private String direccionEmp;
    
    /**
     * Atributo que representa el teléfono fijo del empleador
     */
    private String telefonoFijoEmp;
    
    /**
     * Atributo que representa el representate legal del empleador
     */
    private String representanteLegalEmp;
    
    /**
     * Construtor
     */
    public EmpleadorIntentoAfiliacionDTO() {
        
    }

    /**
     * @return the razonSocialEmp
     */
    public String getRazonSocialEmp() {
        return razonSocialEmp;
    }

    /**
     * @param razonSocialEmp the razonSocialEmp to set
     */
    public void setRazonSocialEmp(String razonSocialEmp) {
        this.razonSocialEmp = razonSocialEmp;
    }

    /**
     * @return the tipoIdentificacionEmp
     */
    public TipoIdentificacionEnum getTipoIdentificacionEmp() {
        return tipoIdentificacionEmp;
    }

    /**
     * @param tipoIdentificacionEmp the tipoIdentificacionEmp to set
     */
    public void setTipoIdentificacionEmp(TipoIdentificacionEnum tipoIdentificacionEmp) {
        this.tipoIdentificacionEmp = tipoIdentificacionEmp;
    }

    /**
     * @return the numeroIdentificacionEmp
     */
    public String getNumeroIdentificacionEmp() {
        return numeroIdentificacionEmp;
    }

    /**
     * @param numeroIdentificacionEmp the numeroIdentificacionEmp to set
     */
    public void setNumeroIdentificacionEmp(String numeroIdentificacionEmp) {
        this.numeroIdentificacionEmp = numeroIdentificacionEmp;
    }

    /**
     * @return the direccionEmp
     */
    public String getDireccionEmp() {
        return direccionEmp;
    }

    /**
     * @param direccionEmp the direccionEmp to set
     */
    public void setDireccionEmp(String direccionEmp) {
        this.direccionEmp = direccionEmp;
    }

    /**
     * @return the telefonoFijoEmp
     */
    public String getTelefonoFijoEmp() {
        return telefonoFijoEmp;
    }

    /**
     * @param telefonoFijoEmp the telefonoFijoEmp to set
     */
    public void setTelefonoFijoEmp(String telefonoFijoEmp) {
        this.telefonoFijoEmp = telefonoFijoEmp;
    }

    /**
     * @return the representanteLegalEmp
     */
    public String getRepresentanteLegalEmp() {
        return representanteLegalEmp;
    }

    /**
     * @param representanteLegalEmp the representanteLegalEmp to set
     */
    public void setRepresentanteLegalEmp(String representanteLegalEmp) {
        this.representanteLegalEmp = representanteLegalEmp;
    }
    
    
}
