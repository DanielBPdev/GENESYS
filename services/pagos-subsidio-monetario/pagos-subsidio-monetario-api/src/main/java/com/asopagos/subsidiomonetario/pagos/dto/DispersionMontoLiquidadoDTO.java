package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de resultante a la dispersión del monto liquidado<br/>
 * <b>Módulo:</b> Asopagos - HU-311-441 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionMontoLiquidadoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3575506460135592833L;

    /**
     * Variable que contiene el valor del monto total a dispersar antes de
     * tener descuentos. Esta variable debe contener el mismo valor del monto liquidado
     */
    private BigDecimal montoTotalDispersion;

    /**
     * Variable que contiene el valor del total descuentos aplicados, que es
     * el monto total para pago a entidades de descuento
     */
    private BigDecimal totalDescuentosAplicados;

    /**
     * Variable que contiene el valor de la diferencia entre el monto total
     * a dispersar y el total de descuentos aplicados
     */
    private BigDecimal montoTotalLiquidar;

    /**
     * Variable que contiene el numero total de beneficiarios que tienen en el
     * registro 'resultado' igual a 'asignar derecho' en el proceso de liquidación
     */
    private Long cantidadCuotasDispersar;

    /**
     * Variable que contiene el total de administradores de subsidio
     * por cada medio de pago
     */
    private Long cantidadAdministradorSubsidios;

    /** Variable que contiene el resumen de la dispersión del pago por tarjeta */
    private DispersionResumenPagoTarjetaDTO resumenPagoTarjeta;

    /** Variable que contiene el resumen de la dispersión del pago por efectivo */
    private DispersionResumenPagoEfectivoDTO resumenPagoEfectivo;

    /** Lista que contiene cada resumen de la dispersión del pago por banco */
    private List<DispersionResumenPagoBancoDTO> resumenPagoBanco;

    /** Lista que contiene cada resumen de la dispersión del pago a las entidades de descuento */
    private List<DispersionResumenEntidadDescuentoDTO> resumenPagoEntidadDescuento;

    /** Variable que contiene el resumen del total de la dispersión */
    private DispersionResumenTotalDTO resumenTotal;

    /**
     * Metodo que obtiene el valor del monto total a dispersar.
     * @return el valor del monto total a dispersar.
     */
    public BigDecimal getMontoTotalDispersion() {
        return montoTotalDispersion;
    }

    /**
     * Metodo que permite cambiar el valor del monto total a dispersar.
     * @param montoTotalDispersion
     *        Variable que contiene el nuevo valor del monto total a dispersar.
     */
    public void setMontoTotalDispersion(BigDecimal montoTotalDispersion) {
        this.montoTotalDispersion = montoTotalDispersion;
    }

    /**
     * Metodo que obtiene el valor del monto total
     * @return the totalDescuentosAplicados
     */
    public BigDecimal getTotalDescuentosAplicados() {
        return totalDescuentosAplicados;
    }

    /**
     * Metodo que permite cambiar el valor del total de descuentos aplicados
     * @param totalDescuentosAplicados
     *        Variable que contiene el nuevo valor del total de descuentos aplicados
     */
    public void setTotalDescuentosAplicados(BigDecimal totalDescuentosAplicados) {
        this.totalDescuentosAplicados = totalDescuentosAplicados;
    }

    /**
     * Metodo que obtiene el valor del monto total
     * @return the montoTotalLiquidar
     */
    public BigDecimal getMontoTotalLiquidar() {
        return montoTotalLiquidar;
    }

    /**
     * Metodo que permite cambiar el valor del monto total a liquidar.
     * @param montoTotalLiquidar
     *        Variable que contiene el nuevo valor del monto total a liquidar
     */
    public void setMontoTotalLiquidar(BigDecimal montoTotalLiquidar) {
        this.montoTotalLiquidar = montoTotalLiquidar;
    }

    /**
     * Metodo que obtiene el valor del monto total
     * @return the cantidadCuotasDispersar
     */
    public Long getCantidadCuotasDispersar() {
        return cantidadCuotasDispersar;
    }

    /**
     * Metodo que permite cambiar el valor de la cantidad de cuotas a dispersar .
     * @param cantidadCuotasDispersar
     *        Variable que contiene el nuevo valor de la cantidad de cuotas a dispersar
     */
    public void setCantidadCuotasDispersar(Long cantidadCuotasDispersar) {
        this.cantidadCuotasDispersar = cantidadCuotasDispersar;
    }

    /**
     * Metodo que obtiene el valor del la cantida de administradores de subsidio.
     * @return el valor de la cantidad de administrador de subsidios
     */
    public Long getCantidadAdministradorSubsidios() {
        return cantidadAdministradorSubsidios;
    }

    /**
     * Metodo que permite cambiar el valor de la cantidad de administradores de subsidios.
     * @param cantidadAdministradorSubsidios
     *        Variable que contiene el nuevo valor de la cantidad de administrador de subsidios
     */
    public void setCantidadAdministradorSubsidios(Long cantidadAdministradorSubsidios) {
        this.cantidadAdministradorSubsidios = cantidadAdministradorSubsidios;
    }

    /**
     * Metodo que obtiene el valor del resumen del pago por tarjeta
     * @return valor del resumen del pago por tarjeta
     */
    public DispersionResumenPagoTarjetaDTO getResumenPagoTarjeta() {
        return resumenPagoTarjeta;
    }

    /**
     * Metodo que permite cambiar el valor del resumen del resumen del pago por tarjeta
     * @param resumenPagoTarjeta
     *        the resumenPagoTarjeta to set
     */
    public void setResumenPagoTarjeta(DispersionResumenPagoTarjetaDTO resumenPagoTarjeta) {
        this.resumenPagoTarjeta = resumenPagoTarjeta;
    }

    /**
     * Metodo que obtiene el valor del resumen del pago por efectivo
     * @return valor que contiene el resumen del pago por efectivo
     */
    public DispersionResumenPagoEfectivoDTO getResumenPagoEfectivo() {
        return resumenPagoEfectivo;
    }

    /**
     * Metodo que permite cambiar el valor del resumen del pago por efectivo
     * @param resumenPagoEfectivo
     *        the resumenPagoEfectivo to set
     */
    public void setResumenPagoEfectivo(DispersionResumenPagoEfectivoDTO resumenPagoEfectivo) {
        this.resumenPagoEfectivo = resumenPagoEfectivo;
    }

    /**
     * Metodo que obtiene el valor de la lista de resumenes del pago por banco
     * @return valor de la lista de resumenes del pago por banco
     */
    public List<DispersionResumenPagoBancoDTO> getResumenPagoBanco() {
        return resumenPagoBanco;
    }

    /**
     * Metodo que permite cambiar el valor de la lista de resumenes del pago por banco
     * @param resumenPagoBanco
     *        the resumenPagoBanco to set
     */
    public void setResumenPagoBanco(List<DispersionResumenPagoBancoDTO> resumenPagoBanco) {
        this.resumenPagoBanco = resumenPagoBanco;
    }

    /**
     * Metodo que obtiene el valor de la lista de resumenes del pago por entidades de descuento
     * @return el valor de la lista de resumenes de la entidad de descuento
     */
    public List<DispersionResumenEntidadDescuentoDTO> getResumenPagoEntidadDescuento() {
        return resumenPagoEntidadDescuento;
    }

    /**
     * Metodo que permite cambiar el valor de la lista de resumenes del pago por entidades de descuento
     * @param resumenPagoEntidadDescuento
     *        the resumenPagoEntidadDescuento to set
     */
    public void setResumenPagoEntidadDescuento(List<DispersionResumenEntidadDescuentoDTO> resumenPagoEntidadDescuento) {
        this.resumenPagoEntidadDescuento = resumenPagoEntidadDescuento;
    }

    /**
     * Metodo que obtiene el valor del resumen total de pagos por dispersión
     * @return the resumenTotal
     */
    public DispersionResumenTotalDTO getResumenTotal() {
        return resumenTotal;
    }

    /**
     * Metodo que permite cambiar el valor del resumen del total de pagos por dispersión
     * @param resumenTotal
     *        the resumenTotal to set
     */
    public void setResumenTotal(DispersionResumenTotalDTO resumenTotal) {
        this.resumenTotal = resumenTotal;
    }

}
