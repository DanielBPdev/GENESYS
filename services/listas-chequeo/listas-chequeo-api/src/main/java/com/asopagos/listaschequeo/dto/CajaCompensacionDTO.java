package com.asopagos.listaschequeo.dto;

import java.io.Serializable;

/**
 * <b>Descripción:</b> DTO para intercambio de requisitos por caja de 
 * compensación
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class CajaCompensacionDTO implements Serializable {
    
	/**
	 * Código identificador de llave primaria del requisito 
	 */
	private Integer idCajaCompensacion;

	/**
	 * Descripción del requisito
	 */
	private String nombre;
	
	/**
	 * Código caja compensación
	 */
	private String codigoCCF;
	
	/**
	 * Nombre Departamento caja compensación
	 */
	private String nombreDepartamentoCajaCompensacion;
	
	/**
	 * Constructor de la clase
	 */
	public CajaCompensacionDTO(){
	}
	
	/**
	 * Constructor de la clase
	 * 
	 * @param idCajaCompensacion
	 * @param nombre
	 */
	public CajaCompensacionDTO(Integer idCajaCompensacion
				, String nombre, String codigoCCF, String nombreDepartamentoCajaCompensacion){
		this.idCajaCompensacion = idCajaCompensacion;
		this.nombre = nombre;
		this.codigoCCF = codigoCCF;
		this.nombreDepartamentoCajaCompensacion = nombreDepartamentoCajaCompensacion;
	}

    /**
     * @return the idCajaCompensacion
     */
    public Integer getIdCajaCompensacion() {
        return idCajaCompensacion;
    }

    /**
     * @param idCajaCompensacion the idCajaCompensacion to set
     */
    public void setIdCajaCompensacion(Integer idCajaCompensacion) {
        this.idCajaCompensacion = idCajaCompensacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	/**
	 * @return the nombreDepartamentoCajaCompensacion
	 */
	public String getNombreDepartamentoCajaCompensacion() {
		return nombreDepartamentoCajaCompensacion;
	}

	/**
	 * @param nombreDepartamentoCajaCompensacion the nombreDepartamentoCajaCompensacion to set
	 */
	public void setNombreDepartamentoCajaCompensacion(String nombreDepartamentoCajaCompensacion) {
		this.nombreDepartamentoCajaCompensacion = nombreDepartamentoCajaCompensacion;
	}

	/**
	 * @return the codigoCCF
	 */
	public String getCodigoCCF() {
		return codigoCCF;
	}

	/**
	 * @param codigoCCF the codigoCCF to set
	 */
	public void setCodigoCCF(String codigoCCF) {
		this.codigoCCF = codigoCCF;
	}

}
