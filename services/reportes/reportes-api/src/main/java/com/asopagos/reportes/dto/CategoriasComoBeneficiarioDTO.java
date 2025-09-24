package com.asopagos.reportes.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class CategoriasComoBeneficiarioDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 8978344274885945389L;

    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private EstadoAfiliadoEnum estadoActualAfiliado;
    private String nombres;
    private TipoBeneficiarioEnum parentezco;
    private ClasificacionEnum clasificacion;
    
    private String categoriaDependiente;
    private String categoriaIndependiente;
    private String categoriaPensionado;
    private String tarifaUVTDependiente;
    private String tarifaUVTIndependiente;
    private String tarifaUVTPensionado;
    
    List<CategoriaDTO> categorias;

    /**
     * 
     */
    public CategoriasComoBeneficiarioDTO() {
    }

    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param estadoActualAfiliado
     * @param nombres
     * @param parentezco
     * @param clasificacion
     * @param categoriaDependiente
     * @param categoriaIndependiente
     * @param categoriaPensionado
     * @param tarifaUVTDependiente
     * @param tarifaUVTIndependiente
     * @param tarifaUVTPensionado
     * @param categorias
     */
    public CategoriasComoBeneficiarioDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            EstadoAfiliadoEnum estadoActualAfiliado, String nombres, TipoBeneficiarioEnum parentezco, ClasificacionEnum clasificacion,
            String categoriaDependiente, String categoriaIndependiente, String categoriaPensionado, 
            String tarifaUVTDependiente, String tarifaUVTIndependiente, String tarifaUVTPensionado, List<CategoriaDTO> categorias) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estadoActualAfiliado = estadoActualAfiliado;
        this.nombres = nombres;
        this.parentezco = parentezco;
        this.clasificacion = clasificacion;
        this.categoriaDependiente = categoriaDependiente;
        this.categoriaIndependiente = categoriaIndependiente;
        this.categoriaPensionado = categoriaPensionado;
        this.tarifaUVTDependiente = tarifaUVTDependiente;
        this.tarifaUVTIndependiente = tarifaUVTIndependiente;
        this.tarifaUVTPensionado = tarifaUVTPensionado;
        this.categorias = categorias;
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
