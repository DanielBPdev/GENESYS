/**
 * 
 */
package com.asopagos.afiliados.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Beneficiario;

/**
 * DTO que contiene la informacion del beneficiario con su afiliacion
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class BeneficiarioAfiliacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1720407893269424476L;
    /**
     * Afiliacion principal al que pertenece el beneficiario
     */
    private Long idAfiliado;
    /**
     * Total de afiliaciones activas que tiene el beneficiario
     */
    private Long totalAfiliacion;
    /**
     * Beneficiario
     */
    private Beneficiario beneficiario;

    /**
     * 
     */
    public BeneficiarioAfiliacionDTO() {

    }

    /**
     * @param idAfiliado
     * @param totalAfiliacion
     * @param beneficiario
     */
    public BeneficiarioAfiliacionDTO(Long idAfiliado, Long totalAfiliacion, Beneficiario beneficiario) {
        super();
        this.idAfiliado = idAfiliado;
        this.totalAfiliacion = totalAfiliacion;
        this.beneficiario = beneficiario;
    }

    /**
     * Método que retorna el valor de idAfiliado.
     * @return valor de idAfiliado.
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * Método que retorna el valor de totalAfiliacion.
     * @return valor de totalAfiliacion.
     */
    public Long getTotalAfiliacion() {
        return totalAfiliacion;
    }

    /**
     * Método que retorna el valor de beneficiario.
     * @return valor de beneficiario.
     */
    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    /**
     * Método encargado de modificar el valor de idAfiliado.
     * @param valor
     *        para modificar idAfiliado.
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * Método encargado de modificar el valor de totalAfiliacion.
     * @param valor
     *        para modificar totalAfiliacion.
     */
    public void setTotalAfiliacion(Long totalAfiliacion) {
        this.totalAfiliacion = totalAfiliacion;
    }

    /**
     * Método encargado de modificar el valor de beneficiario.
     * @param valor
     *        para modificar beneficiario.
     */
    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }
}
