/**
 * 
 */
package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>Descripcion:</b> DTO que representa la entidad TemNovedad<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ExcepcionNovedadPilaModeloDTO implements Serializable {
	private static final long serialVersionUID = -6275562806773481489L;

	/**
	 * Código identificador de llave primaria.
	 */
	private Long id;

	/**
	 * ID de la transacción
	 */
	private Long idTempNovedad;

	/**
	 * Tipo de identificación del aportante
	 */
	private String excepcion;

	/**
	 * Número de ID del aportante
	 */
	private Date fecha;

	public ExcepcionNovedadPilaModeloDTO(Long id, Long idTempNovedad, String excepcion, Date fecha) {
		this.id = id;
		this.idTempNovedad = idTempNovedad;
		this.excepcion = excepcion;
		this.fecha = fecha;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idTempNovedad
	 */
	public Long getIdTempNovedad() {
		return idTempNovedad;
	}

	/**
	 * @param idTempNovedad the idTempNovedad to set
	 */
	public void setIdTempNovedad(Long idTempNovedad) {
		this.idTempNovedad = idTempNovedad;
	}

	/**
	 * @return the excepcion
	 */
	public String getExcepcion() {
		return excepcion;
	}

	/**
	 * @param excepcion the excepcion to set
	 */
	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
