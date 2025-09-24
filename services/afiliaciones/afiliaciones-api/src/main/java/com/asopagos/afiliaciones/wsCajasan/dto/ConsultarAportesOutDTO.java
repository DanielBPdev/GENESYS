package com.asopagos.afiliaciones.wsCajasan.dto;
import java.io.Serializable;

public class ConsultarAportesOutDTO implements Serializable{
    private long anioPago;           
    private Integer fechaConsignacion; 
    private Integer mesPago;           
    private long numeroIdentificacion;               
    private long nroConsignacion;      
    private Double vrAporte;          
    private Double vrIntereses;       

    // Getters y Setters

    public long getAnioPago() {
        return anioPago;
    }

    public void setAnioPago(long anioPago) {
        this.anioPago = anioPago;
    }

    public Integer getFechaConsignacion() {
        return fechaConsignacion;
    }

    public void setFechaConsignacion(Integer fechaConsignacion) {
        this.fechaConsignacion = fechaConsignacion;
    }

    public Integer getMesPago() {
        return mesPago;
    }

    public void setMesPago(Integer mesPago) {
        this.mesPago = mesPago;
    }

    public long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public long getNroConsignacion() {
        return nroConsignacion;
    }

    public void setNroConsignacion(long nroConsignacion) {
        this.nroConsignacion = nroConsignacion;
    }

    public Double getVrAporte() {
        return vrAporte;
    }

    public void setVrAporte(Double vrAporte) {
        this.vrAporte = vrAporte;
    }

    public Double getVrIntereses() {
        return vrIntereses;
    }

    public void setVrIntereses(Double vrIntereses) {
        this.vrIntereses = vrIntereses;
    }
}
