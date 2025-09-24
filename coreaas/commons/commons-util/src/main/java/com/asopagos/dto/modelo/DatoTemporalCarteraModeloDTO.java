package com.asopagos.dto.modelo;

import java.io.Serializable;

import com.asopagos.entidades.ccf.cartera.DatoTemporalCartera;

/**
 * <b>Descripción: </b> Clase que representa el mapeo de atributos para la
 * entidad <code>DatoTemporalCartera</code> <br/>
 * <b>Historia de Usuario: </b> HU-234
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class DatoTemporalCarteraModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 156565611000L;

	/**
	 * Código identificador, llave primaria
	 */
	private Long idDatoTemporalCartera;

	/**
	 * Número de operación
	 */
	private Long numeroOperacion;

	/**
	 * Datos temporales
	 */
	private String jsonPayload;

	/**
	 * Método que convierte una entidad <code>DatoTemporalCartera</code> en el
	 * DTO equivalente
	 * 
	 * @param datoTemporal
	 *            Datos temporales
	 */
	public void convertToDTO(DatoTemporalCartera datoTemporal) {
		this.numeroOperacion = datoTemporal.getNumeroOperacion();
		this.jsonPayload = datoTemporal.getJsonPayload();
		this.idDatoTemporalCartera = datoTemporal.getIdDatoTemporalCartera();
	}

	/**
	 * Método que convierte el DTO en la entidad
	 * <code>DatoTemporalCartera</code> equivalente
	 * 
	 * @return La entidad equivalente
	 */
	public DatoTemporalCartera convertToEntity() {
		DatoTemporalCartera datoTemporal = new DatoTemporalCartera();
		datoTemporal.setNumeroOperacion(this.numeroOperacion);
		datoTemporal.setIdDatoTemporalCartera(this.idDatoTemporalCartera);
		datoTemporal.setJsonPayload(this.jsonPayload);
		return datoTemporal;
	}

	/**
	 * Obtiene el valor de idDatoTemporalCartera
	 * 
	 * @return El valor de idDatoTemporalCartera
	 */
	public Long getIdDatoTemporalCartera() {
		return idDatoTemporalCartera;
	}

	/**
	 * Establece el valor de idDatoTemporalCartera
	 * 
	 * @param idDatoTemporalCartera
	 *            El valor de idDatoTemporalCartera por asignar
	 */
	public void setIdDatoTemporalCartera(Long idDatoTemporalCartera) {
		this.idDatoTemporalCartera = idDatoTemporalCartera;
	}

	/**
	 * Obtiene el valor de jsonPayload
	 * 
	 * @return El valor de jsonPayload
	 */
	public String getJsonPayload() {
		return jsonPayload;
	}

	/**
	 * Establece el valor de jsonPayload
	 * 
	 * @param jsonPayload
	 *            El valor de jsonPayload por asignar
	 */
	public void setJsonPayload(String jsonPayload) {
		this.jsonPayload = jsonPayload;
	}

	/**
	 * Obtiene el valor de numeroOperacion
	 * 
	 * @return El valor de numeroOperacion
	 */
	public Long getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * Establece el valor de numeroOperacion
	 * 
	 * @param numeroOperacion
	 *            El valor de numeroOperacion por asignar
	 */
	public void setNumeroOperacion(Long numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}
}
