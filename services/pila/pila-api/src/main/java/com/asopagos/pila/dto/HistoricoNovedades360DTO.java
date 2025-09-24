package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.aportes.MarcaAccionNovedadEnum;

public class HistoricoNovedades360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String tipoNovedad;
    private String condicion;
    private Date fechaInicio;
    private Date fechaFin;
    private MarcaAccionNovedadEnum estadoNovedad;
    
    /**
     * 
     */
    public HistoricoNovedades360DTO() {
    }
    
    /**
     * @param tipoNovedad
     * @param condicion
     * @param fechaInicio
     * @param fechaFin
     * @param estadoNovedad
     */
    public HistoricoNovedades360DTO(String tipoNovedad, String condicion, Date fechaInicio, Date fechaFin, MarcaAccionNovedadEnum estadoNovedad) {
        this.tipoNovedad = tipoNovedad;
        this.condicion = condicion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoNovedad = estadoNovedad;
    }
    /**
     * @return the tipoNovedad
     */
    public String getTipoNovedad() {
        return tipoNovedad;
    }
    /**
     * @param tipoNovedad the tipoNovedad to set
     */
    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }
    /**
     * @return the condicion
     */
    public String getCondicion() {
        return condicion;
    }
    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }
    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }
    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    /**
     * @return the estadoNovedad
     */
    public MarcaAccionNovedadEnum getEstadoNovedad() {
        return estadoNovedad;
    }
    /**
     * @param estadoNovedad the estadoNovedad to set
     */
    public void setEstadoNovedad(MarcaAccionNovedadEnum estadoNovedad) {
        this.estadoNovedad = estadoNovedad;
    }
}
