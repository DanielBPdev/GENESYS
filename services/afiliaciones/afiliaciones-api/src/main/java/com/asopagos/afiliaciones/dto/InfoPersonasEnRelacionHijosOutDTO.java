package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Objects;
@JsonInclude(Include.NON_EMPTY)
public class InfoPersonasEnRelacionHijosOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long idPersona;

	private String identificacionPersonaEnRelacionHijos;

	private String numeroIdentificacionPersonaEnRelacionHijos;

    private String primerNombrePersonaEnRelacionHijos;

    private String segundoNombrePersonaEnRelacionHijos;

    private String primerApellidoPersonaEnRelacionHijos;

    private String segundoApellidoPersonaEnRelacionHijos;

    private String estadoRerspectoAfiliadoPersonaEnRelacionHijos;

    private Long idBeneficiario;

    public InfoPersonasEnRelacionHijosOutDTO() {
    }

    public InfoPersonasEnRelacionHijosOutDTO(Long idPersona, Long idBeneficiario, String identificacionPersonaEnRelacionHijos, String numeroIdentificacionPersonaEnRelacionHijos, String primerNombrePersonaEnRelacionHijos, String segundoNombrePersonaEnRelacionHijos, String primerApellidoPersonaEnRelacionHijos, String segundoApellidoPersonaEnRelacionHijos, String estadoRerspectoAfiliadoPersonaEnRelacionHijos) {
        this.idPersona = idPersona;
        this.idBeneficiario = idBeneficiario;
        this.identificacionPersonaEnRelacionHijos = identificacionPersonaEnRelacionHijos;
        this.numeroIdentificacionPersonaEnRelacionHijos = numeroIdentificacionPersonaEnRelacionHijos;
        this.primerNombrePersonaEnRelacionHijos = primerNombrePersonaEnRelacionHijos;
        this.segundoNombrePersonaEnRelacionHijos = segundoNombrePersonaEnRelacionHijos;
        this.primerApellidoPersonaEnRelacionHijos = primerApellidoPersonaEnRelacionHijos;
        this.segundoApellidoPersonaEnRelacionHijos = segundoApellidoPersonaEnRelacionHijos;
        this.estadoRerspectoAfiliadoPersonaEnRelacionHijos = estadoRerspectoAfiliadoPersonaEnRelacionHijos;
        
    }

    public Long getIdPersona() {
        return this.idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdentificacionPersonaEnRelacionHijos() {
        return this.identificacionPersonaEnRelacionHijos;
    }

    public void setIdentificacionPersonaEnRelacionHijos(String identificacionPersonaEnRelacionHijos) {
        this.identificacionPersonaEnRelacionHijos = identificacionPersonaEnRelacionHijos;
    }

    public String getNumeroIdentificacionPersonaEnRelacionHijos() {
        return this.numeroIdentificacionPersonaEnRelacionHijos;
    }

    public void setNumeroIdentificacionPersonaEnRelacionHijos(String numeroIdentificacionPersonaEnRelacionHijos) {
        this.numeroIdentificacionPersonaEnRelacionHijos = numeroIdentificacionPersonaEnRelacionHijos;
    }

    public String getPrimerNombrePersonaEnRelacionHijos() {
        return this.primerNombrePersonaEnRelacionHijos;
    }

    public void setPrimerNombrePersonaEnRelacionHijos(String primerNombrePersonaEnRelacionHijos) {
        this.primerNombrePersonaEnRelacionHijos = primerNombrePersonaEnRelacionHijos;
    }

    public String getSegundoNombrePersonaEnRelacionHijos() {
        return this.segundoNombrePersonaEnRelacionHijos;
    }

    public void setSegundoNombrePersonaEnRelacionHijos(String segundoNombrePersonaEnRelacionHijos) {
        this.segundoNombrePersonaEnRelacionHijos = segundoNombrePersonaEnRelacionHijos;
    }

    public String getPrimerApellidoPersonaEnRelacionHijos() {
        return this.primerApellidoPersonaEnRelacionHijos;
    }

    public void setPrimerApellidoPersonaEnRelacionHijos(String primerApellidoPersonaEnRelacionHijos) {
        this.primerApellidoPersonaEnRelacionHijos = primerApellidoPersonaEnRelacionHijos;
    }

    public String getSegundoApellidoPersonaEnRelacionHijos() {
        return this.segundoApellidoPersonaEnRelacionHijos;
    }

    public void setSegundoApellidoPersonaEnRelacionHijos(String segundoApellidoPersonaEnRelacionHijos) {
        this.segundoApellidoPersonaEnRelacionHijos = segundoApellidoPersonaEnRelacionHijos;
    }

    public String getEstadoRerspectoAfiliadoPersonaEnRelacionHijos() {
        return this.estadoRerspectoAfiliadoPersonaEnRelacionHijos;
    }

    public void setEstadoRerspectoAfiliadoPersonaEnRelacionHijos(String estadoRerspectoAfiliadoPersonaEnRelacionHijos) {
        this.estadoRerspectoAfiliadoPersonaEnRelacionHijos = estadoRerspectoAfiliadoPersonaEnRelacionHijos;
    }

    public Long getIdBeneficiario() {
        return this.idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    @Override
    public String toString() {
        return "{" +
            " idPersona='" + getIdPersona() + "'" +
            ", identificacionPersonaEnRelacionHijos='" + getIdentificacionPersonaEnRelacionHijos() + "'" +
            ", numeroIdentificacionPersonaEnRelacionHijos='" + getNumeroIdentificacionPersonaEnRelacionHijos() + "'" +
            ", primerNombrePersonaEnRelacionHijos='" + getPrimerNombrePersonaEnRelacionHijos() + "'" +
            ", segundoNombrePersonaEnRelacionHijos='" + getSegundoNombrePersonaEnRelacionHijos() + "'" +
            ", primerApellidoPersonaEnRelacionHijos='" + getPrimerApellidoPersonaEnRelacionHijos() + "'" +
            ", segundoApellidoPersonaEnRelacionHijos='" + getSegundoApellidoPersonaEnRelacionHijos() + "'" +
            ", estadoRerspectoAfiliadoPersonaEnRelacionHijos='" + getEstadoRerspectoAfiliadoPersonaEnRelacionHijos() + "'" +
            ", idBeneficiario='" + getIdBeneficiario() + "'" +
            "}";
    }

}
