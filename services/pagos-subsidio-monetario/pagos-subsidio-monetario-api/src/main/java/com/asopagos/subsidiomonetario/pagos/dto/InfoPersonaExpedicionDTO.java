package com.asopagos.subsidiomonetario.pagos.dto;

import com.asopagos.enumeraciones.personas.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class InfoPersonaExpedicionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersona;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private EstadoAfiliadoEnum estado;
    private String prioridad;
    private String nombreCompleto;


    public InfoPersonaExpedicionDTO() {
    }

    public InfoPersonaExpedicionDTO(Long idPersona, String tipoIdentificacion, String numeroIdentificacion, EstadoAfiliadoEnum estado, String prioridad) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estado = estado;
        this.prioridad = prioridad;
    }

    public InfoPersonaExpedicionDTO(Long idPersona, String tipoIdentificacion, String numeroIdentificacion, String estado, String prioridad) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estado = EstadoAfiliadoEnum.valueOf(estado);
        this.prioridad = prioridad;
    }

    public InfoPersonaExpedicionDTO(Long idPersona, String tipoIdentificacion, String numeroIdentificacion, String estado, String prioridad, String nombreCompleto) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estado = EstadoAfiliadoEnum.valueOf(estado);
        this.prioridad = prioridad;
        this.nombreCompleto = nombreCompleto;
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

    public EstadoAfiliadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoAfiliadoEnum estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return "InfoPersonaExpedicionDTO{" +
                "idPersona=" + idPersona +
                ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", estado=" + estado +
                ", prioridad='" + prioridad + '\'' +
                '}';
    }
}
