package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que transporta los datos de un beneficiario para la novedad automatica
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class BeneficiarioNovedadAutomaticaDTO implements Serializable {

    private Long idPersonaAfiliado;

    private Long idBeneficiario;


    public BeneficiarioNovedadAutomaticaDTO() {
        super();
    }

    /**
     * @return the idPersonaAfiliado
     */
    public Long getIdPersonaAfiliado() {
        return idPersonaAfiliado;
    }

    /**
     * @param idPersonaAfiliado
     *        the idPersonaAfiliado to set
     */
    public void setIdPersonaAfiliado(Long idPersonaAfiliado) {
        this.idPersonaAfiliado = idPersonaAfiliado;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario
     *        the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

}
