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
 * Validador 16	Validar que empleador no esté registrado como moroso
 * Valida:
 * Pendiente por definir. Se realizará cuando se hayan especificado los procesos de Gestión de Cartera de aportes.
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorNoMoroso extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadorNoMoroso.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			//TODO FALTA REALIZAR LA LOGICA DE MORA
			if(true){ 
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorNoMoroso.execute");
				// Validación exitosa, Validador 16	Validar que empleador no esté registrado como moroso
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADO_MOROSO);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorNoMoroso.execute");
				// Validación no aprobada, Validador 16	Validar que empleador no esté registrado como moroso
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_REGISTRADO_COMO_MOROSO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADO_MOROSO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EMPLEADO_MOROSO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
