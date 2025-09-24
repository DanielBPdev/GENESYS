package com.asopagos.parametros;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public class RelatedTable {

    private String nombre;
    
    private String nombreClase;
    
    private String displayAtributeName;

    private String atributeId;
    
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

    public RelatedTable(){
        
    }
    

    /**
     * @param nombre
     * @param nombreClase
     * @param displayAtributeName
     * @param atributeId
     */
    public RelatedTable(String nombre, String nombreClase, String displayAtributeName, String atributeId) {
        super();
        this.nombre = nombre;
        this.nombreClase = nombreClase;
        this.displayAtributeName = displayAtributeName;
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

    /**
     * @return the nombreClase
     */
    public String getNombreClase() {
        return nombreClase;
    }

    /**
     * @param nombreClase the nombreClase to set
     */
    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }
}
