package com.asopagos.reportes.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "@odata.context", "token", "tokenId", "expiration" })
public class ApiPowerBIToken {

    @JsonProperty("@odata.context")
    private String odataContext;
    @JsonProperty("token")
    private String token;
    @JsonProperty("tokenId")
    private String tokenId;
    @JsonProperty("expiration")
    private String expiration;

    @JsonProperty("@odata.context")
    public String getOdataContext() {
        return odataContext;
    }

    @JsonProperty("@odata.context")
    public void setOdataContext(String odataContext) {
        this.odataContext = odataContext;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("tokenId")
    public String getTokenId() {
        return tokenId;
    }

    @JsonProperty("tokenId")
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @JsonProperty("expiration")
    public String getExpiration() {
        return expiration;
    }

    @JsonProperty("expiration")
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

}