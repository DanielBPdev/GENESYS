package com.asopagos.auditoria.clients;

import java.util.List;
import com.asopagos.auditoria.dto.ParametrizacionTablaAuditableDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/auditoria/actualizarTablasAuditables
 */
public class ActualizarTablasAuditables extends ServiceClient { 
    	private List<ParametrizacionTablaAuditableDTO> tablasAuditablesIn;
  
  
 	public ActualizarTablasAuditables (List<ParametrizacionTablaAuditableDTO> tablasAuditablesIn){
 		super();
		this.tablasAuditablesIn=tablasAuditablesIn;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(tablasAuditablesIn == null ? null : Entity.json(tablasAuditablesIn));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setTablasAuditablesIn (List<ParametrizacionTablaAuditableDTO> tablasAuditablesIn){
 		this.tablasAuditablesIn=tablasAuditablesIn;
 	}
 	
 	public List<ParametrizacionTablaAuditableDTO> getTablasAuditablesIn (){
 		return tablasAuditablesIn;
 	}
  
}