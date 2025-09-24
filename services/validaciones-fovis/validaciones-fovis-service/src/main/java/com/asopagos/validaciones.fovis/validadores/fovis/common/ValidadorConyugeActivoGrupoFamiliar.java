package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si el conyuge asociado a un grupo familiar se
 * encuentra en estado Activo dentro del grupo (v8)
 * 
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorConyugeActivoGrupoFamiliar extends ValidadorFovisAbstract {
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {

		logger.debug("Inicio de método ValidadorConyugeActivoGrupoFamiliar.execute");
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

						if (!esConyugeActivoEnGrupoFamiliar(tipoIdentificacionBeneficiario,
								numeroIdentificacionBeneficiario, tipoIdentificacionJefeHogar,
								numeroIdentificacionJefeHogar)) {
							return crearValidacion(
									myResources.getString(
											ConstantesValidaciones.KEY_CONYUGE_ACTIVO_GRUPO_FAMILIAR_JEFE_HOGAR),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO_EN_GRUPO_FAMILIAR,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						}
					} else {
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_CONYUGE_ACTIVO_GRUPO_FAMILIAR_JEFE_HOGAR),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO_EN_GRUPO_FAMILIAR,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}
					
				} else {
					/* mensaje no evaluado porque falta informacion */
					logger.debug("No evaludao- Falta informacion");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("No evaluado - No llegó el mapa con valores");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO_EN_GRUPO_FAMILIAR);
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
						+ myResources.getString(ConstantesValidaciones.KEY_CONYUGE_ACTIVO_GRUPO_FAMILIAR_JEFE_HOGAR),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO_EN_GRUPO_FAMILIAR,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Metodo qu evalua si el conyuge asociado a la afiliacion se encuentra en
	 * estado activo en el grupo familiar del conyuge solicitante
	 * 
	 * @param tipoIdentificacionBeneficiario : Tipo de identificacion del beneficiario
	 * @param numeroIdentificacionBeneficiario : Numero de identificacion del beneficiario
	 * @param tipoIdentificacionJefeHogar : Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacionJefeHogar : Numero de identificacion del jefe de hogar
	 * @return true si conyuge tiene estado activo en el grupo familiar
	 */
	private boolean esConyugeActivoEnGrupoFamiliar(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario, 
			String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar){
		try {
			logger.debug("Inicio de método ValidadorConyugeActivoGrupoFamiliar.esConyugeActivoEnGrupoFamiliar");
			
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_CONYUGE_ACTIVO_GRUPO_FAMILIAR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, TipoBeneficiarioEnum.CONYUGE.name())
					.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name())
					.getSingleResult();
			logger.debug("Fin de método ValidadorConyugeActivoGrupoFamiliar.esConyugeActivoEnGrupoFamiliar");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	/**
	 * Metodo que verifica si el miembro de hogar referido en la postulacion se
	 * encuentra registado en base de datos como benficiario
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
			logger.debug("Inicio de método ValidadorConyugeActivoGrupoFamiliar.esBeneficiario");
			
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_BENEFICIARIO_DE_JEFE_HOGAR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM,tipoIdentificacionJefeHogar)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefeHogar)
					.getSingleResult();
			logger.debug("Fin de método ValidadorConyugeActivoGrupoFamiliar.esBeneficiario");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

}
