package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobro1D;
import com.asopagos.enumeraciones.cartera.FechaConteoDiasEnum;

/**
 * Entidad que representa la parametrización para la acción de cobro metodo 1D
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:21 p. m.
 */
public class AccionCobro1DModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial version Id
     */
    private static final long serialVersionUID = -8961139305799111767L;
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
     * TIPO DE COMUNICADO
     */
    private String comunicado;

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

    public String getComunicado() {
        return comunicado;
    }

    public void setComunicado(String comunicado) {
        this.comunicado = comunicado;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro1DModeloDTO
     * @param accionCobro1D
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro1DModeloDTO}
     */
    public AccionCobro1DModeloDTO convertToDTO(AccionCobro1D accionCobro1D) {
        super.convertToDTO(accionCobro1D);
        /* Se setean la información al objeto AccionCobro1D1EModeloDTO */
        this.setInicioDiasConteo(accionCobro1D.getInicioDiasConteo());
        this.setDiasTranscurridos(accionCobro1D.getDiasTranscurridos());
        this.setHoraEjecucion(accionCobro1D.getHoraEjecucion() != null ? accionCobro1D.getHoraEjecucion().getTime() : null);
        this.setLimiteEnvio(accionCobro1D.getLimiteEnvio());
        this.setComunicado(accionCobro1D.getComunicado());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro1D}
     * @return devuelve un objeto Entity de {@link AccionCobro1D}
     */
    public AccionCobro1D convertToAccionCobro1DEntity() {
        /* Se instancia objeto AccionCobro1D */
        AccionCobro1D accionCobro1D = new AccionCobro1D();
        /* Se carga información de la clase padre */
        accionCobro1D = (AccionCobro1D) super.convertToEntity(accionCobro1D);
        /* Se setan los valores a accionCobro1D1E */
        accionCobro1D.setInicioDiasConteo(this.getInicioDiasConteo());
        accionCobro1D.setDiasTranscurridos(this.getDiasTranscurridos());
        accionCobro1D.setHoraEjecucion(this.getHoraEjecucion() != null ? new Date(this.getHoraEjecucion()) : null);
        accionCobro1D.setLimiteEnvio(this.getLimiteEnvio());
        accionCobro1D.setComunicado(this.getComunicado());
        return accionCobro1D;
    }

    public AccionCobro1DModeloDTO AccionCobro1DModeloDTO(AccionCobro1D accionCobro1D) {
        this.convertToDTO(accionCobro1D);
        return this;
    }
}