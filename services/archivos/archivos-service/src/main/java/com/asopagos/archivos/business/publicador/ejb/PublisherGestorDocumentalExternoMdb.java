package com.asopagos.archivos.business.publicador.ejb;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.asopagos.archivos.business.interfaces.IConsultasModeloCore;
import com.asopagos.archivos.constants.NamedQueriesConstants;
import com.asopagos.archivos.enums.ProcesoFoliumEnum;
import com.asopagos.cache.CacheManager;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.MetadataArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.exception.TechnicalException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.asopagos.entidades.ccf.archivos.GestorDocumentalFolium;

@Stateless
public class PublisherGestorDocumentalExternoMdb implements IPublisherGestorDocumentalExternoMdb{

	private final ILogger logger = LogManager.getLogger(PublisherGestorDocumentalExternoMdb.class);

	/**
     * Referencia al conection factory configurado para la aplicación
     */
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    /**
     * Queue a la que será enviado el mensaje
     */
    @Resource(mappedName = "java:/jms/queue/GestorDocumentalExternoQueue")
    private Queue archivosQueue;
	
	private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";
	
	private static final String CANAL = "GENESYS";
	private static final String CODIGO_REGISTRO_CANAL = "GENESYS";
	private static final String SALVOCONDUCTO = "SA";
	private static final String PASAPORTE = "PAS";
	private static final String NIT = "NIT";
	
	private static final String SUFIJO_USUARIO = "_SOPORTE";
	
	/** 
     * Inject del EJB para consultas en modelo Core 
     */
    @Inject
    private IConsultasModeloCore consultasCore;
    
    /** Entity Manager */
    @PersistenceContext(unitName = "archivos_PU")
    private EntityManager entityManagerCore;
    
