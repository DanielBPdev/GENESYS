package com.asopagos.parametros;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public class TablaParametro {

    private String nombreTabla;
    
    private String nombreClase;
    
    private List<AtributoTablaParametro> atributos;

    private List<RelatedTable> relatedTables;

    private List<RelatedTable> relatedLists;

    public TablaParametro(){
        
    }

    /**
     * @param nombreTabla
     * @param nombreClase
     * @param columnas
     * @param atributos
     * @param atributosEntidad
     * @param relatedTables
     */
    public TablaParametro(String nombreTabla, String nombreClase, List<AtributoTablaParametro> atributos,
            List<RelatedTable> relatedTables, List<RelatedTable> relatedLists) {

        this.nombreTabla = nombreTabla;
        this.nombreClase = nombreClase;
        this.atributos = atributos;
        this.relatedTables = relatedTables;
        this.relatedLists = relatedLists;
    }

    /**
     * @return the relatedLists
     */
    public List<RelatedTable> getRelatedLists() {
        return relatedLists;
    }

    /**
     * @param relatedLists the relatedLists to set
     */
    public void setRelatedLists(List<RelatedTable> relatedLists) {
        this.relatedLists = relatedLists;
    }
    
    public List<String> getNombresAtributos(){
        
        List<String> nombresAtributos = new ArrayList<>();
        
        for (AtributoTablaParametro atributoTablaParametro : atributos) {
            nombresAtributos.add(atributoTablaParametro.getNombreAtributo());
        }
        
        return nombresAtributos;
    }


    /**
     * @return the atributos
     */
    public List<AtributoTablaParametro> getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(List<AtributoTablaParametro> atributos) {
        this.atributos = atributos;
    }


    /**
     * @return the nombreTabla
     */
    public String getNombreTabla() {
        return nombreTabla;
    }

    /**
     * @param nombreTabla the nombreTabla to set
     */
    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    /**
     * @return the relatedTables
     */
    public List<RelatedTable> getRelatedTables() {
        return relatedTables;
    }

    /**
     * @param relatedTables the relatedTables to set
     */
    public void setRelatedTables(List<RelatedTable> relatedTables) {
        this.relatedTables = relatedTables;
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
