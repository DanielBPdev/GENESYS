package com.asopagos.reportes.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "@odata.context", "id", "name", "webUrl", "embedUrl", "isOwnedByMe", "datasetId" })
public class Report {

    @JsonProperty("@odata.context")
    private String odataContext;
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("webUrl")
    private String webUrl;
    @JsonProperty("embedUrl")
    private String embedUrl;
    @JsonProperty("isOwnedByMe")
    private Boolean isOwnedByMe;
    @JsonProperty("datasetId")
    private String datasetId;

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

    @JsonProperty("webUrl")
    public String getWebUrl() {
        return webUrl;
    }

    @JsonProperty("webUrl")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @JsonProperty("embedUrl")
    public String getEmbedUrl() {
        return embedUrl;
    }

    @JsonProperty("embedUrl")
    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    @JsonProperty("isOwnedByMe")
    public Boolean getIsOwnedByMe() {
        return isOwnedByMe;
    }

    @JsonProperty("isOwnedByMe")
    public void setIsOwnedByMe(Boolean isOwnedByMe) {
        this.isOwnedByMe = isOwnedByMe;
    }

    @JsonProperty("datasetId")
    public String getDatasetId() {
        return datasetId;
    }

    @JsonProperty("datasetId")
    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

}