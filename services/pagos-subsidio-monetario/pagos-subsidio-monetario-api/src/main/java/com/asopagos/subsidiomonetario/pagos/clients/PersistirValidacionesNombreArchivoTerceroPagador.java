package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import java.util.Map;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ValidacionNombreArchivoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/persistirValidacionesNombreArchivoTerceroPagador
 */
public class PersistirValidacionesNombreArchivoTerceroPagador extends ServiceClient { 
   	private Long idArchivoRetiroTerceroPagadorEfectivo;
   	private Map<ValidacionNombreArchivoEnum,ResultadoValidacionNombreArchivoEnum> validaciones;
  
  
 	public PersistirValidacionesNombreArchivoTerceroPagador (Long idArchivoRetiroTerceroPagadorEfectivo,Map<ValidacionNombreArchivoEnum,ResultadoValidacionNombreArchivoEnum> validaciones){
 		super();
		this.idArchivoRetiroTerceroPagadorEfectivo=idArchivoRetiroTerceroPagadorEfectivo;
		this.validaciones=validaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idArchivoRetiroTerceroPagadorEfectivo", idArchivoRetiroTerceroPagadorEfectivo)
			.request(MediaType.APPLICATION_JSON)
			.post(validaciones == null ? null : Entity.json(validaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdArchivoRetiroTerceroPagadorEfectivo (Long idArchivoRetiroTerceroPagadorEfectivo){
 		this.idArchivoRetiroTerceroPagadorEfectivo=idArchivoRetiroTerceroPagadorEfectivo;
 	}
 	
 	public Long getIdArchivoRetiroTerceroPagadorEfectivo (){
 		return idArchivoRetiroTerceroPagadorEfectivo;
 	}
  
  	public void setValidaciones (Map<ValidacionNombreArchivoEnum,ResultadoValidacionNombreArchivoEnum> validaciones){
 		this.validaciones=validaciones;
 	}
 	
 	public Map<ValidacionNombreArchivoEnum,ResultadoValidacionNombreArchivoEnum> getValidaciones (){
 		return validaciones;
 	}
  
}