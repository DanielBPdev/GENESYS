package com.asopagos.listaschequeo.ejb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.converter.SujetoTramiteUtils;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.entidades.ccf.core.GrupoRequisito;
import com.asopagos.entidades.transversal.core.CajaCompensacion;
import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.entidades.transversal.core.RequisitoCajaClasificacion;
import com.asopagos.entidades.transversal.personas.ISujetoTramite;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.listaschequeo.constants.NamedQueriesConstants;
import com.asopagos.listaschequeo.dto.CajaCompensacionDTO;
import com.asopagos.listaschequeo.dto.ConsultarRequisitosListaChequeoOutDTO;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.listaschequeo.dto.RequisitoDTO;
import com.asopagos.listaschequeo.dto.RequisitosCajasCompensacionDTO;
import com.asopagos.listaschequeo.dto.RequisitosClasificacionesDTO;
import com.asopagos.listaschequeo.service.RequisitosService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.parametros.clients.ReplicarActualizacionListaChequeoClasificacion;
import com.asopagos.parametros.clients.ReplicarActualizacionListaChequeoClasificacionPorCaja;
import com.asopagos.parametros.clients.ReplicarCreacionListaChequeoClasificacion;
import com.asopagos.parametros.clients.ReplicarCreacionListaChequeoClasificacionPorCaja;
import com.asopagos.parametros.clients.ReplicarListaChequeo;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.validaciones.clients.ExisteRegistraduriaNacional;


