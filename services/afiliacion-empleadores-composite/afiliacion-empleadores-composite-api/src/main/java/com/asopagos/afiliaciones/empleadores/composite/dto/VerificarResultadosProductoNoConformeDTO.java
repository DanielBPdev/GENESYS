package com.asopagos.afiliaciones.empleadores.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;

/**
 * <b>Descripción:</b> DTO para el servicio de terminar tarea de la HU
 * <b>Historia de Usuario:</b> HU-093
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class VerificarResultadosProductoNoConformeDTO implements Serializable {
    
    
    private List<ItemChequeoDTO> listaChequeo;
    
    private SolicitudAfiliacionEmpleador solicitudAfiliacion;
    
    private Integer resultadoVerificacion;
    
    /**
     * Atributo que representa el id de la instancia del proceso
     */
    private Long idInstanciaProceso;
    
    /**
     * @return the listaChequeo
     */
    public List<ItemChequeoDTO> getListaChequeo() {
        return listaChequeo;
    }

    /**
     * @param listaChequeo the listaChequeo to set
     */
    public void setListaChequeo(List<ItemChequeoDTO> listaChequeo) {
        this.listaChequeo = listaChequeo;
    }

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
     * @return the resultadoVerificacion
     */
    public Integer getResultadoVerificacion() {
        return resultadoVerificacion;
    }

    /**
     * @param resultadoVerificacion the resultadoVerificacion to set
     */
    public void setResultadoVerificacion(Integer resultadoVerificacion) {
        this.resultadoVerificacion = resultadoVerificacion;
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
