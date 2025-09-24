package com.asopagos.dto;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO que representa el resultado de la ejecución de una
 * validación específica <b>Historia de Usuario: </b>TRA
 * 
 * @author Jonatan Javier Velandiajvelandia@heinsohn.com.co
 *         </a>
 */
@XmlRootElement
public class ConsultarEstadoDTO implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Es el entityManager en cual se ejecutara las consultas de los estados
	 */
	private EntityManager entityManager;
	
	/**
	 * Es el tipo de persona, puede ser empleador o persona
	 */
	private String tipoPersona;
	
	/**
	 * Representa el tipo de identificación
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/**
	 * Representa el numero de identificación
	 */
	private String numeroIdentificacion;
	
	/**
	 * Representa el idEmpleador
	 */
	private Long idEmpleador;
	
	/**
	 * Representa el tipo de rol
	 */
	private String tipoRol; 

	
	public String getTipoRol() {
		return tipoRol;
	}

	public void setTipoRol(String tipoRol) {
		this.tipoRol = tipoRol;
	}

	public Long getIdEmpleador() {
		return idEmpleador;
	}

	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
