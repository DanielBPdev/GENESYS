package com.asopagos.fovis.composite.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción: </b> DTO que representa el mensaje de respuesta de una
 * validación de oferente <br/>
 * <b>Historia de Usuario: HU-055 </b>
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class ValidacionOferenteDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2674163909685868420L;

	/**
	 * Nombre de la validación realizada
	 */
	private String nombreValidacion;

	/**
	 * Motivo de la inconsistencia
	 */
	private String motivoInconsistencia;

	/**
	 * Indica si continúa con el registro del oferente
	 */
	private Boolean continuaRegistro;

	/**
	 * Indica si el registro del oferente debe ser por decisión del usuario
	 */
	private Boolean presentaValidacionAlerta;

	/**
	 * Constructor
	 * 
	 * @param nombreValidacion
	 *            Nombre de la validación
	 * @param motivoInconsistencia
	 *            Motivo de la inconsistencia
	 * @param continuaRegistro
	 *            Indica si continúa con el registro del oferente
	 * @param presentaValidacionAlerta
	 *            Indica si el registro del oferente debe ser por decisión del
	 *            usuario
	 */
	public ValidacionOferenteDTO(String nombreValidacion, String motivoInconsistencia, Boolean continuaRegistro, Boolean presentaValidacionAlerta) {
		super();
		this.nombreValidacion = nombreValidacion;
		this.motivoInconsistencia = motivoInconsistencia;
		this.continuaRegistro = continuaRegistro;
		this.presentaValidacionAlerta = presentaValidacionAlerta;
	}

	/**
	 * Obtiene el valor de nombreValidacion
	 * 
	 * @return El valor de nombreValidacion
	 */
	public String getNombreValidacion() {
		return nombreValidacion;
	}

	/**
	 * Establece el valor de nombreValidacion
	 * 
	 * @param nombreValidacion
	 *            El valor de nombreValidacion por asignar
	 */
	public void setNombreValidacion(String nombreValidacion) {
		this.nombreValidacion = nombreValidacion;
	}

	/**
	 * Obtiene el valor de motivoInconsistencia
	 * 
	 * @return El valor de motivoInconsistencia
	 */
	public String getMotivoInconsistencia() {
		return motivoInconsistencia;
	}

	/**
	 * Establece el valor de motivoInconsistencia
	 * 
	 * @param motivoInconsistencia
	 *            El valor de motivoInconsistencia por asignar
	 */
	public void setMotivoInconsistencia(String motivoInconsistencia) {
		this.motivoInconsistencia = motivoInconsistencia;
	}

	/**
	 * Obtiene el valor de continuaRegistro
	 * 
	 * @return El valor de continuaRegistro
	 */
	public Boolean getContinuaRegistro() {
		return continuaRegistro;
	}

	/**
	 * Establece el valor de continuaRegistro
	 * 
	 * @param continuaRegistro
	 *            El valor de continuaRegistro por asignar
	 */
	public void setContinuaRegistro(Boolean continuaRegistro) {
		this.continuaRegistro = continuaRegistro;
	}

	/**
	 * Obtiene el valor de presentaValidacionAlerta
	 * 
	 * @return El valor de presentaValidacionAlerta
	 */
	public Boolean getPresentaValidacionAlerta() {
		return presentaValidacionAlerta;
	}

	/**
	 * Establece el valor de presentaValidacionAlerta
	 * 
	 * @param presentaValidacionAlerta
	 *            El valor de presentaValidacionAlerta por asignar
	 */
	public void setPresentaValidacionAlerta(Boolean presentaValidacionAlerta) {
		this.presentaValidacionAlerta = presentaValidacionAlerta;
	}
	
	public ValidacionOferenteDTO() {
		// TODO Auto-generated constructor stub
	}
}
