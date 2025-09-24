package com.asopagos.validaciones.validadores.novedades.personas;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
 * Beneficiario  con estado respecto al afiliado principal de activo para la fecha que se desea retirar
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
public class ValidadorBeneficiarioActivoFechaRetiro extends ValidadorAbstract{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorBeneficiarioActivoFechaRetiro.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion;
            String numeroIdentificacion;
            
            String fechaRetiro = datosValidacion.get(ConstantesValidaciones.FECHA_RETIRO_PARAM);
            if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null) {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
            }
            else {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            }
            if (fechaRetiro != null) {
            	Date fechaRetiroDate = new Date(new Long(fechaRetiro));
            	// se consultan si el Beneficiario se encontraba activo para la fecha de retiro asociada.
    			List<BigInteger> beneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_ACTIVO_FECHA_RETIRO)
    					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
    					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
    					.setParameter(ConstantesValidaciones.FECHA_RETIRO_PARAM, fechaRetiroDate)
    					.getResultList();
    			
    			// se verifica si para el grupo familiar encontró Beneficiarios si encuentra Habilita la Novedad.
    			if (beneficiarios == null || beneficiarios.isEmpty()){
    				
    				logger.info("NO HABILITADA- Fin de método ValidadorBeneficiarioActivoFechaRetiro.execute 1");
    				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_BENEFICIARIO_INACTIVO_FECHA_RETIRO),ResultadoValidacionEnum.NO_APROBADA,
    						ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_FECHA_RETIRO,
    						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
    					
    			}
    			//validación exitosa
    			logger.debug("HABILITADA- Fin de método ValidadorBeneficiarioActivoFechaRetiro.execute");
    			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_FECHA_RETIRO);	
			} else {
				logger.debug("No evaluado- No llegaron todos los parametros");
				return crearMensajeNoEvaluado();
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_FECHA_RETIRO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_PARAMS),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_FECHA_RETIRO,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}

}
