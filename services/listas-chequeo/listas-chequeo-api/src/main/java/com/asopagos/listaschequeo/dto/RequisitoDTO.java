package com.asopagos.listaschequeo.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;

/**
 * <b>Descripción:</b> DTO para intercambio de requisitos por caja de 
 * compensación
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class RequisitoDTO implements Serializable {
    
    
	/**
	 * Código identificador de llave primaria del requisito 
	 */
    @NotNull(groups = {GrupoActualizacion.class})
	private Long idRequisito;

	/**
	 * Nombre del requisito
	 */
    @NotNull(groups = {GrupoCreacion.class, GrupoActualizacion.class})
	private String nombreRequisito;
    
	/**
	 * Estado del requisito. Valor por defecto habiliado
	 */
    @NotNull(groups = {GrupoActualizacion.class})
	private HabilitadoInhabilitadoEnum estado;
    
    /**
   	 * Constructor de la clase
   	 */
       public RequisitoDTO(){}
    
    /**
	 * Constructor de la clase
	 * 
	 * @param idRequisito
	 * @param nombreRequisito
	 * @param estado
	 */
    public RequisitoDTO(
    		Long idRequisito, String nombreRequisito, 
    		HabilitadoInhabilitadoEnum estado){
    	this.idRequisito = idRequisito;
    	this.nombreRequisito = nombreRequisito;
    	this.estado = estado;
    }

	/**
	 * @return the idRequisito
	 */
	public Long getIdRequisito() {
		return idRequisito;
	}

	/**
	 * @param idRequisito the idRequisito to set
	 */
	public void setIdRequisito(Long idRequisito) {
		this.idRequisito = idRequisito;
	}

	/**
	 * @return the nombreRequisito
	 */
	public String getNombreRequisito() {
		return nombreRequisito;
	}

	/**
	 * @param nombreRequisito the nombreRequisito to set
	 */
	public void setNombreRequisito(String nombreRequisito) {
		this.nombreRequisito = nombreRequisito;
	}

    /**
     * @return the estado
     */
    public HabilitadoInhabilitadoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(HabilitadoInhabilitadoEnum estado) {
        this.estado = estado;
    }
}
