package com.asopagos.archivos.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import com.asopagos.archivos.almacenamiento.AlmacenamientoArchivosFactory;
import com.asopagos.archivos.almacenamiento.IAlmacenamientoArchivos;
import com.asopagos.archivos.business.publicador.ejb.IPublisherGestorDocumentalExternoMdb;
import com.asopagos.archivos.constants.MensajesArchivosConstants;
import com.asopagos.archivos.constants.NamedQueriesConstants;
import com.asopagos.archivos.convert.ConvertHtmlToPdf;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import com.asopagos.archivos.dto.RespuestaECMExternoDTO;
import com.asopagos.archivos.service.ArchivosService;
import com.asopagos.archivos.util.AnalizadorArchivosPDF;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.InformacionArchivoClasificacionDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.ccf.archivos.RespuestaEcmExterno;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion: </b> EJB que implementa los servicios de gestión de archivos
 * para interacción con el ECM <b>Historia de Usuario: </b>Transversal
 * 
 * @author Leonardo Giral <a href="mailto:ogiral@heinsohn.com.co"></a>
 * @author Ricardo Hernandez Cediel
 *         <a href="mailto:hhernandez@heinsohn.com.co"></a>
 */
@Stateless
public class ArchivosBusiness implements ArchivosService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ArchivosBusiness.class);
    
    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "archivos_PU")
    private EntityManager entityManager;
    
    /**
     * Referencia a la implementación de almacenamiento de archivos
     */    
    private IAlmacenamientoArchivos almacenamientoArchivos;
    
    @Inject
    private IPublisherGestorDocumentalExternoMdb publisher;
    
    /**
     * Método de inicialización
     */
    @PostConstruct
    public void inicializarRecursos() {
        almacenamientoArchivos = AlmacenamientoArchivosFactory.obtenerAlmacenamientoArchivos(entityManager);
    }
    
    /**
     * @see com.asopagos.archivos.service.ArchivosService#consultarArchivo(java.lang.String, java.lang.String, com.asopagos.rest.security.dto.UserDTO) 
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public MultipartFormDataOutput consultarArchivo(String identificadorArchivo, String versionDocumento, UserDTO userDTO) {
        return almacenamientoArchivos.consultarArchivo(identificadorArchivo, versionDocumento);
    }

    /**
     * @see com.asopagos.archivos.service.ArchivosService#consultarArchivoInfoHeader(java.lang.String, java.lang.String, boolean, com.asopagos.rest.security.dto.UserDTO) 
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarArchivoInfoHeader(String identificadorArchivo, String versionDocumento, boolean toDownload, UserDTO userDTO) {
        return almacenamientoArchivos.consultarArchivoInfoHeader(identificadorArchivo, versionDocumento, toDownload);
    }

    /**
     * @see com.asopagos.archivos.service.ArchivosService#obtenerArchivo(java.lang.String, com.asopagos.rest.security.dto.UserDTO) 
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InformacionArchivoDTO obtenerArchivo(String identificadorArchivo, UserDTO userDTO) {
        return almacenamientoArchivos.obtenerArchivo(identificadorArchivo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.archivos.service.ArchivosService#eliminarArchivo(java.lang.String)
     */
    @Override
    public void eliminarArchivo(String identificadorArchivo, UserDTO userDTO) {
        almacenamientoArchivos.eliminarArchivo(identificadorArchivo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.archivos.service.ArchivosService#eliminarVersionArchivo(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void eliminarVersionArchivo(String identificadorArchivo, String versionDocumento, UserDTO userDTO) {
        almacenamientoArchivos.eliminarVersionArchivo(identificadorArchivo, versionDocumento);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.archivos.service.ArchivosService#almacenarArchivoPila(com.asopagos.archivos.dto.InformacionArchivoDTO)
     */
    @Override
    public InformacionArchivoDTO almacenarArchivo(InformacionArchivoDTO infoFile, UserDTO userDTO) {
    	InformacionArchivoDTO informacionArchivoDTO = almacenamientoArchivos.almacenarArchivo(infoFile);
    	if(Boolean.parseBoolean(CacheManager.getParametro(ParametrosSistemaConstants.CONEXION_SERVICIO_ECM_EXTERNO).toString())){
			publisher.enviarAColaGestorDocumentalExterno(infoFile);
    	}
    	return informacionArchivoDTO;
    }

    /**
     * @see com.asopagos.archivos.service.ArchivosService#convertHTMLtoPDF(com.asopagos.archivos.dto.InformacionConvertDTO, com.asopagos.rest.security.dto.UserDTO) 
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public byte[] convertHTMLtoPDF(InformacionConvertDTO objInformacionConvertDTO, UserDTO userDTO) {
        logger.debug("Inicia convertHTMLtoPDF(InformacionConvertDTO)");
        try {
            ConvertHtmlToPdf objConvertHtmlToPdf = new ConvertHtmlToPdf(this);
            objInformacionConvertDTO = objConvertHtmlToPdf.convertHtml(objInformacionConvertDTO);
            logger.debug("Finaliza conversion de imagenes a Base65, Empieza creacion de PDF");
            byte[] x = objConvertHtmlToPdf.createPdf(objInformacionConvertDTO);
            logger.debug("Finaliza convertHTMLtoPDF(InformacionConvertDTO)");
            return x;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @see com.asopagos.archivos.service.ArchivosService#analizarYSepararArchivoPDF(com.asopagos.dto.InformacionArchivoDTO, com.asopagos.rest.security.dto.UserDTO) 
     */    
    @Override
    public InformacionArchivoClasificacionDTO analizarYSepararArchivoPDF(InformacionArchivoDTO infoFile, UserDTO userDTO) {
    	String firmaServicio = "ArchivosBusiness.analizarYSepararArchivoPDF(InformacionArchivoDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
    	InformacionArchivoClasificacionDTO informacionArchivoClasificacionDTO = null;
    	List<InformacionArchivoDTO> lstInformacionArchivoDTO = null;
    	List<InformacionArchivoDTO> subArchivos = null;
    	AnalizadorArchivosPDF analizador = null;
    	 
    	informacionArchivoClasificacionDTO = new InformacionArchivoClasificacionDTO();
    	try{
    		//Se instancia el analizador de clasificación de archivos
    		analizador = new AnalizadorArchivosPDF(entityManager);
        	subArchivos = analizador.analizarArchivoPDF(infoFile.getDataFile());
            //Se valida si apesar de que el procesamiento de analisis y clasificacion concluye, 
            //se hallan identificado y clasificado documentos
        	if(subArchivos != null && !subArchivos.isEmpty()){
        		lstInformacionArchivoDTO = new ArrayList<InformacionArchivoDTO>();
        		//Se realiza el proceso de almacenamiento en ECM o CGS
        		for (InformacionArchivoDTO informacionArchivoDTO : subArchivos) {
        			//Si se identifico un codigo de barras en el archivo identificado
        			//se verifica si es procedente de clasificacion 
        			//(exista integridad referencial de la persona y la solicitud)
        			if(informacionArchivoDTO.isClasificable()){
        				//Si es procedente se realiza la creacion del archivo en el gestor documental
        				informacionArchivoDTO = almacenarArchivo(informacionArchivoDTO, userDTO);
            			//Se evalua que si durante el proceso de almacenamiento se logro la clasificación
            			if(informacionArchivoDTO != null && informacionArchivoDTO.isClasificable()){
            				lstInformacionArchivoDTO.add(informacionArchivoDTO);	
            			}	
        			}
        		}
        		//Se valida que el proceso de clasificacion halla sido satisfactorio
            	if(lstInformacionArchivoDTO.size() == 0){
            		informacionArchivoClasificacionDTO.setDocumentosIdentificados(subArchivos.size());
            		informacionArchivoClasificacionDTO.setDocumentosClasificados(0);
                	informacionArchivoClasificacionDTO.setMensajeEstadoProcesamiento(
                			MensajesArchivosConstants.PROCESAMIENTO_NO_EXITOSO);
                	informacionArchivoClasificacionDTO.setProcesamientoExitoso(Boolean.FALSE);
                }else{
                	informacionArchivoClasificacionDTO.setDocumentosIdentificados(subArchivos.size());
            		informacionArchivoClasificacionDTO.setDocumentosClasificados(lstInformacionArchivoDTO.size());
                	informacionArchivoClasificacionDTO.setMensajeEstadoProcesamiento(
                			MensajesArchivosConstants.PROCESAMIENTO_ARCHIVO_EXITOSO);
                	informacionArchivoClasificacionDTO.setProcesamientoExitoso(Boolean.TRUE);
                	informacionArchivoClasificacionDTO.setArchivosClasificados(lstInformacionArchivoDTO);
                }
        	}else{
        		informacionArchivoClasificacionDTO.setDocumentosIdentificados(0);
        		informacionArchivoClasificacionDTO.setDocumentosClasificados(0);
        		informacionArchivoClasificacionDTO.setMensajeEstadoProcesamiento(
            			MensajesArchivosConstants.ERROR_ARCHIVO_CARGUE_ETIQUETAS_PROCESAMIENTO_NO_EXITOSO);
            	informacionArchivoClasificacionDTO.setProcesamientoExitoso(Boolean.FALSE);
        	}
    	}catch (ParametroInvalidoExcepcion pIExcep){
    		informacionArchivoClasificacionDTO.setDocumentosIdentificados(0);
    		informacionArchivoClasificacionDTO.setDocumentosClasificados(0);
    		informacionArchivoClasificacionDTO.setProcesamientoExitoso(Boolean.FALSE);
    		informacionArchivoClasificacionDTO.setMensajeEstadoProcesamiento(MensajesArchivosConstants.
    				ERROR_ARCHIVO_CARGUE_ETIQUETAS_INVALIDAS);
    	}catch (TechnicalException tExcep){
    		informacionArchivoClasificacionDTO.setDocumentosIdentificados(0);
    		informacionArchivoClasificacionDTO.setDocumentosClasificados(0);
    		informacionArchivoClasificacionDTO.setProcesamientoExitoso(Boolean.FALSE);
    		informacionArchivoClasificacionDTO.setMensajeEstadoProcesamiento(tExcep.getMessage());
    		logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
    				+ " :: " + tExcep.getMessage() );
    		//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return informacionArchivoClasificacionDTO;
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarRespuestaECMExterno(RespuestaECMExternoDTO respuesta) {
		String firmaServicio = "guardarRespuestaECMExterno(RespuestaECMExternoDTO";
		logger.debug("Inicia método " + firmaServicio + "con parámetros: " + respuesta.toString());
		
		RespuestaEcmExterno respuestaEcmExterno = new RespuestaEcmExterno(respuesta.getCud(),
				respuesta.getCode(), respuesta.getMessage(), respuesta.getStatus());
		
		entityManager.persist(respuestaEcmExterno);
		entityManager.flush();
		
		logger.debug("Finaliza método " +firmaServicio);
	}    
}
