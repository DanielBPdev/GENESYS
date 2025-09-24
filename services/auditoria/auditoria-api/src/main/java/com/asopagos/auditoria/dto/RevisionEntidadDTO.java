package com.asopagos.auditoria.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.auditoria.RevisionEntidad;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

@XmlRootElement
public class RevisionEntidadDTO implements Serializable{

    private long id;
    
    private String entityClassName;
    
    private Byte revisionType;
    
    public static RevisionEntidadDTO convertToLogAuditoriaEntidadDTO(RevisionEntidad revisionEntidad){
        RevisionEntidadDTO dto = new RevisionEntidadDTO();
        dto.setEntityClassName(revisionEntidad.getEntityClassName());
        dto.setId(revisionEntidad.getId());
        dto.setRevisionType(revisionEntidad.getRevisionType().getRepresentation());
        return dto;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the entityClassName
     */
    public String getEntityClassName() {
        return entityClassName;
    }

    /**
     * @param entityClassName the entityClassName to set
     */
    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    /**
     * @return the revisionType
     */
    public Byte getRevisionType() {
        return revisionType;
    }

    /**
     * @param revisionType the revisionType to set
     */
    public void setRevisionType(Byte revisionType) {
        this.revisionType = revisionType;
    }    
}
