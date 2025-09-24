package com.asopagos.dto.webservices;

import java.io.Serializable;
import javax.validation.constraints.*;


import org.hibernate.validator.constraints.Email;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import java.math.BigDecimal;
import java.util.Date;


public class AfiliaPensionadoDTO extends AfiliaTrabajadorDTO implements Serializable,Cloneable{


    @NotNull(message = "El campo tipoSolicitante es obligatorio.")
    private TipoTipoSolicitanteEnum tipoSolicitante;

    @NotNull(message = "El campo valorMesadaPensional es obligatorio.")
    private BigDecimal valorMesadaPensional;

    @NotNull(message = "El campo pagadorPension es obligatorio.")
    private String pagadorPension;

    public AfiliaPensionadoDTO() {
        super();
    }

    public AfiliaPensionadoDTO(TipoTipoSolicitanteEnum tipoSolicitante, BigDecimal valorMesadaPensional, String pagadorPension) {
        super();
        this.tipoSolicitante = tipoSolicitante;
        this.valorMesadaPensional = valorMesadaPensional;
        this.pagadorPension = pagadorPension;
    }

    public TipoTipoSolicitanteEnum getTipoSolicitante() {
        return this.tipoSolicitante;
    }
    
    public void setTipoSolicitante(TipoTipoSolicitanteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public BigDecimal getValorMesadaPensional() {
        return this.valorMesadaPensional;
    }

    public void setValorMesadaPensional(BigDecimal valorMesadaPensional) {
        this.valorMesadaPensional = valorMesadaPensional;
    }

    public String getPagadorPension() {
        return this.pagadorPension;
    }
    
    public void setPagadorPension(String pagadorPension) {
        this.pagadorPension = pagadorPension;
    }

    public AfiliaPensionadoDTO tipoSolicitante(TipoTipoSolicitanteEnum tipoSolicitante) {
        setTipoSolicitante(tipoSolicitante);
        return this;
    }

    public AfiliaPensionadoDTO valorMesadaPensional(BigDecimal valorMesadaPensional) {
        setValorMesadaPensional(valorMesadaPensional);
        return this;
    }

    public AfiliaPensionadoDTO pagadorPension(String pagadorPension) {
        setPagadorPension(pagadorPension);
        return this;
    }

    
    @Override
    public String toString() {
        return "{" +
            " tipoSolicitante='" + getTipoSolicitante() + "'" +
            ", valorMesadaPensional='" + getValorMesadaPensional() + "'" +
            ", pagadorPension='" + getPagadorPension() + "'" +
            "}";
    }
}