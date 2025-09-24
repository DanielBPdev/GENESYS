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
 * Valida que el campo "Servicios sin afiliación activos/inactivos" 
 * tenga el valor "Inactivo" para poder hacer el registro de la novedad
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorServiciosSinAfiliacionInactivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorServiciosSinAfiliacionInactivo.execute");
		try{
			boolean estadoServiciosSinAfiliacion = Boolean.parseBoolean(
            		datosValidacion.get(ConstantesValidaciones.ESTADO_SERVICIOS_SIN_AFILIACION));

            if(estadoServiciosSinAfiliacion){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorServiciosSinAfiliacionInactivo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SERVICIOS_SIN_AFILIACION_INACT);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorServiciosSinAfiliacionInactivo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_SERVICIOS_SIN_AFILIACION_INACT),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_SERVICIOS_SIN_AFILIACION_INACT,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_SERVICIOS_SIN_AFILIACION_INACT, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}