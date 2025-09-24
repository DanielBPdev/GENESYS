package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de los items para los medios de pago en el resultado detallado de la dispersión
 * <b>Módulo:</b> Asopagos - HU-317-508 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ItemDispersionResultadoMedioPagoFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de condición del beneficiario
     */
    private Long identificadorCondicion;

    /**
     * Tipo de identificación del beneficiario de subsidio
     */
    private TipoIdentificacionEnum tipoIdentificacionBeneficiario;

    /**
     * Número de identificación del beneficiario de subsidio
     */
    private String numeroIdentificacionBeneficiario;

    /**
     * Nombre del beneficiario de subsidio
     */
    private String nombreBeneficiario;

    /**
     * Lista con los valores a dispersar en los meses programados
     */
    private List<BigDecimal> valoresDispersion;
    
    /**
     * Lista de las fechas a dispersar
     */
    private List<Date> fechasDispersion;

    /**
     * Total a dispersar como resultado de la sumatoria de todos los meses
     */
    private BigDecimal total;

    /**
     * @return the identificadorCondicion
     */
    public Long getIdentificadorCondicion() {
        return identificadorCondicion;
    }

    /**
     * @param identificadorCondicion
     *        the identificadorCondicion to set
     */
    public void setIdentificadorCondicion(Long identificadorCondicion) {
        this.identificadorCondicion = identificadorCondicion;
    }

    /**
     * @return the tipoIdentificacionBeneficiario
     */
    public TipoIdentificacionEnum getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario
     *        the tipoIdentificacionBeneficiario to set
     */
    public void setTipoIdentificacionBeneficiario(TipoIdentificacionEnum tipoIdentificacionBeneficiario) {
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

    /**
     * @return the valoresDispersion
     */
    public List<BigDecimal> getValoresDispersion() {
        return valoresDispersion;
    }

    /**
     * @param valoresDispersion
     *        the valoresDispersion to set
     */
    public void setValoresDispersion(List<BigDecimal> valoresDispersion) {
        this.valoresDispersion = valoresDispersion;
    }

    /**
     * @return the fechasDispersion
     */
    public List<Date> getFechasDispersion() {
        return fechasDispersion;
    }

    /**
     * @param fechasDispersion the fechasDispersion to set
     */
    public void setFechasDispersion(List<Date> fechasDispersion) {
        this.fechasDispersion = fechasDispersion;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total
     *        the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
