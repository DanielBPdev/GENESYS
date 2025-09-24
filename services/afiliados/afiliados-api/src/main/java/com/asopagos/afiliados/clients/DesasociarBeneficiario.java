package com.asopagos.afiliados.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/{idAfiliado}/beneficiarios/desasociar/{idBeneficiario}
 */
public class DesasociarBeneficiario extends ServiceClient { 
  	private Long idBeneficiario;
  	private Long idAfiliado;
    
  
 	public DesasociarBeneficiario (Long idBeneficiario,Long idAfiliado){
 		super();
		this.idBeneficiario=idBeneficiario;
		this.idAfiliado=idAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idBeneficiario", idBeneficiario)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdBeneficiario (Long idBeneficiario){
 		this.idBeneficiario=idBeneficiario;
 	}
 	
 	public Long getIdBeneficiario (){
 		return idBeneficiario;
 	}
  	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
  
}