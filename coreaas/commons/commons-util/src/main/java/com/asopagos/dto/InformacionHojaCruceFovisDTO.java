/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que transporta los datos de cruce de Fovis.
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class InformacionHojaCruceFovisDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6206204857588165223L;

    /**
     * 
     */
    private String nroCedula;

    /**
     * 
     */
    private String nitEntidad;

    /**
     * 
     */
    private String nombreEntidad;

    /**
     * 
     */
    private String identificacion;

    /**
     * 
     */
    private String apellidos;

    /**
     * 
     */
    private String nombres;

    /**
     * 
     */
    private String cedulaCatastral;

    /**
     * 
     */
    private String direccionInmueble;

    /**
     *
     */
    private String direccion;

    /**
     * 
     */
    private String matriculaInmobiliaria;

    /**
     * 
     */
    private String departamento;

    /**
     * 
     */
    private String municipio;

    /**
     * 
     */
    private String apellidosNombres;

    /**
     * 
     */
    private Date fechaSolicitud;

    /**
     * 
     */
    private String entidadOtorgante;

    /**
     * 
     */
    private String cajaCompensacion;

    /**
     * 
     */
    private String asignadoPosteriorReporte;

    /**
     * 
     */
    private String tipoInformacion;

    /**
     * 
     */
    private Date fechaCorte;

    /**
     * 
     */
    private Date fechaActualizacion;

    /**
     * 
     */
    private String puntaje;

    /**
     * 
     */
    private String sexo;

    /**
     * 
     */
    private String zona;

    /**
     * 
     */
    private String parentesco;

    /**
     * 
     */
    private String folio;

    /**
     * 
     */
    private String documento;

    /**
     * 
     */
    private String tipoDocumento;

    /**
     * Cod tipo documento
     */
    private String codTipoDocumento;

    /**
     * Fecha asignacion
     */
    private Date fechaAsignacion;

    /**
     * Valor asignado
     */
    private String valorAsignado;

    /**
     * 
     */
    public InformacionHojaCruceFovisDTO() {
        super();
    }

    /**
     * @return the nroCedula
     */
    public String getNroCedula() {
        return nroCedula;
    }

    /**
     * @param nroCedula
     *        the nroCedula to set
     */
    public void setNroCedula(String nroCedula) {
        this.nroCedula = nroCedula;
    }

    /**
     * @return the nitEntidad
     */
    public String getNitEntidad() {
        return nitEntidad;
    }

    /**
     * @param nitEntidad
     *        the nitEntidad to set
     */
    public void setNitEntidad(String nitEntidad) {
        this.nitEntidad = nitEntidad;
    }

    /**
     * @return the nombreEntidad
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    /**
     * @param nombreEntidad
     *        the nombreEntidad to set
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion
     *        the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos
     *        the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres
     *        the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the cedulaCatastral
     */
    public String getCedulaCatastral() {
        return cedulaCatastral;
    }

    /**
     * @param cedulaCatastral
     *        the cedulaCatastral to set
     */
    public void setCedulaCatastral(String cedulaCatastral) {
        this.cedulaCatastral = cedulaCatastral;
    }

    /**
     * @return the direccionInmueble
     */
    public String getDireccionInmueble() {
        return direccionInmueble;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion
     *        the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param direccionInmueble
     *        the direccionInmueble to set
     */
    public void setDireccionInmueble(String direccionInmueble) {
        this.direccionInmueble = direccionInmueble;
    }

    /**
     * @return the matriculaInmobiliaria
     */
    public String getMatriculaInmobiliaria() {
        return matriculaInmobiliaria;
    }

    /**
     * @param matriculaInmobiliaria
     *        the matriculaInmobiliaria to set
     */
    public void setMatriculaInmobiliaria(String matriculaInmobiliaria) {
        this.matriculaInmobiliaria = matriculaInmobiliaria;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento
     *        the departamento to set
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
     * @param municipio
     *        the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     * @param apellidosNombres
     *        the apellidosNombres to set
     */
    public void setApellidosNombres(String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     * @return the fechaSolicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud
     *        the fechaSolicitud to set
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the entidadOtorgante
     */
    public String getEntidadOtorgante() {
        return entidadOtorgante;
    }

    /**
     * @param entidadOtorgante
     *        the entidadOtorgante to set
     */
    public void setEntidadOtorgante(String entidadOtorgante) {
        this.entidadOtorgante = entidadOtorgante;
    }

    /**
     * @return the cajaCompensacion
     */
    public String getCajaCompensacion() {
        return cajaCompensacion;
    }

    /**
     * @param cajaCompensacion
     *        the cajaCompensacion to set
     */
    public void setCajaCompensacion(String cajaCompensacion) {
        this.cajaCompensacion = cajaCompensacion;
    }

    /**
     * @return the asignadoPosteriorReporte
     */
    public String getAsignadoPosteriorReporte() {
        return asignadoPosteriorReporte;
    }

    /**
     * @param asignadoPosteriorReporte
     *        the asignadoPosteriorReporte to set
     */
    public void setAsignadoPosteriorReporte(String asignadoPosteriorReporte) {
        this.asignadoPosteriorReporte = asignadoPosteriorReporte;
    }

    /**
     * @return the tipoInformacion
     */
    public String getTipoInformacion() {
        return tipoInformacion;
    }

    /**
     * @param tipoInformacion
     *        the tipoInformacion to set
     */
    public void setTipoInformacion(String tipoInformacion) {
        this.tipoInformacion = tipoInformacion;
    }

    /**
     * @return the fechaCorte
     */
    public Date getFechaCorte() {
        return fechaCorte;
    }

    /**
     * @param fechaCorte
     *        the fechaCorte to set
     */
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    /**
     * @return the fechaActualizacion
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion
     *        the fechaActualizacion to set
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the puntaje
     */
    public String getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje
     *        the puntaje to set
     */
    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo
     *        the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona
     *        the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * @return the parentesco
     */
    public String getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *        the parentesco to set
     */
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * @return the folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio
     *        the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento
     *        the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento
     *        the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the codTipoDocumento
     */
    public String getCodTipoDocumento() {
        return codTipoDocumento;
    }

    /**
     * @param codTipoDocumento
     */
    public void setCodTipoDocumento(String codTipoDocumento) {
        this.codTipoDocumento = codTipoDocumento;
    }

    /**
     * @return the fechaAsignacion
     */
    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * @param fechaAsignacion
     */
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    /**
     * @return the valorAsignado
     */
    public String getValorAsignado() {
        return valorAsignado;
    }

    /**
     * @param valorAsignado
     */
    public void setValorAsignado(String valorAsignado) {
        this.valorAsignado = valorAsignado;
    }
}