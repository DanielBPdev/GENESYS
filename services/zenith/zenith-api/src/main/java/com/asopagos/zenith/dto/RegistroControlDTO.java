package com.asopagos.zenith.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>Descripción:</b> DTO que mapea los datos de registro control para el archivo consultado por Zenith<br/>
 * <b>Módulo:</b> integración Zenith
 *
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class RegistroControlDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tipoRegistro;
	private String CodigoCajaCompensacion;
	private String fechaInicioCorte;
	private String fechaFinCorte;
	private Integer cantidadRegistrosArchivo;
	private String nombreArchivo;
	
	
	/**
	 * 
	 */
	public RegistroControlDTO() {
	}
	
	
	/**
	 * @param tipoRegistro
	 * @param codigoCajaCompensacion
	 * @param fechaInicioCorte
	 * @param fechaFinCorte
	 * @param cantidadRegistrosArchivo
	 * @param nombreArchivo
	 */
	public RegistroControlDTO(Integer tipoRegistro, String codigoCajaCompensacion, String fechaInicioCorte,
			String fechaFinCorte, Integer cantidadRegistrosArchivo, String nombreArchivo) {
		this.tipoRegistro = tipoRegistro;
		CodigoCajaCompensacion = codigoCajaCompensacion;
		this.fechaInicioCorte = fechaInicioCorte;
		this.fechaFinCorte = fechaFinCorte;
		this.cantidadRegistrosArchivo = cantidadRegistrosArchivo;
		this.nombreArchivo = nombreArchivo;
	}


	/**
	 * @return the tipoRegistro
	 */
	public Integer getTipoRegistro() {
		return tipoRegistro;
	}


	/**
	 * @param tipoRegistro the tipoRegistro to set
	 */
	public void setTipoRegistro(Integer tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}


	/**
	 * @return the codigoCajaCompensacion
	 */
	public String getCodigoCajaCompensacion() {
		return CodigoCajaCompensacion;
	}


	/**
	 * @param codigoCajaCompensacion the codigoCajaCompensacion to set
	 */
	public void setCodigoCajaCompensacion(String codigoCajaCompensacion) {
		CodigoCajaCompensacion = codigoCajaCompensacion;
	}


	/**
	 * @return the fechaInicioCorte
	 */
	public String getFechaInicioCorte() {
		return fechaInicioCorte;
	}


	/**
	 * @param fechaInicioCorte the fechaInicioCorte to set
	 */
	public void setFechaInicioCorte(String fechaInicioCorte) {
		this.fechaInicioCorte = fechaInicioCorte;
	}


	/**
	 * @return the fechaFinCorte
	 */
	public String getFechaFinCorte() {
		return fechaFinCorte;
	}


	/**
	 * @param fechaFinCorte the fechaFinCorte to set
	 */
	public void setFechaFinCorte(String fechaFinCorte) {
		this.fechaFinCorte = fechaFinCorte;
	}


	/**
	 * @return the cantidadRegistrosArchivo
	 */
	public Integer getCantidadRegistrosArchivo() {
		return cantidadRegistrosArchivo;
	}


	/**
	 * @param cantidadRegistrosArchivo the cantidadRegistrosArchivo to set
	 */
	public void setCantidadRegistrosArchivo(Integer cantidadRegistrosArchivo) {
		this.cantidadRegistrosArchivo = cantidadRegistrosArchivo;
	}


	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}


	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return tipoRegistro + "," + CodigoCajaCompensacion + "," + fechaInicioCorte + "," + fechaFinCorte + ","
				+ cantidadRegistrosArchivo + "," + nombreArchivo + "\r\n";
	}
	
	
	
	
}
