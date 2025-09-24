package com.asopagos.entidaddescuento.clients;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadDescuento/archivoDescuentos/generarResultados
 */
public class GenerarResultadosArchivoDescuento extends ServiceClient { 
    	private ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO trazabilidadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionArchivoDTO result;
  
 	public GenerarResultadosArchivoDescuento (ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO trazabilidadDTO){
 		super();
		this.trazabilidadDTO=trazabilidadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(trazabilidadDTO == null ? null : Entity.json(trazabilidadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InformacionArchivoDTO) response.readEntity(InformacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InformacionArchivoDTO getResult() {
		return result;
	}

 
  
  	public void setTrazabilidadDTO (ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO trazabilidadDTO){
 		this.trazabilidadDTO=trazabilidadDTO;
 	}
 	
 	public ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO getTrazabilidadDTO (){
 		return trazabilidadDTO;
 	}
  
}