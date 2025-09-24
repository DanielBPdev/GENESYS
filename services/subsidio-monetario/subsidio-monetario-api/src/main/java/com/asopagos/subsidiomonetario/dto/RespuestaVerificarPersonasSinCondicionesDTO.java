package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author amarin
 *
 */
public class RespuestaVerificarPersonasSinCondicionesDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Indica si la persona tiene o no condiciones en staging
     */
    private Boolean sinCondiciones;
    
    /**
     * Lista de los periodos en los que las personas no tienen condiciones en staging
     */
    private List<Long> periodosSinCondiciones;
    
    /**
     * Lista de personas para las que no se tienen condiciones en staging
     */
    private List<Integer> personasSinCondiciones;
    
    /**
     * Lista de empleadores para los que no se tienen condiciones en el staging
     */
    private List<Integer> empleadoresSinCondiciones;

    /**
     * @return the empleadoresSinCondiciones
     */
    public List<Integer> getEmpleadoresSinCondiciones() {
        return empleadoresSinCondiciones;
    }

    /**
     * @param empleadoresSinCondiciones the empleadoresSinCondiciones to set
     */
    public void setEmpleadoresSinCondiciones(List<Integer> empleadoresSinCondiciones) {
        this.empleadoresSinCondiciones = empleadoresSinCondiciones;
    }

    /**
     * @return the personasSinCondiciones
     */
    public List<Integer> getPersonasSinCondiciones() {
        return personasSinCondiciones;
    }

    /**
     * @param personasSinCondiciones the personasSinCondiciones to set
     */
    public void setPersonasSinCondiciones(List<Integer> personasSinCondiciones) {
        this.personasSinCondiciones = personasSinCondiciones;
    }

    /**
     * @return the sinCondiciones
     */
    public Boolean getSinCondiciones() {
        return sinCondiciones;
    }

    /**
     * @param sinCondiciones the sinCondiciones to set
     */
    public void setSinCondiciones(Boolean sinCondiciones) {
        this.sinCondiciones = sinCondiciones;
    }

    /**
     * @return the periodosSinCondiciones
     */
    public List<Long> getPeriodosSinCondiciones() {
        return periodosSinCondiciones;
    }

    /**
     * @param periodosSinCondicones the periodosSinCondiciones to set
     */
    public void setPeriodosSinCondiciones(List<Long> periodosSinCondiciones) {
        this.periodosSinCondiciones = periodosSinCondiciones;
    }
}
