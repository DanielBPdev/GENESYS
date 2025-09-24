package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;

public class GeneracionArchivoCierreDTO implements Serializable{
    
    /**
     * Serial
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Fecha inicial del periodo
     */
    private Long fechaInicio;
    
    /**
     * Fecha final del periodo
     */
    private Long fechaFin;
    
    /**
     * Resumen de recaudo de aportes
     */
    private List<ResumenCierreRecaudoDTO> resumenRecaudo;
    
    
    public GeneracionArchivoCierreDTO() {
    }


    public GeneracionArchivoCierreDTO(Long fechaInicio, Long fechaFin, List<ResumenCierreRecaudoDTO> resumenRecaudo) {
        super();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.resumenRecaudo = resumenRecaudo;
    }


    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }


    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }


    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }


    /**
     * @return the resumenRecaudo
     */
    public List<ResumenCierreRecaudoDTO> getResumenRecaudo() {
        return resumenRecaudo;
    }


    /**
     * @param resumenRecaudo the resumenRecaudo to set
     */
    public void setResumenRecaudo(List<ResumenCierreRecaudoDTO> resumenRecaudo) {
        this.resumenRecaudo = resumenRecaudo;
    }
    
}