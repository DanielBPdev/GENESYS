package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.LicenciaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/crearActualizarLicencia
 */
public class CrearActualizarLicencia extends ServiceClient { 
    	private LicenciaModeloDTO licenciaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private LicenciaModeloDTO result;
  
 	public CrearActualizarLicencia (LicenciaModeloDTO licenciaDTO){
 		super();
		this.licenciaDTO=licenciaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(licenciaDTO == null ? null : Entity.json(licenciaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (LicenciaModeloDTO) response.readEntity(LicenciaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public LicenciaModeloDTO getResult() {
		return result;
	}

 
  
  	public void setLicenciaDTO (LicenciaModeloDTO licenciaDTO){
 		this.licenciaDTO=licenciaDTO;
 	}
 	
 	public LicenciaModeloDTO getLicenciaDTO (){
 		return licenciaDTO;
 	}
  
}