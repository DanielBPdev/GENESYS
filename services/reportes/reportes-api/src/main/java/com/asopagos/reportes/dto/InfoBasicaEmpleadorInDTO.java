package com.asopagos.reportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class InfoBasicaEmpleadorInDTO implements Serializable{



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
     * 
     */
    public InfoBasicaEmpleadorInDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     */
    public InfoBasicaEmpleadorInDTO(TipoIdentificacionEnum tipoID, String identificacion) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
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
}
