/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.dto;

import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.rest.security.dto.UserDTO;
import java.io.Serializable;

/**
 *
 * @author linam
 */
public class AfiliacionPersonaWebMasivaDTO implements Serializable {
    /**
     * SERIAL
     */
    private static final long serialVersionUID = 6379204003755500411L;
    
    private AfiliarTrabajadorCandidatoDTO candidatoAfiliacion;
    private UserDTO userDTO;

    public AfiliacionPersonaWebMasivaDTO(AfiliarTrabajadorCandidatoDTO candidatoAfiliacion, UserDTO userDTO) {
        this.candidatoAfiliacion = candidatoAfiliacion;
        this.userDTO = userDTO;
    }

    public AfiliacionPersonaWebMasivaDTO() {
    }

    public AfiliarTrabajadorCandidatoDTO getCandidatoAfiliacion() {
        return candidatoAfiliacion;
    }

    public void setCandidatoAfiliacion(AfiliarTrabajadorCandidatoDTO candidatoAfiliacion) {
        this.candidatoAfiliacion = candidatoAfiliacion;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
    
    
}
