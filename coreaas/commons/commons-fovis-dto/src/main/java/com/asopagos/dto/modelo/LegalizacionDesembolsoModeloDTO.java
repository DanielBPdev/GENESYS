package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.entidades.ccf.fovis.LegalizacionDesembolso;
import com.asopagos.enumeraciones.fovis.FormaPagoEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * 
 * DTO que Contiene la información de la entidad Legalización Procesos FOVIS
 * 3.2.4
 * 
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class LegalizacionDesembolsoModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 8858122927407222038L;
	/**
	 * Identificador único de la LegalizacionDesembolso
	 */
	private Long idLegalizacionDesembolso;
	/**
	 * Asociación de la Forma de pago
	 */
	private FormaPagoEnum formaPago;
	/**
	 * Asociación del tipo de Medio de Pago
	 */
	private TipoMedioDePagoEnum tipoMedioPago;
	/**
	 * Valor a desembolsar de la solicitud
	 */
	private BigDecimal valorADesembolsar;
	/**
	 * Fecha limite de pago del desembolso
	 */
	private Long fechaLimitePago;
	/**
	 * Asociación de la visita
	 */
	private Long idVisita;
	/**
	 * Indicador de si el subsidio ha sido o no desembolsado
	 */
	private Boolean subsidioDesembolsado;

	/**
	 * Identificador del documento soporte del desembolso
	 */
	private String idDocumento;

	/**
	 * Fecha transferencia
	 */
	private Long fechaTransferencia;

	/**
	 * Observaciones
	 */
	private String observaciones;

	/**
	 * Valor a desembolsar de la solicitud
	 */
	private BigDecimal montoDesembolsado;
	
	/**
	 * Identificador del documento soporte del desembolso Back
	 */
	private String idDocumentoSoporteBack;
	
	/**
	 * Observaciones del Back
	 */
	private String observacionesBack;

	/**
	 * Constructor de ParametrizacionFOVISDTO.
	 */
	public LegalizacionDesembolsoModeloDTO() {
	}

	/**
	 * Constructor que recibe el Entity para mapear las propiedades de la clase.
	 */
	public LegalizacionDesembolsoModeloDTO(LegalizacionDesembolso legalizacionDesembolso) {
		copyEntityToDTO(legalizacionDesembolso);
	}

	/**
	 * Convierte el actual DTO en el entity equivalente.
	 * 
	 * @return Entity Legalizacion
	 */
	public LegalizacionDesembolso convertToEntity() {
		LegalizacionDesembolso legalizacionDesembolso = new LegalizacionDesembolso();
		legalizacionDesembolso.setIdLegalizacionDesembolso(this.getIdLegalizacionDesembolso());
		legalizacionDesembolso.setFormaPago(this.getFormaPago());
		legalizacionDesembolso
				.setFechaLimitePago(this.getFechaLimitePago() != null ? new Date(this.getFechaLimitePago()) : null);
		legalizacionDesembolso.setTipoMedioPago(this.getTipoMedioPago());
		legalizacionDesembolso.setValorADesembolsar(this.getValorADesembolsar());
		legalizacionDesembolso.setIdVisita(this.getIdVisita());
		legalizacionDesembolso.setSubsidioDesembolsado(this.getSubsidioDesembolsado());
		legalizacionDesembolso.setFechaTransferencia(
				this.getFechaTransferencia() != null ? new Date(this.getFechaTransferencia()) : null);
		legalizacionDesembolso.setObservaciones(this.getObservaciones());
		legalizacionDesembolso.setIdDocumento(this.getIdDocumento());
		legalizacionDesembolso.setMontoDesembolsado(this.getMontoDesembolsado());
		legalizacionDesembolso.setIdDocumentoSoporteBack(this.getIdDocumentoSoporteBack());
		legalizacionDesembolso.setObservacionesBack(this.getObservacionesBack());
		return legalizacionDesembolso;
	}

	/**
	 * Copia las propiedades del DTO actual al entity que llega por parámetro.
	 * 
	 * @param legalizacionDesembolso
	 * @return El Entity con las propiedades modificadas.
	 */
	public LegalizacionDesembolso copyDTOToEntity(LegalizacionDesembolso legalizacionDesembolso) {
		if (this.getIdLegalizacionDesembolso() != null) {
			legalizacionDesembolso.setIdLegalizacionDesembolso(this.getIdLegalizacionDesembolso());
		}
		if (this.getFechaLimitePago() != null) {
			legalizacionDesembolso.setFechaLimitePago(new Date(this.getFechaLimitePago()));
		}
		if (this.getFormaPago() != null) {
			legalizacionDesembolso.setFormaPago(this.getFormaPago());
		}
		if (this.getTipoMedioPago() != null) {
			legalizacionDesembolso.setTipoMedioPago(this.getTipoMedioPago());
		}
		if (this.getValorADesembolsar() != null) {
			legalizacionDesembolso.setValorADesembolsar(this.getValorADesembolsar());
		}
		if (this.getIdVisita() != null) {
			legalizacionDesembolso.setIdVisita(this.getIdVisita());
		}
		if (this.getSubsidioDesembolsado() != null) {
			legalizacionDesembolso.setSubsidioDesembolsado(this.getSubsidioDesembolsado());
		}
		if (this.getFechaTransferencia() != null) {
			legalizacionDesembolso.setFechaTransferencia(new Date(this.getFechaTransferencia()));
		}
		if (this.getIdDocumento() != null) {
			legalizacionDesembolso.setIdDocumento(this.getIdDocumento());
		}
		if (this.getMontoDesembolsado() != null) {
			legalizacionDesembolso.setMontoDesembolsado(this.getMontoDesembolsado());
		}
		if (this.getObservaciones() != null) {
			legalizacionDesembolso.setObservaciones(this.getObservaciones());
		}
		if (this.getObservacionesBack() != null) {
			legalizacionDesembolso.setObservacionesBack(this.getObservacionesBack());
		}
		if (this.getIdDocumentoSoporteBack() != null) {
			legalizacionDesembolso.setIdDocumentoSoporteBack(this.getIdDocumentoSoporteBack());
		}
		return legalizacionDesembolso;
	}

	/**
	 * Copia las propiedades del entity que llega por parámetro al actual DTO.
	 * 
	 * @param legalizacionDesembolso
	 */
	public void copyEntityToDTO(LegalizacionDesembolso legalizacionDesembolso) {
		if (legalizacionDesembolso.getIdLegalizacionDesembolso() != null) {
			this.setIdLegalizacionDesembolso(legalizacionDesembolso.getIdLegalizacionDesembolso());
		}
		if (legalizacionDesembolso.getFechaLimitePago() != null) {
			this.setFechaLimitePago(legalizacionDesembolso.getFechaLimitePago().getTime());
		}
		if (legalizacionDesembolso.getFormaPago() != null) {
			this.setFormaPago(legalizacionDesembolso.getFormaPago());
		}
		if (legalizacionDesembolso.getTipoMedioPago() != null) {
			this.setTipoMedioPago(legalizacionDesembolso.getTipoMedioPago());
		}
		if (legalizacionDesembolso.getValorADesembolsar() != null) {
			this.setValorADesembolsar(legalizacionDesembolso.getValorADesembolsar());
		}
		if (legalizacionDesembolso.getIdVisita() != null) {
			this.setIdVisita(legalizacionDesembolso.getIdVisita());
		}
		if (legalizacionDesembolso.getSubsidioDesembolsado() != null) {
			this.setSubsidioDesembolsado(legalizacionDesembolso.getSubsidioDesembolsado());
		}
		if (legalizacionDesembolso.getFechaTransferencia() != null) {
			this.setFechaTransferencia(legalizacionDesembolso.getFechaTransferencia().getTime());
		}
		if (legalizacionDesembolso.getIdDocumento() != null) {
			this.setIdDocumento(legalizacionDesembolso.getIdDocumento());
		}
		if (legalizacionDesembolso.getMontoDesembolsado() != null) {
			this.setMontoDesembolsado(legalizacionDesembolso.getMontoDesembolsado());
		}
		if (legalizacionDesembolso.getObservaciones() != null) {
			this.setObservaciones(legalizacionDesembolso.getObservaciones());
		}		
		if (legalizacionDesembolso.getObservacionesBack() != null) {
			this.setObservacionesBack(legalizacionDesembolso.getObservacionesBack());
		}
		if (legalizacionDesembolso.getIdDocumentoSoporteBack() != null) {
			this.setIdDocumentoSoporteBack(legalizacionDesembolso.getIdDocumentoSoporteBack());
		}
	}

	/**
	 * @return the idLegalizacionDesembolso
	 */
	public Long getIdLegalizacionDesembolso() {
		return idLegalizacionDesembolso;
	}

	/**
	 * @param idLegalizacionDesembolso
	 *            the idLegalizacionDesembolso to set
	 */
	public void setIdLegalizacionDesembolso(Long idLegalizacionDesembolso) {
		this.idLegalizacionDesembolso = idLegalizacionDesembolso;
	}

	/**
	 * @return the formaPago
	 */
	public FormaPagoEnum getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago
	 *            the formaPago to set
	 */
	public void setFormaPago(FormaPagoEnum formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @return the tipoMedioPago
	 */
	public TipoMedioDePagoEnum getTipoMedioPago() {
		return tipoMedioPago;
	}

	/**
	 * @param tipoMedioPago
	 *            the tipoMedioPago to set
	 */
	public void setTipoMedioPago(TipoMedioDePagoEnum tipoMedioPago) {
		this.tipoMedioPago = tipoMedioPago;
	}

	/**
	 * @return the valorADesembolsar
	 */
	public BigDecimal getValorADesembolsar() {
		return valorADesembolsar;
	}

	/**
	 * @param valorADesembolsar
	 *            the valorADesembolsar to set
	 */
	public void setValorADesembolsar(BigDecimal valorADesembolsar) {
		this.valorADesembolsar = valorADesembolsar;
	}

	/**
	 * @return the fechaLimitePago
	 */
	public Long getFechaLimitePago() {
		return fechaLimitePago;
	}

	/**
	 * @param fechaLimitePago
	 *            the fechaLimitePago to set
	 */
	public void setFechaLimitePago(Long fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}

	/**
	 * @return the idVisita
	 */
	public Long getIdVisita() {
		return idVisita;
	}

	/**
	 * @param idVisita
	 *            the idVisita to set
	 */
	public void setIdVisita(Long idVisita) {
		this.idVisita = idVisita;
	}

	/**
	 * @return the subsidioDesembolsado
	 */
	public Boolean getSubsidioDesembolsado() {
		return subsidioDesembolsado;
	}

	/**
	 * @param subsidioDesembolsado
	 *            the subsidioDesembolsado to set
	 */
	public void setSubsidioDesembolsado(Boolean subsidioDesembolsado) {
		this.subsidioDesembolsado = subsidioDesembolsado;
	}

	/**
	 * @return the idDocumento
	 */
	public String getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento
	 *            the idDocumento to set
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the fechaTransferencia
	 */
	public Long getFechaTransferencia() {
		return fechaTransferencia;
	}

	/**
	 * @param fechaTransferencia
	 *            the fechaTransferencia to set
	 */
	public void setFechaTransferencia(Long fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the montoDesembolsado
	 */
	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}

	/**
	 * @param montoDesembolsado
	 *            the montoDesembolsado to set
	 */
	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}

	/**
	 * @return the idDocumentoSoporteBack
	 */
	public String getIdDocumentoSoporteBack() {
		return idDocumentoSoporteBack;
	}

	/**
	 * @param idDocumentoSoporteBack the idDocumentoSoporteBack to set
	 */
	public void setIdDocumentoSoporteBack(String idDocumentoSoporteBack) {
		this.idDocumentoSoporteBack = idDocumentoSoporteBack;
	}

	/**
	 * @return the observacionesBack
	 */
	public String getObservacionesBack() {
		return observacionesBack;
	}

	/**
	 * @param observacionesBack the observacionesBack to set
	 */
	public void setObservacionesBack(String observacionesBack) {
		this.observacionesBack = observacionesBack;
	}

}
