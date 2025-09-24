package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.personas.AdministradorSubsidio;
import com.asopagos.entidades.ccf.personas.Persona;

/**
 * DTO con los datos del Modelo de administrador de subsidio.
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 *
 */
public class AdministradorSubsidioModeloDTO extends PersonaModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 986332605379476395L;

    /**
     * Código identificador de llave primaria del Administrador del Subsidio
     */
    private Long idAdministradorSubsidio;

    /**
     * Código del municipio asociado al administrador del subsidio a través del medio de pago efectivo.
     */
    private String codigoMunicipio;

    /**
     * Constructor de la clase.
     */
    public AdministradorSubsidioModeloDTO() {
        super();
    }
    
    /**
     * Constructor que asigna los datos al DTO de AdministradorSubsidio y Persona
     * @param persona
     * @param personaDetalle
     */
    public AdministradorSubsidioModeloDTO(AdministradorSubsidio administradorSubsidio, Persona persona) {
        this.setIdAdministradorSubsidio(administradorSubsidio.getIdAdministradorSubsidio());
        super.convertToDTO(persona, null);
    }
    /**
     * Constructor que asigna los datos al DTO de AdministradorSubsidio y Persona
     * @param persona
     * @param personaDetalle
     */
    public AdministradorSubsidioModeloDTO(AdministradorSubsidio administradorSubsidio, Persona persona, String codigoMunicipio) {
        this.setIdAdministradorSubsidio(administradorSubsidio.getIdAdministradorSubsidio());
        super.convertToDTO(persona, null);
        this.setCodigoMunicipio(codigoMunicipio);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return Persona
     */
    public AdministradorSubsidio convertDTOToAdmnistradorSubsidioEntity() {
        AdministradorSubsidio admninstradorSubsidio = new AdministradorSubsidio();
        admninstradorSubsidio.setIdAdministradorSubsidio(this.getIdAdministradorSubsidio());
        admninstradorSubsidio.setIdPersona(this.getIdPersona());
        return admninstradorSubsidio;
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return Persona
     */
    public Persona convertDTOToAdmnistradorSubsidioPersonaEntity() {
        return super.convertToPersonaEntity();
    }

    /**
     * @param Asocia
     *        los datos de la Entidad al DTO
     */
    public void convertToAdministradorSubsidioDTO(AdministradorSubsidio administradorSubsidio) {
        this.setIdAdministradorSubsidio(administradorSubsidio.getIdAdministradorSubsidio());
        this.setIdPersona(administradorSubsidio.getIdPersona());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * @param persona
     *        previamente consultada.
     */
    public AdministradorSubsidio copyDTOToEntity(AdministradorSubsidio administradorSubsidio) {
        if (this.getIdAdministradorSubsidio() != null) {
            administradorSubsidio.setIdAdministradorSubsidio(this.getIdAdministradorSubsidio());
        }
        if (this.getIdPersona() != null) {
            administradorSubsidio.setIdPersona(this.getIdPersona());
        }
        return administradorSubsidio;
    }

    /**
     * @return the idAdministradorSubsidio
     */
    public Long getIdAdministradorSubsidio() {
        return idAdministradorSubsidio;
    }

    /**
     * @param idAdministradorSubsidio
     *        the idAdministradorSubsidio to set
     */
    public void setIdAdministradorSubsidio(Long idAdministradorSubsidio) {
        this.idAdministradorSubsidio = idAdministradorSubsidio;
    }

    /**
     * @return the codigoMunicipio
     */
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * @param codigoMunicipio
     *        the codigoMunicipio to set
     */
    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

}
