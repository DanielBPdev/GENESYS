package com.asopagos.comunicados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/comunicados/comunicadosEnviados
 */
public class ComunicadosEnviados extends ServiceClient {
 
  
  	private Boolean esEmpleador;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String numIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Object> result;
  
 	public ComunicadosEnviados (Boolean esEmpleador,TipoIdentificacionEnum tipoIdentificacion,String numIdentificacion){
 		super();
		this.esEmpleador=esEmpleador;
		this.tipoIdentificacion=tipoIdentificacion;
		this.numIdentificacion=numIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("esEmpleador", esEmpleador)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numIdentificacion", numIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Object>) response.readEntity(new GenericType<List<Object>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Object> getResult() {
		return result;
	}

 
  	public void setEsEmpleador (Boolean esEmpleador){
 		this.esEmpleador=esEmpleador;
 	}
 	
 	public Boolean getEsEmpleador (){
 		return esEmpleador;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setNumIdentificacion (String numIdentificacion){
 		this.numIdentificacion=numIdentificacion;
 	}
 	
 	public String getNumIdentificacion (){
 		return numIdentificacion;
 	}
  
}