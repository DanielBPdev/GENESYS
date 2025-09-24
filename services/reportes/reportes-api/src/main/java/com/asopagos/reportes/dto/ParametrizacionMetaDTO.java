package com.asopagos.reportes.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.MetaEnum;
import com.asopagos.enumeraciones.reportes.PeriodicidadMetaEnum;

/**
 * <b>Descripción</b> DTO que representa los datos de la parametrizacion de las metas
 * <b></b>
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
@XmlRootElement
public class ParametrizacionMetaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Meta de la parametrización
     */
    private MetaEnum meta;
    /**
     * Periodicidad de la meta
     */
    private PeriodicidadMetaEnum periodicidad;
    /**
     * Periodo de la parametrización
     */
    private String periodo;
    /**
     * Frecuencia de los valores
     */
    private FrecuenciaMetaEnum frecuencia;
    /**
     * Lista con los datos parametrizados de los filtros escogidos
     */
    private List<DatosParametrizacionMetaDTO> datosParametrizacion;

    public ParametrizacionMetaDTO() {
    }

    /**
     * @return the meta
     */
    public MetaEnum getMeta() {
        return meta;
    }

    /**
     * @param meta
     *        the meta to set
     */
    public void setMeta(MetaEnum meta) {
        this.meta = meta;
    }

    /**
     * @return the periodicidad
     */
    public PeriodicidadMetaEnum getPeriodicidad() {
        return periodicidad;
    }

    /**
     * @param periodicidad
     *        the periodicidad to set
     */
    public void setPeriodicidad(PeriodicidadMetaEnum periodicidad) {
        this.periodicidad = periodicidad;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo
     *        the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the frecuencia
     */
    public FrecuenciaMetaEnum getFrecuencia() {
        return frecuencia;
    }

    /**
     * @param frecuencia
     *        the frecuencia to set
     */
    public void setFrecuencia(FrecuenciaMetaEnum frecuencia) {
        this.frecuencia = frecuencia;
    }

    /**
     * @return the datosParametrizacion
     */
    public List<DatosParametrizacionMetaDTO> getDatosParametrizacion() {
        return datosParametrizacion;
    }

    /**
     * @param datosParametrizacion
     *        the datosParametrizacion to set
     */
    public void setDatosParametrizacion(List<DatosParametrizacionMetaDTO> datosParametrizacion) {
        this.datosParametrizacion = datosParametrizacion;
    }

}
