package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * si la persona está registrada como empleador y estado activo 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaComoEmpleadorActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorPersonaComoEmpleadorActivo.execute");
		try{
			//se verifica que la novedad se ejecuta sobre un beneficiario
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!= null && 
					datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!= null){
				
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<Empleador> empleadores = (List<Empleador>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_POR_TIPO_NUMERO_ESTADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.ESTADO_EMPLEADOR_PARAM, EstadoEmpleadorEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(empleadores != null && !empleadores.isEmpty()){
					//validación fallida
					logger.info("NO HABILITADA- Fin de método ValidadorPersonaComoEmpleadorActivo.execute 1");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_EMPLEADOR_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoEmpleadorActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR_ACTIVO);
				}
			}
			else{
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
		        
		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<Empleador> empleadores = (List<Empleador>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_POR_TIPO_NUMERO_ESTADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.ESTADO_EMPLEADOR_PARAM, EstadoEmpleadorEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(empleadores != null && !empleadores.isEmpty()){
					//validación fallida
					logger.info("NO HABILITADA- Fin de método ValidadorPersonaComoEmpleadorActivo.execute 2");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_EMPLEADOR_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoEmpleadorActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR_ACTIVO);
				}

			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}