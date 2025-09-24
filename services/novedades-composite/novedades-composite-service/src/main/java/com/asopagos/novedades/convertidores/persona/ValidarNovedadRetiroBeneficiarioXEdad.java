/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.List;
import com.asopagos.afiliados.clients.ConsultarBeneficariosXEdad;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para validar novedad Retirar
 * beneficiarios de 18 años y mayores de 18 años que no tengan cédula de
 * ciudadanía<br>
 * 
 * <b>Historia de Usuario:</b> HU 496
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ValidarNovedadRetiroBeneficiarioXEdad implements ValidacionMasivaCore {

	private final ILogger logger = LogManager.getLogger(ValidarNovedadRetiroBeneficiarioXEdad.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadRetiroBeneficiarioXEdad.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try {
			/* Consulta los beneficiarios */
			ConsultarBeneficariosXEdad consultarBeneficariosXEdad = new ConsultarBeneficariosXEdad();
			consultarBeneficariosXEdad.execute();
			List<BeneficiarioNovedadAutomaticaDTO> listaBeneficiarios = consultarBeneficariosXEdad.getResult();
			if (listaBeneficiarios != null && !listaBeneficiarios.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setListaBeneficiarios(listaBeneficiarios);
			}

			logger.debug("Finaliza método ValidarNovedadRetiroBeneficiarioXEdad.validar()");
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarNovedadRetiroBeneficiarioXEdad.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}
	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
}
