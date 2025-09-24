package com.asopagos.validaciones.fovis.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AutenticacionTokenDTO {

    private Boolean ok;
    private String token;
    private String data;

    public AutenticacionTokenDTO() {
    }

    @Override
    public String toString() {
        return "{" +
            " ok='" + getOk() + "'" +
            ", token='" + getToken() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }


    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AutenticacionTokenDTO(String data, String token, Boolean ok) {
        this.ok = ok;
        this.token = token;
        this.data = data;
    }
}