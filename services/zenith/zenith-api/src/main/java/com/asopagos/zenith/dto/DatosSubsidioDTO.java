package com.asopagos.zenith.dto;

import java.io.Serializable;

public class DatosSubsidioDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer identificadorSubsidio;
	private String codigoCCF;
	private Short codigoAQuienSeOtorgoSubsidio;
	private String fechaAsignacionSubsidio;
	private String valor;
	private Short codigoTipoSubsidio;
	private Short estado;
	private Short departamentoRecepcion;
	private Short municipioRecepcion;
	private String fechaEntregaUltimoSubsidio;
	private String tipoIdentificacionBeneficiario;
	private String numeroIdentificacionBeneficiario;
	private String generoBeneficiario;
	private String fechaNacimientoBeneficiario;
	private String primerApellidoBeneficiario;
	private String segundoApellidoBeneficiario;
	private String primerNombreBeneficiario;
	private String segundoNombreBeneficiario;
	
	/**
	 * 
	 */
	public DatosSubsidioDTO() {
	}

	/**
	 * @param identificadorSubsidio
	 * @param codigoCCF
	 * @param codigoAQuienSeOtorgoSubsidio
	 * @param fechaAsignacionSubsidio
	 * @param valor
	 * @param codigoTipoSubsidio
	 * @param estado
	 * @param departamentoRecepcion
	 * @param municipioRecepcion
	 * @param fechaEntregaUltimoSubsidio
	 * @param tipoIdentificacionBeneficiario
	 * @param numeroIdentificacionBeneficiario
	 * @param generoBeneficiario
	 * @param fechaNacimientoBeneficiario
	 * @param primerApellidoBeneficiario
	 * @param segundoApellidoBeneficiario
	 * @param primerNombreBeneficiario
	 * @param segundoNombreBeneficiario
	 */
	public DatosSubsidioDTO(Integer identificadorSubsidio, String codigoCCF, Short codigoAQuienSeOtorgoSubsidio,
			String fechaAsignacionSubsidio, String valor, Short codigoTipoSubsidio, Short estado,
			Short departamentoRecepcion, Short municipioRecepcion, String fechaEntregaUltimoSubsidio,
			String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario, String generoBeneficiario,
			String fechaNacimientoBeneficiario, String primerApellidoBeneficiario, String segundoApellidoBeneficiario,
			String primerNombreBeneficiario, String segundoNombreBeneficiario) {
		this.identificadorSubsidio = identificadorSubsidio;
		this.codigoCCF = codigoCCF;
		this.codigoAQuienSeOtorgoSubsidio = codigoAQuienSeOtorgoSubsidio;
		this.fechaAsignacionSubsidio = (fechaAsignacionSubsidio != null && fechaAsignacionSubsidio.length()>10) ? fechaAsignacionSubsidio.substring(0,10) : null;
		this.valor = valor;
		this.codigoTipoSubsidio = codigoTipoSubsidio;
		this.estado = estado;
		this.departamentoRecepcion = departamentoRecepcion;
		this.municipioRecepcion = municipioRecepcion;
		this.fechaEntregaUltimoSubsidio = (fechaEntregaUltimoSubsidio != null && fechaEntregaUltimoSubsidio.length()>10) ? fechaEntregaUltimoSubsidio.substring(0,10) : null;
		this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
		this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
		this.generoBeneficiario = generoBeneficiario;
		this.fechaNacimientoBeneficiario = fechaNacimientoBeneficiario;
		this.primerApellidoBeneficiario = primerApellidoBeneficiario;
		this.segundoApellidoBeneficiario = segundoApellidoBeneficiario;
		this.primerNombreBeneficiario = primerNombreBeneficiario;
		this.segundoNombreBeneficiario = segundoNombreBeneficiario;
	}

	/**
	 * @return the identificadorSubsidio
	 */
	public Integer getIdentificadorSubsidio() {
		return identificadorSubsidio;
	}

	/**
	 * @param identificadorSubsidio the identificadorSubsidio to set
	 */
	public void setIdentificadorSubsidio(Integer identificadorSubsidio) {
		this.identificadorSubsidio = identificadorSubsidio;
	}

	/**
	 * @return the codigoCCF
	 */
	public String getCodigoCCF() {
		return codigoCCF;
	}

	/**
	 * @param codigoCCF the codigoCCF to set
	 */
	public void setCodigoCCF(String codigoCCF) {
		this.codigoCCF = codigoCCF;
	}

	/**
	 * @return the codigoAQuienSeOtorgoSubsidio
	 */
	public Short getCodigoAQuienSeOtorgoSubsidio() {
		return codigoAQuienSeOtorgoSubsidio;
	}

	/**
	 * @param codigoAQuienSeOtorgoSubsidio the codigoAQuienSeOtorgoSubsidio to set
	 */
	public void setCodigoAQuienSeOtorgoSubsidio(Short codigoAQuienSeOtorgoSubsidio) {
		this.codigoAQuienSeOtorgoSubsidio = codigoAQuienSeOtorgoSubsidio;
	}

	/**
	 * @return the fechaAsignacionSubsidio
	 */
	public String getFechaAsignacionSubsidio() {
		return fechaAsignacionSubsidio;
	}

	/**
	 * @param fechaAsignacionSubsidio the fechaAsignacionSubsidio to set
	 */
	public void setFechaAsignacionSubsidio(String fechaAsignacionSubsidio) {
		this.fechaAsignacionSubsidio = fechaAsignacionSubsidio;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the codigoTipoSubsidio
	 */
	public Short getCodigoTipoSubsidio() {
		return codigoTipoSubsidio;
	}

	/**
	 * @param codigoTipoSubsidio the codigoTipoSubsidio to set
	 */
	public void setCodigoTipoSubsidio(Short codigoTipoSubsidio) {
		this.codigoTipoSubsidio = codigoTipoSubsidio;
	}

	/**
	 * @return the estado
	 */
	public Short getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Short estado) {
		this.estado = estado;
	}

	/**
	 * @return the departamentoRecepcion
	 */
	public Short getDepartamentoRecepcion() {
		return departamentoRecepcion;
	}

	/**
	 * @param departamentoRecepcion the departamentoRecepcion to set
	 */
	public void setDepartamentoRecepcion(Short departamentoRecepcion) {
		this.departamentoRecepcion = departamentoRecepcion;
	}

	/**
	 * @return the municipioRecepcion
	 */
	public Short getMunicipioRecepcion() {
		return municipioRecepcion;
	}

	/**
	 * @param municipioRecepcion the municipioRecepcion to set
	 */
	public void setMunicipioRecepcion(Short municipioRecepcion) {
		this.municipioRecepcion = municipioRecepcion;
	}

	/**
	 * @return the fechaEntregaUltimoSubsidio
	 */
	public String getFechaEntregaUltimoSubsidio() {
		return fechaEntregaUltimoSubsidio;
	}

	/**
	 * @param fechaEntregaUltimoSubsidio the fechaEntregaUltimoSubsidio to set
	 */
	public void setFechaEntregaUltimoSubsidio(String fechaEntregaUltimoSubsidio) {
		this.fechaEntregaUltimoSubsidio = fechaEntregaUltimoSubsidio;
	}

	/**
	 * @return the tipoIdentificacionBeneficiario
	 */
	public String getTipoIdentificacionBeneficiario() {
		return tipoIdentificacionBeneficiario;
	}

	/**
	 * @param tipoIdentificacionBeneficiario the tipoIdentificacionBeneficiario to set
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
	 * @param numeroIdentificacionBeneficiario the numeroIdentificacionBeneficiario to set
	 */
	public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
		this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
	}

	/**
	 * @return the generoBeneficiario
	 */
	public String getGeneroBeneficiario() {
		return generoBeneficiario;
	}

	/**
	 * @param generoBeneficiario the generoBeneficiario to set
	 */
	public void setGeneroBeneficiario(String generoBeneficiario) {
		this.generoBeneficiario = generoBeneficiario;
	}

	/**
	 * @return the fechaNacimientoBeneficiario
	 */
	public String getFechaNacimientoBeneficiario() {
		return fechaNacimientoBeneficiario;
	}

	/**
	 * @param fechaNacimientoBeneficiario the fechaNacimientoBeneficiario to set
	 */
	public void setFechaNacimientoBeneficiario(String fechaNacimientoBeneficiario) {
		this.fechaNacimientoBeneficiario = fechaNacimientoBeneficiario;
	}

	/**
	 * @return the primerApellidoBeneficiario
	 */
	public String getPrimerApellidoBeneficiario() {
		return primerApellidoBeneficiario;
	}

	/**
	 * @param primerApellidoBeneficiario the primerApellidoBeneficiario to set
	 */
	public void setPrimerApellidoBeneficiario(String primerApellidoBeneficiario) {
		this.primerApellidoBeneficiario = primerApellidoBeneficiario;
	}

	/**
	 * @return the segundoApellidoBeneficiario
	 */
	public String getSegundoApellidoBeneficiario() {
		return segundoApellidoBeneficiario;
	}

	/**
	 * @param segundoApellidoBeneficiario the segundoApellidoBeneficiario to set
	 */
	public void setSegundoApellidoBeneficiario(String segundoApellidoBeneficiario) {
		this.segundoApellidoBeneficiario = segundoApellidoBeneficiario;
	}

	/**
	 * @return the primerNombreBeneficiario
	 */
	public String getPrimerNombreBeneficiario() {
		return primerNombreBeneficiario;
	}

	/**
	 * @param primerNombreBeneficiario the primerNombreBeneficiario to set
	 */
	public void setPrimerNombreBeneficiario(String primerNombreBeneficiario) {
		this.primerNombreBeneficiario = primerNombreBeneficiario;
	}

	/**
	 * @return the segundoNombreBeneficiario
	 */
	public String getSegundoNombreBeneficiario() {
		return segundoNombreBeneficiario;
	}

	/**
	 * @param segundoNombreBeneficiario the segundoNombreBeneficiario to set
	 */
	public void setSegundoNombreBeneficiario(String segundoNombreBeneficiario) {
		this.segundoNombreBeneficiario = segundoNombreBeneficiario;
	}
}
