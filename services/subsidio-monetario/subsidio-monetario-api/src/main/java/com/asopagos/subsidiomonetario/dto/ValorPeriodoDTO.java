package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>Descripcion:</b> Clase DTO que contiene el valor seleccionado para un periodo <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class ValorPeriodoDTO implements Serializable {

    private static final long serialVersionUID = -1113273171972835905L;

    /**
     * Fecha del periodo en formato Long 
     */
    private Long periodo;
    
    /**
     * Valor de la cuota en el periodo 
     */
    private BigDecimal valorPeriodo;
    
    /** Valor de la cuota agraria en el periodo*/
    private BigDecimal valorCuotaAgraria;
    
    /** Valor del factor de discapacidad en el periodo */
    private BigDecimal valorDiscapacidad;
    
    /** Valor de la cuota ajustado en BD */
    private BigDecimal nuevoValorCuota;
    
    /** Valor de la cuota agraria ajustado en BD */
    private BigDecimal nuevoValorCuotaAgraria;
    
    /** Valor que indica si la validación la paremetrización se encuentra registrada */
    private Boolean resultado;

    /**
     * @param periodo
     * @param valorPeriodo
     */
    public ValorPeriodoDTO(Long periodo, BigDecimal valorPeriodo) {
        this.periodo = periodo;
        this.valorPeriodo = valorPeriodo;
    }    

    /**
     * @param periodo
     */
    public ValorPeriodoDTO(Long periodo) {
        this.periodo = periodo;
    }
    
    /**
     * @param periodo
     * @param valorPeriodo
     * @param valorCuotaAgraria
     */
    public ValorPeriodoDTO(Long periodo, BigDecimal valorPeriodo, BigDecimal valorCuotaAgraria) {
        this.periodo = periodo;
        this.valorPeriodo = valorPeriodo;
        this.valorCuotaAgraria = valorCuotaAgraria;
    }   
   
    /**
     * @param valorPeriodo
     * @param valorCuotaAgraria
     */
    public ValorPeriodoDTO(BigDecimal valorPeriodo, BigDecimal valorCuotaAgraria) {
        this.valorPeriodo = valorPeriodo;
        this.valorCuotaAgraria = valorCuotaAgraria;
    } 

    /**
     * @param periodo
     * @param valorPeriodo
     * @param valorCuotaAgraria
     * @param valorDiscapacidad
     * @param nuevoValorCuota
     * @param nuevoValorCuotaAgraria
     */
    public ValorPeriodoDTO(Date periodo, BigDecimal valorPeriodo, BigDecimal valorCuotaAgraria, 
            BigDecimal nuevoValorCuota, BigDecimal nuevoValorCuotaAgraria) {
        this.periodo = periodo.getTime();
        this.valorPeriodo = valorPeriodo;
        this.valorCuotaAgraria = valorCuotaAgraria;        
        this.nuevoValorCuota = nuevoValorCuota;
        this.nuevoValorCuotaAgraria = nuevoValorCuotaAgraria;
    }
    
    /**
     * @param periodo
     * @param resultado
     */
    public ValorPeriodoDTO(Long periodo, Boolean resultado) {
        this.periodo = periodo;
        this.resultado = resultado;
    }
    
    /**
     * Constructor por defecto
     */
    public ValorPeriodoDTO(){
        super();
    }

    /**
     * @return the periodo
     */
    public Long getPeriodo() {
        return periodo;
    }
    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }
    /**
     * @return the valorPeriodo
     */
    public BigDecimal getValorPeriodo() {
        return valorPeriodo;
    }
    /**
     * @param valorPeriodo the valorPeriodo to set
     */
    public void setValorPeriodo(BigDecimal valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }

    /**
     * @return the valorCuotaAgraria
     */
    public BigDecimal getValorCuotaAgraria() {
        return valorCuotaAgraria;
    }

    /**
     * @param valorCuotaAgraria the valorCuotaAgraria to set
     */
    public void setValorCuotaAgraria(BigDecimal valorCuotaAgraria) {
        this.valorCuotaAgraria = valorCuotaAgraria;
    }

    /**
     * @return the valorDiscapacidad
     */
    public BigDecimal getValorDiscapacidad() {
        return valorDiscapacidad;
    }

    /**
     * @param valorDiscapacidad the valorDiscapacidad to set
     */
    public void setValorDiscapacidad(BigDecimal valorDiscapacidad) {
        this.valorDiscapacidad = valorDiscapacidad;
    }

    /**
     * @return the nuevoValorCuota
     */
    public BigDecimal getNuevoValorCuota() {
        return nuevoValorCuota;
    }

    /**
     * @param nuevoValorCuota the nuevoValorCuota to set
     */
    public void setNuevoValorCuota(BigDecimal nuevoValorCuota) {
        this.nuevoValorCuota = nuevoValorCuota;
    }

    /**
     * @return the nuevoValorCuotaAgraria
     */
    public BigDecimal getNuevoValorCuotaAgraria() {
        return nuevoValorCuotaAgraria;
    }

    /**
     * @param nuevoValorCuotaAgraria the nuevoValorCuotaAgraria to set
     */
    public void setNuevoValorCuotaAgraria(BigDecimal nuevoValorCuotaAgraria) {
        this.nuevoValorCuotaAgraria = nuevoValorCuotaAgraria;
    }

	/**
	 * @return the resultado
	 */
	public Boolean getResultado() {
		return resultado;
	}

	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}


}
