package com.asopagos.dto.subsidiomonetario.pagos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.EstadoDerechoSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información por beneficiario/grupo familiar de un subsidio<br/>
 * <b>Módulo:</b> Asopagos - HU - <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class ItemSubsidioBeneficiarioDTO implements Serializable {

    private static final long serialVersionUID = 4792219991539038811L;

    //CAMPOS POR GRUPO FAMILIAR

    /**
     * Nombre completo del administrador del subsidio
     */
    private String nombreAdminSubsidio;

    /**
     * Tipo de identificación del administrador del subsidio
     */
    private TipoIdentificacionEnum tipoIdAdminSubsidio;

    /**
     * Número de identificación del administrador del subsidio
     */
    private String numeroIdAdminSubsidio;

    /**
     * Estado del derecho del subsidio (Estado del Subsidio)
     */
    private EstadoDerechoSubsidioEnum estadoDerechoSubsidio;

    /**
     * Valor de descuento del subsidio
     */
    private BigDecimal valorDescuento;

    /**
     * Valor del subsidio monetario
     */
    private BigDecimal valorSubsidioMonetario;

    /**
     * Valor total del subsidio
     */
    private BigDecimal valorTotal;

    // CAMPOS POR BENEFICIARIO

    /**
     * Tipo de identificación del beneficiario
     */
    private TipoIdentificacionEnum tipoIdentificacionBeneficiario;

    /**
     * Número de identificación del beneficiario
     */
    private String numeroIdentificacionBeneficiario;

    /**
     * Nombre completo del beneficiario
     */
    private String nombreBeneficiario;

    /**
     * Tipo de beneficiario (Cónyuge - Hijo -Padre)
     */
    private TipoBeneficiarioEnum tipoBeneficiario;

    /**
     * fecha liquidación
     */
    private String fechaLiquidacion;

    /**
     * Tipo de cuota del subsidio
     */
    private TipoCuotaSubsidioEnum tipoCuotaSubsidio;
   
     /**
     * Periodo de Pago subsidio
     */
    private String periodo;
       /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param peridoSubsidio
     *        the peridoSubsidio to set
     */
    public void setPeriodo(String peridoSubsidio) {
        this.periodo = peridoSubsidio;
    }


    /**
     * Tipo de liquidación del subsidio monetario
     */
    private TipoProcesoLiquidacionEnum tipoLiqSubsidioMonetario;

    /**
     * @return the nombreAdminSubsidio
     */
    public String getNombreAdminSubsidio() {
        return nombreAdminSubsidio;
    }

    /**
     * @param nombreAdminSubsidio
     *        the nombreAdminSubsidio to set
     */
    public void setNombreAdminSubsidio(String nombreAdminSubsidio) {
        this.nombreAdminSubsidio = nombreAdminSubsidio;
    }

    /**
     * @return the tipoIdAdminSubsidio
     */
    public TipoIdentificacionEnum getTipoIdAdminSubsidio() {
        return tipoIdAdminSubsidio;
    }

    /**
     * @param tipoIdAdminSubsidio
     *        the tipoIdAdminSubsidio to set
     */
    public void setTipoIdAdminSubsidio(TipoIdentificacionEnum tipoIdAdminSubsidio) {
        this.tipoIdAdminSubsidio = tipoIdAdminSubsidio;
    }

    /**
     * @return the numeroIdAdminSubsidio
     */
    public String getNumeroIdAdminSubsidio() {
        return numeroIdAdminSubsidio;
    }

    /**
     * @param numeroIdAdminSubsidio
     *        the numeroIdAdminSubsidio to set
     */
    public void setNumeroIdAdminSubsidio(String numeroIdAdminSubsidio) {
        this.numeroIdAdminSubsidio = numeroIdAdminSubsidio;
    }

    /**
     * @return the estadoDerechoSubsidio
     */
    public EstadoDerechoSubsidioEnum getEstadoDerechoSubsidio() {
        return estadoDerechoSubsidio;
    }

    /**
     * @param estadoDerechoSubsidio
     *        the estadoDerechoSubsidio to set
     */
    public void setEstadoDerechoSubsidio(EstadoDerechoSubsidioEnum estadoDerechoSubsidio) {
        this.estadoDerechoSubsidio = estadoDerechoSubsidio;
    }

    /**
     * @return the valorDescuento
     */
    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    /**
     * @param valorDescuento
     *        the valorDescuento to set
     */
    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    /**
     * @return the valorSubsidioMonetario
     */
    public BigDecimal getValorSubsidioMonetario() {
        return valorSubsidioMonetario;
    }

    /**
     * @param valorSubsidioMonetario
     *        the valorSubsidioMonetario to set
     */
    public void setValorSubsidioMonetario(BigDecimal valorSubsidioMonetario) {
        this.valorSubsidioMonetario = valorSubsidioMonetario;
    }

    /**
     * @return the valorTotal
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal
     *        the valorTotal to set
     */
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
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
     * @return the tipoBeneficiario
     */
    public TipoBeneficiarioEnum getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario
     *        the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(TipoBeneficiarioEnum tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the fechaLiquidacion
     */
    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *        the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the tipoCuotaSubsidio
     */
    public TipoCuotaSubsidioEnum getTipoCuotaSubsidio() {
        return tipoCuotaSubsidio;
    }

    /**
     * @param tipoCuotaSubsidio
     *        the tipoCuotaSubsidio to set
     */
    public void setTipoCuotaSubsidio(TipoCuotaSubsidioEnum tipoCuotaSubsidio) {
        this.tipoCuotaSubsidio = tipoCuotaSubsidio;
    }

    /**
     * @return the tipoLiqSubsidioMonetario
     */
    public TipoProcesoLiquidacionEnum getTipoLiqSubsidioMonetario() {
        return tipoLiqSubsidioMonetario;
    }

    /**
     * @param tipoLiqSubsidioMonetario
     *        the tipoLiqSubsidioMonetario to set
     */
    public void setTipoLiqSubsidioMonetario(TipoProcesoLiquidacionEnum tipoLiqSubsidioMonetario) {
        this.tipoLiqSubsidioMonetario = tipoLiqSubsidioMonetario;
    }

}
