package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.CuentaAporteDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.AnalisisDevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarCuentaAportes
 */
public class ConsultarCuentaAporte extends ServiceClient { 
   	private Long idPersonaCotizante;
   	private List<AnalisisDevolucionDTO> analisisDevolucionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAporteDTO> result;
  
 	public ConsultarCuentaAporte (Long idPersonaCotizante,List<AnalisisDevolucionDTO> analisisDevolucionDTO){
 		super();
		this.idPersonaCotizante=idPersonaCotizante;
		this.analisisDevolucionDTO=analisisDevolucionDTO;
 	} 
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPersonaCotizante", idPersonaCotizante)
			.request(MediaType.APPLICATION_JSON)
			.post(analisisDevolucionDTO == null ? null : Entity.json(analisisDevolucionDTO));
		return response;
	} 
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CuentaAporteDTO>) response.readEntity(new GenericType<List<CuentaAporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CuentaAporteDTO> getResult() {
		return result;
	}

 
  	public void setIdPersonaCotizante (Long idPersonaCotizante){
 		this.idPersonaCotizante=idPersonaCotizante;
 	}
 	
 	public Long getIdPersonaCotizante (){
 		return idPersonaCotizante;
 	}
  
  	public void setAnalisisDevolucionDTO (List<AnalisisDevolucionDTO> analisisDevolucionDTO){
 		this.analisisDevolucionDTO=analisisDevolucionDTO;
 	}
 	
 	public List<AnalisisDevolucionDTO> getAnalisisDevolucionDTO (){
 		return analisisDevolucionDTO;
 	}
  
}