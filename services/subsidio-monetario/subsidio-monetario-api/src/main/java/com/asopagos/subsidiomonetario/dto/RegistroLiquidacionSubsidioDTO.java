package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de un registro para el
 * archivo de liquidación<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-442<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class RegistroLiquidacionSubsidioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fecha de liquidación para el proceso relacionado
     */
    private Date fechaLiquidacion;

    /**
     * Tipo de identificación del empleador asociado al subsidio
     */
    private String tipoIdentificacionEmpleador;

    /**
     * Número de identificación del empleador asociado al subsidio
     */
    private String numeroIdentificacionEmpleador;

    /**
     * Nombre del empleador
     */
    private String nombreEmpleador;

    /**
     * Código ciiu asociado a la actividad del empleador
     */
    private String ciiu;

    /**
     * Condición agraria del empleador
     */
    private String condicionAgraria;

    /**
     * Código de la sucursal del empleador
     */
    private String codigoSucursal;

    /**
     * Año de beneficio sobre la ley 1429
     */
    private String anioBeneficio1429;

    /**
     * Tipo de identificación del trabajador asociado al subsidio
     */
    private String tipoIdentificacionTrabajador;

    /**
     * Número de identificación del trabajador asociado al subsidio
     */
    private String numeroIdentificacionTrabajador;

    /**
     * Nombre del trabajador
     */
    private String nombreTrabajador;

    /**
     * Tipo de identificación del beneficiario asociado al subsidio
     */
    private String tipoIdentificacionBeneficiario;

    /**
     * Número de identificación del beneficiario asociado al subsidio
     */
    private String numeroIdentificacionBeneficiario;

    /**
     * Nombre del beneficiario
     */
    private String nombreBeneficiario;

    /**
     * Tipo de solicitante del subsidio
     */
    private String tipoSolicitante;

    /**
     * Clasificicación del solicitante de subsidio
     */
    private String clasificacion;

    /**
     * Valor original de la cuota asignada
     */
    private BigDecimal valorCuota;

    /**
     * Descuentos sobre el valor de la cuota
     */
    private BigDecimal descuento;

    /**
     * Valor total a pagar al beneficiairo
     */
    private BigDecimal valorPagar;

    /**
     * Indicador de invalidez
     */
    private String invalidez;

    /**
     * Tipo de identificación del administrador de subsidio asociado al subsidio
     */
    private String tipoIdentificacionAdministrador;

    /**
     * Número de identificación del administrador asociado al subsidio
     */
    private String numeroIdentificacionAdministrador;

    /**
     * Nombre del administrador asociado al subsidio
     */
    private String nombreAdministrador;

    /**
     * Código del convenio con las entidades de descuento
     */
    private String codigoConvenio;

    /**
     * Fecha del periodo liquidado
     */
    private String periodoLiquidado;

    /**
     * Tipo de periodo liquidado: retroactivo o regular
     */
    private String tipoPeriodo;
    
    /**
     * Fecha de la dispersión de la liquidación para el proceso relacionado
     */
    private Date fechaDispersion;
    
    /**
     * Nombre del medio de pago 
     */
    private String medioDePago;
    
    /**
     * Numero radicado
     */
    private String numeroRadicado;

    /** constructor por defecto */
    public RegistroLiquidacionSubsidioDTO() {}
    
    /** constructor consulta vista 360 empleador*/
	public RegistroLiquidacionSubsidioDTO(TipoIdentificacionEnum tipoIdentificacionTrabajador,
			String numeroIdentificacionTrabajador, String nombreTrabajador,
			TipoIdentificacionEnum tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario,
			String nombreBeneficiario, TipoIdentificacionEnum tipoIdentificacionAdministrador,
			String numeroIdentificacionAdministrador, String nombreAdministrador, Date fechaLiquidacion,
			Date fechaDispersion, TipoMedioDePagoEnum medioDePago, BigDecimal valorCuota, BigDecimal descuento,
			BigDecimal valorPagar, String numeroRadicacion) {
    	this.setTipoIdentificacionTrabajador(tipoIdentificacionTrabajador.name());
		this.setNumeroIdentificacionTrabajador(numeroIdentificacionTrabajador);
		this.setNombreTrabajador(nombreTrabajador);
		
		this.setTipoIdentificacionBeneficiario(tipoIdentificacionBeneficiario.name());
		this.setNumeroIdentificacionBeneficiario(numeroIdentificacionBeneficiario);
		this.setNombreBeneficiario(nombreBeneficiario);

		this.setTipoIdentificacionAdministrador(tipoIdentificacionAdministrador.name());
		this.setNumeroIdentificacionAdministrador(numeroIdentificacionAdministrador);
		this.setNombreAdministrador(nombreAdministrador);
		
		this.setFechaLiquidacion(fechaLiquidacion);
		this.setFechaDispersion(fechaDispersion);
		
		this.setMedioDePago(medioDePago.name());
		
		this.setValorCuota(valorCuota);
		this.setDescuento(descuento);
		this.setValorPagar(valorPagar);
		
		this.setNumeroRadicado(numeroRadicacion);
    }
    /**
     * @return the fechaLiquidacion
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *        the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the tipoIdentificacionEmpleador
     */
    public String getTipoIdentificacionEmpleador() {
        return tipoIdentificacionEmpleador;
    }

    /**
     * @param tipoIdentificacionEmpleador
     *        the tipoIdentificacionEmpleador to set
     */
    public void setTipoIdentificacionEmpleador(String tipoIdentificacionEmpleador) {
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
    }

    /**
     * @return the numeroIdentificacionEmpleador
     */
    public String getNumeroIdentificacionEmpleador() {
        return numeroIdentificacionEmpleador;
    }

    /**
     * @param numeroIdentificacionEmpleador
     *        the numeroIdentificacionEmpleador to set
     */
    public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
        this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
    }

    /**
     * @return the nombreEmpleador
     */
    public String getNombreEmpleador() {
        return nombreEmpleador;
    }

    /**
     * @param nombreEmpleador
     *        the nombreEmpleador to set
     */
    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    /**
     * @return the ciiu
     */
    public String getCiiu() {
        return ciiu;
    }

    /**
     * @param ciiu
     *        the ciiu to set
     */
    public void setCiiu(String ciiu) {
        this.ciiu = ciiu;
    }

    /**
     * @return the condicionAgracia
     */
    public String getCondicionAgraria() {
        return condicionAgraria;
    }

    /**
     * @param condicionAgracia
     *        the condicionAgracia to set
     */
    public void setCondicionAgraria(String condicionAgraria) {
        this.condicionAgraria = condicionAgraria;
    }

    /**
     * @return the codigoSucursal
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * @param codigoSucursal
     *        the codigoSucursal to set
     */
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * @return the anioBeneficio1429
     */
    public String getAnioBeneficio1429() {
        return anioBeneficio1429;
    }

    /**
     * @param anioBeneficio1429
     *        the anioBeneficio1429 to set
     */
    public void setAnioBeneficio1429(String anioBeneficio1429) {
        this.anioBeneficio1429 = anioBeneficio1429;
    }

    /**
     * @return the tipoIdentificacionTrabajador
     */
    public String getTipoIdentificacionTrabajador() {
        return tipoIdentificacionTrabajador;
    }

    /**
     * @param tipoIdentificacionTrabajador
     *        the tipoIdentificacionTrabajador to set
     */
    public void setTipoIdentificacionTrabajador(String tipoIdentificacionTrabajador) {
        this.tipoIdentificacionTrabajador = tipoIdentificacionTrabajador;
    }

    /**
     * @return the numeroIdentificacionTrabajador
     */
    public String getNumeroIdentificacionTrabajador() {
        return numeroIdentificacionTrabajador;
    }

    /**
     * @param numeroIdentificacionTrabajador
     *        the numeroIdentificacionTrabajador to set
     */
    public void setNumeroIdentificacionTrabajador(String numeroIdentificacionTrabajador) {
        this.numeroIdentificacionTrabajador = numeroIdentificacionTrabajador;
    }

    /**
     * @return the nombreTrabajador
     */
    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    /**
     * @param nombreTrabajador
     *        the nombreTrabajador to set
     */
    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    /**
     * @return the tipoIdentificacionBeneficiario
     */
    public String getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario
     *        the tipoIdentificacionBeneficiario to set
     */
    public void setTipoIdentificacionBeneficiario(String tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    /**
     * @return the numeroIdentificacionBeneficiario
     */
    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    /**
     * @param numeroIdentificacionBeneficiario
     *        the numeroIdentificacionBeneficiario to set
     */
    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }

    /**
     * @return the nombreBeneficiario
     */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /**
     * @param nombreBeneficiario
     *        the nombreBeneficiario to set
     */
    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /**
     * @return the tipoSolicitante
     */
    public String getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante
     *        the tipoSolicitante to set
     */
    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the clasificacion
     */
    public String getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion
     *        the clasificacion to set
     */
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the valorCuota
     */
    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    /**
     * @param valorCuota
     *        the valorCuota to set
     */
    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    /**
     * @return the descuento
     */
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * @param descuento
     *        the descuento to set
     */
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the valorPagar
     */
    public BigDecimal getValorPagar() {
        return valorPagar;
    }

    /**
     * @param valorPagar
     *        the valorPagar to set
     */
    public void setValorPagar(BigDecimal valorPagar) {
        this.valorPagar = valorPagar;
    }

    /**
     * @return the invalidez
     */
    public String getInvalidez() {
        return invalidez;
    }

    /**
     * @param invalidez
     *        the invalidez to set
     */
    public void setInvalidez(String invalidez) {
        this.invalidez = invalidez;
    }

    /**
     * @return the tipoIdentificacionAdministrador
     */
    public String getTipoIdentificacionAdministrador() {
        return tipoIdentificacionAdministrador;
    }

    /**
     * @param tipoIdentificacionAdministrador
     *        the tipoIdentificacionAdministrador to set
     */
    public void setTipoIdentificacionAdministrador(String tipoIdentificacionAdministrador) {
        this.tipoIdentificacionAdministrador = tipoIdentificacionAdministrador;
    }

    /**
     * @return the numeroIdentificacionAdministrador
     */
    public String getNumeroIdentificacionAdministrador() {
        return numeroIdentificacionAdministrador;
    }

    /**
     * @param numeroIdentificacionAdministrador
     *        the numeroIdentificacionAdministrador to set
     */
    public void setNumeroIdentificacionAdministrador(String numeroIdentificacionAdministrador) {
        this.numeroIdentificacionAdministrador = numeroIdentificacionAdministrador;
    }

    /**
     * @return the nombreAdministrador
     */
    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    /**
     * @param nombreAdministrador
     *        the nombreAdministrador to set
     */
    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    /**
     * @return the codigoConvenio
     */
    public String getCodigoConvenio() {
        return codigoConvenio;
    }

    /**
     * @param codigoConvenio
     *        the codigoConvenio to set
     */
    public void setCodigoConvenio(String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    /**
     * @return the periodoLiquidado
     */
    public String getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    /**
     * @param periodoLiquidado
     *        the periodoLiquidado to set
     */
    public void setPeriodoLiquidado(String periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    /**
     * @return the tipoPeriodo
     */
    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * @param tipoPeriodo
     *        the tipoPeriodo to set
     */
    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

	/**
	 * @return the fechaDispersion
	 */
	public Date getFechaDispersion() {
		return fechaDispersion;
	}

	/**
	 * @param fechaDispersion the fechaDispersion to set
	 */
	public void setFechaDispersion(Date fechaDispersion) {
		this.fechaDispersion = fechaDispersion;
	}

	/**
	 * @return the medioDePago
	 */
	public String getMedioDePago() {
		return medioDePago;
	}

	/**
	 * @param medioDePago the medioDePago to set
	 */
	public void setMedioDePago(String medioDePago) {
		this.medioDePago = medioDePago;
	}
	
	/**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

}
