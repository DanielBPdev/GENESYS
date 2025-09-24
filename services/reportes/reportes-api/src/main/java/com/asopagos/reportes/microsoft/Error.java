package com.asopagos.reportes.microsoft;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "error", "error_description", "error_codes", "timestamp", "trace_id", "correlation_id" })
public class Error {

    @JsonProperty("error")
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("error_codes")
    private List<Integer> errorCodes = null;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("trace_id")
    private String traceId;
    @JsonProperty("correlation_id")
    private String correlationId;

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(String error) {
        this.error = error;
    }

    @JsonProperty("error_description")
    public String getErrorDescription() {
        return errorDescription;
    }

    @JsonProperty("error_description")
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @JsonProperty("error_codes")
    public List<Integer> getErrorCodes() {
        return errorCodes;
    }

    @JsonProperty("error_codes")
    public void setErrorCodes(List<Integer> errorCodes) {
        this.errorCodes = errorCodes;
    }

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("trace_id")
    public String getTraceId() {
        return traceId;
    }

    @JsonProperty("trace_id")
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @JsonProperty("correlation_id")
    public String getCorrelationId() {
        return correlationId;
    }

    @JsonProperty("correlation_id")
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

}