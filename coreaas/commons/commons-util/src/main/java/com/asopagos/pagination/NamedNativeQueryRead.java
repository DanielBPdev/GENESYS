package com.asopagos.pagination;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <b>Descripcion:</b> Clase que representa un named query <br/>
 * <b>Módulo:</b> Asopagos - HU transversal<br/>
 *
 * @author  <a href="mailto:atoro@heinsohn.com.co"> atoro</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "named-native-query", propOrder = {
    "description",
    "query",
    "hint"
})
public class NamedNativeQueryRead {
    
    protected String description;
    
    @XmlElement(required = true)
    protected String query;
    

    protected List<HintRead> hint = null;
    
    @XmlAttribute(name = "name", required = true)
    protected String name;
    
    @XmlAttribute(name = "result-set-mapping")
    protected String resultMapping;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return the hints
     */
    public List<HintRead> getHints() {
        return hint;
    }

    /**
     * @return the resultMapping
     */
    public String getResultMapping() {
        return resultMapping;
    }

    
}
