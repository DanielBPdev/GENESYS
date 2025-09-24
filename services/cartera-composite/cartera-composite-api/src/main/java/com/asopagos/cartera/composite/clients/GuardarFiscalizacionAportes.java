package com.asopagos.cartera.composite.clients;

import java.lang.String;
import com.asopagos.dto.modelo.ProgramacionFiscalizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarFiscalizacionAportes
 */
public class GuardarFiscalizacionAportes extends ServiceClient { 
   	private String numeroRadicacion;
   	private ProgramacionFiscalizacionDTO programacionFiscalizacionDTO;
  
  
 	public GuardarFiscalizacionAportes (String numeroRadicacion,ProgramacionFiscalizacionDTO programacionFiscalizacionDTO){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.programacionFiscalizacionDTO=programacionFiscalizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroRadicacion", numeroRadicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(programacionFiscalizacionDTO == null ? null : Entity.json(programacionFiscalizacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setProgramacionFiscalizacionDTO (ProgramacionFiscalizacionDTO programacionFiscalizacionDTO){
 		this.programacionFiscalizacionDTO=programacionFiscalizacionDTO;
 	}
 	
 	public ProgramacionFiscalizacionDTO getProgramacionFiscalizacionDTO (){
 		return programacionFiscalizacionDTO;
 	}
  
}