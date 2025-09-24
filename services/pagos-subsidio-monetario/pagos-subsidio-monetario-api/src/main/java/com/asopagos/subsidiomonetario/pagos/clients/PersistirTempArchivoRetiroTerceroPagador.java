package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.TempArchivoRetiroTerceroPagadorEfectivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/persistirTempArchivoRetiroTerceroPagador
 */
public class PersistirTempArchivoRetiroTerceroPagador extends ServiceClient { 
    	private TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public PersistirTempArchivoRetiroTerceroPagador (TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO){
 		super();
		this.TempArchivoRetiroTerceroPagadorEfectivoDTO=TempArchivoRetiroTerceroPagadorEfectivoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(TempArchivoRetiroTerceroPagadorEfectivoDTO == null ? null : Entity.json(TempArchivoRetiroTerceroPagadorEfectivoDTO));
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

 
  
  	public void setTempArchivoRetiroTerceroPagadorEfectivoDTO (TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO){
 		this.TempArchivoRetiroTerceroPagadorEfectivoDTO=TempArchivoRetiroTerceroPagadorEfectivoDTO;
 	}
 	
 	public TempArchivoRetiroTerceroPagadorEfectivoDTO getTempArchivoRetiroTerceroPagadorEfectivoDTO (){
 		return TempArchivoRetiroTerceroPagadorEfectivoDTO;
 	}
  
}