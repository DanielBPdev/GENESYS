/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.empleadores.clients.ConsultarEmpleadoresInactivar1429;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * Clase que contiene la lógica para validar novedad Inactivar Ley 1429
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ValidarNovedadInactivarLey1429 implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadInactivarLey1429.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try {
			logger.debug("Inicia método ValidarNovedadInactivarLey1429.validar()");
			ConsultarEmpleadoresInactivar1429 consultarEmpleadores = new ConsultarEmpleadoresInactivar1429();
			consultarEmpleadores.execute();
			List<Long> idEmpleadores = consultarEmpleadores.getResult();
			if (idEmpleadores != null && !idEmpleadores.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setIdEmpleadores(idEmpleadores);
			}
			
			logger.info("Finaliza método ValidarNovedadInactivarLey1429.validar()");
		} catch (Exception e) {
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
