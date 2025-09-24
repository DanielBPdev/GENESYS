package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.ActividadCarteraModeloDTO;
import com.asopagos.dto.modelo.AgendaCarteraModeloDTO;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;

/**
 * DTO que contiene los datos de la gestión cobro manual para cartera
 * 
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 * @updated 27-Febr.-2018 12:28:50 p.m.
 */
@XmlRootElement
public class GestionCicloManualDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -655678878891L;
	/**
	 * Listado de las agendas pertenecientes a Cartera
	 */
	private List<AgendaCarteraModeloDTO> lstAgendasCartera;

	/**
	 * Actividades de cartera modelo DTO
	 */
	private List<ActividadCarteraModeloDTO> lstActividadesCartera;

	/**
	 * Estado de gestión manual
	 */
	private EstadoFiscalizacionEnum estadoGestionManual;

	/**
	 * Método que retorna el valor de lstAgendasCartera.
	 * 
	 * @return valor de lstAgendasCartera.
	 */
	public List<AgendaCarteraModeloDTO> getLstAgendasCartera() {
		return lstAgendasCartera;
	}

	/**
	 * Método que retorna el valor de lstActividadesCartera.
	 * 
	 * @return valor de lstActividadesCartera.
	 */
	public List<ActividadCarteraModeloDTO> getLstActividadesCartera() {
		return lstActividadesCartera;
	}

	/**
	 * Método encargado de modificar el valor de lstAgendasCartera.
	 * 
	 * @param valor
	 *            para modificar lstAgendasCartera.
	 */
	public void setLstAgendasCartera(List<AgendaCarteraModeloDTO> lstAgendasCartera) {
		this.lstAgendasCartera = lstAgendasCartera;
	}

	/**
	 * Método encargado de modificar el valor de lstActividadesCartera.
	 * 
	 * @param valor
	 *            para modificar lstActividadesCartera.
	 */
	public void setLstActividadesCartera(List<ActividadCarteraModeloDTO> lstActividadesCartera) {
		this.lstActividadesCartera = lstActividadesCartera;
	}

	/**
	 * Obtiene el valor de estadoGestionManual
	 * 
	 * @return El valor de estadoGestionManual
	 */
	public EstadoFiscalizacionEnum getEstadoGestionManual() {
		return estadoGestionManual;
	}

	/**
	 * Establece el valor de estadoGestionManual
	 * 
	 * @param estadoGestionManual
	 *            El valor de estadoGestionManual por asignar
	 */
	public void setEstadoGestionManual(EstadoFiscalizacionEnum estadoGestionManual) {
		this.estadoGestionManual = estadoGestionManual;
	}
}