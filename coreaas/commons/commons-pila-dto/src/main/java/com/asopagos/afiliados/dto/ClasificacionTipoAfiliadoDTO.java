/**
 * 
 */
package com.asopagos.afiliados.dto;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * DTO que contiene la clasificación y tipo afiliado de un afiliado principal.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ClasificacionTipoAfiliadoDTO {
	
	/**
	 * Atributo que contiene el tipo de afiliación.
	 */
	private TipoAfiliadoEnum tipoAfiliado;
	/**
	 * Atributo que contiene la clasificación.
	 */
	private ClasificacionEnum clasificacion;
	/**
	 * Método que retorna el valor de tipoAfiliado.
	 * @return valor de tipoAfiliado.
	 */
	public TipoAfiliadoEnum getTipoAfiliado() {
		return tipoAfiliado;
	}
	/**
	 * Método encargado de modificar el valor de tipoAfiliado.
	 * @param valor para modificar tipoAfiliado.
	 */
	public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}
	/**
	 * Método que retorna el valor de clasificacion.
	 * @return valor de clasificacion.
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}
	/**
	 * Método encargado de modificar el valor de clasificacion.
	 * @param valor para modificar clasificacion.
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}
}
