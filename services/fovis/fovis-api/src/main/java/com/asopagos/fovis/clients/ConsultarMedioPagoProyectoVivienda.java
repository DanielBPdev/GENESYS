package com.asopagos.fovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarMedioPagoProyectoVivienda
 */
public class ConsultarMedioPagoProyectoVivienda extends ServiceClient {
 
  
  	private Long idProyectoVivienda;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private MedioDePagoModeloDTO result;
  
 	public ConsultarMedioPagoProyectoVivienda (Long idProyectoVivienda){
 		super();
		this.idProyectoVivienda=idProyectoVivienda;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idProyectoVivienda", idProyectoVivienda)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (MedioDePagoModeloDTO) response.readEntity(MedioDePagoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public MedioDePagoModeloDTO getResult() {
		return result;
	}

 
  	public void setIdProyectoVivienda (Long idProyectoVivienda){
 		this.idProyectoVivienda=idProyectoVivienda;
 	}
 	
 	public Long getIdProyectoVivienda (){
 		return idProyectoVivienda;
 	}
  
}