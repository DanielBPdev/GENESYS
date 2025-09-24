package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado del item asociado a un registro de liquidación por fallecimiento <br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-317-510<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ItemResultadoLiquidacionFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la condición de beneficiario
     */
    private Long idCondicionBeneficiarioAfiliado;

    /**
     * Identificador de condición persona en el proceso de liquidación para el beneficiario
     */
    private Long idCondicionPersona;

    /**
     * Tipo de identificación del beneficiario de subsidio
     */
    private TipoIdentificacionEnum tipoIdentificacionBeneficiarioAfiliado;

    /**
     * Número de identificación del beneficiario de subsidio
     */
    private String numeroIdentificacionBeneficiarioAfiliado;

    /**
     * Nombre del beneficiario del subsidio
     */
    private String nombreBeneficiarioAfiliado;

    /**
     * Parentesco del beneficiario con el afiliado principal
     */
    private ClasificacionEnum parentesco;

    /**
     * Resultado de cumplimiento para beneficio de subsidio
     */
    private TipoCumplimientoEnum resultado;

    /**
     * Total de subsidio asignado para el beneficiario
     */
    private BigDecimal totalDerecho;

    /** Monto total descontado Entidades */
	private BigDecimal totalDescuentosPorEntidad;

    /**
     * Total descontado sobre el derecho asignado para el beneficiario
     */
    private BigDecimal totalDescuentos;

    /**
     * Total de subsidio asignado menos total descontado; consolidado subsidio
     */
    private BigDecimal totalPagar;

    /**
     * Atributo que permite indicar la acción de confirmación sobre el afiliado o los beneficiarios
     */
    private Boolean indicadorConfirmacion;


    public BigDecimal getTotalDescuentosPorEntidad() {
		return totalDescuentosPorEntidad;
	}

	public void setTotalDescuentosPorEntidad(BigDecimal totalDescuentosPorEntidad) {
		this.totalDescuentosPorEntidad = totalDescuentosPorEntidad;
	}

    /**
     * @return the idCondicionBeneficiarioAfiliado
     */
    public Long getIdCondicionBeneficiarioAfiliado() {
        return idCondicionBeneficiarioAfiliado;
    }

    /**
     * @return the idCondicionPersona
     */
    public Long getIdCondicionPersona() {
        return idCondicionPersona;
    }

    /**
     * @param idCondicionPersona
     *        the idCondicionPersona to set
     */
    public void setIdCondicionPersona(Long idCondicionPersona) {
        this.idCondicionPersona = idCondicionPersona;
    }

    /**
     * @param idCondicionBeneficiarioAfiliado
     *        the idCondicionBeneficiarioAfiliado to set
     */
    public void setIdCondicionBeneficiarioAfiliado(Long idCondicionBeneficiarioAfiliado) {
        this.idCondicionBeneficiarioAfiliado = idCondicionBeneficiarioAfiliado;
    }

    /**
     * @return the tipoIdentificacionBeneficiarioAfiliado
     */
    public TipoIdentificacionEnum getTipoIdentificacionBeneficiarioAfiliado() {
        return tipoIdentificacionBeneficiarioAfiliado;
    }

    /**
     * @param tipoIdentificacionBeneficiarioAfiliado
     *        the tipoIdentificacionBeneficiarioAfiliado to set
     */
    public void setTipoIdentificacionBeneficiarioAfiliado(TipoIdentificacionEnum tipoIdentificacionBeneficiarioAfiliado) {
        this.tipoIdentificacionBeneficiarioAfiliado = tipoIdentificacionBeneficiarioAfiliado;
    }

    /**
     * @return the numeroIdentificacionBeneficiarioAfiliado
     */
    public String getNumeroIdentificacionBeneficiarioAfiliado() {
        return numeroIdentificacionBeneficiarioAfiliado;
    }

    /**
     * @param numeroIdentificacionBeneficiarioAfiliado
     *        the numeroIdentificacionBeneficiarioAfiliado to set
     */
    public void setNumeroIdentificacionBeneficiarioAfiliado(String numeroIdentificacionBeneficiarioAfiliado) {
        this.numeroIdentificacionBeneficiarioAfiliado = numeroIdentificacionBeneficiarioAfiliado;
    }

    /**
     * @return the nombreBeneficiarioAfiliado
     */
    public String getNombreBeneficiarioAfiliado() {
        return nombreBeneficiarioAfiliado;
    }

    /**
     * @param nombreBeneficiarioAfiliado
     *        the nombreBeneficiarioAfiliado to set
     */
    public void setNombreBeneficiarioAfiliado(String nombreBeneficiarioAfiliado) {
        this.nombreBeneficiarioAfiliado = nombreBeneficiarioAfiliado;
    }

    /**
     * @return the parentesco
     */
    public ClasificacionEnum getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *        the parentesco to set
     */
    public void setParentesco(ClasificacionEnum parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * @return the resultado
     */
    public TipoCumplimientoEnum getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *        the resultado to set
     */
    public void setResultado(TipoCumplimientoEnum resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the totalDerecho
     */
    public BigDecimal getTotalDerecho() {
        return totalDerecho;
    }

    /**
     * @param totalDerecho
     *        the totalDerecho to set
     */
    public void setTotalDerecho(BigDecimal totalDerecho) {
        this.totalDerecho = totalDerecho;
    }

    /**
     * @return the totalDescuentos
     */
    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    /**
     * @param totalDescuentos
     *        the totalDescuentos to set
     */
    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    /**
     * @return the totalPagar
     */
    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    /**
     * @param totalPagar
     *        the totalPagar to set
     */
    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    /**
     * @return the indicadorConfirmacion
     */
    public Boolean getIndicadorConfirmacion() {
        return indicadorConfirmacion;
    }

    /**
     * @param indicadorConfirmacion
     *        the indicadorConfirmacion to set
     */
    public void setIndicadorConfirmacion(Boolean indicadorConfirmacion) {
        this.indicadorConfirmacion = indicadorConfirmacion;
    }

}
