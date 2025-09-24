package com.asopagos.aportes.composite.dto;

import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * DTO que representa un pago a efectuar luego de una solicitud de devolución de
 * aportes.
 * 
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra
 *         Zuluaga. </a>
 */
public class PagoDTO {

	/**
	 * Forma de pago de la devolución.
	 */
	private TipoMedioDePagoEnum formaPago;

	/**
	 * Identificador de la sede de la caja de compensación
	 */
	private Long sede;

	/**
	 * Indica si devuelve o no dinero en efectivo.
	 */
	private Boolean efectivoActivo;

	/**
	 * Nombres y Apellidos del titular de la cuenta bancaria.
	 */
	private String titularNombres;

	/**
	 * Tipo de identificación del titular de la cuenta.
	 */
	private TipoIdentificacionEnum titularTipoIdentificacion;

	/**
	 * Número de identificación del documento del titular de la cuenta.
	 */
	private String titularNumeroIdentificacion;

	/**
	 * Banco relacionado.
	 */
	private Long idBanco;

	/**
	 * Dígito de verificación si se elige NIT de la lista de tipo de documento.
	 */
	private Short titularDv;

	/**
	 * Tipo de cuenta a la que se hará el desembolso de la devolución.
	 */
	private TipoCuentaEnum titularTipoCuenta;

	/**
	 * Número de cuenta del titular de la misma.
	 */
	private String titularNumeroCuenta;

	/**
	 * Lista que indica las opciones posibles de tarjeta.
	 */
	// ToDo - Por determinar en donde se encuentran los datos para crear la enum
	// e implementar la variable.
	// private TarjetaEnum disponeTarjeta;

	/**
	 * Lista de estados posibles de la tarjeta.
	 */
	// ToDo - Por determinar en donde se encuentran los datos para crear la enum
	// e implementar la variable.
	// private EstadoTarjetaEnum estadoTarjeta;

	/**
	 * Variable que indica si se emite o no una tarjeta.
	 */
	private Boolean emiteTarjeta;

	/**
	 * Variable que dice si se emite o no inmediatamente una tarjeta.
	 */
	private Boolean emiteInmeditamente;

	/**
	 * Método que transforma el DTO en un ojecto tipo
	 * <code>MedioDePagoModeloDTO</code>
	 * 
	 * @return El objeto <code>MedioDePagoModeloDTO</code> equivalente
	 */

	private Boolean tarjetaMultiservicio;

	private Long sitioPago;

	private Boolean cobroJudicial;

	public MedioDePagoModeloDTO convertToMedioDePagoModeloDTO() {
		MedioDePagoModeloDTO medioDePagoDTO = new MedioDePagoModeloDTO();

		medioDePagoDTO.setIdBanco(this.getIdBanco());
		medioDePagoDTO.setTipoCuenta(this.getTitularTipoCuenta());
		medioDePagoDTO.setNumeroCuenta(this.getTitularNumeroCuenta());
		medioDePagoDTO.setTipoIdentificacionTitular(this.getTitularTipoIdentificacion());
		medioDePagoDTO.setNumeroIdentificacionTitular(this.getTitularNumeroIdentificacion());
		medioDePagoDTO.setDigitoVerificacionTitular(this.getTitularDv());
		medioDePagoDTO.setNombreTitularCuenta(this.getTitularNombres());

		medioDePagoDTO.setSede(this.getSede());
		medioDePagoDTO.setEfectivo(Boolean.TRUE);

		medioDePagoDTO.setTipoMedioDePago(this.getFormaPago());
		medioDePagoDTO.setSitioPago(this.getSitioPago());
		return medioDePagoDTO;
	}

	/**
	 * Método que retorna el valor de formaPago.
	 * 
	 * @return valor de formaPago.
	 */
	public TipoMedioDePagoEnum getFormaPago() {
		return formaPago;
	}

