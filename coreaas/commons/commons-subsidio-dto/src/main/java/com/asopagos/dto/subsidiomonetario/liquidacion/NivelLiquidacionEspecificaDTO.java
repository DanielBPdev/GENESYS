package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class NivelLiquidacionEspecificaDTO implements Serializable {

    private static final long serialVersionUID = -7627523494862857121L;

    /** Id empleador */
    private Long idEmpleador;
    
    /** Id afiliado principal */
    private Long idAfiliadoPrincipal;
    
    /** Nivel de liquidacion */
    private String nivelLiquidacion;

    /**
     * @param idEmpleador
     * @param idAfiliadoPrincipal
     * @param nivelLiquidacion
     */
    public NivelLiquidacionEspecificaDTO(Long idEmpleador, Long idAfiliadoPrincipal, String nivelLiquidacion) {
        this.idEmpleador = idEmpleador;
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
        this.nivelLiquidacion = nivelLiquidacion;
    }    
    
    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }
    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }
    /**
     * @return the idAfiliadoPrincipal
     */
    public Long getIdAfiliadoPrincipal() {
        return idAfiliadoPrincipal;
    }
    /**
     * @param idAfiliadoPrincipal the idAfiliadoPrincipal to set
     */
    public void setIdAfiliadoPrincipal(Long idAfiliadoPrincipal) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
    }
    /**
     * @return the nivelLiquidacion
     */
    public String getNivelLiquidacion() {
        return nivelLiquidacion;
    }
    /**
     * @param nivelLiquidacion the nivelLiquidacion to set
     */
    public void setNivelLiquidacion(String nivelLiquidacion) {
        this.nivelLiquidacion = nivelLiquidacion;
    }
    
    

}