/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados 
 * con la gestión de requisitos para la afiliación de empleadores
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class RequisitosBusiness implements RequisitosService {
    
	/**
     * Referencia a la unidad de persistencia
     */
	@PersistenceContext(unitName = "listaschequeo_PU")
	private EntityManager entityManager;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(RequisitosBusiness.class);

    @Override
    public List<Long> crearRequisitos(List<RequisitoDTO> requisitos) {
    	Requisito requisitoEntity;
    	List<Long> idRequisitos = new ArrayList<>();
    	for (RequisitoDTO requisitoDTO : requisitos) {
    		requisitoEntity = new Requisito();
    		requisitoEntity.setDescripcion(requisitoDTO.getNombreRequisito());
    		requisitoEntity.setEstado(requisitoDTO.getEstado());
    		if (requisitoDTO.getIdRequisito() != null
    				&& requisitoDTO.getIdRequisito() > NumerosEnterosConstants.CERO) {
    			requisitoEntity.setIdRequisito(requisitoDTO.getIdRequisito());
    			requisitoEntity = entityManager.merge(requisitoEntity);
			}else{
				entityManager.persist(requisitoEntity);
			}
    		idRequisitos.add(requisitoEntity.getIdRequisito());
		}
        return idRequisitos;
    }

    @Override
    public void actualizarEstadoRequisito(Short idRequisito, HabilitadoInhabilitadoEnum estado) {
    	Requisito requisitoUpdate = entityManager.getReference(Requisito.class, Long.valueOf(idRequisito));
    	requisitoUpdate.setEstado(estado);
		entityManager.merge(requisitoUpdate);
		ReplicarListaChequeo replicarListaChequeo = new ReplicarListaChequeo(requisitoUpdate);
		replicarListaChequeo.execute();
    }
    
    @Override
    public void actualizarEstadoRequisitoCaja(Requisito requisito, UserDTO userDTO) {
        Requisito requisitoActualizar = entityManager.createQuery("from Requisito where descripcion = :descripcion", Requisito.class)
                .setParameter("descripcion", requisito.getDescripcion())
                .getSingleResult();
        requisitoActualizar.setEstado(requisito.getEstado());
        entityManager.merge(requisitoActualizar);
    }

    /**
     * Método encargado de consultar los requisitos sobre la tabla Requisito
     * 
     * @param nombre
     * 			nombre del requisito
     * @return  List<RequisitoDTO>
     * 			lista con los requisitos consultados	
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RequisitoDTO> consultarRequisitos(String nombre) {
    	
    	if (nombre != null && !nombre.trim().equals("")) {
    		return entityManager.createNamedQuery(
                    NamedQueriesConstants.CONSULTAR_LISTA_REQUISITOS_NOMBRE, RequisitoDTO.class)
        			.setParameter("nombre", "%"+nombre+"%")
                    .getResultList();
		}else{
			return entityManager.createNamedQuery(
                    NamedQueriesConstants.CONSULTAR_LISTA_REQUISITOS,  RequisitoDTO.class)
                    .getResultList();
		}
    }

    /**
     * Método encargado de consultar los requisitos de las cajas
     * de compensación consultando sobre las tablas Requisito,
     * CajaCompensacion y RequisitoCajaClasificacion.
     * 
     * @param tipoTransaccion
     * 				tipo de transacción
     * @param clasificacion 
     * 				clasificación
     * @return  RequisitosCajasCompensacionDTO
     * 				objeto con la información consultada
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RequisitosCajasCompensacionDTO consultarRequisitosCajasCompensacion(
    				TipoTransaccionEnum tipoTransaccion, ClasificacionEnum clasificacion) {
    	RequisitosCajasCompensacionDTO requisitosCajasCompensacionDTO = new RequisitosCajasCompensacionDTO();
    	
    	List<RequisitoDTO> listaRequisitos = new ArrayList<>();
    	RequisitoDTO requisitoDTO = null;
    	
    	List<RequisitoCajaClasificacionDTO> listaRequisitosCajas  = entityManager.createNamedQuery(
                NamedQueriesConstants.CONSULTAR_REQUISITOS_CAJA_COMPENSACION, RequisitoCajaClasificacionDTO.class)
    			.setParameter("tipoTransaccion", tipoTransaccion)
    			.setParameter("clasificacion", clasificacion)
                .getResultList();
    	
    	if (listaRequisitosCajas != null && !listaRequisitosCajas.isEmpty()) {
    		Map<String, String> mapRequisitos = new HashMap<>();
			for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : listaRequisitosCajas) {
				// se consulta y configuran los requisitos
			    Requisito requisito = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_REQUISITOS_ID, Requisito.class)
			            .setParameter("idRequisito", requisitoCajaClasificacionDTO.getIdRequisito()).getSingleResult(); 
				requisitoDTO = new RequisitoDTO();
				requisitoDTO.setIdRequisito(requisito.getIdRequisito());
				requisitoDTO.setNombreRequisito(requisito.getDescripcion());
				requisitoDTO.setEstado(requisito.getEstado());
				if (!mapRequisitos.containsKey(requisitoDTO.getNombreRequisito())) {
					mapRequisitos.put(requisitoDTO.getNombreRequisito(), "");
					listaRequisitos.add(requisitoDTO);
				}
                List<GrupoRequisito> gruposRequisitosBD = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPO_USUARIO, GrupoRequisito.class)
                        .setParameter("idRequisitoCajaClasificacion", requisitoCajaClasificacionDTO.getIdRequisitoTipoSolicitanteCaja())
                        .getResultList();
                if (gruposRequisitosBD != null && !gruposRequisitosBD.isEmpty()) {
                    List<String> gruposUsuarios= new ArrayList<>();
                    for (GrupoRequisito grupo : gruposRequisitosBD) {
                        gruposUsuarios.add(grupo.getGrupoUsuario());
                    }
                    requisitoCajaClasificacionDTO.setGrupoUsuario(gruposUsuarios);
                }
			}
			requisitosCajasCompensacionDTO.setRequsitos(listaRequisitos);
    		requisitosCajasCompensacionDTO.setRequsitosCajas(listaRequisitosCajas);
		}
    	List<CajaCompensacionDTO> listaCajasConsult  = entityManager.createNamedQuery(
                NamedQueriesConstants.CONSULTAR_CAJAS_COMPENSACION, CajaCompensacionDTO.class)
                .getResultList();
		
		requisitosCajasCompensacionDTO.setCajasCompensacion(listaCajasConsult);
    	return requisitosCajasCompensacionDTO;
    }

    /**
     * Método encargado de consultar los requisitos de las cajas
     * de compensación consultando sobre las tablas Requisito,
     * CajaCompensacion y RequisitoCajaClasificacion.
     * 
     * @param tipoTransaccion
     * 				tipo de transacción
     * @param tipoSolicitante
     * 				Tipo de solicitante
     * @param clasificacion 
     * 				clasificación
     * @return  RequisitosCajasCompensacionDTO
     * 				objeto con la información consultada
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RequisitosClasificacionesDTO consultarRequisitosClasificaciones(
    		TipoTransaccionEnum tipoTransaccion, String tipoSolicitante, Integer idCajaCompensacion) {
    	
    	RequisitosClasificacionesDTO requisitosClasificacionDTO = new RequisitosClasificacionesDTO();
    	List<RequisitoDTO> listaRequisitos = new ArrayList<>();
    	RequisitoDTO requisitoDTO;
    	
        List<ClasificacionEnum> listaClasificaciones = new ArrayList<>();
        if (tipoTransaccion.getEsNovedad() && tipoTransaccion.isAplicaClasificacion() ) {
            listaClasificaciones.add(TiposSolicitanteBusiness.getTxClasificacion(tipoTransaccion));
        } else {
            ISujetoTramite sujetoTramite = SujetoTramiteUtils.toSujetoTramite(tipoSolicitante);
            for (ClasificacionEnum clasificacion : ClasificacionEnum.values()) {
                if (clasificacion.getSujetoTramite() == sujetoTramite) {
                    listaClasificaciones.add(clasificacion);
                }
            }
        }
        
    	if (!listaClasificaciones.isEmpty()) {
    		
    		List<ElementoListaDTO> clasificaciones = new ArrayList<>();
    		ElementoListaDTO elemento;
    		for (ClasificacionEnum clasificacionEnum : listaClasificaciones) {
				elemento = new ElementoListaDTO();
				elemento.setIdentificador(clasificacionEnum.name());
				elemento.setValor(clasificacionEnum.getDescripcion());
				clasificaciones.add(elemento);
			}
    		
            List<RequisitoCajaClasificacionDTO> listaRequisitosCajas = consultarRequisitosCajaClasificacion(tipoTransaccion,
                    listaClasificaciones, idCajaCompensacion);
        	
        	if (listaRequisitosCajas != null && !listaRequisitosCajas.isEmpty()) {
        		Map<String, String> mapRequisitos = new HashMap<>();
        		for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : listaRequisitosCajas) {
        			// se consulta y configuran los requisitos
        		    Requisito requisito = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_REQUISITOS_ID,Requisito.class)
                            .setParameter("idRequisito", requisitoCajaClasificacionDTO.getIdRequisito()).getSingleResult(); 
    				requisitoDTO = new RequisitoDTO();
    				requisitoDTO.setIdRequisito(requisito.getIdRequisito());
    				requisitoDTO.setNombreRequisito(requisito.getDescripcion());
    				requisitoDTO.setEstado(requisito.getEstado());
    				if (!mapRequisitos.containsKey(requisitoDTO.getNombreRequisito())) {
    					mapRequisitos.put(requisitoDTO.getNombreRequisito(), "");
    					listaRequisitos.add(requisitoDTO);
                    }
                    List<GrupoRequisito> gruposRequisitosBD = entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPO_USUARIO, GrupoRequisito.class)
                            .setParameter("idRequisitoCajaClasificacion", requisitoCajaClasificacionDTO.getIdRequisitoTipoSolicitanteCaja())
                            .getResultList();
                    if (gruposRequisitosBD != null && !gruposRequisitosBD.isEmpty()) {
                        List<String> gruposUsuarios = new ArrayList<>();
                        for (GrupoRequisito grupo : gruposRequisitosBD) {
                            gruposUsuarios.add(grupo.getGrupoUsuario());
                        }
                        requisitoCajaClasificacionDTO.setGrupoUsuario(gruposUsuarios);
                    }
    			}
        		requisitosClasificacionDTO.setRequsitos(listaRequisitos);
        		requisitosClasificacionDTO.setClasificaciones(clasificaciones);
        		requisitosClasificacionDTO.setRequsitosCajas(listaRequisitosCajas);
    		}else{
    			requisitosClasificacionDTO.setClasificaciones(clasificaciones);
    		}
		} else {
            return null;
        }
    	return requisitosClasificacionDTO;
    }

    private List<RequisitoCajaClasificacionDTO> consultarRequisitosCajaClasificacion(TipoTransaccionEnum tipoTransaccion,
            List<ClasificacionEnum> listaClasificaciones, Integer idCajaCompensacion) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_REQUISITOS_CAJA_CLASIFICACION, RequisitoCajaClasificacionDTO.class)
                .setParameter("tipoTransaccion", tipoTransaccion).setParameter("clasificaciones", listaClasificaciones)
                .setParameter("idCajaCompensacion", idCajaCompensacion).getResultList();
    }

    /**
     * Método encargado de registar los datos en la
     * tabla RequisitoCajaClasificacion
     * 
     * @param requisitosCajaClasificacion
     * 				objeto con los datos a registrar
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion, UserDTO userDTO) {
        RequisitoCajaClasificacion requisitoCajaClasificacion = null;
        
        System.out.println("los requisitos que llegaron de replicación son: ");
        requisitosCajaClasificacion.forEach(requisito -> System.out.println(requisito.toString()));
        
        for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : requisitosCajaClasificacion) {
            requisitoCajaClasificacion = convertInfoDTOTOEntity(requisitoCajaClasificacionDTO);
            entityManager.persist(requisitoCajaClasificacion);
            entityManager.flush();
            requisitoCajaClasificacionDTO.setIdRequisitoTipoSolicitanteCaja(requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja());
            crearActualizarGrupoUsuario(requisitoCajaClasificacionDTO);
        }
    }
    
    private void crearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion){
    	RequisitoCajaClasificacion requisitoCajaClasificacion = null;
        
        System.out.println("los requisitos que llegaron de replicación son: ");
        requisitosCajaClasificacion.forEach(requisito -> System.out.println(requisito.toString()));
        
        for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : requisitosCajaClasificacion) {
            requisitoCajaClasificacion = convertInfoDTOTOEntity(requisitoCajaClasificacionDTO);
            entityManager.persist(requisitoCajaClasificacion);
            entityManager.flush();
            requisitoCajaClasificacionDTO.setIdRequisitoTipoSolicitanteCaja(requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja());
            crearActualizarGrupoUsuario(requisitoCajaClasificacionDTO);
        }
    }

    private RequisitoCajaClasificacion convertInfoDTOTOEntity(RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO) {
        RequisitoCajaClasificacion requisitoCajaClasificacion = new RequisitoCajaClasificacion();
        // Consulta la caja de compensacion por id
        CajaCompensacion cajaCompensacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CAJA_COMPENSACION_ID, CajaCompensacion.class)
                .setParameter("idCajaCompensacion", requisitoCajaClasificacionDTO.getIdCajaCompensacion()).getSingleResult();
        requisitoCajaClasificacion.setCajaCompensacion(cajaCompensacion);
        requisitoCajaClasificacion.setClasificacion(requisitoCajaClasificacionDTO.getClasificacion());
        requisitoCajaClasificacion.setEstado(requisitoCajaClasificacionDTO.getEstado());
        
        Long idRequisitoTipoSolicitanteCaja = consultarId(requisitoCajaClasificacionDTO);
        requisitoCajaClasificacion.setIdRequisitoTipoSolicitanteCaja(idRequisitoTipoSolicitanteCaja);
        
        // Consula el requisito por id
        Requisito requisito = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_REQUISITOS_ID, Requisito.class)
                .setParameter("idRequisito", requisitoCajaClasificacionDTO.getIdRequisito()).getSingleResult();
        requisitoCajaClasificacion.setRequisito(requisito);
        if (requisitoCajaClasificacionDTO.getTextoAyuda() != null && !requisitoCajaClasificacionDTO.getTextoAyuda().trim().equals("")) {
            requisitoCajaClasificacion.setTextoAyuda(requisitoCajaClasificacionDTO.getTextoAyuda());
        }
        requisitoCajaClasificacion.setTipoTransaccion(requisitoCajaClasificacionDTO.getTipoTransaccion());
        requisitoCajaClasificacion.setTipoRequisito(requisitoCajaClasificacionDTO.getTipoRequisito());
        return requisitoCajaClasificacion;
    }

    /**
     * @param requisitoCajaClasificacionDTO
     * @return
     */
    private Long consultarId(RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO) {
    	try {
    		RequisitoCajaClasificacion requisitoCajaClasificacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_REQUISITO_CAJA_CLASIFICACION, RequisitoCajaClasificacion.class)
                    .setParameter("idCajaCompensacion", requisitoCajaClasificacionDTO.getIdCajaCompensacion())
                    .setParameter("tipoTransaccion", requisitoCajaClasificacionDTO.getTipoTransaccion())
                    .setParameter("clasificacion", requisitoCajaClasificacionDTO.getClasificacion())
                    .setParameter("requisito", requisitoCajaClasificacionDTO.getIdRequisito())
                    .getSingleResult();
            
            return requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja();
		} catch (Exception e) {
			return null;
		}
        
    }

    /**
     * Método encargado de actualizar los datos en la
     * tabla RequisitoCajaClasificacion
     * 
     * @param requisitosCajaClasificacion
     * 				objeto con los datos a registrar
     */
    @Override
    public void actualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion, UserDTO userDTO) {
        RequisitoCajaClasificacion requisitoCajaClasificacion = null;
        for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : requisitosCajaClasificacion) {
            requisitoCajaClasificacion = convertInfoDTOTOEntity(requisitoCajaClasificacionDTO);
            entityManager.merge(requisitoCajaClasificacion);
            entityManager.flush();
            requisitoCajaClasificacionDTO.setIdRequisitoTipoSolicitanteCaja(requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja());
            crearActualizarGrupoUsuario(requisitoCajaClasificacionDTO);
        }
    }
    
    private void actualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        RequisitoCajaClasificacion requisitoCajaClasificacion = null;
        for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : requisitosCajaClasificacion) {
            requisitoCajaClasificacion = convertInfoDTOTOEntity(requisitoCajaClasificacionDTO);
            entityManager.merge(requisitoCajaClasificacion);
            entityManager.flush();
            requisitoCajaClasificacionDTO.setIdRequisitoTipoSolicitanteCaja(requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja());
            crearActualizarGrupoUsuario(requisitoCajaClasificacionDTO);
        }
    } 
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void crearActualizarGrupoUsuario(RequisitoCajaClasificacionDTO requisitoCajaClasificacion) {
        
        if (requisitoCajaClasificacion == null) {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        List<String> grupoUsuarioDTO = requisitoCajaClasificacion.getGrupoUsuario();
        if (grupoUsuarioDTO == null || grupoUsuarioDTO.isEmpty()) {
            //revisar que si borre los gruposUsuario; Debe salir excepcion o el metodo debe retornar      
            entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_GRUPO_USUARIO_ID_REQUISITO_CAJA_CLASIFICACION)
                    .setParameter("idRequisitoCajaClasificacion", requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja())
                    .executeUpdate();
            logger.debug("Finaliza crearActualizarGrupoUsuario(RequisitoCajaClasificacionDTO)");
        }
        else {
            List<Long> idsGruposUsuario = new ArrayList<Long>();
            List<GrupoRequisito> grupoRequisitosBD = null;
            if (requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja() != null) {
               grupoRequisitosBD = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPO_USUARIO, GrupoRequisito.class)
                       .setParameter("idRequisitoCajaClasificacion", requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja())
                       .getResultList();

                for (String grupoUsuario : grupoUsuarioDTO) {
                    GrupoRequisito grupoRequisitoActualizar = null;
                    if (grupoRequisitosBD != null && !grupoRequisitosBD.isEmpty()) {
                        for (GrupoRequisito grupoRequisitoExistente : grupoRequisitosBD) {
                            if (grupoUsuario != null && (grupoUsuario.equals(grupoRequisitoExistente.getGrupoUsuario()))) {
                                grupoRequisitoActualizar = grupoRequisitoExistente;
                                break;
                            }
                        }
                    }
                    if (grupoRequisitoActualizar == null) {
                        grupoRequisitoActualizar = new GrupoRequisito();
                        grupoRequisitoActualizar
                                .setIdRequisitoCajaClasificacion(requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja());
                        grupoRequisitoActualizar.setGrupoUsuario(grupoUsuario);
                        grupoRequisitosBD.add(grupoRequisitoActualizar);
                        entityManager.persist(grupoRequisitoActualizar);
                    } 
                    idsGruposUsuario.add(grupoRequisitoActualizar.getIdGrupoRequisito());
                }
                entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_GRUPOS_USUARIOS_NO_PRESENTES)
                        .setParameter("idRequisitoCajaClasificacion", requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja())
                        .setParameter("idsGruposUsuarios", idsGruposUsuario).executeUpdate();
                logger.debug("Finaliza crearActualizarGrupoUsuario(RequisitoCajaClasificacionDTO)");
            }
        }
    }
    
    /**
     * Método encargado de consultar la información de las listas de chequeo, 
     * para el tipo de solicitante, caja de compensación y empleador recibidos
     * por parámetro
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @see com.asopagos.listaschequeo.service.IRequisitosService#consultarRequisitosListaChequeo(java.lang.Long, java.lang.Long, java.lang.Long) 
     * @param tipoTransaccion
     * @param clasificacion Parámetro opcional incluido a partir del proceso 
     * 1.2.1 
     * @return  lista de requisitos listas de chequeo
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConsultarRequisitosListaChequeoOutDTO> consultarRequisitosListaChequeo(TipoTransaccionEnum tipoTransaccion,
            ClasificacionEnum clasificacion, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<String> grupos) {

        String cajaCompensacion = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID);
        Integer idCajaCompensacion = Integer.valueOf(cajaCompensacion);

        List<Object[]> listaRaw;
        Long idRCC;
        Boolean isRegistraduria = false; 
        if (tipoIdentificacion == null || numeroIdentificacion == null) {
            listaRaw = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CHEQUEO)
                    .setParameter("tipoTransaccion", tipoTransaccion.name()).setParameter("clasificacion", clasificacion.name())
                    .setParameter("idCajaCompensacion", idCajaCompensacion).getResultList();
        } else {
            listaRaw = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CHEQUEO_PERSONA)
                    .setParameter("tipoTransaccion", tipoTransaccion.name()).setParameter("clasificacion", clasificacion.name())
                    .setParameter("idCajaCompensacion", idCajaCompensacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name()).getResultList();

            ExisteRegistraduriaNacional registraduria = new ExisteRegistraduriaNacional(tipoIdentificacion, numeroIdentificacion);
            logger.info(tipoIdentificacion + " " + numeroIdentificacion);
            registraduria.execute();
            isRegistraduria = registraduria.getResult();
            logger.info(isRegistraduria);
        }

        if (listaRaw.isEmpty()) {
            return null;
        } else {
            List<ConsultarRequisitosListaChequeoOutDTO> requisitosListaChequeo = new ArrayList();
            for (Object[] itemRaw : listaRaw) {
                ConsultarRequisitosListaChequeoOutDTO requisito = new ConsultarRequisitosListaChequeoOutDTO();
                if((((BigInteger) itemRaw[0]).longValue() == 69 || ((BigInteger) itemRaw[0]).longValue() == 75) && isRegistraduria){
                    requisito.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OPCIONAL);
                }else{
                    requisito.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.valueOf((String) itemRaw[2]));
                }
                requisito.setIdRequisito(((BigInteger) itemRaw[0]).longValue());
                requisito.setNombreRequisito((String) itemRaw[1]);
                requisito.setTipoRequisito((String) itemRaw[3]);
                requisito.setTextoAyuda((String) itemRaw[4]);
                if (numeroIdentificacion != null && tipoIdentificacion != null) {
                    if (grupos != null && !grupos.isEmpty()) {
                        Long cantidadGruposUsuarios;
                        try {
                            idRCC = new Long((itemRaw[5]).toString());
                            cantidadGruposUsuarios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPO_USUARIO_ID_RCC, Long.class)
                                .setParameter("idRequisitoCajaClasificacion", idRCC).setParameter("listaGrupos", grupos)
                                .getSingleResult();
                        } catch (Exception e) {
                            cantidadGruposUsuarios=0L;
                        }                      
                        /** if (cantidadGruposUsuarios>0) {
                         Se mostrara la imagen precargada de la Base de datos
                         idenpendientemente del grupo de usuarios
                            */
                            requisito.setIdentificadorDocumentoPrevio((String) itemRaw[6]);
                       /** }
                        else {
                            requisito.setIdentificadorDocumentoPrevio(null);
                        }*/
                    } else{
                        requisito.setIdentificadorDocumentoPrevio(null);
                    }
                    if (itemRaw[7] != null){
                        Date fecha =  (Date) itemRaw[7];
                        requisito.setFechaRecepcionDocumentos(fecha.getTime());    
                    }
                }
                requisitosListaChequeo.add(requisito); 
            }
            
            return requisitosListaChequeo;
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.listaschequeo.service.RequisitosService#replicarCrearRequisitosCajaClasificacion(java.util.List)
     */
    @Override
    public void replicarCrearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        logger.debug("Inicia  guardarCrearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
        
        crearRequisitosCajaClasificacion(requisitosCajaClasificacion);
        
        ReplicarCreacionListaChequeoClasificacion replicarListaChequeoClasificacion = new ReplicarCreacionListaChequeoClasificacion(requisitosCajaClasificacion);
        replicarListaChequeoClasificacion.execute();
        
        logger.debug("Finaliza guardarCrearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
    }

    /** (non-Javadoc)
     * @see com.asopagos.listaschequeo.service.RequisitosService#replicarActualizarRequisitosCajaClasificacion(java.util.List)
     */
    @Override
    public void replicarActualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        logger.debug("Inicia guardarActualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
        
        actualizarRequisitosCajaClasificacion(requisitosCajaClasificacion);
        
        ReplicarActualizacionListaChequeoClasificacion replicarListaChequeoClasificacion = new ReplicarActualizacionListaChequeoClasificacion(requisitosCajaClasificacion);
        replicarListaChequeoClasificacion.execute();
        
        logger.debug("Finaliza guardarActualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
    }  
    

	
	/* (non-Javadoc)
	 * @see com.asopagos.listaschequeo.service.RequisitosService#replicarCrearRequisitosClasificacionPorCaja(java.util.List)
	 */
	@Override
	public void replicarCrearRequisitosClasificacionPorCaja(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
		logger.debug("Inicia  replicarCrearRequisitosClasificacionPorCaja(List<RequisitoCajaClasificacionDTO>)");
        
        crearRequisitosCajaClasificacion(requisitosCajaClasificacion);
        
        ReplicarCreacionListaChequeoClasificacionPorCaja replicarListaChequeoClasificacion = new ReplicarCreacionListaChequeoClasificacionPorCaja(requisitosCajaClasificacion);
        replicarListaChequeoClasificacion.execute();
        
        logger.debug("Finaliza replicarCrearRequisitosClasificacionPorCaja(List<RequisitoCajaClasificacionDTO>)");
	}

	
	/* (non-Javadoc)
	 * @see com.asopagos.listaschequeo.service.RequisitosService#replicarActualizarRequisitosClasificacionPorCaja(java.util.List)
	 */
	@Override
	public void replicarActualizarRequisitosClasificacionPorCaja(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {

		logger.debug("Inicia replicarActualizarRequisitosClasificacionPorCaja(List<RequisitoCajaClasificacionDTO>)");
        
        actualizarRequisitosCajaClasificacion(requisitosCajaClasificacion);
        
        ReplicarActualizacionListaChequeoClasificacionPorCaja replicarListaChequeoClasificacion = new ReplicarActualizacionListaChequeoClasificacionPorCaja(requisitosCajaClasificacion);
        replicarListaChequeoClasificacion.execute();
        
        logger.debug("Finaliza replicarActualizarRequisitosClasificacionPorCaja(List<RequisitoCajaClasificacionDTO>)");
		
	}
}
