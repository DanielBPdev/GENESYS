package com.asopagos.pila.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que contiene la respuesta con los contadores 
 * para los archivos de operador financiero<br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda </a>
 */
public class ConsultasArchivosOperadorFinancieroDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Numero de archivos OF procesados finalizados  */
    private Long numArchivosOFProcesadosFinalizados;
    
    /** Numero de archivos OF con inconsistencias  */
    private Long numArchivosOFConInconsistencias;
    
    /** Numero de archivos OF en bandejas de gestion  */
    private Long numArchivosOFBandejasGestion;
    
    /** Numero de archivos OF pendientes por procesar manual  */
    private Long numArchivosOFPendientesProcesarManual;
    
    /** Numero de archivos OF procesados finalizados manual  */
    private Long numArchivosOFProcesadosFinalizadosManual;
    
    /** Numero de archivos OF cargados exitosamente  */
    private Long numArchivosOFCargadosExitosamenteCtrl;
    
    /** Numero de archivos OF en proceso (Control)  */
    private Long numArchivosOFEnProcesoCtrl;
    
    /** Numero de archivos OF con inconsistencias (Control)  */
    private Long numArchivosOFConInconsistenciasCtrl;
    
    /** Numero de archivos OF procesados finalizados (Control)  */
    private Long numArchivosOFProcesadosFinalizadosCtrl;
    
    /** Numero de archivos OF en bandejas de gestion (Control)  */
    private Long numArchivosOFBandejasGestionCtrl;
    
    /** Numero de archivos OF en proceso manual  */
    private Long numAarchivosOFEnProcesoManualCtrl;
    
    /** Numero de archivos OF con procesados finalizados manual  */
    private Long numArchivosOFProcesadosFinalizadosManualCtrl;
 
    /** Registros dentro de los archivos F cargados al sistema */
    private Long numRegistrosFcargados;

    /** Registros dentro de los archivos F que aun no han sido procesados */
    private Long numRegistrosFEnProceso;

    /** Registros dentro de los archivos F procesados conciliados */
    private Long numRegistrosFProcesados;

    /** Registros financieros con inconsistencias */
    private Long numRegistrosFInconsistencias;    
    
    /**
     * @return the numArchivosOFProcesadosFinalizados
     */
    public Long getNumArchivosOFProcesadosFinalizados() {
        return numArchivosOFProcesadosFinalizados;
    }
    /**
     * @param numArchivosOFProcesadosFinalizados the numArchivosOFProcesadosFinalizados to set
     */
    public void setNumArchivosOFProcesadosFinalizados(Long numArchivosOFProcesadosFinalizados) {
        this.numArchivosOFProcesadosFinalizados = numArchivosOFProcesadosFinalizados;
    }
    /**
     * @return the numArchivosOFConInconsistencias
     */
    public Long getNumArchivosOFConInconsistencias() {
        return numArchivosOFConInconsistencias;
    }
    /**
     * @param numArchivosOFConInconsistencias the numArchivosOFConInconsistencias to set
     */
    public void setNumArchivosOFConInconsistencias(Long numArchivosOFConInconsistencias) {
        this.numArchivosOFConInconsistencias = numArchivosOFConInconsistencias;
    }
    /**
     * @return the numArchivosOFBandejasGestion
     */
    public Long getNumArchivosOFBandejasGestion() {
        return numArchivosOFBandejasGestion;
    }
    /**
     * @param numArchivosOFBandejasGestion the numArchivosOFBandejasGestion to set
     */
    public void setNumArchivosOFBandejasGestion(Long numArchivosOFBandejasGestion) {
        this.numArchivosOFBandejasGestion = numArchivosOFBandejasGestion;
    }
    /**
     * @return the numArchivosOFPendientesProcesarManual
     */
    public Long getNumArchivosOFPendientesProcesarManual() {
        return numArchivosOFPendientesProcesarManual;
    }
    /**
     * @param numArchivosOFPendientesProcesarManual the numArchivosOFPendientesProcesarManual to set
     */
    public void setNumArchivosOFPendientesProcesarManual(Long numArchivosOFPendientesProcesarManual) {
        this.numArchivosOFPendientesProcesarManual = numArchivosOFPendientesProcesarManual;
    }
    /**
     * @return the numArchivosOFProcesadosFinalizadosManual
     */
    public Long getNumArchivosOFProcesadosFinalizadosManual() {
        return numArchivosOFProcesadosFinalizadosManual;
    }
    /**
     * @param numArchivosOFProcesadosFinalizadosManual the numArchivosOFProcesadosFinalizadosManual to set
     */
    public void setNumArchivosOFProcesadosFinalizadosManual(Long numArchivosOFProcesadosFinalizadosManual) {
        this.numArchivosOFProcesadosFinalizadosManual = numArchivosOFProcesadosFinalizadosManual;
    }
    /**
     * @return the numArchivosOFCargadosExitosamenteCtrl
     */
    public Long getNumArchivosOFCargadosExitosamenteCtrl() {
        return numArchivosOFCargadosExitosamenteCtrl;
    }
    /**
     * @param numArchivosOFCargadosExitosamenteCtrl the numArchivosOFCargadosExitosamenteCtrl to set
     */
    public void setNumArchivosOFCargadosExitosamenteCtrl(Long numArchivosOFCargadosExitosamenteCtrl) {
        this.numArchivosOFCargadosExitosamenteCtrl = numArchivosOFCargadosExitosamenteCtrl;
    }
    /**
     * @return the numArchivosOFEnProcesoCtrl
     */
    public Long getNumArchivosOFEnProcesoCtrl() {
        return numArchivosOFEnProcesoCtrl;
    }
    /**
     * @param numArchivosOFEnProcesoCtrl the numArchivosOFEnProcesoCtrl to set
     */
    public void setNumArchivosOFEnProcesoCtrl(Long numArchivosOFEnProcesoCtrl) {
        this.numArchivosOFEnProcesoCtrl = numArchivosOFEnProcesoCtrl;
    }
    /**
     * @return the numArchivosOFConInconsistenciasCtrl
     */
    public Long getNumArchivosOFConInconsistenciasCtrl() {
        return numArchivosOFConInconsistenciasCtrl;
    }
    /**
     * @param numArchivosOFConInconsistenciasCtrl the numArchivosOFConInconsistenciasCtrl to set
     */
    public void setNumArchivosOFConInconsistenciasCtrl(Long numArchivosOFConInconsistenciasCtrl) {
        this.numArchivosOFConInconsistenciasCtrl = numArchivosOFConInconsistenciasCtrl;
    }
    /**
     * @return the numArchivosOFProcesadosFinalizadosCtrl
     */
    public Long getNumArchivosOFProcesadosFinalizadosCtrl() {
        return numArchivosOFProcesadosFinalizadosCtrl;
    }
    /**
     * @param numArchivosOFProcesadosFinalizadosCtrl the numArchivosOFProcesadosFinalizadosCtrl to set
     */
    public void setNumArchivosOFProcesadosFinalizadosCtrl(Long numArchivosOFProcesadosFinalizadosCtrl) {
        this.numArchivosOFProcesadosFinalizadosCtrl = numArchivosOFProcesadosFinalizadosCtrl;
    }
    /**
     * @return the numArchivosOFBandejasGestionCtrl
     */
    public Long getNumArchivosOFBandejasGestionCtrl() {
        return numArchivosOFBandejasGestionCtrl;
    }
    /**
     * @param numArchivosOFBandejasGestionCtrl the numArchivosOFBandejasGestionCtrl to set
     */
    public void setNumArchivosOFBandejasGestionCtrl(Long numArchivosOFBandejasGestionCtrl) {
        this.numArchivosOFBandejasGestionCtrl = numArchivosOFBandejasGestionCtrl;
    }
    /**
     * @return the numAarchivosOFEnProcesoManualCtrl
     */
    public Long getNumAarchivosOFEnProcesoManualCtrl() {
        return numAarchivosOFEnProcesoManualCtrl;
    }
    /**
     * @param numAarchivosOFEnProcesoManualCtrl the numAarchivosOFEnProcesoManualCtrl to set
     */
    public void setNumAarchivosOFEnProcesoManualCtrl(Long numAarchivosOFEnProcesoManualCtrl) {
        this.numAarchivosOFEnProcesoManualCtrl = numAarchivosOFEnProcesoManualCtrl;
    }
    /**
     * @return the numArchivosOFProcesadosFinalizadosManualCtrl
     */
    public Long getNumArchivosOFProcesadosFinalizadosManualCtrl() {
        return numArchivosOFProcesadosFinalizadosManualCtrl;
    }
    /**
     * @param numArchivosOFProcesadosFinalizadosManualCtrl the numArchivosOFProcesadosFinalizadosManualCtrl to set
     */
    public void setNumArchivosOFProcesadosFinalizadosManualCtrl(Long numArchivosOFProcesadosFinalizadosManualCtrl) {
        this.numArchivosOFProcesadosFinalizadosManualCtrl = numArchivosOFProcesadosFinalizadosManualCtrl;
    }
    /**
     * @return the numRegistrosFcargados
     */
    public Long getNumRegistrosFcargados() {
        return numRegistrosFcargados;
    }
    /**
     * @param numRegistrosFcargados the numRegistrosFcargados to set
     */
    public void setNumRegistrosFcargados(Long numRegistrosFcargados) {
        this.numRegistrosFcargados = numRegistrosFcargados;
    }
    /**
     * @return the numRegistrosFEnProceso
     */
    public Long getNumRegistrosFEnProceso() {
        return numRegistrosFEnProceso;
    }
    /**
     * @param numRegistrosFEnProceso the numRegistrosFEnProceso to set
     */
    public void setNumRegistrosFEnProceso(Long numRegistrosFEnProceso) {
        this.numRegistrosFEnProceso = numRegistrosFEnProceso;
    }
    /**
     * @return the numRegistrosFProcesados
     */
    public Long getNumRegistrosFProcesados() {
        return numRegistrosFProcesados;
    }
    /**
     * @param numRegistrosFProcesados the numRegistrosFProcesados to set
     */
    public void setNumRegistrosFProcesados(Long numRegistrosFProcesados) {
        this.numRegistrosFProcesados = numRegistrosFProcesados;
    }
    /**
     * @return the numRegistrosFInconsistencias
     */
    public Long getNumRegistrosFInconsistencias() {
        return numRegistrosFInconsistencias;
    }
    /**
     * @param numRegistrosFInconsistencias the numRegistrosFInconsistencias to set
     */
    public void setNumRegistrosFInconsistencias(Long numRegistrosFInconsistencias) {
        this.numRegistrosFInconsistencias = numRegistrosFInconsistencias;
    }
}
