package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.entidades.ccf.fovis.AhorroPrevio;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;

/**
 * <b>Descripción: </b> DTO que representa la información de un registro de la
 * tabla <code>AhorroPrevio</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AhorroPrevioModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7447478577870827910L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idAhorroPrevio;

	private TipoAhorroPrevioEnum nombreAhorro;

	private String entidad;

	/**
	 * Puede referirse a fechaApertura o fechaPrimerPago
	 */
	private Long fechaInicial;

	private BigDecimal valor;

	private Long fechaInmovilizacion;

	private Long fechaAdquisicion;

	/**
	 * Referencia a la tabla <code>PostulacionFOVIS</code>
	 */
	private Long idPostulacion;
	
	private Boolean ahorroMovilizado;
	
	/**
	 * Constructor por defecto
	 */
	public AhorroPrevioModeloDTO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor a partir de una entidad AhorroPrevio
	 * @param ahorroPrevio
	 */
	public AhorroPrevioModeloDTO(AhorroPrevio ahorroPrevio){
		
		this.setIdAhorroPrevio(ahorroPrevio.getIdAhorroPrevio());
		this.setEntidad(ahorroPrevio.getEntidad() != null ? ahorroPrevio.getEntidad() : null);
		this.setFechaAdquisicion(ahorroPrevio.getFechaAdquisicion() != null ? ahorroPrevio.getFechaAdquisicion().getTime() : null);
		this.setFechaInicial(ahorroPrevio.getFechaInicial() != null ? ahorroPrevio.getFechaInicial().getTime() : null);
		this.setFechaInmovilizacion(ahorroPrevio.getFechaInmovilizacion() != null ? ahorroPrevio.getFechaInmovilizacion().getTime() : null);
		this.setIdAhorroPrevio(ahorroPrevio.getIdAhorroPrevio() != null ? ahorroPrevio.getIdAhorroPrevio() : null);
		this.setNombreAhorro(ahorroPrevio.getNombreAhorro() != null ? ahorroPrevio.getNombreAhorro() : null);
		this.setValor(ahorroPrevio.getValor());
		//this.setAhorroMovilizado(ahorroPrevio.getAhorroMovilizado);
		
	}
	
	/**
	 * Método que convierte el DTO en entidad
	 * 
	 * @return La entidad <code>AhorroPrevio</code> equivalente
	 */
	public AhorroPrevio convertToEntity() {
		AhorroPrevio ahorroPrevio = new AhorroPrevio();
		ahorroPrevio.setEntidad(this.getEntidad());

		if (this.getFechaAdquisicion() != null) {
			ahorroPrevio.setFechaAdquisicion(new Date(this.getFechaAdquisicion()));
		}

		if (this.getFechaInicial() != null) {
			ahorroPrevio.setFechaInicial(new Date(this.fechaInicial));
		}

		if (this.getFechaInmovilizacion() != null) {
			ahorroPrevio.setFechaInmovilizacion(new Date(this.fechaInmovilizacion));
		}

		ahorroPrevio.setIdAhorroPrevio(this.idAhorroPrevio);
		ahorroPrevio.setIdPostulacion(this.idPostulacion);
		ahorroPrevio.setNombreAhorro(this.nombreAhorro);
		ahorroPrevio.setValor(this.valor);
		return ahorroPrevio;
	}

	/**
	 * Método que convierte una entidad <code>AhorroPrevio</code> al DTO
	 * 
	 * @param ahorroPrevio
	 *            La entidad <code>AhorroPrevio</code> a convertir
	 */
	public void convertToDTO(AhorroPrevio ahorroPrevio) {
		this.setEntidad(ahorroPrevio.getEntidad());

		if (ahorroPrevio.getFechaAdquisicion() != null) {
			this.setFechaAdquisicion(ahorroPrevio.getFechaAdquisicion().getTime());
		}

		if (ahorroPrevio.getFechaInicial() != null) {
			this.setFechaInicial(ahorroPrevio.getFechaInicial().getTime());
		}

		if (ahorroPrevio.getFechaInmovilizacion() != null) {
			this.setFechaInmovilizacion(ahorroPrevio.getFechaInmovilizacion().getTime());
		}

		this.setIdAhorroPrevio(ahorroPrevio.getIdAhorroPrevio());
		this.setIdPostulacion(ahorroPrevio.getIdPostulacion());
		this.setNombreAhorro(ahorroPrevio.getNombreAhorro());
		this.setValor(ahorroPrevio.getValor());
	}

	/**
	 * Obtiene el valor de idAhorroPrevio
	 * 
	 * @return El valor de idAhorroPrevio
	 */
	public Long getIdAhorroPrevio() {
		return idAhorroPrevio;
	}

	/**
	 * Establece el valor de idAhorroPrevio
	 * 
	 * @param idAhorroPrevio
	 *            El valor de idAhorroPrevio por asignar
	 */
	public void setIdAhorroPrevio(Long idAhorroPrevio) {
		this.idAhorroPrevio = idAhorroPrevio;
	}

	/**
	 * Obtiene el valor de nombreAhorro
	 * 
	 * @return El valor de nombreAhorro
	 */
	public TipoAhorroPrevioEnum getNombreAhorro() {
		return nombreAhorro;
	}

	/**
	 * Establece el valor de nombreAhorro
	 * 
	 * @param nombreAhorro
	 *            El valor de nombreAhorro por asignar
	 */
	public void setNombreAhorro(TipoAhorroPrevioEnum nombreAhorro) {
		this.nombreAhorro = nombreAhorro;
	}

	/**
	 * Obtiene el valor de entidad
	 * 
	 * @return El valor de entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Establece el valor de entidad
	 * 
	 * @param entidad
	 *            El valor de entidad por asignar
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * Obtiene el valor de fechaInicial
	 * 
	 * @return El valor de fechaInicial
	 */
	public Long getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Establece el valor de fechaInicial
	 * 
	 * @param fechaInicial
	 *            El valor de fechaInicial por asignar
	 */
	public void setFechaInicial(Long fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Obtiene el valor de valor
	 * 
	 * @return El valor de valor
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * Establece el valor de valor
	 * 
	 * @param valor
	 *            El valor de valor por asignar
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene el valor de fechaInmovilizacion
	 * 
	 * @return El valor de fechaInmovilizacion
	 */
	public Long getFechaInmovilizacion() {
		return fechaInmovilizacion;
	}

	/**
	 * Establece el valor de fechaInmovilizacion
	 * 
	 * @param fechaInmovilizacion
	 *            El valor de fechaInmovilizacion por asignar
	 */
	public void setFechaInmovilizacion(Long fechaInmovilizacion) {
		this.fechaInmovilizacion = fechaInmovilizacion;
	}

	/**
	 * Obtiene el valor de fechaAdquisicion
	 * 
	 * @return El valor de fechaAdquisicion
	 */
	public Long getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * Establece el valor de fechaAdquisicion
	 * 
	 * @param fechaAdquisicion
	 *            El valor de fechaAdquisicion por asignar
	 */
	public void setFechaAdquisicion(Long fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	/**
	 * Obtiene el valor de idPostulacion
	 * 
	 * @return El valor de idPostulacion
	 */
	public Long getIdPostulacion() {
		return idPostulacion;
	}

	/**
	 * Establece el valor de idPostulacion
	 * 
	 * @param idPostulacion
	 *            El valor de idPostulacion por asignar
	 */
	public void setIdPostulacion(Long idPostulacion) {
		this.idPostulacion = idPostulacion;
	}

	/**
	 * @return the ahorroMovilizado
	 */
	public Boolean getAhorroMovilizado() {
		return ahorroMovilizado;
	}

	/**
	 * @param ahorroMovilizado the ahorroMovilizado to set
	 */
	public void setAhorroMovilizado(Boolean ahorroMovilizado) {
		this.ahorroMovilizado = ahorroMovilizado;
	}
	
}
