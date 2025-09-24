package com.asopagos.novedades.dto;

import java.util.List;

/**
 * <b>Descripcion:</b> Clase que contiene los datos resumidos que se muestran en la vista 360 para las novedades de un empleador<br/>
 * <b>Módulo:</b> Asopagos - HU TRA 488<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */

public class DatosNovedadEmpleadorPaginadoDTO {

    /**
     * Tipo de transaccion de la novedad (para indicar el nombre).
     */
    private List<DatosNovedadEmpleadorDTO> datosNovedadEmpleador;
    /**
     * Atributo que contiene el total de registros para enviar en el response.
     */
    private Integer totalRecords;

    public DatosNovedadEmpleadorPaginadoDTO() {
    }

    /**
     * @return the datosNovedadEmpleador
     */
    public List<DatosNovedadEmpleadorDTO> getDatosNovedadEmpleador() {
        return datosNovedadEmpleador;
    }

    /**
     * @param datosNovedadEmpleador
     *        the datosNovedadEmpleador to set
     */
    public void setDatosNovedadEmpleador(List<DatosNovedadEmpleadorDTO> datosNovedadEmpleador) {
        this.datosNovedadEmpleador = datosNovedadEmpleador;
    }

    /**
     * @return the totalRecords
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     * @param totalRecords
     *        the totalRecords to set
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
}
