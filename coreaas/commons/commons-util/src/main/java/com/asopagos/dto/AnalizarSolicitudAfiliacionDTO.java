package com.asopagos.dto;

import java.io.Serializable;

/**
 * <b>Descripción:</b> DTO para el servicio de terminar tarea de la HU
 * <b>Historia de Usuario:</b> HU-070
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class AnalizarSolicitudAfiliacionDTO implements Serializable {
    
    /**
     * Atributo de serialización
     */
    private static final long serialVersionUID = 1L;

    private Long idTarea;
    
    private EscalamientoSolicitudDTO registroResultado;
    
    /**
     * Atributo que representa el id de la instancia del proceso
     */
    private Long idInstanciaProceso;
    
    /**
     * Atributo que representa el id de la solicitud de afiliación del empleador
     */
    private Long idSolicitudAfiliacionEmpleador;
    
    public AnalizarSolicitudAfiliacionDTO() {
        
    }

    /**
     * @return the registroResultado
     */
    public EscalamientoSolicitudDTO getRegistroResultado() {
        return registroResultado;
    }

    /**
     * @param registroResultado the registroResultado to set
     */
    public void setRegistroResultado(EscalamientoSolicitudDTO registroResultado) {
        this.registroResultado = registroResultado;
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
     * @return the idSolicitudAfiliacionEmpleador
     */
    public Long getIdSolicitudAfiliacionEmpleador() {
        return idSolicitudAfiliacionEmpleador;
    }

    /**
     * @param idSolicitudAfiliacionEmpleador the idSolicitudAfiliacionEmpleador to set
     */
    public void setIdSolicitudAfiliacionEmpleador(Long idSolicitudAfiliacionEmpleador) {
        this.idSolicitudAfiliacionEmpleador = idSolicitudAfiliacionEmpleador;
    }
}
