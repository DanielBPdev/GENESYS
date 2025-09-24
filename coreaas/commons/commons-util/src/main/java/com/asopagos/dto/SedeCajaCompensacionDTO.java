package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.SedeCajaCompensacion;

/**
 * <b>Descripcion:</b> Clase que encapsula los datos de resultado general de la consulta a la Sede de la Caja de Compensacion Familiar<br/>
 * <b>Módulo:</b> Transversal Asopagos<br/>
 *
 * @author Luis Arturo Zarate Ayala <a href="mailto:lzarate@heinsohn.com.co"> lzarate</a>
 */
@XmlRootElement
public class SedeCajaCompensacionDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    private Long idSedeCajaCompensacion;

    private String nombre;

    private Boolean virtual;

    private String codigo;

    public static SedeCajaCompensacionDTO convertSedeCajaCompensacionToDTO(SedeCajaCompensacion sedeCajaCompensacion) {
        if (sedeCajaCompensacion != null) {
            SedeCajaCompensacionDTO sedeCajaCompensacionDTO = new SedeCajaCompensacionDTO();
            sedeCajaCompensacionDTO.setCodigo(sedeCajaCompensacion.getCodigo());
            sedeCajaCompensacionDTO.setIdSedeCajaCompensacion(sedeCajaCompensacion.getIdSedeCajaCompensacion());
            sedeCajaCompensacionDTO.setNombre(sedeCajaCompensacion.getNombre());
            sedeCajaCompensacionDTO.setVirtual(sedeCajaCompensacion.isVirtual());
            return sedeCajaCompensacionDTO;
        }
        else {
            return null;
        }
    }

    /**
     * @return the idSedeCajaCompensacion
     */
    public Long getIdSedeCajaCompensacion() {
        return idSedeCajaCompensacion;
    }

    /**
     * @param idSedeCajaCompensacion
     *        the idSedeCajaCompensacion to set
     */
    public void setIdSedeCajaCompensacion(Long idSedeCajaCompensacion) {
        this.idSedeCajaCompensacion = idSedeCajaCompensacion;
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
     * @return the virtual
     */
    public Boolean getVirtual() {
        return virtual;
    }

    /**
     * @param virtual
     *        the virtual to set
     */
    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
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

}
