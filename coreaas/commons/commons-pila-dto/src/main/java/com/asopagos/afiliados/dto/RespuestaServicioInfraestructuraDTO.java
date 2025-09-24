package com.asopagos.afiliados.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que se emplea para entregar la respuesta de los servicios encargados de la creación e inactivación de Sitios de
 * Pago, Infraestructuras y Tipos de Infraestructura<br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class RespuestaServicioInfraestructuraDTO implements Serializable {
    private static final long serialVersionUID = -3561587424275157338L;

    /** Mensaje de respuesta del servicio */
    private String mensajeRespuesta;

    /**
     * @return the mensajeRespuesta
     */
    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    /**
     * @param mensajeRespuesta the mensajeRespuesta to set
     */
    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }
}
