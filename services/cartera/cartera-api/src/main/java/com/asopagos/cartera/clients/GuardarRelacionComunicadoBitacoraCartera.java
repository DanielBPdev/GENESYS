package com.asopagos.cartera.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.dto.modelo.GuardarRelacionComunicadoBitacoraCarteraDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarRelacionComunicadoBitacoraCartera
 */
public class GuardarRelacionComunicadoBitacoraCartera extends ServiceClient { 
    	private GuardarRelacionComunicadoBitacoraCarteraDTO relacionComunicadoBitacoraCarteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarRelacionComunicadoBitacoraCartera (GuardarRelacionComunicadoBitacoraCarteraDTO relacionComunicadoBitacoraCarteraDTO){
 		super();
		this.relacionComunicadoBitacoraCarteraDTO=relacionComunicadoBitacoraCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(relacionComunicadoBitacoraCarteraDTO == null ? null : Entity.json(relacionComunicadoBitacoraCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}


    public GuardarRelacionComunicadoBitacoraCarteraDTO getRelacionComunicadoBitacoraCarteraDTO() {
        return this.relacionComunicadoBitacoraCarteraDTO;
    }

    public void setRelacionComunicadoBitacoraCarteraDTO(GuardarRelacionComunicadoBitacoraCarteraDTO relacionComunicadoBitacoraCarteraDTO) {
        this.relacionComunicadoBitacoraCarteraDTO = relacionComunicadoBitacoraCarteraDTO;
    }
    public void setResult(Long result) {
        this.result = result;
    }
 
}