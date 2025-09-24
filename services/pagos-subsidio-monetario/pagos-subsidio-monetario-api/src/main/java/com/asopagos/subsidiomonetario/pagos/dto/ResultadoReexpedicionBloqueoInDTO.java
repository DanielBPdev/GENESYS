package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase DTO para la entrada del servicio CapturarResultadoReexpedicionBloqueo <br/>
 * <b>Módulo:</b> Control de cambíos Tarjeta (Isla)<br/>
 *
 * @author <a href="mailto:squintero@heinsohn.com.co"> Steven Quintero González</a>
 */
@XmlRootElement
public class ResultadoReexpedicionBloqueoInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idProceso;
	private Long consecutivoRegistro;
	private Short tipoNovedad;
	private String tipoIdentificacion;
	private String identificacion;
	private String numeroTarjeta;
	private String numeroTarjetaExpedida;
	private BigDecimal saldoNuevaTarjeta;
	private String fechaTransaccion;
	private String usuario;
	private Long idMEdioDePagoActualizar;
	private String tipoSolicitud;

	public ResultadoReexpedicionBloqueoInDTO() {
	}

	
	
	/**
	 * @return the idProceso
	 */
	public Long getIdProceso() {
		return idProceso;
	}
	
	/**
	 * @param idProceso the idProceso to set
	 */
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	
	/**
	 * @return the consecutivoRegistro
	 */
	public Long getConsecutivoRegistro() {
		return consecutivoRegistro;
	}
	
	/**
	 * @param consecutivoRegistro the consecutivoRegistro to set
	 */
	public void setConsecutivoRegistro(Long consecutivoRegistro) {
		this.consecutivoRegistro = consecutivoRegistro;
	}
	
	/**
	 * @return the tipoNovedad
	 */
	public Short getTipoNovedad() {
		return tipoNovedad;
	}
	
	/**
	 * @param tipoNovedad the tipoNovedad to set
	 */
	public void setTipoNovedad(Short tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
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
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}
	
	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	/**
	 * @return the numeroTarjeta
	 */
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	
	/**
	 * @param numeroTarjeta the numeroTarjeta to set
	 */
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	
	/**
	 * @return the numeroTarjetaExpedida
	 */
	public String getNumeroTarjetaExpedida() {
		return numeroTarjetaExpedida;
	}
	
	/**
	 * @param numeroTarjetaExpedida the numeroTarjetaExpedida to set
	 */
	public void setNumeroTarjetaExpedida(String numeroTarjetaExpedida) {
		this.numeroTarjetaExpedida = numeroTarjetaExpedida;
	}
	
	/**
	 * @return the saldoNuevaTarjeta
	 */
	public BigDecimal getSaldoNuevaTarjeta() {
		return saldoNuevaTarjeta;
	}
	
	/**
	 * @param saldoNuevaTarjeta the saldoNuevaTarjeta to set
	 */
	public void setSaldoNuevaTarjeta(BigDecimal saldoNuevaTarjeta) {
		this.saldoNuevaTarjeta = saldoNuevaTarjeta;
	}
	
	/**
	 * @return the fechaTransaccion
	 */
	public String getFechaTransaccion() {
		return fechaTransaccion;
	}
	
	/**
	 * @param fechaTransaccion the fechaTransaccion to set
	 */
	public void setFechaTransaccion(String fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}
	
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public Long getIdMEdioDePagoActualizar() {
		return this.idMEdioDePagoActualizar;
	}

	public void setIdMEdioDePagoActualizar(Long idMEdioDePagoActualizar) {
		this.idMEdioDePagoActualizar = idMEdioDePagoActualizar;
	}


	public String getTipoSolicitud() {
		return this.tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	@Override
	public String toString() {
		return "{" +
			" idProceso='" + getIdProceso() + "'" +
			", consecutivoRegistro='" + getConsecutivoRegistro() + "'" +
			", tipoNovedad='" + getTipoNovedad() + "'" +
			", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
			", identificacion='" + getIdentificacion() + "'" +
			", numeroTarjeta='" + getNumeroTarjeta() + "'" +
			", numeroTarjetaExpedida='" + getNumeroTarjetaExpedida() + "'" +
			", saldoNuevaTarjeta='" + getSaldoNuevaTarjeta() + "'" +
			", fechaTransaccion='" + getFechaTransaccion() + "'" +
			", usuario='" + getUsuario() + "'" +
			", idMEdioDePagoActualizar='" + getIdMEdioDePagoActualizar() + "'" +
			", tipoSolicitud='" + getTipoSolicitud() + "'" +
			"}";
	}


}
