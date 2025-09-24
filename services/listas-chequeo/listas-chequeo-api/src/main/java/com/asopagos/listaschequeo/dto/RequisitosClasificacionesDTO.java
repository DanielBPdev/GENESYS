package com.asopagos.listaschequeo.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.ElementoListaDTO;

/**
 * <b>Descripción:</b> DTO para la consulta de requisitos Vs clasificaciones
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class RequisitosClasificacionesDTO implements Serializable {


	/**
	 * Lista de requisitos
	 */
    private List<RequisitoDTO> requsitos;
   
	/**
	 * Lista de cajas de compensacion
	 */
    private List<ElementoListaDTO> clasificaciones;
    
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

	/**
	 * @return the clasificaciones
	 */
	public List<ElementoListaDTO> getClasificaciones() {
		return clasificaciones;
	}

	/**
	 * @param clasificaciones the clasificaciones to set
	 */
	public void setClasificaciones(List<ElementoListaDTO> clasificaciones) {
		this.clasificaciones = clasificaciones;
	}
    
}
