package com.asopagos.validaciones.fovis.validadores.novedades.personas;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
import com.asopagos.validaciones.fovis.util.ValidacionFovisPersonaUtils;

import java.util.List;
import java.util.Map;

/**
 * CLASE QUE VALIDA:
 * si la persona está registrada como beneficiario hijo y estado activo
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaComoHijoActivo extends ValidadorFovisAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaComoHijoActivo.execute");
		try{
			//se verifica que la novedad se ejecuta sobre un beneficiario
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM)!= null && 
					datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM)!= null &&
					datosValidacion.get(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM)!= null){
				
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
		        String tipoBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM);
		        String numeroIdentificacionAfiliado=datosValidacion.get(ConstantesValidaciones.NUMERO_IDENTIFICACION_AFILIADO);
		        String tipoIdentificacionAfiliado=datosValidacion.get(ConstantesValidaciones.TIPO_IDENTIFICACION_AFILIADO);
				//se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
				// List<Beneficiario> beneficiario = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ACTIVO_POR_TIPO)
				//modificacion 07/10/2021 adicion condicional namedquery
		        List<Beneficiario> beneficiario = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ACTIVO_POR_TIPO_Y_AFILIADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionFovisPersonaUtils.obtenerClasificacionHijo())
			 			.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
						.setParameter(ConstantesValidaciones.NUMERO_IDENTIFICACION_AFILIADO, numeroIdentificacionAfiliado)
						.getResultList();

		        //se realiza la validación
				if(beneficiario != null && !beneficiario.isEmpty()){

					if(tipoBeneficiario.equals("HIJO_BIOLOGICO") ||tipoBeneficiario.equals("HIJASTRO") ||
					tipoBeneficiario.equals("HIJO_ADOPTIVO") ||tipoBeneficiario.equals("HERMANO_HUERFANO_DE_PADRES")){
						//validación fallida
						logger.debug("NO HABILITADA- Fin de método ValidadorPersonaComoHijoActivo.execute");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_ACTIVO,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);	
					} else {
						//validación fallida
						logger.debug("NO HABILITADA- Fin de método ValidadorPersonaComoHijoActivo.execute");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_ACTIVO,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
					}
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoHijoActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_ACTIVO);
				}
			}
			else{
				//si no es una novedad ejecutada sobre un beneficiario, se obtienen los datos que es de un afiliado principal
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM));
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				
		        
		        
		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<Beneficiario> beneficiario = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ACTIVO_POR_TIPO)
						.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionFovisPersonaUtils.obtenerClasificacionHijo())
						.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(beneficiario != null && !beneficiario.isEmpty()){
					//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorPersonaComoHijoActivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoHijoActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_ACTIVO);
				}
			}

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}