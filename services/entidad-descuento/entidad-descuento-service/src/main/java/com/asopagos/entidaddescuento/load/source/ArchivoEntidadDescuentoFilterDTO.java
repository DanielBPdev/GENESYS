package com.asopagos.entidaddescuento.load.source;

import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;

/**
 * <b>Descripcion:</b> Clase que se encarga de definir los atributos utilizados como filtros en la generación de archivos de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU 432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ArchivoEntidadDescuentoFilterDTO extends QueryFilterInDTO {

    private static final long serialVersionUID = 1L;

    /**
     * Atributo que representa el identificador del número Radicación.
     */
    private String numeroRadicacion;
    
    private Long idEntidadDescuento;

	/**
	 * @return the numeroRadicacion
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	/**
	 * @param numeroRadicacion the numeroRadicacion to set
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	/**
	 * @return the idEntidadDescuento
	 */
	public Long getIdEntidadDescuento() {
		return idEntidadDescuento;
	}

	/**
	 * @param idEntidadDescuento the idEntidadDescuento to set
	 */
	public void setIdEntidadDescuento(Long idEntidadDescuento) {
		this.idEntidadDescuento = idEntidadDescuento;
	}

}
