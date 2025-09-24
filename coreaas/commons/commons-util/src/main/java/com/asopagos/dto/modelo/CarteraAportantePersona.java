package com.asopagos.dto.modelo;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import java.io.Serializable;

public class CarteraAportantePersona implements Serializable {

    private static final long serialVersionUID = 8859026526280068108L;

    private String nombreCotizante;
    private String apellidoCotizante;
    private Long deudaUnitaria;
    private TipoIdentificacionEnum tipoIdentificacion;
    private CarteraModeloDTO aportante;

    public CarteraAportantePersona() {
    }

    public String getNombreCotizante() {
        return nombreCotizante;
    }

    public void setNombreCotizante(String nombreCotizante) {
        this.nombreCotizante = nombreCotizante;
    }

    public String getApellidoCotizante() {
        return apellidoCotizante;
    }

    public void setApellidoCotizante(String apellidoCotizante) {
        this.apellidoCotizante = apellidoCotizante;
    }

    public Long getDeudaUnitaria() {
        return deudaUnitaria;
    }

    public void setDeudaUnitaria(Long deudaUnitaria) {
        this.deudaUnitaria = deudaUnitaria;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public CarteraModeloDTO getAportante() {
        return aportante;
    }

    public void setAportante(CarteraModeloDTO aportante) {
        this.aportante = aportante;
    }
}
