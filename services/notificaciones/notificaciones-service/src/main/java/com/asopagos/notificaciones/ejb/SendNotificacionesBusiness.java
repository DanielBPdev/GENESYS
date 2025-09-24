package com.asopagos.notificaciones.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.cache.CacheManager;
import com.asopagos.comunicados.clients.ActualizarCertificado;
import com.asopagos.comunicados.clients.ConstruirPersistirComunicado;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.comunicados.Comunicado;
import com.asopagos.entidades.ccf.comunicados.DestinatarioGrupo;
import com.asopagos.entidades.ccf.comunicados.DetalleComunicadoEnviado;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.comunicados.PrioridadDestinatario;
import com.asopagos.entidades.ccf.comunicados.VariableComunicado;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.notificaciones.NotificacionDestinatario;
import com.asopagos.entidades.transversal.notificaciones.NotificacionEnviada;
import com.asopagos.enumeraciones.comunicados.EstadoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.comunicados.MedioComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.notificaciones.EstadoEnvioNotificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.business.interfaces.IConsultasModeloCore;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.ComunicadoPersistenciaDTO;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.enums.EnvioComunicadoProcesoEnum;
import com.asopagos.notificaciones.envio.EnvioComunicado;
import com.asopagos.notificaciones.envio.EnvioComunicadoFactory;
import com.asopagos.notificaciones.service.SendNotificacionesService;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.ValidacionCamposUtil;
import com.asopagos.entidades.transversal.core.ModuloPlantillaComunicado;
import com.asopagos.entidades.transversal.core.ComunicadoNiyaraky;
import java.util.Date;
import org.jose4j.json.JsonUtil;
import com.asopagos.dto.modelo.ResultadoEnvioComunicadoCarteraDTO;

