package com.asopagos.aportes.composite.dto;

import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class ProcesoIngresoUtilitarioDTO {
    
    private TipoAfiliadoEnum tipoCotizante;
    private TipoIdentificacionEnum tipoIdAportante;
    private String numeroIdAportante;
    private TipoIdentificacionEnum tipoIdCotizante;
    private String numeroIdCotizante;
    private Boolean esReintegrable;
    private Long idRegistroDetallado;
    private Long idRegistroGeneral;
    private Boolean esEmpleadorReintegrable;
    private CanalRecepcionEnum canalRecepcion;
    private Long fechaIngreso;
    private String fechaIng;
    private String log;
    private String estadoAfiliacion;
    private String fechaRet;
    private Long fecharetiro;
    private Long idRegistroDetalladoRet;
    
    public Long getIdRegistroDetalladoRet() {
		return idRegistroDetalladoRet;
	}

	public void setIdRegistroDetalladoRet(Long idRegistroDetalladoRet) {
		this.idRegistroDetalladoRet = idRegistroDetalladoRet;
	}

	public String getFechaRet() {
		return fechaRet;
	}

	public void setFechaRet(String fechaRet) {
		this.fechaRet = fechaRet;
	}

	public Long getFecharetiro() {
		return fecharetiro;
	}

	public void setFecharetiro(Long fecharetiro) {
		this.fecharetiro = fecharetiro;
	}
    
    public String getFechaIng() {
		return fechaIng;
	}

	public void setFechaIng(String fechaIng) {
		this.fechaIng = fechaIng;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getEstadoAfiliacion() {
		return estadoAfiliacion;
	}

	public void setEstadoAfiliacion(String estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}

	/**
     * Constructor vacío
     * */
    public ProcesoIngresoUtilitarioDTO() {
    }

    /**
     * @return the tipoCotizante
     */
    public TipoAfiliadoEnum getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante
     *        the tipoCotizante to set
     */
    public void setTipoCotizante(TipoAfiliadoEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * @return the tipoIdAportante
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante
     *        the tipoIdAportante to set
     */
    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * @param numeroIdAportante
     *        the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante
     *        the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the numeroIdCotizante
     */
    public String getNumeroIdCotizante() {
        return numeroIdCotizante;
    }

    /**
     * @param numeroIdCotizante
     *        the numeroIdCotizante to set
     */
    public void setNumeroIdCotizante(String numeroIdCotizante) {
        this.numeroIdCotizante = numeroIdCotizante;
    }

    /**
     * @return the esReintegrable
     */
    public Boolean getEsReintegrable() {
        return esReintegrable;
    }

    /**
     * @param esReintegrable
     *        the esReintegrable to set
     */
    public void setEsReintegrable(Boolean esReintegrable) {
        this.esReintegrable = esReintegrable;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado
     *        the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral
     *        the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the esEmpleadorReintegrable
     */
    public Boolean getEsEmpleadorReintegrable() {
        return esEmpleadorReintegrable;
    }

    /**
     * @param esEmpleadorReintegrable
     *        the esEmpleadorReintegrable to set
     */
    public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
    }

    /**
     * @return the canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * @param canalRecepcion
     *        the canalRecepcion to set
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

    /**
     * @return the fechaIngreso
     */
    public Long getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso
     *        the fechaIngreso to set
     */
    public void setFechaIngreso(Long fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

}
