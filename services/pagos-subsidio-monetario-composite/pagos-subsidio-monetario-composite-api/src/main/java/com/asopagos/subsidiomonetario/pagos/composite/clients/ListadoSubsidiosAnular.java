package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioPrescribirAnularFechaDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/listado/subsidiosAnular
 */
public class ListadoSubsidiosAnular extends ServiceClient {
 
  
  	private String tipo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SubsidioMonetarioPrescribirAnularFechaDTO> result;
  
 	public ListadoSubsidiosAnular (String tipo){
 		super();
		this.tipo=tipo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipo", tipo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SubsidioMonetarioPrescribirAnularFechaDTO>) response.readEntity(new GenericType<List<SubsidioMonetarioPrescribirAnularFechaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SubsidioMonetarioPrescribirAnularFechaDTO> getResult() {
		return result;
	}

 
  	public void setTipo (String tipo){
 		this.tipo=tipo;
 	}
 	
 	public String getTipo (){
 		return tipo;
 	}
  
}