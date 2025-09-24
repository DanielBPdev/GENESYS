package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformacionBeneficiarioAfiliadoDTO implements Serializable {

    private TipoIdentificacionEnum tipoIdentificacionAfiliado;

    private String numeroIdentificacionAfiliado;

    private String primerNombreAfiliado;
    
    private String segundoNombreAfiliado;

    private String primerApellidoAfiliado;

    private String segundoApellidoAfiliado;

    private CategoriaPersonaEnum categoria;


    public InformacionBeneficiarioAfiliadoDTO() {
    }

    public InformacionBeneficiarioAfiliadoDTO(TipoIdentificacionEnum tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado, String primerNombreAfiliado, String segundoNombreAfiliado, String primerApellidoAfiliado, String segundoApellidoAfiliado, CategoriaPersonaEnum categoria) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
        this.primerNombreAfiliado = primerNombreAfiliado;
        this.segundoNombreAfiliado = segundoNombreAfiliado;
        this.primerApellidoAfiliado = primerApellidoAfiliado;
        this.segundoApellidoAfiliado = segundoApellidoAfiliado;
        this.categoria = categoria;
    }


    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return this.tipoIdentificacionAfiliado;
    }

    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }

    public String getNumeroIdentificacionAfiliado() {
        return this.numeroIdentificacionAfiliado;
    }

    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }

    public String getPrimerNombreAfiliado() {
        return this.primerNombreAfiliado;
    }

    public void setPrimerNombreAfiliado(String primerNombreAfiliado) {
        this.primerNombreAfiliado = primerNombreAfiliado;
    }

    public String getSegundoNombreAfiliado() {
        return this.segundoNombreAfiliado;
    }

    public void setSegundoNombreAfiliado(String segundoNombreAfiliado) {
        this.segundoNombreAfiliado = segundoNombreAfiliado;
    }

    public String getPrimerApellidoAfiliado() {
        return this.primerApellidoAfiliado;
    }

    public void setPrimerApellidoAfiliado(String primerApellidoAfiliado) {
        this.primerApellidoAfiliado = primerApellidoAfiliado;
    }

    public String getSegundoApellidoAfiliado() {
        return this.segundoApellidoAfiliado;
    }

    public void setSegundoApellidoAfiliado(String segundoApellidoAfiliado) {
        this.segundoApellidoAfiliado = segundoApellidoAfiliado;
    }

    public CategoriaPersonaEnum getCategoria() {
        return this.categoria;
    }

    public void setCategoria(CategoriaPersonaEnum categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "{" +
            " tipoIdentificacionAfiliado='" + getTipoIdentificacionAfiliado() + "'" +
            ", numeroIdentificacionAfiliado='" + getNumeroIdentificacionAfiliado() + "'" +
            ", primerNombreAfiliado='" + getPrimerNombreAfiliado() + "'" +
            ", segundoNombreAfiliado='" + getSegundoNombreAfiliado() + "'" +
            ", primerApellidoAfiliado='" + getPrimerApellidoAfiliado() + "'" +
            ", segundoApellidoAfiliado='" + getSegundoApellidoAfiliado() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }

}
