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
 * Validadador que verifica si el parentesco de una persona es el mismo registrado en la base de datos
 * con el del registrado en el formulario FOVIS respecto al jefe de hogar (v22)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorParentescoConJefeHogar extends ValidadorFovisAbstract {
	
	/**
	 * Constante que establece la cantidad de caracteres a eliminar del dato tipoBeneficiario proveniente
	 * de pantallas, a fin de que el dato corresponda a alguno de los contenidos en la base de datos
	 */
	private static final int CARACTERES_A_ELIMINAR = 6;

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorParentescoConJefeHogar.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String tipoIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				String numeroIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				String tipoBeneficiario = datosValidacion.get(ConstantesValidaciones.PARENTESCO_BENEF_PARAM);
				/*
				 * Evalua si los datos de tipoIdentificacion y
				 * numeroIdentificacion no estan nulos o vacios
				 */
				if ((tipoIdentificacionBeneficiario != null && !tipoIdentificacionBeneficiario.equals(""))
						&& (numeroIdentificacionBeneficiario != null && !numeroIdentificacionBeneficiario.equals(""))
						&& (numeroIdentificacionJefeHogar != null && !numeroIdentificacionJefeHogar.equals(""))
						&& (tipoIdentificacionJefeHogar != null && !tipoIdentificacionJefeHogar.equals(""))
						&& (tipoBeneficiario != null && !tipoBeneficiario.isEmpty())) {
					if (esBeneficiario(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario,
							tipoIdentificacionJefeHogar, numeroIdentificacionJefeHogar)) {
						if (!parentescoCoincide(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario, tipoIdentificacionJefeHogar, 
								numeroIdentificacionJefeHogar, tipoBeneficiario)) {
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.KEY_PARENTESCO_NO_COINCIDE),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PARENTESCO_CON_JEFE_HOGAR,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PARENTESCO_CON_JEFE_HOGAR);

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
						+ myResources.getString(ConstantesValidaciones.KEY_PARENTESCO_NO_COINCIDE),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_PARENTESCO_CON_JEFE_HOGAR,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
	/**
	 * Metodo que evalua si el parentesco de una persona, relacionado en el formulario FOVIS y el registrado en la base de datos
	 * con respecto al jefe de hogar, es el mismo
	 * 
	 * @param tipoIdentificacionBeneficiario : Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario : Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar : Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar : Numero de identificacion del jefe de hogar
	 * @return true si el parentesco es el mismo
	 */
	private boolean parentescoCoincide(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario, 
			String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar, String tipoBeneficiario) {
		try {
			logger.debug("Inicio de método ValidadorParentescoConJefeHogar.parentescoCoincide");
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_COINCIDENCIA_PARENTESCO_CON_JEFE_HOGAR)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
			.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
			.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
			.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiario.equals(ClasificacionEnum.HERMANO_HOGAR.name()) 
					? ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES.name() : tipoBeneficiario.substring(0, tipoBeneficiario.length()- CARACTERES_A_ELIMINAR))
			.getSingleResult();
			logger.debug("Fin de método ValidadorParentescoConJefeHogar.parentescoCoincide");
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
			logger.debug("Inicio de método ValidadorParentescoConJefeHogar.esBeneficiario");
			
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_BENEFICIARIO_DE_JEFE_HOGAR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.getSingleResult();
			logger.debug("Fin de método ValidadorHijoBiologicoNoRegistradoBd.esBeneficiario");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

}
