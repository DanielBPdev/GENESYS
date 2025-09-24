package com.asopagos.validaciones.fovis.validadores.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
import com.asopagos.validaciones.fovis.util.ValidacionFovisPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorAfiliadoPrincipalBeneficiarioConyuge extends ValidadorFovisAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaAfiliadoConyuge.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {

				logger.info("datosValidacion ---> " + datosValidacion);
				String tipoIdA = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);

				//documentos de identificación del beneficiario a validar
				TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum
						.obtnerTiposIdentificacionEnum(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
				String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				
				//documentos de identificación del afiliado a principal
				TipoIdentificacionEnum tipoIdentificacionAfiliado = TipoIdentificacionEnum
						.obtnerTiposIdentificacionEnum(datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM));
				String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);

				logger.info("tipoIdentificacionAfiliado ---> " + tipoIdentificacionAfiliado);
				logger.info("numeroIdentificacionAfiliado ---> " + numeroIdentificacionAfiliado);
				
				if (tipoIdentificacionBeneficiario != null && !tipoIdentificacionBeneficiario.equals("")
						&& numeroIdentificacionBeneficiario != null && !numeroIdentificacionBeneficiario.equals("")) {
					logger.info("ingresa el primer if de conyugue");
					List<ClasificacionEnum> clasificacionConyuge = new ArrayList<ClasificacionEnum>();
					clasificacionConyuge.add(ClasificacionEnum.CONYUGE);
					boolean existe = false;
					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADOS)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, clasificacionConyuge)
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO,
									ValidacionFovisPersonaUtils.obtenerListaEstadoActivo())
							.getResultList();
					logger.info("personasConTipoYNumero " + personasConTipoYNumero.size());
					if (personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) {
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADOS)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, clasificacionConyuge)
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO,
										ValidacionFovisPersonaUtils.obtenerListaEstadoActivo())
								.getResultList();
						logger.info("personasConNumero " + personasConNumero.size());
						if (personasConNumero != null && !personasConNumero.isEmpty()) {
							if (personasConNumero.iterator().next().getPersona().getTipoIdentificacion()
									.equals(tipoIdentificacionBeneficiario)
									&& personasConNumero.iterator().next().getPersona().getNumeroIdentificacion()
											.equals(numeroIdentificacionBeneficiario)) {
								logger.info("val 1 " + personasConNumero.iterator().next().getPersona().getTipoIdentificacion());
								logger.info("tipoIdentificacionBeneficiario " + tipoIdentificacionBeneficiario);
								logger.info("val 2 " + personasConNumero.iterator().next().getPersona().getNumeroIdentificacion());
								logger.info("numeroIdentificacionBeneficiario " + numeroIdentificacionBeneficiario);
								existe = false;
							} else {
								existe = true;
								logger.info("truee 1" + existe);
							}
						}
					} else {
						logger.info("val 1 " + personasConTipoYNumero.iterator().next().getPersona().getTipoIdentificacion());
						logger.info("tipoIdentificacionBeneficiario " + tipoIdentificacionBeneficiario);
						logger.info("val 2 " + personasConTipoYNumero.iterator().next().getPersona().getNumeroIdentificacion());
						logger.info("numeroIdentificacionBeneficiario " + numeroIdentificacionBeneficiario);
						if (personasConTipoYNumero.iterator().next().getPersona().getTipoIdentificacion()
								.equals(tipoIdentificacionBeneficiario)
								&& personasConTipoYNumero.iterator().next().getPersona().getNumeroIdentificacion()
										.equals(numeroIdentificacionBeneficiario)) {
							existe = false;
						} else {
							existe = true;
							logger.info("true 2  " + existe);
						}
						//existe = true;
					}
					if (existe) {
						logger.debug("No aprobada- Existe persona afiliada con un beneficiario tipo conyuge");
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_CONYUGE_REGISTRADO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}

				} else {
					logger.debug("NO EVALUADO - No hay parametros");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("NO EVALUADO- no hay valores en el map");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE);
		} catch (Exception e) {
			logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada", e);
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_CONYUGE_REGISTRADO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
