package com.asopagos.reportes.ejb;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.DashBoardConsultaDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.reportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.reportes.microsoft.Access;
import com.asopagos.reportes.microsoft.ApiPowerBIToken;
import com.asopagos.reportes.microsoft.BearerToken;
import com.asopagos.reportes.microsoft.Dataset;
import com.asopagos.reportes.microsoft.EmbedToken;
import com.asopagos.reportes.microsoft.EmbeddedReport;
import com.asopagos.reportes.microsoft.Report;
import com.asopagos.reportes.service.ConsultasPowerBIService;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de Power BI DashBoard <br/>
 * 
 * <b>Módulo:</b> Asopagos - Reportes 3.2.1 - 3.2.5
 *
 * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
 */
@Stateless
public class ConsultasPowerBIBusiness implements ConsultasPowerBIService {

    private static final String MICROSOFT_CLIENT_ID = "MICROSOFT_CLIENT_ID";
    private static final String MICROSOFT_USERNAME = "MICROSOFT_USERNAME";
    private static final String MICROSOFT_PASSWORD = "MICROSOFT_PASSWORD";
    private static final String MICROSOFT_DEFAULT_AUTHORITY = "https://login.windows.net/common/oauth2/token";
    private static final String DEFAULT_POWER_BI_RESOURCE_ID = "https://analysis.windows.net/powerbi/api";
    private static final String POWERBI_URL = "https://api.powerbi.com/v1.0/myorg/";
    private static final String BEARER_TYPE = "Bearer";

    private final ILogger logger = LogManager.getLogger(ConsultasPowerBIBusiness.class);

    @Inject
    private IConsultasModeloCore consultasCore;

    @Override
    public DashBoardConsultaDTO consultarPermisos(String rolUsuario, UserDTO userDTO) {
        String firmaMetodo = "consultarPermisos(String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        DashBoardConsultaDTO dashBoardConsultaDTO = null;
        if (rolUsuario != null && !rolUsuario.trim().isEmpty()) {
            try {
                dashBoardConsultaDTO = consultasCore.consultarPermisos(rolUsuario);
            } catch (NoResultException e) {
            } catch (Exception e) {
                logger.error(firmaMetodo + " :: " + MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + " :: " + e.getMessage());
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dashBoardConsultaDTO;
    }

    /* (non-Javadoc)
     * @see com.asopagos.reportes.service.ConsultasPowerBIService#getEmbeddedReport(java.lang.String, java.lang.String)
     */
    @Override
    public EmbeddedReport getEmbeddedReport(String groupId, String reportId) {

        BearerToken bearerToken = getTokenFromMicrosoft();
        ApiPowerBIToken apiToken = getTokenFromPowerBI(groupId, reportId, bearerToken);
        Report report = getReport(groupId, reportId, bearerToken);
        Dataset dataset = getDataset(groupId, report.getDatasetId(), bearerToken);
        
        EmbeddedReport powerBiToken = new EmbeddedReport();
        powerBiToken.setId(reportId);
        powerBiToken.setEmbedToken(construirEmbedToken(apiToken));
        powerBiToken.setEmbedUrl(report.getEmbedUrl());
        powerBiToken.setMinutesToExpiration(calcularMinutosExpiracionToken(apiToken));
        powerBiToken.setIsEffectiveIdentityRequired(dataset.getIsEffectiveIdentityRequired());
        powerBiToken.setIsEffectiveIdentityRolesRequired(dataset.getIsEffectiveIdentityRolesRequired());
        powerBiToken.setEnableRLS(false);
        
        return powerBiToken;
    }
    
    /**
     * Se obtiene un token jwt desde Microsoft con las credenciales configuradas como parametros en la aplicación
     * @return
     */
    private BearerToken getTokenFromMicrosoft() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(MICROSOFT_DEFAULT_AUTHORITY);
        Form form = new Form().param("grant_type", "password")
                .param("resource", DEFAULT_POWER_BI_RESOURCE_ID)
                .param("client_id", CacheManager.getParametro(MICROSOFT_CLIENT_ID).toString())
                .param("username", CacheManager.getParametro(MICROSOFT_USERNAME).toString())
                .param("password", CacheManager.getParametro(MICROSOFT_PASSWORD).toString());

        return webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED),
                BearerToken.class);
    }
    
    /**
     * Se obtiene un token desde PowerBI correspondiente a un reporte
     * @param groupId
     * @param reportId
     * @param bearerToken Token jwt otorgado por Microsoft
     * @return
     */
    private ApiPowerBIToken getTokenFromPowerBI(String groupId, String reportId, BearerToken bearerToken) {
        UriBuilder builder = UriBuilder.fromPath(POWERBI_URL)
                .path("groups/{groupId}/reports/{reportId}/GenerateToken");
        URI uri = builder.build(groupId, reportId);
        Client client = ClientBuilder.newClient();

        WebTarget webTarget = client.target(uri.toString());
        Access access = new Access();
        access.setAccessLevel("View");
        access.setAllowSaveAs("true");

        return webTarget.path("").request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " + bearerToken.getAccessToken())
                .post(Entity.entity(access, MediaType.APPLICATION_JSON), ApiPowerBIToken.class);
    }

    private Report getReport(String groupId, String reportId, BearerToken bearerToken) {
        UriBuilder builder = UriBuilder.fromPath(POWERBI_URL)
                .path("groups/{groupId}/reports/{reportId}");
        URI uri = builder.build(groupId, reportId);
        Client client = ClientBuilder.newClient();

        WebTarget webTarget = client.target(uri.toString());

        return webTarget.path("").request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " + bearerToken.getAccessToken())
                .get(Report.class);
    }
    
    private Dataset getDataset(String groupId, String datasetId, BearerToken bearerToken) {
        UriBuilder builder = UriBuilder.fromPath(POWERBI_URL)
                .path("groups/{groupId}/datasets/{datasetId}");
        URI uri = builder.build(groupId, datasetId);
        Client client = ClientBuilder.newClient();

        WebTarget webTarget = client.target(uri.toString());

        return webTarget.path("").request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " +bearerToken.getAccessToken())
                .get(Dataset.class);
    }

    private EmbedToken construirEmbedToken(ApiPowerBIToken apiToken) {
        EmbedToken embedToken = new EmbedToken();
        embedToken.setToken(apiToken.getToken());
        embedToken.setExpiration(apiToken.getExpiration());
        embedToken.setTokenId(apiToken.getTokenId());
        return embedToken;
    }

    private Integer calcularMinutosExpiracionToken(ApiPowerBIToken apiToken) {
        Instant expiracion = Instant.parse(apiToken.getExpiration());
        Instant ahora = Instant.now();
        Long minutes = ChronoUnit.MINUTES.between(ahora, expiracion);
        return minutes.intValue();
    }

}
