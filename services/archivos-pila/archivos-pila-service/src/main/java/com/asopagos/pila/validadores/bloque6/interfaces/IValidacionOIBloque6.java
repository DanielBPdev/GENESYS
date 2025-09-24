package com.asopagos.pila.validadores.bloque6.interfaces;

import java.util.Map;
import javax.ejb.Local;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripción:</b> Interfaz que expone la función para la conciliación del Operador de Información con el Operador Financiero en el
 * Bloque 6 <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 393 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IValidacionOIBloque6 {

	/**
	 * Procedimiento para la validación de bloque 6 para Operador Financiero y de Información
	 * 
	 * @param contexto
	 * @param result 
	 * @throws ErrorFuncionalValidacionException
	 */
	public RespuestaValidacionDTO validarBloque6(Map<String, Object> contexto, RespuestaValidacionDTO result);

}
