package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 26
 * Validar que el valor del campo "¿Empleador cubierto por beneficios de Ley 590 de 2000?" sea "No".
 * 
 * @author Julián Andrés Muñoz Cardozo <jmunoz@heinsohn.com.co>
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorNoCubiertoBeneficio590 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadorNoCubiertoBeneficio590.execute");
		try {
			boolean beneficioCubierto590 = Boolean.parseBoolean(datosValidacion.get(ConstantesValidaciones.BENEFICIO_CUBIERTO_590));

			if (!beneficioCubierto590) {
				logger.debug("Aprobado");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_NO_CUBIERTO_BENEFICIO_590);
			}else{
				logger.debug(
						"No aprobada - El empleador está cubierto por beneficios de Ley 1429 de 2010 y/o el beneficio ha caducado");
				return crearValidacion(
						myResources.getString(
								ConstantesValidaciones.KEY_EMPLEADOR_MARCADO_COMO_BENEFICIARIO_LEY_590_2000),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_NO_CUBIERTO_BENEFICIO_590,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_EMPLEADOR_MARCADO_COMO_BENEFICIARIO_LEY_590_2000,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_NO_CUBIERTO_BENEFICIO_590,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
