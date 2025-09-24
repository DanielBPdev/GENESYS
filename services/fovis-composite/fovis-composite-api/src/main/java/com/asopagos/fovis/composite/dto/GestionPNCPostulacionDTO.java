package com.asopagos.fovis.composite.dto;

import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos necesarios para gestionar la información de Producto no conforme en la postulación.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-030-3.2.2-043 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class GestionPNCPostulacionDTO {

    /**
     * Identificador global de la solicitud de postulación.
     */
    private Long idSolicitud;
    /**
     * Identificador de la tarea.
     */
    private Long idTarea;
    /**
     * Tipo transaccion en proceso (se debe enviar en la postulación web).
     */
    private TipoTransaccionEnum tipoTransaccionEnum;
    /**
     * Identificador de la instancia de proceso BPM (se debe enviar en la postulación web).
     */
    private Long idInstanciaProceso;

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *        the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea
     *        the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * @return the tipoTransaccionEnum
     */
    public TipoTransaccionEnum getTipoTransaccionEnum() {
        return tipoTransaccionEnum;
    }

    /**
     * @param tipoTransaccionEnum
     *        the tipoTransaccionEnum to set
     */
    public void setTipoTransaccionEnum(TipoTransaccionEnum tipoTransaccionEnum) {
        this.tipoTransaccionEnum = tipoTransaccionEnum;
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso
     *        the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

}
