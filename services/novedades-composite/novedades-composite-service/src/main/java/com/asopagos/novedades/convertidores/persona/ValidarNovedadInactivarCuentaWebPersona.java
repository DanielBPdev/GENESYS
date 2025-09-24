/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.personas.clients.ConsultarPersonasInactivarCtaWeb;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para validar novedad Inactivación de Cuentas Web Persona.
 * Novedad Personas
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ValidarNovedadInactivarCuentaWebPersona implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadInactivarCuentaWebPersona.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar(com.asopagos.enumeraciones.core.TipoTransaccionEnum)
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadInactivarCuentaWebPersona.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try{
			/*Consulta las personas que ya se venció el certificado Escolaridad.*/
			ConsultarPersonasInactivarCtaWeb consultarPersonasInactivarCtaWeb = new ConsultarPersonasInactivarCtaWeb();
			consultarPersonasInactivarCtaWeb.execute();
			List<Long> idPersonaAfiliados = consultarPersonasInactivarCtaWeb.getResult();
			if (idPersonaAfiliados != null && !idPersonaAfiliados.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setIdPersonaAfiliados(idPersonaAfiliados);
			}
			
			logger.debug("Finaliza método ValidarNovedadInactivarCuentaWebPersona.validar()");
		}catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarNovedadInactivarCuentaWebPersona.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}	
	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
}
