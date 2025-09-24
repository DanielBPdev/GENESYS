package com.asopagos.aportes.composite.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Clase que implementa los métodos de negocio relacionados
 * con el proceso de registro o relación de aportes
 * 
 * <b>Módulo:</b> Asopagos - HU-211-397, HU-211-403, HU-211-404, HU-211-405,
 * HU-211-399, HU-211-392<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <anbuitrago@heinsohn.com.co>Andres Felipe Buitrago</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 * @author <a href="mailto:squintero@heinsohn.com.co">Steven Quintero
 *         González.</a>
 */
/**
 * @author Juan Diego
 *
 */
@Local
public interface IAporteNovedadLocal {

	/**
	 * @param novedadPilaDTO
	 * @param canal
	 * @param tipoIdAportante
	 * @param numeroIdAportante
	 * @param personaCotizante
	 * @param esTrabajadorReintegrable
	 * @param esEmpleadorReintegrable
	 * @param userDTO
	 * @param idsNovedadesProcesadas
	 * @throws Exception 
	 */
	public void transaccionRegistrarNovedad(NovedadPilaDTO novedadPilaDTO,CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, UserDTO userDTO,List<Long> idsNovedadesProcesadas)throws Exception;

}
