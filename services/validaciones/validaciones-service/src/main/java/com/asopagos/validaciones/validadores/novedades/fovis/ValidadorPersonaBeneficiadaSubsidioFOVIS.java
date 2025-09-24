package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica si una persona ha sido beneficiaria de subsidio FOVIS
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPersonaBeneficiadaSubsidioFOVIS extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {

		logger.debug("Inicio de método ValidadorPersonaBeneficiadaSubsidioFOVIS.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
					if (personaBeneficiarioBeneficiadoSubsidio(tipoIdentificacion, numeroIdentificacion) 
							|| personaJefeHogarBeneficiadoSubsidio(tipoIdentificacion, numeroIdentificacion)) {
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIADA_SUBSIDIO_FOVIS),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIADA_CON_SUBSIDIO_60_FOVIS,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIADA_CON_SUBSIDIO_60_FOVIS);

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
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIADA_CON_SUBSIDIO_60_FOVIS,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}
	
	/**
	 * Valida si una persona jefe hogar ha sido beneficiado con subsidio Fovis
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	private boolean personaJefeHogarBeneficiadoSubsidio(String tipoIdentificacion, 
			String numeroIdentificacion){
		
		logger.debug("Inicio de método ValidadorPersonaBeneficiadaSubsidioFOVIS.personaJefeHogarRegistraMalUso");
		try {
			 List<Integer> beneficiosAsociados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_JEFE_HOGAR_BENEFICIADO_SUBSIDIO_FOVIS)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.ESTADO_HOGAR_PARAM, EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name())
			.getResultList();
			 
			 if (beneficiosAsociados == null || beneficiosAsociados.isEmpty()) {
				return false;
			}
			 
			logger.debug("Fin de método ValidadorPersonaBeneficiadaSubsidioFOVIS.personaJefeHogarRegistraMalUso");
			return true;
		} catch (NoResultException nre) {
			return false;
		}
	}
	
	/**
	 * Valida si una persona beneficiario ha sido beneficiada con subsidio Fovis
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	private boolean personaBeneficiarioBeneficiadoSubsidio(String tipoIdentificacion, 
			String numeroIdentificacion){
		
		logger.debug("Inicio de método ValidadorPersonaBeneficiadaSubsidioFOVIS.personaBeneficiarioRegistraMalUso");
		try {
			List<Integer> beneficiosAsociados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_BENEFICIADO_SUBSIDIO_FOVIS)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.ESTADO_HOGAR_PARAM, EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name())
			.getResultList();
			
			if (beneficiosAsociados == null || beneficiosAsociados.isEmpty()) {
				return false;
			}
			 
			logger.debug("Fin de método ValidadorPersonaBeneficiadaSubsidioFOVIS.personaBeneficiarioRegistraMalUso");
			return true;
		} catch (NoResultException nre) {
			return false;
		}
	}
}
