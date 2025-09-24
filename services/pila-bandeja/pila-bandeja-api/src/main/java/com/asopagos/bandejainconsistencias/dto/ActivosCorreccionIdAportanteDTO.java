package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;

/**
 * <b>Descripcion:</b> DTO que contiene los entities que son modificados por el cambio en el número de 
 * identificación del aportante<br/>
 * <b>Módulo:</b> Asopagos - HU-211-392 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ActivosCorreccionIdAportanteDTO implements Serializable {
    private static final long serialVersionUID = -1226895260691525825L;
    
    private IndicePlanillaModeloDTO indiceA;
    private IndicePlanillaModeloDTO indiceI;
    private IndicePlanillaOF indiceF;
    private EstadoArchivoPorBloque estadosI;
    private Object registro1I;
    private Object registro1A;
    private PilaArchivoFRegistro6 registro6F;
    private SolicitudCambioNumIdentAportante solicitudCambio;
    private HistorialEstadoBloque historialEstadoIAprobacion;
    private HistorialEstadoBloque historialEstadoI;
    
    /**
     * @return the indiceA
     */
    public IndicePlanillaModeloDTO getIndiceA() {
        return indiceA;
    }
    /**
     * @param indiceA the indiceA to set
     */
    public void setIndiceA(IndicePlanillaModeloDTO indiceA) {
        this.indiceA = indiceA;
    }
    /**
     * @return the indiceI
     */
    public IndicePlanillaModeloDTO getIndiceI() {
        return indiceI;
    }
    /**
     * @param indiceI the indiceI to set
     */
    public void setIndiceI(IndicePlanillaModeloDTO indiceI) {
        this.indiceI = indiceI;
    }
    /**
     * @return the registro1I
     */
    public Object getRegistro1I() {
        return registro1I;
    }
    /**
     * @param registro1i the registro1I to set
     */
    public void setRegistro1I(Object registro1i) {
        registro1I = registro1i;
    }
    /**
     * @return the registro1A
     */
    public Object getRegistro1A() {
        return registro1A;
    }
    /**
     * @param registro1a the registro1A to set
     */
    public void setRegistro1A(Object registro1a) {
        registro1A = registro1a;
    }
    /**
     * @return the registro6F
     */
    public PilaArchivoFRegistro6 getRegistro6F() {
        return registro6F;
    }
    /**
     * @param registro6f the registro6F to set
     */
    public void setRegistro6F(PilaArchivoFRegistro6 registro6f) {
        registro6F = registro6f;
    }
    /**
     * @return the estadosI
     */
    public EstadoArchivoPorBloque getEstadosI() {
        return estadosI;
    }
    /**
     * @param estadosI the estadosI to set
     */
    public void setEstadosI(EstadoArchivoPorBloque estadosI) {
        this.estadosI = estadosI;
    }
    /**
     * @return the solicitudCambio
     */
    public SolicitudCambioNumIdentAportante getSolicitudCambio() {
        return solicitudCambio;
    }
    /**
     * @param solicitudCambio the solicitudCambio to set
     */
    public void setSolicitudCambio(SolicitudCambioNumIdentAportante solicitudCambio) {
        this.solicitudCambio = solicitudCambio;
    }
    /**
     * @return the indiceF
     */
    public IndicePlanillaOF getIndiceF() {
        return indiceF;
    }
    /**
     * @param indiceF the indiceF to set
     */
    public void setIndiceF(IndicePlanillaOF indiceF) {
        this.indiceF = indiceF;
    }
    /**
     * @return the historialEstadoIAprobacion
     */
    public HistorialEstadoBloque getHistorialEstadoIAprobacion() {
        return historialEstadoIAprobacion;
    }
    /**
     * @param historialEstadoIAprobacion the historialEstadoIAprobacion to set
     */
    public void setHistorialEstadoIAprobacion(HistorialEstadoBloque historialEstadoIAprobacion) {
        this.historialEstadoIAprobacion = historialEstadoIAprobacion;
    }
    /**
     * @return the historialEstadoI
     */
    public HistorialEstadoBloque getHistorialEstadoI() {
        return historialEstadoI;
    }
    /**
     * @param historialEstadoI the historialEstadoI to set
     */
    public void setHistorialEstadoI(HistorialEstadoBloque historialEstadoI) {
        this.historialEstadoI = historialEstadoI;
    }
}
