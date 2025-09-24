package com.asopagos.dto;

import java.io.Serializable;
import java.util.Map;
import com.asopagos.enumeraciones.comunicados.PlantillaProcesoEnum;

/**
 * <b>Descripción:</b> Clase DTO que representa una plantilla del JSON del dato temporal del comunicado
 * 
 * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
 *
 */
public class PlantillaComunicadoDTO implements Serializable {
    
    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = 1349426997475243916L;

    /**
     * Atributo que representa la plantilla de un comunicado
     */
    private PlantillaProcesoEnum plantilla;
    
    /**
     * Atributo que representa una url de redirección que se utiliza después de enviar el comunicado
     */
    private String urlRedireccion;
    
    /**
     * Atributo que representa si se debe avanzar a otra tarea después de enviar el comunicado
     */
    private Boolean avanzarTarea;
    
    /**
     * Atributo que representa la información adicional de la plantilla
     */
    private  Map<String, Object> informacionAdicional;
    
    /**
     * Constructor
     */
    public PlantillaComunicadoDTO() {
        
    }
    
    /**
     * Constructor 
     * 
     * @param plantilla
     *        Plantilla de un comunicado
     * @param urlRedireccion
     *        url de redirección que se utiliza después de enviar el comunicado
     * @param avanzarTarea
     *        true si se debe avanzar a otra tarea después de enviar el comunicado de lo contrario false
     * @param informacionAdicional
     *        Información adicional de la plantilla
     */
    public PlantillaComunicadoDTO(PlantillaProcesoEnum plantilla, String urlRedireccion, Boolean avanzarTarea,
            Map<String, Object> informacionAdicional) {
        super();
        this.plantilla = plantilla;
        this.urlRedireccion = urlRedireccion;
        this.avanzarTarea = avanzarTarea;
        this.informacionAdicional = informacionAdicional;
    }

    /**
     * @return the plantilla
     */
    public PlantillaProcesoEnum getPlantilla() {
        return plantilla;
    }

    /**
     * @param plantilla the plantilla to set
     */
    public void setPlantilla(PlantillaProcesoEnum plantilla) {
        this.plantilla = plantilla;
    }

    /**
     * @return the urlRedireccion
     */
    public String getUrlRedireccion() {
        return urlRedireccion;
    }

    /**
     * @param urlRedireccion the urlRedireccion to set
     */
    public void setUrlRedireccion(String urlRedireccion) {
        this.urlRedireccion = urlRedireccion;
    }

    /**
     * @return the avanzarTarea
     */
    public Boolean getAvanzarTarea() {
        return avanzarTarea;
    }

    /**
     * @param avanzarTarea the avanzarTarea to set
     */
    public void setAvanzarTarea(Boolean avanzarTarea) {
        this.avanzarTarea = avanzarTarea;
    }

    /**
     * @return the informacionAdicional
     */
    public Map<String, Object> getInformacionAdicional() {
        return informacionAdicional;
    }

    /**
     * @param informacionAdicional the informacionAdicional to set
     */
    public void setInformacionAdicional(Map<String, Object> informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }  
}
