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
 * /rest/pagosSubsidioMonetario/consultarDetallesSubsidioAsignadosPorCuentaAdmin
 */
public class ConsultarDetallesSubsidioAsignadosPorCuentaAdmin extends ServiceClient {
 
  
  	private Long idCuentaAdminSub;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSubsidioAsignadoDTO> result;
  
 	public ConsultarDetallesSubsidioAsignadosPorCuentaAdmin (Long idCuentaAdminSub){
 		super();
		this.idCuentaAdminSub=idCuentaAdminSub;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCuentaAdminSub", idCuentaAdminSub)
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

 
  	public void setIdCuentaAdminSub (Long idCuentaAdminSub){
 		this.idCuentaAdminSub=idCuentaAdminSub;
 	}
 	
 	public Long getIdCuentaAdminSub (){
 		return idCuentaAdminSub;
 	}
  
}