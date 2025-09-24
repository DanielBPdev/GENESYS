package com.asopagos.novedades.convertidores.persona;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import java.util.List;
import com.asopagos.afiliados.clients.ConsultarExVeteranos;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.RolafiliadoNovedadAutomaticaDTO;


public class ValidarClaseTrabajadorNovedadAutomatica implements ValidacionMasivaCore{

    private final ILogger logger = LogManager.getLogger(ValidarClaseTrabajadorNovedadAutomatica.class);

	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarClaseTrabajadorNovedadAutomatica.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try {
			/* Consulta los beneficiarios */
			ConsultarExVeteranos consultar = new ConsultarExVeteranos();
			consultar.execute();
			List<RolafiliadoNovedadAutomaticaDTO> listaVeteranos = consultar.getResult(); 
			if (listaVeteranos != null && !listaVeteranos.isEmpty()) {
				datosNovedadAutomaticaDTO =  new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setListaRolafiliados(listaVeteranos);
			}
			logger.debug("Finaliza método ValidarClaseTrabajadorNovedadAutomatica.validar()");
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarClaseTrabajadorNovedadAutomatica.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}
    @Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
}
