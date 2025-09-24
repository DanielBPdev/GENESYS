package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class InfoEmpleadorInDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Tipo de identificación del empleador
    */
    private TipoIdentificacionEnum tipoID;

    /**
    * Número de identificación del empleador
    */
    private String identificacionEmpleador;

    /**
     * Número de identificación del afiliado
     */
    private String identificacionAfiliado;
    
    /**
     * Código de la sucursal 
     */
    private String codigoSucursal;
    
    /**
     * 
     */
    public InfoEmpleadorInDTO() {
    }

    /**
     * @param tipoID
     * @param identificacionEmpleador
     * @param identificacionAfiliado
     * @param codigoSucursal
     */
    public InfoEmpleadorInDTO(TipoIdentificacionEnum tipoID, String identificacionEmpleador, String identificacionAfiliado,
            String codigoSucursal) {
        this.tipoID = tipoID;
        this.identificacionEmpleador = identificacionEmpleador;
        this.identificacionAfiliado = identificacionAfiliado;
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * @return the tipoID
     */
    public TipoIdentificacionEnum getTipoID() {
        return tipoID;
    }

    /**
     * @param tipoID the tipoID to set
     */
    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    /**
     * @return the identificacionEmpleador
     */
    public String getIdentificacionEmpleador() {
        return identificacionEmpleador;
    }

    /**
     * @param identificacionEmpleador the identificacionEmpleador to set
     */
    public void setIdentificacionEmpleador(String identificacionEmpleador) {
        this.identificacionEmpleador = identificacionEmpleador;
    }

    /**
     * @return the identificacionAfiliado
     */
    public String getIdentificacionAfiliado() {
        return identificacionAfiliado;
    }

    /**
     * @param identificacionAfiliado the identificacionAfiliado to set
     */
    public void setIdentificacionAfiliado(String identificacionAfiliado) {
        this.identificacionAfiliado = identificacionAfiliado;
    }

    /**
     * @return the codigoSucursal
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * @param codigoSucursal the codigoSucursal to set
     */
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }
}
