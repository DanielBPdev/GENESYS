package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la logica para verificar cuando una persona no es mayor de
 * 15 años
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ValidadorPersonaNoMayor15 extends ValidadorAbstract {

	/**
	 * Constante que estable la edad a verificar
	 */
	private static final int EDAD_ESTABLECIDA = 15;

	/**
	 * Metodo encargado de verificiar la edad de la persona
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorPersonaNoMayor15");
			boolean existe = false;
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
			    String tipoIdentificacion = null;
                String numeroIdentificacion = null;
                // Se verifica si la validacion aplica por una novedad de beneficiario
                // Se obtienen los datos de identificacion segun a quien aplica la novedad
                if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!= null && 
                        datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!= null){
                    tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
                    numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
                } else {
                    tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                    numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                }
                TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
				String fechaNacimientoLong = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				PersonaDetalle personaDetalle = null;
				/* Se verifica si la fecha de nacimiento es null */
				if (fechaNacimientoLong == null || fechaNacimientoLong.equals("")) {
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_15);
				} else {
					String fechaNacimiento = ValidacionPersonaUtils.convertirFecha(fechaNacimientoLong);
					if (tipoIdentificacionEnum != null && numeroIdentificacion != null
							&& !numeroIdentificacion.equals("")) {
						Persona persona = null;
						List<Persona> personaTipoIdentificacion = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionEnum)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.getResultList();
						try {
							personaDetalle = entityManager
									.createNamedQuery(
											NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
											PersonaDetalle.class)
									.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionEnum)
									.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
									.getSingleResult();
						} catch (NoResultException e) {
							personaDetalle = null;
						}
						if (personaTipoIdentificacion.size() > 0) {
							persona = personaTipoIdentificacion.get(0);
							existe = true;
						} else {
							List<Persona> personaIdentificacion = entityManager
									.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO)
									.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionEnum)
									.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
									.getResultList();
							try {
								personaDetalle = entityManager
										.createNamedQuery(
												NamedQueriesConstants.BUSCAR_PERSONADETALLE_ID_NUMERO_IDENTIFICACION,
												PersonaDetalle.class)
										.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
										.getSingleResult();
							} catch (NoResultException e) {
								personaDetalle = null;
							}
							if (personaIdentificacion.size() > 0 && personaDetalle != null) {
								persona = personaIdentificacion.get(0);
								existe = true;
							}
						}

						if (existe 
						        && personaDetalle != null
						        && personaDetalle.getFechaNacimiento() != null) {
							/*
							 * si existe la persona se toma la fecha de
							 * nacimiento guardada
							 */
							fechaNacimiento = ValidacionPersonaUtils
									.convertirFecha(personaDetalle.getFechaNacimiento().getTime() + "");

						}
						int edadPersona = ValidacionPersonaUtils.calcularEdadAnos(fechaNacimiento);
						if (edadPersona < EDAD_ESTABLECIDA) {
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_MAYOR_15),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_15,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
						}
					} else {
						/* mensaje no evaluado porque falta informacion */
						logger.debug("No evaludao- Falta informacion");
						return crearMensajeNoEvaluado();
					}
				}
			} else {
				/* mensaje no evaluado porque faltan datos */
				logger.debug("No evaludao- Faltan datos");
				return crearMensajeNoEvaluado();
			}
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_15);
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
			logger.debug("No evaludao- Ocurrio alguna excepcion");
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_MAYOR_15),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_15,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
}