	/**
	 * Método encargado de modificar el valor de formaPago.
	 * 
	 * @param valor
	 *            para modificar formaPago.
	 */
	public void setFormaPago(TipoMedioDePagoEnum formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * Método que retorna el valor de efectivoActivo.
	 * 
	 * @return valor de efectivoActivo.
	 */
	public Boolean getEfectivoActivo() {
		return efectivoActivo;
	}

	/**
	 * Método encargado de modificar el valor de efectivoActivo.
	 * 
	 * @param valor
	 *            para modificar efectivoActivo.
	 */
	public void setEfectivoActivo(Boolean efectivoActivo) {
		this.efectivoActivo = efectivoActivo;
	}

	/**
	 * Método que retorna el valor de titularNombres.
	 * 
	 * @return valor de titularNombres.
	 */
	public String getTitularNombres() {
		return titularNombres;
	}

	/**
	 * Método encargado de modificar el valor de titularNombres.
	 * 
	 * @param valor
	 *            para modificar titularNombres.
	 */
	public void setTitularNombres(String titularNombres) {
		this.titularNombres = titularNombres;
	}

	/**
	 * Método que retorna el valor de titularTipoIdentificacion.
	 * 
	 * @return valor de titularTipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTitularTipoIdentificacion() {
		return titularTipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de titularTipoIdentificacion.
	 * 
	 * @param valor
	 *            para modificar titularTipoIdentificacion.
	 */
	public void setTitularTipoIdentificacion(TipoIdentificacionEnum titularTipoIdentificacion) {
		this.titularTipoIdentificacion = titularTipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de titularNumeroIdentificacion.
	 * 
	 * @return valor de titularNumeroIdentificacion.
	 */
	public String getTitularNumeroIdentificacion() {
		return titularNumeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de titularNumeroIdentificacion.
	 * 
	 * @param valor
	 *            para modificar titularNumeroIdentificacion.
	 */
	public void setTitularNumeroIdentificacion(String titularNumeroIdentificacion) {
		this.titularNumeroIdentificacion = titularNumeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de titularDv.
	 * 
	 * @return valor de titularDv.
	 */
	public Short getTitularDv() {
		return titularDv;
	}

	/**
	 * Método encargado de modificar el valor de titularDv.
	 * 
	 * @param valor
	 *            para modificar titularDv.
	 */
	public void setTitularDv(Short titularDv) {
		this.titularDv = titularDv;
	}

	/**
	 * Método que retorna el valor de titularTipoCuenta.
	 * 
	 * @return valor de titularTipoCuenta.
	 */
	public TipoCuentaEnum getTitularTipoCuenta() {
		return titularTipoCuenta;
	}

	/**
	 * Método encargado de modificar el valor de titularTipoCuenta.
	 * 
	 * @param valor
	 *            para modificar titularTipoCuenta.
	 */
	public void setTitularTipoCuenta(TipoCuentaEnum titularTipoCuenta) {
		this.titularTipoCuenta = titularTipoCuenta;
	}

	/**
	 * Método que retorna el valor de titularNumeroCuenta.
	 * 
	 * @return valor de titularNumeroCuenta.
	 */
	public String getTitularNumeroCuenta() {
		return titularNumeroCuenta;
	}

	/**
	 * Método encargado de modificar el valor de titularNumeroCuenta.
	 * 
	 * @param valor
	 *            para modificar titularNumeroCuenta.
	 */
	public void setTitularNumeroCuenta(String titularNumeroCuenta) {
		this.titularNumeroCuenta = titularNumeroCuenta;
	}

	/**
	 * Método que retorna el valor de emiteTarjeta.
	 * 
	 * @return valor de emiteTarjeta.
	 */
	public Boolean getEmiteTarjeta() {
		return emiteTarjeta;
	}

	/**
	 * Método encargado de modificar el valor de emiteTarjeta.
	 * 
	 * @param valor
	 *            para modificar emiteTarjeta.
	 */
	public void setEmiteTarjeta(Boolean emiteTarjeta) {
		this.emiteTarjeta = emiteTarjeta;
	}

	/**
	 * Método que retorna el valor de emiteInmeditamente.
	 * 
	 * @return valor de emiteInmeditamente.
	 */
	public Boolean getEmiteInmeditamente() {
		return emiteInmeditamente;
	}

	/**
	 * Método encargado de modificar el valor de emiteInmeditamente.
	 * 
	 * @param valor
	 *            para modificar emiteInmeditamente.
	 */
	public void setEmiteInmeditamente(Boolean emiteInmeditamente) {
		this.emiteInmeditamente = emiteInmeditamente;
	}

	/**
	 * Método que retorna el valor de idBanco.
	 * 
	 * @return valor de idBanco.
	 */
	public Long getIdBanco() {
		return idBanco;
	}

	/**
	 * Método encargado de modificar el valor de idBanco.
	 * 
	 * @param valor
	 *            para modificar idBanco.
	 */
	public void setIdBanco(Long idBanco) {
		this.idBanco = idBanco;
	}

	/**
	 * Obtiene el valor de sede
	 * 
	 * @return El valor de sede
	 */
	public Long getSede() {
		return sede;
	}

	/**
	 * Establece el valor de sede
	 * 
	 * @param sede
	 *            El valor de sede por asignar
	 */
	public void setSede(Long sede) {
		this.sede = sede;
	}


	/**
	 * Obtiene el valor de sede
	 * 
	 * @return El valor de sede
	 */
	public Boolean getTarjetaMultiservicio() {
		return tarjetaMultiservicio;
	}

	/**
	 * Establece el valor de sede
	 * 
	 * @param sede
	 *            El valor de sede por asignar
	 */
	public void setTarjetaMultiservicio(Boolean tarjetaMultiservicio) {
		this.tarjetaMultiservicio = tarjetaMultiservicio;
	}

	public void setSitioPago(Long sitioPago) {
        this.sitioPago = sitioPago;
    }

	public Long getSitioPago() {
        return sitioPago;
    }

	public Boolean getCobroJudicial() {
		return this.cobroJudicial;
	}

	public void setCobroJudicial(Boolean cobroJudicial) {
		this.cobroJudicial = cobroJudicial;
	}

}
