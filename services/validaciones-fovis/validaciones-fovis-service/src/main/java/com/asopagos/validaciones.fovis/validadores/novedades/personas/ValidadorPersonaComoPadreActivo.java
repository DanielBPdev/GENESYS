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
 * si la persona está registrada como beneficiario padre y estado activo
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaComoPadreActivo extends ValidadorFovisAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaComoPadreActivo.execute");
		try{
			//se verifica que la novedad se ejecuta sobre un beneficiario
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!= null && 
					datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!= null){
				
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
		        
		        
		        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<Beneficiario> beneficiario = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ACTIVO_POR_TIPO)
						.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionFovisPersonaUtils.obtenerClasificacionPadre())
						.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(beneficiario != null && !beneficiario.isEmpty()){
					//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorPersonaComoPadreActivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_PADRE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_PADRE_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoPadreActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_PADRE_ACTIVO);
				}
			}
			else{
				//si no es una novedad ejecutada sobre un beneficiario, se obtienen los datos que es de un afiliado principal
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
		        
		        
		      //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado
		        List<Beneficiario> beneficiario = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ACTIVO_POR_TIPO)
						.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionFovisPersonaUtils.obtenerClasificacionPadre())
						.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
						.getResultList();

		        //se realiza la validación
				if(beneficiario != null && !beneficiario.isEmpty()){
					//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorPersonaComoPadreActivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_PADRE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_PADRE_ACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
				}else{
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaComoPadreActivo.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_PADRE_ACTIVO);
				}
			}

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_PADRE_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}