package com.asopagos.clienteanibol.dto;

public class ConsultasTarjetaDTO {

	private String idProceso;
	private String idEntidad;
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	private String tarjeta;
	
	public String getIdProceso() {
		return idProceso;
	}
	
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}
	
	
	
}
