package com.asopagos.aportes.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que contiene información básica de la planilla <br/>
 * <b>Módulo:</b> Asopagos - HU-211-400 <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */
public class InformacionPlanillasRegistrarProcesarDTO implements Serializable {
	private static final long serialVersionUID = 623667065854215914L;

	/** Cantidad de aportes temporales a persistir en core */
	private Integer cantidadTemAportes;

	/** Cantidad de novedades temporales a persistir en core */
	private Integer cantidadTemNovedad;

	/** Identificador de la planilla */
	private Long registroGeneral;
	
	/** Constructor por defecto */
	public InformacionPlanillasRegistrarProcesarDTO(){
	    super();
	}

	/** Constructor para consulta JPA (Aportes) */
	public InformacionPlanillasRegistrarProcesarDTO(Long registroGeneral, Integer cantidadTemAportes) {
		this.registroGeneral = registroGeneral;
		this.cantidadTemAportes = cantidadTemAportes;
	}

	/** Constructor para consulta JPA (novedades) */
	public InformacionPlanillasRegistrarProcesarDTO(Integer cantidadTemNovedad, Long registroGeneral) {
		this.registroGeneral = registroGeneral;
		this.cantidadTemNovedad = cantidadTemNovedad;
	}

	/**
	 * @return the cantidadTemAportes
	 */
	public Integer getCantidadTemAportes() {
		return cantidadTemAportes;
	}

	/**
	 * @param cantidadTemAportes
	 *            the cantidadTemAportes to set
	 */
	public void setCantidadTemAportes(Integer cantidadTemAportes) {
		this.cantidadTemAportes = cantidadTemAportes;
	}

	/**
	 * @return the registroGeneral
	 */
	public Long getRegistroGeneral() {
		return registroGeneral;
	}

	/**
	 * @param registroGeneral
	 *            the registroGeneral to set
	 */
	public void setRegistroGeneral(Long registroGeneral) {
		this.registroGeneral = registroGeneral;
	}

	/**
	 * @return the cantidadTemNovedad
	 */
	public Integer getCantidadTemNovedad() {
		return cantidadTemNovedad;
	}

	/**
	 * @param cantidadTemNovedad the cantidadTemNovedad to set
	 */
	public void setCantidadTemNovedad(Integer cantidadTemNovedad) {
		this.cantidadTemNovedad = cantidadTemNovedad;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(cantidadTemAportes != null){
			return "InformacionPlanillasRegistrarProcesarDTO [cantidadTemAportes = " + cantidadTemAportes
					+ ", registroGeneral=" + registroGeneral + "]";
		}else{
			return "InformacionPlanillasRegistrarProcesarDTO [cantidadTemNovedad = " + cantidadTemNovedad
					+ ", registroGeneral=" + registroGeneral + "]";
		}
		
	}
	
	
}
