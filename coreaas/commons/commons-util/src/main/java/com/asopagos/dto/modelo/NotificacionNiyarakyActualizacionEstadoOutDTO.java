package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
public class NotificacionNiyarakyActualizacionEstadoOutDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean ok;
    private String msg;
    private History history;
    private Integer codigo;

    public NotificacionNiyarakyActualizacionEstadoOutDTO() {
    }

    public NotificacionNiyarakyActualizacionEstadoOutDTO(boolean ok, String msg, History history) {
        this.ok = ok;
        this.msg = msg;
        this.history = history;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @XmlRootElement
    public static class History implements Serializable {

        private static final long serialVersionUID = 1L;

        private String idMensaje;
        private int estado;
        private String descripcion;

        private String fechaEvento;

        // Constructor vacío
        public History() {
        }

        // Constructor con todos los campos
        public History(String idMensaje, int estado, String descripcion, String fechaEvento) {
            this.idMensaje = idMensaje;
            this.estado = estado;
            this.descripcion = descripcion;
            this.fechaEvento = fechaEvento;
        }

        // Getters y Setters
        public String getIdMensaje() {
            return idMensaje;
        }

        public void setIdMensaje(String idMensaje) {
            this.idMensaje = idMensaje;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getFechaEvento() {
            return fechaEvento;
        }

        public void setFechaEvento(String fechaEvento) {
            this.fechaEvento = fechaEvento;
        }

    }

    @Override
    public String toString() {
        return "{" +
            " ok='" + isOk() + "'" +
            ", msg='" + getMsg() + "'" +
            ", history='" + getHistory() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
