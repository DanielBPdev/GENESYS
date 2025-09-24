package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoPeriodoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoRetroactivoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información de detalle para el
 * resultado de la consulta de una liquidación para las vistas 360<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - Vista 360 empleador<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo</a>
 */
public class ConsultaLiquidacionSubsidioMonetarioDTO implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;

	/** Indica el tipo de liquidacion */
	private String tipoLiquidacion;

	/** Número de radicado de la solicitud */
	private String numeroRadicado;

	/** Fecha en la cual se inició la liquidación */
	private Date fechaLiquidacion;

	/** Indica si cumple o no con las condiciones */
	private Boolean cumple;

	/** Razón o causa por la cual falla las condiciones de liquidación */
	private String razonCausal;

	/** Fecha del periodo para la cual se realizó la liquidación */
	private Date periodo;

	/** Número de trabajadores relacionados en la liquidación */
	private Long numeroTrabajadores;

	/** Monto total liquidado */
	private BigDecimal montoLiquidado;

	/** Monto total descontado Entidades */
	private BigDecimal totalDescuentosPorEntidad;

	/** Monto total descontado */
	private BigDecimal montoDescontado;

	/** Monto total a pagar */
	private BigDecimal montoParaPago;
	
	/** Nombre o razón social del empleador */
	private String razonSocialEmpleador;
	
	/** Monto total a pagar */
	private BigDecimal montoRetirado;
	
	/** tipo de Periodo */
	private TipoPeriodoEnum tipoPeriodo;
	
	/** tipo de Periodo */
	private TipoRetroactivoEnum tipoRetroactivo;
	
	/** Tipo de proceso de liquidacion */
	private TipoProcesoLiquidacionEnum tipoProcesoLiquidacion;
	
	/** Tipo de liquidación especifica */
	private TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica;
	
	/** Tipo de identificacion del empleador */
	private TipoIdentificacionEnum tipoIdentificacionEmpl;
	
	/** Número de identificacion del empleador */
	private String numeroIdentificacionEmpl;
	
	/** Monto total programado */
    private BigDecimal montoProgramado;
    
    private BigDecimal totalPagar;
    
    private BigDecimal montoDispersado;
    
	private Boolean cumpleSubsidioPeriodoFallecimiento;

	private ResultadoProcesoEnum resultadoProceso;
    
    private String estadoLiquidacion;

	public Boolean getCumpleSubsidioPeriodoFallecimiento() {
		return this.cumpleSubsidioPeriodoFallecimiento;
	}

	public void setCumpleSubsidioPeriodoFallecimiento(Boolean cumpleSubsidioPeriodoFallecimiento) {
		this.cumpleSubsidioPeriodoFallecimiento = cumpleSubsidioPeriodoFallecimiento;
	}

	public BigDecimal getTotalDescuentosPorEntidad() {
		return totalDescuentosPorEntidad;
	}

	public void setTotalDescuentosPorEntidad(BigDecimal totalDescuentosPorEntidad) {
		this.totalDescuentosPorEntidad = totalDescuentosPorEntidad;
	}

	/**
	 * @return the tipoLiquidacion
	 */
	public String getTipoLiquidacion() {
		return tipoLiquidacion;
	}

	/**
	 * @param tipoLiquidacion
	 *            the tipoLiquidacion to set
	 */
	public void setTipoLiquidacion(String tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
	}

	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado
	 *            the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 *            the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the cumple
	 */
	public Boolean getCumple() {
		return cumple;
	}

	/**
	 * @param cumple
	 *            the cumple to set
	 */
	public void setCumple(Boolean cumple) {
		this.cumple = cumple;
	}

	/**
	 * @return the razonCausal
	 */
	public String getRazonCausal() {
		return razonCausal;
	}

	/**
	 * @param razonCausal
	 *            the razonCausal to set
	 */
	public void setRazonCausal(String razonCausal) {
		this.razonCausal = razonCausal;
	}

	/**
	 * @return the periodo
	 */
	public Date getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo
	 *            the periodo to set
	 */
	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the numeroTrabajadores
	 */
	public Long getNumeroTrabajadores() {
		return numeroTrabajadores;
	}

	/**
	 * @param numeroTrabajadores
	 *            the numeroTrabajadores to set
	 */
	public void setNumeroTrabajadores(Long numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}

	/**
	 * @return the montoLiquidado
	 */
	public BigDecimal getMontoLiquidado() {
		return montoLiquidado;
	}

	/**
	 * @param montoLiquidado
	 *            the montoLiquidado to set
	 */
	public void setMontoLiquidado(BigDecimal montoLiquidado) {
		this.montoLiquidado = montoLiquidado;
	}

	/**
	 * @return the montoDescontado
	 */
	public BigDecimal getMontoDescontado() {
		return montoDescontado;
	}

	/**
	 * @param montoDescontado
	 *            the montoDescontado to set
	 */
	public void setMontoDescontado(BigDecimal montoDescontado) {
		this.montoDescontado = montoDescontado;
	}

	/**
	 * @return the montoParaPago
	 */
	public BigDecimal getMontoParaPago() {
		return montoParaPago;
	}

	/**
	 * @param montoParaPago
	 *            the montoParaPago to set
	 */
	public void setMontoParaPago(BigDecimal montoParaPago) {
		this.montoParaPago = montoParaPago;
	}

	/**
	 * @return the razonSocialEmpleador
	 */
	public String getRazonSocialEmpleador() {
		return razonSocialEmpleador;
	}

	/**
	 * @param razonSocialEmpleador the razonSocialEmpleador to set
	 */
	public void setRazonSocialEmpleador(String razonSocialEmpleador) {
		this.razonSocialEmpleador = razonSocialEmpleador;
	}

	/**
	 * @return the montoRetirado
	 */
	public BigDecimal getMontoRetirado() {
		return montoRetirado;
	}

	/**
	 * @param montoRetirado the montoRetirado to set
	 */
	public void setMontoRetirado(BigDecimal montoRetirado) {
		this.montoRetirado = montoRetirado;
	}

	/**
	 * @return the tipoPeriodo
	 */
	public TipoPeriodoEnum getTipoPeriodo() {
		return tipoPeriodo;
	}

	/**
	 * @param tipoPeriodo the tipoPeriodo to set
	 */
	public void setTipoPeriodo(TipoPeriodoEnum tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	/**
	 * @return the tipoRetroactivo
	 */
	public TipoRetroactivoEnum getTipoRetroactivo() {
		return tipoRetroactivo;
	}

	/**
	 * @param tipoRetroactivo the tipoRetroactivo to set
	 */
	public void setTipoRetroactivo(TipoRetroactivoEnum tipoRetroactivo) {
		this.tipoRetroactivo = tipoRetroactivo;
	}

	/**
	 * @return the tipoProcesoLiquidacion
	 */
	public TipoProcesoLiquidacionEnum getTipoProcesoLiquidacion() {
		return tipoProcesoLiquidacion;
	}

	/**
	 * @param tipoProcesoLiquidacion the tipoProcesoLiquidacion to set
	 */
	public void setTipoProcesoLiquidacion(TipoProcesoLiquidacionEnum tipoProcesoLiquidacion) {
		this.tipoProcesoLiquidacion = tipoProcesoLiquidacion;
	}

	/**
	 * @return the tipoLiquidacionEspecifica
	 */
	public TipoLiquidacionEspecificaEnum getTipoLiquidacionEspecifica() {
		return tipoLiquidacionEspecifica;
	}

	/**
	 * @param tipoLiquidacionEspecifica the tipoLiquidacionEspecifica to set
	 */
	public void setTipoLiquidacionEspecifica(TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica) {
		this.tipoLiquidacionEspecifica = tipoLiquidacionEspecifica;
	}

	/**
	 * @return the tipoIdentificacionEmpl
	 */
	public TipoIdentificacionEnum getTipoIdentificacionEmpl() {
		return tipoIdentificacionEmpl;
	}

	/**
	 * @param tipoIdentificacionEmpl the tipoIdentificacionEmpl to set
	 */
	public void setTipoIdentificacionEmpl(TipoIdentificacionEnum tipoIdentificacionEmpl) {
		this.tipoIdentificacionEmpl = tipoIdentificacionEmpl;
	}

	/**
	 * @return the numeroIdentificacionEmpl
	 */
	public String getNumeroIdentificacionEmpl() {
		return numeroIdentificacionEmpl;
	}

	/**
	 * @param numeroIdentificacionEmpl the numeroIdentificacionEmpl to set
	 */
	public void setNumeroIdentificacionEmpl(String numeroIdentificacionEmpl) {
		this.numeroIdentificacionEmpl = numeroIdentificacionEmpl;
	}

    /**
     * @return the montoProgramado
     */
    public BigDecimal getMontoProgramado() {
        return montoProgramado;
    }

    /**
     * @param montoProgramado the montoProgramado to set
     */
    public void setMontoProgramado(BigDecimal montoProgramado) {
        this.montoProgramado = montoProgramado;
    }

    /**
     * @return the totalPagar
     */
    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    /**
     * @param totalPagar the totalPagar to set
     */
    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

	/**
	 * @return the montoDispersado
	 */
	public BigDecimal getMontoDispersado() {
		return montoDispersado;
	}

	/**
	 * @param montoDispersado the montoDispersado to set
	 */
	public void setMontoDispersado(BigDecimal montoDispersado) {
		this.montoDispersado = montoDispersado;
	}

	/**
	 * @return the resultadoProceso
	 */
	public ResultadoProcesoEnum getResultadoProceso() {
		return resultadoProceso;
	}

	/**
	 * @param resultadoProceso the resultadoProceso to set
	 */
	public void setResultadoProceso(ResultadoProcesoEnum resultadoProceso) {
		this.resultadoProceso = resultadoProceso;
	}

	/**
	 * @return the estadoLiquidacion
	 */
	public String getEstadoLiquidacion() {
		return estadoLiquidacion;
	}

	/**
	 * @param estadoLiquidacion the estadoLiquidacion to set
	 */
	public void setEstadoLiquidacion(String estadoLiquidacion) {
		this.estadoLiquidacion = estadoLiquidacion;
	}
}
