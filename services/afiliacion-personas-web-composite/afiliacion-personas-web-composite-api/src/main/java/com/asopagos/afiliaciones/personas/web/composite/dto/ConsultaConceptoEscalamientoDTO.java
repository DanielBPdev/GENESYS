package com.asopagos.afiliaciones.personas.web.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.ResultadoGeneralValidacionBeneficiarioDTO;

/**
 * <b>Descripción:</b> DTO que transporta los datos básicos de la verificacion
 * del resultado de la solicitud afiliacion persona
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class ConsultaConceptoEscalamientoDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
    private Long idSolicitudGlobal;
    
    private Long idTarea;
    
    private Integer resultadoAnalisis;
    
    private List<ResultadoGeneralValidacionBeneficiarioDTO> resultadoGeneralBeneficiarios;

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * @return the resultadoAnalisis
     */
    public Integer getResultadoAnalisis() {
        return resultadoAnalisis;
    }

    /**
     * @param resultadoAnalisis the resultadoAnalisis to set
     */
    public void setResultadoAnalisis(Integer resultadoAnalisis) {
        this.resultadoAnalisis = resultadoAnalisis;
    }

    /**
     * @return the resultadoGeneralBeneficiarios
     */
    public List<ResultadoGeneralValidacionBeneficiarioDTO> getResultadoGeneralBeneficiarios() {
        return resultadoGeneralBeneficiarios;
    }

    /**
     * @param resultadoGeneralBeneficiarios the resultadoGeneralBeneficiarios to set
     */
    public void setResultadoGeneralBeneficiarios(List<ResultadoGeneralValidacionBeneficiarioDTO> resultadoGeneralBeneficiarios) {
        this.resultadoGeneralBeneficiarios = resultadoGeneralBeneficiarios;
    }

}
