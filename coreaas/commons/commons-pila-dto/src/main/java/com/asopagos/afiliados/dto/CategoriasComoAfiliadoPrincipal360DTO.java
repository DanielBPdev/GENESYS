package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;

public class CategoriasComoAfiliadoPrincipal360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Boolean categoriaMarcada;
    
    private CategoriaPersonaEnum categoriaDependiente;
    private CategoriaPersonaEnum categoriaIndependiente;
    private CategoriaPersonaEnum categoriaPensionado;
    
    private List<Categoria360DTO> categorias;

    /**
     * 
     */
    public CategoriasComoAfiliadoPrincipal360DTO() {
    }

    /**
     * @param categoriaDependiente
     * @param categoriaIndependiente
     * @param categoriaPensionado
     * @param categorias
     */
    public CategoriasComoAfiliadoPrincipal360DTO(CategoriaPersonaEnum categoriaDependiente, CategoriaPersonaEnum categoriaIndependiente, CategoriaPersonaEnum categoriaPensionado,
            List<Categoria360DTO> categorias) {
        this.categoriaDependiente = categoriaDependiente;
        this.categoriaIndependiente = categoriaIndependiente;
        this.categoriaPensionado = categoriaPensionado;
        this.categorias = categorias;
    }

    
    
    
    /**
     * @return the categoriaMarcada
     */
    public Boolean getCategoriaMarcada() {
        return categoriaMarcada;
    }

    /**
     * @param categoriaMarcada the categoriaMarcada to set
     */
    public void setCategoriaMarcada(Boolean categoriaMarcada) {
        this.categoriaMarcada = categoriaMarcada;
    }

    /**
     * @return the categoriaDependiente
     */
    public CategoriaPersonaEnum getCategoriaDependiente() {
        return categoriaDependiente;
    }

    /**
     * @param categoriaDependiente the categoriaDependiente to set
     */
    public void setCategoriaDependiente(CategoriaPersonaEnum categoriaDependiente) {
        this.categoriaDependiente = categoriaDependiente;
    }

    /**
     * @return the categoriaIndependiente
     */
    public CategoriaPersonaEnum getCategoriaIndependiente() {
        return categoriaIndependiente;
    }

    /**
     * @param categoriaIndependiente the categoriaIndependiente to set
     */
    public void setCategoriaIndependiente(CategoriaPersonaEnum categoriaIndependiente) {
        this.categoriaIndependiente = categoriaIndependiente;
    }

    /**
     * @return the categoriaPensionado
     */
    public CategoriaPersonaEnum getCategoriaPensionado() {
        return categoriaPensionado;
    }

    /**
     * @param categoriaPensionado the categoriaPensionado to set
     */
    public void setCategoriaPensionado(CategoriaPersonaEnum categoriaPensionado) {
        this.categoriaPensionado = categoriaPensionado;
    }

    /**
     * @return the categorias
     */
    public List<Categoria360DTO> getCategorias() {
        return categorias;
    }

    /**
     * @param categorias the categorias to set
     */
    public void setCategorias(List<Categoria360DTO> categorias) {
        this.categorias = categorias;
    }
}
