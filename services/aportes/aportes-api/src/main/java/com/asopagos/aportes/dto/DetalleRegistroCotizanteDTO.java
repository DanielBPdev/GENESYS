package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
public class DetalleRegistroCotizanteDTO implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 8933813641189306391L;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo a nivel 2 del detallado o cotizante
	 */
	private Long numeroOperacion;

	/**
	 * Descripción del estado del registro a nivel de Aportante
	 */
	private EstadoRegistroAporteEnum tipoRegistro;

	/**
	 * Tipo de identificacion del cotizante
	 */
	private TipoIdentificacionEnum tipoIdentificacionCotizante;

	/**
	 * Número de Identificación de cotizante
	 */
	private String numeroIdentificacionCotizante;

	/**
	 * Primer nombre del cotizante.
	 */
	private String primerNombre;
	/**
	 * Segundo nombre del cotizante.
	 */
	private String segundoNombre;
	/**
	 * Primer apellido del cotizante.
	 */
	private String primerApellido;
	/**
	 * Segundo apellido del cotizante.
	 */
	private String segundoApellido;

	/**
	 * Período de pago
	 */
	private String periodoAporte;

	/**
	 * Fecha de recaudo del aporte
	 */
	private Date fechaRecaudo;

    /**
     * Aporte de registro
     */
    private BigDecimal tarifa;

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
	 * Fecha de la novedad
	 */
	private Date fechaNovedad;

	/**
	 * Tipo de transacción si la correccion es positiva fue la creada a partir
	 * de un aporte existente si es negativa es por que el aporte se le anularon
	 * sus valores
	 */
	private String tipoTransaccion;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo de la correccion a segundo nivel
	 */
	private Long numeroOperacionCorreccion;

	/**
	 * ID del aporte general al que está relacionado
	 */
	private Long idAporteGeneral;

	private String numPlanilla;

	private Boolean pagadorPorSiMismo ;
	
	private TipoIdentificacionEnum tipoIdentificacionTercero;

	private String numeroIdentificacionTercero;
	
	private String razonSocialtercero;

	private Date fechaProcesamiento;

	/** Constructor por defecto */
	public DetalleRegistroCotizanteDTO() {
	}

	/**
	 * Constructor para la consulta de aportes, devoluciones y legalizaciones
	 */
	public DetalleRegistroCotizanteDTO(Long numeroOperacion, String tipoRegistro, String tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String periodoAporte, Date fechaRecaudo, BigDecimal tarifa, BigDecimal monto, BigDecimal interes,
			BigDecimal totalAporte, String usuario, Long idAporteGeneral) {
		this.numeroOperacion = numeroOperacion;
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionCotizante = TipoIdentificacionEnum.valueOf(tipoIdentificacionCotizante);
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.periodoAporte = periodoAporte;
		this.fechaRecaudo = fechaRecaudo;
		this.tarifa = tarifa;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.usuario = usuario;
		this.fechaNovedad = fechaRecaudo;
		this.idAporteGeneral = idAporteGeneral;
	}
	public DetalleRegistroCotizanteDTO(Long numeroOperacion, String tipoRegistro, String tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String periodoAporte, Date fechaRecaudo, BigDecimal tarifa, BigDecimal monto, BigDecimal interes,
			BigDecimal totalAporte, String usuario, Long idAporteGeneral,Date fechaProcesamiento,String pagadorPorSiMismo, String tipoIdentificacionTercero,
			String numeroIdentificacionTercero, String razonSocialtercero) {
		this.numeroOperacion = numeroOperacion;
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionCotizante = TipoIdentificacionEnum.valueOf(tipoIdentificacionCotizante);
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.periodoAporte = periodoAporte;
		this.fechaRecaudo = fechaRecaudo;
		this.tarifa = tarifa;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.usuario = usuario;
		this.fechaNovedad = fechaRecaudo;
		this.idAporteGeneral = idAporteGeneral;
		this.fechaProcesamiento = fechaProcesamiento;
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

	/** Constructor para la consulta de correcciones */
	public DetalleRegistroCotizanteDTO(Long numeroOperacion, String tipoRegistro, String tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String periodoAporte, Date fechaRecaudo, String tipoTransaccion, BigDecimal tarifa, BigDecimal monto,
			BigDecimal interes, BigDecimal totalAporte, Long numeroOperacionCorreccion, Long idAporteGeneral, String usuario,
			String pagadorPorSiMismo, String tipoIdentificacionTercero,String numeroIdentificacionTercero, String razonSocialtercero) {
		this.numeroOperacion = numeroOperacion;
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionCotizante = TipoIdentificacionEnum.valueOf(tipoIdentificacionCotizante);
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.periodoAporte = periodoAporte;
		this.fechaRecaudo = fechaRecaudo;
		this.tipoTransaccion = tipoTransaccion;
		this.tarifa = tarifa;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.numeroOperacionCorreccion = numeroOperacionCorreccion == null ? 0L : numeroOperacionCorreccion;
		this.idAporteGeneral = idAporteGeneral;

		this.fechaNovedad = fechaRecaudo;
		this.usuario = usuario;
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
		public DetalleRegistroCotizanteDTO(Long numeroOperacion, String tipoRegistro, String tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String periodoAporte, Date fechaRecaudo, String tipoTransaccion, BigDecimal tarifa, BigDecimal monto,
			BigDecimal interes, BigDecimal totalAporte, Long numeroOperacionCorreccion, Long idAporteGeneral, String usuario,String numPlanilla,
			String pagadorPorSiMismo, String tipoIdentificacionTercero,
			String numeroIdentificacionTercero, String razonSocialtercero) {
		this.numeroOperacion = numeroOperacion;
		this.tipoRegistro = EstadoRegistroAporteEnum.valueOf(tipoRegistro);
		this.tipoIdentificacionCotizante = TipoIdentificacionEnum.valueOf(tipoIdentificacionCotizante);
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.periodoAporte = periodoAporte;
		this.fechaRecaudo = fechaRecaudo;
		this.tipoTransaccion = tipoTransaccion;
		this.tarifa = tarifa;
		this.monto = monto;
		this.interes = interes;
		this.totalAporte = totalAporte;
		this.numeroOperacionCorreccion = numeroOperacionCorreccion == null ? 0L : numeroOperacionCorreccion;
		this.idAporteGeneral = idAporteGeneral;

		this.fechaNovedad = fechaRecaudo;
		this.usuario = usuario;
		this.numPlanilla = numPlanilla;
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
	 * @return the tipoIdentificacionCotizante
	 */
	public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
		return tipoIdentificacionCotizante;
	}

	/**
	 * @param tipoIdentificacionCotizante
	 *            the tipoIdentificacionCotizante to set
	 */
	public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
	}

	/**
	 * @return the numeroIdentificacionCotizante
	 */
	public String getNumeroIdentificacionCotizante() {
		return numeroIdentificacionCotizante;
	}

	/**
	 * @param numeroIdentificacionCotizante
	 *            the numeroIdentificacionCotizante to set
	 */
	public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
	}

	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * @param primerNombre
	 *            the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * @param segundoNombre
	 *            the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido
	 *            the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido
	 *            the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
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
	 * @return the fechaNovedad
	 */
	public Date getFechaNovedad() {
		return fechaNovedad;
	}

	/**
	 * @param fechaNovedad
	 *            the fechaNovedad to set
	 */
	public void setFechaNovedad(Date fechaNovedad) {
		this.fechaNovedad = fechaNovedad;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion
	 *            the tipoTransaccion to set
	 */
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
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
	 * @return the idAporteGeneral
	 */
	public Long getIdAporteGeneral() {
		return idAporteGeneral;
	}

	/**
	 * @param idAporteGeneral
	 *            the idAporteGeneral to set
	 */
	public void setIdAporteGeneral(Long idAporteGeneral) {
		this.idAporteGeneral = idAporteGeneral;
	}

    /**
     * @return the tarifa
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa the tarifa to set
     */
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

	public String getNumPlanilla() {
		return this.numPlanilla;
	}

	public void setNumPlanilla(String numPlanilla) {
		this.numPlanilla = numPlanilla;

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

	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}
}