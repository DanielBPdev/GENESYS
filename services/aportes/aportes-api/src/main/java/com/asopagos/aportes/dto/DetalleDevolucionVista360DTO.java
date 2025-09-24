package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.ccf.aportes.DevolucionAporte;
import com.asopagos.entidades.ccf.personas.MedioConsignacion;
import com.asopagos.entidades.ccf.personas.MedioDePago;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la iformacion de la devolucion
 * con recuado asociado vista 360 de aportes<br/>
 * <b>Módulo:</b> Asopagos - HU TRA-488,TRA-499,TRA-001,TRA-002,TRA-003 <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class DetalleDevolucionVista360DTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Descuentos por gestión de pago de operador de información. Un valor
     * <code>null</code> en este campo, indica que este descuento no fue
     * aplicado
     */
    private BigDecimal descuentoGestionPagoOI;
    /**
     * Descuentos por gestión financiera. Un valor <code>null</code> en este
     * campo, indica que este descuento no fue aplicado
     */
    private BigDecimal descuentoGestionFinanciera;
    /**
     * Otros descuentos. Un valor <code>null</code> en este campo, indica que
     * este descuento no fue aplicado
     */
    private BigDecimal descuentoOtro;
    /**
     * Tipo de Medio de Pago.
     */
    private TipoMedioDePagoEnum tipoMediopago;
    /**
     * Tipo de cuenta asociada al Medio Transferencia
     */
    private TipoCuentaEnum tipoCuenta;
    /**
     * Numero de Cuenta asociada al Medio Transferencia.
     */
    private String numeroCuenta;
    /**
     * Tipo Identificación del Titular de la Cuenta
     */
    private TipoIdentificacionEnum tipoIdentificacionTitular;
    /**
     * Numero Identificación del Titular de la Cuenta
     */
    private String numeroIdentificacionTitular;
    /**
     * Numero Identificación del Titular de la Cuenta
     */
    private Short digitoVerificacionTitular;
    /**
     * Nombre del Titular de la Cuenta
     */
    private String nombreTitularCuenta;
    /**
     * Identificador del medio de pago
     */
    private Long idMedioPago;

    /**
     * Constructor de la clase
     */
    public DetalleDevolucionVista360DTO(DevolucionAporte d, TipoMedioDePagoEnum tipoPago, Long idMedioPago) {
        this.descuentoGestionPagoOI = d.getDescuentoGestionPagoOI() != null ? d.getDescuentoGestionPagoOI() : null;
        this.descuentoGestionFinanciera = d.getDescuentoGestionFinanciera() != null ? d.getDescuentoGestionFinanciera() : null;
        this.descuentoOtro = d.getDescuentoOtro() != null ? d.getDescuentoOtro() : null;
        this.tipoMediopago = tipoPago;
        this.idMedioPago = idMedioPago;
    }

    /**
     * 
     */
    public DetalleDevolucionVista360DTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Método que retorna el valor de descuentoGestionPagoOI.
     * @return valor de descuentoGestionPagoOI.
     */
    public BigDecimal getDescuentoGestionPagoOI() {
        return descuentoGestionPagoOI;
    }

    /**
     * Método que retorna el valor de descuentoGestionFinanciera.
     * @return valor de descuentoGestionFinanciera.
     */
    public BigDecimal getDescuentoGestionFinanciera() {
        return descuentoGestionFinanciera;
    }

    /**
     * Método que retorna el valor de descuentoOtro.
     * @return valor de descuentoOtro.
     */
    public BigDecimal getDescuentoOtro() {
        return descuentoOtro;
    }

    /**
     * Método que retorna el valor de tipoMediopago.
     * @return valor de tipoMediopago.
     */
    public TipoMedioDePagoEnum getTipoMediopago() {
        return tipoMediopago;
    }

    /**
     * Método que retorna el valor de tipoCuenta.
     * @return valor de tipoCuenta.
     */
    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * Método que retorna el valor de numeroCuenta.
     * @return valor de numeroCuenta.
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionTitular.
     * @return valor de tipoIdentificacionTitular.
     */
    public TipoIdentificacionEnum getTipoIdentificacionTitular() {
        return tipoIdentificacionTitular;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionTitular.
     * @return valor de numeroIdentificacionTitular.
     */
    public String getNumeroIdentificacionTitular() {
        return numeroIdentificacionTitular;
    }

    /**
     * Método que retorna el valor de digitoVerificacionTitular.
     * @return valor de digitoVerificacionTitular.
     */
    public Short getDigitoVerificacionTitular() {
        return digitoVerificacionTitular;
    }

    /**
     * Método que retorna el valor de nombreTitularCuenta.
     * @return valor de nombreTitularCuenta.
     */
    public String getNombreTitularCuenta() {
        return nombreTitularCuenta;
    }

    /**
     * Método encargado de modificar el valor de descuentoGestionPagoOI.
     * @param valor
     *        para modificar descuentoGestionPagoOI.
     */
    public void setDescuentoGestionPagoOI(BigDecimal descuentoGestionPagoOI) {
        this.descuentoGestionPagoOI = descuentoGestionPagoOI;
    }

    /**
     * Método encargado de modificar el valor de descuentoGestionFinanciera.
     * @param valor
     *        para modificar descuentoGestionFinanciera.
     */
    public void setDescuentoGestionFinanciera(BigDecimal descuentoGestionFinanciera) {
        this.descuentoGestionFinanciera = descuentoGestionFinanciera;
    }

    /**
     * Método encargado de modificar el valor de descuentoOtro.
     * @param valor
     *        para modificar descuentoOtro.
     */
    public void setDescuentoOtro(BigDecimal descuentoOtro) {
        this.descuentoOtro = descuentoOtro;
    }

    /**
     * Método encargado de modificar el valor de tipoMediopago.
     * @param valor
     *        para modificar tipoMediopago.
     */
    public void setTipoMediopago(TipoMedioDePagoEnum tipoMediopago) {
        this.tipoMediopago = tipoMediopago;
    }

    /**
     * Método encargado de modificar el valor de tipoCuenta.
     * @param valor
     *        para modificar tipoCuenta.
     */
    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * Método encargado de modificar el valor de numeroCuenta.
     * @param valor
     *        para modificar numeroCuenta.
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionTitular.
     * @param valor
     *        para modificar tipoIdentificacionTitular.
     */
    public void setTipoIdentificacionTitular(TipoIdentificacionEnum tipoIdentificacionTitular) {
        this.tipoIdentificacionTitular = tipoIdentificacionTitular;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionTitular.
     * @param valor
     *        para modificar numeroIdentificacionTitular.
     */
    public void setNumeroIdentificacionTitular(String numeroIdentificacionTitular) {
        this.numeroIdentificacionTitular = numeroIdentificacionTitular;
    }

    /**
     * Método encargado de modificar el valor de digitoVerificacionTitular.
     * @param valor
     *        para modificar digitoVerificacionTitular.
     */
    public void setDigitoVerificacionTitular(Short digitoVerificacionTitular) {
        this.digitoVerificacionTitular = digitoVerificacionTitular;
    }

    /**
     * Método encargado de modificar el valor de nombreTitularCuenta.
     * @param valor
     *        para modificar nombreTitularCuenta.
     */
    public void setNombreTitularCuenta(String nombreTitularCuenta) {
        this.nombreTitularCuenta = nombreTitularCuenta;
    }

    /**
     * @return the idMedioPago
     */
    public Long getIdMedioPago() {
        return idMedioPago;
    }

    /**
     * @param idMedioPago
     *        the idMedioPago to set
     */
    public void setIdMedioPago(Long idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

}
