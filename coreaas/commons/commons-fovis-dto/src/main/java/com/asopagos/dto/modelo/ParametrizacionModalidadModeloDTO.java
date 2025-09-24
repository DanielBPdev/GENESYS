package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.asopagos.entidades.fovis.parametricas.ParametrizacionModalidad;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;

/**
 * Clase encargada de almacenar los datos de el Modelo de la entidad
 * ModalidadFOVIS
 * 
 * @author flopez
 *
 */
public class ParametrizacionModalidadModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -7922614586306273674L;
    /**
     * Identificador único, llave primaria
     */
    private Long idParametrizacionModalidad;
    /**
     * Nombre de la Modalidad FOVIS
     */
    private ModalidadEnum nombre;
    /**
     * Estado de la Modalidad FOVIS
     */
    private Boolean estado;
    /**
     * Tope SMLMV de la Modalidad FOVIS
     */
    private BigDecimal topeSMLMV;
    /**
     * Tope Avalúo Catastral para la modalidad FOVIS
     */
    private BigDecimal topeAvaluoCatastral;
    /**
     * Lista de rangos asociados a la modalidad
     */
    private List<RangoTopeValorSFVModeloDTO> rangosSVFPorModalidad;
    /**
     * Lista de formas de pago disponibles para la modalidad
     */
    private List<FormaPagoModalidadModeloDTO> formasDePagoModalidad;

    /**
     * Constructor de ModalidadFOVISModeloDTO.
     */
    public ParametrizacionModalidadModeloDTO() {
    }

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase.
     */
    public ParametrizacionModalidadModeloDTO(ParametrizacionModalidad modalidadFOVIS) {
        this.idParametrizacionModalidad = modalidadFOVIS.getIdParametrizacionModalidad();
        this.nombre = modalidadFOVIS.getNombre();
        this.estado = modalidadFOVIS.getEstado();
        this.topeSMLMV = modalidadFOVIS.getTopeSMLMV();
        this.topeAvaluoCatastral = modalidadFOVIS.getTopeAvaluoCatastral();
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return Entity ModalidadFOVIS
     */
    public ParametrizacionModalidad convertToEntity() {
        ParametrizacionModalidad modalidadFOVIS = new ParametrizacionModalidad();
        modalidadFOVIS.setIdParametrizacionModalidad(this.getIdParametrizacionModalidad());
        modalidadFOVIS.setEstado(this.getEstado());
        modalidadFOVIS.setNombre(this.getNombre());
        modalidadFOVIS.setTopeSMLMV(this.getTopeSMLMV());
        modalidadFOVIS.setTopeAvaluoCatastral(this.getTopeAvaluoCatastral());
        return modalidadFOVIS;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro.
     * @param modalidadFOVIS
     * @return la ModalidadFOVIS con las propiedades modificadas.
     */
    public ParametrizacionModalidad copyDTOToEntity(ParametrizacionModalidad modalidadFOVIS) {
        if (this.getIdParametrizacionModalidad() != null) {
            modalidadFOVIS.setIdParametrizacionModalidad(this.getIdParametrizacionModalidad());
        }
        if (this.getNombre() != null) {
            modalidadFOVIS.setNombre(this.getNombre());
        }
        if (this.getEstado() != null) {
            modalidadFOVIS.setEstado(this.getEstado());
        }
        if (this.getTopeSMLMV() != null) {
            modalidadFOVIS.setTopeSMLMV(this.getTopeSMLMV());
        }
        if (this.getTopeAvaluoCatastral() != null) {
            modalidadFOVIS.setTopeAvaluoCatastral(this.getTopeAvaluoCatastral());
        }
        return modalidadFOVIS;
    }

    /**
     * @return the idParametrizacionModalidad
     */
    public Long getIdParametrizacionModalidad() {
        return idParametrizacionModalidad;
    }

    /**
     * @param idParametrizacionModalidad
     *        the idParametrizacionModalidad to set
     */
    public void setIdParametrizacionModalidad(Long idParametrizacionModalidad) {
        this.idParametrizacionModalidad = idParametrizacionModalidad;
    }

    /**
     * Obtiene el valor de nombre
     * 
     * @return El valor de nombre
     */
    public ModalidadEnum getNombre() {
        return nombre;
    }

    /**
     * Establece el valor de nombre
     * 
     * @param nombre
     *        El valor de nombre por asignar
     */
    public void setNombre(ModalidadEnum nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor de estado
     * 
     * @return El valor de estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * Establece el valor de estado
     * 
     * @param estado
     *        El valor de estado por asignar
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el valor de topeSMLMV
     * 
     * @return El valor de topeSMLMV
     */
    public BigDecimal getTopeSMLMV() {
        return topeSMLMV;
    }

    /**
     * Establece el valor de topeSMLMV
     * 
     * @param topeSMLMV
     *        El valor de topeSMLMV por asignar
     */
    public void setTopeSMLMV(BigDecimal topeSMLMV) {
        this.topeSMLMV = topeSMLMV;
    }

    /**
     * Obtiene el valor de topeAvaluoCatastral
     * 
     * @return El valor de topeAvaluoCatastral
     */
    public BigDecimal getTopeAvaluoCatastral() {
        return topeAvaluoCatastral;
    }

    /**
     * Establece el valor de topeAvaluoCatastral
     * 
     * @param topeAvaluoCatastral
     *        El valor de topeAvaluoCatastral por asignar
     */
    public void setTopeAvaluoCatastral(BigDecimal topeAvaluoCatastral) {
        this.topeAvaluoCatastral = topeAvaluoCatastral;
    }

    /**
     * @return the rangosSVFPorModalidad
     */
    public List<RangoTopeValorSFVModeloDTO> getRangosSVFPorModalidad() {
        return rangosSVFPorModalidad;
    }

    /**
     * @param rangosSVFPorModalidad
     *        the rangosSVFPorModalidad to set
     */
    public void setRangosSVFPorModalidad(List<RangoTopeValorSFVModeloDTO> rangosSVFPorModalidad) {
        this.rangosSVFPorModalidad = rangosSVFPorModalidad;
    }

    /**
     * @return the formasDePagoModalidad
     */
    public List<FormaPagoModalidadModeloDTO> getFormasDePagoModalidad() {
        return formasDePagoModalidad;
    }

    /**
     * @param formasDePagoModalidad
     *        the formasDePagoModalidad to set
     */
    public void setFormasDePagoModalidad(List<FormaPagoModalidadModeloDTO> formasDePagoModalidad) {
        this.formasDePagoModalidad = formasDePagoModalidad;
    }
}
