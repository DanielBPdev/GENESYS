package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/consultarRolesEmpleadorAfiliado
 */
public class ConsultarRolesEmpleadorAfiliado extends ServiceClient { 
   	private Long idEmpleador;
   	private List<Long> idsPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RolAfiliadoModeloDTO> result;
  
 	public ConsultarRolesEmpleadorAfiliado (Long idEmpleador,List<Long> idsPersona){
 		super();
		this.idEmpleador=idEmpleador;
		this.idsPersona=idsPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(idsPersona == null ? null : Entity.json(idsPersona));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<RolAfiliadoModeloDTO>) response.readEntity(new GenericType<List<RolAfiliadoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<RolAfiliadoModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setIdsPersona (List<Long> idsPersona){
 		this.idsPersona=idsPersona;
 	}
 	
 	public List<Long> getIdsPersona (){
 		return idsPersona;
 	}
  
}