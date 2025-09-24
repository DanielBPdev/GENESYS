package com.asopagos.notificaciones.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.entidades.ccf.comunicados.DestinatarioGrupo;
import com.asopagos.entidades.ccf.comunicados.GrupoPrioridad;
import com.asopagos.entidades.ccf.comunicados.PrioridadDestinatario;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.jms.JMSUtilJboss;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.ConstansNotification;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.GrupoPrioridadDTO;
import com.asopagos.notificaciones.dto.GrupoRolPrioridadDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.enums.TipoNotificacionEnum;
import com.asopagos.notificaciones.service.NotificacionesService;
import com.asopagos.notificaciones.service.SendNotificacionesService;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.ValidacionCamposUtil;
import com.asopagos.dto.modelo.ResultadoEnvioComunicadoCarteraDTO;

/**
 * Servicio de norificaciones
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 */
/**
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
@Stateless
public class NotificacionesBusiness implements NotificacionesService {

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "notificaciones_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(NotificacionesBusiness.class);
    
    /**
     * Cantidad maxima de notificaciones a incluir en cada mensaje enviado a la cola
     */
    private final Integer NOTIFICACIONES_POR_MENSAJE = 100;

	@Inject
	private SendNotificacionesService sendNotificacionesService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.usuarios.service.NotificacionesService#enviarCorreo(com.
     * asopagos.notificaciones.dto.NotificacionDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    public void enviarCorreo(NotificacionDTO notificacion, UserDTO userDTO) {
        logger.debug("Inicia enviarCorreo(NotificacionDTO)");
        try {
            Boolean verificarArchivoAdjunto = (Boolean) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_ADJUNTO)
                    .setParameter("pcoEtiqueta", notificacion.getEtiquetaPlantilla()).getSingleResult();
            if(verificarArchivoAdjunto == Boolean.FALSE){
                notificacion.setArchivosAdjuntos(null);
                notificacion.setArchivosAdjuntosIds(null);
            }
            Map<String, Object> message = new HashMap<String, Object>();
            message.put(ConstansNotification.NOTIFICACION_PARAM, notificacion);
            message.put(ConstansNotification.USER_PARAM, userDTO);
            message.put(ConstansNotification.TIPO_NOTIFICACION_PARAMETRIZADA, TipoNotificacionEnum.SIMPLE);
            JMSUtilJboss.sendMenssages(message, ConstansNotification.QUEUE_MAILS);
        } catch (Exception e) {
            logger.error("Finaliza enviarCorreo(NotificacionDTO): Error inesperado");
            logger.debug("Finaliza enviarCorreo(NotificacionDTO): Error inesperado");
            throw new TechnicalException(e.getMessage());
        }
        logger.debug("Finaliza enviarCorreo(NotificacionDTO)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.notificaciones.service.NotificacionesService#
     * enviarCorreoParametrizado(com.asopagos.notificaciones.dto.
     * NotificacionParametrizadaDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
        try {
            Map<String, Object> message = new HashMap<String, Object>();
            message.put(ConstansNotification.NOTIFICACION_PARAM, notificacion);
            message.put(ConstansNotification.USER_PARAM, userDTO);
            message.put(ConstansNotification.TIPO_NOTIFICACION_PARAMETRIZADA, TipoNotificacionEnum.PARAMETRIZADA);
            JMSUtilJboss.sendMenssages(message, ConstansNotification.QUEUE_MAILS);
        } catch (Exception e) {
            logger.error("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
            logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
            throw new TechnicalException(e.getMessage());
        }
        logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
    }

    @Override
    public ResultadoEnvioComunicadoCarteraDTO EnviarCorreoParametrizadoCartera(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        logger.info("Inicia EnviarCorreoParametrizadoCartera(NotificacionParametrizadaDTO notificacion, UserDTO userDTO");
        ResultadoEnvioComunicadoCarteraDTO resultadoEnvioComunicadoCartera = new ResultadoEnvioComunicadoCarteraDTO();
        resultadoEnvioComunicadoCartera = sendNotificacionesService.enviarEmailImplementacionCartera(notificacion, userDTO);
        logger.info("Fin EnviarCorreoParametrizadoCartera(NotificacionParametrizadaDTO notificacion, UserDTO userDTO");
        return resultadoEnvioComunicadoCartera;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.notificaciones.service.NotificacionesService#enviarCorreoMasivo(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void enviarCorreoMasivo(List<NotificacionDTO> notificaciones, UserDTO userDTO) {
        logger.debug("Inicia enviarCorreoMasivo(List<NotificacionDTO>)");
        for (NotificacionDTO notificacionDTO : notificaciones) {
            this.enviarCorreo(notificacionDTO, userDTO);
        }
        logger.debug("Finaliza enviarCorreoMasivo(List<NotificacionDTO>)");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, Object> consultarArchivoAdjunto(EtiquetaPlantillaComunicadoEnum visualizarArchivoAdjunto){
        //Se consulta sobre la tabla PlantillaComunicado con etiquetaPlantillaComunicadoEnum 
        // y se recupera pcoEtiqueta y nombreQuery
        Map<String, Object> valorRetorno = new HashMap<>();
        try{
        Boolean tieneArchivoAdjunto = (Boolean) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_ADJUNTO)
                    .setParameter("pcoEtiqueta", visualizarArchivoAdjunto.name()).getSingleResult();
                    valorRetorno.put("pcoEtiqueta", tieneArchivoAdjunto);
        }catch (NoResultException e) {
            valorRetorno.put("pcoEtiqueta", "Sin archivo adjunto");
        }
        return valorRetorno;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.service.NotificacionesService#obtenerCorreosPrioridad(com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CorreoPrioridadPersonaDTO> obtenerCorreosPrioridad(NotificacionParametrizadaDTO notificacion, UserDTO userDTO){
        
        List<PrioridadDestinatario> listaPrioridadDestinatario = new ArrayList<PrioridadDestinatario>();
        NotificacionesUtil util = new NotificacionesUtil();
        List<CorreoPrioridadPersonaDTO> destinatarioEmail = new ArrayList<CorreoPrioridadPersonaDTO>();
        try {
            listaPrioridadDestinatario = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PRIORIDAD_ETIQUETA_PROCESO_DESTINATARIO_COMUNICADO,
                            PrioridadDestinatario.class)
                    .setParameter("proceso", notificacion.getProcesoEvento())
                    .setParameter("etiqueta", notificacion.getEtiquetaPlantillaComunicado()).getResultList();
            if(listaPrioridadDestinatario != null && !listaPrioridadDestinatario.isEmpty()){
                if (notificacion.getIdSolicitud()!=null) {          
                    if(!notificacion.getProcesoEvento().equals("VISTAS_360"))
                    {   
                        TipoTransaccionEnum tipoTx= entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_TRANSACCION,TipoTransaccionEnum.class)
                                .setParameter("idSolicitud", notificacion.getIdSolicitud()).getSingleResult();
                        for (PrioridadDestinatario prioridadDestinatario : listaPrioridadDestinatario) {
                            DestinatarioGrupo destinatarioGrupo = consultarDestinatarioPorGrupo(prioridadDestinatario.getIdGrupoPrioridad()).get(0);
                            Map<String, Object> parametros = new HashMap<String, Object>();
                            parametros.put("idSolicitud", notificacion.getIdSolicitud());
                            if (tipoTx!= null) {
                                List<CorreoPrioridadPersonaDTO> correos = util.obtenerDestinatario(destinatarioGrupo.getRolContacto(), tipoTx,
                                        parametros, entityManager);
                                if(correos!=null&&!correos.isEmpty()){
                                    boolean agregar = false;
                                    for(CorreoPrioridadPersonaDTO correo : correos) {
                                        if(correo.getEmail()!=null&&correo.getEmail()!=""||correo.getNoFormalizado()==Boolean.TRUE)
                                        {
                                            agregar = true;
                                            break;
                                        }
                                    }
                                    if(agregar) {
                                        destinatarioEmail.addAll(correos);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //GLPI 29357: Lógica para la obtención del correo principal del empleador
                    else{           
                        List<CorreoPrioridadPersonaDTO> listCorreoPrioridadPersonaDTO = new ArrayList<CorreoPrioridadPersonaDTO>();
                        listCorreoPrioridadPersonaDTO = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CORREO_PRIORIDAD_PERSONA, CorreoPrioridadPersonaDTO.class)
                        .setParameter("idCertificado", notificacion.getIdSolicitud())
                        .getResultList(); 
                        if (!listCorreoPrioridadPersonaDTO.isEmpty())
                        {
                            CorreoPrioridadPersonaDTO correoPrioridadPersonaDTO = listCorreoPrioridadPersonaDTO.get(0);
                            logger.info("correo:"+correoPrioridadPersonaDTO.getEmail());
                            if(correoPrioridadPersonaDTO.getEmail()!=null && correoPrioridadPersonaDTO.getEmail()!="" )//== Boolean.TRUE)
                            {
                                destinatarioEmail.add(correoPrioridadPersonaDTO);
                            }
                        }
                    }
                }
                else if(notificacion.getParametros()!=null 
                        && notificacion.getParametros().getNumeroIdentificacion()!=null
                        && notificacion.getParametros().getTipoIdentificacion()!=null
                        && !EtiquetaPlantillaComunicadoEnum.LIQ_APO_MAN.equals(notificacion.getEtiquetaPlantillaComunicado())){
                    
                    List<DestinatarioGrupo> listaDestinatariosGrupo = new ArrayList<>();
                    Map<String, Object> parametros = new HashMap<String, Object>();
                    
                    if (notificacion.getProcesoEvento().equals("RECAUDO_APORTES_PILA")) {
                        for (PrioridadDestinatario prioridad : listaPrioridadDestinatario) {
                            listaDestinatariosGrupo = entityManager
                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DESTINATARIO_POR_GRUPO, DestinatarioGrupo.class)
                                    .setParameter("grupoPrioridad", prioridad.getIdGrupoPrioridad()).getResultList();

                            parametros = new HashMap<String, Object>();
                            parametros.put("mapa", notificacion.getParametros());
                            parametros.put("idSolicitud", null);

                            List<CorreoPrioridadPersonaDTO> correos = util.obtenerDestinatario(
                                    listaDestinatariosGrupo.get(0).getRolContacto(), TipoTransaccionEnum.RECAUDO_APORTES_PILA, parametros,
                                    entityManager);
                            if (correos != null 
                                    && !correos.isEmpty()
                                    && correos.get(0).getEmail() != null
                                    && ValidacionCamposUtil.validarEmail(correos.get(0).getEmail())) {
                                destinatarioEmail.addAll(correos);
                                break;
                            }
                        }
                    }
                    else {
                        listaDestinatariosGrupo = entityManager
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DESTINATARIO_POR_GRUPO, DestinatarioGrupo.class)
                                .setParameter("grupoPrioridad", listaPrioridadDestinatario.get(0).getIdGrupoPrioridad()).getResultList();
                        for (DestinatarioGrupo destinatarioGrupo : listaDestinatariosGrupo) {

                            parametros.put("mapa", notificacion.getParametros());
                            parametros.put("idSolicitud", null);
                            List<CorreoPrioridadPersonaDTO> correos = util.obtenerDestinatario(destinatarioGrupo.getRolContacto(),
                                    TipoTransaccionEnum.CONVENIO_PAGO, parametros, entityManager);
                            if (correos != null && !correos.isEmpty()) {
                                destinatarioEmail.addAll(correos);
                            }
                        }
                    }
                }          
                else if(ProcesoEnum.GESTION_COBRO_MANUAL.name().equals(notificacion.getProcesoEvento()) && EtiquetaPlantillaComunicadoEnum.LIQ_APO_MAN.equals(notificacion.getEtiquetaPlantillaComunicado())){
                    List<DestinatarioGrupo> listaDestinatariosGrupo = entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_DESTINATARIO_POR_GRUPO, DestinatarioGrupo.class)
                            .setParameter("grupoPrioridad", listaPrioridadDestinatario.get(0).getIdGrupoPrioridad()).getResultList();
                    
                    for (DestinatarioGrupo destinatarioGrupo : listaDestinatariosGrupo) {
                        Map<String, Object> parametros = new HashMap<String, Object>();
                        parametros.put("mapa", notificacion.getParametros());
                        parametros.put("idSolicitud",0); // Valor dummy para que se pueda realizar la consulta en la línea siguiente 
                        List<CorreoPrioridadPersonaDTO> correos = util.obtenerDestinatario(destinatarioGrupo.getRolContacto(), TipoTransaccionEnum.CONVENIO_PAGO, parametros, entityManager);
                        
                        if(correos!=null&&!correos.isEmpty()){
                            destinatarioEmail.addAll(correos);    
                        }
                    }
                }
            }
        return destinatarioEmail;
        } catch (NoResultException e) {
            logger.error("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO): Aún no se ha realizado la parametrización de destinatarios para el envío de este comunicado en el proceso");
            logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO): Aún no se ha realizado la parametrización de destinatarios para el envío de este comunicado en el proceso");
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.service.NotificacionesService#obtenerGruposPrioridad(com.asopagos.enumeraciones.core.ProcesoEnum, com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<GrupoRolPrioridadDTO> obtenerGruposPrioridad(String proceso){
        logger.debug("Inicia obtenerGruposPrioridad(String)");
        
        List<GrupoRolPrioridadDTO> gruposRol = new ArrayList<GrupoRolPrioridadDTO>();
        
        try {
            List<EtiquetaPlantillaComunicadoEnum> etiquetas = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ETIQUETAS_POR_PROCESO,EtiquetaPlantillaComunicadoEnum.class)
                .setParameter("proceso", proceso).getResultList();
        
            for (EtiquetaPlantillaComunicadoEnum etiqueta: etiquetas) {
                List<PrioridadDestinatario> prioridadesDestinatarioTemporal = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PRIORIDAD_PROCESO_DESTINATARIO_COMUNICADO,PrioridadDestinatario.class)
                        .setParameter("proceso", proceso).setParameter("etiqueta", etiqueta).getResultList();
                List<GrupoPrioridadDTO> gruposPrioridadDTO= new ArrayList<GrupoPrioridadDTO>();
                for (PrioridadDestinatario prioridadDestinatarioTemporal : prioridadesDestinatarioTemporal) {
                    GrupoPrioridad grupoPrioridadBD = entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPO_PRIORIDAD, GrupoPrioridad.class)
                            .setParameter("idGrupoPrioridad", prioridadDestinatarioTemporal.getIdGrupoPrioridad()).getSingleResult();
                    GrupoPrioridadDTO grupoPrioridad = new GrupoPrioridadDTO();
                    grupoPrioridad.setIdGrupoPrioridad(grupoPrioridadBD.getIdGrupoPrioridad());
                    grupoPrioridad.setNombre(grupoPrioridadBD.getNombre());
                    grupoPrioridad.setPrioridad(prioridadDestinatarioTemporal.getPrioridad());
                    gruposPrioridadDTO.add(grupoPrioridad);
                }
                GrupoRolPrioridadDTO grupoRol = new GrupoRolPrioridadDTO();
                grupoRol.setEtiquetaComunicado(etiqueta);
                grupoRol.setListaGrupoPrioridadDTO(gruposPrioridadDTO);
                gruposRol.add(grupoRol);
            }
                        
        } catch (NoResultException e) {
            logger.error("Finaliza obtenerGruposPrioridad(String):No existen grupos prioridades parametrizados");
            logger.debug("Finaliza obtenerGruposPrioridad(String):No existen grupos prioridades parametrizados");
            return null;
        } catch (Exception e) {
            logger.error("Finaliza obtenerGruposPrioridad(String): Error inesperado");
            logger.debug("Finaliza obtenerGruposPrioridad(String): Error inesperado");
            throw new TechnicalException(e.getMessage());
        }
        logger.debug("Finaliza obtenerGruposPrioridad(String)");
        return gruposRol;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.service.NotificacionesService#actualizarGruposPrioridad(java.util.List,
     *      com.asopagos.enumeraciones.core.ProcesoEnum, com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum)
     */
    private void actualizarGruposPrioridad(String proceso, EtiquetaPlantillaComunicadoEnum etiqueta, List<GrupoPrioridadDTO> gruposPrioridadDTO){
        logger.debug("Inicia actualizarGruposPrioridad()");
        try {
            List<Long> idsGrupoPrioridad = new ArrayList<Long>();
            if(gruposPrioridadDTO==null||gruposPrioridadDTO.isEmpty()){
                entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_GRUPOS_PRIORIDAD_CERO_ETIQUETA_PROCESO)
                .setParameter("proceso", proceso).setParameter("etiqueta", etiqueta).executeUpdate();
            } else {
                for (GrupoPrioridadDTO grupoPrioridadDTO : gruposPrioridadDTO) {
                    idsGrupoPrioridad.add(grupoPrioridadDTO.getIdGrupoPrioridad());
                    entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_PRIORIDAD_ETIQUETA_PROCESO_DESTINATARIO_COMUNICADO)
                            .setParameter("proceso", proceso).setParameter("etiqueta", etiqueta)
                            .setParameter("prioridad", grupoPrioridadDTO.getPrioridad())
                            .setParameter("idGrupoPrioridad", grupoPrioridadDTO.getIdGrupoPrioridad()).executeUpdate();
                }
                
                entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_PRIORIDAD_CERO_ETIQUETA_PROCESO)
                .setParameter("proceso", proceso).setParameter("etiqueta", etiqueta)
                .setParameter("idsGrupoPrioridad", idsGrupoPrioridad).executeUpdate();
            }
        }  catch (NoResultException e) {
            logger.error("Finaliza obtenerGruposPrioridad(ProcesoEnum, EtiquetaPlantillaComunicadoEnum):No existen grupos prioridades parametrizados");
            logger.debug("Finaliza obtenerGruposPrioridad(ProcesoEnum, EtiquetaPlantillaComunicadoEnum):No existen grupos prioridades parametrizados");
        } catch (Exception e) {
            logger.error("Finaliza obtenerGruposPrioridad(ProcesoEnum, EtiquetaPlantillaComunicadoEnum):Error técnico inesperado");
            logger.debug("Finaliza obtenerGruposPrioridad(ProcesoEnum, EtiquetaPlantillaComunicadoEnum):Error técnico inesperado");
            throw new TechnicalException(e.getMessage());
        }
    }

    
    @Override
    public void actualizarGruposPrioridadLista(String proceso, List<GrupoRolPrioridadDTO> gruposPrioridadDTO) {
        logger.debug("Inicia actualizarGruposPrioridadLista(String proceso, List<GrupoRolPrioridadDTO> gruposPrioridadDTO)");
        try {
            for (GrupoRolPrioridadDTO grupoRolPrioridadDTO : gruposPrioridadDTO) {
                actualizarGruposPrioridad(proceso, grupoRolPrioridadDTO.getEtiquetaComunicado(),
                        grupoRolPrioridadDTO.getListaGrupoPrioridadDTO());
            }
        } catch (Exception e) {
            logger.error("Finaliza actualizarGruposPrioridadLista(String proceso, List<GrupoRolPrioridadDTO> gruposPrioridadDTO):Error técnico inesperado");
            logger.debug("Finaliza actualizarGruposPrioridadLista(String proceso, List<GrupoRolPrioridadDTO> gruposPrioridadDTO):Error técnico inesperado");
            throw new TechnicalException(e.getMessage());
        }
        
        
    }
    
    /* (non-Javadoc)
     * @see com.asopagos.notificaciones.service.NotificacionesService#enviarMultiplesCorreosPorConexion(java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO> notificaciones,
             UserDTO userDTO) {
        logger.debug("Inicia enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO>, UserDTO)");
        try {
//          sendNotificacionesService.enviarMultiplesCorreosPorConexion(notificaciones, userDTO);
            
            int count = 0;
            List<NotificacionParametrizadaDTO> notificacionesTemp = new ArrayList<NotificacionParametrizadaDTO>();
            for( NotificacionParametrizadaDTO notificacion : notificaciones) {
                count ++;
                notificacionesTemp.add(notificacion);
                //Se construye el mensaje a enviar con determinado numero de notificaciones 
                logger.debug("NOTIFICACIONES POR MENSAJE : " + NOTIFICACIONES_POR_MENSAJE);
                if(count == NOTIFICACIONES_POR_MENSAJE) {
                    Map<String, Object> message = new HashMap<String, Object>();
                    message.put(ConstansNotification.NOTIFICACION_PARAM, notificacionesTemp);
                    message.put(ConstansNotification.USER_PARAM, userDTO);
                    message.put(ConstansNotification.TIPO_NOTIFICACION_PARAMETRIZADA, TipoNotificacionEnum.MULTIPLE_POR_CONEXION);
                    JMSUtilJboss.sendMenssages(message, ConstansNotification.QUEUE_MAILS);
                    count = 0;
                    notificacionesTemp = new ArrayList<NotificacionParametrizadaDTO>();
                }
            }
            if (count > 0) {
                Map<String, Object> message = new HashMap<String, Object>();
                message.put(ConstansNotification.NOTIFICACION_PARAM, notificacionesTemp);
                message.put(ConstansNotification.USER_PARAM, userDTO);
                message.put(ConstansNotification.TIPO_NOTIFICACION_PARAMETRIZADA, TipoNotificacionEnum.MULTIPLE_POR_CONEXION);
                JMSUtilJboss.sendMenssages(message, ConstansNotification.QUEUE_MAILS);
            }
            
        } catch (Exception e) {
            logger.error("Finaliza enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO>, UserDTO)");
            logger.debug("Finaliza enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO>, UserDTO)");
            throw new TechnicalException(e.getMessage());
        }
        logger.debug("Finaliza enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO>, UserDTO)");
    }

    @Override
    public TipoTransaccionEnum buscarTipoTransaccionSolicitud(Long idSolicitud) {
        String firma = "buscarTipoTransaccionSolicitud(Long)";
        logger.debug("inicio "+firma);
        try {
            List<String> tipoTx = (List<String>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_TX_POR_SOLICITUD)
                    .setParameter("idSolicitud", idSolicitud)
                    .getResultList();

            logger.debug("fin "+firma);
            return TipoTransaccionEnum.valueOf(tipoTx.get(0));
            
        } catch (Exception e) {
            logger.debug("fin "+firma);
            throw new TechnicalException(e.getMessage());
        }
    }
    
    /**
     * Método encargado de consultar un destinatario por el id de su grupo de prioridad
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param grupoPrioridad id del grupo de prioridad
     * @return lista de destinatarios
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DestinatarioGrupo> consultarDestinatarioPorGrupo(Long grupoPrioridad){
    	logger.debug("Inicia consultarDestinatarioPorGrupo(Long grupoPrioridad)");
    	List<DestinatarioGrupo> destinariosGrupo = new ArrayList<>();
        try {
        	destinariosGrupo = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_DESTINATARIO_POR_GRUPO, DestinatarioGrupo.class)
                    .setParameter("grupoPrioridad", grupoPrioridad).getResultList();
        } catch (Exception e) {
            logger.error("consultarDestinatarioPorGrupo(Long grupoPrioridad): Error técnico inesperado");
        }
        logger.debug("Finaliza consultarDestinatarioPorGrupo(Long grupoPrioridad)");
        return destinariosGrupo;
    }
}