/**
 * Servicio de norificaciones comunes y de persistencia
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
@Stateless
public class SendNotificacionesBusiness implements SendNotificacionesService {

	/**
	 * Referencia a la unidad de persistencia del servicio
	 */
	@PersistenceContext(unitName = "notificaciones_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(SendNotificacionesBusiness.class);
	
	/**
	 * Se injecta ejb con lógica de persistencia
	 */
	@Inject
	private IConsultasModeloCore consultasModeloCore;

    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.service.SendNotificacionesService#enviarEmail(com.asopagos.notificaciones.dto.NotificacionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
	public NotificacionEnviada enviarEmail(NotificacionDTO notificacion, UserDTO userDTO) {
		return enviarEmailImplementacion(notificacion, userDTO, null);
	}
	
    /**
     * (non-Javadoc)
     * @see com.asopagos.notificaciones.service.SendNotificacionesService#enviarEmailParametrizado(com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
	public void enviarEmailParametrizado(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        enviarCorreoParametrizado(notificacion, userDTO);
    }
	
	
	/**
	 * (non-Javadoc)
	 * @see com.asopagos.notificaciones.service.SendNotificacionesService#enviarMultiplesCorreosPorConexion(java.util.List, com.asopagos.rest.security.dto.UserDTO)
	 */
	public void enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO> notificaciones, UserDTO userDTO) {
		enviarMultiplesCorreosPorConexionImpl(notificaciones, userDTO);
    }

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.notificaciones.service.SendNotificacionesService#enviarEmail
	 *      (com.asopagos.notificaciones.dto.NotificacionDTO,
	 *      com.asopagos.rest.security.dto.UserDTO,
	 *      com.asopagos.entidades.ccf.comunicados.PlantillaComunicado)
	 */
	public NotificacionEnviada enviarEmail(NotificacionDTO notificacion, UserDTO userDTO, PlantillaComunicado plantilla,
			List<PrioridadDestinatario> listaPrioridadDestinatario) {
		logger.debug("Inicia enviarCorreo(NotificacionDTO)");
		
		Boolean enviadoCola = false;

		NotificacionEnviada notEnv = new NotificacionEnviada();
		NotificacionesUtil util = new NotificacionesUtil();

		List<NotificacionEnviada> notificacionEnviadaPrioridades = new ArrayList<>();
        EnvioComunicadoProcesoEnum envioProceso = EnvioComunicadoProcesoEnum.obtenerEnumeracionPorProceso(notificacion.getProcesoEvento());
        
        if (notificacion.getTipoTx() == null
                && Boolean.TRUE.equals(notificacion.getComunicadoEditado())) {
            TipoTransaccionEnum tipoTx= entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_TRANSACCION,TipoTransaccionEnum.class)
                    .setParameter("idSolicitud", notificacion.getIdSolicitud()).getSingleResult();
            notificacion.setTipoTx(tipoTx);
        }
        if (notificacion.getDestinatarioTO() == null) {
            notificacion.setDestinatarioTO(new ArrayList<String>());
        }
		
		if (!notificacion.isReplantearDestinatarioTO()) {
			// se valida si llegan prioridades para replantear al destinatario
			if (listaPrioridadDestinatario != null && !listaPrioridadDestinatario.isEmpty()) {
			    for (PrioridadDestinatario prioridadDestinatario : listaPrioridadDestinatario) {
					// select
					List<DestinatarioGrupo> listaDestinatariosGrupo = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESTINATARIO_POR_GRUPO,
									DestinatarioGrupo.class)
							.setParameter("grupoPrioridad", prioridadDestinatario.getIdGrupoPrioridad())
							.getResultList();
					List<NotificacionEnviada> notificaciones = new ArrayList<NotificacionEnviada>();
					for (DestinatarioGrupo destinatarioGrupo : listaDestinatariosGrupo) {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("idSolicitud", notificacion.getIdSolicitud());
						parametros.put("mapa", notificacion.getParametros());
						List<CorreoPrioridadPersonaDTO> destinatarioEmail = null;
						
						if (notificacion.getTipoTx() != null) {
						    destinatarioEmail = util.obtenerDestinatario(destinatarioGrupo.getRolContacto(),
	                                notificacion.getTipoTx(), parametros, entityManager);
						}
						if (destinatarioEmail != null
						        && !destinatarioEmail.isEmpty()
						        && emailValido(destinatarioEmail.get(0).getEmail())) {
    						for (CorreoPrioridadPersonaDTO destinatario : destinatarioEmail) {
    						    if(!notificacion.getDestinatarioTO().contains(destinatario.getEmail())){
    						        notificacion.getDestinatarioTO().add(destinatario.getEmail());
    						    }
    	                        notificacion.setAutorizaEnvio(destinatario.getAutorizaEnvio() != null ? destinatario.getAutorizaEnvio() : Boolean.TRUE);
    	                        notificacion.setIdPersona(destinatario.getIdPersona());
    	                        notificacion.setIdEmpleador(destinatario.getIdEmpleador() != null ? destinatario.getIdEmpleador() : null);
    	                        notificacion.setIdEmpresa(destinatario.getIdEmpresa() != null ? destinatario.getIdEmpresa() : null);
    	                        notEnv = enviarEmailImplementacion(notificacion, userDTO, plantilla);
    	                        notificaciones.add(notEnv);   
    	                        notificacionEnviadaPrioridades.add(notEnv);
    	                        enviadoCola = true;
    	                        //Se reinicia la lista de destinatarios a fin de no repetir envios en las alertas de BPM
    	                        //Considerando que tecnicamente este tipo de notificaciones tienen mas de un rol asociado
    	                        //a la primera prioridad
    	                        notificacion.setDestinatarioTO(new ArrayList<>());
                            }
					    }
					}
					for (NotificacionEnviada notificacionActual : notificaciones) {
                        if (!EstadoEnvioNotificacionEnum.FALLIDA.equals(notificacionActual.getEstadoEnvioNot())) {
                            notEnv.setEstadoEnvioNot(notificacionActual.getEstadoEnvioNot());
                            break;
                        }
                    }
					if (!notificaciones.isEmpty()&&!EstadoEnvioNotificacionEnum.FALLIDA.equals(notEnv.getEstadoEnvioNot())) {
                        break;
                    }
				}
			} else {
				notEnv = enviarEmailImplementacion(notificacion, userDTO, plantilla);
				enviadoCola = true;
			}
		} else {
			notEnv = enviarEmailImplementacion(notificacion, userDTO, plantilla);
			enviadoCola = true;
		}
		
		//Verifica si no se ha enviado el mensaje a la cola (envío a destinatarios opcionales y/o en su defecto crear la traza del comunicado)
		if(!enviadoCola){
		    notEnv = enviarEmailImplementacion(notificacion, userDTO, plantilla);
		}
		
		if (envioProceso!=null && envioProceso.getValidarProceso() && notificacionEnviadaPrioridades!=null && !notificacionEnviadaPrioridades.isEmpty()){
            EstadoEnvioNotificacionEnum estadoEnvios = EstadoEnvioNotificacionEnum.ENVIADA;
            for (NotificacionEnviada notificacionEnviada : notificacionEnviadaPrioridades) {
                if(EstadoEnvioNotificacionEnum.FALLIDA.equals(notificacionEnviada.getEstadoEnvioNot())){
                    estadoEnvios = EstadoEnvioNotificacionEnum.FALLIDA;
                    break;
                }
            }
            procesoPostEnvio(notificacion, estadoEnvios);
        }else if(envioProceso!=null && envioProceso.getValidarProceso()&&notificacion.isReplantearDestinatarioTO()){
            procesoPostEnvio(notificacion, notEnv.getEstadoEnvioNot());
        }
		logger.debug("Finaliza enviarCorreo(NotificacionDTO)");
		return notEnv;
	}

	/**
	 * Método que envía la notificación y crea le log de la ejecución el la base
	 * de datos
	 * 
	 * @param notificacion
	 * @param userDTO
	 * @param plantilla
	 * @param prioridades
	 * @return
	 */
	private NotificacionEnviada enviarEmailImplementacion(NotificacionDTO notificacion, UserDTO userDTO,
			PlantillaComunicado plantilla) {
        logger.info("Inicia enviarEmailImplementacion(NotificacionDTO, UserDTO, PlantillaComunicado)");
        NotificacionEnviada notEnv = new NotificacionEnviada();
        Comunicado comunicado = new Comunicado();
		String numeroIdentificacionEmpleador = null;
        notEnv.setFechaEnvio(Calendar.getInstance().getTime());
        notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIADA);
        notEnv.setIdSedeCajaCompensacion(
                userDTO.getSedeCajaCompensacion() != null ? Long.valueOf(userDTO.getSedeCajaCompensacion()) : null);
        notEnv.setProcesoEvento(notificacion.getProcesoEvento());
        notEnv.setRemitente(notificacion.getRemitente());

		// Si no se tiene la plantilla de antemano, la buscamos mediante la etiqueta (EJ: NTF_PAG_DVL_APT) 
        if(plantilla==null&&notificacion.getEtiquetaPlantilla()!=null){
            plantilla = consultasModeloCore.consultarPlantillaComunicado(notificacion.getEtiquetaPlantilla());
        }

		//logger.info("=====================");
		//logger.info(notificacion.toString());
		//logger.info(plantilla != null ? plantilla.toString() : "Plantilla no encontrada");
		//logger.info("=====================");

        //Si el comunicado corresponde al cierre de la solicitud de aportes se invoca un servicio que
        //crea una nueva transacción para guardar el comunicado, a fin de garantizar que exista en el
        //momento en que se actualiza el registro en la tabla RegistroEstadoAporte (proceso postenvio)
        if (plantilla != null && EtiquetaPlantillaComunicadoEnum.NTF_PAG_DVL_APT.equals(plantilla.getEtiqueta())){
            NotificacionParametrizadaDTO notifiP = (NotificacionParametrizadaDTO) notificacion;
            ComunicadoPersistenciaDTO comunicadoPersistencia = new ComunicadoPersistenciaDTO(notifiP, notEnv, plantilla);
            
            ConstruirPersistirComunicado comunicadoSrv = new ConstruirPersistirComunicado(comunicadoPersistencia);
            comunicadoSrv.execute();
            comunicado = comunicadoSrv.getResult();
        }else {
			if(EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_UNO_PRE_PAG_TRA.equals(plantilla.getEtiqueta()) ||
			EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_DOS_PRE_PAG_TRA.equals(plantilla.getEtiqueta()) ||
			EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_TRES_PRE_PAG_TRA.equals(plantilla.getEtiqueta()) )
			{
				notificacion.setIdSolicitud(null);
			}
            comunicado = persistirComunicado(notEnv, notificacion, userDTO, plantilla);
        }
        
		// Consulta dinámica del módulo al que pertenece la plantilla
        ModuloPlantillaComunicado moduloPlantilla = obtenerModuloDePlantilla(plantilla.getIdPlantillaComunicado());

        try {
            /*Verifica si desde la pantalla se realizá o no el envio del comunicado,
              a fin de persistir la relacion comunicado - solicitud para posterior consulta
              por vista 360*/
            if (notificacion.getRequiereEnviarse() != null && !notificacion.getRequiereEnviarse()) {
                //Actualiza la notificación con esrado ENVIO_CANCELADO
                notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIO_CANCELADO);
                //Actualizar comunicado con estado NO_ENVIADO
                actualizarComunicado(notEnv,comunicado);
                logger.warn("Envío cancelado desde pantalla");
            }else if (notificacion.getAutorizaEnvio() != null && notificacion.getAutorizaEnvio().equals(Boolean.FALSE)){
                //Actualiza la notificacion con estado NO_AUTORIZA_ENVIO
                notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.NO_AUTORIZA_ENVIO);
                //Actualiza el comunicado con estado NO_AUTORIZA_ENVIO
                actualizarComunicado(notEnv,comunicado);
                logger.warn("La entidad no autoriza el envío de comunicados");
			} else if (!validarEnvioPorModulo(moduloPlantilla)) {
                notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIO_INACTIVO);
                //Actualiza el comunicado con estado NO_AUTORIZA_ENVIO
                actualizarComunicado(notEnv,comunicado); 
                logger.warn("La conexión al servidor de correo se encuentra desactivada envioPorModulo");
            }else{
                NotificacionesUtil not = new NotificacionesUtil();
				Map<String,Object> retorno = null;
				// Si el correo es certificado por Nirayakys, se envia a ellos
				// Caso contrario se envia por AWS 
				if (moduloPlantilla != null && Boolean.TRUE.equals(moduloPlantilla.isCertificacionComunicado() != null ? moduloPlantilla.isCertificacionComunicado() : false)) {					
					// Realiza el envio al proveedor
					ComunicadoNiyaraky comNiya = new ComunicadoNiyaraky();

					/* Consulta el NIT de la empresa con el fin de generar un asunto único
					* Necesario para que la validación de Niyaraky no genere el error de 
					* Mensaje repetido (causado por la llave unica de asunto;destinatario) en un plazo de tiempo corto */
					if(notificacion.getIdEmpleador() != null){
						try{
							logger.warn("Entra a validar el empleador comunicados");
							numeroIdentificacionEmpleador = (String) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_NUMERO_IDENTIFICACION_EMPRESA_CARTERA)
							.setParameter("idEmpleador", notificacion.getIdEmpleador()).getSingleResult();
							logger.warn(numeroIdentificacionEmpleador);
							notificacion.setNumeroIdentificacionEmpleador(numeroIdentificacionEmpleador);
						}catch(NoResultException nre){
							logger.debug(nre);
						}
					}

					retorno = JsonUtil.parseJson(not.enviarCorreoNiyarakyUtil(notificacion));
					// Persistencia en la tabla ComunicadoNiyaraky
					comNiya.setComunicado(comunicado.getIdComunicado() != null ? comunicado.getIdComunicado() : null);
					comNiya.setIdMensaje(retorno.get("idMensaje") != null ? Long.valueOf(retorno.get("idMensaje").toString()) : null);
					comNiya.setObservacion((String) retorno.get("Observacion") != null ? (String) retorno.get("Observacion") : null);
					comNiya.setFechaEnvio(new Date());
					persistirEnvioNiyaraky(comNiya);
				}else{
					logger.warn("Entra a AWS");
					not.enviarCorreoUtil(notificacion);
				}
            }
        } catch (TechnicalException e) {
            logger.error("Finaliza enviarEmailImplementacion(NotificacionDTO, UserDTO, PlantillaComunicado): Error Técnico", e.getCause());
            notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.FALLIDA);
            //actualizar comunicado con estado fallido
            actualizarComunicado(notEnv,comunicado);
            notEnv.setError(e.getCause().getMessage());
        } catch (Exception e) {
            logger.error("Finaliza enviarEmailImplementacion(NotificacionDTO, UserDTO, PlantillaComunicado): Error inesperado", e);
            notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.FALLIDA);
            //actualizar comunicado con estado fallido
            actualizarComunicado(notEnv,comunicado);
            notEnv.setError(e.getMessage());
        }
        EnvioComunicadoProcesoEnum envioProceso = EnvioComunicadoProcesoEnum.obtenerEnumeracionPorProceso(notificacion.getProcesoEvento());
        persistirEnvio(notEnv, notificacion);
		
        //no se realiza proceso de postEnvio en caso de ser una afiliación exitosa del empleador
        if (envioProceso !=null 
                && notificacion.getParams() != null
                && EnvioComunicadoProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL.equals(envioProceso)
                && !notificacion.getParams().containsKey("idIntentoAfiliacion")) {
            envioProceso = null;
        }
        if (envioProceso != null && !envioProceso.getValidarProceso()){
            notificacion.setIdComunicado(comunicado.getIdComunicado() != null ? comunicado.getIdComunicado() : null);
            procesoPostEnvio(notificacion, notEnv.getEstadoEnvioNot());    
        }
        logger.debug("Finaliza enviarEmailImplementacion(NotificacionDTO, UserDTO, PlantillaComunicado)");
        return notEnv;
    }
	
	@Override
	public ResultadoEnvioComunicadoCarteraDTO enviarEmailImplementacionCartera(NotificacionDTO notificacion, UserDTO userDTO) {
        logger.info("Inicia enviarEmailImplementacionCartera(NotificacionDTO, UserDTO, PlantillaComunicado)");
        NotificacionEnviada notEnv = new NotificacionEnviada();
		PlantillaComunicado plantilla = null;
		String numeroIdentificacionEmpleador = null;
		ResultadoEnvioComunicadoCarteraDTO resultadoEnvioComunicadoCartera = new ResultadoEnvioComunicadoCarteraDTO();
        Comunicado comunicado = new Comunicado();
        notEnv.setFechaEnvio(Calendar.getInstance().getTime());
        notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIADA);
        notEnv.setIdSedeCajaCompensacion(
                userDTO.getSedeCajaCompensacion() != null ? Long.valueOf(userDTO.getSedeCajaCompensacion()) : null);
        notEnv.setProcesoEvento(notificacion.getProcesoEvento());
        notEnv.setRemitente(notificacion.getRemitente());

		// Si no se tiene la plantilla de antemano, la buscamos mediante la etiqueta (EJ: NTF_PAG_DVL_APT) 
        if(plantilla==null&&notificacion.getEtiquetaPlantilla()!=null){
            plantilla = consultasModeloCore.consultarPlantillaComunicado(notificacion.getEtiquetaPlantilla());
        }

		logger.info("=====================");
		logger.info(notificacion.toString());
		logger.info(plantilla != null ? plantilla.toString() : "Plantilla no encontrada");
		logger.info("=====================");

        //Si el comunicado corresponde al cierre de la solicitud de aportes se invoca un servicio que
        //crea una nueva transacción para guardar el comunicado, a fin de garantizar que exista en el
        //momento en que se actualiza el registro en la tabla RegistroEstadoAporte (proceso postenvio)
        if (plantilla != null && EtiquetaPlantillaComunicadoEnum.NTF_PAG_DVL_APT.equals(plantilla.getEtiqueta())){
            NotificacionParametrizadaDTO notifiP = (NotificacionParametrizadaDTO) notificacion;
            ComunicadoPersistenciaDTO comunicadoPersistencia = new ComunicadoPersistenciaDTO(notifiP, notEnv, plantilla);
            
            ConstruirPersistirComunicado comunicadoSrv = new ConstruirPersistirComunicado(comunicadoPersistencia);
            comunicadoSrv.execute();
            comunicado = comunicadoSrv.getResult();
        }else {
			if(EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_UNO_PRE_PAG_TRA.equals(plantilla.getEtiqueta()) ||
			EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_DOS_PRE_PAG_TRA.equals(plantilla.getEtiqueta()) ||
			EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_TRES_PRE_PAG_TRA.equals(plantilla.getEtiqueta()) )
			{
				notificacion.setIdSolicitud(null);
			}
            comunicado = persistirComunicado(notEnv, notificacion, userDTO, plantilla);
        }
        
		// Consulta dinámica del módulo al que pertenece la plantilla
        ModuloPlantillaComunicado moduloPlantilla = obtenerModuloDePlantilla(plantilla.getIdPlantillaComunicado());

		resultadoEnvioComunicadoCartera.setIdComunicado(comunicado.getIdComunicado() != null ? comunicado.getIdComunicado() : null);
        try {
            /*Verifica si desde la pantalla se realizá o no el envio del comunicado,
              a fin de persistir la relacion comunicado - solicitud para posterior consulta
              por vista 360*/
            if (notificacion.getRequiereEnviarse() != null && !notificacion.getRequiereEnviarse()) {
                //Actualiza la notificación con esrado ENVIO_CANCELADO
                notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIO_CANCELADO);
                //Actualizar comunicado con estado NO_ENVIADO
                actualizarComunicado(notEnv,comunicado);
				resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
                logger.warn("Envío cancelado desde pantalla");
            }else if (notificacion.getAutorizaEnvio() != null && notificacion.getAutorizaEnvio().equals(Boolean.FALSE)){
                //Actualiza la notificacion con estado NO_AUTORIZA_ENVIO
                notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.NO_AUTORIZA_ENVIO);
                //Actualiza el comunicado con estado NO_AUTORIZA_ENVIO
                actualizarComunicado(notEnv,comunicado);
				resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
                logger.warn("La entidad no autoriza el envío de comunicados");
			} else if (!validarEnvioPorModulo(moduloPlantilla)) {
                notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIO_INACTIVO);
                //Actualiza el comunicado con estado NO_AUTORIZA_ENVIO
                actualizarComunicado(notEnv,comunicado); 
				resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
                logger.warn("La conexión al servidor de correo se encuentra desactivada envioPorModulo");
            }else{
                NotificacionesUtil not = new NotificacionesUtil();
				Map<String,Object> retorno = null;
				// Si el correo es certificado por Nirayakys, se envia a ellos
				// Caso contrario se envia por AWS 
				if (moduloPlantilla != null && Boolean.TRUE.equals(moduloPlantilla.isCertificacionComunicado() != null ? moduloPlantilla.isCertificacionComunicado() : false)) {					
					// Realiza el envio al proveedor
					ComunicadoNiyaraky comNiya = new ComunicadoNiyaraky();

					/* Consulta el NIT de la empresa con el fin de generar un asunto único
					* Necesario para que la validación de Niyaraky no genere el error de 
					* Mensaje repetido (causado por la llave unica de asunto;destinatario) en un plazo de tiempo corto */
					if(notificacion.getIdEmpleador() != null){
						try{
							logger.warn("Entra a validar el empleador comunicados");
							numeroIdentificacionEmpleador = (String) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_NUMERO_IDENTIFICACION_EMPRESA_CARTERA)
							.setParameter("idEmpleador", notificacion.getIdEmpleador()).getSingleResult();
							logger.warn(numeroIdentificacionEmpleador);
							notificacion.setNumeroIdentificacionEmpleador(numeroIdentificacionEmpleador);
						}catch(NoResultException nre){
							logger.debug(nre);
						}
					}

					retorno = JsonUtil.parseJson(not.enviarCorreoNiyarakyUtil(notificacion));
					// Persistencia en la tabla ComunicadoNiyaraky
					comNiya.setComunicado(comunicado.getIdComunicado() != null ? comunicado.getIdComunicado() : null);
					comNiya.setIdMensaje(retorno.get("idMensaje") != null ? Long.valueOf(retorno.get("idMensaje").toString()) : null);
					comNiya.setObservacion((String) retorno.get("Observacion") != null ? (String) retorno.get("Observacion") : null);
					comNiya.setFechaEnvio(new Date());
					resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(true);
					persistirEnvioNiyaraky(comNiya);
				}else{
					logger.warn("Entra a AWS");
					resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(true);
					not.enviarCorreoUtil(notificacion);
				}
            }
        } catch (TechnicalException e) {
            logger.error("Finaliza enviarEmailImplementacion(NotificacionDTO, UserDTO, PlantillaComunicado): Error Técnico", e.getCause());
            notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.FALLIDA);
			resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
            //actualizar comunicado con estado fallido
            actualizarComunicado(notEnv,comunicado);
            notEnv.setError(e.getCause().getMessage());
        } catch (Exception e) {
            logger.error("Finaliza enviarEmailImplementacion(NotificacionDTO, UserDTO, PlantillaComunicado): Error inesperado", e);
            notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.FALLIDA);
			resultadoEnvioComunicadoCartera.setEnvioComunicadosResultado(false);
            //actualizar comunicado con estado fallido
            actualizarComunicado(notEnv,comunicado);
            notEnv.setError(e.getMessage());
        }
        EnvioComunicadoProcesoEnum envioProceso = EnvioComunicadoProcesoEnum.obtenerEnumeracionPorProceso(notificacion.getProcesoEvento());
        persistirEnvio(notEnv, notificacion);
		
        //no se realiza proceso de postEnvio en caso de ser una afiliación exitosa del empleador
        if (envioProceso !=null 
                && notificacion.getParams() != null
                && EnvioComunicadoProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL.equals(envioProceso)
                && !notificacion.getParams().containsKey("idIntentoAfiliacion")) {
            envioProceso = null;
        }
        if (envioProceso != null && !envioProceso.getValidarProceso()){
            notificacion.setIdComunicado(comunicado.getIdComunicado() != null ? comunicado.getIdComunicado() : null);
            procesoPostEnvio(notificacion, notEnv.getEstadoEnvioNot());    
        }
        logger.debug("Finaliza enviarEmailImplementacionCartera(NotificacionDTO, UserDTO, PlantillaComunicado)");
        return resultadoEnvioComunicadoCartera;
    }

	private ComunicadoNiyaraky persistirEnvioNiyaraky(ComunicadoNiyaraky comNiya) {
		logger.debug("Inicia persistirEnvioNiyaraky(ComunicadoNiyaraky comNiya)");
		try {
		    entityManager.persist(comNiya);
        } catch (Exception e) {
            logger.error("No es posible persistir el envio", e);
            logger.debug("Finaliza persistirEnvio(NotificacionEnviada, NotificacionDTO) ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
		logger.debug("Finaliza persistirEnvioNiyaraky(ComunicadoNiyaraky comNiya)");
		return comNiya;
	}

	// Metodo para obtener el modulo de una plantilla de forma dinámica
	private ModuloPlantillaComunicado obtenerModuloDePlantilla(Long idPlantillaComunicado) {
		try {
			return consultasModeloCore.obtenerModuloDePlantilla(idPlantillaComunicado);
		} catch (Exception e) {
			logger.error("Error al consultar el modulo de la plantilla: " + idPlantillaComunicado, e);
			ModuloPlantillaComunicado moduloPorDefecto = new ModuloPlantillaComunicado();
			moduloPorDefecto.setModulo("DEFAULT");
			return moduloPorDefecto;
		}
	}

	private boolean validarEnvioPorModulo(ModuloPlantillaComunicado moduloPlantilla) {
		boolean envioCorreosActivo = Boolean.parseBoolean((String) CacheManager.getParametro("ENVIO_CORREOS_ACTIVO"));

		if (moduloPlantilla.getModulo().equalsIgnoreCase("DEFAULT")) {
			return envioCorreosActivo;
		}

		boolean envioModuloActivo = Boolean.parseBoolean((String) CacheManager.getParametro("ENVIO_COMUNICADOS_" + moduloPlantilla.getModulo().toUpperCase()));

		// Reglas generales aplicables

		// Si ambos parámetros son false, no se envía ningún comunicado
		if (!envioCorreosActivo && !envioModuloActivo) {
			logger.warn("El envio de comunicados esta desactivado");
			return false;
		}
			
		// Si el envío global está activo pero el envío del módulo está desactivado, no se envía
		if (envioCorreosActivo && !envioModuloActivo) {
			logger.warn("El envío para el módulo " + moduloPlantilla.getModulo() + " está desactivado");
			return false;
		}

		// Si el envío global está desactivado pero el del módulo está activo, se permite solo para ese módulo
		if (!envioCorreosActivo && envioModuloActivo) {
			logger.info("El envío está permitido solo para el módulo " + moduloPlantilla.getModulo());
			return true;
		}

		// Si todas las condiciones se cumplen, se permite el envío
		return true;
	}
	
	private void procesoPostEnvio(NotificacionDTO notificacion, EstadoEnvioNotificacionEnum estadoEnvioNot) {
	    EnvioComunicado envioComunicado = EnvioComunicadoFactory.getInstance(notificacion.getProcesoEvento(), estadoEnvioNot);
	    if(envioComunicado != null){
	        envioComunicado.procesar(notificacion);    
	    }
    } 

	/**
	 * Método que se encarga de peristir la información del comunicado.
	 * 
	 * @param notificacion
	 * @return
	 */
	private Comunicado persistirComunicado(NotificacionEnviada notEnv, NotificacionDTO notificacion, UserDTO userDTO,
			PlantillaComunicado plantilla) {
		logger.debug("Inicia persistirComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado)");
		
		Comunicado comunicado = new Comunicado();
		comunicado.setNumeroCorreoMasivo(notEnv.getIdEnvioNot());
		comunicado.setMedioComunicado(MedioComunicadoEnum.ENVIADO);
		comunicado.setRemitente(userDTO.getNombreUsuario());
		comunicado.setSedeCajaCompensacion(userDTO.getSedeCajaCompensacion());
		comunicado.setIdSolicitud(notificacion.getIdSolicitud());
		comunicado.setEmpleador(notificacion.getIdEmpleador() != null ? notificacion.getIdEmpleador() : null);
		comunicado.setEmpresa(notificacion.getIdEmpresa() != null ? notificacion.getIdEmpresa() : null);
		if(notificacion.getIdPersona()!=null){
			Persona per = new Persona();
			per.setIdPersona(notificacion.getIdPersona());
			comunicado.setPersonaComunicado(per);
		}else{
			logger.debug("No Se Encuentra La Persona A La Cual Se Enviara El Comunicado, En La Plantilla: "+notificacion.getProcesoEvento());
		}
		if (notificacion.getTextoAdicionar() != null && !notificacion.getTextoAdicionar().isEmpty()) {
			comunicado.setTextoAdicionar(notificacion.getTextoAdicionar());
		}
		if (notificacion.getEmail() != null && !notificacion.getEmail().isEmpty()) {
			comunicado.setEmail(notificacion.getEmail());
		}
		if (EstadoEnvioNotificacionEnum.FALLIDA.equals(notEnv.getEstadoEnvioNot())) {
			comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.FALLIDO);
		}
		comunicado.setFechaComunicado(Calendar.getInstance().getTime());
		comunicado.setMensajeEnvio(notificacion.getMensaje());

		if (plantilla != null) {
			comunicado.setPlantillaComunicado(plantilla);
			//Para Validar Que Es Un Certificado
			if(plantilla.getEtiqueta().name().startsWith("COM_GEN_CER_")){
				comunicado.setComCertificado(notificacion.getIdSolicitud());
				comunicado.setIdSolicitud(null);
			}
		}
		
		// se obtiene el id del archivo adjunto (comunicado PDF)
		if (notificacion.getArchivosAdjuntos() != null && notificacion.getArchivosAdjuntos().size()>0 
				&& notificacion.getArchivosAdjuntos().get(0).getIdECM()!=null) {
            comunicado.setIdentificadorArchivoComunicado(notificacion.getArchivosAdjuntos().get(0).getIdECM());
        }else if (notificacion.getArchivosAdjuntosIds() != null && !notificacion.getArchivosAdjuntosIds().isEmpty()) {
			comunicado.setIdentificadorArchivoComunicado(notificacion.getArchivosAdjuntosIds().get(0));
		}

		// almacena el destinatario
		StringBuilder emails = new StringBuilder();
		if (notificacion.getDestinatarioTO() != null) {
			for (String email : notificacion.getDestinatarioTO()) {
				if (emails.length() > 0) {
					emails.append(", ");
				}
				emails.append(email);
			}
		}
		if (notificacion.getDestinatarioCC() != null) {
			for (String email : notificacion.getDestinatarioCC()) {
				if (emails.length() > 0) {
					emails.append(", ");
				}
				emails.append(email);
			}
		}
		if (notificacion.getDestinatarioBCC() != null) {
			for (String email : notificacion.getDestinatarioBCC()) {
				if (emails.length() > 0) {
					emails.append(", ");
				}
				emails.append(email);
			}
		}
		if (emails.length() > 0) {
			comunicado.setDestinatario(emails.toString());
		}
		
		if (notEnv.getEstadoEnvioNot() != null && !notEnv.getEstadoEnvioNot().equals(EstadoEnvioNotificacionEnum.NO_AUTORIZA_ENVIO)) {
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.EXITOSO);
        }else
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.NO_AUTORIZA_ENVIO);
		
		try {
			comunicado = entityManager.merge(comunicado);
			
			// Se almacena un registro en la tabla DetalleComunicadoEnviado. Aplica únicamente cuando el comunicado no va asociado a ninguna solicitud.
			// Si llega el parámetro "identificador", indica que es un comunicado que no va asociado a ninguna solicitud 
			if(notificacion.getParams()!=null && notificacion.getParams().containsKey("identificador")){
				DetalleComunicadoEnviado detalleComunicadoEnviado = new DetalleComunicadoEnviado();
				detalleComunicadoEnviado.setIdComunicado(comunicado.getIdComunicado());
				detalleComunicadoEnviado.setTipoTransaccion(notificacion.getTipoTx());
				detalleComunicadoEnviado.setIdentificador(Long.parseLong(notificacion.getParams().get("identificador").toString()));
				entityManager.merge(detalleComunicadoEnviado);
			}	
				
        } catch (Exception e) {
            logger.error("No es posible persistir el comunicado", e);
            logger.debug("Finaliza persistirComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado) ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
		
		logger.debug(
				"Finaliza persistirComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado)");
		return comunicado;
	}
	
	/**
	 * método que acutaliza el comunicado enviado con la incormación de la notificación enviada 
	 * 
	 * @param notEnv
	 * @param comunicado
	 * @return
	 */
	private Comunicado actualizarComunicado(NotificacionEnviada notEnv, Comunicado comunicado) {
        logger.debug("Inicia actualizarComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado)");
        
        if (EstadoEnvioNotificacionEnum.FALLIDA.equals(notEnv.getEstadoEnvioNot())) {
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.FALLIDO);
        }else if (EstadoEnvioNotificacionEnum.ENVIO_CANCELADO.equals(notEnv.getEstadoEnvioNot())){
        	comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.NO_ENVIADO);
        }else if (EstadoEnvioNotificacionEnum.NO_AUTORIZA_ENVIO.equals(notEnv.getEstadoEnvioNot())) {
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.NO_AUTORIZA_ENVIO);
        }else if (EstadoEnvioNotificacionEnum.ENVIO_INACTIVO.equals(notEnv.getEstadoEnvioNot())){
            comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.ENVIO_INACTIVO);
        }
        try {
            entityManager.merge(comunicado);
        } catch (Exception e) {
            logger.error("No es posible actualizar el comunicado", e);
            logger.debug("Finaliza persistirComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado) ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(
                "Finaliza persistirComunicado(NotificacionEnviada, NotificacionDTO, UserDTO, PlantillaComunicado)");
        return comunicado;
    }

	/**
	 * Método encargado de persistir una notificación enviada
	 * 
	 * @param notEnv
	 * @return
	 */
	private NotificacionEnviada persistirEnvio(NotificacionEnviada notEnv, NotificacionDTO notificacion) {
		logger.debug("Inicia persistirEnvio(NotificacionEnviada, NotificacionDTO)");
		try {
		    entityManager.persist(notEnv);

	        persistirDestinatarios(notEnv, notificacion.getDestinatarioTO(), "TO");
	        persistirDestinatarios(notEnv, notificacion.getDestinatarioCC(), "CC");
	        persistirDestinatarios(notEnv, notificacion.getDestinatarioBCC(), "BCC");
    
        } catch (Exception e) {
            logger.error("No es posible persistir el envio", e);
            logger.debug("Finaliza persistirEnvio(NotificacionEnviada, NotificacionDTO) ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
		
		logger.debug("Finaliza persistirEnvio(NotificacionEnviada)");
		return notEnv;
	}

	/**
	 * Método encargado de persistir los destintarios de una una notificación
	 * enviada
	 * 
	 * @param notEnv
	 * @param destinatario
	 */
	private void persistirDestinatarios(NotificacionEnviada notEnv, List<String> destinatario, String tipo) {
		logger.debug("Inicia persistirDestinatarios(NotificacionEnviada, List<String>)");
		NotificacionDestinatario notDes;
		if (destinatario != null) {
			for (String email : destinatario) {
				if (email != null && !email.isEmpty()) {
					notDes = new NotificacionDestinatario();
					notDes.setNotEnviada(notEnv);
					notDes.setDestintatario(email);
					notDes.setTipoDestintatario(tipo);
					try {
					    entityManager.persist(notDes);                        
                    } catch (Exception e) {
                        logger.error("No es posible persistir el envio", e);
                        logger.debug("Finaliza persistirDestinatarios(NotificacionEnviada, List<String>, String) ");
                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }
				}
			}
		}
		logger.debug("Finaliza persistirDestinatarios(NotificacionEnviada, List<String>)");
	}
	
	
	/**
     * Lógica que envia un correo parametrizado 
     * 
     * @param notificacion
     * @param userDTO
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");

        try {
			Boolean verificarArchivoAdjunto = (Boolean) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_ADJUNTO)
				.setParameter("pcoEtiqueta", notificacion.getEtiquetaPlantillaComunicado().name()).getSingleResult();
			if (verificarArchivoAdjunto == Boolean.FALSE) {
				notificacion.setArchivosAdjuntos(null);
				notificacion.setArchivosAdjuntosIds(null);
			}

        	PlantillaComunicado plantilla = consultasModeloCore.consultarPlantillaComunicado(notificacion.getEtiquetaPlantillaComunicado());
				//se crea el if para GLPI 52581 - Alexander Camelo
				logger.info("**__**enviarCorreoParametrizado plantilla");  
		if(EtiquetaPlantillaComunicadoEnum.NTF_FLL_PRCS_SEVEN.equals(plantilla.getEtiqueta())){
			Object[] textoRespuestSeven=null;
			try{
				 textoRespuestSeven = (Object[])entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TEXTO_AUDITORIA_SEVEN).getSingleResult();
			}catch(NoResultException ex){
			   logger.info("**__**Error Consulta Vacia "+ex);   
			}catch(Exception el){
				  logger.info("**__**Error Consulta Vacia "+el);   
			}
			if(textoRespuestSeven != null){
				String textoConcatenado="Enviados: "+textoRespuestSeven[0].toString()+"\nExitosos: "+
												textoRespuestSeven[1].toString()+"\nErrores: "+textoRespuestSeven[2].toString();
			plantilla.setMensaje(replaceTextoClaveValorSeven(plantilla.getMensaje(),"${textoDetalleSeven}",textoConcatenado));
			}
}
        	List<VariableComunicado> variables = consultasModeloCore.consultarVariableComunicado(plantilla.getIdPlantillaComunicado());
            PlantillasUtil.construirPlantilla(notificacion, plantilla, variables);

            List<PrioridadDestinatario> listaPrioridadDestinatario = new ArrayList<PrioridadDestinatario>();
            try {
                listaPrioridadDestinatario = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PRIORIDAD_ETIQUETA_PROCESO_DESTINATARIO_COMUNICADO,
                                PrioridadDestinatario.class)
                        .setParameter("proceso", notificacion.getProcesoEvento())
                        .setParameter("etiqueta", notificacion.getEtiquetaPlantillaComunicado()).getResultList();
    
            } catch (NoResultException e) {
                logger.error("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO): Aún no se ha realizado la parametrización de destinatarios para el envío de este comunicado en el proceso");
                logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO): Aún no se ha realizado la parametrización de destinatarios para el envío de este comunicado en el proceso");
            }
            
            NotificacionEnviada notEnv = enviarEmail(notificacion, userDTO, plantilla,
                    listaPrioridadDestinatario);

            logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
        } catch (NonUniqueResultException e) {
            logger.error(
                    "Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO): Error hay más de una plantilla para el identificador dado",e);
        } catch (NoResultException e) {
            logger.error(
                    "Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO): Error no se encuentra una plantilla para el identificador dado",e);
        }
    }
//Metodo Creado para renplazar la calve del mensaje por el valor de la consulta
private String replaceTextoClaveValorSeven (String string, String key, String value) {
	StringBuilder sb = new StringBuilder(string);
	int start = sb.indexOf(key, 0);
	while (start > -1) {
	int end = start + key.length();
	int nextSearchStart = start + value.length();
	sb.replace(start, end, value);
	start = sb.indexOf(key, nextSearchStart);
	}
	return sb.toString();
}
    /**
     * Lógica que envia multiples correos parametrizados por conexión,
     * sin hacer uso de la priorización de los destinatarios parametrizados
     *  
     * @param notificaciones
     * @param userDTO
     */
    private void enviarMultiplesCorreosPorConexionImpl(List<NotificacionParametrizadaDTO> notificaciones, UserDTO userDTO) {
        logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
        PlantillaComunicado plantilla;
        Map<Long, Comunicado> comunicados = new HashMap<Long, Comunicado>();
        Map<Long, NotificacionEnviada> notiEnviadas = new HashMap<Long, NotificacionEnviada>();
        Map<EtiquetaPlantillaComunicadoEnum, PlantillaComunicado> plantillas = new HashMap<EtiquetaPlantillaComunicadoEnum, PlantillaComunicado>();
        List<NotificacionParametrizadaDTO> notificacionesAutorizadas = new ArrayList<>();
        List<NotificacionParametrizadaDTO> notificacionesNoValidas = new ArrayList<>();

        // se debe guardar cada comunicado que se debe enviar
        for (NotificacionParametrizadaDTO notificacion : notificaciones) {
        	try {
        		NotificacionEnviada notEnv = new NotificacionEnviada();
	    		notEnv.setFechaEnvio(Calendar.getInstance().getTime());
	    		notEnv.setIdSedeCajaCompensacion(
	                    userDTO.getSedeCajaCompensacion() != null ? Long.valueOf(userDTO.getSedeCajaCompensacion()) : null);
	            notEnv.setProcesoEvento(notificacion.getProcesoEvento());
	            notEnv.setRemitente(notificacion.getRemitente());
	            
				// Obtener la plantilla correspondiente o usar la cacheada
	            if(plantillas.containsKey(notificacion.getEtiquetaPlantillaComunicado())) {
	            	plantilla = plantillas.get(notificacion.getEtiquetaPlantillaComunicado());
	            } else {
	            	plantilla = consultasModeloCore.consultarPlantillaComunicado(notificacion.getEtiquetaPlantillaComunicado());
	            	plantillas.put(notificacion.getEtiquetaPlantillaComunicado(), plantilla);
	            }
	            if (notificacion.getAutorizaEnvio() != null && notificacion.getAutorizaEnvio().equals(Boolean.TRUE)) {
					ModuloPlantillaComunicado moduloPlantilla = obtenerModuloDePlantilla(plantilla.getIdPlantillaComunicado());
					notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIADA);
					Comunicado comunicado = persistirComunicado(notEnv, notificacion, userDTO, plantilla);
					comunicados.put(comunicado.getIdComunicado(), comunicado);
					notiEnviadas.put(comunicado.getIdComunicado(), notEnv);
					notificacion.setIdComunicado(comunicado.getIdComunicado());
					if(!validarEnvioPorModulo(moduloPlantilla)){
						notificacion.setValidoEnvio(false);
						notificacionesNoValidas.add(notificacion);
					}else{
						notificacionesAutorizadas.add(notificacion);
					}
                }else{
                    notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.NO_AUTORIZA_ENVIO);
                    Comunicado comunicado = persistirComunicado(notEnv, notificacion, userDTO, plantilla);
                    notificacion.setIdComunicado(comunicado.getIdComunicado());
                    persistirEnvio(notEnv, notificacion);
                    logger.warn("La entidad no autoriza el envío de comunicados");
                }
	            
	            
        	} catch (Exception e) {
        		notificacion.setCausaErrorEnvio("Ha ocurrido un peristiendo el comunicado: " +e.getMessage());
        		notificacion.setEstadoEnvioNotificacion(EstadoEnvioNotificacionEnum.FALLIDA);
        	}
        }
        
        try {
			Comunicado comunicado;
			NotificacionEnviada notEnv;
			for (NotificacionParametrizadaDTO noti : notificacionesNoValidas) {
				if(noti.getValidoEnvio() == false){
					if (comunicados.containsKey(noti.getIdComunicado())
							&& notiEnviadas.containsKey(noti.getIdComunicado())) {

						comunicado = comunicados.get(noti.getIdComunicado());
						notEnv = notiEnviadas.get(noti.getIdComunicado());
						notEnv.setEstadoEnvioNot(EstadoEnvioNotificacionEnum.ENVIO_INACTIVO);
						actualizarComunicado(notEnv, comunicado);

						EnvioComunicadoProcesoEnum envioProceso = EnvioComunicadoProcesoEnum
								.obtenerEnumeracionPorProceso(noti.getProcesoEvento());
						persistirEnvio(notEnv, noti);
						if (envioProceso != null && !envioProceso.getValidarProceso()) {
							procesoPostEnvio(noti, EstadoEnvioNotificacionEnum.FALLIDA);
						}
					}
					logger.warn("La conexión al servidor de correo se encuentra desactivada");
				}
			}
		
        	NotificacionesUtil not = new NotificacionesUtil();
        	List<NotificacionParametrizadaDTO> notificacionesEnviadas = not.enviarListaCorreoUtil(notificacionesAutorizadas);
        	
			// se debe actualizar cada comunicado con el resultado del envío y
			// persistir en elvío de la notificación
			for (NotificacionParametrizadaDTO noti : notificacionesEnviadas) {
				if (comunicados.containsKey(noti.getIdComunicado())
						&& notiEnviadas.containsKey(noti.getIdComunicado())) {

					comunicado = comunicados.get(noti.getIdComunicado());
					notEnv = notiEnviadas.get(noti.getIdComunicado());
					notEnv.setEstadoEnvioNot(noti.getEstadoEnvioNotificacion());
					notEnv.setError(noti.getCausaErrorEnvio());
					actualizarComunicado(notEnv, comunicado);

					EnvioComunicadoProcesoEnum envioProceso = EnvioComunicadoProcesoEnum
							.obtenerEnumeracionPorProceso(noti.getProcesoEvento());
					persistirEnvio(notEnv, noti);
					if (envioProceso != null && !envioProceso.getValidarProceso()) {
						procesoPostEnvio(noti, notEnv.getEstadoEnvioNot());
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"Finaliza enviarMultiplesCorreosPorConexionImpl(List<NotificacionParametrizadaDTO>, UserDTO): Error técnico");
			logger.debug(
					"Finaliza enviarMultiplesCorreosPorConexionImpl(List<NotificacionParametrizadaDTO>, UserDTO): Error técnico");
		}
		logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
	}
    
    /**
     * Valida que un email sea valido para envío de correo (null y pattern).
     * parametrizada
     * 
     * @param notificacionParametrizadaDTO
     *        Información de la notificación parametrizada
     * @return <code>true</code> si todas las direcciones de correo son válidas.
     *         <code>false</code> en otro caso
     */
    private Boolean emailValido(String email) {
        logger.debug("Inicio de método emailValido(String)");

        if (!ValidacionCamposUtil.validarEmail(email)) {
            logger.warn("Fin de método emailValido(String). El destinatario presentan correo eletrónico invalido : "
                    + email);
            return Boolean.FALSE;
        }
        logger.debug("Fin de método emailValido(String)");
        return Boolean.TRUE;
    }
}
