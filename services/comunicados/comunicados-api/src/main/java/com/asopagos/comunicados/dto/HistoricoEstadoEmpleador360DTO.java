package com.asopagos.comunicados.dto;

import java.io.Serializable;

public class HistoricoEstadoEmpleador360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String estado;
    private String fechaMovimiento;
    
    /**
     * 
     */
    public HistoricoEstadoEmpleador360DTO() {
    }

    /**
     * @param estado
     * @param fechaMovimiento
     */
    public HistoricoEstadoEmpleador360DTO(String estado, String fechaMovimiento) {
        this.estado = estado;
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the fechaMovimiento
     */
    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * @param fechaMovimiento the fechaMovimiento to set
     */
    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
}
