package com.asopagos.bandejainconsistencias.clients;

import java.util.List;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/rechazarSolicitudCambioIdenficacion
 */
public class RechazarSolicitudCambioIden extends ServiceClient { 
   	private String comentarios;
  	private RazonRechazoSolicitudCambioIdenEnum razonRechazo;
   	private List<SolicitudCambioNumIdentAportante> listaSolicitudes;
  
  
 	public RechazarSolicitudCambioIden (String comentarios,RazonRechazoSolicitudCambioIdenEnum razonRechazo,List<SolicitudCambioNumIdentAportante> listaSolicitudes){
 		super();
		this.comentarios=comentarios;
		this.razonRechazo=razonRechazo;
		this.listaSolicitudes=listaSolicitudes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("comentarios", comentarios)
			.queryParam("razonRechazo", razonRechazo)
			.request(MediaType.APPLICATION_JSON)
			.post(listaSolicitudes == null ? null : Entity.json(listaSolicitudes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setComentarios (String comentarios){
 		this.comentarios=comentarios;
 	}
 	
 	public String getComentarios (){
 		return comentarios;
 	}
  	public void setRazonRechazo (RazonRechazoSolicitudCambioIdenEnum razonRechazo){
 		this.razonRechazo=razonRechazo;
 	}
 	
 	public RazonRechazoSolicitudCambioIdenEnum getRazonRechazo (){
 		return razonRechazo;
 	}
  
  	public void setListaSolicitudes (List<SolicitudCambioNumIdentAportante> listaSolicitudes){
 		this.listaSolicitudes=listaSolicitudes;
 	}
 	
 	public List<SolicitudCambioNumIdentAportante> getListaSolicitudes (){
 		return listaSolicitudes;
 	}
  
}