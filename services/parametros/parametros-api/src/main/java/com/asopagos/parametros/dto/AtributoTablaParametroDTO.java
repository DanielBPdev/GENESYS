package com.asopagos.parametros.dto;
import java.util.ArrayList;
import java.util.List;
import com.asopagos.enumeraciones.core.TipoListaParametroEnum;
import com.asopagos.enumeraciones.core.TipoParametroEnum;
import com.asopagos.parametros.AtributoTablaParametro;
import com.asopagos.parametros.ValidacionParametro;

/**
 * <b>Descripcion:</b> Clase que representa el atributo de un paramtro<br/>
 * <b>Módulo:</b> Asopagos - HU Transversal<br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public class AtributoTablaParametroDTO {

    /**
     * Nombre representativo 
     */
    private String nombre;
    
    /**
     * Nombre del atributo
     */
    private String nombreAtributo;
    
    /**
     * Tipo de dato
     */
    private String tipoDato;
    
    /**
     * Define el tipo de parametro a implementar
     */
    private TipoParametroEnum tipoParametro;

    /**
     * Define el tipo de lista de un parametro
     */
    private TipoListaParametroEnum tipoListaParametro;
    
    /**
     * @return the tipoListaParametro
     */
    public TipoListaParametroEnum getTipoListaParametro() {
        return tipoListaParametro;
    }

    /**
     * @param tipoListaParametro the tipoListaParametro to set
     */
    public void setTipoListaParametro(TipoListaParametroEnum tipoListaParametro) {
        this.tipoListaParametro = tipoListaParametro;
    }
    
    /**
     * lista de validaciones
     */
    private List<ValidacionParametroDTO> validaciones;
    
   
    public AtributoTablaParametroDTO(){
        
    }
    
    public static AtributoTablaParametroDTO convertToDTO(AtributoTablaParametro atributoTablaParametro){
        
        AtributoTablaParametroDTO atributoTablaParametroDTO = new AtributoTablaParametroDTO();

        atributoTablaParametroDTO.setNombre(atributoTablaParametro.getNombre());
        atributoTablaParametroDTO.setNombreAtributo(atributoTablaParametro.getNombreAtributo());
        atributoTablaParametroDTO.setTipoDato(atributoTablaParametro.getTipoDato());
        atributoTablaParametroDTO.setTipoParametro(atributoTablaParametro.getTipoParametro());
        atributoTablaParametroDTO.setTipoListaParametro(atributoTablaParametro.getTipoListaParametro());
        
        List<ValidacionParametro> validacionesParametro = atributoTablaParametro.getValidaciones();        
        
        List<ValidacionParametroDTO> validacionesParametroDTO = new ArrayList<>(); 
        
        if(validacionesParametro != null && !validacionesParametro.isEmpty()){
            for (ValidacionParametro validacionParametro : validacionesParametro) {
                
                validacionesParametroDTO.add(ValidacionParametroDTO.convertToDTO(validacionParametro));
            }            
        }
                
        atributoTablaParametroDTO.setValidaciones(validacionesParametroDTO);
        
        return atributoTablaParametroDTO;
    }
 
    
    

    /**
     * @param nombre
     * @param nombreAtributo
     * @param tipoDato
     * @param tipoParametro
     * @param validaciones
     */
    public AtributoTablaParametroDTO(String nombre, String nombreAtributo, String tipoDato, TipoParametroEnum tipoParametro,
            List<ValidacionParametroDTO> validaciones) {

        this.nombre = nombre;
        this.nombreAtributo = nombreAtributo;
        this.tipoDato = tipoDato;
        this.tipoParametro = tipoParametro;
        this.validaciones = validaciones;
    }

    /**
     * @return the tipoParametro
     */
    public TipoParametroEnum getTipoParametro() {
        return tipoParametro;
    }

    /**
     * @param tipoParametro the tipoParametro to set
     */
    public void setTipoParametro(TipoParametroEnum tipoParametro) {
        this.tipoParametro = tipoParametro;
    }
    
    /**
     * @return the nombreAtributo
     */
    public String getNombreAtributo() {
        return nombreAtributo;
    }
    /**
     * @param nombreAtributo the nombreAtributo to set
     */
    public void setNombreAtributo(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
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
     * @return the tipoDato
     */
    public String getTipoDato() {
        return tipoDato;
    }
    /**
     * @param tipoDato the tipoDato to set
     */
    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
    /**
     * @return the validaciones
     */
    public List<ValidacionParametroDTO> getValidaciones() {
        return validaciones;
    }
    /**
     * @param validaciones the validaciones to set
     */
    public void setValidaciones(List<ValidacionParametroDTO> validaciones) {
        this.validaciones = validaciones;
    }
    
    
    
}
