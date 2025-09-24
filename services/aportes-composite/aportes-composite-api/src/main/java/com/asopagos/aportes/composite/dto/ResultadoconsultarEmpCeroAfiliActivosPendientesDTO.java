/**
 * 
 */
package com.asopagos.aportes.composite.dto;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.dto.ConsultasEmpresaPersonaDTO;

/**
 * @author anbuitrago
 *
 */
public class ResultadoconsultarEmpCeroAfiliActivosPendientesDTO {

	private ConsultasEmpresaPersonaDTO consultaEmpPer;
	private List<EmpCeroAfilBandejaTablasDTO> listaEmpCeroAfil= new ArrayList<EmpCeroAfilBandejaTablasDTO>();
	/**
	 * @return the consultaEmpPer
	 */
	public ConsultasEmpresaPersonaDTO getConsultaEmpPer() {
		return consultaEmpPer;
	}
	/**
	 * @param consultaEmpPer the consultaEmpPer to set
	 */
	public void setConsultaEmpPer(ConsultasEmpresaPersonaDTO consultaEmpPer) {
		this.consultaEmpPer = consultaEmpPer;
	}
	/**
	 * @return the listaEmpCeroAfil
	 */
	public List<EmpCeroAfilBandejaTablasDTO> getListaEmpCeroAfil() {
		return listaEmpCeroAfil;
	}
	/**
	 * @param listaEmpCeroAfil the listaEmpCeroAfil to set
	 */
	public void setListaEmpCeroAfil(List<EmpCeroAfilBandejaTablasDTO> listaEmpCeroAfil) {
		this.listaEmpCeroAfil = listaEmpCeroAfil;
	}

	
	
}
