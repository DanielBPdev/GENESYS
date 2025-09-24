/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.dto.modelo;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.io.Serializable;

/**
 *
 * @author linam
 */
public class ConfirmacionAbonoBancarioCargueDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 22574235798347593L;

    /**
     * Descripción del tipo de identificación del admin subsidio
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Descripción del numero de identificación del admin subsidio
     */
    private String numeroIdentificacion;
    /**
     * Descripción del casId del admin subsidio
     */
    private String casId;
    /**
     * Descripción del tipo de cuenta del admin subsidio
     */
    private String tipoCuenta;
    /**
     * Descripción del numero de cuenta del admin subsidio
     */
    private String numeroCuenta;
    /**
     * Descripción del valor de transferencia
     */
    private String valorTransferencia;
    /**
     * Descripción del resultado
     */
    private String resultadoAbono;
    /**
     * Descripción del motivoRechazo
     */
    private String motivoRechazoAbono;
    /**
     * Descripción de la fecha de confirmacion del abono 
     */
    private String fechaConfirmacionAbono;

    public ConfirmacionAbonoBancarioCargueDTO() {
    }

    public ConfirmacionAbonoBancarioCargueDTO(
            String tipoIdentificacion,
            String numeroIdentificacion,
            String casId,
            String tipoCuenta,
            String numeroCuenta,
            String valorTransferencia,
            String resultadoAbono,
            String motivoRechazoAbono,
            String fechaConfirmacionAbono

    ) {
        this.tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        this.numeroIdentificacion = numeroIdentificacion; 
        this.casId = casId; 
        this.tipoCuenta = tipoCuenta; 
        this.numeroCuenta = numeroCuenta; 
        this.valorTransferencia = valorTransferencia; 
        this.resultadoAbono = resultadoAbono; 
        this.motivoRechazoAbono = motivoRechazoAbono; 
        this.fechaConfirmacionAbono = fechaConfirmacionAbono; 
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }


    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getCasId() {
        return casId;
    }

    public void setCasId(String casId) {
        this.casId = casId;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getValorTransferencia() {
        return valorTransferencia;
    }

    public void setValorTransferencia(String valorTransferencia) {
        this.valorTransferencia = valorTransferencia;
    }

    public String getResultadoAbono() {
        return resultadoAbono;
    }

    public void setResultadoAbono(String resultadoAbono) {
        this.resultadoAbono = resultadoAbono;
    }

    public String getMotivoRechazoAbono() {
        return motivoRechazoAbono;
    }

    public void setMotivoRechazoAbono(String motivoRechazoAbono) {
        this.motivoRechazoAbono = motivoRechazoAbono;
    }

    public String getFechaConfirmacionAbono() {
        return fechaConfirmacionAbono;
    }

    public void setFechaConfirmacionAbono(String fechaConfirmacionAbono) {
        this.fechaConfirmacionAbono = fechaConfirmacionAbono;
    }

    
    
    
}