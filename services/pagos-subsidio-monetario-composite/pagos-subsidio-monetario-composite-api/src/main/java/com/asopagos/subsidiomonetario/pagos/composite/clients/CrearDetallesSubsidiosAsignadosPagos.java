package com.asopagos.subsidiomonetario.pagos.composite.clients;

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
 * /rest/PagosSubsidioMonetarioComposite/crearDetallesSubsidiosAsignadosPagos
 */
public class CrearDetallesSubsidiosAsignadosPagos extends ServiceClient { 
    	private List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignados;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearDetallesSubsidiosAsignadosPagos (List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignados){
 		super();
		this.detallesSubsidiosAsignados=detallesSubsidiosAsignados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(detallesSubsidiosAsignados == null ? null : Entity.json(detallesSubsidiosAsignados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setDetallesSubsidiosAsignados (List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignados){
 		this.detallesSubsidiosAsignados=detallesSubsidiosAsignados;
 	}
 	
 	public List<DetalleSubsidioAsignadoDTO> getDetallesSubsidiosAsignados (){
 		return detallesSubsidiosAsignados;
 	}
  
}