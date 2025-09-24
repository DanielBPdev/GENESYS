package com.asopagos.reportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class InfoAfiliadoInDTO implements Serializable{

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
    * Código de la caja de compensación. Este es el código nacional de la caja (CCF###) 
    * el cual no afecta la consulta, pero debe ir en los parámetros de entrada por defecto 
    * parametrizado con el código de la caja respectiva. 
    * 
    * El estándar de este WS fue generado por el convenio Caja Sin Fronteras y cada 
    * CCF debe exponer el mismo WS. 
    * El propósito de este campo es diferenciar este WS para cada CCF.
    */
    private String codigoCaja;

    /**
    * Tipo de afiliado (Dependiente, Independiente, Pensionado)
    */
    private TipoAfiliadoEnum tipoAfiliado;

    /**
     * 
     */
    public InfoAfiliadoInDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     * @param codigoCaja
     * @param tipoAfiliado
     */
    public InfoAfiliadoInDTO(TipoIdentificacionEnum tipoID, String identificacion, String codigoCaja, TipoAfiliadoEnum tipoAfiliado) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.codigoCaja = codigoCaja;
        this.tipoAfiliado = tipoAfiliado;
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
     * @return the codigoCaja
     */
    public String getCodigoCaja() {
        return codigoCaja;
    }

    /**
     * @param codigoCaja the codigoCaja to set
     */
    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }
}
