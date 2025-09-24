package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información de la cuenta del administrador de subsidio <br/>
 * <b>Módulo:</b> Asopagos - HU-TRA-001<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo</a>
 */
@XmlRootElement
public class VistaRetirosSubsidioDTO implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -8739778805610614015L;

    /** Identificador del detalle de subsidio original */
    private Long idSubsidioAsignado;
    
    /** valor pendiente de pago del subsidio original */
    private BigDecimal valorPendiente;
    
    /** Listado de las cuentas relacionadoas */
    private List<CuentaAdministradorSubsidioDTO> cuentasAdministradorSubsidio;

	/**
	 * @return the idSubsidioAsignado
	 */
	public Long getIdSubsidioAsignado() {
		return idSubsidioAsignado;
	}

	/**
	 * @param idSubsidioAsignado the idSubsidioAsignado to set
	 */
	public void setIdSubsidioAsignado(Long idSubsidioAsignado) {
		this.idSubsidioAsignado = idSubsidioAsignado;
	}

	/**
	 * @return the valorPendiente
	 */
	public BigDecimal getValorPendiente() {
		return valorPendiente;
	}

	/**
	 * @param valorPendiente the valorPendiente to set
	 */
	public void setValorPendiente(BigDecimal valorPendiente) {
		this.valorPendiente = valorPendiente;
	}

	/**
	 * @return the cuentasAdministradorSubsidio
	 */
	public List<CuentaAdministradorSubsidioDTO> getCuentasAdministradorSubsidio() {
		return cuentasAdministradorSubsidio;
	}

	/**
	 * @param cuentasAdministradorSubsidio the cuentasAdministradorSubsidio to set
	 */
	public void setCuentasAdministradorSubsidio(List<CuentaAdministradorSubsidioDTO> cuentasAdministradorSubsidio) {
		this.cuentasAdministradorSubsidio = cuentasAdministradorSubsidio;
	}
}
