package com.asopagos.pila.validadores.bloque3.interfaces;

import java.util.Map;
import javax.ejb.Local;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripción:</b> Interface que expone la validación de bloque 3 para los archivos del operador de Información<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IValidacionOIBloque3 {

    /**
     * Validación del bloque 3 de Operador de Información
     * 
     * @param contexto
     *        Mapa de variables de contexto
     * @param result 
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO con el resultado de la validación
     * @throws ErrorFuncionalValidacionException
     */
	public RespuestaValidacionDTO validarBloque3(Map<String, Object> contexto, RespuestaValidacionDTO result)
			throws ErrorFuncionalValidacionException;

}
