package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos necesarios para llevar a cabo un 
 * reintegro de empleador por aportes <br/>
 * <b>Módulo:</b> Asopagos - HU-211-397 - HU-211-398 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ActivacionEmpleadorDTO implements Serializable {
    private static final long serialVersionUID = -8503451192824238898L;

    /** ID de registro de empleador a reactivar */
    private Long idAportante;
    
    /** Canal por el cual se hace el reintegro */
    private CanalRecepcionEnum canalReintegro;
    
    /** Referencia al aporte (registro general en PILA) que genera el reintegro */
    private Long idRegistroGeneral;
    
    /** Fecha de reintegro del empleador (primer día calendario del mes del aporte) */
    private Long fechaReintegro;
    
    /** Tipo identificación empleador */
    private TipoIdentificacionEnum tipoIdEmpleador;
    
    /** Número de identificación del empleador */
    private String numIdEmpleador;

    /**
     * @return the idAportante
     */
    public Long getIdAportante() {
        return idAportante;
    }

    /**
     * @param idAportante the idAportante to set
     */
    public void setIdAportante(Long idAportante) {
        this.idAportante = idAportante;
    }

    /**
     * @return the canalReintegro
     */
    public CanalRecepcionEnum getCanalReintegro() {
        return canalReintegro;
    }

    /**
     * @param canalReintegro the canalReintegro to set
     */
    public void setCanalReintegro(CanalRecepcionEnum canalReintegro) {
        this.canalReintegro = canalReintegro;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the fechaReintegro
     */
    public Long getFechaReintegro() {
        return fechaReintegro;
    }

    /**
     * @param fechaReintegro the fechaReintegro to set
     */
    public void setFechaReintegro(Long fechaReintegro) {
        this.fechaReintegro = fechaReintegro;
    }

    /**
     * @return the tipoIdEmpleador
     */
    public TipoIdentificacionEnum getTipoIdEmpleador() {
        return tipoIdEmpleador;
    }

    /**
     * @param tipoIdEmpleador the tipoIdEmpleador to set
     */
    public void setTipoIdEmpleador(TipoIdentificacionEnum tipoIdEmpleador) {
        this.tipoIdEmpleador = tipoIdEmpleador;
    }

    /**
     * @return the numIdEmpleador
     */
    public String getNumIdEmpleador() {
        return numIdEmpleador;
    }

    /**
     * @param numIdEmpleador the numIdEmpleador to set
     */
    public void setNumIdEmpleador(String numIdEmpleador) {
        this.numIdEmpleador = numIdEmpleador;
    }
}
