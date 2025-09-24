package com.asopagos.novedades.composite.service;

import java.util.List;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interfaz que define los servicios de composición del
 * proceso de afiliación de personas WEB
 *
 * @author Julian Andres Sanchez
 *         <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez@heinsohn.com.co
 *         </a>
 */
public interface ValidacionNovedadCompositeService {

	/**
	 * Método encargado de realizar las validaciones de negocio respectivas
	 * 
	 * @param idCargueMultiple,
	 *            id del cargue multiple
	 * @param lstTrabajadorCandidatoDTO,
	 *            lista de trabajadores a realizar la afiliacion
	 * @param numeroDiaTemporizador,
	 *            numero de días con los que se cuenta para iniciar el
	 *            temporizador
	 */
	public void validarDatosAfiliacionTrabajadorCandidato(Long idCargueMultiple,
			List<AfiliarTrabajadorCandidatoDTO> lstSolicitudNovedadDTO, Long numeroDiaTemporizador,String nombreArchivo,UserDTO userDTO);
}
