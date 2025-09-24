package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobro2G;
import com.asopagos.enumeraciones.cartera.FechaConteoDiasEnum;

/**
 * Entidad que representa la parametrización para la acción de cobro metodo 2G
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:30 p. m.
 */
public class AccionCobro2GModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 155555551011L;
    /**
     * Fecha de inicio de conteo de días: Fecha de envío liquidación de aportes, Fecha
     * de entrega de liquidación de aportes
     */
    private FechaConteoDiasEnum inicioDiasConteo;
    /**
     * Número de días transcurridos desde la fecha de inicio del conteo de días
     */
    private Long diasTranscurridos;
    /**
     * Hora inicio de ejecución del proceso
     */
    private Long horaEjecucion;
    /**
     * Plazo límite de envío de comunicado
     */
    private Long limiteEnvio;

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro2GModeloDTO
     * @param accionCobro2G
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro2GModeloDTO}
     */
    public AccionCobro2GModeloDTO convertToDTO(AccionCobro2G accionCobro2G) {
        super.convertToDTO(accionCobro2G);
        /* Se setean la información al objeto AccionCobro2F2GModeloDTO */
        this.setInicioDiasConteo(accionCobro2G.getInicioDiasConteo());
        this.setDiasTranscurridos(accionCobro2G.getDiasTranscurridos());
        this.setHoraEjecucion(accionCobro2G.getHoraEjecucion() != null ? accionCobro2G.getHoraEjecucion().getTime() : null);
        this.setLimiteEnvio(accionCobro2G.getLimiteEnvio());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro2G}
     * @return devuelve un objeto Entity de {@link AccionCobro2G}
     */
    public AccionCobro2G convertToAccionCobro2GEntity() {
        /* Se instancia objeto AccionCobro2F2G */
        AccionCobro2G accionCobro2G = new AccionCobro2G();
        /* Se carga información de la clase padre */
        accionCobro2G = (AccionCobro2G) super.convertToEntity(accionCobro2G);
        /* Se setan los valores a accionCobro2F2G */
        accionCobro2G.setInicioDiasConteo(this.getInicioDiasConteo());
        accionCobro2G.setDiasTranscurridos(this.getDiasTranscurridos());
        accionCobro2G.setHoraEjecucion(this.getHoraEjecucion() != null ? new Date(this.getHoraEjecucion()) : null);
        accionCobro2G.setLimiteEnvio(this.getLimiteEnvio());
        return accionCobro2G;
    }

    /**
     * @return the inicioDiasConteo
     */
    public FechaConteoDiasEnum getInicioDiasConteo() {
        return inicioDiasConteo;
    }

    /**
     * @param inicioDiasConteo
     *        the inicioDiasConteo to set
     */
    public void setInicioDiasConteo(FechaConteoDiasEnum inicioDiasConteo) {
        this.inicioDiasConteo = inicioDiasConteo;
    }

    /**
     * @return the diasTranscurridos
     */
    public Long getDiasTranscurridos() {
        return diasTranscurridos;
    }

    /**
     * @param diasTranscurridos
     *        the diasTranscurridos to set
     */
    public void setDiasTranscurridos(Long diasTranscurridos) {
        this.diasTranscurridos = diasTranscurridos;
    }

    /**
     * @return the horaEjecucion
     */
    public Long getHoraEjecucion() {
        return horaEjecucion;
    }

    /**
     * @param horaEjecucion
     *        the horaEjecucion to set
     */
    public void setHoraEjecucion(Long horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    /**
     * @return the limiteEnvio
     */
    public Long getLimiteEnvio() {
        return limiteEnvio;
    }

    /**
     * @param limiteEnvio
     *        the limiteEnvio to set
     */
    public void setLimiteEnvio(Long limiteEnvio) {
        this.limiteEnvio = limiteEnvio;
    }
}