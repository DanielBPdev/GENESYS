package com.asopagos.dto;

import java.io.Serializable;


public class SolicitudSubsidioFosfecDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tipoVinculacion;
	private String ultimaCcf;
	private String fechaPostulacion;
	private String adminSalud;
	private String postulaPension;
	private String adminPension;
	private String subsiodioEconomico;
	private String ahorroCesantias;
	private String fondoAdminCesantias;
	private String postulaBonoAlimentacion;
	private String verificacionRequisitos;
	private String fechaVerificacion;
	private String vigencia;
	

	/**
	 * 
	 */
	public SolicitudSubsidioFosfecDTO() {
	}
	
	/**
	 * @param tipoVinculacion
	 * @param ultimaCcf
	 * @param fechaPostulacion
	 * @param adminSalud
	 * @param postulaPension
	 * @param adminPension
	 * @param subsiodioEconomico
	 * @param ahorroCesantias
	 * @param fondoAdminCesantias
	 * @param postulaBonoAlimentacion
	 * @param verificacionRequisitos
	 * @param fechaVerificacion
	 */
	public SolicitudSubsidioFosfecDTO(String tipoVinculacion, String ultimaCcf, String fechaPostulacion,
			String adminSalud, String postulaPension, String adminPension, String subsiodioEconomico,
			String ahorroCesantias, String fondoAdminCesantias, String postulaBonoAlimentacion,
			String verificacionRequisitos, String fechaVerificacion) {
		this.tipoVinculacion = tipoVinculacion;
		this.ultimaCcf = ultimaCcf;
		this.fechaPostulacion = fechaPostulacion;
		this.adminSalud = adminSalud;
		this.postulaPension = postulaPension;
		this.adminPension = adminPension;
		this.subsiodioEconomico = subsiodioEconomico;
		this.ahorroCesantias = ahorroCesantias;
		this.fondoAdminCesantias = fondoAdminCesantias;
		this.postulaBonoAlimentacion = postulaBonoAlimentacion;
		this.verificacionRequisitos = verificacionRequisitos;
		this.fechaVerificacion = fechaVerificacion;
	}

	/**
	 * @return the tipoVinculacion
	 */
	public String getTipoVinculacion() {
		return tipoVinculacion;
	}
	
	/**
	 * @param tipoVinculacion the tipoVinculacion to set
	 */
	public void setTipoVinculacion(String tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}
	
	/**
	 * @return the ultimaCcf
	 */
	public String getUltimaCcf() {
		return ultimaCcf;
	}
	
	/**
	 * @param ultimaCcf the ultimaCcf to set
	 */
	public void setUltimaCcf(String ultimaCcf) {
		this.ultimaCcf = ultimaCcf;
	}
	
	/**
	 * @return the fechaPostulacion
	 */
	public String getFechaPostulacion() {
		return fechaPostulacion;
	}
	
	/**
	 * @param fechaPostulacion the fechaPostulacion to set
	 */
	public void setFechaPostulacion(String fechaPostulacion) {
		this.fechaPostulacion = fechaPostulacion;
	}
	
	/**
	 * @return the adminSalud
	 */
	public String getAdminSalud() {
		return adminSalud;
	}
	
	/**
	 * @param adminSalud the adminSalud to set
	 */
	public void setAdminSalud(String adminSalud) {
		this.adminSalud = adminSalud;
	}
	
	/**
	 * @return the postulaPension
	 */
	public String getPostulaPension() {
		return postulaPension;
	}
	
	/**
	 * @param postulaPension the postulaPension to set
	 */
	public void setPostulaPension(String postulaPension) {
		this.postulaPension = postulaPension;
	}
	
	/**
	 * @return the adminPension
	 */
	public String getAdminPension() {
		return adminPension;
	}
	
	/**
	 * @param adminPension the adminPension to set
	 */
	public void setAdminPension(String adminPension) {
		this.adminPension = adminPension;
	}
	
	/**
	 * @return the subsiodioEconomico
	 */
	public String getSubsiodioEconomico() {
		return subsiodioEconomico;
	}
	
	/**
	 * @param subsiodioEconomico the subsiodioEconomico to set
	 */
	public void setSubsiodioEconomico(String subsiodioEconomico) {
		this.subsiodioEconomico = subsiodioEconomico;
	}
	
	/**
	 * @return the ahorroCesantias
	 */
	public String getAhorroCesantias() {
		return ahorroCesantias;
	}
	
	/**
	 * @param ahorroCesantias the ahorroCesantias to set
	 */
	public void setAhorroCesantias(String ahorroCesantias) {
		this.ahorroCesantias = ahorroCesantias;
	}
	
	/**
	 * @return the fondoAdminCesantias
	 */
	public String getFondoAdminCesantias() {
		return fondoAdminCesantias;
	}
	
	/**
	 * @param fondoAdminCesantias the fondoAdminCesantias to set
	 */
	public void setFondoAdminCesantias(String fondoAdminCesantias) {
		this.fondoAdminCesantias = fondoAdminCesantias;
	}
	
	/**
	 * @return the postulaBonoAlimentacion
	 */
	public String getPostulaBonoAlimentacion() {
		return postulaBonoAlimentacion;
	}
	
	/**
	 * @param postulaBonoAlimentacion the postulaBonoAlimentacion to set
	 */
	public void setPostulaBonoAlimentacion(String postulaBonoAlimentacion) {
		this.postulaBonoAlimentacion = postulaBonoAlimentacion;
	}
	
	/**
	 * @return the verificacionRequisitos
	 */
	public String getVerificacionRequisitos() {
		return verificacionRequisitos;
	}
	
	/**
	 * @param verificacionRequisitos the verificacionRequisitos to set
	 */
	public void setVerificacionRequisitos(String verificacionRequisitos) {
		this.verificacionRequisitos = verificacionRequisitos;
	}
	
	/**
	 * @return the fechaVerificacion
	 */
	public String getFechaVerificacion() {
		return fechaVerificacion;
	}
	
	/**
	 * @param fechaVerificacion the fechaVerificacion to set
	 */
	public void setFechaVerificacion(String fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}
}
