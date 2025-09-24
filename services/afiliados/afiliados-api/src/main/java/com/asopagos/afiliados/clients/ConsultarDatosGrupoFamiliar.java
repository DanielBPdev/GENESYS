package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/{idGrupoFamiliar}/consultarDatosGrupoFamiliar
 */
public class ConsultarDatosGrupoFamiliar extends ServiceClient {
 
  	private Long idGrupoFamiliar;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private GrupoFamiliarModeloDTO result;
  
 	public ConsultarDatosGrupoFamiliar (Long idGrupoFamiliar){
 		super();
		this.idGrupoFamiliar=idGrupoFamiliar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idGrupoFamiliar", idGrupoFamiliar)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (GrupoFamiliarModeloDTO) response.readEntity(GrupoFamiliarModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public GrupoFamiliarModeloDTO getResult() {
		return result;
	}

 	public void setIdGrupoFamiliar (Long idGrupoFamiliar){
 		this.idGrupoFamiliar=idGrupoFamiliar;
 	}
 	
 	public Long getIdGrupoFamiliar (){
 		return idGrupoFamiliar;
 	}
  
  
}