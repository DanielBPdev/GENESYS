package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/ejecutarOrquestadorStaginFallecimiento
 */
public class EjecutarOrquestadorStaginFallecimiento extends ServiceClient { 
   	private Long fechaActual;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
   
  
 	public EjecutarOrquestadorStaginFallecimiento (Long fechaActual, String tipoIdentificacion, String numeroIdentificacion){
 		super();
		this.fechaActual=fechaActual;
        this.tipoIdentificacion=tipoIdentificacion;
        this.numeroIdentificacion=numeroIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("fechaActual", fechaActual)
            .queryParam("tipoIdentificacion", tipoIdentificacion)
            .queryParam("numeroIdentificacion",numeroIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setFechaActual (Long fechaActual){
 		this.fechaActual=fechaActual;
 	}
 	
 	public Long getFechaActual (){
 		return fechaActual;
 	}

    public void setTipoIdentificacion (String tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}

 	public String getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}

    public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}

 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}


  
  
}