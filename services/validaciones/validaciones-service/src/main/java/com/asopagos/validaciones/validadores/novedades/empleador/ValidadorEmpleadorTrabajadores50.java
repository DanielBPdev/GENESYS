package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

 /**
 *
 * Validador 10	Validar que haya 50 o menos trabajadores activos asociados al empleador
 * Valida:
 * El empleador tiene 50 o menos trabajadores activos
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorTrabajadores50 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTrabajActivosAsociadosEmpleador50.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
	        
			// Se consulta la cantidad de trabajadores activos asociados a la sucursal			
	     	Long cantidadTrabajadores = (Long) entityManager.createNamedQuery(
	     			NamedQueriesConstants.CONTAR_TRABAJADORES_ACTIVOS_ASOCIADOS_SUCURSAL)
	     				.setParameter(ConstantesValidaciones.NAMED_QUERY_PARAM_ID_EMPLEADOR, empleador.getIdEmpleador())
	     				.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO).getSingleResult();
	        
	        // Se valida la condición
			if(cantidadTrabajadores <= NumerosEnterosConstants.CINCUENTA){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorTrabajActivosAsociadosEmpleador50.execute");
				// Validación exitosa, Validador 10	Validar que haya 50 o menos trabajadores activos asociados al empleador
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_TRABAJADORES_50);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorTrabajActivosAsociadosEmpleador50.execute");
				// Validación no aprobada, Validador 10	Validar que haya 50 o menos trabajadores activos asociados al empleador
				return crearValidacion(myResources.getString(ConstantesValidaciones.EMPLEADOR_CON_MAS_DE_50_TRABAJADORES),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_TRABAJADORES_50,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_TRABAJADORES_50, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
