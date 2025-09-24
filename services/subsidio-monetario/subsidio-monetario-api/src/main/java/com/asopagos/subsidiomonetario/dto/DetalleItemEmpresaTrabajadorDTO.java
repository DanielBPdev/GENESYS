package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado para cada item de las consultas de la segunda sección de la liquidación masiva<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-436<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DetalleItemEmpresaTrabajadorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tipo de identificación del empleador */
    private TipoIdentificacionEnum tipoIdEmpleador;

    /** Número de identificación del empleador */
    private String idEmpleador;

    /** Nombre del empleador */
    private String nombreEmpleador;

    /** Número consecutivo para los items relacionados con cada trabajador */
    private Integer numeral;

    /** Tipo de identificación del trabajador */
    private TipoIdentificacionEnum tipoIdTrabajador;

    /** Número de identificación del trabajador */
    private String idTrabajador;

    /** Nombre del trabajador */
    private String nombreTrabajador;

    /** Tipo de identificación del beneficiario */
    private TipoIdentificacionEnum tipoIdBeneficiario;

    /** Número de identificación del beneficiario */
    private String idBeneficiario;

    /** Nombre del beneficiario */
    private String nombreBeneficiario;

    /** Valor de la cuota asignada */
    private BigDecimal valorCuota;

    /** Periodo de liquidación */
    private Date periodoLiquidado;

    /** Año en el que se encuentra el beneficio */
    private Integer anioBeneficio;

    /** Código de la entidad de descuento */
    private String codigoEntidadDescuento;

    /** Nombre de la entidad de descuento */
    private String nombreEntidadDescuento;

    /**
     * @return the tipoIdEmpleador
     */
    public TipoIdentificacionEnum getTipoIdEmpleador() {
        return tipoIdEmpleador;
    }

    /**
     * @param tipoIdEmpleador
     *        the tipoIdEmpleador to set
     */
    public void setTipoIdEmpleador(TipoIdentificacionEnum tipoIdEmpleador) {
        this.tipoIdEmpleador = tipoIdEmpleador;
    }

    /**
     * @return the nombreEmpleador
     */
    public String getNombreEmpleador() {
        return nombreEmpleador;
    }

    /**
     * @param nombreEmpleador
     *        the nombreEmpleador to set
     */
    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    /**
     * @return the tipoIdTrabajador
     */
    public TipoIdentificacionEnum getTipoIdTrabajador() {
        return tipoIdTrabajador;
    }

    /**
     * @param tipoIdTrabajador
     *        the tipoIdTrabajador to set
     */
    public void setTipoIdTrabajador(TipoIdentificacionEnum tipoIdTrabajador) {
        this.tipoIdTrabajador = tipoIdTrabajador;
    }

    /**
     * @return the nombreTrabajador
     */
    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    /**
     * @param nombreTrabajador
     *        the nombreTrabajador to set
     */
    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    /**
     * @return the tipoIdBeneficiario
     */
    public TipoIdentificacionEnum getTipoIdBeneficiario() {
        return tipoIdBeneficiario;
    }

    /**
     * @param tipoIdBeneficiario
     *        the tipoIdBeneficiario to set
     */
    public void setTipoIdBeneficiario(TipoIdentificacionEnum tipoIdBeneficiario) {
        this.tipoIdBeneficiario = tipoIdBeneficiario;
    }

    /**
     * @return the nombreBeneficiario
     */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /**
     * @param nombreBeneficiario
     *        the nombreBeneficiario to set
     */
    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /**
     * @return the valorCuota
     */
    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    /**
     * @param valorCuota
     *        the valorCuota to set
     */
    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    /**
     * @return the periodoLiquidado
     */
    public Date getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    /**
     * @param periodoLiquidado
     *        the periodoLiquidado to set
     */
    public void setPeriodoLiquidado(Date periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    /**
     * @return the anioBeneficio
     */
    public Integer getAnioBeneficio() {
        return anioBeneficio;
    }

    /**
     * @param anioBeneficio
     *        the anioBeneficio to set
     */
    public void setAnioBeneficio(Integer anioBeneficio) {
        this.anioBeneficio = anioBeneficio;
    }

    /**
     * @return the codigoEntidadDescuento
     */
    public String getCodigoEntidadDescuento() {
        return codigoEntidadDescuento;
    }

    /**
     * @param codigoEntidadDescuento
     *        the codigoEntidadDescuento to set
     */
    public void setCodigoEntidadDescuento(String codigoEntidadDescuento) {
        this.codigoEntidadDescuento = codigoEntidadDescuento;
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
     * @return the numeral
     */
    public Integer getNumeral() {
        return numeral;
    }

    /**
     * @param numeral
     *        the numeral to set
     */
    public void setNumeral(Integer numeral) {
        this.numeral = numeral;
    }

    /**
     * @return the idEmpleador
     */
    public String getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(String idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idTrabajador
     */
    public String getIdTrabajador() {
        return idTrabajador;
    }

    /**
     * @param idTrabajador the idTrabajador to set
     */
    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    /**
     * @return the idBeneficiario
     */
    public String getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario the idBeneficiario to set
     */
    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

}
