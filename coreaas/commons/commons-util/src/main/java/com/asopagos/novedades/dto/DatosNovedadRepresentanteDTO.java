package com.asopagos.novedades.dto;

import java.util.List;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;

/**
 * DTO que contiene los datos de Actualización de Representente Legal, Suplente, y Socios
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class DatosNovedadRepresentanteDTO {

	/** Identificación del Empleador*/
	private Long idEmpleador;
	
    /**Persona asociada al representante Legal*/
	private Persona representanteLegal;
	
	/**Persona asociada al representante Legal Suplente*/
	private Persona representanteLegalSuplente;
	
	/**Listado de socios empleador*/
	private List<SocioEmpleador> sociosEmpleador;

	
	
	
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
	 * @return the representanteLegal
	 */
	public Persona getRepresentanteLegal() {
		return representanteLegal;
	}

	/**
	 * @param representanteLegal the representanteLegal to set
	 */
	public void setRepresentanteLegal(Persona representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	/**
	 * @return the representanteLegalSuplente
	 */
	public Persona getRepresentanteLegalSuplente() {
		return representanteLegalSuplente;
	}

	/**
	 * @param representanteLegalSuplente the representanteLegalSuplente to set
	 */
	public void setRepresentanteLegalSuplente(Persona representanteLegalSuplente) {
		this.representanteLegalSuplente = representanteLegalSuplente;
	}

	/**
	 * @return the sociosEmpleador
	 */
	public List<SocioEmpleador> getSociosEmpleador() {
		return sociosEmpleador;
	}

	/**
	 * @param sociosEmpleador the sociosEmpleador to set
	 */
	public void setSociosEmpleador(List<SocioEmpleador> sociosEmpleador) {
		this.sociosEmpleador = sociosEmpleador;
	}
}
