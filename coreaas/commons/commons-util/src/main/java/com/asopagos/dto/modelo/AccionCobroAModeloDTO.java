package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobroA;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;

/**
 * Entidad que representa la acción de cobro para el metodo 1 y metodo 2 de tipo A
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:15 p. m.
 */
public class AccionCobroAModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -922055218681390331L;
    /**
     * Suspensión automatica habilitada
     */
    private Boolean suspensionAutomatica;
    /**
     * Número de días hábiles después de la fecha límite de pago en que se ejecuta la
     * acción
     */
    private Long diasLimitePago;
    /**
     * Fecha y hora de inicio de ejecución del procedimiento
     */
    private Long fechaHoraEjecucion;
    /**
     * Límite de envío del comunicado (Número de días hábiles antes de ejecutar la
     * siguiente acción)
     */
    private Long diasLimiteEnvioComunicado;
    /**
     * Representa el método para la acción de cobro (Método 1 o Método 2)
     */
    private MetodoAccionCobroEnum metodo;

    /**
     * @return the suspensionAutomatica
     */
    public Boolean getSuspensionAutomatica() {
        return suspensionAutomatica;
    }

    /**
     * @param suspensionAutomatica
     *        the suspensionAutomatica to set
     */
    public void setSuspensionAutomatica(Boolean suspensionAutomatica) {
        this.suspensionAutomatica = suspensionAutomatica;
    }

    /**
     * @return the diasLimitePago
     */
    public Long getDiasLimitePago() {
        return diasLimitePago;
    }

    /**
     * @param diasLimitePago
     *        the diasLimitePago to set
     */
    public void setDiasLimitePago(Long diasLimitePago) {
        this.diasLimitePago = diasLimitePago;
    }

    /**
     * @return the fechaHoraEjecucion
     */
    public Long getFechaHoraEjecucion() {
        return fechaHoraEjecucion;
    }

    /**
     * @param fechaHoraEjecucion the fechaHoraEjecucion to set
     */
    public void setFechaHoraEjecucion(Long fechaHoraEjecucion) {
        this.fechaHoraEjecucion = fechaHoraEjecucion;
    }

    /**
     * @return the diasLimiteEnvioComunicado
     */
    public Long getDiasLimiteEnvioComunicado() {
        return diasLimiteEnvioComunicado;
    }

    /**
     * @param diasLimiteEnvioComunicado the diasLimiteEnvioComunicado to set
     */
    public void setDiasLimiteEnvioComunicado(Long diasLimiteEnvioComunicado) {
        this.diasLimiteEnvioComunicado = diasLimiteEnvioComunicado;
    }

    /**
     * @return the metodo
     */
    public MetodoAccionCobroEnum getMetodo() {
        return metodo;
    }

    /**
     * @param metodo
     *        the metodo to set
     */
    public void setMetodo(MetodoAccionCobroEnum metodo) {
        this.metodo = metodo;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobroAModeloDTO
     * @param accionCobroA
     *        recibe la entity
     * @return devuelve un objeto DTO AccionCobroAModeloDTO
     */
    public AccionCobroAModeloDTO convertToDTO(AccionCobroA accionCobroA) {
        super.convertToDTO(accionCobroA);
        /* Se setean la información al objeto AccionCobroAModeloDTO */
        this.setDiasLimitePago(accionCobroA.getDiasLimitePago());
        this.setDiasLimiteEnvioComunicado(accionCobroA.getDiasLimiteEnvioComunicado());
        this.setFechaHoraEjecucion(accionCobroA.getFechaHoraEjecucion()!=null?accionCobroA.getFechaHoraEjecucion().getTime():null);
        this.setMetodo(accionCobroA.getMetodo());
        this.setSuspensionAutomatica(accionCobroA.getSuspensionAutomatica());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobroA}
     * @return devuelve un objeto Entity de {@link AccionCobroA}
     */
    public AccionCobroA convertToAccionCobroAEntity() {
        /* Se instancia objeto AccionCobroA */
        AccionCobroA accionCobroA = new AccionCobroA();
        /* Se carga información de la clase padre */
        accionCobroA = (AccionCobroA) super.convertToEntity(accionCobroA);
        /* Se setan los valores a accionCobroA */
        accionCobroA.setDiasLimitePago(this.getDiasLimitePago());
        accionCobroA.setDiasLimiteEnvioComunicado(this.getDiasLimiteEnvioComunicado());
        accionCobroA.setMetodo(this.getMetodo());
        accionCobroA.setFechaHoraEjecucion(this.getFechaHoraEjecucion()!=null? new Date(this.getFechaHoraEjecucion()):null);
        accionCobroA.setSuspensionAutomatica(this.getSuspensionAutomatica());
        return accionCobroA;
    }
}