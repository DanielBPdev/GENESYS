package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.core.TipoInfraestructura;
import com.asopagos.enumeraciones.core.MedidaCapacidadInfraestructuraEnum;

/**
 * <b>Descripcion:</b> DTO que representa al entity TipoInfraestructura <br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class TipoInfraestructuraModeloDTO implements Serializable {
    private static final long serialVersionUID = -7775667970162305950L;

    /** Identificador único del registro */
    private Long id;

    /** Código del tipo de infraestructura definido por la SSF */
    private String codigo;

    /** Descripción del tipo de infraestructura */
    private String nombre;

    /** Medida de la papacidad instalada del tipo de infraestructura de acuerdo a lo establecido por la SSF */
    private MedidaCapacidadInfraestructuraEnum medidaCapacidad;

    /** Indicador de registro activo */
    private Boolean activo;

    /** Método para convertir de entidad a DTO */
    public void convertToDTO(TipoInfraestructura tipoInfraestructura) {
        this.id = tipoInfraestructura.getId();
        this.codigo = tipoInfraestructura.getCodigo();
        this.nombre = tipoInfraestructura.getNombre();
        this.medidaCapacidad = tipoInfraestructura.getMedidaCapacidad();
        this.activo = tipoInfraestructura.getActivo();
    }

    /** Método para convertir de DTO a Entity */
    public TipoInfraestructura convertToEntity() {
        TipoInfraestructura tipoInfraestructura = new TipoInfraestructura();
        tipoInfraestructura.setId(this.getId());
        tipoInfraestructura.setCodigo(this.getCodigo());
        tipoInfraestructura.setNombre(this.getNombre());
        tipoInfraestructura.setMedidaCapacidad(this.getMedidaCapacidad());
        tipoInfraestructura.setActivo(this.getActivo());
        return tipoInfraestructura;
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
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *        the codigo to set
     */
    public void setCodigo(String codigo) {
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
     * @return the medidaCapacidad
     */
    public MedidaCapacidadInfraestructuraEnum getMedidaCapacidad() {
        return medidaCapacidad;
    }

    /**
     * @param medidaCapacidad
     *        the medidaCapacidad to set
     */
    public void setMedidaCapacidad(MedidaCapacidadInfraestructuraEnum medidaCapacidad) {
        this.medidaCapacidad = medidaCapacidad;
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
