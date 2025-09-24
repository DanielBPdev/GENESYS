package com.asopagos.afiliados.constants;

import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class TrabajadorDTO {

    /** */
    private RolAfiliado rolAfiliado;

    /** */
    private PersonaDetalle personaDetalle;

    public TrabajadorDTO(RolAfiliado rolAfiliado, PersonaDetalle personaDetalle) {
        this.rolAfiliado = rolAfiliado;
        this.personaDetalle = personaDetalle;
    }

    /**
     * @return the rolAfiliado
     */
    public RolAfiliado getRolAfiliado() {
        return rolAfiliado;
    }

    /**
     * @param rolAfiliado
     *        the rolAfiliado to set
     */
    public void setRolAfiliado(RolAfiliado rolAfiliado) {
        this.rolAfiliado = rolAfiliado;
    }

    /**
     * @return the personaDetalle
     */
    public PersonaDetalle getPersonaDetalle() {
        return personaDetalle;
    }

    /**
     * @param personaDetalle
     *        the personaDetalle to set
     */
    public void setPersonaDetalle(PersonaDetalle personaDetalle) {
        this.personaDetalle = personaDetalle;
    }
}
