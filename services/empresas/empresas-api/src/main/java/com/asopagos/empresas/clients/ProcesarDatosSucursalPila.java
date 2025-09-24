package com.asopagos.empresas.clients;

import com.asopagos.dto.DatosRegistroSucursalPilaDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/procesarDatosSucursalPila
 */
public class ProcesarDatosSucursalPila extends ServiceClient { 
    	private DatosRegistroSucursalPilaDTO datosRegistroSucursal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SucursalEmpresa result;
  
 	public ProcesarDatosSucursalPila (DatosRegistroSucursalPilaDTO datosRegistroSucursal){
 		super();
		this.datosRegistroSucursal=datosRegistroSucursal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosRegistroSucursal == null ? null : Entity.json(datosRegistroSucursal));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SucursalEmpresa) response.readEntity(SucursalEmpresa.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SucursalEmpresa getResult() {
		return result;
	}

 
  
  	public void setDatosRegistroSucursal (DatosRegistroSucursalPilaDTO datosRegistroSucursal){
 		this.datosRegistroSucursal=datosRegistroSucursal;
 	}
 	
 	public DatosRegistroSucursalPilaDTO getDatosRegistroSucursal (){
 		return datosRegistroSucursal;
 	}
  
}