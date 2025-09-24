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
public class EncabezadoAportesFuturosDTO implements Serializable {
    
    
	private static final long serialVersionUID = 1L;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo general
	 */
	private String consecutivoEncabezado;
        private String operacionRecaudo;
        private String fechaRegistro;
        private String antiguedadRecaudo;
        private String fechaPago;
        private String tipoEntidad;
        private String tipoIdentificacionEntidad;
        private String numeroIdentificacionEntidad;
        private String razonSocial;
        private String montoAporte;
        private String montoInteres;
        private String montoTotal;
        private String estadoAporte;
        private String pagoSiMismo;
        private String aporteConDetalle;
        private String tipoReconocimiento;
        private String fechaMovimiento;
        private String formaReconocimiento;
        private String detalleEncabezado;
        private String apgId;

        public EncabezadoAportesFuturosDTO() {
        }

    public EncabezadoAportesFuturosDTO(String consecutivoEncabezado, String operacionRecaudo, String fechaRegistro, String antiguedadRecaudo, String fechaPago, String tipoEntidad, String tipoIdentificacionEntidad, String numeroIdentificacionEntidad, String razonSocial, String montoAporte, String montoInteres, String montoTotal, String estadoAporte, String pagoSiMismo, String aporteConDetalle, String tipoReconocimiento, String fechaMovimiento, String formaReconocimiento, String detalleEncabezado, String apgId) {
        this.consecutivoEncabezado = consecutivoEncabezado;
        this.operacionRecaudo = operacionRecaudo;
        this.fechaRegistro = fechaRegistro;
        this.antiguedadRecaudo = antiguedadRecaudo;
        this.fechaPago = fechaPago;
        this.tipoEntidad = tipoEntidad;
        this.tipoIdentificacionEntidad = tipoIdentificacionEntidad;
        this.numeroIdentificacionEntidad = numeroIdentificacionEntidad;
        this.razonSocial = razonSocial;
        this.montoAporte = montoAporte;
        this.montoInteres = montoInteres;
        this.montoTotal = montoTotal;
        this.estadoAporte = estadoAporte;
        this.pagoSiMismo = pagoSiMismo;
        this.aporteConDetalle = aporteConDetalle;
        this.tipoReconocimiento = tipoReconocimiento;
        this.fechaMovimiento = fechaMovimiento;
        this.formaReconocimiento = formaReconocimiento;
        this.detalleEncabezado = detalleEncabezado;
        this.apgId = apgId;
    }

    public String getApgId() {
        return apgId;
    }

    public void setApgId(String apgId) {
        this.apgId = apgId;
    }



    public String getConsecutivoEncabezado() {
        return consecutivoEncabezado;
    }

    public void setConsecutivoEncabezado(String consecutivoEncabezado) {
        this.consecutivoEncabezado = consecutivoEncabezado;
    }

    public String getOperacionRecaudo() {
        return operacionRecaudo;
    }

    public void setOperacionRecaudo(String operacionRecaudo) {
        this.operacionRecaudo = operacionRecaudo;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public String getTipoIdentificacionEntidad() {
        return tipoIdentificacionEntidad;
    }

    public void setTipoIdentificacionEntidad(String tipoIdentificacionEntidad) {
        this.tipoIdentificacionEntidad = tipoIdentificacionEntidad;
    }

    public String getNumeroIdentificacionEntidad() {
        return numeroIdentificacionEntidad;
    }

    public void setNumeroIdentificacionEntidad(String numeroIdentificacionEntidad) {
        this.numeroIdentificacionEntidad = numeroIdentificacionEntidad;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    public String getEstadoAporte() {
        return estadoAporte;
    }

    public void setEstadoAporte(String estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    public String getPagoSiMismo() {
        return pagoSiMismo;
    }

    public void setPagoSiMismo(String pagoSiMismo) {
        this.pagoSiMismo = pagoSiMismo;
    }

    public String getAporteConDetalle() {
        return aporteConDetalle;
    }

    public void setAporteConDetalle(String aporteConDetalle) {
        this.aporteConDetalle = aporteConDetalle;
    }

    public String getTipoReconocimiento() {
        return tipoReconocimiento;
    }

    public void setTipoReconocimiento(String tipoReconocimiento) {
        this.tipoReconocimiento = tipoReconocimiento;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getFormaReconocimiento() {
        return formaReconocimiento;
    }

    public void setFormaReconocimiento(String formaReconocimiento) {
        this.formaReconocimiento = formaReconocimiento;
    }

    public String getDetalleEncabezado() {
        return detalleEncabezado;
    }

    public void setDetalleEncabezado(String detalleEncabezado) {
        this.detalleEncabezado = detalleEncabezado;
    }
        
        
	/**
	 * Referencia al registro general del aporte (staging)
	 */
	
        
    
}
