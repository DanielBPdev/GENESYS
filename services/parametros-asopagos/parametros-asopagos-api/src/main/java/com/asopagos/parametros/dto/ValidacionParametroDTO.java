package com.asopagos.parametros.dto;

import java.util.List;
import com.asopagos.parametros.GrupoValidacionEnum;
import com.asopagos.parametros.TipoValidacionEnum;
import com.asopagos.parametros.ValidacionParametro;

/**
 * <b>Descripcion:</b> Clase que representa la validacion asignada de un dato<br/>
 * <b>Módulo:</b> Asopagos - HU Transversal <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public class ValidacionParametroDTO {
        
    /**
     * lista de grupo de validacion
     */
    private List<GrupoValidacionEnum> grupoValidacion;
    
    /**
     * tipo de validacion
     */
    private TipoValidacionEnum tipoValidacion;
    
    /**
     * minimo de caracteres
     */
    private int min;
    
    /**
     * maximo de caracteres
     */
    private int max;
    
    public ValidacionParametroDTO(){
        
    }
    
    public static ValidacionParametroDTO convertToDTO(ValidacionParametro validacionParametro){
        
        ValidacionParametroDTO validacionParametroDTO =  new ValidacionParametroDTO();
        
        validacionParametroDTO.setGrupoValidacion(validacionParametro.getGrupoValidacion());
        
        validacionParametroDTO.setMax(validacionParametro.getMax());
        validacionParametroDTO.setMin(validacionParametro.getMin());
        validacionParametroDTO.setTipoValidacion(validacionParametro.getTipoValidacion());
        
        return validacionParametroDTO;
    }
    
    /**
     * @param grupoValidacion
     * @param tipoValidacion
     * @param min
     * @param max
     */
    public ValidacionParametroDTO(List<GrupoValidacionEnum> grupoValidacion, TipoValidacionEnum tipoValidacion, int min, int max) {
        this.grupoValidacion = grupoValidacion;
        this.tipoValidacion = tipoValidacion;
        this.min = min;
        this.max = max;
    }
    /**
     * @return the grupoValidacion
     */
    public List<GrupoValidacionEnum> getGrupoValidacion() {
        return grupoValidacion;
    }
    /**
     * @param grupoValidacion the grupoValidacion to set
     */
    public void setGrupoValidacion(List<GrupoValidacionEnum> grupoValidacion) {
        this.grupoValidacion = grupoValidacion;
    }
    /**
     * @return the tipoValidacion
     */
    public TipoValidacionEnum getTipoValidacion() {
        return tipoValidacion;
    }
    /**
     * @param tipoValidacion the tipoValidacion to set
     */
    public void setTipoValidacion(TipoValidacionEnum tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }
    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}
