package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;

/**
 * <b>Descripcion:</b> Clase que encapsula los datos de evaluacion general Producto No Conforme<br/>
 * <b>Módulo:</b> Asopagos - HU 121-122<br/>
 *
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co"> hhernandez</a>
 */
public class ResultadoGeneralProductoNoConformeBeneficiarioDTO implements Serializable {

    private Long idBeneficiario;

    private ResultadoGeneralProductoNoConformeEnum resultadoGeneralBeneficiario;

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario
     *        the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the resultadoGeneralBeneficiario
     */
    public ResultadoGeneralProductoNoConformeEnum getResultadoGeneralBeneficiario() {
        return resultadoGeneralBeneficiario;
    }

    /**
     * @param resultadoGeneralBeneficiario
     *        the resultadoGeneralBeneficiario to set
     */
    public void setResultadoGeneralBeneficiario(ResultadoGeneralProductoNoConformeEnum resultadoGeneralBeneficiario) {
        this.resultadoGeneralBeneficiario = resultadoGeneralBeneficiario;
    }
    
}
