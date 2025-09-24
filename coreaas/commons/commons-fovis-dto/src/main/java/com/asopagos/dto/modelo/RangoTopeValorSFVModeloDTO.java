package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.fovis.parametricas.RangoTopeValorSFV;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.OperadorComparacionEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que se encarga de almacenar los datos de el Modelo de la entidad RangoTopeValorSFV<br/>
 * <b>Módulo:</b> Asopagos - HU-312-022 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class RangoTopeValorSFVModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2713503064700470168L;
    /**
     * Identificador del rango SVF para la modalidad
     */
    private Long idRangoTopeValorSFV;
    /**
     * Nombre del rango
     */
    private String nombre;
    /**
     * Valor mínimo del rango para ingresos del hogar
     */
    private BigDecimal valorMinimo;
    /**
     * Valor máximo del rango para ingresos del hogar
     */
    private BigDecimal valorMaximo;
    /**
     * Operador de comparación para el valor mímimo
     */
    private OperadorComparacionEnum operadorValorMinimo;
    /**
     * Operador de comparación para el valor máximo
     */
    private OperadorComparacionEnum operadorValorMaximo;
    /**
     * Tope en SMLMV
     */
    private BigDecimal topeSMLMV;
    /**
     * ModalidadFOVIS a la que pertenece
     */
    private ModalidadEnum idParametrizacionModalidad;

    /**
     * Constructor de RangoTopeValorSFVDTO
     */
    public RangoTopeValorSFVModeloDTO() {
    }

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase
     */
    public RangoTopeValorSFVModeloDTO(RangoTopeValorSFV rangoTopeValorSFV) {
        this.idRangoTopeValorSFV = rangoTopeValorSFV.getIdRangoTopeValorSFV();
        this.nombre = rangoTopeValorSFV.getNombre();
        this.valorMinimo = rangoTopeValorSFV.getValorMinimo();
        this.valorMaximo = rangoTopeValorSFV.getValorMaximo();
        this.operadorValorMinimo = rangoTopeValorSFV.getOperadorValorMinimo();
        this.operadorValorMaximo = rangoTopeValorSFV.getOperadorValorMaximo();
        this.topeSMLMV = rangoTopeValorSFV.getTopeSMLMV();
        this.idParametrizacionModalidad = rangoTopeValorSFV.getModalidad();
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return Entity RangoTopeValorSFV
     */
    public RangoTopeValorSFV convertToEntity() {
        RangoTopeValorSFV rangoTopeValorSFV = new RangoTopeValorSFV();
        rangoTopeValorSFV.setIdRangoTopeValorSFV(this.getIdRangoTopeValorSFV());
        rangoTopeValorSFV.setNombre(this.getNombre());
        rangoTopeValorSFV.setModalidad(this.getIdParametrizacionModalidad());
        rangoTopeValorSFV.setValorMinimo(this.getValorMinimo());
        rangoTopeValorSFV.setValorMaximo(this.getValorMaximo());
        rangoTopeValorSFV.setOperadorValorMinimo(this.getOperadorValorMinimo());
        rangoTopeValorSFV.setOperadorValorMaximo(this.getOperadorValorMaximo());
        rangoTopeValorSFV.setTopeSMLMV(this.getTopeSMLMV());
        return rangoTopeValorSFV;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro
     * @param rangoTopeValorSFV
     * @return el RangoTopeValorSFV con las propiedades modificadas
     */
    public RangoTopeValorSFV copyDTOToEntity(RangoTopeValorSFV rangoTopeValorSFV) {
        if (this.getIdRangoTopeValorSFV() != null) {
            rangoTopeValorSFV.setIdRangoTopeValorSFV(this.getIdRangoTopeValorSFV());
        }
        if (this.getNombre() != null) {
            rangoTopeValorSFV.setNombre(this.getNombre());
        }
        if (this.getIdParametrizacionModalidad() != null) {
            rangoTopeValorSFV.setModalidad(this.getIdParametrizacionModalidad());
        }
        if (this.getOperadorValorMinimo() != null) {
            rangoTopeValorSFV.setOperadorValorMinimo(this.getOperadorValorMinimo());
        }
        if (this.getOperadorValorMaximo() != null) {
            rangoTopeValorSFV.setOperadorValorMaximo(this.getOperadorValorMaximo());
        }
        if (this.getValorMinimo() != null) {
            rangoTopeValorSFV.setValorMinimo(this.getValorMinimo());
        }
        if (this.getValorMaximo() != null) {
            rangoTopeValorSFV.setValorMaximo(this.getValorMaximo());
        }
        if (this.getTopeSMLMV() != null) {
            rangoTopeValorSFV.setTopeSMLMV(this.getTopeSMLMV());
        }
        return rangoTopeValorSFV;
    }

    /**
     * @return the idRangoTopeValorSFV
     */
    public Long getIdRangoTopeValorSFV() {
        return idRangoTopeValorSFV;
    }

    /**
     * @param idRangoTopeValorSFV
     *        the idRangoTopeValorSFV to set
     */
    public void setIdRangoTopeValorSFV(Long idRangoTopeValorSFV) {
        this.idRangoTopeValorSFV = idRangoTopeValorSFV;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the valorMinimo
     */
    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }

    /**
     * @param valorMinimo
     *        the valorMinimo to set
     */
    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    /**
     * @return the valorMaximo
     */
    public BigDecimal getValorMaximo() {
        return valorMaximo;
    }

    /**
     * @param valorMaximo
     *        the valorMaximo to set
     */
    public void setValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    /**
     * @return the operadorValorMinimo
     */
    public OperadorComparacionEnum getOperadorValorMinimo() {
        return operadorValorMinimo;
    }

    /**
     * @param operadorValorMinimo
     *        the operadorValorMinimo to set
     */
    public void setOperadorValorMinimo(OperadorComparacionEnum operadorValorMinimo) {
        this.operadorValorMinimo = operadorValorMinimo;
    }

    /**
     * @return the operadorValorMaximo
     */
    public OperadorComparacionEnum getOperadorValorMaximo() {
        return operadorValorMaximo;
    }

    /**
     * @param operadorValorMaximo
     *        the operadorValorMaximo to set
     */
    public void setOperadorValorMaximo(OperadorComparacionEnum operadorValorMaximo) {
        this.operadorValorMaximo = operadorValorMaximo;
    }

    /**
     * @return the topeSMLMV
     */
    public BigDecimal getTopeSMLMV() {
        return topeSMLMV;
    }

    /**
     * @param topeSMLMV
     *        the topeSMLMV to set
     */
    public void setTopeSMLMV(BigDecimal topeSMLMV) {
        this.topeSMLMV = topeSMLMV;
    }

    /**
     * @return the idParametrizacionModalidad
     */
    public ModalidadEnum getIdParametrizacionModalidad() {
        return idParametrizacionModalidad;
    }

    /**
     * @param idParametrizacionModalidad
     *        the idParametrizacionModalidad to set
     */
    public void setIdParametrizacionModalidad(ModalidadEnum idParametrizacionModalidad) {
        this.idParametrizacionModalidad = idParametrizacionModalidad;
    }

}
