package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobro2D;
import com.asopagos.enumeraciones.cartera.FechaConteoDiasEnum;

/**
 * Entidad que representa la parametrización para la acción de cobro metodo 2D
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:27 p. m.
 */
public class AccionCobro2DModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -4408237955018632225L;
    /**
     * Fecha de inicio de conteo de días después de la fecha de entrega de la citación
     * a notificación personal: Fecha de envío citación a notificación personal, Fecha
     * de entrega de citación a notificación personal
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

    private Boolean anexaLiquidacion;

    public Boolean getAnexaLiquidacion() {
        return anexaLiquidacion;
    }

    public void setAnexaLiquidacion(Boolean anexaLiquidacion) {
        this.anexaLiquidacion = anexaLiquidacion;
    }

    /**
     * @return the inicioDiasConteo
     */
    public FechaConteoDiasEnum getInicioDiasConteo() {
        return inicioDiasConteo;
    }

    /**
     * @param inicioDiasConteo the inicioDiasConteo to set
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
     * @param horaEjecucion the horaEjecucion to set
     */
    public void setHoraEjecucion(Long horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro2DModeloDTO
     * @param accionCobro2D
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro2DModeloDTO}
     */
    public AccionCobro2DModeloDTO convertToDTO(AccionCobro2D accionCobro2D) {
        super.convertToDTO(accionCobro2D);
        /* Se setean la información al objeto AccionCobro2DModeloDTO */
        this.setInicioDiasConteo(accionCobro2D.getInicioDiasConteo());
        this.setDiasTranscurridos(accionCobro2D.getDiasTranscurridos());
        this.setHoraEjecucion(accionCobro2D.getHoraEjecucion()!=null?accionCobro2D.getHoraEjecucion().getTime():null);
        this.setAnexaLiquidacion(accionCobro2D.getAnexaLiquidacion());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro2D}
     * @return devuelve un objeto Entity de {@link AccionCobro2D}
     */
    public AccionCobro2D convertToAccionCobro2DEntity() {
        /* Se instancia objeto AccionCobro2D */
        AccionCobro2D accionCobro2D = new AccionCobro2D();
        /* Se carga información de la clase padre */
        accionCobro2D = (AccionCobro2D) super.convertToEntity(accionCobro2D);
        /* Se setan los valores a accionCobro2D */
        accionCobro2D.setInicioDiasConteo(this.getInicioDiasConteo());
        accionCobro2D.setDiasTranscurridos(this.getDiasTranscurridos());
        accionCobro2D.setHoraEjecucion(this.getHoraEjecucion()!=null? new Date(this.getHoraEjecucion()):null);
        accionCobro2D.setAnexaLiquidacion(this.getAnexaLiquidacion());
        return accionCobro2D;
    }
}