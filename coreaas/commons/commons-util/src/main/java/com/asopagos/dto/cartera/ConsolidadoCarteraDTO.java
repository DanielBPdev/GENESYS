package com.asopagos.dto.cartera;

import java.io.Serializable;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ConsolidadoCarteraDTO implements Serializable{

    /**
     * Serial version
     */
    private static final long serialVersionUID = -7977673988421442286L;

    /**
     * Identificador de cartera
     */
    private Long idCartera;
    
    /**
     * Descripción del tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;
    
    /**
     * Tipo de solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

    /**
     * Tipo de acción de cobro para el proceso de Cartera
     */
    private TipoAccionCobroEnum accionCobro;
    
    /**
     * Método constructor
     */
    public ConsolidadoCarteraDTO() {
    }

    /**
     * Método constructor
     * 
     * @param idCartera Identificador de la entidad Cartera
     * @param tipoIdentificacion Tipo de identificación de la persona asociada a Cartera
     * @param numeroIdentificacion Número de identificación de la persona asociada a Cartera
     * @param tipoSolicitante Tipo de solicitante de Cartera
     */
    public ConsolidadoCarteraDTO(Long idCartera, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante, TipoAccionCobroEnum accionCobro) {
        this.idCartera = idCartera;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoSolicitante = tipoSolicitante;
        this.accionCobro = accionCobro;
    }

    /**
     * @return the idCartera
     */
    public Long getIdCartera() {
        return idCartera;
    }

    /**
     * @param idCartera the idCartera to set
     */
    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the tipoSolicitante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the accionCobro
     */
    public TipoAccionCobroEnum getAccionCobro() {
        return accionCobro;
    }

    /**
     * @param accionCobro the accionCobro to set
     */
    public void setAccionCobro(TipoAccionCobroEnum accionCobro) {
        this.accionCobro = accionCobro;
    }
}
