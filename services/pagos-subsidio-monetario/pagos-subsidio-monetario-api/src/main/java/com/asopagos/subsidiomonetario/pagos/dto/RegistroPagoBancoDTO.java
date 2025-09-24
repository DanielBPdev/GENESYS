package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de un registro para el
 * archivo de consignaciones o pagos judiciales<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-441<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class RegistroPagoBancoDTO implements Serializable {

    private static final long serialVersionUID = 295799647936159466L;

    /***
     * Código de la empresa asociado al pago
     */
    private String codigoEmpresa;

    /**
     * Número de la cuenta a debitar
     */
    private String numeroCuentaSudameris;

    /**
     * Tipo de cuenta a debitar
     */
    private String tipoCuentaSudameris;

    /**
     * Nombre de la empresa asociada al pago
     */
    private String nombreEmpresa;

    /**
     * Descripción general del registro asociado al pago
     */
    private String descripcionGeneral;

    /**
     * Código del banco receptor
     */
    private String codigoBancoReceptor;

    /***
     * Tipo de cuenta del banco receptor
     */
    private String tipoCuentaReceptora;

    /**
     * Número de cuenta del banco receptor
     */
    private String numeroCuentaReceptora;

    /**
     * Número de identificación del administrador de subsidio asociado al pago
     */
    private String numeroIdentificacion;

    /**
     * Nombre del administrador de subsidio asociado al pago
     */
    private String nombreDestinatario;

    /**
     * Descripción del pago
     */
    private String descripcionPago;

    /**
     * Valor total asociado al pago
     */
    private BigDecimal valorPago;

    /**
     * @return the codigoEmpresa
     */
    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    /**
     * @param codigoEmpresa
     *        the codigoEmpresa to set
     */
    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    /**
     * @return the numeroCuentaSudameris
     */
    public String getNumeroCuentaSudameris() {
        return numeroCuentaSudameris;
    }

    /**
     * @param numeroCuentaSudameris
     *        the numeroCuentaSudameris to set
     */
    public void setNumeroCuentaSudameris(String numeroCuentaSudameris) {
        this.numeroCuentaSudameris = numeroCuentaSudameris;
    }

    /**
     * @return the tipoCuentaSudameris
     */
    public String getTipoCuentaSudameris() {
        return tipoCuentaSudameris;
    }

    /**
     * @param tipoCuentaSudameris
     *        the tipoCuentaSudameris to set
     */
    public void setTipoCuentaSudameris(String tipoCuentaSudameris) {
        this.tipoCuentaSudameris = tipoCuentaSudameris;
    }

    /**
     * @return the nombreEmpresa
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * @param nombreEmpresa
     *        the nombreEmpresa to set
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * @return the descripcionGeneral
     */
    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    /**
     * @param descripcionGeneral
     *        the descripcionGeneral to set
     */
    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }

    /**
     * @return the codigoBancoReceptor
     */
    public String getCodigoBancoReceptor() {
        return codigoBancoReceptor;
    }

    /**
     * @param codigoBancoReceptor
     *        the codigoBancoReceptor to set
     */
    public void setCodigoBancoReceptor(String codigoBancoReceptor) {
        this.codigoBancoReceptor = codigoBancoReceptor;
    }

    /**
     * @return the tipoCuentaReceptora
     */
    public String getTipoCuentaReceptora() {
        return tipoCuentaReceptora;
    }

    /**
     * @param tipoCuentaReceptora
     *        the tipoCuentaReceptora to set
     */
    public void setTipoCuentaReceptora(String tipoCuentaReceptora) {
        this.tipoCuentaReceptora = tipoCuentaReceptora;
    }

    /**
     * @return the numeroCuentaReceptora
     */
    public String getNumeroCuentaReceptora() {
        return numeroCuentaReceptora;
    }

    /**
     * @param numeroCuentaReceptora
     *        the numeroCuentaReceptora to set
     */
    public void setNumeroCuentaReceptora(String numeroCuentaReceptora) {
        this.numeroCuentaReceptora = numeroCuentaReceptora;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombreDestinatario
     */
    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    /**
     * @param nombreDestinatario
     *        the nombreDestinatario to set
     */
    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    /**
     * @return the descripcionPago
     */
    public String getDescripcionPago() {
        return descripcionPago;
    }

    /**
     * @param descripcionPago
     *        the descripcionPago to set
     */
    public void setDescripcionPago(String descripcionPago) {
        this.descripcionPago = descripcionPago;
    }

    /**
     * @return the valorPago
     */
    public BigDecimal getValorPago() {
        return valorPago;
    }

    /**
     * @param valorPago
     *        the valorPago to set
     */
    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

}
