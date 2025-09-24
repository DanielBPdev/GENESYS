package com.asopagos.comunicados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/certificados/actualizarCertificado
 */
public class ActualizarCertificado extends ServiceClient { 
   	private EtiquetaPlantillaComunicadoEnum etiqueta;
  	private Long idCertificado;
   
  
 	public ActualizarCertificado (EtiquetaPlantillaComunicadoEnum etiqueta,Long idCertificado){
 		super();
		this.etiqueta=etiqueta;
		this.idCertificado=idCertificado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("etiqueta", etiqueta)
			.queryParam("idCertificado", idCertificado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setEtiqueta (EtiquetaPlantillaComunicadoEnum etiqueta){
 		this.etiqueta=etiqueta;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getEtiqueta (){
 		return etiqueta;
 	}
  	public void setIdCertificado (Long idCertificado){
 		this.idCertificado=idCertificado;
 	}
 	
 	public Long getIdCertificado (){
 		return idCertificado;
 	}
  
  
}