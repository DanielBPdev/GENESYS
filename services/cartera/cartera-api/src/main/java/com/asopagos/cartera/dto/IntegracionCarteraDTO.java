package com.asopagos.cartera.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoConvenioPagoEnum;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripción: </b> Clase que representa la estructura de datos de respuesta
 * para los servicios de integración de cartera <br/>
 * <b>Historia de Usuario: </b> Portafolio de servicios - Cartera
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class IntegracionCarteraDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7321220741055980334L;

	/**
	 * Tipo de aportante
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoAportante;

	/**
	 * Tipo de identificación del aportante
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del aportante
	 */
	private String numeroIdentificacion;

	/**
	 * Nombre o razón social del aportante
	 */
	private String razonSocial;

	/**
	 * Deuda presunta del aportante
	 */
	private BigDecimal deudaPresunta;

	/**
	 * Estado de cartera del aportante
	 */
	private EstadoCarteraEnum estadoCartera;

	/**
	 * Periodo de deuda del aportante
	 */
	private String periodoEnMora;

	/**
	 * Cantidad de meses en mora que presenta el aportante
	 */
	private Integer mesesEnMora;

	/**
	 * Periodo de consulta de la deuda
	 */
	private String periodoConsulta;

	/**
	 * Línea de cobro
	 */
	private TipoLineaCobroEnum lineaDeCobro;

	/**
	 * Método de cobro (aplica únicamente para empleadores)
	 */
	private MetodoAccionCobroEnum metodoDeCobro;

	/**
	 * Tipo de acción de cobro
	 */
	private TipoAccionCobroEnum tipoAccionDeCobro;

	/**
	 * Tipo de deuda
	 */
	private TipoDeudaEnum tipoDeDeuda;

	/**
	 * Número del convenio de pago
	 */
	private Long numeroConvenioPago;

	/**
	 * Estado del convenio de pago
	 */
	private EstadoConvenioPagoEnum estadoConvenioPago;

	/**
	 * Fecha límite del convenio de pago. Formato yyyy-MM-dd
	 */
	private String fechaLimitePago;

	/**
	 * Fecha de registro del convenio de pago. Formato yyyy-MM-dd
	 */
	private String fechaRegistroConvenioPago;

	/**
	 * Fecha de registro del aportante en cartera. Formato yyyy-MM-dd
	 */
	private String fechaInicioCartera;

	/**
	 * Lista de periodos en mora
	 */
	private List<DetalleIntegracionCarteraDTO> periodosMora;

	/**
	 * Obtiene el valor de tipoAportante
	 * 
	 * @return El valor de tipoAportante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
		return tipoAportante;
	}

	/**
	 * Establece el valor de tipoAportante
	 * 
	 * @param tipoAportante
	 *            El valor de tipoAportante por asignar
	 */
	public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
		this.tipoAportante = tipoAportante;
	}

	/**
	 * Obtiene el valor de razonSocial
	 * 
	 * @return El valor de razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Establece el valor de razonSocial
	 * 
	 * @param razonSocial
	 *            El valor de razonSocial por asignar
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Obtiene el valor de deudaPresunta
	 * 
	 * @return El valor de deudaPresunta
	 */
	public BigDecimal getDeudaPresunta() {
		return deudaPresunta;
	}

	/**
	 * Establece el valor de deudaPresunta
	 * 
	 * @param deudaPresunta
	 *            El valor de deudaPresunta por asignar
	 */
	public void setDeudaPresunta(BigDecimal deudaPresunta) {
		this.deudaPresunta = deudaPresunta;
	}

	/**
	 * Obtiene el valor de estadoCartera
	 * 
	 * @return El valor de estadoCartera
	 */
	public EstadoCarteraEnum getEstadoCartera() {
		return estadoCartera;
	}

	/**
	 * Establece el valor de estadoCartera
	 * 
	 * @param estadoCartera
	 *            El valor de estadoCartera por asignar
	 */
	public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
		this.estadoCartera = estadoCartera;
	}

	/**
	 * Obtiene el valor de periodoEnMora
	 * 
	 * @return El valor de periodoEnMora
	 */
	public String getPeriodoEnMora() {
		return periodoEnMora;
	}

	/**
	 * Establece el valor de periodoEnMora
	 * 
	 * @param periodoEnMora
	 *            El valor de periodoEnMora por asignar
	 */
	public void setPeriodoEnMora(String periodoEnMora) {
		this.periodoEnMora = periodoEnMora;
	}

	/**
	 * Obtiene el valor de mesesEnMora
	 * 
	 * @return El valor de mesesEnMora
	 */
	public Integer getMesesEnMora() {
		return mesesEnMora;
	}

	/**
	 * Establece el valor de mesesEnMora
	 * 
	 * @param mesesEnMora
	 *            El valor de mesesEnMora por asignar
	 */
	public void setMesesEnMora(Integer mesesEnMora) {
		this.mesesEnMora = mesesEnMora;
	}

	/**
	 * Obtiene el valor de lineaDeCobro
	 * 
	 * @return El valor de lineaDeCobro
	 */
	public TipoLineaCobroEnum getLineaDeCobro() {
		return lineaDeCobro;
	}

	/**
	 * Establece el valor de lineaDeCobro
	 * 
	 * @param lineaDeCobro
	 *            El valor de lineaDeCobro por asignar
	 */
	public void setLineaDeCobro(TipoLineaCobroEnum lineaDeCobro) {
		this.lineaDeCobro = lineaDeCobro;
	}

	/**
	 * Obtiene el valor de metodoDeCobro
	 * 
	 * @return El valor de metodoDeCobro
	 */
	public MetodoAccionCobroEnum getMetodoDeCobro() {
		return metodoDeCobro;
	}

	/**
	 * Establece el valor de metodoDeCobro
	 * 
	 * @param metodoDeCobro
	 *            El valor de metodoDeCobro por asignar
	 */
	public void setMetodoDeCobro(MetodoAccionCobroEnum metodoDeCobro) {
		this.metodoDeCobro = metodoDeCobro;
	}

	/**
	 * Obtiene el valor de tipoAccionDeCobro
	 * 
	 * @return El valor de tipoAccionDeCobro
	 */
	public TipoAccionCobroEnum getTipoAccionDeCobro() {
		return tipoAccionDeCobro;
	}

	/**
	 * Establece el valor de tipoAccionDeCobro
	 * 
	 * @param tipoAccionDeCobro
	 *            El valor de tipoAccionDeCobro por asignar
	 */
	public void setTipoAccionDeCobro(TipoAccionCobroEnum tipoAccionDeCobro) {
		this.tipoAccionDeCobro = tipoAccionDeCobro;
	}

	/**
	 * Obtiene el valor de tipoDeDeuda
	 * 
	 * @return El valor de tipoDeDeuda
	 */
	public TipoDeudaEnum getTipoDeDeuda() {
		return tipoDeDeuda;
	}

	/**
	 * Establece el valor de tipoDeDeuda
	 * 
	 * @param tipoDeDeuda
	 *            El valor de tipoDeDeuda por asignar
	 */
	public void setTipoDeDeuda(TipoDeudaEnum tipoDeDeuda) {
		this.tipoDeDeuda = tipoDeDeuda;
	}

	/**
	 * Obtiene el valor de numeroConvenioPago
	 * 
	 * @return El valor de numeroConvenioPago
	 */
	public Long getNumeroConvenioPago() {
		return numeroConvenioPago;
	}

	/**
	 * Establece el valor de numeroConvenioPago
	 * 
	 * @param numeroConvenioPago
	 *            El valor de numeroConvenioPago por asignar
	 */
	public void setNumeroConvenioPago(Long numeroConvenioPago) {
		this.numeroConvenioPago = numeroConvenioPago;
	}

	/**
	 * Obtiene el valor de estadoConvenioPago
	 * 
	 * @return El valor de estadoConvenioPago
	 */
	public EstadoConvenioPagoEnum getEstadoConvenioPago() {
		return estadoConvenioPago;
	}

	/**
	 * Establece el valor de estadoConvenioPago
	 * 
	 * @param estadoConvenioPago
	 *            El valor de estadoConvenioPago por asignar
	 */
	public void setEstadoConvenioPago(EstadoConvenioPagoEnum estadoConvenioPago) {
		this.estadoConvenioPago = estadoConvenioPago;
	}

	/**
	 * Obtiene el valor de fechaLimitePago
	 * 
	 * @return El valor de fechaLimitePago
	 */
	public String getFechaLimitePago() {
		return fechaLimitePago;
	}

	/**
	 * Establece el valor de fechaLimitePago
	 * 
	 * @param fechaLimitePago
	 *            El valor de fechaLimitePago por asignar
	 */
	public void setFechaLimitePago(String fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}

	/**
	 * Obtiene el valor de fechaRegistroConvenioPago
	 * 
	 * @return El valor de fechaRegistroConvenioPago
	 */
	public String getFechaRegistroConvenioPago() {
		return fechaRegistroConvenioPago;
	}

	/**
	 * Establece el valor de fechaRegistroConvenioPago
	 * 
	 * @param fechaRegistroConvenioPago
	 *            El valor de fechaRegistroConvenioPago por asignar
	 */
	public void setFechaRegistroConvenioPago(String fechaRegistroConvenioPago) {
		this.fechaRegistroConvenioPago = fechaRegistroConvenioPago;
	}

	/**
	 * Obtiene el valor de tipoIdentificacion
	 * 
	 * @return El valor de tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Establece el valor de tipoIdentificacion
	 * 
	 * @param tipoIdentificacion
	 *            El valor de tipoIdentificacion por asignar
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroIdentificacion
	 * 
	 * @return El valor de numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Establece el valor de numeroIdentificacion
	 * 
	 * @param numeroIdentificacion
	 *            El valor de numeroIdentificacion por asignar
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Obtiene el valor de fechaInicioCartera
	 * 
	 * @return El valor de fechaInicioCartera
	 */
	public String getFechaInicioCartera() {
		return fechaInicioCartera;
	}

	/**
	 * Establece el valor de fechaInicioCartera
	 * 
	 * @param fechaInicioCartera
	 *            El valor de fechaInicioCartera por asignar
	 */
	public void setFechaInicioCartera(String fechaInicioCartera) {
		this.fechaInicioCartera = fechaInicioCartera;
	}

	/**
	 * Obtiene el valor de periodosMora
	 * 
	 * @return El valor de periodosMora
	 */
	public List<DetalleIntegracionCarteraDTO> getPeriodosMora() {
		return periodosMora;
	}

	/**
	 * Establece el valor de periodosMora
	 * 
	 * @param periodosMora
	 *            El valor de periodosMora por asignar
	 */
	public void setPeriodosMora(List<DetalleIntegracionCarteraDTO> periodosMora) {
		this.periodosMora = periodosMora;
	}

	/**
	 * Obtiene el valor de periodoConsulta
	 * 
	 * @return El valor de periodoConsulta
	 */
	public String getPeriodoConsulta() {
		return periodoConsulta;
	}

	/**
	 * Establece el valor de periodoConsulta
	 * 
	 * @param periodoConsulta
	 *            El valor de periodoConsulta por asignar
	 */
	public void setPeriodoConsulta(String periodoConsulta) {
		this.periodoConsulta = periodoConsulta;
	}
}
