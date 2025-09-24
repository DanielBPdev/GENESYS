package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InfoUltimoAporteDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal ultimoAporteRecibido;
    private BigDecimal valorUltimoAporte;
    private BigDecimal valorUltimoSalario;
    private Date fechaUltimoAporte;
    private String periodoPagado;
    private Short horasLaboradasUltimoPago;
    private String sucursalAsociada;

    /**
     * 
     */
    public InfoUltimoAporteDTO() {
    }

    /**
     * @param ultimoAporteRecibido
     * @param valorUltimoAporte
     * @param valorUltimoSalario
     * @param fechaUltimoAporte
     * @param periodoPagado
     * @param horasLaboradasUltimoPago
     * @param sucursalAsociada
     */
    public InfoUltimoAporteDTO(BigDecimal ultimoAporteRecibido, BigDecimal valorUltimoAporte, BigDecimal valorUltimoSalario, Date fechaUltimoAporte,
            String periodoPagado, Short horasLaboradasUltimoPago) {
        this.ultimoAporteRecibido = ultimoAporteRecibido;
        this.valorUltimoAporte = valorUltimoAporte;
        this.valorUltimoSalario = valorUltimoSalario;
        this.fechaUltimoAporte = fechaUltimoAporte;
        this.periodoPagado = periodoPagado;
        this.horasLaboradasUltimoPago = horasLaboradasUltimoPago;
    }

    /**
     * @return the ultimoAporteRecibido
     */
    public BigDecimal getUltimoAporteRecibido() {
        return ultimoAporteRecibido;
    }

    /**
     * @param ultimoAporteRecibido the ultimoAporteRecibido to set
     */
    public void setUltimoAporteRecibido(BigDecimal ultimoAporteRecibido) {
        this.ultimoAporteRecibido = ultimoAporteRecibido;
    }

    /**
     * @return the valorUltimoAporte
     */
    public BigDecimal getValorUltimoAporte() {
        return valorUltimoAporte;
    }

    /**
     * @param valorUltimoAporte the valorUltimoAporte to set
     */
    public void setValorUltimoAporte(BigDecimal valorUltimoAporte) {
        this.valorUltimoAporte = valorUltimoAporte;
    }

    /**
     * @return the valorUltimoSalario
     */
    public BigDecimal getValorUltimoSalario() {
        return valorUltimoSalario;
    }

    /**
     * @param valorUltimoSalario the valorUltimoSalario to set
     */
    public void setValorUltimoSalario(BigDecimal valorUltimoSalario) {
        this.valorUltimoSalario = valorUltimoSalario;
    }

    /**
     * @return the fechaUltimoAporte
     */
    public Date getFechaUltimoAporte() {
        return fechaUltimoAporte;
    }

    /**
     * @param fechaUltimoAporte the fechaUltimoAporte to set
     */
    public void setFechaUltimoAporte(Date fechaUltimoAporte) {
        this.fechaUltimoAporte = fechaUltimoAporte;
    }

    /**
     * @return the periodoPagado
     */
    public String getPeriodoPagado() {
        return periodoPagado;
    }

    /**
     * @param periodoPagado the periodoPagado to set
     */
    public void setPeriodoPagado(String periodoPagado) {
        this.periodoPagado = periodoPagado;
    }

    /**
     * @return the horasLaboradasUltimoPago
     */
    public Short getHorasLaboradasUltimoPago() {
        return horasLaboradasUltimoPago;
    }

    /**
     * @param horasLaboradasUltimoPago the horasLaboradasUltimoPago to set
     */
    public void setHorasLaboradasUltimoPago(Short horasLaboradasUltimoPago) {
        this.horasLaboradasUltimoPago = horasLaboradasUltimoPago;
    }

    /**
     * @return the sucursalAsociada
     */
    public String getSucursalAsociada() {
        return sucursalAsociada;
    }

    /**
     * @param sucursalAsociada the sucursalAsociada to set
     */
    public void setSucursalAsociada(String sucursalAsociada) {
        this.sucursalAsociada = sucursalAsociada;
    }
}
