package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 *
 * Clase que implementa la lógica de la validación para un empleador con al menos dos sucursales.
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorDosSucursales extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadorDosSucursales.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        List<SucursalEmpresa> sucursalesEmpresa = (List<SucursalEmpresa>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSALES_EMPLEADOR)
	        		.setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacion)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacion)
	        		.getResultList();
	        
	        // Se valida la condición
			if(!sucursalesEmpresa.isEmpty() && sucursalesEmpresa.size()>=2){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorDosSucursales.execute");
				// Empleador tiene al menos dos sucursales
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_DOS_SUCURSALES);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorDosSucursales.execute");
				// Empleador no tiene al menos dos sucursales
				///TODO CAMBIAR MENSAJE
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_DOS_SUCURSALES),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_DOS_SUCURSALES,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_DOS_SUCURSALES, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}