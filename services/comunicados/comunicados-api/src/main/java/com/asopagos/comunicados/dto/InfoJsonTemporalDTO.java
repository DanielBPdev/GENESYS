package com.asopagos.comunicados.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;

public class InfoJsonTemporalDTO implements Serializable{
    
    
    private static final long serialVersionUID = 1L;
    
    private Long idSolicitud;
    
    private String idInstanciaProceso;
    
    private Long idTarea;
    
    private String url;
    
    private String numeroRadicado;
    
    private ResultadoProcesoEnum resultadoProceso;
    
    private ClasificacionEnum clasificacion;
    
    public InfoJsonTemporalDTO() {
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

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the resultadoProceso
     */
    public ResultadoProcesoEnum getResultadoProceso() {
        return resultadoProceso;
    }

    /**
     * @param resultadoProceso the resultadoProceso to set
     */
    public void setResultadoProceso(ResultadoProcesoEnum resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }

    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }
    
}
