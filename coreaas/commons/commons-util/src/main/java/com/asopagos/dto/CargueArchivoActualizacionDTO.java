package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.novedades.CargueArchivoActualizacion;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del Cargue Archivo de actualizacion<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class CargueArchivoActualizacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador cargue
     */
    private Long idCargueArchivoActualizacion;

    /**
     * Nombre archivo
     */
    private String nombreArchivo;

    /**
     * Fecha de procesamiento del archivo
     */
    private Date fechaProcesamiento;

    /**
     * Identificador ECM del archivo
     */
    private String codigoIdentificacionECM;

    /**
     * Estado cargue actualizacion
     */
    private EstadoCargueArchivoActualizacionEnum estado;

    /**
     * Fecha de aceptacion del archivo
     */
    private Date fechaAceptacion;

    /**
     * Constructor
     */
    public CargueArchivoActualizacionDTO() {
        super();
    }

    /**
     * Convierte la entidad en DTO
     * @param cargueArchivoActualizacion
     *        Entidad
     * @return DTO
     */
    public static CargueArchivoActualizacionDTO convertCargueArchivoActualizacionToDTO(
            CargueArchivoActualizacion cargueArchivoActualizacion) {
        CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO = new CargueArchivoActualizacionDTO();
        cargueArchivoActualizacionDTO.setIdCargueArchivoActualizacion(cargueArchivoActualizacion.getIdCargueArchivoActualizacion());
        cargueArchivoActualizacionDTO.setNombreArchivo(cargueArchivoActualizacion.getNombreArchivo());
        cargueArchivoActualizacionDTO.setFechaProcesamiento(cargueArchivoActualizacion.getFechaProcesamiento());
        cargueArchivoActualizacionDTO.setCodigoIdentificacionECM(cargueArchivoActualizacion.getCodigoIdentificacionECM());
        cargueArchivoActualizacionDTO.setEstado(cargueArchivoActualizacion.getEstado());
        cargueArchivoActualizacionDTO.setFechaAceptacion(cargueArchivoActualizacion.getFechaAceptacion());
        return cargueArchivoActualizacionDTO;
    }

    /**
     * Convierte el DTO a entity
     * @return Entidad
     */
    public CargueArchivoActualizacion convertToEntity() {
        CargueArchivoActualizacion cargueArchivoActualizacion = new CargueArchivoActualizacion();
        cargueArchivoActualizacion.setCodigoIdentificacionECM(this.getCodigoIdentificacionECM());
        cargueArchivoActualizacion.setFechaProcesamiento(this.getFechaProcesamiento());
        cargueArchivoActualizacion.setIdCargueArchivoActualizacion(this.getIdCargueArchivoActualizacion());
        cargueArchivoActualizacion.setNombreArchivo(this.getNombreArchivo());
        cargueArchivoActualizacion.setEstado(this.getEstado());
        cargueArchivoActualizacion.setFechaAceptacion(this.getFechaAceptacion());
        return cargueArchivoActualizacion;
    }

    /**
     * @return the idCargueArchivoActualizacion
     */
    public Long getIdCargueArchivoActualizacion() {
        return idCargueArchivoActualizacion;
    }

    /**
     * @param idCargueArchivoActualizacion
     *        the idCargueArchivoActualizacion to set
     */
    public void setIdCargueArchivoActualizacion(Long idCargueArchivoActualizacion) {
        this.idCargueArchivoActualizacion = idCargueArchivoActualizacion;
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
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the fechaProcesamiento
     */
    public Date getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    /**
     * @param fechaProcesamiento
     *        the fechaProcesamiento to set
     */
    public void setFechaProcesamiento(Date fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
     * @return the codigoIdentificacionECM
     */
    public String getCodigoIdentificacionECM() {
        return codigoIdentificacionECM;
    }

    /**
     * @param codigoIdentificacionECM
     *        the codigoIdentificacionECM to set
     */
    public void setCodigoIdentificacionECM(String codigoIdentificacionECM) {
        this.codigoIdentificacionECM = codigoIdentificacionECM;
    }

    /**
     * @return the estado
     */
    public EstadoCargueArchivoActualizacionEnum getEstado() {
        return estado;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(EstadoCargueArchivoActualizacionEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the fechaAceptacion
     */
    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    /**
     * @param fechaAceptacion
     *        the fechaAceptacion to set
     */
    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

}
