/**
 * 
 */
package com.asopagos.novedades.fovis.composite.service;

import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;

/**
 * Fabrica para la construcción de la clase novedades.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class NovedadFovisAbstractFactory {

    /**
     * Instancia Singleton de la clase.
     */
    private static NovedadFovisAbstractFactory instance;

    /**
     * Método que obtiene la instancia singleton de la clase NovedadAbstractFactory.
     * 
     * @return Instancia Singleton
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static NovedadFovisAbstractFactory getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (instance == null) {
            instance = new NovedadFovisAbstractFactory();
        }
        return instance;
    }

    /**
     * Método que se encarga de obtener una novedad.
     * @param novedadDTO
     *        dto de las novedades.
     * @return novedad determinada.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public NovedadFovisCore obtenerServicioNovedad(ParametrizacionNovedadModeloDTO novedadDTO)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<NovedadFovisCore> clazz = (Class<NovedadFovisCore>) Class.forName(novedadDTO.getRutaCualificada());
        NovedadFovisCore servicioNovedad = clazz.newInstance();
        return servicioNovedad;
    }
}
