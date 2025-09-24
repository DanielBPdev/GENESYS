package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.cartera.EstadoCorreoCertificadoEnum;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
public class NotificacionNiyarakyActualizacionEstadoInDTO implements Serializable {

    public NotificacionNiyarakyActualizacionEstadoInDTO() {
    }

    /**
     * Identificador del mensaje
     */
    @JsonProperty("idmensaje")
    @NotNull(message = "El idMensaje es obligatorio.")
    private String idMensaje;

    /**
     * Estado del correo certificado
     */
    @NotNull(message = "El estado es obligatorio.")
    private Integer estado;

    /**
     * Descripción del correo
     */
    @NotNull(message = "La descripción es obligatoria.")
    private String descripcion;

    /**
     * Fecha del evento
     */
    private String fecha_evento;

    public String getIdMensaje() {
        return this.idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Integer getEstado() {
        return this.estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_evento() {
        return this.fecha_evento;
    }

    public void setFecha_evento(String fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    @Override
    public String toString() {
        return "{" +
            " idMensaje='" + getIdMensaje() + "'" +
            ", estado='" + getEstado() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha_evento='" + getFecha_evento() + "'" +
            "}";
    }
}
