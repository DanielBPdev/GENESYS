package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.cartera.CarteraNovedad;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción: </b> Clase que representa los datos de una novedad
 * relacionada a un cotizante en cartera <br/>
 * <b>Historia de Usuario: </b> HU-239
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class CarteraNovedadModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7952933231618369151L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idCarteraNovedad;

	/**
	 * Tipo de novedad
	 */
	private TipoTransaccionEnum tipoNovedad;

	/**
	 * Indica si la novedad fue seleccionada
	 */
	private Boolean condicion;

	/**
	 * Fecha de inicio de la novedad
	 */
	private Long fechaInicio;

	/**
	 * Fecha fin de la novedad
	 */
	private Long fechaFin;

	/**
	 * Atributo que indica si se debería aplicar la novedad
	 */
	private Boolean aplicar;

	/**
	 * Atributo que indica si se aplica a procesos posteriores.
	 */
	private Boolean novedadFutura;

	/**
	 * Identificador de la persona a quien se aplica la novedad. Referencia a la
	 * tabla <code>Persona</code>
	 */
	private Long idPersona;

	/**
	 * Fecha de creación del registro
	 */
	private Long fechaCreacion;

	/**
	 * Método que convierte en DTO en un entidad <code>CarteraNovedad</code>
	 * 
	 * @return La entidad equivalente
	 */
	public CarteraNovedad convertToEntity() {
		CarteraNovedad carteraNovedad = new CarteraNovedad();
		carteraNovedad.setAplicar(this.getAplicar() != null ? this.getAplicar() : Boolean.FALSE);
		carteraNovedad.setCondicion(this.getCondicion() != null ? this.getCondicion() : Boolean.FALSE);
		carteraNovedad.setFechaFin(this.getFechaFin() != null ? new Date(this.getFechaFin()) : null);
		carteraNovedad.setFechaInicio(this.getFechaInicio() != null ? new Date(this.getFechaInicio()) : new Date());
		carteraNovedad.setIdCarteraNovedad(this.getIdCarteraNovedad());
		carteraNovedad.setNovedadFutura(this.getNovedadFutura() ? this.getNovedadFutura() : Boolean.FALSE);
		carteraNovedad.setTipoNovedad(this.getTipoNovedad());
		carteraNovedad.setIdPersona(this.getIdPersona());
		carteraNovedad.setFechaCreacion(this.getFechaCreacion() != null ? new Date(this.getFechaCreacion()) : new Date());
		return carteraNovedad;
	}

	/**
	 * Obtiene el valor de tipoNovedad
	 * 
	 * @return El valor de tipoNovedad
	 */
	public TipoTransaccionEnum getTipoNovedad() {
		return tipoNovedad;
	}

	/**
	 * Establece el valor de tipoNovedad
	 * 
	 * @param tipoNovedad
	 *            El valor de tipoNovedad por asignar
	 */
	public void setTipoNovedad(TipoTransaccionEnum tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
	}

	/**
	 * Obtiene el valor de condicion
	 * 
	 * @return El valor de condicion
	 */
	public Boolean getCondicion() {
		return condicion;
	}

	/**
	 * Establece el valor de condicion
	 * 
	 * @param condicion
	 *            El valor de condicion por asignar
	 */
	public void setCondicion(Boolean condicion) {
		this.condicion = condicion;
	}

	/**
	 * Obtiene el valor de fechaInicio
	 * 
	 * @return El valor de fechaInicio
	 */
	public Long getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Establece el valor de fechaInicio
	 * 
	 * @param fechaInicio
	 *            El valor de fechaInicio por asignar
	 */
	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Obtiene el valor de fechaFin
	 * 
	 * @return El valor de fechaFin
	 */
	public Long getFechaFin() {
		return fechaFin;
	}

	/**
	 * Establece el valor de fechaFin
	 * 
	 * @param fechaFin
	 *            El valor de fechaFin por asignar
	 */
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Obtiene el valor de aplicar
	 * 
	 * @return El valor de aplicar
	 */
	public Boolean getAplicar() {
		return aplicar;
	}

	/**
	 * Establece el valor de aplicar
	 * 
	 * @param aplicar
	 *            El valor de aplicar por asignar
	 */
	public void setAplicar(Boolean aplicar) {
		this.aplicar = aplicar;
	}

	/**
	 * Obtiene el valor de novedadFutura
	 * 
	 * @return El valor de novedadFutura
	 */
	public Boolean getNovedadFutura() {
		return novedadFutura;
	}

	/**
	 * Establece el valor de novedadFutura
	 * 
	 * @param novedadFutura
	 *            El valor de novedadFutura por asignar
	 */
	public void setNovedadFutura(Boolean novedadFutura) {
		this.novedadFutura = novedadFutura;
	}

	/**
	 * Obtiene el valor de idCarteraNovedad
	 * 
	 * @return El valor de idCarteraNovedad
	 */
	public Long getIdCarteraNovedad() {
		return idCarteraNovedad;
	}

	/**
	 * Establece el valor de idCarteraNovedad
	 * 
	 * @param idCarteraNovedad
	 *            El valor de idCarteraNovedad por asignar
	 */
	public void setIdCarteraNovedad(Long idCarteraNovedad) {
		this.idCarteraNovedad = idCarteraNovedad;
	}

	/**
	 * Obtiene el valor de idPersona
	 * 
	 * @return El valor de idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Establece el valor de idPersona
	 * 
	 * @param idPersona
	 *            El valor de idPersona por asignar
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Obtiene el valor de fechaCreacion
	 * 
	 * @return El valor de fechaCreacion
	 */
	public Long getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece el valor de fechaCreacion
	 * 
	 * @param fechaCreacion
	 *            El valor de fechaCreacion por asignar
	 */
	public void setFechaCreacion(Long fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
