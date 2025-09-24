package com.asopagos.asignaciones.dto;

public class InicioTareaDTO {
    private Long proceso;
    private Long tarea;
    private String usuario;
    private Long fecha;
    
    public Long getProceso() {
        return proceso;
    }
    public Long getTarea() {
        return tarea;
    }
    public String getUsuario() {
        return usuario;
    }
    public Long getFecha() {
        return fecha;
    }
    public void setProceso(Long proceso) {
        this.proceso = proceso;
    }
    public void setTarea(Long tarea) {
        this.tarea = tarea;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }
 

}