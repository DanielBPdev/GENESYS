package com.asopagos.novedades.dto;

/**
 * DTO que contiene los campos de una persona de una sucursal.
 * 
 * @author Julián Andrés Muñoz Cardozo <jmunoz@heinsohn.com.co>
 *
 */
public class SucursalPersonaDTO {
    
    /**
     * Variable idPersona
     */
    private Long idPersona;
    
    /**
     * Variable IdSucursal
     */
    private Long idSucursal;

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the idSucursal
     */
    public Long getIdSucursal() {
        return idSucursal;
    }

    /**
     * @param idSucursal the idSucursal to set
     */
    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
    
}
