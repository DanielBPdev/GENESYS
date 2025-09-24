package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.LicenciaModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarLicencia
 */
public class ConsultarLicencia extends ServiceClient {
 
  
  	private Long idProyectoVivienda;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<LicenciaModeloDTO> result;
  
 	public ConsultarLicencia (Long idProyectoVivienda){
 		super();
		this.idProyectoVivienda=idProyectoVivienda;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idProyectoVivienda", idProyectoVivienda)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<LicenciaModeloDTO>) response.readEntity(new GenericType<List<LicenciaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<LicenciaModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdProyectoVivienda (Long idProyectoVivienda){
 		this.idProyectoVivienda=idProyectoVivienda;
 	}
 	
 	public Long getIdProyectoVivienda (){
 		return idProyectoVivienda;
 	}
  
}