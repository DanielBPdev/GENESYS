package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.LineaCobro;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

/**
 * Representa la clase donde se parametrizan las lineas de cobro(LC1, LC2, LC3)
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:12 p. m.
 */
public class LineaCobroModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = -5477805174174290522L;
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
     * Método que sirve para convertir de una entidad a un DTO para este caso LineaCobroModeloDTO
     * @param lineaCobro
     *        recibe la entity
     * @return devuelve un objeto DTO LineaCobroModeloDTO
     */
    public LineaCobroModeloDTO convertToDTO(LineaCobro lineaCobro) {
        super.convertToDTO(lineaCobro);
        /* Se setean la información al objeto ParametrizacionFiscalizacionModeloDTO */
        this.setDiasLimitePago(lineaCobro.getDiasLimitePago());
        this.setDiasParametrizados(lineaCobro.getDiasParametrizados());
        this.setHabilitarAccionCobroA(lineaCobro.getHabilitarAccionCobroA());
        this.setHabilitarAccionCobroB(lineaCobro.getHabilitarAccionCobroB());
        this.setTipoLineaCobro(lineaCobro.getTipoLineaCobro());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso LineaCobro
     * @return devuelve un objeto Entity de LineaCobro
     */
    public LineaCobro convertToLineaCobroEntity() {
        /* Se instancia objeto LineaCobro */
        LineaCobro lineaCobro = new LineaCobro();
        /* Se carga información de la clase padre */
        lineaCobro = (LineaCobro) super.convertToEntity(lineaCobro);
        /* Se setan los valores a lineaCobro */
        lineaCobro.setDiasLimitePago(this.getDiasLimitePago());
        lineaCobro.setDiasParametrizados(this.getDiasParametrizados());
        lineaCobro.setHabilitarAccionCobroA(this.getHabilitarAccionCobroA());
        lineaCobro.setHabilitarAccionCobroB(this.getHabilitarAccionCobroB());
        lineaCobro.setTipoLineaCobro(this.getTipoLineaCobro());
        return lineaCobro;
    }
}