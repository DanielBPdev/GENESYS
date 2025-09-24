package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author amarin
 *
 */
public class VerificarPersonasSinCondicionesDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de periodos a consultar
     */
    private List<Long> periodos;
    
    /**
     * Lista de personas a consultar
     */
    private List<Integer> idPersonas;
    
    /**
     * Lista de empleadores a consultar
     */
    private List<Integer> idEmpleadores;

    /**
     * @return the periodos
     */
    public List<Long> getPeriodos() {
        return periodos;
    }

    /**
     * @param periodos the periodos to set
     */
    public void setPeriodos(List<Long> periodos) {
        this.periodos = periodos;
    }

    /**
     * @return the idPersonas
     */
    public List<Integer> getIdPersonas() {
        return idPersonas;
    }

    /**
     * @param idPersonas the idPersonas to set
     */
    public void setIdPersonas(List<Integer> idPersonas) {
        this.idPersonas = idPersonas;
    }

    /**
     * @return the idEmpleadores
     */
    public List<Integer> getIdEmpleadores() {
        return idEmpleadores;
    }

    /**
     * @param idEmpleadores the idEmpleadores to set
     */
    public void setIdEmpleadores(List<Integer> idEmpleadores) {
        this.idEmpleadores = idEmpleadores;
    }
}
