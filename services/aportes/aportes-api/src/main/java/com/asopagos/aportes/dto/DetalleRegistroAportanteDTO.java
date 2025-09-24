package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.aportes.DestinatarioDevolucionEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;


import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
public class DetalleRegistroAportanteDTO implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -1556743265842850652L;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo general
	 */
	private Long numeroOperacion;

	/**
	 * Indica el tipo de solicitante que realizo el movimiento en aporte
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoAportante;

	/**
	 * Descripción del estado del registro a nivel de Aportante
	 */
	private EstadoRegistroAporteEnum tipoRegistro;

	/**
	 * Tipo de identificacion del aporte
	 */
	private TipoIdentificacionEnum tipoIdentificacionAportante;

	/**
	 * Número de Identificación de aporte
	 */
	private String numeroIdentificacionAportante;

	/**
	 * Descripción de la razón social
	 */
	private String razonSocial;

	/**
	 * Período de pago
	 */
	private String periodoAporte;

	/**
	 * Fecha de recaudo del aporte
	 */
	private Date fechaRecaudo;

	/**
	 * Numero de planilla o manual
	 */
	private String numeroPlanilla;

	/**
	 * Numero de planilla o manual
	 */
	private String recaudoOriginal;

	/**
	 * Aporte de registro
	 */
	private BigDecimal monto;

	/**
	 * Interes de aporte
	 */
	private BigDecimal interes;

	/**
	 * Total de aportes
	 */
	private BigDecimal totalAporte;

	/**
	 * Usuario que ejecuto la operación
	 */
	private String usuario;

	/**
	 * Destinatario de la devolución.(Aplica para registros que sean
	 * Devoluciones)
	 */
	private DestinatarioDevolucionEnum destinatario;

	/**
	 * Descuentos por gestión financiera. Un valor <code>null</code> en este
	 * campo, indica que este descuento no fue aplicado (Aplica para registros
	 * que sean Devoluciones)
	 */
	private BigDecimal valorGastosBancarios;

	/**
	 * Fecha de reconocimiento del aporte, en la cual se almacena la forma de
	 * reconocimiento (Aplica para registros legalizados)
	 */
	private Date fechaReconocimiento;

	/**
	 * Fecha de reconocimiento del aporte, en la cual se almacena la forma de
	 * reconocimiento (Aplica para registros legalizados)
	 */
	private Date fechaProcesamiento;

	/**
	 * Fecha de recaudo de la corrección
	 */
	private Date fechaRecaudoCorreccion;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo de la correccion
	 */
	private Long numeroOperacionCorreccion;

	/**
	 * ID de registro general
	 */
	private Long idRegistroGeneral;

	/**
	 * Modalidad de recaudo de aporte
	 */
	private ModalidadRecaudoAporteEnum modalidadRecaudo;

	/**
	 * Oportunidad de pago
	 * Posibles opciones: PERIODO_RETROACTIVO, PERIODO_REGULAR, PERIODO_FUTURO
	 */
	private String oportunidadDePago;

	/**
	 * Estado aportante
	 */

	private String estadoAportante;


	/**
	 * Banco recaudador o código de la entidad financiera
	 */

	private String codigoBancoRecaudador;

	/**
	 * Número de cuenta de recaudo
	 * corresponde al valor dado por staging en PILA
	 */

	private String cuentaBancariaRecaudo;


	/**
	 * Sucursal asociada al aporte
	 */

	private String sucursalEmpresa;

	/**
	 * Tarifa de cortizante
	 */

	private BigDecimal tarifaAportante;

	/**
	 * Fecha de pago del aporte
	 */
	private Date fechaPago;

	/**
	 * Fecha de pago del aporte
	 */
	private Date fechaDevolucion;


	/** Digito verificacion
	 * 
	 */

	 private String digitoVerificacion;

	/** Numero solicitud devolucion
	 *
	 */
	 private String numeroSolicitudDevolucion;

	 /** Numero solicitud de solicitud Correccion
	 *
	 */
	private String numeroSolicitudCorreccion;

	private String tipoPlanilla;

	private Boolean pagadorPorSiMismo ;
	
	private TipoIdentificacionEnum tipoIdentificacionTercero;

	private String numeroIdentificacionTercero;
	
	private String razonSocialtercero;

	private String motivoDeRetiro;


	/** Constructor por defecto */
	public DetalleRegistroAportanteDTO() {
	}

	/** Constructor para consulta de aportes y legalizaciones */
	public DetalleRegistroAportanteDTO(Long numeroOperacion, String tipoAportante, String tipoRegistro,
			String tipoIdentificacionAportante, String numeroIdentificacionAportante, String razonSocial,
			String periodoAporte, Date fechaRecaudo, Date fechaReconocimiento, String numeroPlanilla, BigDecimal monto, BigDecimal interes,
			BigDecimal totalAporte, String usuario, Long idRegistroGeneral, String modalidadRecaudo,
			Date fechaProcesamiento, String oportunidadDePago, String estadoAportante, String sucursalEmpresa,
			String digitoVerificacion, String pagadorPorSiMismo, String tipoIdentificacionTercero,
			String numeroIdentificacionTercero, String razonSocialtercero, String motivoDeRetiro) {
		this.numeroOperacion = numeroOperacion;
		this.tipoAportante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante);
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
		this.razonSocial = razonSocial;
		this.periodoAporte = periodoAporte;
		this.fechaRecaudo = fechaRecaudo;
		this.fechaReconocimiento = fechaReconocimiento;
		this.numeroPlanilla = numeroPlanilla;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.usuario = usuario;
		this.idRegistroGeneral = idRegistroGeneral;
		this.modalidadRecaudo = ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudo);
		this.fechaProcesamiento = fechaProcesamiento;
		this.oportunidadDePago = oportunidadDePago;
		this.estadoAportante = estadoAportante;
		this.sucursalEmpresa = sucursalEmpresa;
		this.digitoVerificacion = digitoVerificacion;
		if(pagadorPorSiMismo == null){
			this.pagadorPorSiMismo = Boolean.TRUE;
		}else{
			this.pagadorPorSiMismo = Boolean.FALSE;
		}
		if(tipoIdentificacionTercero != null){
			this.tipoIdentificacionTercero = TipoIdentificacionEnum.valueOf(tipoIdentificacionTercero);
		}
		this.numeroIdentificacionTercero = numeroIdentificacionTercero;
		this.razonSocialtercero = razonSocialtercero;
		this.motivoDeRetiro = motivoDeRetiro;


	}
		/** Constructor para consulta devoluciones */


	public DetalleRegistroAportanteDTO(Long numeroOperacion, String tipoAportante, String tipoRegistro,
			String tipoIdentificacionAportante, String numeroIdentificacionAportante, String razonSocial,
			String periodoAporte, Date fechaRecaudo, String numeroPlanilla, BigDecimal monto, BigDecimal interes,
			BigDecimal totalAporte, String usuario, Long idRegistroGeneral, String modalidadRecaudo,
			Date fechaReconocimiento, String oportunidadDePago, String estadoAportante, String sucursalEmpresa,
			String digitoVerificacion, String pagadorPorSiMismo, String tipoIdentificacionTercero,
			String numeroIdentificacionTercero, String razonSocialtercero) {
		this.numeroOperacion = numeroOperacion;
		this.tipoAportante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante);
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
		this.razonSocial = razonSocial;
		this.periodoAporte = periodoAporte;
		this.fechaRecaudo = fechaRecaudo;
		this.numeroPlanilla = numeroPlanilla;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.usuario = usuario;
		this.idRegistroGeneral = idRegistroGeneral;
		this.modalidadRecaudo = ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudo);
		this.fechaProcesamiento = fechaProcesamiento;
		this.oportunidadDePago = oportunidadDePago;
		this.estadoAportante = estadoAportante;
		this.sucursalEmpresa = sucursalEmpresa;
		this.digitoVerificacion = digitoVerificacion;
		if(pagadorPorSiMismo == null){
			this.pagadorPorSiMismo = Boolean.TRUE;
		}else{
			this.pagadorPorSiMismo = Boolean.FALSE;
		}
		if(tipoIdentificacionTercero != null){
			this.tipoIdentificacionTercero = TipoIdentificacionEnum.valueOf(tipoIdentificacionTercero);
		}
		this.numeroIdentificacionTercero = numeroIdentificacionTercero;
		this.razonSocialtercero = razonSocialtercero;


	}

	/** Constructor para consulta de devoluciones */
	public DetalleRegistroAportanteDTO(Long numeroOperacion, String tipoAportante, String tipoRegistro,
			String tipoIdentificacionAportante, String numeroIdentificacionAportante, String razonSocial,
			String periodoAporte, Date fechaDevolucion, String numeroPlanilla, String numeroSolicitudDevolucion, BigDecimal monto, BigDecimal interes,
			BigDecimal totalAporte, String usuario, String destinatario, BigDecimal valorGastosBancarios,
			Long idRegistroGeneral, String modalidadRecaudo,
			String oportunidadDePago, String estadoAportante, String sucursalEmpresa, 
			String digitoVerificacion, Date fechaReconocimiento, Date fechaRecaudo, String pagadorPorSiMismo, String tipoIdentificacionTercero,
			String numeroIdentificacionTercero, String razonSocialtercero) {
		this.numeroOperacion = numeroOperacion;
		this.tipoAportante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante);
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
		this.razonSocial = razonSocial;
		this.periodoAporte = periodoAporte;
		this.fechaDevolucion = fechaDevolucion;
		this.numeroPlanilla = numeroPlanilla;
		this.numeroSolicitudDevolucion = numeroSolicitudDevolucion;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.usuario = usuario;
		this.destinatario = DestinatarioDevolucionEnum.valueOf(destinatario);
		this.valorGastosBancarios = valorGastosBancarios;
		this.idRegistroGeneral = idRegistroGeneral;
		this.modalidadRecaudo = ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudo);
		this.oportunidadDePago = oportunidadDePago;
		this.estadoAportante = estadoAportante;
		this.sucursalEmpresa = sucursalEmpresa;
		this.digitoVerificacion = digitoVerificacion;
		this.fechaReconocimiento = fechaReconocimiento;
		this.fechaRecaudo = fechaRecaudo;
			if(pagadorPorSiMismo == null){
			this.pagadorPorSiMismo = Boolean.TRUE;
		}else{
			this.pagadorPorSiMismo = Boolean.FALSE;
		}
		if(tipoIdentificacionTercero != null){
			this.tipoIdentificacionTercero = TipoIdentificacionEnum.valueOf(tipoIdentificacionTercero);
		}
		this.numeroIdentificacionTercero = numeroIdentificacionTercero;
		this.razonSocialtercero = razonSocialtercero;
	}
	/**
	 * Constructor para consulta de correcciones
	 */
	public DetalleRegistroAportanteDTO(Long numeroOperacion, String tipoAportante, String tipoRegistro,
			String tipoIdentificacionAportante, String numeroIdentificacionAportante, String razonSocial,
			String periodoAporte, String numeroSolicitudCorreccion, Date fechaRecaudo, Date fechaRecaudoCorreccion, String numeroPlanilla,
			BigDecimal monto, BigDecimal interes, BigDecimal totalAporte, String usuario,
			Long numeroOperacionCorreccion, Long idRegistroGeneral, String modalidadRecaudo,
			String oportunidadDePago, String estadoAportante, String sucursalEmpresa, 
			String digitoVerificacion, Date fechaReconocimiento,String pagadorPorSiMismo, String tipoIdentificacionTercero,
			String numeroIdentificacionTercero, String razonSocialtercero) {
		this.numeroOperacion = numeroOperacion;
		this.tipoAportante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante);
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
		this.razonSocial = razonSocial;
		this.periodoAporte = periodoAporte;
		this.numeroSolicitudCorreccion = numeroSolicitudCorreccion;
		this.fechaRecaudo = fechaRecaudo;
		this.fechaRecaudoCorreccion = fechaRecaudoCorreccion;
		this.numeroPlanilla = numeroPlanilla;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.usuario = usuario;
		this.numeroOperacionCorreccion = numeroOperacionCorreccion;
		this.idRegistroGeneral = idRegistroGeneral;
		this.modalidadRecaudo = ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudo);
		this.oportunidadDePago = oportunidadDePago;
		this.estadoAportante = estadoAportante;
		this.sucursalEmpresa = sucursalEmpresa;
		this.digitoVerificacion = digitoVerificacion;
		this.fechaReconocimiento = fechaReconocimiento;
			if(pagadorPorSiMismo == null){
			this.pagadorPorSiMismo = Boolean.TRUE;
		}else{
			this.pagadorPorSiMismo = Boolean.FALSE;
		}
		if(tipoIdentificacionTercero != null){
			this.tipoIdentificacionTercero = TipoIdentificacionEnum.valueOf(tipoIdentificacionTercero);
		}
		this.numeroIdentificacionTercero = numeroIdentificacionTercero;
		this.razonSocialtercero = razonSocialtercero;
	}

	/**
	 * Constructor para consulta de correcciones a la alta
	 */
	public DetalleRegistroAportanteDTO(Long numeroOperacion, String tipoAportante, String tipoRegistro,
			String tipoIdentificacionAportante, String numeroIdentificacionAportante, String razonSocial,
			String periodoAporte, Date fechaRecaudo, Date fechaRecaudoCorreccion, String numeroPlanilla, String recaudoOriginal,
			BigDecimal monto, BigDecimal interes, BigDecimal totalAporte, String usuario,
			Long numeroOperacionCorreccion, Long idRegistroGeneral, String modalidadRecaudo,
			String oportunidadDePago, String estadoAportante, String sucursalEmpresa, 
			String digitoVerificacion, Date fechaReconocimiento, String pagadorPorSiMismo, String tipoIdentificacionTercero,
			String numeroIdentificacionTercero, String razonSocialtercero, String motivoDeRetiro) {
		this.numeroOperacion = numeroOperacion;
		this.tipoAportante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante);
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
		this.razonSocial = razonSocial;
		this.periodoAporte = periodoAporte;
		this.fechaRecaudo = fechaRecaudo;
		this.fechaRecaudoCorreccion = fechaRecaudoCorreccion;
		this.numeroPlanilla = numeroPlanilla;
		this.recaudoOriginal = recaudoOriginal;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.usuario = usuario;
		this.numeroOperacionCorreccion = numeroOperacionCorreccion;
		this.idRegistroGeneral = idRegistroGeneral;
		this.modalidadRecaudo = ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudo);
		this.oportunidadDePago = oportunidadDePago;
		this.estadoAportante = estadoAportante;
		this.sucursalEmpresa = sucursalEmpresa;
		this.digitoVerificacion = digitoVerificacion;
		this.fechaReconocimiento = fechaReconocimiento;
		if(pagadorPorSiMismo == null){
			this.pagadorPorSiMismo = Boolean.TRUE;
		}else{
			this.pagadorPorSiMismo = Boolean.FALSE;
		}
		if(tipoIdentificacionTercero != null){
			this.tipoIdentificacionTercero = TipoIdentificacionEnum.valueOf(tipoIdentificacionTercero);
		}
		this.numeroIdentificacionTercero = numeroIdentificacionTercero;
		this.razonSocialtercero = razonSocialtercero;
		this.motivoDeRetiro = motivoDeRetiro;

	}

	/**
	 * @return the numeroOperacion
	 */
	public Long getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * @param numeroOperacion
	 *            the numeroOperacion to set
	 */
	public void setNumeroOperacion(Long numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	/**
	 * @return the tipoAportante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
		return tipoAportante;
	}

	/**
	 * @param tipoAportante
	 *            the tipoAportante to set
	 */
	public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
		this.tipoAportante = tipoAportante;
	}

	/**
	 * @return the tipoRegistro
	 */
	public EstadoRegistroAporteEnum getTipoRegistro() {
		return tipoRegistro;
	}

	/**
	 * @param tipoRegistro
	 *            the tipoRegistro to set
	 */
	public void setTipoRegistro(EstadoRegistroAporteEnum tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	/**
	 * @return the tipoIdentificacionAportante
	 */
	public TipoIdentificacionEnum getTipoIdentificacionAportante() {
		return tipoIdentificacionAportante;
	}

	/**
	 * @param tipoIdentificacionAportante
	 *            the tipoIdentificacionAportante to set
	 */
	public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
		this.tipoIdentificacionAportante = tipoIdentificacionAportante;
	}

	/**
	 * @return the numeroIdentificacionAportante
	 */
	public String getNumeroIdentificacionAportante() {
		return numeroIdentificacionAportante;
	}

	/**
	 * @param numeroIdentificacionAportante
	 *            the numeroIdentificacionAportante to set
	 */
	public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial
	 *            the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the periodoAporte
	 */
	public String getPeriodoAporte() {
		return periodoAporte;
	}

	/**
	 * @param periodoAporte
	 *            the periodoAporte to set
	 */
	public void setPeriodoAporte(String periodoAporte) {
		this.periodoAporte = periodoAporte;
	}

	/**
	 * @return the fechaRecaudo
	 */
	public Date getFechaRecaudo() {
		return fechaRecaudo;
	}

	/**
	 * @param fechaRecaudo
	 *            the fechaRecaudo to set
	 */
	public void setFechaRecaudo(Date fechaRecaudo) {
		this.fechaRecaudo = fechaRecaudo;
	}

	/**
	 * @return the numeroPlanilla
	 */
	public String getNumeroPlanilla() {
		return numeroPlanilla;
	}

	/**
	 * @param numeroPlanilla
	 *            the numeroPlanilla to set
	 */
	public void setNumeroPlanilla(String numeroPlanilla) {
		this.numeroPlanilla = numeroPlanilla;
	}

	/**
	 * @return the recaudoOriginal
	 */
	public String getRecaudoOriginal() {
		return recaudoOriginal;
	}

	/**
	 * @param recaudoOriginal
	 *            the recaudoOriginal to set
	 */
	public void setRecaudoOriginal(String recaudoOriginal) {
		this.recaudoOriginal = recaudoOriginal;
	}

	/**
	 * @return the monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @param monto
	 *            the monto to set
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * @return the interes
	 */
	public BigDecimal getInteres() {
		return interes;
	}

	/**
	 * @param interes
	 *            the interes to set
	 */
	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	/**
	 * @return the totalAporte
	 */
	public BigDecimal getTotalAporte() {
		return totalAporte;
	}

	/**
	 * @param totalAporte
	 *            the totalAporte to set
	 */
	public void setTotalAporte(BigDecimal totalAporte) {
		this.totalAporte = totalAporte;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the destinatario
	 */
	public DestinatarioDevolucionEnum getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario
	 *            the destinatario to set
	 */
	public void setDestinatario(DestinatarioDevolucionEnum destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the valorGastosBancarios
	 */
	public BigDecimal getValorGastosBancarios() {
		return valorGastosBancarios;
	}

	/**
	 * @param valorGastosBancarios
	 *            the valorGastosBancarios to set
	 */
	public void setValorGastosBancarios(BigDecimal valorGastosBancarios) {
		this.valorGastosBancarios = valorGastosBancarios;
	}

	/**
	 * @return the fechaReconocimiento
	 */
	public Date getFechaReconocimiento() {
		return fechaReconocimiento;
	}

	/**
	 * @param fechaReconocimiento
	 *            the fechaReconocimiento to set
	 */
	public void setFechaReconocimiento(Date fechaReconocimiento) {
		this.fechaReconocimiento = fechaReconocimiento;
	}

	/**
	 * @return the fechaReconocimiento
	 */
	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	/**
	 * @param fechaReconocimiento
	 *            the fechaReconocimiento to set
	 */
	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}

	/**
	 * @return the fechaRecaudoCorreccion
	 */
	public Date getFechaRecaudoCorreccion() {
		return fechaRecaudoCorreccion;
	}

	/**
	 * @param fechaRecaudoCorreccion
	 *            the fechaRecaudoCorreccion to set
	 */
	public void setFechaRecaudoCorreccion(Date fechaRecaudoCorreccion) {
		this.fechaRecaudoCorreccion = fechaRecaudoCorreccion;
	}

	/**
	 * @return the numeroOperacionCorreccion
	 */
	public Long getNumeroOperacionCorreccion() {
		return numeroOperacionCorreccion;
	}

	/**
	 * @param numeroOperacionCorreccion
	 *            the numeroOperacionCorreccion to set
	 */
	public void setNumeroOperacionCorreccion(Long numeroOperacionCorreccion) {
		this.numeroOperacionCorreccion = numeroOperacionCorreccion;
	}

	/**
	 * @return the modalidadRecaudo
	 */
	public ModalidadRecaudoAporteEnum getModalidadRecaudo() {
		return modalidadRecaudo;
	}

	/**
	 * @param modalidadRecaudo
	 *            the modalidadRecaudo to set
	 */
	public void setModalidadRecaudo(ModalidadRecaudoAporteEnum modalidadRecaudo) {
		this.modalidadRecaudo = modalidadRecaudo;
	}

	/**
	 * @return the idRegistroGeneral
	 */
	public Long getIdRegistroGeneral() {
		return idRegistroGeneral;
	}

	/**
	 * @param idRegistroGeneral
	 *            the idRegistroGeneral to set
	 */
	public void setIdRegistroGeneral(Long idRegistroGeneral) {
		this.idRegistroGeneral = idRegistroGeneral;
	}


	public String getOportunidadDePago() {
		return this.oportunidadDePago;
	}

	public void setOportunidadDePago(String oportunidadDePago) {
		this.oportunidadDePago = oportunidadDePago;
	}

	public String getEstadoAportante() {
		return this.estadoAportante;
	}

	public void setEstadoAportante(String estadoAportante) {
		this.estadoAportante = estadoAportante;
	}

	public String getCodigoBancoRecaudador() {
		return this.codigoBancoRecaudador;
	}

	public void setCodigoBancoRecaudador(String codigoBancoRecaudador) {
		this.codigoBancoRecaudador = codigoBancoRecaudador;
	}

	public String getCuentaBancariaRecaudo() {
		return this.cuentaBancariaRecaudo;
	}

	public void setCuentaBancariaRecaudo(String cuentaBancariaRecaudo) {
		this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;
	}

	public String getSucursalEmpresa() {
		return this.sucursalEmpresa;
	}

	public void setSucursalEmpresa(String sucursalEmpresa) {
		this.sucursalEmpresa = sucursalEmpresa;
	}

	public BigDecimal getTarifaAportante() {
		return this.tarifaAportante;
	}

	public void setTarifaAportante(BigDecimal tarifaAportante) {
		this.tarifaAportante = tarifaAportante;
	}


	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Date getFechaDevolucion() {
		return this.fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}


	public String getDigitoVerificacion() {
		return this.digitoVerificacion;
	}

	public void setDigitoVerificacion(String digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	public String getNumeroSolicitudDevolucion() {
		return this.numeroSolicitudDevolucion;
	}

	public void setNumeroSolicitudDevolucion(String numeroSolicitudDevolucion) {
		this.numeroSolicitudDevolucion = numeroSolicitudDevolucion;
	}

	public String getNumeroSolicitudCorreccion() {
		return this.numeroSolicitudCorreccion;
	}

	public void setNumeroSolicitudCorreccion(String numeroSolicitudCorreccion) {
		this.numeroSolicitudCorreccion = numeroSolicitudCorreccion;
	}

	public String getTipoPlanilla() {
		return this.tipoPlanilla;
	}

	public void setTipoPlanilla(String tipoPlanilla) {
		this.tipoPlanilla = tipoPlanilla;
	}
	
	public boolean getPagadorPorSiMismo() {
		return this.pagadorPorSiMismo;
	}

	public void setPagadorPorSiMismo(boolean pagadorPorSiMismo) {
		this.pagadorPorSiMismo = pagadorPorSiMismo;
	}

	public TipoIdentificacionEnum getTipoIdentificacionTercero() {
		return this.tipoIdentificacionTercero;
	}

	public void setTipoIdentificacionTercero(TipoIdentificacionEnum tipoIdentificacionTercero) {
		this.tipoIdentificacionTercero = tipoIdentificacionTercero;
	}
	public String getNumeroIdentificacionTercero(){
		return this.numeroIdentificacionTercero;
	}

	public void setNumeroIdentificacionTercero(String numeroIdentificacionTercero) {
		this.numeroIdentificacionTercero = numeroIdentificacionTercero;
	}

	public String getRazonSocialtercero(){
		return this.razonSocialtercero;
	}

	public void setRazonSocialtercero(String razonSocialtercero) {
		this.razonSocialtercero = razonSocialtercero;
	}

	public String getMotivoDeRetiro(){
		return this.motivoDeRetiro;
	}

	public void setMotivoDeRetiro(String motivoDeRetiro) {
		this.motivoDeRetiro = motivoDeRetiro;
	}




	@Override
    public String toString()
    {
      return ToStringBuilder.reflectionToString(this);
    }

}