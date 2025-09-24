package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.DescuentoSubsidioAsignadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarDescuentosSubsidioAsignado
 */
public class ConsultarDescuentosSubsidioAsignado extends ServiceClient {
 
  
  	private Long idDetalleSubsidioAsignado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DescuentoSubsidioAsignadoDTO> result;
  
 	public ConsultarDescuentosSubsidioAsignado (Long idDetalleSubsidioAsignado){
 		super();
		this.idDetalleSubsidioAsignado=idDetalleSubsidioAsignado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idDetalleSubsidioAsignado", idDetalleSubsidioAsignado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DescuentoSubsidioAsignadoDTO>) response.readEntity(new GenericType<List<DescuentoSubsidioAsignadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DescuentoSubsidioAsignadoDTO> getResult() {
		return result;
	}

 
  	public void setIdDetalleSubsidioAsignado (Long idDetalleSubsidioAsignado){
 		this.idDetalleSubsidioAsignado=idDetalleSubsidioAsignado;
 	}
 	
 	public Long getIdDetalleSubsidioAsignado (){
 		return idDetalleSubsidioAsignado;
 	}
  
}