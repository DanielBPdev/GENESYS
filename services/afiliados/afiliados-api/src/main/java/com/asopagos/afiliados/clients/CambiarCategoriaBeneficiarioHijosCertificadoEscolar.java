package com.asopagos.afiliados.clients;

import java.lang.Long;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;


import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/CambiarCategoriaBeneficiarioHijosCertificadoEscolar
 */
public class CambiarCategoriaBeneficiarioHijosCertificadoEscolar extends ServiceClient { 
    	private List<Long> datosHijos;
  
  
 	public CambiarCategoriaBeneficiarioHijosCertificadoEscolar (List<Long> datosHijos){
 		super();
		this.datosHijos=datosHijos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosHijos == null ? null : Entity.json(datosHijos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosHijos (List<Long> datosHijos){
 		this.datosHijos=datosHijos;
 	}
 	
 	public List<Long> getDatosHijos (){
 		return datosHijos;
 	}
  
}