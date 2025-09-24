package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class CategoriaBeneficiarioSTDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Arreglo de categorias respecto al afiliado principal
	 */
	private grupoCategoriasSTDTO arregloPrincipal;
	
	/**
	 * Arreglo de categorias respecto al afiliado secundario
	 */
	private grupoCategoriasSTDTO arregloSecundario;

	
	public CategoriaBeneficiarioSTDTO() {
	}
	
	/**
	 * @return the arregloPrincipal
	 */
	public grupoCategoriasSTDTO getArregloPrincipal() {
		return arregloPrincipal;
	}

	/**
	 * @param arregloPrincipal the arregloPrincipal to set
	 */
	public void setArregloPrincipal(grupoCategoriasSTDTO arregloPrincipal) {
		this.arregloPrincipal = arregloPrincipal;
	}

	/**
	 * @return the arregloSecundario
	 */
	public grupoCategoriasSTDTO getArregloSecundario() {
		return arregloSecundario;
	}

	/**
	 * @param arregloSecundario the arregloSecundario to set
	 */
	public void setArregloSecundario(grupoCategoriasSTDTO arregloSecundario) {
		this.arregloSecundario = arregloSecundario;
	}
}