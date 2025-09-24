package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DocumentosSeguimientoNovedadesListDTO implements Serializable {

    private static final long serialVersionUID = -5392494252729120112L;

    private Long dsnId;
    private Long dsnNrOperacion;
    private Date dsnFecha;
    private List<String> dsnNovedad;
    private Date dsnFechaRegistroNovedad;
    private String dsnEstado;

    public DocumentosSeguimientoNovedadesListDTO() {
    }

    public DocumentosSeguimientoNovedadesListDTO(Long dsnId, Long dsnNrOperacion, Date dsnFecha, List<String> dsnNovedad, Date dsnFechaRegistroNovedad, String dsnEstado) {
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

    public List<String> getDsnNovedad() {
        return dsnNovedad;
    }

    public void setDsnNovedad(List<String> dsnNovedad) {
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
