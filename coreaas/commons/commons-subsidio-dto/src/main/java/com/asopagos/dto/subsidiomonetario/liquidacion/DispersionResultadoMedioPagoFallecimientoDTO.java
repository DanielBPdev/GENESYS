package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información resultado de los pagos para un determinado medio en un subsidio de
 * fallecimiento
 * <b>Módulo:</b> Asopagos - HU-317-508 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DispersionResultadoMedioPagoFallecimientoDTO implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de condición del administrador de subsidio
     */
    private Long identificadorCondicionAdministrador;

    /**
     * Lista de identificadores de condiciones para las personas relacionadas en la consulta detallada
     */
    private List<Long> identificadoresCondiciones;

    /**
     * Tipo de identificación del administrador de subsidio
     */
    private TipoIdentificacionEnum tipoIdentificacionAdministrador;

    /**
     * Número de identificación del administrador de subsidio
     */
    private String numeroIdentificacionAdministrador;

    /**
     * Nombre del administrador de subsidio
     */
    private String nombreAdministrador;

    /**
     * Número de tarjeta para cuando el medio de pago relacionado es tarjeta
     */
    private String numeroTarjeta;

    /**
     * Estado tarjeta para cuando el medio de pago relacionado es tarjeta
     */
    private EstadoTarjetaMultiserviciosEnum estadoTarjeta;

    /**
     * Nombre del banco asociado a la dispersión
     */
    private String banco;

    /**
     * Tipo de cuenta bancaria (ahorros o corriente)
     */
    private TipoCuentaEnum tipoCuenta;

    /**
     * Numero de la cuenta bancaria
     */
    private String numeroCuenta;

    /**
     * Titular de la cuenta bancaria
     */
    private String titularCuenta;

    /**
     * Tipo de identificación de la entidad de descuento
     */
    private TipoIdentificacionEnum tipoIdentificacionEntidadDescuento;

    /**
     * Número de identificación de la entidad de descuento
     */
    private String numeroIdentificacionEntidadDescuento;

    /**
     * Nombre de la entidad de descuento
     */
    private String nombreEntidadDescuento;

    /**
     * Lista de detalles de las dispersiones
     */
    private List<ItemDispersionResultadoMedioPagoFallecimientoDTO> itemsDetalle;

    /**
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO clone() throws CloneNotSupportedException {
        return (DispersionResultadoMedioPagoFallecimientoDTO) super.clone();
    }
    
    /**
     * @return the identificadorCondicionAdministrador
     */
    public Long getIdentificadorCondicionAdministrador() {
        return identificadorCondicionAdministrador;
    }

    /**
     * @param identificadorCondicionAdministrador
     *        the identificadorCondicionAdministrador to set
     */
    public void setIdentificadorCondicionAdministrador(Long identificadorCondicionAdministrador) {
        this.identificadorCondicionAdministrador = identificadorCondicionAdministrador;
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

    /**
     * @return the tipoIdentificacionAdministrador
     */
    public TipoIdentificacionEnum getTipoIdentificacionAdministrador() {
        return tipoIdentificacionAdministrador;
    }

    /**
     * @param tipoIdentificacionAdministrador
     *        the tipoIdentificacionAdministrador to set
     */
    public void setTipoIdentificacionAdministrador(TipoIdentificacionEnum tipoIdentificacionAdministrador) {
        this.tipoIdentificacionAdministrador = tipoIdentificacionAdministrador;
    }

    /**
     * @return the numeroIdentificacionAdministrador
     */
    public String getNumeroIdentificacionAdministrador() {
        return numeroIdentificacionAdministrador;
    }

    /**
     * @param numeroIdentificacionAdministrador
     *        the numeroIdentificacionAdministrador to set
     */
    public void setNumeroIdentificacionAdministrador(String numeroIdentificacionAdministrador) {
        this.numeroIdentificacionAdministrador = numeroIdentificacionAdministrador;
    }

    /**
     * @return the nombreAdministrador
     */
    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    /**
     * @param nombreAdministrador
     *        the nombreAdministrador to set
     */
    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta
     *        the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the estadoTarjeta
     */
    public EstadoTarjetaMultiserviciosEnum getEstadoTarjeta() {
        return estadoTarjeta;
    }

    /**
     * @param estadoTarjeta
     *        the estadoTarjeta to set
     */
    public void setEstadoTarjeta(EstadoTarjetaMultiserviciosEnum estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @param banco
     *        the banco to set
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * @return the tipoCuenta
     */
    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta
     *        the tipoCuenta to set
     */
    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta
     *        the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the titularCuenta
     */
    public String getTitularCuenta() {
        return titularCuenta;
    }

    /**
     * @param titularCuenta
     *        the titularCuenta to set
     */
    public void setTitularCuenta(String titularCuenta) {
        this.titularCuenta = titularCuenta;
    }

    /**
     * @return the tipoIdentificacionEntidadDescuento
     */
    public TipoIdentificacionEnum getTipoIdentificacionEntidadDescuento() {
        return tipoIdentificacionEntidadDescuento;
    }

    /**
     * @param tipoIdentificacionEntidadDescuento
     *        the tipoIdentificacionEntidadDescuento to set
     */
    public void setTipoIdentificacionEntidadDescuento(TipoIdentificacionEnum tipoIdentificacionEntidadDescuento) {
        this.tipoIdentificacionEntidadDescuento = tipoIdentificacionEntidadDescuento;
    }

    /**
     * @return the numeroIdentificacionEntidadDescuento
     */
    public String getNumeroIdentificacionEntidadDescuento() {
        return numeroIdentificacionEntidadDescuento;
    }

    /**
     * @param numeroIdentificacionEntidadDescuento
     *        the numeroIdentificacionEntidadDescuento to set
     */
    public void setNumeroIdentificacionEntidadDescuento(String numeroIdentificacionEntidadDescuento) {
        this.numeroIdentificacionEntidadDescuento = numeroIdentificacionEntidadDescuento;
    }

    /**
     * @return the nombreEntidadDescuento
     */
    public String getNombreEntidadDescuento() {
        return nombreEntidadDescuento;
    }

    /**
     * @param nombreEntidadDescuento
     *        the nombreEntidadDescuento to set
     */
    public void setNombreEntidadDescuento(String nombreEntidadDescuento) {
        this.nombreEntidadDescuento = nombreEntidadDescuento;
    }

    /**
     * @return the itemsDetalle
     */
    public List<ItemDispersionResultadoMedioPagoFallecimientoDTO> getItemsDetalle() {
        return itemsDetalle;
    }

    /**
     * @param itemsDetalle
     *        the itemsDetalle to set
     */
    public void setItemsDetalle(List<ItemDispersionResultadoMedioPagoFallecimientoDTO> itemsDetalle) {
        this.itemsDetalle = itemsDetalle;
    }

}
