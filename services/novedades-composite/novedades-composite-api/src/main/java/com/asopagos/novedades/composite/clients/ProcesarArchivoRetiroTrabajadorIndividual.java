package com.asopagos.novedades.composite.clients;

import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesMasivasComposite/procesarArchivoRetiroTrabajadorIndividual
 */
public class ProcesarArchivoRetiroTrabajadorIndividual extends ServiceClient { 
    	private InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadDTO result;
  
 	public ProcesarArchivoRetiroTrabajadorIndividual (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO){
 		super();
		this.informacionActualizacionNovedadDTO=informacionActualizacionNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(informacionActualizacionNovedadDTO == null ? null : Entity.json(informacionActualizacionNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadDTO) response.readEntity(SolicitudNovedadDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadDTO getResult() {
		return result;
	}

 
  
  	public void setInformacionActualizacionNovedadDTO (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO){
 		this.informacionActualizacionNovedadDTO=informacionActualizacionNovedadDTO;
 	}
 	
 	public InformacionActualizacionNovedadDTO getInformacionActualizacionNovedadDTO (){
 		return informacionActualizacionNovedadDTO;
 	}
  
}