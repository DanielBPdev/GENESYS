package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la logica para verificar la Fecha de nacimiento mayor a la
 * del día de inicio de labores con empleador
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ValidadorFechaNacimientoMayorFechaInicioLabores extends ValidadorAbstract {

	/**
	 * Metodo encargado de validar la fecha de nacimiento mayor a la fecha de
	 * inicio de labores con el empleador
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorFechaNacimientoMayorFechaInicioLabores");
			boolean existe = false;
			RolAfiliado rolAfiliado = null;
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				PersonaDetalle personaDetalle = null;
				/* Se verifica si la fecha de nacimiento es null */
				if (fechaNacimiento == null || fechaNacimiento.equals("")) {
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_LABORES);
				} else {
					if (tipoIdentificacion != null && !tipoIdentificacion.equals("")) {
						List<RolAfiliado> rolAfiliadoTipoNumero = entityManager
								.createNamedQuery(
										NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getResultList();
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
						if (rolAfiliadoTipoNumero.size() > 0) {
							rolAfiliado = rolAfiliadoTipoNumero.get(0);
							existe = true;
						} else {
							List<RolAfiliado> rolAfiliadoNumero = entityManager
									.createNamedQuery(
											NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_NUMERO_IDENTIFICACION)
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
							if (rolAfiliadoNumero.size() > 0 && personaDetalle != null) {
								rolAfiliado = rolAfiliadoNumero.get(0);
								existe = true;
							}
						}
						if (existe) {
							fechaNacimiento = personaDetalle.getFechaNacimiento().getTime() + "";
						}
						Date fNacimiento = new Date(new Long(fechaNacimiento));
						if (fNacimiento.getTime() > rolAfiliado.getFechaIngreso().getTime()) {
							/*
							 * Fecha de nacimiento mayor a la del día de inicio
							 * de labores con empleador
							 */
							return crearValidacion(
									myResources.getString(
											ConstantesValidaciones.KEY_FECHA_NACIMIENTO_MAYOR_FECHA_INICIO_LABORES),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_LABORES,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						}
					} else {
						// mensaje no evaluado porque no hay parametros
						logger.debug("No evaludao- Falta informacion");
						return crearMensajeNoEvaluado();
					}
				}
			} else {
				// mensaje no evaluado porque no llegaron datos
				logger.debug("No evaludao- Faltan datos");
				return crearMensajeNoEvaluado();
			}
			/* exitoso */
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_LABORES);
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
						+ myResources.getString(ConstantesValidaciones.KEY_FECHA_NACIMIENTO_MAYOR_FECHA_INICIO_LABORES),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_LABORES,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
