package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripcion:</b> Clase DTO para la comunicación con pantallas que representa un registro del resumen del listado de subsidios a anular
 * por prescripción o por vencimiento
 *
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 */
public class SubsidioAnularDTO implements Serializable{

    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = -1333464203501090630L;
    
    /**
     * Atributo que representa la liquidación asociada del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private BigInteger solicitudLiquidacionSubsidio;
    
    /**
     * Atributo que representa el periodo de liquidación del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Date periodoLiquidado;
    
    /**
     * Atributo que representa el tipo de cuota que tiene el subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private String tipoCuotaSubsidio;
    
    /**
     * Atributo que representa el tipo de liquidación asociada al detalle
     */
    private String tipoLiquidacion;
    
    /**
     * Atributo que representa el medio de pago
     */
    private TipoMedioDePagoEnum medioDePago;
    
    /**
     * Atributo que representa el nombre del departamento del sitio de pago
     */
    private String nombreDepartamento;
    
    /**
     * Atributo que representa el nombre del municipio del sitio de pago
     */
    private String nombreMunicipio;
    
    /**
     * Atributo que representa el Valor total del subsidio monetario a ser
     * prescrito o anulado por fecha de vencimiento
     */
    private BigDecimal valorTotal;

    /**
     * Construtor
     */
    public SubsidioAnularDTO() {
        
    }
    
    /**
     * Constructor
     * @param campos Arreglo con los campos de un registro del resumen del listado de subsidios a anular
     * por prescripción o por vencimiento 
     * @param filtro 6 si se filtra por medio de pago de lo contrario se filtro por otro criterio
     */
    public SubsidioAnularDTO(Object[] campos,Integer filtro) {
        super();
        if(filtro<6) {
        this.solicitudLiquidacionSubsidio = campos[0] != null ? (BigInteger)campos[0] : null;
        this.periodoLiquidado = campos[1] != null ? (Date)campos[1] : null;
        this.tipoCuotaSubsidio = campos[2] != null ? (String)campos[2] : null;
        this.tipoLiquidacion = campos[3] != null ? (String)campos[3] : null;
        this.medioDePago = campos[4] != null ? TipoMedioDePagoEnum.valueOf((String)campos[4]) : null;
        this.valorTotal = campos[5] != null ? (BigDecimal)campos[5] : null;
        }else{
            this.nombreDepartamento = campos[0] != null ? (String)campos[0] : null;
            this.nombreMunicipio = campos[1] != null ? (String)campos[1] : null;
            this.valorTotal = campos[2] != null ? (BigDecimal)campos[2] : null;
        }
    }

    /**
     * @return the solicitudLiquidacionSubsidio
     */
    public BigInteger getSolicitudLiquidacionSubsidio() {
        return solicitudLiquidacionSubsidio;
    }

    /**
     * @param solicitudLiquidacionSubsidio the solicitudLiquidacionSubsidio to set
     */
    public void setSolicitudLiquidacionSubsidio(BigInteger solicitudLiquidacionSubsidio) {
        this.solicitudLiquidacionSubsidio = solicitudLiquidacionSubsidio;
    }

    /**
     * @return the periodoLiquidado
     */
    public Date getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    /**
     * @param periodoLiquidado the periodoLiquidado to set
     */
    public void setPeriodoLiquidado(Date periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    /**
     * @return the tipoCuotaSubsidio
     */
    public String getTipoCuotaSubsidio() {
        return tipoCuotaSubsidio;
    }

    /**
     * @param tipoCuotaSubsidio the tipoCuotaSubsidio to set
     */
    public void setTipoCuotaSubsidio(String tipoCuotaSubsidio) {
        this.tipoCuotaSubsidio = tipoCuotaSubsidio;
    }

    /**
     * @return the tipoLiquidacion
     */
    public String getTipoLiquidacion() {
        return tipoLiquidacion;
    }

    /**
     * @param tipoLiquidacion the tipoLiquidacion to set
     */
    public void setTipoLiquidacion(String tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the nombreDepartamento
     */
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    /**
     * @param nombreDepartamento the nombreDepartamento to set
     */
    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    /**
     * @return the nombreMunicipio
     */
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    /**
     * @param nombreMunicipio the nombreMunicipio to set
     */
    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    /**
     * @return the valorTotal
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
