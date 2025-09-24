package com.asopagos.parametros.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.ElementoListaDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlRootElement
public class RelatedTableDTO {

    private String nombre;
   
    private String displayAtributeName;
    
    private String atributeId;
    
    private List<ElementoListaDTO> listaDatos;
    
    public RelatedTableDTO(){
        
    }
    
    /**
     * @param nombre
     * @param nombreClase
     * @param displayAtributeName
     * @param atributeId
     * @param listaDatos
     */
    public RelatedTableDTO(String nombre, String displayAtributeName, String atributeId) {

        this.nombre = nombre;
        this.displayAtributeName = displayAtributeName;
        this.atributeId = atributeId;
    }



    /**
     * @return the listaDatos
     */
    public List<ElementoListaDTO> getListaDatos() {
        return listaDatos;
    }

    /**
     * @param listaDatos the listaDatos to set
     */
    public void setListaDatos(List<ElementoListaDTO> listaDatos) {
        this.listaDatos = listaDatos;
    }

    /**
     * @return the atributeId
     */
    public String getAtributeId() {
        return atributeId;
    }



    /**
     * @param atributeId the atributeId to set
     */
    public void setAtributeId(String atributeId) {
        this.atributeId = atributeId;
    }



    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the displayAtributeName
     */
    public String getDisplayAtributeName() {
        return displayAtributeName;
    }

    /**
     * @param displayAtributeName the displayAtributeName to set
     */
    public void setDisplayAtributeName(String displayAtributeName) {
        this.displayAtributeName = displayAtributeName;
    }

    
}
