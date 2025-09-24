package com.asopagos.afiliaciones.empleadores.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.afiliaciones.empleadores.composite.enu.GestionarProductoNoConformeSubsanableEnum;

/**
 * <b>Descripción:</b> DTO para el servicio de terminar tarea de la HU
 * <b>Historia de Usuario:</b> HU-092
 * 
 * @author Andrey G. López <alopez@heinsohn.com.co>
 */
@XmlRootElement
public class GestionarProductoNoConformeSubsanableDTO implements Serializable  {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long idTarea;
    
    private Long idSolicitud;
    
    private GestionarProductoNoConformeSubsanableEnum resultado;

    
    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public GestionarProductoNoConformeSubsanableEnum getResultado() {
        return resultado;
    }

    public void setResultado(GestionarProductoNoConformeSubsanableEnum resultado) {
        this.resultado = resultado;
    }

}