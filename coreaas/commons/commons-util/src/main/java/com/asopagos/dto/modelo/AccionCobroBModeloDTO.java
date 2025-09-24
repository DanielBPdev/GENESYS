package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobroB;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;

/**
 * Representa la Acción de Cobro B
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:17 p. m.
 */
public class AccionCobroBModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 2335986269419020130L;
    /**
     * Fecha de generación y envío de aviso de incumplimiento (Fecha asociada al mes
     * siguiente de la fecha límite de pago de aportes)
     */
    private Long diasGeneracionAviso;
    /**
     * Hora inicio de ejecución del proceso
     */
    private Long horaEjecucion;
    /**
     * Límite de envío del aviso de incumplimiento (Fecha asociada al mes siguiente de
     * la fecha límite de pago de aportes)
     */
    private Long limiteEnvioComunicado;
    /**
     * Representa el método para la acción de cobro (Método 1 o Método 2)
     */
    private MetodoAccionCobroEnum metodo;

    /**
     * @return the diasGeneracionAviso
     */
    public Long getDiasGeneracionAviso() {
        return diasGeneracionAviso;
    }

    /**
     * @param diasGeneracionAviso the diasGeneracionAviso to set
     */
    public void setDiasGeneracionAviso(Long diasGeneracionAviso) {
        this.diasGeneracionAviso = diasGeneracionAviso;
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
     * @return the limiteEnvioComunicado
     */
    public Long getLimiteEnvioComunicado() {
        return limiteEnvioComunicado;
    }

    /**
     * @param limiteEnvioComunicado
     *        the limiteEnvioComunicado to set
     */
    public void setLimiteEnvioComunicado(Long limiteEnvioComunicado) {
        this.limiteEnvioComunicado = limiteEnvioComunicado;
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
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobroBModeloDTO
     * @param accionCobroB
     *        recibe la entity
     * @return devuelve un objeto DTO AccionCobroBModeloDTO
     */
    public AccionCobroBModeloDTO convertToDTO(AccionCobroB accionCobroB) {
        super.convertToDTO(accionCobroB);
        /* Se setean la información al objeto AccionCobroBModeloDTO */
        this.setDiasGeneracionAviso(accionCobroB.getDiasGeneracionAviso());
        this.setHoraEjecucion(accionCobroB.getHoraEjecucion()!=null?accionCobroB.getHoraEjecucion().getTime():null);
        this.setMetodo(accionCobroB.getMetodo());
        this.setLimiteEnvioComunicado(accionCobroB.getLimiteEnvioComunicado());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobroB}
     * @return devuelve un objeto Entity de {@link AccionCobroB}
     */
    public AccionCobroB convertToAccionCobroBEntity() {
        /* Se instancia objeto AccionCobroB */
        AccionCobroB accionCobroB = new AccionCobroB();
        /* Se carga información de la clase padre */
        accionCobroB = (AccionCobroB) super.convertToEntity(accionCobroB);
        /* Se setan los valores a accionCobroB */
        accionCobroB.setDiasGeneracionAviso(this.getDiasGeneracionAviso());
        accionCobroB.setHoraEjecucion(this.getHoraEjecucion()!=null? new Date(this.getHoraEjecucion()):null);
        accionCobroB.setMetodo(this.getMetodo());
        accionCobroB.setLimiteEnvioComunicado(this.getLimiteEnvioComunicado());
        return accionCobroB;
    }
}