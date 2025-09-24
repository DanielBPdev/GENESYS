package com.asopagos.empleadores.clients;

import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import java.lang.Long;
import com.asopagos.dto.UbicacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/consultarUbicacionRolContactoEmpleador/{idPersona}
 */
public class ConsultarUbicacionRolContactoEmpleador extends ServiceClient {
 
  	private Long idPersona;
  
  	private TipoRolContactoEnum tipoRolContactoEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private UbicacionDTO result;
  
 	public ConsultarUbicacionRolContactoEmpleador (Long idPersona,TipoRolContactoEnum tipoRolContactoEmpleador){
 		super();
		this.idPersona=idPersona;
		this.tipoRolContactoEmpleador=tipoRolContactoEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPersona", idPersona)
									.queryParam("tipoRolContactoEmpleador", tipoRolContactoEmpleador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (UbicacionDTO) response.readEntity(UbicacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public UbicacionDTO getResult() {
		return result;
	}

 	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  	public void setTipoRolContactoEmpleador (TipoRolContactoEnum tipoRolContactoEmpleador){
 		this.tipoRolContactoEmpleador=tipoRolContactoEmpleador;
 	}
 	
 	public TipoRolContactoEnum getTipoRolContactoEmpleador (){
 		return tipoRolContactoEmpleador;
 	}
  
}