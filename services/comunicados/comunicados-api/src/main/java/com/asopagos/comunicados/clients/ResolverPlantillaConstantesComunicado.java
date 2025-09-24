package com.asopagos.comunicados.clients;

import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/{EtiquetaPlantillaComunicadoEnum}/resolverPlantillaConstantesComunicado
 */
public class ResolverPlantillaConstantesComunicado extends ServiceClient { 
  	private EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum;
    	private ParametrosComunicadoDTO parametros;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PlantillaComunicado result;
  
 	public ResolverPlantillaConstantesComunicado (EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum,ParametrosComunicadoDTO parametros){
 		super();
		this.EtiquetaPlantillaComunicadoEnum=EtiquetaPlantillaComunicadoEnum;
		this.parametros=parametros;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("EtiquetaPlantillaComunicadoEnum", EtiquetaPlantillaComunicadoEnum)
			.request(MediaType.APPLICATION_JSON)
			.post(parametros == null ? null : Entity.json(parametros));
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
  
  
  	public void setParametros (ParametrosComunicadoDTO parametros){
 		this.parametros=parametros;
 	}
 	
 	public ParametrosComunicadoDTO getParametros (){
 		return parametros;
 	}
  
}