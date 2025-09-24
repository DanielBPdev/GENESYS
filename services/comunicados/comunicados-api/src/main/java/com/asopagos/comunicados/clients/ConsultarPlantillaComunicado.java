package com.asopagos.comunicados.clients;

import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/plantillas/{etiquetaPlantilla}
 */
public class ConsultarPlantillaComunicado extends ServiceClient {
 
  	private EtiquetaPlantillaComunicadoEnum etiquetaPlantilla;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PlantillaComunicado result;
  
 	public ConsultarPlantillaComunicado (EtiquetaPlantillaComunicadoEnum etiquetaPlantilla){
 		super();
		this.etiquetaPlantilla=etiquetaPlantilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("etiquetaPlantilla", etiquetaPlantilla)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (PlantillaComunicado) response.readEntity(PlantillaComunicado.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public PlantillaComunicado getResult() {
		return result;
	}

 	public void setEtiquetaPlantilla (EtiquetaPlantillaComunicadoEnum etiquetaPlantilla){
 		this.etiquetaPlantilla=etiquetaPlantilla;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getEtiquetaPlantilla (){
 		return etiquetaPlantilla;
 	}
  
  
}