package com.asopagos.dto.modelo;

import java.util.List;

/**
 * DTO con los datos del Modelo de respuestaAportante.
 */
public class RespuestaCargueMasivoAportante {
    private String respuesta;
    private List<String> errores;


    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public RespuestaCargueMasivoAportante() {
    }
}
