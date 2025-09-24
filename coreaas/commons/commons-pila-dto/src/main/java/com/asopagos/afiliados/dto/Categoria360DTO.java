package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;

public class Categoria360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private CategoriaPersonaEnum categoriaDependiente;
    private CategoriaPersonaEnum categoriaIndependiente;
    private CategoriaPersonaEnum categoriaPensionado;
    private Date fechaCambioCategoria;
    private MotivoCambioCategoriaEnum motivoCambioCategoria;
    
    /**
     * 
     */
    public Categoria360DTO() {
    }

    /**
     * @param categoriaDependiente
     * @param categoriaIndependiente
     * @param categoriaPensionado
     * @param fechaCambioCategoria
     * @param motivoCambioCategoria
     */
    public Categoria360DTO(CategoriaPersonaEnum categoriaDependiente, CategoriaPersonaEnum categoriaIndependiente, CategoriaPersonaEnum categoriaPensionado,
            Date fechaCambioCategoria, MotivoCambioCategoriaEnum motivoCambioCategoria) {
        this.categoriaDependiente = categoriaDependiente;
        this.categoriaIndependiente = categoriaIndependiente;
        this.categoriaPensionado = categoriaPensionado;
        this.fechaCambioCategoria = fechaCambioCategoria;
        this.motivoCambioCategoria = motivoCambioCategoria;
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
     * @return the fechaCambioCategoria
     */
    public Date getFechaCambioCategoria() {
        return fechaCambioCategoria;
    }

    /**
     * @param fechaCambioCategoria the fechaCambioCategoria to set
     */
    public void setFechaCambioCategoria(Date fechaCambioCategoria) {
        this.fechaCambioCategoria = fechaCambioCategoria;
    }

    /**
     * @return the motivoCambioCategoria
     */
    public MotivoCambioCategoriaEnum getMotivoCambioCategoria() {
        return motivoCambioCategoria;
    }

    /**
     * @param motivoCambioCategoria the motivoCambioCategoria to set
     */
    public void setMotivoCambioCategoria(MotivoCambioCategoriaEnum motivoCambioCategoria) {
        this.motivoCambioCategoria = motivoCambioCategoria;
    }
}
