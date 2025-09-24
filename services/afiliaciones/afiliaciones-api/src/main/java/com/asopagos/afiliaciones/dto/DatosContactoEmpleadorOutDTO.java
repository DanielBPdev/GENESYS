package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class DatosContactoEmpleadorOutDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Sucursal de la empresa a la que pertenece el contacto.
     */
    private String codigoSucursal;
    
    /**
     * Nombre de la sucursal del empleador
     */
    private String nombreSucursal;
    
    /**
     * Contacto Afiliación, Contacto Aportes, Contacto Subsidio
     */
    private TipoRolContactoEnum tipoContacto;

    /**
     * Tipo de identificación del contacto para aportes
     */
    private TipoIdentificacionEnum tipoID;

    /**
     * Número de identificación del contacto para aportes
     */
    private String identificacion;

    /**
     * Nombre completo del contacto para aportes
     */
    private String nombreCompleto;

    /**
     * Departamento de ubicación del contacto para aportes
     */
    private String departamento;

    /**
     * Municipio de ubicación del contacto para aportes
     */
    private String municipio;

    /**
     * Dirección física principal del contacto para aportes
     */
    private String direccionPrincipal;

    /**
     * Indicativo del teléfono fijo + número fijo del contacto para aportes
     */
    private String telefonoFijo;

    /**
     * Número telefónico del contacto para aportes
     */
    private String celular;

    /**
     * Correo electrónico del contacto para aportes
     */
    private String correoElectronico;

    /**
     * 
     */
    public DatosContactoEmpleadorOutDTO() {
    }

    /**
     * @param tipoContacto
     * @param tipoID
     * @param identificacion
     * @param nombreCompleto
     * @param departamento
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     * @param correoElectronico
     */
    public DatosContactoEmpleadorOutDTO(TipoRolContactoEnum tipoContacto, TipoIdentificacionEnum tipoID, String identificacion,
            String nombreCompleto, String departamento, String municipio, String direccionPrincipal, String telefonoFijo, String celular,
            String correoElectronico) {
        this.tipoContacto = tipoContacto;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.departamento = departamento;
        this.municipio = municipio;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the tipoContacto
     */
    public TipoRolContactoEnum getTipoContacto() {
        return tipoContacto;
    }

    /**
     * @param tipoContacto the tipoContacto to set
     */
    public void setTipoContacto(TipoRolContactoEnum tipoContacto) {
        this.tipoContacto = tipoContacto;
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
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

	/**
	 * @return the codigoSucursal
	 */
	public String getCodigoSucursal() {
		return codigoSucursal;
	}

	/**
	 * @param codigoSucursal the codigoSucursal to set
	 */
	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}

	/**
	 * @return the nombreSucursal
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}

	/**
	 * @param nombreSucursal the nombreSucursal to set
	 */
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
}
