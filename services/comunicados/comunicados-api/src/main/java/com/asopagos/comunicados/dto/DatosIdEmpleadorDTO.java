package com.asopagos.comunicados.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class DatosIdEmpleadorDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TipoIdentificacionEnum tipoIdEmpleador;
    private String numeroIdEmpleador;
    private int orden;

    /**
     * 
     */
    public DatosIdEmpleadorDTO() {
    }

    /**
     * @param tipoIdEmpleador
     * @param numeroIdEmpleador
     */
    public DatosIdEmpleadorDTO(TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
        this.tipoIdEmpleador = tipoIdEmpleador;
        this.numeroIdEmpleador = numeroIdEmpleador;
    }
    
    /**
     * @param tipoIdEmpleador
     * @param numeroIdEmpleador
     */
    public DatosIdEmpleadorDTO(TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, int orden) {
        this.tipoIdEmpleador = tipoIdEmpleador;
        this.numeroIdEmpleador = numeroIdEmpleador;
        this.orden = orden;
    }

    /**
     * @return the tipoIdEmpleador
     */
    public TipoIdentificacionEnum getTipoIdEmpleador() {
        return tipoIdEmpleador;
    }

    /**
     * @param tipoIdEmpleador
     *        the tipoIdEmpleador to set
     */
    public void setTipoIdEmpleador(TipoIdentificacionEnum tipoIdEmpleador) {
        this.tipoIdEmpleador = tipoIdEmpleador;
    }

    /**
     * @return the numeroIdEmpleador
     */
    public String getNumeroIdEmpleador() {
        return numeroIdEmpleador;
    }

    /**
     * @param numeroIdEmpleador
     *        the numeroIdEmpleador to set
     */
    public void setNumeroIdEmpleador(String numeroIdEmpleador) {
        this.numeroIdEmpleador = numeroIdEmpleador;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    
}
