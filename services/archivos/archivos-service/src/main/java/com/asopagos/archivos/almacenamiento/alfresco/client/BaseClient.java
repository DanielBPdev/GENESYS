package com.asopagos.archivos.almacenamiento.alfresco.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * This class contains only the logic that is specific to using the Public API
 * against an Alfresco repository running on-premise (5.0 or later).
 * This class is based in jpotts's example hosted in github
 * @author Leonardo Giral <a href="mailto:ogiral@heinsohn.com.co"></a>
 */
public abstract class BaseClient extends BaseAPIClient {
    
    //URL base Atompub ALFRESCO
    public static final String CMIS_URL = "/public/cmis/versions/1.1/atom";
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private HttpRequestFactory requestFactory;
    private Session cmisSession;
    /**
     * Logger's Reference
     */
    private final ILogger log = LogManager.getLogger(BaseClient.class);

    /**
     * @param requestFactory
     * @return
     */
    public String getAtomPubURL(HttpRequestFactory requestFactory) {
        String alfrescoAPIUrl = getAlfrescoAPIUrl();
        String atomPubURL;
        try {
            atomPubURL = alfrescoAPIUrl + getHomeNetwork() + CMIS_URL;
        } catch (IOException ioe) {
            log.debug("Warning: Couldn't determine home network, defaulting to -default-");
            atomPubURL = alfrescoAPIUrl + "-default-" + CMIS_URL;
        }
        return atomPubURL;
    }

    /**
     * @return 
     * @see com.asopagos.archivos.alfresco.client.BaseAPIClient#getCmisSession()
     */
    @Override
    public Session getCmisSession() {
        if (cmisSession == null) {
            // default factory implementation
            SessionFactory factory = SessionFactoryImpl.newInstance();
            Map<String, String> parameter = new HashMap<>();

            // connection settings
            parameter.put(SessionParameter.ATOMPUB_URL, getAtomPubURL(getRequestFactory()));
            parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            parameter.put(SessionParameter.AUTH_HTTP_BASIC, "true");
            parameter.put(SessionParameter.USER, getUsername());
            parameter.put(SessionParameter.PASSWORD, getPassword());
            parameter.put(SessionParameter.OBJECT_FACTORY_CLASS,
                    "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

            List<Repository> repositories = factory.getRepositories(parameter);

            cmisSession = repositories.get(0).createSession();
        }
        return this.cmisSession;
    }

    /**
     * Uses basic authentication to create an HTTP request factory.
     *
     * @return HttpRequestFactory
     */
    @Override
    public HttpRequestFactory getRequestFactory() {
        if (this.requestFactory == null) {
            this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                    request.setParser(new JsonObjectParser(new JacksonFactory()));
                    request.getHeaders().setBasicAuthentication(getUsername(), getPassword());
                }
            });
        }
        return this.requestFactory;
    }

    /**
     * @see com.asopagos.archivos.almacenamiento.alfresco.client.BaseAPIClient#getAlfrescoAPIUrl() 
     */
    @Override
    protected abstract String getAlfrescoAPIUrl();

    /**
     * @return
     */
    protected abstract String getUsername();

    /**
     * @return
     */
    protected abstract String getPassword();

}
