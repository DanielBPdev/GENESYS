package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Date;
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

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaEdadMayorAfiliado extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorPersonaEdadMayorAfiliado");
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				
				TipoIdentificacionEnum tipoIdentificacion = null;
				String numeroIdentificacion = null;
				
				//Condicion para cuando la validacion viene desde ingreso beneficiario PILA -Aportes
				if(tipoId == null && datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM) != null
						&& datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM) != null
						&& datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null
						&& datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null) {
					tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
					tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
					numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				}else {
					tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
					numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				}
				
				String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				PersonaDetalle personaDetalle = null;
				/* Se verifica si la fecha de nacimiento es null */
				if (fechaNacimiento == null || fechaNacimiento.equals("")) {
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_AFILIADO);
				} else {
					if (tipoIdentificacion != null && !tipoIdentificacion.equals("") && numeroIdentificacion != null
							&& !numeroIdentificacion.equals("")) {
						Persona personaEncontrado = null;
						List<Persona> personaTipoIdentificacion = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.getResultList();
						try {
							personaDetalle = entityManager
									.createNamedQuery(
											NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION,
											PersonaDetalle.class)
									.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
									.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
									.getSingleResult();
						} catch (NoResultException e) {
							personaDetalle = null;
						}
						if (!personaTipoIdentificacion.isEmpty()) {
							personaEncontrado = personaTipoIdentificacion.get(0);
						} else {
							List<Persona> personaIdentificacion = entityManager
									.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_NUMERO)
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
							if (!personaIdentificacion.isEmpty()) {
								personaEncontrado = personaTipoIdentificacion.get(0);
							}
						}
						if (personaEncontrado != null && personaDetalle != null) {
							Date fNacimiento = new Date(new Long(fechaNacimiento));
							/*
							 * Verificacion de la fecha nacimiento del afiliado
							 * es mayor a la del afiliado principal
							 */
							if (fNacimiento.getTime() < personaDetalle.getFechaNacimiento().getTime()) {
								return crearValidacion(
										myResources
												.getString(ConstantesValidaciones.KEY_PERSONA_MAYOR_AFILIADO_PRINCIPAL),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_AFILIADO,
										TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
							}
						} else {
							/* Afiliado principal no encontrado */
							return crearMensajeNoEvaluado();
						}

					} else {
						/* mensaje no evaluado porque no hay parametros */
						return crearMensajeNoEvaluado();
					}
				}
			} else {
				/* mensaje no evaluado porque no llegaron datos */
				return crearMensajeNoEvaluado();
			}
			/* exitoso */
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_AFILIADO);
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_MAYOR_AFILIADO_PRINCIPAL),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_AFILIADO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
