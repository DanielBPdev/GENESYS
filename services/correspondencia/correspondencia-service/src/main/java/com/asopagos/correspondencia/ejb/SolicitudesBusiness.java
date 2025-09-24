package com.asopagos.correspondencia.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.correspondencia.constants.NamedQueriesConstants;
import com.asopagos.correspondencia.dto.RemisionDocumento;
import com.asopagos.correspondencia.service.CorrespondenciaService;
import com.asopagos.correspondencia.service.SolicitudesService;
import com.asopagos.dto.FiltroSolicitudDTO;
import com.asopagos.dto.ResultadoConsultaSolicitudDTO;
import com.asopagos.dto.afiliaciones.ItemDetalleRemisionBackDTO;
import com.asopagos.dto.afiliaciones.ItemResumenRemisionBackDTO;
import com.asopagos.dto.afiliaciones.ListaDetalleRemisionBackDTO;
import com.asopagos.dto.afiliaciones.RecepcionSolicitudDTO;
import com.asopagos.dto.afiliaciones.RemisionBackDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.core.CajaCorrespondencia;
import com.asopagos.entidades.ccf.fovis.SolicitudLegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.SolicitudPostulacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ConsultarSolicitudesFiltroSolicitud;
import com.asopagos.tareashumanas.clients.EnviarSenal;
import com.asopagos.tareashumanas.clients.ObtenerTareaActivaInstancia;
import com.asopagos.tareashumanas.clients.ObtenerTareasAsignadasUsuario;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.tareashumanas.dto.TareaDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * implementa los servicios relacionados con la gestion de solicitudes de
 * remisión <b>Módulo:</b> Asopagos - HU <br/>
 * <b>Módulo:</b> Asopagos - HU 086 <br/>
 *
 * @author Harold Andrés Alzate Betancur
 *         <a href="mailto:halzate@heinsohn.com.co"> halzate@heinsohn.com.co</a>
 */
