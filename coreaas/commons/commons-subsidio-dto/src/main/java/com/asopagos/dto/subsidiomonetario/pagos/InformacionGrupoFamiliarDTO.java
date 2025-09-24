package com.asopagos.dto.subsidiomonetario.pagos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de los grupos familiares relacionados con un administrador de
 * subsidio/afiliado/beneficiario<br/>
 * <b>Módulo:</b> Asopagos - Servicios de Integración<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class InformacionGrupoFamiliarDTO implements Serializable {

    private static final long serialVersionUID = -577278762399731079L;

    /**
     * Referencia de los datos del grupo familiar
     */
    private Long numeroGrupoFamilarRelacionador;

    /**
     * Tipo de transacción de la cuenta del administrador del subsidio.
     */
    private TipoTransaccionSubsidioMonetarioEnum tipoTransaccion;

    /**
     * Estado de la transacción de la cuenta del administrador del subsidio.
     */
    private EstadoTransaccionSubsidioEnum estadoTransaccion;

    /**
     * Origen de la transacción de la cuenta del administrador del subsidio.
     */
    private OrigenTransaccionEnum origenTransaccion;

    /**
     * Tipo de medio de pago relacionado con la cuenta del administrador del subsidio.
     */
    private TipoMedioDePagoEnum medioDePago;

    /**
     * valor subsidio monetario
     */
    private BigDecimal valorSubsidioMonetario;

    /**
     * Número de la tarjeta.
     */
    private String numeroTarjeta;

    /**
     * Nombre del banco del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String nombreBanco;

    /**
     * Tipo de cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private TipoCuentaEnum tipoCuenta;

    /**
     * Número de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String numeroCuenta;

    /**
     * Tipo de identificación del titular de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio;

    /**
     * variable que contiene el mismo valor del campo "valor original transacción"
     */
    private BigDecimal valorRealTransaccion;

    /**
     * Fecha y hora en la cual se realiza la transacción en la
     * cuenta del administrador del subsidio.
     */
    private String fechaHoraTransaccion;

    /**
     * Número de radicación generado para la transacción.
     */
    private String idTransaccion;
    
    /**
     * Códgio DANE del departamento de ubicación donde recibe el subsidio
     */
    private String departamentoCodigo;
    
    /**
     * Códgio DANE del municipio de ubicación donde recibe el subsidio
     */
    private String municipioCodigo;
    
    /**
     * Nombre del usuario que realizo la transacción
     */
    private String usuarioTransaccion;

    /**
     * Nombre del tercero pagador relacionado con la cuenta del administrador del subsidio.
     */
    private String nombreTerceroPagador;

    /**
     * Tipo de identificación del tercero pagador (convenio)
     */
    private TipoIdentificacionEnum tipoIdTerceroPagador;

    /**
     * Número de identificación del tercero pagador (convenio)
     */
    private String numeroIdTerceroPagador;

    /**
     * Nombre completo del administrador del subsidio
     */
    private String nombreAdminSubsidio;

    /**
     * Tipo de identificación del administrador de subsidio.
     */
    private TipoIdentificacionEnum tipoIdAdminSubsidio;

    /**
     * Número de identificación del administrador de subsidio
     */
    private String numeroIdAdminSubsidio;

    /**
     * Nombre del sitio de pago (Departamento,Municipio)
     */
    private String nombreSitioPago;

    /**
     * Nombre del sitio de cobro (Departamento,Municipio)
     */
    private String nombreSitioCobro;

    /**
     * Nombre completo del afiliado
     */
    private String nombreAfiliado;

    /**
     * Número de identificación del afiliado
     */
    private String numeroIdAfiliado;

    /**
     * Tipo de identififación del afiliado
     */
    private TipoIdentificacionEnum tipoIdAfiliado;

    /**
     * Periodo en que se realiza el subsidio
     */
    private String periodo;

    /**
     * Información de los beneficiarios
     */
    private List<ItemSubsidioBeneficiarioDTO> lstBeneficiarios;

    /**
     * @return the numeroGrupoFamilarRelacionador
     */
    public Long getNumeroGrupoFamilarRelacionador() {
        return numeroGrupoFamilarRelacionador;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionSubsidioMonetarioEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @return the estadoTransaccion
     */
    public EstadoTransaccionSubsidioEnum getEstadoTransaccion() {
        return estadoTransaccion;
    }

    /**
     * @return the origenTransaccion
     */
    public OrigenTransaccionEnum getOrigenTransaccion() {
        return origenTransaccion;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @return the tipoIdentificacionTitularCuentaAdminSubsidio
     */
    public TipoIdentificacionEnum getTipoIdentificacionTitularCuentaAdminSubsidio() {
        return tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @return the valorRealTransaccion
     */
    public BigDecimal getValorRealTransaccion() {
        return valorRealTransaccion;
    }

    /**
     * @return the fechaHoraTransaccion
     */
    public String getFechaHoraTransaccion() {
        return fechaHoraTransaccion;
    }

    /**
     * @return the usuarioTransaccion
     */
    public String getUsuarioTransaccion() {
        return usuarioTransaccion;
    }

    /**
     * @return the nombreTerceroPagador
     */
    public String getNombreTerceroPagador() {
        return nombreTerceroPagador;
    }

    /**
     * @return the nombreAdminSubsidio
     */
    public String getNombreAdminSubsidio() {
        return nombreAdminSubsidio;
    }

    /**
     * @return the tipoIdAdminSubsidio
     */
    public TipoIdentificacionEnum getTipoIdAdminSubsidio() {
        return tipoIdAdminSubsidio;
    }

    /**
     * @return the numeroIdAdminSubsidio
     */
    public String getNumeroIdAdminSubsidio() {
        return numeroIdAdminSubsidio;
    }

    /**
     * @return the nombreSitioPago
     */
    public String getNombreSitioPago() {
        return nombreSitioPago;
    }

    /**
     * @return the nombreSitioCobro
     */
    public String getNombreSitioCobro() {
        return nombreSitioCobro;
    }

    /**
     * @return the nombreAfiliado
     */
    public String getNombreAfiliado() {
        return nombreAfiliado;
    }

    /**
     * @return the numeroIdAfiliado
     */
    public String getNumeroIdAfiliado() {
        return numeroIdAfiliado;
    }

    /**
     * @return the tipoIdAfiliado
     */
    public TipoIdentificacionEnum getTipoIdAfiliado() {
        return tipoIdAfiliado;
    }

    /**
     * @param numeroGrupoFamilarRelacionador
     *        the numeroGrupoFamilarRelacionador to set
     */
    public void setNumeroGrupoFamilarRelacionador(Long numeroGrupoFamilarRelacionador) {
        this.numeroGrupoFamilarRelacionador = numeroGrupoFamilarRelacionador;
    }

    /**
     * @param tipoTransaccion
     *        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @param estadoTransaccion
     *        the estadoTransaccion to set
     */
    public void setEstadoTransaccion(EstadoTransaccionSubsidioEnum estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    /**
     * @param origenTransaccion
     *        the origenTransaccion to set
     */
    public void setOrigenTransaccion(OrigenTransaccionEnum origenTransaccion) {
        this.origenTransaccion = origenTransaccion;
    }

    /**
     * @param medioDePago
     *        the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @param tipoIdentificacionTitularCuentaAdminSubsidio
     *        the tipoIdentificacionTitularCuentaAdminSubsidio to set
     */
    public void setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio) {
        this.tipoIdentificacionTitularCuentaAdminSubsidio = tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @param valorRealTransaccion
     *        the valorRealTransaccion to set
     */
    public void setValorRealTransaccion(BigDecimal valorRealTransaccion) {
        this.valorRealTransaccion = valorRealTransaccion;
    }

    /**
     * @param fechaHoraTransaccion
     *        the fechaHoraTransaccion to set
     */
    public void setFechaHoraTransaccion(String fechaHoraTransaccion) {
        this.fechaHoraTransaccion = fechaHoraTransaccion;
    }

    /**
     * @param usuarioTransaccion
     *        the usuarioTransaccion to set
     */
    public void setUsuarioTransaccion(String usuarioTransaccion) {
        this.usuarioTransaccion = usuarioTransaccion;
    }

    /**
     * @param nombreTerceroPagador
     *        the nombreTerceroPagador to set
     */
    public void setNombreTerceroPagador(String nombreTerceroPagador) {
        this.nombreTerceroPagador = nombreTerceroPagador;
    }

    /**
     * @param nombreAdminSubsidio
     *        the nombreAdminSubsidio to set
     */
    public void setNombreAdminSubsidio(String nombreAdminSubsidio) {
        this.nombreAdminSubsidio = nombreAdminSubsidio;
    }

    /**
     * @param tipoIdAdminSubsidio
     *        the tipoIdAdminSubsidio to set
     */
    public void setTipoIdAdminSubsidio(TipoIdentificacionEnum tipoIdAdminSubsidio) {
        this.tipoIdAdminSubsidio = tipoIdAdminSubsidio;
    }

    /**
     * @param numeroIdAdminSubsidio
     *        the numeroIdAdminSubsidio to set
     */
    public void setNumeroIdAdminSubsidio(String numeroIdAdminSubsidio) {
        this.numeroIdAdminSubsidio = numeroIdAdminSubsidio;
    }

    /**
     * @param nombreSitioPago
     *        the nombreSitioPago to set
     */
    public void setNombreSitioPago(String nombreSitioPago) {
        this.nombreSitioPago = nombreSitioPago;
    }

    /**
     * @param nombreSitioCobro
     *        the nombreSitioCobro to set
     */
    public void setNombreSitioCobro(String nombreSitioCobro) {
        this.nombreSitioCobro = nombreSitioCobro;
    }

    /**
     * @param nombreAfiliado
     *        the nombreAfiliado to set
     */
    public void setNombreAfiliado(String nombreAfiliado) {
        this.nombreAfiliado = nombreAfiliado;
    }

    /**
     * @param numeroIdAfiliado
     *        the numeroIdAfiliado to set
     */
    public void setNumeroIdAfiliado(String numeroIdAfiliado) {
        this.numeroIdAfiliado = numeroIdAfiliado;
    }

    /**
     * @param tipoIdAfiliado
     *        the tipoIdAfiliado to set
     */
    public void setTipoIdAfiliado(TipoIdentificacionEnum tipoIdAfiliado) {
        this.tipoIdAfiliado = tipoIdAfiliado;
    }

    /**
     * @return the valorSubsidioMonetario
     */
    public BigDecimal getValorSubsidioMonetario() {
        return valorSubsidioMonetario;
    }

    /**
     * @param valorSubsidioMonetario
     *        the valorSubsidioMonetario to set
     */
    public void setValorSubsidioMonetario(BigDecimal valorSubsidioMonetario) {
        this.valorSubsidioMonetario = valorSubsidioMonetario;
    }

    /**
     * @return the tipoIdTerceroPagador
     */
    public TipoIdentificacionEnum getTipoIdTerceroPagador() {
        return tipoIdTerceroPagador;
    }

    /**
     * @return the numeroIdTerceroPagador
     */
    public String getNumeroIdTerceroPagador() {
        return numeroIdTerceroPagador;
    }

    /**
     * @param tipoIdTerceroPagador
     *        the tipoIdTerceroPagador to set
     */
    public void setTipoIdTerceroPagador(TipoIdentificacionEnum tipoIdTerceroPagador) {
        this.tipoIdTerceroPagador = tipoIdTerceroPagador;
    }

    /**
     * @param numeroIdTerceroPagador
     *        the numeroIdTerceroPagador to set
     */
    public void setNumeroIdTerceroPagador(String numeroIdTerceroPagador) {
        this.numeroIdTerceroPagador = numeroIdTerceroPagador;
    }

    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @return the tipoCuenta
     */
    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroTarjeta
     *        the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @param nombreBanco
     *        the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @param tipoCuenta
     *        the tipoCuenta to set
     */
    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @param numeroCuenta
     *        the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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
     * @return the lstBeneficiarios
     */
    public List<ItemSubsidioBeneficiarioDTO> getLstBeneficiarios() {
        return lstBeneficiarios;
    }

    /**
     * @param lstBeneficiarios
     *        the lstBeneficiarios to set
     */
    public void setLstBeneficiarios(List<ItemSubsidioBeneficiarioDTO> lstBeneficiarios) {
        this.lstBeneficiarios = lstBeneficiarios;
    }

	/**
	 * @return the departamentoCodigo
	 */
	public String getDepartamentoCodigo() {
		return departamentoCodigo;
	}

	/**
	 * @param departamentoCodigo the departamentoCodigo to set
	 */
	public void setDepartamentoCodigo(String departamentoCodigo) {
		this.departamentoCodigo = departamentoCodigo;
	}

	/**
	 * @return the municipioCodigo
	 */
	public String getMunicipioCodigo() {
		return municipioCodigo;
	}

	/**
	 * @param municipioCodigo the municipioCodigo to set
	 */
	public void setMunicipioCodigo(String municipioCodigo) {
		this.municipioCodigo = municipioCodigo;
	}

	/**
	 * @return the idTransaccion
	 */
	public String getIdTransaccion() {
		return idTransaccion;
	}

	/**
	 * @param idTransaccion the idTransaccion to set
	 */
	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}	
}
