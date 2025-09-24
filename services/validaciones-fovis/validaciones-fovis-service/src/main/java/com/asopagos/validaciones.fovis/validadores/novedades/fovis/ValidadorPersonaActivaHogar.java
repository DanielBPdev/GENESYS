package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Objetivo :  Validar estado de la persona sea ACTIVO con respecto al hogar
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPersonaActivaHogar extends ValidadorFovisAbstract {


	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaActivaHogar.execute");
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
							
					if (objetoValidacion.equals(ClasificacionEnum.JEFE_HOGAR.name())) {

						if (!personaJefeHogarActiva(tipoIdentificacionJefeHogar, numeroIdentificacionJefeHogar)) {
							return crearValidacion(
									myResources
											.getString(ConstantesValidaciones.KEY_PERSONA_NO_ACTIVA_HOGAR),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PERSONA_ACTIVA_HOGAR,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						}

					} else {
						if (!personaItegranteHogarActiva(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario, tipoIdentificacionJefeHogar, numeroIdentificacionJefeHogar)) {

							return crearValidacion(
									myResources
											.getString(ConstantesValidaciones.KEY_PERSONA_NO_ACTIVA_HOGAR),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PERSONA_ACTIVA_HOGAR,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						}
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_ACTIVA_HOGAR);

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
						+ myResources.getString(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_PERSONA_ACTIVA_HOGAR,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}
	/**
	 * Método que valida si un jefe de hogar se encuentra en estado activo con respecto al hogar
	 * @param tipoIdentificacionBeneficiario
	 * @param numeroIdentificacionBeneficiario
	 * @param tipoIdentificacionJefeHogar
	 * @param numeroIdentificacionJefeHogar
	 * @return
	 */
	private boolean personaJefeHogarActiva(String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar) {
		try {
			logger.debug("Inicio de método ValidadorPersonaActivaHogar.personaBeneficiarioActiva");
			
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_ACTIVO)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.ESTADO_JEFE_HOGAR, EstadoFOVISHogarEnum.ACTIVO.name())
					.getSingleResult();
			logger.debug("Fin de método ValidadorPersonaActivaHogar.personaBeneficiarioActiva");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}


	/**
	 * Método que valida si un integrante de hogar se encuentra en estado activo con respecto al hogar
	 * @param tipoIdentificacionBeneficiario
	 * @param numeroIdentificacionBeneficiario
	 * @param tipoIdentificacionJefeHogar
	 * @param numeroIdentificacionJefeHogar
	 * @return
	 */
	private boolean personaItegranteHogarActiva(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario,
			String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar) {
		try {
			logger.debug("Inicio de método ValidadorPersonaActivaHogar.personaBeneficiarioActiva");
			
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_INTEGRANTE_HOGAR_ACTIVO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.ESTADO_INTEGRANTE_HOGAR, EstadoFOVISHogarEnum.ACTIVO.name())
					.getSingleResult();
			logger.debug("Fin de método ValidadorPersonaActivaHogar.personaBeneficiarioActiva");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
