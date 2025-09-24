package com.asopagos.empleadores.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/{tipoBeneficio}/inactivarBeneficios
 */
public class InactivarBeneficios extends ServiceClient { 
  	private TipoBeneficioEnum tipoBeneficio;
    	private List<Long> idEmpleadores;
  
  
 	public InactivarBeneficios (TipoBeneficioEnum tipoBeneficio,List<Long> idEmpleadores){
 		super();
		this.tipoBeneficio=tipoBeneficio;
		this.idEmpleadores=idEmpleadores;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("tipoBeneficio", tipoBeneficio)
			.request(MediaType.APPLICATION_JSON)
			.post(idEmpleadores == null ? null : Entity.json(idEmpleadores));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setTipoBeneficio (TipoBeneficioEnum tipoBeneficio){
 		this.tipoBeneficio=tipoBeneficio;
 	}
 	
 	public TipoBeneficioEnum getTipoBeneficio (){
 		return tipoBeneficio;
 	}
  
  
  	public void setIdEmpleadores (List<Long> idEmpleadores){
 		this.idEmpleadores=idEmpleadores;
 	}
 	
 	public List<Long> getIdEmpleadores (){
 		return idEmpleadores;
 	}
  
}