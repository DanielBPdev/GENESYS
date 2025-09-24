/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.empleadores.clients.ConsultarEmpleadoresInactivar590;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * Clase que contiene la lógica para validar novedad Inactivar Ley 590 de 2000
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ValidarNovedadInactivarLey590 implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadInactivarLey590.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadInactivarLey590.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null; 
		try{
			ConsultarEmpleadoresInactivar590 consultarEmpleadoresInactivar590 = new ConsultarEmpleadoresInactivar590();
			consultarEmpleadoresInactivar590.execute();
			List<Long> idEmpleadores = consultarEmpleadoresInactivar590.getResult();
			if (idEmpleadores != null && !idEmpleadores.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setIdEmpleadores(idEmpleadores);
			}
			logger.debug("Finaliza método ValidarNovedadInactivarLey590.validar()");
		}catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarNovedadInactivarLey590.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}
	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
	
}
