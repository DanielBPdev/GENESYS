package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.TipoConsultanteAportesWS;


public class ConsultarAportesInDTO {

    private Long numeroIdentificacion;
    private Integer anio;
    private TipoConsultanteAportesWS tipoConsultante;

    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(Long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public TipoConsultanteAportesWS getTipoConsultante() {
        return tipoConsultante;
    }

    public void setTipoConsultante(TipoConsultanteAportesWS tipoConsultante) {
        this.tipoConsultante = tipoConsultante;
    }

    public ConsultarAportesInDTO(Long numeroIdentificacion, Integer anio, TipoConsultanteAportesWS tipoConsultante) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.anio = anio;
        this.tipoConsultante = tipoConsultante;
    }
    
    public ConsultarAportesInDTO() {
    }   
}
