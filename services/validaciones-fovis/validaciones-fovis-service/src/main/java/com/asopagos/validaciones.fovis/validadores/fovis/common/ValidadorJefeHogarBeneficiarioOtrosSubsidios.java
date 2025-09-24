package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
/**
 * Validadador que verifica si el jefe de hogar ha sido beneficiario de algun tipo de subsidio de vivienda
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorJefeHogarBeneficiarioOtrosSubsidios extends ValidadorFovisAbstract {

	
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {

		logger.debug("Inicio de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {

					if (!haRegistradoMejoraConformacionNuevoHogar(tipoIdentificacion, numeroIdentificacion))/*PENDIENTE*/ {

//						
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDAR_JEFE_HOGAR_REGISTRA_MEJORA_NUEVO_HOGAR);

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
						+ myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_BENEFICIARIO_OTROS_SUBSIDIOS),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDAR_JEFE_HOGAR_REGISTRA_MEJORA_NUEVO_HOGAR,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}

	/**
	 * Método encargado de validar, <b>por tipo y número de identificación</b>, si una perosna ha sido beneficiaria de un subsidio
	 * y este le fue asignado y no ha renunciado, no ha reembolsado dicho subsidio o aparece asociado a un subsidio restituído.
	 * 
	 * @param tipoIdentificacion tipo de identificación del jefe de hogar.
	 * @param numeroIdentificacion número de identificación del jefe de hogar.
	 * @return true si ha sido beneficiario de otros subsidios
	 */
	private boolean haSidoBeneficiarioOtroSubsidioSinReembolso(String tipoIdentificacion, String numeroIdentificacion) {
//		try {
//			logger.debug("Inicio de método ValidadorJefeHogarDependienteAlDiaAportes.estaAlDiaPorTipoNumeroDocumento");
//			
//			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_BENEFICIARIO_OTRO_SUBSIDIO_SIN_REEMBOLOSO)
//					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacion)
//					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
//			logger.debug("Fin de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.haSidoBeneficiarioOtroSubsidioSinReembolso");
			return true;
//		} catch (NoResultException e) {
//			return false;
//		}
	}
	
	/**
	 * Metodo que evalua si el solicitante ha sido beneficiario de la modalidad Mejora de Vivienda Saluble
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return true si ha sido beneficiario de Mejora de Vivienda Saludable
	 */
	private boolean haSidoBeneficiarioMejoraViviendaSualudable(String tipoIdentificacion, String numeroIdentificacion) {
//		try {
//			logger.debug("Inicio de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.haSidoBeneficiarioMejoraViviendaSualudable");
//			
//			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_BENEFICIARIO_MEJORA_VIIVENDA_SALUDABLE)
//					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacion)
//					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
//			logger.debug("Fin de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.haSidoBeneficiarioMejoraViviendaSualudableo");
			return true;
//		} catch (NoResultException e) {
//			return false;
//		}
	}
	/**
	 * Metodo que evalua si el solictante ha registrado una novedad de Mejora de Conformacion de nuevo hogar
	 * 
	 * @param tipoIdentificacion tipo de identificación del jefe de hogar.
	 * @param numeroIdentificacion número de identificación del jefe de hogar.
	 * @return true si el solicitante ha registrado la novedad de Mejora de Conformacion de nuevo hogar
	 */
	private boolean haRegistradoMejoraConformacionNuevoHogar(String tipoIdentificacion, String numeroIdentificacion) {
//		try {
//			logger.debug("Inicio de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.haRegistradoMejoraConformacionNuevoHogar");
//			/* se verifica si está al día con el tipo y número de documento */
//			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_REGISTRA_MEJORA_NUEVO_HOGAR)
//					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacion)
//					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
//			logger.debug("Fin de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.haRegistradoMejoraConformacionNuevoHogar");
			return true;
		//} catch (NoResultException e) {
//			return false;
//		}
	}
	
}
