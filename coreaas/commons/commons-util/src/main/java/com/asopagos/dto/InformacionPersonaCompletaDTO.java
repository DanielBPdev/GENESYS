package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InformacionPersonaCompletaDTO implements Serializable {
    private InformacionPersonaDTO informacionPersona;
    private List<InformacionPersonaEmpleadorDTO> informacionEmpleadores;

    public InformacionPersonaDTO getInformacionPersona() {
        return informacionPersona;
    }

    public void setInformacionPersona(InformacionPersonaDTO informacionPersona) {
        this.informacionPersona = informacionPersona;
    }

    public List<InformacionPersonaEmpleadorDTO> getInformacionEmpleadores() {
        return informacionEmpleadores;
    }

    public void setInformacionEmpleadores(List<InformacionPersonaEmpleadorDTO> informacionEmpleadores) {
        this.informacionEmpleadores = informacionEmpleadores;
    }

    @Override
    public String toString() {
        return "{" +
            " informacionPersona='" + getInformacionPersona() + "'" +
            ", informacionEmpleadores='" + getInformacionEmpleadores() + "'" +
            "}";
    }
    
}