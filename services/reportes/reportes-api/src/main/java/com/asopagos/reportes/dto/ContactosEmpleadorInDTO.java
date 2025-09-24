package com.asopagos.reportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class ContactosEmpleadorInDTO implements Serializable{



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
    private String identificacion;

    /**
     * Código de la sucursal 
     */
    private String codigoSucursal;
    
    /**
     * 
     */
    public ContactosEmpleadorInDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     * @param codigoSucursal
     */
    public ContactosEmpleadorInDTO(TipoIdentificacionEnum tipoID, String identificacion, String codigoSucursal) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
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
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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
