package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobro2C;

/**
 * Entidad que representa la parametrización para la acción de cobro método 2C
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:25 p. m.
 */
public class AccionCobro2CModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -3733073435245626322L;
    /**
     * Se anexa liquidación de aportes a la citación para notificación personal
     */
    private Boolean anexoLiquidacion;
    /**
     * Se guardan los dias de generacion de acta de liquidacion
     */
    private Long diasGeneracionActa;
    /**
     * Fecha de ejecución del procedimiento
     */
    private Long diasEjecucion;
    /**
     * Hora inicio de ejecución del proceso
     */
    private Long horaEjecucion;

    /**
     * @return the anexoLiquidacion
     */
    public Boolean getAnexoLiquidacion() {
        return anexoLiquidacion;
    }

    /**
     * @param anexoLiquidacion
     *        the anexoLiquidacion to set
     */
    public void setAnexoLiquidacion(Boolean anexoLiquidacion) {
        this.anexoLiquidacion = anexoLiquidacion;
    }

    /**
     * @return the diasEjecucion
     */
    public Long getDiasEjecucion() {
        return diasEjecucion;
    }

    /**
     * @param diasEjecucion the diasEjecucion to set
     */
    public void setDiasEjecucion(Long diasEjecucion) {
        this.diasEjecucion = diasEjecucion;
    }

    /**
     * @return the horaEjecucion
     */
    public Long getHoraEjecucion() {
        return horaEjecucion;
    }

    /**
     * @param horaEjecucion the horaEjecucion to set
     */
    public void setHoraEjecucion(Long horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    /**
     *
     * @return the DiasGeneracionActa
     */
    public Long getDiasGeneracionActa() {
        return diasGeneracionActa;
    }

    /**
     *
     * @param diasGeneracionActa the DiasGeneracionActa set
     */
    public void setDiasGeneracionActa(Long diasGeneracionActa) {
        this.diasGeneracionActa = diasGeneracionActa;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro2CModeloDTO
     * @param accionCobro2C
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro2CModeloDTO}
     */
    public AccionCobro2CModeloDTO convertToDTO(AccionCobro2C accionCobro2C) {
        super.convertToDTO(accionCobro2C);
        /* Se setean la información al objeto AccionCobro2CModeloDTO */
        this.setAnexoLiquidacion(accionCobro2C.getAnexoLiquidacion());
        this.setDiasEjecucion(accionCobro2C.getDiasEjecucion());
        this.setDiasGeneracionActa(accionCobro2C.getDiasGeneracionActa());
        this.setHoraEjecucion(accionCobro2C.getHoraEjecucion()!=null?accionCobro2C.getHoraEjecucion().getTime():null);
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro2C}
     * @return devuelve un objeto Entity de {@link AccionCobro2C}
     */
    public AccionCobro2C convertToAccionCobro2CEntity() {
        /* Se instancia objeto AccionCobro2C */
        AccionCobro2C accionCobro2C = new AccionCobro2C();
        /* Se carga información de la clase padre */
        accionCobro2C = (AccionCobro2C) super.convertToEntity(accionCobro2C);
        /* Se setan los valores a accionCobro2C */
        accionCobro2C.setAnexoLiquidacion(this.getAnexoLiquidacion());
        accionCobro2C.setDiasEjecucion(this.getDiasEjecucion());
        accionCobro2C.setDiasGeneracionActa(this.getDiasGeneracionActa());
        accionCobro2C.setHoraEjecucion(this.getHoraEjecucion()!=null? new Date(this.getHoraEjecucion()):null);
        return accionCobro2C;
    }
}