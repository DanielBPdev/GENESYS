package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

public class InfoHijoBiologicoOutDTO implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Tipo de identificación del padre biológico
    */
    private String tipoIdHijoBiologico;

    /**
    * Número de identificación del padre biológico
    */
    private String identificacionHijoBiologico;

    /**
    * Nombre completo del padre biológico
    */
    private String nombreHijoBiologico;

    /**
     * 
     */
    public InfoHijoBiologicoOutDTO() {
    }

    /**
     * @param tipoIdHijoBiologico
     * @param identificacionHijoBiologico
     * @param nombreHijoBiologico
     */
    public InfoHijoBiologicoOutDTO(String tipoIdHijoBiologico, String identificacionHijoBiologico, String nombreHijoBiologico) {
        this.tipoIdHijoBiologico = tipoIdHijoBiologico;
        this.identificacionHijoBiologico = identificacionHijoBiologico;
        this.nombreHijoBiologico = nombreHijoBiologico;
    }

    /**
     * @return the tipoIdHijoBiologico
     */
    public String getTipoIdHijoBiologico() {
        return tipoIdHijoBiologico;
    }

    /**
     * @param tipoIdHijoBiologico the tipoIdHijoBiologico to set
     */
    public void setTipoIdHijoBiologico(String tipoIdHijoBiologico) {
        this.tipoIdHijoBiologico = tipoIdHijoBiologico;
    }

    /**
     * @return the identificacionHijoBiologico
     */
    public String getIdentificacionHijoBiologico() {
        return identificacionHijoBiologico;
    }

    /**
     * @param identificacionHijoBiologico the identificacionHijoBiologico to set
     */
    public void setIdentificacionHijoBiologico(String identificacionHijoBiologico) {
        this.identificacionHijoBiologico = identificacionHijoBiologico;
    }

    /**
     * @return the nombreHijoBiologico
     */
    public String getNombreHijoBiologico() {
        return nombreHijoBiologico;
    }

    /**
     * @param nombreHijoBiologico the nombreHijoBiologico to set
     */
    public void setNombreHijoBiologico(String nombreHijoBiologico) {
        this.nombreHijoBiologico = nombreHijoBiologico;
    }
}
