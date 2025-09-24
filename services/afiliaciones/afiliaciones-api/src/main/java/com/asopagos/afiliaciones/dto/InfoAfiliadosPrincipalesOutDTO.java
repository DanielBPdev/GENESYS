package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class InfoAfiliadosPrincipalesOutDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
     
    /**
    * Tipo de identificación del afiliado
    */
    private TipoIdentificacionEnum tipoID;
    
    /**
    * Número de identificación del afiliado
    */
    private String identificacion;
    
    /**
    * Primer Nombre del afiliado
    */
    private String razonSocial;
    
    /**
    * Segundo nombre del afiliado
    */
    private String estadoAfiliado;

    /**
    * ID del afiliado
    */
    private String perId;

    public TipoIdentificacionEnum getTipoID() {
        return this.tipoID;
    }

    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    public String getIdentificacion() {
        return this.identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getEstadoAfiliado() {
        return this.estadoAfiliado;
    }

    public void setEstadoAfiliado(String estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }

    public String getPerId() {
        return this.perId;
    }

    public void setPerId(String perId) {
        this.perId = perId;
    }

    @Override
    public String toString() {
        return "{" +
            " tipoID='" + getTipoID() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", estadoAfiliado='" + getEstadoAfiliado() + "'" +
            ", perId='" + getPerId() + "'" +
            "}";
    }

}
