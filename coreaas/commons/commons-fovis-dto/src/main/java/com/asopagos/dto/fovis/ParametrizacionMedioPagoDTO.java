package com.asopagos.dto.fovis;

import com.asopagos.entidades.ccf.personas.MediosPagoCCF;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que hace referencia a la parametrización de los medios de pago para FOVIS<br/>
 * <b>Módulo:</b> Asopagos - HU-321-022 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class ParametrizacionMedioPagoDTO {

    /**
     * Identificador de la tabla de medios de pago CCF
     */
    private TipoMedioDePagoEnum medioPago;

    /**
     * Indica si el medio de pago está habilitado o no para FOVIS
     */
    private Boolean habilitado;

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase.
     */
    public ParametrizacionMedioPagoDTO(MediosPagoCCF mediosPagoCCF) {
        this.medioPago = mediosPagoCCF.getMedioPago();
        this.habilitado = mediosPagoCCF.getAplicaFOVIS();
    }

   
    /**
	 * @return the medioPago
	 */
	public TipoMedioDePagoEnum getMedioPago() {
		return medioPago;
	}


	/**
	 * @param medioPago the medioPago to set
	 */
	public void setMedioPago(TipoMedioDePagoEnum medioPago) {
		this.medioPago = medioPago;
	}


	/**
     * @return the habilitado
     */
    public Boolean getHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado
     *        the habilitado to set
     */
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    public ParametrizacionMedioPagoDTO() {
		// TODO Auto-generated constructor stub
	}
}
