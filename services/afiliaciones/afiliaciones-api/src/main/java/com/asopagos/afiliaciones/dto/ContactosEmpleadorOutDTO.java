package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.List;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class ContactosEmpleadorOutDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idEmpleador;
    
    /**
    * Tipo de identificación del empleador
    */
    private TipoIdentificacionEnum tipoID;

    /**
    * Número de identificación del empleador
    */
    private String identificacion;

    /**
    * Razón social del empleador
    */
    private String razonSocial;

    /**
    * Departamento de ubicación del empleador
    */
    private String departamento;

    /**
    * Municipio de ubicación del empleador
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
     * Datos de contacto del empleador
     */
    private List<List<DatosContactoEmpleadorOutDTO>> datosDeContacto;
    
    /**
     * @param idEmpleador
     * @param tipoID
     * @param identificacion
     * @param razonSocial
     * @param departamento
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     */
    public ContactosEmpleadorOutDTO(Long idEmpleador, String tipoID, String identificacion, String razonSocial, String departamento, String municipio,
            String direccionPrincipal, String telefonoFijo, String celular) {
        this.idEmpleador = idEmpleador;
        this.tipoID = tipoID != null ? TipoIdentificacionEnum.valueOf(tipoID):null;
        this.identificacion = identificacion;
        this.razonSocial = razonSocial;
        this.departamento = departamento;
        this.municipio = municipio;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
    }

    /**
     * 
     */
    public ContactosEmpleadorOutDTO() {
    }
    
    /**
     * @param idEmpleador
     * @param tipoID
     * @param identificacion
     * @param razonSocial
     * @param departamento
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     */
    public ContactosEmpleadorOutDTO(Long idEmpleador, TipoIdentificacionEnum tipoID, String identificacion, String razonSocial, String departamento, String municipio,
            String direccionPrincipal, String telefonoFijo, String celular) {
        this.idEmpleador = idEmpleador;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.razonSocial = razonSocial;
        this.departamento = departamento;
        this.municipio = municipio;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
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
     * @return the datosDeContacto
     */
    public List<List<DatosContactoEmpleadorOutDTO>> getDatosDeContacto() {
        return datosDeContacto;
    }

    /**
     * @param datosDeContacto the datosDeContacto to set
     */
    public void setDatosDeContacto(List<List<DatosContactoEmpleadorOutDTO>> datosDeContacto) {
        this.datosDeContacto = datosDeContacto;
    }
}
