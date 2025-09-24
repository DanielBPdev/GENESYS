package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class CategoriasComoBeneficiario360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Boolean categoriaMarcada;
    
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private EstadoAfiliadoEnum estadoActualAfiliado;
    private String nombres;
    private TipoBeneficiarioEnum parentezco;
    private ClasificacionEnum clasificacion;
    
    private CategoriaPersonaEnum categoriaDependiente;
    private CategoriaPersonaEnum categoriaIndependiente;
    private CategoriaPersonaEnum categoriaPensionado;
    
    private List<CategoriaHeredada360DTO> categorias;

    /**
     * 
     */
    public CategoriasComoBeneficiario360DTO() {
    }

    /**
     * @param categoriaDependiente
     * @param categoriaIndependiente
     * @param categoriaPensionado
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param estadoActualAfiliado
     * @param nombres
     * @param parentezco
     * @param clasificacion
     * @param categorias
     */
    public CategoriasComoBeneficiario360DTO(CategoriaPersonaEnum categoriaDependiente, CategoriaPersonaEnum categoriaIndependiente,
            CategoriaPersonaEnum categoriaPensionado, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            EstadoAfiliadoEnum estadoActualAfiliado, String nombres, TipoBeneficiarioEnum parentezco, ClasificacionEnum clasificacion,
            List<CategoriaHeredada360DTO> categorias) {
        this.categoriaDependiente = categoriaDependiente;
        this.categoriaIndependiente = categoriaIndependiente;
        this.categoriaPensionado = categoriaPensionado;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estadoActualAfiliado = estadoActualAfiliado;
        this.nombres = nombres;
        this.parentezco = parentezco;
        this.clasificacion = clasificacion;
        this.categorias = categorias;
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
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the estadoActualAfiliado
     */
    public EstadoAfiliadoEnum getEstadoActualAfiliado() {
        return estadoActualAfiliado;
    }

    /**
     * @param estadoActualAfiliado the estadoActualAfiliado to set
     */
    public void setEstadoActualAfiliado(EstadoAfiliadoEnum estadoActualAfiliado) {
        this.estadoActualAfiliado = estadoActualAfiliado;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the parentezco
     */
    public TipoBeneficiarioEnum getParentezco() {
        return parentezco;
    }

    /**
     * @param parentezco the parentezco to set
     */
    public void setParentezco(TipoBeneficiarioEnum parentezco) {
        this.parentezco = parentezco;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the categorias
     */
    public List<CategoriaHeredada360DTO> getCategorias() {
        return categorias;
    }

    /**
     * @param categorias the categorias to set
     */
    public void setCategorias(List<CategoriaHeredada360DTO> categorias) {
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
}
