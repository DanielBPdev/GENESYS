/**
 * 
 */
package com.asopagos.pila.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> DTO que contiene la combinación de datos de un registro detallado y de sus novedades <br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConsultaNovedadesPorRegistroDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID registro detallado */
    private Long idRegistroDetallado;
    
    /** Cantidad de novedades asociadas al registro detallado */
    private Long cantidadNovedadesAsociadas;
    
    /**
     * Constructor para consulta PilaService.RegistroDetalladoNovedad.ConsultarCantidadNovedadesPorRegistroDetallado
     * */
    public ConsultaNovedadesPorRegistroDTO(Long idRegistroDetallado, Long cantidadNovedadesAsociadas){
        this.idRegistroDetallado = idRegistroDetallado;
        this.cantidadNovedadesAsociadas = cantidadNovedadesAsociadas;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the cantidadNovedadesAsociadas
     */
    public Long getCantidadNovedadesAsociadas() {
        return cantidadNovedadesAsociadas;
    }

    /**
     * @param cantidadNovedadesAsociadas the cantidadNovedadesAsociadas to set
     */
    public void setCantidadNovedadesAsociadas(Long cantidadNovedadesAsociadas) {
        this.cantidadNovedadesAsociadas = cantidadNovedadesAsociadas;
    }
    
    public ConsultaNovedadesPorRegistroDTO() {
		// TODO Auto-generated constructor stub
	}
}
