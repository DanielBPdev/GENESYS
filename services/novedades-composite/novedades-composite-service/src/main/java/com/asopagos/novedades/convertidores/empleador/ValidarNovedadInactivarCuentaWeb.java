/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.empleadores.clients.ConsultarEmpleadoresInactivarCuentaWeb;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.usuarios.clients.BloquearUsuariosPendienteInactivacion;
import com.asopagos.usuarios.dto.UsuarioDTO;

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
public class ValidarNovedadInactivarCuentaWeb implements ValidacionMasivaCore{

	private final ILogger logger = LogManager.getLogger(ValidarNovedadInactivarCuentaWeb.class);
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.ValidacionMasivaCore#validar()
	 */
	@Override
	public DatosNovedadAutomaticaDTO validar() {
		logger.debug("Inicia método ValidarNovedadInactivarCuentaWeb.validar()");
		DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
		try{
			/* Consulta los usuarios que estan en el grupo USUARIOS_RETIRADOS */
			BloquearUsuariosPendienteInactivacion usuariosPendienteInactivacion=new BloquearUsuariosPendienteInactivacion(true);
			usuariosPendienteInactivacion.execute();
			
			List <UsuarioDTO> usuarios=usuariosPendienteInactivacion.getResult();
			
			/*Consulta los empleadores que se inactivaran porque ya cumplen con el periodo de retiro.*/
			ConsultarEmpleadoresInactivarCuentaWeb consultarEmpleadoresInactivarCtaWeb=new ConsultarEmpleadoresInactivarCuentaWeb(usuarios);
			consultarEmpleadoresInactivarCtaWeb.execute();
			List<Long> idEmpleadores = consultarEmpleadoresInactivarCtaWeb.getResult();
			if (idEmpleadores != null && !idEmpleadores.isEmpty()) {
				datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaDTO();
				datosNovedadAutomaticaDTO.setIdEmpleadores(idEmpleadores);
			}
			
			
			logger.debug("Finaliza método ValidarNovedadInactivarCuentaWeb.validar()");
		}catch (Exception e) {
			logger.error("Ocurrio un error inesperado en ValidarNovedadInactivarCuentaWeb.validar()", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return datosNovedadAutomaticaDTO;
	}	

	@Override
	public DatosNovedadAutomaticaDTO validar(String sinCertificado){
		return null;
	}
}
