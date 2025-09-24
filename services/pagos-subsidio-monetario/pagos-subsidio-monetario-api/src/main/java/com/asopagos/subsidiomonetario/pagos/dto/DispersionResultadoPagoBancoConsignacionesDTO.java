package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de las consignaciones realizadas en la sección pago en bancos de la dispersión
 * masiva de liquidación.<br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class DispersionResultadoPagoBancoConsignacionesDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -915308116060635176L;

    /** Identificador de la entidad bancaria */
    private BigDecimal identificadorBanco;

    /** Nombre del banco donde se realizaron las consignaciones */
    private String nombreBanco;

    /** Numero NIT */
    private String NIT;

    /** Lista donde se muestra quienes realizaron las consignaciones en el banco */
    private List<ItemResultadoPagoBancoDTO> lstConsignaciones;

    /**
     * @return the identificadorBanco
     */
    public BigDecimal getIdentificadorBanco() {
        return identificadorBanco;
    }

    /**
     * @param identificadorBanco
     *        the identificadorBanco to set
     */
    public void setIdentificadorBanco(BigDecimal identificadorBanco) {
        this.identificadorBanco = identificadorBanco;
    }

    /**
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @param nombreBanco
     *        the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @return the nIT
     */
    public String getNIT() {
        return NIT;
    }

    /**
     * @param nIT
     *        the nIT to set
     */
    public void setNIT(String nIT) {
        NIT = nIT;
    }

    /**
     * @return the lstCosignaciones
     */
    public List<ItemResultadoPagoBancoDTO> getLstConsignaciones() {
        return lstConsignaciones;
    }

    /**
     * @param lstCosignaciones
     *        the lstCosignaciones to set
     */
    public void setLstConsignaciones(List<ItemResultadoPagoBancoDTO> lstCosignaciones) {
        this.lstConsignaciones = lstCosignaciones;
    }

}
