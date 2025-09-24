package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/actualizarEstado/retirosConciliados/terceroPagador/{idArchivoRetiroTerceroPagador}
 */
public class ActualizarEstadoRetirosConciliadosTerceroPagador extends ServiceClient { 
  	private Long idArchivoRetiroTerceroPagador;
    
  
 	public ActualizarEstadoRetirosConciliadosTerceroPagador (Long idArchivoRetiroTerceroPagador){
 		super();
		this.idArchivoRetiroTerceroPagador=idArchivoRetiroTerceroPagador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idArchivoRetiroTerceroPagador", idArchivoRetiroTerceroPagador)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdArchivoRetiroTerceroPagador (Long idArchivoRetiroTerceroPagador){
 		this.idArchivoRetiroTerceroPagador=idArchivoRetiroTerceroPagador;
 	}
 	
 	public Long getIdArchivoRetiroTerceroPagador (){
 		return idArchivoRetiroTerceroPagador;
 	}
  
  
  
}