package com.asopagos.reportes.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "token_type", "scope", "expires_in", "ext_expires_in", "expires_on", "not_before", "resource", "access_token",
        "refresh_token" })
public class BearerToken {

    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("ext_expires_in")
    private String extExpiresIn;
    @JsonProperty("expires_on")
    private String expiresOn;
    @JsonProperty("not_before")
    private String notBefore;
    @JsonProperty("resource")
    private String resource;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    @JsonProperty("expires_in")
    public String getExpiresIn() {
        return expiresIn;
    }

    @JsonProperty("expires_in")
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    @JsonProperty("ext_expires_in")
    public String getExtExpiresIn() {
        return extExpiresIn;
    }

    @JsonProperty("ext_expires_in")
    public void setExtExpiresIn(String extExpiresIn) {
        this.extExpiresIn = extExpiresIn;
    }

    @JsonProperty("expires_on")
    public String getExpiresOn() {
        return expiresOn;
    }

    @JsonProperty("expires_on")
    public void setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
    }

    @JsonProperty("not_before")
    public String getNotBefore() {
        return notBefore;
    }

    @JsonProperty("not_before")
    public void setNotBefore(String notBefore) {
        this.notBefore = notBefore;
    }

    @JsonProperty("resource")
    public String getResource() {
        return resource;
    }

    @JsonProperty("resource")
    public void setResource(String resource) {
        this.resource = resource;
    }

    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonProperty("refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonProperty("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}