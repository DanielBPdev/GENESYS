package com.asopagos.afiliaciones.empleadores.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;

/**
 * <b>Descripción:</b> DTO para el servicio de terminar tarea de la HU
 * <b>Historia de Usuario:</b> HU-091
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class VerificarInformacionSolicitudDTO implements Serializable {
    
    
    private Long idTarea;
    
    private SolicitudAfiliacionEmpleador solicitudAfiliacion;
    
    private ResultadoGeneralProductoNoConformeEnum resultadoGeneral;
    
    /**
     * Atributo que representa el id de la instancia del proceso
     */
    private Long idInstanciaProceso;

    /**
     * @return the solicitudAfiliacion
     */
    public SolicitudAfiliacionEmpleador getSolicitudAfiliacion() {
        return solicitudAfiliacion;
    }

    /**
     * @param solicitudAfiliacion the solicitudAfiliacion to set
     */
    public void setSolicitudAfiliacion(SolicitudAfiliacionEmpleador solicitudAfiliacion) {
        this.solicitudAfiliacion = solicitudAfiliacion;
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
     * @return the resultadoGeneral
     */
    public ResultadoGeneralProductoNoConformeEnum getResultadoGeneral() {
        return resultadoGeneral;
    }

    /**
     * @param resultadoGeneral the resultadoGeneral to set
     */
    public void setResultadoGeneral(ResultadoGeneralProductoNoConformeEnum resultadoGeneral) {
        this.resultadoGeneral = resultadoGeneral;
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
}
