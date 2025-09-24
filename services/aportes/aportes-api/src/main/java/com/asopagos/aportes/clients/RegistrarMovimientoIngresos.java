package com.asopagos.aportes.clients;

import java.util.List;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/registrarMovimientoIngresos
 */
public class RegistrarMovimientoIngresos extends ServiceClient { 
   	private EstadoRegistroAporteEnum estadoRegistro;
   	private List<Long> idsAportes;
  
  
 	public RegistrarMovimientoIngresos (EstadoRegistroAporteEnum estadoRegistro,List<Long> idsAportes){
 		super();
		this.estadoRegistro=estadoRegistro;
		this.idsAportes=idsAportes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("estadoRegistro", estadoRegistro)
			.request(MediaType.APPLICATION_JSON)
			.post(idsAportes == null ? null : Entity.json(idsAportes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setEstadoRegistro (EstadoRegistroAporteEnum estadoRegistro){
 		this.estadoRegistro=estadoRegistro;
 	}
 	
 	public EstadoRegistroAporteEnum getEstadoRegistro (){
 		return estadoRegistro;
 	}
  
  	public void setIdsAportes (List<Long> idsAportes){
 		this.idsAportes=idsAportes;
 	}
 	
 	public List<Long> getIdsAportes (){
 		return idsAportes;
 	}
  
}