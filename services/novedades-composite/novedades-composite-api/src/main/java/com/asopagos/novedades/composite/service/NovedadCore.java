/**
 * 
 */
package com.asopagos.novedades.composite.service;

import javax.persistence.EntityManager;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Interface para la ejecución de las novedades.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public interface NovedadCore {
	
	/**
	 * Método que se encarga de ejecutar el servicio de novedad.
	 * * Works like @link com.asopagos.novedades.composite.service.NovedadCore#transformarEjecutarRutinaNovedad(SolicitudNovedadDTO, EntityManager)
	 * @param solicitudNovedadDTO datos de la solicitud de novedad.
	 * @return servicio a ejecutar.
	 */
    @Deprecated    
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO);

	
	/**
     * Método que se encarga de ejecutar el servicio de novedad.
	 * @param datosNovedad 
	 * @param entityManager 
	 * @param userDTO 
     * @param solicitudNovedadDTO datos de la solicitud de novedad.
     */
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager,UserDTO userDTO);


}
