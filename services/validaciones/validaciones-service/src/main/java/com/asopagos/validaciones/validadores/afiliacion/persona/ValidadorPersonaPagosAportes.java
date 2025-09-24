package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada para el mismo emleador. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaPagosAportes extends ValidadorAbstract {
	/**
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.ejb.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		// TODO Auto-generated method stub
		return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_PAGOS_APORTES);
	}

	

}
