package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.Date;

public class DocumentosSeguimientoGestionDTO implements Serializable {

    private static final long serialVersionUID = -7907467119885169667L;

    private Long dsgId;
    private Long dsgNrOperacion;
    private Date dgsFecha;
    private String dsgActividad;
    private String dsgMedio;
    private String dsgResultado;
    private String dsgUsuario;
    private String dsgDocumento;
    private String dsgObservaciones;

    public DocumentosSeguimientoGestionDTO() {
    }

    public DocumentosSeguimientoGestionDTO(Long dsgId, Long dsgNrOperacion, Date dgsFecha, String dsgActividad, String dsgMedio, String dsgResultado, String dsgUsuario, String dsgDocumento,String dsgObservaciones) {
        this.dsgId = dsgId;
        this.dsgNrOperacion = dsgNrOperacion;
        this.dgsFecha = dgsFecha;
        this.dsgActividad = dsgActividad;
        this.dsgMedio = dsgMedio;
        this.dsgResultado = dsgResultado;
        this.dsgUsuario = dsgUsuario;
        this.dsgDocumento = dsgDocumento;
        this.dsgObservaciones=dsgObservaciones;
    }

    public Long getDsgId() {
        return dsgId;
    }

    public void setDsgId(Long dsgId) {
        this.dsgId = dsgId;
    }

    public Long getDsgNrOperacion() {
        return dsgNrOperacion;
    }

    public void setDsgNrOperacion(Long dsgNrOperacion) {
        this.dsgNrOperacion = dsgNrOperacion;
    }

    public Date getDgsFecha() {
        return dgsFecha;
    }

    public void setDgsFecha(Date dgsFecha) {
        this.dgsFecha = dgsFecha;
    }

    public String getDsgActividad() {
        return dsgActividad;
    }

    public void setDsgActividad(String dsgActividad) {
        this.dsgActividad = dsgActividad;
    }

    public String getDsgMedio() {
        return dsgMedio;
    }

    public void setDsgMedio(String dsgMedio) {
        this.dsgMedio = dsgMedio;
    }

    public String getDsgResultado() {
        return dsgResultado;
    }

    public void setDsgResultado(String dsgResultado) {
        this.dsgResultado = dsgResultado;
    }

    public String getDsgUsuario() {
        return dsgUsuario;
    }

    public void setDsgUsuario(String dsgUsuario) {
        this.dsgUsuario = dsgUsuario;
    }

    public String getDsgDocumento() {
        return dsgDocumento;
    }

    public void setDsgDocumento(String dsgDocumento) {
        this.dsgDocumento = dsgDocumento;
    }

    public String getDsgObservaciones() {
        return dsgObservaciones;
    }

    public void setDsgObservaciones(String dsgObservaciones) {
        this.dsgObservaciones = dsgObservaciones;
    }

    @Override
    public String toString() {
        return "DocumentosSeguimientoGestionDTO{" +
                "dsgId=" + dsgId +
                ", dsgNrOperacion=" + dsgNrOperacion +
                ", dgsFecha=" + dgsFecha +
                ", dsgActividad='" + dsgActividad + '\'' +
                ", dsgMedio='" + dsgMedio + '\'' +
                ", dsgResultado='" + dsgResultado + '\'' +
                ", dsgUsuario='" + dsgUsuario + '\'' +
                ", dsgDocumento='" + dsgDocumento + '\'' +
                ", dsgObservaciones='" + dsgObservaciones + '\'' +
                '}';
    }
}
