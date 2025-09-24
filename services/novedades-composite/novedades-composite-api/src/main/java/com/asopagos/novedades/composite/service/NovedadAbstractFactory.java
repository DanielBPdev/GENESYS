/**
 * 
 */
package com.asopagos.novedades.composite.service;

import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;

/**
 * Fabrica para la construcción de la clase novedades.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class NovedadAbstractFactory {

	/**
	 * Instancia Singleton de la clase.
	 */
	private static NovedadAbstractFactory instance;

	/**
	 * Método que obtiene la instancia singleton de la clase NovedadAbstractFactory.
	 * 
	 * @return Instancia Singleton
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static NovedadAbstractFactory getInstance()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (instance == null) {
			instance = new NovedadAbstractFactory();
		}
		return instance;
	}
	
	/**
	 * Método que se encarga de obtener una novedad.
	 * @param novedadDTO dto de las novedades.
	 * @return novedad determinada.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public NovedadCore obtenerServicioNovedad(ParametrizacionNovedadModeloDTO novedadDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<NovedadCore> clazz = (Class<NovedadCore>) Class.forName(novedadDTO.getRutaCualificada());
		NovedadCore servicioNovedad = clazz.newInstance();
		return servicioNovedad;
	}
}
