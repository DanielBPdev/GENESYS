package com.asopagos.novedades.fovis.clients;

import com.asopagos.dto.modelo.DetalleNovedadFovisModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/crearActualizarDetalleNovedad
 */
public class CrearActualizarDetalleNovedad extends ServiceClient { 
    	private DetalleNovedadFovisModeloDTO detalleNovedadFovis;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleNovedadFovisModeloDTO result;
  
 	public CrearActualizarDetalleNovedad (DetalleNovedadFovisModeloDTO detalleNovedadFovis){
 		super();
		this.detalleNovedadFovis=detalleNovedadFovis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(detalleNovedadFovis == null ? null : Entity.json(detalleNovedadFovis));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DetalleNovedadFovisModeloDTO) response.readEntity(DetalleNovedadFovisModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DetalleNovedadFovisModeloDTO getResult() {
		return result;
	}

 
  
  	public void setDetalleNovedadFovis (DetalleNovedadFovisModeloDTO detalleNovedadFovis){
 		this.detalleNovedadFovis=detalleNovedadFovis;
 	}
 	
 	public DetalleNovedadFovisModeloDTO getDetalleNovedadFovis (){
 		return detalleNovedadFovis;
 	}
  
}