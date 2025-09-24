package com.asopagos.validaciones.validadores.afiliacion.persona;

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
 * Clase que contiene la lógica para validar la existencia de una persona.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorExistenciaPersona extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorExistenciaPersona.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				String primerNombre = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_PARAM);
				String primerApellido = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM);
				logger.info("Emilio ---- Se esta evaluando la existencia de la persona ");
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
							if (!existePersonaPorNumeroDocumento(numeroIdentificacion))
								if (fechaNacimiento != null && primerNombre != null && primerApellido != null) {
									Date fechaNacimientoDate = new Date(new Long(fechaNacimiento));
									if (!existePersonaPorNombreApellidoYFechaNacimiento(primerNombre, primerApellido,
											fechaNacimientoDate)) {
												logger.info("No aprobada- No existe persona con número y tipo documento que se busca");
										logger.debug("No aprobada- No existe persona con número y tipo documento");
										return crearValidacion(
												myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_EXISTE),
												ResultadoValidacionEnum.NO_APROBADA,
												ValidacionCoreEnum.VALIDACION_EXISTENCIA_PERSONA, null);
									}
								} else {
									logger.debug("No evaluado- No llegaron todos los parametros");
									logger.info("No evaluado- No llegaron todos los parametros para la consulta");
									return crearMensajeNoEvaluado();
								}
						}
					}
				} else {
					logger.debug("No evaluado- No llegaron todos los parametros");
					logger.info("No evaluado- No llegaron todos los parametros para la consulta 2");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("No evaluado- No llegó el mapa con valores");
				logger.info("No evaluado- No llegó el mapa con valores mensaje de no evaluado");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			logger.info("Es aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EXISTENCIA_PERSONA);

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado();
		}
	}

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_EXISTE),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_EXISTENCIA_PERSONA,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
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
			logger.debug("Inicio de método ValidadorExistenciaPersona.existePersonaPorTipoNumeroDocumento");
			/* se verifica existencia con el tipo y número de documento */
			entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_EXISTENCIA_PERSONA_POR_TIPO_NUMERO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM,
							TipoIdentificacionEnum.valueOf(tipoIdentificacion))
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
			logger.debug("Fin de método ValidadorExistenciaPersona.existePersonaPorTipoNumeroDocumento");
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
		logger.debug("Inicio de método ValidadorExistenciaPersona.existePersonaPorNumeroDocumento");
		/* se verifica existencia con el tipo y número de documento */
		List<Persona> personas = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_NUMERO)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
		logger.debug("Fin de método ValidadorExistenciaPersona.existePersonaPorNumeroDocumento");
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
		logger.debug("Inicio de método ValidadorExistenciaPersona.existePersonaPorNombreApellidoYFechaNacimiento");
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
