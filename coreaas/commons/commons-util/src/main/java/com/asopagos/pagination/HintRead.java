package com.asopagos.pagination;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <b>Descripcion:</b> Clase que representa los hints de una named query<br/>
 * <b>Módulo:</b> Asopagos - HU transversal<br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "query-hint", propOrder = {
    "description"
})
public class HintRead {
    
    protected String description;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    
    @XmlAttribute(name = "value", required = true)
    protected String value;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    
}

