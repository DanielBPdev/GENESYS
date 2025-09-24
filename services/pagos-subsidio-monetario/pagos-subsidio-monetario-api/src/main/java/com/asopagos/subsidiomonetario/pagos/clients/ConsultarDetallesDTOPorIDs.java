package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/consultarDetallesDTOPorIDs
 */
public class ConsultarDetallesDTOPorIDs extends ServiceClient { 
    	private List<Long> listaIdsDetalles;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSubsidioAsignadoDTO> result;
  
 	public ConsultarDetallesDTOPorIDs (List<Long> listaIdsDetalles){
 		super();
		this.listaIdsDetalles=listaIdsDetalles;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaIdsDetalles == null ? null : Entity.json(listaIdsDetalles));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<DetalleSubsidioAsignadoDTO>) response.readEntity(new GenericType<List<DetalleSubsidioAsignadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DetalleSubsidioAsignadoDTO> getResult() {
		return result;
	}

 
  
  	public void setListaIdsDetalles (List<Long> listaIdsDetalles){
 		this.listaIdsDetalles=listaIdsDetalles;
 	}
 	
 	public List<Long> getListaIdsDetalles (){
 		return listaIdsDetalles;
 	}
  
}