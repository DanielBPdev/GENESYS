package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

import java.io.Serializable;

public class FiltroCarteraLineaCobroDTO implements Serializable {

    private static final long serialVersionUID = 8859026526280068108L;

    /**
     * Identificador de cartera (En vista 360 se usa este campo guardando el numero de operación)
     */
    private Long idCartera;

    /**
     * Identificador de la entidad reportada en cartera
     */
    private Long idPersona;

    /**
     * Periodo de deuda de la entidad
     */
    private Long periodoDeuda;

    /**
     * Método de acción de cobro
     */
    private MetodoAccionCobroEnum metodo;

    /**
     * Tipo de acción de cobro
     */
    private TipoAccionCobroEnum tipoAccionCobro;

    /**
     * Tipo de línea de cobro
     */
    private TipoLineaCobroEnum tipoLineaCobro;

    /**
     * Tipo de solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

    public FiltroCarteraLineaCobroDTO() {
    }

    public FiltroCarteraLineaCobroDTO(Cartera cartera) {
        this.idCartera = cartera.getIdCartera();
        this.idPersona = cartera.getIdPersona();
        this.periodoDeuda = cartera.getPeriodoDeuda() != null ? cartera.getPeriodoDeuda().getTime() : null;
        this.metodo = cartera.getMetodo();
        this.tipoAccionCobro = cartera.getTipoAccionCobro();
        this.tipoLineaCobro = cartera.getTipoLineaCobro();
        this.tipoSolicitante = cartera.getTipoSolicitante();
    }

    public Long getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getPeriodoDeuda() {
        return periodoDeuda;
    }

    public void setPeriodoDeuda(Long periodoDeuda) {
        this.periodoDeuda = periodoDeuda;
    }

    public MetodoAccionCobroEnum getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoAccionCobroEnum metodo) {
        this.metodo = metodo;
    }

    public TipoAccionCobroEnum getTipoAccionCobro() {
        return tipoAccionCobro;
    }

    public void setTipoAccionCobro(TipoAccionCobroEnum tipoAccionCobro) {
        this.tipoAccionCobro = tipoAccionCobro;
    }

    public TipoLineaCobroEnum getTipoLineaCobro() {
        return tipoLineaCobro;
    }

    public void setTipoLineaCobro(TipoLineaCobroEnum tipoLineaCobro) {
        this.tipoLineaCobro = tipoLineaCobro;
    }

    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }
}
