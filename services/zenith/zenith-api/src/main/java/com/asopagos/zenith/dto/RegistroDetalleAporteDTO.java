package com.asopagos.zenith.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RegistroDetalleAporteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tipoIdentificacionCotizante;
	private String numeroIdentificacionCotizante;
	private String periodoAporte;
	private Short diasCotizadosPeriodo;
	private Short tipoCotizante;
	private String codigoCCFDondeRealizoAporte;
	private String empresaDondeRealizoAporte;
	private String nITEmpresaRealizoAporte;
	private BigDecimal tarifaAporte;
	private BigDecimal valorAporteObligatorio;
	
	/**
	 * 
	 */
	public RegistroDetalleAporteDTO() {
	}

	/**
	 * @param tipoIdentificacionCotizante
	 * @param numeroIdentificacionCotizante
	 * @param periodoAporte
	 * @param diasCotizadosPeriodo
	 * @param tipoCotizante
	 * @param codigoCCFDondeRealizoAporte
	 * @param empresaDondeRealizoAporte
	 * @param nITEmpresaRealizoAporte
	 */
	public RegistroDetalleAporteDTO(String tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
			String periodoAporte, Short diasCotizadosPeriodo, Short tipoCotizante, String codigoCCFDondeRealizoAporte,
			String empresaDondeRealizoAporte, String nITEmpresaRealizoAporte, BigDecimal tarifaAporte, BigDecimal valorAporteObligatorio) {
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.periodoAporte = periodoAporte;
		this.diasCotizadosPeriodo = diasCotizadosPeriodo;
		this.tipoCotizante = tipoCotizante;
		this.codigoCCFDondeRealizoAporte = codigoCCFDondeRealizoAporte;
		this.empresaDondeRealizoAporte = empresaDondeRealizoAporte;
		this.nITEmpresaRealizoAporte = nITEmpresaRealizoAporte;
		this.tarifaAporte = tarifaAporte;
		this.valorAporteObligatorio = valorAporteObligatorio;
	}
	
	public BigDecimal getTarifaAporte() {
		return this.tarifaAporte;
	}

	public void setTarifaAporte(BigDecimal tarifaAporte) {
		this.tarifaAporte = tarifaAporte;
	}

	public BigDecimal getValorAporteObligatorio() {
		return this.valorAporteObligatorio;
	}

	public void setValorAporteObligatorio(BigDecimal valorAporteObligatorio) {
		this.valorAporteObligatorio = valorAporteObligatorio;
	}

	/**
	 * @return the tipoIdentificacionCotizante
	 */
	public String getTipoIdentificacionCotizante() {
		return tipoIdentificacionCotizante;
	}
	/**
	 * @param tipoIdentificacionCotizante the tipoIdentificacionCotizante to set
	 */
	public void setTipoIdentificacionCotizante(String tipoIdentificacionCotizante) {
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
	}
	/**
	 * @return the numeroIdentificacionCotizante
	 */
	public String getNumeroIdentificacionCotizante() {
		return numeroIdentificacionCotizante;
	}
	/**
	 * @param numeroIdentificacionCotizante the numeroIdentificacionCotizante to set
	 */
	public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
	}
	/**
	 * @return the periodoAporte
	 */
	public String getPeriodoAporte() {
		return periodoAporte;
	}
	/**
	 * @param periodoAporte the periodoAporte to set
	 */
	public void setPeriodoAporte(String periodoAporte) {
		this.periodoAporte = periodoAporte;
	}
	/**
	 * @return the diasCotizadosPeriodo
	 */
	public Short getDiasCotizadosPeriodo() {
		return diasCotizadosPeriodo;
	}
	/**
	 * @param diasCotizadosPeriodo the diasCotizadosPeriodo to set
	 */
	public void setDiasCotizadosPeriodo(Short diasCotizadosPeriodo) {
		this.diasCotizadosPeriodo = diasCotizadosPeriodo;
	}
	/**
	 * @return the tipoCotizante
	 */
	public Short getTipoCotizante() {
		return tipoCotizante;
	}
	/**
	 * @param tipoCotizante the tipoCotizante to set
	 */
	public void setTipoCotizante(Short tipoCotizante) {
		this.tipoCotizante = tipoCotizante;
	}
	/**
	 * @return the codigoCCFDondeRealizoAporte
	 */
	public String getCodigoCCFDondeRealizoAporte() {
		return codigoCCFDondeRealizoAporte;
	}
	/**
	 * @param codigoCCFDondeRealizoAporte the codigoCCFDondeRealizoAporte to set
	 */
	public void setCodigoCCFDondeRealizoAporte(String codigoCCFDondeRealizoAporte) {
		this.codigoCCFDondeRealizoAporte = codigoCCFDondeRealizoAporte;
	}
	/**
	 * @return the empresaDondeRealizoAporte
	 */
	public String getEmpresaDondeRealizoAporte() {
		return empresaDondeRealizoAporte;
	}
	/**
	 * @param empresaDondeRealizoAporte the empresaDondeRealizoAporte to set
	 */
	public void setEmpresaDondeRealizoAporte(String empresaDondeRealizoAporte) {
		this.empresaDondeRealizoAporte = empresaDondeRealizoAporte;
	}
	/**
	 * @return the nITEmpresaRealizoAporte
	 */
	public String getnITEmpresaRealizoAporte() {
		return nITEmpresaRealizoAporte;
	}
	/**
	 * @param nITEmpresaRealizoAporte the nITEmpresaRealizoAporte to set
	 */
	public void setnITEmpresaRealizoAporte(String nITEmpresaRealizoAporte) {
		this.nITEmpresaRealizoAporte = nITEmpresaRealizoAporte;
	}

	
	public int compararPeriodos(String periodo) {
		return this.periodoAporte.compareTo(periodo);
	}
	
	public int compararDiasCotizados(Short dias) {
		return this.diasCotizadosPeriodo.compareTo(dias);
	}


}
