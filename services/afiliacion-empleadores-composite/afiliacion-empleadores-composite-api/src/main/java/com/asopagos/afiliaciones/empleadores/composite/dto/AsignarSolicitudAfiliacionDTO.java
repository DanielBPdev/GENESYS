package com.asopagos.afiliaciones.empleadores.composite.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;

/**
 * <b>Descripción:</b> DTO para el servicio de terminar tarea de la HU
 * <b>Historia de Usuario:</b> HU-332
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class AsignarSolicitudAfiliacionDTO implements Serializable {
    
    
    @NotNull
    private Long idTarea;
    
    @NotNull 
    private SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador;
    
    private Long idSedeCajaCompensacion;

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
     * @return the solicitudAfiliacionEmpleador
     */
    public SolicitudAfiliacionEmpleador getSolicitudAfiliacionEmpleador() {
        return solicitudAfiliacionEmpleador;
    }

    /**
     * @param solicitudAfiliacionEmpleador the solicitudAfiliacionEmpleador to set
     */
    public void setSolicitudAfiliacionEmpleador(SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador) {
        this.solicitudAfiliacionEmpleador = solicitudAfiliacionEmpleador;
    }

	/**
	 * @return the idSedeCajaCompensacion
	 */
	public Long getIdSedeCajaCompensacion() {
		return idSedeCajaCompensacion;
	}

	/**
	 * @param idSedeCajaCompensacion the idSedeCajaCompensacion to set
	 */
	public void setIdSedeCajaCompensacion(Long idSedeCajaCompensacion) {
		this.idSedeCajaCompensacion = idSedeCajaCompensacion;
	}
    
}
