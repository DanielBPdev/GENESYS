package com.asopagos.archivos.almacenamiento.google;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import com.asopagos.archivos.almacenamiento.IAlmacenamientoArchivos;
import static com.asopagos.archivos.almacenamiento.IAlmacenamientoArchivos.VERSION_CLIENT_SEPARATOR;

import com.asopagos.archivos.constants.DocumentMetadataConstants;
import com.asopagos.archivos.constants.DocumentType;
import com.asopagos.archivos.constants.DocumentType.Documents;
import com.asopagos.archivos.constants.NamedQueriesConstants;
import com.asopagos.archivos.business.ejb.ConvertToPDF;
import com.asopagos.archivos.business.interfaces.IConsultasModeloCore;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.archivos.ArchivoAlmacenado;
import com.asopagos.entidades.ccf.archivos.PropietarioArchivo;
import com.asopagos.entidades.ccf.archivos.VersionArchivo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.Interpolator;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.pdf.qrcode.ByteArray;
import javax.ejb.EJB;

/**
 * Implementación para almacenamiento de archivos en el servicio de la nube
 * Google Storage
 *
 * @author sbrinez
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 */
@Stateless
public class AlmacenamientoGoogle implements IAlmacenamientoArchivos {

    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AlmacenamientoGoogle.class);    

    /**
     * Referencia al objeto Storage de Google Storage
     */
    private Storage storage;

    /**
     * Nombre del Bucket para la caja de compensación
     */
    private String BUCKET_CAJA_COMPENSACION;

    /** 
     * Inject del EJB para consultas en modelo Core 
     */
    @Inject
    private IConsultasModeloCore consultasCore;
    
    @EJB
    private ConvertToPDF convertToPDF;
    /**
     * Inicializador del EJB
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     */
    @PostConstruct
    public void inicializador() {
    	String codigoCaja = (String) CacheManager.getConstante(ConstantesSistemaConstants.GOOGLE_STORAGE_BUCKET);
        BUCKET_CAJA_COMPENSACION = codigoCaja.toLowerCase();
        storage = StorageOptions.getDefaultInstance().getService();
        logger.debug("Iniciando instancia de AlmacenamientoGoogle con credenciales en " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
    }

    @Override
    public InformacionArchivoDTO almacenarArchivo(InformacionArchivoDTO infoFile) {
        logger.info("ingresa almacenarArchivo google");
        String firmaServicio = "AlmacenamientoGoogle.almacenarArchivo( InformacionArchivoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        InformacionArchivoDTO informacionArchivoDTO = null;
        String nombreArchivo = generarNombreArchivo(infoFile);
        infoFile.setFileName(nombreArchivo);
        Map<String, String> metadata = null;
        String idDocumentoCompuesto = null;
        PropietarioArchivo propietario = null;
        ArchivoAlmacenado archivoAlmacenado = null;
        VersionArchivo versionArchivo = null;
        Long idPropietario = null;
        Long idArchivoAlmacenado = null;
        boolean success = false;
        boolean errorClasificacionDocumento = false;
        //try {
        	byte[] dataFile = infoFile.getDataFile().clone();
            BlobId blobId = BlobId.of(BUCKET_CAJA_COMPENSACION, infoFile.getFileName());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).
            		setMetadata(crearMetadataRequisitoDocumental(infoFile)).
        			setContentType(infoFile.getFileType()).
        			build();
            Blob blob = storage.create(blobInfo, dataFile);
       
        	if (infoFile.getTipoIdentificacionPropietario() != null && infoFile.getNumeroIdentificacionPropietario() != null) {
                idPropietario = consultasCore.consultarPropietarioDocumento(infoFile);
                if( idPropietario != null ){
                	if (infoFile.getIdRequisito() != null) {
                        idArchivoAlmacenado = consultasCore.consultarRequisitoPropietarioDocumento(idPropietario, infoFile);
                    }
                }else{
                	propietario = consultasCore.registrarPropietarioArchivo(infoFile);
                	if( propietario != null ){
                		idPropietario = propietario.getIdPropietarioArchivo();	
                	}
                }
            }
            if (idArchivoAlmacenado == null) {
                archivoAlmacenado = consultasCore.registrarArchivoAlmacenado(idPropietario, infoFile); 
                if(archivoAlmacenado != null){
                	idArchivoAlmacenado = archivoAlmacenado.getIdArchivoAlmacenado();	
                }
            }
            //una vez creado el documento, se obtiene la metadata y se transforma en un String con formato JSON
            Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
            if(infoFile.getJsonMetadata() == null){
            	infoFile.setDataFile(null);
            	infoFile.setJsonMetadata(gson.toJson(infoFile));
            	infoFile.setDataFile(dataFile);            	
            }
            
            versionArchivo = consultasCore.registrarVersionArchivo(idArchivoAlmacenado, nombreArchivo, 
        		blob.getGeneration().toString(), infoFile);
        
            //Si el archivo es clasificable, se relaciona en el requisito documental asociado a la solicitud 
            if( infoFile.isClasificable() ){
            	idDocumentoCompuesto = nombreArchivo.concat("_").concat(blob.getGeneration().toString());
            	infoFile.setIdentificadorDocumento(idDocumentoCompuesto);
            	success = consultasCore.clasificarRequisitoDocumental(infoFile);
            	if(!success){
            		logger.debug( firmaServicio + " :: No se pudo realizar el proceso de clasificiación del documento" );
            		infoFile.setClasificable(false);
            		errorClasificacionDocumento = true;
            	}
            }
            //Si el archivo fue marcado para clasificacion por escaneo masivo,  y sucedio un error en su procedimiento
            //se elimina del repositorio documental por ser una archivo invalido
            if(!errorClasificacionDocumento){
                informacionArchivoDTO = new InformacionArchivoDTO();
                informacionArchivoDTO.setDocName(infoFile.getDocName());
                informacionArchivoDTO.setFileName(nombreArchivo);
                informacionArchivoDTO.setProcessName(infoFile.getProcessName());
                informacionArchivoDTO.setFileType(infoFile.getFileType());
                informacionArchivoDTO.setClasificable(infoFile.isClasificable());
                informacionArchivoDTO.setIdentificadorDocumento(nombreArchivo);
                informacionArchivoDTO.setVersionDocumento(blob.getGeneration().toString());
            }else{
            	//Si el archivo fue marcado para clasificar, y se presento un error en su proceso 
            	//se elimina del repositorio documental.
            	eliminarVersionArchivo(nombreArchivo, blob.getGeneration().toString());
            	informacionArchivoDTO = new InformacionArchivoDTO();
                informacionArchivoDTO.setDocName(infoFile.getDocName());
                informacionArchivoDTO.setFileName(nombreArchivo);
                informacionArchivoDTO.setProcessName(infoFile.getProcessName());
                informacionArchivoDTO.setFileType(infoFile.getFileType());
                informacionArchivoDTO.setClasificable(infoFile.isClasificable());	
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return informacionArchivoDTO;

        /*} catch (Exception e) {
            String msg = "No es posible crear el archivo " + infoFile.getDocName() + " -  " + infoFile.getFileName() + " en el bucket " + BUCKET_CAJA_COMPENSACION;
            logger.error(msg);
            throw new TechnicalException(msg, e);
        }
        */
    }

    @Override
    public InformacionArchivoDTO obtenerArchivo(String identificadorArchivo) {
    	Map<String, String> metadata = null;
    	String nombreArchivo = null;
        identificadorArchivo = identificadorArchivo.split(VERSION_CLIENT_SEPARATOR)[0];
        BlobId blobId = BlobId.of(BUCKET_CAJA_COMPENSACION, identificadorArchivo);
        Blob blob = storage.get(blobId);
        if (blob != null) {
            InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO();
            informacionArchivoDTO.setDataFile(blob.getContent());
            metadata = blob.getMetadata();
            //Se verifica si existe metadata asociado y esta contiene la propiedad nombre del documento
            if(metadata != null && 
        		metadata.get(DocumentMetadataConstants.NOMBRE_DOCUMENTO) != null){
            	nombreArchivo = metadata.get(DocumentMetadataConstants.NOMBRE_DOCUMENTO);
            }else{
            	nombreArchivo = blob.getName();
            }

            nombreArchivo = (Documents.getEnumType(blob.getContentType()).getExtension() != null && Documents.getEnumType(blob.getContentType()).getExtension() == ".txt") ? nombreArchivo : nombreArchivo + Documents.getEnumType(blob.getContentType()).getExtension();
            informacionArchivoDTO.setFileName(nombreArchivo);
            informacionArchivoDTO.setFileType(blob.getContentType());
            informacionArchivoDTO.setIdentificadorDocumento(identificadorArchivo);
            informacionArchivoDTO.setVersionDocumento(blob.getGeneration().toString());
            //Seguimiento al archivo
//            String dataFile = new String(informacionArchivoDTO.getDataFile());
//            System.out.println("obtenerArchivo - Archiivo: " + informacionArchivoDTO.getFileName());
//            System.out.println("dataFile: " + dataFile);
            return informacionArchivoDTO;
        } else {
            logger.debug("No se pudo encontrar el documento con el identificador: " + identificadorArchivo + " en el bucket " + BUCKET_CAJA_COMPENSACION);
            return null;
        }        
    }

    @Override
    public MultipartFormDataOutput consultarArchivo(String identificadorArchivo, String versionDocumento) {
        String[] srtIdAndVersion = identificadorArchivo.split(VERSION_CLIENT_SEPARATOR);
        identificadorArchivo = srtIdAndVersion[0];
        if (srtIdAndVersion.length == 2) {
            versionDocumento = srtIdAndVersion[1];
        }
        BlobId blobId = BlobId.of(BUCKET_CAJA_COMPENSACION, identificadorArchivo);
        Blob blob;
        if (versionDocumento == null) {
            blob = storage.get(blobId);
        } else {
            try {
                blob = storage.get(blobId, Storage.BlobGetOption.generationMatch(Long.valueOf(versionDocumento)));
            } catch (NumberFormatException nfe) {
                blob = storage.get(blobId);
            }
        }
        if (blob != null) {
            MultipartFormDataOutput mdo = new MultipartFormDataOutput();
            mdo.addFormData("file", blob.getContent(), MediaType.valueOf(blob.getContentType()));
            mdo.addFormData("fileName", blob.getName(), MediaType.valueOf(blob.getContentType()));
            return mdo;
        } else {
            logger.debug("No se pudo encontrar el documento con el identificador: " + identificadorArchivo + " en el bucket " + BUCKET_CAJA_COMPENSACION);
            return null;
        }
    }

    @Override
    public Response consultarArchivoInfoHeader(String identificadorArchivo, String versionDocumento, boolean toDownload) {
    	
    	Map<String, String> metadata = null;
    	
        Response.ResponseBuilder response;
        String[] srtIdAndVersion = identificadorArchivo.split(VERSION_CLIENT_SEPARATOR);
        identificadorArchivo = srtIdAndVersion[0];
        if (srtIdAndVersion.length == 2) {
            versionDocumento = srtIdAndVersion[1];
        }

        BlobId blobId = BlobId.of(BUCKET_CAJA_COMPENSACION, identificadorArchivo);
        Blob blob;
        if (versionDocumento == null) {
            blob = storage.get(blobId);
        } else {
            try {
                blob = storage.get(blobId, Storage.BlobGetOption.generationMatch(Long.parseLong(versionDocumento)));
            } catch (NumberFormatException nfe) {
                blob = storage.get(blobId);
            }
        }

        if (blob != null) {
            String getTipe;
            //Se obtiene la metadata del documento cargado
            metadata = blob.getMetadata();
            String nombreArchivo = null;
            //Se verifica si existe metadata asociado y esta contiene la propiedad nombre del documento
            if(metadata != null && 
        		metadata.get(DocumentMetadataConstants.NOMBRE_DOCUMENTO) != null){
            	nombreArchivo = metadata.get(DocumentMetadataConstants.NOMBRE_DOCUMENTO);
            }else{
            	nombreArchivo = blob.getName();
            }
            if (toDownload) {
                // DESCARGAR Documento original
                response = Response.ok(new ByteArrayInputStream(blob.getContent()));
                response.header(DocumentType.CONTENT_TYPE, blob.getContentType());
                getTipe = "attachment; filename=";
            } else {
                // VER Documento en PDF
                byte[] documentPDF;
                if (Documents.PDF.getMimeType().equals(blob.getContentType())) {
                    documentPDF = blob.getContent();
                } else {
                    try {
                        documentPDF = convertToPDF.convertToPDF(blob.getContent(), Documents.getEnumType(blob.getContentType()));
                    } catch (Exception e) {
                        // Dado que el proceso de reinicio de libreoffice toma un tiempo se espera 1 segundo para que pueda iniciar el proceso
                        documentPDF = covertirAPdf(blob);

                    }
                }
                if (documentPDF != null) {
                    response = Response.ok((new ByteArrayInputStream(documentPDF)));
                    response.header(DocumentType.CONTENT_TYPE, Documents.PDF.getMimeType());
                } else {
                    // DESCARGAR Documento original
                    response = Response.ok(new ByteArrayInputStream(blob.getContent()));
                    response.header(DocumentType.CONTENT_TYPE, blob.getContentType());
                }
                getTipe = "inline; filename=";
            }
            response.header(DocumentType.CONTENT_DISPOSITION, getTipe + nombreArchivo );
            return response.build();
        } else {
            logger.debug("No se pudo encontrar el documento con el identificador: " + identificadorArchivo + " en el bucket " + BUCKET_CAJA_COMPENSACION);
            return null;
        }
    }

    private byte[] covertirAPdf(Blob blob) {
        byte[] documentPDF;
        try {
            Thread.sleep(1000);
            documentPDF = convertToPDF.convertToPDF(blob.getContent(), Documents.getEnumType(blob.getContentType()));
        } catch (Exception e) {
            documentPDF = null;
        }
        return documentPDF;
    }

    @Override
    public void eliminarArchivo(String identificadorArchivo) {
        eliminarVersionArchivo(identificadorArchivo, null);
    }

    @Override
    public void eliminarVersionArchivo(String identificadorArchivo, String versionDocumento) {
        
        logger.debug(Interpolator.interpolate("Iniciando la eliminación del archivo con identificador {0}, versión {1}", identificadorArchivo, versionDocumento));
        
        BlobId blobId = BlobId.of(BUCKET_CAJA_COMPENSACION, identificadorArchivo);
        boolean eliminado;
        if (versionDocumento == null) {
            eliminado = storage.delete(blobId);
        } else {
            try {
                eliminado = storage.delete(blobId, Storage.BlobSourceOption.generationMatch(Long.parseLong(versionDocumento)));
            } catch (NumberFormatException nfe) {
                eliminado = storage.delete(blobId);
            }
        }
        if (eliminado) {
            //Eliminación de la entidad VersionArchivo
            if (versionDocumento == null) {
            	consultasCore.eliminarRegistroVersionArchivo(identificadorArchivo);
            } else {
            	consultasCore.eliminarRegistroVersionArchivo(identificadorArchivo, versionDocumento);
            }

            List<Long> idsArchivoAlmacenado = consultasCore.consultarRegistrosVersionArchivoPorId(identificadorArchivo); 
            
            if (idsArchivoAlmacenado != null && !idsArchivoAlmacenado.isEmpty() && idsArchivoAlmacenado.size() == 1) {
                //Eliminación de la entidad ArchivoAlmacenado
            	consultasCore.eliminarRegistroArchivoAlmacenado(idsArchivoAlmacenado.get(0));
            }
            logger.debug(Interpolator.interpolate("Archivo con identificador {0}, versión {1} eliminado correctamente", identificadorArchivo, versionDocumento));
        } else {
            logger.debug(Interpolator.interpolate("No se pudo eliminar el archivo con identificador {0}, versión {1}", identificadorArchivo, versionDocumento));
        }
    }

    private String generarNombreArchivo(InformacionArchivoDTO infoFile) {
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID().toString());
        String[] tokens = infoFile.getFileName().split("\\.");
        if (tokens.length > 1) {
            sb.append(".");
            sb.append(tokens[1].toLowerCase());
        }
        return sb.toString();
    }
    
    /**
     * Metodo que permite obtener la informacion del archivo a almacenar en el CGS 
     * e incorporarla como metadata del documento
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	informacionArchivoDTO
     * 	 	  	<code>InformacionArchivoDTO</code>
     * 		  	Estructura de datos con la informacion del archivo a almacenar
     * 
     * @return  <code>Map<String, String></code>
     * 		 	El mapa con las propiedades del documento encontradas 			
     */
    private Map<String, String> crearMetadataRequisitoDocumental(InformacionArchivoDTO informacionArchivoDTO){
    	String firmaServicio = "AlmacenamientoGoogle.crearMetadataRequisitoDocumental( InformacionArchivoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, String> metadata = new HashMap<String, String>(); 
		try {
			if(informacionArchivoDTO.getFileName() != null){
				metadata.put(DocumentMetadataConstants.FILE_NAME, 
					informacionArchivoDTO.getFileName());
			}
			if(informacionArchivoDTO.getIdSolicitud() != null){
				metadata.put(DocumentMetadataConstants.ID_SOLICITUD,
					informacionArchivoDTO.getIdSolicitud().toString());
			}
			if(informacionArchivoDTO.getIdRequisito() != null){
				metadata.put(DocumentMetadataConstants.ID_REQUISITO
					, informacionArchivoDTO.getIdRequisito().toString());
			}
			if(informacionArchivoDTO.getIdPersona() != null){
				metadata.put(DocumentMetadataConstants.ID_PERSONA
					, informacionArchivoDTO.getIdPersona().toString());
			}
			if(informacionArchivoDTO.getFileType() != null){
				metadata.put(DocumentMetadataConstants.FILE_TYPE, 
					informacionArchivoDTO.getFileType());
			}
			if(informacionArchivoDTO.getTipoIdentificacionPropietario() != null){
				metadata.put(DocumentMetadataConstants.TIPO_IDENTIFICACION_PROPIETARIO, 
					informacionArchivoDTO.getTipoIdentificacionPropietario().getDescripcion());
			}
			if(informacionArchivoDTO.getNumeroIdentificacionPropietario() != null){
				metadata.put(DocumentMetadataConstants.NUMERO_IDENTIFICACION_PROPIETARIO, 
					informacionArchivoDTO.getNumeroIdentificacionPropietario());
			}
			if(informacionArchivoDTO.getTipoPropietario() != null){
				metadata.put(DocumentMetadataConstants.TIPO_PROPIETARIO, 
					informacionArchivoDTO.getTipoPropietario().toString());
			}
			if( informacionArchivoDTO.isClasificable() ){
				metadata.put(DocumentMetadataConstants.DOCUMENTO_CLASIFICADO, "SI");
			}else{
				metadata.put(DocumentMetadataConstants.DOCUMENTO_CLASIFICADO, "NO");
			}
			if(informacionArchivoDTO.getProcessName() != null){
				metadata.put(DocumentMetadataConstants.PROCESO_TRANSACCION, 
					informacionArchivoDTO.getProcessName());
			}
			if(informacionArchivoDTO.getIdInstanciaProceso() != null){
				metadata.put(DocumentMetadataConstants.ID_INSTANCIA_PROCESO, 
					informacionArchivoDTO.getIdInstanciaProceso());
			}
			if(informacionArchivoDTO.getDescription() != null){
				metadata.put(DocumentMetadataConstants.DESCRIPCION_DOCUMENTO, 
					informacionArchivoDTO.getDescription());
			}
			if(informacionArchivoDTO.getDocName() != null){
				metadata.put(DocumentMetadataConstants.NOMBRE_DOCUMENTO, 
					informacionArchivoDTO.getDocName());
			}
			if(informacionArchivoDTO.getSize() != null){
				metadata.put(DocumentMetadataConstants.SIZE_DOCUMENTO, 
					getStringSizeLengthFile(informacionArchivoDTO.getSize()));
			}
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
			metadata = null;
		}
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    	return metadata ;
    }
    
	/**
	 * Metodo que permite realizar la conversion del tamaño a bytes, kilobytes, megabytes o Gigabytes 
	 * dada la longitud del tamaño en bytes
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param size
	 * 		  <code>long</code>
	 * 		  El tamanio del archivo 
	 * 
	 * @return <code>String</code>
	 * 		  El tamanio del archivo expresado en bytes, kilobytes, megabytes o Gigabytes
	 */
	public String getStringSizeLengthFile(long size) {
	    DecimalFormat df = new DecimalFormat("0.00");
	    float sizeKb = 1024.0f;
	    float sizeMo = sizeKb * sizeKb;
	    float sizeGo = sizeMo * sizeKb;
	    float sizeTerra = sizeGo * sizeKb;
	    if(size < sizeMo)
	        return df.format(size / sizeKb)+ " KB";
	    else if(size < sizeGo)
	        return df.format(size / sizeMo) + " MB";
	    else if(size < sizeTerra)
	        return df.format(size / sizeGo) + " GB";

	    return size + " bytes ";
	}
	
   
}
