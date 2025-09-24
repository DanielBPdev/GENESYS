package com.asopagos.legalizacionfovis.dto;

import java.math.BigInteger;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 *
 * <b>Descripcion:</b> Clase que contiene los datos necesarios para mostrar en la lista de solicitudes para legalización y desembolso<br/>
 * <b>Módulo:Fovis</b> Asopagos - HU 3.2.4-052<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class SolicitudPostulacionLegalizacionDTO {

    /**
     * Numero de radicación de la solicitud de postulacion.
     */
    private String numeroRadicacionSolicitud;
    /**
     * Tipo de indentificación del jefe del hogar.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Numero de indentificación del jefe del hogar.
     */
    private String numeroIdentificacion;
    /**
     * Nombre completo del jefe del hogar.
     */
    private String nombreCompleto;
    /**
     * Estado del hogar.
     */
    private EstadoHogarEnum estadoHogar;
    /**
     * Fecha fin de vigencia de los subsidios asignados
     */
    private Long fechaFinVigencia;
    /**
     * Fecha fin del plazo para la legalización de los subsidios asignados
     */
    private Long fechaFinPlazoLegalizacion;
    /**
     * Propiedad para almacenar si la legalización y desembolso es viable o no
     */
    private Boolean legalizacionYDesembolsoViable;
    /**
     * Identificador global de la Solicitud de Postulación
     */
    private BigInteger idSolicitudGlobalPostulacion;
    /**
     * Identificador de la Solicitud de Postulación
     */
    private BigInteger idSolicitudPostulacion;
    /**
     * Identificador de la Postulación Fovis
     */
    private BigInteger idPostulacion;
    /**
     * Identificador de la Solicitud de Legalización y Desembolso en curso
     */
    private BigInteger idSolicitudLegalizacionDesembolsoEnCurso;
    /**
     * Identificador global de la Solicitud de Legalización y Desembolso
     */
    private BigInteger idSolicitudGlobalLegalizacionDesembolso;

    /**
     * Fecha inicio vigencia
     */
    private Long fechaInicioVigencia;

    /**
     * Fecha Fin Primera vigencia
     */
    private Long fechaFinPrimeraVigencia;

    /**
     * Fecha Fin Segunda vigencia
     */
    private Long fechaFinSegundaVigencia;


    public SolicitudPostulacionLegalizacionDTO() {
    }

    /**
     * Constructor que recibe los datos a mostrar.
     * @param numeroRadicacionSolicitud
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param nombreCompleto
     * @param estadoHogar
     * @param fechaFinVigencia
     * @param idSolicitudGlobalPostulacion
     * @param idSolicitudPostulacion
     * @param idPostulacion
     * @param idSolicitudLegalizacionDesembolsoEnCurso
     */
    public SolicitudPostulacionLegalizacionDTO(String numeroRadicacionSolicitud, TipoIdentificacionEnum tipoIdentificacion,
                                               String numeroIdentificacion, String nombreCompleto, EstadoHogarEnum estadoHogar, Long fechaFinVigencia,
                                               BigInteger idSolicitudGlobalPostulacion, BigInteger idSolicitudPostulacion, BigInteger idPostulacion,
                                               BigInteger idSolicitudLegalizacionDesembolsoEnCurso, BigInteger idSolicitudGlobalLegalizacionDesembolso, Long fechaInicioVigencia ) {
        this.setNumeroRadicacionSolicitud(numeroRadicacionSolicitud);
        this.setTipoIdentificacion(tipoIdentificacion);
        this.setNumeroIdentificacion(numeroIdentificacion);
        this.setNombreCompleto(nombreCompleto);
        this.setEstadoHogar(estadoHogar);
        this.setFechaFinVigencia(fechaFinVigencia);
        this.setIdSolicitudGlobalPostulacion(idSolicitudGlobalPostulacion);
        this.setIdSolicitudPostulacion(idSolicitudPostulacion);
        this.setIdPostulacion(idPostulacion);
        this.setIdSolicitudLegalizacionDesembolsoEnCurso(idSolicitudLegalizacionDesembolsoEnCurso);
        this.setIdSolicitudGlobalLegalizacionDesembolso(idSolicitudGlobalLegalizacionDesembolso);
        this.setFechaInicioVigencia(fechaInicioVigencia);
    }

    /**
     * @return the numeroRadicacionSolicitud
     */
    public String getNumeroRadicacionSolicitud() {
        return numeroRadicacionSolicitud;
    }

    /**
     * @param numeroRadicacionSolicitud
     *        the numeroRadicacionSolicitud to set
     */
    public void setNumeroRadicacionSolicitud(String numeroRadicacionSolicitud) {
        this.numeroRadicacionSolicitud = numeroRadicacionSolicitud;
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
     * @return the fechaFinVigencia
     */
    public Long getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    /**
     * @param fechaFinVigencia
     *        the fechaFinVigencia to set
     */
    public void setFechaFinVigencia(Long fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    /**
     * @return the fechaFinPlazoLegalizacion
     */
    public Long getFechaFinPlazoLegalizacion() {
        return fechaFinPlazoLegalizacion;
    }

    /**
     * @param fechaFinPlazoLegalizacion
     *        the fechaFinPlazoLegalizacion to set
     */
    public void setFechaFinPlazoLegalizacion(Long fechaFinPlazoLegalizacion) {
        this.fechaFinPlazoLegalizacion = fechaFinPlazoLegalizacion;
    }

    /**
     * @return the legalizacionYDesembolsoViable
     */
    public Boolean getLegalizacionYDesembolsoViable() {
        return legalizacionYDesembolsoViable;
    }

    /**
     * @param legalizacionYDesembolsoViable
     *        the legalizacionYDesembolsoViable to set
     */
    public void setLegalizacionYDesembolsoViable(Boolean legalizacionYDesembolsoViable) {
        this.legalizacionYDesembolsoViable = legalizacionYDesembolsoViable;
    }

    /**
     * @return the idSolicitudGlobalPostulacion
     */
    public BigInteger getIdSolicitudGlobalPostulacion() {
        return idSolicitudGlobalPostulacion;
    }

    /**
     * @param idSolicitudGlobalPostulacion
     *        the idSolicitudGlobalPostulacion to set
     */
    public void setIdSolicitudGlobalPostulacion(BigInteger idSolicitudGlobalPostulacion) {
        this.idSolicitudGlobalPostulacion = idSolicitudGlobalPostulacion;
    }

    /**
     * @return the idSolicitudPostulacion
     */
    public BigInteger getIdSolicitudPostulacion() {
        return idSolicitudPostulacion;
    }

    /**
     * @param idSolicitudPostulacion
     *        the idSolicitudPostulacion to set
     */
    public void setIdSolicitudPostulacion(BigInteger idSolicitudPostulacion) {
        this.idSolicitudPostulacion = idSolicitudPostulacion;
    }

    /**
     * @return the idPostulacion
     */
    public BigInteger getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * @param idPostulacion
     *        the idPostulacion to set
     */
    public void setIdPostulacion(BigInteger idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    /**
     * @return the idSolicitudLegalizacionDesembolsoEnCurso
     */
    public BigInteger getIdSolicitudLegalizacionDesembolsoEnCurso() {
        return idSolicitudLegalizacionDesembolsoEnCurso;
    }

    /**
     * @param idSolicitudLegalizacionDesembolsoEnCurso
     *        the idSolicitudLegalizacionDesembolsoEnCurso to set
     */
    public void setIdSolicitudLegalizacionDesembolsoEnCurso(BigInteger idSolicitudLegalizacionDesembolsoEnCurso) {
        this.idSolicitudLegalizacionDesembolsoEnCurso = idSolicitudLegalizacionDesembolsoEnCurso;
    }

    /**
     * @return the idSolicitudGlobalLegalizacionDesembolso
     */
    public BigInteger getIdSolicitudGlobalLegalizacionDesembolso() {
        return idSolicitudGlobalLegalizacionDesembolso;
    }

    /**
     * @param idSolicitudGlobalLegalizacionDesembolso
     *        the idSolicitudGlobalLegalizacionDesembolso to set
     */
    public void setIdSolicitudGlobalLegalizacionDesembolso(BigInteger idSolicitudGlobalLegalizacionDesembolso) {
        this.idSolicitudGlobalLegalizacionDesembolso = idSolicitudGlobalLegalizacionDesembolso;
    }

    public Long getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Long fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Long getFechaFinPrimeraVigencia() {
        return fechaFinPrimeraVigencia;
    }

    public void setFechaFinPrimeraVigencia(Long fechaFinPrimeraVigencia) {
        this.fechaFinPrimeraVigencia = fechaFinPrimeraVigencia;
    }

    public Long getFechaFinSegundaVigencia() {
        return fechaFinSegundaVigencia;
    }

    public void setFechaFinSegundaVigencia(Long fechaFinSegundaVigencia) {
        this.fechaFinSegundaVigencia = fechaFinSegundaVigencia;
    }
}
