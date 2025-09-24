package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.fovis.parametricas.CicloAsignacion;
import com.asopagos.entidades.fovis.parametricas.CicloModalidad;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que se encarga de almacenar los datos de las modalidades habilitadas para el ciclo de asignación<br/>
 * <b>Módulo:</b> Asopagos - HU-312-022 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class CicloModalidadModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1523205572187041893L;
    /**
     * Identificador único, llave primaria
     */
    private Long idCicloModalidad;
    /**
     * Ciclo asignación asociado al Ciclo Modalidad
     */
    private CicloAsignacionModeloDTO cicloAsignacion;
    /**
     * Modalidad asociada al Ciclo Modalidad
     */
    private ModalidadEnum idParametrizacionModalidad;

    /**
     * Constructor de CicloModalidadModelDTO.
     */
    public CicloModalidadModeloDTO() {
    }

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase.
     */
    public CicloModalidadModeloDTO(Long idCicloModalidad, CicloAsignacion cicloAsignacion, ModalidadEnum modalidad) {
        this.idCicloModalidad = idCicloModalidad;
        this.cicloAsignacion = new CicloAsignacionModeloDTO(cicloAsignacion);
        this.idParametrizacionModalidad = modalidad;
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return Entity CicloModalidad
     */
    public CicloModalidad convertToEntity() {
        CicloModalidad cicloModalidad = new CicloModalidad();
        cicloModalidad.setIdCicloModalidad(this.getIdCicloModalidad());
        if (this.getCicloAsignacion() != null && this.getCicloAsignacion().getIdCicloAsignacion() != null) {
            cicloModalidad.setIdCicloAsignacion(this.getCicloAsignacion().getIdCicloAsignacion());
        }
        cicloModalidad.setModalidad(this.getIdParametrizacionModalidad());
        return cicloModalidad;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro.
     * @param cicloModalidad
     * @return El CicloModalidad con las propiedades modificadas.
     */
    public CicloModalidad copyDTOToEntity(CicloModalidad cicloModalidad) {
        if (this.getIdCicloModalidad() != null) {
            cicloModalidad.setIdCicloModalidad(this.getIdCicloModalidad());
        }
        if (this.getCicloAsignacion() != null && this.getCicloAsignacion().getIdCicloAsignacion() != null) {
            cicloModalidad.setIdCicloAsignacion(this.getCicloAsignacion().getIdCicloAsignacion());
        }
        if (this.getIdParametrizacionModalidad() != null) {
            cicloModalidad.setModalidad(this.getIdParametrizacionModalidad());
        }
        return cicloModalidad;
    }

    /**
     * @return the idCicloModalidad
     */
    public Long getIdCicloModalidad() {
        return idCicloModalidad;
    }

    /**
     * @param idCicloModalidad
     *        the idCicloModalidad to set
     */
    public void setIdCicloModalidad(Long idCicloModalidad) {
        this.idCicloModalidad = idCicloModalidad;
    }

    /**
     * @return the cicloAsignacion
     */
    public CicloAsignacionModeloDTO getCicloAsignacion() {
        return cicloAsignacion;
    }

    /**
     * @param cicloAsignacion
     *        the cicloAsignacion to set
     */
    public void setCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacion) {
        this.cicloAsignacion = cicloAsignacion;
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
