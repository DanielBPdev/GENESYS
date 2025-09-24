package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * valida que los datos ingresados no correspondan 
 * a otro usuario ya registrado en el sistema
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorCambioTipoNumeroUnicaPersona extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorCambioTipoNumeroUnicaPersona.execute");
		try{
			
			TipoIdentificacionEnum tipoIdentificacion = null;
	        String numeroIdentificacion = null;
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!=null && datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!=null){
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
			}else{
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_CAMBIO_PARAM));
				numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_CAMBIO_PARAM);
			}
	        
	        //se consulta el afiliado con el tipo y número de documento
	        Persona persona = (Persona) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();

	        //se realiza la validación
			if(persona != null){
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorCambioTipoNumeroUnicaPersona.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_DOCUMENTO_UNICA_PERSONA),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_DOCUMENTO_UNICA_PERSONA,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}else{
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorCambioTipoNumeroUnicaPersona.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DOCUMENTO_UNICA_PERSONA);	
			}
		} 
		catch (NoResultException nre) {
			//validación exitosa
			logger.debug("HABILITADA- Fin de método ValidadorCambioTipoNumeroUnicaPersona.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DOCUMENTO_UNICA_PERSONA);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_DOCUMENTO_UNICA_PERSONA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}