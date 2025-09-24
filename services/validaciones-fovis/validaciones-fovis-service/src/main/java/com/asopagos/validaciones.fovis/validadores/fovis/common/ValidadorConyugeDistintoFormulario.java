package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si el jefe de hogar registra en el formulario FOVIS a su conyuge activo
 * y este es distinto al que se encuentra registrado en base de datos (v34)
 * 
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorConyugeDistintoFormulario extends ValidadorFovisAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorConyugeDistintoFormulario.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String tipoIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				String numeroIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				String objetoValidacion = datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM);

				/*
				 * Evalua si los datos de tipoIdentificacion y
				 * numeroIdentificacion no estan nulos o vacios
				 */
				if ((tipoIdentificacionBeneficiario != null && !tipoIdentificacionBeneficiario.equals(""))
						&& (numeroIdentificacionBeneficiario != null && !numeroIdentificacionBeneficiario.equals(""))
						&& (numeroIdentificacionJefeHogar != null && !numeroIdentificacionJefeHogar.equals(""))
						&& (tipoIdentificacionJefeHogar != null && !tipoIdentificacionJefeHogar.equals(""))) {
						if (objetoValidacion.equals(ClasificacionEnum.CONYUGE_HOGAR.name()) && tieneConyugeDistintoRegistrado(tipoIdentificacionBeneficiario,
								numeroIdentificacionBeneficiario, tipoIdentificacionJefeHogar,
								numeroIdentificacionJefeHogar)) {
							return crearValidacion(
									myResources.getString(
											ConstantesValidaciones.KEY_CONYUGE_FORMULARIO_DISTINTO_BD),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_CONYUGE_DISTINTO_FORMULARIO,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CONYUGE_DISTINTO_FORMULARIO);

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
						+ myResources.getString(ConstantesValidaciones.KEY_CONYUGE_FORMULARIO_DISTINTO_BD),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_CONYUGE_DISTINTO_FORMULARIO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Metodo que evalua si el solictante señala un conyugee en el formulario FOVIS 
	 * y este se encuentra registrado en la base de datos como su conyuge
	 * 
	 * @param tipoIdentificacionBeneficiario: Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario: Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar: Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar: Numero de identificacion del jefe de hogar
	 * @return true si la madre no se encuentra registrada en la base de datos como madre
	 */
	private boolean tieneConyugeDistintoRegistrado(String tipoIdentificacionBeneficiario,
			String numeroIdentificacionBeneficiario, String tipoIdentificacionJefeHogar,
			String numeroIdentificacionJefeHogar) {
		try {
			logger.debug(
					"Inicio de método ValidadorConyugeDistintoFormulario.tieneMadreRegistrada");
			/* se verifica si está al día con el tipo y número de documento */
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_PERSONA_REGISTRA_CONYUGE_FOVIS_DISTINTO_BD)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, TipoBeneficiarioEnum.CONYUGE.name())
					.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name())
					.getSingleResult();
			logger.debug("Fin de método ValidadorConyugeDistintoFormulario.tieneMadreRegistrada");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
