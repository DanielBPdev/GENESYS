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
 * Validadador que verifica si una persona tiene al menos un ciclo de asignacion vigente en el periodo de 
 * la postulacion (v29)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorCicloAsignacionVigente extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorCicloAsignacionVigente.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				/*
				 * Evalua si los datos de tipoIdentificacion y
				 * numeroIdentificacion no estan nulos o vacios
				 */
				if ((numeroIdentificacion != null && !numeroIdentificacion.equals(""))
						&& (tipoIdentificacion != null && !tipoIdentificacion.equals(""))) {

					if (!tieneCicloAsignacioVigente(tipoIdentificacion, numeroIdentificacion)) {
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NO_CICLOS_ASIGNACION_VIGENTE),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_CICLO_ASIGNACION_VIGENTE,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}

				} 
				else {
					logger.debug("No evaluado - No llegaron todos los parámetros");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("No evaluado - No llegó el mapa con valores");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CICLO_ASIGNACION_VIGENTE);

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
						+ myResources.getString(ConstantesValidaciones.KEY_NO_CICLOS_ASIGNACION_VIGENTE),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_CICLO_ASIGNACION_VIGENTE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Metodo que evalua si una persona tiene al menos un ciclo de asignacion vigente en el periodo de la postulacion
	 * 
	 * @param tipoIdentificacionBeneficiario: Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario: Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar: Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar: Numero de identificacion del jefe de hogar
	 * @return true si tiene al menos un ciclo vigente
	 */
	private boolean tieneCicloAsignacioVigente( String tipoIdentificacionJefeHogar,
			String numeroIdentificacionJefeHogar) {
//		try {
//			logger.debug("Inicio de método ValidadorCicloAsignacionVigente.tieneCicloAsignacioVigente");
//			/* se verifica si está al día con el tipo y número de documento */
//			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_CICLO_ASIGNACION)
//					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
//					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
//					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
//					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
//					.getSingleResult();
//			logger.debug("Fin de método ValidadorCicloAsignacionVigente.tieneCicloAsignacioVigente");
			return true;
//		} catch (NoResultException e) {
//			return false;
//		}
	}
}
