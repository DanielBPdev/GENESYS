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
 * Validador 46. Validar estado de afiliación del empleador diferente a "Activo"
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorEmpleadorDiferenteActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadorDiferenteActivo.execute");
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
			List<EstadoEmpleadorEnum> estadosInvalidos = new ArrayList<EstadoEmpleadorEnum>();
			estadosInvalidos.add(EstadoEmpleadorEnum.ACTIVO);
	        
	        // Se valida la condición
			if(estadosInvalidos.contains(empleador.getEstadoEmpleador())){
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorDiferenteActivo.execute");
				// Validación no aprobada, Validador 4. Validar estado de afiliación (caso activo, inactivo o no formalizado)
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_ESTADO_AFILIACION_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_DIFERENTE_ACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}else{
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorDiferenteActivo.execute");
				// Validación exitosa, Validador 4. Validar estado de afiliación (caso activo, inactivo o no formalizado)
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_DIFERENTE_ACTIVO);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_DIFERENTE_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
