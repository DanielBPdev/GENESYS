package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.AbonoAnuladoDetalleAnuladoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/anularSubsidiosMonetariosConReemplazo
 */
public class AnularSubsidiosMonetariosConReemplazo extends ServiceClient { 
    	private AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public AnularSubsidiosMonetariosConReemplazo (AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO){
 		super();
		this.abonoAnuladoDetalleAnuladoDTO=abonoAnuladoDetalleAnuladoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(abonoAnuladoDetalleAnuladoDTO == null ? null : Entity.json(abonoAnuladoDetalleAnuladoDTO));
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

 
  
  	public void setAbonoAnuladoDetalleAnuladoDTO (AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO){
 		this.abonoAnuladoDetalleAnuladoDTO=abonoAnuladoDetalleAnuladoDTO;
 	}
 	
 	public AbonoAnuladoDetalleAnuladoDTO getAbonoAnuladoDetalleAnuladoDTO (){
 		return abonoAnuladoDetalleAnuladoDTO;
 	}
  
}