package com.asopagos.archivos.almacenamiento.alfresco.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.util.FileUtils;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import com.asopagos.archivos.almacenamiento.alfresco.api.model.ContainerEntry;
import com.asopagos.archivos.almacenamiento.alfresco.api.model.ContainerList;
import com.asopagos.archivos.almacenamiento.alfresco.api.model.NetworkEntry;
import com.asopagos.archivos.almacenamiento.alfresco.api.model.NetworkList;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;

/**
 * This class contains constants and methods that are common across
 * the Alfresco Public API regardless of where the target repository is
 * hosted.
 * This class is based in jpotts's example hosted in github
 * @author Leonardo Giral <a href="mailto:ogiral@heinsohn.com.co"></a>
 *
 */
public abstract class BaseAPIClient {
    /**
     * Default Alfresco public access URLS
     */
    public static final String SITES_URL = "/public/alfresco/versions/1/sites/";
    public static final String NODES_URL = "/public/alfresco/versions/1/nodes/";
    /**
     * Logger's Reference
     */
    private final ILogger logger = LogManager.getLogger(BaseAPIClient.class);
    /**
     * It's an a grupation of users where the current connetion user belong
     */
    private String homeNetwork;

    /**
     * Use the CMIS API to get a handle to the root folder of the
     * target site, then create a new folder, then create
     * a new document in the new folder
     *
     * @param parentFolderId
     *        Id of parent folder where new folder will be create
     * @param folderName
     *        The name of the new folder
     * @return Folder
     *         Folder's object created
     * 
     */
    public Folder createFolder(String parentFolderId, String folderName) {
        Session cmisSession = getCmisSession();
        Folder rootFolder = (Folder) cmisSession.getObject(parentFolderId);
        Folder subFolder;
        try {
            // Making an assumption here that you probably wouldn't normally do
            subFolder = (Folder) cmisSession.getObjectByPath(rootFolder.getPath() + "/" + folderName);
            logger.debug("Folder already existed!");
        } catch (CmisObjectNotFoundException onfe) {
            Map<String, Object> props = new HashMap<>();
            props.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
            props.put(PropertyIds.NAME, folderName);
            subFolder = rootFolder.createFolder(props);
            String subFolderId = subFolder.getId();
            logger.debug("Created new folder: " + subFolderId);
        }
        return subFolder;
    }

    /**
     * Metodo que permite crear una subcarpeta o tomar la referencia de
     * la misma si existe indicado en <code>parentFolder</code> y
     * <code>folderName</code>
     * 
     * @param parentFolder
     *        <code>Folder</code> el directorio padre donde se creara la carpeta
     * @param folderName
     *        <code>Folder</code> el sub directorio a crear
     * 
     * @return <code>Folder</code>
     *         La carpeta indicada
     * 
     * @throws TechnicalException
     */
    public Folder createChildFolder(final Folder parentFolder, final String folderName) throws TechnicalException {
        Folder childFolder;
        Session cmisSession = getCmisSession();
        try {
            return FileUtils.createFolder(parentFolder, folderName, "cmis:folder");
        } catch (CmisContentAlreadyExistsException e) {
            logger.debug("La carpeta hija ya existe");
            childFolder = (Folder) cmisSession.getObjectByPath(parentFolder.getPath() + "/" + folderName);
            return childFolder;
        } catch (Exception e) {
            logger.debug("no se creo folder hijo");
            throw new TechnicalException(e);
        }
    }

    /**
     * Get's The current connetion user belong
     * @return Current user network
     * @throws IOException
     *         When it's not possible get connection with Alfresco
     */
    public String getHomeNetwork() throws IOException {
        if (this.homeNetwork == null) {
            GenericUrl url = new GenericUrl(getAlfrescoAPIUrl());

            HttpRequest request = getRequestFactory().buildGetRequest(url);

            NetworkList networkList = request.execute().parseAs(NetworkList.class);
            logger.debug("Found " + networkList.list.pagination.totalItems + " networks.");
            for (NetworkEntry networkEntry : networkList.list.entries) {
                if (networkEntry.entry.homeNetwork) {
                    this.homeNetwork = networkEntry.entry.id;
                }
            }

            if (this.homeNetwork == null) {
                this.homeNetwork = "-default-";
            }

            logger.debug("The current home network appears to be: " + homeNetwork);
        }
        return this.homeNetwork;
    }

