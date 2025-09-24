package com.asopagos.afiliaciones.empleadores.ejb;

import static com.asopagos.util.Interpolator.interpolate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.afiliaciones.empleadores.constants.NamedQueriesConstants;
import com.asopagos.afiliaciones.empleadores.dto.RespuestaConsultaSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.dto.SolicitudAfiliacionEmpleadorDTO;
import com.asopagos.afiliaciones.empleadores.service.AfiliacionEmpleadoresService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.dto.SolicitudDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * implemanta los servicios relacionados con la gestion de afiliaciones
 * <b>Módulo:</b> Asopagos - HU <br/>
 * m 66-92-93-70
 *
 * @author Josué Nicolás Pinzón Villamil
 *         <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon@heinsohn.com.co
 *         </a>
 */
@Stateless
public class AfiliacionEmpleadoresBusiness implements AfiliacionEmpleadoresService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "afiliaciones_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(AfiliacionEmpleadoresBusiness.class);

    /**
     * Metodo que busca a un empleador y confirma su existencia por medio de una
     * persona
     *
     * @param persona
     *        Persona unica asociada a un empleador
     * @return Retorna un empleador
     */
    private Empleador buscarEmpleadorPersona(Persona persona) {
        try {
            Empleador empleador;
            Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR);
            q.setParameter("numeroIdentificacion", persona.getNumeroIdentificacion());
            q.setParameter("tipoIdentificacion", persona.getTipoIdentificacion());
            empleador = (Empleador) q.getSingleResult();
            return empleador;
        } catch (NonUniqueResultException nue) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (NoResultException e) {
        	return null;
        }
    }

    /**
     * Metodo que busca a un empleador y confirma su existencia por medio de una
     * persona
     *
     * @param persona
     *        Persona unica asociada a un empleador
     * @return Retorna un empleador
     */
    private Empleador buscarEmpleador(Long idEmpleador) {
        List<Empleador> listEmpleador;
        Query q = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_ID);
        q.setParameter("idEmpleador", idEmpleador);

        listEmpleador = q.getResultList();

        if (listEmpleador.size() > 0) {
            return listEmpleador.get(0);
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.afiliaciones.service.AfiliacionesService#crearSolicitudAfiliacionEmpleador(com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador)
     */
    @Override
    public Long crearSolicitudAfiliacionEmpleador(SolicitudAfiliacionEmpleador solAfiliacionEmpleador) {

        Empleador empl;
        if (solAfiliacionEmpleador.getIdEmpleador() == null) {
            solAfiliacionEmpleador.getSolicitudGlobal().setFechaCreacion(new Date());
            entityManager.persist(solAfiliacionEmpleador);
            return solAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador();
        }
        else {
            empl = buscarEmpleador(solAfiliacionEmpleador.getIdEmpleador());
            if (empl != null) {
                empl = buscarEmpleadorPersona(empl.getEmpresa().getPersona());
            }                        
            if (empl == null) {
            	solAfiliacionEmpleador.getSolicitudGlobal().setFechaCreacion(new Date());
                entityManager.persist(solAfiliacionEmpleador);
                return solAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador();
            }            
        }
        try {
            entityManager.merge(empl);
            solAfiliacionEmpleador.setIdEmpleador(solAfiliacionEmpleador.getIdEmpleador());
            solAfiliacionEmpleador.getSolicitudGlobal().setFechaCreacion(new Date());
            entityManager.persist(solAfiliacionEmpleador);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }

        return solAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador();

    }

    @Override
    public Boolean crearSolicitudAfiliacionEmpleadorCeroTrabajadores(SolicitudAfiliacionEmpleador solAfiliacionEmpleador) {
        logger.info("Inicia el metodo crearSolicitudAfiliacionEmpleadorCeroTrabajadores" + solAfiliacionEmpleador.getIdEmpleador());
        try {
            // Antes de persistir la solicitud de 0 trabajadores, se valida los campos de la ultima afiliacion
            List<SolicitudAfiliacionEmpleador> solicitudesAnteriores =
            entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_BY_EMPLEADOR, SolicitudAfiliacionEmpleador.class)
                    .setParameter("idEmpleador", solAfiliacionEmpleador.getIdEmpleador())
                    .getResultList();
            if (solicitudesAnteriores != null && !solicitudesAnteriores.isEmpty()) {
                SolicitudAfiliacionEmpleador ultimaSolicitud = solicitudesAnteriores.get(0);
                // seteo de valores
                solAfiliacionEmpleador.setCodigoEtiquetaPreimpresa(ultimaSolicitud.getCodigoEtiquetaPreimpresa());
                solAfiliacionEmpleador.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
                solAfiliacionEmpleador.setNumeroCustodiaFisica(ultimaSolicitud.getNumeroCustodiaFisica());
                solAfiliacionEmpleador.setNumeroActoAdministrativo(ultimaSolicitud.getNumeroActoAdministrativo());
                solAfiliacionEmpleador.setFechaAprobacionConsejo(ultimaSolicitud.getFechaAprobacionConsejo());

            }
            entityManager.persist(solAfiliacionEmpleador);
            return new Boolean(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.service.AfiliacionesService#consultarSolicitudAfiliacionEmpleador(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public SolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador(Long idSolicitudAfiliacionEmpleador) {
        try {
            return entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICTUD, SolicitudAfiliacionEmpleador.class)
                    .setParameter("solicitudAfiliacion", idSolicitudAfiliacionEmpleador).getSingleResult();
        } catch (NoResultException nre) {
            logger.warn("No existe la solicitud de afiliación " + idSolicitudAfiliacionEmpleador);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.afiliaciones.service.AfiliacionesService#actualizarSolicitudAfiliacionEmpleador(java.lang.Long)
     */
    @Override
    public void actualizarSolicitudAfiliacionEmpleador(Long idSolicitudAfiliacionEmpleador,
            SolicitudAfiliacionEmpleador solAfiliacionEmpleador) {
        logger.debug("Inicia actualizarSolicitudAfiliacionEmpleador(Long, SolicitudAfiliacionEmpleador)");
        try {
            solAfiliacionEmpleador.setIdSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador);
            entityManager.merge(solAfiliacionEmpleador);
        } catch (Exception e) {
            logger.error("Finaliza actualizarSolicitudAfiliacionEmpleador(Long, SolicitudAfiliacionEmpleador): ", e);
            logger.debug("Finaliza actualizarSolicitudAfiliacionEmpleador(Long, SolicitudAfiliacionEmpleador): Error inesperado");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }
        logger.debug("Finaliza actualizarSolicitudAfiliacionEmpleador(Long, SolicitudAfiliacionEmpleador)");
    }

    /**
     * @see com.asopagos.afiliaciones.service.AfiliacionesService#actualizarEstadoSolicitudAfiliacion(java.lang.Long,
     *      com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum)
     */
    @Override
    public void actualizarEstadoSolicitudAfiliacion(Long idSolicitudAfiliacionEmpleador, EstadoSolicitudAfiliacionEmpleadorEnum estado) {
        try {
            logger.debug("Inicia actualizarEstadoSolicitudAfiliacion(Long, EstadoSolicitudAfiliacionEmpleadorEnum)");
            SolicitudAfiliacionEmpleador solAfilEmpleador = consultarSolicitudAfiliacionEmpleador(idSolicitudAfiliacionEmpleador);
            if (solAfilEmpleador == null) {
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
            } 
            actualizarEstadoSolicitudAfiliacion(estado, solAfilEmpleador);
        } catch (ParametroInvalidoExcepcion e) {
            logger.error("Finaliza actualizarEstadoSolicitudAfiliacion(Long, EstadoSolicitudAfiliacionEmpleadorEnum): Error recurso invalido");
            throw e;
        } catch (Exception e) {
            logger.error("Finaliza actualizarEstadoSolicitudAfiliacion(Long, EstadoSolicitudAfiliacionEmpleadorEnum): Error " + e);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
        }
    }
    
    /**
     *Consulta Descentralizada por numero de  Nit con serial
     */
    @Override
    public List<PreRegistroEmpresaDesCentralizada> ConsultarRegistrodes(String numeroIdentificacion) {
        
        List<PreRegistroEmpresaDesCentralizada> descentralizada = new ArrayList<>();
        descentralizada = (List<PreRegistroEmpresaDesCentralizada>) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_PREREGISTRO_DESCENTRALIZADA)
                .setParameter("numeroDocumentoConSerial", numeroIdentificacion)
                .getResultList();
        return descentralizada;
    }

    /**
     * Método con la lógica de actualizar el estado de la solicitud
     * 
     * @param idSolicitudAfiliacionEmpleador
     * @param estado
     * @param solAfilEmpleador
     */
    private void actualizarEstadoSolicitudAfiliacion(EstadoSolicitudAfiliacionEmpleadorEnum estado,
            SolicitudAfiliacionEmpleador solAfilEmpleador) {
        logger.debug(
                "Inicia actualizarEstadoSolicitudAfiliacion(Long, EstadoSolicitudAfiliacionEmpleadorEnum, SolicitudAfiliacionEmpleador)");
        if (solAfilEmpleador != null) {
        	/*se verifica si el nuevo estado es CERRADA para actualizar el resultado del proceso 
        	 * Mantis 0216650 */
        	if(EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA.equals(estado)&&
        			(EstadoSolicitudAfiliacionEmpleadorEnum.APROBADA.equals(solAfilEmpleador.getEstadoSolicitud())
        					|| EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA.equals(solAfilEmpleador.getEstadoSolicitud()) ||
        					EstadoSolicitudAfiliacionEmpleadorEnum.DESISTIDA.equals(solAfilEmpleador.getEstadoSolicitud()) ||
        					EstadoSolicitudAfiliacionEmpleadorEnum.CANCELADA.equals(solAfilEmpleador.getEstadoSolicitud()))){
        		solAfilEmpleador.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.valueOf(solAfilEmpleador.getEstadoSolicitud().name()));
        	}
        	/*Fin modificacion mantis*/
            solAfilEmpleador.setEstadoSolicitud(estado);
            entityManager.merge(solAfilEmpleador);
        }
        else {
            // en caso se no estar en el sistema se alerta con un mensaje
            logger.warn(
                    "Finaliza actualizarEstadoSolicitudAfiliacion(Long, EstadoSolicitudAfiliacionEmpleadorEnum, SolicitudAfiliacionEmpleador): No se encuentra la solicitud");
        }
        logger.debug(
                "Finaliza actualizarEstadoSolicitudAfiliacion(Long, EstadoSolicitudAfiliacionEmpleadorEnum, SolicitudAfiliacionEmpleador)");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.empleadores.service.
     *      AfiliacionEmpleadoresService#consultarPendientesAprobacionConsejo(java.
     *      lang.Long, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudAfiliacionEmpleador> consultarPendientesAprobacionConsejo() {
        logger.debug("Inicia generarReporteDeEmpleadoresAfiliados(Long, Long)");

        List<SolicitudAfiliacionEmpleador> listSolicitudAfiliacionEmpleadores;

        EstadoEmpleadorEnum estado = EstadoEmpleadorEnum.ACTIVO;

        // se consulta los empleados afiliados en un rango de fechas
        listSolicitudAfiliacionEmpleadores = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_LISTA_SOL_AFILI_EMP_PENDIENTES_APROBACION)
                .setParameter("estado", estado).getResultList();

        // Si la lista no es vacia la retorna
        if (listSolicitudAfiliacionEmpleadores.size() > 0) {
            logger.debug("Finaliza generarReporteDeEmpleadoresAfiliados(Long, Long)");
            return listSolicitudAfiliacionEmpleadores;
        }
        logger.debug("Finaliza generarReporteDeEmpleadoresAfiliados(Long, Long): No hay datos para los filstro suministrados");
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.empleadores.service.
     *      AfiliacionEmpleadoresService#registrarAprobacionConsejo(java.util.List,
     *      java.lang.String, java.lang.Long)
     */
    public void registrarAprobacionConsejo(List<Long> solicitudes, String numeroActoAdministrativo, Long fechaAprobacionConsejo) {
        logger.debug("Inicia registrarAprobacionConsejo(List<Long>, String, Long)");
        try {
            Date fecha = new Date(fechaAprobacionConsejo);
			for(Long idSolicitud : solicitudes){
				SolicitudAfiliacionEmpleador solicitud = entityManager.getReference(SolicitudAfiliacionEmpleador.class, idSolicitud);
				solicitud.setFechaAprobacionConsejo(fecha);
				solicitud.setNumeroActoAdministrativo(numeroActoAdministrativo);
				entityManager.merge(solicitud);
			}

        } catch (Exception e) {
            logger.error("Finaliza registrarAprobacionConsejo(List<Long>, String, Long)", e);
            logger.debug("Finaliza registrarAprobacionConsejo(List<Long>, String, Long)");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        logger.debug("Finaliza registrarAprobacionConsejo(List<Long>, String, Long)");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.empleadores.service.
     *      AfiliacionEmpleadoresService#consultarSolicitud(com.asopagos.
     *      enumeraciones.personas.TipoIdentificacionEnum, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public List<RespuestaConsultaSolicitudDTO> consultarSolicitud(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String numeroRadicado, Short digitoVerificacion) {
        Query q = armarConsulta(tipoIdentificacion, numeroIdentificacion, numeroRadicado);
        
        List<SolicitudAfiliacionEmpleador> resultados = q.getResultList();
        return convertirADTO(resultados);
    }

    private List<RespuestaConsultaSolicitudDTO> convertirADTO(List<SolicitudAfiliacionEmpleador> resultados) {
        List<RespuestaConsultaSolicitudDTO> respuesta = new ArrayList<>();
        for (SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador : resultados) {
            RespuestaConsultaSolicitudDTO item = new RespuestaConsultaSolicitudDTO();
            item.setCanalRecepcion(solicitudAfiliacionEmpleador.getSolicitudGlobal().getCanalRecepcion());
            item.setEstadoSolicitud(solicitudAfiliacionEmpleador.getEstadoSolicitud());
            item.setFechaRadicacion(solicitudAfiliacionEmpleador.getSolicitudGlobal().getFechaRadicacion());
			if (solicitudAfiliacionEmpleador.getIdEmpleador() != null) {
				Empleador e = entityManager.find(Empleador.class, solicitudAfiliacionEmpleador.getIdEmpleador());
				item.setNumeroIdentificacion(e.getEmpresa().getPersona().getNumeroIdentificacion());
				item.setRazonSocial(e.getEmpresa().getPersona().getRazonSocial());
				item.setTipoIdentificacion(e.getEmpresa().getPersona().getTipoIdentificacion());
			}
			item.setNumeroRadicado(solicitudAfiliacionEmpleador.getSolicitudGlobal().getNumeroRadicacion());
            item.setIdSolicitud(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador());
            item.setIdEmpleador(solicitudAfiliacionEmpleador.getIdEmpleador());
            if (solicitudAfiliacionEmpleador.getSolicitudGlobal().getIdInstanciaProceso()!=null) {
                item.setIdInstanciaProceso(Long.parseLong(solicitudAfiliacionEmpleador.getSolicitudGlobal().getIdInstanciaProceso()));                
            }
            item.setTipoTransaccion(solicitudAfiliacionEmpleador.getSolicitudGlobal().getTipoTransaccion());
            respuesta.add(item);
        }
        return respuesta;
    }

    private Query armarConsulta(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicado) {
    	Query q =null;
    	if(tipoIdentificacion!=null && numeroIdentificacion!=null){
    		if(numeroRadicado!=null){
    			q= entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR);
                q.setParameter("tipoIdentificacion", tipoIdentificacion);
                q.setParameter("numeroIdentificacion", numeroIdentificacion);
                q.setParameter("numeroRadicado", numeroRadicado);
    		}else{
    			q= entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_SIN_RADICADO);
    			q.setParameter("tipoIdentificacion", tipoIdentificacion);
    			q.setParameter("numeroIdentificacion", numeroIdentificacion);    			
    		}
    		
    	}else if(numeroIdentificacion!=null){
    		if(numeroRadicado!=null){
    			q= entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_NUMEROID_CON_RADICADO);
                q.setParameter("numeroIdentificacion", numeroIdentificacion);
                q.setParameter("numeroRadicado", numeroRadicado);
    		}else{
    			q= entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_NUMEROID_SIN_RADICADO);
    			q.setParameter("numeroIdentificacion", numeroIdentificacion);    			
    		}
    		
    	}else if(numeroRadicado!=null){
    		
    		q= entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_POR_NUMERO_RADICADO);
            q.setParameter("numeroRadicado", numeroRadicado);
    	}
    	
    	return q;
    }
    
    /**
     * Método encargado de consultar la solicitud afiliación empleado
     * 
     * @param numeroRadicado
     *        numero de radicado
     * 
     * @param tipoIdentificacion
     *        tipo de identificación
     * @param numeroIdentificacion
     *        numero de identificación
     * 
     * @return objeto con la solicitud afiliacion empleado
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudAfiliacionEmpleadorDTO consultarSolicitudAfiliEmpleador(String numeroRadicado,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, CanalRecepcionEnum canalRecepcion) {
        try {
    	    SolicitudAfiliacionEmpleador solAfi = (SolicitudAfiliacionEmpleador) entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR)
				.setParameter("numeroRadicado", numeroRadicado)
				.setParameter("tipoIdentificacion", tipoIdentificacion)
				.setParameter("numeroIdentificacion", numeroIdentificacion)
				.setParameter("canalRecepcion", canalRecepcion)
				.getSingleResult();
    		return SolicitudAfiliacionEmpleadorDTO.convertToDTO(solAfi);
    	} catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudAfiliacionEmpleador(String, TipoIdentificacionEnum, String)" + interpolate(
                    "No se encontraron resultados con el nro radicación {0} ingresada.", numeroRadicado));
    	    return null;
    	} catch (NonUniqueResultException nur) {
			logger.error(
					"Finaliza consultarSolicitudAfiliacionEmpleador(String, TipoIdentificacionEnum, String)");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
    	}
    }
    
    /**
     * Método encargado de consultar la solicitud afiliación de empleador por medio del número de radicado 
     * 
     * @param numeroRadicado
     *        numero de radicado
     *        
     * @param tipoIdentificacion
     *        tipo de identificación
     * @param numeroIdentificacion
     *        numero de identificación
     * 
     * @return objeto con la solicitud afiliacion empleado
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudAfiliacionEmpleadorDTO consultarSolicitudAfiliacionEmpleadorPorRadicado(String numeroRadicado) {
        try {
            SolicitudAfiliacionEmpleador solAfi = (SolicitudAfiliacionEmpleador) entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_POR_NUMERO_RADICADO)
                .setParameter("numeroRadicado", numeroRadicado)
                .getSingleResult();
            return SolicitudAfiliacionEmpleadorDTO.convertToDTO(solAfi);
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudAfiliacionEmpleadorPorRadicado(String)" + interpolate(
                    "No se encontraron resultados con el nro radicación {0} ingresada.", numeroRadicado));
            return null;
        } catch (NonUniqueResultException nur) {
            logger.error(
                    "Finaliza consultarSolicitudAfiliacionEmpleadorPorRadicado(String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudDTO consultarSolicitudAfiliacionEmpleadorPorInstancia(String instanciaProceso) {
        SolicitudDTO solicitudDTO = null;
        try {
            Solicitud solAfi = (Solicitud) entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPLEADOR_POR_INSTANCIA_PROCESO)
                .setParameter("instanciaProceso", instanciaProceso)
                .getSingleResult();

            if (solAfi != null) {
                solicitudDTO = new SolicitudDTO(solAfi);
            }
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.error("Finaliza consultarSolicitudAfiliacionEmpleadorPorInstancia(String)" + interpolate(
                    "No se encontraron resultados con el nro radicación {0} ingresada.", instanciaProceso));
            return null;
        } catch (NonUniqueResultException nur) {
            logger.error(
                    "Finaliza consultarSolicitudAfiliacionEmpleadorPorInstancia(String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
        }
    }
    
    
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SolicitudAfiliacionEmpleador> consultarSolicitudesEnProceso(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Long idEmpleador) {

		logger.debug(
				"Inicia consultarSolicitudesEnProceso(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion, Long idEmpleador)");
		List<EstadoSolicitudAfiliacionEmpleadorEnum> estados = new ArrayList<>();
		estados.add(EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
		estados.add(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
		if(tipoIdentificacion != null && numeroIdentificacion != null){
			Query consultarSolTemporal = entityManager.createNamedQuery(NamedQueriesConstants.NAMED_QUERY_SOLIC_EMPL_PERSONA)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("estadosSolicitud", estados);

			List<SolicitudAfiliacionEmpleador> sate = consultarSolTemporal.getResultList();
			if (sate != null && !sate.isEmpty()) {
				return sate;
			} else {
				logger.debug(
						"Inicia consultarSolicitudesEnProceso(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion, Long idEmpleador)");
				return null;
			}
		}
		else if (idEmpleador != null && idEmpleador.equals("")) {

			Query consultarSolTemporal = entityManager.createNamedQuery(NamedQueriesConstants.NAMED_QUERY_SOLIC_EMPL)
					.setParameter("idEmpleador", idEmpleador).setParameter("estadosSolicitud", estados);

			List<SolicitudAfiliacionEmpleador> sate = consultarSolTemporal.getResultList();
			if (sate != null && !sate.isEmpty()) {
				return sate;
			} else {
				logger.debug(
						"Inicia consultarSolicitudesEnProceso(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion, Long idEmpleador)");
				return null;
			}
		} else {
			logger.debug(
					"Inicia consultarSolicitudesEnProceso(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion, Long idEmpleador)");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		}

	}
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Integer consultarIdDepartamento(String departamento) {
        try {
            Integer idDepartamento = Integer.valueOf( (Short) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_ID_DPTO)
                    .setParameter("dpto",departamento)
                    .getSingleResult());

            return idDepartamento;
        } catch (NoResultException NoResultException) {
            return 0;
        }
    }
    
    @Override
    public void actualizarFechaDesafiliacionEmpleador(Long idEmpleador){
        logger.info("Inicia actualizarFechaDesafiliacionEmpleador(Long idEmpleador)");
        try {
           Empleador empleador = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_ID,Empleador.class)
           .setParameter("idEmpleador", idEmpleador).getSingleResult();
           empleador.setFechaGestionDesafiliacion(null);
            entityManager.merge(empleador);
        } catch (NoResultException NoResultException) {
            logger.error("Error al actualizar la fecha de desafiliación del empleador");
        }
    }


    @Override
    public List<SolicitudAfiliacionEmpleador> consultarSolicitudAfiliacionEmpleadorAnteriores(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        List<SolicitudAfiliacionEmpleador> solicitudesEmpleador = new ArrayList();
        solicitudesEmpleador = entityManager
            .createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_AFILIACION_EMPLEADOR)
            .setParameter("numeroIdentificacion", numeroIdentificacion)
            .setParameter("tipoIdentificacion", tipoIdentificacion)
            .getResultList();
        
        return solicitudesEmpleador;
    }
} 
