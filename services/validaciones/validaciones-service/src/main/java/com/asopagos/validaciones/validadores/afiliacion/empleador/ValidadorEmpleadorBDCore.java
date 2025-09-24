package com.asopagos.validaciones.validadores.afiliacion.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.EstadoListaEspecialRevisionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.EstadosUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorCore;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
/**
 * @author jcamargo
 *
 */
public class ValidadorEmpleadorBDCore implements ValidadorCore {

	private static final String VALIDACIONES_LISTAS_ESPECIAL_BUSCAR_POR_EMPRESA = "Validaciones.ListasEspecial.buscarPorEmpresa";
	private static final String VALIDACIONES_LISTAS_ESPECIAL_BUSCAR_POR_PERSONA = "Validaciones.ListasEspecial.buscarPorPersona";
	private ResourceBundle myResources = ResourceBundle.getBundle(ConstantesValidaciones.NOMBRE_BUNDLE_MENSAJES);
	private EntityManager entityManager;

	public static final String CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL = "Empleador.razonSocial.buscarTodos";
	public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO = "Empleador.tipoIdentificacion.numIdentificacion.buscarTodos";
	public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_DV = "Empleador.tipoIdentificacion.numIdentificacion.DV.buscarTodos";
	public static final String CONSULTAR_ESTADO_CARTERA_EMPLEADOR = "Validaciones.cartera.consultarEstadoCartera";
	public static final String CONSULTAR_PERSONA_DETALLE_TIPO_INDENTIFICACION_NUMERO = "PersonaDetalle.tipoIdentificacion.numIdentificacion.fallecida";
	
