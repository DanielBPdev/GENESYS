package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si el jefe de hogar registra en el formulario FOVIS un hijastro
 * y este se encuentra registrado en base de datos como su hijastro (v16)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorHijastroNoRegistradoBd extends ValidadorFovisAbstract {
	
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorHijastroNoRegistradoBaseDeDatos.execute");
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
						if (!tieneHijastroRegistrado(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario,
								tipoIdentificacionJefeHogar, numeroIdentificacionJefeHogar)) {
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJASTRO_SIN_REGISTRAR_BD),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_HIJASTRO_NO_REGISTRADO_EN_BD,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HIJASTRO_NO_REGISTRADO_EN_BD);

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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJASTRO_SIN_REGISTRAR_BD),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_HIJASTRO_NO_REGISTRADO_EN_BD,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
	/**
	 * Metodo que evalua si el solictante señala un hijastro en el formulario FOVIS 
	 * y este se encuentra registrado en la base de datos tambien como su hijastro
	 * 
	 * @param tipoIdentificacionBeneficiario : Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario : Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar : Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar : Numero de identificacion del jefe de hogar
	 * @return true true si el hijastro no se encuentra registrado en la base de datos como hijastro
	 */
	private boolean tieneHijastroRegistrado(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario, 
			String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar) {
		try {
			logger.debug("Inicio de método ValidadorHijastroNoRegistradoBd.tieneHijastroRegistrado");
			/* se verifica si está al día con el tipo y número de documento */
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_PERSONA_NO_REGISTRA_HIJASTRO_BD_SI_FOVIS)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
			.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
			.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
			.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ClasificacionEnum.HIJASTRO.name())
			.getSingleResult();
			logger.debug("Fin de método ValidadorHijastroNoRegistradoBd.tieneHijastroRegistrado");
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
			logger.debug("Inicio de método ValidadorHijastroNoRegistradoBd.esBeneficiario");
			
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_BENEFICIARIO_DE_JEFE_HOGAR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.getSingleResult();
			logger.debug("Fin de método ValidadorHijastroNoRegistradoBd.esBeneficiario");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
