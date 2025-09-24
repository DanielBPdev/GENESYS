package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorAfiliadoPrincipalHermanoHuerfano extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorAfiliadoPrincipalHermanoHuerfano");
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdA = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				TipoIdentificacionEnum tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoIdA);
				String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				if (tipoIdentificacionAfiliado != null && !tipoIdentificacionAfiliado.equals("")
						&& numeroIdentificacionAfiliado != null && !numeroIdentificacionAfiliado.equals("")) {
					boolean existe = false;

					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADOS)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerListHijoHuerfano())
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerEstadosAfiliado())
							.getResultList();
					if (personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) {
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADOS)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM,ValidacionPersonaUtils.obtenerListHijoHuerfano())
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerEstadosAfiliado())
								.getResultList();
						if (personasConNumero != null && !personasConNumero.isEmpty()) {
							existe = true;
						}
					}else{
						existe = true;
					}
					if (existe) {
						/**
						 * El afiliado principal ya tiene registrado un hermano
						 * huérfano (activo o inactivo)
						 */
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_AFILIADO_PRINCIPAL_HERMANO_HUERFANO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_AFILIADO_CON_HERMANO_HUERFANO,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
					}
				} else {
					/* mensaje no evaluado porque no hay parametros */
					return crearMensajeNoEvaluado();
				}

			} else {
				/* mensaje no evaluado porque no llegaron datos */
				return crearMensajeNoEvaluado();
			}
			/* exitoso */
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_CON_HERMANO_HUERFANO);
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
						+ myResources.getString(ConstantesValidaciones.KEY_AFILIADO_PRINCIPAL_HERMANO_HUERFANO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_AFILIADO_CON_HERMANO_HUERFANO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
}
