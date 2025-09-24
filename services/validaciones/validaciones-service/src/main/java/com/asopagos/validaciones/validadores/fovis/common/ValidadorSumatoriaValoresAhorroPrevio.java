package com.asopagos.validaciones.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica que la sumatoria de los valores de ahorro previo sean superiores
 * al minimo parametrizado (v30)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorSumatoriaValoresAhorroPrevio extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorSumatoriaValoresAhorroPrevio.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);

				/*
				 * Evalua si los datos de tipoIdentificacion y
				 * numeroIdentificacion no estan nulos o vacios
				 */
				if ((numeroIdentificacion != null && !numeroIdentificacion.equals(""))
						&& (tipoIdentificacion != null && !tipoIdentificacion.equals(""))) {

					if (!ahorroPrevioValido(tipoIdentificacion, numeroIdentificacion)) {
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_SUMATORIA_AHORRO_PREVIO_INFERIOR_MINIMO_PARAMETRIZADO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_SUMATORIA_VALORES_AHORRO_PREVIO,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SUMATORIA_VALORES_AHORRO_PREVIO);

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
						+ myResources.getString(ConstantesValidaciones.KEY_SUMATORIA_AHORRO_PREVIO_INFERIOR_MINIMO_PARAMETRIZADO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_SUMATORIA_VALORES_AHORRO_PREVIO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Metodo que verifica si el valor del ahorro previo es superior al minimo parametrizado
	 * 
	 * @param tipoIdentificacionBeneficiario: Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario: Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar: Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar: Numero de identificacion del jefe de hogar
	 * @return true si el ahorro previo es superior al minimo parametrizado
	 */
	private boolean ahorroPrevioValido(String tipoIdentificacion,
			String numeroIdentificacion) {
		try {
			logger.debug("Inicio de método ValidadorSumatoriaValoresAhorroPrevio.tieneCicloAsignacioVigente");
			/* se verifica si está al día con el tipo y número de documento */
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_AHORRO_MINIMO)
					.setParameter(ConstantesValidaciones.NOMBRE_PARAMETRO_FOVIS,ParametroFOVISEnum.VALOR_MINIMO_AHORRO_PREVIO.name())
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			logger.debug("Fin de método ValidadorSumatoriaValoresAhorroPrevio.tieneCicloAsignacioVigente");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
