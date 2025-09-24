package com.asopagos.pila.validadores.bloque5.interfaces;

import java.util.Map;
import javax.ejb.Local;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripción:</b> Interface que expone la función para la validación ID de aportante en los archivos del operador de Información en el
 * Bloque 5 <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */

@Local
public interface IValidacionOIBloque5 {

	/**
	 * Metodo que se encargará de realizar la validacion
	 * 
	 * @param contexto
	 * @throws ErrorFuncionalValidacionException
	 */
	public void validarBloque5(Map<String, Object> contexto) throws ErrorFuncionalValidacionException;

}
