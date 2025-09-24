package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Solo se habilita la novedad cuando es la primera vez que 
 * el campo "Medio de pago asignado"  toma el valor "tarjeta" 
 *  
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorTarjetaPrimeraVez extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTarjetaPrimeraVez.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //TODO LÓGICA DE LA VALIDACIÓN
	        
	        //se realiza la validación
			if(true){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorTarjetaPrimeraVez.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TARJETA_PRIMERA_VEZ);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorTarjetaPrimeraVez.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TARJETA_PRIMERA_VEZ),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TARJETA_PRIMERA_VEZ,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TARJETA_PRIMERA_VEZ, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}