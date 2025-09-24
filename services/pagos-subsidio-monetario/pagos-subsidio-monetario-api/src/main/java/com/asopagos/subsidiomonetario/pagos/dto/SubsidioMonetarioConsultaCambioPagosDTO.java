package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de busqueda para la consulta
 * de la Historia de Usuario<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 219 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */
@XmlRootElement
public class SubsidioMonetarioConsultaCambioPagosDTO implements Serializable {

    private static final long serialVersionUID = 4130606019713993683L;

    /**
     * Identificador del abono (cuenta de administrador del subsidio)
     */
    private Long identificadorTransaccionAbono;

    /**
     * Rango inicial del subsidio monetario a ser anulado por
     * perdida de derecho o para generar informe de retiros
     */
    private Long fechaInicial;

    /**
     * Rango final del subsidio monetario a ser anulado por
     * perdida de derecho o para generar informe de retiros
     */
    private Long fechaFinal;

    /**
     * Tipo de identificación del administrador del subsidio.
     */
    private TipoIdentificacionEnum tipoIdAdmin;

    /**
     * Numero de identificación del administrador del subsidio.
     */
    private String numeroIdAdmin;

    /**
     * Medio de pago por el cual se quiere realizar el filtro
     */
    private TipoMedioDePagoEnum medioDePago;

    /**
     * Número de tarjeta del administrador del subsidio si el medio de pago es por tarjeta
     */
    private String numeroTarjetaAdmin;

    /**
     * Código del banco relacionado del administrador del subsidio si el medio de pago es por transferencia (Banco)
     */
    private String codigoBancoAdmin;

    /**
     * Nombre del banco relacionado con el administrador del subsidio si el medio de pago es por transferencia (Banco)
     */
    private String nombreBancoAdmin;

    /**
     * Tipo de cuenta del administrador del subsidio si el medio de pago es por transferencia (Banco)
     */
    private TipoCuentaEnum tipoCuentaAdminSubsidio;

    /**
     * Número de cuentadel administrador del subsidio si el medio de pago es por transferencia (Banco)
     */
    private String numeroCuentaAdminSubsidio;

    /**
     * Tipo de identificación del titular de la cuenta si el medio de pago es por transferencia (Banco)
     */
    private TipoIdentificacionEnum tipoIdTitularCuenta;

    /**
     * Número de identificación del titular de la cuenta si el medio de pago es por transferencia (Banco)
     */
    private String numeroIdTitularCuenta;

    /**
     * @return the identificadorTransaccionAbono
     */
    public Long getIdentificadorTransaccionAbono() {
        return identificadorTransaccionAbono;
    }

    /**
     * @param identificadorTransaccionAbono
     *        the identificadorTransaccionAbono to set
     */
    public void setIdentificadorTransaccionAbono(Long identificadorTransaccionAbono) {
        this.identificadorTransaccionAbono = identificadorTransaccionAbono;
    }

    /**
     * @return the fechaInicial
     */
    public Long getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial
     *        the fechaInicial to set
     */
    public void setFechaInicial(Long fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Long getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal
     *        the fechaFinal to set
     */
    public void setFechaFinal(Long fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago
     *        the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the numeroTarjetaAdmin
     */
    public String getNumeroTarjetaAdmin() {
        return numeroTarjetaAdmin;
    }

    /**
     * @param numeroTarjetaAdmin
     *        the numeroTarjetaAdmin to set
     */
    public void setNumeroTarjetaAdmin(String numeroTarjetaAdmin) {
        this.numeroTarjetaAdmin = numeroTarjetaAdmin;
    }

    /**
     * @return the codigoBancoAdmin
     */
    public String getCodigoBancoAdmin() {
        return codigoBancoAdmin;
    }

    /**
     * @param codigoBancoAdmin
     *        the codigoBancoAdmin to set
     */
    public void setCodigoBancoAdmin(String codigoBancoAdmin) {
        this.codigoBancoAdmin = codigoBancoAdmin;
    }

    /**
     * @return the nombreBancoAdmin
     */
    public String getNombreBancoAdmin() {
        return nombreBancoAdmin;
    }

    /**
     * @param nombreBancoAdmin
     *        the nombreBancoAdmin to set
     */
    public void setNombreBancoAdmin(String nombreBancoAdmin) {
        this.nombreBancoAdmin = nombreBancoAdmin;
    }

    /**
     * @return the tipoCuentaAdminSubsidio
     */
    public TipoCuentaEnum getTipoCuentaAdminSubsidio() {
        return tipoCuentaAdminSubsidio;
    }

    /**
     * @param tipoCuentaAdminSubsidio
     *        the tipoCuentaAdminSubsidio to set
     */
    public void setTipoCuentaAdminSubsidio(TipoCuentaEnum tipoCuentaAdminSubsidio) {
        this.tipoCuentaAdminSubsidio = tipoCuentaAdminSubsidio;
    }

    /**
     * @return the numeroCuentaAdminSubsidio
     */
    public String getNumeroCuentaAdminSubsidio() {
        return numeroCuentaAdminSubsidio;
    }

    /**
     * @param numeroCuentaAdminSubsidio
     *        the numeroCuentaAdminSubsidio to set
     */
    public void setNumeroCuentaAdminSubsidio(String numeroCuentaAdminSubsidio) {
        this.numeroCuentaAdminSubsidio = numeroCuentaAdminSubsidio;
    }

    /**
     * @return the tipoIdTitularCuenta
     */
    public TipoIdentificacionEnum getTipoIdTitularCuenta() {
        return tipoIdTitularCuenta;
    }

    /**
     * @param tipoIdTitularCuenta
     *        the tipoIdTitularCuenta to set
     */
    public void setTipoIdTitularCuenta(TipoIdentificacionEnum tipoIdTitularCuenta) {
        this.tipoIdTitularCuenta = tipoIdTitularCuenta;
    }

    /**
     * @return the numeroIdTitularCuenta
     */
    public String getNumeroIdTitularCuenta() {
        return numeroIdTitularCuenta;
    }

    /**
     * @param numeroIdTitularCuenta
     *        the numeroIdTitularCuenta to set
     */
    public void setNumeroIdTitularCuenta(String numeroIdTitularCuenta) {
        this.numeroIdTitularCuenta = numeroIdTitularCuenta;
    }

    /**
     * @return the tipoIdAdmin
     */
    public TipoIdentificacionEnum getTipoIdAdmin() {
        return tipoIdAdmin;
    }

    /**
     * @param tipoIdAdmin
     *        the tipoIdAdmin to set
     */
    public void setTipoIdAdmin(TipoIdentificacionEnum tipoIdAdmin) {
        this.tipoIdAdmin = tipoIdAdmin;
    }

    /**
     * @return the numeroIdAdmin
     */
    public String getNumeroIdAdmin() {
        return numeroIdAdmin;
    }

    /**
     * @param numeroIdAdmin
     *        the numeroIdAdmin to set
     */
    public void setNumeroIdAdmin(String numeroIdAdmin) {
        this.numeroIdAdmin = numeroIdAdmin;
    }

}
