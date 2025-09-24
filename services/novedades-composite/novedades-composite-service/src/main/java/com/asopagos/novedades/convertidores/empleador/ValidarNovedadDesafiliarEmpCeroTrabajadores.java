/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.empleadores.clients.ConsultarEmpleadoresCeroTrabajadores;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para validar novedad Inactivar Cuenta Web Empleador
 * Novedad 30.
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ValidarNovedadDesafiliarEmpCeroTrabajadores implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadDesafiliarEmpCeroTrabajadores.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadDesafiliarEmpCeroTrabajadores.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try{
			ConsultarEmpleadoresCeroTrabajadores consultarEmpleadoresCeroTrabajadores = new ConsultarEmpleadoresCeroTrabajadores();
			consultarEmpleadoresCeroTrabajadores.execute();
			List<Long> idEmpleadores = consultarEmpleadoresCeroTrabajadores.getResult();
			
			if(idEmpleadores != null && !idEmpleadores.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setIdEmpleadores(idEmpleadores);
			}
			logger.debug("Finaliza método ValidarNovedadDesafiliarEmpCeroTrabajadores.validar()");
		}catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarNovedadDesafiliarEmpCeroTrabajadores.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}	
	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
}
