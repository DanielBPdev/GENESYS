package com.asopagos.solicitud.composite.dto;

import java.util.List;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos a mostrar de las distintas solicitudes asociadas a un usuario.<br/>
 * <b>Módulo:</b> Asopagos - HU3.2.2-042 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class DatosSeguimientoSolicitudesDTO {

    /**
     * Lista de solicitudes de afiliacion de Personas
     */
    private List<SolicitudAfiliacionPersonaDTO> solicitudesAfiliacionPersonas;
    /**
     * Lista de solicitudes de novedad
     */
    private List<SolicitudNovedadModeloDTO> solicitudesNovedad;
    /**
     * Lista de solicitudes de postulación
     */
    private List<SolicitudPostulacionModeloDTO> solicitudesPostulacion;

    /**
     * @return the solicitudesAfiliacionPersonas
     */
    public List<SolicitudAfiliacionPersonaDTO> getSolicitudesAfiliacionPersonas() {
        return solicitudesAfiliacionPersonas;
    }

    /**
     * @param solicitudesAfiliacionPersonas
     *        the solicitudesAfiliacionPersonas to set
     */
    public void setSolicitudesAfiliacionPersonas(List<SolicitudAfiliacionPersonaDTO> solicitudesAfiliacionPersonas) {
        this.solicitudesAfiliacionPersonas = solicitudesAfiliacionPersonas;
    }

    /**
     * @return the solicitudesNovedad
     */
    public List<SolicitudNovedadModeloDTO> getSolicitudesNovedad() {
        return solicitudesNovedad;
    }

    /**
     * @param solicitudesNovedad
     *        the solicitudesNovedad to set
     */
    public void setSolicitudesNovedad(List<SolicitudNovedadModeloDTO> solicitudesNovedad) {
        this.solicitudesNovedad = solicitudesNovedad;
    }

    /**
     * @return the solicitudesPostulacion
     */
    public List<SolicitudPostulacionModeloDTO> getSolicitudesPostulacion() {
        return solicitudesPostulacion;
    }

    /**
     * @param solicitudesPostulacion
     *        the solicitudesPostulacion to set
     */
    public void setSolicitudesPostulacion(List<SolicitudPostulacionModeloDTO> solicitudesPostulacion) {
        this.solicitudesPostulacion = solicitudesPostulacion;
    }

}
