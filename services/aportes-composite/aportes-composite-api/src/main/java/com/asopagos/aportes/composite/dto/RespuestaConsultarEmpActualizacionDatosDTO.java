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
public class RespuestaConsultarEmpActualizacionDatosDTO {
	private ConsultasEmpresaPersonaDTO consulta;
	private List<EmpActualizacionDatosTablaDTO> listaActualizacion= new ArrayList<>();
	/**
	 * @return the consulta
	 */
	public ConsultasEmpresaPersonaDTO getConsulta() {
		return consulta;
	}
	/**
	 * @param consulta the consulta to set
	 */
	public void setConsulta(ConsultasEmpresaPersonaDTO consulta) {
		this.consulta = consulta;
	}
	/**
	 * @return the listaActualizacion
	 */
	public List<EmpActualizacionDatosTablaDTO> getListaActualizacion() {
		return listaActualizacion;
	}
	/**
	 * @param listaActualizacion the listaActualizacion to set
	 */
	public void setListaActualizacion(List<EmpActualizacionDatosTablaDTO> listaActualizacion) {
		this.listaActualizacion = listaActualizacion;
	}


}
