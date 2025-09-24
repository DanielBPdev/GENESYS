package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos generales de un registro
 * histórico de postulación <br/>
 * <b>Historia de Usuario: HU-039 </b>
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class PostulacionDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 145454766563511212L;

    /**
     * Número de radicado de la postulación
     */
    private String numeroRadicacion;

    /**
     * Fecha de la radicación
     */
    private Long fechaRadicacion;

    /**
     * Estado del hogar en la postulación
     */
    private EstadoHogarEnum estadoHogar;

    /**
     * Estado de la postulación
     */
    private EstadoSolicitudPostulacionEnum estadoSolicitud;

    /**
     * Indica la clasificacion de la persona asociada a la postulacion
     */
    private ClasificacionEnum clasificacion;

    /**
     * Indica el estado de la persona en la postulacion
     */
    private EstadoFOVISHogarEnum estadoPersonaHogar;

    /**
     * Indentificado de la postulación
     */
    private Long idPostulacionFovis;

    /**
     * Constructor por defecto
     */
    public PostulacionDTO() {
        super();
    }

    /**
     * Constructor de clase con información
     * @param numeroRadicacion
     *        Número de radicado de la postulación
     * @param fechaRadicacion
     *        Fecha radicación postulación
     * @param estadoHogar
     *        Estado del hogar
     * @param estadoSolicitud
     *        Estado de la solicitud de postulación
     * @param clasificacion
     *        Clasificación de la persona en la postulación
     * @param estadoPersonaHogar
     *        Estado de la persona en la postulación
     */
    public PostulacionDTO(String numeroRadicacion, Date fechaRadicacion, EstadoHogarEnum estadoHogar,
            EstadoSolicitudPostulacionEnum estadoSolicitud, ClasificacionEnum clasificacion, EstadoFOVISHogarEnum estadoPersonaHogar, Long idPostulacion) {
        super();
        this.numeroRadicacion = numeroRadicacion;
        if (fechaRadicacion != null) {
            this.fechaRadicacion = fechaRadicacion.getTime();
        }
        this.estadoHogar = estadoHogar;
        this.estadoSolicitud = estadoSolicitud;
        this.clasificacion = clasificacion;
        this.estadoPersonaHogar = estadoPersonaHogar;
        this.idPostulacionFovis = idPostulacion;
    }

    /**
     * Constructor de clase con información
     * @param numeroRadicacion
     *        Número de radicado de la postulación
     * @param fechaRadicacion
     *        Fecha radicación postulación
     * @param estadoHogar
     *        Estado del hogar
     * @param estadoSolicitud
     *        Estado de la solicitud de postulación
     * @param estadoPersonaHogar
     *        Estado de la persona en la postulación
     */
    public PostulacionDTO(String numeroRadicacion, Date fechaRadicacion, EstadoHogarEnum estadoHogar,
            EstadoSolicitudPostulacionEnum estadoSolicitud, EstadoFOVISHogarEnum estadoPersonaHogar, Long idPostulacion) {
        super();
        this.numeroRadicacion = numeroRadicacion;
        if (fechaRadicacion != null) {
            this.fechaRadicacion = fechaRadicacion.getTime();
        }
        this.estadoHogar = estadoHogar;
        this.estadoSolicitud = estadoSolicitud;
        this.estadoPersonaHogar = estadoPersonaHogar;
        this.clasificacion = ClasificacionEnum.JEFE_HOGAR;
        this.idPostulacionFovis = idPostulacion;
    }

    /**
     * Obtiene el valor de numeroRadicacion
     * 
     * @return El valor de numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * Establece el valor de numeroRadicacion
     * 
     * @param numeroRadicacion
     *        El valor de numeroRadicacion por asignar
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * Obtiene el valor de fechaRadicacion
     * 
     * @return El valor de fechaRadicacion
     */
    public Long getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * Establece el valor de fechaRadicacion
     * 
     * @param fechaRadicacion
     *        El valor de fechaRadicacion por asignar
     */
    public void setFechaRadicacion(Long fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * Obtiene el valor de estadoHogar
     * 
     * @return El valor de estadoHogar
     */
    public EstadoHogarEnum getEstadoHogar() {
        return estadoHogar;
    }

    /**
     * Establece el valor de estadoHogar
     * 
     * @param estadoHogar
     *        El valor de estadoHogar por asignar
     */
    public void setEstadoHogar(EstadoHogarEnum estadoHogar) {
        this.estadoHogar = estadoHogar;
    }

    /**
     * Obtiene el valor de estadoSolicitud
     * 
     * @return El valor de estadoSolicitud
     */
    public EstadoSolicitudPostulacionEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * Establece el valor de estadoSolicitud
     * 
     * @param estadoSolicitud
     *        El valor de estadoSolicitud por asignar
     */
    public void setEstadoSolicitud(EstadoSolicitudPostulacionEnum estadoSolicitud) {
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
     * @return the estadoPersonaHogar
     */
    public EstadoFOVISHogarEnum getEstadoPersonaHogar() {
        return estadoPersonaHogar;
    }

    /**
     * @param estadoPersonaHogar
     *        the estadoPersonaHogar to set
     */
    public void setEstadoPersonaHogar(EstadoFOVISHogarEnum estadoPersonaHogar) {
        this.estadoPersonaHogar = estadoPersonaHogar;
    }

    /**
     * @return the idPostulacionFovis
     */
    public Long getIdPostulacionFovis() {
        return idPostulacionFovis;
    }

    /**
     * @param idPostulacionFovis the idPostulacionFovis to set
     */
    public void setIdPostulacionFovis(Long idPostulacionFovis) {
        this.idPostulacionFovis = idPostulacionFovis;
    }

}
