package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class BeneficiarioSTDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
    * Tipo de identificación del beneficiario
    */
    private TipoIdentificacionEnum tipoID;
    
    /**
    * Número de identificación del beneficiario
    */
    private String identificacion;
    
    /**
    * Estado del beneficiario
    */
    private EstadoAfiliadoEnum estado;
    
    /**
    * (Cónyuge - Hijo - Padre)
    */
    private ClasificacionEnum tipoDeBeneficiario;

    /**
     * 
     */
    public BeneficiarioSTDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     * @param estado
     * @param tipoDeBeneficiario
     */
    public BeneficiarioSTDTO(TipoIdentificacionEnum tipoID, String identificacion, EstadoAfiliadoEnum estado,
            ClasificacionEnum tipoDeBeneficiario) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.estado = estado;
        this.tipoDeBeneficiario = tipoDeBeneficiario;
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
     * @return the estado
     */
    public EstadoAfiliadoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoAfiliadoEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoDeBeneficiario
     */
    public ClasificacionEnum getTipoDeBeneficiario() {
        return tipoDeBeneficiario;
    }

    /**
     * @param tipoDeBeneficiario the tipoDeBeneficiario to set
     */
    public void setTipoDeBeneficiario(ClasificacionEnum tipoDeBeneficiario) {
        this.tipoDeBeneficiario = tipoDeBeneficiario;
    }
}
