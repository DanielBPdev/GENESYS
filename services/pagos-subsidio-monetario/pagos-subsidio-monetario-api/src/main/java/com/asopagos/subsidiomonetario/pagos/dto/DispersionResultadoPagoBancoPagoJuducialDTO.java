package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;

/**
 * <b>Descripcion:</b>Clase DTO que contiene la información de los pagos judiciales realizados en la sección pago en bancos de la dispersión masiva de liquidación.<br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResultadoPagoBancoPagoJuducialDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8410431149355865032L;

    /**Nombre del banco donde se realizaron los pagos judiciales*/
    private String nombreBanco;
    
    /**Numero NIT*/
    private String NIT;
    
    /**Total del monto pagado*/
    private BigDecimal totalMonto;
    
    /**Cuenta del banco en que ocurrío el pago judicial*/
    private String cuentaBanco;
    
    /**Tipo de cuenta del banco*/
    private TipoCuentaEnum tipoCuenta;
    
    /**Lista donde se muestra quienes realizaron los pagos judiciales en el banco*/
    private List<ItemResultadoPagoBancoDTO> lstConsignaciones;

    
    
    /**
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @param nombreBanco the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @return the nIT
     */
    public String getNIT() {
        return NIT;
    }

    /**
     * @param nIT the nIT to set
     */
    public void setNIT(String nIT) {
        NIT = nIT;
    }

    /**
     * @return the totalMonto
     */
    public BigDecimal getTotalMonto() {
        return totalMonto;
    }

    /**
     * @param totalMonto the totalMonto to set
     */
    public void setTotalMonto(BigDecimal totalMonto) {
        this.totalMonto = totalMonto;
    }

    /**
     * @return the cuentaBanco
     */
    public String getCuentaBanco() {
        return cuentaBanco;
    }

    /**
     * @param cuentaBanco the cuentaBanco to set
     */
    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    /**
     * @return the tipoCuenta
     */
    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta the tipoCuenta to set
     */
    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @return the lstCosignaciones
     */
    public List<ItemResultadoPagoBancoDTO> getLstConsignaciones() {
        return lstConsignaciones;
    }

    /**
     * @param lstCosignaciones the lstCosignaciones to set
     */
    public void setLstConsignaciones(List<ItemResultadoPagoBancoDTO> lstConsignaciones) {
        this.lstConsignaciones = lstConsignaciones;
    } 
    
    
}
