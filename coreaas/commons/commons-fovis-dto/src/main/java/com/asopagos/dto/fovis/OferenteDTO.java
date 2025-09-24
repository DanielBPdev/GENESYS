package com.asopagos.dto.fovis;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.OferenteModeloDTO;

/**
 * <b>Descripción: </b> DTO que representa los datos de un oferente <br/>
 * <b>Historia de Usuario: HU-054 </b>
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class OferenteDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2674163909685868420L;

	/**
	 * Información del oferente
	 */
	private OferenteModeloDTO oferente;

	/**
	 * Indica si está registrado como oferente
	 */
	private Boolean esOferente;
	
	/**
	 * Constructor que recibe los datos del modelo de oferente
	 * @param oferente
	 */
	public OferenteDTO(OferenteModeloDTO oferente) {
		
		this.oferente = oferente;
	}
	
	/**
	 * Constructor por defecto
	 */
	public OferenteDTO() {
		
	}

	/**
	 * Obtiene el valor de esOferente
	 * 
	 * @return El valor de esOferente
	 */
	public Boolean getEsOferente() {
		return esOferente;
	}

	/**
	 * Establece el valor de esOferente
	 * 
	 * @param esOferente
	 *            El valor de esOferente por asignar
	 */
	public void setEsOferente(Boolean esOferente) {
		this.esOferente = esOferente;
	}

	/**
	 * Obtiene el valor de oferente
	 * 
	 * @return El valor de oferente
	 */
	public OferenteModeloDTO getOferente() {
		return oferente;
	}

	/**
	 * Establece el valor de oferente
	 * 
	 * @param oferente
	 *            El valor de oferente por asignar
	 */
	public void setOferente(OferenteModeloDTO oferente) {
		this.oferente = oferente;
	}
}
