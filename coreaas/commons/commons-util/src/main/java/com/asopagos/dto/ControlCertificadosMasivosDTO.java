package com.asopagos.dto;

import java.util.Date;
import com.asopagos.enumeraciones.core.TipoCertificadoMasivoEnum;

public class ControlCertificadosMasivosDTO {
    
    private Long idControlCertificado;

    private String idArchivoECM;

    private String nombreArchivo;

    private Date fechaGeneracion;

    private Long idEmpleador;

    private TipoCertificadoMasivoEnum tipoCertificado;

    private String estado;

    private String porcentaje;

    private String nombreCargue;

    public ControlCertificadosMasivosDTO() {
    }

    public ControlCertificadosMasivosDTO(Long idControlCertificado, String idArchivoECM, String nombreArchivo, Date fechaGeneracion, Long idEmpleador, TipoCertificadoMasivoEnum tipoCertificado) {
        this.idControlCertificado = idControlCertificado;
        this.idArchivoECM = idArchivoECM;
        this.nombreArchivo = nombreArchivo;
        this.fechaGeneracion = fechaGeneracion;
        this.idEmpleador = idEmpleador;
        this.tipoCertificado = tipoCertificado;
    }

    public ControlCertificadosMasivosDTO(Long idControlCertificado,String idArchivoECM,Date fechaGeneracion,String tipoCertificado,String estado,String porcentaje,String nombreCargue){
        this.idControlCertificado = idControlCertificado;
        this.idArchivoECM = idArchivoECM;
        this.fechaGeneracion = fechaGeneracion;
        this.tipoCertificado = TipoCertificadoMasivoEnum.valueOf(tipoCertificado);
        this.estado =  estado;
        this.porcentaje =  porcentaje;
        this.nombreCargue =  nombreCargue;
    }

    public Long getIdControlCertificado() {
        return this.idControlCertificado;
    }

    public void setIdControlCertificado(Long idControlCertificado) {
        this.idControlCertificado = idControlCertificado;
    }

    public String getIdArchivoECM() {
        return this.idArchivoECM;
    }

    public void setIdArchivoECM(String idArchivoECM) {
        this.idArchivoECM = idArchivoECM;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Date getFechaGeneracion() {
        return this.fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Long getIdEmpleador() {
        return this.idEmpleador;
    }

    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    public TipoCertificadoMasivoEnum getTipoCertificado() {
        return this.tipoCertificado;
    }

    public void setTipoCertificado(TipoCertificadoMasivoEnum tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPorcentaje() {
        return this.porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombreCargue() {
        return this.nombreCargue;
    }

    public void setNombreCargue(String nombreCargue) {
        this.nombreCargue = nombreCargue;
    }

    @Override
    public String toString() {
        return "{" +
            " idControlCertificado='" + getIdControlCertificado() + "'" +
            ", idArchivoECM='" + getIdArchivoECM() + "'" +
            ", nombreArchivo='" + getNombreArchivo() + "'" +
            ", fechaGeneracion='" + getFechaGeneracion() + "'" +
            ", idEmpleador='" + getIdEmpleador() + "'" +
            ", tipoCertificado='" + getTipoCertificado() + "'" +
            ", estado='" + getEstado() + "'" +
            ", porcentaje='" + getPorcentaje() + "'" +
            ", nombreCargue='" + getNombreCargue() + "'" +
            "}";
    }

}
