package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Validar que el afiliado principal en el campo "Estado civil" 
 * tenga los valores "Soltero", "Viudo" para poder hacer el registro de la novedad
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoSolteroViudo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoSolteroViudo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se listan los estados válidos para aprobar la validación
	        List<EstadoCivilEnum> estadosValidos = new ArrayList<EstadoCivilEnum>();
	        estadosValidos.add(EstadoCivilEnum.SOLTERO);
	        estadosValidos.add(EstadoCivilEnum.VIUDO);
	        
	        //se consulta el afiliado con el tipo y número de documento
	        EstadoCivilEnum estadoCivilAfiliado= (EstadoCivilEnum) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			
			if(estadoCivilAfiliado != null && estadosValidos.contains(estadoCivilAfiliado)){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoSolteroViudo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_SOLTERO_VIUDO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoSolteroViudo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_SOLTERO_VIUDO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_AFILIADO_SOLTERO_VIUDO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_SOLTERO_VIUDO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
