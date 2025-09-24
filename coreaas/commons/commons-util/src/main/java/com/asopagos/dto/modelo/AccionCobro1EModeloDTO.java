package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobro1E;
import com.asopagos.enumeraciones.cartera.FechaConteoDiasEnum;

/**
 * Entidad que representa la parametrización para la acción de cobro metodo 1E
 * @author clmarin
 * @version 1.0
 */
public class AccionCobro1EModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial version Id
     */
    private static final long serialVersionUID = -4121151072355288120L;
    /**
     * Fecha de inicio de conteo de días: Fecha de envío liquidación de aportes o
     * Fecha de entrega liquidación de aportes
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
     * Fecha de inicio de conteo de días: Fecha de envío Primer Aviso Persuasivo o
     * Fecha de entrega Primer Aviso Persuasivo
     */
    private FechaConteoDiasEnum inicioDiasConteoPersuasivo;
    /**
     * Número de días transcurridos desde la fecha de inicio del conteo de días del Primer Aviso Persuasivo
     */
    private Long diasTranscurridosPersuasivo;
    /**
     * Plazo límite de envío de comunicado Primer Aviso Persuasivo
     */
    private Long limiteEnvioPersuasivo;

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

    /**
     * @return the inicioDiasConteoPersuasivo
     */
    public FechaConteoDiasEnum getInicioDiasConteoPersuasivo() {
        return inicioDiasConteoPersuasivo;
    }

    /**
     * @param inicioDiasConteoPersuasivo
     *        the inicioDiasConteoPersuasivo to set
     */
    public void setInicioDiasConteoPersuasivo(FechaConteoDiasEnum inicioDiasConteoPersuasivo) {
        this.inicioDiasConteoPersuasivo = inicioDiasConteoPersuasivo;
    }

    /**
     * @return the diasTranscurridosPersuasivo
     */
    public Long getDiasTranscurridosPersuasivo() {
        return diasTranscurridosPersuasivo;
    }

    /**
     * @param diasTranscurridosPersuasivo
     *        the diasTranscurridosPersuasivo to set
     */
    public void setDiasTranscurridosPersuasivo(Long diasTranscurridosPersuasivo) {
        this.diasTranscurridosPersuasivo = diasTranscurridosPersuasivo;
    }

    /**
     * @return the limiteEnvioPersuasivo
     */
    public Long getLimiteEnvioPersuasivo() {
        return limiteEnvioPersuasivo;
    }

    /**
     * @param limiteEnvioPersuasivo
     *        the limiteEnvioPersuasivo to set
     */
    public void setLimiteEnvioPersuasivo(Long limiteEnvioPersuasivo) {
        this.limiteEnvioPersuasivo = limiteEnvioPersuasivo;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro1EModeloDTO
     * @param accionCobro1E
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro1EModeloDTO}
     */
    public AccionCobro1EModeloDTO convertToDTO(AccionCobro1E accionCobro1E) {
        super.convertToDTO(accionCobro1E);
        /* Se setean la información al objeto AccionCobro1EModeloDTO */
        this.setInicioDiasConteo(accionCobro1E.getInicioDiasConteo());
        this.setDiasTranscurridos(accionCobro1E.getDiasTranscurridos());
        this.setHoraEjecucion(accionCobro1E.getHoraEjecucion() != null ? accionCobro1E.getHoraEjecucion().getTime() : null);
        this.setLimiteEnvio(accionCobro1E.getLimiteEnvio());
        this.setInicioDiasConteoPersuasivo(accionCobro1E.getInicioDiasConteoPersuasivo());
        this.setDiasTranscurridosPersuasivo(accionCobro1E.getDiasTranscurridosPersuasivo());
        this.setLimiteEnvioPersuasivo(accionCobro1E.getLimiteEnvioPersuasivo());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro1D}
     * @return devuelve un objeto Entity de {@link AccionCobro1D}
     */
    public AccionCobro1E convertToAccionCobro1EEntity() {
        /* Se instancia objeto AccionCobro1E */
        AccionCobro1E accionCobro1E = new AccionCobro1E();
        /* Se carga información de la clase padre */
        accionCobro1E = (AccionCobro1E) super.convertToEntity(accionCobro1E);
        /* Se setan los valores a accionCobro1E */
        accionCobro1E.setInicioDiasConteo(this.getInicioDiasConteo());
        accionCobro1E.setDiasTranscurridos(this.getDiasTranscurridos());
        accionCobro1E.setHoraEjecucion(this.getHoraEjecucion() != null ? new Date(this.getHoraEjecucion()) : null);
        accionCobro1E.setLimiteEnvio(this.getLimiteEnvio());
        accionCobro1E.setInicioDiasConteoPersuasivo(this.getInicioDiasConteoPersuasivo());
        accionCobro1E.setDiasTranscurridosPersuasivo(this.getDiasTranscurridosPersuasivo());
        accionCobro1E.setLimiteEnvioPersuasivo(this.getLimiteEnvioPersuasivo());
        return accionCobro1E;
    }
}