package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class CategoriaAfiliadoSTDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Arreglo de categorias como afiliado principal
	 */
	private grupoCategoriasSTDTO arregloPrincipal;
	
	/**
	 * Arreglo de categorias del conyuge
	 */
	private grupoCategoriasSTDTO arregloConyuge;

	private List<grupoCategoriasSTDTO> arregloBeneficiarios;
	
	public CategoriaAfiliadoSTDTO() {
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
	 * @return the arregloConyuge
	 */
	public grupoCategoriasSTDTO getArregloConyuge() {
		return arregloConyuge;
	}

	/**
	 * @param arregloConyuge the arregloConyuge to set
	 */
	public void setArregloConyuge(grupoCategoriasSTDTO arregloConyuge) {
		this.arregloConyuge = arregloConyuge;
	}

	/**
	 * @return the arregloBeneficiarios
	 */
	public List<grupoCategoriasSTDTO> getArregloBeneficiarios() {
		return arregloBeneficiarios;
	}

	/**
	 * @param arregloBeneficiarios the arregloBeneficiarios to set
	 */
	public void setArregloBeneficiarios(List<grupoCategoriasSTDTO> arregloBeneficiarios) {
		this.arregloBeneficiarios = arregloBeneficiarios;
	}
	
	
}
