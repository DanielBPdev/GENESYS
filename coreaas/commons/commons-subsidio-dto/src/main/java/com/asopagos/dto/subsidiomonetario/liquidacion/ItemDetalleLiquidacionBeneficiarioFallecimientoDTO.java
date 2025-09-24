package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información detallada por item de las cuotas asignadas a los beneficiarios
 * <b>Módulo:</b> Asopagos - HU-317-517 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ItemDetalleLiquidacionBeneficiarioFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Periodo de la cuota de fallecimiento
     */
    private Date periodo;

    /**
     * Valor total de la cuota liquidada por subsidio de fallecimiento
     */
    private BigDecimal valorCuotaAjustada;

    /**
     * Valor total de la cuota liquidada por subsidio de fallecimiento
     */
    private BigDecimal montoCuotaLiquidada;

    /**
     * Descuentos por concepto de pago de subsidio
     */
    private BigDecimal descuentoCuotaMonetaria;

    /**
     * Descuentos por concepto de pignoración de entidades de descuento
     */
    private BigDecimal descuentoEntidades;

    /**
     * Total a pagar (montoCuotaLiquidada - descuentoCuotaMonetaria - descuentoEntidades)
     */
    private BigDecimal totalPagar;

    /**
     * @return the periodo
     */
    public Date getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo
     *        the periodo to set
     */
    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getValorCuotaAjustada() {
        return this.valorCuotaAjustada;
    }

    public void setValorCuotaAjustada(BigDecimal valorCuotaAjustada) {
        this.valorCuotaAjustada = valorCuotaAjustada;
    }

    /**
     * @return the montoCuotaLiquidada
     */
    public BigDecimal getMontoCuotaLiquidada() {
        return montoCuotaLiquidada;
    }

    /**
     * @param montoCuotaLiquidada
     *        the montoCuotaLiquidada to set
     */
    public void setMontoCuotaLiquidada(BigDecimal montoCuotaLiquidada) {
        this.montoCuotaLiquidada = montoCuotaLiquidada;
    }

    /**
     * @return the descuentoCuotaMonetaria
     */
    public BigDecimal getDescuentoCuotaMonetaria() {
        return descuentoCuotaMonetaria;
    }

    /**
     * @param descuentoCuotaMonetaria
     *        the descuentoCuotaMonetaria to set
     */
    public void setDescuentoCuotaMonetaria(BigDecimal descuentoCuotaMonetaria) {
        this.descuentoCuotaMonetaria = descuentoCuotaMonetaria;
    }

    /**
     * @return the descuentoEntidades
     */
    public BigDecimal getDescuentoEntidades() {
        return descuentoEntidades;
    }

    /**
     * @param descuentoEntidades
     *        the descuentoEntidades to set
     */
    public void setDescuentoEntidades(BigDecimal descuentoEntidades) {
        this.descuentoEntidades = descuentoEntidades;
    }

    /**
     * @return the totalPagar
     */
    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    /**
     * @param totalPagar
     *        the totalPagar to set
     */
    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

}
