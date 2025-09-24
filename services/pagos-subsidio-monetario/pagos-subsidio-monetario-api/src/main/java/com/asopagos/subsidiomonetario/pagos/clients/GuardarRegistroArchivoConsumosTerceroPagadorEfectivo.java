package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.ArchivoRetiroTerceroPagadorEfectivoDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/guardarRegistroArchivoConsumosTerceroPagadorEfectivo
 */
public class GuardarRegistroArchivoConsumosTerceroPagadorEfectivo extends ServiceClient { 
    	private ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarRegistroArchivoConsumosTerceroPagadorEfectivo (ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO){
 		super();
		this.archivoConsumosDTO=archivoConsumosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoConsumosDTO == null ? null : Entity.json(archivoConsumosDTO));
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

 
  
  	public void setArchivoConsumosDTO (ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO){
 		this.archivoConsumosDTO=archivoConsumosDTO;
 	}
 	
 	public ArchivoRetiroTerceroPagadorEfectivoDTO getArchivoConsumosDTO (){
 		return archivoConsumosDTO;
 	}
  
}