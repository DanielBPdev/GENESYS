package com.asopagos.comunicados.clients;

import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/plantillas/{etiquetaPlantilla}
 */
public class ActualizarPlantillaComunicado extends ServiceClient { 
  	private EtiquetaPlantillaComunicadoEnum etiquetaPlantilla;
    	private PlantillaComunicado plantillaComunicado;
  
  
 	public ActualizarPlantillaComunicado (EtiquetaPlantillaComunicadoEnum etiquetaPlantilla,PlantillaComunicado plantillaComunicado){
 		super();
		this.etiquetaPlantilla=etiquetaPlantilla;
		this.plantillaComunicado=plantillaComunicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("etiquetaPlantilla", etiquetaPlantilla)
			.request(MediaType.APPLICATION_JSON)
			.put(plantillaComunicado == null ? null : Entity.json(plantillaComunicado));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setEtiquetaPlantilla (EtiquetaPlantillaComunicadoEnum etiquetaPlantilla){
 		this.etiquetaPlantilla=etiquetaPlantilla;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getEtiquetaPlantilla (){
 		return etiquetaPlantilla;
 	}
  
  
  	public void setPlantillaComunicado (PlantillaComunicado plantillaComunicado){
 		this.plantillaComunicado=plantillaComunicado;
 	}
 	
 	public PlantillaComunicado getPlantillaComunicado (){
 		return plantillaComunicado;
 	}
  
}