@Stateless
public class SolicitudesBusiness implements SolicitudesService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "correspondencia_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(SolicitudesBusiness.class);

	/**
	 * Referencia al EJB AfiliacionesBusiness
	 */
	@EJB
	private CorrespondenciaService correspondenciaBusiness;

	/**
	 * <b>Descripción</b>Método encargado de resolver el listado de las
	 * solicitudes remisión back <br/>
	 * 
	 * @param fechaInicial,
	 *            fecha inicial
	 * @param fechaFinal,
	 *            fecha final
	 * @param proceso,
	 *            enum con el tipo de proceso
	 * @param userDTO,
	 *            usuario de la sesión
	 * @return RemisionBackDTO objeto con la lista de detalles y resumen
	 */
	public RemisionBackDTO generarListadoSolicitudesRemisionBack(Long fechaInicial, Long fechaFinal,
			ProcesoEnum proceso, String usuario) {

        Date fechaInicialDate = new Date(fechaInicial);
        Date fechaFinalDate = new Date(fechaFinal);

        List<TipoTransaccionEnum> tiposTransacciones = TipoTransaccionEnum.obtnerTiposTransaccionPorProceso(proceso);
        if (tiposTransacciones.isEmpty()) {
            logger.debug(
                    "Finaliza generarListadoSolicitudesRemisionBack(Long, Long, ProcesoEnum, String): No hay datos para los filtros suministrados");
            throw new TechnicalException("No hay datos para los filtros suministrados");
        }

        RemisionBackDTO remision = new RemisionBackDTO();

        String consulta = NamedQueriesConstants.CONSULTAR_REMISION;

        TypedQuery<RemisionDocumento> typedQuery = entityManager.createNamedQuery(consulta, RemisionDocumento.class)
                .setParameter("fechaInicial", fechaInicialDate)
                .setParameter("fechaFinal", fechaFinalDate)
                .setParameter("tipoTransaccion", tiposTransacciones);
        
        List<RemisionDocumento> listaRemisionDocumento = typedQuery
                .getResultList();
        
        ObtenerTareasAsignadasUsuario obtenerTareasAsignadas = new ObtenerTareasAsignadasUsuario(usuario);
        obtenerTareasAsignadas.execute();
        List<TareaDTO> tareasAsignadas = obtenerTareasAsignadas.getResult();
        
        Set<Long> instanciasProceso = tareasAsignadas
                .stream()
                .map(TareaDTO::getIdInstanciaProceso)
                .collect(Collectors.toSet());
        
        List<ItemResumenRemisionBackDTO> listaResumen = new ArrayList<>();
        List<ListaDetalleRemisionBackDTO> listaDetalle = new LinkedList<>();
        
        Map<String, List<RemisionDocumento>> groupByDestinatarios = listaRemisionDocumento
                .stream()
                .filter(r -> instanciasProceso.contains(Long.valueOf(r.getIdInstanciaProceso())))
                .collect(Collectors.groupingBy(RemisionDocumento::getDestinatario));
        
        Map<String, List<RemisionDocumento>> destinatariosOrdenados = new LinkedHashMap<>();
        groupByDestinatarios.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEachOrdered(e -> destinatariosOrdenados.put(e.getKey(), e.getValue()));
        
        Map<String, ResultadoConsultaSolicitudDTO> radicados = consultarRadicados(fechaInicial, fechaFinal, proceso);
        
        for (Map.Entry<String,List<RemisionDocumento>> destinatario: destinatariosOrdenados.entrySet()) {
            
            List<RemisionDocumento> listaRemisiones = destinatariosOrdenados.get(destinatario.getKey());
            RemisionDocumento remisionParaResumen = listaRemisiones.get(0);
            
            ItemResumenRemisionBackDTO itemResumen = crearResumenRemision(remisionParaResumen);
            itemResumen.setCantidadDocumentos(listaRemisiones.size());
            listaResumen.add(itemResumen);
            
            List<ItemDetalleRemisionBackDTO> listItemDetalleRemisionBack = new ArrayList<>();
            for (RemisionDocumento remisionDocumento : listaRemisiones) {
                ItemDetalleRemisionBackDTO itemDetalle = convertirAItemDetalle(remisionDocumento, radicados);
                listItemDetalleRemisionBack.add(itemDetalle);
            }
            
            ListaDetalleRemisionBackDTO itemListaDetalle = crearItemListaDetalle(destinatario, remisionParaResumen,
                    listItemDetalleRemisionBack);
            listaDetalle.add(itemListaDetalle);
        }
        
        List<ListaDetalleRemisionBackDTO> listaDetalleOrdenadaSede = ordenarPorSede(listaDetalle);
        
        remision.setResumen(listaResumen);
        remision.setDetalle(listaDetalleOrdenadaSede);
        return remision;
	}

    private Map<String, ResultadoConsultaSolicitudDTO> consultarRadicados(Long fechaInicial, Long fechaFinal, ProcesoEnum proceso) {
        FiltroSolicitudDTO filtroSolicitud = new FiltroSolicitudDTO();
        filtroSolicitud.setEstadoSolicitud("PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS");
        filtroSolicitud.setFechaInicio(fechaInicial);
        filtroSolicitud.setFechaFin(fechaFinal);
        filtroSolicitud.setProceso(proceso);            
        
        ConsultarSolicitudesFiltroSolicitud consultarSolicitudesFiltroSolicitud = new ConsultarSolicitudesFiltroSolicitud(
                filtroSolicitud);
        consultarSolicitudesFiltroSolicitud.execute();
        List<ResultadoConsultaSolicitudDTO> listaSolicitudesResultado = consultarSolicitudesFiltroSolicitud.getResult();
        return listaSolicitudesResultado
                    .stream()
                    .collect(Collectors.toMap(ResultadoConsultaSolicitudDTO::getNumeroRadicacion, resultado -> resultado));
        
    }

    private ItemResumenRemisionBackDTO crearResumenRemision(RemisionDocumento remisionParaResumen) {
        ItemResumenRemisionBackDTO itemResumen = new ItemResumenRemisionBackDTO();
        itemResumen.setDestinatario(remisionParaResumen.getDestinatario());
        itemResumen.setIdInstanciaProceso(remisionParaResumen.getIdInstanciaProceso());
        itemResumen.setSedeDestinatario(remisionParaResumen.getNombreSede());
        itemResumen.setTipoTransaccion(remisionParaResumen.getTipoTransaccion());
        return itemResumen;
    }

    private ItemDetalleRemisionBackDTO convertirAItemDetalle(RemisionDocumento remisionDocumento, Map<String, ResultadoConsultaSolicitudDTO> radicados) {
        ItemDetalleRemisionBackDTO itemDetalle = new ItemDetalleRemisionBackDTO();
        itemDetalle.setNombre(remisionDocumento.getNombreSede());
        itemDetalle.setNombreDocumentoRequisito(remisionDocumento.getDescripcionDocumento());
        itemDetalle.setFechaRadicacion(remisionDocumento.getFechaRadicacion());
        itemDetalle.setNumeroRadicado(remisionDocumento.getNumeroRadicacion());
        if(radicados.containsKey(remisionDocumento.getNumeroRadicacion())){
            itemDetalle.setNombre(radicados.get(remisionDocumento.getNumeroRadicacion()).getNombreRazonSocialSolictante()); 
            itemDetalle.setNumeroIdentificacionSolicitante(radicados.get(remisionDocumento.getNumeroRadicacion()).getNumeroIdentificacionSolicitante());
        }
        return itemDetalle;
    }
    
    private ListaDetalleRemisionBackDTO crearItemListaDetalle(Map.Entry<String, List<RemisionDocumento>> destinatario,
            RemisionDocumento remisionParaResumen, List<ItemDetalleRemisionBackDTO> listItemDetalleRemisionBack) {
        ListaDetalleRemisionBackDTO itemListaDetalle = new ListaDetalleRemisionBackDTO();
        itemListaDetalle.setDestinatario(destinatario.getKey());
        itemListaDetalle.setIdInstanciaProceso(remisionParaResumen.getIdInstanciaProceso());
        itemListaDetalle.setRemitente(remisionParaResumen.getUsuarioRadicacion());
        itemListaDetalle.setSedeDestinatario(remisionParaResumen.getNombreSede());
        itemListaDetalle.setItems(listItemDetalleRemisionBack);
        return itemListaDetalle;
    }
    
    private List<ListaDetalleRemisionBackDTO> ordenarPorSede(List<ListaDetalleRemisionBackDTO> listaDetalle) {
        Map<String, List<ListaDetalleRemisionBackDTO>> groupBySedes = 
                listaDetalle.stream().collect(Collectors.groupingBy(ListaDetalleRemisionBackDTO::getSedeDestinatario));
        Map<String, List<ListaDetalleRemisionBackDTO>> sedesOrdenadas = new LinkedHashMap<>();
        groupBySedes.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEachOrdered(e -> sedesOrdenadas.put(e.getKey(), e.getValue()));
        List<ListaDetalleRemisionBackDTO> listaDetalleOrdenado = new LinkedList<>();
        for (Map.Entry<String,List<ListaDetalleRemisionBackDTO>> sede: sedesOrdenadas.entrySet()) {
            listaDetalleOrdenado.addAll(sede.getValue());
        }
        return listaDetalleOrdenado;
    }


	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.SolicitudesService#asociarSolicitudesACajaCorrespondencias(java.lang.String,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public List<String> asociarSolicitudesACajaCorrespondencias(String codigoSede, List<String> listaRadicados,
			UserDTO userDTO) {

		CajaCorrespondencia cajaCorrespondencia = correspondenciaBusiness.obtenerCajaCorrespondenciaAbierta(codigoSede,
				userDTO);
		
		List<String> idsInstanciaProceso = new ArrayList<>();
		
		//Se consultan las solicitudes a actualizar y se actualiza una por una para dejar traza de auditoria

		List<Solicitud> solicitudes = new ArrayList<>();
		solicitudes.addAll(entityManager
			.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_A_ACTUALIZAR)
			.setParameter("numerosRadicado", listaRadicados).getResultList()
			);
		
		for (Solicitud solicitud : solicitudes){
			solicitud = entityManager.merge(solicitud);
			solicitud.setIdCajaCorrespondencia(cajaCorrespondencia.getIdCajaCorrespondencia());
			solicitud.setEstadoDocumentacion(EstadoDocumentacionEnum.ENVIADA_AL_BACK);
			
			idsInstanciaProceso.add(solicitud.getIdInstanciaProceso());
			logger.info("Radicado procesado " + solicitud.getNumeroRadicacion());
		}
        return idsInstanciaProceso; 
		
