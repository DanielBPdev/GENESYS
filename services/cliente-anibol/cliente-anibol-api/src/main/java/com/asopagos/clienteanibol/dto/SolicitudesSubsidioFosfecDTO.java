package com.asopagos.clienteanibol.dto;

import java.io.Serializable;
import java.util.Date;

import com.asopagos.clienteanibol.enums.VerificacionRequisitosEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Contiene informacion sobre la solicitudes de subsidio de desempleo que se han
 * radicado con respecto a un afiliado
 * 
 * @author jbuitrago
 *
 */
public class SolicitudesSubsidioFosfecDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoIdentificacionEnum tipoIdentificacionCesante;
	private String numeroIdentificacionCesante;
	private TipoAfiliadoEnum tipoVinculacionCCF;
	private String codigoUltimaCCF;

	/** Fecha en la que se radica la solicitud de beneficio FOSFEC en la CCF */
	private Long fechaPostulacionMPC;

	/** Codigo de la entidad administradora de salud */
	private String administradorSalud;

	private Boolean postulaPension;

	/** Codigo de la entidad administradora de pension */
	private String administradorPension;

	private Boolean subsidioEconomicoCuotaMonetaria;
	private Boolean ahorroCesantiasMPC;

	/** Codigo de la entidad administradora de cesantias */
	private String administradorFondoCesantias;

	private Integer porcentajeAhorroCesantias;
	private Boolean postulaBonoAlimentacion;
	private VerificacionRequisitosEnum verificacionRequisitos;
	private Long fechaVerificacionPostulacion;
	private Integer vigenciaPostulante;

	public TipoIdentificacionEnum getTipoIdentificacionCesante() {
		return tipoIdentificacionCesante;
	}

	public void setTipoIdentificacionCesante(TipoIdentificacionEnum tipoIdentificacionCesante) {
		this.tipoIdentificacionCesante = tipoIdentificacionCesante;
	}

	public String getNumeroIdentificacionCesante() {
		return numeroIdentificacionCesante;
	}

	public void setNumeroIdentificacionCesante(String numeroIdentificacionCesante) {
		this.numeroIdentificacionCesante = numeroIdentificacionCesante;
	}

	public TipoAfiliadoEnum getTipoVinculacionCCF() {
		return tipoVinculacionCCF;
	}

	public void setTipoVinculacionCCF(TipoAfiliadoEnum tipoVinculacionCCF) {
		this.tipoVinculacionCCF = tipoVinculacionCCF;
	}

	public String getCodigoUltimaCCF() {
		return codigoUltimaCCF;
	}

	public void setCodigoUltimaCCF(String codigoUltimaCCF) {
		this.codigoUltimaCCF = codigoUltimaCCF;
	}

	public Long getFechaPostulacionMPC() {
		return fechaPostulacionMPC;
	}

	public void setFechaPostulacionMPC(Long fechaPostulacionMPC) {
		this.fechaPostulacionMPC = fechaPostulacionMPC;
	}

	public String getAdministradorSalud() {
		return administradorSalud;
	}

	public void setAdministradorSalud(String administradorSalud) {
		this.administradorSalud = administradorSalud;
	}

	public Boolean getPostulaPension() {
		return postulaPension;
	}

	public void setPostulaPension(Boolean postulaPension) {
		this.postulaPension = postulaPension;
	}

	public String getAdministradorPension() {
		return administradorPension;
	}

	public void setAdministradorPension(String administradorPension) {
		this.administradorPension = administradorPension;
	}

	public Boolean getSubsidioEconomicoCuotaMonetaria() {
		return subsidioEconomicoCuotaMonetaria;
	}

	public void setSubsidioEconomicoCuotaMonetaria(Boolean subsidioEconomicoCuotaMonetaria) {
		this.subsidioEconomicoCuotaMonetaria = subsidioEconomicoCuotaMonetaria;
	}

	public Boolean getAhorroCesantiasMPC() {
		return ahorroCesantiasMPC;
	}

	public void setAhorroCesantiasMPC(Boolean ahorroCesantiasMPC) {
		this.ahorroCesantiasMPC = ahorroCesantiasMPC;
	}

	public String getAdministradorFondoCesantias() {
		return administradorFondoCesantias;
	}

	public void setAdministradorFondoCesantias(String administradorFondoCesantias) {
		this.administradorFondoCesantias = administradorFondoCesantias;
	}

	public Integer getPorcentajeAhorroCesantias() {
		return porcentajeAhorroCesantias;
	}

	public void setPorcentajeAhorroCesantias(Integer porcentajeAhorroCesantias) {
		this.porcentajeAhorroCesantias = porcentajeAhorroCesantias;
	}

	public Boolean getPostulaBonoAlimentacion() {
		return postulaBonoAlimentacion;
	}

	public void setPostulaBonoAlimentacion(Boolean postulaBonoAlimentacion) {
		this.postulaBonoAlimentacion = postulaBonoAlimentacion;
	}

	public VerificacionRequisitosEnum getVerificacionRequisitos() {
		return verificacionRequisitos;
	}

	public void setVerificacionRequisitos(VerificacionRequisitosEnum verificacionRequisitos) {
		this.verificacionRequisitos = verificacionRequisitos;
	}

	public Long getFechaVerificacionPostulacion() {
		return fechaVerificacionPostulacion;
	}

	public void setFechaVerificacionPostulacion(Long fechaVerificacionPostulacion) {
		this.fechaVerificacionPostulacion = fechaVerificacionPostulacion;
	}

	public Integer getVigenciaPostulante() {
		return vigenciaPostulante;
	}

	public void setVigenciaPostulante(Integer vigenciaPostulante) {
		this.vigenciaPostulante = vigenciaPostulante;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SolicitudesSubsidioFosfecDTO() {
	}

	public SolicitudesSubsidioFosfecDTO(TipoIdentificacionEnum tipoIdentificacionCesante,
			String numeroIdentificacionCesante, TipoAfiliadoEnum tipoVinculacionCCF, String codigoUltimaCCF,
			Long fechaPostulacionMPC, String administradorSalud, Boolean postulaPension, String administradorPension,
			Boolean subsidioEconomicoCuotaMonetaria, Boolean ahorroCesantiasMPC, String administradorFondoCesantias,
			Integer porcentajeAhorroCesantias, Boolean postulaBonoAlimentacion,
			VerificacionRequisitosEnum verificacionRequisitos, Long fechaVerificacionPostulacion,
			Integer vigenciaPostulante) {
		this.tipoIdentificacionCesante = tipoIdentificacionCesante;
		this.numeroIdentificacionCesante = numeroIdentificacionCesante;
		this.tipoVinculacionCCF = tipoVinculacionCCF;
		this.codigoUltimaCCF = codigoUltimaCCF;
		this.fechaPostulacionMPC = fechaPostulacionMPC;
		this.administradorSalud = administradorSalud;
		this.postulaPension = postulaPension;
		this.administradorPension = administradorPension;
		this.subsidioEconomicoCuotaMonetaria = subsidioEconomicoCuotaMonetaria;
		this.ahorroCesantiasMPC = ahorroCesantiasMPC;
		this.administradorFondoCesantias = administradorFondoCesantias;
		this.porcentajeAhorroCesantias = porcentajeAhorroCesantias;
		this.postulaBonoAlimentacion = postulaBonoAlimentacion;
		this.verificacionRequisitos = verificacionRequisitos;
		this.fechaVerificacionPostulacion = fechaVerificacionPostulacion;
		this.vigenciaPostulante = vigenciaPostulante;
	}

}
