package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> DTO que contiene la información de detalle para el
 * resultado de la consulta de una liquidación para las vistas 360<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - Vista 360 empleador<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo</a>
 */
public class ConsultaValidacionesLiquidacionSubsidioMonetarioDTO implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;

	/** validaciones del camino del empleador */
	private List<ConsultaLiquidacionSubsidioMonetarioDTO> validacionesEmpleador;
	
	/** validaciones del camino del trabajador */
	private List<ConsultaLiquidacionSubsidioMonetarioDTO> validacionesTrabajador;

	/**
	 * @return the validacionesEmpleador
	 */
	public List<ConsultaLiquidacionSubsidioMonetarioDTO> getValidacionesEmpleador() {
		return validacionesEmpleador;
	}

	/**
	 * @param validacionesEmpleador the validacionesEmpleador to set
	 */
	public void setValidacionesEmpleador(List<ConsultaLiquidacionSubsidioMonetarioDTO> validacionesEmpleador) {
		this.validacionesEmpleador = validacionesEmpleador;
	}

	/**
	 * @return the validacionesTrabajador
	 */
	public List<ConsultaLiquidacionSubsidioMonetarioDTO> getValidacionesTrabajador() {
		return validacionesTrabajador;
	}

	/**
	 * @param validacionesTrabajador the validacionesTrabajador to set
	 */
	public void setValidacionesTrabajador(List<ConsultaLiquidacionSubsidioMonetarioDTO> validacionesTrabajador) {
		this.validacionesTrabajador = validacionesTrabajador;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConsultaValidacionesLiquidacionSubsidioMonetarioDTO [validacionesEmpleador=");
		builder.append(validacionesEmpleador);
		builder.append(", validacionesTrabajador=");
		builder.append(validacionesTrabajador);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
