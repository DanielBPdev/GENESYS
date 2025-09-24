package com.asopagos.listaschequeo.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripción:</b> DTO para la consulta de requisitos Vs cajas de 
 * comepnsación
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class RequisitosCajasCompensacionDTO implements Serializable {

	/**
	 * Lista de requisitos
	 */
    private List<RequisitoDTO> requsitos;
   
	/**
	 * Lista de cajas de compensacion
	 */
    private List<CajaCompensacionDTO> cajasCompensacion;
    
	/**
	 * Lista de requisitos contra cajas de compensacion
	 */    
    private List<RequisitoCajaClasificacionDTO> requsitosCajas;

    /**
     * @return the requsitos
     */
    public List<RequisitoDTO> getRequsitos() {
        return requsitos;
    }

    /**
     * @param requsitos the requsitos to set
     */
    public void setRequsitos(List<RequisitoDTO> requsitos) {
        this.requsitos = requsitos;
    }

    /**
     * @return the cajasCompensacion
     */
    public List<CajaCompensacionDTO> getCajasCompensacion() {
        return cajasCompensacion;
    }

    /**
     * @param cajasCompensacion the cajasCompensacion to set
     */
    public void setCajasCompensacion(List<CajaCompensacionDTO> cajasCompensacion) {
        this.cajasCompensacion = cajasCompensacion;
    }

    /**
     * @return the requsitosCajas
     */
    public List<RequisitoCajaClasificacionDTO> getRequsitosCajas() {
        return requsitosCajas;
    }

    /**
     * @param requsitosCajas the requsitosCajas to set
     */
    public void setRequsitosCajas(List<RequisitoCajaClasificacionDTO> requsitosCajas) {
        this.requsitosCajas = requsitosCajas;
    }
    
}
