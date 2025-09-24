package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos de una persona con su tipo de Afiliación.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@XmlRootElement
public class PersonaRetiroNovedadAutomaticaDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 4261860423867861696L;

    /**
     * Lista de identificadores de persona a retirar
     */
    private List<Long> idPersonaAfiliado;

    /**
     * Tipo de afiliación de las personas a retirar
     */
    private TipoAfiliadoEnum tipoAfiliadoEnum;

    /**
     * Lista de roles afiliado a retirar
     */
    private List<RolAfiliadoModeloDTO> listRolAfiliado;

    /**
     * Constructor por defecto
     */
    public PersonaRetiroNovedadAutomaticaDTO() {
        super();
    }

    /**
     * @return the idPersonaAfiliado
     */
    public List<Long> getIdPersonaAfiliado() {
        return idPersonaAfiliado;
    }

    /**
     * @param idPersonaAfiliado
     *        the idPersonaAfiliado to set
     */
    public void setIdPersonaAfiliado(List<Long> idPersonaAfiliado) {
        this.idPersonaAfiliado = idPersonaAfiliado;
    }

    /**
     * @return the tipoAfiliadoEnum
     */
    public TipoAfiliadoEnum getTipoAfiliadoEnum() {
        return tipoAfiliadoEnum;
    }

    /**
     * @param tipoAfiliadoEnum
     *        the tipoAfiliadoEnum to set
     */
    public void setTipoAfiliadoEnum(TipoAfiliadoEnum tipoAfiliadoEnum) {
        this.tipoAfiliadoEnum = tipoAfiliadoEnum;
    }

    /**
     * @return the listRolAfiliado
     */
    public List<RolAfiliadoModeloDTO> getListRolAfiliado() {
        return listRolAfiliado;
    }

    /**
     * @param listRolAfiliado
     *        the listRolAfiliado to set
     */
    public void setListRolAfiliado(List<RolAfiliadoModeloDTO> listRolAfiliado) {
        this.listRolAfiliado = listRolAfiliado;
    }

}
