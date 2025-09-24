package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contiene los datos para la consulta de solicitudes de corrección 
 * por listado de IDs de aporte general<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosConsultaSolicitudesAporDevCorDTO implements Serializable {
    private static final long serialVersionUID = 5914500887348242975L;
    
    /** Mapa de solicitudes de aportes */
    private Map<Long, SolicitudAporteModeloDTO> solicitudesAporte;
    
    /** Mapa de solicitudes de devolución de aportes */
    private Map<Long, Map<Long, SolicitudDevolucionAporteModeloDTO>> solicitudesDevolucion;
    
    /** Mapa de solicitudes de corrección de aportes */
    private Map<Long, List<SolicitudCorreccionAporteModeloDTO>> solicitudesCorreccion;

    /**
     * @return the solicitudesCorreccion
     */
    public Map<Long, List<SolicitudCorreccionAporteModeloDTO>> getSolicitudesCorreccion() {
        return solicitudesCorreccion;
    }

    /**
     * @param solicitudesCorreccion the solicitudesCorreccion to set
     */
    public void setSolicitudesCorreccion(Map<Long, List<SolicitudCorreccionAporteModeloDTO>> solicitudesCorreccion) {
        this.solicitudesCorreccion = solicitudesCorreccion;
    }

    /**
     * @return the solicitudesAporte
     */
    public Map<Long, SolicitudAporteModeloDTO> getSolicitudesAporte() {
        return solicitudesAporte;
    }

    /**
     * @param solicitudesAporte the solicitudesAporte to set
     */
    public void setSolicitudesAporte(Map<Long, SolicitudAporteModeloDTO> solicitudesAporte) {
        this.solicitudesAporte = solicitudesAporte;
    }

    /**
     * @return the solicitudesDevolucion
     */
    public Map<Long, Map<Long, SolicitudDevolucionAporteModeloDTO>> getSolicitudesDevolucion() {
        return solicitudesDevolucion;
    }

    /**
     * @param solicitudesDevolucion the solicitudesDevolucion to set
     */
    public void setSolicitudesDevolucion(Map<Long, Map<Long, SolicitudDevolucionAporteModeloDTO>> solicitudesDevolucion) {
        this.solicitudesDevolucion = solicitudesDevolucion;
    }
}
