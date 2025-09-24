package com.asopagos.reportes.dto;

import java.io.Serializable;
import java.util.List;

public class CategoriasComoAfiliadoPrincipalDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String categoriaDependiente;
    private String categoriaIndependiente;
    private String categoriaPensionado;
    private String tarifaUVTDependiente;
    private String tarifaUVTIndependiente;
    private String tarifaUVTPensionado;
    
    private List<CategoriaDTO> categorias;

    /**
     * 
     */
    public CategoriasComoAfiliadoPrincipalDTO() {
    }

    /**
     * @param categoriaDependiente
     * @param categoriaIndependiente
     * @param categoriaPensionado
     * @param tarifaUVTDependiente
     * @param tarifaUVTIndependiente
     * @param tarifaUVTPensionado
     * @param categorias
     */
    public CategoriasComoAfiliadoPrincipalDTO(String categoriaDependiente, String categoriaIndependiente, String categoriaPensionado,
            String tarifaUVTDependiente, String tarifaUVTIndependiente, String tarifaUVTPensionado,
            List<CategoriaDTO> categorias) {
        this.categoriaDependiente = categoriaDependiente;
        this.categoriaIndependiente = categoriaIndependiente;
        this.categoriaPensionado = categoriaPensionado;
        this.tarifaUVTDependiente = tarifaUVTDependiente;
        this.tarifaUVTIndependiente = tarifaUVTIndependiente;
        this.tarifaUVTPensionado = tarifaUVTPensionado;
        this.categorias = categorias;
    }

    /**
     * @return the categoriaDependiente
     */
    public String getCategoriaDependiente() {
        return categoriaDependiente;
    }

    /**
     * @param categoriaDependiente the categoriaDependiente to set
     */
    public void setCategoriaDependiente(String categoriaDependiente) {
        this.categoriaDependiente = categoriaDependiente;
    }

    /**
     * @return the categoriaIndependiente
     */
    public String getCategoriaIndependiente() {
        return categoriaIndependiente;
    }

    /**
     * @param categoriaIndependiente the categoriaIndependiente to set
     */
    public void setCategoriaIndependiente(String categoriaIndependiente) {
        this.categoriaIndependiente = categoriaIndependiente;
    }

    /**
     * @return the categoriaPensionado
     */
    public String getCategoriaPensionado() {
        return categoriaPensionado;
    }

    /**
     * @param categoriaPensionado the categoriaPensionado to set
     */
    public void setCategoriaPensionado(String categoriaPensionado) {
        this.categoriaPensionado = categoriaPensionado;
    }

    /**
     * @return the tarifaUVTDependiente
     */
    public String getTarifaUVTDependiente() {
        return this.tarifaUVTDependiente;
    }

    /**
     * @param tarifaUVTDependiente the tarifaUVTDependiente to set
     */
    public void setTarifaUVTDependiente(String tarifaUVTDependiente) {
        this.tarifaUVTDependiente = tarifaUVTDependiente;
    }

    /**
     * @return the tarifaUVTIndependiente
     */
    public String getTarifaUVTIndependiente() {
        return this.tarifaUVTIndependiente;
    }

    /**
     * @param tarifaUVTIndependiente the tarifaUVTIndependiente to set
     */
    public void setTarifaUVTIndependiente(String tarifaUVTIndependiente) {
        this.tarifaUVTIndependiente = tarifaUVTIndependiente;
    }

    /**
     * @return the tarifaUVTPensionado
     */
    public String getTarifaUVTPensionado() {
        return this.tarifaUVTPensionado;
    }

    /**
     * @param tarifaUVTPensionado the tarifaUVTPensionado to set
     */
    public void setTarifaUVTPensionado(String tarifaUVTPensionado) {
        this.tarifaUVTPensionado = tarifaUVTPensionado;
    }


    /**
     * @return the categorias
     */
    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    /**
     * @param categorias the categorias to set
     */
    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }
}
