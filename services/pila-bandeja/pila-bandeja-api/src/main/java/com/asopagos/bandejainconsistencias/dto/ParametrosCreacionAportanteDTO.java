package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */

public class ParametrosCreacionAportanteDTO implements Serializable {
    private static final long serialVersionUID = 7242609289228127910L;
    
    private TipoIdentificacionEnum tipoIdentificacion; 
    private String idAportante;
    private String nombreAportante; 
    private Short digVerAportante;
    private String codMunicipio;
    private String codDepartamento;
    private String direccion;
    private String email;
    private String telefono; 
    private Date fechaMatricula;
    private NaturalezaJuridicaEnum naturalezaJuridica;
    private String codSucursal;
    private String nomSucursal;
    
    /**
     * Constructor con valores
     * */
    public ParametrosCreacionAportanteDTO(TipoIdentificacionEnum tipoIdentificacion, String idAportante, String nombreAportante,
            Short digVerAportante, String codMunicipio, String codDepartamento, String direccion, String email, String telefono,
            Date fechaMatricula, NaturalezaJuridicaEnum naturalezaJuridica, String codSucursal, String nomSucursal) {
        super();
        this.tipoIdentificacion = tipoIdentificacion;
        this.idAportante = idAportante;
        this.nombreAportante = nombreAportante;
        this.digVerAportante = digVerAportante;
        this.codMunicipio = codMunicipio;
        this.codDepartamento = codDepartamento;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaMatricula = fechaMatricula;
        this.naturalezaJuridica = naturalezaJuridica;
        this.codSucursal = codSucursal;
        this.nomSucursal = nomSucursal;
    }
    
    
    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    /**
     * @return the idAportante
     */
    public String getIdAportante() {
        return idAportante;
    }
    /**
     * @param idAportante the idAportante to set
     */
    public void setIdAportante(String idAportante) {
        this.idAportante = idAportante;
    }
    /**
     * @return the nombreAportante
     */
    public String getNombreAportante() {
        return nombreAportante;
    }
    /**
     * @param nombreAportante the nombreAportante to set
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }
    /**
     * @return the digVerAportante
     */
    public Short getDigVerAportante() {
        return digVerAportante;
    }
    /**
     * @param digVerAportante the digVerAportante to set
     */
    public void setDigVerAportante(Short digVerAportante) {
        this.digVerAportante = digVerAportante;
    }
    /**
     * @return the codMunicipio
     */
    public String getCodMunicipio() {
        return codMunicipio;
    }
    /**
     * @param codMunicipio the codMunicipio to set
     */
    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
    }
    /**
     * @return the codDepartamento
     */
    public String getCodDepartamento() {
        return codDepartamento;
    }
    /**
     * @param codDepartamento the codDepartamento to set
     */
    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }
    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }
    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }
    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    /**
     * @return the fechaMatricula
     */
    public Date getFechaMatricula() {
        return fechaMatricula;
    }
    /**
     * @param fechaMatricula the fechaMatricula to set
     */
    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
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
    /**
     * @return the codSucursal
     */
    public String getCodSucursal() {
        return codSucursal;
    }
    /**
     * @param codSucursal the codSucursal to set
     */
    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }
    /**
     * @return the nomSucursal
     */
    public String getNomSucursal() {
        return nomSucursal;
    }
    /**
     * @param nomSucursal the nomSucursal to set
     */
    public void setNomSucursal(String nomSucursal) {
        this.nomSucursal = nomSucursal;
    }
}
