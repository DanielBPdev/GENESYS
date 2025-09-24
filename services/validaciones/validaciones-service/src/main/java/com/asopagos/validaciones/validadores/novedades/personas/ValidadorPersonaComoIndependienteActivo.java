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
 * si la persona está registrada como afiliado independiente y estado activo.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaComoIndependienteActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorPersonaComoIndependienteActivo.execute");
		try{
			//se verifica que la novedad se ejecuta sobre un beneficiario
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!= null && 
					datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!= null){
				
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_TIPO_NUMERO_TIPO_AFILIACION_ESTADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)
						.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(rolAfiliado != null && !rolAfiliado.isEmpty()){
					//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorPersonaComoIndependienteActivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_INDEPENDIENTE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoIndependienteActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO);
				}
			}
			else{
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
		        
		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_TIPO_NUMERO_TIPO_AFILIACION_ESTADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)
						.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(rolAfiliado != null && !rolAfiliado.isEmpty()){
					//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorPersonaComoIndependienteActivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_INDEPENDIENTE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoIndependienteActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO);
				}

			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}