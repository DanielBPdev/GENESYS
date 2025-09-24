package com.asopagos.notificaciones.dto;

import java.io.Serializable;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.transversal.notificaciones.NotificacionEnviada;

public class ComunicadoPersistenciaDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private NotificacionParametrizadaDTO notificacion;
    
    private NotificacionEnviada notEnv;
    
    private PlantillaComunicado plantilla;
    
    public ComunicadoPersistenciaDTO() {
    }
    
    public ComunicadoPersistenciaDTO(NotificacionParametrizadaDTO notificacion, NotificacionEnviada notEnv, PlantillaComunicado plantilla) {
        this.notificacion = notificacion;
        this.notEnv = notEnv;
        this.plantilla = plantilla;
    }



    /**
     * @return the notificacion
     */
    public NotificacionParametrizadaDTO getNotificacion() {
        return notificacion;
    }

    /**
     * @param notificacion the notificacion to set
     */
    public void setNotificacion(NotificacionParametrizadaDTO notificacion) {
        this.notificacion = notificacion;
    }

    /**
     * @return the notEnv
     */
    public NotificacionEnviada getNotEnv() {
        return notEnv;
    }

    /**
     * @param notEnv the notEnv to set
     */
    public void setNotEnv(NotificacionEnviada notEnv) {
        this.notEnv = notEnv;
    }

    /**
     * @return the plantilla
     */
    public PlantillaComunicado getPlantilla() {
        return plantilla;
    }

    /**
     * @param plantilla the plantilla to set
     */
    public void setPlantilla(PlantillaComunicado plantilla) {
        this.plantilla = plantilla;
    }
    
}
