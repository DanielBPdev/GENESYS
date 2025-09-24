package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/verDetallesSubsidioAsignado/{idCuentaAdminSubsidio}
 */
public class VerDetallesSubsidioAsignado extends ServiceClient {
 
  	private Long idCuentaAdminSubsidio;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSubsidioAsignadoDTO> result;
  
 	public VerDetallesSubsidioAsignado (Long idCuentaAdminSubsidio){
 		super();
		this.idCuentaAdminSubsidio=idCuentaAdminSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idCuentaAdminSubsidio", idCuentaAdminSubsidio)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DetalleSubsidioAsignadoDTO>) response.readEntity(new GenericType<List<DetalleSubsidioAsignadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DetalleSubsidioAsignadoDTO> getResult() {
		return result;
	}

 	public void setIdCuentaAdminSubsidio (Long idCuentaAdminSubsidio){
 		this.idCuentaAdminSubsidio=idCuentaAdminSubsidio;
 	}
 	
 	public Long getIdCuentaAdminSubsidio (){
 		return idCuentaAdminSubsidio;
 	}
  
  
}