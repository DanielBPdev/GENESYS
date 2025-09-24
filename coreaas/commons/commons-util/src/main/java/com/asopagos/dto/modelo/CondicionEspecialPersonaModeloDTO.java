package com.asopagos.dto.modelo;

import java.io.Serializable;

import com.asopagos.entidades.ccf.personas.CondicionEspecialPersona;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de la tabla
 * <code>CondicionEspecialPersona</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class CondicionEspecialPersonaModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -8013069654864678043L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long id;

	/**
	 * Nombre de la condición especial
	 */
	private NombreCondicionEspecialEnum nombreCondicion;

	/**
	 * Persona asociada a la Condición Especial Persona
	 */
	private Long idPersona;

	/**
	 * Constructor
	 */
	public CondicionEspecialPersonaModeloDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param nombreCondicionEspecial
	 *            Nombre de la condición especial
	 * @param idPersona
	 *            Identificador de la persona -> Referencia a la tabla
	 *            <code>Persona</code>
	 */
	public CondicionEspecialPersonaModeloDTO(NombreCondicionEspecialEnum nombreCondicionEspecial, Long idPersona) {
		super();
		this.nombreCondicion = nombreCondicionEspecial;
		this.idPersona = idPersona;
	}

	/**
	 * Método que convierte una entidad <code>CondicionEspecialPersona</code> al
	 * DTO
	 * 
	 * @param condicionEspecialPersona
	 *            La entidad <code>CondicionEspecialPersona</code>
	 */
	public void convertToDTO(CondicionEspecialPersona condicionEspecialPersona) {
		this.setId(condicionEspecialPersona.getId());
		this.setIdPersona(condicionEspecialPersona.getIdPersona());
		this.setNombreCondicion(condicionEspecialPersona.getNombreCondicion());
	}

	/**
	 * Método que convierte el DTO a entidad
	 * 
	 * @return La entidad <code>CondicionEspecialPersona</code> equivalente
	 */
	public CondicionEspecialPersona convertToEntity() {
		CondicionEspecialPersona condicionEspecialPersona = new CondicionEspecialPersona();
		condicionEspecialPersona.setId(this.getId());
		condicionEspecialPersona.setNombreCondicion(this.nombreCondicion);
		condicionEspecialPersona.setIdPersona(this.idPersona);
		return condicionEspecialPersona;
	}

	/**
	 * Obtiene el valor de id
	 * 
	 * @return El valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id
	 * 
	 * @param id
	 *            El valor de id por asignar
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de idPersona
	 * 
	 * @return El valor de idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Establece el valor de idPersona
	 * 
	 * @param idPersona
	 *            El valor de idPersona por asignar
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Obtiene el valor de nombreCondicion
	 * 
	 * @return El valor de nombreCondicion
	 */
	public NombreCondicionEspecialEnum getNombreCondicion() {
		return nombreCondicion;
	}

	/**
	 * Establece el valor de nombreCondicion
	 * 
	 * @param nombreCondicion
	 *            El valor de nombreCondicion por asignar
	 */
	public void setNombreCondicion(NombreCondicionEspecialEnum nombreCondicion) {
		this.nombreCondicion = nombreCondicion;
	}
}
