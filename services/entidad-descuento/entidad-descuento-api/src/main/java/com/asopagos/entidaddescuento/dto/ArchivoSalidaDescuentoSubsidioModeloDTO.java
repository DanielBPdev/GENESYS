package com.asopagos.entidaddescuento.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoSalidaDescuentoSubsidio;

/**
 * <b>Descripción:</b> DTO que contiene los atributos de trazabilidad para un archivo de descuento
 * <br>
 * HU 311-432<br>
 * 
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@XmlRootElement
public class ArchivoSalidaDescuentoSubsidioModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador 
     */
    private Long idArchivoSalidaDescuentoSubsidio;

    /**
     * Identificador de solicitud liquidación subsidio
     */
    private Long idSolicitudLiquidacionSubsidio;
    
    /**
     * Identificador de la Entidad de Descuento
     */
    private Long idEntidadDescuento;
    
    /**
     * Nombre de salida del archivo
     */
    private String nombreOUT;
    
    /**
     * Codigo identificación del archivo ECM SAlida
     */
    private String codigoIdentificacionECMSalida;
    
    /**
     * Fecha de Generación del Archivo.
     */
    private Date fechaGeneracion;
  
    /**
     * Metodo que convierte una Entidad a un DTO.
     * @param archivoDescuento
     *        entidad con la información del archivo de descuento
     */
    public void convertToDTO(ArchivoSalidaDescuentoSubsidio archivoDescuento) {
        this.setIdArchivoSalidaDescuentoSubsidio(archivoDescuento.getIdArchivoSalidaDescuentoSubsidio());
        this.setIdSolicitudLiquidacionSubsidio(archivoDescuento.getIdSolicitudLiquidacionSubsidio());
        this.setIdEntidadDescuento(archivoDescuento.getIdEntidadDescuento());
        this.setNombreOUT(archivoDescuento.getNombreOUT());
        this.setCodigoIdentificacionECMSalida(archivoDescuento.getCodigoIdentificacionECMSalida());
        this.setFechaGeneracion(archivoDescuento.getFechaGeneracion());
    }

    /**
     * 
     * Metodo que convierte el DTO a la entidad
     * 
     * @return entidad correspondiente a la información de trazabilidad para el archivo de descuento
     */
    public ArchivoSalidaDescuentoSubsidio convertToEntity() {

    	ArchivoSalidaDescuentoSubsidio infoArchivo = new ArchivoSalidaDescuentoSubsidio();

        infoArchivo.setIdArchivoSalidaDescuentoSubsidio(this.getIdArchivoSalidaDescuentoSubsidio());
        infoArchivo.setIdSolicitudLiquidacionSubsidio(this.getIdSolicitudLiquidacionSubsidio());
        infoArchivo.setIdEntidadDescuento(this.getIdEntidadDescuento());
        infoArchivo.setNombreOUT(this.getNombreOUT());
        infoArchivo.setCodigoIdentificacionECMSalida(this.getCodigoIdentificacionECMSalida());
        infoArchivo.setFechaGeneracion(this.getFechaGeneracion());

        return infoArchivo;
    }

	/**
	 * @return the idArchivoSalidaDescuentoSubsidio
	 */
	public Long getIdArchivoSalidaDescuentoSubsidio() {
		return idArchivoSalidaDescuentoSubsidio;
	}

	/**
	 * @param idArchivoSalidaDescuentoSubsidio the idArchivoSalidaDescuentoSubsidio to set
	 */
	public void setIdArchivoSalidaDescuentoSubsidio(Long idArchivoSalidaDescuentoSubsidio) {
		this.idArchivoSalidaDescuentoSubsidio = idArchivoSalidaDescuentoSubsidio;
	}

	/**
	 * @return the idSolicitudLiquidacionSubsidio
	 */
	public Long getIdSolicitudLiquidacionSubsidio() {
		return idSolicitudLiquidacionSubsidio;
	}

	/**
	 * @param idSolicitudLiquidacionSubsidio the idSolicitudLiquidacionSubsidio to set
	 */
	public void setIdSolicitudLiquidacionSubsidio(Long idSolicitudLiquidacionSubsidio) {
		this.idSolicitudLiquidacionSubsidio = idSolicitudLiquidacionSubsidio;
	}

	/**
	 * @return the idEntidadDescuento
	 */
	public Long getIdEntidadDescuento() {
		return idEntidadDescuento;
	}

	/**
	 * @param idEntidadDescuento the idEntidadDescuento to set
	 */
	public void setIdEntidadDescuento(Long idEntidadDescuento) {
		this.idEntidadDescuento = idEntidadDescuento;
	}

	/**
	 * @return the nombreOUT
	 */
	public String getNombreOUT() {
		return nombreOUT;
	}

	/**
	 * @param nombreOUT the nombreOUT to set
	 */
	public void setNombreOUT(String nombreOUT) {
		this.nombreOUT = nombreOUT;
	}

	/**
	 * @return the codigoIdentificacionECMSalida
	 */
	public String getCodigoIdentificacionECMSalida() {
		return codigoIdentificacionECMSalida;
	}

	/**
	 * @param codigoIdentificacionECMSalida the codigoIdentificacionECMSalida to set
	 */
	public void setCodigoIdentificacionECMSalida(String codigoIdentificacionECMSalida) {
		this.codigoIdentificacionECMSalida = codigoIdentificacionECMSalida;
	}

	/**
	 * @return the fechaGeneracion
	 */
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	/**
	 * @param fechaGeneracion the fechaGeneracion to set
	 */
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

    
}
