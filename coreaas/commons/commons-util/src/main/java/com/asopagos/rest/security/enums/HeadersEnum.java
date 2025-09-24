package com.asopagos.rest.security.enums;

public enum HeadersEnum {
    
    TX_ID ("TX_ID"), IP_ORIGIN ("X-Real-IP"), REQUEST_ID("REQUEST_ID"), PROFILE("Profile");

    private String name;

    private HeadersEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}