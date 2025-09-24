package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 33	Ejecutar la HU-13-496 Registro de novedades automáticas
Valida:
Al terminar de efectuar las validaciones previamente descritas, se debe ejecutar la HU-13-496 Registro de novedades automáticas.
 *  
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEjecutarProceso13HU496 extends ValidadorAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorEjecutarProceso13HU496.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
                    //TODO se debe invocar metodo para calcular metodo del empleador
                    //empleador.setEstadoEmpleador(calcularEstadoEmpleador(empleador));
	        
			// Se listan los estados aceptados para la ejecución 
	        // exitosa de la validación y habilitación de la novedad
			List<EstadoEmpleadorEnum> estadosValidos = new ArrayList<EstadoEmpleadorEnum>();
	        estadosValidos.add(EstadoEmpleadorEnum.ACTIVO);
	        estadosValidos.add(EstadoEmpleadorEnum.INACTIVO);
	        estadosValidos.add(EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
	        estadosValidos.add(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
	        
	        // Se valida la condición
			if(true){ // PENDIENTE
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEjecutarProceso13HU496.execute");
				// Validación exitosa, Validador 33	Ejecutar la HU-13-496 Registro de novedades automáticas
				return null; //crearMensajeExitoso(ValidacionCoreEnum.VALIDAR_ESTADO_AFILIACION);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEjecutarProceso13HU496.execute");
				// Validación no aprobada, Validador 33	Ejecutar la HU-13-496 Registro de novedades automáticas
				return null; // crearValidacion(myResources.getString(ConstantesValidaciones.ESTADO_AFILIACION_DIFERENTE_ACTIVO_INACTIVO_NO_FORMALIZADO),ResultadoValidacionEnum.NO_APROBADA,
//						ValidacionCoreEnum.VALIDAR_ESTADO_AFILIACION,
//						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return null; // crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
//					ValidacionCoreEnum.VALIDAR_ESTADO_AFILIACION, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
