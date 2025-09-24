package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;
import com.asopagos.dto.EmpleadorDTO;

public class RadicarSolicitudAfiliacionDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private byte[] formulario;
	
	private String numeroRadicado;
	
	private Long idTarea;
	
	private String email;

	private Long idInstanciaProceso;
	
	private EmpleadorDTO empleador;
	

	/**
	 * @return the formulario
	 */
	public byte[] getFormulario() {
		return formulario;
	}

	/**
	 * @param formulario the formulario to set
	 */
	public void setFormulario(byte[] formulario) {
		this.formulario = formulario;
	}

	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * @return the idTarea
	 */
	public Long getIdTarea() {
		return idTarea;
	}

	/**
	 * @param idTarea the idTarea to set
	 */
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdInstanciaProceso() {
		// TODO Auto-generated method stub
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(Long idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	/**
	 * @return the empleador
	 */
	public EmpleadorDTO getEmpleador() {
		return empleador;
	}

	/**
	 * @param empleador the empleador to set
	 */
	public void setEmpleador(EmpleadorDTO empleador) {
		this.empleador = empleador;
	}
}
