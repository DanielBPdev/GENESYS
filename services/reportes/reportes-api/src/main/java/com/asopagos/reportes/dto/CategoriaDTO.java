package com.asopagos.reportes.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;

@XmlRootElement
public class CategoriaDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5998248248451508723L;

    private String categoriaDependiente;
    private String categoriaIndependiente;
    private String categoriaPensionado;
    private String fechaCambioCategoria;
    private String tarifaUVTDependiente;
    private String tarifaUVTIndependiente;
    private String tarifaUVTPensionado;
    private MotivoCambioCategoriaEnum motivoCambioCategoria;
    private Boolean respectoAfiliadoPrincipal;

    /**
     * 
     */
    public CategoriaDTO() {
    }

    /**
     * @param categoriaDependiente
     * @param categoriaIndependiente
     * @param categoriaPensionado
     * @param tarifaUVTDependiente
     * @param tarifaUVTIndependiente
     * @param tarifaUVTPensionado
     * @param fechaCambioCategoria
     * @param motivoCambioCategoria
     * @param respectoAfiliadoPrincipal
     */
    public CategoriaDTO(String categoriaDependiente, String categoriaIndependiente, String categoriaPensionado,
            String tarifaUVTDependiente, String tarifaUVTIndependiente, String tarifaUVTPensionado,
            String fechaCambioCategoria, MotivoCambioCategoriaEnum motivoCambioCategoria,Boolean respectoAfiliadoPrincipal) {
        this.categoriaDependiente = categoriaDependiente;
        this.categoriaIndependiente = categoriaIndependiente;
        this.categoriaPensionado = categoriaPensionado;
        this.tarifaUVTDependiente = tarifaUVTDependiente;
        this.tarifaUVTIndependiente = tarifaUVTIndependiente;
        this.tarifaUVTPensionado = tarifaUVTPensionado;
        this.fechaCambioCategoria = fechaCambioCategoria;
        this.motivoCambioCategoria = motivoCambioCategoria;
        this.respectoAfiliadoPrincipal=respectoAfiliadoPrincipal;
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
     * @return the fechaCambioCategoria
     */
    public String getFechaCambioCategoria() {
        return fechaCambioCategoria;
    }

    /**
     * @param fechaCambioCategoria the fechaCambioCategoria to set
     */
    public void setFechaCambioCategoria(String fechaCambioCategoria) {
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
        /**
     * @param respectoAfiliadoPrincipal the respectoAfiliadoPrincipal to set
     */
    public void setRespectoAfiliadoPrincipal(Boolean respectoAfiliadoPrincipal) {
        this.respectoAfiliadoPrincipal = respectoAfiliadoPrincipal;
    }

    /**
     * @return the fechaCambioCategoria
     */
    public Boolean getRespectoAfiliadoPrincipal() {
        return respectoAfiliadoPrincipal;
    }
}
