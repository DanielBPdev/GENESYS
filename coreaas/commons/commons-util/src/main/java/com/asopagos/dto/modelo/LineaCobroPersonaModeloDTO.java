package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.LineaCobroPersona;
import com.asopagos.enumeraciones.cartera.MetodoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

/**
 * Representa la clase donde se parametrizan las lineas de cobro(LC4, LC5)
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:12 p. m.
 */
public class LineaCobroPersonaModeloDTO implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = -5946527158829110258L;
    /**
     * Identificador de la entidad de linea de cobro persona Independiente o pensionado
     */
    private Long idLineaCobroPersona;
    /**
     * Habilita la accion de cobro: Notificación de inconsistencias en recaudo de
     * aportes
     */
    private Boolean habilitarAccionCobroA;
    /**
     * Número de días hábiles después de la fecha límite de pago en que se ejecuta la
     * acción
     */
    private Long diasLimitePago;
    /**
     * Estar dentro de los Y días parametrizados antes de la fecha de la siguiente
     * acción de cobro de la línea
     */
    private Long diasParametrizados;
    /**
     * Habilitar acción de cobro: Acciones manuales
     */
    private Boolean habilitarAccionCobroB;
    /**
     * Linea de cobro para la parametrización definida en la entidad.
     */
    private TipoLineaCobroEnum tipoLineaCobro;
    /**
     * Método del envio del comunicado este puede ser Fisico o Electronico
     */
    private MetodoEnvioComunicadoEnum metodoEnvioComunicado;
    
    /**
     * @return the idLineaCobroPersona
     */
    public Long getIdLineaCobroPersona() {
        return idLineaCobroPersona;
    }

    /**
     * @param idLineaCobroPersona the idLineaCobroPersona to set
     */
    public void setIdLineaCobroPersona(Long idLineaCobroPersona) {
        this.idLineaCobroPersona = idLineaCobroPersona;
    }

    /**
     * @return the habilitarAccionCobroA
     */
    public Boolean getHabilitarAccionCobroA() {
        return habilitarAccionCobroA;
    }

    /**
     * @param habilitarAccionCobroA
     *        the habilitarAccionCobroA to set
     */
    public void setHabilitarAccionCobroA(Boolean habilitarAccionCobroA) {
        this.habilitarAccionCobroA = habilitarAccionCobroA;
    }

    /**
     * @return the diasLimitePago
     */
    public Long getDiasLimitePago() {
        return diasLimitePago;
    }

    /**
     * @param diasLimitePago the diasLimitePago to set
     */
    public void setDiasLimitePago(Long diasLimitePago) {
        this.diasLimitePago = diasLimitePago;
    }

    /**
     * @return the diasParametrizados
     */
    public Long getDiasParametrizados() {
        return diasParametrizados;
    }

    /**
     * @param diasParametrizados
     *        the diasParametrizados to set
     */
    public void setDiasParametrizados(Long diasParametrizados) {
        this.diasParametrizados = diasParametrizados;
    }

    /**
     * @return the habilitarAccionCobroB
     */
    public Boolean getHabilitarAccionCobroB() {
        return habilitarAccionCobroB;
    }

    /**
     * @param habilitarAccionCobroB
     *        the habilitarAccionCobroB to set
     */
    public void setHabilitarAccionCobroB(Boolean habilitarAccionCobroB) {
        this.habilitarAccionCobroB = habilitarAccionCobroB;
    }

    /**
     * @return the tipoLineaCobro
     */
    public TipoLineaCobroEnum getTipoLineaCobro() {
        return tipoLineaCobro;
    }

    /**
     * @param tipoLineaCobro
     *        the tipoLineaCobro to set
     */
    public void setTipoLineaCobro(TipoLineaCobroEnum tipoLineaCobro) {
        this.tipoLineaCobro = tipoLineaCobro;
    }

    /**
     * @return the metodoEnvioComunicado
     */
    public MetodoEnvioComunicadoEnum getMetodoEnvioComunicado() {
        return metodoEnvioComunicado;
    }

    /**
     * @param metodoEnvioComunicado the metodoEnvioComunicado to set
     */
    public void setMetodoEnvioComunicado(MetodoEnvioComunicadoEnum metodoEnvioComunicado) {
        this.metodoEnvioComunicado = metodoEnvioComunicado;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso LineaCobroPersonaModeloDTO
     * @param lineaCobro
     *        recibe la entity
     * @return devuelve un objeto DTO LineaCobroModeloDTO
     */
    public LineaCobroPersonaModeloDTO convertToDTO(LineaCobroPersona lineaCobro) {
        this.setIdLineaCobroPersona(lineaCobro.getIdLineaCobroPersona());
        this.setDiasLimitePago(lineaCobro.getDiasLimitePago());
        this.setDiasParametrizados(lineaCobro.getDiasParametrizados());
        this.setHabilitarAccionCobroA(lineaCobro.getHabilitarAccionCobroA());
        this.setHabilitarAccionCobroB(lineaCobro.getHabilitarAccionCobroB());
        this.setTipoLineaCobro(lineaCobro.getTipoLineaCobro());
        this.setMetodoEnvioComunicado(lineaCobro.getMetodoEnvioComunicado());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso LineaCobroPersona
     * @return devuelve un objeto Entity de LineaCobroPersona
     */
    public LineaCobroPersona convertToLineaCobroPersonaEntity() {
        LineaCobroPersona lineaCobro = new LineaCobroPersona();
        lineaCobro.setIdLineaCobroPersona(this.getIdLineaCobroPersona());
        lineaCobro.setDiasLimitePago(this.getDiasLimitePago());
        lineaCobro.setDiasParametrizados(this.getDiasParametrizados());
        lineaCobro.setHabilitarAccionCobroA(this.getHabilitarAccionCobroA());
        lineaCobro.setHabilitarAccionCobroB(this.getHabilitarAccionCobroB());
        lineaCobro.setTipoLineaCobro(this.getTipoLineaCobro());
        lineaCobro.setMetodoEnvioComunicado(this.getMetodoEnvioComunicado());
        return lineaCobro;
    }
}