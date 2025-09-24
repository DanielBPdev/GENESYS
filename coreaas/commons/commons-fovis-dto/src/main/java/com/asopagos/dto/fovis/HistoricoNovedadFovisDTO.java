/**
 * 
 */
package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO que contiene los datos de una solicitud de novedad FOVIS.
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 *
 */
@XmlRootElement
public class HistoricoNovedadFovisDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Descripción del tipo de transacción dentro del proceso de la solicitud
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * Número de radicación de la solicitud
     */
    private String numeroRadicacion;

    /**
     * Fecha de radicación de la solicitud
     */
    private Date fechaRadicacion;

    /**
     * Estado de la solicitud de postulación.
     */
    private EstadoSolicitudNovedadFovisEnum estadoSolicitud;

    /**
     * Descripción de la clasificación del tipo solicitante
     */
    private ClasificacionEnum clasificacion;

    /**
     * Tipo identificacion persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Numero identificacion persona
     */
    private String numeroIdentificacion;

    /**
     * Nombre completo de la persona con la novedad
     */
    private String nombreCompleto;

    /**
     * Código identificador de llave primaria de la solicitud
     */
    private Long idSolicitud;

    /**
     * Resultado del procesamiento de la solicitud
     */
    private ResultadoProcesoEnum resultadoProceso;

    /**
     * Constructor con los datos de la solicitud desde la entidad
     * 
     * @param solicitudAnalisisNovedadFovis
     *        Entidad solicitud analisis novedad
     */
    public HistoricoNovedadFovisDTO() {
        super();
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion
     *        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the fechaRadicacion
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion
     *        the fechaRadicacion to set
     */
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudNovedadFovisEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudNovedadFovisEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion
     *        the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

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
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto
     *        the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

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
     * @return the resultadoProceso
     */
    public ResultadoProcesoEnum getResultadoProceso() {
        return resultadoProceso;
    }

    /**
     * @param resultadoProceso
     *        the resultadoProceso to set
     */
    public void setResultadoProceso(ResultadoProcesoEnum resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }

}
