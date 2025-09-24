package com.asopagos.correspondencia.clients;

import com.asopagos.dto.afiliaciones.DestinatarioCajaCorrespondenciaDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest//cajasCorrespondencia/{codigoEtiqueta}/cerrar
 */
public class CerrarCajaCorrespondencia extends ServiceClient { 
  	private String codigoEtiqueta;
    	private DestinatarioCajaCorrespondenciaDTO inDTO;
  
  
 	public CerrarCajaCorrespondencia (String codigoEtiqueta,DestinatarioCajaCorrespondenciaDTO inDTO){
 		super();
		this.codigoEtiqueta=codigoEtiqueta;
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("codigoEtiqueta", codigoEtiqueta)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setCodigoEtiqueta (String codigoEtiqueta){
 		this.codigoEtiqueta=codigoEtiqueta;
 	}
 	
 	public String getCodigoEtiqueta (){
 		return codigoEtiqueta;
 	}
  
  
  	public void setInDTO (DestinatarioCajaCorrespondenciaDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public DestinatarioCajaCorrespondenciaDTO getInDTO (){
 		return inDTO;
 	}
  
}