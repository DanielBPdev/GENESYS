package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar si una persona está afiliada
 * (activa) con el mismo empleador relacionado en la solicitud. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaAfiliadaMismoEmpleador extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaAfiliadaMismoEmpleador.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String tipoIdEmpleador = datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM);
				TipoIdentificacionEnum tipoIdentificacionEmpleador = TipoIdentificacionEnum.valueOf(tipoIdEmpleador);
				String numeroIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
				
				boolean existe = false;
				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))
						&& (tipoIdentificacionEmpleador != null && !tipoIdentificacionEmpleador.equals(""))
						&& (numeroIdentificacionEmpleador != null && !numeroIdentificacionEmpleador.equals(""))) {
					
					List<RolAfiliado> afiliacionesConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_ASOCIADO_POR_TIPO_NUMERO_AFILIADO_EMPLEADOR_TIPO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacionEmpleador)
							.setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacionEmpleador)
							.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
							.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
							.getResultList();
					
					if(afiliacionesConTipoYNumero != null && !afiliacionesConTipoYNumero.isEmpty()){
						// List<RolAfiliado> afiliacionesConNumero = entityManager
						// 		.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_ASOCIADO_POR_TIPO_AFILIADO_EMPLEADOR_TIPO_ESTADO)
						// 		.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						// 		.setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacionEmpleador)
						// 		.setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacionEmpleador)
						// 		.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
						// 		.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
						// 		.getResultList();
						//if(afiliacionesConNumero != null && !afiliacionesConNumero.isEmpty()){
							existe = true;
						//}
						
					}
					
					if(existe){
						logger.debug("No aprobada- Existe persona afiliada para el mismo empleador de tipo dependiente");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_TRABAJADOR_AFILIADA),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_MISMO_EMPLEADOR,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}
				}else{
					logger.debug("No evaluada-  No hay parametros");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.debug("No evaluada-  No esta lleno el map");
				return crearMensajeNoEvaluado();
			}
			/*exitoso*/
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MISMO_EMPLEADOR);
		} catch (Exception e) {
			logger.error("No evaluado ocurrió alguna excepción",e);
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
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_MISMO_EMPLEADOR,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
}
