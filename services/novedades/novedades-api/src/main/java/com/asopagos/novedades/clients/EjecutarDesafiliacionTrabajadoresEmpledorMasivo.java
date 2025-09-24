package com.asopagos.novedades.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.core.GenericType;
import java.lang.String;
import java.lang.Long;
/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/ejecutarDesafiliacionTrabajadoresEmpledorMasivo
 */
public class EjecutarDesafiliacionTrabajadoresEmpledorMasivo extends ServiceClient { 
    	private String numerRadicacionEmpresa;
        private Long idEmpledor;

 	public EjecutarDesafiliacionTrabajadoresEmpledorMasivo (String  numerRadicacionEmpresa,Long idEmpledor){
 		super();
		this.numerRadicacionEmpresa=numerRadicacionEmpresa;
        this.idEmpledor=idEmpledor;
 	}
 

 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
            .queryParam("numerRadicacionEmpresa", numerRadicacionEmpresa)
            .queryParam("idEmpledor", idEmpledor)
		.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
  	public void setNumerRadicacionEmpresa (String numerRadicacionEmpresa){
 		this.numerRadicacionEmpresa=numerRadicacionEmpresa;
 	}
 	
 	public String getNumerRadicacionEmpresa (){
 		return numerRadicacionEmpresa;
 	}
    public void setIdEmpledor (Long idEmpledor){
 		this.idEmpledor=idEmpledor;
 	}
 	
 	public Long getIdEmpledor (){
 		return idEmpledor;
 	}
}