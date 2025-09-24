package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.usuarios.constants.TipoGrupoEnum;
import com.asopagos.usuarios.dto.GrupoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/grupos/{tipo}
 */
public class ConsultarGrupos extends ServiceClient {
 
  	private TipoGrupoEnum tipo;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<GrupoDTO> result;
  
 	public ConsultarGrupos (TipoGrupoEnum tipo){
 		super();
		this.tipo=tipo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("tipo", tipo)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<GrupoDTO>) response.readEntity(new GenericType<List<GrupoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<GrupoDTO> getResult() {
		return result;
	}

 	public void setTipo (TipoGrupoEnum tipo){
 		this.tipo=tipo;
 	}
 	
 	public TipoGrupoEnum getTipo (){
 		return tipo;
 	}
  
  
}