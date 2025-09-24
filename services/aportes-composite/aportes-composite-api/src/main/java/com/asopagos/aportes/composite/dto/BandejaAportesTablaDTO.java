/**
 * 
 */
package com.asopagos.aportes.composite.dto;

/**
 * @author anbuitrago
 *
 */
public class BandejaAportesTablaDTO {
	private String estadoInicial;
	private Long numeroPlanilla;
	private String tipoIdentificacionAportante;
	private Long numeroIdentificacionAportante;
	private String nombreAportante;
	private String tipoIdentificacionCotizante;
	private Long numeroIdentificacionCotizante;
	private String estadoAportante;
	private String estadoCotizante;
	/**
	 * @return the estadoInicial
	 */
	public String getEstadoInicial() {
		return estadoInicial;
	}
	/**
	 * @param estadoInicial the estadoInicial to set
	 */
	public void setEstadoInicial(String estadoInicial) {
		this.estadoInicial = estadoInicial;
	}
	/**
	 * @return the numeroPlanilla
	 */
	public Long getNumeroPlanilla() {
		return numeroPlanilla;
	}
	/**
	 * @param numeroPlanilla the numeroPlanilla to set
	 */
	public void setNumeroPlanilla(Long numeroPlanilla) {
		this.numeroPlanilla = numeroPlanilla;
	}
	/**
	 * @return the tipoIdentificacionAportante
	 */
	public String getTipoIdentificacionAportante() {
		return tipoIdentificacionAportante;
	}
	/**
	 * @param tipoIdentificacionAportante the tipoIdentificacionAportante to set
	 */
	public void setTipoIdentificacionAportante(String tipoIdentificacionAportante) {
		this.tipoIdentificacionAportante = tipoIdentificacionAportante;
	}
	/**
	 * @return the numeroIdentificacionAportante
	 */
	public Long getNumeroIdentificacionAportante() {
		return numeroIdentificacionAportante;
	}
	/**
	 * @param numeroIdentificacionAportante the numeroIdentificacionAportante to set
	 */
	public void setNumeroIdentificacionAportante(Long numeroIdentificacionAportante) {
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
	}
	/**
	 * @return the nombreAportante
	 */
	public String getNombreAportante() {
		return nombreAportante;
	}
	/**
	 * @param nombreAportante the nombreAportante to set
	 */
	public void setNombreAportante(String nombreAportante) {
		this.nombreAportante = nombreAportante;
	}
	/**
	 * @return the tipoIdentificacionCotizante
	 */
	public String getTipoIdentificacionCotizante() {
		return tipoIdentificacionCotizante;
	}
	/**
	 * @param tipoIdentificacionCotizante the tipoIdentificacionCotizante to set
	 */
	public void setTipoIdentificacionCotizante(String tipoIdentificacionCotizante) {
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
	}
	/**
	 * @return the numeroIdentificacionCotizante
	 */
	public Long getNumeroIdentificacionCotizante() {
		return numeroIdentificacionCotizante;
	}
	/**
	 * @param numeroIdentificacionCotizante the numeroIdentificacionCotizante to set
	 */
	public void setNumeroIdentificacionCotizante(Long numeroIdentificacionCotizante) {
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
	}
	/**
	 * @return the estadoAportante
	 */
	public String getEstadoAportante() {
		return estadoAportante;
	}
	/**
	 * @param estadoAportante the estadoAportante to set
	 */
	public void setEstadoAportante(String estadoAportante) {
		this.estadoAportante = estadoAportante;
	}
	/**
	 * @return the estadoCotizante
	 */
	public String getEstadoCotizante() {
		return estadoCotizante;
	}
	/**
	 * @param estadoCotizante the estadoCotizante to set
	 */
	public void setEstadoCotizante(String estadoCotizante) {
		this.estadoCotizante = estadoCotizante;
	}
	
	public BandejaAportesTablaDTO() {
		
	}
	/**
	 * @param estadoInicial
	 * @param numeroPlanilla
	 * @param tipoIdentificacionAportante
	 * @param numeroIdentificacionAportante
	 * @param nombreAportante
	 * @param tipoIdentificacionCotizante
	 * @param numeroIdentificacionCotizante
	 * @param estadoAportante
	 * @param estadoCotizante
	 */
	public BandejaAportesTablaDTO(String estadoInicial, Long numeroPlanilla, String tipoIdentificacionAportante,
			Long numeroIdentificacionAportante, String nombreAportante, String tipoIdentificacionCotizante,
			Long numeroIdentificacionCotizante, String estadoAportante, String estadoCotizante) {
		this.estadoInicial = estadoInicial;
		this.numeroPlanilla = numeroPlanilla;
		this.tipoIdentificacionAportante = tipoIdentificacionAportante;
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
		this.nombreAportante = nombreAportante;
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.estadoAportante = estadoAportante;
		this.estadoCotizante = estadoCotizante;
	}
	
	
}
