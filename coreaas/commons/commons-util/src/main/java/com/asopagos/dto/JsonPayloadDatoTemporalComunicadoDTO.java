package com.asopagos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>Descripcion:</b> Clase DTO que representa los objetos que contiene el JSON del dato temporal del comunicado
 *
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 */
public class JsonPayloadDatoTemporalComunicadoDTO implements Serializable {
  
    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = 5242093228861509644L;
    
    /**
     * Atributo que representa la Lista que contiene las plantillas de los comunicados a enviar
     */
    private List<PlantillaComunicadoDTO> plantillas;
    
    /**
     * Atributo que representa el id de la instancia del proceso que se usa en el contexto del JSON
     */
    private Long idInstanciaProceso;
    
    /**
     * Atributo que representa el id de la instancia del proceso que se usa en el contexto del JSON
     */
    private Long idSolicitud;
    
    
    /**
     * Atributo que representa el contexto del comunicado
     */
    private ContextoComunicadoDTO contexto;
    
    /**
     * Atributo que representa el id de la tarea 
     */
    private Long idTarea;
    
    
    /**
     * Atributo que representa la información compartida que se agregará a cada plantilla
     */
    private Map<String,Object> informacionCompartidaPlantillas;
    
    
    /**
     * Constructor
     */
    public JsonPayloadDatoTemporalComunicadoDTO() {
        this.plantillas = new ArrayList<>();
        this.informacionCompartidaPlantillas = new HashMap<>();
        this.contexto = new ContextoComunicadoDTO();
        this.contexto.setInformacionAdicional(new HashMap<>());
    }
    
    /**
     * Método encargado de asignar valores al JSON
     * 
     * @param idTarea
     *        Id de la tarea que va en el campo dtcIdTarea de la tabla DatoTemporalComunicado
     * @param idSolicitud
     *        Id de la solicitud que va en el campo dtcIdSolicitud de la tabla DatoTemporalComunicado
     * @param idInstanciaProceso
     *        Id de la instancia del proceso que se usa en el contexto del JSON
     * @param idSolicitudContexto
     *        Id de la solicitud que se usa en el contexto del JSON
     * @param plantillas
     */
    public void asignarValoresJson(Long idTarea, Long idSolicitud, Long idInstanciaProceso,  Long idSolicitudContexto, PlantillaComunicadoDTO... plantillas) {
        this.idTarea = idTarea;
        this.idSolicitud = idSolicitud;
        this.idInstanciaProceso = idInstanciaProceso;
        asignarValoresPlantillas(plantillas);
        asignarValoresContexto(idInstanciaProceso, idSolicitudContexto);
    }
    
    /**
     * Método utilizado para agregar plantillas 
     * 
     * @param plantillas 
     *        Plantillas a agregar
     */
    public void asignarValoresPlantillas(PlantillaComunicadoDTO... plantillas){
        for(PlantillaComunicadoDTO plantilla: plantillas) {
            this.plantillas.add(plantilla);
        }
    }
    
    /**
     * Método utilizado para agregar valores a la información adicional del contexto o con la información compartida de las plantillas
     * 
     * @param informacionAdicional 
     *        Mapa con la información adicional del contexto o con la información compartida de las plantillas
     * @param objetos
     *        Objetos a agregar al mapa
     */
    public void agregarInformacionAdicional(Map<String,Object> informacionAdicional, InformacionAdicionalDTO... objetos) {
        for(InformacionAdicionalDTO objeto : objetos) {
            informacionAdicional.put(objeto.getLlave(), objeto.getValor());
        }
    }
    
    /**
     * Método utilizado para asignar valor al contexto
     * 
     * @param idInstanciaProceso
     *        Id de la instancia del proceso que se usa en el contexto del JSON
     * @param idSolicitud
     *        Id de la solicitud que se usa en el contexto del JSON
     */
    public void asignarValoresContexto(Long idInstanciaProceso, Long idSolicitud) {
        this.contexto.setIdInstanciaProceso(idInstanciaProceso);
        this.contexto.setIdSolicitud(idSolicitud);
    } 
    
    /**
     * @return the plantillas
     */
    public List<PlantillaComunicadoDTO> getPlantillas() {
        return plantillas;
    }

    /**
     * @param plantillas the plantillas to set
     */
    public void setPlantillas(List<PlantillaComunicadoDTO> plantillas) {
        this.plantillas = plantillas;
    }

    /**
     * @return the contexto
     */
    public ContextoComunicadoDTO getContexto() {
        return contexto;
    }

    /**
     * @param contexto the contexto to set
     */
    public void setContexto(ContextoComunicadoDTO contexto) {
        this.contexto = contexto;
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

    /**
     * @return the informacionCompartidaPlantillas
     */
    public Map<String, Object> getInformacionCompartidaPlantillas() {
        return informacionCompartidaPlantillas;
    }

    /**
     * @param informacionCompartidaPlantillas the informacionCompartidaPlantillas to set
     */
    public void setInformacionCompartidaPlantillas(Map<String, Object> informacionCompartidaPlantillas) {
        this.informacionCompartidaPlantillas = informacionCompartidaPlantillas;
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    
    
}