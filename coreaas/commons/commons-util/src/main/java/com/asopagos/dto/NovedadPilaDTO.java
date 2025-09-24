package com.asopagos.dto;	
import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO con los datos del Modelo de NovedadPila.
 * 
 * @author Steven Quintero<squintero@heinsohn.com.co>
 *
 */
public class NovedadPilaDTO implements Serializable{
    private static final long serialVersionUID = -37669882563735510L;
    
    private Boolean marcaNovedadSimulado;
	private Boolean marcaNovedadManual;
	private TipoTransaccionEnum tipoTransaccion;
	private Boolean esIngreso;
	private Boolean esRetiro;
	private TipoIdentificacionEnum tipoIdentificacionCotizante;
	private String numeroIdentificacionCotizante;
	private Date fechaInicioNovedad;
	private Date fechaFinNovedad;
	private String accionNovedad;
	private String mensajeNovedad;
	private Long idRegistroDetallado;
	private TipoAfiliadoEnum tipoCotizante;
	private String valor;
	private Long idTransaccion;
	private Long idRegistroGeneral;
	private Boolean esTrabajadorReintegrable;
	private Boolean esEmpleadorReintegrable;
    private Long idRegistroDetalladoNovedad;
    private Long idTenNovedad;   
	private Boolean novedadexistenteCore;
	private Boolean isIngresoRetiro;
	private String beneficiarios;
	/**
	 * 
	 */
	public NovedadPilaDTO() {
	}

	/**
	 * @param marcaNovedadSimulado
	 * @param marcaNovedadManual
	 * @param tipoTransaccion
	 * @param esIngreso
	 * @param esRetiro
	 * @param tipoIdentificacionCotizante
	 * @param numeroIdentificacionCotizante
	 * @param fechaInicioNovedad
	 * @param fechaFinNovedad
	 * @param accionNovedad
	 * @param mensajeNovedad
	 * @param tipoCotizante
	 * @param valor 
	 */
	public NovedadPilaDTO(Boolean marcaNovedadSimulado, Boolean marcaNovedadManual, TipoTransaccionEnum tipoTransaccion,
			Boolean esIngreso, Boolean esRetiro, TipoIdentificacionEnum tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, Date fechaInicioNovedad, Date fechaFinNovedad, String accionNovedad,
			String mensajeNovedad, TipoAfiliadoEnum tipoCotizante,String valor) {
		this.marcaNovedadSimulado = marcaNovedadSimulado;
		this.marcaNovedadManual = marcaNovedadManual;
		this.tipoTransaccion = tipoTransaccion;
		this.esIngreso = esIngreso;
		this.esRetiro = esRetiro;
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.fechaInicioNovedad = fechaInicioNovedad;
		this.fechaFinNovedad = fechaFinNovedad;
		this.accionNovedad = accionNovedad;
		this.mensajeNovedad = mensajeNovedad;
		this.tipoCotizante = tipoCotizante;
		this.valor = valor;
	}
	
	/**
     * @param marcaNovedadSimulado
     * @param marcaNovedadManual
     * @param tipoTransaccion
     * @param esIngreso
     * @param esRetiro
     * @param tipoIdentificacionCotizante
     * @param numeroIdentificacionCotizante
     * @param fechaInicioNovedad
     * @param fechaFinNovedad
     * @param accionNovedad
     * @param mensajeNovedad
     * @param tipoCotizante
	 * @param idTransaccion 
	 * @param idRegistroGeneral 
	 * @param idTenNovedad 
	 * @param novedadAplicada 
     */
    public NovedadPilaDTO(Boolean marcaNovedadSimulado, Boolean marcaNovedadManual, TipoTransaccionEnum tipoTransaccion,
            Boolean esIngreso, Boolean esRetiro, TipoIdentificacionEnum tipoIdentificacionCotizante,
            String numeroIdentificacionCotizante, Date fechaInicioNovedad, Date fechaFinNovedad, String accionNovedad,
            String mensajeNovedad, TipoAfiliadoEnum tipoCotizante, Long idTransaccion, Long idRegistroGeneral,
            Long idTenNovedad, Boolean novedadexistenteCore) {
        this.marcaNovedadSimulado = marcaNovedadSimulado;
        this.marcaNovedadManual = marcaNovedadManual;
        this.tipoTransaccion = tipoTransaccion;
        this.esIngreso = esIngreso;
        this.esRetiro = esRetiro;
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
        this.fechaInicioNovedad = fechaInicioNovedad;
        this.fechaFinNovedad = fechaFinNovedad;
        this.accionNovedad = accionNovedad;
        this.mensajeNovedad = mensajeNovedad;
        this.tipoCotizante = tipoCotizante;
        this.idTransaccion = idTransaccion;
        this.idRegistroGeneral = idRegistroGeneral;
        this.idTenNovedad = idTenNovedad;  
		this.novedadexistenteCore = novedadexistenteCore;      
        
        
    }

