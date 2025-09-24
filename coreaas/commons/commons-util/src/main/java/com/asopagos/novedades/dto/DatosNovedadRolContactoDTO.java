package com.asopagos.novedades.dto;

import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;

/**
 * DTO que contiene los datos de Actualización de Roles de Contacto Afiliacion/Aportes/Subsidio
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class DatosNovedadRolContactoDTO {

	/** Identificación del Empleador*/
	private Long idEmpleador;
	
	/** Identificación del rol Aportes*/
	private Long idRolAportes;
	
	/** Identificación del rol Aportes*/
	private Long idRolAfiliacion;
	
	/** Identificación del rol Aportes*/
	private Long idRolSubsidio;
	
	/** Rol Aportes*/
	private RolContactoEmpleador rolAportes;
	
	/** Rol Afiliación*/
	private RolContactoEmpleador rolAfiliacion;
	
	/** Rol Subsidio*/
	private RolContactoEmpleador rolSubsidio;

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
	 * @return the idRolAportes
	 */
	public Long getIdRolAportes() {
		return idRolAportes;
	}

	/**
	 * @param idRolAportes the idRolAportes to set
	 */
	public void setIdRolAportes(Long idRolAportes) {
		this.idRolAportes = idRolAportes;
	}

	/**
	 * @return the idRolAfiliacion
	 */
	public Long getIdRolAfiliacion() {
		return idRolAfiliacion;
	}

	/**
	 * @param idRolAfiliacion the idRolAfiliacion to set
	 */
	public void setIdRolAfiliacion(Long idRolAfiliacion) {
		this.idRolAfiliacion = idRolAfiliacion;
	}

	/**
	 * @return the idRolSubsidio
	 */
	public Long getIdRolSubsidio() {
		return idRolSubsidio;
	}

	/**
	 * @param idRolSubsidio the idRolSubsidio to set
	 */
	public void setIdRolSubsidio(Long idRolSubsidio) {
		this.idRolSubsidio = idRolSubsidio;
	}

	/**
	 * @return the rolAportes
	 */
	public RolContactoEmpleador getRolAportes() {
		return rolAportes;
	}

	/**
	 * @param rolAportes the rolAportes to set
	 */
	public void setRolAportes(RolContactoEmpleador rolAportes) {
		this.rolAportes = rolAportes;
	}

	/**
	 * @return the rolAfiliacion
	 */
	public RolContactoEmpleador getRolAfiliacion() {
		return rolAfiliacion;
	}

	/**
	 * @param rolAfiliacion the rolAfiliacion to set
	 */
	public void setRolAfiliacion(RolContactoEmpleador rolAfiliacion) {
		this.rolAfiliacion = rolAfiliacion;
	}

	/**
	 * @return the rolSubsidio
	 */
	public RolContactoEmpleador getRolSubsidio() {
		return rolSubsidio;
	}

	/**
	 * @param rolSubsidio the rolSubsidio to set
	 */
	public void setRolSubsidio(RolContactoEmpleador rolSubsidio) {
		this.rolSubsidio = rolSubsidio;
	}
}
