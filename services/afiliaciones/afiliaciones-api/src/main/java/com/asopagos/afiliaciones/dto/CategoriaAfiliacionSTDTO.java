package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.List;

public class CategoriaAfiliacionSTDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Clasificación del afiliado
	 */
	private String clasificacion;
	
	/**
	 * Arreglo de las categorias
	 */
	private List<CategoriaSTDTO> arregloCategorias;

	
	
	public CategoriaAfiliacionSTDTO() {
	}

	public CategoriaAfiliacionSTDTO(String clasificacion, List<CategoriaSTDTO> arregloCategorias) {
		this.clasificacion = clasificacion;
		this.arregloCategorias = arregloCategorias;
	}

	/**
	 * @return the clasificacion
	 */
	public String getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion the clasificacion to set
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * @return the arregloCategorias
	 */
	public List<CategoriaSTDTO> getArregloCategorias() {
		return arregloCategorias;
	}

	/**
	 * @param arregloCategorias the arregloCategorias to set
	 */
	public void setArregloCategorias(List<CategoriaSTDTO> arregloCategorias) {
		this.arregloCategorias = arregloCategorias;
	}
	
}
