package com.asopagos.bandejainconsistencias.clients;

import java.util.List;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.bandejainconsistencias.dto.ResultadoAprobacionCambioIdentificacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/aprobarSolicitudCambioIdentificacion
 */
public class AprobarSolicitudCambioIden extends ServiceClient { 
    	private List<SolicitudCambioNumIdentAportante> listaSolicitudes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoAprobacionCambioIdentificacionDTO result;
  
 	public AprobarSolicitudCambioIden (List<SolicitudCambioNumIdentAportante> listaSolicitudes){
 		super();
		this.listaSolicitudes=listaSolicitudes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaSolicitudes == null ? null : Entity.json(listaSolicitudes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoAprobacionCambioIdentificacionDTO) response.readEntity(ResultadoAprobacionCambioIdentificacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoAprobacionCambioIdentificacionDTO getResult() {
		return result;
	}

 
  
  	public void setListaSolicitudes (List<SolicitudCambioNumIdentAportante> listaSolicitudes){
 		this.listaSolicitudes=listaSolicitudes;
 	}
 	
 	public List<SolicitudCambioNumIdentAportante> getListaSolicitudes (){
 		return listaSolicitudes;
 	}
  
}