package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Si el trabajador tiene activa una cesión o retención del subsidio, 
 * no se puede habilitar la novedad de pignoración del subsidio
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEstadoSubsidioTrabajador extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEstadoSubsidioTrabajador.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se verifica que el administrador del subsidio del grupo familiar tenga activa una cesión o una retención del subsidio
			boolean activaCesionORetencion = (Boolean) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CESION_RETENCION_SUBSIDIO_AFILIADO)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
	        		.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
	        		.getSingleResult();
			
	        //se realiza la validación
			if(activaCesionORetencion){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorEstadoSubsidioTrabajador.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_SUBCIDIO_TRABAJADOR);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorEstadoSubsidioTrabajador.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_SUBCIDIO_TRABAJADOR),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_SUBCIDIO_TRABAJADOR,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_SUBCIDIO_TRABAJADOR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}