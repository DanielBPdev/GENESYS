/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio
 */
@XmlRootElement
public class DetalleAportesFuturosDTO implements Serializable {
    
    
	private static final long serialVersionUID = 1L;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo general
	 */
	private String consecutivoDetalle;
        private String operacionRecaudo;
        private String fechaRegistroAporte;
        private String antiguedadRecaudo;
        private String fechaPago;
        private String tipoPersona;
        private String tipoIdentificacion;
        private String numeroIdentificacion;
        private String nombres;
        private String montoAporte ;
        private String montoInteres;
        private String montoTotal;
        private String tipoReconocimiento ;
        private String formaReconocimiento;

    public DetalleAportesFuturosDTO() {
    }

    public DetalleAportesFuturosDTO(String consecutivoDetalle, String operacionRecaudo, String fechaRegistroAporte, String antiguedadRecaudo, String fechaPago, String tipoPersona, String tipoIdentificacion, String numeroIdentificacion, String nombres, String montoAporte, String montoInteres, String montoTotal, String tipoReconocimiento, String formaReconocimiento) {
        this.consecutivoDetalle = consecutivoDetalle;
        this.operacionRecaudo = operacionRecaudo;
        this.fechaRegistroAporte = fechaRegistroAporte;
        this.antiguedadRecaudo = antiguedadRecaudo;
        this.fechaPago = fechaPago;
        this.tipoPersona = tipoPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.montoAporte = montoAporte;
        this.montoInteres = montoInteres;
        this.montoTotal = montoTotal;
        this.tipoReconocimiento = tipoReconocimiento;
        this.formaReconocimiento = formaReconocimiento;
    }

    public String getConsecutivoDetalle() {
        return consecutivoDetalle;
    }

    public void setConsecutivoDetalle(String consecutivoDetalle) {
        this.consecutivoDetalle = consecutivoDetalle;
    }

    public String getOperacionRecaudo() {
        return operacionRecaudo;
    }

    public void setOperacionRecaudo(String operacionRecaudo) {
        this.operacionRecaudo = operacionRecaudo;
    }

    public String getFechaRegistroAporte() {
        return fechaRegistroAporte;
    }

    public void setFechaRegistroAporte(String fechaRegistroAporte) {
        this.fechaRegistroAporte = fechaRegistroAporte;
    }

    public String getAntiguedadRecaudo() {
        return antiguedadRecaudo;
    }

    public void setAntiguedadRecaudo(String antiguedadRecaudo) {
        this.antiguedadRecaudo = antiguedadRecaudo;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getMontoAporte() {
        return montoAporte;
    }

    public void setMontoAporte(String montoAporte) {
        this.montoAporte = montoAporte;
    }

    public String getMontoInteres() {
        return montoInteres;
    }

    public void setMontoInteres(String montoInteres) {
        this.montoInteres = montoInteres;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getTipoReconocimiento() {
        return tipoReconocimiento;
    }

    public void setTipoReconocimiento(String tipoReconocimiento) {
        this.tipoReconocimiento = tipoReconocimiento;
    }

    public String getFormaReconocimiento() {
        return formaReconocimiento;
    }

    public void setFormaReconocimiento(String formaReconocimiento) {
        this.formaReconocimiento = formaReconocimiento;
    }

        
        
        
	/**
	 * Referencia al registro general del aporte (staging)
	 */
	
        
    
}