//		// Se setea la caja de correspondencia de las solicitudes
//		entityManager.createNamedQuery(NamedQueriesConstants.ASOCIAR_SOLICITUDES_A_CAJA_CORRESPONDENCIA)
//				.setParameter("idCajaCorrespondencia", cajaCorrespondencia.getIdCajaCorrespondencia())
//				.setParameter("numerosRadicacion", listaRadicados).executeUpdate();
//
//		// Se actualiza el estado de la documentación
//		entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_DOCUMENTACION_SOLICITUDES)
//				.setParameter("nuevoEstado", EstadoDocumentacionEnum.ENVIADA_AL_BACK.name())
//				.setParameter("numerosRadicacion", listaRadicados).executeUpdate();
	}

	/**
	 * <b>Descripción</b>Método encargado de actualizar
	 * 
	 * @param numeroRadicado
	 *            Número radicado para consultar la Solicitud que será
	 *            actualizada
	 * @param estadoDocumento
	 *            estado documento para decidir como actulizar el estado de la
	 *            solicitud
	 * @param numeroCustodia
	 *            número custodia que será actualizado
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void registrarRecepcionSolicitud(String numeroRadicado, RecepcionSolicitudDTO datosRecepcionSolicitud) {
		logger.debug("Inicia registrarRecepcionSolicitud(String, RecepcionSolicitudDTO)");
		try {
			List<Solicitud> solicitudes = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_A_ACTUALIZAR)
					.setParameter("numeroRadicado", numeroRadicado).getResultList();

			if (solicitudes != null && !solicitudes.isEmpty()) {
				Solicitud sol = solicitudes.get(0);
				
				/*Se consulta si la solicitud es para Empleador, Persona o Novedad*/
				SolicitudAfiliacionPersona solPersona = null;
				SolicitudAfiliacionEmpleador solEmpleador = null;
				SolicitudNovedad solNovedad = null;
				SolicitudPostulacion solPostulacion = null;
				SolicitudLegalizacionDesembolso solLegalizacion = null;
				try {
					solPersona = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_SOLICITUD_PERSONA, SolicitudAfiliacionPersona.class)
							.setParameter("idSolicitud", sol.getIdSolicitud()).getSingleResult();
				} catch (NoResultException e) {
					solPersona = null;
				}
				if(solPersona == null) {
					try {
						solEmpleador = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_SOLICITUD_EMPLEADOR, SolicitudAfiliacionEmpleador.class)
								.setParameter("idSolicitud", sol.getIdSolicitud()).getSingleResult();
					} catch (NoResultException e) {
						solEmpleador = null;
					}
				}
				if (solPersona == null && solEmpleador == null) {
					try {
						solNovedad = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_SOLICITUD_NOVEDAD, SolicitudNovedad.class)
								.setParameter("idSolicitud", sol.getIdSolicitud()).getSingleResult();
					} catch (NoResultException e) {
						solNovedad = null;
					}
				}
				if (solPersona == null && solEmpleador == null && solNovedad == null) {
					try {
						solPostulacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_SOLICITUD_POSTULACION, SolicitudPostulacion.class)
								.setParameter("idSolicitud", sol.getIdSolicitud()).getSingleResult();
					} catch (NoResultException e) {
						solPostulacion = null;
					}
				}
				if (solPersona == null && solEmpleador == null && solNovedad == null && solPostulacion == null) {
                    try {
                        solLegalizacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_SOLICITUD_LEGALIZACION, SolicitudLegalizacionDesembolso.class)
                                .setParameter("idSolicitud", sol.getIdSolicitud()).getSingleResult();
                    } catch (NoResultException e) {
                        solLegalizacion = null;
                    }
                }
				/*Si se trata de solicitudes para empleador, se actualiza número de custodia*/
				if (solEmpleador != null) {
					// Se actualiza el nro de custodia en SolicitudAfiliacionEmpleador
					SolicitudAfiliacionEmpleador solicitudUpdate = entityManager.getReference(
							SolicitudAfiliacionEmpleador.class, 
							solEmpleador.getIdSolicitudAfiliacionEmpleador());
					solicitudUpdate.setNumeroCustodiaFisica(datosRecepcionSolicitud.getNumeroCustodia());
					entityManager.merge(solicitudUpdate);
					
//					entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_NRO_CUSTODIA_SOLICITUD_AFILIACION_EMPLEADOR)
//					.setParameter("idSolitudAfiliacionEmpleador", solEmpleador.getIdSolicitudAfiliacionEmpleador())
//					.setParameter("numeroCustodia", datosRecepcionSolicitud.getNumeroCustodia()).executeUpdate();
				}
				/* Se actualiza el estado de la solicitud, segun corresponda.*/
				if (datosRecepcionSolicitud.getEstadoDocumento().equals(EstadoDocumentacionEnum.RECIBIDA_EN_EL_BACK)) {
					if (solEmpleador != null) {
						SolicitudAfiliacionEmpleador solicitudUpdate = entityManager.getReference(
								SolicitudAfiliacionEmpleador.class, 
								solEmpleador.getIdSolicitudAfiliacionEmpleador());
						solicitudUpdate.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.ASIGNADA_AL_BACK);
						entityManager.merge(solicitudUpdate);
						
//						entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_SOLICITUD)
//							.setParameter("estadoSolicitud", EstadoSolicitudAfiliacionEmpleadorEnum.ASIGNADA_AL_BACK)
//							.setParameter("idSolitudAfiliacionEmpleador", solEmpleador.getIdSolicitudAfiliacionEmpleador())
//							.executeUpdate();
					} else if (solPersona != null) {
						SolicitudAfiliacionPersona solicitudUpdate = entityManager.getReference(
								SolicitudAfiliacionPersona.class, 
								solPersona.getIdSolicitudAfiliacionPersona());
						solicitudUpdate.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.ASIGNADA_AL_BACK);
						entityManager.merge(solicitudUpdate);
						
