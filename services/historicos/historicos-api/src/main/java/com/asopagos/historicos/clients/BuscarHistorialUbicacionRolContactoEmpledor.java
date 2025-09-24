package com.asopagos.historicos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import java.lang.Long;
import com.asopagos.dto.UbicacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicos/buscarHistorialUbicacionRolContactoEmpledor/{idPersona}
 */
public class BuscarHistorialUbicacionRolContactoEmpledor extends ServiceClient {
 
  	private Long idPersona;
  
  	private Long fechaRevision;
  	private TipoRolContactoEnum tipoRolContactoEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UbicacionDTO> result;
  
 	public BuscarHistorialUbicacionRolContactoEmpledor (Long idPersona,Long fechaRevision,TipoRolContactoEnum tipoRolContactoEmpleador){
 		super();
		this.idPersona=idPersona;
		this.fechaRevision=fechaRevision;
		this.tipoRolContactoEmpleador=tipoRolContactoEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPersona", idPersona)
									.queryParam("fechaRevision", fechaRevision)
						.queryParam("tipoRolContactoEmpleador", tipoRolContactoEmpleador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<UbicacionDTO>) response.readEntity(new GenericType<List<UbicacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<UbicacionDTO> getResult() {
		return result;
	}

 	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  	public void setFechaRevision (Long fechaRevision){
 		this.fechaRevision=fechaRevision;
 	}
 	
 	public Long getFechaRevision (){
 		return fechaRevision;
 	}
  	public void setTipoRolContactoEmpleador (TipoRolContactoEnum tipoRolContactoEmpleador){
 		this.tipoRolContactoEmpleador=tipoRolContactoEmpleador;
 	}
 	
 	public TipoRolContactoEnum getTipoRolContactoEmpleador (){
 		return tipoRolContactoEmpleador;
 	}
  
}