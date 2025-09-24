package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO con los datos de control para la validación de subgrupos de registros
 * en planillas de corrección<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ControlSubgrupoCorreccionDTO implements Serializable {
    private static final long serialVersionUID = 2797777600922285019L;

    /** Tipo ID Cotizante */
    private TipoIdentificacionEnum tipoId;
    
    /** Número ID Cotizante */
    private String numId;
    
    /** Valor Correcciones primer registro */
    private String primeraCorreccion;
    
    /** Valor Correcciones último registro */
    private String ultimaCorreccion;
    
    /** Cantidad de registros A */
    private Integer cantidadA;
    
    /** Cantidad de registros C */
    private Integer cantidadC;
    
    /** Números de línea del subgrupo */
    private Set<Long> numerosDeLinea;
    
    /** Número de línea de la primera corrección */
    private Long lineaPrimera;
    
    /** Número de línea de la última corrección */
    private Long lineaUltima;
    
    /**
     * Constructor de clase 
     * */
    public ControlSubgrupoCorreccionDTO(){
        super();
        numerosDeLinea = new HashSet<>();
    }

    /**
     * @return the tipoId
     */
    public TipoIdentificacionEnum getTipoId() {
        return tipoId;
    }

    /**
     * @param tipoId the tipoId to set
     */
    public void setTipoId(TipoIdentificacionEnum tipoId) {
        this.tipoId = tipoId;
    }

    /**
     * @return the numId
     */
    public String getNumId() {
        return numId;
    }

    /**
     * @param numId the numId to set
     */
    public void setNumId(String numId) {
        this.numId = numId;
    }

    /**
     * @return the primeraCorreccion
     */
    public String getPrimeraCorreccion() {
        return primeraCorreccion;
    }

    /**
     * @param primeraCorreccion the primeraCorreccion to set
     */
    public void setPrimeraCorreccion(String primeraCorreccion) {
        this.primeraCorreccion = primeraCorreccion;
    }

    /**
     * @return the ultimaCorreccion
     */
    public String getUltimaCorreccion() {
        return ultimaCorreccion;
    }

    /**
     * @param ultimaCorreccion the ultimaCorreccion to set
     */
    public void setUltimaCorreccion(String ultimaCorreccion) {
        this.ultimaCorreccion = ultimaCorreccion;
    }

    /**
     * @return the cantidadA
     */
    public Integer getCantidadA() {
        return cantidadA;
    }

    /**
     * @param cantidadA the cantidadA to set
     */
    public void setCantidadA(Integer cantidadA) {
        this.cantidadA = cantidadA;
    }

    /**
     * @return the cantidadC
     */
    public Integer getCantidadC() {
        return cantidadC;
    }

    /**
     * @param cantidadC the cantidadC to set
     */
    public void setCantidadC(Integer cantidadC) {
        this.cantidadC = cantidadC;
    }

    /**
     * @return the numerosDeLinea
     */
    public Set<Long> getNumerosDeLinea() {
        return numerosDeLinea;
    }

    /**
     * @param numerosDeLinea the numerosDeLinea to set
     */
    public void setNumerosDeLinea(Set<Long> numerosDeLinea) {
        this.numerosDeLinea = numerosDeLinea;
    }

    /**
     * @return the lineaPrimera
     */
    public Long getLineaPrimera() {
        return lineaPrimera;
    }

    /**
     * @param lineaPrimera the lineaPrimera to set
     */
    public void setLineaPrimera(Long lineaPrimera) {
        this.lineaPrimera = lineaPrimera;
    }

    /**
     * @return the lineaUltima
     */
    public Long getLineaUltima() {
        return lineaUltima;
    }

    /**
     * @param lineaUltima the lineaUltima to set
     */
    public void setLineaUltima(Long lineaUltima) {
        this.lineaUltima = lineaUltima;
    }
}