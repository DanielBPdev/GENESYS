package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
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
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaMenor60 extends ValidadorAbstract {

	/**
	 * Constante que estable la edad a verificar
	 */
	private static final int EDAD_ESTABLECIDA = 60;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.info("Inicio ValidadorPersonaMenor60");
			boolean existe = false;
			if (datosValidacion != null && !datosValidacion.isEmpty()){ /*&& (datosValidacion.get(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM).equals("MADRE") || datosValidacion.get(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM).equals("PADRE"))) {*/
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				Boolean condicionInvalidez = null;
				logger.info("datosValidacion---> " +datosValidacion);
				if(datosValidacion.get(ConstantesValidaciones.CONDICION_INVALIDEZ_PARAM)!=null){
					condicionInvalidez = Boolean.parseBoolean(datosValidacion.get(ConstantesValidaciones.CONDICION_INVALIDEZ_PARAM));
				}
				logger.info("condicionInvalidez inicial" +condicionInvalidez);
				
				PersonaDetalle personaDetalle = null;
				CondicionInvalidez conInvalidez= null;
				
				/* Se verifica si la fecha de nacimiento es null */
				if (fechaNacimiento == null || fechaNacimiento.equals("")) {
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MENOR_60);
				} else {
					if (tipoIdentificacion != null && !tipoIdentificacion.equals("")) {
						Beneficiario beneficiario = null;
						List<Beneficiario> beneficiarioTipoIdentificacion = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO)
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
						if (beneficiarioTipoIdentificacion != null && !beneficiarioTipoIdentificacion.isEmpty()) {
							beneficiario = beneficiarioTipoIdentificacion.get(0);
							existe = true;
						} else {
							List<Beneficiario> beneficiarioIdentificacion = entityManager
									.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_DOCUMENTO)
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
							if (beneficiarioIdentificacion != null && !beneficiarioIdentificacion.isEmpty()
									&& personaDetalle != null) {
								beneficiario = beneficiarioIdentificacion.get(0);
								existe = true;
							}
						}
						
                        try {
                            conInvalidez = entityManager
                                    .createNamedQuery(
                                            NamedQueriesConstants.BUSCAR_CONDICIONINVALIDEZ_ID_NUMERO_TIPO_IDENTIFICACION,
                                            CondicionInvalidez.class)
                                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                                    .getSingleResult();
                        } catch (NoResultException e) {
                            conInvalidez = null;
                        }
						logger.info("condicionInvalidez 2.0" +condicionInvalidez);

                        if (condicionInvalidez == null && conInvalidez != null) {
                            condicionInvalidez = conInvalidez.getCondicionInvalidez();
                        }

						logger.info("condicionInvalidez 3.0" +condicionInvalidez);
						if(condicionInvalidez==null){
							condicionInvalidez = false;
						}

						logger.info("condicionInvalidez 4.0" +condicionInvalidez);
						int edadPersona = ValidacionPersonaUtils
								.calcularEdadAnos(ValidacionPersonaUtils.convertirFecha(fechaNacimiento));
						/*
						 * Verificacion de la edad del beneficiario sea menor a
						 * la edad establecidad que es 60 años
						 */
						if (edadPersona < EDAD_ESTABLECIDA) {
							/*si el benenficiario no tiene condicion de invalidez NO APROBADA*/
							logger.info("condicionInvalidez 5.0" +condicionInvalidez);
							if (condicionInvalidez) {
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_PERSONA_MENOR_60_INVALIDEZ),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_PERSONA_MENOR_60,
										TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
							} else {
								/*El beneficiario si tiene condicion de invalidez APROBADA*/
								return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MENOR_60);
							}
						}

					} else {
						/* mensaje no evaluado porque falta informacion */
						return crearMensajeNoEvaluado();
					}
				}
			} else {
				/* mensaje no evaluado porque faltan datos */
				return crearMensajeNoEvaluado();
			}
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MENOR_60);
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_MENOR_60),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_MENOR_60,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
