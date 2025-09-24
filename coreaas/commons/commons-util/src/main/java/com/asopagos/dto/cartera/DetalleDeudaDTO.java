package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;

/**
 * DTO que contiene los datos de la Deuda
 * 
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 * @updated 27-Febr.-2018 04:38:50 p.m.
 */
@XmlRootElement
public class DetalleDeudaDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 305036566963167400L;
	/**
	 * Listado de deudas perteneciente a trabajadores activos
	 */
	private List<CarteraDependienteModeloDTO> lstTrabajadoresActivos;
	/**
	 * Listado de deudas perteneciente a trabajadores no activos
	 */
	private List<CarteraDependienteModeloDTO> lstTrabajadoresNoActivos;
	/**
	 * Listado de deudas perteneciente a trabajadores ingresado manualmente
	 */
	private List<CarteraDependienteModeloDTO> lstIngresoManual;

	/**
	 * Obtiene el valor de lstTrabajadoresActivos
	 * 
	 * @return El valor de lstTrabajadoresActivos
	 */
	public List<CarteraDependienteModeloDTO> getLstTrabajadoresActivos() {
		return lstTrabajadoresActivos;
	}

	/**
	 * Establece el valor de lstTrabajadoresActivos
	 * 
	 * @param lstTrabajadoresActivos
	 *            El valor de lstTrabajadoresActivos por asignar
	 */
	public void setLstTrabajadoresActivos(List<CarteraDependienteModeloDTO> lstTrabajadoresActivos) {
		this.lstTrabajadoresActivos = lstTrabajadoresActivos;
	}

	/**
	 * Obtiene el valor de lstTrabajadoresNoActivos
	 * 
	 * @return El valor de lstTrabajadoresNoActivos
	 */
	public List<CarteraDependienteModeloDTO> getLstTrabajadoresNoActivos() {
		return lstTrabajadoresNoActivos;
	}

	/**
	 * Establece el valor de lstTrabajadoresNoActivos
	 * 
	 * @param lstTrabajadoresNoActivos
	 *            El valor de lstTrabajadoresNoActivos por asignar
	 */
	public void setLstTrabajadoresNoActivos(List<CarteraDependienteModeloDTO> lstTrabajadoresNoActivos) {
		this.lstTrabajadoresNoActivos = lstTrabajadoresNoActivos;
	}

	/**
	 * Obtiene el valor de lstIngresoManual
	 * 
	 * @return El valor de lstIngresoManual
	 */
	public List<CarteraDependienteModeloDTO> getLstIngresoManual() {
		return lstIngresoManual;
	}

	/**
	 * Establece el valor de lstIngresoManual
	 * 
	 * @param lstIngresoManual
	 *            El valor de lstIngresoManual por asignar
	 */
	public void setLstIngresoManual(List<CarteraDependienteModeloDTO> lstIngresoManual) {
		this.lstIngresoManual = lstIngresoManual;
	}
}