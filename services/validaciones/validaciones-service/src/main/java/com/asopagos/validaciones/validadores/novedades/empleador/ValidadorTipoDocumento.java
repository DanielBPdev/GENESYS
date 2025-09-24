package com.asopagos.validaciones.validadores.novedades.empleador;

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
 * 
 * Validador 5	Validar tipo de documento de identificación para cambio de tipo y/o número
 * 
 * Valida:
 * "El valor previo del campo ""Tipo de documento de
 * identificación"" del empleador sujeto de la novedad debe ser: 
 * -NIT
 * -Cédula de ciudadanía
 * -Tarjeta de identidad
 * -Cédula de extranjería
 * -Registro civil
 * -Carné diplomático.
 * -Pasaporte.
 * -Salvoconducto de permanencia.
 * -Permiso especial de permanencia.
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorTipoDocumento extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTipoDocumentos.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        
			 List<TipoIdentificacionEnum> documentosValidos = new ArrayList<TipoIdentificacionEnum>();
			 	documentosValidos.add(TipoIdentificacionEnum.NIT);
			 	documentosValidos.add(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
		        documentosValidos.add(TipoIdentificacionEnum.TARJETA_IDENTIDAD);
		        documentosValidos.add(TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
		        documentosValidos.add(TipoIdentificacionEnum.REGISTRO_CIVIL);
		        documentosValidos.add(TipoIdentificacionEnum.CARNE_DIPLOMATICO);
		        documentosValidos.add(TipoIdentificacionEnum.PASAPORTE);
		        documentosValidos.add(TipoIdentificacionEnum.SALVOCONDUCTO);
		        documentosValidos.add(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
			    documentosValidos.add(TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
			
	        // Se valida la condición
			if(documentosValidos.contains(tipoIdentificacion)){
				logger.info("VALIDACION EXITOSA- Fin de método ValidadorTipoDocumentos.execute");
				// Validación exitosa, Validador 5	Validar tipo de documento de identificación para * * cambio de tipo y/o número
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorTipoDocumentos.execute");
				// Validación no aprobada, Validador 5	Validar tipo de documento de identificación para * * cambio de tipo y/o número
				return crearValidacion(myResources.getString(ConstantesValidaciones.TIPO_IDENT_DIFERENTE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}


