package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

public class ValidadorPersonaSancionadaFovis extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {

		logger.debug("Inicio de método ValidadorPersonaHabilitadaSubsidioFovis.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String objetoValidacion = datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
					if (objetoValidacion.equals(ClasificacionEnum.JEFE_HOGAR.name())) {
						
						if (jefeHogarInhabilitadoSubsidio(tipoIdentificacion, numeroIdentificacion)) {
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.KEY_PERSONA_SANCIONADA_FOVIS),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						}
						
					}else{
						if (beneficiarioInhabilitadoSubsidio(tipoIdentificacion, numeroIdentificacion)) {
							
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.KEY_PERSONA_SANCIONADA_FOVIS),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS);

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
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}
	
	/**
	 * Valida si el jefe de hogar se encuentra habilitado para subsidio de vivienda FOVIS
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	private boolean jefeHogarInhabilitadoSubsidio(String tipoIdentificacion, 
			String numeroIdentificacion){
		
		logger.debug("Inicio de método ValidadorPersonaHabilitadaSubsidioFovis.jefeHogarInhabilitadoSubsidio");
		try {
	
			entityManager.createNativeQuery(NamedQueriesConstants.CONSULTAR_JEFE_HOGAR_INHABILITADO_SUBSIDIO_FOVIS)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.INHABILITADO_SUBSIDIO_PARAM, NumerosEnterosConstants.UNO)
			.getSingleResult();
			 
			logger.debug("Fin de método ValidadorPersonaHabilitadaSubsidioFovis.jefeHogarInhabilitadoSubsidio");
			return true;
		} catch (NoResultException nre) {
			return false;
		}
	}
	/**
	 * Valida si el integrante del hogar se encuentra habilitado para subsidio de vivienda FOVIS
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	private boolean beneficiarioInhabilitadoSubsidio(String tipoIdentificacion, 
			String numeroIdentificacion){
		
		logger.debug("Inicio de método ValidadorPersonaHabilitadaSubsidioFovis.beneficiarioInhabilitadoSubsidio");
		try {
	
			entityManager.createNativeQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_INHABILITADO_SUBSIDIO_FOVIS)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.INHABILITADO_SUBSIDIO_PARAM, NumerosEnterosConstants.UNO)
			.getSingleResult();
			 
			logger.debug("Fin de método ValidadorPersonaHabilitadaSubsidioFovis.beneficiarioInhabilitadoSubsidio");
			return true;
		} catch (NoResultException nre) {
			return false;
		}
	}
}
