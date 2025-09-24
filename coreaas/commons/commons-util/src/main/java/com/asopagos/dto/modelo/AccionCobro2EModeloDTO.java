package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobro2E;
import com.asopagos.enumeraciones.cartera.FechaConteoDiasEnum;

/**
 * Entidad que representa la parametrización de acción de cobro para el método 2E
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:28 p. m.
 */
public class AccionCobro2EModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 2382362742337859328L;

    /**
     * Identificador de la entidad de acción de cobro 2E
     */
    private Long idAccionCobro2E;
    
    /**
     * Fecha de inicio de conteo de días después de la fecha de entrega de la
     * notificación por aviso: Fecha de envío notificación por aviso, Fecha de entrega
     * de envío notificación por aviso
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
     * @return the idAccionCobro2E
     */
    public Long getIdAccionCobro2E() {
        return idAccionCobro2E;
    }

    /**
     * @param idAccionCobro2E the idAccionCobro2E to set
     */
    public void setIdAccionCobro2E(Long idAccionCobro2E) {
        this.idAccionCobro2E = idAccionCobro2E;
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
     * Método encargado de convertir una entidad a DTO
     * 
     * @param accionCobro2E
     *        Accion de cobro para el metodo 2 tipo E para la Linea de Cobro 1
     */
    public void convertToDTO(AccionCobro2E accionCobro2E) {
        this.setIdAccionCobro2E(accionCobro2E.getIdAccionCobro2E());
        this.setInicioDiasConteo(accionCobro2E.getInicioDiasConteo());
        this.setDiasTranscurridos(accionCobro2E.getDiasTranscurridos());
        this.setHoraEjecucion(accionCobro2E.getHoraEjecucion()!=null?accionCobro2E.getHoraEjecucion().getTime():null);
        this.setAnexaLiquidacion(accionCobro2E.getAnexaLiquidacion());
    }

    /**
     * Método encargado de convertir un DTO a Entidad
     * @return entidad convertida.
     */
    public AccionCobro2E convertToEntity() {
        AccionCobro2E accionCobro2E = new AccionCobro2E();
        accionCobro2E.setIdAccionCobro2E(this.getIdAccionCobro2E());
        accionCobro2E.setDiasTranscurridos(this.getDiasTranscurridos());
        accionCobro2E.setInicioDiasConteo(this.getInicioDiasConteo());
        accionCobro2E.setHoraEjecucion(this.getHoraEjecucion()!=null? new Date(this.getHoraEjecucion()):null);
        accionCobro2E.setAnexaLiquidacion(this.getAnexaLiquidacion());
        return accionCobro2E;
    }
}