package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class SucursalEmpresaOutDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 8356761299074476569L;

    /**
    * Código de la sucursal del empleador
    */
    private String codigoSucursal;

    /**
    * Estado de la sucursal
    */
    private EstadoActivoInactivoEnum estadoSucursal;

    /**
    * Nombre de la sucursal del empleador
    */
    private String nombreSucursal;

    /**
    * Dirección física de la sucursal
    */
    private String direccionPrincipalSucursal;

    /**
    * Código DANE del departamento
    */
    private String codigoDepartamentoSucursal;

    /**
    * Nombre del departamento de la sucursal
    */
    private String nombreDepartamentoSucursal;

    /**
    * Código DANE del municipio
    */
    private String codigoMunicipioSucursal;

    /**
    * Nombre del municipio de la sucursal
    */
    private String nombreMunicipioSucursal;

    /**
    * Email de la sucursal
    */
    private String emailSucursal;

    /**
    * Código CIIU de la sucursal
    */
    private String codigoActividadSucursal;

    /**
    * Nombre de la actividad de la sucursal
    */
    private String nombreActividadSucursal;

    /**
    * Teléfono principal de la sucursal
    */
    private String telefonoPrincipalSucursal;

    /**
     * Último Período aporte Sucursal
     */
    private String ultimoPeriodoAportesSucursal;
    
    /**
     * 
     */
    public SucursalEmpresaOutDTO() {
    }



    /**
     * @param codigoSucursal
     * @param estadoSucursal
     * @param nombreSucursal
     * @param direccionPrincipalSucursal
     * @param codigoDepartamentoSucursal
     * @param nombreDepartamentoSucursal
     * @param codigoMunicipioSucursal
     * @param nombreMunicipioSucursal
     * @param emailSucursal
     * @param codigoActividadSucursal
     * @param nombreActividadSucursal
     * @param telefonoPrincipalSucursal
     */
    public SucursalEmpresaOutDTO(String codigoSucursal, EstadoActivoInactivoEnum estadoSucursal, String nombreSucursal,
            String direccionPrincipalSucursal, String codigoDepartamentoSucursal, String nombreDepartamentoSucursal,
            String codigoMunicipioSucursal, String nombreMunicipioSucursal, String emailSucursal, String codigoActividadSucursal,
            String nombreActividadSucursal, String telefonoPrincipalSucursal) {
        this.codigoSucursal = codigoSucursal;
        this.estadoSucursal = estadoSucursal;
        this.nombreSucursal = nombreSucursal;
        this.direccionPrincipalSucursal = direccionPrincipalSucursal;
        this.codigoDepartamentoSucursal = codigoDepartamentoSucursal;
        this.nombreDepartamentoSucursal = nombreDepartamentoSucursal;
        this.codigoMunicipioSucursal = codigoMunicipioSucursal;
        this.nombreMunicipioSucursal = nombreMunicipioSucursal;
        this.emailSucursal = emailSucursal;
        this.codigoActividadSucursal = codigoActividadSucursal;
        this.nombreActividadSucursal = nombreActividadSucursal;
        this.telefonoPrincipalSucursal = telefonoPrincipalSucursal;
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
     * @return the estadoSucursal
     */
    public EstadoActivoInactivoEnum getEstadoSucursal() {
        return estadoSucursal;
    }

    /**
     * @param estadoSucursal the estadoSucursal to set
     */
    public void setEstadoSucursal(EstadoActivoInactivoEnum estadoSucursal) {
        this.estadoSucursal = estadoSucursal;
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

    /**
     * @return the direccionPrincipalSucursal
     */
    public String getDireccionPrincipalSucursal() {
        return direccionPrincipalSucursal;
    }

    /**
     * @param direccionPrincipalSucursal the direccionPrincipalSucursal to set
     */
    public void setDireccionPrincipalSucursal(String direccionPrincipalSucursal) {
        this.direccionPrincipalSucursal = direccionPrincipalSucursal;
    }

    /**
     * @return the codigoDepartamentoSucursal
     */
    public String getCodigoDepartamentoSucursal() {
        return codigoDepartamentoSucursal;
    }

    /**
     * @param codigoDepartamentoSucursal the codigoDepartamentoSucursal to set
     */
    public void setCodigoDepartamentoSucursal(String codigoDepartamentoSucursal) {
        this.codigoDepartamentoSucursal = codigoDepartamentoSucursal;
    }

    /**
     * @return the nombreDepartamentoSucursal
     */
    public String getNombreDepartamentoSucursal() {
        return nombreDepartamentoSucursal;
    }

    /**
     * @param nombreDepartamentoSucursal the nombreDepartamentoSucursal to set
     */
    public void setNombreDepartamentoSucursal(String nombreDepartamentoSucursal) {
        this.nombreDepartamentoSucursal = nombreDepartamentoSucursal;
    }

    /**
     * @return the codigoMunicipioSucursal
     */
    public String getCodigoMunicipioSucursal() {
        return codigoMunicipioSucursal;
    }

    /**
     * @param codigoMunicipioSucursal the codigoMunicipioSucursal to set
     */
    public void setCodigoMunicipioSucursal(String codigoMunicipioSucursal) {
        this.codigoMunicipioSucursal = codigoMunicipioSucursal;
    }

    /**
     * @return the nombreMunicipioSucursal
     */
    public String getNombreMunicipioSucursal() {
        return nombreMunicipioSucursal;
    }

    /**
     * @param nombreMunicipioSucursal the nombreMunicipioSucursal to set
     */
    public void setNombreMunicipioSucursal(String nombreMunicipioSucursal) {
        this.nombreMunicipioSucursal = nombreMunicipioSucursal;
    }

    /**
     * @return the emailSucursal
     */
    public String getEmailSucursal() {
        return emailSucursal;
    }

    /**
     * @param emailSucursal the emailSucursal to set
     */
    public void setEmailSucursal(String emailSucursal) {
        this.emailSucursal = emailSucursal;
    }

    /**
     * @return the codigoActividadSucursal
     */
    public String getCodigoActividadSucursal() {
        return codigoActividadSucursal;
    }

    /**
     * @param codigoActividadSucursal the codigoActividadSucursal to set
     */
    public void setCodigoActividadSucursal(String codigoActividadSucursal) {
        this.codigoActividadSucursal = codigoActividadSucursal;
    }

    /**
     * @return the nombreActividadSucursal
     */
    public String getNombreActividadSucursal() {
        return nombreActividadSucursal;
    }

    /**
     * @param nombreActividadSucursal the nombreActividadSucursal to set
     */
    public void setNombreActividadSucursal(String nombreActividadSucursal) {
        this.nombreActividadSucursal = nombreActividadSucursal;
    }

    /**
     * @return the telefonoPrincipalSucursal
     */
    public String getTelefonoPrincipalSucursal() {
        return telefonoPrincipalSucursal;
    }

    /**
     * @param telefonoPrincipalSucursal the telefonoPrincipalSucursal to set
     */
    public void setTelefonoPrincipalSucursal(String telefonoPrincipalSucursal) {
        this.telefonoPrincipalSucursal = telefonoPrincipalSucursal;
    }



	/**
	 * @return the ultimoPeriodoAportesSucursal
	 */
	public String getUltimoPeriodoAportesSucursal() {
		return ultimoPeriodoAportesSucursal;
	}



	/**
	 * @param ultimoPeriodoAportesSucursal the ultimoPeriodoAportesSucursal to set
	 */
	public void setUltimoPeriodoAportesSucursal(String ultimoPeriodoAportesSucursal) {
		this.ultimoPeriodoAportesSucursal = ultimoPeriodoAportesSucursal;
	}
    
    
    
}
