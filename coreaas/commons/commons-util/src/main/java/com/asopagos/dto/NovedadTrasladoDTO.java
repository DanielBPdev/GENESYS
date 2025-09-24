package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import java.lang.Long;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;

@XmlRootElement
public class NovedadTrasladoDTO {

    private MedioDePagoModeloDTO medioTraslado;

    private PersonaModeloDTO personaAdmin;


    public NovedadTrasladoDTO() {
    }

    public NovedadTrasladoDTO(MedioDePagoModeloDTO medioTraslado, PersonaModeloDTO personaAdmin) {
        this.medioTraslado = medioTraslado;
        this.personaAdmin = personaAdmin;
    }

    public MedioDePagoModeloDTO getMedioTraslado() {
        return this.medioTraslado;
    }

    public void setMedioTraslado(MedioDePagoModeloDTO medioTraslado) {
        this.medioTraslado = medioTraslado;
    }

    public PersonaModeloDTO getPersonaAdmin() {
        return this.personaAdmin;
    }

    public void setPersonaAdmin(PersonaModeloDTO personaAdmin) {
        this.personaAdmin = personaAdmin;
    }

    @Override
    public String toString() {
        return "{" +
                " medioTraslado='" + getMedioTraslado() + "'" +
                ", personaAdmin='" + getPersonaAdmin() + "'" +
                "}";
    }


}
