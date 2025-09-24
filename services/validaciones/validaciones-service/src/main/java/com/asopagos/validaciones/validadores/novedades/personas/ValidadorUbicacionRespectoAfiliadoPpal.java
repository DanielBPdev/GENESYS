package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Valor del campo "Misma información de ubicación y 
 * correspondencia del afiliado principal?" debe estar 
 * marcado como "False" (para cada grupo familiar)
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorUbicacionRespectoAfiliadoPpal extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorUbicacionRespectoAfiliadoPpal.execute");
		try{
			boolean mismaInfoUbicacion = Boolean.parseBoolean(
            		datosValidacion.get(ConstantesValidaciones.MISMA_INFO_UBICACION_AFILIADO_PRINCIPAL));

            if(mismaInfoUbicacion){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorUbicacionRespectoAfiliadoPpal.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_UBICACION_RESPECTO_AFILIADO_PPAL);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorUbicacionRespectoAfiliadoPpal.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_UBICACION_RESPECTO_AFILIADO_PPAL),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_UBICACION_RESPECTO_AFILIADO_PPAL,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_UBICACION_RESPECTO_AFILIADO_PPAL, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}