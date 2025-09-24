package com.asopagos.reportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;

/**
 * <b>Descripcion:</b> Clase DTO que muestra la información del resultado del reporte que se va a generar<br/>
 * <b>Módulo:</b> Asopagos - HU - <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class ResultadoReporteDTO implements Serializable {

    private static final long serialVersionUID = 812181297523655311L;

    /**
     * Nombre del reporte que se va a generar.
     */
    private String nombreReporteGenerado;

    /**
     * Número de registros del reporte
     */
    private Integer numeroRegistros;

    /**
     * Formato de entrega del reporte según la norma
     */
    private FormatoReporteEnum formatoEntregaReporteNorma;

    /**
     * periodicidad del reporte
     */
    private FrecuenciaMetaEnum periodicidadReporte;
    
    /**
     * Datos de la ficha de control
     */
    private FichaControlDTO fichaControl;
    
    private Byte periodosDesagregado;

    /**
     * @return the nombreReporteGenerado
     */
    public String getNombreReporteGenerado() {
        return nombreReporteGenerado;
    }

    /**
     * @return the numeroRegistros
     */
    public Integer getNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     * @return the formatoEntregaReporteNorma
     */
    public FormatoReporteEnum getFormatoEntregaReporteNorma() {
        return formatoEntregaReporteNorma;
    }

    /**
     * @param nombreReporteGenerado
     *        the nombreReporteGenerado to set
     */
    public void setNombreReporteGenerado(String nombreReporteGenerado) {
        this.nombreReporteGenerado = nombreReporteGenerado;
    }

    /**
     * @param numeroRegistros
     *        the numeroRegistros to set
     */
    public void setNumeroRegistros(Integer numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    /**
     * @param formatoEntregaReporteNorma
     *        the formatoEntregaReporteNorma to set
     */
    public void setFormatoEntregaReporteNorma(FormatoReporteEnum formatoEntregaReporteNorma) {
        this.formatoEntregaReporteNorma = formatoEntregaReporteNorma;
    }

    /**
     * @return the periodicidadReporte
     */
    public FrecuenciaMetaEnum getPeriodicidadReporte() {
        return periodicidadReporte;
    }

    /**
     * @param periodicidadReporte
     *        the periodicidadReporte to set
     */
    public void setPeriodicidadReporte(FrecuenciaMetaEnum periodicidadReporte) {
        this.periodicidadReporte = periodicidadReporte;
    }

	public FichaControlDTO getFichaControl() {
		return fichaControl;
	}

	public void setFichaControl(FichaControlDTO fichaControl) {
		this.fichaControl = fichaControl;
	}

	public Byte getPeriodosDesagregado() {
		return periodosDesagregado;
	}

	public void setPeriodosDesagregado(Byte periodosDesagregado) {
		this.periodosDesagregado = periodosDesagregado;
	}
    
    

}
