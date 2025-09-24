package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información de resultado para una validación en el proceso de liquidación por
 * fallecimiento
 * <b>Módulo:</b> Asopagos - HU-317-513,514,515,516 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoValidacionFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Tipo de validación realizada
     */
    private ConjuntoValidacionSubsidioEnum validacion;

    /**
     * Resultado del cumplimiento sobre la validación aplicada
     */
    private Boolean resultado;

    /**
     * Identificador del conjunto validación
     */
    private Long idConjuntoValidacionSubsidio;

    /**
     * @return the validacion
     */
    public ConjuntoValidacionSubsidioEnum getValidacion() {
        return validacion;
    }

    /**
     * @param validacion
     *        the validacion to set
     */
    public void setValidacion(ConjuntoValidacionSubsidioEnum validacion) {
        this.validacion = validacion;
    }

    /**
     * @return the resultado
     */
    public Boolean getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *        the resultado to set
     */
    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the idConjuntoValidacionSubsidio
     */
    public Long getIdConjuntoValidacionSubsidio() {
        return idConjuntoValidacionSubsidio;
    }

    /**
     * @param idConjuntoValidacionSubsidio
     *        the idConjuntoValidacionSubsidio to set
     */
    public void setIdConjuntoValidacionSubsidio(Long idConjuntoValidacionSubsidio) {
        this.idConjuntoValidacionSubsidio = idConjuntoValidacionSubsidio;
    }

}
