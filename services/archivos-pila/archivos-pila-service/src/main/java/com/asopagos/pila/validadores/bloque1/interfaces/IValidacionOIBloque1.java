package com.asopagos.pila.validadores.bloque1.interfaces;

import java.util.Map;
import javax.ejb.Local;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripción:</b> Interface que expone los servicios para realizar la validacion en el nombre del archivo <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IValidacionOIBloque1 {

	/**
	 * Función para la validación del bloque 1 del Operador de Información
	 * 
	 * @param nombreArchivo
	 *        Nombre del archivo a validar
	 * @param resources
	 *        Mapa de variables de contexto de validación
	 * @param perfilArchivo
	 *        Perfil de lectura del archivo
	 * @param result 
	 * @return <b>RespuestaValidacionDTO</b>
	 *         DTO con el resultado de la validación
	 * @throws ErrorFuncionalValidacionException
	 */
	public RespuestaValidacionDTO validacionNombreArchivo(String nombreArchivo, Map<String, Object> resources,
			PerfilLecturaPilaEnum perfilArchivo, RespuestaValidacionDTO result)
					throws ErrorFuncionalValidacionException;
}
