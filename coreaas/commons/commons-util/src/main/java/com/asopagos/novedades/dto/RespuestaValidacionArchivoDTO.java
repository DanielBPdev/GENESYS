/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.novedades.dto;

import com.asopagos.dto.ItemChequeoDTO;
import java.util.List;

/**
 *
 * @author linam
 */
public class RespuestaValidacionArchivoDTO {
      /**
     * SERIAL
     */
    private static final long serialVersionUID = 357448766592913292L;
    
    /**
    * Mensaje correspondiente al resultado de la validacion
    */
    private String mensaje;

    /**
     * Status correspondiente al resultado de la validacion
     */
    private String status;
    
    
    private List<String> listError;

   
    public RespuestaValidacionArchivoDTO(){
        
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getListError() {
        return listError;
    }

    public void setListError(List<String> listError) {
        this.listError = listError;
    }
    
    
}
