package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.PersonaDTO;
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
 * la persona sujeto de la novedad tiene tipo de identificación "Tarjeta de identidad" 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorTipoDocumentoTarjetaIdentidad extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTipoDocumentoTarjetaIdentidad.execute");
		try{
			//el tipo de documento 
			TipoIdentificacionEnum tipoIdentificacion;
	        //el numero de identificacion actual del afiliado
			String numeroIdentificacion;
			
			//se verifica si vienen datos de un beneficiario, y de ser así, se asume que la validación
			//se ejecutará sobre este y no sobre el afiliado principal
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null 
					&& datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != ""
					&& datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null 
					&& datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != ""){
				
				//el tipo de documento al que se quiere cambiar (CEDULA_CIUDADANIA)
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        //el numero de identificacion actual del afiliado
				numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
				
			}
			else{
				//el tipo de documento al que se quiere cambiar (CEDULA_CIUDADANIA)
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        //el numero de identificacion actual del afiliado
				numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			}
	        
	        //se consulta la persona con el tipo y número de documento
	        PersonaDTO persona = (PersonaDTO) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_DOCUMENTO)
	    			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
	    			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
	    			.getSingleResult();
	        
	        //se listan los estados válidos para aprobar la validación
	        List<TipoIdentificacionEnum> tipo = new ArrayList<TipoIdentificacionEnum>();
	        tipo.add(TipoIdentificacionEnum.TARJETA_IDENTIDAD);
	        
			if(persona != null && tipo.contains(persona.getTipoIdentificacion())){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorTipoDocumentoTarjetaIdentidad.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_TARJETA_IDENTIDAD);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorTipoDocumentoTarjetaIdentidad.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TIPO_DOCUMENTO_TARJETA_IDENTIDAD),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_TARJETA_IDENTIDAD,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch(NoResultException nre)
		{
			//validación fallida
			logger.debug("NO HABILITADA- Fin de método ValidadorTipoDocumentoTarjetaIdentidad.execute");
			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TIPO_DOCUMENTO_TARJETA_IDENTIDAD),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_TARJETA_IDENTIDAD,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_TARJETA_IDENTIDAD, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}