package com.asopagos.validaciones.fovis.validadores.novedades.fovis;


import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Objetivo : Validar si se ha reportado mal uso de los servicios/subsidios de la caja de compensación 
 * por parte de la persona
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorUsoServiciosCCF extends ValidadorFovisAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {

		logger.debug("Inicio de método ValidadorUsoServiciosCCF.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
					if (personaBeneficiarioRegistraMalUso(tipoIdentificacion, numeroIdentificacion) 
							|| personaJefeHogarRegistraMalUso(tipoIdentificacion, numeroIdentificacion)) {
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_REGISTRA_MAL_USO_SERVICIOS),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_REPORTA_MAL_USO_SERVICIOS_43_FOVIS,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_REPORTA_MAL_USO_SERVICIOS_43_FOVIS);

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
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_REPORTA_MAL_USO_SERVICIOS_43_FOVIS,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}
	
	/**
	 * Valida si una persona jefe hogar tiene registros de mal uso de servicios/subsidios 
	 * de la caja de compensacion
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	private boolean personaJefeHogarRegistraMalUso(TipoIdentificacionEnum tipoIdentificacion, 
			String numeroIdentificacion){
		
		logger.debug("Inicio de método ValidadorUsoServiciosCCF.personaJefeHogarRegistraMalUso");
		try {
			 List<MotivoDesafiliacionAfiliadoEnum> motivosDesafiliacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOTIVOS_DESAFILIACION_JEFE_HOGAR, MotivoDesafiliacionAfiliadoEnum.class)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION, MotivoDesafiliacionAfiliadoEnum.MAL_USO_DE_SERVICIOS_CCF)
			.getResultList();
			 
			 if (motivosDesafiliacion == null || motivosDesafiliacion.isEmpty()) {
					return false;
				}
			 
			logger.debug("Fin de método ValidadorUsoServiciosCCF.personaJefeHogarRegistraMalUso");
			return true;
		} catch (NoResultException nre) {
			return false;
		}
	}
	
	/**
	 * Valida si una persona beneficiario tiene registros de mal uso de servicios/subsidios 
	 * de la caja de compensación
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	private boolean personaBeneficiarioRegistraMalUso(TipoIdentificacionEnum tipoIdentificacion, 
			String numeroIdentificacion){
		
		logger.debug("Inicio de método ValidadorUsoServiciosCCF.personaBeneficiarioRegistraMalUso");
		try {
			 List<MotivoDesafiliacionBeneficiarioEnum> motivosDesafiliacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOTIVOS_DESAFILIACION_BENEFICIARIO, MotivoDesafiliacionBeneficiarioEnum.class)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION, MotivoDesafiliacionBeneficiarioEnum.MAL_USO_DE_SERVICIOS_CCF)
			.getResultList();
			 
			 if (motivosDesafiliacion == null || motivosDesafiliacion.isEmpty()) {
				return false;
			}
			 
			logger.debug("Fin de método ValidadorUsoServiciosCCF.personaBeneficiarioRegistraMalUso");
			return true;
		} catch (NoResultException nre) {
			return false;
		}
	}
}
