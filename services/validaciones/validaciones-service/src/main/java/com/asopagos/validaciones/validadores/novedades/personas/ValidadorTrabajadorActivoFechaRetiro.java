package com.asopagos.validaciones.validadores.novedades.personas;

import java.math.BigInteger;
import java.util.Date;
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
 * Afiliado principal con estado activo respecto al tipo de afiliación frente a la caja de compensación para  la fecha en la cual se desea retirar?
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
public class ValidadorTrabajadorActivoFechaRetiro extends ValidadorAbstract{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTrabajadorActivoFechaRetiro.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));;
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            String idRolAfiliado = datosValidacion.get(ConstantesValidaciones.KEY_ID_ROL_AFILIADO);
            String fechaRetiro = datosValidacion.get(ConstantesValidaciones.FECHA_RETIRO_PARAM);
			System.out.println("Validando si el Trabajador se encontraba activo para la fecha de retiro asociada. "+fechaRetiro);

            
            if (fechaRetiro != null) {
            	Date fechaRetiroDate = new Date(new Long(fechaRetiro));
				System.out.println(fechaRetiroDate);
				System.out.println(idRolAfiliado);
            	// se consultan si el Trabajador se encontraba activo para la fecha de retiro asociada.
            	List<BigInteger> trabajadores = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_TRABAJADOR_ACTIVO_FECHA_RETIRO)
    					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
    					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
    					.setParameter(ConstantesValidaciones.KEY_ID_ROL_AFILIADO, idRolAfiliado)
    					.setParameter(ConstantesValidaciones.FECHA_RETIRO_PARAM, fechaRetiroDate)
    					.getResultList();
    			
    			// se verifica si para el grupo familiar encontró Beneficiarios si encuentra Habilita la Novedad.
    			if (trabajadores == null || trabajadores.isEmpty()){
    				
    				logger.debug("NO HABILITADA- Fin de método ValidadorTrabajadorActivoFechaRetiro.execute");
    				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TRABAJADOR_INACTIVO_FECHA_RETIRO),ResultadoValidacionEnum.NO_APROBADA,
    						ValidacionCoreEnum.VALIDACION_TRABAJADOR_ACTIVO_FECHA_RETIRO,
    						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
    					
    			}
    			//validación exitosa
    			logger.debug("HABILITADA- Fin de método ValidadorTrabajadorActivoFechaRetiro.execute");
    			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TRABAJADOR_ACTIVO_FECHA_RETIRO);	
			} else {
				logger.debug("No evaluado- No llegaron todos los parametros");
				return crearMensajeNoEvaluado();
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TRABAJADOR_ACTIVO_FECHA_RETIRO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
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
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_TRABAJADOR_ACTIVO_FECHA_RETIRO,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}

}
