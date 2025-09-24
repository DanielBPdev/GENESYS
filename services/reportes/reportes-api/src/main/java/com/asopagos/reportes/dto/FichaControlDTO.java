package com.asopagos.reportes.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.asopagos.entidades.ccf.core.DatosFichaControl;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;

/**
 * <b>Descripcion:</b> Clase DTO que muestra la información del resultado del reporte que se va a generar<br/>
 * <b>Módulo:</b> Asopagos - HU - <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class FichaControlDTO implements Serializable {

    private static final long serialVersionUID = 812181297523655311L;

    /**
     * 
     */
    private String codigoAdministradora;

    /**
     * 
     */
    private String nombreAdministrador;
    
    /**
     * 
     */
    private String nombreResponsableEnvio;
    
    /**
     *
     */
    private String cargoResponsableEnvio;
    
    /**
     * 
     */
    private String emailResponsable_1;
    
    /**
     * 
     */
    private String emailResponsable_2;
    
    /**
     * 
     */
    private String indicativo;
    
    /**
     * 
     */
    private String telefono;
    
    /**
     *
     */
    private String nombreArchivo;
    
    /**
     * 
     */
    private Integer cantidadRegistros;
    
    /**
     * 
     */
    private String valorCarteraReportada;
    
    /**
     * 
     */
    private Long fechaCorteReporte;
    
    /**
     * 
     */
    private Long fechaPublicacionReporte;
    
    /**
     * 
     */
    private String metodologiaCalculoDeudaPresunta;
    
    public FichaControlDTO(){    	
    }
    
    public FichaControlDTO(DatosFichaControl datosFichaControl){
    	setNombreResponsableEnvio(datosFichaControl.getNombreResponsableEnvio());   	
        setCargoResponsableEnvio(datosFichaControl.getCargoResponsableEnvio());      
        setEmailResponsable_1(datosFichaControl.getCorreoElectronicoUno());      
        setEmailResponsable_2(datosFichaControl.getCorreoElectronicoDos());      
        setTelefono(datosFichaControl.getTelefonoResponsable());      
        setIndicativo(datosFichaControl.getIndicativoResponsable());        
    }
    	

	public String getCodigoAdministradora() {
		return codigoAdministradora;
	}

	public void setCodigoAdministradora(String codigoAdministradora) {
		this.codigoAdministradora = codigoAdministradora;
	}

	public String getNombreAdministrador() {
		return nombreAdministrador;
	}

	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}

	public String getNombreResponsableEnvio() {
		return nombreResponsableEnvio;
	}

	public void setNombreResponsableEnvio(String nombreResponsableEnvio) {
		this.nombreResponsableEnvio = nombreResponsableEnvio;
	}

	public String getCargoResponsableEnvio() {
		return cargoResponsableEnvio;
	}

	public void setCargoResponsableEnvio(String cargoResponsableEnvio) {
		this.cargoResponsableEnvio = cargoResponsableEnvio;
	}

	public String getEmailResponsable_1() {
		return emailResponsable_1;
	}

	public void setEmailResponsable_1(String emailResponsable_1) {
		this.emailResponsable_1 = emailResponsable_1;
	}

	public String getEmailResponsable_2() {
		return emailResponsable_2;
	}

	public void setEmailResponsable_2(String emailResponsable_2) {
		this.emailResponsable_2 = emailResponsable_2;
	}

	public String getIndicativo() {
		return indicativo;
	}

	public void setIndicativo(String indicativo) {
		this.indicativo = indicativo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Integer getCantidadRegistros() {
		return cantidadRegistros;
	}

	public void setCantidadRegistros(Integer cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}

	public String getValorCarteraReportada() {
		return valorCarteraReportada;
	}

	public void setValorCarteraReportada(String valorCarteraReportada) {
		this.valorCarteraReportada = valorCarteraReportada;
	}

	public Long getFechaCorteReporte() {
		return fechaCorteReporte;
	}

	public void setFechaCorteReporte(Long fechaCorteReporte) {
		this.fechaCorteReporte = fechaCorteReporte;
	}

	public Long getFechaPublicacionReporte() {
		return fechaPublicacionReporte;
	}

	public void setFechaPublicacionReporte(Long fechaPublicacionReporte) {
		this.fechaPublicacionReporte = fechaPublicacionReporte;
	}

	public String getMetodologiaCalculoDeudaPresunta() {
		return metodologiaCalculoDeudaPresunta;
	}

	public void setMetodologiaCalculoDeudaPresunta(String metodologiaCalculoDeudaPresunta) {
		this.metodologiaCalculoDeudaPresunta = metodologiaCalculoDeudaPresunta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

   

}
