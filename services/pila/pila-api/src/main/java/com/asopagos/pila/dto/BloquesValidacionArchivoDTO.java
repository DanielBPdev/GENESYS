package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la respuesta para la tabla del detalle del archivo <br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-211-401 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda </a>
 */
public class BloquesValidacionArchivoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Descripcion de la validacion */
    private String descripcionValidacion;

    /** Bloque de validacion en el cual se encuentra el archivo */
    private EstadoProcesoArchivoEnum bloqueEstadoArchivo;

    /** Fecha de proceso del archivo */
    private Long fechaProceso;

    /** Usuario que proceso el archivo */
    private List<String> usuarioProceso;
    
    /** Bloque de validación */
    private BloqueValidacionEnum bloque;
    
    /** Historial de estados del bloque de validación */
    private List<HistorialEstadoBloque> historialEstados;

    /**
     * @param bloqueEstadoArchivo
     * @param fechaProceso
     * @param usuarioProceso
     */
    public BloquesValidacionArchivoDTO(String descripcionValidacion, EstadoProcesoArchivoEnum bloqueEstadoArchivo, Long fechaProceso,
            List<String> usuarioProceso, BloqueValidacionEnum bloque, List<HistorialEstadoBloque> historialEstados) {
        this.descripcionValidacion = descripcionValidacion;
        this.bloqueEstadoArchivo = bloqueEstadoArchivo;
        this.fechaProceso = fechaProceso;
        this.usuarioProceso = usuarioProceso;
        this.bloque = bloque;
        this.historialEstados = historialEstados;
    }

    /**
     * @return the descripcionValidacion
     */
    public String getDescripcionValidacion() {
        return descripcionValidacion;
    }

    /**
     * @param descripcionValidacion
     *        the descripcionValidacion to set
     */
    public void setDescripcionValidacion(String descripcionValidacion) {
        this.descripcionValidacion = descripcionValidacion;
    }

    /**
     * @return the bloqueEstadoArchivo
     */
    public EstadoProcesoArchivoEnum getBloqueEstadoArchivo() {
        return bloqueEstadoArchivo;
    }

    /**
     * @param bloqueEstadoArchivo
     *        the bloqueEstadoArchivo to set
     */
    public void setBloqueEstadoArchivo(EstadoProcesoArchivoEnum bloqueEstadoArchivo) {
        this.bloqueEstadoArchivo = bloqueEstadoArchivo;
    }

    /**
     * @return the fechaProceso
     */
    public Long getFechaProceso() {
        return fechaProceso;
    }

    /**
     * @param fechaProceso
     *        the fechaProceso to set
     */
    public void setFechaProceso(Long fechaProceso) {
        this.fechaProceso = fechaProceso;
    }
    
    /**
     * @return the usuarioProceso
     */
    public List<String> getUsuarioProceso() {
        return usuarioProceso;
    }

    /**
     * @param usuarioProceso the usuarioProceso to set
     */
    public void setUsuarioProceso(List<String> usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    /**
     * @return the bloque
     */
    public BloqueValidacionEnum getBloque() {
        return bloque;
    }

    /**
     * @param bloque the bloque to set
     */
    public void setBloque(BloqueValidacionEnum bloque) {
        this.bloque = bloque;
    }

    /**
     * @return the historialEstados
     */
    public List<HistorialEstadoBloque> getHistorialEstados() {
        return historialEstados;
    }

    /**
     * @param historialEstados the historialEstados to set
     */
    public void setHistorialEstados(List<HistorialEstadoBloque> historialEstados) {
        this.historialEstados = historialEstados;
    }

    public BloquesValidacionArchivoDTO() {}
}
