package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.EstadosUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

 /**
 *
 * Validador 4. Validar estado de afiliación (caso activo, inactivo o no formalizado)
 * 
 * Condiciones necesarias para habilitar la novedad:
 * 
 * Valida:
 * "El valor del campo ""Estado de afiliación"" debe ser alguno de los siguientes: 
 * -""Activo"", 
 * -""Inactivo""
 * -""No formalizado - Retirado con aportes""
 * -""No formalizado - Con información"""
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEstadoAfiliacion extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEstaAfilActInactNoFormalizado.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();

            List<ConsultarEstadoDTO> listConsulta = new ArrayList<ConsultarEstadoDTO>();
            ConsultarEstadoDTO paramsConsulta = new ConsultarEstadoDTO();
            paramsConsulta.setEntityManager(entityManager);
            paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
            paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
            paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
            listConsulta.add(paramsConsulta);

            List<EstadoDTO> listEstado = EstadosUtils.consultarEstadoCaja(listConsulta);
            empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(listEstado.get(0).getEstado().toString()));
			
	        
			// Se listan los estados aceptados para la ejecución 
	        // exitosa de la validación y habilitación de la novedad
			List<EstadoEmpleadorEnum> estadosValidos = new ArrayList<EstadoEmpleadorEnum>();
	        estadosValidos.add(EstadoEmpleadorEnum.ACTIVO);
	        estadosValidos.add(EstadoEmpleadorEnum.INACTIVO);
	        estadosValidos.add(EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
	        estadosValidos.add(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
	        
	        // Se valida la condición
			if(estadosValidos.contains(empleador.getEstadoEmpleador())){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEstaAfilActInactNoFormalizado.execute");
				// Validación exitosa, Validador 4. Validar estado de afiliación (caso activo, inactivo o no formalizado)
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_AFILIACION);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEstaAfilActInactNoFormalizado.execute");
				// Validación no aprobada, Validador 4. Validar estado de afiliación (caso activo, inactivo o no formalizado)
				return crearValidacion(myResources.getString(ConstantesValidaciones.ESTADO_AFILIACION_DIFERENTE_ACTIVO_INACTIVO_NO_FORMALIZADO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_AFILIACION,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_ESTADO_AFILIACION, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
