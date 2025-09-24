package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/crearActualizarListaSolicitudAnalisisNovedadFOVIS
 */
public class CrearActualizarListaSolicitudAnalisisNovedadFOVIS extends ServiceClient { 
    	private List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolicitudes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudAnalisisNovedadFOVISModeloDTO> result;
  
 	public CrearActualizarListaSolicitudAnalisisNovedadFOVIS (List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolicitudes){
 		super();
		this.listSolicitudes=listSolicitudes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listSolicitudes == null ? null : Entity.json(listSolicitudes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitudAnalisisNovedadFOVISModeloDTO>) response.readEntity(new GenericType<List<SolicitudAnalisisNovedadFOVISModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SolicitudAnalisisNovedadFOVISModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setListSolicitudes (List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolicitudes){
 		this.listSolicitudes=listSolicitudes;
 	}
 	
 	public List<SolicitudAnalisisNovedadFOVISModeloDTO> getListSolicitudes (){
 		return listSolicitudes;
 	}
  
}