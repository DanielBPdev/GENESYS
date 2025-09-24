package com.asopagos.comunicados.clients;

import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import java.util.Map;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/{EtiquetaPlantillaComunicadoEnum}/resolverPlantillaCarteraConsolidadoComunicado
 */
public class ResolverPlantillaCarteraConsolidadoComunicado extends ServiceClient { 
  	private EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum;
    	private Map<String,Object> map;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PlantillaComunicado result;
  
 	public ResolverPlantillaCarteraConsolidadoComunicado (EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum,Map<String,Object> map){
 		super();
		this.EtiquetaPlantillaComunicadoEnum=EtiquetaPlantillaComunicadoEnum;
		this.map=map;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("EtiquetaPlantillaComunicadoEnum", EtiquetaPlantillaComunicadoEnum)
			.request(MediaType.APPLICATION_JSON)
			.post(map == null ? null : Entity.json(map));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (PlantillaComunicado) response.readEntity(PlantillaComunicado.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public PlantillaComunicado getResult() {
		return result;
	}

 	public void setEtiquetaPlantillaComunicadoEnum (EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum){
 		this.EtiquetaPlantillaComunicadoEnum=EtiquetaPlantillaComunicadoEnum;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getEtiquetaPlantillaComunicadoEnum (){
 		return EtiquetaPlantillaComunicadoEnum;
 	}
  
  
  	public void setMap (Map<String,Object> map){
 		this.map=map;
 	}
 	
 	public Map<String,Object> getMap (){
 		return map;
 	}
  
}