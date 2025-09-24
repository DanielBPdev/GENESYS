package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.LicenciaDetalleModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/crearActualizarDetalleLicencia
 */
public class CrearActualizarDetalleLicencia extends ServiceClient { 
    	private LicenciaDetalleModeloDTO licenciaDetalleDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private LicenciaDetalleModeloDTO result;
  
 	public CrearActualizarDetalleLicencia (LicenciaDetalleModeloDTO licenciaDetalleDTO){
 		super();
		this.licenciaDetalleDTO=licenciaDetalleDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(licenciaDetalleDTO == null ? null : Entity.json(licenciaDetalleDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (LicenciaDetalleModeloDTO) response.readEntity(LicenciaDetalleModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public LicenciaDetalleModeloDTO getResult() {
		return result;
	}

 
  
  	public void setLicenciaDetalleDTO (LicenciaDetalleModeloDTO licenciaDetalleDTO){
 		this.licenciaDetalleDTO=licenciaDetalleDTO;
 	}
 	
 	public LicenciaDetalleModeloDTO getLicenciaDetalleDTO (){
 		return licenciaDetalleDTO;
 	}
  
}