package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarBeneficiariosAfiliacion
 */
public class ConsultarBeneficiariosAfiliacion extends ServiceClient {
 
  
  	private Long idAfiliado;
  	private Long idRolAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiarioModeloDTO> result;
  
 	public ConsultarBeneficiariosAfiliacion (Long idAfiliado,Long idRolAfiliado){
 		super();
		this.idAfiliado=idAfiliado;
		this.idRolAfiliado=idRolAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idAfiliado", idAfiliado)
						.queryParam("idRolAfiliado", idRolAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BeneficiarioModeloDTO>) response.readEntity(new GenericType<List<BeneficiarioModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BeneficiarioModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  	public void setIdRolAfiliado (Long idRolAfiliado){
 		this.idRolAfiliado=idRolAfiliado;
 	}
 	
 	public Long getIdRolAfiliado (){
 		return idRolAfiliado;
 	}
  
}