package com.asopagos.constantes.parametros.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.AreaCajaCompensacion;

/**
 * <b>Descripción:</b> DTO empleado para establecer las areas o dependencias de caja de compensacion familiar
 * <b>Historia de Usuario:</b> HU-Transversal
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class AreaCajaCompensacionDTO implements Serializable {

    private Short idAreaCajaCompensacion;

    private String nombre;

    /**
     * Metodo encargado de convertir a Dto un objeto de tipo AreaCajaCompensacion
     * @param areaCajaCompensacion,
     *        parametro de entrada
     * @return objeto DTO
     */
    public static AreaCajaCompensacionDTO convertToDTO(AreaCajaCompensacion areaCajaCompensacion) {
        AreaCajaCompensacionDTO dto = new AreaCajaCompensacionDTO();
        dto.setIdAreaCajaCompensacion(areaCajaCompensacion.getIdAreaCajaCompensacion());
        dto.setNombre(areaCajaCompensacion.getNombre());
        return dto;
    }

    /**
     * @return the idAreaCajaCompensacion
     */
    public Short getIdAreaCajaCompensacion() {
        return idAreaCajaCompensacion;
    }

    /**
     * @param idAreaCajaCompensacion
     *        the idAreaCajaCompensacion to set
     */
    public void setIdAreaCajaCompensacion(Short idAreaCajaCompensacion) {
        this.idAreaCajaCompensacion = idAreaCajaCompensacion;
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

}
