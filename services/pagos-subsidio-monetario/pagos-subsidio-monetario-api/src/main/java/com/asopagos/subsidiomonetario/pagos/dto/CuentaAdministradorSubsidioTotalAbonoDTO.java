package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CuentaAdministradorSubsidioTotalAbonoDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * lista cuenta administrador subsidio
     */
    private List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidios;
    
    /**
     * 
     */
    private BigDecimal valorTotalAbono;

    /**
     * @return the listaCuentaAdminSubsidios
     */
    public List<CuentaAdministradorSubsidioDTO> getListaCuentaAdminSubsidios() {
        return listaCuentaAdminSubsidios;
    }

    /**
     * @param listaCuentaAdminSubsidios the listaCuentaAdminSubsidios to set
     */
    public void setListaCuentaAdminSubsidios(List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidios) {
        this.listaCuentaAdminSubsidios = listaCuentaAdminSubsidios;
    }

    /**
     * @return the valorTotalAbono
     */
    public BigDecimal getValorTotalAbono() {
        return valorTotalAbono;
    }

    /**
     * @param valorTotalAbono the valorTotalAbono to set
     */
    public void setValorTotalAbono(BigDecimal valorTotalAbono) {
        this.valorTotalAbono = valorTotalAbono;
    }
    
}
