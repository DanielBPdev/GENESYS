package com.asopagos.dto.modelo;

import java.io.Serializable;

import com.asopagos.entidades.ccf.cartera.CicloAportante;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * DTO que representa el modelo del ciclo del aportante
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class CicloAportanteModeloDTO implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -2346441530578706843L;
	/**
	 * identificador único del ciclo del aportante
	 */
	private Long idCicloAportante;
	/**
	 * Persona aportante.
	 */
	private Long idPersona;

	/**
	 * Identificador que relaciona el ciclo de fiscalización.
	 */
	private Long idCicloFiscalizacion;

	/**
	 * Identificador que relaciona la solicitudde fiscalización.
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitanteMovimientoAporteEnum;

	/**
	 * Campo que representa al analista
	 */
	private String analista;

	/**
	 * Método Constructor
	 */
	public CicloAportanteModeloDTO() {

	}

	/**
	 * Método encargado de convertir de DTO a entidad
	 * 
	 * @return CicloAportante
	 */
	public CicloAportante convertToEntity() {
		CicloAportante cicloAportante = new CicloAportante();
		cicloAportante.setIdCicloAportante(this.getIdCicloAportante());
		cicloAportante.setTipoSolicitanteMovimientoAporteEnum(this.getTipoSolicitanteMovimientoAporteEnum());
		cicloAportante.setIdCicloCartera(this.getIdCicloFiscalizacion());
		cicloAportante.setIdPersona(this.getIdPersona());
		return cicloAportante;
	}

	/**
	 * Método encargado de convertir de Entidad a DTO.
	 * 
	 * @param CicloAportante
	 *            entidad a convertir.
	 */
	public void convertToDTO(CicloAportante cicloAportante) {
		this.setIdCicloAportante(cicloAportante.getIdCicloAportante());
		this.setTipoSolicitanteMovimientoAporteEnum(cicloAportante.getTipoSolicitanteMovimientoAporteEnum());
		this.setIdCicloFiscalizacion(cicloAportante.getIdCicloCartera());
		this.setIdPersona(cicloAportante.getIdPersona());
	}

	/**
	 * @return the idCicloAportante
	 */
	public Long getIdCicloAportante() {
		return idCicloAportante;
	}

	/**
	 * @param idCicloAportante
	 *            the idCicloAportante to set
	 */
	public void setIdCicloAportante(Long idCicloAportante) {
		this.idCicloAportante = idCicloAportante;
	}

	/**
	 * Método que retorna el valor de idPersona.
	 * 
	 * @return valor de idPersona.
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Método encargado de modificar el valor de idPersona.
	 * 
	 * @param valor
	 *            para modificar idPersona.
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Método que retorna el valor de idCicloFiscalizacion.
	 * 
	 * @return valor de idCicloFiscalizacion.
	 */
	public Long getIdCicloFiscalizacion() {
		return idCicloFiscalizacion;
	}

	/**
	 * Método encargado de modificar el valor de idCicloFiscalizacion.
	 * 
	 * @param valor
	 *            para modificar idCicloFiscalizacion.
	 */
	public void setIdCicloFiscalizacion(Long idCicloFiscalizacion) {
		this.idCicloFiscalizacion = idCicloFiscalizacion;
	}

	/**
	 * Método que retorna el valor de tipoSolicitanteMovimientoAporteEnum.
	 * 
	 * @return valor de tipoSolicitanteMovimientoAporteEnum.
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitanteMovimientoAporteEnum() {
		return tipoSolicitanteMovimientoAporteEnum;
	}

	/**
	 * Método encargado de modificar el valor de
	 * tipoSolicitanteMovimientoAporteEnum.
	 * 
	 * @param valor
	 *            para modificar tipoSolicitanteMovimientoAporteEnum.
	 */
	public void setTipoSolicitanteMovimientoAporteEnum(TipoSolicitanteMovimientoAporteEnum tipoSolicitanteMovimientoAporteEnum) {
		this.tipoSolicitanteMovimientoAporteEnum = tipoSolicitanteMovimientoAporteEnum;
	}

	/**
	 * Método que retorna el valor de analista.
	 * 
	 * @return valor de analista.
	 */
	public String getAnalista() {
		return analista;
	}

	/**
	 * Método encargado de modificar el valor de analista.
	 * 
	 * @param valor
	 *            para modificar analista.
	 */
	public void setAnalista(String analista) {
		this.analista = analista;
	}

}
