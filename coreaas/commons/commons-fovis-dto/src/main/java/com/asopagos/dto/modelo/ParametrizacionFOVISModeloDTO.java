package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.fovis.parametricas.ParametrizacionFOVIS;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.fovis.PlazoVencimientoEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que se encarga de almacenar los datos de el Modelo de la entidad ParametrizacionFOVIS<br/>
 * <b>Módulo:</b> Asopagos - HU-312-022 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class ParametrizacionFOVISModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4580677998545498705L;
    /**
     * Identificador único de la parametrización FOVIS
     */
    private Long idParametrizacion;
    /**
     * Enum que indica el nombre del parámetro
     */
    private ParametroFOVISEnum parametro;
    /**
     * Indica el plazo de vencimiento para la parametricacion de novedades de vencimiento de subsidios
     */
    private PlazoVencimientoEnum plazoVencimiento;
    /**
     * Indica el plazo adicional de vencimiento para la parametricacion de novedades de vencimiento de subsidios
     */
    private PlazoVencimientoEnum plazoAdicional;
    /**
     * Valor del parámetro para cuando es texto
     */
    private Boolean valor;
    /**
     * Valor del parámetro para cuando es numérico
     */
    private BigDecimal valorNumerico;
    /**
     * Valor numérico adicional del parámetro
     */
    private BigDecimal valorAdicional;
    /**
     * Valor del parámetro para cuando es texto
     */
    private String valorString;

    /**
     * Constructor de ParametrizacionFOVISDTO
     */
    public ParametrizacionFOVISModeloDTO() {
    }

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase
     */
    public ParametrizacionFOVISModeloDTO(ParametrizacionFOVIS parametrizacionFOVIS) {
        this.idParametrizacion = parametrizacionFOVIS.getIdParametrizacion();
        this.parametro = parametrizacionFOVIS.getParametro();
        this.plazoVencimiento = parametrizacionFOVIS.getPlazoVencimiento();
        this.plazoAdicional = parametrizacionFOVIS.getPlazoAdicional();
        this.valor = parametrizacionFOVIS.getValor();
        this.valorNumerico = parametrizacionFOVIS.getValorNumerico();
        this.valorAdicional = parametrizacionFOVIS.getValorAdicional();
        this.valorString = parametrizacionFOVIS.getValorString();
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return Entity ParametrizacionFOVIS
     */
    public ParametrizacionFOVIS convertToEntity() {
        ParametrizacionFOVIS parametrizacionFOVIS = new ParametrizacionFOVIS();
        parametrizacionFOVIS.setIdParametrizacion(this.getIdParametrizacion());
        parametrizacionFOVIS.setParametro(this.getParametro());
        parametrizacionFOVIS.setPlazoVencimiento(this.getPlazoVencimiento());
        parametrizacionFOVIS.setPlazoAdicional(this.getPlazoAdicional());
        parametrizacionFOVIS.setValor(this.getValor());
        parametrizacionFOVIS.setValorNumerico(this.getValorNumerico());
        parametrizacionFOVIS.setValorAdicional(this.getValorAdicional());
        parametrizacionFOVIS.setValorString(this.getValorString());
        return parametrizacionFOVIS;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro
     * @param parametrizacionFOVIS
     * @return La parametrizacionFOVIS con las propiedades modificadas
     */
    public ParametrizacionFOVIS copyDTOToEntity(ParametrizacionFOVIS parametrizacionFOVIS) {
        if (this.getIdParametrizacion() != null) {
            parametrizacionFOVIS.setIdParametrizacion(this.getIdParametrizacion());
        }
        if (this.getParametro() != null) {
            parametrizacionFOVIS.setParametro(this.getParametro());
        }
        if (this.getPlazoVencimiento() != null) {
            parametrizacionFOVIS.setPlazoVencimiento(this.getPlazoVencimiento());
        }
        if (this.getPlazoAdicional() != null) {
            parametrizacionFOVIS.setPlazoAdicional(this.getPlazoAdicional());
        }
        if (this.getValor() != null) {
            parametrizacionFOVIS.setValor(this.getValor());
        }
        if (this.getValorNumerico() != null) {
            parametrizacionFOVIS.setValorNumerico(this.getValorNumerico());
        }
        if (this.getValorAdicional() != null) {
            parametrizacionFOVIS.setValorAdicional(this.getValorAdicional());
        }
        if (this.getValorString() != null) {
            parametrizacionFOVIS.setValorString(this.getValorString());
        }
        return parametrizacionFOVIS;
    }

    /**
     * @return the idParametrizacion
     */
    public Long getIdParametrizacion() {
        return idParametrizacion;
    }

    /**
     * @param idParametrizacion
     *        the idParametrizacion to set
     */
    public void setIdParametrizacion(Long idParametrizacion) {
        this.idParametrizacion = idParametrizacion;
    }

    /**
     * @return the parametro
     */
    public ParametroFOVISEnum getParametro() {
        return parametro;
    }

    /**
     * @param parametro
     *        the parametro to set
     */
    public void setParametro(ParametroFOVISEnum parametro) {
        this.parametro = parametro;
    }

    /**
     * @return the valor
     */
    public Boolean getValor() {
        return valor;
    }

    /**
     * @param valor
     *        the valor to set
     */
    public void setValor(Boolean valor) {
        this.valor = valor;
    }

    /**
     * @return the valorNumerico
     */
    public BigDecimal getValorNumerico() {
        return valorNumerico;
    }

    /**
     * @param valorNumerico
     *        the valorNumerico to set
     */
    public void setValorNumerico(BigDecimal valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    /**
     * @return the plazoVencimiento
     */
    public PlazoVencimientoEnum getPlazoVencimiento() {
        return plazoVencimiento;
    }

    /**
     * @param plazoVencimiento
     *        the plazoVencimiento to set
     */
    public void setPlazoVencimiento(PlazoVencimientoEnum plazoVencimiento) {
        this.plazoVencimiento = plazoVencimiento;
    }

    /**
     * @return the plazoAdicional
     */
    public PlazoVencimientoEnum getPlazoAdicional() {
        return plazoAdicional;
    }

    /**
     * @param plazoAdicional
     *        the plazoAdicional to set
     */
    public void setPlazoAdicional(PlazoVencimientoEnum plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    /**
     * @return the valorAdicional
     */
    public BigDecimal getValorAdicional() {
        return valorAdicional;
    }

    /**
     * @param valorAdicional
     *        the valorAdicional to set
     */
    public void setValorAdicional(BigDecimal valorAdicional) {
        this.valorAdicional = valorAdicional;
    }

    /**
     * @return the valorString
     */
    public String getValorString() {
        return valorString;
    }

    /**
     * @param valorString
     *        the valorString to set
     */
    public void setValorString(String valorString) {
        this.valorString = valorString;
    }

}
