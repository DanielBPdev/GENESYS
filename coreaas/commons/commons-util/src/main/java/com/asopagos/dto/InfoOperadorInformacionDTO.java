package com.asopagos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.transversal.core.CajaCompensacion;

@XmlRootElement
public class InfoOperadorInformacionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OperadorInformacion operadorInformacion;
	private CajaCompensacion cajaCompensacion;
	
	
	public InfoOperadorInformacionDTO() {
	}
	
	public InfoOperadorInformacionDTO(OperadorInformacion operadorInformacion, CajaCompensacion cajaCompensacion) {
		this.operadorInformacion = operadorInformacion;
		this.cajaCompensacion = cajaCompensacion;
	}
	/**
	 * @return the operadorInformacion
	 */
	public OperadorInformacion getOperadorInformacion() {
		return operadorInformacion;
	}
	/**
	 * @param operadorInformacion the operadorInformacion to set
	 */
	public void setOperadorInformacion(OperadorInformacion operadorInformacion) {
		this.operadorInformacion = operadorInformacion;
	}
	/**
	 * @return the cajaCompensacion
	 */
	public CajaCompensacion getCajaCompensacion() {
		return cajaCompensacion;
	}
	/**
	 * @param cajaCompensacion the cajaCompensacion to set
	 */
	public void setCajaCompensacion(CajaCompensacion cajaCompensacion) {
		this.cajaCompensacion = cajaCompensacion;
	}
	
	

}
