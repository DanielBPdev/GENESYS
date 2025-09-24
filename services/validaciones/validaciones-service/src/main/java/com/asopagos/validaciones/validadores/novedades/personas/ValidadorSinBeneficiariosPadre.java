package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * Validar que no hay beneficiarios tipo "Padre" (ACTIVO) en todos los grupos familiares 
 * relacionados con el afiliado principal
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorSinBeneficiariosPadre extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorSinBeneficiariosPadre.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);       
	        
	        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado (activo o inactivo)
	        List <Beneficiario> beneficiario = (List <Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_POR_AFILIADO_TIPO_ESTADO)
			.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionPadre())
			.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerListaEstadoActivo())
    		.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
    		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.getResultList();
	        
			if(beneficiario == null || beneficiario.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorSinBeneficiariosPadre.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SIN_BENEFICIARIO_PADRE);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorSinBeneficiariosPadre.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_SIN_BENEFICIARIO_PADRE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_SIN_BENEFICIARIO_PADRE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_SIN_BENEFICIARIO_PADRE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}