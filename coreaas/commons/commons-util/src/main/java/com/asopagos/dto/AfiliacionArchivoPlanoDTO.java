/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.dto;

import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author linam
 */
@XmlRootElement
public class AfiliacionArchivoPlanoDTO implements Serializable {
     /**
     * SERIAL
     */
    private static final long serialVersionUID = 6379204003755500411L;
    
    private int cantidadCandidatos; 
    
    private List<AfiliarTrabajadorCandidatoDTO> listaCandidatos;

    public AfiliacionArchivoPlanoDTO(int cantidadCandidatos, List<AfiliarTrabajadorCandidatoDTO> listaCandidatos) {
        this.cantidadCandidatos = cantidadCandidatos;
        this.listaCandidatos = listaCandidatos;
    }

    public AfiliacionArchivoPlanoDTO() {
    }
    

    public int getCantidadCandidatos() {
        return cantidadCandidatos;
    }

    public void setCantidadCandidatos(int cantidadCandidatos) {
        this.cantidadCandidatos = cantidadCandidatos;
    }

    public List<AfiliarTrabajadorCandidatoDTO> getListaCandidatos() {
        return listaCandidatos;
    }

    public void setListaCandidatos(List<AfiliarTrabajadorCandidatoDTO> listaCandidatos) {
        this.listaCandidatos = listaCandidatos;
    }
    
    
}
