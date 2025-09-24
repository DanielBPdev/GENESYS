package com.asopagos.auditoria.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.auditoria.Revision;
import com.asopagos.entidades.auditoria.RevisionEntidad;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlRootElement
public class RevisionDTO implements Serializable{

    private long id;
    
    private long timestamp;
    
    private String ip;
    
    private String requestId;
    
    private String nombreUsuario;
    
    private String entityClassName;
   
    private Set<RevisionEntidadDTO> modifiedEntityTypes;
    
    public static RevisionDTO convertToDTO(Revision revision){
        RevisionDTO revisionDTO = new RevisionDTO();
        revisionDTO.setId(revision.getId());
        revisionDTO.setIp(revision.getIp());
        revisionDTO.setNombreUsuario(revision.getNombreUsuario());
        revisionDTO.setTimestamp(revision.getTimestamp());
        revisionDTO.setRequestId(revision.getRequestId());
        if (revision.getModifiedEntityTypes()!=null && !revision.getModifiedEntityTypes().isEmpty()) {
            Set<RevisionEntidadDTO> ent = new HashSet<>();
            for (RevisionEntidad entidades : revision.getModifiedEntityTypes()) {
                RevisionEntidadDTO log = RevisionEntidadDTO.convertToLogAuditoriaEntidadDTO(entidades);
                ent.add(log);
            }
            revisionDTO.setModifiedEntityTypes(ent);
        }
        return revisionDTO;
    }

   
    
    public static RevisionDTO convertToDTO(RevisionEntidad revisionEntidad){
        Revision revision = revisionEntidad.getRevision();
        RevisionDTO revisionDTO = new RevisionDTO();
        revisionDTO.setId(revision.getId());
        revisionDTO.setIp(revision.getIp());
        revisionDTO.setNombreUsuario(revision.getNombreUsuario());
        revisionDTO.setTimestamp(revision.getTimestamp());
        revisionDTO.setRequestId(revision.getRequestId());
        if (revision.getModifiedEntityTypes()!=null && !revision.getModifiedEntityTypes().isEmpty()) {
            Set<RevisionEntidadDTO> ent = new HashSet<>();
            for (RevisionEntidad entidades : revision.getModifiedEntityTypes()) {
                RevisionEntidadDTO log = RevisionEntidadDTO.convertToLogAuditoriaEntidadDTO(entidades);
                ent.add(log);
            }
        
            revisionDTO.setModifiedEntityTypes(ent);
        }
        
        return revisionDTO;
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
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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
     * @return the modifiedEntityTypes
     */
    public Set<RevisionEntidadDTO> getModifiedEntityTypes() {
        return modifiedEntityTypes;
    }

    /**
     * @param modifiedEntityTypes the modifiedEntityTypes to set
     */
    public void setModifiedEntityTypes(Set<RevisionEntidadDTO> modifiedEntityTypes) {
        this.modifiedEntityTypes = modifiedEntityTypes;
    }
}
