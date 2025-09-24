package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class RolafiliadoNovedadAutomaticaDTO implements Serializable{
 
    private Long idRolafiliado;
    private Long idPersonaAfiliado;

    public RolafiliadoNovedadAutomaticaDTO() {
        super();
    }

    public RolafiliadoNovedadAutomaticaDTO(Long idRolafiliado, Long idPersonaAfiliado) {
        this.idRolafiliado = idRolafiliado;
        this.idPersonaAfiliado = idPersonaAfiliado;
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
    public Long getIdRolafiliado() {
        return idRolafiliado;
    }

    /**
     * @param IdRolafiliado
     *        the IdRolafiliado to set
     */
    public void setIdRolafiliado(Long idRolafiliado) {
        this.idRolafiliado = idRolafiliado;
    }
}
