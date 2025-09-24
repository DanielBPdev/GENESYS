package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.aportes.DevolucionAporte;
import com.asopagos.enumeraciones.aportes.DestinatarioDevolucionEnum;
import com.asopagos.enumeraciones.aportes.MotivoPeticionEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos relacionados a una
 * devolución de aportes <br/>
 * <b>Historia de Usuario: </b> HU-485, HU-486
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class DevolucionAporteModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 6182110449735885709L;

	/**
	 * Identificador de la devolución
	 */
	private Long idDevolucionAporte;

	/**
	 * Fecha en la que se registra la solicitud de devolución
	 */
	private Long fechaRecepcion;

	/**
	 * Motivo por el cual se solicita devolución
	 */
	private MotivoPeticionEnum motivoPeticion;

	/**
	 * Destinatario de la devolución
	 */
	private DestinatarioDevolucionEnum destinatarioDevolucion;

	/**
	 * Cuando <code>destinatarioDevolucion=Otro</code>, este campo es
	 * diigenciado
	 */
	private String otroDestinatario;
	   /**
     * Cuando <code>cajaCompensacion=Otra (null)</code>, este campo es
     * diigenciado.
     */
    private String otraCaja;

    /**
     * Cuando <code>motivoPeticion=Otro</code>, este campo es
     * diigenciado.
     */
    private String otroMotivo;

	/**
	 * Monto de los aportes solicitados para devolución
	 */
	private BigDecimal montoAportes;

	/**
	 * Monto de intereses solicitados para devolución
	 */
	private BigDecimal montoIntereses;

	/**
	 * Determina los periodos reclamados para devolución, separados por |
	 */
	private String periodoReclamado;

	/**
	 * Descuentos por gestión de pago de operador de información. Un valor
	 * <code>null</code> en este campo, indica que este descuento no fue
	 * aplicado
	 */
	private BigDecimal descuentoGestionPagoOI;

	/**
	 * Descuentos por gestión financiera. Un valor <code>null</code> en este
	 * campo, indica que este descuento no fue aplicado
	 */
	private BigDecimal descuentoGestionFinanciera;

	/**
	 * Otros descuentos. Un valor <code>null</code> en este campo, indica que
	 * este descuento no fue aplicado
	 */
	private BigDecimal descuentoOtro;

	/**
	 * Identificador único de la CCF -> Referencia a la tabla
	 * <code>CajaCompensacion</code>
	 */
	private Integer cajaCompensacion;

	/**
	 * Forma de pago -> Referencia a la tabla <code>MedioDePago</code>
	 */
	private Long formaPago;

	/**
	 * Sede de la CCF -> Referencia a la tabla <code>SedeCajaCompensacion</code>
	 */
	private Long sedeCCF;

	/**
	 * Método que convierte una entidad <code>DevolucionAporte</code> en DTO
	 * 
	 * @param devolucionAporte
	 *            La entidad a convertir
	 */
	public void convertToDTO(DevolucionAporte devolucionAporte) {
		if (devolucionAporte.getFechaRecepcion() != null) {
			this.fechaRecepcion = devolucionAporte.getFechaRecepcion().getTime();
		}

		this.motivoPeticion = devolucionAporte.getMotivoPeticion();
		this.destinatarioDevolucion = devolucionAporte.getDestinatarioDevolucion();
		this.otroDestinatario = devolucionAporte.getOtroDestinatario();
		this.montoAportes = devolucionAporte.getMontoAportes();
		this.montoIntereses = devolucionAporte.getMontoIntereses();
		this.periodoReclamado = devolucionAporte.getPeriodoReclamado();
		this.descuentoGestionPagoOI = devolucionAporte.getDescuentoGestionPagoOI();
		this.descuentoGestionFinanciera = devolucionAporte.getDescuentoGestionFinanciera();
		this.descuentoOtro = devolucionAporte.getDescuentoOtro();
		this.cajaCompensacion = devolucionAporte.getCajaCompensacion();
		this.formaPago = devolucionAporte.getMedioPago();
		this.sedeCCF = devolucionAporte.getSedeCCF();
		this.otraCaja = devolucionAporte.getOtraCaja();
		this.otroMotivo = devolucionAporte.getOtroMotivo();
	}

	/**
	 * Método que convierte el DTO en una entidad <code>DevolucionAporte</code>
	 * 
	 * @return Una entidad <code>DevolucionAporte</code> con los datos
	 *         equivalentes
	 */
	public DevolucionAporte convertToEntity() {
		DevolucionAporte devolucionAporte = new DevolucionAporte();

		if (this.fechaRecepcion != null) {
			devolucionAporte.setFechaRecepcion(new Date(this.getFechaRecepcion()));
		}

		devolucionAporte.setMotivoPeticion(this.motivoPeticion);
		devolucionAporte.setDestinatarioDevolucion(this.destinatarioDevolucion);
		devolucionAporte.setOtroDestinatario(this.otroDestinatario);
		devolucionAporte.setMontoAportes(this.montoAportes);
		devolucionAporte.setMontoIntereses(this.montoIntereses);
		devolucionAporte.setPeriodoReclamado(this.periodoReclamado);
		devolucionAporte.setDescuentoGestionPagoOI(this.descuentoGestionPagoOI);
		devolucionAporte.setDescuentoGestionFinanciera(this.descuentoGestionFinanciera);
		devolucionAporte.setDescuentoOtro(this.descuentoOtro);
		devolucionAporte.setCajaCompensacion(this.cajaCompensacion);
		devolucionAporte.setMedioPago(this.formaPago);
		devolucionAporte.setSedeCCF(this.sedeCCF);
		devolucionAporte.setOtraCaja(this.otraCaja);
		devolucionAporte.setOtroMotivo(this.otroMotivo);

		return devolucionAporte;
	}

	/**
	 * Obtiene el valor de idDevolucionAporte
	 * 
	 * @return El valor de idDevolucionAporte
	 */
	public Long getIdDevolucionAporte() {
		return idDevolucionAporte;
	}

	/**
	 * Establece el valor de idDevolucionAporte
	 * 
	 * @param idDevolucionAporte
	 *            El valor de idDevolucionAporte por asignar
	 */
	public void setIdDevolucionAporte(Long idDevolucionAporte) {
		this.idDevolucionAporte = idDevolucionAporte;
	}

	/**
	 * Obtiene el valor de motivoPeticion
	 * 
	 * @return El valor de motivoPeticion
	 */
	public MotivoPeticionEnum getMotivoPeticion() {
		return motivoPeticion;
	}

	/**
	 * Establece el valor de motivoPeticion
	 * 
	 * @param motivoPeticion
	 *            El valor de motivoPeticion por asignar
	 */
	public void setMotivoPeticion(MotivoPeticionEnum motivoPeticion) {
		this.motivoPeticion = motivoPeticion;
	}

	/**
	 * Obtiene el valor de destinatarioDevolucion
	 * 
	 * @return El valor de destinatarioDevolucion
	 */
	public DestinatarioDevolucionEnum getDestinatarioDevolucion() {
		return destinatarioDevolucion;
	}

	/**
	 * Establece el valor de destinatarioDevolucion
	 * 
	 * @param destinatarioDevolucion
	 *            El valor de destinatarioDevolucion por asignar
	 */
	public void setDestinatarioDevolucion(DestinatarioDevolucionEnum destinatarioDevolucion) {
		this.destinatarioDevolucion = destinatarioDevolucion;
	}

	/**
	 * Obtiene el valor de otroDestinatario
	 * 
	 * @return El valor de otroDestinatario
	 */
	public String getOtroDestinatario() {
		return otroDestinatario;
	}

	/**
	 * Establece el valor de otroDestinatario
	 * 
	 * @param otroDestinatario
	 *            El valor de otroDestinatario por asignar
	 */
	public void setOtroDestinatario(String otroDestinatario) {
		this.otroDestinatario = otroDestinatario;
	}

	/**
     * Método que retorna el valor de otraCaja.
     * @return valor de otraCaja.
     */
    public String getOtraCaja() {
        return otraCaja;
    }

    /**
     * Método que retorna el valor de otroMotivo.
     * @return valor de otroMotivo.
     */
    public String getOtroMotivo() {
        return otroMotivo;
    }

    /**
     * Método encargado de modificar el valor de otraCaja.
     * @param valor para modificar otraCaja.
     */
    public void setOtraCaja(String otraCaja) {
        this.otraCaja = otraCaja;
    }

    /**
     * Método encargado de modificar el valor de otroMotivo.
     * @param valor para modificar otroMotivo.
     */
    public void setOtroMotivo(String otroMotivo) {
        this.otroMotivo = otroMotivo;
    }

    /**
	 * Obtiene el valor de montoAportes
	 * 
	 * @return El valor de montoAportes
	 */
	public BigDecimal getMontoAportes() {
		return montoAportes;
	}

	/**
	 * Establece el valor de montoAportes
	 * 
	 * @param montoAportes
	 *            El valor de montoAportes por asignar
	 */
	public void setMontoAportes(BigDecimal montoAportes) {
		this.montoAportes = montoAportes;
	}

	/**
	 * Obtiene el valor de montoIntereses
	 * 
	 * @return El valor de montoIntereses
	 */
	public BigDecimal getMontoIntereses() {
		return montoIntereses;
	}

	/**
	 * Establece el valor de montoIntereses
	 * 
	 * @param montoIntereses
	 *            El valor de montoIntereses por asignar
	 */
	public void setMontoIntereses(BigDecimal montoIntereses) {
		this.montoIntereses = montoIntereses;
	}

	/**
	 * Obtiene el valor de periodoReclamado
	 * 
	 * @return El valor de periodoReclamado
	 */
	public String getPeriodoReclamado() {
		return periodoReclamado;
	}

	/**
	 * Establece el valor de periodoReclamado
	 * 
	 * @param periodoReclamado
	 *            El valor de periodoReclamado por asignar
	 */
	public void setPeriodoReclamado(String periodoReclamado) {
		this.periodoReclamado = periodoReclamado;
	}

	/**
	 * Obtiene el valor de descuentoGestionPagoOI
	 * 
	 * @return El valor de descuentoGestionPagoOI
	 */
	public BigDecimal getDescuentoGestionPagoOI() {
		return descuentoGestionPagoOI;
	}

	/**
	 * Establece el valor de descuentoGestionPagoOI
	 * 
	 * @param descuentoGestionPagoOI
	 *            El valor de descuentoGestionPagoOI por asignar
	 */
	public void setDescuentoGestionPagoOI(BigDecimal descuentoGestionPagoOI) {
		this.descuentoGestionPagoOI = descuentoGestionPagoOI;
	}

	/**
	 * Obtiene el valor de descuentoGestionFinanciera
	 * 
	 * @return El valor de descuentoGestionFinanciera
	 */
	public BigDecimal getDescuentoGestionFinanciera() {
		return descuentoGestionFinanciera;
	}

	/**
	 * Establece el valor de descuentoGestionFinanciera
	 * 
	 * @param descuentoGestionFinanciera
	 *            El valor de descuentoGestionFinanciera por asignar
	 */
	public void setDescuentoGestionFinanciera(BigDecimal descuentoGestionFinanciera) {
		this.descuentoGestionFinanciera = descuentoGestionFinanciera;
	}

	/**
	 * Obtiene el valor de descuentoOtro
	 * 
	 * @return El valor de descuentoOtro
	 */
	public BigDecimal getDescuentoOtro() {
		return descuentoOtro;
	}

	/**
	 * Establece el valor de descuentoOtro
	 * 
	 * @param descuentoOtro
	 *            El valor de descuentoOtro por asignar
	 */
	public void setDescuentoOtro(BigDecimal descuentoOtro) {
		this.descuentoOtro = descuentoOtro;
	}

	/**
	 * Obtiene el valor de formaPago
	 * 
	 * @return El valor de formaPago
	 */
	public Long getFormaPago() {
		return formaPago;
	}

	/**
	 * Establece el valor de formaPago
	 * 
	 * @param formaPago
	 *            El valor de formaPago por asignar
	 */
	public void setFormaPago(Long formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * Obtiene el valor de sedeCCF
	 * 
	 * @return El valor de sedeCCF
	 */
	public Long getSedeCCF() {
		return sedeCCF;
	}

	/**
	 * Establece el valor de sedeCCF
	 * 
	 * @param sedeCCF
	 *            El valor de sedeCCF por asignar
	 */
	public void setSedeCCF(Long sedeCCF) {
		this.sedeCCF = sedeCCF;
	}

	/**
	 * Obtiene el valor de cajaCompensacion
	 * 
	 * @return El valor de cajaCompensacion
	 */
	public Integer getCajaCompensacion() {
		return cajaCompensacion;
	}

	/**
	 * Establece el valor de cajaCompensacion
	 * 
	 * @param cajaCompensacion
	 *            El valor de cajaCompensacion por asignar
	 */
	public void setCajaCompensacion(Integer cajaCompensacion) {
		this.cajaCompensacion = cajaCompensacion;
	}

	/**
	 * Obtiene el valor de fechaRecepcion
	 * 
	 * @return El valor de fechaRecepcion
	 */
	public Long getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * Establece el valor de fechaRecepcion
	 * 
	 * @param fechaRecepcion
	 *            El valor de fechaRecepcion por asignar
	 */
	public void setFechaRecepcion(Long fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
}
