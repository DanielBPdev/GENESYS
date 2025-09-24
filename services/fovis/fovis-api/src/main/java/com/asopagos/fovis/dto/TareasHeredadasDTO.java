package com.asopagos.fovis.dto;

import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta de un postulante a FOVIS.
 * <b>HU-032</b>
 * @author <a href="mailto:criparra@heinsohn.com.co">Cristian David Parra Zuluaga</a>
 */
@XmlRootElement
public class TareasHeredadasDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = -7671976893350969838L;

    /**
     * Solicitud.
     */
    private Long idSolicitud;

    /**
     * Numero Radicado.
     */
    private String numeroRadicado;

    /**
     * Numero Radicado.
     */
    private String numeroRadicadoCruce;

    /**
     * Tipo Identificacion.
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificacion
     */
    private String numeroIdentificacion;

    /**
     * Nombres apellidos
     */
    private String nombres;

    /**
     * Fecha radicacion
     */
    private Date fechaRadicacion;

    /**
     * Tipo Transaccion
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * Estado cruce
     */
    private String estadoCruce;

    /**
     * Ciclo asignacion
     */
    private String cicloAsignacion;

    /**
     * Tipo Transaccion tarea
     */
    private TipoTransaccionEnum tipoTransaccionPostulacion;

    public TareasHeredadasDTO(Long idSolicitud, String numeroRadicado, String numeroRadicadoCruce, String tipoIdentificacion, String numeroIdentificacion, String nombres, Date fechaRadicacion, String tipoTransaccion, String estadoCruce, String cicloAsignacion, String tipoTransaccionPostulacion) {
        super();
        this.idSolicitud = idSolicitud;
        this.numeroRadicado = numeroRadicado;
        this.numeroRadicadoCruce = numeroRadicadoCruce;
        this.tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.fechaRadicacion = fechaRadicacion;
        this.tipoTransaccion = TipoTransaccionEnum.valueOf(tipoTransaccion);
        this.estadoCruce = estadoCruce;
        this.cicloAsignacion = cicloAsignacion;
        this.tipoTransaccionPostulacion = TipoTransaccionEnum.valueOf(tipoTransaccionPostulacion);
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public String getNumeroRadicadoCruce() {
        return numeroRadicadoCruce;
    }

    public void setNumeroRadicadoCruce(String numeroRadicadoCruce) {
        this.numeroRadicadoCruce = numeroRadicadoCruce;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getEstadoCruce() {
        return estadoCruce;
    }

    public void setEstadoCruce(String estadoCruce) {
        this.estadoCruce = estadoCruce;
    }

    public String getCicloAsignacion() {
        return cicloAsignacion;
    }

    public void setCicloAsignacion(String cicloAsignacion) {
        this.cicloAsignacion = cicloAsignacion;
    }

    public TipoTransaccionEnum getTipoTransaccionPostulacion() {
        return tipoTransaccionPostulacion;
    }

    public void setTipoTransaccionPostulacion(TipoTransaccionEnum tipoTransaccionPostulacion) {
        this.tipoTransaccionPostulacion = tipoTransaccionPostulacion;
    }
}
