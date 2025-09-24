package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.PersonaDTO;
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
 * Valor del campo "Estado civil" del afiliado principal debe tener alguno 
 * de los siguientes valores: "Soltero", "Separado/divorciado", "Viudo", "Vacío"
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEstadoCivilAfiliado extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEstadoCivilAfiliado.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se listan los estados válidos para aprobar la validación
	        List<EstadoCivilEnum> estadosValidos = new ArrayList<EstadoCivilEnum>();
	        estadosValidos.add(EstadoCivilEnum.SOLTERO);
	        estadosValidos.add(EstadoCivilEnum.SEPARADO);
	        estadosValidos.add(EstadoCivilEnum.DIVORCIADO);
	        estadosValidos.add(EstadoCivilEnum.VIUDO);
	        
	        //se consulta el afiliado con el tipo y número de documento
	        PersonaDTO Afiliado= (PersonaDTO) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_DOCUMENTO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
	        
			if(estadosValidos.contains(Afiliado.getEstadoCivil())){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorEstadoCivilAfiliado.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_CIVIL_AFILIADO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorEstadoCivilAfiliado.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_CIVIL_AFILIADO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_CIVIL_AFILIADO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_CIVIL_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
