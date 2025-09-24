package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 *
 * Validador 7	Validar que haya al menos 1 sucursal
 * Valida:
 * El empleador debe tener como mìnimo una sucursal "Activa"
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorExistenciaSucursal extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorExistenciaSucursal.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			
			// Se consultan la cantidad de sucursales asociadas al empleador
			Long cantidadSucursales = (Long) entityManager.createNamedQuery(
					NamedQueriesConstants.CONTAR_SUCURSALES_EMPLEADOR)
					.setParameter(ConstantesValidaciones.NAMED_QUERY_PARAM_ID_EMPRESA, empleador.getEmpresa().getIdEmpresa())
					.setParameter(ConstantesValidaciones.ESTADO_SUCURSAL, EstadoActivoInactivoEnum.ACTIVO)
					.getSingleResult();
	        
	        // Se valida la condición
			if(cantidadSucursales >= NumerosEnterosConstants.UNO){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorExistenciaSucursal.execute");
				// Validación exitosa, Validador 7	Validar que haya al menos 1 sucursal
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EXISTENCIA_SUCURSAL);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorExistenciaSucursal.execute");
				// Validación no aprobada, Validador 7	Validar que haya al menos 1 sucursal
				return crearValidacion(myResources.getString(ConstantesValidaciones.EMPLEADOR_SUCURSAL_ACTIVA),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EXISTENCIA_SUCURSAL,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EXISTENCIA_SUCURSAL, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
