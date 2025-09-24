package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si una persona presenta una condicion de invalidez registrada en en base de datos
 * y la misma no se encuentra referida en el formulario FOVIS (v12)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */

public class ValidadorCondicionInvalidezBdNoRegistradaFovis extends ValidadorFovisAbstract {

	/**Constante para identificar el valor activo del estado de la condicion de Invalidez*/
	private static final int ACTIVO = 1;
	
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorCondicionInvalidezBdNoRegistradaFovis.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {

					if (tieneCondicionInvalidezRegistrada(tipoIdentificacion, numeroIdentificacion)) {

						return crearValidacion(
								myResources.getString(
										ConstantesValidaciones.KEY_PERSONA_CONDICION_INVALIDEZ_SIN_SENALAR_EN_FOVIS),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD);

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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_CONDICION_INVALIDEZ_SIN_SENALAR_EN_FOVIS),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
	/**
	 * Metodo que evalua si el solictante registra una condicion de invalidez en la base de datos y la misma
	 * no esta referida en el formulario FOVIS
	 * 
	 * @param tipoIdentificacion tipo de identificación del jefe de hogar.
	 * @param numeroIdentificacion número de identificación del jefe de hogar.
	 * @return true si el solicitante registra la condicion de invalidez en base de datos y no la ha referido en el 
	 * formulario FOVIS
	 */
	private boolean tieneCondicionInvalidezRegistrada(String tipoIdentificacion, String numeroIdentificacion) {
		try {
			logger.debug("Inicio de método ValidadorCondicionInvalidezBdNoRegistradaFovis.noTieneCondicionInvalidezRegistrada");
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_PERSONA_INVALIDEZ)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.ESTADO_CONDICION_INVALIDEZ_PARAM, ACTIVO).getSingleResult();
			logger.debug("Fin de método ValidadorCondicionInvalidezBdNoRegistradaFovis.noTieneCondicionInvalidezRegistrada");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
