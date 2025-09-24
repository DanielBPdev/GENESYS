package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio DELETE
 * /rest/afiliados/{idAfiliado}/beneficiarios/eliminar
 */
public class EliminarBeneficiario extends ServiceClient {
 
  	private Long idAfiliado;
  
  
  
 	public EliminarBeneficiario (Long idAfiliado,DatosBasicosIdentificacionDTO inDTO){
 		super();
		this.idAfiliado=idAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAfiliado", idAfiliado)
									.request(MediaType.APPLICATION_JSON).delete();
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
}