//						entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_SOLICITUD_PERSONA)
//							.setParameter("estadoSolicitud", EstadoSolicitudAfiliacionPersonaEnum.ASIGNADA_AL_BACK)
//							.setParameter("idSolitudAfiliacionPersona", solPersona.getIdSolicitudAfiliacionPersona())
//							.executeUpdate();
					} else if (solNovedad != null) {
						SolicitudNovedad solicitudUpdate = entityManager.getReference(
								SolicitudNovedad.class, 
								solNovedad.getIdSolicitudNovedad());
						solicitudUpdate.setEstadoSolicitud(EstadoSolicitudNovedadEnum.ASIGNADA_AL_BACK);
						entityManager.merge(solicitudUpdate);
						
//						entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_SOLICITUD_NOVEDAD)
//						.setParameter("estadoSolicitud", EstadoSolicitudNovedadEnum.ASIGNADA_AL_BACK)
//						.setParameter("idSolicitudNovedad", solNovedad.getIdSolicitudNovedad())
//						.executeUpdate();
					} else if (solPostulacion != null) {
						SolicitudPostulacion solicitudUpdate = entityManager.getReference(
								SolicitudPostulacion.class, 
								solPostulacion.getIdSolicitudPostulacion());
						solicitudUpdate.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.ASIGNADA_AL_BACK);
						entityManager.merge(solicitudUpdate);
					} else if (solLegalizacion != null) {
                        SolicitudLegalizacionDesembolso solicitudUpdate = entityManager.getReference(
                                SolicitudLegalizacionDesembolso.class, 
                                solLegalizacion.getIdSolicitudLegalizacionDesembolso());
                        solicitudUpdate.setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum.LEGALIZACION_Y_DESEMBOLSO_ASIGNADO_AL_BACK);
                        entityManager.merge(solicitudUpdate);
                    }
					
				}
				// Se actualiza estadoDocumentacion en Solicitud
				//Se consultan las solicitudes a actualizar y se actualiza una por una para dejar traza de auditoria
				List<Solicitud> solicitudesUpdate = new ArrayList<>();
				solicitudesUpdate.addAll(entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_A_ACTUALIZAR)
					.setParameter("numeroRadicado", numeroRadicado).getResultList());
				
				for (Solicitud solicitud : solicitudesUpdate){
					solicitud = entityManager.merge(solicitud);
					solicitud.setEstadoDocumentacion(datosRecepcionSolicitud.getEstadoDocumento());
				}
				
