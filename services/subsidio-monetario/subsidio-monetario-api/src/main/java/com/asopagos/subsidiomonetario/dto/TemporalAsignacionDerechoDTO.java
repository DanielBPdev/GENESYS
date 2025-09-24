package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.subsidiomonetario.EstadoDerechoSubsidioEnum;

/**
 * DTO temporal para la generación de la información temporal relacionada a un subsidio monetario
 * @author rlopez
 *
 */
public class TemporalAsignacionDerechoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoIdentificacionBeneficiario;

    private String numeroIdentificacionBeneficiario;

    private String tipoIdentificacionEmpleador;

    private String numeroIdentificacionEmpleador;

    private String tipoIdentificacionTrabajador;

    private String numeroIdentificacionTrabajador;

    private String resultadoCausal;

    private String estadoDerecho;

    private BigDecimal valorCuota;

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
     * @return the resultadoCausal
     */
    public String getResultadoCausal() {
        return resultadoCausal;
    }

    /**
     * @param resultadoCausal
     *        the resultadoCausal to set
     */
    public void setResultadoCausal(String resultadoCausal) {
        this.resultadoCausal = resultadoCausal;
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
     * @return the estadoDerecho
     */
    public String getEstadoDerecho() {
        return estadoDerecho;
    }

    /**
     * @param estadoDerecho
     *        the estadoDerecho to set
     */
    public void setEstadoDerecho(String estadoDerecho) {
        this.estadoDerecho = estadoDerecho;
    }

}
