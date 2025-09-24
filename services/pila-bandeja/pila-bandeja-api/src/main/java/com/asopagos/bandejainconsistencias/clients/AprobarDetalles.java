package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.bandejainconsistencias.dto.PreparacionAprobacion399DTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/AprobarDetalles
 */
public class AprobarDetalles extends ServiceClient { 
    	private List<Long> IdsDetallados;
        private Boolean reproceso;
		private String usuario;
  
  
 	
  
 	public AprobarDetalles (List<Long> IdsDetallados,Boolean reproceso, String usuario){
 		super();
		this.IdsDetallados=IdsDetallados;
        this.reproceso = reproceso;
		this.usuario = usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
		    .queryParam("reproceso", reproceso)
			.queryParam("usuario", usuario)
			.request(MediaType.APPLICATION_JSON)
			.post(IdsDetallados == null ? null : Entity.json(IdsDetallados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		
	}

    public void setreproceso (Boolean reproceso){
        this.reproceso=reproceso;
    }
    
    public Boolean  getreproceso (){
        return reproceso;
    }
  
  	public void setIdsDetallados (List<Long> IdsDetallados){
 		this.IdsDetallados=IdsDetallados;
 	}
 	
 	public List<Long>  getIdsDetallados (){
 		return IdsDetallados;
 	}

	 public void setusuario (String usuario){
		this.usuario=usuario;
	}
	
	public String  getusuario (){
		return usuario;
	}
 
  
}