package com.asopagos.validaciones.validadores.common;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
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
public class ValidadorPersonaBeneficiarioConyuge extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorPersonaBeneficiarioConyuge");
			boolean existe = false;
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")) {
					List<Beneficiario> beneficiarioTipoNumeroActivo = entityManager
							.createNamedQuery(
									NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ClasificacionEnum.CONYUGE)
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
							.getResultList();

					if (beneficiarioTipoNumeroActivo!=null && !beneficiarioTipoNumeroActivo.isEmpty()) {
						if (beneficiarioTipoNumeroActivo.iterator().next().getAfiliado().getPersona()
								.getNumeroIdentificacion()
								.equals(datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM))
								&& beneficiarioTipoNumeroActivo.iterator().next().getAfiliado().getPersona()
										.getTipoIdentificacion()
										.equals(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(
												datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM)))) {
							existe = false;
						} else {
							existe = true;
						}
					} else {
						List<Beneficiario> beneficiarioNumeroActivo = entityManager
								.createNamedQuery(
										NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM,
										ClasificacionEnum.CONYUGE)
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
								.getResultList();
						if (beneficiarioNumeroActivo != null && !beneficiarioNumeroActivo.isEmpty()) {
							if (beneficiarioTipoNumeroActivo.iterator().next().getAfiliado().getPersona()
									.getNumeroIdentificacion()
									.equals(datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM))
									&& beneficiarioTipoNumeroActivo.iterator().next().getAfiliado().getPersona()
											.getTipoIdentificacion()
											.equals(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(datosValidacion
													.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM)))) {
								existe = false;
							} else {
								existe = true;
							}
						}
					}
				} else {
					/* mensaje no evaluado porque no hay parametros */
					return crearMensajeNoEvaluado();
				}
				if (existe) {
					/*No aprobada- Existe Persona registrada como beneficiario tipo hijo y estado afiliado activo*/
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_CONYUGE),
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_CONYUGE,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			} else {
				/* mensaje no evaluado porque no llegaron datos */
				return crearMensajeNoEvaluado();
			}
			/* exitoso */
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_CONYUGE);
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_CONYUGE),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_CONYUGE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
