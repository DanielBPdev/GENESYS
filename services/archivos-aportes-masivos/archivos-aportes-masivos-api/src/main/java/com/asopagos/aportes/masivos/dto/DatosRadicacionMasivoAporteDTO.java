package com.asopagos.aportes.masivos.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Datos de aportes masivos relacionada con el aportante
 * author: Juan David Quintero juan.quintero@asopagos.com
 */
@XmlRootElement
public class DatosRadicacionMasivoAporteDTO implements Serializable {

    private String numeroRadicacion;

    private BigDecimal montoTotalDiligenciado;

    private Date fechaRadicado;

    private BigDecimal montoTotalGestionable;


    private String usuarioRadicacion;

    public DatosRadicacionMasivoAporteDTO() {}


    public String getNumeroRadicacion() {
        return this.numeroRadicacion;
    }

    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    public BigDecimal getMontoTotalDiligenciado() {
        return this.montoTotalDiligenciado;
    }

    public void setMontoTotalDiligenciado(BigDecimal montoTotalDiligenciado) {
        this.montoTotalDiligenciado = montoTotalDiligenciado;
    }

    public Date getFechaRadicado() {
        return this.fechaRadicado;
    }

    public void setFechaRadicado(Date fechaRadicado) {
        this.fechaRadicado = fechaRadicado;
    }

    public BigDecimal getMontoTotalGestionable() {
        return this.montoTotalGestionable;
    }

    public void setMontoTotalGestionable(BigDecimal montoTotalGestionable) {
        this.montoTotalGestionable = montoTotalGestionable;
    }

    public String getUsuarioRadicacion() {
        return this.usuarioRadicacion;
    }

    public void setUsuarioRadicacion(String usuarioRadicacion) {
        this.usuarioRadicacion = usuarioRadicacion;
    }


}