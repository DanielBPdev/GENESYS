package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * <b>Descripcion:</b> DTO de control de lote de archivos OF <br/>
 * <b>Módulo:</b> Asopagos - HU-211-407 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ControlLoteOFDTO implements Serializable {
    private static final long serialVersionUID = -7995825298260159419L;
    
    /** Número de lote */
    private Integer numeroLote;
    
    /** Línea de inicio del bloque */
    private Long lineaInicioLote;
    
    /** Línea de inicio del bloque */
    private Long lineaFinLote;
    
    /** Contador de cantidad de planillas del lote */
    private Integer contadorPlanillasLote = 0;
    
    /** Sumatoria de registros en planillas del lote */
    private Integer sumatoriaCantidadRegistrosPlanillasLote = 0;
    
    /** Sumatoria del valor recaudado por planilla del lote */
    private BigDecimal sumatoriaValorRecaudoPlanillasLote = BigDecimal.ZERO;
    
    /** Set de las líneas Registro tipo 6 y 8 en el lote */
    private Set<Long> lineasEnLote;

    /**
     * @param numeroLote
     */
    public ControlLoteOFDTO(Integer numeroLote, Long lineaInicioLote) {
        super();
        this.numeroLote = numeroLote;
        this.lineaInicioLote = lineaInicioLote;
        this.lineasEnLote = new HashSet<>();
    }

    /**
     * @return the numeroLote
     */
    public Integer getNumeroLote() {
        return numeroLote;
    }

    /**
     * @param numeroLote the numeroLote to set
     */
    public void setNumeroLote(Integer numeroLote) {
        this.numeroLote = numeroLote;
    }

    /**
     * @return the contadorPlanillasLote
     */
    public Integer getContadorPlanillasLote() {
        return contadorPlanillasLote;
    }
    
    public void addContadorPlanillasLote() {
        this.contadorPlanillasLote ++;
    }

    /**
     * @return the sumatoriaCantidadRegistrosPlanillasLote
     */
    public Integer getSumatoriaCantidadRegistrosPlanillasLote() {
        return sumatoriaCantidadRegistrosPlanillasLote;
    }

    /**
     * @param valorAdicion the valorAdicion to add
     */
    public void addSumatoriaCantidadRegistrosPlanillasLote(Integer valorAdicion) {
        this.sumatoriaCantidadRegistrosPlanillasLote += valorAdicion;
    }

    /**
     * @return the sumatoriaValorRecaudoPlanillasLote
     */
    public BigDecimal getSumatoriaValorRecaudoPlanillasLote() {
        return sumatoriaValorRecaudoPlanillasLote;
    }

    /**
     * @param valorAdicion the valorAdicion to add
     */
    public void addSumatoriaValorRecaudoPlanillasLote(BigDecimal valorAdicion) {
        this.sumatoriaValorRecaudoPlanillasLote = this.sumatoriaValorRecaudoPlanillasLote.add(valorAdicion);
    }

    /**
     * @return the lineasEnLote
     */
    public Set<Long> getLineasEnLote() {
        return lineasEnLote;
    }

    /**
     * @param lineasEnLote the lineasEnLote to set
     */
    public void setLineasEnLote(Set<Long> lineasEnLote) {
        this.lineasEnLote = lineasEnLote;
    }

    /**
     * @return the lineaInicioLote
     */
    public Long getLineaInicioLote() {
        return lineaInicioLote;
    }

    /**
     * @param lineaInicioLote the lineaInicioLote to set
     */
    public void setLineaInicioLote(Long lineaInicioLote) {
        this.lineaInicioLote = lineaInicioLote;
    }

    /**
     * @return the lineaFinLote
     */
    public Long getLineaFinLote() {
        return lineaFinLote;
    }

    /**
     * @param lineaFinLote the lineaFinLote to set
     */
    public void setLineaFinLote(Long lineaFinLote) {
        this.lineaFinLote = lineaFinLote;
    }
}
