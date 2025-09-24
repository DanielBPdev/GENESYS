/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;

/**
 * @author squintero
 *
 */
public class ConfirmacionProcesoArchivoZenithDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreArchivo;
	private String resultado;
	private String observacion;
	private String fechaTransacciones;
	
	/**
	 * 
	 */
	public ConfirmacionProcesoArchivoZenithDTO() {
	}
	
	/**
	 * @param nombreArchivo
	 * @param resultado
	 * @param observacion
	 * @param fechaTransacciones
	 */
	public ConfirmacionProcesoArchivoZenithDTO(String nombreArchivo, String resultado, String observacion, String fechaTransacciones) {
		this.nombreArchivo = nombreArchivo;
		this.resultado = resultado;
		this.observacion = observacion;
		this.fechaTransacciones = fechaTransacciones;
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

	/**
	 * @return the resultadoProceso
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @param resultado the resultadoProceso to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the resultadoProceso to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the fecha
	 */
	public String getFechaTransacciones() {
		return fechaTransacciones;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFechaTransacciones(String fechaTransacciones) {
		this.fechaTransacciones = fechaTransacciones;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nombreArchivo);
		builder.append(",");
		builder.append(resultado);
		builder.append(",");
		builder.append(fechaTransacciones);
		return builder.toString();
	}
	
	
}
