package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import com.asopagos.dto.InformacionBeneficiarioDTO;
import com.asopagos.dto.InformacionBeneficiarioAfiliadoDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformacionBeneficiarioCompletaDTO implements Serializable {

    private List<InformacionBeneficiarioDTO> informacionBeneficiario;

    private List<InformacionBeneficiarioAfiliadoDTO> informacionBeneficiarioAfiliado;

    public InformacionBeneficiarioCompletaDTO() {
    }

    public InformacionBeneficiarioCompletaDTO(List<InformacionBeneficiarioDTO> informacionBeneficiario, List<InformacionBeneficiarioAfiliadoDTO> informacionBeneficiarioAfiliado) {
        this.informacionBeneficiario = informacionBeneficiario;
        this.informacionBeneficiarioAfiliado = informacionBeneficiarioAfiliado;
    }

    public List<InformacionBeneficiarioDTO> getInformacionBeneficiario() {
        return this.informacionBeneficiario;
    }

    public void setInformacionBeneficiario(List<InformacionBeneficiarioDTO> informacionBeneficiario) {
        this.informacionBeneficiario = informacionBeneficiario;
    }

    public List<InformacionBeneficiarioAfiliadoDTO> getInformacionBeneficiarioAfiliado() {
        return this.informacionBeneficiarioAfiliado;
    }

    public void setInformacionBeneficiarioAfiliado(List<InformacionBeneficiarioAfiliadoDTO> informacionBeneficiarioAfiliado) {
        this.informacionBeneficiarioAfiliado = informacionBeneficiarioAfiliado;
    }

    @Override
    public String toString() {
        return "{" +
            " informacionBeneficiario='" + getInformacionBeneficiario() + "'" +
            ", informacionBeneficiarioAfiliado='" + getInformacionBeneficiarioAfiliado() + "'" +
            "}";
    }
}