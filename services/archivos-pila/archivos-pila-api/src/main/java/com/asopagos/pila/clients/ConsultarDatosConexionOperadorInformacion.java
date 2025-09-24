package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivosPILA/consultarDatosConexionOperadorInformacion
 */
public class ConsultarDatosConexionOperadorInformacion extends ServiceClient {
 
  
  	private Long idProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConexionOperadorInformacion> result;
  
 	public ConsultarDatosConexionOperadorInformacion (Long idProceso){
 		super();
		this.idProceso=idProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idProceso", idProceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConexionOperadorInformacion>) response.readEntity(new GenericType<List<ConexionOperadorInformacion>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ConexionOperadorInformacion> getResult() {
		return result;
	}

 
  	public void setIdProceso (Long idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public Long getIdProceso (){
 		return idProceso;
 	}
  
}