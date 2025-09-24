package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASAE QUE VALIDA:
 * tipo de documento de identificación para cambio de tipo y/o número
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorTipoDocumento extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTipoDocumento.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion;
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!=null)
			{
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
			}
			else{
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			}
	        
			List<TipoIdentificacionEnum> documentosValidos = new ArrayList<TipoIdentificacionEnum>();
			documentosValidos.add(TipoIdentificacionEnum.REGISTRO_CIVIL);
			documentosValidos.add(TipoIdentificacionEnum.TARJETA_IDENTIDAD);
			documentosValidos.add(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
			documentosValidos.add(TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
			documentosValidos.add(TipoIdentificacionEnum.PASAPORTE);
			documentosValidos.add(TipoIdentificacionEnum.CARNE_DIPLOMATICO);
			documentosValidos.add(TipoIdentificacionEnum.SALVOCONDUCTO);
			documentosValidos.add(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
			documentosValidos.add(TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
			
	        // Se valida la condición
			if(documentosValidos.contains(tipoIdentificacion)){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorTipoDocumento.execute");
				
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_PERSONA);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorTipoDocumento.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TIPO_DOCUMENTO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_PERSONA,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_PERSONA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
