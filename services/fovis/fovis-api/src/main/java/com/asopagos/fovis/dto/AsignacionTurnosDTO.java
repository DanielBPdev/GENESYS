package com.asopagos.fovis.dto;

import com.asopagos.rest.security.dto.UserDTO;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta de un postulante a FOVIS.
 * <b>HU-032</b>
 * @author <a href="mailto:criparra@heinsohn.com.co">Cristian David Parra Zuluaga</a>
 */
@XmlRootElement
public class AsignacionTurnosDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = -7671976893350969838L;

    /**
     * usuario.
     */
    private UserDTO usuarioAsignar;

    /**
     * usuario.
     */
    private UserDTO usuarioRadica;

    /**
     * Número de radicado.
     */
    private String radicado;

    /**
     * Número de contador.
     */
    private Long idSolicitud;

    public AsignacionTurnosDTO(UserDTO usuarioAsignar, UserDTO usuarioRadica, String radicado, Long idSolicitud) {
        this.usuarioAsignar = usuarioAsignar;
        this.usuarioRadica = usuarioRadica;
        this.radicado = radicado;
        this.idSolicitud = idSolicitud;
    }

    /**
     * Método constructor para devolver los datos consultados relacionados a la lista de postulantes a FOVIS.
     */


    public AsignacionTurnosDTO() {

    }

    public UserDTO getUsuarioAsignar() {
        return usuarioAsignar;
    }

    public void setUsuarioAsignar(UserDTO usuarioAsignar) {
        this.usuarioAsignar = usuarioAsignar;
    }

    public UserDTO getUsuarioRadica() {
        return usuarioRadica;
    }

    public void setUsuarioRadica(UserDTO usuarioRadica) {
        this.usuarioRadica = usuarioRadica;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
