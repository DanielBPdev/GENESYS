package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase que encapsula los datos de resultado general de validacion del beneficiario<br/>
 * <b>Módulo:</b> Asopagos - HU 123-380<br/>
 *
 * @author Luis Arturo Zarate Ayala <a href="mailto:lzarate@heinsohn.com.co"> lzarate</a>
 */
@XmlRootElement
public class ResultadoGeneralValidacionBeneficiarioDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    private BeneficiarioDTO beneficiario;

    private Boolean afiliable;

    /**
     * @return the afiliable
     */
    public Boolean getAfiliable() {
        return afiliable;
    }

    /**
     * @param afiliable
     *        the afiliable to set
     */
    public void setAfiliable(Boolean afiliable) {
        this.afiliable = afiliable;
    }

    /**
     * @return the beneficiario
     */
    public BeneficiarioDTO getBeneficiario() {
        return beneficiario;
    }

    /**
     * @param beneficiario the beneficiario to set
     */
    public void setBeneficiario(BeneficiarioDTO beneficiario) {
        this.beneficiario = beneficiario;
    }
    
}
