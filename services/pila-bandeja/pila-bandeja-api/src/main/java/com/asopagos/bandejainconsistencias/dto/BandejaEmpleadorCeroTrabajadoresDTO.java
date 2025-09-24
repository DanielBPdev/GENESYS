/**
 * 
 */
package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */
public class BandejaEmpleadorCeroTrabajadoresDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEmpleador;
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private Short digitoVerificacion;
    private String razonSocial;
    private Date fechaAfiliacion;
    private Date fechaIngresoBandeja;
    private Short cantIngresoBandeja;
    private String estadoMorosidad;
    private String ultimoPeriodoPagado;
    private String fechaUltimoPeriodoPagado;
    private Boolean tieneReportadoRetiroTotalTrabajadores;
    private Boolean tieneHistoricoAportes;
    private Boolean tieneHistoricoAfiliaciones;
    private Boolean esGestionado;
    private Long mantenerAfiliacionPor;

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador
     *        the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion
     *        the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *        the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the fechaAfiliacion
     */
    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    /**
     * @param fechaAfiliacion
     *        the fechaAfiliacion to set
     */
    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    /**
     * @return the fechaIngresobandeja
     */
    public Date getFechaIngresoBandeja() {
        return fechaIngresoBandeja;
    }

    /**
     * @param fechaIngresobandeja
     *        the fechaIngresobandeja to set
     */
    public void setFechaIngresoBandeja(Date fechaIngresoBandeja) {
        this.fechaIngresoBandeja = fechaIngresoBandeja;
    }

    /**
     * @return the cantIngresoBandeja
     */
    public Short getCantIngresoBandeja() {
        return cantIngresoBandeja;
    }

    /**
     * @param cantIngresoBandeja
     *        the cantIngresoBandeja to set
     */
    public void setCantIngresoBandeja(Short cantIngresoBandeja) {
        this.cantIngresoBandeja = cantIngresoBandeja;
    }

    /**
     * @return the estadoMorosidad
     */
    public String getEstadoMorosidad() {
        return estadoMorosidad;
    }

    /**
     * @param estadoMorosidad
     *        the estadoMorosidad to set
     */
    public void setEstadoMorosidad(String estadoMorosidad) {
        this.estadoMorosidad = estadoMorosidad;
    }

    /**
     * @return the ultimoPeriodoPagado
     */
    public String getUltimoPeriodoPagado() {
        return ultimoPeriodoPagado;
    }

    /**
     * @param ultimoPeriodoPagado
     *        the ultimoPeriodoPagado to set
     */
    public void setUltimoPeriodoPagado(String ultimoPeriodoPagado) {
        this.ultimoPeriodoPagado = ultimoPeriodoPagado;
    }

    /**
     * @return the fechaUltimoPeriodoPagado
     */
    public String getFechaUltimoPeriodoPagado() {
        return fechaUltimoPeriodoPagado;
    }

    /**
     * @param fechaUltimoPeriodoPagado
     *        the fechaUltimoPeriodoPagado to set
     */
    public void setFechaUltimoPeriodoPagado(String fechaUltimoPeriodoPagado) {
        this.fechaUltimoPeriodoPagado = fechaUltimoPeriodoPagado;
    }

    /**
     * @return the tieneReportadoRetiroTotalTrabajadores
     */
    public Boolean getTieneReportadoRetiroTotalTrabajadores() {
        return tieneReportadoRetiroTotalTrabajadores;
    }

    /**
     * @param tieneReportadoRetiroTotalTrabajadores
     *        the tieneReportadoRetiroTotalTrabajadores to set
     */
    public void setTieneReportadoRetiroTotalTrabajadores(Boolean tieneReportadoRetiroTotalTrabajadores) {
        this.tieneReportadoRetiroTotalTrabajadores = tieneReportadoRetiroTotalTrabajadores;
    }

    /**
     * @return the tieneHistoricoAportes
     */
    public Boolean getTieneHistoricoAportes() {
        return tieneHistoricoAportes;
    }

    /**
     * @param tieneHistoricoAportes
     *        the tieneHistoricoAportes to set
     */
    public void setTieneHistoricoAportes(Boolean tieneHistoricoAportes) {
        this.tieneHistoricoAportes = tieneHistoricoAportes;
    }

    /**
     * @return the tieneHistoricoAfiliaciones
     */
    public Boolean getTieneHistoricoAfiliaciones() {
        return tieneHistoricoAfiliaciones;
    }

    /**
     * @param tieneHistoricoAfiliaciones
     *        the tieneHistoricoAfiliaciones to set
     */
    public void setTieneHistoricoAfiliaciones(Boolean tieneHistoricoAfiliaciones) {
        this.tieneHistoricoAfiliaciones = tieneHistoricoAfiliaciones;
    }

    /**
     * @return the esGestionado
     */
    public Boolean getEsGestionado() {
        return esGestionado;
    }

    /**
     * @param esGestionado
     *        the esGestionado to set
     */
    public void setEsGestionado(Boolean esGestionado) {
        this.esGestionado = esGestionado;
    }

    /**
     * @return the mantenerAfiliacionPor
     */
    public Long getMantenerAfiliacionPor() {
        return mantenerAfiliacionPor;
    }

    /**
     * @param mantenerAfiliacionPor
     *        the mantenerAfiliacionPor to set
     */
    public void setMantenerAfiliacionPor(Long mantenerAfiliacionPor) {
        this.mantenerAfiliacionPor = mantenerAfiliacionPor;
    }

}
