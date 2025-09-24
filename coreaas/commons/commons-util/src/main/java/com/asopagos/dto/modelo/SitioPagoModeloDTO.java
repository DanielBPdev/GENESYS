package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.core.SitioPago;

/**
 * <b>Descripcion:</b> DTO que representa al Entity SitioPago <br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class SitioPagoModeloDTO implements Serializable {
    private static final long serialVersionUID = -6715390004721266375L;

    /** Identificador único del registro */
    private Long id;

    /** Código del sitio de pago definido por la CCF */
    private String codigo;

    /** Nombre del sitio de pago */
    private String nombre;

    /** Referencia a la infraestructura para el sitio de pago */
    private Long infraestructura;

    /** Indicador de registro activo */
    private Boolean activo;
    
    /** Indicador de que es sito pago principal */
    private Boolean principal;

    /** Método para convertir de entidad a DTO */
    public void convertToDTO(SitioPago sitioPago) {
        this.id = sitioPago.getId();
        this.codigo = sitioPago.getCodigo();
        this.nombre = sitioPago.getNombre();
        this.infraestructura = sitioPago.getInfraestructura();
        this.activo = sitioPago.getActivo();
        this.principal = sitioPago.getPrincipal();
    }

    /** Método para convertir de DTO a Entity */
    public SitioPago convertToEntity() {
        SitioPago sitioPago = new SitioPago();
        sitioPago.setId(this.getId());
        sitioPago.setCodigo(this.getCodigo());
        sitioPago.setNombre(this.getNombre());
        sitioPago.setInfraestructura(this.getInfraestructura());
        sitioPago.setActivo(this.getActivo());
        sitioPago.setPrincipal(this.getPrincipal());
        return sitioPago;
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
     * @return the infraestructura
     */
    public Long getInfraestructura() {
        return infraestructura;
    }

    /**
     * @param infraestructura
     *        the infraestructura to set
     */
    public void setInfraestructura(Long infraestructura) {
        this.infraestructura = infraestructura;
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

    /**
     * @return the principal
     */
    public Boolean getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
