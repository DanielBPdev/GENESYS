package com.asopagos.novedades.composite.dto;

import java.util.List;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;

/**
 * DTO que contiene los datos de desafiliación.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class EmpleadorAfiliadosDTO {
    
    /**
     * Variable idPersona
     */
    private EmpleadorModeloDTO empleador;
    
    /**
     * Variable IdSucursal
     */
    private List<RolAfiliadoModeloDTO> roles;
    
    /**
     * Variable que indica si se debe o no inactivar una cuenta web.
     */
    private Boolean inactivarCuentaWeb;

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
	 * Método que retorna el valor de roles.
	 * @return valor de roles.
	 */
	public List<RolAfiliadoModeloDTO> getRoles() {
		return roles;
	}

	/**
	 * Método encargado de modificar el valor de roles.
	 * @param valor para modificar roles.
	 */
	public void setRoles(List<RolAfiliadoModeloDTO> roles) {
		this.roles = roles;
	}

	/**
	 * Método que retorna el valor de inactivarCuentaWeb.
	 * @return valor de inactivarCuentaWeb.
	 */
	public Boolean getInactivarCuentaWeb() {
		return inactivarCuentaWeb;
	}

	/**
	 * Método encargado de modificar el valor de inactivarCuentaWeb.
	 * @param valor para modificar inactivarCuentaWeb.
	 */
	public void setInactivarCuentaWeb(Boolean inactivarCuentaWeb) {
		this.inactivarCuentaWeb = inactivarCuentaWeb;
	}
    
}
