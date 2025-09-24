package com.asopagos.afiliaciones.wsCajasan.dto;

public class AuthenticationResultDTO {
    private String token;
    private String url;

    public AuthenticationResultDTO(String token, String url) {
        this.token = token;
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public String getUrl() {
        return url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
