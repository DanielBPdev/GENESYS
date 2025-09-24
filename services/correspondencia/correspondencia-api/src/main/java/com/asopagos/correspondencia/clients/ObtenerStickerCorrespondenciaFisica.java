package com.asopagos.correspondencia.clients;

import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest//cajasCorrespondencia
 */
public class ObtenerStickerCorrespondenciaFisica extends ServiceClient { 
   	private String sede;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private NumeroRadicadoCorrespondenciaDTO result;
  
 	public ObtenerStickerCorrespondenciaFisica (String sede){
 		super();
		this.sede=sede;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("sede", sede)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (NumeroRadicadoCorrespondenciaDTO) response.readEntity(NumeroRadicadoCorrespondenciaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public NumeroRadicadoCorrespondenciaDTO getResult() {
		return result;
	}

 
  	public void setSede (String sede){
 		this.sede=sede;
 	}
 	
 	public String getSede (){
 		return sede;
 	}
  
  
}