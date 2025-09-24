package com.asopagos.reportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class GrupoFamiliarInDTO implements Serializable{

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
    private String identificacionAfiliado;
    
    /**
     * Número de identificación del beneficiario
     */
    private String identificacionBeneficiario;
    
    /**
     * 
     */
    public GrupoFamiliarInDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     */
    public GrupoFamiliarInDTO(TipoIdentificacionEnum tipoID, String identificacionAfiliado, String identificacionBeneficiario) {
        this.tipoID = tipoID;
        this.identificacionAfiliado = identificacionAfiliado;
        this.identificacionBeneficiario = identificacionBeneficiario;
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
     * @return the identificacionBeneficiario
     */
    public String getIdentificacionBeneficiario() {
        return identificacionBeneficiario;
    }

    /**
     * @param identificacionBeneficiario the identificacionBeneficiario to set
     */
    public void setIdentificacionBeneficiario(String identificacionBeneficiario) {
        this.identificacionBeneficiario = identificacionBeneficiario;
    }
}
