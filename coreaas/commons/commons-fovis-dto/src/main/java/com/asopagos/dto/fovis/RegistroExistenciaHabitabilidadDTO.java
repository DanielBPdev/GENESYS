package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.CondicionVisitaModeloDTO;
import com.asopagos.dto.modelo.VisitaModeloDTO;

/**
 * Clase que define los atributos necesarios para Registrar el concepto de existencia y habitabilidad.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class RegistroExistenciaHabitabilidadDTO implements Serializable {

	/**
	 * Variable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**Datos de la visita*/
	private VisitaModeloDTO visita;
	/**Datos detalle de la visita*/
	private List<CondicionVisitaModeloDTO> condicionesVisitaDTO;
	/**Identificador de la solicitud global*/
	private Long idSolicitudGlobal;
	/**Identificador de la tarea*/
    private Long idTarea;
	/**Identifica si se debe terminar la tarea cuando la esta realizando el Inspector FOVIS*/
	private Boolean terminarTarea;

    /**
     * @return the visita
     */
    public VisitaModeloDTO getVisita() {
        return visita;
    }

    /**
     * @param visita the visita to set
     */
    public void setVisita(VisitaModeloDTO visita) {
        this.visita = visita;
    }

    /**
     * @return the condicionesVisitaDTO
     */
    public List<CondicionVisitaModeloDTO> getCondicionesVisitaDTO() {
        return condicionesVisitaDTO;
    }

    /**
     * @param condicionesVisitaDTO the condicionesVisitaDTO to set
     */
    public void setCondicionesVisitaDTO(List<CondicionVisitaModeloDTO> condicionesVisitaDTO) {
        this.condicionesVisitaDTO = condicionesVisitaDTO;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the terminarTarea
     */
    public Boolean getTerminarTarea() {
        return terminarTarea;
    }

    /**
     * @param terminarTarea the terminarTarea to set
     */
    public void setTerminarTarea(Boolean terminarTarea) {
        this.terminarTarea = terminarTarea;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }
	
}
