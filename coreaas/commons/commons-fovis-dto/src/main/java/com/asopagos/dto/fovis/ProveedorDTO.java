package com.asopagos.dto.fovis;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.ProveedorModeloDTO;

/**
 * <b>Descripción: </b> DTO que representa los datos de un proveedor <br/>
 * 
 * 
 * @author 
 * Lina M.
 */
@XmlRootElement
public class ProveedorDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2674163909685868420L;

	/**
	 * Información del proveedor
	 */
	private ProveedorModeloDTO proveedor;

	/**
	 * Indica si está registrado como proveedor
	 */
	private Boolean esProveedor;
	
	/**
	 * Constructor que recibe los datos del modelo de proveedor
	 * @param proveedor
	 */
	public ProveedorDTO(ProveedorModeloDTO proveedor) {
		
		this.proveedor = proveedor;
	}
	
	/**
	 * Constructor por defecto
	 */
	public ProveedorDTO() {
		
	}

	/**
	 * Obtiene el valor de esOferente
	 * 
	 * @return El valor de esOferente
	 */
	public Boolean getEsProveedor() {
		return esProveedor;
	}

	/**
	 * Establece el valor de esProveeor
	 * 
	 * @param esProveedor
	 *            El valor de esProveeor por asignar
	 */
	public void setEsProveedor(Boolean esProveedor) {
		this.esProveedor = esProveedor;
	}

	/**
	 * Obtiene el valor de oferente
	 * 
	 * @return El valor de oferente
	 */
	public ProveedorModeloDTO getProveedor() {
		return proveedor;
	}

	/**
	 * Establece el valor de oferente
	 * 
	 * @param proveedor
	 *            El valor de proveedor por asignar
	 */
	public void setProveedor(ProveedorModeloDTO proveedor) {
		this.proveedor = proveedor;
	}
}
