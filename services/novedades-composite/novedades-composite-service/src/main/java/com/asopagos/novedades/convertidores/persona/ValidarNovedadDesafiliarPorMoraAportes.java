/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.afiliados.clients.ConsultarPersonasMoraAportes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para validar novedad de Desafiliacion de Independientes
 * y Pensionados por Mora en Aportes de acuerdo a un tiempo parametrizado.
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ValidarNovedadDesafiliarPorMoraAportes implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadDesafiliarPorMoraAportes.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar(com.asopagos.enumeraciones.core.TipoTransaccionEnum)
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadInactivarCuentaWebPersona.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try{
			
			ConsultarPersonasMoraAportes consultarPersonasMoraAportes = new ConsultarPersonasMoraAportes();
			consultarPersonasMoraAportes.execute();
			List<PersonaRetiroNovedadAutomaticaDTO> personasRetiro = consultarPersonasMoraAportes.getResult();
			
			if (personasRetiro != null && !personasRetiro.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				List<Long> idPersonaAfiliados = new ArrayList<>();
				for (PersonaRetiroNovedadAutomaticaDTO personaRetiroDTO : personasRetiro) {
					idPersonaAfiliados.addAll(personaRetiroDTO.getIdPersonaAfiliado());
				}
				datosNovedadAutomaticaDTO.setIdPersonaAfiliados(idPersonaAfiliados);
				datosNovedadAutomaticaDTO.setListaPersonasRetiro(personasRetiro);
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
