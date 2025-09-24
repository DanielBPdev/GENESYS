package com.asopagos.dto.fovis;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contiene la información de una condición de hogar asociada a la novedad 31 Ajuste y actualización valor SFV (Decreto 133 de 2018)
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
@XmlRootElement
public class CondicionHogarNovedadDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 6709143866204306956L;

    /**
     * Contiene el nombre de la condición
     */
    private String nombre;

    /**
     * Contiene el valor de la condición expresado en S, N o NA
     */
    private String valor;

    /**
     * Contiene las observaciones relacionadas a la condición
     */
    private String observaciones;

    public CondicionHogarNovedadDTO() {
        super();
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
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor
     *        the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones
     *        the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
