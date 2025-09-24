package com.asopagos.clienteanibol.clients;

import com.asopagos.clienteanibol.dto.TarjetaDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/anibol/consultaTarjetaActiva
 */
public class ConsultarTarjetaActiva extends ServiceClient {
 
  
  	private String id;
  	private String tipoId;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TarjetaDTO result;
  
 	public ConsultarTarjetaActiva (String id,String tipoId){
 		super();
		this.id=id;
		this.tipoId=tipoId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("id", id)
						.queryParam("tipoId", tipoId)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (TarjetaDTO) response.readEntity(TarjetaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public TarjetaDTO getResult() {
		return result;
	}

 
  	public void setId (String id){
 		this.id=id;
 	}
 	
 	public String getId (){
 		return id;
 	}
  	public void setTipoId (String tipoId){
 		this.tipoId=tipoId;
 	}
 	
 	public String getTipoId (){
 		return tipoId;
 	}
  
}