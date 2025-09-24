/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.empleadores.dto;

/**
 *
 * @author Diego Alejandro GAravito Feliciano
 */
public class RespuestaDTO {
    
    private String RespuestaConsulta ;

    public RespuestaDTO(String Respuestaconsulta) {
        this.RespuestaConsulta = Respuestaconsulta;
    }

    public RespuestaDTO() {
    }

    public String getRespuestaconsulta() {
        return RespuestaConsulta;
    }

    public void setRespuestaconsulta(String Respuestaconsulta) {
        this.RespuestaConsulta = Respuestaconsulta;
    }
    
}