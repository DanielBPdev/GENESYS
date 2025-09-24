/**
 * 
 */
package com.asopagos.dto.aportes;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;

/**
 * <b>Descripción</b> Clase DTO que permite modelar una evaluación vigente
 * dentro de un análisis de devolución de aportes.
 * 
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra
 *         Zuluaga. </a>
 */
@XmlRootElement
public class EvaluacionDTO implements Serializable{

	/**
	 * Valor del aporte a devolver.
	 */
	private BigDecimal valorAporte;

	/**
	 * Tipo de Solicitante del cotizante.
	 */
	private TipoAfiliadoEnum persona;

	/**
	 * Validación de Clase de aportante dependiente.
	 */
	private EstadoValidacionRegistroAporteEnum depV0;

	/**
	 * Campo que indica el resultado de la validación de la “Tarifa” para un
	 * “Dependiente”.
	 */
	private EstadoValidacionRegistroAporteEnum depV1;

	/**
	 * Campo que indica el resultado de la validación del “Tipo de cotizante”
	 * para un “Dependiente”.
	 */
	private EstadoValidacionRegistroAporteEnum depV2;

	/**
	 * Campo que indica el resultado de la validación de los “Días cotizados”
	 * para un “Dependiente”.
	 */
	private EstadoValidacionRegistroAporteEnum depV3;

	/**
	 * Campo que indica el resultado de la validación de la “Tarifa” para un
	 * “Independiente”.
	 */
	private EstadoValidacionRegistroAporteEnum idv1;

	/**
	 * Campo que indica el resultado de la validación de la “Tarifa” para un
	 * “Pensionado”.
	 */
	private EstadoValidacionRegistroAporteEnum pdv1 ;

	/**
	 * Validación de Clase de aportante dependiente.
	 */
	private String idEcm;

    /**
     * Método que retorna el valor de valorAporte.
     * @return valor de valorAporte.
     */
    public BigDecimal getValorAporte() {
        return valorAporte;
    }

    /**
     * Método encargado de modificar el valor de valorAporte.
     * @param valor para modificar valorAporte.
     */
    public void setValorAporte(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }

    /**
     * Método que retorna el valor de persona.
     * @return valor de persona.
     */
    public TipoAfiliadoEnum getPersona() {
        return persona;
    }

    /**
     * Método encargado de modificar el valor de persona.
     * @param valor para modificar persona.
     */
    public void setPersona(TipoAfiliadoEnum persona) {
        this.persona = persona;
    }

    /**
     * Método que retorna el valor de depV0.
     * @return valor de depV0.
     */
    public EstadoValidacionRegistroAporteEnum getDepV0() {
        return depV0;
    }

    /**
     * Método encargado de modificar el valor de depV0.
     * @param valor para modificar depV0.
     */
    public void setDepV0(EstadoValidacionRegistroAporteEnum depV0) {
        this.depV0 = depV0;
    }

    /**
     * Método que retorna el valor de depV1.
     * @return valor de depV1.
     */
    public EstadoValidacionRegistroAporteEnum getDepV1() {
        return depV1;
    }

    /**
     * Método encargado de modificar el valor de depV1.
     * @param valor para modificar depV1.
     */
    public void setDepV1(EstadoValidacionRegistroAporteEnum depV1) {
        this.depV1 = depV1;
    }

    /**
     * Método que retorna el valor de depV2.
     * @return valor de depV2.
     */
    public EstadoValidacionRegistroAporteEnum getDepV2() {
        return depV2;
    }

    /**
     * Método encargado de modificar el valor de depV2.
     * @param valor para modificar depV2.
     */
    public void setDepV2(EstadoValidacionRegistroAporteEnum depV2) {
        this.depV2 = depV2;
    }

    /**
     * Método que retorna el valor de depV3.
     * @return valor de depV3.
     */
    public EstadoValidacionRegistroAporteEnum getDepV3() {
        return depV3;
    }

    /**
     * Método encargado de modificar el valor de depV3.
     * @param valor para modificar depV3.
     */
    public void setDepV3(EstadoValidacionRegistroAporteEnum depV3) {
        this.depV3 = depV3;
    }

    /**
     * Método que retorna el valor de idv1.
     * @return valor de idv1.
     */
    public EstadoValidacionRegistroAporteEnum getIdv1() {
        return idv1;
    }

    /**
     * Método encargado de modificar el valor de idv1.
     * @param valor para modificar idv1.
     */
    public void setIdv1(EstadoValidacionRegistroAporteEnum idv1) {
        this.idv1 = idv1;
    }

    /**
     * Método que retorna el valor de pdv1.
     * @return valor de pdv1.
     */
    public EstadoValidacionRegistroAporteEnum getPdv1() {
        return pdv1;
    }

    /**
     * Método encargado de modificar el valor de pdv1.
     * @param valor para modificar pdv1.
     */
    public void setPdv1(EstadoValidacionRegistroAporteEnum pdv1) {
        this.pdv1 = pdv1;
    }

    /**
     * Método que retorna el valor de idEcm.
     * @return valor de idEcm.
     */
    public String getIdEcm() {
        return idEcm;
    }

    /**
     * Método encargado de modificar el valor de idEcm.
     * @param valor para modificar idEcm.
     */
    public void setIdEcm(String idEcm) {
        this.idEcm = idEcm;
    }


}
