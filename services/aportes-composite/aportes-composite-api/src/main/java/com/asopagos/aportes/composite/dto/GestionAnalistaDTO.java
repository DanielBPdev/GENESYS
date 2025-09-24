package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase DTO con los datos manejados temporalmente.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class GestionAnalistaDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Atributo que contiene el id de la solicitud que se esta gestionando.
     */
    @NotNull
    private Long idSolicitud;
    /**
     * Id de la tarea para cambiar de usuario.
     */
    @NotNull
    private Long idTarea;
    
    /**
     * Usuario analista contable al que se le asignará la tarea.
     */
    @NotNull
    private String usuarioAnalistaContable;
    /**
     * Método que retorna el valor de usuarioAnalistaContable.
     * @return valor de usuarioAnalistaContable.
     */
    public String getUsuarioAnalistaContable() {
        return usuarioAnalistaContable;
    }
    /**
     * Método encargado de modificar el valor de usuarioAnalistaContable.
     * @param valor para modificar usuarioAnalistaContable.
     */
    public void setUsuarioAnalistaContable(String usuarioAnalistaContable) {
        this.usuarioAnalistaContable = usuarioAnalistaContable;
    }
    /**
     * Método que retorna el valor de idSolicitud.
     * @return valor de idSolicitud.
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }
    /**
     * Método encargado de modificar el valor de idSolicitud.
     * @param valor para modificar idSolicitud.
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    /**
     * Método que retorna el valor de idTarea.
     * @return valor de idTarea.
     */
    public Long getIdTarea() {
        return idTarea;
    }
    /**
     * Método encargado de modificar el valor de idTarea.
     * @param valor para modificar idTarea.
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }
 
}
