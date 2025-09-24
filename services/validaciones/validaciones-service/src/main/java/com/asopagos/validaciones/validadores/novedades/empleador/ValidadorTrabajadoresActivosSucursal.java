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
 * Validador 6	Validar que haya 0 trabajadores activos asociados a la sucursal
 * Valida:
 * El número de trabajadores activos asociados a la sucursal debe ser cero
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorTrabajadoresActivosSucursal extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTrabajActivosAsociadosSucursal.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        Long idSucursal  = Long.parseLong(datosValidacion.get(ConstantesValidaciones.ID_SUCURSAL_PARAM));
	        
	        // Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
	        
	        // Se consulta la cantidad de trabajadores activos asociados a la sucursal			
	     	Long cantidadTrabajadores = (Long) entityManager.createNamedQuery(
	     			NamedQueriesConstants.CONSULTA_CANTIDAD_TRABAJADORES_ASOCIADOS_SUCURSAL_BY_ESTADO)
	     				.setParameter(ConstantesValidaciones.NAMED_QUERY_PARAM_ID_EMPLEADOR, empleador.getIdEmpleador())
	     				.setParameter(ConstantesValidaciones.ID_SUCURSAL_PARAM, idSucursal)
	     				.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO).getSingleResult();
	        
	        // Se valida la condición
			if(cantidadTrabajadores == NumerosEnterosConstants.CERO){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorTrabajActivosAsociadosSucursal.execute");
				// Validación exitosa, Validador 6	Validar que haya 0 trabajadores activos asociados a la sucursal
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS_SUCURSAL);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorTrabajActivosAsociadosSucursal.execute");
				// Validación no aprobada, Validador 6	Validar que haya 0 trabajadores activos asociados a la sucursal
				return crearValidacion(myResources.getString(ConstantesValidaciones.TRABAJADORES_NO_SON_CERO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS_SUCURSAL,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS_SUCURSAL, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
