package com.asopagos.entidaddescuento.clients;

import com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadDescuento/archivoDescuentos/crearRegistroArchivoSalida
 */
public class CrearRegistroArchivoSalidaDescuento extends ServiceClient { 
    	private ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo;
  
  
 	public CrearRegistroArchivoSalidaDescuento (ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo){
 		super();
		this.infoArchivo=infoArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(infoArchivo == null ? null : Entity.json(infoArchivo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInfoArchivo (ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo){
 		this.infoArchivo=infoArchivo;
 	}
 	
 	public ArchivoSalidaDescuentoSubsidioModeloDTO getInfoArchivo (){
 		return infoArchivo;
 	}
  
}