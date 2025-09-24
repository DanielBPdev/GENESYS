package com.asopagos.comunicados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/plantillas/{EtiquetaPlantillaComunicadoEnum}/resolverVariablesIdPlantilla
 */
public class ResolverVariablesComunicadoIdPlantilla extends ServiceClient {
 
  	private EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum;
  
  	private Long idSolicitud;
  	private Long idInstancia;
  	private Long idPlantillaComunicado;
  	private String ordenamiento;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public ResolverVariablesComunicadoIdPlantilla (EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum,Long idSolicitud,Long idInstancia,Long idPlantillaComunicado,String ordenamiento){
 		super();
		this.EtiquetaPlantillaComunicadoEnum=EtiquetaPlantillaComunicadoEnum;
		this.idSolicitud=idSolicitud;
		this.idInstancia=idInstancia;
		this.idPlantillaComunicado=idPlantillaComunicado;
		this.ordenamiento=ordenamiento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("EtiquetaPlantillaComunicadoEnum", EtiquetaPlantillaComunicadoEnum)
									.queryParam("idSolicitud", idSolicitud)
						.queryParam("idInstancia", idInstancia)
						.queryParam("idPlantillaComunicado", idPlantillaComunicado)
						.queryParam("ordenamiento", ordenamiento)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,Object> getResult() {
		return result;
	}

 	public void setEtiquetaPlantillaComunicadoEnum (EtiquetaPlantillaComunicadoEnum EtiquetaPlantillaComunicadoEnum){
 		this.EtiquetaPlantillaComunicadoEnum=EtiquetaPlantillaComunicadoEnum;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getEtiquetaPlantillaComunicadoEnum (){
 		return EtiquetaPlantillaComunicadoEnum;
 	}
  
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  	public void setIdInstancia (Long idInstancia){
 		this.idInstancia=idInstancia;
 	}
 	
 	public Long getIdInstancia (){
 		return idInstancia;
 	}
  	public void setIdPlantillaComunicado (Long idPlantillaComunicado){
 		this.idPlantillaComunicado=idPlantillaComunicado;
 	}
 	
 	public Long getIdPlantillaComunicado (){
 		return idPlantillaComunicado;
 	}
  	public void setOrdenamiento (String ordenamiento){
 		this.ordenamiento=ordenamiento;
 	}
 	
 	public String getOrdenamiento (){
 		return ordenamiento;
 	}
  
}