package com.asopagos.reportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class InfoTotalBeneficiarioInDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoID;
    
    /**
     * Número de identificación del beneficiario
     */
    private String identificacionBeneficiario;
    
    /**
     * Número de identificación de la persona
     */
    private String identificacionAfiliado;

    /**
     * 
     */
    public InfoTotalBeneficiarioInDTO() {
    }

    /**
     * @param tipoID
     * @param identificacionBeneficiario
     * @param identificacionAfiliado
     */
    public InfoTotalBeneficiarioInDTO(TipoIdentificacionEnum tipoID, String identificacionBeneficiario, String identificacionAfiliado) {
        this.tipoID = tipoID;
        this.identificacionBeneficiario = identificacionBeneficiario;
        this.identificacionAfiliado = identificacionAfiliado;
    }

    /**
     * @return the tipoID
     */
    public TipoIdentificacionEnum getTipoID() {
        return tipoID;
    }

    /**
     * @param tipoID
     *        the tipoID to set
     */
    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    /**
     * @return the identificacionBeneficiario
     */
    public String getIdentificacionBeneficiario() {
        return identificacionBeneficiario;
    }

    /**
     * @param identificacionBeneficiario
     *        the identificacionBeneficiario to set
     */
    public void setIdentificacionBeneficiario(String identificacionBeneficiario) {
        this.identificacionBeneficiario = identificacionBeneficiario;
    }

    /**
     * @return the identificacionAfiliado
     */
    public String getIdentificacionAfiliado() {
        return identificacionAfiliado;
    }

    /**
     * @param identificacionAfiliado
     *        the identificacionAfiliado to set
     */
    public void setIdentificacionAfiliado(String identificacionAfiliado) {
        this.identificacionAfiliado = identificacionAfiliado;
    }
}
