/**
 * 
 */
package com.asopagos.novedades.composite.service;

import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;

/**
 * Interface para la ejecución de validación en Novedades Masivas.
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public interface ValidacionMasivaCore {
	
	/**
	 * Metodo que ejecuta la validacion para identificar los datos objeto de la novedad
	 * @return Objeto con los datos de la novedad
	 */
	public DatosNovedadAutomaticaDTO validar();

	public DatosNovedadAutomaticaDTO validar(String sinCertificado);



}
