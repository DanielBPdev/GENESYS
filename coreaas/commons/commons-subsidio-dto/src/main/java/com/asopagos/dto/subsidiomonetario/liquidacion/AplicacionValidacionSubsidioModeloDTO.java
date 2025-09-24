package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import com.asopagos.entidades.subsidiomonetario.liquidacion.AplicacionValidacionSubsidio;

/**
 * <b>Descripcion:</b> Clase DTO con los atributos referentes a una aplicación de validación <br/>
 * <b>Módulo:</b> Asopagos - HU 317-513,514,515,516<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class AplicacionValidacionSubsidioModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la aplicación validación subsidio
     */
    private Long idAplicacionValidacionSubsidio;

    /**
     * Identificador del conjunto validación subsidio
     */
    private Long idConjuntoValidacionSubsidio;

    /**
     * Identificador de la solicitud de liquidación de subsidio
     */
    private Long idSolicitudLiquidacionSubsidio;

    /**
     * Indicador de validación
     */
    private Boolean isValidable;

    /**
     * Método que permite realizar la conversión de una entidad AplicacionValidacionSubsidio a su correspondiente DTO
     * @param aplicacionValidacion
     */
    public void convertToDTO(AplicacionValidacionSubsidio aplicacionValidacion) {
        this.idAplicacionValidacionSubsidio = aplicacionValidacion.getIdAplicacionValidacionSubsidio();
        this.idConjuntoValidacionSubsidio = aplicacionValidacion.getIdConjuntoValidacionSubsidio();
        this.idSolicitudLiquidacionSubsidio = aplicacionValidacion.getIdSolicitudLiquidacionSubsidio();
        this.isValidable = aplicacionValidacion.getIsValidable();
    }

    /**
     * Método que permite realizar la conversión del DTO a la entidad AplicacionValidacionSubsidio
     * @return
     */
    public AplicacionValidacionSubsidio converToEntity() {
        AplicacionValidacionSubsidio aplicacionValidacionSubsidio = new AplicacionValidacionSubsidio();
        aplicacionValidacionSubsidio.setIdAplicacionValidacionSubsidio(this.getIdAplicacionValidacionSubsidio());
        aplicacionValidacionSubsidio.setIdConjuntoValidacionSubsidio(this.getIdConjuntoValidacionSubsidio());
        aplicacionValidacionSubsidio.setIdSolicitudLiquidacionSubsidio(this.getIdSolicitudLiquidacionSubsidio());
        aplicacionValidacionSubsidio.setIsValidable(this.getIsValidable());

        return aplicacionValidacionSubsidio;
    }

    /**
     * @return the idAplicacionValidacionSubsidio
     */
    public Long getIdAplicacionValidacionSubsidio() {
        return idAplicacionValidacionSubsidio;
    }

    /**
     * @param idAplicacionValidacionSubsidio
     *        the idAplicacionValidacionSubsidio to set
     */
    public void setIdAplicacionValidacionSubsidio(Long idAplicacionValidacionSubsidio) {
        this.idAplicacionValidacionSubsidio = idAplicacionValidacionSubsidio;
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

    /**
     * @return the idSolicitudLiquidacionSubsidio
     */
    public Long getIdSolicitudLiquidacionSubsidio() {
        return idSolicitudLiquidacionSubsidio;
    }

    /**
     * @param idSolicitudLiquidacionSubsidio
     *        the idSolicitudLiquidacionSubsidio to set
     */
    public void setIdSolicitudLiquidacionSubsidio(Long idSolicitudLiquidacionSubsidio) {
        this.idSolicitudLiquidacionSubsidio = idSolicitudLiquidacionSubsidio;
    }

    /**
     * @return the isValidable
     */
    public Boolean getIsValidable() {
        return isValidable;
    }

    /**
     * @param isValidable
     *        the isValidable to set
     */
    public void setIsValidable(Boolean isValidable) {
        this.isValidable = isValidable;
    }

}
