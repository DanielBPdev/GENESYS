package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.Afiliado;
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
 * Clase que contiene la lógica para validar si la persona es beneficiario tipo hijo de dos grupo familiar. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaHijoDosGrupoFamiliar extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaHijoDosGrupoFamiliar.execute");
		try{
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String grupoFamiliarActual = datosValidacion.get(ConstantesValidaciones.ID_GRUPOFAMILIAR);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String tipoIdAfiliado = datosValidacion.get(ConstantesValidaciones.TIPO_IDENTIFICACION_AFILIADO);
				TipoIdentificacionEnum tipoIdentificacionAfiliado = null;
				if (tipoIdAfiliado != null) {
					tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoIdAfiliado);
				}
				String numeroIdAfiliado = datosValidacion.get(ConstantesValidaciones.NUMERO_IDENTIFICACION_AFILIADO);

				logger.info("grupoFamilyActual: " + grupoFamiliarActual);
                                String proceso = datosValidacion.get(ConstantesValidaciones.NAMED_QUERY_PARAM_OBJ_VAL);
                                
                                logger.info("Inicio de método ValidadorPersonaHijoDosGrupoFamiliar.execute tipoIdentificacion " + tipoIdentificacion);
                                if(proceso.equals("HERMANO_HUERFANO_DE_PADRES")){
                                    List<Beneficiario> personaHermanoHuerfano = entityManager
                                                            .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_HERMANO_HUERFANO_DE_PADRES)
                                                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                                                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
									
									if(!personaHermanoHuerfano.isEmpty()){
										if (personaHermanoHuerfano.size() >= 2) {
											return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_DOS_GRUPO_FAMILIAR),
																						   ResultadoValidacionEnum.NO_APROBADA,
																						   ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_DOS_GRUPO_FAMILIAR,
																						   TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
											}else{
												Beneficiario beneficiario = personaHermanoHuerfano.get(0);
												logger.info(beneficiario.getGrupoFamiliar().getIdGrupoFamiliar());
												logger.info(grupoFamiliarActual);
																	
												if (!Long.toString(beneficiario.getGrupoFamiliar().getIdGrupoFamiliar()).equals(grupoFamiliarActual)) {
													return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_DOS_GRUPO_FAMILIAR),
																								ResultadoValidacionEnum.NO_APROBADA,
																								ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_DOS_GRUPO_FAMILIAR,TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);	
												}
											}
											logger.info("Inicio de método ValidadorPersonaHijoDosGrupoFamiliar IF HAY VALOR" + personaHermanoHuerfano.size());
										}else{
                                        logger.info("Inicio de método ValidadorPersonaHijoDosGrupoFamiliar IF (NOOOO) HAY VALOR" + personaHermanoHuerfano.size());
                                    }
                                }
                                
				Long idBeneficiario = null;
				if(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO)!=null){
					idBeneficiario = new Long(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO));
				}
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")){
					boolean existe = false;
					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, obtenerClasificacionHijo())
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO).getResultList();
					if(personasConTipoYNumero == null || personasConTipoYNumero.isEmpty() || personasConTipoYNumero.size()<2){
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, obtenerClasificacionHijo())
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO).getResultList();
						if(personasConNumero != null && !personasConNumero.isEmpty()&& personasConNumero.size()==2){
							existe=true;
							for (Beneficiario beneficiario : personasConNumero) {
								if(beneficiario.getIdBeneficiario().equals(idBeneficiario)){
									existe =false;
								}
							}
						}
					}else{
						existe = true;
						for (Beneficiario beneficiario : personasConTipoYNumero) {
							if(beneficiario.getIdBeneficiario().equals(idBeneficiario)){
								existe =false;
							}
							else if(Long.toString(beneficiario.getGrupoFamiliar().getIdGrupoFamiliar()).equals(grupoFamiliarActual)){
								existe =false;
							}
						}

					}
					if(existe){
						logger.debug("No aprobada- La persona ya está como hijo en dos grupos familiares");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_DOS_GRUPO_FAMILIAR),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_DOS_GRUPO_FAMILIAR,TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);					
					}
				}else{
					logger.debug("NO EVALUADO - No hay parametros");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.debug("NO EVALUADO- no hay valores en el map");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_DOS_GRUPO_FAMILIAR);
		}catch(Exception e){
			logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada",e);
			return crearMensajeNoEvaluado();
		}
	}


	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * @return validacion afiliaacion instanciada.
	 */
	private  ValidacionDTO crearMensajeNoEvaluado(){
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_DOS_GRUPO_FAMILIAR),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_DOS_GRUPO_FAMILIAR,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

    /**
     * Método que obtiene las clasisficaciones por sujeto tramite.
     * @param tipo de sujeto tramite.
     * @return listado de las clasificaciones.
     */
	public List<ClasificacionEnum> obtenerClasificacionHijo(){
		List<ClasificacionEnum> clasificacionList = new ArrayList<ClasificacionEnum>();
		clasificacionList.add(ClasificacionEnum.HIJASTRO);
		clasificacionList.add(ClasificacionEnum.HIJO_ADOPTIVO);
		clasificacionList.add(ClasificacionEnum.HIJO_BIOLOGICO);
		clasificacionList.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
		clasificacionList.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
		
		return clasificacionList;
	}
}
