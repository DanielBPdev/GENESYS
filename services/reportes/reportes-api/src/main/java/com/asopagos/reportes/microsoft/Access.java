package com.asopagos.reportes.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "accessLevel", "allowSaveAs" })
public class Access {

    @JsonProperty("accessLevel")
    private String accessLevel;
    @JsonProperty("allowSaveAs")
    private String allowSaveAs;

    @JsonProperty("accessLevel")
    public String getAccessLevel() {
        return accessLevel;
    }

    @JsonProperty("accessLevel")
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    @JsonProperty("allowSaveAs")
    public String getAllowSaveAs() {
        return allowSaveAs;
    }

    @JsonProperty("allowSaveAs")
    public void setAllowSaveAs(String allowSaveAs) {
        this.allowSaveAs = allowSaveAs;
    }

}