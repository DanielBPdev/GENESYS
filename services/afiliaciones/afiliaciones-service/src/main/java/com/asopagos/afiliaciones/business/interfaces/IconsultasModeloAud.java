package com.asopagos.afiliaciones.business.interfaces;

import java.util.List;

import javax.ejb.Local;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de
 * información en el modelo de datos Core_aud <br/>
 *
 * @author <a href="mailto:squintero@heinsohn.com.co"> squintero</a>
 */
@Local
public interface IconsultasModeloAud {

	
	public List<Object[]> consultarSalarioMinimoPeriodo(Long periodo);
}
