/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.List;
import com.asopagos.afiliados.clients.ConsultarVencimientoIncapacidades;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para validar novedad Vencimiento de Incapacidades.
 * Novedad Personas
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ValidarNovedadVencIncapacidades implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadVencIncapacidades.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadVencIncapacidades.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO =  null;
		try{
			/*Consulta las personas que ya se venció el certificado Escolaridad.*/
			ConsultarVencimientoIncapacidades consultarVencimientoIncapacidades = new ConsultarVencimientoIncapacidades();
			consultarVencimientoIncapacidades.execute();
			List<Long> idPersonasAfiliados = consultarVencimientoIncapacidades.getResult();
			if (idPersonasAfiliados!= null && !idPersonasAfiliados.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setIdPersonaAfiliados(idPersonasAfiliados);
			}
			
			logger.debug("Finaliza método ValidarNovedadVencIncapacidades.validar()");
		}catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarNovedadVencIncapacidades.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}	
	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
}
