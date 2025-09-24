/**
 * 
 */
package com.asopagos.pila.dto;

import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoParejaArchivosEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.ParejaArchivosEnum;

/**
 * DTO para la respuesta de aplicación de estado
 * 
 * @author abaquero
 */
public class RespuestaRegistroEstadoDTO {
	// para validación individual
	private EstadoProcesoArchivoEnum estado;
	private String causaPuntual;
	private AccionProcesoArchivoEnum accion;
	
	// para validación de pareja
	private EstadoParejaArchivosEnum estadoPareja;
	private ParejaArchivosEnum parejaArchivos;
	
	/**
	 * @return the estadoPareja
	 */
	public EstadoParejaArchivosEnum getEstadoPareja() {
		return estadoPareja;
	}
	/**
	 * @param estadoPareja the estadoPareja to set
	 */
	public void setEstadoPareja(EstadoParejaArchivosEnum estadoPareja) {
		this.estadoPareja = estadoPareja;
	}
	/**
	 * @return the parejaArchivos
	 */
	public ParejaArchivosEnum getParejaArchivos() {
		return parejaArchivos;
	}
	/**
	 * @param parejaArchivos the parejaArchivos to set
	 */
	public void setParejaArchivos(ParejaArchivosEnum parejaArchivos) {
		this.parejaArchivos = parejaArchivos;
	}
	/**
	 * @return the estado
	 */
	public EstadoProcesoArchivoEnum getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoProcesoArchivoEnum estado) {
		this.estado = estado;
	}
	/**
	 * @return the causaPuntual
	 */
	public String getCausaPuntual() {
		return causaPuntual;
	}
	/**
	 * @param causaPuntual the causaPuntual to set
	 */
	public void setCausaPuntual(String causaPuntual) {
		this.causaPuntual = causaPuntual;
	}
	/**
	 * @return the accion
	 */
	public AccionProcesoArchivoEnum getAccion() {
		return accion;
	}
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(AccionProcesoArchivoEnum accion) {
		this.accion = accion;
	}
	
	
}
