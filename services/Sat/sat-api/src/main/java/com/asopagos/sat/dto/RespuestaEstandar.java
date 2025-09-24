/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sergio Reyes
 */
public class RespuestaEstandar {
    private String estado;
    private String elemento;
    private Integer exitosos;
    private Integer fallidos;
    private List<String> mensajes;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    
    public void adicionarMensaje(String mensaje){
        if(mensajes == null){
            mensajes = new ArrayList<String>();
        }
        mensajes.add(mensaje);
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Integer getExitosos() {
        return exitosos;
    }

    public void setExitosos(Integer exitosos) {
        this.exitosos = exitosos;
    }

    public Integer getFallidos() {
        return fallidos;
    }

    public void setFallidos(Integer fallidos) {
        this.fallidos = fallidos;
    }
    
    
    
    
}
