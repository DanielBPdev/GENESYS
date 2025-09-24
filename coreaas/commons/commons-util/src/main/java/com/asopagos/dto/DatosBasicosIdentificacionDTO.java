package com.asopagos.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;

/**
 * <b>Descripción:</b> DTO para los datos básicos de identificación de una
 * persona
 * <b>Historia de Usuario:</b> HU-TRA-059 Digitar datos y verificar requisitos
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class DatosBasicosIdentificacionDTO implements Serializable {

    @NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
    private PersonaDTO persona;

    @NotNull(groups = { GrupoCreacion.class })
    private TipoAfiliadoEnum tipoAfiliado;

    private CanalRecepcionEnum canalRecepcion;

    /**
     * @return the persona
     */
    public PersonaDTO getPersona() {
        return persona;
    }

    /**
     * @param persona
     *        the persona to set
     */
    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado
     *        the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * @param canalRecepcion
     *        the canalRecepcion to set
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

}
