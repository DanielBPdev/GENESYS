package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Que se haya pagado subsidio monetario por el/los beneficiarios del trabajador dependiente
 *  
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPagoSubsidioBenefDependiente extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPagoSubsidioBenefDependiente.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        Long beneficiariosConSubsidio = 0L;
	        
	        if(tipoIdentificacion != null && numeroIdentificacion != null){
	        	beneficiariosConSubsidio += (Long) entityManager.createNamedQuery(
		     			NamedQueriesConstants.CONTAR_BENEFICIARIOS_AFILIADO_CON_SUBSIDIO)
	     				.setParameter("tipoIdAfiliado", tipoIdentificacion.name())
	     				.setParameter("numeroIdAfiliado", numeroIdentificacion)
	     				.getSingleResult();
	        }
	        
	        //se realiza la validación
			if(beneficiariosConSubsidio != null && beneficiariosConSubsidio > 0){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorPagoSubsidioBenefDependiente.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PAGO_SUBCIDIO_BENEFICIARIO_DEP);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorPagoSubsidioBenefDependiente.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PAGO_SUBCIDIO_BENEFICIARIO_DEP),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_PAGO_SUBCIDIO_BENEFICIARIO_DEP,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PAGO_SUBCIDIO_BENEFICIARIO_DEP, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}