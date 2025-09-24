package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class RepresentanteLegalOutDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Tipo de identificación del representante legal
    */
    private TipoIdentificacionEnum tipoID;

    /**
    * Número de identificación del representante legal
    */
    private String identificacion;

    /**
     * corresponde al dígito de verificación de la identificación de la empresa
     */
    private Short digitoVerificacion;
    
    /**
    * Nombre completo del representante legal
    */
    private String nombreCompleto;

    /**
    * Código DANE del departamento de ubicación del representante legal
    */
    private String departamentoCodigo;
    
    /**
     * Nombre del Departamento de ubicación del representante legal
     */
    private String departamento;

    /**
    * Código Dane del municipio de ubicación del representante legal
    */
    private String municipioCodigo;
    
    /**
     * Nombre del Municipio de ubicación del representante legal
     */
    private String municipio;

    /**
    * Dirección física principal del representante legal
    */
    private String direccionPrincipal;

    /**
    * Indicativo del teléfono fijo + número fijo del representante legal
    */
    private String telefonoFijo;

    /**
    * Número telefónico del celular del representante legal
    */
    private String celular;

    /**
    * Correo electrónico del representante legal
    */
    private String correoElectronico;

    /**
     * 
     */
    public RepresentanteLegalOutDTO() {
    }
    
    /**
     * @param tipoID
     * @param identificacion
     * @param digitoVerificacion
     * @param nombreCompleto
     * @param departamentoCodigo
     * @param departamento
     * @param municipioCodigo
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     * @param correoElectronico
     */
    public RepresentanteLegalOutDTO(TipoIdentificacionEnum tipoID, String identificacion, Short digitoVerificacion, String nombreCompleto,
            String departamentoCodigo, String departamento, String municipioCodigo, String municipio, String direccionPrincipal,
            String telefonoFijo, String celular, String correoElectronico) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.digitoVerificacion = digitoVerificacion;
        this.nombreCompleto = nombreCompleto;
        this.departamentoCodigo = departamentoCodigo;
        this.departamento = departamento;
        this.municipioCodigo = municipioCodigo;
        this.municipio = municipio;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
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
}