    /**
     * Método encargado de construir el dto que se envía a la cola para que sea procesado y enviado al gestor documental externo.
     * 
     * @param informacionArchivoDTO
     * 			<code>com.asopagos.dto.InformacionArchivoDTO</code> DTO con la metadata actual del archivo
     */
    @Override
    @Asynchronous
    public void enviarAColaGestorDocumentalExterno(InformacionArchivoDTO informacionArchivoDTO) {
		logger.info("ingresa almacenarArchivo enviarAColaGestorDocumentalExterno");
    	MetadataArchivoDTO metadataArchivo = new MetadataArchivoDTO();
		GestorDocumentalFolium gestorDocumental= new GestorDocumentalFolium(); 
    	ProcesoFoliumEnum proceso = consultarProcesoArchivo(informacionArchivoDTO.getProcessName());
		Object numeroRadicado = null;
		Object[] persona = null;

		logger.info(informacionArchivoDTO.toString());
		logger.info("Id solicitud"+informacionArchivoDTO.getIdSolicitud());

    	
    	if(proceso != null && (proceso.getSufijoProceso() != null && proceso.getSufijoTema() != null)){
    		metadataArchivo.setCanal(CANAL);
        	metadataArchivo.setTipoDocumental(proceso != null ? proceso.getSufijoProceso() : null);
        	metadataArchivo.setTema(proceso != null ? proceso.getSufijoTema() : null);
        	metadataArchivo.setObservacion(informacionArchivoDTO.getDescription());
        	metadataArchivo.setGenerarArchivo(false);
        	metadataArchivo.setUsuario(((String) CacheManager.getParametro(ParametrosSistemaConstants.NOMBRE_CCF)) + SUFIJO_USUARIO);
        	//se setea datos para auditoria
			gestorDocumental.setGdfcanal(CANAL);
        	gestorDocumental.setGdftipoDocumental(proceso != null ? proceso.getSufijoProceso() : null);
        	gestorDocumental.setGdftema(proceso != null ? proceso.getSufijoTema() : null);
        	gestorDocumental.setGdfobservacion(informacionArchivoDTO.getDescription());
        	gestorDocumental.setGdfgenerarArchivo(false);
        	gestorDocumental.setGdfUsuario(((String) CacheManager.getParametro(ParametrosSistemaConstants.NOMBRE_CCF)) + SUFIJO_USUARIO);
        	
			
			
			if (informacionArchivoDTO.getTipoIdentificacionPropietario() != null
					&& informacionArchivoDTO.getNumeroIdentificacionPropietario() != null) {
				
				metadataArchivo.setTipoIdentificacion(obtenerTipoIdentificacion(informacionArchivoDTO.getTipoIdentificacionPropietario()));
				metadataArchivo.setNumeroIdentificacion(informacionArchivoDTO.getNumeroIdentificacionPropietario());

				gestorDocumental.setGdftipoIdentificacion(obtenerTipoIdentificacion(informacionArchivoDTO.getTipoIdentificacionPropietario()));
	        	gestorDocumental.setGdfnumeroIdentificacion(informacionArchivoDTO.getNumeroIdentificacionPropietario());
				
				try {
					Object[] objs = null;
					if (informacionArchivoDTO.getTipoIdentificacionPropietario() != null
					&& informacionArchivoDTO.getNumeroIdentificacionPropietario() != null) {

					objs = (Object[]) entityManagerCore
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_METADATA_RESTANTE_ARCHIVO)
							.setParameter("tipoId", informacionArchivoDTO.getTipoIdentificacionPropietario().name())
							.setParameter("numeroId", informacionArchivoDTO.getNumeroIdentificacionPropietario())
							.getSingleResult();
					}

							// if(metadataArchivo.getCodigoRegistroCanal() == null || metadataArchivo.getCodigoRegistroCanal().equals("null")){
					        // switch(informacionArchivoDTO.getProcessName()){
							// 	case "AFILIACION_PERSONAS_PRESENCIAL":
							// 		numeroRadicado = (Object) entityManagerCore
							// 		.createNamedQuery(NamedQueriesConstants.CONSULTAR_METADATA_NUMERO_RADICACION)
							// 		.setParameter("numeroIdentificacion", informacionArchivoDTO.getNumeroIdentificacionPropietario())
							// 		.setParameter("tipoIdentificacion", informacionArchivoDTO.getTipoIdentificacionPropietario().name())
							// 		.setParameter("proceso", informacionArchivoDTO.getProcessName())
							// 		.getSingleResult();	

							// 		logger.info("Tipo identificacion "+numeroRadicado);
							// 		break;
							// 	default:
							// 		break;
							// 	}
							// }

					logger.info(informacionArchivoDTO.getIdInstanciaProceso());
					logger.info("setCodigoRegistroCanal "+numeroRadicado);

					metadataArchivo.setCodigoRegistroCanal(numeroRadicado!=null?numeroRadicado.toString():"GENESYS");
					metadataArchivo.setBarrio("");
					if(objs != null){
						metadataArchivo.setNombre(objs[0] != null ? objs[0].toString() : null);
						metadataArchivo.setCiudad(objs[1] != null ? objs[1].toString() : null);
						metadataArchivo.setDireccion(objs[2] != null ? objs[2].toString() : null);
						metadataArchivo.setTelefono(objs[3] != null ? objs[3].toString() : null);
						metadataArchivo.setCelular(objs[4] != null ? objs[4].toString() : null);
						metadataArchivo.setEmail(objs[5] != null ? objs[5].toString() : null);
					}
					
					//
					gestorDocumental.setGdfcodigoRegistrCanal(numeroRadicado!=null?numeroRadicado.toString():"GENESYS");
					gestorDocumental.setGdfbarrio("");
					if(objs != null){
						gestorDocumental.setGdfnombre(objs[0] != null ? objs[0].toString() : null);
						gestorDocumental.setGdfciudad(objs[1] != null ? objs[1].toString() : null);
						gestorDocumental.setGdfdireccion(objs[2] != null ? objs[2].toString() : null);
						gestorDocumental.setGdftelefono(objs[3] != null ? objs[3].toString() : null);
						gestorDocumental.setGdfcelular(objs[4] != null ? objs[4].toString() : null);
						gestorDocumental.setGdfemail(objs[5] != null ? objs[5].toString() : null);
					}
				} catch (NoResultException nre) {
					logger.info("error"+nre);
					System.out.println("no se encontró la persona en bdat");
				}
				
			}
			try {
				numeroRadicado = (Object) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_METADATA_NUMERO_RADICACION_INSTANCIA_PROCESO)
					.setParameter("instanciaProceso", informacionArchivoDTO.getIdInstanciaProceso())
					.getSingleResult();	
			} catch (NoResultException nre) {
				logger.info("error"+nre);
				System.out.println("no se encontró el numero de radicado");
			}
			try {
				switch (proceso.getSufijoTema()) {
					case "AFLAFE":
					persona = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_RADICACION_AFLAFE)
					.setParameter("numeroRadicacion", numeroRadicado)
					.getSingleResult();	
						break;
					 case "AFLNIP":
					 numeroRadicado = (Object[]) entityManagerCore
					 .createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_RADICACION_AFLAFE)
					 .setParameter("numeroRadicacion", numeroRadicado)
					 .getSingleResult();	
					 	break;
					case "AFLTRA":
					persona = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_RADICACION_AFLTRA)
					.setParameter("numeroRadicacion", numeroRadicado)
					.getSingleResult();	
						break;
					case "PFOVIS":
					persona = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_RADICACION_PFOVIS)
					.setParameter("numeroRadicacion", numeroRadicado)
					.getSingleResult();	
						break;
					case "NFOVIS":
					persona = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_RADICACION_NFOVIS)
					.setParameter("numeroRadicacion", numeroRadicado)
					.getSingleResult();	
						break;
					case "AFLNVE":
					persona = (Object[]) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_RADICACION_AFLNVE)
					.setParameter("numeroRadicacion", numeroRadicado)
					.getSingleResult();	
						break;
				
					default:
						break;
				}
				if(persona != null){
					metadataArchivo.setNombre(persona[0] != null ? persona[0].toString() : null);
					metadataArchivo.setNumeroIdentificacion(persona[1] != null ? persona[1].toString() : null);
					metadataArchivo.setTipoIdentificacion(persona[2] != null ? TipoIdentificacionEnum.valueOf(persona[2].toString()).getValorEnPILA() : null);
					metadataArchivo.setCiudad(persona[3] != null ? persona[3].toString() : null);
					metadataArchivo.setDireccion(persona[4] != null ? persona[4].toString() : null);
					metadataArchivo.setTelefono(persona[5] != null ? persona[5].toString() : null);
					metadataArchivo.setCelular(persona[6] != null ? persona[6].toString() : null);
					metadataArchivo.setEmail(persona[7] != null ? persona[7].toString() : null);
				}
				
				//
				gestorDocumental.setGdfbarrio("");
				if(persona != null){
					gestorDocumental.setGdfnombre(persona[0] != null ? persona[0].toString() : null);
					gestorDocumental.setGdfnumeroIdentificacion(persona[1] != null ? persona[1].toString() : null);
					gestorDocumental.setGdftipoIdentificacion(persona[2] != null ? TipoIdentificacionEnum.valueOf(persona[2].toString()).getValorEnPILA() : null);
					gestorDocumental.setGdfciudad(persona[3] != null ? persona[3].toString() : null);
					gestorDocumental.setGdfdireccion(persona[4] != null ? persona[4].toString() : null);
					gestorDocumental.setGdftelefono(persona[5] != null ? persona[5].toString() : null);
					gestorDocumental.setGdfcelular(persona[6] != null ? persona[6].toString() : null);
					gestorDocumental.setGdfemail(persona[7] != null ? persona[7].toString() : null);
				}
			
			} catch (NoResultException nre) {
				logger.info("error"+nre);
				System.out.println("no se persona por numero radicado");
			}

			metadataArchivo.setCodigoRegistroCanal(numeroRadicado!=null?numeroRadicado.toString():"GENESYS");
			gestorDocumental.setGdfcodigoRegistrCanal(numeroRadicado!=null?numeroRadicado.toString():"GENESYS");

			logger.info("getCodigoRegistroCanal"+metadataArchivo.getCodigoRegistroCanal());
            Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
            
            System.out.println(metadataArchivo.toString());
            System.out.println(gestorDocumental.toString());

			//se persisten datos en base de datos core
				try {
					entityManagerCore.persist(gestorDocumental);
				} catch (Exception e) {
					// TODO: handle exception
				}
				String mensaje = gson.toJson(metadataArchivo);
				
				System.out.println("el mensaje enviado es: " + mensaje);
					logger.info("enviando mensaje a la cola");
					enviarMensaje(archivosQueue, mensaje);
    	}
	}
    
    /**
	 * Método encargado de obtener una instancia de ProcesoEnum que representa el proceso asociado 
	 * en la metadata del archivo
	 * 
	 * @param procesoMetadata
	 * 			<code>String</code> nombre del proceso que viene en la metadata del archivo.
	 * 
	 * @return ProcesoEnum que representa el proceso del que proviene el archivo. Si no encuentra uno para relacionar retorna null.
	 */
	private ProcesoFoliumEnum consultarProcesoArchivo(String procesoMetadata) {
		ProcesoFoliumEnum proceso = null;
		try {
			proceso = ProcesoFoliumEnum.valueOf(procesoMetadata);
			return proceso;
		} catch (Exception e) {
			return null;
		}
	}

	/**
     * Método encargado de poner el mensaje en la cola
     * 
     * @param queue 
     * 			<code>javax.jms.Queue</code> cola donde se pondrá el mensaje.
     * 
     * @param mensaje
     * 			<code>String</code> mensaje que se pondrá en la cola. 
     */
    private void enviarMensaje(Queue queue, String mensaje) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            connection.start();
            TextMessage message = session.createTextMessage();
            message.setText(mensaje);
            producer.send(message);
        } catch (JMSException e) {
            throw new TechnicalException("Error al enviar el mensaje: " + mensaje, e);
        }
    }
    
    private String obtenerTipoIdentificacion(TipoIdentificacionEnum tipoId) {
		String tipoIdentificacion;
		switch (tipoId) {
		case SALVOCONDUCTO:
			tipoIdentificacion = SALVOCONDUCTO;
			break;
		case PASAPORTE:
			tipoIdentificacion = PASAPORTE;
			break;
		case NIT:
			tipoIdentificacion = NIT;
			break;
		default:
			tipoIdentificacion = tipoId.getValorEnPILA(); 
			break;
		}
		return tipoIdentificacion;
	}
}
