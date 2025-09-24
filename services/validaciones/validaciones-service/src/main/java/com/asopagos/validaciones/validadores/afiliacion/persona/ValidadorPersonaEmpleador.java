package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para si existe una persona registrada como empleador (activo). 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaEmpleador extends ValidadorAbstract {
	
	/**
	  * Referencia al logger
	  */
	 private ILogger logger = LogManager.getLogger(ValidadorPersonaEmpleador.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaEmpleador.execute");
		try{
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")){
					boolean existe = false;
					List<Empleador> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_POR_TIPO_NUMERO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.ESTADO_EMPLEADOR_PARAM,EstadoEmpleadorEnum.ACTIVO)
							.getResultList();
					if(personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()){
						List<Empleador> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_POR_NUMERO_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.ESTADO_EMPLEADOR_PARAM,EstadoEmpleadorEnum.ACTIVO)
								.getResultList();
						if(personasConNumero != null && !personasConNumero.isEmpty()){
							existe = true;
						}
					}else{
						existe = true;
					}
					if(existe){
						logger.debug("No aprobada- Existe un empleador con esa persona");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_EMPLEADOR),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR,TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);					
					}
					
				}else{
					logger.debug("NO EVALUADO - No hay parametros");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.debug("NO EVALUADO- no hay valores en el map");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR);
		}catch(Exception e){
			logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada",e);
			return crearMensajeNoEvaluado();
		}
	}

	

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * @return validacion afiliaacion instanciada.
	 */
	private  ValidacionDTO crearMensajeNoEvaluado(){
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_EMPLEADOR),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_EMPLEADOR,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
