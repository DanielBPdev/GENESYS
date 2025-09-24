package com.asopagos.dto.subsidiomonetario.pagos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InformacionLiquidacionFallecimientoDTO implements Serializable{
    
    
    private String numeroOperacion;
    private Date fechaCreacion;
    private String tipoIdAdministrador;
    private String numeroIdAdministrador;
    private String nombreAdministrador;
    private Date periodoLiquidado;
    private BigDecimal valorSubsidio;
    private BigDecimal descuento;
    private BigDecimal valorPagar;
    private Date fechaProgramada;
    private String medioPago;

    public InformacionLiquidacionFallecimientoDTO(String numeroOperacion, Date fechaCreacion, String tipoIdAdministrador, String numeroIdAdministrador, String nombreAdministrador, Date periodoLiquidado, BigDecimal valorSubsidio, BigDecimal descuento, BigDecimal valorPagar, Date fechaProgramada, String medioPago) {
        this.numeroOperacion = numeroOperacion;
        this.fechaCreacion = fechaCreacion;
        this.tipoIdAdministrador = tipoIdAdministrador;
        this.numeroIdAdministrador = numeroIdAdministrador;
        this.nombreAdministrador = nombreAdministrador;
        this.periodoLiquidado = periodoLiquidado;
        this.valorSubsidio = valorSubsidio;
        this.descuento = descuento;
        this.valorPagar = valorPagar;
        this.fechaProgramada = fechaProgramada;
        this.medioPago = medioPago;
    }



    public InformacionLiquidacionFallecimientoDTO() {
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTipoIdAdministrador() {
        return tipoIdAdministrador;
    }

    public void setTipoIdAdministrador(String tipoIdAdministrador) {
        this.tipoIdAdministrador = tipoIdAdministrador;
    }

    public String getNumeroIdAdministrador() {
        return numeroIdAdministrador;
    }

    public void setNumeroIdAdministrador(String numeroIdAdministrador) {
        this.numeroIdAdministrador = numeroIdAdministrador;
    }

    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    public Date getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    public void setPeriodoLiquidado(Date periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    public BigDecimal getValorSubsidio() {
        return valorSubsidio;
    }

    public void setValorSubsidio(BigDecimal valorSubsidio) {
        this.valorSubsidio = valorSubsidio;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(BigDecimal valorPagar) {
        this.valorPagar = valorPagar;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
    
}
