package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import com.asopagos.entidades.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersona;

/**
 * <b>Descripcion:</b> Clase DTO con los atributos referentes a una aplicación de validación subsidio persona <br/>
 * <b>Módulo:</b> Asopagos - HU 317-513,514,515,516<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class AplicacionValidacionSubsidioPersonaModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la Aplicación Validacion Subsidio Persona
     */
    private Long idAplicacionValidacionSubsidioPersona;

    /**
     * Identificador de la Aplicación Validación Subsidio
     */
    private Long idAplicacionValidacionSubsidio;

    /**
     * Identificador de la PersonaLiquidaciónEspecífica
     */
    private Long idPersonaLiquidacionEspecifica;

    /**
     * Método que se encarga de realizar la conversión de una entidad de tipo AplicacionValidacionSubsidioPersona a su correspondiente DTO
     * @param aplicacionValidacionPersona
     */
    public void converToDTO(AplicacionValidacionSubsidioPersona aplicacionValidacionPersona) {
        this.idAplicacionValidacionSubsidioPersona = aplicacionValidacionPersona.getIdAplicacionValidacionSubsidioPersona();
        this.idAplicacionValidacionSubsidio = aplicacionValidacionPersona.getIdAplicacionValidacionSubsidio();
        this.idPersonaLiquidacionEspecifica = aplicacionValidacionPersona.getIdPersonaLiquidacionEspecifica();
    }

    /**
     * Método que permite realizar la conversión del DTO AplicacionValidacionSubsidioPersonaDTO a su correspondiente entidad
     * @return
     */
    public AplicacionValidacionSubsidioPersona convertToEntity() {
        AplicacionValidacionSubsidioPersona aplicacionValidacion = new AplicacionValidacionSubsidioPersona();
        aplicacionValidacion.setIdAplicacionValidacionSubsidioPersona(this.getIdAplicacionValidacionSubsidioPersona());
        aplicacionValidacion.setIdAplicacionValidacionSubsidio(this.getIdAplicacionValidacionSubsidio());
        aplicacionValidacion.setIdPersonaLiquidacionEspecifica(this.getIdPersonaLiquidacionEspecifica());
        return aplicacionValidacion;
    }

    /**
     * @return the idAplicacionValidacionSubsidioPersona
     */
    public Long getIdAplicacionValidacionSubsidioPersona() {
        return idAplicacionValidacionSubsidioPersona;
    }

    /**
     * @param idAplicacionValidacionSubsidioPersona
     *        the idAplicacionValidacionSubsidioPersona to set
     */
    public void setIdAplicacionValidacionSubsidioPersona(Long idAplicacionValidacionSubsidioPersona) {
        this.idAplicacionValidacionSubsidioPersona = idAplicacionValidacionSubsidioPersona;
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
     * @return the idPersonaLiquidacionEspecifica
     */
    public Long getIdPersonaLiquidacionEspecifica() {
        return idPersonaLiquidacionEspecifica;
    }

    /**
     * @param idPersonaLiquidacionEspecifica
     *        the idPersonaLiquidacionEspecifica to set
     */
    public void setIdPersonaLiquidacionEspecifica(Long idPersonaLiquidacionEspecifica) {
        this.idPersonaLiquidacionEspecifica = idPersonaLiquidacionEspecifica;
    }

}
