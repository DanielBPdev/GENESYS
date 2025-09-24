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
 * /rest/pagosSubsidioMonetario/consultar/listaDetallesSubsidioAsignado
 */
public class ObtenerListadoDetallesSubsidioAsingnado extends ServiceClient { 
    	private List<Long> listaIdsDetalle;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSubsidioAsignadoDTO> result;
  
 	public ObtenerListadoDetallesSubsidioAsingnado (List<Long> listaIdsDetalle){
 		super();
		this.listaIdsDetalle=listaIdsDetalle;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaIdsDetalle == null ? null : Entity.json(listaIdsDetalle));
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

 
  
  	public void setListaIdsDetalle (List<Long> listaIdsDetalle){
 		this.listaIdsDetalle=listaIdsDetalle;
 	}
 	
 	public List<Long> getListaIdsDetalle (){
 		return listaIdsDetalle;
 	}
  
}