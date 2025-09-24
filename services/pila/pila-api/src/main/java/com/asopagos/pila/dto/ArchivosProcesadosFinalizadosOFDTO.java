package com.asopagos.pila.dto;

import java.io.Serializable;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de la consulta principal parametrizada de pantalla <br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */
public class ArchivosProcesadosFinalizadosOFDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Identificador del Indice de planilla */
    private Long idPlanilla;

    /** Fecha y hora de descarga */
    private Long fechaHoraDescarga;

    /** Número de planilla asociada */
    private Long numeroPlanilla;

    /** Tipo de archivo */
    private TipoArchivoPilaEnum tipoArchivo;

    /** Tipo de planilla (I, A...) */
    private String tipoPlanilla;

    /** Tamaño del archivo en KB */
    private Long tamanoArchivo;

    /** Extension del archivo cargado */
    private String extension;

    /** Estado del proceso en el bloque 0 */
    private EstadoProcesoArchivoEnum bq0estado;

    /** Nombre del archivo */
    private String nombreArchivo;

    /** Nombre del archivo en el bloque 1 */
    private EstadoProcesoArchivoEnum bq1nombreArchivo;

    /** Estructura del archivo en el bloque 2 */
    private EstadoProcesoArchivoEnum bq2Estructura;

    /** Tipo de archivo en bloque 2 */
    private TipoArchivoPilaEnum bq2TipoArchivo;

    /** Consistencia entre pareja de archivos en el bloque 3 */
    private EstadoProcesoArchivoEnum bq3ConsistenciaEntrePareja;

    /** Consistencia del archivo individual en el bloque 4 */
    private EstadoProcesoArchivoEnum bq4ConsistenciaIndividual;

    /** Revision del numero y tipo de documento del aportante en el bloque 5 */
    private EstadoProcesoArchivoEnum bq5TipoNumDocVsBD;

    /** Conciliacion del archivo OI vs OF en bloque 6 */
    private EstadoProcesoArchivoEnum bq6ConciliacionOIvsOF;

    /** Relacion del registro de aportes en bloque 8 */
    private EstadoProcesoArchivoEnum relacionRegistroAportes;

    /** Relacion del registro de novedades en el bloque 9 */
    private EstadoProcesoArchivoEnum relacionRegistroNovedades;

    /** Recaudo notificado bloque 10 */
    private EstadoProcesoArchivoEnum notificado;

    /** Estado actual del archivo en Indice Planilla */
    private EstadoProcesoArchivoEnum estadoActualArchivo;
    
    /** Tipo de operador */
    private TipoOperadorEnum tipoOperador;

    /**
     * @param idPlanilla
     * @param fechaHoraDescarga
     * @param numeroPlanilla
     * @param tipoArchivo
     * @param tipoPlanilla
     * @param tamanoArchivo
     * @param bq0estado
     * @param nombreArchivo
     * @param bq2Estructura
     * @param bq2TipoArchivo
     * @param bq3ConsistenciaEntrePareja
     * @param bq4ConsistenciaIndividual
     * @param bq5TipoNumDocVsBD
     * @param bq6ConciliacionOIvsOF
     * @param relacionRegistroAportes
     * @param relacionRegistroNovedades
     * @param notificado
     * @param estadoActualArchivo
     * @param bq1nombreArchivo
     */
    public ArchivosProcesadosFinalizadosOFDTO(Long idPlanilla, Date fechaHoraDescarga, Long numeroPlanilla, TipoArchivoPilaEnum tipoArchivo,
            String tipoPlanilla, Long tamanoArchivo, EstadoProcesoArchivoEnum bq0estado, String nombreArchivo,
            EstadoProcesoArchivoEnum bq2Estructura, TipoArchivoPilaEnum bq2TipoArchivo, EstadoProcesoArchivoEnum bq3ConsistenciaEntrePareja,
            EstadoProcesoArchivoEnum bq4ConsistenciaIndividual, EstadoProcesoArchivoEnum bq5TipoNumDocVsBD,
            EstadoProcesoArchivoEnum bq6ConciliacionOIvsOF, EstadoProcesoArchivoEnum relacionRegistroAportes,
            EstadoProcesoArchivoEnum relacionRegistroNovedades, EstadoProcesoArchivoEnum notificado,
            EstadoProcesoArchivoEnum estadoActualArchivo, EstadoProcesoArchivoEnum bq1nombreArchivo, String tipoOperador) {
        this.idPlanilla = idPlanilla;
        this.fechaHoraDescarga = fechaHoraDescarga.getTime();
        this.numeroPlanilla = numeroPlanilla;
        this.tipoArchivo = tipoArchivo;
        this.tipoPlanilla = tipoPlanilla;
        this.tamanoArchivo = tamanoArchivo;
        this.extension = nombreArchivo.substring(nombreArchivo.length() - 3);
        this.bq0estado = bq0estado;
        this.nombreArchivo = nombreArchivo;
        this.bq1nombreArchivo = bq1nombreArchivo;
        this.bq2Estructura = bq2Estructura;
        this.bq2TipoArchivo = bq2TipoArchivo;
        this.bq3ConsistenciaEntrePareja = bq3ConsistenciaEntrePareja;
        this.bq4ConsistenciaIndividual = bq4ConsistenciaIndividual;
        this.bq5TipoNumDocVsBD = bq5TipoNumDocVsBD;
        this.bq6ConciliacionOIvsOF = bq6ConciliacionOIvsOF;
        this.relacionRegistroAportes = relacionRegistroAportes;
        this.relacionRegistroNovedades = relacionRegistroNovedades;
        this.notificado = notificado;
        this.estadoActualArchivo = estadoActualArchivo;
        this.tipoOperador = TipoOperadorEnum.valueOf(tipoOperador);
    }

    public ArchivosProcesadosFinalizadosOFDTO(Long idPlanilla, Date fechaHoraDescarga, TipoArchivoPilaEnum tipoArchivo,
            Long tamanoArchivo, String nombreArchivo, EstadoProcesoArchivoEnum bq0estado, EstadoProcesoArchivoEnum bq1nombreArchivo,
            EstadoProcesoArchivoEnum bq6ConciliacionOIvsOF, EstadoProcesoArchivoEnum estadoActualArchivo, String tipoOperador) {
        this.idPlanilla = idPlanilla;
        this.fechaHoraDescarga = fechaHoraDescarga.getTime();
        this.tipoArchivo = tipoArchivo;
        this.tamanoArchivo = tamanoArchivo;
        this.nombreArchivo = nombreArchivo;
        this.extension = nombreArchivo.substring(nombreArchivo.length() - 3);
        this.bq0estado = bq0estado;
        this.bq1nombreArchivo = bq1nombreArchivo;
        this.bq6ConciliacionOIvsOF = bq6ConciliacionOIvsOF;
        this.estadoActualArchivo = estadoActualArchivo;
        this.tipoOperador = TipoOperadorEnum.valueOf(tipoOperador);
    }

    /**
     * @return the idPlanilla
     */
    public Long getIdPlanilla() {
        return idPlanilla;
    }

    /**
     * @param idPlanilla
     *        the idPlanilla to set
     */
    public void setIdPlanilla(Long idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    /**
     * @return the fechaHoraDescarga
     */
    public Long getFechaHoraDescarga() {
        return fechaHoraDescarga;
    }

    /**
     * @param fechaHoraDescarga
     *        the fechaHoraDescarga to set
     */
    public void setFechaHoraDescarga(Long fechaHoraDescarga) {
        this.fechaHoraDescarga = fechaHoraDescarga;
    }

    /**
     * @return the numeroPlanilla
     */
    public Long getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla
     *        the numeroPlanilla to set
     */
    public void setNumeroPlanilla(Long numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the tipoArchivo
     */
    public TipoArchivoPilaEnum getTipoArchivo() {
        return tipoArchivo;
    }

    /**
     * @param tipoArchivo
     *        the tipoArchivo to set
     */
    public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    /**
     * @return the tipoPlanilla
     */
    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * @param tipoPlanilla
     *        the tipoPlanilla to set
     */
    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * @return the tamanoArchivo
     */
    public Long getTamanoArchivo() {
        return tamanoArchivo;
    }

    /**
     * @param tamanoArchivo
     *        the tamanoArchivo to set
     */
    public void setTamanoArchivo(Long tamanoArchivo) {
        this.tamanoArchivo = tamanoArchivo;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension
     *        the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the bq0estado
     */
    public EstadoProcesoArchivoEnum getBq0estado() {
        return bq0estado;
    }

    /**
     * @param bq0estado
     *        the bq0estado to set
     */
    public void setBq0estado(EstadoProcesoArchivoEnum bq0estado) {
        this.bq0estado = bq0estado;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo
     *        the nombreArchivo to set
     */
    public void setNombreArchivo(String bq1nombreArchivo) {
        this.nombreArchivo = bq1nombreArchivo;
    }

    /**
     * @return the bq2Estructura
     */
    public EstadoProcesoArchivoEnum getBq2Estructura() {
        return bq2Estructura;
    }

    /**
     * @param bq2Estructura
     *        the bq2Estructura to set
     */
    public void setBq2Estructura(EstadoProcesoArchivoEnum bq2Estructura) {
        this.bq2Estructura = bq2Estructura;
    }

    /**
     * @return the bq2TipoArchivo
     */
    public TipoArchivoPilaEnum getBq2TipoArchivo() {
        return bq2TipoArchivo;
    }

    /**
     * @param bq2TipoArchivo
     *        the bq2TipoArchivo to set
     */
    public void setBq2TipoArchivo(TipoArchivoPilaEnum bq2TipoArchivo) {
        this.bq2TipoArchivo = bq2TipoArchivo;
    }

    /**
     * @return the bq3ConsistenciaEntrePareja
     */
    public EstadoProcesoArchivoEnum getBq3ConsistenciaEntrePareja() {
        return bq3ConsistenciaEntrePareja;
    }

    /**
     * @param bq3ConsistenciaEntrePareja
     *        the bq3ConsistenciaEntrePareja to set
     */
    public void setBq3ConsistenciaEntrePareja(EstadoProcesoArchivoEnum bq3ConsistenciaEntrePareja) {
        this.bq3ConsistenciaEntrePareja = bq3ConsistenciaEntrePareja;
    }

    /**
     * @return the bq4ConsistenciaIndividual
     */
    public EstadoProcesoArchivoEnum getBq4ConsistenciaIndividual() {
        return bq4ConsistenciaIndividual;
    }

    /**
     * @param bq4ConsistenciaIndividual
     *        the bq4ConsistenciaIndividual to set
     */
    public void setBq4ConsistenciaIndividual(EstadoProcesoArchivoEnum bq4ConsistenciaIndividual) {
        this.bq4ConsistenciaIndividual = bq4ConsistenciaIndividual;
    }

    /**
     * @return the bq5TipoNumDocVsBD
     */
    public EstadoProcesoArchivoEnum getBq5TipoNumDocVsBD() {
        return bq5TipoNumDocVsBD;
    }

    /**
     * @param bq5TipoNumDocVsBD
     *        the bq5TipoNumDocVsBD to set
     */
    public void setBq5TipoNumDocVsBD(EstadoProcesoArchivoEnum bq5TipoNumDocVsBD) {
        this.bq5TipoNumDocVsBD = bq5TipoNumDocVsBD;
    }

    /**
     * @return the bq6ConciliacionOIvsOF
     */
    public EstadoProcesoArchivoEnum getBq6ConciliacionOIvsOF() {
        return bq6ConciliacionOIvsOF;
    }

    /**
     * @param bq6ConciliacionOIvsOF
     *        the bq6ConciliacionOIvsOF to set
     */
    public void setBq6ConciliacionOIvsOF(EstadoProcesoArchivoEnum bq6ConciliacionOIvsOF) {
        this.bq6ConciliacionOIvsOF = bq6ConciliacionOIvsOF;
    }

    /**
     * @return the relacionRegistroAportes
     */
    public EstadoProcesoArchivoEnum getRelacionRegistroAportes() {
        return relacionRegistroAportes;
    }

    /**
     * @param relacionRegistroAportes
     *        the relacionRegistroAportes to set
     */
    public void setRelacionRegistroAportes(EstadoProcesoArchivoEnum relacionRegistroAportes) {
        this.relacionRegistroAportes = relacionRegistroAportes;
    }

    /**
     * @return the relacionRegistroNovedades
     */
    public EstadoProcesoArchivoEnum getRelacionRegistroNovedades() {
        return relacionRegistroNovedades;
    }

    /**
     * @param relacionRegistroNovedades
     *        the relacionRegistroNovedades to set
     */
    public void setRelacionRegistroNovedades(EstadoProcesoArchivoEnum relacionRegistroNovedades) {
        this.relacionRegistroNovedades = relacionRegistroNovedades;
    }

    /**
     * @return the notificado
     */
    public EstadoProcesoArchivoEnum getNotificado() {
        return notificado;
    }

    /**
     * @param notificado
     *        the notificado to set
     */
    public void setNotificado(EstadoProcesoArchivoEnum notificado) {
        this.notificado = notificado;
    }

    /**
     * @return the estadoActualArchivo
     */
    public EstadoProcesoArchivoEnum getEstadoActualArchivo() {
        return estadoActualArchivo;
    }

    /**
     * @param estadoActualArchivo
     *        the estadoActualArchivo to set
     */
    public void setEstadoActualArchivo(EstadoProcesoArchivoEnum estadoActualArchivo) {
        this.estadoActualArchivo = estadoActualArchivo;
    }

    public ArchivosProcesadosFinalizadosOFDTO() {
    }

    /**
     * @return the bq1nombreArchivo
     */
    public EstadoProcesoArchivoEnum getBq1nombreArchivo() {
        return bq1nombreArchivo;
    }

    /**
     * @param bq1nombreArchivo the bq1nombreArchivo to set
     */
    public void setBq1nombreArchivo(EstadoProcesoArchivoEnum bq1nombreArchivo) {
        this.bq1nombreArchivo = bq1nombreArchivo;
    }

    /**
     * @return the tipoOperador
     */
    public TipoOperadorEnum getTipoOperador() {
        return tipoOperador;
    }

    /**
     * @param tipoOperador the tipoOperador to set
     */
    public void setTipoOperador(TipoOperadorEnum tipoOperador) {
        this.tipoOperador = tipoOperador;
    }


    public void limpiarNulos() {
        if (this.idPlanilla == null) this.idPlanilla = 0L;
        if (this.fechaHoraDescarga == null) this.fechaHoraDescarga = new Date().getTime();
        if (this.numeroPlanilla == null) this.numeroPlanilla = 0L;
        if (this.tipoPlanilla == null) this.tipoPlanilla = ""	;
        if (this.tamanoArchivo == null) this.tamanoArchivo = 0L;
        if (this.extension == null) this.extension = "";
        if (this.nombreArchivo == null) this.nombreArchivo = "";
        
    }

    public String[] toListString(){
        this.limpiarNulos();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        String fechaHoraDescargaString  = dateFormat.format(new Date(fechaHoraDescarga));  

        String[] data = new String[] {
            fechaHoraDescargaString,
            Long.toString(this.numeroPlanilla),
            this.tipoArchivo == null ? "" : this.tipoArchivo.getCodigo(),
            this.tipoPlanilla,
            this.bq0estado + ", " + this.extension + ", " + Long.toString(this.tamanoArchivo) + " KB",
            this.nombreArchivo,
            this.bq2Estructura == null ? "" : this.bq2Estructura.getDescripcion(),
            this.bq3ConsistenciaEntrePareja == null ? "" : this.bq3ConsistenciaEntrePareja.getDescripcion(),
            this.bq4ConsistenciaIndividual == null ? "" : this.bq4ConsistenciaIndividual.getDescripcion(),
            this.bq5TipoNumDocVsBD == null ? "" : this.bq5TipoNumDocVsBD.getDescripcion(),
            this.bq6ConciliacionOIvsOF == null ? "" : this.bq6ConciliacionOIvsOF.getDescripcion(),
            this.relacionRegistroAportes == null ? "" : this.relacionRegistroAportes.getDescripcion(),
            this.relacionRegistroNovedades == null ? "" : this.relacionRegistroNovedades.getDescripcion(),
            this.notificado == null ? "" : this.notificado.getDescripcion(),
            this.estadoActualArchivo == null ? "" : this.estadoActualArchivo.getDescripcion()
        };
        return data;

    }
}
