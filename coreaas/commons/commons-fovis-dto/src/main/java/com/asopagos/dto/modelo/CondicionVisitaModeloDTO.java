package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CondicionVisita;
import com.asopagos.enumeraciones.fovis.CondicionesVerificadasEnum;

@XmlRootElement
public class CondicionVisitaModeloDTO implements Serializable{
	

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
	 * Llave unica
	 */
	 private Long idCondicionVisita;
	
	 /**
	  * Indicador S/N si la condición evaluada en la visita cumple con los parametros
      * establecidos por la caja de compensacion [S=Si N=No]
	  */
	 private boolean cumpleCondicion;
	 
	 /**
	  * Obsevaciones asociadas a la evaluacion de una condicion
      */
	 private String observaciones;
	 
	 /**
	  * Enumerado que indica la condicion a evaluar
	  */
	 private CondicionesVerificadasEnum condicion;
	 
	 /**
	  * Asociacion con una visita
	  */
	 private Long idVisita;
	 
	 /**
	  * Constructor por defecto
	  */
	 public CondicionVisitaModeloDTO() {
	 }
	 
	 /**
	  * Contructor a partir de una entidad CondicionVisita
	  * @param condicionVisita
	  */
	 public CondicionVisitaModeloDTO(CondicionVisita condicionVisita ){
		
		 this.setCondicion(condicionVisita.getCondicion());
		 this.setCumpleCondicion(condicionVisita.isCumpleCondicion());
		 this.setObservaciones(condicionVisita.getObservacion() != null ? condicionVisita.getObservacion() : null);
		 this.setIdVisita(condicionVisita.getIdVisita());
	}
	 
	 public CondicionVisita convertToEntity(){
		 
		 CondicionVisita condicionVisita = new CondicionVisita();
		 
		 condicionVisita.setIdCondicionVisita(this.idCondicionVisita);
		 condicionVisita.setCumpleCondicion(this.cumpleCondicion);
		 condicionVisita.setIdVisita(this.idVisita);
		 condicionVisita.setCondicion(this.condicion);
		 
		 if (this.observaciones != null) {
			 condicionVisita.setObservacion(this.observaciones);
		}
		 
		 return condicionVisita;
	 }

	/**
	 * @return the idCondicionVisita
	 */
	public Long getIdCondicionVisita() {
		return idCondicionVisita;
	}

	/**
	 * @param idCondicionVisita the idCondicionVisita to set
	 */
	public void setIdCondicionVisita(Long idCondicionVisita) {
		this.idCondicionVisita = idCondicionVisita;
	}

	/**
	 * @return the cumpleCondicion
	 */
	public boolean isCumpleCondicion() {
		return cumpleCondicion;
	}

	/**
	 * @param cumpleCondicion the cumpleCondicion to set
	 */
	public void setCumpleCondicion(boolean cumpleCondicion) {
		this.cumpleCondicion = cumpleCondicion;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the condicion
	 */
	public CondicionesVerificadasEnum getCondicion() {
		return condicion;
	}

	/**
	 * @param condicion the condicion to set
	 */
	public void setCondicion(CondicionesVerificadasEnum condicion) {
		this.condicion = condicion;
	}

	/**
	 * @return the idVisita
	 */
	public Long getIdVisita() {
		return idVisita;
	}

	/**
	 * @param idVisita the idVisita to set
	 */
	public void setIdVisita(Long idVisita) {
		this.idVisita = idVisita;
	}
	 
	 
}