	/**
	 * Método que retorna el valor de marcaNovedadSimulado.
	 * @return valor de marcaNovedadSimulado.
	 */
	public Boolean getMarcaNovedadSimulado() {
		return marcaNovedadSimulado;
	}

	/**
	 * Método que retorna el valor de tipoCotizante.
	 * @return valor de tipoCotizante.
	 */
	public TipoAfiliadoEnum getTipoCotizante() {
		return tipoCotizante;
	}

	/**
	 * Método encargado de modificar el valor de tipoCotizante.
	 * @param valor para modificar tipoCotizante.
	 */
	public void setTipoCotizante(TipoAfiliadoEnum tipoCotizante) {
		this.tipoCotizante = tipoCotizante;
	}

	/**
	 * Método encargado de modificar el valor de marcaNovedadSimulado.
	 * @param valor para modificar marcaNovedadSimulado.
	 */
	public void setMarcaNovedadSimulado(Boolean marcaNovedadSimulado) {
		this.marcaNovedadSimulado = marcaNovedadSimulado;
	}

	/**
	 * Método que retorna el valor de marcaNovedadManual.
	 * @return valor de marcaNovedadManual.
	 */
	public Boolean getMarcaNovedadManual() {
		return marcaNovedadManual;
	}

	/**
	 * Método encargado de modificar el valor de marcaNovedadManual.
	 * @param valor para modificar marcaNovedadManual.
	 */
	public void setMarcaNovedadManual(Boolean marcaNovedadManual) {
		this.marcaNovedadManual = marcaNovedadManual;
	}

	/**
	 * Método que retorna el valor de tipoTransaccion.
	 * @return valor de tipoTransaccion.
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * Método encargado de modificar el valor de tipoTransaccion.
	 * @param valor para modificar tipoTransaccion.
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * Método que retorna el valor de esIngreso.
	 * @return valor de esIngreso.
	 */
	public Boolean getEsIngreso() {
		return esIngreso;
	}

	/**
	 * Método encargado de modificar el valor de esIngreso.
	 * @param valor para modificar esIngreso.
	 */
	public void setEsIngreso(Boolean esIngreso) {
		this.esIngreso = esIngreso;
	}

	/**
	 * Método que retorna el valor de esRetiro.
	 * @return valor de esRetiro.
	 */
	public Boolean getEsRetiro() {
		return esRetiro;
	}

