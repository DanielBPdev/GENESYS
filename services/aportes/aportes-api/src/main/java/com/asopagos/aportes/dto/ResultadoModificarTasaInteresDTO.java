package com.asopagos.aportes.dto;

/**
 * Clase DTO para la respuesta de la modificacion de la tabla TasaInteresMora
 * @author amarin
 *
 */
public class ResultadoModificarTasaInteresDTO {
    
    /**
     * respuesta de la consulta
     */
    private Boolean respuestaServicio;
    
    /**
     * Bandera para saber si la tasa fue modificada 
     */
    private Boolean modificaTasa;

    /**
     * @return the respuestaServicio
     */
    public Boolean getRespuestaServicio() {
        return respuestaServicio;
    }

    /**
     * @param respuestaServicio the respuestaServicio to set
     */
    public void setRespuestaServicio(Boolean respuestaServicio) {
        this.respuestaServicio = respuestaServicio;
    }

    /**
     * @return the modificaTasa
     */
    public Boolean getModificaTasa() {
        return modificaTasa;
    }

    /**
     * @param modificaTasa the modificaTasa to set
     */
    public void setModificaTasa(Boolean modificaTasa) {
        this.modificaTasa = modificaTasa;
    }
    
    
}
