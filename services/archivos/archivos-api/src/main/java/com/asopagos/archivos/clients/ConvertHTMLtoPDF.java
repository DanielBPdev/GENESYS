package com.asopagos.archivos.clients;

import com.asopagos.archivos.dto.InformacionConvertDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivos/convertHTMLtoPDF
 */
public class ConvertHTMLtoPDF extends ServiceClient { 
    	private InformacionConvertDTO objInformacionConvertDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private byte[] result;
  
 	public ConvertHTMLtoPDF (InformacionConvertDTO objInformacionConvertDTO){
 		super();
		this.objInformacionConvertDTO=objInformacionConvertDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(objInformacionConvertDTO == null ? null : Entity.json(objInformacionConvertDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (byte[]) response.readEntity(byte[].class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public byte[] getResult() {
		return result;
	}

 
  
  	public void setObjInformacionConvertDTO (InformacionConvertDTO objInformacionConvertDTO){
 		this.objInformacionConvertDTO=objInformacionConvertDTO;
 	}
 	
 	public InformacionConvertDTO getObjInformacionConvertDTO (){
 		return objInformacionConvertDTO;
 	}
  
}