package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroCorreccionEnum;

/**
 * <b>Descripcion:</b> DTO que agrupa la informacíon para presentar en la pantalla de resultados
 * de la validación de registros de planilla de corrección (Validar Planilla Nueva - registros C) <br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConjuntoResultadoRegistroCorreccionCDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Resultado comparación registros de corrección en registro C */
    private EstadoValidacionRegistroCorreccionEnum resultadoValidacionCorreccionC;

    /** Número ID Cotizante en registro de corrección C */
    private String idCotizanteC;

    /** Número de secuencia en C */
    private Long secuenciaC;

    /** Estado de evaluación en registro de corrección C */
    private EstadoAporteEnum estadoEvaluacionC;

    /** Valor del aporte obligatorio en registro de corrección C */
    private Integer aporteObligatorioC;

    /** Tipo de afiliado en registro de corrección C */
    private TipoAfiliadoEnum tipoAfiliadoC;

    /** Validación clase aportante en registro de corrección C */
    private EstadoValidacionRegistroAporteEnum estadoV0C;

    /** Validación tarifa en registro de corrección C */
    private EstadoValidacionRegistroAporteEnum estadoV1C;

    /** Validación tipo cotizante en registro de corrección C */
    private EstadoValidacionRegistroAporteEnum estadoV2C;

    /** Validación días cotizados en registro de corrección C */
    private EstadoValidacionRegistroAporteEnum estadoV3C;

    /** ID de registro detallado de corrección C */
    private Long idRegDetC;
    
    /** Marca de estado aprobado fase 1*/
    private Boolean registradoFase1;
    
    /** Marca de estado aprobado fase 2*/
    private Boolean registradoFase2;
    
    /** Marca de estado aprobado fase 3*/
    private Boolean registradoFase3;
    
    /** Marca de registro original anulado */
    private Boolean aporteAnulado = Boolean.FALSE;

    /**
     * @return the resultadoValidacionCorreccionC
     */
    public EstadoValidacionRegistroCorreccionEnum getResultadoValidacionCorreccionC() {
        return resultadoValidacionCorreccionC;
    }

    /**
     * @param resultadoValidacionCorreccionC the resultadoValidacionCorreccionC to set
     */
    public void setResultadoValidacionCorreccionC(EstadoValidacionRegistroCorreccionEnum resultadoValidacionCorreccionC) {
        this.resultadoValidacionCorreccionC = resultadoValidacionCorreccionC;
    }

    /**
     * @return the idCotizanteC
     */
    public String getIdCotizanteC() {
        return idCotizanteC;
    }

    /**
     * @param idCotizanteC the idCotizanteC to set
     */
    public void setIdCotizanteC(String idCotizanteC) {
        this.idCotizanteC = idCotizanteC;
    }

    /**
     * @return the secuenciaC
     */
    public Long getSecuenciaC() {
        return secuenciaC;
    }

    /**
     * @param secuenciaC the secuenciaC to set
     */
    public void setSecuenciaC(Long secuenciaC) {
        this.secuenciaC = secuenciaC;
    }

    /**
     * @return the estadoEvaluacionC
     */
    public EstadoAporteEnum getEstadoEvaluacionC() {
        return estadoEvaluacionC;
    }

    /**
     * @param estadoEvaluacionC the estadoEvaluacionC to set
     */
    public void setEstadoEvaluacionC(EstadoAporteEnum estadoEvaluacionC) {
        this.estadoEvaluacionC = estadoEvaluacionC;
    }

    /**
     * @return the aporteObligatorioC
     */
    public Integer getAporteObligatorioC() {
        return aporteObligatorioC;
    }

    /**
     * @param aporteObligatorioC the aporteObligatorioC to set
     */
    public void setAporteObligatorioC(Integer aporteObligatorioC) {
        this.aporteObligatorioC = aporteObligatorioC;
    }

    /**
     * @return the tipoAfiliadoC
     */
    public TipoAfiliadoEnum getTipoAfiliadoC() {
        return tipoAfiliadoC;
    }

    /**
     * @param tipoAfiliadoC the tipoAfiliadoC to set
     */
    public void setTipoAfiliadoC(TipoAfiliadoEnum tipoAfiliadoC) {
        this.tipoAfiliadoC = tipoAfiliadoC;
    }

    /**
     * @return the estadoV0C
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV0C() {
        return estadoV0C;
    }

    /**
     * @param estadoV0C the estadoV0C to set
     */
    public void setEstadoV0C(EstadoValidacionRegistroAporteEnum estadoV0C) {
        this.estadoV0C = estadoV0C;
    }

    /**
     * @return the estadoV1C
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV1C() {
        return estadoV1C;
    }

    /**
     * @param estadoV1C the estadoV1C to set
     */
    public void setEstadoV1C(EstadoValidacionRegistroAporteEnum estadoV1C) {
        this.estadoV1C = estadoV1C;
    }

    /**
     * @return the estadoV2C
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV2C() {
        return estadoV2C;
    }

    /**
     * @param estadoV2C the estadoV2C to set
     */
    public void setEstadoV2C(EstadoValidacionRegistroAporteEnum estadoV2C) {
        this.estadoV2C = estadoV2C;
    }

    /**
     * @return the estadoV3C
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV3C() {
        return estadoV3C;
    }

    /**
     * @param estadoV3C the estadoV3C to set
     */
    public void setEstadoV3C(EstadoValidacionRegistroAporteEnum estadoV3C) {
        this.estadoV3C = estadoV3C;
    }

    /**
     * @return the idRegDetC
     */
    public Long getIdRegDetC() {
        return idRegDetC;
    }

    /**
     * @param idRegDetC the idRegDetC to set
     */
    public void setIdRegDetC(Long idRegDetC) {
        this.idRegDetC = idRegDetC;
    }

    /**
     * @return the registradoFase1
     */
    public Boolean getRegistradoFase1() {
        return registradoFase1;
    }

    /**
     * @param registradoFase1 the registradoFase1 to set
     */
    public void setRegistradoFase1(Boolean registradoFase1) {
        this.registradoFase1 = registradoFase1;
    }

    /**
     * @return the registradoFase2
     */
    public Boolean getRegistradoFase2() {
        return registradoFase2;
    }

    /**
     * @param registradoFase2 the registradoFase2 to set
     */
    public void setRegistradoFase2(Boolean registradoFase2) {
        this.registradoFase2 = registradoFase2;
    }

    /**
     * @return the registradoFase3
     */
    public Boolean getRegistradoFase3() {
        return registradoFase3;
    }

    /**
     * @param registradoFase3 the registradoFase3 to set
     */
    public void setRegistradoFase3(Boolean registradoFase3) {
        this.registradoFase3 = registradoFase3;
    }

    /**
     * @return the aporteAnulado
     */
    public Boolean getAporteAnulado() {
        return aporteAnulado;
    }

    /**
     * @param aporteAnulado the aporteAnulado to set
     */
    public void setAporteAnulado(Boolean aporteAnulado) {
        this.aporteAnulado = aporteAnulado;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConjuntoResultadoRegistroCorreccionCDTO [");
		if (resultadoValidacionCorreccionC != null) {
			builder.append("resultadoValidacionCorreccionC=");
			builder.append(resultadoValidacionCorreccionC);
			builder.append(", ");
		}
		if (idCotizanteC != null) {
			builder.append("idCotizanteC=");
			builder.append(idCotizanteC);
			builder.append(", ");
		}
		if (secuenciaC != null) {
			builder.append("secuenciaC=");
			builder.append(secuenciaC);
			builder.append(", ");
		}
		if (estadoEvaluacionC != null) {
			builder.append("estadoEvaluacionC=");
			builder.append(estadoEvaluacionC);
			builder.append(", ");
		}
		if (aporteObligatorioC != null) {
			builder.append("aporteObligatorioC=");
			builder.append(aporteObligatorioC);
			builder.append(", ");
		}
		if (tipoAfiliadoC != null) {
			builder.append("tipoAfiliadoC=");
			builder.append(tipoAfiliadoC);
			builder.append(", ");
		}
		if (estadoV0C != null) {
			builder.append("estadoV0C=");
			builder.append(estadoV0C);
			builder.append(", ");
		}
		if (estadoV1C != null) {
			builder.append("estadoV1C=");
			builder.append(estadoV1C);
			builder.append(", ");
		}
		if (estadoV2C != null) {
			builder.append("estadoV2C=");
			builder.append(estadoV2C);
			builder.append(", ");
		}
		if (estadoV3C != null) {
			builder.append("estadoV3C=");
			builder.append(estadoV3C);
			builder.append(", ");
		}
		if (idRegDetC != null) {
			builder.append("idRegDetC=");
			builder.append(idRegDetC);
			builder.append(", ");
		}
		if (registradoFase1 != null) {
			builder.append("registradoFase1=");
			builder.append(registradoFase1);
			builder.append(", ");
		}
		if (registradoFase2 != null) {
			builder.append("registradoFase2=");
			builder.append(registradoFase2);
			builder.append(", ");
		}
		if (registradoFase3 != null) {
			builder.append("registradoFase3=");
			builder.append(registradoFase3);
			builder.append(", ");
		}
		if (aporteAnulado != null) {
			builder.append("aporteAnulado=");
			builder.append(aporteAnulado);
		}
		builder.append("]");
		return builder.toString();
	}

	
}
