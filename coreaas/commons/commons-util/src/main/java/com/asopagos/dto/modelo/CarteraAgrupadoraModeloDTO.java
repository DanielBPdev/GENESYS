package com.asopagos.dto.modelo;

import java.io.Serializable;

import com.asopagos.entidades.ccf.cartera.CarteraAgrupadora;

/**
 * <b>Descripción: </b> DTO que mapea los datos de la entidad CarteraModeloDTO
 * <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class CarteraAgrupadoraModeloDTO implements Serializable {
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 885904526280068108L;

	/**
	 * Llave primaria
	 */
	private Long idCarteraAgrupadora;

	/**
	 * Número de operación
	 */
	private Long numeroOperacion;

	/**
	 * idCartera
	 */
	private Long idCartera;

	/**
	 * Método constructor
	 */
	public CarteraAgrupadoraModeloDTO() {
	}

	/**
	 * Método constructor con la cartera y la persona.
	 */
	public CarteraAgrupadoraModeloDTO(CarteraAgrupadora carteraAgrupadora) {
		this.setIdCartera(carteraAgrupadora.getIdCartera());
		this.setIdCarteraAgrupadora(carteraAgrupadora.getIdCarteraAgrupadora());
		this.setNumeroOperacion(carteraAgrupadora.getNumeroOperacion());
	}

	/**
	 * Transofrma una entidad en el DTO
	 * 
	 * @param cartera
	 *            Entidad
	 */
	public void convertToDTO(CarteraAgrupadora carteraAgrupadora) {
		this.setIdCartera(carteraAgrupadora.getIdCartera());
		this.setIdCarteraAgrupadora(carteraAgrupadora.getIdCarteraAgrupadora());
		this.setNumeroOperacion(carteraAgrupadora.getNumeroOperacion());
	}

	/**
	 * Metodo que convierte de DTO a entidad
	 */
	public CarteraAgrupadora convertToEntity() {
		CarteraAgrupadora carteraAgrupadora = new CarteraAgrupadora();
		carteraAgrupadora.setIdCartera(this.idCartera);
		carteraAgrupadora.setIdCarteraAgrupadora(this.idCarteraAgrupadora);
		carteraAgrupadora.setNumeroOperacion(this.numeroOperacion);
		return carteraAgrupadora;
	}

	/**
	 * Obtiene el valor de idCarteraAgrupadora
	 * 
	 * @return El valor de idCarteraAgrupadora
	 */
	public Long getIdCarteraAgrupadora() {
		return idCarteraAgrupadora;
	}

	/**
	 * Establece el valor de idCarteraAgrupadora
	 * 
	 * @param idCarteraAgrupadora
	 *            El valor de idCarteraAgrupadora por asignar
	 */
	public void setIdCarteraAgrupadora(Long idCarteraAgrupadora) {
		this.idCarteraAgrupadora = idCarteraAgrupadora;
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

	/**
	 * Obtiene el valor de idCartera
	 * 
	 * @return El valor de idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/**
	 * Establece el valor de idCartera
	 * 
	 * @param idCartera
	 *            El valor de idCartera por asignar
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

}
