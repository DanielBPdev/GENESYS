package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
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
public class ValidadorFechaInicioLaboresMayorFechaAfiliacion extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
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
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")) {
					List<RolAfiliado> rolAfiliadoTipoNumero = entityManager
							.createNamedQuery(
									NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getResultList();

					if (rolAfiliadoTipoNumero.size() > 0) {
						rolAfiliado = rolAfiliadoTipoNumero.get(0);
						existe = true;
					} else {
						List<RolAfiliado> rolAfiliadoNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_NUMERO_IDENTIFICACION)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.getResultList();
						if (rolAfiliadoNumero.size() > 0) {
							rolAfiliado = rolAfiliadoNumero.get(0);
							existe = true;
						}
					}
					if (existe) {
						Date fechaActual = new Date();
						// Verificacion de la fecha de ingreso es mayor a la
						// fecha actual
						if (rolAfiliado.getFechaIngreso().getTime() > fechaActual.getTime()) {
							return crearValidacion(
									myResources.getString(
											ConstantesValidaciones.KEY_INICIO_LABORES_POSTERIOR_FECHA_AFILIACION),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_FECHA_LABORES_MAYOR_FECHA_AFILIACION,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						}
					}
				} else {
					/* mensaje no evaluado porque no hay parametros */
					logger.debug("No evaludao- Falta informacion");
					return crearMensajeNoEvaluado();
				}
			} else {
				/* mensaje no evaluado porque no llegaron datos */
				logger.debug("No evaludao- Faltan datos");
				return crearMensajeNoEvaluado();
			}
			/* exitoso */
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_LABORES_MAYOR_FECHA_AFILIACION);
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
						+ myResources.getString(ConstantesValidaciones.KEY_INICIO_LABORES_POSTERIOR_FECHA_AFILIACION),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_FECHA_LABORES_MAYOR_FECHA_AFILIACION,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}