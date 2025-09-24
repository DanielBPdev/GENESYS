package com.asopagos.dto.modelo;

import java.io.Serializable;

import java.util.Date;

public class RegistroDespliegueAmbienteDTO implements Serializable {

    private Date ultimaFechaDespliegue;

    private String ramaDespliegue;

    public RegistroDespliegueAmbienteDTO(Date fechaDespliegue, String rama){
        this.ultimaFechaDespliegue = fechaDespliegue;
        this.ramaDespliegue = rama;
    }

    public Date getUltimaFechaDespliegue(){
        return this.ultimaFechaDespliegue;
    }

    public void setUltimaFechaDespliegue(Date fecha){
        this.ultimaFechaDespliegue = fecha;
    }

    public String getRamaDespliegue(){
        return this.ramaDespliegue;
    }

    public void setRamaDespliegue(String rama){
        this.ramaDespliegue = rama;
    }


}