	/**
	 * Método encargado de modificar el valor de esRetiro.
	 * @param valor para modificar esRetiro.
	 */
	public void setEsRetiro(Boolean esRetiro) {
		this.esRetiro = esRetiro;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionCotizante.
	 * @return valor de tipoIdentificacionCotizante.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
		return tipoIdentificacionCotizante;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionCotizante.
	 * @param valor para modificar tipoIdentificacionCotizante.
	 */
	public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionCotizante.
	 * @return valor de numeroIdentificacionCotizante.
	 */
	public String getNumeroIdentificacionCotizante() {
		return numeroIdentificacionCotizante;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionCotizante.
	 * @param valor para modificar numeroIdentificacionCotizante.
	 */
	public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
	}

	/**
	 * Método que retorna el valor de fechaInicioNovedad.
	 * @return valor de fechaInicioNovedad.
	 */
	public Date getFechaInicioNovedad() {
		return fechaInicioNovedad;
	}

	/**
	 * Método encargado de modificar el valor de fechaInicioNovedad.
	 * @param valor para modificar fechaInicioNovedad.
	 */
	public void setFechaInicioNovedad(Date fechaInicioNovedad) {
		this.fechaInicioNovedad = fechaInicioNovedad;
	}

	/**
	 * Método que retorna el valor de fechaFinNovedad.
	 * @return valor de fechaFinNovedad.
	 */
	public Date getFechaFinNovedad() {
		return fechaFinNovedad;
	}

	/**
	 * Método encargado de modificar el valor de fechaFinNovedad.
	 * @param valor para modificar fechaFinNovedad.
	 */
	public void setFechaFinNovedad(Date fechaFinNovedad) {
		this.fechaFinNovedad = fechaFinNovedad;
	}

	/**
	 * Método que retorna el valor de accionNovedad.
	 * @return valor de accionNovedad.
	 */
	public String getAccionNovedad() {
		return accionNovedad;
	}

	/**
	 * Método encargado de modificar el valor de accionNovedad.
	 * @param valor para modificar accionNovedad.
	 */
	public void setAccionNovedad(String accionNovedad) {
		this.accionNovedad = accionNovedad;
	}

	/**
	 * Método que retorna el valor de mensajeNovedad.
	 * @return valor de mensajeNovedad.
	 */
	public String getMensajeNovedad() {
		return mensajeNovedad;
	}

	/**
	 * Método encargado de modificar el valor de mensajeNovedad.
	 * @param valor para modificar mensajeNovedad.
	 */
	public void setMensajeNovedad(String mensajeNovedad) {
		this.mensajeNovedad = mensajeNovedad;
	}

	/**
	 * @return the idRegistroDetallado
	 */
	public Long getIdRegistroDetallado() {
		return idRegistroDetallado;
	}

	/**
	 * @param idRegistroDetallado the idRegistroDetallado to set
	 */
	public void setIdRegistroDetallado(Long idRegistroDetallado) {
		this.idRegistroDetallado = idRegistroDetallado;
	}

	/**
	 * Método que retorna el valor de valor.
	 * @return valor de valor.
	 */ 
	public String getValor() {
		return valor;
	}

	/**
	 * Método encargado de modificar el valor de valor.
	 * @param valor para modificar valor.
	 */ 
	public void setValor(String valor) {
		this.valor = valor;
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

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the esTrabajadorReintegrable
     */
    public Boolean getEsTrabajadorReintegrable() {
        return esTrabajadorReintegrable;
    }

    /**
     * @param esTrabajadorReintegrable the esTrabajadorReintegrable to set
     */
    public void setEsTrabajadorReintegrable(Boolean esTrabajadorReintegrable) {
        this.esTrabajadorReintegrable = esTrabajadorReintegrable;
    }

    /**
     * @return the esEmpleadorReintegrable
     */
    public Boolean getEsEmpleadorReintegrable() {
        return esEmpleadorReintegrable;
    }

    /**
     * @param esEmpleadorReintegrable the esEmpleadorReintegrable to set
     */
    public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
    }

    /**
     * @return the idRegistroDetalladoNovedad
     */
    public Long getIdRegistroDetalladoNovedad() {
        return idRegistroDetalladoNovedad;
    }

    /**
     * @param idRegistroDetalladoNovedad the idRegistroDetalladoNovedad to set
     */
    public void setIdRegistroDetalladoNovedad(Long idRegistroDetalladoNovedad) {
        this.idRegistroDetalladoNovedad = idRegistroDetalladoNovedad;
    }

    /**
     * @return s
     */
    public Long getIdTenNovedad() {
        return idTenNovedad;
    }

    /**
     * @param idTenNovedad
     */
    public void setIdTenNovedad(Long idTenNovedad) {
        this.idTenNovedad = idTenNovedad;
    }

       /**
     * @return the novedadexistenteCore
     */
    public Boolean getNovedadexistenteCore() {
        return novedadexistenteCore;
    }

    /**
     * @param novedadexistenteCore
     *        the novedadexistenteCore to set
     */
    public void setNovedadexistenteCore(Boolean novedadexistenteCore) {
        this.novedadexistenteCore = novedadexistenteCore;
    }

	public Boolean getIsIngresoRetiro() {
		return this.isIngresoRetiro;
	}

	public void setIsIngresoRetiro(Boolean isIngresoRetiro) {
		this.isIngresoRetiro = isIngresoRetiro;
	}

	public String getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(String beneficiarios) {
		this.beneficiarios = beneficiarios;
	}
}
