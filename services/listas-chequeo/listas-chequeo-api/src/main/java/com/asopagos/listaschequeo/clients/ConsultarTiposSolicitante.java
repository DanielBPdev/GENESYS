package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tiposSolicitante
 */
public class ConsultarTiposSolicitante extends ServiceClient {
 
  
  	private TipoTransaccionEnum tipoTx;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ElementoListaDTO> result;
  
 	public ConsultarTiposSolicitante (TipoTransaccionEnum tipoTx){
 		super();
		this.tipoTx=tipoTx;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoTx", tipoTx)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ElementoListaDTO>) response.readEntity(new GenericType<List<ElementoListaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ElementoListaDTO> getResult() {
		return result;
	}

 
  	public void setTipoTx (TipoTransaccionEnum tipoTx){
 		this.tipoTx=tipoTx;
 	}
 	
 	public TipoTransaccionEnum getTipoTx (){
 		return tipoTx;
 	}
  
}