package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.fovis.parametricas.FormaPagoModalidad;
import com.asopagos.enumeraciones.fovis.FormaPagoEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que se encarga de almacenar los datos de el Modelo de la entidad FormaPagoModalidad<br/>
 * <b>Módulo:</b> Asopagos - HU-312-022 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class FormaPagoModalidadModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2270161408348601980L;
    /**
     * Identificador único, llave primaria
     */
    private Long idFormaPagoModalidad;
    /**
     * Nombre de la modalidad.
     */
    private ModalidadEnum idParametrizacionModalidad;
    /**
     * Enum que establece el nombre de la forma de pago.
     */
    private FormaPagoEnum formaPago;

    /**
     * Constructor de FormaPagoModalidadModelDTO.
     */
    public FormaPagoModalidadModeloDTO() {
    }

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase.
     */
    public FormaPagoModalidadModeloDTO(FormaPagoModalidad formaPagoModalidad) {
        this.idFormaPagoModalidad = formaPagoModalidad.getIdFormaPagoModalidad();
        this.idParametrizacionModalidad = formaPagoModalidad.getModalidad();
        this.formaPago = formaPagoModalidad.getFormaPago();
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return Entity FormaPagoModalidad
     */
    public FormaPagoModalidad convertToEntity() {
        FormaPagoModalidad formaPagoModalidad = new FormaPagoModalidad();
        formaPagoModalidad.setIdFormaPagoModalidad(this.getIdFormaPagoModalidad());
        formaPagoModalidad.setModalidad(this.getIdParametrizacionModalidad());
        formaPagoModalidad.setFormaPago(this.getFormaPago());
        return formaPagoModalidad;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro.
     * @param formaPagoModalidad
     * @return La FormaPagoModalidad con las propiedades modificadas.
     */
    public FormaPagoModalidad copyDTOToEntity(FormaPagoModalidad formaPagoModalidad) {
        if (this.getIdFormaPagoModalidad() != null) {
            formaPagoModalidad.setIdFormaPagoModalidad(this.getIdFormaPagoModalidad());
        }
        if (this.getIdParametrizacionModalidad() != null) {
            formaPagoModalidad.setModalidad(this.getIdParametrizacionModalidad());
        }
        if (this.getFormaPago() != null) {
            formaPagoModalidad.setFormaPago(this.getFormaPago());
        }
        return formaPagoModalidad;
    }

    /**
     * @return the idFormaPagoModalidad
     */
    public Long getIdFormaPagoModalidad() {
        return idFormaPagoModalidad;
    }

    /**
     * @param idFormaPagoModalidad
     *        the idFormaPagoModalidad to set
     */
    public void setIdFormaPagoModalidad(Long idFormaPagoModalidad) {
        this.idFormaPagoModalidad = idFormaPagoModalidad;
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

    /**
     * @return the formaPago
     */
    public FormaPagoEnum getFormaPago() {
        return formaPago;
    }

    /**
     * @param formaPago
     *        the formaPago to set
     */
    public void setFormaPago(FormaPagoEnum formaPago) {
        this.formaPago = formaPago;
    }
}
