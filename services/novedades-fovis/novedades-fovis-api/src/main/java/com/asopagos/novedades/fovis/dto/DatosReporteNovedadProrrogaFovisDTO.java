/**
 * 
 */
package com.asopagos.novedades.fovis.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos del reporte para las solicitudes de novedad de Prórroga FOVIS.
 * @author Alexander Quintero Duarte <alquintero@heinsohn.com.co>
 */
@XmlRootElement
public class DatosReporteNovedadProrrogaFovisDTO {

    /**
     * Id de la solicitud de novedad
     */
    private Long idSolicitudNovedadFovis;
    /**
     * Id instancia del proceso.
     */
    private Long idInstancia;
    /**
     * Estado de la solicitud de novedad.
     */
    private EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad;
    /**
     * Número de radicación despues de radicar la solicitud
     */
    private String numeroRadicacion;
    /**
     * Fecha de radicación de la solicitud
     */
    private Long fechaRadicacion;
    /**
     * Resultado del proceso para la solicitud de novedad
     */
    private ResultadoProcesoEnum resultadoProceso;
    /**
     * Identificador postulacion
     */
    private Long idPostulacion;
    /*
     * Estado del hogar sujeto de la novedad
     */
    private EstadoHogarEnum estadoHogar;
    /**
     * Descripción del tipo de identificación del jefe de hogar
     */
    private TipoIdentificacionEnum tipoIdentificacionJefeHogar;
    /**
     * Número de identificación del jefe de hogar
     */
    private String numeroIdentificacionJefeHogar;
    /**
     * Contiene el nombre completo del jefe del hogar
     */
    private String nombreCompletoJefeHogar;

    /**
     * Constructor que recibe los datos del DTO.
     */
    public DatosReporteNovedadProrrogaFovisDTO(Long idSolicitudNovedadFovis, EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad,
            String numeroRadicacion, Date fechaRadicacion, ResultadoProcesoEnum resultadoProceso, Long idPostulacion,
            EstadoHogarEnum estadoHogar, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String nombreCompletoJefeHogar) {
        this.idSolicitudNovedadFovis = idSolicitudNovedadFovis;
        this.estadoSolicitudNovedad = estadoSolicitudNovedad;
        this.numeroRadicacion = numeroRadicacion;
        this.fechaRadicacion = fechaRadicacion != null ? fechaRadicacion.getTime() : null;
        this.resultadoProceso = resultadoProceso;
        this.idPostulacion = idPostulacion;
        this.estadoHogar = estadoHogar;
        this.tipoIdentificacionJefeHogar = tipoIdentificacion;
        this.numeroIdentificacionJefeHogar = numeroIdentificacion;
        this.nombreCompletoJefeHogar = nombreCompletoJefeHogar;
    }

    /**
     * @return the idSolicitudNovedadFovis
     */
    public Long getIdSolicitudNovedadFovis() {
        return idSolicitudNovedadFovis;
    }

    /**
     * @param idSolicitudNovedadFovis
     *        the idSolicitudNovedadFovis to set
     */
    public void setIdSolicitudNovedadFovis(Long idSolicitudNovedadFovis) {
        this.idSolicitudNovedadFovis = idSolicitudNovedadFovis;
    }

    /**
     * @return the idInstancia
     */
    public Long getIdInstancia() {
        return idInstancia;
    }

    /**
     * @param idInstancia
     *        the idInstancia to set
     */
    public void setIdInstancia(Long idInstancia) {
        this.idInstancia = idInstancia;
    }

    /**
     * @return the estadoSolicitudNovedad
     */
    public EstadoSolicitudNovedadFovisEnum getEstadoSolicitudNovedad() {
        return estadoSolicitudNovedad;
    }

    /**
     * @param estadoSolicitudNovedad
     *        the estadoSolicitudNovedad to set
     */
    public void setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad) {
        this.estadoSolicitudNovedad = estadoSolicitudNovedad;
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
    public Long getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion
     *        the fechaRadicacion to set
     */
    public void setFechaRadicacion(Long fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
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
     * @return the estadoHogar
     */
    public EstadoHogarEnum getEstadoHogar() {
        return estadoHogar;
    }

    /**
     * @param estadoHogar
     *        the estadoHogar to set
     */
    public void setEstadoHogar(EstadoHogarEnum estadoHogar) {
        this.estadoHogar = estadoHogar;
    }

    /**
     * @return the tipoIdentificacionJefeHogar
     */
    public TipoIdentificacionEnum getTipoIdentificacionJefeHogar() {
        return tipoIdentificacionJefeHogar;
    }

    /**
     * @param tipoIdentificacionJefeHogar
     *        the tipoIdentificacionJefeHogar to set
     */
    public void setTipoIdentificacionJefeHogar(TipoIdentificacionEnum tipoIdentificacionJefeHogar) {
        this.tipoIdentificacionJefeHogar = tipoIdentificacionJefeHogar;
    }

    /**
     * @return the numeroIdentificacionJefeHogar
     */
    public String getNumeroIdentificacionJefeHogar() {
        return numeroIdentificacionJefeHogar;
    }

    /**
     * @param numeroIdentificacionJefeHogar
     *        the numeroIdentificacionJefeHogar to set
     */
    public void setNumeroIdentificacionJefeHogar(String numeroIdentificacionJefeHogar) {
        this.numeroIdentificacionJefeHogar = numeroIdentificacionJefeHogar;
    }

    /**
     * @return the nombreCompletoJefeHogar
     */
    public String getNombreCompletoJefeHogar() {
        return nombreCompletoJefeHogar;
    }

    /**
     * @param nombreCompletoJefeHogar
     *        the nombreCompletoJefeHogar to set
     */
    public void setNombreCompletoJefeHogar(String nombreCompletoJefeHogar) {
        this.nombreCompletoJefeHogar = nombreCompletoJefeHogar;
    }

}
