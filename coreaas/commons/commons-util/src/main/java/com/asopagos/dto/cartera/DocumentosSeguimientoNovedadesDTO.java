package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.Date;

public class DocumentosSeguimientoNovedadesDTO implements Serializable {

    private static final long serialVersionUID = -5392494252729120112L;

    private Long dsnId;
    private Long dsnNrOperacion;
    private Date dsnFecha;
    private String dsnNovedad;
    private Date dsnFechaRegistroNovedad;
    private String dsnEstado;

    public DocumentosSeguimientoNovedadesDTO() {
    }

    public DocumentosSeguimientoNovedadesDTO(Long dsnId, Long dsnNrOperacion, Date dsnFecha, String dsnNovedad, Date dsnFechaRegistroNovedad, String dsnEstado) {
        this.dsnId = dsnId;
        this.dsnNrOperacion = dsnNrOperacion;
        this.dsnFecha = dsnFecha;
        this.dsnNovedad = dsnNovedad;
        this.dsnFechaRegistroNovedad = dsnFechaRegistroNovedad;
        this.dsnEstado = dsnEstado;
    }

    public Long getDsnId() {
        return dsnId;
    }

    public void setDsnId(Long dsnId) {
        this.dsnId = dsnId;
    }

    public Long getDsnNrOperacion() {
        return dsnNrOperacion;
    }

    public void setDsnNrOperacion(Long dsnNrOperacion) {
        this.dsnNrOperacion = dsnNrOperacion;
    }

    public Date getDsnFecha() {
        return dsnFecha;
    }

    public void setDsnFecha(Date dsnFecha) {
        this.dsnFecha = dsnFecha;
    }

    public String getDsnNovedad() {
        return dsnNovedad;
    }

    public void setDsnNovedad(String dsnNovedad) {
        this.dsnNovedad = dsnNovedad;
    }

    public Date getDsnFechaRegistroNovedad() {
        return dsnFechaRegistroNovedad;
    }

    public void setDsnFechaRegistroNovedad(Date dsnFechaRegistroNovedad) {
        this.dsnFechaRegistroNovedad = dsnFechaRegistroNovedad;
    }

    public String getDsnEstado() {
        return dsnEstado;
    }

    public void setDsnEstado(String dsnEstado) {
        this.dsnEstado = dsnEstado;
    }
}
