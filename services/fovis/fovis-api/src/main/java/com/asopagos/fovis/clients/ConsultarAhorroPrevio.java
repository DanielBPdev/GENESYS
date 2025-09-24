package com.asopagos.fovis.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarAhorroPrevio
 */
public class ConsultarAhorroPrevio extends ServiceClient {
 
  
  	private TipoAhorroPrevioEnum tipoAhorro;
  	private Long idPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AhorroPrevioModeloDTO result;
  
 	public ConsultarAhorroPrevio (TipoAhorroPrevioEnum tipoAhorro,Long idPostulacion){
 		super();
		this.tipoAhorro=tipoAhorro;
		this.idPostulacion=idPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoAhorro", tipoAhorro)
						.queryParam("idPostulacion", idPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (AhorroPrevioModeloDTO) response.readEntity(AhorroPrevioModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public AhorroPrevioModeloDTO getResult() {
		return result;
	}

 
  	public void setTipoAhorro (TipoAhorroPrevioEnum tipoAhorro){
 		this.tipoAhorro=tipoAhorro;
 	}
 	
 	public TipoAhorroPrevioEnum getTipoAhorro (){
 		return tipoAhorro;
 	}
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
}