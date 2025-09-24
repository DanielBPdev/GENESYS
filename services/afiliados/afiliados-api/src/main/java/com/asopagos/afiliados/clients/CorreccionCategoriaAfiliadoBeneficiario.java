package com.asopagos.afiliados.clients;

import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/datosIdentificacionYUbicacion
 */
public class CorreccionCategoriaAfiliadoBeneficiario extends ServiceClient { 
  
  
 	public CorreccionCategoriaAfiliadoBeneficiario (){
 		super();
 	}
 
    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null)); // Se envía un cuerpo vacío (JSON null)
        return response;
    }
	
	@Override
	protected void getResultData(Response response) {
	}
	

  
}