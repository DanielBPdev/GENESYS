/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.personas.clients.ConsultarVencimientoCertificadoEscolaridad;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para validar novedad Vencimiento Certificado Escolaridad.
 * Novedad Personas
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ValidarNovedadVencCertificadoEscolaridad implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadVencCertificadoEscolaridad.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadVencCertificadoEscolaridad.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try{
			/*Consulta las personas que ya se venció el certificado Escolaridad.*/
			ConsultarVencimientoCertificadoEscolaridad consultarVencimientoCertificadoEscolaridad = new ConsultarVencimientoCertificadoEscolaridad();
			consultarVencimientoCertificadoEscolaridad.execute();
			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiarios = consultarVencimientoCertificadoEscolaridad.getResult();
			
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				datosNovedadAutomaticaDTO =  new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setListaBeneficiarios(listaBeneficiarios);
			}
			
			logger.debug("Finaliza método ValidarNovedadVencCertificadoEscolaridad.validar()");
		}catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarNovedadVencCertificadoEscolaridad.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}	
	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
}
