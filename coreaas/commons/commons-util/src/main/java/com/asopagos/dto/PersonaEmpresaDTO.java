package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;

/**
 * @author squintero
 *
 */
public class PersonaEmpresaDTO implements Serializable {

	
	private EmpleadorModeloDTO empleador;
	private PersonaModeloDTO persona;
	
	
	/**
	 * 
	 */
	public PersonaEmpresaDTO() {
	}

	/**
	 * @param empleador
	 * @param persona
	 */
	public PersonaEmpresaDTO(EmpleadorModeloDTO empleador, PersonaModeloDTO persona) {
		this.empleador = empleador;
		this.persona = persona;
	}

	/**
	 * Método que retorna el valor de empleador.
	 * @return valor de empleador.
	 */
	public EmpleadorModeloDTO getEmpleador() {
		return empleador;
	}

	/**
	 * Método encargado de modificar el valor de empleador.
	 * @param valor para modificar empleador.
	 */
	public void setEmpleador(EmpleadorModeloDTO empleador) {
		this.empleador = empleador;
	}

	/**
	 * Método que retorna el valor de persona.
	 * @return valor de persona.
	 */
	public PersonaModeloDTO getPersona() {
		return persona;
	}

	/**
	 * Método encargado de modificar el valor de persona.
	 * @param valor para modificar persona.
	 */
	public void setPersona(PersonaModeloDTO persona) {
		this.persona = persona;
	}
}
	