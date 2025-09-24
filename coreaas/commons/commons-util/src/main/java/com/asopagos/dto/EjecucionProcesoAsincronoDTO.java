package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.general.EjecucionProcesoAsincrono;
import com.asopagos.enumeraciones.fovis.TipoProcesoAsincronoEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion de la ejecucuion de proceso asincrono Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 * 321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class EjecucionProcesoAsincronoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 479655495479204055L;

    /**
     * Identificador ejecucion
     */
    private Long id;

    /**
     * Fecha y hora en la que se inicio la ejecucion
     */
    private Date fechaInicio;

    /**
     * Fecha y hora en la que se finalizo la ejecucion
     */
    private Date fechaFin;

    /**
     * Indica si el proceso ya fue revisado por el usuario
     */
    private Boolean procesoRevisado;

    /**
     * Tipo proceso asincrono ejecutado
     */
    private TipoProcesoAsincronoEnum tipoProceso;

    /**
     * Identificador para el proceso ejecutado, depende del tipo de proceso
     */
    private Long idProceso;

    /**
     * Indica si el proceso se cancelo por el usuario
     */
    private Boolean procesoCancelado;

    /**
     * Indica la cantidad total de elementos a revisar en el proceso
     */
    private Short cantidadTotalProceso;

    /**
     * Indica la cantidad de elementos que ha avanzado el proceso
     */
    private Short cantidadAvanceProceso;

    /**
     * Convierte la entidad en DTO
     * @param cargueArchivoCruceFovis
     *        Entidad
     * @return DTO
     */
    public static EjecucionProcesoAsincronoDTO convertEntityToDTO(EjecucionProcesoAsincrono ejecucionProcesoAsincrono) {
        EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO = new EjecucionProcesoAsincronoDTO();
        ejecucionProcesoAsincronoDTO.setId(ejecucionProcesoAsincrono.getId());
        ejecucionProcesoAsincronoDTO.setFechaInicio(ejecucionProcesoAsincrono.getFechaInicio());
        ejecucionProcesoAsincronoDTO.setFechaFin(ejecucionProcesoAsincrono.getFechaFin());
        ejecucionProcesoAsincronoDTO.setProcesoRevisado(ejecucionProcesoAsincrono.getProcesoRevisado());
        ejecucionProcesoAsincronoDTO.setTipoProceso(ejecucionProcesoAsincrono.getTipoProceso());
        ejecucionProcesoAsincronoDTO.setIdProceso(ejecucionProcesoAsincrono.getIdProceso());
        ejecucionProcesoAsincronoDTO.setProcesoCancelado(ejecucionProcesoAsincrono.getProcesoCancelado());
        ejecucionProcesoAsincronoDTO.setCantidadAvanceProceso(ejecucionProcesoAsincrono.getCantidadAvanceProceso());
        ejecucionProcesoAsincronoDTO.setCantidadTotalProceso(ejecucionProcesoAsincrono.getCantidadTotalProceso());
        return ejecucionProcesoAsincronoDTO;
    }

    /**
     * Convierte el DTO a entity
     * @return Entidad
     */
    public EjecucionProcesoAsincrono convertToEntity() {
        EjecucionProcesoAsincrono ejecucionProcesoAsincrono = new EjecucionProcesoAsincrono();
        ejecucionProcesoAsincrono.setId(this.getId());
        ejecucionProcesoAsincrono.setFechaInicio(this.getFechaInicio());
        ejecucionProcesoAsincrono.setFechaFin(this.getFechaFin());
        ejecucionProcesoAsincrono.setProcesoRevisado(this.getProcesoRevisado());
        ejecucionProcesoAsincrono.setTipoProceso(this.getTipoProceso());
        ejecucionProcesoAsincrono.setIdProceso(this.getIdProceso());
        ejecucionProcesoAsincrono.setProcesoCancelado(this.getProcesoCancelado());
        ejecucionProcesoAsincrono.setCantidadAvanceProceso(this.getCantidadAvanceProceso());
        ejecucionProcesoAsincrono.setCantidadTotalProceso(this.getCantidadTotalProceso());
        return ejecucionProcesoAsincrono;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the procesoRevisado
     */
    public Boolean getProcesoRevisado() {
        return procesoRevisado;
    }

    /**
     * @param procesoRevisado
     *        the procesoRevisado to set
     */
    public void setProcesoRevisado(Boolean procesoRevisado) {
        this.procesoRevisado = procesoRevisado;
    }

    /**
     * @return the tipoProceso
     */
    public TipoProcesoAsincronoEnum getTipoProceso() {
        return tipoProceso;
    }

    /**
     * @param tipoProceso
     *        the tipoProceso to set
     */
    public void setTipoProceso(TipoProcesoAsincronoEnum tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    /**
     * @return the idProceso
     */
    public Long getIdProceso() {
        return idProceso;
    }

    /**
     * @param idProceso the idProceso to set
     */
    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    /**
     * @return the procesoCancelado
     */
    public Boolean getProcesoCancelado() {
        return procesoCancelado;
    }

    /**
     * @param procesoCancelado the procesoCancelado to set
     */
    public void setProcesoCancelado(Boolean procesoCancelado) {
        this.procesoCancelado = procesoCancelado;
    }

    /**
     * @return the cantidadTotalProceso
     */
    public Short getCantidadTotalProceso() {
        return cantidadTotalProceso;
    }

    /**
     * @param cantidadTotalProceso the cantidadTotalProceso to set
     */
    public void setCantidadTotalProceso(Short cantidadTotalProceso) {
        this.cantidadTotalProceso = cantidadTotalProceso;
    }

    /**
     * @return the cantidadAvanceProceso
     */
    public Short getCantidadAvanceProceso() {
        return cantidadAvanceProceso;
    }

    /**
     * @param cantidadAvanceProceso the cantidadAvanceProceso to set
     */
    public void setCantidadAvanceProceso(Short cantidadAvanceProceso) {
        this.cantidadAvanceProceso = cantidadAvanceProceso;
    }

}
