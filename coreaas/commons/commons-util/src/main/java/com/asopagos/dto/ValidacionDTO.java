package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;

/**
 * <b>Descripción:</b> DTO que representa el resultado de la ejecución de una
 * validación específica <b>Historia de Usuario: </b>TRA
 * 
 * @author Jorge Leonardo Camargo Cuervo
 *         <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 *         </a>
 */
@XmlRootElement
public class ValidacionDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Es el detalle del resultado de la validación ejecutada
     */
    private String detalle;
    /**
     * Es la validación ejecutada
     */
    private ValidacionCoreEnum validacion;
    /**
     * Es el resultado de la validación ejecutada
     */
    private ResultadoValidacionEnum resultado;

    /**
     * Es el tipo de excepción que puede retornar la validación
     */
    private TipoExcepcionFuncionalEnum tipoExcepcion;

    /**
     * Es el id del beneficiario al que se valido
     */
    private List<Long> idBeneficiario;

    /**
     * Indica el bloque de validaciones al que pertenede la validación
     */
    private String bloque;

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle
     *        the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the validacion
     */
    public ValidacionCoreEnum getValidacion() {
        return validacion;
    }

    /**
     * @param validacion
     *        the validacion to set
     */
    public void setValidacion(ValidacionCoreEnum validacion) {
        this.validacion = validacion;
    }

    /**
     * @return the resultado
     */
    public ResultadoValidacionEnum getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *        the resultado to set
     */
    public void setResultado(ResultadoValidacionEnum resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the tipoExcepcion
     */
    public TipoExcepcionFuncionalEnum getTipoExcepcion() {
        return tipoExcepcion;
    }

    /**
     * @param tipoExcepcion
     *        the tipoExcepcion to set
     */
    public void setTipoExcepcion(TipoExcepcionFuncionalEnum tipoExcepcion) {
        this.tipoExcepcion = tipoExcepcion;
    }

    /**
     * @return the idBeneficiario
     */
    public List<Long> getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario
     *        the idBeneficiario to set
     */
    public void setIdBeneficiario(List<Long> idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the bloque
     */
    public String getBloque() {
        return bloque;
    }

    /**
     * @param bloque
     *        the bloque to set
     */
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    @Override
    public String toString() {
        return "{" +
            " detalle='" + getDetalle() + "'" +
            ", validacion='" + getValidacion() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", tipoExcepcion='" + getTipoExcepcion() + "'" +
            ", idBeneficiario='" + getIdBeneficiario() + "'" +
            ", bloque='" + getBloque() + "'" +
            "}";
    }

}
