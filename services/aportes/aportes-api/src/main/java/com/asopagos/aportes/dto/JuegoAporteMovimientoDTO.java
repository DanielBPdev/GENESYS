package com.asopagos.aportes.dto;

import java.io.Serializable;

import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contien el juego de aporte detallado y movimiento aporte que se 
 * desea persistir<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class JuegoAporteMovimientoDTO implements Serializable {
	private static final long serialVersionUID = -7755026242804425136L;
	
	/** Aporte detallado */
	private AporteDetalladoModeloDTO aporteDetallado;
	
	/** Movimiento aporte */
	private MovimientoAporteModeloDTO movimientoAporte;

	/**
	 * @return the aporteDetallado
	 */
	public AporteDetalladoModeloDTO getAporteDetallado() {
		return aporteDetallado;
	}

	/**
	 * @param aporteDetallado the aporteDetallado to set
	 */
	public void setAporteDetallado(AporteDetalladoModeloDTO aporteDetallado) {
		this.aporteDetallado = aporteDetallado;
	}

	/**
	 * @return the movimientoAporte
	 */
	public MovimientoAporteModeloDTO getMovimientoAporte() {
		return movimientoAporte;
	}

	/**
	 * @param movimientoAporte the movimientoAporte to set
	 */
	public void setMovimientoAporte(MovimientoAporteModeloDTO movimientoAporte) {
		this.movimientoAporte = movimientoAporte;
	}
}
