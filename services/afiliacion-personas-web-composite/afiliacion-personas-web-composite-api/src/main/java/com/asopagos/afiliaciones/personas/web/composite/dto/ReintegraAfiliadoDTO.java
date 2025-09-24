package com.asopagos.afiliaciones.personas.web.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos del reintegro de un pensionado o independiente
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class ReintegraAfiliadoDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
    private DecisionSiNoEnum decision;
    
    private AfiliadoInDTO afiliadoInDTO;

    /**
     * @return the decision
     */
    public DecisionSiNoEnum getDecision() {
        return decision;
    }

    /**
     * @param decision
     *        the decision to set
     */
    public void setDecision(DecisionSiNoEnum decision) {
        this.decision = decision;
    }

    /**
     * @return the afiliadoInDTO
     */
    public AfiliadoInDTO getAfiliadoInDTO() {
        return afiliadoInDTO;
    }

    /**
     * @param afiliadoInDTO the afiliadoInDTO to set
     */
    public void setAfiliadoInDTO(AfiliadoInDTO afiliadoInDTO) {
        this.afiliadoInDTO = afiliadoInDTO;
    }
    
}
