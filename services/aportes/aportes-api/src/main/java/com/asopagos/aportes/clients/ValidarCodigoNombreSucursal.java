package com.asopagos.aportes.clients;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/validarCodigoNombreSucursal
 */
public class ValidarCodigoNombreSucursal extends ServiceClient { 
   	private String codigoSucursalPila;
  	private Long idRegistroGeneral;
  	private String codigoSucursalPrincipal;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarCodigoNombreSucursal (String codigoSucursalPila,Long idRegistroGeneral,String codigoSucursalPrincipal){
 		super();
		this.codigoSucursalPila=codigoSucursalPila;
		this.idRegistroGeneral=idRegistroGeneral;
		this.codigoSucursalPrincipal=codigoSucursalPrincipal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("codigoSucursalPila", codigoSucursalPila)
			.queryParam("idRegistroGeneral", idRegistroGeneral)
			.queryParam("codigoSucursalPrincipal", codigoSucursalPrincipal)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  	public void setCodigoSucursalPila (String codigoSucursalPila){
 		this.codigoSucursalPila=codigoSucursalPila;
 	}
 	
 	public String getCodigoSucursalPila (){
 		return codigoSucursalPila;
 	}
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  	public void setCodigoSucursalPrincipal (String codigoSucursalPrincipal){
 		this.codigoSucursalPrincipal=codigoSucursalPrincipal;
 	}
 	
 	public String getCodigoSucursalPrincipal (){
 		return codigoSucursalPrincipal;
 	}
  
  
}