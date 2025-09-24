/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Alexander.camelo
 */
public class CuotaPeriodoIVRDTO implements Serializable {
    private static final long serialVersionUID = 1L;
     
    private BigDecimal valorPago;

    private String periodoSubsidio;

    private String modalidadPago;

    public CuotaPeriodoIVRDTO() {
    }

    public CuotaPeriodoIVRDTO(BigDecimal valorPago, String periodoSubsidio, String modalidadPago) {
        this.valorPago = valorPago;
        this.periodoSubsidio = periodoSubsidio;
        this.modalidadPago = modalidadPago;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getPeriodoSubsidio() {
        return periodoSubsidio;
    }

    public void setPeriodoSubsidio(String periodoSubsidio) {
        this.periodoSubsidio = periodoSubsidio;
    }

    public String getModalidadPago() {
        return modalidadPago;
    }

    public void setModalidadPago(String modalidadPago) {
        this.modalidadPago = modalidadPago;
    }

}
