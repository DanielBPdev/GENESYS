package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.entidades.ccf.fovis.PostulacionAsignacion;
import com.asopagos.enumeraciones.fovis.PrioridadAsignacionEnum;
import com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum;

/**
 * DTO que representa los datos de la entidad PostulacionAsignacion
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 *
 */
public class PostulacionAsignacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único, llave primaria
     */
    private Long idPostulacionAsignacion;

    /**
     * Informacón de la PostulacionFOVIS
     */
    private Long idPostulacion;

    /**
     * Información del ciclo de asignacion de la postulacion
     */
    private Long idCicloAsignacion;

    /**
     * Información de la solicitud de asignación
     */
    private Long idSolicitudAsignacion;

    /**
     * Información de la calificacion de postulacion
     */
    private Long idCalificacionPostulacion;

    /**
     * Resultado de asignación del hogar
     */
    private ResultadoAsignacionEnum resultadoAsignacion;

    /**
     * Valor asignado de SFV
     */
    private BigDecimal valorAsignadoSFV;

    /**
     * Prioridad obtenida durante el análisis de asignación del hogar al
     * subsidio
     */
    private PrioridadAsignacionEnum prioridadAsignacion;

    /**
     * Identificador de la carta generada por la asignacion
     */
    private String idDocumentoActaAsignacion;
    
    /**
     * recurso prioridad de la asignacion
     */
    private String recursoPrioridad;

    /**
     * Constructor vacio
     */
    public PostulacionAsignacionDTO() {
        super();
    }

    /**
     * Constructor que recibe los datos de la entidad
     * @param postulacionAsignacion
     *        Informacion postulacion asignación
     */
    public PostulacionAsignacionDTO(PostulacionAsignacion postulacionAsignacion) {
        this.convertToDTO(postulacionAsignacion);
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param postulacionAsignacion
     */
    public void convertToDTO(PostulacionAsignacion postulacionAsignacion) {
        this.setIdPostulacionAsignacion(postulacionAsignacion.getIdPostulacionAsignacion());
        this.setIdPostulacion(postulacionAsignacion.getIdPostulacion());
        this.setIdCicloAsignacion(postulacionAsignacion.getIdCicloAsignacion());
        this.setIdSolicitudAsignacion(postulacionAsignacion.getIdSolicitudAsignacion());
        this.setIdCalificacionPostulacion(postulacionAsignacion.getIdCalificacionPostulacion());
        this.setPrioridadAsignacion(postulacionAsignacion.getPrioridadAsignacion());
        this.setResultadoAsignacion(postulacionAsignacion.getResultadoAsignacion());
        this.setValorAsignadoSFV(postulacionAsignacion.getValorAsignadoSFV());
        this.setIdDocumentoActaAsignacion(postulacionAsignacion.getIdDocumentoActaAsignacion());
        this.setRecursoPrioridad(postulacionAsignacion.getRecursoPrioridad());
    }

    /**
     * Asocia los datos de la postulacion a la relacion de asignacion y postulacion
     * @param postulacionFOVISModeloDTO
     *        Información postulacion
     */
    public void convertToDTO(PostulacionFOVISModeloDTO postulacionFOVISModeloDTO) {
        this.setIdPostulacion(postulacionFOVISModeloDTO.getIdPostulacion());
        this.setIdCicloAsignacion(postulacionFOVISModeloDTO.getIdCicloAsignacion());
        this.setIdSolicitudAsignacion(postulacionFOVISModeloDTO.getIdSolicitudAsignacion());
        this.setIdCalificacionPostulacion(postulacionFOVISModeloDTO.getIdCalificacionPostulacion());
        this.setPrioridadAsignacion(postulacionFOVISModeloDTO.getPrioridadAsignacion());
        this.setResultadoAsignacion(postulacionFOVISModeloDTO.getResultadoAsignacion());
        this.setValorAsignadoSFV(postulacionFOVISModeloDTO.getValorAsignadoSFV());
        this.setIdDocumentoActaAsignacion(postulacionFOVISModeloDTO.getIdDocumento());
        this.setRecursoPrioridad(postulacionFOVISModeloDTO.getRecursoPrioridad());
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return PostulacionAsignacion
     */
    public PostulacionAsignacion convertToEntity() {
        PostulacionAsignacion postulacionAsignacion = new PostulacionAsignacion();
        postulacionAsignacion.setIdPostulacionAsignacion(this.getIdPostulacionAsignacion());
        postulacionAsignacion.setIdPostulacion(this.getIdPostulacion());
        postulacionAsignacion.setIdCicloAsignacion(this.getIdCicloAsignacion());
        postulacionAsignacion.setIdSolicitudAsignacion(this.getIdSolicitudAsignacion());
        postulacionAsignacion.setIdCalificacionPostulacion(this.getIdCalificacionPostulacion());
        postulacionAsignacion.setPrioridadAsignacion(this.getPrioridadAsignacion());
        postulacionAsignacion.setResultadoAsignacion(this.getResultadoAsignacion());
        postulacionAsignacion.setValorAsignadoSFV(this.getValorAsignadoSFV());
        postulacionAsignacion.setIdDocumentoActaAsignacion(this.getIdDocumentoActaAsignacion());
        postulacionAsignacion.setRecursoPrioridad(this.getRecursoPrioridad());
        return postulacionAsignacion;
    }

    /**
     * @return the idPostulacionAsignacion
     */
    public Long getIdPostulacionAsignacion() {
        return idPostulacionAsignacion;
    }

    /**
     * @param idPostulacionAsignacion
     *        the idPostulacionAsignacion to set
     */
    public void setIdPostulacionAsignacion(Long idPostulacionAsignacion) {
        this.idPostulacionAsignacion = idPostulacionAsignacion;
    }

    /**
     * @return the idPostulacion
     */
    public Long getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * @param idPostulacion
     *        the idPostulacion to set
     */
    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    /**
     * @return the idCicloAsignacion
     */
    public Long getIdCicloAsignacion() {
        return idCicloAsignacion;
    }

    /**
     * @param idCicloAsignacion
     *        the idCicloAsignacion to set
     */
    public void setIdCicloAsignacion(Long idCicloAsignacion) {
        this.idCicloAsignacion = idCicloAsignacion;
    }

    /**
     * @return the idSolicitudAsignacion
     */
    public Long getIdSolicitudAsignacion() {
        return idSolicitudAsignacion;
    }

    /**
     * @param idSolicitudAsignacion
     *        the idSolicitudAsignacion to set
     */
    public void setIdSolicitudAsignacion(Long idSolicitudAsignacion) {
        this.idSolicitudAsignacion = idSolicitudAsignacion;
    }

    /**
     * @return the idCalificacionPostulacion
     */
    public Long getIdCalificacionPostulacion() {
        return idCalificacionPostulacion;
    }

    /**
     * @param idCalificacionPostulacion
     *        the idCalificacionPostulacion to set
     */
    public void setIdCalificacionPostulacion(Long idCalificacionPostulacion) {
        this.idCalificacionPostulacion = idCalificacionPostulacion;
    }

    /**
     * @return the resultadoAsignacion
     */
    public ResultadoAsignacionEnum getResultadoAsignacion() {
        return resultadoAsignacion;
    }

    /**
     * @param resultadoAsignacion
     *        the resultadoAsignacion to set
     */
    public void setResultadoAsignacion(ResultadoAsignacionEnum resultadoAsignacion) {
        this.resultadoAsignacion = resultadoAsignacion;
    }

    /**
     * @return the valorAsignadoSFV
     */
    public BigDecimal getValorAsignadoSFV() {
        return valorAsignadoSFV;
    }

    /**
     * @param valorAsignadoSFV
     *        the valorAsignadoSFV to set
     */
    public void setValorAsignadoSFV(BigDecimal valorAsignadoSFV) {
        this.valorAsignadoSFV = valorAsignadoSFV;
    }

    /**
     * @return the prioridadAsignacion
     */
    public PrioridadAsignacionEnum getPrioridadAsignacion() {
        return prioridadAsignacion;
    }

    /**
     * @param prioridadAsignacion
     *        the prioridadAsignacion to set
     */
    public void setPrioridadAsignacion(PrioridadAsignacionEnum prioridadAsignacion) {
        this.prioridadAsignacion = prioridadAsignacion;
    }

    /**
     * @return the idDocumentoActaAsignacion
     */
    public String getIdDocumentoActaAsignacion() {
        return idDocumentoActaAsignacion;
    }

    /**
     * @param idDocumentoActaAsignacion
     *        the idDocumentoActaAsignacion to set
     */
    public void setIdDocumentoActaAsignacion(String idDocumentoActaAsignacion) {
        this.idDocumentoActaAsignacion = idDocumentoActaAsignacion;
    }
    
    /**
     * @return the recursoPrioridad
     */
    public String getRecursoPrioridad() {
        return recursoPrioridad;
    }

    /**
     * @param recursoPrioridad
     *        the recursoPrioridad to set
     */
    public void setRecursoPrioridad(String recursoPrioridad) {
        this.recursoPrioridad = recursoPrioridad;
    }


}
