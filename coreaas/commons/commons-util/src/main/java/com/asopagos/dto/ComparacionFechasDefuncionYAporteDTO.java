package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.dto.modelo.PersonaModeloDTO;

/**
 * @author squintero
 */
public class ComparacionFechasDefuncionYAporteDTO implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private PersonaModeloDTO persona;
	private Long fechaRegistroAporte;

	/**
	 * 
	 */
	public ComparacionFechasDefuncionYAporteDTO() {
	}

	/**
	 * @param persona
	 * @param fechaRegistroAporte
	 */
	public ComparacionFechasDefuncionYAporteDTO(PersonaModeloDTO persona, Long fechaRegistroAporte) {
		this.persona = persona;
		this.fechaRegistroAporte = fechaRegistroAporte;
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

	/**
	 * Método que retorna el valor de fechaRegistroAporte.
	 * @return valor de fechaRegistroAporte.
	 */
	public Long getFechaRegistroAporte() {
		return fechaRegistroAporte;
	}

	/**
	 * Método encargado de modificar el valor de fechaRegistroAporte.
	 * @param valor para modificar fechaRegistroAporte.
	 */
	public void setFechaRegistroAporte(Long fechaRegistroAporte) {
		this.fechaRegistroAporte = fechaRegistroAporte;
	}
}