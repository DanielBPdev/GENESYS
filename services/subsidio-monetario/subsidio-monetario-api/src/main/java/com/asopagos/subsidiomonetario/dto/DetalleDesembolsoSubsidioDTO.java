package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el detalle del desembolso de un subsidio especifico<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-523<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DetalleDesembolsoSubsidioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Periodo de fallecimiento de individuo asociado a la liquidación */
    private Date periodoFallecimiento;

    /** Indicardor de aportes para el periodo actual */
    private String aportesPeriodoActual;

    /** Indicador de información para desembolso para consideración de la caja */
    private String informacionParaDesembolso;

    /** Año de fallecimiento del individuo asociado a la liquidación */
    private Date anioFallecimiento;

    /** Estado del aporte */
    private EstadoAporteEnum estadoAporte;

    /** Modo de desembolso utilizado en el subsidio */
    private ModoDesembolsoEnum modoDesembolso;

    /**
     * @return the periodoFallecimiento
     */
    public Date getPeriodoFallecimiento() {
        return periodoFallecimiento;
    }

    /**
     * @param periodoFallecimiento
     *        the periodoFallecimiento to set
     */
    public void setPeriodoFallecimiento(Date periodoFallecimiento) {
        this.periodoFallecimiento = periodoFallecimiento;
    }

    /**
     * @return the aportesPeriodoActual
     */
    public String getAportesPeriodoActual() {
        return aportesPeriodoActual;
    }

    /**
     * @param aportesPeriodoActual
     *        the aportesPeriodoActual to set
     */
    public void setAportesPeriodoActual(String aportesPeriodoActual) {
        this.aportesPeriodoActual = aportesPeriodoActual;
    }

    /**
     * @return the informacionParaesembolso
     */
    public String getInformacionParaDesembolso() {
        return informacionParaDesembolso;
    }

    /**
     * @param informacionParaesembolso
     *        the informacionParaesembolso to set
     */
    public void setInformacionParaDesembolso(String informacionParaDesembolso) {
        this.informacionParaDesembolso = informacionParaDesembolso;
    }

    /**
     * @return the anioFallecimiento
     */
    public Date getAnioFallecimiento() {
        return anioFallecimiento;
    }

    /**
     * @param anioFallecimiento
     *        the anioFallecimiento to set
     */
    public void setAnioFallecimiento(Date anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    /**
     * @return the estadoAporte
     */
    public EstadoAporteEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * @param estadoAporte
     *        the estadoAporte to set
     */
    public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * @return the modoDesembolso
     */
    public ModoDesembolsoEnum getModoDesembolso() {
        return modoDesembolso;
    }

    /**
     * @param modoDesembolso
     *        the modoDesembolso to set
     */
    public void setModoDesembolso(ModoDesembolsoEnum modoDesembolso) {
        this.modoDesembolso = modoDesembolso;
    }

}
