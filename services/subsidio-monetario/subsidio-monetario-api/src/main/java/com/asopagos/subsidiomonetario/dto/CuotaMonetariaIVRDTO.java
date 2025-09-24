/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Alexander.camelo
 */
public class CuotaMonetariaIVRDTO implements Serializable{
     private static final long serialVersionUID = 1L;
        
    private Long resultado;

    private String fechaCobro;

    private BigDecimal valorAcumulado;

    private List<CuotaPeriodoIVRDTO> listaPeriodos;
    
 public CuotaMonetariaIVRDTO(){
     
 }
    public CuotaMonetariaIVRDTO(Long resultado, String fechaCobro, BigDecimal valorAcumulado, List<CuotaPeriodoIVRDTO> listaPeriodos) {
        this.resultado = resultado;
        this.fechaCobro = fechaCobro;
        this.valorAcumulado = valorAcumulado;
        this.listaPeriodos = listaPeriodos;
    }

    public Long getResultado() {
        return resultado;
    }

    public void setResultado(Long resultado) {
        this.resultado = resultado;
    }

    public String getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(String fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public BigDecimal getValorAcumulado() {
        return valorAcumulado;
    }

    public void setValorAcumulado(BigDecimal valorAcumulado) {
        this.valorAcumulado = valorAcumulado;
    }

    public List<CuotaPeriodoIVRDTO> getListaPeriodos() {
        return listaPeriodos;
    }

    public void setListaPeriodos(List<CuotaPeriodoIVRDTO> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }
    
}
