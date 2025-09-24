package com.asopagos.empleadores.ejb;

import static com.asopagos.util.Interpolator.interpolate;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.DatosRegistroSucursalPilaDTO;
import com.asopagos.dto.fovis.ProveedorDTO;
import com.asopagos.dto.modelo.BancoModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.ProveedorModeloDTO;												  
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empleadores.constants.NamedQueriesConstants;
import com.asopagos.empresas.service.EmpresasService;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import com.asopagos.entidades.ccf.fovis.LegalizacionDesembolosoProveedor;
import com.asopagos.entidades.ccf.fovis.Oferente;
import com.asopagos.entidades.ccf.fovis.Proveedor;												  
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rutine.empleadores.CrearEmpresaRutine;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de empresas <b>Historia de Usuario:</b> HU 111-066, HU
 * 111-070, TRA 111-329
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class EmpresasBusiness implements EmpresasService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "empresas_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(EmpresasBusiness.class);

	/**
	 * @see com.asopagos.empresas.service.EmpresasService#consultarSucursalesEmpresa(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SucursalEmpresa> consultarSucursalesEmpresa(Long idEmpresa) {

		logger.debug("Inicia consultarSucursalesEmpresa(Long)");
		List<SucursalEmpresa> listSucursales = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSALES_EMPLEADOR)
				.setParameter("idEmpresa", idEmpresa).getResultList();

		if (listSucursales.size() > 0) {
			logger.debug("Finaliza consultarSucursalesEmpresa(Long)");
			return listSucursales;
		} else {
			logger.debug("Finaliza consultarSucursalesEmpresa(Long): No hay resultados para el filtro de búsqueda");
			return null;
		}
	}

	/**
	 * @see com.asopagos.empresas.service.EmpresasService#actualizarSucursalEmpresa(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	public void actualizarSucursalEmpresa(Long idEmpresa, List<SucursalEmpresa> listadoSucursales) {

		logger.debug("Inicia actualizarSucursalEmpresa(Long, Long, SucursalEmpresa)");
		// se consulta que el empleador sí éste registrado
		Empresa empleador = null;

		try {
			empleador = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID, Empresa.class)
					.setParameter("idEmpresa", idEmpresa).getSingleResult();
		} catch (NoResultException e) {
			empleador = null;
		} catch (Exception e) {
			logger.debug("Finaliza actualizarSucursalEmpresa(Long, SucursalEmpresa)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		// si se encuentra el empleador
		if (empleador != null) {
			for (SucursalEmpresa sucursalEmpresa : listadoSucursales) {
				// se consulta de que la sucursal sí éste registrada
				SucursalEmpresa suEmpresa = entityManager.find(SucursalEmpresa.class,
						sucursalEmpresa.getIdSucursalEmpresa());

				// si se encuentra una sucursal
				if (suEmpresa != null) {
					logger.info(suEmpresa.toString());
					try {
							if ((suEmpresa.getUbicacion() == null || suEmpresa.getUbicacion().getIdUbicacion() == null) && (sucursalEmpresa.getUbicacion().getIdUbicacion() == null)) {
							// si la sucursal no tiene ubicación
								entityManager.persist(sucursalEmpresa.getUbicacion());
							} else {
								// se actualiza la información de la ubicación de la
								// sucursal de empleador
								if(suEmpresa.getUbicacion() != null){
									sucursalEmpresa.getUbicacion().setIdUbicacion(suEmpresa.getUbicacion().getIdUbicacion());
								}

								entityManager.merge(sucursalEmpresa.getUbicacion());
							}
						
						// se actualiza la información básica de
						entityManager.merge(sucursalEmpresa);
					} catch (PersistenceException pe) {
						logger.error("Error de persistencia ", pe);
						logger.debug(
								"Finaliza actualizarSucursalEmpresa(Long, Long, SucursalEmpresa): Error de persistencia");
						throw new TechnicalException(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO);
					}
				} else {
					logger.debug(
							"Finaliza actualizarSucursalEmpresa(Long, Long, SucursalEmpresa): No existe la sucursal a actualzar");
					throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
				}
			}
		} else {
			logger.debug(
					"Finaliza actualizarSucursalEmpresa(Long, Long, SucursalEmpresa): No existe el empleador asociado a al sucursal a actualizar");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
		logger.debug("Finaliza actualizarSucursalEmpresa(Long, Long, SucursalEmpresa)");
	}

	/**
	 * @see com.asopagos.empresas.service.EmpresasService#crearSucursalEmpresa(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	public List<Long> crearSucursalEmpresa(Long idEmpresa, List<SucursalEmpresa> listadoSucursales) {

		logger.info("Inicia crearSucursalEmpresa(Long, List<SucursalEmpresa>) id "+ idEmpresa);
		List<Long> listIdsSucursales = new ArrayList();
		try {
			// se consulta de que la empresa exista
			Empresa empresa = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID, Empresa.class)
					.setParameter("idEmpresa", idEmpresa).getSingleResult();

                        logger.info("Inicia crearSucursalEmpresa 2");
			// Recorre el listado para registrar cada sucursal
			for (SucursalEmpresa sucursal : listadoSucursales) {
                            logger.info("Inicia crearSucursalEmpresa 3");
				if (sucursal != null) {
                                    logger.info("Inicia crearSucursalEmpresa 4");
					try {
						SucursalEmpresa sucuOriginal = null;
						if (sucursal.getIdSucursalEmpresa() != null) {
                                                    logger.info("Inicia crearSucursalEmpresa 5");
							sucuOriginal = entityManager
									.createNamedQuery(
											NamedQueriesConstants.CONSULTAR_SUCURSALES_EMPLEADOR_POR_IDSUCURSAL,
											SucursalEmpresa.class)
									.setParameter("idSucursalEmpresa", sucursal.getIdSucursalEmpresa())
									.getSingleResult();
						} else {
                                                    logger.info("Inicia crearSucursalEmpresa 6");
							sucuOriginal = entityManager
									.createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSAL_CODIGO_ID_EMPRESA,
											SucursalEmpresa.class)
									.setParameter("codigoSucursal", sucursal.getCodigo())
									.setParameter("idEmpresa", idEmpresa).getSingleResult();
							sucursal.setIdSucursalEmpresa(sucuOriginal.getIdSucursalEmpresa());
						}
						List<SucursalEmpresa> listSucu = new ArrayList<SucursalEmpresa>();
						if(sucuOriginal.getUbicacion() != null){
							sucursal.getUbicacion().setIdUbicacion(sucuOriginal.getUbicacion().getIdUbicacion());
						}
						listSucu.add(sucursal);
                                                logger.info("Inicia crearSucursalEmpresa 7");
						actualizarSucursalEmpresa(idEmpresa, listSucu);
                                                logger.info("Inicia crearSucursalEmpresa 8");
						listIdsSucursales.add(sucuOriginal.getIdSucursalEmpresa());
					} catch (NoResultException e) {
						// valida que la sucursal tenga ubicacion y id de
						// ubicacion
                                                logger.info("Inicia crearSucursalEmpresa 9");
						if (sucursal.getUbicacion().getIdUbicacion() != null) {
                                                    logger.info("Inicia crearSucursalEmpresa 10");
							// consulta la ubicacion para validar que existe
							Ubicacion ubicacion = entityManager
									.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_ID, Ubicacion.class)
									.setParameter("idUbicacionEmpresa", sucursal.getUbicacion().getIdUbicacion())
									.getSingleResult();
                                                        logger.info("Inicia crearSucursalEmpresa 11");
							if (ubicacion != null) {
                                                            logger.info("Inicia crearSucursalEmpresa 12");
								// Asigna la misma ubicacion de la oficina
								// principal del
								// empleador ajuste glpi medios de pago
								// se consultan las ubicaciones de la empresa para encontrar la principal
								
								List<UbicacionEmpresa> ubicacionesEmpresa = consultarUbicacionesEmpresa(idEmpresa);
								Ubicacion ubicacionPrincipal = empresa.getPersona().getUbicacionPrincipal();
								for (UbicacionEmpresa ubis : ubicacionesEmpresa) {
									if (ubis.getTipoUbicacion().equals(TipoUbicacionEnum.UBICACION_PRINCIPAL)) {
										ubicacionPrincipal = ubis.getUbicacion();
									}
								}
								sucursal.setUbicacion(ubicacionPrincipal);
							} else {
                                                            logger.info("Inicia crearSucursalEmpresa 13");
								sucursal.getUbicacion().setIdUbicacion(null);
								entityManager.persist(sucursal.getUbicacion());
                                                                logger.info("Inicia crearSucursalEmpresa 14");
							}
						} else {
                                                    logger.info("Inicia crearSucursalEmpresa 15");
							entityManager.persist(sucursal.getUbicacion());
                                                        logger.info("Inicia crearSucursalEmpresa 16");
						}
						// crea la sucursal y adiciona el id al listado
						// sucursal.setEmpresa(empleador.getIdEmpresa());
						sucursal.setEstadoSucursal(EstadoActivoInactivoEnum.ACTIVO);
                                                logger.info("Inicia crearSucursalEmpresa 17");
						entityManager.persist(sucursal);
						listIdsSucursales.add(sucursal.getIdSucursalEmpresa());
                                                logger.info("Inicia crearSucursalEmpresa 18");
					}
				}
			}
			logger.info("Finaliza crearSucursalEmpresa(Long, List<SucursalEmpresa>)");
			return listIdsSucursales;

		} catch (NoResultException e) {
			logger.debug(
					"Finaliza crearSucursalEmpresa(Long, List<SucursalEmpresa>): No existe el empleador a asociado a la sucursal");
			return null;
		} catch (Exception e) {
			logger.error("Existe más de un empleador", e);
			logger.debug("Finaliza crearSucursalEmpresa(Long, List<SucursalEmpresa>): Existe más de un empleador");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + "\n" + e.getMessage());
		}
	}
	/**
	 * @see com.asopagos.empresas.service.EmpresasService#consultarUbicacionesEmpresa(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UbicacionEmpresa> consultarUbicacionesEmpresa(Long idEmpresa) {
		logger.debug("Inicia actualizarSucursalEmpresa(Long, Long, SucursalEmpresa)");
		// se consulta que el empleador sí éste registrado
		Empresa empresa = null;

		try {
			empresa = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID, Empresa.class)
					.setParameter("idEmpresa", idEmpresa).getSingleResult();
		} catch (NoResultException e) {
			empresa = null;
		} catch (Exception e) {
			logger.debug("Finaliza actualizarSucursalEmpresa(Long, SucursalEmpresa)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		if (empresa != null) {
			List<UbicacionEmpresa> ubicaciones = (List<UbicacionEmpresa>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACIONES_EMPLEADOR)
					.setParameter("idEmpresa", idEmpresa).getResultList();
			logger.debug("Finaliza consultarUbicacionesEmpresa(Long)");
			return ubicaciones;
		} else {
			logger.debug("Finaliza consultarUbicacionesEmpresa(Long): No existe el empleador");
			return null;
		}
	}

	/**
	 * @see com.asopagos.empresas.service.EmpresasService#crearUbicacionesEmpresa(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	public List<Long> crearUbicacionesEmpresa(Long idEmpresa, List<UbicacionEmpresa> ubicaciones) {
		logger.debug("Inicia crearUbicacionesEmpresa(Long, List<UbicacionEmpresa>)");
		List<Long> idsUbicaciones = gestionarUbicacionesEmpresa(idEmpresa, ubicaciones, false);
		logger.debug("Finaliza crearUbicacionesEmpresa(Long, List<UbicacionEmpresa>)");
		return idsUbicaciones;
	}

	/**
	 * @see com.asopagos.empresas.service.EmpresasService#actualizarUbicacionesEmpresa(java.lang.Long,
	 *      java.util.List)
	 */
	@Override
	public void actualizarUbicacionesEmpresa(Long idEmpresa, List<UbicacionEmpresa> listUbicaciones) {
		logger.info("Inicia crearUbicacionesEmpresa(Long, List<UbicacionEmpresa>)" + idEmpresa);
                if(listUbicaciones.get(0) != null){
                    logger.info("Inicia crearUbicacionesEmpresa(Long, List<UbicacionEmpresa>)" + listUbicaciones.get(0).getIdUbicacionEmpresa());
                }
		gestionarUbicacionesEmpresa(idEmpresa, listUbicaciones, true);
		logger.info("Finaliza crearUbicacionesEmpresa(Long, List<UbicacionEmpresa>)");
	}

	/**
	 * Método que centraliza la creación y modificación de ubicaciones del
	 * emplador
	 * 
	 * @param idEmpresa
	 * @param ubicaciones
	 * @param modificar
	 * @return
	 */
	private List<Long> gestionarUbicacionesEmpresa(Long idEmpresa, List<UbicacionEmpresa> ubicaciones,
			Boolean modificar) {

		logger.debug("Inicia getionarUbicacionesEmpresa(Long, List<UbicacionEmpresa>, Boolean)");
		Set<Long> listIdsUbicaciones = new HashSet();
		// valida que el empleador si existe en el sistema
		Empresa empresa = null;
		try {
			empresa = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID, Empresa.class)
					.setParameter("idEmpresa", idEmpresa).getSingleResult();
		} catch (NoResultException e) {
			empresa = null;
		}
		if (empresa != null) {
			// valida que el usuario no tenga
			Map<TipoUbicacionEnum, UbicacionEmpresa> ubicacionesExistentes = new EnumMap(TipoUbicacionEnum.class);
			List<UbicacionEmpresa> ubicacionesEmpresa = consultarUbicacionesEmpresa(idEmpresa);

			if (ubicaciones != null && !ubicaciones.isEmpty()) {
				if (ubicacionesEmpresa != null && !ubicacionesEmpresa.isEmpty()) {
					for (UbicacionEmpresa ue : ubicacionesEmpresa) {
						ubicacionesExistentes.put(ue.getTipoUbicacion(), ue);
					}
				}
				// Recorre el listado para guardar cada ubicación
				for (UbicacionEmpresa ue : ubicaciones) {
					// se valida para evitar que cree un tipo de ubicación ya
					// existente
					if (ubicacionesExistentes.containsKey(ue.getTipoUbicacion())) {
						UbicacionEmpresa ueExistente = ubicacionesExistentes.get(ue.getTipoUbicacion());
						if (modificar || ((empresa.getCreadoPorPila()) != null && empresa.getCreadoPorPila())) {
							Ubicacion ubicacion = ue.getUbicacion();
							// Setear nuevamente las propiedades por si han
							// cambiado
							ubicacion.setEmail(ue.getUbicacion().getEmail());
							ubicacion.setAutorizacionEnvioEmail(ue.getUbicacion().getAutorizacionEnvioEmail());

							ubicacion.setIdUbicacion(ueExistente.getUbicacion().getIdUbicacion());
							ubicacion.setIdUbicacion(ueExistente.getUbicacion().getIdUbicacion());
							logger.info("**__**  ENTRA A ACTUALIZAR UBICACION PRIMER MERGE getEmail: "+ubicacion.getEmail()+" ubicacion.setAutorizacionEnvioEmail: "+ubicacion.getAutorizacionEnvioEmail());
							if (ubicacion.getEmail() != null || ubicacion.getAutorizacionEnvioEmail() != null 
							&& ubicacion.getIdUbicacion() != null) {
							ubicacion = entityManager.merge(ubicacion);
							}
							ue.setUbicacion(ubicacion);
							ubicacionesExistentes.put(ue.getTipoUbicacion(), ue);
						}
						listIdsUbicaciones.add(ueExistente.getIdUbicacionEmpresa());
					} else {
						Ubicacion ubicacion = ue.getUbicacion();
						if (ubicacion != null) {
							if (ubicacion.getIdUbicacion() != null) {
								
							logger.info("**__**  ENTRA A ACTUALIZAR UBICACION SEGUNDO MERGE getEmail: "+ubicacion.getEmail()+" ubicacion.setAutorizacionEnvioEmail: "+ubicacion.getAutorizacionEnvioEmail());
							if (ubicacion.getEmail() != null || ubicacion.getAutorizacionEnvioEmail() != null 
							&& ubicacion.getIdUbicacion() != null) {
							ubicacion = entityManager.merge(ubicacion);
							}
							} else {
								entityManager.persist(ubicacion);
							}
						}
						ue.setUbicacion(ubicacion);
						ue.setIdEmpresa(empresa.getIdEmpresa());

						if (ue.getIdUbicacionEmpresa() != null) {
							entityManager.merge(ue);
						} else {
							entityManager.persist(ue);
						}
						listIdsUbicaciones.add(ue.getIdUbicacionEmpresa());
						// marca la nueva ubicación para que no se persistan dos
						// del mismo tipo
						ubicacionesExistentes.put(ue.getTipoUbicacion(), ue);
					}
					// se verifica el cambio de la ubicación principal para
					// actualizar la persona empelador
					/* La ubicacion de la persona es distinta a la de la empresa y no se debe hacer el match*/
//					if (TipoUbicacionEnum.UBICACION_PRINCIPAL.equals(ue.getTipoUbicacion())) {
//						if (ubicacionesExistentes.get(ue.getTipoUbicacion()).getUbicacion() != null) {
//							empresa.getPersona().setUbicacionPrincipal(
//									ubicacionesExistentes.get(ue.getTipoUbicacion()).getUbicacion());
//							if (empresa.getPersona() != null && empresa.getPersona().getUbicacionPrincipal() != null) {
//								entityManager.merge(empresa.getPersona().getUbicacionPrincipal());
//							}
//						}
//					}
				}
			}
		} else {
			logger.debug(
					"Finaliza getionarUbicacionesEmpresa(Long, List<UbicacionEmpresa>, Boolean): No existe el empleador a actualizar");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
		// Retorna el listado de id de las ubicaciones creadas
		logger.debug("Finaliza getionarUbicacionesEmpresa(Long, List<UbicacionEmpresa>, Boolean)");
		return new ArrayList(listIdsUbicaciones);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.empresas.service.EmpresasService#actualizarEmpresa(com.
	 * asopagos.entidades.ccf.personas.Empresa)
	 */
	@Override
	public void actualizarEmpresa(Empresa empresa) {
		try {
			logger.debug("Inicio de método actualizarEmpresa(Empresa empresa)");
			if(empresa.getArl()!=null && empresa.getArl().getIdARL()==null){
				empresa.setArl(null);
			}
			entityManager.merge(empresa);
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.empresas.service.EmpresasService#actualizarUbicacion(com.
	 * asopagos.entidades.ccf.core.Ubicacion)
	 */
	@Override
	public void actualizarUbicacion(Ubicacion ubicacion) {
		try {
			logger.debug("Inicio de método actualizarUbicacion(Ubicacion ubicacion)");
			entityManager.merge(ubicacion);
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.empresas.service.EmpresasService#consultarSucursal(java.lang
	 * .Long)
	 */
	@Override
	public SucursalEmpresaModeloDTO consultarSucursal(Long idSucursal) {
		try {
			// Consulta la SucursalEmpresa por el Id.
			SucursalEmpresa sucursalEmpresa = entityManager.find(SucursalEmpresa.class, idSucursal);
			SucursalEmpresaModeloDTO sucursalEmpresaDTO = new SucursalEmpresaModeloDTO();
			sucursalEmpresaDTO.convertToDTO(sucursalEmpresa);
			return sucursalEmpresaDTO;
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.empresas.service.EmpresasService#
	 * obtenerCodigoDisponibleSucursal(java.lang.Long)
	 */
	@Override
	public String obtenerCodigoDisponibleSucursal(Long idEmpresa) {
		String codigoSucursalDisponible = "";
		try {
			/*
			 * Consulta los codigos de las sucursales ordenados ascendentemente.
			 */
			List<Integer> codigosSucursal = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_CODIGOS_SUCURSALES_EMPRESA)
					.setParameter("idEmpresa", idEmpresa).getResultList();
			int consecutivo = 1;
			int numeroConsecutivos = codigosSucursal.size();
			if (codigosSucursal != null && !codigosSucursal.isEmpty()) {
				for (Integer codigo : codigosSucursal) {
					/* Si no existe código con ese consecutivo se retorna. */
					if (codigo != consecutivo) {
						codigoSucursalDisponible = consecutivo + "";
						break;
					} else if (numeroConsecutivos == consecutivo) {
						/*
						 * Si ya alcanzó el máximo de consecutivos, se crea uno
						 * nuevo.
						 */
						consecutivo++;
						codigoSucursalDisponible = consecutivo + "";
						break;
					}
					consecutivo++;
				}
			}
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		return codigoSucursalDisponible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.empresas.service.EmpresasService#crearEmpresa(com.asopagos.
	 * dto.modelo.EmpresaModeloDTO)
	 */
	@Override
	public Long crearEmpresa(EmpresaModeloDTO empresaModeloDTO) {
		CrearEmpresaRutine ce = new CrearEmpresaRutine();
		return ce.crearEmpresa(empresaModeloDTO, entityManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.empresas.service.EmpresasService#procesarDatosSucursalPila(
	 * com.asopagos.dto.modelo.SucursalEmpresaModeloDTO)
	 */
	@Override
	public SucursalEmpresa procesarDatosSucursalPila(DatosRegistroSucursalPilaDTO datosRegistroSucursal) {
		logger.debug("Inicia procesarDatosSucursalPila(DatosRegistroSucursalPilaDTO)");
		SucursalEmpresa sucursal = null;
		final String paramIdPersonaCotizante = "idPersonaCotizante";
		final String paramCodigoSucursal = "codigoSucursal";
		final String paramIdEmpresa = "idEmpresa";

		Long idEmpresaSucursal = datosRegistroSucursal.getSucursalEmpresaModeloDTO().getIdEmpresa();
		try {
			if (idEmpresaSucursal != null) {
				sucursal = (SucursalEmpresa) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSAL)
						.setParameter(paramIdPersonaCotizante, datosRegistroSucursal.getIdPersonaCotizante())
						.setParameter(paramCodigoSucursal,
								datosRegistroSucursal.getSucursalEmpresaModeloDTO().getCodigo())
						.setParameter(paramIdEmpresa, idEmpresaSucursal).getSingleResult();
			} else {
				sucursal = (SucursalEmpresa) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSAL)
						.setParameter(paramIdPersonaCotizante, datosRegistroSucursal.getIdPersonaCotizante())
						.setParameter(paramCodigoSucursal,
								datosRegistroSucursal.getSucursalEmpresaModeloDTO().getCodigo())
						.getSingleResult();
			}
			logger.debug("Finaliza procesarDatosSucursalPila(DatosRegistroSucursalPilaDTO)");
			return sucursal;

		} catch (NoResultException nre) {
			logger.debug("Finaliza procesarDatosSucursalPila(DatosRegistroSucursalPilaDTO)");
			return obtenerSucursal(datosRegistroSucursal.getSucursalEmpresaModeloDTO().getCodigo());
		}
	}

	private SucursalEmpresa obtenerSucursal(String codigo) {
		try {
			SucursalEmpresa sucursal = (SucursalEmpresa) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSAL_POR_CODIGO)
					.setParameter("codigoSucursal", codigo).getSingleResult();

			return sucursal;

		} catch (Exception e) {
			return null;
		}
	}

	/** (non-Javadoc)
	 * @see com.asopagos.empresas.service.EmpresasService#crearActualizarOferente(com.asopagos.dto.modelo.OferenteModeloDTO)
	 */
	@Override
	public OferenteModeloDTO crearActualizarOferente(OferenteModeloDTO oferenteDTO) {
		logger.debug("Inicia el servicio crearActualizarOferente");
		if(oferenteDTO.getEmpresa() == null && oferenteDTO.getPersona().getTipoIdentificacion().equals(TipoIdentificacionEnum.NIT)){
			Empresa empresa = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_PERSONA, Empresa.class)
					.setParameter("numeroIdentificacion", oferenteDTO.getPersona().getNumeroIdentificacion())
					.setParameter("tipoIdentificacion", oferenteDTO.getPersona().getTipoIdentificacion()).getSingleResult();
			EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
			empresaDTO.convertToDTO(empresa);
			oferenteDTO.setEmpresa(empresaDTO);
		}
		Oferente oferente = oferenteDTO.convertToEntity();
		Oferente managed = entityManager.merge(oferente);
		oferenteDTO.setIdOferente(managed.getIdOferente());
		logger.debug("Finaliza el servicio crearActualizarOferente");
		return oferenteDTO;
	}
        
	/** (non-Javadoc)
	 * @see com.asopagos.empresas.service.EmpresasService#crearActualizarProveedor(com.asopagos.dto.modelo.ProveedorModeloDTO)
	 */
	@Override
	public ProveedorModeloDTO crearActualizarProveedor(ProveedorModeloDTO proveedorDTO) {
		logger.debug("Inicia el servicio crearActualizarProveedor");
		Proveedor proveedor = proveedorDTO.convertToEntity();
		Proveedor managed = entityManager.merge(proveedor);
		proveedorDTO.setIdOferente(managed.getIdOferente());
		logger.debug("Finaliza el servicio crearActualizarProveedor");
		return proveedorDTO;
	}
        
	/** (non-Javadoc)
	 * @see com.asopagos.empresas.service.EmpresasService#consultarOferente(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	public OferenteModeloDTO consultarOferente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug(interpolate("Inicia consultarOferente({0}, {1})", tipoIdentificacion, numeroIdentificacion));
		OferenteModeloDTO oferenteDTO = new OferenteModeloDTO();
		boolean existeOferente = true;
		try {
			/* Consulta si existe como Oferente */
			oferenteDTO = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_OFERENTE_POR_IDENTIFICACION,
							OferenteModeloDTO.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getSingleResult();

			if(oferenteDTO.getEmpresa() == null && oferenteDTO.getPersona().getTipoIdentificacion().equals(TipoIdentificacionEnum.NIT)){
				Empresa empresa = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_PERSONA, Empresa.class)
						.setParameter("numeroIdentificacion", oferenteDTO.getPersona().getNumeroIdentificacion())
						.setParameter("tipoIdentificacion", oferenteDTO.getPersona().getTipoIdentificacion()).getSingleResult();
				EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
				empresaDTO.convertToDTO(empresa);
				oferenteDTO.setEmpresa(empresaDTO);
			}
			/* Si existe como empresa */
			if (oferenteDTO.getEmpresa() != null && oferenteDTO.getEmpresa().getIdEmpresa() != null) {
				Empresa empresaAsociada = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID, Empresa.class)
						.setParameter("idEmpresa", oferenteDTO.getEmpresa().getIdEmpresa()).getSingleResult();
				EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
				empresaDTO.convertToDTO(empresaAsociada);
				oferenteDTO.setEmpresa(empresaDTO);
				/* Se consulta la ubicación del representante legal */
				UbicacionModeloDTO ubicacionRepresentante = null;
				if (empresaAsociada.getIdUbicacionRepresentanteLegal() != null) {
					Ubicacion ubicacion = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_ID, Ubicacion.class)
							.setParameter("idUbicacionEmpresa", empresaAsociada.getIdUbicacionRepresentanteLegal())
							.getSingleResult();
					ubicacionRepresentante = new UbicacionModeloDTO();
					ubicacionRepresentante.convertToDTO(ubicacion);
				}
				if (empresaAsociada.getIdPersonaRepresentanteLegal() != null) {
					/* Se consulta la debugrmación del Representante Legal */
					Persona representanteLegal = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_ID, Persona.class)
							.setParameter("idPersona", empresaAsociada.getIdPersonaRepresentanteLegal())
							.getSingleResult();
					PersonaModeloDTO representanteDTO = new PersonaModeloDTO(representanteLegal, null);
					representanteDTO.setUbicacionModeloDTO(ubicacionRepresentante);
					oferenteDTO.setRepresentanteLegal(representanteDTO);

				}
			}
		} catch (NoResultException e) {
			existeOferente = false;
			logger.debug("El Oferente no existe como Oferente.");
		}
		if (!existeOferente) {
			Empresa empresa = null;
			try {
				/* Consulta la empresa asociada al Oferente. */
				empresa = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_PERSONA, Empresa.class)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();
				EmpresaModeloDTO empresaModeloDTO = new EmpresaModeloDTO();
				empresaModeloDTO.convertToDTO(empresa);
				oferenteDTO.setEmpresa(empresaModeloDTO);
			} catch (NoResultException e) {
				logger.debug("El Oferente no existe como Empresa.");
			}
			if (empresa == null) {
				try {
					/*
					 * Si no existe como Empresa, se consultan los datos de
					 * la Persona.
					 */
					Persona persona = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA, Persona.class)
							.setParameter("numeroIdentificacion", numeroIdentificacion)
							.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();
					PersonaModeloDTO personaDTO = new PersonaModeloDTO();
					personaDTO.convertToDTO(persona, null);
					oferenteDTO.setPersona(personaDTO);
				} catch (NoResultException e) {
					logger.debug("El Oferente no existe como Persona.");
				}
			}
		}

		logger.debug(interpolate("Finaliza consultarOferente({0}, {1})", tipoIdentificacion, numeroIdentificacion));
		return oferenteDTO;
	}
        
        /** (non-Javadoc)
	 * @see com.asopagos.empresas.service.EmpresasService#consultarProveedor(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	public ProveedorModeloDTO consultarProveedor(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		logger.debug(interpolate("Inicia consultarProveedor({0}, {1})", tipoIdentificacion, numeroIdentificacion));
                List<ProveedorModeloDTO> listProveedores = new ArrayList<>();             
		ProveedorModeloDTO proveedorDTO = new ProveedorModeloDTO();
		boolean existeProveedor = true;              
            
		try {

                    /* Consulta si existe como Proveedor */
			listProveedores = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PROVEEDOR_POR_IDENTIFICACION,
							ProveedorModeloDTO.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
                                           
                        
			if (listProveedores != null && !listProveedores.isEmpty() && listProveedores.size() > 0 ) {
				proveedorDTO = listProveedores.get(0);
                                proveedorDTO.setTipoTable("PROVEEDOR");
                                if(proveedorDTO.getBanco() != null && proveedorDTO.getBanco().getId() != null){                                    
                                    String nombreBanco = "";
                                    try {
                                        nombreBanco = (String)entityManager.
                                            createNativeQuery("select banNombre from banco where banid = "+proveedorDTO.getBanco().getId())
                                        .getSingleResult();
                                        proveedorDTO.getBanco().setNombre(nombreBanco);                                        
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
			}else{
                            existeProveedor = false;
                            logger.debug("El proveedor no existe como Proveedor.");
                        }
			/* Si existe como empresa */
			if (proveedorDTO.getEmpresa() != null && proveedorDTO.getEmpresa().getIdEmpresa() != null) {
				Empresa empresaAsociada = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID, Empresa.class)
						.setParameter("idEmpresa", proveedorDTO.getEmpresa().getIdEmpresa()).getSingleResult();
				EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
				empresaDTO.convertToDTO(empresaAsociada);
				proveedorDTO.setEmpresa(empresaDTO);
				/* Se consulta la ubicación del representante legal */
				UbicacionModeloDTO ubicacionRepresentante = null;
				if (empresaAsociada.getIdUbicacionRepresentanteLegal() != null) {
					Ubicacion ubicacion = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_ID, Ubicacion.class)
							.setParameter("idUbicacionEmpresa", empresaAsociada.getIdUbicacionRepresentanteLegal())
							.getSingleResult();
					ubicacionRepresentante = new UbicacionModeloDTO();
					ubicacionRepresentante.convertToDTO(ubicacion);
				}
				if (empresaAsociada.getIdPersonaRepresentanteLegal() != null) {
					/* Se consulta la debugrmación del Representante Legal */
					Persona representanteLegal = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_ID, Persona.class)
							.setParameter("idPersona", empresaAsociada.getIdPersonaRepresentanteLegal())
							.getSingleResult();
					PersonaModeloDTO representanteDTO = new PersonaModeloDTO(representanteLegal, null);
					representanteDTO.setUbicacionModeloDTO(ubicacionRepresentante);
					proveedorDTO.setRepresentanteLegal(representanteDTO);

				}
			}
		} catch (NoResultException e) {
			existeProveedor = false;
			logger.debug("El Oferente no existe como Oferente.");
		}
		if (!existeProveedor) {
			Empresa empresa = null;
			try {
				/* Consulta la empresa asociada al Oferente. */
				empresa = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_PERSONA, Empresa.class)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();
				EmpresaModeloDTO empresaModeloDTO = new EmpresaModeloDTO();
				empresaModeloDTO.convertToDTO(empresa);
				proveedorDTO.setEmpresa(empresaModeloDTO);
			} catch (NoResultException e) {
				logger.debug("El Oferente no existe como Empresa.");
			}
			if (empresa == null) {
				try {
					/*
					 * Si no existe como Empresa, se consultan los datos de
					 * la Persona.
					 */
					Persona persona = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA, Persona.class)
							.setParameter("numeroIdentificacion", numeroIdentificacion)
							.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();
					PersonaModeloDTO personaDTO = new PersonaModeloDTO();
					personaDTO.convertToDTO(persona, null);
					proveedorDTO.setPersona(personaDTO);
				} catch (NoResultException e) {
					logger.debug("El Oferente no existe como Persona.");
				}
			}
		}

		logger.debug(interpolate("Finaliza consultarOferente({0}, {1})", tipoIdentificacion, numeroIdentificacion));
		return proveedorDTO;
	}

        
        /** (non-Javadoc)
	 * @see com.asopagos.empresas.service.EmpresasService#consultarLegalizacionDesembolsoProveedor(java.lang.String,java.lang.String,java.lang.String)
	 */
	/* 
	<!-- 49270BORRADOKT comentado ya que el glpi 49270 contempla la tabla LegalizacionDesembolosoProveedor pero esta nunca se inserta y no se utiliza
	@Override
	public List<LegalizacionDesembolosoProveedorModeloDTO> consultarLegalizacionDesembolsoProveedor(String idlegalizacionDesembolosoProveedor, String numeroRadicacion, String sldId) {
		logger.debug(interpolate("Inicia consultarLegalizacionDesembolsoProveedor({0})",  idlegalizacionDesembolosoProveedor,  numeroRadicacion,  sldId));
		List<LegalizacionDesembolosoProveedorModeloDTO> listLegalizacionDesembolosoProveedorModeloDTO = new ArrayList<>();
		boolean existe = true;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				if(numeroRadicacion == null){
					numeroRadicacion = "0";
				}	
                
             List<Object[]> resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LEGALIZACION_DESEMBOLSO_PROVEEDOR)
                .setParameter("idlegalizacionDesembolosoProveedor", idlegalizacionDesembolosoProveedor)
                .setParameter("numeroRadicacion", numeroRadicacion)
                .setParameter("sldId", sldId)
                        .getResultList();
	  
             for (Object[] obj : resultado) {
                    LegalizacionDesembolosoProveedorModeloDTO objLocal = new  LegalizacionDesembolosoProveedorModeloDTO();
                    PersonaModeloDTO persona = new PersonaModeloDTO();
                    ProveedorModeloDTO proveedor = new ProveedorModeloDTO();
                    BancoModeloDTO banco = new BancoModeloDTO();
                    
                    BigDecimal ValorADesembolsar  = new BigDecimal(String.valueOf(obj[5] == null ? "0": obj[5].toString()));                    
					Date fecha = null;
                     
                    objLocal.setIdlegalizacionDesembolosoProveedor(Long.valueOf(String.valueOf(obj[0] == null ? "": obj[0].toString())));
                    objLocal.setIdProveedor(Long.valueOf(String.valueOf(obj[1] == null ? "0": obj[1].toString())));
                    objLocal.setIdPersona(Long.valueOf(String.valueOf(obj[2] == null ? "0": obj[2].toString())));                    
                    objLocal.setSldId(Long.valueOf(String.valueOf(obj[3] == null ? "0": obj[3].toString())));                  
                    objLocal.setNumeroRadicacion(String.valueOf(String.valueOf(obj[4] == null ? null: obj[4].toString())));
                    objLocal.setValorADesembolsar(ValorADesembolsar);
                    objLocal.setPorcentajeASembolsar(Double.valueOf(String.valueOf(obj[6] == null ? "0": obj[6].toString())));
                    String fechaString = obj[7] != null ? obj[7].toString():null;

					if(fechaString != null){
						try {
							objLocal.setFecha(format.parse(fechaString));
						} catch (ParseException e) {
							e.printStackTrace();
						}						
					}
                    
                    persona.setNumeroIdentificacion(String.valueOf(obj[8] == null ? "0": obj[8].toString()));
                    persona.setRazonSocial(String.valueOf(obj[9] == null ? "": obj[9].toString()));
                    if(obj[10] != null){
                        persona.setTipoIdentificacion(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(obj[10].toString()));
                    }
                    persona.setPrimerNombre(String.valueOf(obj[11] == null ? "": obj[11].toString()));
                    persona.setSegundoNombre(String.valueOf(obj[12] == null ? "": obj[12].toString()));
                    persona.setPrimerApellido(String.valueOf(obj[13] == null ? "": obj[13].toString()));
                    persona.setSegundoApellido(String.valueOf(obj[14] == null ? "": obj[14].toString()));
                    
                    String estadoOferente = String.valueOf(obj[15] == null ? "": obj[15].toString());
					
                    if(estadoOferente != null){
                        if(estadoOferente.equalsIgnoreCase(EstadoOferenteEnum.ACTIVO.toString())){
                            proveedor.setEstado(EstadoOferenteEnum.ACTIVO);
                        }else{
                            proveedor.setEstado(EstadoOferenteEnum.INACTIVO);
                        }                        
                    }
                    
                    proveedor.setCuentaBancaria(obj[16] == null ? null: (Boolean)obj[16]);
                                      
                    banco.setCodigoPILA(String.valueOf(obj[17] == null ? "": obj[17].toString()));
                    banco.setNombre(String.valueOf(obj[18] == null ? "": obj[18].toString()));
                    banco.setMedioPago(obj[19] == null ? null: (Boolean)obj[19]);
                    banco.setCodigo(String.valueOf(obj[20] == null ? "": obj[20].toString()));
                    
                    if(obj[21] != null){
                        proveedor.setTipoCuenta(TipoCuentaEnum.obtenerTipoCuentaEnum(obj[21].toString()));
                    }
                    
                    proveedor.setNumeroCuenta(String.valueOf(obj[22] == null ? "": obj[22].toString()));
                    if(obj[23] != null){
                        proveedor.setTipoIdentificacionTitular(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(obj[23].toString()));
                    }
                    proveedor.setNumeroIdentificacionTitular(String.valueOf(obj[24] == null ? "": obj[24].toString()));
                    proveedor.setDigitoVerificacionTitular(obj[25] == null ? null: (Short)obj[25]);
                    proveedor.setNombreTitularCuenta(String.valueOf(obj[26] == null ? "": obj[26].toString()));
                    proveedor.setConcepto(obj[27] == null ? null: (Integer)obj[27]);
                    
                    proveedor.setBanco(banco);
                    proveedor.setPersona(persona);
                    objLocal.setProveedor(proveedor);
                    
                    listLegalizacionDesembolosoProveedorModeloDTO.add(objLocal);
                }
                                                                      
//		} catch (NoResultException e) {
//			existe = false;
//			logger.debug("El Oferente no existe como Oferente.");
//		}
		

		logger.debug(interpolate("Finaliza consultarLegalizacionDesembolsoProveedor({0})",  idlegalizacionDesembolosoProveedor,  numeroRadicacion,  sldId));
		return listLegalizacionDesembolosoProveedorModeloDTO;
	}
*/
        
        /** (non-Javadoc)
	 * @see com.asopagos.empresas.service.EmpresasService#crearActualizarProveedor(com.asopagos.dto.modelo.ProveedorModeloDTO)
	 */
	@Override
	public LegalizacionDesembolosoProveedorModeloDTO crearActualizarLegalizacionDesembolsoProveedor(LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedorDTO) {
                logger.debug("Inicia el servicio crearActualizarLegalizacionDesembolsoProveedor");
		LegalizacionDesembolosoProveedor leg = legalizacionDesembolosoProveedorDTO.convertToEntity();
		LegalizacionDesembolosoProveedor managed = entityManager.merge(leg);
		legalizacionDesembolosoProveedorDTO.setIdlegalizacionDesembolosoProveedor(managed.getIdlegalizacionDesembolosoProveedor());
		logger.debug("Finaliza el servicio crearActualizarLegalizacionDesembolsoProveedor");
		return legalizacionDesembolosoProveedorDTO;
	}
        
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.empresas.service.EmpresasService#obtenerSucursalEmpresa(java
	 * .lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@Override
	public SucursalEmpresaModeloDTO obtenerSucursalEmpresa(String codigoSucursal,
			TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante) {
		try {
			logger.debug("Inicia el servicio obtenerSucursalEmpresa");
			SucursalEmpresa sucursal = (SucursalEmpresa) entityManager
					.createNamedQuery(
							NamedQueriesConstants.CONSULTAR_SUCURSAL_POR_CODIGO_Y_DATOS_DE_IDENTIFICACION_EMPRESA)
					.setParameter("codigoSucursal", codigoSucursal).setParameter("tipoIdAportante", tipoIdAportante)
					.setParameter("numeroIdAportante", numeroIdAportante).getSingleResult();

			SucursalEmpresaModeloDTO sucursalEmpresaModeloDTO = new SucursalEmpresaModeloDTO();
			sucursalEmpresaModeloDTO.convertToDTO(sucursal);

			logger.debug("Finaliza el servicio obtenerSucursalEmpresa");
			return sucursalEmpresaModeloDTO;

		} catch (Exception e) {
			logger.debug("Finaliza el servicio obtenerSucursalEmpresa");
			return null;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.empresas.service.EmpresasService#consultarEmpresa(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<EmpresaModeloDTO> consultarEmpresa(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String razonSocial) {
		logger.debug(interpolate("Inicia consultarEmpresa({0}, {1})", tipoIdentificacion, numeroIdentificacion));

		if (razonSocial == null && (tipoIdentificacion == null || numeroIdentificacion == null)) {
			logger.debug("Finaliza el servicio consultarEmpresa");
			throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}

		List<EmpresaModeloDTO> empresasDTO = new ArrayList<>();

		EmpresaModeloDTO empresaTipoNumeroDTO = null;
		if (tipoIdentificacion != null && numeroIdentificacion != null) {
			try {
				Empresa emp = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_PERSONA, Empresa.class)
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.setParameter("tipoIdentificacion", tipoIdentificacion).getSingleResult();

				empresaTipoNumeroDTO = new EmpresaModeloDTO();
				empresaTipoNumeroDTO.convertToDTO(emp);

				List<UbicacionEmpresa> ubi = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_PRINCIPAL_POR_EMPRESA, UbicacionEmpresa.class)
						.setParameter("idEmpresa", emp.getIdEmpresa()).getResultList();

				if (ubi != null && !ubi.isEmpty()) {
					UbicacionModeloDTO ubiModelo = new UbicacionModeloDTO();
					ubiModelo.convertToDTO(ubi.get(0).getUbicacion());
					empresaTipoNumeroDTO.setUbicacionModeloDTO(ubiModelo);
				}
				empresasDTO.add(empresaTipoNumeroDTO);

			} catch (NoResultException e) {
				logger.debug("Sin resultados para los criterios de busqueda");
			} catch (NonUniqueResultException e) {
				logger.debug("Finaliza el servicio consultarEmpresa");
				throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO, e);
			}
			
		}

		if (razonSocial != null) {
			final String PORCENTAJE = "%";
			List<Empresa> empresas = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_RAZONSOCIAL, Empresa.class)
					.setParameter("razonSocial", PORCENTAJE + razonSocial + PORCENTAJE).getResultList();

			// validar que las empresas obtenidas no se encuentren ya en el
			// arreglo
			for (int i = 0; i < empresas.size(); i++) {
				EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
				empresaDTO.convertToDTO(empresas.get(i));
				if (empresaTipoNumeroDTO != null) {
					if (empresaTipoNumeroDTO.getIdEmpresa() != empresaDTO.getIdEmpresa()) {
						empresasDTO.add(empresaDTO);
					}
				} else {
					empresasDTO.add(empresaDTO);
				}
			}
		}

		if (empresasDTO.isEmpty()) {
			logger.debug("Finaliza el servicio consultarEmpresa");
			return null;
		} else {
			logger.debug("Finaliza el servicio consultarEmpresa");
			return empresasDTO;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.empresas.service.EmpresasService#
	 * consultarOferentePorRazonSocial(java.lang.String)
	 */
	@Override
	public List<OferenteModeloDTO> consultarOferentePorRazonSocial(String razonSocialNombre) {
		logger.debug(interpolate("Inicia consultarOferentePorRazonSocial({0})", razonSocialNombre));
		List<OferenteModeloDTO> listOferentes = new ArrayList<>();
		boolean existeOferente = true;
		final String PORCENTAJE = "%";
		try {
			/* Consulta si existe como Oferente */
			listOferentes = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_OFERENTE_POR_RAZON_SOCIAL,
							OferenteModeloDTO.class)
					.setParameter("razonSocial", PORCENTAJE+razonSocialNombre+PORCENTAJE).getResultList();
                        
                                                
                        
			if (listOferentes == null || listOferentes.isEmpty()) {
				existeOferente = false;
			}
			/* Si existe como empresa 
			if (oferenteDTO.getEmpresa() != null && oferenteDTO.getEmpresa().getIdEmpresa() != null) {
				Empresa empresaAsociada = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID, Empresa.class)
						.setParameter("idEmpresa", oferenteDTO.getEmpresa().getIdEmpresa()).getSingleResult();
				EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
				empresaDTO.convertToDTO(empresaAsociada);
				oferenteDTO.setEmpresa(empresaDTO);
			}*/
		} catch (NoResultException e) {
			existeOferente = false;
			logger.debug("El Oferente no existe como Oferente.");
		}
		if (!existeOferente) {
			/* Consulta la empresa asociada al Oferente. */
			List<Empresa> listEmpresa = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_RAZONSOCIAL, Empresa.class)
						.setParameter("razonSocial", PORCENTAJE+razonSocialNombre+PORCENTAJE).getResultList();
			for (Empresa empresa : listEmpresa) {
				listOferentes.add(new OferenteModeloDTO(null, empresa.getPersona(), empresa));
			}
			if (listEmpresa == null || listEmpresa.isEmpty()) {
				/*
				 * Si no existe como Empresa, se consultan los datos de
				 * la Persona.
				 */
				List<Persona> listPersona = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_RAZON_SOCIAL,
								Persona.class)
						.setParameter("razonSocial", razonSocialNombre).getResultList();
				for (Persona persona : listPersona) {
					if(persona.getTipoIdentificacion().equals(TipoIdentificacionEnum.NIT)) {
						Empresa empresa = entityManager
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_PERSONA, Empresa.class)
								.setParameter("numeroIdentificacion", persona.getNumeroIdentificacion())
								.setParameter("tipoIdentificacion", persona.getTipoIdentificacion()).getSingleResult();
						listOferentes.add(new OferenteModeloDTO(null, persona, empresa));
					}else{
						listOferentes.add(new OferenteModeloDTO(null, persona));
					}

				}
			}
		}

		logger.debug(interpolate("Inicia consultarOferentePorRazonSocial({0})", razonSocialNombre));
		return listOferentes;
	}

        
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.empresas.service.EmpresasService#
	 * consultarProveedorPorRazonSocial(java.lang.String)
	 */
	@Override
	public List<ProveedorModeloDTO> consultarProveedorPorRazonSocial(String razonSocialNombre) {
		logger.debug(interpolate("Inicia consultarProveedorPorRazonSocial({0})", razonSocialNombre));
                List<ProveedorModeloDTO> listProveedores= new ArrayList<>();
		boolean existeProveedor = true;
		final String PORCENTAJE = "%";
		try {
              
                        /* Consulta si existe como Proveedor */
			listProveedores = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PROVEEDOR_POR_RAZON_SOCIAL, 
                                                ProveedorModeloDTO.class)
					.setParameter("razonSocial", PORCENTAJE+razonSocialNombre+PORCENTAJE).getResultList();
                        
			if (listProveedores == null || listProveedores.isEmpty()) {
				existeProveedor = false;
			}else{
                            for (ProveedorModeloDTO proveedor : listProveedores) {
				proveedor.setTipoTable("PROVEEDOR");
                            }
                        }
			/* Si existe como empresa 
			if (oferenteDTO.getEmpresa() != null && oferenteDTO.getEmpresa().getIdEmpresa() != null) {
				Empresa empresaAsociada = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID, Empresa.class)
						.setParameter("idEmpresa", oferenteDTO.getEmpresa().getIdEmpresa()).getSingleResult();
				EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
				empresaDTO.convertToDTO(empresaAsociada);
				oferenteDTO.setEmpresa(empresaDTO);
			}*/
		} catch (NoResultException e) {
			existeProveedor = false;
			logger.debug("El Proveedor no existe como Proveedor.");
		}
		if (!existeProveedor) {
			/* Consulta la empresa asociada al Oferente. */
			List<Empresa> listEmpresa = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_RAZONSOCIAL, Empresa.class)
						.setParameter("razonSocial", PORCENTAJE+razonSocialNombre+PORCENTAJE).getResultList();
			
			for (Empresa empresa : listEmpresa) {
				listProveedores.add(new ProveedorModeloDTO(null, empresa.getPersona(), empresa));
			}
			if (listEmpresa == null || listEmpresa.isEmpty()) {
				/*
				 * Si no existe como Empresa, se consultan los datos de
				 * la Persona.
				 */
				List<Persona> listPersona = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_RAZON_SOCIAL,
								Persona.class)
						.setParameter("razonSocial", razonSocialNombre).getResultList();
				for (Persona persona : listPersona) {
					listProveedores.add(new ProveedorModeloDTO(null, persona));
				}
			}
		}

		logger.debug(interpolate("Inicia consultarProveedorPorRazonSocial({0})", razonSocialNombre));
		return listProveedores;
	}
        
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.empresas.service.EmpresasService#crearSucursalEmpresaPila(java.lang.Long,
	 *      com.asopagos.dto.modelo.SucursalEmpresaModeloDTO)
	 */
	@Override
	public void crearSucursalEmpresaPila(Long idEmpresa, SucursalEmpresaModeloDTO sucursalDTO) {
		String firmaServicio = "EmpresasBusiness.crearSucursalEmpresaPila(Long, SucursalEmpresaModeloDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		SucursalEmpresa sucursal = sucursalDTO.convertToEntity();
		sucursal.setIdEmpresa(idEmpresa);

		try {
			if (sucursal.getIdSucursalEmpresa() == null) {
				entityManager.persist(sucursal);
			} else {
				entityManager.merge(sucursal);
			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio
					+ MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public EmpresaModeloDTO consultarEmpresaPorId(Long idEmpresa) {
        String firmaServicio = "EmpresasBusiness.consultarEmpresaPorId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        EmpresaModeloDTO result = null;

        try {
            Empresa empresa = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID, Empresa.class)
                    .setParameter("idEmpresa", idEmpresa).getSingleResult();
            
            result = new EmpresaModeloDTO(empresa);
        } catch (NoResultException e) {
            logger.debug(firmaServicio + " :: No se encuentra una empresa con el ID " + idEmpresa);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.empresas.service.EmpresasService#consultarRepresentantesLegalesEmpresa(Long, Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Persona consultarRepresentantesLegalesEmpresa(Long idEmpresa, Boolean titular) {
        String firmaServicio = "EmpresasBusiness.consultarRepresentantesLegalesEmpresa(Long, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        // se valida que si envíe un valor válido del criterio de búsqueda
        if (idEmpresa == null || titular == null) {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        String consulta = null;
        if (titular) {
            consulta = NamedQueriesConstants.CONSULTAR_REPRESENTANTE_LEGAL_PRIN_BY_ID_EMPRESA;
        }
        else {
            consulta = NamedQueriesConstants.CONSULTAR_REPRESENTANTE_LEGAL_SUPL_BY_ID_EMPRESA;
        }

        Persona persona = null;
        Ubicacion ubicacion = null;
        try {
            Object[] result = (Object[]) entityManager.createNamedQuery(consulta).setParameter("idEmpresa", idEmpresa).getSingleResult();
            persona = (Persona) result[0];
            ubicacion = (Ubicacion) result[1];
            entityManager.detach(persona);
            if (ubicacion != null) {
                entityManager.detach(ubicacion);
            }
            persona.setUbicacionPrincipal(ubicacion);
        } catch (NoResultException e) {
            return null;
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return persona;
    }

    /*
    * (non-Javadoc)
    * 
    * @see
    * com.asopagos.empresas.service.EmpresasService#obtenerSucursalEmpresaByCodigoAndEmpleador(java
    * .lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
    * java.lang.String)
    */
   @Override
   public SucursalEmpresaModeloDTO obtenerSucursalEmpresaByCodigoAndEmpleador(String codigoSucursal,
                   TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
        try {
                logger.debug("Inicia el servicio obtenerSucursalEmpresaByCodigoAndEmpleador");
                SucursalEmpresa sucursal = (SucursalEmpresa) entityManager
                                .createNamedQuery(
                                                NamedQueriesConstants.CONSULTAR_SUCURSAL_POR_CODIGO_Y_DATOS_DE_IDENTIFICACION_EMPRESA2)
                                .setParameter("codigoSucursal", codigoSucursal)
                                .setParameter("tipoIdEmpleador", tipoIdEmpleador)
                                .setParameter("numeroIdEmpleador", numeroIdEmpleador).getSingleResult();

                SucursalEmpresaModeloDTO sucursalEmpresaModeloDTO = new SucursalEmpresaModeloDTO();
                sucursalEmpresaModeloDTO.convertToDTO(sucursal);

                logger.debug("Finaliza el servicio obtenerSucursalEmpresaByCodigoAndEmpleador");
                return sucursalEmpresaModeloDTO;

        } catch (Exception e) {
                logger.debug("Finaliza el servicio obtenerSucursalEmpresaByCodigoAndEmpleador");
                return null;
        }
    }
   
   /*
    * (non-Javadoc)
    * 
    * @see
    * com.asopagos.empresas.service.EmpresasService#obtenerSucursalEmpresaByNombreAndEmpleador(java
    * .lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
    * java.lang.String)
    */
   @Override
   public SucursalEmpresaModeloDTO obtenerSucursalEmpresaByNombreAndEmpleador(String nombreSucursal,
																			  TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
	   try {
		   SucursalEmpresa sucursal = (SucursalEmpresa) entityManager
				   .createNamedQuery(
						   NamedQueriesConstants.CONSULTAR_SUCURSAL_POR_NOMBRE_Y_DATOS_DE_IDENTIFICACION_EMPRESA, SucursalEmpresa.class)
				   .setParameter("nombreSucursal", nombreSucursal)
				   .setParameter("tipoIdEmpleador", tipoIdEmpleador)
				   .setParameter("numeroIdEmpleador", numeroIdEmpleador).getResultList().get(0);

		   SucursalEmpresaModeloDTO sucursalEmpresaModeloDTO = new SucursalEmpresaModeloDTO();
		   sucursalEmpresaModeloDTO.convertToDTO(sucursal);

		   logger.debug("Finaliza el servicio obtenerSucursalEmpresaByNombreAndEmpleador");
		   return sucursalEmpresaModeloDTO;
	   } catch (Exception e) {
		   logger.info("catch " + e);
		   return null;
	   }
   }
}
