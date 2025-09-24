package com.asopagos.novedades.convertidores.persona;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import java.util.List;
import com.asopagos.afiliados.clients.ConsultarBeneficariosPadresXEdadCambioCategoria;
import com.asopagos.afiliados.clients.ConsultarBeneficariosCertificadoEscolarCambioCategoria;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;

public class ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica implements ValidacionMasivaCore{

    private final ILogger logger = LogManager.getLogger(ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica.class);

    /* (non-Javadoc)
	 * 
/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado) {
		logger.debug("Inicia método ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try {
			/* Consulta los beneficiarios */
			ConsultarBeneficariosPadresXEdadCambioCategoria consultarBeneficariosPadresXEdadCambioCategoria = new ConsultarBeneficariosPadresXEdadCambioCategoria();
			consultarBeneficariosPadresXEdadCambioCategoria.execute();
			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiarios = consultarBeneficariosPadresXEdadCambioCategoria.getResult();
			ConsultarBeneficariosCertificadoEscolarCambioCategoria consultarBeneficariosCertificadoEscolarCambioCategoria = new ConsultarBeneficariosCertificadoEscolarCambioCategoria(sinCertificado);
			consultarBeneficariosCertificadoEscolarCambioCategoria.execute(); 
			listaBeneficiarios.addAll(consultarBeneficariosCertificadoEscolarCambioCategoria.getResult());
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				datosNovedadAutomaticaDTO =  new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setListaBeneficiarios(listaBeneficiarios);
			}
			
			logger.debug("Finaliza método ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica.validar()");
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}
	@Override
	public DatosNovedadAutomaticaDTO validar(){
	logger.debug("Inicia método ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try {
			/* Consulta los beneficiarios */
			ConsultarBeneficariosPadresXEdadCambioCategoria consultarBeneficariosPadresXEdadCambioCategoria = new ConsultarBeneficariosPadresXEdadCambioCategoria();
			consultarBeneficariosPadresXEdadCambioCategoria.execute();
			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiarios = consultarBeneficariosPadresXEdadCambioCategoria.getResult();
			ConsultarBeneficariosCertificadoEscolarCambioCategoria consultarBeneficariosCertificadoEscolarCambioCategoria = new ConsultarBeneficariosCertificadoEscolarCambioCategoria("ConCertificado");
			consultarBeneficariosCertificadoEscolarCambioCategoria.execute(); 
			listaBeneficiarios.addAll(consultarBeneficariosCertificadoEscolarCambioCategoria.getResult());
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				datosNovedadAutomaticaDTO =  new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setListaBeneficiarios(listaBeneficiarios);
			}
			
			logger.debug("Finaliza método ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica.validar()");
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}
    
}
