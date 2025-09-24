package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;

/**
 * DTO que contiene la línea de cobro con sus detalles de cartera de mora
 * @author Silvio Alexander López Herrera <silopez@heinsohn.com.co>
 * @created 21-feb-2017 3:31:37 p.m.
 */
public class GestionLineaCobroDTO implements Serializable {
    
    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = 7027950785126519252L;

    /**
     * Metodo de accion de cobro solo para la Línea de cobro 1
     */
    private MetodoAccionCobroEnum metodo;
    
    /**
     * Detalles de cartera mora
     */
    private List<GestionDetalleCarteraMoraDTO> detallesCarteraMora;

    /**
     * Método que retorna el valor de metodo.
     * @return valor de metodo.
     */
    public MetodoAccionCobroEnum getMetodo() {
        return metodo;
    }

    /**
     * Método encargado de modificar el valor de metodo.
     * @param valor para modificar metodo.
     */
    public void setMetodo(MetodoAccionCobroEnum metodo) {
        this.metodo = metodo;
    }

    /**
     * Método que retorna el valor de detallesCarteraMora.
     * @return valor de detallesCarteraMora.
     */
    public List<GestionDetalleCarteraMoraDTO> getDetallesCarteraMora() {
        return detallesCarteraMora;
    }

    /**
     * Método encargado de modificar el valor de detallesCarteraMora.
     * @param valor para modificar detallesCarteraMora.
     */
    public void setDetallesCarteraMora(List<GestionDetalleCarteraMoraDTO> detallesCarteraMora) {
        this.detallesCarteraMora = detallesCarteraMora;
    }     
    
    
}
