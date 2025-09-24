/**
 * 
 */
package com.asopagos.novedades.fovis.composite.service;

import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Interface para la ejecución de las novedades.
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public interface NovedadFovisCore {
	
	/**
	 * Método que se encarga de ejecutar el servicio de novedad.
	 * @param solicitudNovedadDTO datos de la solicitud de novedad.
	 * @return servicio a ejecutar.
	 */
	public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO);



}
