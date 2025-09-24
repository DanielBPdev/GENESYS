package com.asopagos.tareashumanas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.tareashumanas.dto.TareaDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tareasHumanas/asignadas/usuario/{nombreUsuario}
 */
public class ObtenerTareasAsignadasUsuario extends ServiceClient {
 
  	private String nombreUsuario;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TareaDTO> result;
  
 	public ObtenerTareasAsignadasUsuario (String nombreUsuario){
 		super();
		this.nombreUsuario=nombreUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("nombreUsuario", nombreUsuario)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TareaDTO>) response.readEntity(new GenericType<List<TareaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TareaDTO> getResult() {
		return result;
	}

 	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  
}