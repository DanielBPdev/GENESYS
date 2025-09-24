package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class ParametrosGestionCobroManualDTO implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 350774521018346078L;

	/**
	 * Lista con los estados de Solicitud de gestión de cobro manual
	 */
	private List<EstadoFiscalizacionEnum> estadosFiscalizacion;

	/**
	 * Lista con los tipos de solicitantes
	 */
	private List<TipoSolicitanteMovimientoAporteEnum> tipoSolicitantes;

	/**
	 * Lista con los identificadores de persona
	 */
	private List<Long> idsPersonas;

	/**
	 * Indica si el usuario tiene rol Supervisor Cartera
	 */
	private Boolean esSupervisor;

	/**
	 * @return the estadosFiscalizacion
	 */
	public List<EstadoFiscalizacionEnum> getEstadosFiscalizacion() {
		return estadosFiscalizacion;
	}

	/**
	 * @param estadosFiscalizacion
	 *            the estadosFiscalizacion to set
	 */
	public void setEstadosFiscalizacion(List<EstadoFiscalizacionEnum> estadosFiscalizacion) {
		this.estadosFiscalizacion = estadosFiscalizacion;
	}

	/**
	 * @return the tipoSolicitantes
	 */
	public List<TipoSolicitanteMovimientoAporteEnum> getTipoSolicitantes() {
		return tipoSolicitantes;
	}

	/**
	 * @param tipoSolicitantes
	 *            the tipoSolicitantes to set
	 */
	public void setTipoSolicitantes(List<TipoSolicitanteMovimientoAporteEnum> tipoSolicitantes) {
		this.tipoSolicitantes = tipoSolicitantes;
	}

	/**
	 * @return the idsPersonas
	 */
	public List<Long> getIdsPersonas() {
		return idsPersonas;
	}

	/**
	 * @param idsPersonas
	 *            the idsPersonas to set
	 */
	public void setIdsPersonas(List<Long> idsPersonas) {
		this.idsPersonas = idsPersonas;
	}

	/**
	 * Obtiene el valor de esSupervisor
	 * 
	 * @return El valor de esSupervisor
	 */
	public Boolean getEsSupervisor() {
		return esSupervisor;
	}

	/**
	 * Establece el valor de esSupervisor
	 * 
	 * @param esSupervisor
	 *            El valor de esSupervisor por asignar
	 */
	public void setEsSupervisor(Boolean esSupervisor) {
		this.esSupervisor = esSupervisor;
	}
}
