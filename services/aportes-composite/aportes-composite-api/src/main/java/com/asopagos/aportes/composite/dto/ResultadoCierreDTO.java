/**
 * 
 */
package com.asopagos.aportes.composite.dto;

import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;

/**
 * Clase DTO que contiene los datos del resultado por parte de un usuario para el cierre del recaudo.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ResultadoCierreDTO {
    /**
     * Estado del resultado del usuario.
     */
    private EstadoSolicitudCierreRecaudoEnum estado;
    /**
     * Número de radicación de la solicitud.
     */
    private String numeroRadicacion;
    /**
     * Id de la tarea.
     */
    private Long idTarea;
    /**
     * Comentarios del usuario.
     */
    private String observaciones;
    /**
     * Usuario elegido.
     */
    private String usuario;
    /**
     * Método que retorna el valor de estado.
     * @return valor de estado.
     */
    public EstadoSolicitudCierreRecaudoEnum getEstado() {
        return estado;
    }
    /**
     * Método encargado de modificar el valor de estado.
     * @param valor para modificar estado.
     */
    public void setEstado(EstadoSolicitudCierreRecaudoEnum estado) {
        this.estado = estado;
    }
    /**
     * Método que retorna el valor de numeroRadicacion.
     * @return valor de numeroRadicacion.
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }
    /**
     * Método encargado de modificar el valor de numeroRadicacion.
     * @param valor para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }
    /**
     * Método que retorna el valor de idTarea.
     * @return valor de idTarea.
     */
    public Long getIdTarea() {
        return idTarea;
    }
    /**
     * Método encargado de modificar el valor de idTarea.
     * @param valor para modificar idTarea.
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }
    /**
     * Método que retorna el valor de observaciones.
     * @return valor de observaciones.
     */
    public String getObservaciones() {
        return observaciones;
    }
    /**
     * Método encargado de modificar el valor de observaciones.
     * @param valor para modificar observaciones.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    /**
     * Método que retorna el valor de usuario.
     * @return valor de usuario.
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * Método encargado de modificar el valor de usuario.
     * @param valor para modificar usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
   }
