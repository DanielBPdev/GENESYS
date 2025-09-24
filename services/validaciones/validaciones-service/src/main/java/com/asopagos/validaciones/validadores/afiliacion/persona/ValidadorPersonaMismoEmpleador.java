package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaMismoEmpleador extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaGrupoFamiliar.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				
				String tipoIdEmpleador = datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM);
				TipoIdentificacionEnum tipoIdentificacionEmpleador = TipoIdentificacionEnum.valueOf(tipoIdEmpleador);
				String numeroIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
				
				if (tipoIdentificacion != null && tipoIdentificacionEmpleador != null
						&& numeroIdentificacionEmpleador != null && numeroIdentificacion != null) {
					boolean existe = false;
					
					if(tipoIdentificacion.equals(tipoIdentificacionEmpleador) && numeroIdentificacion.equals(numeroIdentificacionEmpleador)){
						existe = true;
					}
					if (existe) {
						logger.debug("No aprobada- La persona es el mismo empleador con cierto tipo de solicitante");
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_MISMO_EMPLEADOR),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_MISMO_EMPLEADOR_SOLICITANTE,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
					}

				} else {
					logger.debug("NO EVALUADO - No hay parametros");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("NO EVALUADO- no hay valores en el map");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MISMO_EMPLEADOR_SOLICITANTE);
		} catch (Exception e) {
			logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada", e);
			return crearMensajeNoEvaluado();
		}
	}

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_MISMO_EMPLEADOR),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_MISMO_EMPLEADOR_SOLICITANTE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
