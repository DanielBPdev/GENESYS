package com.asopagos.personas.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos de un empleador e identificacion de una persona y su estado respecto a la caja
 * 
 * @author Julián Andrés Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class DetalleBeneficiarioDTO  implements Serializable {


    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = 1L;


    private Long idBeneficiario;

    private Long idGrupoFamiliar;

    public DetalleBeneficiarioDTO() {

    }


    public Long getIdBeneficiario() {
        return this.idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }


    public Long getIdGrupoFamiliar() {
        return this.idGrupoFamiliar;
    }

    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }


}
