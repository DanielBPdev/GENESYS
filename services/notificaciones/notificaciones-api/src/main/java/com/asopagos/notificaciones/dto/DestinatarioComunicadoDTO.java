package com.asopagos.notificaciones.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class DestinatarioComunicadoDTO implements Serializable{

    /**
     * Id que representa el DTO Destinario Comunicado 
     */
    private Long idDestinatarioComunicado;

    /**
     * Id que identifica el proceso asociado al Destinatario comunicado
     */ 
    private String proceso;
    
    /**
     * Id que identifica la etiqueta de la plantilla asociada al Destinatario comunicado
     */ 
    private EtiquetaPlantillaComunicadoEnum etiqueta;

    /**
     * @return the idDestinatarioComunicado
     */
    public Long getIdDestinatarioComunicado() {
        return idDestinatarioComunicado;
    }

    /**
     * @param idDestinatarioComunicado the idDestinatarioComunicado to set
     */
    public void setIdDestinatarioComunicado(Long idDestinatarioComunicado) {
        this.idDestinatarioComunicado = idDestinatarioComunicado;
    }

    /**
     * @return the proceso
     */
    public String getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the etiqueta
     */
    public EtiquetaPlantillaComunicadoEnum getEtiqueta() {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(EtiquetaPlantillaComunicadoEnum etiqueta) {
        this.etiqueta = etiqueta;
    }
}