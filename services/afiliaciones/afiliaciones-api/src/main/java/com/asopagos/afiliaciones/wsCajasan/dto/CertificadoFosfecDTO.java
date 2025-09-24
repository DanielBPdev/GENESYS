package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class CertificadoFosfecDTO {
    private TipoIdentificacionEnum tipoDto;
    private String numeroIdentificacion;
    private String nombreTrabajador;
    private Integer diasCotizados;
    private String fechaUltimoRetiro;
    private String ultimoPeriodoAporte;

    public CertificadoFosfecDTO() {
    }

    public CertificadoFosfecDTO(TipoIdentificacionEnum tipoDto, String numeroIdentificacion, String nombreTrabajador,
                               Integer diasCotizados, String fechaUltimoRetiro, String ultimoPeriodoAporte) {
        this.tipoDto = tipoDto;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreTrabajador = nombreTrabajador;
        this.diasCotizados = diasCotizados;
        this.fechaUltimoRetiro = fechaUltimoRetiro;
        this.ultimoPeriodoAporte = ultimoPeriodoAporte;
    }

    public TipoIdentificacionEnum getTipoDto() {
        return tipoDto;
    }

    public void setTipoDto(TipoIdentificacionEnum tipoDto) {
        this.tipoDto = tipoDto;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public Integer getDiasCotizados() {
        return diasCotizados;
    }

    public void setDiasCotizados(Integer diasCotizados) {
        this.diasCotizados = diasCotizados;
    }

    public String getFechaUltimoRetiro() {
        return fechaUltimoRetiro;
    }

    public void setFechaUltimoRetiro(String fechaUltimoRetiro) {
        this.fechaUltimoRetiro = fechaUltimoRetiro;
    }

    public String getUltimoPeriodoAporte() {
        return ultimoPeriodoAporte;
    }

    public void setUltimoPeriodoAporte(String ultimoPeriodoAporte) {
        this.ultimoPeriodoAporte = ultimoPeriodoAporte;
    }


}
