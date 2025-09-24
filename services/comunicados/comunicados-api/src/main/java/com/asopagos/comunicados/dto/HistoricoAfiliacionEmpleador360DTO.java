package com.asopagos.comunicados.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;

public class HistoricoAfiliacionEmpleador360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String fechaIngreso;
    private String fechaRetiro;
    private String estadoAnterior;
    private String motivo;
    private String numeroTrabajadores;

    
    /**
     * 
     */
    public HistoricoAfiliacionEmpleador360DTO() {
    }
    /**
     * @param fechaIngreso
     * @param fechaRetiro
     * @param estadoAnterior
     * @param motivo
     * @param numeroTrabajadores
     */
    public HistoricoAfiliacionEmpleador360DTO(String fechaIngreso, String fechaRetiro, String estadoAnterior, String motivo,
            String numeroTrabajadores) {
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
        this.estadoAnterior = estadoAnterior;
        this.motivo = motivo;
        this.numeroTrabajadores = numeroTrabajadores;
    }
    /**
     * @return the fechaIngreso
     */
    public String getFechaIngreso() {
        return fechaIngreso;
    }
    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    /**
     * @return the fechaRetiro
     */
    public String getFechaRetiro() {
        return fechaRetiro;
    }
    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }
    /**
     * @return the estadoAnterior
     */
    public String getEstadoAnterior() {
        return estadoAnterior;
    }
    /**
     * @param estadoAnterior the estadoAnterior to set
     */
    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }
    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }
    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    /**
     * @return the numeroTrabajadores
     */
    public String getNumeroTrabajadores() {
        return numeroTrabajadores;
    }
    /**
     * @param numeroTrabajadores the numeroTrabajadores to set
     */
    public void setNumeroTrabajadores(String numeroTrabajadores) {
        this.numeroTrabajadores = numeroTrabajadores;
    }
}
