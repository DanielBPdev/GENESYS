package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroCorreccionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información que se presenta por registro original
 * en la pantalla de "Validar Nueva Planilla"<br/>
 * 
 * <b>Módulo:</b> Asopagos - HU-211-410<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConjuntoResultadoRegistroCorreccionADTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Tipo identificación del cotizante en registro corrección A */
    private TipoIdentificacionEnum tipoIdCotizante;

    /** Nombre del cotizante */
    private String nombreCotizante;

    /** Número ID Cotizante en registro de corrección A */
    private String idCotizanteA;

    /** Número de secuencia en A */
    private Long secuenciaA;

    /** Estado de evaluación en registro de corrección A */
    private EstadoAporteEnum estadoEvaluacionA;

    /** Valor del aporte obligatorio en registro de corrección A */
    private Integer aporteObligatorioA;

    /** Tipo de afiliado en registro de corrección A */
    private TipoAfiliadoEnum tipoAfiliadoA;

    /** Validación clase aportante registro original */
    private EstadoValidacionRegistroAporteEnum estadoV0O;

    /** Validación tarifa registro original */
    private EstadoValidacionRegistroAporteEnum estadoV1O;

    /** Validación tipo cotizante registro original */
    private EstadoValidacionRegistroAporteEnum estadoV2O;

    /** Validación días cotizados registro original */
    private EstadoValidacionRegistroAporteEnum estadoV3O;

    /** Número ID Cotizante en registro planilla Original */
    private Long idRegDetOriginal;

    /** ID de registro detallado de corrección A */
    private Long idRegDetA;

    /** Resultado comparación registros de corrección en registro A */
    private EstadoValidacionRegistroCorreccionEnum resultadoValidacionCorreccionA;
    
    /** Marca de estado aprobado fase 1*/
    private Boolean registradoFase1;
    
    /** Marca de estado aprobado fase 2*/
    private Boolean registradoFase2;
    
    /** Marca de estado aprobado fase 3*/
    private Boolean registradoFase3;
    
    /** Listado de conjuntos de resultados corrección tipo C asociados */
    private List<ConjuntoResultadoRegistroCorreccionCDTO> resultadosC;
    
    /** Marca de registro original anulado */
    private Boolean aporteAnulado = Boolean.FALSE;

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the nombreCotizante
     */
    public String getNombreCotizante() {
        return nombreCotizante;
    }

    /**
     * @param nombreCotizante the nombreCotizante to set
     */
    public void setNombreCotizante(String nombreCotizante) {
        this.nombreCotizante = nombreCotizante;
    }

    /**
     * @return the idCotizanteA
     */
    public String getIdCotizanteA() {
        return idCotizanteA;
    }

    /**
     * @param idCotizanteA the idCotizanteA to set
     */
    public void setIdCotizanteA(String idCotizanteA) {
        this.idCotizanteA = idCotizanteA;
    }

    /**
     * @return the secuenciaA
     */
    public Long getSecuenciaA() {
        return secuenciaA;
    }

    /**
     * @param secuenciaA the secuenciaA to set
     */
    public void setSecuenciaA(Long secuenciaA) {
        this.secuenciaA = secuenciaA;
    }

    /**
     * @return the estadoEvaluacionA
     */
    public EstadoAporteEnum getEstadoEvaluacionA() {
        return estadoEvaluacionA;
    }

    /**
     * @param estadoEvaluacionA the estadoEvaluacionA to set
     */
    public void setEstadoEvaluacionA(EstadoAporteEnum estadoEvaluacionA) {
        this.estadoEvaluacionA = estadoEvaluacionA;
    }

    /**
     * @return the aporteObligatorioA
     */
    public Integer getAporteObligatorioA() {
        return aporteObligatorioA;
    }

    /**
     * @param aporteObligatorioA the aporteObligatorioA to set
     */
    public void setAporteObligatorioA(Integer aporteObligatorioA) {
        this.aporteObligatorioA = aporteObligatorioA;
    }

    /**
     * @return the tipoAfiliadoA
     */
    public TipoAfiliadoEnum getTipoAfiliadoA() {
        return tipoAfiliadoA;
    }

    /**
     * @param tipoAfiliadoA the tipoAfiliadoA to set
     */
    public void setTipoAfiliadoA(TipoAfiliadoEnum tipoAfiliadoA) {
        this.tipoAfiliadoA = tipoAfiliadoA;
    }

    /**
     * @return the estadoV0O
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV0O() {
        return estadoV0O;
    }

    /**
     * @param estadoV0O the estadoV0O to set
     */
    public void setEstadoV0O(EstadoValidacionRegistroAporteEnum estadoV0O) {
        this.estadoV0O = estadoV0O;
    }

    /**
     * @return the estadoV1O
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV1O() {
        return estadoV1O;
    }

    /**
     * @param estadoV1O the estadoV1O to set
     */
    public void setEstadoV1O(EstadoValidacionRegistroAporteEnum estadoV1O) {
        this.estadoV1O = estadoV1O;
    }

    /**
     * @return the estadoV2O
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV2O() {
        return estadoV2O;
    }

    /**
     * @param estadoV2O the estadoV2O to set
     */
    public void setEstadoV2O(EstadoValidacionRegistroAporteEnum estadoV2O) {
        this.estadoV2O = estadoV2O;
    }

    /**
     * @return the estadoV3O
     */
    public EstadoValidacionRegistroAporteEnum getEstadoV3O() {
        return estadoV3O;
    }

    /**
     * @param estadoV3O the estadoV3O to set
     */
    public void setEstadoV3O(EstadoValidacionRegistroAporteEnum estadoV3O) {
        this.estadoV3O = estadoV3O;
    }

    /**
     * @return the idRegDetA
     */
    public Long getIdRegDetA() {
        return idRegDetA;
    }

    /**
     * @param idRegDetA the idRegDetA to set
     */
    public void setIdRegDetA(Long idRegDetA) {
        this.idRegDetA = idRegDetA;
    }

    /**
     * @return the resultadoValidacionCorreccionA
     */
    public EstadoValidacionRegistroCorreccionEnum getResultadoValidacionCorreccionA() {
        return resultadoValidacionCorreccionA;
    }

    /**
     * @param resultadoValidacionCorreccionA the resultadoValidacionCorreccionA to set
     */
    public void setResultadoValidacionCorreccionA(EstadoValidacionRegistroCorreccionEnum resultadoValidacionCorreccionA) {
        this.resultadoValidacionCorreccionA = resultadoValidacionCorreccionA;
    }

    /**
     * @return the resultadosC
     */
    public List<ConjuntoResultadoRegistroCorreccionCDTO> getResultadosC() {
        return resultadosC;
    }

    /**
     * @param resultadosC the resultadosC to set
     */
    public void setResultadosC(List<ConjuntoResultadoRegistroCorreccionCDTO> resultadosC) {
        this.resultadosC = resultadosC;
    }

    /**
     * @return the idRegDetOriginal
     */
    public Long getIdRegDetOriginal() {
        return idRegDetOriginal;
    }

    /**
     * @param idRegDetOriginal the idRegDetOriginal to set
     */
    public void setIdRegDetOriginal(Long idRegDetOriginal) {
        this.idRegDetOriginal = idRegDetOriginal;
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
		builder.append("ConjuntoResultadoRegistroCorreccionADTO [");
		if (tipoIdCotizante != null) {
			builder.append("tipoIdCotizante=");
			builder.append(tipoIdCotizante);
			builder.append(", ");
		}
		if (nombreCotizante != null) {
			builder.append("nombreCotizante=");
			builder.append(nombreCotizante);
			builder.append(", ");
		}
		if (idCotizanteA != null) {
			builder.append("idCotizanteA=");
			builder.append(idCotizanteA);
			builder.append(", ");
		}
		if (secuenciaA != null) {
			builder.append("secuenciaA=");
			builder.append(secuenciaA);
			builder.append(", ");
		}
		if (estadoEvaluacionA != null) {
			builder.append("estadoEvaluacionA=");
			builder.append(estadoEvaluacionA);
			builder.append(", ");
		}
		if (aporteObligatorioA != null) {
			builder.append("aporteObligatorioA=");
			builder.append(aporteObligatorioA);
			builder.append(", ");
		}
		if (tipoAfiliadoA != null) {
			builder.append("tipoAfiliadoA=");
			builder.append(tipoAfiliadoA);
			builder.append(", ");
		}
		if (estadoV0O != null) {
			builder.append("estadoV0O=");
			builder.append(estadoV0O);
			builder.append(", ");
		}
		if (estadoV1O != null) {
			builder.append("estadoV1O=");
			builder.append(estadoV1O);
			builder.append(", ");
		}
		if (estadoV2O != null) {
			builder.append("estadoV2O=");
			builder.append(estadoV2O);
			builder.append(", ");
		}
		if (estadoV3O != null) {
			builder.append("estadoV3O=");
			builder.append(estadoV3O);
			builder.append(", ");
		}
		if (idRegDetOriginal != null) {
			builder.append("idRegDetOriginal=");
			builder.append(idRegDetOriginal);
			builder.append(", ");
		}
		if (idRegDetA != null) {
			builder.append("idRegDetA=");
			builder.append(idRegDetA);
			builder.append(", ");
		}
		if (resultadoValidacionCorreccionA != null) {
			builder.append("resultadoValidacionCorreccionA=");
			builder.append(resultadoValidacionCorreccionA);
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
		if (resultadosC != null) {
			builder.append("resultadosC=");
			builder.append(resultadosC);
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
