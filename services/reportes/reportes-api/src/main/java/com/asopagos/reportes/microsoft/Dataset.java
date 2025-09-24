package com.asopagos.reportes.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "@odata.context", "id", "name", "configuredBy", "isRefreshable", "isEffectiveIdentityRequired",
        "isEffectiveIdentityRolesRequired", "isOnPremGatewayRequired" })
public class Dataset {

    @JsonProperty("@odata.context")
    private String odataContext;
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("configuredBy")
    private String configuredBy;
    @JsonProperty("isRefreshable")
    private Boolean isRefreshable;
    @JsonProperty("isEffectiveIdentityRequired")
    private Boolean isEffectiveIdentityRequired;
    @JsonProperty("isEffectiveIdentityRolesRequired")
    private Boolean isEffectiveIdentityRolesRequired;
    @JsonProperty("isOnPremGatewayRequired")
    private Boolean isOnPremGatewayRequired;

    @JsonProperty("@odata.context")
    public String getOdataContext() {
        return odataContext;
    }

    @JsonProperty("@odata.context")
    public void setOdataContext(String odataContext) {
        this.odataContext = odataContext;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("configuredBy")
    public String getConfiguredBy() {
        return configuredBy;
    }

    @JsonProperty("configuredBy")
    public void setConfiguredBy(String configuredBy) {
        this.configuredBy = configuredBy;
    }

    @JsonProperty("isRefreshable")
    public Boolean getIsRefreshable() {
        return isRefreshable;
    }

    @JsonProperty("isRefreshable")
    public void setIsRefreshable(Boolean isRefreshable) {
        this.isRefreshable = isRefreshable;
    }

    @JsonProperty("isEffectiveIdentityRequired")
    public Boolean getIsEffectiveIdentityRequired() {
        return isEffectiveIdentityRequired;
    }

    @JsonProperty("isEffectiveIdentityRequired")
    public void setIsEffectiveIdentityRequired(Boolean isEffectiveIdentityRequired) {
        this.isEffectiveIdentityRequired = isEffectiveIdentityRequired;
    }

    @JsonProperty("isEffectiveIdentityRolesRequired")
    public Boolean getIsEffectiveIdentityRolesRequired() {
        return isEffectiveIdentityRolesRequired;
    }

    @JsonProperty("isEffectiveIdentityRolesRequired")
    public void setIsEffectiveIdentityRolesRequired(Boolean isEffectiveIdentityRolesRequired) {
        this.isEffectiveIdentityRolesRequired = isEffectiveIdentityRolesRequired;
    }

    @JsonProperty("isOnPremGatewayRequired")
    public Boolean getIsOnPremGatewayRequired() {
        return isOnPremGatewayRequired;
    }

    @JsonProperty("isOnPremGatewayRequired")
    public void setIsOnPremGatewayRequired(Boolean isOnPremGatewayRequired) {
        this.isOnPremGatewayRequired = isOnPremGatewayRequired;
    }

}