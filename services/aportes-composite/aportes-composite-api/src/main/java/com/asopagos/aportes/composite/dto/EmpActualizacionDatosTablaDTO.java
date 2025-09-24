/**
 * 
 */
package com.asopagos.aportes.composite.dto;

import java.util.Date;

/**
 * @author anbuitrago
 *
 */
public class EmpActualizacionDatosTablaDTO {

	private Long numeroRegistro;
	private String tipoIdentificacion;
	private Long numIdentificacion;
	private String nombreEmpresa;
	private String tipoInconsistencia;
	private String canalContacto;
	private Date fechaIngresoBandeja;
	private String estadoGestion;
	private String observaciones;
	
	
	
	
	/**
	 * @return the numeroRegistro
	 */
	public Long getNumeroRegistro() {
		return numeroRegistro;
	}
	/**
	 * @param numeroRegistro the numeroRegistro to set
	 */
	public void setNumeroRegistro(Long numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	/**
	 * @return the tipoIdentificacion
	 */
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	/**
	 * @return the numIdentificacion
	 */
	public Long getNumIdentificacion() {
		return numIdentificacion;
	}
	/**
	 * @param numIdentificacion the numIdentificacion to set
	 */
	public void setNumIdentificacion(Long numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}
	/**
	 * @return the nombreEmpresa
	 */
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	/**
	 * @param nombreEmpresa the nombreEmpresa to set
	 */
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	/**
	 * @return the tipoInconsistencia
	 */
	public String getTipoInconsistencia() {
		return tipoInconsistencia;
	}
	/**
	 * @param tipoInconsistencia the tipoInconsistencia to set
	 */
	public void setTipoInconsistencia(String tipoInconsistencia) {
		this.tipoInconsistencia = tipoInconsistencia;
	}
	/**
	 * @return the canalContacto
	 */
	public String getCanalContacto() {
		return canalContacto;
	}
	/**
	 * @param canalContacto the canalContacto to set
	 */
	public void setCanalContacto(String canalContacto) {
		this.canalContacto = canalContacto;
	}
	/**
	 * @return the fechaIngresoBandeja
	 */
	public Date getFechaIngresoBandeja() {
		return fechaIngresoBandeja;
	}
	/**
	 * @param fechaIngresoBandeja the fechaIngresoBandeja to set
	 */
	public void setFechaIngresoBandeja(Date fechaIngresoBandeja) {
		this.fechaIngresoBandeja = fechaIngresoBandeja;
	}
	/**
	 * @return the estadoGestion
	 */
	public String getEstadoGestion() {
		return estadoGestion;
	}
	/**
	 * @param estadoGestion the estadoGestion to set
	 */
	public void setEstadoGestion(String estadoGestion) {
		this.estadoGestion = estadoGestion;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @param numeroRegistro
	 * @param tipoIdentificacion
	 * @param numIdentificacion
	 * @param nombreEmpresa
	 * @param tipoInconsistencia
	 * @param canalContacto
	 * @param fechaIngresoBandeja
	 * @param estadoGestion
	 * @param observaciones
	 */
	public EmpActualizacionDatosTablaDTO(Long numeroRegistro, String tipoIdentificacion, Long numIdentificacion,
			String nombreEmpresa, String tipoInconsistencia, String canalContacto, Date fechaIngresoBandeja,
			String estadoGestion, String observaciones) {
		this.numeroRegistro = numeroRegistro;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numIdentificacion = numIdentificacion;
		this.nombreEmpresa = nombreEmpresa;
		this.tipoInconsistencia = tipoInconsistencia;
		this.canalContacto = canalContacto;
		this.fechaIngresoBandeja = fechaIngresoBandeja;
		this.estadoGestion = estadoGestion;
		this.observaciones = observaciones;
	}

	public EmpActualizacionDatosTablaDTO(){
		
	}
	 
	
}