	public static final String CONSULTAR_SUBSANACION_EMPLEADOR = "Validaciones.consultar.subsanacion.empleador";
        private final ILogger logger = LogManager.getLogger(ValidadorEmpleadorBDCore.class);
	
	
	
	
	/**
	 * Metodo que se encarga de validar parametros para realizar la respectiva validación para reintegro de un empleador
	 * @param empleador
	 * @return 
	 */
	private boolean noCumpleEmpleadorDatos(Empleador empleador) {
		if (empleador.getMotivoDesafiliacion() == null ) {
			return true;
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
            logger.info("*--INICIO--* execute");
		if (datosValidacion != null && !datosValidacion.isEmpty()) {
			String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);

			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			String digVer = datosValidacion.get("digitoVerificacion");
			Short dv = null;
			if (digVer != null) {
				dv = Short.parseShort(datosValidacion.get("digitoVerificacion"));
			}
			ListaEspecialRevision listaEspecialRevision = null;
			try {
				Query listas = armarConsultaListas(datosValidacion);
				listaEspecialRevision = (ListaEspecialRevision) listas.getSingleResult();
			} catch (NoResultException e1) {
			}

			if ((tipoIdentificacion != null) && (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
				try {
					List<Empleador> result = buscarEmpleador(TipoIdentificacionEnum.valueOf(tipoIdentificacion),
							numeroIdentificacion, dv, null);
					boolean personaFallecida = consultarPersona(TipoIdentificacionEnum.valueOf(tipoIdentificacion),
                            numeroIdentificacion);
                    if (personaFallecida) {
                        logger.info("*--INICIO--* execute 1");
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA),
                                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
					if (result != null && !result.isEmpty()) {
						Empleador emp = result.get(0);
                        EstadoEmpleadorEnum estadoEmpleador = emp.getEstadoEmpleador();
                        EstadoCarteraEnum estadoAporte=consultarEstadoCartera(TipoIdentificacionEnum.valueOf(tipoIdentificacion),
                                numeroIdentificacion);
						
		                //TODO se debe invocar metodo para calcular metodo del empleador
		                //emp.setEstadoEmpleador(calcularEstadoEmpleador(emp));
						if (emp != null) {
							boolean noCumple=noCumpleEmpleadorDatos(emp);
							if (estadoEmpleador
									.equals(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES)
									|| estadoEmpleador
											.equals(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION)) {
								if (listaEspecialRevision != null) {
									if (listaEspecialRevision.getEstado()
											.equals(EstadoListaEspecialRevisionEnum.EXCLUIDO)) {
                                                                            logger.info("*--INICIO--* execute 2");
										return crearValidacion(
												myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DB_EXITO),
												ResultadoValidacionEnum.APROBADA,
												ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
									} else if (listaEspecialRevision.getEstado()
											.equals(EstadoListaEspecialRevisionEnum.INCLUIDO)) {
                                                                            logger.info("*--INICIO--* execute 3");
										return crearValidacion(
												myResources
														.getString(ConstantesValidaciones.MENSAJE_EMPL_EXISTE_LISTAS),
												ResultadoValidacionEnum.NO_APROBADA,
												ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
												TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
									}
								} else {
                                                                    logger.info("*--INICIO--* execute 4");
									return crearValidacion(
											myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DB_EXITO),
											ResultadoValidacionEnum.APROBADA,
											ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
								}
							} else if (estadoEmpleador.equals(EstadoEmpleadorEnum.INACTIVO)
									|| estadoEmpleador
											.equals(EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES)) {
								if(noCumple){
									throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_HTTP_CONFLICT,
											"El empleador se encuentra con datos inconsistentes");
								}
								Boolean enListasEspecial = listaEspecialRevision != null && listaEspecialRevision
										.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO);
								if (EstadoCarteraEnum.MOROSO.equals(estadoAporte)) {
                                                                    logger.info("*--INICIO--* execute 4");
									return crearValidacion(
											myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_MOROSO),
											ResultadoValidacionEnum.NO_APROBADA,
											ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
											TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
								} else if (EstadoCarteraEnum.AL_DIA.equals(estadoAporte)) {

									if (emp.getMotivoDesafiliacion().equals(MotivoDesafiliacionEnum.CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO)
											|| emp.getMotivoDesafiliacion().equals(MotivoDesafiliacionEnum.FUSION_ADQUISICION)) {
                                                                            logger.info("*--INICIO--* execute 5");
                                                                            return crearValidacion(
												myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DESAF),
												ResultadoValidacionEnum.NO_APROBADA,
												ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
												TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
									} else if (emp.getMotivoDesafiliacion()
											.equals(MotivoDesafiliacionEnum.EXPULSION_POR_INFORMACION_INCORRECTA)
											|| emp.getMotivoDesafiliacion().equals(
													MotivoDesafiliacionEnum.EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS)) {
										
										Boolean expulsionSubsanada = null;
										Object resultado = entityManager.createNamedQuery(CONSULTAR_SUBSANACION_EMPLEADOR)
												.setParameter("idEmpleador", emp.getIdEmpleador())
							                    .getSingleResult();
										if (resultado == null) {
											expulsionSubsanada = null;
							            }
							            else {
							            	if(Boolean.TRUE.equals(resultado)){
							            		expulsionSubsanada = true;
							            	}else{
							            		expulsionSubsanada = false;
							            	}
							            }
										//if (emp.getExpulsionSubsanada() == null || Boolean.FALSE.equals(emp.getExpulsionSubsanada())) { //Se modifico en la mantis 262664 por la linea siguiente
										if (expulsionSubsanada == null || Boolean.FALSE.equals(expulsionSubsanada)) {
                                                                                    logger.info("*--INICIO--* execute 6");
											return crearValidacion(
													myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DESAF),
													ResultadoValidacionEnum.NO_APROBADA,
													ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
													TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
										} else {
											if (enListasEspecial) {
                                                                                            logger.info("*--INICIO--* execute 7");
												return crearValidacion(
														myResources
																.getString(
																		ConstantesValidaciones.KEY_MENSAJE_EMP_DESAF_LISTAS)
																.replace(ConstantesValidaciones.MENSAJE_PARAM_0,
																		emp.getMotivoDesafiliacion().getDescripcion()),
														ResultadoValidacionEnum.NO_APROBADA,
														ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
														TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
											} else {
                                                                                            logger.info("*--INICIO--* execute 8");
												return crearValidacion(
														myResources.getString(
																ConstantesValidaciones.KEY_MENSAJE_EMP_DB_EXITO),
														ResultadoValidacionEnum.APROBADA,
														ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
											}
										}

									} else if (emp.getMotivoDesafiliacion()
											.equals(MotivoDesafiliacionEnum.CERO_TRABAJADORES_NOVEDAD_INTERNA)
											|| emp.getMotivoDesafiliacion()
													.equals(MotivoDesafiliacionEnum.CERO_TRABAJADORES_SOLICITUD_EMPLEADOR)
											|| emp.getMotivoDesafiliacion()
													.equals(MotivoDesafiliacionEnum.RETIRO_POR_TRASLADO_OTRA_CCF)
											|| emp.getMotivoDesafiliacion()
													.equals(MotivoDesafiliacionEnum.EXPULSION_POR_MOROSIDAD)
											|| emp.getMotivoDesafiliacion().equals(MotivoDesafiliacionEnum.OTRO)
											|| emp.getMotivoDesafiliacion().equals(MotivoDesafiliacionEnum.ANULADO)) {
										if (enListasEspecial) {
                                                                                    logger.info("*--INICIO--* execute 9");
											return crearValidacion(
													myResources
															.getString(
																	ConstantesValidaciones.KEY_MENSAJE_EMP_DESAF_LISTAS)
															.replace(ConstantesValidaciones.MENSAJE_PARAM_0,
																	emp.getMotivoDesafiliacion().getDescripcion()),
													ResultadoValidacionEnum.NO_APROBADA,
													ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
													TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
										} 
                                                                                
                                                                                else {
										    /* GLPI 62287
                                                                                        if(emp.getMotivoDesafiliacion().equals(MotivoDesafiliacionEnum.OTRO)){
                                                                                        logger.info("*--INICIO--* execute 10");
                                                                                        return crearValidacion(
                                                                                                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_OTRO).replace(
                                                                                                        ConstantesValidaciones.MENSAJE_PARAM_0,
                                                                                                        emp.getMotivoDesafiliacion().getDescripcion()),
                                                                                                ResultadoValidacionEnum.NO_APROBADA,
                                                                                                ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
                                                                                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
                                                                                    }*/
										    logger.info("*--INICIO--* execute 11");
											return crearValidacion(
													myResources
															.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DB_EXITO),
													ResultadoValidacionEnum.APROBADA,
													ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE, null);
										}
									}
								}
                                                                logger.info("*--INICIO--* execute 12");
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_NO_ACTIVO),
										ResultadoValidacionEnum.APROBADA,
										ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
							} else if (emp.getEstadoEmpleador().equals(EstadoEmpleadorEnum.ACTIVO)) {
                                                            logger.info("*--INICIO--* execute 13");
								/*return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_ACTIVO),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
										TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);*/
							} else {
								Boolean enListasEspecial = listaEspecialRevision != null && listaEspecialRevision
										.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO);
								if (!enListasEspecial) {
                                                                    logger.info("*--INICIO--* execute 14");
									return crearValidacion(
											myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DB_EXITO),
											ResultadoValidacionEnum.APROBADA,
											ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
								} else {
                                                                    logger.info("*--INICIO--* execute 15");
									return crearValidacion(
											myResources.getString(ConstantesValidaciones.MENSAJE_EMPL_EXISTE_LISTAS),
											ResultadoValidacionEnum.NO_APROBADA,
											ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
											TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
								}
							}
						} else {
							Boolean enListasEspecial = listaEspecialRevision != null && listaEspecialRevision
									.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO);
							if (!enListasEspecial) {
                                                            logger.info("*--INICIO--* execute 16");
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DB_EXITO),
										ResultadoValidacionEnum.APROBADA,
										ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
							} else {
                                                            logger.info("*--INICIO--* execute 17");
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.MENSAJE_EMPL_EXISTE_LISTAS),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
										TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
							}
						}
					} else {
						Boolean enListasEspecial = listaEspecialRevision != null
								&& listaEspecialRevision.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO);
						if (!enListasEspecial) {
                                                    logger.info("*--INICIO--* execute 18");
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_DB_EXITO),
									ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
						} else {
                                                    logger.info("*--INICIO--* execute 19");
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.MENSAJE_EMPL_EXISTE_LISTAS),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
						}
					}
				} catch (NoResultException e) {
					Boolean enListasEspecial = listaEspecialRevision != null
							&& listaEspecialRevision.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO);
					if (enListasEspecial) {
                                            logger.info("*--INICIO--* execute 20");
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_NO_ACTIVO_EN_LISTAS),
								ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
					} else {
                                            logger.info("*--INICIO--* execute 21");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_NO_ACTIVO),
								ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
					}
				} 
			} else {
                            logger.info("*--INICIO--* execute 22");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_PARAMS),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
			}
		}
                logger.info("*--INICIO--* execute 23");
		return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#setEntityManager(javax.
	 * persistence.EntityManager)
	 */
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private List<Empleador> buscarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Short digitoVerificacion, String razonSocial) {

		List<Empleador> listEmpleador = new ArrayList<>();

		if (razonSocial != null) {
			// consultar empleador por razon social
			listEmpleador = (List<Empleador>) entityManager.createNamedQuery(CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL)
					.setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();

		} else if (digitoVerificacion != null && tipoIdentificacion != null && numeroIdentificacion != null) {
			// consultar empleador por tipo, numero de identificacion y digito
			// de verificacion
			listEmpleador = (List<Empleador>) entityManager
					.createNamedQuery(CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_DV)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("digitoVerificacion", digitoVerificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		} else if (tipoIdentificacion != null && numeroIdentificacion != null) {
			// consultar empleador por tipo y numero de identificacion
			listEmpleador = (List<Empleador>) entityManager
					.createNamedQuery(CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
		}
		
		  List<ConsultarEstadoDTO> listConsulta= new ArrayList<ConsultarEstadoDTO>();
	        for (Empleador empleador : listEmpleador) {
	            ConsultarEstadoDTO paramsConsulta= new ConsultarEstadoDTO();
	            paramsConsulta.setEntityManager(entityManager);
	            paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
	            paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
	            paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
	            listConsulta.add(paramsConsulta);
	        }
        if (!listConsulta.isEmpty() && listConsulta != null) {
            List<EstadoDTO> listEstadod = EstadosUtils.consultarEstadoCaja(listConsulta);
            for (EstadoDTO estadoDTO : listEstadod) {
                for (Empleador empleador : listEmpleador) {
                    if (empleador.getEmpresa().getPersona().getNumeroIdentificacion().equals(estadoDTO.getNumeroIdentificacion())
                            && empleador.getEmpresa().getPersona().getTipoIdentificacion().equals(estadoDTO.getTipoIdentificacion())) {
                        empleador.setEstadoEmpleador(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
                        if(estadoDTO.getEstado() != null){
                            empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoDTO.getEstado().toString()));                            
                        }
                        break;
                    }
                }
            }
        }
		
		return listEmpleador;
	}

	private ValidacionDTO crearValidacion(String detalle, ResultadoValidacionEnum resultado,
			ValidacionCoreEnum validacion) {
		ValidacionDTO resultadoValidacion = new ValidacionDTO();
		resultadoValidacion.setDetalle(detalle);
		resultadoValidacion.setResultado(resultado);
		resultadoValidacion.setValidacion(validacion);
		return resultadoValidacion;
	}

	private ValidacionDTO crearValidacion(String detalle, ResultadoValidacionEnum resultado,
			ValidacionCoreEnum validacion, TipoExcepcionFuncionalEnum excepcion) {
		ValidacionDTO resultadoValidacion = new ValidacionDTO();
		resultadoValidacion.setDetalle(detalle);
		resultadoValidacion.setResultado(resultado);
		resultadoValidacion.setValidacion(validacion);
		resultadoValidacion.setTipoExcepcion(excepcion);
		return resultadoValidacion;
	}
	
    private EstadoEmpleadorEnum consultarEstadoEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        ConsultarEstadoDTO consultEstado = new ConsultarEstadoDTO();
        consultEstado.setEntityManager(entityManager);
        consultEstado.setTipoIdentificacion(tipoIdentificacion);
        consultEstado.setNumeroIdentificacion(numeroIdentificacion);
        consultEstado.setTipoPersona(ConstantesComunes.EMPLEADORES);
        List<ConsultarEstadoDTO> listConsultEstados = new ArrayList<ConsultarEstadoDTO>();
        listConsultEstados.add(consultEstado);
        List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listConsultEstados);
        return EstadoEmpleadorEnum.valueOf(listEstados.get(0).getEstado().toString());
    }
    
    private boolean consultarPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            Boolean fallecida = (Boolean) entityManager.createNamedQuery(CONSULTAR_PERSONA_DETALLE_TIPO_INDENTIFICACION_NUMERO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
            return fallecida;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
	
    private EstadoCarteraEnum consultarEstadoCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {

        try {
            EstadoCarteraEnum estadoCarteraEnum = null;
            List<EstadoCarteraEnum> lstEstadosCartera = entityManager.createNamedQuery(CONSULTAR_ESTADO_CARTERA_EMPLEADOR)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoSolicitante", TipoSolicitanteMovimientoAporteEnum.EMPLEADOR)
                    .setParameter("estadoOperacion", EstadoOperacionCarteraEnum.VIGENTE).getResultList();
            switch (lstEstadosCartera.size()) {
                case 0:
                    estadoCarteraEnum = EstadoCarteraEnum.AL_DIA;
                    break;
                case 1:
                    estadoCarteraEnum = lstEstadosCartera.get(0);
                    break;
                default:
                    for (EstadoCarteraEnum estadoCartera : lstEstadosCartera) {
                        if (estadoCartera.equals(EstadoCarteraEnum.MOROSO)) {
                            return estadoCartera;
                        }
                    }
                    estadoCarteraEnum = EstadoCarteraEnum.AL_DIA;
                    break;
            }
            return estadoCarteraEnum;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	private Query armarConsultaListas(Map<String, String> params) {
		String numeroIdentificacion = params.get(ConstantesValidaciones.NUM_ID_PARAM);
		String tipoIdentificacion = params.get(ConstantesValidaciones.TIPO_ID_PARAM);
		String digVer = params.get("digitoVerificacion");
		Query query = null;
		if (numeroIdentificacion != null && !numeroIdentificacion.equals("") && tipoIdentificacion != null
				&& !tipoIdentificacion.equals("") && (digVer == null)) {
			query = entityManager.createNamedQuery(VALIDACIONES_LISTAS_ESPECIAL_BUSCAR_POR_PERSONA)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoIdentificacion", TipoIdentificacionEnum.valueOf(tipoIdentificacion));
		} else if (numeroIdentificacion != null && !numeroIdentificacion.equals("") && tipoIdentificacion != null
				&& !tipoIdentificacion.equals("") && (digVer != null && !digVer.equals(""))) {
			query = entityManager.createNamedQuery(VALIDACIONES_LISTAS_ESPECIAL_BUSCAR_POR_EMPRESA)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("tipoIdentificacion", TipoIdentificacionEnum.valueOf(tipoIdentificacion))
					.setParameter("digitoVerificacion", Byte.parseByte(digVer));
		}
		return query;
	}
}
