package com.asopagos.afiliaciones.personas.web.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.afiliaciones.personas.web.composite.enums.AccionResultadoEnum;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.ResultadoGeneralValidacionBeneficiarioDTO;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos básicos de la verificacion
 * del resultado de la solicitud afiliacion persona
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class VerificarResultadoSolicitudAfiliacionPersonaDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    private Long idSolicitudGlobal;
    
    private AccionResultadoEnum accionResultado;

    private ResultadoGeneralProductoNoConformeEnum resultadoGeneralAfiliado;
    														
    private List<ResultadoGeneralValidacionBeneficiarioDTO> resultadoGeneralBeneficiarios;
    
    private EscalamientoSolicitudDTO escalamientoSolicitudDTO;
    
    private Long idTarea;

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the resultadoGeneralBeneficiarios
     */
    public List<ResultadoGeneralValidacionBeneficiarioDTO> getResultadoGeneralBeneficiarios() {
        return resultadoGeneralBeneficiarios;
    }

    /**
     * @param resultadoGeneralBeneficiarios
     *        the resultadoGeneralBeneficiarios to set
     */
    public void setResultadoGeneralBeneficiarios(List<ResultadoGeneralValidacionBeneficiarioDTO> resultadoGeneralBeneficiarios) {
        this.resultadoGeneralBeneficiarios = resultadoGeneralBeneficiarios;
    }

    /**
     * @return the resultadoGeneralAfiliado
     */
    public ResultadoGeneralProductoNoConformeEnum getResultadoGeneralAfiliado() {
        return resultadoGeneralAfiliado;
    }

    /**
     * @param resultadoGeneralAfiliado
     *        the resultadoGeneralAfiliado to set
     */
    public void setResultadoGeneralAfiliado(ResultadoGeneralProductoNoConformeEnum resultadoGeneralAfiliado) {
        this.resultadoGeneralAfiliado = resultadoGeneralAfiliado;
    }

    /**
     * @return the accionResultado
     */
    public AccionResultadoEnum getAccionResultado() {
        return accionResultado;
    }

    /**
     * @param accionResultado the accionResultado to set
     */
    public void setAccionResultado(AccionResultadoEnum accionResultado) {
        this.accionResultado = accionResultado;
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
     * @return the escalamientoSolicitudDTO
     */
    public EscalamientoSolicitudDTO getEscalamientoSolicitudDTO() {
        return escalamientoSolicitudDTO;
    }

    /**
     * @param escalamientoSolicitudDTO the escalamientoSolicitudDTO to set
     */
    public void setEscalamientoSolicitudDTO(EscalamientoSolicitudDTO escalamientoSolicitudDTO) {
        this.escalamientoSolicitudDTO = escalamientoSolicitudDTO;
    }

}
