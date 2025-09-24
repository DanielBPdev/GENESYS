/**
 * 
 */
package com.asopagos.afiliados.dto;

import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;

/**
 * DTO que contiene el rol afiliado y el empleador asociado en caso de los dependientes.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class RolAfiliadoEmpleadorDTO {
	
	/**
	 * Rol afiliado.
	 */
	private RolAfiliadoModeloDTO rolAfiliado;
	/**
	 * Empleador asociado al rol afiliado para el caso de los dependientes.
	 */
	private EmpleadorModeloDTO empleador;
	/**
	 * Método que retorna el valor de rolAfiliado.
	 * @return valor de rolAfiliado.
	 */
	public RolAfiliadoModeloDTO getRolAfiliado() {
		return rolAfiliado;
	}
	/**
	 * Método encargado de modificar el valor de rolAfiliado.
	 * @param valor para modificar rolAfiliado.
	 */
	public void setRolAfiliado(RolAfiliadoModeloDTO rolAfiliado) {
		this.rolAfiliado = rolAfiliado;
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
	
}
