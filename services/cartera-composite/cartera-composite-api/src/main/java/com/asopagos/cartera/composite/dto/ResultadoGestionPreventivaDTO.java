package com.asopagos.cartera.composite.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * DTO que contiene la información del resultado de la gestión preventiva.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @version 1.0
 * @created 03-oct.-2017 8:49:59 a.m.
 */
public class ResultadoGestionPreventivaDTO implements Serializable {

    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = -4489906588307814486L;
    /**
     * Atributo que indica si hubo contacto efectivo con el aportante.
     */
    private Boolean contactoEfectivo;
    /**
     * Atributo que indica si el aportante requiere un proceso de fiscalización.
     */
    private Boolean requiereFiscalizacion;
    /**
     * Atributo que contiene la descripción de las acciones realizadas y los
     * resultados obtenidos.
     */
    private String descripcion;
    /**
     * Atributo que contiene el id de la tarea.
     */
    private Long idTarea;
    /**
     * Número de radicación de la solicitud preventiva.
     */
    @NotNull
    private String numeroRadicacion;

    public ResultadoGestionPreventivaDTO() {

    }

    /**
     * Método que retorna el valor de contactoEfectivo.
     * @return valor de contactoEfectivo.
     */
    public Boolean getContactoEfectivo() {
        return contactoEfectivo;
    }

    /**
     * Método encargado de modificar el valor de contactoEfectivo.
     * @param valor
     *        para modificar contactoEfectivo.
     */
    public void setContactoEfectivo(Boolean contactoEfectivo) {
        this.contactoEfectivo = contactoEfectivo;
    }

    /**
     * Método que retorna el valor de requiereFiscalizacion.
     * @return valor de requiereFiscalizacion.
     */
    public Boolean getRequiereFiscalizacion() {
        return requiereFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de requiereFiscalizacion.
     * @param valor
     *        para modificar requiereFiscalizacion.
     */
    public void setRequiereFiscalizacion(Boolean requiereFiscalizacion) {
        this.requiereFiscalizacion = requiereFiscalizacion;
    }

    /**
     * Método que retorna el valor de descripcion.
     * @return valor de descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método encargado de modificar el valor de descripcion.
     * @param valor
     *        para modificar descripcion.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * @param valor
     *        para modificar idTarea.
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
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
     * @param valor
     *        para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

}