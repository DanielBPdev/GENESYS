package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.ListaChequeoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/listasChequeo/consultarListaChequeo
 */
public class ConsultarListaChequeo extends ServiceClient { 
   	private ClasificacionEnum clasificacion;
  	private Long idSolicitud;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
   	private List<String> grupos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ListaChequeoDTO> result;
  
 	public ConsultarListaChequeo (ClasificacionEnum clasificacion,Long idSolicitud,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,List<String> grupos){
 		super();
		this.clasificacion=clasificacion;
		this.idSolicitud=idSolicitud;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.grupos=grupos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("clasificacion", clasificacion)
			.queryParam("idSolicitud", idSolicitud)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(grupos == null ? null : Entity.json(grupos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ListaChequeoDTO>) response.readEntity(new GenericType<List<ListaChequeoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ListaChequeoDTO> getResult() {
		return result;
	}

 
  	public void setClasificacion (ClasificacionEnum clasificacion){
 		this.clasificacion=clasificacion;
 	}
 	
 	public ClasificacionEnum getClasificacion (){
 		return clasificacion;
 	}
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
  	public void setGrupos (List<String> grupos){
 		this.grupos=grupos;
 	}
 	
 	public List<String> getGrupos (){
 		return grupos;
 	}
  
}