package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.GrupoFamiliarDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/{idAfiliado}/gruposFamiliares
 */
public class ConsultarGrupoFamiliar extends ServiceClient {
 
  	private Long idAfiliado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<GrupoFamiliarDTO> result;
  
 	public ConsultarGrupoFamiliar (Long idAfiliado){
 		super();
		this.idAfiliado=idAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAfiliado", idAfiliado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<GrupoFamiliarDTO>) response.readEntity(new GenericType<List<GrupoFamiliarDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<GrupoFamiliarDTO> getResult() {
		return result;
	}

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
}