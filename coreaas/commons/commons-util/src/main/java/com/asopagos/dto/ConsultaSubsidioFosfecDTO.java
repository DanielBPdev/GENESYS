package com.asopagos.dto;
import java.io.Serializable;

public class ConsultaSubsidioFosfecDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	private String codigoCCF;
	
	private String idProceso;
	private String usuario;
	private String fecha_hora;
	private String maquina;
	
	public ConsultaSubsidioFosfecDTO (){}
	

	/**
	 * @return the tipoIdentificacion
	 */
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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
	 * @return the idProceso
	 */
	public String getIdProceso() {
		return idProceso;
	}

	/**
	 * @param idProceso the idProceso to set
	 */
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the fecha_hora
	 */
	public String getFecha_hora() {
		return fecha_hora;
	}

	/**
	 * @param fecha_hora the fecha_hora to set
	 */
	public void setFecha_hora(String fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	/**
	 * @return the maquina
	 */
	public String getMaquina() {
		return maquina;
	}

	/**
	 * @param maquina the maquina to set
	 */
	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return tipoIdentificacion + "," + numeroIdentificacion + "," + codigoCCF;
	}
}