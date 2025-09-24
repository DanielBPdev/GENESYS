package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Solo se inactiva al beneficiario hasta que el afiliado principal 
 * solo tenga un tipo de afiliación (trabajador dependiente, independiente o pensionado)
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorDesactivacionBeneficiario extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorDesactivacionBeneficiario.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

	        //se consultan los tipos de afiliado del afiliado principal
	        List <TipoAfiliadoEnum> tipoAfiliado = (List <TipoAfiliadoEnum>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_TIPOS_ASOCIADOS_AFILIADO_POR_NUMERO_Y_TIPO_DOCUMENTO)
    		.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
    		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.getResultList();
	        
	        //se realiza la validación
			if(tipoAfiliado != null && !tipoAfiliado.isEmpty() && tipoAfiliado.size() == 1){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorDesactivacionBeneficiario.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DESACTIVACION_BENEFICIARIO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorDesactivacionBeneficiario.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_DESACTIVACION_BENEFICIARIO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_DESACTIVACION_BENEFICIARIO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_DESACTIVACION_BENEFICIARIO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}