package com.asopagos.aportes.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ModificarTasaInteresMoraDTO {
    
    /**
     * Id de la tasa de interes
     */
    private Long idTasa;
    
    /**
     * Periodo de la tasa de interes
     */
    private Long periodo;
    
    /**
     * Tasa de interes de mora
     */
    private BigDecimal tasaInteres;
    
    /**
     * Resolucion de la tasa de interes
     */
    private String normativa;

    /**
     * @return the idTasa
     */
    public Long getIdTasa() {
        return idTasa;
    }

    /**
     * @param idTasa the idTasa to set
     */
    public void setIdTasa(Long idTasa) {
        this.idTasa = idTasa;
    }

    /**
     * @return the periodo
     */
    public Long getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the tasaInteres
     */
    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    /**
     * @param tasaInteres the tasaInteres to set
     */
    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    /**
     * @return the normativa
     */
    public String getNormativa() {
        return normativa;
    }

    /**
     * @param normativa the normativa to set
     */
    public void setNormativa(String normativa) {
        this.normativa = normativa;
    }
    
}
