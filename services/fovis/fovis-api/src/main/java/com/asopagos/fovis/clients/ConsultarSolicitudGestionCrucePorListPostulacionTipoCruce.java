package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarSolicitudGestionCrucePorListPostulacionTipoCruce
 */
public class ConsultarSolicitudGestionCrucePorListPostulacionTipoCruce extends ServiceClient {
 
  
  	private TipoCruceEnum tipoCruce;
  	private List<Long> listIdSolicitudPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudGestionCruceDTO> result;
  
 	public ConsultarSolicitudGestionCrucePorListPostulacionTipoCruce (TipoCruceEnum tipoCruce,List<Long> listIdSolicitudPostulacion){
 		super();
		this.tipoCruce=tipoCruce;
		this.listIdSolicitudPostulacion=listIdSolicitudPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoCruce", tipoCruce)
						.queryParam("listIdSolicitudPostulacion", listIdSolicitudPostulacion.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudGestionCruceDTO>) response.readEntity(new GenericType<List<SolicitudGestionCruceDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudGestionCruceDTO> getResult() {
		return result;
	}

 
  	public void setTipoCruce (TipoCruceEnum tipoCruce){
 		this.tipoCruce=tipoCruce;
 	}
 	
 	public TipoCruceEnum getTipoCruce (){
 		return tipoCruce;
 	}
  	public void setListIdSolicitudPostulacion (List<Long> listIdSolicitudPostulacion){
 		this.listIdSolicitudPostulacion=listIdSolicitudPostulacion;
 	}
 	
 	public List<Long> getListIdSolicitudPostulacion (){
 		return listIdSolicitudPostulacion;
 	}
  
}