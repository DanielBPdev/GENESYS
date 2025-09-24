package com.asopagos.validaciones.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si el jefe de hogar registra en el formulario FOVIS un hijo adoptivo
 * y este se encuentra registrado en base de datos como su hijo adoptivo (v16)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorHijoAdoptivoNoRegistradoBd extends ValidadorAbstract{


	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorHijoAdoptivoNoRegistradoBd.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String tipoIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				String numeroIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);

				/*
				 * Evalua si los datos de tipoIdentificacion y
				 * numeroIdentificacion no estan nulos o vacios
				 */
				if ((tipoIdentificacionBeneficiario != null && !tipoIdentificacionBeneficiario.equals(""))
						&& (numeroIdentificacionBeneficiario != null && !numeroIdentificacionBeneficiario.equals(""))
						&& (numeroIdentificacionJefeHogar != null && !numeroIdentificacionJefeHogar.equals(""))
						&& (tipoIdentificacionJefeHogar != null && !tipoIdentificacionJefeHogar.equals(""))) {
					
					if (esBeneficiario(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario,
							tipoIdentificacionJefeHogar, numeroIdentificacionJefeHogar)) {
						if (!tieneHijoAdoptivoRegistrado(tipoIdentificacionBeneficiario,
								numeroIdentificacionBeneficiario, tipoIdentificacionJefeHogar,
								numeroIdentificacionJefeHogar)) {
							return crearValidacion(
									myResources.getString(
											ConstantesValidaciones.KEY_PERSONA_HIJO_ADOPTIVO_SIN_REGISTRAR_BD),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_HIJO_ADOPTIVO_NO_REGISTRADO_EN_BD,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HIJO_ADOPTIVO_NO_REGISTRADO_EN_BD);

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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_ADOPTIVO_SIN_REGISTRAR_BD),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_HIJO_ADOPTIVO_NO_REGISTRADO_EN_BD,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
	/**
	 * Metodo que evalua si el solictante señala un hijo adoptivo en el formulario FOVIS 
	 * y este se encuentra registrado en la base de datos como su hijo adoptivo
	 * 
	 * @param tipoIdentificacionBeneficiario : Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario : Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar : Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar : Numero de identificacion del jefe de hogar
	 * @return true si el hijo adoptivo no se encuentra registrado en la base de datos como hijo adoptivo
	 */
	private boolean tieneHijoAdoptivoRegistrado(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario, 
			String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar) {
		try {
			logger.debug("Inicio de método ValidadorHijoAdoptivoNoRegistradoBd.tieneHijoAdoptivoRegistrado");
			/* se verifica si está al día con el tipo y número de documento */
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_PERSONA_NO_REGISTRA_HIJO_ADOPTIVO_BD_SI_FOVIS)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
			.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
			.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
			.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ClasificacionEnum.HIJO_ADOPTIVO.name())
			.getSingleResult();
			logger.debug("Fin de método ValidadorHijoAdoptivoNoRegistradoBd.tieneHijoAdoptivoRegistrado");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	/**
	 * Metodo que verifica si el miembro de hogar referido en la postulacion se
	 * encuentra registado en base de datos
	 * 
	 * @param tipoIdentificacionBeneficiario: Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario: Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar: Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar: Numero de identificacion del jefe de hogar
	 * @return true si el miembro no se encuentra registrado como beneficiario del grupo familiar del jefe de hogar
	 */
	private boolean esBeneficiario(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario,
			String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar) {
		try {
			logger.debug("Inicio de método ValidadorHijoAdoptivoNoRegistradoBd.esBeneficiario");
			
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_BENEFICIARIO_DE_JEFE_HOGAR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.getSingleResult();
			logger.debug("Fin de método ValidadorHijoAdoptivoNoRegistradoBd.esBeneficiario");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
