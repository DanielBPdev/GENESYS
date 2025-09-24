package com.asopagos.novedades.composite.dto;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SolicitudAfiliacionPersonaModeloDTO;

/**
 * DTO que contiene los datos de desafiliación.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class SolicitudAfiliacionRolDTO {
    
	 /**
     * Variable idPersona
     */
    private SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacion;
    
    /**
     * Variable IdSucursal
     */
    private RolAfiliadoModeloDTO rol;

	/**
	 * Método que retorna el valor de solicitudAfiliacion.
	 * @return valor de solicitudAfiliacion.
	 */
	public SolicitudAfiliacionPersonaModeloDTO getSolicitudAfiliacion() {
		return solicitudAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de solicitudAfiliacion.
	 * @param valor para modificar solicitudAfiliacion.
	 */
	public void setSolicitudAfiliacion(SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacion) {
		this.solicitudAfiliacion = solicitudAfiliacion;
	}

	/**
	 * Método que retorna el valor de rol.
	 * @return valor de rol.
	 */
	public RolAfiliadoModeloDTO getRol() {
		return rol;
	}

	/**
	 * Método encargado de modificar el valor de rol.
	 * @param valor para modificar rol.
	 */
	public void setRol(RolAfiliadoModeloDTO rol) {
		this.rol = rol;
	}

	        
}
