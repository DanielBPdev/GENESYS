package com.asopagos.solicitud.composite.factory;

import com.asopagos.solicitud.composite.dto.CambiarEstadoSolicitudFinGestionDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public abstract class Solicitud {

    /**
     * Actualiza el estado de la solicitud indentificada por el número de radicado dado
     * 
     * @param inDTO
     */
    public abstract void actualizarEstadoSolicitud(CambiarEstadoSolicitudFinGestionDTO inDTO);
}
