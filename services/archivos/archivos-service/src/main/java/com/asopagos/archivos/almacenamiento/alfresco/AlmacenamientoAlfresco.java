package com.asopagos.archivos.almacenamiento.alfresco;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.archivos.almacenamiento.IAlmacenamientoArchivos;
import com.asopagos.archivos.almacenamiento.alfresco.client.BaseClient;
import com.asopagos.archivos.constants.DocumentType;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

/**
 * Clase que implementa las funcionalidades de almacenamiento en el ECM Alfresco
 * @author Leonardo Giral
 */
@Stateless
public class AlmacenamientoAlfresco extends BaseClient implements IAlmacenamientoArchivos {
    

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AlmacenamientoAlfresco.class);
    
    private static final String PDF_FOLDER = "PDF";
    
    private static final String PILA_PROCESS_NAME = "PILA";

    private static final String TEMPORAL_NAME_FILE_ES = "(Copia de trabajo)";
    
    private static final String TEMPORAL_NAME_FILE_EN = "(Working Copy)";
    
    private static final String EXT_SEPARATOR = "\\.";
    
    private static final String URL_SEPARATOR = "/";
    
    /**
     * Datos de conexión al EMC
     */
    private static final String ECM_USERNAME = (String) CacheManager.getConstante(ConstantesSistemaConstants.ECM_USERNAME);
    private static final String ECM_PASSWORD = (String) CacheManager.getConstante(ConstantesSistemaConstants.ECM_PASSWORD);
    private static final String ECM_HOST = (String) CacheManager.getConstante(ConstantesSistemaConstants.ECM_HOST);

    /**
     * Sitio en el ECM de la caja de compensación
     */
    private String CAJA_COMPENSACION_SITE = null;
    
    /**
     * Inicializador del EJB
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     */
    @PostConstruct
    public void inicializador() {
    	CAJA_COMPENSACION_SITE = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_SITE);
    }


    /**
     * Retorna la ruta de acceso público al EMC
     * 
     * @return
     */
    @Override
    protected String getAlfrescoAPIUrl() {
        return ECM_HOST + "/api/";
    }
    
    /**
     * Obtiene el SITE definido para la CCF
     * 
     * @return
     */
    @Override
    public String getSite() {
        return CAJA_COMPENSACION_SITE;
    }

    /**
     * Retorna el usuario de conexión con el ECM
     * 
     * @return
     */
    @Override
    protected String getUsername() {
        return ECM_USERNAME;
    }

    /**
     * Retorna el password del usuario de conexión al ECM
     * 
     * @return
     */
    @Override
    protected String getPassword() {
        return ECM_PASSWORD;
    }    
        

    @Override
    public InformacionArchivoDTO almacenarArchivo(InformacionArchivoDTO infoFile) {
        logger.info("ingresa almacenarArchivo alfresco");
        String rootFolderId;
        InformacionArchivoDTO informacionArchivoDTO = null;
        Document documento;
        Folder subFolder;
        Folder subFolderPlantilla;

        validateInfoFile(infoFile);
        try {
            // Se localiza el folder raíz del SITE de la CCF
            rootFolderId = getRootFolderId(getSite());
            // Se crea nuevo directorio en el directorio raíz con el nombre del
            // proceso en el que se carga el archivo
            subFolder = createFolder(rootFolderId, infoFile.getProcessName());

            logger.debug(
                    "ArchivosBusiness.almacenarArchivo :: Si existe el atributo IdInstanciaProceso entonces se crea una subcarpeta para el almacenamiento del archivo");
            // Si existe el atributo IdInstanciaProceso entonces se crea una
            // subcarpeta para el almacenamiento del archivo
            if (infoFile.getIdInstanciaProceso() != null) {
                subFolderPlantilla = createChildFolder(subFolder, infoFile.getIdInstanciaProceso());
                documento = createDocument(subFolderPlantilla, infoFile);
            } else {
                documento = createDocument(subFolder, infoFile);
            }
            informacionArchivoDTO = new InformacionArchivoDTO();
            if (infoFile.isFront()) {
                // Se retorna entre comillas el id del objeto debido al
                // componente de visualización utilizado en AngularJS
                informacionArchivoDTO.setDocName(infoFile.getDocName());
                informacionArchivoDTO.setProcessName(infoFile.getProcessName());
                informacionArchivoDTO.setFileType(infoFile.getFileType());
                informacionArchivoDTO.setIdentificadorDocumento(documento.getVersionSeriesId());
                informacionArchivoDTO.setVersionDocumento(documento.getVersionLabel());
                return informacionArchivoDTO;
            } else {
                informacionArchivoDTO.setDocName(infoFile.getDocName());
                informacionArchivoDTO.setProcessName(infoFile.getProcessName());
                informacionArchivoDTO.setFileType(infoFile.getFileType());
                informacionArchivoDTO.setIdentificadorDocumento(documento.getVersionSeriesId());
                informacionArchivoDTO.setVersionDocumento(documento.getVersionLabel());
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new TechnicalException("No es posible crear el archivo " + infoFile.getDocName() + " -  " + infoFile.getFileName(), e);
        }
        return informacionArchivoDTO;
    }
    
    @Override
    public InformacionArchivoDTO obtenerArchivo(String identificadorArchivo) {
        
        ContentStream documentData = null;
        Document documento;
        List<Document> lstDocumentoVersiones;
        String[] srtIdAndVersion;
        String idDocumento;
        String versionDocumento = null;

        if (identificadorArchivo == null) {
            logger.error("No se ha indicado un identificador de archivo ");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        Session cmisSession = getCmisSession();
        Document documentFamily;

        srtIdAndVersion = identificadorArchivo.split(VERSION_CLIENT_SEPARATOR);
        if (srtIdAndVersion.length == 2) {
            idDocumento = srtIdAndVersion[0];
            versionDocumento = srtIdAndVersion[1];
        } else {
            idDocumento = identificadorArchivo;
        }
        try {
            CmisObject cmisObject = cmisSession.getObject(idDocumento);
            if (cmisObject instanceof Document) {
                documentFamily = (Document) cmisObject;
                if (versionDocumento != null) {
                    lstDocumentoVersiones = documentFamily.getAllVersions();
                    for (Document docVersion : lstDocumentoVersiones) {
                        if (versionDocumento.equals(docVersion.getVersionLabel())) {
                            documentData = docVersion.getContentStream();
                            break;
                        }
                    }
                } else { 
                    documento = documentFamily.getObjectOfLatestVersion(false);
                    documentData = documento.getContentStream();
                    versionDocumento = documento.getVersionLabel();
                }

                InformacionArchivoDTO in = new InformacionArchivoDTO();
                byte[] bytes;
                try {
                    bytes = IOUtils.toByteArray(documentData.getStream());
                    in.setDataFile(bytes);
                    in.setFileName(documentData.getFileName());
                    in.setFileType(documentData.getMimeType());
                    in.setIdentificadorDocumento(idDocumento);
                    in.setVersionDocumento(versionDocumento);
                } catch (IOException e) {
                    String message = " No se encuentra un documento con el identificador: " + idDocumento;
                    logger.debug(message, e);
                    return null;
                }
                return in;
            } else {
                logger.debug("El id dado no corresponde a un objeto documento");
                return null;
            }
        } catch (CmisObjectNotFoundException confe) {
            String message = " No se encuentra un documento con el identificador: " + identificadorArchivo;
            logger.debug(message, confe);
            return null;
        }
    }

    @Override
    public MultipartFormDataOutput consultarArchivo(String identificadorArchivo, String versionDocumento) {
        
        String[] srtIdAndVersion;
        String idDocumento;
        ContentStream documentData = null;
        Document documentFamily;
        List<Document> lstDocumentoVersiones;

        if (identificadorArchivo == null) {
            logger.error("No se ha indicado un identificador de archivo ");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        Session cmisSession = getCmisSession();
        srtIdAndVersion = identificadorArchivo.split(VERSION_CLIENT_SEPARATOR);
        if (srtIdAndVersion.length == 2) {
            idDocumento = srtIdAndVersion[0];
            versionDocumento = srtIdAndVersion[1];
        } else {
            idDocumento = identificadorArchivo;
        }
        try {
            CmisObject cmisObject = cmisSession.getObject(idDocumento);
            if (cmisObject instanceof Document) {
                documentFamily = (Document) cmisObject;
                if (versionDocumento != null) {
                    lstDocumentoVersiones = documentFamily.getAllVersions();
                    for (Document docVersion : lstDocumentoVersiones) {
                        if (versionDocumento.equals(docVersion.getVersionLabel())) {
                            documentData = docVersion.getContentStream();
                            break;
                        }
                    }
                } else {
                    documentData = documentFamily.getObjectOfLatestVersion(false).getContentStream();
                }
                MultipartFormDataOutput mdo = new MultipartFormDataOutput();
                mdo.addFormData("file", documentData.getStream(), MediaType.valueOf(documentData.getMimeType()));
                mdo.addFormData("fileName", documentData.getFileName(), MediaType.valueOf(documentData.getMimeType()));
                return mdo;
                
            } else {
                logger.debug("El id dado no corresponde a un objeto documento");
            }
        } catch (CmisObjectNotFoundException confe) {
            String message = " No se encuentra un documento con el identificador: " + idDocumento;
            logger.debug(message, confe);
        }
        return null;
    }

    @Override
    public Response consultarArchivoInfoHeader(String identificadorArchivo, String versionDocumento, boolean toDownload) {

        Session cmisSession;
        Response.ResponseBuilder response;
        Document documentPDF;
        Document documentFamily;
        Document documentSelected;

        List<Document> lstDocumentoVersiones;
        ContentStream documentData = null;

        String[] srtIdAndVersion;
        String idDocumento;

        if (identificadorArchivo == null) {
            logger.error("No se ha indicado un identificador de archivo ");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        cmisSession = getCmisSession();
        srtIdAndVersion = identificadorArchivo.split(VERSION_CLIENT_SEPARATOR);
        if (srtIdAndVersion.length == 2) {
            idDocumento = srtIdAndVersion[0];
            versionDocumento = srtIdAndVersion[1];
        } else {
            idDocumento = identificadorArchivo;
        }
        try {
            CmisObject cmisObject = cmisSession.getObject(idDocumento);
            if (cmisObject instanceof Document) {
                documentFamily = (Document) cmisObject;
                if (versionDocumento != null) {
                    lstDocumentoVersiones = documentFamily.getAllVersions();
                    for (Document docVersion : lstDocumentoVersiones) {
                        if (versionDocumento.equals(docVersion.getVersionLabel())) {
                            documentSelected = docVersion;
                            documentData = documentSelected.getContentStream();
                            break;
                        }
                    }
                } else {
                    documentSelected = documentFamily.getObjectOfLatestVersion(false);
                    documentData = documentSelected.getContentStream();
                }

                if (toDownload) {
                    // DESCARGAR documento original
                    response = Response.ok((documentData.getStream()));
                    response.header(DocumentType.CONTENT_TYPE, documentFamily.getContentStreamMimeType());
                } else {
                    // VER Documento en PDF
                    documentPDF = obtenerDocumentoPdf(cmisSession, documentFamily, versionDocumento);
                    if (documentPDF != null) {
                        response = Response.ok((documentPDF.getContentStream().getStream()));
                        response.header(DocumentType.CONTENT_TYPE, documentPDF.getContentStreamMimeType());
                    } else {
                        // DESCARGAR documento original
                        response = Response.ok((documentData.getStream()));
                        response.header(DocumentType.CONTENT_TYPE, documentFamily.getContentStreamMimeType());
                    }
                }
                String getTipe = toDownload ? "attachment; filename=" : "inline; filename=";
                response.header(DocumentType.CONTENT_DISPOSITION, getTipe + documentFamily.getContentStreamFileName());
                return response.build();
            } else {
                logger.debug("El id dado no corresponde a un objeto documento");
            }
        } catch (CmisObjectNotFoundException confe) {
            String message = " No se encuentra un documento con el identificador: " + idDocumento;
            logger.debug(message, confe);
        }
        return null;
    }
    
    @Override
    public void eliminarArchivo(String identificadorArchivo) {
        
        Document documentoOriginal;
        Document documentPDF;
        if (identificadorArchivo == null) {
            logger.error("No se ha indicado un identificador de archivo ");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        Session cmisSession = getCmisSession();
        try {
            CmisObject cmisObject = cmisSession.getObject(identificadorArchivo);
            if (cmisObject instanceof Document) {
                // cmisObject.delete(true);
                documentoOriginal = ((Document) cmisObject).getObjectOfLatestVersion(false);
                documentPDF = obtenerDocumentoPdf(cmisSession, documentoOriginal, null);
                documentoOriginal.delete(false);
                if (documentPDF != null) {
                    documentPDF.delete(false);
                }
                logger.debug("El documento: " + identificadorArchivo + "se ha eliminado");
            } else {
                logger.debug("El id dado no corresponde a un objeto documento");
            }
        } catch (CmisObjectNotFoundException confe) {
            String message = " No se encuentra un documento con el identificador: " + identificadorArchivo;
            logger.debug(message, confe);
        }
    }
    
    @Override
    public void eliminarVersionArchivo(String identificadorArchivo, String versionDocumento) {

        Document documentPDF;
        Document documentoOriginal;
        List<Document> versions;

        if (identificadorArchivo == null) {
            logger.error("No se ha indicado un identificador de archivo ");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        Session cmisSession = getCmisSession();
        try {
            CmisObject cmisObject = cmisSession.getObject(identificadorArchivo);
            if (cmisObject instanceof Document) {

                if (versionDocumento == null) {
                    // cmisObject.delete(true);
                    documentoOriginal = ((Document) cmisObject).getObjectOfLatestVersion(false);
                    documentPDF = obtenerDocumentoPdf(cmisSession, documentoOriginal, versionDocumento);
                    if (documentPDF != null) {
                        documentPDF.delete(false);
                    }
                    documentoOriginal.delete(false);
                    logger.debug("El documento: " + identificadorArchivo + "se ha eliminado");
                } else {
                    versions = ((Document) cmisObject).getAllVersions();
                    for (Document documentoV : versions) {
                        if (versionDocumento.equals(documentoV.getVersionLabel())) {
                            documentPDF = obtenerDocumentoPdf(cmisSession, documentoV, versionDocumento);
                            if (documentPDF != null) {
                                documentPDF.delete(false);
                            }
                            documentoV.delete(false);
                            logger.debug("El documento: " + identificadorArchivo + " y numero de version: " + versionDocumento
                                    + " se ha eliminado");
                        }
                    }
                }
            } else {
                logger.debug("El id dado no corresponde a un objeto documento");
            }
        } catch (CmisObjectNotFoundException confe) {
            String message = " No se encuentra un documento con el identificador: " + identificadorArchivo;
            logger.debug(message, confe);
        }
    }    
    
    /**
     * Valida que se haya enviado la información necesaria para almacenar el
     * archivo
     * 
     * @param infoFile
     */
    private void validateInfoFile(InformacionArchivoDTO infoFile) {
        try {
            Validate.notNull(infoFile);
            Validate.notNull(infoFile.getDataFile(), "\n\tNo se ha enviado información de archivo a almacenar");
            Validate.notNull(infoFile.getDocName(), "\n\tNo se ha indicado el nombre del archivo a almacenar");
            Validate.notNull(infoFile.getProcessName(), "\n\tNo se ha indicado el proceso en el que se genera el archivo a almacenar");
            Validate.notNull(infoFile.getFileType(), "\n\tNo se ha indicado el tipo del archivo a almacenar");
        } catch (RuntimeException rte) {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS + rte.getMessage());
        }
    }

    /**
     * Crea un documento con la información idicada en <code>infoFile</code> en
     * el directorio <code>parentFolder</code>.
     * 
     * @param parentFolder
     *        Directorio al que se le agregará el nuevo documento
     * @param infoFile
     *        Contiene la información del archivo a almacenar
     * @return Objeto {@link Document} CMIS que representa el documento recién
     *         creado
     */
    private Document createDocument(Folder parentFolder, InformacionArchivoDTO infoFile) {

        String descripcionPath = null;
        Map<String, Object> props = new HashMap<>(3);
        // Add the object type ID
        props.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        // Add the name
        props.put(PropertyIds.NAME, infoFile.getFileName());
        props.put(PropertyIds.CONTENT_STREAM_FILE_NAME, infoFile.getFileName());
        props.put(PropertyIds.CONTENT_STREAM_MIME_TYPE, getMimeType(infoFile));
        props.put(PropertyIds.CREATION_DATE, new GregorianCalendar());

        if (infoFile.getIdInstanciaProceso() != null) {
            descripcionPath = infoFile.getIdInstanciaProceso();
        }

        props.put(PropertyIds.DESCRIPTION, descripcionPath);
        borrarCaracteresEspeciales(infoFile);
        Session cmisSession = getCmisSession();

        ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(infoFile.getFileName(), -1, getMimeType(infoFile),
                new ByteArrayInputStream(infoFile.getDataFile()));

        Document document = null;
        try {
            eliminarTemporalesPdf(parentFolder, document, infoFile.getIdInstanciaProceso());

            document = parentFolder.createDocument(props, contentStream, null);
            logger.debug("Se creado el nuevo documento con id: " + document.getId());

        } catch (CmisContentAlreadyExistsException ccaee) {
        	/* 	IMPORTANTE Se comenta el siguiente código, para dar solución temporal al error Caused by: org.apache.chemistry.opencmis.commons.exceptions.CmisVersioningException: Check out failed: This node is already checked out.
        	 	Para restaurar, descomentar el siguiente snippet y comentar el código entre los comentarios FV-001 */
        	
//            String pathObj = parentFolder.getPath() + "/" + infoFile.getFileName();
//            document = (Document) cmisSession.getObjectByPath(pathObj);
//            logger.debug("El documento: " + infoFile.getDocName() + ", ya existe, se creará una nueva versión");            
//            ObjectId idOfCheckedOutdocumentument = document.checkOut();
//            Document pwc = (Document) cmisSession.getObject(idOfCheckedOutdocumentument);
//            contentStream = cmisSession.getObjectFactory().createContentStream(infoFile.getFileName(), -1, getMimeType(infoFile),
//                    new ByteArrayInputStream(infoFile.getDataFile()));
//            ObjectId objectId = pwc.checkIn(false, null, contentStream, null);
//            document = (Document) cmisSession.getObject(objectId);
//            logger.debug("La nueva versión del documento es:" + document.getVersionLabel());
        	
        	// Inicia FV001
        	String pathObj = parentFolder.getPath() + "/" + infoFile.getFileName();
            document = (Document) cmisSession.getObjectByPath(pathObj);
            logger.debug("El documento: " + infoFile.getDocName() + ", ya existe, se creará una nueva versión");
            Document pwc;
            
            try {
	            ObjectId idOfCheckedOutdocumentument = document.checkOut();
	            pwc = (Document) cmisSession.getObject(idOfCheckedOutdocumentument);
	            contentStream = cmisSession.getObjectFactory().createContentStream(infoFile.getFileName(), -1, getMimeType(infoFile),
	                    new ByteArrayInputStream(infoFile.getDataFile()));
            } catch(Exception e) {
            	pwc = document;
            }
            
            ObjectId objectId = pwc.checkIn(false, null, contentStream, null);
            document = (Document) cmisSession.getObject(objectId);
            logger.debug("La nueva versión del documento es:" + document.getVersionLabel());
            // Finaliza FV-001
        } catch (CmisRuntimeException excep) {
            logger.error("Error en el versionamiento del documento: duplicacion de archivo", excep);
            logger.debug("Finaliza createDocument(Folder, InformacionArchivoDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        return document;
    }
    
    private void eliminarTemporalesPdf(Folder parentFolder, Document document, String idInstanciaProceso) {
        Folder pdfFolder;
        Session cmisSession = getCmisSession();
        try {
            pdfFolder = getDirectorioPDF(parentFolder, idInstanciaProceso);
            if (pdfFolder != null) {
                for (CmisObject child : pdfFolder.getChildren()) {
                    if (child.getName().contains(TEMPORAL_NAME_FILE_ES) || child.getName().contains(TEMPORAL_NAME_FILE_EN)) {
                        Document doc = (Document) cmisSession.getObject(child.getId());
                        if (doc instanceof Document) {
                            child.delete(true);
                        }
                    }
                }
            }
        } catch (CmisObjectNotFoundException exception) {
            logger.debug("No se encontro una carpeta PDF");
        }
    }

    private Folder getDirectorioPDF(Folder parentFolder, String idInstanciaProceso) {
        Folder mainFolder;
        Folder pdfFolder = null;
        Session cmisSession = getCmisSession();
        try {
            if (idInstanciaProceso != null) {
                mainFolder = parentFolder.getFolderParent();
            } else {
                mainFolder = parentFolder;
            }
            pdfFolder = (Folder) cmisSession.getObjectByPath(mainFolder.getPath() + "/" + PDF_FOLDER + "/");
        } catch (CmisObjectNotFoundException exception) {
            logger.debug("No se encontro una carpeta PDF");
        }
        return pdfFolder;
    }

    /**
     * @param infoFile
     *        Retorna el Mimetype del archivo a ser cargado
     * @return
     */
    private String getMimeType(InformacionArchivoDTO infoFile) {
        return infoFile.getFileType();
    }    
    
    /**
     * Eliminar los caracteres especiales del nombre de archivo para su versionamiento en el ECM
     * 
     * @param infoFile
     *        <code>InformacionArchivoDTO</code>
     *        Representa los metadatos de los archivos a cargar en el ECM
     */
    private void borrarCaracteresEspeciales(InformacionArchivoDTO infoFile) {
        // Clean File Name. The name of file doesn't may have special characters
        // Replace spaces and slash to "_"
        // Se excluyen los procesos que requieren no modificar el nombre de los archivos 
        if (!PILA_PROCESS_NAME.equals(infoFile.getProcessName())
                && !ProcesoEnum.PAGO_APORTES_MANUAL.name().equals(infoFile.getProcessName())
                && !ProcesoEnum.DEVOLUCION_APORTES.name().equals(infoFile.getProcessName())
                && !ProcesoEnum.CORRECCION_APORTES.name().equals(infoFile.getProcessName())
                && !ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name().equals(infoFile.getProcessName())) {
            infoFile.setFileName(infoFile.getFileName().replaceAll("[^a-zA-Z.\\d]", "_"));
        }
    }    
    
    private Document obtenerDocumentoPdf(Session cmisSession, Document documentFamily, String versionDocumento) {
        Document documentPDF = null;
        String strPath = documentFamily.getPaths().iterator().next();
        String strSubFolder;
        String strFileName;
        String[] srtPDFPath;
        List<Document> lstDocumentoVersiones;
        strSubFolder = documentFamily.getProperty(PropertyIds.DESCRIPTION).getValue();
        if (strSubFolder != null) {
            strPath = strPath.replace(strSubFolder, PDF_FOLDER);
            srtPDFPath = strPath.split(EXT_SEPARATOR);
            if (srtPDFPath.length == 2) {
                strPath = srtPDFPath[0] + "." + PDF_FOLDER.toLowerCase();
            } else {
                strPath = null;
            }
        } else {
            strFileName = String.valueOf(documentFamily.getProperty(PropertyIds.NAME).getValueAsString());
            srtPDFPath = strPath.split(strFileName);
            if (srtPDFPath.length == 2) {
                strPath = srtPDFPath[0] + URL_SEPARATOR + PDF_FOLDER.toUpperCase() + URL_SEPARATOR + strFileName.split(EXT_SEPARATOR)[0]
                        + PDF_FOLDER.toLowerCase();
            } else {
                strPath = srtPDFPath[0] + PDF_FOLDER.toUpperCase() + URL_SEPARATOR + strFileName.split(EXT_SEPARATOR)[0] + "."
                        + PDF_FOLDER.toLowerCase();
            }
        }
        try {
            if (strPath != null) {
                documentPDF = (Document) cmisSession.getObjectByPath(strPath);
                if (versionDocumento != null) {
                    lstDocumentoVersiones = documentPDF.getAllVersions();
                    for (Document docVersion : lstDocumentoVersiones) {
                        if (versionDocumento.equals(docVersion.getVersionLabel())) {
                            documentPDF = docVersion;
                            break;
                        }
                    }
                } else {
                    documentPDF = documentPDF.getObjectOfLatestVersion(false);
                }
            }
        } catch (CmisObjectNotFoundException excep) {
            logger.error("Error obteniendo el PDF: No se logro encontrar el documento o puede no existir", excep);
            logger.debug("Finaliza obtenerDocumentoPdf(Session, Document, String)");
        }
        return documentPDF;
    }    

}
