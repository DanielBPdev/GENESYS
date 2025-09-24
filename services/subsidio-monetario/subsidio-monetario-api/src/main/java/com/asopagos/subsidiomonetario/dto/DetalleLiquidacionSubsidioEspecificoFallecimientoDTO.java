package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la informacio de detalle de la liquidación de subsidio especifico de fallecimiento para la vista 360<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-TRA-001<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q</a>
 */
public class DetalleLiquidacionSubsidioEspecificoFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = -8942307016851103397L;

    
    /** Numero de radicado de la liquidación */
    private String numeroRadicacion;
    
    /** Fecha de inicio de la liquidación */
    private Date fechaLiquidacion;

    /**
     * Descripción del tipo del proceso de liquidación de subsidio
     */
    private TipoProcesoLiquidacionEnum tipoLiquidacion;
    
    /**
     * Descripción del tipo del proceso de liquidación de subsidio especifica
     */
    private TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica;
    
    /**
     * Descripción del estado de liquidación de subsidio
     */
    private EstadoProcesoLiquidacionEnum estadoLiquidacion;
    
    /** Numero de radicado de la liquidación */
    private String resultadoProceso;
  
    /**
     * Indicador de consideración para desembolso frente al aporte
     */
    private Boolean consideracionAporteDesembolso;

    /**
     * Modo en el que se realiza en desembolso (una cuota o mes a mes)
     */
    private ModoDesembolsoEnum modoDesembolso;
    
    /** Valor dispersado */
    private BigDecimal valorDispersado;
    
    /** Valor pendiente programado */
    private BigDecimal pendienteProgramado;
    
    /** Valor pagado */
    private BigDecimal pagado;
    
    /** Valor de saldo */
    private BigDecimal saldo;
    
    /** Valor de cuota monetaria */
    private BigDecimal valorCuota;
    
    /** Valor descontado de la cuota monetaria */
    private BigDecimal valorDescuento;
    
    /** Valor de la cuota menos lo descontado*/
    private BigDecimal valorCuotaAjustada;
   
    /** Valor fecha fallecimiento */
    private Date fechaFallecimiento;
    
    /** Indica si tiene aportes o no */
    private Boolean tieneAportes;
    
    /** indica el estado de los aportes  */
    private String estadoAportes;
    
	/**
	 * @return the numeroRadicacion
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	/**
	 * @param numeroRadicacion the numeroRadicacion to set
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}


	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}


	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}


	/**
	 * @return the tipoLiquidacion
	 */
	public TipoProcesoLiquidacionEnum getTipoLiquidacion() {
		return tipoLiquidacion;
	}


	/**
	 * @param tipoLiquidacion the tipoLiquidacion to set
	 */
	public void setTipoLiquidacion(TipoProcesoLiquidacionEnum tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
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
	 * @return the estadoLiquidacion
	 */
	public EstadoProcesoLiquidacionEnum getEstadoLiquidacion() {
		return estadoLiquidacion;
	}


	/**
	 * @param estadoLiquidacion the estadoLiquidacion to set
	 */
	public void setEstadoLiquidacion(EstadoProcesoLiquidacionEnum estadoLiquidacion) {
		this.estadoLiquidacion = estadoLiquidacion;
	}


	/**
	 * @return the resultadoProceso
	 */
	public String getResultadoProceso() {
		return resultadoProceso;
	}


	/**
	 * @param resultadoProceso the resultadoProceso to set
	 */
	public void setResultadoProceso(String resultadoProceso) {
		this.resultadoProceso = resultadoProceso;
	}


	/**
	 * @return the modoDesembolso
	 */
	public ModoDesembolsoEnum getModoDesembolso() {
		return modoDesembolso;
	}


	/**
	 * @param modoDesembolso the modoDesembolso to set
	 */
	public void setModoDesembolso(ModoDesembolsoEnum modoDesembolso) {
		this.modoDesembolso = modoDesembolso;
	}


	/**
	 * @return the consideracionAporteDesembolso
	 */
	public Boolean getConsideracionAporteDesembolso() {
		return consideracionAporteDesembolso;
	}


	/**
	 * @param consideracionAporteDesembolso the consideracionAporteDesembolso to set
	 */
	public void setConsideracionAporteDesembolso(Boolean consideracionAporteDesembolso) {
		this.consideracionAporteDesembolso = consideracionAporteDesembolso;
	}


	/**
	 * @return the valorDispersado
	 */
	public BigDecimal getValorDispersado() {
		return valorDispersado;
	}


	/**
	 * @param valorDispersado the valorDispersado to set
	 */
	public void setValorDispersado(BigDecimal valorDispersado) {
		this.valorDispersado = valorDispersado;
	}


	/**
	 * @return the pendienteProgramado
	 */
	public BigDecimal getPendienteProgramado() {
		return pendienteProgramado;
	}


	/**
	 * @param pendienteProgramado the pendienteProgramado to set
	 */
	public void setPendienteProgramado(BigDecimal pendienteProgramado) {
		this.pendienteProgramado = pendienteProgramado;
	}


	/**
	 * @return the pagado
	 */
	public BigDecimal getPagado() {
		return pagado;
	}


	/**
	 * @param pagado the pagado to set
	 */
	public void setPagado(BigDecimal pagado) {
		this.pagado = pagado;
	}


	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}


	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}


	/**
	 * @return the valorCuota
	 */
	public BigDecimal getValorCuota() {
		return valorCuota;
	}


	/**
	 * @param valorCuota the valorCuota to set
	 */
	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}


	/**
	 * @return the valorDescuento
	 */
	public BigDecimal getValorDescuento() {
		return valorDescuento;
	}


	/**
	 * @param valorDescuento the valorDescuento to set
	 */
	public void setValorDescuento(BigDecimal valorDescuento) {
		this.valorDescuento = valorDescuento;
	}


	/**
	 * @return the valorCuotaAjustada
	 */
	public BigDecimal getValorCuotaAjustada() {
		return valorCuotaAjustada;
	}


	/**
	 * @param valorCuotaAjustada the valorCuotaAjustada to set
	 */
	public void setValorCuotaAjustada(BigDecimal valorCuotaAjustada) {
		this.valorCuotaAjustada = valorCuotaAjustada;
	}


	/**
	 * @return the fechaFallecimiento
	 */
	public Date getFechaFallecimiento() {
		return fechaFallecimiento;
	}


	/**
	 * @param fechaFallecimiento the fechaFallecimiento to set
	 */
	public void setFechaFallecimiento(Date fechaFallecimiento) {
		this.fechaFallecimiento = fechaFallecimiento;
	}


	/**
	 * @return the tieneAportes
	 */
	public Boolean getTieneAportes() {
		return tieneAportes;
	}


	/**
	 * @param tieneAportes the tieneAportes to set
	 */
	public void setTieneAportes(Boolean tieneAportes) {
		this.tieneAportes = tieneAportes;
	}


	/**
	 * @return the estadoAportes
	 */
	public String getEstadoAportes() {
		return estadoAportes;
	}


	/**
	 * @param estadoAportes the estadoAportes to set
	 */
	public void setEstadoAportes(String estadoAportes) {
		this.estadoAportes = estadoAportes;
	}
    
    
}
