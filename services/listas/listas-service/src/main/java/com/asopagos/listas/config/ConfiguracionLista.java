package com.asopagos.listas.config;

import java.util.Map;

/**
 * <b>Descripción:</b> Clase que contiene los datos que representan la 
 * configuración de una lista genérica
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class ConfiguracionLista {
	
	
	private Integer idLista;
	
	private String nombreClase;
    
	private String campoCodigo;
	
	private String campoNombre;
    
    private Map<String, Object> atributos;
	

	/**
	 * @return the idLista
	 */
	public Integer getIdLista() {
		return idLista;
	}

	/**
	 * @param idLista the idLista to set
	 */
	public void setIdLista(Integer idLista) {
		this.idLista = idLista;
	}

	/**
	 * @return the nombreClase
	 */
	public String getNombreClase() {
		return nombreClase;
	}

	/**
	 * @param nombreClase the nombreClase to set
	 */
	public void setNombreClase(String nombreClase) {
		this.nombreClase = nombreClase;
	}

	/**
	 * @return the campoCodigo
	 */
	public String getCampoCodigo() {
		return campoCodigo;
	}

	/**
	 * @param campoCodigo the campoCodigo to set
	 */
	public void setCampoCodigo(String campoCodigo) {
		this.campoCodigo = campoCodigo;
	}

	/**
	 * @return the campoNombre
	 */
	public String getCampoNombre() {
		return campoNombre;
	}

	/**
	 * @param campoNombre the campoNombre to set
	 */
	public void setCampoNombre(String campoNombre) {
		this.campoNombre = campoNombre;
	}

    /**
     * @return the atributos
     */
    public Map<String, Object> getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(Map<String, Object> atributos) {
        this.atributos = atributos;
    }

}
