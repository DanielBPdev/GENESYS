package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resultado para la dispersión de la liquidación por fallecimiento<br/>
 * <b>Módulo:</b> Asopagos - HU-317-508 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DispersionMontoLiquidadoFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Monto total a dispersar para la liquidación de fallecimiento, antes de descuentos
     */
    private BigDecimal montoTotalDispersion;

    /**
     * Total de descuentos aplicados sobre la liquidación de fallecimiento
     */
    private BigDecimal totalDescuentosAplicados;

    /**
     * Monto total a liquidar para la liquidación de fallecimiento, después de descuentos
     */
    private BigDecimal montoTotalLiquidar;

    /**
     * Cantidad de cuotas a dispersar para la liquidación de fallecimiento
     */
    private Long cantidadCuotasDispersar;

    /**
     * Cantidad de administradores de subsidio relacionados a la liquidación de fallecimiento
     */
    private Long cantidadAdministradorSubsidios;

    /**
     * Indicardor del tipo de desembolso (Una sola cuota o mes por mes)
     */
    private ModoDesembolsoEnum tipoDesembolso;

    /**
     * Resumen de dispersión al medio de pago tarjeta
     */
    private DispersionResumenMedioPagoFallecimientoDTO resumenPagosTarjeta;

    /**
     * Resumen de dispersión al medio de pago efectivo
     */
    private DispersionResumenMedioPagoFallecimientoDTO resumenPagosEfectivo;

    /**
     * Resumen de dispersión al medio de pago banco por consignación
     */
    private DispersionResumenMedioPagoFallecimientoDTO resumenPagosBancoConsignacion;

    /**
     * Resumen de dispersión al medio de pago banco por pagos judiciales
     */
    private DispersionResumenMedioPagoFallecimientoDTO resumenPagosBancoPagosJudiciales;

    /**
     * Resumen de dispersión para los descuentos realizados en la liquidación de fallecimiento
     */
    private DispersionResumenMedioPagoFallecimientoDTO resumenDescuentos;

    /**
     * Lista de identificadores de condición de las personas relacionadas a la liquidación de fallecimiento
     */
    private List<Long> identificadoresCondiciones;

    /**
     * @return the montoTotalDispersion
     */
    public BigDecimal getMontoTotalDispersion() {
        return montoTotalDispersion;
    }

    /**
     * @param montoTotalDispersion
     *        the montoTotalDispersion to set
     */
    public void setMontoTotalDispersion(BigDecimal montoTotalDispersion) {
        this.montoTotalDispersion = montoTotalDispersion;
    }

    /**
     * @return the totalDescuentosAplicados
     */
    public BigDecimal getTotalDescuentosAplicados() {
        return totalDescuentosAplicados;
    }

    /**
     * @param totalDescuentosAplicados
     *        the totalDescuentosAplicados to set
     */
    public void setTotalDescuentosAplicados(BigDecimal totalDescuentosAplicados) {
        this.totalDescuentosAplicados = totalDescuentosAplicados;
    }

    /**
     * @return the montoTotalLiquidar
     */
    public BigDecimal getMontoTotalLiquidar() {
        return montoTotalLiquidar;
    }

    /**
     * @param montoTotalLiquidar
     *        the montoTotalLiquidar to set
     */
    public void setMontoTotalLiquidar(BigDecimal montoTotalLiquidar) {
        this.montoTotalLiquidar = montoTotalLiquidar;
    }

    /**
     * @return the cantidadCuotasDispersar
     */
    public Long getCantidadCuotasDispersar() {
        return cantidadCuotasDispersar;
    }

    /**
     * @param cantidadCuotasDispersar
     *        the cantidadCuotasDispersar to set
     */
    public void setCantidadCuotasDispersar(Long cantidadCuotasDispersar) {
        this.cantidadCuotasDispersar = cantidadCuotasDispersar;
    }

    /**
     * @return the cantidadAdministradorSubsidios
     */
    public Long getCantidadAdministradorSubsidios() {
        return cantidadAdministradorSubsidios;
    }

    /**
     * @param cantidadAdministradorSubsidios
     *        the cantidadAdministradorSubsidios to set
     */
    public void setCantidadAdministradorSubsidios(Long cantidadAdministradorSubsidios) {
        this.cantidadAdministradorSubsidios = cantidadAdministradorSubsidios;
    }

    /**
     * @return the tipoDesembolso
     */
    public ModoDesembolsoEnum getTipoDesembolso() {
        return tipoDesembolso;
    }

    /**
     * @param tipoDesembolso
     *        the tipoDesembolso to set
     */
    public void setTipoDesembolso(ModoDesembolsoEnum tipoDesembolso) {
        this.tipoDesembolso = tipoDesembolso;
    }

    /**
     * @return the resumenPagosTarjeta
     */
    public DispersionResumenMedioPagoFallecimientoDTO getResumenPagosTarjeta() {
        return resumenPagosTarjeta;
    }

    /**
     * @param resumenPagosTarjeta
     *        the resumenPagosTarjeta to set
     */
    public void setResumenPagosTarjeta(DispersionResumenMedioPagoFallecimientoDTO resumenPagosTarjeta) {
        this.resumenPagosTarjeta = resumenPagosTarjeta;
    }

    /**
     * @return the resumenPagosEfectivo
     */
    public DispersionResumenMedioPagoFallecimientoDTO getResumenPagosEfectivo() {
        return resumenPagosEfectivo;
    }

    /**
     * @param resumenPagosEfectivo
     *        the resumenPagosEfectivo to set
     */
    public void setResumenPagosEfectivo(DispersionResumenMedioPagoFallecimientoDTO resumenPagosEfectivo) {
        this.resumenPagosEfectivo = resumenPagosEfectivo;
    }

    /**
     * @return the resumenPagosBancoConsignacion
     */
    public DispersionResumenMedioPagoFallecimientoDTO getResumenPagosBancoConsignacion() {
        return resumenPagosBancoConsignacion;
    }

    /**
     * @param resumenPagosBancoConsignacion
     *        the resumenPagosBancoConsignacion to set
     */
    public void setResumenPagosBancoConsignacion(DispersionResumenMedioPagoFallecimientoDTO resumenPagosBancoConsignacion) {
        this.resumenPagosBancoConsignacion = resumenPagosBancoConsignacion;
    }

    /**
     * @return the resumenPagosBancoPagosJudiciales
     */
    public DispersionResumenMedioPagoFallecimientoDTO getResumenPagosBancoPagosJudiciales() {
        return resumenPagosBancoPagosJudiciales;
    }

    /**
     * @param resumenPagosBancoPagosJudiciales
     *        the resumenPagosBancoPagosJudiciales to set
     */
    public void setResumenPagosBancoPagosJudiciales(DispersionResumenMedioPagoFallecimientoDTO resumenPagosBancoPagosJudiciales) {
        this.resumenPagosBancoPagosJudiciales = resumenPagosBancoPagosJudiciales;
    }

    /**
     * @return the resumenDescuentos
     */
    public DispersionResumenMedioPagoFallecimientoDTO getResumenDescuentos() {
        return resumenDescuentos;
    }

    /**
     * @param resumenDescuentos
     *        the resumenDescuentos to set
     */
    public void setResumenDescuentos(DispersionResumenMedioPagoFallecimientoDTO resumenDescuentos) {
        this.resumenDescuentos = resumenDescuentos;
    }

    /**
     * @return the identificadoresCondiciones
     */
    public List<Long> getIdentificadoresCondiciones() {
        return identificadoresCondiciones;
    }

    /**
     * @param identificadoresCondiciones
     *        the identificadoresCondiciones to set
     */
    public void setIdentificadoresCondiciones(List<Long> identificadoresCondiciones) {
        this.identificadoresCondiciones = identificadoresCondiciones;
    }

}
