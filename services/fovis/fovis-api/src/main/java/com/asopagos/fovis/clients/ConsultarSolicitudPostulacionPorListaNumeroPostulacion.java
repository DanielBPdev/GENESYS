package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/consultarSolicitudPostulacionPorListaNumeroPostulacion
 */
public class ConsultarSolicitudPostulacionPorListaNumeroPostulacion extends ServiceClient {
 
  
  	private List<String> listNumeroPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudPostulacionModeloDTO> result;
  
 	public ConsultarSolicitudPostulacionPorListaNumeroPostulacion (List<String> listNumeroPostulacion){
		super();
		this.listNumeroPostulacion=listNumeroPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.request(MediaType.APPLICATION_JSON)
				.post(listNumeroPostulacion == null ? null : Entity.json(listNumeroPostulacion));
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudPostulacionModeloDTO>) response.readEntity(new GenericType<List<SolicitudPostulacionModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudPostulacionModeloDTO> getResult() {
		return result;
	}

 
  	public void setListNumeroPostulacion (List<String> listNumeroPostulacion){
 		this.listNumeroPostulacion=listNumeroPostulacion;
 	}
 	
 	public List<String> getListNumeroPostulacion (){
 		return listNumeroPostulacion;
 	}
  
}