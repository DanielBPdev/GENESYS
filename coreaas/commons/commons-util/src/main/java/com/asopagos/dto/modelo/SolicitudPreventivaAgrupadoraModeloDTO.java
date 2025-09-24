package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.SolicitudPreventiva;
import com.asopagos.entidades.ccf.cartera.SolicitudPreventivaAgrupadora;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum;

/**
 * <b>Descripcion:</b> Clase que agrupa las solicitudes de gestion preventiva<br/>
 * <b>Módulo:</b> Asopagos - HU 160<br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class SolicitudPreventivaAgrupadoraModeloDTO extends SolicitudModeloDTO implements Serializable{

    /**
     * Identificador único de una solicitud preventiva agrupadora.
     */
    private Long idSolicitudPreventivaAgrupadora;
    
    /**
     * Estado de la solicitud preventiva agrupadora.
     */
    private EstadoSolicitudPreventivaEnum estadoSolicitudPreventivaAgrupadora; 
    
    /**
     * Constructor de la clase
     */
    public SolicitudPreventivaAgrupadoraModeloDTO() {
    }

    /**
     * Método encargado de convertir de DTO a entidad.
     * @return SolicitudPreventiva convertida.
     */
    public SolicitudPreventivaAgrupadora convertToEntity() {
        SolicitudPreventivaAgrupadora solicitudPreventivaAgrupadora = new SolicitudPreventivaAgrupadora();
        solicitudPreventivaAgrupadora.setIdSolicitudPreventivaAgrupadora(this.getIdSolicitudPreventivaAgrupadora());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudPreventivaAgrupadora.setSolicitudGlobal(solicitudGlobal);
        solicitudPreventivaAgrupadora.setEstadoSolicitudPreventivaAgrupadora(this.getEstadoSolicitudPreventivaAgrupadora());
        return solicitudPreventivaAgrupadora;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitudPreventivaAgrupadora
     *        entidad a convertir.
     */
    public void convertToDTO(SolicitudPreventivaAgrupadora solicitudPreventivaAgrupadora) {
        if (solicitudPreventivaAgrupadora.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudPreventivaAgrupadora.getSolicitudGlobal());
        }
        this.setIdSolicitudPreventivaAgrupadora(solicitudPreventivaAgrupadora.getIdSolicitudPreventivaAgrupadora());
        this.setEstadoSolicitudPreventivaAgrupadora(solicitudPreventivaAgrupadora.getEstadoSolicitudPreventivaAgrupadora());
    }
    
    /**
     * Método que retorna el valor de idSolicitudPreventivaAgrupadora.
     * @return valor de idSolicitudPreventivaAgrupadora.
     */
    public Long getIdSolicitudPreventivaAgrupadora() {
        return idSolicitudPreventivaAgrupadora;
    }

    /**
     * Método que retorna el valor de estadoSolicitudPreventivaAgrupadora.
     * @return valor de estadoSolicitudPreventivaAgrupadora.
     */
    public EstadoSolicitudPreventivaEnum getEstadoSolicitudPreventivaAgrupadora() {
        return estadoSolicitudPreventivaAgrupadora;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudPreventivaAgrupadora.
     * @param valor para modificar idSolicitudPreventivaAgrupadora.
     */
    public void setIdSolicitudPreventivaAgrupadora(Long idSolicitudPreventivaAgrupadora) {
        this.idSolicitudPreventivaAgrupadora = idSolicitudPreventivaAgrupadora;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitudPreventivaAgrupadora.
     * @param valor para modificar estadoSolicitudPreventivaAgrupadora.
     */
    public void setEstadoSolicitudPreventivaAgrupadora(EstadoSolicitudPreventivaEnum estadoSolicitudPreventivaAgrupadora) {
        this.estadoSolicitudPreventivaAgrupadora = estadoSolicitudPreventivaAgrupadora;
    }
    
}
