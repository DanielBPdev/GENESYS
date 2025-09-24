/**
 * 
 */
package com.asopagos.novedades.fovis.composite.service;

import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;

/**
 * Interface para la ejecución de validación en Novedades Automaticas FOVIS.
 * @author Edward Castano <ecastano@heinsohn.com.co>
 *
 */
public interface ValidacionAutomaticaFovisCore {

    /**
     * Metodo que ejecuta la validacion para identificar los datos objeto de la novedad
     * @return Objeto con los datos de la novedad
     */
    public DatosNovedadAutomaticaFovisDTO validar();

    /**
     * Metodo que ejecuta la validacion para identificar los datos objeto de la novedad
     * @return Objeto con los datos de la novedad
     */
    public DatosNovedadAutomaticaFovisDTO validar(ParametroFOVISEnum parametro);

}
