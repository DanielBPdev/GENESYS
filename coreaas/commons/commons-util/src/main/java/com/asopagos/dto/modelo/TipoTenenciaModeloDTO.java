package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.core.TipoTenencia;

/**
 * <b>Descripcion:</b> DTO que representa al Entity Infraestructura <br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class TipoTenenciaModeloDTO implements Serializable {
    private static final long serialVersionUID = 6713190497259558520L;

    /** Identificador único del registro */
    private Long id;

    /** Código del tipo de tenecia definido por la SSF */
    private Short codigo;

    /** Nombre del tipo de tenencia */
    private String nombre;

    /** Indicador de registro activo */
    private Boolean activo;

    /** Método para convertir de entidad a DTO */
    public void convertToDTO(TipoTenencia tipoTenencia) {
        this.id = tipoTenencia.getId();
        this.codigo = tipoTenencia.getCodigo();
        this.nombre = tipoTenencia.getNombre();
        this.activo = tipoTenencia.getActivo();
    }

    /** Método para convertir de DTO a Entity */
    public TipoTenencia convertToEntity() {
        TipoTenencia tipoTenencia = new TipoTenencia();
        tipoTenencia.setId(this.getId());
        tipoTenencia.setCodigo(this.getCodigo());
        tipoTenencia.setNombre(this.getNombre());
        tipoTenencia.setActivo(this.getActivo());
        return tipoTenencia;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public Short getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *        the codigo to set
     */
    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo
     *        the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
