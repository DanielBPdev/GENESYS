package com.asopagos.pagination;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase que representa el archivo de namedQueries<br/>
 * <b>Módulo:</b> Asopagos - HU transversal <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlRootElement
public class EntityMappingsRead implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "named-query")
    List<NamedQueryRead> namedQueries;

    @XmlElement(name = "named-native-query")
    List<NamedNativeQueryRead> namedNativeQueries;
    
    /**
     * @return the namedQueries
     */
    public List<NamedQueryRead> getNamedQueries() {
        return this.namedQueries;
    }

    /**
     * Método que retorna el valor de namedNativeQueries.
     * @return valor de namedNativeQueries.
     */
    public List<NamedNativeQueryRead> getNamedNativeQueries() {
        return namedNativeQueries;
    }
    
}
