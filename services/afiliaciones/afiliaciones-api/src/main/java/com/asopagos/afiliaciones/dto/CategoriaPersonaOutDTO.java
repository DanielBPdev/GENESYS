package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class CategoriaPersonaOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Estado de las categorias de la persona cuando es un afiliado
	 */
	private CategoriaAfiliadoSTDTO categoriasAfiliadoPrincipal;
	
	/**
	 * Estado de las categorias de la persona cuando es un beneficiario
	 */
	private CategoriaBeneficiarioSTDTO categoriasBeneficiario;
	
	private String mensajeError;
	
	public CategoriaPersonaOutDTO() {
	}

	/**
	 * @return the categoriasAfiliadoPrincipal
	 */
	public CategoriaAfiliadoSTDTO getCategoriasAfiliadoPrincipal() {
		return categoriasAfiliadoPrincipal;
	}

	/**
	 * @param categoriasAfiliadoPrincipal the categoriasAfiliadoPrincipal to set
	 */
	public void setCategoriasAfiliadoPrincipal(CategoriaAfiliadoSTDTO categoriasAfiliadoPrincipal) {
		this.categoriasAfiliadoPrincipal = categoriasAfiliadoPrincipal;
	}

	/**
	 * @return the categoriasBeneficiario
	 */
	public CategoriaBeneficiarioSTDTO getCategoriasBeneficiario() {
		return categoriasBeneficiario;
	}

	/**
	 * @param categoriasBeneficiario the categoriasBeneficiario to set
	 */
	public void setCategoriasBeneficiario(CategoriaBeneficiarioSTDTO categoriasBeneficiario) {
		this.categoriasBeneficiario = categoriasBeneficiario;
	}

	/**
	 * @return the mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * @param mensajeError the mensajeError to set
	 */
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
