package com.asopagos.validaciones.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/validacionesAPI/personas/validar
 */
public class ValidarPersonas extends ServiceClient { 
   	private String bloque;
  	private ProcesoEnum proceso;
  	private String objetoValidacion;
   	private Map<String,String> datosValidacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ValidacionDTO> result;
  
 	public ValidarPersonas (String bloque,ProcesoEnum proceso,String objetoValidacion,Map<String,String> datosValidacion){
 		super();
		this.bloque=bloque;
		this.proceso=proceso;
		this.objetoValidacion=objetoValidacion;
		this.datosValidacion=datosValidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("bloque", bloque)
			.queryParam("proceso", proceso)
			.queryParam("objetoValidacion", objetoValidacion)
			.request(MediaType.APPLICATION_JSON)
			.post(datosValidacion == null ? null : Entity.json(datosValidacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ValidacionDTO>) response.readEntity(new GenericType<List<ValidacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ValidacionDTO> getResult() {
		return result;
	}

 
  	public void setBloque (String bloque){
 		this.bloque=bloque;
 	}
 	
 	public String getBloque (){
 		return bloque;
 	}
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setObjetoValidacion (String objetoValidacion){
 		this.objetoValidacion=objetoValidacion;
 	}
 	
 	public String getObjetoValidacion (){
 		return objetoValidacion;
 	}
  
  	public void setDatosValidacion (Map<String,String> datosValidacion){
 		this.datosValidacion=datosValidacion;
 	}
 	
 	public Map<String,String> getDatosValidacion (){
 		return datosValidacion;
 	}
  
}