//				entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_DOCUMENTACION_SOLICITUD)
//						.setParameter("numeroRadicado", numeroRadicado)
//						.setParameter("estadoDocumento", datosRecepcionSolicitud.getEstadoDocumento()).executeUpdate();
			}
			logger.debug("Finaliza registrarRecepcionSolicitud(String, RecepcionSolicitudDTO)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en el servicio registrarRecepcionSolicitud(String, RecepcionSolicitudDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		
	}
	
	@Override
    public String continuarTareaAfiEmpleador(UserDTO user) {
        logger.info("Inicia continuarTareaAfiEmpleador()");

        ObtenerTareaActivaInstancia obtenerTareaSrv = null;
        TerminarTarea terminarTareaSrv = null;
        EnviarSenal enviarSenalrsv = null;
        Map<String, Object> params = new HashMap<>();
        String nombreTarea = "Asignar solicitud afiliación empleador";
        int warnCount = 0;
        String numeroRadicado = "";

        try {
            List<Solicitud> solicitudes = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_AFIEMP_DOCREC_ASIGBACK, Solicitud.class).getResultList();

            for (Solicitud solicitud : solicitudes) {

                numeroRadicado = solicitud.getNumeroRadicacion();
                logger.info("Cerrando la tarea asociada al radicado " + numeroRadicado);

                obtenerTareaSrv = new ObtenerTareaActivaInstancia(Long.valueOf(solicitud.getIdInstanciaProceso()));
                obtenerTareaSrv.execute();

                TareaDTO tarea = obtenerTareaSrv.getResult();

                if (tarea != null && tarea.getNombre().equals(nombreTarea)) {
                    terminarTareaSrv = new TerminarTarea(tarea.getId(), params);
                    terminarTareaSrv.execute();
                    logger.info("Se cerró la tarea " + tarea.getId());

                    enviarSenalrsv = new EnviarSenal(ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL, "recepcionDocumentosSignal",
                            Long.valueOf(solicitud.getIdInstanciaProceso()), null);
                    enviarSenalrsv.execute();
                    logger.info("Se inició la tarea de VerificarSolicitud para la instancia " + solicitud.getIdInstanciaProceso());
                }
                else
                    logger.info("La instancia no tiene tareas activas" + solicitud.getIdInstanciaProceso());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            warnCount++;
            logger.warn("Ocurrió un error procesando la solicitud con radicado " + numeroRadicado, e);
        }
        logger.info("Finaliza continuarTareaAfiEmpleador()");
        return "Solicitudes procesadas con " + warnCount + " warn";
    }
}
