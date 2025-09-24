package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resumen de resultado de evaluación para una liquidación específica de fallecimiento<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-317-507<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResumenResultadosEvaluacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de condición para el fallecido (beneficiario o afiliado)
     */
    private Long idCondicionTrabajador;

    /**
     * Identificador de condición persona para el trabajador
     */
    private Long idCondicionPersona;

    /**
     * Nombre del afiliado principal
     */
    private String nombreTrabajador;

    /**
     * Resultado del cumplimiento para el beneficio de subsidio
     */
    private TipoCumplimientoEnum cumpleCondicionesTrabajador;

    /**
     * Tipo de identificación del beneficiario de subsidio
     */
    private TipoIdentificacionEnum tipoIdentificacionTrabajador;

    /**
     * Número de identificación del afiliado principal
     */
    private String numeroIdentificacionTrabajador;

    /**
     * @return the idCondicionTrabajador
     */
    public Long getIdCondicionTrabajador() {
        return idCondicionTrabajador;
    }

    /**
     * @param idCondicionTrabajador
     *        the idCondicionTrabajador to set
     */
    public void setIdCondicionTrabajador(Long idCondicionTrabajador) {
        this.idCondicionTrabajador = idCondicionTrabajador;
    }

    /**
     * @return the nombreTrabajador
     */
    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    /**
     * @return the idCondicionPersona
     */
    public Long getIdCondicionPersona() {
        return idCondicionPersona;
    }

    /**
     * @param idCondicionPersona
     *        the idCondicionPersona to set
     */
    public void setIdCondicionPersona(Long idCondicionPersona) {
        this.idCondicionPersona = idCondicionPersona;
    }

    /**
     * @param nombreTrabajador
     *        the nombreTrabajador to set
     */
    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    /**
     * @return the cumpleCondicionesTrabajador
     */
    public TipoCumplimientoEnum getCumpleCondicionesTrabajador() {
        return cumpleCondicionesTrabajador;
    }

    /**
     * @param cumpleCondicionesTrabajador
     *        the cumpleCondicionesTrabajador to set
     */
    public void setCumpleCondicionesTrabajador(TipoCumplimientoEnum cumpleCondicionesTrabajador) {
        this.cumpleCondicionesTrabajador = cumpleCondicionesTrabajador;
    }

    /**
     * @return the tipoIdentificacionTrabajador
     */
    public TipoIdentificacionEnum getTipoIdentificacionTrabajador() {
        return tipoIdentificacionTrabajador;
    }

    /**
     * @param tipoIdentificacionTrabajador
     *        the tipoIdentificacionTrabajador to set
     */
    public void setTipoIdentificacionTrabajador(TipoIdentificacionEnum tipoIdentificacionTrabajador) {
        this.tipoIdentificacionTrabajador = tipoIdentificacionTrabajador;
    }

    /**
     * @return the numeroIdentificacionTrabajador
     */
    public String getNumeroIdentificacionTrabajador() {
        return numeroIdentificacionTrabajador;
    }

    /**
     * @param numeroIdentificacionTrabajador
     *        the numeroIdentificacionTrabajador to set
     */
    public void setNumeroIdentificacionTrabajador(String numeroIdentificacionTrabajador) {
        this.numeroIdentificacionTrabajador = numeroIdentificacionTrabajador;
    }

}
