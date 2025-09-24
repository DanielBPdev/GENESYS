package com.asopagos.dto.aportes;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;

/**
 * Clase DTO que representa los aportes de un cotizante
 * 
 * @author <a href="mailto:jusanchez@heinsohn.com.co">Julian Andres Sanchez </a>
 */
@XmlRootElement
public class AportesDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2245244988159307379L;
	/**
	 * Estado del aporte
	 */
	private EstadoAporteEnum estadoAporte;
	/**
	 * Aporte final
	 */
	private BigDecimal aporteFinal;
	/**
	 * Intereses Finales
	 */
	private BigDecimal interesesFinales;
	/**
	 * Tipo ajuste movimiento aporte
	 */
	private TipoAjusteMovimientoAporteEnum tipoAjusteMovimiento;
	/**
	 * Cumple aportes
	 */
	private Boolean cumple;
	/**
	 * Comentario del aporte realizado
	 */
	private String comentario;
	/**
	 * Sub tipo de cotizante
	 */
	private SubTipoCotizanteEnum subTipoCotizante;
	/**
	 * Tipo de cotizante
	 */
	private TipoCotizanteEnum tipoCotizante;
	/**
	 * Codigo del departamento
	 */
	private Long codigoDepto;
	/**
	 * Codigo del Municipio
	 */
	private Long codigoMunicipio;
	/**
	 * Días Cotizados actual.
	 */
	private Long diasCotizado;
	/**
	 * Días Cotizados nuevo
	 */
	private Long diasCotizadoNuevo;
	/**
	 * Salario basico
	 */
	private BigDecimal salarioBasico;
	/**
	 * Salario basico
	 */
	private BigDecimal salarioBasicoNuevo;
	/**
	 * Ingreso base cotizaciones
	 */
	private BigDecimal ibc;
	/**
	 * Ingreso base cotizaciones nuevo.
	 */
	private BigDecimal ibcNuevo;
	/**
	 * Tarifa
	 */
	private BigDecimal tarifa;
	/**
	 * Tarifa
	 */
	private BigDecimal tarifaNuevo;
	/**
	 * Salario Integral
	 */
	private Boolean salarioIntegral;
	/**
	 * Salario Integral
	 */
	private Boolean salarioIntegralNuevo;
	/**
	 * Número de horas laborales
	 */
	private Long numeroHorasLaboral;
	/**
	 * Número de horas laborales
	 */
	private Long numeroHorasLaboralNuevo;
	/**
	 * Aportes en Mora
	 */
	private BigDecimal moraAporte;
	/**
	 * Aportes en Mora
	 */
	private BigDecimal moraAporteNuevo;
	/**
	 * Aporte obligatorio.
	 */
	private BigDecimal aporteObligatorio;
	/**
	 * Aporte obligatorio Nuevo.
	 */
	private BigDecimal aporteObligatorioNuevo;

	/**
	 * Aplicar monto aporte obligatorio a devolución
	 */
	private Boolean aplicarAporteObligatorio;
	/**
	 * Aplicar mora del cotizante a devolución
	 */
	private Boolean aplicarMoraCotizante;

	/**
	 * Método que retorna el valor de estadoAporte.
	 * 
	 * @return valor de estadoAporte.
	 */
	public EstadoAporteEnum getEstadoAporte() {
		return estadoAporte;
	}

	/**
	 * Método encargado de modificar el valor de estadoAporte.
	 * 
	 * @param valor
	 *            para modificar estadoAporte.
	 */
	public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
		this.estadoAporte = estadoAporte;
	}

	/**
	 * Método que retorna el valor de aporteFinal.
	 * 
	 * @return valor de aporteFinal.
	 */
	public BigDecimal getAporteFinal() {
		return aporteFinal;
	}

	/**
	 * Método encargado de modificar el valor de aporteFinal.
	 * 
	 * @param valor
	 *            para modificar aporteFinal.
	 */
	public void setAporteFinal(BigDecimal aporteFinal) {
		this.aporteFinal = aporteFinal;
	}

	/**
	 * Método que retorna el valor de interesesFinales.
	 * 
	 * @return valor de interesesFinales.
	 */
	public BigDecimal getInteresesFinales() {
		return interesesFinales;
	}

	/**
	 * Método encargado de modificar el valor de interesesFinales.
	 * 
	 * @param valor
	 *            para modificar interesesFinales.
	 */
	public void setInteresesFinales(BigDecimal interesesFinales) {
		this.interesesFinales = interesesFinales;
	}

	/**
	 * Método que retorna el valor de tipoAjusteMovimiento.
	 * 
	 * @return valor de tipoAjusteMovimiento.
	 */
	public TipoAjusteMovimientoAporteEnum getTipoAjusteMovimiento() {
		return tipoAjusteMovimiento;
	}

	/**
	 * Método encargado de modificar el valor de tipoAjusteMovimiento.
	 * 
	 * @param valor
	 *            para modificar tipoAjusteMovimiento.
	 */
	public void setTipoAjusteMovimiento(TipoAjusteMovimientoAporteEnum tipoAjusteMovimiento) {
		this.tipoAjusteMovimiento = tipoAjusteMovimiento;
	}

	/**
	 * Método que retorna el valor de cumple.
	 * 
	 * @return valor de cumple.
	 */
	public Boolean getCumple() {
		return cumple;
	}

	/**
	 * Método encargado de modificar el valor de cumple.
	 * 
	 * @param valor
	 *            para modificar cumple.
	 */
	public void setCumple(Boolean cumple) {
		this.cumple = cumple;
	}

	/**
	 * Método que retorna el valor de comentario.
	 * 
	 * @return valor de comentario.
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * Método encargado de modificar el valor de comentario.
	 * 
	 * @param valor
	 *            para modificar comentario.
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * Método que retorna el valor de subTipoCotizante.
	 * 
	 * @return valor de subTipoCotizante.
	 */
	public SubTipoCotizanteEnum getSubTipoCotizante() {
		return subTipoCotizante;
	}

	/**
	 * Método encargado de modificar el valor de subTipoCotizante.
	 * 
	 * @param valor
	 *            para modificar subTipoCotizante.
	 */
	public void setSubTipoCotizante(SubTipoCotizanteEnum subTipoCotizante) {
		this.subTipoCotizante = subTipoCotizante;
	}

	/**
	 * Método que retorna el valor de tipoCotizante.
	 * 
	 * @return valor de tipoCotizante.
	 */
	public TipoCotizanteEnum getTipoCotizante() {
		return tipoCotizante;
	}

	/**
	 * Método encargado de modificar el valor de tipoCotizante.
	 * 
	 * @param valor
	 *            para modificar tipoCotizante.
	 */
	public void setTipoCotizante(TipoCotizanteEnum tipoCotizante) {
		this.tipoCotizante = tipoCotizante;
	}

	/**
	 * Método que retorna el valor de codigoDepto.
	 * 
	 * @return valor de codigoDepto.
	 */
	public Long getCodigoDepto() {
		return codigoDepto;
	}

	/**
	 * Método encargado de modificar el valor de codigoDepto.
	 * 
	 * @param valor
	 *            para modificar codigoDepto.
	 */
	public void setCodigoDepto(Long codigoDepto) {
		this.codigoDepto = codigoDepto;
	}

	/**
	 * Método que retorna el valor de codigoMunicipio.
	 * 
	 * @return valor de codigoMunicipio.
	 */
	public Long getCodigoMunicipio() {
		return codigoMunicipio;
	}

	/**
	 * Método encargado de modificar el valor de codigoMunicipio.
	 * 
	 * @param valor
	 *            para modificar codigoMunicipio.
	 */
	public void setCodigoMunicipio(Long codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	/**
	 * Método que retorna el valor de diasCotizado.
	 * 
	 * @return valor de diasCotizado.
	 */
	public Long getDiasCotizado() {
		return diasCotizado;
	}

	/**
	 * Método encargado de modificar el valor de diasCotizado.
	 * 
	 * @param valor
	 *            para modificar diasCotizado.
	 */
	public void setDiasCotizado(Long diasCotizado) {
		this.diasCotizado = diasCotizado;
	}

	/**
	 * Método que retorna el valor de diasCotizadoNuevo.
	 * 
	 * @return valor de diasCotizadoNuevo.
	 */
	public Long getDiasCotizadoNuevo() {
		return diasCotizadoNuevo;
	}

	/**
	 * Método encargado de modificar el valor de diasCotizadoNuevo.
	 * 
	 * @param valor
	 *            para modificar diasCotizadoNuevo.
	 */
	public void setDiasCotizadoNuevo(Long diasCotizadoNuevo) {
		this.diasCotizadoNuevo = diasCotizadoNuevo;
	}

	/**
	 * Método que retorna el valor de salarioBasico.
	 * 
	 * @return valor de salarioBasico.
	 */
	public BigDecimal getSalarioBasico() {
		return salarioBasico;
	}

	/**
	 * Método encargado de modificar el valor de salarioBasico.
	 * 
	 * @param valor
	 *            para modificar salarioBasico.
	 */
	public void setSalarioBasico(BigDecimal salarioBasico) {
		this.salarioBasico = salarioBasico;
	}

	/**
	 * Método que retorna el valor de salarioBasicoNuevo.
	 * 
	 * @return valor de salarioBasicoNuevo.
	 */
	public BigDecimal getSalarioBasicoNuevo() {
		return salarioBasicoNuevo;
	}

	/**
	 * Método encargado de modificar el valor de salarioBasicoNuevo.
	 * 
	 * @param valor
	 *            para modificar salarioBasicoNuevo.
	 */
	public void setSalarioBasicoNuevo(BigDecimal salarioBasicoNuevo) {
		this.salarioBasicoNuevo = salarioBasicoNuevo;
	}

	/**
	 * Método que retorna el valor de ibc.
	 * 
	 * @return valor de ibc.
	 */
	public BigDecimal getIbc() {
		return ibc;
	}

	/**
	 * Método encargado de modificar el valor de ibc.
	 * 
	 * @param valor
	 *            para modificar ibc.
	 */
	public void setIbc(BigDecimal ibc) {
		this.ibc = ibc;
	}

	/**
	 * Método que retorna el valor de ibcNuevo.
	 * 
	 * @return valor de ibcNuevo.
	 */
	public BigDecimal getIbcNuevo() {
		return ibcNuevo;
	}

	/**
	 * Método encargado de modificar el valor de ibcNuevo.
	 * 
	 * @param valor
	 *            para modificar ibcNuevo.
	 */
	public void setIbcNuevo(BigDecimal ibcNuevo) {
		this.ibcNuevo = ibcNuevo;
	}

	/**
	 * Método que retorna el valor de tarifa.
	 * 
	 * @return valor de tarifa.
	 */
	public BigDecimal getTarifa() {
		return tarifa;
	}

	/**
	 * Método encargado de modificar el valor de tarifa.
	 * 
	 * @param valor
	 *            para modificar tarifa.
	 */
	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Método que retorna el valor de tarifaNuevo.
	 * 
	 * @return valor de tarifaNuevo.
	 */
	public BigDecimal getTarifaNuevo() {
		return tarifaNuevo;
	}

	/**
	 * Método encargado de modificar el valor de tarifaNuevo.
	 * 
	 * @param valor
	 *            para modificar tarifaNuevo.
	 */
	public void setTarifaNuevo(BigDecimal tarifaNuevo) {
		this.tarifaNuevo = tarifaNuevo;
	}

	/**
	 * Método que retorna el valor de numeroHorasLaboral.
	 * 
	 * @return valor de numeroHorasLaboral.
	 */
	public Long getNumeroHorasLaboral() {
		return numeroHorasLaboral;
	}

	/**
	 * Método encargado de modificar el valor de numeroHorasLaboral.
	 * 
	 * @param valor
	 *            para modificar numeroHorasLaboral.
	 */
	public void setNumeroHorasLaboral(Long numeroHorasLaboral) {
		this.numeroHorasLaboral = numeroHorasLaboral;
	}

	/**
	 * Método que retorna el valor de numeroHorasLaboralNuevo.
	 * 
	 * @return valor de numeroHorasLaboralNuevo.
	 */
	public Long getNumeroHorasLaboralNuevo() {
		return numeroHorasLaboralNuevo;
	}

	/**
	 * Método encargado de modificar el valor de numeroHorasLaboralNuevo.
	 * 
	 * @param valor
	 *            para modificar numeroHorasLaboralNuevo.
	 */
	public void setNumeroHorasLaboralNuevo(Long numeroHorasLaboralNuevo) {
		this.numeroHorasLaboralNuevo = numeroHorasLaboralNuevo;
	}

	/**
	 * Método que retorna el valor de moraAporte.
	 * 
	 * @return valor de moraAporte.
	 */
	public BigDecimal getMoraAporte() {
		return moraAporte;
	}

	/**
	 * Método encargado de modificar el valor de moraAporte.
	 * 
	 * @param valor
	 *            para modificar moraAporte.
	 */
	public void setMoraAporte(BigDecimal moraAporte) {
		this.moraAporte = moraAporte;
	}

	/**
	 * Método que retorna el valor de moraAporteNuevo.
	 * 
	 * @return valor de moraAporteNuevo.
	 */
	public BigDecimal getMoraAporteNuevo() {
		return moraAporteNuevo;
	}

	/**
	 * Método encargado de modificar el valor de moraAporteNuevo.
	 * 
	 * @param valor
	 *            para modificar moraAporteNuevo.
	 */
	public void setMoraAporteNuevo(BigDecimal moraAporteNuevo) {
		this.moraAporteNuevo = moraAporteNuevo;
	}

	/**
	 * Método que retorna el valor de aporteObligatorio.
	 * 
	 * @return valor de aporteObligatorio.
	 */
	public BigDecimal getAporteObligatorio() {
		return aporteObligatorio;
	}

	/**
	 * Método encargado de modificar el valor de aporteObligatorio.
	 * 
	 * @param valor
	 *            para modificar aporteObligatorio.
	 */
	public void setAporteObligatorio(BigDecimal aporteObligatorio) {
		this.aporteObligatorio = aporteObligatorio;
	}

	/**
	 * Método que retorna el valor de aporteObligatorioNuevo.
	 * 
	 * @return valor de aporteObligatorioNuevo.
	 */
	public BigDecimal getAporteObligatorioNuevo() {
		return aporteObligatorioNuevo;
	}

	/**
	 * Método encargado de modificar el valor de aporteObligatorioNuevo.
	 * 
	 * @param valor
	 *            para modificar aporteObligatorioNuevo.
	 */
	public void setAporteObligatorioNuevo(BigDecimal aporteObligatorioNuevo) {
		this.aporteObligatorioNuevo = aporteObligatorioNuevo;
	}

	/**
	 * Obtiene el valor de salarioIntegral
	 * 
	 * @return El valor de salarioIntegral
	 */
	public Boolean getSalarioIntegral() {
		return salarioIntegral;
	}

	/**
	 * Establece el valor de salarioIntegral
	 * 
	 * @param salarioIntegral
	 *            El valor de salarioIntegral por asignar
	 */
	public void setSalarioIntegral(Boolean salarioIntegral) {
		this.salarioIntegral = salarioIntegral;
	}

	/**
	 * Obtiene el valor de salarioIntegralNuevo
	 * 
	 * @return El valor de salarioIntegralNuevo
	 */
	public Boolean getSalarioIntegralNuevo() {
		return salarioIntegralNuevo;
	}

	/**
	 * Establece el valor de salarioIntegralNuevo
	 * 
	 * @param salarioIntegralNuevo
	 *            El valor de salarioIntegralNuevo por asignar
	 */
	public void setSalarioIntegralNuevo(Boolean salarioIntegralNuevo) {
		this.salarioIntegralNuevo = salarioIntegralNuevo;
	}

	/**
	 * Obtiene el valor de aplicarAporteObligatorio
	 * 
	 * @return El valor de aplicarAporteObligatorio
	 */
	public Boolean getAplicarAporteObligatorio() {
		return aplicarAporteObligatorio;
	}

	/**
	 * Establece el valor de aplicarAporteObligatorio
	 * 
	 * @param aplicarAporteObligatorio
	 *            El valor de aplicarAporteObligatorio por asignar
	 */
	public void setAplicarAporteObligatorio(Boolean aplicarAporteObligatorio) {
		this.aplicarAporteObligatorio = aplicarAporteObligatorio;
	}

	/**
	 * Obtiene el valor de aplicarMoraCotizante
	 * 
	 * @return El valor de aplicarMoraCotizante
	 */
	public Boolean getAplicarMoraCotizante() {
		return aplicarMoraCotizante;
	}

	/**
	 * Establece el valor de aplicarMoraCotizante
	 * 
	 * @param aplicarMoraCotizante
	 *            El valor de aplicarMoraCotizante por asignar
	 */
	public void setAplicarMoraCotizante(Boolean aplicarMoraCotizante) {
		this.aplicarMoraCotizante = aplicarMoraCotizante;
	}
}