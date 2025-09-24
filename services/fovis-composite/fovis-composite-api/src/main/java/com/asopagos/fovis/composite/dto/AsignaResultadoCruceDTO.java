package com.asopagos.fovis.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.CruceDetalleDTO;

/**
 * 
 * <b>Descripcion:</b> Clase que define los datos de entrada el resultado para aceptar un cruce.<br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1-033 <br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa Salamanca</a>
 */
@XmlRootElement
public class AsignaResultadoCruceDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2490124364455926859L;
    /**
     * Identificador cargue aceptar
     */
    private Long idCargue;
    /**
     * Lista Usuarios asignar cruce
     */
    private List<String> listUsuarios;
    /**
     * Lista de cruces aceptados
     */
    private List<CruceDetalleDTO> listCruces;

    /**
     * Identificador de la tarea
     */
    private Long idTarea;
    
    /**
     * Identificador proceso asincrono
     */
    private Long idProcesoAsincrono;

    /**
     * @return the idCargue
     */
    public Long getIdCargue() {
        return idCargue;
    }

    /**
     * @param idCargue
     *        the idCargue to set
     */
    public void setIdCargue(Long idCargue) {
        this.idCargue = idCargue;
    }

    /**
     * @return the listUsuarios
     */
    public List<String> getListUsuarios() {
        return listUsuarios;
    }

    /**
     * @param listUsuarios
     *        the listUsuarios to set
     */
    public void setListUsuarios(List<String> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    /**
     * @return the listCruces
     */
    public List<CruceDetalleDTO> getListCruces() {
        return listCruces;
    }

    /**
     * @param listCruces
     *        the listCruces to set
     */
    public void setListCruces(List<CruceDetalleDTO> listCruces) {
        this.listCruces = listCruces;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea
     *        the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * @return the idProcesoAsincrono
     */
    public Long getIdProcesoAsincrono() {
        return idProcesoAsincrono;
    }

    /**
     * @param idProcesoAsincrono the idProcesoAsincrono to set
     */
    public void setIdProcesoAsincrono(Long idProcesoAsincrono) {
        this.idProcesoAsincrono = idProcesoAsincrono;
    }

}
