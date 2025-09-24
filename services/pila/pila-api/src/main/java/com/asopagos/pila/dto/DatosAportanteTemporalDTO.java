package com.asopagos.pila.dto;

import java.io.Serializable;

import com.asopagos.entidades.pila.temporal.TemAportante;

/**
 * DTO con el constructor de la consulta de los datos de los aportantes
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
public class DatosAportanteTemporalDTO implements Serializable {
	private static final long serialVersionUID = 5165142646354283L;

	private TemAportante aportante;
	private Long idPersona;
	private Long idEmpresa;
	private Long idEmpleador;
	private Boolean tieneCotizanteDependienteReintegrable;
	private String estadoEmpleador;
	private String periodoAporte;
	private String modalidadRecaudoAporte;

	public DatosAportanteTemporalDTO(TemAportante aportante, Long idPersona, Long idEmpresa, Long idEmpleador,
			String estadoEmpleador, Boolean tieneCotizanteDependienteReintegrable, String periodoAporte,
			String modalidadRecaudoAporte) {
		this.aportante = aportante;
		this.idPersona = idPersona;
		this.idEmpresa = idEmpresa;
		this.idEmpleador = idEmpleador;
		this.estadoEmpleador = estadoEmpleador;
		this.tieneCotizanteDependienteReintegrable = tieneCotizanteDependienteReintegrable;
		this.periodoAporte = periodoAporte;
		this.modalidadRecaudoAporte = modalidadRecaudoAporte;
	}

	/**
	 * @return the aportante
	 */
	public TemAportante getAportante() {
		return aportante;
	}

	/**
	 * @param aportante the aportante to set
	 */
	public void setAportante(TemAportante aportante) {
		this.aportante = aportante;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the idEmpresa
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	/**
	 * @return the tieneCotizanteDependienteReintegrable
	 */
	public Boolean getTieneCotizanteDependienteReintegrable() {
		return tieneCotizanteDependienteReintegrable;
	}

	/**
	 * @param tieneCotizanteDependienteReintegrable the
	 *                                              tieneCotizanteDependienteReintegrable
	 *                                              to set
	 */
	public void setTieneCotizanteDependienteReintegrable(Boolean tieneCotizanteDependienteReintegrable) {
		this.tieneCotizanteDependienteReintegrable = tieneCotizanteDependienteReintegrable;
	}

	/**
	 * @return the estadoEmpleador
	 */
	public String getEstadoEmpleador() {
		return estadoEmpleador;
	}

	/**
	 * @param estadoEmpleador the estadoEmpleador to set
	 */
	public void setEstadoEmpleador(String estadoEmpleador) {
		this.estadoEmpleador = estadoEmpleador;
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

	public String getModalidadRecaudoAporte() {
		return modalidadRecaudoAporte;
	}

	public void setModalidadRecaudoAporte(String modalidadRecaudoAporte) {
		this.modalidadRecaudoAporte = modalidadRecaudoAporte;
	}

}
