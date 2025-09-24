package com.asopagos.validaciones.validadores.fovis.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica el registro en base de datos de alguna persona por distintos
 * parametros
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPersonaNoTipoNumeroDocumento extends ValidadorAbstract{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaNoTipoNumeroDocumento.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				String primerNombre = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_PARAM);
				String primerApellido = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM);
				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))
						|| (primerNombre != null && primerApellido != null)) {
					if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
							&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
						if (!existePersonaPorTipoNumeroDocumento(tipoIdentificacion, numeroIdentificacion)) {
							/*
							 * si no se encuentra se valida existencia con el
							 * número de documento
							 */
							if (existePersonaPorNumeroDocumento(numeroIdentificacion)) {
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_PERSONA_EXISTE_OTROS_PARAMETROS),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO,
										TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
							}
							if (fechaNacimiento != null && primerNombre != null && primerApellido != null) {
								Date fechaNacimientoDate = new Date(new Long(fechaNacimiento));
								if (existePersonaPorNombreApellidoYFechaNacimiento(primerNombre, primerApellido,
										fechaNacimientoDate)) {
									logger.debug("No aprobado - Persona previamente registrada con otro tipo / número de documento de identidad");
									return crearValidacion(
											myResources.getString(ConstantesValidaciones.KEY_PERSONA_EXISTE_OTROS_PARAMETROS),
											ResultadoValidacionEnum.NO_APROBADA,
											ValidacionCoreEnum.VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO, 
											TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
								}
							} else {
								logger.debug("No evaluado- No llegaron todos los parametros");
								return crearMensajeNoEvaluado();
							}
						}
					}
				} else {
					logger.debug("No evaluado- No llegaron todos los parametros");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("No evaluado- No llegó el mapa con valores");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO);

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado();
		}
	}

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_EXISTE_OTROS_PARAMETROS),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Método encargado de validar si existe una persona por tipo y número de
	 * identificación.
	 * 
	 * @param tipoIdentificacion
	 *            tipo de identificación de la persona.
	 * @param numeroIdentificacion
	 *            número de identificación de la persona.
	 * @return true si existe o false si no existe.
	 */
	private boolean existePersonaPorTipoNumeroDocumento(String tipoIdentificacion, String numeroIdentificacion) {
		try {
			logger.debug("Inicio de método ValidadorPersonaNoTipoNumeroDocumento.existePersonaPorTipoNumeroDocumento");
			/* se verifica existencia con el tipo y número de documento */
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_EXISTENCIA_PERSONA_POR_TIPO_NUMERO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,
							TipoIdentificacionEnum.valueOf(tipoIdentificacion))
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
			logger.debug("Fin de método ValidadorPersonaNoTipoNumeroDocumento.existePersonaPorTipoNumeroDocumento");
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * Método encargado de validar si existe una persona por el número de
	 * identificación.
	 * 
	 * @param numeroIdentificacion
	 *            número de identificación de la persona.
	 * @return true si existe o false si no existe.
	 */
	private boolean existePersonaPorNumeroDocumento(String numeroIdentificacion) {
		logger.debug("Inicio de método ValidadorPersonaNoTipoNumeroDocumento.existePersonaPorNumeroDocumento");
		/* se verifica existencia con el tipo y número de documento */
		List<Persona> personas = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_NUMERO)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
		logger.debug("Fin de método ValidadorPersonaNoTipoNumeroDocumento.existePersonaPorNumeroDocumento");
		if (personas == null || personas.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Método encargado de validar si existe una persona por el primer nombre y
	 * apellido y fehca de nacimiento.
	 * 
	 * @param numeroIdentificacion
	 *            número de identificación de la persona.
	 * @return true si existe o false si no existe.
	 */
	private boolean existePersonaPorNombreApellidoYFechaNacimiento(String primerNombre, String primerApellido,
			Date fechaNacimiento) {
		logger.debug("Inicio de método ValidadorPersonaNoTipoNumeroDocumento.existePersonaPorNombreApellidoYFechaNacimiento");
		/*
		 * si no se encuenta se valida existencia con nombre, apellido y fecha
		 * de nacimiento
		 */
		List<Persona> personasPorNombre = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_NOMBRE_APELLIDO_FECHA_NACIMIENTO)
				.setParameter(ConstantesValidaciones.PRIMER_APELLIDO_PARAM, primerApellido)
				.setParameter(ConstantesValidaciones.PRIMER_NOMBRE_PARAM, primerNombre)
				.setParameter(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM, fechaNacimiento).getResultList();
		logger.debug("Fin de método ValidadorExistenciaPersona.existePersonaPorNombreApellidoYFechaNacimiento");
		if (personasPorNombre == null || personasPorNombre.isEmpty()) {
			return false;
		}
		return true;
	}
}
