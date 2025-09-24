package com.asopagos.novedades.composite.service.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Local
public interface IConsultasModeloCoreNovedadesComposite {


	/**
     * Método para la persistencia de un paquete de aportes detallados
	 * @param aportesDetallados
	 * @return
	 */
	 public Map<String, String> radicarListaSolicitudesNovedades(List<Long> listIdSolicitud,String sede,  UserDTO userDTO);
	
	
}
