package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.LicenciaDetalleModeloDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarDetalleLicencia
 */
public class ConsultarDetalleLicencia extends ServiceClient {
 
  
  	private Long idLicencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<LicenciaDetalleModeloDTO> result;
  
 	public ConsultarDetalleLicencia (Long idLicencia){
 		super();
		this.idLicencia=idLicencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idLicencia", idLicencia)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<LicenciaDetalleModeloDTO>) response.readEntity(new GenericType<List<LicenciaDetalleModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<LicenciaDetalleModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdLicencia (Long idLicencia){
 		this.idLicencia=idLicencia;
 	}
 	
 	public Long getIdLicencia (){
 		return idLicencia;
 	}
  
}