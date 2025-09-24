package com.asopagos.aportes.composite.dto;

import com.asopagos.aportes.composite.enumeraciones.TipoProcesamientoAporteEnum;

/**
 * <b>Descripcion:</b> DTO empleado para recibir los datos relacionados con el aporte que será registrado o relacionado <br/>
 * <b>Módulo:</b> Asopagos - HU-211-397 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class RegistroRelacionAporteDTO {

    /** ID de índice de planilla */
    private Long idIndicePlanilla;
    
    /** Tipo de procesamiento del aporte */
    private TipoProcesamientoAporteEnum tipoProceso;

    /**
     * @return the idIndicePlanilla
     */
    public Long getIdIndicePlanilla() {
        return idIndicePlanilla;
    }
    
    /**
     * @param idIndicePlanilla the idIndicePlanilla to set
     */
    public void setIdIndicePlanilla(Long idIndicePlanilla) {
        this.idIndicePlanilla = idIndicePlanilla;
    }

    /**
     * @return the tipoProceso
     */
    public TipoProcesamientoAporteEnum getTipoProceso() {
        return tipoProceso;
    }

    /**
     * @param tipoProceso the tipoProceso to set
     */
    public void setTipoProceso(TipoProcesamientoAporteEnum tipoProceso) {
        this.tipoProceso = tipoProceso;
    }
}
