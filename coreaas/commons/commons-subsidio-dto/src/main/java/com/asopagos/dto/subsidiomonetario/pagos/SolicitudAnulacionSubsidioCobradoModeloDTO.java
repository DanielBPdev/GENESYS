package com.asopagos.dto.subsidiomonetario.pagos;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobrado;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSolicitudAnulacionSubsidioCobradoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;

/**
 * <b>Descripcion:</b>Entidad que representa los datos de una solucitud de anulación de subsidio
 * cobrado </br>
 * <b>Módulo:</b> Asopagos - HU-31-222-227<br/>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez Cediel</a>
 */
@XmlRootElement
public class SolicitudAnulacionSubsidioCobradoModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8207064035392088304L;

    /**
     * Identificador para una solicitud de anulación de subsidio cobrado
     */
    @NotNull
    private Long idSolicitudAnulacionSubsidioCobrado;

    /**
     * fecha y hora de creación de la solicitud de anulación de subsidio cobrado
     */
    @NotNull
    private Date fechaHoraCreacionSolicitud;

    /**
     * Representa el estado de la solicitud de anulación de subsidio cobrado
     */
    @NotNull
    private EstadoSolicitudAnulacionSubsidioCobradoEnum estadoSolicitud;

    /**
     * Representa el motivo de anulacion de la solicitud
     */
    @NotNull
    private MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion;

    /**
     * Referencia a la solicitud global
     */
    @NotNull
    private Long idSolicitudGlobal;

    /**
     * Observaciones de aprobacion o rechazo para la solicitud de Anulacion de subsidio cobrado
     */
    private String observaciones;

    /**
     * Metodo encargado de convertir una entidad a DTO
     * @param Entidad
     *        a convertir
     */
    public void convertToDTO(SolicitudAnulacionSubsidioCobrado solicitudAnulacionSubsidioCobrado) {
        this.setIdSolicitudAnulacionCobradoSubsidio(solicitudAnulacionSubsidioCobrado.getIdSolicitudAnulacionSubsidioCobrado());
        this.setIdSolicitudGlobal(solicitudAnulacionSubsidioCobrado.getIdSolicitudGlobal());
        this.setEstadoSolicitud(solicitudAnulacionSubsidioCobrado.getEstadoSolicitud());
        if (solicitudAnulacionSubsidioCobrado.getFechaHoraCreacionSolicitud() != null) {
            this.setFechaHoraCreacionSolicitud(solicitudAnulacionSubsidioCobrado.getFechaHoraCreacionSolicitud());
        }
        this.setMotivoAnulacion(solicitudAnulacionSubsidioCobrado.getMotivoAnulacion());
        if (solicitudAnulacionSubsidioCobrado.getObservaciones() != null) {
            this.setObservaciones(solicitudAnulacionSubsidioCobrado.getObservaciones());
        }
    }

    /**
     * Metodo encargado de convertir de DTO a entidad
     * @return Entidad convertida
     */
    public SolicitudAnulacionSubsidioCobrado convertToEntity() {
        SolicitudAnulacionSubsidioCobrado solicitudAnulacionSubsidioCobrado = new SolicitudAnulacionSubsidioCobrado();
        solicitudAnulacionSubsidioCobrado.setIdSolicitudAnulacionCobradoSubsidio(this.getIdSolicitudAnulacionSubsidioCobrado());
        solicitudAnulacionSubsidioCobrado.setIdSolicitudGlobal(this.getIdSolicitudGlobal());
        solicitudAnulacionSubsidioCobrado.setEstadoSolicitud(this.getEstadoSolicitud());
        if (this.getFechaHoraCreacionSolicitud() != null) {
            solicitudAnulacionSubsidioCobrado.setFechaHoraCreacionSolicitud(this.getFechaHoraCreacionSolicitud());
        }
        solicitudAnulacionSubsidioCobrado.setMotivoAnulacion(this.getMotivoAnulacion());
        if (this.getObservaciones() != null) {
            solicitudAnulacionSubsidioCobrado.setObservaciones(this.getObservaciones());
        }
        return solicitudAnulacionSubsidioCobrado;
    }

    /**
     * @return the idSolicitudAnulacionSubsidioCobrado
     */
    public Long getIdSolicitudAnulacionSubsidioCobrado() {
        return idSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @param idSolicitudAnulacionSubsidioCobrado
     *        the idSolicitudAnulacionSubsidioCobrado to set
     */
    public void setIdSolicitudAnulacionCobradoSubsidio(Long idSolicitudAnulacionSubsidioCobrado) {
        this.idSolicitudAnulacionSubsidioCobrado = idSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @return the fechaHoraCreacionSolicitud
     */
    public Date getFechaHoraCreacionSolicitud() {
        return fechaHoraCreacionSolicitud;
    }

    /**
     * @param fechaHoraCreacionSolicitud
     *        the fechaHoraCreacionSolicitud to set
     */
    public void setFechaHoraCreacionSolicitud(Date fechaHoraCreacionSolicitud) {
        this.fechaHoraCreacionSolicitud = fechaHoraCreacionSolicitud;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudAnulacionSubsidioCobradoEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudAnulacionSubsidioCobradoEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the motivoAnulacion
     */
    public MotivoAnulacionSubsidioAsignadoEnum getMotivoAnulacion() {
        return motivoAnulacion;
    }

    /**
     * @param motivoAnulacion
     *        the motivoAnulacion to set
     */
    public void setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones
     *        the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}