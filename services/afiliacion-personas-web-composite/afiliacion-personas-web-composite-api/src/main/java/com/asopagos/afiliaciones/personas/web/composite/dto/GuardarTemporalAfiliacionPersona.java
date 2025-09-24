package com.asopagos.afiliaciones.personas.web.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.GrupoFamiliarDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;

@XmlRootElement
public class GuardarTemporalAfiliacionPersona implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private IdentificacionUbicacionPersonaDTO modeloInformacion;
    
    private InformacionLaboralTrabajadorDTO infoLaboral;
    
    private List<BeneficiarioDTO> beneficiarios;
    
    private List<GrupoFamiliarDTO> gruposFamiliares;
    
    public GuardarTemporalAfiliacionPersona() {
        super();
    }

    /**
     * @return the modeloInformacion
     */
    public IdentificacionUbicacionPersonaDTO getModeloInformacion() {
        return modeloInformacion;
    }

    /**
     * @param modeloInformacion the modeloInformacion to set
     */
    public void setModeloInformacion(IdentificacionUbicacionPersonaDTO modeloInformacion) {
        this.modeloInformacion = modeloInformacion;
    }

    /**
     * @return the infoLaboral
     */
    public InformacionLaboralTrabajadorDTO getInfoLaboral() {
        return infoLaboral;
    }

    /**
     * @param infoLaboral the infoLaboral to set
     */
    public void setInfoLaboral(InformacionLaboralTrabajadorDTO infoLaboral) {
        this.infoLaboral = infoLaboral;
    }

    /**
     * @return the beneficiarios
     */
    public List<BeneficiarioDTO> getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios the beneficiarios to set
     */
    public void setBeneficiarios(List<BeneficiarioDTO> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    /**
     * @return the gruposFamilares
     */
    public List<GrupoFamiliarDTO> getGruposFamiliares() {
        return gruposFamiliares;
    }

    /**
     * @param gruposFamilares the gruposFamilares to set
     */
    public void setGruposFamiliares(List<GrupoFamiliarDTO> gruposFamiliares) {
        this.gruposFamiliares = gruposFamiliares;
    }

}
