package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 39	Validar que el empleador no tenga asociado un acto 
 * administrativo de confirmación de su afiliación desde su más reciente activación.
 * Valida:
 * Validar que el empleador no tenga asociado un acto administrativo de 
 * confirmación de su afiliación desde su más reciente activación.
 *  
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorSinActoAdministrativo extends ValidadorAbstract {
	/*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorEmpleadorSinActoAdministrativo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        List<Empleador> empleadores 
	        		= (List<Empleador>) entityManager
	        				.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_NO_TIENE_ACTO_ADMINISTRATIVO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.ESTADO_EMPLEADOR_PARAM, EstadoEmpleadorEnum.ACTIVO)
					.setParameter(ConstantesValidaciones.RESULTADO_PROCESO_PARAM, ResultadoProcesoEnum.APROBADA)
					.getSingleResult();
	        
	        
	        // Se valida la condición
			if(empleadores == null || empleadores.isEmpty()){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorSinActoAdministrativo.execute");
				// Validación exitosa, Validador 39	Validar que el empleador no tenga...
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_SIN_ACTO_ADMINISTRATIVO);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorSinActoAdministrativo.execute");
				// Validación no aprobada, Validador 39	Validar que el empleador no tenga...
				return crearValidacion(myResources.getString(ConstantesValidaciones
						.KEY_EMPLEADOR_TIENE_ASOCIADO_ACTO_ADMINISTRATIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_SIN_ACTO_ADMINISTRATIVO, TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_SIN_ACTO_ADMINISTRATIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
