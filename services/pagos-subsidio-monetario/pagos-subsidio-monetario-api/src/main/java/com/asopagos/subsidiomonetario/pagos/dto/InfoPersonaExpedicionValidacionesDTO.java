package com.asopagos.subsidiomonetario.pagos.dto;


import com.asopagos.enumeraciones.personas.EstadoBeneficiarioEnum;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class InfoPersonaExpedicionValidacionesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersona;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private Long administradorSubsidio;
    private Long grupoFamiliar;
    private String marca;
    private EstadoBeneficiarioEnum estadoBeneficiario;


    public InfoPersonaExpedicionValidacionesDTO() {
    }

    public InfoPersonaExpedicionValidacionesDTO(Long idPersona, String tipoIdentificacion, String numeroIdentificacion, Long administradorSubsidio, Long grupoFamiliar, String marca, EstadoBeneficiarioEnum estadoBeneficiario) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.administradorSubsidio = administradorSubsidio;
        this.grupoFamiliar = grupoFamiliar;
        this.marca = marca;
        this.estadoBeneficiario = estadoBeneficiario;

    }

    public InfoPersonaExpedicionValidacionesDTO(Long idPersona, String tipoIdentificacion, String numeroIdentificacion, Long administradorSubsidio, Long grupoFamiliar, String marca, String estadoBeneficiario) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.administradorSubsidio = administradorSubsidio;
        this.grupoFamiliar = grupoFamiliar;
        this.marca = marca;
        this.estadoBeneficiario = EstadoBeneficiarioEnum.valueOf(estadoBeneficiario);

    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Long getAdministradorSubsidio() {
        return administradorSubsidio;
    }

    public void setAdministradorSubsidio(Long administradorSubsidio) {
        this.administradorSubsidio = administradorSubsidio;
    }

    public Long getGrupoFamiliar() {
        return grupoFamiliar;
    }

    public void setGrupoFamiliar(Long grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public EstadoBeneficiarioEnum getEstadoBeneficiario() {
        return estadoBeneficiario;
    }

    public void setEstadoBeneficiario(EstadoBeneficiarioEnum estadoBeneficiario) {
        this.estadoBeneficiario = estadoBeneficiario;
    }

    @Override
    public String toString() {
        return "InfoPersonaExpedicionValidacionesDTO{" +
                "idPersona=" + idPersona +
                ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", administradorSubsidio=" + administradorSubsidio +
                ", grupoFamiliar=" + grupoFamiliar +
                ", marca=" + marca +
                ", estadoBeneficiario=" + estadoBeneficiario +
                '}';
    }
}
