package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearListaCondicionEspecialPersona/{tipoIdentificacion}/{numeroIdentificacion}/{idPostulacion}
 */
public class CrearListaCondicionEspecialPersona extends ServiceClient { 
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
    private List<NombreCondicionEspecialEnum> listaCondicionEspecial;
    private Long idPostulacion;
  
  
 	public CrearListaCondicionEspecialPersona (String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,List<NombreCondicionEspecialEnum> listaCondicionEspecial, Long idPostulacion){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.listaCondicionEspecial=listaCondicionEspecial;
		this.idPostulacion=idPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroIdentificacion", numeroIdentificacion)
			.resolveTemplate("tipoIdentificacion", tipoIdentificacion)
			.resolveTemplate("idPostulacion", idPostulacion)
			.request(MediaType.APPLICATION_JSON)
			.post(listaCondicionEspecial == null ? null : Entity.json(listaCondicionEspecial));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
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
  
  
  	public void setListaCondicionEspecial (List<NombreCondicionEspecialEnum> listaCondicionEspecial){
 		this.listaCondicionEspecial=listaCondicionEspecial;
 	}
 	
 	public List<NombreCondicionEspecialEnum> getListaCondicionEspecial (){
 		return listaCondicionEspecial;
 	}
 	
 	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
}
