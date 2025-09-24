package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos de los anticipos
 * desembolsados de legalización y desembolso<br/>
 * <b>Módulo:Fovis</b> Asopagos - HU Transversal<br/>
 *
 * @author <a href="jocorrea@heinsohn.com.co">Jose Arley Correa</a>
 */
public class CondicionesLegalizacionDesembolsoDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -3550013203000501658L;

    /**
     * Valor SFV asignado a la Postulación.
     */
    private BigDecimal valorAsignadoSFV;

    /**
     * Valor SFV calculado asociado a la Postulación.
     */
    private BigDecimal valorAjusteIPCSFV;

    /**
     * Valor desembolsado de la solicitud.
     */
    private BigDecimal valorDesembolsado;

    /**
     * Valor desembolsado de la solicitud.
     */
    private BigDecimal valorPendienteDesembolsar;
    
    /**
     * Valor fecha transferencia
     */
    private Date fechaTransferenciaDesembolso;
    
    /**
     * Valor SFV ajustado del subsidio asociado a la Postulación.
     */
    private BigDecimal valorSFVAjustado;
    

    /**
     * Lista Anticipos desembolso
     */
    private List<HistoricoLegalizacionFOVISDTO> listaAnticipos;

    /**
     * Constructor por defecto
     */
    public CondicionesLegalizacionDesembolsoDTO() {
        super();
    }

    /**
     * @return the valorAsignadoSFV
     */
    public BigDecimal getValorAsignadoSFV() {
        return valorAsignadoSFV;
    }

    /**
     * @param valorAsignadoSFV
     *        the valorAsignadoSFV to set
     */
    public void setValorAsignadoSFV(BigDecimal valorAsignadoSFV) {
        this.valorAsignadoSFV = valorAsignadoSFV;
    }

    /**
     * @return the valorAjusteIPCSFV
     */
    public BigDecimal getValorAjusteIPCSFV() {
        return valorAjusteIPCSFV;
    }

    /**
     * @param valorAjusteIPCSFV
     *        the valorAjusteIPCSFV to set
     */
    public void setValorAjusteIPCSFV(BigDecimal valorAjusteIPCSFV) {
        this.valorAjusteIPCSFV = valorAjusteIPCSFV;
    }

    /**
     * @return the valorDesembolsado
     */
    public BigDecimal getValorDesembolsado() {
        return valorDesembolsado;
    }

    /**
     * @param valorDesembolsado
     *        the valorDesembolsado to set
     */
    public void setValorDesembolsado(BigDecimal valorDesembolsado) {
        this.valorDesembolsado = valorDesembolsado;
    }

    /**
     * @return the valorPendienteDesembolsar
     */
    public BigDecimal getValorPendienteDesembolsar() {
        return valorPendienteDesembolsar;
    }

    /**
     * @param valorPendienteDesembolsar
     *        the valorPendienteDesembolsar to set
     */
    public void setValorPendienteDesembolsar(BigDecimal valorPendienteDesembolsar) {
        this.valorPendienteDesembolsar = valorPendienteDesembolsar;
    }
    
    /**
     * @return the ValorSFVAjustado
     */
    public BigDecimal getValorSFVAjustado() {
        return valorSFVAjustado;
    }
    /**
     * @param ValorSFVAjustado
     *        the ValorSFVAjustado to set
     */
    public void setValorSFVAjustado(BigDecimal valorSFVAjustado) {
        this.valorSFVAjustado = valorSFVAjustado;
    }

    /**
     * @return the listaAnticipos
     */
    public List<HistoricoLegalizacionFOVISDTO> getListaAnticipos() {
        return listaAnticipos;
    }

    /**
     * @param listaAnticipos
     *        the listaAnticipos to set
     */
    public void setListaAnticipos(List<HistoricoLegalizacionFOVISDTO> listaAnticipos) {
        this.listaAnticipos = listaAnticipos;
    }

    /**
     * @return the fechaTransferenciaDesembolso
     */
    public Date getFechaTransferenciaDesembolso() {
        return fechaTransferenciaDesembolso;
    }

    /**
     * @param fechaTransferenciaDesembolso the fechaTransferenciaDesembolso to set
     */
    public void setFechaTransferenciaDesembolso(Date fechaTransferenciaDesembolso) {
        this.fechaTransferenciaDesembolso = fechaTransferenciaDesembolso;
    }
}