    /**
     * @param parentFolder
     * @param file
     * @param fileType
     * @return
     * @throws FileNotFoundException
     */
    public Document createDocument(Folder parentFolder, File file, String fileType) throws FileNotFoundException {
        return createDocument(parentFolder, file, fileType, null);
    }

    /**
     * Use the CMIS API to create a document in a folder
     *
     * @param parentFolder
     * @param file
     * @param fileType
     * @param props
     * @return
     * @throws FileNotFoundException
     *
     *
     */
    public Document createDocument(Folder parentFolder, File file, String fileType, Map<String, Object> props)
            throws FileNotFoundException {

        Session cmisSession = getCmisSession();

        String fileName = file.getName();

        // create a map of properties if one wasn't passed in
        if (props == null) {
            props = new HashMap<>();
        }

        // Add the object type ID if it wasn't already
        if (props.get("cmis:objectTypeId") == null) {
            props.put("cmis:objectTypeId", "cmis:document");
        }

        // Add the name if it wasn't already
        if (props.get("cmis:name") == null) {
            props.put("cmis:name", fileName);
        }

        ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, file.length(), fileType,
                new FileInputStream(file));

        Document document;
        try {
            document = parentFolder.createDocument(props, contentStream, null);
            logger.debug("Created new document: " + document.getId());
        } catch (CmisContentAlreadyExistsException ccaee) {
            document = (Document) cmisSession.getObjectByPath(parentFolder.getPath() + "/" + fileName);
            logger.debug("Document already exists: " + fileName);
        }

        return document;
    }

    /**
     * Use the REST API to find the documentLibrary folder for
     * the target site
     * @param site
     * @return String
     *
     * @author jpotts
     * @throws java.io.IOException
     *
     */
    public String getRootFolderId(String site) throws IOException {

        GenericUrl containersUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + SITES_URL + site + "/containers");
        logger.debug(containersUrl);
        HttpRequest request = getRequestFactory().buildGetRequest(containersUrl);
        ContainerList containerList = request.execute().parseAs(ContainerList.class);
        String rootFolderId = null;
        for (ContainerEntry containerEntry : containerList.list.entries) {
            if (containerEntry.entry.folderId.equals("documentLibrary")) {
                rootFolderId = containerEntry.entry.id;
                break;
            }
        }
        return rootFolderId;
    }

    /**
     * Use the REST API to "like" an object
     *
     * @param objectId
     * @throws IOException
     */
    public void like(String objectId) throws IOException {
        GenericUrl likeUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + NODES_URL + objectId + "/ratings");
        HttpContent body = new ByteArrayContent("application/json", "{\"id\": \"likes\", \"myRating\": true}".getBytes());
        HttpRequest request = getRequestFactory().buildPostRequest(likeUrl, body);
        request.execute();
        logger.debug("You liked: " + objectId);
    }

    /**
     * Use the REST API to comment on an object
     *
     * @param objectId
     * @param comment
     * @throws IOException
     */
    public void comment(String objectId, String comment) throws IOException {
        GenericUrl commentUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + NODES_URL + objectId + "/comments");
        HttpContent body = new ByteArrayContent("application/json", ("{\"content\": \"" + comment + "\"}").getBytes());
        HttpRequest request = getRequestFactory().buildPostRequest(commentUrl, body);
        request.execute();
        logger.debug("You commented on: " + objectId);
    }

    /**
     * @return
     */
    public abstract String getSite();

    /**
     * @return
     */
    protected abstract String getAlfrescoAPIUrl();

    /**
     * @return
     */
    protected abstract Session getCmisSession();

    /**
     * @return
     */
    protected abstract HttpRequestFactory getRequestFactory();
}
