package com.asopagos.dto.subsidiomonetario.pagos;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información del subsidio por administrador<br/>
 * <b>Módulo:</b> Asopagos - Servicios de Integración<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class InformacionAdminSubsidioDTO implements Serializable{

    private static final long serialVersionUID = -7133945325671355104L;
    
    /**
     * Nombre completo del administrador del subsidio
     */
    private String nombreAdminSubsidio;

    /**
     * Tipo de identificación del administrador del subsidio
     */
    private TipoIdentificacionEnum tipoIdAdminSubsidio;

    /**
     * Número de identificación del administrador del subsidio
     */
    private String numeroIdAdminSubsidio;
    
    /**
     * Información de cada grupo familiar relacionado
     */
    private List<InformacionGrupoFamiliarDTO> lstGruposFamiliares;

    /**
     * @return the nombreAdminSubsidio
     */
    public String getNombreAdminSubsidio() {
        return nombreAdminSubsidio;
    }

    /**
     * @return the tipoIdAdminSubsidio
     */
    public TipoIdentificacionEnum getTipoIdAdminSubsidio() {
        return tipoIdAdminSubsidio;
    }

    /**
     * @return the numeroIdAdminSubsidio
     */
    public String getNumeroIdAdminSubsidio() {
        return numeroIdAdminSubsidio;
    }

    /**
     * @return the lstGruposFamiliares
     */
    public List<InformacionGrupoFamiliarDTO> getLstGruposFamiliares() {
        return lstGruposFamiliares;
    }

    /**
     * @param nombreAdminSubsidio the nombreAdminSubsidio to set
     */
    public void setNombreAdminSubsidio(String nombreAdminSubsidio) {
        this.nombreAdminSubsidio = nombreAdminSubsidio;
    }

    /**
     * @param tipoIdAdminSubsidio the tipoIdAdminSubsidio to set
     */
    public void setTipoIdAdminSubsidio(TipoIdentificacionEnum tipoIdAdminSubsidio) {
        this.tipoIdAdminSubsidio = tipoIdAdminSubsidio;
    }

    /**
     * @param numeroIdAdminSubsidio the numeroIdAdminSubsidio to set
     */
    public void setNumeroIdAdminSubsidio(String numeroIdAdminSubsidio) {
        this.numeroIdAdminSubsidio = numeroIdAdminSubsidio;
    }

    /**
     * @param lstGruposFamiliares
     *        the lstGruposFamiliares to set
     */
    public void setLstGruposFamiliares(List<InformacionGrupoFamiliarDTO> lstGruposFamiliares) {
        this.lstGruposFamiliares = lstGruposFamiliares;
    }
    
    
}
