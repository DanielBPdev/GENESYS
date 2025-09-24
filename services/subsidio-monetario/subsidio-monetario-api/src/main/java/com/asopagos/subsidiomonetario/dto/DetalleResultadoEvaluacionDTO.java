package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información de detalle para el resultado de evaluación de una liquidación especifica<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-523<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DetalleResultadoEvaluacionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tipo de identificación del beneficiario asociado a la liquidcion */
    private TipoIdentificacionEnum tipoIdentificacion;

    /** Número de identificación del benedifiario */
    private Long numeroIdentificacion;

    /** Nombre del beneficiario */
    private String nombre;

    /** Parentesco del beneficiario con la persona asociada a la liquidacion */
    private TipoBeneficiarioEnum parentesco;

    /** Resultado que indica si el beneficiario cumple o no cumple */
    private TipoCumplimientoEnum resultado;

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(Long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the parentesco
     */
    public TipoBeneficiarioEnum getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *        the parentesco to set
     */
    public void setParentesco(TipoBeneficiarioEnum parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * @return the resultado
     */
    public TipoCumplimientoEnum getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *        the resultado to set
     */
    public void setResultado(TipoCumplimientoEnum resultado) {
        this.resultado = resultado;
    }

}
