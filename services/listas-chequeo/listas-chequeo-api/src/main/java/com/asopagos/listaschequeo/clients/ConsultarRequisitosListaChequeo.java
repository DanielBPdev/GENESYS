package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.listaschequeo.dto.ConsultarRequisitosListaChequeoOutDTO;
import java.util.List;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/requisitos/listasChequeo
 */
public class ConsultarRequisitosListaChequeo extends ServiceClient { 
   	private ClasificacionEnum clasificacion;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private TipoTransaccionEnum tipoTransaccion;
   	private List<String> grupos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultarRequisitosListaChequeoOutDTO> result;
  
 	public ConsultarRequisitosListaChequeo (ClasificacionEnum clasificacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,TipoTransaccionEnum tipoTransaccion,List<String> grupos){
 		super();
		this.clasificacion=clasificacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.tipoTransaccion=tipoTransaccion;
		this.grupos=grupos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("clasificacion", clasificacion)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.queryParam("tipoTransaccion", tipoTransaccion)
			.request(MediaType.APPLICATION_JSON)
			.post(grupos == null ? null : Entity.json(grupos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ConsultarRequisitosListaChequeoOutDTO>) response.readEntity(new GenericType<List<ConsultarRequisitosListaChequeoOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ConsultarRequisitosListaChequeoOutDTO> getResult() {
		return result;
	}

 
  	public void setClasificacion (ClasificacionEnum clasificacion){
 		this.clasificacion=clasificacion;
 	}
 	
 	public ClasificacionEnum getClasificacion (){
 		return clasificacion;
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
  	public void setTipoTransaccion (TipoTransaccionEnum tipoTransaccion){
 		this.tipoTransaccion=tipoTransaccion;
 	}
 	
 	public TipoTransaccionEnum getTipoTransaccion (){
 		return tipoTransaccion;
 	}
  
  	public void setGrupos (List<String> grupos){
 		this.grupos=grupos;
 	}
 	
 	public List<String> getGrupos (){
 		return grupos;
 	}
  
}