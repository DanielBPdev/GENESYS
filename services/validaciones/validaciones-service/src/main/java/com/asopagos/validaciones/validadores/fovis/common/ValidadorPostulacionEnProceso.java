package com.asopagos.validaciones.validadores.fovis.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si una persona tiene alguna postulación en proceso.
 *
 * @author <a href="mailto:flopez@heinsohn.com.co">Fabian López</a>
 */
public class ValidadorPostulacionEnProceso extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaActivaOtroSubsidio.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String idSolicitudPostulacion = datosValidacion.get(ConstantesValidaciones.ID_SOLICITUDPOSTULACION);
				/*
				 * Evalua si los datos de tipoIdentificacion y
				 * numeroIdentificacion no estan nulos o vacios
				 */
				if (numeroIdentificacion != null && !numeroIdentificacion.isEmpty() && tipoIdentificacion != null
						&& !tipoIdentificacion.isEmpty()) {
					if (postulacionEnProceso(tipoIdentificacion, numeroIdentificacion, idSolicitudPostulacion)) {
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_SOLICITUD_EN_PROCESO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_SOLICITUD_EN_PROCESO,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_SOLICITUD_EN_PROCESO);

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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_SOLICITUD_EN_PROCESO),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_PERSONA_SOLICITUD_EN_PROCESO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Metodo que evalua si un Jefe de Hogar está activo en otra Postulación.
	 *
	 * @param tipoIdentificacion:
	 *            Tipo de identidiacion del jefe de hogar
	 * @param numeroIdentificacion:
	 *            Numero de identificacion del jefe de hogar
	 * @return true si tiene postulaciones activas
	 */
	@SuppressWarnings("unchecked")
	private boolean postulacionEnProceso(String tipoIdentificacion, String numeroIdentificacion,
										 String idSolicitudPostulacion) {
		Boolean postulacionEnProceso = Boolean.FALSE;

		logger.debug("Inicio de método ValidadorPostulacionEnProceso.postulacionEnProceso");

		List<String> estadoHogarEnums = new ArrayList<>();
		estadoHogarEnums.add(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA.name());
		estadoHogarEnums.add(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA.name());
		estadoHogarEnums.add(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA.name());
		estadoHogarEnums.add(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name());
		estadoHogarEnums.add(EstadoHogarEnum.RECHAZADO_POR_SUSPENSION_SIN_RENOVACION.name());
		estadoHogarEnums.add(EstadoHogarEnum.RECHAZADO.name());
		estadoHogarEnums.add(EstadoHogarEnum.RESTITUIDO_CON_SANCION.name());
		estadoHogarEnums.add(EstadoHogarEnum.HOGAR_DESISTIO_POSTULACION.name());
		estadoHogarEnums.add(EstadoHogarEnum.RENUNCIO_A_SUBSIDIO_ASIGNADO.name());

		// Se valida si existe como JefeHogar miembro de otra solicitud de postulación
		List<BigInteger> datosSolicitud = entityManager
				.createNamedQuery(NamedQueriesConstants.VALIDAR_SOLICITUD_POSTULACION_EN_PROCESO_JEFEHOGAR)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
				.setParameter(ConstantesValidaciones.ESTADO_HOGAR_POS_PARAM, estadoHogarEnums)
				.getResultList();
		if (datosSolicitud != null && !datosSolicitud.isEmpty()) {
			postulacionEnProceso = Boolean.TRUE;
		}
		/* Se consulta hacia Integrante de Hogar. */
		if (!postulacionEnProceso) {
			// Se valida si existe como IntegranteHogar miembro de otra
			// solicitud de postulación en proceso
			datosSolicitud = entityManager
					.createNamedQuery(NamedQueriesConstants.VALIDAR_SOLICITUD_POSTULACION_EN_PROCESO_INTEGRANTEHOGAR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.ESTADO_SOLICITUD_POSTULACION, EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA.name())
					.getResultList();
			if (datosSolicitud != null && !datosSolicitud.isEmpty()) {
				postulacionEnProceso = Boolean.TRUE;
			}

		}

		/* Si es otra postulación asociada*/
		if (postulacionEnProceso && idSolicitudPostulacion != null
				&& !idSolicitudPostulacion.isEmpty()) {
			postulacionEnProceso = Boolean.FALSE;
			for (BigInteger idSolicitudProceso : datosSolicitud) {
				/*Si es diferente a la solicitud en proceso.*/
				if (idSolicitudProceso != null &&
						idSolicitudProceso.longValue() != Long.parseLong(idSolicitudPostulacion)) {
					postulacionEnProceso = Boolean.TRUE;
				}

			}
		}else if (idSolicitudPostulacion == null && postulacionEnProceso){
			postulacionEnProceso = Boolean.TRUE;
		}else{
			postulacionEnProceso = Boolean.FALSE;
		}

		logger.debug("Fin de método ValidadorPostulacionEnProceso.postulacionEnProceso");
		return postulacionEnProceso;
	}
}