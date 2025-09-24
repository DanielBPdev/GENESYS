package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import javax.persistence.NoResultException;

/**
 * CLASE QUE VALIDA:
 * Habilita la novedad de incluir o excluir de acuerdo a si existe en 
 * la tabla CORE.[PersonaExclusionSumatoriaSalario]
 * 
 */
public class ValidadorPersonaSumatoriaSalarioConyuge extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorPersonaSumatoriaSalarioConyuge.execute");
		try{
			String objetoValidacion = datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM);
			TipoIdentificacionEnum tipoIdentificacion;
			String numeroIdentificacion;
			Boolean personaSumatoriaSalarios = null;
			String personaSumatoriaSalariosEstado = null;
			
			// Obtiene el tipo y numero de identificacion de la persona
			// Sino hay beneficiario, trae el afiliado
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null &&
					datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null){
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
			}else{
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			}
			
	        //se consulta el ID de la persona
			Long idPersona = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ID_PERSONA_TIPO_Y_NUM_IDENTIFICACION)
									.setParameter("tipoIdentificacion", tipoIdentificacion)
									.setParameter("numeroIdentificacion", numeroIdentificacion)
									.getSingleResult();

			//se consulta que la persona cuente con estado inactivo para poder mostrar la novedad
			try{
				personaSumatoriaSalariosEstado = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_SUMATORIA_SALARIOS_ESTADO_CONYUGE)
							.setParameter("idPersona", idPersona)
							.getSingleResult();		
			}catch(NoResultException nre){
				logger.info("Sin resultados en estado persona");
			}

			logger.info(personaSumatoriaSalariosEstado);

			if ("ACTIVO".equals(personaSumatoriaSalariosEstado)) {
				logger.info("entra aca");
				return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
			}

	        if(idPersona != null){	
				// Se valida que exista en la tabla [PersonaExclusionSumatoriaSalario] y se accede a su estado
				try{
					personaSumatoriaSalarios = (Boolean) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_SUMATORIA_SALARIOS)
						.setParameter("idPersona", idPersona)
						.getSingleResult();		
				}catch(NoResultException nre){
					logger.info("Sin resultados en personaSumatoriaSalarios");
				}
				if(personaSumatoriaSalarios != null){
					if(personaSumatoriaSalarios == true){
						return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE);
					}
				}
	        }

			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_SUMATORIA_SALARIO_CONYUGE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
