package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.BeneficiarioDTO;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/{idAfiliado}/beneficiariosSolicitud
 */
public class ConsultarBeneficiariosSolicitud extends ServiceClient {
 
  	private Long idAfiliado;
  
  	private Long idSolicitud;
  	private Boolean sinDesafiliacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiarioDTO> result;
  
 	public ConsultarBeneficiariosSolicitud (Long idAfiliado,Long idSolicitud,Boolean sinDesafiliacion){
 		super();
		this.idAfiliado=idAfiliado;
		this.idSolicitud=idSolicitud;
		this.sinDesafiliacion=sinDesafiliacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAfiliado", idAfiliado)
									.queryParam("idSolicitud", idSolicitud)
						.queryParam("sinDesafiliacion", sinDesafiliacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BeneficiarioDTO>) response.readEntity(new GenericType<List<BeneficiarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BeneficiarioDTO> getResult() {
		return result;
	}

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  	public void setSinDesafiliacion (Boolean sinDesafiliacion){
 		this.sinDesafiliacion=sinDesafiliacion;
 	}
 	
 	public Boolean getSinDesafiliacion (){
 		return sinDesafiliacion;
 	}
  
}