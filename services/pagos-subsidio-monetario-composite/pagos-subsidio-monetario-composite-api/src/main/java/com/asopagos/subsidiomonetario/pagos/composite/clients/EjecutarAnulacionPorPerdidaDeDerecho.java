package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidiosConsultaAnularPerdidaDerechoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/ejecutarAnulacion/porPerdidaDeDerecho
 */
public class EjecutarAnulacionPorPerdidaDeDerecho extends ServiceClient { 
    	private List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosCandidatosAnular;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public EjecutarAnulacionPorPerdidaDeDerecho (List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosCandidatosAnular){
 		super();
		this.subsidiosCandidatosAnular=subsidiosCandidatosAnular;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(subsidiosCandidatosAnular == null ? null : Entity.json(subsidiosCandidatosAnular));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setSubsidiosCandidatosAnular (List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosCandidatosAnular){
 		this.subsidiosCandidatosAnular=subsidiosCandidatosAnular;
 	}
 	
 	public List<SubsidiosConsultaAnularPerdidaDerechoDTO> getSubsidiosCandidatosAnular (){
 		return subsidiosCandidatosAnular;
 	}
  
}