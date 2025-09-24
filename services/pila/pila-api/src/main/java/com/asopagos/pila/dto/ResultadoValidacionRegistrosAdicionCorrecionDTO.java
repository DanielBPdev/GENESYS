package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> DTO que agrupa la informacíon para presentar en la pantalla de resultados
 * de la validación de registros de planilla de corrección (Validar Planilla Nueva) <br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoValidacionRegistrosAdicionCorrecionDTO implements Serializable {
    private static final long serialVersionUID = -1462402501918638022L;

    /** Número de la planilla */
    private String numeroPlanilla;

    /** Tipo de planilla */
    private String tipoPlanilla;

    /** Fecha de procesamiento */
    private Long fechaProceso;
    
    /** ID del registro general del archivo de corrección */
    private Long idRegGeneralAdicionCorreccion;
    
    /** ID del registro general del archivo original */
    private Long idRegGeneralOriginal;
    
    /** Indica que el USP para la fase 2 ya ha sido ejecutado */
    private Boolean hayDatosFase2;
    
    /** Listado de conjuntos de resultados */
    private List<ConjuntoResultadoRegistroCorreccionCDTO> resultados;

    private Long idTransaccion;
    
    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the tipoPlanilla
     */
    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * @param tipoPlanilla the tipoPlanilla to set
     */
    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * @return the fechaProceso
     */
    public Long getFechaProceso() {
        return fechaProceso;
    }

    /**
     * @param fechaProceso the fechaProceso to set
     */
    public void setFechaProceso(Long fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    /**
     * @return the resultados
     */
    public List<ConjuntoResultadoRegistroCorreccionCDTO> getResultados() {
        return resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(List<ConjuntoResultadoRegistroCorreccionCDTO> resultados) {
        this.resultados = resultados;
    }

    /**
     * @return the idRegGeneralAdicionCorreccion
     */
    public Long getIdRegGeneralAdicionCorreccion() {
        return idRegGeneralAdicionCorreccion;
    }

    /**
     * @param idRegGeneralAdicionCorreccion the idRegGeneralAdicionCorreccion to set
     */
    public void setIdRegGeneralAdicionCorreccion(Long idRegGeneralAdicionCorreccion) {
        this.idRegGeneralAdicionCorreccion = idRegGeneralAdicionCorreccion;
    }

    /**
     * @return the idRegGeneralOriginal
     */
    public Long getIdRegGeneralOriginal() {
        return idRegGeneralOriginal;
    }

    /**
     * @param idRegGeneralOriginal the idRegGeneralOriginal to set
     */
    public void setIdRegGeneralOriginal(Long idRegGeneralOriginal) {
        this.idRegGeneralOriginal = idRegGeneralOriginal;
    }

    /**
     * @return the hayDatosFase2
     */
    public Boolean getHayDatosFase2() {
        return hayDatosFase2;
    }

    /**
     * @param hayDatosFase2 the hayDatosFase2 to set
     */
    public void setHayDatosFase2(Boolean hayDatosFase2) {
        this.hayDatosFase2 = hayDatosFase2;
    }

    
    
	/**
	 * @return the idTransaccion
	 */
	public Long getIdTransaccion() {
		return idTransaccion;
	}

	/**
	 * @param idTransaccion the idTransaccion to set
	 */
	public void setIdTransaccion(Long idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultadoValidacionRegistrosAdicionCorrecionDTO [");
		if (numeroPlanilla != null) {
			builder.append("numeroPlanilla=");
			builder.append(numeroPlanilla);
			builder.append(", ");
		}
		if (tipoPlanilla != null) {
			builder.append("tipoPlanilla=");
			builder.append(tipoPlanilla);
			builder.append(", ");
		}
		if (fechaProceso != null) {
			builder.append("fechaProceso=");
			builder.append(fechaProceso);
			builder.append(", ");
		}
		if (idRegGeneralAdicionCorreccion != null) {
			builder.append("idRegGeneralAdicionCorreccion=");
			builder.append(idRegGeneralAdicionCorreccion);
			builder.append(", ");
		}
		if (idRegGeneralOriginal != null) {
			builder.append("idRegGeneralOriginal=");
			builder.append(idRegGeneralOriginal);
			builder.append(", ");
		}
		if (hayDatosFase2 != null) {
			builder.append("hayDatosFase2=");
			builder.append(hayDatosFase2);
			builder.append(", ");
		}
		if (resultados != null) {
			builder.append("resultados=");
			builder.append(resultados);
			builder.append(", ");
		}
		if (idTransaccion != null) {
			builder.append("idTransaccion=");
			builder.append(idTransaccion);
		}
		builder.append("]");
		return builder.toString();
	}



}
