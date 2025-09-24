package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida que el empleador este creado en el sistema con cualquier estado
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ValidadorEmpleadorExiste extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.ejb.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadorExiste.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el empleador con el tipo y nro documento
 			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
 					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
 					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
 					.getSingleResult();
 			
 			// Se valida la condición
			if(empleador != null){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorExiste.execute");
				// Validación exitosa
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_EXISTE);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorExiste.execute");
				// Validación no aprobada
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_NO_EXISTE_SISTEMA),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_EXISTE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
	     	        
		}catch(Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			// Validación con problema técnico
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_EXISTE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
