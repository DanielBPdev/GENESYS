package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona es beneficiario tipo padre.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaBeneficiarioPadre extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorPersonaBeneficiarioPadre");
			boolean existe = false;
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				
				TipoBeneficiarioEnum tipoBene=null;
				TipoAfiliadoEnum tipoAfiliado=null;
                try {
                    tipoBene = TipoBeneficiarioEnum.valueOf(datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM));
                } catch (Exception e) {
                    tipoBene = null;
                    try {
                        tipoAfiliado = TipoAfiliadoEnum.valueOf(datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM));
                    } catch (Exception e2) {
                        tipoAfiliado = null;
                    }
                }
				List<Long> idBeneficiario=new ArrayList<Long>();
				
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")) {
					List<Beneficiario> beneficiarioTipoNumeroActivo = entityManager
							.createNamedQuery(
									NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionPadre())
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
							.getResultList();

					if (beneficiarioTipoNumeroActivo != null && !beneficiarioTipoNumeroActivo.isEmpty()) {
						if (tipoAfiliado != null) {
							existe = true;
							for (Beneficiario beneficiario : beneficiarioTipoNumeroActivo) {                                
							    idBeneficiario.add(beneficiario.getIdBeneficiario());
                            }
						}						
						else if(beneficiarioTipoNumeroActivo.iterator().next().getAfiliado().getPersona()
								.getNumeroIdentificacion()
								.equals(datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM))
								&& beneficiarioTipoNumeroActivo.iterator().next().getAfiliado().getPersona()
										.getTipoIdentificacion()
										.equals(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(
												datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM)))) {
							existe = false;	
                        }
                        else {
                            if (tipoBene != null) {
                                for (Beneficiario beneficiario : beneficiarioTipoNumeroActivo) {
                                    idBeneficiario.add(beneficiario.getIdBeneficiario());
                                }
                            }
							existe = true;							
						}
					} else {
						List<Beneficiario> beneficiarioNumeroActivo = entityManager
								.createNamedQuery(
										NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM,ValidacionPersonaUtils.obtenerClasificacionPadre())
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
								.getResultList();
						if (beneficiarioNumeroActivo!=null && !beneficiarioNumeroActivo.isEmpty()) {
							if (tipoAfiliado != null) {
                                for (Beneficiario beneficiario : beneficiarioNumeroActivo) {
                                    idBeneficiario.add(beneficiario.getIdBeneficiario());
                                }
								existe = true;
							}
							else if (beneficiarioNumeroActivo.iterator().next().getAfiliado().getPersona()
									.getNumeroIdentificacion()
									.equals(datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM))
									&& beneficiarioNumeroActivo.iterator().next().getAfiliado().getPersona()
											.getTipoIdentificacion()
											.equals(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(
													datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM)))) {
								existe = false;
							} else {
								if (tipoBene != null) {
	                                for (Beneficiario beneficiario : beneficiarioNumeroActivo) {
	                                    idBeneficiario.add(beneficiario.getIdBeneficiario());
	                                }
								}
								existe = true;
							}
						} 
					}
				} else {
					/* mensaje no evaluado porque no hay parametros */
					return crearMensajeNoEvaluado();
				}
				if (existe) {
					/*
					 * No aprobada- Existe Persona registrada como beneficiario
					 * tipo hijo y estado afiliado activo
					 */
					if (tipoAfiliado != null || TipoBeneficiarioEnum.CONYUGE.equals(tipoBene)) {
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_PADRE),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_PADRE,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1,idBeneficiario);
					}
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_PADRE),
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_PADRE,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			} else {
				/* mensaje no evaluado porque no llegaron datos */
				return crearMensajeNoEvaluado();
			}
			/* exitoso */
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_PADRE);
		} catch (Exception e) {
			logger.error("Ocurrió un error en el ValidadorPersonaBeneficiarioPadre.execute",e);
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_PADRE),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_BENEFICIARIO_PADRE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}


}
