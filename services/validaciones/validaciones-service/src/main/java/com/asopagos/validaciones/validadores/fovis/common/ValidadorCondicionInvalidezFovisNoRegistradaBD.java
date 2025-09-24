package com.asopagos.validaciones.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si una persona presenta una condicion de invalidez registrada en el formulario FOVIS
 * y la misma no se encuentra registrada en base de datos (v13)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorCondicionInvalidezFovisNoRegistradaBD extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorCondicionInvalidezFovisNoRegistradaEnBaseDeDatos.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {

					if (!tieneCondicionInvalidezRegistrada(tipoIdentificacion, numeroIdentificacion)) {
						
						return crearValidacion(
								myResources.getString(
										ConstantesValidaciones.KEY_PERSONA_CONDICION_INVALIDEZ_SIN_REGISTRAR_BD),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_BD_REGISTRADA_FOVIS,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
						
					}

				} else {
					logger.debug("No evaluado - No llegaron todos los parámetros");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("No evaluado - No llegó el mapa con valores");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_BD_REGISTRADA_FOVIS);

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado();
		}
	}

	/**
	 * Mensaje utilizado cuando por alguna razón no se puede evaluar.
	 * 
	 * @return validacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_CONDICION_INVALIDEZ_SIN_REGISTRAR_BD),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_BD_REGISTRADA_FOVIS,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Metodo que evalua si el solictante señala una condicion de invalidez en el formulario FOVIS y la misma
	 * no esta referida en la base de datos
	 * 
	 * @param tipoIdentificacion tipo de identificación del jefe de hogar.
	 * @param numeroIdentificacion número de identificación del jefe de hogar.
	 * @return true si el solicitante señala la condicion de invalidez en el formulario FOVIS y no la ha referido en la
	 * base de datos
	 */
	private boolean tieneCondicionInvalidezRegistrada(String tipoIdentificacion, String numeroIdentificacion) {
		try {
			logger.debug("Inicio de método ValidadorCondicionInvalidezFovisNoRegistradaEnBaseDeDato.noTieneCondicionInvalidezRegistrada");
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_PERSONA_INVALIDEZ)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
			logger.debug("Fin de método ValidadorCondicionInvalidezFovisNoRegistradaEnBaseDeDatos.noTieneCondicionInvalidezRegistrada");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

}
