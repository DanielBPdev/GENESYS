package com.asopagos.aportes.masivos.dto;

import com.asopagos.entidades.pila.masivos.MasivoArchivo;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArchivoAporteMasivoDTO implements Serializable {

    private Long idArchivo;

    private String nombreArchivo;

    private String estadoArchivo;

    private String numeroRadicacion;

    private Date fechaProcesamiento;

    private Date fechaActualizacion;

    private String usuario;

    private Long solicitud;

    private Long idCargue;

    private Boolean valSubsidio;

    private Long totalRegistros;

    private Long totalErrorRegistros;

    private Boolean puedeProcesar;

    private String nombreArchivoECM;

    public ArchivoAporteMasivoDTO(MasivoArchivo masivoArchivo,
            Long totalRegistros,
            Long totalErroresRegistros) {
        this.idArchivo = masivoArchivo.getId();
        this.nombreArchivo = masivoArchivo.getNombreOriginalArchivo();
        this.nombreArchivoECM = masivoArchivo.getNombreArchivo();
        this.estadoArchivo = masivoArchivo.getEstado();
        this.numeroRadicacion = masivoArchivo.getNumeroRadicacion();
        this.fechaProcesamiento = masivoArchivo.getFechaProcesamiento();
        this.fechaProcesamiento = masivoArchivo.getFechaActualizacion();
        this.usuario = masivoArchivo.getUsuario();
        this.solicitud = masivoArchivo.getSolicitud();
        this.idCargue = masivoArchivo.getIdCargue();
        this.valSubsidio = masivoArchivo.getValSubsidio();
        this.totalRegistros = totalRegistros;
        this.totalErrorRegistros = totalErroresRegistros;
        this.puedeProcesar = Boolean.TRUE;
    }

    public Long getIdArchivo() {
        return this.idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getEstadoArchivo() {
        return this.estadoArchivo;
    }

    public void setEstadoArchivo(String estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }

    public String getNumeroRadicacion() {
        return this.numeroRadicacion;
    }

    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    public Date getFechaProcesamiento() {
        return this.fechaProcesamiento;
    }

    public void setFechaProcesamiento(Date fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public Date getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getSolicitud() {
        return this.solicitud;
    }

    public void setSolicitud(Long solicitud) {
        this.solicitud = solicitud;
    }

    public Long getIdCargue() {
        return this.idCargue;
    }

    public void setIdCargue(Long idCargue) {
        this.idCargue = idCargue;
    }

    public Boolean isValSubsidio() {
        return this.valSubsidio;
    }

    public Boolean getValSubsidio() {
        return this.valSubsidio;
    }

    public void setValSubsidio(Boolean valSubsidio) {
        this.valSubsidio = valSubsidio;
    }

    public Long getTotalRegistros() {
        return this.totalRegistros;
    }

    public void setTotalRegistros(Long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Long getTotalErrorRegistros() {
        return this.totalErrorRegistros;
    }

    public void setTotalErrorRegistros(Long totalErrorRegistros) {
        this.totalErrorRegistros = totalErrorRegistros;
    }

    public Boolean isPuedeProcesar() {
        return this.puedeProcesar;
    }

    public Boolean getPuedeProcesar() {
        return this.puedeProcesar;
    }

    public void setPuedeProcesar(Boolean puedeProcesar) {
        this.puedeProcesar = puedeProcesar;
    }

    public String getNombreArchivoECM() {
        return this.nombreArchivoECM;
    }

    public void setNombreArchivoECM(String nombreArchivoECM) {
        this.nombreArchivoECM = nombreArchivoECM;
    }


}
