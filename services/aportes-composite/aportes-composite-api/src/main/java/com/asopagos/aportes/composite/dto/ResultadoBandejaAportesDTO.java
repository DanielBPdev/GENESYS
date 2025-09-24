/**
 * 
 */
package com.asopagos.aportes.composite.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anbuitrago
 *
 */
public class ResultadoBandejaAportesDTO {
	private List<BandejaAportesTablaDTO> aprobar = new ArrayList<BandejaAportesTablaDTO>();
	private List<BandejaAportesTablaDTO> gestion = new ArrayList<BandejaAportesTablaDTO>();
	/**
	 * @return the aprobar
	 */
	public List<BandejaAportesTablaDTO> getAprobar() {
		return aprobar;
	}
	/**
	 * @param aprobar the aprobar to set
	 */
	public void setAprobar(List<BandejaAportesTablaDTO> aprobar) {
		this.aprobar = aprobar;
	}
	/**
	 * @return the gestion
	 */
	public List<BandejaAportesTablaDTO> getGestion() {
		return gestion;
	}
	/**
	 * @param gestion the gestion to set
	 */
	public void setGestion(List<BandejaAportesTablaDTO> gestion) {
		this.gestion = gestion;
	}


}
