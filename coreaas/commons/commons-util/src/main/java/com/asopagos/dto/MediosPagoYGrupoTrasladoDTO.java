package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import java.lang.Long;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Alex-tintot:p
 *
 */
@XmlRootElement
public class MediosPagoYGrupoTrasladoDTO implements Serializable {

    private List<Long> idsMediosDePagoPrevios;

    private List<Long> idsGrupoFamiliar;


    public MediosPagoYGrupoTrasladoDTO() {
    }


    public MediosPagoYGrupoTrasladoDTO(List<Long> idsMediosDePagoPrevios, List<Long> idsGrupoFamiliar) {
        this.idsMediosDePagoPrevios = idsGrupoFamiliar;
        this.idsGrupoFamiliar = idsGrupoFamiliar;
    }


    public List<Long> getIdsMediosDePagoPrevios() {
        return this.idsMediosDePagoPrevios;
    }

    public void setIdsMediosDePagoPrevios(List<Long> idsMediosDePagoPrevios) {
        this.idsMediosDePagoPrevios = idsMediosDePagoPrevios;
    }

    public List<Long> getIdsGrupoFamiliar() {
        return this.idsGrupoFamiliar;
    }

    public void setIdsGrupoFamiliar(List<Long> idsGrupoFamiliar) {
        this.idsGrupoFamiliar = idsGrupoFamiliar;
    }

    
}
