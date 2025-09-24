package com.asopagos.reportes.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.ClasificacionEnum;


public class DetalleBeneficiarioDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long idDetalleBeneficiario;
    private ClasificacionEnum tipoBeneficiario;
    /**
     * @return the idDetalleBeneficiario
     */
    public Long getIdDetalleBeneficiario() {
        return idDetalleBeneficiario;
    }
    /**
     * @param idDetalleBeneficiario the idDetalleBeneficiario to set
     */
    public void setIdDetalleBeneficiario(Long idDetalleBeneficiario) {
        this.idDetalleBeneficiario = idDetalleBeneficiario;
    }
    /**
     * @return the tipoBeneficiario
     */
    public ClasificacionEnum getTipoBeneficiario() {
        return tipoBeneficiario;
    }
    /**
     * @param tipoBeneficiario the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(ClasificacionEnum tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }
}
