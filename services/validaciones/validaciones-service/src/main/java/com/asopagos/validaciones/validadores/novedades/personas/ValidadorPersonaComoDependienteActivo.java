package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * si la persona está registrada como afiliado dependiente y estado activo.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaComoDependienteActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorPersonaComoDependienteActivo.execute");
		try{
			//se verifica que la novedad se ejecuta sobre un beneficiario
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!= null && 
					datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!= null){
			logger.info("Inicio de método ValidadorPersonaComoDependienteActivo.execute 2 ");	
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
		        
		        logger.info("Inicio de método ValidadorPersonaComoDependienteActivo.execute 3");
		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_TIPO_NUMERO_TIPO_AFILIACION_ESTADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
						.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
						.getResultList();
logger.info("Inicio de método ValidadorPersonaComoDependienteActivo.execute 4");
		        //se realiza la validación
				if(rolAfiliado != null && !rolAfiliado.isEmpty()){
					//validación fallida
					logger.info("NO HABILITADA- Fin de método ValidadorPersonaComoDependienteActivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_DEPENDIENTE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_DEPENDIENTE_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);	//GLPI 72669
				}else{
					//validación exitosa
					logger.info("HABILITADA- Fin de método ValidadorPersonaComoDependienteActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_DEPENDIENTE_ACTIVO);
				}
			}
			else{
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
		        
		        
		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_TIPO_NUMERO_TIPO_AFILIACION_ESTADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
						.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(rolAfiliado != null && !rolAfiliado.isEmpty()){
					//validación fallida
					logger.info("NO HABILITADA- Fin de método ValidadorPersonaComoDependienteActivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_DEPENDIENTE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_DEPENDIENTE_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);	
				}else{
					//validación exitosa
					logger.info("HABILITADA- Fin de método ValidadorPersonaComoDependienteActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_DEPENDIENTE_ACTIVO);
				}

			}
		} catch (Exception e) {
			logger.info("No evaluado - Ocurrió alguna excepción", e);
                        logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_DEPENDIENTE_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}