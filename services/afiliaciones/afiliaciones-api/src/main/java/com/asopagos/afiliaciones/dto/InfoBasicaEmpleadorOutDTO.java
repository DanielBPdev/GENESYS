package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class InfoBasicaEmpleadorOutDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
    * Tipo de identificación del empleador
    */
    private TipoIdentificacionEnum tipoID;

    /**
    * Número de identificación del empleador
    */
    private String identificacion;

    /**
     * Dígito de verificación del empleador
     */
    private Short digitoVerificacion;
    
    /**
    * Razón social del empleador
    */
    private String razonSocial;
    
    /**
     * Nombre comercial del empleador
     */
    private String nombreComercial;

    /**
     * Naturaleza jurídica del empleador
     */
    private NaturalezaJuridicaEnum naturalezaJuridica;
    
    /**
    * Codigo DANE del Departamento de ubicación del empleador
    */
    private String departamentoCodigo;
    
    /**
     * Nombre del Departamento de ubicación del empleador
     */
    private String departamento;
    
    /**
    * Codigo DANE del Municipio de ubicación del empleador
    */
    private String municipioCodigo;

    /**
     * Nombre del Municipio de ubicación del empleador
     */
    private String municipio;
    
    /**
    * Dirección física principal del empleador
    */
    private String direccionPrincipal;

    /**
    * Indicativo del teléfono fijo + número fijo del empleador
    */
    private String telefonoFijo;

    /**
    * Número telefónico del celular del empleador
    */
    private String celular;

    /**
    * (Activo - Inactivo)
    */
    private String estadoAfiliacion;

    /**
    * (Al día - Moroso)
    */
    private EstadoCarteraEnum estadoCartera;

    /**
     * 
     */
    public InfoBasicaEmpleadorOutDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     * @param digitoVerificacion
     * @param razonSocial
     * @param nombreComercial
     * @param departamentoCodigo
     * @param departamento
     * @param municipioCodigo
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     * @param estadoAfiliacion
     */
    public InfoBasicaEmpleadorOutDTO(TipoIdentificacionEnum tipoID, String identificacion, Short digitoVerificacion, String razonSocial,
            String nombreComercial, String departamentoCodigo, String departamento, String municipioCodigo, String municipio,
            String direccionPrincipal, String telefonoFijo, String celular, String estadoAfiliacion) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.digitoVerificacion = digitoVerificacion;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.departamentoCodigo = departamentoCodigo;
        this.departamento = departamento;
        this.municipioCodigo = municipioCodigo;
        this.municipio = municipio;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.estadoAfiliacion = estadoAfiliacion;
    }
    
    /**
     * @param tipoID
     * @param identificacion
     * @param digitoVerificacion
     * @param razonSocial
     * @param nombreComercial
     * @param departamentoCodigo
     * @param departamento
     * @param municipioCodigo
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     * @param estadoAfiliacion
     * @param estadoCartera
     */
    public InfoBasicaEmpleadorOutDTO(TipoIdentificacionEnum tipoID, String identificacion, Short digitoVerificacion, String razonSocial,
            String nombreComercial, String departamentoCodigo, String departamento, String municipioCodigo, String municipio,
            String direccionPrincipal, String telefonoFijo, String celular, String estadoAfiliacion,
            EstadoCarteraEnum estadoCartera) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.digitoVerificacion = digitoVerificacion;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.departamentoCodigo = departamentoCodigo;
        this.departamento = departamento;
        this.municipioCodigo = municipioCodigo;
        this.municipio = municipio;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.estadoAfiliacion = estadoAfiliacion;
        this.estadoCartera = estadoCartera;
    }

    /**
     * @return the tipoID
     */
    public TipoIdentificacionEnum getTipoID() {
        return tipoID;
    }

    /**
     * @param tipoID the tipoID to set
     */
    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the nombreComercial
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * @param nombreComercial the nombreComercial to set
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
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
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
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
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the direccionPrincipal
     */
    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    /**
     * @param direccionPrincipal the direccionPrincipal to set
     */
    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    /**
     * @return the telefonoFijo
     */
    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    /**
     * @param telefonoFijo the telefonoFijo to set
     */
    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the estadoAfiliacion
     */
    public String getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    /**
     * @param estadoAfiliacion the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(String estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the estadoCartera
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    /**
     * @param estadoCartera the estadoCartera to set
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

	/**
	 * @return the naturalezaJuridica
	 */
	public NaturalezaJuridicaEnum getNaturalezaJuridica() {
		return naturalezaJuridica;
	}

	/**
	 * @param naturalezaJuridica the naturalezaJuridica to set
	 */
	public void setNaturalezaJuridica(NaturalezaJuridicaEnum naturalezaJuridica) {
		this.naturalezaJuridica = naturalezaJuridica;
	}
}
