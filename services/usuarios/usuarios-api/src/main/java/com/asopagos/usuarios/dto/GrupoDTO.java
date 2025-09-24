package com.asopagos.usuarios.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que transporta los datos de un grupo de KeyCloak
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class GrupoDTO implements Serializable{

    //Atributos copia de GroupRepresentation
    /**
     * Identificador del grupo
     */
    protected String id;
    
    /**
     * Nombre del Grupo 
     */
    protected String name;
    
    /**
     * Path del Grupo 
     */
    protected String path;
    
    /**
     * Atributos del grupo
     */
    protected Map<String, List<String>>  attributes;
    
    /**
     * Roles asociados al grupo
     */
    protected List<String> realmRoles;
    
    /**
     * Cliente Roles
     */
    protected Map<String, List<String>> clientRoles;
       
    //Atributos definidos en el refactor de ElementoListaDTO a GrupoDTO    
    /**
     * Representa el valor del name del grupo
     */
    private String key;
    
    /**
     * Representa el valor del atributo DESCRIPCION del grupo en KeyCloak 
     */
    private String valor;

    /**
     * Metodo encargado de retornar un DTO a partir de los parametros del grupo
     * @param id, Identificador
     * @param name, Nombre del Grupo
     * @param path, Path del grupo
     * @param realmRoles, roles
     * @param attributes, atributos del grupo
     * @param clientRoles, clientes del rol
     * @param subGroups, subgrupo
     * @return DTO
     */
    public static GrupoDTO toGrupoDTO(String id, String name, String path, List<String> realmRoles, Map<String, List<String>>  attributes, Map<String, List<String>> clientRoles){
        GrupoDTO grupo = new GrupoDTO();
        grupo.setAttributes(attributes);
        grupo.setClientRoles(clientRoles);
        grupo.setId(id);
        grupo.setName(name);
        grupo.setKey(name);
        grupo.setPath(path);
        grupo.setRealmRoles(realmRoles);
        return grupo;
    }
    
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the attributes
     */
    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, List<String>> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the realmRoles
     */
    public List<String> getRealmRoles() {
        return realmRoles;
    }

    /**
     * @param realmRoles the realmRoles to set
     */
    public void setRealmRoles(List<String> realmRoles) {
        this.realmRoles = realmRoles;
    }

    /**
     * @return the clientRoles
     */
    public Map<String, List<String>> getClientRoles() {
        return clientRoles;
    }

    /**
     * @param clientRoles the clientRoles to set
     */
    public void setClientRoles(Map<String, List<String>> clientRoles) {
        this.clientRoles = clientRoles;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }


    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }


    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    
}
