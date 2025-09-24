package com.asopagos.reportes.microsoft;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "id", "embedUrl", "embedToken", "minutesToExpiration", "IsEffectiveIdentityRolesRequired",
        "IsEffectiveIdentityRequired", "EnableRLS", "Username", "Roles", "ErrorMessage" })
public class EmbeddedReport {

    @JsonProperty("id")
    private String id;
    @JsonProperty("embedUrl")
    private String embedUrl;
    @JsonProperty("embedToken")
    private EmbedToken embedToken;
    @JsonProperty("minutesToExpiration")
    private Integer minutesToExpiration;
    @JsonProperty("IsEffectiveIdentityRolesRequired")
    private Boolean isEffectiveIdentityRolesRequired;
    @JsonProperty("IsEffectiveIdentityRequired")
    private Boolean isEffectiveIdentityRequired;
    @JsonProperty("EnableRLS")
    private Boolean enableRLS;
    @JsonProperty("Username")
    private Object username;
    @JsonProperty("Roles")
    private Object roles;
    @JsonProperty("ErrorMessage")
    private Object errorMessage;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("embedUrl")
    public String getEmbedUrl() {
        return embedUrl;
    }

    @JsonProperty("embedUrl")
    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    @JsonProperty("embedToken")
    public EmbedToken getEmbedToken() {
        return embedToken;
    }

    @JsonProperty("embedToken")
    public void setEmbedToken(EmbedToken embedToken) {
        this.embedToken = embedToken;
    }

    @JsonProperty("minutesToExpiration")
    public Integer getMinutesToExpiration() {
        return minutesToExpiration;
    }

    @JsonProperty("minutesToExpiration")
    public void setMinutesToExpiration(Integer minutesToExpiration) {
        this.minutesToExpiration = minutesToExpiration;
    }

    @JsonProperty("IsEffectiveIdentityRolesRequired")
    public Boolean getIsEffectiveIdentityRolesRequired() {
        return isEffectiveIdentityRolesRequired;
    }

    @JsonProperty("IsEffectiveIdentityRolesRequired")
    public void setIsEffectiveIdentityRolesRequired(Boolean isEffectiveIdentityRolesRequired) {
        this.isEffectiveIdentityRolesRequired = isEffectiveIdentityRolesRequired;
    }

    @JsonProperty("IsEffectiveIdentityRequired")
    public Boolean getIsEffectiveIdentityRequired() {
        return isEffectiveIdentityRequired;
    }

    @JsonProperty("IsEffectiveIdentityRequired")
    public void setIsEffectiveIdentityRequired(Boolean isEffectiveIdentityRequired) {
        this.isEffectiveIdentityRequired = isEffectiveIdentityRequired;
    }

    @JsonProperty("EnableRLS")
    public Boolean getEnableRLS() {
        return enableRLS;
    }

    @JsonProperty("EnableRLS")
    public void setEnableRLS(Boolean enableRLS) {
        this.enableRLS = enableRLS;
    }

    @JsonProperty("Username")
    public Object getUsername() {
        return username;
    }

    @JsonProperty("Username")
    public void setUsername(Object username) {
        this.username = username;
    }

    @JsonProperty("Roles")
    public Object getRoles() {
        return roles;
    }

    @JsonProperty("Roles")
    public void setRoles(Object roles) {
        this.roles = roles;
    }

    @JsonProperty("ErrorMessage")
    public Object getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("ErrorMessage")
    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

}