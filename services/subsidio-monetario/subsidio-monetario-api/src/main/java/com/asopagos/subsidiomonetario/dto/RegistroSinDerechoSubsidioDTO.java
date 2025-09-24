package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * <b>Descripcion:</b> DTO que contiene el resultado de un registro para el
 * archivo de personas sin derecho<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-317-266<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class RegistroSinDerechoSubsidioDTO implements Serializable {

    private static final long serialVersionUID = -1098497840065446130L;

    /**
     * Fecha de la liquidación en la que el beneficiario no obtuvo derecho
     */
    private Date fechaLiquidacion;

    /**
     * Tipo de liquidación (Masiva, por ajuste o reconocimiento)
     */
    private String tipoLiquidacion;

    /**
     * Subtipo de liquidación para el caso de liquidación por Ajuste
     */
    private String subtipoLiquidacion;

    /**
     * Tipo de identificación del empleador
     */
    private String tipoIdentificacionEmpleador;

    /**
     * Número de identificación del empleador
     */
    private String numeroIdentificacionEmpleador;

    /**
     * Nombre del empleador
     */
    private String nombreEmpleador;

    /**
     * Clasificación del empleador
     */
    private String ciiu;

    /**
     * Condición agraria asociada al empleador
     */
    private String condicionAgraria;

    /**
     * Sucursal del empleador
     */
    private String codigoSucursal;

    /**
     * Año del beneficio 1429 del empleador
     */
    private String anioBeneficio1429;

    /**
     * Tipo de identificación del trabajador
     */
    private String tipoIdentificacionTrabajador;

    /**
     * Número de identificación del trabajador
     */
    private String numeroIdentificacionTrabajador;

    /**
     * Nombre del trabajador
     */
    private String nombreTrabajador;

    /**
     * Tipo de identificación del beneficiario
     */
    private String tipoIdentificacionBeneficiario;

    /**
     * Número de identificación del beneficiario
     */
    private String numeroIdentificacionBeneficiario;

    /**
     * Nombre del beneficiario
     */
    private String nombreBeneficiario;

    /**
     * Tipo de solicitante de la liquidación
     */
    private String tipoSolicitante;

    /**
     * Clasificación del solicitante de liquidación
     */
    private String clasificacion;

    /**
     * Razones por las cuales no tiene derecho
     */
    private String razonesSinDerecho;

    /**
     * Periodo de liquidación
     */
    private String periodoLiquidado;

    /**
     * Tipo de periodo de la liquidación (Regular - Retroactivo)
     */
    private String tipoPeriodo;

    /**
     * @return the fechaLiquidacion
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *        the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the tipoLiquidacion
     */
    public String getTipoLiquidacion() {
        return tipoLiquidacion;
    }

    /**
     * @param tipoLiquidacion
     *        the tipoLiquidacion to set
     */
    public void setTipoLiquidacion(String tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }

    /**
     * @return the subtipoLiquidacion
     */
    public String getSubtipoLiquidacion() {
        return subtipoLiquidacion;
    }

    /**
     * @param subtipoLiquidacion
     *        the subtipoLiquidacion to set
     */
    public void setSubtipoLiquidacion(String subtipoLiquidacion) {
        this.subtipoLiquidacion = subtipoLiquidacion;
    }

    /**
     * @return the tipoIdentificacionEmpleador
     */
    public String getTipoIdentificacionEmpleador() {
        return tipoIdentificacionEmpleador;
    }

    /**
     * @param tipoIdentificacionEmpleador
     *        the tipoIdentificacionEmpleador to set
     */
    public void setTipoIdentificacionEmpleador(String tipoIdentificacionEmpleador) {
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
    }

    /**
     * @return the numeroIdentificacionEmpleador
     */
    public String getNumeroIdentificacionEmpleador() {
        return numeroIdentificacionEmpleador;
    }

    /**
     * @param numeroIdentificacionEmpleador
     *        the numeroIdentificacionEmpleador to set
     */
    public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
        this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
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
     * @return the ciiu
     */
    public String getCiiu() {
        return ciiu;
    }

    /**
     * @param ciiu
     *        the ciiu to set
     */
    public void setCiiu(String ciiu) {
        this.ciiu = ciiu;
    }

    /**
     * @return the condicionAgraria
     */
    public String getCondicionAgraria() {
        return condicionAgraria;
    }

    /**
     * @param condicionAgraria
     *        the condicionAgraria to set
     */
    public void setCondicionAgraria(String condicionAgraria) {
        this.condicionAgraria = condicionAgraria;
    }

    /**
     * @return the codigoSucursal
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * @param codigoSucursal
     *        the codigoSucursal to set
     */
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * @return the anioBeneficio1429
     */
    public String getAnioBeneficio1429() {
        return anioBeneficio1429;
    }

    /**
     * @param anioBeneficio1429
     *        the anioBeneficio1429 to set
     */
    public void setAnioBeneficio1429(String anioBeneficio1429) {
        this.anioBeneficio1429 = anioBeneficio1429;
    }

    /**
     * @return the tipoIdentificacionTrabajador
     */
    public String getTipoIdentificacionTrabajador() {
        return tipoIdentificacionTrabajador;
    }

    /**
     * @param tipoIdentificacionTrabajador
     *        the tipoIdentificacionTrabajador to set
     */
    public void setTipoIdentificacionTrabajador(String tipoIdentificacionTrabajador) {
        this.tipoIdentificacionTrabajador = tipoIdentificacionTrabajador;
    }

    /**
     * @return the numeroIdentificacionTrabajador
     */
    public String getNumeroIdentificacionTrabajador() {
        return numeroIdentificacionTrabajador;
    }

    /**
     * @param numeroIdentificacionTrabajador
     *        the numeroIdentificacionTrabajador to set
     */
    public void setNumeroIdentificacionTrabajador(String numeroIdentificacionTrabajador) {
        this.numeroIdentificacionTrabajador = numeroIdentificacionTrabajador;
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
     * @return the tipoSolicitante
     */
    public String getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante
     *        the tipoSolicitante to set
     */
    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the clasificacion
     */
    public String getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion
     *        the clasificacion to set
     */
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the razonesSinDerecho
     */
    public String getRazonesSinDerecho() {
        return razonesSinDerecho;
    }

    /**
     * @param razonesSinDerecho
     *        the razonesSinDerecho to set
     */
    public void setRazonesSinDerecho(String razonesSinDerecho) {
        this.razonesSinDerecho = razonesSinDerecho;
    }

    /**
     * @return the periodoLiquidado
     */
    public String getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    /**
     * @param periodoLiquidado
     *        the periodoLiquidado to set
     */
    public void setPeriodoLiquidado(String periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    /**
     * @return the tipoPeriodo
     */
    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * @param tipoPeriodo
     *        the tipoPeriodo to set
     */
    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    /**
     * @return the tipoIdentificacionBeneficiario
     */
    public String getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario
     *        the tipoIdentificacionBeneficiario to set
     */
    public void setTipoIdentificacionBeneficiario(String tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    /**
     * @return the numeroIdentificacionBeneficiario
     */
    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    /**
     * @param numeroIdentificacionBeneficiario
     *        the numeroIdentificacionBeneficiario to set
     */
    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
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

    public String toStringDerecho(String fechaGeneracion, String sep) {
        String stringFechaLiquidacion = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            stringFechaLiquidacion = sdf.format(fechaLiquidacion);
        } catch (Exception e) {
            stringFechaLiquidacion = fechaGeneracion;
        }
        return stringFechaLiquidacion + sep
            + tipoLiquidacion + sep
            + subtipoLiquidacion + sep
            + tipoIdentificacionEmpleador + sep
            + numeroIdentificacionEmpleador + sep
            + nombreEmpleador + sep
            + ciiu + sep
            + condicionAgraria + sep
            + codigoSucursal + sep
            + anioBeneficio1429 + sep
            + tipoIdentificacionTrabajador + sep
            + numeroIdentificacionTrabajador + sep
            + nombreTrabajador + sep
            + tipoIdentificacionBeneficiario + sep
            + numeroIdentificacionBeneficiario + sep
            + nombreBeneficiario + sep
            + tipoSolicitante + sep
            + clasificacion + sep
            + razonesSinDerecho + sep
            + periodoLiquidado + sep
            + tipoPeriodo + sep
            + "\n";
    }

}
