package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contiene la empresa y sucursal creadas a partir de los datos 
 * de la planilla PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-392 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CreacionAportanteDTO implements Serializable {
    private static final long serialVersionUID = -9201734768697502123L;
    
    /** DTO de la empresa a crear */
    private EmpresaModeloDTO empresa;
    
    /** DTO de la sucursal a crear */
    private SucursalEmpresaModeloDTO sucursal;
    
    /** indicador de proceso de creación exitosa */
    private Boolean creacionExitosa = Boolean.FALSE;

    /**
     * @return the empresa
     */
    public EmpresaModeloDTO getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(EmpresaModeloDTO empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the sucursal
     */
    public SucursalEmpresaModeloDTO getSucursal() {
        return sucursal;
    }

    /**
     * @param sucursal the sucursal to set
     */
    public void setSucursal(SucursalEmpresaModeloDTO sucursal) {
        this.sucursal = sucursal;
    }

    /**
     * @return the creacionExitosa
     */
    public Boolean getCreacionExitosa() {
        return creacionExitosa;
    }

    /**
     * @param creacionExitosa the creacionExitosa to set
     */
    public void setCreacionExitosa(Boolean creacionExitosa) {
        this.creacionExitosa = creacionExitosa;
    }

}
