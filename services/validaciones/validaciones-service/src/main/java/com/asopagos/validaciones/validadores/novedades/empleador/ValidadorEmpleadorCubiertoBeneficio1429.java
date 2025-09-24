package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 19
 * Clase que contiene la lógica para Validar:
 * Valor del campo "¿Empleador cubierto por beneficios de Ley 1429 de 2010?" igual "si"
 * @author Julián Andrés Muñoz Cardozo <jmunoz@heinsohn.com.co>
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorCubiertoBeneficio1429 extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorEmpleadorCubiertoBeneficio1429.execute");
        try {
			boolean beneficioCubierto1429 = Boolean.parseBoolean(datosValidacion.get(ConstantesValidaciones.BENEFICIO_CUBIERTO_1429));

			if (beneficioCubierto1429) {
				logger.debug("Aprobado");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_CUBIERTO_BENEFICIO_1429);
			}else{
				logger.debug(
						"No aprobada - El empleador no está cubierto por beneficios de Ley 1429 de 2010 y/o el beneficio ha caducado");
				return crearValidacion(
						myResources.getString(
								ConstantesValidaciones.KEY_VALOR_CAMPO_BENEFICIO_LEY_1429_2010_NO),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_CUBIERTO_BENEFICIO_1429,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALOR_CAMPO_BENEFICIO_LEY_1429_2010_NO,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_CUBIERTO_BENEFICIO_1429,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
    }
}
