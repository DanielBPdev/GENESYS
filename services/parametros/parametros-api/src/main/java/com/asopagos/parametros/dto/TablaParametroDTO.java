package com.asopagos.parametros.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.parametros.AtributoTablaParametro;
import com.asopagos.parametros.RelatedTable;
import com.asopagos.parametros.TablaParametro;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlRootElement
public class TablaParametroDTO {

    private String nombreTabla;
    
    private List<AtributoTablaParametroDTO> atributos;
    
    private List<RelatedTableDTO> relatedTables;
    
    private List<RelatedTableDTO> relatedLists;



    public TablaParametroDTO(){
        
    }
    
    /**
     * @return the relatedLists
     */
    public List<RelatedTableDTO> getRelatedLists() {
        return relatedLists;
    }

    /**
     * @param relatedLists the relatedLists to set
     */
    public void setRelatedLists(List<RelatedTableDTO> relatedLists) {
        this.relatedLists = relatedLists;
    }
    
    /**
     * @return the atributos
     */
    public List<AtributoTablaParametroDTO> getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(List<AtributoTablaParametroDTO> atributos) {
        this.atributos = atributos;
    }

    public static TablaParametroDTO convertToDTO(TablaParametro tablaParametro){
        
        TablaParametroDTO tablaParametroDTO = new TablaParametroDTO();
        
        tablaParametroDTO.setNombreTabla(tablaParametro.getNombreTabla());
        
        List<RelatedTable> relatedTablesParametro = tablaParametro.getRelatedTables();
        
        List<RelatedTable> relatedListsParametro = tablaParametro.getRelatedLists();
        
        if(relatedTablesParametro != null && !relatedTablesParametro.isEmpty()){

            List<RelatedTableDTO> relatedTables = new ArrayList<>();

            for (RelatedTable relatedTable : relatedTablesParametro) {
                    
                relatedTables.add(new RelatedTableDTO(relatedTable.getNombre(), relatedTable.getDisplayAtributeName(),relatedTable.getAtributeId()));
            }
         
            tablaParametroDTO.setRelatedTables(relatedTables);
        }

        
        if(relatedListsParametro != null && !relatedListsParametro.isEmpty()){

            List<RelatedTableDTO> relatedTables = new ArrayList<>();

            for (RelatedTable relatedTable : relatedListsParametro) {
                    
                relatedTables.add(new RelatedTableDTO(relatedTable.getNombre(), relatedTable.getDisplayAtributeName(), relatedTable.getAtributeId()));
 
            }
         
            tablaParametroDTO.setRelatedLists(relatedTables);
        }
        
        
        List<AtributoTablaParametro> atributos = tablaParametro.getAtributos();

        List<AtributoTablaParametroDTO> atributosDTO = new ArrayList<>();
        
        for (AtributoTablaParametro atributoTablaParametro : atributos) {
            
                atributosDTO.add(AtributoTablaParametroDTO.convertToDTO(atributoTablaParametro));
            
        }
        
        tablaParametroDTO.setAtributos(atributosDTO);

        return tablaParametroDTO;
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
    public List<RelatedTableDTO> getRelatedTables() {
        return relatedTables;
    }

    /**
     * @param relatedTables the relatedTables to set
     */
    public void setRelatedTables(List<RelatedTableDTO> relatedTables) {
        this.relatedTables = relatedTables;
    }
    
    
    

}
